/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.user.entity;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name="sys_serial")
public class Serial implements Serializable{


	private static final long serialVersionUID = 3639141328950655926L;

	private String id;//主键
	private String email;
	private boolean status = Boolean.FALSE;//是否过期
	private Date satrtDate = new Date();//记录发送邮件的日期
	private Date endDate = new DateTime().plusDays(1).toDate();//有效期为一天
	private String key;//序列号

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

	@Column(name="email",length=256,nullable=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="status")
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name="satrt_date",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSatrtDate() {
		return satrtDate;
	}
	public void setSatrtDate(Date satrtDate) {
		this.satrtDate = satrtDate;
	}

	@Column(name="end_date",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="serial_key",length=160,nullable=false)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
