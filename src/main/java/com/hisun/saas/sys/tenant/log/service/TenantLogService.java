package com.hisun.saas.sys.tenant.log.service;

import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.log.vo.TenantLogVo;

import java.util.Map;

/**
 * <p>Title: TenantLogService.java </p>
 * <p>Package com.hisun.saas.sys.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月21日 上午11:28:44 
 * @version 
 */
public interface TenantLogService extends BaseService<TenantLog, String>{

	public void log(TenantLog log);
	
	public PagerVo<TenantLogVo> selectLog(int pageSize, int pageNum, String property);
	
	public PagerVo<TenantLogVo> selectSecurityLog(int pageSize, int pageNum, String property);
	
	PagerVo<TenantLogVo> searchLogList(int pageSize, int pageNum, String property, String starttime,
								 String starttime2, String userName, short type);

    PagerVo<Map> adminSearchLoginLog(String start, String end, String realname, String email, String ip,int pageNum, int pageSize);

    PagerVo<TenantLogVo> searchOperationLog(int pageSize, int pageNum, String start, String end, String searchContent, String tenantId, String type);
}
