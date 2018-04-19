package com.hisun.saas.sys.tenant.message.service;

import com.hisun.saas.sys.tenant.message.entity.TenantOnlineMessage;
import com.hisun.saas.sys.tenant.message.vo.TenantOnlineMessageData;
import com.hisun.base.service.BaseService;

import java.util.Map;

/**
 * <p>类名称：TenantOnlineMessageService</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:04
 * @创建人联系方式：lihm_gz@30wish.net
 */
public interface TenantOnlineMessageService extends BaseService<TenantOnlineMessage, String> {

    void saveOnlineMessage(TenantOnlineMessageData onlineMessageData);

    int update(String sql, Map<String, Object> paramMap);

    /**
     * 标识全部已读
     * @param createUserId
     */
    void updateAllRead(String createUserId);
}
