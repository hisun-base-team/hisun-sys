/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.util;
import com.hisun.saas.sys.taglib.tree.TreeNodeImpl;

public class TreeNodeGenerator {

	public static TreeNodeImpl genTreeNode(String nodeKey,
												   String nodeName, String nodeHref, String parentNodeKey,
												   boolean isDynamic, boolean isCurrentNode) {
		TreeNodeImpl node = new TreeNodeImpl();
		node = new TreeNodeImpl();
		node.setText(nodeName);// 节点文本
		node.setHref(nodeHref);// 节点链接
		node.setTarget("");// 链接目标
		node.setJsFunction("javascript:{}");// 链接函数
		node.setHelpCode("");// 助记码
		node.setKey(nodeKey);// 节点键值
		node.setParentKey(parentNodeKey);// 父节点键值 最高层父key,必需为0
		if (isDynamic == true) {
			node.setDynamicLoading(1);// 是否动态加载 1 为动态加载 0为否
		} else {
			node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
		}

		node.setInputValue("");// 复选框值
		node.setCheckboxJs("");// checkbox的单击函数
		if (isCurrentNode == true) {
			node.setIsCurrentNode("true");
		} else {
			node.setIsCurrentNode("false");
		}
		node.setDefaultSelected("false");// 是否默认选中
		node.setEnable("false"); // 复选是否可用 true可用 false 不可用
		node.setDynamicAttri("");// 动态扩展属性
		node.setGrayImg("false"); // true
		// 为显示灰色复选框图片,false为不显示灰色复选框图片
		node.setFullName(nodeName);// 节点全称
		node.setCustomContent(nodeName);// 自定义内容
		node.setFullUrl(nodeName);// 全路径
		node.setForShort("");// 简称
		node.setHelpCode2("");// 助记码2
		return node;
	}

	public static TreeNodeImpl genTreeNode(String nodeKey,
												   String nodeName, String nodeHref, String parentNodeKey,
												   boolean isDynamic, boolean isCurrentNode, boolean isDefaultSelect,
												   boolean checkboxEnable) {
		TreeNodeImpl node = new TreeNodeImpl();
		node = new TreeNodeImpl();
		node.setText(nodeName);// 节点文本
		node.setHref(nodeHref);// 节点链接
		node.setTarget("");// 链接目标
		node.setJsFunction("javascript:{}");// 链接函数
		node.setHelpCode("");// 助记码
		node.setKey(nodeKey);// 节点键值
		node.setParentKey(parentNodeKey);// 父节点键值 最高层父key,必需为0
		if (isDynamic == true) {
			node.setDynamicLoading(1);// 是否动态加载 1 为动态加载 0为否
		} else {
			node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
		}

		node.setInputValue("");// 复选框值
		node.setCheckboxJs("");// checkbox的单击函数
		if (isCurrentNode == true) {
			node.setIsCurrentNode("true");
		} else {
			node.setIsCurrentNode("false");
		}

		node.setDefaultSelected(String.valueOf(isDefaultSelect));// 是否默认选中
		node.setEnable(String.valueOf(checkboxEnable)); // 复选是否可用 true可用 false
		// 不可用
		node.setDynamicAttri("");// 动态扩展属性
		node.setGrayImg("false"); // true
		// 为显示灰色复选框图片,false为不显示灰色复选框图片
		node.setFullName(nodeName);// 节点全称
		node.setCustomContent(nodeName);// 自定义内容
		node.setFullUrl(nodeName);// 全路径
		node.setForShort("");// 简称
		node.setHelpCode2("");// 助记码2
		return node;
	}
}