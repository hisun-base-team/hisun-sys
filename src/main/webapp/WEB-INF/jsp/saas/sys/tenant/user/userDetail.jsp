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

								<li class="active"><a href="#tab_1_2" data-toggle="tab">个人信息详情</a></li>

								<!-- <li><a href="#tab_1_4" data-toggle="tab" id="meaasgeTab">消息设置</a></li> -->

							<!-- 	<li><a href="#tab_1_6" data-toggle="tab">Help</a></li> -->

							</ul>

							<div class="tab-content">
 
								<div class="tab-pane profile-classic row-fluid active" id="tab_1_2">

									<div class="span2"><img src="${path }/sys/tenant/user/photo/${headPhoto}" alt=""  class="headPhoto" style="width: 238px;"/></div>

									<ul class="unstyled span10">

										<li><span>真实姓名:</span> <c:out value="${user.realname }"></c:out></li>
										<!-- <li><span>First Name:</span> John</li>
										<li><span>Last Name:</span> Doe</li>
										<li><span>Counrty:</span> Spain</li> -->

										<li><span>生日:</span> <c:out value="${user.birthday }"></c:out></li>

										<li><span>职称:</span> <c:out value="${user.positional }"></c:out></li>

										<li><span>邮箱:</span> <a href="#"><c:out value="${user.email }"></c:out></a></li>

										<li><span>特长:</span> <c:out value="${user.specialty }"></c:out></li>

										<li><span>个人主页:</span> <a href="<c:out value="${user.website }"></c:out>"><c:out value="${user.website }"></c:out></a></li>

										<li><span>电话:</span> <c:out value="${user.tel }"></c:out></li>

										<li><span>个人简介:</span> <c:out value="${user.about }"></c:out></li>

									</ul>

								</div>
                                <c:if test="${!empty error}">
                                <div class="alert alert-danger" id="error">
                                    <i class="icon-remove-sign"></i>
										<span class="ng-isolate-scope">
											<span class="ng-binding"><c:out value="${error}"></c:out></span>
										</span>
                                </div>
                                </c:if>

                                <div class="form-actions" style="padding-left: 250px">
                                    <a href="${path}/sys/tenant/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default" data-dismiss="modal"><i class="m-icon-swapleft"></i> 返回
                                    </a>
                                </div>
								<!--tab_1_2-->

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
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
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

	var FormValidation = function() {


		return {
			//main function to initiate the module
			init : function() {

				var form = $('#changePwdForm');

				form.validate({
					doNotHideMessage : true, //this option enables to show the error/success messages on tab switch.
					errorElement : 'span', //default input error message container
					errorClass : 'validate-inline', // default input error message class
					focusInvalid : false, // do not focus the last invalid input
					rules : {
						oldPwd : {
							required : true,
                            remote : "<%=path%>/sys/tenant/user/checkOldPwd"
						},
						pwd : {
							required : true
						},
						pwd2 : {
							required : true,
							equalTo : "#pwd"
						}
					},

					messages : {
						oldPwd : {
							required : "旧密码不能为空",
                            remote : "旧密码不正确,请重新输入"
						},
						pwd : {
							required : "新密码不能为空"
						},
						pwd2 : {
							required : "确认新密码不能为空",
							equalTo : "两次填写的新密码不一致"
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


						$.ajax({
							url : "${path}/sys/tenant/user/checkOldPwd",
							type : "get",
							data : {"oldPwd":$("#oldPwd").val()},
							dataType : "json",
							headers: {
								"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
							},
							success : function(json){
								if(!json){
									showTip("提示","输入的旧密码不正确!",2000);
								}else{
									$(form).ajaxSubmit({
										beforeSend : function() {
											$("#changePwdSubmitBtn").html("保存中...");
											$("#changePwdSubmitBtn").attr("disabled", "disabled");
										},
										headers: {
											"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
										},
										success : function(data) {
											if(data){
												showTip("提示","修改成功!",2000);
												document.getElementById("changePwdForm").reset();
												$(".control-group").removeClass("error").removeClass("success");
												$(".help-inline").remove();
												$("#pwdstrength").hide();
												$("#changePwdSubmitBtn").html("确定");
												$("#changePwdSubmitBtn").removeAttr("disabled");
											}
										}

									});
								}
							}
						});



					}

				});
			}

		};

	}();
		var add = $("#add");
		var modal = $("#modal");
		//var changePwdForm = new EstValidate("changePwdForm");
		var infoform = new EstValidate("infoform");
		var bool = true;
		jQuery(document).ready(function() {
			FormValidation.init();
			$("#submitBtn").on("click",function(){
				$("#photoForm").ajaxSubmit({
					type : "json",
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
							success : function(data) {
								if (data.success == "true" || data.success == true) {
									$(".headPhoto").attr("src","${path }/sys/tenant/user/photo/"+data.photoName);
									$("#topheadPhoto").attr("src","${path }/sys/tenant/user/photo/"+data.photoName);
									showTip("提示", "修改成功！");
									setTimeout("$('#responsive').modal('hide');",1000);
								}else{
									showTip("警告", "修改失败！");
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
					this.files = [];
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
								  headers: {
									  "OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
								  },
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
							//"birthday":document.getElementById('birthday').value,
							"email":document.getElementById('email').value,
							"tel":document.getElementById('tel').value,
							//"specialty":document.getElementById('specialty').value,
							"positional":document.getElementById('positional').value
							//,
							//"website":document.getElementById('website').value,
							//"about":document.getElementById('about').value
							},
						dataType : "json",
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(json){
							if(!json){
								showTip("提示","修改失败!",2000);
							}else{
								//showTip("提示","修改成功!",2000);
								window.location.reload();
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