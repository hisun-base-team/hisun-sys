<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%--
  ~ Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
  ~ http://www.hn-hisun.com
  ~ 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
  --%>
<form action="" class="form-horizontal" id="addForm" method="post">
    <div id="roleIdsGroup" class="control-group">
        <label class="control-label">选择角色<span class="required">*</span></label>
        <SelectTag:SelectTag id="roleIds" width="150px" moreSelectAll="false"
                             token="${sessionScope.OWASP_CSRFTOKEN}"
                             radioOrCheckbox="checkbox" moreSelectSearch="no"
                             selectUrl="${path}/sys/tenant/user/${userId}/roles"
                             />
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

