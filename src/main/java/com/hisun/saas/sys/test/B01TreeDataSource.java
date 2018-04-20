package com.hisun.saas.sys.test;
import com.google.common.collect.Lists;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.taglib.tree.AbstractTreeDataSource;
import com.hisun.saas.sys.taglib.tree.TreeNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Component
public class B01TreeDataSource extends AbstractTreeDataSource {
	private  static  final Logger logger = LoggerFactory.getLogger(B01TreeDataSource.class);

	@Override
	public List getChildrenNodes(String parentKey, String parentText) {

		return null;
	}


	public List getNodes(){
		List<TreeNodeImpl> nodeList = new ArrayList<TreeNodeImpl>();
		try {
			TreeNodeImpl node = new TreeNodeImpl();
			node.setText("aaaa");// 节点文本
			node.setHref("javascript:{}");
			node.setTarget("");// 链接目标
			node.setJsFunction("javascript:{}");// 链接函数
			node.setHelpCode("");// 助记码
			node.setKey("1");// 节点键值
			node.setParentKey("");
			node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
			node.setInputValue("");// 复选框值
			node.setCheckboxJs("");// checkbox的单击函数
			node.setIsCurrentNode("false");
			node.setDefaultSelected("false");// 是否默认选中
			node.setEnable("true"); // 复选是否可用 true可用 false 不可用
			node.setDynamicAttri("");// 动态扩展属性
			node.setGrayImg("false"); // true
			// 为显示灰色复选框图片,false为不显示灰色复选框图片
			node.setFullName("aaaa");// 节点全称
			node.setCustomContent("aaaa");// 自定义内容
			node.setFullUrl("");// 全路径
			node.setForShort("");// 简称
			node.setHelpCode2("");// 助记码2
			nodeList.add(node);


			node = new TreeNodeImpl();
			node.setText("aaaa.1");// 节点文本
			node.setHref("javascript:{}");
			node.setTarget("");// 链接目标
			node.setJsFunction("javascript:{}");// 链接函数
			node.setHelpCode("");// 助记码
			node.setKey("2");// 节点键值
			node.setParentKey("1");
			node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
			node.setInputValue("");// 复选框值
			node.setCheckboxJs("");// checkbox的单击函数
			node.setIsCurrentNode("false");
			node.setDefaultSelected("false");// 是否默认选中
			node.setEnable("true"); // 复选是否可用 true可用 false 不可用
			node.setDynamicAttri("");// 动态扩展属性
			node.setGrayImg("false"); // true
			// 为显示灰色复选框图片,false为不显示灰色复选框图片
			node.setFullName("aaaa");// 节点全称
			node.setCustomContent("aaaa");// 自定义内容
			node.setFullUrl("");// 全路径
			node.setForShort("");// 简称
			node.setHelpCode2("");// 助记码2
			nodeList.add(node);

		}catch (Exception e){
			e.printStackTrace();
		}
		return nodeList;

	}


}
