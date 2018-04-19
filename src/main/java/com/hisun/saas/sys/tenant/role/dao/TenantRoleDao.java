/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.dao;

import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.base.dao.BaseDao;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public interface TenantRoleDao extends BaseDao<TenantRole,String>{

    public void batchSaveResource(final String roleId, final String[] resourceIds);

    /**
     * 根据CODE获取
     * @param code
     * @return
     */
    public TenantRole getByCode(String code);
}
