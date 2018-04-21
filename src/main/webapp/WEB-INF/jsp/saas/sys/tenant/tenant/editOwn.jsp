<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改租户信息</title>
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

							<span class="hidden-480">修改租户信息</span>

						</div>
					</div>

					<div class="portlet-body form">
						<form class="form-horizontal myform"
						id="form1" method="post">
							<div class="tab-pane active" id="tab1">
								<div>
									<div id="nameGroup" class="control-group">
										<label class="control-label mylabel">租户名称<span
												class="required">*</span>
										</label>
										<div class="controls">
											<input class="span6 m-wrap" type="text" name="name" id="name" required maxlength="64" value="${cloud:htmlEscape(entity.name)}">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label mylabel">创建时间<span
												class="required">&nbsp;</span>
										</label>
										<div class="controls" style="margin-top: 8px;">
											<fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate>
										</div>
									</div>
									<div  class="form-actions">
										<button id="submitBtn" type="button" class="btn green mybutton" onclick="saveSubmit()"><i class='icon-ok'></i> 确定</button>
										<a href="${path}/sys/tenant/tenant/own/view?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="text/javascript">
	var myVld = new EstValidate2("form1");
	var myLoading = new MyLoading("${path}",{zindex : 11111});
	function saveSubmit(){
		var bool = myVld.form();
		if(bool){
			myLoading.show();
			$.ajax({
				url : "${path }/sys/tenant/tenant/own/update",
				type : "post",
				data : $('#form1').serialize(),
				dataType : "json",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(json){
					myLoading.hide();
					if(json.privilegeCode == 1){
						showTip("提示","操作成功",2000);
						setTimeout(function(){
							window.location.href = "${path}/sys/tenant/tenant/own/view?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
						},2000)
					}else{
						showTip("提示", json.message, 2000);
					}
				},
				error : function(arg1, arg2, arg3){
					myLoading.hide();
					showTip("提示","出错了请联系管理员",2000);
				}
			});
		}
	}
</script>
</body>
</html>