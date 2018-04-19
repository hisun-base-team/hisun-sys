function MyLoading(imgPath,options){
	var defaultOptions = {
			zindex : 10050
	}
	var option = $.extend(defaultOptions, options);
	var htmlStr = '<div class="modal-backdrop fade in" style="z-index: '+ option.zindex +'!important;display: none;text-align: center;vertical-align: middle;">'
					+'<div style="display: table;height: 100%;width: 100%;z-index:' + option.zindex +'">'
					+ '<div style="display: table-cell;height: 100%;width: 100%;vertical-align: middle;z-index:' + option.zindex +'">'
					+ '<img alt="" src="'+ imgPath +'/images/templateImage/712.GIF" style="z-index:' + option.zindex +'"></div></div></div>';
	
	var jqObj;
	$('body').append(htmlStr);
	jqObj = $('body > div:last');
	this.show = function(){
		jqObj.show();
	}
	
	this.hide = function(){
		jqObj.hide()
	}
}