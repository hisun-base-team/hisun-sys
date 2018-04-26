<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典项管理</title>
<link href="${path}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="main_left">
			<div class="portlet box grey mainleft">
				<div class="portlet-body leftbody">
					<Tree:tree id="leftTree" treeUrl="${path}sys/admin/dictionary/item/tree/${typeId}" token="${sessionScope.OWASP_CSRFTOKEN}"
							   onClick="onClickByTree" submitType="post" dataType="json" isSearch="false"/>
				</div>
			</div>
		</div>
		<div class="main_right" id="rightList" ></div>
	</div>
</div>
<script type="text/javascript" src="${path}/js/zTree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${path}/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path}/js/common/validate-message.js"></script>
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
		$("#leftTree_div").css('height',divHeight);
	}



	function onClickByTree (event, treeId, treeNode){
		$("#resourceId").val(treeNode.id);
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

		var zTree = $.fn.zTree.getZTreeObj("leftTree");
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
		$("#resourceId").val(node.id);
		selectId=node.id;
		zTree.expandNode(node, true, false , true);
	});

	function refreshTree() {
		$("#leftTree").empty();
		refreshTreeTag("leftTree", setting_leftTree, "");
		selectNodeTree();
	}
	function selectNodeTree(){
		var zTree = $.fn.zTree.getZTreeObj("leftTree");
		var id = $("#resourceId").val();
		var node = zTree.getNodeByParam('id',id);// 获取id为-1的点
		zTree.selectNode(node);
		zTree.expandNode(node, true, false , true);
	}

	function pagehref (pageNum ,pageSize){
		$.ajax({
			cache:false,
			type: 'POST',
			dataType : "html",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			url: "${path}/sys/tenant/resourcePrivilege/ajax/privilegeList?resourceId="+$('#resourceId').val()+"&pageNum="+pageNum+"&pageSize="+pageSize,// 请求的action路径
			error: function () {// 请求失败处理函数
				alert('请求失败');
			},
			success:function(html){
				$("#listResource").html(html);
			}
		});
	}
</script>
</body>
</html>