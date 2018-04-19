<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8" />
    <title>注册</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${path}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/style-metro.css" rel="stylesheet" type="text/css"/>
    <link href="${path}/css/style.css" rel="stylesheet" type="text/css"/>
    <link href='${path}/css/bootstrap-modal.css' rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${path}/css/login_style.css" rel="stylesheet" type="text/css"/>
    <!-- END PAGE LEVEL STYLES -->
    <style>
        #formbox{padding: 0; width: 1000px;}
        #formbox .item{ height: 50px;}

    </style>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed white">

<div class="regis_1000">
    <div class="regis_logo"><img src="${path}/images/templateImage/logo_login.png"></div>
    <div  id="oldR">
        <c:if test="${status == '0'}">
            <div class="Invitation_choice">您的邮箱【<c:out value="${email}"></c:out>】已经注册并激活为“<c:out value="${tenant.name}"></c:out>”的用户请直接<a href="${path}/login">登录</a>查看</div>
        </c:if>
        <c:if test="${status == '1'}">
            <form class="form-horizontal" action="#" id="form1">
                <input type="hidden" name="activationId" value="${activationId}">
                <input type="hidden" name="newTenantId" value="${newTenant.id}">
                <input type="hidden" name="userId" value="${user.id}">
                <input type="hidden" name="newTenantName" value="${newTenant.name}">
            </form>
            <div class="yaoqin_show">
                <div class="Invitation_choice">您的邮箱<span class="red">【<c:out value="${email}"></c:out>】</span>
                    <c:if test="${oldTenant == null}">
                        您当前不属于任何租户，您可以选择
                    </c:if>
                    <c:if test="${oldTenant != null}">
                        已经注册并激活为“<c:out value="${oldTenant.name}"></c:out>”的用户，您可以选择
                    </c:if>
                </div>
                <div class="choice_but">
                    <button class="btn red" type="button" onclick="goLogin()">
                        <c:if test="${oldTenant == null}">
                            拒绝加入
                        </c:if>
                        <c:if test="${oldTenant != null}">
                            留在“${oldTenant.name}”
                        </c:if></button>
                    <button class="btn blue" type="button" onclick="registerToNewTenant()">加入“${newTenant.name}”</button>
                </div>
                <div class="Warm_tips">
                    <span class="tips_tit">温馨提示：</span>
                    <div class="Warm_tips_show">1:留在原有租户,将放弃加入新租户的机会<br>
                                                2:加入新租户,将离开原有租户<br>
                                                3:注入新租户之后,将保留原有个人信息、登录账号及密码,为了您的登录,请尽快<a href="${path}/login">登录</a>修改</div>
                </div>
            </div>

        </c:if>
        <c:if test="${status == '2'}">
            <form class="form-horizontal" action="#" id="form1">
                <input type="hidden" name="activationId" value="${activationId}">
                <input type="hidden" name="email" value="${email}">
                <input type="hidden" name="type" value="0">
            </form>
            <div>
                <div class="Invitation_choice">您的邮箱<span class="red">【<c:out value="${email}"></c:out>】</span>已经注册为“<c:out value="${registerName}"></c:out>”的用户，但未激活,您可以选择</div>
                <div class="choice_but">
                    <button class="btn red" type="button" id="getActivate" onclick="getActivateLink('0')">获取激活链接</button>
                    <button class="btn blue" type="button" onclick="getActivateLink('1')">加入“${tenant.name}”</button>
                </div>
                <div class="Warm_tips">
                    <span class="tips_tit">温馨提示：</span>
                    <div class="Warm_tips_show">1:激活链接将发到您的邮箱,重新激活将放弃加入新租户的机会<br>
                        2:加入新租户,将不能再激活已经注册的账号<br>
                        3:加入新租户需要重新填写注册信息</div>
                </div>
            </div>
        </c:if>
    </div>
    <div class="Registered" style="display: none" id="newR">
        <form class="form-horizontal" action="#" id="form2">
            <input type="hidden" name="activationId" value="${activationId}">
            <input type="hidden" name="roleId" value="${roleId}">
            <input type="hidden" name="tenantId" value="${tenantId}">
            <div id="emailGroup" class="control-group">
                <div class="controls Va_tips">
                    <input class="m-wrap span3" type="text" name="email" id="email" required customEmail="true" tenantEmailUnique="true" readonly="readonly" value="<c:out value="${email}"></c:out>">
                </div>
            </div>
            <div id="usernameGroup" class="control-group">
                <div class="controls Va_tips">
                    <input class="m-wrap span3" type="text" name="username" id="username" required usernamePattern="true" tenantUsernameUnique="true" placeholder="用户名">
                </div>
            </div>
            <%--<div id="realnameGroup" class="control-group">
                <label class="control-label mylabel">真实姓名<span class="required">*</span>
                </label>
                <div class="controls inputtext">
                    <input type="text" class="m-wrap  span3" name="realname" id="realname" required minlenght="2" maxlength="20"/>
                </div>
            </div>--%>
            <div id="passwordGroup" class="control-group" style=" margin-bottom: 10px !important;">
                <div class="controls Va_tips">
                    <input type="password" id="password" class="m-wrap span3"  name="password" class="password" minlength="6" maxlength="16" pwdStrengh="true" placeholder="密码"/>
                </div>
                <%--<div class="controls_Cue">
                    <span class="clear"></span>
                    <label class="strengthA" id="pwdstrength" style="display:block;"><span class="fl">安全程度：</span><b></b></label>
                </div>--%>
            </div>

            <%--<div id="rePwdGroup" class="control-group">
                <label class="control-label mylabel">确认密码<span
                        class="required">*</span>
                </label>
                <div class="controls">
                    <input class="m-wrap span3" type="password" name="rePwd" id="rePwd" required equals="password">
                </div>
            </div>--%>
            <%--<div id="telGroup" class="control-group" >
                <label class="control-label mylabel">手机号码<span class="required">*</span>
                </label>
                <div class="controls inputtext">
                    <input type="text" class="m-wrap span3" name="tel" id="tel" required mobilePhone="true" />
                </div>
            </div>--%>

        </form>

        <div class="Regis_but">
            <a class="Regisbuttom" href="javascript:saveSubmit();">点击注册</a>
        </div>
    </div>
    <div class="login_footer">2014-2015 版权所有©上海三零卫士信息安全有限公司 www.30ue.com </div>
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
<script type="text/javascript">
    window.PATH = "${path}";
</script>
<script type="text/javascript" src="${path}/js/jquery-1.8.2.min.js"></script>
<script src='${path}/js/bootstrap-modal.js' type="text/javascript"></script>
<script src='${path}/js/bootstrap-modalmanager.js' type="text/javascript"></script>
<script type="text/javascript" src="${path}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script src="${path }/js/validate.js" type="text/javascript" ></script>
<script type="text/javascript">
    var myLoading = new MyLoading("${path}",{zindex : 11111});
    function goLogin() {
        window.location.href = "${path}" + "/login";
    }
    function registerToNewTenant() {
        myLoading.show();
        $.ajax({
            url: "${path }/sys/tenant/user/registerToNewTenant",
            type: "post",
            data: $('#form1').serialize(),
            dataType: "json",
            success: function (json) {
                myLoading.hide();
                showTip(json.message, 2000);
                if (json.code == 0) {
                    setTimeout(function () {
                        window.location.href = "${path}/login"
                    }, 1000);
                }
            },
            error: function (arg1, arg2, arg3) {
                myLoading.hide();
                showTip("提示", "出错了请联系管理员", 2000);
            }
        });
    }
    function showTip(message,timeout){
        $("#messageModalContent1").html(message);
        $('#messageModal1').modal("show");
        if(timeout){
            setTimeout(function(){
                $('#messageModal1').modal("hide");
            },timeout);
        }
    }
    function getActivateLink(type) {
        $("[name='type']").val(type);
        $("#getActivate").attr("disabled", "disabled");
        $.ajax({
            url: "${path }/sys/tenant/user/getActivateLink",
            type: "post",
            data: $('#form1').serialize(),
            dataType: "json",
            success: function (json) {
                if (json.code == "1"){
                    showTip(json.message, 2000);
                } else if(json.code == "2"){
                    $("#oldR").css("display", "none");
                    $("#newR").css("display", "");
                } else if (json.code == "-1"){
                    showTip(json.message, 2000);
                    $("#getActivate").removeAttr("disabled");
                }
            },
            error: function (arg1, arg2, arg3) {
                myLoading.hide();
                showTip("提示", "出错了请联系管理员", 2000);
            }
        });
    }

    var myVld = new EstValidate2("form2");
    function saveSubmit(){
        var bool = myVld.form();
        if(bool){
            myLoading.show();
            $.ajax({
                url: "${path }/sys/tenant/user/activate",
                type: "post",
                data: $('#form2').serialize(),
                dataType: "json",
                success: function (json) {
                    myLoading.hide();
                    if (json.code == 1) {
                        showTip("注册成功", 2000);
                        setTimeout(function () {
                            window.location.href = "${path}/login"
                        }, 2000);
                    } else {
                        showTip(json.message, 2000);
                    }
                },
                error: function (arg1, arg2, arg3) {
                    myLoading.hide();
                    showTip("提示", "出错了请联系管理员", 2000);
                }
            });
        }
    }
</script>
</body>
</html>