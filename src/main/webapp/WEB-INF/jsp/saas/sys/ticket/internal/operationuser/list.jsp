<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cloud" uri="/WEB-INF/tld-management/management-function.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>  
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
<!-- END PAGE LEVEL STYLES -->
<title>运维人员信息</title>
</head>
<body>

			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12 responsive">
						<%-- 表格开始 --%>
						<div class="portlet box grey">
							<div class="portlet-title" style="vertical-align: middle;">
								<div class="caption">运维人员信息</div>
								<div class="btn-group fr">
									<a id="sample_editable_1_new" class="btn green" href="${path }${modualBasePath}/add">
										<i class="icon-plus"></i>添加 
									</a>
								</div>
							</div>
					
							<div class="portlet-body">
								<table class="table table-striped table-bordered table-hover dataTable table-set">
									<thead>
										<tr>
											<th width="10%">运维人员账号</th>
											<th width="10%">运维人员姓名</th>
											<th width="10%">职位</th>
											<th width="10%">工作经验</th>
											<th width="10%">技能</th>
											<th width="10%">资格证书</th>
											<th width="200px;">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pager.datas}" var="info">
											<tr style="text-overflow:ellipsis;">
												<td><c:out value="${info.user.username }"></c:out></td>
												<td><c:out value="${info.user.realname }"></c:out></td>
												<td title="<c:out value="${info.position }"></c:out>"><c:out value="${info.position }"></c:out> </td>
												<td title="<c:out value="${info.experience }"></c:out>"><c:out value="${info.experience }"></c:out> </td>
												<td title="<c:out value="${info.skill }"></c:out>"><c:out value="${info.skill }"></c:out> </td>
												<td title="<c:out value="${info.certificate }"></c:out>"><c:out value="${info.certificate }"></c:out> </td>
												<td class="Left_alignment">
													<a href="${path }${modualBasePath}/edit/${info.id}" >修改</a> |
													<a href="javascript:del('${info.id }')">删除</a>
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
	<script type="text/javascript">
		(function(){
			App.init();
		})();
	
		function pagehref (pageNum ,pageSize){
			window.location.href ="${path}${modualBasePath}/list?pageNum="+pageNum+"&pageSize="+pageSize;
		}
		
		function searchSubmit(){
			document.searchForm.submit();
		}
		
		function del(id){
			actionByConfirm1('',"${path}${modualBasePath}/delete/"+id,null,function(json){
				if(json.code == 1){
					showTip("提示","操作成功");
					setTimeout(function(){
						window.location.href = "${path}${modualBasePath}/list";
					},1500);
					
				}else{
					showTip("提示", json.message, 2000);	
				}
			},"删除")
		}
	</script>
</body>
</html>
