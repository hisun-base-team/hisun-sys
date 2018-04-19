/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.entity;

import com.hisun.base.entity.TombstoneEntity;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant")
public class Tenant extends TombstoneEntity implements Serializable {


    private String id;//主键
    private String name;//租户名
    private int status;//保留以后使用
    private String token = RandomStringUtils.randomAlphanumeric(32);
    private List<TenantUser> users;
    private List<TenantRole> roles;
    private List<Tenant2Resource> tenant2Resources;
    private List<Tenant2Privilege> tenant2Privileges;

    public Tenant() {}
    public Tenant(String id) {
        this.id = id;
    }


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

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantUser> getUsers() {
        return users;
    }

    public void setUsers(List<TenantUser> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TenantRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TenantRole> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Tenant2Resource> getTenant2Resources() {
        return tenant2Resources;
    }

    public void setTenant2Resources(List<Tenant2Resource> tenant2Resources) {
        this.tenant2Resources = tenant2Resources;
    }

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Tenant2Privilege> getTenant2Privileges() {
        return tenant2Privileges;
    }

    public void setTenant2Privileges(List<Tenant2Privilege> tenant2Privileges) {
        this.tenant2Privileges = tenant2Privileges;
    }
}
