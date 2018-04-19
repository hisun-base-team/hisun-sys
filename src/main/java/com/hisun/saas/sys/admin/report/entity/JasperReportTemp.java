/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.report.entity;

import com.hisun.base.entity.TombstoneEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name="sys_report_template")
public class JasperReportTemp  extends TombstoneEntity implements Serializable {


    private static final long serialVersionUID = 3452107429943352578L;

    private String id;
    private String reportTempName;
    private String reportTempPath;
    private String reportTempType;
    private String reportTempDesc;
    private String identCode;
    private String serverId;

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

    /**
     * 报表模板名称
     */
    @Column(name="report_temp_name")
    public String getReportTempName() {
        return reportTempName;
    }

    public void setReportTempName(String reportTempName) {
        this.reportTempName = reportTempName;
    }
    /**
     * 报表模板路径
     */
    @Column(name="report_temp_path")
    public String getReportTempPath() {
        return reportTempPath;
    }

    public void setReportTempPath(String reportTempPath) {
        this.reportTempPath = reportTempPath;
    }

    /**
     * 报表模板描述
     */
    @Column(name="report_temp_desc")
    public String getReportTempDesc() {
        return reportTempDesc;
    }
    public void setReportTempDesc(String reportTempDesc) {
        this.reportTempDesc = reportTempDesc;
    }

    /**
     * 报表模板类型
     */
    @Column(name="report_temp_type")
    public String getReportTempType() {
        return reportTempType;
    }

    public void setReportTempType(String reportTempType) {
        this.reportTempType = reportTempType;
    }

    @Column(name="ident_code")
    public String getIdentCode() {
        return identCode;
    }
    public void setIdentCode(String identCode) {
        this.identCode = identCode;
    }

    @Column(name="server_id")
    public String getServerId() {
        return serverId;
    }
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
