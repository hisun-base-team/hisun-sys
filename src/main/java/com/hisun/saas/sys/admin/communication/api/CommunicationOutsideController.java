/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.communication.api;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.service.EmailTemplateService;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.service.SMSService;
import com.hisun.saas.sys.admin.communication.service.SMSTemplateService;
import com.hisun.saas.sys.admin.communication.vo.MailSendVo;
import com.hisun.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/api/communication")
public class CommunicationOutsideController extends BaseController{

	@Resource
	private EmailTemplateService emailTemplateService;
	
	@Resource
	private MailService mailService;
	
	@Resource
	private SMSService smsService;
	
	@Resource
	private SMSTemplateService smsTemplateService;
	
	/*@RequestMapping(value="/get/email/tpl",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getEmailTplMessage(@RequestParam String permission,HttpServletRequest req){
		Map<String,Object> map = Maps.newHashMap();
		Map<String, String> params = Maps.newHashMap();
		Enumeration<String> keys = req.getParameterNames(); 
		while(keys.hasMoreElements()) { 
		    String key = keys.nextElement(); 
		    params.put(key, req.getParameter(key));
		} 
		try {
			String str = this.emailTemplateService.getTplMessage(permission, params);
			map.put("message", str);
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "查询邮件模板异常!");
			map.put("success", false);
			logger.error("查询邮件模板异常!",e);
		}
		return map;
	}*/
	
	@RequestMapping(value="/send/email",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> sendEmail(@RequestParam String permission, @RequestParam String title, @RequestParam  String text, @RequestParam MailSendVo vo){
		Map<String,Object> map = Maps.newHashMap();
		try {
			this.mailService.sendEmail(permission,title, text, vo,Boolean.FALSE);
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "发送邮件异常!");
			map.put("success", false);
			logger.error("发送邮件异常!",e);
		}
		return map;
	}
	
	@RequestMapping(value="/send/sms",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> sendSMS(@RequestParam String text,@RequestParam String ...mobile){
		Map<String,Object> map = Maps.newHashMap();
		try {
			this.smsService.sendSms(text, mobile);
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "发送短信异常!");
			map.put("success", false);
			logger.error("发送短信异常!",e);
		}
		return map;
	}
	
	@RequestMapping(value="/get/sms/tpl/message",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getSMSTplMessage(@RequestParam String permission,HttpServletRequest req){
		Map<String,Object> map = Maps.newHashMap();
		Map<String, String> params = Maps.newHashMap();
		Enumeration<String> keys = req.getParameterNames(); 
		while(keys.hasMoreElements()) { 
		    String key = keys.nextElement(); 
		    params.put(key, req.getParameter(key));
		} 
		try {
			String str = this.smsTemplateService.getTplMessage(permission, params);
			map.put("message", str);
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "获取短信模板异常!");
			map.put("success", false);
			logger.error("获取短信模板异常!",e);
		}
		return map;
	}
}
