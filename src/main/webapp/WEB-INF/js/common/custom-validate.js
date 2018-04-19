/**
 * jquery custom validate method 是否相等
 */
jQuery.validator.addMethod("equals", function(value, element, attrValue) {   
    if(element.value == document.getElementById(attrValue).value){
    	return true
    }else{
    	return false;
    }
}, "密码不一致");

/**
 * 邮箱验证，由于jquery自带的邮箱验证与后台的匹配模式不能一致，所以另外起一个邮箱验证
 */
jQuery.validator.addMethod("customEmail", function(value, element) {
	if(value==null || value==''){
		return this.optional(element) || true;//空不验证
	}
    var bool = /^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/i.test(value);
    return this.optional(element) || bool;
}, "邮箱格式不正确");

/**
 * 密码验证
 */
jQuery.validator.addMethod("customPassword", function(value, element) {
	if(value==null || value==''){
		return this.optional(element) || true;//空不验证
	}
	var normal = /[a-zA-Z0-9]/;
	var special = /[。~!@#$%\^\+\*&\\\/\?\|:\.<>{}()';="]/;
    var bool = ( normal.test(value) && special.test(value) );
    return this.optional(element) || bool;
}, "密码需要出现数字或字母并存在特殊字符");

/**
 * 国内手机号码
 */
jQuery.validator.addMethod("mobilePhone", function(value, element) {
	if(value==null || value==''){
		return this.optional(element) || true;//空不验证
	}
	//var normal = /^\d{11}$/;
	var normal = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    var bool = normal.test(value);
    return this.optional(element) || bool;
}, "请正确填写您的手机号码");

/**
 * 多选框
 */
jQuery.validator.addMethod("checkboxRequire", function(value, element, attrValue) {
	var checkArrs = document.getElementsByName(attrValue);
	if(checkArrs.length==0){
		return false;
	}
	var bool = false;
	for(var i=0;i<checkArrs.length;i++){
		if(checkArrs[i].checked){
			bool = true;
			break;
		}
	}
	return bool;
}, "请选择至少一个选项");

/**
 * 文件大小限制
 */
jQuery.validator.addMethod("fileSizeLimit", function(value, element, attrValue) {
	if(element.files.length==0){
		return true;
	}
    var byteSize  = element.files[0].size;
 	var size = Math.ceil(byteSize / 1024/1024); // Size in MB.
	var bool = false;
	if(size <= attrValue){
		return true;
	}else{
		return false;
	}
}, "文件最大不超过{0}MB");

/**
 * 文件类型限制
 */
jQuery.validator.addMethod("fileType", function(value, element, attrValue) {
	if(element.files.length==0){
		return true;
	}
	var name  = element.files[0].name;
	var nameSplit = name.split("\.");
	var types = attrValue.split(",");
	var bool = false;
	var prefix = nameSplit[nameSplit.length-1];
	for(var i=0;i<types.length;i++){
		if(types[i] == prefix){
			bool = true;
			break;
		}
	}
	return bool;
}, "文件类型只能是{0}");

/**
 * IP地址验证
 */
jQuery.validator.addMethod("ipAddress", function(value, element) {
	if(value==null || value==''){
		return this.optional(element) || true;//空不验证
	}
	var bool = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/i.test(value);
	return this.optional(element) || bool;
}, "IP格式错误");

/**
 * 定时器书写格式的校验
 */
jQuery.validator.addMethod("timingCron", function(value, element, attrValue) {
	if(value==null || value==''){
		return this.optional(element) || true;//空不验证
	}
	var bool = cronValidate(value);
    return this.optional(element) || bool;
}, "定时器格式书写不正确！");


/**
 * 时间间隔校验
 */
jQuery.validator.addMethod("intervalValue", function(value, element, attrValue) {
	if(value==null || value==''){
		return this.optional(element) || true;//空不验证
	}
	if($("#intervalUnit").val()=="HOUR"){
		return value >= 0.1;
	}else if($("#intervalUnit").val()=="MINUTE"){
		return value >= 30;
	}
	
    return this.optional(element);
}, "间隔时间不能小于30分钟！");

/**
 * 验证ZTREE选则不能为空
 */
jQuery.validator.addMethod("treeSelRequired", function(value, element, attrValue) {
	var treeObj = window[attrValue];
	if(!treeObj){
		return false;
	}
	var selNodes = treeObj.getCheckedNodes();
	if(selNodes.length==0){
		return false;
	}else{
		return true;
	}
}, "不能为空，请选择数据");

/**
 * 验证ZTREE选则不能为空
 */
jQuery.validator.addMethod("rolePattern", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var reg = /^ROLE_[A-Z]{1,27}$/;
	var r = value.match(reg);
	if(r == null){
		return false;
	}else{
		return true;
	}
}, "以ROLE_开头，并且只允许大写字母,如：ROLE_ADMINTRATOR");

/**
 * 注册用户名验证
 */
jQuery.validator.addMethod("usernamePattern", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var reg = /^[0-9a-zA-z]{4,30}$/;
	var r = value.match(reg);
	if(r == null){
		return false;
	}else{
		return true;
	}
}, "5-30个数字或英文字母");

/**
 * 密码验证
 */
jQuery.validator.addMethod("passwordPattern", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var reg = /^.*[A-Za-z0-9\\w_-]+.*$/;
	var r = value.match(reg);
	if(r == null){
		return false;
	}else{
		return true;
	}
}, "由英文、数字及标点符号组成");

/**
 * 邮箱唯一性
 */
jQuery.validator.addMethod("tenantEmailUnique", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var result = false;
	$.ajax({
		url : window.PATH + "/sys/tenant/user/un/checkEmail",
		type : "post",
		data : {"email": value},
		dataType : "json",
		async : false,
		cache : false,
		headers: {
			"OWASP_CSRFTOKEN": $(element).attr("csrftoken")
		},
		success : function(json){
			if(json.code == 1){
				result =  true;
			}else if(json.code == -1){
				if(window.showTip){
					showTip("提示",json.message,null);
				}
				result =  false;
			}else if(json.code == -2){
				result =   false;
			}
		},
		error : function(arg1, arg2, arg3){
			showTip("","请求错误");
		}
	});
	return result;
}, "邮箱已被注册");

/**
 * 用户名唯一性
 */
jQuery.validator.addMethod("tenantUsernameUnique", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var result = false;
	$.ajax({
		url : window.PATH + "/sys/tenant/user/un/checkUserName",
		type : "post",
		data : {"username": value},
		dataType : "json",
		async : false,
		cache : false,
		headers: {
			"OWASP_CSRFTOKEN": $(element).attr("csrftoken")
		},
		success : function(json){
			if(json.code == 1){
				result =  true;
			}else if(json.code == -1){
				if(window.showTip){
					showTip("提示",json.message, null);
				}
				result =  false;
			}else if(json.code == -2){
				result =  false;
			}
		},
		error : function(arg1, arg2, arg3){
			showTip("","请求错误");
		}
	});
	return result;
}, "用户名已被注册");

/**
 * 用户名唯一性
 */
jQuery.validator.addMethod("pwdStrengh", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var pattern_1 = /^.*([\W_])+.*$/i;
	var pattern_2 = /^.*([a-zA-Z])+.*$/i;
	var pattern_3 = /^.*([0-9])+.*$/i;
	var level = 0;
	if (value.length > 10) {
		level++;
	}
	if (pattern_1.test(value)) {
		level++;
	}
	if (pattern_2.test(value)) {
		level++;
	}
	if (pattern_3.test(value)) {
		level++;
	}
	if (level > 3) {
		level = 3;
	}
	if(level < 2){
		return false;
	}else{
		return true;
	}
}, "密码强度太弱");

/**
 * IP唯一性
 */
jQuery.validator.addMethod("ipUnique", function(value, element, attrValue) {
    if($.trim(value) == ""){
        return false;
    }
    var result = false;
    $.ajax({
        url : window.PATH + "/sacm/host/ci/checkIp",
        type : "post",
        data : {"ip": value},
        dataType : "json",
        async : false,
        cache : false,
        success : function(json){
            if(json.hasIp){
                result =  false;
            }else {
                result =  true;
            }
        },
        error : function(arg1, arg2, arg3){
            showTip("","请求错误");
        }
    });
    return result;
}, "IP已经存在");

/**
 * 邮箱唯一性
 */
jQuery.validator.addMethod("adminEmailUnique", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var result = false;
	$.ajax({
		url : window.PATH + "/sys/admin/user/checkEmail",
		type : "get",
		data : {"email": value},
		dataType : "json",
		async : false,
		cache : false,
		headers: {
			"OWASP_CSRFTOKEN": $(element).attr("csrftoken")
		},
		success : function(json){
			if(json.code == 1){
				result =  true;
			}else if(json.code == -1){
				if(window.showTip){
					showTip("提示",json.message,null);
				}
				result =  false;
			}else if(json.code == -2){
				result =   false;
			}
		},
		error : function(arg1, arg2, arg3){
			showTip("","请求错误",null);
		}
	});
	return result;
}, "邮箱已被注册");

/**
 * 用户名唯一性
 */
jQuery.validator.addMethod("adminUsernameUnique", function(value, element, attrValue) {
	if($.trim(value) == ""){
		return false;
	}
	var result = false;
	$.ajax({
		url : window.PATH + "/sys/admin/user/checkUserName",
		type : "get",
		data : {"username": value},
		dataType : "json",
		async : false,
		cache : false,
		headers: {
			"OWASP_CSRFTOKEN": $(element).attr("csrftoken")
		},
		success : function(json){
			if(json.code == 1){
				result =  true;
			}else if(json.code == -1){
				if(window.showTip){
					showTip("提示",json.message,null);
				}
				result =  false;
			}else if(json.code == -2){
				result =  false;
			}
		},
		error : function(arg1, arg2, arg3){
			showTip("","请求错误",null);
		}
	});
	return result;
}, "用户名已被注册");