/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dzda.entity;

import com.hisun.base.entity.TombstoneEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liuzj {279421824@qq.com}
 */
@Entity
@Table(name = "e_catalog_type_info")
public class ECatalogTypeInfo  extends TombstoneEntity implements Serializable {
    private String id;
    private String catalogCode;
    private String catalogValue;
    private Integer sort;

    private ECatalogTypeInfo parent;

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

    @Basic
    @Column(name = "catalog_code")
    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    @Basic
    @Column(name = "catalog_value")
    public String getCatalogValue() {
        return catalogValue;
    }

    public void setCatalogValue(String catalogValue) {
        this.catalogValue = catalogValue;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public ECatalogTypeInfo getParent() {
        return parent;
    }

    public void setParent(ECatalogTypeInfo parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECatalogTypeInfo that = (ECatalogTypeInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (catalogCode != null ? !catalogCode.equals(that.catalogCode) : that.catalogCode != null) return false;
        if (catalogValue != null ? !catalogValue.equals(that.catalogValue) : that.catalogValue != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (catalogCode != null ? catalogCode.hashCode() : 0);
        result = 31 * result + (catalogValue != null ? catalogValue.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }
}
