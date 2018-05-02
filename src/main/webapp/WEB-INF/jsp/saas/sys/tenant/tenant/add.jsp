<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加租户</title>
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
						<span class="hidden-480">添加租户</span>
					</div>
				</div>
				<div class="portlet-body form">
					<form class="form-horizontal " id="form1" method="post">
						<div id="nameGroup" class="control-group">
							<label class="control-label mylabel">租户名称<span
									class="required">*</span>
							</label>
							<div class="controls">
								<input class="span6 m-wrap" type="text" name="name" id="name" required maxlength="100" onblur="getSimpleName();">
							</div>
						</div>
						<div id="shortNameGroup" class="control-group">
							<label class="control-label mylabel">租户简称<span
									class="required">*</span>
							</label>
							<div class="controls">
								<input class="span6 m-wrap" type="text" name="shortName" id="shortName" required maxlength="60" onblur="getSimpleName();">
							</div>
						</div>
						<div id="shortNamePyGroup" class="control-group">
							<label class="control-label mylabel">简称拼音<span
									class="required">*</span>
							</label>
							<div class="controls">
								<input class="span6 m-wrap" type="text" name="shortNamePy" id="shortNamePy"  required maxlength="15">
							</div>
						</div>

						<div id="adminUserNameGroup" class="control-group">
							<label class="control-label mylabel">默认管理员账号<span
									class="required">*</span>
							</label>
							<div class="controls">
								<input class="span6 m-wrap" type="text" name="adminUserName" id="adminUserName" value="" required minlength="4" maxlength="30" usernamePattern="true" tenantUsernameUnique="true" csrftoken="${sessionScope.OWASP_CSRFTOKEN}">
							</div>
						</div>
						<div id="adminUserPasswordGroup" class="control-group">
							<label class="control-label mylabel">默认账号密码<span class="required">*</span>
							</label>
							<div class="controls">
								<input class="span6 m-wrap" type="text" name="adminUserPassword" id="adminUserPassword"  value="" required="true" minlength="6" maxlength="20" >
							</div>
						</div>

						<div  class="form-actions">
							<button id="submitBtn" type="button" class="btn green mybutton" onclick="saveSubmit()"><i class='icon-ok'></i> 确定</button>
							<a href="${path}/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
							   data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
							</a>
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
	(function(){
		App.init();
		$("#adminUserName").val('');
		$("#adminUserPassword").val('');
	})();

	var myVld = new EstValidate2("form1");
	var myLoading = new MyLoading("${path}",{zindex : 11111});
	function saveSubmit(){
		var bool = myVld.form();
		if(bool){
			myLoading.show();
			$.ajax({
				url : "${path }/sys/tenant/tenant/save",
				type : "post",
				data : $('#form1').serialize(),
				dataType : "json",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(json){
					myLoading.hide();
					if(json.code == 1){
						showTip("提示","操作成功!",2000);
						setTimeout(function(){
							window.location.href = "${path}/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"
						},2000) ;
					}else{
						showTip("提示", json.message, 2000);
					}
				},
				error : function(arg1, arg2, arg3){
					myLoading.hide();
					showTip("提示","出错了,请联系管理员!",2000);
				}
			});
		}
	}
	function getSimpleName(){
		var name = $("#name").val().trim();
		var shortName = $("#shortName").val().trim();
		if(name!="" && shortName==""){
			$("#shortName").val(name);
		}
		$.ajax({
			url : "${path}/sys/tenant/tenant/ajax/getTenantNameCode",
			type : "post",
			data: {"name":name,
				"shortName":shortName
			},
			headers:{
				OWASP_CSRFTOKEN:"${sessionScope.OWASP_CSRFTOKEN}"
			},
			dataType : "json",
			success : function(data){
				$("#shortNamePy").val(data.pinYinHead);
				$("#adminUserName").val(data.pinYinHead+"_admin");
				$("#adminUserPassword").val(data.pinYinHead+"_admin111111");
			},
			error : function(){
				showTip("提示","出错了请联系管理员", 1500);
			}
		});
	}
</script>
</body>
</html>