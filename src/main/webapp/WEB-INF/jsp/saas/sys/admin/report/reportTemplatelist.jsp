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
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
<!-- END PAGE LEVEL STYLES -->
<title>报表模板列表</title>
</head>
<body>
			<div class="container-fluid">

				<div class="row-fluid">
					<div class="span12 responsive">
						<%-- 表格开始 --%>
						<div class="portlet box grey">
							<div class="portlet-title" style="vertical-align: middle;">
								<div class="caption">
									报表模板列表
								</div>
								<div class="clearfix fr">
									<a id="sample_editable_1_new" class="btn green" href="javascript:addReportTemp()">
										<i class="icon-plus"></i>
										添加报表模板
									</a>
								</div>
							</div>
					
							<div class="portlet-body">
								<%-- 按钮操作结束 --%>
								<table class="table table-striped table-bordered table-hover dataTable table-set">
									<thead>
										<tr>
											<th width="15%">名称</th>
											<th width="40%">模板路径</th>
											<th width="10%">模板类型</th>
											<th width="20%">描述</th>
											<th width="10%">状态</th>
											<th width="12%">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pager.datas}" var="reportTemp">
											<tr style="text-overflow:ellipsis;">
												<td><a href="javascript:updateReport('${reportTemp.id }')"><c:out value="${reportTemp.reportTempName}"></c:out></a></td>
												<td title="${reportTemp.reportTempPath }"><c:out value="${reportTemp.reportTempPath }"></c:out></td>
												<td title="${reportTemp.reportTempType }"><c:out value="${reportTemp.reportTempType }"></c:out></td>
												<td title="${reportTemp.reportTempDesc}"><c:out value="${reportTemp.reportTempDesc}"></c:out></td>
												<td>
													<c:choose>
														<c:when test="${reportTemp.tombstone ==0 }">
															可用
														</c:when>
														<c:when test="${reportTemp.tombstone ==1 }">
															不可用
														</c:when>
													</c:choose>

												</td>
												<td>
													<a href="javascript:updateReport('${reportTemp.id }')" class="">修 改</a> |
													<a href="javascript:del('${reportTemp.id }','${reportTemp.reportTempName }')" class="">删 除</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							    <jsp:include page="/WEB-INF/jsp/common/page.jsp">
									<jsp:param value="${pager.total }" name="total"/>
									<jsp:param value="${pager.pageCount }" name="endPage"/>
									<jsp:param value="${pager.pageSize }" name="pageSize"/>
									<jsp:param value="${pager.pageNum }" name="page"/>
								</jsp:include>
							</div>
						</div>
						<%-- 表格结束 --%>
					</div>
				</div>
				<div id="myModalForReport" class="modal container hide fade" tabindex="-1" style="padding-bottom: 20px;" data-width="1000">
					<div class="modal-body" id="addReportTable"></div>

				</div>
				<%-- END PAGE CONTENT--%>  
			</div>

	<script type="text/javascript">
		(function(){
			App.init();
			$('#myModalForReport').on('hide.bs.modal', function () {
				initModalForReport();
			});
		})();

		function initModalForReport()
		{

		}
	
		function pagehref (pageNum ,pageSize){
			window.location.href ="${path}/sys/report/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize;
		}
		
		function searchSubmit(){
			document.searchForm.submit();
		}

		function addReportTemp()
		{
			$.ajax({
				url : '${path }/sys/report/ajax/add/',
				type : "get",
				data : {},
				dataType : "html",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(html){
					$("#addReportTable").html(html);
					$('#myModalForReport').modal({
						keyboard: false,
						backdrop: 'static'
					});
				},
				error : function(){
				}
			});
		}

		var del = function(id,itemName){
			actionByConfirm1(itemName, "${path}/sys/report/delete?id=" + id,{} ,function(data,status){
				if (data.success == true) {
					showTip("提示","删除成功", 1000);
					setTimeout(function(){window.location.href = "${path}/sys/report/list"},2000);
				}else{
					showTip("提示", data.msg, 2000);
				}
			});
		};

		function updateReport(id)
		{
			$.ajax({
				url : '${path }/sys/report/ajax/update/',
				type : "get",
				data : {"id":id},
				dataType : "html",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(html){
					$("#addReportTable").html(html);
					$('#myModalForReport').modal({
						keyboard: false,
						backdrop: 'static'
					});
				},
				error : function(){
				}
			});
		}

	</script>
</body>
</html>
