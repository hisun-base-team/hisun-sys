<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<div class="span6" style="width: 100%; margin: 0px;">
	<div class="portlet box grey">
		<div class="portlet-title" style="vertical-align: middle;">
			<div class="caption">字典项</div>
			<div class="clearfix fr">
				<a id="addItem" class="btn green" href="#">
					<i class="icon-plus"></i> 添加
				</a>
			</div>
		</div>
		<div class="portlet-body fuelux">
			<table class="table table-striped table-bordered table-hover dataTable table-set">
				<thead>
				<tr>
					<!-- <th>字典类型名</th> -->
					<th>字典项名</th>
					<th>字典项值</th>
					<th>排序</th>
					<th>备注</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${pager.datas}" var="item">
					<tr>
						<td><c:out value="${item.name }"></c:out></td>
						<td><c:out value="${item.code }"></c:out></td>
						<td><c:out value="${item.sort }"></c:out></td>
						<td><c:out value="${item.remark }"></c:out> </td>
						<td><a href="javascript:;" class="btn mini blue "  onclick="updateResource('${item.id}');"><i class="icon-pencil"></i>&nbsp;修&nbsp;改</a>
							&nbsp;&nbsp;&nbsp;
							<a href="javascript:;" class="btn mini red" id="${item.id}" itemname="<c:out value="${item.name }"></c:out>" onclick="delResource(this);"><i class="icon-trash"></i>&nbsp;删&nbsp;除</a>
							<input type="hidden" name="delId" id="delId" >
						</td>
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
<script type="text/javascript">
	$("#addItem").click(function(){
		var currentNodeId = $("#currentNodeId").val();
		var currentNodeParentId = $("#currentNodeParentId").val();
		$.ajax({
			url : "${path}/sys/admin/dictionary/item/ajax/add?currentNodeId="+currentNodeId+"&currentNodeParentId="+currentNodeParentId,
			type : "get",
			data : null,
			dataType : "html",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(html){
				$("#rightList").html(html);
			},
			error : function(){

			}
		});
	});

</script>