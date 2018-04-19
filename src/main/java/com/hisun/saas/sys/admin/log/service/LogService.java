package com.hisun.saas.sys.admin.log.service;

import com.hisun.saas.sys.admin.log.entity.Log;
import com.hisun.saas.sys.admin.log.vo.LogVo;
import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;

/**
 * <p>Title: LogService.java </p>
 * <p>Package com.hisun.cloud.sys.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月21日 上午11:28:44 
 * @version 
 */
public interface LogService  extends BaseService<Log, String> {

	public void log(Log log);
	
	public PagerVo<LogVo> selectLog(int pageSize, int pageNum, String property);
	
	PagerVo<LogVo> searchOwnSecurityLogList(int pageSize, int pageNum, String start,
											String end, String searchContent);

	PagerVo<LogVo> searchAllSecurityLogList(int pageSize, int pageNum, String start,
											String end, String searchContent);

	PagerVo<LogVo> searchLogList(int pageSize, int pageNum,String property, String start,
										 String end, String userName,String type);
}
