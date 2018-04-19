<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />

<title>用户列表</title>

<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<meta content="" name="description" />

<meta content="" name="author" />

<!-- BEGIN PAGE LEVEL STYLES -->

<link rel="stylesheet" type="text/css" href="<%=path%>/css/select2_metro.css" />

<link rel="stylesheet" href="<%=path%>/css/DT_bootstrap.css" />


<style type="text/css">

.mylabel {
	font-size: 16px;
	cursor: default;
	width: 100px !important;
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

.myinput {
	width: 220px !important;
}

.inputtext {
	 margin-left:120px !important;
}

table {
	border-collapse:collapse !important;
}

.help-inline{
	margin: 0px;
}

.help-inline span{
	margin: 0px;
	padding: 0px;
}

.controls {
  margin-left: 130px !important;
}
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
	
						<form action="<%=path%>/sys/tenant/user/update" class="form-horizontal myform"
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
	
						<form action="<%=path%>/sys/tenant/user/saveRelation" class="form-horizontal myform"
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

				<div class="row-fluid">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
 							<div class="row-fluid mb10" style="">
			                    <div class="caption_title">用户列表</div>
			                    	<a href="${path}/sys/tenant/user/invite?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn blue fr" type="button">邀请成员</a>
									<a href="javascript:;" onclick="add();" class="btn fr" style="margin-right: 5px;"><i class="icon-plus"></i> 添加</a>	
			                </div>	
							
							<div class="clearfix">

								<div class="control-group">

									
									<div id="query" style="float: left;">
										<form action="${path}/sys/tenant/user/list" id="getUserForm" style="margin: 0 0 10px;" method="get">
											<input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
											<input type="text" name="searchContent" id="searchContent" style="margin:0px;" class="m-wrap" placeholder="用户名/邮箱/姓名/手机搜索" value='${cloud:htmlEscape(searchContent)}' />
											<button type="submit" class="btn Short_but" id="searchButton">查询</button>
											<button onclick="resetSearch()" type="reset" class="btn Short_but" id="resetButton">清空</button>
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
										<%--<th>性别</th>--%>
										<th>邮箱</th>
										<th>手机号码</th>
										<th>职务</th>
										<!-- <th>所属组织</th>
										<th>组织性质</th> -->
										<th>用户角色</th>
										<th style="text-align:center;width:200px;">操作</th>

									</tr>

								</thead>
								<tbody>
									<c:forEach items="${pager.datas}" var="user"
										varStatus="status">
										<tr style="height: 50px;">
											<td title="<c:out value="${user.username}"></c:out>"><a href="javascript:void(0);" onclick="toDetail('${user.id}')"><c:out value="${user.username}"></c:out></a></td>
											<td title="<c:out value="${user.realname}"></c:out>"><c:out value="${user.realname}"></c:out></td>
											<%--<td>
												<c:choose>
													<c:when test="${user.sex}">
														<c:out value="男"></c:out>
													</c:when>
													<c:otherwise>
														<c:out value="女"></c:out>
													</c:otherwise>
												</c:choose>
											</td>--%>
											<td title="<c:out value="${user.email}"></c:out>"><c:out value="${user.email}"></c:out></td>
											<td title="<c:out value="${user.tel}"></c:out>"><c:out value="${user.tel}"></c:out></td>
											<td title="<c:out value="${user.positional}"></c:out>"><c:out value="${user.positional}"></c:out></td>
											<td title="<c:out value="${user.roleName}"></c:out>"><c:out value="${user.roleName}"></c:out></td>
											<%--<td>
												<c:if test="${user.locked}">
													是
												</c:if>
												<c:if test="${!user.locked}">
													否
												</c:if>
											</td>--%>
												<td style="text-align: center;" class="Left_alignment" title="">
                                                    <c:if test="${user.username != username}">
													<a href="javascript:void(0);" class="setRole" value="<c:out value="${user.id}"></c:out>" itemProperty='<c:out value="${user.id}"></c:out>'>
														赋予角色
													</a>|
													<a href="javascript:void(0);" class=" remove" value="<c:out value="${user.id}"></c:out>" itemName="<c:out value="${user.username}"></c:out>">
														移除
													</a>
                                                    </c:if>
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
	var add = function(){
		location.href="${path}/sys/tenant/user/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
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
		
		$("#inviteMemberSubmitBtn").on("click",function(){
			$("#inviteMemberForm").ajaxSubmit(
					{
						
						beforeSend : function() {
							$("#inviteMemberSubmitBtn").html("邮件发送中...");
							$("#inviteMemberSubmitBtn").attr("disabled", "disabled");
						},
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(data) {
							if (data.success == "true" || data.success == true) {
								$("#inviteMemberSubmitBtn").html("邮件发送成功");
								
								showTip("提示", "邮件发送成功！");
								setTimeout("$('#inviteMemberModal').modal('hide');",1000);
							}else{
								showTip("提示", "邮件格式不对！");
								$("#inviteMemberSubmitBtn").html("发送邀请").removeAttr("disabled");
							}
							
						}

					});
		});
		
		App.init();//必须，不然导航栏及其菜单无法折叠
		
		//邀请成员填写邮箱地址的窗口关闭时清空内容
		$('#inviteMemberModal').on('hide.bs.modal', function () {
			initInviteMemberModal();
		});
		
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
			actionByConfirm1(itemName, "<%=path%>/sys/tenant/user/delete/" + id, null, deleteCallBack, "移除");
			
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
			localPost("<%=path%>/sys/tenant/user/locked",{"userid":userid,"locked":locked},function(data){
				if (data.success) {
					showTip("提示",data.msg, 1300);
					setTimeout("window.location.href='"+"${path}/sys/tenant/user/list'",1300);
				}
			},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		});

		$('.setRole').click(function () {
			var userId = $(this).attr("itemProperty");
            localPost("<%=path%>/sys/tenant/user/getRoleSelection/" + userId,null, function(data,status){
                if (status == "success") {
                    $('#userId').val(userId);
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
                        selection.append("<li><input type='checkbox' class='input_mult' name='chooseRoles' data-type='checkbox1' "+element.selected+"  data-value='"+element.roleName+"' value='"+element.id+"' id='"+element.id+"' /><span onclick=checkRoleSel(" +"'" +element.id+ "'"+")>"+element.roleName+"</span></li>");
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

                    },"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});

                    $('#myModal2').modal({
                        keyboard: false,
                        backdrop: 'static'
                    });
                }
            }, null , {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		});
		
		$('.chooseTenant').click(function () {
			var id = $(this).attr("value");
			
			localPost("<%=path%>/sys/user/getTenantSelection", {"userId" : id}, function(data,status){
				if (status == "success") {
					$('#userIdInchooseTenant').val(id);
					var selection = $('.Multiselect_show');
					selection.empty();
					/* selection.append("<option value=''>请选择...</option>"); */
					$.each(data.tenantSelections, function(index,element) {
						//selection.append("<option class='selections' value='" + element.id + "' " + element.selected + " >" + element.tenantName + "</option>");
						if(element.selected==="checked"){
							var tenantNames = $("#tenantNames");
							var value = tenantNames.val();
							if(value){
								tenantNames.val(value+element.tenantName+",");
							}else{
								tenantNames.val(element.tenantName+",");
							}
						}
						selection.append("<li><input type='checkbox' class='input_mult' name='chooseTenants' data-type='checkbox' "+element.selected+"  data-value='"+element.tenantName+"' value='"+element.id+"' /><span>"+element.tenantName+"</span></li>");
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
					$('#myModal3').modal({
						keyboard: false,
						backdrop: 'static'
					});
				}
			}, null, {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
			
			
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
						setTimeout("$('#updateModal').modal('hide');",1000);
						setTimeout("window.location.href='"+"${path}/sys/tenant/user/list'",1300);
						
					}
					
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
							setTimeout("window.location.href='"+"${path}/sys/tenant/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);
							
						}
						
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
						setTimeout("window.location.href='"+"${path}/sys/tenant/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'",1300);
						
					}
					
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
	
	function initInviteMemberModal() {
		$("#inviteMemberSubmitBtn").html("<i class='icon-ok'></i>发送邀请");
		$("#inviteMemberSubmitBtn").removeAttr("disabled");
		$('.myinput').val("");
		$("#inviteEmail").select2('data', null);
		$(".inviteMemberValMes").html("");
		$('.inviteMemberInputGroup').removeClass('error success');
	}
	
	function pagehref(pageNum, pageSize) {
		var url = location.href;
		var length = url.indexOf("?");
		var searchContent = $("#searchContent").val();
		length = length > 0 ? length : url.length;
		location.href=url.substring(0, length) + "?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum=" + pageNum  + "&pageSize=" + pageSize + "&searchContent=" +searchContent;
	}
    function checkRoleSel(id) {
        var check = $("#"+id).attr("checked");
        var data_value = $("#"+id).attr('data-value'),
                txtalso = $.trim($("#roles").val());
        if (check) {
            $("#"+id).removeAttr("checked");
            if(txtalso.indexOf(data_value+',') != -1) {
                txtalso = txtalso.replace(data_value+',', '');
            }
        } else {
            $("#"+id).attr("checked","checked");
            if(txtalso.length > 0) {
                if(txtalso.indexOf(data_value+',') != -1) {
                    return ;
                } else {
                    txtalso += data_value + ',';
                }
            } else {
                txtalso = data_value+',';
            }
        }
        $("#roles").val(txtalso);
    }
    function resetSearch() {
        $("#searchContent").removeAttr("value");
    }

    function toDetail(userId) {
        location.href="${path}/sys/tenant/user/detail/"+userId+"?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
    }

	</script>
</body>
</html>
