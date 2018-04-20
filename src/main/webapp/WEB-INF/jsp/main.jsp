<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><sitemesh:write property="title"/></title>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<sitemesh:write property="head"/>
<style type="text/css">
li {
    cursor: pointer;
}
.page-sidebar .sidebar-toggler {
    opacity: 0.5;
}
#tipModal {
    z-index:20000!important;
}
#confirmModal {
    z-index: 20000 !important;
}
</style>
</head>
<body class="page-header-fixed">
<!-- BEGIN CONFIRM&TIP@IMPORTANT MODAL DIV  -->
<div id="confirmModal" class="modal container hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitle" >提示</h3>
    </div>
    <div class="modal-body mt10" id="confirmModalContent">
        <p class="font16">这里放置需弹窗宽度自适应的内容...</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="insureBtn"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>
<div id="confirmModal1" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitle1" >提示</h3>
    </div>
    <div class="modal-body" id="confirmModalContent1">
        <p class="font16">这里放置需弹窗宽度自适应的内容...</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="insureBtn1"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>
<div id="confirmModalBox" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitleBox" >提示</h3>
    </div>
    <div class="modal-body" id="confirmModalContentBox">
        <p class="font16">这里放置需弹窗宽度自适应的内容...</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="insureBtnBox"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="tipConfirmModalBox" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="tipConfirmModalTitleBox" >提示</h3>
    </div>
    <div class="modal-body" id="tipConfirmModalContentBox">
        <p class="font16">这里放置需弹窗宽度自适应的内容...</p>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="confirmModal2" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitle2" >提示</h3>
    </div>
    <div class="modal-body" id="confirmModalContent2">
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="confirmBtn"><i class="icon-ok"></i> 确 定</button>
        <button type="button" class="btn" id="cancalBtn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="messageModal1" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="messageModalTitle1" >提示</h3>
    </div>
    <div class="modal-body" id="messageModalContent1">
    </div>
    <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="messageModal" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="messageModalTitle" >提示</h3>
    </div>
    <div class="modal-body" style="text-align: center;">
        <div class="Cue_modal" style="text-align:center"><img src="${path}/images/templateImage/ico_error.png"><span style="padding-left:15px;" id="messageModalContent">您确定要进行该操作吗？</span></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="messageModal2" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3>提示</h3>
    </div>
    <div class="modal-body" style="text-align: center;">
        <div class="Cue_modal" style="text-align:center"><img src="${path}/images/templateImage/SuccessICO.png"><span style="padding-left:15px;" id="messageModalContent2">您确定要进行该操作吗？</span></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>
<div id="tipModal" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="tipModalTitle"></h3>
    </div>
    <div class="modal-body">
        <div class="tipModalContent"></div>
    </div>

    <!-- <div class="modal-footer">
        <button type="button" class="btn green"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn  black"><i class="icon-remove icon-white"></i> 关 闭</button>
    </div> -->
</div>
<div id="Prompt" class="modal hide fade" tabindex="-1" data-backdrop="static" data-width="560" >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="PromptClose"></button>
        <h3 id="PromptmodalTitle">您确定要删除123拓展信息吗？</h3>
    </div>
    <div class="modal-body">
        <div class="Cue_Prompt_imp">请认真阅读以下说明！</div>
        <div class="Cue_Prompt_imp02" id="PrompModalContent">这个操作不能撤消。这将永久删除123拓展，删除后将不可恢复，请认真审核确认后再进行操作，确认后请在下面填写你要删除的信息。</div>
        <div class="row-fluid">
            <div class="inp_modalname" id="PrompModalContent01">请输入你要删除的扩展信息名称：</div>
            <input type="text" value="" name="PromptName" id ="PromptName" style="cursor: pointer;" class="m-wrap span12" size="">
        </div>
        <div class="modal_but">
            <a class="mobut" href="###" id="PromptBtn">我已了解清楚，确认删除</a>
        </div>
    </div>
</div>
<!-- BEGIN CONFIRM&TIP@IMPORTANT MODAL DIV -->
<div class="header navbar navbar-inverse navbar-fixed-top">
    <!-- BEGIN TOP NAVIGATION BAR -->
    <div class="navbar-inner">
        <div class="container-fluid">
            <!-- BEGIN LOGO -->
            <c:choose>
                <c:when test="${username ne 'admin'}">
                    <a class="brand" href="${path}/dashboard" style="margin-left: 0px;">
                            <%-- <img src="<%=path%>/images/logo-mid.png" alt="logo" /> --%>
                        <img style="max-height: 32px;" src="<%=path%>/images/${mainLogo}" alt="logo"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="brand" href="javaScript:;" style="margin-left: 0px;">
                        <img style="max-height: 32px;" src="<%=path%>/images/${mainLogo}" alt="logo"/>
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
                <shiro:hasPermission name="adminticekt:ticketservice ">
                    <li class="dropdown lired" id="header_notification_bar">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="username">工单服务</span>
                            <i class="icon-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu extended notification"
                            style="width: 120px !important;min-width: 120px !important;">
                            <shiro:hasPermission name="adminticket:ticket_createIndex">
                                <li>
                                    <a style="width: 100px;"
                                       href="${path}/sys/admin/ticket/createIndex?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                        <i class="icon-pencil"></i> 提交工单
                                    </a>
                                </li>
                                <li>
                                    <a style="width: 100px;"
                                       href="${path}/sys/admin/ticket/mine?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                        <i class="icon-folder-open"></i> 我的工单</a>
                                </li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="adminticket:ticket_customadmin">
                                <li>
                                    <a style="width: 100px;"
                                       href="${path}/sys/admin/ticket/custom/admin/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                        <i class="icon-pencil"></i> 工单列表
                                    </a>
                                </li>
                            </shiro:hasPermission>
                        </ul>
                    </li>
                </shiro:hasPermission>
                <c:if test="${!empty tenant.id}">
                    <li class="dropdown user">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img alt="" style=" height:29px; width:29px; border-radius:50%;padding: 0 6px 0 0;"
                                 src="${path }/sys/tenant/user/headimg/${userId}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                            <span class="username"><%=username%></span>
                            <i class="icon-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="${path }/sys/tenant/user/profile?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                    <i class="icon-user"> 个人中心</i></a>
                            </li>
                            <li><a href="${path }/logout">
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
                            <li>
                                <a href="${path}/sys/admin/user/profile?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                                    <i class="icon-user"> 个人中心</i></a>
                            </li>
                            <li><a href="${path}/admin/logout" id="logout">
                                <i class="icon-off"></i> 退出</a>
                            </li>

                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <!-- END TOP NAVIGATION BAR -->
</div>
<div class="page-container row-fluid">
    <!-- BEGIN HORIZONTAL MENU PAGE  -->
    <div class="page-sidebar nav-collapse">
        <div class="scroller" data-height="100%" data-always-visible="1" data-rail-visible="1" style="padding-bottom:50px;">
            <ul class="page-sidebar-menu hidden-phone hidden-tablet"
                id="moduleUl">
                <li class="nudisplay" style="">
                    <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                    <div class="sidebar-toggler hidden-phone"></div>
                    <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                </li>
            </ul>
        </div>
    </div>
    <!-- END HORIZONTAL MENU PAGE  -->
    <!-- BEGIN CONTENT PAGE  -->
    <div class="page-content" id="page-content">
        <sitemesh:write property='body'/>
    </div>
    <!-- END CONTENT PAGE  -->
</div>
<!-- BEGIN MENU SCRIPT  -->
<script type="text/javascript">
    var iscode = "${param._show}";//是否展开左侧菜单
    var tenantUrl = "sys/admin";
    var sidebarHtml = "";//页面一开始左侧导航中的html
    jQuery(document).ready(function () {
        var tenant = "${tenant}";
        if (tenant) {
            tenantUrl = "sys/tenant"
        }
        $(".dropdown").on("click", function (event) {
            event.stopPropagation();
            if ($(this).hasClass("open")) {
                $(this).removeClass("open");
            } else {
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

        getSidebar();
        getMenus();

        App.init();//必须，不然导航栏及其菜单无法折叠
        getMessage();
        setInterval(getMessage, 30000);
    });

    /**
     * 获取一开始左侧导航已有的html
     */
    function getSidebar() {
        if ($("#moduleUl") != null) {
            sidebarHtml = $("#moduleUl").html();
        }
    }
    /**
     * 初始化菜单
     */
    function getMenus(reInit) {
        localPost("${path}/sys/resource/getMenu", null,
                function (data, status) {
                    if (status == "success") {
                        var datas = data.data;
                        if ($("#systemUl") != null) {
                            $("#systemUl").html('');//清空原有菜单
                        }
                        if ($("#moduleUl") != null) {
                            $("#moduleUl").html(sidebarHtml);//重置左侧菜单为一开始的样子
                            if (reInit === true) {
                                App.init();
                            }//使左侧的伸缩按钮生效
                        }
                        for (var key in datas) {
                            if (datas[key].resourceType != 2) {
                                //第一层永远是系统类型，如果不是则跳过不显示，去资源管理重新排查为什么不是系统类型。下面的ALTER是给开发时候排查的
                                continue;
                            }
                            if (datas[key].url != null && datas[key].url.length > 0) {
                                href = "${path}" + datas[key].url;
                            } else {
                                href = "#";
                            }

                            if ($("#systemUl") != null) {
                                //顶部横向菜单
                                //按目前(20160510)的下面这个判断完全用不上，永远都会去到ELSE判断里面
                                if (datas[key].permission === 'user:all' || datas[key].permission === 'tenant:usercenter' || datas[key].permission === 'adminticekt:ticketservice') {

                                    $("#systemUl").append("<li queryCode='" + datas[key].queryCode + "' style='display: none' class='systemLi' id='" + datas[key].resId + "' resid='" + datas[key].resId + "' pid='" + datas[key].pId + "'><a taget='" + href + "' onclick=topMenus(\'" + datas[key].resId + "\',this,true);> " + datas[key].resourceName + " <span class='selected'></span> </a></li>")
                                } else {

                                    $("#systemUl").append("<li queryCode='" + datas[key].queryCode + "' class='systemLi' id='" + datas[key].resId + "' resid='" + datas[key].resId + "' pid='" + datas[key].pId + "'><a taget='" + href + "' onclick=topMenus(\'" + datas[key].resId + "\',this,true);> " + datas[key].resourceName + " <span class='selected'></span> </a></li>")
                                }
                            }
                            //左侧菜单
                            var levelOneResources = datas[key].children;
                            //左侧菜单第一层
                            for (var levelOneResourcesKey in levelOneResources) {
                                var levelTwoUlHtml = "";
                                var childActive = "";

                                var levelTwoResources = levelOneResources[levelOneResourcesKey].children;
                                //左侧菜单第二层
                                for (var levelTwoResourcesKey in levelTwoResources) {
                                    var levelThreeUlHtml = "";
                                    var levelThreeActive = "";

                                    if (levelTwoResources[levelTwoResourcesKey].resourceType == 1) {//操作类型资源直接跳出当前循环
                                        continue;
                                    }

                                    if (levelTwoResources[levelTwoResourcesKey].url != null && levelTwoResources[levelTwoResourcesKey].url.length > 0) {
                                        href = "${path}" + levelTwoResources[levelTwoResourcesKey].url;
                                    } else {
                                        href = "#";
                                    }

                                    var levelThreeResources = levelTwoResources[levelTwoResourcesKey].children;
                                    //左侧菜单第三层
                                    for (var levelThreeResourcesKey in levelThreeResources) {

                                        if (levelThreeResources[levelThreeResourcesKey].resourceType == 1) {//操作类型直接跳出当前循环
                                            continue;
                                        }
                                        if (levelThreeResources[levelThreeResourcesKey].url != null && levelThreeResources[levelThreeResourcesKey].url.length > 0) {
                                            href = "${path}" + levelThreeResources[levelThreeResourcesKey].url;
                                        } else {
                                            href = "#";
                                        }
                                        levelThreeUlHtml += "<li queryCode='" + levelThreeResources[levelThreeResourcesKey].queryCode + "' resid='" + levelThreeResources[levelThreeResourcesKey].resId + "' pid='" + levelThreeResources[levelThreeResourcesKey].pId + "' >"
                                                + "<a id='" + levelThreeResources[levelThreeResourcesKey].resId + "' taget='" + href + "' onclick=leftMenus(\'" + levelThreeResources[levelThreeResourcesKey].resId + "\',this);> " + levelThreeResources[levelThreeResourcesKey].resourceName + "</a>"
                                                + "</li>";
                                    }

                                    if (levelThreeUlHtml.length > 0) {//有子菜单
                                        levelThreeUlHtml = "<ul class='sub-menu'>" + levelThreeUlHtml + "</ul>";
                                    }
                                    levelTwoUlHtml += "<li queryCode='" + levelTwoResources[levelTwoResourcesKey].queryCode + "' resid='" + levelTwoResources[levelTwoResourcesKey].resId + "' pid='" + levelTwoResources[levelTwoResourcesKey].pId + "' >"
                                            + "<a id='" + levelTwoResources[levelTwoResourcesKey].resId + "' taget='" + href + "' onclick=leftMenus('" + levelTwoResources[levelTwoResourcesKey].resId + "',this);> " + levelTwoResources[levelTwoResourcesKey].resourceName
                                            + (levelThreeResources.length > 0 ? "<span class='arrow'></span>" : "")
                                            + "</a>"
                                            + levelThreeUlHtml
                                            + "</li>";

                                }

                                if (levelTwoUlHtml.length > 0) {//有子菜单
                                    levelTwoUlHtml = "<ul class='sub-menu'>" + levelTwoUlHtml + "</ul>";
                                }
                                if (levelOneResources[levelOneResourcesKey].url != null && levelOneResources[levelOneResourcesKey].url.length > 0) {
                                    href = "${path}" + levelOneResources[levelOneResourcesKey].url;
                                } else {
                                    href = "#";
                                }

                                if ($("#moduleUl") != null) {
                                    $("#moduleUl").append("<li queryCode='" + levelOneResources[levelOneResourcesKey].queryCode + "' resid='" + levelOneResources[levelOneResourcesKey].resId + "' pid='" + levelOneResources[levelOneResourcesKey].pId + "' class='" + datas[key].resId + "'>" +
                                            "<a id='" + levelOneResources[levelOneResourcesKey].resId + "' taget='" + href + "' onclick=leftMenus(\'" + levelOneResources[levelOneResourcesKey].resId + "\',this);>" +
                                            "<i class='" + levelOneResources[levelOneResourcesKey].resourceIcon + "'></i><span class='title'>" + levelOneResources[levelOneResourcesKey].resourceName + "</span>" +
                                            (levelTwoResources.length > 0 ? "<span class='arrow'></span>" : "") + "</a>" + levelTwoUlHtml + "</li>");
                                }
                            }
                        }

                        var id = $('.systemLi').first()[0].getAttribute('resid');
                        if (iscode === "true") {
                            $(".page-sidebar .sidebar-toggler").trigger("click");
                        }
                        $("#moduleUl").children().not($(".nudisplay")).hide();
                        $("." + id).show();

                        if (menuResourceId != null && menuResourceId.length > 0) {
                            var resourceTag = $("#" + menuResourceId);
                            if (resourceTag.length > 0) {
                                var hasLeftMenu = false;//是否有经过左边菜单的判断
                                while (resourceTag.length > 0) {
                                    if (resourceTag[0].tagName == 'A') {
                                        resourceTag.parent().addClass("active");//菜单高亮
                                        resourceTag = $("#" + resourceTag.parent().attr("pid"));//上一级菜单
                                        hasLeftMenu = true;
                                    } else if (resourceTag[0].tagName == 'LI') {
                                        resourceTag.addClass("active");
                                        topMenus(resourceTag.attr("resid"), resourceTag.find("a"), false);
                                        //把左边菜单设置显示
                                        var leftMenuLevelOne = $("#moduleUl li[pid='" + resourceTag.attr("pid") + "']");
                                        leftMenuLevelOne.show();
                                        leftMenuLevelOne.find("ul li").show();
                                        break;
                                    }
                                }
                                if (!hasLeftMenu) {
                                    var childrenFirstMenu = $("#moduleUl li[pid='" + menuResourceId + "']:first");
                                    childrenFirstMenu.addClass("active");
                                }
                                $("#moduleUl").find("li.active > a").append("<span class='selected'></span>");
                                $("#moduleUl").find("li.active > a > .arrow").addClass("open");
                            }
                            /*var topMenu = $(".systemLi[querycode='"+currentLocation[0]+"']");
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
                             }*/
                        } else {
                            var firstMenu = $(".systemLi:first");
                            firstMenu.addClass("active");
                            firstMenu.children("a").append("<span class='selected'></span>");
                            var childrenMenus = $("#moduleUl li[pid='" + firstMenu.attr("resid") + "']");
                            var menuLevel = 1;
                            while (childrenMenus.length > 0) {
                                childrenMenus.show();//显示子菜单;
                                var nextLevelChildrenMenus = $(childrenMenus[0]).children("ul li");
                                if (nextLevelChildrenMenus.length > 0) {
                                    //有子菜单，父级菜单要打开
                                    $(childrenMenus[0]).addClass("active");
                                    $(childrenMenus[0]).children("a").children(".arrow").addClass("open");
                                    if (menuLevel == 1) {
                                        $(childrenMenus[0]).children("a").append("<span class='selected'></span>");
                                    }
                                    childrenMenus = nextLevelChildrenMenus;
                                    menuLevel++;
                                } else {
                                    break;
                                }
                            }

                        }

                    }
                }, "json", {"OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"});
    }

    function topMenus(topMenus, url, bool) {

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
        if (bool) {
            var url = $(url).attr('taget');
            var len = url.indexOf("#");
            len = len >= 0 ? len : url.length;
            if (url.indexOf('?') > 0) {
                url = url.substr(0, len) + "&_show=" + $('body').hasClass("page-sidebar-closed") + url.substr(len) + "&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
            } else {
                url = url.substr(0, len) + "?_show=" + $('body').hasClass("page-sidebar-closed") + url.substr(len) + "&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
            }
            window.location.href = url;
        }
    }

    function leftMenus(leftMenus, url, bool) {

        var ul = $("#" + leftMenus + " +ul");
        var length = ul.length;
        var a = $("#" + leftMenus);
        var findarrow = a.find(".arrow");
        if (findarrow.hasClass("open")) {
            findarrow.removeClass("open");
        } else {
            findarrow.addClass("open");
        }
        if (length <= 0) {

            var url = $(url).attr('taget');
            var len = url.indexOf("#");
            len = len >= 0 ? len : url.length;
            if (url.indexOf('?') > 0) {
                url = url.substr(0, len) + "&_show=" + $('body').hasClass("page-sidebar-closed") + url.substr(len) + "&OWASP_CSRFTOKEN=${OWASP_CSRFTOKEN}";
            } else {
                url = url.substr(0, len) + "?_show=" + $('body').hasClass("page-sidebar-closed") + url.substr(len) + "&OWASP_CSRFTOKEN=${OWASP_CSRFTOKEN}";
            }
            //$("li[resid="+leftMenus+"]").addClass("active");
            //window.leftMenusid = leftMenus;
            window.location.href = url;
            //initMenus(leftMenus,url);
        } else {
            var state = ul.css("display");
            if (state == "none") {
                $("li[resid=" + leftMenus + "]").addClass("open");
                ul.slideDown(200);
            } else {
                $("li[resid=" + leftMenus + "]").removeClass("open");
                ul.slideUp(200);
            }
        }
    }

    function initMenus(id, url) {
        localPost("${path}/" + tenantUrl + "/resource/link", {"menuCode": id}, function (data) {
            if (data.success) {
                window.location.href = url;
            }
        }, "json", {"OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"});
    }
    function getMessage() {
        var messages = $("#messages");
        var message_badge = $("#message_badge");
        //发送post请求
        localPost("${path}/" + tenantUrl + "/message/pending", null,
                function (data) {
                    messages.empty();
                    message_badge.text(data.count);
                    if (!data.count) {
                        data.count = 0;
                    }
                    var start = "<li><p>您有" + data.count
                            + "条新消息</p></li>";
                    var conent = "";
                    var message = data.message;
                    for (var i in message) {
                        conent += "<li><a style=\"white-space: inherit;\" href='${path }/" + tenantUrl + "/message/messages?messageType="
                                + message[i].type
                                + "&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'><i class=\"icon-warning-sign\"></i>"
                                + message[i].title + "</a></li>";
                    }

                    var end = "<li class=\"external\"><a href=\"${path }/" + tenantUrl + "/message/messages?type=all&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}\">查看所有消息<i class=\"m-icon-swapright\"></i></a></li> ";
                    messages.append(start + conent + end);
                }, "json", {"OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"});
    }
</script>
<!-- END MENU SCRIPT  -->
<!-- BEGIN CONFIRM&TIP@IMPORTANT MODAL SCRIPT  -->
<script type="text/javascript">
    var confirmRet = false;//确认框的返回值，默认为false，只有点击确认按钮才返回true
    var urlOfconfirmModal = null;
    var dataOfconfirmModal = null;
    var callBackOfconfirmModal = null;
    var loadingModal = null;
    var defaultConfirmTitle = $("#confirmModalTitle").html();
    var defaultConfirmContent = $("#confirmModalContent").html();
    var defaultConfirmTitle1 = $("#confirmModalTitle1").html();
    var defaultConfirmContent1 = $("#confirmModalContent1").html();
    var defaultConfirmTitleBox = $("#confirmModalTitleBox").html();
    var defaultConfirmContentBox = $("#confirmModalContentBox").html();
    var defaultTipConfirmTitleBox = $("#tipConfirmModalTitleBox").html();
    var defaultTipConfirmContentBox = $("#tipConfirmModalContentBox").html();
    var onfirFlag = false;
    $("#insureBtn").click(function() {
        confirmRet = true;
        $('#confirmModal').modal('hide');
        if (confirmRet) {
            localPost(urlOfconfirmModal, dataOfconfirmModal, callBackOfconfirmModal, "json", {
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });
        }
        initConfirmModal();
    });
    $("#insureBtn1").click(function() {
        confirmRet = true;
        $('#confirmModal1').modal('hide');
        if (confirmRet) {
            localPost(urlOfconfirmModal, dataOfconfirmModal, callBackOfconfirmModal,"json",{
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });
        }
        initConfirmModal1();
    });
    $("#insureBtnBox").click(function() {
        onfirFlag = true;
        $('#confirmModalBox').modal('hide');
        if (onfirFlag) {
            if(null!=loadingModal)
            {
                loadingModal.show();
            }
            localPost(urlOfconfirmModal, dataOfconfirmModal, callBackOfconfirmModal,"json",{
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });
        }
    });

    /**
     * 为提示框设置标题
     */
    function setConfirmTitle(title) {
        $("#confirmModalTitle").html(title);
    }

    function setConfirmTitle1(title) {
        $("#confirmModalTitle1").html(title);
    }
    /**
     * 设置提示框的提示语
     */
    function setConfirmContent(content) {
        $("#confirmModalContent").html(content);
    }

    function setConfirmContent1(content) {
        $("#confirmModalContent1").html(content);
    }

    function setConfirmContentBox(content) {
        $("#confirmModalContentBox").html(content);
    }

    function setTipConfirmContentBox(content) {
        $("#tipConfirmModalContentBox").html(content);
    }

    function showMessage(content) {
        $("#messageModalContent").html(content);
        $('#messageModal').modal('show');
    }

    function showConfirm(name, operation) {
        var tempInConfirmModal = name;
        if (name != null && name.length > 0) {
            tempInConfirmModal = "“" + name + "”";
        }
        var defaultOperation ="删除";
        if (operation != null) {
            defaultOperation = operation;
        }
        setConfirmContent("确定要" + defaultOperation + tempInConfirmModal + "吗？");
        $('#confirmModal').modal('show');
    }

    function showConfirm1(name, operation) {
        var tempInConfirmModal = name;
        if (name != null && name.length > 0) {
            tempInConfirmModal = "“" + name + "”";
        }
        var defaultOperation ="删除";
        if (operation != null) {
            defaultOperation = operation;
        }
        setConfirmContent1("确定要" + defaultOperation + tempInConfirmModal + "吗？");
        $('#confirmModal1').modal('show');
    }

    function showConfirmBox(name, operation) {
        var tempInConfirmModal = name;
        if (name != null && name.length > 0) {
            tempInConfirmModal = "“" + name + "”";
        }
        var defaultOperation ="删除";
        if (operation != null) {
            defaultOperation = operation;
        }
        setConfirmContentBox("确定要" + defaultOperation + tempInConfirmModal + "吗？");
        $('#confirmModalBox').modal('show');
    }

    function showTipConfirmBox(name, operation) {
        var tempInConfirmModal = name;
        if (name != null && name.length > 0) {
            tempInConfirmModal = "“" + name + "”";
        }
        var defaultOperation ="";
        if (operation != null) {
            defaultOperation = operation;
        }
        setTipConfirmContentBox( defaultOperation + tempInConfirmModal );
        $('#tipConfirmModalBox').modal('show');
    }

    function showConfirm2(data) {
        $('#confirmModalContent2').html(data.content);
        $("#confirmBtn").unbind().bind('click',function() {
            $('#confirmModal2').modal('hide');
            if($.isFunction(data.confirm)){
                data.confirm();
            }
        });
        $("#cancalBtn").unbind().bind('click',function() {
            $('#confirmModal2').modal('hide');
            if($.isFunction(data.cancal)){
                data.cancal();
            }
        });
        $('#confirmModal2').modal('show');
    }

    function showMessage1(content) {
        $('#messageModalContent1').html(content);
        $('#messageModal1').modal('show');
    }
    //show成功的message
    function showMessage2(content) {
        $('#messageModalContent2').html(content);
        $('#messageModal2').modal('show');
    }

    function actionByConfirm(name, url, data, callBack, operation) {
        urlOfconfirmModal = url;
        dataOfconfirmModal = data;
        callBackOfconfirmModal = callBack;
        showConfirm(name, operation);
    }

    function actionByConfirm1(name, url, data, callBack, operation) {
        urlOfconfirmModal = url;
        dataOfconfirmModal = data;
        callBackOfconfirmModal = callBack;
        showConfirm1(name, operation);
    }

    function confirmationBox(name, url, data, callBack, operation,loading) {
        urlOfconfirmModal = url;
        dataOfconfirmModal = data;
        callBackOfconfirmModal = callBack;
        loadingModal = loading;
        showConfirmBox(name, operation);
    }

    function tipConfirmationBox(name, operation) {
        showTipConfirmBox(name, operation);
    }

    function initConfirmModal() {
        confirmRet = false;
        urlOfconfirmModal = null;
        dataOfconfirmModal = null;
        callBackOfconfirmModal = null;
        setConfirmTitle(defaultConfirmTitle);
        setConfirmContent(defaultConfirmContent);
    }
    function initConfirmModal1() {
        confirmRet = false;
        urlOfconfirmModal = null;
        dataOfconfirmModal = null;
        callBackOfconfirmModal = null;
        setConfirmTitle1(defaultConfirmTitle);
        setConfirmContent1(defaultConfirmContent);
    }
</script>
<script type="text/javascript">

    var hideTimeOut = 800;
    var defaultTipTitle = 	$("#tipModalTitle").html();
    var defaultTipContent = $(".tipModalContent").html();

    $('#tipModal').on('hide.bs.modal', function () {
        initTipModal();
    });

    /**
     * 为提示框设置标题
     */
    function setTipTitle(title) {
        $("#tipModalTitle").html(title);
    }

    /**
     * 设置默认的自动消失时间
     */
    function setTimeOut(time) {
        hideTimeOut = time;
    }

    /**
     * 设置提示框的提示语
     */
    function setTipContent(content) {
        $(".tipModalContent").html(content);
    }

    function showTip(title, content, time, noautoclose) {
        var tipModal = $('#tipModal');
        var tip = tipModal.is(':visible');
        if(tip){
            return ;
        }else{
            setTipTitle(title);
            setTipContent(content);
            tipModal.modal('show');
            if(!isNaN(time)){
                if(time>0){
                    setTimeout("$('#tipModal').modal('hide');",time);
                }
            }else{
                if(noautoclose){
                    return ;
                }else{
                    setTimeout("$('#tipModal').modal('hide');",hideTimeOut);
                }
            }
        }
    }

    function showSaveSuccess() {
        showTip("提示", "保存成功！");
    }

    function showSaveFail(cause) {
        showTip("提示", "保存失败！原因为" + cause);
    }

    function showDelFail(name, cause) {
        showTip("提示", "删除“" + name + "”失败！原因为" + cause);
    }

    function initTipModal() {
        setTipTitle(defaultTipTitle);
        setTipContent(defaultTipContent);
    }
</script>
<script type="text/javascript">

    var PromptmodalTitle = 	$("#PromptmodalTitle").html();
    var PrompModalContent = $("#PrompModalContent").html();
    var PrompModalContent01 = $("#PrompModalContent01").html();

    $('#Prompt').on('hide.bs.modal', function () {
        initPrompModal();
    });

    function initPrompModal() {
        $("#PromptmodalTitle").html(PromptmodalTitle);
        $("#PrompModalContent").html(PrompModalContent);
        $("#PrompModalContent01").html(PrompModalContent01);
    }

    function showPrompModal(title, content, content01,url,data,fuc) {
        $("#PromptmodalTitle").html("您确定要删除"+title+"吗？");
        $("#PrompModalContent").html(content);
        $("#PrompModalContent01").html(content01);
        $("#PromptBtn").html("我已了解清楚，确认删除");
        $('#Prompt').modal('show');
        window.url = url;
        window.data = data;
        window.title = title;
        window.fuc = fuc;
    }

    function showPrompModal2(title,name, content, content01,url,data,fuc) {
        $("#PromptmodalTitle").html(title);
        $("#PrompModalContent").html(content);
        $("#PrompModalContent01").html(content01);
        $("#PromptBtn").html("我已了解清楚，确认提交");
        $('#Prompt').modal('show');
        window.url = url;
        window.data = data;
        window.title = name;
        window.fuc = fuc;
    }
    $(function(){
        $("#PromptName").on("keyup",function(){
            if(window.title===$(this).val()){
                $("#PromptBtn").addClass("hover");
            }else{
                $("#PromptBtn").removeClass("hover");
            }
        });
        $("#PromptClose").on("click",function(){
            $("#PromptName").val("");
            $("#PromptBtn").removeClass("hover");
            try{
                myLoading.hide();
            }catch(e){}

        });
        $("#PromptBtn").click(function() {
            if(window.title===$("#PromptName").val()){
                $('#Prompt').modal('hide');
                //$.post(window.url, window.data,window.fuc);

                /*localPost(window.url, window.data, fuc, "json",{
                 "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
                 });*/
                $.ajax({
                    cache:false,
                    url:window.url,
                    type:'post',
                    data:window.data,
                    headers:{
                        OWASP_CSRFTOKEN:"${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    contentType: "application/json; charset=utf-8",
                    dataType : "json",
                    success:fuc
                });

                $("#PromptName").val("");
                $("#PromptBtn").removeClass("hover");
            }
        });
    });
</script>
<!-- END CONFIRM&TIP@IMPORTANT MODAL SCRIPT  -->
</body>
</html>
