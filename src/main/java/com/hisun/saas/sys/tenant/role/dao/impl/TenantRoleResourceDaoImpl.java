/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.dao.impl;

import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleResource;
import com.hisun.base.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Repository
public class TenantRoleResourceDaoImpl extends BaseDaoImpl<TenantRoleResource,String> implements TenantRoleResourceDao {

    public List<TenantResource> findResourcesByUser(String userId){
        StringBuffer sql = new StringBuffer();
        sql.append(" select DISTINCT tenantResource.* from sys_tenant_user user  ");
        sql.append(" inner join sys_tenant_user_role userRole on user.id = userRole.tenant_user_id ");
        sql.append(" inner join sys_tenant_role role on userRole.tenant_role_id = role.id ");
        sql.append(" inner join sys_tenant_role_resource roleResource on role.id = roleResource.role_id ");
        sql.append(" inner join sys_tenant_2_resource tenant2Resource on roleResource.tenant_2_resource_id = tenant2Resource.id");
        sql.append(" inner join sys_tenant_resource tenantResource on tenant2Resource.resource_id = tenantResource.id");
        sql.append(" where user.id = :userId");
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId", userId);
        return nativeList(TenantResource.class, sql.toString(), paramMap);
    }
}
