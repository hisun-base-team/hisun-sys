<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/common/top.jsp"%>
<link href="${path }/css/login.css" rel="stylesheet" type="text/css"/>
<link href="${path }/css/login_style.css" rel="stylesheet" type="text/css"/>
<title>30云平台登录</title>
</head>

<!-- BEGIN BODY -->

<body class="page-header-fixed white" style="background-color:#F8F8F8;">
	<div class="regis_1000" >
		<div class="regis_logo"><img src="${path}/images/templateImage/logo_login.png"></div>
		<div class="regis_top">
			<h3 class="regis_top_h3">邮箱找回密码</h3>
		</div>
		<div class="Registered">
			<div class="back_pass">请填写您需要重置密码的帐号邮箱</div>
			<form class="form-horizontal" action="${path }/sys/tenant/user/forgot" method="POST" id="form1">
				<div class="control-group">
					<div class="controls">
						<input type="text" class="span3 m-wrap" placeholder="请输入您的邮箱"  name="email" id="email">
					</div>
				</div>
			</form>

			<div class="Regis_but" style="margin-top:35px;">
				<a class="Regisbuttom" href="javascript:void()" onclick="mySubmit()">发送重置密码的邮件</a>
			</div>

			<div class="backPass_but"><a href="javascript:void()" onclick="loginBack()">返回登录</a></div>
		</div>
	</div>
	<script type="text/javascript" src="${path }/js/jquery-1.10.1.min.js"></script>
	<script src="${path }/js/bootstrap-modal.js" type="text/javascript"></script>
	<script src="${path }/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script type="text/javascript" src="${path }/js/jquery.form.js"></script>
	<script type="text/javascript" src="${path }/js/common/loading.js"></script>
	<%@ include file="/WEB-INF/jsp/inc/confirmModal.jsp"%>
	<script>
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var myLoading = new MyLoading("${path}",{zindex : 11111});
		function mySubmit(){
			if(!reg.test($("email").val())){

			}
			myLoading.show();
			$("#form1").submit();
		}

		function loginBack(){
			window.location.href = "${path}/login";
		}

	</script>

</body>
