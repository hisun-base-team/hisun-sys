package com.hisun.saas.sys.tenant.vo;

import java.util.Date;

public class TenantOutside{
	
	private String id;//组织id
	
	private String tenantName;//组织名
	
	private String property;//性质，0运营商、1服务提供商、2客户
	
	private Date createTime;//团队/组织创建时间

	private boolean status;//1表示注销
	
	private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
