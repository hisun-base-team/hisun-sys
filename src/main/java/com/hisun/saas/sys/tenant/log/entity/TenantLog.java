/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.log.entity;

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
	private String userId;//操作人
	private Date createTime = new Date();//操作时间
	private String ip;
	private short type;//操作类型 1新增 2修改 3删除 4登录 5登出 6异常
	private String content;//日志内容

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

	@Column(name="create_time",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name="type",nullable=false,length=1)
	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
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
	
}
