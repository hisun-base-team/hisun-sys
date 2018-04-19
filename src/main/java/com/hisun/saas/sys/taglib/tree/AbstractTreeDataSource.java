/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.tree;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

import com.hisun.saas.sys.taglib.util.Attribute;
import com.hisun.saas.sys.taglib.util.Attributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



public abstract class AbstractTreeDataSource implements TreeDataSource {

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

	public abstract List<TreeNodeImpl> getChildrenNodes(String parentKey, String parentText) throws Exception;
	/**
	 * 获取当前树的第一次加载时的节点数据。如果是第一级节点，则其父
	 */
	public abstract List<TreeNodeImpl> getNodes() throws Exception;
	/**
	 * 实现一个空的根据codes取得value的方法
	 */
	public  String getValueByKeys(String codes,String userParameter,String chooseType,String unitCode) throws Exception{
		return "";
	}
	/**
	 * 根据输入内容得到模糊匹配的数据
	 */
	public  String checkTextEqual(String text,String userParameter,String chooseType,String unitCod) throws Exception{
		return "";
	}
	/**
	 * 动态加载中根据查询条件得到一组codes
	 */
	public  List getKeysByQueryInfo(String codes,String userParameter,String unitCode) throws Exception{
		return null;
	}
	/**
	 * 动态加载中根据codes得到一组到最高节点的codes    code,code
	 */
	public String getParamentCodesByKeys(String codes,String userParameter,String dicCode) throws Exception{
		return "";
	}
}
