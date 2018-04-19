package com.hisun.saas.sys.admin.user.service;

import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.user.vo.UserVo;

/**
 * 
 *<p>类名称：UserService</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月18日 下午5:11:13
 *@创建人联系方式：qinjw@30wish.net
 *@
 *@version
 */

public interface UserService extends BaseService<User, String> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	
	/**
	 * 根据email查找用户
	 * @param email
	 * @return
	 */
	User findByEmail(String email);
	
	/**
	 * 根据用户名查找其角色
	 * @param username
	 * @return
	 */
	//String findRoles(String username);
	
	/**
	 * 根据用户名查找其权限
	 * @param username
	 * @return
	 */
	//Set<String> findPermissions(String username);
	
	/**
	 * 根据用户名查找其登录信息的封装对象
	 * @param username
	 * @return UserLoginDetails
	 */
	UserLoginDetails findUserLoginDetails(String username);

	void update(User entity, boolean changePassword);
	
	/**

	 * 确认输入的密码是否正确
	 * @param user
	 * @param inputPassword
	 * @return
	 */
	boolean credentialsPassword(User user, String inputPassword);
	
	public PagerVo<UserVo> searchUserByName(int pageSize, int pageNum, UserVo userVo)throws Exception;
	
}