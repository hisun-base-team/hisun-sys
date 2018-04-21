<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<%
  boolean isAjax = request.getHeader("X-Requested-With") != null;
  pageContext.setAttribute("isAjax", isAjax);
  if(isAjax){
    response.setHeader("Content-Type", "text/plain;charset=UTF-8");
  }
%>
<c:if test="${isAjax}">
  {"success":false,"privilegeCode":-1,"message":"无效请求"}
</c:if>
<c:if test="${!isAjax}">
  <!DOCTYPE html>
  <html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>30云平台</title>
  </head>

  <!-- BEGIN BODY -->

  <body class="page-header-fixed white" style="background-color:#F8F8F8;">
  <div class="regis_1000" >
    <div class="regis_logo" style="text-align: center;margin-top: 50px;"><img src="${path}/images/templateImage/logo_login.png"></div>
    <div style="text-align: center;font-size: 20px;margin-top: 50px;">无效请求。</div>
  </div>
  </body>
</c:if>