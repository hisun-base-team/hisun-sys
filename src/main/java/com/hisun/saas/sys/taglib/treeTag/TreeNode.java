/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.treeTag;

import com.hisun.util.StringUtils;

/**
 * @author liuzj {279421824@qq.com}
 */
public class TreeNode {

	/**
	 * 树节点id
	 */
	private String id;
	
	/**
	 * 树节点父id
	 */
	private String pId;
	
	/**
	 * 树节点名字
	 */
	private String name;
	
	/**
	 * 是否打开节点
	 */
	private boolean open;

	/**
	 * 资源对应的url
	 * @return
	 */
	private String url;

	/**
	 * 自定义图标
	 */
	private String icon;

	//父节点自定义展开时图标的 URL 路径
	private String iconOpen;

    //父节点自定义折叠时图标的 URL 路径
	private String iconClose;

	private boolean checked;//是否默认选中

	private boolean nocheck;//true 表示此节点不显示 checkbox / radio，不影响勾选的关联关系，不影响父节点的半选状态。false 表示节点具有正常的勾选功能

	private boolean chkDisabled;//设置节点的 checkbox / radio 是否禁用true 表示此节点的 checkbox / radio 被禁用。false 表示此节点的 checkbox / radio 可以使用。

	private String key;//存储返回值 如果和id一样则可以不传 例如字典树则需要在此传入字典code

	private String description;

	/**
	 * 记录 treeNode 节点是否为父节点。1、初始化节点数据时，根据 treeNode.children 属性判断，有子节点则设置为 true，否则为 false
	 2、初始化节点数据时，如果设定 treeNode.isParent = true，即使无子节点数据，也会设置为父节点
	 3、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
	 */
	private boolean isParent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getKey() {
		if(key == null){
			return id;
		}else{
			return key;
		}
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		if(description == null){
			return name;
		}else{
			return description;
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
}
