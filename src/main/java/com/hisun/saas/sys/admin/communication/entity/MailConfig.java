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
@Entity
@Table(name = "sys_mail_config")
public class MailConfig implements Serializable {

    private static final long serialVersionUID = -8054009271924766931L;

    private String id;
    private String name;
    private String account;// 发件人账户
    private String password;// 密码
    private String email;// 发件人邮箱
    private String emailServer;// SMTP
    private Boolean status;//是否启用
    private String mailServer;//邮件服务器api URL
    private String version;// version
    private String apiUser;
    private String apiKey;
    private String send;
    private String sendTemplate;
    private String addTemplate;
    private String deleteTemplate;
    private String updateTemplate;
    private String getTemplate;
    private boolean type = Boolean.FALSE;//默认触发类型

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

    @Column(name = "account", nullable = false, length = 50)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "email_server", nullable = false, length = 50)
    public String getEmailServer() {
        return emailServer;
    }

    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }


    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "status", nullable = false)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @Column(name = "mail_server")
    public String getMailServer() {
        return mailServer;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    @Column(name = "api_user")
    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }


    @Column(name = "api_key")
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


    @Column(name = "send_uri")
    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }


    @Column(name = "send_template_uri")
    public String getSendTemplate() {
        return sendTemplate;
    }

    public void setSendTemplate(String sendTemplate) {
        this.sendTemplate = sendTemplate;
    }


    @Column(name = "add_template_uri")
    public String getAddTemplate() {
        return addTemplate;
    }

    public void setAddTemplate(String addTemplate) {
        this.addTemplate = addTemplate;
    }


    @Column(name = "delete_template_uri")
    public String getDeleteTemplate() {
        return deleteTemplate;
    }

    public void setDeleteTemplate(String deleteTemplate) {
        this.deleteTemplate = deleteTemplate;
    }


    @Column(name = "update_template_uri")
    public String getUpdateTemplate() {
        return updateTemplate;
    }

    public void setUpdateTemplate(String updateTemplate) {
        this.updateTemplate = updateTemplate;
    }


    @Column(name = "get_template_uri")
    public String getGetTemplate() {
        return getTemplate;
    }

    public void setGetTemplate(String getTemplate) {
        this.getTemplate = getTemplate;
    }


    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isType() {
        return type;
    }

    @Column(name = "type", nullable = false)
    public boolean getType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }
}
