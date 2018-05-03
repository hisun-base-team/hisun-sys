/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleTpltDao;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleTpltResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTplt;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTpltResource;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import com.hisun.saas.sys.tenant.role.service.TenantRoleTpltService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TenantRoleTpltServiceImpl extends BaseServiceImpl<TenantRoleTplt,String>
        implements TenantRoleTpltService {

    private TenantRoleTpltDao tenantRoleTpltDao;

    @Resource
    private  TenantRoleTpltResourceDao tenantRoleTpltResourceDao;


    @Resource
    @Override
    public void setBaseDao(BaseDao<TenantRoleTplt, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantRoleTpltDao = (TenantRoleTpltDao)baseDao;
    }


    public boolean existRoleCode(String roleCode){
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" roleCodePrefix = :code","code",roleCode));
        List<TenantRoleTplt> roleTpltList = this.list(query,null);
        if(roleTpltList!=null && roleTpltList.size()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void update(TenantRoleTplt tenantRoleTplt, List<TenantRoleTpltResource> tenantRoleTpltResources) {
        //删除原有的
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" tenantRoleTplt.id = :tpltId ","tpltId",tenantRoleTplt.getId()));
        this.tenantRoleTpltResourceDao.deleteBatch(query);
        tenantRoleTplt.getTenantRoleTpltResources().clear();
        tenantRoleTplt.getTenantRoleTpltResources().addAll(tenantRoleTpltResources);
        this.update(tenantRoleTplt);
    }

}
