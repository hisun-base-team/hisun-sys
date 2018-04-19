<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>激活</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/MD5.js"></script>
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<link href="css/bootstrap.css" rel="stylesheet">
	
<script type="text/javascript">
$(document).ready(function(){
	$("#msgLabel").text("");
	$("#inputPassword").tooltip({trigger:'focus'});
	var email='<%=request.getParameter("email")%>';
	var activationcode='<%=request.getParameter("activationcode")%>';
	
	$("#email").val(email);
	$("#activationcode").val(activationcode);
	
	    $("#inputPassword").change(function(){
	    	$("#inputPwdMD5").val(hex_md5($("#inputPassword").val()));
	  			});
	    
	    
	    $("#forgotPwdForm").submit(function(e){
	    	
	    	
			if($("#inputPassword").val()==""||$("#confirmPassword").val()=="")
				{
	    		if($("#inputPassword").val()=="")
	    			{
	    			$("#inputPassword").parent().addClass("has-error");
	    			$("#msgLabel").text("请输入完整！");
	    			}
	    		else
    			{
	    			$("#inputPassword").parent().removeClass("has-error");
	    			$("#msgLabel").text("");
	    			}
	    		if($("#confirmPassword").val()=="")
	    			{
	    			$("#confirmPassword").parent().addClass("has-error");
	    			$("#msgLabel").text("请输入完整！");
	    			}
	    		else
    			{
	    			$("#confirmPassword").parent().removeClass("has-error");
	    			$("#msgLabel").text("");
	    			}
    	    	e.preventDefault();
	    }
			else
				{
				if(!($("#inputPassword").val().length>=6&&$("#inputPassword").val().length<=20))
				{
		    		$("#msgLabel").text("请按照要求设置密码！");
		    		$("#inputPassword").parent().addClass("has-error");
		 	    	e.preventDefault();
				}
				else	
				{	
		    	if($("#inputPassword").val()!=$("#confirmPassword").val())
	    		{
	    	$("#msgLabel").text("请输入相同的密码！");
	    	$("#confirmPassword").val("");
	    	$("#confirmPassword").parent().addClass("has-error");
	    	e.preventDefault();
	    		}
	    	else
	    		{
	    		$("#msgLabel").text("");
	    		$("#confirmPassword").parent().removeClass("has-error");
	    		}
				}
		    	
				}
	    		

	    });
	    
	    $("#inputPassword").blur(function(){
	    	if(!($(this).val().length>=6&&$(this).val().length<=20))
	    		{
	    		$("#msgLabel").text("请按照要求设置密码！");
	    		$(this).parent().addClass("has-error");
	    		}
	    	else
	    		{
	    		$("#msgLabel").text("");
	    		$(this).parent().removeClass("has-error");
	    		$("#confirmPassword").parent().removeClass("has-error");
	    		}
	    });
	    
/*		$.ajax({
			cache : true,
			type:"POST",
			url : "activation/Verify",
		//	contentType : 'application/json; charset=utf-8',
			 data : {
			      'email' : email,
			      'activationcode' : activationcode
			     },  
			error: function (request, message, ex) {
			    alert(request.responseText);
			},
			success : function(data) {
				
					if(data.root=="ActivatedCodeRightTenantAdminActivated")
						{
					$("#activationspan").text("验证成功，将为您自动跳转至登录页面");	
				//	location.reload();
					 window.location.href="/30Cloud/login";
					 }
					else
						$("#activationspan").text("验证失败！");
					
			}
			});*/
});
</script>
  </head>
  <body>
<div id="container">
  <div id="header">
    <div id="blank1"></div>
    <div id="LOGOdiv">
      <table id="LOGOtable">
        <tr>
          <td width="126" align="center">LOGO!</td>
          <td width="62" align="center">激活</td>
        </tr>
      </table>
    </div>
    <div id="blank2"></div>
    <div id="operation">
      <table id="operationtable">
        <tr>
          <td align="center" width="50%"></td>
          <td align="center"><a class="operationLink" href='#'>联系客服</a></td>
        </tr>
      </table>
    </div>
  </div>
  <div id="content">
<table align="center" width="300px">
<tr height="60px"><td></td></tr>
<tr><td><div id="inputPwd">
  <form  method="post" action="forgotPassword/activationResetPassword" id="forgotPwdForm">
<table width="100%">
<tr height="60px"><td style="font-size:20px">激活账户</td></tr>
<tr height="50px"><td>请设置您的登录密码</td></tr>
<tr><td>
      
          <input type="password" class="form-control" id="inputPassword" data-placement="right" placeholder="登录密码" title="请输入6-20位的字母、数字、特殊符号作为密码">
       
</td></tr>
<tr><td><div class="blank"></td></tr>
<tr><td>
      
          <input type="password" class="form-control" id="confirmPassword" placeholder="确认密码">
        
</td></tr>

<tr><td><div class="blank has-error">
 <label id="msgLabel" class="control-label"></label>
</div></td></tr>
<tr><td><input type="submit" class="btn btn-primary"  value="激活账户"/></td></tr>
</table>
          <input type="hidden" id="inputPwdMD5" name="password">
          <input type="hidden" id="email" name="email">
          <input type="hidden" id="activationcode" name="activationcode">
</form>
</div></td></tr>
</table>
</div>
    <div id="footer">
    <span id="footerSpan">Copyright 2015-2020 30cloud.com 版权所有</span>
    </div>
</div>
</body>
</html>
<style type="text/css">
#container {
	width: 100%;
}
#header {
	height: 60px;
	width: 94%;
	margin-right: auto;
	margin-left: auto;
	box-shadow: 0px 1px 0px #E6E6E6;
}
#content {
	height: 450px;
	width: 88%;
	margin-left: auto;
	margin-right: auto;
}
#footer{
	height: 90px;
	width: 94%;
	box-shadow: 0px -1px 0px #E6E6E6;
	margin-right: auto;
	margin-left: auto;
	text-align:center;
}
#footerSpan{
	font-size: 12px;
	color: #B0B0B0;
	line-height: 60px;
	height: 20px;
	width: 100%;
}
#LOGOtable {
	width: 100%;
	height: 100%;
}
#operation {
	float: right;
	height: 100%;
	width: 200px;
}
#LOGOdiv {
	float: left;
	height: 100%;
	width: 200px;
}
.operationLink {
	display: block;
	line-height: 56px;
}
.operationLink:hover {
	box-shadow: 0px 1px 0px #3090e4;
	color: #333;
}
#operationtable {
	height: 100%;
	width: 100%;
	font-size: 14px;
}
.blank{
	height:20px;
}
#blank1 {
	float: left;
	width: 7%;
	height: 100%;
}
#blank2 {
	float: right;
	width: 6%;
	height: 100%;
}
#blank3 {
	width: 100%;
	height: 40px;
}
#msgLabel{
	font-size:10px !important;
}
a:link {
	text-decoration: none;
	color: #666;
	outline: 0 none;
}
a:visited {
	text-decoration: none;
	color: #666;
}
a:hover {
	text-decoration: none;
	color: #666;
}
a:active {
	text-decoration: none;
	color: #666;
}
</style>