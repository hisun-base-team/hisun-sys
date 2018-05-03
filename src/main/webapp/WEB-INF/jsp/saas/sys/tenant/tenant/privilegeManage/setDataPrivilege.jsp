<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
	<!-- END PAGE LEVEL STYLES -->
	<title>${resourceName} 权限资源配置</title>
	<style type="text/css">
		.tree_class {width: 280px;}
	</style>
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">${tenantName} 设置“${resourceName} ”的数据范围</div>
					<%--<shiro:hasPermission name="tenant:tenantadd">--%>

					<%--</shiro:hasPermission>--%>
					<div class="clearfix fr">
						<c:if test="${not empty vos}">
							<a id="sample_editable_1_new" class="btn green" href="javascript:save();">
								保存
							</a>
						</c:if>
						<a class="btn" href="${path}/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><i class="icon-undo"></i>返回</a>
					</div>
				</div>
				<form class="form-horizontal myform"
					  id="form1" method="post">
					<input type="hidden" name="resourceId" id="resourceId" value="${resourceId}">
					<input type="hidden" name="tenantId" id="tenantId" value="${tenantId}">
					<table class="table table-striped table-bordered table-hover dataTable table-set">
						<thead>
						<tr>
							<th width="200px">数据资源名称</th>
							<th width="300px">范围</th>
							<th>描述</th>
						</tr>
						</thead>
						<tbody>
						<c:if test="${not empty vos}">
							<c:forEach items="${vos}" var="vo" varStatus="varStatus">
								<tr style="text-overflow:ellipsis;">
									<td><c:out value="${vo.privilegeName }"></c:out></td>
									<td style="margin-bottom: 0px">
										<c:if test="${vo.privilegeDisplayType==1}"><!-- 树形下拉 -->
											<Tree:tree id="${vo.tenantPrivilegeId}"  treeUrl="${vo.selectUrl}" selectClass="tree_class"  token="${sessionScope.OWASP_CSRFTOKEN}"  chkboxType=" 'Y' : 'ps', 'N' : 'ps'"
												   radioOrCheckbox="checkbox" checkedByTitle="true" isSelectTree="true" submitType="get" dataType="json" isSearch="false" defaultkeys="${vo.selectedValues}" defaultvalues="${vo.selectedNames}"/>
										</c:if>
										<c:if test="${vo.privilegeDisplayType==2}"><!-- 多选下拉 -->
											<SelectTag:SelectTag id="${vo.tenantPrivilegeId}" width="294px" moreSelectAll="true"
															 radioOrCheckbox="checkbox" moreSelectSearch="no" selectUrl="${vo.selectUrl}" defaultkeys="${vo.selectedValues}"/>
										</c:if>
									</td>
									<td><c:out value="${vo.privilegeDescription }"></c:out>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty vos}">
							<tr style="text-overflow:ellipsis;">
								<td colspan="3" style="text-align: center"><p style="font-size: 16px; "><b>${resourceName} 不需要设置数据权限</b></p>
								</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<%-- 表格结束 --%>
	</div>
</div>

<%-- END PAGE CONTENT--%>
</div>
<script type="text/javascript" src="${path}/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${path}/js/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript">
	(function(){
		App.init();

	})();


	function save(){


		$.ajax({
			url : "${path}/sys/tenant/tenant/save/tenant2ResourcePrivilege",
			type : "post",
			data : $('#form1').serialize(),
			headers:{
				OWASP_CSRFTOKEN:"${sessionScope.OWASP_CSRFTOKEN}"
			},
			dataType : "html",
			success : function(html){
				showTip("提示","数据资源保存成功！",2000);
			},
			error : function(){
				showTip("提示","出错了请联系管理员", 1500);
			}
		});
	}


</script>
</body>
</html>
