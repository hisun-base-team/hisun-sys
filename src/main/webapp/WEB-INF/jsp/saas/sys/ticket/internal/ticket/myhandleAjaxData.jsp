<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cloud" uri="/WEB-INF/tld-management/management-function.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
        <%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<table id="sample_2" class="table table-striped table-bordered table-hover table-full-width">
    <thead>
        <tr>
			<th width="">标题</th>
			<th width="130">创建日期</th>
			<th width="12%">创建人</th>
			<th width="50px">状态</th>
         </tr>
     </thead>
     <tbody>
		<c:forEach items="${pager.datas}" var="ticket">
			<tr style="text-overflow:ellipsis;">
				<td title="<c:out value="${ticket.title }"></c:out>">
					<a href="${path }/management/ticket/expert/detail?ticketId=${ticket.id}">
						<c:out value="${ticket.title }"></c:out>
					</a>
				</td>
				<td><fmt:formatDate value="${ticket.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td><c:out value="${ticket.createUser.username }"></c:out> </td>
				<td>
					<c:choose>
						<c:when test="${ticket.status==0 }">
							待处理
						</c:when>
						<c:when test="${ticket.status==1 }">
							处理中
						</c:when>
						<c:when test="${ticket.status==2 }">
							已解决
						</c:when>
						<c:when test="${ticket.status==3 }">
							已完成
						</c:when>
					</c:choose>
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
<script>
function pagehref (pageNum ,pageSize){
	loadData("${param.status}",pageNum,pageSize);
}
</script>