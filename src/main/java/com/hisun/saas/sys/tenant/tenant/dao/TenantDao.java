/**
 * 
 */
package com.hisun.saas.sys.tenant.tenant.dao;

import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.base.dao.BaseDao;

import java.util.List;

/**
 * 
 *<p>类名称：OrganizationDao</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月27日 上午9:29:26
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
public interface TenantDao extends BaseDao<Tenant, String> {

	public List<Tenant> selectTenantSp(String tenantId, int pageNum, int pageSize);
	
	public Integer getMaxSort(String pId);
}
