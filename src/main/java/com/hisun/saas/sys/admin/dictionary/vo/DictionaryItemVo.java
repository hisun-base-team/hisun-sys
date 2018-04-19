package com.hisun.saas.sys.admin.dictionary.vo;

/**
 * 
 *<p>类名称：DictionaryItemVo</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：Jason
 *@创建时间：2015年3月19日 下午14:35:28
 *@创建人联系方式：jason4j@qq.com
 *@version
 */
public class DictionaryItemVo {

	/**
	 * 节点id
	 */
	private String id;
	
	/**
	 * 节点父id
	 */
	private String pId;
	
	/**
	 * 树节点名字
	 */
	private String name;
	
	private String value;
	/**
	 * 是否打开节点
	 */
	private boolean open;

	
	private String href;
	
	private String url;
	
	private Integer sort;
	
	private String remark;
	
	private String queryCode;
	
	private String typeName;
	
	private String typeId;
	
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

	public String getQueryCode() {
		return queryCode;
	}

	public void setQueryCode(String queryCode) {
		this.queryCode = queryCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
