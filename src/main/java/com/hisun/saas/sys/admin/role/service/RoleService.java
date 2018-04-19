/**
 * 
 */
package com.hisun.saas.sys.admin.role.service;

import com.hisun.saas.sys.admin.role.entity.Role;
import com.hisun.base.service.BaseService;

import java.util.List;

/**
 * 
 *<p>类名称：RoleService</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月18日 下午3:50:57
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
public interface RoleService extends BaseService<Role, String> {


	public void deleteRoleResource(String roleId);
	
	public void saveRoleResource(String roleId,List<String> resource);
	
	public void saveRoleResourceWithoutDelete(String roleId,final List<String> resource);
	
	public void deleteRoleUser(String roleId);
	
	public void saveRoleMember(String roleId,String []userIds);
	
}
