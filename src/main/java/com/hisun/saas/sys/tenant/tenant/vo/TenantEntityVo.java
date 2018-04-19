package com.hisun.saas.sys.tenant.tenant.vo;

import com.hisun.base.vo.TombstoneVo;

/**
 * Created by zhouying on 2017/10/11.
 */
public class TenantEntityVo extends TombstoneVo {

    private String tenantId;
    private TenantVo tenantVo;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public TenantVo getTenantVo() {
        return tenantVo;
    }

    public void setTenantVo(TenantVo tenantVo) {
        this.tenantVo = tenantVo;
    }
}
