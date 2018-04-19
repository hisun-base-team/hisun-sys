package com.hisun.saas.sys.taglib.tree;

public interface SanTreeNodeInterface {
	void setText(String text);
	String getText();
	void setHref(String href);
	String getHref();
	void setTarget(String target);
	String getTarget();
	void setJsFunction(String jsFunction); 
	String getJsFunction();
	void setHelpCode(String HelCode);
	String getHelpCode();
	void setKey(String key);
	String getKey();
	void setParentKey(String parentKey);
	String getParentKey();
	void setDynamicLoading(int DynamicLoading);
	int getDynamicLoading();
	void setInputValue(String inputValue);
	String geInputValue();
	void setCheckboxJs(String checkboxJs);
	String getCheckboxJs();
	String getDynamicAttri();
	void setDynamicAttri(String dynamicAttri);
	void setGrayImg(String grayImg);
	String getGrayImg();
	String getFullName();
	void setFullName(String fullName);
	
	void setCustomContent(String customContent);
	String getCustomContent();
	void setFullUrl(String fullUrl);
	String getFullUrl();
	void setForShort(String forShort);
	String getForShort();
	void setHelpCode2(String helpCode2);
	String getHelpCode2();
	
	void setRadioEnable(String radioEnable);
	String getRadioEnable();
	
	String getIsolateGrayImg();
	void setIsolateGrayImg(String isolateGrayImg);
	
	String getCustomAttribute();
	void setCustomAttribute(String customAttribute);
	String getCheckboxDisplay();
	void setCheckboxDisplay(String checkboxDisplay);
	
	String getNodeOperateImg();
	void setNodeOperateImg(String nodeOperateImg);
	String getNodeOpen();
    void setNodeOpen(String nodeOpen);
	String getOpen();
	void setOpen(String open);	
	String getCengImg();
	void setCengImg(String cengImg);
	
	String getChecked();
	void setChecked(String checked);
	String getIsCurrentNode();
	void setIsCurrentNode(String isCurrentNode);
	
	String getCheckboxName();
	void setCheckboxName(String checkboxName);
	
	String getTitleTip();
	void setTitleTip(String titleTip);
	
	String getChildSelectedStatus();
	void setChildSelectedStatus(String childSelectedStatus);
}

