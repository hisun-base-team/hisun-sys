/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.service;

import com.hisun.saas.sys.tenant.tenant.vo.TenantVo;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;

public interface TenantService extends BaseService<Tenant, String> {

	 Tenant save(TenantVo tenantVo);
	 void updateToFreeze(Tenant tenant);
	 void updateToActivate(Tenant tenant);
	 void updateOwn(TenantVo vo);
	 Tenant selectTenantByToken(String token);
	 Tenant getByName(String name);


}

