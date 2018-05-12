/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.treeTag;

import com.hisun.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * @author liuzj {279421824@qq.com}
 */
public final class TreeTag extends BodyTagSupport {

	private String text;

	private String id ="";
	//复选的存取值的名称
	private String valueName;
	//是否必填
	private String required="false";

	private String selectClass;
	//调用树实现方法的URL
	private String treeUrl="";
	//是否加载在选择框的树 false为单独使用的树 true为带下拉的树
	private String isSelectTree="false";
	//是否带搜索功能 false为不带搜索 true为带搜索功能
	private String isSearch="false";

	private String radioOrCheckbox="radio";
	/**勾选 checkbox 对于父子节点的关联关系。[setting.check.enable = true 且 setting.check.chkStyle = "checkbox" 时生效]
	 默认值：{ 'Y': 'ps', 'N': 'ps' }
	 JSON 格式说明
	 Y 属性定义 checkbox 被勾选后的情况；
	 N 属性定义 checkbox 取消勾选后的情况；
	 "p" 表示操作会影响父级节点；
	 "s" 表示操作会影响子级节点。
	 请注意大小写，不要改变
	 **/
	private String  chkboxType="";

	//用于存储自定义属性 shortName:aaa
	private String userParameter="";

	//用于捕获节点被点击的事件回调函数
	private String onClick="";

	//动态加载 true为动态加载 false为静态加载
	private String  dynamicLoading="false";
	//Ajax 获取的数据类型。 默认为text
	private String  dataType="json";

	/**Ajax 请求提交的静态参数键值对 Array(String)
	 Array(String) 格式说明
	可以为空[ ]，如果有 key，则必须存在 value。 例如：[key, value]
	JSON 格式说明
	直接用 JSON 格式制作键值对，例如：{ key1:value1, key2:value2 }
	 **/
	private String otherParam="";

	/**
	 * Ajax 的 http 请求模式
	 * 默认值："post"
	 * type = "post" 表示异步加载采用 post 方法请求
	 * type = "get" 表示异步加载采用 get 方法请求
	 */
	private String submitType="post";

	private String token;

	private String defaultvalues="";//默认的值 要有多个必须和key的内容对应  用,分开

	private String defaultkeys="";//默认的知道的相应key值 要有多个必须和值的内容对应  用,分开

	//单击标题是否会影响复选框
	private String checkedByTitle="false";

	//单击标题会选中复选框 但是不会取消选中（主要用于权限树）
	private String checkedAndNoUnCheckedUnByTitle="false";

	//在多选时，是否合并取值 如果当前节点选中且所有子节点选中，则只记录父节点ID 在后面加：1 如果子节点部分选中则在父节点后面加：0  默认人false 为true时合并选取的值
	private String valueMerge = "false";

	//主要用于解决可能选中的数据太多 通过URL传过来可能会有问题  需要用户自己在数据源初始化选中数据 默认为客服端初始化选中数据 为0  为1时数据源初始化数据
	private String initCheckboxValueType= "0" ;

	private String beforeCheck;//用于捕获 勾选 或 取消勾选 之前的事件回调函数，并且根据返回值确定是否允许 勾选 或 取消勾选 如果返回 false，将不会改变勾选状态，并且无法触发 onCheck 事件回调函数

	private String onCheck;//用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数

	private String height;//设置高度 如果没设置则表示为默认高度
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

	public String buildHTML(){
		StringBuffer nodesHtml=new StringBuffer();
		if(isSelectTree.equals("false")){
			if(this.isSearch!=null && this.isSearch.equals("true")) {
				nodesHtml.append("<div class=\"input-append date form_datetime\" >");
				nodesHtml.append("<input type = \"text\" id = \""+id+"_keyword\" placeholder = \"搜索关键字...\" class=\"span6 m-wrap\"");
				nodesHtml.append("style = \"width:175px;height:16px !important\" name = \"keyword\" value = \"\" / >");
				nodesHtml.append("<span class=\"add-on\" onclick = \"clearTreeQuery('"+id+"_keyword','"+dataType+"','"+submitType+"','"+treeUrl+"','"+id+"',setting_"+id+",'"+isSearch+"','"+token+"');\" ><i class=\"icon-remove\" ></i ></span >");
				nodesHtml.append("</div>");
			}
			//单独使用的树
			nodesHtml.append("<div class=\"zTreeDemoBackground\" id=\""+id+"_div\" style=\" overflow: auto;margin: 0px;padding: 0px\">");
			nodesHtml.append("<ul id=\""+id+"\" class=\"ztree\"></ul>");
			nodesHtml.append("</div>");
		}else{
			if(valueName==null || valueName.equals("")){
				valueName = id+"_value";
			}
			nodesHtml.append("<input type=\"hidden\" id=\""+id+"\" name=\""+id+"\" value=\""+ StringUtils.trimNullCharacter2Empty(this.defaultkeys)+"\"/>");
			nodesHtml.append("<input type=\"hidden\" id=\""+id+"_tree_valueName\" name=\""+id+"_tree_valueName\" value=\""+valueName+"\"/>");
			nodesHtml.append("<input type=\"text\" id=\""+valueName+"\" name=\""+valueName+"\" value=\""+StringUtils.trimNullCharacter2Empty(this.defaultvalues)+"\" readonly=\"readonly\"");
			if(required.equals("true")) {
				nodesHtml.append(" required ");
			}
			if(selectClass!=null && !selectClass.equals("")){
				nodesHtml.append("class=\""+selectClass+"\"");
			}else{
				nodesHtml.append("class=\"m-wrap span6\"");
			}
			nodesHtml.append(" style=\"cursor: pointer;");
			if(height!=null && !height.equals("")){
				nodesHtml.append("height:"+height+" !important;");
			}

			nodesHtml.append("\" onclick=\"showTagDivTree('"+id+"_treeSelDiv','"+id+"','"+valueName+"','"+isSearch+"')\"");
			nodesHtml.append(">");
			nodesHtml.append("<div tabindex=\"0\" style=\"border: 1px solid #aaa;position: absolute;z-index: 9999999;display: none;float: left;" +
					"list-style: none;text-shadow: none;\" id=\""+id+"_treeSelDiv\">");//margin-top: -10px;
			if(this.isSearch!=null && this.isSearch.equals("true")) {
				nodesHtml.append("<div class=\"input-append date form_datetime\" id=\"" + id + "_searchDiv\" style=\"z-index: 9999998;margin-bottom:0px\">");
				nodesHtml.append("<input type=\"text\" id=\"" + id + "_tree_keyword\" placeholder=\"搜索关键字...\" style=\"width:240px;height:16px !important\"");
				if (selectClass != null && !selectClass.equals("")) {
					nodesHtml.append("class=\"" + selectClass + "\"");
				} else {
					nodesHtml.append("class=\"m-wrap span6\"");
				}
				nodesHtml.append("/>");
				nodesHtml.append("<span class=\"add-on\" onclick=\"clearTreeQuery('" + id + "_tree_keyword','" + dataType + "','" + submitType + "','" + treeUrl + "','" + id + "',setting_" + id + ",'" + isSearch + "','" + token + "');\"><i class=\"icon-remove\"></i></span>");
				nodesHtml.append("</div>");
			}
			nodesHtml.append("<ul id=\""+id+"_tree\" class=\"ztree\" style=\"background:#FFFFFF !important;height:300px; overflow-x: auto; margin: 0px;padding: 0px; border-top:none;\"></ul>");
			nodesHtml.append("</div>");
		}
		nodesHtml.append("<input type=\"hidden\" id=\""+id+"_tagDefineAtt\" onClickFunc=\""+onClick+"\" radioOrCheckbox=\""+radioOrCheckbox+"\" " +
				"dataType=\""+dataType+"\" submitType=\""+submitType+"\" url=\""+treeUrl+"\" isSearch=\""+isSearch+"\" beforeCheckFunc=\""+beforeCheck+"\"" +
				" onclickFunc=\""+onClick+"\" onCheckFunc=\""+onCheck+"\" token=\""+token+"\" userParameter=\""+userParameter+"\" checkedByTitle=\""+checkedByTitle+"\"" +
				" checkedAndNoUnCheckedUnByTitle=\""+checkedAndNoUnCheckedUnByTitle+"\" valueMerge=\""+valueMerge+"\" initCheckboxValueType=\""+initCheckboxValueType+"\">");
		nodesHtml.append("<script>");
//		if(isSelectTree!=null && isSelectTree.equals("true")) {
//			nodesHtml.append("if(window.document.getElementById(\"" + valueName + "\").offsetWidth!=0){$(\"#" + id + "_treeSelDiv\").css(\"width\",window.document.getElementById(\"" + valueName + "\").offsetWidth-2);}");
//			if(this.isSearch!=null && this.isSearch.equals("true")) {
//				nodesHtml.append("if(window.document.getElementById(\"" + valueName + "\").offsetWidth!=0){$(\"#" + id + "_searchDiv\").css(\"width\", window.document.getElementById(\"" + valueName + "\").offsetWidth-2);}");
//				nodesHtml.append("if(window.document.getElementById(\"" + valueName + "\").offsetWidth!=0){$(\"#" + id + "_tree_keyword\").css(\"width\", window.document.getElementById(\"" + valueName + "\").offsetWidth-30);}");
//			}
//		}
		nodesHtml.append("\n\tvar setting_"+id+" = {");
		if(radioOrCheckbox!=null && radioOrCheckbox.equals("checkbox")) {
			nodesHtml.append("\n\t\tcheck: {");
			nodesHtml.append("\n\t\t\tenable: true,");
			if(chkboxType!=null && !chkboxType.equals("")) {
				nodesHtml.append("\n\t\t\tchkboxType: {"+chkboxType+"}");
			}else{
				nodesHtml.append("\n\t\t\tchkboxType: {\"Y\":\"\", \"N\":\"\"}");
			}
			nodesHtml.append("\n\t\t},");
		}
		nodesHtml.append("\n\t\tview: {");
		nodesHtml.append("\n\t\t\tselectedMulti: false");
		nodesHtml.append("\n\t\t\t},");
		nodesHtml.append("\n\t\tedit: {");
		nodesHtml.append("\n\t\t\tenable: true,");
		nodesHtml.append("\n\t\t\tshowRemoveBtn: false,");
		nodesHtml.append("\n\t\t\tshowRenameBtn: false");
		nodesHtml.append("\n\t\t},");
		nodesHtml.append("\n\t\tdata: {");
		nodesHtml.append("\n\t\t\tsimpleData: {");
		nodesHtml.append("\n\t\t\t\tenable: true");
		nodesHtml.append("\n\t\t\t}");
		nodesHtml.append("\n\t\t},");
		nodesHtml.append("\n\t\tcallback: {");
		//单选使用
		if(radioOrCheckbox!=null && radioOrCheckbox.equals("radio")) {
//			if (onClick != null && !onClick.equals("")) {
//				nodesHtml.append("\n\t\t\tonClick :" + onClick + "");
//			} else {
				nodesHtml.append("\n\t\t\tonClick :onClickBySelectTreeTag,");
//			}
		}
//		else if(radioOrCheckbox!=null && radioOrCheckbox.equals("checkbox")) {
//			if (onClick != null && !onClick.equals("")) {
//				nodesHtml.append("\n\t\t\tonClick :onClickByTreeTag,");
//			}
//		}
		//复选使用
		if(radioOrCheckbox!=null && radioOrCheckbox.equals("checkbox")){
			nodesHtml.append("\n\t\t\tbeforeClick :beforeClickByTreeCheckBox,");
			if(isSelectTree.equals("true")) {
				nodesHtml.append("\n\t\t\tonCheck :onCheckByTreeCheckBox,");
			}else{
				if(onCheck!=null && !onCheck.equals("")) {
					nodesHtml.append("\n\t\t\tonCheck :onCheckByCheckBox,");
				}
			}
			if(StringUtils.isNotEmpty(beforeCheck)) {
				nodesHtml.append("\n\t\t\tbeforeCheck :beforeCheckByTreeTag");
			}
		}
		nodesHtml.append("\n\t\t}");
		nodesHtml.append("\n\t};");
		if(isSelectTree.equals("false")) {
			nodesHtml.append("treeLoadByTag('" + dataType + "','" + submitType + "','" + treeUrl + "','" + id + "',setting_" + id + ",'" + isSearch + "','"+token+"')");
		}else{
			nodesHtml.append("treeLoadByTag('" + dataType + "','" + submitType + "','" + treeUrl + "','" + id + "_tree',setting_" + id + ",'" + isSearch + "','"+token+"')");
		}
		nodesHtml.append("</script>");
		return nodesHtml.toString();
	}

	public void release() {
		super.release();
	}



	public String getUserParameter() {
		return userParameter;
	}

	public void setUserParameter(String userParameter) {
		this.userParameter = userParameter;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTreeUrl() {
		return treeUrl;
	}

	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}

	public String getRadioOrCheckbox() {
		return radioOrCheckbox;
	}

	public void setRadioOrCheckbox(String radioOrCheckbox) {
		this.radioOrCheckbox = radioOrCheckbox;
	}

	public String getChkboxType() {
		return chkboxType;
	}

	public void setChkboxType(String chkboxType) {
		this.chkboxType = chkboxType;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getDynamicLoading() {
		return dynamicLoading;
	}

	public void setDynamicLoading(String dynamicLoading) {
		this.dynamicLoading = dynamicLoading;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public String getIsSelectTree() {
		return isSelectTree;
	}

	public void setIsSelectTree(String isSelectTree) {
		this.isSelectTree = isSelectTree;
	}

	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getSelectClass() {
		return selectClass;
	}

	public void setSelectClass(String selectClass) {
		this.selectClass = selectClass;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDefaultvalues() {
		return defaultvalues;
	}

	public void setDefaultvalues(String defaultvalues) {
		this.defaultvalues = defaultvalues;
	}

	public String getDefaultkeys() {
		return defaultkeys;
	}

	public void setDefaultkeys(String defaultkeys) {
		this.defaultkeys = defaultkeys;
	}

	public String getCheckedByTitle() {
		return checkedByTitle;
	}

	public void setCheckedByTitle(String checkedByTitle) {
		this.checkedByTitle = checkedByTitle;
	}

	public String getCheckedAndNoUnCheckedUnByTitle() {
		return checkedAndNoUnCheckedUnByTitle;
	}

	public void setCheckedAndNoUnCheckedUnByTitle(String checkedAndNoUnCheckedUnByTitle) {
		this.checkedAndNoUnCheckedUnByTitle = checkedAndNoUnCheckedUnByTitle;
	}

	public String getValueMerge() {
		return valueMerge;
	}

	public void setValueMerge(String valueMerge) {
		this.valueMerge = valueMerge;
	}

	public String getInitCheckboxValueType() {
		return initCheckboxValueType;
	}

	public void setInitCheckboxValueType(String initCheckboxValueType) {
		this.initCheckboxValueType = initCheckboxValueType;
	}

	public String getBeforeCheck() {
		return beforeCheck;
	}

	public void setBeforeCheck(String beforeCheck) {
		this.beforeCheck = beforeCheck;
	}

	public String getOnCheck() {
		return onCheck;
	}

	public void setOnCheck(String onCheck) {
		this.onCheck = onCheck;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}