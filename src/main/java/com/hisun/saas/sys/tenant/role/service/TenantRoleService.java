/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.service;

import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.vo.TreeNodeVo;

import java.util.List;


public interface TenantRoleService extends BaseService<TenantRole,String> {

    Integer getMaxSort();
    void updateTenantRole(TenantRole tenantRole, Integer oldSort);
    boolean existRoleCode(String roleCode);
}
