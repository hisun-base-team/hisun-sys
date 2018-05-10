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
<title>单位管理</title>
<link href="${path}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	.page-content{   padding: 0 !important; }
	ul.ztree{margin-bottom: 10px; background: #f1f3f6 !important;}
	.portlet.box.grey.mainleft{background-color: #f1f3f6;overflow: hidden; padding: 0px !important; margin-bottom: 0px;}
	.main_left{float:left; width:220px;  margin-right:10px; background-color: #f1f3f6; }
	.main_right{display: table-cell; width:2000px; padding:20px 20px; }
	.portlet-title .caption.mainlefttop{ border: none !important; background-color:#eaedf1;width: 220px; height: 48px;line-height: 48px;padding: 0;margin: 0;text-indent: 1em; }
	.portlet.box .portlet-body.leftbody{padding: 15px 8px;}</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="main_left">
			<div class="portlet box grey mainleft">
				<div class="portlet-body leftbody">
					<input type="hidden" id="currentNodeId"  name="currentNodeId" value="" />
					<input type="hidden" id="currentNodeName"  name="currentNodeName" value="" />
					<input type="hidden" id="currentNodeParentId"  name="currentNodeParentId" value="" />
					<Tree:tree id="leftTree" treeUrl="${path}/sys/admin/dzda/catalogType/tree" token="${sessionScope.OWASP_CSRFTOKEN}"
							   onClick="onClickByTree" submitType="post" dataType="json" isSearch="false"/>
				</div>
			</div>
		</div>
		<div class="main_right" id="rightList" ></div>
	</div>
</div>
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
		$("#currentNodeId").val(treeNode.id);//赋值
		$("#currentNodeName").val(treeNode.name);//赋值
		$("#currentNodeParentId").val(treeNode.pId);//赋值
		$.ajax({
			url : "${path}/sys/admin/dzda/catalogType/ajax/list",
			type : "get",
			data : null,
			dataType : "html",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			data:{
				"currentNodeId":treeNode.id,
				"currentNodeParentId":treeNode.pId,
				"currentNodeName":treeNode.name
			},
			success : function(html){
				$("#rightList").html(html);
			},
			error : function(){

			}
		});
	}

	$(document).ready(function(){
		App.init();//必须，不然导航栏及其菜单无法折叠
		var zTree = $.fn.zTree.getZTreeObj("leftTree");//取得树对象
		var node = zTree.getNodes()[0];// 获取第一个点
		$("#currentNodeId").val(node.id);//赋值
		$("#currentNodeName").val(node.name);//赋值
		$("#currentNodeParentId").val(node.pId);//赋值
		$.ajax({
			url: "${path}/sys/admin/dzda/catalogType/ajax/list",// 请求的action路径
			type: 'POST',
			dataType : "html",
			data:{
				"currentNodeId":node.id,
				"currentNodeParentId":node.pId,
				"currentNodeName":node.name
			},
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(html){
				$("#rightList").html(html);
			},
			error : function(){
				alert('请求失败');
			}
		});
		zTree.selectNode(node);//默认选中
		zTree.expandNode(node, true, false , true);//展开
	});

	function refreshTree() {
		$("#leftTree").empty();
		refreshTreeTag("leftTree",setting_leftTree,"");
		selectNodeTree();
	}
	function selectNodeTree(){
		var zTree1 = $.fn.zTree.getZTreeObj("leftTree");
		var id = $("#currentNodeId").val();
		var node = zTree1.getNodeByParam('id',id);// 获取id为-1的点
		zTree1.selectNode(node);
		zTree1.expandNode(node, true, false , true);
	}


</script>
</body>
</html>