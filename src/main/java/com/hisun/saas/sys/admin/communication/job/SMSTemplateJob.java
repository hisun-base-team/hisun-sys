package com.hisun.saas.sys.admin.communication.job;

import java.util.List;

import javax.annotation.Resource;

import com.hisun.saas.sys.admin.communication.entity.SMSTemplate;
import com.hisun.saas.sys.admin.communication.service.SMSService;
import com.hisun.saas.sys.admin.communication.service.SMSTemplateService;
import com.hisun.saas.sys.admin.communication.vo.MessageResult;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;

/**
 * <p>Title: SMSTemplateJob.java </p>
 * <p>Package com.hisun.cloud.sys.job </p>
 * <p>Description: 定时更新短信模板状态</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月11日 下午2:12:33 
 * @version 
 */
public class SMSTemplateJob {

	@Resource
	private SMSTemplateService smsTemplateService;
	
	@Resource
	private SMSService smsService;
	
	public void execute(){
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" checkStatus !=:checkStatus " ,"checkStatus","SUCCESS"));
		List<SMSTemplate> smsTemplates = smsTemplateService.list(query,null);
		//smsTemplateService
		//MessageResults messageResults = smsService.getAllTpl();
		for(SMSTemplate template:smsTemplates){
			MessageResult messageResults = smsService.getTpl(Long.valueOf(template.getTplId()));
			template.setCheckStatus(messageResults.getTemplate().getCheck_status());
			smsTemplateService.update(template);
		}
		
	}
}
