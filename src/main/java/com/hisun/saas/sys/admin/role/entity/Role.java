/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.role.entity;

import com.hisun.saas.sys.entity.AbstractRole;
import com.hisun.saas.sys.admin.user.entity.UserRole;
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
@Table(name="sys_role")
public class Role extends AbstractRole implements Serializable {

	private static final long serialVersionUID = 4862916079571039467L;
	private List<RoleResource> roleResources;
	private List<UserRole> userRoles;//用户拥有的角色(中间表)

	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE})
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	public List<RoleResource> getRoleResources() {
		return roleResources;
	}
	public void setRoleResources(List<RoleResource> roleResources) {
		this.roleResources = roleResources;
	}
}