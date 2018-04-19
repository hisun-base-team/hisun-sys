/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.base.entity;

import com.hisun.base.entity.TombstoneEntity;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantFilterParam", type = "string"))
@Filters({
        @Filter(name = "tenantFilter", condition = " tenant_id=:tenantFilterParam ")
})
public class TenantEntity extends TombstoneEntity {

    protected Tenant tenant;

    @ManyToOne(targetEntity = Tenant.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

}
