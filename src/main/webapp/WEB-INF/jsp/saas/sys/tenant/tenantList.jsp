<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hisun.base.auth.Constants" %>
<%@include file="../../../inc/servlet.jsp" %>
<%@include file="../../../inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />

<title>组织列表</title>

<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<meta content="" name="description" />

<meta content="" name="author" />

<!-- BEGIN PAGE LEVEL STYLES -->

<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/select2_metro.css" />

<link rel="stylesheet" href="<%=path%>/css/DT_bootstrap.css" />


<style type="text/css">
.mybodyfont{
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif !important;
}

.mycontent {
	margin-top: 20px;
}

.mylabel {
	font-size: 16px;
	cursor: default;
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif;
}

.mybutton-group {
	text-align: right;
}

.mybutton {
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif;
	font-size: 16px;
}

.btn {
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif !important;
}

table {
	border-collapse:collapse !important;
}
</style>
<!-- END PAGE LEVEL STYLES -->

</head>
<body>
	<div class="alert alert-success hide" id="alertSuccess" style="display:none;" >
		<button class="close" data-dismiss="alert"></button>
		操作成功!
	</div>
	<div class="alert alert-error hide" id="alertErr" style="display:none;" >
		<button class="close" data-dismiss="alert"></button>
		出错了请联系管理员!
	</div>
			<div id="myModal" class="modal hide fade">

				<div class="modal-header">

					<button data-dismiss="modal" class="close" type="button"></button>

					<h3 id="modalTitle">添加组织</h3>

				</div>

				<div class="modal-body">

				</div>

			</div>

			<!-- end 新增组织的模态面板 -->
			<!-- begin 分配用户的模态面板 -->

				<div id="myModal2" class="modal hide fade">
	
					<div class="modal-header">
	
						<button data-dismiss="modal" class="close" type="button"></button>
	
						<h3 id="modalTitle2">分配用户</h3>
	
					</div>
	
					<div class="modal-body">
	
						<form action="<%=path%>/sys/org/setUser" class="form-horizontal myform"
							id="submit_form2" method="post">
							<div class="tab-pane active" id="tab2">
								<div>
	
									<div class="control-group">
	
										<label class="control-label mylabel">选择用户
										</label>
	
										<div class="controls">
											<input type="hidden" id="orgId" name="orgId" class="myinput"/>
											<input type="hidden" id="userIds" name="userIds" class="myinput"/>
											<select class="span6 m-wrap" multiple name="userSelection" id="userSelection">
												<option value="" id="noUserSelection">请选择...</option>
											</select>
	
										</div>
	
									</div>
	
									<div class="control-group mybutton-group">
										<button id="submitBtn2" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
										<button type="button" class="btn btn-default" 
							               data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
							            </button>
									</div>
	
								</div>
							</div>
						</form>
	
					</div>
	
				</div>
	
				<!-- end 分配用户的模态面板 -->

			<!-- BEGIN PAGE CONTAINER-->

			<div class="container-fluid">
				
				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->

					<div class="portlet box grey mycontent">

						<div class="portlet-title">

							<div class="caption">
								组织列表
							</div>
							<div class="clearfix fr">
								<a href="javascript:;" onclick="addTenant();" class="btn green">
									 <i class="icon-plus"></i> 添加
								</a>
							</div>
						</div>
						<div class="portlet-body">
							<shiro:hasPermission name="<%=Constants.ADMIN_PERMISSION %>">
								<div id="query" class="clearfix form-horizontal">
										<form <%-- action="${path}/sys/tenant/searchTenant" --%> id="getTenantForm" style="margin: 0 0 10px;" method="get">
											<input type="text" name="tenant_Name" id="tenant_Name" style="margin:0px;" placeholder="输入组织名进行搜索" value="${tenant_Name }" />
											<button type="button"  id="searchButton" class="btn Short_but">搜索</button>
										</form>
								</div>
							</shiro:hasPermission>
							<table
								class="table table-striped table-bordered table-hover table-full-width"
								id="sample_2">

								<thead>

									<tr>

										<th>组织名</th>
										<!-- <th>类别</th> -->
										<th>性质</th>
										<!-- <th>创建人</th> -->
										<th>创建时间</th>
										<th>是否注销</th>
										<th>排序</th>
										<th width="120px">操作</th>

									</tr>

								</thead>
								<tbody>
									<c:forEach items="${requestScope.pager.datas}" var="tenant"
										varStatus="status">
										<tr style="height: 50px;">
											<td title="<c:out value="${tenant.tenantName}"></c:out>"><c:out value="${tenant.tenantName}"></c:out></td>
											<%-- <td title="<c:out value="${tenant.typeStr}"></c:out>"><c:out value="${tenant.typeStr}"></c:out></td> --%>
											<td title="<c:out value="${tenant.property}"></c:out>">
											
												<c:choose>
													<c:when test="${tenant.property=='0'}">
														<c:out value="运营商"></c:out>
													</c:when>
													<c:when test="${tenant.property=='1'}">
														<c:out value="服务提供商"></c:out>
													</c:when>
													<c:otherwise>
														<c:out value="客户"></c:out>
													</c:otherwise>
												</c:choose>
											</td>
											<%-- <td title="<c:out value="${tenant.createUser}"></c:out>"><c:out value="${tenant.createUser}"></c:out></td> --%>
											<td title="<c:out value="${tenant.createTimeStr}"></c:out>"><c:out value="${tenant.createTimeStr}"></c:out></td>
											<td>
													<c:choose>
														<c:when test="${tenant.status}"><c:out value="是"></c:out></c:when>
														<c:otherwise><c:out value="否"></c:out></c:otherwise>
													</c:choose>
											</td>
											<td><c:out value="${tenant.sort }"></c:out> </td>
											<td title="" class="Left_alignment">
												<a href="javascript:;" onclick="updateTenant('${tenant.id}');" class="" value="<c:out value="${tenant.id}"></c:out>" itemProperty="<c:out value="${tenant.property}"></c:out>" itemType="<c:out value="${tenant.type}"></c:out>">
													编辑
												</a>
												<%-- |
												<a href="javascript:void(0);" class="remove" value="<c:out value="${tenant.id}"></c:out>" itemName="<c:out value="${tenant.tenantName}"></c:out>">
													删除
												</a> --%>
												<shiro:hasPermission name="<%=Constants.ADMIN_PERMISSION %>">
													|<a href="javascript:void(0);" class="logoff" value="<c:out value="${tenant.id}"></c:out>" itemName="<c:out value="${tenant.tenantName}"></c:out>">
														注销
													</a>
												</shiro:hasPermission>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<c:if test="${pager.pageCount>0}">
								<jsp:include page="../../../common/page.jsp">
									<jsp:param value="${pager.total}" name="total" />
									<jsp:param value="${pager.pageCount}" name="endPage" />
									<jsp:param value="${pager.pageNum}" name="page" />
									<jsp:param value="${pager.pageSize}" name="pageSize" />
								</jsp:include>
							</c:if>
						</div>

					</div>

					<!-- END EXAMPLE TABLE PORTLET-->

				</div>

				<!-- END PAGE CONTENT-->

			</div>

			<!-- END PAGE CONTAINER-->

		</div>

		<!-- END PAGE -->

	</div>

	<script type="text/javascript" src="<%=path%>/js/select2.min.js"></script>

	<script type="text/javascript"
		src="<%=path%>/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/js/DT_bootstrap.js"></script>

	<!-- END PAGE LEVEL PLUGINS -->

	<script>
		
		jQuery(document).ready(function() {
			
			App.init();//必须，不然导航栏及其菜单无法折叠

			$("#cancel").on("click",function(event){
				event.stopPropagation();//阻止冒泡
				document.getElementById("addForm").reset();
				$("#id").val("");
				$(".control-group").removeClass("error").removeClass("success");
				$(".help-inline").remove();
				$("#modal").hide();
				$("#add").hide();
			});
			
			$("#close").on("click",function(event){
				event.stopPropagation();//阻止冒泡
				document.getElementById("addForm").reset();
				$("#id").val("");
				$(".control-group").removeClass("error").removeClass("success");
				$(".help-inline").remove();
				$("#modal").hide();
				$("#add").hide();
			});
			
			$('#myModal').on('hide.bs.modal', function () {
				initModal();
			});
			$('#myModal2').on('hide.bs.modal', function () {
				$('.userSelections').remove();
				$("#submitBtn2").removeAttr("disabled");
				$("#noUserSelection").removeAttr("selected");
			});
			
			$("#searchButton").on("click",function(){
				$.get("<%=path%>/sys/tenant/ajax/list?pId="+$('#pId').val()+"&tenantName=" +$('#tenant_Name').val(),function(data){
					$("#tenantList").html(data);
				},"html");
			});
			
			$('.edit').click(function () {
				var id = $(this).attr("value");
				var property = $(this).attr("itemProperty");
				var type = $(this).attr("itemType");
				$.get("<%=path%>/sys/tenant/load", {"id" : id, "property" : property, "type" : type}, function(data,status){
					if (status == "success") {
						if (data.vo != null) {
							$('#id').val(data.vo.id);
							$('#tenantName').val(data.vo.tenantName);
							$('#createTimeStr').val(data.vo.createTimeStr);
							$('#property').val(data.vo.property);
							$('#type').val(data.vo.type);
						}
						$("#modalTitle").html("修改组织");
						
						/* var propertySelection = $('#property');
						$.each(data.propertySelections, function(index,element) {
							propertySelection.append("<option class='selections' value='" + element.privilegeCode + "' " + element.selected + " >" + element.detail + "</option>");
						});
						var typeSelection = $('#type');
						$.each(data.typeSelections, function(index,element) {
							typeSelection.append("<option class='selections' value='" + element.privilegeCode + "' " + element.selected + " >" + element.detail + "</option>");
						}); */
						
						$('#myModal').modal({
							keyboard: false,
							backdrop: 'static'
						});
					}
				});
			});
			
			$('.remove').click(function () {
				var id = $(this).attr("value");
				var itemName = $(this).attr("itemName");
				actionByConfirm1(itemName, "<%=path%>/sys/tenant/delete/" + id, function(data,status){
					if (status == "success") {
						location.reload();
					}
				});
				
			});
			
			$('.logoff').click(function () {
				var id = $(this).attr("value");
				var itemName = $(this).attr("itemName");
				actionByConfirm1(itemName, "<%=path%>/sys/tenant/logoff/" + id,{}, function(data,status){
					if (status == "success") {
						window.location.href='${path}/sys/tenant/list/tree?tenantId='+$('#pId').val();
					}
				},"注销");
				
			});
			
			$('#submitBtn2').click(function () {
				var userIds = "";
				$(".userSelections").each(function(index, item) {
					if ($(item).attr("selected") == "selected") {
						userIds = userIds + $(item).attr("value") + ";"
					}
				});
				$("#userIds").val(userIds);
				$('#submit_form2').ajaxSubmit({
	            	beforeSend : function() {
						$("#submitBtn2").html("保存中...");
						$("#submitBtn2").attr("disabled", "disabled");
					},
					success : function(data) {
						if (data == "true") {
							$("#submitBtn2").html("保存成功");
							showSaveSuccess();
							
							setTimeout("$('#myModal2').modal('hide');",1000);
							setTimeout("location.reload();",1300);
							
						}
						
					}
	            }); 
				
			});
					
		});
		
		function initModal() {
			$("#modalTitle").html("新增组织");
			$("#submitBtn").html("<i class='icon-ok'></i>保存");
			$(".help-inline").html("");
			$("#submitBtn").removeAttr("disabled");
			$('.myinput').val("");
			$('.selections').remove();
			$('.control-group').removeClass('error success');
		}
		
		function pagehref(pageNum, pageSize) {
			//var url = location.href;
			//var length = url.indexOf("?");
			//var tenant_Name = $("#tenant_Name").val();
			//length = length > 0 ? length : url.length;
			//alert(url.substring(0, length) + "?pageNum=" + pageNum  + "&pageSize=" + pageSize);
			//location.href=url.substring(0, length) + "?pageNum=" + pageNum  + "&pageSize=" + pageSize + "&tenant_Name=" + tenant_Name;
			$("#tenantList").load("${path}/sys/tenant/ajax/list?pId="+$('#pId').val()+"&pageNum=" + pageNum  + "&pageSize=" + pageSize);
		}
	</script>
</body>
</html>
