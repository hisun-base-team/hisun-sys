<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录日志</title>
<!-- BEGIN PAGE LEVEL STYLES -->

<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/select2_metro.css" />

<link rel="stylesheet" href="<%=path%>/css/DT_bootstrap.css" />


<style type="text/css">
.mybodyfont{
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif !important;
}

.mycontent {
	margin-top: 20px;
}

.mylabel {
	font-size: 16px;
	cursor: default;
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif;
}

.mybutton-group {
	text-align: right;
}

.mybutton {
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif;
	font-size: 16px;
}

.btn {
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif !important;
}

table {
	border-collapse:collapse !important;
}
</style>
<!-- END PAGE LEVEL STYLES -->
</head>
<body>

			<div class="container-fluid">
				
				<div class="span6" style="width: 100%; margin: 0px;">

					<div class="portlet-title" style="vertical-align: middle; margin-bottom: 0px; border-bottom:0;">
						<div class="caption">登录日志</div>
						<%--<div class="btn-group fr">
                            <a id="sample_editable_1_new" class="btn green" href="${path }/sys/tenant/tenant/add">
                                <i class="icon-plus"></i> 创 建
                            </a>
                        </div>--%>
					</div>

					<div class="portlet-body">
						<div class="row-fluid" style="font-size: 14px;">
							<div id="query" style="float: left;margin-left: 10px;width: 100%;">
								<form action="${path}/sys/admin/log/security/search" id="getLogForm" style="margin: 0 0 10px;" method="get" class="form-horizontal">
									<input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
									开始日期：
									<div class="input-append" id="starttimeDiv">
										<input type="text" class="span12" value='<c:out value="${param.start }"></c:out>' name="start" id="start" readonly/>
									</div>
									<span class="inp_name" style="">结束日期：</span>
									<div class="input-append" id="endtimeDiv">
										<input type="text" class="span12" value='<c:out value="${param.end }"></c:out>' name="end" id="end" readonly/>
									</div>
									<span class="inp_name" style=""></span>
									<button type="submit" class="btn blue Short_but">查询</button>
									<button type="button" class="btn blue Short_but" onclick="searchReset()">清空</button>
								</form>
							</div>
						</div>
					</div>
				<table
					class="table table-striped table-bordered table-hover dataTable table-set">
					<thead>
						<tr>
							<th style="width: 70%">登录地址</th>
							<th style="width: 10%">操作人</th>
							<th style="width: 20%">登录时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pager.datas}" var="logs">
							<tr>
								<td><c:out value="${logs.ip }"></c:out></td>
								<td><c:out value="${logs.userName }"></c:out></td>
								<td><fmt:formatDate value="${logs.createTime }" pattern="yyyy年MM月dd日 HH:mm:ss"/>  </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="/WEB-INF/jsp/common/page.jsp">
					<jsp:param value="${pager.total }" name="total" />
					<jsp:param value="${pager.pageCount }" name="endPage" />
					<jsp:param value="${pager.pageNum }" name="page" />
					<jsp:param value="${pager.pageSize }" name="pageSize"/>
				</jsp:include>


			</div>
			
		</div>
	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script type="text/javascript" src="<%=path%>/js/select2.min.js"></script>

	<script type="text/javascript"
		src="<%=path%>/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/js/DT_bootstrap.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="<%=path%>/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		
		App.init();//必须，不然导航栏及其菜单无法折叠
		
		$("#submitBtn").on("click",function(){
			$("#starttime").val("");
			$("#endtime").val("");
			$("#userName").val("");
		});

		$('#start').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		$('#end').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});

	});
	
	function pagehref(pageNum, pageSize) {
		var url = location.href;
		var length = url.indexOf("?");
		var start = $("#start").val();
		var end = $("#end").val();
		var searchContent = $("#searchContent").val();
		searchContent=searchContent?searchContent:"";
		length = length > 0 ? length : url.length;
		window.location.href = url.substring(0, length) + "?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize+"&start="+start+"&end="+end+"&searchContent="+searchContent;
	}



	function calendarCancel(targetId){
		$("#"+targetId).removeAttr("value");
	}

	function searchReset(){
		$("#start").removeAttr("value");
		$("#end").removeAttr("value");
		$("#searchContent").removeAttr("value");
	}
	</script>
</body>
</html>