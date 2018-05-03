/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.entity;

import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_role_tplt_resource")
public class TenantRoleTpltResource implements Serializable{

    private static final long serialVersionUID = -537977489846250898L;
    private String id;
    private String resourceName;

    private TenantResource tenantResource;
    private TenantRoleTplt tenantRoleTplt;

    @Id
    @GenericGenerator(name="generator",strategy="uuid")
    @GeneratedValue(generator="generator")
    @Column(name="id",nullable=false,unique=true,length=32)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    @Basic
    @Column(name = "resource_name", nullable = false, length = 255)
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }


    @ManyToOne(targetEntity = TenantResource.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    public TenantResource getTenantResource() {
        return tenantResource;
    }

    public void setTenantResource(TenantResource tenantResource) {
        this.tenantResource = tenantResource;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_tplt_id")
    public TenantRoleTplt getTenantRoleTplt() {
        return tenantRoleTplt;
    }

    public void setTenantRoleTplt(TenantRoleTplt tenantRoleTplt) {
        this.tenantRoleTplt = tenantRoleTplt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TenantRoleTpltResource that = (TenantRoleTpltResource) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(tenantResource.getId(), that.getTenantResource().getId())
                .append(tenantRoleTplt.getId(), that.getTenantRoleTplt().getId())
                .append(resourceName, that.resourceName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tenantResource.getId())
                .append(tenantRoleTplt.getId())
                .append(resourceName)
                .toHashCode();
    }
}
