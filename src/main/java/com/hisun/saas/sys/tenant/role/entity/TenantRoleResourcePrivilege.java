/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.entity;

import com.hisun.base.entity.AbstractRoleResourcePrivilege;
import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResourcePrivilege;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2ResourcePrivilege;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_role_resource_privilege")
public class TenantRoleResourcePrivilege extends AbstractRoleResourcePrivilege implements Serializable {

    private String id;
    private TenantRole tenantRole;
    private TenantResource tenantResource;
    private TenantPrivilege tenantPrivilege;
    private Tenant2ResourcePrivilege tenant2ResourcePrivilege;
    private TenantRoleResource tenantRoleResource;
    private TenantResourcePrivilege tenantResourcePrivilege;

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

    @ManyToOne(targetEntity =  TenantRole.class,fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    public TenantRole getTenantRole() {
        return tenantRole;
    }

    public void setTenantRole(TenantRole tenantRole) {
        this.tenantRole = tenantRole;
    }

    @ManyToOne(targetEntity =  TenantResource.class,fetch = FetchType.LAZY)
    @JoinColumn(name="resource_id")
    public TenantResource getTenantResource() {
        return tenantResource;
    }

    public void setTenantResource(TenantResource tenantResource) {
        this.tenantResource = tenantResource;
    }

    @ManyToOne(targetEntity =  TenantPrivilege.class,fetch = FetchType.LAZY)
    @JoinColumn(name="privilege_id")
    public TenantPrivilege getTenantPrivilege() {
        return tenantPrivilege;
    }

    public void setTenantPrivilege(TenantPrivilege tenantPrivilege) {
        this.tenantPrivilege = tenantPrivilege;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tenant_2_resource_privilege_id")
    public Tenant2ResourcePrivilege getTenant2ResourcePrivilege() {
        return tenant2ResourcePrivilege;
    }

    public void setTenant2ResourcePrivilege(Tenant2ResourcePrivilege tenant2ResourcePrivilege) {
        this.tenant2ResourcePrivilege = tenant2ResourcePrivilege;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_resource_id")
    public TenantRoleResource getTenantRoleResource() {
        return tenantRoleResource;
    }

    public void setTenantRoleResource(TenantRoleResource tenantRoleResource) {
        this.tenantRoleResource = tenantRoleResource;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resource_privilege_id")
    public TenantResourcePrivilege getTenantResourcePrivilege() {
        return tenantResourcePrivilege;
    }
    public void setTenantResourcePrivilege(TenantResourcePrivilege tenantResourcePrivilege) {
        this.tenantResourcePrivilege = tenantResourcePrivilege;
    }



}
