<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>租户信息</title>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

			<div class="portlet box grey">

				<div class="portlet-title">

					<div class="caption">

						<span class="hidden-480">租户信息</span>
					</div>
				</div>

				<div class="portlet-body form">
					<div class="span12">
						<div class="tab-content">

							<div class="tab-pane profile-classic row-fluid active" id="tab_1_2">
									<table
											class="table table-striped table-bordered table-hover table-full-width"
											id="sample_2">

										<thead>
											<tr>
												<th>租户名称</th>
												<th>创建日期</th>
												<th>成员人数</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td> <c:out value="${entity.name }"></c:out></td>
												<td> <fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
												<td> <a href="${path}/sys/tenant/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><c:out value="${memberCount }"></c:out></a></td>
												<td>
													<shiro:hasPermission name="tenant:tenantownupdate">
														<a  href="${path}/sys/tenant/tenant/own/update?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" style="">修改信息</a>
													</shiro:hasPermission>
												</td>
											</tr>
										</tbody>
									</table>

							</div>

						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>