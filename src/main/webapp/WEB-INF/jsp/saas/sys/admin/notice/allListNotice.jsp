<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知列表</title>
</head>
<body>
	<div class="container-fluid">

				<div class="row-fluid">
					<div class="span12 responsive">
						<%-- 表格开始 --%>
						<div class="portlet box grey">
							<div class="portlet-title" style="vertical-align: middle;">
								<div class="caption">通知</div>
							</div>
							<div class="portlet-body">
								<%-- 按钮操作结束 --%>
								<div class="dataTables_wrapper form-inline">
			                              <ul class="ulnotice">
				                              <c:forEach items="${pager.datas }" var="notice">
				                              	<li ><span class="time02"><fmt:formatDate value="${notice.startDate}" pattern="yyyy-MM-dd"/></span><a href="javaScript:;" onClick="openModalNotice('${notice.id}');"><i class="icon-caret-right"></i><c:out value="${notice.noticeTitle }"></c:out></a></li>
				                              </c:forEach>
			                              </ul>
									<jsp:include page="/WEB-INF/jsp/common/page.jsp">
										<jsp:param value="${pager.total }" name="total"/>
										<jsp:param value="${pager.pageCount }" name="endPage"/>
										<jsp:param value="${pager.pageNum }" name="page"/>
										<jsp:param value="${pager.pageSize }" name="pageSize"/>
									</jsp:include>
								</div>
							</div>
							<%-- 表格结束 --%>
						</div>
					</div>
				</div>
				
				<%-- END PAGE CONTENT--%>
				<div class="modal-scrollable" style="z-index: 10050;display: none;" id="add">
					
					<div id="responsive" class="modal hide fade in" tabindex="-1"
						style="display: block; width: 760px;">

						<div class="span12">

						<!-- BEGIN SAMPLE FORM PORTLET-->   

							<div class="modal-header">
								<button class="close" id="close" type="button"></button>
								<h3 id="modalTitle">编辑通知公告</h3>
							</div>

							<div class="modal-body form">

								<!-- BEGIN FORM-->

								<form action="${path }/sys/admin/notice/add" class="form-horizontal" id="addForm" method="post">
									<input type="hidden" id="id" name="id" value=""/>
									<div id="noticeTitleGroup" class="control-group">

										<label class="control-label">公告标题<span class="required">*</span></label>

										<div class="controls">

											<input type="text" id="noticeTitle" name="noticeTitle" class="span6 m-wrap" required maxlength="255">

											<!-- <span class="help-inline">Some hint here</span> -->

										</div>

									</div>

									<div class="control-group" id="noticeContentGroup">

										<label class="control-label">公告内容<span class="required">*</span></label>

										<div class="controls">
											
											<textarea rows="5" id="noticeContent" name="noticeContent" class="span6 m-wrap" required></textarea> 
										</div>

									</div>
									<div class="control-group" id="pushWayGroup">

										<label class="control-label">推送方式<span class="required">*</span></label>

										<div class="controls">
											
											<!-- <input type="text" id="pushWay" name="pushWay" class="span6 m-wrap" required maxlength="255"> -->
											<select class="span6 m-wrap" id="pushWay" name="pushWay" tabindex="1">
												<option value="0">短信</option>
												<option value="1">邮件</option>
												<option value="2">在线</option>
											</select>
										</div>

									</div>

									<div id="statusGroup" class="control-group">

										<label class="control-label">是否显示</label>

										<div class="controls">

											<select class="span6 m-wrap" id="status" name="status" tabindex="1">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>

										</div>

									</div>
									<div class="control-group mybutton-group">
										<button id="cancel" type="button" data-dismiss="modal" class="btn" style="float: right;margin-left: 5px;"><i class="icon-remove-sign"></i>关闭</button>      
										<button id="submit" type="button" class="btn green"  style="float: right;"><i class="icon-ok"></i>保存</button>                   
									</div>
								</form>

								<!-- END FORM-->       

							</div>

						<!-- END SAMPLE FORM PORTLET-->

					</div>

					</div>
				</div>
				<div class="modal-backdrop fade in" style="z-index: 10049;display: none;" id="modal"></div>
				<%-- END PAGE CONTENT--%>  
			</div>
			
			 <div id="ModalNotice" class="modal hide fade" tabindex="-1" data-width="800">
                      <div class="modal-header" >
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                          <h3>通告公告</h3>
                      </div>
                      <div class="modal-body">
                      
                      	  <div class="mod_notshow">
                          		<h1 id="NoticeTitle"></h1>
                                <div class="nottime" id="NoticeTime">时间：2015-05-17</div>
                                <p id="NoticeContent"></p>
                          </div>
                          
                      </div>

                      <div class="modal-footer">
                          <!--<button type="button" class="btn green"><i class="icon-ok"></i> 确 定</button>-->
                          <button type="button" data-dismiss="modal" class="btn  black"><i class="icon-remove icon-white"></i> 关 闭</button>
                      </div>
                  </div>
                  
			<script src="${path }/js/common/loading.js" type="text/javascript" ></script>
	<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${path }/js/common/custom-validate.js"></script>
	<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
	<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script type="text/javascript">
	var add = $("#add");
	var modal = $("#modal");
	var del = $("#del");
	var addForm = new EstValidate("addForm");
	function openModalNotice(id){
		$.ajax({
			url : "${path}/sys/admin/notice/get/"+id,
			type : "get",
			data : null,
			dataType : "json",
			success : function(data){
				$("#NoticeTitle").text(data.data.noticeTitle);
				$("#NoticeTime").text("时间:"+getLocalTime(data.data.startDate));
				$("#NoticeContent").html(data.data.noticeContent);
				$("#ModalNotice").modal('show');
			},
			error : function(){
				showTip("提示","出错了，请联系管理员")
			}
		});
	}

	function getLocalTime(nS) {       
		return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');       
	} 
	(function(){
		App.init();
		
		$("#cancel").on("click",function(){
			document.getElementById("addForm").reset();
			$("#id").val("");
			$(".control-group").removeClass("error").removeClass("success");
			$(".help-inline").remove();
			$("#modal").hide();
			$("#add").hide();
		});
		
		$("#close").on("click",function(e){
			e.preventDefault();
			document.getElementById("addForm").reset();
			$("#id").val("");
			$(".control-group").removeClass("error").removeClass("success");
			$(".help-inline").remove();
			$("#modal").hide();
			$("#add").hide();
		});
		
		$("#submit").on("click",function(){
			var bool = addForm.form();
			if(bool){
				if($("#id").val()===""){
					$.ajax({
						url : "${path}/sys/admin/notice/add",
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
								window.location.reload();
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
						url : "${path}/sys/admin/notice/update",
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
								window.location.reload();
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
	})();
	
	var addSMS = function(){
		modal.show();
		add.show();
	}
	
	var del = function(obj){
		var id = obj.id;
		var itemName = $(obj).attr("itemname");
		actionByConfirm1(itemName, "${path}/sys/admin/notice/delete/" + id,{} ,function(data,status){
			if (status == "success") {
				location.reload();
			}
		}); 
	}; 
	var update = function(id){
		$.ajax({
			url : "${path}/sys/admin/notice/get/"+id,
			type : "get",
			dataType : "json",
			success : function(json){
				if(json.success){
					$("#id").val(json.data.id);
					$("#noticeTitle").val(json.data.noticeTitle);
					$("#noticeContent").val(json.data.noticeContent);
					$("#pushWay").val(json.data.pushWay);
					if(json.data.status){
						$("#status").val("1");
					}else{
						$("#status").val("0");
					}
					$("#add").show();
					$("#modal").show();
				}
			}
		});
	};
</script>
</body>
</html>