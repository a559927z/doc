require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'bootstrap', 'jgGrid',
    'underscore', 'utils', 'timeLine2'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getTrainCostYearUrl: webRoot + '/trainBoard/getTrainCostYear.do',       //获取当前年度培训费用以及预算率
        getTrainPlanUrl: webRoot + '/trainBoard/getTrainPlan.do',               //获取当前年度培训计划完成率
        getTrainCoverUrl: webRoot + '/trainBoard/getTrainCover.do',             //获取培训覆盖率
        getTrainingCostUrl: webRoot + '/trainBoard/getTrainingCost.do',         //培训费用统计
        getPerCapitaCostUrl: webRoot + '/trainBoard/getPerCapitaCost.do',       //人均培训费用统计
        getPlanCompletionUrl: webRoot + '/trainBoard/getPlanCompletion.do',     //培训计划完成率
        getPerCapitaHoursUrl: webRoot + '/trainBoard/getPerCapitaHours.do',     //下级组织人均学时对比
        getExpenseStatisticsUrl: webRoot + '/trainBoard/getExpenseStatistics.do',//费用统计
        getYearCostUrl: webRoot + '/trainBoard/getYearCost.do',                 //费用年度趋势图
        getSubOrganCostUrl: webRoot + '/trainBoard/getSubOrganizationCost.do',   //下级组织培训费用对比
        getSubOrganAvgCostUrl: webRoot + '/trainBoard/getSubOrganizationAvgCost.do',//下级组织培训平均费用对比
        getImplementationUrl: webRoot + '/trainBoard/getImplementation.do',         //实施统计
        getSubOrganPassengersUrl: webRoot + '/trainBoard/getSubOrganizationPassengers.do', //下级组织培训人次对比
        getSubOrganCoverUrl: webRoot + '/trainBoard/getSubOrganizationCover.do',        //下级组织培训覆盖率对比
        getTrainingTypeUrl: webRoot + '/trainBoard/getTrainingType.do',                  //培训类型次数统计
        findTrainingTypeRecordUrl: webRoot + '/trainBoard/findTrainingTypeRecord.do',         //查询培训分类明细
        getTrainingSatisfactionUrl: webRoot + '/trainBoard/getTrainingSatisfaction.do',   //培训满意度年度统计
        getInternalTrainerUrl: webRoot + '/trainBoard/getInternalTrainer.do',           //下级组织内部讲师统计
        getInternalTrainerListUrl: webRoot + '/trainBoard/getInternalTrainerList.do',   //内部讲师清单
        findTrainingRecordUrl: webRoot + '/trainBoard/findTrainingRecord.do'           //培训记录
    };

    $(win.document.getElementById('tree')).next().show();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    //var ecConfig = require('echarts2.2.7/config');
    var TextShape = require('zrender/shape/Text');
    var pieDefaultColor = ['#0b98e0', '#00bda9', '#4573a7', '#92c3d4', '#de6e1b', '#ff0084', '#af00e1', '#8d55f6', '#6a5888', '#2340f3'];

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        var curTopTabId = getActiveTabId(".leftListDiv");
        changeData(curTopTabId);
    };


    /**
     * 管理建议与备忘
     * @type {{init: timeLineObj.init, getOption: timeLineObj.getOption}}
     */
    var timeLineObj = {
        init: function (organId) {
            var self = this;
            self.organizationId = organId;
            $('#timeLine').timeLine(self.getOption());	//初始化
        },
        getOption: function () {
            var organizationId = this.organizationId;
            var quotaId = $('#quotaId').val();
            //参数配置
            var options = {
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
        }
    }

    var showDataZoomNumber = 4;
    var optionGrid = {
            borderWidth: 0,
            x: 55,
            x2: 15,
            y: 25,
            y2: 70
        };

        var dataZoom = {
            show: true,
            realtime: true,
            height: 20,
            start: 60,
            end: 0,
            showDetail: false,
            y: 320
        };
        
        
    /**
     * 培训费用统计
     * @type {{chartId: string, chartObj: null, init: trainCostObj.init, requestData: trainCostObj.requestData, render: trainCostObj.render}}
     */
    var trainCostOption = {
    	tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                      type: 'line',
                      lineStyle:{
                      	color: '#999999',
                      	type: 'dashed',
                      	width: 1
                      }
                  },
                  formatter : function(param){
                  	return '<span class="tooltip-title">' 
                  		+ param[0].name + '</span><br/>'
                  	 	+ '<span class="tooltip-content-span">' 
                  	 	+ param[0].seriesName
                  		+ ' : ' + param[0].value + '万元</span>';
                  }
        },
        grid: optionGrid,
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                	show : true,
                	lineStyle: {
                        color: '#666666'
                    }
                },
                data: [],
                axisLine: {
                    show: true ,
                    onZero: false,
                    lineStyle: {
                        color: "#666666",
                        width: 1
                    }
                },
                axisLabel: {
                    //rotate: 30,
                    itemStyle: {
                        color: '#666666'
                    }, textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"                    }
                }
            }
        ],
        color: ['#23C6C8'],
        yAxis: [
            {
            	type: 'value',
            	name: "（万元）",
            	splitNumber:5,
            	 splitLine: {
                 	show : true,
                 	lineStyle : {
                 		color : '#e4e4e4'
                 	}
                 },
                axisLine: {
                	 show: true,
                     onZero: false,
                     lineStyle: {
                         width: 1,
                         color: '#666666'
                     }
                }
            }
        ],
        series: [
            {
                name: '辅助',
                type: 'bar',
                stack: '培训费用统计',
                itemStyle: {
                    normal: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    },
                    emphasis: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            },
            {
                type: 'bar',
                stack: '培训费用统计',
                name: '培训费用统计',
                barWidth: 30,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: 'black'
                            }
                        },
                        color: function (a) {
                            if (a.dataIndex == 0) {
                                return "#A6A6A6";
                            } else if (a.dataIndex == 1) {
                                return "#1C84C6";
                            } else {
                                return "#23C6C8";
                            }
                        }
                    }
                },
                data: []
            }
        ]
    };
    var trainCostObj = {
        chartId: "trainCostChart",
        chartObj: null,
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != orgId) {
                self.requestData(orgId);
            }
            self.resizeChart();
        },
        requestData: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getTrainingCostUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            if (_.isNull(result.budgetCost) || _.isNull(result.cost)) {
                hideChart(self.chartId, true);
                return;
            }
            var data1 = [result.budgetCost, result.cost], data0 = [0, 0];
            var xdata = ['当年预算', '当年累计'];
            var list = result.list;
            var sumVal = 0;
            _.each(list, function (item, i) {
                data0.push(sumVal);
                data1.push(Tc.formatFloat(item.cost));
                xdata.push(item.yearMonth);
                sumVal += item.cost;
            });
            if(result.list.length > showDataZoomNumber){
            	trainCostOption.dataZoom = dataZoom;
            } else {
            	trainCostOption.dataZoom = null;
            }
            /*if (data1.length > 8) {   //加斜体
                trainCostOption.xAxis[0].axisLabel.rotate = 30;
            }*/
            var max = result.cost > result.budgetCost ? result.cost : result.budgetCost;
            self.chartObj.clear();
            trainCostOption.xAxis[0].data = xdata;
            trainCostOption.yAxis[0].max = max;
            trainCostOption.series[0].data = data0;
            trainCostOption.series[1].data = data1;
            self.chartObj.setOption(trainCostOption, true);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /**
     * 人均培训费用统计
     * @type {{chartId: string, chartObj: null, init: avgpriceCostObj.init, requestData: avgpriceCostObj.requestData, render: avgpriceCostObj.render}}
     */
    var avgpriceCostOption = {
       tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'line',
                    lineStyle:{
                    	color: '#999999',
                    	type: 'dashed',
                    	width: 1
                    }
                },
                formatter : function(param){
                	return '<span class="tooltip-title">' 
                		+ param[0].name + '</span><br/>'
                	 	+ '<span class="tooltip-content-span">' 
                	 	+ param[0].seriesName
                		+ ' : ' + param[0].value + '万元</span>';
                }
            },
        grid: optionGrid,
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                	show : true,
                	lineStyle: {
                        color: '#666666'
                    }
                },
                data: [],
                axisLine: {
                    show: true ,
                    onZero: false,
                    lineStyle: {
                        color: "#666666",
                        width: 1
                    }
                },
                axisLabel: {
                    //rotate: 30,
                    itemStyle: {
                        color: '#666666'
                    }, textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"                    }
                }
            }
        ],
        color: ['#3285C7'],
        yAxis: [
            {
                type: 'value',
                name: "（万元）",
                splitNumber:5,
           	   splitLine: {
                	show : true,
                	lineStyle : {
                		color : '#e4e4e4'
                	}
                },
               axisLine: {
               	 show: true,
                    onZero: false,
                    lineStyle: {
                        width: 1,
                        color: '#666666'
                    }
               }
            }
        ],
        series: [
            {
                type: 'bar',
                barWidth: 30,
                name:"人均培训费用统计",
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                },
                data: []
            }
        ]
    };
    var avgpriceCostObj = {
        chartId: 'avgpriceCostChart',
        chartObj: null,
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != orgId) {
                self.requestData(orgId);
            }
            self.resizeChart();
        },
        requestData: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getPerCapitaCostUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this, data = [], xdata = [];
            $.each(result, function (i, o) {
                data.push(Tc.formatFloat(o.avgCost));
                xdata.push(o.yearMonth);
            });
            
            if(data.length > showDataZoomNumber){
            	avgpriceCostOption.dataZoom = dataZoom;
            } else {
            	avgpriceCostOption.dataZoom = null;
            }
            if (data.length > 0) {
                self.chartObj.clear();
                /*if (data.length > 6) {   //加斜体
                    completionRateOption.xAxis[0].axisLabel.rotate = 30;
                }*/
                avgpriceCostOption.xAxis[0].data = xdata;
                avgpriceCostOption.series[0].data = data;
                self.chartObj.setOption(avgpriceCostOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /**
     * 培训计划完成率
     * @type {{chartId: string, chartObj: null, init: avgpriceCostObj.init, requestData: avgpriceCostObj.requestData, render: avgpriceCostObj.render}}
     */
    var completionRateOption = {
        tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'line',
                    lineStyle:{
                    	color: '#999999',
                    	type: 'dashed',
                    	width: 1
                    }
                },
                formatter : function(param){
                	return '<span class="tooltip-title">' 
                		+ param[0].name + '</span><br/>'
                	 	+ '<span class="tooltip-content-span">' 
                	 	+ param[0].seriesName
                		+ ' : ' + Tc.formatFloat(param[0].value * 100)  + '%</span>';
                }
        },
        grid:optionGrid,
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                	show : true,
                	lineStyle: {
                        color: '#666666'
                    }
                },
                data: [],
                axisLine: {
                    show: true ,
                    onZero: false,
                    lineStyle: {
                        color: "#666666",
                        width: 1
                    }
                },
                axisLabel: {
                    //rotate: 30,
                    itemStyle: {
                        color: '#666666'
                    }, textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                min: 0,
                max: 1,
                splitNumber: 5,
           	 	splitLine: {
              	show : true,
              	lineStyle : {
              		color : '#e4e4e4'
              	}
           	 	},
                axisLine: {
               	 show: true,
                 onZero: false,
                 lineStyle: {
                     width: 1,
                     color: '#666666'
                 }
                },
                axisLabel: {
                    show: true,
                    formatter: function (value) {
                        return Tc.formatFloat(value * 100) + '%';
                    }
                }
            }
        ],
        series: [
            {
                type: 'bar',
                name: '培训计划完成率',
                barWidth: 30,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return Tc.formatFloat(i.value * 100) + '%';
                            }
                        },
                        color: function (a) {
                            if (a.dataIndex == 0) {
                                return "#1C84C6";
                            } else {
                                return "#23C6C8";
                            }
                        }
                    }
                },
                data: []
            }
        ]
    };
    var completionRateObj = {
        chartId: "completionRateChart",
        chartObj: null,
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != orgId) {
                self.requestData(orgId);
            }
            self.resizeChart();
        },
        requestData: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getPlanCompletionUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            if (_.isNull(result.cumulative)) {
                hideChart(self.chartId, true);
                return;
            }
            var data = [result.cumulative], xdata = ['当年累计'];
            var list = result.list;
            $.each(list, function (i, o) {
                data.push(o.completeRate);
                xdata.push(o.yearMonth);
            });
            self.chartObj.clear();
            if(result.list.length > showDataZoomNumber){
            	completionRateOption.dataZoom = dataZoom;
            } else {
            	completionRateOption.dataZoom = null;
            }
         /*   if (data.length > 6) {   //加斜体
                completionRateOption.xAxis[0].axisLabel.rotate = 30;
            }*/
            completionRateOption.xAxis[0].data = xdata;
            completionRateOption.series[0].data = data;
            self.chartObj.setOption(completionRateOption);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /**
     * 下级组织人均学时对比
     * @type {{chartId: string, chartObj: null, init: avgpriceCostObj.init, requestData: avgpriceCostObj.requestData, render: avgpriceCostObj.render}}
     */
    var subOrganSchoolOption = {
        tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'line',
                    lineStyle:{
                    	color: '#999999',
                    	type: 'dashed',
                    	width: 1
                    }
                },
                formatter : function(param){
                	return '<span class="tooltip-title">' 
                		+ param[0].name + '</span><br/>'
                	 	+ '<span class="tooltip-content-span">' 
                	 	+ param[0].seriesName
                		+ ' : ' + param[0].value + '小时</span>';
                }
        },
        grid: optionGrid,
        toolbox: false,
        color: ["#3285C7"],
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true ,
                onZero: false,
                lineStyle: {
                    color: "#666666",
                    width: 1
                }
            },
            axisLabel: {
                //rotate: 30,
                itemStyle: {
                    color: '#666666'
                }, textStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"                    }
            },
            axisTick: {
            	show : true,
            	lineStyle: {
                    color: '#666666'
                }
            },
            data: []
        }],
        yAxis: [
            {
                type: "value",
                name: "（小时）",
                splitNumber:5,
           	    splitLine: {
                	show : true,
                	lineStyle : {
                		color : '#e4e4e4'
                	}
                },
               axisLine: {
               	 show: true,
                    onZero: false,
                    lineStyle: {
                        width: 1,
                        color: '#666666'
                    }
               }
            }
        ],
        series: [
            {
                clickable: false,
                type: "bar",
                name: '下级组织人均学时对比',
                barWidth: 30,
                data: [],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: "#000000"
                            }
                        }
                    }
                }
            }
        ],
        dataZoom: {}
    };
    var subOrganSchoolObj = {
        chartId: 'subOrganSchoolChart',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
            self.resizeChart();
        },
        requestData: function (organId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getPerCapitaHoursUrl, {organId: organId}, function (result) {
                self.organId = organId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.drawDate(result.beginDate, result.endDate);
                    self.render(result.list);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (data) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var list = _.sortBy(data, function (item) {
                return -item.hours;
            });
            $.each(list, function (i, item) {
                xAxisData.push(item.organizationName);
                seriesData.push(item.hours);
            });
            var len = xAxisData.length;
            if (len > 0) {
                var num = 4, dataZoom = {};//4个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                        _.each(xAxisData, function (item, i) {
                            xAxisData[i] = item.length > 6 ? item.substring(0, 5) + '...' : item;
                        });
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 20,
                        end: end,
                        showDetail: false,
                        zoomLock: true
                    };
                }
                self.chartObj.clear();
                subOrganSchoolOption.dataZoom = dataZoom;
                subOrganSchoolOption.xAxis[0].data = xAxisData;
                subOrganSchoolOption.series[0].data = seriesData;
                self.chartObj.setOption(subOrganSchoolOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        drawDate: function (startDate, endDate) {
            var self = this;
            var date = '(' + startDate + '-' + endDate + ')';
            $('#subOrganSchoolDate').text(date);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    };

    /**
     * 费用年度趋势图
     * @type {{grid: {x: number, y: number, x2: number, borderWidth: number}, xAxis: *[], yAxis: *[], calculable: boolean, series: *[]}}
     */
    var yearTrendOption = {
        grid: {
            x: 55,
            x2: 15,
            borderWidth: 0
        },
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                    lineStyle: {
                        color: '#cecece'
                    }
                },
                data: [],
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: '#000000'
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                axisLine: false,
                splitLine: false
            }
        ],
        calculable: false,
        series: [
            {
                type: "line",
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return i.name == yearTrendObj.year ? '' : i.value;
                            }
                        }
                    },
                    emphasis: {
                        label: false
                    }
                },
                clickable: false,
                symbolSize: 0,
                data: [],
                markPoint: {
                    itemStyle: {
                        normal: {
                            color: '#333',
                            borderWidth: 30
                        }
                    },
                    clickable: false,
                    data: []
                }
            }
        ]
    }
    var yearTrendObj = {
        chartId: "yearTrendChart",
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
            self.resizeChart();
        },
        requestData: function (organId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getYearCostUrl, {organId: organId}, function (result) {
                self.organId = organId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            var data = [], xAxisData = [], pointData = [];
            var max = _.max(result, 'cost').cost;
            _.each(result, function (item, i) {
                var year = item.year + '年';
                data.push({name: year, value: Tc.formatFloat(item.cost), key: i});
                xAxisData.push(year);
                if (i == result.length - 1) {
                    self.year = year;
                    pointData.push({name: '标注', value: item.cost, xAxis: year, yAxis: item.cost + max / 9});
                }
            });
            if (data.length > 0) {
                self.chartObj.clear();
                yearTrendOption.xAxis[0].data = xAxisData;
                yearTrendOption.series[0].data = data;
                yearTrendOption.series[0].markPoint.data = pointData;
                self.chartObj.setOption(yearTrendOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /**
     * 下级组织培训费用对比
     */
    var subOrganContrastOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        toolbox: false,
        color: ["#3285C7"],
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [
            {
                type: "value",
                axisLine: {
                    show: false
                },
                axisLabel: {
                    formatter: "{value}",
                    textStyle: {
                        color: "rgb(0, 0, 0)",
                        fontSize: 13
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: "solid",
                        color: "rgb(204, 204, 204)"
                    }
                }
            }
        ],
        series: [
            {
                clickable: false,
                type: "bar",
                barWidth: 30,
                data: [],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: "#000000"
                            }
                        }
                    }
                }
            }
        ],
        dataZoom: {}
    };
    var subOrganContrastObj = {
        chartId: "subOrganContrastChart",
        chartObj: null,
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            var _childs = $('#' + self.chartId).prev('.bottom-div-right').children();
            if (self.organId != orgId) {
                var _child = _.find(_childs, function (obj) {
                    return $(obj).hasClass('select');
                });
                var toggle = $(_child).data('toggle');
                if (toggle == 'avgCost') {
                    self.requestAvgCost(orgId);
                } else {
                    self.requestTotalCost(orgId);
                }
            }
            self.resizeChart();

            _childs.click(function () {
                var $this = $(this);
                if ($this.hasClass('select')) {
                    return;
                }
                $this.siblings().removeClass('select');
                $this.addClass('select');
                var toggle = $this.data('toggle');
                if (toggle == 'avgCost') {
                    self.requestAvgCost(self.organId);
                } else {
                    self.requestTotalCost(self.organId);
                }
            });
        },
        requestTotalCost: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSubOrganCostUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.drawDate(result.beginDate, result.endDate);
                    self.render(result.list, 0);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        requestAvgCost: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSubOrganAvgCostUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result, 1);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result, type) {
            var self = this;
            var data = [], xdata = [];
            var list = _.sortBy(result, function (item) {
                return -(type == 0 ? item.cost : item.avgCost);
            });
            $.each(list, function (i, o) {
                data.push(type == 0 ? o.cost : o.avgCost);
                xdata.push(o.organizationName);
            });
            var len = xdata.length;
            if (len > 0) {
                var num = 4, dataZoom = {};//3个以上显示滚动条
                if (xdata.length > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                        _.each(xdata, function (item, i) {
                            xdata[i] = item.length > 6 ? item.substring(0, 5) + '...' : item;
                        });
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 20,
                        end: end,
                        showDetail: false,
                        zoomLock: true
                    };
                }
                self.chartObj.clear();
                subOrganContrastOption.dataZoom = dataZoom;
                subOrganContrastOption.xAxis[0].data = xdata;
                subOrganContrastOption.xAxis[0].data = xdata;
                subOrganContrastOption.series[0].data = data;
                self.chartObj.setOption(subOrganContrastOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        drawDate: function (startDate, endDate) {
            var self = this;
            var date = '(' + startDate + '-' + endDate + ')';
            $('#subOrganContrastDate').text(date);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /***
     * 下级组织培训人次对比
     * @type {{toolbox: boolean, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {x: number, y: number, x2: number, borderWidth: number}, dataZoom: {}}}
     */
    var peopleCompareOption = {
        toolbox: false,
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [
            {
                type: "value",
                axisLine: {
                    show: false
                },
                axisLabel: {
                    formatter: "{value}",
                    textStyle: {
                        color: "rgb(0, 0, 0)",
                        fontSize: 13
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: "solid",
                        color: "rgb(204, 204, 204)"
                    }
                }
            }
        ],
        series: [
            {
                clickable: false,
                type: "bar",
                barWidth: 30,
                barGap: 50,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: "#000000"
                            }
                        }
                    }
                },
                data: []
            }
        ],
        color: ["#3285C7"],
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        dataZoom: {}
    };
    var peopleCompareObj = {
        chartId: 'peopleCompareChart',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
            self.resizeChart();
        },
        requestData: function (organId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSubOrganPassengersUrl, {organId: organId}, function (result) {
                self.organId = organId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.drawDate(result.beginDate, result.endDate);
                    self.render(result.list);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var list = _.sortBy(result, function (item) {
                return -item.frequency;
            });
            _.each(list, function (item) {
                xAxisData.push(item.organizationName);
                seriesData.push(item.frequency);
            });
            var len = xAxisData.length;
            if (len > 0) {
                var num = 4, dataZoom = {};//3个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                        _.each(xAxisData, function (item, i) {
                            xAxisData[i] = item.length > 6 ? item.substring(0, 5) + '...' : item;
                        });
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 20,
                        end: end,
                        showDetail: false,
                        zoomLock: true   //当设置为true时选择区域不能伸缩
                    };
                }
                //if (xAxisData.length > 6) {   //加斜体
                //    peopleCompareOption.xAxis[0].axisLabel.rotate = 30;
                //}
                self.chartObj.clear();
                peopleCompareOption.dataZoom = dataZoom;
                peopleCompareOption.xAxis[0].data = xAxisData;
                peopleCompareOption.series[0].data = seriesData;
                self.chartObj.setOption(peopleCompareOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        drawDate: function (startDate, endDate) {
            var self = this;
            var date = '(' + startDate + '-' + endDate + ')';
            $('#peopleCompareDate').text(date);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    };

    /***
     * 下级组织培训覆盖率对比
     * @type {{chartId: string, chartObj: null, chartOption, gridId: string, gridObj: null, init: dismissContrastObj.init, initLineLengend: dismissContrastObj.initLineLengend, initData: dismissContrastObj.initData, getRequestData: dismissContrastObj.getRequestData, generateChart: dismissContrastObj.generateChart, packMarkLineData: dismissContrastObj.packMarkLineData, generateGrid: dismissContrastObj.generateGrid, resizeGrid: dismissContrastObj.resizeGrid, resizeChart: dismissContrastObj.resizeChart}}
     */

    var coverageContrastOption = {
        grid: {
            x: 55,
            y: 40,
            x2: 15,
            borderWidth: 0
        },
        dataZoom: {},
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: false,
            axisLine: {
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisLabel: {
                formatter: '{value}%'
            }
        }],
        color: ['#3285C7'],
        series: [{
            type: "bar",
            clickable: false,
            barWidth: 30,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: '#333'
                        },
                        formatter: function (i) {
                            return i.value + '%';
                        }
                    }
                },
                emphasis: {
                    label: false
                }
            },
            data: [],
            markLine: {
                clickable: false,
                symbolSize: [0, 0],
                itemStyle: {
                    normal: {
                        color: '#6FB12D',
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
        }]
    }
    var coverageContrastObj = {
        chartId: 'coverageContrastChart',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
            self.resizeChart();
        },
        //因为图表中的横线没有图例，故用zrender画一个
        initLineLengend: function () {
            var _ZR = this.chartObj.getZrender();
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 65,
                    y: 15,
                    color: '#6FB12D',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 18px 微软雅黑',
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 40,
                    y: 15,
                    color: '#666',
                    text: '公司覆盖率',
                    textAlign: 'left',
                    textFont: 'normal 13px 微软雅黑'
                },
                hoverable: false
            }));
            _ZR.refresh();
        },
        requestData: function (organId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSubOrganCoverUrl, {organId: organId}, function (result) {
                self.organId = organId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.drawDate(result.beginDate, result.endDate);
                    self.render(result.list, result.companyCover);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (subData, curRate) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var list = _.sortBy(subData, function (item) {
                return -item.coverageRate;
            });
            var max = 0;
            _.each(list, function (item) {
                category.push(item.organizationName);
                barDataArr.push({name: item.organizationName, value: Tc.formatFloat(item.coverageRate * 100)});
                if (max < item.coverageRate) {
                    max = item.coverageRate;
                }
            });
            max = max > curRate ? max : curRate;
            var len = category.length;
            if (len > 0) {
                var num = 4, dataZoom = {};//3个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                        _.each(category, function (item, i) {
                            category[i] = item.length > 6 ? item.substring(0, 5) + '...' : item;
                        });
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 20,
                        end: end,
                        showDetail: false,
                        zoomLock: true   //当设置为true时选择区域不能伸缩
                    };
                }
                self.chartObj.clear();
                coverageContrastOption.dataZoom = dataZoom;
                coverageContrastOption.yAxis[0].max = (max + max / 10) * 100;
                coverageContrastOption.xAxis[0].data = category;
                coverageContrastOption.series[0].data = barDataArr;
                coverageContrastOption.series[0].markLine.data = self.packMarkLineData(barDataArr.length, curRate * 100);
                self.chartObj.setOption(coverageContrastOption, true);
                self.initLineLengend();
            } else {
                hideChart(self.chartId, true);
            }
        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}]];
        },
        drawDate: function (startDate, endDate) {
            var self = this;
            var date = '(' + startDate + '-' + endDate + ')';
            $('#coverageContrastDate').text(date);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    };

    /*==============================================================*/
    /* 培训类型次数统计
     /*==============================================================*/
    var trainTypeNumOption = {
        color: pieDefaultColor,
        calculable: false,
        series: [{
            type: 'pie',
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        }
                    },
                    labelLine: {
                        length: 1
                    }
                }
            },
            radius: '60%',
            data: []
        }]
    };
    var trainTypeGridOption = {
        url: urls.findTrainingTypeRecordUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,
        colNames: ['培训课程', '培训时间', '状态', '参培人数', '覆盖率', '主办方'],
        colModel: [
            {name: 'courseName', width: 250, sortable: false, align: 'left'},
            {
                name: 'startDate', width: 110, sortable: false, align: 'left',
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }
                    return '开始:' + value + '&nbsp;&nbsp;<br>结束:' + (row.endDate == null ? '-' : row.endDate);
                }
            },
            {
                name: 'status', width: 80, fixed: true, sortable: false, align: 'center',
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }
                    return value == 1 ? '已完成' : '进行中';
                }
            },
            {name: 'trainNum', width: 60, fixed: true, sortable: false, align: 'center'},
            {
                name: 'coverageRate', width: 60, fixed: true, sortable: false, align: 'center',
                formatter: function (value) {
                    if (_.isNull(value)) {
                        return "";
                    }
                    return Tc.formatFloat(value * 100) + '%';
                }
            },
            {name: 'trainUnit', width: 120, fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 38,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        rowHeight: 36,
        styleUI: 'Bootstrap',
        pager: "#trainTypePager",
        loadComplete: function (xhr) {
            $('#trainTypeTable').find(".ui-jqgrid-bdiv").height(tableHeight + 2);
        }
    };
    var trainTypeNumObj = {
        chartId: 'trainTypeNumChart',
        gridId: '#trainTypeGrid',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
            self.resizeChart();
        },
        requestData: function (organId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getTrainingTypeUrl, {organId: organId}, function (result) {
                self.organId = organId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.drawDate(result.year);
                    self.render(result.list);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (data) {
            var self = this;
            var seriesData = [], source = [];
            _.each(data, function (obj) {
                if (obj.frequency == '-' || obj.frequency <= 0) {
                    return true;
                }
                source.push(obj);
                seriesData.push(formatPieData(obj.courseTypeKey, obj.courseTypeName, obj.frequency));
            });
            if (seriesData.length > 0) {
                self.chartObj.clear();
                trainTypeNumOption.series[0].data = seriesData;
                self.initLengend(self.chartObj, data, trainTypeNumOption.color);
                self.chartObj.setOption(trainTypeNumOption, true);
                self.initModal(source);
                self.chartObj.on('click', function (e) {
                    trainTypeNumObj.initGridAndShow(e.data.key);
                });
            } else {
                hideChart(self.chartId, true);
            }
        },
        //因为图表中没有图例，故用zrender画一个
        initLengend: function (_chartObj, data, colors) {
            var self = this;
            var _ZR = _chartObj.getZrender();
            var len = data.length, ids = 0;
            _.each(data, function (obj, i) {
                if (obj.frequency == '-' || obj.frequency <= 0) {
                    return true;
                }
                _ZR.addShape(new TextShape({
                    style: {
                        x: 0,
                        y: 10 + ids * 19,
                        color: colors[ids],
                        text: '▅',
                        textAlign: 'left',
                        textFont: 'bolder 17px 微软雅黑'
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: 21,
                        y: 13 + (ids * 19),
                        color: '#000',
                        text: obj.courseTypeName,
                        textAlign: 'left',
                        textBaseline: 'middle',
                        textFont: 'border 15px 微软雅黑'
                    },
                    hoverable: false
                }));
                ids++;
            });
            _ZR.refresh();
        },
        drawDate: function (date) {
            $('#trainTypeNumDate').text(date);
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        },
        initModal: function (data) {
            var self = this;
            var html = '';
            _.each(data, function (item, i) {
                if (i == 0) {
                    html += '<li role="presentation" class="active">';
                    self.fisrtType = item.courseTypeKey;
                } else {
                    html += '<li role="presentation">';
                }
                html += '<a href="#tabpanel" aria-controls="' + item.courseTypeKey + '" role="tab" data-toggle="tab">' + item.courseTypeName + '</a>';
                html += '</li>';
            });
            var $tabs = $('#trainProjectTabs');
            $tabs.html(html);
            $tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
                var $this = $(e.target);
                var key = $this.attr('aria-controls');
                trainTypeNumObj.initGrid(trainTypeNumObj.organId, key);
            });
        },
        initGrid: function (organId, type) {
            var self = this;
            var selectType = type ? type : self.fisrtType;
            if (!_.isUndefined(self.gridOrganId) || !_.isUndefined(self.selectType)) {
                self.resizeGrid(organId, selectType);
                return true;
            }
            trainTypeGridOption.postData = {'type': selectType, 'organId': organId};
            $(self.gridId).jqGrid(trainTypeGridOption);
            self.gridOrganId = organId;
            self.selectType = selectType;
        },
        resizeGrid: function (organId, type) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                postData: {'type': type, 'organId': organId}
            }).trigger("reloadGrid");
            self.selectType = type;
            self.gridOrganId = organId;
        },
        initGridAndShow: function (key) {
            var self = this;
            $('#trainProjectTabs a[aria-controls="' + key + '"]').tab('show');
            self.initGrid(self.organId, key);
            $('#trainProjectModal').modal('show');
        },
    };

    /**
     * 培训满意度年度统计
     */
    var satisfactionOption = {
        grid: {
            borderWidth: 0,
            x: 55,
            x2: 15,
            y: 25
        },
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: false,
                data: [],
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    //rotate: 30,
                    itemStyle: {
                        color: '#000000'
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12
                        //fontFamily: "'Applied Font Regular', 'Applied Font'"
                    }
                }
            }
        ],
        color: ['#3285C7'],
        yAxis: [
            {
                type: 'value',
                min: 0,
                max: 1,
                splitNumber: 5,
                axisLine: {
                    show: true,
                    lineStyle: {
                        width: 0,
                        color: '#000000'
                    }
                },
                axisLabel: {
                    show: true,
                    formatter: function (value) {
                        return Tc.formatFloat(value * 100) + '%';
                    }
                }
            }
        ],
        series: [
            {
                type: 'bar',
                barWidth: 30,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return Tc.formatFloat(i.value * 100) + '%';
                            }
                        }
                    }
                },
                data: []
            }
        ]
    };
    var satisfactionObj = {
        chartId: "satisfactionChart",
        chartObj: null,
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != orgId) {
                self.requestData(orgId);
            }
            self.resizeChart();
        },
        requestData: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getTrainingSatisfactionUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var data = [], xdata = [];
            _.each(result, function (item) {
                data.push(item.soure);
                xdata.push(item.year + '年');
            });
            if (data.length > 0) {
                if (data.length > 6) {   //加斜体
                    satisfactionOption.xAxis[0].axisLabel.rotate = 30;
                }
                satisfactionOption.xAxis[0].data = xdata;
                satisfactionOption.series[0].data = data;
                this.chartObj.setOption(satisfactionOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /***
     * 内部讲师统计
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}, formatter: peopleCompareOption.tooltip.formatter}, toolbox: {show: boolean, feature: {mark: {show: boolean}, dataView: {show: boolean, readOnly: boolean}, magicType: {show: boolean, type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}}, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {borderColor: string, x: number, y: number, x2: number, borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, height: number, start: number, end: number, showDetail: boolean}}}
     */
    var teacherGridOption = {
        url: urls.getInternalTrainerListUrl,
        datatype: "json",
        mtype: 'POST',
        altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 307,
        colNames: ['名称', '讲师级别', '所属组织', '职级', '课程设计数', '授课数'],
        colModel: [
            {name: 'lecturerName', width: 100, sortable: false, align: 'center'},
            {name: 'level', width: 100, sortable: false, align: 'center'},
            {name: 'organizationName', width: 100, fixed: true, sortable: false, align: 'left'},
            {name: 'abilityLvName', width: 40, fixed: true, sortable: false, align: 'center'},
            {name: 'courseNumber', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'teachingNumber', width: 60, fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 30,
        scroll: true
    };
    var teacherChartOption = {
        toolbox: false,
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [
            {
                type: "value",
                axisLine: {
                    show: false
                },
                axisLabel: {
                    formatter: "{value}",
                    textStyle: {
                        color: "rgb(0, 0, 0)",
                        fontSize: 13
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: "solid",
                        color: "rgb(204, 204, 204)"
                    }
                }
            }
        ],
        series: [
            {
                clickable: false,
                type: "bar",
                data: [],
                barWidth: 30,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: "#000000"
                            }
                        }
                    }
                },
                barMaxWidth: 50
            }
        ],
        color: ["#3285C7"],
        grid: {
            borderColor: "#ffffff",
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        dataZoom: {}
    };
    var teacherObj = {
        gridId: '#teacherGrid',
        chartId: 'teacherChart',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestChart(organId);
            }
            self.resizeChart();
            self.initGrid(organId);
        },
        requestChart: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getInternalTrainerUrl, {organId: orgId}, function (result) {
                self.organId = orgId;
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (data) {
            var self = this;
            var category = [], barDataArr = [];
            var list = _.sortBy(data, function (item) {
                return -item.lecturerNum;
            });
            _.each(list, function (item) {
                var organName = item.organizationName;
                category.push(organName);
                barDataArr.push({keyId: item.organizationId, name: organName, value: item.lecturerNum});
            });
            var len = category.length;
            if (len > 0) {
                var num = 4, dataZoom = {};//4个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                        _.each(category, function (item, i) {
                            category[i] = item.length > 6 ? item.substring(0, 5) + '...' : item;
                        });
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 20,
                        end: end,
                        showDetail: false,
                        zoomLock: true
                    };
                }
                self.chartObj.clear();
                teacherChartOption.dataZoom = dataZoom;
                teacherChartOption.xAxis[0].data = category;
                teacherChartOption.series[0].data = barDataArr;
                self.chartObj.setOption(teacherChartOption, true);
            } else {
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        },
        initGrid: function (organId) {
            var self = this;
            if (self.gridOrganId && self.gridOrganId != organId) {
                self.resizeGrid(organId);
                return true;
            }
            teacherGridOption.postData = {'organId': organId};
            $(self.gridId).jqGrid(teacherGridOption);
            self.gridOrganId = organId;
        },
        resizeGrid: function (organId) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                postData: {'organId': organId}
            }).trigger("reloadGrid");
            resizeGrid(self.gridId);
            self.gridOrganId = organId;
        }
    };


    /***
     * 培训记录
     * @type {{searchBoxId: string, gridId: string, clearCondBtnId: string, resultData: null, init: runOffDetailObj.init, getRequestData: runOffDetailObj.getRequestData, initData: runOffDetailObj.initData, resizeGrid: runOffDetailObj.resizeGrid}}
     */
    var tableHeight = 558;
    var trainGridOption = {
        url: urls.findTrainingRecordUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,
        colNames: ['姓名', '培训类型', '培训课程', '培训时间', '学时(小时)', '培训状态'],
        colModel: [
            {name: 'userName', index: 'userName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'typeName', index: 'typeName', width: 140, fixed: true, sortable: false, align: 'center'},
            {name: 'courseName', index: 'courseName', width: 180, sortable: false, align: 'left'},
            {
                name: 'startDate', index: 'startDate', width: 80, sortable: false, align: 'left',
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }
                    return '开始:' + value + '&nbsp;&nbsp;<br>结束:' + (row.finishDate == null ? '-' : row.finishDate);
                }
            },
            {name: 'trainTime', index: 'trainTime', width: 70, fixed: true, sortable: false, align: 'center'},
            {
                name: 'status', index: 'status', width: 100, fixed: true, sortable: false, align: 'center',
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }
                    return value == 1 ? '已完成' : '进行中';
                }
            }
        ],
        rownumbers: true,
        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#trainDetailSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $("#trainDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
        }
    };
    var trainDetailObj = {
        gridId: '#trainDetailGrid',
        resultData: null,
        init: function (organId) {
            var self = this;
            if (self.hasInit) {
                if (self.organId != organId) {
                    self.reloadData(organId);
                }
                return;
            }
            self.loadComple();
            trainGridOption.postData = {organId: organId};
            $(self.gridId).jqGrid(trainGridOption);
            self.hasInit = true;
            self.organId = organId;
        },
        loadComple: function () {
            $("#searchBtn").click(function () {
                var searchTxt = $.trim($("#searchTxt").val());
                if (searchTxt != "") {
                    trainDetailObj.reloadData(reqOrgId, searchTxt);
                }
            });
            $("#searchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#searchBtn").click();
                }
            })
        },
        reloadData: function (organId, keyName) {
            var self = this;
            var _keyName = keyName || self.keyName;
            var params = {organId: organId, keyName: _keyName};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.keyName = _keyName;
            self.resizeGrid();
            self.organId = organId;
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#trainDeailTable').width() * 0.98);
        }
    };

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    /**
     * 重新加载表格
     * @param gridId
     */
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    /**
     * 返回当前焦点tab页显示的区域id
     */
    function getActiveTabId(targetDom) {
        var _currObj = _.find($(targetDom), function (obj) {
            return $(obj).hasClass('selectList');
        });
        return _currObj != null ? $(_currObj).attr('page') : 'page-one';
    }

    function formatPieData(key, name, value) {
        if (!value) {
            return {};
        }
        //页面展示为 ：name，value
        return {
            value: value,
            key: key,
            name: (name + '\n' + Tc.formatNumber(value))
        };
    }

    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
            $chart.children().show();
        }
    }

    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
    }

    var pageOneObj = {
        trainCover: 0.0,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.requestTrainCostYear();
            self.requestTrainPlan();
            self.requestTrainCover();
            trainCostObj.init(self.organId);
            avgpriceCostObj.init(self.organId);
            subOrganSchoolObj.init(self.organId);
            completionRateObj.init(self.organId);
        },
        requestTrainCostYear: function () {
            var self = this;
            $.get(urls.getTrainCostYearUrl, {organId: self.organId}, function (rs) {
                var $parent = $('#costNumTxt').parents('.body-div');
                self.costYear = rs.year;
                $('#costYearTxt').text(rs.year);
                if (!_.isNull(rs.cost)) {
                    $parent.children('div.noData').remove();
                    $parent.children().show();
                    $('#costNumTxt').text(rs.cost);
                    $('#budgetRateTxt').text(Tc.formatFloat(rs.budgetRate * 100));
                } else {
                    $parent.children('div.noData').remove();
                    $parent.children().hide();
                    $parent.append('<div class="noData">暂无数据</div>');
                }
            });
        },
        requestTrainPlan: function () {
            var self = this;
            $.get(urls.getTrainPlanUrl, {organId: self.organId}, function (rs) {
                var $parent = $('#completeRateTxt').parents('.body-div');
                self.completeYear = rs.year;
                $('#completeYearTxt').text(rs.year);
                if (!_.isNull(rs.completeRate)) {
                    $parent.children('div.noData').remove();
                    $parent.children().show();
                    $('#completeRateTxt').text(Tc.formatFloat(rs.completeRate * 100));
                } else {
                    $parent.children('div.noData').remove();
                    $parent.children().hide();
                    $parent.append('<div class="noData">暂无数据</div>');
                }
            });
        },
        requestTrainCover: function () {
            var self = this;
            $.get(urls.getTrainCoverUrl, {organId: self.organId}, function (rs) {
                var $parent = $('#coverageRateTxt').parents('.body-div');
                $('#coverageYearTxt').text(self.costYear);
                if (!_.isNull(rs.result)) {
                    self.trainCover = rs.result;
                    $parent.children('div.noData').remove();
                    $parent.children().show();
                    $('#coverageRateTxt').text(Tc.formatFloat(rs.result * 100));
                } else {
                    $parent.children('div.noData').remove();
                    $parent.children().hide();
                    $parent.append('<div class="noData">暂无数据</div>');
                }
            });
        }
    }

    var pageTwoObj = {
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.requestExpenseStatistics();
            yearTrendObj.init(self.organId);
            subOrganContrastObj.init(self.organId);
            self.requestImplementation();
            peopleCompareObj.init(self.organId);
            coverageContrastObj.init(self.organId);
            trainTypeNumObj.init(self.organId);
            satisfactionObj.init(self.organId);
            teacherObj.init(self.organId);
        },
        requestExpenseStatistics: function () {
            var self = this;
            $.get(urls.getExpenseStatisticsUrl, {organId: self.organId}, function (rs) {
                $('#costStatisticsDate').text('(' + rs.year + ')');
                $('#proportion').text(_.isNull(rs.proportion) ? '-' : Tc.formatFloat(rs.proportion * 100));
                $('#avgCost').text(_.isNull(rs.avgCost) ? '-' : Tc.formatFloat(rs.avgCost));
            });
        },
        requestImplementation: function () {
            var self = this;
            $.get(urls.getImplementationUrl, {organId: self.organId}, function (rs) {
                $('#implementerDate').text('(' + rs.year + ')');
                $('#trainPassengers').text(rs.trainPassengers);
                $('#classHour').text(_.isNull(rs.manpower) ? '-' : Tc.formatFloat(rs.manpower));
            });
        }
    }

    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData(targetAreaId) {
        var selOrganId = reqOrgId;
        if (targetAreaId == 'page-three') {
            //培训明细
            trainDetailObj.init(selOrganId);
        } else if (targetAreaId == 'page-two') {
            pageTwoObj.init(selOrganId);
        } else {
            timeLineObj.init(selOrganId);
            pageOneObj.init(selOrganId);
        }
    }

    changeData('page-one');

    /*切换左边导航*/
    $(".leftListDiv").click(function (e) {
        e.stopPropagation();
        if ($(this).hasClass("selectList")) {
            return;
        } else {
            var _page = $(this).attr("page");
            $(".rightBodyPage").hide();
            $("#" + _page).show();
            $(".leftListDiv").removeClass("selectList");
            $(this).addClass("selectList");
            changeData(_page);
        }
    });

    /***
     * 培训总览锚点事件
     */
    $('a[class="anchor"]').click(function (e) {
        $($(".leftListDiv")[1]).click();
        var sign = $(this).data('sign');
        if (sign == 1) {
            var offsetTop = $("#implementerStatistics").offset().top;
            $(window).scrollTop(offsetTop);
        }
    });


    $(window).resize(function () {
        var _page = $('.leftListDiv.selectList').attr('page');
        if (_page == 'page-three') {
            //培训明细
            trainDetailObj.resizeGrid();
        } else if (_page == 'page-two') {
            yearTrendObj.resizeChart();
            subOrganContrastObj.resizeChart();
            peopleCompareObj.resizeChart();
            coverageContrastObj.resizeChart();
            trainTypeNumObj.resizeChart();
            satisfactionObj.resizeChart();

            teacherObj.resizeChart();
        } else {
            trainCostObj.resizeChart();
            avgpriceCostObj.resizeChart();
            subOrganSchoolObj.resizeChart();
            completionRateObj.resizeChart();
        }
    });
})
;