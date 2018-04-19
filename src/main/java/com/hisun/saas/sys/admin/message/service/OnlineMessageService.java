package com.hisun.saas.sys.admin.message.service;

import com.hisun.saas.sys.admin.message.entity.OnlineMessage;
import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.admin.message.vo.OnlineMessageData;

import java.util.Map;

/**
 * <p>Title: OnlineMessageService.java </p>
 * <p>Package com.hisun.cloud.sys.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月29日 上午10:43:56 
 * @version 
 */
public interface OnlineMessageService extends BaseService<OnlineMessage, String>{

	public void saveOnlineMessage(OnlineMessageData onlineMessageData);
	
	public int update(String sql, Map<String, Object> paramMap);  
}
