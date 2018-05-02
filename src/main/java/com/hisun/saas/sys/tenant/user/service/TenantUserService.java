/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.service;

import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.entity.Activation;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.vo.TenantUserVo;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;

import java.util.List;


public interface TenantUserService extends BaseService<TenantUser,String> {

    /**
     * 根据条件分页查询用户列表
     * @param pageSize
     * @param pageNum
     * @param userVo
     * @return
     */
    PagerVo<TenantUserVo> pagerList(int pageSize, int pageNum, TenantUserVo userVo);

    public UserLoginDetails findUserLoginDetails(String username);

    /**
     * 更改密码
     * @param entity
     * @param changePassword
     */
    void update(TenantUser entity, boolean changePassword);

    TenantUser findByUsername(String username);

    public void saveInviteRegister(TenantRegisterVo vo, String activationId, String roleId, String tenantId);

    TenantUser findByEmail(String email);

    /**
     * 确认输入的密码是否正确
     * @param user
     * @param inputPassword
     * @return
     */
    boolean credentialsPassword(TenantUser user, String inputPassword);

    void saveUserAndRole(TenantUser tenantUser, String roleId);

    public int countMember(String tenantId);

    /**
     * 注册到新租户,并修改角色
     * @param tenantUser
     * @param activation
     * @param tenant
     */
    void saveRegisterToNewTenant(TenantUser tenantUser, Activation activation, Tenant tenant) throws Exception;

    /**
     * 覆盖LIST，默认加上TENANT
     * @return
     */
    public List<TenantUser> list();

    String saveNoPassword(TenantUser user);
}
