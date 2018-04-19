<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script id="container" name="content" type="text/plain">
    </script>
    <script type="text/javascript">
    window.PATH = "${path}";
    </script>
    <!-- 配置文件,可以根据功能点有自己的文件 -->
    <script type="text/javascript" src="${path }/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 ,目前不能修改-->
    <script type="text/javascript" src="${path }/ueditor/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('container');
    </script>
    <button onclick="alert(ue.getContent())">获取转义后内容</button>
    <button onclick="alert(ue.hasContents())">判断是否空</button>
</body>
</html>