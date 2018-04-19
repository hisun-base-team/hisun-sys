<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="/WEB-INF/tld/Tree.tld" prefix="Hisuntree" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
<link href="${path }/css/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>下拉多级树选择卡</title>
</head>



<body class="content_wrap"  scroll="no">
<form action="form1" id="form1">
<input type="hidden" name="defulatKeys" id="defulatKeys" value=""/>
<input type="hidden" name="queryinfo" id="queryinfo" value=""/>
<input type="hidden" name="checkedkeys" id="checkedkeys" value=""/>

<TABLE height="100%" width="100%" cellspacing="1" cellpadding="1" border="0">
	<tr height="500px" >
		<td width="350" style="border-right-width: 1px;border-right-style: solid;border-right-color: #71ADFF;">
			<TABLE height="100%" width="100%" cellspacing="1" cellpadding="1" border="0">
				<TR height="21" valign="middle">
					<TD  width="100%">
						<TABLE  height="21"  width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr height="21">
								<td width="100%" style="padding-left:0px;height:21px;padding-top:0px;">
									<input type="text" name="selectValue" title="此处显示的为您选中的数据" value="" readonly style="width:95%;height:21px"/>
									<input type="hidden" name="selectkey" value=""/>
									<input type="hidden" name="dynamicAttri" value=""/>
									<input type="hidden" name="queryKeys" value=""/><!-- 查询选择中用于接受显示在文本框里的id -->
								</td>
							</tr>
						</table>
					</TD>
				</TR>
				<TR valign="top">
					<TD width="100%">
						<div id="showTree" style="height:100%;width:350px;overflow-x:auto;overflow-y:auto;z-index:1;"><Hisuntree:tree type="${Treetype }" expandByCheckbox="${expandByCheckbox}"
											openOnclick="${openOnclick }" divId="showTree" checkboxclickDefault="${checkboxFunc }"
											dblonclick="${dblonclick }" aclickDefault="${aclickDefault }" userParameter="${userParameter }"
											checkboxDisplay="${checkboxDisplay }" radioOrCheckbox="${radioOrCheckBox }"
											parentRadioEnable="${parentRadioEnable }" parentCheckboxEnable="${parentCheckboxEnable }"
											parentChildIsolate="${parentChildIsolate }" parentSelectedWithChild="${parentSelectedWithChild }"
											parentClearWithChild="${parentClearWithChild }" lowerSelectedAllWithSuperior="${lowerSelectedAllWithSuperior }"
											lowerClearWithSuperior="${lowerClearWithSuperior }" lowerClearAllWithSuperior="${lowerClearAllWithSuperior }"
											dataSource="${dataSource }" ></Hisuntree:tree></div>
					</TD>
				</TR>
			</table>
		</td>
		<td>
			<TABLE  width="100%" height="100%" cellspacing="1" cellpadding="1" border="0">
				<TR height="10" valign="middle">
					<TD style="align:left;valign:middle;">

						<div class="input-append date form_datetime">

							<input type="text" style="width:250px;" placeholder="请在此输入搜索条件..." class="span6 m-wrap" name="queryValueInfo" value=""  onchange="queryInfo(this);" onkeyup="javascript:{if(event.keyCode==13){queryInfo_Onkeyup(this);}}"/>
							<span class="add-on" onclick="clearQuery();"><i class="icon-remove"></i></span>
							<span class="add-on" onclick="queryInfo_buttom();"><i class="icon-search"></i></span>
							<%--<img style="border:0;margin-top:0px;padding-top:0px;cursor:hand;" title="点击此处开始查询" onclick="javascript:queryInfo_buttom();" src="${contextPath}/images/option/lookup.gif"/>--%>
						</div>
					</TD>
				</TR>
				<TR valign="top">
					<TD width="100%">
						<div id="showQueryTree" nowrap style="z-Index:1;swhite-space:nowrap;height:100%;width:320;overflow-x:auto;overflow-y:auto;">
							 &nbsp;
						</div>
					</TD>
				</TR>
			</table>
		</td>
	</tr>
	<tr height="10%" valign="middle">
		<td align="center" colspan="2"  style="border-top-width: 1px;border-top-style: solid;	border-top-color: #71ADFF;">
			<button type="button" class="btn green"  onclick="save()" id="listBtnOk"><i class="icon-ok"></i> 确定</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn"  onclick="myClear()"><i class="icon-refresh"></i> 清空 </button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-default" data-dismiss="modal"><i class='icon-remove-sign'></i> 关闭</button>
		</td>
	</tr>
</TABLE>
</form>
<script language=javascript>
	var defulatKeysobj=document.getElementById("defulatKeys")
	var selectValueobj=document.all("selectValue");
	var selectkeyobj=document.all("selectkey");
	var dynamicAttriobj=document.all("dynamicAttri");
	var showTreeobj=document.all("showTree");
	var queryInfoobj=document.all("queryValueInfo");
	var showQueryTreeobj=document.all("showQueryTree");
	var queryKeysObj=document.all("queryKeys");
	var _gzzzbtext_queryInfoobj=document.all("_gzzzbtext_queryValueInfo");


	var array=new Array();
	var isUnlawfulClose=true;
	//加载完页面初始化搜索数据或勾中复选的选中项

	(function(){
		try{

			var useType="${useType}";
			//取得传入的参数 传入的参数为包含key value dynamicAttri的对象数组 （用于解决选择的个数过多而导致url超长的问题）
			var oMyObject = window.dialogArguments;
			var keys="";
			var queryinfo="";
			var dynamicAttri="";
//		if(oMyObject!=""){
//			keys=oMyObject.keys;
//			queryinfo=oMyObject.queryinfo;
//			dynamicAttri=oMyObject.dynamicAttri;
//		}else{
			keys="${queryKeys}";
			queryinfo="${queryinfo}";
			dynamicAttri="${dynamicAttri}";
//		}
			//如果为查询类型 则要把传入的key的有:0的全部替换为空（‘’）
			if(useType=="query"){
				var re = /:0/g;
				keys=keys.replace(re,"");
			}
			defulatKeysobj.value=keys;
			selectkeyobj.value=keys;

			selectValueobj.value=queryinfo;
			dynamicAttriobj.value=dynamicAttri;


			var initCheckboxValueType="${initCheckboxValueType}";
			if(initCheckboxValueType=="0"){
				systemInit();
			}else{
				userInit();
			}

		}catch(e){
			alert("onload:"+e.description);
		}
	})();
	//由控件初始化默认值
	function systemInit(){
		try{
			var results=null;
			var queryValue=selectValueobj.value;
			var queryKeys=defulatKeysobj.value;
			var matchingsetup="${matchingsetup}";
			var radioOrCheckBox="${radioOrCheckBox}";
			var rightQueryInfo="${rightQueryInfo}";
			var staticdata="${staticdata}";
			var useType="${useType}";
			var tree=getObjByMainDivId ("showTree");
			if(tree==null){
				return false;
			}

			//判断是否是静态的数据源
			if(staticdata=="no"){
				if(queryKeys == ""){
					//tree.openFirstNode();
				}

				if(radioOrCheckBox=="radio"){
					if(queryKeys!=""){
						//根据传入的key得到节点对象集合
						results=tree.getNodesByKeys(queryKeys,"1");
					}else if(queryValue!=""){

						results=tree.getNodesByTexts(queryValue,matchingsetup);
					}
					if(results!=null&&results!="")
						tree.setCurrentNodeByNode(results[0]);
				}else{
					if(queryKeys!=""){
						//根据传入的key得到一个对象集  包含了所有匹配的对象
						results=tree.getNodesByKeys(queryKeys,"1");
						if(results!=null&&results!=""){
							for(var i=0;i<results.length;i++){
								//设置命中的对象为选中状态
								tree.setCheckboxSelected(results[i],true);
							}
						}
					}else if(queryValue!=""){
						results=tree.getNodesByTexts(queryValue,matchingsetup);
					}
				}
				//如果集合不为空  则把集合中所有的节点展开
				if(results!=null&&results!=""){
					for(var i=0;i<results.length;i++){
						//节点展开的方法
						tree.ExpandNodeAndNextNode(results[i]);
					}
				}
				//判断是否传入的要查询的内容
				if(rightQueryInfo!=""){
					selectValueobj.value=queryValue;
					selectkeyobj.value=queryKeys;
					queryInfoobj.value=rightQueryInfo;
					//调用查询的方法
					queryInfo(queryInfoobj);
					//把右边搜索的提示隐藏
					//_gzzzbtext_queryInfoobj.style.visibility="hidden";
				}else{
					//如果传入的value和key都不为空  则直接把数据赋值给selectValueobj对象及selectkeyobj对象
					if(queryKeys!=""&&queryValue!=""){
						selectValueobj.value=queryValue;
						selectkeyobj.value=queryKeys;
						//如果key为空 而value不为空  则当作查询调用
					}else if(queryKeys==""&&queryValue!=""){
						if(radioOrCheckBox=="radio"){
							queryInfoobj.value=queryValue;
							queryInfo(queryInfoobj);
							if(queryValue!="")
								_gzzzbtext_queryInfoobj.style.visibility="hidden";

						}else{
							queryInfoobj.value=queryValue;
							queryInfo(queryInfoobj);
							if(queryValue!="")
								_gzzzbtext_queryInfoobj.style.visibility="hidden";
						}

					}
				}
			}else{
				//执行动态数据源的方法
				staticOnload();
				//如果传入的查询内容不为空 则执行查询
				if(rightQueryInfo!=""){
					queryInfoobj.value=rightQueryInfo;
					//调用查询的方法
					queryInfo(queryInfoobj);
					//把右边搜索的提示隐藏
//					把右边搜索的提示隐藏_gzzzbtext_queryInfoobj.style.visibility="hidden";
				}
			}
		}catch(e){
			alert("systemInit:"+e.description);
		}
	}

	//由用户初始化默认值 只有静态的复选能看到去区别 （用户方法 初始化选中有二次开发着实现 控件需要把二次开发着选中的数据读取出来）
	function userInit(){
		try{
			var results=null;

			var matchingsetup="${matchingsetup}";
			var radioOrCheckBox="${radioOrCheckBox}";
			var rightQueryInfo="${rightQueryInfo}";
			var staticdata="${staticdata}";
			var tree=getObjByMainDivId ("showTree");
			var selectvaluetype="${selectvaluetype}";
			var queryValue="";
			var queryKeys="";
			if(tree==null){
				return false;
			}
			if(staticdata=="no"){
				if(radioOrCheckBox=="radio"){
					if(queryKeys!=""){
						results=tree.getNodesByKeys(queryKeys,"1");
					}else if(queryValue!=""){
						results=tree.getNodesByTexts(queryValue,matchingsetup);
					}
					if(results!=null&&results!="")
						tree.setCurrentNodeByNode(results[0]);
				}else{
					queryValue="";
					queryKeys="";
					var CurrentDynamicAttriValue="";
					var allCheckboxArr=null;
					if(tree!=null){
						//取得所有选中的节点的属性集合
						allCheckboxArr=tree.getSelectedNodeAttribute();
					}

					if(allCheckboxArr!=null){
						//如果集合长度大于0 则把选中的节点的属性读取出来
						for(var i=0;i<allCheckboxArr.length;i++){
							if(queryKeys==""){
								queryKeys=allCheckboxArr[i][1];
								if(selectvaluetype=="1"){
									queryValue=allCheckboxArr[i][7];
								}else{
									queryValue=allCheckboxArr[i][4];
								}
								CurrentDynamicAttriValue=allCheckboxArr[i][5];
							}else{
								queryKeys=queryKeys+","+allCheckboxArr[i][1];
								if(selectvaluetype=="1"){
									queryValue=queryValue+","+allCheckboxArr[i][7];
								}else{
									queryValue=queryValue+","+allCheckboxArr[i][4];
								}
								CurrentDynamicAttriValue=CurrentDynamicAttriValue+","+allCheckboxArr[i][5];
							}
						}
					}
					dynamicAttriobj.value=CurrentDynamicAttriValue;

				}
				//如果集合不为空  则把集合中所有的节点展开
				if(results!=null&&results!=""){
					for(var i=0;i<results.length;i++){
						tree.ExpandNodeAndNextNode(results[i]);
					}
				}
				//判断是否传入的要查询的内容
				if(rightQueryInfo!=""){
					selectValueobj.value=queryValue;
					selectkeyobj.value=queryKeys;
					queryInfoobj.value=rightQueryInfo;
					//调用查询的方法
					queryInfo(queryInfoobj);
					//把右边搜索的提示隐藏
					_gzzzbtext_queryInfoobj.style.visibility="hidden";
				}else{
					if(queryKeys!=""&&queryValue!=""){
						selectValueobj.value=queryValue;
						selectkeyobj.value=queryKeys;

					}else if(queryKeys==""&&queryValue!=""){
						if(radioOrCheckBox=="radio"){
							queryInfoobj.value=queryValue;
							queryInfo(queryInfoobj);
							if(queryValue!="")
								_gzzzbtext_queryInfoobj.style.visibility="hidden";

						}else{
							queryInfoobj.value=queryValue;
							queryInfo(queryInfoobj);
							if(queryValue!="")
								_gzzzbtext_queryInfoobj.style.visibility="hidden";
						}

					}
				}
			}else{
				//执行动态数据源的方法
				staticOnload();
				//如果传入的查询内容不为空 则执行查询

				if(rightQueryInfo!=""){
					queryInfoobj.value=rightQueryInfo;
					queryInfo(queryInfoobj);
					_gzzzbtext_queryInfoobj.style.visibility="hidden";
				}
			}
		}catch(e){
			alert("userInit:"+e.description);
		}
	}
	//var index=0;
	//var CheckboxCode;
	function staticOnload(){
		try{
			var useType="${useType}";
			var contextPath1="${contextPath1}";
			var userParameter="${userParameter}";
			var radioOrCheckBox="${radioOrCheckBox}";
			var dataSource="${dataSource}";
			var unitCode="${unitCode}";
			var queryinfo=selectValueobj.value;

			var queryKeys=defulatKeysobj.value;
			var matchingsetup="${matchingsetup}";
			var radioOrCheckBox="${radioOrCheckBox}";
			var tree1=getObjByMainDivId("showTree");
			if(tree1==null){
				return false;
			}
			//如果传入的值不为空 则调用servlet查询该内容是否存在
			if(queryinfo!=""){
				document.all("queryinfo").value = queryKeys;

				$.ajax({
					async:false,
					type:"POST",
					url : contextPath+"/sys/selectOption/ajax/dynamidcSelectQueryData",
					dataType : "json",
					headers:{
						"OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
					},
					data:{
						"queryType":1,
						"userParameter":userParameter,
						"dataSource":dataSource,
						"unitCode":unitCode,
						"radioOrCheckBox":radioOrCheckBox,
						"queryinfo":queryKeys
					},
					success:function(json){
						var reqText = json.resultsHtml;
						if(reqText!=null && reqText!=""){
							if(radioOrCheckBox=="radio"){
								tree1.ExpandSelectedNodeByKeys(reqText);
							}else{
								var code=reqText.split(";");
								tree1.ExpandByKeys(code);
							}
						}
					},
					error : function(){
						myLoading.hide();
						showTip("提示","出错了,请检查网络!",2000);
					}
				});
//	   		var form = document.getElementById('form1');
//		   	var b = new AJAXInteraction(contextPath1+"/GetDynamicSelectQueryDataAjaxServlet.san?queryType=1&userParameter="+userParameter+"&dataSource="+dataSource+"&unitCode="+unitCode+"&radioOrCheckBox="+radioOrCheckBox, function(reqText) {
//				if(reqText!=null && reqText!=""){
//					if(radioOrCheckBox=="radio"){
//						tree1.ExpandSelectedNodeByKeys(reqText);
//					}else{
//						var code=reqText.split(";");
//						tree1.ExpandByKeys(code);
//					}
//				}
//			});
//			b.doPost(getRequestBody(form));
				selectValueobj.value=queryinfo;
				selectkeyobj.value=queryKeys;
			}
		}catch(e){
			alert("staticOnload:"+e.description);
		}
	}

	/*function staticOnloadCheckBoxChecked(){
	 try{
	 var results;
	 if(index==CheckboxCode.length){
	 }else{
	 var tree1=getObjByMainDivId("showTree");
	 tree1.ExpandNodeByKeys(CheckboxCode[index]);

	 if(CheckboxCode[index].indexOf(",")=="-1"){
	 results=tree1.getNodesByKeys(CheckboxCode[index],"1");

	 }else{
	 var newcode=CheckboxCode[index].split(",");
	 results=tree1.getNodesByKeys(newcode[newcode.length-1],"1");
	 }
	 if(results[0]!=undefined){
	 tree1.setNodeCheckboxSelected(results[0],true);
	 index++;
	 }
	 window.setTimeout("staticOnloadCheckBoxChecked()",100);

	 }
	 }catch(e){
	 alert("staticOnloadCheckBoxChecked:"+e.description);
	 }
	 }*/
	//用于关闭窗口的时候如果有选择的数据 则自动把数据填入文本框
	/*window.onbeforeunload = function(){
	 if(isUnlawfulClose){
	 if(selectValueobj.value!=""){
	 array[0]=selectkeyobj.value;
	 array[1]=selectValueobj.value;
	 array[2]=dynamicAttriobj.value;
	 returnValue=array;
	 }else{
	 returnValue=null;
	 }
	 }
	 }*/
	//提交数据的方法
	function save(){
		try{
			//======================================================================
			var staticdata="${staticdata}";
			var radioOrCheckBox="${radioOrCheckBox}";
			//判断是否是静态的数据源
			if(staticdata=="no"){
				array[0]=selectkeyobj.value;
				array[1]=selectValueobj.value;
				array[2]=dynamicAttriobj.value;

			}else{
				if(radioOrCheckBox=="radio"){
					if(selectkeyobj.value !=""){
						setDynamicAttriByChangeValue(selectkeyobj.value);
					}else{
						array[0]=selectkeyobj.value;
						array[1]=selectValueobj.value;
						array[2]=dynamicAttriobj.value;

					}
				}else{
					array[0]=selectkeyobj.value;
					array[1]=selectValueobj.value;
					array[2]=dynamicAttriobj.value;
				}
			}
			_gzzzb_saveValue(array,"${id}")

			$('#selectOptionModal').modal('hide');
		}catch(e){
			alert("save:"+e.description);
		}
	}
	//查询的方法
	function queryInfo(obj){
		try{
			var radioOrCheckBox="${radioOrCheckBox}";
			var selectvaluetype="${selectvaluetype}";
			var rightQueryInfo="${rightQueryInfo}";
			var useType="${useType}";
			//if(rightQueryInfo!=""&&obj.value=="")
			var staticdata="${staticdata}";
			//判断是动态查询还是静态查询 staticdata为no则是静态查询
			if(staticdata=="no"){
				if(obj.value==""){
					alert("请先输入查询条件!");
					showQueryTreeobj.innerHTML="";
					obj.focus();
					obj.previousSibling.style.visibility="hidden";
				}else{
					var matchingsetup="${matchingsetup}";
					var tree1=getObjByMainDivId("showTree");
					if(tree1!=undefined){
						var matching=matchingsetup.split(",");
						if(radioOrCheckBox=="radio"){
							var querySize=tree1.queryLoadNode(obj.value,showQueryTreeobj,"${contextPath1}","javascript:{}","javascript:{}","selected_values('2')",matching,obj.value,"selected_values_dblonclick('2');");
							//取得查询到的记录条数  用于处理查询结果  如果没查到则提示 没找到相关信息 如果找到以一条 则自动选中且得到焦点 同步左边的对应节点 且展开
							if(parseInt(querySize)==0){
								showQueryTreeobj.innerHTML="<p align=\"center\" style=\"color:#4A71F7;font-size:15px;\">没找到相关信息...</p>";
							}else if(parseInt(querySize)==1){
								var tree2=getObjByMainDivId("showQueryTree");
								var arr=tree2.getAllNodeAttribute();

								//如果selectvaluetype为1 则读取全称  为0时则读取显示字段
								if(selectvaluetype=="1"){
									selectValueobj.value=arr[0][7];
								}else	{
									selectValueobj.value=arr[0][10];
								}
								//如果selectValueobj.value的值为空 则取集合中的第5个属性
								if(selectValueobj.value=="")
									selectValueobj.value=arr[0][4];
								//如果selectValueobj.value的值为空 则取集合中的第9个属性
								if(selectValueobj.value=="")
									selectValueobj.value=arr[0][8];
								//取得key值
								selectkeyobj.value=arr[0][1];

								//通过key取得匹配的集合(只能少于等于1)
								var results=tree2.getNodesByKeys(arr[0][1],"1");
								//如果集合不为空 则设置第一个为当前得到焦点
								if(results!=null&&results!=""){
									tree2.setCurrentNodeByNode(results[0]);
								}
								dynamicAttriobj.value=tree2.getCurrentDynamicAttriValue(results[0]);
								var results1=tree1.getNodesByKeys(arr[0][1],"1");
								if(results1!=null&&results1!=""){
									tree1.setCurrentNodeByNode(results1[0]);
									tree1.ExpandNodeAndNextNode(results1[0]);
								}
							}
						}else{
							var querySize;
							//增加类型
							if(useType=="add"){
								//静态的多级查询
								//得到查询的记录条数
								querySize=tree1.queryLoadNode(obj.value,showQueryTreeobj,"${contextPath1}","javascript:{}","treechecked(this,'2')","selected_values_checkbox('2')",matching,selectkeyobj.value);
							}else{
								//查询类型的方法
								querySize=tree1.queryLoadNode(obj.value,showQueryTreeobj,"${contextPath1}","javascript:{}","treechecked_rigth_query(this)","selected_values_checkbox_rigth_query()",matching,selectkeyobj.value);
							}
							//如果查询的记录条数为0  则提示没找到相关信息
							if(parseInt(querySize)==0){
								showQueryTreeobj.innerHTML="<p align=\"center\" style=\"color:#4A71F7;font-size:15px;\">没找到相关信息...</p>";
							}else if(parseInt(querySize)==1){//如果查询的记录条数为1  则自动选中该记录
								var tree2=getObjByMainDivId("showQueryTree");
								var arr=tree2.getAllNodeAttribute();
								//把该记录的内容填入文本框 同步左边的树
								var results=tree2.getNodesByKeys(arr[0][1],"1");
								if(results!=null&&results!=""){
									tree2.setCurrentNodeByNode(results[0]);
									tree2.setNodeCheckboxSelected(results[0],true);
								}
								var isChecked=false;//用于判断改查询的记录是否在左边已经选中 如果选中则只让其焦点且展开  如果没选中过则选中左边的树 让其焦点且展开
								var results1=tree1.getNodesByKeys(arr[0][1],"1");
								if(results1!=null&&results1!=""){
									isChecked=tree1.getCheckboxSelectedByNode(results1[0]);
									//如果节点没有选中 则设置节点为选中状态
									if(isChecked==false){
										tree1.setNodeCheckboxSelected(results1[0],true);
									}
									//设置当前节点得到焦点
									tree1.setCurrentNodeByNode(results1[0]);
									//展开当前节点
									tree1.ExpandNodeAndNextNode(results1[0]);
								}

								getCheckedValues();
							}else{
								var newkey=selectkeyobj.value;//用来保存新的选中的key
								var tree2=getObjByMainDivId("showQueryTree");
								if(newkeys!=""){
									var newkeys = newkey.split(",");
									for(var i=0;i<newkeys.length;i++){
										var results=tree2.getNodesByKeys(newkeys[i],"1");
										if(results!=null&&results!=""){
											tree2.setNodeCheckboxSelected(results[0],true);
										}
									}
								}
							}
						}
					}
				}
			}else{

				//动态的查询
				var contextPath1="${contextPath1}";
				var userParameter="${userParameter}";
				var radioOrCheckBox="${radioOrCheckBox}";
				var dataSource="${dataSource}";
				var unitCode="${unitCode}";
				var queryinfo=queryInfoobj.value;
				var checkedkeys=selectkeyobj.value;


				document.all("queryinfo").value = queryinfo;
				document.all("checkedkeys").value = checkedkeys;

				//调用servlet 取得查询的数据
				$.ajax({
					async:false,
					type:"POST",
					url : contextPath+"/sys/selectOption/ajax/dynamidcSelectQueryData",
					dataType : "json",
					headers:{
						"OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
					},
					data:{
						"queryType":2,
						"userParameter":userParameter,
						"dataSource":dataSource,
						"unitCode":unitCode,
						"radioOrCheckBox":radioOrCheckBox,
						"queryinfo":queryinfo
					},
					success:function(json){
						var reqText = json.resultsHtml;
						if(reqText!=null && reqText!=""){
							if(reqText.indexOf("#$#$#$#$")==-1){
								showQueryTreeobj.innerHTML=reqText;
								if(useType!="add"){
									window.setTimeout("treechecked_ByQuery_ChangeRightCheckbox()",200);
								}
							}else{
								var arr=reqText.split("#$#$#$#$");
								showQueryTreeobj.innerHTML=arr[0];
								if(radioOrCheckBox=="radio"){
									_gzzzb_changevalues(null,arr[1],arr[2]);
								}else{
									if(useType=="add"){
										checkboxchangevalue_query(arr[1],arr[2]);
									}else{
										var tree1=getObjByMainDivId("showTree");
										showQueryTreeobj.innerHTML=arr[0];
										checkboxchangevalue_ByQuery(true,arr[1]);
										//tree1.ExpandSelectedNodeByKeys(arr[1],true);
										//window.setTimeout("treechecked_ByQuery()",200);
										//window.setTimeout("treechecked_ByQuery_ChangeRightCheckbox()",200);
									}
								}
							}
						}else{
							showQueryTreeobj.innerHTML="<p align=\"center\" style=\"color:#4A71F7;font-size:15px;\">没找到相关信息...</p>";
						}
					},
					error : function(){
						myLoading.hide();
						showTip("提示","出错了,请检查网络!",2000);
					}
				});

			}
		}catch(e){
			alert("queryInfo:"+e.description);
		}
	}
	//用于查询的时候如果只匹配到了一条记录 则直接选中
	function checkboxchangevalue_query(codes,texts){
		try{
			var tree1=getObjByMainDivId("showTree");
			tree1.ExpandSelectedNodeByKeys(codes);
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");

			if(codes.indexOf(",")=="-1"){
				treetext=texts;
				treekey=codes;
			}else{
				var code=codes.split(",");
				var text=texts.split("/");
				treetext=text[text.length-1];
				treekey=code[code.length-1];
			}
			changeCheckedStatue_static(treekey,true,'2')

			getCheckedValues();
		}catch(e){
			alert("checkboxchangevalue:"+e.description);
		}
	}
	function queryInfo_Onkeyup(obj){
		obj.blur();
	}
	/**
	 单选的查询出来的数据的点击内容选中的方法  num为1则是左边树调用  2则为右边树调用
	 */
	function selected_values(num){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			if(num=="1"){
				tree1=getObjByMainDivId("showTree");
			}else{
				tree1=getObjByMainDivId("showQueryTree");
			}
			//得到点击的节点的展开状态
			var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj());
			//判断节点对象是否展开 true为展开  则选中该点击的内容
			if(nodestatue==true){
				//取得点击节点的所有信息
				var arr=tree1.getNodeAttribute(tree1.getCurrentNodeObj());
				//如果selectvaluetype为1 则读取全称  为0时则读取显示字段
				if(selectvaluetype=="1"){
					selectValueobj.value=arr[7];
				}else	{
					selectValueobj.value=arr[10];
				}
				if(selectValueobj.value=="")
					selectValueobj.value=arr[8];

				if(selectValueobj.value=="")
					selectValueobj.value=arr[4];

				selectkeyobj.value=arr[1];
				dynamicAttriobj.value=tree1.getCurrentDynamicAttriValue(tree1.getCurrentNodeObj());
			}

			//用于左边的树得到焦点
			if(num=="2"){
				var results=null;
				var tree2=getObjByMainDivId("showTree");
				//这里有个BUG  如果搜索的是一级节点 则无法命中左边的
				if(arr!=undefined){
					if(arr[1]!=""){
						results=tree2.getNodesByKeys(arr[1],"1");
					}
				}
				if(results!=null&&results!=""){
					tree2.setCurrentNodeByNode(results[0]);
					tree2.ExpandNodeAndNextNode(results[0]);
				}
			}
		}catch(e){
			alert("selected_values:"+e.description);
		}
	}
	//复选选择数据的方法  num为1则是左边树调用  2则为右边树调用  (静态)
	function treechecked(obj,num){
		try{
			var chooseType="${chooseType}";
			var useType="${useType}";
			if(useType=="add"){
				if(chooseType=="0"){
					treechecked_noRelation(obj,num);//没如何父子关系的复选
				}else if(chooseType=="1"){
					treechecked_relation(obj,num);//子选中 父选中 ;父取消 子全取消
				}else if(chooseType=="2"){
					treechecked_relation_ByChilden(obj,num);// 父选中 子全部选中
				}else if(chooseType=="3"){
					treechecked_ByTree(obj,num);//子选中 父选中 ;父取消 子全取消
				}
			}else{
				//进入静态的查询方法
				treeCheckedQuery();
			}
		}catch(e){
			alert("treechecked:"+e.description);
		}
	}

	function treeCheckedQuery(){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;

			var treetext;
			var key;
			var newtext="";
			var newkey="";
			var newQueryKeys="";
			tree1=getObjByMainDivId("showTree");
			var tree2=getObjByMainDivId("showQueryTree");
			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var selectedDynamicAttri=dynamicAttriobj.value;
			var queryKeys=queryKeysObj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");

			var selectedDynamicAttris=selectedDynamicAttri.split(",");

			//如果查询区有数据 则先把查询区的数据全部置为不选中
			if(tree2!=undefined){
				var queryArr=tree2.getAllNodes();
				for(var i=0;i<queryArr.length;i++){
					tree2.setCheckboxSelected(queryArr[i],false);
				}
			}

			var checkedArr=tree1.getAllNodesByStatus("true");
			for(var i=0;i<checkedArr.length;i++){
				//接受子节点状态 0表示全部子节点全部没选中 或节点没展开 （此状态下需要在ID后加：0 表示没影响关系） 1表示子节点全部选中或子节点部分选中 有影响关系
				/*var childStatue=-1;
				 var noCheckedNum=0;//接收子节点里没选中的个数
				 var checkedNum=0;//接收子节点里选中的个数
				 var queryKey=newQueryKeys.split(",");
				 var newkeys=newkey.split(",");
				 var attribute=tree1.getNodeAttribute(checkedArr[i]);
				 //得到所有的子节点
				 var childArr=tree1.getAllChildNodes(checkedArr[i]);

				 for(var j=0;j<childArr.length;j++){
				 var arr2=tree1.getNodeAttribute(childArr[j]);
				 if(arr2[6]=="false"){
				 noCheckedNum++;
				 }else{
				 checkedNum++;
				 }
				 }
				 //如果查询区有数据 则先把左边树中选中的节点选中
				 if(tree2!=undefined){
				 //var arr2=tree2.getNodeAttribute(childArr[i]);
				 var Nodes2=tree2.getNodesByKey(attribute[1],"1");
				 if(Nodes2!=""){
				 tree2.setCheckboxSelected(Nodes2[0],true);
				 }
				 }
				 //alert("value:"+attribute[7]+"==checkedNum:"+checkedNum+"==noCheckedNum:"+noCheckedNum+"==childArr.length:"+childArr.length);
				 //如果字节点长度为0 且 节点状态为-1则把节点状态设置成1

				 if(childArr.length==0&&childStatue==-1){
				 childStatue=1;
				 }
				 if(checkedNum==childArr.length&&childStatue==-1){
				 childStatue=1;
				 }
				 if(noCheckedNum==childArr.length&&childStatue==-1){
				 childStatue=1;
				 }
				 if(checkedNum==0&&childStatue==-1){
				 childStatue=0;
				 }
				 if(noCheckedNum>0&&childStatue==-1){
				 childStatue=0;
				 }
				 */
				var attribute=tree1.getNodeAttribute(checkedArr[i]);
				var queryKey=newQueryKeys.split(",");
				var newkeys=newkey.split(",");
				//接受字节点状态 0表示全部子节点全部没选中 或节点没展开 （此状态下需要在ID后加：0 表示没影响关系） 1表示子节点全部选中或子节点部分选中 有影响关系
				var childStatue = tree1.getValueByName(checkedArr[i],"selectall")

				var parentObj= checkedArr[i];
				var parentText="";//用于接受最高选中的节点内容
				var parentKey="";//用于接受最高选中的节点key
				while(1==1){
					if(parentObj==null){
						break;
					}else{
						var arr2=tree1.getNodeAttribute(parentObj);
						if(arr2[6]=="true"){
							if(selectvaluetype=="1"){
								parentText=arr2[7];//选中的数据内容
							}else{
								parentText=arr2[10];
							}

							if(parentText=="")
								parentText=arr2[8];
							if(parentText=="")
								parentText=arr2[4];

							parentKey=arr2[1];
						}
					}
					parentObj=tree1.getTreeParentNode(parentObj);
				}

				treekey=attribute[1];//选中的key
				if(childStatue==0&&parentText!=""){
					parentText=parentText+"(部分)";
					treekey=treekey+":0";
				}

				var isAddKey=true;//接受是否需要把当前节点的key加进去   如果父节点是部分则需要  如果是全部则不需要
				for(var m=0;m<newkeys.length;m++){
					if(newkeys[m]==parentKey){
						isAddKey=false;
					}
				}
				//alert("name:"+attribute[7]+"＝＝parentText："+parentText+"===isAddKey:"+isAddKey);

				var isEqual=false;//该记录的最高节点信息是否已经加载
				for(var n=0;n<queryKey.length;n++){
					if(queryKey[n]==parentKey){
						isEqual=true;
					}
				}

				if(isEqual==false){
					if(newQueryKeys==""){
						newtext=parentText;
						newQueryKeys=parentKey;
					}else{
						newtext=newtext+","+parentText;
						newQueryKeys=newQueryKeys+","+parentKey;
					}
				}

				if(isAddKey==true){
					if(newkey==""){
						newkey=treekey;
						selectedDynamicAttri=attribute[5];
					}else{
						newkey=newkey+","+treekey;
						selectedDynamicAttri=selectedDynamicAttri+","+attribute[5];
					}
				}else{
					if(treekey==parentKey){
						if(newkey==""){
							newkey=parentKey;
						}else{
							newkey=newkey+","+parentKey;
						}
					}
				}
			}

			queryKeysObj.value=newQueryKeys;
			selectValueobj.value=newtext;
			selectkeyobj.value=newkey;
			dynamicAttriobj.value=selectedDynamicAttri;//保存二次开发者自定义的数据内容
		}catch(e){
			alert("treeCheckedQuery:"+e.description);
		}
	}

	function treechecked_rigth_query(obj){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			tree1=getObjByMainDivId("showQueryTree");

			var arr;
			if(obj!=null){
				if(obj.tagName=="DIV"){
					arr=tree1.getNodeAttribute(obj);
				}else{
					arr=tree1.getNodeAttribute(tree1.getNodeByCheckbox(obj));
				}
			}
			if(selectvaluetype=="1"){
				treetext=arr[7];//选中的数据内容
			}else{
				treetext=arr[10];
			}
			if(treetext=="")
				treetext=arr[8];
			if(treetext=="")
				treetext=arr[4];

			treekey=arr[1];//选中的key


			tree2=getObjByMainDivId("showTree");

			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var selectedDynamicAttri=dynamicAttriobj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");
			var selectedDynamicAttris=selectedDynamicAttri.split(",");

			var Nodes=tree2.getNodesByKey(arr[1],"1");
			obj=Nodes[0];
			tree2.ExpandNodeAndNextNode(obj);
			if(arr[6]=="true"){
				var childArr=tree2.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					var arrNode=tree2.getNodeAttribute(childArr[i]);
					if(arrNode[6]=="false"||arrNode[1]==arr[1]){
						tree2.setCheckboxSelected(childArr[i],true);
						var arr2=tree1.getNodeAttribute(childArr[i]);
						var Nodes2=tree1.getNodesByKey(arr2[1],"1");
						if(Nodes2!=""){
							tree1.setCheckboxSelected(Nodes2[0],true);
						}
					}
				}
			}else{
				var childArr=tree2.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					tree2.setCheckboxSelected(childArr[i],false);
					var arr2=tree1.getNodeAttribute(childArr[i]);
					var Nodes2=tree1.getNodesByKey(arr2[1],"1");
					if(Nodes2!=""){
						tree1.setCheckboxSelected(Nodes2[0],false);
					}
				}
			}
			treeCheckedQuery();
		}catch(e){
			alert("treechecked_relation_ByChilden_right:"+e.description);
		}
	}

	function selected_values_checkbox_rigth_query(){
		try
		{
			var tree1=getObjByMainDivId("showQueryTree");
			//取得节点展开状态
			var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj())
			var arr=tree1.getNodeAttribute(tree1.getCurrentNodeObj());
			if(nodestatue==true){
				if(arr[6]=="true"){
					tree1.setCheckboxSelected(tree1.getCurrentNodeObj(),false);
				}else{
					tree1.setCheckboxSelected(tree1.getCurrentNodeObj(),true);
				}
				treechecked_rigth_query(tree1.getCurrentNodeObj());
			}
		}catch(e){
			alert("selected_values_checkbox_rigth_query:"+e.description);
		}
	}

	//不存在父子影响的静态选择数据方法
	function treechecked_noRelation(obj,num){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";

			if(num=="1"){
				tree1=getObjByMainDivId("showTree");
			}else{
				tree1=getObjByMainDivId("showQueryTree");
			}

			var arr;
			if(obj!=null){
				if(obj.tagName=="DIV"){
					arr=tree1.getNodeAttribute(obj);
				}else{
					arr=tree1.getNodeAttribute(tree1.getNodeByCheckbox(obj));
				}
			}
			if(selectvaluetype=="1"){
				treetext=arr[7];//选中的数据内容
			}else{
				treetext=arr[10];
			}
			if(treetext=="")
				treetext=arr[8];
			if(treetext=="")
				treetext=arr[4];

			treekey=arr[1];//选中的key

			if(num=="1"){
				tree2=tree1;
			}else{
				tree2=getObjByMainDivId("showTree");
			}

			if(arr[6]=="true"){

				if(num=="1"){
					changeCheckedStatue(arr[1],true,"1")
				}else
					changeCheckedStatue(arr[1],true,"2")
			}else{

				if(num=="1")
					changeCheckedStatue(arr[1],false,"1")
				else
					changeCheckedStatue(arr[1],false,"2")
			}

			/*
			 var textvalue= selectValueobj.value;
			 var key=selectkeyobj.value;
			 var selectedDynamicAttri=dynamicAttriobj.value;
			 var invalues=textvalue.split(",");
			 var keys=key.split(",");
			 var selectedDynamicAttris=selectedDynamicAttri.split(",");
			 if(arr[6]=="true"){
			 if(textvalue==""){
			 newtext=treetext;
			 newkey=treekey;
			 selectedDynamicAttri=arr[5];
			 }else{
			 var iscunz=false;
			 for(var i=0;i<keys.length;i++){
			 if(keys[i]==treekey){
			 iscunz=true;
			 }
			 }
			 if(iscunz==false){
			 newtext=textvalue+","+treetext;
			 newkey=key+","+treekey;
			 selectedDynamicAttri=selectedDynamicAttri+","+arr[5];
			 }else{
			 newtext=textvalue;
			 newkey=key;
			 selectedDynamicAttri=selectedDynamicAttri;
			 }
			 }

			 if(num=="1"){
			 changeCheckedStatue(arr[1],true,"1")
			 }else
			 changeCheckedStatue(arr[1],true,"2")
			 }else{
			 for(var i=0;i<keys.length;i++){
			 if(keys[i]!=treekey&&keys[i]!=""){
			 if(newtext==""){
			 newtext=invalues[i];
			 newkey=keys[i];
			 selectedDynamicAttri=selectedDynamicAttris[i];
			 }else{
			 newtext=newtext+","+invalues[i];
			 newkey=newkey+","+keys[i];
			 selectedDynamicAttri=selectedDynamicAttri+","+selectedDynamicAttris[i];
			 }
			 }
			 }
			 if(num=="1")
			 changeCheckedStatue(arr[1],false,"1")
			 else
			 changeCheckedStatue(arr[1],false,"2")
			 }
			 selectValueobj.value=newtext;
			 selectkeyobj.value=newkey;
			 dynamicAttriobj.value=selectedDynamicAttri;//保存二次开发者自定义的数据内容
			 */


		}catch(e){
			alert("treechecked_noRelation:"+e.description);
		}
	}
	//存在父子影响的静态选择数据方法
	function treechecked_relation(obj,num){
		try{
			if(num=="1"){
				treechecked_relation_left(obj);
			}else{
				treechecked_relation_right(obj);
			}
		}catch(e){
			alert("treechecked_relation:"+e.description);
		}
	}

	//调用树的特殊控制
	function treechecked_ByTree(obj,num){
		try{
			if(num=="1"){
				treechecked_ByTree_left(obj);
			}else{
				treechecked_ByTree_right(obj);
			}
		}catch(e){
			alert("treechecked_relation:"+e.description);
		}
	}
	function treechecked_ByTree_left(obj){
		try{
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			tree1=getObjByMainDivId("showTree");
			tree2=getObjByMainDivId("showQueryTree");
			var arr;

			if(obj!=null){
				if(obj.tagName!="DIV"){
					obj=tree1.getNodeByCheckbox(obj);
				}

			}
			//alert(obj.tagName);
			checkSel(getInputNode(obj),false);

			/*arr=tree1.getNodeAttribute(obj);
			 var parentObj=obj;

			 var textvalue= selectValueobj.value;
			 var key=selectkeyobj.value;
			 var selectedDynamicAttri=dynamicAttriobj.value;
			 var invalues=textvalue.split(",");
			 var keys=key.split(",");
			 var selectedDynamicAttris=selectedDynamicAttri.split(",");
			 if(arr[6]=="true"){
			 while(1==1){
			 if(parentObj==null){
			 break;
			 }else{
			 tree1.setNodeCheckboxSelected(parentObj,true);
			 if(tree2!=undefined){
			 var arr2=tree2.getNodeAttribute(parentObj);
			 var Nodes2=tree2.getNodesByKey(arr2[1],"1");
			 if(Nodes2!=""){
			 tree2.setNodeCheckboxSelected(Nodes2[0],true);
			 }
			 }
			 }
			 parentObj=tree1.getTreeParentNode(parentObj);
			 }
			 }else{
			 if(tree2!=undefined){
			 var arr2=tree2.getNodeAttribute(obj);
			 var Nodes2=tree2.getNodesByKey(arr2[1],"1");
			 if(Nodes2!=""){
			 tree2.setNodeCheckboxSelected(Nodes2[0],false);
			 }
			 }
			 var childArr=tree1.getAllChildNodes(obj);
			 childArr[childArr.length]=obj;
			 for(var i=0;i<childArr.length;i++){
			 arr=tree1.getNodeAttribute(childArr[i]);
			 if(arr[6]=="true"){
			 tree1.setNodeCheckboxSelected(childArr[i],false);
			 if(tree2!=undefined){
			 var arr2=tree2.getNodeAttribute(childArr[i]);
			 var Nodes2=tree2.getNodesByKey(arr2[1],"1");
			 if(Nodes2!=""){
			 tree2.setNodeCheckboxSelected(Nodes2[0],false);
			 }
			 }
			 }
			 }
			 }*/
			getCheckedValues();
		}catch(e){
			alert("treechecked_relation_left:"+e.description);
		}
	}

	//父选中 子全部选中
	function treechecked_relation_ByChilden(obj,num){
		try{
			if(num=="1"){
				treechecked_relation_ByChilden_left(obj);
			}else{
				treechecked_relation_ByChilden_right(obj);
			}
		}catch(e){
			alert("treechecked_relation:"+e.description);
		}
	}

	function treechecked_relation_ByChilden_left(obj){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			tree1=getObjByMainDivId("showTree");
			tree2=getObjByMainDivId("showQueryTree");



			var arr;
			if(obj!=null){
				if(obj.tagName!="DIV"){
					obj=tree1.getNodeByCheckbox(obj);
				}

			}
			arr=tree1.getNodeAttribute(obj);
			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var selectedDynamicAttri=dynamicAttriobj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");
			var selectedDynamicAttris=selectedDynamicAttri.split(",");

			if(arr[6]=="true"){
				tree1.ExpandNodeAndNextNode(obj);
				var childArr=tree1.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					var arrNode=tree1.getNodeAttribute(childArr[i]);
					if(arrNode[6]=="false"||arrNode[1]==arr[1]){
						tree1.setCheckboxSelected(childArr[i],true);
						if(tree2!=undefined){
							var arr2=tree2.getNodeAttribute(childArr[i]);
							var Nodes2=tree2.getNodesByKey(arr2[1],"1");
							if(Nodes2!=""){
								tree2.setCheckboxSelected(Nodes2[0],true);
							}
						}
					}
				}
			}else{
				var childArr=tree1.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					tree1.setCheckboxSelected(childArr[i],false);
					if(tree2!=undefined){
						var arr2=tree2.getNodeAttribute(childArr[i]);
						var Nodes2=tree2.getNodesByKey(arr2[1],"1");
						if(Nodes2!=""){
							tree2.setCheckboxSelected(Nodes2[0],false);
						}
					}
				}
				var arrkeys="";
				for(var j=0;j<childArr.length;j++){
					var arr2=tree1.getNodeAttribute(childArr[j]);
					if(arrkeys==""){
						arrkeys=arr2[1];
					}else{
						arrkeys=arrkeys+","+arr2[1];
					}
				}

			}

			getCheckedValues();

			/*var selectArrObj=tree1.getAllNodesByStatus("true");
			 for(var i=0;i<selectArrObj.length;i++){
			 var selectArr=tree1.getNodeAttribute(selectArrObj[i]);
			 if(selectvaluetype=="1"){
			 treetext=selectArr[7];//选中的数据内容
			 }else{
			 treetext=selectArr[10];
			 }
			 if(treetext=="")
			 treetext=selectArr[8];
			 if(treetext=="")
			 treetext=selectArr[4];

			 treekey=selectArr[1];//选中的key
			 if(newtext==""){
			 newtext=treetext;
			 newkey=treekey;
			 selectedDynamicAttri=selectArr[5];
			 }else{
			 newtext=newtext+","+treetext;
			 newkey=newkey+","+treekey;
			 selectedDynamicAttri=selectedDynamicAttri+","+selectArr[5];
			 }
			 }*/
		}catch(e){
			alert("treechecked_relation_ByChilden_left:"+e.description);
		}
	}

	//按顺序读取所选中的项的数据
	function getCheckedValues(){
		var selectvaluetype="${selectvaluetype}";
		var tree1;
		var tree2;
		var treetext;
		var treekey;
		var newtext="";
		var newkey="";
		tree1=getObjByMainDivId("showTree");
		tree2=getObjByMainDivId("showQueryTree");

		var selectArrObj=tree1.getAllNodesByStatus("true");
		var selectedDynamicAttri;
		for(var i=0;i<selectArrObj.length;i++){
			var selectArr=tree1.getNodeAttribute(selectArrObj[i]);
			if(selectvaluetype=="1"){
				treetext=selectArr[7];//选中的数据内容
			}else{
				treetext=selectArr[10];
			}
			if(treetext=="")
				treetext=selectArr[8];
			if(treetext=="")
				treetext=selectArr[4];

			treekey=selectArr[1];//选中的key
			if(newtext==""){
				newtext=treetext;
				newkey=treekey;
				selectedDynamicAttri=selectArr[5];
			}else{
				newtext=newtext+","+treetext;
				newkey=newkey+","+treekey;
				selectedDynamicAttri=selectedDynamicAttri+","+selectArr[5];
			}
		}
		selectValueobj.value=newtext;
		selectkeyobj.value=newkey;
		dynamicAttriobj.value=selectedDynamicAttri;
	}
	function treechecked_relation_ByChilden_right(obj){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			tree1=getObjByMainDivId("showQueryTree");

			var arr;
			if(obj!=null){
				if(obj.tagName=="DIV"){
					arr=tree1.getNodeAttribute(obj);
				}else{
					arr=tree1.getNodeAttribute(tree1.getNodeByCheckbox(obj));
				}
			}
			if(selectvaluetype=="1"){
				treetext=arr[7];//选中的数据内容
			}else{
				treetext=arr[10];
			}
			if(treetext=="")
				treetext=arr[8];
			if(treetext=="")
				treetext=arr[4];

			treekey=arr[1];//选中的key


			tree2=getObjByMainDivId("showTree");

			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var selectedDynamicAttri=dynamicAttriobj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");
			var selectedDynamicAttris=selectedDynamicAttri.split(",");

			var Nodes=tree2.getNodesByKey(arr[1],"1");
			obj=Nodes[0];
			if(arr[6]=="true"){
				tree1.ExpandNodeAndNextNode(obj);
				var childArr=tree2.getAllChildNodes(obj);

				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					var arrNode=tree2.getNodeAttribute(childArr[i]);
					if(arrNode[6]=="false"||arrNode[1]==arr[1]){

						tree2.setCheckboxSelected(childArr[i],true);
						var arr2=tree1.getNodeAttribute(childArr[i]);
						var Nodes2=tree1.getNodesByKey(arr2[1],"1");
						if(Nodes2!=""){
							tree1.setCheckboxSelected(Nodes2[0],true);
						}
					}
				}
			}else{
				var childArr=tree2.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					tree2.setCheckboxSelected(childArr[i],false);

					var arr2=tree1.getNodeAttribute(childArr[i]);
					var Nodes2=tree1.getNodesByKey(arr2[1],"1");
					if(Nodes2!=""){
						tree1.setCheckboxSelected(Nodes2[0],false);
					}

				}
			}

			getCheckedValues();
		}catch(e){
			alert("treechecked_relation_ByChilden_right:"+e.description);
		}
	}
	function treechecked_relation_left(obj){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			tree1=getObjByMainDivId("showTree");
			tree2=getObjByMainDivId("showQueryTree");
			var arr;

			if(obj!=null){
				if(obj.tagName!="DIV"){
					obj=tree1.getNodeByCheckbox(obj);
				}

			}
			arr=tree1.getNodeAttribute(obj);
			var parentObj=obj;

			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var selectedDynamicAttri=dynamicAttriobj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");
			var selectedDynamicAttris=selectedDynamicAttri.split(",");
			if(arr[6]=="true"){
				while(1==1){
					if(parentObj==null){
						break;
					}else{
						tree1.setNodeCheckboxSelected(parentObj,true);
						if(tree2!=undefined){
							var arr2=tree2.getNodeAttribute(parentObj);
							var Nodes2=tree2.getNodesByKey(arr2[1],"1");
							if(Nodes2!=""){
								tree2.setNodeCheckboxSelected(Nodes2[0],true);
							}
						}
					}
					parentObj=tree1.getTreeParentNode(parentObj);
				}
			}else{
				if(tree2!=undefined){
					var arr2=tree2.getNodeAttribute(obj);
					var Nodes2=tree2.getNodesByKey(arr2[1],"1");
					if(Nodes2!=""){
						tree2.setNodeCheckboxSelected(Nodes2[0],false);
					}
				}
				var childArr=tree1.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					arr=tree1.getNodeAttribute(childArr[i]);
					if(arr[6]=="true"){
						tree1.setNodeCheckboxSelected(childArr[i],false);
						if(tree2!=undefined){
							var arr2=tree2.getNodeAttribute(childArr[i]);
							var Nodes2=tree2.getNodesByKey(arr2[1],"1");
							if(Nodes2!=""){
								tree2.setNodeCheckboxSelected(Nodes2[0],false);
							}
						}
					}
				}
			}
			getCheckedValues();
		}catch(e){
			alert("treechecked_relation_left:"+e.description);
		}
	}

	function treechecked_relation_right(obj){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			var tree2;
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			tree1=getObjByMainDivId("showTree");
			tree2=getObjByMainDivId("showQueryTree");


			var arr;
			if(obj!=null){
				if(obj.tagName!="DIV"){
					obj=tree1.getNodeByCheckbox(obj);
				}

			}
			arr=tree1.getNodeAttribute(obj);
			var Nodes=tree1.getNodesByKey(arr[1],"1");
			obj=Nodes[0];
			var parentObj=obj;
			if(arr[6]=="true"){
				tree1.setNodeCheckboxSelected(obj,true);
				tree1.ExpandNodeAndNextNode(obj);

				while(1==1){
					parentObj=tree1.getTreeParentNode(parentObj);
					if(parentObj==null){
						break;
					}else{
						tree1.setNodeCheckboxSelected(parentObj,true);
						arr=tree1.getNodeAttribute(parentObj);
						var Nodes2=tree2.getNodesByKey(arr[1],"1");
						if(Nodes2!=""){
							tree1.setNodeCheckboxSelected(Nodes2[0],true);
						}
					}

				}
			}else{
				tree1.setNodeCheckboxSelected(obj,false);
				tree1.ExpandNodeAndNextNode(obj);

				var childArr=tree1.getAllChildNodes(obj);
				childArr[childArr.length]=obj;
				for(var i=0;i<childArr.length;i++){
					arr=tree1.getNodeAttribute(childArr[i]);
					if(arr[6]=="true"){
						tree1.setNodeCheckboxSelected(childArr[i],false);
						arr=tree1.getNodeAttribute(childArr[i]);
						var Nodes2=tree2.getNodesByKey(arr[1],"1");
						if(Nodes2!=""){
							tree1.setNodeCheckboxSelected(Nodes2[0],false);
						}
					}
				}
			}
			getCheckedValues();
		}catch(e){
			alert("treechecked_relation_right:"+e.description);
		}
	}
	//复选选中数据的方法   num为1则是左边树调用  2则为右边树调用
	function selected_values_checkbox(num){
		try
		{
			var tree1;
			var tree2;
			if(num=="1"){
				tree1=getObjByMainDivId("showTree");
			}else{
				tree1=getObjByMainDivId("showQueryTree");
				tree2 = getObjByMainDivId("showTree");
			}
			//取得节点展开状态
			var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj())
			var arr=tree1.getNodeAttribute(tree1.getCurrentNodeObj());
			if(nodestatue==true){
				//if(num=="2"){
				if(arr[6]=="true"){
					tree1.setNodeCheckboxSelected(tree1.getCurrentNodeObj(),false);
				}else{
					tree1.setNodeCheckboxSelected(tree1.getCurrentNodeObj(),true);
				}
				//}
				if(num=="2"){
					var Nodes=tree2.getNodesByKey(arr[1],"1");
					if(Nodes!=""){
						tree2.ExpandNodeAndNextNode(Nodes[0]);
					}
				}
				treechecked(tree1.getCurrentNodeObj(),num);
			}
		}catch(e){
			alert("selected_values_checkbox:"+e.description);
		}
	}
	//同步搜索数据和全部数据的选中情况
	function changeCheckedStatue(key,ischecked,num){
		try
		{
			var tree2;

			if(num=="1"){
				tree2=getObjByMainDivId("showQueryTree");
			}else{
				tree2=getObjByMainDivId("showTree");
			}
			if(tree2!=null&&tree2!=undefined){
				var Nodes=tree2.getNodesByKey(key,"1");
				if(Nodes!=""){
					tree2.setNodeCheckboxSelected(Nodes[0],ischecked);
					tree2.ExpandNodeAndNextNode(Nodes[0]);
				}
			}
			getCheckedValues();
		}catch(e){
			alert("changeCheckedStatue:"+e.description);
		}
	}

	function selected_values_dblonclick(num){
		try
		{
			var selectvaluetype="${selectvaluetype}";
			var tree1;
			if(num=="1"){
				tree1=getObjByMainDivId("showTree");
			}else{
				tree1=getObjByMainDivId("showQueryTree");
			}
			//得到点击的节点的展开状态
			var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj());
			//判断节点对象是否展开 true为展开  则选中该点击的内容
			if(nodestatue==true){
				//取得点击节点的所有信息
				var arr=tree1.getNodeAttribute(tree1.getCurrentNodeObj());

				//如果selectvaluetype为1 则读取全称  为0时则读取显示字段
				if(selectvaluetype=="1"){
					selectValueobj.value=arr[7];
				}else	{
					selectValueobj.value=arr[10];
				}
				if(selectValueobj.value=="")
					selectValueobj.value=arr[8];
				if(selectValueobj.value=="")
					selectValueobj.value=arr[4];

				selectkeyobj.value=arr[1];
				dynamicAttriobj.value=tree1.getCurrentDynamicAttriValue(tree1.getCurrentNodeObj());
			}

			//用于左边的树得到焦点
			/*if(num=="2"){
			 var results=null;
			 var tree2=getObjByMainDivId("showTree");
			 //这里有个BUG  如果搜索的是一级节点 则无法命中左边的
			 if(arr!=undefined){
			 if(arr[1]!=""){
			 results=tree2.getNodesByKeys(arr[1],"1");
			 }
			 }
			 if(results!=null&&results!=""){
			 tree2.setCurrentNodeByNode(results[0]);
			 tree2.ExpandNodeAndNextNode(results[0]);
			 }
			 }*/
			array[0]=selectkeyobj.value;
			array[1]=selectValueobj.value;
			array[2]=dynamicAttriobj.value;
			isUnlawfulClose=false;
			returnValue=array;
			window.close();
		}catch(e){
			alert("selected_values_dblonclick:"+e.description);
		}
	}
	//
	function queryInfo_buttom(){
		queryInfoobj.blur();
	}
	function clearQuery(){
		queryInfoobj.value="";
		if(showQueryTreeobj.innerHTML!=""){
			showQueryTreeobj.innerHTML=""
		}
		queryInfoobj.focus();
	}
	//清除的操作 同时要清楚记录全部树的选中数据及显示查询树的全部数据
	function myClear(){
		var radioOrCheckBox="${radioOrCheckBox}";
		var useType="${useType}";
		var keys=selectkeyobj.value;
		if(useType=="query"){
			var tree1=getObjByMainDivId("showTree");
			if(tree1!=null&&tree1!=undefined){
				var checkedArr=tree1.getAllNodesByStatus("true");
				for(var i=0;i<checkedArr.length;i++){
					tree1.setCheckboxSelected(checkedArr[i],false);
				}
			}
		}else{
			if(radioOrCheckBox=="checkbox"){
				var tree1=getObjByMainDivId("showTree");
				if(tree1!=null&&tree1!=undefined){
					if(selectkeyobj.value!=""){
						//如果类型为查询类型 则把有:0替换为空""
						if(useType=="query"){
							var re = /:0/g;
							keys=keys.replace(re,"");
						}
						var Nodes=tree1.getNodesByKeys(keys,"1");
						if(Nodes!=""){
							for(var i=0;i<Nodes.length;i++){
								tree1.setCheckboxSelected(Nodes[i],false);
							}
						}
					}
				}
			}
		}
		if(showQueryTreeobj.innerHTML!=""){
			showQueryTreeobj.innerHTML!=""
		}
		selectValueobj.value="";

		selectkeyobj.value="";
		queryInfoobj.value="";
		queryKeysObj.value="";
		dynamicAttriobj.value="";
		showQueryTreeobj.innerHTML="";
	}




	//动态查询的单选单击内容的方法
	function _gzzzb_changevalues(obj,codes,texts){
		try{
			var tree1=getObjByMainDivId("showTree");
			tree1.ExpandSelectedNodeByKeys(codes);//”001,002,003”
			showTreeobj.scrollIntoView(true);
			if(codes.indexOf(",")=="-1"){
				selectValueobj.value=texts;
				selectkeyobj.value=codes;
			}else{
				var code=codes.split(",");
				var text=texts.split("/");
				selectValueobj.value=text[text.length-1];
				selectkeyobj.value=code[code.length-1];
			}
			//setDynamicAttriByChangeValue(selectkeyobj.value);
			//window.setTimeout("setDynamicAttriByChangeValue('"+selectkeyobj.value+"')",100);
		}catch(e){
			alert("_gzzzb_changevalues:"+e.description);
		}
	}
	function setDynamicAttriByChangeValue(key){
		var tree1=getObjByMainDivId("showTree");
		var results=null;
		results=tree1.getNodesByKeys(key,"1");
		if(results!=null&&results!=""){
			tree1.setCurrentNodeByNode(results[0]);
			tree1.ExpandNodeAndNextNode(results[0]);
			var arr2=tree1.getNodeAttribute(results[0]);
			dynamicAttriobj.value = arr2[5];

			array[0]=selectkeyobj.value;
			array[1]=selectValueobj.value;

			array[2]=dynamicAttriobj.value;
			isUnlawfulClose=false;
			returnValue=array;
			window.close();
		}else{
			window.setTimeout("setDynamicAttriByChangeValue('"+key+"')",50);
		}
	}
	//动态查询的单选双击内容的方法
	function _gzzzb_changevalues_ondblclick(obj,codes,texts){
		try{
			if(codes.indexOf(",")=="-1"){
				selectValueobj.value=texts;
				selectkeyobj.value=codes;
			}else{
				var code=codes.split(",");
				var text=texts.split("/");
				selectValueobj.value=text[text.length-1];
				selectkeyobj.value=code[code.length-1];
			}

			setDynamicAttriByChangeValue(selectkeyobj.value);
		}catch(e){
			alert("_gzzzb_changevalues_ondblclick:"+e.description);
		}
	}

	//复选选择数据的方法  num为1则是左边树调用  2则为右边树调用  (动态)
	function treechecked_static(obj){
		try{
			var useType="${useType}";
			if(useType=="add"){
				treechecked_ByAdd(obj);
			}else{
				window.setTimeout("treechecked_ByQuery()",200);
			}

		}catch(e){
			alert("treechecked_static:"+e.description);
		}
	}

	function treechecked_ByAdd(obj){
		var selectvaluetype="${selectvaluetype}";
		var tree1;
		var treetext;
		var treekey;
		var newtext="";
		var newkey="";
		tree1=getObjByMainDivId("showTree");
		var arr;

		if(obj!=null){
			if(obj.tagName=="DIV"){
				arr=tree1.getNodeAttribute(obj);
			}else{
				arr=tree1.getNodeAttribute(tree1.getNodeByCheckbox(obj));
			}
		}

		if(selectvaluetype=="1"){
			treetext=arr[7];//选中的数据内容
		}else{
			treetext=arr[10];
		}
		if(treetext=="")
			treetext=arr[8];
		if(treetext=="")
			treetext=arr[4];

		treekey=arr[1];//选中的key

		var textvalue= selectValueobj.value;
		var key=selectkeyobj.value;
		var invalues=textvalue.split(",");
		var keys=key.split(",");
		var iscunz=false;
		if(arr[6]=="true"){
			changeCheckedStatue_static(arr[1],true,'1')
		}else{
			changeCheckedStatue_static(arr[1],false,'1')
		}
		getCheckedValues();
	}

	//查询下拉的复选数据
	function treechecked_ByQuery(){
		try{
			var selectvaluetype="${selectvaluetype}";
			var tree1;

			var treetext;
			var key;
			var newtext="";
			var newkey="";
			var newQueryKeys="";
			tree1=getObjByMainDivId("showTree");

			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var selectedDynamicAttri=dynamicAttriobj.value;
			var queryKeys=queryKeysObj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");

			var selectedDynamicAttris=selectedDynamicAttri.split(",");
			var checkedArr=tree1.getAllNodesByStatus("true");
			var results=tree1.getNodesByKey("1","1");//1为绝对匹配,0为模糊匹配
			//alert(results[0]+ " : "+ getValueByName(results[0],"selectall"));
			for(var i=0;i<checkedArr.length;i++){
				//接受字节点状态 0表示全部子节点全部没选中 或节点没展开 （此状态下需要在ID后加：0 表示没影响关系） 1表示子节点全部选中或子节点部分选中 有影响关系
				/*var childStatue=-1;//节点状态
				 var noCheckedNum=0;//接收子节点里没选中的个数
				 var checkedNum=0;//接收子节点里选中的个数
				 var queryKey=newQueryKeys.split(",");
				 var newkeys=newkey.split(",");
				 var attribute=tree1.getNodeAttribute(checkedArr[i]);
				 var childArr=tree1.getAllChildNodes(checkedArr[i]);
				 for(var j=0;j<childArr.length;j++){
				 var arr2=tree1.getNodeAttribute(childArr[j]);
				 if(arr2[6]=="false"){
				 noCheckedNum++;
				 }else{
				 checkedNum++;
				 }
				 }

				 //alert("value:"+attribute[7]+"==checkedNum:"+checkedNum+"==noCheckedNum:"+noCheckedNum+"==childArr.length:"+childArr.length);
				 //如果子节点个数为0 节点状态为-1 则标识
				 if(childArr.length==0&&childStatue==-1){
				 childStatue=1;
				 }
				 if(checkedNum==childArr.length&&childStatue==-1){
				 childStatue=1;
				 }
				 if(noCheckedNum==childArr.length&&childStatue==-1){
				 childStatue=1;
				 }
				 if(checkedNum==0&&childStatue==-1){
				 childStatue=0;
				 }
				 if(noCheckedNum>0&&childStatue==-1){
				 childStatue=0;
				 }*/


				var attribute=tree1.getNodeAttribute(checkedArr[i]);
				var queryKey=newQueryKeys.split(",");
				var newkeys=newkey.split(",");
				//接受字节点状态 0表示没选中全部子节点 或节点没展开 （此状态下需要在ID后加：0 表示没影响关系） 1表示子节点全部选中或子节点部分选中 有影响关系
				var childStatue = tree1.getValueByName(checkedArr[i],"selectall")
				//alert(attribute+"===="+childStatue);

				var parentObj= checkedArr[i];
				var parentText="";//用于接受最高选中的节点内容
				var parentKey="";//用于接受最高选中的节点key
				while(1==1){
					if(parentObj==null){
						break;
					}else{
						var arr2=tree1.getNodeAttribute(parentObj);
						if(arr2[6]=="true"){
							if(selectvaluetype=="1"){
								parentText=arr2[7];//选中的数据内容
							}else{
								parentText=arr2[10];
							}

							if(parentText=="")
								parentText=arr2[8];
							if(parentText=="")
								parentText=arr2[4];

							parentKey=arr2[1];
						}
					}
					parentObj=tree1.getTreeParentNode(parentObj);
				}

				treekey=attribute[1];//选中的key
				if(childStatue==0&&parentText!=""){
					parentText=parentText+"(部分)";
					treekey=treekey+":0";
				}

				var isAddKey=true;//接受是否需要把当前节点的key加进去   如果父节点是部分则需要  如果是全部则不需要
				for(var m=0;m<newkeys.length;m++){
					if(newkeys[m]==parentKey){
						isAddKey=false;
					}
				}
				//alert("name:"+attribute[7]+"＝＝parentText："+parentText+"===isAddKey:"+isAddKey);

				var isEqual=false;//该记录的最高节点信息是否已经加载
				for(var n=0;n<queryKey.length;n++){
					if(queryKey[n]==parentKey){
						isEqual=true;
					}
				}

				if(isEqual==false){
					if(newQueryKeys==""){
						newtext=parentText;
						newQueryKeys=parentKey;
					}else{
						newtext=newtext+","+parentText;
						newQueryKeys=newQueryKeys+","+parentKey;
					}
				}

				if(isAddKey==true){
					if(newkey==""){
						newkey=treekey;
						selectedDynamicAttri=attribute[5];
					}else{
						newkey=newkey+","+treekey;
						selectedDynamicAttri=selectedDynamicAttri+","+attribute[5];
					}
				}else{
					if(treekey==parentKey){
						if(newkey==""){
							newkey=parentKey;
						}else{
							newkey=newkey+","+parentKey;
						}
					}
				}
			}

			queryKeysObj.value=newQueryKeys;
			selectValueobj.value=newtext;
			selectkeyobj.value=newkey;
			dynamicAttriobj.value=selectedDynamicAttri;//保存二次开发者自定义的数据内容
			window.setTimeout("treechecked_ByQuery_ChangeRightCheckbox()",200);
			/*alert("queryKeysObj.value=============="+queryKeysObj.value);
			 alert("selectValueobj.value=============="+selectValueobj.value);
			 alert("selectkeyobj.value=============="+selectkeyobj.value);
			 alert("dynamicAttriobj.value=============="+dynamicAttriobj.value);*/
		}catch(e){
			alert("treechecked_ByQuery:"+e.description);
		}
	}

	//查询下拉中选择左边树 如果右边树有查询数据   改变右边查询数据的选中情况
	function treechecked_ByQuery_ChangeRightCheckbox(){
		try{
			var tree1=getObjByMainDivId("showTree");
			var checkedArr=tree1.getAllNodesByStatus("true");
			var checkboxobj=document.all("gzzzb_checkbox");
			//alert(checkboxobj);
			if(checkboxobj!=null){
				for(var i=0;i<checkboxobj.length;i++){
					checkboxobj[i].checked=false;
				}
				for(var i=0;i<checkedArr.length;i++){
					var attribute=tree1.getNodeAttribute(checkedArr[i]);
					for(var j=0;j<checkboxobj.length;j++){
						var checkboxkeys=checkboxobj[j].key;
						if(checkboxkeys.indexOf(",")=="-1"){
							if(attribute[1]==checkboxkeys){
								checkboxobj[j].checked=true;
								//break;
							}
						}else{
							var code=checkboxkeys.split(",");
							if(attribute[1]==code[code.length-1]){
								checkboxobj[j].checked=true;
								//break;
							}
						}

					}
				}
			}
		}catch(e){
			alert("treechecked_ByQuery_ChangeRightCheckbox:"+e.description);
		}
	}

	function openOnclick_ByQuery(){
		window.setTimeout("treechecked_ByQuery()",200);
	}

	//复选选中数据的方法   num为1则是左边树调用  2则为右边树调用
	function selected_values_checkbox_static(){
		try
		{
			var useType="${useType}";
			if(useType=="add"){
				var tree1=getObjByMainDivId("showTree");
				//取得节点展开状态
				//var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj())
				var arr=tree1.getNodeAttribute(tree1.getCurrentNodeObj());
				//if(nodestatue==true){
				if(arr[6]=="true"){
					tree1.setNodeCheckboxSelected(tree1.getCurrentNodeObj(),false);
				}else{
					tree1.setNodeCheckboxSelected(tree1.getCurrentNodeObj(),true);
				}
				treechecked_static(tree1.getCurrentNodeObj());
				//}


			}else{
				window.setTimeout("treechecked_ByQuery()",200);
			}
		}catch(e){
			alert("selected_values_checkbox_static:"+e.description);
		}
	}

	function changeCheckedStatue_static(key,ischecked,num){
		try{
			if(num==1){
				var checkboxobj=document.all("gzzzb_checkbox");
				if(checkboxobj!=null){
					for(var i=0;i<checkboxobj.length;i++){
						var checkboxkeys=checkboxobj[i].key;
						if(checkboxkeys.indexOf(",")=="-1"){
							if(key==checkboxkeys){
								checkboxobj[i].checked=ischecked;
								return false;
							}
						}else{
							var code=checkboxkeys.split(",");
							if(key==code[code.length-1]){
								checkboxobj[i].checked=ischecked;
								return false;
							}
						}

					}
				}
			}else{
				var tree1=getObjByMainDivId("showTree");
				if(tree1!=null&&tree1!=undefined){
					var Nodes=tree1.getNodesByKey(key,"1");
					if(Nodes!=""){
						tree1.setNodeCheckboxSelected(Nodes[0],ischecked);
						tree1.ExpandNodeAndNextNode(Nodes[0]);
					}else{
						window.setTimeout("changeCheckedStatue_static('"+key+"',"+ischecked+",'"+num+"')",100);
					}
				}
			}
			getCheckedValues();
		}catch(e){
			alert("changeCheckedStatue_static:"+e.description);
		}
	}
	function checkboxchangevalue_checkbox(obj){
		if(obj.checked==true)
			obj.checked=false;
		else
			obj.checked=true;
	}
	function checkboxchangevalue(obj,codes,texts){
		try{
			var useType="${useType}";
			if(useType=="add"){
				checkboxchangevalue_ByAdd(obj,codes,texts);
			}else{
				checkboxchangevalue_ByQuery(obj,codes);
			}
		}catch(e){
			alert("checkboxchangevalue:"+e.description);
		}
	}
	function checkboxchangevalue_ByAdd(obj,codes,texts){
		try{
			var tree1=getObjByMainDivId("showTree");
			tree1.ExpandSelectedNodeByKeys(codes);
			var treetext;
			var treekey;
			var newtext="";
			var newkey="";
			var textvalue= selectValueobj.value;
			var key=selectkeyobj.value;
			var invalues=textvalue.split(",");
			var keys=key.split(",");


			if(codes.indexOf(",")=="-1"){
				treetext=texts;
				treekey=codes;
			}else{
				var code=codes.split(",");
				var text=texts.split("/");
				treetext=text[text.length-1];
				treekey=code[code.length-1];
			}


			if(obj.checked==true){
				changeCheckedStatue_static(treekey,true,'2')
			}else{
				changeCheckedStatue_static(treekey,false,'2')
			}

			/*
			 if(obj.checked==true){
			 if(textvalue==""){
			 newtext=treetext;
			 newkey=treekey;
			 }else{
			 newtext=textvalue+","+treetext;
			 newkey=key+","+treekey;
			 }
			 changeCheckedStatue_static(treekey,true,'2')
			 }else{
			 for(var i=0;i<keys.length;i++){
			 if(keys[i]!=treekey&&keys[i]!=""){
			 if(newtext==""){
			 newtext=invalues[i];
			 newkey=keys[i];
			 }else{
			 newtext=newtext+","+invalues[i];
			 newkey=newkey+","+keys[i];
			 }
			 }
			 }
			 changeCheckedStatue_static(treekey,false,'2')
			 }
			 selectValueobj.value=newtext;
			 selectkeyobj.value=newkey;	*/
		}catch(e){
			alert("checkboxchangevalue_ByAdd:"+e.description);
		}
	}
	//查询下拉的右边的点击复选框时间
	function checkboxchangevalue_ByQuery(obj,codes){
		try{
			var tree1=getObjByMainDivId("showTree");
			var newCode = codes;
			if(codes.indexOf(",")!="-1"){
				var code=codes.split(",");
				newCode=code[code.length-1];
			}

			var isChecked = true;
			if(obj==true){
				tree1.ExpandSelectedNodeByKeys(codes,true);
			}else if(obj!=null){
				isChecked = obj.checked;
				tree1.ExpandSelectedNodeByKeys(codes,obj.checked);
			}

			var results=null;
			results=tree1.getNodesByKeys(newCode,"1");
			if(results!=null&&results!=""){
				tree1.setNodeCheckboxSelected(results[0],isChecked);
				treechecked_ByQuery();
			}else{
				window.setTimeout("checkboxchangevalue_ByQuery(null,'"+newCode+"')",100);
			}
		}catch(e){
			alert("checkboxchangevalue_ByQuery:"+e.description);
		}
	}


	function _gzzzb_changevalues_checkbox(obj,codes,texts){
		if(obj.firstChild.checked==false){
			obj.firstChild.checked=true;
		}else{
			obj.firstChild.checked=false;
		}
		checkboxchangevalue(obj.firstChild,codes,texts);
	}

	//控制提示消息的层===================
	function ShowDiv(obj){
		if(obj.value=="")
			obj.previousSibling.style.visibility="visible";
	}
	function hiddlen(obj)
	{
		obj.style.visibility="hidden";
		document.all(obj.id.substring(11,obj.id.length)).focus();
	}
	function _gzzzbtext_changevalue(obj){

		obj.previousSibling.style.visibility="hidden";
	}
	function hiddlen_div(obj){
		if(obj.previousSibling.style.visibility=="visible")
			obj.previousSibling.style.visibility="hidden";
	}

	function _gzzzb_changecolor(obj,num)
	{
		if(num=="1"){
			obj.bgColor="#7B6DFC";
		}
		else
			obj.bgColor="";
	}

	function changeSelectStatue(obj){
		var tree1=getObjByMainDivId("showTree");
		var treetext;
		var treekey;
		var newtext="";
		var newkey="";
		var selectvaluetype="${selectvaluetype}";
		var tree1=getObjByMainDivId("showTree");
		var tree2=getObjByMainDivId("showQueryTree");
		var arr=tree1.getAllNodes();
		if(obj.checked==true){
			selectValueobj.value="";
			selectkeyobj.value="";
			dynamicAttriobj.value="";
			for(var i=0;i<arr.length;i++){
				tree1.setNodeCheckboxSelected(arr[i],true);
				var arrNode=tree1.getNodeAttribute(arr[i]);
				if(selectvaluetype=="1"){
					treetext=arrNode[7];//选中的数据内容
				}else{
					treetext=arrNode[10];
				}
				if(treetext=="")
					treetext=arrNode[8];
				if(treetext=="")
					treetext=arrNode[4];

				treekey=arrNode[1];//选中的key
				if(selectkeyobj.value==""){
					selectValueobj.value=treetext;
					selectkeyobj.value=treekey;
					dynamicAttriobj.value=arrNode[5];
				}else{
					selectValueobj.value=selectValueobj.value+","+treetext;
					selectkeyobj.value=selectkeyobj.value+","+treekey;
					dynamicAttriobj.value=dynamicAttriobj.value+","+arrNode[5];
				}
				if(tree2!=undefined){
					var arr2=tree2.getNodeAttribute(arr[i]);
					var Nodes2=tree2.getNodesByKey(arr2[1],"1");
					if(Nodes2!=""){
						tree2.setNodeCheckboxSelected(Nodes2[0],true);
					}
				}
			}
		}else{
			for(var i=0;i<arr.length;i++){
				tree1.setNodeCheckboxSelected(arr[i],false);
				selectValueobj.value="";
				selectkeyobj.value="";
				dynamicAttriobj.value="";
				if(tree2!=undefined){
					var arr2=tree2.getNodeAttribute(arr[i]);
					var Nodes2=tree2.getNodesByKey(arr2[1],"1");
					if(Nodes2!=""){
						tree2.setNodeCheckboxSelected(Nodes2[0],false);
					}
				}
			}
		}
	}
</script>
</body>
</html>
