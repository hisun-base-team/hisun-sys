/**
 * 
 */
package com.hisun.saas.sys.tenant.tenant.dao.impl;

import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.base.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *<p>类名称：OrganizationDaoImpl</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月27日 上午9:30:06
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
@Repository
public class TenantDaoImpl extends BaseDaoImpl<Tenant, String>
		implements TenantDao {

	@Override
	public List<Tenant> selectTenantSp(String tenantId, int pageNum,
									   int pageSize) {
		/*StringBuilder sb = new StringBuilder(
				"SELECT d.* FROM SYS_TENANT d "
						+ "WHERE  EXISTS (SELECT DISTINCT(c.tenant_id) "
						+ " FROM SYS_TENANT a,SYS_TENANT_CUSTOMER b,SYS_CUSTOMER c WHERE a.s_id = b.tenant_id AND b.customer_id = c.s_id "
						+ " AND a.s_id=:tenantId AND c.tenant_id = d.s_id)");*/
		StringBuilder sb = new StringBuilder("SELECT a.* from sys_tenant a WHERE EXISTS ( SELECT b.* FROM sys_customer b WHERE a.s_id = b.tenant_sp_id AND b.tenant_id=:tenantId )");
		List<?> tenants = getSession().createSQLQuery(sb.toString()).addEntity( Tenant.class).setString("tenantId", tenantId).setFirstResult(pageSize * (pageNum - 1)).setMaxResults(pageSize).list();
		return (List<Tenant>) tenants;
	}

	
	@Override
	public Integer getMaxSort(String pId) {
		Map<String, Object> map=new HashMap<String, Object>();
		String hql = "select max(t.sort)+1 as sort from SYS_TENANT t where t.p_id=:pId";
		map.put("pId", pId);
		List<Map> maxSorts = this.countReturnMapBySql(hql, map);
		if(maxSorts.isEmpty()||maxSorts.get(0).get("sort")==null){
			return 1;
		}else{
			Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
			return maxSort;
		}
	}
}
