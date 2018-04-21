<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>  
<div class="modal-header">
	<button data-dismiss="modal" class="close" type="button"></button>
	<h3 id="modalTitle">
		<c:choose>
			<c:when test="${type==0}">
				新增报表模板
			</c:when>
			<c:when test="${type==1}">
				修改报表模板
			</c:when>
		</c:choose>
	</h3>
</div>

<div class="modal-body">

						<%-- BEGIN SAMPLE FORM PORTLET 表单主体--%>   

								<!-- BEGIN FORM-->

								<form action="${path }/sys/report/save" class="form-horizontal" id="addForm" method="post" enctype="multipart/form-data">
									<input type="hidden" name="type" value="${type }" id="type">
									<input type="hidden" name="id" value="${jasperReportTemp.id }" id="id">
									<div id="reportTempCodeGroup" class="control-group" style=" margin-top: 10px;" >
										<label class="control-label">编码<span class="required">*</span></label>
										<div class="controls">
											<input type="text" class="span6 m-wrap" name="reportTempCode" required maxlength="32" id="reportTempCode" style="height: 16px !important; " value="<c:out value="${jasperReportTemp.reportTempCode }"/>" />
										</div>

									</div>

									<div id="reportTempNameGroup" class="control-group" style=" margin-top: 10px;" >
										<label class="control-label">名称<span class="required">*</span></label>
										<div class="controls">
											<input type="text" class="span6 m-wrap" name="reportTempName" required maxlength="200" id="reportTempName" style="height: 16px !important; " value="<c:out value="${jasperReportTemp.reportTempName }"/>" />
										</div>

									</div>

									<div id="reportTempPathGroup" class="control-group" style=" margin-top: 10px;">
										<label class="control-label mylabel1">报表模板<span
												class="required">*</span>
										</label>
										<div class="controls">
										<input class="span6 m-wrap" type="text" id="reportTempPath" name="reportTempPath" readonly="readonly" style="height: 20px !important;" value="<c:out value="${jasperReportTemp.reportTempPath }"/>" />
											<span class="btn green file_but" >
													<i class="icon-plus icon-white"></i>
												<span>上传报表模板</span>
											<input class="file_progress" type="file" name="browseTemplate" id="browseTemplate">
	  									</span><div id="baseMsg"></div>
											</div>
									</div>

									<div id="reportTempTypeGroup" class="control-group" style=" margin-top: 10px;" >
										<label class="control-label">报表类型<span
												class="required">*</span>
										</label>
										<!--<div class="controls">
											<input type="text" class="span6 m-wrap" name="reportTempType" maxlength="200" id="reportTempType" style="height: 16px !important; " value="<c:out value="${jasperReportTemp.reportTempType }"/>" />
										</div>-->
										<select class="span6 m-wrap" name="reportTempType" id="reportTempType" style="margin-left: 20px;">
											<option value="jasper" <c:if test="${jasperReportTemp.reportTempType eq 'jasper'}">selected</c:if>>jasper</option>
											<option value="jrxml" <c:if test="${jasperReportTemp.reportTempType eq 'jrxml'}">selected</c:if>>jrxml</option>
										</select>
									</div>

									<div id="reportTempDescGroup" class="control-group" style=" margin-top: 10px;">
										<label class="control-label">描述</label>
										<div class="controls">
											<textarea class="span6" style="" rows="2" name="reportTempDesc" maxlength="400" id="reportTempDesc" ><c:out value="${jasperReportTemp.reportTempDesc }"/></textarea>
										</div>

									</div>

									<div id="tombDiv">

							<div id="tombstoneGroup" class="control-group">


								<label class="control-label">状态</label>

								<div class="controls">

									<label class="radio">

										<div class="radio">
											<span class=""><input type="radio" value="0" <c:if test="${jasperReportTemp.tombstone eq '0'}">checked</c:if>
												name="tombstone" id="tombstone"></span>
										</div> 可用

									</label>
									<label class="radio">

										<div class="radio">
											<span class="checked"><input type="radio" <c:if test="${jasperReportTemp.tombstone eq '1'}">checked</c:if>
												value="1" name="tombstone" id="tombstone"></span>
										</div> 不可用

									</label>

								</div>

							</div>

									</div>

									<div class="control-group mybutton-group" style="text-align: right;">
										<button type="button" class="btn green" onclick="formSubmit()" id="preservaButton"><i class="icon-ok"></i> 确定</button>
										<a class="btn" href="javascript:chance()"><i class="icon-remove-sign"></i> 关闭</a>
									</div>
								</form>
							</div>


						<%-- END SAMPLE FORM PORTLET--%>
				

</div>
<script type="text/javascript" src="${path }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path }/js/common/est-validate-init.js"></script>
<script type="text/javascript" src="${path }/js/common/validate-message.js"></script>
<script src="${path }/js/common/loading.js" type="text/javascript" ></script>
<script type="text/javascript">
var baseLine = false;
	(function(){
		App.init();
	})();

$('#browseTemplate').unbind().bind('change',function(){
	var reportTempType = $("#reportTempType").val();
	if(this.files.length>0){
		document.getElementById("baseMsg").innerHTML = "";
		var name = this.files[0].name
		var arr = name.split(".");
		if(arr.length<2 || !(arr[arr.length-1]==reportTempType)){
			showTip("提示","请上传"+reportTempType+"结尾的报表模板",2000);
			return;
		}
		$("#reportTempPath").val(name);
	}else{
		$("#reportTempPath").val("");
	}
});


	var myLoading = new MyLoading('${path}',{zindex:11111});
	var myVld = new EstValidate("addForm");
	//myVld.form();
	function formSubmit(){
		var reportTempPath = $("#reportTempPath").val();
		var bool = myVld.form();
		if(reportTempPath == "")
		{
			document.getElementById("baseMsg").innerHTML = "<font color='red'>请先选择并上传报表模板文件</font>";
			bool = false;
		}

		if(bool){
			myLoading.show();
			$("#preservaButton").html("<i class='icon-list'></i> 操作中...");
			$("#preservaButton").attr("disabled", "disabled");
			$("#addForm").ajaxSubmit({
				url : "${path }/sys/report/save",
				type : "post",
				data : $("#addForm").serialize(),
				headers: {
					"OWASP_CSRFTOKEN":"${sessionScope.OWASP_CSRFTOKEN}"
				},
				dataType : "json",
				success : function(json){
					if(json.code == 1){
						myLoading.hide();
						$("#preservaButton").html("<i class='icon-ok'></i> 确定");
						$("#preservaButton").removeAttr("disabled");

						showTip('提示','操作成功', 1000);
						$('#myModalForReport').modal('hide');

						setTimeout(function(){window.location.href = "${path}/sys/report/list"},2000);
					}else{
						myLoading.hide();
						$("#preservaButton").html("<i class='icon-ok'></i> 确定");
						$("#preservaButton").removeAttr("disabled");
						showTip("提示", json.message, 1000);
						$('#myModalForReport').modal('hide');
						setTimeout(function(){window.location.href = "${path}/sys/report/list?OWASP_CSRFTOKEN=${sessionScope.OWASP_CSRFTOKEN}"},2000);

					}
				},
				error : function(){
					myLoading.hide();
					$("#preservaButton").html("<i class='icon-ok'></i> 确定");
					$("#preservaButton").removeAttr("disabled");
					showTip("提示","出错了请联系管理员",2000);
				}
			});
		}
	}

	function chance()
	{
		$('#myModalForReport').modal('hide');
	}

</script>

