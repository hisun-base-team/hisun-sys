/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.user.entity;

import com.hisun.base.entity.AbstractUser;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */

@Entity
@Table(name = "sys_user")
public class User extends AbstractUser implements Serializable {

    private static final long serialVersionUID = 4151243613906297549L;

    private boolean admin = false;
    private List<UserRole> userRoles;//用户拥有的角色(中间表)

    public User() {
    }
    public User(String id) {
        setId(id);
    }
    @Column(name = "is_admin")
    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.DELETE})
    public List<UserRole> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}