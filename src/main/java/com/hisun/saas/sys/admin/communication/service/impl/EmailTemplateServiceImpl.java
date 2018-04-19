package com.hisun.saas.sys.admin.communication.service.impl;

import com.hisun.saas.sys.admin.communication.entity.EmailTemplate;
import com.hisun.saas.sys.admin.communication.service.EmailTemplateService;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.communication.dao.EmailTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: EmailTemplateServiceImpl.java </p>
 * <p>Package com.hisun.cloud.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月5日 下午4:25:30 
 * @version 
 */
@Service
public class EmailTemplateServiceImpl extends BaseServiceImpl<EmailTemplate, String> implements EmailTemplateService {

	private EmailTemplateDao emailTemplateDao;
	
	@Resource
	public void setBaseDao(BaseDao<EmailTemplate, String> emailTemplateDao) {
		this.baseDao = emailTemplateDao;
		this.emailTemplateDao = (EmailTemplateDao) emailTemplateDao;	
	}

	@Override
	public String getTplMessage(String permission, Map<String, String> paramsMap) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(
				" EmailTemplate.permission = :permission", "permission",
				permission));
		List<EmailTemplate> templates = emailTemplateDao.list(query, null);

		if (templates.size()==0) {
			return null;
		} else {
			String message = templates.get(0).getTplContent();
			for (Map.Entry<String, String> param : paramsMap.entrySet()) {
				message = message.replace("%" + param.getKey() + "%", param.getValue());
			}
			return message;
		}
	}

	@Override
	public EmailTemplate getByPermission(String permission) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(
				" EmailTemplate.permission = :permission", "permission",
				permission));
		List<EmailTemplate> templates = emailTemplateDao.list(query, null,1,1);

		if (templates.size()==0) {
			return null;
		} else {
			return templates.get(0);
		}
	}
}
