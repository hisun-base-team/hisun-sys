package com.hisun.saas.sys.tenant.user.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserRoleDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.saas.sys.tenant.user.service.TenantUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TenantUserRoleServiceImpl extends BaseServiceImpl<TenantUserRole,String> implements TenantUserRoleService {

    private TenantUserRoleDao tenantUserRoleDao;

    @Resource
    private TenantUserDao tenantUserDao;

    @Resource
    private TenantRoleDao tenantRoleDao;

    @Resource
    public void setBaseDao(BaseDao<TenantUserRole, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantUserRoleDao = (TenantUserRoleDao)baseDao;
    }

    @Override
    public List<TenantUserRole> getUserRoleByUserId(String userId) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" user.id = :userId", "userId", userId));
        List<TenantUserRole> roleList = tenantUserRoleDao.list(query, null);
        return roleList;
    }

    @Override
    public void saveOrUpdate(String userId, String[] roleIds) {
        TenantUser user = this.tenantUserDao.getByPK(userId);
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" user = :user ", "user", user));
        tenantUserRoleDao.deleteBatch(query);
        if (roleIds != null && roleIds.length > 0) {
            TenantUserRole userRole;
            for (String roleId : roleIds) {
                userRole = new TenantUserRole();
                userRole.setUser(user);
                TenantRole role = this.tenantRoleDao.getByPK(roleId);
                userRole.setRole(role);
                this.tenantUserRoleDao.save(userRole);
            }
        }
    }
}
