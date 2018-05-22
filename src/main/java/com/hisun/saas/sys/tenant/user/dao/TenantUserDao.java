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

    TenantUser saveRegister(TenantRegisterVo vo, Tenant tenant);
    void updateToFreezeByTenant(String tenantId);
    void updateToActivateByTenant(String tenantId);
}
