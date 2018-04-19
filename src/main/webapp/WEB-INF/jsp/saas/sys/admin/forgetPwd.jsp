<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>找回密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
	<link href="css/bootstrap.css" rel="stylesheet">
	  <script type="text/javascript">
    $(document).ready(function(){
    	var email='<%=request.getParameter("email")%>';

    	if(email!="null")
    		{
    		$("#inputEmail").val(email);
    		$("#emailtd").text($("#inputEmail").val());
    		}

    	$("#inputEmail").change(function(){
    		
    		$("#emailtd").text($(this).val());
    		
    	});
    	
    	$("#submitReq").click(function(){
    		
    		if($("#inputEmail").val()!=""&&$("#inputCheckCode").val()!="")
    			$.ajax({
    				cache : true,
    				type:"POST",
    				url : "forgotPassword/FPWSendLink",
    			//	contentType : 'application/json; charset=utf-8',
    				 data : {
    				      'email' : $("#inputEmail").val(),
    				      'checkcode' : $("#inputCheckCode").val()
    				     },  
    				error: function (request, message, ex) {
    				    alert(request.responseText);
    				},
    				success : function(data) {
    					
							$("#table1").hide();
							$("#table2").show();
    						
    				}
    				});
    		else
    		alert("请补充完整！");
    	});
    
	
	    	

			

        

    });
    function changeImg(){
        document.getElementById("imgcode").src ="checkCode?"+Math.random();    
        }
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
          <td width="62" align="center">密码找回</td>
        </tr>
      </table>
    </div>
    <div id="blank2"></div>
    <div id="operation">
      <table id="operationtable">
        <tr>
          <td align="center" width="50%"><a class="operationLink" href='${path}/admin/login'>登录</a></td>
          <td align="center"><a class="operationLink" href='#'>联系客服</a></td>
        </tr>
      </table>
    </div>
  </div>
  <div id="content">
    <table align="center" width="340px">
      <tr height="60px">
        <td></td>
      </tr>
      <tr>
        <td><div id="inputPwd">
            <table width="100%" id="table1">
              <tr height="60px">
                <td style="font-size:20px" colspan="3">忘记密码？</td>
              </tr>
              <tr height="60px">
                <td colspan="3">请填写您的登录用email，我们会向您的邮箱发送一个链接，请根据链接提示重置您的密码。</td>
              </tr>
              <tr>
                <td colspan="3"><div class="input-group col-md-12">
                    <input type="text" class="form-control" id="inputEmail" placeholder="Email">
                  </div></td>
              </tr>
              <tr>
                <td colspan="3"><div class="blank"></td>
              </tr>
              <tr>
                <td width="50%"><div class="input-group col-md-12">
                    <input type="text" class="form-control" id="inputCheckCode" placeholder="验证码">
                  </div></td>
                <td width="35%" align="left"><div id="imagediv"> <a href='javascript:changeImg()'><img src='checkCode' title='看不清?点击刷新' id='imgcode'/></a> </div></td>
                <td width="15%" align="center"><a href='javascript:changeImg()'><img src='images/repeat.png' style="width:15px;height:15px;" title='点击刷新'/></a></td>
              </tr>
              <tr>
                <td><div class="blank"></td>
              </tr>
              <tr>
                <td><input type="button" class="btn btn-primary"  id="submitReq" value="重置密码"/></td>
              </tr>
            </table>
            
               <table width="100%" id="table2" style="display:none">
                  <tr height="60px"><td style="font-size:20px" colspan="3">重置密码</td></tr>
                  <tr height="40px"><td>重置密码的链接已经发送至你的邮箱</td></tr>
                  <tr height="40px"><td id="emailtd"></td></tr>
                  <tr height="40px"><td>请跟随邮件里的引导来重设你的登录密码！</td></tr>
            	</table>
          </div></td>
      </tr>
    </table>
  </div>
  <div id="footer"> <span id="footerSpan">Copyright 2015-2020 30cloud.com 版权所有</span> </div>
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
	height: 500px;
	width: 88%;
	margin-left: auto;
	margin-right: auto;
}
#footer {
	height: 90px;
	width: 94%;
	box-shadow: 0px -1px 0px #E6E6E6;
	margin-right: auto;
	margin-left: auto;
	text-align: center;
}
#footerSpan {
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
.blank {
	height: 20px;
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
#imagediv {
	float: right;
	width: 80%;
	text-align: center;
}
</style>