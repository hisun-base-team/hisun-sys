<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<div class="span6" style="width: 100%; margin: 0px;">
    <div class="portlet-title" style="vertical-align: middle;">
        <div class="caption">字典项</div>
        <div class="clearfix fr">
            <a id="addItem" class="btn green" href="#">
                <i class="icon-plus"></i> 添加
            </a>
            <a id="returnDicTypeList" class="btn btn-default" href="#">
                <i class="icon-arrow-left"></i> 返回
            </a>
        </div>
    </div>
    <div class="clearfix">
        <form action="${path }/sys/admin/dictionary/item/ajax/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"
              method="post" id="searchForm" name="searchForm" style="margin: 0 0 0px;">
            <input type="text" class="m-wrap" name="searchName" id="searchName" value="${searchName}"
                   placeholder="字典项名称/代码">
            <button type="button" class="btn  Short_but" id="searchButton">查询</button>
            <button type="button" class="btn  Short_but" id="resetButton">清空</button>
        </form>
    </div>
    <div class="portlet-body">
        <table class="table table-striped table-bordered table-hover dataTable table-set">
            <thead>
            <tr>
                <!-- <th>字典类型名</th> -->
                <th >字典项名</th>
                <th width="20%">字典项值</th>
                <th width="10%">是否显示</th>
                <th width="10%">排序</th>
                <th width="90">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pager.datas}" var="item">
                <tr >
                    <td <c:if test="${item.display eq 1}">class="col9"</c:if> ><c:out value="${item.name }"></c:out></td>
                    <td <c:if test="${item.display eq 1}">class="col9"</c:if> ><c:out value="${item.code }"></c:out></td>
                    <td <c:if test="${item.display eq 1}">class="col9"</c:if> ><c:out value="${item.displayStr }"></c:out></td>
                    <td <c:if test="${item.display eq 1}">class="col9"</c:if> ><c:out value="${item.sort }"></c:out></td>
                    <td>
                            <%--<a href="javascript:;" class="btn mini blue "  onclick="updateItem('${items.id}');"><i class="icon-pencil"></i>&nbsp;修&nbsp;改</a>--%>
                            <%--&nbsp;&nbsp;&nbsp;--%>
                            <%--<a href="javascript:;" class="btn mini red" id="${items.id}" itemname="<c:out value="${items.item }"></c:out>" onclick="delItem(this);"><i class="icon-trash"></i>&nbsp;删&nbsp;除</a>--%>
                            <%--<input type="hidden" name="delId" id="delId" >--%>
                        <a href="javascript:updateItem('${item.id}');">编辑</a>
                        |<a id="${item.id}" itemname="<c:out value="${item.name }"></c:out>" href="javascript:void(0)"
                            onclick="delItem(this);">删除</a>
                            <%--<a href="javascript:;" class="btn mini blue "  onclick="updateItem('${item.id}');"><i class="icon-pencil"></i>&nbsp;修&nbsp;改</a>--%>
                            <%--<a href="javascript:;" class="btn mini red" id="${item.id}" itemname="<c:out value="${item.name }"></c:out>" onclick="delItem(this);"><i class="icon-trash"></i>&nbsp;删&nbsp;除</a>--%>
                            <%--<input type="hidden" name="delId" id="delId" >--%>

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
<script type="text/javascript">
    $("#addItem").click(function () {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        var typeId = $("#typeId").val();
        $.ajax({
            url: "${path}/sys/admin/dictionary/item/ajax/add?typeId=" + typeId + "&currentNodeId=" + currentNodeId + "&currentNodeName=" + currentNodeName + "&currentNodeParentId=" + currentNodeParentId,
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

    $("#returnDicTypeList").click(function(){
        window.location.href = "${path}/sys/admin/dictionary/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
    });

    function updateItem(id) {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        var typeId = $("#typeId").val();
        $.ajax({
            url: "${path}/sys/admin/dictionary/item/ajax/edit/" + id + "?typeId=" + typeId + "&currentNodeId=" + currentNodeId + "&currentNodeName=" + currentNodeName + "&currentNodeParentId=" + currentNodeParentId,
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
    $("#resetButton").click(function () {
        $("#searchName").removeAttrs("value");
    });


    $("#searchButton").click(function () {
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        var searchName = $("#searchName").val();
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
                "typeId": "${typeId}",
                "searchName":searchName
            },
            url: "${path}/sys/admin/dictionary/item/ajax/list",// 请求的action路径
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (html) {
                $("#rightList").html(html);
            }
        });

    });

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
                "typeId": "${typeId}",
                "searchName":"${searchName}",
                "pageNum": pageNum,
                "pageSize": pageSize
            },
            url: "${path}/sys/admin/dictionary/item/ajax/list",// 请求的action路径
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (html) {
                $("#rightList").html(html);
            }
        });
    }

    function delItem(obj) {
        var id = obj.id;
        var itemName = $(obj).attr("itemname");
        var currentNodeId = $("#currentNodeId").val();
        var currentNodeName = $("#currentNodeName").val();
        var currentNodeParentId = $("#currentNodeParentId").val();
        actionByConfirm1(itemName, "${path}/sys/admin/dictionary/item/delete/" + id, {}, function (data, status) {

            if (data.success == true) {
                showTip("提示", "成功删除！", 2000);
                refreshTree();
                var currentNodeId = $("#currentNodeId").val();
                var currentNodeName = $("#currentNodeName").val();
                var currentNodeParentId = $("#currentNodeParentId").val();
                $.ajax({
                    url: "${path}/sys/admin/dictionary/item/ajax/list",// 请求的action路径
                    type: 'POST',
                    dataType: "html",
                    data: {
                        "currentNodeId": currentNodeId,
                        "currentNodeParentId": currentNodeName,
                        "currentNodeName": currentNodeParentId,
                        "typeId": "${typeId}"
                    },
                    headers: {
                        "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    success: function (html) {
                        $("#rightList").html(html);
                    },
                    error: function () {
                        alert('请求失败');
                    }
                });
            } else {
                showTip("提示", data.msg, 2000);
            }
        });
    }

</script>