package com.hisun.saas.sys.taglib.treeTag.vo;

/**
 * 
 *<p>类名称：TreeNodeVo</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：liuzj
 *@创建时间：2018年4月22日
 *@创建人联系方式：liuzj@qq.com
 *@version
 */
public class TreeNodeVo {

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
}
