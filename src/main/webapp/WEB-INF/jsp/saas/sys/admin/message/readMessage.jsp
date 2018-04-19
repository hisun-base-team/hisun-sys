<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<div class="portlet-body fuelux">

	<table
		class="table table-striped table-bordered table-hover dataTable table-set">
		<thead>
			<tr>
				<th style="width: 20%">消息标题</th>
				<th>消息内容</th>
				<th style="width: 150px">创建时间</th>
				<th style="width: 120px;">操作</th>
			</tr>
		</thead>
		<tbody id="read">
			<c:forEach items="${pager.datas}" var="onlineMessage">
				<tr>
					<td><c:out value="${onlineMessage.title }"></c:out></td>
					<td><c:out value="${onlineMessage.content }"></c:out></td>
					<td><c:out value="${onlineMessage.createDate }"></c:out></td>
					<td class="Left_alignment"><a href="javascript:;" class=""  onclick="read('${onlineMessage.id}','read','0');">未读</a>|
						<a href="javascript:;" class=""  onclick="read('${onlineMessage.id}','read','1');">待处理</a>
					</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="/WEB-INF/jsp/common/page.jsp">
		<jsp:param value="${pager.total }" name="total" />
		<jsp:param value="${pager.pageCount }" name="endPage" />
		<jsp:param value="${pager.pageNum }" name="page" />
		<jsp:param value="${pager.pageSize }" name="pageSize" />
	</jsp:include>
</div>