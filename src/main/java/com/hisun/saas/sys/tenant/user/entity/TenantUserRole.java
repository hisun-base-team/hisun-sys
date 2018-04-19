/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.entity;

import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_user_role")
public class TenantUserRole implements Serializable{


    private String id;//主键
    private TenantUser user;
    private TenantRole role;

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
    @JoinColumn(name="tenant_user_id")
    public TenantUser getUser() {
        return user;
    }
    public void setUser(TenantUser user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tenant_role_id")
    public TenantRole getRole() {
        return role;
    }
    public void setRole(TenantRole role) {
        this.role = role;
    }
}
