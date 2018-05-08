/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

//执行下拉选择框加载
function selectLoadByTag(selectUrl,id,token){
    var selectDefineAttObj = document.getElementById(id+"_tagDefineAtt");
    var userParameter = selectDefineAttObj.getAttribute("userParameter");
    var defaultkeys = selectDefineAttObj.getAttribute("defaultkeys");
    var radioOrCheckbox = selectDefineAttObj.getAttribute("radioOrCheckbox");
    var moreSelectSearch = selectDefineAttObj.getAttribute("moreSelectSearch");
    var moreSelectAll = selectDefineAttObj.getAttribute("moreSelectAll");
    var buttonHeight = selectDefineAttObj.getAttribute("buttonHeight");//文本的高度
    var defaultkeyArr = defaultkeys.split(",")

    $.ajax({
        async : false,
        cache:false,
        type:"POST",
        dataType : "json",
        url: selectUrl,// 请求的action路径
        headers: {
            "OWASP_CSRFTOKEN":token
        },
        error: function () {// 请求失败处理函数
            alert('加载下拉控件数据数据失败');
        },
        success:function(data){
            if(data.success) {
                var options = data.data;
                if (options != null) {
                    for (var i in options) {
                        var map = options[i];
                        var optionKey = map["optionKey"];
                        var optionValue = map["optionValue"];
                        var selected = map["selected"];
                        if (selected == "true") {
                            $("#" + id).append("<option value='" + optionKey + "' selected>" + optionValue + "</option>");
                        } else {
                            var selected = false;
                            for (var n = 0; n < defaultkeyArr.length; n++) {
                                if (optionKey == defaultkeyArr[n]) {
                                    selected = true;
                                }
                            }
                            if (selected == true) {
                                $("#" + id).append("<option value='" + optionKey + "' selected>" + optionValue + "</option>");
                            } else {
                                $("#" + id).append("<option value='" + optionKey + "'>" + optionValue + "</option>");
                            }
                        }


                    }
                }
                if (radioOrCheckbox == "checkbox") {
                    var search = false;
                    var selectAll = false;
                    if (moreSelectSearch == "true") {
                        search = true;
                    }
                    if (moreSelectAll == "true") {
                        selectAll = true;
                    }
                    $("#" + id).multiselect({
                        columns: 1,
                        placeholder: '请选择...',
                        search: search,
                        selectGroup: true,
                        selectAll: selectAll
                    });
                }
                if (buttonHeight != "") {
                    var obj = $("#" + id);
                    var buttonObj = obj.siblings().children()[0]
                    buttonObj.style.height = buttonHeight;
                    //buttonObj.height(buttonHeight);
                }
                //$("#"+id).innerHTML=data.options;
            }else{
                alert('加载下拉控件数据数据失败');
            }
        }
    });
}