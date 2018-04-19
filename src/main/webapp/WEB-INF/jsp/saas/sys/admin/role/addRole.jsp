<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加角色</title>
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

							<span class="hidden-480">添加角色</span>

						</div>
						<div class="tools">
							<a href="javascript:location.reload();" class="reload"></a>
						</div>
					</div>

					<div class="portlet-body form">
						<form action="<%=path%>/sys/admin/role/save" class="form-horizontal myform"
							  id="addForm" method="post">
							<div class="tab-pane active" id="tab1">
								<div>
	
									<div class="control-group" id="roleNameGroup">
	
										<label class="control-label mylabel">角色名<span
											class="required">*</span>
										</label>
	
										<div class="controls">
											<input type="hidden" id="id" name="id" class="myinput"/>
											<input type="text" class="span6 m-wrap myinput " name="roleName" id="roleName" required
												placeholder="长度为1-30位" /> <span
												class="help-inline"></span>
	
										</div>
	
									</div>
									<div class="control-group" id="roleCodeGroup">
		
											<label class="control-label mylabel">角色代码<span
												class="required">*</span>
											</label>
		
											<div class="controls">
												<input type="text" class="span6 m-wrap myinput required" name="roleCode" id="roleCode"
												placeholder="必须以ROLE_开头,且唯一" rolePattern="true" required/>
												<span class="help-inline"></span>
											</div>
		
										</div>

									<div class="control-group" id="sortGroup">

										<label class="control-label mylabel">排序<span
												class="required">*</span>
										</label>

										<div class="controls">
											<input type="text" class="span6 m-wrap myinput required" name="sort" id="sort"
												    required digits="true"/>
											<span class="help-inline"></span>
										</div>

									</div>
									<div class="control-group" id="descriptionGroup">

										<label class="control-label mylabel">描述<span
												class="required"></span>
										</label>

										<div class="controls">
											<textarea name="description" id="description"  cols="40"></textarea>
										</div>

									</div>
									<div  class="form-actions">
										<button id="submitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<a href="${path}/sys/admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
							               data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
							            </a>
									</div>
	
								</div>
							</div>
						</form>
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
			$("#submitBtn").on("click",function() {
				var bool = addForm.form()
				var aa = $("#addForm").serialize();
				if (bool) {
					$.ajax({
						url : "<%=path%>/sys/admin/role/save",
						type : "post",
						data : $("#addForm").serialize(),
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(data){
							if (data == "true") {
								$("#submitBtn").html("保存成功");

								showSaveSuccess();
								setTimeout("$('#myModal').modal('hide');",1000);
								setTimeout(function(){
									window.location.href='${path}/sys/admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'
								},1300);
							}
						}
					});
				}
			});
		});
	</script>		
</body>
</html>