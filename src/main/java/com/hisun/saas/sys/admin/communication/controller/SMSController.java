package com.hisun.saas.sys.admin.communication.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.entity.SMSConfig;
import com.hisun.saas.sys.admin.communication.service.SMSService;
import com.hisun.saas.sys.admin.communication.vo.MessageResult;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.communication.entity.SMSTemplate;
import com.hisun.saas.sys.admin.communication.service.SMSTemplateService;
import com.hisun.saas.sys.admin.communication.vo.Template;
import com.hisun.util.HttpClientUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: SMSController.java </p>
 * <p>Package com.hisun.cloud.sys.controller </p>
 * <p>Description: 短信控制器</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月30日 上午11:19:02 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/sms")
public class SMSController extends BaseController {

	@Resource
	private com.hisun.saas.sys.admin.communication.service.SMSConfigService SMSConfigService;
	
	@Resource
	private SMSService smsService;
	
	@Resource
	private SMSTemplateService smsTemplateService;

	@RequiresPermissions("admin-sys:sms_config")
	@RequestMapping(value="/config")
	public ModelAndView listSMSConfig(HttpServletRequest req,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			//query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
			Long total = SMSConfigService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("sort"));
			List<SMSConfig> resultList = SMSConfigService.list(query, orderBy, pageNum, pageSize);
			PagerVo<SMSConfig> pager = new PagerVo<SMSConfig>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/sms/listSMS",map);
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				SMSConfig entity = this.SMSConfigService.getByPK(id);
				map.put("data", entity);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("admin-sys:sms_add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(SMSConfig config) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SMSConfigService.save(config);
			map.put("success", true);
			map.put("data", config);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}

		return map;
	}

	@RequiresPermissions("admin-sys:sms_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public @ResponseBody ModelAndView add() {
		return new ModelAndView("saas/sys/admin/sms/addSMSConfig");
	}


	@RequiresPermissions("admin-sys:sms_delete")
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.SMSConfigService.deleteByPK(id);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
			map.put("message", "系统错误");
		}
		return map;
	}

	@RequiresPermissions("admin-sys:sms_update")
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(SMSConfig config){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.SMSConfigService.update(config);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}
		return map;
	}

	@RequiresPermissions("admin-sys:sms_update")
	@RequestMapping(value = "/update/{id}")
	public ModelAndView update(@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("config",this.SMSConfigService.getByPK(id));
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/sms/updateSMSConfig",map);
	}

	@RequiresPermissions(value = "admin-sys:sms_test")
	@RequestMapping(value = "/test/{id}")
	public ModelAndView test(@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("id",id);
			map.put("code", RandomStringUtils.randomNumeric(4));
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/sms/testSMSConfig",map);
	}

	@RequiresPermissions(value = "admin-sys:sms_test")
	@RequestMapping(value = "/test")
	public @ResponseBody  Map<String,Object> test(String id,String code,String tel){
		Map<String, Object> map = new HashMap<String, Object>();
		SMSConfig config = this.SMSConfigService.getByPK(id);

		StringBuilder sb = new StringBuilder();
		sb.append(config.getSmsServer()).append("/").append(config.getVersion()).append("/")
				.append(config.getSend());
		Map<String, String> params = Maps.newHashMap();
		params.put("apikey", config.getApikey());
		params.put("text", "您的验证码是"+code);
		params.put("mobile", tel);
		MessageResult result = HttpClientUtil.post(sb.toString(), params,MessageResult.class);
		if(result==null){
			logger.error("网络异常,无法请求云片网！");
			map.put("success",false);
		}else{
			if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
				logger.error(ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
				map.put("success",false);
			}
			map.put("success",true);
		}

		return map;
	}

	@RequiresPermissions("admin-sys:sms_tpl")
	@RequestMapping(value = "/tpl")
	public ModelAndView tpl(HttpServletRequest req,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			//query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
			Long total = smsTemplateService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("sort"));
			List<SMSTemplate> resultList = smsTemplateService.list(query, orderBy, pageNum, pageSize);
			PagerVo<SMSTemplate> pager = new PagerVo<SMSTemplate>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/sms/listTpl",map);
	}

	@RequiresPermissions("admin-sys:smstpl_add")
	@RequestMapping(value = "/tpl/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addTpl(@RequestParam(value="tplContent")String tplContent,@RequestParam(value="permission")String permission){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MessageResult result =this.smsService.addTpl(""+tplContent, Integer.valueOf(0));
			if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
				 map.put("success", false);
				 map.put("message", result.getMsg());
			 }else{
				 Template template = result.getTemplate();
				 SMSTemplate smsTemplate = new SMSTemplate();
				 smsTemplate.setTplId(template.getTpl_id());
				 smsTemplate.setCheckStatus(template.getCheck_status());
				 smsTemplate.setReason(template.getReason());
				 smsTemplate.setTplContent(template.getTpl_content());
				 smsTemplate.setPermission(permission);
				 this.smsTemplateService.save(smsTemplate);
				 map.put("success", true);
				 map.put("data", result.getTemplate());
			 }
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "系统错误");
		}
		return map;
	}

	@RequiresPermissions("admin-sys:smstpl_delete")
	@RequestMapping(value = "/tpl/delete/{id}/{tlpid}")
	public @ResponseBody Map<String, Object> deleteTpl(
			@PathVariable("id") Long id,@PathVariable("tlpid") String tlpid)  throws GenericException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (id!=null) {
				MessageResult result = this.smsService.delTpl(id);
				if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
					 map.put("success", false);
					map.put("message", result.getMsg());
				 }else{
					 this.smsTemplateService.deleteByPK(tlpid);
					 map.put("success", true);
				 }
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");

		}
		return map;
	}

	@RequiresPermissions("admin-sys:smstpl_update")
	@RequestMapping(value = "/tpl/update")
	public @ResponseBody Map<String, Object> updateTpl(SMSTemplate template) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MessageResult result = this.smsService.updateTpl(Long.valueOf(template.getTplId()), template.getTplContent()+"");
			if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
				 map.put("success", false);
				 map.put("message", result.getMsg());
			 }else{
				 SMSTemplate entity = this.smsTemplateService.getByPK(template.getId());
				 entity.setTplContent(""+template.getTplContent());
				 entity.setPermission(template.getPermission());
				 this.smsTemplateService.update(entity);
				 map.put("success", true);
			 }
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");
		}
		return map;
	}
	
	@RequestMapping(value = "/tpl/get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getTpl(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				SMSTemplate entity = this.smsTemplateService.getByPK(id);
				entity.setTplContent(entity.getTplContent().replace("", ""));
				map.put("data", entity);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}
}
