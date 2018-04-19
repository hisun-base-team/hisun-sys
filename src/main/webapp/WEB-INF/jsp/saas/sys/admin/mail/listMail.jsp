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
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
<!-- END PAGE LEVEL STYLES -->
<title>邮件配置</title>
</head>
<body>
			<div class="container-fluid">

				<div class="row-fluid">
					<div class="span12 responsive">
						<%-- 表格开始 --%>
						<div class="portlet box grey">
							<div class="portlet-title" style="vertical-align: middle;">
								<div class="caption">邮件配置</div>
								<div class="clearfix fr">
									<shiro:hasPermission name="admin-platform:mail_add">
										<a id="sample_editable_1_new" class="btn green" href="${path}/sys/admin/mail/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
											<i class="icon-plus"></i> 添加
										</a>
									</shiro:hasPermission>
								</div>
							</div>
							<div class="portlet-body">
								<%-- 按钮操作结束 --%>
								<div class="dataTables_wrapper form-inline">
								    <%-- 查找框 --%>
									<table class="table table-striped table-bordered table-hover dataTable table-set">
										<thead>
											<tr>
												<th>名字</th>
												<th>邮件服务地址</th>
												<th>版本号</th>
												<th>发送uri</th>
												<th>类型</th>
												<th>是否启用</th>
												<th width="120px">操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${pager.datas }" var="config" >
												<tr style="height: 50px;">
													<td title='<c:out value="${config.name }"></c:out>'><c:out value="${config.name }"></c:out></td>
													<td title='<c:out value="${config.mailServer }"></c:out>'><c:out value="${config.mailServer }"></c:out></td>
													<td title='<c:out value="${config.version }"></c:out>'><c:out value="${config.version }"></c:out></td>
													<td title='<c:out value="${config.send }"></c:out>'><c:out value="${config.send }"></c:out></td>
													<td title='<c:out value="${config.type }"></c:out>'>
														<c:if test="${config.type }">
															批量
														</c:if>
														<c:if test="${!config.type }">
															触发
														</c:if>
													</td>
													<td>
														<c:choose>
															<c:when test="${config.status}">
																<c:out value="是"></c:out>
															</c:when>
															<c:otherwise>
																<c:out value="否"></c:out>
															</c:otherwise>
														</c:choose>
													</td>
													<td class="Left_alignment">
														<shiro:hasPermission name="admin-platform:mail_update">
															<a href="<%=path%>/sys/admin/mail/update/${config.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">编辑</a>|
														</shiro:hasPermission>
														<shiro:hasPermission name="admin-platform:mail_test">
															<a href="<%=path%>/sys/admin/mail/test/${config.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="" >测试</a>|
														</shiro:hasPermission>
														<shiro:hasPermission name="admin-platform:mail_delete">
															<a href="javascript:;" class="" id="${config.id}" itemname="<c:out value="${config.name }"></c:out>" onclick="del(this);">删除</a>
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
			$(".help-inline").remove();
			$("#modal").hide();
			$("#add").hide();
		});
		
		$("#close").on("click",function(e){
			e.preventDefault();
			document.getElementById("addForm").reset();
			$("#id").val("");
			$(".control-group").removeClass("error").removeClass("success");
			$(".help-inline").remove();
			$("#modal").hide();
			$("#add").hide();
		});
		
		$("#submit").on("click",function(){
			var bool = addForm.form();
			if(bool){
				if($("#id").val()===""){
					$.ajax({
						url : "${path}/sys/admin/mail/add",
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
								$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								window.location.reload();
							}else{
								document.getElementById("addForm").reset();
								$("#id").val("");
								$(".control-group").removeClass("error").removeClass("success");
								$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								$("#alertErr").show();
								setTimeout(function () {
							        $("#alertErr").hide();
							    }, 5000);
							}
						},
						error : function(){
							document.getElementById("addForm").reset();
							$("#id").val("");
							$(".control-group").removeClass("error").removeClass("success");
							$(".help-inline").remove();
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
						url : "${path}/sys/admin/mail/update",
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
								$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								window.location.reload();
							}else{
								document.getElementById("addForm").reset();
								$("#id").val("");
								$(".control-group").removeClass("error").removeClass("success");
								$(".help-inline").remove();
								$("#modal").hide();
								$("#add").hide();
								$("#alertErr").show();
								setTimeout(function () {
							        $("#alertErr").hide();
							    }, 5000);
							}
						},
						error : function(){
							document.getElementById("addForm").reset();
							$("#id").val("");
							$(".control-group").removeClass("error").removeClass("success");
							$(".help-inline").remove();
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
		$('#modalTitle').text('添加配置');
	}
	
	var del = function(obj){
		var id = obj.id;
		var itemName = $(obj).attr("itemname");
		actionByConfirm1(itemName, "${path}/sys/admin/mail/delete/" + id,{} ,function(data,status){
			if (status == "success") {
				location.reload();
			}
		}); 
	}; 
	var update = function(id){
		$.ajax({
			url : "${path}/sys/admin/mail/get/"+id,
			type : "get",
			dataType : "json",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(json){
				if(json.success){
					$("#id").val(json.data.id);
					$("#name").val(json.data.name);
					$("#account").val(json.data.account);
					$("#password").val(json.data.password);
					$("#email").val(json.data.email);
					$("#emailServer").val(json.data.emailServer);
					$("#mailServer").val(json.data.mailServer);
					$("#version").val(json.data.version);
					$("#apiUser").val(json.data.apiUser);
					$("#apiKey").val(json.data.apiKey);
					$("#send").val(json.data.send);
					$("#sendTemplate").val(json.data.sendTemplate);
					$("#addTemplate").val(json.data.addTemplate);
					$("#deleteTemplate").val(json.data.deleteTemplate);
					$("#updateTemplate").val(json.data.updateTemplate);
					$("#getTemplate").val(json.data.getTemplate);
					if(json.data.status) {
						$("#status").val(1);
					}else{
						$("#status").val(0);
					}
					$("#add").show();
					$("#modal").show();
					$('#modalTitle').text('编辑配置');
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
