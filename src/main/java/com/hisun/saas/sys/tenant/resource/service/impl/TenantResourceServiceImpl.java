/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.resource.dao.TenantResourceDao;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResource;
import com.hisun.saas.sys.tenant.role.service.TenantRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantResourceServiceImpl extends BaseServiceImpl<TenantResource,String>
        implements TenantResourceService {

    private TenantResourceDao tenantResourceDao;

    @Resource
    public void setBaseDao(BaseDao<TenantResource, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantResourceDao = (TenantResourceDao)baseDao;
    }
}
