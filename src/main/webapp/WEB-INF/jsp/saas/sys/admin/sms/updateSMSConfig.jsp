<%--
  Created by IntelliJ IDEA.
  User: Jason
  Date: 15/12/5
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改短信配置</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

            <div class="portlet box grey">

                <div class="portlet-title">

                    <div class="caption">

                        <span class="hidden-480">修改短信配置</span>

                    </div>
                    <div class="tools">
                        <a href="javascript:location.reload();" class="reload"></a>
                    </div>
                </div>

                <div class="portlet-body form">
                    <form action="" class="form-horizontal" id="addForm" method="post">
                        <input type="hidden" id="id" name="id" value="${config.id}"/>
                        <div id="smsNameGroup" class="control-group">

                            <label class="control-label">配置名<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="smsName" name="smsName" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.smsName)}">

                                <!-- <span class="help-inline">Some hint here</span> -->

                            </div>

                        </div>

                        <div class="control-group" id="smsServerGroup">

                            <label class="control-label">服务器地址<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="smsServer" name="smsServer" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.smsServer)}">

                            </div>

                        </div>
                        <div class="control-group" id="apikeyGroup">

                            <label class="control-label">apikey<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="apikey" name="apikey" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.apikey)}" >

                            </div>

                        </div>
                        <div class="control-group" id="versionGroup">

                            <label class="control-label">版本<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="version" name="version" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.version)}">

                            </div>

                        </div>
                        <div class="control-group" id="sendGroup">

                            <label class="control-label">发送uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="send" name="send" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.send)}">

                            </div>

                        </div>
                        <div class="control-group" id="tplSendGroup">

                            <label class="control-label">模板发送uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="tplSend" name="tplSend" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.tplSend)}">

                            </div>

                        </div>
                        <div class="control-group" id="getTplGroup">

                            <label class="control-label">获取模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="getTpl" name="getTpl" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.getTpl)}">

                            </div>

                        </div>
                        <div class="control-group" id="addTplGroup">

                            <label class="control-label">新增模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="addTpl" name="addTpl" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.addTpl)}">

                            </div>

                        </div>
                        <div class="control-group" id="updateTplGroup">

                            <label class="control-label">更新模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="updateTpl" name="updateTpl" class="span6 m-wrap" required maxlength="255" value="${cloud:htmlEscape(config.updateTpl)}">

                            </div>

                        </div>

                        <div id="delTplGroup" class="control-group">

                            <label class="control-label">删除模板uri</label>

                            <div class="controls">

                                <input class="span6 m-wrap" type="text" id="delTpl" name="delTpl"  maxlength="255" value="${cloud:htmlEscape(config.delTpl)}">

                            </div>

                        </div>
                        <div id="statusGroup" class="control-group">

                            <label class="control-label">是否启用<span class="required">*</span></label>

                            <div class="controls">


                                <select class="span6 m-wrap" id="status" name="status" data-placeholder="Choose a Category" tabindex="1" required >
                                    <option value="">请选择...</option>
                                    <c:if test="${config.status}">
                                        <option value="1" selected="selected">是</option>
                                        <option value="0">否</option>
                                    </c:if>
                                    <c:if test="${!config.status}">
                                        <option value="1">是</option>
                                        <option value="0" selected="selected">否</option>
                                    </c:if>

                                </select>

                            </div>

                        </div>
                        <div class="form-actions">
                            <a id="submit" class="btn green" ><i class="icon-ok"></i> 确定</a>
                            <a href="${path}/sys/admin/sms/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
                               data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
                            </a>
                        </div>
                        <!-- <div class="form-actions">
                            <button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i>关闭</button>
                            <button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i>保存</button>

                        </div> -->

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
            var bool = addForm.form()
            var aa = $("#addForm").serialize();
            if (bool) {
                $.ajax({
                    url : "<%=path%>/sys/admin/sms/update",
                    type : "post",
                    data : $("#addForm").serialize(),
                    headers: {
                        "OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    success : function(data){
                        if (data.success) {
                            showSaveSuccess();
                            setTimeout("window.location.href='${path}/sys/admin/sms/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';",1300);
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>
