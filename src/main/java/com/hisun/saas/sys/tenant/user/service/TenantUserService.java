/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.service;

import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.entity.TenantDepartment;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.entity.Activation;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.vo.TenantUserVo;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;

import java.util.List;


public interface TenantUserService extends BaseService<TenantUser,String> {


    UserLoginDetails findUserLoginDetails(String username);
    void update(TenantUser tenantUser, boolean changePassword);
    void resetPwd(TenantUser tenantUser,String newPwd);
    boolean checkUsername(String username);
    TenantUser findByUsername(String username);
    void saveInviteRegister(TenantRegisterVo vo, String activationId, String roleId, String tenantId);
    TenantUser findByEmail(String email);
    boolean credentialsPassword(TenantUser user, String inputPassword);
    Integer getMaxSort(String departmentId);
    void updateUser(TenantUser tenantUser,String oldDepartmentId,Integer oldSort);
    void saveOrUpdateUserRoles(TenantUser tenantUser,List<String> roleIds);
}
