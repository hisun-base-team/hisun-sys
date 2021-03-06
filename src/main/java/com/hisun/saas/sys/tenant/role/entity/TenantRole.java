/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.entity;

import com.hisun.saas.sys.entity.AbstractRole;
import com.hisun.saas.sys.tenant.Constants;
import com.hisun.saas.sys.tenant.base.entity.TenantEntityInterface;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */

@Entity
@Table(name = "sys_tenant_role")
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantFilterParam", type = "string"))
@Filters({
        @Filter(name = "tenantFilter", condition = " tenant_id=:tenantFilterParam ")
})
public class TenantRole extends AbstractRole implements TenantEntityInterface,Serializable {

    private static final long serialVersionUID = 2994446571781933243L;

    private int isDefault = Constants.NORMAL_ROLE;
    private Tenant tenant;
    private List<TenantUserRole> tenantUserRoles;
    private List<TenantRoleResource> tenantRoleResources;
    private TenantRoleTplt tenantRoleTplt;

    @Column(name = "is_default")
    public int getIsDefault() { return isDefault;}
    public void setIsDefault(int isDefault) { this.isDefault = isDefault;}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL})
    public List<TenantUserRole> getTenantUserRoles() {
        return tenantUserRoles;
    }
    public void setTenantUserRoles(List<TenantUserRole> tenantUserRoles) {
        this.tenantUserRoles = tenantUserRoles;
    }

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL})
    public List<TenantRoleResource> getTenantRoleResources() {
        return tenantRoleResources;
    }
    public void setTenantRoleResources(List<TenantRoleResource> tenantRoleResources) {
        this.tenantRoleResources = tenantRoleResources;
    }

    @ManyToOne(targetEntity = TenantRoleTplt.class,fetch = FetchType.LAZY)
    @JoinColumn(name="role_tplt_id")
    public TenantRoleTplt getTenantRoleTplt() { return tenantRoleTplt;}

    public void setTenantRoleTplt(TenantRoleTplt tenantRoleTplt) {
        this.tenantRoleTplt = tenantRoleTplt;
    }

    public void addTenantUserRole(TenantUserRole tenantUserRole){
        if(tenantUserRoles!=null){
            tenantUserRoles = new ArrayList<>();
        }
        tenantUserRole.setRole(this);
        tenantUserRoles.add(tenantUserRole);
    }
}
