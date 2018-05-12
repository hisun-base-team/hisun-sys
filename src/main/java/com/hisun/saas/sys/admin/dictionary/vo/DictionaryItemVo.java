/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.vo;


public class DictionaryItemVo {

	private String id;
	private String pId;
	private String pId_value;

	private String name;
	private String code;
	private boolean open;
	private String href;
	private String url;
	private Integer sort;
	private String remark;
	private String typeName;
	private String typeId;
	private int display=0;
	private String displayStr = "是";


	
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpId_value() {
		return pId_value;
	}

	public void setpId_value(String pId_value) {
		this.pId_value = pId_value;
	}

	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}

	public String getDisplayStr() {
		if(display==0){
			displayStr="是";
		}else{
			displayStr="否";
		}
		return displayStr;
	}

	public void setDisplayStr(String displayStr) {

		this.displayStr = displayStr;
	}
}
