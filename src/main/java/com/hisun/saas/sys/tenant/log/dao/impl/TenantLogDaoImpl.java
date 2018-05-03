/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.log.dao.impl;

import com.hisun.saas.sys.tenant.log.dao.TenantLogDao;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.base.dao.imp.TenantBaseDaoImpl;
import org.springframework.stereotype.Repository;


@Repository
public  class TenantLogDaoImpl extends TenantBaseDaoImpl<TenantLog, String> implements TenantLogDao {

}
