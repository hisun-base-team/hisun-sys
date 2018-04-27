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
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">设置“${resourceName} ”的数据范围</div>
					<%--<shiro:hasPermission name="tenant:tenantadd">--%>

					<%--</shiro:hasPermission>--%>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable table-set">
					<thead>
					<tr>
						<th style="width: 20px;"><input type="checkbox" id="allCheck" onchange="dataAllcheckChange()" ></th>
						<th width="100px">数据资源名称</th>
						<th width="300px">范围</th>
						<th>描述</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${requestScope.vos}" var="vo" varStatus="varStatus">
						<tr style="text-overflow:ellipsis;">
							<td><input type="checkbox" value="${vo.id }" name="dataIds" <c:if test="${vo.isChecked eq 'true'}">checked</c:if>></td>
							<td><c:out value="${vo.name }"></c:out></td>
							<td></td>
							<td><c:out value="${vo.description }"></c:out>
							</td>

						</tr>
					</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
		<%-- 表格结束 --%>
	</div>
</div>

<%-- END PAGE CONTENT--%>
</div>
<script type="text/javascript" src="<%=path%>/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript">
	(function(){
		App.init();

	})();
	function dataAllcheckChange(){
		var allCheck = document.getElementById("allCheck");
		var checks = document.getElementsByName("dataIds");
		if(checks){
			for(var i=0;i<checks.length;i++) {
				checks[i].checked = allCheck.checked;
				if (allCheck.checked == true) {
					checks[i].parentNode.className = "checked";
				}else{
					checks[i].parentNode.className = "";
				}
			}
		}
	}
	function pagehref (pageNum ,pageSize){
		window.location.href ="${path}/sys/tenant/privilege/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize+"&name=" + encodeURI(encodeURI("${name}"))+"&tombstone="+$("#tombstone").val();
		+"&start=${param.start}&end=${param.end}&status=${param.status}";
	}


	function save(){
		var resourceId = "${resourceId}";
		var resourceName = "${resourceName}";
		if(resourceId=="1"){
			showTip("提示","不可对“资源树”节点进行配置",2000);
			return;
		}
		var checks = document.getElementsByName("dataIds");
		var privilegeIds = "";
		var checkedCount = 0;
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked==true){
				checkedCount ++;
				if(privilegeIds==""){
					privilegeIds = checks[i].value;
				}else{
					privilegeIds =privilegeIds+","+checks[i].value;
				}
			}
		}
		if(checkedCount == 0){
			showTip("提示","请选择权限资源",2000);
			return;
		}
		$.ajax({
			url : "${path}/sys/tenant/resourcePrivilege/ajax/save",
			type : "post",
			data: {"resourceId":resourceId,
				"privilegeIds":privilegeIds
			},
			headers:{
				OWASP_CSRFTOKEN:"${sessionScope.OWASP_CSRFTOKEN}"
			},
			dataType : "html",
			success : function(html){
				showTip("提示","“"+resourceName+"”权限资源配置成功！",2000);
			},
			error : function(){
				showTip("提示","出错了请联系管理员", 1500);
			}
		});
	}


</script>
</body>
</html>
