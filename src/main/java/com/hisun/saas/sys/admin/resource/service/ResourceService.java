package com.hisun.saas.sys.admin.resource.service;

import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.base.service.BaseService;

/**
 * <p>类名称：PlatfResourceService</p>
 * <p>类描述: </p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：Jason
 * @创建时间：2015-3-18 15 :43:37
 * @创建人联系方式：jason4j@qq.com
 */
public interface ResourceService extends BaseService<Resource, String> {


	/**
	 * Save resource.
	 *
	 * @param resource the resource
	 * @param type     the type
	 * @throws Exception the exception
	 */
	public void saveResource(Resource resource, int type) throws Exception;


	/**
	 * Update resources.
	 *
	 * @param resource the resource
	 * @param oldPid   the old pid
	 * @param oldSort  the old sort
	 * @param type     the type
	 * @throws Exception the exception
	 */
	public void updateResources(Resource resource, String oldPid, Integer oldSort, int type) throws Exception;


	public void deleteByPK(final String id);

	/**
	 * Gets max sort.
	 *
	 * @param pId  the p id
	 * @param type the type
	 * @return the max sort
	 */
	public Integer getMaxSort(String pId, int type);

	/**
	 * Update sort and query code.
	 *
	 * @param resource the resource
	 * @param oldSort  the old sort
	 * @param type     the type
	 * @throws Exception the exception
	 */
	public void updateSortAndQueryCode(Resource resource,Integer oldSort,int type)throws Exception;

	/**
	 * Refresh query code for up.
	 *
	 * @param startCode the start code
	 * @param endCode   the end code
	 * @param type      the type
	 * @throws Exception the exception
	 */
	//public void refreshQueryCodeForUp(String startCode, String endCode,int type)throws Exception;

	/**
	 * Refresh query code for down.
	 *
	 * @param startCode the start code
	 * @param endCode   the end code
	 * @param type      the type
	 * @throws Exception the exception
	 */
	//public void refreshQueryCodeForDown(String startCode, String endCode,int type) throws Exception;


	/**
	 * Refresh query code to tmp.
	 *
	 * @param oldQueryCode the old query code
	 * @param type         the type
	 * @throws Exception the exception
	 */
	public void refreshQueryCodeToTmp(String oldQueryCode,int type)throws Exception;

	/**
	 * Refresh query code to formal.
	 *
	 * @param oldQueryCode the old query code
	 * @param newQueryCode the new query code
	 * @param type         the type
	 * @throws Exception the exception
	 */
	public void refreshQueryCodeToFormal(String oldQueryCode,String newQueryCode,int type)throws Exception;

}
