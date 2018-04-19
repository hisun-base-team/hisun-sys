package com.hisun.saas.sys.admin.dictionary.vo;


/**
 * <p>Title: DictionaryTypeVo.java </p>
 * <p>Package com.hisun.saas.sys.dictionary.vo </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月14日 下午4:53:37 
 * @version 
 */
public class DictionaryTypeVo {

	private String id;//逻辑主键
	
	private String name;//字典类型名称
	
	private String remark;//备注说明
	
	private String code;//字典类型编码
	
	private Integer sort;//排序
	
	private String selected ;

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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
}
