$.extend({
	cloudAjax:function(para){
		var	zindex = 20000;
		var htmlStr = '<div class="modal-backdrop fade in" style="z-index: '+ zindex +'!important;display: none;text-align: center;vertical-align: middle;">'
						+'<div style="display: table;height: 100%;width: 100%;z-index:' + zindex +'">'
						+ '<div style="display: table-cell;height: 100%;width: 100%;vertical-align: middle;z-index:' + zindex +'">'
						+ '<img alt="" src="'+ para.path +'/images/templateImage/712.GIF" style="z-index:' + zindex +'"></div></div></div>';
		
		var jqObj;
		$('body').append(htmlStr);
		jqObj = $('body > div:last');
		$.ajax({
			async : para.async||true,
			url : para.url,
			type : para.type||'get',
			headers : para.headers||{},
			data :para.data||{},
			dataType : para.dataType||'json',
			beforeSend : function(XHR){
				jqObj.show();
				if($.isFunction(para.beforeSend)){
					para.beforeSend(XHR);
				}
			},
			success : function(data, textStatus){
				if($.isFunction(para.success)){
					para.success(data, textStatus);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				if($.isFunction(para.error)){
					para.error(XMLHttpRequest, textStatus, errorThrown);
				}
			},
			complete : function(XHR, TS){
				jqObj.hide();
				if($.isFunction(para.complete)){
					para.complete(XHR, TS);
				}
			}
		});
	}
});