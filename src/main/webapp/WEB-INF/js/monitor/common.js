function changeUrl(serviceName){
	var url = "/";
	for(i=0;i<serviceName.length;i++){
		var str = serviceName.charAt(i);
		if (/^[A-Z]+$/.test(str)){
			str = "/"+str.toLowerCase();
		}
		url = url + str;
	} 
	return url;
}