package com.hisun.saas.sys.tenant.user.service;

import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.base.service.BaseService;

import java.util.List;

/**
 * Created by liyikai on 15/11/18.
 */
public interface TenantUserRoleService extends BaseService<TenantUserRole,String> {

    /**
     * 根据用户ID查询用户拥有的角色
     * @param userId
     * @return
     */
    List<TenantUserRole> getUserRoleByUserId(String userId);

    /**
     * 保存用户与角色的关系
     * @param userId
     * @param roleIds
     */
    void saveOrUpdate(String userId, String[] roleIds);
}
