package com.hisun.saas.sys.tenant.message.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.message.dao.TenantNoticeDao;
import com.hisun.saas.sys.tenant.message.entity.TenantNotice;
import com.hisun.saas.sys.tenant.message.service.TenantNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>类名称：TenantNoticeServiceImpl</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:17
 * @创建人联系方式：lihm_gz@30wish.net
 */
@Service
public class TenantNoticeServiceImpl extends BaseServiceImpl<TenantNotice, String> implements TenantNoticeService {

    private TenantNoticeDao tenantNoticeDao;


    @Resource
    @Override
    public void setBaseDao(BaseDao<TenantNotice, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantNoticeDao = (TenantNoticeDao) baseDao;
    }
}
