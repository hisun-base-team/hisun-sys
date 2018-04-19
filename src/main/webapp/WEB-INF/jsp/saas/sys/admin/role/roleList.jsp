<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />

<title>角色列表</title>

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

.select2-drop {
	  z-index: 999999 !important;
}
</style>
<!-- END PAGE LEVEL STYLES -->

</head>
<body>

			<!-- end 新增角色的模态面板 -->
			<!-- begin 授权的模态面板 -->

				<div id="myModal2" class="modal hide fade">
	
					<div class="modal-header">
	
						<button data-dismiss="modal" class="close" type="button"></button>
	
						<h3 id="modalTitle2">授权</h3>
	
					</div>
	
					<div class="modal-body">
	
						<form action="<%=path%>/sys/admin/role/setAuth" class="form-horizontal myform"
							id="submit_form2" method="post">
							<div class="tab-pane active" id="tab2">
								<div>
	
									<div class="control-group">
	
										<label class="control-label mylabel">资源
										</label>
	
										<div class="controls">
											<input type="hidden" id="roleId" name="roleId" class="myinput"/>
											<input type="hidden" id="resIds" name="resIds" class="myinput"/>
											<div class="treeDemoDiv">
												<div id="treeDemo" class="ztree"></div>
											</div>
	
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
	
			<div class="container-fluid">
				
				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->

					<div class="">
						<div class="portlet-title" style="border-bottom: 0px; margin-bottom: 0px;">
							<div class="caption">
								角色列表
							</div>
								<div class="clearfix fr">
									<shiro:hasPermission name="admin-platform:role_add">
										<a href="javascript:;" onclick="add();" class="btn green">
											 <i class="icon-plus"></i> 添加
										</a>
									</shiro:hasPermission>
								</div>
						</div>
						<div class="clearfix">

							<div class="control-group">


								<div id="query" style="float: left;">
									<form id="getUserForm" style="margin: 0 0 10px;" method="get">

										<input type="text" name="searchContent" id="searchContent" style="margin:0px;" class="m-wrap" placeholder="角色名" value="${cloud:htmlEscape(searchContent)}" />
										<button type="button" class="btn Short_but" id="searchButton">搜索</button>
										<button type="button" class="btn Short_but" id="reset" onclick="searchReset();">清空</button>
									</form>
								</div>
							</div>

						</div>
						<div class="portlet-body">
							<table
								class="table table-striped table-bordered table-hover table-full-width"
								>

								<thead>

									<tr>

										<th>角色名</th>
										<th>角色代码</th>
										<th>排序</th>
										<th>描述</th>
										<th width="160px" style="text-align: center;">操作</th>

									</tr>

								</thead>
								<tbody>
									<c:forEach items="${requestScope.pager.datas}" var="role"
										varStatus="status">
										<c:choose>
											<c:when test="${username eq 'admin'}">
												<tr style="height: 50px;">
													<td title="<c:out value="${role.roleName}"></c:out>"><c:out value="${role.roleName}"></c:out></td>
													<td>
														<c:out value="${role.roleCode}"></c:out>
													</td>
													<td><c:out value="${role.sort}"></c:out></td>
													<td><c:out value="${role.description}"></c:out></td>
														<%-- <td><c:out value="${role.orgName }"></c:out></td> --%>
													<td title=""  class="Left_alignment" style="text-align: center;">
														<shiro:hasPermission name="admin-platform:role_update">
															<c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">
																<a href="javascript:;" onclick="update('${role.id}');" class="" value="<c:out value="${role.id}"></c:out>" itemProperty="<c:out value="${role.property}"></c:out>">
																	编辑
																</a>|
															</c:if>
														</shiro:hasPermission>
															<a href="${path}/sys/admin/role/authorization/${role.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="" value="<c:out value="${role.id}"></c:out>" >
																授权
															</a><c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">|</c:if>
														<c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">
															<shiro:hasPermission name="admin-platform:role_delete">
																<a href="javascript:void(0);" class="remove" value="<c:out value="${role.id}"></c:out>" itemName="<c:out value="${role.roleName}"></c:out>">
																	删除
																</a>|
															</shiro:hasPermission>
															<shiro:hasPermission name="admin-platform:role_member">
																<a href="${path}/sys/admin/role/member/${role.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="member" value="<c:out value="${role.id}"></c:out>" itemName="<c:out value="${role.id}"></c:out>">
																	成员
																</a>
															</shiro:hasPermission>
														</c:if>
													</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">
													<tr style="height: 50px;">
														<td title="<c:out value="${role.roleName}"></c:out>"><c:out value="${role.roleName}"></c:out></td>
														<td>
															<c:out value="${role.roleCode}"></c:out>
														</td>
														<td><c:out value="${role.sort}"></c:out></td>
														<td><c:out value="${role.description}"></c:out></td>
															<%-- <td><c:out value="${role.orgName }"></c:out></td> --%>
														<td title=""  class="Left_alignment" style="text-align: center;">
															<shiro:hasPermission name="admin-platform:role_update">
																<c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">
																	<a href="javascript:;" onclick="update('${role.id}');" class="" value="<c:out value="${role.id}"></c:out>" itemProperty="<c:out value="${role.property}"></c:out>">
																		编辑
																	</a>|
																</c:if>
															</shiro:hasPermission>
															<a href="${path}/sys/admin/role/authorization/<c:out value="${role.id}"></c:out>" class="" value="<c:out value="${role.id}"></c:out>" >
																授权
															</a><c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">|</c:if>
															<c:if test="${role.roleCode ne 'ROLE_ADMINISTRATOR'}">
																<shiro:hasPermission name="admin-platform:role_delete">
																	<a href="javascript:void(0);" class="remove" value="<c:out value="${role.id}"></c:out>" itemName="<c:out value="${role.roleName}"></c:out>">
																		删除
																	</a>|
																</shiro:hasPermission>
																<shiro:hasPermission name="admin-platform:role_member">
																	<a href="${path}/sys/admin/role/member/<c:out value="${role.id}"></c:out>" class="member" value="<c:out value="${role.id}"></c:out>" itemName="<c:out value="${role.id}"></c:out>">
																		成员
																	</a>
																</shiro:hasPermission>
															</c:if>
														</td>
													</tr>
												</c:if>
											</c:otherwise>

										</c:choose>
									</c:forEach>
								</tbody>
							</table>
							<c:if test="${pager.pageCount>0}">
								<jsp:include page="/WEB-INF/jsp/common/page.jsp">
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

	<!-- END CONTAINER -->

	<script type="text/javascript" src="<%=path%>/js/select2.min.js"></script>

	<script type="text/javascript"
		src="<%=path%>/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/js/DT_bootstrap.js"></script>

	<script>
		var searchReset =function (){
			$("#searchContent").removeAttrs("value");
		};
	var add = function(){
		location.href="${path}/sys/admin/role/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
	}
	
	var update = function(role){
		location.href="${path}/sys/admin/role/update/"+role+"?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
	}
	var resTree;
	var resTree1;
		jQuery(document).ready(function() {

			App.init();//必须，不然导航栏及其菜单无法折叠

			$("#searchButton").on("click",function(){
				window.location.href="<%=path%>/sys/admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&searchContent=" +$('#searchContent').val();
			});

			$('#addRoleBtn').click(function () {
				$('#myModal').modal({
					keyboard: false,
					backdrop: 'static'
				});
			});
			
			$('.edit').click(function () {
				var id = $(this).attr("value");
				var property = $(this).attr("itemProperty");
				$.get("<%=path%>/sys/admin/role/load", {"id" : id}, function(data,status){
					if (status == "success") {
						$('#id').val(data.vo.id);
						$('#roleName').val(data.vo.roleName);
						$("#modalTitle").html("修改角色");
						
						$("#roleCode").val(data.vo.roleCode)
						
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
				actionByConfirm1(itemName, "<%=path%>/sys/admin/role/delete/" + id, function(data,status){
					if (status == "success") {
						location.reload();
					}
				});
				
			});
			
			$('.grant').click(function () {
				var id = $(this).attr("value");
				$('#roleId').val(id);
				
				$.get("<%=path%>/sys/admin/role/resource/" + id, function(data,status){
					if (status == "success") {
						//var authName = data.data.authName;
						var resIdList = data.data;
						//var resIdList = data.data.resIdList;
						
						//$('#id').val(id);
						//$('#authName').val(authName);
						if (resTree != null && resIdList != null) {
							if (resIdList.length > 0) {//初始化选中状态时，是不联动的，而根节点的选中没保存进数据库，所以要手动判断是否有节点选中然后补上根节点
								var node = resTree.getNodeByParam("id", "1", null);
								resTree.checkNode(node, true, false);
							}
							$.each(resIdList, function(index, resId) {
								var node = resTree.getNodeByParam("id", resId, null);
								resTree.checkNode(node, true, false);
							});						
						}
						$('#myModal2').modal({
							keyboard: false,
							backdrop: 'static'
						});
					}
				});
					
			});
			
			$('#submitBtn3').click(function () {
				/* var authIds = "";
				$(".authSelections").each(function(index, item) {
					if ($(item).attr("selected") == "selected") {
						authIds = authIds + $(item).attr("value") + ";"
					}
				}); */
				//$("#authIds").val(authIds);
				if (resTree != null) {//获取资源树选中的节点
					var nodes = resTree1.getCheckedNodes(true);
					var resIdsObj = $('#resIds1');
					$.each(nodes, function(index, node) {
						resIdsObj.val(resIdsObj.val() + node.id + ";");
					});
					$('#submit_form3').ajaxSubmit({
		            	beforeSend : function() {
							$("#submitBtn3").html("保存中...");
							$("#submitBtn3").attr("disabled", "disabled");
						},
						success : function(data) {
							if (data.success) {
								$("#submitBtn3").html("保存成功");
								showSaveSuccess();
								
								setTimeout("$('#myModal3').modal('hide');",1000);
								setTimeout("location.reload();",1300);
								
							}
							
						}
		            }); 
				}
				
			});
			$('#submitBtn2').click(function () {
				/* var authIds = "";
				$(".authSelections").each(function(index, item) {
					if ($(item).attr("selected") == "selected") {
						authIds = authIds + $(item).attr("value") + ";"
					}
				}); */
				//$("#authIds").val(authIds);
				if (resTree != null) {//获取资源树选中的节点
					var nodes = resTree.getCheckedNodes(true);
					var resIdsObj = $('#resIds');
					$.each(nodes, function(index, node) {
						resIdsObj.val(resIdsObj.val() + node.id + ";");
					});
					$('#submit_form2').ajaxSubmit({
		            	beforeSend : function() {
							$("#submitBtn2").html("保存中...");
							$("#submitBtn2").attr("disabled", "disabled");
						},
						success : function(data) {
							if (data.success) {
								$("#submitBtn2").html("保存成功");
								showSaveSuccess();
								
								setTimeout("$('#myModal2').modal('hide');",1000);
								setTimeout("location.reload();",1300);
								
							}
							
						}
		            }); 
				}
				
			});
					
			$('#submitBtn4').click(function () {
				var datas = $("#select2_sample6").select2("val");
				$.ajax({
					url : "${path }/sys/admin/role/update/"+$('#roleId2').val(),
					type : "POST",
					data : {"orgId":$("#select2_sample6").select2("val")},
					dataType : "json",
					success : function(json){
						if(!json){
							showTip("提示","修改组织失败!",2000);
						}else{
							showTip("提示","修改组织成功!",2000);
							setTimeout("$('#myModal4').modal('hide');",1000);
							window.location.reload();
						}
					}
				});
				
			});
			
		});
		
		function initModal() {
			$("#modalTitle").html("新增角色");
			$("#submitBtn").html("<i class='icon-ok'></i>保存");
			$("#submitBtn").removeAttr("disabled");
			$('.myinput').val("");
			$(".help-inline").html("");
			$('.selections').remove();
			$('.control-group').removeClass('error success');
		}
		
		function pagehref(pageNum, pageSize) {
			var url = location.href;
			var length = url.indexOf("?");
			var searchContent = $("#searchContent").val();
			length = length > 0 ? length : url.length;
			location.href=url.substring(0, length) + "?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum=" + pageNum  + "&pageSize=" + pageSize + "&searchContent=" +searchContent;
		}
	</script>
</body>
</html>
