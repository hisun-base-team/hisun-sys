/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.exception.ErrorMsgShowException;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.resource.dao.ResourceDao;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.auth.service.PasswordHelper;
import com.hisun.saas.sys.auth.vo.PasswordSecurity;
import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.entity.AbstractRole;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleResourceDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.dao.ActivationDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUser4AdminDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserRoleDao;
import com.hisun.saas.sys.tenant.user.entity.Activation;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.saas.sys.tenant.user.service.TenantUser4AdminService;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class TenantUser4AdminServiceImpl extends BaseServiceImpl<TenantUser,String> implements TenantUser4AdminService {

    private static Logger logger = Logger.getLogger(TenantUser4AdminServiceImpl.class);

    private TenantUser4AdminDao tenantUser4AdminDao;

    @Resource
    @Qualifier("tenantUser4AdminDao")
    @Override
    public void setBaseDao(BaseDao<TenantUser, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantUser4AdminDao = (TenantUser4AdminDao)baseDao;
    }

}
