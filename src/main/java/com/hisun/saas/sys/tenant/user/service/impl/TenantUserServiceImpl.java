/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.service.PasswordHelper;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.auth.vo.PasswordSecurity;
import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.entity.AbstractRole;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.exception.ErrorMsgShowException;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.resource.dao.ResourceDao;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.dao.ActivationDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserRoleDao;
import com.hisun.saas.sys.tenant.user.entity.Activation;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.saas.sys.tenant.user.vo.TenantUserVo;
import com.hisun.saas.sys.util.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantUserServiceImpl extends BaseServiceImpl<TenantUser,String> implements TenantUserService {

    private static Logger logger = Logger.getLogger(TenantUserServiceImpl.class);

    private TenantUserDao tenantUserDao;
    @javax.annotation.Resource
    private TenantUserRoleDao tenantUserRoleDao;
    @javax.annotation.Resource
    private ResourceDao resourceDao;
    @javax.annotation.Resource
    private PasswordHelper passwordHelper;
    @javax.annotation.Resource
    private TenantDao tenantDao;
    @javax.annotation.Resource
    private TenantRoleDao tenantRoleDao;
    @javax.annotation.Resource
    private TenantRoleResourceDao tenantRoleResourceDao;
    @javax.annotation.Resource
    private ActivationDao activationDao;


    @javax.annotation.Resource
    @Override
    public void setBaseDao(BaseDao<TenantUser, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantUserDao = (TenantUserDao)baseDao;
    }

    @Override
    public String save(TenantUser entity) {
        //加密密码
        passwordHelper.encryptPassword(entity);
        entity.setCreateDate(new Date());
        String pk = super.save(entity);
        return pk;
    }

    @Override
    public void update(TenantUser entity,boolean changePassword) {
        if(changePassword){
            //加密密码
            passwordHelper.encryptPassword(entity);
        }
        entity.setUpdateDate(new Date());
        super.update(entity);
    }

    @Override
    public UserLoginDetails findUserLoginDetails(String username) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" username = :username", "username", username));
        List<TenantUser> userList = tenantUserDao.list(query, null);
        TenantUser user = userList.size()==0?null:userList.get(0);
        if (user != null) {
            UserLoginDetails userLoginDetails = new UserLoginDetails();
            BeanUtils.copyProperties(user,userLoginDetails);
            userLoginDetails.setUser(user);
            userLoginDetails.setUserid(user.getId());
            userLoginDetails.setUsername(user.getUsername());
            userLoginDetails.setRealname(StringUtils.isBlank(user.getRealname())?user.getUsername():user.getRealname());
            userLoginDetails.setTenant(user.getTenant());
            userLoginDetails.setTenantId(user.getTenant().getId());
            userLoginDetails.setTenantName(user.getTenant().getName());
            userLoginDetails.setUserType(Constants.USER_TYPE_TENANT);
            List<AbstractRole> roles = new ArrayList<AbstractRole>();
            List<TenantUserRole> tenantUserRoles = user.getUserRoles();
            for(TenantUserRole userRole : tenantUserRoles){
                roles.add(userRole.getRole());
            }
            userLoginDetails.setRoles(roles);
            List<AbstractResource> resources = new ArrayList<AbstractResource>();
            List<TenantResource> tenantResources = this.tenantRoleResourceDao.findResourcesByUser(user.getId());
            for(TenantResource tenantResource : tenantResources){
                resources.add(tenantResource);
            }
            userLoginDetails.setResources(resources);
            return userLoginDetails;
        }
        return null;
    }

    @Override
    public void saveInviteRegister(TenantRegisterVo vo, String activationId, String roleId, String tenantId) {
        Tenant tenant = tenantDao.getByPK(tenantId);
        TenantUser tenantUser = tenantUserDao.saveRegister(vo, tenant);
        if(tenantUser == null){
            //保存不成功，抛错回退
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" username = :username", "username", vo.getUsername().trim()));
            long count = tenantUserDao.count(query);
            if(count > 0){
                throw new ErrorMsgShowException("用户名以被注册");
            }
            query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" email = :email", "email", vo.getEmail().trim()));
            count = this.tenantUserDao.count(query);
            if(count > 0){
                throw new ErrorMsgShowException("用户名以被注册");
            }
        }
        tenantUser.setTombstone(TombstoneEntity.TOMBSTONE_FALSE);
        tenantUser.setStatus(TenantUser.STATUS_ACTIVATION);
        TenantRole role = tenantRoleDao.getByPK(roleId);
        TenantUserRole userRole = new TenantUserRole();
        userRole.setUser(tenantUser);
        userRole.setRole(role);
        tenantUserRoleDao.save(userRole);
        Activation activation = activationDao.getByPK(activationId);
        activation.setStatus("1");
        activationDao.update(activation);
    }


    public boolean checkUsername(String username){
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" username = :username", "username", username));
        Long count = tenantUserDao.count(query);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public TenantUser findByUsername(String username) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" username = :username", "username", username));
        List<TenantUser> userList = tenantUserDao.list(query, null);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public TenantUser findByEmail(String email) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" User.email = :email", "email", email));
        List<TenantUser> userList = tenantUserDao.list(query, null);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public boolean credentialsPassword(TenantUser user, String inputPassword) {
        return passwordHelper.credentialsPassword(user, inputPassword);
    }

    @Override
    public void saveUserAndRole(TenantUser tenantUser, String roleId) {
        //加密密码
        passwordHelper.encryptPassword(tenantUser);
        tenantUser.setCreateDate(new Date());
        String pk = super.save(tenantUser);
        TenantRole role = tenantRoleDao.getByPK(roleId);
        // 判断角色，没有则默认添加普通用户角色
        if (role == null) {
            role = tenantRoleDao.getByCode("ROLE_TENANT");
        }

        TenantUserRole userRole = new TenantUserRole();
        userRole.setUser(tenantUser);
        userRole.setRole(role);
        tenantUserRoleDao.save(userRole);
    }

    @Override
    public void update(TenantUser tenantUser) {
        tenantUserDao.update(tenantUser);
    }

    @Override
    public int countMember(String tenantId) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and("tenant.id = :tenantId", "tenantId", tenantId));
        Long total = tenantUserDao.count(query);
        return total.intValue();
    }

    @Override
    public void saveRegisterToNewTenant(TenantUser tenantUser, Activation activation, Tenant tenant) throws Exception{

        // 注册到新租户
        tenantUser.setTenant(tenant);
        tenantUserDao.update(tenantUser);
        // 更新激活状态
        activationDao.update(activation, new String[]{"status"});

        // 保存新角色
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" user = :user ", "user", tenantUser));
        tenantUserRoleDao.deleteBatch(query);

        TenantRole tenantRole = tenantRoleDao.getByPK(activation.getRoleId());
        TenantUserRole tenantUserRole = new TenantUserRole();
        tenantUserRole.setRole(tenantRole);
        tenantUserRole.setUser(tenantUser);

        tenantUserRoleDao.save(tenantUserRole);
    }

    @Override
    public List<TenantUser> list() {
        CommonConditionQuery query = new CommonConditionQuery();
        UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
        if(details!=null && details.getTenantId()!=null){
            query.add(CommonRestrictions.and("tenant.id = :tenantId", "tenantId", details.getTenantId()));
        }
        return tenantUserDao.list(query,null);
    }

    @Override
    public String saveNoPassword(TenantUser user) {
        user.setCreateDate(new Date());
        String pk = super.save(user);
//        List<TenantUserRole> roles =  tenantUserRoleDao.getUserRoleByUserId(pk);
//        // 判断角色，没有则默认添加普通用户角色
//        if (roles == null || roles.size() < 1) {
//            TenantRole role = tenantRoleDao.getByCode("ROLE_TENANTADMIN");
//            TenantUserRole userRole = new TenantUserRole();
//            userRole.setUser(user);
//            userRole.setRole(role);
//            tenantUserRoleDao.save(userRole);
//        }
        return pk;
    }



    public void resetPwd(TenantUser tenantUser,String newPwd){
        PasswordSecurity  passwordSecurity = passwordHelper.encryptPassword(newPwd);
        tenantUser.setSalt(passwordSecurity.getSalt());
        tenantUser.setPassword(passwordSecurity.getPassword());
        this.tenantUserDao.update(tenantUser);
    }
}
