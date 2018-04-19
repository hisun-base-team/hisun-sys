function LoadRecord(path,inTime){
	this.inTime = inTime;
	this.msgContent = $('#msgContent');
	this.load = function(consultId){
		var o = this.msgContent.find("li:first");
		var timeStr = inTime;
		if(o.length>0){
			var datetimeObj = $(o[0]).find(".datetime");
			if(datetimeObj.length>0){
				timeStr = datetimeObj[0].innerHTML;
			}
		}
		$('#moreBut').attr('disabled',true);
		$.ajax({
			url : path+"/management/est/chatRecord/ajax/loadRecord?consultId="+consultId+"&dateStr="+timeStr,
			data : {},
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			dataType : "html",
			type : "get",
			success : function(html){
				$('#moreBut').attr('disabled',false);
				$('#msgContent').prepend(html);
			},
			error: function(XMLHttpRequest,textStatus,errorThrown){
				$('#moreBut').attr('disabled',false);
                if(textStatus=="timeout"){
                	msgListener();
                }
        	}
		});
	}
}