/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.entity;

import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name="sys_tenant_role_resource")
public class TenantRoleResource implements Serializable{


    private String id;//主键
    private TenantRole role;
    private Tenant2Resource tenant2Resource;
    private TenantResource tenantResource;
    private List<TenantRoleResourcePrivilege> tenantRoleResourcePrivileges;


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
    @JoinColumn(name="role_id")
    public TenantRole getRole() {
        return role;
    }
    public void setRole(TenantRole role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tenant_2_resource_id")
    public Tenant2Resource getTenant2Resource() {
        return tenant2Resource;
    }
    public void setTenant2Resource(Tenant2Resource tenant2Resource) {
        this.tenant2Resource = tenant2Resource;
    }

    @ManyToOne(targetEntity =TenantResource.class, fetch = FetchType.LAZY)
    @JoinColumn(name="resource_id")
    public TenantResource getTenantResource() {
        return tenantResource;
    }
    public void setTenantResource(TenantResource tenantResource) {
        this.tenantResource = tenantResource;
    }


    @OneToMany(mappedBy = "tenantRoleResource")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantRoleResourcePrivilege> getTenantRoleResourcePrivileges() {
        return tenantRoleResourcePrivileges;
    }
    public void setTenantRoleResourcePrivileges(List<TenantRoleResourcePrivilege> tenantRoleResourcePrivileges) {
        this.tenantRoleResourcePrivileges = tenantRoleResourcePrivileges;
    }
}
