/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dzda.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.dzda.dao.ECatalogTypeDao;
import com.hisun.saas.sys.admin.dzda.entity.ECatalogTypeInfo;
import com.hisun.saas.sys.admin.dzda.service.ECatalogTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzj {279421824@qq.com}
 */
@Service
public class ECatalogTypeServiceImpl extends BaseServiceImpl<ECatalogTypeInfo,String>
        implements ECatalogTypeService {

    private ECatalogTypeDao eCatalogTypeDao;

    @Resource
    public void setBaseDao(BaseDao<ECatalogTypeInfo, String> baseDao) {
        this.baseDao = baseDao;
        this.eCatalogTypeDao = (ECatalogTypeDao)baseDao;
    }

    public Integer getMaxSort(String pId) {
        Map<String, Object> map=new HashMap<String, Object>();
        String hql = "select max(e.sort)+1 as sort from ECatalogTypeInfo e ";
        if(pId!=null && !pId.equals("")) {
            hql = hql+"where e.parent.id =:pId";
            map.put("pId", pId);
        }else{
            hql = hql+"where e.parent is null";
        }
        List<Map> maxSorts = this.eCatalogTypeDao.list(hql, map);
        if(maxSorts.get(0).get("sort")==null){
            return 1;
        }else{
            Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
            return maxSort;
        }
    }

    public void updateCatalogType(ECatalogTypeInfo eCatalogTypeInfo, String oldPid, Integer oldSort){
        String newParentId = "";
        if(eCatalogTypeInfo.getParent()!=null){
            newParentId = eCatalogTypeInfo.getParent().getId();
        }
        if(com.hisun.util.StringUtils.trimNull2Empty(oldPid).equals(newParentId)){
            //父部门没有改变的情况下
            this.updateSort(eCatalogTypeInfo, oldSort);
        }else{
            //父部门改变的情况下
            String pId = "";
            if (eCatalogTypeInfo.getParent() != null) {
                pId = eCatalogTypeInfo.getParent().getId();
            }
            int newSort = eCatalogTypeInfo.getSort();
            int maxSort = this.getMaxSort(pId);
            if (newSort > maxSort) {
                newSort = maxSort;
            }
            eCatalogTypeInfo.setSort(newSort);
            this.updateSort(eCatalogTypeInfo, maxSort);
        }
        this.eCatalogTypeDao.update(eCatalogTypeInfo);
    }

    private void updateSort(ECatalogTypeInfo eCatalogTypeInfo, Integer oldSort)  {
        CommonConditionQuery query = new CommonConditionQuery();
        Integer newSort = eCatalogTypeInfo.getSort();
        String pId = "";
        if(eCatalogTypeInfo.getParent()!=null){
            pId = eCatalogTypeInfo.getParent().getId();
        }
        String sql="update e_catalog_type_info t set ";
        if(newSort>oldSort){
            sql+="t.sort=t.sort-1";
        }else{
            sql+="t.sort=t.sort+1";
        }

        if(pId!=null && !pId.equals("")) {
            sql +=" where  t.parent_id='"+eCatalogTypeInfo.getParent().getId()+"'";
        }else{
            sql +=" where t.parent_id is null";
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
        this.eCatalogTypeDao.executeNativeBulk(sql,query);
    }
}
