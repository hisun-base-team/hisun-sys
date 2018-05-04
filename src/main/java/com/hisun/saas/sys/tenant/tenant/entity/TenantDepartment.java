/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.entity;

import com.hisun.saas.sys.tenant.base.entity.TenantEntity;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
@Table(name = "sys_tenant_department")
public class TenantDepartment extends TenantEntity implements Serializable{

    private static final long serialVersionUID = -1213942874623720335L;
    private String id;
    private String name;
    private Integer sort;

    private TenantDepartment parent;
    private List<TenantDepartment> children;
    private List<TenantUser> tenantUsers;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false, unique = true, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public TenantDepartment getParent() {
        return parent;
    }

    public void setParent(TenantDepartment parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.REFRESH})
    public List<TenantDepartment> getChildren() {
        return children;
    }

    public void setChildren(List<TenantDepartment> children) {
        this.children = children;
    }

    @OneToMany(mappedBy = "tenantDepartment", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.REFRESH})
    public List<TenantUser> getTenantUsers() {
        return tenantUsers;
    }

    public void setTenantUsers(List<TenantUser> tenantUsers) {
        this.tenantUsers = tenantUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TenantDepartment that = (TenantDepartment) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(sort, that.sort)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(sort)
                .toHashCode();
    }
}
