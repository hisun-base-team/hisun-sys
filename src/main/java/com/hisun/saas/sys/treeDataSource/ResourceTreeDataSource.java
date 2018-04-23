package com.hisun.saas.sys.treeDataSource;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.admin.role.vo.ResourceVo;
import com.hisun.saas.sys.taglib.tree.AbstractTreeDataSource;
import com.hisun.saas.sys.taglib.tree.TreeNodeImpl;
import com.hisun.util.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceTreeDataSource extends AbstractTreeDataSource {
	@javax.annotation.Resource
	ResourceService resourceService;
	@Override
	public List getChildrenNodes(String parentKey, String parentText) {

		return null;
	}


	public List getNodes(){
		List<TreeNodeImpl> nodeList = new ArrayList<TreeNodeImpl>();
		try {
			List<Resource> resources;
			String status = null;
			CommonOrderBy orderBy = new CommonOrderBy();
			CommonConditionQuery query = new CommonConditionQuery();
			orderBy.add(CommonOrder.asc("queryCode"));
			if(status!=null){
				query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
			}
//			else{
//				query.add(CommonRestrictions.and(" resourceType != :resourceType ", "resourceType", Integer.valueOf(1)));
//			}
			query.add(CommonRestrictions.and(" type = :type ", "type", Integer.valueOf(0)));

			resources = resourceService.list(query, orderBy);

			TreeNodeImpl node = new TreeNodeImpl();
			node.setText("资源树");// 节点文本
			node.setHref("javascript:{}");
			node.setTarget("");// 链接目标
			node.setJsFunction("javascript:refright('1')");// 链接函数
			node.setHelpCode("");// 助记码
			node.setKey("1");// 节点键值
			node.setParentKey("");
			node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
			node.setInputValue("");// 复选框值
			node.setCheckboxJs("");// checkbox的单击函数
			node.setIsCurrentNode("true");
			node.setDefaultSelected("false");// 是否默认选中
			node.setEnable("true"); // 复选是否可用 true可用 false 不可用
			node.setDynamicAttri("");// 动态扩展属性
			node.setGrayImg("false"); // true
			// 为显示灰色复选框图片,false为不显示灰色复选框图片
			node.setFullName("资源树");// 节点全称
			node.setCustomContent("资源树");// 自定义内容
			nodeList.add(node);
			for (Resource resource : resources) {
				node = new TreeNodeImpl();
				node.setText(resource.getResourceName());// 节点文本
				node.setHref("javascript:refright('"+resource.getId()+"')");
				node.setTarget("");// 链接目标
				node.setJsFunction("javascript:{}");// 链接函数
				node.setHelpCode("");// 助记码
				node.setKey(resource.getId());// 节点键值
				node.setParentKey(resource.getpId());
				node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
				node.setInputValue("");// 复选框值
				node.setCheckboxJs("");// checkbox的单击函数
				node.setIsCurrentNode("false");
				node.setDefaultSelected("false");// 是否默认选中
				node.setEnable("true"); // 复选是否可用 true可用 false 不可用
				node.setDynamicAttri("");// 动态扩展属性
				node.setGrayImg("false"); // true
				// 为显示灰色复选框图片,false为不显示灰色复选框图片
				node.setFullName(resource.getResourceName());// 节点全称
				node.setCustomContent(resource.getResourceName());// 自定义内容
				node.setFullUrl("");// 全路径
				node.setForShort("");// 简称
				node.setHelpCode2("");// 助记码2
				nodeList.add(node);
			}



		}catch (Exception e){
			e.printStackTrace();
		}
		return nodeList;

	}


}
