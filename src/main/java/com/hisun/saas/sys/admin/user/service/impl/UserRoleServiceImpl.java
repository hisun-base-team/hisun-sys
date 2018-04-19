package com.hisun.saas.sys.admin.user.service.impl;

import com.hisun.saas.sys.admin.role.entity.Role;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.user.dao.UserRoleDao;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.role.dao.RoleDao;
import com.hisun.saas.sys.admin.user.dao.UserDao;
import com.hisun.saas.sys.admin.user.entity.UserRole;
import com.hisun.saas.sys.admin.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 *<p>类名称：UserRoleRelationServiceImpl</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月24日 上午10:23:38
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
@Service
public class UserRoleServiceImpl extends
		BaseServiceImpl<UserRole, String> implements UserRoleService {

	private UserRoleDao userRoleDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private RoleDao roleDao;
	
    @Resource
	public void setBaseDao(BaseDao<UserRole, String> userRoleRelationDao) {
		this.baseDao = userRoleRelationDao;
        this.userRoleDao = (UserRoleDao) userRoleRelationDao;
	}
    
	@Override
	public void saveOrUpdate(String userId, String[] roles) {
		User user = this.userDao.getByPK(userId);
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" UserRole.user = :user ", "user", user));
		userRoleDao.deleteBatch(query);
		if(roles!=null&&roles.length>0){
			UserRole userRole;
			for(String role : roles){
				userRole = new UserRole();
				userRole.setUser(user);
				Role role2 = this.roleDao.getByPK(role);
				userRole.setRole(role2);
				this.userRoleDao.save(userRole);
			}
		}
	}
    
}
