package com.hisun.saas.sys.admin.communication.service.impl;

import com.hisun.saas.sys.admin.communication.dao.SMSTemplateDao;
import com.hisun.saas.sys.admin.communication.entity.SMSTemplate;
import com.hisun.saas.sys.admin.communication.service.SMSTemplateService;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: SMSTemplateServiceImpl.java </p>
 * <p>Package com.hisun.saas.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月5日 上午10:41:01 
 * @version 
 */
@Service
public class SMSTemplateServiceImpl extends BaseServiceImpl<SMSTemplate, String> implements SMSTemplateService {

	private SMSTemplateDao smsTemplateDao;
	
	@Resource
	public void setBaseDao(BaseDao<SMSTemplate, String> smsTemplateDao) {
		this.baseDao = smsTemplateDao;
		this.smsTemplateDao = (SMSTemplateDao) smsTemplateDao;	
	}

	@Override
	public String getTplMessage(String permission, Map<String, String> paramsMap) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(
				" SMSTemplate.permission = :permission", "permission",
				permission));
		List<SMSTemplate> templates = smsTemplateDao.list(query, null);

		if (templates.size()==0) {
			return null;
		} else {
			String message = templates.get(0).getTplContent();
			for (Map.Entry<String, String> param : paramsMap.entrySet()) {
				message = message.replace("#"+param.getKey()+"#", param.getValue());
			}
			return message;
		}
	}

}
