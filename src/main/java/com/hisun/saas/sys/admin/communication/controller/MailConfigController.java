package com.hisun.saas.sys.admin.communication.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.entity.EmailTemplate;
import com.hisun.saas.sys.admin.communication.entity.MailConfig;
import com.hisun.saas.sys.admin.communication.service.EmailTemplateService;
import com.hisun.saas.sys.admin.communication.service.MailConfigService;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailResult;
import com.hisun.saas.sys.admin.communication.vo.MailSendVo;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.util.HttpClientUtil;
import com.hisun.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
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
 * <p>Title: MailConfigController.java </p>
 * <p>Package com.hisun.saas.sys.controller </p>
 * <p>Description: 邮件配置</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月4日 上午9:33:55 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/mail")
public class MailConfigController extends BaseController{

	@Resource
	private MailConfigService mailConfigService;
	
	@Resource
	private EmailTemplateService emailTemplateService;

	@Resource
	private MailService mailService;
	
	@RequestMapping(value="/config")
	public ModelAndView listSMSConfig(HttpServletRequest req,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize){
		
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			Long total = mailConfigService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			List<MailConfig> resultList = mailConfigService.list(query, orderBy, pageNum, pageSize);
			PagerVo<MailConfig> pager = new PagerVo<MailConfig>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/mail/listMail",map);
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				MailConfig entity = this.mailConfigService.getByPK(id);
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

	@RequiresPermissions("admin-platform:mail_add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(MailConfig config)  throws GenericException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			mailConfigService.save(config);
			map.put("success", true);
			map.put("data", config);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
			throw new GenericException(e);
		}

		return map;
	}

	@RequiresPermissions("admin-platform:mail_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public @ResponseBody ModelAndView add() {
		return new ModelAndView("saas/sys/admin/mail/addMailConfig");
	}

	@RequestMapping(value = "/update/{id}")
	public ModelAndView update(@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("config",this.mailConfigService.getByPK(id));
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/mail/updateMailConfig",map);
	}

	@RequiresPermissions("admin-platform:mail_delete")
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				this.mailConfigService.deleteByPK(id);
				map.put("success", true);
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
			throw new GenericException(e);

		}
		return map;
	}

	@RequiresPermissions("admin-platform:mail_update")
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(MailConfig config){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.mailConfigService.update(config);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}
		return map;
	}

	@RequiresPermissions("admin-platform:mail_test")
	@RequestMapping(value = "/test/{id}")
	public ModelAndView test(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("tpls",this.emailTemplateService.list());
		return new ModelAndView("saas/sys/admin/mail/testMailConfig",map);
	}

	@RequiresPermissions("admin-platform:mail_test")
	@RequestMapping(value = "/test")
	public @ResponseBody  Map<String,Object> test(String id,String from,String to,String tpl,String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		MailConfig config = this.mailConfigService.getByPK(id);
		EmailTemplate templateService = this.emailTemplateService.getByPK(tpl);
		StringBuilder sb = new StringBuilder();
		sb.append(config.getMailServer()).append("/").append(config.getVersion()).append("/")
				.append(config.getSendTemplate());
		Map<String, String> params = Maps.newHashMap();
		params.put("api_user",config.getApiUser());
		params.put("api_key",config.getApiKey());
		params.put("from",from);
		params.put("template_invoke_name",templateService.getPermission());
		//params.put("to",emailTo);
		MailSendVo vo = new  MailSendVo();
		List<String> list = Lists.newArrayList();
		list.add(to);
		vo.setTo(list);
		Map<String,List<String>> maps = Maps.newHashMap();
		vo.setSub(maps);

		params.put("substitution_vars", JacksonUtil.nonDefaultMapper().toJson(vo));
		//params.put("html",text);
		params.put("subject",title);
		MailResult mailResult = HttpClientUtil.post(sb.toString(),params,MailResult.class);
		if(StringUtils.equalsIgnoreCase(MailResult.SUCCESS,mailResult.getMessage())){
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}
	@RequestMapping(value="/tpl")
	public ModelAndView tpl(HttpServletRequest req,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException{
		
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			//query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
			Long total = emailTemplateService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("sort"));
			List<EmailTemplate> resultList = emailTemplateService.list(query, orderBy, pageNum, pageSize);
			PagerVo<EmailTemplate> pager = new PagerVo<EmailTemplate>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/mail/listTpl",map);
	}
	
	@RequestMapping(value = "/tpl/get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getTpl(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				EmailTemplate entity = this.emailTemplateService.getByPK(id);
				map.put("data", entity);
				map.put("success", true);
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			map.put("success", false);
		}
		return map;
	}

	@RequiresPermissions("admin-platform:mailtpl_add")
	@RequestMapping(value = "/tpl/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addTpl(EmailTemplate config)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MailResult result = this.mailService.addTpl(config.getPermission(),config.getName(),config.getTplContent(),config.getSubject(),1);
			if(StringUtils.equalsIgnoreCase(MailResult.SUCCESS,result.getMessage())){
				emailTemplateService.save(config);
				map.put("success", true);
				map.put("data", config);
			}else{
				map.put("success", false);
				map.put("data", "添加失败，请稍后重试!");
			}

		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}

		return map;
	}

	@RequiresPermissions("admin-platform:mailtpl_delete")
	@RequestMapping(value = "/tpl/delete/{id}")
	public @ResponseBody Map<String, Object> deleteTpl(
			@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				EmailTemplate template = this.emailTemplateService.getByPK(id);
				MailResult result = this.mailService.delTpl(template.getPermission());
				if(StringUtils.equalsIgnoreCase(MailResult.SUCCESS,result.getMessage())){
					this.emailTemplateService.deleteByPK(id);
					map.put("success", true);
				}else{
					map.put("success", false);
					map.put("data", "删除失败，请稍后重试!");
				}

			}
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);

		}
		return map;
	}

	@RequiresPermissions("admin-platform:mailtpl_update")
	@RequestMapping(value = "/tpl/update")
	public @ResponseBody Map<String, Object> update(EmailTemplate config){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MailResult result = this.mailService.updateTpl(config.getPermission(),config.getName(),config.getTplContent(),config.getSubject());
			if(StringUtils.equalsIgnoreCase(MailResult.SUCCESS,result.getMessage())) {
				this.emailTemplateService.update(config);
				map.put("success", true);
			}else{
				map.put("success", false);
				map.put("data", "更新失败，请稍后重试!");
			}
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}
}
