<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<title>角色授权</title>
</head>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

			<div class="portlet box grey">

				<div class="portlet-title">

					<div class="caption">

						<span class="hidden-480">角色授权</span>

					</div>
				</div>

				<div class="portlet-body form">
					<form action="#" class="form-horizontal myform"
							id="submit_form" method="post">
					<div class="tab-pane active" id="tab1">
						<div>

								<div class="control-group">

									<label class="control-label mylabel">角色名：
									</label>
									<div class="controls">
										<div style="margin-top: 6px;"><c:out value="${entity.roleName }"></c:out></div>
										</div>
								</div>
								<div class="control-group">
									<label class="control-label mylabel">资源：
									</label>
									<div class="controls">
										<input type="hidden" id="id" name="id" value='${entity.id }'/>
										<div class="treeDemoDiv">
											<div id="treeDemo" class="ztree"></div>
										</div>
									</div>

								</div>
								<div  class="form-actions">
									<button id="submitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i> 授权</button>
									<a href="${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
	</div>
</div>
<script type="text/javascript" src="<%=path%>/js/zTree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${path }/js/common/loading.js"></script>
<script type="text/javascript">
	var setting = {
		view : {
			selectedMulti : true
		},
		edit : {
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		check : {
			chkboType : {
				"Y" : "ps",
				"N" : "ps"
			},
			chkStyle : "checkbox",
			enable : true,
			chkDisabledInherit : true,
		}
	};
	var myLoading = new MyLoading("${path}",{zindex : 11111});
	var resTree;
	$(function(){
		$("#submitBtn").on("click",function(){
			if (resTree != null) {//获取资源树选中的节点
				var nodes = resTree.getCheckedNodes(true);
				var resourceIdArr = new Array();
				for(var i=0;i<nodes.length;i++){
					resourceIdArr.push(nodes[i].id);
				}
				var paramObj = new Object();
				paramObj.resourceIds = resourceIdArr;
				myLoading.show();
				$.ajax({
					url : "${path }/sys/tenant/role/setresource/save/${entity.id}?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}",
					type : "post",
					data : $.param(paramObj,true),
					dataType : "json",
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					success : function(json){
						myLoading.hide();
						if(json.privilegeCode==1){
							showTip("提示","授权成功",2000);
							setTimeout(function(){
								window.location.href = "${path}/sys/tenant/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}";
							},2000);
						}else{
							showTip("提示",json.message,2000);
						}
					},
					error : function(arg1, arg2, arg3){
						myLoading.hide();
						showTip("提示","出错了请联系管理员",2000);
					}
				});
			}
		});

		$.ajax({
			url : "${path }/sys/tenant/role/setresource/tree/${entity.id}",
			type : "get",
			data : null,
			dataType : "json",
			headers: {
				"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
			},
			success : function(json){
				resTree = $.fn.zTree.init($("#treeDemo"), setting, json);
				var root = resTree.getNodeByParam("id", "1", null);
				resTree.expandNode(root);
			},
			error : function(arg1, arg2, arg3){
				showTip("提示","出错了请联系管理员",2000);
			}
		});
	});
</script>
</body>
</html>