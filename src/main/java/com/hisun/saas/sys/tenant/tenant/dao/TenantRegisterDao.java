/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.dao;

import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.base.dao.BaseDao;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;


public interface TenantRegisterDao extends BaseDao<TenantRegister,String> {

    public String save(TenantRegisterVo vo);
    public int updateRegisterActivate(String id);
}
