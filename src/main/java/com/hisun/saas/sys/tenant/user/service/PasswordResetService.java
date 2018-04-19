package com.hisun.saas.sys.tenant.user.service;/**
 * Created by liyikai on 15/11/22.
 */

import com.hisun.saas.sys.tenant.user.entity.PasswordReset;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.BaseService;

/**
 * <p>类名称:PasswordResetService</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/22下午4:31
 * @创建人联系方式:init@hn-hisun.com
 */
public interface PasswordResetService extends BaseService<PasswordReset,String> {

    /**
     * 忘记密码
     * @param email
     */
    public void addForgot(String email)  throws GenericException;

    /**
     * 重置密码
     * @param id
     * @param password
     */
    public void addReset(String id,String password);
}
