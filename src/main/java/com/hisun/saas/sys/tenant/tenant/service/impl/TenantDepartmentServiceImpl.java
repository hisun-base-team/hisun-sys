/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDepartmentDao;
import com.hisun.saas.sys.tenant.tenant.entity.TenantDepartment;
import com.hisun.saas.sys.tenant.tenant.service.TenantDepartmentService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantDepartmentServiceImpl extends BaseServiceImpl<TenantDepartment,String>
        implements TenantDepartmentService {

    private TenantDepartmentDao tenantDepartmentDao;


    @Resource
    public void setBaseDao(BaseDao<TenantDepartment, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantDepartmentDao = (TenantDepartmentDao)baseDao;
    }



    public Integer getMaxSort(String pId) {
        Map<String, Object> map=new HashMap<String, Object>();
        String hql = "select max(t.sort)+1 as sort from TenantDepartment t ";
        if(pId!=null && !pId.equals("")) {
            hql = hql+"where t.parent.id =:pId";
            map.put("pId", pId);
        }else{
            hql = hql+"where t.parent is null";
        }
        List<Map> maxSorts = this.tenantDepartmentDao.list(hql, map);
        if(maxSorts.get(0).get("sort")==null){
            return 1;
        }else{
            Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
            return maxSort;
        }
    }



    public void saveTenantDepartment(TenantDepartment tenantDepartment) {
        String pId = "";
        if(tenantDepartment.getParent()!=null){
            pId = tenantDepartment.getParent().getId();
        }
        Integer oldSort = this.getMaxSort(pId);
        Integer newSort = tenantDepartment.getSort();
        int retval = newSort.compareTo(oldSort);
        if(retval>0){
            newSort = oldSort;
        }
        tenantDepartment.setSort(newSort);
        this.updateSort(tenantDepartment, oldSort);
        this.save(tenantDepartment);

    }

    private void updateSort(TenantDepartment tenantDepartment, Integer oldSort)  {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        CommonConditionQuery query = new CommonConditionQuery();
        Integer newSort = tenantDepartment.getSort();
        String pId = "";
        if(tenantDepartment.getParent()!=null){
            pId = tenantDepartment.getParent().getId();
        }
        String sql="update sys_tenant_department t set ";
        if(newSort>oldSort){
            sql+="t.sort=t.sort-1";
        }else{
            sql+="t.sort=t.sort+1";
        }
        if(pId!=null && !pId.equals("")) {
            sql +=" where t.tenant_id='"+userLoginDetails.getTenant().getId()+"' and  t.parent_id='"+tenantDepartment.getParent().getId()+"'";
        }else{
            sql +=" where t.tenant_id='"+userLoginDetails.getTenant().getId()+"' and t.parent_id is null";
        }

        if(newSort>oldSort){
            sql+=" and t.sort<="+newSort+" and t.sort >"+oldSort;
        }else{
            if(newSort==oldSort){
                sql+=" and t.sort = -100";
            }else{
                sql+=" and t.sort<"+oldSort+" and t.sort>="+newSort;
            }
        }
        this.tenantDepartmentDao.executeNativeBulk(sql,query);
    }

    public void updateTenantDepartment(TenantDepartment tenantDepartment, String oldPid, Integer oldSort){
        String newParentId = "";
        if(tenantDepartment.getParent()!=null){
            newParentId = tenantDepartment.getParent().getId();
        }
        if(com.hisun.util.StringUtils.trimNull2Empty(oldPid).equals(newParentId)){
            //父部门没有改变的情况下
            this.updateSort(tenantDepartment, oldSort);
        }else{
            //父部门改变的情况下
            String pId = "";
            if (tenantDepartment.getParent() != null) {
                pId = tenantDepartment.getParent().getId();
            }
            int newSort = tenantDepartment.getSort();
            int maxSort = this.getMaxSort(pId);
            if (newSort > maxSort) {
                newSort = maxSort;
            }
            tenantDepartment.setSort(newSort);
            this.updateSort(tenantDepartment, maxSort);
        }
        this.tenantDepartmentDao.update(tenantDepartment);
    }

}
