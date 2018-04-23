package com.hisun.saas.sys.admin.message.service.impl;

import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.message.dao.OnlineMessageDao;
import com.hisun.saas.sys.admin.message.entity.OnlineMessage;
import com.hisun.saas.sys.admin.message.service.OnlineMessageService;
import com.hisun.saas.sys.admin.message.vo.OnlineMessageData;
import com.hisun.saas.sys.admin.user.dao.UserDao;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: SysLogService.java </p>
 * <p>Package com.hisun.saas.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月21日 上午11:32:59 
 * @version 
 */
@Service
public class OnlineMessageServiceImpl extends BaseServiceImpl<OnlineMessage, String> implements OnlineMessageService {

	private OnlineMessageDao onlineMessageDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private TenantDao tenantDao;
	
	@Override
	@Resource
	public void setBaseDao(BaseDao<OnlineMessage, String> onlineMessageDao) {
		this.baseDao = onlineMessageDao;
		this.onlineMessageDao = (OnlineMessageDao) onlineMessageDao;
	}

	@Override
	public void saveOnlineMessage(OnlineMessageData onlineMessageData) {
		//UserLoginDetails userLoginDetails = Function.getUserLoginDetails();
		
		User createUser = this.userDao.getByPK(onlineMessageData.getUserId());
		Tenant tenant = this.tenantDao.getByPK(onlineMessageData.getTenantId());
		OnlineMessage onlineMessage = new OnlineMessage();
		
		onlineMessage.setCreateDate(new Date());
		onlineMessage.setCreateUserName(createUser.getRealname());
		//onlineMessage.setTenant(tenant);
		onlineMessage.setType(onlineMessageData.getType().getValue());
		onlineMessage.setContent(onlineMessageData.getContent());
		onlineMessage.setTitle(onlineMessageData.getTitle());
		
		this.onlineMessageDao.save(onlineMessage);
	}

	@Override
	public int update(String sql, Map<String, Object> paramMap) {
		return this.onlineMessageDao.update(sql, paramMap);
	}


}
