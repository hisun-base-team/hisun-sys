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
<title>添加租户权限资源</title>
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
							<span class="hidden-480">添加租户权限资源</span>
						</div>
					</div>
					<div class="portlet-body form">
						<form class="form-horizontal " id="form1" method="post">
							<div id="nameGroup" class="control-group">
								<label class="control-label ">租户权限资源名称<span
										class="required">*</span>
								</label>
								<div class="controls">
									<input class="span6 m-wrap" type="text" name="name" id="name" required maxlength="15">
								</div>
							</div>
							<div id="codeGroup" class="control-group">
								<label class="control-label ">code<span
										class="required">*</span>
								</label>
								<div class="controls">
									<input class="span6 m-wrap" type="text" name="code" id="code" required maxlength="50" >
								</div>
							</div>
							<div id="impclassGroup" class="control-group">
								<label class="control-label ">实现类<span class="required">*</span></label>
								<div class="controls">
									<input class="span6 m-wrap" type="text" name="impclass" id="impclass" required minlength="4" maxlength="128">
								</div>
							</div>
							<div id="selectUrlGroup" class="control-group">
								<label class="control-label ">权限数据源URL<span
										class="required">*</span>
								</label>
								<div class="controls">
									<input class="span6 m-wrap" type="text" name="selectUrl" id="selectUrl" required minlength="4" maxlength="200">
								</div>
								<div class="controls">
									<i class="icon-exclamation-sign" style="padding-right: 5px;"></i>请填写数据源URL,例如:<em>/api/dictionary/tree?typeCode=GB/T16835-1997</em>
								</div>
							</div>
							<div id="displayTypeGroup" class="control-group">
								<label class="control-label ">权限资源类型<span
										class="required">*</span>
								</label>
								<div class="controls">
									<select class="m-wrap span6" name="displayType" id="displayType">
										<option value="1">树形多选</option>
										<option value="2">单层多选</option>
									</select>
								</div>
							</div>
							<div id="sortGroup" class="control-group">
								<label class="control-label ">排序<span
										class="required">*</span>
								</label>
								<div class="controls">
									<input type="text" class="span6 m-wrap" id="sort" name="sort" number="true"  required maxlength="3"  value="${maxSort}" />
								</div>
							</div>
							<div id="descriptionGroup" class="control-group">
								<label class="control-label ">备注
								</label>
								<div class="controls">
									<textarea class="span6 m-wrap" id="description" name="description" style="height:56px;"></textarea>
								</div>
							</div>
							<div  class="form-actions">
								<button id="submitBtn" type="button" class="btn green mybutton" onclick="saveSubmit()"><i class='icon-ok'></i> 确定</button>
								<a href="${path}/sys/tenant/privilege/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
								   data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
								</a>
							</div>
						</form>
					</div>
					</div>
			</div>
		</div>
	</div>
<script type="application/javascript">
	window.PATH = "${path}";
</script>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="text/javascript">
	var myVld = new EstValidate2("form1");
	var myLoading = new MyLoading("${path}",{zindex : 11111});
	function saveSubmit(){
		localPost("${path}/sys/tenant/privilege/code/check",{
			"code":$("#code").val(),
			"id":''//$("#id").val()
		},function(data) {
			if(!data.success){
				showTip("提示", "code必须唯一！");
			}else{
				var bool = myVld.form();
				if(bool){
					myLoading.show();
					$.ajax({
						url : "${path }/sys/tenant/privilege/save",
						type : "post",
						data : $('#form1').serialize(),
						dataType : "json",
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(json){
							myLoading.hide();
							if(json.code == 1){
								showTip("提示","操作成功",2000);
								setTimeout(function(){
									window.location.href = "${path}/sys/tenant/privilege/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"
								},2000) ;
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
		},"json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
	}
</script>
</body>
</html>