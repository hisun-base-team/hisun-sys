<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成员登录日志</title>
<!-- BEGIN PAGE LEVEL STYLES -->

<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/select2_metro.css" />

<link rel="stylesheet" href="<%=path%>/css/DT_bootstrap.css" />
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="row-fluid mb10" style="vertical-align: middle;">
					<div class="caption_title">登录日志</div>

				</div>
				<div class="clearfix">

					<div class="control-group">


						<div id="query" style="float: left;">
							<form action="${path }/sys/tenant/log/login/admin/list" id="searchForm" name="searchForm" style="margin: 0 0 10px;" method="get">
								<input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
								登录时间从：
								<div class="input-append" id="fromDateDiv" style="margin-bottom: 0px;">
									<input type="text" class="span12" id="start" value="${cloud:htmlEscape(start) }" onchange="startChange()" name="start" readonly>
								</div>
								到：
								<div class="input-append" id="toDateDiv" style="margin-bottom: 0px;">
									<input type="text" class="span12" id="end" name="end" class="m-wrap" value="${cloud:htmlEscape(end) }" onchange="endChange()" readonly>
								</div>
								登录ip：<input type="text" class="m-wrap" name="ip" id="ip" placeholder="登录ip" value="${cloud:htmlEscape(ip)}">
								<input type="text" name="searchContent" id="searchContent" style="margin:0px;" class="m-wrap" placeholder="用户名/邮箱/姓名/手机搜索" value="${searchContent}">
								<button type="submit" class="btn Short_but" id="searchButton">查询</button>
								<button onclick="searchReset()" type="reset" class="btn Short_but" id="resetButton">清空</button>
							</form>
						</div>
					</div>

				</div>
				<div class="portlet-body">
					<div class="row-fluid" style="font-size: 14px;">
					</div>
					<table class="table table-striped table-bordered table-hover dataTable table-set">
						<thead>
						<tr>
							<th style="width: 60%">登录ip</th>
							<th style="width: 20%">姓名</th>
							<th style="width: 20%">登录时间</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${pager.datas}" var="logs">
							<tr>
								<td><c:out value="${logs.ip }"></c:out></td>
								<td><c:out value="${logs.realname }"></c:out></td>
								<td><fmt:formatDate value="${logs.operateTime }" pattern="yyyy年MM月dd日 HH:mm:ss"/>  </td>
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
			<%-- 表格结束 --%>
		</div>
	</div>

	<%-- END PAGE CONTENT--%>
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
			$("#starttime").removeAttr(value);
			$("#endtime").removeAttr(value);
		});

		startCalendar = $("#start").datetimepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			startView: 2,
			minView : 2,
			autoclose : true,
			endDate : '${end}',
			forceParse : false
		}).on('hide', function(ev){
			$("#start").blur();
		});

		endCalendar = $("#end").datetimepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			startView: 2,
			minView : 2,
			autoclose : true,
			startDate : '${start}'
		}).on('hide', function(ev){
			$("#end").blur();
		});

	});
	
	function pagehref(pageNum, pageSize) {
		window.location.href = "${path}/sys/tenant/log/login/admin/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize + "&" + $("#searchForm").serialize();
	}

	function startChange(){
		var startVal = document.getElementById("start").value;
		$("#end").datetimepicker("remove");
		endCalendar = $("#end").datetimepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			startView: 2,
			minView : 2,
			autoclose : true,
			startDate : startVal
		}).on('hide', function(ev){
			$("#start").blur();
		});
	}

	function endChange(){
		var endVal = document.getElementById("end").value;
		$("#start").datetimepicker("remove");
		startCalendar = $("#start").datetimepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			startView: 2,
			minView : 2,
			autoclose : true,
			endDate : endVal
		}).on('hide', function(ev){
			$("#end").blur();
		});
	}

	function calendarCancel(targetId){
		$("#"+targetId).removeAttr("value");
	}

		function searchReset(){
			//("#start").removeAttr("value");
			//$("#end").removeAttr("value");
			//$("#searchContent").removeAttr("value");
			window.location.href = "${path}/sys/tenant/log/login/admin/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
		}

		function startShow(){
			$("#start").datetimepicker("show");
		}
	</script>
</body>
</html>