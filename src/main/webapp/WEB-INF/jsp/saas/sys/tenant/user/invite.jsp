<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邀请用户</title>
<style type="text/css">
h4{
	color: #d84c31;
	font-size: 16px;
}
.input-group-btn{
	position: relative;
  	font-size: 0;
  	white-space: nowrap;
  	width: 1%;
  	vertical-align: middle;
  	float: left;
  	 /*  display: table-cell; */
}
.input-group-btn .btn{
 background-color: #fff !important; 
 padding:7px 20px !important;
 }
</style>
</head>
<body>
	<!-- BEGIN PAGE CONTAINER-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

				<div class="portlet box grey">

					<div class="portlet-title">

						<div class="caption">

							<span class="hidden-480">邀请用户</span>

						</div>
						<div class="tools">
							<a href="javascript:location.reload();" class="reload"></a>
						</div>
					</div>

					<div class="portlet-body form">
						<form action="${path}/sys/tenant/user/send/invite"
							class="form-horizontal myform" id="inviteMemberForm"
							method="post">
							<div class="tab-pane active">
								<div>
									<h4 style="color: #d84c31;font-size: 16px;">输入成员邮箱</h4>
									<div id="invite_members">
										<div class="control-group inviteMemberInputGroup" id="tpl">
											<div class="form-group invite_members">
												<input type="text" class="span6 select2 fl" 
													name="inviteEmail" placeholder="输入被邀请人的邮箱" style="height: 36px;border-radius:3px 0 0 3px !important;"/>
												<div class="input-group-btn setRoleClass">
                                                    <input type="hidden" name="roles" value="${roles[0].id}">
													<button type="button" class="btn btn-default dropdown-toggle ng-binding" data-toggle="dropdown" style="border-radius:0 3px 3px 0 !important; border: 1px solid #ccc; border-left: none;  /* background-color: #fff !important; */">${roles[0].roleName}
														<span class="caret"></span> 
													</button>
													<ul class="dropdown-menu pull-right" role="menu" style="left: -79px;">
                                                        <c:forEach items="${roles}" var="role" varStatus="status">
                                                            <li>
                                                                <a href="javascript:;" onclick="setRoles(this,'${role.id}')"  class="binding">${role.roleName}</a>
                                                            </li>
                                                        </c:forEach>
													</ul>
												</div>
												<span class="delete col-xs-2" style="line-height:33px;margin-left: 115px;">
													<a href="javascript:;" onClick="deleteMember(this)" style="color: #333">删除</a> 
												</span>
											</div>
										</div>
										<div class="control-group inviteMemberInputGroup">
											<div class="form-group invite_members">
												<input type="text" class="span6 select2 fl" 
													name="inviteEmail" placeholder="输入被邀请人的邮箱" style="height: 36px;border-radius:3px 0 0 3px !important;"/>
												<div class="input-group-btn setRoleClass">
                                                    <input type="hidden" name="roles" value="${roles[0].id}">
													<button type="button" class="btn btn-default dropdown-toggle ng-binding" data-toggle="dropdown" style="border-radius:0 3px 3px 0 !important; border: 1px solid #ccc; border-left: none;  /* background-color: #fff !important; */">${roles[0].roleName}
														<span class="caret"></span> 
													</button>
													<ul class="dropdown-menu pull-right" role="menu" style="left: -79px;">
                                                        <c:forEach items="${roles}" var="role" varStatus="status">
                                                            <li>
                                                                <a href="javascript:;" onclick="setRoles(this,'${role.id}')" class="binding">${role.roleName}</a>
                                                            </li>
                                                        </c:forEach>
													</ul> 
												</div>
												<span class="delete col-xs-2" style="line-height:33px;margin-left: 115px;">
													<a href="javascript:;" onClick="deleteMember(this)" style="color: #333">删除</a> 
												</span>
											</div>
										</div>
										<div class="control-group inviteMemberInputGroup">
											<div class="form-group invite_members">
												<input type="text" class="span6 select2 fl" 
													name="inviteEmail" placeholder="输入被邀请人的邮箱" style="height: 36px;border-radius:3px 0 0 3px !important;"/>
												<div class="input-group-btn setRoleClass">
                                                    <input type="hidden" name="roles" value="${roles[0].id}">
													<button type="button" class="btn btn-default dropdown-toggle ng-binding" data-toggle="dropdown" style="border-radius:0 3px 3px 0 !important; border: 1px solid #ccc; border-left: none;  /* background-color: #fff !important; */">${roles[0].roleName}
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu pull-right" role="menu" style="left: -79px;">
                                                        <c:forEach items="${roles}" var="role" varStatus="status">
                                                            <li>
                                                                <a href="javascript:;" onclick="setRoles(this,'${role.id}')" class="binding">${role.roleName}</a>
                                                            </li>
                                                        </c:forEach>
													</ul> 
												</div>
												<span class="delete col-xs-2" style="line-height:33px;margin-left: 115px;">
													<a href="javascript:;" onClick="deleteMember(this)" style="color: #333">删除</a> 
												</span>
											</div>
										</div>
										<a href="javascript:;" class="btn" onClick="inviteMember()">再添加一个</a>
										<br>
										<br>
									</div>
									
									<h4 style="color: #d84c31;font-size: 16px;">说点什么</h4>
									<div class="control-group">
										<div>
											<textarea rows="7" cols="20" id="emailcontent"
												name="emailcontent" required
												style="margin-left: 0px; margin-right: 0px; width: 800px;">三零优异云服务系统是一套针对云主机的安全服务自动化工具集，能够帮助您轻松应对云主机进行检测、加固、审计这些环节的安全服务工作.</textarea>
										</div>
									</div>
									<div class="alert alert-danger" style="display: none;" id="error"> 
										<i class="icon-remove-sign"></i> 
										<span class="ng-isolate-scope">
											<span class="ng-binding">请至少输入一个邮箱进行邀请</span>
										</span> 
									</div>
                                    <div class="alert alert-danger" style="display: none;" id="errorRole">
                                        <i class="icon-remove-sign"></i>
										<span class="ng-isolate-scope">
											<span class="ng-binding">请至少选择一下角色</span>
										</span>
                                    </div>
									<div class="form-actions">
										<button id="inviteMemberSubmitBtn" type="button"
											class="btn green mybutton">
											<i class='icon-ok'></i>发送邀请
										</button>
										<a href="${path}/sys/tenant/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
											data-dismiss="modal">
											<i class='icon-remove-sign'></i>取消
										</a>
									</div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <%-- END SAMPLE FORM PORTLET--%>
            </div>
        </div>
    </div>
		<script type="text/javascript">
			var tpl = $("#tpl").html();
			function inviteMember(){
				$(".inviteMemberInputGroup:last").after("<div class='control-group inviteMemberInputGroup'>"+tpl+"</div>");
				if($(".inviteMemberInputGroup").length>=1){
					$(".delete:first").show();
				}
                $(".binding").on("click",function(){
                    $(this).parents(".input-group-btn").find("button").html($(this).text()+"<span class='caret'></span>");
                });
			}
			function deleteMember(inviteMember){
				$(inviteMember).parents(".inviteMemberInputGroup").remove();
				if($(".inviteMemberInputGroup").length<=1){
					$(".delete").hide();
				}
			}
            function setRoles(obj, roleId){
                $(obj).parents(".setRoleClass").children("[name='roles']").val(roleId);
            }
			$(function(){
				$(".binding").on("click",function(){
					$(this).parents(".input-group-btn").find("button").html($(this).text()+"<span class='caret'></span>");
				});
				
				$("#inviteMemberSubmitBtn").on("click",function(){
					var bool = false;
					$("input[name='inviteEmail']").each(function(){
						if($(this).val().trim().length>1&&$(this).val()!=null){
							bool = true;
					    	return false;
						}
					  });
					if(bool){
						$("#error").hide();
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
											$("input[name='inviteEmail']").each(function(){$(this).val("");});
											$("#inviteMemberSubmitBtn").html("发送邀请").removeAttr("disabled");

											setTimeout(function(){
												window.location.href='${path}/sys/tenant/user/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';
											},1300);
										}else{
											showTip("提示", "邮件格式不对！", null);
											$("#inviteMemberSubmitBtn").html("发送邀请").removeAttr("disabled");
										}
									}
	
								});
					}else{
						$("#error").show();
					}
				});
			});
		</script>
</body>
</html>