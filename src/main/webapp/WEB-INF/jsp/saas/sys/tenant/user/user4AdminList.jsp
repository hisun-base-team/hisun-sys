<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>

<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="portlet-title" style="vertical-align: middle;">
            <div class="caption">用户列表</div>
            <div class="clearfix fr">
                <a id="returnList" class="btn"
                   href="${path}/sys/tenant/tenant/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                    <i class="icon-undo"></i>返回
                </a>
            </div>
        </div>
        <div class="clearfix">
            <div class="control-group">
                <div id="query" style="float: left;">
                    <form action="${path}/sys/tenant/user/4admin/list" id="getUserForm" style="margin: 0 0 10px;"
                          method="get">
                        <input type="hidden" id="tenantId" name="tenantId" value="${tenantId}"/>
                        <input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
                        <input type="text" name="searchContent" id="searchContent" style="margin:0px;" class="m-wrap"
                               placeholder="用户名/姓名" value='${cloud:htmlEscape(searchContent)}'/>
                        <button type="submit" class="btn Short_but" id="searchButton">查询</button>
                        <button type="button" class="btn Short_but" id="resetButton">清空</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="portlet-body">
            <table class="table table-striped table-bordered table-hover dataTable table-set">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>姓名</th>
                    <th>职务</th>
                    <th>手机号码</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pager.datas}" var="user"
                           varStatus="status">
                    <tr style="height: 50px;">
                        <td class="editUser" userId="${user.id}" title="<c:out value="${user.username}"></c:out>"><a
                                href="javascript:void(0);"><c:out value="${user.username}"></c:out></a></td>
                        <td title="<c:out value="${user.realname}"></c:out>"><c:out
                                value="${user.realname}"></c:out></td>
                            <%--<td>
                                <c:choose>
                                    <c:when test="${user.sex}">
                                        <c:out value="男"></c:out>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="女"></c:out>
                                    </c:otherwise>
                                </c:choose>
                            </td>--%>
                        <td title="<c:out value="${user.positional}"></c:out>"><c:out
                                value="${user.positional}"></c:out></td>
                        <td title="<c:out value="${user.tel}"></c:out>"><c:out value="${user.tel}"></c:out></td>
                        <td>
                            <c:if test="${user.locked}">
                                锁定
                            </c:if>
                            <c:if test="${!user.locked}">
                                正常
                            </c:if>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${pager.pageCount>0}">
                <jsp:include page="/WEB-INF/jsp/common/page.jsp">
                    <jsp:param value="${pager.total}" name="total"/>
                    <jsp:param value="${pager.pageCount}" name="endPage"/>
                    <jsp:param value="${pager.pageNum}" name="page"/>
                    <jsp:param value="${pager.pageSize}" name="pageSize"/>
                </jsp:include>
            </c:if>
        </div>

    </div>
</div>

<!-- END PAGE CONTAINER-->
<script type="text/javascript" src="${path}/js/select2.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.form.js"></script>
<script type="text/javascript" src="${path}/js/DT_bootstrap.js"></script>
<script>
    jQuery(document).ready(function() {
        App.init();//必须，不然导航栏及其菜单无法折叠
    });
    $("#resetButton").click(function () {
        $("#searchContent").removeAttrs("value");
    });

    function pagehref(pageNum, pageSize) {
        var searchContent = $("#searchContent").val();
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        $.ajax({
            cache: false,
            type: 'POST',
            dataType: "html",
            headers: {
                "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
            },
            data: {
                "currentNodeId": currentNodeId,
                "currentNodeParentId": currentNodeParentId,
                "currentNodeName": currentNodeName,
                "searchContent": searchContent,
                "pageNum": pageNum,
                "pageSize": pageSize
            },
            url: "${path}/sys/tenant/user/ajax/list",// 请求的action路径
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (html) {
                $("#rightList").html(html);
            }
        });

    }

</script>
