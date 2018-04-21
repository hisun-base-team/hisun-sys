/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.entity;

import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResourcePrivilege;
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
@Table(name = "sys_tenant_2_resource_privilege")
public class Tenant2ResourcePrivilege implements Serializable{

    private String id;
    private Tenant tenant;
    private Tenant2Resource tenant2Resource;
    private TenantPrivilege tenantPrivilege;
    private String sqlFilterExpress;
    private String hqlFilterExpress;
    private String selectedExpress;
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

    @ManyToOne(targetEntity = Tenant.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_2_resource_id")
    public Tenant2Resource getTenant2Resource() { return tenant2Resource; }
    public void setTenant2Resource(Tenant2Resource tenant2Resource) { this.tenant2Resource = tenant2Resource;}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id")
    public TenantPrivilege getTenantPrivilege() {
        return tenantPrivilege;
    }
    public void setTenantPrivilege(TenantPrivilege tenantPrivilege) {
        this.tenantPrivilege = tenantPrivilege;
    }

    @Column(name="sql_filter_express",nullable=false)
    public String getSqlFilterExpress() { return sqlFilterExpress;}
    public void setSqlFilterExpress(String sqlFilterExpress) { this.sqlFilterExpress = sqlFilterExpress;}

    @Column(name="selected_express",nullable=false)
    public String getSelectedExpress() {
        return selectedExpress;
    }
    public void setSelectedExpress(String selectedExpress) {
        this.selectedExpress = selectedExpress;
    }

    @Column(name="hql_filter_express",nullable=false)
    public String getHqlFilterExpress() {
        return hqlFilterExpress;
    }

    public void setHqlFilterExpress(String hqlFilterExpress) {
        this.hqlFilterExpress = hqlFilterExpress;
    }

    @OneToMany(mappedBy = "tenant2ResourcePrivilege")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantRoleResourcePrivilege> getTenantRoleResourcePrivileges() {
        return tenantRoleResourcePrivileges;
    }

    public void setTenantRoleResourcePrivileges(List<TenantRoleResourcePrivilege> tenantRoleResourcePrivileges) {
        this.tenantRoleResourcePrivileges = tenantRoleResourcePrivileges;
    }
}
