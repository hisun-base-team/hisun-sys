function startScoreInit(startTargetId,targetId,valId,defaultScore){
	var stepW = 24;
    var description = new Array("非常差，回去再练练","真的是差，都不忍心说你了","一般，还过得去吧","很好，是我想要的东西","太完美了，此物只得天上有，人间哪得几回闻!");
    var stars = $("#"+startTargetId+" > li");
    var descriptionTemp;
    $("#"+targetId).css("width",0);
    if(defaultScore){
    	$("#"+targetId).css({"width":stepW*defaultScore});
    }
    stars.each(function(i){
        $(stars[i]).click(function(e){
            var n = i+1;
            $('#'+valId).val(n);
            $("#"+targetId).css({"width":stepW*n});
            descriptionTemp = description[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp;
        });
    });
    stars.each(function(i){
        $(stars[i]).hover(
            function(){
                $(".description").text(description[i]);
            },
            function(){
                if(descriptionTemp != null)
                	$(".description").text("当前您的评价为："+descriptionTemp);
                else 
                    $(".description").text(" ");
            }
        );
    });
}

function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};
