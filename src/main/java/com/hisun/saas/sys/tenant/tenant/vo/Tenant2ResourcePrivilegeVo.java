/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.vo;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class Tenant2ResourcePrivilegeVo {

    private String id;
    private String tenant2ResourceId;
    private String tenantPrivilegeId;
    private String privilegeName;
    private String privilegeDescription;
    private String privilegeImpclass;
    private String selectUrl;
    private String sqlFilterExpress;
    private String hqlFilterExpress;
    private String selectedNames;
    private String selectedValues;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenant2ResourceId() {
        return tenant2ResourceId;
    }

    public void setTenant2ResourceId(String tenant2ResourceId) {
        this.tenant2ResourceId = tenant2ResourceId;
    }

    public String getTenantPrivilegeId() {
        return tenantPrivilegeId;
    }

    public void setTenantPrivilegeId(String tenantPrivilegeId) {
        this.tenantPrivilegeId = tenantPrivilegeId;
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

    public String getSelectUrl() {
        return selectUrl;
    }

    public void setSelectUrl(String selectUrl) {
        this.selectUrl = selectUrl;
    }

    public String getSqlFilterExpress() {
        return sqlFilterExpress;
    }

    public void setSqlFilterExpress(String sqlFilterExpress) {
        this.sqlFilterExpress = sqlFilterExpress;
    }

    public String getHqlFilterExpress() {
        return hqlFilterExpress;
    }

    public void setHqlFilterExpress(String hqlFilterExpress) {
        this.hqlFilterExpress = hqlFilterExpress;
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
}
