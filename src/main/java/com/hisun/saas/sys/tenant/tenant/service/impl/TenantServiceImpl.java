/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service.impl;

import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.base.auth.service.PasswordHelper;
import com.hisun.base.auth.service.SessionHelper;
import com.hisun.base.auth.vo.PasswordSecurity;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.base.vo.PagerVo;
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
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantServiceImpl extends
		BaseServiceImpl<Tenant, String> implements TenantService {

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
	public void setBaseDao(BaseDao<Tenant, String> tenantDao) {
		this.baseDao = tenantDao;
        this.tenantDao = (TenantDao) tenantDao;
	}
    
	@Override
	public PagerVo<Tenant> listPager(String name,Integer tombstone,String start,String end, int pageNum,int pageSize) {
		CommonConditionQuery query = new CommonConditionQuery();
			if(StringUtils.isNotBlank(start)){
			query.add(CommonRestrictions.and(" createDate >= :start ", "start", new DateTime(start).toDate()));
		}
		if(StringUtils.isNotBlank(end)){
			query.add(CommonRestrictions.and(" createDate <= :end ", "end", new DateTime(end).plusDays(1).toDate()));
		}
		if(StringUtils.isNotBlank(name)){
			query.add(CommonRestrictions.and(" name like :name ", "name", "%"+name+"%"));
		}
		if(tombstone!=-1){
			query.add(CommonRestrictions.and(" tombstone = :tombstone ", "tombstone", tombstone));
		}
		CommonOrderBy orderBy = new CommonOrderBy();
		orderBy.add(CommonOrder.desc("createDate"));
		List<Tenant> list = tenantDao.list(query, orderBy, pageNum, pageSize);
		Long total = tenantDao.count(query);
		PagerVo<Tenant> pager = new PagerVo<Tenant>(list, total.intValue(), pageNum, pageSize);
		return pager;
	}

	@Override
	public Tenant add(TenantVo vo) throws GenericException {
		Tenant entity = new Tenant();
		BeanUtils.copyProperties(vo,entity);
		entity.setId(null);
		setCreateUser(UserLoginDetailsUtil.getUserLoginDetails(), entity);
		tenantDao.save(entity);

		//创建管理员
		TenantUser tenantUser = new TenantUser();
		tenantUser.setTenant(entity);
		tenantUser.setUsername(vo.getUsername());
		tenantUser.setEmail(vo.getEmail());
		PasswordSecurity passwordSecurity = passwordHelper.encryptPassword(vo.getPassword());
		tenantUser.setPassword(passwordSecurity.getPassword());
		tenantUser.setSalt(passwordSecurity.getSalt());
		tenantUserDao.save(tenantUser);

		//关联管理员角色
		TenantRole tenantRole = tenantRoleDao.getByCode("ROLE_TENANTADMIN");
		if(tenantRole == null){
			throw new GenericException("管理员角色不存在");
		}
		TenantUserRole tenantUserRole = new TenantUserRole();
		tenantUserRole.setRole(tenantRole);
		tenantUserRole.setUser(tenantUser);
		tenantUserRoleDao.save(tenantUserRole);
		return entity;
	}

	@Override
	public Tenant update(TenantVo vo) {
		Tenant entity = tenantDao.getByPK(vo.getId());
		BeanUtils.copyProperties(vo,entity);
		setUpdateUser(UserLoginDetailsUtil.getUserLoginDetails(), entity);
		tenantDao.update(entity);
		return entity;
	}

	@Override
	public void deleteById(String id) {
		Tenant tenant = tenantDao.getByPK(id);
		setUpdateUser(UserLoginDetailsUtil.getUserLoginDetails(),tenant);
		tenant.setTombstone(TombstoneEntity.TOMBSTONE_TRUE);
		tenantDao.update(tenant);
		//注销租户下的用户
		tenantUserDao.deleteByTenantId(id);
		List<TenantUser> userList = tenantUserDao.listByTenantId(id);
		for(TenantUser tenantUser : userList){
			try{
				sessionHelper.kickOutSession(tenantUser.getUsername());
			}catch (Exception e){
				//只抓错不用输出，踢掉SESSION失败
			}
		}
	}

	@Override
	public int countUserByTenantId(String id) {
		String sql = "select count(1) from sys_tenant_user where tenant_id = :tenantId";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tenantId",id);
		int count = tenantDao.countBySql(sql,paramMap);
		return count;
	}

	@Override
	public void updateActivate(String id) {
		Tenant tenant = tenantDao.getByPK(id);
		setUpdateUser(UserLoginDetailsUtil.getUserLoginDetails(), tenant);
		tenant.setTombstone(TombstoneEntity.TOMBSTONE_FALSE);
		tenantDao.update(tenant);
		//用户全部也要激活
		tenantUserDao.activateByTenantId(id);
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
