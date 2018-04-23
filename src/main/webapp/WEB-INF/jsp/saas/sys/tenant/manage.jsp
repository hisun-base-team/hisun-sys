<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../inc/servlet.jsp" %>
<%@include file="../../../inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织管理</title>
<!-- BEGIN PAGE LEVEL STYLES -->

<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/select2_metro.css" />

<link rel="stylesheet" href="<%=path%>/css/DT_bootstrap.css" />
<link href="<%=path%>/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
.mybodyfont{
	font-family: 'Helvetica Neue', Helvetica, 'Hiragino Sans GB', 'Segoe UI',
		'Microsoft Yahei', Tahoma, Arial, STHeiti, sans-serif !important;
}

.mycontent {
	margin-top: 20px;
	border: 0px !important;
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

			<div class="container-fluid">
				
				<div class="row-fluid profile">

					<div class="span12">

						<!--BEGIN TABS-->

						<div class="tabbable tabbable-custom tabbable-full-width">

							<ul class="nav nav-tabs">

							<!-- 	<li class="active"><a href="#tab_1_1" data-toggle="tab">Overview</a></li>-->

								<li class="active"><a href="#tab_1_2" data-toggle="tab">基本信息</a></li> 
								<shiro:hasRole name="ROLE_MEMBER"> 
								<%-- <c:if test="${org.property eq '2'}"> --%>
									<li><a href="#tab_1_3" data-toggle="tab">服务商管理</a></li>
								<%-- </c:if> --%>
								</shiro:hasRole>
								<shiro:hasAnyRoles name="ROLE_CARRIERS,ROLE_CARRIERS"> 
								<%-- <c:if test="${org.property ne '2'}"> --%>
									<li><a href="#tab_1_4" data-toggle="tab">客户管理</a></li>
								<%-- </c:if> --%>
							    </shiro:hasAnyRoles>
								<!-- <li><a href="#tab_1_4" data-toggle="tab">Projects</a></li> -->

							<!-- 	<li><a href="#tab_1_6" data-toggle="tab">Help</a></li> -->

							</ul>

							<div class="tab-content">
 
								<div class="tab-pane profile-classic row-fluid active" id="tab_1_2">

									<div class="portlet box grey mycontent">

					

						<!-- begin 新增组织的模态面板 -->

						<div id="myModal" class="modal hide fade">
			
							<div class="modal-header">
			
								<button data-dismiss="modal" class="close" type="button"></button>
			
								<h3 id="modalTitle">添加组织</h3>
			
							</div>
			
							<div class="modal-body">
			
								<form action="<%=path%>/sys/tenant/save" class="form-horizontal myform"
									id="submit_form" method="post">
									<div class="tab-pane active" id="tab1">
										<div>
			
											<div class="control-group">
			
												<label class="control-label mylabel">组织名<span
													class="required">*</span>
												</label>
			
												<div class="controls">
													<input type="hidden" id="id" name="id" class="myinput"/>
													<input type="text" class="m-wrap myinput required" name="tenantName" id="tenantName"
														placeholder="长度为1-30位" /> <span
														class="help-inline"></span>
			
												</div>
			
											</div>
											<div class="control-group mybutton-group">
												<button id="submitBtn" type="submit" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
												<button type="button" class="btn btn-default" 
									               data-dismiss="modal"><i class='icon-remove-sign'></i> 关闭
									            </button>
											</div>
			
										</div>
									</div>
								</form>
			
							</div>
			
						</div>
			
						<div class="portlet-body">
							
							<table
								class="table table-striped table-bordered table-hover table-full-width"
								id="sample_2">

								<thead>

									<tr>

										<th>组织名</th>
										<!-- <th>类别</th> -->
										<th>性质</th>
										<!-- <th>创建人</th>
										<th>创建时间</th>
										<th>是否注销</th> -->
										<th width="120px">操作</th>

									</tr>

								</thead>
								<tbody>
										<tr style="height: 50px;">
											<td title="<c:out value="${org.tenantName}"></c:out>"><c:out value="${org.tenantName}"></c:out></td>
											<%-- <td title="<c:out value="${org.typeStr}"></c:out>"><c:out value="${org.typeStr}"></c:out></td> --%>
											<td title="<c:out value="${org.property}"></c:out>">
											
												<c:choose>
													<c:when test="${org.property=='0'}">
														<c:out value="运营商"></c:out>
													</c:when>
													<c:when test="${org.property=='1'}">
														<c:out value="服务提供商"></c:out>
													</c:when>
													<c:otherwise>
														<c:out value="客户"></c:out>
													</c:otherwise>
												</c:choose>
											</td>
											<%-- <td title="<c:out value="${org.createUser}"></c:out>"><c:out value="${org.createUser}"></c:out></td>
											<td title="<c:out value="${org.operateTime}"></c:out>"><c:out value="${org.operateTime}"></c:out></td>
											<td>
													<c:choose>
														<c:when test="${org.status}"><c:out value="是"></c:out></c:when>
														<c:otherwise><c:out value="否"></c:out></c:otherwise>
													</c:choose>
											</td> --%>
											<td title=""  class="Left_alignment">
												<a href="javascript:void(0);" class="edit" value="<c:out value="${org.id}"></c:out>" itemProperty="<c:out value="${org.property}"></c:out>">
													编辑
												</a>
												<%-- <a href="javascript:void(0);" class="btn red mini remove" value="<c:out value="${org.id}"></c:out>" itemName="<c:out value="${org.type}"></c:out>">
													<i class="icon-trash"></i> 删除
												</a> --%>
												<%-- <shiro:hasPermission name="<%=Constants.ADMIN_PERMISSION %>">
													<a href="javascript:void(0);" class="btn red mini logoff" value="<c:out value="${org.id}"></c:out>" itemName="<c:out value="${org.type}"></c:out>">
														<i class="icon-trash"></i> 注销
													</a>
												</shiro:hasPermission> --%>
											</td>
										</tr>
								</tbody>
							</table>
							
						</div>

					</div>

								</div>

								<!--tab_1_2-->
						<shiro:hasRole name="ROLE_MEMBER"> 
						<%-- <c:if test="${org.property eq '2'}"> --%>
								<div class="tab-pane row-fluid profile-account" id="tab_1_3">
									<!-- begin 新增组织的模态面板 -->

						<div id="myModal3" class="modal hide fade">
			
							<div class="modal-header">
			
								<button data-dismiss="modal" class="close" type="button"></button>
			
								<h3 id="modalTitle3">新增服务商</h3>
			
							</div>
			
							<div class="modal-body">
			
								<form action="" class="form-horizontal myform"
									id="submit_form" method="post">
									<div class="tab-pane active" id="tab1">
										<div>
			
											<div class="control-group">
			
												<label class="control-label mylabel">服务商<span
													class="required">*</span>
												</label>
			
												<div class="controls">
													<input type="hidden" id="tenantid1" name="tenantid1" class="myinput" value='<c:out value="${org.id}"></c:out>'/>
													<form:select path="tenants" id="tenantsp">
														  <c:forEach var="item" items="${tenants}">
															  <option value="${item.id }">
															  	<c:out value="${item.tenantName }"></c:out>
															  </option>
														  </c:forEach>
													</form:select>
												</div>
			
											</div>
											
											<div class="control-group mybutton-group">
												<button id="submitBtn3" type="button" class="btn green mybutton"><i class='icon-ok'></i>保存</button>
												<button type="button" class="btn btn-default" 
									               data-dismiss="modal"><i class='icon-remove-sign'></i>关闭
									            </button>
											</div>
			
										</div>
									</div>
								</form>
			
							</div>
			
						</div>
			
						<!-- end 新增组织的模态面板 -->
									<div class="row-fluid">

										<div class="span12">
											
											<div class="portlet-body">
										<div class="clearfix">
			
											<div class="btn-group">
			
												<button id="addOrgBtn" class="btn green add">
			
													 <i class="icon-plus"></i> 添加服务商
			
												</button>
			
											</div>
										
										</div>
							<table
								class="table table-striped table-bordered table-hover table-full-width"
								id="sample_2">

								<thead>

									<tr>

										<th>组织名</th>
										<!-- <th>类别</th> -->
										<th>性质</th>
										<!-- <th>创建人</th> -->
									</tr>

								</thead>
								<tbody>
									<c:forEach items="${requestScope.sp.datas}" var="tenant"
										varStatus="status">
										<tr style="height: 50px;">
											<td title="<c:out value="${tenant.tenantName}"></c:out>"><c:out value="${tenant.tenantName}"></c:out></td>
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
										</tr>
										<input class="sp" type="hidden" value="${tenant.id}"/>
									</c:forEach>
								</tbody>
							</table>
							<c:if test="${sp.pageCount>0}">
								<jsp:include page="../../../common/page.jsp">
									<jsp:param value="${sp.total}" name="total" />
									<jsp:param value="${sp.pageCount}" name="endPage" />
									<jsp:param value="${sp.pageNum}" name="page" />
									<jsp:param value="${sp.pageSize}" name="pageSize" />
								</jsp:include>
							</c:if>
						</div>
						
										</div>

									</div>

								</div>
					</shiro:hasRole>
					<%-- </c:if> --%>
					
					<shiro:hasAnyRoles name="ROLE_CARRIERS,ROLE_CARRIERS"> 
					<%-- <c:if test="${org.property ne '2'}"> --%>
								<div class="tab-pane row-fluid profile-account" id="tab_1_4">
								<!-- begin 分配用户的模态面板 -->

				<div id="myModal2" class="modal hide fade" >
	
					<div class="modal-header">
	
						<button data-dismiss="modal" class="close" type="button"></button>
	
						<h3 id="modalTitle2">分配用户</h3>
	
					</div>
	
					<div class="modal-body">
	
						<form action="${path }/sys/tenant/assignment" class="form-horizontal myform"
							id="submit_form2" method="post">
							<div class="tab-pane active" id="tab2">
								<div>
	
									<div class="control-group">
	
										<label class="control-label mylabel">分派用户
										</label>
	
										<div class="controls">
											<input type="hidden" id="tenantid" name="tenantid" class="myinput"/>
											<form:select path="users" items="${users}" itemLabel="text" itemValue="id" id="select2_sample6" class="span6 select2" multiple="multiple" >
										    </form:select>
	
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
									<div class="row-fluid">

										<div class="span12">

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
										<th width="120px">操作</th>

									</tr>

								</thead>
								<tbody>
										<c:forEach items="${custom.datas}" var="tenant"
											varStatus="status">
											<tr style="height: 50px;">
												<td title="<c:out value="${tenant.tenantName}"></c:out>"><c:out value="${tenant.tenantName}"></c:out></td>
												<%-- <td title="<c:out value="${org.typeStr}"></c:out>"><c:out value="${org.typeStr}"></c:out></td> --%>
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
												<td title="<c:out value="${tenant.operateTime}"></c:out>"><fmt:formatDate value="${tenant.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
												<td>
														<c:choose>
															<c:when test="${tenant.status}"><c:out value="是"></c:out></c:when>
															<c:otherwise><c:out value="否"></c:out></c:otherwise>
														</c:choose>
												</td>
												<td title="">
													<a href="<%=path%>/sys/tenant/assignment/<c:out value="${tenant.id}"></c:out>" class="" value="<c:out value="${tenant.id}"></c:out>" itemProperty="<c:out value="${tenant.property}"></c:out>" >
														分派人员
													</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<c:if test="${custom.pageCount>0}">
									<jsp:include page="../../../common/page.jsp">
										<jsp:param value="${custom.total}" name="total" />
										<jsp:param value="${custom.pageCount}" name="endPage" />
										<jsp:param value="${custom.pageNum}" name="page" />
										<jsp:param value="${custom.pageSize}" name="pageSize" />
									</jsp:include>
								</c:if>
											<!--end span9-->                                    

										</div>

									</div>

								</div>
						</shiro:hasAnyRoles>
						<%-- </c:if> --%>
								<!--end tab-pane-->

							</div>

						</div>

						<!--END TABS-->

					</div>

				</div>
			</div>
			

	<!-- <div class="footer">

		<div class="footer-inner"></div>

		<div class="footer-tools">

			<span class="go-top"> <i class="icon-angle-up"></i>

			</span>

		</div>

	</div> -->

	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script type="text/javascript" src="<%=path%>/js/select2.min.js"></script>

	<script type="text/javascript"
		src="<%=path%>/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/js/DT_bootstrap.js"></script>
	<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
	<script type="text/javascript" src="<%=path%>/js/zTree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		
		App.init();//必须，不然导航栏及其菜单无法折叠
		FormValidation.init();
		$('#myModal').on('hide.bs.modal', function () {
			initModal();
		});
		$('#myModal2').on('hide.bs.modal', function () {
			//$('.userSelections').remove();
			$("#submitBtn2").removeAttr("disabled");
			//$("#noUserSelection").removeAttr("selected");
		});
		$('#myModal3').on('hide.bs.modal', function () {
			//$('.userSelections').remove();
			$("#submitBtn3").removeAttr("disabled");
			//$("#noUserSelection").removeAttr("selected");
		});
		
		$('#submitBtn2').click(function () {
			var datas = $("#select2_sample6").select2("val");
			$.ajax({
				url : "${path }/sys/tenant/assignment",
				type : "POST",
				data : {"tenantid":$("#tenantid").val(),
					"assignment":$("#select2_sample6").select2("val")},
				dataType : "json",
				success : function(json){
					if(!json){
						showTip("提示","分派人员失败!",2000);
					}else{
						showTip("提示","分派人员成功!",2000);
						setTimeout("$('#myModal2').modal('hide');",1000);
						//window.location.reload();
					}
				}
			});
			
		});
		
		$('#submitBtn3').click(function () {
			var datas = $("#tenantsp").val();
			var bool = false;
			$(".sp").each(function(){
				if($(this).val()===datas){
					bool = true;
					return false;
				}
			});
			if(bool){
				showTip("提示","您已经选择了该服务商,请选择其他服务商!",2000);
			}else{
				$.ajax({
					url : "${path }/sys/tenant/update",
					type : "POST",
					data : {"tenantid":$("#tenantid1").val(),
						"tenantsp":datas},
					dataType : "json",
					success : function(json){
						if(!json){
							showTip("提示","选择服务商失败!",2000);
						}else{
							//showTip("提示","分派人员成功!",2000);
							setTimeout("$('#myModal3').modal('hide');",1000);
							window.location.reload();
						}
					}
				});
			}
			
		});
		
		$('.add').click(function () {
			$('#myModal3').modal('show');
		});
		
		$('.assignment').click(function () {
			var id = $(this).attr("value");
			//var property = $(this).attr("itemProperty");
			//var type = $(this).attr("itemType");
			$("#tenantid").val(id);
			$.post("<%=path%>/sys/tenant/load/assignment", {"tenantid" : id}, function(data,status){
				if (status == "success") {
					$('#myModal2').modal('show');
					if(data.assignment===null){
						$("#select2_sample6").select2("val","");
					}else{
						$("#select2_sample6").select2("val",data.assignment.split(","));
					}
				}
			});
		});
		
		<%-- var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true,
					nocheckInherit: true
					
				}
			};
		
		$.get("<%=path%>/sys/user/select", function(data,status){
			if (status == "success") {
				resTree1 = $.fn.zTree.init($("#treeDemo1"), setting, data.data);
			}
		}); --%>
		
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
					
					$('#myModal').modal({
						keyboard: false,
						backdrop: 'static'
					});
				}
			});
		});
		 $("#select2_sample6").select2({
			  placeholder: "分派人员",
			  allowClear: true,
			  theme: "classic"
			});
		 //.select2("val",['402881ec4cc6d307014cc6d50f990000','2']);
	});	
	
	var SPSubmit = function(orgid){
		$.ajax({
			url : "${path }/sys/tenant/update",
			type : "POST",
			data : {"tenantid":orgid,
				"tenantsp":document.getElementById('tenantsp').value},
			dataType : "json",
			success : function(json){
				if(!json){
					showTip("提示","选择供应商失败!",2000);
				}else{
					showTip("提示","选择供应商成功!",2000);
					//window.location.reload();
				}
			}
		});
	};
	/* var assignment= function(orgid){
		//alert($("#select2_sample6").select2("val"));
		$.ajax({
			url : "${path }/sys/org/assignment",
			type : "POST",
			data : {"orgid":orgid,
				"assignment":$("#select2_sample6").select2("val")},
			dataType : "json",
			success : function(json){
				if(!json){
					showTip("提示","分派人员失败!",2000);
				}else{
					showTip("提示","分派人员成功!",2000);
					//window.location.reload();
				}
			}
		});
	}; */
	
	var initAssignment = function(){
		var assignments = $("#assignments").val();
		var arrayObj = new Array();
		//arrayObj.push('402881384cd62c22014cd99fa18c0000');
		arrayObj=assignments.split(",");
		$("#select2_sample6").select2("val",arrayObj);
	};
	
	var FormValidation = function() {

		return {
			//main function to initiate the module
			init : function() {

				function format(state) {
					if (!state.id)
						return state.text; // optgroup
					return "<img class='flag' src='assets/img/flags/"
							+ state.id.toLowerCase()
							+ ".png'/>&nbsp;&nbsp;" + state.text;
				}

				var form = $('#submit_form');

				form.validate({
					doNotHideMessage : true, //this option enables to show the error/success messages on tab switch.
					errorElement : 'span', //default input error message container
					errorClass : 'validate-inline', // default input error message class
					focusInvalid : false, // do not focus the last invalid input
					rules : {
						tenantName : {
							maxlength : 30,
							required : true
						},
						type : {
							required : true
						},
						property : {
							required : true
						}
						/* ,
						createUser : {
							required : true
						},
						createTimeStr : {
							required : true
						} */
					},

					messages : {
						tenantName : {
							maxlength : "输入的名称必须为少于30位",
							required : "输入的名称不能为空"
						},
						type : {
							required : "请选择类别"
						},
						property : {
							required : "请选择性质"
						}
						/* ,
						createUser : {
							required : "输入的创建人不能为空"
						},
						createTimeStr : {
							required : "输入的创建时间不能为空"
						} */
					},

					errorPlacement : function(error, element) { // render error placement for each input type
						var error_td = element.next('.help-inline');
						error_td.append(error);
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.help-inline')
								.removeClass('ok'); // display OK icon
						$(element).closest('.control-group').removeClass(
								'success').addClass('error'); // set error class to the control group
					},

					unhighlight : function(element) { // revert the change dony by hightlight
						$(element).closest('.control-group').removeClass(
								'error'); // set error class to the control group
					},
					
					success : function(label) {
						label.addClass('valid ok') // mark the current input as valid and display OK icon
						.closest('.control-group').removeClass('error')
								.addClass('success'); // set success class to the control group
					},

					submitHandler : function(form) {

						$(form).ajaxSubmit(
							{
								beforeSend : function() {
									$("#submitBtn").html("保存中...");
									$("#submitBtn").attr("disabled", "disabled");
								},
								success : function(data) {
									if (data == "true") {
										$("#submitBtn").html("保存成功");
										
										showSaveSuccess();
										setTimeout("$('#myModal').modal('hide');",1000);
										setTimeout("location.reload();",1300);
									}
									
								}

							});

						}

					});
				}

			};

		}();
	function initModal() {
		$("#modalTitle").html("新增组织");
		$("#submitBtn").html("<i class='icon-ok'></i>保存");
		$(".help-inline").html("");
		$("#submitBtn").removeAttr("disabled");
		$('.myinput').val("");
		$('.selections').remove();
		$('.control-group').removeClass('error success');
	}
	
	</script>
</body>
</html>