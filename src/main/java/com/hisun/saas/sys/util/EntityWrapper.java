/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.util;

import com.hisun.base.entity.BaseEntity;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.base.entity.TenantEntity;

import java.util.Date;


public class EntityWrapper {

    public static void wrapperSaveBaseProperties(BaseEntity entity, UserLoginDetails userLoginDetails) {
        if (userLoginDetails == null) {
            return;
        }
        entity.setCreateDate(new Date());
        entity.setCreateUserId(userLoginDetails.getUserid());
        entity.setCreateUserName(userLoginDetails.getUsername());

    }

    public static void wrapperUpdateBaseProperties(BaseEntity entity, UserLoginDetails userLoginDetails) {
        if (userLoginDetails == null) {
            return;
        }
        entity.setUpdateDate(new Date());
        entity.setUpdateUserId(userLoginDetails.getUserid());
        entity.setUpdateUserName(userLoginDetails.getUsername());
    }

    public static void wrapperSaveBaseProperties(TenantEntity entity, UserLoginDetails userLoginDetails) {
        if (userLoginDetails == null) {
            return;
        }
        entity.setTenant(userLoginDetails.getTenant());
        entity.setCreateDate(new Date());
        entity.setCreateUserId(userLoginDetails.getUserid());
        entity.setCreateUserName(userLoginDetails.getUsername());

    }

    public static void wrapperUpdateBaseProperties(TenantEntity entity, UserLoginDetails userLoginDetails) {
        if (userLoginDetails == null) {
            return;
        }
        entity.setUpdateDate(new Date());
        entity.setUpdateUserId(userLoginDetails.getUserid());
        entity.setUpdateUserName(userLoginDetails.getUsername());
    }
}
