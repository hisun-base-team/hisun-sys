<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
	<!-- END PAGE LEVEL STYLES -->
	<title>工单类别</title>
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">工单类别列表</div>
				</div>

				<div class="portlet-body">
					<table class="table table-striped table-bordered table-hover dataTable table-set">
						<thead>
						<tr>
							<th>类别名称</th>
							<th>排序</th>
							<th>创建时间</th>
							<th>创建人</th>
							<th>更新时间</th>
							<th>更新人</th>
							<th width="200px;">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${pager.datas}" var="entity" varStatus="varStatus">
							<tr style="text-overflow:ellipsis;">
								<td><c:out value="${entity.name }"></c:out></td>
								<td><c:out value="${entity.priority }"></c:out></td>
								<td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
								<td>
									<c:out value="${entity.createUsername}"></c:out>
								</td>
								<td><fmt:formatDate value="${entity.updateDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
								<td>
									<c:out value="${entity.updateUsername}"></c:out>
								</td>
								<td class="Left_alignment">
									<a href="${path }/sys/tenant/tenant/sysadmin/view/${entity.id}" >修改</a> |
									<a href="${path }/sys/tenant/user/sysAdmin/list?tenantId=${entity.id}">删除</a>
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
		var startDate = $("#start").datepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			weekStart : 1,
			autoclose : true
			<c:if test="${fn:length(param.end) > 0}">
			,startDate : "${param.end}"
		</c:if>
		}).on('hide', function(ev){
			$("#start").blur();
		});
		var endDate = $("#end").datepicker({
			language:  'zh-CN',
			format: "yyyy-mm-dd",
			pickerPosition: "bottom-left",
			weekStart : 1,
			autoclose : true
			<c:if test="${fn:length(param.start) > 0}">
				,startDate : "${param.start}"
			</c:if>
		}).on('hide', function(ev){
			$("#end").blur();
		});
	})();

	function pagehref (pageNum ,pageSize){
		window.location.href ="${path}/sys/tenant/tenant/list?pageNum="+pageNum+"&pageSize="+pageSize+"&name=" + encodeURI(encodeURI("${name}"))
				+"&start=${param.start}&end=${param.end}&status=${param.status}";
	}

	function del(id){
		actionByConfirm1('',"${path}/sys/tenant/tenant/delete/"+id,null,function(json){
			if(json.privilegeCode == 1){
				showTip("提示","操作成功");
				setTimeout(function(){
					window.location.href ="${path}/sys/tenant/tenant/list?name=" + encodeURI(encodeURI("${name}"))
							+"&start=${param.start}&end=${param.end}&status=${param.status}";
				},1500);

			}else{
				showTip("提示", json.message, 2000);
			}
		},"冻结")
	}

	function activate(id){
		actionByConfirm1('',"${path}/sys/tenant/tenant/activate/"+id,null,function(json){
			if(json.privilegeCode == 1){
				showTip("提示","操作成功");
				setTimeout(function(){
					/*window.location.href ="${path}/sys/tenant/tenant/list?name=" + encodeURI(encodeURI("${name}"))
							+"&start=${param.start}&end=${param.end}&status=${param.status}";*/
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
