
require(['jquery',  'echarts', 'echarts/chart/line', 'underscore',"appBase",'chartTooltip'], function ($, echarts) {

	var webRoot = G_WEB_ROOT;
		    var urls = {
		        getEmpInfoUrl: webRoot + '/mobile/talentContrast/getEmpInfo.do',               //获取员工信息
		        getPerfChangeUrl: webRoot + '/mobile/talentContrast/getPerfChange.do',          //获取员工绩效信息
		        getJobChangeUrl: webRoot + '/mobile/talentContrast/getJobChange.do',             //获取工作异动相关信息
		        getSearchEmpUrl: webRoot + '/mobile/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
		        getEvalReportUrl: webRoot + '/mobile/talentContrast/getEvalReport.do',          //获取测试信息
		        getDepartChangeUrl: webRoot + '/mobile/talentContrast/getDepartChange.do',      //获取本公司经历信息
		        getPastResumeUrl: webRoot + '/mobile/talentContrast/getPastResume.do'              //获取过往履历信息
		    };
		  var empId=$("#empId").val();  

		  var jobChangeObj = {
			    	chartId: 'chartId',
			    	option:{
			    		animation:animation,
			    	       grid: {
				                borderWidth: 0,
				                x: 40,
				                y: 20,
				                x2: 10,
				                y2: 40
				            },
			            calculable: false,
			            xAxis: [
			                {
			                    boundaryGap: true,
			                    type: "category",
//			                    name: "工作异动",
			                    nameTextStyle: {
			                        color: '#000000'
			                    },axisLabel:{
			                    	textStyle:{}
			                    },
			                    splitLine: {
			                        color: ['#ccc'],
			                        width: 1,
			                        type: 'dashed'
			                    },
			                    axisLine: {
			                        lineStyle: {
			                            color: '#BCBCBC'
			                        }
			                    }, axisTick: {
			                    	show: false
			                    },
			                    data: []
			                }
			            ],
			            yAxis: [
			                {
			                    boundaryGap: false,
			                    type: "category",
//			                    name: "职级",
			                    nameTextStyle: {
			                        color: '#000000'
			                    },
			                    splitLine: {
			                        color: ['#ccc'],
			                        width: 1,
			                        type: 'dashed'
			                    },axisLabel:{
			                    	textStyle:{}
			                    },
			                    axisTick: {
			                        show: false
			                    },
			                    axisLine: {
//			                            onZero: false,
			                        lineStyle: {
			                            color: '#BCBCBC'
			                        }
			                    },
			                    data: []
			                },
			                {
			                    type: "value",
			                    show: false,
			                    splitLine: false,
			                    min: 1,
			                    max: 5,         //TODO 此处根据不同公司的职级划分来定义
			                    splitNumber: 5,
			                    axisLine: {
			                        lineStyle: {
			                            color: '#BCBCBC'
			                        }
			                    }
			                }
			            ],
			            series: [
			                {
			                    type: "line",
			                    smooth: false,
			                    yAxisIndex: 1,
			                    itemStyle: {
			                        normal: {
			                            label: {
			                                show: true,
			                                textStyle: {
			                                    color: '#000000'
			                                },
			                                formatter: function (a) {
			                                    var data = a.data.data;
			                                    return data.rankName + '\n' + data.positionName;
			                                }
			                            },
			                            lineStyle: {
			                                color: '#86D7C6'
			                                // type: 'dotted'
			                            },
			                            color: '#86D7C6'
			                        }
			                    },
			                    data: []
			                }
			            ]
			        },
			        init: function (empId) {
			            var self = this;
			            $.post(urls.getJobChangeUrl, {'empId': empId}, function (rs) {
			                if (_.isEmpty(rs)) {
			                    return;
			                }
			                self.initChart(rs);
			            });
			        },
			        initChart: function (source) {
			            var self = this;
			            var filterData = {};
			            for (var i = source.length - 1; i >= 0; i--) {
			                //10/22，跟xn,sy确定以季度为单位，一个季度有多条数据，取最后一条
			                var obj = source[i];
			                var timeKey = self.getYearQuarter(obj.changeDate);
			                if(filterData[timeKey]==undefined){
			                	filterData[timeKey]=[];
			                }
			                filterData[timeKey].push(obj);
			            }
			            self.filterData = filterData;
			            var timeArr = self.packTimeArr(filterData);
			            var seriesData = self.getSeriesData(filterData, timeArr);
			            
			            var chartObj = echarts.init(document.getElementById(self.chartId ));
		                var xAxisDataNew = [];
		                self.option.yAxis[0].data=['1', '2', '3', '4', '5'];
		                self.option.series[0].itemStyle.normal.label.formatter=function (a, b, c) {

	                    };
		                self.option.xAxis[0].data = timeArr;
		                self.option.series[0].data = seriesData;
		                chartObj.setOption(self.option, true);
		                $('#' + self.chartId).chartTooltip({chart:chartObj,regularX:true,formatter:function(index,v,p){
		                	var data=v.data[0].value.data;
	                    	var cols=[];
	                    	$.each(data,function(i,item){
	                    		 cols.push({name:item.changeDate,value:item.positionName+" "+item.rankName});
	                    	});
		                	

	                        return {title:v.name,cols:cols,valueSize:13};
	                    }});
			        },
			        getSeriesData: function (filterData, timeArr) {
			            var seriesData = [];
			            $.each(timeArr, function (i, time) {
			                var obj = filterData[time];
			                var rsData;
			                if (!obj) {
			                    var preData = _.clone(seriesData[i - 1]);
			                    preData.symbolSize = 0;
			                    rsData = preData;
			                } else {
			                    //-1是为了在坐标上正确显示，如A1.2,应该是显示在坐标轴[0,1]区间
			                    var value = parseFloat(obj[obj.length-1].rankName.substr(-3, 3)) ;
			                    rsData = {value: value, data: obj};
			                }
			                seriesData.push(rsData);
			            });
			            return seriesData;
			        },
			        //遍历时间集合，生成每一个区间。如：开始时间 = 2014Q4 ,结束时间 = 2015Q2,则生成[2014Q4,2015Q1,2015Q2]
			        packTimeArr: function (filterData) {
			            var filterTime = _.keys(filterData);
			            return filterTime;
			        },
			        //转换成年度季度，如2015Q1
			        getYearQuarter: function (date) {
			            var ymd = date.split('-');
			            var month = parseInt(ymd[1]);
			            var quarter;
			            if (month <= 3) {
			                quarter = 1;
			            } else if (month <= 6) {
			                quarter = 2;
			            } else if (month <= 9) {
			                quarter = 3;
			            } else {
			                quarter = 4;
			            }
			            return ymd[0] + 'Q' + quarter;
			        }
			    };
		  jobChangeObj.init(empId);
	    
	});