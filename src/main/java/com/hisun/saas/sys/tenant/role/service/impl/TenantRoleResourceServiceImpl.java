/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.service.impl;

import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResource;
import com.hisun.saas.sys.tenant.role.service.TenantRoleResourceService;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantRoleResourceServiceImpl extends BaseServiceImpl<TenantRoleResource,String> implements TenantRoleResourceService {

    private TenantRoleResourceDao tenantRoleResourceDao;

    @Resource
    @Override
    public void setBaseDao(BaseDao<TenantRoleResource, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantRoleResourceDao = (TenantRoleResourceDao)baseDao;
    }

    public TenantRoleResource findTenantRoleResourceByRoleAndReource(String roleId,String resourceId){
        CommonOrderBy orderBy = new CommonOrderBy();
        CommonConditionQuery query =  new CommonConditionQuery();
        query.add(CommonRestrictions.and(" role.id = :roleId ", "roleId", roleId));
        query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
        List<TenantRoleResource> list = this.tenantRoleResourceDao.list(query,orderBy);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
