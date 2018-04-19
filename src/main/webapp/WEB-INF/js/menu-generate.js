/*该JS为配合jsp/inc/top.jsp,jsp/inc/left.jsp文件，
根据资源管理中的配置生成头部系统菜单项，左侧功能模块导航栏*/

var resources;
var currentLocation = new Array();
var tempItem;//由于遍历资源时，第3级先于第2级遍历出结果，所以把当前第3级位置信息保存到该变量，等第2级位置信息放入currentLocation后通过该临时变量来push第3级


/**
 * 初始化头部菜单栏、左侧导航栏、正文面包屑导航栏
 * @param context 项目的web context
 * @param location 当前页面在资源管理中所处的位置
 */
function initMenu(context, location) {
	$.get(context + "/platform/resource/getMenu", function(data,status){
		if (status == "success") {
			var currentSystemId = location.length > 0 ? location[0] : "";//当前所在系统资源id，暂时固定，其实应该通过一组表明location的变量来获取
			var active = "";
			var href = "#";//保存href路径
			//因为获取到的data本身没有做层次处理，所以要分3次遍历，已确保高层次完全生成然后再生成子元素
			$.each(data.data, function(index, item) {//系统，上方菜单项
				if (item.resId == currentSystemId) {
					active = " active";
					//存放当前位置信息
					currentLocation.push(item);
				} else {
					active = "";
				}
				if (item.url != null && item.url.length > 0) {
					href = context + item.url;
				} else {
					href = "#";
				}
				if ($("#systemUl") != null) {
					$("#systemUl").append("<li class='systemLi" + active + "' value='" + item.resId + "'><a href='" + href + "'> " + item.resourceName + " <span class='selected'></span> </a></li>")
				}
			});
			var currentModuleId = location.length > 1 ? location[1] : "";
			var selectedHtml = "";//当前选中的样式html
			var arrowHtml = "";//模块菜单右侧可折叠箭头的样式html
			var open = "";//表示是否展开的样式
			
			if (currentLocation.length > 0) {
				var currentChildId = location.length > 2 ? location[2] : "";
				var leftMenuItems = currentLocation[0].children;
				$.each(leftMenuItems, function(index, item) {//左侧导航栏，模块
					if (item.isMenu == "true" || item.isMenu == true) {//isMenu字段控制是否显示在左侧导航栏
						var childUlHtml = "";
						var childActive = "";
						
						if (item.resId == currentModuleId) {
							active = " active";
							open = " open";
							selectedHtml = " <span class='selected '></span> "
							//存放当前位置信息
							currentLocation.push(item);
						} else {
							active = "";
							selectedHtml = "";
							open = "";
						}
						
						var lv2Items = item.children;
						$.each(lv2Items, function(_index, _item) {//再次遍历寻找模块的子菜单
							if (_item.isMenu == "true" || _item.isMenu == true) {//isMenu字段控制是否显示在左侧导航栏
								if (_item.resId == currentChildId) {
									childActive = " active";
									selectedHtml = " <span class='selected '></span> ";
									//临时保存当前位置信息
									tempItem = _item;
								} else {
									childActive = "";
									selectedHtml = "";
								}
								if (_item.url != null && _item.url.length > 0) {
									href = context + _item.url;
								} else {
									href = "#";
								}
								childUlHtml += "<li class='" + childActive + "'><a href='" + href + "'>" + _item.resourceName + "</a></li>" + selectedHtml;
							} else {
								if (_item.resId == currentChildId) {
									//临时保存当前位置信息
									tempItem = _item;
								}
							}
						});
						
						if (childUlHtml.length > 0) {//有子菜单
							childUlHtml = "<ul class='sub-menu'>" + childUlHtml + "</ul>";
							
							arrowHtml = "<span class='arrow" + open + "'></span>"
							
						}
						//存放当前位置信息
						if (tempItem != null) {
							currentLocation.push(tempItem);
							tempItem = null;
						}
						if (item.url != null && item.url.length > 0) {
							href = context + item.url;
						} else {
							href = "#";
						}
						if ($("#moduleUl") != null) {
							$("#moduleUl").append("<li class='" + active + open + "'><a href='" + href + "'> <i" +
									"class='icon-cogs'></i> <span class='title'>" + item.resourceName + "</span>" + 
									selectedHtml + arrowHtml +
							"</a>" + childUlHtml + "</li>");
						}
					} else {
						if (item.resId == currentModuleId) {//就算不现实在菜单也要查找出当前位置对应资源，以生成面包屑导航
							//存放当前位置信息
							currentLocation.push(item);
							var lv2Items = item.children;
							$.each(lv2Items, function(_index, _item) {//寻找下级菜单当前位置
								if (_item.resId == currentChildId) {
									//存放当前位置信息
									currentLocation.push(_item);
								}
							});
						}
					}
				});
			}
			
		}
	});
}


function generateTopMenu(context) {
	$.get(context + "/platform/resource/getMenu", function(data,status){
		if (status == "success") {
			$.each(data.data, function(index, item) {
				if (item.pId == "1") {
					$("#systemUl").append("<li class='active systemLi' value='" + item.id + "'><a href='#'> " + item.name + " <span class='selected'></span> </a></li>")
				}
				
			});
		}
	});
}

