<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<link href="${path}/css/cropbox.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="portlet box grey">
			<div class="portlet-title">
				<div class="caption">
					<span class="hidden-480">添加用户</span>
				</div>
				<div class="clearfix fr">
					<button id="submitBtn" type="button" class="btn green "><i class='icon-ok'></i> 确认</button>
					<a href="${path}/sys/tenant/user/index?currentNodeId=${currentNodeId}&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default" data-dismiss="modal"><i class='icon-remove-sign'></i> 取消</a>
				</div>
			</div>
			<div class="portlet-body form">
				<form action="${path}/sys/tenant/user/save" class="form-horizontal" id="addForm" method="post">
					<input type="hidden" name="fileName" id="fileName" />
					<input type="hidden" name="currentNodeId" id="currentNodeId" value="${currentNodeId}" />
					<div class="tab-pane active" id="tab1">
						<div id="usernameGroup" class="control-group">
							<label class="control-label ">用户名<span class="required">*</span>
							</label>
							<div class="controls ">
								<input type="text" class="span6 m-wrap " name="username" id="username" required maxlength="30" placeholder="长度为5-30位"  "/>
							</div>
						</div>
						<div id="realnameGroup" class="control-group">
							<label class="control-label ">姓名<span class="required">*</span></label>
							<div class="controls ">
								<input type="text" class="span6 m-wrap " name="realname" id="realname" required placeholder="输入姓名" />
							</div>
						</div>
						<div id="departmentNameGroup" class="control-group" >
							<label class="control-label">上级部门</label>
							<div class="controls">
								<Tree:tree id="departmentId" valueName="departmentName" treeUrl="${path}/sys/tenant/department/tree" token="${sessionScope.OWASP_CSRFTOKEN}"
										   submitType="post" dataType="json" isSearch="false" isSelectTree="true" required="false"
										   defaultkeys="${vo.departmentId}" defaultvalues="${vo.departmentName}" />
							</div>
						</div>
						<div id="sortGroup" class="control-group">
							<label class="control-label ">排序<span class="required">*</span>
							</label>
							<div class="controls ">
								<input type="text" class="span6 m-wrap " name="sort" id="sort" value="${vo.sort}" required  max="999" placeholder="排序号"  />
							</div>
						</div>
						<div id="pwdGroup" class="control-group">
							<label class="control-label">密码<span class="required">*</span></label>
							<div class="controls">
								<input class="m-wrap span6" type="password" name="pwd" id="pwd" class="password" minlength="6" maxlength="16" pwdStrengh="true" placeholder="密码">
							</div>
						</div>
						<div id="rePwdGroup" class="control-group">
							<label class="control-label">确认密码<span class="required">*</span></label>
							<div class="controls">
								<input class="m-wrap span6" type="password" name="rePwd" id="rePwd" class="password" required equals="pwd" placeholder="确认密码">
							</div>
						</div>

						<div id="sexStrGroup" class="control-group" >
							<label class="control-label">性别</label>
							<div class="controls ">
								<select name="sexStr" id="sexStr" >
									<option value="">请选择</option>
									<option value="1">男</option>
									<option value="0">女</option>
								</select>
							</div>
						</div>
						<div id="positionalGroup" class="control-group">
							<label class="control-label">单位职务</label>
							<div class="controls">
								<input type="text"  class="m-wrap span6" id="positional" name="positional" maxlength="64" placeholder="列如:科长" />
							</div>
						</div>
						<div id="telGroup" class="control-group" >
							<label class="control-label ">手机号码
							</label>
							<div class="controls ">
								<input type="text" class="span6 m-wrap " name="tel" id="tel" placeholder="输入手机号码" maxlength="20" />
							</div>
						</div>
						<div id="emailGroup" class="control-group">
							<label class="control-label ">邮箱</label>
							<div class="controls ">
								<input type="text" class="span6 m-wrap " name="email" id="email"   placeholder="常用邮箱地址" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label ">头像</label>
							<div class="container" style="width: 640px;margin-left: 200px;">
								<div class="imageBox">
									<div class="thumbBox"></div>
									<div class="spinner" style="display: none">Loading...</div>
								</div>
								<div class="action">
									<div class="new-contentarea tc"> <a href="javascript:void(0)" class="upload-img">
										<label for="upload-file">上传图像</label>
									</a>
										<input type="file" class="" name="upload-file" id="upload-file" />
									</div>
									<input type="button" id="btnCrop"  class="Btnsty_peyton" value="裁切">
									<input type="button" id="btnZoomIn" class="Btnsty_peyton blue" value="+"  >
									<input type="button" id="btnZoomOut" class="Btnsty_peyton blue" value="-" >
								</div>
								<div class="cropped"></div>
								<input id="headPhotoImg" name="headPhotoImg" value="" type="hidden"/>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${path}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path}/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path}/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path}/js/common/validate-message.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		App.init();//必须，不然导航栏及其菜单无法折叠
	});
	var addForm = new EstValidate("addForm");
	$(function(){
		$("#submitBtn").on("click",function(){
			var currentNodeId = $("#currentNodeId").val();
			var bool = addForm.form();
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