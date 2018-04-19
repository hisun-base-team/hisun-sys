package com.hisun.saas.sys.admin.role.vo;

/**
 * 
 *<p>类名称：ResourceVo</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：Jason
 *@创建时间：2015年3月19日 下午14:35:28
 *@创建人联系方式：jason4j@qq.com
 *@version
 */
public class ResourceVo {

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
	private String href;
	
	//resource entity中的对应属性，用于直接entity转vo在前台进行显示
	private String resourceName;
	private Integer resourceType;
	private Integer status;
	//private boolean isMenu;
	private String url;
	private Integer sort;
	private String permission;
	private String description;
	private String resourceIcon;
	private boolean chkDisabled;
	private boolean checked;
	
	private boolean sysResource;
	
	public boolean isSysResource() {
		return sysResource;
	}

	public void setSysResource(boolean sysResource) {
		this.sysResource = sysResource;
	}

	public String getResourceIcon() {
		return resourceIcon;
	}

	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
