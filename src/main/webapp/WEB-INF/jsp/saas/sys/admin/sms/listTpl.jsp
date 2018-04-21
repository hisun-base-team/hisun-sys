<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- <%@taglib prefix="cloud" uri="/WEB-INF/tld/function.tld"%> --%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${path}/css/DT_bootstrap.css" />
<!-- END PAGE LEVEL STYLES -->
<title>短信模板配置</title>
</head>
<body>
			<div class="container-fluid">

				<div class="row-fluid">
					<div class="span12 responsive">
						<%-- 表格开始 --%>
						<div class="portlet box grey">
							<div class="portlet-title" style="vertical-align: middle;">
								<div class="caption">短信配置</div>
								<div class="clearfix fr">
									<shiro:hasPermission name="admin-platform:smstpl_add">
										<a id="sample_editable_1_new" class="btn green" href="javascript:;" onclick="addSMS();">
											<i class="icon-plus"></i> 添加
										</a>
									</shiro:hasPermission>
								</div>
							</div>
							<div class="portlet-body">
								<div class="dataTables_wrapper form-inline">
									<table class="table table-striped table-bordered table-hover dataTable table-set">
										<thead>
											<tr>
												<th width="200px">模板id</th>
												<th>模板内容</th>
												<th width="100px">审核状态</th>
												<th>未通过原因</th>
												<th width="200px">模板字符串</th>
												<th width="120px">操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${pager.datas }" var="config" >
												<tr style="height: 50px;">
													<td title=''><c:out value="${config.tplId }"></c:out></td>
													<td title='${cloud:htmlEscape(config.tplContent)}'><c:out value="${config.tplContent }"></c:out></td>
													<td title=''>
														<c:choose>
															<c:when test="${config.checkStatus == 'SUCCESS'}">
																<c:out value="审核成功"></c:out>
															</c:when>
															<c:when test="${config.checkStatus == 'FAIL'}">
																<c:out value="审核失败"></c:out>
															</c:when>
															<c:otherwise>
																<c:out value="未审核"></c:out>
															</c:otherwise>
														</c:choose>
													</td>
													<td title='${cloud:htmlEscape(config.reason)}'>
														<c:choose>
															<c:when test="${config.reason == null }">
																<c:out value="无"></c:out>
															</c:when>
															<c:otherwise>
																<c:out value="${config.reason }"></c:out>
															</c:otherwise>
														</c:choose>
													</td>
													<td><c:out value="${config.permission }"></c:out></td>
													<td class="Left_alignment">
														<shiro:hasPermission name="admin-platform:smstpl_update">
															<a href="javascript:;" class=""  onclick="update('${config.id}');">编辑</a>|
														</shiro:hasPermission>
														<shiro:hasPermission name="admin-platform:smstpl_delete">
															<a href="javascript:;" class="" id="${config.tplId}" tplId="${config.id }" itemname="${cloud:htmlEscape(config.tplContent)}" onclick="del(this);">删除</a>
														</shiro:hasPermission>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<jsp:include page="/WEB-INF/jsp/common/page.jsp">
										<jsp:param value="${pager.total }" name="total"/>
										<jsp:param value="${pager.pageCount }" name="endPage"/>
										<jsp:param value="${pager.pageNum }" name="page"/>
										<jsp:param value="${pager.pageSize }" name="pageSize"/>
									</jsp:include>
								</div>
							</div>
							<%-- 表格结束 --%>
						</div>
					</div>
				</div>
				
				<%-- END PAGE CONTENT--%>
				<div class="modal-scrollable" style="z-index: 10050;display: none;" id="add">
					
					<div id="responsive" class="modal hide fade in" tabindex="-1"
						style="display: block; width: 760px;top: 25%">

						<div class="span12">

						<!-- BEGIN SAMPLE FORM PORTLET-->   

							<div class="modal-header">
								<button class="close" id="close" type="button"></button>
								<h3 id="modalTitle">编辑模板</h3>
							</div>

							<div class="modal-body form">

								<!-- BEGIN FORM-->

								<form action="#" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="id" name="id" value=""/>
									<input type="hidden" id="tplId" name="tplId"/>
									<div class="control-group" id="permissionGroup">

										<label class="control-label">模板字符串<span class="required">*</span></label>

										<div class="controls">
											
											<input type="text" id="permission" name="permission" class="span6 m-wrap" required maxlength="255">

										</div>

									</div>
									<div id="tplContentGroup" class="control-group">

										<label class="control-label">模板内容<span class="required">*</span></label>

										<div class="controls">

											<textarea id="tplContent" name="tplContent" class="span6 m-wrap" required maxlength="255" rows="3" placeholder="您的验证码是#privilegeCode#"></textarea>
										</div>
										<span class="help-inline">变量格式：变量以两个半角的#号包围，变量名可以是英文(区分大小写)，数字或者下划线，如：#privilegeCode#,#n_1#</span>
									</div>
									
									<div class="control-group mybutton-group">
										<button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i> 关闭</button>      
										<button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i> 确定</button>                   
									</div>
									<!-- <div class="form-actions">
										<button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i>关闭</button>      
										<button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i>保存</button>                   

									</div> -->

								</form>

								<!-- END FORM-->       

							</div>

						<!-- END SAMPLE FORM PORTLET-->

					</div>

					</div>
				</div>
				<div class="modal-backdrop fade in" style="z-index: 10049;display: none;" id="modal"></div>
				<%-- END PAGE CONTENT--%>  
			</div>
	<script src="${path }/js/common/loading.js" type="text/javascript" ></script>
	<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript">
	var add = $("#add");
	var modal = $("#modal");
	var del = $("#del");
	var addForm = new EstValidate("addForm");
	(function(){
		App.init();
		
		$("#cancel").on("click",function(){
			document.getElementById("addForm").reset();
			$("#id").val("");
			$(".control-group").removeClass("error").removeClass("success");
			//$(".help-inline").remove();
			$("#modal").hide();
			$("#add").hide();
		});
		
		$("#close").on("click",function(e){
			e.preventDefault();
			document.getElementById("addForm").reset();
			$("#id").val("");
			$(".control-group").removeClass("error").removeClass("success");
			//$(".help-inline").remove();
			$("#modal").hide();
			$("#add").hide();
		});
		
		$("#submit").on("click",function(){
			var bool = addForm.form();
			if(bool){
				if($("#id").val()===""){
					$.ajax({
						url : "${path}/sys/admin/sms/tpl/add",
						type : "post",
						data : $("#addForm").serialize(),
						dataType : "json",
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(json){
							if(json.success){
								document.getElementById("addForm").reset();
								$("#id").val("");
								$(".control-group").removeClass("error").removeClass("success");
								//$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								showTip("提示","操作成功", 2000);
								setTimeout(function(){
									window.location.reload();
								});
							}else{
								document.getElementById("addForm").reset();
								$("#id").val("");
								$(".control-group").removeClass("error").removeClass("success");
								$("#modal").hide();
								$("#add").hide();
								showTip("提示",json.message,null);
							}
						},
						error : function(){
							document.getElementById("addForm").reset();
							$("#id").val("");
							$(".control-group").removeClass("error").removeClass("success");
							//$(".help-inline").remove();
							$("#modal").hide();
							$("#add").hide();
							$("#alertErr").show();
							setTimeout(function () {
						        $("#alertErr").hide();
						    }, 5000);
						}
					});
				}else{
					$.ajax({
						url : "${path}/sys/admin/sms/tpl/update",
						type : "post",
						data : $("#addForm").serialize(),
						dataType : "json",
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(json){
							if(json.success){
								document.getElementById("addForm").reset();
								$("#id").val("");
								$(".control-group").removeClass("error").removeClass("success");
								//$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								showTip("提示","操作成功", 2000);
								setTimeout(function(){
									window.location.reload();
								});
							}else{
								document.getElementById("addForm").reset();
								$("#id").val("");
								$(".control-group").removeClass("error").removeClass("success");
								//$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								showTip("提示",json.message,null);
							}
						},
						error : function(){
							document.getElementById("addForm").reset();
							$("#id").val("");
							$(".control-group").removeClass("error").removeClass("success");
							//$(".help-inline").remove();
							$("#modal").hide();
							$("#add").hide();
							$("#alertErr").show();
							setTimeout(function () {
						        $("#alertErr").hide();
						    }, 5000);
						}
					});
					
				}
				
			}
		});
	})();
	
	var addSMS = function(){
		modal.show();
		add.show();
		$('#modalTitle').text('添加模板');
	}
	
	var del = function(obj){
		var id = obj.id;
		var tlpid = $(obj).attr("tplId");
		var itemName = $(obj).attr("itemname");
		actionByConfirm1(itemName, "${path}/sys/admin/sms/tpl/delete/" + id+"/"+tlpid,{} ,function(data,status){
			if (status == "success") {
				if(data.success == true){
					location.reload();
				}else{
					showTip("提示", data.message, null);
				}
			}
		}); 
	}; 
	var update = function(id){
		$.ajax({
			url : "${path}/sys/admin/sms/tpl/get/"+id,
			type : "get",
			dataType : "json",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(json){
				if(json.success){
					$("#id").val(json.data.id);
					$("#tplId").val(json.data.tplId);
					$("#tplContent").val(json.data.tplContent);
					$("#permission").val(json.data.permission);
					$("#add").show();
					$("#modal").show();
					$('#modalTitle').text('编辑模板');
				}
			}
		});
	};
	
	function pagehref(pageNum, pageSize) {
		var url = location.href;
		var length = url.indexOf("?");
		length = length > 0 ? length : url.length;
		//alert(url.substring(0, length) + "?pageNum=" + pageNum  + "&pageSize=" + pageSize);
		location.href=url.substring(0, length) + "?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum=" + pageNum  + "&pageSize=" + pageSize;
	}
</script>
</body>
</html>
