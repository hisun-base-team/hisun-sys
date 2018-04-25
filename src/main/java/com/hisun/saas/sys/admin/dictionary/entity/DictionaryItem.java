/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.entity;

import com.hisun.base.entity.TombstoneEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_dict_item")
public class DictionaryItem  extends TombstoneEntity implements Serializable {

    private static final long serialVersionUID = -6174662855194278964L;

    private String id;//逻辑主键
    private DictionaryItem parentItem;//上级字典项
    private String name;//字典项名称
    private String code;//字典项值
    private String remark;//备注说明
    private Integer sort = 1;//排序
    private String queryCode;//查询code(树形结构查询定位专用,最多9层)

    private DictionaryType dictionaryType;
    private List<DictionaryItem> childrenItems;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dict_type_id")
    public DictionaryType getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    public DictionaryItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(DictionaryItem parentItem) {
        this.parentItem = parentItem;
    }

    @Column(name = "name",nullable = false,length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code",nullable = false,length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @OneToMany(mappedBy = "parentItem", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<DictionaryItem> getChildrenItems() {
        return childrenItems;
    }

    public void setChildrenItems(List<DictionaryItem> childrenItems) {
        this.childrenItems = childrenItems;
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

    @Column(name = "query_code", unique = true, length = 27, nullable = false)
    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }
}
