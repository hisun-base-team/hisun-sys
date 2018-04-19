package com.hisun.saas.sys.tenant.message.service;

import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.tenant.message.entity.TenantMessage;

/**
 * <p>类名称：TenantMessageService</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:01
 * @创建人联系方式：lihm_gz@30wish.net
 */
public interface TenantMessageService extends BaseService<TenantMessage, String> {

    TenantMessage save(String userId,String tenantId);

    TenantMessage findMessageByUserId(String userId);
}
