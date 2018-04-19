package com.hisun.saas.sys.tenant.tenant.vo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>类名称:TenantRegisterVo</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/19下午3:02
 * @创建人联系方式:init@hn-hisun.com
 */
public class TenantRegisterVo {

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-z]{1,30}$")
    private String username;

    @Size(min=2,max=30)
    private String realname;

    @Size(min=6,max=16)
    @Pattern(regexp = "^.*([\\W_a-zA-z0-9-])+.*$")
    private String password;

    @NotBlank
    private String rePwd;

    @NotBlank
    @Pattern(regexp = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$")
    private String tel;

    @Size(min=1,max=30)
    private String tenantName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getRePwd() {
        return rePwd;
    }

    public void setRePwd(String rePwd) {
        this.rePwd = rePwd;
    }
}
