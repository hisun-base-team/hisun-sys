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
.navbar-inner a{
	border-bottom: none !important;
}

li {
	cursor: pointer;
}

.page-sidebar .sidebar-toggler {
	opacity: 0.5;
	/* margin-left: 130px; */
}
.sidebar-toggler {
  background-image: url(${path}/images/templateImage/sidebar-toggler.jpg);
  background-color: #242424;
}
.page-sidebar .sidebar-search input {
  width: 120px;
}
.page-sidebar .sidebar-search .submit {
  background-image: url(${path}/images/templateImage/search-icon.png);
}
.page-sidebar .sidebar-search {
  margin: 8px 20px 20px 20px;
  opacity: 1;
}

.topUl {
	margin-top: 0!important;
}

.modal {
	left : 0 !important;
	margin-left: 0 !important
}

.odd {
	background-color: rgba(100,100,100,0.3) !important
}

.table-bordered td {
	/*color: #FFFFFF !important;*/
	background-color: transparent !important
}

.table thead tr th {
	background-color: transparent !important
}
/* 
.row-open {
	z-index:0 !important;
} */

.webuploader-element-invisible{ position:absolute; left:0; top:0; height:35px !important;filter: Alpha(opacity=0);-moz-opacity:0;opacity:0; cursor:pointer;  width:100px !important;}

</style>
<script>
var iscode = "${param.privilegeCode}";
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
						//左侧菜单
						var selectedHtml = "";//当前选中的样式html
						var arrowHtml = "";//模块菜单右侧可折叠箭头的样式html
						var open = "";//表示是否展开的样式
						var childrens = datas[key].children;
						
						for(var childrenKey in childrens){
							var childUlHtml = "";
							var childActive = "";
							
							if (childrens[childrenKey].sort == 1) {
								active = " active ";
								open = " open ";
								selectedHtml = " <span class='selected '></span> "
							} else {
								active = " ";
								selectedHtml = " ";
								open = " ";
							}
							
							var childrens1 =childrens[childrenKey].children;
							for(var childrenKey1 in childrens1){
								if(childrens1[childrenKey1].resourceType==1){//操作直接跳出当前循环
									continue;
								}
								if (childrens1[childrenKey1].sort == 1) {
									childActive = " active";
									selectedHtml = " <span class='selected '></span> ";
								} else {
									childActive = "";
									selectedHtml = "";
								}
								if (childrens1[childrenKey1].url != null && childrens1[childrenKey1].url.length > 0) {
									href = "${path}" + childrens1[childrenKey1].url;
								} else {
									href = "#";
								}
								childUlHtml += "<li queryCode='"+childrens1[childrenKey1].queryCode+"' resid='"+childrens1[childrenKey1].resId+"' class='" + childActive + "'><a id='"+childrens1[childrenKey1].resId+"' taget='" + href + "' onclick=leftMenus(\'"+childrens1[childrenKey1].resId+"\',this);> " + childrens1[childrenKey1].resourceName + "</a></li>" + selectedHtml;
							}
							
							if (childUlHtml.length > 0) {//有子菜单
								childUlHtml = "<ul class='sub-menu'>" + childUlHtml + "</ul>";
								
								arrowHtml = "<span class='arrow" + open + "'></span>"
								
							}
							
							if (childrens[childrenKey].url != null && childrens[childrenKey].url.length > 0) {
								href = "${path}" + childrens[childrenKey].url;
							} else {
								href = "#";
							}
							//childUlHtml += "<li class='" + childActive + "'><a href='" + href + "'>" + childrens[childrenKey].resourceName + "</a></li>" + selectedHtml;
							
							
							if ($("#moduleUl") != null) {
								$("#moduleUl").append("<li queryCode='"+childrens[childrenKey].queryCode+"' resid='"+childrens[childrenKey].resId+"' class='" + active + open + datas[key].resId+ "'><a id='"+childrens[childrenKey].resId+"' taget='" + href + "' onclick=leftMenus(\'"+childrens[childrenKey].resId+"\',this);> <i" +
										"class='icon-cogs'></i> <span class='title'>" + childrens[childrenKey].resourceName + "</span>" + 
										selectedHtml + arrowHtml +
								"</a>" + childUlHtml + "</li>");
							}
						}
					}
					//topMenus($('.systemLi').first()[0].getAttribute('resid'),$($('.systemLi').first()[0]).find("a"),true);
					$(".systemLi").removeClass("active");
					var id = $('.systemLi').first()[0].getAttribute('resid');
					$(".systemLi[resid='"+id+"']").addClass("active");
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
							$(".systemLi[resid='" + topMenus + "']").addClass("active");
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
						}else{
							//topMenus($('.systemLi').first()[0].getAttribute('resid'),$($('.systemLi').first()[0]).find("a"),true);
						}
					}
					//$(".page-sidebar .sidebar-toggler").trigger("click");
					if (iscode==="true") {
					   $(".page-sidebar .sidebar-toggler").trigger("click");
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
		getMessage();
		setInterval(getMessage, 30000);
		$('.header .nav li.lired').hover(function(e) {
			 $(this).find('i.icon-bell-alt').css('color','#f30')
		 	},function(){
			 $(this).find('i.icon-bell-alt').css('color','#8a8a8a')
       });
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
			url +="&privilegeCode="+topMenus;
		}else{
			url +="?privilegeCode="+topMenus;
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
<%@include file="inc/waitModal.jsp" %>
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

				<ul class="nav pull-right topUl">
					<!-- <li class="dropdown"><a href="mailto:30SCloud@30wish.net"
						class="dropdown-toggle"> <i class="icon-warning-sign"></i>
							BUG报告
					</a></li> -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<c:if test="${!empty userId && username ne 'admin'}">
						<li class="dropdown lired hide" id="header_notification_bar">
							<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> 
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
                                     src="${path }/sys/tenant/user/photo/<%=headPhoto%>">
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



		<!-- BEGIN HORIZONTAL MENU PAGE SIDEBAR1 -->

		<div class="page-sidebar nav-collapse" style="z-index:200;">

			<!-- BEGIN SIDEBAR MENU -->

			<ul class="page-sidebar-menu hidden-phone hidden-tablet"
				id="moduleUl" style="padding-top:61px">
				
			</ul>

			<!-- END SIDEBAR MENU -->

		</div>
		<div class="page-content" id="page-content" style="background-color:rgba(45, 62, 80, 0.82);padding:0px;">
			<sitemesh:write property='body' />
		</div>
	</div>
</body>
</html>
