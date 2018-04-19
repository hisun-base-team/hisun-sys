package com.hisun.saas.sys.admin.communication.service;

import com.hisun.saas.sys.admin.communication.entity.EmailTemplate;
import com.hisun.base.service.BaseService;

import java.util.Map;

/**
 * <p>Title: EmailTemplateService.java </p>
 * <p>Package com.hisun.saas.sys.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月5日 下午4:24:56 
 * @version 
 */
public interface EmailTemplateService extends BaseService<EmailTemplate, String>{

    String getTplMessage(String permission, Map<String, String> paramsMap);

    EmailTemplate getByPermission(String permission);

}
