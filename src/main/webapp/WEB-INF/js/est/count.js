function DashboardCount(targetDivs, valueOption){
	this.targetDivs = targetDivs;
	this.valueOption = valueOption;
	this.init = function(){
		for(var i=0;i<this.targetDivs.length;i++){
			$('#'+this.targetDivs[i].id).easyPieChart({
	             animate: 1000,
	             size: 200,
	             lineWidth: 10,
	             barColor: App.getLayoutColorCode(targetDivs[i].color)
	         });
		}
	}
	
	this.loadData = function(url){
		$.ajax({
			url : url,
			data : null,
			dataType : "json",
			type : "get",
			success : function(json){
				var vals = jsos.vMap;
				for(var key in valueOption){
					document.getElementById(key).innerHTML = vals[key];
				}
			},
			error: function(XMLHttpRequest,textStatus,errorThrown){
                alert(textStatus+":"+errorThrown);
        	}
		});
		
	}
	return this;
}