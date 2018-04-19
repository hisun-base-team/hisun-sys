<%--
  Created by IntelliJ IDEA.
  User: Jason
  Date: 15/12/8
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SQL监控</title>
</head>
<body>
    <div class="container-fluid" style="text-align: center;margin-top: 20%;">
        <shiro:hasPermission name="sql:druid">
            <a href="${path}/admin/druid/index.html"class="btn btn-default blue"
               data-dismiss="modal" target="_blank">查看SQL监控页</a>
        </shiro:hasPermission>
    </div>
</body>
</html>