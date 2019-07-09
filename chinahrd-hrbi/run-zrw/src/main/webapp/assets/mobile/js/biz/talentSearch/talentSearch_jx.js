
require(['jquery',  'echarts', 'echarts/chart/line',"appBase",'underscore'], function ($, echarts) {

	var webRoot = G_WEB_ROOT;
		    var urls = {
		        getPerfChangeUrl: webRoot + '/mobile/talentContrast/getPerfChange.do',          //获取员工绩效信息
		      
		    };
		  var empId=$("#empId").val();  
		  var  perfEnum=["一星","二星","三星","四星","五星"]
		   
		    /**
		     * 个人绩效
		     */
		    var perfObj = {
		            chartId: 'chart',
		            isQuarter: false,
		            isFirst: true,
		            option:{
		            	animation:animation,
			            grid: {
			                borderWidth: 0,
			                x: 40,
			                y: 10,
			                x2: 10,
			                y2: 40
			            },
			            calculable: false,
			            xAxis: [
			                {
			                    boundaryGap: false,
			                    type: "category",
			                    splitLine: {
			                        color: ['#ccc'],
			                        width: 0,
			                        type: 'dashed'
			                    },
			                    axisLine: {
			                        lineStyle: {
			                            width: 0,
			                            color: '#BCBCBC'
			                        }
			                    },
			                    axisLabel: {
			                        rotate: 30,
			                    },
			                    axisTick: false,
			                    data: []
			                }
			            ],
			            yAxis: [
			                {
			                    axisTick: false,
			                    boundaryGap: false,
			                    type: "category",
			                    splitLine: {
			                        color: ['#ccc'],
			                        width: 0,
			                        type: 'dashed'
			                    },

			                    axisLine: {
			                        onZero: false,
			                        lineStyle: {
			                            width: 0,
			                            color: '#BCBCBC'
			                        }
			                    },axisLabel:{
			                    	textStyle:{}
			                    },
			                    data: []
			                },
			                {
			                    boundaryGap: false,
			                    type: "value",
			                    show: false,
			                    splitLine: false,
			                    splitNumber: 1,
			                    min: 1, //最小1星
			                    max: perfEnum.length,         //TODO 此处根据不同公司的职级划分来定义
			                    splitNumber: perfEnum.length,
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
			                    smooth: false, //不用平滑模式
			                    yAxisIndex: 1,
			                    showAllSymbol: true,
			                    itemStyle: {
			                        normal: {
			                            label: {
			                                show: false
			                            },
			                            lineStyle: {
			                                color: '#86D7C6'
			                                // ,type: 'dotted'
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
		                $.post(urls.getPerfChangeUrl, {'empId': empId}, function (rs) {
		                    if (_.isNull(rs)) {
		                        return;
		                    }
		                    self.extendData(rs);
		                });
		            },
		            initChart: function (xAxisData, seriesData) {
		            	   var self = this;
		                var chartObj = echarts.init(document.getElementById(self.chartId));
		                var xAxisDataNew = [];
		                $.each(xAxisData, function (i, obj) {
		                    var str = obj.substring(0, 4);
		                    str += obj.substring(5, 6);
		                    xAxisDataNew.push(str);
		                })
		                self.option.yAxis[0].data=perfEnum;
		                self.option.xAxis[0].data=xAxisDataNew;

		                self.option.series[0].data = seriesData;
		                chartObj.setOption(self.option, true);
	
		            },
		            extendData: function (source) {
		                var self = this;
		                var bool = false;
		                if(_.isEmpty(source)){
		                	return;
		                }
		                var newSource = [];

		                for (var i = 0; i < source.length; i++) {
		                    newSource[i] = source[source.length - 1 - i];
		                }

		                var chartData={xAxisYearData:[],xAxisQuarterData:[],seriesData:[]};
		                $.each(newSource, function (i, obj) {
		                    var yearMonth = obj.yearMonth.toString();
		                    var month = parseInt(yearMonth.substring(4, yearMonth.length));
		                    if (month > 1 && month <= 3 || month > 7 && month <= 9) {
		                        bool = true;
		                    }
		                    chartData.xAxisYearData.push(obj.rankingYear);
		                    chartData.xAxisQuarterData.push(obj.rankingQuarter);
		                    chartData.seriesData.push(obj.perfKey);
		                });
		                self.isQuarter = bool;
		                self.initChart(bool ? chartData.xAxisQuarterData : chartData.xAxisYearData, chartData.seriesData);


		            }
		        };
		    perfObj.init(empId);
	    
	});