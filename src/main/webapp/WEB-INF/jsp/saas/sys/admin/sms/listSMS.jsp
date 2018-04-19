<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
<!-- END PAGE LEVEL STYLES -->
<title>短信配置</title>
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
							<shiro:hasPermission name="admin-platform:sms_add">
								<a id="sample_editable_1_new" class="btn green" href="${path}/sys/admin/sms/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" >
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
										<th>服务器地址</th>
										<%--<th>APIKEY</th>
										<th>版本号</th>--%>
										<th>发送uri</th>
										<th>模板发送uri</th>
										<th>获取模板uri</th>
										<th>新增模板uri</th>
										<th>修改模板uri</th>
										<th>删除模板uri</th>

										<th width="100px">是否启用</th>
										<th width="120px">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pager.datas }" var="config" >
										<tr style="height: 50px;">
											<td title='<c:out value="${config.smsName }"></c:out>'><c:out value="${config.smsName }"></c:out></td>
											<td title='<c:out value="${config.smsServer }"></c:out>'><c:out value="${config.smsServer }"></c:out></td>
											<%--<td title='<c:out value="${config.apikey }"></c:out>'><c:out value="${config.apikey }"></c:out></td>--%>
											<%--<td title='<c:out value="${config.version }"></c:out>'><c:out value="${config.version }"></c:out></td>--%>
											<td title='<c:out value="${config.send }"></c:out>'><c:out value="${config.send }"></c:out></td>
											<td title='<c:out value="${config.tplSend }"></c:out>'><c:out value="${config.tplSend }"></c:out></td>
											<td title='<c:out value="${config.getTpl }"></c:out>'><c:out value="${config.getTpl }"></c:out></td>
											<td title='<c:out value="${config.addTpl }"></c:out>'><c:out value="${config.addTpl }"></c:out></td>
											<td title='<c:out value="${config.updateTpl }"></c:out>'><c:out value="${config.updateTpl }"></c:out></td>
											<td title='<c:out value="${config.delTpl }"></c:out>'><c:out value="${config.delTpl }"></c:out></td>
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
												<shiro:hasPermission name="admin-platform:sms_update">
													<a href="<%=path%>/sys/admin/sms/update/${config.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="" >编辑</a>|
												</shiro:hasPermission>
												<shiro:hasPermission name="admin-platform:sms_test">
													<a href="<%=path%>/sys/admin/sms/test/${config.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="" >测试</a>|
												</shiro:hasPermission>
												<shiro:hasPermission name="admin-platform:sms_delete">
													<a href="javascript:;" class="" id="${config.id}" itemname="<c:out value="${config.smsName }"></c:out>" onclick="del(this);">删除</a>
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
						url : "${path}/sys/admin/sms/add",
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
						url : "${path}/sys/admin/sms/update",
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
		$('#modalTitle').text('添加短信');
	}
	
	var del = function(obj){
		var id = obj.id;
		var itemName = $(obj).attr("itemname");
		actionByConfirm1(itemName, "${path}/sys/admin/sms/delete/" + id,{} ,function(data,status){
			if (status == "success") {
				location.reload();
			}
		}); 
	};

	var update = function(id){
		$.ajax({
			url : "${path}/sys/admin/sms/get/"+id,
			type : "get",
			dataType : "json",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(json){
				if(json.success){
					$("#id").val(json.data.id);
					$("#smsName").val(json.data.smsName);
					$("#smsServer").val(json.data.smsServer);
					$("#apikey").val(json.data.apikey);
					$("#send").val(json.data.send);
					$("#tplSend").val(json.data.tplSend);
					$("#getTpl").val(json.data.getTpl);
					$("#addTpl").val(json.data.addTpl);
					$("#updateTpl").val(json.data.updateTpl);
					$("#delTpl").val(json.data.delTpl);
					$("#version").val(json.data.version);
					if(json.data.status) {
						$("#status").val(1);
					}else{
						$("#status").val(0);
					}
					$("#add").show();
					$("#modal").show();
					$('#modalTitle').text('编辑短信');
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
