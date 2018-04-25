/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.entity;

import com.hisun.base.entity.TombstoneEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_dict_type")
public class DictionaryType extends TombstoneEntity implements Serializable {


	private static final long serialVersionUID = 8092682463630146605L;
	private String id;//逻辑主键
	private String name;//字典类型名称
	private String code;//字典类型代码
	private String remark;//备注说明


	private List<DictionaryItem> dictionaryItems;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false, unique = true,length=32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(name="name",length=200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="code",length=100,nullable = false, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="remark",length=255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(mappedBy = "dictionaryType", fetch = FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	public List<DictionaryItem> getDictionaryItems() {
		return dictionaryItems;
	}

	public void setDictionaryItems(List<DictionaryItem> dictionaryItems) {
		this.dictionaryItems = dictionaryItems;
	}
}
