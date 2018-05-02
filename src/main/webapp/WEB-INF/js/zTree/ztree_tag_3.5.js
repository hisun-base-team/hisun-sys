/**
 * 
 * @param zTreeId ztree对象的id,不需要#
 * @param searchField 输入框选择器
 * @param isHighLight 是否高亮,默认高亮,传入false禁用
 * @param isExpand 是否展开,默认合拢,传入true展开
 * @returns
 */	
 function fuzzySearch(zTreeId, searchField, isHighLight, isExpand){
	var zTreeObj = $.fn.zTree.getZTreeObj(zTreeId);//获取树对象
	if(!zTreeObj){
		alter("获取树对象失败");
	}
	var nameKey = zTreeObj.setting.data.key.name; //获取name属性的key
	isHighLight = isHighLight===false?false:true;//除直接输入false的情况外,都默认为高亮
	isExpand = isExpand?true:false;
	zTreeObj.setting.view.nameIsHTML = isHighLight;//允许在节点名称中使用html,用于处理高亮
	
	var metaChar = '[\\[\\]\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)]'; //js正则表达式元字符集
	var rexMeta = new RegExp(metaChar, 'gi');//匹配元字符的正则表达式
	
	// 过滤ztree显示数据
	function ztreeFilter(zTreeObj,_keywords,callBackFunc) {
		if(!_keywords){
			_keywords =''; //如果为空，赋值空字符串
		}
		
		// 查找符合条件的叶子节点
		function filterFunc(node) {
			if(node && node.oldname && node.oldname.length>0){
				node[nameKey] = node.oldname; //如果存在原始名称则恢复原始名称
			}
			//node.highlight = false; //取消高亮
			zTreeObj.updateNode(node); //更新节点让之前对节点所做的修改生效
			if (_keywords.length == 0) { 
				//如果关键字为空,返回true,表示每个节点都显示
				zTreeObj.showNode(node);
				zTreeObj.expandNode(node,isExpand); //关键字为空时是否展开节点
				return true;
			}
			//节点名称和关键字都用toLowerCase()做小写处理
			if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase())!=-1) {
				if(isHighLight){ //如果高亮，对文字进行高亮处理
					//创建一个新变量newKeywords,不影响_keywords在下一个节点使用
					//对_keywords中的元字符进行处理,否则无法在replace中使用RegExp
					var newKeywords = _keywords.replace(rexMeta,function(matchStr){
						//对元字符做转义处理
						return '\\' + matchStr;
						
					});
					node.oldname = node[nameKey]; //缓存原有名称用于恢复
					//为处理过元字符的_keywords创建正则表达式,全局且不分大小写
					var rexGlobal = new RegExp(newKeywords, 'gi');//'g'代表全局匹配,'i'代表不区分大小写
					//无法直接使用replace(/substr/g,replacement)方法,所以使用RegExp
					node[nameKey] = node.oldname.replace(rexGlobal, function(originalText){
						//将所有匹配的子串加上高亮效果
						var highLightText =
							'<span style="color: whitesmoke;background-color: darkred;">'
							+ originalText
							+'</span>';
						return 	highLightText;					
					});
					zTreeObj.updateNode(node); //update让更名和高亮生效
				}
				zTreeObj.showNode(node);//显示符合条件的节点
				return true; //带有关键字的节点不隐藏
			}
			
			zTreeObj.hideNode(node); // 隐藏不符合要求的节点
			return false; //不符合返回false
		}
		var nodesShow = zTreeObj.getNodesByFilter(filterFunc); //获取匹配关键字的节点
		processShowNodes(nodesShow, _keywords);//对获取的节点进行二次处理
	}
	
	/**
	 * 对符合条件的节点做二次处理
	 */
	function processShowNodes(nodesShow,_keywords){
		if(nodesShow && nodesShow.length>0){
			//关键字不为空时对关键字节点的祖先节点进行二次处理
			if(_keywords.length>0){ 
				$.each(nodesShow, function(n,obj){
					var pathOfOne = obj.getPath();//向上追溯,获取节点的所有祖先节点(包括自己)
					if(pathOfOne && pathOfOne.length>0){
						// i < pathOfOne.length-1, 对节点本身不再操作
						for(var i=0;i<pathOfOne.length-1;i++){
							zTreeObj.showNode(pathOfOne[i]); //显示节点
							zTreeObj.expandNode(pathOfOne[i],true); //展开节点
						}
					}
				});				
			}else{ //关键字为空则显示所有节点, 此时展开根节点
				var rootNodes = zTreeObj.getNodesByParam('level','0');//获得所有根节点
				$.each(rootNodes,function(n,obj){
					zTreeObj.expandNode(obj,true); //展开所有根节点
				});
			}
		}
	}
	
	//监听关键字input输入框文字变化事件
	$(searchField).bind('input propertychange', function() {
		var _keywords = $(this).val();
		searchNodeLazy(_keywords); //调用延时处理
	});

	var timeoutId = null;
	// 有输入后定时执行一次，如果上次的输入还没有被执行，那么就取消上一次的执行
	function searchNodeLazy(_keywords) {
		if (timeoutId) { //如果不为空,结束任务
			clearTimeout(timeoutId);
		}
		timeoutId = setTimeout(function() {
			ztreeFilter(zTreeObj,_keywords);	//延时执行筛选方法
			$(searchField).focus();//输入框重新获取焦点
		}, 500);
	}
}
/**
 * 清除查询内容
 * @param queryName
 * @param dataType
 * @param submitType
 * @param url
 * @param id
 * @param setting
 * @param isSearch
 * @param token
 */
function clearTreeQuery(queryName,dataType,submitType,url,id,setting,isSearch,token){
	$("#"+queryName+"").val('');
	treeLoadByTag(dataType,submitType,url,id,setting,isSearch,token);
}
//执行树加载
function treeLoadByTag(dataType,submitType,treeUrl,id,tagSetting,isSearch,token,loadAfterMethod){
	var treeDefineAttObj = document.getElementById(id+"_tagDefineAtt");
	if(treeDefineAttObj==null){
		treeDefineAttObj = document.getElementById(id.substring(0,id.lastIndexOf("_tree"))+"_tagDefineAtt");
	}
	var userParameter = treeDefineAttObj.getAttribute("userParameter");
	var valueMerge = treeDefineAttObj.getAttribute("valueMerge");
	var initCheckboxValueType = treeDefineAttObj.getAttribute("initCheckboxValueType");

	var dataValue = "";
	if(userParameter!=null && userParameter!="") {
		var userParameterArr = userParameter.split(";");
		for (var i = 0; i < userParameterArr.length; i++) {
			var nv = userParameterArr[i].split(":");
			var name = nv[0];
			var value ="";
			if (nv.length >= 2) {
				value = nv[1];
			}
			dataValue = dataValue+"'"+name+"':'"+value+"',";
		}
		dataValue ="\{"+dataValue+"\}";
	}
	$.ajax({
		async : false,
		cache:false,
		type: submitType,
		dataType : dataType,
		url: treeUrl,// 请求的action路径
		//data:dataValue,
		headers: {
			"OWASP_CSRFTOKEN":token
		},
		error: function () {// 请求失败处理函数
			alert('加载树数据失败');
		},
		success:function(data){
			if(data.success){
				$.fn.zTree.init($("#"+id+""), tagSetting, data.data);
				zTree1 = $.fn.zTree.getZTreeObj(id);
				if(isSearch=="true") {
					fuzzySearch(id, '#'+id+'_keyword', false, true); //初始化模糊搜索方法
				}
				if(loadAfterMethod!=null && loadAfterMethod){
					eval(loadAfterMethod);
					//eval(loadAfterMethod+"(event,treeId, treeNode)");
				}
				if(initCheckboxValueType == "0"){//默认为客服端初始化选中数据 为0  为1时数据源初始化数据
					//反选
					var keyObj =document.getElementById(id.substring(0,id.lastIndexOf("_tree")))
					//var treeValueObj = document.getElementById(id+"_valueName");
					if(keyObj!=null && keyObj.value!=""){
						var radioOrCheckbox = treeDefineAttObj.getAttribute("radioOrCheckbox");
						var selectzTree = $.fn.zTree.getZTreeObj(id);
						if(radioOrCheckbox=="radio") {
							var node = selectzTree.getNodeByParam('id', keyObj.value);
							selectzTree.selectNode(node);
						}else if(radioOrCheckbox=="checkbox") {
							if (valueMerge == "true") {//特殊处理的反选
								var checkedNodeIds = keyObj.value.split(",");
								for (var i = 0; i < checkedNodeIds.length; i++) {
									var checkedNodeId = checkedNodeIds[i];
									if(checkedNodeId.lastIndexOf(":0")>-1){//部分选中
										var checkedNodeId = checkedNodeId.substring(0,checkedNodeId.lastIndexOf(":0"));
										var node = selectzTree.getNodeByParam('id', checkedNodeId);
										selectzTree.checkNode(node, true, false);
									}else if(checkedNodeId.lastIndexOf(":1")>-1){//子节点全部选中
										var checkedNodeId = checkedNodeId.substring(0,checkedNodeId.lastIndexOf(":1"));
										var checkedNode = selectzTree.getNodeByParam('id', checkedNodeId);
										var allhChildNodes = getAllChildrenNodes(checkedNode,new Array());//得到所有子节点选中
										selectzTree.checkNode(checkedNode, true, false);
										if(allhChildNodes!=null){
											for(var j=0;j<allhChildNodes.length;j++){
												//var childNode = selectzTree.getNodeByParam('id', allhChildNodes[j]);
												selectzTree.checkNode(allhChildNodes[j], true, false);
											}
										}
									}
								}
							} else {
								var checkedNodeIds = keyObj.value.split(",");
								for (var i = 0; i < checkedNodeIds.length; i++) {
									var node = selectzTree.getNodeByParam('id', checkedNodeIds[i]);
									selectzTree.checkNode(node, true, false);
								}
							}
						}
					}
				}
			}else{
				alert('请求失败');
			}
		}
	});
}
//树形下拉显示
function showTagDivTree(treeSelDiv,id,valueName,isSearch){
	if(window.document.getElementById(valueName).offsetWidth!=0){
		$("#" + id + "_treeSelDiv").css("width",window.document.getElementById(valueName).offsetWidth-3);
	}
	if(isSearch!=null && isSearch=="true"){
		if(window.document.getElementById(valueName).offsetWidth!=0){
			$("#" + id + "_searchDiv").css("width", window.document.getElementById(valueName).offsetWidth-3);
			$("#" + id + "_tree_keyword").css("width", window.document.getElementById(valueName).offsetWidth-30);
		}
	}
	$('#'+treeSelDiv).toggle();
	$("body").bind("mousedown",{treeSelDiv:treeSelDiv},onBodyDownByTagTree);
}
//树形下拉隐藏
function hideTagDivTree(treeSelDiv) {
	$('#'+treeSelDiv).fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownByTagTree);
}
function onBodyDownByTagTree(event) {//|| event.target.id == ""
	if (!(event.target.id == "menuBtn"  || event.target.id == event.data.treeSelDiv || $(event.target).parents("#"+event.data.treeSelDiv+"").length>0)) {
		hideTagDivTree(event.data.treeSelDiv);
	}
}
//选择树形控件单选
function onClickBySelectTreeTag(event,treeId, treeNode) {
	var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
	if (treeDefineAttObj == null) {
		treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
	}
	if (treeDefineAttObj != null) {
		var onClickFunc = treeDefineAttObj.getAttribute("onclickfunc");
		if(onClickFunc!=null && onClickFunc!=""){
			eval(onClickFunc+"(event,treeId, treeNode)");
		}
	}
	setValuesByRadio(treeId, treeNode);
	$('#'+treeId+'SelDiv').toggle();
}
//树形控件单选
function onClickByTreeTag(event,treeId, treeNode) {
	var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
	if (treeDefineAttObj == null) {
		treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
	}
	if (treeDefineAttObj != null) {
		var onClickFunc = treeDefineAttObj.getAttribute("onclickfunc");
		if(onClickFunc!=null && onClickFunc!=""){
			eval(onClickFunc+"(event,treeId, treeNode)");
		}
	}
}
//单选树赋值
function setValuesByRadio(treeId,treeNode){
	try{
		var keyObj =document.getElementById(treeId.substring(0,treeId.lastIndexOf("_tree")))
		var treeValueObj = document.getElementById(treeId+"_valueName");
		var text = treeNode.name;
		keyObj.value=treeNode.id;
		$('#'+treeValueObj.value).val(text);
	}catch(e){
	}
}

//多选方法
function beforeClickByTreeCheckBox(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
	if (treeDefineAttObj == null) {
		treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
	}
	var checkedByTitle = treeDefineAttObj.getAttribute("checkedByTitle");
	var checkedAndNoUnCheckedUnByTitle = treeDefineAttObj.getAttribute("checkedAndNoUnCheckedUnByTitle");
	if(checkedAndNoUnCheckedUnByTitle=="true"){
		if(treeNode.checked == false){
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
		}
	}else if(checkedByTitle=="true"){
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
	}


	if (treeDefineAttObj != null) {
		var onClickFunc = treeDefineAttObj.getAttribute("onclickfunc");
		if(onClickFunc!=null && onClickFunc!=""){
			eval(onClickFunc+"(event,treeId, treeNode)");
		}
	}
	return false;
}

function onCheckByTreeCheckBox(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
	if (treeDefineAttObj == null) {
		treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
	}
	var valueMerge = treeDefineAttObj.getAttribute("valueMerge");
	if(valueMerge == "true"){
		var checkedArr = new Array();//所有部分选中的节点;
		checkedArr = getValueByQuery(zTree);
		var keyObj =document.getElementById(treeId.substring(0,treeId.lastIndexOf("_tree")))
		var treeValueObj = document.getElementById(treeId+"_valueName");
		keyObj.value=checkedArr[0];
		$('#'+treeValueObj.value).val(checkedArr[1]);
	}else{
		var nodes = zTree.getCheckedNodes(true);
		var keys ="";
		var values = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(keys==""){
				keys= nodes[i].id;
				values= nodes[i].name;
			}else{
				keys= keys+","+nodes[i].id ;
				values=values+","+ nodes[i].name;
			}

		}
		var keyObj =document.getElementById(treeId.substring(0,treeId.lastIndexOf("_tree")));
		var treeValueObj = document.getElementById(treeId+"_valueName");
		keyObj.value=keys;
		$('#'+treeValueObj.value).val(values);
	}
}

function beforeCheckByTreeTag(treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
	if (treeDefineAttObj == null) {
		treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
	}
	if (treeDefineAttObj != null) {
		var beforeCheckFunc = treeDefineAttObj.getAttribute("beforeCheckFunc");
		if(beforeCheckFunc!=null && beforeCheckFunc!=""){
			return eval(beforeCheckFunc+"(treeId, treeNode)");
		}
	}
	return true;
}
/**
 * 刷新树
 * @param id 定义ID
 * @param tagSetting setting_加id  如定义id为demoTree 则传入setting_demoTree
 * @param loadAfterMethod 刷新后调用的私有方法
 */
function refreshTreeTag(id,tagSetting,loadAfterMethod){
	var treeDefineAttObj = document.getElementById(id+"_tagDefineAtt");
	var dataType = treeDefineAttObj.getAttribute("dataType");
	var submitType = treeDefineAttObj.getAttribute("submitType");
	var url = treeDefineAttObj.getAttribute("url");
	var isSearch = treeDefineAttObj.getAttribute("isSearch");
	var token = treeDefineAttObj.getAttribute("token");
	treeLoadByTag(dataType,submitType,url,id,tagSetting,isSearch,token,loadAfterMethod);
}

/**
 * 刷新树
 * @param id 定义ID
 * @param tagSetting setting_加id  如定义id为demoTree 则传入setting_demoTree
 * @param loadAfterMethod 刷新后调用的私有方法
 */
function refreshSelectTreeTag(id,tagSetting,loadAfterMethod){
	var treeDefineAttObj = document.getElementById(id+"_tagDefineAtt");
	var dataType = treeDefineAttObj.getAttribute("dataType");
	var submitType = treeDefineAttObj.getAttribute("submitType");
	var url = treeDefineAttObj.getAttribute("url");
	var isSearch = treeDefineAttObj.getAttribute("isSearch");
	var token = treeDefineAttObj.getAttribute("token");

	treeLoadByTag(dataType,submitType,url,id+"_tree",tagSetting,isSearch,token,loadAfterMethod);
}

//得到特殊查询的值
function getValueByQuery(zTree){
	var nodes = zTree.getCheckedNodes(true)
	var someCheckedNodes = new Array();//所有部分选中的节点;
	var allCheckedNodes = new Array();//所有子节点全部选中的节点
	for (var i = nodes.length - 1; i >= 0; i--) {
		var halfCheck = nodes[i].getCheckStatus();
		if (halfCheck.half) {//部分选中
			someCheckedNodes.push(nodes[i]);
		}else{
			allCheckedNodes.push(nodes[i]);
		}
	}
	var allCheckedTopNodes = new Array();//所有子节点全部选中的节点顶级节点集合
	getAllCheckedTopNodes(zTree,allCheckedNodes,allCheckedTopNodes);
	var checkedArr = new Array();//所有部分选中的节点;
	var checkedKeys = "";
	var checkedValues = "";
	for (var i = 0;i<someCheckedNodes.length;i++) {
		if(checkedKeys==""){
			checkedKeys = someCheckedNodes[i].id+":0";
			checkedValues = someCheckedNodes[i].name+"(部分)";
		}else{
			checkedKeys = checkedKeys+","+someCheckedNodes[i].id+":0";
			checkedValues = checkedValues+","+someCheckedNodes[i].name+"(部分)";
		}
	}
	for (var i = 0;i<allCheckedTopNodes.length;i++) {
		if(checkedKeys==""){
			checkedKeys = allCheckedTopNodes[i].id+":1";
			checkedValues = allCheckedTopNodes[i].name;
		}else{
			checkedKeys = checkedKeys+","+allCheckedTopNodes[i].id+":1";
			checkedValues = checkedValues+","+allCheckedTopNodes[i].name;
		}
	}
	checkedArr.push(checkedKeys);
	checkedArr.push(checkedValues);
	return checkedArr;
}
//得到所有子节点全部选中的节点顶级节点集合
function getAllCheckedTopNodes(zTree,allCheckedNodes,allCheckedTopNodes){
	for(var i=0;i<allCheckedNodes.length ;i++) {
		var node = allCheckedNodes[i];
		var pathArr = node.getPath();
		boo1:for(var j=0;j<pathArr.length;j++){
			var pathNode = pathArr[j];
			var halfCheck = pathNode.getCheckStatus();
			if (!halfCheck.half) {//取得节点的最顶级的全选节点
				var isAdd = true;
				boo:for(var k=0;k<allCheckedTopNodes.length ;k++) {
					if(pathNode.id == allCheckedTopNodes[k].id){
						isAdd = false
						break boo;
					}
				}
				if(isAdd == true){
					allCheckedTopNodes.push(pathNode);//将顶级节点存入allCheckedTopNodes集合中
				}
				break boo1;
			}
		}
	}
}

//得到节点的所有子节点
function getAllChildrenNodes(treeNode,result){
	if (treeNode.isParent) {
		var childrenNodes = treeNode.children;
		if (childrenNodes) {
			for (var i = 0; i < childrenNodes.length; i++) {
				result.push(childrenNodes[i]);
				result = getAllChildrenNodes(childrenNodes[i], result);
			}
		}
	}
	return result;
}