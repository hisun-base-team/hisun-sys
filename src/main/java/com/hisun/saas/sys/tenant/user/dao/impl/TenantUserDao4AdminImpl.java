/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.dao.impl;

import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.dao.TenantUser4AdminDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import org.springframework.stereotype.Repository;

@Repository("tenantUser4AdminDao")
public class TenantUserDao4AdminImpl extends BaseDaoImpl<TenantUser,String> implements TenantUser4AdminDao {

}
