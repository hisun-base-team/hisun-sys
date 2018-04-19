package com.hisun.saas.sys.admin.resource.vo;

import java.util.List;

public class ResourceMenuItem {
	
	private String resId;//对应resource的id
	private String resourceName;//名称
	private Integer resourceType;//类型，0 菜单 1 操作 2 系统
	private boolean isMenu;//是否菜单
	private String url;
	private Integer sort;
	private boolean isTop;//是否顶层
	private List<ResourceMenuItem> children;
	private String queryCode;
	private String resourceIcon;
	private String permission;
	private String pId;//父ID

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	public boolean isMenu() {
		return isMenu;
	}
	public void setMenu(boolean isMenu) {
		this.isMenu = isMenu;
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
	
	public boolean getIsMenu() {
		return isMenu;
	}
	
	public void setIsMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}
	public boolean isTop() {
		return isTop;
	}
	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}
	public List<ResourceMenuItem> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceMenuItem> children) {
		this.children = children;
	}
	public String getQueryCode() {
		return queryCode;
	}
	public void setQueryCode(String queryCode) {
		this.queryCode = queryCode;
	}
	public String getResourceIcon() {
		return resourceIcon;
	}
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
}
