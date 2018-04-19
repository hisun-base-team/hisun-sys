package com.hisun.saas.sys.taglib.tree.impl;
import com.hisun.saas.sys.taglib.hisunTree.TreeObject;

import java.util.ArrayList;
import java.util.List;


public class TreeDataSource extends TreeObject {

	@Override
	public List getChildrenNodes(String parentKey, String parentText) {
		List retList=new ArrayList();
		String checked=this.getRequest().getParameter("checked");

		if(parentKey.equals("001")){
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setText("中国人1_1");
			node1_1.setKey("001_001");
			node1_1.setHref("javascript:{void()}");
			node1_1.setCheckboxJs("javascript:{void()}");
			node1_1.setDynamicLoading(1);
			node1_1.setParentKey(parentKey);
			retList.add(node1_1);

			SanTreeNode node1_2=new SanTreeNode();
			node1_2.setText("中国人1_2");
			node1_2.setKey("001_002");
			node1_2.setDynamicLoading(1);
			node1_2.setParentKey(parentKey);
			retList.add(node1_2);

			SanTreeNode node1_3=new SanTreeNode();
			node1_3.setText("中国人1_3");
			node1_3.setKey("001_003");
			node1_3.setDynamicLoading(1);
			node1_3.setParentKey(parentKey);
			retList.add(node1_3);

			SanTreeNode node1_4=new SanTreeNode();
			node1_4.setText("中国人1_4");
			node1_4.setKey("001_004");
			node1_4.setDynamicLoading(1);
			node1_4.setParentKey(parentKey);
			retList.add(node1_4);

		}else if(parentKey.equals("001_001")){
			SanTreeNode node1_1_1=new SanTreeNode();
			node1_1_1.setText("中国人1_1_1");
			node1_1_1.setKey("001_001_001");
			node1_1_1.setDynamicLoading(1);
			node1_1_1.setParentKey(parentKey);
			retList.add(node1_1_1);

			SanTreeNode node1_1_2=new SanTreeNode();
			node1_1_2.setText("中国人1_1_2");
			node1_1_2.setKey("001_001_002");
			node1_1_2.setDynamicLoading(1);
			node1_1_2.setParentKey(parentKey);
			retList.add(node1_1_2);

			SanTreeNode node1_1_3=new SanTreeNode();
			node1_1_3.setText("中国人1_1_3");
			node1_1_3.setKey("001_001_003");
			node1_1_3.setDynamicLoading(1);
			node1_1_3.setParentKey(parentKey);
			retList.add(node1_1_3);

		}else if(parentKey.equals("001_002")){
			SanTreeNode node1_2_1=new SanTreeNode();
			node1_2_1.setText("中国人1_2_1");
			node1_2_1.setKey("001_002_001");
			node1_2_1.setDynamicLoading(1);
			node1_2_1.setParentKey(parentKey);
			retList.add(node1_2_1);

			SanTreeNode node1_2_2=new SanTreeNode();
			node1_2_2.setText("中国人1_2_2");
			node1_2_2.setKey("001_002_002");
			node1_2_2.setDynamicLoading(1);
			node1_2_2.setParentKey(parentKey);
			retList.add(node1_2_2);

			SanTreeNode node1_2_3=new SanTreeNode();
			node1_2_3.setText("中国人1_2_3");
			node1_2_3.setKey("001_002_003");
			node1_2_3.setDynamicLoading(1);
			node1_2_3.setParentKey(parentKey);
			retList.add(node1_2_3);

		}else if(parentKey.equals("001_003")){
			SanTreeNode node1_3_1=new SanTreeNode();
			node1_3_1.setText("中国人1_3_1");
			node1_3_1.setKey("001_003_001");
			node1_3_1.setDynamicLoading(1);
			node1_3_1.setParentKey(parentKey);
			retList.add(node1_3_1);

			SanTreeNode node1_3_2=new SanTreeNode();
			node1_3_2.setText("中国人1_3_2");
			node1_3_2.setKey("001_003_002");
			node1_3_2.setDynamicLoading(1);
			node1_3_2.setParentKey(parentKey);
			retList.add(node1_3_2);

			SanTreeNode node1_3_3=new SanTreeNode();
			node1_3_3.setText("中国人1_3_3");
			node1_3_3.setKey("001_003_003");
			node1_3_3.setDynamicLoading(1);
			node1_3_3.setParentKey(parentKey);
			retList.add(node1_3_3);

		}
		return retList;
	}

	/*
	 SanTreeNode(text,href,target,jsFunction,helpCode,
			 key,parentKey,dynamicLoading,inputValue,checkboxJs,defaultSelected,enable);
			 String text;//节点文本
			 String href;//节点链接
			 String target;//链接目标
			 String jsFunction;//链接函数
			 String helpCode;//助记码
			 String key;//节点键值
			 String parentKey;//父节点键值 最高层父key,必需为0
			 int dynamicLoading;//是否动态加载 1 为动态加载 0为否
			 String inputValue;//复选框值
			 String checkboxJs;//checkbox中的js方法
			 String defaultSelected;//是否默认选中
			 String enable //复选是否可用 true可用 false 不可用
			 String  dynamicAttri//动态扩展属性
			 String grayImg; //true 为显示灰色复选框图片,false为不显示灰色复选框图片
			 String fullName;//节点全称
	*/

	public List getNodes(){

		SanTreeNode root1=new SanTreeNode();
		root1.setText("待办");
		root1.setDynamicLoading(1);
		root1.setDynamicAttri("111");

		SanTreeNode root2=new SanTreeNode();
		root2.setText("共享");

		List list=new ArrayList();

		//静态
		this.getNodesByStatic(list);

		//动态
		//this.getNodesByDynamic(list);

		return list;//返回单位人员树的节点信息
	}

	/*
	 * 得到节点对象列表
	 */
	private List getTreeNodes(List list){
		List retList=new ArrayList();
		try{
			/*
			 SanTreeNode(text,href,target,jsFunction,helpCode,
				 key,parentKey,dynamicLoading,inputValue,checkboxJs,defaultSelected,enable);
				 String text;//节点文本
				 String href;//节点链接
				 String target;//链接目标
				 String jsFunction;//链接函数
				 String helpCode;//助记码
				 String key;//节点键值
				 String parentKey;//父节点键值 最高层父key,必需为0
				 int dynamicLoading;//是否动态加载 1 为动态加载 0为否
				 String inputValue;//复选框值
				 String checkboxJs;
				 String defaultSelected;//是否默认选中
				 String enable //复选是否可用 true可用 false 不可用
			*/
//			for(int i=0;i<list.size();i++){
//				Unit punite=(Unit)list.get(i);
//				String parentID="0";
//				String unitCode=punite.getCode();
//				String fullName=punite.getFullName();
//
//				if(punite.getParent()==null){
//					retList.add(new SanTreeNode(fullName,"javascript:{}","","javascript:{}","","u_"+unitCode,parentID,1,unitCode,"","false","true"));
//				}
//
//				//setDepartment(unitCode,retList,"u_");
//				//setUserNodes(unitCode,retList,"u_");
//			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return retList;
	}

	/*
	 * unit_code 为单位code
	 * list 存储节点的总列表
	 * pfx 前缀
	 */
	private void setDepartment(String unit_code,List list,String pfx) throws Exception {
//		UnitManagerInterface uniorUnits = (UnitManagerInterface) getBean("unitManager");
//		List departmentArr=uniorUnits.getAllJuniorUnits(unit_code);//得到单位下的所有部门
//		for(int i=0;i<departmentArr.size();i++){
//			Unit unit=(Unit)departmentArr.get(i);
//			String parentID=unit.getParent().getCode();
//			String departmentCode=unit.getCode();
//			String deptName=unit.getFullName();
//			list.add(new SanTreeNode(deptName,"javascript:{}","","javascript:{}","","d_"+departmentCode,pfx+unit_code,0,"","","false","true"));
//			setUserNodes(departmentCode,list,"d_");
//		}
	}

	/*
	 * unit_code 为单位code
	 * list 存储节点的总列表
	 */
	private void setUserNodes(String unit_code,List list,String pfx){
//		UnitManagerInterface uniorUnits = (UnitManagerInterface) getBean("unitManager");
//		ArrayList Userlist;
//		try {
//			Userlist = uniorUnits.getAllUsersOfUnit(unit_code);
//			for(int i=0;i<Userlist.size();i++){
//				UserAccount userAccount=(UserAccount)Userlist.get(i);
//				String parentID=unit_code;
//				String userId=userAccount.getId();
//				String userName=userAccount.getUserName();
//				//单位code对应的用户创建节点
//				if(userAccount.getUnit().getCode().equals(unit_code)){
//					list.add(new SanTreeNode(userName,"javascript:{}","","javascript:{}","","u_"+userId,pfx+parentID,0,"","","false","true"));
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//得到单位下的用户，包括子单位下的用户

	}

	private void getNodesByStatic(List<SanTreeNode> list){

		SanTreeNode node1=new SanTreeNode();
		node1.setText("中国人1");
		node1.setKey("001");
		node1.setParentKey("0");
		node1.setHref("javascript:{aClick()}");
		list.add(node1);
		SanTreeNode node1_1=new SanTreeNode();
		node1_1.setText("中国人1_1");
		node1_1.setHref("javascript:{aClick()}");
		node1_1.setKey("001_001");
		node1_1.setParentKey("001");
		list.add(node1_1);
		SanTreeNode node1_1_1=new SanTreeNode();
		node1_1_1.setText("中国人1_1_1");
		node1_1_1.setKey("001_001_001");
		node1_1_1.setParentKey("001_001");
		list.add(node1_1_1);
		SanTreeNode node1_1_2=new SanTreeNode();
		node1_1_2.setText("中国人1_1_2");
		node1_1_2.setKey("001_001_002");
		node1_1_2.setParentKey("001_001");
		list.add(node1_1_2);
		SanTreeNode node1_1_3=new SanTreeNode();
		node1_1_3.setText("中国人1_1_3");
		node1_1_3.setKey("001_001_003");
		node1_1_3.setParentKey("001_001");
		list.add(node1_1_3);
		SanTreeNode node1_1_4=new SanTreeNode();
		node1_1_4.setText("中国人1_1_4");
		node1_1_4.setKey("001_001_004");
		node1_1_4.setParentKey("001_001");
		list.add(node1_1_4);

		SanTreeNode node1_2=new SanTreeNode();
		node1_2.setText("中国人1_2");
		node1_2.setKey("001_002");
		node1_2.setParentKey("001");
		list.add(node1_2);

		SanTreeNode node1_3=new SanTreeNode();
		node1_3.setText("中国人1_3");
		node1_3.setKey("001_003");
		node1_3.setParentKey("001");
		list.add(node1_3);

		SanTreeNode node1_4=new SanTreeNode();
		node1_4.setText("中国人1_4");
		node1_4.setKey("001_004");
		node1_4.setParentKey("001");
		list.add(node1_4);

		SanTreeNode node1_5=new SanTreeNode();
		node1_5.setText("中国人1_5");
		node1_5.setKey("001_005");
		node1_5.setParentKey("001");
		list.add(node1_5);

		SanTreeNode node2=new SanTreeNode();
		node2.setText("中国人2");
		node2.setKey("002");
		node2.setParentKey("0");
		list.add(node2);

		SanTreeNode node3=new SanTreeNode();
		node3.setText("中国人3");
		node3.setKey("003");
		node3.setParentKey("0");
		list.add(node3);



		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setText("市委组织部");
		node1.setKey("unit");
		node1.setParentKey("0");
		list.add(node1);
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setText("干部三处");
			node1_1.setKey("dept");
			node1_1.setParentKey("unit");
			list.add(node1_1);
				SanTreeNode node1_1_1=new SanTreeNode();
				node1_1_1.setText("黄仰辉");
				node1_1_1.setKey("001");
				node1_1_1.setParentKey("dept");
				list.add(node1_1_1);

				SanTreeNode node1_1_2=new SanTreeNode();
				node1_1_2.setText("丛跃进");
				node1_1_2.setKey("002");
				node1_1_2.setParentKey("dept");
				list.add(node1_1_2);

				SanTreeNode node1_1_3=new SanTreeNode();
				node1_1_3.setText("张春英");
				node1_1_3.setKey("003");
				node1_1_3.setParentKey("dept");
				list.add(node1_1_3);

				SanTreeNode node1_1_4=new SanTreeNode();
				node1_1_4.setText("齐吉生");
				node1_1_4.setKey("004");
				node1_1_4.setParentKey("dept");
				list.add(node1_1_4);

			SanTreeNode node2=new SanTreeNode();
			node2.setText("市纪委");
			node2.setKey("unit2");
			node2.setParentKey("0");
			node2.setDynamicLoading(0);
			list.add(node2);
				  SanTreeNode node2_1=new SanTreeNode();
				  node2_1.setText("测试用户");
				  node2_1.setKey("testuser1");
				  node2_1.setParentKey("unit2");
				  node2_1.setDynamicLoading(0);
				  list.add(node2_1);

		*/

		///////begin 风险评估
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setText("应急预案管理");
		node1.setKey("root1");
		node1.setHref("javascript:{refreshRight('root1')}");
		node1.setParentKey("0");
		list.add(node1);
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setText("预案规划管理");
			node1_1.setKey("root1_1");
			node1_1.setIsCurrentNode("true");
			node1_1.setHref("javascript:{refreshRight('root1_1')}");
			node1_1.setParentKey("root1");
			list.add(node1_1);

			SanTreeNode node1_2=new SanTreeNode();
			node1_2.setText("预案编制管理");
			node1_2.setKey("root1_2");
			node1_2.setHref("javascript:{refreshRight('root1_2')}");
			node1_2.setParentKey("root1");
			list.add(node1_2);

			SanTreeNode node1_3=new SanTreeNode();
			node1_3.setText("预案评审管理");
			node1_3.setKey("root1_3");
			node1_3.setHref("javascript:{refreshRight('root1_3')}");
			node1_3.setParentKey("root1");
			list.add(node1_3);

			SanTreeNode node1_4=new SanTreeNode();
			node1_4.setText("预案发布管理");
			node1_4.setKey("root1_4");
			node1_4.setHref("javascript:{refreshRight('root1_4')}");
			node1_4.setParentKey("root1");
			list.add(node1_4);

			SanTreeNode node1_5=new SanTreeNode();
			node1_5.setText("预案培训管理");
			node1_5.setKey("root1_5");
			node1_5.setHref("javascript:{refreshRight('root1_5')}");
			node1_5.setParentKey("root1");
			list.add(node1_5);

			SanTreeNode node1_6=new SanTreeNode();
			node1_6.setText("应急演练管理");
			node1_6.setKey("root1_6");
			node1_6.setHref("javascript:{refreshRight('root1_6')}");
			node1_6.setParentKey("root1");
			list.add(node1_6);

			SanTreeNode node1_7=new SanTreeNode();
			node1_7.setText("演练评估管理");
			node1_7.setKey("root1_7");
			node1_7.setHref("javascript:{refreshRight('root1_7')}");
			node1_7.setParentKey("root1");
			list.add(node1_7);

			SanTreeNode node1_8=new SanTreeNode();
			node1_8.setText("预案维护管理");
			node1_8.setKey("root1_8");
			node1_8.setHref("javascript:{refreshRight('root1_8')}");
			node1_8.setParentKey("root1");
			list.add(node1_8);

			SanTreeNode node1_9=new SanTreeNode();
			node1_9.setText("预案统计分析");
			node1_9.setKey("root1_9");
			node1_9.setHref("javascript:{refreshRight('root1_9')}");
			node1_9.setParentKey("root1");
			list.add(node1_9);

			SanTreeNode node1_10=new SanTreeNode();
			node1_10.setText("数据导入导出");
			node1_10.setKey("root1_10");
			node1_10.setHref("javascript:{refreshRight('root1_10')}");
			node1_10.setParentKey("root1");
			list.add(node1_10);
		*/
		/////end 风险评估



		/////begin 业务影响分析
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setText("业务影响分析");
		node1.setKey("yewuyingxianfenxi");
		node1.setParentKey("0");
		list.add(node1);
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setText("应用系统定义");
			node1_1.setKey("yewuyingxianfenxi_1");
			node1_1.setParentKey("yewuyingxianfenxi");
			list.add(node1_1);

			SanTreeNode node1_2=new SanTreeNode();
			node1_2.setText("相关分析");
			node1_2.setKey("yewuyingxianfenxi_2");
			node1_2.setParentKey("yewuyingxianfenxi");
			list.add(node1_2);

			SanTreeNode node1_3=new SanTreeNode();
			node1_3.setText("重要性分析");
			node1_3.setKey("yewuyingxianfenxi_3");
			node1_3.setParentKey("yewuyingxianfenxi");
			list.add(node1_3);

			SanTreeNode node1_4=new SanTreeNode();
			node1_4.setText("依赖性分析");
			node1_4.setKey("yewuyingxianfenxi_4");
			node1_4.setParentKey("yewuyingxianfenxi");
			list.add(node1_4);

			SanTreeNode node1_5=new SanTreeNode();
			node1_5.setText("分析报告编写");
			node1_5.setKey("yewuyingxianfenxi_5");
			node1_5.setParentKey("yewuyingxianfenxi");
			list.add(node1_5);

			SanTreeNode node1_6=new SanTreeNode();
			node1_6.setText("分析报告审核");
			node1_6.setKey("yewuyingxianfenxi_6");
			node1_6.setParentKey("yewuyingxianfenxi");
			list.add(node1_6);

			SanTreeNode node1_7=new SanTreeNode();
			node1_7.setText("分析报告发布");
			node1_7.setKey("yewuyingxianfenxi_7");
			node1_7.setParentKey("yewuyingxianfenxi");
			list.add(node1_7);

			SanTreeNode node1_8=new SanTreeNode();
			node1_8.setText("分析报告维护");
			node1_8.setKey("yewuyingxianfenxi_8");
			node1_8.setParentKey("yewuyingxianfenxi");
			list.add(node1_8);

			SanTreeNode node1_9=new SanTreeNode();
			node1_9.setText("业务分析查询");
			node1_9.setKey("yewuyingxianfenxi_9");
			node1_9.setParentKey("yewuyingxianfenxi");
			list.add(node1_9);

			SanTreeNode node1_10=new SanTreeNode();
			node1_10.setText("数据导入导出");
			node1_10.setKey("yewuyingxianfenxi_10");
			node1_10.setParentKey("yewuyingxianfenxi");
			list.add(node1_10);
		*/
		////end 业务影响分析


		/////首页
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setText("风险评估");
		node1.setKey("root1");
		node1.setHref("javascript:{refreshRight('root1')}");
		node1.setDynamicLoading(1);
		node1.setParentKey("0");
		list.add(node1);

		SanTreeNode node2=new SanTreeNode();
		node2.setText("业务影响分析");
		node2.setKey("root2");
		node2.setHref("javascript:{refreshRight('root2')}");
		node2.setDynamicLoading(1);
		node2.setParentKey("0");
		list.add(node2);

		SanTreeNode node3=new SanTreeNode();
		node3.setText("预案管理");
		node3.setKey("root3");
		node3.setHref("javascript:{refreshRight('root3')}");
		node3.setDynamicLoading(1);
		node3.setParentKey("0");
		node3.setIsCurrentNode("true");

		list.add(node3);
			SanTreeNode node3_1=new SanTreeNode();
			node3_1.setText("预案规划管理");
			node3_1.setKey("root3_1");
			node3_1.setHref("javascript:{refreshRight('root3_1')}");
			node3_1.setParentKey("root3");
			list.add(node3_1);

			SanTreeNode node3_2=new SanTreeNode();
			node3_2.setText("预案编制管理");
			node3_2.setKey("root3_2");
			node3_2.setHref("javascript:{refreshRight('root3_2')}");
			node3_2.setParentKey("root3");
			list.add(node3_2);

			SanTreeNode node3_3=new SanTreeNode();
			node3_3.setText("预案发布管理");
			node3_3.setKey("root3_3");
			node3_3.setHref("javascript:{refreshRight('root3_3')}");
			node3_3.setParentKey("root3");
			list.add(node3_3);

			SanTreeNode node3_4=new SanTreeNode();
			node3_4.setText("预案培训管理");
			node3_4.setKey("root3_4");
			node3_4.setHref("javascript:{refreshRight('root3_4')}");
			node3_4.setParentKey("root3");
			list.add(node3_4);

			SanTreeNode node3_5=new SanTreeNode();
			node3_5.setText("应急演练管理");
			node3_5.setKey("root3_5");
			node3_5.setHref("javascript:{refreshRight('root3_5')}");
			node3_5.setParentKey("root3");
			list.add(node3_5);

			SanTreeNode node3_6=new SanTreeNode();
			node3_6.setText("演练评估管理");
			node3_6.setKey("root3_6");
			node3_6.setHref("javascript:{refreshRight('root3_6')}");
			node3_6.setParentKey("root3");
			list.add(node3_6);

			SanTreeNode node3_7=new SanTreeNode();
			node3_7.setText("预案维护管理");
			node3_7.setKey("root3_7");
			node3_7.setHref("javascript:{refreshRight('root3_7')}");
			node3_7.setParentKey("root3");
			list.add(node3_7);

		SanTreeNode node4=new SanTreeNode();
		node4.setText("预警预报");
		node4.setKey("root4");
		node4.setDynamicLoading(1);
		node4.setHref("javascript:{refreshRight('root4')}");
		node4.setParentKey("0");
		list.add(node4);

		SanTreeNode node5=new SanTreeNode();
		node5.setText("应急响应指挥");
		node5.setKey("root5");
		node5.setDynamicLoading(1);
		node5.setHref("javascript:{refreshRight('root5')}");
		node5.setParentKey("0");
		list.add(node5);

		SanTreeNode node6=new SanTreeNode();
		node6.setText("事件统计分析");
		node6.setKey("root6");
		node6.setDynamicLoading(1);
		node6.setHref("javascript:{refreshRight('root6')}");
		node6.setParentKey("0");
		list.add(node6);

		SanTreeNode node7=new SanTreeNode();
		node7.setText("IT资源管理");
		node7.setKey("root7");
		node7.setDynamicLoading(1);
		node7.setHref("javascript:{refreshRight('root7')}");
		node7.setParentKey("0");
		list.add(node7);

		SanTreeNode node8=new SanTreeNode();
		node8.setText("组织人员管理");
		node8.setKey("root8");
		node8.setDynamicLoading(1);
		node8.setHref("javascript:{refreshRight('root8')}");
		node8.setParentKey("0");
		list.add(node8);

		SanTreeNode node9=new SanTreeNode();
		node9.setText("系统管理");
		node9.setKey("root9");
		node9.setDynamicLoading(1);
		node9.setHref("javascript:{refreshRight('root9')}");
		node9.setParentKey("0");
		list.add(node9);
		*/
		/////首页


		//////begin 预警预报管理
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setText("预警预报管理");
		node1.setKey("root1");
		node1.setHref("javascript:{refreshRight('root1')}");
		node1.setParentKey("0");
		list.add(node1);
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setText("威胁信息管理");
			node1_1.setKey("child1");
			node1_1.setHref("javascript:{refreshRight('child1')}");
			node1_1.setParentKey("root1");
			list.add(node1_1);

			SanTreeNode node1_2=new SanTreeNode();
			node1_2.setText("预警信息编制");
			node1_2.setKey("child2");
			node1_2.setIsCurrentNode("true");
			node1_2.setHref("javascript:{refreshRight('child2')}");
			node1_2.setParentKey("root1");
			list.add(node1_2);

			SanTreeNode node1_3=new SanTreeNode();
			node1_3.setText("预警审核管理");
			node1_3.setKey("child3");
			node1_3.setHref("javascript:{refreshRight('child3')}");
			node1_3.setParentKey("root1");
			list.add(node1_3);

			SanTreeNode node1_4=new SanTreeNode();
			node1_4.setText("预警发布管理");
			node1_4.setKey("child4");
			node1_4.setHref("javascript:{refreshRight('child4')}");
			node1_4.setParentKey("root1");
			list.add(node1_4);

			SanTreeNode node1_5=new SanTreeNode();
			node1_5.setText("预警状态管理");
			node1_5.setKey("child5");
			node1_5.setHref("javascript:{refreshRight('child5')}");
			node1_5.setParentKey("root1");
			list.add(node1_5);

			SanTreeNode node1_6=new SanTreeNode();
			node1_6.setText("预警统计分析");
			node1_6.setKey("child6");
			node1_6.setHref("javascript:{refreshRight('child6')}");
			node1_6.setParentKey("root1");
			list.add(node1_6);

			SanTreeNode node1_7=new SanTreeNode();
			node1_7.setText("数据导入导出");
			node1_7.setKey("child7");
			node1_7.setHref("javascript:{refreshRight('child7')}");
			node1_7.setParentKey("root1");
			list.add(node1_7);
		*/
		//////end 预警预报管理

		//////begin 应急响应指挥
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setKey("root1");
		node1.setText("应急响应指挥");
		node1.setHref("javascript:{refreshRight('root1')}");
		node1.setParentKey("0");
		list.add(node1);
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setKey("child1");
			node1_1.setHref("javascript:{refreshRight('child1')}");
			node1_1.setText("事态评估管理");
			node1_1.setIsCurrentNode("true");
			node1_1.setParentKey("root1");
			list.add(node1_1);

			SanTreeNode node1_2=new SanTreeNode();
			node1_2.setKey("child2");
			node1_2.setHref("javascript:{refreshRight('child2')}");
			node1_2.setText("事件监督台");
			node1_2.setParentKey("root1");
			list.add(node1_2);

			SanTreeNode node1_3=new SanTreeNode();
			node1_3.setKey("child3");
			node1_3.setHref("javascript:{refreshRight('child3')}");
			node1_3.setText("紧急事件处置");
			node1_3.setParentKey("root1");
			list.add(node1_3);

			SanTreeNode node1_4=new SanTreeNode();
			node1_4.setKey("child4");
			node1_4.setHref("javascript:{refreshRight('child4')}");
			node1_4.setText("事件回放管理");
			node1_4.setParentKey("root1");
			list.add(node1_4);

			SanTreeNode node1_5=new SanTreeNode();
			node1_5.setKey("child5");
			node1_5.setHref("javascript:{refreshRight('child5')}");
			node1_5.setText("事件报告管理");
			node1_5.setParentKey("root1");
			list.add(node1_5);

			SanTreeNode node1_6=new SanTreeNode();
			node1_6.setKey("child6");
			node1_6.setHref("javascript:{refreshRight('child6')}");
			node1_6.setText("事件统计分析");
			node1_6.setParentKey("root1");
			list.add(node1_6);

			SanTreeNode node1_7=new SanTreeNode();
			node1_7.setKey("child7");
			node1_7.setHref("javascript:{refreshRight('child7')}");
			node1_7.setText("数据导入导出");
			node1_7.setParentKey("root1");
			list.add(node1_7);
		 */
		//////end 应急响应指挥

		///// begin 统计分析管理
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setKey("root1");
		node1.setHref("javascript:{refreshRight('root1')}");
		node1.setText("统计分析管理");
		node1.setParentKey("0");
		list.add(node1);
			SanTreeNode node1_1=new SanTreeNode();
			node1_1.setKey("root1_1");
			node1_1.setHref("javascript:{refreshRight('root1_1')}");
			node1_1.setText("查询器管理");
			node1_1.setParentKey("root1");
			list.add(node1_1);

			SanTreeNode node1_3=new SanTreeNode();
			node1_3.setKey("root1_3");
			node1_3.setHref("javascript:{refreshRight('root1_3')}");
			node1_3.setText("报表模板管理");
			node1_3.setParentKey("root1");
			list.add(node1_3);

			SanTreeNode node1_4=new SanTreeNode();
			node1_4.setKey("root1_4");
			node1_4.setHref("javascript:{refreshRight('root1_4')}");
			node1_4.setText("部门统计分析");
			node1_4.setParentKey("root1");
			list.add(node1_4);

			SanTreeNode node1_5=new SanTreeNode();
			node1_5.setKey("root1_5");
			node1_5.setHref("javascript:{refreshRight('root1_5')}");
			node1_5.setText("人员统计分析");
			node1_5.setIsCurrentNode("true");
			node1_5.setParentKey("root1");
			list.add(node1_5);

			SanTreeNode node1_6=new SanTreeNode();
			node1_6.setKey("root1_6");
			node1_6.setHref("javascript:{refreshRight('root1_6')}");
			node1_6.setText("业务统计分析");
			node1_6.setParentKey("root1");
			list.add(node1_6);

			SanTreeNode node1_7=new SanTreeNode();
			node1_7.setKey("root1_7");
			node1_7.setHref("javascript:{refreshRight('root1_7')}");
			node1_7.setText("资产统计分析");
			node1_7.setParentKey("root1");
			list.add(node1_7);

			SanTreeNode node1_8=new SanTreeNode();
			node1_8.setKey("root1_8");
			node1_8.setHref("javascript:{refreshRight('root1_8')}");
			node1_8.setText("风险统计分析");
			node1_8.setParentKey("root1");
			list.add(node1_8);

			SanTreeNode node1_9=new SanTreeNode();
			node1_9.setKey("root1_9");
			node1_9.setHref("javascript:{refreshRight('root1_9')}");
			node1_9.setText("预案统计分析");
			node1_9.setParentKey("root1");
			list.add(node1_9);

			SanTreeNode node1_10=new SanTreeNode();
			node1_10.setKey("root1_10");
			node1_10.setHref("javascript:{refreshRight('root1_10')}");
			node1_10.setText("预警统计分析");
			node1_10.setParentKey("root1");
			list.add(node1_10);

			SanTreeNode node1_11=new SanTreeNode();
			node1_11.setKey("root1_11");
			node1_11.setHref("javascript:{refreshRight('root1_11')}");
			node1_11.setText("事件统计分析");
			node1_11.setParentKey("root1");
			list.add(node1_11);

			SanTreeNode node1_12=new SanTreeNode();
			node1_12.setKey("root1_12");
			node1_12.setHref("javascript:{refreshRight('root1_12')}");
			node1_12.setText("数据导入导出");
			node1_12.setParentKey("root1");
			list.add(node1_12);

		///// end 统计分析管理
		/*
		SanTreeNode node1=new SanTreeNode();
		node1.setText("应急领导小组");
		node1.setKey("root1");
		node1.setHref("javascript:{refreshRight('root1')}");
		node1.setDynamicLoading(1);
		list.add(node1);

		SanTreeNode node2=new SanTreeNode();
		node2.setText("应急指挥中心");
		node2.setKey("root2");
		node2.setHref("javascript:{refreshRight('root2')}");
		node2.setDynamicLoading(1);
		list.add(node2);

		SanTreeNode node3=new SanTreeNode();
		node3.setText("应急专家组");
		node3.setKey("root3");
		node3.setHref("javascript:{refreshRight('root3')}");
		node3.setDynamicLoading(1);
		list.add(node3);

		SanTreeNode node4=new SanTreeNode();
		node4.setText("事件评估组");
		node4.setKey("root4");
		node4.setHref("javascript:{refreshRight('root4')}");
		node4.setDynamicLoading(1);
		list.add(node4);

		SanTreeNode node5=new SanTreeNode();
		node5.setText("系统恢复组");
		node5.setKey("root5");
		node5.setHref("javascript:{refreshRight('root5')}");
		node5.setDynamicLoading(1);
		list.add(node5);

		SanTreeNode node6=new SanTreeNode();
		node6.setText("网络恢复组");
		node6.setKey("root6");
		node6.setHref("javascript:{refreshRight('root6')}");
		node6.setDynamicLoading(1);
		list.add(node6);

		SanTreeNode node7=new SanTreeNode();
		node7.setText("应用恢复组");
		node7.setKey("root7");
		node7.setHref("javascript:{refreshRight('root7')}");
		node7.setDynamicLoading(1);
		list.add(node7);

		SanTreeNode node8=new SanTreeNode();
		node8.setText("后勤保障组");
		node8.setKey("root8");
		node8.setHref("javascript:{refreshRight('root8')}");
		node8.setDynamicLoading(1);
		list.add(node8);

		SanTreeNode node9=new SanTreeNode();
		node9.setText("设备厂商组");
		node9.setKey("root9");
		node9.setHref("javascript:{refreshRight('root9')}");
		node9.setDynamicLoading(1);
		list.add(node9);

		SanTreeNode node10=new SanTreeNode();
		node10.setText("服务外包组");
		node10.setKey("root10");
		node10.setHref("javascript:{refreshRight('root10')}");
		node10.setDynamicLoading(1);
		list.add(node10);
		*/
	}

	private void getNodesByDynamic(List<SanTreeNode> list){

		SanTreeNode node1=new SanTreeNode();
		node1.setText("中国人1");
		node1.setKey("001");
		node1.setParentKey("0");
		node1.setDynamicLoading(1);
		list.add(node1);

		SanTreeNode node2=new SanTreeNode();
		node2.setText("中国人2");
		node2.setKey("002");
		node2.setParentKey("0");
		node2.setDynamicLoading(1);
		list.add(node2);

		SanTreeNode node3=new SanTreeNode();
		node3.setText("中国人3");
		node3.setKey("003");
		node3.setParentKey("0");
		node3.setDynamicLoading(1);
		list.add(node3);

		SanTreeNode node4=new SanTreeNode();
		node4.setText("中国人4");
		node4.setKey("004");
		node4.setParentKey("0");
		node4.setDynamicLoading(1);
		list.add(node4);
	}

}
