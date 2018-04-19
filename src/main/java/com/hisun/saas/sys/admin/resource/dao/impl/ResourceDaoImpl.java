package com.hisun.saas.sys.admin.resource.dao.impl;

import com.hisun.saas.sys.admin.resource.dao.ResourceDao;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.base.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource, String> implements ResourceDao {

	@Override
	public Integer getMaxSort(String pId,int type) {
		Map<String, Object> map = new HashMap<>();
		String hql = "select max(t.sort)+1 as sort from sys_resource t where t.p_id=:pId and t.type=:type";
		map.put("pId", pId);
		map.put("type",type);
		List<Map> maxSorts = this.countReturnMapBySql(hql, map);
		if(maxSorts.isEmpty()||maxSorts.get(0).get("sort")==null){
			return Integer.valueOf("1");
		}else{
			Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
			return maxSort;
		}
	}

	@Override
	public List<Resource> getByTenantUserId(String tenantUserId) {
		String sql = "select res.* from sys_tenant_user_role userRole ,sys_tenant_role role,sys_tenant_role_resource roleRes,sys_resource res"
				+ " where userRole.tenant_user_id = :userId and userRole.tenant_role_id = role.id"
				+ " and role.id = roleRes.tenant_role_id and roleRes.resource_id = res.id and res.type = :type";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", tenantUserId);
		paramMap.put("type", 1);
		return nativeList(Resource.class, sql, paramMap);
	}

}
