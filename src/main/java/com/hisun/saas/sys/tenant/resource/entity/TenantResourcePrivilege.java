/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.entity;

import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResourcePrivilege;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_resource_privilege")
public class TenantResourcePrivilege implements Serializable{

    private static final long serialVersionUID = -5751870644084541232L;
    private String id;
    private TenantResource tenantResource;
    private TenantPrivilege tenantPrivilege;
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

    @ManyToOne(targetEntity = TenantResource.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    public TenantResource getTenantResource() {
        return tenantResource;
    }

    public void setTenantResource(TenantResource tenantResource) {
        this.tenantResource = tenantResource;
    }

    @ManyToOne(targetEntity = TenantPrivilege.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id")
    public TenantPrivilege getTenantPrivilege() {
        return tenantPrivilege;
    }
    public void setTenantPrivilege(TenantPrivilege tenantPrivilege) {
        this.tenantPrivilege = tenantPrivilege;
    }

    @OneToMany(mappedBy = "tenantPrivilege")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantRoleResourcePrivilege> getTenantRoleResourcePrivileges() {
        return tenantRoleResourcePrivileges;
    }
    public void setTenantRoleResourcePrivileges(List<TenantRoleResourcePrivilege> tenantRoleResourcePrivileges) {
        this.tenantRoleResourcePrivileges = tenantRoleResourcePrivileges;
    }
}
