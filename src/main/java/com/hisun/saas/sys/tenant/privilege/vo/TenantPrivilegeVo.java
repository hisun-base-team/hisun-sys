/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.privilege.vo;

import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.vo.TombstoneVo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author liuzj {279421824@qq.com}
 */
public class TenantPrivilegeVo extends TombstoneVo {

    private String id;
    private String name;
    private String code;//唯一
    private String description;
    private String impclass;//实现类
    private Integer type = 1;//1-行过滤,2-列过滤
    private String selectUrl;
    private Integer displayType=1;//1-树形selectOption,2-多选selecteOption
    private Integer sort;
    private String isChecked;//在资源权限资源配置时用记录是否已选择该资源 true为选中

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImpclass() {
        return impclass;
    }

    public void setImpclass(String impclass) {
        this.impclass = impclass;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSelectUrl() { return selectUrl;}

    public void setSelectUrl(String selectUrl) { this.selectUrl = selectUrl;}

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
