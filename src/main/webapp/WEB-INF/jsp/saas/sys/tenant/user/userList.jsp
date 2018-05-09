<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <div id="myModal" class="modal hide fade">
        <div class="modal-header">
            <button data-dismiss="modal" class="close" type="button"></button>
            <h3 id="modalTitle">赋予角色</h3>
        </div>
        <div class="modal-body">
        </div>
    </div>
    <div class="row-fluid">
        <div class="portlet-title" style="vertical-align: middle;">
            <div class="caption">用户列表</div>
            <div class="clearfix fr">
                <a id="add" name="add" class="btn green" href="#"><i class="icon-plus"></i> 添加</a>
            </div>
        </div>
        <div class="clearfix">
            <div class="control-group">
                <div id="query" style="float: left;">
                    <form action="${path}/sys/tenant/user/ajax/list" id="getUserForm" style="margin: 0 0 10px;"
                          method="get">
                        <input type="hidden" id="currentNodeId" name="currentNodeId" value=""/>
                        <input type="hidden" id="currentNodeName" name="currentNodeName" value=""/>
                        <input type="hidden" id="currentNodeParentId" name="currentNodeParentId" value=""/>
                        <input type="hidden" name="OWASP_CSRFTOKEN" value="${sessionScope.OWASP_CSRFTOKEN}">
                        <input type="text" name="searchContent" id="searchContent" style="margin:0px;" class="m-wrap"
                               placeholder="用户名/姓名" value='${cloud:htmlEscape(searchContent)}'/>
                        <button type="button" class="btn Short_but" id="searchButton">查询</button>
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
                    <th>排序</th>
                    <th style="text-align:center;width:200px;">操作</th>
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
                        <td><c:out value="${user.sort}"></c:out></td>
                        <td style="text-align: center;" class="Left_alignment" title="">
                            <a href="javascript:void(0);" class="setRoles" userId="<c:out value="${user.id}"></c:out>"
                               userRealName="<c:out value="${user.realname}"></c:out>">
                                赋予角色
                            </a>|
                            <a href="javascript:void(0);" class="resetPwd"
                               userName="<c:out value="${user.username}"></c:out>"
                               userId="<c:out value="${user.id}"></c:out>"
                               userRealName="<c:out value="${user.realname}"></c:out>">
                                重置密码
                            </a>
                            <br>
                            <c:if test="${!user.locked}">
                                <a href="javascript:void(0);" class="lockUser"
                                   userId="<c:out value="${user.id}"></c:out>"
                                   userRealName="<c:out value="${user.realname}"></c:out>">
                                    锁定用户
                                </a>|
                            </c:if>
                            <c:if test="${user.locked}">
                                <a href="javascript:void(0);" class="unLockUser"
                                   userId="<c:out value="${user.id}"></c:out>"
                                   userRealName="<c:out value="${user.realname}"></c:out>">
                                    解锁用户
                                </a>|
                            </c:if>
                            <a href="javascript:void(0);" class="delUser" userId="<c:out value="${user.id}"></c:out>"
                               userRealName="<c:out value="${user.realname}"></c:out>">
                                注销用户
                            </a>
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
        <div class="modal-scrollable" style="z-index: 10050;display: none;" id="setRolesModal">
            <div id="responsive" class="modal hide fade in" tabindex="-1" style="display: block; width:760px;top:25%;">
                <div class="span12">
                    <div class="modal-header">
                        <button class="close" id="setRolesModalClose" type="button"></button>
                        <h3 id="modalTitle">授予角色</h3>
                    </div>
                    <div class="modal-body form">
                        <form action="${path }/sys/resource/add" class="form-horizontal" id="addForm" method="post">
                            <input type="hidden" id="pId" name="pId" value=""/>
                            <input type="hidden" id="id" name="id" value=""/>
                            <div id="resourceNameGroup" class="control-group">
                                <label class="control-label">选择角色<span class="required">*</span></label>

                            </div>
                            <div class="control-group mybutton-group">
                                <button id="setRolesModalCancel" type="button" data-dismiss="modal" class="btn"
                                        style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i> 关闭
                                </button>
                                <button id="setRolesModalSubmit" type="button" class="btn green" style="float: right;">
                                    <i class="icon-ok"></i> 确定
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTAINER-->
<script type="text/javascript" src="${path}/js/select2.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.form.js"></script>
<script type="text/javascript" src="${path}/js/DT_bootstrap.js"></script>
<script>


    $("#resetButton").click(function () {
        $("#searchContent").removeAttrs("value");
    });

    $("#searchButton").click(function () {
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
                "searchContent": searchContent
            },
            url: "${path}/sys/tenant/user/ajax/list",// 请求的action路径
            error: function () {// 请求失败处理函数
                showTip("提示", "系统错误!", 2000);
            },
            success: function (html) {
                $("#rightList").html(html);
            }
        });
    });

    $("#add").click(function () {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        window.location.href = "${path}/sys/tenant/user/add?currentNodeId=" + currentNodeId + "&currentNodeParentId=" + currentNodeParentId + "&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
    });


    $(".editUser").click(function () {
        var userId = $(this).attr("userId");
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        window.location.href = "${path}/sys/tenant/user/edit/" + userId + "?currentNodeId=" + currentNodeId + "&currentNodeParentId=" + currentNodeParentId + "&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";

    });

    $(".setRoles").click(function () {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        var modal = $("#setRolesModal");
        modal.show();
    });
    $("#setRolesModalCancel").click(function () {
        var modal = $("#setRolesModal");
        modal.hide();
    });
    $("#setRolesModalClose").on("click", function () {
        var modal = $("#setRolesModal");
        modal.hide();
    });

    $(".resetPwd").on("click", function () {
        var userRealName = $(this).attr("userRealName");
        var userId = $(this).attr("userId");
        var userName = $(this).attr("userName");
        actionByConfirm1("", "${path}/sys/tenant/user/reset/password/" + userId, {}, function (data, status) {
            if (data.success == true) {
                showTip("提示", "重置密码成功！", 2000);
            } else {
                showTip("提示", data.msg, 2000);
            }
        }, "为用户:" + userRealName + ",重置密码为:" + userName + "111111");
    });


    $(".lockUser").on("click", function () {
        var userRealName = $(this).attr("userRealName");
        var userId = $(this).attr("userId");
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        actionByConfirm1("", "${path}/sys/tenant/user/lock/" + userId, {}, function (data, status) {
            if (data.success == true) {
                showTip("提示", "锁定成功！", 2000);
                $.ajax({
                    cache: false,
                    type: 'POST',
                    dataType: "html",
                    data: {
                        "currentNodeId": currentNodeId,
                        "currentNodeParentId": currentNodeParentId,
                        "currentNodeName": currentNodeName
                    },
                    headers: {
                        "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    url: "${path}/sys/tenant/user/ajax/list",// 请求的action路径
                    error: function () {// 请求失败处理函数
                        alert('请求失败');
                    },
                    success: function (html) {
                        $("#rightList").html(html);
                    }
                });
            } else {
                showTip("提示", data.msg, 2000);
            }
        }, "锁定用户:" + userRealName + " ");
    });

    $(".unLockUser").on("click", function () {
        var userRealName = $(this).attr("userRealName");
        var userId = $(this).attr("userId");
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        actionByConfirm1("", "${path}/sys/tenant/user/unlock/" + userId, {}, function (data, status) {
            if (data.success == true) {
                showTip("提示", "解锁成功！", 2000);
                $.ajax({
                    cache: false,
                    type: 'POST',
                    dataType: "html",
                    data: {
                        "currentNodeId": currentNodeId,
                        "currentNodeParentId": currentNodeParentId,
                        "currentNodeName": currentNodeName
                    },
                    headers: {
                        "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    url: "${path}/sys/tenant/user/ajax/list",// 请求的action路径
                    error: function () {// 请求失败处理函数
                        alert('请求失败');
                    },
                    success: function (html) {
                        $("#rightList").html(html);
                    }
                });
            } else {
                showTip("提示", data.msg, 2000);
            }
        }, "解锁用户:" + userRealName + " ");
    });


    $(".delUser").on("click", function () {
        var userRealName = $(this).attr("userRealName");
        var userId = $(this).attr("userId");
        showPrompModal(userRealName, "这个操作将不能撤销,它将删除[" + userRealName + "]的账号，删除后将不可恢复，请认真审核确认后再进行操作，确认后请在下面填写你要删除的账号姓名。", "",
                "${path}/sys/tenant/user/delete/" + userId, {}, function (data, status) {
                    if (data.success == true) {
                        showTip("提示", "注销成功！", 2000);
                        //TODO
                    } else {
                        showTip("提示", data.msg, 2000);
                    }
                });
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
