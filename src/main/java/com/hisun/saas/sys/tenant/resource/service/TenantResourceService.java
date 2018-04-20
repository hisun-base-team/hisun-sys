/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.service;

import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public interface TenantResourceService extends BaseService<TenantResource,String>{


    public void saveResource(TenantResource resource) throws Exception;
    public void updateResources(TenantResource resource, String oldPid) throws Exception;
    public Integer getMaxSort(String pId);
    public void updateSortAndQueryCode(TenantResource resource,Integer oldSort)throws Exception;
    public void refreshQueryCodeToTmp(String oldQueryCode)throws Exception;
    public void refreshQueryCodeToFormal(String oldQueryCode,String newQueryCode)throws Exception;



}
