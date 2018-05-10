<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<div class="span12">
	<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>
	<div class="portlet box grey">
		<div class="portlet-title">
			<div class="caption">
				<span class="hidden-480">添加目录类型</span>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="${path }/sys/tenant/tenant/department/save" class="form-horizontal" id="addForm" method="post">
				<input type="hidden" id="id" name="id" value=""/>
				<input type="hidden" id="currentNodeId" name="currentNodeId" value="${currentNodeId}"/>
				<input type="hidden" id="currentNodeName" name="currentNodeName" value="${currentNodeName}"/>
				<input type="hidden" id="currentNodeParentId" name="currentNodeParentId" value="${currentNodeParentId}"/>
				<input type="hidden" id="parentSort" name="parentSort" value="${parentSort}"/>
				<div id="parentNameGroup" class="control-group" >
					<label class="control-label">上级目录</label>
					<div class="controls">
						<Tree:tree id="parentId" valueName="parentName" treeUrl="${path}/sys/admin/dzda/catalogType/add/edit/tree" token="${sessionScope.OWASP_CSRFTOKEN}"
								   submitType="post" dataType="json" isSearch="false" isSelectTree="true" required="false" onClick="onClickByTree1"
								   defaultkeys="${vo.parentId}" defaultvalues="${vo.parentName}" />
					</div>
				</div>
				<div id="nameGroup" class="control-group">
					<label class="control-label">目录名称<span class="required">*</span></label>
					<div class="controls">
						<input type="text" id="name" name="name" class="span6 m-wrap" required maxlength="200">
					</div>
				</div>
				<div id="sortGroup" class="control-group" >
					<label class="control-label">排序<span class="required">*</span></label>
					<div class="controls">
						<input type="text" id="sort" onblur="onblurSort(this)" name="sort" number="true" class="span6 m-wrap" required max="999" value="${vo.sort}" >
					</div>

				</div>
				<div id="pcsjValueGroup" class="control-group">
					<label class="control-label">目录编码</label>
					<div class="controls">
						<input size="16" type="text"  class="span6 m-wrap" value="${vo.catalogCode}" readonly id="catalogCode" name="catalogCode" >
					</div>
				</div>
				<div class="form-actions">
					<button id="submitbut" type="button" class="btn green mybutton" ><i class='icon-ok'></i> 确定</button>
					<a href="javascript:returnList();" class="btn btn-default" data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
					</a>
				</div>
			</form>
			<!-- END FORM-->
		</div>
</div>
</div>
<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="text/javascript">
	var addForm = new EstValidate("addForm");

	$(function(){
		$("#submitbut").on("click",function(){
			var currentNodeId = $("#currentNodeId").val();
			var currentNodeName = $("#currentNodeName").val();
			var currentNodeParentId = $("#currentNodeParentId").val();
			var bool = addForm.form();
			if(bool){
				$.ajax({
					url : "${path}/sys/admin/dzda/catalogType/save",
					type : "post",
					data : $("#addForm").serialize(),
					dataType : "json",
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					success : function(json){
						if(json.success){
							refreshTree();
							$.ajax({
								url: "${path}/sys/admin/dzda/catalogType/ajax/list",// 请求的action路径
								type: 'POST',
								dataType : "html",
								data:{
								"currentNodeId":currentNodeId,
								"currentNodeParentId":currentNodeParentId,
								"currentNodeName":currentNodeName
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
						}else{
							document.getElementById("addForm").reset();
							$(".control-group").removeClass("error").removeClass("success");
							$(".help-inline").remove();
							showTip("警告","新增部门项失败!",2000);
						}
					},
					error : function(){
						document.getElementById("addForm").reset();
						$(".control-group").removeClass("error").removeClass("success");
						$(".help-inline").remove();
					}
				});
			}
		});
	});

	function returnList(){
		var currentNodeId = $("#currentNodeId").val();
		var currentNodeName = $("#currentNodeName").val();
		var currentNodeParentId = $("#currentNodeParentId").val();
		$.ajax({
			url: "${path}/sys/admin/dzda/catalogType/ajax/list",// 请求的action路径
			type: 'POST',
			dataType : "html",
			data:{
				"currentNodeId":currentNodeId,
				"currentNodeParentId":currentNodeParentId,
				"currentNodeName":currentNodeName
			},
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(html){
				$("#rightList").html(html);
			},
			error : function(){
				alert('请求失败!');
			}
		});
	}

	function onClickByTree1 (event, treeId, treeNode){
		$("#currentNodeId").val(treeNode.id);//赋值
		$("#currentNodeName").val(treeNode.name);//赋值
		$("#currentNodeParentId").val(treeNode.pId);//赋值
		var currentNodeId = $("#currentNodeId").val();
		var currentNodeName = $("#currentNodeName").val();
		var currentNodeParentId = $("#currentNodeParentId").val();
		$.ajax({
			url : "${path}/sys/admin/dzda/catalogType/ajax/changeAddEdit?currentNodeId="+currentNodeId+"&currentNodeName="+currentNodeName+"&currentNodeParentId="+currentNodeParentId,
			type : "get",
			data : null,
			dataType : "json",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(data){
				$("#sort").val(data.sort);//赋值
				$("#catalogCode").val(data.catalogCode);
			},
			error : function(){

			}
		});
	}
	function onblurSort (obj){

		var currentNodeId = $("#currentNodeId").val();
		var currentNodeName = $("#currentNodeName").val();
		var currentNodeParentId = $("#currentNodeParentId").val();
		var parentSort = $("#parentSort").val();
		var sort = $("#sort").val();
		if(currentNodeParentId==''){
			if(sort>=10){
				$("#catalogCode").val(sort*10);
			}else {
				$("#catalogCode").val('0'+sort*10);
			}
		}else {
			var code = parentSort*10+sort*1;
			if(parentSort>=10){
				$("#catalogCode").val(code);
			}else {
				$("#catalogCode").val('0'+code);
			}
		}

	}
</script>
