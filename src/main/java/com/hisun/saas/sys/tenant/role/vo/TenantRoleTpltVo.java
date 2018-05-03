/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.vo;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class TenantRoleTpltVo {

    private String id;
    private String roleName;
    private String roleCodePrefix;
    private String roleDescription;

    private String resourceNames;
    private String resourceIds;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 1,max = 100)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^ROLE_[A-Z]{1,27}$")
    public String getRoleCodePrefix() {
        return roleCodePrefix;
    }

    public void setRoleCodePrefix(String roleCodePrefix) {
        this.roleCodePrefix = roleCodePrefix;
    }

    @Size(max = 200)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getResourceNames() {
        return resourceNames;
    }

    public void setResourceNames(String resourceNames) {
        this.resourceNames = resourceNames;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
