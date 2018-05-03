<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
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
<title>编辑角色模板</title>
</head>
<body>
	<!-- BEGIN PAGE CONTAINER-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>
				<div class="portlet box grey">
					<div class="portlet-title">
						<div class="caption">
							<span class="hidden-480">编辑角色模板</span>
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form action="${path }/sys/tenant/role/tplt/update" class="form-horizontal" id="addForm" method="post">
							<input type="hidden" name="id" id="id" value="${vo.id}" />
							<div id="roleNameGroup" class="control-group">
								<label class="control-label">角色名称<span class="required">*</span></label>
								<div class="controls">
									<input type="text" id="roleName" name="roleName" class="span6 m-wrap" value="${vo.roleName}" required maxlength="100">
									<!-- <span class="help-inline">Some hint here</span> -->
								</div>
							</div>
							<div class="control-group" id="roleCodePrefixGroup">
								<label class="control-label">角色代码前缀<span class="required">*</span></label>
								<div class="controls">
									<input type="text" id="roleCodePrefix" name="roleCodePrefix" class="span6 m-wrap"  value="${vo.roleCodePrefix}" required maxlength="100" placeholder="唯一标示">
								</div>

							</div>
							<div class="control-group" id="resourceNamesGroup">
								<label class="control-label">资源权限<span class="required">*</span></label>
								<div class="controls">
									<Tree:tree id="resourceIds" valueName="resourceNames"  treeUrl="${path}/sys/tenant/resource/select/tree" token="${sessionScope.OWASP_CSRFTOKEN}"
											   required="true" onClick="" selectClass="m-wrap span6" radioOrCheckbox="checkbox" submitType="post"
											   dataType="json" isSearch="false" isSelectTree="true" chkboxType="'Y':'ps','N':'ps'" defaultkeys="${vo.resourceIds}"
											   defaultvalues="${vo.resourceNames}" />
								</div>
							</div>
							<div class="control-group" id="roleDescriptionGroup">
								<label class="control-label">备注</label>
								<div class="controls">
									<textarea id="roleDescription" name="roleDescription" class="span6 m-wrap"  maxlength="200" rows="5">${vo.roleDescription}</textarea>
								</div>
							</div>
							<div class="form-actions">
								<button id="submitbut" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
								<a href="${path }/sys/tenant/role/tplt/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
								   data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
								</a>
							</div>
						</form>
						<!-- END FORM-->
					</div>
			</div>
		</div>
		</div>
	</div>
	<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
	<script type="text/javascript">
	var addForm = new EstValidate("addForm");
	$(function(){
		$("#submitbut").on("click",function(){
			var bool = addForm.form();
			if(bool){
				localPost("${path}/sys/tenant/role/tplt/code/check",{
					"code":$("#roleCodePrefix").val(),
					"id":$("#id").val()
				},function(data) {
					if(!data.success){
						showTip("提示", "角色模板代码必须唯一！");
					}else{
						$.ajax({
							url : "${path}/sys/tenant/role/tplt/update",
							type : "post",
							data : $("#addForm").serialize(),
							dataType : "json",
							headers: {
								"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
							},
							success : function(json){
								if(json.success){
									showTip("提示","修改角色模板类型成功!",2000);
									setTimeout("window.location.href='${path}/sys/tenant/role/tplt/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);
								}else{
									document.getElementById("addForm").reset();
									$(".control-group").removeClass("error").removeClass("success");
									$(".help-inline").remove();
									showTip("警告","修改角色模板类型失败!",2000);
								}
							},
							error : function(){
								showTip("警告","修改角色模板类型失败!",2000);
								document.getElementById("addForm").reset();
								$(".control-group").removeClass("error").removeClass("success");
								$(".help-inline").remove();
							}
						});
					}
				},"json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
			}
		});
	});
	</script>
</body>
</html>