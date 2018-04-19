<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>租户信息</title>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

			<div class="portlet box grey">

				<div class="portlet-title">

					<div class="caption">

						<span class="hidden-480">租户信息</span>
					</div>
				</div>

				<div class="portlet-body form">
					<div class="span12">
						<div class="tab-content">

							<div class="tab-pane profile-classic row-fluid active" id="tab_1_2">
								<ul class="unstyled span12" >

									<li style="cursor: default;">
										<span>租户名称:</span> <c:out value="${entity.name }"></c:out>
										<shiro:hasPermission name="tenant:tenantupdate">
											<a class="btn blue mini" href="javascript:openEdit();" style="margin-left: 20px;">修改租户名称</a>
										</shiro:hasPermission>
									</li>

									<li style="cursor: default;"><span>创建日期:</span> <fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></li>

									<li style="cursor: default;"><span>成员人数:</span> <a href="${path}/sys/tenant/user/sysAdmin/list?tenantId=${entity.id}&OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"><c:out value="${memberCount }"></c:out></a></li>

									<li style="cursor: default;"><span>租户状态:</span> ${entity.tombstone==0?'激活':'注销'}</li>
								</ul>

							</div>

						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="updateNameModal" class="modal container hide fade" tabindex="-1" data-width="800">
	<div class="modal-header">
		<button class="close" type="button" data-dismiss="modal"></button>
		<h3>修改租户名称</h3>
	</div>
	<div class="modal-body">
		<form action="" id="updateForm" class="form-horizontal myform">
			<input type="hidden" name="id" value="${entity.id}">
			<div id="nameGroup" class='control-group'>
				<label class='control-label' style="width: 160px;">租户名称：</label>
				<div class="controls" style="margin-left: 160px;">
					<input class="m-wrap" type="text" name="name" id="name" required maxlength="64" value="">
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer" style="height: 36px;">
		<button type="button" data-dismiss="modal" class="btn  "><i class="icon-remove icon-white"></i> 关 闭</button>
		<a type='button' class='btn green' href='javascript:void(0);' onclick='update();'><i class='icon-ok'></i>确定</a>
	</div>
</div>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="application/javascript">
	var myVld = new EstValidate("updateForm");
	var myLoading = new MyLoading("${path}",{zindex : 11111});
	function openEdit(){
		$('#name').val('<c:out value="${entity.name }"></c:out>');
		$('#updateNameModal').modal('show');
	}

	function update(){
		var bool = myVld.form();
		if(bool){
			myLoading.show();
			$.ajax({
				url : "${path }/sys/tenant/tenant/update",
				type : "post",
				data : $('#updateForm').serialize(),
				dataType : "json",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(json){
					myLoading.hide();
					if(json.code == 1){
						showTip("提示","操作成功",2000);
						setTimeout(function(){
							window.location.reload()
						},2000) ;
					}else{
						showTip("提示", json.message, 2000);
					}
				},
				error : function(arg1, arg2, arg3){
					myLoading.hide();
					showTip("提示","出错了请联系管理员",2000);
				}
			});
		}
	}
</script>
</body>
</html>