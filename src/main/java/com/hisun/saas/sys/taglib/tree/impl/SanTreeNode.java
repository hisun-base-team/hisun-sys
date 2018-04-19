package com.hisun.saas.sys.taglib.tree.impl;

import com.hisun.saas.sys.taglib.tree.SanTreeNodeInterface;

public class SanTreeNode implements SanTreeNodeInterface {
	private String text="";//节点文本
	private String href="javascript:{}";//节点链接
	private String target="";//链接目标
	private String jsFunction="javascript:{}";//链接函数
	private String helpCode="";//助记码
	private String key="";//节点键值
	private String parentKey="";//父节点键值
	private String inputValue="";//复选框值
	private String checkboxJs="";//checkbox的单击函数
	private int dynamicLoading=0;//是否动态加载 1 为动态加载 0为否
	private String defaultSelected=null;//是否默认选中
	private String enable="true";//复选是否可用 true可用 false 不可用
	private String dynamicAttri="";//动态扩展属性
	private String grayImg="false";//true 为显示灰色复选框图片,false为不显示灰色复选框图片
	private String fullName="";//节点全称

	private String customContent="";//自定义内容
	private String fullUrl="";//全路径
	private String forShort="";//节点标题简称
	private String helpCode2="";//助记码2

	private String radioEnable="true"; //在单选的情况下默认可选

	private String isolateGrayImg="true";//受子节点影响的灰勾

	private String customAttribute="";//自定义属性

	private String checkboxDisplay="true"; //true为显示 false为不显示

	//节点操作图片 收缩，展开，没有子节点
	private String nodeOperateImg="";
	//节点是否展开
	private String nodeOpen="display:none";
	//设置节点是否处于展开状态属性值
	private String open="no";
	//设置层次线条图片
	private String cengImg="";

	private String checked="";

	private String checkboxName="";

	private String titleTip;

	//(目录未使用)
	private String childSelectedStatus;//0 为没有选中,1 全部选中,2 部份选中

	//是否为当前节点
	private String isCurrentNode="false";

	//用于存储子节点的选中状态值
	private String childSelAll="0";//0为未全部选中,1为全部选中

	/**
	 * @return the childSelAll
	 */
	public String getChildSelAll() {
		return childSelAll;
	}

	/**
	 * @param childSelAll the childSelAll to set
	 */
	public void setChildSelAll(String childSelAll) {
		this.childSelAll = childSelAll;
	}

	public String getIsCurrentNode() {
		return isCurrentNode;
	}

	public void setIsCurrentNode(String isCurrentNode) {
		this.isCurrentNode = isCurrentNode;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getCengImg() {
		return cengImg;
	}

	public void setCengImg(String cengImg) {
		this.cengImg = cengImg;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getNodeOpen() {
		return nodeOpen;
	}

	public void setNodeOpen(String nodeOpen) {
		this.nodeOpen = nodeOpen;
	}

	public String getNodeOperateImg() {
		return nodeOperateImg;
	}

	public void setNodeOperateImg(String nodeOperateImg) {
		this.nodeOperateImg = nodeOperateImg;
	}

	////////////////////////////////////////////////////////////
	public String getCheckboxDisplay() {
		if(this.checkboxDisplay.equals("true")){
			return "";
		}else{
			return "display:none";
		}
	}

	public void setCheckboxDisplay(String checkboxDisplay) {
		this.checkboxDisplay = checkboxDisplay;
	}

	public String getCustomAttribute() {
		if(this.customAttribute==null){
			return "";
		}else{
			return customAttribute;
		}
	}

	public void setCustomAttribute(String customAttribute) {
		this.customAttribute = customAttribute;
	}

	public String getIsolateGrayImg() {
		if(this.isolateGrayImg.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setIsolateGrayImg(String isolateGrayImg) {
		this.isolateGrayImg = isolateGrayImg;
	}

	public String getRadioEnable() {
		if(this.radioEnable.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setRadioEnable(String radioEnable) {
		this.radioEnable = radioEnable;
	}

	public String getFullName() {
		return fullName;

	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCheckboxJs() {
		return checkboxJs;
	}

	public void setCheckboxJs(String checkboxJs) {
		this.checkboxJs = checkboxJs;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable=enable;

	}

	public String getInputValue() {
		return inputValue;
	}

	public SanTreeNode(){

	}

	/*
		 String text;//节点文本
		 String href;//节点链接
		 String target;//链接目标
		 String jsFunction;//链接函数
		 String helpCode;//助记码
		 String key;//节点键值
		 String parentKey;//父节点键值 最高层父key,必需为0
		 int dynamicLoading;//是否动态加载 1 为动态加载 0为否
		 String inputValue;//复选框值
		 String checkboxJs;
		 String defaultSelected;//是否默认选中
		 String enable //复选是否可用 true可用 false 不可用
	*/
	public SanTreeNode(String text,String href,String target,String jsFunction,String helpCode,
					   String key,String parentKey,int dynamicLoading,String inputValue,String checkboxJs,String defaultSelected,String enable){
		this.text=text;
		this.href=href;
		this.target=target;
		this.jsFunction=jsFunction;
		this.helpCode=helpCode;
		this.key=key;
		this.parentKey=parentKey;
		this.dynamicLoading=dynamicLoading;
		this.inputValue=inputValue;
		this.checkboxJs=checkboxJs;
		this.defaultSelected=defaultSelected;
		this.enable=enable;
	}

	/*
	 String text;//节点文本
	 String href;//节点链接
	 String target;//链接目标
	 String jsFunction;//链接函数
	 String helpCode;//助记码
	 String key;//节点键值
	 String parentKey;//父节点键值 最高层父key,必需为0
	 int dynamicLoading;//是否动态加载 1 为动态加载 0为否
	 String inputValue;//复选框值
	 String defaultSelected;//是否默认选中
*/
	public SanTreeNode(String text,String href,String target,String jsFunction,String helpCode,
					   String key,String parentKey,int dynamicLoading,String inputValue,String defaultSelected){
		this.text=text;
		this.href=href;
		this.target=target;
		this.jsFunction=jsFunction;
		this.helpCode=helpCode;
		this.key=key;
		this.parentKey=parentKey;
		this.dynamicLoading=dynamicLoading;
		this.inputValue=inputValue;
		this.defaultSelected=defaultSelected;
	}

	/*
	 String text;//节点文本
	 String href;//节点链接
	 String target;//链接目标
	 String jsFunction;//链接函数
	 String helpCode;//助记码
	 String key;//节点键值
	 String parentKey;//父节点键值 最高层父key,必需为0
	 int dynamicLoading;//是否动态加载 1 为动态加载 0为否
	 String inputValue;//复选框值
	 String checkboxJs;//checkbox中的js方法
	 String defaultSelected;//是否默认选中
	 String enable //复选是否可用 true可用 false 不可用
	 String  dynamicAttri//动态扩展属性
	 String grayImg; //true 为显示灰色复选框图片,false为不显示灰色复选框图片

	 version 1.0
	 */
	public SanTreeNode(String text,String href,String target,String jsFunction,String helpCode,
					   String key,String parentKey,int dynamicLoading,String inputValue,String checkboxJs,
					   String defaultSelected,String enable,String dynamicAttri,String grayImg){
		this.text=text;
		this.href=href;
		this.target=target;
		this.jsFunction=jsFunction;
		this.helpCode=helpCode;
		this.key=key;
		this.parentKey=parentKey;
		this.dynamicLoading=dynamicLoading;
		this.inputValue=inputValue;
		this.checkboxJs=checkboxJs;
		this.defaultSelected=defaultSelected;
		this.enable=enable;
		this.dynamicAttri=dynamicAttri;
		this.grayImg=grayImg;
	}
	/*
	 String text;//节点文本
	 String href;//节点链接
	 String target;//链接目标
	 String jsFunction;//链接函数
	 String helpCode;//助记码
	 String key;//节点键值
	 String parentKey;//父节点键值 最高层父key,必需为0
	 int dynamicLoading;//是否动态加载 1 为动态加载 0为否
	 String inputValue;//复选框值
	 String checkboxJs;//checkbox中的js方法
	 String defaultSelected;//是否默认选中
	 String enable //复选是否可用 true可用 false 不可用
	 String  dynamicAttri//动态扩展属性
	 String grayImg; //true 为显示灰色复选框图片,false为不显示灰色复选框图片
	 String fullName;//节点全称
	 version 1.1 
	 */
	public SanTreeNode(String text,String href,String target,String jsFunction,String helpCode,
					   String key,String parentKey,int dynamicLoading,String inputValue,String checkboxJs,
					   String defaultSelected,String enable,String dynamicAttri,String grayImg,String fullName){
		this.text=text;
		this.href=href;
		this.target=target;
		this.jsFunction=jsFunction;
		this.helpCode=helpCode;
		this.key=key;
		this.parentKey=parentKey;
		this.dynamicLoading=dynamicLoading;
		this.inputValue=inputValue;
		this.checkboxJs=checkboxJs;
		this.defaultSelected=defaultSelected;
		this.enable=enable;
		this.dynamicAttri=dynamicAttri;
		this.grayImg=grayImg;
		this.fullName=fullName;
	}
	public String getDefaultSelected() {
		if(this.defaultSelected==null){
			return "-1";
		}else if(this.defaultSelected.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setDefaultSelected(String defaultSelected) {
		this.defaultSelected = defaultSelected;
	}

	public String getHelpCode() {
		return helpCode;
	}

	public String getHref() {
		if(this.href==null || this.href.equals("")){
			return "javascript:{}";
		}else{
			return href;
		}
	}

	public String getJsFunction() {
		if(this.jsFunction==null || this.jsFunction.equals("")){
			return "javascript:{}";
		}else{
			return jsFunction;
		}
	}

	public String getKey() {
		return key;
	}

	public String getParentKey() {
		return parentKey;
	}

	public String getTarget() {
		return target;
	}

	public String getText() {
		return text;
	}

	public int getDynamicLoading() {
		return dynamicLoading;
	}

	public void setHelpCode(String helpCode){
		this.helpCode=helpCode;
	}

	public void setHref(String href) {
		this.href=href;
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction=jsFunction;

	}

	public void setKey(String key) {
		this.key=key;

	}

	public void setParentKey(String parentKey) {
		this.parentKey=parentKey;

	}

	public void setTarget(String target) {
		this.target=target;

	}

	public void setText(String text) {
		this.text=text;

	}

	public void setDynamicLoading(int DynamicLoading) {
		this.dynamicLoading=DynamicLoading;

	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public String geInputValue() {
		return this.inputValue;
	}

	public String getGrayImg() {
		if(this.grayImg.equals("true")){
			return "1";
		}else{
			return "0";
		}
	}

	public void setGrayImg(String grayImg) {
		this.grayImg=grayImg;

	}

	public String getDynamicAttri() {

		return this.dynamicAttri;
	}

	public void setDynamicAttri(String dynamicAttri) {
		this.dynamicAttri=dynamicAttri;
	}

	public String getCustomContent() {
		return this.customContent;
	}

	public String getForShort() {
		return this.forShort;
	}

	public String getFullUrl() {
		return this.fullUrl;
	}

	public String getHelpCode2() {
		return this.helpCode2;
	}

	public void setCustomContent(String customContent) {
		this.customContent=customContent;
	}

	public void setForShort(String forShort) {
		this.forShort=forShort;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl=fullUrl;
	}

	public void setHelpCode2(String helpCode2) {
		this.helpCode2=helpCode2;
	}

	public String getCheckboxName() {
		return checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	public String getTitleTip() {
		return titleTip;
	}

	public void setTitleTip(String titleTip) {
		this.titleTip = titleTip;
	}

	public String getChildSelectedStatus() {
		if(childSelectedStatus==null){
			return "";
		}else{
			return childSelectedStatus;
		}
	}

	public void setChildSelectedStatus(String childSelectedStatus) {
		this.childSelectedStatus = childSelectedStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SanTreeNode other = (SanTreeNode) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}


