<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授权角色</title>
<link href="${path}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

				<div class="portlet box grey">

					<div class="portlet-title">

						<div class="caption">

							<span class="hidden-480">角色授权</span>

						</div>
						<div class="tools">
							<a href="javascript:location.reload();" class="reload"></a>
						</div>
					</div>

					<div class="portlet-body form">
						<form action="${path}/sys/admin/role/setAuth" class="form-horizontal myform"
						id="submit_form" method="post">
							<div class="tab-pane active" id="tab1">
								<div>
	
									<div class="control-group">
	
										<label class="control-label mylabel">角色名:
										</label>
	
										<div class="controls">
											<input type="hidden" id="id" name="id" value='<c:out value="${vo.id }"></c:out>'/>
											<div style="margin-top: 6px;"><c:out value="${vo.roleName }"></c:out></div>	
										</div>
	
									</div>
									<div class="control-group">
		
											<label class="control-label mylabel">资源:
											</label>
		
											<div class="controls">
												<input type="hidden" id="resIds" name="resIds" class="myinput"/>
												<div class="treeDemoDiv">
													<div id="treeDemo" class="ztree"></div>
												</div>
											</div>
		
										</div>
									<div  class="form-actions">
										<button id="submitBtn" type="button" class="btn green mybutton" onclick="authSubmit()"><i class='icon-ok'></i> 授权</button>
										<a href="${path}/sys/admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
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
	<script type="text/javascript" src="${path}/js/zTree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript">
	var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			check: {
				enable: true,
				nocheckInherit: true
				
			}
		};
	var resTree;
		$(function(){
			localPost("${path}/sys/admin/resource/select/tree",{"status":1}, function(data,status){
				if (status == "success") {
					//setting.data.key.url = "_url" ;
					resTree = $.fn.zTree.init($("#treeDemo"), setting, data.data);

					localPost("${path}/sys/admin/role/resource/${vo.id}",null, function(data,status){
						if (status == "success") {
							var resIdList = data.data;
							if (resTree != null && resIdList != null) {
								if (resIdList.length > 0) {//初始化选中状态时，是不联动的，而根节点的选中没保存进数据库，所以要手动判断是否有节点选中然后补上根节点
									var node = resTree.getNodeByParam("id", "1", null);
									resTree.checkNode(node, true, false);
								}
								$.each(resIdList, function(index, resId) {
									var node = resTree.getNodeByParam("id", resId, null);
									if(node){
										resTree.checkNode(node, true, false);
									}
								});						
							}
						}
					},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
				}
			},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		});

		function authSubmit(){
			if (resTree != null) {//获取资源树选中的节点
				var nodes = resTree.getCheckedNodes(true);
				var resIdsObj = $('#resIds');
				$.each(nodes, function(index, node) {
					resIdsObj.val(resIdsObj.val() + node.id + ";");
				});
				$('#submit_form').ajaxSubmit({
					beforeSend : function() {
						$("#submitBtn").html("授权中...");
						$("#submitBtn").attr("disabled", "disabled");
					},
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					success : function(data) {
						if (data.success) {
							$("#submitBtn").html("授权成功");
							showTip("提示", "授权成功！",3000);

							setTimeout("$('#myModal2').modal('hide');",1000);
							$("#submitBtn").html("授权");
							$("#submitBtn").removeAttr("disabled");
						}

					}
				});
			}
		}
	</script>
</body>
</html>