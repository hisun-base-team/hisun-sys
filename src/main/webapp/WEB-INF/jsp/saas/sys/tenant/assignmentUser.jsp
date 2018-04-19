<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/select2_metro.css" />
<title>分派人员</title>
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

							<span class="hidden-480">分派人员</span>

						</div>
					</div>

					<div class="portlet-body form">
						<form action="${path }/sys/tenant/assignment" class="form-horizontal myform"
						id="submit_form" method="post">
							<div class="tab-pane active" id="tab1">
								<div>
	
									<div class="control-group">
									
										<label class="control-label mylabel">分派人员
										</label>
			
										<div class="controls">
											<input type="hidden" id="tenantid" name="tenantid" class="myinput"/>
											<form:select path="users" items="${users}" itemLabel="text" itemValue="id" id="select2_sample6" class="span6 select2" multiple="multiple" >
										    </form:select>
			
										</div>
			
									</div>
									<div class="form-actions">
										<button id="submitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<a href="<%=path%>/sys/tenant/manage" class="btn btn-default"
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
	<script type="text/javascript" src="<%=path%>/js/select2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#select2_sample6").select2({
				  placeholder: "分派人员",
				  allowClear: true,
				  theme: "classic"
				});
			var assignment = "${assignment}";
			if(assignment===""){
				$("#select2_sample6").select2("val","");
			}else{
				$("#select2_sample6").select2("val",assignment.split(","));
			}
			
			$('#submitBtn').click(function () {
				var datas = $("#select2_sample6").select2("val");
				$.ajax({
					url : "${path }/sys/tenant/assignment",
					type : "POST",
					data : {"tenantid":"${tenantId}",
						"assignment":datas},
					dataType : "json",
					success : function(json){
						if(!json){
							showTip("提示","分派人员失败!",2000);
						}else{
							showTip("提示","分派人员成功!",2000);
							//window.location.reload();
						}
					}
				});
				
			});
		});
	</script>
</body>
</html>