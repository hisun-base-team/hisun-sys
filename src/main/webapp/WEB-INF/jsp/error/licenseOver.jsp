<%@ page language="java" pageEncoding="UTF-8"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>30云平台</title>
    <style>
        body{ background-color:#F8F8F8; font-size:14px;}
        .overduetext{ text-align:center; margin-top:30px; font-size:15px;}
        .overduetext em{ font-size:16px; font-style:normal; color:#D50003; font-weight:bold;}
        .overdueinput{ text-align:center; width:740px; margin:20px auto 20px auto;}
        .span01{ float:left; margin-top:22px;}
        .textarea{ width:625px; height:60px;}
        .overduelink{ text-align:center;}
        .overduelink a{ text-decoration:none;}
        .Send_out{ background-color:#1484D5; display:inline-block; color:#FFFFFF; padding:5px 10px; border-radius:4px; margin-right:20px;}
        .Send_out:hover{ background-color:#006dbb;}
        .Return{ color:#3B89D3;}
        .Return:hover{ color:#f60;}
    </style>
</head>

<!-- BEGIN BODY -->
<body class="page-header-fixed white" style="background-color:#F8F8F8;">
<%--<div class="regis_1000" >
    <div class="regis_logo" style="text-align: center;margin-top: 50px;"><img src="${path}/images/templateImage/logo_login.png"></div>
    <div style="text-align: center;font-size: 20px;margin-top: 50px;">您好！系统试用期已到，如想继续使用，请拨打<span style="color: #f30;padding: 0 5px;">020-38288430</span>进行购买。已经购买了激活码请在下方输入</div>
    <div class="Registered">
        <div class="back_pass">请输入激活码</div>
        <form class="form-horizontal"  method="POST" id="form1">
            <div class="control-group">
                <div class="controls">
                    <textarea rows="5" cols="100" name="encrypt" id="encrypt"></textarea>
                </div>
            </div>
        </form>

        <div class="Regis_but" style="margin-top:35px;">
            <a class="Regisbuttom" href="javascript:void(0);" onclick="activite()">激活</a>
        </div>

    </div>
</div>--%>
    <div style="text-align:center; margin-top:5%;"><img src="${path}/images/templateImage/logo_login.png"></div>
    <div class="overduetext">您好！系统试用期已到，如想继续使用，请拨打<em>020-38288430</em>进行购买。已经购买了激活码请在下方输入</div>
    <div class="overdueinput"><span class="span01">请输入激活码</span><textarea class="textarea" style="resize: none;" name="encrypt" id="encrypt"></textarea></div>
    <div class="overduelink"><a class="Send_out" href="javascript:void(0);" onclick="activite()">马上激活</a></div>
<script type="application/javascript" src="${path}/js/jquery-1.8.2.min.js"></script>
<script type="application/javascript">
    function activite(){
        var val = $("#encrypt").val();
        if($.trim(val) == ""){
            alert("激活码不能空");
            return;
        }
        $.ajax({
            url : "${path }/license/activate",
            type : "post",
            data : $("#form1").serialize(),
            dataType : "json",
            success : function(json){
                if(json.code == 1){
                    window.location.href = "${path}/login";
                }else{
                    alert(json.message);
                }
            },
            error : function(arg1, arg2, arg3){
                showTip("提示","出错了请联系管理员",2000);
            }
        });
    }
</script>
</body>
