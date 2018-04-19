/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.message.service.impl;

import com.hisun.saas.sys.tenant.message.service.TenantMessageService;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.tenant.message.dao.TenantMessageDao;
import com.hisun.saas.sys.tenant.message.entity.TenantMessage;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantMessageServiceImpl extends BaseServiceImpl<TenantMessage, String> implements TenantMessageService {

    private TenantMessageDao tenantMessageDao;

    @Resource
    private TenantUserDao tenantUserDao;

    @Resource
    private TenantDao tenantDao;

    @Override
    public void setBaseDao(BaseDao<TenantMessage, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantMessageDao = (TenantMessageDao) baseDao;
    }

    @Override
    public TenantMessage save(String userId, String tenantId) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" createUserId =:userId ", "userId", userId));
        query.add(CommonRestrictions.and(" tenant.id =:tenantId ", "tenantId", tenantId));
        List<TenantMessage> messages = this.tenantMessageDao.list(query, null);
        TenantMessage message = new TenantMessage();
        if (messages.size() > 0) {
            return messages.get(0);
        } else {
            TenantUser createUser = this.tenantUserDao.getByPK(userId);
            Tenant tenant = this.tenantDao.getByPK(tenantId);
            message.setCreateDate(new Date());
            message.setCreateUserName(createUser.getUsername());
            message.setTenant(tenant);
            this.tenantMessageDao.save(message);
        }
        return message;
    }

    @Override
    public TenantMessage findMessageByUserId(String userId) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" createUserId = :id", "id", userId));
        List<TenantMessage> messageList = this.tenantMessageDao.list(query, null);
        if (messageList.size() == 0) {
            return null;
        }
        return messageList.get(0);
    }
}
