jQuery(document).ready(function() {

    App.init();//必须，不然导航栏及其菜单无法折叠
    checkItemGroup($("#applyObject"),$('#monitorId').val());
    $("#applyObject").change(function() {
      checkItemGroup(this,$('#monitorId').val());
    });
    
    $('#isSms').change(function () {
        var set = "[clt=smsGroup]";
        var checked = $(this).is(":checked");
        $(set).each(function () {
            if (checked) {
                $(this).attr("checked", true);
                $(this).attr("value",1);
                changeStatus($(this).attr("prefix"));
            } else {
                $(this).attr("checked", false);
                $(this).attr("value",0);
                changeStatus($(this).attr("prefix"));
            }
        });
        jQuery.uniform.update(set);
    });
    
    $('#isMail').change(function () {
        var set = "[clt=mailGroup]";
        var checked = $(this).is(":checked");
        $(set).each(function () {
            if (checked) {
                $(this).attr("checked", true);
                $(this).attr("value",1);
                changeStatus($(this).attr("prefix"));
            } else {
                $(this).attr("checked", false);
                $(this).attr("value",0);
                changeStatus($(this).attr("prefix"));
            }
        });
        jQuery.uniform.update(set);
    });
    
    $('#isWechat').change(function () {
        var set = "[clt=wechatGroup]";
        var checked = $(this).is(":checked");
        $(set).each(function () {
            if (checked) {
                $(this).attr("checked", true);
                $(this).attr("value",1);
                changeStatus($(this).attr("prefix"));
            } else {
                $(this).attr("checked", false);
                $(this).attr("value",0);
                changeStatus($(this).attr("prefix"));
            }
        });
        jQuery.uniform.update(set);
    });
    
    $('[clt=smsGroup]').each(function () {
    	var checked = $(this).is(":checked");
    	if (checked) {
            $(this).attr("value",1);
            changeStatus($(this).attr("prefix"));
        } else {
            $(this).attr("value",0);
            changeStatus($(this).attr("prefix"));
        }
    });
    
    $('[clt=smsGroup]').change(function () {
    	var checked = $(this).is(":checked");
    	if (checked) {
            $(this).attr("value",1);
            changeStatus($(this).attr("prefix"));
        } else {
            $(this).attr("value",0);
            changeStatus($(this).attr("prefix"));
        }
    });
    
    $('[clt=mailGroup]').each(function () {
    	var checked = $(this).is(":checked");
    	if (checked) {
            $(this).attr("value",1);
            changeStatus($(this).attr("prefix"));
        } else {
            $(this).attr("value",0);
            changeStatus($(this).attr("prefix"));
        }
    });
    
    $('[clt=mailGroup]').change(function () {
    	var checked = $(this).is(":checked");
    	if (checked) {
            $(this).attr("value",1);
            changeStatus($(this).attr("prefix"));
        } else {
            $(this).attr("value",0);
            changeStatus($(this).attr("prefix"));
        }
    });
    
    $('[clt=wechatGroup]').each(function () {
    	var checked = $(this).is(":checked");
    	if (checked) {
            $(this).attr("value",1);
            changeStatus($(this).attr("prefix"));
        } else {
            $(this).attr("value",0);
            changeStatus($(this).attr("prefix"));
        }
    });
    
    $('[clt=wechatGroup]').change(function () {
    	var checked = $(this).is(":checked");
    	if (checked) {
            $(this).attr("value",1);
            changeStatus($(this).attr("prefix"));
        } else {
            $(this).attr("value",0);
            changeStatus($(this).attr("prefix"));
        }
    });
    
});

var ENABLE = 1;
var DISABLE = 0;

function changeStatus(prefix){
	var result = false;
	$('[prefix='+prefix+']').each(function () {
    	if ($(this).is(":checked")) {
    		result = true;
        }
    });
	if(result){
		$('[prefix='+prefix+'_status]').val(ENABLE);
	}else{
		$('[prefix='+prefix+'_status]').val(DISABLE);
	}
}

function configStatus(obj){
    var prefix = $(obj).attr("prefix")+'_status';
    if($(obj).is(":checked")){
        $("[prefix='"+prefix+"']").attr('value',ENABLE);
    }else{
        $("[prefix='"+prefix+"']").attr('value',DISABLE);
    }
}
