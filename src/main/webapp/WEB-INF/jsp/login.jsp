<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="common/top.jsp"%>
<link href="${path }/css/login_style.css" rel="stylesheet" type="text/css"/>
<title>干部信息管理大数据应用平台</title>
</head>

<!-- BEGIN BODY -->

<body class="page-header-fixed white">
    <div class="login">
    		<div class="login_header">
            	 <div class="login_header_1000">
					 <div class="login_logo"><img style="max-height: 46px;" src="${path }/images/templateImage/${loginLogo}"></div>
					 <div class="Customer_name">${loginTitle}</div>
                 </div>
            </div>
            <div class="login_main">
            	<form class="form-vertical login-form" action="signin" method="post" id="signin" name="myform" >
                  <div class="login_mod">
                       <div class="login_mod_show">
                  	   
	                  	   <h1>用户登录</h1>
	                  	   <c:if test="${param.error eq 1}">
								<div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;"><button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>用户名或密码错误!</div>
							</c:if>
							<c:if test="${param.error eq 2}">
								<div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;"><button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>验证码错误!</div>
							</c:if>
						   <c:if test="${param.error eq 3}">
							   <div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;"><button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>账号已被锁定,15分钟自动解锁!</div>
						   </c:if>
						   <c:if test="${param.error eq 4}">
							   <div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;"><button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>你已被移出原有组织!</div>
						   </c:if>
						   <c:if test="${param.error eq 5}">
							   <div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;"><button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>你的账号已被冻结!</div>
						   </c:if>
						   <c:if test="${param.error eq 6}">
							   <div class="alert alert-error hide" style="display: block;width: 241px;margin: 15px auto 0 auto;color: #f00;"><button class="close" data-dismiss="alert" style="top: -6px;right: -33px;"></button>未激活账号!</div>
						   </c:if>
	                            <div class="control-group">
								<label class="control-label visible-ie8 visible-ie9">用户名</label>
				
								<div class="controls login_controls">
				
									<div class="input-icon left login_mod_inp">
				
										<i class="icon-user" style="font-size: 20px;color: #CCCCCC;padding: 3px 5px 0 5px; cursor: default !important;"></i>
				
										<input class="m-wrap placeholder-no-fix" type="text" style="padding-left:44px !important;" placeholder="用户名" name="username" id="username" value='<c:out value="${param.username }"></c:out>'/>
				
									</div>
				
								</div>
				
							</div>
			
							<div class="control-group">
				
								<label class="control-label visible-ie8 visible-ie9">密码</label>
				
								<div class="controls login_controls">
				
									<div class="input-icon left login_mod_inp">
				
										<i class="icon-lock" style="font-size: 20px;color: #CCCCCC;padding: 3px 5px 0 5px; cursor: default !important;"></i>
				
										<input class="m-wrap placeholder-no-fix" style="padding-left:44px !important;" type="password" placeholder="密码" name="password"/>
				
									</div>
				
								</div>
				
							</div>

							<c:if test="${captchaActivated }">
							<div class="control-group">
				
								<label class="control-label visible-ie8 visible-ie9">验证码</label>
				
								<div class="controls login_controls" style="position:relative;">
				
									<div class="input-icon left login_mod_inp02" style="width: 183px;">
				
										<i class="icon-lock" style="font-size: 20px;color: #CCCCCC;padding: 3px 5px 0 5px; cursor: default !important;"></i>
				
										<input class="m-wrap placeholder-no-fix" type="text" placeholder="验证码" style="padding-left:44px !important;" name="kaptcha" id="kaptcha" style="width: 150px;"/>
										
				      					
									</div>
									
									<div style="position: absolute;top: 0;right: 0;">
										<img src="kaptcha/image" id="kaptchaImage" />  
										<br/>
										<a href="#" onclick="refreshCaptcha()" style="margin-left: 10px;">看不清?换一张</a>  
									</div>
								</div>
				
							</div>
							</c:if>
						   <c:choose>
						   		<c:when test="${isdemo}">
									   <div class="login_but">
										   <!-- <a class="loginbuttom" href="###">立即登录</a> -->
										   <button type="submit" class="loginbuttom" style="border: none; font-family:'微软雅黑'; ">
											   立即登录
										   </button>
									   </div>
									<div class="BackPassword" style="color: red;">
										用户名为:test1 密码为:123test
										</div>
							   </c:when>
							   <c:otherwise>
								   <div class="login_but">
									   <!-- <a class="loginbuttom" href="###">立即登录</a> -->
									   <button type="submit" class="loginbuttom" style="border: none; font-family:'微软雅黑'; ">
										   立即登录
									   </button>
								   </div>

								   <%--<c:if test="${isSaas==true}"><div class="BackPassword"><a href="${path }/register">新租户注册</a> |</c:if> <a href="${path }/sys/tenant/user/forgot">忘记密码 ? 点击找回密码&gt;&gt;</a></div>--%>

							   </c:otherwise>
						   </c:choose>
					   </div>
                  </div>
                  
                </form>
            </div>
            <div class="login_footer">2014-2018 版权所有©上海三零卫士信息安全有限公司</div>
    </div>
	

	<%@include file="common/bottom.jsp"%>
	<script type="text/javascript" src="${path }/js/jquery.form.js"></script>
	<script src="${path }/js/login.js" type="text/javascript"></script>      
	<script>
		
		var refreshCaptcha = function(){
			document.getElementById("kaptchaImage").src="kaptcha/image?t=" + Math.random();  
		};
	
		$(function() {     
			$("#mybutton").on("click",function(){
				var remember = $("#remember");
				remember.val(remember.attr("checked")==="checked");
				 document.myform.submit(); 
			}); 
			$("#kaptchaImage").on("click",function(){
				document.getElementById("kaptchaImage").src="kaptcha/image?t=" + Math.random(); 
			});
		 	Login.init();
		 	$("#logo").css({
			  "margin-top" : ($(window).height() - $("#logo").outerHeight()- $("#content").outerHeight())/5
		 	});
		 	/* $("#kaptcha").on("blur",function(){
		 		alert($("#orgSelect").find("option:selected").text());
		 	}); */
		 	/*$("#username").on("blur",function(){
		 		var username=$(this).val();
		 		$.post("${path }/load/assignment/"+username,function(data){
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
