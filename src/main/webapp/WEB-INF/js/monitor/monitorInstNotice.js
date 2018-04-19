jQuery(document).ready(function() {


    $("[clt='cltSms']").change(function(){
        selectAll(this,"data-set","clt");
    });
    $("[clt='cltMail']").change(function(){
        selectAll(this,"data-set","clt");
    });
    $("[clt='cltWechat']").change(function(){
        selectAll(this,"data-set","clt");
    });

    var groupName = "clt";
    var mailName = "mailGroup";
    var smsName = "smsGroup";
    var wechat = "wechatGroup";

    $("["+groupName+"='"+mailName+"']").change(function(){
        checkSelect(mailName,groupName,"#isMail");
        changeStatus(this);
    });
    $("["+groupName+"='"+smsName+"']").change(function(){
        checkSelect(smsName,groupName,"#isSms");
        changeStatus(this);
    });
    $("["+groupName+"='"+wechat+"']").change(function(){
        checkSelect(wechat,groupName,"#isWechat");
        changeStatus(this);
    });
});

function disable(element){
    $(element).attr("disabled","disabled");
}

function enable(element){
    $(element).removeAttrs("disabled");
}

var ENABLE = 1;
var DISABLE = 0;
function configStatus(val){
    var isStart = val.id+'.status';
    if(val.checked){
        $("[name='"+isStart+"']").attr('value',ENABLE);
    }else{
        $("[name='"+isStart+"']").attr('value',DISABLE);
    }
}

/**
 * ckeckbox 全选
 * @param element 被选择的checkbox
 * @param groupAttrName 需要全部选择checkbox的组属性名
 * @param cltAttrName 控制属性名
 */
function selectAll(element,groupAttrName,cltAttrName){
    var group = $(element).attr(groupAttrName);
    var checked = $(element).is(":checked");
    $("["+cltAttrName+"='"+group+"']").each(function (index,element) {
        if (checked) {
            $(element).attr("checked", true);
        } else {
            $(element).attr("checked", false);
        }
        changeStatus(this);
    });
    jQuery.uniform.update("["+cltAttrName+"='"+group+"']");
}

function checkSelect(groupName,cltAttrName,targetEl){
    var checked = true;
    $("["+cltAttrName+"='"+groupName+"']").each(function(index,element){
        var enable = $(element).is(":checked");
        var name = $(element).attr('prefix')+".status";
        if (!enable) {
            checked = enable;
        }else{
            $("[name='"+name+"']").attr("value",ENABLE);
        }
    });

    if(checked){
        $(targetEl).attr("checked",true);
    }else{
        $(targetEl).attr("checked",false);
    }
    jQuery.uniform.update(targetEl);
}
function changeStatus(element){
    var prefix = $(element).attr("prefix");
    var disable = true;
    var target = "[name='"+prefix+".status']";
    $("[prefix='"+prefix+"']").each(function(index,el){
        if($(el).is(":checked")){
            disable = false;
            return disable;
        }
    });
    if(disable){
        $(target).attr("value",0);
    }else{
        $(target).attr("value",1);
    }
}