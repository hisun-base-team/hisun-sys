/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.entity;

import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResource;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_2_resource")
public class Tenant2Resource {

    private String id;
    private Tenant tenant;
    private TenantResource tenantResource;
    private List<TenantRoleResource> tenantRoleResources;
    private List<Tenant2ResourcePrivilege> tenant2ResourcePrivileges;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    public TenantResource getTenantResource() {
        return tenantResource;
    }
    public void setTenantResource(TenantResource tenantResource) {
        this.tenantResource = tenantResource;
    }

    @OneToMany(mappedBy = "tenant2Resource")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantRoleResource> getTenantRoleResources() {
        return tenantRoleResources;
    }

    public void setTenantRoleResources(List<TenantRoleResource> tenantRoleResources) {
        this.tenantRoleResources = tenantRoleResources;
    }

    @OneToMany(mappedBy = "tenant2Resource")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Tenant2ResourcePrivilege> getTenant2ResourcePrivileges() {
        return tenant2ResourcePrivileges;
    }

    public void setTenant2ResourcePrivileges(List<Tenant2ResourcePrivilege> tenant2ResourcePrivileges) {
        this.tenant2ResourcePrivileges = tenant2ResourcePrivileges;
    }
}
