package com.hisun.saas.sys.tenant.user.dao;

import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.base.dao.BaseDao;

import java.util.List;

/**
 * Created by liyikai on 15/11/18.
 */
public interface TenantUserDao extends BaseDao<TenantUser,String>{

    /**
     * 防止并发用户名和邮箱会出现重复，需要额外写保存发吧
     * @param vo
     * @return
     */
    TenantUser saveRegister(TenantRegisterVo vo, Tenant tenant);

    List<TenantUser> listByTenantId(String tenantId);

    /**
     * 根据租户ID注销租户下用户
     * @param tenantId
     */
    void deleteByTenantId(String tenantId);

    /**
     * 激活租户下全部用户
     * @param tenantId
     */
    void activateByTenantId(String tenantId);
}
