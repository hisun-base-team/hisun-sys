package com.hisun.saas.sys.admin.communication.service;

import com.hisun.saas.sys.admin.communication.entity.MailConfig;
import com.hisun.base.service.BaseService;

public interface MailConfigService extends BaseService<MailConfig, String>{
	
	/**
	 * 获取启用的邮件配置
	 * @return
	 */
	MailConfig getMailConfig(Boolean type);
}
