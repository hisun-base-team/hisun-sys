/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth;

import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.entity.AbstractRole;
import com.hisun.saas.sys.entity.AbstractUser;
import com.hisun.saas.sys.admin.Constants;
import com.hisun.saas.sys.admin.resource.vo.ResourceMenuItem;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.util.BeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class UserLoginDetails extends AbstractUserLoginDetails {

    private AbstractUser user;
    private List<AbstractRole> roles;
    private List<AbstractResource> resources;
   // private Map<AbstractResource,List<>>

    private String tenantId;
    private String tenantName;
    private Tenant tenant;
    private int userType;

    public List<AbstractResource> getResources() {
        return resources;
    }
    public void setResources(List<AbstractResource> resources) {
        this.resources = resources;
    }


    public List<AbstractRole> getRoles() {
        return roles;
    }
    public void setRoles(List<AbstractRole> roles) {
        this.roles = roles;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public AbstractUser getUser() {
        return user;
    }
    public void setUser(AbstractUser user) {
        this.user = user;
    }

    public int getUserType() {
        return userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }


}