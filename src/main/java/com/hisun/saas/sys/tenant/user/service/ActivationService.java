/**
 * 
 */
package com.hisun.saas.sys.tenant.user.service;

import com.hisun.saas.sys.tenant.user.entity.Activation;
import com.hisun.base.service.BaseService;

/**
 * 
 *<p>类名称：ActivationService</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月18日 下午5:08:35
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */

public interface ActivationService extends BaseService<Activation, String> {

    void saveAndSendEmail(String email, String emailContent, String roleId) throws Exception;

}
