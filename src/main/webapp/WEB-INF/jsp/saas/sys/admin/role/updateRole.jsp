<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑角色</title>
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

							<span class="hidden-480">编辑角色</span>

						</div>
						<div class="tools">
							<a href="javascript:location.reload();" class="reload"></a>
						</div>
					</div>

					<div class="portlet-body form">
						<form action="${path}/sys/admin/role/update" class="form-horizontal myform"
							  id="addForm" name="addForm" method="post">
							<input name="id" type="hidden" value='<c:out value="${vo.id }"></c:out>'/>
							<div class="tab-pane active" id="tab1">
								<div>
	
									<div class="control-group" id="roleNameGroup">
	
										<label class="control-label mylabel">角色名<span
											class="required">*</span>
										</label>
	
										<div class="controls">
											<input type="text" class="span6 m-wrap myinput " name="roleName" id="roleName" required
												placeholder="长度为1-30位" value='${cloud:htmlEscape(vo.roleName)}'/> <span
												class="help-inline"></span>
	
										</div>
	
									</div>
									<div class="control-group" id="roleCodeGroup">
		
											<label class="control-label mylabel">角色代码<span
												class="required">*</span>
											</label>
		
											<div class="controls">
												<input type="text" class="span6 m-wrap myinput " required name="roleCode" id="roleCode" value='<c:out value="${vo.roleCode}"></c:out>'
												placeholder="必须以ROLE_开头,且唯一" rolePattern="true"/>
												<span class="help-inline"></span>
											</div>
		
										</div>
									<div class="control-group" id="sortGroup">

										<label class="control-label mylabel">排序<span
												class="required">*</span>
										</label>

										<div class="controls">
											<input type="text" class="span6 m-wrap myinput required" name="sort" id="sort"
												   required digits="true" value="<c:out value="${vo.sort}"></c:out>"/>
											<span class="help-inline"></span>
										</div>

									</div>
									<div class="control-group" id="descriptionGroup">

										<label class="control-label mylabel">描述<span
												class="required">*</span>
										</label>

										<div class="controls">
											<textarea name="description" id="description"  cols="40" >${cloud:htmlEscape(vo.description)}</textarea>
										</div>

									</div>
									<div  class="form-actions">
										<button id="submitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<a href="${path}/sys//admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
				var bool = addForm.form();
				if (bool) {
					$.ajax({
						url : "${path}/sys/admin/role/update",
						type : "post",
						data : $("#addForm").serialize(),
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(data){
							if (data.success) {
								$("#submitBtn").html("修改成功");
								showTip("提示", "修改成功！");

								setTimeout("$('#myModal').modal('hide');",1000);
								setTimeout(function(){
									window.location.href='${path}/sys/admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';
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