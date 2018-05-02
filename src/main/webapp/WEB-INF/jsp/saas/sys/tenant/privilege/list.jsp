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
	<title>租户权限管理</title>
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">租户权限列表</div>
					<%--<shiro:hasPermission name="tenant:tenantadd">--%>
						<div class="btn-group fr">
							<a id="sample_editable_1_new" class="btn green" href="${path }/sys/tenant/privilege/add?&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
								<i class="icon-plus"></i>添加
							</a>
						</div>
					<%--</shiro:hasPermission>--%>
				</div>


					<table class="table table-striped table-bordered table-hover dataTable table-set">
						<thead>
						<tr>
							<th width="100px">名称</th>
							<th width="100px">代码</th>
							<th width="200px">实现类</th>
							<th>权限数据源URL</th>
							<th width="60px">排序</th>
							<th width="150px;">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${requestScope.pager.datas}" var="entity" varStatus="varStatus">
							<tr style="text-overflow:ellipsis;">
								<td><a href="${path }/sys/tenant/privilege/edit/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" ><c:out value="${entity.name }"></c:out></a></td>
								<td><c:out value="${entity.code }"></c:out></td>
								<td><c:out value="${entity.impclass }"></c:out>
								<td><c:out value="${entity.selectUrl }"></c:out></td>
								<td><c:out value="${entity.sort }"></c:out></td>
								<td class="Left_alignment">
									<a href="${path }/sys/tenant/privilege/edit/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" >编辑</a>
									|<a href="javascript:void(0)" onclick="del('${entity.id }')">删除</a>
									|<a href="#">被调用资源</a>

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

	function pagehref (pageNum ,pageSize){
		window.location.href ="${path}/sys/tenant/privilege/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize+"&name=" + encodeURI(encodeURI("${name}"))+"&tombstone="+$("#tombstone").val();
				+"&start=${param.start}&end=${param.end}&status=${param.status}";
	}

	function del(id){
		actionByConfirm1('',"${path}/sys/tenant/privilege/delete/"+id,null,function(json){
			if(json.code == 1){
				showTip("提示","操作成功");
				setTimeout(function(){
					window.location.href ="${path}/sys/tenant/privilege/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&name=" + encodeURI(encodeURI("${name}"))
							+"&start=${param.start}&end=${param.end}&status=${param.status}";
				},1500);

			}else{
				showTip("提示", json.message, 2000);
			}
		},"删除")
	}

	function activate(id){
		actionByConfirm1('',"${path}/sys/tenant/privilege/activate/"+id,null,function(json){
			if(json.code == 1){
				showTip("提示","操作成功");
				setTimeout(function(){
					window.location.reload();
				},1500);

			}else{
				showTip("提示", json.message, 2000);
			}
		},"激活")
	}

	function startChange(){
		var startVal = document.getElementById("start").value;
		$("#end").datepicker("remove");
		$("#end").datepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			weekStart : 1,
			autoclose : true,
			startDate : startVal
		}).on('hide', function(ev){
			$("#start").blur();
		});
	}

	function endChange(){
		var endVal = document.getElementById("end").value;
		$("#start").datepicker("remove");
		$("#start").datepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			weekStart : 1,
			autoclose : true,
			endDate : endVal
		}).on('hide', function(ev){
			$("#end").blur();
		});
	}

	function calendarCancel(targetId){
		$("#"+targetId).val("");
	}

	function searchReset(){
		$("#name").removeAttr("value");
		$("#start").removeAttr("value");
		$("#end").removeAttr("value");
	}
</script>
</body>
</html>
