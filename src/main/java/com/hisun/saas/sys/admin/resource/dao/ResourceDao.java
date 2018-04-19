/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.resource.dao;

import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.base.dao.BaseDao;

import java.util.List;
/**
 * @author Rocky {rockwithyou@126.com}
 */
public interface ResourceDao extends BaseDao<Resource, String> {

	/**
	 * 根据父节点id和资源类型获取子节点最大的排序
	 *
	 * @param pId
	 * @param type
	 * @return
	 */
	Integer getMaxSort(String pId, int type);

	/**
	 * 根据租户用户ID获取资源
	 * @param tenantUserId
	 * @return
	 */
	List<Resource> getByTenantUserId(String tenantUserId);
}
