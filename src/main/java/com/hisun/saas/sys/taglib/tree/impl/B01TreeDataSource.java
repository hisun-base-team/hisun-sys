//package com.hisun.saas.sys.taglib.tree.impl;
//import com.google.common.collect.Lists;
//import com.hisun.base.dao.util.CommonConditionQuery;
//import com.hisun.base.dao.util.CommonOrder;
//import com.hisun.base.dao.util.CommonOrderBy;
//import com.hisun.base.dao.util.CommonRestrictions;
//import com.hisun.saas.sys.taglib.hisunTree.TreeObject;
//import com.hisun.saas.zzb.app.console.bset.entity.AppBsetB01;
//import com.hisun.saas.zzb.app.console.bset.entity.AppBsetFl;
//import com.hisun.saas.zzb.app.console.bset.entity.AppBsetFl2B01;
//import com.hisun.saas.zzb.app.console.bset.service.AppBsetB01Service;
//import com.hisun.saas.zzb.app.console.bset.service.AppBsetFl2B01Service;
//import com.hisun.saas.zzb.app.console.bset.service.AppBsetFlService;
//import com.hisun.saas.zzb.app.console.bset.vo.B01TreeVo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//@Component
//public class B01TreeDataSource extends TreeObject {
//	private  static  final Logger logger = LoggerFactory.getLogger(B01TreeDataSource.class);
//	@Resource
//	private AppBsetFlService appBsetFlService;
//	@Resource
//	private AppBsetB01Service appBsetB01Service;
//	@Override
//	public List getChildrenNodes(String parentKey, String parentText) {
//
//		return null;
//	}
//
//
//	public List getNodes(){
//// 返回节点集合
//		List<SanTreeNode> nodeList = new ArrayList<SanTreeNode>();
//		// 获取当前用户的单位CODE
//
//		String dataSourceTyle = "b01";//用于记录机构树组合方式 如果分类的顶级节点为隐藏则由b01直接组成
//		CommonConditionQuery query = new CommonConditionQuery();
//		query.add(CommonRestrictions.and(" parentFl.id is null and  tombstone = :tombstone", "tombstone", 0));
//		CommonOrderBy orderBy = new CommonOrderBy();
//		orderBy.add(CommonOrder.asc("parentFl.id"));
//		orderBy.add(CommonOrder.asc("px"));
//		List<AppBsetFl> topAppBsetFls = this.appBsetFlService.list(query, orderBy);
//		if(topAppBsetFls!=null && topAppBsetFls.size()>0){
//			if(topAppBsetFls.get(0).getIsHidden()==AppBsetFl.DISPLAY){
//				dataSourceTyle = "fl";
//			}
//		}
//		try {
//			nodeList = getB01TreeVoList(dataSourceTyle, null);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return nodeList;
//
//	}
//
//	//得到树的集合 dataSourceTyle fl为先读取分类，找分类下的机构 b01表示直接找机构组成树结构  loadNodeTrpe topNode为加载一级节点 childNode加载子节点
//	public List<SanTreeNode> getB01TreeVoList(String dataSourceTyle,String queryParentKey)throws Exception{
//		List<SanTreeNode> nodeList = new ArrayList<SanTreeNode>();
//		if(dataSourceTyle.equals("fl")){
//			return getB01TreeVoListByFl(nodeList,queryParentKey);
//		}else{
//			return getB01TreeVoListByB01(nodeList,queryParentKey);
//		}
//	}
//	private List<SanTreeNode> getB01TreeVoListByFl(List<SanTreeNode> nodeList,String queryParentKey)throws Exception{
//		CommonConditionQuery query = new CommonConditionQuery();
//		if(queryParentKey!=null && !queryParentKey.equals("")) {
//			query.add(CommonRestrictions.and(" parentFl.id =:queryParentKey", "queryParentKey",queryParentKey));
//		}
//		CommonOrderBy orderBy = new CommonOrderBy();
//
//		//orderBy.add(CommonOrder.asc("parentFl.px"));
//		orderBy.add(CommonOrder.asc("px"));
//		List<AppBsetFl> sppBsetFls = this.appBsetFlService.list(query, orderBy);
//		List<B01TreeVo> b01TreeVoList = Lists.newArrayList();
//		if(sppBsetFls != null){
//			for (AppBsetFl fl : sppBsetFls) {
//				String parentKey = "0";
//
//
//				if(fl.getParentFl()!=null){//由于部分节点找不到parent 故做此处理
//					try {
//						fl.getParentFl().getId();
//					}catch(Exception e) {
//						//continue;
//					}
//				}
//				if( fl.getParentFl()!=null) {
////					if (queryParentKey == null) {
////						continue;
////					} else {
//						parentKey = fl.getParentFl().getId();
////					}
//				}
//				SanTreeNode node = new SanTreeNode();
//				node.setText(fl.getFl());// 节点文本
//				node.setHref("javascript:{}");
//				node.setTarget("");// 链接目标
//				node.setJsFunction("javascript:{}");// 链接函数
//				node.setHelpCode("");// 助记码
//				node.setKey(fl.getId());// 节点键值
//				node.setParentKey(parentKey);
//				node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
//				node.setInputValue("");// 复选框值
//				node.setCheckboxJs("");// checkbox的单击函数
//				node.setIsCurrentNode("false");
//				node.setDefaultSelected("false");// 是否默认选中
//				node.setEnable("true"); // 复选是否可用 true可用 false 不可用
//				node.setDynamicAttri("");// 动态扩展属性
//				node.setGrayImg("false"); // true
//				// 为显示灰色复选框图片,false为不显示灰色复选框图片
//				node.setFullName(fl.getFl());// 节点全称
//				node.setCustomContent(fl.getFl());// 自定义内容
//				node.setFullUrl("");// 全路径
//				node.setForShort("");// 简称
//				node.setHelpCode2("");// 助记码2
//				nodeList.add(node);
//				if(fl.getAppBsetFl2B01s()!=null){//将机构增加到树
//					for(AppBsetFl2B01 fl2b01 : fl.getAppBsetFl2B01s()){
//						try {
//							fl2b01.getAppBsetB01().getId();
//						}catch(Exception e) {
//							continue;
//						}
//						if(queryParentKey==null) {
//							continue;
//						}
//						AppBsetB01 appBsetB01 = fl2b01.getAppBsetB01();
//						if(appBsetB01!=null) {
//							SanTreeNode nodeB01 = new SanTreeNode();
//							nodeB01.setText(appBsetB01.getB0101());// 节点文本
//							nodeB01.setHref("javascript:{}");
//							nodeB01.setTarget("");// 链接目标
//							nodeB01.setJsFunction("javascript:{}");// 链接函数
//							nodeB01.setHelpCode("");// 助记码
//							nodeB01.setKey(appBsetB01.getId());// 节点键值
//							nodeB01.setParentKey(fl.getId());
//							nodeB01.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
//							nodeB01.setInputValue("");// 复选框值
//							nodeB01.setCheckboxJs("");// checkbox的单击函数
//							nodeB01.setIsCurrentNode("false");
//							nodeB01.setDefaultSelected("false");// 是否默认选中
//							nodeB01.setEnable("true"); // 复选是否可用 true可用 false 不可用
//							nodeB01.setDynamicAttri("");// 动态扩展属性
//							nodeB01.setGrayImg("false"); // true
//							// 为显示灰色复选框图片,false为不显示灰色复选框图片
//							nodeB01.setFullName(fl.getFl());// 节点全称
//							nodeB01.setCustomContent("");// 自定义内容
//							nodeB01.setFullUrl("");// 全路径
//							nodeB01.setForShort("");// 简称
//							nodeB01.setHelpCode2("");// 助记码2
//							nodeList.add(nodeB01);
//						}
//					}
//				}
//			}
//		}
//		return nodeList;
//	}
//	private List<SanTreeNode> getB01TreeVoListByB01(List<SanTreeNode> nodeList,String queryParentKey)throws Exception{
//		CommonConditionQuery query = new CommonConditionQuery();
//		CommonOrderBy orderBy = new CommonOrderBy();
//		if(queryParentKey!=null && !queryParentKey.equals("")) {
//			query.add(CommonRestrictions.and(" parentB01.id =:queryParentKey", "queryParentKey",queryParentKey));
//		}
//		orderBy.add(CommonOrder.asc("queryCode"));
//		orderBy.add(CommonOrder.asc("parentB01.id"));
//		orderBy.add(CommonOrder.asc("px"));
//		List<AppBsetB01> appBsetB01s = this.appBsetB01Service.list(query, orderBy);
//		List<B01TreeVo> b01TreeVoList = Lists.newArrayList();
//		if(appBsetB01s != null) {
//			for (AppBsetB01 b01 : appBsetB01s) {
//				B01TreeVo b01TreeVo = new B01TreeVo();
//				b01TreeVo.setId(b01.getId());
//				String parentKey = queryParentKey==null?"0":queryParentKey;
//
//				SanTreeNode node = new SanTreeNode();
//				node.setText(b01.getB0101());// 节点文本
//				node.setHref("javascript:{}");
//				node.setTarget("");// 链接目标
//				node.setJsFunction("javascript:{}");// 链接函数
//				node.setHelpCode("");// 助记码
//				node.setKey(b01.getId());// 节点键值
//				node.setParentKey(parentKey);
//				node.setDynamicLoading(0);// 是否动态加载 1 为动态加载 0为否
//				node.setInputValue("");// 复选框值
//				node.setCheckboxJs("");// checkbox的单击函数
//				node.setIsCurrentNode("false");
//				node.setDefaultSelected("false");// 是否默认选中
//				node.setEnable("true"); // 复选是否可用 true可用 false 不可用
//				node.setDynamicAttri("");// 动态扩展属性
//				node.setGrayImg("false"); // true
//				// 为显示灰色复选框图片,false为不显示灰色复选框图片
//				node.setFullName(b01.getB0101());// 节点全称
//				node.setCustomContent("");// 自定义内容
//				node.setFullUrl("");// 全路径
//				node.setForShort("");// 简称
//				node.setHelpCode2("");// 助记码2
//				nodeList.add(node);
//			}
//		}
//		return nodeList;
//	}
//	private List<SanTreeNode> getB01VoListByFlId(List<SanTreeNode> nodeList,String queryParentKey)throws Exception{
//		CommonConditionQuery query = new CommonConditionQuery();
//		if(queryParentKey!=null && !queryParentKey.equals("")) {
//			query.add(CommonRestrictions.and(" id =:queryParentKey", "queryParentKey",queryParentKey));
//		}
//		CommonOrderBy orderBy = new CommonOrderBy();
//
//		//orderBy.add(CommonOrder.asc("parentFl.px"));
//		orderBy.add(CommonOrder.asc("px"));
//		List<AppBsetFl> sppBsetFls = this.appBsetFlService.list(query, orderBy);
//
//		if(sppBsetFls!=null && sppBsetFls.size()>0){
//			if(sppBsetFls.get(0).getAppBsetFl2B01s()!=null) {
//					for(AppBsetFl2B01 b01 : sppBsetFls.get(0).getAppBsetFl2B01s()){
//					B01TreeVo b01TreeVo = new B01TreeVo();
//					b01TreeVo.setId(b01.getId());
//
//					SanTreeNode node = new SanTreeNode();
//					node.setText(b01.getAppBsetB01().getB0101());// 节点文本
//					node.setHref("javascript:{}");
//					node.setTarget("");// 链接目标
//					node.setJsFunction("javascript:{}");// 链接函数
//					node.setHelpCode("");// 助记码
//					node.setKey(b01.getAppBsetB01().getId());// 节点键值
//					node.setParentKey(queryParentKey);
//					node.setDynamicLoading(1);// 是否动态加载 1 为动态加载 0为否
//					node.setInputValue("");// 复选框值
//					node.setCheckboxJs("");// checkbox的单击函数
//					node.setIsCurrentNode("false");
//					node.setDefaultSelected("false");// 是否默认选中
//					node.setEnable("true"); // 复选是否可用 true可用 false 不可用
//					node.setDynamicAttri("");// 动态扩展属性
//					node.setGrayImg("false"); // true
//					// 为显示灰色复选框图片,false为不显示灰色复选框图片
//					node.setFullName(b01.getAppBsetB01().getB0101());// 节点全称
//					node.setCustomContent("");// 自定义内容
//					node.setFullUrl("");// 全路径
//					node.setForShort("");// 简称
//					node.setHelpCode2("");// 助记码2
//					nodeList.add(node);
//				}
//			}
//		}
//		return nodeList;
//	}
//}
