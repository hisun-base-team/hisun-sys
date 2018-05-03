/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.entity;

import com.hisun.base.entity.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_role_tplt")
public class TenantRoleTplt extends BaseEntity implements Serializable{


    private static final long serialVersionUID = 7275667399297325772L;
    private String id;
    private String roleName;
    private String roleCodePrefix;
    private String roleDescription;

    private List<TenantRoleTpltResource> tenantRoleTpltResources;

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
    @Column(name = "role_name", nullable = false, length = 100)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_code_prefix", nullable = false, length = 100)
    public String getRoleCodePrefix() {
        return roleCodePrefix;
    }

    public void setRoleCodePrefix(String roleCodePrefix) {
        this.roleCodePrefix = roleCodePrefix;
    }

    @Basic
    @Column(name = "role_description", length = 200)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }


    @OneToMany(mappedBy = "tenantRoleTplt",fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantRoleTpltResource> getTenantRoleTpltResources() {
        return tenantRoleTpltResources;
    }

    public void setTenantRoleTpltResources(List<TenantRoleTpltResource> tenantRoleTpltResources) {
        this.tenantRoleTpltResources = tenantRoleTpltResources;
    }

    public void addTenantRoleTpltResource(TenantRoleTpltResource tenantRoleTpltResource){
        if(this.tenantRoleTpltResources==null){
            this.tenantRoleTpltResources = new ArrayList<>();
        }
        tenantRoleTpltResource.setTenantRoleTplt(this);
        this.tenantRoleTpltResources.add(tenantRoleTpltResource);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TenantRoleTplt that = (TenantRoleTplt) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(roleName, that.roleName)
                .append(roleCodePrefix, that.roleCodePrefix)
                .append(roleDescription, that.roleDescription)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(roleName)
                .append(roleCodePrefix)
                .append(roleDescription)
                .toHashCode();
    }
}
