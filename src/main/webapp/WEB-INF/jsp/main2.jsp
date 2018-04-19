<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="inc/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><sitemesh:write property="title" /></title>
	<%@include file="inc/import.jsp" %><%-- 引入必须在原来的CSS之前 --%>
	<sitemesh:write property="head" />

	<style type="text/css">
		li {
			cursor: pointer;
		}

		.page-sidebar .sidebar-toggler {
			opacity: 0.5;
			/* margin-left: 130px; */
		}


	</style>
	<script>
		var iscode = "${param._show}";//是否展开左侧菜单
		var tenantUrl = "platform/admin";
		jQuery(document).ready(function() {
			var tenant = "${tenant}";
			if (tenant) {
				tenantUrl = "platform/tenant"
			}
			$(".dropdown").on("click",function(event){
				event.stopPropagation();
				if($(this).hasClass("open")){
					$(this).removeClass("open");
				}else{
					$(this).addClass("open");
				}
			});

			$('.page-sidebar').on('click', '.sidebar-toggler', function (e) {
				var body = $('body');
				var sidebar = $('.page-sidebar');

				if ((body.hasClass("page-sidebar-hover-on") && body.hasClass('page-sidebar-fixed')) || sidebar.hasClass('page-sidebar-hovering')) {
					body.removeClass('page-sidebar-hover-on');
					sidebar.css('width', '').hide().show();
					e.stopPropagation();
					//runResponsiveHandlers();
					return;
				}

				$(".sidebar-search", sidebar).removeClass("open");

				if (body.hasClass("page-sidebar-closed")) {
					body.removeClass("page-sidebar-closed");
					if (body.hasClass('page-sidebar-fixed')) {
						sidebar.css('width', '');
					}
				} else {
					body.addClass("page-sidebar-closed");
				}
				//runResponsiveHandlers();
			});

			// handle the search bar close
			$('.page-sidebar').on('click', '.sidebar-search .remove', function (e) {
				e.preventDefault();
				$('.sidebar-search').removeClass("open");
			});

			$.get("${path}/sys/resource/getMenu",
					function(data, status) {
						if (status == "success") {
							var datas = data.data;
							var firstResourceName = datas[0].resourceName;
							for (var key in datas) {
								if(datas[key].resourceType !=2){
									//第一层永远是系统类型，如果不是则跳过不显示，去资源管理重新排查为什么不是系统类型。下面的ALTER是给开发时候排查的
									continue;
								}
								if (datas[key].url != null&& datas[key].url.length > 0) {
									href = "${path}"+ datas[key].url;
									if (datas[key].resourceName === firstResourceName) {
										levelOneActive = " active";
									} else {
										levelOneActive = "";
									}
								} else {
									href = "#";
								}

								if ($("#systemUl") != null) {
									//按目前(20160510)的下面这个判断完全用不上，永远都会去到ELSE判断里面
									if(datas[key].permission==='user:all' || datas[key].permission==='tenant:usercenter'){

										$("#systemUl").append("<li queryCode='"+datas[key].queryCode+"' style='display: none' class='systemLi" + levelOneActive + "' resid='" + datas[key].resId + "'><a taget='" + href + "' onclick=topMenus(\'"+datas[key].resId+"\',this,true);> "+ datas[key].resourceName+ " <span class='selected'></span> </a></li>")
									}else{

										$("#systemUl").append("<li queryCode='"+datas[key].queryCode+"' class='systemLi" + levelOneActive + "' resid='" + datas[key].resId + "'><a taget='" + href + "' onclick=topMenus(\'"+datas[key].resId+"\',this,true);> "+ datas[key].resourceName+ " <span class='selected'></span> </a></li>")
									}
								}
								//左侧菜单
								var levelOneSelectedHtml = "";//当前选中的样式html
								var arrowHtml = "";//模块菜单右侧可折叠箭头的样式html
								var levelOneOpen = "";//表示是否展开的样式
								var levelOneResources = datas[key].children;
								var levelOneActive = "";
								//左侧菜单第一层
								for(var levelOneResourcesKey in levelOneResources){
									var levelTwoUlHtml = "";
									var childActive = "";

									if (levelOneResources[levelOneResourcesKey].sort == 1) {
										levelOneActive = " active ";
										levelOneOpen = " open ";
										levelOneSelectedHtml = " <span class='selected '></span> "
									} else {
										levelOneActive = " ";
										levelOneSelectedHtml = " ";
										levelOneOpen = " ";
									}

									var levelTwoResources =levelOneResources[levelOneResourcesKey].children;
									//左侧菜单第二层
									var levelTwoOpen = "";
									var levelTwoActive = "";
									var levelTwoSelectedHtml = "";
									for(var levelTwoResourcesKey in levelTwoResources){
										var levelThreeUlHtml = "";
										var levelThreeActive = "";

										if (levelTwoResources[levelTwoResourcesKey].sort == 1) {
											levelTwoActive = " active ";
											levelTwoOpen = " open ";
											levelTwoSelectedHtml = " <span class='selected '></span> "
										} else {
											levelTwoActive = " ";
											levelTwoSelectedHtml = " ";
											levelTwoOpen = " ";
										}

										if(levelTwoResources[levelTwoResourcesKey].resourceType==1){//操作直接跳出当前循环
											continue;
										}
										if (levelTwoResources[levelTwoResourcesKey].sort == 1) {
											childActive = " active";
											levelOneSelectedHtml = " <span class='selected '></span> ";
										} else {
											childActive = "";
											levelOneSelectedHtml = "";
										}
										if (levelTwoResources[levelTwoResourcesKey].url != null && levelTwoResources[levelTwoResourcesKey].url.length > 0) {
											href = "${path}" + levelTwoResources[levelTwoResourcesKey].url;
										} else {
											href = "#";
										}

										var levelThreeResources = levelTwoResources[levelTwoResourcesKey].children;
										//左侧菜单第三层
										for(var levelThreeResourcesKey in levelThreeResources){

											if(levelThreeResources[levelThreeResourcesKey].resourceType==1){//操作直接跳出当前循环
												continue;
											}
											if (levelThreeResources[levelThreeResourcesKey].sort == 1) {
												levelThreeActive = " active";
												levelTwoSelectedHtml = " <span class='selected '></span> ";
											} else {
												levelThreeActive = "";
												levelTwoSelectedHtml = "";
											}
											if (levelThreeResources[levelThreeResourcesKey].url != null && levelThreeResources[levelThreeResourcesKey].url.length > 0) {
												href = "${path}" + levelThreeResources[levelThreeResourcesKey].url;
											} else {
												href = "#";
											}
											levelThreeUlHtml += "<li queryCode='"+levelThreeResources[levelThreeResourcesKey].queryCode+"' resid='"+levelThreeResources[levelThreeResourcesKey].resId+"' class='" + levelThreeActive + "'><a id='"+levelThreeResources[levelThreeResourcesKey].resId+"' taget='" + href + "' onclick=leftMenus(\'"+levelThreeResources[levelThreeResourcesKey].resId+"\',this);> " + levelThreeResources[levelThreeResourcesKey].resourceName + "</a></li>" + levelTwoSelectedHtml;
										}

										if (levelThreeUlHtml.length > 0) {//有子菜单
											levelThreeUlHtml = "<ul class='sub-menu'>" + levelThreeUlHtml + "</ul>";
											//arrowHtml1 = "<span class='arrow" + open1 + "'></span>";
										}
										if(levelTwoResources[levelTwoResourcesKey].children.length>0){
											arrowHtml1 = "<span class='arrow'></span>";
										}else{
											arrowHtml1 = "";
										}
										//levelTwoUlHtml += "<li queryCode='"+levelTwoResources[levelTwoResourcesKey].queryCode+"' resid='"+levelTwoResources[levelTwoResourcesKey].resId+"' class='" + childActive + "'><a id='"+levelTwoResources[levelTwoResourcesKey].resId+"' taget='" + href + "' onclick=leftMenus(\'"+levelTwoResources[levelTwoResourcesKey].resId+"\',this);> " + levelTwoResources[levelTwoResourcesKey].resourceName+ arrowHtml1 + "</a>"+levelThreeUlHtml+"</li>" + levelTwoSelectedHtml ;
										levelTwoUlHtml += "<li queryCode='"+levelTwoResources[levelTwoResourcesKey].queryCode+"' resid='"+levelTwoResources[levelTwoResourcesKey].resId+"' class='" + levelTwoActive + "'><a id='"+levelTwoResources[levelTwoResourcesKey].resId+"' taget='" + href + "' onclick=leftMenus(\'"+levelTwoResources[levelTwoResourcesKey].resId+"\',this);> " + levelTwoResources[levelTwoResourcesKey].resourceName+ arrowHtml1 + "</a>"+levelThreeUlHtml+"</li>" + levelTwoSelectedHtml ;

									}

									if (levelTwoUlHtml.length > 0) {//有子菜单
										levelTwoUlHtml = "<ul class='sub-menu'>" + levelTwoUlHtml + "</ul>";
									}
									if(levelOneResources[levelOneResourcesKey].children.length>0){
										arrowHtml = "<span class='arrow" + levelOneOpen + "'></span>";
									}else{
										arrowHtml = "";
									}

									if (levelOneResources[levelOneResourcesKey].url != null && levelOneResources[levelOneResourcesKey].url.length > 0) {
										href = "${path}" + levelOneResources[levelOneResourcesKey].url;
									} else {
										href = "#";
									}
									//childUlHtml += "<li class='" + childActive + "'><a href='" + href + "'>" + childrens[childrenKey].resourceName + "</a></li>" + selectedHtml;


									if ($("#moduleUl") != null) {
										$("#moduleUl").append("<li queryCode='"+levelOneResources[levelOneResourcesKey].queryCode+"' resid='"+levelOneResources[levelOneResourcesKey].resId+"' class='" + levelOneActive + levelOneOpen + datas[key].resId+ "'><a id='"+levelOneResources[levelOneResourcesKey].resId+"' taget='" + href + "' onclick=leftMenus(\'"+levelOneResources[levelOneResourcesKey].resId+"\',this);>" +
												"<i class='"+levelOneResources[levelOneResourcesKey].resourceIcon+"'></i><span class='title'>" + levelOneResources[levelOneResourcesKey].resourceName + "</span>" +
												levelOneSelectedHtml + arrowHtml +
												"</a>" + levelTwoUlHtml + "</li>");
									}
								}
							}

							$(".systemLi").removeClass("active");//把所有系统的激活去掉
							var id = $('.systemLi').first()[0].getAttribute('resid');
							if (iscode==="true") {
								$(".page-sidebar .sidebar-toggler").trigger("click");
							}
							$("#moduleUl").children().not($(".nudisplay")).hide();
							$("."+id).show();

							if(currentPage!=null){
								var currentLocation = currentPage.split(",");
								/* for(var i in currentLocation){
								 alert(currentLocation[i]);
								 } */
								if(currentLocation.length>0&&currentLocation[0]!=null){

									var topMenu = $(".systemLi[querycode='"+currentLocation[0]+"']");
									//topMenus(topMenu.attr("resid"),topMenu.find("a"),false);
									var topMenus = topMenu.attr("resid");
									$(".systemLi").removeClass("active");
									$(".systemLi[resid='" + topMenus + "']").addClass("active");//把现在所在系统样式激活为
									$("#moduleUl").children().not($(".nudisplay")).hide();
									if ($("." + topMenus).length <= 0) {
										$(".page-sidebar").hide();
										$(".page-content").css("margin-left", "0");
									} else {
										$("." + topMenus).show();
										$(".page-sidebar").show();
										$(".page-content").css("margin-left", "180px");
									}
									if(currentLocation.length>1){
										var leftMenu = $("li[querycode='"+currentLocation[1]+"']");
										$("#moduleUl li").removeClass("open").removeClass("active");
										leftMenu.addClass("open").addClass("active");
										var a = $(leftMenu.find('a').first()[0]);
										a.find(".arrow").remove();
										a.append(" <span class='selected '></span> ");
									}
									if(currentLocation.length>2){
										$("li[querycode='"+currentLocation[2]+"']").addClass("active");
									}
									if(currentLocation.length>3){
										$("li[querycode='"+currentLocation[3]+"']").addClass("active");
									}
								}else{
									//topMenus($('.systemLi').first()[0].getAttribute('resid'),$($('.systemLi').first()[0]).find("a"),true);
								}
							}

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
			getMessage();
			setInterval(getMessage, 30000);
		});
		function topMenus(topMenus,url,bool){

			$(".systemLi").removeClass("active");
			$(".systemLi[resid='"+topMenus+"']").addClass("active");
			$("#moduleUl").children().not($(".nudisplay")).hide();
			if($("."+topMenus).length<=0){
				$(".page-sidebar").hide();
				$(".page-content").css("margin-left","0");
			}else{
				$("."+topMenus).show();
				$(".page-sidebar").show();
				$(".page-content").css("margin-left","180px");
			}
			if(bool){
				var url = $(url).attr('taget');
				/*if(url.indexOf('?')>0){
				 url +="&code="+topMenus;
				 }else{
				 url +="?code="+topMenus;
				 } */
				var len = url.indexOf("#");
				len = len >= 0 ? len : url.length;
				if(url.indexOf('?')>0){
					url = url.substr(0,len) + "&_show="+$('body').hasClass("page-sidebar-closed") + url.substr(len);
				}else{
					url = url.substr(0,len) + "?_show="+$('body').hasClass("page-sidebar-closed") + url.substr(len);
				}
				window.location.href=url;
				//initMenus(topMenus,url);
				//window.location.href=$(url).attr('taget');
				//alert(topMenus);
			}
		}

		function leftMenus(leftMenus,url,bool){

			var ul = $("#"+leftMenus+" +ul");
			var length = ul.length;
			var a = $("#"+leftMenus);
			var findarrow = a.find(".arrow");
			if (findarrow.hasClass("open")){
				findarrow.removeClass("open");
			}else{
				findarrow.addClass("open");
			}
			if(length<=0){

				var url = $(url).attr('taget');
				var len = url.indexOf("#");
				len = len >= 0 ? len : url.length;
				if(url.indexOf('?')>0){
					url = url.substr(0,len) + "&_show="+$('body').hasClass("page-sidebar-closed") + url.substr(len);
				}else{
					url = url.substr(0,len) + "?_show="+$('body').hasClass("page-sidebar-closed") + url.substr(len);
				}
				//$("li[resid="+leftMenus+"]").addClass("active");
				//window.leftMenusid = leftMenus;
				window.location.href=url;
				//initMenus(leftMenus,url);
			}else{
				a.append(" <span class='selected '></span> ");
				var state = ul.css("display");
				if(state == "none") {
					$("li[resid="+leftMenus+"]").addClass("open");
					ul.slideDown(200);
				} else{
					$("li[resid="+leftMenus+"]").removeClass("open");
					ul.slideUp(200);
				}
			}
		}

		function initMenus(id,url){
			$.post("${path}/"+tenantUrl+"/resource/link",{"menuCode":id}, function(data){
				if(data.success){
					window.location.href=url;
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
						if(!data.count){
							data.count = 0;
						}
						var start = "<li><p>您有" + data.count
								+ "条新消息</p></li>";
						var conent = "";
						var message = data.message;
						for ( var i in message) {
							conent += "<li><a href='${path }/" + tenantUrl + "/message/messages?messageType="
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
								 src="${path }/sys/tenant/user/headimg/${userId}">
							<span class="username"><%=username%></span>
							<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<%=path%>/sys/tenant/user/profile">
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
								 src="${path }/sys/admin/user/photo/<%=headPhoto%>">
							<span class="username"><%=username%></span>
							<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<%=path%>/sys/admin/user/profile">
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



	<!-- BEGIN HORIZONTAL MENU PAGE SIDEBAR1 -->

	<div class="page-sidebar nav-collapse">
		<div class="scroller" data-height="100%" data-always-visible="1" data-rail-visible="1" style=" padding-bottom:50px;">
			<ul class="page-sidebar-menu hidden-phone hidden-tablet"
				id="moduleUl">
				<li class="nudisplay" style="">
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
			</ul>
		</div>
		<!-- END SIDEBAR MENU -->

	</div>
	<div class="page-content" id="page-content">
		<sitemesh:write property='body' />
	</div>
</div>
</body>
</html>
