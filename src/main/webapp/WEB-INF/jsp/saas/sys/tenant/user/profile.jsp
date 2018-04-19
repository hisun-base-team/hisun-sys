<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<link href='<c:url value="/css/cropbox.css" />' rel="stylesheet" type="text/css"/>
<style type="text/css">
.sub-section {
	margin-bottom: 40px;
	padding-left: 20px;
}
.sub-section span {
  color: #aaa;
}	

.radio{
  position: relative;
  display: block;
  min-height: 20px;
  margin-top: 15px !important;
  margin-bottom: 10px;
  }
</style>
</head>
<body>
	
			<div class="container-fluid">
				
				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid profile">

					<div class="span12">

						<!--BEGIN TABS-->

						<div class="tabbable tabbable-custom tabbable-full-width">

							<ul class="nav nav-tabs">

							<!-- 	<li class="active"><a href="#tab_1_1" data-toggle="tab">Overview</a></li>-->

								<li class="active"><a href="#tab_1_2" data-toggle="tab">个人中心</a></li> 

								<li><a href="#tab_1_3" data-toggle="tab">信息维护</a></li>

								<!-- <li><a href="#tab_1_4" data-toggle="tab" id="meaasgeTab">消息设置</a></li> -->

							<!-- 	<li><a href="#tab_1_6" data-toggle="tab">Help</a></li> -->

							</ul>

							<div class="tab-content">
 
								<div class="tab-pane profile-classic row-fluid active" id="tab_1_2">

									<div class="span2"><img src="${path }/sys/tenant/user/photo/${headPhoto}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" alt=""  class="headPhoto" style="width: 238px;"/></div>

									<ul class="unstyled span10">

										<li><span>真实姓名:</span> <c:out value="${user.realname }"></c:out></li>

										<li><span>生日:</span> <c:out value="${user.birthday }"></c:out></li>

										<li><span>职称:</span> <c:out value="${user.positional }"></c:out></li>

										<li><span>邮箱:</span> <a href="#"><c:out value="${user.email }"></c:out></a></li>

										<li><span>特长:</span> <c:out value="${user.specialty }"></c:out></li>

										<li><span>个人主页:</span> <a href="<c:out value="${user.website }"></c:out>"><c:out value="${user.website }"></c:out></a></li>

										<li><span>电话:</span> <c:out value="${user.tel }"></c:out></li>

										<li><span>个人简介:</span> <c:out value="${user.about }"></c:out></li>

									</ul>

								</div>

								<!--tab_1_2-->

								<div class="tab-pane row-fluid profile-account" id="tab_1_3">

									<div class="row-fluid">

										<div class="span12">

											<div class="span2">

												<ul class="ver-inline-menu tabbable margin-bottom-10">

													<li class="active">

														<a data-toggle="tab" href="#tab_1-1">

														<i class="icon-cog"></i> 

														基本信息

														</a> 

														<span class="after"></span>                                    

													</li>

													<li><a data-toggle="tab" href="#tab_2-2" ><i class="icon-picture"></i> 修改头像</a></li>

													<li class=""><a data-toggle="tab" href="#tab_3-3"><i class="icon-lock"></i> 修改密码</a></li>

													<!-- <li class=""><a data-toggle="tab" href="#tab_4-4"><i class="icon-eye-open"></i> Privacity Settings</a></li> -->

												</ul>

											</div>

											<div class="span9">

												<div class="tab-content">
													
													<div id="tab_1-1" class="tab-pane active">

														<div style="height: auto;" id="accordion1-1" class="accordion collapse">
															<div class="modal-body form form-horizontal">
															<form action="${path }/sys/tenant/user/update" id="infoform" name="infoform"  class="form-horizontal">
																<input id="id" name="id" value="<c:out value="${user.id }"></c:out>" type="hidden">
																<div id="realnameGroup" class="control-group">
																	<label class="control-label">真实姓名<span class="required">*</span></label>
																	<div class="controls">
																	<input type="text" class="m-wrap span8" id="realname" name="realname" required value='<c:out value="${user.realname }"></c:out>'/>
																	</div>
																</div>
																<div id="emailGroup" class="control-group">
																	<input type="hidden" id="oldEmail" name="oldEmail" value="${user.email }">
																	<label class="control-label">邮箱<span class="required">*</span></label>
																	<div class="controls">
																	<input type="text" class="m-wrap span8"  id="email" name="email" required customEmail="true" uniqueEmail="true" value='<c:out value="${user.email }"></c:out>'/>
																	</div>
																</div>
																<div id="telGroup" class="control-group">
																
																	<label class="control-label">手机号码<span class="required">*</span></label>
																	<div class="controls">
																	<input type="text" class="m-wrap span8"  id="tel" name="tel" required value='<c:out value="${user.tel }"></c:out>'/>
																	</div>
																</div>
																
																<div id="positionalGroup" class="control-group">
																	<label class="control-label">单位职务<span class="required">*</span></label>
																	<div class="controls">
																	<input type="text" class="m-wrap span8" id="positional" name="positional" required value='<c:out value="${user.positional }"></c:out>'/>
																	</div>
																</div>
									<div class="form-actions">
										<a href="javaScript:;" class="btn green" id="infoSubmit"><i class='icon-ok'></i> 确定</a>
							            </a>
									</div>
															</form>
															</div>
														</div>

													</div>
													
													<div id="tab_2-2" class="tab-pane">

														<div style="height: auto;" id="accordion2-2" class="accordion collapse">

															<form action="#">
																<div class="controls">

																	<div class="thumbnail" style="width: 150px; height: 150px;">

																		<img src="${path }/sys/tenant/user/photo/${headPhoto}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" alt="" class="headPhoto">

																	</div>

																</div>

																<div class="space10"></div>

																<div class="submit-btn">

																	<a href="javaScript:;" id="upload" class="btn green">上传头像</a>
																	<!-- <a href="#" class="btn">Cancel</a> -->

																</div>

															</form>

														</div>

													</div>

													<div id="tab_3-3" class="tab-pane">

														<div style="height: auto;" id="accordion3-3" class="accordion collapse">
															<div class="modal-body form form-horizontal">
															<form action="${path}/sys/tenant/user/changePwd"  id="changePwdForm" name="changePwdForm" method="post" class="form-horizontal">
															
															<div id="oldPwdGroup" class="control-group">

																<label class="control-label">旧密码<span class="required">*</span></label>
						
																<div class="controls">
						
																	<input type="password" id="oldPwd" name="oldPwd" class="span6 m-wrap" required maxlength="16" minlength="6" placeholder="输入旧密码">

																</div>

														   </div>
																<div id="passwordGroup" class="control-group">
																	<label class="control-label">新密码<span class="required">*</span></label>

																	<div class="controls">
																		<input class="m-wrap span6" type="password" name="password" id="password" class="password" minlength="6" maxlength="16" pwdStrengh="true" placeholder="密码">
																	</div>
																</div>
																<div id="rePwdGroup" class="control-group">
																	<label class="control-label">确认新密码<span class="required">*</span></label>
																	<div class="controls">
																		<input class="m-wrap span6" type="password" name="rePwd" id="rePwd" class="password" required equals="password" placeholder="确认密码">
																	</div>
																</div>
																<div class="submit-btn form-actions">

																	<button id="changePwdSubmitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>

																	<!-- <a href="#" class="btn">Cancel</a> -->

																</div>
															</form>
															</div>
														</div>

													</div>

												</div>

											</div>

											<!--end span9-->                                   

										</div>

									</div>

								</div>

							</div>

						</div>

						<!--END TABS-->

					</div>

				</div>
				
					
					<div id="responsive" class="modal hide fade" data-width="650">

							<div class="modal-header">
								<h3 id="modalTitle">修改头像</h3>
							</div>

							<div class="modal-body form">

								<!-- BEGIN FORM-->

								<form action="${path }/sys/tenant/user/update/photo" class="form-horizontal" id="photoForm" name="photoForm" method="post">
									<div class="container" style="width: 640px;">
							                  <div class="imageBox">
							                    <div class="thumbBox"></div>
							                    <div class="spinner" style="display: none">Loading...</div>
							                  </div>
							                  <div class="action"> 
							                    <!-- <input type="file" id="file" style=" width: 200px">-->
							                    <div class="new-contentarea tc"> <a href="javascript:void(0)" class="upload-img">
							                      <label for="upload-file">修改头像</label>
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
							        
								</form>

								<!-- END FORM-->       

							</div>
				        <div class="form-actions">
							<button id="submitBtn" type="submit" class="btn green mybutton"><i class='icon-ok'></i> 保存</button>
							<a href="javascript:;" class="btn btn-default"
								data-dismiss="modal"><i class='icon-remove-sign'></i> 关闭
				           	</a>
						</div>

				</div>
			</div>
	
	<script src="<%=path%>/js/app.js"></script>      
	<script type="text/javascript" src="${path }/js/cropbox.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/common/custom-validate.js"></script>
	<script type="text/javascript" src="<%=path%>/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="<%=path%>/js/common/validate-message.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="<%=path%>/js/jquery.blockui.min.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=path%>/js/jquery.toggle.buttons.js"></script>
	<script type="text/javascript" src="<%=path%>/js/select2.js"></script>
	<script>
	jQuery.validator.addMethod("uniqueEmail", function(value, element) {
		if(value==null || value==''){
			return this.optional(element) || true;//空不验证
		}
		var bool;
		$.ajax({
			url : "${path }/sys/tenant/user/checkEmail?email=" + value,
			type : "GET",
			async : false,
			dataType : "json",
			success : function(json){
				if(document.getElementById('oldEmail').value === value){
					bool = true;
				}else{
					bool=json;
				}
			}
		});
	    return this.optional(element) || bool;
	}, "该邮箱已被使用");

	var myVld = new EstValidate2("changePwdForm");

		var add = $("#add");
		var modal = $("#modal");
		//var changePwdForm = new EstValidate("changePwdForm");
		var infoform = new EstValidate("infoform");
		var bool = true;
		jQuery(document).ready(function() {
			$("#changePwdSubmitBtn").on("click",function(){
				var bool = myVld.form();
				if(bool){
					$.ajax({
						url : "${path}/sys/tenant/user/checkOldPwd",
						type : "get",
						data : {"oldPwd":$("#oldPwd").val()},
						dataType : "json",
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(json){
							if(json.success == false){
								showTip("提示",json.message,2000);
							}else{
								$("#changePwdForm").ajaxSubmit({
									beforeSend : function() {
										$("#changePwdSubmitBtn").html("保存中...");
										$("#changePwdSubmitBtn").attr("disabled", "disabled");
									},
									headers: {
										"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
									},
									success : function(data) {
										if(data.success = true){
											showTip("提示","修改成功!",2000);
											document.getElementById("changePwdForm").reset();
											$(".control-group").removeClass("error").removeClass("success");
											$(".help-inline").remove();
											$("#pwdstrength").hide();
											$("#changePwdSubmitBtn").html("确定");
											$("#changePwdSubmitBtn").removeAttr("disabled");
										}else{
											showTip("提示", data.message, 2000);
										}
									}

								});
							}
						}
					});
				}
			});
			$("#submitBtn").on("click",function(){
				$("#photoForm").ajaxSubmit({
							dataType : "json",
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
							success : function(data) {
								if (data.success == "true" || data.success == true) {
									$(".headPhoto").attr("src","${path }/sys/tenant/user/photo/"+data.photoName+"?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}");
									$("#topheadPhoto").attr("src","${path }/sys/tenant/user/photo/"+data.photoName+"?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}");
									showTip("提示", "修改成功！");
									setTimeout("$('#responsive').modal('hide');",1000);
								}else{
									showTip("提示", data.message, null);
								}
								
							}

						});
			});

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
					//$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
					//$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
					$('.cropped').append('<img src="'+img+'" style="width:150px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 6px #7E7E7E;"><p>150px*150px</p>');
				})
				$('#btnZoomIn').on('click', function(){
					cropper.zoomIn();
				})
				$('#btnZoomOut').on('click', function(){
					cropper.zoomOut();
				})
		   App.init();
			$("#responsive").on('hide.bs.modal', function () {
			});
		   $("#meaasgeTab").on("click",function(){
			   if(bool){
			        $('.success-toggle-button').toggleButtons({
			        	onChange: function ($el, status, e) {
			        		var name = $el.context.name;
			        		//var status = !status;
			        		  $.ajax({
			      				url : "${path }/sys/message/user/conf/update",
			      				type : "POST",
			      				data : {"messageId":$("#messageId").val(),"name":$el.context.name,"status":status},
			      				dataType : "json",
			      				success : function(json){
			      					
			      				}
			      			});
			        	 },
			        	 label: {
			        		    enabled: "开",
			        		    disabled: "关"
			        		  },
			            style: {
			            	// Accepted values ["primary", "danger", "info", "success", "warning"] or nothing
			                enabled: "success",
			                disabled: "info"
			            }
			        });
			        bool=false;
			   }
		   });
	        
		   $("#upload").on("click",function(){
			   $('#responsive').modal('show');
		   });


			$("#close").on("click",function(e){
				 modal.hide();
				 add.hide();
			});
			
			$("#infoSubmit").on("click",function(){
				var bool = infoform.form();
				if(bool){
					$.ajax({
						url : "${path }/sys/tenant/user/update",
						type : "POST",
						data : {"realname":document.getElementById('realname').value,
							"id":document.getElementById('id').value,
							"email":document.getElementById('email').value,
							"tel":document.getElementById('tel').value,
							"positional":document.getElementById('positional').value
							},
						dataType : "json",
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(json){
							if(json.success == false){
								showTip("提示",json.message,2000);
							}else{
								showTip("提示","修改成功!",2000);
								setTimeout(function(){
									window.location.reload();
								},2000);
							}
						}
					});
				}
				//document.infoform.submit();	
			});
			 $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd', pickerPosition: "bottom-left",
				    startView: 2,
				    minView : 1,
				    language: 'zh-CN',
				    minView: 'month',
				    autoclose : true});
		});
	</script>
			<script src="${path }/js/validate.js" type="text/javascript" ></script>

</body>
</html>