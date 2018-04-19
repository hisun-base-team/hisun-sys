package com.hisun.saas.sys.admin.communication.dao.impl;

import com.hisun.saas.sys.admin.communication.entity.SMSConfig;
import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.saas.sys.admin.communication.dao.SMSConfigDao;

import org.springframework.stereotype.Repository;

@Repository("smsConfigDao")
public class SMSConfigDaoImpl extends BaseDaoImpl<SMSConfig, String> implements SMSConfigDao{

}
