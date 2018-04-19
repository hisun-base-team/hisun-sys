/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.dao.impl;

import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.user.dao.TenantUserRoleDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Rocky {rockwithyou@126.com}
 */
@Repository
public class TenantUserRoleDaoImpl extends BaseDaoImpl<TenantUserRole,String> implements TenantUserRoleDao {
    @Override
    public List<TenantRole> findRolesByUser(String userId) {
        String sql = "select r.* from sys_tenant_user_role ur ,sys_tenant_role r"
                + " where ur.tenant_user_id = :userId and ur.tenant_role_id = r.id";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId", userId);
        return nativeList(TenantRole.class, sql, paramMap);
    }

}
