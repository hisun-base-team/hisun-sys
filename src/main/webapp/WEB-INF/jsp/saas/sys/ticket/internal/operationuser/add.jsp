<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>  
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<link href="${path }/css/select2/select2-4.0.0.css" rel="stylesheet" type="text/css"/>
<title>添加运维人员</title>
</head>
<body>
			<div class="container-fluid">

				<div class="row-fluid">
					<div class="span12">
						<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>   

						<div class="portlet box grey">

							<div class="portlet-title">

								<div class="caption">

									<span class="hidden-480">添加运维人员</span>

								</div>
								<div class="btn-group fr">

										<button type="button" class="btn green" style="margin-right: 5px;" onclick="saveSubmit()"><i class="icon-ok"></i> 确定</button>

										<a class="btn" href="${path }${modualBasePath}/list"><i class="icon-remove-sign"></i> 取消</a>

									</div>
							</div>

							<div class="portlet-body form">
								<!-- BEGIN FORM-->

								<form action="${path }/management/est/ticketType/save" class="form-horizontal" id="form1" method="post">
									<div id="userIdGroup" class="control-group">
										<label class="control-label">账号选择<span class="required">*</span></label>
										<div class="controls">
												<select class="js-data-example-ajax span6" id="userId" name="userId" required="true">
												  
												</select>
										</div>
									</div>
									<div id="positionGroup" class="control-group">
										<label class="control-label">职务</label>
										<div class="controls">
											<input class="span6 m-wrap" id="position" name="position" maxlength="255">
										</div>

									</div>
									<div id="experienceGroup" class="control-group">
										<label class="control-label">工作经验</label>
										<div class="controls">
											<input class="span6 m-wrap" id="experience" name="experience" maxlength="255">
										</div>

									</div>
									<div id="skillGroup" class="control-group">
										<label class="control-label">技能标签</label>
										<div class="controls">
											<textarea class="span6 m-wrap" rows="3" id="skill" name="skill" maxlength="500"></textarea>
										</div>

									</div>
									<div id="certificateGroup" class="control-group">
										<label class="control-label">资格证书</label>
										<div class="controls">
											<textarea class="span6 m-wrap" rows="3" id="certificate" name="certificate" maxlength="500"></textarea>
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
<script type="text/javascript" src="${path }/js/select2/select2.full.js"></script>
<script type="text/javascript" src="${path }/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript">

	function select2Format(state){
		if (!state.id){
    		return state.realname; // optgroup
    	} 
        return $("<span><img class='img-flag' src='" + state.headImgUrl + "' width='50' height='50'/>" + state.realname+"</span>");
	}
	
	(function(){
		App.init();
		$("#userId").select2({
			language: "select2/i18n/zh",
		    minimumInputLength: 2,
		    templateResult: select2Format,
		    templateSelection : function(state){
		        return state.realname;
			},
		    text : function(item){
		    	return item.realname; 
		    },
		    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
		    	url: "${path}${modualBasePath}/seluser",
		        dataType: 'json',
		        delay: 500,
		        type : "post",
		        data: function (params) {
		          return {
		            name: params.term, // search term
		            pageNum : params.page
		          };
		        },
		        processResults: function (data, params) {
		          params.page = params.page || 1;
		          return {
		            results: data.userList,
		            pagination: {
		              more: data.more
		            }
		          };
		        },
		        cache: true
		    }
		});
	})();

	var myVld = new EstValidate2("form1");
	function saveSubmit(){
		var bool = myVld.form();
		if(bool){
			$.ajax({
				url : "${path }${modualBasePath}/save",
				type : "post",
				data : $('#form1').serialize(),
				dataType : "json",
				success : function(json){
					if(json.code == 1){
						showTip("提示","操作成功",2000);
						setTimeout(function(){window.location.href = "${path}${modualBasePath}/list"},2000) ;
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
