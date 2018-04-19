/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.communication.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_sms_config")
public class SMSConfig implements Serializable {

    private static final long serialVersionUID = 686169949853994911L;


    private String id;
    private String smsName;//名字
    private String smsServer;// 服务器地址
    private String apikey;
    private String version;// version
    private Boolean status = Boolean.FALSE;//是否启用
    private String send;
    private String tplSend;
    private String getTpl;
    private String addTpl;
    private String updateTpl;
    private String delTpl;

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


    @Column(name = "sms_server", nullable = false, length = 50)
    public String getSmsServer() {
        return smsServer;
    }

    public void setSmsServer(String smsServer) {
        this.smsServer = smsServer;
    }


    @Column(name = "version", nullable = false, length = 50)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    @Column(name = "sms_name", nullable = false)
    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }


    @Column(name = "status", nullable = false)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @Column(name = "apikey", nullable = false, length = 50)
    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }


    @Column(name = "send_uri", length = 50)
    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }


    @Column(name = "tpl_send_uri", length = 50)
    public String getTplSend() {
        return tplSend;
    }

    public void setTplSend(String tplSend) {
        this.tplSend = tplSend;
    }


    @Column(name = "get_tpl_uri", length = 50)
    public String getGetTpl() {
        return getTpl;
    }

    public void setGetTpl(String getTpl) {
        this.getTpl = getTpl;
    }


    @Column(name = "add_tpl_uri", length = 50)
    public String getAddTpl() {
        return addTpl;
    }

    public void setAddTpl(String addTpl) {
        this.addTpl = addTpl;
    }


    @Column(name = "update_tpl_uri", length = 50)
    public String getUpdateTpl() {
        return updateTpl;
    }

    public void setUpdateTpl(String updateTpl) {
        this.updateTpl = updateTpl;
    }


    @Column(name = "del_tpl_uri", length = 50)
    public String getDelTpl() {
        return delTpl;
    }

    public void setDelTpl(String delTpl) {
        this.delTpl = delTpl;
    }
}