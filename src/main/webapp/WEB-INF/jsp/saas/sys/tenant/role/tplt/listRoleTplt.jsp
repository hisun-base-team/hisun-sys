<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色模板管理</title>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">角色模板管理</div>
					<div class="clearfix fr">
						<a id="addDicType" class="btn green" href="${path}/sys/tenant/role/tplt/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
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
									<th style="width: 20%">角色名称</th>
									<th style="width: 10%">角色代码前缀</th>
									<th >描述</th>
									<th width="120px">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.datas}" var="vo" >
									<tr style="height: 50px;">
										<td><a href="${path}/sys/tenant/role/tplt/edit/${vo.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><c:out value="${vo.roleName}"></c:out></a></td>
										<td><c:out value="${vo.roleCodePrefix}"></c:out></td>
										<td title='${cloud:htmlEscape(vo.roleDescription)}'><c:out value="${vo.roleDescription}"></c:out></td>
										<td class="Left_alignment">
											<a href="${path}/sys/tenant/role/tplt/edit/${vo.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="" >编辑</a>|
											<a href="javascript:;" class="" id="${vo.id}" voname="<c:out value='${vo.roleName}'></c:out>" onclick="del(this);">删除</a>
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
	var voname = $(obj).attr("voname");
	actionByConfirm1(voname, "${path}/sys/tenant/role/tplt/delete/" + id,{} ,function(data,status){
		if (status == "success") {
			location.reload();
		}
	});
};

function pagehref(pageNum, pageSie){
	window.location.href = "${path}/sys/tenant/role/tplt/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
}
</script>
</body>
</html>