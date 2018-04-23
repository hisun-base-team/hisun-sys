/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.entity;

import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_resource")
public class TenantResource extends AbstractResource
        implements Serializable, Comparable<TenantResource> {

    private static final long serialVersionUID = -7479768004811251553L;
    private List<Tenant2Resource> tenant2Resources;

    @Override
    public int compareTo(TenantResource resource) {
        TenantResource r = resource;
        return sort.compareTo(r.getSort());
    }

    @OneToMany(mappedBy = "tenantResource")
    @Cascade({CascadeType.ALL})
    public List<Tenant2Resource> getTenant2Resources() {
        return tenant2Resources;
    }

    public void setTenant2Resources(List<Tenant2Resource> tenant2Resources) {
        this.tenant2Resources = tenant2Resources;
    }
}
