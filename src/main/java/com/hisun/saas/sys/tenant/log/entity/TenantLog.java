/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.log.entity;

import com.hisun.saas.sys.log.LogOperateStatus;
import com.hisun.saas.sys.tenant.base.entity.TenantEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_log")
public class TenantLog extends TenantEntity implements Serializable {

	private static final long serialVersionUID = -8654892637892153229L;

	private String id;//逻辑主键
	private String userId;//操作人ID
	private String userName;//操作人姓名
	private String ip;
	private Date operateTime;//操作时间
	private String invokeMethod;//调用方法名
	private String content;//日志内容
	private int type;//日志类型
	private int status = LogOperateStatus.NORMAL.getStatus();//运行状态

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false, unique = true,length=32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="user_id",length=36)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", nullable = false)
	public String getUserName() { return userName;}
	public void setUserName(String userName) { this.userName = userName;}

	@Column(name="operate_time",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name="ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name="type",nullable=false,length=1)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	@Column(name="invoke_method")
	public String getInvokeMethod() {
		return invokeMethod;
	}

	public void setInvokeMethod(String invokeMethod) {
		this.invokeMethod = invokeMethod;
	}

	@Column(name="content")
	@Type(type="text")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() { return status;}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
