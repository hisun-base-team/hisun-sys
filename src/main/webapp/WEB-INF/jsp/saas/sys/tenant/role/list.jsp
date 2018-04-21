<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<title>角色列表</title>
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">角色列表</div>
					<div class="btn-group fr">
						<a id="sample_editable_1_new" class="btn green" href="${path }/sys/tenant/role/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
							<i class="icon-plus"></i> 创 建
						</a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="row-fluid" style="font-size: 14px;">
						<form action="${path }/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" method="post" id="searchForm" name="searchForm">
							<input type="text" class="m-wrap" name="name" id="searchName" value="${name}" placeholder="角色名">
							<button type="submit" class="btn blue Short_but">查询</button>
							<button type="button" class="btn blue Short_but" onclick="searchReset()">清空</button>
						</form>
					</div>
					<table class="table table-striped table-bordered table-hover dataTable table-set">
						<thead>
						<tr>
							<th width="20%">名字</th>
							<th>描述</th>
							<th width="80px">排序</th>
							<th width="200px;">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${requestScope.pager.datas}" var="entity">
							<tr style="text-overflow:ellipsis;">
								<td><c:out value="${entity.roleName }"></c:out></td>
								<td title="<c:out value="${entity.description}"></c:out>"><c:out value="${entity.description}"></c:out></td>
								<td>${entity.sort}</td>
								<td class="Left_alignment">
									<a href="${path }/sys/tenant/role/edit/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" >修改</a> |
									<a href="${path}/sys/tenant/role/setresource/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" >授权</a> |
									<a href="javascript:void(0)" onclick="del('${entity.id }')">删除</a>
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

	<%-- END PAGE CONTENT--%>
</div>
<script type="text/javascript">
	(function(){
		App.init();
	})();

	function pagehref (pageNum ,pageSize){
		window.location.href ="${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize+"&name=" + encodeURI(encodeURI("${name}"));
	}

	function del(id){
		actionByConfirm1('',"${path}/sys/tenant/role/delete/"+id,null,function(json){
			if(json.code == 1){
				showTip("提示","操作成功");
				setTimeout(function(){
					window.location.href ="${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&name=" + encodeURI(encodeURI("${name}"));
				},1500);

			}else{
				showTip("提示", json.message, 2000);
			}
		},"删除")
	}

	function searchReset(){
		$("#searchName").removeAttrs("value");
	}
</script>
</body>
</html>
