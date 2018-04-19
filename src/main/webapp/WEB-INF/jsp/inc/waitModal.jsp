<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- begin 确认框的模态面板 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<style type="text/css">
#waitModal {
	z-index:20000!important;
	top: 40%!important;
	left: 40%!important;
	width: 400px!important;
}
</style>
<div id="waitModal" class="modal hide" tabindex="-1" style="">
    <div class="modal-body">
         <div class="waitModalContent"><i class="icon-spinner icon-spin icon-large"></i>正在处理...</div>
    </div>
</div>

<!-- end 提示框的模态面板 -->
<!-- 脚本 -->
<script>
var defaultWaitTitle = 	$("#waitModalTitle").html();
var defaultWaitContent = $(".waitModalContent").html();
$('#waitModal').on('hide.bs.modal', function () {
	initwaitModal();
});
/**
 * 为提示框设置标题
 */
function setWaitTitle(title) {
	$("#waitModalTitle").html(title);
} 
/**
 * 设置提示框的提示语
 */
function setWaitContent(content) {
	$(".waitModalContent").html(content);
}

function showWait(title, content) {
	var waitModal = $('#waitModal');
	var wait = waitModal.is(':visible');
	if(wait){
		return ;
	}else{
		if (title != null) {
			setWaitTitle(title);
		}
		if (content != null) {
			setWaitContent(content);
		}
		waitModal.modal({
			keyboard: false,
			backdrop: 'static'
		});
	}
}

function hideWait() {
	var waitModal = $('#waitModal');
	var wait = waitModal.is(':visible');
	if(wait){
		return ;
	}else{
		waitModal.modal('hide');
	}
}

function initwaitModal() {
	setWaitTitle(defaultWaitTitle);
	setWaitContent(defaultWaitContent);
}
</script>
