<%@ page language="java" pageEncoding="utf-8"%>
<!-- begin 确认框的模态面板 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<style type="text/css">
#confirmModal {
	z-index: 20000 !important;
}
</style>
<!-- <div id="confirmModal" class="modal hide fade"
	aria-labelledby="confirmModalTitle">

	<div class="modal-header">

		<button data-dismiss="modal" class="close" type="button"></button>

		<h3 id="confirmModalTitle">提示</h3>

	</div>

	<div class="modal-body" id="confirmModalContent"></div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="insureBtn"><i class='icon-ok'></i>确认</button>
		<button type="button" class="btn btn-default blue" data-dismiss="modal"><i class='icon-remove-sign'></i>取消</button>
	</div>
</div> -->
<div id="confirmModal" class="modal container hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitle" >提示</h3>
    </div>
    <div class="modal-body mt10" id="confirmModalContent">
        <p class="font16">这里放置需弹窗宽度自适应的内容...</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="insureBtn"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>
<div id="confirmModal1" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitle1" >提示</h3>
    </div>
    <div class="modal-body" id="confirmModalContent1">
        <p class="font16">这里放置需弹窗宽度自适应的内容...</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="insureBtn1"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>
<div id="confirmModalBox" class="modal hide fade" tabindex="-1" style="">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		<h3 id="confirmModalTitleBox" >提示</h3>
	</div>
	<div class="modal-body" id="confirmModalContentBox">
		<p class="font16">这里放置需弹窗宽度自适应的内容...</p>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn green" id="insureBtnBox"><i class="icon-ok"></i> 确 定</button>
		<button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
	</div>
</div>

<div id="tipConfirmModalBox" class="modal hide fade" tabindex="-1" style="">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		<h3 id="tipConfirmModalTitleBox" >提示</h3>
	</div>
	<div class="modal-body" id="tipConfirmModalContentBox">
		<p class="font16">这里放置需弹窗宽度自适应的内容...</p>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
	</div>
</div>

<div id="confirmModal2" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="confirmModalTitle2" >提示</h3>
    </div>
    <div class="modal-body" id="confirmModalContent2">
    </div>
    <div class="modal-footer">
        <button type="button" class="btn green" id="confirmBtn"><i class="icon-ok"></i> 确 定</button>
        <button type="button" class="btn" id="cancalBtn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="messageModal1" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="messageModalTitle1" >提示</h3>
    </div>
    <div class="modal-body" id="messageModalContent1">
    </div>
    <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="messageModal" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="messageModalTitle" >提示</h3>
    </div>
    <div class="modal-body" style="text-align: center;">
    	<div class="Cue_modal" style="text-align:center"><img src="${path}/images/templateImage/ico_error.png"><span style="padding-left:15px;" id="messageModalContent">您确定要进行该操作吗？</span></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>

<div id="messageModal2" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3>提示</h3>
    </div>
    <div class="modal-body" style="text-align: center;">
    	<div class="Cue_modal" style="text-align:center"><img src="${path}/images/templateImage/SuccessICO.png"><span style="padding-left:15px;" id="messageModalContent2">您确定要进行该操作吗？</span></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove-sign"></i> 关 闭</button>
    </div>
</div>


<!-- end 确认框的模态面板 -->
<!-- 脚本 -->
<script>
	var confirmRet = false;//确认框的返回值，默认为false，只有点击确认按钮才返回true
	var urlOfconfirmModal = null;
	var dataOfconfirmModal = null;
	var callBackOfconfirmModal = null;
	var loadingModal = null;
	var defaultConfirmTitle = $("#confirmModalTitle").html();
	var defaultConfirmContent = $("#confirmModalContent").html();
	var defaultConfirmTitle1 = $("#confirmModalTitle1").html();
	var defaultConfirmContent1 = $("#confirmModalContent1").html();
	var defaultConfirmTitleBox = $("#confirmModalTitleBox").html();
	var defaultConfirmContentBox = $("#confirmModalContentBox").html();
	var defaultTipConfirmTitleBox = $("#tipConfirmModalTitleBox").html();
	var defaultTipConfirmContentBox = $("#tipConfirmModalContentBox").html();
	var onfirFlag = false;
	$("#insureBtn").click(function() {
		confirmRet = true;
		$('#confirmModal').modal('hide');
		if (confirmRet) {
            localPost(urlOfconfirmModal, dataOfconfirmModal, callBackOfconfirmModal, "json", {
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });
		}
		initConfirmModal();
	});
	$("#insureBtn1").click(function() {
		confirmRet = true;
		$('#confirmModal1').modal('hide');
		if (confirmRet) {
            localPost(urlOfconfirmModal, dataOfconfirmModal, callBackOfconfirmModal,"json",{
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });
		}
		initConfirmModal1();
	});
	$("#insureBtnBox").click(function() {
		onfirFlag = true;
		$('#confirmModalBox').modal('hide');
		if (onfirFlag) {
			if(null!=loadingModal)
			{
				loadingModal.show();
			}
            localPost(urlOfconfirmModal, dataOfconfirmModal, callBackOfconfirmModal,"json",{
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });
		}
	});

	/**
	 * 为提示框设置标题
	 */
	function setConfirmTitle(title) {
		$("#confirmModalTitle").html(title);
	}

	function setConfirmTitle1(title) {
		$("#confirmModalTitle1").html(title);
	}
	/**
	 * 设置提示框的提示语
	 */
	function setConfirmContent(content) {
		$("#confirmModalContent").html(content);
	}

	function setConfirmContent1(content) {
		$("#confirmModalContent1").html(content);
	}

	function setConfirmContentBox(content) {
		$("#confirmModalContentBox").html(content);
	}

	function setTipConfirmContentBox(content) {
		$("#tipConfirmModalContentBox").html(content);
	}

	function showMessage(content) {
		$("#messageModalContent").html(content);
		$('#messageModal').modal('show');
	}
	
	function showConfirm(name, operation) {
		var tempInConfirmModal = name;
		if (name != null && name.length > 0) {
			tempInConfirmModal = "“" + name + "”";
		}
		var defaultOperation ="删除";
		if (operation != null) {
			defaultOperation = operation;
		}
		setConfirmContent("确定要" + defaultOperation + tempInConfirmModal + "吗？");
		$('#confirmModal').modal('show');
	}

	function showConfirm1(name, operation) {
		var tempInConfirmModal = name;
		if (name != null && name.length > 0) {
			tempInConfirmModal = "“" + name + "”";
		}
		var defaultOperation ="删除";
		if (operation != null) {
			defaultOperation = operation;
		}
		setConfirmContent1("确定要" + defaultOperation + tempInConfirmModal + "吗？");
		$('#confirmModal1').modal('show');
	}

	function showConfirmBox(name, operation) {
		var tempInConfirmModal = name;
		if (name != null && name.length > 0) {
			tempInConfirmModal = "“" + name + "”";
		}
		var defaultOperation ="删除";
		if (operation != null) {
			defaultOperation = operation;
		}
		setConfirmContentBox("确定要" + defaultOperation + tempInConfirmModal + "吗？");
		$('#confirmModalBox').modal('show');
	}

	function showTipConfirmBox(name, operation) {
		var tempInConfirmModal = name;
		if (name != null && name.length > 0) {
			tempInConfirmModal = "“" + name + "”";
		}
		var defaultOperation ="";
		if (operation != null) {
			defaultOperation = operation;
		}
		setTipConfirmContentBox( defaultOperation + tempInConfirmModal );
		$('#tipConfirmModalBox').modal('show');
	}
	
	function showConfirm2(data) {
		$('#confirmModalContent2').html(data.content);
		$("#confirmBtn").unbind().bind('click',function() {
			$('#confirmModal2').modal('hide');
			if($.isFunction(data.confirm)){
				data.confirm();
			}
		});
		$("#cancalBtn").unbind().bind('click',function() {
			$('#confirmModal2').modal('hide');
			if($.isFunction(data.cancal)){
				data.cancal();
			}
		});
		$('#confirmModal2').modal('show');
	}
	
	function showMessage1(content) {
		$('#messageModalContent1').html(content);
		$('#messageModal1').modal('show');
	}
	//show成功的message
	function showMessage2(content) {
		$('#messageModalContent2').html(content);
		$('#messageModal2').modal('show');
	}
	
	function actionByConfirm(name, url, data, callBack, operation) {
		urlOfconfirmModal = url;
		dataOfconfirmModal = data;
		callBackOfconfirmModal = callBack;
		showConfirm(name, operation);
	}
	
	function actionByConfirm1(name, url, data, callBack, operation) {
		urlOfconfirmModal = url;
		dataOfconfirmModal = data;
		callBackOfconfirmModal = callBack;
		showConfirm1(name, operation);
	}

	function confirmationBox(name, url, data, callBack, operation,loading) {
		urlOfconfirmModal = url;
		dataOfconfirmModal = data;
		callBackOfconfirmModal = callBack;
		loadingModal = loading;
		showConfirmBox(name, operation);
	}

	function tipConfirmationBox(name, operation) {
		showTipConfirmBox(name, operation);
	}
	
	function initConfirmModal() {
		confirmRet = false;
		urlOfconfirmModal = null;
		dataOfconfirmModal = null;
		callBackOfconfirmModal = null;
		setConfirmTitle(defaultConfirmTitle);
		setConfirmContent(defaultConfirmContent);
	}
	function initConfirmModal1() {
		confirmRet = false;
		urlOfconfirmModal = null;
		dataOfconfirmModal = null;
		callBackOfconfirmModal = null;
		setConfirmTitle1(defaultConfirmTitle);
		setConfirmContent1(defaultConfirmContent);
	}
</script>



<!-- begin 提示框的模态面板 -->
<style type="text/css">
#tipModal {
	z-index:20000!important;
}
</style>

<!-- <div id="tipModal" class="modal hide fade"
	aria-labelledby="tipModalTitle">

	<div class="modal-header">

		<button data-dismiss="modal" class="close" type="button"></button>

		<h3 id="tipModalTitle">提示</h3>

	</div>

	<div class="modal-body" id="tipModalContent"></div>
</div> -->

<div id="tipModal" class="modal hide fade" tabindex="-1" style="">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="tipModalTitle"></h3>
    </div>
    <div class="modal-body">
         <div class="tipModalContent"></div>
    </div>

    <!-- <div class="modal-footer">
        <button type="button" class="btn green"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn  black"><i class="icon-remove icon-white"></i> 关 闭</button>
    </div> -->
</div>


<!-- end 提示框的模态面板 -->
<!-- 脚本 -->
<script>

var hideTimeOut = 800;
var defaultTipTitle = 	$("#tipModalTitle").html();
var defaultTipContent = $(".tipModalContent").html();

$('#tipModal').on('hide.bs.modal', function () {
	initTipModal();
});

/**
 * 为提示框设置标题
 */
function setTipTitle(title) {
	$("#tipModalTitle").html(title);
} 

/**
 * 设置默认的自动消失时间
 */
function setTimeOut(time) {
	hideTimeOut = time;
}

/**
 * 设置提示框的提示语
 */
function setTipContent(content) {
	$(".tipModalContent").html(content);
}

function showTip(title, content, time, noautoclose) {
	var tipModal = $('#tipModal');
	var tip = tipModal.is(':visible');
	if(tip){
		return ;
	}else{
		setTipTitle(title);
		setTipContent(content);
		tipModal.modal('show');
		if(!isNaN(time)){
			if(time>0){
				setTimeout("$('#tipModal').modal('hide');",time);
			}
		}else{
			if(noautoclose){
				return ;
			}else{
				setTimeout("$('#tipModal').modal('hide');",hideTimeOut);
			}
		}
	}
}

function showSaveSuccess() {
	showTip("提示", "保存成功！");
}

function showSaveFail(cause) {
	showTip("提示", "保存失败！原因为" + cause);
}

function showDelFail(name, cause) {
	showTip("提示", "删除“" + name + "”失败！原因为" + cause);
}

function initTipModal() {
	setTipTitle(defaultTipTitle);
	setTipContent(defaultTipContent);
}
</script>


<!--重要提示性弹窗 -->
<div id="Prompt" class="modal hide fade" tabindex="-1" data-backdrop="static" data-width="560" >
     <div class="modal-header">
         <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="PromptClose"></button>
         <h3 id="PromptmodalTitle">您确定要删除123拓展信息吗？</h3>
     </div>
     <div class="modal-body">
          <div class="Cue_Prompt_imp">请认真阅读以下说明！</div>
          <div class="Cue_Prompt_imp02" id="PrompModalContent">这个操作不能撤消。这将永久删除123拓展，删除后将不可恢复，请认真审核确认后再进行操作，确认后请在下面填写你要删除的信息。</div>
          <div class="row-fluid">
          		<div class="inp_modalname" id="PrompModalContent01">请输入你要删除的扩展信息名称：</div>
               <input type="text" value="" name="PromptName" id ="PromptName" style="cursor: pointer;" class="m-wrap span12" size="">
          </div>
          <div class="modal_but">
             <a class="mobut" href="###" id="PromptBtn">我已了解清楚，确认删除</a>
          </div>
     </div>
</div>
<!--END 重要提示性弹窗 -->
<script>

var PromptmodalTitle = 	$("#PromptmodalTitle").html();
var PrompModalContent = $("#PrompModalContent").html();
var PrompModalContent01 = $("#PrompModalContent01").html();

$('#Prompt').on('hide.bs.modal', function () {
	initPrompModal();
});

function initPrompModal() {
	$("#PromptmodalTitle").html(PromptmodalTitle);
	$("#PrompModalContent").html(PrompModalContent);
	$("#PrompModalContent01").html(PrompModalContent01);
}

function showPrompModal(title, content, content01,url,data,fuc) {
	$("#PromptmodalTitle").html("您确定要删除"+title+"吗？");
	$("#PrompModalContent").html(content);
	$("#PrompModalContent01").html(content01);
	$("#PromptBtn").html("我已了解清楚，确认删除");
	$('#Prompt').modal('show');
	window.url = url;
	window.data = data;
	window.title = title;
	window.fuc = fuc;
}

function showPrompModal2(title,name, content, content01,url,data,fuc) {
	$("#PromptmodalTitle").html(title);
	$("#PrompModalContent").html(content);
	$("#PrompModalContent01").html(content01);
	$("#PromptBtn").html("我已了解清楚，确认提交");
	$('#Prompt').modal('show');
	window.url = url;
	window.data = data;
	window.title = name;
	window.fuc = fuc;
}
$(function(){
	$("#PromptName").on("keyup",function(){
		if(window.title===$(this).val()){
			$("#PromptBtn").addClass("hover");
		}else{
			$("#PromptBtn").removeClass("hover");
		}
	});
	$("#PromptClose").on("click",function(){
		$("#PromptName").val("");
		$("#PromptBtn").removeClass("hover");
		try{
			myLoading.hide();
		}catch(e){}

	});
	$("#PromptBtn").click(function() {
		if(window.title===$("#PromptName").val()){
			$('#Prompt').modal('hide');
			//$.post(window.url, window.data,window.fuc);

            /*localPost(window.url, window.data, fuc, "json",{
                "OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
            });*/
			$.ajax({
				cache:false,
				url:window.url,
				type:'post',
				data:window.data,
				headers:{
					OWASP_CSRFTOKEN:"${sessionScope.OWASP_CSRFTOKEN}"
				},
				contentType: "application/json; charset=utf-8",
				dataType : "json",
				success:fuc
			});

			$("#PromptName").val("");
			$("#PromptBtn").removeClass("hover");
		}
	});
});
</script>