/**
 * jquery validate init
 * @returns
 */
EstValidate = function(formId){
	var  vld = $("#"+formId).validate({
		errorClass : "help-inline",
		validClass : "help-inline ok valid",
		showErrors : function(errorMap, errorList){
			var f = document.getElementById(formId);
			for(var i=0;i<errorList.length;i++){
				var ele = errorList[i].element;
				var errEle = $("#"+formId).find("#"+ele.name+"Err");
				var line = $("#"+formId).find("[id='" + ele.name + "Group']");
				line[0].className = "control-group error";
				if(errEle.length > 0){
					errEle[0].className = "help-inline";
					errEle[0].innerHTML = errorMap[ele.name];
				}else{
					$(ele.parentNode).append("<span id=\"" + ele.name + "Err\" for=\"" + ele.name + "\" class=\"help-inline\">" + errorMap[ele.name] + "</span>");
				}
			}
			var sucList = this.successList;
			for(var i=0;i<sucList.length;i++){
				var ele = sucList[i];
				var line = $("#"+formId).find("[id='" + ele.name + "Group']");
				line[0].className = "control-group success";
				var errEle = $("#"+formId).find("#"+ele.name+"Err");
				if(errEle.length > 0){
					errEle[0].className = "help-inline ok";
					errEle[0].innerHTML = "";
				}else{
					$(ele.parentNode).append("<span id=\"" + ele.name + "Err\" for=\"" + ele.name + "\" class=\"help-inline ok\"></span>");
				}
			}
		}
	});
	return vld;
}

EstValidate2 = function(formId){
	var  vld = $("#"+formId).validate({
		errorClass : "help-inline",
		validClass : "help-inline ok valid",
		showErrors : function(errorMap, errorList){
			var f = document.getElementById(formId);
			for(var i=0;i<errorList.length;i++){
				var ele = errorList[i].element;
				var errEle = $("#"+formId).find("#"+ele.name+"Err");
				var line = $("#"+formId).find("[id='" + ele.name + "Group']");
				line[0].className = "control-group error";
				if(errEle.length > 0){
					errEle[0].className = "help-inline";
					errEle[0].innerHTML = errorMap[ele.name];
				}else{
					var tmp = $(ele).parents(".controls");
					tmp.append("<span id=\"" + ele.name + "Err\" for=\"" + ele.name + "\" class=\"help-inline\">" + errorMap[ele.name] + "</span>");
				}
			}
			var sucList = this.successList;
			for(var i=0;i<sucList.length;i++){
				var ele = sucList[i];
				var line = $("#"+formId).find("[id='" + ele.name + "Group']");
				line[0].className = "control-group success";
				var errEle = $("#"+formId).find("#"+ele.name+"Err");
				if(errEle.length > 0){
					errEle[0].className = "help-inline ok";
					errEle[0].innerHTML = "";
				}else{
					var tmp = $(ele).parents(".controls");
					tmp.append("<span id=\"" + ele.name + "Err\" for=\"" + ele.name + "\" class=\"help-inline ok\"></span>");
				}
			}
		}
	});
	return vld;
}

/**
 * 工单服务专用
 * @param formId
 * @returns {*|jQuery}
 * @constructor
 */
AdminTicketValidate = function(formId){
	var  vld = $("#"+formId).validate({
		errorClass : "help-inline",
		validClass : "help-inline ok valid",
		showErrors : function(errorMap, errorList){
			var f = document.getElementById(formId);
			for(var i=0;i<errorList.length;i++){
				var ele = errorList[i].element;
				var name = ele.name;
				if(name == ""){
					name = ele.id;
				}
				var errEle = $("#"+formId).find("#"+name+"Error");
				var $errEle = $(errEle);
				if(errEle.length > 0){
					$errEle.text(errorMap[name]);
					$errEle.show();
					$(ele).addClass("bordeRed");
				}
			}
			var sucList = this.successList;
			for(var i=0;i<sucList.length;i++){
				var ele = sucList[i];
				var name = ele.name;
				if(name == ""){
					name = ele.id;
				}
				var errEle = $("#"+formId).find("#"+name+"Error");
				if(errEle.length > 0){
					errEle.css("display", "none");
					$(ele).removeClass("bordeRed");
				}
			}
		}
	});
	return vld;
}