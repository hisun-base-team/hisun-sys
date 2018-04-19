package com.hisun.saas.sys.tenant.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.annotation.Resource;

import com.hisun.saas.sys.tenant.vo.TenantOutside;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.base.controller.BaseController;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * <p>Title: OutsideController.java </p>
 * <p>Package com.hisun.saas.sys.controller </p>
 * <p>Description: 外部接口入口</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月28日 上午11:34:24 
 * @version 
 */
@Controller
@RequestMapping("/outside")
public class OutsideController extends BaseController{

	@Resource
	private TenantService tenantService;
	
	@RequestMapping(value = "/tenant/token/{token}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getTenantByToken(
			@PathVariable(value = "token") String token) throws IllegalAccessException, InvocationTargetException{
		Map<String, Object> map = Maps.newHashMap();
		if(StringUtils.length(StringUtils.trimToEmpty(token))==32){
			Tenant tenant =tenantService.selectTenantByToken(token);
			if(tenant==null){
				map.put("message", "没有该组织,请确认token是否正确!");
				map.put("success", false);
			}else{
				TenantOutside tenantOutside = new TenantOutside();
				BeanUtils.copyProperties(tenantOutside,tenant);
				map.put("message", "");
				map.put("success", true);
				map.put("tenant", tenantOutside);
			}
		}else{
			map.put("message", "token长度不对!");
			map.put("success", false);
		}
		return map;
	}
}
