<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="portlet-title" style="vertical-align: middle;">
            <div class="caption">部门列表</div>
            <div class="clearfix fr">
                <a id="add" class="btn green" href="#"><i class="icon-plus"></i> 添加</a>
                <%--<a id="jztp" class="btn green" href="#"><i class="icon-plus"></i> 加载图片</a>--%>
            </div>
        </div>
        <div class="clearfix">
            <table class="table table-striped table-bordered table-hover dataTable table-set">
                <thead>
                <tr>
                    <!-- <th>字典类型名</th> -->
                    <th>部门名称</th>
                    <th>排序</th>
                    <th width="90">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pager.datas}" var="vo">
                    <tr>
                        <td><c:out value="${vo.name }"></c:out></td>
                        <td><c:out value="${vo.sort }"></c:out></td>
                        <td><a href="javascript:update('${vo.id}');">编辑</a>|<a id="${vo.id}" voname="${vo.name}"
                                                                               href="javascript:void(0)"
                                                                               onclick="del(this);">删除</a>
                        </td>
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

    <div class="modal-scrollable" style="z-index: 10050;display: none;" id="del1">
        <div id="static" class="modal hide fade in" tabindex="-1"
             data-backdrop="static" data-keyboard="false" style="display: block;">
            <div class="modal-body">
                <p>该树节点还有子节点，不能删除!</p>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn" id="delCancel1">关闭</button>
            </div>
        </div>

    </div>

</div>
<script type="text/javascript">
    $("#add").click(function () {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        $.ajax({
            url: "${path}/sys/tenant/department/ajax/add?currentNodeId=" + currentNodeId + "&currentNodeName=" + currentNodeName + "&currentNodeParentId=" + currentNodeParentId,
            type: "get",
            data: null,
            dataType: "html",
            headers: {
                "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
            },
            success: function (html) {
                $("#rightList").html(html);
            },
            error: function () {

            }
        });
    });

    <%--$("#jztp").click(function () {--%>
        <%--window.open("${path}/zzb/dzda/mlcl/jztp/index?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}");--%>
    <%--});--%>


    function update(id) {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        $.ajax({
            url: "${path}/sys/tenant/department/ajax/edit/" + id + "?currentNodeId=" + currentNodeId + "&currentNodeName=" + currentNodeName + "&currentNodeParentId=" + currentNodeParentId,
            type: "get",
            data: null,
            dataType: "html",
            headers: {
                "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
            },
            success: function (html) {
                $("#rightList").html(html);
            },
            error: function () {

            }
        });
    }
    function pagehref(pageNum, pageSize) {
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
                "pageNum": pageNum,
                "pageSize": pageSize
            },
            url: "${path}/sys/tenant/department/ajax/list",// 请求的action路径
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (html) {
                $("#rightList").html(html);
            }
        });
    }

    function del(obj) {
        var id = obj.id;
        var voname = $(obj).attr("voname");
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        actionByConfirm1(voname, "${path}/sys/tenant/department/delete/" + id, {}, function (data, status) {
            if (data.success == true) {
                showTip("提示", "成功删除！", 2000);
                refreshTree();
                var currentNodeId = $("#currentNodeId").val();
                var currentNodeName = $("#currentNodeName").val();
                var currentNodeParentId = $("#currentNodeParentId").val();
                $.ajax({
                    url: "${path}/sys/tenant/department/ajax/list",
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
                    success: function (html) {
                        $("#rightList").html(html);
                    },
                    error: function () {
                        showTip("提示", "删除失败!", 2000);
                    }
                });
            } else {
                showTip("提示", data.msg, 2000);
            }
        });
    }

</script>