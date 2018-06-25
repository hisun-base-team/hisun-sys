/**
 * 
 * @param zTreeId ztree对象的id,不需要#
 * @param searchField 输入框选择器
 * @param isHighLight 是否高亮,默认高亮,传入false禁用
 * @param isExpand 是否展开,默认合拢,传入true展开
 * @returns
 */	
 function fuzzySearch(zTreeId, searchField, isHighLight, isExpand){
    var treeDefineAttObj = document.getElementById(zTreeId+"_tagDefineAtt");
    if(treeDefineAttObj==null){
        treeDefineAttObj = document.getElementById(zTreeId.substring(0,zTreeId.lastIndexOf("_tree"))+"_tagDefineAtt");
    }
    var dtjz = treeDefineAttObj.getAttribute("dtjz");//是否动态加载
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
        if(dtjz!=null && dtjz=="true") {//动态加载查询
            var param = $.trim($("input[name='param']").val());
            var node = zTreeObj.getNodeByParam("id", 0, null);
            if(_keywords != ""){
                zTreeObj.setting.async.otherParam=["param", _keywords];
            }else {
                //搜索参数为空时必须将参数数组设为空
                zTreeObj.setting.async.otherParam=[];
            }
			//过滤时记录选择节点
            zTreeObj.setting.callback.onAsyncSuccess=onAsyncSuccessByTreeTag;
            zTreeObj.reAsyncChildNodes(node, "refresh");
        }else {
            // 查找符合条件的叶子节点
            function filterFunc(node) {
                if (node && node.oldname && node.oldname.length > 0) {
                    node[nameKey] = node.oldname; //如果存在原始名称则恢复原始名称
                }
                //node.highlight = false; //取消高亮
                zTreeObj.updateNode(node); //更新节点让之前对节点所做的修改生效
                if (_keywords.length == 0) {
                    //如果关键字为空,返回true,表示每个节点都显示
                    zTreeObj.showNode(node);
                    zTreeObj.expandNode(node, isExpand); //关键字为空时是否展开节点
                    return true;
                }
                //节点名称和关键字都用toLowerCase()做小写处理
                if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase()) != -1) {
                    if (isHighLight) { //如果高亮，对文字进行高亮处理
                        //创建一个新变量newKeywords,不影响_keywords在下一个节点使用
                        //对_keywords中的元字符进行处理,否则无法在replace中使用RegExp
                        var newKeywords = _keywords.replace(rexMeta, function (matchStr) {
                            //对元字符做转义处理
                            return '\\' + matchStr;

                        });
                        node.oldname = node[nameKey]; //缓存原有名称用于恢复
                        //为处理过元字符的_keywords创建正则表达式,全局且不分大小写
                        var rexGlobal = new RegExp(newKeywords, 'gi');//'g'代表全局匹配,'i'代表不区分大小写
                        //无法直接使用replace(/substr/g,replacement)方法,所以使用RegExp
                        node[nameKey] = node.oldname.replace(rexGlobal, function (originalText) {
                            //将所有匹配的子串加上高亮效果
                            var highLightText =
                                '<span style="color: whitesmoke;background-color: darkred;">'
                                + originalText
                                + '</span>';
                            return highLightText;
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
function clearTreeQuery(queryName,id,dtjz) {
    if (dtjz != null && dtjz == "true") {//动态加载
        $("#" + queryName + "").val('');
        var treeObj = $.fn.zTree.getZTreeObj(id);
        var node = treeObj.getNodeByParam("id", 0, null);
        //搜索参数为空时重新加载树且反选
        var keyObj =document.getElementById(id.substring(0,id.lastIndexOf("_tree")))
        treeObj.setting.async.otherParam=["defaultkeys",keyObj.value];
        treeObj.setting.callback.onAsyncSuccess=onAsyncSuccessByTreeTag;//
        treeObj.reAsyncChildNodes(node, "refresh");
    } else {
		$("#" + queryName + "").val('');
		$("#" + queryName).trigger("input");
	}
	//treeLoadByTag(dataType,submitType,url,id,setting,isSearch,token);
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
	var dtjz = treeDefineAttObj.getAttribute("dtjz");//是否动态加载
    var shjz = treeDefineAttObj.getAttribute("shjz");//动态加载时 是否实时加载 true为动态加载

    var isSelectTree = treeDefineAttObj.getAttribute("isSelectTree");//是否下拉控件展示的树

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

	if(dtjz!=null && dtjz=="true"){//动态加载
        if(shjz!=null && shjz=="true"){
            //树实时加载顶级节点
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
                    $.fn.zTree.init($("#"+id+""), tagSetting,data);
                    zTree1 = $.fn.zTree.getZTreeObj(id);
                    if(isSearch=="true") {
                        fuzzySearch(id, '#'+id+'_keyword', false, true); //初始化模糊搜索方法
                    }
                    if(loadAfterMethod!=null && loadAfterMethod){
                        eval(loadAfterMethod);
                        //eval(loadAfterMethod+"(event,treeId, treeNode)");
                    }
                    zTree1.setting.async.otherParam=[];
                }
            });
        }else{
            //下拉控件 延迟加载
            $.fn.zTree.init($("#"+id+""), tagSetting);
            zTree1 = $.fn.zTree.getZTreeObj(id);
            if(isSearch=="true") {
                fuzzySearch(id, '#'+id+'_keyword', false, true); //初始化模糊搜索方法
            }
            if(loadAfterMethod!=null && loadAfterMethod){
                eval(loadAfterMethod);
                //eval(loadAfterMethod+"(event,treeId, treeNode)");
            }
            zTree1.setting.async.otherParam=[];
		}



	}else{
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
								var node = selectzTree.getNodeByParam('key', keyObj.value);
								selectzTree.selectNode(node);
							}else if(radioOrCheckbox=="checkbox") {
								if (valueMerge == "true") {//特殊处理的反选
									var checkedNodeIds = keyObj.value.split(",");
									for (var i = 0; i < checkedNodeIds.length; i++) {
										var checkedNodeId = checkedNodeIds[i];
										if(checkedNodeId.lastIndexOf(":0")>-1){//部分选中
											var checkedNodeId = checkedNodeId.substring(0,checkedNodeId.lastIndexOf(":0"));
											var node = selectzTree.getNodeByParam('key', checkedNodeId);
											selectzTree.checkNode(node, true, false);
										}else if(checkedNodeId.lastIndexOf(":1")>-1){//子节点全部选中
											var checkedNodeId = checkedNodeId.substring(0,checkedNodeId.lastIndexOf(":1"));
											var checkedNode = selectzTree.getNodeByParam('key', checkedNodeId);
											var allhChildNodes = getAllChildrenNodes(checkedNode,new Array());//得到所有子节点选中
											selectzTree.checkNode(checkedNode, true, false);
											if(allhChildNodes!=null){
												for(var j=0;j<allhChildNodes.length;j++){
													//var childNode = selectzTree.getNodeByParam('key', allhChildNodes[j]);
													selectzTree.checkNode(allhChildNodes[j], true, false);
												}
											}
										}
									}
								} else {
									var checkedNodeIds = keyObj.value.split(",");
									for (var i = 0; i < checkedNodeIds.length; i++) {
										var node = selectzTree.getNodeByParam('key', checkedNodeIds[i]);
										if(node!=null){
											selectzTree.checkNode(node, true, false);
										}
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
}

//用于动态加载反选
function onAsyncSuccessByTreeTag(event, treeId, treeNode, msg){
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
    if (treeDefineAttObj == null) {
        treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
    }
    var valueMerge = treeDefineAttObj.getAttribute("valueMerge");
    var initCheckboxValueType = treeDefineAttObj.getAttribute("initCheckboxValueType");
    if(initCheckboxValueType == "0"){//默认为客服端初始化选中数据 为0  为1时数据源初始化数据
        //反选
        var keyObj =document.getElementById(treeId.substring(0,treeId.lastIndexOf("_tree")))
        //var treeValueObj = document.getElementById(id+"_valueName");
        if(keyObj!=null && keyObj.value!=""){
            var radioOrCheckbox = treeDefineAttObj.getAttribute("radioOrCheckbox");
            if(radioOrCheckbox=="radio") {
                var node = zTree.getNodeByParam('key', keyObj.value);
                zTree.selectNode(node);
            }else if(radioOrCheckbox=="checkbox") {
                if (valueMerge == "true") {//特殊处理的反选
                    var checkedNodeIds = keyObj.value.split(",");
                    for (var i = 0; i < checkedNodeIds.length; i++) {
                        var checkedNodeId = checkedNodeIds[i];
                        if(checkedNodeId.lastIndexOf(":0")>-1){//部分选中
                            var checkedNodeId = checkedNodeId.substring(0,checkedNodeId.lastIndexOf(":0"));
                            var node = zTree.getNodeByParam('key', checkedNodeId);
                            zTree.checkNode(node, true, false);
                        }else if(checkedNodeId.lastIndexOf(":1")>-1){//子节点全部选中
                            var checkedNodeId = checkedNodeId.substring(0,checkedNodeId.lastIndexOf(":1"));
                            var checkedNode = zTree.getNodeByParam('key', checkedNodeId);
                            var allhChildNodes = getAllChildrenNodes(checkedNode,new Array());//得到所有子节点选中
                            zTree.checkNode(checkedNode, true, false);
                            if(allhChildNodes!=null){
                                for(var j=0;j<allhChildNodes.length;j++){
                                    //var childNode = selectzTree.getNodeByParam('key', allhChildNodes[j]);
                                    zTree.checkNode(allhChildNodes[j], true, false);
                                }
                            }
                        }
                    }
                } else {
                    var checkedNodeIds = keyObj.value.split(",");
                    for (var i = 0; i < checkedNodeIds.length; i++) {
                        var node = zTree.getNodeByParam('key', checkedNodeIds[i]);
                        if(node!=null){
                            zTree.checkNode(node, true, false);
                            var nodePath = node.getPath();
                            for (var j = 0; j< nodePath.length; j++) {
                                zTree.expandNode(nodePath[j], true, false , true);
                            }

                        }
                    }
                }
            }
        }else{
        	var cuaNodeDt = $("#"+treeId+"_tree_cuaNode_id");//页面定义一个反选变量 用于树刷新反选
        	if(cuaNodeDt!=null && cuaNodeDt.val()!=""){
                var cuaNodeIdValue = cuaNodeDt.val();
                var nodeDt = zTree.getNodeByParam('key', cuaNodeIdValue);
                zTree.selectNode(nodeDt);
			}
		}
    }
    //加载完成后将参数和回掉函数清空
    zTree.setting.async.otherParam=[];
    zTree.setting.callback.onAsyncSuccess="";
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
	try {
		var keyObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")))
		var oldKey = keyObj.value;
		var treeValueObj = document.getElementById(treeId + "_valueName");
		var text = treeNode.name;
		if (treeNode.key == null || treeNode.key == undefined) {//如果自定义的treeVo 可能没设置key 则取ID
			keyObj.value = treeNode.id;
		} else {
			keyObj.value = treeNode.key;
		}
		$('#'+treeValueObj.value).val(text);
		if(oldKey!=keyObj.value){
            var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
            if (treeDefineAttObj == null) {
                treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
            }
            var onchangeFunc = treeDefineAttObj.getAttribute("onchangeFunc");
            if(onchangeFunc!=""){
            	eval(onchangeFunc);
			}
		}


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
//复选赋值
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
		//处理思路 在查询时，已选择的值可能不在查询的范围内，则先取得已经选择的值且不在展示节点的值，再取得当前查询结果所选择的值 组合成最终选取的值
		var keyObj =document.getElementById(treeId.substring(0,treeId.lastIndexOf("_tree")));
		var treeValueObj = document.getElementById(treeId+"_valueName");
		var oldKeys = keyObj.value;
		var oldValues = $('#'+treeValueObj.value).val();
		var oldKeyArr = oldKeys.split(",");
        var oldValueArr = oldValues.split(",");
        var topNodes = zTree.getNodes();//
        var allNodes = zTree.transformToArray(topNodes);
        var nodes = zTree.getCheckedNodes(true);
        //取得已选择值在当前所展示树中不存在的id
		var oldCheckedKeysByTag="";
        var oldCheckedValuesByTag="";
        for(var j=0;j<oldKeyArr.length;j++) {
            var isAdd = true;
            boo:for (var i = 0; i < allNodes.length; i++) {
				if(oldKeyArr[j]==allNodes[i].key){

                    isAdd = false;
                    break boo;
				}
            }
            if(isAdd == true){
                if(oldCheckedKeysByTag==""){
                    oldCheckedKeysByTag= oldKeyArr[j];
                    oldCheckedValuesByTag= oldValueArr[j];
                }else{
                    oldCheckedKeysByTag= oldCheckedKeysByTag+","+oldKeyArr[j] ;
                    oldCheckedValuesByTag=oldCheckedValuesByTag+","+ oldValueArr[j];
                }
			}
        }
        var newKeyArr = "";
        //取得当前展示节点所有选中id
        var checkedKeys ="";//
        var checkedValues = "";
        if(oldCheckedKeysByTag!=null && oldCheckedKeysByTag!=undefined){
            newKeyArr = oldCheckedKeysByTag.split(",");
            checkedKeys =oldCheckedKeysByTag;//
            checkedValues = oldCheckedValuesByTag;
        }


        for (var i=0;i<nodes.length; i++) {
        	var isAdd = true;
        	boo:for(var j=0;j<newKeyArr.length;j++){
        		if(nodes[i].key==newKeyArr[j]){
                    isAdd = false;
                    break boo;
				}
			}
			if(isAdd==true){
                if(checkedKeys==""){
                    checkedKeys= nodes[i].key;
                    checkedValues= nodes[i].name;
                }else{
                    checkedKeys= checkedKeys+","+nodes[i].key ;
                    checkedValues=checkedValues+","+ nodes[i].name;
                }
			}
        }
		keyObj.value=checkedKeys;
		$('#'+treeValueObj.value).val(checkedValues);
	}
}
//复选树复选事件
function onCheckByCheckBox(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var treeDefineAttObj = document.getElementById(treeId+ "_tagDefineAtt");
	if (treeDefineAttObj == null) {
		treeDefineAttObj = document.getElementById(treeId.substring(0, treeId.lastIndexOf("_tree")) + "_tagDefineAtt");
	}
	if (treeDefineAttObj != null) {
		var onCheckFunc = treeDefineAttObj.getAttribute("onCheckFunc");
		if(onCheckFunc!=null && onCheckFunc!=""){
			return eval(onCheckFunc+"(treeId, treeNode)");
		}
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
 * 重新传入URL 刷新树
 * @param id 定义ID
 * @param tagSetting setting_加id  如定义id为demoTree 则传入setting_demoTree
 * @param loadAfterMethod 刷新后调用的私有方法
 */
function refreshTreeTagByNewUrl(id,tagSetting,loadAfterMethod,url){
    var treeDefineAttObj = document.getElementById(id+"_tagDefineAtt");
    var dataType = treeDefineAttObj.getAttribute("dataType");
    var submitType = treeDefineAttObj.getAttribute("submitType");
    if(url==null || url==""){
        url = treeDefineAttObj.getAttribute("url");
	}else{
        treeDefineAttObj.setAttribute("url",url);
	}
    var isSearch = treeDefineAttObj.getAttribute("isSearch");
    var token = treeDefineAttObj.getAttribute("token");
    treeLoadByTag(dataType,submitType,url,id+"_tree",tagSetting,isSearch,token,loadAfterMethod);

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

//动态刷新树 且选中传入的节点
function refreshTreeTagByDt(treeId,nodeId) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    var node = treeObj.getNodeByParam("id", 0, null);
    $("#"+treeId+"_tree_cuaNode_id").val(nodeId);
    //加载树且反选
    if (nodeId != null && nodeId != undefined && nodeId != "") {
		treeObj.setting.async.otherParam = ["defaultkeys", nodeId];
	}else{
        treeObj.setting.async.otherParam = ["defaultkeys", ""];
	}
	treeObj.setting.callback.onAsyncSuccess = onAsyncSuccessByTreeTag;//
	treeObj.reAsyncChildNodes(node, "refresh");

    // var cuanode = treeObj.getNodeByParam('id',nodeId);// 获取id为-1的点
    // treeObj.selectNode(cuanode);
    // treeObj.expandNode(node, true, false , true);
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
			checkedKeys = someCheckedNodes[i].key+":0";
			checkedValues = someCheckedNodes[i].name+"(部分)";
		}else{
			checkedKeys = checkedKeys+","+someCheckedNodes[i].key+":0";
			checkedValues = checkedValues+","+someCheckedNodes[i].name+"(部分)";
		}
	}
	for (var i = 0;i<allCheckedTopNodes.length;i++) {
		if(checkedKeys==""){
			checkedKeys = allCheckedTopNodes[i].key+":1";
			checkedValues = allCheckedTopNodes[i].name;
		}else{
			checkedKeys = checkedKeys+","+allCheckedTopNodes[i].key+":1";
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
					if(pathNode.key == allCheckedTopNodes[k].key){
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
