/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.resource.dao.TenantResourceDao;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResource;
import com.hisun.saas.sys.tenant.role.service.TenantRoleResourceService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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


    public void saveResource(TenantResource resource) throws Exception {

        Integer oldSort = this.tenantResourceDao.getMaxSort(resource.getpId());
        Integer newSort = resource.getSort();
        int retval = 0;
        if(oldSort!=null){
            retval = newSort.compareTo(oldSort);
        }

        if(retval>0){
            newSort = oldSort;
        }

        String queryCode = "";
        DecimalFormat decimalFormat = new DecimalFormat("000");
        if(!StringUtils.equals("1", resource.getpId())){
            TenantResource parentResource = this.tenantResourceDao.getByPK(resource.getpId());
            if(parentResource!=null){
                queryCode = parentResource.getQueryCode()+decimalFormat.format(newSort);
            }else{
                queryCode = decimalFormat.format(newSort);
            }
        }else{
            queryCode = decimalFormat.format(newSort);
        }
        resource.setQueryCode(queryCode);
        resource.setSort(newSort);

        this.updateSortAndQueryCode(resource, oldSort);
        this.tenantResourceDao.save(resource);
    }

    @Override
    public void updateSortAndQueryCode(TenantResource resource, Integer oldSort) throws Exception {
        if(resource!=null){
            Map<String, Object> map = Collections.emptyMap();
            Integer newSort = resource.getSort();
            String newQueryCode = resource.getQueryCode();

            DecimalFormat decimalFormat = new DecimalFormat("000");
            TenantResource parentResource = this.tenantResourceDao.getByPK(resource.getpId());
            String oldQueryCode;
            if(parentResource==null){
                oldQueryCode = decimalFormat.format(oldSort);
            }else{
                oldQueryCode = parentResource.getQueryCode()+decimalFormat.format(oldSort);
            }

            //String oldQueryCode = this.generateQueryCode(currentOrg.getParent().getQueryCode(), oldOrder, "0000");

            String sql="UPDATE sys_tenant_resource t SET ";
            if(newSort>oldSort){
                sql+="t.SORT=t.SORT-1";
            }else{
                sql+="t.SORT=t.SORT+1";
            }
            sql+=" where t.p_id='"+resource.getpId()+"' ";
            if(newSort>oldSort){
                sql+=" and t.SORT<="+newSort+" and t.SORT >"+oldSort;
            }else{
                if(newSort==oldSort){
                    sql+=" and t.SORT = -100";
                }else{
                    sql+=" and t.SORT<"+oldSort+" and t.SORT>="+newSort;
                }
            }

            this.tenantResourceDao.update(sql,map);

            if(newSort>oldSort){
                this.refreshQueryCodeForUp(oldQueryCode, newQueryCode);
            }else{
                if(newSort==oldSort){
                }else{
                    this.refreshQueryCodeForDown(newQueryCode, oldQueryCode);
                }
            }
        }else{
            throw new Exception("参数为空!");
        }
    }

    private void refreshQueryCodeForUp(String startCode, String endCode)
            throws Exception {
        Map<String, Object> map = Collections.emptyMap();
        int i = startCode.length();
        String updateSql = "UPDATE SYS_RESOURCE r SET r.QUERY_CODE = concat(substring(r.query_code, 1, "+i+"-3),right(concat('000',substring(r.query_code,"+i+"-3+1,3)-1),3),substring(r.QUERY_CODE,"+i+"+1,length(r.QUERY_CODE)))"+
                "WHERE substr(r.QUERY_CODE,1,"+i+") >= '"+startCode+"' and substr(r.QUERY_CODE,1,"+i+") <='"+endCode+"'" ;
        this.tenantResourceDao.update(updateSql,map);
    }

    private void refreshQueryCodeForDown(String startCode, String endCode)
            throws Exception {
        Map<String, Object> map = Collections.emptyMap();
        int i = startCode.length();
        String updateSql = "UPDATE SYS_RESOURCE r SET r.QUERY_CODE = concat(substring(r.query_code, 1, "+i+"-3),right(concat('000',substring(r.query_code,"+i+"-3+1,3)+1),3),substring(r.QUERY_CODE,"+i+"+1,length(r.QUERY_CODE)))"+
                "WHERE substr(r.QUERY_CODE,1,"+i+") >= '"+startCode+"' and substr(r.QUERY_CODE,1,"+i+") <='"+endCode+"' ";
        this.tenantResourceDao.update(updateSql,map);
    }

    @Override
    public void updateResources(TenantResource resource, 
                                String oldPid) throws Exception {
        TenantResource resource2 = this.tenantResourceDao.getByPK(resource.getId());
        Integer oldSatus = resource2.getStatus();
        Integer newStatus = resource.getStatus();
        if(resource.getResourceType()!=1){

            int newSort = resource.getSort().intValue();
            int maxSort = this.tenantResourceDao.getMaxSort(resource.getpId());
            if(newSort>maxSort){
                newSort = maxSort;
            }

            String queryCode = "";
            DecimalFormat decimalFormat = new DecimalFormat("000");
            if(!StringUtils.equals("1", resource.getpId())){
                TenantResource parentResource = this.tenantResourceDao.getByPK(resource.getpId());
                queryCode = parentResource.getQueryCode()+decimalFormat.format(newSort);
            }else{
                queryCode = decimalFormat.format(newSort);
            }
            resource.setQueryCode(queryCode);
            resource.setSort(newSort);
            if(StringUtils.isNotBlank(resource2.getQueryCode())){
                this.refreshQueryCodeToTmp(resource2.getQueryCode());
            }
            if(StringUtils.equals(resource.getpId(), oldPid)){
                this.updateSortAndQueryCode(resource, resource2.getSort());
            }else{
                this.updateSortAndQueryCode(resource, maxSort);
            }
            if(StringUtils.isNotBlank(resource2.getQueryCode())){
                this.refreshQueryCodeToFormal(resource2.getQueryCode(), queryCode);
            }

        }
        BeanUtils.copyProperties(resource2, resource);
        this.tenantResourceDao.update(resource2);
        if(oldSatus!=newStatus){
            extracted(resource, newStatus);
        }
    }


    private void extracted(TenantResource resource, Integer newStatus) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" pId = :pId ", "pId", resource.getId()));
        CommonOrderBy orderBy = new CommonOrderBy();
        orderBy.add(CommonOrder.asc("sort"));
        List<TenantResource> resources = tenantResourceDao.list(query, orderBy);
        for(TenantResource resource3 : resources){
            resource3.setStatus(newStatus);
            this.tenantResourceDao.update(resource3);
            extracted(resource3, newStatus);
        }
    }

    @Override
    public void refreshQueryCodeToTmp(String oldQueryCode) throws Exception {
        Map<String, Object> map = Collections.emptyMap();
        int i = oldQueryCode.length();
        String tmpCode = oldQueryCode.substring(0,oldQueryCode.length()-3)+"tmp";
        String updateSql = "UPDATE SYS_RESOURCE b set b.QUERY_CODE = concat('"+tmpCode+"',substr(b.QUERY_CODE,"+i+"+1,length(b.QUERY_CODE))) where substr(b.QUERY_CODE,1,"+i+") like '"+oldQueryCode+"%' ";
        this.tenantResourceDao.update(updateSql, map);
    }

    public void refreshQueryCodeToFormal(String oldQueryCode,String newQueryCode)  {
        Map<String, Object> map = Collections.emptyMap();
        int i = oldQueryCode.length();
        String tmpCode = oldQueryCode.substring(0,oldQueryCode.length()-3)+"tmp";
        String updateSql="update SYS_RESOURCE b set b.QUERY_CODE = concat('"+newQueryCode+"',substr(b.QUERY_CODE,"+(i+1)+",length(b.QUERY_CODE))) where substr(b.QUERY_CODE,1,"+i+") like'"+tmpCode+"%' ";
        this.tenantResourceDao.update(updateSql, map);
    }

    @Override
    public Integer getMaxSort(String pId) {
        return this.tenantResourceDao.getMaxSort(pId);
    }
}
