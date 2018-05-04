/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.util;

import java.util.Date;

import com.hisun.base.entity.BaseEntity;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.base.entity.TenantEntity;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;

import org.apache.shiro.SecurityUtils;

/**
 * 用于entity转vo，及vo转entity；入参对象里的对象属性，只要在支持范围内，也可直接完成转换。
 * 目前支持转换的对象有net.wish30.cloud.automation.entity包下的
 * JobTarget, ScriptParameter, Script, JobTemplateScript, JobTemplate, JobScriptParameterInst, JobScriptInst, 
 * Job
 * @author liuzj
 *
 */

public class BeanTrans {
	
	
	
	/**
	 * 为entity类set基础属性，即BaseEntity声明的属性，创建人创建时间、修改人修改时间等属性。type作为区分，save表示创建**和修改**均需要set，用于新增，update表示只set修改**，用于修改
	 * @param entity
	 * @param userLoginDetails
	 * @param type
	 */
	public static void setBaseProperties(BaseEntity entity, UserLoginDetails userLoginDetails, String type) {
		if (userLoginDetails == null) {
			return;
		}
		String user_id = userLoginDetails.getUserid();
		String user_name = userLoginDetails.getUsername();
		Date now = new Date();
		if (SAVE.equals(type)) {
			entity.setCreateDate(now);
			entity.setCreateUserId(user_id);
			entity.setCreateUserName(user_name);
			entity.setUpdateDate(now);
			entity.setUpdateUserId(user_id);
			entity.setUpdateUserName(user_name);
		} else if (UPDATE.equals(type)) {
			entity.setUpdateDate(now);
			entity.setUpdateUserId(user_id);
			entity.setUpdateUserName(user_name);
		}
	}
	
	public static final String SAVE="save";
	public static final String UPDATE="update";
	
	/**
	 * 在新建保存的时候自动set入tenant和其他基本信息
	 * @param entity
	 * @param
	 * @param type
	 */
	public static void setBaseProperties(TenantEntity entity, String type) {
		UserLoginDetails userLoginDetails =(UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		setBaseProperties(entity, userLoginDetails, type);
		if (SAVE.equals(type)) {
			Tenant tenant=new Tenant();
			tenant.setId(userLoginDetails.getTenantId());
			entity.setTenant(tenant);;
		}
	}
	
	public static void setBasePropertiesNotIncludeUser(BaseEntity entity, String type) {
		Date now = new Date();
		if (SAVE.equals(type)) {
			entity.setCreateDate(now);
			entity.setUpdateDate(now);
		} else if (UPDATE.equals(type)) {
			entity.setUpdateDate(now);
		}
	}
	
	
}
