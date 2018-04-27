
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${path}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.page-content{   padding: 0 !important; }
ul.ztree{margin-bottom: 10px; background: #f1f3f6 !important;}
.portlet.box.grey.mainleft{background-color: #f1f3f6;overflow: hidden; padding: 0px !important; margin-bottom: 0px;} 
.main_left{float:left; width:220px;  margin-right:10px; background-color: #f1f3f6; }
.main_right{display: table-cell; width:2000px; padding:20px 20px; }
.portlet-title .caption.mainlefttop{ border: none !important; background-color:#eaedf1;width: 220px; height: 48px;line-height: 48px;padding: 0;margin: 0;text-indent: 1em; }
.portlet.box .portlet-body.leftbody{padding: 15px 8px;}
</style>
<title>资源管理</title>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="main_left">
			<div class="portlet box grey mainleft">
				<div class="portlet-body leftbody">
					<Tree:tree id="treeDemo" treeUrl="${path}/sys/tenant/resourcePrivilege/tree" token="${sessionScope.OWASP_CSRFTOKEN}"
							   onClick="onClickByTree" submitType="post" dataType="json" isSearch="false" userParameter="dictionaryType:aaa"/>
					<%--<div class="zTreeDemoBackground" id="tree">--%>
						<%--<ul id="treeDemo" class="ztree"></ul>--%>
					<%--</div>--%>
				</div>
			</div>
		</div>
		<div class="main_right" id="listResource" ></div>
	</div>
</div>
<script type="text/javascript" src="${path}/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript">
	$(function(){
		changeTreeDivHeight();
		//当浏览器大小改变的时候,要重新计算
		$(window).resize(function(){
			changeTreeDivHeight();
		})
	});
	function changeTreeDivHeight(){
		var divHeight = $(window).height()-60;
		$("#treeDemo_div").css('height',divHeight);
	}



	function onClickByTree (event, treeId, treeNode){
		$.ajax({
			url : "${path}/sys/tenant/resourcePrivilege/ajax/privilegeList?resourceId="+treeNode.id+"&resourceName="+treeNode.name,
			type : "get",
			data : null,
			dataType : "html",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(html){
				$("#listResource").html(html);
			},
			error : function(){

			}
		});
	}

	$(document).ready(function(){
		//初始化菜单
		App.init();//必须，不然导航栏及其菜单无法折叠

		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		// zTree.expandAll(true);

		var node = zTree.getNodes()[0];// 获取第一个点
		//$("#listResource").load("${path}/sys/tenant/resourcePrivilege/privilegeList?resourceId=1");
		$.ajax({
			cache:false,
			type: 'POST',
			dataType : "html",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			url: "${path}/sys/tenant/resourcePrivilege/ajax/privilegeList?resourceId="+node.id+"&resourceName="+node.name,// 请求的action路径
			error: function () {// 请求失败处理函数
				alert('请求失败');
			},
			success:function(html){
				$("#listResource").html(html);
			}
		});
		zTree.selectNode(node);
		zTree.expandNode(node, true, false , true);
	});

</script>
</body>
</html>