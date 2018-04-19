package com.hisun.saas.sys.admin.communication.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.communication.dao.SMSConfigDao;
import com.hisun.saas.sys.admin.communication.entity.SMSConfig;
import com.hisun.saas.sys.admin.communication.service.SMSConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("SMSConfigService")
public class SMSConfigServiceImpl extends BaseServiceImpl<SMSConfig, String> implements SMSConfigService {

	private SMSConfigDao smsConfigDao;
	
	@Resource
	public void setBaseDao(BaseDao<SMSConfig, String> smsConfigDao) {
		this.baseDao = smsConfigDao;
		this.smsConfigDao = (SMSConfigDao) smsConfigDao;	
	}

}
