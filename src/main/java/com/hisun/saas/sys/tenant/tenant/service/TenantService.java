package com.hisun.saas.sys.tenant.tenant.service;

import com.hisun.saas.sys.tenant.tenant.vo.TenantVo;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;

/**
 * 
 *<p>类名称：OrganizationService</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月27日 上午9:31:13
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */

public interface TenantService extends BaseService<Tenant, String> {

	/**
	 * 根据名字模糊查询
	 */
	public PagerVo<Tenant> listPager(String name, Integer tombstone, String start, String end, int pageNum, int pageSize);
	
	public Tenant add(TenantVo vo) throws GenericException;

	public Tenant update(TenantVo vo);

	public void deleteById(String id);

	public int countUserByTenantId(String id);

	/**
	 * 激活
	 * @param id
	 */
	public void updateActivate(String id);

	/**
	 * 更新自己租户信息
	 */
	public void updateOwn(TenantVo vo);

	Tenant selectTenantByToken(String token);

	public Tenant getByName(String name);
}

