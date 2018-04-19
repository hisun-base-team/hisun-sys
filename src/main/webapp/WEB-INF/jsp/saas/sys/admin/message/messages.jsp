<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<title>消息中心</title>
<link href='<c:url value="/css/profile.css" />' rel="stylesheet" type="text/css"/>
<link href='<c:url value="/css/datepicker.css" />' rel="stylesheet" type="text/css"/>
<link href='<c:url value="/css/datetimepicker.css" />' rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/select2_metro.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap-toggle-buttons.css" />" />
<style type="text/css">
	a:hover{color:#d14836 !important;text-decoration:none}
</style>
</head>
<body>
			<div class="container-fluid">
				
				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid profile">

					<div class="span12">

						<!--BEGIN TABS-->

						<div class="tabbable tabbable-custom tabbable-full-width">
							
									<div style="float: right;padding: 9px 15px;" id="allread">
										<a href="javascript:;" style="color: #666; font-weight: normal;" onClick="allRead();"> 全部标为已读 </a>
									</div>
									<ul class="nav nav-tabs">
		
									<!-- 	<li class="active"><a href="#tab_1_1" data-toggle="tab">Overview</a></li>-->
		
										<li class="active"><a href="#tab_1_2" data-toggle="tab" id="unreadTab">未读</a></li>
		
										<li><a href="#tab_1_4" data-toggle="tab" id="readTab">已读</a></li>
		
									<!-- 	<li><a href="#tab_1_6" data-toggle="tab">Help</a></li> -->
		
									</ul>
								

							<div class="tab-content" style="padding-top: 8px;">
 
								<div class="tab-pane profile-classic row-fluid active" id="tab_1_2">

									<div class="portlet-body fuelux">
									
										<table
											class="table table-striped table-bordered table-hover dataTable table-set">
											<thead>
												<tr>
													<th style="width: 20%">消息标题</th>
													<th>消息内容</th>
													<th style="width: 150px">创建时间</th>
													<th width="120px">操作</th>
												</tr>
											</thead>
											<tbody id="unread">
												<c:forEach items="${unread}" var="onlineMessage">
													<tr>
														<td><c:out value="${onlineMessage.title }"></c:out></td>
														<td><c:out value="${onlineMessage.content }"></c:out></td>
														<td><c:out value="${onlineMessage.createDate }"></c:out></td>
														<td class="Left_alignment">
															<a href="javascript:;" class=""  onclick="read('${onlineMessage.id}','unread','2');">已读</a>|
															<a href="javascript:;" class=""  onclick="read('${onlineMessage.id}','unread','1');">待处理</a>
														</td>
													</tr>	
												</c:forEach>
											</tbody>
										</table>
									
									</div>
									
								</div>

								<!--tab_1_2-->

								<div class="tab-pane row-fluid profile-account" id="tab_1_3">

									<div class="portlet-body fuelux">
									
										<table
											class="table table-striped table-bordered table-hover dataTable table-set">
											<thead>
												<tr>
													<th style="width: 20%">消息标题</th>
													<th>消息内容</th>
													<th style="width: 150px">创建时间</th>
													<th style="width: 10%">操作人</th>
													<th style="width: 120px;">操作</th>
												</tr>
											</thead>
											<tbody id="pending">

											</tbody>
										</table>
									
									</div>

									</div>

									<div class="tab-pane row-fluid profile-account" id="tab_1_4">
										
										
									</div>
								</div>

								<!--end tab-pane-->


								<!--end tab-pane-->
								<!--end tab-pane-->

							</div>

						</div>

						<!--END TABS-->

					</div>

				</div>
				
			</div>
		</div>
	</div>
	<script src="${path }/js/app.js"></script>      
	<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${path }/js/jquery.blockui.min.js" type="text/javascript"></script> 
	<script type="text/javascript" src="${path }/js/jquery.toggle.buttons.js"></script>
	<script type="text/javascript" src="${path }/js/select2.js"></script>
	<script>
		function allRead(){
			localPost("${path}/sys/admin/message/all/read",null,function(data){
				if(data.success===true){
				 var type = $("#unread");
				   var type1 = "unread";
				   var fun1="read";
				   var fun2="pending";
				   var status1="2";
				   var status2="1";
				   var content1="已读";
				   var content2="待处理";
				   $.post("${path}/sys/admin/message/list", { "status":0},
							  function(data){
								if(data.success===true){
									type.empty();
									var data = data.data;
									var html="";
									for(var i in data){
										html+="<tr><td>"+data[i].title+"</td><td>"+data[i].content+"</td><td>"+data[i].createDate+"</td><td class=\"Left_alignment\">"
											+"<a href=\"javascript:;\" class=\"\"  onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status1+"')"+"\">"+content1+"</a>|<a href=\"javascript:;\" class=\"\" "+
											" onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status2+"')"+"\">"+content2+"</a></td></tr>";
									}
									type.append(html);
								}
							  });
				}else{
					showTip("警告", "操作失败！", 1300);
				}
			}, "json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		}
		jQuery(document).ready(function() {       
			
		   App.init();
			
		   $("#unreadTab").on("click",function(){
			   $("#allread").show();
			   var type = $("#unread");
			   var type1 = "unread";
			   var fun1="read";
			   var fun2="pending";
			   var status1="2";
			   var status2="1";
			   var content1="已读";
			   var content2="待处理";
			   localPost("${path}/sys/admin/message/list", { "status":0},
						  function(data){
							if(data.success===true){
								type.empty();
								var data = data.data;
								var html="";
								for(var i in data){
									html+="<tr><td>"+data[i].title+"</td><td>"+data[i].content+"</td><td>"+data[i].createDate+"</td><td  class=\"Left_alignment\">"
										+"<a href=\"javascript:;\" class=\"\"  onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status1+"')"+"\">"+content1+"</a>|<a href=\"javascript:;\" class=\" \" "+
										" onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status2+"')"+"\">"+content2+"</a></td></tr>";
								}
								type.append(html);
							}
						  }), "json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"};
		   });
		   $("#pendingTab").on("click",function(){
			   $("#allread").show();
			   var type = $("#pending");
			   var type1 = "pending";
			   var fun1="read";
			   var fun2="read";
			   var status1="0";
			   var status2="2";
			   var content1="未读";
			   var content2="已读";
			   localPost("${path}/sys/admin/message/list",{ "status":1},
						  function(data){
							if(data.success===true){
								type.empty();
								var data = data.data;
								var html="";
								for(var i in data){
									html+="<tr><td>"+data[i].title+"</td><td>"+data[i].content+"</td><td>"+data[i].createDate+"</td><td class=\"Left_alignment\">"
										+"<a href=\"javascript:;\" class=\"\"  onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status1+"')"+"\">"+content1+"</a>|<a href=\"javascript:;\" class=\"\" "+
										" onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status2+"')"+"\">"+content2+"</a></td></tr>";
								}
								type.append(html);
							}
						  }, "json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		   });
		   $("#readTab").on("click",function(){
			   $("#allread").hide();
			   //$("#tab_1_4").load("${path}/sys/admin/message/list/sitemesh/read");
			   $.ajax({
				   url : "${path}/sys/admin/message/list/sitemesh/read",
				   type : "post",
				   data : null,
				   headers: {
					   "OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				   },
				   dataType: "html",
				   success : function(html){
					   $("#tab_1_4").html(html);
				   }
			   });
		   });
			
		});

		function pagehref (pageNum ,pageSize){
			//$("#tab_1_4").load("${path}/sys/admin/message/list/sitemesh/read?pageNum="+pageNum+"&pageSize="+pageSize);
			$.ajax({
				url : "${path}/sys/admin/message/list/sitemesh/read",
				type : "post",
				data : {"pageNum":pageNum, "pageSize":pageSize},
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				dataType: "html",
				success : function(html){
					$("#tab_1_4").html(html);
				}
			});
		}
		
		function read(id,type1,status){
			var type = $("#"+type1);
			var fun1;
			var fun2;
			var status1;var status2;
			var content1;
			var content2;
			var class1;var class2;
			var class3;var class4;
			if(type1==="unread"){
				fun1="read";
				fun2="pending";
				status1="2";
				status2="1";
				content1="已读";
				content2="待处理";
				//class1=" purple ";
				//class2=" blue ";
				
				//class3=" icon-share ";
				//class4=" icon-pause ";
			}else if(type1==="pending"){
				fun1="read";
				fun2="read";
				status1="0";
				status2="2";
				content1="未读";
				content2="已读";
				//class1=" green ";
				//class2=" purple ";
				
				//class3=" icon-envelope ";
				//class4=" icon-share ";
			}else{
				fun1="read";
				fun2="pending";
				status1="0";
				status2="1";
				content1="未读";
				content2="待处理";
				//class1=" green ";
				//class2=" blue ";
				
				//class3=" icon-envelope ";
				//class4=" icon-pause ";
			}
			localPost("${path}/sys/admin/message/update", { "id": id,"status":status,"type":type1},
					  function(data){
						if(data.success===true){
							type.empty();
							var data = data.data;
							var html="";
							for(var i in data){
								html+="<tr><td>"+data[i].title+"</td><td>"+data[i].content+"</td><td>"+data[i].createDate+"</td><td class=\"Left_alignment\">"
									+"<a href=\"javascript:;\" class=\""+class1+" \"  onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status1+"')"+"\">"+content1+"</a>|<a href=\"javascript:;\" class=\" "+class2+" \" "+
									" onclick=\""+fun1+"('"+data[i].id+"','"+type1+"','"+status2+"')"+"\">"+content2+"</a></td></tr>";
							}
							type.append(html);
						}
					  }, "json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		}
	</script>
	<c:choose>
			<c:when test="${param.type eq '1'}">
				<script type="text/javascript">
				 $(function(){
					 $("#pendingTab").click();
				 });
				</script>
			</c:when>
			<c:when test="${param.type eq '2'}">
				<script type="text/javascript">
 				$(function(){
 					$("#readTab").click();
				 });
				</script>
			</c:when>
		</c:choose>
	<script type="text/javascript" src="${path }/js/jquery.complexify.banlist.js"></script>
	<script type="text/javascript" src="${path }/js/jquery.complexify.js"></script>
</body>
</html>