<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加公告</title>
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

							<span class="hidden-480">添加公告</span>

						</div>
					</div>

					<div class="portlet-body form">
						<!-- BEGIN FORM-->

								<form action="${path }/sys/admin/notice/save" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="id" name="id" value=""/>
									<div id="noticeTitleGroup" class="control-group">

										<label class="control-label">公告标题<span class="required">*</span></label>

										<div class="controls">

											<input type="text" id="noticeTitle" name="noticeTitle" class="span6 m-wrap" required maxlength="255">

											<!-- <span class="help-inline">Some hint here</span> -->

										</div>

									</div>

									<div class="control-group" id="noticeContentGroup">

										<label class="control-label">公告内容<span class="required">*</span></label>

										<div class="controls" style="border: 1px solid #ddd;width: 725px;border-top:none;">
											<input id="noticeContent" name="noticeContent" type="hidden"/>
											
											<script id="container" name="content" type="text/plain"></script>
										</div>

									</div>
									<div class="control-group" id="pushWayGroup">

										<label class="control-label">推送方式<span class="required">*</span></label>

										<div class="controls">
											
											<!-- <input type="text" id="pushWay" name="pushWay" class="span6 m-wrap" required maxlength="255"> -->
											<select class="span6 m-wrap" id="pushWay" name="pushWay" tabindex="1">
												<option value="0">短信</option>
												<option value="1">邮件</option>
												<option value="2" selected = "selected">在线</option>
											</select>
										</div>

									</div>

									<div id="statusGroup" class="control-group">

										<label class="control-label">是否显示</label>

										<div class="controls">

											<select class="span6 m-wrap" id="status" name="status" tabindex="1">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>

										</div>

									</div>
									<div class="form-actions">
										<button id="submitbut" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<a href="<%=path%>/sys/admin/notice/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
							               data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
							            </a>
									</div>
								</form>

								<!-- END FORM-->       
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    window.PATH = "${path}";
    </script>
	<!-- 配置文件,可以根据功能点有自己的文件 -->
    <script type="text/javascript" src="${path }/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 ,目前不能修改-->
    <script type="text/javascript" src="${path }/ueditor/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('container');
    </script>
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
					$("#noticeContent").val(ue.getContent());
					$.ajax({
						url : "${path}/sys/admin/notice/save",
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
								showTip("提示","新增公告成功!",2000);
								setTimeout("window.location.href='${path}/sys/admin/notice/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);
							}else{
								document.getElementById("addForm").reset();
								$(".control-group").removeClass("error").removeClass("success");
								$(".help-inline").remove();
								showTip("警告","新增公告失败!",2000);
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