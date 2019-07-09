require(['jquery', 'echarts', 'echarts/chart/bar','echarts/chart/line', 'echarts/chart/pie','jgGrid',
    'underscore', 'utils',  "appBase","chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
    		getTopDataUrl: webRoot + '/mobile/laborEfficiency/getTopData.do',
    		getLaborEfficiencyRatioUrl: webRoot + '/mobile/laborEfficiency/getLaborEfficiencyRatio.do',           //劳动力效能对比
    		getLaborEfficiencyTrendUrl: webRoot + '/mobile/laborEfficiency/getLaborEfficiencyTrend.do',           //劳动力效能趋势
    		 queryOvertimeByOrganUrl: webRoot + '/mobile/laborEfficiency/queryOvertimeByOrgan.do',           //组织机构加班时长
    		 queryOvertimeTrendUrl: webRoot + '/mobile/laborEfficiency/queryOvertimeTrend.do',           //加班时长趋势-人均时长  总长
    		 queryCheckWorkTypeLayoutUrl: webRoot + '/mobile/laborEfficiency/queryCheckWorkTypeLayout.do',           //考勤类型分布
    		 queryAttendanceDetailUrl: webRoot + '/mobile/laborEfficiency/queryAttendanceDetail.do',           // 出勤明细
    		 queryCheckWorkTypeUrl: webRoot + '/mobile/laborEfficiency/queryCheckWorkType.do',           // 出勤明细-考勤类型
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var time=$("#time").val();
    var timeRange=$("#timeRange").val();
    function loadPage(){
        height=getWindowHeight()-140;
        $("#workDetailPanel").width($(window).width())
        $("#workDetailPanel").height(height)
    }
    loadPage();
    $(window).bind(resizeEvent,function(){
    	laborRatioObj.resize();
		laborEfficiencyTrendObj.resize();
		overtimeAvgObj.resize();
		overtimeAvgHoursObj.resize();
		checkWorkTypeObj.resize();
		tableGrid.resize();
    });
    

    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	  var load = $(e.target).attr('load');
    	if(targetAreaId=="laborEfficiencyPanel"){
    		tableGrid.showScroll(false);
    		laborRatioObj.resize();
    		laborEfficiencyTrendObj.resize();
    	}else if(targetAreaId=="overtimePanel"){
    		tableGrid.showScroll(false);
    		if(load=="true"){
    			overtimeAvgObj.resize();
        		overtimeAvgHoursObj.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			overtimeAvgObj.init(reqOrganId,time);
      			overtimeAvgHoursObj.init(reqOrganId,time);
      		}
    	}else if(targetAreaId=="attendanceTimePanel"){
    		tableGrid.showScroll(false);
    		if(load=="true"){
    			checkWorkTypeObj.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			checkWorkTypeObj.init(reqOrganId,time);
      		}
    		
    	}else if(targetAreaId=="attendanceDetailPanel"){
    		tableGrid.showScroll(true);
    		if(load=="true"){
    			tableGrid.resize();
      		}else{
      			$(e.target).attr('load',"true");
//      			tableGrid.init(reqOrganId,time);
      			attendanceDetailCheckWorkTypeObj.init(reqOrganId,time);
      		}
    	}
    });
    
    
    /*==============================================================*/
    /* TOP															*/
    /*==============================================================*/
    var topObj={
    		chartId:"top",
    	className:"teamCardPanel",
    	init:function (organId) {
    		var self=this;
    		loading(self.chartId);
    		$.ajax({
    			url : urls.getTopDataUrl,
    			data : {
        			organId : organId
        		},
    			type : 'post',
    			success : function(data){
    				loaded(self.chartId);
    				self.loadData(data);
    			},
    			error : function(){}
    		});
    	},loadData:function(data){
    		var teamCards=$("."+this.className).children(".teamCard");
    		$.each(teamCards,function(i,item){
    			var span=$(item).children(".value").children("span");
    			var label=$(item).children("label");
    			if(i==0){
    				$(span).text(data.laborEfficiency.num)
    			}else if(i==1){
    				$(span).text(data.overtimeHours.totalNum)
    			}else if(i==2){
    				$(span).text(data.overtimeWarningCount.conNum)
    			}
    		});
    	}
    
    }
    
    
    /*==============================================================*/
    /* 劳动力效能下级组织对比															*/
    var laborRatioObj = {
		chartId: 'laborRatioChart',
        chartObj: null,
        option: {
        	animation:animation,
        	grid: defaultGrid,
	        xAxis: [{
	        	type: 'value',
	        	axisLine: false,
                splitLine: splitLine,
                axisTick: {
                    show: false
                },
                axisLabel: {
                	 itemStyle: {
                         color: '#BEBEBE'
                     }, formatter: '{value}%'
                }
            }],
	        yAxis: [{
	        	type: 'category',
	            splitLine: false,
	            axisLine: {
                	show:false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#333'
                    }
                },
                data: []
	        }],
	        series: [{
	            type: 'bar',
	            name:"劳动力效能",
	            barWidth :barWidth,
	            itemStyle: {
	                normal: {
	                	color: barColor,
	                    label: {
	                        show: false,
	                        textStyle: {
	                            color: '#000'
	                        },
	                        formatter: function (i) {
	                            return i.value + '%';
	                        }
	                    }
	                }
	            },
	            data: [],
	            markLine: {
                    clickable: false,
                    symbolSize: [0, 0],
                    itemStyle: {
                        normal: {
                            color: lineColor,
                            lineStyle: {
                                type: 'solid'
                            }
                        },
                        emphasis: {
                            label: false
                        }
                    },
                    data: []
                }
	        }],
        },
        init: function (organId, time) {
            var self = this;
            self.chartObj = echarts.init(document.getElementById(self.chartId));
            self.initData(organId, time);
        },
        initData: function (organId, time) {
        	var self = this;
        	var beginTime;
        	var endTime;
        	loading(self.chartId);
        	$.get(urls.getLaborEfficiencyRatioUrl, {organId: organId,time: time}, function (data) {
        		loaded(self.chartId);
        		self.generateChart(data);
        	});
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
    		if (_.isEmpty(data.list)) {
    			 showEmptyTip($('#' + self.chartId));
            } else {
            	$.each(data.list, function (i, item) {
                    category.push(item.organName);
                    barDataArr.push({id: item.organId, name: item.organName, value: parseFloat(item.attendanceRate*100).toFixed(2), flag: item.hasChildren});
                });
            	 scalChartHeight(self,data.list.length);
                self.option.yAxis[0].data = category;
                self.option.series[0].data = barDataArr;
                self.option.series[0].markLine.data = self.packMarkLineData(barDataArr.length, data.curr.attendanceRate*100);
                self.chartObj.setOption(self.option, true);
                $('#' + self.chartId).parent().children(".note").html("<div class='line'></div><div class='lineNote'>当前组织</div>");
                $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(i,v,p){ 
                	var cols=[{name:v.name,value:v.data[0].value.value,unit:"%"}];
                	cols.push({name:"当前组织",value:(data.curr.attendanceRate*100).toFixed(2),unit:"%"});
                    return {title:timeRange,cols:cols};
                }});
            }
        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: value, yAxis: -1},
                {xAxis:value, yAxis:  cateLen + 1}]];
        },
        resize: function () {
            this.chartObj.resize();
        }
    };

    /** 劳动力效能趋势 */
    var laborEfficiencyTrendObj = {
		chartId: "laborEfficiencyRatio",
	    chartObj: null,
		option: {
        	animation:animation,
        	grid: defaultDoubleGrid,
        	legend: getDefaultLegend(["劳动力效能",'环比变化']),
	        xAxis: [{
                type: "category",
                splitLine: false,
                axisTick: false,
                data: [],
                axisLine: false,
                axisLabel: {
                    itemStyle: {
                        color: '#BEBEBE'
                    }
                }
            }],
	        yAxis: [{
	            type: 'value',
	            splitLine: splitLine,
	            axisLine: false,
	            axisLabel: {
	                formatter: '{value}%'
	            }
	        },{
	            type: 'value',
	            splitLine: splitLine,
	            axisLine: false,
	            axisLabel: {
	                formatter: '{value}%'
	            }
	        }],
	        series: [{
	            type: 'bar',
	            barWidth :barWidth,
	            name:'劳动力效能',
	            yAxisIndex: 0,
	            itemStyle: {
	                normal: {
	                	color: barColor,
	                    label: {
	                    	show: false,
	                        textStyle: {
	                            color: '#000'
	                        },
	                        formatter: function (i) {
	                            return i.value + '%';
	                        }
	                    }
	                }
	            },
	            data: [],
	        },{

                name: "环比变化",
                type: 'line',
                yAxisIndex: 1,
        		itemStyle: {
                    normal: {
                    	color: lineColor,
                    }
                },
                symbol: 'circle',
                data: []
            }],
        },
		init: function (organId, time) {
			var self = this;
			self.chartObj =  echarts.init(document.getElementById(self.chartId));
            self.initData(organId, time);
		},
		initData: function (organId, time) {
			var self = this;
        	var beginTime;
        	var endTime
        	loading(self.chartId);
        	$.get(urls.getLaborEfficiencyTrendUrl, {organId: organId, time: time}, function (data) {
        				loaded(self.chartId);
        		self.generateChart(data);
        	});
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var lineDataArr = [];
            if (_.isEmpty(data.list)) {
            	 showEmptyTip($('#' + self.chartId));
            } else {
            	var len=0;
            	$.each(data.list, function (i, item) {
            		len++;
                    category.push(item.typeName.toString().substr(2, 2) + '/' + item.typeName.toString().substr(4, 2));
                    barDataArr.push({name: item.yearMonth, value: parseFloat(item.attendanceRate*100).toFixed(2)});
                    lineDataArr.push(parseFloat(item.rate*100).toFixed(2));
                });
            	self.option.xAxis[0].data = category;
                self.option.series[0].data = barDataArr;
                self.option.series[1].data = lineDataArr;
                if($(window).width()<360&&len>=6){
                	self.option.xAxis[0].axisLabel.rotate=30;
                }
                self.chartObj.setOption(self.option, true);
                $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(i,v,p){ 
                	var cols=[{name:v.data[0].name,value:v.data[0].value.value,unit:"%"}];
                	cols.push({name:v.data[1].name,value:v.data[1].value,unit:"%"});
                    return {title:v.name,cols:cols};
                }});
            }
        },
        resize: function () {
        	this.chartObj.resize();
        }
    };
   
    
    
    /**
     * 加班时长-平均时长
     * */
    var overtimeAvgObj = {
    	chartId: "overtimeChart",
    	btnId:"overtimeBtn",
		chartObj: null,
		xAxisData:[],
		data:[{data:[],markLine:null},{data:[],markLine:null}],
		option: {
			animation:animation,
        	grid: defaultGrid,
	        xAxis: [{
	            type: 'value',
	            splitLine: splitLine,
	            axisLine: false,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        }],
	        yAxis: [{
	            type: 'category',
	            splitLine: false,
	            axisLine: false,
	            axisTick: false,
	            axisLabel: {
	                show: true,
	                itemStyle: {
	                    color: '#D7D7D7'
	                }
	            },
	            data: []
	        }],
	        series: [{
	            type: 'bar',
	            barCategoryGap: '45%',
	            barWidth: barWidth,
	            itemStyle: {
	                normal: {
	                	color: barColor,
	                    label: {
	                        show: false,
	                        textStyle: {
	                            color: '#000'
	                        },
	                        formatter: function(o){
	                        	return o.value;
	                        }
	                    }
	                }
	            },
	            data: [],
	            markLine: {
	                clickable: false,
	                symbolSize: [0, 0],
	                itemStyle: {
	                    normal: {
	                        color: lineColor,
	                        lineStyle: {
	                            type: 'solid'
	                        }
	                    }
	                },
	                data: []
	            }
	        }],
		},
		init: function (organId, time) {
			var self = this;
			self.chartObj =  echarts.init(document.getElementById(self.chartId));
            self.getRequestData(organId, time);
		},
        getRequestData: function(organId, time) {
        	var self = this;
        	loading(self.chartId);
        	$.get(urls.queryOvertimeByOrganUrl, 
        			{organId: organId, time: time},
        		function (data) {
        			loaded(self.chartId);
        			if (_.isEmpty(data.list)) {
                     	showEmptyTip($('#' + self.chartId));
                     }else{
                    	 self.packData(data); 
                     }
        			self.clickTab(0);
        			// 添加点击事件  
        			self.clickBtn();
        	});
        },packData:function(data){
        	 	var self = this;
        		$.each(data.list, function (i, item) {
            		 self.xAxisData.push(item.organName);
            		 self.data[0].data.push(item.avgNum);
            		 self.data[1].data.push(item.totalNum);
                });
        		self.data[0].markLine=self.packMarkLineData(self.xAxisData.length, data.avgNum);
        		self.data[1].markLine=self.packMarkLineData(self.xAxisData.length, data.totalNum);
        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: value, yAxis: -1},
                {xAxis: value, yAxis: cateLen + 1}]];
        },clickBtn:function(){
        	var self=this;
            $("#"+self.btnId).children().on(clickEvent,function(){
       		 var _t=this;
       		 	 self.clickTab($(this).attr("data"));
            	 setBtnClass(_t);
             });

        },clickTab:function(type){
        	 var self = this;
        	 if(self.xAxisData.length>0){
        		 scalChartHeight(self,self.xAxisData.length);
        		 var copy=[];
        		 var data=self.data[type].data;
        		 $.each(self.xAxisData,function(i,name){
        			copy.push({name:name,val:data[i]});
        		 });
        		 console.log(copy)
        		 copy=_.sortBy(copy, 'val');
        		 var xAxisData=[];
        		 var series=[];
        		 console.log(copy)
        		 $.each(copy,function(i,o){
        			 xAxisData.push(o.name);
        			 series.push(parseFloat(o.val).toFixed(2));
        		 });
        		 
        		 self.option.yAxis[0].data = xAxisData;
                 self.option.series[0].data = series;
                 self.option.series[0].markLine.data = self.data[type].markLine;
                 self.chartObj.setOption(self.option, true);
                 $('#' + self.chartId).parent().children(".note").html("<div class='line'></div><div class='lineNote'>当前组织</div>");
                 $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(i,v,p){ 
                 	var cols=[{name:v.name,value:v.data[0].value,unit:"小时"}];
                 	cols.push({name:"当前组织",value:(self.data[type].markLine[0][0].yAxis*1).toFixed(2),unit:"小时"});
                     return {title:timeRange,cols:cols};
                 }});
        	 }
        },
        resize: function () {
        	this.chartObj.resize();
        }
    };
    
    
    /**
     * 加班时长趋势-平均时长
     * */
    var overtimeAvgHoursObj = {
    	chartId : 'overtimeTrendChart',
    	btnId:'overtimeTrendBtn',
    	xAxisData:[],
		data:[{bar:[],line:[]},{bar:[],line:[]}],
		chartObj : null,
		option: {
			animation:animation,
        	grid: defaultDoubleGrid,
        	legend: getDefaultLegend(["人均加班时长","环比变化"]),
	        xAxis: [{
	            type: 'category',
	            splitLine: false,
	            axisLine: false,
	            axisTick: false,
	            axisLabel: {
	                show: true,
	                itemStyle: {
	                    color: '#D7D7D7'
	                }
	            },
	            data: []
	        }],
	        yAxis: [{
	            type: 'value',
	            splitLine: splitLine,
	            axisLine: false,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        },{
	            type: 'value',
	            splitLine: splitLine,
	            axisLine: false,
	            axisLabel: {
	                formatter: '{value}%'
	            }
	        }],
	        series: [{
	            type: 'bar',
	            name:'人均加班时长',
	            barWidth: barWidth,
	            yAxisIndex: 0,
	            itemStyle: {
	                normal: {
	                	color: barColor,
	                    label: {
	                        show: false,
	                        textStyle: {
	                            color: '#000'
	                        },
	                        formatter: function(o){
	                        	return o.value;
	                        }
	                    }
	                }
	            },
	            data: []
	        },{
        		itemStyle: {
                    normal: {
                    	color: lineColor,
                    }
                },
                name: "环比变化",
                type: 'line',
                yAxisIndex: 1,
                symbol: 'circle',
                data: []
            }],
		},
    	init : function(organId,time){
    		var self = this;
    		self.chartObj =  echarts.init(document.getElementById(self.chartId));
    		self.getRequestDataFun(organId,time);
    	},
    	getRequestDataFun: function(organId,time){
    		var self = this;
    		var param = {
				organId :organId,
				time : time,
    		};
    		loading(self.chartId);
    		$.ajax({
    			url : urls.queryOvertimeTrendUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				loaded(self.chartId);
    				if (_.isEmpty(data)) {
                     	showEmptyTip($('#' + self.chartId));
                     }else{
                    	 self.packData(data); 
                     }
        			self.clickTab(0);
        			// 添加点击事件  
        			self.clickBtn();
    			},
    			error : function(){}
    		});
    	},packData:function(data){
    		var self = this;
    		$.each(data.list, function (i, item) {
        		 self.xAxisData.push(item.typeName);
        		 self.data[0].bar.push(parseFloat(item.avgNum).toFixed(2));
        		 self.data[1].bar.push(parseFloat(item.totalNum).toFixed(2));
        		 self.data[0].line.push(parseFloat(item.avgRate).toFixed(2));
        		 self.data[1].line.push(parseFloat(item.totalRate).toFixed(2));
            });

		},clickBtn:function(){
        	var self=this;
            $("#"+self.btnId).children().on(clickEvent,function(){
       		 var _t=this;
       		 	 self.clickTab($(this).attr("data"));
            	 setBtnClass(_t);
             });

        },clickTab:function(type){
        	 var self = this;
        	 if(self.xAxisData.length>0){
        		 if($(window).width()<360&&self.xAxisData.length>=6){
                 	self.option.xAxis[0].axisLabel.rotate=30;
                 }
        		 self.option.xAxis[0].data = this.xAxisData;
     			self.option.series[0].data = this.data[type].bar;
     			self.option.series[1].data = this.data[type].line;
                 self.chartObj.setOption(self.option, true);
                 $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(i,v,p){ 
                	 var cols=[{name:v.data[0].name,value:v.data[0].value,unit:"小时"}];
                 	cols.push({name:v.data[1].name,value:v.data[1].value,unit:"%"});
                     return {title:v.name,cols:cols};
                 }});
        	 }
        },
        resize: function () {
        	this.chartObj.resize();
        }
    }
    
    /**
     * 考勤类型
     * */
    var checkWorkTypeObj = {
		chartId : 'checkWorkTypeChart',
		chartObj : null,
    	option : getDefaultPieOption({name: "考勤类型", toroidal: true}),
		init : function(organId,time){
    		var self = this;
    		self.chartObj =  echarts.init(document.getElementById(self.chartId));
    		self.getRequestDataFun(organId,time);
    	},
    	getRequestDataFun: function(organId,time){
    		var self = this;
    		var param = {
    			organId : organId,
    			time : time
    		};
    		loading(self.chartId);
    		$.ajax({
    			url : urls.queryCheckWorkTypeLayoutUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				loaded(self.chartId);
    				self.getOption(data);
    			},
    			error : function(){}
    		});
    	},
    	getOption: function(data){
			var self = this;
			if(data.pieList && data.pieList.length > 0){
				self.option.series[0].data = data.pieList;
				self.chartObj.setOption(self.option);
				var legend=[];
				$.each(data.pieList, function(i, item) {
					legend.push('<div>'+
					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
					'<span>'+ item.name + '</span>'+
					'</div>');
				});
				appendLegend($("#" + this.chartId), legend);
				$('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
	            	var cols = [  ];
						$.each(data.pieList, function(i, item) {
							cols.push({
								name : item.name,
								value : item.value+"小时" ,
								rate : "("+item.rate+")"
							});

						});

						return {
							color:colorPie,
							title : "",
							note:"",
							cols : cols
						};            }});
			}else{
				showEmptyTip($('#' + self.chartId));
			}
		},
        resize: function () {
        	this.chartObj.resize();
        }
    }
    
    /**
     * 出勤明细-考勤类型
     * */
    var attendanceDetailCheckWorkTypeObj = {
        organId: null,
        userName: null,
        date: null,
        init: function (organId,time) {
            var self = this;
            self.organId = organId;
//            if (self.date == null) {
//                self.date = dateYear + '' + dateMonth;
//            }
            self.time=time;
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            $.ajax({
                url: urls.queryCheckWorkTypeUrl,
                type: 'post',
                success: function (data) {
                    self.loadGridFun(data);
                },
                error: function () {
                }
            });
        },
        loadGridFun: function (data) {
            var self = this;
            var modelArray = data.colModelArray;
            if (modelArray != null && modelArray.length > 0) {
                modelArray[1].formatter = nameFormatter;
                for (var i = 5; i < modelArray.length ; i++) {
                    modelArray[i].formatter = changeNullFormatter;
                }
                tableGrid.colModelArray = modelArray;
                var arr=[];
                $.each(data.nameArray,function(i,o){
                	arr.push('<span class="grid-th">'+o+'</span>')
                });
                tableGrid.checkWorkTypeNameArray = arr;

                tableGrid.organId = self.organId;
                tableGrid.userName = self.userName;
                tableGrid.init(self.organId,self.time);
            }
        }
    }
    //姓名列
    var nameFormatter = function (value, options, rows) {
        return "<span class='grid-td-high' >" + value + "</span>";
    };

    //其他列
    var changeNullFormatter = function (cellvalue, options, rowObject) {
        if (cellvalue == null) {
            return '0';
        } else {
            return cellvalue;
        }
    };
    var tableOption= {
			url: urls.queryAttendanceDetailUrl,
            datatype: 'json',
            postData: {'organId': reqOrganId,'time':time},
            mtype: 'POST',
//            autowidth: true,
            colNames: [
//                       '<span class="grid-th">姓名</span>', 
//                       '<span class="grid-th">所属机构</span>',
//                       '<span class="grid-th">实出勤</span>', 
//                       '<span class="grid-th">应出勤</span>',
//                       '<span class="grid-th">加班</span>',
//                       '<span class="grid-th">休假</span>'
                       ],
            colModel: [
//                       {
//                           name: 'userName',
//                           index: 'userName',
//                           sortable: false,
//                           align: 'center',
//                           formatter: function (value, options, row) {
//                               if (_.isEmpty(value)) {
//                                   return "";
//                               }
//                               return "<span class='grid-td-high'>"+value+"<span>";
//                           }
//                       },
//                       {name: 'organName', index: 'organName', sortable: false, align: 'center',
//                    	   formatter: function (value, options, row) {
//                               if (_.isEmpty(value)) {
//                                   return "";
//                               }
//                               return "<span class='grid-td'>"+value+"<span>";
//                           }},
//                       {name: 'shouldNum', index: 'shouldNum', sortable: false, align: 'center',
//                    	   formatter: function (value, options, row) {
//                    		  
//                           return "<span class='grid-td'>"+value+"<span>";
//                       }},
//                       {
//                           name: 'actualNum',
//                           index: 'actualNum',
//                           sortable: false,
//                           align: 'center',
//                    	   formatter: function (value, options, row) {
//                    		 
//                           return "<span class='grid-td'>"+value+"<span>";
//                           }
//                       },{
//                           name: 'overtimeNum',
//                           index: 'overtimeNum',
//                           sortable: false,
//                           align: 'center',
//                           formatter: function (value, options, row) {
//                               return "<span class='grid-td'>"+value+"<span>";
//                           }
//                       },{
//                           name: 'operate',
//                           index: 'operate',
//                           sortable: false,
//                           align: 'center',
//                           formatter: function (value, options, row) {
//                               if (_.isEmpty(value)) {
//                                   return "0";
//                               }
//                               return "<span class='grid-td'>"+value+"<span>";
//                           }
//                       }
                ],loadComplete:function(xhr){
                	tableGrid.tableOption.scroll=xhr.total>1;
                },
                scroll: true
        };

	var tableGrid={
			gridId:"#workDetail",
			PanelId:"#workDetailPanel",
			tableOption:tableOption,
			load:false,
			scroll:null,
			init:function(){
				var self=this;
				self.tableOption.colNames=self.checkWorkTypeNameArray,
				self.tableOption.colModel= self.colModelArray,
				$(self.gridId).jqGrid(self.tableOption);
				this.resize();
			    var loadLeft=($(window).width()/2-20-10); 
	            $(self.PanelId).find("#load_workDetail").css('left', loadLeft+"px");
			},
//			resize: function () {
//	        	var self=this;
//	        	if(window.screenFull){
//	        		$(self.gridId).setGridWidth($(self.PanelId).width()+22);
//	        	}else{
//	        		if(this.scroll){
//	        			$(self.gridId).setGridWidth($(self.PanelId).width()+22);
//	        		}else{
//	        			$(self.gridId).setGridWidth($(self.PanelId).width());
//	        		}
//	        		
//	        	}
//	            $(self.gridId).setGridHeight($(self.PanelId).height());
//	        },
	        resize: function () {
	        	var self=this;
	            $(this.gridId).setGridHeight($(self.PanelId).height()-10);
	            //$(this.gridId).setGridWidth($('#trainDetailGridPanel').width()-30);
	            if(!this.load){
	            	this.loadScroll();
	            }
	        },showScroll:function(bool){
	        	if(this.scroll!=null){
	        		if(bool){
	        			$(this.scroll).show();
	        		}else{
	        			$(this.scroll).hide();
	        		}
	        	}
	        },loadScroll:function(){
	        	 var self = this;
	        	 this.load=true;
	        	  var width=$(this.gridId).width()-$(this.PanelId).width();
	              if(width<0){
	              	return;
	              }
	              var scrollWidth=$(self.PanelId).width()-width;
	              var scal=1;
	              if(scrollWidth<50){
	              	scrollWidth=50;
	              	var newScrollMoveWidth=$(window).width()-50-15-15;
	              	scal=newScrollMoveWidth/width;
	              }
	              var div=$("<div class='scrollTable-hide'></div>")
	              $(this.gridId).append(div);
	              this.scroll=$("<div class='scrollTable'></div>")
	              $(this.scroll).width(scrollWidth);
	              $("body").append(self.scroll);
	              var lastX=0;
	              var lastY=0;
	              var scrollX=true;
	              $(this.gridId).on("touchstart",function(e){
	              	var _touch = e.originalEvent.targetTouches[0]; 
	              	lastX= _touch.pageX;
	              	lastY= _touch.pageY;
	              	scrollX=true;
	              });

	              $(this.gridId).on("touchmove",function(e){
	              	var _touch = e.originalEvent.targetTouches[0]; 
	              	var nowX= _touch.pageX;
	              	var nowY= _touch.pageY;
	              	var contentX = ($(div).css("left")).replace('px', '');
	              	var moveX = (nowX-lastX)*1;
	              	var moveY = (nowY-lastY)*1;
	              	  lastX=nowX;
	              	  if(scrollX||Math.abs(moveY)>10){
	              		  scrollX=false;
	              		  return;
	              	  }
	              	  var currLeft=parseInt(contentX) + moveX;
	              	 
	              	  if(currLeft>0||-currLeft>width){
	              		  return;
	              	  }
	              	div.css("left",currLeft+"px");
	              	var scrollLeft=-currLeft*scal+15;
	              	 $(self.scroll).css("left",scrollLeft+"px");
	              	$(self.PanelId).find("table").css('-webkit-transform', 'translate3d(' + currLeft + 'px,0,0)');
	              	
	              });
	        }
	}
    /*==============================================================*/
    /* 页面初始化													*/
    /*==============================================================*/
    topObj.init(reqOrganId,time);
    laborRatioObj.init(reqOrganId,time);
    laborEfficiencyTrendObj.init(reqOrganId,time);
    //laborRatioObj.init(reqOrganId);
    //laborEfficiencyTrendObj.init();
//    $.screenZoom([
//               	constellatoryObj,
//               	bloodObj,
//               	sexObj,
//               	marryObj,
//               	ageObj,
//               	personalityObj
//              ]);
	
});