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
@Table(name="sys_activation")
public class Activation implements Serializable {

	private static final long serialVersionUID = -4965449106763660156L;

	private String id;//主键
	private String email;
	private String status;//激活状态
	private String inviteUserId;//邀请人的userId
	private String inviteUserName;//邀请人姓名
	private String inviteTenantId;//邀请人组织
    private String roleId;// 角色

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
	@Column(name="email",length=256)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="status",length=4)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="invite_user_id",length=64)
	public String getInviteUserId() {
		return inviteUserId;
	}
	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
	@Column(name="invite_user_name",length=32)
	public String getInviteUserName() {
		return inviteUserName;
	}
	public void setInviteUserName(String inviteUserName) {
		this.inviteUserName = inviteUserName;
	}
	@Column(name="invite_tenant_id",length=100)
	public String getInviteTenantId() {
		return inviteTenantId;
	}
	public void setInviteTenantId(String inviteTenantId) {
		this.inviteTenantId = inviteTenantId;
	}
	@Column(name="role_id", length = 32)
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
