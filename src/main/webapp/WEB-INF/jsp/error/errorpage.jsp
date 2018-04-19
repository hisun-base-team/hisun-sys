<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
        <%@include file="../inc/servlet.jsp" %>
<%@include file="../inc/taglib.jsp" %>  
<!DOCTYPE html>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/common/common.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/datetimepicker.css" rel="stylesheet" type="text/css"/>
    <c:set var="path" value="${pageContext.request.contextPath}"></c:set>
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
    <%-- <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/> --%>
    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js" type="text/javascript"></script>
    <script type="application/javascript">
        jQuery(document).ready(function() {
            App.init();//必须，不然导航栏及其菜单无法折叠
        });

    </script>
    <!-- END PAGE LEVEL STYLES -->
    <title>错误</title>
</head>
<body class="page-404-full-page">
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<div class="row-fluid">

    <div class="span12 page-404">

        <div class="number" style="background-image:url(${pageContext.request.contextPath}/images/error-red.png);background-size:100% 100%;width: 256px;height: 256px">

        </div>

        <div class="details">

            <h3>噢，系统出现问题了！</h3>

            <p>

                我们会尽快解决它！<br />

                请稍后再访问。<br /><br />
                <a href="${pageContext.request.contextPath}">返回首页</a>或者尝试使用搜索栏。

            </p>

            <form action="#">

                <div class="input-append">

                    <input class="m-wrap" size="16" type="text" placeholder="关键字..." />

                    <button class="btn blue">搜索</button>

                </div>

            </form>

        </div>

    </div>

</div>
</body>
</html>
