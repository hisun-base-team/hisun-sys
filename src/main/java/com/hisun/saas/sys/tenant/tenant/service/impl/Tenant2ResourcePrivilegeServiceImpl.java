/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.resource.dao.TenantResourcePrivilegeDao;
import com.hisun.saas.sys.tenant.resource.entity.TenantResourcePrivilege;
import com.hisun.saas.sys.tenant.resource.service.TenantResourcePrivilegeService;
import com.hisun.saas.sys.tenant.tenant.dao.Tenant2ResourcePrivilegeDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2ResourcePrivilege;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourcePrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class Tenant2ResourcePrivilegeServiceImpl extends BaseServiceImpl<Tenant2ResourcePrivilege,String>
        implements Tenant2ResourcePrivilegeService {

    private Tenant2ResourcePrivilegeDao tenant2ResourcePrivilegeDao;

    @Resource
    public void setBaseDao(BaseDao<Tenant2ResourcePrivilege, String> baseDao) {
        this.baseDao = baseDao;
        this.tenant2ResourcePrivilegeDao = (Tenant2ResourcePrivilegeDao)baseDao;
    }
}
