/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service.impl;

import com.hisun.saas.sys.tenant.Constants;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleTpltDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTplt;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.saas.sys.auth.service.PasswordHelper;
import com.hisun.saas.sys.auth.service.SessionHelper;
import com.hisun.saas.sys.auth.vo.PasswordSecurity;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantVo;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserRoleDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.util.EntityWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant, String> implements TenantService {

	private static final Logger logger = Logger.getLogger(TenantServiceImpl.class);

	private TenantDao tenantDao;

	@Resource
	private SessionHelper sessionHelper;

	@Resource
	private TenantUserDao tenantUserDao;

	@Resource
	private PasswordHelper passwordHelper;

	@Resource
	private TenantRoleDao tenantRoleDao;

	@Resource
	private TenantUserRoleDao tenantUserRoleDao;

	@Resource
	private TenantRoleTpltDao tenantRoleTpltDao;
	
    @Resource
	public void setBaseDao(BaseDao<Tenant, String> tenantDao) {
		this.baseDao = tenantDao;
        this.tenantDao = (TenantDao) tenantDao;
	}

	@Override
	public Tenant save(TenantVo vo){
		Tenant tenant = new Tenant();
		BeanUtils.copyProperties(vo,tenant);
		EntityWrapper.wrapperSaveBaseProperties(tenant,UserLoginDetailsUtil.getUserLoginDetails());
		tenantDao.save(tenant);
		//创建管理员
		TenantUser tenantUser = new TenantUser();
		tenantUser.setTenant(tenant);
		tenantUser.setUsername(vo.getAdminUserName());
		tenantUser.setRealname(vo.getAdminUserName());
		tenantUser.setStatus(TenantUser.STATUS_ACTIVATION);
		tenantUser.setSort(1);
		PasswordSecurity passwordSecurity = passwordHelper.encryptPassword(vo.getAdminUserPassword());
		tenantUser.setPassword(passwordSecurity.getPassword());
		tenantUser.setSalt(passwordSecurity.getSalt());
		tenantUserDao.save(tenantUser);
		//创建管理员角色
		TenantRole role = new TenantRole();
		role.setTenant(tenant);
		role.setRoleName("管理员");
		role.setRoleCode(Constants.ROLE_ADMIN_PREFIX+vo.getShortNamePy().toUpperCase());
		role.setDescription("单位管理员角色");
		role.setSort(1);
		role.setIsDefault(Constants.DEFAULT_ROLE);
		//设置缺省角色模板
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and("roleCodePrefix = : codePrefix","codePrefix",Constants.ROLE_ADMIN_PREFIX));
		List<TenantRoleTplt> tenantRoleTplts =  tenantRoleTpltDao.list(query,null);
		if(tenantRoleTplts!=null&& tenantRoleTplts.size()>0){
			role.setTenantRoleTplt(tenantRoleTplts.get(0));
		}
		tenantRoleDao.save(role);
		//关联管理员角色
		TenantUserRole tenantUserRole = new TenantUserRole();
		tenantUserRole.setUser(tenantUser);
		tenantUserRole.setRole(role);
		tenantUserRoleDao.save(tenantUserRole);
		return tenant;
	}

	public void updateToFreeze(Tenant tenant) {
		EntityWrapper.wrapperUpdateBaseProperties(tenant,UserLoginDetailsUtil.getUserLoginDetails());
		tenant.setTombstone(TombstoneEntity.TOMBSTONE_TRUE);
		tenantDao.update(tenant);
		//注销租户下的用户
		tenantUserDao.updateToFreezeByTenant(tenant.getId());
		List<TenantUser> userList = tenant.getUsers();
		for(TenantUser tenantUser : userList){
			try{
				sessionHelper.kickOutSession(tenantUser.getUsername());
			}catch (Exception e){
				logger.error("冻结租户操作:踢掉租户下所有在线用户SESSION失败");
			}
		}
	}


	public void updateToActivate(Tenant tenant) {
		EntityWrapper.wrapperUpdateBaseProperties(tenant,UserLoginDetailsUtil.getUserLoginDetails());
		tenant.setTombstone(TombstoneEntity.TOMBSTONE_FALSE);
		tenantDao.update(tenant);
		//用户全部也要激活
    	tenantUserDao.updateToActivateByTenant(tenant.getId());
	}

	@Override
	public void updateOwn(TenantVo vo) {
		UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
		Tenant tenant = tenantDao.getByPK(details.getTenantId());
		tenant.setName(vo.getName());
		tenantDao.update(tenant);
	}

	@Override
	public Tenant selectTenantByToken(String token) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" token=:token ", "token", token));
		List<Tenant> tenants = this.tenantDao.list(query, null,1,1);
		if(tenants.size()>0){
			return tenants.get(0);
		}
		return null;
	}

	@Override
	public Tenant getByName(String name) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" name = :name", "name", name));
		List<Tenant> tenants = tenantDao.list(query, null, 1, 1);
		return tenants.isEmpty()?null:tenants.get(0);
	}
}
