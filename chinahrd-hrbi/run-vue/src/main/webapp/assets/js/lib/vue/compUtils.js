define([], function() {
	var ajaxOption={
			url:'',
			async:true,
			cache:false,
			cache:false,
			cache:false,
			
	}
	return {
		/********************legend***************************************/
		legend : {
			orient : 'vertical',
			type : 'bar',
			x : '85%',
			y : 'center',
			selectedMode : false,
			textStyle : {
				color : '#555',
				fontFamily : '微软雅黑 verdana tahoma',
				fontSize : 12
			},
			itemWidth : 10,
			itemHeight : 10,
			itemGap : 12,
			backgroundColor : '#fff',
			padding : 0,
			data : []
		},

		/********************END legend***************************************/
		
		/******************************Colors*******************************/
		colorPie : [ '#5cb7f1', '#01d286', '#e5689b', '#b285c3', '#4682bc',
				'#8f684b', '#f28e7f', '#fbc370', '#8eb3e8', '#6386ce' ],
		colorLine : [ '#f5b147', '#a88fd7', '#ce646e', '#7ace64', '#49bebf', '#4ba0ec' ],
		
		guage: {
			axisLine_lineStyle_color : [[0.3, '#72B031'], [0.7, '#F1A502'], [1, '#D4531A']],
			axisLabel_textStyle_color : '#333',
			pointer_color : '#000',
		},
		/******************************Colors*******************************/
		
		/******************************Status*******************************/
		/**
		 * 是否加载中...
		 */
		isLoading:function(data){
			return data==undefined || data==null || data.code!=200;
		},
		/******************************Status*******************************/
		
		/******************************Data constructing********************/
		/**
		 * 组件内部使有，分解外部调用后传入来结构
		 */
		analysisData:function(data){
			return data.data;
		},
		/**
		 * 数据包
		 * 作用：调用处使用。请求后数据调用，封装好统一格式
		 */
		dataPacket:function(data){
			return {
				code:200,
				data:data
			}
		},

		/******************************END Data constructing ********************/
		
		
		/*******************************ajaxs********************************/
		ajax:function(option){
			$.ajax({url:option.url,
				   type:option.type,
				   async:option.async,
				   susscue:function(){
					   
				   },
			});
			
		},
		

		/*******************************END ajaxs********************************/
		
	}
})
