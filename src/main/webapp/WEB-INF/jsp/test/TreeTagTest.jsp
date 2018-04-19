<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="/WEB-INF/tld/Tree.tld" prefix="Hisuntree" %>
<%@ taglib uri="/WEB-INF/tld/selectOption.tld" prefix="SelectOption" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${path }/css/bootstrap-fileupload.css">

	<script type="text/javascript" src="${path}/js/jquery.json-2.3.min.js"></script>

	<link href="${contextPath}/css/zTreeStyle/zzbxtree.css" rel="stylesheet" type="text/css" />
	<script src="${contextPath}/js/hisunTree/hisunxtree.js" language="javascript"></script>
	<script src="${contextPath}/js/hisunTree/xtree.js" type="text/javascript"></script>
	<script src="${contextPath}/js/select_option.js" language=javascript></script>


	<link href="${path }/css/style.css" rel="stylesheet" type="text/css">

	<%--<script src="${contextPath}/js/selectCheckBox/jquery.easyui.min.js" language=javascript></script>--%>
	<%--<link href="${path }/css/selectCheckBox/easyui.css" rel="stylesheet" type="text/css">--%>
	<%--<script src="${contextPath}/js/selectCheckBox/jquery.easyui.min.js" language=javascript></script>--%>
	<link href="${path }/css/selectCheckBox/jquery.multiselect.css" rel="stylesheet" type="text/css">
	<script src="${contextPath}/js/selectCheckBox/jquery.multiselect.js" language=javascript></script>
	<link href="${path}/css/select2_metro.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/css/chosen.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="<%=path%>/js/select2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/chosen.jquery.min.js"></script>

	<script src="${contextPath}/js/zipjs/zip.js" charset=“utf-8”></script>
	<script src="${contextPath}/js/zipjs/mime-types.js"  charset=“utf-8”></script>
	<%--<script src="http://apps.bdimg.com/libs/jquery/1.9.0/jquery.js"></script>--%>
	<script src="${contextPath}/js/zipjs/UnZipArchive.js"  charset=“utf-8”></script>
	<%--<script src="${contextPath}/js/zipjs/z-worker.js"></script>--%>
	<!-- END PAGE LEVEL STYLES -->
	<title>树测试</title>
	<style type="text/css">
		form {
			margin: 0 0 0px;
		}
	</style>
</head>
<body>
<div id="selectOptionModal" class="modal container hide fade" tabindex="-1" data-width="700" data-height="550">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button data-dismiss="modal" class="close"  type="button"></button>
				<h3 class="modal-title" id="selectOptionTitle" >
					选择卡
				</h3>
			</div>
			<div class="modal-body" id="selectOptionDiv">
			</div>
		</div>
	</div>
</div>
<TABLE height="100%" width="100%" border="0" bordercolor="red" cellspacing="0" cellpadding="0" >

	<TR height="100%" valign="top">
		<TD colspan="2">
			<div id="b01Tree"><Hisuntree:tree divId="b01Tree" checkboxDisplay="yes" radioOrCheckbox="checkbox" parentRadioEnable="true"
							   parentCheckboxEnable="true" parentChildIsolate="true"
							   dataSource="b01TreeDataSource"
							   userParameter="" /></div>
			<%--<div id="showTree1">--%>

			<%--<Gzzbtree:tree divId="showTree1" userParameter="aa:666" dblonclick="aaa()" checkboxDisplay="no" radioOrCheckbox="checkbox" parentRadioEnable="true" parentCheckboxEnable="true" parentChildIsolate="true" dataSource="com.hisun.saas.sys.tools.taglib.tree.impl.TreeDataSource"/>--%>
			<%--</div> 													--%>
		</TD>
	</TR>
	<TR height="100%" valign="top">
		<TD colspan="2" width="300px">
			<div class="controls" >
				<SelectOption:SelectOption id="sex1" textClass="span4 chosen"  isshowtree="no"
										   radioOrCheckbox="radio" moreSelectSearch="yes"  dataSource="sexDataSource"/>
				<%--<select class="m-wrap span4" required name="tblx" id="tblx" disabled>--%>
					<%--<option value="" ></option>--%>
					<%--<option value="1" selected>饼图</option>--%>
					<%--<option value="2" >柱状图</option>--%>
					<%--<option value="3" >折线图</option>--%>
				<%--</select>--%>
			</div>
		</TD>
	</TR>
	<TR height="100%" valign="top">
		<TD colspan="2" width="300px">
			<div class="controls">
				<SelectOption:SelectOption id="sex" textClass="m-wrap span4"  isshowtree="no" defaultkeys="0,1"
										   radioOrCheckbox="checkbox"	moreSelectSearch="yes"	   dataSource="sexDataSource"/>
				<%--<select name="qweqwew" multiple="multiple" class="span6 m-wrap" id="qweqwew" disabled>--%>
					<%--<option value="AK">Alaska</option>--%>
					<%--<option value="AZ">Arizona</option>--%>
					<%--<option value="AR">Arkansas</option>--%>
					<%--<option value="CA">California</option>--%>
					<%--<option value="CO">Colorado</option>--%>
					<%--<option value="CT">Connecticut</option>--%>
					<%--<option value="DE">Delaware</option>--%>
					<%--<option value="FL">Florida</option>--%>
					<%--<option value="GA">Georgia</option>--%>
					<%--<option value="HI">Hawaii</option>--%>
					<%--<option value="ID">Idaho</option>--%>
					<%--<option value="IL">Illinois</option>--%>
					<%--<option value="IN">Indiana</option>--%>
					<%--<option value="IA">Iowa</option>--%>
					<%--<option value="KS">Kansas</option>--%>
					<%--<option value="KY">Kentucky</option>--%>
					<%--<option value="LA">Louisiana</option>--%>
					<%--<option value="ME">Maine</option>--%>
					<%--<option value="MD">Maryland</option>--%>
					<%--<option value="MA">Massachusetts</option>--%>
					<%--<option value="MI">Michigan</option>--%>
					<%--<option value="MN">Minnesota</option>--%>
					<%--<option value="MS">Mississippi</option>--%>
					<%--<option value="MO">Missouri</option>--%>
					<%--<option value="MT">Montana</option>--%>
					<%--<option value="NE">Nebraska</option>--%>
					<%--<option value="NV">Nevada</option>--%>
					<%--<option value="NH">New Hampshire</option>--%>
					<%--<option value="NJ">New Jersey</option>--%>
					<%--<option value="NM">New Mexico</option>--%>
					<%--<option value="NY">New York</option>--%>
					<%--<option value="NC">North Carolina</option>--%>
					<%--<option value="ND">North Dakota</option>--%>
					<%--<option value="OH">Ohio</option>--%>
					<%--<option value="OK">Oklahoma</option>--%>
					<%--<option value="OR">Oregon</option>--%>
					<%--<option value="PA">Pennsylvania</option>--%>
					<%--<option value="RI">Rhode Island</option>--%>
					<%--<option value="SC">South Carolina</option>--%>
					<%--<option value="SD">South Dakota</option>--%>
					<%--<option value="TN">Tennessee</option>--%>
					<%--<option value="TX">Texas</option>--%>
					<%--<option value="UT">Utah</option>--%>
					<%--<option value="VT">Vermont</option>--%>
					<%--<option value="VA">Virginia</option>--%>
					<%--<option value="WA">Washington</option>--%>
					<%--<option value="WV">West Virginia</option>--%>
					<%--<option value="WI">Wisconsin</option>--%>
					<%--<option value="WY">Wyoming</option>--%>
				<%--</select>--%>
			</div>
		</TD>
	</TR>
	<%--<TR height="100%" valign="top">--%>
		<%--<TD colspan="2" width="300px">--%>
			<%--<div class="controls">--%>
				<%--<select name="demo3" multiple="multiple" class="span4 m-wrap" id="demo3">--%>
					<%--<option value="阿斯顿撒">阿斯顿撒</option>--%>
					<%--<option value="发俄方v">发俄方v</option>--%>
					<%--<option value="文过饰非">文过饰非</option>--%>
					<%--<option value="风格风格">风格风格</option>--%>
					<%--<option value="和团结他人">和团结他人</option>--%>
					<%--<option value="沿途有">沿途有</option>--%>
					<%--<option value="爱对方的观点">爱对方的观点</option>--%>
					<%--<option value="日以后就让她怀孕">日以后就让她怀孕</option>--%>
				<%--</select>--%>
			<%--</div>--%>
		<%--</TD>--%>
	<%--</TR>--%>
	<TR height="100%" valign="top">
		<TD colspan="2" width="300px">
			<SelectOption:SelectOption id="manageUnitId"  textClass="m-wrap span4" allownull="false" onchange="changeA01z1();"
									   des="档案管理单位" textFontSize="10pt" width="200px" isshowtree="yes"
									   formName="eApplyE01z8Form" fieldCode="manageUnitId" fieldText="manageUnitName"
									   staticdata="no" isEnterQuery="false" radioOrCheckbox="checkbox" matchingsetup="2,1,0,0,0,0,0"
									   selectvaluetype="1" checkboxDisplay="yes" isDisabled="false" title=""
									   dataSource="b01TreeDataSource" userParameter="isShowDepart:false;"/>
		</TD>
	</TR>

	<TR height="100%" valign="top">
		<TD colspan="2" width="300px">
			<SelectOption:SelectOption id="manageUnitIdaaa"   textClass="m-wrap span4" allownull="false" onchange="changeA01z1();"
									   des="档案管理单位" textFontSize="10pt" width="200px" isshowtree="yes"
									   formName="eApplyE01z8Form" fieldCode="manageUnitIdaa" fieldText="manageUnitNameaa"
									   staticdata="yes" isEnterQuery="false" radioOrCheckbox="radio" matchingsetup="2,1,0,0,0,0,0"
									   selectvaluetype="1" checkboxDisplay="none" isDisabled="false" title=""
									   dataSource="dynamicB01TreeDataSource" userParameter="isShowDepart:false;"/>
		</TD>
	</TR>

	<TR height="100%" valign="top">
		<TD colspan="2" width="300px">
			<input type="file" id="file">
			<ul id="dir">

			</ul>
		</TD>
	</TR>
</TABLE>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>

<script type="text/javascript">
	$("#file").change(function (e) {
		var file = this.files[0];
		window.un = new UnZipArchive( file );
		un.getData( function() {
			//获取所以的文件和文件夹列表;
			var arr = un.getEntries();
			//拼接字符串
			var str = "";
			for(var i=0; i<arr.length; i++ ) {
				//点击li的话直接下载文件;

				var isDirectory = "图片";
				if(arr[i].directory == true){
					isDirectory = "文件夹";
				}

//				str += "<li onclick=download('"+arr[i].filename+"')>"+isDirectory+":"+arr[i].filename+"&nbsp;&nbsp;&nbsp;"+"文件大小："+arr[i].uncompressedSize+"字节</li>"
				str += "<li>"+isDirectory+":"+arr[i].filename+"&nbsp;&nbsp;&nbsp;"+"文件大小："+arr[i].uncompressedSize+"字节</li>"
			};
			$("#dir").html( str );
		});
	});
	var download = function ( filename ) {
		un.download( filename );
	};

	function addNode(){
//   var tree1=getObjByMainDivId("showTree3");
//   var nodeName=document.getElementById("nodeName").value;
//
//   if(tree1.currentNode!=null && nodeName.length>0){
//     tree1.add(nodeName,"javascript:{alert('执行链接');}","","javascript:{alert('执行函数');}","key","helpCode","","","","");
//   }
	}

//	$(function () {
//		$('#ddlLine').combotree({
//			valueField: "id", //Value字段
//			textField: "text", //Text字段
//			multiple: true,
//			data: [{ "id": 13, "text": "C1" }, { "id": 14, "text": "C2" }, { "id": 15, "text": "C3"}]
//
//		});
//	})



//	$('#sex').multiselect({
//		columns:1,
//		placeholder: '请选择...',
//		search: true,
//		selectGroup: true,
//		selectAll: true
//	});
//	$('#demo3').multiselect({
//		columns:1,
//		placeholder: '请选择...',
//		search: true,
//		selectGroup: true
//	});

	function delNode(){
		var tree1=getObjByMainDivId("showTree");
		try{
			tree1.deleteNode();
		}catch(e){
			alert(e.description);
		}
	}

	function queryNode(){
		var tree1=getObjByMainDivId("showTree");

		var nName=document.getElementById("nName").value;
		var arr=tree1.getExpandNodeByNodeName(nName);
		// tree1.getCurrentNodeInfo();

	}

	function queryNodeByKey(){
		var tree1=getObjByMainDivId("showTree");
		var key=document.getElementById("key").value;
		var arr=tree1.getNodesByKey(key);

	}

	function queryNodeByHelpCode(){
		var tree1=getObjByMainDivId("showTree");
		var helpCode=document.getElementById("helpCode").value;
		var arr=tree1.getNodesByHelpCode(helpCode);
	}

	function addBNode(){
//loadTree
		var tree1=getObjByMainDivId("loadTree");
		alert(tree1.currentNode.cengCount);
		/*if(nodeName.length>0){
		 //创建一个兄弟节点
		 tree1.addBrotherNode(nodeName,"javascript:{alert('执行链接');}","","javascript:{alert('执行函数');}","key","helpCode","","","","");
		 }*/

		//alert(tree1.getNodeAllInfos(tree1.currentNode));

	}

	var i=0;
	function getInputValue(){

		var tree1=getObjByMainDivId("showTree");
		var arr=tree1.getNodesByKey("aa","1");

		//tree1.setNodeCheckboxSelected(tree1.currentNode,true);

		/*for(var i=0;i<results.length;i++){
		 alert(results[i]);
		 }*/

		// tree1.ExpandNodeByObj(tree1.currentNode);

		// alert(tree1.getAllNodeInfos(tree1.currentNode));
		/* if(i==0){
		 tree1.setNodeCheckboxEnable(tree1.currentNode,false);
		 i=1;
		 }else{
		 tree1.setNodeCheckboxEnable(tree1.currentNode,true);
		 i=0;
		 }*/



		//tree1.getTreeParentChildNodes(tree1.getTreeParentNode(tree1.getCurrentNodeObj()));

		//tree1.setNodeCheckboxEnable(tree1.getCurrentNodeObj(),true);

		//tree1.setNodeCheckboxSelected(tree1.getCurrentNodeObj(),true);

		//alert(getInputNode(tree1.getCurrentNodeObj()).disabled);

	}

	function checkboxjs(){
		return false;
	}

	function ajaxLoadTree(){
		try{
//	var a = new AJAXInteraction("/GZZZB/TestServlet?contextPath=/GZZZB&parentChildIsolate=true&parentCheckboxEnable=true&parentRadioEnable=true&radioOrCheckBox=checkbox&checkboxDisplay=black&divId=showTree1&dataSource=com.san.gzzzb.tools.tree.impl.UserTreeDataSource",function(reqText) {
//		 	 if(reqText!=null && reqText!=""){
//
//		 	    var jsStr=reqText.substr(reqText.indexOf("#####")+5);
//			 	document.getElementById("showTree1").innerHTML=reqText.replace("#####"+jsStr,"");
//			 	eval(reqText.substr(reqText.indexOf("#####")+5));
//			 	var tree=getObjByMainDivId("showTree1");
//
//			 	tree.setDefaultKeys("001,002");
//			 }
//	});
//	a.doGet();
		}catch(e){
			alert(e.description);
		}
	}

	function imgClick(){
		//alert("imgClick");
	}

	function checkBoxClick(){
		//alert("checkBoxClick");
	}

	function aClick(){
		alert("aClick");
	}

	function test(){
		//alert("test");
	}

	function loadQueryNode(MainDivId,QueryDivId){
		try{

			var queryItem=document.getElementById("queryNodeName").value;
			var QueryDiv=document.getElementById(QueryDivId);//查询对象
			var MainDiv=document.getElementById(MainDivId);//存存储对象

			var tree1=getObjByMainDivId(QueryDivId);
			var matching=new Array();
			matching[0]=2;
			matching[1]=0;
			matching[2]=0;
			matching[3]=0;
			matching[4]=0;
			matching[5]=0;
			matching[6]=0;

			tree1.queryLoadNode(queryItem,MainDiv,"${contextPath}","imgClick()","checkBoxClick()","aClick()",matching,"u_9073","aaa()");

		}catch(e){
			alert(" loadQueryNode : " + e.description);
		}
	}
	function aaa(){
//  alert("双击....");
	}
</script>
</body>
</html>
