/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.entity;

import com.hisun.saas.sys.tenant.base.entity.TenantEntityInterface;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.entity.AbstractUser;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantFilterParam", type = "string"))
@Filters({
		@Filter(name = "tenantFilter", condition = " tenant_id=:tenantFilterParam ")
})
@Entity
@Table(name="sys_tenant_user")
public class TenantUser extends AbstractUser implements TenantEntityInterface, Serializable {

	public static int STATUS_NO_ACTIVATION = 0;//未激活
	public static int STATUS_ACTIVATION = 1;//激活


	private Tenant tenant;
	private int status=STATUS_ACTIVATION;//状态
	private List<TenantUserRole> userRoles;

	public TenantUser(){}
	public TenantUser(String id){
		setId(id);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tenant_id")
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	public List<TenantUserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<TenantUserRole> userRoles) {
		this.userRoles = userRoles;
	}
}