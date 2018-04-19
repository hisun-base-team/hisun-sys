<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@include file="../../../../inc/servlet.jsp" %>
<%@include file="../../../../inc/taglib.jsp" %>
<table
		class="table table-striped table-bordered table-hover table-full-width"
		id="sample_2">

	<thead>

	<tr>

		<th>用户名</th>
		<th>邮箱</th>
		<th>真实姓名</th>
		<th>手机号码</th>

	</tr>

	</thead>
	<tbody >

	<c:forEach items="${requestScope.pager.datas}" var="user"
			   varStatus="status">
		<tr style="height: 50px;">
			<td title="<c:out value="${user.username}"></c:out>"><c:out value="${user.username}"></c:out></td>
			<td title="<c:out value="${user.email}"></c:out>"><c:out value="${user.email}"></c:out></td>
			<td title="<c:out value="${user.realname}"></c:out>"><c:out value="${user.realname}"></c:out></td>
			<td title="<c:out value="${user.tel}"></c:out>"><c:out value="${user.tel}"></c:out></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<c:if test="${pager.pageCount>0}">
	<jsp:include page="/WEB-INF/jsp/common/page.jsp">
		<jsp:param value="${pager.total}" name="total" />
		<jsp:param value="${pager.pageCount}" name="endPage" />
		<jsp:param value="${pager.pageNum}" name="page" />
		<jsp:param value="${pager.pageSize}" name="pageSize" />
	</jsp:include>
</c:if>
