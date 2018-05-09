package com.hisun.saas.sys.tenant.role.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TenantRoleServiceImpl extends BaseServiceImpl<TenantRole,String> implements TenantRoleService {

    private TenantRoleDao tenantRoleDao;

    @Resource
    @Override
    public void setBaseDao(BaseDao<TenantRole, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantRoleDao = (TenantRoleDao)baseDao;
    }


    public Integer getMaxSort() {
        Map<String, Object> map = new HashMap<String, Object>();
        String hql = "select max(t.sort)+1 as sort from TenantRole t ";
        List<Map> maxSorts = this.tenantRoleDao.list(hql, map);
        if (maxSorts.get(0).get("sort") == null) {
            return 1;
        } else {
            Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
            return maxSort;
        }
    }


    private void updateSort(TenantRole tenantRole, Integer oldSort) {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        CommonConditionQuery query = new CommonConditionQuery();
        Integer newSort = tenantRole.getSort();
        String sql = "update sys_tenant_role t set ";
        if (newSort > oldSort) {
            sql += "t.sort=t.sort-1";
        } else {
            sql += "t.sort=t.sort+1";
        }
        sql += " where t.tenant_id='" + userLoginDetails.getTenant().getId();

        if (newSort > oldSort) {
            sql += " and t.sort<=" + newSort + " and t.sort >" + oldSort;
        } else {
            if (newSort == oldSort) {
                sql += " and t.sort = -100";
            } else {
                sql += " and t.sort<" + oldSort + " and t.sort>=" + newSort;
            }
        }
        this.tenantRoleDao.executeNativeBulk(sql, query);
    }

    public void updateTenantRole(TenantRole tenantRole, Integer oldSort) {
        this.updateSort(tenantRole, oldSort);
        this.tenantRoleDao.update(tenantRole);
    }


    public boolean existRoleCode(String roleCode){
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" roleCode = :roleCode","roleCode",roleCode));
        Long count = this.count(query);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

}
