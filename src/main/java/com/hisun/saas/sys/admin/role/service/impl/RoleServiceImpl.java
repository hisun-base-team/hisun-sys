/**
 * 
 */
package com.hisun.saas.sys.admin.role.service.impl;

import com.hisun.saas.sys.admin.role.entity.Role;
import com.hisun.saas.sys.admin.role.service.RoleService;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.role.dao.RoleDao;
import com.hisun.saas.sys.admin.user.dao.UserRoleDao;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.user.entity.UserRole;
import com.hisun.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends
		BaseServiceImpl<Role, String> implements RoleService {

	private RoleDao roleDao;
	
	@javax.annotation.Resource
	private UserRoleDao userRoleDao;
	
    @Resource
	public void setBaseDao(BaseDao<Role, String> roleDao) {
		this.baseDao = roleDao;
        this.roleDao = (RoleDao) roleDao;
	}

    
	@Override
	public void deleteRoleResource(final String roleId) {
		StringBuilder sb = new StringBuilder(" DELETE FROM SYS_ROLE_RESOURCE WHERE role_id=?");
		List<Object> list = new ArrayList<Object>();
		list.add(roleId);
		roleDao.executeNativeBulk(sb.toString(),list);
	}
	@Override
	public void saveRoleResource(final String roleId,final List<String> resource) {
		this.deleteRoleResource(roleId);
		this.saveRoleResourceWithoutDelete(roleId, resource);
	}
	
	@Override
	public void saveRoleResourceWithoutDelete(final String roleId,final List<String> resource) {
		final StringBuilder sb = new StringBuilder(" insert into SYS_ROLE_RESOURCE(id,role_id,resource_id) values(?,?,?)");
		this.roleDao.getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = connection.prepareStatement(sb.toString());
					for (int i = 1; i < resource.size(); i++) {
						if (StringUtils.isNotBlank(resource.get(i))) {
							stmt.setString(1,UUIDUtil.getUUID());
							stmt.setString(2, roleId);
							stmt.setString(3, resource.get(i));
							stmt.addBatch();
						}
					}
					stmt.executeBatch();
				}catch (SQLException e){
					throw e;
				}finally {
					if(stmt!=null){
						stmt.close();
					}
				}
			}
		});
	}
	
	@Override
	public void deleteByPK(final String pk) {
		final StringBuilder sb = new StringBuilder(" DELETE FROM SYS_ROLE_RESOURCE WHERE role_id=?");
		this.roleDao.getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = connection.prepareStatement(sb.toString());
					stmt.setString(1, pk);
					stmt.executeUpdate();
					connection.commit();
				}catch (SQLException e){
					throw e;
				}finally {
					if(stmt!=null){
						stmt.close();
					}
				}
			}
		});
		super.deleteByPK(pk);
	}
	@Override
	public void deleteRoleUser(final String roleId) {
		final StringBuilder sb = new StringBuilder(" DELETE FROM SYS_USER_ROLE WHERE role_id=?");
		this.roleDao.getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = connection.prepareStatement(sb.toString());
					stmt.setString(1, roleId);
					stmt.executeUpdate();
					connection.commit();
				}catch (SQLException e){
					throw e;
				}finally {
					if(stmt!=null){
						stmt.close();
					}
				}
			}
		});
		this.roleDao.deleteByPK(roleId);
	}


	@Override
	public void saveRoleMember(String roleId, String []userIds) {

		String hql = "select DISTINCT(userRole) from UserRole as userRole "
				+ "join userRole.role as role "
				+ " where role.id = '" + roleId + "'";
		List<UserRole> userRoles = this.userRoleDao.list(hql, null, null);
		
		for(UserRole userRole : userRoles){
			this.userRoleDao.delete(userRole);
		}
		if(userIds!=null){
			Role role = new Role();
			role.setId(roleId);
			User user ;
			UserRole userRole;
			for(String id : userIds){
				user = new User();
				user.setId(id);
				userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUser(user);
				this.userRoleDao.save(userRole);
			}
		}

	}
}
