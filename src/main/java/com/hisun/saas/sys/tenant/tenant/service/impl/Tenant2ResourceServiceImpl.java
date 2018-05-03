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
import com.hisun.saas.sys.tenant.Constants;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResource;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTpltResource;
import com.hisun.saas.sys.tenant.role.service.TenantRoleResourceService;
import com.hisun.saas.sys.tenant.tenant.dao.Tenant2ResourceDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class Tenant2ResourceServiceImpl extends BaseServiceImpl<Tenant2Resource,String>
        implements Tenant2ResourceService {

    private Tenant2ResourceDao tenant2ResourceDao;

    @Resource
    private TenantRoleResourceService tenantRoleResourceService;

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
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public String save(Tenant2Resource tenant2Resource){
        String pk = this.tenant2ResourceDao.save(tenant2Resource);
        //取得当前单位下的缺省Role
        List<TenantRole> roles = tenant2Resource.getTenant().getRoles();
        if(roles!=null && roles.size()>0){
            List<TenantRole> defaultRoles = new ArrayList<>();
            for(TenantRole role : roles){
                if(role.getIsDefault()== Constants.DEFAULT_ROLE){
                    defaultRoles.add(role);
                }
            }
            //循环当前单位下的缺省Role,如果当前资源在确认Role的对应的RoleTplt下,则默认授予当前Role
            for(TenantRole role : defaultRoles){
                List<TenantRoleTpltResource> tenantRoleTpltResources = role.getTenantRoleTplt().getTenantRoleTpltResources();
                for(TenantRoleTpltResource tenantRoleTpltResource : tenantRoleTpltResources){
                    if (tenantRoleTpltResource.getTenantResource().getId().equals(tenant2Resource.getTenantResource().getId())) {
                        TenantRoleResource  tenantRoleResource = new TenantRoleResource();
                        tenantRoleResource.setRole(role);
                        tenantRoleResource.setTenantResource(tenant2Resource.getTenantResource());
                        tenantRoleResource.setTenant2Resource(tenant2Resource);
                        tenantRoleResourceService.save(tenantRoleResource);
                    }
                }
            }
        }
        return pk;
    }
}
