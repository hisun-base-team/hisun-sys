<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>用户列表</title>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${path}/css/DT_bootstrap.css" />
<style type="text/css">
</style>
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <!-- begin 新增用户的模态面板 -->
	<div id="updateModal" class="modal hide fade" >
		<div class="modal-header">
			<button class="close" type="button" id="close1" data-dismiss="modal"></button>
			<h3 id="modalTitle">新增用户</h3>

		</div>

		<div class="modal-body" style="padding: 10px;">

			<form action="${path}/sys/admin/user/update?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="form-horizontal myform"
				id="submit_form" method="post">
				<div class="tab-pane active" id="tab1">
					<div>

						<div class="control-group" >

							<label class="control-label mylabel">邮箱<span
								class="required">*</span>
							</label>

							<div class="controls inputtext">
								<input type="hidden" id="id" name="id" class="myinput"/>
								<input type="text" class="span6 m-wrap myinput" name="email" id="email"
									class="required mail" placeholder="你的常用邮箱地址"
									/> <span class="help-inline"></span>
							</div>

						</div>

						<!-- <div class="control-group" >

							<label class="control-label mylabel">用户名<span
								class="required">*</span>
							</label>

							<div class="controls inputtext">

								<input type="text" class="span6 m-wrap myinput" name="username" id="username"
									class="required" placeholder="长度为5-30位" /> <span
									class="help-inline"></span>

							</div>

						</div> -->
						<div class="control-group" >

							<label class="control-label mylabel">真实姓名<span
								class="required">*</span>
							</label>

							<div class="controls inputtext">

								<input type="text" class="span6 m-wrap myinput" name="realname" id="realname"
									class="required" placeholder="输入真实姓名" /> <span
									class="help-inline"></span>

							</div>

						</div>

						<div class="control-group" >

							<label class="control-label mylabel">手机号码<span
								class="required">*</span>
							</label>

							<div class="controls inputtext">

								<input type="text" class="span6 m-wrap myinput" name="tel" id="tel"
									class="required" placeholder="输入手机号码" /> <span
									class="help-inline"></span>

							</div>

						</div>
						<div class="control-group mybutton-group" style="margin: 0px;">
							<button id="submitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i>保存</button>
							<button type="button" class="btn btn-default" id="close"
								data-dismiss="modal"><i class='icon-remove-sign'></i>关闭
							</button>
						</div>

					</div>
				</div>
			</form>

		</div>

	</div>
	<!-- end 新增用户的模态面板 -->
	<!-- begin 分配角色的模态面板 -->
	<div id="myModal2" class="modal hide fade">

		<div class="modal-header">

			<button data-dismiss="modal" class="close" type="button"></button>

			<h3 id="modalTitle2">赋予角色</h3>

		</div>

		<div class="modal-body">

			<form action="${path}/sys/admin/user/saveRelation?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="form-horizontal myform"
				id="submit_form2" method="post">
				<div class="tab-pane active" id="tab2">
					<div>

						<div class="control-group">

							<label class="control-label mylabel">角色名
							</label>

							<div class="controls" style="position:relative">
								<input type="hidden" id="userId" name="userId" class="myinput"/>
								<input type="hidden" id="relationId" name="relationId" class="myinput"/>
								<!-- <select class="span6 m-wrap" name="roleSelection" id="roleSelection">

									<option value="">请选择...</option>

								</select> -->
								<div class="Multiselect_select1">
									<input class="txtValue" type="text" id="roles" name="roles" value="" readonly="readonly"/>
									<i class="icon-sort-down"></i>
								</div>

								<ul class="Multiselect_show1">
								</ul>
							</div>

						</div>

						<div class="control-group mybutton-group">
							<button id="submitBtn2" type="button" class="btn green mybutton"><i class='icon-ok'></i> 确定</button>
							<button type="button" class="btn btn-default rolesclose"
							   data-dismiss="modal"><i class='icon-remove-sign'></i> 关闭
							</button>
						</div>

					</div>
				</div>
			</form>

		</div>

	</div>
	<!-- end 分配角色的模态面板 -->
	<div class="row-fluid">
		<div class="row-fluid mb10" style="">
			<div class="caption_title">用户列表</div>
				<shiro:hasPermission name="admin-platform:user_add">
					<a href="javascript:;" onclick="add();" class="btn fr" style="margin-right: 5px;"><i class="icon-plus"></i> 添加</a>
				</shiro:hasPermission>
		</div>
		<div class="clearfix">

			<div class="control-group">


				<div id="query" style="float: left;">
					<form action="${path}/sys/admin/user/list" id="getUserForm" style="margin: 0 0 10px;" method="post">
						<input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
						<input type="text" name="searchContent" id="searchContent" style="margin:0px;" class="m-wrap" placeholder="用户名/邮箱/姓名/手机搜索" value='${cloud:htmlEscape(searchContent)}' />
						<button type="button" class="btn Short_but" id="searchButton">查询</button>
						<button type="button" class="btn Short_but" id="reset" onclick="searchReset();">清空</button>
					</form>
				</div>
			</div>

		</div>
		<table
			class="table table-striped table-bordered table-hover table-full-width"
			id="sample_2">

			<thead>

				<tr>

					<th>用户名</th>
					<th>真实姓名</th>
<%--
					<th>性别</th>
--%>
					<th>邮箱</th>
					<th>手机号码</th>
					<th>锁定</th>
					<!-- <th>所属组织</th>
					<th>组织性质</th> -->
					<!-- <th>所属角色</th> -->
					<th  style="width:200px;">操作</th>

				</tr>

			</thead>
			<tbody>
				<c:forEach items="${requestScope.pager.datas}" var="user"
					varStatus="status">
					<tr style="height: 50px;">
						<td title="${cloud:htmlEscape(user.username)}"><c:out value="${user.username}"></c:out></td>
						<td title="${cloud:htmlEscape(user.realname)}"><c:out value="${user.realname}"></c:out></td>
						<td title="${cloud:htmlEscape(user.email)}"><c:out value="${user.email}"></c:out></td>
						<td title="${cloud:htmlEscape(user.tel)}"><c:out value="${user.tel}"></c:out></td>
						<td>
							<c:if test="${user.locked}">
								是
							</c:if>
							<c:if test="${!user.locked}">
								否
							</c:if>
						</td>
							<td  class="Left_alignment" title="">
								<%--<c:if test="${username eq 'admin' }">
								<a href="javascript:void(0);" onclick="edit(this);" value="<c:out value="${user.id}"></c:out>">
									 编辑
								</a>|
								</c:if>--%>
								<shiro:hasPermission name="admin-platform:user_save_rolerelation">
									<a href="javascript:void(0);" class="setRole" value="<c:out value="${user.id}"></c:out>" itemProperty='<c:out value="${user.id}"></c:out>'>
										赋予角色
									</a>|
								</shiro:hasPermission>
								<shiro:hasPermission name="admin-platform:user_lock">
									<c:if test="${username eq 'admin' }">
										<c:if test="${user.locked}">
											<a href="javascript:void(0);" class="locked" userid="<c:out value="${user.id}"></c:out>" itemProperty='0'>
												解锁
											</a>|
										</c:if>
										<c:if test="${!user.locked}">
											<a href="javascript:void(0);" class="locked" userid="<c:out value="${user.id}"></c:out>" itemProperty='1'>
												锁定
											</a>|
										</c:if>
									</c:if>
								</shiro:hasPermission>
								<shiro:hasPermission name="admin-platform:user_delete">
									<a href="javascript:void(0);" class=" remove" value="<c:out value="${user.id}"></c:out>" itemName="<c:out value="${user.username}"></c:out>">
										注销
									</a>
								</shiro:hasPermission>
							</td>
					</tr>
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
<!-- END PAGE CONTAINER-->
<!-- BEGIN PAGE LEVEL PLUGINS  -->
<script type="text/javascript" src="${path}/js/select2.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.form.js"></script>
<script type="text/javascript" src="${path}/js/DT_bootstrap.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPT  -->
<script type="text/javascript">
		var searchReset =function (){
			$("#searchContent").removeAttrs("value");
			window.location.href = "${path}/sys/admin/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
		};
	var edit = function (a) {
		var id = $(a).attr("value");
		$.ajax({
			url: "${path}/sys/admin/user/load",
			type: "post",
			data: {"id" : id},
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			dataType: "json",
			success: function(data,status){
				if (status == "success") {
					$('#id').val(data.vo.id);
					$('#email').val(data.vo.email);
					$('#username').val(data.vo.username);
					$('#realname').val(data.vo.realname);
					$('#tel').val(data.vo.tel);
					$("#modalTitle").html("修改用户");
					$('#updateModal').modal({
						keyboard: false,
						backdrop: 'static'
					});
					$('#updateModal').show();
				}
			},
			error: function (arg1, arg2, arg3) {
				showTip("提示", "出错了请联系管理员", 2000);
			}
		});
	};
	var add = function(){
		location.href="${path}/sys/admin/user/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
	}

	
	jQuery(document).ready(function() {
		$(".Multiselect_select1").on("click",function(e){
			  e.stopPropagation();
			  if($(".Multiselect_show1").is(":visible")){
			  	$(' .Multiselect_show1 ').hide();
			  }else{
				$(' .Multiselect_show1 ').show();
			  }
		});
		$('[data-type="checkbox"]').click(function(){
			var data_value = $(this).attr('data-value'),
				txtalso = $.trim($(".txtValue").val());
			if($(this).prop("checked")) {
				if(txtalso.length > 0) {
					if(txtalso.indexOf(data_value+',') != -1) {
						return ;
					} else {
						txtalso += data_value + ',';
					}
				} else {
					txtalso = data_value+',';
				}
			} else {
				if(txtalso.indexOf(data_value+',') != -1) {
					txtalso = txtalso.replace(data_value+',', '');
				}
			}
			$(".txtValue").val(txtalso);
			
		});
		
		$('[data-type="checkbox1"]').click(function(){
			var data_value = $(this).attr('data-value'),
				txtalso = $.trim($("#roles").val());
			if($(this).prop("checked")) {
				if(txtalso.length > 0) {
					if(txtalso.indexOf(data_value+',') != -1) {
						return ;
					} else {
						txtalso += data_value + ',';
					}
				} else {
					txtalso = data_value+',';
				}
			} else {
				if(txtalso.indexOf(data_value+',') != -1) {
					txtalso = txtalso.replace(data_value+',', '');
				}
			}
			$("#roles").val(txtalso);
			
		});
		
		$(".tenantclose").on("click",function(){
			$("#tenantNames").val('');
		});
		
		$(".rolesclose").on("click",function(){
			$(' .Multiselect_show1 ').hide();
			$("#roles").val('');
		});
		
		$("#close").on("click",function(){
			initModal();
		});
		
		$("#close1").on("click",function(){
			initModal();
		});
		
		App.init();//必须，不然导航栏及其菜单无法折叠
		
		$('#myModal').on('hide.bs.modal', function () {
			initModal();
		});
		
		$('#myModal2').on('hide.bs.modal', function () {
			initModal2();
		});
		
		$('#myModal3').on('hide.bs.modal', function () {
			initModal3();
		});
		
		
		var deleteCallBack = function(data,status){
			if (status == "success") {
				location.reload();
			}
		}
		

		$('.remove').click(function () {
			var id = $(this).attr("value");
			var itemName = $(this).attr("itemName");
			actionByConfirm1(itemName, "${path}/sys/admin/user/delete/" + id, null, deleteCallBack, "注销");
			
		});
		
		//邀请成员按钮被点击
		$("#inviteBtn").click(function() {
			$('#inviteMemberModal').modal({
				keyboard: false,
				backdrop: 'static'
			});
		});

		$(".locked").on("click",function(){
			var userid = $(this).attr("userid");
			var locked = $(this).attr("itemProperty");
			localPost("${path}/sys/admin/user/locked",{"userid":userid,"locked":locked},function(data){
				if (data.success) {
					showTip("提示",data.message, 1300);
					setTimeout(function(){
						window.location.href='${path}/sys/admin/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';
					},1300);
				}else{
					showTip("提示",data.message,null);
				}
			},"json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		});

		$('.setRole').click(function () {
			var userid = $(this).attr("itemProperty");
				localPost("${path}/sys/admin/user/getRoleSelection", {'userId':userid}, function(data,status){
						if (status == "success") {
							$('#userId').val(userid);
							$('#relationId').val(data.relationId);
							var selection = $('.Multiselect_show1');
							selection.empty();
							$.each(data.roleSelections, function(index,element) {
								if(element.selected==="checked"){
									var roles = $("#roles");
									var value = roles.val();
									if(value){
										roles.val(value+element.roleName+",");
									}else{
										roles.val(element.roleName+",");
									}
								}
								selection.append("<li><input type='checkbox' class='input_mult' name='chooseRoles' data-type='checkbox1' "+element.selected+"  data-value='"+element.roleName+"' value='"+element.id+"' /><span>"+element.roleName+"</span></li>");
							});
							$('[data-type="checkbox1"]').click(function(){
								var data_value = $(this).attr('data-value'),
									txtalso = $.trim($("#roles").val());
								if($(this).prop("checked")) {
									if(txtalso.length > 0) {
										if(txtalso.indexOf(data_value+',') != -1) {
											return ;
										} else {
											txtalso += data_value + ',';
										}
									} else {
										txtalso = data_value+',';
									}
								} else {
									if(txtalso.indexOf(data_value+',') != -1) {
										txtalso = txtalso.replace(data_value+',', '');
									}
								}
								$("#roles").val(txtalso);
								
							});
							
							$('#myModal2').modal({
								keyboard: false,
								backdrop: 'static'
							});
						}
					}, "json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});

		});
	
		$("#searchButton").on("click",function(){
			$("#getUserForm").submit();
		});
		
		$('#submitBtn').click(function () {
			
            $('#submit_form').ajaxSubmit({
            	beforeSend : function() {
					$("#submitBtn").html("修改中...");
					$("#submitBtn").attr("disabled", "disabled");
				},
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(data) {
					if (data.success == "true" || data.success == true) {
						$("#submitBtn").html("修改成功");
						showSaveSuccess();
						//initModal();
						setTimeout("$('#updateModal').modal('hide');",1000);
						setTimeout(function(){
							window.location.href='${path}/sys/admin/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';
						},1300);
					}else{
						$("#submitBtn").html("保存");
						$("#submitBtn").removeAttr("disabled");
						showTip("提示", "系统错误，请联系管理员", null);
					}
				},
				error : function(data){
					$("#submitBtn").html("保存");
					$("#submitBtn").removeAttr("disabled");
					showTip("提示", "系统错误，请联系管理员", null);
				}
            });  
		
	});
		$('#submitBtn2').click(function () {
				
	            $('#submit_form2').ajaxSubmit({
	            	beforeSend : function() {
	            		$(' .Multiselect_show1 ').hide();
						$("#submitBtn2").html("保存中...");
						$("#submitBtn2").attr("disabled", "disabled");
					},
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					success : function(data) {
						if (data.success == "true" || data.success == true) {
							$("#submitBtn2").html("保存成功");
							showSaveSuccess();
							//initModal();
							setTimeout("$('#myModal2').modal('hide');",1000);
							setTimeout(function(){
								window.location.href='${path}/sys/admin/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';
							},1300);
							
						}else{
							$("#submitBtn2").html("保存");
							$("#submitBtn2").removeAttr("disabled");
							showTip("提示", "系统错误，请联系管理员", null);
						}
					},
					error : function(data){
						$("#submitBtn2").html("保存");
						$("#submitBtn2").removeAttr("disabled");
						showTip("提示", "系统错误，请联系管理员", null);
					}
	            });  
			
		});
		
		$('#submitBtn3').click(function () {
			
            $('#submit_form3').ajaxSubmit({
            	beforeSend : function() {
					$("#submitBtn3").html("保存中...");
					$("#submitBtn3").attr("disabled", "disabled");
				},
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(data) {
					if (data.success == "true" || data.success == true) {
						$("#submitBtn3").html("保存成功");
						showSaveSuccess();
						//initModal();
						setTimeout("$('#myModal3').modal('hide');",1000);
						setTimeout(function(){
							window.location.href='${path}/sys/admin/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';
						},1300);
					}else{
						$("#submitBtn3").html("保存");
						$("#submitBtn3").removeAttr("disabled");
						showTip("提示", "系统错误，请联系管理员", null);
					}
				},
				error : function(data){
					$("#submitBtn3").html("保存");
					$("#submitBtn3").removeAttr("disabled");
					showTip("提示", "系统错误，请联系管理员", null);
				}
            });  

	});
	
	});


	function initModal() {
		$("#modalTitle").html("新增用户");
		$("#submitBtn").html("<i class='icon-ok'></i>保存");
		$("#submitBtn").removeAttr("disabled");
		$('.myinput').val("");
		$(".help-inline").html("");
		$('.control-group').removeClass('error success');
	}
	
	function initModal2() {
		$("#submitBtn2").html("<i class='icon-ok'></i>确定");
		$("#submitBtn2").removeAttr("disabled");
		$('.myinput').val("");
		$(".help-inline").html("");
		$('.selections').remove();
		$('.control-group').removeClass('error success');
	}
	
	function initModal3() {
		$("#submitBtn3").html("<i class='icon-ok'></i>确定");
		$("#submitBtn3").removeAttr("disabled");
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
<!-- END PAGE LEVEL SCRIPT  -->
</body>
</html>
