/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name="sys_tenant_password_reset")
public class PasswordReset implements Serializable{


    private static final long serialVersionUID = -937896914751345495L;
    private String id;//主键
    private TenantUser tenantUser;

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
    @ManyToOne(targetEntity = TenantUser.class)
    @JoinColumn(name="tenant_user_id")
    public TenantUser getTenantUser() {
        return tenantUser;
    }

    public void setTenantUser(TenantUser tenantUser) {
        this.tenantUser = tenantUser;
    }
}
