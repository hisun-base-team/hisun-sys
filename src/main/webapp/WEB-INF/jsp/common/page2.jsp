<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
	从后台传到页面必要参数：
	1.total,数据总数。
	2.page,当前所在第几页。
	3.endPage,分页总数，可以用Math.ceil(((double)total)/pageSize)采用向上取整获得
	
	当前页前后 补充页数默认2页，例如当前页是第10页，前后页补充2页就是"8,9,10,11,12",
	当前页补充页数修改只需修改page.jsp中的c标签中的value即可，代码片段如下：
	<c:set value="2" var="numSpace"></c:set>
	
	
	c.分页跳转方法
	需要在各自引用页面需要添加js方法pagehref(arg0,arg1)
	arg0:跳转页数
	arg1:每页大小
	如需其它参数请自己获取,例如用jquery：
	function pagehref (pageNum ,pageSize){
		window.location.href ="../user/searchUser.ai?pageNum="+pageStep+"&pageSize="+pageSize+"&username="+$("#searchbar").val();
	}
	
	d.java模拟页数输出在PageTest测试类里面
--%>
<c:set value="${param.endPage }" var="endPage"></c:set>
<c:set value="${param.pageSize }" var="pageSize"></c:set>
<c:set value="${param.total }" var="total"></c:set>
<c:set value="${param.page }" var="page"></c:set>
<c:set value="${param.callback}" var="callback"></c:set>
<!--分页代码 -->
<div class="row-fluid">
	<div class="span12">
		<div style="float: right;" class="dataTables_paginate paging_bootstrap pagination">
<span class="pageStatis">
	<span class="paspan">共<em>${total}</em>条</span><span class="paspan">每页显示</span>
	<select class="pageselect" name="rowsNum" onchange="searchRowsNum(this);">
		<option value="10" ${param.pageSize ==10?'selected="selected"':''}>10</option>
		<option value="20" ${param.pageSize ==20?'selected="selected"':''}>20</option>
		<option value="30" ${param.pageSize ==30?'selected="selected"':''}>30</option>
		<option value="40" ${param.pageSize ==40?'selected="selected"':''}>40</option>
		<option value="50" ${param.pageSize ==50?'selected="selected"':''}>50</option>
	</select>
	<span class="paspan">条</span>
</span>

			<ul class="pagination-centered" style="float:right;">
		   		<!-- 前后填充多少页的定义 -->
		   		<c:set value="2" var="numSpace"></c:set>
		   		<li><span>共${endPage }页</span> </li>
		  		<%-- <c:if test="${endPage == 0 }">
		  			<li><span class="this-page end">1</span> </li>
		  		</c:if> --%>
		  		<c:if test="${endPage > 0 }">
			  		<li class="prev"><a href="javascript:${callback }(1,${param.pageSize });"><span class="hidden-480">«</span></a></li>
			  		<c:choose>
			  			<c:when test="${page>1 }">
			  				<li class="prev"><a href="javascript:${callback }(${page-1 },${param.pageSize });"><span class="hidden-480">‹</span></a></li>
			  			</c:when>
			  			<c:otherwise>
				  			<li class="prev disabled"><span class="hidden-480">‹</span></a></li>
				  		</c:otherwise>	
			  		</c:choose>
			  		
			  		<!-- 当前页大于（总页数-填充页）执行,例如填充页设置了2,总页数是43，那么当前页在41,42执行-->
			  		<c:set var="normalBegin" value="${page - numSpace }" />
			  		<c:if test="${ normalBegin > 0 }">
			  			<c:set var="needFill" value="${ 2*numSpace - ((page - numSpace)>0?numSpace:(page - 1)) - (endPage - page)  }" />
			  			<c:if test="${needFill > 0 }">
			  				<c:set var="begin" value="${normalBegin - needFill > 0?normalBegin-needFill:1}" />
			  				<c:set  var="end" value="${(page-numSpace)-1}"/>
			  				<c:if test="${begin<=end }">
			  					<c:forEach var="pageStep" begin="${begin }" end="${end}" step="1">				
					  				<li><a href="javascript:${callback }(${pageStep},${param.pageSize });">${pageStep}</a> </li>
					  			</c:forEach>
			  				</c:if>
			  			</c:if>
			  		</c:if>
			  		
		  			<c:forEach var="pageStep" begin="${ (page - numSpace)>1?(page-numSpace):1 }" end="${(page+numSpace)<endPage?(page+numSpace):endPage}" step="1">				
			  			<c:if test="${pageStep == page }">
			  				<li class="active"><a href="javascript:void(0);">${pageStep }</a></li>
			  			</c:if>
			  			<c:if test="${pageStep != page }">
			  				<li><a href="javascript:${callback }(${pageStep},${param.pageSize });">${pageStep }</a> </li>
			  			</c:if>
			  		</c:forEach>
			  		
			  		<!-- 当前页小于等于填充页执行,例如填充页设置了2,当前页在1,2会执行 -->
			  		<c:if test="${page<=numSpace}">
						<c:set var="normalEnd" value="${page + numSpace}" />
						<c:set var="after" value="${endPage - page>numSpace?numSpace:(endPage - page)}" />
						<c:set var="preview" value="${(page - numSpace)>0?numSpace:(page - 1)}" />
						<c:set var="needFill" value="${2*numSpace - preview - after}" />
						<c:if test="${needFill > 0 && normalEnd+needFill<=endPage}">
							<c:set var="begin" value="${page+numSpace+1}" />
							<c:if test="${begin<=endPage }">
								<c:set var="end" value="${normalEnd+needFill}"/>
								<c:forEach var="pageStep" begin="${begin }" end="${end}" step="1">
									<li><a href="javascript:${callback }(${pageStep},${param.pageSize });">${pageStep }</a> </li>
								</c:forEach>
							</c:if>
						</c:if>
					</c:if>
			  		
			  		<c:choose>
			  			<c:when test="${(endPage - page)>0 }">
			  				<li class="next"><a href="javascript:${callback }(${page+1 },${param.pageSize });"><span class="hidden-480">›</span> </a></li>
			  			</c:when>
			  			<c:otherwise>
				  			<li class="next disabled"><span class="hidden-480">›</span></li>
				  		</c:otherwise>	
			  		</c:choose>
			  		
		  			<li><a href="javascript:${callback }(${endPage },${param.pageSize });">»</a></li>
		  		</c:if>
		  		
		  	</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	function searchRowsNum(selTarget)
	{
		var pageSize = selTarget.options[selTarget.selectedIndex].innerHTML;
		${callback }(${page },pageSize);
	}
</script>