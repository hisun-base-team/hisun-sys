<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../inc/servlet.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/common/common.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/datetimepicker.css" rel="stylesheet" type="text/css"/>
    <c:set var="path" value="${pageContext.request.contextPath}"></c:set>
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link rel="stylesheet" href="${path }/css/DT_bootstrap.css"/>
    <%-- <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/> --%>
    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js" type="text/javascript"></script>

    <!-- END PAGE LEVEL STYLES -->
    <title>错误-访问拒绝</title>
</head>
<body class="page-404-full-page">
<div class="row-fluid">

    <div class="span12 page-404">

        <div class="number">

            403

        </div>

        <div class="details">

            <h3>您没有足够的权限访问该页面！</h3>

            <p>
                您没有足够的权限访问该页面<br />

                <a href="javascript:history.back(-1)">返回上一页。</a>

            </p>

            <%--<form action="#">--%>

            <%--<div class="input-append">--%>

            <%--<input class="m-wrap" size="16" type="text" placeholder="关键字..." />--%>

            <%--<button class="btn blue">搜索</button>--%>

            <%--</div>--%>

            <%--</form>--%>

        </div>

    </div>

</div>

</div>
</body>
</html>
