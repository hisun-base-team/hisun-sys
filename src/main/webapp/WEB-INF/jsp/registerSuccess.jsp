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
	<title>申请租户提示</title>
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
		.regis_top{ height:24px; overflow:hidden;}
		.Success_tips{ margin:0 auto; text-align:center; margin-top:100px;}
		.Success_tips span{ font-size:20px; padding-left:30px;}
		.Success_sumin{ text-align:center; margin-top:20px; color:#666666;}
	</style>
</head>

<body class="page-header-fixed white" style="background-color:#F8F8F8;">
	<div class="regis_1000" style="min-height:600px;">
		<div class="regis_logo"><img src="${path}/images/templateImage/logo_login.png"></div>

			<div class="Success_tips"><img src="${path}/images/templateImage/SuccessICO.png"><span>注册成功！</span></div>
			<div class="Success_sumin">验证邮件已经发送至您的邮箱中,<a href="javascript:void(0)" onclick="openEmail()">登录邮箱激活</a></div>
			<div class="backPass_but"><a href="${path}/login">返回登录</a></div>
		</div>

	<div class="login_footer">2014-2015 版权所有©上海三零卫士信息安全有限公司 www.30ue.com </div>
   <script>
	   function openEmail(){
		   var a = "${email}".split("@");
		   if (a.length > 0) {
			   var b = "http://mail." + a[1];
			   window.open(b)
		   }
	   }
   </script>
</body>

</html>