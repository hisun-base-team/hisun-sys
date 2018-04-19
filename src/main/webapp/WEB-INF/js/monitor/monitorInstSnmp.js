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

var WINDOW = "1";
var LINUX = "2";
function checkItemGroup(sysTypeEl){
    var target = $(sysTypeEl).val();
    if(LINUX==target){
        $("[group='linuxItemMonitorItemGroup']").each(function (index, element) {
            $(element).slideDown("slow",null);
            $(element).find("input").removeAttrs("disabled");
        });
        $("[group='windowsMonitorItemGroup']").each(function (index, element) {
            $(element).slideUp("slow",null);
            $(element).find("input").attr("disabled","disabled");
        });
    }else if(WINDOW==target){
        $("[group='windowsMonitorItemGroup']").each(function (index, element) {
            $(element).slideDown("slow",null);
            $(element).find("input").removeAttrs("disabled");
        });
        $("[group='linuxItemMonitorItemGroup']").each(function (index, element) {
            $(element).slideUp("slow",null);
            $(element).find("input").attr("disabled","disabled");
        });
    }
}

function serverCheckItemGroup(applyObject,keyName){
    var target = $(applyObject).val();
    $("[key='"+keyName+"']").each(function(index,element){
        $(element).slideUp("slow",null);
        $(element).find("input").attr("disabled","disabled");
    });
    $("[group='"+target+"']").each(function(index,element){
        $(element).slideDown("slow",null);
        $(element).find("input").removeAttrs("disabled");
    });
}