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
	<title>密码重置成功</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="${path}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/css/style.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->

	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="${path}/css/login_style.css" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed white" style="background-color:#F8F8F8;">
	<div class="regis_1000" style="min-height:600px;">
		<div class="regis_logo"><img src="${path}/images/templateImage/logo_login.png"></div>
		<div class="regis_top">
			<h3 class="regis_top_h3">邮箱重置密码</h3>
		</div>

		<c:if test="${success==true}">
			<div class="Success_tips"><img src="${path}/images/templateImage/SuccessICO.png"><span>申请密码重置成功！</span></div>
			<div class="Success_sumin">尊敬的用户，请注意查询邮件进行密码重置，谢谢！</div>
			<div class="backPass_but"><a href="${path}/login">返回登录</a></div>
		</c:if>
		<c:if test="${success==false}">
			<div class="Success_tips"><img src="${path}/images/templateImage/ErrorICO.png"><span>申请密码重置失败 !</span></div>
			<div class="Success_sumin">邮件发送不成功，请稍后再试</div>
			<div class="backPass_but"><a href="${path}/login">返回登录</a></div>
		</c:if>


	</div>

	<div class="login_footer">2014-2015 版权所有©上海三零卫士信息安全有限公司 www.30ue.com </div>
</body>
<!-- END BODY -->
</html>