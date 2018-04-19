<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
	<div class="alert alert-success hide" id="alertSuccess" style="display:none;" >
		<button class="close" data-dismiss="alert"></button>
		操作成功!
	</div>
	<div class="alert alert-error hide" id="alertErr" style="display:none;" >
		<button class="close" data-dismiss="alert"></button>
		出错了请联系管理员!
	</div>
	<div class="span6" style="width: 100%; margin: 0px;">

		<div class="portlet box grey">
			<div style="margin: 0px; padding: 0px;">
				<div class="portlet-title" style="vertical-align: middle;">
					<div class="caption">资源列表</div>
					<div class="clearfix fr">
						<a id="sample_editable_1_new" class="btn green" href="javascript:;" onclick="addResource();">
							<i class="icon-plus"></i> 添加
						</a>
					</div>
				</div>

			</div>

			<div class="portlet-body fuelux">
				<table
					class="table table-striped table-bordered table-hover dataTable table-set">
					<thead>
						<tr>
							<th>资源名</th>
							<th>类型</th>
							<th>图标</th>
							<th>排序</th>
							<th>URL路径</th>
							<th>权限字符串</th>
							<th>可用</th>
							<th width="80px">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pager.datas}" var="resources">
							<tr>
								<td><c:out value="${resources.resourceName }"></c:out></td>
								<td>
									<c:choose>
										<c:when test="${resources.resourceType==0}"><c:out value="菜单"></c:out></c:when>
										<c:when test="${resources.resourceType==1}"><c:out value="操作"></c:out></c:when>
										<c:otherwise><c:out value="系统"></c:out></c:otherwise>
									</c:choose> 
								
								</td>
								<td><c:out value="${resources.resourceIcon }"></c:out>&nbsp;&nbsp;<i class='<c:out value="${resources.resourceIcon }"></c:out>'></i></td>
								<td><span class=""><c:out value="${resources.sort }"></c:out> </span></td>
								<td><c:out value="${resources.url }"></c:out> </td>
								<td><c:out value="${resources.permission }"></c:out> </td>
								<td>
									<c:choose>
										<c:when test="${resources.status==0}"><c:out value="是"></c:out></c:when>
										<c:otherwise><c:out value="否"></c:out></c:otherwise>
									</c:choose>
								</td>
								<td><a href="javascript:;" class=""  onclick="updateResource('${resources.id}');">编辑</a>
								<a href="javascript:;" class="" id="${resources.id}" itemname="<c:out value="${resources.resourceName }"></c:out>" onclick="delResource(this);">删除</a>
									<input type="hidden" name="delId" id="delId" >
									</td>
									
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="/WEB-INF/jsp/common/page.jsp">
					<jsp:param value="${pager.total }" name="total" />
					<jsp:param value="${pager.pageCount }" name="endPage" />
					<jsp:param value="${pager.pageNum }" name="page" />
					<jsp:param value="${pager.pageSize }" name="pageSize"/>
				</jsp:include>
			</div>

		</div>

<div class="modal-scrollable" style="z-index: 10050;display: none;" id="del1">
	<div id="static" class="modal hide fade in" tabindex="-1"
		data-backdrop="static" data-keyboard="false" style="display: block;">

		<div class="modal-body">

			<p>该树节点还有子节点，不能删除!</p>

		</div>

		<div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn" id="delCancel1">关闭</button>
		</div>

	</div>

</div>

	</div>

	<script type="text/javascript">
		var add = $("#add");
		var modal = $("#modal");
		var del = $("#del");
		var delId = $("#delId");
		var delResource = function(obj){
			var id = obj.id;
			var itemName = $(obj).attr("itemname");
			actionByConfirm1(itemName, "${path}/sys/admin/resource/delete/" + id,{} ,function(data,status){
				if (status == "success") {
					if(data.success ==true){
						refreshTree();
						//$("#listResource").load("${path}/sys/admin/resource/sitemesh/list?pId="+$("#pId").val());
						$.ajax({
							async : false,
							cache:false,
							type: 'GET',
							dataType : "html",
							url: "${path}/sys/admin/resource/sitemesh/list?pId="+$("#pId").val(),// 请求的action路径
							headers: {
								"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
							},
							error: function () {// 请求失败处理函数
								alert('请求失败');
							},
							success:function(html){
								$("#listResource").html(html);
							}
						});
					}else{
						showTip("提示",data.message, null);
					}
				}
			});
		};
	
		var addResource = function(){
			localPost("${path}/sys/admin/resource/max/sort/",{"pId":$("#pId").val()},function(data){
				$("#sort").val(data.maxSort);
				modal.show();
				add.show();
			},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		}
		$(function(){
			
			$("#delCancel #close").on("click",function(){
				del.hide();
				modal.hide();
			});
			
			$("#delCancel1").on("click",function(){
				$("#del1").hide();
				modal.hide();
			});
		});
		
	</script>