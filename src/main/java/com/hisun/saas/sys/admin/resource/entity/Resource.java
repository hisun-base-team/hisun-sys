/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */
package com.hisun.saas.sys.admin.resource.entity;

import com.hisun.base.entity.AbstractResource;
import com.hisun.saas.sys.admin.role.entity.RoleResource;
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
@Table(name = "sys_resource")
public class Resource extends AbstractResource implements Serializable, Comparable<Resource> {

    private int type = 0;//0表示平台资源，1表示前台资源
    private List<RoleResource> roleResources;

    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    @OneToMany(mappedBy="role",fetch= FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.DELETE})
    public List<RoleResource> getRoleResources() {
        return roleResources;
    }
    public void setRoleResources(List<RoleResource> roleResources) {
        this.roleResources = roleResources;
    }

    @Override
    public int compareTo(Resource resource) {
        Resource r = resource;
        return sort.compareTo(r.getSort());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return new EqualsBuilder()
                .append(type, resource.type)
                .append(id, resource.id)
                .append(pId, resource.pId)
                .append(resourceName, resource.resourceName)
                .append(resourceType, resource.resourceType)
                .append(status, resource.status)
                .append(url, resource.url)
                .append(sort, resource.sort)
                .append(permission, resource.permission)
                .append(description, resource.description)
                .append(queryCode, resource.queryCode)
                .append(resourceIcon, resource.resourceIcon)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(pId)
                .append(resourceName)
                .append(resourceType)
                .append(status)
                .append(url)
                .append(sort)
                .append(permission)
                .append(description)
                .append(queryCode)
                .append(resourceIcon)
                .append(type)
                .toHashCode();
    }
}
