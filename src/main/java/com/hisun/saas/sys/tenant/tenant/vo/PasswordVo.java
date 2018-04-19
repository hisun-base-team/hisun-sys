package com.hisun.saas.sys.tenant.tenant.vo;

/**
 * <p>类名称:PasswordVo</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/22下午2:54
 * @创建人联系方式:init@hn-hisun.com
 */
public class PasswordVo {

    private String password;

    private String salt;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
