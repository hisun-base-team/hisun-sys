function MsgView(addId, inInfo, path){
	this.targetDom = $('#'+addId);
	this.inInfo = inInfo;
	this.userMap = new Object();
	this.path = path;
	this.msgIn = function(contentArr){
		var str = "";
		for(var i=0;i<contentArr.length;i++){
			str += '<li class="in">'+
			  '<img class="avatar" alt="" src="' + this.inInfo.img + '">'+
			  '<div class="message">'+
			  '<span class="arrow"></span>'+
			  '<a href="#" class="name">' + this.inInfo.name +'</a>'+
			  '<span class="datetime">' + contentArr[i].time + '</span>'+
			  '<span class="body">'+ contentArr[i].msg + '</span></div></li>';
		}
		this.targetDom.append(str);
	}
	
	this.msgOut = function(contentArr){
		var str = "";
		for(var i=0;i<contentArr.length;i++){
			str += '<li class="out">'+
			  '<img class="avatar" alt="" src="' + this.path+contentArr[i].img + '">'+
			  '<div class="message">'+
			  '<span class="arrow"></span>'+
			  '<a href="#" class="name">' + contentArr[i].name +'</a>'+
			  '<span class="datetime">' + contentArr[i].time + '</span>'+
			  '<span class="body">'+ contentArr[i].msg + '</span></div></li>';
		}
		this.targetDom.append(str);
	}
	
	this.setOutInfo = function(info){
		this.outInfo = info;
	}
	
	
}

function CustomerMsgView(addId, inInfo,path){
	
	MsgView.call(this,addId, inInfo,path);
	//写死不传参数
	this.expertInfoDiv = $('#outInfoDiv');
	this.msgOut = function(contentArr){
		var str = "";
		for(var i=0;i<contentArr.length;i++){
			if(!this.userMap[contentArr[i].id]){
				if($('#info'+contentArr[i].id).length==0){
					this.readExpert(contentArr[i].id, this.expertInfoDiv);
				}	
				this.userMap[contentArr[i].id] = contentArr[i].id;
			}
			str += '<li class="out">'+
			  '<img class="avatar" alt="" src="' + this.path + contentArr[i].img + '">'+
			  '<div class="message">'+
			  '<span class="arrow"></span>'+
			  '<a href="#" class="name">' + contentArr[i].name +'</a>'+
			  '<span class="datetime">' + contentArr[i].time + '</span>'+
			  '<span class="body">'+ contentArr[i].msg + '</span></div></li>';
		}
		this.targetDom.append(str);
	}
	
	
	this.readExpert = function(userId, targetDiv){
		$.ajax({
			url : path+"/management/est/consult/ajax/chat/expert/get?userId="+userId,
			data : null,
			dataType : "html",
			type : "get",
			success : function(html){
				targetDiv.append(html);
				/* hasOut=1;
				msgView.setOutInfo(json.info);
				document.getElementById("outImg").src = json.info.img;
				document.getElementById("outName").innerHTML = json.info.name;
				document.getElementById("outOrg").innerHTML = json.orgName;
				document.getElementById("outInfoDiv").style.display = "block";
				document.getElementById("titleName").innerHTML = "您正与"+json.info.name+"专家聊天";
				if(json.position){
					document.getElementById("outPositionP").style.display = "block";
					document.getElementById("outPosition").innerHTML = json.position;
				}
				if(json.experience){
					document.getElementById("outExperienceP").style.display = "block";
					document.getElementById("outExperience").innerHTML = json.experience;
				}
				if(json.skill){
					document.getElementById("outSkillP").style.display = "block";
					document.getElementById("outSkill").innerHTML = json.skill;
				}
				if(json.certificate){
					document.getElementById("outCertificateP").style.display = "block";
					document.getElementById("outCertificate").innerHTML = json.certificate;
				} */
			},
			error: function(XMLHttpRequest,textStatus,errorThrown){
                //alert(textStatus+":"+errorThrown);
        	}
		});
	}
}