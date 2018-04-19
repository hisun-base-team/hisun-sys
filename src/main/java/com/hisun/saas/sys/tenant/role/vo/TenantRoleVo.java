package com.hisun.saas.sys.tenant.role.vo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>类名称:TenantRoleVo</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/18下午2:37
 * @创建人联系方式:init@hn-hisun.com
 */
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
