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
    <title>角色管理</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="portlet-title" style="vertical-align: middle;">
            <div class="caption">角色列表</div>
            <div class="clearfix fr">
                <a id="addRole" class="btn green"
                   href="${path}/sys/tenant/role/add?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">
                    <i class="icon-plus"></i>添加
                </a>
            </div>
        </div>
        <div class="clearfix">
            <form action="${path }/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"
                  method="post" id="searchForm" name="searchForm" style="margin: 0 0 0px;" >
                <input type="text" class="m-wrap" name="searchName" id="searchName" value="${searchName}" placeholder="角色名称/代码">
                <button type="submit" class="btn  Short_but">查询</button>
                <button type="button" class="btn  Short_but" id ="resetButton">清空</button>
            </form>
        </div>
        <div class="portlet-body">
            <table class="table table-striped table-bordered table-hover dataTable table-set">
                <thead>
                <tr>
                    <th width="20%">名称</th>
                    <th width="15%">代码</th>
                    <th>描述</th>
                    <th width="8%">排序</th>
                    <th width="200px;">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.pager.datas}" var="entity">
                    <tr style="text-overflow:ellipsis;">
                        <td><c:out value="${entity.roleName }"></c:out></td>
                        <td><c:out value="${entity.roleCode }"></c:out></td>
                        <td title="<c:out value="${entity.description}"></c:out>"><c:out
                                value="${entity.description}"></c:out></td>
                        <td>${entity.sort}</td>
                        <td class="Left_alignment">
                            <a href="${path }/sys/tenant/role/edit/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">修改</a>
                            |
                            <a href="${path }/sys/tenant/role/privilegeManage/${entity.id}?roleName=${entity.roleName }&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}">授权</a>
                            <c:if test="${entity.isDefault ne 1}">
                            |
                            <a href="javascript:void(0)" class="delRole" roleId="${entity.id}">删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <jsp:include page="/WEB-INF/jsp/common/page.jsp">
                <jsp:param value="${pager.total }" name="total"/>
                <jsp:param value="${pager.pageCount }" name="endPage"/>
                <jsp:param value="${pager.pageSize }" name="pageSize"/>
                <jsp:param value="${pager.pageNum }" name="page"/>
            </jsp:include>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        App.init();
    });
    function pagehref(pageNum, pageSize) {
        window.location.href = "${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&pageNum=" + pageNum + "&pageSize=" + pageSize + "&searchName=" + encodeURI(encodeURI("${searchName}"));
    }

    $(".delRole").click(function(){
        var roleId = $(this).attr("roleId");
        actionByConfirm1('', "${path}/sys/tenant/role/delete/" + roleId, null, function (data) {
            if (data.success == true) {
                showTip("提示", "操作成功");
                setTimeout(function () {
                    window.location.href = "${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}&searchName=" + encodeURI(encodeURI("${searchName}"));
                }, 1500);

            } else {
                showTip("提示", data.message, 2000);
            }
        }, "删除");
    });
    $("#resetButton").click(function () {
        $("#searchName").removeAttrs("value");
    });

</script>
</body>
</html>
