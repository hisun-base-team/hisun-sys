function versionChange(val) {
    var ver = val.options[val.selectedIndex].text;
    scyLvShange(document.getElementById("scyLv"));
    if (ver == 'v2c') {
        $("[version='v3']").each(function () {
            $(this).slideUp("slow",null);
            disable($(this).attr("name"));

            $("#passwordGroup").slideDown("slow",null);;
            enable("#password");
        });
    } else if (ver == 'v3') {
        $("[version='v3']").each(function () {
            $(this).slideDown("slow",null);

            enable($(this).attr("name"));
        });
    }
}

function scyLvShange(val){
    var ver = val.options[val.selectedIndex].text;
    if (ver == 'NoAuthNoPriv') {
        $("#AuthComponent").slideUp("slow",null);
        $("#passwordGroup").slideUp("slow",null);
        $("#privProtComponent").slideUp("slow",null);

        disable("#authProtocolId");
        disable("#userName");
        disable("#password");
        disable("#privProtId");
        disable("#priv");
    } else if (ver == 'AuthNoPriv') {
        $("#AuthComponent").slideDown("slow",null);;
        $("#passwordGroup").slideDown("slow",null);;
        $("#privProtComponent").slideUp("slow",null);

        enable("#authProtocolId");
        enable("#userName");
        enable("#password");
        disable("#privProtId");
        disable("#priv");
    }else if (ver == 'AuthPriv'){
        $("#AuthComponent").slideDown("slow",null);
        $("#passwordGroup").slideDown("slow",null);
        $("#privProtComponent").slideDown("slow",null);
        enable("#authProtocolId");
        enable("#userName");
        enable("#password");
        enable("#privProtId");
        enable("#priv");
    }
}

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
        if (!enable) {
            checked = enable;
        }
        var name = $(element).attr('prefix')+".status";
        $("[name='"+name+"']").attr("value",ENABLE);
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
var WINDOW = "1";
var LINUX = "2";