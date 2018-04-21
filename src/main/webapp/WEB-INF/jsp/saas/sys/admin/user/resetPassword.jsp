<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>密码重置</title>
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
	<div class="regis_top">
		<span>已有账号？<a href="${path}/admin/login">点击登录</a></span>
		<h3 class="regis_top_h3">密码重置</h3>
	</div>
	<div class="Registered">
		<c:choose>
			<c:when test="${invalidKey}" >
				<form class="form-horizontal" action="#" id="form1">
					<div id="passwordGroup" class="control-group">
						<div class="controls Va_tips">
							<input class="m-wrap span3" type="password" name="password" id="password" class="password" minlength="6" maxlength="16" pwdStrengh="true" placeholder="密码">
						</div>
					</div>
					<div id="rePwdGroup" class="control-group">
						<div class="controls Va_tips">
							<input class="m-wrap span3" type="password" name="rePwd" id="rePwd" class="password" required equals="password" placeholder="确认密码">
						</div>
					</div>
				</form>
				<div class="Regis_but">
					<a class="Regisbuttom" href="javascript:saveSubmit();">确定重置</a>
				</div>
			</c:when>
			<c:otherwise>
			<div class="error-panel ng-hide">
				<i class="imgicon-warning"></i><br>
				<span>重置密码申请已经过期，请检查您的邮箱或者重新设置</span>
			</div>
			</c:otherwise>

		</c:choose>
	</div>
</div>
<div class="login_footer">2014-2015 版权所有©上海三零卫士信息安全有限公司 www.30ue.com </div>
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
<script>
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
	var myVld = new EstValidate2("form1");
	var myLoading = new MyLoading("${path}",{zindex : 11111});
	function saveSubmit(){
		var bool = myVld.form();
		if(bool){
			myLoading.show();
			$.ajax({
				url : "${path }/sys/admin/user/pwdreset/${key}",
				type : "post",
				data : $('#form1').serialize(),
				dataType : "json",
				success : function(json){
					myLoading.hide();
					if(json.code == 1){
						showTip("","重置密码成功",2000);
						setTimeout(function(){window.location.href = "${path}/admin/login"},2000) ;
					}else{
						showTip("",json.message);
					}
				},
				error : function(arg1, arg2, arg3){
					myLoading.hide();
					showTip("","出错了请联系管理员");
				}
			});
		}
	}

	function showTip(title,message,timeout){
		$("#messageModalContent1").html(message);
		$('#messageModal1').modal("show");
		if(timeout){
			setTimeout(function(){
				$('#messageModal1').modal("hide");
			},timeout);
		}
	}
</script>
</body>
</html>