/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.dao.impl;

import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.saas.sys.tenant.resource.dao.TenantResourceDao;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Repository
public class TenantResourceDaoImpl extends BaseDaoImpl<TenantResource,String>
       implements TenantResourceDao {

    @Override
    public Integer getMaxSort(String pId) {
        Map<String, Object> map = new HashMap<>();
        String hql = "select max(t.sort)+1 as sort from sys_tenant_resource t where t.p_id=:pId";
        map.put("pId", pId);
        List<Map> maxSorts = this.countReturnMapBySql(hql, map);
        if(maxSorts.isEmpty()||maxSorts.get(0).get("sort")==null){
            return Integer.valueOf("1");
        }else{
            Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
            return maxSort;
        }
    }
}
