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
				<span class="hidden-480">添加字典项</span>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="${path }/sys/admin/dictionary/save" class="form-horizontal" id="addForm" method="post">
				<input type="hidden" id="id" name="id" value=""/>
				<input type="hidden" id="currentNodeId" name="currentNodeId" value="${currentNodeId}"/>
				<input type="hidden" id="currentNodeName" name="currentNodeName" value="${currentNodeName}"/>
				<input type="hidden" id="currentNodeParentId" name="currentNodeParentId" value="${currentNodeParentId}"/>
				<input type="hidden" id="typeId" name="typeId" value="${typeId}"/>
				<div id="nameGroup" class="control-group">
					<label class="control-label">字典项名称<span class="required">*</span></label>
					<div class="controls">
						<input type="text" id="name" name="name" class="span6 m-wrap" required maxlength="200">
						<!-- <span class="help-inline">Some hint here</span> -->
					</div>
				</div>
				<div id="codeGroup" class="control-group">
					<label class="control-label">字典项代码<span class="required">*</span></label>
					<div class="controls">
						<input type="text" id="code" name="code" class="span6 m-wrap" required maxlength="100" placeholder="唯一标示">
					</div>

				</div>
				<div id="pId_valueGroup" class="control-group" >
					<label class="control-label">上级字典项<span class="required">*</span></label>
					<div class="controls">
						<Tree:tree id="pId" treeUrl="${path}/sys/admin/dictionary/item/tree/${typeId}" token="${sessionScope.OWASP_CSRFTOKEN}"
								   submitType="post" dataType="json" isSearch="false" isSelectTree="true" defaultkeys="${currentNodeId}" defaultvalues="${currentNodeName}"/>
					</div>

				</div>
				<div class="control-group" id="displayGroup">
					<label class="control-label">是否显示<span class="required">*</span></label>
					<div class="controls" >
						<select name="display" id="display" class="span6 m-wrap" >
							<option value="0" selected>是</option>
							<option value="1">否</option>
						</select>
					</div>
				</div>
				<div id="sortGroup" class="control-group" >
					<label class="control-label">排序<span class="required">*</span></label>
					<div class="controls">
						<input type="text" id="sort" name="sort" number="true" class="span6 m-wrap" required max="999" value="${sort}" >
					</div>

				</div>
				<div id="remarkGroup" class="control-group" >
					<label class="control-label">备注</label>
					<div class="controls">
						<textarea id="remark" name="remark" class="span6 m-wrap" maxlength="255" rows="5"></textarea>
					</div>
				</div>
				<div class="form-actions">
					<button id="submitbut" type="button" class="btn green mybutton" ><i class='icon-ok'></i> 确定</button>
					<a href="javascript:returnItemList();" class="btn btn-default"
					   data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
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
			var typeId = "${typeId}";
			localPost("${path}/sys/admin/dictionary/item/code/check",{
				"typeId":"${typeId}",
				"code":$("#code").val(),
				"id":$("#id").val()
			},function(data) {
				if(!data.success){
					showTip("提示", "字典项代码必须唯一！");
				}else{
					var bool = addForm.form();
					if(bool){
						$.ajax({
							url : "${path}/sys/admin/dictionary/item/save",
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
										url: "${path}/sys/admin/dictionary/item/ajax/list",// 请求的action路径
										type: 'POST',
										dataType : "html",
										data:{
										"currentNodeId":currentNodeId,
										"currentNodeParentId":currentNodeName,
										"currentNodeName":currentNodeParentId,
										"typeId":"${typeId}"
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



									<%--setTimeout("window.location.href='${path}/sys/admin/dictionary/item/ajax/list?typeId="+typeId+"&currentNodeId="+currentNodeId+"&currentNodeName="+currentNodeName+"&currentNodeParentId="+currentNodeParentId+"&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);--%>
								}else{
									document.getElementById("addForm").reset();
									$(".control-group").removeClass("error").removeClass("success");
									$(".help-inline").remove();
									showTip("警告","新增字典项失败!",2000);
								}
							},
							error : function(){
								document.getElementById("addForm").reset();
								$(".control-group").removeClass("error").removeClass("success");
								$(".help-inline").remove();
							}
						});
					}
				}
			},"json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		});
	});

	function returnItemList(){
		var currentNodeId = $("#currentNodeId").val();
		var currentNodeName = $("#currentNodeName").val();
		var currentNodeParentId = $("#currentNodeParentId").val();
		$.ajax({
			url: "${path}/sys/admin/dictionary/item/ajax/list",// 请求的action路径
			type: 'POST',
			dataType : "html",
			data:{
				"currentNodeId":currentNodeId,
				"currentNodeParentId":currentNodeName,
				"currentNodeName":currentNodeParentId,
				"typeId":"${typeId}"
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
	}
	<%--$(function(){--%>
		<%--$("#submitbut").on("click",function(){--%>
			<%--var bool = addForm.form();--%>
			<%--if(bool){--%>
				<%--$.ajax({--%>
					<%--url : "${path}/sys/admin/dictionary/item/save",--%>
					<%--type : "post",--%>
					<%--data : $("#addForm").serialize(),--%>
					<%--dataType : "json",--%>
					<%--headers: {--%>
						<%--"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"--%>
					<%--},--%>
					<%--success : function(json){--%>
						<%--if(json.success){--%>
							<%--showTip("提示","新增字典类型成功!",2000);--%>
							<%--//setTimeout("window.location.href='${path}/sys/admin/dictionary/item/index/${typeId}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);--%>
						<%--}else{--%>
							<%--document.getElementById("addForm").reset();--%>
							<%--$(".control-group").removeClass("error").removeClass("success");--%>
							<%--$(".help-inline").remove();--%>
							<%--showTip("警告","新增字典类型失败!",2000);--%>
						<%--}--%>
					<%--},--%>
					<%--error : function(){--%>
						<%--alert("aaaaaaaaaaaa");--%>
						<%--document.getElementById("addForm").reset();--%>
						<%--$(".control-group").removeClass("error").removeClass("success");--%>
						<%--$(".help-inline").remove();--%>
					<%--}--%>
				<%--});--%>
			<%--}--%>
		<%--});--%>
	<%--});--%>
</script>
