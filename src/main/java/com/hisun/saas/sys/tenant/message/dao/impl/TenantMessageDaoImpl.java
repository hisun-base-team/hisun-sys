package com.hisun.saas.sys.tenant.message.dao.impl;

import com.hisun.saas.sys.tenant.base.dao.imp.TenantBaseDaoImpl;
import com.hisun.saas.sys.tenant.message.dao.TenantMessageDao;
import com.hisun.saas.sys.tenant.message.entity.TenantMessage;
import org.springframework.stereotype.Repository;

/**
 * <p>类名称：TenantMessageDaoImpl</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午3:51
 * @创建人联系方式：lihm_gz@30wish.net
 */
@Repository
public class TenantMessageDaoImpl extends TenantBaseDaoImpl<TenantMessage, String> implements TenantMessageDao {
}
