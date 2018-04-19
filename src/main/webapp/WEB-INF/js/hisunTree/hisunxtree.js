/**----------------------------------------\
 |      Cross Browser Tree Widget 1.1      |
 |-----------------------------------------|
 | Created by chens
 | Created 2008-3-21
 \----------------------------------------*/

var gZzbXTree_isSync4LoadDynamicNode = false; // 是否同步加载动态节点，缺省是异步。liufq 20141022

var zzbTreeConfig  = {
	zzb_checkedTrueIcon			:'/images/tImg/ct.png',
	zzb_checkedFalseIcon			:'/images/tImg/cf.png',
	zzb_contextPath				:'',
	zzb_imgPath					:'',
	//用于点击打开子节点图片
	zzb_openIcon					:'/images/tImg/Lp.png',
	//用于点击收缩子节点图片

	zzb_closeIcon				:'/images/tImg/Lm.png',
	//没有子节点状态下图片
	zzb_noIcon					:'/images/tImg/T.png',
	zzb_lastChildIcon			:'/images/tImg/L.png',
	zzb_lineIcon					:'/images/tImg/I.png',
	//
	open_foldericon				:'/images/tImg/openfoldericon.png',
	close_foldericon				:'/images/tImg/foldericon.png',
	zzb_nullIcon					:'/images/tImg/b.png',
	zzb_topOpenIcon				:'/images/tImg/Tps.png',
	zzb_topCloseIcon				:'/images/tImg/Tlm.png',
	zzb_topNoNodeIcon			:'/images/tImg/Tp.gif'

};

var zzbTreeHandler = {
	idCounter		: 1,
	idPrefix		: "-zzb-tree-object-",
	getId			: function(divID) { return divID+this.idPrefix + this.idCounter++;},
	nodeDiv			:null,
	//用于多个树对象,用div键值对 保存树对象

	zzb_TreeObjs :{}
	//zzb_showDivTreeObjs :{}
}

/**
 执行默认的js函数
 */
var _sanTreeNode_obj=null; //单击对象
var _sanTree_div_Node_obj=null; //节点对象
function defaultOnclick(obj){//obj可能是链接对象 也可能是复选框对象
	try{
		if(obj==null){
			//通过链接对象,得到节点对象
			_sanTree_div_Node_obj=_sanTreeNode_obj.parentNode;
		}else{
			//通过复选框对象,得到节点对象
			_sanTree_div_Node_obj=obj.parentNode;
		}
		//如果当前对象为节点对象,进行下面的操作
		if(_sanTree_div_Node_obj!=null){
			var tree=getObjByMainDivId(_sanTree_div_Node_obj.getAttribute("md"));
			for(var i=0;i<tree.onclicks.length;i++){
				eval(tree.onclicks[i]);

			}
		}
	}catch(e){
		alert(" * defaultOnclick * " + e.description);
	}
}

function zzbTreeAbstractNode(MainDiv) {

	this.MainDiv=MainDiv;//存储树的div对象
	this.currentNode = null;

	//查询得到的对象集合

	this.resultArr=new Array();

	//查询条件
	this.queryStr="";

	//查询点击数
	this.clickCount=0;

	this.zzb_is_init="true";
	this.focusColor="#000033";//当前树的当前节点的初始颜色
	this.true_focus="#000033";//当前树的当前节点显示颜色
	this.false_focus="#C0C0C0";//非当前树的当前节点显示颜色

	this.onclicks=new Array();
	//////////////////////////////////
	this.zzb_DynamicLocal=false;//是否为动态加载
	this.zzb_RadioOrCheckBox=true;//是单选还是多选
	this.zzb_parentRadioEnable=false;//单选的情况下 父节点的选择框是否可选
	this.zzb_parentCheckboxEnable=true;//多选的情况下 父节点的选择框是否可选
	this.zzb_parentChildIsolate=true;//选中父节点是否影响子节点
	this.zzb_OnlyImgExpand=false;//
	this.zzb_rootNodeShow=true;//
	this.zzb_auto_dynamic_load="false";
	this.zzb_checkboxDisplay="";
	this.zzb_dataSource="";
	this.userParameter="";
	this.currentNodeNavigation="";//当前节点的导肮信息
	this.ondblclick="";
	this.aclickDefault="";
	this.checkboxclickDefault;
	this.defaultKeys="";
	this.seletedCheckboxByTitle="";//单击标题是否会影响复选框
	this.firstSelectedByTitle="";//单击标题是否会影响复选框
	this.selectedTitleByCheckbox="";//单击复选框，标题得到焦点
	this.type="";
	this.openOnclick="";
	this.expandByCheckbox="1";
	this.setCurentNodeBool=false;

	//选中上级节点时，下级节点是否要自动全部选中，是或否 1,0
	this.parentSelectedWithChild="";
	//取消上级节点的选中状态时，下级节点是否要自动全部取消，是或否 1,0
	this.parentClearWithChild="";
	//选中下级节点时，如果下级同级节点已全部都为选中状态，其上级是否自动选中，是或否 1,0
	this.lowerSelectedAllWithSuperior="";
	//取消下级节点的选中状态时，其上级节点选中状态是否要自动取消，是或否 1,0
	this.lowerClearWithSuperior="";
	//取消下级节点的选中状态时，其上级节点不自动取消的情况下，如果下级同级节点已全部为未选中状态，其上级是否自动取消，是或否 1,0
	this.lowerClearAllWithSuperior="";
	//配置下级节点选中父节点是否。是或否 1,0
	this.lowerSelectedWithSuperior="";
}

ZZBTreeItem.prototype.setParentSelectedWithChild = function (parentSelectedWithChild){
	this.parentSelectedWithChild=parentSelectedWithChild;
	//alert("parentSelectedWithChild:" + parentSelectedWithChild);
}

ZZBTreeItem.prototype.setParentClearWithChild = function (parentClearWithChild){
	this.parentClearWithChild=parentClearWithChild;
	//alert("parentClearWithChild:" + parentClearWithChild);
}

ZZBTreeItem.prototype.setLowerSelectedAllWithSuperior = function (lowerSelectedAllWithSuperior){
	this.lowerSelectedAllWithSuperior=lowerSelectedAllWithSuperior;
	//alert("lowerSelectedAllWithSuperior:" + lowerSelectedAllWithSuperior);
}

ZZBTreeItem.prototype.setLowerClearWithSuperior = function (lowerClearWithSuperior){
	this.lowerClearWithSuperior=lowerClearWithSuperior;
	//alert("lowerClearWithSuperior:" + lowerClearWithSuperior);
}

ZZBTreeItem.prototype.setLowerClearAllWithSuperior = function (lowerClearAllWithSuperior){
	this.lowerClearAllWithSuperior=lowerClearAllWithSuperior;
	//alert("lowerClearAllWithSuperior:" + lowerClearAllWithSuperior);
}

ZZBTreeItem.prototype.setLowerSelectedWithSuperior = function (lowerSelectedWithSuperior){
	this.lowerSelectedWithSuperior=lowerSelectedWithSuperior;
}

ZZBTreeItem.prototype.setExpandByCheckbox = function (expandByCheckbox){
	this.expandByCheckbox=expandByCheckbox;
}

ZZBTreeItem.prototype.setOpenOnclick = function (openOnclick){
	this.openOnclick=openOnclick;
}

ZZBTreeItem.prototype.setType = function (type){
	this.type=type;
}

ZZBTreeItem.prototype.setSelectedTitleByCheckbox = function (selectedTitleByCheckbox){
	this.selectedTitleByCheckbox=selectedTitleByCheckbox;
}

ZZBTreeItem.prototype.setFirstSelectedByTitle = function (firstSelectedByTitle){
	this.firstSelectedByTitle=firstSelectedByTitle;
}

ZZBTreeItem.prototype.setCheckboxclickDefault = function (checkboxclickDefault){
	this.checkboxclickDefault=checkboxclickDefault;
}

ZZBTreeItem.prototype.setAclickDefault = function (aclickDefault){
	this.aclickDefault=aclickDefault;
}
ZZBTreeItem.prototype.setSeletedCheckboxByTitle = function (seletedCheckboxByTitle){
	this.seletedCheckboxByTitle=seletedCheckboxByTitle;
}
ZZBTreeItem.prototype.setOndblclick = function (ondblclick){
	this.ondblclick=ondblclick;
}
ZZBTreeItem.prototype.setUserParameter = function (userParameter){
	this.userParameter=userParameter;
}

ZZBTreeItem.prototype.setIsInit = function (boolStr){
	this.zzb_is_init=boolStr;
}
function ZZBTreeItem(MainDiv,conPath,useWise){
	//
	if(useWise!=null && useWise!="" && useWise=="jsp"){
		if(conPath!="" && conPath.length>0){
			zzbTreeConfig.zzb_contextPath=conPath;
		}
		this.base = zzbTreeAbstractNode;
		this.base(MainDiv);
		zzbTreeHandler.zzb_TreeObjs[MainDiv.id]=this;
		this.createDiv();
		//ExpandDefeultNode(this.MainDiv,false,"\\",this);
	}else{//
		zzbTreeConfig.zzb_contextPath=conPath;
		this.base = zzbTreeAbstractNode;
		this.base(MainDiv);
		zzbTreeHandler.zzb_TreeObjs[MainDiv.id]=this;
		this.createDiv();
		//SavaInitialInfo(this.MainDiv,this,"\\");
		//anewInitialize(this.MainDiv);
	}

}

ZZBTreeItem.prototype.init = function (){
	if(this.zzb_is_init=="true"){
		/*if(this.zzb_auto_dynamic_load=="1"){
		 autoDynamicFirstFloorNode(this.MainDiv);
		 }else{*/
		/*
		 var d1 = new Date();
		 var startStr=d1.getHours() + ":" + d1.getMinutes() + ":" + d1.getSeconds() + ":" + d1.getMilliseconds();
		 ExpandDefeultNode(this.MainDiv,false,"\\",this);
		 var d2 = new Date();
		 var endStr=d2.getHours() + ":" + d2.getMinutes() + ":" + d2.getSeconds() + ":" + d2.getMilliseconds();
		 alert("startStrssss:"+startStr + "  endStr:" + endStr);
		 */
		if(this.currentNodeNavigation!=null && this.currentNodeNavigation!=""){
			this.setCurrentByCurrentNodeNavigation(this.currentNodeNavigation);
		}
		//}
	}
}
ZZBTreeItem.prototype.setCurrentByCurrentNodeNavigation = function (currentNodeNavigation){
	//var treeObj=getObjByMainDivId(obj.getAttribute("md"));
	try{
		var currentNodeNavigationArr=currentNodeNavigation.split(","); // 形如：1,2,1  表示第1个节点下的第2个节点下的第1个节点
		var obj=this.MainDiv;
		for(var i=0;i<currentNodeNavigationArr.length;i++){
			obj=obj.firstChild; // 取下级的第一个节点
			var j = parseInt(currentNodeNavigationArr[i]); // 本级中的第几个
			while(j > 1){
				obj=obj.nextSibling.nextSibling; // 移动到下一个节点
				j--;
			}
			if(i==(currentNodeNavigationArr.length-1)){ // 已经是最后一个，就不需要再移动到此节点的下级节点容器了
				break;
			}
			obj=obj.nextSibling; // 指向下级节点的容器
		}
		if(obj!=null){
			//gZzbXTree_isSync4LoadDynamicNode = true; // 指定同步加载树的子节点 liufq 20141024
			this.ExpandNodeAndNextNode(obj);
			//gZzbXTree_isSync4LoadDynamicNode = false; // 还原为异步加载
			this.setCurrentNodeByNode(obj);
//	  	  document.getElementById("showTree").scrollIntoView();
			//obj.scrollIntoView(true);
		}
	}catch(e){
		alert(" * setCurrentByCurrentNodeNavigation * " + e.description + "(" + currentNodeNavigation + ")");
	}

}
/*
 初始化设置
 */
/*
 ZZBTreeItem.prototype.initTree = function (){
 try{
 var obj=this.MainDiv;
 changeOperateImg(obj.firstChild);//处理第一个节点
 //////////////////////////////////////////////
 var childNodeLast=obj.lastChild;
 if(childNodeLast.nodeName=="DIV"){//如果第一个是div节点
 childNodeLast=obj.lastChild.previousSibling;
 }else{//不是DIV节点
 childNodeLast=obj.lastChild.previousSibling.previousSibling;
 }
 changeOperateImg(childNodeLast);//处理最后一个子节点
 }catch(e){
 alert("initTree::" + e.description);
 }
 }
 */

//添加一个根节点
/*
 rootName: 根节点名字

 hUrl:根节点链接

 jsf:根节点js方法
 rootShowBool:是否显示根节点

 */
ZZBTreeItem.prototype.addRootNode = function (rootName,hUrl,jsf,rootShowBool){
	try{

		//得到存储树的div对象
		var topDiv=this.MainDiv;
		//创建一个div对象
		var rootDiv = zzbTreeHandler.nodeDiv.cloneNode(true);
		rootDiv.id=zzbTreeHandler.getId(this.MainDiv.id);
		if(!rootShowBool){
			rootDiv.style.display="none";
		}
		rootDiv.setAttribute("md",this.MainDiv.id);
		rootDiv.className ="webfx-tree-item";
		rootDiv.innerHTML="<img id=\""+rootDiv.id+"-2-icon\" class=\"webfx-tree-icon\" src=\"" + zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath + zzbTreeConfig.open_foldericon+"\" ><a href=\""+jsf+"\" id=\""+rootDiv.id+"\" onfocus=\"nodeFocus(this)\"><b>"+rootName+"</b></a>";
		topDiv.appendChild(rootDiv);

		//添加一个兄弟节点用于保存了节点
		var nodeCont = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeCont.id=rootDiv.id+"-cont";
		nodeCont.className="webfx-tree-container";
		topDiv.appendChild(nodeCont);

	}catch(e){
		alert(" * addRootNode * "+e.description);
	}

	return rootDiv;
}

//添加一个节点

/*
 pObj:		父节点

 nodeName:	节点名

 hUrl:		链接URL
 target:     链接目标
 jsf :javascript方法
 key:        二次开发都提供的key值


 */
ZZBTreeItem.prototype.copyNode = function (copyObj,pObj,imgClick,checkBoxClick,aClick,showTitle,ondblclick){

	try{
		var treeObj=getObjByMainDivId(pObj.getAttribute("md"));
		//设置为动态加载时 得到父对象的层次数
		if(this.zzb_DynamicLocal==true &&(pObj==null || (parseInt(pObj.getAttribute("cs"))+1)>1)){
			return null;
		}
		var cs=parseInt(pObj.getAttribute("cs"))+1;
		//if(cs<2){
		////父div对象
		var parentDiv=pObj.nextSibling;
		parentDiv.setAttribute("cct","1");//标识子节点存放区已经有值

		//创建一个节点div对象
		var nodeDiv = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeDiv.id=zzbTreeHandler.getId(this.MainDiv.id);
		nodeDiv.setAttribute("md",this.MainDiv.id);
		nodeDiv.setAttribute("cs",cs);
		exchangeNodeValue(copyObj,nodeDiv,pObj.getAttribute("md"));
		nodeDiv.setAttribute("className","t1");

		//通过层次数判断应该缩进多少
		var cengImg="";
		var i=cs;

		while(i>1){
			cengImg+="<img id=\""+nodeDiv.id+"-indent-"+cs+"\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_nullIcon+"\">";
			i--;
		}

		//动态加载方法
		var dynamicFunction="";
		if(this.zzb_DynamicLocal==true){
			dynamicFunction="Ldn(this);";
		}


		var radioOrCheckBoxHtml="<img onclick=\"javascript:{gic(this);"+checkBoxClick+";}\" style=\"display:none;cursor:hand\" id=\"inputImg\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_checkedTrueIcon+"\"><input type=\"checkbox\" style=\""+treeObj.zzb_checkboxDisplay+"\" tag=\"INPUT\" name=\""+nodeDiv.id+"\" sel=\"0\" onclick=\"checkSel(this);"+checkBoxClick+";\" name=\""+nodeDiv.id+"\" style=\"height:13px\">";

		var openCloseImg="<img open=\"no\"  onclick=\"orc(this);"+imgClick+";\" onfocus=\"\" style=\"cursor:hand;display:\" id=\""+nodeDiv.id+"-plus\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_openIcon+"\"/>";

		var title=getNodeText(copyObj);//设置显示标题
		if(showTitle!=null && showTitle!=""){
			title=showTitle;
		}

		var aObj=getAChildNode(copyObj);
		var hrefHtml="<a href=\""+aObj.getAttribute("href")+"\"  target=\""+aObj.getAttribute("target")+"\" onfocus=\"nodeFocus(this)\" ondblclick=\""+aObj.getAttribute("ondblclick")+";"+ondblclick+"\" jsf=\""+aObj.getAttribute("jsf")+"\" onClick=\"setNodesLineByAObj(this);"+aClick+";return expandByA(this);\">"+title+"</a>";

		nodeDiv.innerHTML=cengImg+openCloseImg+radioOrCheckBoxHtml+hrefHtml;

		parentDiv.appendChild(nodeDiv);

		//添加一个兄弟节点用于保存了节点
		var nodeCont = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeCont.id=nodeDiv.id+"-cont";
		nodeCont.style.display="none";
		nodeCont.setAttribute("className","t2");
		parentDiv.appendChild(nodeCont);

		/*
		 支技动态加载的情况下

		 */
		if(this.zzb_DynamicLocal){
			this.addDynamicLoading("Loading.....",nodeDiv);
		}
		//}

	}catch(e){
		alert(" * copyNode * " + e.description);
	}
	return nodeDiv;
}

//添加一个节点

/*
 pObj:		父节点

 nodeName:	节点名

 hUrl:		链接URL
 target:     链接目标
 jsf :javascript方法
 key:        二次开发都提供的key值


 */

/*
 ZZBTreeItem.prototype.addDownNode = function (pObj,nodeName,hUrl,target,jsf,key,hc,aBefore,aAfter,iv){

 try{

 //设置为动态加载时 得到父对象的层次数
 if(zzbTreeConfig.zzb_DynamicLocal==true &&(pObj==null || (parseInt(pObj.getAttribute("cs"))+1)>1)){
 //alert(cs);
 return null;
 }

 var cs=parseInt(pObj.getAttribute("cs"))+1;
 //if(cs<2){
 ////父div对象
 var parentDiv=pObj.nextSibling;
 parentDiv.setAttribute("cct","1");//标识子节点存放区已经有值

 //创建一个节点div对象
 var nodeDiv = zzbTreeHandler.nodeDiv.cloneNode(true);
 nodeDiv.setAttribute("hc",hc);
 nodeDiv.setAttribute("key",key);
 nodeDiv.setAttribute("iv",iv);
 nodeDiv.id=zzbTreeHandler.getId(this.MainDiv.id);
 nodeDiv.setAttribute("md",this.MainDiv.id);
 nodeDiv.setAttribute("cs",cs);

 nodeDiv.className ="webfx-tree-item";

 //pObj.nextSibling.nextSibling.id);
 //通过层次数判断应该缩进多少

 var cengImg="";
 var i=cs;

 while(i>1){
 cengImg+="<img id=\""+nodeDiv.id+"-indent-"+cs+"\" src=\""+zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_nullIcon+"\">";
 i--;
 }

 //动态加载方法

 var dynamicFunction="";
 if(zzbTreeConfig.zzb_DynamicLocal==true){
 dynamicFunction="LoadDynamicNode(this);";
 }

 var radioOrCheckBoxHtml="<img style=\"display:none;cursor:hand\" onclick=\"gic(this)\" src=\""+zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_checkedTrueIcon+"\"><input type=\"checkbox\" tag=\"INPUT\" sel=\"0\" onclick=\"checkSel(this);\" name=\""+nodeDiv.id+"\" style=\"height:13px\">";

 nodeDiv.innerHTML=cengImg+"<img open=\"no\"  onclick=\"orc(this);"+dynamicFunction+"setNodesLineByImgObj(this);\" onfocus=\"\" style=\"cursor:hand;display:\" id=\""+nodeDiv.id+"-plus\" src=\""+zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_openIcon+"\"/>"+radioOrCheckBoxHtml+aBefore+"<a href=\""+hUrl+"\" style=\"cursor:hand;\" target=\""+target+"\" onfocus=\"nodeFocus(this)\" ondblclick=\"executeJs(this)\" jsf=\""+jsf+"\" onClick=\"javascript:{setNodesLineByAObj(this);return expandByA(this);}\">"+nodeName+"</a><div>"+aAfter+"</div>";
 parentDiv.appendChild(nodeDiv);

 //添加一个兄弟节点用于保存了节点
 var nodeCont = zzbTreeHandler.nodeDiv.cloneNode(true);
 nodeCont.id=nodeDiv.id+"-cont";
 nodeCont.style.display="none";
 nodeCont.className="webfx-tree-container";
 parentDiv.appendChild(nodeCont);


 //支技动态加载的情况下

 if(zzbTreeConfig.zzb_DynamicLocal){
 this.addDynamicLoading("Loading.....",nodeDiv);
 }
 //}

 }catch(e){
 alert("addDownNode:"+e.description);
 }
 return nodeDiv;
 }
 */

/*
 双击执行相应操作
 */
function executeJs(obj){
	_sanTreeNode_obj=obj;//将当前对象赋于,公用变量
	eval(obj.getAttribute("jsf"));
	return true;
}

function ej(obj){
	executeJs(obj)
}
/*
 添加一个节点

 */
ZZBTreeItem.prototype.add = function(nodeName,hUrl,target,jsf,key,hc,aBefore,aAfter,iv){
	try{

		//得到父对象的层次数

		var cs=parseInt(this.currentNode.getAttribute("cs"))+1;
		////父div对象
		var parentDiv=this.currentNode.nextSibling;
		parentDiv.setAttribute("cct","1");//标识子节点存放区已经有值


		//创建一个节点div对象
		var nodeDiv = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeDiv.setAttribute("hc", hc);
		nodeDiv.setAttribute("key",key);
		nodeDiv.setAttribute("iv",iv);
		nodeDiv.id=zzbTreeHandler.getId(this.MainDiv.id);
		nodeDiv.setAttribute("cs",cs);
		nodeDiv.setAttribute("md",this.MainDiv.id);
		nodeDiv.setAttribute("className","webfx-tree-item");

		//通过层次数判断应该缩进多少

		var cengImg="";
		var i=cs;
		while(i>1){
			cengImg+="<img id=\""+nodeDiv.id+"-indent-"+cs+"\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_nullIcon+"\">";
			i--;
		}

		//动态加载方法

		var dynamicFunction="";
		if(this.zzb_DynamicLocal==true){
			dynamicFunction="LoadDynamicNode(this);";
		}

		var radioOrCheckBoxHtml="<img style=\"display:none;cursor:hand\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_checkedTrueIcon+"\"><input type=\"checkbox\" tag=\"INPUT\" sel=\"0\" onclick=\"checkSel(this)\" name=\""+nodeDiv.id+"\" style=\"height:13px\">";
		var operateImg="";
		if(this.zzb_DynamicLocal){
			operateImg=zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_openIcon;
		}else{
			operateImg=zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_noIcon;
		}
		nodeDiv.innerHTML=cengImg+"<img open=\"no\" onclick=\"orc(this);"+dynamicFunction+"setNodesLineByImgObj(this);\" style=\"cursor:hand;display:\" id=\""+nodeDiv.id+"-plus\" src=\""+operateImg+"\"/>"+radioOrCheckBoxHtml+aBefore+"<a href=\""+hUrl+"\" target=\""+target+"\" onfocus=\"nodeFocus(this)\" jsf=\""+jsf+"\" onClick=\"expandByA(this)\" >"+nodeName+"</a><div>"+aAfter+"</div>";
		parentDiv.appendChild(nodeDiv);

		//添加一个兄弟节点用于保存了节点
		var nodeCont = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeCont.id=nodeDiv.id+"-cont";
		nodeCont.style.display="none";
		nodeCont.setAttribute("className","webfx-tree-container");
		parentDiv.appendChild(nodeCont);

		/*
		 支技动态加载的情况下

		 */
		if(this.zzb_DynamicLocal){
			this.addDynamicLoading("Loading.....",nodeDiv);
		}

		//不是动态加载就展开
		if(!this.zzb_DynamicLocal){
			setNodesLineByDivObj(this.currentNode);
			expandByDiv(this.currentNode);
		}

	}catch(e){
		alert(" * add * "+e.description);
	}
	return nodeDiv;

}

/*
 添加一个兄弟节点

 */
ZZBTreeItem.prototype.addBrotherNode = function(nodeName,hUrl,target,jsf,key,hc,aBefore,aAfter,iv){
	try{

		if(this.currentNode==null){return false;}

		//得到父对象的层次数

		var cs=parseInt(this.currentNode.getAttribute("cs"));
		//创建一个节点div对象
		var nodeDiv = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeDiv.setAttribute("hc",hc);
		nodeDiv.setAttribute("key",key);
		nodeDiv.setAttribute("iv",iv);
		nodeDiv.id=zzbTreeHandler.getId(this.MainDiv.id);
		nodeDiv.setAttribute("cs",cs);
		nodeDiv.setAttribute("md",this.MainDiv.id);
		nodeDiv.setAttribute("className","webfx-tree-item");

		//通过层次数判断应该缩进多少


		var cengImg="";
		var i=cs;
		while(i>1){
			cengImg+="<img id=\""+nodeDiv.id+"-indent-"+cs+"\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_nullIcon+"\">";
			i--;
		}

		//动态加载方法

		var dynamicFunction="";
		if(this.zzb_DynamicLocal==true){
			dynamicFunction="LoadDynamicNode(this);";
		}

		var radioOrCheckBoxHtml="<img style=\"display:none;cursor:hand\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_checkedTrueIcon+"\"><input type=\"checkbox\" sel=\"0\" tag=\"INPUT\" onclick=\"checkSel(this)\" name=\""+nodeDiv.id+"\" style=\"height:13px\">";
		var operateImg="";
		if(this.zzb_DynamicLocal){
			operateImg=zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_openIcon;
		}else{
			operateImg=zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_noIcon;
		}

		nodeDiv.innerHTML=cengImg+"<img open=\"no\" onclick=\"orc(this);"+dynamicFunction+"setNodesLineByImgObj(this);\" style=\"cursor:hand;display:\" id=\""+nodeDiv.id+"-plus\" src=\""+operateImg+"\"/>"+radioOrCheckBoxHtml+aBefore+"<a href=\""+hUrl+"\" target=\""+target+"\" onfocus=\"nodeFocus(this)\" jsf=\""+jsf+"\" onClick=\"expandByA(this)\" >"+nodeName+"</a><div>"+aAfter+"</div>";
		//parentDiv.appendChild(nodeDiv);

		this.currentNode.nextSibling.insertAdjacentElement("afterEnd",nodeDiv);


		//this.currentNode.nextSibling.insertAdjacentElement("afterEnd",nodeDiv);
		//nodeDiv.insertAdjacentElement("afterEnd",nodeContDiv);

		//添加一个兄弟节点用于保存了节点
		var nodeCont = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeCont.id=nodeDiv.id+"-cont";
		nodeCont.style.display="none";
		nodeCont.className="webfx-tree-container";
		//parentDiv.appendChild(nodeCont);
		nodeDiv.insertAdjacentElement("afterEnd",nodeCont);

		/*
		 支技动态加载的情况下

		 */
		if(this.zzb_DynamicLocal){
			this.addDynamicLoading("Loading.....",nodeDiv);
		}

		//不是动态加载就展开
		if(!this.zzb_DynamicLocal){
			setNodesLineByDivObj(this.currentNode);
			expandByDiv(this.currentNode);
		}

	}catch(e){
		alert(" * addBrotherNode * "+e.description);
	}
	return nodeDiv;

}

/*
 创建兄弟节点
 */
ZZBTreeItem.prototype.createDiv = function(){
	try{
//		zzbTreeHandler.nodeDiv=document.createElement("<DIV pID=''  rn=\"\" nt=\"\" sf=\"\" fn=\"\" hy1=\"\" is='' gc='0' gi='' da='' ds='0' dd='0' clickCount='0' iv='' style='display: block;' cs='0' dc='0' md='' key='' igi='1' icd='1' hc=''>");
//		zzbTreeHandler.nodeDiv= $("<DIV pID=''  rn=\"\" nt=\"\" sf=\"\" fn=\"\" hy1=\"\" is=''
//		gc='0' gi='' da='' ds='0' dd='0' clickCount='0' iv=''
//		style='display: block;' cs='0' dc='0' md='' key='' igi='1' icd='1' hc=''>");
		var div =  document.createElement("div");
		div.setAttribute("pID","");
		div.setAttribute("rn","");
		div.setAttribute("nt","");
		div.setAttribute("sf","");
		div.setAttribute("fn","");
		div.setAttribute("hy1","");
		div.setAttribute("is","");
		div.setAttribute("gc","0");
		div.setAttribute("gi","");
		div.setAttribute("da","");
		div.setAttribute("ds","0");
		div.setAttribute("dd","0");
		div.setAttribute("clickCount","0");
		div.setAttribute("iv","");
		div.setAttribute("style","display: block;");
		div.setAttribute("cs","0");
		div.setAttribute("dc","0");
		div.setAttribute("md","");
		div.setAttribute("key","");
		div.setAttribute("igi","1");
		div.setAttribute("icd","1");
		div.setAttribute("hc","");
		zzbTreeHandler.nodeDiv = div;
	}catch(e){
		alert(" * createDiv *" + e.description);
	}
}

/*
 设置系统当路径
 */
ZZBTreeItem.prototype.setContextPath = function (context_path){
	zzbTreeConfig.zzb_contextPath=context_path;
}

/*
 动态加载 添加一个loading节点
 */
ZZBTreeItem.prototype.addDynamicLoading = function(nodeName,obj){
	try{

		//得到父对象的层次数
		var cs=parseInt(obj.getAttribute("cs"))+1;
		////父div对象
		var parentDiv=obj.nextSibling;
		parentDiv.setAttribute("cct","1");//标识子节点存放区已经有值


		//创建一个节点div对象
		var nodeDiv = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeDiv.setAttribute("hc",hc);
		nodeDiv.setAttribute("key",key);
		nodeDiv.id=zzbTreeHandler.getId(this.MainDiv.id);
		nodeDiv.setAttribute("cs",cs);
		nodeDiv.setAttribute("md",this.MainDiv.id);
		nodeDiv.className ="webfx-tree-item";


		var cengImg="";
		var i=cs;
		while(i>1){
			cengImg+="<img id=\""+nodeDiv.id+"indent"+cs+"\" src=\"images/blank.png\">";
			i--;
		}


		//var radioOrCheckBoxHtml="<input type=\"checkbox\" sel=\"1\" onclick=\"checkSel(this)\" name=\""+nodeDiv.id+"\" style=\"height:12px\">";

		nodeDiv.innerHTML=cengImg+"<img open=\"no\" onclick=\"orc(this);setNodesLineByImgObj(this);\" style=\"cursor:hand;display:\" id=\""+nodeDiv.id+"-plus\" src=\""+zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_noIcon+"\"/><a>"+nodeName+"</a>";
		parentDiv.appendChild(nodeDiv);

		//添加一个兄弟节点用于保存了节点
		var nodeCont = zzbTreeHandler.nodeDiv.cloneNode(true);
		nodeCont.id=nodeDiv.id+"-cont";
		nodeCont.style.display="none";
		nodeCont.className="webfx-tree-container";
		parentDiv.appendChild(nodeCont);


		//setNodesLineByDivObj(this.currentNode);


		//expandByDiv(this.currentNode);

	}catch(e){
		alert(" * addDynamicLoading * "+e.description);
	}
	return nodeDiv;

}

/*
 设置子节点是　disabled 的值
 */
function setChildNodeStatus(obj,bool){
	try{
		/*var childs=getDivChildNodes(obj);
		 for(var i=0;i<childs.length;i++){
		 getInputNode(childs[i]).disabled=bool;
		 setChildNodeStatus(childs[i],true);
		 i++;
		 }*/
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null){
						checkboxObj.disabled=bool;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = setChildNodeStatus(child,bool);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		//alert("setChildNodeStatus:::"+e.description);
	}
}

function Ldn(obj){
	isFloorExpand=0;
	///////////检查是否有子节点，如果是未加载状态，但是已经有子节点,就不再进行动态加载
	var results=new Array();
	results=getAllChildNodes(obj.parentNode.nextSibling,results);
	if(results.length>0 && obj.parentNode.getAttribute("dc")=="0"){


		obj.parentNode.setAttribute("dc", "1");//标识为第一次加载
		var nextSiblingNode =obj.parentNode.nextSibling.firstChild.nextSibling;
		var childNode = obj.parentNode.nextSibling.firstChild;
		var parentNode = obj.parentNode.nextSibling;
		parentNode.removeChild(nextSiblingNode);
		parentNode.removeChild(childNode);
	}else{
		LoadDynamicNode(obj);
	}
}


/*
 动态加载 子节点
 obj
 position 动态加载操作的触发点
 */
var position=null;
function LoadDynamicNode(obj,tempPosition){
	try{
		position=tempPosition;
		var divObj=obj.parentNode;
		if(divObj.getAttribute("dc")=="0"){//0表示未加载过子节点 同一个节点只能加载一次
			divObj.setAttribute("dc","1");//标识为第一次加载
			var treeObj=getObjByMainDivId(divObj.getAttribute("md"));
			//treeObj.currentNode=divObj;
			//判断是否有下一个节点
			var isNextNode="";
			var nObj=divObj.nextSibling.nextSibling;
			if(nObj!=null && nObj.getAttribute("nt")!=undefined && nObj.nodeName=="DIV"){
				isNextNode+="yes:";
			}else{
				isNextNode+="no:";
			}

			var parentObj=getParentNode(divObj);
			//判断所有父级节点是否有下级节点
			while(parentObj!=null){
				var nextObj=parentObj.nextSibling.nextSibling;
				if(nextObj!=null &&
						nextObj.getAttribute("nt")!=undefined && nextObj.nodeName=="DIV"){
					isNextNode+="yes:";
				}else{
					isNextNode+="no:";
				}
				parentObj=getParentNode(parentObj);
			}
			////////////////
			//得到节点信息
			///////////////得到数据结构 创建子节点  nodeInfo中存储着要传递的所有参数值
			var defaultKeys=treeObj.getDefaultKeys();
			var parentText=getNodeText(divObj);//encodeURIComponent(getNodeText(divObj));
			var checkboxObj=getInputNode(divObj);
			var checked="";
			if(checkboxObj!=null && checkboxObj.checked==true){
				checked="true";
			}else{
				checked="false";
			}
			var divId=divObj.getAttribute("md");
			var nodeInfo=getNodeInfo(divObj);
			var dataSource=treeObj.zzb_dataSource;
			var checkboxDisplay=treeObj.zzb_checkboxDisplay;
			var radioOrCheckBox=treeObj.zzb_RadioOrCheckBox;
			var parentRadioEnable=divObj.parentRadioEnable;
			var parentCheckboxEnable=treeObj.zzb_parentCheckboxEnable;
			var parentChildIsolate=treeObj.zzb_parentChildIsolate;
			var contextPath=zzbTreeConfig.zzb_contextPath;

			var ondblclick=treeObj.ondblclick;
			var userParameter=treeObj.userParameter;
			var checkboxclickDefault=treeObj.checkboxclickDefault;
			var dynamicAttri=getValueByName(divObj,"dynamicattri");
			var selectFunction=divObj.getAttribute("sf");
			$.ajax({
				async:false,
				type:"POST",
				url:contextPath+"/sys/hisunTree/ajax/loadTree",
				dataType : "json",
				headers:{
					"OWASP_CSRFTOKEN":'${sessionScope.OWASP_CSRFTOKEN}'
				},
				data:{
					"selectFunction":selectFunction,
					"contextPath":+contextPath,
					"parentChildIsolate":parentChildIsolate,
					"parentCheckboxEnable":parentCheckboxEnable,
					"parentRadioEnable":parentRadioEnable,
					"radioOrCheckBox":radioOrCheckBox,
					"checkboxDisplay":checkboxDisplay,
					"divId":divId,
					"dataSource":dataSource,
					"parentText":parentText,
					"parentkey":divObj.getAttribute("key"),
					"defaultKeys":defaultKeys,
					"isNextNode":isNextNode,
					"ondblclick":ondblclick,
					"userParameter":userParameter,
					"checkboxclickDefault":checkboxclickDefault,
					"dynamicAttri":dynamicAttri,
					"checked":checked,
					"cengCount":divObj.getAttribute("cs")},
				success:function(json){
					if( json.nodesHtml!=null && json.nodesHtml!=""){
						if(isFloorExpand==0){
							LoadDynamicAddNode(divObj,json.nodesHtml);
						}else if(isFloorExpand==1){
							DynamicFloorNode(divObj,json.nodesHtml);
						}else if(isFloorExpand==2){
							DynamicLoadExpandNode(divObj,json.nodesHtml);
						}
					}else{
						//删除loading节点
						delLoadingNode(divObj);
						changeOperateImg(divObj);
					}
				},
				error : function(){
					myLoading.hide();
					showTip("提示","出错了,请检查网络!",2000);
				}
			});
		}
		//////////////
	}catch(e){
		alert(" * LoadDynamicNode * "+e.description);
	}
}

/*
 设置操作图片对象

 */
function setOperateImg(obj,imgUrl){
	getImgChildNode(obj).setAttribute("src",imgUrl);
}

/*
 改变操作图片
 */
function changeOperateImg(obj){
	try{
		var cs=obj.getAttribute("cs");
		if(isExsitChildNode(obj)==true){//有子节点的情况

			if(cs=="1"){
				//alert("第一层 第一个子节点");
				if(isExsitLastChildNode(obj) && !isExsitFirstChildNode(obj)){//检查是否为最后一个子节点
					if(isExsitOpenNode(obj)){//展开的情况
						setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_closeIcon);
					}else{//没展开的情况
						setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_openIcon);
					}
				}else if(isExsitFirstChildNode(obj)){//检查是否第一个
					// alert("第一层 第一个子节点");
					if(isExsitOpenNode(obj)){
						setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_topCloseIcon);
					}else{
						setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_topOpenIcon);
					}
				}else{
					if(isExsitOpenNode(obj)){
						setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_closeIcon);
					}else{
						setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_openIcon);
					}
				}
			}else{
				if(isExsitOpenNode(obj)){//展开的情况
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_closeIcon);
				}else{//没展开的情况
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_openIcon);
				}
			}
		}else{//无子节点的情况下
			//第一层的情况下
			if(cs=="1"){
				if(isExsitLastChildNode(obj)){//检查是否为最后一个子节点
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_lastChildIcon);
				}else if(isExsitFirstChildNode(obj)){//检查是否第一个
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_topNoNodeIcon);
				}else{
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_noIcon);
				}
			}else{
				if(isExsitLastChildNode(obj)){//检查是否为最后一个子节点
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_lastChildIcon);
				}else{
					setOperateImg(obj,zzbTreeConfig.zzb_contextPath + zzbTreeConfig.zzb_imgPath+zzbTreeConfig.zzb_noIcon);
				}
			}
		}
	}catch(e){
		alert(" * changeOperateImg * " + e.description);
	}
}

/*
 通过标题设置复选框选中状态
 obj:为复选框对象
 */
function setCheckboxByTitle(obj,position){
	var treeObj=getObjByMainDivId(obj.getAttribute("md"));
	var checkboxObj=getInputNode(obj);
	//alert("position :" + position);
	if(checkboxObj!=null && treeObj.type=="2" && isChildNodeExist(obj)==false && position=="a"){
		setChecked(checkboxObj);
	}
}

/*
 为节点动态添加子节点
 */
function LoadDynamicAddNode(obj,nodeHtml){
	try{
		obj.nextSibling.innerHTML=nodeHtml;
		var treeObj=getObjByMainDivId(obj.getAttribute("md"));
		var checkboxObj=getInputNode(obj);
		if(checkboxObj!=null && checkboxObj.checked==true && treeObj.zzb_parentChildIsolate==true){
			if(noSelectedNodeByKeysChildBool!=true){
				selectedChild(obj);
			}
		}

		//自动加载动态节点下的子节点
		if(treeObj.zzb_auto_dynamic_load=="1" && obj.nextSibling.firstChild.getAttribute("dd")=="1"){
			autoDynamicNode(obj.nextSibling.firstChild);
		}
	}catch(e){
	}
}

var isFloorExpand=0;
var floor_keys="";
var keyArr_index=0;
var key_index=0;
var keysArr_keys=new Array();
var keyArr_index=0;
function DynamicFloorNode(obj,nodeHtml){
	try{
		obj.nextSibling.innerHTML=nodeHtml;
		var treeObj=getObjByMainDivId(obj.getAttribute("md"));
		treeObj.ExpandNodeByObj(obj);
		//var checkboxObj=getInputNode(obj);
		var floor_keys_arr=floor_keys.split(",");
		//if(floor_keys_arr.length==1){
		//alert("只有一个....");
		//treeObj.setCurrentNodeByNode(obj);
		//treeObj.setCheckboxSelected(results[0],true);
		//}
		key_index=key_index+1;
		//通过所有的key值循环展开节点对象，并将最后一个置为当前节点
		if(floor_keys_arr.length>key_index){
			var key=floor_keys_arr[key_index];
			if(key!=""){

				results=treeObj.getNodesByKeys(key,"1");
				if(results[0]!=undefined){
					if(results.length>0 && (floor_keys_arr.length-1)!=(key_index)){
						if(results[0].getAttribute("dd")=="1"){
							isFloorExpand=1;
							LoadDynamicNode(getImgChildNode(results[0]));
						}
					}
					//如果是最后一个key值设为当前节点
					if((floor_keys_arr.length-1)==(key_index)){
						treeObj.setCurrentNodeByNode(results[0]);
						//treeObj.setCheckboxSelected(results[0],true);
						setCheckboxSelectedType1(results[0]);
						keyArr_index=keyArr_index+1;
						isFloorExpand=0;
					}
				}
				//
			}
		}else{
			isFloorExpand=0;
			floor_keys="";
			key_index=0;
		}
	}catch(e){
		isFloorExpand=0;
		alert(" * DynamicFloorNode * " + e.description);
	}
}

ZZBTreeItem.prototype.setCheckboxSelectedType1 = function(obj){
	try{

		if(getInputNode(obj)==null){
			return false;
		}
		/*
		 if(SelectedNodeByKeysBool==true){
		 getInputNode(obj).sel="0";
		 }else{
		 getInputNode(obj).sel="1";
		 }
		 */

		if(noSelectedNodeByKeysChildBool==true){
			selectChecked(getInputNode(obj));
			expandByDiv(obj);
		}else{
			checkSelType1(getInputNode(obj));
			expandByDiv(obj);
		}
		changeNodeSelectAllStatus(obj);
	}catch(e){
		alert(" * setCheckboxSelectedType1 * " + e.description);
	}
}

var zzbtree_return_childList=new Array();
function setCheckboxSelectedType1(obj){
	try{

		if(getInputNode(obj)==null){
			ExpandNodeAndNextNode(obj);
			setCurrentNodeByNode(obj);
			expandByDiv(obj);
			return false;
		}

		/*
		 if(SelectedNodeByKeysBool==true){
		 getInputNode(obj).sel="0";
		 }else{
		 getInputNode(obj).sel="1";
		 }
		 */
		if(noSelectedNodeByKeysChildBool==true){
			selectChecked(getInputNode(obj));
			ExpandNodeAndNextNode(obj);
			setCurrentNodeByNode(obj);
			//expandByDiv(obj);
		}else{
			ExpandNodeAndNextNode(obj);
			//checkSelType1(getInputNode(obj));
			selectChecked(getInputNode(obj));
			setCurrentNodeByNode(obj);
			//expandByDiv(obj);
		}
		//alert("a111111");
		zzbtree_return_childList[zzbtree_return_childList.length]=obj;
		changeNodeSelectAllStatus(obj);
	}catch(e){
		alert("* setCheckboxSelectedType1 *" + e.description);
	}
}

function DynamicLoadExpandNode(obj,nodeHtml){
	try{
		;
		obj.nextSibling.innerHTML=nodeHtml;
		var treeObj=getObjByMainDivId(obj.getAttribute("md"));
		//var checkboxObj=getInputNode(obj);
		var floor_keys_arr=keysArr_keys[keyArr_index].split(",");
		key_index=key_index+1;
		if(floor_keys_arr.length==1){
			keyArr_index=keyArr_index+1;
			isFloorExpand=0;
			setCheckboxSelectedType1(obj);//选中命中的最后个节点
			treeObj.ExpandByKeys(keysArr_keys);
			return true;
		}
		//通过所有的key值循环展开节点对象，并将最后一个置为当前节点
		if(floor_keys_arr.length>key_index){
			treeObj.ExpandNodeByObj(obj);
			var key=floor_keys_arr[key_index];
			if(key!=""){
				results=treeObj.getNodesByKeys(key,"1");

				if(results[0]!=undefined){
					//如果不是最后一个节点进行判断，根据加载情况，决定是否要动态加载子节点
					if(results.length>0 && (floor_keys_arr.length-1)!=(key_index)){
						if(results[0].getAttribute("dd")=="1"){
							results[0].setAttribute("dc","0");
							isFloorExpand=2;
							LoadDynamicNode(getImgChildNode(results[0]));
						}
					}
					//最后一个节点
					if((floor_keys_arr.length-1)==(key_index)){
						setCheckboxSelectedType1(results[0]);//选中命中的最后个节点

						keyArr_index=keyArr_index+1;
						isFloorExpand=0;
						treeObj.ExpandByKeys(keysArr_keys);
					}



					/*
					 if((floor_keys_arr.length-1)==(key_index)){
					 //如果是最后一个将索引清零，否则继续展开节点操作

					 if(keyArr_index==(keysArr_keys.length-1)){
					 //alert("keyArr_index:" + keyArr_index);
					 //keyArr_index=0;
					 }else{
					 noSelectedNodeByKeysChildBool=false;
					 SelectedNodeByKeysBool=false;

					 keyArr_index=keyArr_index+1;
					 isFloorExpand=0;
					 treeObj.ExpandByKeys(keysArr_keys);
					 }
					 }
					 */

				}
				//
			}
		}else{
			isFloorExpand=0;
			floor_keys="";
			key_index=0;
		}
	}catch(e){
		isFloorExpand=0;
		alert(" * DynamicLoadExpandNode * " + e.description);
	}
}
/*
 通过逗号分开的，一组key值展开最后一个节点
 */
var noSelectedNodeByKeysChildBool=false;
ZZBTreeItem.prototype.ExpandByKeys = function(keysArr){
	try{
		noSelectedNodeByKeysChildBool=true;
		SelectedNodeByKeysBool=true;
		keysArr_keys=keysArr;
		key_index=0;
		if(keyArr_index<(keysArr_keys.length) && keysArr_keys.length>0){
			this.ExpandDynamicByKeys();
		}else{
			isFloorExpand=0;
			keyArr_index=0;
			noSelectedNodeByKeysChildBool=false;
			SelectedNodeByKeysBool=false;
			//展开命中的所有节点

			for(var i=0;i<keysArr_keys.length;i++){
				var tempKeyArr=keysArr_keys[i].split(",");
				var results=this.getNodesByKey(tempKeyArr[tempKeyArr.length-1],"1");
				expandByDiv(results[0]);
			}
		}
	}catch(e){
		alert("* ExpandByKeys *" + e.description);
	}
}
ZZBTreeItem.prototype.ExpandDynamicByKeys = function(){
	try{//
		var keysArr=keysArr_keys[keyArr_index].split(",");
		if(keysArr.length>0){
			var key=keysArr[key_index];
			var results=new Array();
			results=this.getNodesByKeys(key,"1");
			//alert(key);
			if(results.length>0){
				isFloorExpand=2;
				//没有加载过的情况下
				if(results[0].getAttribute("dc")=="0"){
					if((keysArr.length-1)==key_index){
						setCheckboxSelectedType1(results[0]);
						//进入下一组定位
						key_index=0;
						keyArr_index++;
						noSelectedNodeByKeysChildBool=false;
						SelectedNodeByKeysBool=false;
						this.ExpandByKeys(keysArr_keys);
					}else{
						LoadDynamicNode(getImgChildNode(results[0]));
					}
				}else{
					//已经加载的情况下
					var b=1;
					while(b==1){
						var key2=keysArr[key_index];
						var results2=this.getNodesByKey(key2,"1");

						if(results2.length>0){
							if(results2[0].getAttribute("dc")=="1"){//如果是已经加载的情况
								this.ExpandNodeByObj(results2[0]);
								if(key_index==(keysArr.length-1)){
									/////////////对最后一个进行选中
									var lastKey=keysArr[key_index];
									var lastResults=this.getNodesByKeys(lastKey,"1");
									if(lastResults!=null && lastResults.length>0){
										setCheckboxSelectedType1(lastResults[0]);
									}
									///////////////////////////
									//进入下一组定位
									key_index=0;
									keyArr_index++;
									//alert("进入下一组定位.....:" + keysArr_keys[keyArr_index]);
									noSelectedNodeByKeysChildBool=false;
									SelectedNodeByKeysBool=false;
									this.ExpandByKeys(keysArr_keys);
									b=0;
								}else{
									key_index++;
								}

							}else{//没有加载的情况
								if(key_index==(keysArr.length-1)){
									var lastKey=keysArr[key_index];
									var lastResults=this.getNodesByKeys(lastKey,"1");
									setCheckboxSelectedType1(lastResults[0]);
									b=0;

									//进入下一组定位
									key_index=0;
									keyArr_index++;
									noSelectedNodeByKeysChildBool=false;
									SelectedNodeByKeysBool=false;
									this.ExpandByKeys(keysArr_keys);
								}else{
									isFloorExpand=2;
									LoadDynamicNode(getImgChildNode(results2[0]));
									b=0;
								}

							}
						}
					}
				}
			}

		}
	}catch(e){
		alert(" * ExpandDynamicByKeys * " + e.description);
	}
}

/*用于单选
 通过逗号分开的，一组key值展开并选中最后一个节点
 */
var SelectedNodeByKeysBool=null;
ZZBTreeItem.prototype.ExpandSelectedNodeByKeys = function(keys,bool){
	try{
		floor_keys=keys;
		key_index=0;//用于记录当前操作key存储的索引值
		SelectedNodeByKeysBool=bool;
		this.ExpandDynamicNodeByKeys();
	}catch(e){
		alert("* ExpandSelectedNodeByKeys * " + e.description);
	}
}
ZZBTreeItem.prototype.ExpandDynamicNodeByKeys = function(){
	try{
		var keysArr=floor_keys.split(",");
		if(keysArr.length>0){
			var key=keysArr[key_index];
			var results=new Array();
			results=this.getNodesByKeys(key,"1");
			if(results.length>0){
				var dc=getValueByName(results[0],"dc");	//0表示未加载过子节点 同一个节点只能加载一次
				var dd=getValueByName(results[0],"dd");	//是否为动态加载 子节点 1为动态载 0为静态
				//alert("dd:" + dd + " dc:" + dc);
				//alert("results:" + results[0].getAttribute("nt") + " dc:" +dc+ " dd:" + dd);
				if(dd!="1" || dc=="1"){//如果是静态树或者是动态树但节点已经加载了子节点的情况下
					this.ExpandNextNodeByKeys();
				}else{
					//第一个
					if(key_index==(keysArr.length-1)){
						this.setCurrentNodeByNode(results[0]);
						setCheckboxSelectedType1(results[0]);
					}else{
						if(dd=="1"){
							isFloorExpand=1;
							LoadDynamicNode(getImgChildNode(results[0]));
						}
					}
				}
			}

		}
	}catch(e){
		alert("* ExpandDynamicNodeByKeys *" + e.description);
	}
}

ZZBTreeItem.prototype.ExpandNextNodeByKeys = function(){
	try{

		var floor_keys_arr=floor_keys.split(",");
		var key=floor_keys_arr[key_index];
		if(key!=""){
			var results=this.getNodesByKeys(key,"1");
			var dc=getValueByName(results[0],"dc");
			var dd=getValueByName(results[0],"dd");
			//如果是最后一个key值设为当前节点

			if((floor_keys_arr.length-1)==key_index){
				this.setCurrentNodeByNode(results[0]);
				//this.setCheckboxSelected(results[0],true);
				setCheckboxSelectedType1(results[0]);
			}else{//如果不是最后一个,还要继续展开
				if(dc=="1" || dd!="1"){
					key_index=key_index+1;
					//////如果是最后一个节点
					if((floor_keys_arr.length-1)==key_index){
						var lastKey=floor_keys_arr[key_index];
						var lastResults=this.getNodesByKeys(lastKey,"1");
						this.setCurrentNodeByNode(lastResults[0]);
						setCheckboxSelectedType1(lastResults[0]);
					}else{
						this.ExpandDynamicNodeByKeys();
					}
					////////////////

				}else{
					if(dd=="1"){
						isFloorExpand=1;
						LoadDynamicNode(getImgChildNode(results[0]));
					}
				}
			}
			//
		}

	}catch(e){
		alert(" * ExpandNextNodeByKeys *" + e.description);
	}
}

function selectedChildNode(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(child.getAttribute("ds")!="0"){
						selectChecked(checkboxObj);
					}
					//////////////////
				}
				child = child.nextSibling.nextSibling;
			}
		}
	}catch(e){
		alert(" * selectedChildNode *" + e.description);
	}
}

function selectedChild(obj){
	try{
		var tree=getObjByMainDivId(obj.getAttribute("md"));
		if(tree.type=="1"){
			var checkboxObj=getInputNode(obj);
			if(checkboxObj.checked==true){
				var childNum=getChildNodeByStatus(obj,true);
				if(childNum==0){
					selectNodesCheckbox(obj.nextSibling);//选中
					if(tree.zzb_parentChildIsolate){
						updateChildNodeSelAll(obj.nextSibling);
					}
				}
			}else{
				var selectedBool=isExistChildAllSelected(true,obj);
				if(selectedBool==true){
					//clearChildDefaultSelected(obj.nextSibling);
					clearNodesCheckbox(obj.nextSibling);//取消

				}
			}
		}else{
			selectedChildNode(obj.nextSibling);
		}
	}catch(e){
		alert(" * selectedChild * " + e.description);
	}
}
/*
 检查是否为同层中,最顶的一个节点
 */
function isExistTopNode(obj){

	try{
		var bool=false;
		if(obj.previousSibling==null){
			bool=true;
		}
		return bool;
	}catch(e){
		bool=true;
		alert("isExistTopNode : " + e.description);
	}

}

/*
 删除loading节点
 position　动态加载操作的触发点
 */
function delLoadingNode(obj){
	try{


		var childs=getDivChildNodes(obj);
		var nextSiblingNode = childs[0].nextSibling;
		var childNode = childs[0];
		var parentNode = childs[0].parentNode;
		parentNode.removeChild(nextSiblingNode);
		parentNode.removeChild(childNode);
		//childs[0].nextSibling.removeNode(true);
		//childs[0].removeNode(true);
		obj.nextSibling.style.display="none";
		obj.nextSibling.setAttribute("cct", "0");

		//对复选框进行处理
		setCheckboxByTitle(obj,position);
	}catch(e){
		//alert("delLoadingNode:"+e.description);
	}
}


///////////////////////////////////////////////////////////////////////////////////////////////////
function orc(obj){
	try{
		/////////////下拉框菜的配置信息设置
		noSelectedNodeByKeysChildBool=false;
		SelectedNodeByKeysBool=false;
		////////////
		var treeObj=getObjByMainDivId(obj.parentNode.getAttribute("md"));
		if(obj.getAttribute("open")=="no"){
			if(isExsitChildNode(obj.parentNode)==true){
				expand(obj);
			}

		}else{
			if(isExsitChildNode(obj.parentNode)==true){
				collapse(obj);
			}
		}
		//单击操作收缩复选框选择框数
		if(treeObj.openOnclick!=""){
			eval(treeObj.openOnclick);
		}
	}catch(e){
		alert("orc"+e.description);
	}
}

/*
 通过div对象 展开
 */
function expandByDiv(obj){
	try{
		if(obj.getAttribute("dd")=="1"){
			//LoadDynamicNode(getImgChildNode(obj));
			Ldn(getImgChildNode(obj));
			expand(getImgChildNode(obj));
		}else{
			expand(getImgChildNode(obj));
		}
	}catch(e){
		//alert("expandByDiv:: " + e.description);
	}
}

/*
 通过checkbox对象 展开
 */
function expandByCheckbox(checkboxObj){
	try{
		//isExsitChildNode
		if(checkboxObj.parentNode.getAttribute("dd")!="1"){
			expand(getImgChildNode(checkboxObj.parentNode));//展开了节点
		}else{//复选框动态加载
			LoadDynamicNode(getImgChildNode(checkboxObj.parentNode));
			expand(getImgChildNode(checkboxObj.parentNode))
		}

	}catch(e){
		alert("expandByDiv:: " + e.description);
	}
}

/*
 通过图片对象展开
 */
function expand(obj){
	try{

		///////展开操作
		if(obj!=null){
			var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
			//alert("设置线条.....");
			//setNodesLineByImgObj(obj);//子节线的调整
			var parentObj=obj.parentNode;
			//if(parentObj.nextSibling.cct=="1"){
			if(isExsitChildNode(obj.parentNode)){//展开操作
				parentObj.nextSibling.style.display = 'block';
				obj.setAttribute("open","yes");
				changeOperateImg(parentObj);
			}else{
				changeOperateImg(parentObj);
			}
//		   console.debug();
			if(tree.type=="1"){
				/*
				 var checkboxObj=getInputNode(parentObj);
				 if(checkboxObj.checked==true){
				 var childNum=getChildNodeByStatus(parentObj,true);
				 if(childNum==0){
				 selectNodesCheckbox(parentObj.nextSibling);//选中
				 }
				 }else{
				 var selectedBool=isExistChildAllSelected(true,parentObj);
				 if(selectedBool==true){
				 clearNodesCheckbox(parentObj.nextSibling);//取消
				 }
				 }
				 */
				var checkboxObj=getInputNode(parentObj);
				if(checkboxObj!=null && checkboxObj.checked==true){
					if(noSelectedNodeByKeysChildBool!=true){
						var childNum=getChildNodeByStatus(parentObj,true);
						if(childNum==0){
							//selectNodesCheckbox(obj.parentNode.nextSibling);
							selectNodesCheckboxUseType1(parentObj.nextSibling);//选中
							//这一步判断操作后的子节点是否全部选中
							var selectedBool=isExistChildAllSelected(true,parentObj);
							if(selectedBool==true){
								//alert("子节点已经全部选中....");
								selectNodesCheckbox(parentObj.nextSibling);
							}
							//clearLowerSelected(obj) selectNodesCheckboxUseType1(obj)
						}
					}

				}else{
					if(noSelectedNodeByKeysChildBool!=true){
						var selectedBool=isExistChildAllSelected(true,parentObj);
						if(selectedBool==true){
							//clearNodesCheckbox(obj.parentNode.nextSibling);//取消
							clearLowerSelected(parentObj.nextSibling);//取消
							//这一步判断操作后的子节点是否全部没有选中
							var childNum=getChildNodeByStatus(parentObj,true);
							if(childNum==0){
								//alert("子节点全部取消....");
								clearNodesCheckbox(parentObj.nextSibling);
							}
						}
					}
				}
			}
			//检查子节中最后一个节点

			/*if(!tree.zzb_DynamicLocal){
			 if(!isChildNodeExist(getLastChild(parentObj))){
			 if(getImgChildNode(getLastChild(parentObj))!=null){
			 getImgChildNode(getLastChild(parentObj)).src=zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_lastChildIcon;
			 }
			 }
			 }*/

			/*
			 不是最后一个子节点的时候图片处理
			 */
			/*var childNodes=getChildNodes(parentObj);
			 for(var i=0; i<childNodes.length;i++){
			 if(childNodes[i]!=getLastChild(parentObj)){
			 if(!isChildNodeExist(childNodes[i])){
			 getImgChildNode(childNodes[i]).src=zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_noIcon;
			 }
			 }
			 }*/
		}
		/////////////////
	}catch(e){
		alert("expand : " + e.description);
	}
}
/*
 */
function getLastChild(obj){

	try{
		var rObj=null;
		rObj=obj.nextSibling.lastChild.previousSibling
		return rObj;
	}catch(e){
		rObj=null;
	}

}

/*
 检测是否有子节点
 */
function isExsitChildNode(obj){
	try{
		if(getDivChildNodes(obj)!=null && getDivChildNodes(obj).length>0){
			return true;
		}else{
			return false;
		}
	}catch(e){
		alert(" * isExsitChildNode * " + e.description);
	}
}
/*
 检查是否为最后一个子节点
 */
function isExsitLastChildNode(obj){
	try{
		if(getNextBrotherNode(obj)==null){
			return true;
		}else{
			if(getNextBrotherNode(obj).nodeName=="DIV"){
				return false;
			}else{
				return true;
			}
		}
	}catch(e){
		alert(" * isExsitLastChildNode * " + e.description);
	}
}

/*
 检测子节点是否展开isExistOpenNode
 */
function isExsitOpenNode(obj){

	try
	{
		var bool=false;
		if(getImgChildNode(obj).getAttribute("open")=="yes"){
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}
	catch (e)
	{
		bool=false;
		alert("isExsitOpenNode:::" + e.description);
	}
}

/*
 检测子节点是否展开 展开和没子节点true 没展开false
 */
function isExsitOpenOrChildNode(obj){

	try
	{
		var bool=false;
		if(getImgChildNode(obj).getAttribute("open")=="yes"){
			bool=true;
		}else{
			if(isExsitChildNode(obj)){
				bool=false;
			}else{
				bool=true;
			}
		}
		return bool;
	}
	catch (e)
	{
		bool=false;
		//alert("isExsitOpenOrChildNode:::" + e.description);
	}
}

/*
 检查是否为第一个子节点
 */
function isExsitFirstChildNode(obj){
	try{
		if(getPreviousBrotherNode(obj)==null){
			return true;
		}else{
			if(getPreviousBrotherNode(obj).nodeName=="DIV"){
				return false;
			}else{
				return true;
			}
		}
	}catch(e){
		alert(" * isExsitFirstChildNode * " + e.description);
	}
}

//操作类型为1的情况
function eaType1(obj){
	try{
		if(obj.parentNode.getAttribute("tc")=="0" || obj.parentNode.getAttribute("tc")=="3"){
			//如果第一次单击的时候已经选中，直接执行展开操作
			if(getInputNode(obj.parentNode).checked==true){
				//如果已经展开了,就进行收缩操作,并设置单击次数为2
				if(getImgChildNode(obj.parentNode).getAttribute("open")=="yes"){
					collapseByDiv(obj.parentNode);
					obj.parentNode.setAttribute("tc","2");
				}else{
					//如果是收缩的状态下,就进行收缩操作,并设置单击次数为3
					expandByDiv(obj.parentNode);
					obj.parentNode.setAttribute("tc","3");
				}
			}else{
				//如果是未选中的状态下的,设置状态选中，并根据节点展开的情况对单击次数进行设置
				checkSelType1(getInputNode(obj.parentNode),true);
				if(getImgChildNode(obj.parentNode).getAttribute("open")=="yes"){
					obj.parentNode.setAttribute("tc","3");
				}else{
					obj.parentNode.setAttribute("tc","1");
				}

			}
		}else if(obj.parentNode.getAttribute("tc")=="1"){
			//第二次单击
			if(getInputNode(obj.parentNode).checked==true){

				if(getImgChildNode(obj.parentNode).getAttribute("open")=="yes"){
					//如果是收缩的状态下,就进行收缩操作,并设置单击次数为3
					collapseByDiv(obj.parentNode);
					obj.parentNode.setAttribute("tc","0");
				}else{
					//如果已经展开了,就进行收缩操作,并设置单击次数为2
					expandByDiv(obj.parentNode);
					obj.parentNode.setAttribute("tc","2");
				}
			}else{
				obj.parentNode.setAttribute("tc","0");
				ea(obj);
			}
		}else if(obj.parentNode.getAttribute("tc")=="2"){
			//如果第三次单击的时候已经选中，直接执行展开操作
			if(getInputNode(obj.parentNode).checked==true){
				if(getImgChildNode(obj.parentNode).getAttribute("open")=="yes"){
					collapseByDiv(obj.parentNode);
					obj.parentNode.setAttribute("tc","2");
				}else{
					expandByDiv(obj.parentNode);
					obj.parentNode.setAttribute("tc","3");
				}
			}else{
				checkSelType1(getInputNode(obj.parentNode),true);
				obj.parentNode.setAttribute("tc","3");
			}
		}
	}catch(e){
		//alert(" * eaType1 * " + e.description);
	}
}

function checkType1(obj){
	try{
		var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
		//如果是动态加载了节点并且节点已经被加载
		if(obj.parentNode.getAttribute("dd")=="1"){//子节点为零的情况下
			//节点已经加载的情况下
			if(obj.parentNode.getAttribute("dc")=="1"){
				var childArr=tree.getTreeParentChildNodes(obj.parentNode);
				//动态加载完后没有子节点
				if(childArr.length==0){
					var checkboxdisplay=getValueByName(obj.parentNode,"checkboxdisplay");
					if(checkboxdisplay=="1" && getInputNode(obj.parentNode).checked==false){
						selectChecked(getInputNode(obj.parentNode));
					}else{
						clearChecked(getInputNode(obj.parentNode));
					}
				}else{
					//有子节点
					eaType1(obj);
				}
			}else{
				//节点未加载的情况下
				eaType1(obj);
			}
		}else{
			//静态树的情况下
			//如果没有子节点
			var childArr=tree.getTreeParentChildNodes(obj.parentNode);
			if(childArr.length==0){
				var checkboxdisplay=getValueByName(obj.parentNode,"checkboxdisplay");
				if(checkboxdisplay=="1" && getInputNode(obj.parentNode).checked==false){
					selectChecked(getInputNode(obj.parentNode));
				}else{
					clearChecked(getInputNode(obj.parentNode));
				}
			}else{
				eaType1(obj);
			}
		}
		if(tree.lowerSelectedAllWithSuperior==true){
			checkBrotherNodeSelect(obj.parentNode);
		}

		if(tree.aclickDefault!=""){
			eval(tree.aclickDefault);
		}

		//检查处理子节点选中状态
		changeNodeSelectAllStatus(obj.parentNode);
	}catch(e){
		alert(" * checkType1 * " + e.description);
	}
}
function ea(obj){

	/////////////下拉框菜的配置信息设置
	noSelectedNodeByKeysChildBool=false;
	SelectedNodeByKeysBool=false;
	////////////
	var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
	//树操作类型为1的情况下
	//alert("type:" + tree.type);
	if(tree.type=="1"){
		checkType1(obj);
	}else if(tree.type=="2"){//如果没有子节点的情况下，第一次单击标题选中复选框，第二次取消选中.
		if(isChildNodeExist(obj.parentNode)){
			expandByA(obj);
		}else{
			var dd=getValueByName(obj.parentNode,"dd");
			var dc=getValueByName(obj.parentNode,"dc");

			if(dd=="0"){//如果是静态的节点
				setChecked(getInputNode(obj.parentNode));
			}else{//如果是动态的节点子节点已经加载
				if(dc=="1"){
					setChecked(getInputNode(obj.parentNode));
				}
			}
		}
	}else{
		expandByA(obj);
	}
	return false;
}

function checkSelType1(obj,bool){
	try{
		if(obj!=null){
			var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));

			///单击标题的情况下/////////
			if(bool==true){
				setChecked(obj);
			}
			////////////
			if(tree.zzb_RadioOrCheckBox){//复选
				//////////////////////////
				if(tree.zzb_parentChildIsolate){
					if(getDivChildNodes(obj.parentNode).length>0){//先判断是否为父节点
						///////处理父节点//////
						if(tree.zzb_parentCheckboxEnable){//父节点可以选中
							///////处理子节点/////////
							if(tree.zzb_parentChildIsolate){
								//在设置选择父会影响子节点的情况下
								if(obj.checked==true){
									var childNum=getChildNodeByStatus(obj.parentNode,true);
									if(childNum==0){

										if(tree.parentSelectedWithChild==true){
											selectNodesCheckboxUseType1(obj.parentNode.nextSibling);//选中
										}

										//这一步判断操作后的子节点是否全部选中
										var selectedBool=isExistChildAllSelected(true,obj.parentNode);
										if(selectedBool==true){
											//alert("子节点已经全部选中....");
											if(tree.parentSelectedWithChild==true){
												selectNodesCheckbox(obj.parentNode.nextSibling);
											}
											updateChildNodeSelAll(obj.parentNode.nextSibling);
										}
										//clearLowerSelected(obj) selectNodesCheckboxUseType1(obj)
									}
								}else{
									var selectedBool=isExistChildAllSelected(true,obj.parentNode);
									if(selectedBool==true){
										//clearNodesCheckbox(obj.parentNode.nextSibling);//取消
										if(tree.parentClearWithChild==true){
											clearLowerSelected(obj.parentNode.nextSibling);//取消
										}
										//这一步判断操作后的子节点是否全部没有选中
										var childNum=getChildNodeByStatus(obj.parentNode,true);
										if(childNum==0){
											//alert("子节点全部取消....");
											if(tree.parentClearWithChild==true){
												clearNodesCheckbox(obj.parentNode.nextSibling);
											}
										}
									}
								}
							}
						}else{//父节子不能选中
							clearChecked(obj);
						}
						/////////////////////////
					}
					//检查兄弟节点选中状态,用于决定父节点是否要变灰
					if(obj.checked==false){
						checkBrotherNodeSelect(obj.parentNode);
					}else{
						if(tree.lowerSelectedAllWithSuperior==true){
							checkBrotherNodeSelect(obj.parentNode);
						}
					}
				}else{
					//父节点子和子节点不相互影响
					if(getDivChildNodes(obj.parentNode).length>0){
						if(!tree.zzb_parentCheckboxEnable){
							clearChecked(obj);
						}
					}
				}

				//设置复选框选中，标题得到焦点
				if(tree.selectedTitleByCheckbox=="true"){
					tree.setCurrentNodeByNode(obj.parentNode);
				}
			}else{//单选
				if(getDivChildNodes(obj.parentNode).length>0){//如果是父节点检查父节点是否可以选中
					if(tree.zzb_parentRadioEnable){
						clearAllOtherSelected(getMainDivByMainDivId(obj.parentNode.getAttribute("md")),obj);//清除其它节点
					}
				}else{
					clearAllOtherSelected(getMainDivByMainDivId(obj.parentNode.getAttribute("md")),obj);//清除其它节点
				}
			}

			//检查处理子节点选中状态
			changeNodeSelectAllStatus(obj.parentNode);
		}
	}catch(e){
		alert(" * checkSelType1 * " + e.description);
	}
}
var gzzzb_tree_node_obj=null;
/*
 单击单链接对象展开子节点
 */
function expandByA(obj){
	try{
		if(obj!=null){
			var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
			var bool=false;
			if(!isNodeExistOpen(obj.parentNode) && isChildNodeExist(obj.parentNode)){//子节点隐藏的情况下 并且 子节点存在的情况下
				if(!tree.zzb_OnlyImgExpand){
					expandByDiv(obj.parentNode);
					eval(obj.href);//执行链接操作
					/////////////////////////////////////////////////////
				}
				if(tree.zzb_checkboxDisplay=="" && tree.seletedCheckboxByTitle=="1"){

					if(obj.parentNode.getAttribute("gi")=="1"){
						grayCheckSel(getInputNode(obj.parentNode));
					}else{
						if(tree.getCheckboxStatus(obj.parentNode)!="disabled"){

							if(tree.firstSelectedByTitle=="true"){
								if(getInputNode(obj.parentNode).checked==false){
									checkSel(getInputNode(obj.parentNode),true);
								}
							}else{
								checkSel(getInputNode(obj.parentNode),true);
							}
						}
					}
				}
			}else{//子节点展开的情况下
				//设置复选框选中状态
				if(tree.zzb_checkboxDisplay=="" && tree.seletedCheckboxByTitle=="1"){

					if(obj.parentNode.getAttribute("gi")=="1"){
						grayCheckSel(getInputNode(obj.parentNode));
					}else{
						if(tree.getCheckboxStatus(obj.parentNode)!="disabled"){

							if(tree.firstSelectedByTitle=="true"){

								if(getValueByName(obj.parentNode,"checkboxdisplay")=="1" && getInputNode(obj.parentNode).checked==false){
									checkSel(getInputNode(obj.parentNode),true);
								}
							}else{
								checkSel(getInputNode(obj.parentNode),true);
							}
						}

					}
				}


				gzzzb_tree_node_obj=obj.parentNode;
				_sanTreeNode_obj=obj;//将当前对象赋于,公用变量
				eval(obj.getAttribute("jsf"));//执行函数
				eval(obj.href);//执行链接操作

			}
			if(tree.aclickDefault!=""){
				eval(tree.aclickDefault);
			}

			//检查处理子节点选中状态
			changeNodeSelectAllStatus(obj.parentNode);
		}

	}catch(e){
		//alert(" * expandByA * "+e.description);
	}
	//根据返回的true 还是 false 来确定是否执行链接

	return false;
}

function dea(obj){
	return dbexpandByA(obj);
}



/**
 在复选框发生变化时触发此事件
 */
function cch(obj){
	/*
	 var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
	 var parentObj=tree.getTreeParentNode(obj.parentNode);
	 var bool=isExistChildSelect(tree.currentNode);
	 */
	/*
	 if(parentObj!=null){
	 var checkbox=getInputNode(parentObj);
	 if(obj.checked==false){
	 //var bool=isExistChildSelect(parentObj);
	 alert(parentObj.getAttribute("nt")+"　选中状态为:" + bool);
	 //parentObj.selAll="0";//0代表子节点没有全部选中
	 }
	 }
	 */
}

/*
 双击单链接对象展开子节点
 */
function dbexpandByA(obj){
	/*try{

	 if(obj!=null){
	 var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
	 var bool=false;
	 if(!isNodeExistOpen(obj.parentNode) && isChildNodeExist(obj.parentNode)){//子节点隐藏的情况下 并且 子节点存在的情况下
	 if(!tree.zzb_OnlyImgExpand){
	 expandByDiv(obj.parentNode);
	 obj.parentNode.setAttribute("clickCount","1");//
	 /////////////////////////////////////如果是动态加载,就执行动态加载操作
	 if(obj.parentNode.getAttribute("dd")=="1"){
	 LoadDynamicNode(getImgChildNode(obj.parentNode));
	 }
	 /////////////////////////////////////////////////////
	 }
	 }else{//子节点展开的情况下
	 //if(obj.parentNode.clickCount=="1" || obj.parentNode.clickCount=="0" || !isChildNodeExist(obj.parentNode)){//是第二次单击情况下
	 //	_sanTreeNode_obj=obj;//将当前对象赋于,公用变量
	 //	eval(obj.jsf);//执行函数

	 //	obj.parentNode.clickCount="2";//代表已经点击第二次
	 //	bool=true;//执行链接
	 //}

	 _sanTreeNode_obj=obj;//将当前对象赋于,公用变量
	 eval(obj.jsf);//执行函数
	 //设置复选框选中状态
	 //if(obj.parentNode.checkboxDisplay==""){
	 //  checkSel(getInputNode(obj.parentNode));
	 //}
	 //
	 }
	 }
	 }catch(e){
	 alert("expandByA"+e.description);
	 }
	 */
	//根据返回的true 还是 false 来确定是否执行链接
	//return false;
}


/*
 通过图片对象收缩
 */
function collapse(obj){
	try{
		var parentObj=obj.parentNode;
		parentObj.nextSibling.style.display = 'none';
		obj.setAttribute("open","no");
		changeOperateImg(parentObj);

	}catch(e){
		alert(" * collapse * " + e.description);
	}
}

/*
 通过链接对象收缩
 */
function collapseByA(obj){
	try{
		collapse(getImgChildNode(obj.parentNode));
	}catch(e){
		alert("* collapseByA * " + e.description);
	}
}

/*
 通过div对象 收缩
 */
function collapseByDiv(obj){
	try{
		collapse(getImgChildNode(obj));
	}catch(e){
		alert("* collapseByDiv * " + e.description);
	}
}

/*
 通过div对象,让命中节点选中
 */
function divFocus(obj){
	try{
		setDivColor(obj,obj.getAttribute("md"));
		var treeObj=zzbTreeHandler.zzb_TreeObjs[obj.getAttribute("md")];

		if(treeObj.currentNode!=null &  treeObj.currentNode!=obj){
			//把旧的默认选中对象还原
			clearColor(treeObj.currentNode,obj.getAttribute("md"));
		}
		//保存当前div对象
		treeObj.currentNode=obj;
	}catch(e){
		alert("* divFocus * " + e.description);
	}
}

function nf(obj){
	nodeFocus(obj);
}
/*
 通过链接对象,让命中节点选中

 */
function nodeFocus(obj){
	try{
		if(obj!=null){
			var treeObj=getObjByMainDivId(obj.parentNode.getAttribute('md'));
			if(treeObj.zzb_RadioOrCheckBox == false){//单选的情况下
				if(obj.parentNode.getAttribute('rn')=="0"){//单行情况下节点是否可以选中
					obj.blur();
				}else{
					setCurrentNode(obj.parentNode);//设置成为当前节点
				}
			}else{//复选的情况下
				setCurrentNode(obj.parentNode);//设置成为当前节点
			}
			//保存当前div对象
		}
	}catch(e){
		alert("* nodeFocus * " + e.description);
	}
}
/*
 设置成为当前节点
 */
function setCurrentNode(obj){
	try{
		var treeObj=zzbTreeHandler.zzb_TreeObjs[obj.getAttribute('md')];//得到树对象
		//alert(treeObj.currentNode.getAttribute("key"));
		if(treeObj.currentNode!=null){
			//alert("上一个默认节点...." + treeObj.currentNode.getAttribute("key"));
			//alert(treeObj.currentNode);
			//把旧的默认选中对象还原
			clearColor(treeObj.currentNode,obj.getAttribute('md'));

		}
		setDivColor(obj, obj.getAttribute('md'));
		//alert("当前节点....");
		treeObj.currentNode = obj;
	}catch(e){
		alert(" * setCurrentNode * " + e.description);
	}
}

/*
 设置当前树,或者不是当前树
 */
function setTreeFocus(bool,md){
	try{
		var treeObj=zzbTreeHandler.zzb_TreeObjs[md];//得到树对象
		if(bool){
			//在当前节点不为空的情况下,做以下操作
			if(treeObj.currentNode!=null){
				treeObj.focusColor=treeObj.true_focus;//设置为当前树的时候,当前节点的颜色
				setDivColor(treeObj.currentNode,md);
			}
		}else{
			//在当前节点不为空的情况下,做以下操作
			if(treeObj.currentNode!=null){
				treeObj.focusColor=treeObj.false_focus;//设置为非当前树的时候,当前节点的颜色
				setDivColor(treeObj.currentNode,md);
			}
		}
	}catch(e){
		alert(" * setTreeFocus * " + e.description);
	}
}
/*
 检测子节点是否展开
 */
function isNodeExistOpen(obj){

	try
	{
		var bool=false;
		if(obj.nextSibling.style.display=="block"){
			bool=true;
		}
		return bool;
	}
	catch (e)
	{
		bool=false;
		alert("* isNodeExistOpen * " + e.description);
	}
}

/*
 检测是否有子节点

 */
function isChildNodeExist(obj){

	try
	{
		var bool=false;
		if(obj!=null){
			if(obj.nextSibling.childNodes.length>0){
				bool=true;
			}
		}
		return bool;
	}
	catch (e)
	{
		bool=false;
	}
}

/*
 设置选中对象颜色
 */
function setDivColor(obj,md){
	try{
		//#000033 focusColor #FFFFFF	   "#C0C0C0"
		var tree=getObjByMainDivId(md);
		getAChildNode(obj).style.backgroundColor=tree.focusColor;
		getAChildNode(obj).style.color="#FFFFFF";
	}catch(e){
		alert(" * setDivColor * " + e.description);
	}
}

/*
 取消选中对象颜色
 */
function clearColor(obj,md){
	try{
		getAChildNode(obj).style.backgroundColor="";
		getAChildNode(obj).style.color="#000000";
	}catch(e){
		//alert("clearColor : " + e.description);
	}
}

/////////////start 添加线操作函数////////////////////////////////////////////////////////////
function getLineImgObjs(obj){
	try{
		var arr=new Array();
		for(var i=0;i<obj.childNodes.length;i++){
			if(obj.childNodes[i].id.indexOf("indent")!=-1){
				arr[arr.length]=obj.childNodes[i];
			}
		}
	}catch(e){
		alert("* getLineImgObjs * "+e.description);
	}
	return arr;
}

//////////////////////////////////////////////////////
/*
 展开时 通过 图片对象 添加线

 */
function setNodesLineByImgObj(obj){
	try{
		/* if(obj!=null){
		 var childNodes=getDivChildNodeByImg(obj).nextSibling.childNodes;
		 if(childNodes!=null && childNodes.length>0){
		 for(var i=0;i<childNodes.length;i++){
		 //alert("id: " + getNodeText(childNodes[i]));
		 //alert("id: " + childNodes[i]+ "text: " + geNodeText(childNodes[i]));
		 addLine(childNodes[i]);
		 setNodesLineByImgObj(getImgChildNode(childNodes[i]));
		 i++;
		 }
		 }
		 }*/
	}catch(e){
		alert("* setNodesLineByImgObj * "+e.description);
	}
}

/*
 展开时 通过 链接对象 添加线
 */
function setNodesLineByAObj(obj){
	try{
		//alert("setNodesLineByAObj");
		var parentObj=obj.parentNode;
		if(parentObj!=null){
			var plusImgObj=getImgChildNode(parentObj);
			if(plusImgObj!=null){
				setNodesLineByImgObj(plusImgObj);
			}
		}
	}catch(e){
		alert("* setNodesLineByImgObj * "+e.description);
	}
}

/*
 展开时 通过 DIV对象 添加线
 */
function setNodesLineByDivObj(obj){
	try{
		/* var childNodes=obj.nextSibling.childNodes;
		 if(childNodes!=null){
		 for(var i=0;i<childNodes.length;i++){
		 addLine(childNodes[i]);
		 //
		 setNodesLineByDivObj(childNodes[i]);
		 //
		 i++;
		 }
		 }*/
	}catch(e){
		alert("* setNodesLineByDivObj * "+e.description);
	}
}

/*
 展开时添加上下连接线
 bool 1 obj为div对象 0 obj为Img对象

 */
function addLine(obj){
	try{
		//得到图片修改的数组
		/*var cs=parseInt(obj.cs);//得到层次数
		 var ArrImgConfig=new Array(cs-1);//记录当前展开节点的图片数
		 var pObj=obj;
		 for(var i=cs-1;i>0;i--){
		 pObj = getParentNode(pObj);
		 if(getNextBrotherNode(pObj)!=null && getNextBrotherNode(pObj).nodeName=="DIV"){//检查下一个兄弟节点是否存在
		 ArrImgConfig[i-1]=1;//1 表示存在加

		 }else{
		 ArrImgConfig[i-1]=0;//0 表示不存在
		 }
		 }
		 ///////////////
		 //修改图片
		 var arrImg=getLineImgObjs(obj);//得到节点下的所有图片对象
		 for(var j=0;j<arrImg.length;j++){
		 if(ArrImgConfig[j]==1){//根据以上标识 确定是否对图片进行更改
		 arrImg[j].src=zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_lineIcon;//
		 }else{
		 arrImg[j].src=zzbTreeConfig.zzb_contextPath+zzbTreeConfig.zzb_nullIcon;
		 }
		 }*/
	}catch(e){
		alert("* addLine * "+e.description);
	}
}

/*
 obj	:为根节点
 NodeID :要找的节点ID
 resultObjArr: 用于收集搜索得到对象的,数组变量
 view:是否定位,并默认选中第一个

 */
var index=0;
//
function getQueryExpandByName(obj,nodeName,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					/////////////////////////////

					if(child!=null && child.getAttribute("nt")!=undefined){
						if (child.getAttribute("nt").toLowerCase().indexOf(nodeName.toLowerCase())!=-1)
						{
							resultObjArr[resultObjArr.length] = child;
						}
					}
					////////////////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryExpandByName(child,nodeName,resultObjArr);
					}

				}else{
					break;
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryExpandByName * " + e.description );
	}
	return resultObjArr;
}

/*
 obj	:为根节点
 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryInputValueAndNodeInfo(obj,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("dd")!="1"){
						var checkboxObj=getInputNode(child);
						if(checkboxObj!=null && checkboxObj.checked==true){
							var infoArr=new Array();
							infoArr[infoArr.length]=child.getAttribute("iv");
							infoArr[infoArr.length]=child.getAttribute("key");
							infoArr[infoArr.length]=child.getAttribute("hc");
							infoArr[infoArr.length]=getNodeText(child);
							infoArr[infoArr.length]=child.getAttribute("da");
							resultObjArr[resultObjArr.length]=infoArr;
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryInputValueAndNodeInfo(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}


	}catch(e){
		alert("* getQueryInputValueAndNodeInfo * " + e.description);
	}
	return resultObjArr;
}

/*
 obj	:为根节点
 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryInputValueAndNodeUrlInfo(obj,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.checked==true){
						var infoArr=new Array();
						infoArr[infoArr.length]=child.getAttribute("iv");
						infoArr[infoArr.length]=child.getAttribute("key");
						infoArr[infoArr.length]=child.getAttribute("hc");
						infoArr[infoArr.length]=getNodeNameUrl(child);
						resultObjArr[resultObjArr.length]=infoArr;

					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryInputValueAndNodeUrlInfo(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryInputValueAndNodeUrlInfo * " + e.description);
	}
	return resultObjArr;
}

/*
 得到选中的所有节点信息
 iv,key,hc,NodeNameUrl,da
 */
function getQuerySelectedAllInfo(obj,resultObjArr,infoName){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.checked==true){
						var infoArr=new Array();
						if(infoName.indexOf("iv")!=-1)
							infoArr[infoArr.length]=child.getAttribute("iv");
						if(infoName.indexOf("key")!=-1)
							infoArr[infoArr.length]=child.getAttribute("key");
						if(infoName.indexOf("hc")!=-1)
							infoArr[infoArr.length]=child.getAttribute("hc");
						if(infoName.indexOf("NodeNameUrl")!=-1)
							infoArr[infoArr.length]=getNodeNameUrl(child);
						if(infoName.indexOf("da")!=-1)
							infoArr[infoArr.length]=child.getAttribute("da");
						resultObjArr[resultObjArr.length]=infoArr;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQuerySelectedAllInfo(child,resultObjArr,infoName);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQuerySelectedAllInfo * " + e.description);
	}
	return resultObjArr;
}

/*
 得到选中的所有节点信息 , 包括灰勾的节点信息
 iv,key,hc,NodeNameUrl,da,checkStatus
 */
function getQueryAllSelectedOrGrayInfo(obj,resultObjArr,infoName){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var status=getCheckStatusByNodeObj(child);
					if(status=="true" || status=="gray"){
						var infoArr=new Array();
						if(infoName.indexOf("iv")!=-1)
							infoArr[infoArr.length]=child.getAttribute("iv");
						if(infoName.indexOf("key")!=-1)
							infoArr[infoArr.length]=child.getAttribute("key");
						if(infoName.indexOf("hc")!=-1)
							infoArr[infoArr.length]=child.getAttribute("hc");
						if(infoName.indexOf("NodeNameUrl")!=-1)
							infoArr[infoArr.length]=getNodeNameUrl(child);
						if(infoName.indexOf("da")!=-1)
							infoArr[infoArr.length]=child.getAttribute("da");
						if(infoName.indexOf("checkStatus")!=-1)
						{
							infoArr[infoArr.length]=getCheckStatusByNodeObj(child);
						}
						resultObjArr[resultObjArr.length]=infoArr;
					}

					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryAllSelectedOrGrayInfo(child,resultObjArr,infoName);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryAllSelectedOrGrayInfo * " + e.description);
	}
	return resultObjArr;
}

/*
 得到选中的所有节点信息 , 包括灰勾的节点信息
 iv,key,hc,NodeNameUrl,da,checkStatus
 */
function getQueryAllGrayInfo(obj,resultObjArr,infoName){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(getCheckStatusByNodeObj(child)=="gray"){
						var infoArr=new Array();
						if(infoName.indexOf("iv")!=-1)
							infoArr[infoArr.length]=child.getAttribute("iv");
						if(infoName.indexOf("key")!=-1)
							infoArr[infoArr.length]=child.getAttribute("key");
						if(infoName.indexOf("hc")!=-1)
							infoArr[infoArr.length]=child.getAttribute("hc");
						if(infoName.indexOf("NodeNameUrl")!=-1)
							infoArr[infoArr.length]=getNodeNameUrl(child);
						if(infoName.indexOf("da")!=-1)
							infoArr[infoArr.length]=child.getAttribute("da");
						if(infoName.indexOf("checkStatus")!=-1)
						{
							infoArr[infoArr.length]=getCheckStatusByNodeObj(child);
						}
						resultObjArr[resultObjArr.length]=infoArr;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryAllGrayInfo(child,resultObjArr,infoName);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryAllGrayInfo * " + e.description);
	}
	return resultObjArr;
}

/*
 得到所有节点信息
 iv,key,hc,NodeNameUrl,da,checkStatus
 */

function getQueryAllInfo(obj,resultObjArr,infoName){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////

					if(child.getAttribute("nt")!=undefined){//用于标识是否动态加载的未加载节点
						var infoArr=new Array();
						if(infoName.indexOf("iv")!=-1)
							infoArr[infoArr.length]=child.getAttribute("iv");
						if(infoName.indexOf("key")!=-1)
							infoArr[infoArr.length]=child.getAttribute("key");
						if(infoName.indexOf("hc")!=-1)
							infoArr[infoArr.length]=child.getAttribute("hc");
						if(infoName.indexOf("NodeNameUrl")!=-1)
							infoArr[infoArr.length]=getNodeNameUrl(child);
						if(infoName.indexOf("da")!=-1)
							infoArr[infoArr.length]=child.getAttribute("da");
						if(infoName.indexOf("checkStatus")!=-1)
						{
							infoArr[infoArr.length]=getCheckStatusByNodeObj(child);
						}
						resultObjArr[resultObjArr.length]=infoArr;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryAllInfo(child,resultObjArr,infoName);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryAllInfo * " + e.description);
	}
	return resultObjArr;
}

/*
 如果层大于0 并且cengCount有效的情况下,返回true
 */
function isExistBigCengCounto(obj){
	try{
		var bool=false;
		if(isNaN(parseInt (obj.getAttribute("cs")))){
			bool=false;
		}else if(parseInt (obj.getAttribute("cs"))>0){
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}catch(e){
		return false;
	}
}

/*
 得到所有节点
 iv,key,hc,NodeNameUrl,da,checkStatus
 */
function getQueryAllNodes(obj,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("nt")!=undefined && isExistBigCengCounto(child) && child.getAttribute("icd")=="1"){
						resultObjArr[resultObjArr.length]=child;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryAllNodes(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryAllNodes *" + e.description);
	}
	return resultObjArr;
}

/*
 选中所有节点
 iv,key,hc,NodeNameUrl,da,checkStatus
 */
function selectedAllNodes(obj,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("nt")!=undefined && isExistBigCengCounto(child) && child.getAttribute("icd")=="1"){
						var checkboxObj=getInputNode(child);
						if(checkboxObj!=null){
							selectChecked(checkboxObj);
							resultObjArr[resultObjArr.length]=child;
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = selectedAllNodes(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryAllNodes * " + e.description);
	}
	return resultObjArr
}

/*
 选中所有节点
 iv,key,hc,NodeNameUrl,da,checkStatus
 */
function clearAllNodes(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("nt")!=undefined && isExistBigCengCounto(child) && child.getAttribute("icd")=="1"){
						var checkboxObj=getInputNode(child);
						if(checkboxObj!=null){
							clearChecked(checkboxObj);
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = clearAllNodes(child);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryAllNodes * " + e.description);
	}
}

/*
 得到节点名的全部径 a/a1/a2
 */
function getNodeNameUrl(obj,prefix){
	try{
		var pfix="\\";
		if(prefix!=null && prefix!="" && prefix.length>0){
			pfix=prefix;
		}
		var returnStr=getNodeText(obj);
		var cs=parseInt(obj.getAttribute("cs"))-1;
		var tempObj=obj;
		for(var i=cs;i>0;i--){
			tempObj=getParentNode(tempObj);

			if(tempObj!=null){
				returnStr=getNodeText(tempObj)+pfix+returnStr;
			}
		}
		return returnStr;
	}catch(e){
		alert("* getNodeNameUrl * " + e.description);
	}

}

/*
 obj	:为根节点
 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryInputValue(obj,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.checked==true){
						getInputNode(child,resultObjArr)
						resultObjArr[resultObjArr.length]=child.getAttribute("iv");
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryInputValue(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryInputValue * " + e.description);
	}
	return resultObjArr;
}

/*
 obj	:为根节点
 key :要找的节点键值

 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryExpandByKey(obj,key,resultObjArr,matching){
	try{

		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.getAttribute("nt")!=undefined && child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("key")!=null && child.getAttribute("key")!=undefined){
						if(matching=="1"){
							if (child.getAttribute("key").toLowerCase()==key.toLowerCase())
							{
								resultObjArr[resultObjArr.length] = child;
							}
						}else{
							if (child.getAttribute("key").toLowerCase().indexOf(key.toLowerCase())!=-1)
							{
								resultObjArr[resultObjArr.length] = child;
							}
						}
					}
					var text=child.getAttribute("nt");
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryExpandByKey(child,key,resultObjArr,matching);
					}
				}
				child = child.nextSibling;
			}
		}


	}catch(e){
		alert("* getQueryExpandByKey * " + e.description);
	}
	return resultObjArr;
}

/*
 obj	:为根节点
 keys :一组节点键值用逗号(,)分开

 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryExpandByKeys(obj,keys,resultObjArr,matching){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////

					if(child.getAttribute("key")!=null && child.getAttribute("key")!=undefined){

						var keysArr=keys.split(",");


						if(matching=="1"){
							for(var i=0;i<keysArr.length;i++){

								if (child.getAttribute("key").toLowerCase()==keysArr[i].toLowerCase())
								{
									resultObjArr[resultObjArr.length] = child;
									break;
								}
							}
						}else{
							for(var i=0;i<keysArr.length;i++){
								if (child.getAttribute("key").toLowerCase().indexOf(keysArr[i].toLowerCase())!=-1)
								{
									resultObjArr[resultObjArr.length] = child;
									break;
								}
							}
						}

					}
					//////////////////

					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryExpandByKeys(child,keys,resultObjArr,matching);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryExpandByKeys *" + e.description);
	}
	return resultObjArr;
}

/*
 obj	:为根节点
 keys :一组节点键值用逗号(,)分开

 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryExpandByTexts(obj,texts,resultObjArr,matching){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("nt")!=null && child.getAttribute("nt")!=undefined){
						var textsArr=texts.split(",");
						if(matching=="1"){
							for(var i=0;i<textsArr.length;i++){
								if (child.getAttribute("nt").toLowerCase()==textsArr[i].toLowerCase())
								{
									resultObjArr[resultObjArr.length] = child;
									break;
								}
							}
						}else{
							for(var i=0;i<textsArr.length;i++){
								if (child.getAttribute("nt").toLowerCase().indexOf(textsArr[i].toLowerCase())!=-1)
								{
									resultObjArr[resultObjArr.length] = child;
									break;
								}
							}
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryExpandByTexts(child,texts,resultObjArr,matching);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryExpandByTexts * " + e.description);
	}
	return resultObjArr;
}

/*
 展开默认选中的节点,锁定到第一个
 */
var curNodeBool=true;
function ExpandDefeultNode(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					/*if(childs[i].getAttribute("ds")=="1" && childs[i].getAttribute("dd")!="1"){//默认节点选中
					 if(childs[i].checkboxDisplay!="none" && getCheckStatusByNodeObj(childs[i])!="gray" && isExistBigCengCounto(childs[i])){
					 clearChecked(getInputNode(childs[i]));
					 checkSel(getInputNode(childs[i]));
					 }
					 bool=true;
					 }
					 //设置展开和收缩图片
					 if(parseInt(childs[i].getAttribute("cs"))>0 ){
					 changeOperateImg(childs[i]);
					 }

					 //设置全路径
					 if(childs[i].getAttribute("dd")!="1"){
					 childs[i].setAttribute("is",getCheckStatusByNodeObj(childs[i]));
					 if(treeObj!=null && treeObj!=undefined){
					 childs[i].setAttribute("fl",treeObj.getNodeNameUrl(childs[i],prefix));
					 }
					 }

					 if(childs[i].getAttribute("ds")=="1" && childs[i].getAttribute("dd")=="1"){
					 //展开默认选的动态加载节点
					 LoadDynamicNode(getImgChildNode(childs[i]));
					 }*/

					////////////
					if(child.getAttribute("ds")=="1" && child.getAttribute("dd")=="1"){
						//展开默认选的动态加载节点
						LoadDynamicNode(getImgChildNode(child));
					}
					//alert("当前节点..........");
					if(curNodeBool==true){
						if(child.getAttribute("ds")=="1")
						{
							//默认锁定第一个找到的节点
							//obj.scrollIntoView(true);
							divFocus(child);
							curNodeBool=false;
						}
					}
					////////////

					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = ExpandDefeultNode(child);
					}
				}
				child = child.nextSibling;//.nextSibling;
			}

		}

	}catch(e){
		alert("* ExpandDefeultNode * " + e.description);
	}
	//return bool;
}
/*
 自动加载单个节点
 */
function autoDynamicNode(obj){
	LoadDynamicNode(getImgChildNode(obj));
}
/*
 自动加载第一层节点的子节点
 */
function autoDynamicFirstFloorNode(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					////////////
					if(child.getAttribute("dd")=="1"){
						//展开默认选的动态加载节点
						LoadDynamicNode(getImgChildNode(child));
					}
				}else{
					break;
				}
				child = child.nextSibling.nextSibling;//.nextSibling;
			}
			//alert("不是 div 结束循环....");
		}

	}catch(e){
		alert("* ExpandDefeultNode * " + e.description);
	}
	//return bool;
}
/*
 得到第一层所有节点
 */
function getFirstFloorNode(obj){
	var results=new Array();
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					var cs = child.getAttribute("cs");
					if(child.getAttribute("cs") != undefined && child.getAttribute("cs") == 1){
						results[results.length] = child;
					}
				}
				try{
					child = child.nextSibling;//.nextSibling;
				}catch(e){
					child=null;
				}
			}
		}
	}catch(e){
		alert(" * getFirstFloorNode * " + e.description);
	}
	return results;
}




/*
 得到第一层所有节点
 */
ZZBTreeItem.prototype.getFirstFloorNode = function(){
	return getFirstFloorNode(this.MainDiv);
}

/*
 清理复选框选中状态并缩

 */
function clearSelectedCollapse(obj,treeObj){
	try{
		if(obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var imgObj=getImgChildNode(child);
					if(imgObj.getAttribute("open")=="yes"){
						collapseByDiv(child);
					}
					if(child.userRight!=undefined){
						child.userRight="";
					}
					treeObj.setCheckboxSelected(child,false);
					//////////////////
					child = child.nextSibling;
					if(child.hasChildNodes()){
						bool = clearSelectedCollapse(child,treeObj);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert(" * clearSelectedCollapse * " + e.description);
	}
}

ZZBTreeItem.prototype.clearSelectedCollapse = function(){
	clearSelectedCollapse(this.MainDiv,this);
}
/*
 展开节点和下级子节点

 */
function ExpandNodeAndNextNode(obj){
	try{
		var parentNode=getParentNode(obj);
		if(parentNode!=null && parentNode.getAttribute("cs")!=undefined){
			expandByDiv(parentNode);
			ExpandNodeAndNextNode(parentNode);
		}
	}catch(e){
		alert("* ExpandNodeAndNextNode * " + e.description);
	}
}

/*
 展开节点和下级子节点
 */
ZZBTreeItem.prototype.ExpandNodeAndNextNode = function(obj){
	var results=new Array();
	expandByDiv(obj);
	ExpandNodeAndNextNode(obj);
}

/*
 展开节点
 */
function ExpandNodeByObj(obj,compareObj,bool,resultArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child==compareObj){//默认节点选中
						bool=true;
						resultArr[resultArr.length]=child;
						//展开节点以及子节点
						expandByDiv(child);
						var parentNode=getParentNode(child);
						while(parentNode!=null){
							expandByDiv(parentNode);
							parentNode=getParentNode(parentNode);
						}
					}
					//////////////////
					var contextChild = child.nextSibling;
					if(contextChild.hasChildNodes()){
						ExpandNodeByObj(contextChild,compareObj,bool,resultArr);
					}
					child=child.nextSibling;
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* ExpandNodeByObj * " + e.description);
	}
	return bool;
}
/*
 保存初始状态
 */
function SavaInitialInfo(obj,treeObj,prefix){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("nt")!=undefined){
						child.setAttribute("is",getCheckStatusByNodeObj(child));
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = SavaInitialInfo(child,treeObj,prefix);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* SavaInitialInfo * " + e.description);
	}
}

/*
 得到复选框状态
 gray,false,true 三种状态
 */
function getCheckStatusByNodeObj(obj){
	var retStatus="";
	try{
		//alert(getNodeText(obj));
		if(obj!=null && getNodeText(obj)!=""){
			if(getRrayImgStatus(getInputNode(obj))){
				retStatus="gray";
			}else{
				var checked=getInputNode(obj).checked;
				if(checked){
					retStatus="true";
				}else{
					retStatus="false";
				}
			}
		}
	}catch(e){
		//alert(" getCheckStatusByNodeObj : " + e.description);
	}
	return retStatus;
}

/*
 得到复选框状态
 gray,false,true,disabled 四种状态
 */
function getCheckboxStatusByNodeObj(obj){
	var retStatus="";
	try{
		if(obj!=null && getNodeText(obj)!=""){
			var checkboxObj=getInputNode(obj);
			if(checkboxObj!=null){
				if(getRrayImgStatus(checkboxObj)){
					retStatus="gray";
				}else{
					if(checkboxObj.disabled==true){
						retStatus="disabled";
					}else{
						var checked=checkboxObj.checked;
						if(checked){
							retStatus="true";
						}else{
							retStatus="false";
						}
					}
				}
			}
		}
	}catch(e){
		alert("* getCheckStatusByNodeObj * " + e.description);
	}
	return retStatus;
}

/*
 obj	:为根节点
 key :要找的节点键值

 resultObjArr: 用于收集搜索得到对象的,数组变量
 */
function getQueryNodeByKey(obj,key,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if (child.getAttribute("key")!=undefined && child.getAttribute("key").toLowerCase().indexOf(key.toLowerCase())!=-1){
						resultObjArr[resultObjArr.length] = child;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryNodeByKey(child,key,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryNodeByKey *" + e.description);
	}
	return resultObjArr;
}

/*
 obj	:为根节点
 hc :要找的节点助记码
 resultObjArr: 用于收集搜索得到对象的,数组变量

 */
function getQueryExpandByHelpCode(obj,hc,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////

					if (child.getAttribute("hc")!=undefined && child.getAttribute("hc").toLowerCase().indexOf(hc.toLowerCase())!=-1)
					{
						resultObjArr[resultObjArr.length] = child;
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getQueryExpandByHelpCode(child,hc,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getQueryExpandByHelpCode * " + e.description);
	}
	return resultObjArr;
}

/*
 设置为当前节点,展开,滚动到可见区域显示

 */
function setCurrentNodeExpandView(obj){
	try{
		this.currentNode=obj;
		divFocus(obj);
		var arr=new Array();
		arr=QueryObjExpand(getObjByMainDivId(obj.getAttribute("md")).MainDiv,obj,arr);
	}catch(e){
		alert("* setCurrentNodeExpandView * " + e.description);
		//alert("setCurrentNodeExpandView");
	}
}

/*
 初始化查询条件

 */
function initialQueryConfig(treeObj){
	try{
		treeObj.resultArr=new Array();
		treeObj.clickCount=0;
		treeObj.queryStr="";
	}catch(e){
		alert("* initialQueryConfig * " + e.description);
	}
}

/*
 Obj	:为根节点
 resultObjArr: 用于收集搜索得到对象的,数组变量
 递归查询节点并展开
 */

function QueryObjExpand(divObj,obj,resultObjArr){
	try{
		if (divObj.hasChildNodes()){
			var child = divObj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("key")==obj.getAttribute("key")){
						expandByDiv(child);
						var parentNode=getParentNode(child);
						while(parentNode!=null){
							expandByDiv(parentNode);
							parentNode=getParentNode(parentNode);
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = ExpandDefeultNode(child,obj,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* QueryObjExpand * " + e.description);
	}
	return resultObjArr;
}

/*
 清除所有子复选框的选中状态
 */
function clearNodesCheckbox(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.nodeName=="INPUT"){
						if(checkboxObj.disabled == false){//
							if(checkboxObj.checked==true){
								clearChecked(checkboxObj);
							}
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = clearNodesCheckbox(child);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* clearNodesCheckbox * " + e.description);
	}
}

/*
 得到灰色图片状态
 */
function getRrayImgStatus(obj){
	var bool=false;
	try{
		if(obj.parentNode.getAttribute("gi")=="0"){
			return false;
		}
		if(obj.previousSibling.style.display!="none"){
			bool=true;
		}

	}catch(e){
		bool=false;
	}
	return bool;
}

/*
 设置所有子复选框的状态选中
 */
function selectNodesCheckbox(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.disabled == false){
						if(checkboxObj.checked==false){
							selectChecked(checkboxObj);
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						if(child!=null){
							bool = selectNodesCheckbox(child);
						}
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* selectNodesCheckbox * " + e.description);
	}
}

/*
 更新子节点选中状态属性值
 */
function updateChildNodeSelAll(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.disabled == false){
						if(checkboxObj.checked==true){
							child.setAttribute("selAll","1");
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						if(child!=null){
							bool = updateChildNodeSelAll(child);
						}
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* selectNodesCheckbox * " + e.description);
	}
}

/*
 设置所有子复选框的状态默认选中
 */
function setChildDefaultSelected(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.disabled == false){
						if(checkboxObj.checked==false){
							if(child.getAttribute("ds")!="0"){
								selectChecked(checkboxObj);
							}
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						if(child!=null){
							bool = setChildDefaultSelected(child);
						}
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* setChildDefaultSelected * " + e.description);
	}
}

/*
 清除所有子复选框的默认选中状态
 */
function clearChildDefaultSelected(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.nodeName=="INPUT"){
						if(checkboxObj.disabled == false){//
							if(checkboxObj.checked==true){
								if(child.getAttribute("ds")!="1"){
									clearChecked(checkboxObj);
								}
							}
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = clearChildDefaultSelected(child);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* clearChildDefaultSelected * " + e.description);
	}
}

/*
 设置所有子复选框的状态选中
 */
function selectNodesCheckboxUseType1(obj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.disabled == false){

						if(checkboxObj.checked == false){
							selectChecked(checkboxObj);
						}
					}
					//////////////////
					child = child.nextSibling;
					/*
					 if (child.hasChildNodes()){
					 if(child!=null){
					 bool = selectNodesCheckbox(child);
					 }
					 }
					 */
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* selectNodesCheckboxUseType1 * " + e.description);
	}
}
//清除所有同层节点的其它节点选中状态
function clearCheckBoxSelected(obj){
	try{
		//检查其它选中的子节点进行清除
		var arr=getParentAllInput(obj);
		//对所有兄弟节点进行判断
		for(var k=0;k<arr.length;k++){
			if(arr[k].name!=getInputNode(obj).name){
				clearChecked(arr[k]);
			}
		}
	}catch(e){
		alert("* clearCheckBoxSelected * " + e.description);
	}
}

//清除下级节点的其它节点选中状态
function clearLowerSelected(obj,currentInputObj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj!=currentInputObj){
						clearChecked(checkboxObj);
					}
					//////////////////
					child = child.nextSibling;
					/*
					 if (child.hasChildNodes()){
					 bool = clearAllOtherSelected(child,currentInputObj);
					 }
					 */
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* clearLowerSelected *" + e.description);
	}
}

//清除所有节点的其它节点选中状态
function clearAllOtherSelected(obj,currentInputObj){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj!=currentInputObj){
						clearChecked(checkboxObj);
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = clearAllOtherSelected(child,currentInputObj);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert(" * clearAllOtherSelected * " + e.description);
	}
}

/*
 selectChild  选中的子节点数
 grayImgCount 灰勾状态下的子节点数
 childCount   总的子节点数
 */
function getSelectedInfos(obj,selecedInfArr){
	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null){
			if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
				//////////////////
				selecedInfArr[2]++;
				var checkboxObj=getInputNode(child);
				if(checkboxObj!=null && checkboxObj.checked==true){
					selecedInfArr[0]++;
				}

				if(getRrayImgStatus(checkboxObj)==true){
					if(checkboxObj.previousSibling.style.display!="none"){
						selecedInfArr[1]++;
					}
				}

				//////////////////
				child = child.nextSibling;
				if (child.hasChildNodes()){
					bool = getSelectedInfos(child,selecedInfArr);
				}
			}
			try{
				child = child.nextSibling;
			}catch(e){
				child=null;
			}
		}
	}
	return selecedInfArr;
}
/*
 得到所有子节点是否全部选中
 */
function getChildNodeAllSelect(obj){
	var rObj=new Array();
	try{
		if(obj.childNodes!=null && obj.childNodes.length>0){
			var selectChild=0;//选中的子节点数
			var grayImgCount=0;//灰色勾数量 记录变量
			var childCount=0;//子节点总数
			var selecedInfArr=new Array();
			selecedInfArr[0]=0;
			selecedInfArr[1]=0;
			selecedInfArr[2]=0;
			selecedInfArr=getSelectedInfos(obj.nextSibling,selecedInfArr);
			selectChild=selecedInfArr[0];
			grayImgCount=selecedInfArr[1];
			childCount=selecedInfArr[2];

			/*
			 childs=getAllChildNodes(obj.nextSibling,childs);//getDivChildNodes(obj);
			 for(var i=0; i<childs.length; i++){
			 if(childs[i].nodeName=="DIV" ){
			 childCount++;
			 //alert(childs[i].getAttribute("nt") + " == " + getInputNode(childs[i]).checked);
			 if(getInputNode(childs[i]).checked==true){
			 selectChild++;
			 }
			 if(getRrayImgStatus(getInputNode(childs[i]))==true){
			 if(getInputNode(childs[i]).previousSibling.style.display!="none"){
			 grayImgCount=grayImgCount+1;
			 }
			 }

			 }
			 }
			 */

			//alert("总节点数:"+childCount + "选中的节点数:"+selectChild + "灰勾的节点数:"+grayImgCount);
			rObj[0]=false;
			rObj[1]=selectChild;
			rObj[2]=childCount;
			//alert(" 总节点数:" + childCount + " 选中个数:" + selectChild + " 灰勾节点个数:" + grayImgCount);
			if((selectChild==childCount || selectChild==0) && grayImgCount==0){
				rObj[0]=true;
			}

		}
	}catch(e){
		alert("* getChildNodeAllSelect * " + e.description);
	}
	//alert("rObj:::" + rObj);
	return rObj;
}

function getInfosByStatus(status,obj){

	var num=0;
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.checked==status){
						num++;
					}
					child = child.nextSibling;
				}
				try{
					child = child.nextSibling;
				}catch(e){
					child=null;
				}
			}
		}
	}catch(e){
		alert("* getInfosByStatus * " + e.description);
	}
	return num;
}

/*
 得到某种状态下子节点个数
 */
function getChildNodeByStatus(obj,status){
	try{
		var num=getInfosByStatus(status,obj.nextSibling);
		return num;
	}catch(e){
		alert("* getChildNodeByStatus * " + e.description);
		return 0;
	}
}

function isExistChildAllSelected(status,obj){
	var bool=getAllChildNodeIsStatus(status,obj.nextSibling);
	return bool;
}

function getAllChildNodeIsStatus(status,obj){
	var bool=true;
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
					//////////////////
					var checkboxObj=getInputNode(child);
					if(checkboxObj!=null && checkboxObj.checked!=status){
						bool=false;
					}
					child = child.nextSibling;
				}
				try{
					child = child.nextSibling;
				}catch(e){
					child=null;
				}
			}
		}
	}catch(e){
		alert("* getAllChildNodeIsStatus * " + e.description);
	}
	return bool;
}
/*
 检查是否为顶层Div
 */
function isExistTopDiv(obj)
{

	var bool=false;
	try{
		if(obj.id==obj.getAttribute("md")){
			//alert(md);
		}
	}catch(e){
		bool=true;
	}
	return bool;
}

/*
 检查子兄弟节点选中状态

 */
function checkBrotherNodeSelect(obj){
	try{
		if(obj!=null && obj.getAttribute("icd")!="0"){

			var tree=getObjByMainDivId(obj.getAttribute("md"));
			if(tree.zzb_parentChildIsolate && tree.getCheckboxStatus(getParentNode(obj))!="disabled"){//在设置选择父会影响子节点的情况下
				if(getParentNode(obj)!=null && getParentNode(obj).nodeName=="DIV" && getParentNode(obj).getAttribute("md")!=null && getParentNode(obj).getAttribute("cs")!="0"){
					var parentObj=getParentNode(obj);
					var rObj=getChildNodeAllSelect(parentObj);

					if(tree.lowerClearWithSuperior==true){
						if(getInputNode(obj).checked==false){
							getInputNode(getParentNode(obj)).checked=false;
						}
					}

					if(tree.lowerSelectedWithSuperior==true){
						if(getInputNode(obj).checked==true){
							getInputNode(getParentNode(obj)).checked=true;
						}
					}

					if(rObj[0]){//子节点全部选中
						//检查父节点是否有灰勾状态设置
						if(getParentNode(obj).getAttribute("gi")=="1" && getParentNode(obj).getAttribute("igi")=="1"){
							getInputNode(getParentNode(obj)).previousSibling.style.display="none";//隐藏图片
							getInputNode(getParentNode(obj)).style.display="";//显示checkbox
						}


						//alert("子节点全部选中...:" + getNodeText(getParentNode(obj)) + " rObj : " + rObj);
						if(getParentNode(obj).getAttribute("igi")=="1"){
							//selectChecked(getInputNode(getParentNode(obj)));
							if(rObj[1]==0){//子节点全部没有选中
								if(tree.lowerClearWithSuperior==true){
									clearChecked(getInputNode(getParentNode(obj)));
								}else if(tree.lowerClearWithSuperior==false && tree.lowerClearAllWithSuperior==true){
									clearChecked(getInputNode(getParentNode(obj)));
								}
							}else if(rObj[1]==rObj[2]){//选中节点和所有子节点相同的情况下
								selectChecked(getInputNode(getParentNode(obj)));
							}
						}

						//

					}else{//子节点未全部选中
						//alert("子节点全部没选中");
						//检查父节点是否有灰勾状态设置
						if(getParentNode(obj).getAttribute("gi")=="1" && getParentNode(obj).getAttribute("igi")=="1"){
							getInputNode(getParentNode(obj)).previousSibling.style.display="";//显示图片
							getInputNode(getParentNode(obj)).style.display="none";//隐藏checkbox
						}

						/*子节未全部选中下，父节点变空白复选框
						 if(getParentNode(obj).igi=="1"){
						 clearChecked(getInputNode(getParentNode(obj)));//图片变灰同时复选框选中
						 }
						 */
					}

					/*
					 alert(parentObj.nodeName);
					 if(parentObj.nodeName=="DIV"){
					 alert(getValueByName(parentObj,"text"));
					 }
					 */

					checkBrotherNodeSelect(parentObj);

				}
			}
		}
	}catch(e){
		alert("* checkBrotherNodeSelect * " + e.description);
	}
}

/*

 */
function setChildChecked(obj){
	clearChecked(getInputNode(obj.parentNode));
	checkSel(getInputNode(obj.parentNode));


	getInputNode(obj.parentNode).previousSibling.style.display="none";//隐藏图片
	getInputNode(obj.parentNode).style.display="";//显示checkbox


}

function gic(obj){
	grayImgClick(obj);
}
/*
 单击灰色图片时,触发此操作
 */
function grayImgClick(obj){

	/*obj.nextSibling.style.display="";
	 obj.style.display="none";

	 obj.parentNode.setAttribute("gc","1");//单击了一次灰图片
	 clearChecked(obj.nextSibling);
	 checkSel(obj.nextSibling);*/
	grayCheckSel(obj.nextSibling);

}

/*
 复选框点击次数
 */
function checkBoxClickCount(obj){

	var status="";
	try{
		//alert(getInputNode(obj).checked);
		if(obj.getAttribute("gi")=="1"){
			if(getInputNode(obj).previousSibling.style.display!="none" || obj.getAttribute("gc")=="1"){//图片隐藏的情况下
				getInputNode(obj).style.display="";//显示checkbox
				getInputNode(obj).previousSibling.style.display="none";
				obj.setAttribute("gc","0");
				clearChecked(obj.nextSibling);
				return "";
			}
			if(getInputNode(obj).checked==true){//复选框选中的情况
				getInputNode(obj).previousSibling.style.display="";
				getInputNode(obj).style.display="none";
				status="gray";
			}
		}
	}catch(e){
		alert("* checkBoxClickCount * " + e.description);
	}
	return status;
}

/*
 恢复到复选框初始状态
 */
function setCheckBoxInitial(obj,bool){
	try{
		if(bool){
			var checkboxDefault=obj.getAttribute("is");
			if(checkboxDefault=="true"){
				getInputNode(obj).previousSibling.style.display="none";
				getInputNode(obj).style.display="";
				selectChecked(getInputNode(obj));
			}else if(checkboxDefault=="gray"){
				getInputNode(obj).previousSibling.style.display="";
				getInputNode(obj).style.display="none";
			}else if(checkboxDefault=="false"){
				getInputNode(obj).previousSibling.style.display="none";
				getInputNode(obj).style.display="";
				clearChecked(getInputNode(obj));
			}
		}
		var childs=getDivChildNodes(obj);
		if(childs!=null && childs.length>0){
			for(var i=0; i<childs.length; i++){
				setCheckBoxInitial(childs[i],true);
			}
		}
	}catch(e){
		alert("* setCheckBoxInitial * " + e.description);
	}
}




function grayCheckSel(obj){
	try{
		if(getRrayImgStatus(obj)==true){//单击灰勾图片时
			obj.parentNode.setAttribute("gc","1");
			obj.style.display=""
			obj.previousSibling.style.display="none";
		}else if(obj.checked==false){//单击勾中复选框时
			obj.parentNode.setAttribute("gc","2");
		}else if(obj.checked==true){//单击没有勾中的复选框时
			obj.parentNode.setAttribute("gc","3");
			obj.previousSibling.style.display="";//显示灰勾图片
			obj.style.display="none";//隐藏复选框
		}
		if(obj.parentNode.getAttribute("gc")=="1"){
			checkSel(obj);
		}else if(obj.parentNode.getAttribute("gc")=="2"){
			checkSel(obj);
		}else if(obj.parentNode.getAttribute("gc")=="3"){
			setCheckBoxInitial(obj.parentNode,false);
			//检查兄弟节点选中状态,用于决定父节点是否要变灰
			if(tree.lowerSelectedAllWithSuperior==true){
				checkBrotherNodeSelect(obj.parentNode);
			}
		}
	}catch(e){
		alert("* grayCheckSel * " + e.description);
	}
}

function cs(obj,executeA){
	var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));
	if(tree.type=="1"){
		checkSelType1(obj);
	}else{
		if(obj.parentNode.getAttribute("gi")=="1"){//如果此节点有灰勾状态
			grayCheckSel(obj);
		}else{
			checkSel(obj,executeA);
		}
	}

}

/*
 当单击复选框时,根据配置信息做相应的动作
 */
function checkSel(obj,executeA){
	try{
		if(obj!=null){
			var tree=getObjByMainDivId(obj.parentNode.getAttribute("md"));

			////单击标题的情况下//////
			if(executeA==true){
				setChecked(obj);
			}
			///////////////////////

			//有子节点展开
			if(isChildNodeExist(obj.parentNode)){
				if(tree.expandByCheckbox!="0"){
					expandByCheckbox(obj);
				}
			}

			if(tree.zzb_RadioOrCheckBox){//复选
				/////////
				if(tree.zzb_parentChildIsolate){
					if(getDivChildNodes(obj.parentNode).length>0){//先判断是否为父节点
						///////处理父节点//////
						if(tree.zzb_parentCheckboxEnable){//父节点可以选中
							///////处理子节点/////////
							if(tree.zzb_parentChildIsolate){

								//在设置选择父会影响子节点的情况下
								if(obj.checked==true){
									if(tree.parentSelectedWithChild==true){
										selectNodesCheckbox(obj.parentNode.nextSibling);//选中
									}

								}else{
									if(tree.parentClearWithChild==true){
										clearNodesCheckbox(obj.parentNode.nextSibling);//取消
									}

								}
							}

						}else{//父节子不能选中

							clearChecked(obj);
						}
						/////////////////////////
					}

					//检查兄弟节点选中状态,用于决定父节点是否要变灰
					if(obj.checked==false){
						//alert(obj.parentNode.nodeName);
						checkBrotherNodeSelect(obj.parentNode);
					}else{
						if(tree.lowerSelectedAllWithSuperior==true || tree.lowerSelectedWithSuperior==true){
							checkBrotherNodeSelect(obj.parentNode);
						}
					}
				}else{
					//父节点子和子节点不相互影响
					if(getDivChildNodes(obj.parentNode).length>0){

						if(!tree.zzb_parentCheckboxEnable){

							clearChecked(obj);
						}
					}
				}
				//设置复选框选中，标题得到焦点
				if(tree.selectedTitleByCheckbox=="true"){

					tree.setCurrentNodeByNode(obj.parentNode);

				}
			}else{//单选
				if(getDivChildNodes(obj.parentNode).length>0){//如果是父节点检查父节点是否可以选中
					if(tree.zzb_parentRadioEnable){
						clearAllOtherSelected(getMainDivByMainDivId(obj.parentNode.getAttribute("md")),obj);//清除其它节点
					}
				}else{
					clearAllOtherSelected(getMainDivByMainDivId(obj.parentNode.getAttribute("md")),obj);//清除其它节点
				}
			}

			//检查处理子节点选中状态
			changeNodeSelectAllStatus(obj.parentNode);
		}

	}catch(e){
		alert("* checkSel * " + e.description);
	}

}

/*
 得到同级节点信息
 */
function getSameLowerNodeSelectedInfo(obj){
	//0所有同级节点选中信息(0都没有选中，1为全部选中，2为部分选中),
	//1同级节点总数量，
	//2为同级节点选中数量
	var info=new Array(3);

	var parentObj=getParentNode(obj);
	if(parentObj!=null){
		var results=new Array();
		results=getNextChildNodes(parentObj.nextSibling,results);
		var sum=0;
		var selectedsum=0;
		for(var i=0;i<results.length;i++){
			var tempObj=getInputNode(results[i]);
			sum++;
			if(tempObj.checked==true){
				selectedsum++;
			}
		}
		if(selectedsum==0){
			info[0]=0;
		}else if(sum==selectedsum){
			info[0]=1;
		}else{
			info[0]=2;
		}
		info[1]=sum;
		info[2]=selectedsum;
	}
	return info;
}

function clearChecked(obj){
	if(obj!=null){
		//if(obj.sel=="1"){
		obj.checked=false;
		//obj.sel="0";
		//}
	}
}

function selectChecked(obj){
	if(obj!=null){
		//if(obj.sel=="0"){
		obj.checked=true;
		//obj.sel="1";
		//}
	}
}

function setChecked(obj){
	if(obj!=null){
		if(obj.checked==true){
			clearChecked(obj);
		}else{
			selectChecked(obj);
		}
	}
}

function setGray(obj){
	try{
		clearChecked(getInputNode(obj));
		getInputNode(obj).previousSibling.style.display="";//显示图片
		getInputNode(obj).style.display="none";//隐藏checkbox
	}catch(e){
		alert("* setGray * " + e.description);
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////
function getDivChildNodeByImg(obj)
{
	var divObj=null;
	try
	{
		divObj=obj.parentNode;
	}
	catch (e)
	{
		divObj=null;
	}
	return divObj;
}

/*
 得到加减号图片对象


 */
function getImgChildNode(obj)
{
	var rObj=null;
	try
	{
		if(!obj){
			//alert("obj is null");
			return rObj;
		}
		var childNodes=obj.childNodes;
		for(var i=0;i<childNodes.length;i++){
			if(childNodes.nodeType==3){
				continue;
			}
			if(childNodes[i].nodeName=="IMG" ){
				if(childNodes[i].id.substr(childNodes[i].id.indexOf("plus"))=="plus"){
					rObj=childNodes[i];
					break;
				}
			}
		}
	}
	catch (e)
	{
		alert("getImgChildNode::" +e.description);
	}

	return rObj;
}

/*
 得到节点文本
 */
function getNodeText(obj){
	var aText="";
	try{
		var childNodes=obj.childNodes;
		for(var i=0;i<childNodes.length;i++){
			if(childNodes[i].nodeName=="A"){
				aText=childNodes[i].innerText;
				break;
			}
		}
	}catch(e){
		alert("* getNodeText * " + e.description);
	}

	return aText;
}

/*
 得到下一个节点

 */
function getNextNode(obj){
	try
	{
		return obj.nextSibling;
	}
	catch (e)
	{
		alert("* getNextNode * " + e.description);
	}

}
/*
 找到要删除的节点,包括它的子节点

 */
function getDelNodes(obj,resultObjArr){
	try{

		/*
		 resultObjArr[resultObjArr.length] = Obj;
		 if(Obj.childNodes.length>0){
		 var childs=getDivChildNodes(Obj);
		 for(var i=0; i<childs.length; i++){
		 var len = resultObjArr.length;
		 if(childs[i].length.length>0){
		 resultObjArr = getDelNodes(childs[i], resultObjArr);
		 }
		 }
		 }
		 */
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					resultObjArr[resultObjArr.length]=child;
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getDelNodes(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert("* getDelNodes * "+e.description);
	}
	return resultObjArr;
}

/*
 得到内容div节点
 */
function getDivChildNodes(obj){
	var childNodes=null;
	try {
		//alert(obj.firstChild.nextSibling.nodeName)
		//if (obj.nodeName != "SCRIPT") {
		   if (obj.firstChild.nodeName == "DIV") {
			   childNodes = obj.childNodes;
		   } else {
			   if (obj.nextSibling != null) {
				   childNodes = obj.nextSibling.childNodes;
			   }
		   }
	    //}
	}catch(e){
		alert("getDivChildNodes :: "+e.description);
	}
	return childNodes;
}

/*
 得到子节点

 */
function getChildNodes(obj){
	var retObj=new Array();
	try{
		obj=obj.nextSibling;
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){

				if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
					//////////////////
					retObj[retObj.length]=child;
					//////////////////
				}
				child = child.nextSibling.nextSibling;
			}
		}
	}catch(e){
		alert("* getChildNodes * " + e.description);
	}
	return retObj;
}

/*
 得到所有子节点包括子节点的的子节点
 */
function getAllChildNodes(obj,results){
	/*var childs=getDivChildNodes(obj);
	 for(var i=0;i<childs.length;i++){
	 if(childs[i].nodeName=="DIV"){
	 results[results.length]=childs[i];
	 getAllChildNodes(childs[i],results);
	 }
	 i++;
	 }*/
	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null){
			if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
				//////////////////
				results[results.length]=child;
				//////////////////
				child = child.nextSibling;
				if (child.hasChildNodes()){
					bool = getAllChildNodes(child,results);
				}
			}
			child = child.nextSibling;
		}
	}
	return results;
}

/*
 得到所有子节点包括子节点的的子节点
 */
function getAllChildNodesByStatus(obj,results,status){
	/*var childs=getDivChildNodes(obj);
	 for(var i=0;i<childs.length;i++){
	 if(childs[i].nodeName=="DIV"){
	 results[results.length]=childs[i];
	 getAllChildNodes(childs[i],results);
	 }
	 i++;
	 }*/
	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null){
			if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
				//////////////////
				var bool=getCheckboxStatusByNodeObj(child);
				//alert(child.getAttribute("nt") + "  checked:" + bool);
				if(bool==status){
					results[results.length]=child;
				}
				//////////////////
				child = child.nextSibling;
				if (child.hasChildNodes()){
					bool = getAllChildNodesByStatus(child,results,status);
				}
			}
			child = child.nextSibling;
		}
	}
	return results;
}

function getNextChildNodes(obj,results){
	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null){
			if(child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
				results[results.length]=child;
			}
			child = child.nextSibling;
		}
	}
	return results;
}
/*
 得到<a> 节点
 */
function getAChildNode(obj){
	try{
		var rObj=null;
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null && child!=undefined){
				if (child.nodeName == "A"){
					//////////////////
					rObj=child;
					//////////////////
				}
				child = child.nextSibling;
			}
		}
	}catch(e){
		//alert("getAChildNode : "  + e.description);
		return null;
	}
	return rObj;
}

/*
 得到上一个兄弟节点
 */
function getPreviousBrotherNode(obj){
	var rObj=null;
	try
	{
		rObj=obj.previousSibling.previousSibling;
	}
	catch (e)
	{
		rObj=null;
	}

	return rObj;
}

/*
 得到下一个兄弟节点

 */
function getNextBrotherNode(obj){
	var rObj=null;
	try{
		rObj=obj.nextSibling.nextSibling;
	}catch(e){
		rObj=null;
	}
	return rObj;
}

/*
 得到div的父节点
 */
function getParentNode(obj){
	try{
		var parentObj=obj.parentNode.previousSibling;
		if(isExistBigCengCounto(parentObj)){
			return parentObj;
		}else{
			return null;
		}
	}catch (e){
		return null;
		alert("getParentNode :" + e.description);
	}

}

/*
 得到复选框节点
 */
function getInputNode(obj){
	var rObj=null;
	try{
		/*var contChilds=obj.childNodes;
		 if(contChilds!=null && contChilds.length>0){
		 for(var i=0;i<contChilds.length;i++){
		 if(contChilds[i].tag=="INPUT"){
		 rObj=contChilds[i];
		 }
		 }
		 }*/
		if(getValueByName(obj,"checkboxdisplay")=="0"){
			return null;
		}
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				//////////////////
				if(child.nodeName=="INPUT"){
					rObj=child;
				}
				//////////////////
				child = child.nextSibling;
			}
		}
	}catch(e){
		alert(" * getInputNode * " + e.description);
	}
	return rObj;
}

/*
 得到父节点下所有checkbox子节点

 */
function getParentAllInput(obj){
	var rArr=new Array();
	try{
		var contChilds=obj.parentNode.childNodes;
		for(var i=0;i<contChilds.length;i++){
			if(contChilds[i].nodeName=="DIV"){
				rArr[rArr.length]=getInputNode(contChilds[i]);
			}
			i++;
		}
	}catch(e){
		alert("* getParentAllInput * " + e.description);
	}
	return rArr;
}

/*
 得到父节点的复选框节点
 */
function getParentInputNode(obj){
	var rObj=null;
	try{
		var childs=obj.parentNode.previousSibling.childNodes;

		for(var i=0;i<childs.length;i++){
			if(childs[i].tag=="INPUT"){
				rObj=childs[i];
				break;
			}
		}
	}catch(e){
		alert("* getParentInputNode * " + e.description);
	}
	return rObj;
}

/*
 通过mainDivId 得到tree对象
 */
function getObjByMainDivId(md){
	return zzbTreeHandler.zzb_TreeObjs[md];
}
/*
 通过mainDivId 生成tree对象
 */
function setObjByMainDivId(mainDiv){
	var tree=new ZZBTreeItem(mainDiv,"","jsp");
}

/*
 通过mainDivId 得到div对象
 */
function getMainDivByMainDivId(md){
	return zzbTreeHandler.zzb_TreeObjs[md].MainDiv;
}

/*
 得到一组节点信息

 */
function getNodeInfos(obj){
	var nodeInfoArr=new Array();
	try{
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("iv");//复选框
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("key");//键值
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("hc");//助记码
		nodeInfoArr[nodeInfoArr.length]=getNodeNameUrl(obj);//全路径
		nodeInfoArr[nodeInfoArr.length]=getNodeText(obj);//节点标题
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("da");//节点自定义属性
		nodeInfoArr[nodeInfoArr.length]=getCheckStatusByNodeObj(obj);//复选框状态
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("fn");//全称
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("ft");//简称
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("hc2");//助记码2
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("cc");//自定义标题


	}catch(e){
		alert("* getNodeInfos * " + e.description);
	}
	return nodeInfoArr;
}

/*
 得到一个节点信息

 */
function getNodeInfo(obj){

	var nodeInfoArr=new Array();
	try{
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("key");//键值
		nodeInfoArr[nodeInfoArr.length]=obj.getAttribute("hc");//助记码
		nodeInfoArr[nodeInfoArr.length]=getNodeText(obj);//节点标题

	}catch(e){
		alert("* getNodeInfo * " + e.description);
	}
	return nodeInfoArr;
}

//////////////////////////二次开发使用函数

/*
 设置为当前节点,参数为复选框对象
 */
ZZBTreeItem.prototype.setCurrentNodeByCheckbox = function(checkboxObj,executeA){
	var aObj=getAChildNode(checkboxObj.parentNode);
	if(executeA==true){
		//aObj.click();
	}
	nodeFocus(aObj);
}

/*
 设置为当前节点 参数为节点对象
 */
ZZBTreeItem.prototype.setCurrentNodeByNode =function (obj){
	nodeFocus(getAChildNode(obj));
	//getAChildNode(this.currentNode).blur();

}

function setCurrentNodeByNode(obj){
	nodeFocus(getAChildNode(obj));
	//getAChildNode(this.currentNode).blur();

}

/*
 设置为当前节点 参数为节点对象
 */
ZZBTreeItem.prototype.setCurrentNodeByNode2 =function (obj){
	this.setCurentNodeBool=true;
	//alert("setCurrentNodeByNode2：");
	nodeFocus(getAChildNode(obj));
	//getAChildNode(this.currentNode).blur();

}

/*
 配置多选信息

 */
ZZBTreeItem.prototype.setCheckboxConfig = function(checkBoxBool,selectParentBool,selectWay){
	this.zzb_RadioOrCheckBox=checkBoxBool;
	this.zzb_parentCheckboxEnable=selectParentBool;
	this.zzb_parentChildIsolate=selectWay;

}

ZZBTreeItem.prototype.setDataSource = function(dataSource){
	this.zzb_dataSource=dataSource;
}

ZZBTreeItem.prototype.setcheckboxDisplay = function(checkboxDisplay){
	this.zzb_checkboxDisplay=checkboxDisplay;
}

ZZBTreeItem.prototype.setzzb_auto_dynamic_load = function(zzb_auto_dynamic_load){
	this.zzb_auto_dynamic_load=zzb_auto_dynamic_load;
}

/*
 通过节点key查找,并设置命中节点是否选中状态,同时设置它的子节点是否选中
 */
ZZBTreeItem.prototype.setNodeStatus = function(key,nodeBool,childNodeBool){
	try{
		var resultArr=new Array();
		resultArr=this.getNodesByKey(key);//通过key值得到相关节点
		//处理找到的节点
		if(resultArr.length>0){
			//展开找到的节点
			expandByDiv(resultArr[0]);
			//选中找到的节点
			if(nodeBool){
				getInputNode(resultArr[0]).checked=true;
			}
			if(childNodeBool){
				setChildNodeStatus(resultArr[0],childNodeBool);
			}
		}
	}catch(e){
		//alert("setNodeStatus::"+e.description);
	}
}

/*
 清空查询条件重新查询
 */
ZZBTreeItem.prototype.clearQueryStr = function(){
	this.queryStr="";
}

/*
 nodeName  : 节点名

 通过节点名得到找到并展开节点对象 返回一个array 是一组节点对象

 */
ZZBTreeItem.prototype.getExpandNodeByNodeName = function(nodeName)
{
	//try{

	//判断是否第一次查询
	var resultArr=new Array();
	resultArr=getQueryExpandByName(this.MainDiv,nodeName,resultArr);

	/*if(this.queryStr!=nodeName){
	 //初始化查询变量
	 initialQueryConfig(getObjByMainDivId(this.MainDiv.id));

	 //得到查询结课
	 resultArr=getQueryExpandByName(this.MainDiv,nodeName,resultArr);

	 if(resultArr.length>0){
	 //设置为当前节点,展开,显示
	 setCurrentNodeExpandView(resultArr[this.clickCount]);
	 this.queryStr=nodeName;
	 this.clickCount++;
	 }
	 this.resultArr=resultArr;
	 }else{
	 //只有一个的时候复位

	 if(this.resultArr.length==1){this.clickCount=0;}

	 //查询记录已经存在,下移到命中的下一个

	 if(this.clickCount<this.resultArr.length){

	 //设置为当前节点,展开,显示
	 setCurrentNodeExpandView(this.resultArr[this.clickCount]);

	 //如果已经是最后一个就返回第一个

	 if(this.clickCount==this.resultArr.length-1){
	 this.clickCount=0;
	 }else{
	 this.clickCount++;
	 }
	 }
	 }
	 }catch(e){alert("getExpandNodeByNodeName"+e.description);}*/

	return resultArr;
}


/*
 转换节点对象为一组数据

 array[0] 为 checkBox值

 array[1] 为key值

 array[2] 为助记码
 array[3] 为节点名

 */

ZZBTreeItem.prototype.changeObj = function(objArr){
	var retArray = new Array();
	for(var i=0;i<objArr.length;i++){
		var divArray=new Array();
		divArray[0]=objArr[i].getAttribute("iv");
		divArray[1]=objArr[i].getAttribute("key");
		divArray[2]=objArr[i].getAttribute("hc");
		divArray[3]=getNodeText(objArr[i]);
		divArray[4]=objArr[i];
		retArray[retArray.length]=divArray;
	}
	return retArray;
}

/*
 通过助记码找到并展开节点对象
 */
ZZBTreeItem.prototype.getNodesByHelpCode = function(hc)
{
	//try
	//{
	//判断是否第一次查询
	var resultArr=new Array();
	resultArr=getQueryExpandByHelpCode(this.MainDiv,hc,resultArr);
	/*if(this.queryStr!=hc){
	 //初始化查询变量
	 initialQueryConfig(getObjByMainDivId(this.MainDiv.id));
	 //得到查询结课
	 this.resultArr=getQueryExpandByHelpCode(this.MainDiv,hc,this.resultArr);

	 if(this.resultArr.length>0){
	 //设置为当前节点,展开,显示
	 setCurrentNodeExpandView(this.resultArr[this.clickCount]);
	 this.queryStr=hc;
	 this.clickCount++;
	 }
	 }else{
	 //只有一个的时候复位

	 if(this.resultArr.length==1){this.clickCount=0;}

	 //查询记录已经存在,下移到命中的下一个

	 if(this.clickCount<this.resultArr.length){

	 //设置为当前节点,展开,显示
	 setCurrentNodeExpandView(this.resultArr[this.clickCount]);

	 //如果已经是最后一个就返回第一个

	 if(this.clickCount==this.resultArr.length-1){
	 this.clickCount=0;
	 }else{
	 this.clickCount++;
	 }
	 }
	 }
	 }
	 catch (e)
	 {
	 alert("getNodesByHelpCode"+e.description);
	 }
	 */

	return resultArr;
}

/**
 keys为一组key值,之间用逗号分开(,)
 matching匹配方式  “1” 为绝对匹配 “0” 为模糊匹
 */
ZZBTreeItem.prototype.getNodesByKeys = function(keys,matching)
{
	var results=new Array();
	results=getQueryExpandByKeys(this.MainDiv,keys,results,matching);
	return results;
}

/**
 text为一组key值,之间用逗号分开(,)
 matching匹配方式  “1” 为绝对匹配 “0” 为模糊匹
 */
ZZBTreeItem.prototype.getNodesByTexts = function(texts,matching)
{
	var resultsArr=new Array();
	resultsArr=getQueryExpandByTexts(this.MainDiv,texts,resultsArr,matching);
	return resultsArr;
}

/*
 通过key键值找到并展开节点对象
 */
ZZBTreeItem.prototype.getNodesByKey = function(key,matching)
{
	//this.resultArr 为树对象的一个变量
	var resultArr=new Array();
	resultArr=getQueryExpandByKey(this.MainDiv,key,resultArr,matching);
	/* try{
	 if(key!=null && key.length>0){
	 //判断是否第一次查询
	 if(this.queryStr!=key){
	 //初始化查询变量
	 initialQueryConfig(getObjByMainDivId(this.MainDiv.id));
	 //得到查询结课
	 this.resultArr=getQueryExpandByKey(this.MainDiv,key,this.resultArr,matching);
	 if(this.resultArr.length>0){
	 //设置为当前节点,展开,显示
	 setCurrentNodeExpandView(this.resultArr[this.clickCount]);
	 this.queryStr=key;
	 this.clickCount++;
	 }
	 }else{
	 //只有一个的时候复位
	 if(this.resultArr.length==1){this.clickCount=0;}
	 //查询记录已经存在,下移到命中的下一个
	 if(this.clickCount<this.resultArr.length){
	 //设置为当前节点,展开,显示
	 setCurrentNodeExpandView(this.resultArr[this.clickCount]);
	 //如果已经是最后一个就返回第一个
	 if(this.clickCount==this.resultArr.length-1){
	 this.clickCount=0;
	 }else{
	 this.clickCount++;
	 }
	 }
	 }
	 }
	 }catch(e){
	 alert("getNodesByKey::"+e.description);
	 }
	 */
	return resultArr;
}

ZZBTreeItem.prototype.deleteNode = function(){
	if(this.currentNode!=null){


		var previousBrotherNode=getPreviousBrotherNode(this.currentNode);

		var nextSiblingNode = this.currentNode.nextSibling;
		var parentNode = this.currentNode.parentNode;
		parentNode.removeChild(nextSiblingNode);
		parentNode.removeChild(this.currentNode);
	}else{
		alert("请选中要删除的节点!");
	}
}

/*

 zzb_openIcon 用于点击打开子节点图片

 zzb_closeIcon 用于点击收缩子节点图片

 */
ZZBTreeItem.prototype.setImages = function (zzb_openIcon,zzb_closeIcon){
	zzbTreeConfig.zzb_openIcon=zzb_openIcon;
	zzbTreeConfig.zzb_closeIcon=zzb_closeIcon;
}

/*
 设置节点是复选框状态
 */
ZZBTreeItem.prototype.setNodeCheckboxEnable = function(obj,bool){
	if(bool==true){
		if(getInputNode(obj).disabled!=false){
			getInputNode(obj).disabled=false;
			renewChildStatus(obj);
		}
	}else{
		if(getInputNode(obj).disabled!=true){
			obj.history=getCheckStatusByNodeObj(obj);//保存当前节点,的状态
			getInputNode(obj).previousSibling.style.display="none";
			getInputNode(obj).style.display="";
			getInputNode(obj).disabled=true;
		}

		/*
		 保存子节点状态
		 */
		//savaChildNodeCheckboxStatus(obj);
	}
}

/*
 保存子节点状态
 */
function savaChildNodeCheckboxStatus(obj){
	var childNodes=getChildNodes(obj);
	if(childNodes!=null && childNodes.length>0){
		for(var i=0;i<childNodes.length;i++){
			childNodes[i].history=getCheckStatusByNodeObj(childNodes[i]);
		}
	}
}

/*
 恢复子节点上次状态
 */
function renewChildStatus(obj){
	var childNodes=getChildNodes(obj);
	if(obj!=null){
		if(obj.getAttribute("history")=="gray"){
			setGray(obj);
		}else if(obj.getAttribute("history")=="true"){
			selectChecked(getInputNode(obj));
		}else if(obj.getAttribute("history")=="false"){
			clearChecked(getInputNode(obj));
		}
	}
	if(childNodes!=null && childNodes.length>0){
		for(var i=0;i<childNodes.length;i++){
			if(childNodes[i].getAttribute("history")=="gray"){
				setGray(childNodes[i]);
			}else if(childNodes[i].getAttribute("history")=="true"){
				selectChecked(getInputNode(childNodes[i]));
			}else if(childNodes[i].getAttribute("history")=="false"){
				clearChecked(getInputNode(childNodes[i]));
			}
		}
	}

}

////////////////begin 静态树查询//////////////////////////////////////////////////////////////////////////
/*
 用查询出来的记录,重新生成树第一层记录
 obj为查询到的节点,
 MainDiv为存储重构节点的层对象,
 rootObj为存储重构节点的根节点对象
 bool 用于是否显示全路径

 */
function buildTree(obj,MainDiv,rootObj,bool,imgClick,checkboxClick,aClick,dbonclick){
	//var tree=new ZZBTreeItem(MainDiv,"","jsp");
	var tree=getObjByMainDivId(MainDiv.id);
	rootObj.setAttribute("md",MainDiv.id);
	var aObj=getAChildNode(obj);
	var showTitle="";
	if(bool){
		showTitle=getNodeNameUrl(obj,"/");
	}
	var parentObj=tree.copyNode(obj,rootObj,imgClick,checkboxClick,aClick,showTitle,dbonclick);
	//buildTreeChildNode(obj,parentObj,MainDiv,imgClick,checkboxClick,aClick,dbonclick);
}

/*
 交换两个节点对象的属性值
 */
function exchangeNodeValue(obj,targetObj,md){//obj 为查询到的节点,targetObj为重构的节点,md 为存储重构节点的层id
	targetObj.setAttribute("md",md);

	targetObj.setAttribute("gi",obj.getAttribute("gi"));

	targetObj.setAttribute("dd",obj.getAttribute("dd"));
	targetObj.setAttribute("ds",obj.getAttribute("ds"));

	targetObj.setAttribute("iv",obj.getAttribute("iv"));
	targetObj.setAttribute("key",obj.getAttribute("key"));
	targetObj.setAttribute("hc",obj.getAttribute("hc"));
	targetObj.setAttribute("fn",obj.getAttribute("fn"));
	targetObj.setAttribute("fl",obj.getAttribute("fl"));
	targetObj.setAttribute("nt",getNodeText(obj));
	targetObj.setAttribute("da",obj.getAttribute("da"));
	targetObj.setAttribute("ft",obj.getAttribute("ft"));
	targetObj.setAttribute("hc2",obj.getAttribute("hc2"));
	targetObj.setAttribute("cc",obj.getAttribute("cc"));
}

/*
 交换配置信息

 */
ZZBTreeItem.prototype.exchangeConfig = function(md){//obj 为查询到的节点,targetObj为重构的节点,md 为存储重构节点的层id
	///匹配信息
	var tree=getObjByMainDivId(md);
	this.zzb_DynamicLocal=tree.zzb_DynamicLocal;//是否为动态加载
	this.zzb_RadioOrCheckBox=tree.zzb_RadioOrCheckBox;//
	this.zzb_parentRadioEnable=tree.zzb_parentRadioEnable;//
	this.zzb_parentCheckboxEnable=tree.zzb_parentCheckboxEnable;//
	this.zzb_parentChildIsolate=tree.zzb_parentChildIsolate;//
	this.zzb_OnlyImgExpand=tree.zzb_OnlyImgExpand;//
	this.zzb_rootNodeShow=tree.zzb_rootNodeShow;//
	this.zzb_checkboxDisplay=tree.zzb_checkboxDisplay;
	//选中上级节点时，下级节点是否要自动全部选中，是或否 1,0
	this.parentSelectedWithChild=tree.parentSelectedWithChild;
	//取消上级节点的选中状态时，下级节点是否要自动全部取消，是或否 1,0
	this.parentClearWithChild=tree.parentClearWithChild;
	//选中下级节点时，如果下级同级节点已全部都为选中状态，其上级是否自动选中，是或否 1,0
	this.lowerSelectedAllWithSuperior=tree.lowerSelectedAllWithSuperior;
	//取消下级节点的选中状态时，其上级节点选中状态是否要自动取消，是或否 1,0
	this.lowerClearWithSuperior=tree.lowerClearWithSuperior;
	//取消下级节点的选中状态时，其上级节点不自动取消的情况下，如果下级同级节点已全部为未选中状态，其上级是否自动取消，是或否 1,0
	this.lowerClearAllWithSuperior=tree.lowerClearAllWithSuperior;


}

/*
 为第一层记录,添加子节点
 */
function buildTreeChildNode(obj,parentObj,MainDiv,imgClick,checkboxClick,aClick,dbonclick){//obj 为查询到的节点,parentObj为父节点,MainDiv为存储重构节点层对象
	//var tree=new ZZBTreeItem(MainDiv,"","jsp");
	var tree=getObjByMainDivId(MainDiv.id);
	var childs=getDivChildNodes(obj);//得到子节点
	for(var i=0;i<childs.length;i++){
		if(childs[i].nodeName=="DIV"){
			var aObj=getAChildNode(childs[i]);
			/* var selectFunctionArr=childs[i].sf.split("#");
			 var imgClick="";
			 var aClick="";
			 var checkBoxClick="";
			 if(selectFunctionArr.length==3){

			 if(selectFunctionArr[0]!="no"){
			 imgClick=selectFunctionArr[0];
			 }
			 if(selectFunctionArr[1]!="no"){
			 aClick=selectFunctionArr[1];
			 }
			 if(selectFunctionArr[2]!="no"){
			 checkBoxClick=selectFunctionArr[2];
			 }
			 }*/
			var pObj=tree.copyNode(childs[i],parentObj,imgClick,checkboxClick,aClick,"",dbonclick);
			/*if(pObj!=null){
			 tree.querySelected(pObj,keys);
			 }*/
			buildTreeChildNode(childs[i],pObj,MainDiv,imgClick,checkboxClick,aClick,dbonclick);

		}
		i++
	}
}

/*
 obj	:存储静态节点的最高层对象 (所有查询出来的节点都来源于此)
 nodeName :用于搜索的节点名
 resultObjArr: 用于收集搜索得到对象的,数组变量
 MainDiv:保存查询结果的层对象
 rootObj: 查询结果的根节点
 matching:查询匹配 0不匹配 1完全匹配 2模糊匹配
 matching[0] 节点名
 matching[1] key
 matching[2] 助记码1
 matching[3] 助记码2
 matching[4] 全称
 matching[5] 简称
 matching[6] 全路径
 */
function queryLoadNode(obj,queryItem,resultObjArr,matching){
	try{
		if(obj!=null && obj.nodeName=="DIV" &&parseInt(obj.getAttribute("cs"))>0){
			//if(obj!=null && parseInt(obj.getAttribute("cs"))>0){
			var bool=false;
			var num=-1;
			if(queryItem!=null && queryItem!="" && obj.getAttribute("nt")!=undefined){//节点名查询
				if(matching[0]==1){
					if (obj.getAttribute("nt").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[0]==2){

					if (obj.getAttribute("nt").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}

			}
			if(queryItem!=null && queryItem!="" && bool==false){//key值查询
				if(matching[1]==1){
					if (obj.getAttribute("key").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[1]==2){
					if (obj.getAttribute("key").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}

			}
			if(queryItem!=null && queryItem!="" && bool==false){//助记码1查询
				if(matching[2]==1){
					if (obj.getAttribute("hc").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[2]==2){
					if (obj.getAttribute("hc").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}

			}
			if(queryItem!=null && queryItem!="" && bool==false){//助记码2查询
				if(matching[3]==1){
					if (obj.getAttribute("hc2").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[3]==2){
					if (obj.getAttribute("hc2").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}

			}
			if(queryItem!=null && queryItem!="" && bool==false){
				if(matching[4]==1){
					if (obj.getAttribute("fn").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[4]==2){
					if (obj.getAttribute("fn").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}
			}
			if(queryItem!=null && queryItem!="" && bool==false){
				if(matching[5]==1){
					if (obj.getAttribute("ft").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[5]==2){
					if (obj.getAttribute("ft").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}
			}
			if(queryItem!=null && queryItem!="" && bool==false){
				if(matching[6]==1){
					if (obj.getAttribute("fl").toLowerCase()==queryItem.toLowerCase())
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}else if(matching[6]==2){
					if (obj.getAttribute("fl").toLowerCase().indexOf(queryItem.toLowerCase())!=num)
					{
						resultObjArr[resultObjArr.length]=obj;
						bool=true;
					}
				}
			}

		}
		if(obj.childNodes.length>0) {
			var childs = getDivChildNodes(obj);
			if (childs != null) {
				for (var i = 0; i < childs.length; i++) {
					var len = resultObjArr.length;
					resultObjArr = queryLoadNode(childs[i], queryItem, resultObjArr, matching);
					i++;
				}
			}
		}

	}catch(e){
		alert("* queryLoadNode " + e.description + "*");
	}
	return resultObjArr;
}

/*
 用于条件为空时,加载整颗树
 obj	:存储静态节点的最高层对象 (所有查询出来的节点都来源于此)
 nodeName :用于搜索的节点名
 resultObjArr: 用于收集搜索得到对象的,数组变量
 MainDiv:保存查询结果的层对象
 rootObj: 查询结果的根节点
 */
function queryLoadAllNode(obj,resultObjArr){
	try{
		/*var aObj=getAChildNode(obj);
		 if(obj.cs!="1"){
		 resultObjArr[resultObjArr.length] = obj;
		 }*/
		/*if(obj.childNodes.length>0){
		 var childs=getDivChildNodes(obj);
		 for(var i=0; i<childs.length; i++){
		 var len = resultObjArr.length;
		 if(childs[i].cs=="1"){
		 resultObjArr[resultObjArr.length] = childs[i];
		 }
		 if(childs[i].cs!="1"){//不是第一层节点,递归查找
		 queryLoadAllNode(childs[i],resultObjArr);
		 }
		 i++;
		 }
		 }
		 */
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("cs")=="1"){
						resultObjArr[resultObjArr.length] = child;
					}
					//////////////////
					var cengCount=child.getAttribute("cs");
					child = child.nextSibling;
					if (child.hasChildNodes() && cengCount!="1"){
						bool = queryLoadAllNode(child,resultObjArr);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* queryLoadAllNode " + e.description + "*");
	}
	return resultObjArr;
}

/*
 用于搜索静态树,把找到的节点,重新构选成一颗树
 nodeName 查询的节点名
 MainDiv 用于保存查询结果的层对象
 contextPath 系统当前路径
 queryItem 查询条件值

 */
ZZBTreeItem.prototype.queryLoadNode = function(queryItem,MainDiv,contextPath,imgClick,checkboxClick,aClick,matching,keys,dbonclick){
	var querySize=0;
	try{
		MainDiv.innerHTML="";
		var resultArr=new Array();
		var tree=new ZZBTreeItem(MainDiv,contextPath,"jsp");//创建存储树对象
		tree.exchangeConfig(this.MainDiv.id);//复制配置信息
		var rootObj=tree.addRootNode(" 根节点","javascript:{}","javascript:{}",false);//创建根节点
		rootObj.setAttribute("cs","0");//层次数
		rootObj.setAttribute("md",MainDiv.id);
		if(queryItem==null || queryItem==""){//如果查询条件为空的情况下
			resultArr=queryLoadAllNode(this.MainDiv,resultArr);//加载所有节点
			for(var i=0;i<resultArr.length;i++){
				buildTree(resultArr[i],MainDiv,rootObj,false,imgClick,checkboxClick,aClick,dbonclick);//重新构造树
			}

			tree.querySelecteds(keys);//通过这组key值,在查询结果中,设置节点复选框勾上
		}else{
			resultArr=queryLoadNode(this.MainDiv,queryItem,resultArr,matching);//通过条件查询命节点
			if(resultArr!=null && resultArr.length>0){
				for(var i=0;i<resultArr.length;i++){
					buildTree(resultArr[i],MainDiv,rootObj,true,imgClick,checkboxClick,aClick,dbonclick);//将命中的节点,重新构造树
				}
			}
			tree.querySelecteds(keys);//通过这组key值,在查询结果中,设置节点复选框勾上
		}
		anewInitialize(MainDiv);
		if(resultArr!=null && resultArr.length>0){
			querySize=resultArr.length;
		}

	}catch(e){
		alert("ZZBTreeItem.prototype.queryLoadNode : " + e.description);
	}
	return querySize;
}

/*
 查询命的节点,复选框自动选中
 */
ZZBTreeItem.prototype.querySelecteds = function(keys){
	try{
		var keyArr=keys.split(',');
		var resultArr=this.getAllNodes();
		if(resultArr!=null && resultArr.length>0){
			for(var i=0;i<resultArr.length;i++)
			{
				this.querySelected(resultArr[i],keys);
			}
		}
	}catch(e){
		alert(" querySelecteds : " + e.description);
	}
}
/*
 通过key值设置对象选中
 */
ZZBTreeItem.prototype.querySelected = function(obj,keys){
	try{
		var bool=false;
		var keyArr=keys.split(',');
		for(var j=0;j<keyArr.length;j++)
		{
			if(keyArr[j]!=null && keyArr[j]!="")
			{
				if(obj.getAttribute("key")==keyArr[j])
				{
					bool=true;
				}
			}
		}
		if(bool){
			checkSel(getInputNode(obj));
			this.ExpandNodeByObj(obj);
		}
	}catch(e){
		alert(" querySelected : " + e.description);
	}
}

/*

 */


/*
 重新初始树
 obj 为存储树的层对象
 */
function anewInitialize(obj){

	/*if(obj!=null){
	 if(obj.childNodes.length>0){
	 var childs=getDivChildNodes(obj);
	 for(var i=0; i<childs.length; i++){
	 if(childs[i].nodeName=="DIV" && parseInt(childs[i].cs)>0 ){
	 changeOperateImg(childs[i]);
	 }
	 anewInitialize(childs[i]);
	 i++;
	 }
	 }
	 }*/

	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null){
			if (child.nodeName == "DIV"){
				//////////////////
				if(parseInt(child.getAttribute("cs"))>0){
					changeOperateImg(child);
				}
				//////////////////
				child = child.nextSibling;
				if (child.hasChildNodes()){
					bool = anewInitialize(child);
				}
			}
			child = child.nextSibling;
		}
	}

}
////////////////////////静态树查询 end//////////////////////////////////////////////////////////////////////////


////////////////二次开发使用函数 begin///////////////////////////////////////////////////////////////////////
/*

 */
ZZBTreeItem.prototype.setNodeCheckboxSelected = function(obj,bool){
	if(bool==true){
		selectChecked(getInputNode(obj));
		checkSel(getInputNode(obj));
	}else{
		clearChecked(getInputNode(obj));
		checkSel(getInputNode(obj));
	}
}

/*
 设置复选框选中状态,不会对父子节点选成影响
 */
ZZBTreeItem.prototype.setCheckboxSelected = function(obj,bool){
	if(bool==true){
		selectChecked(getInputNode(obj));
	}else{
		clearChecked(getInputNode(obj));
	}
	//alert(obj.getAttribute("nt"));
	changeNodeSelectAllStatus(obj);
}

/**
 设置节点及下属节点复选框选中
 */
ZZBTreeItem.prototype.setBoxAndChildBoxSelected = function(obj,bool){
	if(bool==true){
		selectChecked(getInputNode(obj));
		checkSel(getInputNode(obj));
	}else{
		clearChecked(getInputNode(obj));
		checkSel(getInputNode(obj));
	}
}

/**
 设置节点复选框选中
 */
ZZBTreeItem.prototype.setBoxSelected = function(obj,bool){
	if(bool==true){
		selectChecked(getInputNode(obj));
	}else{
		clearChecked(getInputNode(obj));
	}
}

/*
 得到节点的checkbox选中状态

 */
ZZBTreeItem.prototype.getCheckboxSelectedByNode = function(obj){
	return getInputNode(obj).checked;
}

/*
 通过复选框对象得到节点对象
 */
ZZBTreeItem.prototype.getNodeByCheckbox = function(checkboxObj){
	return checkboxObj.parentNode;
}

/*
 得到父节点

 */
ZZBTreeItem.prototype.getTreeParentNode = function(obj){
	return getParentNode(obj);
}

/*
 得到子节点

 */
ZZBTreeItem.prototype.getTreeParentChildNodes = function(obj){
	return retObj=getChildNodes(obj);

}

/*
 得到当前节点的信息 返回值为一个array
 0 为键值

 1 为助记码
 2 节点标题
 */
ZZBTreeItem.prototype.getCurrentNodeInfo = function(){
	if(this.currentNode==null){
		return null;
	}else{
		return getNodeInfo(this.currentNode);
	}
}


ZZBTreeItem.prototype.setCurrentNodeNavigation = function(currentNodeNavigation){
	this.currentNodeNavigation=currentNodeNavigation;
}

/*
 得到当前节点对象
 */
ZZBTreeItem.prototype.getCurrentNodeObj = function(){
	return this.currentNode;
}


/*
 得到选中的checkbox的值

 */
ZZBTreeItem.prototype.getInputValues = function(){
	var resultArr=new Array();
	resultArr=getQueryInputValue(this.MainDiv,resultArr);
	return resultArr;
}

/*
 得到选中的checkbox的值 和 节点的信息

 获得多组checkbox和节点的信息
 每组同一个array保存
 0 为 checkBox值

 1 为key值

 2 为节点名


 */
ZZBTreeItem.prototype.getInputValueAndNodeInfo = function(){
	var resultArr=new Array();
	resultArr=getQueryInputValueAndNodeInfo(this.MainDiv,resultArr);
	return resultArr;
}

/*
 得到选中的checkbox的值 和 节点的信息

 获得多组checkbox和节点的信息
 每组同一个array保存
 array[0] 为 checkBox值

 array[1] 为key值

 array[2] 为助记码
 array[3] 为节点名全路径

 */
ZZBTreeItem.prototype.getInputValueAndNodeUrlInfo = function(){
	var resultArr=new Array();
	resultArr=getQueryInputValueAndNodeUrlInfo(this.MainDiv,resultArr);
	return resultArr;
}

/*
 得到当前节点的自定义属性值
 */
ZZBTreeItem.prototype.getCurrentDynamicAttriValue = function (){
	return this.currentNode.getAttribute("da");
}

/*
 得到节点的自定义属性值
 */
ZZBTreeItem.prototype.getDynamicAttriValue = function (obj){
	return obj.getAttribute("da");
}

/*
 得到选中状态下的自定义属性值
 iv,key,hc,NodeNameUrl,da

 */
ZZBTreeItem.prototype.getSelectedDynamicAttriValues = function (){
	var resultArr=new Array();
	resultArr=getQuerySelectedAllInfo(this.MainDiv,resultArr,"da");
	return resultArr;

}

/*
 得到选中状态或灰色图片状态下的节点信息
 iv,key,hc,NodeNameUrl,da,checkStatus

 */

ZZBTreeItem.prototype.getAllSelectedOrGrayNodesInfos = function (){
	var resultArr=new Array();
	resultArr=getQueryAllSelectedOrGrayInfo(this.MainDiv,resultArr,"iv,key,hc,NodeNameUrl,da,checkStatus");
	return resultArr;

}

/*
 得到所有灰勾的信息

 */
ZZBTreeItem.prototype.getAllGrayInfo = function (){
	var resultArr=new Array();
	resultArr=getQueryAllGrayInfo(this.MainDiv,resultArr,"iv,key,hc,NodeNameUrl,da,checkStatus");
	return resultArr;
}

/*
 得到所有节点信息
 */
ZZBTreeItem.prototype.getAllNodesInfos = function (){
	var resultArr=new Array();
	resultArr=getQueryAllInfo(this.MainDiv,resultArr,"iv,key,hc,NodeNameUrl,da,checkStatus");

	return resultArr;

}

/*
 选中所有节点

 */
ZZBTreeItem.prototype.selectedAllNodes = function (){
	var resultArr=new Array();
	resultArr=selectedAllNodes(this.MainDiv,resultArr);
	return resultArr;
}

/*
 取消所有选中节点
 */
ZZBTreeItem.prototype.clearAllNodes = function (){
	clearAllNodes(this.MainDiv);
}

/*
 得到所有节点

 */
ZZBTreeItem.prototype.getAllNodes = function (){
	var resultArr=new Array();
	resultArr=getQueryAllNodes(this.MainDiv,resultArr);
	return resultArr;
}


function getAllNodesByStatus(obj,resultObjArr,status){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					if(child.getAttribute("nt")!=undefined && isExistBigCengCounto(child) && child.getAttribute("icd")=="1"){
						var checkboxObj=getInputNode(child);
						if(checkboxObj.checked==true){
							resultObjArr[resultObjArr.length]=child;
						}
					}
					//////////////////
					child = child.nextSibling;
					if (child.hasChildNodes()){
						bool = getAllNodesByStatus(child,resultObjArr,status);
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		//alert("* getQueryAllNodes err *" + e.description + "*");
	}
	return resultObjArr;
}
/*
 得到所有节点 按照指定的状态
 总共有四种状态 gray false true disabled
 代码: Tree1.getCheckboxStatus(obj)

 */
ZZBTreeItem.prototype.getAllNodesByStatus = function(status){
	var resultArr=new Array();
	var newResultArr=new Array();
	getAllNodesByStatus(this.MainDiv,newResultArr,status);
	/*
	 resultArr=getQueryAllNodes(this.MainDiv,resultArr);
	 for(var i=0;i<resultArr.length;i++){
	 if(status.indexOf(getCheckboxStatusByNodeObj(resultArr[i]))!=-1){
	 newResultArr[newResultArr.length]=resultArr[i];
	 }
	 }
	 */
	return newResultArr;
}

/*
 得到一个节点的,所有属性
 */

/*
 设置节点的自定义属性值
 */
ZZBTreeItem.prototype.setDynamicAttriValue = function (obj,value){
	obj.setAttribute("da",value);
}

/*
 得到节点全路径
 */
ZZBTreeItem.prototype.getNodeNameUrl = function (obj,prefix){
	return getNodeNameUrl(obj,prefix);
}

/*
 返回disabled的状态
 */
ZZBTreeItem.prototype.getNodeCheckboxEnable = function(obj){
	return  getInputNode(obj).disabled;
}

/*
 返回checkbox的状态
 gray false true disabled
 */
ZZBTreeItem.prototype.getCheckboxStatus = function(obj){
	return  getCheckboxStatusByNodeObj(obj);
}


/*
 得到节点信息
 */
ZZBTreeItem.prototype.getNodeAllInfos = function(obj){
	return  getNodeInfos(obj);
}

/*
 得到节点全称
 */
ZZBTreeItem.prototype.getNodeFullName = function(obj){
	return  obj.getAttribute("fn");
}

/*
 展开对像,参数为要展开的子节点
 */
ZZBTreeItem.prototype.ExpandNodeByObj = function(obj){
	var resultArr=new Array();
	resultArr=ExpandNodeByObj(this.MainDiv,obj,false,resultArr);
	if(resultArr.length>0){
		divFocus(resultArr[0]);
	}
}

/*
 得到节点打开状态 true 为展开 false 为收缩
 */
ZZBTreeItem.prototype.getNodeOpenStatus = function(obj){
	//return isExsitOpenNode(obj);
	return isExsitOpenOrChildNode(obj);
}

/*
 得到所有节点的,所有属性
 */
ZZBTreeItem.prototype.getAllNodeAttribute =function(){
	try{
		var resultArr=new Array();
		var resultArr=getQueryAllNodes(this.MainDiv,resultArr);
		var attriArr=new Array();
		if(resultArr!=null && resultArr.length>0){
			for(var i=0;i<resultArr.length;i++){
				attriArr[attriArr.length]=getNodeInfos(resultArr[i]);
			}
		}
		return attriArr;
	}catch(e){
		alert(" ZZBTreeItem.prototype.getAllNodeAttributes : " + e.description);
		return null;
	}
}

/*
 得到一个节点的所有属性
 */
ZZBTreeItem.prototype.getNodeAttribute = function(obj){
	return getNodeInfos(obj);
}

/*
 得到选中节点的所有属性
 */
ZZBTreeItem.prototype.getSelectedNodeAttribute = function(){
	try{
		var selectedArr=new Array();
		var resultArr=new Array();

		resultArr=getQueryAllNodes(this.MainDiv,resultArr);

		for(var i=0;i<resultArr.length;i++){
			if(getCheckStatusByNodeObj(resultArr[i])=="true"){
				selectedArr[selectedArr.length]=getNodeInfos(resultArr[i]);
			}
		}

		return selectedArr;

	}catch(e){
		return null;
	}
}

/*
 设置单选有效 默认为有效 true有效  false无效
 */
ZZBTreeItem.prototype.setRadioEnable = function(obj,bool){
	if(obj!=null){
		if(bool){
			obj.setAttribute("rn","1");
		}else{
			obj.setAttribute("rn","0");
		}
	}
}

/*
 返回单选时,节点是否有效 true为有效 false无效
 */
ZZBTreeItem.prototype.getRadioEnable = function (obj){
	if(obj!=null){
		if(obj.getAttribute("rn")=="1"){
			return true;
		}else if(obj.getAttribute("rn")=="0"){
			return false;
		}else{
			return true;
		}
	}else{
		return null;
	}
}

/*
 设置默keys值
 */
ZZBTreeItem.prototype.setDefaultKeys = function(keys){
	this.defaultKeys=keys;
}

/*
 设置默keys值
 */
ZZBTreeItem.prototype.getDefaultKeys = function(){
	return this.defaultKeys;
}

////////////////二次开发使用函数 end///////////////////////////////////////////////////////////////////////

///////////////对配置信息进行设置 begin////////////////////////////////////////////////////////////////////
/*
 true 为支持动态加载

 false 不支持动态加载

 设置动态加载

 */
ZZBTreeItem.prototype.setDynamicLocal = function (bool){
	this.zzb_DynamicLocal=bool;
}

/*
 bool 设置为true支持复选 false为单选

 */
ZZBTreeItem.prototype.setRadioOrCheckbox = function(bool){
	if(bool=="radio"){
		this.zzb_RadioOrCheckBox=false;
	}else{
		this.zzb_RadioOrCheckBox=true;
	}
}

/*
 单选情况下是否可以选中父节点
 */
ZZBTreeItem.prototype.setRadioSelectParent = function(bool){
//zzb_RadioSelectParent
	this.zzb_parentRadioEnable=bool;
}

/*
 复选的情况下父节点是否可以选中
 */
ZZBTreeItem.prototype.setCheckSelectParent = function(bool){
//
//zzb_CheckSelectParent
	zzbTreeConfig.zzb_parentCheckboxEnable=bool;
	this.zzb_parentCheckboxEnable=bool;

}

/*
 设置为true 选择父节点时子节点全部选中,取消时全部取消(子节点部分选中时，父节点选中变灰（灰勾）
 */
ZZBTreeItem.prototype.setCheckBoxSelectWay = function(bool){
	//zzb_CheckBoxSelectWay
	zzbTreeConfig.zzb_parentChildIsolate=bool;
	this.zzb_parentChildIsolate=bool;
}

/*
 配置单选信息

 */
ZZBTreeItem.prototype.setRadioConfig = function(radioBool,selectParentBool){
	if(radioBool==true){
		this.zzb_RadioOrCheckBox=false;
	}
	this.zzb_parentRadioEnable=selectParentBool;
}

/*
 true只支持图片展开,false文字也可以展开
 */
ZZBTreeItem.prototype.setOnlyImgExpand = function(bool){
	this.zzb_OnlyImgExpand=bool;
}

/*
 设置根节点是否显示
 */
ZZBTreeItem.prototype.setRootNodeShow = function(bool) {
	this.zzb_rootNodeShow=bool;
}

ZZBTreeItem.prototype.openFirstNode = function(){

	var firstFloor=this.getFirstFloorNode();
	//进行动态加载节点
	Ldn(getImgChildNode(firstFloor[0]));
	this.ExpandNodeAndNextNode(firstFloor[0]);
	this.setCurrentNodeByNode(firstFloor[0]);
}

/**
 设置默认函数
 */
ZZBTreeItem.prototype.setOnclicks = function(onclicks) {
	var onclickArr=onclicks.split(";");
	for(var i=0;i<onclickArr.length;i++){
		if(onclickArr[i]!=""){
			//给函数添加默认参数
			var params=onclickArr[i].substring(onclickArr[i].indexOf("(")+1,onclickArr[i].lastIndexOf(")"));
			//得到函数名
			var functionName=onclickArr[i].substring(0,onclickArr[i].indexOf("("));
			//得到函数参数
			var functionParam=onclickArr[i].substring(onclickArr[i].indexOf("(")+1,onclickArr[i].lastIndexOf(")"));
			var str="";
			if(params!=""){
				//本身函数有参数的情况下的处理方式
				str=functionName+"("+"_sanTree_div_Node_obj,"+functionParam+")";
			}else{
				//本身函数没有参数的情况下的处理方式
				str=functionName+"("+"_sanTree_div_Node_obj)";
			}
			this.onclicks[this.onclicks.length]=str;
		}
	}
}

/**
 设置当前树
 参数bool为boolean值 true设置为当前树,false设置为非当前树
 */
ZZBTreeItem.prototype.focus = function(bool){
	setTreeFocus(bool,this.MainDiv.id);
}

/*
 得到所有子节点包括，子节点的子节点
 */
ZZBTreeItem.prototype.getAllChildNodes = function(obj){
	var results=new Array();
	results=getAllChildNodes(obj.nextSibling,results);
	return results;
}
ZZBTreeItem.prototype.getAllChildNodesByStatus = function(obj,status){
	var results=new Array();
	results=getAllChildNodesByStatus(obj.nextSibling,results,status);
	return results;
}

/**
 得到下一级子节点
 */
ZZBTreeItem.prototype.getNextChildNodes = function(obj){
	var results=new Array();
	results=getNextChildNodes(obj.nextSibling,results);
	return results;
}

/*
 obj 为节点对象
 name 节点属性标识
 通过属性的标识 得到属性值
 */
ZZBTreeItem.prototype.getValueByName = function(obj,name){
	/*
	 key 节点键值,
	 helpCode 助记码,
	 text 节点标题
	 dynamicAttri 动态扩展属性,
	 fullName 节点全称,
	 forShort 节点标题简称,
	 helpCode2 助记码2,
	 cs 节点层次数
	 icd 复选框是显示状态
	 dd 是否为动态节点
	 dc 节点是否已经被加载
	 selAll 子节点选中状态
	 */
	var retStr="";
	if(obj!=null && name!=""){
		if(name.toLowerCase()=="key"){
			retStr=obj.getAttribute("key");
		}else if(name.toLowerCase()=="text"){
			retStr=obj.getAttribute("nt");
		}else if(name.toLowerCase()=="helpcode"){
			retStr=obj.getAttribute("hc");
		}else if(name.toLowerCase()=="helpcode2"){
			retStr=obj.getAttribute("hc2");
		}else if(name.toLowerCase()=="dynamicattri"){
			retStr=obj.getAttribute("da");
		}else if(name.toLowerCase()=="fullname"){
			retStr=obj.getAttribute("fn");
		}else if(name.toLowerCase()=="forshort"){
			retStr=obj.getAttribute("ft");
		}else if(name.toLowerCase()=="checkboxdisplay"){
			retStr=obj.getAttribute("icd");
		}else if(name.toLowerCase()=="selectall"){
			retStr=obj.getAttribute("selAll");
		}else if(name.toLowerCase()=="dc"){
			retStr=obj.getAttribute("dc");
		}else if(name.toLowerCase()=="dd"){
			retStr=obj.getAttribute("dd");
		}
	}
	return retStr;
}
/*
 obj 为节点对象
 name 节点属性标识
 通过属性的标识 得到属性值
 */
function getValueByName(obj,name){
	/*
	 key 节点键值,
	 helpCode 助记码,
	 text 节点标题
	 dynamicAttri 动态扩展属性,
	 fullName 节点全称,
	 forShort 节点标题简称,
	 helpCode2 助记码2,
	 dd 是否为动态节点
	 dc 节点是否已经被加载
	 cengCount 节点层次数
	 iv 复选框的值
	 icd 复选框是显示状态
	 selAll 子节点选中状态
	 */
	var retStr="";
	if(obj!=null && name!=""){
		if(name.toLowerCase()=="key"){
			retStr=obj.getAttribute("key");
		}else if(name.toLowerCase()=="text"){
			retStr=obj.getAttribute("nt");
		}else if(name.toLowerCase()=="helpcode"){
			retStr=obj.getAttribute("hc");
		}else if(name.toLowerCase()=="helpcode2"){
			retStr=obj.getAttribute("hc2");
		}else if(name.toLowerCase()=="dynamicattri"){
			retStr=obj.getAttribute("da");
		}else if(name.toLowerCase()=="fullname"){
			retStr=obj.getAttribute("fn");
		}else if(name.toLowerCase()=="forshort"){
			retStr=obj.getAttribute("ft");
		}else if(name.toLowerCase()=="checkboxdisplay"){
			retStr=obj.getAttribute("icd");
		}else if(name.toLowerCase()=="dc"){
			retStr=obj.getAttribute("dc");
		}else if(name.toLowerCase()=="dd"){
			retStr=obj.getAttribute("dd");
		}else if(name.toLowerCase()=="cengcount"){
			retStr=obj.getAttribute("cs");
		}else if(name.toLowerCase()=="inputvalue"){
			retStr=obj.getAttribute("iv");
		}else if(name.toLowerCase()=="selectall"){
			retStr=obj.getAttribute("selAll");
		}
	}
	return retStr;
}

/*
 obj 为节点对象
 name 节点属性标识
 value 属性值
 通过属性的标识 设置属性值
 */
ZZBTreeItem.prototype.setValueByName = function(obj,name,value){
	/*
	 key 节点键值,
	 helpCode 助记码,
	 text 节点标题,
	 dynamicAttri 动态扩展属性,
	 fullName 节点全称,
	 forShort 节点标题简称,
	 helpCode2 助记码2,
	 dc 动态加载次数
	 dd 是否为动态加载子节点
	 cengCount 节点层次数
	 */
	if(obj!=null && name!=""){
		if(name.toLowerCase()=="key"){
			obj.setAttribute("key",value);
		}else if(name.toLowerCase()=="text"){
			obj.setAttribute("nt",value);
		}else if(name.toLowerCase()=="helpcode"){
			obj.setAttribute("hc",value);
		}else if(name.toLowerCase()=="helpcode2"){
			obj.setAttribute("hc2",value);
		}else if(name.toLowerCase()=="dynamicattri"){
			obj.setAttribute("da",value);
		}else if(name.toLowerCase()=="fullname"){
			obj.setAttribute("fn",value);
		}else if(name.toLowerCase()=="forshort"){
			obj.setAttribute("ft",value);
		}else if(name.toLowerCase()=="checkboxdisplay"){
			obj.setAttribute("icd",value);
		}else if(name.toLowerCase()=="dc"){
			obj.setAttribute("dc",value);
		}else if(name.toLowerCase()=="dd"){
			obj.setAttribute("dd",value);
		}else if(name.toLowerCase()=="cengCount"){
			obj.setAttribute("cs",value);
		}
	}
}

/*
 obj 为节点对象
 name 节点属性标识
 value 属性值
 通过属性的标识 设置属性值
 */
function setValueByName(obj,name,value){
	/*
	 key 节点键值,
	 helpCode 助记码,
	 text 节点标题,
	 dynamicAttri 动态扩展属性,
	 fullName 节点全称,
	 forShort 节点标题简称,
	 helpCode2 助记码2,
	 dc 动态加载次数
	 dd 是否为动态加载子节点
	 cengCount 节点层次数
	 */
	if(obj!=null && name!=""){
		if(name.toLowerCase()=="key"){
			obj.setAttribute("key",value);
		}else if(name.toLowerCase()=="text"){
			obj.setAttribute("nt",value);
		}else if(name.toLowerCase()=="helpcode"){
			obj.setAttribute("hc",value);
		}else if(name.toLowerCase()=="helpcode2"){
			obj.setAttribute("hc2",value);
		}else if(name.toLowerCase()=="dynamicattri"){
			obj.setAttribute("da",value);
		}else if(name.toLowerCase()=="fullname"){
			obj.setAttribute("fn",value);
		}else if(name.toLowerCase()=="forshort"){
			obj.setAttribute("ft",value);
		}else if(name.toLowerCase()=="checkboxdisplay"){
			obj.setAttribute("icd",value);
		}else if(name.toLowerCase()=="dc"){
			obj.setAttribute("dc",value);
		}else if(name.toLowerCase()=="dd"){
			obj.setAttribute("dd",value);
		}else if(name.toLowerCase()=="cengCount"){
			obj.setAttribute("cs",value);
		}
	}
}


//2009-02-27////////////////////////////////////////////////////////////////////

/*
 检测下一层子节点选中状态
 obj:节点对象
 returnValue:true为全部选中,false为没有全部选中
 */
function isExistNextChildSelect(obj){
	obj=obj.nextSibling;
	var bool=true;
	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null && bool==true){
			if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
				var checkboxObj=getInputNode(child);
				if(checkboxObj!=null &&(checkboxObj.checked==false || child.getAttribute("selAll")=="0")){
					bool=false;
				}
			}
			child = child.nextSibling;
		}
	}
	return bool;
}
/*
 检测子节点选中状态
 obj:节点对象
 returnValue:true为全部选中,false为没有全部选中
 */
function isExistChildSelect(obj,bool){
	if (obj.hasChildNodes()){
		var child = obj.firstChild;
		while(child !=  null && bool==true){
			if (child.nodeName == "DIV" && child.getAttribute("nt")!=undefined){
				var checkboxObj=getInputNode(child);
				if(checkboxObj!=null && checkboxObj.checked==false){
					bool=false;
				}
				child = child.nextSibling
				if(bool==true){
					isExistChildSelect(child,bool);
				}
			}
			child = child.nextSibling;
		}
	}
	return bool;
}

/*
 对节点的子节点选中状态值进行自己处理
 obj:节点对象
 */
function changeNodeSelectAllStatus(obj){
	var tree=getObjByMainDivId(obj.getAttribute("md"));
	changeNodeSelectAllStatus2Child(obj, tree); //向下递归处理所有层的子节点（包括自己）的“全选”状态

	//检测试当前节点的子节点选中状态
//   var bool=isExistChildSelect(obj.nextSibling,true);
//   if(bool==true){
//     obj.setAttribute("selAll","1");//全部选中
//   }else{
//     obj.setAttribute("selAll","0");//未全部选中
//   }
	// 向上递归处理所有层的父节点的“全选”状态，不包括自己
	changeNodeSelectAllStatus2Parent(tree.getTreeParentNode(obj),tree);
}
//向下递归处理所有层的子节点（包括自己）的“全选”状态
function changeNodeSelectAllStatus2Child(obj,tree){
	try{
		if(obj!=null){
			// 判断是否存在子节点，如果有，则继续递归；如果无，则设置“全选”状态后，退出
			var retObj = getChildNodes(obj);
			if(retObj!=null && retObj.length>0){
				for(var i =0 ;i<retObj.length;i++){
					changeNodeSelectAllStatus2Child(retObj[i], tree);
				}
			}
			//alert(obj.getAttribute("nt")+" begin: " + getValueByName(obj,"selectall"));
			var bool=isExistNextChildSelect(obj);
			if(bool==true){
				obj.setAttribute("selAll","1");//全部选中
			}else{
				obj.setAttribute("selAll","0");//未全部选中
				//判断其父节点所包含的其他节点(也就是兄弟节点)是否改变selALL状态为1。
			}
		}
	}catch(e){
		alert("changeNodeSelectAllStatus2Child err:" + e.description);
	}
}

// 向上递归处理所有层的父节点的“全选”状态
function changeNodeSelectAllStatus2Parent(obj,tree){
	try{
		if(obj!=null){
			//alert(obj.getAttribute("nt")+" begin: " + getValueByName(obj,"selectall"));
			var bool=isExistNextChildSelect(obj);
			if(bool==true){
				obj.setAttribute("selAll","1");//全部选中
			}else{
				obj.setAttribute("selAll","0");//未全部选中
				//判断其父节点所包含的其他节点(也就是兄弟节点)是否改变selALL状态为1。
				var retObj=getChildNodes(obj);
				if(retObj!=null && retObj.length>0){
					for(var i =0 ;i<retObj.length;i++){
						var booool=isExistNextChildSelect(retObj[i]);
						if(booool ==true){
							retObj[i].setAttribute("selAll","1");//全部选中
						}else{
							retObj[i].setAttribute("selAll","0");//全部选中
						}
					}
				}
			}
			//alert(obj.getAttribute("nt")+" begin: " + getValueByName(obj,"selectall"));
			var parentObj=tree.getTreeParentNode(obj);
			changeNodeSelectAllStatus2Parent(parentObj,tree);

		}
	}catch(e){
		alert("changeNodeSelectAllStatus2Parent err:" + e.description);
	}
}

function getNodeBySelAll(obj,resultObjArr){
	try{
		if (obj.hasChildNodes()){
			var child = obj.firstChild;
			while(child !=  null){
				if (child.nodeName == "DIV"){
					//////////////////
					var checkboxObj=null;
					if(child.getAttribute('nt')!=undefined && isExistBigCengCounto(child) && child.getAttribute('icd')=="1"){
						checkboxObj=getInputNode(child);

						if(checkboxObj.checked==true){
							resultObjArr[resultObjArr.length]=child;
						}
					}
					//////////////////
					//alert(child.getAttribute("nt") + " " + child.selAll);
					if(child.getAttribute("selAll")=="0" || (checkboxObj!=null && checkboxObj.checked==false)){
						child = child.nextSibling;
						if (child.hasChildNodes()){
							bool = getNodeBySelAll(child,resultObjArr);
						}
					}
				}
				child = child.nextSibling;
			}
		}

	}catch(e){
		alert("* getNodeBySelAll *" + e.description);
	}
	return resultObjArr;
}
/*

 */
ZZBTreeItem.prototype.getNodeBySelAll = function (obj){
	var resultArr=new Array();
	if(obj==null || obj==undefined){
		resultArr=getNodeBySelAll(this.MainDiv,resultArr);
	}else{
		var checkboxObj=getInputNode(obj);
		if(obj.selAll=="0"){
			if(checkboxObj.checked==true){
				resultArr[resultArr.length]=obj;
			}
			resultArr=getNodeBySelAll(obj.nextSibling,resultArr);
		}else{
			resultArr[resultArr.length]=obj;
		}
	}
	return resultArr;
}
/*
 * 触发指定结点的文字点击事件
 */
ZZBTreeItem.prototype.clickNodeText = function (nodeObj){
	var aObj = nodeObj.firstChild; // 获取其组成的内容第一个对象
	while(aObj != null && aObj.nodeName != 'A'){
		aObj = aObj.nextSibling;
	}
	if (aObj != null){
		aObj.click();
	}
}

