<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>  
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<link href="${path }/css/select2/select2-4.0.0.css" rel="stylesheet" type="text/css"/>
<link href="${path }/css/bootstrap-fileupload.css" rel="stylesheet" type="text/css"/>
<title>新建工单</title>
</head>
<body >
			<div class="container-fluid">
				<div class="row-fluid">
					<a id="sample_editable_1_new" class="btn yellow fr" href="${path }${modualBasePath}/myticket" style="margin: 0 10px 10px 0;">
						<i class="icon-arrow-left"></i> 返回我的工单概览 
					</a>
				</div>	
				<div class="row-fluid">
					<form id="form1" method="post" enctype="multipart/form-data">
		            	<div class="span6 wid330" style="min-height: 700px;">
		                	<div class="portlet box">
		                		<div class="control-group">
		                            <label class="control-label bold">提交人</label>
		                            <div class="controls">
		                            	<input size="16" type="text" value="<c:out value="${details.realname }"></c:out>" readonly="" class="m-wrap span12">
		                            </div>
		                        </div>
		                        <div class="control-group">
	                                <label class="control-label bold">联系电话</label>
	                                <div class="controls">
	                                    <input type="text" class="m-wrap span12" value="<c:out value="${details.tel }"></c:out>" readonly="readonly">
	                                </div>
	                            </div>
		                        <div class="control-group">
		                            <label class="control-label bold">所属单位</label>
		                            <div class="controls">
		                            	<input size="16" type="text" value="<c:out value="${details.tenantName }"></c:out>" readonly="" class="m-wrap span12">
		                            </div>
		                        </div>
		                        <div class="control-group" id="userArea">
		                            <label class="control-label bold">指定受理人</label>
		                            <div class="controls" class="m-wrap span12">
		                                 <select class="m-wrap span12" id="handleUserId" name="handleUserId" >
											<c:forEach items="${operationUserList }" var="operationUser">
												<option value="${operationUser.user.id }" ><c:out value="${operationUser.user.existName }"></c:out></option>
											</c:forEach>
										</select>
		                            </div>
		                        </div>
		                        <button type="button"  class="btn red big btn-block mt20 " onclick="saveSubmit()">提交</button>
		                    </div>
		                </div>
		                
		                <div class="span6 wid_80" style="min-height: 700px;">
		                	<div class="portlet box">                           
		                            <div class="Text_region">
		                            		<input type="hidden" name="ticketId" value="${entity.id }">
		                            		<label class="control-label bold">问题标题<span style="color:red;">*</span></label>
		                            		<input type="text" style="width: 99%" name="title" id="title" maxlength="30">
		                            		<label class="control-label bold">问题描述</label>
		                            		<textarea class="text_Adaptive" name="description" id="description">${description }</textarea>
			                            	<!-- <input type="file" class="default" name="attachFile" id="attachFile"> -->
			                            	<label class="control-label bold">附件(最大10M)</label>
			                            	<div class="fileupload fileupload-new" data-provides="fileupload">
												<div class="input-append">
													<div class="uneditable-input  border_radius heig20">
														<i class="icon-file fileupload-exists"></i> 
														<span class="fileupload-preview"></span>
													</div>
													<span class="btn btn-file border_radius" style="width: 60px;height: 20px;">
													<span class="fileupload-new">选择文件</span>
													<span class="fileupload-exists">重新选择</span>
													<input type="file" class="default" name="attachFile" id="attachFile"/>
													</span>
													<a href="#" class="btn fileupload-exists border_radius" data-dismiss="fileupload">移除</a>
												</div>
											</div>
		                                
		                            </div>                 
		                    </div>
		                </div>
	                </form>
            	</div>
			</div>
		</div>
	</div>

<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="text/javascript" src="${path }/js/bootstrap-fileupload.js"></script>
<script type="text/javascript">
var myLoading = new MyLoading("${path}",20000);

(function(){
	App.init();
})();

	

	//保存提交
	function saveSubmit(){
		if($.trim($('#title').val())==""){
			showTip("提示","请填写标题",2000);
			return;
		}
		var fileObj = document.getElementById('attachFile');
		if(fileObj.files.length>0){
			var byteSize  = fileObj.files[0].size;
		 	var size = Math.ceil(byteSize / 1024/1024); // Size in MB.
			var bool = false;
			if(size > 10){
				showTip("提示","附件超出大小限制",2000);
				return ;
			}
		}
		myLoading.show();
		$("#form1").ajaxSubmit({
			url : "${path }${modualBasePath}/save",
			type : "post",
			dataType : "json",
			enctype : "multipart/form-data",
			success : function(json){
				myLoading.hide();
				if(json.code == 1){
					showTip("提示","操作成功",2000);
					setTimeout(function(){window.location.href = "${path}${modualBasePath}/myticket";},2000) ;
				}else{
					showTip("提示", json.message, 2000);	
				}
			},
			error : function(arg1, arg2, arg3){
				myLoading.hide();
				showTip("提示","出错了请联系管理员");
			}
		});
	}
</script>
</body>

</html>
