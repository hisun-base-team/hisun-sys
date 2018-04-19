<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
        <%@include file="/WEB-INF/jsp/inc/servlet.jsp" %>
<%@include file="/WEB-INF/jsp/inc/taglib.jsp" %>  
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/inc/import.jsp" %>
<link href="${path}/css/common/common.css" rel="stylesheet" type="text/css"/>
<link href="${path}/css/datetimepicker.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${path }/css/DT_bootstrap.css" />
<!-- END PAGE LEVEL STYLES -->
<title>我的工单</title>
</head>
<body>

			<div class="span12">
				<a id="sample_editable_1_new" class="btn green fr" href="${path }${modualBasePath}/add" style="margin: 0 10px 10px 0;">
					<i class="icon-plus"></i> 新建工单 
				</a>
			</div>
			<div class="span12">
                 <div class="Work_order_top">
                      <a href="javascript:loadData(0,1,10)" data="0" class="button_set hover">
                          <h3>${statusCountMap["0"]==null?'0':statusCountMap["0"] }</h3>
                          <span>待处理的工单</span>
                          <p class="arrow_on" style="display:block;"><img src="${path }/images/templateImage/zt01.png"></p>
                      </a>
                      <a href="javascript:loadData(1,1,10)" data="1" class="button_set">
                          <h3>${statusCountMap["1"]==null?'0':statusCountMap["1"] }</h3>
                          <span>处理中的工单</span>
                          <p class="arrow_on"><img src="${path }/images/templateImage/zt01.png"></p>
                      </a>
                      <a href="javascript:loadData(2,1,10)" data="2" class="button_set">
                          <h3>${statusCountMap["2"]==null?'0':statusCountMap["2"] }</h3>
                          <span>已解决的工单</span>
                          <p class="arrow_on"><img src="${path }/images/templateImage/zt01.png"></p>
                      </a>
                      <a href="javascript:loadData(3,1,10)" data="3" class="button_set border_rigth">
                          <h3>${statusCountMap["3"]==null?'0':statusCountMap["3"] }</h3>
                          <span>已完成的工单</span>
                          <p class="arrow_on"><img src="${path }/images/templateImage/zt01.png"></p>
                      </a>
                 </div>
            </div>
            <div class="span12" style="width: 99.6%">
                <div class="portlet-body" id="dataView">
                     <jsp:include page="myticketAjaxData.jsp">
                     	<jsp:param value="0" name="status"/>
                     </jsp:include>    
                </div>
            </div>
	<script type="text/javascript" src="${path }/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap-datetimepicker.js"></script>
	<%@ include file="/WEB-INF/jsp/inc/confirmModal.jsp"%>
	<script type="text/javascript">
		(function(){
			App.init();
		})();
	
		function loadData(status,pageNum,pageSize){
			var tabs = $('.Work_order_top a');
			var _this = this;
			var last = tabs.length-1;
			for(var i=0;i<tabs.length;i++){
				if(tabs[i].getAttribute("data")==status){
					tabs[i].className = "button_set hover " + (i==last?"border_rigth":"");
					$(tabs[i]).children("p")[0].style.display = "block";
				}else{
					tabs[i].className = "button_set " + (i==last?"border_rigth":"");
					$(tabs[i]).children("p")[0].style.display = "none";
				}
			}
			$.ajax({
				url : "${path }${modualBasePath}/ajax/myticket/data",
				type : "get",
				data : {"status":status,"pageSize":pageSize,"pageNum":pageNum},
				dataType : "html",
				success : function(html){
					$('#dataView').html(html);
				},
				error : function(arg1, arg2, arg3){
					myLoading.hide();
					showTip("提示","加载数据失败");
				}
			});
		}
	</script>
</body>
</html>
