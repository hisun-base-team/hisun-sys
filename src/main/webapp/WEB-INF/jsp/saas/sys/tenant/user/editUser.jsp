<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户</title>
<link href="${path}/css/cropbox.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>
			<div class="portlet box grey">
				<div class="portlet-title">
					<div class="caption">
						<span class="hidden-480">修改用户</span>
					</div>
					<div class="clearfix fr">
						<button id="submitBtn" type="button" class="btn green "><i class='icon-ok'></i> 确认</button>
						<a href="${path}/sys/tenant/user/index?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default" data-dismiss="modal"><i class='icon-remove-sign'></i> 取消</a>
					</div>
				</div>
				<div class="portlet-body form">
					<form action="${path}/sys/tenant/user/update" class="form-horizontal" id="updateForm" method="post">
						<input type="hidden" name="fileName" id="fileName" />
						<input type="hidden" name="currentNodeId" id="currentNodeId" value="${currentNodeId}" />
						<div class="tab-pane active" id="tab1">
							<div id="usernameGroup" class="control-group">
								<label class="control-label ">用户名<span class="required">*</span>
								</label>
								<div class="controls ">
									<input type="text" class="span6 m-wrap " name="username" id="username" maxlength="30" placeholder="长度为5-30位" value="${vo.username}" readonly required  />
								</div>
							</div>
							<div id="realnameGroup" class="control-group">
								<label class="control-label ">姓名<span class="required">*</span></label>
								<div class="controls ">
									<input type="text" class="span6 m-wrap " name="realname" id="realname" value="${vo.realname}" required placeholder="输入姓名" />
								</div>
							</div>
							<div id="sexStrGroup" class="control-group" >
								<label class="control-label">性别</label>
								<div class="controls ">
									<select name="sexStr" id="sexStr" >
										<option value="">请选择</option>
										<option value="1" <c:if test="${vo.sexStr ne '1'}"> selected</c:if> >男</option>
										<option value="0" <c:if test="${vo.sexStr ne '0'}"> selected</c:if> >女</option>
									</select>
								</div>
							</div>
							<div id="positionalGroup" class="control-group">
								<label class="control-label">单位职务</label>
								<div class="controls">
									<input type="text" placeholder="科长" class="m-wrap span6" id="positional" name="positional" maxlength="64" value="${vo.positional}" />
								</div>
							</div>
							<div id="telGroup" class="control-group" >
								<label class="control-label ">手机号码
								</label>
								<div class="controls ">
									<input type="text" class="span6 m-wrap " name="tel" id="tel" placeholder="输入手机号码" maxlength="20" value="${vo.tel}"  />
								</div>
							</div>
							<div id="emailGroup" class="control-group">
								<label class="control-label ">邮箱</label>
								<div class="controls ">
									<input type="text" class="span6 m-wrap " name="email" id="email"  placeholder="常用邮箱地址" value="${vo.email}" />
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${path}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path}/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path}/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path}/js/common/validate-message.js"></script>
<script type="text/javascript">
	var updateForm = new EstValidate("updateForm");
	$(function(){
		$("#submitBtn").on("click",function(){
			var currentNodeId = $("#currentNodeId").val();
			var bool = updateForm.form();
			if(bool){
				$("#addForm").ajaxSubmit({
					beforeSend : function() {
						$("#submitBtn").html("保存中...");
						$("#submitBtn").attr("disabled", "disabled");
					},
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					success : function(data) {
						if (data.success == "true" || data.success == true) {
							showTip("提示","保存成功!", 1300);
							window.location.href="${path}/sys/tenant/user/index?currentNodeId="+currentNodeId+"&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
						}else{
							$("#submitBtn").html("保存");
							$("#submitBtn").removeAttr("disabled");
							showTip("提示", data.message, 1300);
						}
					},
					error : function(data){
						$("#submitBtn").html("保存");
						$("#submitBtn").removeAttr("disabled");
						showTip("提示", "系统错误，请联系管理员", 1300);
					}
				});
			}
		});
	});

$(function(){
	 var options =
		{
			thumbBox: '.thumbBox',
			spinner: '.spinner',
			imgSrc: '${path }/images/head.png'
		}
		var cropper = $('.imageBox').cropbox(options);
		$('#upload-file').on('change', function(){
			var reader = new FileReader();
			reader.onload = function(e) {
				options.imgSrc = e.target.result;
				cropper = $('.imageBox').cropbox(options);
			}
			reader.readAsDataURL(this.files[0]);
			$(this).removeAttr("value");
		})
		$('#btnCrop').on('click', function(){
			var img = cropper.getDataURL();
			$('.cropped').html('');
			$("#headPhotoImg").val(img);
			$('.cropped').append('<img src="'+img+'" style="width:150px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 6px #7E7E7E;"><p>150px*150px</p>');
		})
		$('#btnZoomIn').on('click', function(){
			cropper.zoomIn();
		})
		$('#btnZoomOut').on('click', function(){
			cropper.zoomOut();
		})
});
</script>
<script type="text/javascript" src="${path }/js/cropbox.js"></script>
<script type="text/javascript" src="${path }/js/validate.js" ></script>
</body>
</html>