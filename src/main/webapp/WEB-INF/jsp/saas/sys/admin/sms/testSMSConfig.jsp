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
    <title>测试短信配置</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

            <div class="portlet box grey">

                <div class="portlet-title">

                    <div class="caption">

                        <span class="hidden-480">短信验证码配置</span>

                    </div>
                    <div class="tools">
                        <a href="javascript:location.reload();" class="reload"></a>
                    </div>
                </div>

                <div class="portlet-body form">
                    <form action="" class="form-horizontal" id="addForm" method="post">
                        <input type="hidden" id="id" name="id" value="${id}"/>
                        <input type="hidden" id="privilegeCode" name="privilegeCode" value="${privilegeCode}"/>
                        <div id="code1Group" class="control-group">

                            <label class="control-label">短信内容<span class="required">*</span></label>

                            <div class="controls">


                                您的验证码是<input id="code1" style="width: 55px;" class="validcode" name="code1" type="text" placeholder="请填写验证码" disabled="" value="${privilegeCode}">
                                <!-- <span class="help-inline">Some hint here</span> -->

                            </div>

                        </div>
                        <div class="control-group" id="telGroup">

                            <label class="control-label mylabel">手机号码<span
                                    class="required">*</span>
                            </label>

                            <div class="controls inputtext">

                                <input type="text" class="span6 m-wrap myinput" name="tel" id="tel"
                                       class="required" placeholder="输入手机号码" mobilePhone="true" required/> <span
                                    class="help-inline"></span>

                            </div>

                        </div>
                        <div class="form-actions">
                            <a id="submit" class="btn green" ><i class="icon-ok"></i> 发送短信验证码</a>
                            <a href="${path}/sys/admin/sms/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
            var bool = addForm.form()
            var aa = $("#addForm").serialize();
            if (bool) {
                $.ajax({
                    url : "<%=path%>/sys/admin/sms/test",
                    type : "post",
                    data : $("#addForm").serialize(),
                    headers: {
                        "OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
                    },
                    success : function(data){
                        if (data.success) {
                            //showSaveSuccess();
                            showTip("提示", "短信已发送成功！");
                            setTimeout("window.location.href='${path}/sys/admin/sms/config?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}';",1300);
                        }else{
                            showTip("提示", "短信发送失败！");
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>
