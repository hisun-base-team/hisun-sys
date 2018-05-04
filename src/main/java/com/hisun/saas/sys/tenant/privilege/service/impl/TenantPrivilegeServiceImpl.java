/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.privilege.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.privilege.dao.TenantPrivilegeDao;
import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.privilege.service.TenantPrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantPrivilegeServiceImpl extends BaseServiceImpl<TenantPrivilege,String>
        implements TenantPrivilegeService {

    private TenantPrivilegeDao tenantPrivilegeDao;

    @Resource
    public void setBaseDao(BaseDao<TenantPrivilege, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantPrivilegeDao = (TenantPrivilegeDao)baseDao;
    }
    public Integer getMaxSort(){
        Map<String, Object> arg=new HashMap<String, Object>();
        String hql = "select max(t.sort) as sort from sys_tenant_privilege t where t.tombstone=(:tombstone) order by  t.sort asc";
        arg.put("tombstone", "0");
        List<Map> maxSorts = this.tenantPrivilegeDao.nativeList(hql, arg);

        if(maxSorts.get(0).get("sort")==null){
            return 0;
        }else{
            Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
            return maxSort;
        }
    }

    /**
     * 排序处理（首先跟最大的排序号比较，如果大于最大排序号则不处理；
     *        如果小于，就一个个比较，当比较到大于它的就把后面大于它的全部+1；）
     * @param
     */
    public void updateSort(int oldSort,int newSort){
        String sql = "UPDATE sys_tenant_privilege t SET ";
        if(newSort > oldSort) {
            sql = sql + "t.sort=t.sort-1";
        } else {
            sql = sql + "t.sort=t.sort+1";
        }

        sql = sql + " where t.tombstone=(:tombstone) ";
        if(newSort > oldSort) {
            sql = sql + " and t.sort<=" + newSort + " and t.sort >" + oldSort;
        } else if(newSort == oldSort) {
            sql = sql + " and 1<>1";
        } else {
            sql = sql + " and t.sort<" + oldSort + " and t.sort>=" + newSort;
        }
        Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("tombstone", "0");
        this.tenantPrivilegeDao.update(sql, paramMap);
    }


}
