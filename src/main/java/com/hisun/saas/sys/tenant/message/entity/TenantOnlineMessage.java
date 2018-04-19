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
@Table(name = "sys_tenant_online_message")
public class TenantOnlineMessage extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 2084249843729470802L;

    private String id;//主键
    private short type;//消息类型
    private short status = Short.valueOf("0");//消息状态  默认0未读 ,1待处理,2已读
    private String content;//消息内容
    private String title;//消息标题

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


    @Column(name = "type")
    public short getType() {
        return type;
    }
    public void setType(short type) {
        this.type = type;
    }


    @Column(name = "status")
    public short getStatus() {
        return status;
    }
    public void setStatus(short status) {
        this.status = status;
    }


    @Column(name = "content")
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }


    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
