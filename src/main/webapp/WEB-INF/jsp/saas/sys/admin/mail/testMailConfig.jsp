<%--
  Created by IntelliJ IDEA.
  User: Jason
  Date: 15/12/6
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试邮件配置</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

            <div class="portlet box grey">

                <div class="portlet-title">

                    <div class="caption">

                        <span class="hidden-480">测试发送邮件模板</span>

                    </div>
                    <div class="tools">
                        <a href="javascript:location.reload();" class="reload"></a>
                    </div>
                </div>

                <div class="portlet-body form">
                    <form action="" class="form-horizontal" id="addForm" method="post">
                        <input type="hidden" id="id" name="id" value="${id}"/>
                        <div id="fromGroup" class="control-group">

                            <label class="control-label">发信地址<span class="required">*</span></label>

                            <div class="controls">


                                <input id="from" class="span6 m-wrap myinput" name="from" type="text" customEmail="true" required>
                                <!-- <span class="help-inline">Some hint here</span> -->

                            </div>

                        </div>
                        <div id="toGroup" class="control-group">

                            <label class="control-label">接收者地址<span class="required">*</span></label>

                            <div class="controls">


                                <input id="to" class="span6 m-wrap myinput" name="to" type="text" customEmail="true" required>
                                <!-- <span class="help-inline">Some hint here</span> -->

                            </div>

                        </div>
                        <div class="control-group" id="tplGroup">

                            <label class="control-label mylabel">选择测试模板<span
                                    class="required">*</span>
                            </label>

                            <div class="controls inputtext">

                                <select id="tpl" name="tpl" class="span6 m-wrap myinput" required>
                                    <option value="" item="">选择测试模板</option>
                                    <c:forEach items="${tpls}" var="tpl">
                                        <option value="${tpl.id}" item="${tpl.subject}">${tpl.name}</option>
                                    </c:forEach>
                                </select>
                                <span class="help-inline"></span>

                            </div>

                        </div>
                        <div class="control-group" id="titleGroup">

                            <label class="control-label mylabel">邮件主题<span
                                    class="required">*</span>
                            </label>

                            <div class="controls inputtext">

                                <input type="text" class="span6 m-wrap myinput" name="title" id="title"
                                       class="required" required/> <span
                                    class="help-inline"></span>

                            </div>

                        </div>
                        <div class="form-actions">
                            <a id="submit" class="btn green" ><i class="icon-ok"></i> 发送测试邮件</a>
                            <a href="${path}/sys/admin/mail/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
                               data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
                            </a>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript">
    var addForm = new EstValidate("addForm");
    $(function(){
        $("#submit").on("click",function() {
            var bool = addForm.form();
            var aa = $("#addForm").serialize();
            if (bool) {
                $.ajax({
                    url : "${path}/sys/admin/mail/test",
                    type : "post",
                    data : $("#addForm").serialize(),
                    headers: {
                        "OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    success : function(data){
                        if (data.success) {
                            showTip("提示", "邮件已发送成功！",null);
                            setTimeout("window.location.href='${path}/sys/admin/mail/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';",1300);
                        }else{
                            showTip("提示", "邮件发送失败！");
                        }
                    }
                });
            }
        });

        $("#tpl").on("change",function(){
            $("#title").val($($(this).find("option:selected")[0]).attr("item"));
        });
    });
</script>
</body>
</html>
