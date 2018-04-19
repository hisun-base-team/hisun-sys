<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<table class="table table-striped table-bordered table-hover dataTable table-set">
	<thead>
	<tr>
		<th style="width: 20%">消息标题</th>
		<th>消息内容</th>
		<th style="width: 150px">创建时间</th>
		<th width="120px">操作</th>
	</tr>
	</thead>
	<tbody id="unread">
	<c:forEach items="${unread}" var="onlineMessage">
		<tr>
			<td><c:out value="${onlineMessage.title }"></c:out></td>
			<td><c:out value="${onlineMessage.content }"></c:out></td>
			<td><fmt:formatDate value="${onlineMessage.createDate }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
			<td class="Left_alignment">
				<a href="javascript:;" class=""  onclick="read('${onlineMessage.id}','unread','2');">已读</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>