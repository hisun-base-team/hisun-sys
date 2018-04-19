/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.message.entity;

import com.hisun.saas.sys.tenant.base.entity.TenantEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name="sys_tenant_message")
public class TenantMessage extends TenantEntity implements Serializable{

	private static final long serialVersionUID = -3657541297462127538L;

	private String id;//主键
	private boolean noticeEmail = Boolean.FALSE;//是否开启邮件通知
	private boolean noticeSmart = Boolean.FALSE;//是否开启免打扰模式
	private boolean noticeSMS = Boolean.FALSE;//是否开启短信通知
	private boolean noticeExpress = Boolean.FALSE;//是否开启每月速递

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

	@Column(name="notice_email")
	public boolean isNoticeEmail() {
		return noticeEmail;
	}

	public void setNoticeEmail(boolean noticeEmail) {
		this.noticeEmail = noticeEmail;
	}

	@Column(name="notice_smart")
	public boolean isNoticeSmart() {
		return noticeSmart;
	}

	public void setNoticeSmart(boolean noticeSmart) {
		this.noticeSmart = noticeSmart;
	}

	@Column(name="notice_express")
	public boolean isNoticeExpress() {
		return noticeExpress;
	}

	public void setNoticeExpress(boolean noticeExpress) {
		this.noticeExpress = noticeExpress;
	}

	@Column(name="notice_sms")
	public boolean isNoticeSMS() {
		return noticeSMS;
	}

	public void setNoticeSMS(boolean noticeSMS) {
		this.noticeSMS = noticeSMS;
	}

}
