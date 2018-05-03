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
</head>
<body>
<div class="container-fluid">
		<div class="row-fluid">
			<div class="">
				<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>

				<div class="">

					<div class="">
						<form action="${path}/sys/admin/role/set/member" class="form-horizontal myform"
						id="submit_form" method="post">
							<div class="tab-pane active" id="tab1">
								<div>
	
	<div class="main_left" style="">
						<div class="portlet box grey mainleft">
							<div class="portlet-body leftbody">
								<div class="control-group">
	
											<input type="hidden" id="id" name="id" value='<c:out value="${vo.id }"></c:out>'/>
											<div style="margin-top: 6px;">角色名:<c:out value="${vo.roleName }"></c:out></div>	
	
									</div>

		
											<label class="">所属权限:
											</label>
		
											<div class="">
												<div class="treeDemoDiv">
													<div id="treeDemo" class="ztree"></div>
												</div>
											</div>

							</div>
						</div>
					</div>
					<div class="main_right" >
						<div class="portlet-title">

							<div class="caption">
	
								<span class="hidden-480">选择成员</span>
	
							</div>
						</div>
						<div class="control-group">
		
										<div class="controls" style="margin-left:100px;">
											<div class="search_Mobile_bot"  >
						                         <div class="search_Mobile_bot_left" id="selectLeft">
						                         	<p class="pa1">
														可选成员
													</p>
						                         	<select size="12" multiple="multiple" id="select1" class="sea_textarea">
														<c:forEach items="${allUserVos}" var="user" varStatus="status">
															<option value="${user.id }">${user.realname }</option>
														</c:forEach>
													</select>
													<p class="sea_rompt">点击时同时按Ctrl键可选择多个成员</p>    
						                         </div>
													<div class="search_Mobile_bot_middle">
													      <p><a class="btn" id="addSelected" href="javaScript:;"><i class="icon-forward"></i></a></p>
													      <p><a class="btn" id="removeSelected" href="javaScript:;"><i class="icon-backward"></i></a></p>
													</div>
													<div class="search_Mobile_bot_right">
													      <p class="pa1">选择的成员</p>
													      <select size="12" multiple="multiple" id="select2" name="select2" class="sea_textarea">
													      	<c:forEach items="${userVos}" var="user" varStatus="status">
																<option value="${user.id }">${user.realname }</option>
															</c:forEach>
													      </select>
													</div>
						                    </div>
										</div>
	
									</div>
									
									<div  class="form-actions" style="padding-left:100px;">
										<button id="submitBtn" type="button" class="btn green mybutton"><i class='icon-ok'></i>确定</button>
										<a href="${path}/sys/admin/role/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}" class="btn btn-default"
							               data-dismiss="modal"><i class='icon-remove-sign'></i> 取消
							            </a>
									</div>
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
				chkDisabledInherit:true
			}
		};
	var resTree;
		$(function(){
			$("#submitBtn").on("click",function(){
				if (resTree != null) {//获取资源树选中的节点
					var userIds = [];
					var select2=document.getElementById('select2');
			        var length = select2.options.length - 1;
			        for(var i = length; i >= 0; i--){
			        	userIds.push(select2[i].value);
			        }
					$('#submit_form').ajaxSubmit({
		            	beforeSend : function() {
							$("#submitBtn").html("保存中...");
							$("#submitBtn").attr("disabled", "disabled");
						},
					  	data: {
						  "userIds":userIds
					  	},
						headers: {
							"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
						},
						success : function(data) {
							if (data.success) {
								$("#submitBtn").html("保存成功");
								showTip("提示", "保存成功！");
								
								setTimeout("$('#myModal2').modal('hide');",1000);
								$("#submitBtn").html("保存");
								$("#submitBtn").removeAttr("disabled");								
							}
							
						}
		            }); 
				}
			});
			localPost("${path}/sys/admin/role/readonly/resource/${vo.id}",{"status":1}, function(data,status){
				if (status == "success") {
					//setting.data.key.url = "_url" ;
					resTree = $.fn.zTree.init($("#treeDemo"), setting, data.data);
				}
			},"json",{"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"});
			
			$('#addSelected').click(function(){
				addSelected();
	        });
			
	        $('#removeSelected').click(function(){
	        	removeSelected();
	        });
	        
	        /* $('#select1').click(function(){
				addSelected();
		   	}); */
		});
		
		function addSelected(){
			var select1=document.getElementById('select1');
	    	var select2=document.getElementById('select2');
	        var length = select1.options.length - 1;    
	        for(var i = length; i >= 0; i--){    
	            if(select1[i].selected == true){
	            	var varItem = new Option(select1[i].text, select1[i].value);      
	            	select2.options.add(varItem);   
	            	select1.options[i] = null;
	            }    
	        } 
	      	//鼠标双击事件
	    	/* $('#select2').click(function(){
	    		removeSelected();
	       	}); */
		}
		
		function removeSelected(){
			var select1=document.getElementById('select1');
	    	var select2=document.getElementById('select2');
	        var length = select2.options.length - 1;    
	        for(var i = length; i >= 0; i--){
	        	if(select2[i].selected == true){
	                var varItem = new Option(select2[i].text, select2[i].value);      
	                select1.options.add(varItem);   
	                select2.options[i] = null;
	        	}
	        } 
	        //鼠标双击事件
			/* $('#select1').click(function(){
				addSelected();
		   	}); */
		}
	</script>
</body>
</html>