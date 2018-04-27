/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.vo;

import com.hisun.saas.sys.taglib.treeTag.TreeNode;

/**
 * @author liuzj {279421824@qq.com}
 */
public class TenantResourceTreeNode extends TreeNode {

	/**
	 * 是否需要设置数据权限   0表示不设置  1表示需要设置
	 */
	private String privilegeSetting;

	public String getPrivilegeSetting() {
		return privilegeSetting;
	}

	public void setPrivilegeSetting(String privilegeSetting) {
		this.privilegeSetting = privilegeSetting;
	}
}
