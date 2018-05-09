package com.hisun.saas.sys.tenant.role.vo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TenantRoleVo {

    private String id;

    @Size(min=1,max=64)
    private String roleName;

    @Pattern(regexp = "^ROLE_[A-Z]{1,27}$")
    private String roleCode;//角色字符串

    @Size(max=255)
    private String description;

    @Range(min = 0L,max=99L)
    private int sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
