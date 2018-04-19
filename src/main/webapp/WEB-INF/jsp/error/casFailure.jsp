<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <script type="application/javascript">
        jQuery(document).ready(function() {
            App.init();//必须，不然导航栏及其菜单无法折叠
        });

    </script>
<title>CAS认证失败</title>
</head>
<body>
CAS失败了，如错误的Ticket或证书错误
</body>
</html>