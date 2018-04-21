<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<!-- BEGIN HEAD -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/WEB-INF/jsp/inc/styles.jsp" %>
    <link href="${path}/css/login_style.css" rel="stylesheet" type="text/css"/>
    <title>${sysAdminName}</title>
</head>
<!-- BEGIN BODY -->
<body class="page-header-fixed white">
<div class="login">
    <div class="login_header">
        <div class="login_header_1000">
            <div class="login_logo"><img style="max-height: 46px;" src="${path }/images/templateImage/${sysLoginLogo}">
            </div>
            <div class="Customer_name">${sysAdminName}</div>
        </div>
    </div>
    <div class="login_main">
        <form class="form-vertical login-form" action="${path}/admin/signin" method="post" id="myForm" name="myForm">
            <div class="login_mod">
                <div class="login_mod_show">
                    <h1>用户登录</h1>
                    <c:if test="${param.error eq 1}">
                        <div class="alert alert-error hide"
                             style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;">
                            <button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>
                            用户名或密码错误!
                        </div>
                    </c:if>
                    <c:if test="${param.error eq 2}">
                        <div class="alert alert-error hide"
                             style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;">
                            <button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>
                            验证码错误!
                        </div>
                    </c:if>
                    <c:if test="${param.error eq 3}">
                        <div class="alert alert-error hide"
                             style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;">
                            <button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>
                            账号已被锁定,15分钟自动解锁!
                        </div>
                    </c:if>
                    <div class="control-group">
                        <label class="control-label visible-ie8 visible-ie9">用户名</label>
                        <div class="controls login_controls" id="usernameGroup">
                            <div class="input-icon left login_mod_inp">
                                <i class="icon-user"
                                   style="font-size: 20px;color: #CCCCCC;padding: 3px 5px 0 5px; cursor: default !important;"></i>
                                <input class="m-wrap placeholder-no-fix" type="text"
                                       style="padding-left:44px !important;" placeholder="用户名" name="username"
                                       id="username" value='<c:out value="${param.username }"></c:out>' required/>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label visible-ie8 visible-ie9">密码</label>
                        <div class="controls login_controls" id="passwordGroup">
                            <div class="input-icon left login_mod_inp">
                                <i class="icon-lock"
                                   style="font-size: 20px;color: #CCCCCC;padding: 3px 5px 0 5px; cursor: default !important;"></i>
                                <input class="m-wrap placeholder-no-fix" style="padding-left:44px !important;"
                                       type="password" placeholder="密码" name="password" id="password" required/>
                            </div>

                        </div>

                    </div>

                    <div class="control-group" id="codeGroup">

                        <label class="control-label visible-ie8 visible-ie9">验证码</label>

                        <div class="controls login_controls" style="position:relative;">
                            <div class="input-icon left login_mod_inp02" style="width: 183px;">
                                <i class="icon-lock"
                                   style="font-size: 20px;color: #CCCCCC;padding: 3px 5px 0 5px; cursor: default !important;"></i>
                                <input class="m-wrap placeholder-no-fix" type="text" placeholder="验证码"
                                       style="padding-left:44px !important;" name="code" id="code"
                                       style="width: 150px;"/>

                            </div>
                            <div style="position: absolute;top: 0;right: 0;">
                                <img src="kaptcha/image" id="kaptchaImage"/>
                                <br/>
                                <a href="#" onclick="refreshCaptcha()" style="margin-left: 10px;">看不清?换一张</a>
                            </div>
                        </div>
                    </div>
                    <div class="login_but">
                        <!-- <a class="loginbuttom" href="###">立即登录</a> -->
                        <button type="submit" class="loginbuttom" style="border: none; font-family:'微软雅黑'; ">
                            立即登录
                        </button>
                    </div>
                    <%--<div class="BackPassword"><a href="${path }/admin/forgot">忘记密码 ? 点击找回密码&gt;&gt;</a></div>--%>
                </div>
            </div>

        </form>
    </div>
    <div class="login_footer">${sysCopyright}</div>
</div>


<%@include file="/WEB-INF/jsp/inc/script-plugins.jsp"%>
<script src="${path }/js/login.js" type="text/javascript"></script>
<script>
    var refreshCaptcha = function () {
        document.getElementById("kaptchaImage").src = "kaptcha/image?t=" + Math.random();
    };

    $(function () {
        $("#mybutton").on("click", function () {
            var remember = $("#remember");
            remember.val(remember.attr("checked") === "checked");
            document.myform.submit();
        });
        $("#kaptchaImage").on("click", function () {
            document.getElementById("kaptchaImage").src = "kaptcha/image?t=" + Math.random();
        });
        Login.init();
        var validCode = true;
        var bool = true;
        $(".msgs").click(function () {
            var time = 30;
            var code = $(this);
            if (validCode) {
                if ($("#username").val() === "") {
                    $("#usernameGroup").append("<label for=\"username\" class=\"help-inline help-small no-left-padding\">用户名不能为空.</label>");
                    bool = false;
                }
                if ($("#password").val() === "") {
                    $("#passwordGroup").append("<label for=\"password\" class=\"help-inline help-small no-left-padding\">密码不能为空.</label>");
                    bool = false;
                }
                if (bool) {
                    $.ajax({
                        url: "${path }/admin/sms/send/code",
                        type: "post",
                        data: {"username": $("#username").val()},
                        success: function (data) {
                            if (data.success) {
                                validCode = false;
                                code.addClass("msgs1");
                                var t = setInterval(function () {
                                    time--;
                                    code.html(time + "秒重新获取");
                                    if (time == 0) {
                                        clearInterval(t);
                                        code.html("获取短信验证码");
                                        validCode = true;
                                        code.removeClass("msgs1");

                                    }
                                }, 1000);
                            }
                        }
                    });

                }
            }
        });
        $("#logo").css({
            "margin-top": ($(window).height() - $("#logo").outerHeight() - $("#content").outerHeight()) / 5
        });
        /* $("#kaptcha").on("blur",function(){
         alert($("#orgSelect").find("option:selected").text());
         }); */
        /*$("#username").on("blur",function(){
         var username=$(this).val();
         $.post("
        ${path }/load/assignment/"+username,function(data){
         if(data.success){
         if(!data.orgs||data.orgs.length===0||data.orgs.length===1){
         $("#org").empty();
         }else{

         var html = "<select class='m-wrap placeholder-no-fix' id='orgSelect' name='orgSelect' placeholder='选择组织' tabindex='1' style='width: 350px; height: 42px !important; line-height: 48px; border: solid 1px #CCCCCC; background-color: #FFFFFF; border-radius: 3px; margin-top: 10px;'>";
         for(var i in data.orgs){
         if(i==='0'){
         html+="<option selected='selected' value="+data.orgs[i].id+">"+data.orgs[i].tenantName+"</option>";
         }else{
         html+="<option value="+data.orgs[i].id+">"+data.orgs[i].tenantName+"</option>";
         }
         }
         html+="</select>";
         $("#org").empty().append(html);
         // jQuery.uniform.update($("#orgSelect"));
         }
         }
         });
         });*/
    });

</script>
</body>
</html>