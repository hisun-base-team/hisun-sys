package com.hisun.saas.sys.taglib.selectTag;


import com.hisun.saas.sys.taglib.selectTag.vo.SelectNode;
import com.hisun.saas.sys.taglib.util.Attribute;
import com.hisun.saas.sys.taglib.util.Attributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import java.util.List;


public abstract class AbstractSelectObject implements SelectDataSource {

	private ServletContext servletContext;

	private ServletRequest request;

	private Attributes userParameter = new Attributes();
	/**
	 * 设置用户参数。会被自动分解为“属性集合”对象。
	 * @param userParameter String 用户参数（在标签中定义的值）格式为“name:value;name1:value1”
	 */
	public void setUserParameter(String userParameter){
		this.userParameter.addAttributes(userParameter);
	}

	public void setServletContext(ServletContext servletContext){
		this.servletContext=servletContext;
	}

	/**
	 * @return the servletContext
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setRequest(ServletRequest request){
		this.request=request;
	}

	protected  Object getBean(String name){
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
		Object obj = ctx.getBean(name);
		return obj;
	}

	protected  Object getBeanByServletContext(String name){
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		Object obj = ctx.getBean(name);
		return obj;
	}

	protected ServletRequest getRequest(){
		return this.request;
	}

	protected String getUserParameter(){
		return this.userParameter.toString();
	}
	/**
	 * 根据名称获取用户参数中的指定参数值。
	 * @param name
	 * @return
	 */
	protected String getUserParameterValue(String name){
		Attribute attr = this.userParameter.get(name);
		if (attr == null){
			return null;
		}else{
			return attr.getStringValue();
		}
	}

	protected int getuserParameterCount(){
		return this.userParameter.size();
	}

	protected String getUserParameterValue(int index){
		Attribute attr = this.userParameter.get(index);
		if (attr == null){
			return null;
		}else{
			return attr.getStringValue();
		}
	}


	/**
	 * 获取当前树的第一次加载时的节点数据。如果是第一级节点，则其父
	 */
	public abstract List<SelectNode> getDataOptions() throws Exception;
}
