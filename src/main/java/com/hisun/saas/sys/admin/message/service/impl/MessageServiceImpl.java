package com.hisun.saas.sys.admin.message.service.impl;

import com.hisun.saas.sys.admin.message.dao.MessageDao;
import com.hisun.saas.sys.admin.message.service.MessageService;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.user.dao.UserDao;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.message.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: SysLogService.java </p>
 * <p>Package com.hisun.cloud.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月21日 上午11:32:59 
 * @version 
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

	private MessageDao messageDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private TenantDao tenantDao;
	
	@Override
	@Resource
	public void setBaseDao(BaseDao<Message, String> messageDao) {
		this.baseDao = messageDao;
		this.messageDao = (MessageDao) messageDao;
	}

	@Override
	public Message save(String userId, String tenantId) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" Message.createUser.id =:userId ",
				"userId", userId));
		query.add(CommonRestrictions.and(" Message.tenant.id =:tenantId ",
				"tenantId", tenantId));
		List<Message> messages = this.messageDao.list(query, null);
		Message message = new Message();
		if (messages.size() > 0) {
			return messages.get(0);
		} else {
			User createUser = this.userDao.getByPK(userId);
			Tenant tenant = this.tenantDao.getByPK(tenantId);
			message.setCreateDate(new Date());
			message.setCreateUserName(createUser.getUsername());
			message.setTenant(tenant);
			this.messageDao.save(message);
		}
		return message;
	}

	@Override
	public Message findMessageByUserId(String userId) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" createUser.id = :id",
				"id", userId));
		List<Message> messageList = this.messageDao.list(query, null);
		if (messageList.size() == 0) {
			return null;
		}
		return messageList.get(0);
	}


}
