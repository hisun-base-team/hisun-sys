/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.message.entity;

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
@Table(name = "sys_tenant_notice")
public class TenantNotice extends TenantEntity implements Serializable {

    private static final long serialVersionUID = -7479432551501269338L;
    private String id;
    private String noticeTitle;//通知标题
    private String noticeContent;//通知内容
    private short noticeLevel;//通知等级
    private short pushWay;//推送方式  0'短信',1'邮件',2'在线'
    private boolean status; //公告状态 true显示,false不显示
    private Date startDate = new Date();

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false, unique = true, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "notice_title")
    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @Column(name = "notice_content")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }


    @Column(name = "notice_level")
    public short getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(short noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    @Column(name = "push_way")
    public short getPushWay() {
        return pushWay;
    }

    public void setPushWay(short pushWay) {
        this.pushWay = pushWay;
    }

    @Column(name = "status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
