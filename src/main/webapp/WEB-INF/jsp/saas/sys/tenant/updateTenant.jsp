<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改组织</title>
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

							<span class="hidden-480">编辑组织</span>

						</div>
						<div class="tools">
							<a href="javascript:location.reload();" class="reload"></a>
						</div>
					</div>

					<div class="portlet-body form">
						<form action="<%=path%>/sys/tenant/save" class="form-horizontal myform"
						id="submit_form" method="post">
							<div class="tab-pane active" id="tab1">
								<div>
									<div class="control-group">
	
										<label class="control-label mylabel">组织名<span
											class="required">*</span>
										</label>
	
										<div class="controls">
											<input type="hidden" id="id" name="id" value="${vo.id }"/>
											<input type="text" class="span6 m-wrap myinput required" name="tenantName" id="tenantName"
												placeholder="长度为1-30位" value='<c:out value="${vo.tenantName }"></c:out>' /> <span
												class="help-inline"></span>
	
										</div>
	
									</div>
									
									<div class="form-actions">
										<button id="submitBtn" type="submit" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<a href="<%=path%>/sys/tenant/list" class="btn btn-default"
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
	<script type="text/javascript">
	var FormValidation = function() {

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

				var form = $('#submit_form');

				form.validate({
					doNotHideMessage : true, //this option enables to show the error/success messages on tab switch.
					errorElement : 'span', //default input error message container
					errorClass : 'validate-inline', // default input error message class
					focusInvalid : false, // do not focus the last invalid input
					rules : {
						tenantName : {
							maxlength : 30,
							required : true
						},
						type : {
							required : true
						},
						property : {
							required : true
						}
						/* ,
						createUser : {
							required : true
						},
						createTimeStr : {
							required : true
						} */
					},

					messages : {
						orgName : {
							maxlength : "输入的名称必须为少于30位",
							required : "输入的名称不能为空"
						},
						type : {
							required : "请选择类别"
						},
						property : {
							required : "请选择性质"
						}
						/* ,
						createUser : {
							required : "输入的创建人不能为空"
						},
						createTimeStr : {
							required : "输入的创建时间不能为空"
						} */
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

						$(form).ajaxSubmit(
							{
								beforeSend : function() {
									$("#submitBtn").html("修改中...");
									$("#submitBtn").attr("disabled", "disabled");
								},
								success : function(data) {
									if (data == "true") {
										$("#submitBtn").html("修改成功");
										
										showTip("提示", "修改成功！");
										setTimeout("$('#myModal').modal('hide');",1000);
										setTimeout("window.location.href='${path}/sys/tenant/list'",1300);
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
		});
	</script>
</body>
</html>