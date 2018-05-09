<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加角色</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="portlet box grey">
            <div class="portlet-title">
                <div class="caption">
                    <span class="hidden-480">添加角色</span>
                </div>
            </div>
            <div class="portlet-body form">
                <form class="form-horizontal " id="form1" method="post">
                    <div class="tab-pane active" id="tab1">
                        <div>
                            <div id="roleNameGroup" class="control-group">
                                <label class="control-label mylabel">角色名<span
                                        class="required">*</span>
                                </label>
                                <div class="controls">
                                    <input class="span6 m-wrap" type="text" name="roleName" id="name" required
                                           maxlength="64">
                                </div>

                            </div>
                            <div id="roleCodeGroup" class="control-group">
                                <label class="control-label mylabel">角色代码<span
                                        class="required">*</span>
                                </label>
                                <div class="controls">
                                    <input class="span6 m-wrap" type="text" name="roleCode" id="roleCode" required
                                           placeholder="代码必须以'ROLE_'开头">
                                </div>
                            </div>
                            <div id="sortGroup" class="control-group">
                                <label class="control-label mylabel">排序<span
                                        class="required">*</span>
                                </label>
                                <div class="controls">
                                    <input class="span6 m-wrap" type="text" name="sort" id="sort" required min="1"
                                           max="99" value="99">
                                </div>
                            </div>
                            <div id="descriptionGroup" class="control-group">
                                <label class="control-label mylabel">描述<span class="required">*</span>
                                </label>
                                <div class="controls">
                                        <textarea class="span6 m-wrap" style="resize: none;" rows="5" id="description"
                                                  name="description" maxlength="255"></textarea>
                                </div>
                            </div>
                            <div class="form-actions">
                                <button id="submitBtn" type="button" class="btn green mybutton"
                                        onclick="saveSubmit()"><i class='icon-ok'></i> 确定
                                </button>
                                <a href="${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"
                                   class="btn btn-default"
                                   data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
                                </a>
                            </div>

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="text/javascript">
    var myVld = new EstValidate2("form1");//数据校验
    var myLoading = new MyLoading("${path}", {zindex: 11111});//加载提示
    function saveSubmit() {
        var bool = myVld.form();
        if (bool) {
            myLoading.show();
            $.ajax({
                url: "${path}/sys/tenant/role/save",
                type: "post",
                data: $('#form1').serialize(),
                dataType: "json",
                headers: {
                    "OWASP_CSRFTOKEN": "${sessionScope.OWASP_CSRFTOKEN}"
                },
                success: function (data) {
                    myLoading.hide();
                    if (data.success == "true" || data.success == true) {
                        showTip("提示", "操作成功", 2000);
                        setTimeout(function () {
                            window.location.href = "${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"
                        }, 2000);
                    } else {
                        showTip("提示", data.message, 2000);
                    }
                },
                error: function (arg1, arg2, arg3) {
                    myLoading.hide();
                    showTip("提示", "系统错误!", 2000);
                }
            });
        }
    }
</script>
</body>
</html>