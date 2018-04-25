<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑字典</title>
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

							<span class="hidden-480">编辑字典</span>

						</div>
					</div>

					<div class="portlet-body form">
						<!-- BEGIN FORM-->
								<form action="${path}/sys/admin/dictionary/type/save" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="id" name="id" value="${vo.id}"/>
									<div id="nameGroup" class="control-group">
										<label class="control-label">字典名称<span class="required">*</span></label>
										<div class="controls">
											<input type="text" id="name" name="name" value="${vo.name}" class="span6 m-wrap" required maxlength="200">
											<!-- <span class="help-inline">Some hint here</span> -->
										</div>
									</div>
									<div class="control-group" id="codeGroup">
										<label class="control-label">字典代码<span class="required">*</span></label>
										<div class="controls">
											<input type="text" id="code" name="code" value="${vo.code}" class="span6 m-wrap" required maxlength="100" placeholder="唯一标示">
										</div>

									</div>
									<div class="control-group" id="remarkGroup">
										<label class="control-label">备注</label>
										<div class="controls">
											<textarea id="remark" name="remark" class="span6 m-wrap" maxlength="255" rows="5">${vo.remark}</textarea>
										</div>
									</div>
									<div class="form-actions">
										<button id="submitbut" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<a href="${path}/sys/admin/dictionary/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
				$.ajax({
					url : "${path}/sys/admin/dictionary/update",
					type : "post",
					data : $("#addForm").serialize(),
					dataType : "json",
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					success : function(json){
						if(json.success){
							//document.getElementById("addForm").reset();
							//$(".control-group").removeClass("error").removeClass("success");
							//$(".help-inline").remove();
							//ue.setContent('');
							//window.location.reload();
							showTip("提示","修改字典类型成功!",2000);
							setTimeout("window.location.href='${path}/sys/admin/dictionary/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);
						}else{
							document.getElementById("addForm").reset();
							$(".control-group").removeClass("error").removeClass("success");
							$(".help-inline").remove();
							showTip("警告","修改字典类型失败!",2000);
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
	</script>
</body>
</html>