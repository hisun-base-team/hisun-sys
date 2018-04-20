var Gzzzb_SelectOption_ids=new Array();//id的数组
var Gzzzb_SelectOption_objs=new Array();//对象的数组
var timeid;
var isfunction="0";
var isshowdiv="0";//用于控制复选点击确定不显示层

//单选隐藏层的方法 (特殊方法 只用于显示层得到焦点 层隐藏后文本框还需要得到焦点)
function _gzzzb_selectOption_hidden_Select_MainDiv(id){
	window.clearTimeout(timeid);
	timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV_MainDiv('"+id+"','1')",400);
}
function _gzzzb_selectOption_hidden_SelectDIV_MainDiv(id,num)
{
	var divmainobj;//得到最外面的div对象
	var hiddenvalueobj;//得到记录JS参数的隐藏文本对象
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	divmainobj.style.display="none";
	isvaluesava(id,"div");//调用失去焦点保存用户选择的内容（无多级树）
}
//单选隐藏层的方法
function _gzzzb_selectOption_hidden_Select(id){
	window.clearTimeout(timeid);
	timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV('"+id+"','1')",300);
}

//操作单选的层是显示还是隐藏 num为1时执行隐藏 2时执行显示
function _gzzzb_selectOption_hidden_Select_DIV(id,num)
{
	window.clearTimeout(timeid);
	timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV('"+id+"','"+num+"')",300);
}
//单选按回车时调用的方法
function _gzzzb_selectOption_hidden_Select_enter(id){
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	textobj.blur();
}
function _gzzzb_selectOption_hidden_Select_DIV_Input_Onblur(id){
	var divmainobj;//得到最外面的div对象
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	divmainobj.focus();
	window.clearTimeout(timeid);
	//timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV('"+id+"','2')",300);

	//obj.focus();
}
function _gzzzb_selectOption_hidden_Select_DIV_Input_Onblur_id(id){
	var divmainobj;//得到最外面的div对象
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	divmainobj.focus();
}
//隐藏或显示的实际方法 num主要用来判断鼠标从按钮离开后是否进去了div层  在100毫秒里进入了div则层显示  否则层消失 （无复选）
function _gzzzb_selectOption_hidden_SelectDIV(id,num)
{
	var divmainobj;//得到最外面的div对象
	var hiddenvalueobj;//得到记录JS参数的隐藏文本对象
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(num=="1"){
		divmainobj.style.display="none";
		if(hiddenvalueobj.isshowtree=="no"){//判断是否有多级树 为no时是没多级树
			isvaluesava(id,"text");//调用失去焦点保存用户选择的内容（无多级树）
		}else{
			treeisvaluesava(id);//调用失去焦点保存用户选择的内容（多级树）
		}
	}else{
		if(isshowdiv=="0"){
			countPosition(id);
			divmainobj.style.display="block"; //显示层
		}
	}
}

//点击图片的时候的事件
function _gzzzb_getChileTree(id){
	_gzzzb_selectOption_hidden_Select_DIV(id,'2');
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var hiddenallobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenallobj=Gzzzb_SelectOption_objs[idnum];
		}

	}

	/* var queryvalue=textobj.value;
	 var queryItem=queryvalue.split(",");
	 var queryinfo="";
	 //取得最后面一个逗号后的数据
	 if(queryvalue.length>0){
	 if(queryItem.length>1)
	 queryinfo=queryItem[queryItem.length-1];
	 else
	 queryinfo=queryvalue;
	 }

	 if(queryinfo=="")
	 window.setTimeout("divchangesize_all('"+id+"')",100);
	 else
	 window.setTimeout("divchangesize('"+id+"')",100);
	 */

	if(hiddenallobj.style.display=="block"){
		window.setTimeout("divchangesize_all('"+id+"')",100);
	}else{
		window.setTimeout("divchangesize('"+id+"')",100);
	}
}

//用于改变显示层的大小(改变的是记录查询信息的div的高度和宽度)
function divchangesize(id){
	var optiondivobj;
	var hiddenobj;
	var textobj;
	var imgobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optiondivobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_img"){
			imgobj=Gzzzb_SelectOption_objs[idnum];
		}
	}


	var maxwidth=hiddenobj.divmaxwidth;//定义的div的最大宽度
	var maxheight=hiddenobj.divmaxheight;//定义的div的最大高度

	var width=parseInt(textobj.clientWidth)+parseInt(imgobj.clientWidth)+5;//文本框的宽度
	optiondivobj.style.whiteSpace="nowrap";
	//判断显示数据的层的高度有没有超过定义的高度  -20是减去下面按钮的高度 如果超过定义的高度则在显示到最大的高度时显示滚动条 否则自动缩小

	if(optiondivobj.scrollHeight<=maxheight){
		optiondivobj.style.height="auto";
		optiondivobj.style.overflowY="hidden";
	}else{
		optiondivobj.style.height=maxheight;
		optiondivobj.style.overflowY="scroll";
	}
	//判断显示层的宽度 看是否超过了定义的宽度  如果小于文本框的宽度 则显示文本框的宽度  如果大于文本框的宽度  小于定于的最大的宽度 则在2个区域里面自动显示宽度 如果超过定义的最大宽度 则出现滚动条
	if(optiondivobj.scrollWidth<width){

		optiondivobj.style.overflowX="hidden";
		optiondivobj.style.width="100%";
	}else if(optiondivobj.scrollWidth<=maxwidth&&optiondivobj.scrollWidth>=width){
		optiondivobj.style.width="auto";
		optiondivobj.style.overflowX="hidden";
	}else{
		optiondivobj.style.width=maxwidth;
		optiondivobj.style.overflowX="scroll";
	}
}
//用于改变显示层的大小(改变的是记录全部信息的div的高度和宽度 只有多级树的静态查询时有用到)
function divchangesize_all(id){
	var hiddenoptiondivobj;
	var hiddenobj;
	var textobj;
	var imgobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optiondivobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptiondivobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_img"){
			imgobj=Gzzzb_SelectOption_objs[idnum];
		}
	}


	var maxwidth=hiddenobj.divmaxwidth;//定义的div的最大宽度
	var maxheight=hiddenobj.divmaxheight;//定义的div的最大高度
	var width=parseInt(textobj.clientWidth)+parseInt(imgobj.clientWidth)+5;//文本框的宽度

	//判断显示层的宽度 看是否超过了定义的宽度  如果小于文本框的宽度 则显示文本框的宽度  如果大于文本框的宽度  小于定于的最大的宽度 则在2个区域里面自动显示宽度 如果超过定义的最大宽度 则出现滚动条

	if(parseInt(hiddenoptiondivobj.scrollWidth)<width){

		hiddenoptiondivobj.style.width="100%";
		hiddenoptiondivobj.style.overflowX="hidden";
	}else if(parseInt(hiddenoptiondivobj.scrollWidth)<=parseInt(maxwidth)&&parseInt(hiddenoptiondivobj.scrollWidth)>=width){
		hiddenoptiondivobj.style.width="auto";
		hiddenoptiondivobj.style.overflowX="hidden";
	}else{
		hiddenoptiondivobj.style.width=maxwidth;
		hiddenoptiondivobj.style.overflowX="scroll";
	}

	//判断显示数据的层的高度有没有超过定义的高度  -20是减去下面按钮的高度 如果超过定义的高度则在显示到最大的高度时显示滚动条 否则自动缩小
	if(hiddenoptiondivobj.scrollHeight<=maxheight){
		hiddenoptiondivobj.style.height="auto";
		hiddenoptiondivobj.style.overflowY="hidden";
	}else{
		hiddenoptiondivobj.style.overflowY="scroll";
		hiddenoptiondivobj.style.height=maxheight;
	}
}
//如果设置为往上加载 则在此定位往上显示坐标
function countPosition(id){
	var hiddenobj;
	var textobj;
	var divmainobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var showWay=hiddenobj.showWay;
	if(showWay=="top"){
		var iDocHeight = document.body.offsetHeight;
		var obj=textobj.parentNode.parentNode.parentNode;
		var t=obj.offsetTop;
		while(obj=obj.offsetParent)
			t += obj.offsetTop;
		var bottom=  iDocHeight-t-parseInt(textobj.clientTop)*4;
		divmainobj.style.bottom=bottom;
	}
}
//点击内容的时候的事件
function _gzzzb_selected_values(obj,id){
	isshowdiv++;
	//用于点击内容的时候取消隐藏层的操作 不加则把该点击当成失去文本框焦点
	_gzzzb_selectOption_hidden_Select_DIV(id,'2');

	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var fullurlobj;//现在在做选择的记录全路径的对象
	var divmainobj;
	var optionobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			fullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	var tree1=null;

	if(optionobj.style.display=="none"){
		tree1=getObjByMainDivId(id+"_hidden_option_all");
	}
	if(tree1==null)
		tree1=getObjByMainDivId(id+"_option");
	isfunction="0";
	//得到点击的节点的展开状态
	var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj());
	//判断节点对象是否展开 true为展开  则选中该点击的内容
	if(nodestatue==true){
		_gzzzb_selectOption_hidden_Select_DIV(id,'1');
		//取得点击节点的所以信息
		var arr=tree1.getNodeAttribute(tree1.getCurrentNodeObj());

		var fullurls=fullurlobj.value.split(",");
		//如果selectvaluetype为1 则读取全称  为0时则读取显示字段
		if(hiddenobj.getAttribute("selectvaluetype")=="1"){
			textobj.value=arr[7];
		}else	{
			textobj.value=arr[10];
		}
		if(textobj.value=="")
			textobj.value=arr[4];
		if(textobj.value=="")
			textobj.value=arr[8];
		var isbeing="false";
		for(var i=0;i<fullurls.length;i++){
			if(arr[3]==fullurls[i])
				isbeing="true";
		}
		if(isbeing=="false")
			fullurlobj.value=arr[3]+","+fullurlobj.value;
		keyobj.value=arr[1];
		divmainobj.style.display="none";

	}else{
		window.setTimeout("divchangesize('"+id+"')",50);
	}

}
//用于单选改变文本框的值和其对应的key值
function _gzzzb_changevalues(id,value,key,dynamicAttri){
	isshowdiv++;
	var textobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var divmainobj;
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	textobj.value=value;
	keyobj.value=key;
	hiddenobj.setAttribute("dynamicAttri", dynamicAttri);
	isvaluesava(id,"text");//调用保存选中数据的方法
	divmainobj.style.display="none"; //隐藏显示层
	//_gzzzb_selectOption_hidden_SelectDIV(id,"2");
}
//只有一级菜单的时候的移到列上改变颜色
function _gzzzb_changecolor(obj,num,id)
{
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	if(num=="1"){
		if(hiddenobj.onmousemovecolor!=undefined&&hiddenobj.onmousemovecolor!="")
			obj.bgColor=hiddenobj.onmousemovecolor;
		else
			obj.bgColor="#7B6DFC";
	}
	else
		obj.bgColor="";
}
//输入查询时的延迟查询操作
function queryinfo_last(id,choosetype,num){
	isshowdiv="0";
	var hiddenobj;
	var divmainobj;
	var oldoptionobj;
	var textobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var optionobj;
	var queryvalueobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
			oldoptionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_queryValue"){
			queryvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	/*if(textobj.value==queryvalueobj.value){
	 return;
	 }else{
	 queryvalueobj.value=textobj.value;
	 }*/

	oldoptionobj.innerHTML="";
	//oldoptionobj.style.display="none";
	//看是否需要静态加载
	if(hiddenobj.isshowtree=="yes"&&hiddenobj.getAttribute("staticdata")=="no"){
		optionobj.style.display="none";
		window.clearTimeout(timeid);
		timeid=window.setTimeout("loadQueryNode('"+id+"','"+id+"_option','"+id+"_hidden_option_all','"+num+"')",100);
		countPosition(id);
		divmainobj.style.display="block";
		keyobj.value="";
	}else{
		window.clearTimeout(timeid);
		timeid=window.setTimeout("queryinfo('"+id+"','"+choosetype+"','"+num+"')",100);
	}

	if(event.keyCode == 13){//13为回车键值
		//如果是回车，转换为Tab键（可触发该对象的onChange事件)
		event.keyCode=9;//9为Tab键值
	}

}
//单选的构造树的方法
function loadQueryNode(id,MainDivId,QueryDivId,num){
	try{
		//var hiddenobj=document.all("gzzzb_"+id+"_hiddenvalue");
		// var keyobj=document.all(id);//现在在做选择的记录key保存的对象

		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;

		var optionobj;
		var hiddenallobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenallobj=Gzzzb_SelectOption_objs[idnum];
			}
		}

		var queryvalue="";
		if(num=="1")
			queryvalue=textobj.value;

		if(queryvalue==""){
			hiddenallobj.style.display="block";
			_gzzzb_getChileTree(id);

		}else{
			optionobj.style.display="block";
			hiddenallobj.style.display="none";
			var QueryDiv=document.getElementById(QueryDivId);//查询对象
			var MainDiv=document.getElementById(MainDivId);//存存储对象

			var tree1=getObjByMainDivId(QueryDivId);
			if(tree1!=undefined){
				var matching=hiddenobj.getAttribute("matchingsetup").split(",");
				tree1.queryLoadNode(queryvalue,MainDiv,"/GZZZB/","_gzzzb_getChileTree('"+id+"')","no","_gzzzb_selected_values(this,'"+id+"')",matching,keyobj.value);
			}
			window.setTimeout("divchangesize('"+id+"')",50);

			var tree2=getObjByMainDivId(QueryDivId);
			//判断得到的节点树是否为空  如果为空则显示 未找到相关信息

			if(tree2!=undefined){
				if(tree2.getAllNodes()==""){
					optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";
				}
			}else{
				optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";

			}
		}
		addoldselectedvalues(id);
	}catch(e){
		alert(" loadQueryNode : " + e.description);
	}
}

// choosetype 单选还是复选类型  1为单选 2为复选   num为触发事件的位置 1为文本框触发 2为图片触发
function queryinfo_onclick(id,choosetype,num)
{
	isshowdiv="0";
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var fullurlobj;//现在在做选择的记录全路径的对象
	var divmainobj;
	var oldoptionobj;
	var optionobj;
	var optionallobj;
	var hiddenoptionallobj;
	var queryvalueobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			fullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
			oldoptionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			optionallobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_queryValue"){
			queryvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	/*if(textobj.value==queryvalueobj.value){
	 //optionobj.style.display="block";
	 return;
	 }*/
	if(textobj.value!=""){
		//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
		with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
		//================================
	}
	if(textobj.value==""){
		keyobj.value="";
	}
	//得到树需要的相关设置
	var dataSource=hiddenobj.getAttribute("dataSource");
	var contextPath=hiddenobj.getAttribute("contextPath");
	var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
	var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
	var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
	var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
	var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
	var userParameter=hiddenobj.getAttribute("userParameter");
	oldoptionobj.innerHTML="";
	//oldoptionobj.style.display="none";
	var matchingsetup=hiddenobj.getAttribute("matchingsetup");
	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");
	var equalcolor=hiddenobj.getAttribute("equalcolor");
	var dictionaryType=hiddenobj.getAttribute("dictionaryType");
	var selectvaluetype=hiddenobj.getAttribute("selectvaluetype");
	var allowClear=hiddenobj.allowClear;
	var isDynamicLoadDataSource=hiddenobj.isDynamicLoadDataSource;
	var key=keyobj.value;

	// 使用AJAX请求获取查询信息
	if(hiddenobj.isshowtree=="no"){
		var textvalue="";
		if(num=="1")//num为1是文本框查询  如果不是则是点击图片查询 查询内容为空
			textvalue=textobj.value;
		if(isDynamicLoadDataSource=="true"){
			var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?allowClear="+allowClear+"&code="+key+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
				if(reqText!=null && reqText!=""){
					hiddenoptionallobj.innerHTML=reqText;
					divmainobj.style.display="block";
					hiddenoptionallobj.style.display="block";
					optionobj.style.display="none";

					divchangesize_all(id);
				}
				//showquertdata(id,reqText,num);
			});
			b.doGet();
		}else{

			if(hiddenoptionallobj.innerHTML==""){
				var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?allowClear="+allowClear+"&code="+key+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
					if(reqText!=null && reqText!=""){
						hiddenoptionallobj.innerHTML=reqText;
						divmainobj.style.display="block";
						hiddenoptionallobj.style.display="block";
						optionobj.style.display="none";

						divchangesize_all(id);
					}
					//showquertdata(id,reqText,num);
				});
				b.doGet();
			}else{
				//divmainobj.style.display="block";
				//alert(optionallobj.style.display);
				//alert(optionallobj.innerHTML);

				optionobj.style.display="none";
				optionallobj.style.display="none";
				window.setTimeout("divchangesize_all('"+id+"')",100);
				hiddenoptionallobj.style.display="block";

			}
		}

	}else{
		if(hiddenobj.getAttribute("staticdata")=="yes"){
			var textvalue="";
			if(num=="1")
				textvalue=textobj.value;
			setObjByMainDivId(document.getElementById(id+"_option"));
			var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&unitCode="+textvalue+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&fontColor="+fontColor+"&fontSize="+fontSize+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_option&dataSource="+dataSource, function(reqText) {
				if(reqText!=null && reqText!=""){

					var jsStr=reqText.substr(reqText.indexOf("#####")+5);
					optionobj.innerHTML=reqText.replace("#####"+jsStr,"");
					eval(reqText.substr(reqText.indexOf("#####")+5));
					addoldselectedvalues(id);
					window.setTimeout("divchangesize('"+id+"')",50);
				}else{
					optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";
					addoldselectedvalues(id);
				}
			});
			a.doGet();
		}else{
			var textvalue="";
			if(num=="1")
				textvalue=textobj.value;

			if(hiddenoptionallobj.innerHTML==""&&textvalue!=""){
				var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&fontColor="+fontColor+"&fontSize="+fontSize+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_hidden_option_all&dataSource="+dataSource, function(reqText) {
					if(reqText!=null && reqText!=""){
						var jsStr=reqText.substr(reqText.indexOf("#####")+5);
						hiddenoptionallobj.innerHTML=reqText.replace("#####"+jsStr,"");
					}
				});
				b.doGet();
			}
			if(hiddenoptionallobj.innerHTML==""){
				setObjByMainDivId(document.getElementById(id+"_hidden_option_all"));
				var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&fontColor="+fontColor+"&fontSize="+fontSize+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_hidden_option_all&dataSource="+dataSource, function(reqText) {
					if(reqText!=null && reqText!=""){

						var jsStr=reqText.substr(reqText.indexOf("#####")+5);
						hiddenoptionallobj.innerHTML=reqText.replace("#####"+jsStr,"");
						eval(reqText.substr(reqText.indexOf("#####")+5));

					}

					window.setTimeout("divchangesize('"+id+"')",50);
					optionobj.style.display="none";
					_gzzzb_getChileTree(id);
				});
				a.doGet();
			}else{
				optionobj.style.display="none";
			}
			loadQueryNode(id,id+"_option",id+"_hidden_option_all",num);
		}
	}
	countPosition(id);
	divmainobj.style.display="block";
}


//单选树的改变内容的方法
function changevalues_tree(id,value,key){
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var divmainobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	divmainobj.style.display="none";
	textobj.value=value;
	keyobj.value=key;

}
//AJAX取得数据  choosetypetype为类型 1为单选 2为复选 是否有树形结构 文本框调用   num为1时是文本框调用  num为2时是图片调用  显示全部
function queryinfo(id,choosetype,num)
{
	try{
		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var divmainobj;
		var oldoptionobj;
		var optionobj;
		var optionallobj;
		var hiddenoptionallobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
				divmainobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
				oldoptionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				optionallobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
			}
		}

		//取得用户设置的参数
		var dataSource=hiddenobj.getAttribute("dataSource");
		var contextPath=hiddenobj.getAttribute("contextPath");
		var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
		var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		isfunction="0";

		var matchingsetup=hiddenobj.getAttribute("matchingsetup");
		var fontColor=hiddenobj.getAttribute("fontColor");
		var fontSize=hiddenobj.getAttribute("fontSize");
		var equalcolor=hiddenobj.getAttribute("equalcolor");
		var key=keyobj.value;
		// 使用AJAX请求获取查询信息。
		if(hiddenobj.isshowtree=="no"){
			var textvalue="";
			if(num=="1")
				textvalue=textobj.value;
			var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&code="+key+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
				if(reqText!=null && reqText!=""){
					if(reqText.indexOf("gzzzb_returnCodes###")!=-1){
						var keys=reqText.replace("gzzzb_returnCodes###","");
						keyobj.value=keys;
						textobj.blur();
						/*if(event.keyCode == 13){//13为回车键值
						 //如果是回车，转换为Tab键（可触发该对象的onChange事件)
						 event.keyCode=9;//9为Tab键值
						 }*/
						//divmainobj.style.display="none";
					}else{
						//alert(reqText);
						//加载要显示的数据及以前记录过的数据
						showquertdata(id,reqText,num);
						hiddenoptionallobj.style.display="none";
						divmainobj.style.display="block";
						//alert(hiddenoptionallobj.innerHTML);
					}
				}
			});

			a.doGet();
			countPosition(id);

		}else{
			var textvalue="";

			if(num=="1"){
				textvalue=textobj.value;
				keyobj.value="";
			}
			var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&fontColor="+fontColor+"&fontSize="+fontSize+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_hidden_option_all&dataSource="+dataSource, function(reqText) {
				if(reqText!=null && reqText!=""){

					var jsStr=reqText.substr(reqText.indexOf("#####")+5);
					// if(optionallobj.innerHTML==""){
					optionobj.innerHTML=reqText.replace("#####"+jsStr,"");
					// 	}
					eval(reqText.substr(reqText.indexOf("#####")+5));
					addoldselectedvalues(id);

				}else{
					optionobj.innerHTML="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";
					addoldselectedvalues(id);
				}
			});
			a.doGet();
			countPosition(id);
			divmainobj.style.display="block";
		}
	}catch(e){
		alert("queryinfo::" + e.description);
	}
}
//解决是按TAB让输入框得到焦点的问题 单选
function _gzzzb_loadData_onfocus(id){
	try{
		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var divmainobj;
		var oldoptionobj;
		var optionobj;
		var optionallobj;
		var queryvalueobj;
		var hiddenoptionallobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
				divmainobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
				oldoptionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				optionallobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_queryValue"){
				queryvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
			}
		}
		//取得用户设置的参数
		var dataSource=hiddenobj.getAttribute("dataSource");
		var contextPath=hiddenobj.getAttribute("contextPath");
		var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
		var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		var isDynamicLoadDataSource=hiddenobj.isDynamicLoadDataSource;
		var allowClear=hiddenobj.allowClear;
		isfunction="0";

		/*if(textobj.value==queryvalueobj.value){
		 return;
		 }*/
		var matchingsetup=hiddenobj.getAttribute("matchingsetup");
		var fontColor=hiddenobj.getAttribute("fontColor");
		var fontSize=hiddenobj.getAttribute("fontSize");
		var equalcolor=hiddenobj.getAttribute("equalcolor");
		var key=keyobj.value;
		if(isDynamicLoadDataSource=="true"){
			var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?allowClear="+allowClear+"&code="+key+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&type=1&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
				if(reqText!=null && reqText!=""){
					hiddenoptionallobj.innerHTML=reqText;
					//hiddenoptionallobj.style.display="block";
					//optionallobj.style.display="none";
				}
				//showquertdata(id,reqText,1);
			});
			b.doGet();
		}else{
			if(hiddenoptionallobj.innerHTML==""){
				var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?allowClear="+allowClear+"&code="+key+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&type=1&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
					if(reqText!=null && reqText!=""){
						hiddenoptionallobj.innerHTML=reqText;
					}
				});
				b.doGet();
			}
		}
	}catch(e){
		alert("_gzzzb_loadData_onfocus::" + e.description);
	}
}
//解决是按TAB让输入框得到焦点的问题 复选
function _gzzzb_loadDataCheckbox_onfocus(id){
	try{
		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var fullurlobj;//现在在做选择的记录全路径的对象
		var divmainobj;
		var oldoptionobj;
		var optionobj;
		var alloldkeyobj;
		var alloldvalueobj;
		var oldqueryvalue;
		var hiddenoptionallobj;
		var oldkeyobj;
		for(var idnum=0; idnum<Gzzzb_SelectOption_ids.length; idnum++){
			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_hiddenvalue"){
				hiddenobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == id){
				keyobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == id + "_text"){
				textobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_checked_fullurl"){
				fullurlobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_main"){
				divmainobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == id + "_option_old"){
				oldoptionobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == id + "_option"){
				optionobj = Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_checked_allkey"){
				alloldkeyobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_checked_allvalue"){
				alloldvalueobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_oldqueryvalue"){
				oldqueryvalue = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == id + "_hidden_option_all"){
				hiddenoptionallobj = Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_oldquerykey"){
				oldkeyobj = Gzzzb_SelectOption_objs[idnum];
			}
		}

		var dataSource = hiddenobj.getAttribute("dataSource");//得到传数据的数据接口的数据源
		var contextPath = hiddenobj.getAttribute("contextPath");//系统根目录路径
		var checkboxDisplay = hiddenobj.getAttribute("checkboxDisplay");//复选框实现显示
		var radioOrCheckbox = hiddenobj.getAttribute("radioOrCheckbox");//树是单选还是复选
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var matchingsetup=hiddenobj.getAttribute("matchingsetup");
		var fontColor=hiddenobj.getAttribute("fontColor");
		var fontSize=hiddenobj.getAttribute("fontSize");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		var key=keyobj.value;
		if(hiddenoptionallobj.innerHTML==""){//一级选择项的Ajax方法调用
			var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&code="+key+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&type=2&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize, function(reqText) {
				if(reqText!=null && reqText!=""){
					hiddenoptionallobj.innerHTML=reqText;
				}
			});
			b.doGet();
		}
	}catch(e){
		alert("_gzzzb_loadDataCheckbox_onfocus::" + e.description);
	}
}
//动态查询的单选 把记录了的数据查询出来
function addoldselectedvalues(id){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var fullurlobj;//现在在做选择的记录全路径的对象

	var oldoptionobj;
	var alloldkeyobj;
	var alloldvalueobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			fullurlobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
			oldoptionobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			alloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			alloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}

	}
	//取得用户设置的属性
	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");

	var oldinnerhtml="";
	var style="";
	if(fontColor!=""){
		style="color:"+fontColor+";";
	}
	if(fontSize!=""){
		style=style+"font-size:"+fontSize+";";
	}
	var alloldkeys=alloldkeyobj.value.split(",");
	var alloldvalues=alloldvalueobj.value.split(",");
	var alloldfullurl=fullurlobj.value.split(",");
	if(alloldkeyobj.value!=""){
		for(var i=0;i<alloldkeys.length;i++){
			if(alloldkeys[i]!=""){
				if(alloldkeys[i]==" "&&textobj.value==""){
					oldinnerhtml=oldinnerhtml+"<tr height=20><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left onclick=\"changevalues_tree('"+id+"','"+alloldvalues[i]+"','"+alloldkeys[i]+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<font style=\"cursor:hand;\">"+alloldfullurl[i]+"</font></td></tr>";
				}else{
					if(alloldvalues[i].indexOf(textobj.value)==-1&&alloldkeys[i]!=""){
						oldinnerhtml=oldinnerhtml+"<tr height=20><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left onclick=\"changevalues_tree('"+id+"','"+alloldvalues[i]+"','"+alloldkeys[i]+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<font style=\"cursor:hand;\">"+alloldfullurl[i]+"</font></td></tr>";
					}
				}
			}
		}
	}
	if(oldinnerhtml!=""){
		oldinnerhtml="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY><hr>"+oldinnerhtml+"</TBODY></table>";
	}
	if(oldinnerhtml!=""){
		oldoptionobj.innerHTML=oldinnerhtml;
		//oldoptionobj.style.display="block";
	}
	//alert();

}
//单选的只有一级菜单的加载全部查询过的记录
function showquertdata(id,reqText,num){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var fullurlobj;//现在在做选择的记录全路径的对象
	var divmainobj;
	var oldoptionobj;
	var optionobj;
	var alloldkeyobj;
	var selectalloldvalueobj;
	var oldqueryvalue;
	var hiddenoptionallobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			fullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
			oldoptionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			alloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			selectalloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldqueryvalue=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");

	var style="";
	if(fontColor!=""){
		style="color:"+fontColor+";";
	}
	if(fontSize!=""){
		style=style+"font-size:"+fontSize+";";
	}
	isfunction="0";
	var alloldvalues=selectalloldvalueobj.value.split(",");
	var alloldkeys=alloldkeyobj.value.split(",");
	var olddivinnerhtml="";
	var invalue=textobj.value;
	//构造一个显示以前选中过的数据的table
	if(oldqueryvalue!=""){
		olddivinnerhtml="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY>";
		for(var i=0;i<alloldvalues.length;i++){
			if(alloldkeys[i]==" "&&textobj.value==""){
				if(alloldvalues[i]!=invalue&&alloldvalues[i]!=""){
					olddivinnerhtml=olddivinnerhtml+"<tr key="+alloldkeys[i]+" height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+alloldvalues[i]+"','"+alloldkeys[i]+"','');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><font style=\"cursor:hand;\">"+alloldvalues[i]+"</font></td></tr>";
				}
			}else{
				if((alloldvalues[i].indexOf(invalue)==-1&&alloldvalues[i]!="")&&alloldkeys[i]!=invalue){
					olddivinnerhtml=olddivinnerhtml+"<tr key="+alloldkeys[i]+" height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"_gzzzb_changevalues('"+id+"','"+alloldvalues[i]+"','"+alloldkeys[i]+"','');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><font style=\"cursor:hand;\">"+alloldvalues[i]+"</font></td></tr>";
				}
			}
		}
		olddivinnerhtml=olddivinnerhtml+"</TBODY></table>";
	}
	optionobj.innerHTML=reqText;
	/*if(invalue!=""){

	 if(olddivinnerhtml=="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY></TBODY></table>"||oldqueryvalue==""){}
	 else
	 oldoptionobj.innerHTML="<hr>"+olddivinnerhtml;
	 }else{
	 if(olddivinnerhtml=="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY></TBODY></table>"||oldqueryvalue==""){
	 //optionobj.innerHTML	=reqText;
	 }
	 else
	 oldoptionobj.innerHTML	="<hr>"+olddivinnerhtml;
	 }*/
	var maxheight=hiddenobj.divmaxheight;//定义的div的最大高度

	//用于判断是否需要出现滚动条
	if(divmainobj.clientHeight>maxheight+20)
		optionobj.style.height=maxheight;
	if(num==1){
		countPosition(id);
		divmainobj.style.display="block";
	}
	optionobj.style.display="block";
	keyobj.value="";
	divchangesize(id);
	//if(oldoptionobj.innerHTML!="")
	//oldoptionobj.style.display="block";
}
//单选多级树的最好做检查录入的数据是否有完全匹配的内容及保存查询到额内容
function treeisvaluesava(id){
	try{
		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var fullurlobj;//现在在做选择的记录全路径的对象
		var divmainobj;
		var oldoptionobj;
		var optionobj;
		var alloldkeyobj;
		var alloldvalueobj;
		var oldvalueobj;
		var oldkeyobj;
		var hiddenoptionallobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
				fullurlobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
				divmainobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
				oldoptionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
				alloldkeyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
				alloldvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
				oldvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldquerykey"){
				oldkeyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
			}
		}

		//var alloldkeyobj=document.all("gzzzb_"+id+"_checked_allkey");//全部的记录key保存的对象
		//var alloldvalueobj=document.all("gzzzb_"+id+"_checked_allvalue");//全部的记录保存的对象
		//var keyobj=document.all(id);//现在在做选择的记录key保存的对象
		//var oldvalueobj=document.all("gzzzb_"+id+"_oldqueryvalue");//上次的记录保存的对象
		//var oldkeyobj=document.all("gzzzb_"+id+"_oldquerykey");//上次的记录key保存的对象
		//var textobj=document.all(id+"_text");
		//var fullurlobj=document.all("gzzzb_"+id+"_checked_fullurl");//现在在做选择的记录全路径的对象


		var newfullurl=" ";
		//取得树的第一个节点 看其内容是否和输入的值完全匹配 如果完全匹配则在离开的时候增加到key里面
		var tree1=getObjByMainDivId(id+"_option");
		if(tree1!=undefined&&tree1!=null){
			if(optionobj.innerText!="未找到相关信息"){
				var arrall=tree1.getAllNodes();
				if(arrall!=""&&arrall!=null){
					for(var i=0;i<arrall.length;i++){
						var onearr=tree1.getNodeAllInfos(arrall[i]);
						if(textobj.value==onearr[4]){
							if(keyobj.value==""){
								keyobj.value=onearr[1];
								newfullurl=onearr[3];
							}
							break;
						}
					}
				}
			}
		}
		var isAddNewData=hiddenobj.isAddNewData;
		//判断是否有完全匹配的内容
		if((keyobj.value==""||keyobj.value==" ")&&textobj.value!=""){

			//看是否需要录入
			if(isAddNewData=="true"){
				if(confirm("该选择项中没有  "+textobj.value+"  ，如需录入按[确定]，否则按[取消]")){
					if(textobj.value!=""){
						keyobj.value=" ";
					}
					var isbeing="false";//用户记录该数据是否已经选择过 没选择过则需要记录到选择的历史信息里
					var alloldkeys=alloldkeyobj.value.split(",");
					var alloldvalues=alloldvalueobj.value.split(",");

					if(keyobj.value==" "){
						for(var i=0;i<alloldvalues.length;i++){
							if(alloldvalues[i]==textobj.value)
								isbeing="true";
						}
					}else{
						for(var i=0;i<alloldkeys.length;i++){
							if(alloldkeys[i]==keyobj.value)
								isbeing="true";
						}
					}

					if(isbeing=="false"){
						alloldkeyobj.value=keyobj.value+","+alloldkeyobj.value;
						alloldvalueobj.value=textobj.value+","+alloldvalueobj.value;

						fullurlobj.value=textobj.value+","+fullurlobj.value;
					}
				}else{
					textobj.value="";
					textobj.focus();

				}
				_gzzzb_Onchange(id);
			}else{
				if(confirm("该选择项中没有  “"+textobj.value+"”， 如需删除按[确定]，修改按[取消]")){
					textobj.focus();
					textobj.value="";
					_gzzzb_Onchange(id);
				}else{
					textobj.focus();
					//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
					with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
					//================================
					_gzzzb_Onchange(id);
					textobj.click();
				}
			}
		}else{
			_gzzzb_Onchange(id);
			var isbeing="false";

			var alloldkeys=alloldkeyobj.value.split(",");
			var alloldvalues=alloldvalueobj.value.split(",");
			if(keyobj.value!=""){

				if(keyobj.value==""){
					for(var i=0;i<alloldvalues.length;i++){
						if(alloldvalues[i]==textobj.value)
							isbeing="true";
					}
				}else{
					for(var i=0;i<alloldkeys.length;i++){
						if(alloldkeys[i]==keyobj.value)
							isbeing="true";
					}
				}

				if(isbeing=="false"){
					alloldkeyobj.value=keyobj.value+","+alloldkeyobj.value;
					alloldvalueobj.value=textobj.value+","+alloldvalueobj.value;

					if(fullurlobj.value=="")
						fullurlobj.value=newfullurl+",";
					else if(keyobj.value=="")
						fullurlobj.value=newfullurl+","+fullurlobj.value;
				}

			}
		}

		oldvalueobj.value=textobj.value;
		oldkeyobj.value=keyobj.value;

		transferUserFunc(id);
	}catch(e){
		alert("treeisvaluesava::" + e.description);
	}
}
//单选只有一级菜单时保存用户查询数据 如果是显示层失去焦点调用的类型为 div 否则为文本框 text 如果是div类型的则在离开后稳步卡得到焦点
function isvaluesava(id,type)
{
	try{
		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var fullurlobj;//现在在做选择的记录全路径的对象
		var divmainobj;
		var oldoptionobj;
		var optionobj;
		var alloldkeyobj;
		var alloldvalueobj;
		var oldvalueobj;
		var oldkeyobj;
		var hiddenoptionallobj;
		var queryvalueobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
				fullurlobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
				divmainobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
				oldoptionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
				alloldkeyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
				alloldvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
				oldvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldquerykey"){
				oldkeyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_queryValue"){
				queryvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
		}

		//var alloldkeyobj=document.all("gzzzb_"+id+"_checked_allkey");//全部的记录key保存的对象
		//var alloldvalueobj=document.all("gzzzb_"+id+"_checked_allvalue");//全部的记录保存的对象
		//var oldvalueobj=document.all("gzzzb_"+id+"_oldqueryvalue");//上次查询条件的value保存的对象

		//var keyobj=document.all(id);//现在在做选择的记录key保存的对象
		//var oldkeyobj=document.all("gzzzb_"+id+"_oldquerykey");//上次的记录key保存的对象

		//var textobj=document.all(id+"_text");
		//用于验证是否有改变过值
		/*if(_gzzzb_checkIsChange(id)){
		 return false;
		 }*/

		var invalue=textobj.value;

		var allowClear=hiddenobj.allowClear;
		var isall="false";//判断是否有完全匹配的记录
		var alloldvalues=alloldvalueobj.value.split(",");
		var isokallvalue="";
		var isbeing="false";
		//判断输入的内容是否有完全的匹配的内容或key
		if(optionobj.firstChild!=null){
			if(optionobj.firstChild.firstChild.childNodes[0]!=null){
				if(optionobj.firstChild.firstChild.childNodes[0].innerText!="未找到相关信息"){
					if(invalue==optionobj.firstChild.firstChild.childNodes[0].innerText)
						keyobj.value=optionobj.firstChild.firstChild.firstChild.key;
					if(invalue==optionobj.firstChild.firstChild.firstChild.key){
						textobj.value=optionobj.firstChild.firstChild.childNodes[0].innerText;
						keyobj.value=optionobj.firstChild.firstChild.firstChild.key;
					}
				}
			}
		}

		if(invalue!=""){
			if(hiddenoptionallobj.firstChild!=null){
				for(var i=0;i<hiddenoptionallobj.firstChild.firstChild.childNodes.length;i++){
					if(hiddenoptionallobj.firstChild.firstChild.childNodes[i].innerText==textobj.value)
						isall="true";//有完全匹配记录
				}
			}
		}
		var isAddNewData=hiddenobj.isAddNewData;
		//判断是否有不存在选择项中的值

		if(isall=="false"&&textobj.value!=""){
			if(isAddNewData=="true"){
				if(confirm("该选择项中没有  "+textobj.value+"  ，如需录入按[确定]，否则按[取消]")){

					if(textobj.value!=""){
						keyobj.value=" ";
					}
					//textobj.blur();
					var isbeing="false";
					//var alloldkeys=alloldkeyobj.value.split(",");
					//var alloldvalues=alloldvalueobj.value.split(",");

					oldvalueobj.value=textobj.value;
					oldkeyobj.value=keyobj.value;
					if(keyobj.value==" "){
						for(var i=0;i<alloldvalues.length;i++){
							if(alloldvalues[i]==textobj.value)
								isbeing="true";
						}
					}else{
						for(var i=0;i<alloldkeys.length;i++){
							if(alloldkeys[i]==keyobj.value)
								isbeing="true";
						}
					}

					//if(isbeing=="false"){
					//	alloldkeyobj.value=keyobj.value+","+alloldkeyobj.value;
					//	alloldvalueobj.value=textobj.value+","+alloldvalueobj.value;
					//}
					_gzzzb_Onchange(id);
				}else{
					textobj.value="";
					textobj.focus();
					_gzzzb_Onchange(id);
				}
			}else{
				if(confirm("该选择项中没有  “"+textobj.value+"”， 如需删除按[确定]，修改按[取消]")){
					textobj.focus();
					textobj.value="";
					_gzzzb_Onchange(id);
				}else{
					textobj.focus();
					//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
					with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
					//================================
					_gzzzb_Onchange(id);
					textobj.click();
				}
			}
		}else{
			var isbeing="false";
			if(textobj.value!=queryvalueobj.value){
				var dataSource = hiddenobj.getAttribute("dataSource");//得到传数据的数据接口的数据源
				var contextPath = hiddenobj.getAttribute("contextPath");//系统根目录路径
				var checkboxDisplay = hiddenobj.getAttribute("checkboxDisplay");//复选框实现显示
				var radioOrCheckbox = hiddenobj.getAttribute("radioOrCheckbox");//树是单选还是复选
				var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
				var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
				var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
				var userParameter=hiddenobj.getAttribute("userParameter");
				var matchingsetup=hiddenobj.getAttribute("matchingsetup");
				var dictionaryType=hiddenobj.getAttribute("dictionaryType");
				textvalue=textobj.value;
				var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type=1&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource, function(reqText) {
					if(reqText!=null && reqText!=""){
						if(reqText.indexOf("gzzzb_returnCodes###")!=-1){
							var keys=reqText.replace("gzzzb_returnCodes###","");
							keyobj.value=keys;
							if(hiddenoptionallobj.firstChild!=null){
								if(hiddenoptionallobj.firstChild.firstChild.childNodes[0]!=null){
									if(hiddenoptionallobj.firstChild.firstChild.childNodes[0].innerText!="未找到相关信息"){
										for(var i=0;i<hiddenoptionallobj.firstChild.firstChild.childNodes.length;i++){
											if(hiddenoptionallobj.firstChild.firstChild.childNodes[i].key==keyobj.value){
												hiddenoptionallobj.firstChild.firstChild.childNodes[i].firstChild.firstChild.color="red";
											}else{
												if(hiddenoptionallobj.firstChild.firstChild.childNodes[i].firstChild.firstChild.color!=""){
													hiddenoptionallobj.firstChild.firstChild.childNodes[i].firstChild.firstChild.color="";
												}
											}
										}
									}
								}
							}
						}
					}
				});
				a.doGet();
				queryvalueobj.value=textobj.value;
			}

			//if(textobj.value!=""&&keyobj.value!=""){

			if(hiddenoptionallobj.firstChild!=null){
				if(hiddenoptionallobj.firstChild.firstChild.childNodes[0]!=null){
					if(hiddenoptionallobj.firstChild.firstChild.childNodes[0].innerText!="未找到相关信息"){
						for(var i=0;i<hiddenoptionallobj.firstChild.firstChild.childNodes.length;i++){
							if(hiddenoptionallobj.firstChild.firstChild.childNodes[i].key==keyobj.value){
								hiddenoptionallobj.firstChild.firstChild.childNodes[i].firstChild.firstChild.color="red";
							}else{
								if(hiddenoptionallobj.firstChild.firstChild.childNodes[i].firstChild.firstChild.color!=""){
									hiddenoptionallobj.firstChild.firstChild.childNodes[i].firstChild.firstChild.color="";
								}
							}
						}
					}
				}
			}
			//}
			if(textobj.value==""){
				keyobj.value="";
			}
			_gzzzb_Onchange(id);
			oldvalueobj.value=textobj.value;
			oldkeyobj.value=keyobj.value;
			if(type=="div"){
				//textobj.focus();
				//with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
			}
			//if(isbeing=="false"){
			//	alloldkeyobj.value=keyobj.value+","+alloldkeyobj.value;
			//	alloldvalueobj.value=textobj.value+","+alloldvalueobj.value;
			//	}
		}


		//用于判断是否有清除数据的功能
		if(allowClear=="true"){
			//用于判断是否是清楚数据的key
			if(keyobj.value=="gzzzb_selectOption_clear"){
				textobj.value="";
				keyobj.value="";
				oldvalueobj.value="";
				oldkeyobj.value="";
			}else{
				transferUserFunc(id);
			}
		}else{
			transferUserFunc(id);
		}
		//textobj.focus();
	}catch(e){
		alert("_gzzzb_loadDataCheckbox_onfocus::" + e.description);
	}
}
//单选点击取消的方法
function show_parentvalue(id,choosetype){

	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var divmainobj;
	var oldqueryvalue;
	var hiddenoptionallobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldqueryvalue=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldquerykey"){
			oldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	//var oldvalueobj=document.all("gzzzb_"+id+"_oldqueryvalue");//上次查询条件的value保存的对象
	//var keyobj=document.all(id);//现在在做选择的记录key保存的对象
	//var oldkeyobj=document.all("gzzzb_"+id+"_oldquerykey");//上次的记录key保存的对象

	//var textobj=document.all(id+"_text");
	textobj.value=oldqueryvalue.value;
	keyobj.value=oldkeyobj.value;
	divmainobj.style.display="none";
	//window.clearTimeout(timeid);

	//_gzzzb_selectOption_hidden_Select_DIV_checkbox(id,'1');
}





//复选事件
//AJAX取得数据  choosetypetype为类型 1为单选 2为复选 是否有树形结构 文本框调用   num为1时是文本框调用  num为2时是图片调用  显示全部
function checkboxqueryinfo_last(id,choosetype,num){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var optionobj;
	var divmainobj;
	var queryvalueobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_queryValue"){
			queryvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	//把中文，替换为英文,
	var re = /，/g;
	textobj.value=textobj.value.replace(re,",");

	if(textobj.value==queryvalueobj.value){
		//return;
	}else{
		queryvalueobj.value=textobj.value;
	}
	isshowdiv="0";
	var invalue=textobj.value;
	var textvalue="";
	invalues=invalue.split(",");

	var newkey="";
	var keyvalues=keyobj.value.split(",");

	if(invalues[invalues.length-1]!=""){
		if(keyvalues[invalues.length-1]=="")
			newkey=" ,";
	}

	if(hiddenobj.isshowtree=="yes"&&hiddenobj.getAttribute("staticdata")=="no"){
		optionobj.style.display="none";
		loadQueryNodeCheckbox(id,id+"_option",id+"_hidden_option_all",num);
		countPosition(id);
		divmainobj.style.display="block";
	}else{
		window.clearTimeout(timeid);
		timeid=window.setTimeout("checkboxqueryinfo('"+id+"','"+choosetype+"','"+num+"')",100);
		window.setTimeout("divchangesize('"+id+"')",50);

		//addoldCheckboxSelectedvalues(id);
	}
	/*if(event.keyCode == 13){//13为回车键值
	 //如果是回车，转换为Tab键（可触发该对象的onChange事件)
	 event.keyCode=9;//9为Tab键值
	 }*/
}
//点击输入文本框的事件
function checkboxqueryinfo_onclick(id,choosetype,num)
{
	// 使用AJAX请求获取查询信息。
	//if(isfunction=="0"){
	//isfunction++;
	isshowdiv="0";
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var fullurlobj;//现在在做选择的记录全路径的对象
	var divmainobj;
	var oldoptionobj;
	var optionobj;
	var alloldkeyobj;
	var alloldvalueobj;
	var oldqueryvalue;
	var hiddenoptionallobj;
	var oldkeyobj;
	for(var idnum=0; idnum<Gzzzb_SelectOption_ids.length; idnum++){
		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_hiddenvalue"){
			hiddenobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == id){
			keyobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == id + "_text"){
			textobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_checked_fullurl"){
			fullurlobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_main"){
			divmainobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == id + "_option_old"){
			oldoptionobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == id + "_option"){
			optionobj = Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_checked_allkey"){
			alloldkeyobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_checked_allvalue"){
			alloldvalueobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_oldqueryvalue"){
			oldqueryvalue = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == id + "_hidden_option_all"){
			hiddenoptionallobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == "gzzzb_" + id + "_oldquerykey"){
			oldkeyobj = Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(textobj.value != ""){
		if(textobj.value.substring(textobj.value.length-1,textobj.value.length) != ","){
			textobj.value = textobj.value + ",";
		}
		//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
		with(document.selection.createRange()){
			moveStart("character",textobj.value.length);
			collapse();
			select();
		}
		//================================
	}

	//var hiddenobj=document.all("gzzzb_"+id+"_hiddenvalue");
	//oldoptionobj.style.display="none";
	var dataSource = hiddenobj.getAttribute("dataSource");//得到传数据的数据接口的数据源
	var contextPath = hiddenobj.getAttribute("contextPath");//系统根目录路径
	var checkboxDisplay = hiddenobj.getAttribute("checkboxDisplay");//复选框实现显示
	var radioOrCheckbox = hiddenobj.getAttribute("radioOrCheckbox");//树是单选还是复选
	var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
	var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
	var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
	var userParameter=hiddenobj.getAttribute("userParameter");
	var matchingsetup=hiddenobj.getAttribute("matchingsetup");
	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");
	var dictionaryType=hiddenobj.getAttribute("dictionaryType");
	var invalue=textobj.value;
	var textvalue="";
	invalues=invalue.split(",");
	//var checkedkeyobj=document.all(id);

	if(num=="1"){
		if(invalues.length>1)
			textvalue=invalues[invalues.length-1];
		else
			textvalue=invalue;
	}

	//if(invalue==""){
	var checkedkeys=keyobj.value;
	if(hiddenobj.isshowtree=="no"){//一级选择项的Ajax方法调用
		var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&checkedkeys="+checkedkeys+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize, function(reqText) {

			if(reqText!=null && reqText!=""){
				if(hiddenoptionallobj.innerHTML=="")
					hiddenoptionallobj.innerHTML=reqText;
				checboxshowquertdata(id,reqText,num);
			}
		});
		b.doGet();
		/*var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&checkedkeys="+checkedkeys+"&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize, function(reqText) {
		 //if(hiddenoptionallobj.innerHTML=="")
		 //	hiddenoptionallobj.innerHTML=reqText;
		 checboxshowquertdata(id,reqText,num);

		 });
		 a.doGet();*/

	}else{
		//多级树是是动态加载还是静态加载
		if(hiddenobj.getAttribute("staticdata")=="yes"){
			//动态加载数据
			isfunction="0";
			var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&fontColor="+fontColor+"&fontSize="+fontSize+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_option&dataSource="+dataSource+"&defaultKeys="+keyobj.value, function(reqText) {//com.san.gzzzb.tools.taglib.gzzzbSelectOption.SelectOptionTreeDataSource
				if(reqText!=null && reqText!=""){
					var jsStr=reqText.substr(reqText.indexOf("#####")+5);
					optionobj.innerHTML=reqText.replace("#####"+jsStr,"");
					setObjByMainDivId(document.getElementById(id+"_option"));
					eval(reqText.substr(reqText.indexOf("#####")+5));
					addoldCheckboxSelectedvalues(id);
					var tree=getObjByMainDivId(id+"_option");
					tree.setDefaultKeys(keyobj.value);
					window.setTimeout("divchangesize('"+id+"')",50);
				}else{
					optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";
					//addoldCheckboxSelectedvalues(id);
				}
			});
			a.doGet();
			//divmainobj.style.display="block";
		}else{
			//静态加载多级树的数据
			isfunction="0";
						if(hiddenoptionallobj.innerHTML==""){
				var a = new AJAXInteraction(contextPath + "/GetSelectQueryDataAjaxServlet.san?userParameter=" + userParameter
						+ "&dictionaryType=" + dictionaryType
						+ "&querytype=1"
						+ "&id=" + id
						+ "&matchingsetup=" + matchingsetup
						+ "&unitCode=" + textvalue
						+ "&type=" + choosetype
						+ "&isshowtree=" + hiddenobj.isshowtree
						+ "&fontColor=" + fontColor
						+ "&fontSize=" + fontSize
						+ "&contextPath=" + contextPath
						+ "&parentChildIsolate=" + parentChildIsolate
						+ "&parentCheckboxEnable=" + parentCheckboxEnable
						+ "&parentRadioEnable=" + parentRadioEnable
						+ "&radioOrCheckBox=" + radioOrCheckbox
						+ "&checkboxDisplay=" + checkboxDisplay
						+ "&divId=" + id + "_hidden_option_all"
						+ "&dataSource=" + dataSource
						+ "&checkedkeys=" + keyobj.value
						, function(reqText) {
							if(reqText!=null && reqText!=""){
								var jsStr=reqText.substr(reqText.indexOf("#####")+5);

								if(hiddenoptionallobj.innerHTML==""){
									var text=reqText.replace("#####"+jsStr,"");
									hiddenoptionallobj.innerHTML=text;

								}
								setObjByMainDivId(document.getElementById(id+"_hidden_option_all"));
								eval(reqText.substr(reqText.indexOf("#####")+5));
								optionobj.style.display="none";
								_gzzzb_getChileTree(id);
							}
						});
				a.doGet();
			}else{
				optionobj.style.display="none";
			}

			loadQueryNodeCheckbox(id,id+"_option",id+"_hidden_option_all",num);
		}
	}
//	}else{
	countPosition(id);
	divmainobj.style.display="block";
	//}

}
//复选的查询 数据加载
function checkboxqueryinfo(id,choosetype,num)
{
	// 使用AJAX请求获取查询信息。

	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var fullurlobj;//现在在做选择的记录全路径的对象
	var divmainobj;
	var oldoptionobj;
	var optionobj;
	var alloldkeyobj;
	var alloldvalueobj;
	var oldqueryvalue;
	var hiddenoptionallobj;
	var oldkeyobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			fullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
			oldoptionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			alloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			alloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldqueryvalue=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldquerykey"){
			oldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	//oldoptionobj.style.display="none";
	//var hiddenobj=document.all("gzzzb_"+id+"_hiddenvalue");
	//得到树需要的配置参数
	var dataSource=hiddenobj.getAttribute("dataSource");
	var contextPath=hiddenobj.getAttribute("contextPath");
	var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
	var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
	var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
	var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
	var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
	var userParameter=hiddenobj.getAttribute("userParameter");

	isfunction=0;
	//var textobj=document.all(id+"_text");
	var invalue=textobj.value;
	var matchingsetup=hiddenobj.getAttribute("matchingsetup");
	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");
	var equalcolor=hiddenobj.getAttribute("equalcolor");

	var dictionaryType=hiddenobj.getAttribute("dictionaryType");

	//var keyobj=document.all(id);
	var textvalue="";
	var newkey="";
	var equalsValues=textobj.value;//用来保存全部记录
	invalues=invalue.split(",");
	if(num=="1"){
		if(invalues.length>1)
			textvalue=invalues[invalues.length-1];
		else
			textvalue=invalue;
	}
	//判断是否需要显示多级树
	if(hiddenobj.isshowtree=="no"){


		var checkboxalloldkeyobj=alloldkeyobj;//全部的记录key保存的对象
		var checkboxalloldvalueobj=alloldkeyobj;//全部的记录保存的对象
		var allkey=checkboxalloldkeyobj.value.split(",");
		var newqueryvalue="";
		var issave="false";
		var olddivinnerhtml="";

		var newkey="";
		var inputkeys=keyobj.value.split(",");
		var checkboxallkeys=checkboxalloldkeyobj.value.split(",");
		var trobjkey="";
		for(var j=0;j<invalues.length;j++){
			if(invalues[j]!=""){
				var isall="false";
				trobjkey="";
				//判断在记录隐藏的div的数据是否存在完全匹配记录
				for(var i=0;i<hiddenoptionallobj.firstChild.firstChild.childNodes.length;i++){
					var trobj=hiddenoptionallobj.firstChild.firstChild.childNodes[i];
					if(trobj.innerText==invalues[j]){
						isall="true";//有完全匹配记录
						trobjkey=trobj.key;
					}
				}
				if(isall=="false"){
					if(newkey=="")
						newkey=" ,";//没匹配的内容的key
					else
						newkey=newkey+" ,";//没匹配的内容的key
				}else{
					if(newkey=="")
						newkey=trobjkey+",";//匹配的了的key
					else
						newkey=newkey+trobjkey+",";//匹配的了的key
				}
			}
		}
		keyobj.value=newkey;
		var checkedkeys=keyobj.value;
		var newkeys=checkedkeys.split(",");
		var isShow=false;
		for(var i=0;i<newkeys.length;i++){
			if(newkeys[i]==" "){
				isShow=true;
			}
		}
		if(isShow==true){
			var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?equalsValues="+equalsValues+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&checkedkeys="+checkedkeys+"&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
				if(reqText!=null && reqText!=""){
					checboxshowquertdata(id,reqText,num);
				}
			});
			a.doGet();
		}else{
			textobj.blur();
		}
	}else{
		//放置动态查询的函数
		isfunction="0";
		var a = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&unitCode="+textvalue+"&type="+choosetype+"&isshowtree="+hiddenobj.isshowtree+"&fontColor="+fontColor+"&fontSize="+fontSize+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_option&dataSource="+dataSource+"&defaultKeys="+keyobj.value, function(reqText) {//com.san.gzzzb.tools.taglib.gzzzbSelectOption.SelectOptionTreeDataSource
			if(reqText!=null && reqText!=""){
				var jsStr=reqText.substr(reqText.indexOf("#####")+5);
				optionobj.innerHTML=reqText.replace("#####"+jsStr,"");
				eval(reqText.substr(reqText.indexOf("#####")+5));

				addoldCheckboxSelectedvalues(id);
				var tree=getObjByMainDivId(id+"_option");
				tree.setDefaultKeys(keyobj.value);
			}else{
				optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\"><table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table></td></tr></TBODY></table>";
				//addoldCheckboxSelectedvalues(id);
			}
		});
		a.doGet();
		countPosition(id);
		divmainobj.style.display="block";
	}
}
//复选静态加载的方法
function loadQueryNodeCheckbox(id,MainDivId,QueryDivId,num){
	try{

		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var optionobj;
		var hiddenallobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenallobj=Gzzzb_SelectOption_objs[idnum];
			}
		}
		var queryvalue=textobj.value;
		var queryItem=queryvalue.split(",");
		var queryinfo="";
		if(num=="1"){
			if(queryvalue.length>0){
				if(queryItem.length>1)
					queryinfo=queryItem[queryItem.length-1];
				else
					queryinfo=queryvalue;
			}
		}
		if(queryinfo==""){

			hiddenallobj.style.display="block";
			_gzzzb_getChileTree(id);
		}else{
			optionobj.style.display="block";
			hiddenallobj.style.display="none";
			var QueryDiv=hiddenallobj;//查询对象
			var MainDiv=optionobj;//存存储对象

			// var keyobj=document.all(id);//现在在做选择的记录key保存的对象
			var tree1=getObjByMainDivId(QueryDivId);
			if(tree1!=undefined){
				var matching=hiddenobj.getAttribute("matchingsetup").split(",");
				tree1.queryLoadNode(queryinfo,MainDiv,"/GZZZB/","_gzzzb_getChileTree('"+id+"')","treechecked(this,'"+id+"')","selected_values_checkbox(this,'"+id+"')",matching,keyobj.value);
			}
			window.setTimeout("divchangesize('"+id+"')",50);
			var tree2=getObjByMainDivId(QueryDivId);

			if(tree2!=undefined){
				if(tree2.getAllNodes()==""){
					optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";
				}
			}else{
				optionobj.innerHTML="<table width=\"100%\"  cellSpacing=0 cellPadding=0><TBODY><tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" style=\"font-size:13px;font-color:red;\" align=\"center\">未找到相关信息</td></tr></TBODY></table>";
			}
		}
		addoldCheckboxSelectedvalues(id);//加载以前查询过的数据
	}catch(e){
		alert(" loadQueryNodeCheckbox : " + e.description);
	}
}
//加载以前选择过的现在没查询出来的数据
function addoldCheckboxSelectedvalues(id){
	//var alloldkeyobj=document.all("gzzzb_"+id+"_checked_allkey");//全部的记录key保存的对象
	//var alloldvalueobj=document.all("gzzzb_"+id+"_checked_allvalue");//全部的记录保存的对象
	//var keyobj=document.all(id);//现在在做选择的记录key保存的对象
	//var checkboxalloldfullurlobj=document.all("gzzzb_"+id+"_checked_fullurl");//全部的记录的全路径的对象
	//var textobj=document.all(id+"_text");
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var oldoptionobj;
	var alloldkeyobj;
	var alloldvalueobj;
	var checkboxalloldfullurlobj;//现在在做选择的记录全路径的对象
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
			oldoptionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			alloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			alloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			checkboxalloldfullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");

	var style="";
	if(fontColor!=""){
		style="color:"+fontColor+";";
	}
	if(fontSize!=""){
		style=style+"font-size:"+fontSize+";";
	}
	var oldinnerhtml="";

	var alloldkeys=alloldkeyobj.value.split(",");
	var alloldvalues=alloldvalueobj.value.split(",");
	var alloldfullurl=checkboxalloldfullurlobj.value.split(",");
	var textvalue=textobj.value.split(",");
	//判断数据是否需要显示
	if(alloldkeyobj.value!=""){
		if(textobj.value==""){
			for(var i=0;i<alloldkeys.length;i++){
				var ischecked=false;
				if(alloldkeys[i]!=""){
					for(var j=0;j<textvalue.length;j++){
						if(textvalue[j]==alloldvalues[i])
							ischecked=true;
					}
					//判断该记录是否选中  没选中则不打勾
					if(ischecked)
						oldinnerhtml=oldinnerhtml+"<tr height=20 value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\"><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left  style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<input type=\"checkbox\" name='"+id+"_checkbox' value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\" onclick=\"old_selected_values_checkbox(this,'"+id+"');\" checked/>&nbsp;"+alloldfullurl[i]+"</td></tr>";
					else
						oldinnerhtml=oldinnerhtml+"<tr height=20 value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\"><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left  style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<input type=\"checkbox\" name='"+id+"_checkbox' value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\" onclick=\"old_selected_values_checkbox(this,'"+id+"');\"/>&nbsp;"+alloldfullurl[i]+"</td></tr>";

				}
			}
		}else{
			if(textvalue[textvalue.length-1]==""){
				for(var i=0;i<alloldkeys.length;i++){
					var ischecked=false;
					if(alloldkeys[i]==" "&&alloldkeys[i]!=""){
						for(var j=0;j<textvalue.length;j++){
							if(textvalue[j]==alloldvalues[i])
								ischecked=true;
						}
						if(ischecked)
							oldinnerhtml=oldinnerhtml+"<tr height=20 value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\"><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left  style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<input type=\"checkbox\" name='"+id+"_checkbox' value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\" onclick=\"old_selected_values_checkbox(this,'"+id+"');\" checked/>&nbsp;<font style=\"cursor:hand;\">"+alloldfullurl[i]+"</font></td></tr>";
						else
							oldinnerhtml=oldinnerhtml+"<tr height=20 value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\"><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left  style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<input type=\"checkbox\" name='"+id+"_checkbox' value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\" onclick=\"old_selected_values_checkbox(this,'"+id+"');\"/>&nbsp;<font style=\"cursor:hand;\">"+alloldfullurl[i]+"</font></td></tr>";
					}
				}
			}else{

				for(var i=0;i<alloldkeys.length;i++){
					var ischecked=false;
					if(alloldkeys[i]!=""){
						if(alloldvalues[i].indexOf(textvalue[textvalue.length-1])==-1||alloldkeys[i]==" "){
							for(var j=0;j<textvalue.length;j++){
								if(textvalue[j]==alloldvalues[i])
									ischecked=true;
							}
							if(ischecked)
								oldinnerhtml=oldinnerhtml+"<tr height=20 value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\"><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left  style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<input type=\"checkbox\" name='"+id+"_checkbox' value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\" onclick=\"old_selected_values_checkbox(this,'"+id+"');\" checked/>&nbsp;<font style=\"cursor:hand;\">"+alloldfullurl[i]+"</font></td></tr>";
							else
								oldinnerhtml=oldinnerhtml+"<tr height=20 value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\"><td  onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" align=left  style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\">&nbsp;&nbsp;<input type=\"checkbox\"  name='"+id+"_checkbox' value=\""+alloldvalues[i]+"\" key=\""+alloldkeys[i]+"\" onclick=\"old_selected_values_checkbox(this,'"+id+"');\"/>&nbsp;<font style=\"cursor:hand;\">"+alloldfullurl[i]+"</font></td></tr>";

						}
					}
				}
			}
		}
	}
	//把数据整合在成一个完整的table
	if(oldinnerhtml!=""){
		oldinnerhtml="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY><hr>"+oldinnerhtml+"</TBODY></table>";
	}
	//把数据加载进div

	oldoptionobj.innerHTML=oldinnerhtml;

	if(oldoptionobj.innerHTML!=""){
		//oldoptionobj.style.display="block";
	}
}
//复选的数据加载
function checboxshowquertdata(id,reqText,num){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var optionobj;
	var divmainobj;
	var checkboxalloldkeyobj;
	var checkboxalloldvalueobj;
	var checkboxalloldfullurlobj;//现在在做选择的记录全路径的对象
	var hiddenoptionallobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			checkboxalloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			checkboxalloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			checkboxalloldfullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	//if(hiddenoptionallobj.innerHTML=="")
	//		hiddenoptionallobj.innerHTML	=reqText;

	isfunction="0";
	//var keyobj=document.all(id);//现在在做选择的记录key保存的对象

	//var checkboxalloldkeyobj=document.all("gzzzb_"+id+"_checked_allkey");//全部的记录key保存的对象
	//var checkboxalloldvalueobj=document.all("gzzzb_"+id+"_checked_allvalue");//全部的记录保存的对象
	var allkey=checkboxalloldkeyobj.value.split(",");
	var checkedkeys=keyobj.value.split(",");
	var newqueryvalue="";
	var issave="false";
	var olddivinnerhtml="";

	var invalue=textobj.value;
	var invalues;
	var newkey="";
	var invalues=invalue.split(",");

	var fontColor=hiddenobj.getAttribute("fontColor");
	var fontSize=hiddenobj.getAttribute("fontSize");

	var style="";
	if(fontColor!=""){
		style="color:"+fontColor+";";
	}
	if(fontSize!=""){
		style=style+"font-size:"+fontSize+";";
	}
	//判断是否有要显示的以前选中的的记录
	if(checkboxalloldkeyobj.value!=""){
		olddivinnerhtml="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY>";
		var checkeallvalue=checkboxalloldvalueobj.value.split(",");
		for(var i=0;i<allkey.length;i++){
			var oldischecked="false";

			//if(allkey[i]!=" "){
			for(var j=0;j<checkedkeys.length;j++){
				if(allkey[i]==""||allkey[i]==" "){
					if(checkeallvalue[i]==invalues[j])
						oldischecked="true";//已经选中了
				}else{
					if(allkey[i]==checkedkeys[j])
						oldischecked="true";//已经选中了
				}
			}
			if(textobj.value==""){
				if(allkey[i]==" "){
					olddivinnerhtml=olddivinnerhtml+"<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><input type=\"checkbox\" name='"+id+"_checkbox' key=' ' value='"+checkeallvalue[i]+"' onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+checkeallvalue[i]+"</font></td></tr>";
				}
			}else{
				if(allkey[i]==" "){
					if(oldischecked=="true"){
						//控制是否显示以前选中现在还选中的数据
						olddivinnerhtml=olddivinnerhtml+"<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><input type=\"checkbox\" name='"+id+"_checkbox' key=' ' value='"+checkeallvalue[i]+"' onclick=\"checkboxchangevalue(this,'"+id+"');\" checked/><font style=\"cursor:hand;\">"+checkeallvalue[i]+"</font></td></tr>";
					}else{
						olddivinnerhtml=olddivinnerhtml+"<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><input type=\"checkbox\" name='"+id+"_checkbox' key=' ' value='"+checkeallvalue[i]+"' onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+checkeallvalue[i]+"</font></td></tr>";
					}
				}else{
					if(invalues.length>1&&allkey[i]!=""){
						if(invalues[invalues.length-1]!=","){
							if(checkeallvalue[i].indexOf(invalues[invalues.length-1])==-1){
								if(oldischecked=="true"){
									//控制是否显示以前选中现在还选中的数据
									olddivinnerhtml=olddivinnerhtml+"<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><input type=\"checkbox\" name='"+id+"_checkbox' key='"+allkey[i]+"' value='"+checkeallvalue[i]+"' onclick=\"checkboxchangevalue(this,'"+id+"');\" checked/><font style=\"cursor:hand;\">"+checkeallvalue[i]+"</font></td></tr>";
								}else{
									olddivinnerhtml=olddivinnerhtml+"<tr height=20><td onmouseover=\"_gzzzb_changecolor(this,'1','"+id+"');\"  onmouseout=\"_gzzzb_changecolor(this,'2','"+id+"');\" onclick=\"selectcheckbox(this,'"+id+"');\" style=\"height:1.5em;padding-left:1px;cursor:hand;"+style+"\"><input type=\"checkbox\" name='"+id+"_checkbox' key='"+allkey[i]+"' value='"+checkeallvalue[i]+"' onclick=\"checkboxchangevalue(this,'"+id+"');\"/><font style=\"cursor:hand;\">"+checkeallvalue[i]+"</font></td></tr>";
								}
							}
						}
					}
				}
			}
		}

		olddivinnerhtml=olddivinnerhtml+"</TBODY></table>";
	}


	if(invalue!=""){
		if(checkboxalloldkeyobj.value=="")
			optionobj.innerHTML=reqText;
		else{
			if(olddivinnerhtml=="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY></TBODY></table>")
				optionobj.innerHTML=reqText;
			else{
				optionobj.innerHTML=reqText;
				//optionobj.innerHTML=reqText+"<hr>"+olddivinnerhtml;
			}
		}
	}else{
		if(checkboxalloldkeyobj.value==""){
			optionobj.innerHTML	=reqText;
		}
		else{
			if(olddivinnerhtml=="<table width='100%'  cellSpacing=0 cellPadding=0><TBODY></TBODY></table>"){
				optionobj.innerHTML=reqText;
			}else{
				optionobj.innerHTML=reqText;
				//optionobj.innerHTML=reqText+"<hr>"+olddivinnerhtml;
			}
		}
	}
	divchangesize(id);
	//用于判断是否需要出现滚动条
	//if(document.all("gzzzb_"+id+"_main").clientHeight>300)
	//	document.all(id+"_option").style.height="280";
	if(num==1){
		countPosition(id);
		divmainobj.style.display="block";
	}
	//else
	//	_gzzzb_selectOption_ShowDiv_Select(id);
}
var isClickPosition="";//记录点击什么位置选中复选框 checkbox为点击复选框选中  lable为点击内容选中
//复选改变复选框状态
function checkboxchangevalue(obj,id)
{
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var divmainobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(textobj.value!="" && (keyobj.value=="" ||keyobj.value == " ," || keyobj.value==",")){
		textobj.value="";
		keyobj.value="";
	}
	var invalue=textobj.value;
	var invalues=invalue.split(",");

	var newtext="";//接收选择后比较后的内容
	var newkey="";//接收选择后比较后的key
	var keys=keyobj.value.split(",");
	var checkboxkey=obj.key;
	var checkboxvalue=obj.value;

	checkboxGetValueBySort(id);

	/*if(obj.checked==true){
	 var newinkeys=keyobj.value;
	 if(invalue.length>0){
	 if(invalue.lastIndexOf(",")!=invalue.length-1&&newinkeys.substring(newinkeys.lastIndexOf(",")-1,newinkeys.lastIndexOf(","))==" "){
	 newinkeys=keyobj.value.substring(0,keyobj.value.lastIndexOf(",")-1);
	 }
	 }
	 keys=newinkeys.split(",");
	 var newinvalue=invalue;//如果是搜索则把最后一个逗号后的值去掉
	 newinvalue=invalue.substring(0,invalue.lastIndexOf(",")+1);
	 if(invalue==""){
	 newtext=checkboxvalue+",";
	 newkey=checkboxkey+",";
	 }else{
	 newtext=newinvalue+checkboxvalue+",";
	 newkey=newinkeys+checkboxkey+",";
	 }
	 }else{
	 for(var i=0;i<keys.length;i++){
	 if(keys[i]==" "){

	 if(invalues[i]!=checkboxvalue){
	 if(newtext==""){
	 newtext=invalues[i]+",";
	 newkey=keys[i]+",";
	 }else{
	 newtext=newtext+invalues[i]+",";
	 newkey=newkey+keys[i]+",";
	 }
	 }
	 }else{
	 if(keys[i]!=checkboxkey&&keys[i]!=""){
	 if(newtext==""){
	 newtext=invalues[i]+",";
	 newkey=keys[i]+",";
	 }else{
	 newtext=newtext+invalues[i]+",";
	 newkey=newkey+keys[i]+",";
	 }
	 }
	 }
	 }
	 }

	 textobj.value=newtext;
	 keyobj.value=newkey;*/
	isClickPosition="chexkbox"
	//用于控制点击后要 主div（mainDiv）得到焦点 才能离开后失去焦点
	if(divmainobj.style.display=="block")
		divmainobj.focus();

}

function checkboxGetValueBySort(id){
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var newtext="";
	var newkey="";
	var checkObjs = document.all(id+"_checkbox");
	for(var i=0;i<checkObjs.length;i++){
		if(checkObjs[i].checked == true){
			if(newtext==""){
				newtext=checkObjs[i].value;
				newkey=checkObjs[i].key;
			}else{
				newtext=newtext+","+checkObjs[i].value;
				newkey=newkey+","+checkObjs[i].key;
			}
		}
	}
	textobj.value=newtext;
	keyobj.value=newkey;
}
//记录选中的数据 把所以选中的数据加载进来
function selectcheckbox(obj,id)
{
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var checkboxkey=keyobj.value;
	var checkboxvalue=textobj.value;
	if(checkboxvalue!="" && (checkboxkey=="" ||checkboxkey == " ," || checkboxkey==",")){
		textobj.value="";
		keyobj.value="";
	}
	if(isClickPosition!="chexkbox"){
		isClickPosition="lable";
		_gzzzb_selectOption_hidden_Select_DIV_checkbox(id,'2');
		if(obj.firstChild.checked==false){
			obj.firstChild.checked=true;
		}
		else{
			obj.firstChild.checked=false;
		}
		checkboxchangevalue(obj.firstChild,id);
	}
	isClickPosition="";
}
//点击确定按钮时的方法
function checkbox_submit(id){
	var hiddenobj;
	var divmainobj;

	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}

	}
	//window.clearTimeout(timeid);
	isshowdiv++;
	divmainobj.style.display="none";
	if(hiddenobj.isshowtree=="no")
		checkboxisvaluesava(id,"text");
	else
		treecheckboxisvaluesava(id);

	//divmainobj.style.display="none";
}
//复选选中离开后把选中的数据记录为选中过的数据里面去  如果有没有完全匹配的数据则提示是否保存数据
function checkboxisvaluesava(id,type){
	isfunction="0";
	var isall="false";
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var checkboxalloldkeyobj;
	var alloldvalueobj;
	var checkboxalloldvalueobj;
	var hiddenoptionallobj;
	var oldkeyobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			checkboxalloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			checkboxalloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldquerykey"){
			oldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var re = /，/g;
	textobj.value=textobj.value.replace(re,",");
	var invalue=textobj.value;
	var novalue="";
	var nokey="";
	var beingvalue="";
	var beingkey="";

	var isokallkey="";
	var isokallvalue="";
	var nookallkey="";
	var nookallvalue="";

	var invalues=invalue.split(",");
	var inputkeys=keyobj.value.split(",");
	var checkboxallkeys=checkboxalloldkeyobj.value.split(",");
	var checkboxallvalues=checkboxalloldvalueobj.value.split(",");
	//用于验证是否有改变过值
	/*if(_gzzzb_checkIsChange(id)){
	 return false;
	 }*/

	//var newinputkeys=new Array();//用于按tab键没有调用查询的方法 用于接受匹配到的keys的数组
	//判断是否有完全匹配的数据  把数据重组
	for(var j=0;j<invalues.length;j++){
		isall="false";
		var key="";//用来接受从全部数据匹配到的key
		var isbeing="false";
		if(hiddenoptionallobj.firstChild!=null){
			if(hiddenoptionallobj.firstChild.firstChild!=null){
				for(var i=0;i<hiddenoptionallobj.firstChild.firstChild.childNodes.length;i++){
					if(hiddenoptionallobj.firstChild.firstChild.childNodes[i].innerText==invalues[j]){
						isall="true";//有完全匹配记录
						key=hiddenoptionallobj.firstChild.firstChild.childNodes[i].key;
					}
				}
				//newinputkeys[newinputkeys.length]=key;
			}
		}

		if(invalues[j]!=""){

			if(isall=="false"){
				if(nokey==""){
					novalue=invalues[j]+",";
					nokey=" ,";
				}else{
					novalue=novalue+invalues[j]+",";
					nokey=nokey+" ,";
				}
				isbeing="false";
				for(var n=0;n<checkboxallkeys.length;n++){
					if(checkboxallkeys[n]==""||checkboxallkeys[n]==" "){
						if(invalues[j]==checkboxallvalues[n])
							isbeing="true";//如果记录的字段里面有该内容
					}else{
						if(inputkeys[j]==checkboxallkeys[n])
							isbeing="true";//如果记录的字段里面有该内容
					}
				}

				if(isbeing=="false"){
					if(isokallkey==""){
						isokallkey=" ,";
						isokallvalue=invalues[j]+",";
					}else{
						isokallkey=isokallkey+" ,";
						isokallvalue=isokallvalue+invalues[j]+",";
					}
				}
			}else{

				var newkey=inputkeys[j];

				if(newkey=="" || newkey=="undefined"|| newkey==undefined){
					newkey=key;
				}

				if(beingkey==""){
					beingvalue=invalues[j]+",";//匹配到的内容
					beingkey=newkey+",";//匹配的了的key
				}else{
					beingvalue=beingvalue+invalues[j]+",";//匹配到的内容
					beingkey=beingkey+newkey+",";//匹配的了的key
				}

				isbeing="false";
				for(var n=0;n<checkboxallkeys.length;n++){
					if(checkboxallkeys[n]==""||checkboxallkeys[n]==" "){
						if(invalues[j]==checkboxallvalues[n])
							isbeing="true";//如果记录的字段里面有该内容
					}else{
						if(newkey==checkboxallkeys[n])
							isbeing="true";//如果记录的字段里面有该内容
					}
				}
				if(isbeing=="false"){
					if(isokallkey==""){
						isokallkey=newkey+",";
						isokallvalue=invalues[j]+",";
					}else{
						isokallkey=isokallkey+newkey+",";
						isokallvalue=isokallvalue+invalues[j]+",";
					}

					if(nookallkey==""){
						nookallkey=newkey+",";
						nookallvalue=invalues[j]+",";
					}else{
						nookallkey=nookallkey+newkey+",";
						nookallvalue=nookallvalue+invalues[j]+",";
					}
				}
			}
		}
	}
	var isAddNewData=hiddenobj.isAddNewData;
	//如果有不完全匹配的数据则提示是否需要录入
	//alert(novalue);
	//if(isall=="false"){
	if(novalue!=""){
		var shownovalue;

		if(novalue.lastIndexOf(",")==novalue.length-1){
			shownovalue=novalue.substring(0,novalue.lastIndexOf(","));
		}

		if(isAddNewData=="true"){
			if(confirm("该选择项中没有  “"+shownovalue+"”，如需录入按[确定]，否则按[取消]")){
				textobj.blur();
				textobj.value=beingvalue+novalue;
				checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+isokallkey;
				checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+isokallvalue;

				//window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV_checkbox('"+id+"','1')",100)
				//	window.clearTimeout(timeid);

				document.all("gzzzb_"+id+"_main").style.display="none";
				//_gzzzb_Onchange(id);
			}else{
				textobj.value=beingvalue;
				keyobj.value=beingkey;
				textobj.focus();
				textobj.click();
				checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+nookallkey;
				checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+nookallvalue;
				//_gzzzb_Onchange(id);
			}
		}else{
			if(confirm("该选择项中没有  “"+shownovalue+"”， 如需删除按[确定]，修改按[取消]")){
				textobj.focus();
				textobj.value=beingvalue;
				keyobj.value=beingkey;
				checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+nookallkey;
				checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+nookallvalue;
				textobj.click();
				//_gzzzb_Onchange(id);
			}else{
				textobj.focus();
				//_gzzzb_Onchange(id);
				//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
				with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
				//================================
				textobj.click();
			}
		}
	}else{
		//if(textobj.value.lastIndexOf(",")!=textobj.value.length-1){
		//	textobj.value=textobj.value+",";
		//}

		/*if(checkboxalloldkeyobj.value==""){
		 checkboxalloldkeyobj.value=keyobj.value;
		 checkboxalloldvalueobj.value=textobj.value;
		 }else{
		 checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+isokallkey;
		 checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+isokallvalue;
		 }*/
		if(textobj.value!=""&&keyobj.value==""){
			keyobj.value=isokallkey;
		}
	}

	if(textobj.value==""&&keyobj.value!=""){
		keyobj.value="";
	}
	if(textobj.value!=""){
		if(textobj.value.substring(textobj.value.length-1,textobj.value.length)==",")
			textobj.value=textobj.value.substring(0,textobj.value.lastIndexOf(","));
	}
	/*if(textobj.value!=""){
	 if(textobj.value.substring(textobj.value.length-1,textobj.value.length)!=",")
	 textobj.value=textobj.value+",";
	 }*/




	//textobj.focus();
	//document.all(id+"_text").blur();
	//_gzzzb_selectOption_hidden_Select_DIV_checkbox(id,"1");
	//document.all("gzzzb_"+id+"_main").style.display="none";

	var newtext="";
	var newkey="";
	if(isokallkey == ""){
		isokallkey = keyobj.value;
	}else{
		if(keyobj.value !=""){
			isokallkey = isokallkey+","+keyobj.value;
		}
	}
	//alert(isokallkey);
	if(isokallkey!=""){
		var newKeys = isokallkey.split(",");
		var checkObjs = document.all(id+"_checkbox");
		for(var i=0;i<checkObjs.length/2;i++){
			var isChecked = false;
			for(var j=0;j<newKeys.length;j++){
				if(newKeys[j]!="" && checkObjs[i].key == newKeys[j]){
					isChecked = true;
				}
			}
			if(isChecked == true){
				if(newtext==""){
					newtext=checkObjs[i].value;
					newkey=checkObjs[i].key;
				}else{
					newtext=newtext+","+checkObjs[i].value;
					newkey=newkey+","+checkObjs[i].key;
				}
			}
		}
	}
	//alert(newkey);
	textobj.value=newtext;
	keyobj.value=newkey;

	_gzzzb_Onchange(id);
	//checkboxGetValueBySort(id);
	oldvalueobj.value=textobj.value;
	oldkeyobj.value=keyobj.value;
	if(type=="div"){
		textobj.focus();
		with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
	}
	transferUserFunc(id);
}
//验证是否有改变值
function _gzzzb_checkIsChange(id){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var checkboxalloldkeyobj;
	var oldvalueobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			checkboxalloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}

	}


	var textvalue=textobj.value;
	var oldtextvalue=oldvalueobj.value;

	if(textobj.value.substring(textobj.value.length-1,textobj.value.length)==",")
		textvalue=textobj.value.substring(0,textobj.value.lastIndexOf(","));

	if(oldvalueobj.value!=""){
		if(oldvalueobj.value.substring(oldvalueobj.value.length-1,oldvalueobj.value.length)==",")
			oldtextvalue=oldvalueobj.value.substring(0,oldvalueobj.value.lastIndexOf(","));
	}
	if(textvalue==oldtextvalue){
		return true;
	}else{
		return false;
	}

}

function _gzzzb_Onchange(id){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var checkboxalloldkeyobj;
	var oldvalueobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			checkboxalloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}

	}

	var onchageEvent=hiddenobj.onchange;

	var optionType=hiddenobj.getAttribute("optionType");
	var formName=hiddenobj.formName;
	var collectMeans=hiddenobj.collectMeans;
	var mainField=hiddenobj.mainField;
	var fieldCode=hiddenobj.fieldCode;
	var fieldText=hiddenobj.fieldText;

	//用于判断是否触发onchange事件
	//if(oldvalueobj.value!=""){
	var textvalue=textobj.value;
	var oldtextvalue=oldvalueobj.value;

	if(textobj.value.substring(textobj.value.length-1,textobj.value.length)==",")
		textvalue=textobj.value.substring(0,textobj.value.lastIndexOf(","));

	if(oldvalueobj.value!=""){
		if(oldvalueobj.value.substring(oldvalueobj.value.length-1,oldvalueobj.value.length)==",")
			oldtextvalue=oldvalueobj.value.substring(0,oldvalueobj.value.lastIndexOf(","));
	}
	if(textvalue!=oldtextvalue){

		//alert("select_option's changed 1!");

		if(optionType=="tag"){
			_gzzzb_write_dealWithUploadData(id);
		}
		if(onchageEvent!=null&&onchageEvent!="")
			eval(onchageEvent);

	}

	//}
}
//用于生成要上传数据的html
function _gzzzb_write_dealWithUploadData(id){
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var divmainobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");

	var formName=hiddenobj.formName;
	var collectMeans=hiddenobj.collectMeans;
	var mainField=hiddenobj.mainField;
	var fieldCode=hiddenobj.fieldCode;
	var fieldText=hiddenobj.fieldText;
	var childSize=hiddenobj.childSize;
	var textvalues=textobj.value;
	var keys=keyobj.value;
	var textvalue=textvalues.split(",");
	var key=keys.split(",");
	var html="";
	if(collectMeans==null||collectMeans==""){//单选
		//if(textvalues!=""){
		if(fieldText!=null&&fieldText!=""){
			if(fieldCode!=id)
				html="<input type=\"hidden\" name=\""+fieldCode+"\" value=\""+keys+"\" />";
			html=html+"<input type=\"hidden\" name=\""+fieldText+"\" value=\""+textvalues+"\" />";
		}
		//}else{
		//	html="";
		//}
	}else if(radioOrCheckbox=="checkbox"){
		//if(textvalues!=""){
		if(mainField!=null&&mainField!=""){
			for(var i=0;i<textvalue.length;i++){
				if(textvalue[i]!=""){
					html=html+"<input type=\"hidden\" name=\""+collectMeans+"["+childSize+"]."+fieldCode+"\" value=\""+key[i]+"\"><input type=\"hidden\" name=\""+collectMeans+"["+childSize+"]."+fieldText+"\" value=\""+textvalue[i]+"\">";
					childSize=parseInt(childSize)+1;
				}
			}
		}else{
			if(fieldText!=null&&fieldText!=""){
				html="<input type=\"hidden\" name=\""+fieldText+"\" value=\""+textvalues+"\" />";
			}
		}
		//}else{
		//	html="";
		//}
	}
	divmainobj.firstChild.innerHTML=html;
}

//按回车调用的事件
function _gzzzb_selectOption_hidden_Select_checkbox_enter(id){
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	textobj.blur();
}
//复选隐藏选中层的方法
function _gzzzb_selectOption_hidden_Select_checkbox(id){
	window.clearTimeout(timeid);
	timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV_checkbox('"+id+"','1')",100);

}
//复选隐藏选中层的方法
function _gzzzb_selectOption_hidden_Select_DIV_checkbox_mainDIv(id)
{
	window.clearTimeout(timeid);
	timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV_checkbox_mainDIv('"+id+"')",400);
}
function _gzzzb_selectOption_hidden_SelectDIV_checkbox_mainDIv(id)
{
	var hiddenobj;
	var divmainobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	checkboxisvaluesava(id,"div");
	divmainobj.style.display="none";
}
//复选隐藏选中层的方法
function _gzzzb_selectOption_hidden_Select_DIV_checkbox(id,num)
{
	window.clearTimeout(timeid);
	timeid=window.setTimeout("_gzzzb_selectOption_hidden_SelectDIV_checkbox('"+id+"','"+num+"')",300);
}
//隐藏或显示的实际方法 num主要用来判断鼠标从按钮离开后是否进去了div层  在300毫秒里进入了div则层显示  否则层消失 （无复选）
function _gzzzb_selectOption_hidden_SelectDIV_checkbox(id,num)
{
	var hiddenobj;
	var divmainobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(num=="1"){
		if(hiddenobj.isshowtree=="no")
			checkboxisvaluesava(id,"text");
		else{
			treecheckboxisvaluesava(id);
		}
		divmainobj.style.display="none";
	}else{
		if(isshowdiv=="0"){
			countPosition(id);
			divmainobj.style.display="block";
		}
	}
}

function treechecked(obj,id){
	_gzzzb_selectOption_hidden_Select_DIV_checkbox(id,'2');
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var optionobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var invalue=textobj.value;
	var invalues=invalue.split(",");
	var keys=keyobj.value.split(",");
	var newtext="";
	var newkey="";

	var treetext="";
	var treekey="";

	var queryvalue=textobj.value;
	var queryItem=queryvalue.split(",");
	var queryinfo="";
	if(queryvalue.length>0){
		if(queryItem.length>1)
			queryinfo=queryItem[queryItem.length-1];
		else
			queryinfo=queryvalue;
	}

	var tree1=null;

	if(optionobj.style.display=="none"){
		tree1=getObjByMainDivId(id+"_hidden_option_all");
	}
	if(tree1==null)
		tree1=getObjByMainDivId(id+"_option");
	var arr=tree1.getNodeAttribute(tree1.getNodeByCheckbox(obj));
	if(hiddenobj.getAttribute("selectvaluetype")=="1"){
		treetext=arr[7];//选中的数据内容
	}else{
		treetext=arr[10];
	}

	if(treetext=="")
		treetext=arr[4];
	if(treetext=="")
		treetext=arr[8];
	treekey=arr[1];//选中的key

	var textvalue=invalue;
	if(invalue.length>0){

		if(invalue.lastIndexOf(",")==-1){
			textvalue="";
		}else{
			if(invalue.lastIndexOf(",")!=invalue.length-1){
				textvalue=invalue.substring(0,invalue.lastIndexOf(","));
			}
		}
	}
	if(invalue.lastIndexOf(",")!=invalue.length-1)
		textvalue=textvalue+",";

	if(arr[6]=="true"){
		if(invalue==""){
			newtext=treetext+",";
			newkey=treekey+",";
		}else{
			newtext=textvalue+treetext+",";
			newkey=keyobj.value+treekey+",";
		}


		if(optionobj.style.display=="block")
			changeDivChooseStaue_checked(id,treekey,true);
	}else{
		for(var i=0;i<keys.length;i++){
			if(keys[i]!=treekey&&keys[i]!=""){
				if(newtext==""){
					newtext=invalues[i]+",";
					newkey=keys[i]+",";
				}else{
					newtext=newtext+invalues[i]+",";
					newkey=newkey+keys[i]+",";
				}
			}
		}

		if(optionobj.style.display=="block")
			changeDivChooseStaue_checked(id,treekey,false);
	}
	textobj.value=newtext;
	keyobj.value=newkey;
	_gzzzb_getChileTree(id);
	//textobj.value=newselectdvalue+treetext;
	//keyobj.value=newselectdkey+treekey;
	//_gzzzb_getChileTree(id);
	//判断是在查询的div选择还是在记录全部的div选择
	//if(optionobj.style.display=="block"){
	//	changeAllDivChooseStaue_checked(id,keyobj.value);
	//}
}
//有树的复选选中时的方法 动态树  (有选中父节点时字节点也选中则用此函数 暂不提供)
/*function treechecked(obj,id){
 _gzzzb_selectOption_hidden_Select_DIV_checkbox(id,'2');

 var hiddenobj;
 var keyobj;//现在在做选择的记录key保存的对象
 var textobj;
 var optionobj;
 for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
 if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
 hiddenobj=Gzzzb_SelectOption_objs[idnum];
 }
 if(Gzzzb_SelectOption_ids[idnum]==id){
 keyobj=Gzzzb_SelectOption_objs[idnum];
 }

 if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
 textobj=Gzzzb_SelectOption_objs[idnum];
 }
 if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
 optionobj=Gzzzb_SelectOption_objs[idnum];
 }
 }
 if(optionobj.style.display=="block"){
 changeAllDivChooseStaue_nochecked(id,keyobj.value);
 }
 var invalue=textobj.value;
 var invalues=invalue.split(",");
 var keys=keyobj.value.split(",");
 var newtext="";
 var newkey="";

 var treetext="";
 var treekey="";

 var queryvalue=textobj.value;
 var queryItem=queryvalue.split(",");
 var queryinfo="";
 if(queryvalue.length>0){
 if(queryItem.length>1)
 queryinfo=queryItem[queryItem.length-1];
 else
 queryinfo=queryvalue;
 }

 var tree1=null;

 if(optionobj.style.display=="none"){
 tree1=getObjByMainDivId(id+"_hidden_option_all");
 }

 if(tree1==null)
 tree1=getObjByMainDivId(id+"_option");

 if(tree1!=undefined){
 var arr=tree1.getAllNodeAttribute();
 for(var i=0;i<arr.length;i++){
 if(arr[i][6]=="true"){
 if(hiddenobj.getAttribute("selectvaluetype")=="1"){
 treetext=arr[i][7]+","+treetext;//树上的全部选中的数据内容
 }else{
 treetext=arr[i][10]+","+treetext;
 }
 treekey=arr[i][1]+","+treekey;//树上的去全部选中的数据的key
 }
 }
 }

 //for(var j=0;j<keys.length;j++){
 //	if(keys[j]==" "){
 //		newtext=invalues[j]+","+newtext;
 //		newkey=keys[j]+","+newkey;
 //	}
 //}
 var checkboxname=id+"_checkbox";
 var newselectdvalue="";
 var newselectdkey="";
 var selUsers = document.all.tags('input');
 for(i=0; i<selUsers.length; i++)
 {

 if(selUsers(i).type == 'checkbox')
 {
 if(selUsers(i).name==checkboxname){
 if(selUsers(i).checked ==true){
 if(newselectdkey==""){
 newselectdvalue=selUsers(i).value+",";
 newselectdkey=selUsers(i).key+",";
 }else{
 newselectdvalue=newselectdvalue+selUsers(i).value+",";
 newselectdkey=newselectdkey+selUsers(i).key+",";
 }
 }
 }
 }
 }
 textobj.value=newselectdvalue+treetext;
 keyobj.value=newselectdkey+treekey;
 _gzzzb_getChileTree(id);
 //判断是在查询的div选择还是在记录全部的div选择
 if(optionobj.style.display=="block"){
 changeAllDivChooseStaue_checked(id,keyobj.value);
 }
 }*/
//选择查询div时改变记录全部的div的选择信息的函数
/*function changeAllDivChooseStaue_checked(id,keys){
 var tree2=getObjByMainDivId(id+"_hidden_option_all");
 var Nodes=new Array();
 Nodes=tree2.getNodesByKeys(keys,"1");
 for(var m=0;m<Nodes.length;m++){
 tree2.setNodeCheckboxSelected(Nodes[m],true);
 }
 }*
 //选择查询div时改变记录全部的div的选择信息的函数
 /*function changeAllDivChooseStaue_nochecked(id,keys){
 var tree2=getObjByMainDivId(id+"_hidden_option_all");
 var Nodes=new Array();
 Nodes=tree2.getNodesByKeys(keys,"1");
 for(var m=0;m<Nodes.length;m++){
 tree2.setNodeCheckboxSelected(Nodes[m],false);
 }
 }*/

function changeDivChooseStaue_checked(id,key,ischecked){
	var tree2=getObjByMainDivId(id+"_hidden_option_all");
	var Nodes=tree2.getNodesByKey(key,"1");

	tree2.setNodeCheckboxSelected(Nodes[0],ischecked);
}

//有多级树的复选离开验证数据的函数
function treecheckboxisvaluesava(id){
	isshowdiv++;
	if(isfunction=="0")
		isfunction++;
	else
		return;
	var isall="false";

	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var checkboxalloldkeyobj;
	var alloldvalueobj;
	var checkboxalloldvalueobj;
	var hiddenoptionallobj;
	var oldkeyobj;
	var checkboxalloldfullurlobj;
	var optionobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allkey"){
			checkboxalloldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_allvalue"){
			checkboxalloldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldqueryvalue"){
			oldvalueobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
			hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_oldquerykey"){
			oldkeyobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_checked_fullurl"){
			checkboxalloldfullurlobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
			optionobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	var invalue=textobj.value;

	var novalue="";
	var nokey="";
	var beingvalue="";
	var beingkey="";

	var isokallkey="";
	var isokallvalue="";
	var isokfullurl="";
	var nookallkey="";
	var nookallvalue="";
	var nookfullurl="";
	var invalues=invalue.split(",");
	var inputkeys=keyobj.value.split(",");
	var checkboxallkeys=checkboxalloldkeyobj.value.split(",");
	var checkboxallvalues=checkboxalloldvalueobj.value.split(",");
	var checkboxfullurls=checkboxalloldfullurlobj.value.split(",");
	var allvalues=checkboxalloldvalueobj.value.split(",");

	var tree1=null;

	if(optionobj.style.display=="none"){
		tree1=getObjByMainDivId(id+"_hidden_option_all");
	}
	if(tree1==null)
		tree1=getObjByMainDivId(id+"_option");
	var arr;
	if(tree1!=undefined)
		arr=tree1.getAllNodeAttribute();
	for(var j=0;j<invalues.length;j++){
		isall="false";
		var isbeing="false";
		if(optionobj.innerText!="未找到相关信息"){
			if(tree1!=undefined){
				for(var i=0;i<arr.length;i++){
					if(invalues[j]==arr[i][7]||invalues[j]==arr[i][10]){
						isall="true";//有完全匹配记录
					}
				}
			}
		}
		for(var i=0;i<allvalues.length;i++){
			if(allvalues[i]==invalues[j]&&checkboxallkeys[j]!=""){
				isall="true";//有完全匹配记录
			}
		}

		if(invalues[j]!=""){
			if(isall=="false"){
				if(nokey==""){
					novalue=invalues[j]+",";
					nokey=" ,";
				}else{
					novalue=novalue+invalues[j]+",";
					nokey=nokey+" ,";
				}

				isbeing="false";
				for(var n=0;n<checkboxallkeys.length;n++){
					if(checkboxallkeys[n]==""||checkboxallkeys[n]==" "){
						if(invalues[j]==checkboxallvalues[n])
							isbeing="true";//如果记录的字段里面有该内容
					}else{
						if(inputkeys[j]==checkboxallkeys[n])
							isbeing="true";//如果记录的字段里面有该内容
					}
				}

				if(isbeing=="false"){
					if(isokallkey==""){
						isokallkey=" ,";
						isokallvalue=invalues[j]+",";
						isokfullurl=invalues[j]+",";
					}else{
						isokallkey=isokallkey+" ,";
						isokallvalue=isokallvalue+invalues[j]+",";
						isokfullurl=isokfullurl+invalues[j]+",";
					}
				}
			}else{
				var newevenkey=" ";
				var newevenfullurl=" ";
				if(tree1!=undefined){
					if(inputkeys[j]==""){
						for(var i=0;i<arr.length;i++){
							if(invalues[j]==arr[i][7]||invalues[j]==arr[i][10]){
								newevenkey=arr[i][1];
								newevenfullurl=arr[i][3];
							}
						}
					}else{
						for(var i=0;i<arr.length;i++){
							if(invalues[j]==arr[i][7]||invalues[j]==arr[i][10]){
								newevenfullurl=arr[i][3];
							}
						}
					}
				}
				if(beingkey==""){
					beingvalue=invalues[j]+",";//匹配到的内容
					if(inputkeys[j]==""){
						beingkey=newevenkey+",";//匹配的了的key
					}else
						beingkey=inputkeys[j]+",";//匹配的了的key
				}else{
					beingvalue=beingvalue+invalues[j]+",";//匹配到的内容
					if(inputkeys[j]==""){
						beingkey=beingkey+newevenkey+",";//匹配的了的key
					}else
						beingkey=beingkey+inputkeys[j]+",";//匹配的了的key

				}

				isbeing="false";
				for(var n=0;n<checkboxallkeys.length;n++){
					if(checkboxallkeys[n]==""||checkboxallkeys[n]==" "){
						if(invalues[j]==checkboxallvalues[n])
							isbeing="true";//如果记录的字段里面有该内容
					}else{
						if(inputkeys[j]==checkboxallkeys[n])
							isbeing="true";//如果记录的字段里面有该内容
					}
				}

				if(isbeing=="false"){
					if(isokallkey==""){
						if(inputkeys[j]=="")
							isokallkey=newevenkey+",";
						else
							isokallkey=inputkeys[j]+",";
						isokallvalue=invalues[j]+",";
						isokfullurl=newevenfullurl+",";
					}else{
						if(inputkeys[j]=="")
							isokallkey=isokallkey+newevenkey+",";
						else
							isokallkey=isokallkey+inputkeys[j]+",";
						isokfullurl=isokfullurl+newevenfullurl+",";
						isokallvalue=isokallvalue+invalues[j]+",";
					}
				}

			}

		}
	}
	var isAddNewData=hiddenobj.isAddNewData;
	if(novalue!=""){
		var shownovalue=novalue;
		if(novalue.lastIndexOf(",")==novalue.length-1){
			shownovalue=novalue.substring(0,novalue.lastIndexOf(","));
		}
		if(isAddNewData=="true"){
			if(confirm("该选择项中没有  “"+shownovalue+"”， 如需录入按[确定]，否则按[取消]")){
				textobj.blur;
				//keyobj.value=beingkey+nokey;
				textobj.value=beingvalue+novalue;
				checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+isokallkey;
				checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+isokallvalue;
				checkboxalloldfullurlobj.value=checkboxalloldfullurlobj.value+isokfullurl;
				keyobj.value=keyobj.value+nokey;
				_gzzzb_Onchange(id);
			}else{
				textobj.value=beingvalue;
				keyobj.value=beingkey;
				textobj.focus();
				checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+nookallkey;
				checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+nookallvalue;
				checkboxalloldfullurlobj.value=checkboxalloldfullurlobj.value+nookfullurl;
				_gzzzb_Onchange(id);
			}
		}else{
			if(confirm("该选择项中没有  “"+shownovalue+"”，如需删除按[确定]，修改按[取消]")){
				textobj.focus();
				textobj.value=beingvalue;
				keyobj.value=beingkey;
				checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+nookallkey;
				checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+nookallvalue;
				checkboxalloldfullurlobj.value=checkboxalloldfullurlobj.value+nookfullurl;
				_gzzzb_Onchange(id);
			}else{
				textobj.focus();
				//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
				with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
				//================================
				_gzzzb_Onchange(id);
				textobj.click();
			}
		}
	}else{
		keyobj.value=beingkey+nokey;
		//if(textobj.value.lastIndexOf(",")!=textobj.value.length-1){
		//	textobj.value=textobj.value+",";
		//}
		if(checkboxalloldkeyobj.value==""){
			checkboxalloldkeyobj.value=keyobj.value;
			checkboxalloldvalueobj.value=textobj.value;
			checkboxalloldfullurlobj.value=isokfullurl;
		}else{
			checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+isokallkey;
			checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+isokallvalue;
			checkboxalloldfullurlobj.value=checkboxalloldfullurlobj.value+isokfullurl;
		}
		_gzzzb_Onchange(id);
	}


	if(textobj.value==""&&keyobj.value!=""){
		keyobj.value="";
	}

	if(textobj.value!=""){
		if(textobj.value.substring(textobj.value.length-1,textobj.value.length)==",")
			textobj.value=textobj.value.substring(0,textobj.value.lastIndexOf(","));
	}

	/*if(textobj.value!=""){
	 if(textobj.value.substring(textobj.value.length-1,textobj.value.length)!=",")
	 textobj.value=textobj.value+",";
	 }*/
	/*textobj.blur;
	 textobj.value=beingvalue+novalue;
	 checkboxalloldkeyobj.value=checkboxalloldkeyobj.value+isokallkey;
	 checkboxalloldvalueobj.value=checkboxalloldvalueobj.value+isokallvalue;



	 if(textobj.value!=""){
	 if(textobj.value.substring(textobj.value.length-1,textobj.value.length)!=",")
	 textobj.value=textobj.value+",";
	 }*/
	if(textobj.value==""&&keyobj.value!=""){
		keyobj.value="";
	}

	oldvalueobj.value=textobj.value;
	oldkeyobj.value=keyobj.value;

	transferUserFunc(id);
}

//复选点击内容的时候的事件
function selected_values_checkbox(obj,id){
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	// 在全局变量中找到当前发生事件的下拉框对应的FORM控件对象
	for(var idnum=0; idnum<Gzzzb_SelectOption_ids.length; idnum++){
		if(Gzzzb_SelectOption_ids[idnum] == id){ // 是存储当前编码值的控件ID。
			keyobj = Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum] == id+"_text"){ // 是存储当前文本值的控件ID
			textobj = Gzzzb_SelectOption_objs[idnum];
		}
	}

	var queryvalue=textobj.value;
	var queryItem=queryvalue.split(",");
	var queryinfo="";
	if(queryvalue.length>0){
		if(queryItem.length>1)
			queryinfo=queryItem[queryItem.length-1];
		else
			queryinfo=queryvalue;
	}

	_gzzzb_selectOption_hidden_Select_DIV(id,'2');
	//用于判断显示的是记录全部的层还是记录查询数据的层
	var tree1=getObjByMainDivId(id+"_option");
	if(queryinfo=="")
		tree1=getObjByMainDivId(id+"_hidden_option_all");

	isfunction="0";
	//取得节点展开状态
	var nodestatue=tree1.getNodeOpenStatus(tree1.getCurrentNodeObj())
	if(nodestatue==true){
		_gzzzb_selectOption_hidden_Select_DIV(id,'1');
		var arr=tree1.getNodeAllInfos(tree1.getCurrentNodeObj());
		var fullname=tree1.getNodeFullName(tree1.getCurrentNodeObj());
		if(obj.previousSibling.checked==true)
			obj.previousSibling.checked=false;
		else
			obj.previousSibling.checked=true;
		treechecked(obj.previousSibling,id); //点击内容的时候选中该项的复选框 并调用选中的事件
	}else{
		window.setTimeout("divchangesize('" + id + "')", 100);
	}

}
//复选旧数据点击内容的时候的事件
function old_selected_values_checkbox(obj,id){
	_gzzzb_selectOption_hidden_Select_DIV(id,'2');
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}

	//var keyobj=document.all(id);//现在在做选择的记录key保存的对象
	//var textobj=document.all(id+"_text");
	var textvalues=textobj.value.split(",");
	var keyvalues=keyobj.value.split(",");
	var newtextvalue="";
	var newkeyvalue="";

	if(obj.checked==true){
		newtextvalue=obj.value+","+textobj.value;
		newkeyvalue=obj.key+","+keyobj.value;
	}else{
		for(var i=0;i<keyvalues.length;i++){
			var isbeing=false;
			if(keyvalues[i]!=""){
				if(keyvalues[i]==""||keyvalues[i]==" "){
					if(textvalues[i]!=obj.value){
						isbeing=true;
					}
				}else{
					if(keyvalues[i]!=obj.key){
						isbeing=true;
					}
				}
				if(isbeing==true){
					newkeyvalue=keyvalues[i]+","+newkeyvalue;
					newtextvalue=textvalues[i]+","+newtextvalue;
				}
			}
		}

	}
	keyobj.value=newkeyvalue;
	textobj.value=newtextvalue;

	//_gzzzb_selectOption_hidden_Select_DIV_checkbox(id,'1');
}
//点击图片触发的事件
function _gzzzb_selectOption_hidden_Select_checkbox_img(id){
	var divmainobj;
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(divmainobj.style.display=="none"){
		textobj.focus();
		window.clearTimeout(timeid);
		checkboxqueryinfo_onclick(id,'2','2');
	}else{
		divmainobj.style.display="none";
	}
	//if(divmainobj.style.display=="block")
	//	divmainobj.focus();
}
//图片隐藏显示层的方法
function _gzzzb_selectOption_hidden_Select_img(id){
	var divmainobj;
	var textobj;
	var keyobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
			divmainobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(textobj.value==""){
		keyobj.value="";
	}
	if(divmainobj.style.display=="none"){
		textobj.focus();
		window.clearTimeout(timeid);
		//queryinfo_onclick(id,'1','2');
		_gzzzb_loadData_Img(id);
		//_gzzzb_getChileTree(id);
		//divmainobj.style.display="block";
	}else{
		_gzzzb_getKeys(id);
		divmainobj.style.display="none";
	}

	//if(divmainobj.style.display=="block")
	//	divmainobj.focus();
}
//解决是按TAB让输入框得到焦点的问题 单选
function _gzzzb_loadData_Img(id){
	try{
		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var divmainobj;
		var oldoptionobj;
		var optionobj;
		var optionallobj;
		var queryvalueobj;
		var hiddenoptionallobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_main"){
				divmainobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option_old"){
				oldoptionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_option"){
				optionobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				optionallobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_queryValue"){
				queryvalueobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_hidden_option_all"){
				hiddenoptionallobj=Gzzzb_SelectOption_objs[idnum];
			}
		}
		//取得用户设置的参数
		var dataSource=hiddenobj.getAttribute("dataSource");
		var contextPath=hiddenobj.getAttribute("contextPath");
		var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
		var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		var isDynamicLoadDataSource=hiddenobj.isDynamicLoadDataSource;
		var allowClear=hiddenobj.allowClear;

		var maxheight=hiddenobj.divmaxheight;//定义的div的最大高度
		isfunction="0";

		/*if(textobj.value==queryvalueobj.value){
		 return;
		 }*/
		var matchingsetup=hiddenobj.getAttribute("matchingsetup");
		var fontColor=hiddenobj.getAttribute("fontColor");
		var fontSize=hiddenobj.getAttribute("fontSize");
		var equalcolor=hiddenobj.getAttribute("equalcolor");
		var key=keyobj.value;
		if(isDynamicLoadDataSource=="true"){
			var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?allowClear="+allowClear+"&code="+key+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&type=1&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
				if(reqText!=null && reqText!=""){
					hiddenoptionallobj.innerHTML=reqText;
					divmainobj.style.display="block";
					hiddenoptionallobj.style.display="block";
					optionobj.style.display="none";

					divchangesize_all(id);

					_gzzzb_getChileTree(id);
				}
				//showquertdata(id,reqText,1);
			});
			b.doGet();
		}else{
			if(hiddenoptionallobj.innerHTML==""){
				var b = new AJAXInteraction(contextPath+"/GetSelectQueryDataAjaxServlet.san?allowClear="+allowClear+"&code="+key+"&userParameter="+userParameter+"&dictionaryType="+dictionaryType+"&querytype=2&id="+id+"&matchingsetup="+matchingsetup+"&type=1&isshowtree="+hiddenobj.isshowtree+"&dataSource="+dataSource+"&fontColor="+fontColor+"&fontSize="+fontSize+"&equalcolor="+equalcolor, function(reqText) {
					if(reqText!=null && reqText!=""){
						hiddenoptionallobj.innerHTML=reqText;
						_gzzzb_getChileTree(id);
					}
				});
				b.doGet();
			}
			divmainobj.style.display="block";
			hiddenoptionallobj.style.display="block";
			optionobj.style.display="none";

			divchangesize_all(id);
		}

	}catch(e){
		alert("_gzzzb_loadData_onfocus::" + e.description);
	}
}
function _gzzzb_selectOption_openChoose(id,rightQueryInfo,num){
	try{

		var hiddenobj;
		var keyobj;//现在在做选择的记录key保存的对象
		var textobj;
		var isOpenState;//是否打开了
		var isOpenStateIndex;//是否打开了
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}

			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_isOpenState"){
				isOpenState=Gzzzb_SelectOption_objs[idnum];
				isOpenStateIndex = idnum;
			}
		}

		if(textobj.value!=""){
			//=======用于控制得到焦点改变文本框内容后把光标移到到最后的方法
			//with(document.selection.createRange()){moveStart("character",textobj.value.length);collapse();select();}
			//================================
		}
		//得到树需要的相关设置
		var dataSource=hiddenobj.getAttribute("dataSource");
		var staticdata=hiddenobj.getAttribute("staticdata");
		//var contextPath=hiddenobj.getAttribute("contextPath");
		var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
		var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var matchingsetup=hiddenobj.getAttribute("matchingsetup");
		var fontColor=hiddenobj.getAttribute("fontColor");
		var fontSize=hiddenobj.getAttribute("fontSize");
		var equalcolor=hiddenobj.getAttribute("equalcolor");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		var selectvaluetype=hiddenobj.getAttribute("selectvaluetype");
		var optionType=hiddenobj.getAttribute("optionType");
		var onchageEvent=hiddenobj.onchange;
		var dynamicAttri=hiddenobj.getAttribute("dynamicAttri");
		var re = /，/g;
		textobj.value=textobj.value.replace(re,",");
		var keys=keyobj.value;
		var textvalue=textobj.value;
		var textvalues=textvalue.split(",");
		var queryinfo=textvalue;
		var chooseType=hiddenobj.getAttribute("chooseType");
		var useType=hiddenobj.getAttribute("useType");

		var parentSelectedWithChild=hiddenobj.getAttribute("parentSelectedWithChild");
		var parentClearWithChild=hiddenobj.getAttribute("parentClearWithChild");
		var lowerSelectedAllWithSuperior=hiddenobj.getAttribute("lowerSelectedAllWithSuperior");
		var lowerClearWithSuperior=hiddenobj.getAttribute("lowerClearWithSuperior");
		var lowerClearAllWithSuperior=hiddenobj.getAttribute("lowerClearAllWithSuperior");
		var token=hiddenobj.getAttribute("token");
		//if(isOpenState==1){
		//	return ;
		//}

		if(radioOrCheckbox=="checkbox"&&num=="2"&&rightQueryInfo==""){
			if(textvalue.length>0){
				if(textvalue.length>1)
					queryinfo=textvalues[textvalues.length-1];
				else
					queryinfo=textvalue;
			}
		}
		//queryinfo=encodeURIComponent(queryinfo);
		if(dictionaryType!="" && dictionaryType != null){
			if(userParameter!=""){
				userParameter = userParameter+";DictionaryType:" + dictionaryType; //加载 字典类别
			}else{
				userParameter = "DictionaryType:" + dictionaryType; // unitCode;  // 加载 字典类别
			}
		}
		if(keys!=""){
			if(keys.substring(keys.length-1,keys.length) == ","){
				keys=keys.substring(0,parseInt(keys.length)-1);
			}
		}

		if(chooseType =="1" || chooseType =="2"){
			if(keys!=""){
				userParameter = userParameter+";"; //加载 字典类别
			}
		}

		//if(rightQueryInfo=="")
		//	rightQueryInfo=queryinfo;
		var array=new Array();

		/*var editArray=new Array();
		 editArray[0]=keys;
		 editArray[1]=queryinfo;
		 editArray[2]=dynamicAttri;*/
		if(keyobj.value=="" && textobj.value!=""){
			rightQueryInfo = textobj.value;
			queryinfo ="";
		}
		//var myObject = new Object();
		//myObject.keys=keys;
		//myObject.queryinfo=queryinfo;
		//myObject.dynamicAttri=dynamicAttri;
		//var dm="dialogHeight:550px;dialogWidth:700px;center:yes;resizable:no;status:no;";
		Gzzzb_SelectOption_objs[isOpenStateIndex] = 1;
		//array=window.showModalDialog(contextPath+"/selectQueryTreeInfo.jsp?rightQueryInfo="+rightQueryInfo+"&staticdata="+staticdata+"&selectvaluetype="+selectvaluetype+"&userParameter="+userParameter+"&querytype=1&id="+id+"&matchingsetup="+matchingsetup+"&contextPath="+contextPath+"&parentChildIsolate="+parentChildIsolate+"&parentCheckboxEnable="+parentCheckboxEnable+"&parentRadioEnable="+parentRadioEnable+"&radioOrCheckBox="+radioOrCheckbox+"&checkboxDisplay="+checkboxDisplay+"&divId="+id+"_hidden_option_all&dataSource="+dataSource+"&chooseType="+chooseType+"&useType="+useType+"&parentSelectedWithChild="+parentSelectedWithChild+"&parentClearWithChild="+parentClearWithChild+"&lowerSelectedAllWithSuperior="+lowerSelectedAllWithSuperior+"&lowerClearWithSuperior="+lowerClearWithSuperior+"&lowerClearAllWithSuperior="+lowerClearAllWithSuperior,myObject,dm);
		$.ajax({
			async:false,
			url : contextPath+"/sys/selectOption/ajax/loadSelectQueryTreeInfo",
			type : "post",
			data: {
				"keys":keys,
				"queryinfo":queryinfo,
				"dynamicAttri":dynamicAttri,
				"rightQueryInfo":rightQueryInfo,
				"staticdata":staticdata,
				"selectvaluetype":selectvaluetype,
				"userParameter":userParameter,
				"querytype":'1',
				"id":id,
				"matchingsetup":matchingsetup,
				"contextPath":contextPath,
				"parentChildIsolate":parentChildIsolate,
				"parentCheckboxEnable":parentCheckboxEnable,
				"parentRadioEnable":parentRadioEnable,
				"radioOrCheckBox":radioOrCheckbox,
				"checkboxDisplay":checkboxDisplay,
				"divId":id+'hidden_option_all',
				"dataSource":dataSource,
				"chooseType":chooseType,
				"useType":useType,
				"parentSelectedWithChild":parentSelectedWithChild,
				"parentClearWithChild":parentClearWithChild,
				"lowerSelectedAllWithSuperior":lowerSelectedAllWithSuperior,
				"lowerClearWithSuperior":lowerClearWithSuperior,
				"lowerClearAllWithSuperior":lowerClearAllWithSuperior

			},
			headers:{
				OWASP_CSRFTOKEN:token
			},
			dataType : "html",
			success : function(html){
				if(keyobj.value=="" && textobj.value!=""){
					textobj.value="";
				}
				$('#selectOptionDiv').html(html);
				$('#selectOptionModal').modal({
					keyboard: true
				});
			},
			error : function(){
				showTip("提示","出错了请联系管理员", 1500);
			}
		});

		//Gzzzb_SelectOption_objs[isOpenStateIndex] = 0;
		//if(array!=null){
		//	var newValue=array[1];
		//	if(array[1]!=""){
		//		if(array[1].substring(array[1].length-1,array[1].length) == ","){
		//			newValue=array[1].substring(0,parseInt(array[1].length)-1);
		//		}
		//	}
		//	var newKey = array[0];
		//	if(array[0]!=""){
		//		if(array[0].substring(array[0].length-1,array[0].length) == ","){
		//			newKey=array[0].substring(0,parseInt(array[0].length)-1);
		//		}
		//	}
		//	var isEvalChange=false;
		//	if(newValue!=textobj.value){
		//		isEvalChange=true;
		//	}
		//	if(newKey != keyobj.value){
		//		isEvalChange=true;
		//	}
		//	keyobj.value=newKey;
		//	textobj.value=newValue;
        //
		//	hiddenobj.setAttribute("dynamicAttri", array[2]);
		//	if(newValue!=""){
		//		textobj.readOnly=true;
		//		textobj.blur();
		//	}else{
		//		textobj.readOnly=false;
		//		textobj.focus();
		//	}
		//	if(isEvalChange==true){
		//		if(optionType=="tag"){//判断是不是tag的下拉  是的则输出新
		//			_gzzzb_write_dealWithUploadData(id);
		//		}
		//		if(onchageEvent!=null&&onchageEvent!="")
		//			eval(onchageEvent);
		//	}
		//	isopen=false;
		//}else{
		//	if(keyobj.value=="" && textobj.value!=""){
		//		textobj.value="";
		//		textobj.readOnly=false;
		//	}
		//	textobj.blur();
		//}
		//if(keyobj.value=="" && textobj.value==""){
		//	textobj.readOnly=false;
		//}
	}catch(e){
		alert("_gzzzb_selectOption_openChoose:"+e.description);
	}
}

function _gzzzb_saveValue(array,id){
	//Gzzzb_SelectOption_objs[isOpenStateIndex] = 0;
	var hiddenobj;
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}

	}
	var newValue=array[1];
	if(array[1]!=""){
		if(array[1].substring(array[1].length-1,array[1].length) == ","){
			newValue=array[1].substring(0,parseInt(array[1].length)-1);
		}
	}
	var newKey = array[0];
	if(array[0]!=""){
		if(array[0].substring(array[0].length-1,array[0].length) == ","){
			newKey=array[0].substring(0,parseInt(array[0].length)-1);
		}
	}
	var isEvalChange=false;
	if(newValue!=textobj.value){
		isEvalChange=true;
	}
	if(newKey != keyobj.value){
		isEvalChange=true;
	}
	keyobj.value=newKey;
	textobj.value=newValue;

	hiddenobj.setAttribute("dynamicAttri", array[2]);
	if(newValue!=""){
		textobj.readOnly=true;
		textobj.blur();
	}else{
		textobj.readOnly=false;
		textobj.focus();
	}
	var optionType=hiddenobj.getAttribute("optionType");
	var onchageEvent=hiddenobj.onchange;
	if(isEvalChange==true){
		if(optionType=="tag"){//判断是不是tag的下拉  是的则输出新
			_gzzzb_write_dealWithUploadData(id);
		}
		if(onchageEvent!=null&&onchageEvent!="")
			eval(onchageEvent);
	}
	//isopen=false;

	if(keyobj.value=="" && textobj.value==""){
		textobj.readOnly=false;
	}
}
//用于处理点击文本框是不是触发跳出查询的页面
function _gzzzb_selectOption_openChoose_text(id){
	var textobj;
	var keyobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	if(textobj.value!=""&&textobj.readOnly==true){
		_gzzzb_selectOption_openChoose(id,"",'1');
	}
}

function _gzzzb_selectOption_openChoose_onBlur(id){
	try{
		var textobj;
		var keyobj;
		var hiddenobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id){
				keyobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
		}
		//取得用户设置的参数
		var dataSource=hiddenobj.getAttribute("dataSource");
		var contextPath=hiddenobj.getAttribute("contextPath");
		var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
		var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		var optionType=hiddenobj.getAttribute("optionType");
		var onchageEvent=hiddenobj.onchange;
		var matchingsetup=hiddenobj.getAttribute("matchingsetup");
		var staticdata=hiddenobj.getAttribute("staticdata");
		var token=hiddenobj.getAttribute("token");
		var re = /，/g;//把全角的，转为半角的,的正则表达式
		textobj.value=textobj.value.replace(re,",");

		textvalue=textobj.value;
		if(textvalue!=""&&keyobj.value==""){
			var chooseType="3";
			if(radioOrCheckbox=="checkbox")
				chooseType="4";

			$.ajax({
				async:false,
				url : contextPath+"/sys/selectOption/ajax/selectQueryData",
				type : "post",
				data: {
					"type":chooseType,
					"userParameter":userParameter,
					"dictionaryType":dictionaryType,
					"querytype":1,
					"id":id,
					"matchingsetup":matchingsetup,
					"unitCode":textvalue,
					"isshowtree":hiddenobj.isshowtree,
					"contextPath":contextPath,
					"parentChildIsolate":parentChildIsolate,
					"parentCheckboxEnable":parentCheckboxEnable,
					"parentRadioEnable":parentRadioEnable,
					"radioOrCheckBox":radioOrCheckbox,
					"checkboxDisplay":checkboxDisplay,
					"divId":id+'_hidden_option_all',
					"dataSource":dataSource
				},
				headers:{
					OWASP_CSRFTOKEN:token
				},
				dataType : "json",
				success:function(json){
					var reqText = json.resultsHtml;
					if(reqText!=null && reqText!=""){
						if(radioOrCheckbox=="radio"){//单选 判断是否有完全匹配的内容
							if(reqText.indexOf("gzzzb_returnCodes###")!=-1){
								var keys=reqText.replace("gzzzb_returnCodes###","");
								keyobj.value=keys;
								textobj.blur();
								textobj.readOnly=true;


								//alert("select_option's changed 1!");


								//if(isEvalChange==true){
								if(optionType=="tag"){//判断是不是tag的下拉  是的则输出新
									_gzzzb_write_dealWithUploadData(id);
								}
								if(onchageEvent!=null&&onchageEvent!="")
									eval(onchageEvent);
								//}
							}else{
								keyobj.value="";
								_gzzzb_selectOption_openChoose(id,"",'1');
							}
						}else{//复选 判断是否有完全匹配的内容
							if(reqText.indexOf("gzzzb_returnCodes###")!=-1){
								var keys=reqText.replace("gzzzb_returnCodes###","");
								var key=keys.split(",");
								var newKeys="";
								var newValues="";
								var isShow=true;//用于判断是否有没匹配的数据
								var queryName="";//取得需要查询的值  取第一个没匹配到的数据
								var oneNum=0;
								var value=textobj.value.split(",");
								for(var i=0;i<key.length;i++){
									if(key[i]!=" "&&key[i]!=""){
										if(newKeys==""){
											newKeys=key[i];
											newValues=value[i];
										}else{
											newKeys=newKeys+","+key[i];
											newValues=newValues+","+value[i];
										}
									}else{
										isShow=false;
										if(oneNum==0){
											queryName=value[i];
											oneNum=1;
										}
									}
								}
								keyobj.value=newKeys;
								textobj.value=newValues;
								if(isShow==false){
									_gzzzb_selectOption_openChoose(id,queryName,'2');
								}
								if(textobj.value!=""){
									textobj.readOnly=true;
								}
								if(isShow==true){
									if(optionType=="tag"){//判断是不是tag的下拉  是的则输出新
										_gzzzb_write_dealWithUploadData(id);
									}else{ // 弹出窗口中的数据变化

									}
									if(onchageEvent!=null&&onchageEvent!="")
										eval(onchageEvent);
								}
								//textobj.blur();
							}else{
								keyobj.value="";
								var value=textobj.value.split(",");
								textobj.value="";
								_gzzzb_selectOption_openChoose(id,value[0],'2');
							}
						}
					}else{
						keyobj.value="";
						var value=textobj.value.split(",");
						textobj.value="";
						_gzzzb_selectOption_openChoose(id,value[0],'2');
					}
				},
				error : function(){
					showTip("提示","出错了请联系管理员", 1500);
				}
			});
		}
	}catch(e){
		alert("onload:"+e.description);
	}
}
function _gzzzb_selectOption_openChoose_onkeydown(id){
	try{
		var hiddenobj;
		var textobj;
		//var keyobj;
		for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
			if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
				hiddenobj=Gzzzb_SelectOption_objs[idnum];
			}
			if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
				textobj=Gzzzb_SelectOption_objs[idnum];
			}
			//if(Gzzzb_SelectOption_ids[idnum]==id){
			//	keyobj=Gzzzb_SelectOption_objs[idnum];
			//}
		}
		//取得用户设置的参数
		var dataSource=hiddenobj.getAttribute("dataSource");
		var contextPath=hiddenobj.getAttribute("contextPath");
		var checkboxDisplay=hiddenobj.getAttribute("checkboxDisplay");
		var radioOrCheckbox=hiddenobj.getAttribute("radioOrCheckbox");
		var parentRadioEnable=hiddenobj.getAttribute("parentRadioEnable");
		var parentCheckboxEnable=hiddenobj.getAttribute("parentCheckboxEnable");
		var parentChildIsolate=hiddenobj.getAttribute("parentChildIsolate");
		var userParameter=hiddenobj.getAttribute("userParameter");
		var dictionaryType=hiddenobj.getAttribute("dictionaryType");
		var textvalue=textobj.value;
		var isopen=false;

		var textvalue=textobj.value;

		if(textvalue==""){
			if(event.keyCode == 13){//13为回车键值
				//如果是回车，转换为Tab键（可触发该对象的onChange事件)
				event.keyCode=9;//9为Tab键值
			}
		}else{
			if(event.keyCode == 13){//13为回车键值
				//如果是回车，转换为Tab键（可触发该对象的onChange事件)
				event.keyCode=9;//9为Tab键值
			}
			//textobj.blur();
		}

		//textobj.blur();
		/*if(radioOrCheckbox=="radio"){
		 var textvalue=textobj.value;
		 if(textvalue!=""){
		 _gzzzb_selectOption_openChoose(id,"",'1');
		 }
		 }else{
		 var textvalue=textobj.value;

		 if(textvalue!=""){
		 _gzzzb_selectOption_openChoose(id,"",'1');
		 }
		 }*/

	}catch(e){
		alert("onload:"+e.description);
	}

}

/*=======================  二次开发者使用的方法 ================================*/
//给控件赋值 和 key  累加新数据
function _gzzzb_setValue(id,values,keys){
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	keyobj.value=keyobj.value+keys;
	textobj.value=textobj.value+values;
	var onchageEvent=hiddenobj.onchange;
	var optionType=hiddenobj.getAttribute("optionType");
	if(optionType=="tag"){//判断是不是tag的下拉  是的则输出新
		_gzzzb_write_dealWithUploadData(id);
	}
	// 客户端代码调用，不运行总框架的onchage事件。
	if(onchageEvent!=null&&onchageEvent!="")
		eval(onchageEvent);

}
//赋新code和内容，客户端代码调用。
function _gzzzb_setNewValue(id,values,keys){
	var keyobj;//现在在做选择的记录key保存的对象
	var textobj;
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}

		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	keyobj.value=keys;
	textobj.value=values;
	if(textobj.readOnly==true&&textobj.value=="")
		textobj.readOnly=false;
	var onchageEvent=hiddenobj.onchange;
	var optionType=hiddenobj.getAttribute("optionType");

	if(optionType=="tag"){//判断是不是tag的下拉  是的则输出新
		_gzzzb_write_dealWithUploadData(id);
	}
	// 客户端代码调用，不运行总框架的onchage事件。
	if(onchageEvent!=null&&onchageEvent!="")
		eval(onchageEvent);
}

//改变文本框的样式
function _gzzzb_setStyle(id,style){
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	textobj.style.cssText=style;
}
function _gzzzb_setDynamicAttri(id,dynamicAttri){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("dynamicAttri", dynamicAttri);
}
//得到选中的内容的keys
function _gzzzb_getKeys(id){
	var keyobj;//现在在做选择的记录key保存的对象
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id){
			keyobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	return keyobj.value;
}
//得到选中的内容
function _gzzzb_getValues(id){
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	return textobj.value;
}

function _gzzzb_getDynamicAttri(id){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	return hiddenobj.getAttribute("dynamicAttri");
}

function _gzzzb_getTextObj(id){
	var textobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]==id+"_text"){
			textobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	return textobj;
}

function _gzzzb_setUserParameter(id,userParameter){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("userParameter", userParameter);
}

function _gzzzb_getUserParameter(id){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	return hiddenobj.getAttribute("userParameter");
}
function _gzzzb_setDictionaryType(id,dictionaryType){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("dictionaryType", dictionaryType);
}

function _gzzzb_setStaticdata(id,staticdata){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("staticdata", staticdata);
}

function _gzzzb_setUseType(id,useType){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("useType", useType);
}

function _gzzzb_setChooseType(id,chooseType){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("chooseType", chooseType);
}

function _gzzzb_setDataSource(id,dataSource){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("dataSource", dataSource);
}

function _gzzzb_setParentChildIsolate(id,parentChildIsolate){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("parentChildIsolate", parentChildIsolate);
}

function _gzzzb_setParentSelectedWithChild(id,parentSelectedWithChild){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("parentSelectedWithChild", parentSelectedWithChild);
}

function _gzzzb_setParentClearWithChild(id,parentClearWithChild){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("parentClearWithChild", parentClearWithChild);
}

function _gzzzb_setLowerSelectedAllWithSuperior(id,lowerSelectedAllWithSuperior){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("lowerSelectedAllWithSuperior", lowerSelectedAllWithSuperior);
}

function _gzzzb_setLowerClearWithSuperior(id,lowerClearWithSuperior){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("lowerClearWithSuperior", lowerClearWithSuperior);
}

function _gzzzb_setLowerClearAllWithSuperior(id,lowerClearAllWithSuperior){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.setAttribute("lowerClearAllWithSuperior", lowerClearAllWithSuperior);
}

function _gzzzb_setLowerSelectedWithSuperior(id,lowerSelectedWithSuperior){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	hiddenobj.lowerSelectedWithSuperior=lowerSelectedWithSuperior;
}

function Change_cursorPosition(id){
	alert();
}
function transferUserFunc(id){
	var hiddenobj;
	for(var idnum=0;idnum<Gzzzb_SelectOption_ids.length;idnum++){
		if(Gzzzb_SelectOption_ids[idnum]=="gzzzb_"+id+"_hiddenvalue"){
			hiddenobj=Gzzzb_SelectOption_objs[idnum];
		}
	}
	var userFunc=hiddenobj.myFunc;

	if(userFunc!=null){
		if(userFunc!=""){
			eval(userFunc);
		}
	}
}
//取得下拉菜单中动态属性值，动态属性值为name:value;name1:value1
function _gzzzb_getValueFromDynamicAttri(dynamicAttri,name){

	var attris = dynamicAttri.split(";");
	var value="";
	for(var i = 0 ;i<attris.length;i++){

		if(attris[i].indexOf(name)!=-1){

			value = attris[i].split(":")[1];
			break;
		}
	}
	return value;
}