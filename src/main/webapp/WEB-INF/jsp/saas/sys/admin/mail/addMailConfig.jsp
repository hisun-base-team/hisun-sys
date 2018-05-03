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
    <title>新增邮件配置</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

            <div class="portlet box grey">

                <div class="portlet-title">

                    <div class="caption">

                        <span class="hidden-480">新增邮件配置</span>

                    </div>
                    <div class="tools">
                        <a href="javascript:location.reload();" class="reload"></a>
                    </div>
                </div>

                <div class="portlet-body form">
                    <form action="#" class="form-horizontal" id="addForm" method="post">
                        <input type="hidden" id="id" name="id" value=""/>
                        <div id="nameGroup" class="control-group">

                            <label class="control-label">邮件名<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="name" name="name" class="span6 m-wrap" required maxlength="255">

                                <!-- <span class="help-inline">Some hint here</span> -->

                            </div>

                        </div>

                        <div class="control-group" id="accountGroup">

                            <label class="control-label">邮件账户<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" id="account" name="account" class="span6 m-wrap" required maxlength="255">

                            </div>

                        </div>

                        <div id="passwordGroup" class="control-group">

                            <label class="control-label">密码<span class="required">*</span></label>

                            <div class="controls">

                                <input class="span6 m-wrap" type="password" id="password" required name="password"  maxlength="255">


                            </div>

                        </div>

                        <div id="emailGroup" class="control-group">

                            <label class="control-label">发件人邮箱<span class="required">*</span></label>

                            <div class="controls">

                                <input class="span6 m-wrap" type="text" id="email" name="email" maxlength="255" required maxlength="255">
                                <!-- <span class="help-inline">除目录外,其他资源都需填写</span> -->

                            </div>

                        </div>

                        <div id="emailServerGroup" class="control-group">

                            <label class="control-label">SMTP<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="emailServer"  required name="emailServer" >

                            </div>

                        </div>
                        <div id="mailServerGroup" class="control-group">

                            <label class="control-label">邮件服务器地址</label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="mailServer" name="mailServer" >

                            </div>

                        </div>
                        <div id="versionGroup" class="control-group">

                            <label class="control-label">版本</label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="version"   name="version" >

                            </div>

                        </div>
                        <div id="apiUserGroup" class="control-group">

                            <label class="control-label">apiUser<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="apiUser"  name="apiUser" >

                            </div>

                        </div>
                        <div id="apiKeyGroup" class="control-group">

                            <label class="control-label">apiKey<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="apiKey" name="apiKey" >

                            </div>

                        </div>
                        <div id="sendGroup" class="control-group">

                            <label class="control-label">发送邮件uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="send"  name="send" >

                            </div>

                        </div>
                        <div id="sendTemplateGroup" class="control-group">

                            <label class="control-label">模板发送邮件uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="sendTemplate"  name="sendTemplate" >

                            </div>

                        </div>
                        <div id="addTemplateGroup" class="control-group">

                            <label class="control-label">添加模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="addTemplate" name="addTemplate" >

                            </div>

                        </div>
                        <div id="deleteTemplateGroup" class="control-group">

                            <label class="control-label">删除模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="deleteTemplate" name="deleteTemplate" >

                            </div>

                        </div>
                        <div id="updateTemplateGroup" class="control-group">

                            <label class="control-label">更新模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="updateTemplate" name="updateTemplate" >

                            </div>

                        </div>
                        <div id="getTemplateGroup" class="control-group">

                            <label class="control-label">获取模板uri<span class="required">*</span></label>

                            <div class="controls">

                                <input type="text" class="span6 m-wrap popovers" id="getTemplate"  required name="getTemplate" >

                            </div>

                        </div>
                        <div id="statusGroup" class="control-group">

                            <label class="control-label">是否启用<span class="required">*</span></label>

                            <div class="controls">

                                <select class="span6 m-wrap" id="status" name="status" data-placeholder="Choose a Category" tabindex="1" required>
                                    <option value="">请选择...</option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>

                            </div>

                        </div>

                        <div id="typeGroup" class="control-group">

                            <label class="control-label">是否批量<span class="required">*</span></label>

                            <div class="controls">

                                <select class="span6 m-wrap" id="type" name="type" data-placeholder="Choose a type" tabindex="1" required>
                                    <option value="">请选择...</option>
                                    <option value="1">批量</option>
                                    <option value="0">触发</option>
                                </select>

                            </div>

                        </div>
                        <div class="form-actions">
                            <a id="submit" class="btn green" ><i class="icon-ok"></i> 确定</a>
                            <a href="${path}/sys/admin/mail/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
                    url : "${path}/sys/admin/mail/add",
                    type : "post",
                    data : $("#addForm").serialize(),
                    headers: {
                        "OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    success : function(data){
                        if (data.success) {
                            showSaveSuccess();
                            setTimeout("window.location.href='${path}/sys/admin/mail/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';",1300);
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>
