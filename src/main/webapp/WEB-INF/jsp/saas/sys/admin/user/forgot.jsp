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
	<title>找回密码</title>
	<style type="text/css">
		.Registered label{
			text-align: left !important;
		}
		.help-inline {
			color: #bf0a0a;
		}
	</style>
</head>

<!-- BEGIN BODY -->

<body class="page-header-fixed white" style="background-color:#F8F8F8;">
<div class="regis_1000"  style=" width:334px;" >
	<div class="regis_logo"><img src="${path}/images/templateImage/logo_login.png"></div>
	<div class="regis_top">
		<h3 class="regis_top_h3">邮箱找回密码</h3>
	</div>
	<div class="Registered" style="text-align:left;">
		<c:if test="${ !empty success &&!success}">
			<div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;">${message}</div>
		</c:if>
		<div class="back_pass">请填写您需要重置密码的帐号邮箱</div>
		<form class="form-horizontal" action="${path }/sys/admin/user/forgot" method="POST" id="form1">
			<div class="control-group" id="emailGroup">
				<div class="controls ">
					<input type="text" class="span3 m-wrap" placeholder="请输入您的邮箱"  name="email" id="email">
				</div>
			</div>
			<div class="control-group" style=" overflow:hidden;">
				<input type="text" class="span3 m-wrap fl" placeholder="验证码"  style=" width:200px;" id="code" name="code">
				<span class="msgs" style=" height:40px; line-height:40px;margin: 0 0 0 8px;">获取短信验证码</span>

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

		if($("#email").val()===""){
			$("#emailId").remove();
			$("#emailGroup").append("<label id='emailId' for=\"email\" class=\"help-inline help-small no-left-padding\">邮箱不能为空.</label>");
		}else if(!reg.test($("#email").val())){
			$("#emailId").remove();
			$("#emailGroup").append("<label id='emailId' for=\"email\" class=\"help-inline help-small no-left-padding\">邮箱格式不对.</label>");
		}else{
			myLoading.show();
			$("#form1").submit();
		}

	}

	$(function(){
		var validCode=true;
		var bool = true;
		$(".msgs").click (function  () {
			var time=30;
			var code=$(this);
			if (validCode) {
				if($("#email").val()===""){
					$("#emailId").remove();
					$("#emailGroup").append("<label id='emailId' for=\"email\" class=\"help-inline help-small no-left-padding\">邮箱不能为空.</label>");
					bool = false;
				}
				if(bool){
					$.ajax({
						url : "${path }/admin/sms/send/code/email",
						type : "post",
						data : {"email":$("#email").val()},
						success : function(data){
							if (data.success==="0") {
								$("#emailId").remove();
								validCode=false;
								code.addClass("msgs1");
								var t=setInterval(function  () {
									time--;
									code.html(time+"秒重新获取");
									if (time==0) {
										clearInterval(t);
										code.html("获取短信验证码");
										validCode=true;
										code.removeClass("msgs1");

									}
								},1000);
							}else if(data.success==="1"){
								$("#emailId").remove();
								$("#emailGroup").append("<label id='emailId' for=\"email\" class=\"help-inline help-small no-left-padding\">该邮箱不存在!</label>");
							}else{
								showTip("提示","短信验证码发送失败,请稍候再试!",3000);
							}
						}
					});

				}
			}
		});
	});

	function loginBack(){
		window.location.href = "${path}/admin/login";
	}

</script>

</body>
