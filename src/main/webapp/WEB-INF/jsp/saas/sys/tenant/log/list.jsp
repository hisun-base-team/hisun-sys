<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作日志</title>
<!-- BEGIN PAGE LEVEL STYLES -->

<link rel="stylesheet" type="text/css"
	href="${path}/css/select2_metro.css" />
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${path}/css/DT_bootstrap.css" />


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

		<div class="portlet box grey">

			<div class="portlet-title" style="margin: 0px; padding: 0px;">

				<div class="row-fluid mb10" style="vertical-align: middle;">
					<div class="caption_title">操作日志</div>

				</div>

			</div>

			<div class="clearfix">

				<div class="control-group">


					<div id="query">
						<form action="${path}/sys/tenant/log/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" id="searchForm" name="getLogForm" style="margin: 0 0 10px;" method="get">

							<span class="inp_name">操作时间从：</span>
							<div class="input-append" id="starttimeDiv" style="margin-bottom: 0px;">
								<input type="text" class="span12" value='<c:out value="${start }"></c:out>' name="start" id="start" readonly>
							</div>
							<span class="inp_name">到：</span>
							<div class="input-append" id="endtimeDiv" style="margin-bottom: 0px;">
								<input type="text" class="span12" value='<c:out value="${end }"></c:out>' name="end" id="end" readonly>
							</div>
							<%--<span class="inp_name">成员：</span>--%>
							<input type="text" size="16" name="searchContent" id="searchContent" style="margin:0px;" placeholder="输入用户名进行搜索" value="${searchContent }" >

							<span class="inp_name" style="margin-left: 10px;">操作类型：</span>
							<select class="select_form" tabindex="-1" name="type" id="type" style="width: 6%; margin-bottom: 0px;" >
								<option value="">全部</option>
								<option value="1" <c:if test="${type == '1'}">selected="selected"</c:if>>新增</option>
								<option value="2" <c:if test="${type == '2'}">selected="selected"</c:if>>修改</option>
								<option value="3" <c:if test="${type == '3'}">selected="selected"</c:if>>删除</option>
							</select>
							<button type="submit" class="btn blue Short_but">查询</button>
							<button id="submitBtn" type="button" class="btn Short_but">清空</button>
						</form>
					</div>
				</div>

			</div>

			<div class="portlet-body fuelux">
				<table
					class="table table-striped table-bordered table-hover dataTable table-set">
					<thead>
						<tr>
							<th style="width: 25%">登录ip</th>
							<th style="width: 80px">操作人</th>
							<th style="width: 160px">操作时间</th>
							<th style="width: 60px">操作类型</th>
							<th>操作内容</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pager.datas}" var="logs">
							<tr>
								<td><c:out value="${logs.ip }"></c:out></td>
								<td><c:out value="${logs.userName }"></c:out></td>
								<td><fmt:formatDate value="${logs.operateTime }" pattern="yyyy年MM月dd日 HH:mm:ss"/>  </td>
								<td>
									<c:choose>
										<c:when test="${logs.type==1}"><c:out value="新增"></c:out></c:when>
										<c:when test="${logs.type==2}"><c:out value="修改"></c:out></c:when>
										<c:when test="${logs.type==3}"><c:out value="删除"></c:out></c:when>
										<c:otherwise><c:out value="异常"></c:out></c:otherwise>
									</c:choose> 
								</td>
								<td title="<c:out value="${logs.content }"></c:out>"><c:out value="${logs.content }"></c:out></td>
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
			</div>
			
		</div>

	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script type="text/javascript" src="${path}/js/select2.min.js"></script>

	<script type="text/javascript"
		src="${path}/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${path}/js/DT_bootstrap.js"></script>
	<script type="text/javascript" src="${path}/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${path}/js/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="${path}/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="${path}/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		
		App.init();//必须，不然导航栏及其菜单无法折叠
		
		$("#submitBtn").on("click",function(){
			//$('#getLogForm')[0].reset(); 
			//$("#start").val("");
			//$("#end").val("");
			//$("#searchContent").val("");
            //$("#type").val("");
			var url = location.href;
			var length = url.indexOf("?");
			length = length > 0 ? length : url.length;
			window.location.href = url.substring(0, length);
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
			length = length > 0 ? length : url.length;
			window.location.href = url.substring(0, length) + "?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize+"&"+$("#getLogForm").serialize();
	}
	</script>
</body>
</html>