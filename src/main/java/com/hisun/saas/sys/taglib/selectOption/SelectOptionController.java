package com.hisun.saas.sys.taglib.selectOption;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.taglib.tree.SanTreeDataSourceInterface;
import com.hisun.saas.sys.taglib.tree.impl.SanTreeNode;
import com.hisun.saas.sys.taglib.hisunTree.TreeObject;
import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/selectOption")
	public class SelectOptionController extends BaseController{

	@RequestMapping(value = "/ajax/loadSelectQueryTreeInfo")
	public ModelAndView loadSelectQueryTreeInfo(HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			String userParameter=request.getParameter("userParameter")==null?"":request.getParameter("userParameter").toString();
			String dictionaryType=request.getParameter("dictionaryType")==null?"":request.getParameter("dictionaryType").toString();
			String id=request.getParameter("id")==null?"":request.getParameter("id").toString();
			String staticdata=request.getParameter("staticdata")==null?"no":request.getParameter("staticdata").toString();
			String matchingsetup=request.getParameter("matchingsetup")==null?"":request.getParameter("matchingsetup").toString();
			String contextPath1=request.getParameter("contextPath")==null?"":request.getParameter("contextPath").toString();
			String parentChildIsolate=request.getParameter("parentChildIsolate")==null?"":request.getParameter("parentChildIsolate").toString();
			String parentCheckboxEnable=request.getParameter("parentCheckboxEnable")==null?"":request.getParameter("parentCheckboxEnable").toString();
			String parentRadioEnable=request.getParameter("parentRadioEnable")==null?"":request.getParameter("parentRadioEnable").toString();
			String radioOrCheckBox=request.getParameter("radioOrCheckBox")==null?"":request.getParameter("radioOrCheckBox").toString();
			String parentSelectedWithChild=request.getParameter("parentSelectedWithChild")==null?"":request.getParameter("parentSelectedWithChild").toString();
			String parentClearWithChild=request.getParameter("parentClearWithChild")==null?"":request.getParameter("parentClearWithChild").toString();
			String lowerSelectedAllWithSuperior=request.getParameter("lowerSelectedAllWithSuperior")==null?"":request.getParameter("lowerSelectedAllWithSuperior").toString();
			String lowerClearWithSuperior=request.getParameter("lowerClearWithSuperior")==null?"":request.getParameter("lowerClearWithSuperior").toString();
			String lowerClearAllWithSuperior=request.getParameter("lowerClearAllWithSuperior")==null?"":request.getParameter("lowerClearAllWithSuperior").toString();
			String checkboxDisplay=request.getParameter("checkboxDisplay")==null?"":request.getParameter("checkboxDisplay").toString();
			String divId=request.getParameter("divId")==null?"":request.getParameter("divId").toString();
			String dataSource=request.getParameter("dataSource")==null?"":request.getParameter("dataSource").toString();
			String selectvaluetype=request.getParameter("selectvaluetype")==null?"0":request.getParameter("selectvaluetype").toString();
			String queryValue=request.getParameter("queryValue")==null?"":request.getParameter("queryValue").toString();
			String queryKeys=request.getParameter("keys")==null?"":request.getParameter("keys").toString();
			String dynamicAttri=request.getParameter("dynamicAttri")==null?"":request.getParameter("dynamicAttri").toString();
			String rightQueryInfo=request.getParameter("rightQueryInfo")==null?"":request.getParameter("rightQueryInfo").toString();
			String queryinfo=request.getParameter("queryinfo")==null?"":request.getParameter("queryinfo").toString();

			//主要用于解决可能选中的数据太多 通过URL传过来可能会有问题  需要用户自己在数据源初始化选中数据 默认为客服端初始化选中数据 为0  为1时数据源初始化数据
			String initCheckboxValueType=request.getParameter("initCheckboxValueType")==null?"0":request.getParameter("initCheckboxValueType").toString();

			//用于提供是否需要父子影响 0 没如何父子关系的复选  1、子选中 父选中 ;父取消 子全取消   2、父选中 子全部选中  父取消 子全部取消 3、继承树的特殊控制
			String chooseType=request.getParameter("chooseType")==null?"0":request.getParameter("chooseType").toString();
			String useType=request.getParameter("useType")==null?"add":request.getParameter("useType").toString();//下拉类型  为Add时则为增加类型  为query时则为查询类型
			String unitCode="";
			String Treetype="0";
			String openOnclick="";
			if(useType.equals("query")){
				if(staticdata.equals("yes")){
					openOnclick="openOnclick_ByQuery();";
				}else{
					//openOnclick="treeCheckedQuery();";
				}
				parentChildIsolate="true";
				Treetype="1";
				//useType_ByQuery_Keys=useType_ByQuery_Keys.replaceAll(":0","");
			}
			if(rightQueryInfo=="undefined")
				rightQueryInfo="";
//			queryValue = new String(queryValue.getBytes("GBK"));
//			rightQueryInfo=new String(rightQueryInfo.getBytes("GBK"));
			String checkboxFunc="";
			String aclickDefault="";
			String dblonclick="";
			String expandByCheckbox="";//选中父节点时是否展开 0为不展开

			if(chooseType.equals("2")){
				expandByCheckbox="0";
			}

			//设置左边树的点击文本 复选框 图标的方法
			if(radioOrCheckBox.equals("radio")){
				aclickDefault="selected_values('1')";
				dblonclick="selected_values_dblonclick('1');";
			}else{
				if(staticdata.equals("yes")){
					checkboxFunc="treechecked_static(this)";
					aclickDefault="selected_values_checkbox_static()";
				}else{
					checkboxFunc="treechecked(this,'1')";
					aclickDefault="selected_values_checkbox('1')";
				}
			}
			map.put("queryValue",queryValue);
			map.put("queryKeys",queryKeys);
			map.put("rightQueryInfo",rightQueryInfo);
			map.put("initCheckboxValueType",initCheckboxValueType);
			map.put("queryinfo",queryinfo);

			map.put("unitCode",unitCode);
			map.put("Treetype",Treetype);
			map.put("openOnclick",openOnclick);
			map.put("checkboxFunc",checkboxFunc);
			map.put("aclickDefault",aclickDefault);
			map.put("dblonclick",dblonclick);
			map.put("expandByCheckbox",expandByCheckbox);

			map.put("rightQueryInfo",rightQueryInfo);
			map.put("staticdata",staticdata);
			map.put("selectvaluetype",selectvaluetype);
			map.put("userParameter",userParameter);
			map.put("querytype",'1');
			map.put("id",id);
			map.put("matchingsetup",matchingsetup);
			map.put("contextPath1","");
			map.put("parentChildIsolate",parentChildIsolate);
			map.put("parentCheckboxEnable",parentCheckboxEnable);
			map.put("parentRadioEnable",parentRadioEnable);
			map.put("radioOrCheckBox",radioOrCheckBox);
			map.put("checkboxDisplay",checkboxDisplay);
			map.put("divId",divId);
			map.put("dataSource",dataSource);
			map.put("chooseType",chooseType);
			map.put("useType",useType);
			map.put("parentSelectedWithChild",parentSelectedWithChild);
			map.put("parentClearWithChild",parentClearWithChild);
			map.put("lowerSelectedAllWithSuperior",lowerSelectedAllWithSuperior);
			map.put("lowerClearWithSuperior",lowerClearWithSuperior);
			map.put("lowerClearAllWithSuperior",lowerClearAllWithSuperior);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "打开选择框报错！");
			throw new GenericException(e);
		}
		return new ModelAndView("selectOption/selectQueryTreeInfo",map);
	}

	@RequestMapping(value="/ajax/selectQueryData")
	public @ResponseBody
	Map<String,Object> selectQueryData(HttpServletRequest request)throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String querytype=request.getParameter("querytype")==null?"":request.getParameter("querytype");  // 是得到焦点时的查询还是输入时的查询 1为得到焦点 2为查询类型
		String id=request.getParameter("id")==null?"":request.getParameter("id");  // 层id
		String unitCode=request.getParameter("unitCode")==null?"":request.getParameter("unitCode");  // 用户输入的内容
		String code=request.getParameter("code")==null?"":request.getParameter("code");  // 单选的key
		String type=request.getParameter("type")==null?"":request.getParameter("type");  // 单选还是复选类型  1为单选 2为复选
		String isshowtree=request.getParameter("isshowtree")==null?"no":request.getParameter("isshowtree");//是否显示树
		String checkedkeys=request.getParameter("checkedkeys")==null?"":request.getParameter("checkedkeys");  // 复选框选中的key
		String matchingsetup=request.getParameter("matchingsetup")==null?"":request.getParameter("matchingsetup");  // 查询规则
		String fontSize=request.getParameter("fontSize")==null?"":request.getParameter("fontSize");  // 字体大小
		String fontColor=request.getParameter("fontColor")==null?"":request.getParameter("fontColor");  // 字体颜色
		String equalcolor=request.getParameter("equalcolor")==null?"":request.getParameter("equalcolor");  // 字体颜色
		String dictionaryType=request.getParameter("dictionaryType")==null?"":request.getParameter("dictionaryType");  // 字典类别
		String equalsValues=request.getParameter("equalsValues")==null?unitCode:request.getParameter("equalsValues");  // 用于复选接收全部数据去后台匹配
		String allowClear=request.getParameter("allowClear")==null?unitCode:request.getParameter("allowClear");  //单选是否显示空格  用于清空  false为不需要显示空格 true为需要显示空格(仅单级单选)
		if(code.length()>0){
			if(code.substring(code.length()-1).equals(",")){
				code=code.substring(code.length()-1);
			}
		}
		if(dictionaryType.equals("undefined") || dictionaryType==null)
			dictionaryType="";

		String showequalcolor="";
		if(equalcolor.equals(""))
			showequalcolor="#FC6DA7";
		else
			showequalcolor=equalcolor;
		/**
		 * 多级树中使用的属性
		 */
		//String parentkey=request.getParameter("parentkey");
		//String cengCount=request.getParameter("cengCount");
		String dataSource=request.getParameter("dataSource");
		String divId=request.getParameter("divId");
		String checkboxDisplay=request.getParameter("checkboxDisplay");
		String radioOrCheckBox=request.getParameter("radioOrCheckBox");
		String userParameter= StringUtils.trimNullCharacter2Empty(request.getParameter("userParameter")); // 用户数据
		String parentRadioEnable=request.getParameter("parentRadioEnable");
		String parentCheckboxEnable=request.getParameter("parentCheckboxEnable");
		String parentChildIsolate=request.getParameter("parentChildIsolate");
		String contextPath=request.getParameter("contextPath");
		if(!dictionaryType.equals("")){
			if(!userParameter.equals("")){
				String dictionaryString = "DictionaryType:" + dictionaryType;
				if (userParameter.indexOf(dictionaryString) == -1){ // 如果未含有此数据，则追加
					userParameter += ";" + dictionaryString;
				}
			}else{
				userParameter = "DictionaryType:" + dictionaryType; // unitCode;  // 加载 字典类别
			}
		}
		//这里需要注意：如果是动态加载多级树  则需要在数据源取得 queryInfo 做为查询条件
		if(!unitCode.equals("")){ // 单位编码不为空
			String unitCodeString = "queryInfo:"+unitCode;
			if (userParameter.indexOf(unitCodeString) == -1){ // 如果未含有此数据，则追加
				userParameter += ";" + unitCodeString;
			}
		}
		String[] checkedkey=null;
		String[] matchingsetups=matchingsetup.split(",");

		String style="";
		if(fontColor!=""){
			style="color:"+fontColor+";";
		}
		if(fontSize!=""){
			style=style+"font-size:"+fontSize+";";
		}
		try{
			StringBuffer results = new StringBuffer("");
			SanTreeDataSourceInterface obj = ApplicationContextUtil.getBean(dataSource, TreeObject.class);
			obj.setServletContext(request.getServletContext());
			obj.setRequest(request);
			obj.setUserParameter(userParameter);
			List list=new ArrayList();
			if(isshowtree.equals("no")){
				list=obj.getNodes();
			}
			String dataCodes="";
			if(type.equals("2")&&isshowtree.equals("no")){
				if(!unitCode.equals("")){
					String chooseType="radio";

					// 查询是否有精确匹配的数据
					dataCodes=obj.checkTextEqual(equalsValues,userParameter,chooseType,"");
				}
			}else{
				if(!unitCode.equals("")){
					String chooseType="radio";
					if(type.equals("2")){//单级的复选
						chooseType="checkbox";
					}else if(type.equals("3")){//单级的单选(要验证是否有模糊匹配的数据 如果有模糊匹配的数据 则不能返回code)
						chooseType="radioByBlur";
					}else if(type.equals("4")){//单级的单选(要验证是否有模糊匹配的数据 如果有模糊匹配的数据 则不能返回code)
						chooseType="checkboxByBlur";
					}
					// 查询是否有精确匹配的数据
					dataCodes=obj.checkTextEqual(equalsValues,userParameter,chooseType,"");
				}
			}
			boolean isquery=false;//记录是否有查询的记录
			//type为1时是单选
			if(type.equals("1")){
				//isshowtree为yes时是显示多级树
				if(isshowtree.equals("yes")){
					//构造树
					if(!unitCode.equals("")&&!dataCodes.equals("")){
						results.append("gzzzb_returnCodes###"+dataCodes);
					}else{

						/*BuildTreeHtml buildTreeHtml=new BuildTreeHtml(
								divId,
								dataSource,
								checkboxDisplay,
								radioOrCheckBox,
								parentRadioEnable,
								parentCheckboxEnable,
								parentChildIsolate,
								contextPath,
								this.getServletContext(),
								request,
								userParameter,
								"_gzzzb_getChileTree('"+id+"')",
								"no",
								"_gzzzb_selected_values(this,'"+id+"')");
						results.append(buildTreeHtml.getTreeHtmlByServlet());*/
					}
				}
				else{
					if(!unitCode.equals("")&&!dataCodes.equals("")){
						results.append("gzzzb_returnCodes###"+dataCodes);
					}else{
						//没有多级树的下拉
						if(unitCode.equals("")){
							results.append("<table width=\"100%\" border=\"0\" cellSpacing=0 cellPadding=0><TBODY>");
							if(allowClear.equals("true")){
								if(list.size()>0){
									results.append("<tr  key='gzzzb_selectOption_clear' height=20 ><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\" onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','','gzzzb_selectOption_clear');\" style=\"height:1.5em;padding-left:1px;"+style+"\"><font style=\"cursor:hand;\">&nbsp;</font></td></tr>");
								}
							}
							for(int i=0;i<list.size();i++){
								SanTreeNode datainfo=(SanTreeNode)list.get(i);
								String dynamicAttri=datainfo.getDynamicAttri()==null?"":datainfo.getDynamicAttri();
								if(code.equals(datainfo.getKey())){
									results.append("<tr  key='"+datainfo.getKey()+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\" onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"height:1.5em;padding-left:1px;"+style+"\"><font color=\"red\" style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
								}else{
									results.append("<tr  key='"+datainfo.getKey()+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\" onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"height:1.5em;padding-left:1px;"+style+"\"><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
								}
							}
							results.append("</TBODY></table>");
						}else{
							results.append("<table width=\"100%\"  border=\"0\" cellSpacing=0 cellPadding=0><TBODY>");
							for(int i=0;i<list.size();i++){
								SanTreeNode datainfo=(SanTreeNode)list.get(i);

								boolean isshow=false;//记录是否有需要查询的记录  为TRUE时则为该记录为需要的记录、
								boolean isequal=false;//记录是否完全匹配  用于改变颜色
								String name=datainfo.getText().trim();//显示值
								String key=datainfo.getKey();//key
								String dynamicAttri=datainfo.getDynamicAttri()==null?"":datainfo.getDynamicAttri();
								String helpCode1=datainfo.getHelpCode()==null?"":datainfo.getHelpCode().trim();//助记码1
								String helpCode2=datainfo.getHelpCode2()==null?"":datainfo.getHelpCode2().trim();//助记码2
								String fullName=datainfo.getFullName()==null?"":datainfo.getFullName().trim();//全称
								String fullUrl=datainfo.getFullUrl()==null?"":datainfo.getFullUrl().trim();//全路径
								String forShort=datainfo.getForShort()==null?"":datainfo.getForShort().trim();//简称

								//根据二次开发者提供的参数  去查询数据
								for(int m=0;m<matchingsetups.length;m++){
									//matchingsetups[m]等于1 模糊匹配
									if(matchingsetups[m].equals("1")){
										if(m==0){
											if(name.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isshow=true;
												isequal=true;
											}
										}
										if(m==1){
											if(key.equals(unitCode.trim())){
												isshow=true;
												isequal=true;
											}
										}
										if(m==2){
											if(helpCode1.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isshow=true;
												isequal=true;
											}
										}
										if(m==3){
											if(helpCode2.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isshow=true;
												isequal=true;
											}
										}
										if(m==4){
											if(fullName.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isshow=true;
												isequal=true;
											}
										}
										if(m==5){
											if(forShort.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isshow=true;
												isequal=true;
											}
										}
										if(m==6){
											if(fullUrl.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isshow=true;
												isequal=true;
											}
										}
									}else if(matchingsetups[m].equals("2")){
										//matchingsetups[m]等于1 完全匹配
										if(m==0){
											if(name.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isequal=true;
											}
											if(name.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
												isshow=true;
										}
										if(m==1){
											if(key.equals(unitCode.trim())){
												isequal=true;
											}
											if(key.indexOf(unitCode.trim())!=-1)
												isshow=true;
										}
										if(m==2){
											if(helpCode1.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isequal=true;
											}
											if(helpCode1.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
												isshow=true;
										}
										if(m==3){
											if(helpCode2.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isequal=true;
											}
											if(helpCode2.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
												isshow=true;
										}
										if(m==4){
											if(fullName.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isequal=true;
											}
											if(fullName.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
												isshow=true;
										}
										if(m==5){
											if(forShort.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isequal=true;
											}
											if(forShort.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
												isshow=true;
										}
										if(m==6){
											if(fullUrl.toUpperCase().equals(unitCode.toUpperCase().trim())){
												isequal=true;
											}
											if(fullUrl.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
												isshow=true;
										}
									}
								}
								//if((datainfo.getText().toUpperCase()).indexOf(unitCode.toUpperCase().trim())!=-1){
								if(isshow){//判断是否符合条件
									isquery=true;
									if(isequal){//判断是否完全相等  用于改变颜色
										if(querytype.equals("1")){//判断是点击文本框的查询还是输入查询  点击查询则选中  输入查询则改变颜色
											if(code.equals(key)){
												results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><font color=\"red\" style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
											}else{
												results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
											}
										}else{
											if(code.equals(key)){
												results.append("<tr key='"+key+"' height=20><td bgColor=\""+showequalcolor+"\" onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"height:1.5em;padding-left:1px;"+style+"\"><font  color=\"red\" style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
											}else{
												results.append("<tr key='"+key+"' height=20><td bgColor=\""+showequalcolor+"\" onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"height:1.5em;padding-left:1px;"+style+"\"><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
											}
										}
									}else
									if(code.equals(key)){
										results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><font color=\"red\" style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
									}else{
										results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+datainfo.getText()+"','"+datainfo.getKey()+"','"+dynamicAttri+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
									}
								}
							}


							if(isquery==false)//如果没匹配的数据则显示  未找到相关信息
								results.append("<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr>");
							results.append("</TBODY></table>");
						}
					}
				}
			}else{ // 多选
				checkedkey=checkedkeys.split(",");//把选中的key分解成数组
				if(isshowtree.equals("yes")){ // 显示树
					if(!unitCode.equals("")&&!dataCodes.equals("")){
						results.append("gzzzb_returnCodes###"+dataCodes);
					}else{
						/*BuildTreeHtml buildTreeHtml=new BuildTreeHtml(divId,dataSource,checkboxDisplay,
								radioOrCheckBox,parentRadioEnable,parentCheckboxEnable,
								parentChildIsolate,contextPath,this.getServletContext(),request,userParameter,"_gzzzb_getChileTree('"+id+"')","treechecked(this,'"+id+"')","selected_values_checkbox(this,'"+id+"')");
						results.append(buildTreeHtml.getTreeHtmlByServlet());
						*/
					}

				}
				else{
					//if(!unitCode.equals("")&&!dataCodes.equals("")){
					//	results.append("gzzzb_returnCodes###"+dataCodes);
					//}else{
					if(unitCode.equals("")){//判断输入的查询是否为空  为空则显示全部数据
						results.append("<table width=\"100%\" cellSpacing=0 cellPadding=0><TBODY>");
						for(int i=0;i<list.size();i++){

							boolean ischecked=false;
							SanTreeNode datainfo=(SanTreeNode)list.get(i);
							if(checkedkey!=null){
								for(int n=0;n<checkedkey.length;n++){
									if(!checkedkey[n].equals("")&&!checkedkey[n].equals(" ")){
										if(datainfo.getKey().equals(checkedkey[n].trim())){
											ischecked=true;
										}
									}
								}
							}
							if(ischecked==true)
								results.append("<tr height=20 key='"+datainfo.getKey()+"'><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+datainfo.getKey()+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\" checked/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
							else
								results.append("<tr height=20 key='"+datainfo.getKey()+"'><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+datainfo.getKey()+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
						}
						results.append("</TBODY></table>");
					}else{
						results.append("<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY>");
						for(int i=0;i<list.size();i++){
							boolean ischecked=false;
							SanTreeNode datainfo=(SanTreeNode)list.get(i);
							boolean isshow=false;
							boolean isequal=false;//记录是否完全匹配
							String name=datainfo.getText().trim();//显示值
							String key=datainfo.getKey();//key

							String helpCode1=datainfo.getHelpCode()==null?"":datainfo.getHelpCode().trim();//助记码1
							String helpCode2=datainfo.getHelpCode2()==null?"":datainfo.getHelpCode2().trim();//助记码2
							String fullName=datainfo.getFullName()==null?"":datainfo.getFullName().trim();//全称
							String fullUrl=datainfo.getFullUrl()==null?"":datainfo.getFullUrl().trim();//全路径
							String forShort=datainfo.getForShort()==null?"":datainfo.getForShort().trim();//简称
							for(int m=0;m<matchingsetups.length;m++){
								if(matchingsetups[m].equals("1")){
									if(m==0){
										if(name.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isshow=true;
											isequal=true;
										}
									}
									if(m==1){
										if(key.equals(unitCode.trim())){
											isshow=true;
											isequal=true;
										}
									}
									if(m==2){
										if(helpCode1.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isshow=true;
											isequal=true;
										}
									}
									if(m==3){
										if(helpCode2.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isshow=true;
											isequal=true;
										}
									}
									if(m==4){
										if(fullName.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isshow=true;
											isequal=true;
										}
									}
									if(m==5){
										if(forShort.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isshow=true;
											isequal=true;
										}
									}
									if(m==6){
										if(fullUrl.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isshow=true;
											isequal=true;
										}
									}
								}else if(matchingsetups[m].equals("2")){
									if(m==0){
										if(name.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isequal=true;
										}
										if(name.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
											isshow=true;
									}
									if(m==1){
										if(key.toUpperCase().equals(unitCode.trim())){
											isequal=true;
										}
										if(key.toUpperCase().indexOf(unitCode.trim())!=-1)
											isshow=true;
									}
									if(m==2){
										if(helpCode1.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isequal=true;
										}
										if(helpCode1.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
											isshow=true;
									}
									if(m==3){
										if(helpCode2.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isequal=true;
										}
										if(helpCode2.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
											isshow=true;
									}
									if(m==4){
										if(fullName.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isequal=true;
										}
										if(fullName.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
											isshow=true;
									}
									if(m==5){
										if(forShort.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isequal=true;
										}
										if(forShort.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
											isshow=true;
									}
									if(m==6){
										if(fullUrl.toUpperCase().equals(unitCode.toUpperCase().trim())){
											isequal=true;
										}
										if(fullUrl.toUpperCase().indexOf(unitCode.toUpperCase().trim())!=-1)
											isshow=true;
									}
								}
							}
							if(isshow){
								if(checkedkey!=null){//判断是否选中
									for(int n=0;n<checkedkey.length;n++){
										if(!checkedkey[n].equals("")&&!checkedkey[n].equals(" ")){
											if(datainfo.getKey().equals(checkedkey[n].trim())){
												ischecked=true;//如果传入的key和读取的key相等则为选中
											}
										}
									}
								}
								isquery=true;
								if(ischecked==true){//判断是否选中
									if(isequal){//判断是否完全匹配
										if(querytype.equals("1"))
											results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+key+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\" checked/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
										else{
											results.append("<tr key='"+key+"' height=20><td bgColor=\""+showequalcolor+"\" onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+key+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
										}
									}
									else
										results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+key+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\" checked/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
								}else{
									if(isequal){
										if(querytype.equals("1"))
											results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+key+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
										else{
											results.append("<tr key='"+key+"' height=20><td bgColor=\""+showequalcolor+"\" onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+key+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
										}
									}
									else
										results.append("<tr key='"+key+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;"+style+"\"><input type='checkbox' name='"+id+"_checkbox' value='"+datainfo.getText()+"' key=\""+key+"\" onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+datainfo.getText()+"</font></td></tr>");
								}
							}
						}
						if(isquery==false)
							results.append("<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr>");
						results.append("</TBODY></table>");
					}
				}
			}
			//}
			map.put("resultsHtml",results.toString());

			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value="/ajax/dynamidcSelectQueryData")
	public @ResponseBody
	Map<String,Object> dynamidcSelectQueryData(HttpServletRequest request)throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String queryinfo=request.getParameter("queryinfo")==null?"":request.getParameter("queryinfo");  // 用户输入的内容
		String checkedkeys=request.getParameter("checkedkeys")==null?"":request.getParameter("checkedkeys");  // 复选框选中的key
		String matchingsetup=request.getParameter("matchingsetup")==null?"":request.getParameter("matchingsetup");  // 查询规则
		String dictionaryType=request.getParameter("dictionaryType")==null?"":request.getParameter("dictionaryType");  // 字典类别
		String unitCode=request.getParameter("unitCode")==null?"":request.getParameter("unitCode");  // 当前操作人的单位id
		String radioOrCheckBox=request.getParameter("radioOrCheckBox")==null?"radio":request.getParameter("radioOrCheckBox");  // 是单选还是复选
		String queryType=request.getParameter("queryType")==null?"2":request.getParameter("queryType");  // 查询类型 1为初始化数据返回一组codes 2为查询 返回静态HTML


		if(dictionaryType.equals("undefined"))
			dictionaryType="";
//		SystemHelper.getLog().info(this.getClass(),this.getClass().getSimpleName() + ": dictionaryType:" + dictionaryType);
		String dataSource=request.getParameter("dataSource");

		String userParameter=request.getParameter("userParameter")==null?"":request.getParameter("userParameter");

		if(!dictionaryType.equals("")){
			if(!userParameter.equals("")){
				userParameter = userParameter+";DictionaryType:" + dictionaryType; //加载 字典类别
			}else{
				userParameter = "DictionaryType:" + dictionaryType; // unitCode;  // 加载 字典类别
			}
		}
		//这里需要注意：如果是动态加载多级树  则需要在数据源取得 queryInfo 做为查询条件
		if(!queryinfo.equals("")){
			userParameter=userParameter+";queryInfo:"+queryinfo;
		}
		String[] checkedkey=null;
		String[] matchingsetups=matchingsetup.split(",");

		StringBuffer results = new StringBuffer("");
		try{
			SanTreeDataSourceInterface obj = ApplicationContextUtil.getBean(dataSource, TreeObject.class);
			obj.setServletContext(request.getServletContext());
			obj.setRequest(request);
			obj.setUserParameter(userParameter);
			List list=new ArrayList();
			String dataCodes="";
			String dataTexts="";
			int number=0;
			//判断是取得一组父codes  还是取得搜索html
			if(queryType.equals("1")){
				if(!queryinfo.equals("")&&!queryinfo.equals(" ")){
					dataCodes=obj.getParamentCodesByKeys(queryinfo, userParameter, unitCode);
					results.append(dataCodes);
				}
			}else{

				//如果查询条件不为空  则执行查询匹配到的内容
				if(!queryinfo.equals("")){
					//
					list=obj.getKeysByQueryInfo(queryinfo, userParameter, unitCode);
				}
				if(radioOrCheckBox.equals("radio")){
					if(list!=null){
						dataCodes= (String)list.get(0);
						dataTexts= (String)list.get(1);
						if(!dataCodes.equals("")){
							String[] dataCode=dataCodes.split(";");
							String[] dataText=dataTexts.split(";");
							results.append("<table  border=\"0\" width=\"100%\" cellSpacing=0 cellPadding=0><TBODY>");
							for(int i=0;i<dataCode.length;i++){
								if(!dataCode[i].equals("")){
									number++;
									results.append("<tr key='"+dataCode[i]+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1');\" onmouseout=\"_gzzzb_changecolor(this,'2');\" onclick=\"_gzzzb_changevalues(this,'"+dataCode[i]+"','"+dataText[i]+"');\" ondblclick=\"_gzzzb_changevalues_ondblclick(this,'"+dataCode[i]+"','"+dataText[i]+"');\" style=\"cursor:hand;height:1.5em;padding-left:1px;\" noWrap><font style=\"cursor:hand;\">"+dataText[i]+"</font></td></tr>");
								}
							}
							results.append("</tbody></table>");
						}
					}
				}else{
					//复选查询的方法
					if(!checkedkeys.equals("")){
						checkedkey=checkedkeys.split(",");
					}
					if(list!=null){
						dataCodes= (String)list.get(0);//List返回2个String  第一个是codes  第二个是texts
						dataTexts= (String)list.get(1);
						String[] dataCode=dataCodes.split(";");
						String[] dataText=dataTexts.split(";");
						if(dataCode.length==1){
							results.append("<table  border=\"0\" width=\"100%\" cellSpacing=0 cellPadding=0><TBODY>");

							for(int i=0;i<dataCode.length;i++){
								if(!dataCode[i].equals("")){
									number++;
									results.append("<tr key='"+dataCode[i]+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1');\" onmouseout=\"_gzzzb_changecolor(this,'2');\" onclick=\"_gzzzb_changevalues_checkbox(this,'"+dataCode[i]+"','"+dataText[i]+"');\"  style=\"cursor:hand;height:1.5em;padding-left:1px;\" noWrap><input type='checkbox' name='gzzzb_checkbox' value='"+dataText[i]+"' key=\""+dataCode[i]+"\" onclick=\"checkboxchangevalue_checkbox(this);\" checked/><font style=\"cursor:hand;\">"+dataText[i]+"</font></td></tr>");
								}
							}
							results.append("</tbody></table>");
						}else{
							if(!dataCodes.equals("")){
								if(checkedkeys.equals("")){
									results.append("<table border=\"0\" width=\"100%\" cellSpacing=0 cellPadding=0><TBODY>");

									for(int i=0;i<dataCode.length;i++){
										if(!dataCode[i].equals("")){
											number++;
											results.append("<tr key='"+dataCode[i]+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1');\" onmouseout=\"_gzzzb_changecolor(this,'2');\" onclick=\"_gzzzb_changevalues_checkbox(this,'"+dataCode[i]+"','"+dataText[i]+"');\"  style=\"cursor:hand;height:1.5em;padding-left:1px;\" noWrap><input type='checkbox' name='gzzzb_checkbox' value='"+dataText[i]+"' key=\""+dataCode[i]+"\" onclick=\"checkboxchangevalue_checkbox(this);\"/><font style=\"cursor:hand;\">"+dataText[i]+"</font></td></tr>");
										}
									}
									results.append("</tbody></table>");
								}else{
									results.append("<table border=\"0\" width=\"100%\" cellSpacing=0 cellPadding=0><TBODY>");
									for(int i=0;i<dataCode.length;i++){
										boolean ischecked=false;//用来接收是否有选中的记录
										for(int j=0;j<checkedkey.length;j++){
											number++;
											String code="";
											//此方法用于处理多级的id 如001,002,003 只需取得最后一个003来和传入的key来比较
											if(dataCode[i].indexOf(",")==-1){
												code=dataCode[i];
											}else{
												String[] newcode=dataCode[i].split(",");
												code=newcode[newcode.length-1];
											}
											if(!checkedkey[j].equals("")&&!checkedkey[j].equals(" ")&&checkedkey[j].equals(code))
												ischecked=true;
										}
										if(ischecked)
											results.append("<tr key='"+dataCode[i]+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1');\" onmouseout=\"_gzzzb_changecolor(this,'2');\" onclick=\"_gzzzb_changevalues_checkbox(this,'"+dataCode[i]+"','"+dataText[i]+"');\"  style=\"cursor:hand;height:1.5em;padding-left:1px;\" noWrap><input type='checkbox' name='gzzzb_checkbox' value='"+dataText[i]+"' key=\""+dataCode[i]+"\" onclick=\"checkboxchangevalue_checkbox(this);\" checked/><font style=\"cursor:hand;\">"+dataText[i]+"</font></td></tr>");
										else
											results.append("<tr key='"+dataCode[i]+"' height=20><td onmouseover=\"_gzzzb_changecolor(this,'1');\" onmouseout=\"_gzzzb_changecolor(this,'2');\" onclick=\"_gzzzb_changevalues_checkbox(this,'"+dataCode[i]+"','"+dataText[i]+"');\"  style=\"cursor:hand;height:1.5em;padding-left:1px;\" noWrap><input type='checkbox' name='gzzzb_checkbox' value='"+dataText[i]+"' key=\""+dataCode[i]+"\" onclick=\"checkboxchangevalue_checkbox(this);\"/><font style=\"cursor:hand;\">"+dataText[i]+"</font></td></tr>");
									}
									results.append("</tbody></table>");
								}
							}
						}
					}
				}
			}
			if(number==1&&queryType.equals("2")){
				results.append("#$#$#$#$"+dataCodes);
				results.append("#$#$#$#$"+dataTexts);
			}
			map.put("resultsHtml",results.toString());

			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
}
