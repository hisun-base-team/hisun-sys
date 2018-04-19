<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<link href='<c:url value="/css/cropbox.css" />' rel="stylesheet" type="text/css"/>
<style type="text/css">
</style>
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

							<span class="hidden-480">添加用户</span>

						</div>
						<div class="tools">
							<a href="javascript:location.reload();" class="reload"></a>
						</div>
					</div>

					<div class="portlet-body form">
						<form action="<%=path%>/sys/tenant/user/saveByAdd" class="form-horizontal myform"
							id="formbox" method="post">
							<input type="hidden" name="fileName" id="fileName" />
							<div class="tab-pane active" id="tab1">
								<div>
	
									<div class="control-group">

										<label class="control-label mylabel">邮箱<span
											class="required">*</span>
										</label>
				
										<div class="controls inputtext">
											<input type="hidden" id="id" name="id" class="myinput"/>
											<input type="text" class="span6 m-wrap myinput" name="email" id="email"
												class="required mail" placeholder="你的常用邮箱地址" csrftoken="${sessionScope.OWASP_CSRFTOKEN}"
												/> <span class="help-inline"></span>
										</div>
				
									</div>
				
									<div class="control-group">
				
										<label class="control-label mylabel">用户名<span
											class="required">*</span>
										</label>
				 
										<div class="controls inputtext">
				
											<input type="text" class="span6 m-wrap myinput" name="username" id="username"
												class="required" placeholder="长度为5-30位" csrftoken="${sessionScope.OWASP_CSRFTOKEN}"/> <span
												class="help-inline"></span>
				
										</div>
				
									</div>
									<div style="clear: none;"></div> 
									<div class="control-group">
				
										<label class="control-label mylabel">真实姓名<span
											class="required">*</span>
										</label>
				
										<div class="controls inputtext">
				
											<input type="text" class="span6 m-wrap myinput" name="realname" id="realname"
												class="required" placeholder="输入真实姓名" /> <span
												class="help-inline"></span>
				
										</div>
				
									</div>
									<div class="control-group" id="sexGroup">
										<label class="control-label">性别<span class="required">*</span></label>
										<div class="controls inputtext">
											<select name="sex" id="sex" required >
												<option value="">请选择</option>
												<option value="1">男</option>
												<option value="0">女</option>
											</select>
										</div>
									</div>
                                    <div class="control-group">
                                        <label class="control-label mylabel">用户角色<span
                                                class="required">*</span>
                                        </label>

                                        <div class="controls">
                                            <select id="roleId" name="roleId" style="width: 340px">
                                                <c:forEach items="${roles}" var="role" varStatus="status">
                                                    <option value="${role.id}">${role.roleName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
									<div id="positionalGroup" class="control-group">
										<label class="control-label">单位职务<span class="required">*</span></label>
										<div class="controls">
											<input type="text" placeholder="科长" class="m-wrap span6" id="positional" name="positional" required />
											<span class="help-inline"></span>
										</div>
									</div>
									
									<div class="control-group" >
				
										<label class="control-label mylabel">手机号码<span
											class="required">*</span>
										</label>
				
										<div class="controls inputtext">
				
											<input type="text" class="span6 m-wrap myinput" name="tel" id="tel"
												class="required" placeholder="输入手机号码" /> <span
												class="help-inline"></span>
				
										</div>
				
									</div>
									<div style="clear: none;"></div>
									<div class="control-group">
										<label class="control-label mylabel">设置密码<span
												class="required">*</span></label>
										<!--<span class="label_gl"><span class="red">*</span>设置密码：</span>-->
										<div class="controls inputtext">
											<input type="password" id="pwd" class="span6 m-wrap myinput"  name="pwd" class="text" tabindex="2"  placeholder="输入密码"/>
											<span class="help-inline"></span>
											<span class="clear"></span>
											<label class="hide" id="pwdstrength"><span class="fl">安全程度：</span><b></b></label>
											<label id="pwd_error"></label>
										</div>
									</div><!--item end-->

									<div class="control-group">
										<label class="control-label mylabel">确认密码<span
												class="required">*</span></label>
										<!--<span class="label_gl"><span class="red">*</span>确认密码：</span>-->
										<div class="controls inputtext">
											<input type="password" id="pwd2" name="pwd2" class="span6 m-wrap myinput" class="text" tabindex="3" placeholder="再输入一次密码"/>
											<span class="help-inline"></span>
											<span class="clear"></span>
											<label id="pwd2_error"></label>
										</div>
									</div>
									<!--item end-->
									<div style="clear: none;"></div> 
									<div class="control-group">
										<label class="control-label mylabel">头像<span
											class="required">*</span>
										</label>
										<div class="container" style="width: 640px;margin-left: 200px;">
							                  <div class="imageBox">
							                    <div class="thumbBox"></div>
							                    <div class="spinner" style="display: none">Loading...</div>
							                  </div>
							                  <div class="action"> 
							                    <!-- <input type="file" id="file" style=" width: 200px">-->
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
							                  <input id="tenantId" name="tenantId" type="hidden" value="${param.tenantId }">
							          </div>
									</div>
									<div class="form-actions">
										<button id="submitBtn" type="submit" class="btn green mybutton"><i class='icon-ok'></i> 确认</button>
										<a href="${path}/sys/tenant/user/list/?tenantId=${param.tenantId }&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
											data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
							           	</a>
									</div>
	
								</div>
							</div>
						</form>
					</div>

					<%-- END SAMPLE FORM PORTLET--%>
				</div>
			</div>
		</div>
		<%-- <script type="text/javascript" src="${path }/js/xiuxiu/xiuxiu.js"></script> --%>
		<script type="application/javascript">
			window.PATH = "${path}";
		</script>
		<script type="text/javascript" src="<%=path%>/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="<%=path%>/js/common/custom-validate.js"></script>
		<script type="text/javascript" src="<%=path%>/js/common/est-validate-init.js"></script>
		<script type="text/javascript" src="<%=path%>/js/common/validate-message.js"></script>
		<script type="text/javascript">
		var FormValidation = function() {
			
			jQuery.validator.addMethod("isMobile", function(value, element) {  
			     var length = value.length;     
			     var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;     
			     return (length == 11 && mobile.exec(value))? true:false;  
			 }, "请正确填写您的手机号码"); 
			
			return {
				//main function to initiate the module
				init : function() {

					function format(state) {
						if (!state.id)
							return state.text; // optgroup
						return "<img class='flag' src='assets/img/flags/"
								+ state.id.toLowerCase()
								+ ".png'/>&nbsp;&nbsp;" + state.text;
					}

					var form = $('#formbox');

					form.validate({
						doNotHideMessage : true, //this option enables to show the error/success messages on tab switch.
						errorElement : 'span', //default input error message container
						errorClass : 'validate-inline', // default input error message class
						focusInvalid : false, // do not focus the last invalid input
						rules : {
							username : {
								minlength : 5,
								maxlength : 30,
								required : true,
								tenantUsernameUnique : true
							},
							positional : {
								required : true
							},
							pwd : {
								required : true
							},
							pwd2 : {
								required : true,
								equalTo : "#pwd"
							},
							realname : {
								required : true
							},
							tel : {
								required : true,
								isMobile : true
							},
							email : {
								required : true,
								email : true,
								tenantEmailUnique : true
							}
						},

						messages : {
							username : {
								required : "输入的用户名不能为空",
								minlength : "输入的用户名必须为5-30位",
								maxlength : "输入的用户名必须为5-30位",
								remote : "用户名已存在"
							},
							positional : {
								required : "输入的职务不能为空"
							},
							pwd : {
								required : "输入的密码不能为空"
							},
							pwd2 : {
								required : "输入的确认密码不能为空",
								equalTo : "两次填写的密码不一致"
							},
							realname : {
								required : "输入的真实姓名不能为空"
							},
							tel : {
								required : "输入的手机号码不能为空"
							},
							email : {
								required : "输入的邮箱不能为空",
								email : "输入邮件的格式不正确",
								remote : "该邮箱已被注册"
							}
						},

						errorPlacement : function(error, element) { // render error placement for each input type
							var error_td = element.next('.help-inline');
							error_td.append(error);
						},

						highlight : function(element) { // hightlight error inputs
							$(element).closest('.help-inline')
									.removeClass('ok'); // display OK icon
							$(element).closest('.control-group').removeClass(
									'success').addClass('error'); // set error class to the control group
						},

						unhighlight : function(element) { // revert the change dony by hightlight
							$(element).closest('.control-group').removeClass(
									'error'); // set error class to the control group
						},

						success : function(label) {
							label.addClass('valid ok') // mark the current input as valid and display OK icon
							.closest('.control-group').removeClass('error')
									.addClass('success'); // set success class to the control group
						},

						submitHandler : function(form) {

								$(form).ajaxSubmit({
											beforeSend : function() {
												$("#submitBtn").html("保存中...");
												$("#submitBtn").attr("disabled", "disabled");
											},
									headers: {
										"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
									},
											success : function(data) {
												if (data.success == "true" || data.success == true) {
													showSaveSuccess();
													setTimeout("window.location.href='${path}/sys/tenant/user/list/?tenantId=${param.tenantId }&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);
												}else{
													showTip("提示", data.message, null);
												}
											}
		
										});

						}

					});
				}

			};

		}();
		
		$(function(){
			FormValidation.init();
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
		<script src="${path }/js/validate.js" type="text/javascript" ></script>
</body>
</html>