package com.hisun.saas.sys.admin.report.service.impl;

import com.hisun.saas.sys.admin.report.entity.JasperReportTemp;
import com.hisun.saas.sys.admin.report.service.JasperReportTempService;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.report.dao.JasperReportTempDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
@Service
public class JasperReportTempServiceImpl extends BaseServiceImpl<JasperReportTemp, String> implements JasperReportTempService {

	private JasperReportTempDao jasperReportTempDao;
	
	@Resource
	@Override
	public void setBaseDao(BaseDao<JasperReportTemp, String> baseDao) {
		this.baseDao = baseDao;
		this.jasperReportTempDao = (JasperReportTempDao) baseDao;
	}


	@Override
	public JasperReportTemp getByIdentCode(String identCode)  throws GenericException {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" identCode = :identCode", "identCode", identCode));
		List<JasperReportTemp> reportTempList = this.jasperReportTempDao.list(query, null);
		if(reportTempList.size() == 1){
			return reportTempList.get(0);
		}else{
			throw new GenericException("查询不到唯一模版");
		}
	}
}
