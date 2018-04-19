package com.hisun.saas.sys.admin.communication.service;

import java.util.Map;


import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.admin.communication.entity.SMSTemplate;

public interface SMSTemplateService extends BaseService<SMSTemplate, String>{
	
	/**
	 * 根据模板字符串获取模板
	 * @param permission
	 * @param map 模板中的变量
	 * @return
	 */
	public String getTplMessage(String permission,Map<String, String> map);
}
