/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.role.entity;

import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResourcePrivilege;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name="sys_role_resource")
public class RoleResource implements Serializable{


    private String id;//主键
    private Role role;
    private Resource resource;


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
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resource_id")
    public Resource getResource() { return resource;}
    public void setResource(Resource resource) { this.resource = resource;}
}
