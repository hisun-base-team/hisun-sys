/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service;

import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import com.hisun.saas.sys.tenant.tenant.entity.TenantDepartment;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public interface TenantDepartmentService extends BaseService<TenantDepartment,String> {

    Integer getMaxSort(String pId);
    void saveTenantDepartment(TenantDepartment tenantDepartment);
    void updateSort(TenantDepartment tenantDepartment,Integer oldSort);
    void updateTenantDepartment(TenantDepartment tenantDepartment,String oldPid,Integer oldSort)throws Exception;
}
