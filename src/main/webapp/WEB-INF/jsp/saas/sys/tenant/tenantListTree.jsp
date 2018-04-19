<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../inc/servlet.jsp" %>
<%@include file="../../../inc/taglib.jsp" %>
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
<title>租户管理</title>
</head>
<body>
	
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="main_left">
						<div class="portlet box grey mainleft">
							<div class="portlet-title">
								<div class="caption mainlefttop">租户树</div>
							</div>
							<div class="portlet-body leftbody">
								<div class="zTreeDemoBackground" id="tree">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</div>
						</div>
					</div>
					<div class="main_right" id="tenantList" >
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
								<h3 id="modalTitle">编辑租户</h3>
							</div>

							<div class="modal-body form">

								<!-- BEGIN FORM-->

								<form action="${path }/sys/tenant/save" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="pId" name="pId" value=""/>
									<input type="hidden" id="id" name="id" value=""/>
									<div id="tenantNameGroup" class="control-group">

										<label class="control-label">组织名<span class="required">*</span></label>

										<div class="controls">

											<input type="text" id="tenantName" name="tenantName" class="span6 m-wrap" required maxlength="255">

											<!-- <span class="help-inline">Some hint here</span> -->

										</div>

									</div>
									<!-- <div class="control-group" id="pNameGroup">

										<label class="control-label">上级资源<span class="required">*</span></label>

										<div class="controls">
											<input type="hidden" id="newpId" name="newpId" value=""/>
											<input type="text" id="pName" name="pName" readonly="readonly" required="true" class="m-wrap span6" style="cursor: pointer;" onclick="$('#treeSelDiv').toggle();">
											<div tabindex="0" style=" width:268px;position: absolute;top: 103px;left: 195px;z-index: 110050;display: none;float: left;list-style: none;text-shadow: none;padding: 0px;margin: 0px;" id="treeSelDiv">
												<ul id="ztree1" class="ztree" style="height:400px; overflow-x: auto; margin: 0px;padding: 0px; border: solid 1px #ddd;border-top:none;"></ul>
											</div>

										</div>

									</div> -->
									<c:if test="${username eq 'admin'}">
										<div class="control-group" id="propertyGroup">
	
											<label class="control-label">租户类型<span class="required">*</span></label>
	
											<div class="controls">
												
												<select class="span6 m-wrap" id="property" name="property" data-placeholder="Choose a Category" tabindex="1" required>
	
													<option value="">请选择...</option>
														<option value="0">运营商</option>
														<option value="1">服务提供商</option>
														<option value="2">客户</option>
												</select>
	
											</div>
	
										</div>
									</c:if>
									<c:if test="${username ne 'admin'}">
										<input id="property" name="property" value="2" type="hidden"/>
									</c:if>
									<div id="sortGroup" class="control-group">

										<label class="control-label">租户排序<span class="required">*</span></label>

										<div class="controls">

											<input type="text" class="span6 m-wrap popovers" id="sort" name="sort" required max="9999">

										</div>

									</div>
									
									<div id="initialsGroup" class="control-group">

										<label class="control-label">租户缩写</label>

										<div class="controls">

											<input class="span6 m-wrap" type="text" id="initials" name="initials" required maxlength="255">

										</div>

									</div>
									<div class="control-group" id="passwordGroup">
										<label class="control-label">密码</label>
										<div class="controls">
											<input type="password" id="password" name="password" value="" class="span6 m-wrap" required/>
											<br>
											系统自动生成的该管理员账号为租户缩写，默认密码和账号一样，请记住！
											<br>
											这里可以修改默认密码!
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
	<script type="text/javascript" src="<%=path%>/js/zTree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="<%=path%>/js/common/est-validate-init.js"></script>
	<script type="text/javascript">
	var add = $("#add");
	var modal = $("#modal");
	var addTenant = function(){
		$("#passwordGroup").show();
		/* $.get("${path}/sys/resource/max/sort/",{"pId":$("#pId").val()},function(data){
			$("#sort").val(data.maxSort); */
			modal.show();
			add.show();
		//});
	};

		var updateTenant = function(id){
			$("#passwordGroup").hide();
			$.ajax({
				url : "${path}/sys/tenant/load/?id="+id,
				type : "get",
				dataType : "json",
				success : function(json){
					if(json.success){
						//var node = zTree1.getNodeByParam('id', json.data.pId);// 获取id为-1的点
						//$("#pName").val(node.name);
						//$("#newpId").val(json.data.pId);
						$("#pId").val(json.data.pId);
						$("#id").val(json.data.id);
						$("#tenantName").val(json.data.tenantName);
						$("#property").val(json.data.property);
						if(json.data.status){
							$("#status").val("1");
						}else{
							$("#status").val("0");
						}
						$("#sort").val(json.data.sort);
						$("#initials").val(json.data.initials);
						$("#add").show();
						$("#modal").show();
						//zTree1.selectNode(node);
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

		function beforeDrag(treeId, treeNodes) {
			return false;
		}

		function onClick (event, treeId, treeNode){
			//document.getElementById('datas').src ="${path}/sys/resource/list?pId="+treeNode.id;
			$("#pId").val(treeNode.id);
			$.ajax({
				url : "${path}/sys/tenant/ajax/list?pId="+treeNode.id,
				type : "get",
				data : null,
				dataType : "html",
				success : function(html){
					$("#tenantList").html(html);
				},
				error : function(){
					
				}
			});
		}
		var zTree1;
		var addForm = new EstValidate("addForm");
		$(document).ready(function(){
			//初始化菜单
			App.init();//必须，不然导航栏及其菜单无法折叠
			
			//$("#modal").hide();
			//$("#add").hide();
			$("#submit").on("click",function(event){
				//event.stopPropagation();//阻止冒泡
				var bool = addForm.form();
				if(bool){
					if($("#id").val()===""){
						$.ajax({
							url : "${path}/sys/tenant/save",
							type : "post",
							data : $("#addForm").serialize(),
							dataType : "json",
							success : function(json){
								if(json.success){
									document.getElementById("addForm").reset();
									$("#id").val("");
									$(".control-group").removeClass("error").removeClass("success");
									$(".help-inline").remove();
									$("#modal").hide();
									$("#add").hide();
									refreshTree();
									$("#tenantList").load("${path}/sys/tenant/ajax/list?pId="+json.tenantId);
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
							url : "${path}/sys/tenant/update/"+$("#id").val(),
							type : "post",
							data : $("#addForm").serialize(),
							dataType : "json",
							success : function(json){
								if(json.success){
									document.getElementById("addForm").reset();
									$("#id").val("");
									$(".control-group").removeClass("error").removeClass("success");
									$(".help-inline").remove();
									$("#modal").hide();
									$("#add").hide();
									refreshTree();
									$("#tenantList").load("${path}/sys/tenant/ajax/list?pId="+$("#pId").val());
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
					
			});
			$("#cancel").on("click",function(event){
				//event.stopPropagation();//阻止冒泡
				document.getElementById("addForm").reset();
				$("#id").val("");
				$(".control-group").removeClass("error").removeClass("success");
				$(".help-inline").remove();
				$("#modal").hide();
				$("#add").hide();
				$('#treeSelDiv').hide();
			});
			
			$("#tenantName").on("blur",function(evet){
				//event.stopPropagation();//阻止冒泡
				var initials = this.value;
				$.ajax({
					async : false,
					cache:false,
					type: 'POST',
					dataType : "json",
					data:{"initials":initials},
					url: "${path}/sys/tenant/initials",// 请求的action路径
					error: function () {// 请求失败处理函数
						alert('转化租户拼音失败');
					},
					success:function(data){
						if(data.success){
							$("#initials").val(data.initials);
							$("#password").val(data.initials);
						}else{
							alert('转化租户拼音失败');  
						}
					}
				});
			});
			var handlePortletTools = function () {
				
			}
			$("#close").on("click",function(event){
				//event.stopPropagation();//阻止冒泡
				document.getElementById("addForm").reset();
				$("#id").val("");
				$(".control-group").removeClass("error").removeClass("success");
				$(".help-inline").remove();
				$("#modal").hide();
				$("#add").hide();
				$('#treeSelDiv').hide();
			});
			//$("#tenantList").load("${path}/sys/tenant/ajax/list?pId=1");
			$.ajax({
				async : false,
				cache:false,
				type: 'POST',
				dataType : "json",
				url: "${path}/sys/tenant/tree?tenantId=${param.tenantId}",// 请求的action路径
				error: function () {// 请求失败处理函数
					alert('请求失败');
				},
				success:function(data){
					if(data.success){
						$.fn.zTree.init($("#treeDemo"), setting, data.data);
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						// zTree.expandAll(true);
						//var node = zTree.getNodeByParam('id', data.data[0].id);// 获取id为-1的点
						//document.getElementById('datas').src = "${path}/sys/resource/list?pId=1";
						var node;
						if(data.tenantId){
							node = zTree.getNodeByParam('id', data.tenantId);
						}else{
							node = zTree.getNodeByParam('id', data.data[0].id);// 获取id为-1的点
						}
						$("#tenantList").load("${path}/sys/tenant/ajax/list?pId="+node.id);
						zTree.selectNode(node);
						$("#pId").val(node.id);
						selectId=node.id;
						zTree.expandNode(node, true, false , true);
					}else{
						 alert('请求失败');  
					}
				}
			});
			/* }else{
				$("#tenantList").load("${path}/sys/tenant/ajax/list");
			} */
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
				$("#treeDemo").empty();
				
				$.ajax({  
			        async : false,  
			        cache:false,  
			        type: 'POST',  
			        // data:{"userid":Rand},
			        dataType : "json",  
			        url: "${path}/sys/tenant/tree",// 请求的action路径
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
			$.get("<%=path%>/sys/tenant/ajax/list?pId="+$('#pId').val()+"&pageNum="+pageNum+"&pageSize="+pageSize,function(data){
				$("#tenantList").html(data);
			},"html");
		}
	</script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
	
</body>
</html>