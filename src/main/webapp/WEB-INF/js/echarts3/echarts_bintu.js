
var myChartForAllAnnular = echarts.init(document.getElementById('weaklistForAllAnnular'));
optionForAllAnnular = {
	tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c}%",
		backgroundColor: 'rgba(248,248,248,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
		borderColor: '#3f88bb',       // 提示边框颜色
		borderRadius: 4,           // 提示边框圆角，单位px，默认为4
		borderWidth: 1,            // 提示边框线宽，单位px，默认为0（无边框）
		padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
		textStyle: {
			color: '#333333'
		},
	},
	color:[ '#9EF3A5','#F38A88','#F2F39C','#A3F3EF','#B9B6F3'],
	title: {
		text: '0.0',
		subtext:'',
		subtextStyle: {
		color: '#3f88bb',
		fontFamily: 'sans-serif',
		fontSize: 12
	},
		x: 'center',
		y: 'center',
		itemGap: 20,
		textStyle : {
			color : '#3f88bb',
			fontFamily: 'sans-serif',
			fontSize : 11
		}

	},
	legend: {
		orient: 'vertical',
		x: 'left',
		itemWidth : 15,
		itemHeight : 5,
		textStyle : {
			fontSize : 10,
		},
		data:['暂无数据']
	},
	series: [
		{
			name:'访问来源',
			type:'pie',
			radius: ['60%', '80%'],
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: false,
					position: 'center'
				},
				emphasis: {
					show: false,
					textStyle: {
						fontSize: '30',
						fontWeight: 'bold'
					}
				}
			},
			labelLine: {
				normal: {
					show: false
				}
			},
			data:[
				{value:0, name:'暂无数据'}
			]
		}
	]
};
//myChartForAllAnnular.setOption(optionForAllAnnular, true);
//系统漏洞
var myChart11 = echarts.init(document.getElementById('weaklist01'));
option11 = {
	tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c}%",
		backgroundColor: 'rgba(248,248,248,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
			borderColor: '#3f88bb',       // 提示边框颜色
			borderRadius: 4,           // 提示边框圆角，单位px，默认为4
			borderWidth: 1,            // 提示边框线宽，单位px，默认为0（无边框）
			padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
			textStyle: {
				color: '#333333'
			},
	},
	legend: {
		orient: 'vertical',
		x: 'left',
		//data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	},
	
	title: {
		show: true,
		text:'0%',
		x: 'center',
		y: 'center',
		itemGap: 20,
		textStyle : {
			color : 'black',
			fontFamily : '微软雅黑',
			fontSize : 15,
			fontWeight : 'bolder'
		}
	},
	color:['#5cb2ff','#a0d568'],
	series: [
		{
			name:'系统漏洞',
			type:'pie',
			radius: ['50%', '70%'],
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: false,
					position: 'center'
				},
				emphasis: {
					show: false,
					textStyle: {
						fontSize: '12',
						//fontWeight: 'bold'
					}
				}
			},
			labelLine: {
				normal: {
					show: false  
				}
			},
			
			markPoint:{
				symbol:'arrow',
				},
			data:[
				{value:0, name:'成功检查主机'},
				{value:0, name:'还未配置主机'},
			]
			
		},
		
	]
};
myChart11.setOption(option11, true);
 
//高危进程
var myChart12 = echarts.init(document.getElementById('weaklist02'));
option12 = {
	tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c}%",
		backgroundColor: 'rgba(248,248,248,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
			borderColor: '#3f88bb',       // 提示边框颜色
			borderRadius: 4,           // 提示边框圆角，单位px，默认为4
			borderWidth: 1,            // 提示边框线宽，单位px，默认为0（无边框）
			padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
			textStyle: {
				color: '#333333'
			},
	},
	legend: {
		orient: 'vertical',
		x: 'left',
		//data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	},
	
	title: {
		show: true,
		text:'0%',
		x: 'center',
		y: 'center',
		itemGap: 20,
		textStyle : {
			color : 'black',
			fontFamily : '微软雅黑',
			fontSize : 15,
			fontWeight : 'bolder'
		}
			
	},
	color:['#5cb2ff','#a0d568'],
	series: [
		{
			name:'配置不合规',
			type:'pie',
			radius: ['50%', '70%'],
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: false,
					position: 'center'
				},
				emphasis: {
					show: false,
					textStyle: {
						fontSize: '12',
						//fontWeight: 'bold'
					}
				}
			},
			labelLine: {
				normal: {
					show: false  
				}
			},
			markPoint:{
				symbol:'arrow',
				},
			data:[
				{value:0, name:'成功检查主机'},
				{value:0, name:'还未配置主机'},
			]
		},
	]
};
myChart12.setOption(option12, true);
 
//高危账号
var myChart13 = echarts.init(document.getElementById('weaklist03'));
option13 = {
	tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c}%",
		backgroundColor: 'rgba(248,248,248,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
			borderColor: '#3f88bb',       // 提示边框颜色
			borderRadius: 4,           // 提示边框圆角，单位px，默认为4
			borderWidth: 1,            // 提示边框线宽，单位px，默认为0（无边框）
			padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
			textStyle: {
				color: '#333333'
			},
	},
	legend: {
		orient: 'vertical',
		x: 'left',
		//data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	},
	
	title: {
		show: true,
		text:'0%',
		x: 'center',
		y: 'center',
		itemGap: 20,
		textStyle : {
			color : 'black',
			fontFamily : '微软雅黑',
			fontSize : 15,
			fontWeight : 'bolder'
		}
			
	},
	color:['#5cb2ff','#a0d568'],
	series: [
		{
			name:'配置变更',
			type:'pie',
			radius: ['50%', '70%'],
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: false,
					position: 'center'
				},
				emphasis: {
					show: false,
					textStyle: {
						fontSize: '12',
						//fontWeight: 'bold'
					}
				}
			},
			labelLine: {
				normal: {
					show: false  
				}
			},
			markPoint:{
				symbol:'arrow',
				},
			data:[
				{value:0, name:'成功检查主机'},
				{value:0, name:'还未配置主机'},
			]
		},
	]
};
myChart13.setOption(option13, true);

//配置变更
var myChart16 = echarts.init(document.getElementById('weaklist06'));
option16 = {
	tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c}%",
		backgroundColor: 'rgba(248,248,248,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
			borderColor: '#3f88bb',       // 提示边框颜色
			borderRadius: 4,           // 提示边框圆角，单位px，默认为4
			borderWidth: 1,            // 提示边框线宽，单位px，默认为0（无边框）
			padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
			textStyle: {
				color: '#333333'
			},
	},
	legend: {
		orient: 'vertical',
		x: 'left',
		//data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	},
	
	title: {
		show: true,
		text:'0%',
		x: 'center',
		y: 'center',
		itemGap: 20,
		textStyle : {
			color : 'black',
			fontFamily : '微软雅黑',
			fontSize : 15,
			fontWeight : 'bolder'
		}
	},
	color:['#5cb2ff','#a0d568'],
	series: [
		{
			name:'安全问题',
			type:'pie',
			radius: ['50%', '70%'],
			avoidLabelOverlap: false,

			label: {
				normal: {
					show: false,
					position: 'center'
				},
				emphasis: {
					show: false,
					textStyle: {
						fontSize: '12',
						//fontWeight: 'bold'
					}
				}
			},
			labelLine: {
				normal: {
					show: false  
				}
			},
			
			markPoint:{
				symbol:'arrow',
				},
			data:[
				{value:0, name:'成功检查主机'},
				{value:0, name:'还未配置主机'},
			]
		},
	]
};
myChart16.setOption(option16, true);

			  
			  
	  

