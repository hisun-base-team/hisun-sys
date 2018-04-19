<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
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
					<div class="caption">字典项列表</div>
				</div>

			</div>

			<div class="portlet-body fuelux">
				<div class="clearfix">
									<div class="btn-group">
										<a id="sample_editable_1_new" class="btn green" href="javascript:;" onclick="addResource();">
											<i class="icon-plus"></i>字典项创建
										</a>
									</div>
								</div>
				<table
					class="table table-striped table-bordered table-hover dataTable table-set">
					<thead>
						<tr>
							<!-- <th>字典类型名</th> -->
							<th>字典项名</th>
							<th>字典项值</th>
							<th>排序</th>
							<th>备注</th>
							<th width="15%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pager.datas}" var="items">
							<tr>
								<%-- <td><c:out value="${items.dictionaryType.name }"></c:out></td> --%>
								<td>
									<c:out value="${items.item }"></c:out>								
								</td>
								<td><c:out value="${items.value }"></c:out></td>
								<td><span class=""><c:out value="${items.sort }"></c:out> </span></td>
								<td><c:out value="${items.remark }"></c:out> </td>
								<td><a href="javascript:;" class="btn mini blue "  onclick="updateResource('${items.id}');"><i class="icon-pencil"></i>&nbsp;修&nbsp;改</a>
								&nbsp;&nbsp;&nbsp;
								<a href="javascript:;" class="btn mini red" id="${items.id}" itemname="<c:out value="${items.item }"></c:out>" onclick="delResource(this);"><i class="icon-trash"></i>&nbsp;删&nbsp;除</a>
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
			actionByConfirm1(itemName, "${path}/sys/admin/dictionary/item/delete/" + id,{} ,function(data,status){
				if (status == "success") {
					refreshTree();
					$("#listResource").load("${path}/sys/admin/dictionary/item/ajax/list?pId="+$("#pId").val());
				}
			});
		};
	
		var addResource = function(){
			$.get("${path}/sys/admin/dictionary/item/max/sort/",{"pId":$("#pId").val()},function(data){
				$("#sort").val(data.maxSort);
				modal.show();
				add.show();
			});
			$.get("<%=path%>/sys/admin/dictionary/item/select", function(data,status){
				if (status == "success") {
					var selection = $('#type');
					selection.empty();
					//selection.append("<option value=''>请选择...</option>");
					$.each(data.data, function(index,element) {
						selection.append("<option class='selections' value='" + element.id + "' " + element.selected + " >" + element.name + "</option>");
					});
					
				} 
			});
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