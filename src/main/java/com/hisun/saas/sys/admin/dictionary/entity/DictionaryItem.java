/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_dict_item")
public class DictionaryItem implements Serializable {

    private static final long serialVersionUID = -6174662855194278964L;

    private String id;//逻辑主键
    private String pId;//父id
    private DictionaryType dictionaryType;
    private String item;//字典项
    private String value;//字典项值
    private String remark;//备注说明
    private Integer sort = BigDecimal.ZERO.intValue();//排序
    private String queryCode;
    private Date createTime = new Date();//创建时间
    private String createUser;//创建人

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

    @ManyToOne
    @JoinColumn(name = "dict_type_id")
    public DictionaryType getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    @Column(name = "item", length = 50)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    @Column(name = "value", length = 50)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Column(name = "user_id", length = 32)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    @Column(name = "p_id", length = 32)
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Column(name = "query_code", unique = true, length = 27, nullable = false)
    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }
}
