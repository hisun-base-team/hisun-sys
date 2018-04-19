<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>安全日志</title>
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

				<div class="caption_title">安全日志</div>

				<c:if test="${roleCode eq 'ROLE_ADMIN'}">
					<div class="clearfix">
						<div class="control-group">
							<div id="query" style="float: left;margin-left: 10px;width: 100%;">
								<form action="${path}/sys/log/search" id="getLogForm" style="margin: 0 0 10px;" method="get" class="form-horizontal">
									  <div class="row-fluid pt20">
		                                    <div class="row-fluid">
		                                        <input name="type" id="type" value="4" type="hidden"/>
		                                        <span class="inp_name">开始时间：</span>
		                                        <div class="input-append" id="starttimeDiv">
													<input type="text" class="span12" value='<c:out value="${starttime }"></c:out>' name="starttime" id="starttime" readonly>
												</div>
		                                        <span class="inp_name">结束时间：</span>
		                                        <div class="input-append" id="endtimeDiv">
													<input type="text" class="span12" value='<c:out value="${endtime }"></c:out>' name="endtime" id="endtime" readonly>
												</div>
		                                        
		                                        <span class="inp_name">成员：</span>
		                                        <input type="text" size="16" class="m-wrap" name="userName" id="userName" style="margin:0px;" placeholder="输入用户名进行搜索" value="${userName }" >
		                                        <button type="submit" class="btn Short_but">查询</button>
		                                    	<button id="submitBtn" type="button" class="btn Short_but">清空</button>
		                                 </div>
		                            </div>
								</form>
							</div>
						</div>
					</div>
			</c:if>
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
			//$('#getLogForm')[0].reset(); 
			$("#starttime").val("");
			$("#endtime").val("");
			$("#userName").val("");
		});
		 
		$('#starttime').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			});
			$('#endtime').datepicker({
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
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var userName = $("#userName").val();
		userName=userName?userName:"";
		length = length > 0 ? length : url.length;
		window.location.href = url.substring(0, length) + "?pageNum="+pageNum+"&pageSize="+pageSize+"&starttime="+starttime+"&endtime="+endtime+"&userName="+userName+"&type=4";
	}
	</script>
</body>
</html>