package com.hisun.saas.sys.admin.user.service;

import com.hisun.saas.sys.admin.user.entity.Serial;
import com.hisun.base.service.BaseService;

/**
 * <p>Title: SerialService.java </p>
 * <p>Package com.hisun.saas.sys.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月28日 下午3:31:35 
 * @version 
 */
public interface SerialService extends BaseService<Serial, String> {

	Serial findByKey(String key);

	boolean save(Serial entity,String username,String email,String url);
}
