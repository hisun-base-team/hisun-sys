/**
 * 
 */
package com.hisun.saas.sys.admin.user.service;

import com.hisun.saas.sys.admin.user.entity.UserRole;
import com.hisun.base.service.BaseService;

/**
 * 
 *<p>类名称：UserRoleRelationService</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月24日 上午10:23:16
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
public interface UserRoleService extends BaseService<UserRole, String> {

	public void saveOrUpdate(String userId, String[] roles);
}
