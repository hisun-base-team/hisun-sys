<%@ page import="java.util.Properties" %>
<%@ page import="com.hisun.util.ApplicationContextUtil" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<%
    Properties resourcesProperties = ApplicationContextUtil.getBean("resourcesProperties", Properties.class);
    String sysName = resourcesProperties.get("sys.name").toString();
    String sysLoginLogo = resourcesProperties.get("sys.login.logo").toString();
    pageContext.setAttribute("sysName", sysName);
    pageContext.setAttribute("sysLoginLogo", sysLoginLogo);
    boolean isAjax = request.getHeader("X-Requested-With") != null;
    pageContext.setAttribute("isAjax", isAjax);
    if (isAjax) {
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
    }
%>
<c:if test="${isAjax}">
    {"success":false,"code":-1,"message":"无效请求"}
</c:if>
<c:if test="${!isAjax}">
    <!DOCTYPE html>
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/common/common.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/datetimepicker.css" rel="stylesheet" type="text/css"/>
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link rel="stylesheet" href="${path }/css/DT_bootstrap.css"/>
            <%-- <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/> --%>
        <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js" type="text/javascript"></script>
        <title>错误-CSRF</title>
    </head>
    <body class="page-404-full-page">
    <div class="row-fluid">
        <div class="span12 page-404">
            <div class="number">
                400
            </div>
            <div class="details">
                <h3>遇到问题了！</h3>
                <p>
                    当前请求已被服务器拦截<br/>
                    <a href="javascript:history.back(-1)">返回上一页。</a>
                </p>
                <%--<form action="#">--%>
                    <%--<div class="input-append">--%>
                        <%--<input class="m-wrap" size="16" type="text" placeholder="关键字..."/>--%>
                        <%--<button class="btn blue">搜索</button>--%>
                    <%--</div>--%>
                <%--</form>--%>
            </div>
        </div>
    </div>
    </body>
</c:if>