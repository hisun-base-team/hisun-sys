<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>
                 <%@include file="../inc/servlet.jsp" %>
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

    <!-- END PAGE LEVEL STYLES -->
    <title>错误</title>
</head>
<body class="page-404-full-page">

<div class="row-fluid">

    <div class="span12 page-404">

        <div class="number" style="background-image:url(${pageContext.request.contextPath}/images/error-red.png);background-size:100% 100%;width: 256px;height: 230px">

        </div>

        <div class="details">

            <h3><%=exception.toString() %></h3>

            <p>

              	<br />

                <a href="${pageContext.request.contextPath}/dashboard">返回首页</a>或者尝试使用搜索栏。

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
