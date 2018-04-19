package com.hisun.saas.sys.taglib.tree;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
public interface TreeDataSource {
	
	List getChildrenNodes(String parentKey, String parentText) throws Exception;
	List getNodes() throws Exception;
	void setServletContext(ServletContext servletContext);
	void setRequest(ServletRequest request);
	void setUserParameter(String userParameter);
	String getValueByKeys(String codes, String userParameter, String chooseType, String unitCode) throws Exception;
	String checkTextEqual(String text, String userParameter, String chooseType, String unitCode) throws Exception;
	List getKeysByQueryInfo(String text, String userParameter, String unitCode) throws Exception;
	String getParamentCodesByKeys(String codes, String userParameter, String dicCode) throws Exception;
}
  