<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="inc/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><sitemesh:write property="title" /></title>
<sitemesh:write property="head" />
<%@include file="inc/import.jsp" %>
<style type="text/css">
	li{
		cursor: pointer;
	}
</style>
<script>
    var tenantUrl = "sys/admin";
	jQuery(document).ready(function() {
        var tenant = "${tenant}";
        if (tenant) {
            tenantUrl = "sys/tenant"
        }
		getMessage();
		  countMessage();
		  setInterval(getMessage, 30000);
		  setInterval(countMessage, 30000);
		$(".dropdown").on("click",function(event){
			event.stopPropagation();
			if($(this).hasClass("open")){
				$(this).removeClass("open");
			}else{
				$(this).addClass("open");
			}
		});
			$.get("${path}/sys/resource/getMenu",
				function(data, status) {
					if (status == "success") {
						var datas = data.data;
						for (var key in datas) {
							if (datas[key].url != null&& datas[key].url.length > 0) {
								href = "${path}"+ datas[key].url;
								if (datas[key].resourceName === '首页') {
									active = " active";
								} else {
									active = "";
								}
							} else {
								href = "#";
							}
							if ($("#systemUl") != null) {
								if(datas[key].permission==='user:all' || datas[key].permission==='tenant:usercenter'){

									$("#systemUl").append("<li queryCode='"+datas[key].queryCode+"' style='display: none' class='systemLi" + active + "' resid='" + datas[key].resId + "'><a taget='" + href + "' onclick=topMenus(\'"+datas[key].resId+"\',this,true);> "+ datas[key].resourceName+ " <span class='selected'></span> </a></li>")
								}else{

									$("#systemUl").append("<li queryCode='"+datas[key].queryCode+"' class='systemLi" + active + "' resid='" + datas[key].resId + "'><a taget='" + href + "' onclick=topMenus(\'"+datas[key].resId+"\',this,true);> "+ datas[key].resourceName+ " <span class='selected'></span> </a></li>")
								}
							}
						}
						//topMenus($('.systemLi').first()[0].getAttribute('resid'),$($('.systemLi').first()[0]).find("a"),true);
						$(".systemLi").removeClass("active");
						var id = $('.systemLi').first()[0].getAttribute('resid');
						$(".systemLi[resid='"+id+"']").addClass("active");
						$("#moduleUl").children().hide();
						$("."+id).show();
		
					}

					/*if($('body').hasClass("page-sidebar-closed")){
						//去掉滚动条的属性
						var arr = $(".page-sidebar-closed .page-sidebar .slimScrollDiv");
						if(arr.length>0){
							for(var i=0;i<arr.length;i++){
								var target = arr[i];
								target.style.overflow = "";
								$(target).find(".scroller")[0].style.overflow = "";
							}
						}
					}*/
			});
			App.init();//必须，不然导航栏及其菜单无法折叠
			
	});
	function topMenus(topMenus,url,bool){
		
		$(".systemLi").removeClass("active");
		$(".systemLi[resid='"+topMenus+"']").addClass("active");
		$("#moduleUl").children().hide();
		$("."+topMenus).show();
		if(bool){
			 var url = $(url).attr('taget');
			 /*if(url.indexOf('?')>0){
				url +="&code="+topMenus;
			}else{
				url +="?code="+topMenus;
			} */
			window.location.href=url;
			//initMenus(topMenus,url);
			//window.location.href=$(url).attr('taget');
			//alert(topMenus);
		}
	}
	
	
	function initMenus(id,url){
		$.post("${path}/"+tenantUrl+"/resource/link",{"menuCode":id}, function(data){
			   if(data.success){
				   window.location.href=url;
			   }
			 });
	}
	
	function countMessage(){
		$.post("${path}/"+tenantUrl+"/message/ajax/pending",function(data){
			if(data.success){
				$("#message02").text(data.count);
				$("#message03").text(data.read);
			}
		});
	}
	function getMessage() {
		var messages = $("#messages");
		var message_badge = $("#message_badge");

		//发送post请求
		$.post("${path}/"+tenantUrl+"/message/pending",
			function(data) {
				messages.empty();
				message_badge.text(data.count);
				$("#message01").text(data.count);
				if(!data.count){
					data.count = 0;
				}
				var start = "<li><p>您有" + data.count
						+ "条新消息</p></li>";
				var conent = "";
				var message = data.message;
				for ( var i in message) {
					conent += "<li><a style=\"white-space: inherit;\" href='${path }/" + tenantUrl + "/message/messages?type="
							+ message[i].type
							+ "'><i class=\"icon-warning-sign\"></i>"
							+ message[i].title + "</a></li>";
				}

				var end = "<li class=\"external\"><a href=\"${path }/"+tenantUrl+"/message/messages?type=all\">查看所有消息<i class=\"m-icon-swapright\"></i></a></li> ";
				messages.append(start + conent + end);
			});
	}
</script>
</head>
<body class="page-header-fixed">
<%@include file="inc/confirmModal.jsp" %>
<%@include file="inc/servlet.jsp" %>
	<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->

		<div class="navbar-inner">

			<div class="container-fluid">

				<!-- BEGIN LOGO -->

				<c:choose>
					<c:when test="${username ne 'admin'}">
						<a class="brand" href="${path}/dashboard" style="margin-left: 0px;">
								<%-- <img src="<%=path%>/images/logo-mid.png" alt="logo" /> --%>
							<img src="<%=path%>/images/${logo}" alt="logo"/>
						</a>
					</c:when>
					<c:otherwise>
						<a class="brand" href="javaScript:;" style="margin-left: 0px;">
								<%-- <img src="<%=path%>/images/logo-mid.png" alt="logo" /> --%>
							<img src="<%=path%>/images/${logo}" alt="logo"/>
						</a>
					</c:otherwise>
				</c:choose>

				<!-- END LOGO -->

				<!-- BEGIN HORIZANTAL MENU -->

				<div class="navbar hor-menu hidden-phone hidden-tablet">

					<div class="navbar-inner">

						<ul class="nav" id="systemUl" style="width: 100%;">
							<!-- 里面按照资源管理中配置的系统级资源生成上方菜单项 -->
						</ul>

					</div>

				</div>

				<!-- END HORIZANTAL MENU -->

				<!-- BEGIN TOP NAVIGATION MENU -->

				<ul class="nav pull-right" style="margin:0;">
					<!-- <li class="dropdown"><a href="mailto:30SCloud@30wish.net"
						class="dropdown-toggle"> <i class="icon-warning-sign"></i>
							BUG报告
					</a></li> -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<c:if test="${!empty userId && username ne 'admin'}">
						<li class="dropdown lired" id="header_notification_bar">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
								<i class="icon-bell-alt" style="font-size:18px; width:2.5em; text-align:left;"></i> 
								<span class="badge" id="message_badge">0</span>
							</a>

							<ul class="dropdown-menu extended notification" id="messages">

							</ul>
						</li>

					</c:if>
                    <c:if test="${!empty tenant.id}">
                        <li class="dropdown user">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <img alt="" style=" height:29px; width:29px; border-radius:50%;padding: 0 6px 0 0;"
                                     src="${path }/sys/tenant/user/headimg/${userId}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                <span class="username"><%=username%></span>
                                <i class="icon-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=path%>/sys/tenant/user/profile?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                    <i class="icon-user"> 个人中心</i></a>
                                </li>
                                <li><a href="<%=path%>/logout">
                                    <i class="icon-off"></i> 退出</a>
                                </li>

                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${empty tenant.id}">
                        <li class="dropdown user">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <img alt="" id="topheadPhoto"
                                     style=" height:29px; width:29px; border-radius:50%;padding: 0 6px 0 0;"
                                     src="${path }/sys/admin/user/photo/<%=headPhoto%>?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                <span class="username"><%=username%></span>
                                <i class="icon-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=path%>/sys/admin/user/profile?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                    <i class="icon-user"> 个人中心</i></a>
                                </li>
                                <li><a href="<%=path%>/admin/logout" id="logout">
                                    <i class="icon-off"></i> 退出</a>
                                </li>

                            </ul>
                        </li>
                    </c:if>


				</ul>

			</div>

		</div>

	</div>

	<div class="page-container row-fluid" >


		<div class="page-content" style="margin:0  !important;">
			<sitemesh:write property='body' />
		</div>
	</div>
</body>
</html>
