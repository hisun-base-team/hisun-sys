/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.treeTag;
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
}
