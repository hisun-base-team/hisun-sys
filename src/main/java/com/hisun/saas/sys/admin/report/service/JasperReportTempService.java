package com.hisun.saas.sys.admin.report.service;

import com.hisun.saas.sys.admin.report.entity.JasperReportTemp;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.BaseService;

/**
 * 
 *<p>类名称：</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：jamin30
 *@创建时间：2015年10月22日
 *@创建人联系方式：
 *@version
 */
public interface JasperReportTempService extends BaseService<JasperReportTemp, String>{

    JasperReportTemp getByIdentCode(String identCode)  throws GenericException;

}
