<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../inc/servlet.jsp" %>
<%@include file="../../../inc/taglib.jsp" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${path}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
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
<title>用户管理</title>
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
				<input type="hidden" id="pId" name="pId"/>
	</div>
	<script type="text/javascript" src="${path}/js/zTree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="${path}/js/common/est-validate-init.js"></script>
	<script type="text/javascript">
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
				url : "${path}/sys/user/ajax/list?pId="+treeNode.id,
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
		$(document).ready(function(){
			//初始化菜单
			App.init();//必须，不然导航栏及其菜单无法折叠
			
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
						//document.getElementById('datas').src = "${path}/sys/resource/list?pId=1";
						var node;
						if(data.tenantId){
							node = zTree.getNodeByParam('id', data.tenantId);
						}else{
							node = zTree.getNodeByParam('id', data.data[0].id);// 获取id为-1的点
						}
						$("#tenantList").load("${path}/sys/user/ajax/list?pId="+node.id);
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
		<%-- function pagehref (pageNum ,pageSize){
			$.get("${path}/sys/user/ajax/list/"+$('#pId').val()+"?pageNum="+pageNum+"&pageSize="+pageSize,function(data){
				$("#tenantList").html(data);
			},"html");
		} --%>
	</script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
	
</body>
</html>