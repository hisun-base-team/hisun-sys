package com.hisun.saas.sys.admin.message.service;

import com.hisun.saas.sys.admin.message.entity.Message;
import com.hisun.base.service.BaseService;

/**
 * <p>Title: MessageService.java </p>
 * <p>Package com.hisun.cloud.sys.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月29日 上午11:36:31 
 * @version 
 */
public interface MessageService extends BaseService<Message, String>{

	public Message save(String userId,String tenantId);
	
	Message findMessageByUserId(String userId);
}
