/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.resource.dao.TenantResourcePrivilegeDao;
import com.hisun.saas.sys.tenant.resource.entity.TenantResourcePrivilege;
import com.hisun.saas.sys.tenant.resource.service.TenantResourcePrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantResourcePrivilegeServiceImpl extends BaseServiceImpl<TenantResourcePrivilege,String>
        implements TenantResourcePrivilegeService {

    private TenantResourcePrivilegeDao tenantResourcePrivilegeDao;

    @Resource
    public void setBaseDao(BaseDao<TenantResourcePrivilege, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantResourcePrivilegeDao = (TenantResourcePrivilegeDao)baseDao;
    }

    @Override
    public void deleteTenantResourcePrivilege(final String id) {
        StringBuilder sb = new StringBuilder(" DELETE FROM sys_tenant_resource_privilege WHERE id=?");
        List<Object> list = new ArrayList<Object>();
        list.add(id);
        tenantResourcePrivilegeDao.executeNativeBulk(sb.toString(),list);
    }
}
