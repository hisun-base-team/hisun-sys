<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link rel="shortcut icon" href="${path }/images/favicon.ico"/>
    <link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
    <!-- END PAGE LEVEL STYLES -->
    <title>首页</title>
    <style type="text/css">
        .page-content{ background-color:#eaedf1 !important;margin:0 !important; padding-right:0 !important;}
        body {background-color: #eaedf1 !important;}
        .tabbable-custom > .tab-content{ padding:10px 10px 10px 10px !important;}
    </style>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${path}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/style-metro.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="${path}/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${path}/css/glyphicons.css" rel="stylesheet"/>
    <link href="${path}/css/halflings.css" rel="stylesheet"/>
    <style>
        .dropdown-menu{ background-color:inherit; box-shadow: none; border:none;}
        .navigdrop_down{left:-116px;width: 200px;padding: 0;}
        .navigdrop_down02{left:-27px;padding: 0;width: 120px;}

        .nav_a{ border-radius: 8px; color: #ffffff; float: left; margin-top: 50px; margin-left: 20px; transition: background 0.4s ease-in-out 0s;  width: 200px; padding:15px 10px;}
        .navig_contact{width:1200px; margin-top:50px;}
        .dlnavigcont{ height:180px; width: 200px; text-align:center; margin-left:0;}
        .dlnavigcont dd {padding-top: 1px; width:200px;}
        .dlnavigcont dt {float: inherit;height: 72px;width: 200px;margin-left:0;}
        .dlnavigcont dt img{ max-height:72px;}
        .dlnavigcont dd h4 {font-size: 16px;padding: 5px 0 3px;text-align:center;}
        .dlnavigcont dd .navp01{ text-align: center;}
    </style>
</head>
<body >
<div class="navig">
    <div class="navig_header">
        <div class="naviglogo"><img  style="max-height: 44px;" src="${path}/images/${logo}"></div>
        <ul class="ulnavheaderright">
            <c:if test="${!empty userId && username ne 'admin'}">
                <li class="navli01 dropdown">
                    <a href="#" data-toggle="dropdown">
                        <i class="icon-bell-alt" style=""></i>
                        <span class="badge" id="message_badge">0</span>
                        <i class="icon-angle-down"></i>
                    </a>
                    <div class="dropdown-menu navigdrop_down">
                        <p class="mod_zt"></p>
                        <ul class="dropdown-navbell" id="messages">
                        </ul>
                    </div>
                </li>
            </c:if>

            <c:if test="${!empty tenant.id}">
                <li class="navli02 dropdown">
                    <a href="#" data-toggle="dropdown">
                        <img alt="" style=" height:29px; width:29px; border-radius:50%;"
                             src="${path}/sys/tenant/user/photo?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"/>
                        <span class="username">${userRealname}</span>
                        <i class="icon-angle-down"></i>
                    </a>
                    <div class="dropdown-menu navigdrop_down02">
                        <p class="mod_zt02"></p>
                        <ul class="dropdown-navbell02">
                            <li><a href="${path}/sys/tenant/user/profile?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><i class="icon-user"></i> 个人中心</a></li>
                            <li class="external"><a href="${path}/logout"><i class="icon-off"></i> 退出</a></li>
                        </ul>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="navig_contact">
        <a class="nav_a" href="/zzb/app/console/bset/?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
            <dl class="dlnavigcont">
                <dt><img src="${path}/images/templateImage/navIco01.png"></dt>
                <dd>
                    <h4>学位会表决</h4>
                    <p class="navp01">实现学委会表决会议资料无纸化、资料在线阅读、实施投票及结果统计。</p>
                </dd>
            </dl>
        </a>
        <a class="nav_a" href="zzb/app/console/asetA01/?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
            <dl class="dlnavigcont">
                <dt><img src="${path}/images/templateImage/navIco02.png"></dt>
                <dd>
                    <h4>职称评定会表决</h4>
                    <p class="navp01">实现职称评定会表决会议资料无纸化、资料在线阅读、实施投票及结果统计。</p>
                </dd>
            </dl>
        </a>
    </div>
</div>
</body>
<script src="${path}/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${path}/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${path}/js/app.js" type="text/javascript"></script>
<script>
    $(function () {
        getMessage();
        autoFixed();
        //当浏览器大小改变的时候,要重新计算
        $(window).resize(function () {
            autoFixed();
        });
        setInterval(getMessage, 30000);
    })
    function autoFixed() {
        //定义内容区域的高度 var mainHeight = $(window).height()-$(".navbar-inner").outerHeight()
        var mainHeight = $(window).height();
        var mainWidth = $(window).width();
        $('.navig').height(mainHeight);
        if(mainWidth>mainHeight){
            $('.navig').css("background","url(../images/templateImage/navig_bg.jpg) no-repeat center");
        }else{
            $('.navig').css("background","url(../images/templateImage/navig_bg1.jpg) no-repeat center");
        }

    }

    function getMessage() {
        var messages = $("#messages");
        var message_badge = $("#message_badge");

        //发送post请求
        localPost("${path}/sys/tenant/message/pending", null,
                function (data) {
                    messages.empty();
                    message_badge.text(data.count);
                    $("#message01").text(data.count);
                    if (!data.count) {
                        data.count = 0;
                    }
                    var start = "<li><p>您有" + data.count
                            + "条新消息</p></li>";
                    var conent = "";
                    var message = data.message;
                    for (var i in message) {
                        conent += "<li><a style=\"white-space: inherit;\" href='${path }/sys/tenant/message/messages?type="
                                + message[i].type
                                + "&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}'><i class=\"icon-warning-sign\"></i>"
                                + message[i].title + "</a></li>";
                    }

                    var end = "<li class=\"external\"><a href=\"${path }/sys/tenant/message/messages?type=all&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}\">查看所有消息<i class=\"m-icon-swapright\"></i></a></li> ";
                    messages.append(start + conent + end);
                }, "json", {"OWASP_CSRFTOKEN" : "${sessionScope.OWASP_CSRFTOKEN}"});
    }

    function openGzzzb(module){
        var url = ""
        if(module=="gbrm"){
            url ="http://localhost:8080/GZZZB/la/index.jsp?showFlag=init&moduleCode=LA_APPOINT_STUFF";
        }else if(module=="xtbg"){
            url ="http://localhost:8080/GZZZB/ed/edIndex.jsp?moduleCode=ED_APP";
        }else if(module=="gwygl"){
            url ="http://localhost:8080/GZZZB/oa/officialInfoManage/officialInfoIndex.jsp?reportTypeCode=1&moduleCode=OA_REPORT_ZWJS";
        }else if(module=="gbjd"){
            url ="http://localhost:8080/GZZZB/pi/maintenance/index.jsp?fromSys=PrLeaderInfo&moduleCode=PR_LEADER_INFO";
        }else if(module=="gbrc"){
            url ="http://localhost:8080/GZZZB/ed/edIndex.jsp?moduleCode=ED_APP";
        }else if(module=="zsk"){
            url ="http://localhost:8080/GZZZB/rm/resource/rmShouWenIndexMain.jsp?storeroomId=772227561E1E1E0B006BBA7A8AA163CF&storeroomCode=0004";
        }
        window.open(url);
    }
</script>
</body>
</html>
