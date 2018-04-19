/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.filter;

import com.hisun.base.auth.Constants;
import com.hisun.base.auth.service.SessionHelper;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class FreezeTenantUserFilter extends PathMatchingFilter {

    @Resource
    private TenantUserService tenantUserService;

    @Resource
    private SessionHelper sessionHelper;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
        if(details == null || details.getUserType() != Constants.USER_TYPE_TENANT){
            //为空不管,或者不是租户用户
            return true;
        }
        TenantUser tenantUser = tenantUserService.getByPK(details.getUserid());
        if(tenantUser.getTombstone() == TombstoneEntity.TOMBSTONE_TRUE){
            //已被冻结,踢掉SESSION
            sessionHelper.kickOutSession(tenantUser.getUsername());
        }
        return true;
    }
}

