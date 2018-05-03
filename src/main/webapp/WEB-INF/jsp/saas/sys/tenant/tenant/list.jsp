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
	<script src="${contextPath}/js/selectTag.js"  charset=“utf-8”></script>

	<!-- END PAGE LEVEL STYLES -->
	<title>租户列表</title>
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12 responsive">
			<%-- 表格开始 --%>
			<div class="portlet box grey">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">租户列表</div>
					<shiro:hasPermission name="sys-tenant:*">
						<div class="btn-group fr">
							<a id="sample_editable_1_new" class="btn green" href="${path}/sys/tenant/tenant/add?&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
								<i class="icon-plus"></i> 创 建
							</a>
						</div>
					</shiro:hasPermission>
				</div>

				<div class="portlet-body">
					<div class="row-fluid" style="font-size: 14px;">
						<form action="${path }/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" method="post" id="searchForm" name="searchForm">
							租户名称:
							<div class="input-append" id="fromDateDiv" style="margin-bottom: 0px;" >
								<input type="text"  style="width: 100px;" name="name" id="name" value="${name}" >
							</div>
								创建日期:
							<div class="input-append" id="fromDateDiv" style="margin-bottom: 0px;" >
								<input size="16" type="text" readonly=""  id="start"  name="start" class="span12" style="width: 100px;" value="${param.start }" onchange="startChange()" style="width: 90px;">
							</div> -
							<div class="input-append" id="toDateDiv" style="margin-bottom: 0px;">
								<input size="16" type="text" readonly="" id="end" name="end" class="span12"  style="width: 100px;" value="${param.end }" onchange="endChange()" style="width: 90px;">
							</div>
							状态:
							<select name="tombstone" id="tombstone" style="margin-bottom: 0px;width:80px;">
									<option value="-1" ${tombstone == -1?'selected="selected"':''}>全部</option>
									<option value="0" ${tombstone == 0?'selected="selected"':''}>正常</option>
									<option value="1" ${tombstone ==1?'selected="selected"':''}>冻结</option>
								</select>
							<button type="submit" class="btn Short_but">查询</button>
							<button type="button" class="btn Short_but" onclick="searchReset()">清空</button>
						</form>
					</div>
					<table class="table table-striped table-bordered table-hover dataTable table-set">
						<thead>
						<tr>
							<th>租户名称</th>
							<th>创建时间</th>
							<th>状态</th>
							<th width="100px">用户数量</th>
							<th width="200px;">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${pager.datas}" var="entity">
							<tr style="text-overflow:ellipsis;">
								<td><a href="${path}/sys/tenant/tenant/edit/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><c:out value="${entity.name}"></c:out></a></td>
								<td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
								<td>
									<c:choose>
										<c:when test="${entity.tombstone== 0}">
											正常
										</c:when>
										<c:otherwise>
											冻结
										</c:otherwise>
									</c:choose>
								</td>
								<td><a href="${path }/sys/tenant/user/sysAdmin/list?tenantId=${entity.id}&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">${entity.usersCount}</a></td>
								<td class="Left_alignment">
									<a href="${path }/sys/tenant/tenant/privilegeManage/${entity.id}?tenantName=${entity.name }&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" >授权</a>
									<c:if test="${entity.tombstone == 0}">
										| <a href="javascript:void(0)" onclick="del('${entity.id }')">冻结</a>
									</c:if>
									<c:if test="${entity.tombstone == 1}">
										| <a href="javascript:void(0)" onclick="activate('${entity.id }')">激活</a>
									</c:if>
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
<script type="text/javascript" src="${path}/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${path}/js/bootstrap-datepicker.zh-CN.js"></script>
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
		window.location.href ="${path}/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum="+pageNum+"&pageSize="+pageSize+"&name=" + encodeURI(encodeURI("${name}"))+"&tombstone="+$("#tombstone").val();
				+"&start=${param.start}&end=${param.end}&status=${param.status}";
	}

	function del(id){
		actionByConfirm1('',"${path}/sys/tenant/tenant/delete/"+id,null,function(json){
			if(json.code == 1){
				showTip("提示","操作成功");
				setTimeout(function(){
					window.location.href ="${path}/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&name=" + encodeURI(encodeURI("${name}"))
							+"&start=${param.start}&end=${param.end}&status=${param.status}";
				},1500);

			}else{
				showTip("提示", json.message, 2000);
			}
		},"冻结")
	}

	function activate(id){
		actionByConfirm1('',"${path}/sys/tenant/tenant/activate/"+id,null,function(json){
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
