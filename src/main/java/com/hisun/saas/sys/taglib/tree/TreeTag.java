/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.tree;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public final class TreeTag extends BodyTagSupport {

	private String text;

	private String divId;

	private String dataSource;//实现SanTreeDataSourceInterface接口的类

	private String contextPath;

	private String checkboxDisplay;

	private String radioOrCheckbox;

	private String parentRadioEnable;

	private String parentCheckboxEnable;

	private String parentChildIsolate;

	private String userParameter;

	private String defaultOnclick;

	private String aclickDefault;

	private String checkboxclickDefault;

	private String imgclickDefault;

	private String dblonclick;

	private String auto_dynamic_load;

	private String firstSelectedByTitle;

	private String openOnclick;

	//选中上级节点时，下级节点是否要自动全部选中，是或否 true,false
	private String parentSelectedWithChild="true";
	//取消上级节点的选中状态时，下级节点是否要自动全部取消，是或否 true,false
	private String parentClearWithChild="true";
	//选中下级节点时，如果下级同级节点已全部都为选中状态，其上级是否自动选中，是或否 true,false
	private String lowerSelectedAllWithSuperior="true";
	//取消下级节点的选中状态时，其上级节点选中状态是否要自动取消，是或否 true,false
	private String lowerClearWithSuperior="false";
	//取消下级节点的选中状态时，其上级节点不自动取消的情况下，如果下级同级节点已全部为未选中状态，其上级是否自动取消，是或否 true,false
	private String lowerClearAllWithSuperior="true";
	//配置下级节点选中父节点是否。是或否 true,false
	private String lowerSelectedWithSuperior="false";

	/*
	树的操作类型空为默认操作方式;
	1为标题第一次单击复选框选中,第二次展开，第三次收缩,
	2当节点没有子节点时,第一次单击复选框选中、每二次清除选择
	*/
	private String type;

	//单击标题是否会影响复选框
	private String seletedCheckboxByTitle="false";
	//单击复选框，标题得到焦点
	private String selectedTitleByCheckbox="false";
	//是否对树进行初始化
	private String isInit;

	private String expandByCheckbox="1";

	public String getExpandByCheckbox() {
		return expandByCheckbox;
	}

	public void setExpandByCheckbox(String expandByCheckbox) {
		this.expandByCheckbox = expandByCheckbox;
	}

	public String getIsInit() {
		if(this.isInit==null || this.isInit.equals("")){
			return "true";
		}else{
			return isInit;
		}
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public String getDefaultOnclick() {
		if(this.defaultOnclick==null){
			return "";
		}else{
			return defaultOnclick;
		}
	}

	public void setDefaultOnclick(String defaultOnclick) {
		this.defaultOnclick = defaultOnclick;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getUserParameter() {
		return userParameter;
	}

	public void setUserParameter(String userParameter) {
		this.userParameter = userParameter;
	}

	public String getParentCheckboxEnable() {
		return parentCheckboxEnable;
	}

	public void setParentCheckboxEnable(String parentCheckboxEnable) {
		this.parentCheckboxEnable = parentCheckboxEnable;
	}

	public String getParentChildIsolate() {
		return parentChildIsolate;
	}

	public void setParentChildIsolate(String parentChildIsolate) {
		this.parentChildIsolate = parentChildIsolate;
	}

	public String getParentRadioEnable() {
		return this.parentRadioEnable;
	}

	public void setParentRadioEnable(String parentRadioEnable) {
		this.parentRadioEnable = parentRadioEnable;
	}

	public String getRadioOrCheckbox() {
		return radioOrCheckbox;
	}

	public void setRadioOrCheckbox(String radioOrCheckbox) {
		this.radioOrCheckbox = radioOrCheckbox;
	}

	public String getCheckboxDisplay() {
		return checkboxDisplay;
	}

	public void setCheckboxDisplay(String checkDisplay) {
		this.checkboxDisplay = checkDisplay;
	}

	public String getDataSource(){
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDivId() {
		return this.divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	//初始化数据
	public TreeTag() {
		super();

	}

	private String buildHTML() {
		Tree tree =new Tree();

//			Tree tree=new Tree(this.divId,this.dataSource,this.checkboxDisplay,
//					this.radioOrCheckbox,this.parentRadioEnable,this.parentCheckboxEnable,
//					this.parentChildIsolate,this.pageContext,this.userParameter);

		tree.setDivId(this.divId);//设置保存节点的div的id
		tree.setDataSource(this.dataSource);//设置树的数据源
		tree.setCheckboxDisplay(this.checkboxDisplay);//设置是否显示选择框对象
		tree.setRadioOrCheckbox(this.radioOrCheckbox);//设置是单选 还是 复选
		tree.setParentRadioEnable(this.parentRadioEnable);//单选情况下父节点是否可选 true可选 false不可选
		tree.setParentCheckboxEnable(this.parentCheckboxEnable);//复选情况下父节点是否可选 true可选 false不可选
			/*
			  设置为 true 选择父节点时子节点全部选中,取消时全部取消
			  子节点部分选中时，父节点选中变灰（灰勾）
			  设置为 false 父节点子和子节点可以分开选中，相互不影响
			*/
//		HttpServletRequest httpRequest = (HttpServletRequest)this.pageContext.getRequest();
		tree.setParentChildIsolate(this.parentChildIsolate);
		tree.setPageContext(this.pageContext);//设置页面上下文对象
		tree.setServletContext(this.pageContext.getServletContext());//设置servlet上下文对象
		tree.setContextPath("");//设置系统路径
		tree.setUserParameter(this.userParameter);//设置用户自定义使用的参数
		tree.setDefaultOnclick(this.defaultOnclick);//设置默认的单击事件函数
		tree.setRequest(this.pageContext.getRequest());
		tree.setIsInit(this.getIsInit());//是否在加载树时对他进行初始化
		tree.setAClick(this.getAclickDefault());
		tree.setCheckBoxClick(this.getCheckboxclickDefault());
		tree.setImgClick(this.getImgclickDefault());
		tree.setDblonclick(this.getDblonclick());
		tree.setAuto_dynamic_load(this.getAuto_dynamic_load());
		tree.setSeletedCheckboxByTitle(this.getSeletedCheckboxByTitle());
		tree.setFirstSelectedByTitle(this.getFirstSelectedByTitle());
		tree.setSelectedTitleByCheckbox(this.getSelectedTitleByCheckbox());
		tree.setType(this.getType());
		tree.setOpenOnclick(this.getOpenOnclick());
		tree.setExpandByCheckbox(this.getExpandByCheckbox());

		tree.setParentSelectedWithChild(this.getParentSelectedWithChild());
		tree.setParentClearWithChild(this.getParentClearWithChild());
		tree.setLowerSelectedAllWithSuperior(this.getLowerSelectedAllWithSuperior());
		tree.setLowerClearWithSuperior(this.getLowerClearWithSuperior());
		tree.setLowerClearAllWithSuperior(this.getLowerClearAllWithSuperior());
		tree.setLowerSelectedWithSuperior(this.getLowerSelectedWithSuperior());


//		StringBuffer html = new StringBuffer();
//		html.append("");

		return tree.getTreeHtml().toString();
	}


	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() throws JspException {
		if (bodyContent != null) {
			String value = bodyContent.getString().trim();
			if (value.length() > 0)
				text = value;
		}
		return 0;
	}

	public int doEndTag() throws JspException {
		try{
			super.pageContext.getOut().print(this.buildHTML());
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

	public void release() {
		super.release();
	}

	public String getAclickDefault() {
		return aclickDefault;
	}

	public void setAclickDefault(String aclickDefault) {
		this.aclickDefault = aclickDefault;
	}

	public String getCheckboxclickDefault() {
		return checkboxclickDefault;
	}

	public void setCheckboxclickDefault(String checkboxclickDefault) {
		this.checkboxclickDefault = checkboxclickDefault;
	}

	public String getImgclickDefault() {
		return imgclickDefault;
	}

	public void setImgclickDefault(String imgclickDefault) {
		this.imgclickDefault = imgclickDefault;
	}

	public String getDblonclick() {
		return dblonclick;
	}

	public void setDblonclick(String dblonclick) {
		this.dblonclick = dblonclick;
	}

	public String getAuto_dynamic_load() {
		if(this.auto_dynamic_load!=null && this.auto_dynamic_load.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setAuto_dynamic_load(String auto_dynamic_load) {
		this.auto_dynamic_load = auto_dynamic_load;
	}

	public String getSeletedCheckboxByTitle() {
		if(this.seletedCheckboxByTitle!=null && this.seletedCheckboxByTitle.equals("true")){
			return "1";
		}else{
			return "0";
		}
		//return seletedCheckboxByTitle;
	}

	public void setSeletedCheckboxByTitle(String seletedCheckboxByTitle) {
		this.seletedCheckboxByTitle = seletedCheckboxByTitle;
	}

	public String getFirstSelectedByTitle() {
		return firstSelectedByTitle;
	}

	public void setFirstSelectedByTitle(String firstSelectedByTitle) {
		this.firstSelectedByTitle = firstSelectedByTitle;
	}

	public String getSelectedTitleByCheckbox() {
		return selectedTitleByCheckbox;
	}

	public void setSelectedTitleByCheckbox(String selectedTitleByCheckbox) {
		this.selectedTitleByCheckbox = selectedTitleByCheckbox;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOpenOnclick() {
		return openOnclick;
	}

	public void setOpenOnclick(String openOnclick) {
		this.openOnclick = openOnclick;
	}

	public String getParentSelectedWithChild() {
		if(this.parentSelectedWithChild.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setParentSelectedWithChild(String parentSelectedWithChild) {
		this.parentSelectedWithChild = parentSelectedWithChild;
	}

	public String getParentClearWithChild() {
		if(this.parentClearWithChild.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setParentClearWithChild(String parentClearWithChild) {
		this.parentClearWithChild = parentClearWithChild;
	}

	public String getLowerSelectedAllWithSuperior() {
		if(this.lowerSelectedAllWithSuperior.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setLowerSelectedAllWithSuperior(String lowerSelectedAllWithSuperior) {
		this.lowerSelectedAllWithSuperior = lowerSelectedAllWithSuperior;
	}

	public String getLowerClearWithSuperior() {
		if(this.lowerClearWithSuperior.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setLowerClearWithSuperior(String lowerClearWithSuperior) {
		this.lowerClearWithSuperior=lowerClearWithSuperior;
	}

	public String getLowerClearAllWithSuperior() {
		if(this.lowerClearAllWithSuperior.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setLowerClearAllWithSuperior(String lowerClearAllWithSuperior) {
		this.lowerClearAllWithSuperior = lowerClearAllWithSuperior;
	}

	public String getLowerSelectedWithSuperior() {
		if(this.lowerSelectedWithSuperior.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setLowerSelectedWithSuperior(String lowerSelectedWithSuperior) {
		this.lowerSelectedWithSuperior = lowerSelectedWithSuperior;
	}

}