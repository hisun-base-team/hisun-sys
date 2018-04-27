/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.entity;

import com.hisun.base.entity.TombstoneEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@MappedSuperclass
public abstract  class AbstractResource extends TombstoneEntity {

    public final static Integer ENABLE=0;
    public final static Integer DISABLE=0;

    protected String id;
    protected String pId;
    protected String resourceName;
    protected Integer resourceType = BigDecimal.ZERO.intValue();
    protected Integer status = BigDecimal.ZERO.intValue();
    protected String url;
    protected Integer sort = BigDecimal.ZERO.intValue();
    protected String permission;
    protected String description;
    protected String queryCode;//查询编码 3位一层 001-999
    protected String resourceIcon;//资源图标
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

    /**
     * 父节点
     */
    @Column(name = "p_id", nullable = false)
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    /**
     * 资源名字
     */
    @Column(name = "resource_name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * 资源类型，0 菜单 1 操作 2 系统
     */
    @Column(name = "resource_type", nullable = false)
    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 资源状态 0 可用 1 不可用
     */
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 资源路径
     */
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 资源排序
     */
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 权限字符串 参考shiro的认证方式 例如user:view 可以将权限拓展到按钮操作
     */
    @Column(name = "permission", unique = true)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "query_code", length = 27)
    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    @Column(name = "icon", length = 20)
    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

}
