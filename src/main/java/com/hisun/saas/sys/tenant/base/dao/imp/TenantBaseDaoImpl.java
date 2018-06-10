/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.base.dao.imp;

import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.base.entity.TenantEntity;
import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.base.entity.TenantEntityInterface;
import org.apache.log4j.Logger;
import org.apache.shiro.ShiroException;
import org.hibernate.Session;

public class TenantBaseDaoImpl<E extends java.io.Serializable, PK extends java.io.Serializable> extends BaseDaoImpl<E,PK>{

    private static final Logger logger = Logger.getLogger(TenantBaseDaoImpl.class);

    @Override
    public Session getSession() {
        Session session = super.getSession();
    	//如果为租户实体，则默认加上租户过滤条件进行数据逻辑隔离
    	if(TenantEntity.class.isAssignableFrom(this.entityClass)
			|| TenantEntityInterface.class.isAssignableFrom(this.entityClass)){
            UserLoginDetails userLoginDetails = null;
    		try{
    			//通常情况下可取得当前用户信息，因为用户操作都需要通过认证后才能访问资源。
    			//但是后台轮循程序则可能无法取得当前用户信息，会抛出异常，
    			//事实上后台轮循程序不需要通过用户信息来确保数据隔离。
    			userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
    		}catch(ShiroException e){
    			logger.info("This entity is TenantEntity,but tenantFilter is not used.Reason is ["+e.getMessage()+"]");
    		}
    		if(userLoginDetails!=null &&
					(userLoginDetails.getUserType()== Constants.USER_TYPE_TENANT)){
    			session.enableFilter("tenantFilter").setParameter("tenantFilterParam", userLoginDetails.getTenantId());
    		}
    	}
        return session;
    }
}
