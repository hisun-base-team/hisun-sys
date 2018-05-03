/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.service;

import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTplt;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTpltResource;

import java.util.List;


public interface TenantRoleTpltService extends BaseService<TenantRoleTplt,String> {

    boolean existRoleCode(String roleCode);
    void update(TenantRoleTplt tenantRoleTplt, List<TenantRoleTpltResource> tenantRoleTpltResources);
}

