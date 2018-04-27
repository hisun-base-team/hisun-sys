<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">字典管理</div>
					<div class="clearfix fr">
						<a id="addDicType" class="btn green" href="${path}/sys/admin/dictionary/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
							<i class="icon-plus"></i> 添加
						</a>
					</div>
				</div>
				<div class="portlet-body">
					<%-- 按钮操作结束 --%>
					<div class="dataTables_wrapper form-inline">
						<%-- 查找框 --%>
						<table class="table table-striped table-bordered table-hover dataTable table-set">
							<thead>
								<tr>
									<th style="width: 20%">字典名称</th>
									<th style="width: 10%">字典CODE</th>
									<th >说明</th>
									<th width="120px">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.datas}" var="dictionary" >
									<tr style="height: 50px;">
										<td><a href="${path }/sys/admin/dictionary/item/index/${dictionary.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><c:out value="${dictionary.name }"></c:out></a></td>
										<td><c:out value="${dictionary.code }"></c:out></td>
										<td title='${cloud:htmlEscape(dictionary.remark)}'><c:out value="${dictionary.remark }"></c:out></td>
										<td class="Left_alignment">
											<a href="${path }/sys/admin/dictionary/edit/${dictionary.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="" >编辑</a>|
											<a href="javascript:;" class="" id="${dictionary.id}" itemname="<c:out value='${dictionary.name }'></c:out>" onclick="del(this);">删除</a>
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

</div>
<script type="text/javascript">
(function(){
	App.init();
})();
var del = function(obj){
	var id = obj.id;
	var itemName = $(obj).attr("itemname");
	actionByConfirm1(itemName, "${path}/sys/admin/dictionary/delete/" + id,{} ,function(data,status){
		if (status == "success") {
			location.reload();
		}
	});
};

function pagehref(pageNum, pageSie){
	window.location.href = "${path}/sys/admin/dictionary/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
}
</script>
</body>
</html>