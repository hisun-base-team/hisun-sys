<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cloud" uri="/WEB-INF/tld-management/management-function.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<title>修改运维人员信息</title>
</head>
<body>
	
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>   

						<div class="portlet box grey">

							<div class="portlet-title">

								<div class="caption">

									<span class="hidden-480">修改运维人员信息</span>

								</div>
								<div class="btn-group fr">
									<button type="button" class="btn green" style="margin-right: 5px;" onclick="updateSubmit()"><i class="icon-ok"></i> 确定</button>
									<a class="btn" href="${path }${modualBasePath}/list"><i class="icon-remove-sign"></i> 取消</a>
								</div>
							</div>

							<div class="portlet-body form">
								<!-- BEGIN FORM-->

								<form action="${path }${modualBasePath}/update/${entity.id}" class="form-horizontal" id="form1" method="post">
									<div id="userIdGroup" class="control-group">
										<label class="control-label">运维人员</label>
										<div class="controls">
											<input type="hidden" value="${entity.user.id }" name="userId">
											<input class="span6 m-wrap" type="text" value="<c:out value="${entity.user.existName }"></c:out>" readonly="readonly">
										</div>
									</div>
									<div id="positionGroup" class="control-group">
										<label class="control-label">职务</label>
										<div class="controls">
											<input class="span6 m-wrap" id="position" name="position" maxlength="255" value="<c:out value="${entity.position}"></c:out> "></textarea>
										</div>

									</div>
									<div id="experienceGroup" class="control-group">
										<label class="control-label">工作经验</label>
										<div class="controls">
											<input class="span6 m-wrap" id="experience" name="experience" maxlength="255" value="<c:out value="${entity.experience}"></c:out>"></textarea>
										</div>

									</div>
									<div id="skillGroup" class="control-group">
										<label class="control-label">技能标签</label>
										<div class="controls">
											<textarea class="span6 m-wrap" rows="3" id="skill" name="skill" maxlength="500">${entity.skill }</textarea>
										</div>

									</div>
									<div id="certificateGroup" class="control-group">
										<label class="control-label">资格证书</label>
										<div class="controls">
											<textarea class="span6 m-wrap" rows="3" id="certificate" name="certificate" maxlength="500">${entity.certificate }</textarea>
										</div>
									</div>
								</form>
							</div>

						</div>

						<%-- END SAMPLE FORM PORTLET--%>
					</div>
				</div>
				
				<%-- END PAGE CONTENT--%>  
			</div>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript">
	(function(){
		App.init();
	})();

	var myVld = new EstValidate("form1");
	function updateSubmit(){
		var bool = myVld.form();
		if(bool){
			$.ajax({
				url : "${path }${modualBasePath}/update/${entity.id}",
				type : "post",
				data : $('#form1').serialize(),
				dataType : "json",
				success : function(json){
					if(json.privilegeCode == 1){
						showTip("提示","操作成功",2000);
						setTimeout(function(){
							window.location.href = "${path}${modualBasePath}/list"
						},2000) ;
					}else{
						showTip("提示", json.message, 2000);	
					}
				},
				error : function(arg1, arg2, arg3){
					showTip("提示","出错了请联系管理员");
				}
			});
		}
	}
</script>
</body>
</html>
