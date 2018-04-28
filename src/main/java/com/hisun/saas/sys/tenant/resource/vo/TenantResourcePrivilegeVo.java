/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.vo;

/**
 * @author liuzj {279421824@qq.com}
 */
public class TenantResourcePrivilegeVo {

    private String id;
    private String privilegeName;
    private String privilegeDescription;
    private String privilegeImpclass;//实现类
    private String selectedNames;
    private String selectedValues;
    private String selectUrl;
    private String tenantResourceId;
    private String tenantPrivilegeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeDescription() {
        return privilegeDescription;
    }

    public void setPrivilegeDescription(String privilegeDescription) {
        this.privilegeDescription = privilegeDescription;
    }

    public String getPrivilegeImpclass() {
        return privilegeImpclass;
    }

    public void setPrivilegeImpclass(String privilegeImpclass) {
        this.privilegeImpclass = privilegeImpclass;
    }

    public String getSelectedNames() {
        return selectedNames;
    }

    public void setSelectedNames(String selectedNames) {
        this.selectedNames = selectedNames;
    }

    public String getSelectedValues() {
        return selectedValues;
    }

    public void setSelectedValues(String selectedValues) {
        this.selectedValues = selectedValues;
    }

    public String getSelectUrl() {
        return selectUrl;
    }

    public void setSelectUrl(String selectUrl) {
        this.selectUrl = selectUrl;
    }

    public String getTenantResourceId() {
        return tenantResourceId;
    }

    public void setTenantResourceId(String tenantResourceId) {
        this.tenantResourceId = tenantResourceId;
    }

    public String getTenantPrivilegeId() {
        return tenantPrivilegeId;
    }

    public void setTenantPrivilegeId(String tenantPrivilegeId) {
        this.tenantPrivilegeId = tenantPrivilegeId;
    }
}
