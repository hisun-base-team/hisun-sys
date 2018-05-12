/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dzda.dao.impl;

import com.hisun.saas.sys.admin.dzda.dao.ECatalogTypeDao;
import com.hisun.saas.sys.admin.dzda.entity.ECatalogTypeInfo;
import com.hisun.saas.sys.tenant.base.dao.imp.TenantBaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author liuzj {279421824@qq.com}
 */
@Repository
public class ECatalogTypeDaoImpl extends TenantBaseDaoImpl<ECatalogTypeInfo,String>
       implements ECatalogTypeDao {


}
