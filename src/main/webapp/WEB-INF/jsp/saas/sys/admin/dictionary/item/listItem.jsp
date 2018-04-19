<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp"%>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典项列表</title>
<link href="<%=path%>/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
</head>
<body>
	<div class="container-fluid">
				
				
				<div class="row-fluid">

					<div class="span3">

						<div class="portlet box grey">

							<div class="portlet-title">

								<div class="caption">字典树</div>

							</div>

							<div class="portlet-body" style="clear: both; min-height: 500px;">

								<div class="zTreeDemoBackground" id="tree">
									<ul id="treeDemo" class="ztree"></ul>
								</div>

							</div>

						</div>

					</div>

					<div class="span9" id="listResource" >
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
								<h3 id="modalTitle">编辑字典项</h3>
							</div>

							<div class="modal-body form">

								<!-- BEGIN FORM-->

								<form action="${path }/sys/admin/dictionary/item/add" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="pId" name="pId" value=""/>
									<input type="hidden" id="id" name="id" value=""/>
									<div id="itemGroup" class="control-group">

										<label class="control-label">字典项名<span class="required">*</span></label>

										<div class="controls">

											<input type="text" id="item" name="item" class="span6 m-wrap" required maxlength="255">

											<!-- <span class="help-inline">Some hint here</span> -->

										</div>

									</div>
									<div id="valueGroup" class="control-group">

										<label class="control-label">字典项值<span class="required">*</span></label>

										<div class="controls">

											<input type="text" id="value" name="value" class="span6 m-wrap" required maxlength="255">

											<!-- <span class="help-inline">Some hint here</span> -->

										</div>

									</div>
									<div class="control-group" id="pNameGroup">

										<label class="control-label">上级字典项<span class="required">*</span></label>

										<div class="controls">
											<input type="hidden" id="newpId" name="newpId" value=""/>
											<input type="text" id="pName" name="pName" readonly="readonly" required="true" class="m-wrap span6" style="cursor: pointer;" onclick="$('#treeSelDiv').toggle();">
											<div tabindex="0" style=" width:268px;position: absolute;left: 195px;z-index: 110050;display: none;float: left;list-style: none;text-shadow: none;padding: 0px;margin: 0px;" id="treeSelDiv">
												<ul id="ztree1" class="ztree" style="height:400px; overflow-x: auto; margin: 0px;padding: 0px; border: solid 1px #ddd;border-top:none;"></ul>
											</div>

										</div>

									</div>
									<input id="type" name="type" type="hidden" />
									<!-- <div class="control-group">

										<label class="control-label">字典类型<span class="required">*</span></label>

										<div class="controls">
											
											<select class="span6 m-wrap" id="type" name="type" data-placeholder="Choose a Category" tabindex="1">

											</select>

										</div>

									</div> -->
									
									<div id="sortGroup" class="control-group">

										<label class="control-label">字典项排序<span class="required">*</span></label>

										<div class="controls">

											<input type="text" class="span6 m-wrap popovers" id="sort" name="sort" required max="9999">

										</div>

									</div>
									
									<div id="remarkGroup" class="control-group">

										<label class="control-label">字典描述</label>

										<div class="controls">

											<input class="span6 m-wrap" type="text" id="remark" name="remark" required maxlength="255">

										</div>

									</div>
									
									<div class="control-group mybutton-group">
										<button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i>关闭</button>      
										<button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i>保存</button>                   
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
		<!--
		var updateResource = function(id){
			$.ajax({
				url : "${path}/sys/dictionary/item/get/"+id,
				type : "get",
				dataType : "json",
				success : function(json){
					if(json.success){
						var node = zTree1.getNodeByParam('id', json.data.pId);// 获取id为-1的点
						$("#pName").val(node.name);
						$("#newpId").val(json.data.pId);
						$("#pId").val(json.data.pId);
						$("#id").val(json.data.id);
						$("#item").val(json.data.name);
						$("#value").val(json.data.value);
						$("#type").val(json.data.typeId);
						$("#remark").val(json.data.remark);
						$("#sort").val(json.data.sort);
						$("#add").show();
						$("#modal").show();
						zTree1.selectNode(node);
					}
				}
			});
			
			$.get("<%=path%>/sys/admin/dictionary/item/select",{"id":id}, function(data,status){
				if (status == "success") {
					var selection = $('#type');
					selection.empty();
					//selection.append("<option value=''>请选择...</option>");
					$.each(data.data, function(index,element) {
						selection.append("<option class='selections' value='" + element.id + "' " + element.selected + " >" + element.name + "</option>");
					});
					
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
					url:"${path}/sys/admin/dictionary/item/tree/${typeId}",
					dataType : "json",
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
			//if(treeNode.isParent){
			//	zTree1.expandNode(treeNode,true,false,true,false);
			//}else{
				dirSel(treeId,treeNode);
			//}
		}
		
		function dirSel(treeId,treeNode){
			var text = treeNode.name;
			$('#newpId').val(treeNode.id);
			$('#pName').val(text);
			//$('#serverDirSelArea').attr("class","btn-group m-wrap span12");
			//getSla(treeNode.id);
			$.get("${path}/sys/admin/dictionary/item/max/sort/",{"pId":treeNode.id},function(data){
				$("#sort").val(data.maxSort);
			})
			$('#treeSelDiv').toggle();
		}
		
		function beforeDrag(treeId, treeNodes) {
			return false;
		}

		function onClick (event, treeId, treeNode){
			//document.getElementById('datas').src ="${path}/sys/resource/list?pId="+treeNode.id;
			$("#pId").val(treeNode.id);
			$.ajax({
				url : "${path}/sys/admin/dictionary/item/ajax/list?pId="+treeNode.id,
				type : "get",
				data : null,
				dataType : "html",
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
				url: "${path}/sys/admin/dictionary/item/tree/${typeId}",// 请求的action路径
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
				
			var bool = addForm.form();
			if(bool){
				if($("#id").val()===""){
					$.ajax({
						url : "${path}/sys/admin/dictionary/item/add",
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
								$("#listResource").load("${path}/sys/admin/dictionary/item/ajax/list?pId="+$("#pId").val());
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
						url : "${path}/sys/admin/dictionary/item/update",
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
								$("#listResource").load("${path}/sys/admin/dictionary/item/ajax/list?pId="+$("#pId").val());
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
			
			$.ajax({
				async : false,
				cache:false,
				type: 'POST',
				dataType : "json",
				url: "${path}/sys/admin/dictionary/item/tree/${typeId}",// 请求的action路径
				error: function () {// 请求失败处理函数
					alert('请求失败');
				},
				success:function(data){
					if(data.success){
						$.fn.zTree.init($("#treeDemo"), setting, data.data);
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						// zTree.expandAll(true);
						var node = zTree.getNodeByParam('id', data.data[0].id);// 获取id为-1的点
						//document.getElementById('datas').src = "${path}/sys/resource/list?pId=1";
						$("#listResource").load("${path}/sys/admin/dictionary/item/ajax/list?pId=1");
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
		        url: "${path}/sys/admin/dictionary/item/tree/${typeId}",// 请求的action路径
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
			$.get("<%=path%>/sys/admin/dictionary/item/ajax/list?pId="+$('#pId').val()+"&pageNum="+pageNum+"&pageSize="+pageSize,function(data){
				$("#listResource").html(data);
			},"html");
		}
		//-->
	</script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
</body>
</html>