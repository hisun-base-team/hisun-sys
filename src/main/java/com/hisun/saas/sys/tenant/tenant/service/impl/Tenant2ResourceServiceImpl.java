/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.tenant.dao.Tenant2ResourceDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class Tenant2ResourceServiceImpl extends BaseServiceImpl<Tenant2Resource,String>
        implements Tenant2ResourceService {

    private Tenant2ResourceDao tenant2ResourceDao;

    @Resource
    public void setBaseDao(BaseDao<Tenant2Resource, String> baseDao) {
        this.baseDao = baseDao;
        this.tenant2ResourceDao = (Tenant2ResourceDao)baseDao;
    }


    public Tenant2Resource findTenant2ResourceByTenantAndReource(String tenantId,String resourceId){
        CommonOrderBy orderBy = new CommonOrderBy();
        CommonConditionQuery query =  new CommonConditionQuery();
        query.add(CommonRestrictions.and(" tenant.id = :tenantId ", "tenantId", tenantId));
        query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
        List<Tenant2Resource> list = this.tenant2ResourceDao.list(query,orderBy);
        if(list!=null){
            return list.get(0);
        }else{
            return null;
        }
    }
}
