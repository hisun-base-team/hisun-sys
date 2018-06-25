<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>操作日志</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="portlet-title" style="vertical-align: middle;">
            <div class="caption">操作日志</div>
            <div class="clearfix fr">
            </div>
        </div>
        <div class="clearfix">
            <form action="${path}/sys/tenant/log/operate/list" id="getLogForm" style="margin: 0 0 10px;" method="get"
                  class="form-horizontal">
                <input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
                <span class="inp_name">开始日期:</span>
                <div class="input-append" id="starttimeDiv">
                    <input type="text" class="span12" style="width: 100px;"
                           value='<c:out value="${param.starttime }"></c:out>' name="starttime" id="starttime"
                           readonly/>
                </div>
                <span class="inp_name">结束日期:</span>
                <div class="input-append" id="endtimeDiv">
                    <input type="text" class="span12" style="width: 100px;"
                           value='<c:out value="${param.endtime }"></c:out>' name="endtime" id="endtime" readonly/>
                </div>

                <span class="inp_name">用户:</span>
                <input type="text" size="16" class="m-wrap" style="width: 100px;" name="userName" id="userName"
                       style="margin:0px;" placeholder="" value="${cloud:htmlEscape(param.userName)}">
                <span class="inp_name" style="margin-left: 10px;">内容:</span>
                <input type="text" class="m-wrap" style="width: 100px;" name="searchContent" id="searchContent"
                       placeholder="" value="${cloud:htmlEscape(param.searchContent)}">
                <span class="inp_name" style="margin-left: 10px;">操作类型:</span>
                <select class="m-wrap select_form" tabindex="-1" name="type" id="type" style="width: 6%;">
                    <option value="">全部</option>
                    <option value="1" ${param.type eq '1'?'selected="selected"':''}>新增</option>
                    <option value="2" ${param.type eq '2'?'selected="selected"':''}>修改</option>
                    <option value="3" ${param.type eq '3'?'selected="selected"':''}>删除</option>
                    <option value="4" ${param.type eq '4'?'selected="selected"':''}>查询</option>
                    <option value="7" ${param.type eq '7'?'selected="selected"':''}>下载</option>
                    <option value="8" ${param.type eq '8'?'selected="selected"':''}>打印</option>
                </select>
                <button type="submit" class="btn  Short_but">查询</button>
                <button id="submitBtn" type="button" class="btn Short_but">清空</button>
            </form>
        </div>
        <div class="portlet-body">
            <table class="table table-striped table-bordered table-hover dataTable table-set">
                <thead>
                <tr>
                    <th style="width: 8%">用户</th>
                    <th style="width: 10%">地址</th>
                    <th style="width: 15%">操作时间</th>
                    <%--<th style="width: 20%">调用方法</th>--%>
                    <th>操作说明</th>
                    <th style="width: 8%">类型</th>
                    <th style="width: 8%">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pager.datas}" var="logs">
                    <tr>
                        <td><c:out value="${logs.userName }"></c:out></td>
                        <td><c:out value="${logs.ip }"></c:out></td>
                        <td><fmt:formatDate value="${logs.operateTime }" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                        <%--<td><c:out value="${logs.invokeMethod}"></c:out></td>--%>
                        <td title="<c:out value="${logs.content }"></c:out>"><c:out
                                value="${logs.content }"></c:out></td>
                        <td><c:out value="${logs.typeStr }"></c:out></td>
                        <td><c:out value="${logs.statusStr }"></c:out></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <jsp:include page="/WEB-INF/jsp/common/page.jsp">
                <jsp:param value="${pager.total }" name="total"/>
                <jsp:param value="${pager.pageCount }" name="endPage"/>
                <jsp:param value="${pager.pageNum }" name="page"/>
                <jsp:param value="${pager.pageSize }" name="pageSize"/>
            </jsp:include>
        </div>

    </div>
</div>
<script type="text/javascript" src="${path}/js/select2.min.js"></script>
<script type="text/javascript"
        src="${path}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.form.js"></script>
<script type="text/javascript" src="${path}/js/DT_bootstrap.js"></script>
<script type="text/javascript" src="${path}/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${path}/js/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="${path}/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${path}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function () {
        App.init();//必须，不然导航栏及其菜单无法折叠

        $("#submitBtn").on("click", function () {
            //$('#getLogForm')[0].reset();
            $("#starttime").val("");
            $("#endtime").val("");
            $("#userName").val("");
            $("#searchContent").val("");
        });

        $('#starttime').datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN'
        });
        $('#endtime').datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN'
        });
    });

    function pagehref(pageNum, pageSize) {
        var url = location.href;
        var length = url.indexOf("?");
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        var userName = $("#userName").val();
        userName = userName ? userName : "";
        var searchContent = $("#searchContent").val();
        searchContent = searchContent ? searchContent : "";
        var type = $("#type").val();
        length = length > 0 ? length : url.length;
        window.location.href = url.substring(0, length) + "?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum=" + pageNum + "&pageSize=" + pageSize + "&starttime=" + starttime + "&endtime=" + endtime + "&userName=" + userName + "&type=" + type;
    }
</script>
</body>
</html>