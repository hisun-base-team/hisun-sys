package com.hisun.saas.sys.admin.communication.service.impl;

import com.hisun.saas.sys.admin.communication.dao.MailConfigDao;
import com.hisun.saas.sys.admin.communication.service.MailConfigService;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.communication.entity.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("mailConfigService")
public class MailConfigServiceImpl extends BaseServiceImpl<MailConfig, String> implements MailConfigService {

	private MailConfigDao mailConfigDao;
	
	@Resource
	public void setBaseDao(BaseDao<MailConfig, String> mailConfigDao) {
		this.baseDao = mailConfigDao;
		this.mailConfigDao = (MailConfigDao) mailConfigDao;
	}

	@Override
	public MailConfig getMailConfig(Boolean type) {
		CommonConditionQuery query = new CommonConditionQuery();
		/*query.add(CommonRestrictions.and(" MailConfig.status = :status",
				"status", Boolean.TRUE));*/
		query.add(CommonRestrictions.and(" MailConfig.type = :type",
				"type", type));
		List<MailConfig> configs = mailConfigDao.list(query, null);
		if (configs.size() == 0) {
			return null;
		}
		return configs.get(0);
	}

}
