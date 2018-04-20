<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.page-content{   padding: 0 !important; }
ul.ztree{margin-bottom: 10px; background: #f1f3f6 !important;}
.portlet.box.grey.mainleft{background-color: #f1f3f6;overflow: hidden; padding: 0px !important; margin-bottom: 0px;}
.main_left{float:left; width:220px;  margin-right:10px; background-color: #f1f3f6; }
.main_right{display: table-cell; width:2000px; padding:20px 20px; }
.portlet-title .caption.mainlefttop{ border: none !important; background-color:#eaedf1;width: 220px; height: 48px;line-height: 48px;padding: 0;margin: 0;text-indent: 1em; }
.portlet.box .portlet-body.leftbody{padding: 15px 8px;}
</style>
<title>资源管理</title>
</head>
<body>
	
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="main_left">
						<div class="portlet box grey mainleft">
							<div class="portlet-title">
								<div class="caption mainlefttop">资源树</div>
							</div>
							<div class="portlet-body leftbody">
								<div class="zTreeDemoBackground" id="tree">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</div>
						</div>
					</div>
					<div class="main_right" id="listResource" >
					</div>
				</div>
				
				<%-- END PAGE CONTENT--%>
				<div class="modal-scrollable" style="z-index: 10050;display: none;" id="add">
					
					<div id="responsive" class="modal hide fade in" tabindex="-1"
						style="display: block; width: 760px;top: 25%;">

						<div class="span12">

						<!-- BEGIN SAMPLE FORM PORTLET-->   

							<div class="modal-header">
								<button class="close" id="close" type="button"></button>
								<h3 id="modalTitle">编辑资源</h3>
							</div>

							<div class="modal-body form">

								<!-- BEGIN FORM-->

								<form action="${path }/sys/resource/add" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="pId" name="pId" value=""/>
									<input type="hidden" id="id" name="id" value=""/>
									<div id="resourceNameGroup" class="control-group">

										<label class="control-label">资源名<span class="required">*</span></label>

										<div class="controls">

											<input type="text" id="resourceName" name="resourceName" class="span6 m-wrap" required maxlength="255">

											<!-- <span class="help-inline">Some hint here</span> -->

										</div>

									</div>
									<div class="control-group" id="pNameGroup">

										<label class="control-label">上级资源<span class="required">*</span></label>

										<div class="controls">
											<input type="hidden" id="newpId" name="newpId" value=""/>
											<input type="text" id="pName" name="pName" readonly="readonly" required="true" class="m-wrap span6" style="cursor: pointer;" onclick="$('#treeSelDiv').toggle();">
											<div tabindex="0" style=" width:268px;position: absolute;top: 103px;left: 195px;z-index: 110050;display: none;float: left;list-style: none;text-shadow: none;padding: 0px;margin: 0px;" id="treeSelDiv">
												<ul id="ztree1" class="ztree" style="height:400px; overflow-x: auto; margin: 0px;padding: 0px; border: solid 1px #ddd;border-top:none;"></ul>
											</div>

										</div>

									</div>
									<div class="control-group" id="resourceTypeGroup">

										<label class="control-label">资源类型<span class="required">*</span></label>

										<div class="controls">
											
											<select class="span6 m-wrap" id="resourceType" name="resourceType" data-placeholder="Choose a Category" tabindex="1" required>

												<option value="">请选择...</option>

												<option value="0">菜单</option>

												<option value="1">操作</option>

												<option value="2">系统</option>
											</select>

										</div>

									</div>
									<div class="control-group" id="statusGroup">

										<label class="control-label">是否可用<span class="required">*</span></label>

										<div class="controls">
											
											<select class="span6 m-wrap" id="status" name="status" data-placeholder="Choose a Category" tabindex="1" required>
												<option value="">请选择...</option>
												<option value="0">是</option>
												<option value="1">否</option>
											</select>

										</div>

									</div>
									<%--<div class="control-group" id="sysResourceGroup">--%>

										<%--<label class="control-label">是否属基础资源<span class="required">*</span></label>--%>

										<%--<div class="controls">--%>
											<%----%>
											<%--<select class="span6 m-wrap" id="sysResource" name="sysResource" data-placeholder="Choose a Category" tabindex="1" required>--%>
												<%--<option value="">请选择...</option>--%>
												<%--<option value="0">否</option>--%>
												<%--<option value="1">是</option>--%>
											<%--</select>--%>

										<%--</div>--%>

									<%--</div>--%>
									<div id="urlGroup" class="control-group">

										<label class="control-label">资源路径<span class="required">*</span></label>

										<div class="controls">

											<input class="span6 m-wrap" type="text" id="url" name="url" required maxlength="255">

										</div>

									</div>
									
									<div id="permissionGroup" class="control-group">

										<label class="control-label">权限字符串<span class="required">*</span></label>

										<div class="controls">

											<input class="span6 m-wrap" type="text" id="permission" name="permission" maxlength="255" placeholder="例如user:view,用:来分割" required maxlength="255">
											<!-- <span class="help-inline">除目录外,其他资源都需填写</span> -->

										</div>

									</div>

									<div id="sortGroup" class="control-group">

										<label class="control-label">资源排序<span class="required">*</span></label>

										<div class="controls">

											<input type="text" class="span6 m-wrap popovers" id="sort" name="sort" required max="9999">

										</div>

									</div>
									
									<div id="descriptionGroup" class="control-group">

										<label class="control-label">资源描述<span class="required">*</span></label>

										<div class="controls">

											<input class="span6 m-wrap" type="text" id="description" name="description" required maxlength="255">

										</div>

									</div>
									<div id="resourceIconGroup" class="control-group">

										<label class="control-label">资源图标</label>

										<div class="controls">

											<input class="span6 m-wrap" type="text" id="resourceIcon" name="resourceIcon" maxlength="255">

										</div>

									</div>
									<div class="control-group mybutton-group">
										<button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i> 关闭</button>      
										<button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i> 确定</button>                   
									</div>
									<!-- <div class="form-actions">
										<button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i>关闭</button>      
										<button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i>保存</button>                   

									</div> -->

								</form>

								<!-- END FORM-->       

							</div>

						<!-- END SAMPLE FORM PORTLET-->

					</div>

					</div>
				</div>
				<div class="modal-backdrop fade in" style="z-index: 10049;display: none;" id="modal"></div>
			</div>
		</div>
		

	</div>
	<script type="text/javascript" src="<%=path%>/js/zTree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="<%=path%>/js/common/est-validate-init.js"></script>
	<script type="text/javascript">
		var updateResource = function(id){
			$.ajax({
				url : "${path}/sys/admin/resource/get/"+id,
				type : "get",
				dataType : "json",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(json){
					if(json.success){
						var node = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam('id', json.data.pId);// 获取id为-1的点
						if(node)
						$("#pName").val(node.name);
						$("#newpId").val(json.data.pId);
						$("#pId").val(json.data.pId);
						$("#id").val(json.data.id);
						$("#resourceName").val(json.data.resourceName);
						$("#resourceType").val(json.data.resourceType);
						$("#permission").val(json.data.permission);
						$("#status").val(json.data.status);
//						if(json.data.sysResource){
//							$("#sysResource").val(1);
//						}else{
//							$("#sysResource").val(0);
//						}
						$("#url").val(json.data.url);
						$("#sort").val(json.data.sort);
						$("#description").val(json.data.description);
						$("#resourceIcon").val(json.data.resourceIcon);
						$("#add").show();
						$("#modal").show();
						zTree1.selectNode(node);
					}
				}
			});
		};
		var setting = {
			view: {
				selectedMulti: false
			},
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				onClick :onClick
			}
		};

		var setting1 = {
				async: {
					enable: true,
					url:"${path}/sys/admin/resource/select/tree",
					dataType : "json",
					headers: {
						"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
					},
					onAsyncError : function(){
						alert("请求失败");
					}
				},
				view: {
					dblClickExpand: false
				},
				edit: {
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick : onClick1
				}
			};
		function onClick1(event, treeId, treeNode){
			dirSel(treeId,treeNode);
		}
		
		function dirSel(treeId,treeNode){
			var text = treeNode.name;
			$('#newpId').val(treeNode.id);
			$('#pName').val(text);
			//$('#serverDirSelArea').attr("class","btn-group m-wrap span12");
			//getSla(treeNode.id);
			localPost("${path}/sys/admin/resource/max/sort/",{"pId":treeNode.id},function(data){
				$("#sort").val(data.maxSort);
			},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
			$('#treeSelDiv').toggle();
		}
		
		function beforeDrag(treeId, treeNodes) {
			return false;
		}

		function onClick (event, treeId, treeNode){
			//document.getElementById('datas').src ="${path}/sys/resource/list?pId="+treeNode.id;
			$("#pId").val(treeNode.id);
			$.ajax({
				url : "${path}/sys/admin/resource/sitemesh/list?pId="+treeNode.id,
				type : "get",
				data : null,
				dataType : "html",
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				success : function(html){
					$("#listResource").html(html);
				},
				error : function(){
					
				}
			});
		}
		function treeLoad(){
			$.ajax({
				async : false,
				cache:false,
				type: 'POST',
				dataType : "json",
				url: "${path}/sys/admin/resource/select/tree",// 请求的action路径
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				error: function () {// 请求失败处理函数
					alert('请求失败');
				},
				success:function(data){
					if(data.success){
						$.fn.zTree.init($("#ztree1"), setting1, data.data);
						zTree1 = $.fn.zTree.getZTreeObj("ztree1");
					}else{
						 alert('请求失败');  
					}
				}
			});
		}
		var zTree1;
		var addForm = new EstValidate("addForm");
		$(document).ready(function(){
			//初始化菜单
			App.init();//必须，不然导航栏及其菜单无法折叠
			
			treeLoad();
			//$("#modal").hide();
			//$("#add").hide();
			$("#submit").on("click",function(event){
				event.stopPropagation();//阻止冒泡
				localPost("${path}/sys/admin/resource/permission/check",{
					"permission":$("#permission").val(),
					"id":$("#id").val()
				},function(data) {
					if(!data.success){
						showTip("提示", "权限字符串必须唯一！");
					}else{
						var bool = addForm.form();
						if(bool){
							if($("#id").val()===""){
								$.ajax({
									url : "${path}/sys/admin/resource/add",
									type : "post",
									data : $("#addForm").serialize(),
									dataType : "json",
									headers: {
										"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
									},
									success : function(json){
										if(json.success){
											document.getElementById("addForm").reset();
											$("#id").val("");
											$(".control-group").removeClass("error").removeClass("success");
											$(".help-inline").remove();
											$("#modal").hide();
											$("#add").hide();
											refreshTree();
											//$("#listResource").load("${path}/sys/admin/resource/sitemesh/list?pId="+$("#pId").val());
											$.ajax({
												url : "${path}/sys/admin/resource/sitemesh/list?pId="+$("#pId").val(),
												type : "get",
												data : null,
												dataType : "html",
												headers: {
													"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
												},
												success : function(html){
													$("#listResource").html(html);
												},
												error : function(){

												}
											});
											$("#alertSuccess").show();
											setTimeout(function () {
										        $("#alertSuccess").hide();
										    }, 5000);
											
										}else{
											document.getElementById("addForm").reset();
											$("#id").val("");
											$(".control-group").removeClass("error").removeClass("success");
											$(".help-inline").remove();
											$("#modal").hide();
											$("#add").hide();
											$("#alertErr").show();
											setTimeout(function () {
										        $("#alertErr").hide();
										    }, 5000);
										}
									},
									error : function(){
										document.getElementById("addForm").reset();
										$("#id").val("");
										$(".control-group").removeClass("error").removeClass("success");
										$(".help-inline").remove();
										$("#modal").hide();
										$("#add").hide();
										$("#alertErr").show();
										setTimeout(function () {
									        $("#alertErr").hide();
									    }, 5000);
									}
								});
							}else{
								$.ajax({
									url : "${path}/sys/admin/resource/update",
									type : "post",
									data : $("#addForm").serialize(),
									dataType : "json",
									headers: {
										"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
									},
									success : function(json){
										if(json.success){
											document.getElementById("addForm").reset();
											$("#id").val("");
											$(".control-group").removeClass("error").removeClass("success");
											$(".help-inline").remove();
											$("#modal").hide();
											$("#add").hide();
											refreshTree();
											//$("#listResource").load("${path}/sys/admin/resource/sitemesh/list?pId="+$("#pId").val());
											$.ajax({
												url : "${path}/sys/admin/resource/sitemesh/list?pId="+$("#pId").val(),
												type : "get",
												data : null,
												dataType : "html",
												headers: {
													"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
												},
												success : function(html){
													$("#listResource").html(html);
												},
												error : function(){

												}
											});
											$("#alertSuccess").show();
											setTimeout(function () {
										        $("#alertSuccess").hide();
										    }, 5000);
											
										}else{
											document.getElementById("addForm").reset();
											$("#id").val("");
											$(".control-group").removeClass("error").removeClass("success");
											$(".help-inline").remove();
											$("#modal").hide();
											$("#add").hide();
											$("#alertErr").show();
											setTimeout(function () {
										        $("#alertErr").hide();
										    }, 5000);
										}
									},
									error : function(){
										document.getElementById("addForm").reset();
										$("#id").val("");
										$(".control-group").removeClass("error").removeClass("success");
										$(".help-inline").remove();
										$("#modal").hide();
										$("#add").hide();
										$("#alertErr").show();
										setTimeout(function () {
									        $("#alertErr").hide();
									    }, 5000);
									}
								});
								
							}
						}
					}
				},"json", {"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
				
					
			});
			$("#cancel").on("click",function(event){
				event.stopPropagation();//阻止冒泡
				document.getElementById("addForm").reset();
				$("#id").val("");
				$(".control-group").removeClass("error").removeClass("success");
				$(".help-inline").remove();
				$("#modal").hide();
				$("#add").hide();
				$('#treeSelDiv').hide();
			});
			var handlePortletTools = function () {
				
			}
			$("#close").on("click",function(event){
				event.stopPropagation();//阻止冒泡
				document.getElementById("addForm").reset();
				$("#id").val("");
				$(".control-group").removeClass("error").removeClass("success");
				$(".help-inline").remove();
				$("#modal").hide();
				$("#add").hide();
				$('#treeSelDiv').hide();
			});
			$("#permission").on("blur",function(){
				localPost("${path}/sys/admin/resource/permission/check",{
					"permission":this.value,
					"id":$('#id').val()
				},function(data) {
					if(!data.success){
						showTip("提示", "权限字符串必须唯一！");
					}
				},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
			});
			$.ajax({
				async : false,
				cache:false,
				type: 'POST',
				dataType : "json",
				url: "${path}/sys/admin/resource/tree",// 请求的action路径
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				error: function () {// 请求失败处理函数
					alert('请求失败');
				},
				success:function(data){
					if(data.success){
						$.fn.zTree.init($("#treeDemo"), setting, data.data);
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						var node = zTree.getNodeByParam('id', data.data[0].id);// 获取id为-1的点
						$.ajax({
							async : false,
							cache:false,
							type: 'GET',
							dataType : "html",
							url: "${path}/sys/admin/resource/sitemesh/list?pId=1",// 请求的action路径
							headers: {
								"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
							},
							error: function () {// 请求失败处理函数
								alert('请求失败');
							},
							success:function(html){
								$("#listResource").html(html);
							}
						});
						zTree.selectNode(node);
						$("#pId").val(node.id);
						selectId=node.id;
						zTree.expandNode(node, true, false , true);
					}else{
						 alert('请求失败');  
					}
				}
			});
		});
		
		function iFrameHeight() {   
			var ifm= document.getElementById("datas");   
			var subWeb = document.frames ? document.frames["datas"].document : ifm.contentDocument;   
			if(ifm != null && subWeb != null) {
			   ifm.height = subWeb.body.scrollHeight;
			   ifm.width = subWeb.body.scrollWidth;
			}   
		}  
		
		/** * 刷新树 */
		function refreshTree() {
			treeLoad();
			$("#treeDemo").empty();
			$.ajax({  
		        async : false,  
		        cache:false,  
		        type: 'POST',  
		        // data:{"userid":Rand},
		        dataType : "json",  
		        url: "${path}/sys/admin/resource/tree",// 请求的action路径
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
		        error: function () {// 请求失败处理函数
		            alert('请求失败');  
		        },  
		        success:function(data){
		        	if(data.success){
						$.fn.zTree.init($("#treeDemo"), setting, data.data);
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						var id = $("#pId").val();
						var node = zTree.getNodeByParam('id',id==null?data.data[0].id:id);// 获取id为-1的点
						zTree.selectNode(node);
						zTree.expandNode(node, true, false , true);
						//zTree.expandAll(true);
		        	}else{
		        		 alert('请求失败');  
		        	}
		        }  
		    });
		}
		function pagehref (pageNum ,pageSize){
			localPost("<%=path%>/sys/admin/resource/sitemesh/list?pId="+$('#pId').val()+"&pageNum="+pageNum+"&pageSize="+pageSize,null,function(data){
				$("#listResource").html(data);
			},"html",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
		}
	</script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
	
</body>
</html>