require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
         'jgGrid','underscore', 'utils',  "appBase","chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
    		 	getTrainCostYearUrl: webRoot + '/mobile/trainBoard/getTrainCostYear.do',       //获取当前年度培训费用以及预算率
    	        getTrainPlanUrl: webRoot + '/mobile/trainBoard/getTrainPlan.do',               //获取当前年度培训计划完成率
    	        getTrainingCostUrl: webRoot + '/mobile/trainBoard/getTrainingCost.do',         //培训费用统计
    	        getPerCapitaCostUrl: webRoot + '/mobile/trainBoard/getPerCapitaCost.do',       //人均培训费用统计
    	        getPlanCompletionUrl: webRoot + '/mobile/trainBoard/getPlanCompletion.do',     //培训计划完成率
    	        getPerCapitaHoursUrl: webRoot + '/mobile/trainBoard/getPerCapitaHours.do',     //下级组织人均学时对比
    	        getYearCostUrl: webRoot + '/mobile/trainBoard/getYearCost.do',                 //费用年度趋势图
    	        getSubOrganCostUrl: webRoot + '/mobile/trainBoard/getSubOrganizationCost.do',   //下级组织培训费用对比
    	        getSubOrganPassengersUrl: webRoot + '/mobile/trainBoard/getSubOrganizationPassengers.do', //下级组织培训人次对比
    	        getSubOrganCoverUrl: webRoot + '/mobile/trainBoard/getSubOrganizationCover.do',        //下级组织培训覆盖率对比
    	        getTrainingTypeUrl: webRoot + '/mobile/trainBoard/getTrainingType.do',                  //培训类型次数统计
    	        getTrainingSatisfactionUrl: webRoot + '/mobile/trainBoard/getTrainingSatisfaction.do',   //培训满意度年度统计
    	        getInternalTrainerUrl: webRoot + '/mobile/trainBoard/getInternalTrainer.do',           //下级组织内部讲师统计
    	        findTrainingRecordUrl: webRoot + '/mobile/trainBoard/findTrainingRecord.do' 
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var height;
    function loadLSL(){
        height=getWindowHeight()-140;
//        $("#trainDetailGrid").height(height+36);
        $("#trainDetailGridPanel").width($(window).width())
        $("#trainDetailGridPanel").height(height)
    }
    loadLSL();

    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	  var load = $(e.target).attr('load');
    	if(targetAreaId=="trainCostPanel"){
    		trainDetailObj.showScroll(false);
		    trainCostObj.resize();
		    avgpriceCostObj.resize();
		    yearTrendObj.resize();
		    subOrganContrastObj.resize();
    	}else if(targetAreaId=="trainImplementationPanel"){
    		trainDetailObj.showScroll(false);
    		if(load=="true"){
    			completionRateObj.resize();
        		coverageContrastObj.resize();
        		trainTypeObj.resize();
        		teacherObj.resize();
    		}else{
    			$(e.target).attr('load',"true");
    			 completionRateObj.init(reqOrganId);
			     coverageContrastObj.init(reqOrganId);
			     trainTypeObj.init(reqOrganId);
			     teacherObj.init(reqOrganId);
    		}
    		
    	}else if(targetAreaId=="trainEffectPanel"){
    		trainDetailObj.showScroll(false);
    		if(load=="true"){
    			subOrganSchoolObj.resize();
        		peopleCompareObj.resize();
        		satisfactionObj.resize();
    		}else{
    			$(e.target).attr('load',"true");
    			 subOrganSchoolObj.init(reqOrganId);
    			 peopleCompareObj.init(reqOrganId);
    			 satisfactionObj.init(reqOrganId);
    		}
    		
    	}else if(targetAreaId=="trainRecordPanel"){
    		trainDetailObj.showScroll(true);
    		if(load=="true"){
    			//trainDetailObj.resize();
    		}else{
    			$(e.target).attr('load',"true");
    		    trainDetailObj.init(reqOrganId);
    		}
    		
    	}
    });
    
    
    /*==============================================================*/
    /* TOP															*/
    /*==============================================================*/
    var topObj={
    	className:"trainCardPanel",
    	span:[],
    	init:function (organId) {
    		var self=this;
    		self.organId=organId;
    		this.requestTrainCostYear();
    		this.requestTrainPlan();
    		$.each($("."+this.className).children(".trainCard"),function(i,item){
    			self.span.push($(item).children("span"));
        		
    		});
    	},requestTrainCostYear: function () {
            var self = this;
            $.get(urls.getTrainCostYearUrl, {organId: self.organId}, function (rs) {
                if (!_.isNull(rs.budgetRate)) {
                	$(self.span[0]).text(Tc.formatFloat(rs.budgetRate * 100)+"%")
                } else {
                	$(self.span[0]).text("暂无数据");
                }
                var cost=rs.cost;
                subOrganContrastObj.init(reqOrganId,cost);
            });
        },
        requestTrainPlan: function () {
            var self = this;
            $.get(urls.getTrainPlanUrl, {organId: self.organId}, function (rs) {
                if (!_.isNull(rs.completeRate)) {
                	$(self.span[1]).text(Tc.formatFloat(rs.completeRate * 100)+"%")
                } else {
                	$(self.span[1]).text("暂无数据");
                }
            });
        },
    
    }
    
    /**
     *培训费用统计
     */
   var trainCostObj = {
            chartId: "trainCostChart",
            chartObj: null,
            option:{
            	animation:animation,
            	grid: defaultGrid,
                xAxis: [
                    {
                        type: "category",
                        splitLine: false,
                        axisTick: false,
                        data: [],
                        axisLine: false,
                        axisLabel: {
                            //rotate: 30,
                            itemStyle: {
                                color: '#000000'
                            }
                        }
                    }
                ],
                color: ['#23C6C8'],
                yAxis: [
                    {
                        splitLine: splitLine,
                        name: "(" + measurement + ")",
                        type: 'value',
                        axisLine: false,
                    }
                ],
                series: [
                    {
                        name: '辅助',
                        type: 'bar',
                        barCategoryGap: '45%',
                        barWidth :barWidth,
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
                        barCategoryGap: '45%',
                        barWidth :barWidth,
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
            },
            init: function (orgId) {
                var self = this;
                if (self.chartObj == null) {
                    self.chartObj = initEChart(self.chartId);
                }
                if (self.organId != orgId) {
                    self.requestData(orgId);
                }
               
            },
            requestData: function (orgId) {
                var self = this;
                loading(self.chartId);
                $.get(urls.getTrainingCostUrl, {organId: orgId}, function (result) {
                	loaded(self.chartId);
                    if (!_.isEmpty(result)) {

                        self.organId = orgId;
                        self.render(result);
                    } else {
                    	showEmptyTip($('#' + self.chartId));
                    }
                });
            },
            render: function (result) {
                var self = this;
                if (_.isNull(result.budgetCost) || _.isNull(result.cost)) {
                	showEmptyTip($('#' + self.chartId));
                    return;
                }
                var data1 = [result.budgetCost, result.cost], data0 = [0, 0];
                var xdata = ['当年预算', '当年累计'];
                var list = result.list;
                var sumVal = 0;
                _.each(list, function (item, i) {
                    data0.push(sumVal);
                    data1.push(item.cost);
                    xdata.push(item.yearMonth||item.quarter);
                    sumVal += item.cost;
                });
                if (data1.length > 6) {   //加斜体
                    self.option.xAxis[0].axisLabel.rotate = 30;
                }
                var max = result.cost > result.budgetCost ? result.cost : result.budgetCost;
                if(xdata.length>0){
                	 self.chartObj.clear();
                     self.option.xAxis[0].data = xdata;
                     self.option.yAxis[0].max = max;
                     self.option.series[0].data = data0;
                     self.option.series[1].data = data1;
                     self.chartObj.setOption(self.option);
                     $('#' + self.chartId).chartTooltip({
                         chart: self.chartObj,
                         formatter: function (i, v, p) {
                             var cols = [{
                                 name: v.name,
                                 value: (v.data[1].value * 1).toFixed(2),
                                 unit:"万元"
                             }];
                             return {
                                 title: "",
                                 cols: cols
                             };
                         }
                     });
                }else{
                	showEmptyTip($('#' + self.chartId));
                }
               
            },
            resize: function () {
            	 this.chartObj.resize();
            }
        }
    
    /**
     * 人均培训费用统计
     */
    var avgpriceCostObj = {
            chartId: 'avgpriceCostChart',
            chartObj: null,
            option:{
            	animation:animation,
            	grid: defaultGrid,
                xAxis: [
                    {
                        type: "category",
                        splitLine: false,
                        axisTick: false,
                        data: [],
                        axisLine: false,
                        axisLabel: {
                            //rotate: 30,
                            itemStyle: {
                                color: '#000000'
                            }, textStyle: {
                                color: '#000000',
                                fontSize: 12,
                                fontFamily: fontFamily
                            }
                        }
                    }
                ],
                color: ['#3285C7'],
                yAxis: [
                    {	
                    	splitLine:splitLine,
                    	name: "(" + measurement + ")",
                        type: 'value',
                        axisLine: false,
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        barWidth: barWidth,
                        itemStyle: {
                            normal: {
                            	color: barColor,
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
            },
            init: function (orgId) {
                var self = this;
                if (self.chartObj == null) {
                    self.chartObj = initEChart(self.chartId);
                }
                if (self.organId != orgId) {
                    self.requestData(orgId);
                }
               
            },
            requestData: function (orgId) {
                var self = this;
                loading(self.chartId);
                $.get(urls.getPerCapitaCostUrl, {organId: orgId}, function (result) {
                	loaded(self.chartId);
                	if (!_.isEmpty(result)) {
                        self.render(result);
                    } else {
                    	showEmptyTip($('#' + self.chartId));
                    }
                });
            },
            render: function (result) {
                var self = this;
                var data = [], xdata = [];
                $.each(result, function (i, o) {
                    data.push(o.avgCost);
                    xdata.push(o.yearMonth);
                });
                if (data.length > 0) {
                    self.chartObj.clear();
                    if (data.length > 6) {   //加斜体
                    	self.option.xAxis[0].axisLabel.rotate = 30;
                    }
                    self.option.xAxis[0].data = xdata;
                    self.option.series[0].data = data;
                    self.chartObj.setOption(self.option, true);
                    $('#' + self.chartId).chartTooltip({
                        chart: self.chartObj,
                        formatter: function (i, v, p) {
                            var cols = [{
                                name: v.name,
                                value: (v.data[0].value * 1).toFixed(2) ,
                                unit:"万元"
                            }];
                            return {
                                title: "",
                                cols: cols
                            };
                        }
                    });
                }else{
                	showEmptyTip($('#' + self.chartId));
                }
            },
            resize: function () {
            	this.chartObj.resize();
            }
        }
    
    /**
     * 费用年度趋势图
     */
    var yearTrendObj = {
            chartId: "yearTrendChart",
            chartObj: null,
            option : {
            	animation:animation,
                grid: defaultGrid,
                xAxis: [
                    {
                        type: "category",
                        splitLine:false,
                        axisTick: false,
                        data: [],
                        axisLine: false,
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
                    	name: "(" + measurement + ")",
                        type: "value",
                        axisLine: false,
                        splitLine: splitLine
                    }
                ],
                calculable: false,
                series: [
                    {
                        type: "line",
                        itemStyle: {
                            normal: {
                            	color:lineColor,
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
                                    borderWidth: 17
                                }
                            },
                            clickable: false,
                            data: []
                        }
                    }
                ]
            },
            init: function (organId) {
                var self = this;
                if (self.chartObj == null) {
                    self.chartObj = initEChart(self.chartId);
                }
                if (self.organId != organId) {
                    self.requestData(organId);
                }
               
            },
            requestData: function (organId) {
                var self = this;
                loading(self.chartId);
                $.get(urls.getYearCostUrl, {organId: organId}, function (result) {
                	 loaded(self.chartId);
                    if (!_.isEmpty(result)) {
                     
                        self.organId = organId;
                        self.render(result);
                    } else {
                    	showEmptyTip($('#' + self.chartId));
                    }
                });
            },
            render: function (result) {
                var self = this;
                var data = [], xAxisData = [], pointData = [];
                var max = _.max(result, 'cost').cost;
                _.each(result, function (item, i) {
                    var year = item.year + '年';
                    data.push({name: year, value: item.cost, key: i});
                    xAxisData.push(year);
                    if (i == result.length - 1) {
                        self.year = year;
                        pointData.push({name: '标注', value: item.cost, xAxis: year, yAxis: item.cost + max / 9});
                    }
                });
                if(xAxisData.length>0){
                	 self.chartObj.clear();
                     self.option.xAxis[0].data = xAxisData;
                     self.option.series[0].data = data;
                     self.option.series[0].markPoint.data = pointData;
                     self.chartObj.setOption(self.option, true);
                     $('#' + self.chartId).chartTooltip({
                         chart: self.chartObj,
                         formatter: function (i, v, p) {
                             var cols = [{
                                 name: v.name,
                                 value: (v.data[0].value.value * 1).toFixed(2) ,
                                 unit:"万元"
                             }];
                             return {
                                 title: "",
                                 cols: cols
                             };
                         }
                     });
                }else{
                	showEmptyTip($('#' + self.chartId));
                }
               
            },
            resize: function () {
            	this.chartObj.resize();
            }
        }
    
    
    /**
     * 下级组织培训费用对比
     */
    var subOrganContrastObj = {
            chartId: "subOrganContrastChart",
            chartObj: null,
            option :{
            	animation:animation,
            grid: defaultGrid,
            toolbox: false,
            color: ["#3285C7"],
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
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
                	name: "(" + measurement + ")",
                    type: "value",
                    axisLine: false,
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine: splitLine
                }
            ],
            series: [
                {
                    clickable: false,
                    type: "bar",
                    barWidth: barWidth,
                    data: [],
                    itemStyle: {
                        normal: {
                        	color: barColor,
                            label: {
                                show: true,
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    },markLine: {
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
                }
            ]
        },
            init: function (organId,cost) {
            	 var self = this;
                 if (self.chartObj == null) {
                     self.chartObj = initEChart(self.chartId);
                     self.requestData(organId,cost);
                 }
                
            },requestData:function(organId,cost){
            	var self = this;
            	 loading(self.chartId);
                 $.get(urls.getSubOrganCostUrl, {organId: organId}, function (result) {
                	 loaded(self.chartId);
                	 if (!_.isEmpty(result)) {

                         self.render(result.list,cost);
                     } else {
                    	 showEmptyTip($('#' + self.chartId));
                     }
                 });
            },
            
            render: function (result,cost) {
                var self = this;
                var data = [], xdata = [];
                var list = _.sortBy(result, function (item) {
                    return -(item.cost);
                });
                $.each(list, function (i, o) {
                    data.push(o.cost);
                    xdata.push(o.organizationName);
                });
                var len = xdata.length;
                if (len > 0) {
                    self.chartObj.clear();
                    self.option.series[0].markLine.data =self.packMarkLineData(xdata.length, cost);
                    self.option.xAxis[0].data = xdata;
                    self.option.xAxis[0].data = xdata;
                    self.option.series[0].data = data;
                    $('#' + self.chartId).parent().children(".note").html("<div class='line'></div><div class='lineNote'>当前组织</div>");
                    self.chartObj.setOption(self.option, true);
                    $('#' + self.chartId).chartTooltip({
                        chart: self.chartObj,
                        formatter: function (i, v, p) {
                        	var cols = [{
                                name: "当前组织",
                                value: (cost ).toFixed(2),
                                unit:"万元"
                            }];
                             cols.push({
                                name: v.name,
                                value: (v.data[0].value * 1).toFixed(2),
                                unit:"万元"
                            });
                            return {
                                title: "",
                                cols: cols
                            };
                        }
                    });
                } else{
                	showEmptyTip($('#' + self.chartId));
                }
            },packMarkLineData: function (cateLen, value) {
                return [[{xAxis: -1, yAxis: value},
                         {xAxis: cateLen + 1, yAxis: value}]];
                 },
           
            resize: function () {
            	 this.chartObj.resize();
            }
        }
    
    /**
     * 培训计划完成率
     */
    var completionRateObj = {
        chartId: "completionRateChart",
        chartObj: null,
        option :{
        	animation:animation,
            grid: defaultGrid,
            xAxis: [
                {
                    type: "category",
                    splitLine: false,
                    axisTick: false,
                    data: [],
                    axisLine: false,
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
                    type: 'value',
                    min: 0,
                    max: 1,
                    splitNumber: 5,
                    axisLine: false,
                    splitLine:splitLine,
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
                    barWidth: barWidth,
                    itemStyle: {
                        normal: {
                        	color: barColor,
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
        },
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != orgId) {
                self.requestData(orgId);
            }
        },
        requestData: function (orgId) {
            var self = this;
            loading(self.chartId);
            $.get(urls.getPlanCompletionUrl, {organId: orgId}, function (result) {
            	loaded(self.chartId);
                if (!_.isEmpty(result)) {
                    self.organId = orgId;
                    self.render(result);
                } else {
                	 showEmptyTip($('#' + self.chartId));
                }
            });
        },
        render: function (result) {
            var self = this;
            if (_.isNull(result.cumulative)) {
            	showEmptyTip($('#' + self.chartId));
                return;
            }
            var data = [result.cumulative], xdata = ['当年累计'];
            var list = result.list;
            $.each(list, function (i, o) {
                data.push(o.completeRate);
                xdata.push(o.yearMonth);
            });
            if(xdata.length>0){
            	self.chartObj.clear();
                if (data.length >= 6) {   //加斜体
                	self.option.xAxis[0].axisLabel.rotate = 30;
                }
                self.option.xAxis[0].data = xdata;
                self.option.series[0].data = data;
                self.chartObj.setOption(self.option);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [{
                            name: v.name,
                            value: (v.data[0].value * 100).toFixed(2),
                            unit:"%"
                        }];
                        return {
                            title: "",
                            cols: cols
                        };
                    }
                });
            }else{
            	showEmptyTip($('#' + self.chartId));
            }
            
        },
        resize: function () {
        	 this.chartObj.resize();
        }
    }

    /**
     * 下级组织培训覆盖率对比
     */
    var coverageContrastObj = {
        chartId: 'coverageContrastChart',
        chartObj: null,
        option : {
        	animation:animation,
            grid: organGrid,
            dataZoom: {},
            yAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: splitLine,
                axisTick: false,
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#BEBEBE'
                    }
                },
                data: []
            }],
            xAxis: [{
                type: 'value',
                splitLine: false,
                axisLine: false,
                axisLabel: {
                	  show: true,
                    formatter: '{value}%'
                }
            }],
            color: ['#3285C7'],
            series: [{
                type: "bar",
                clickable: false,
                barWidth: barWidth,
                itemStyle: {
                    normal: {
                    	color: barColor,
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
                        	color: lineColor,
                            lineStyle: {
                                type: 'solid'
                            },
                            label: {
                                show: true,
                                textStyle: {
                                    color: '#333'
                                },
                                formatter: function (i) {

                                    return i.data.xAxis.toFixed(2)+'%';
                                }
                            }
                        },
                        emphasis: {
                            label: false
                        }
                    },
                    data: []
                }
            }]
        },
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
        },
        
        requestData: function (organId) {
            var self = this;
            loading(self.chartId);
            $.get(urls.getSubOrganCoverUrl, {organId: organId}, function (result) {
            	loaded(self.chartId);
            	if (!_.isEmpty(result)) {
                    self.organId = organId;
                    self.render(result.list, result.companyCover);
                } else {
                	 showEmptyTip($('#' + self.chartId));
                }
            });
        },
        render: function (subData, curRate) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var list = _.sortBy(subData, function (item) {
                return item.coverageRate;
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
            	scalChartHeight(self,barDataArr.length);
                self.chartObj.clear();
                self.option.xAxis[0].max = (max + max / 10) * 100;
                self.option.yAxis[0].data = category;
                self.option.series[0].data = barDataArr;
                self.option.series[0].markLine.data = self.packMarkLineData(barDataArr.length, curRate * 100);
                self.chartObj.setOption(self.option, true);
                $('#' + self.chartId).parent().children(".note").html("<div class='line'></div><div class='lineNote'>当前组织</div>");
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [{
                            name: "当前组织",
                            value: (curRate * 100).toFixed(2),
                            unit:"%"
                        }];
                        cols.push({
                            name: v.name,
                            value: (v.data[0].value.value * 1).toFixed(2),
                            unit:"%"
                        });
                        return {
                            title: "",
                            cols: cols
                        };
                    }
                });
            } else {
            	showEmptyTip($('#' + self.chartId));
            }
        },
        packMarkLineData: function (cateLen, value) {
            return [[{yAxis: -1, xAxis: value},
                {yAxis: cateLen + 1, xAxis: value}]];
        },

        resize: function () {
        	this.chartObj.resize();
        }
    };
    
    /**
     * 培训类型次数统计
     */
    var trainTypeObj = {
            chartId: 'trainTypeChart',
            chartObj: null,
            option:getDefaultPieOption("培训类型次数"),
            init: function (organId) {
                var self = this;
                if (self.chartObj == null) {
                    self.chartObj = initEChart(self.chartId);
                }
                if (self.organId != organId) {
                    self.requestData(organId);
                }
            },
            requestData: function (organId) {
                var self = this;
                loading(self.chartId);
                $.get(urls.getTrainingTypeUrl, {organId: organId}, function (result) {
                   	loaded(self.chartId);
                	if (!_.isEmpty(result)) {
                        self.render(result.list);
                    } else {
                    	 showEmptyTip($('#' + self.chartId));
                    }
                });
            },
            render: function (data) {
                var self = this;
                var seriesData = [];
                _.each(data, function (obj) {
                    if (obj.frequency == '-' || obj.frequency <= 0) {
                        return true;
                    }
                    seriesData.push({value: obj.frequency,key: obj.courseTypeKey,name: obj.courseTypeName});
                });
                if (seriesData.length > 0) {
                    self.chartObj.clear();
                    self.option.series[0].data = seriesData;
                   // self.initLengend(self.chartObj, data, trainTypeNumOption.color);
                    self.chartObj.setOption(self.option, true);
                    var legend=[],total=0;
            		$.each(seriesData, function(i, item) {
            			total+=parseInt(item.value);
            			legend.push('<div>'+
            					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
            					'<span>'+ item.name + '</span>'+
            					'</div>');
        			});
        			appendLegend($("#" + self.chartId), legend);
                    $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
                    	var cols = [  ];
        					$.each(seriesData, function(i, item) {
        						cols.push({
        							name : item.name,
        							value : item.value+"次" ,
        							rate : "("+((item.value/total)*100).toFixed(2)+"%)"
        						});

        					});
        					return {
        						color:colorPie,
        						title : "",
        						note:"",
        						cols : cols
        					};            }});
                } else {
                	showEmptyTip($('#' + self.chartId));
                }
            },
      
            resize: function () {
            	 this.chartObj.resize();
            }
        };
    
    /**
     * 培训讲师统计
     */
    var teacherObj = {
        chartId: 'teacherChart',
        option :{
        	animation:animation,
            toolbox: false,
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
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
                	name: "(人数)",
                    type: "value",
                    axisLine: false,
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine:splitLine,
                }
            ],
            series: [
                {
                    clickable: false,
                    type: "bar",
                    data: [],
                    barWidth: barWidth,
                    itemStyle: {
                        normal: {
                        	color: barColor,
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
            grid: defaultGrid
        },
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestChart(organId);
            }
        },
        requestChart: function (orgId) {
            var self = this;
            loading(self.chartId);
            $.get(urls.getInternalTrainerUrl, {organId: orgId}, function (result) {
                loaded(self.chartId);
            	if (!_.isEmpty(result)) {
                    self.render(result);
                } else {
                	showEmptyTip($('#' + self.chartId));
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
                self.chartObj.clear();
                self.option.xAxis[0].data = category;
                self.option.series[0].data = barDataArr;
                self.chartObj.setOption(self.option, true);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [{
                            name: v.name,
                            value: v.data[0].value.value ,
                            unit:"人"
                        }];
                        return {
                            title: "",
                            cols: cols
                        };
                    }
                });
            } else {
            	showEmptyTip($('#' + self.chartId));
            }
        },
        resize: function () {
        	this.chartObj.resize();
        }
    };
    
    
    /**
     * 下级组织人均学时分析
     */
    
    var subOrganSchoolObj = {
        chartId: 'subOrganSchoolChart',
        option : {
        	animation:animation,
            grid: organGrid,
            color: ["#3285C7"],
            yAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#BEBEBE'
                    }
                },
                data: []
            }],
            xAxis: [
                {
                	name: "(小时)",
                    type: "value",
                    axisLine: false,
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine:splitLine,
                }
            ],
            series: [
                {
                    clickable: false,
                    type: "bar",
                    barWidth: barWidth,
                    data: [],
                    itemStyle: {
                        normal: {
                        	color: barColor,
                            label: {
                                show: true,
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    }
                }
            ]
        },
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
        },
        requestData: function (organId) {
            var self = this;
            loading(self.chartId);
            $.get(urls.getPerCapitaHoursUrl, {organId: organId}, function (result) {
            	 loaded(self.chartId);
            	if (!_.isEmpty(result)) {
                    self.render(result.list);
                } else {
                	showEmptyTip($('#' + self.chartId));
                }
            });
        },
        render: function (data) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var list = _.sortBy(data, function (item) {
                return item.hours;
            });
            $.each(list, function (i, item) {
                xAxisData.push(item.organizationName);
                seriesData.push(item.hours);
            });
            var len = xAxisData.length;
            if (len > 0) {
            	scalChartHeight(self,xAxisData.length);
                self.chartObj.clear();
                self.option.yAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData;
                self.chartObj.setOption(self.option, true);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [{
                            name: v.name,
                            value: v.data[0].value ,
                            unit:"小时"
                        }];
                        return {
                            title: "",
                            cols: cols
                        };
                    }
                });
            } else {
            	showEmptyTip($('#' + self.chartId));
            }
        },

        resize: function () {
        	 this.chartObj.resize();
        }
    };
    
    /**
     * 下级组织培训人次对比
     */
    var peopleCompareObj = {
        chartId: 'peopleCompareChart',
        chartObj: null,
        option : {
        	animation:animation,
            yAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#BEBEBE'
                    }
                },
                data: []
            }],
            xAxis: [
                {
                	name: "(人数)",
                    type: "value",
                    axisLine: false,
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine:splitLine,
                }
            ],
            series: [
                {
                    clickable: false,
                    type: "bar",
                    barWidth: barWidth,
                    barGap: 50,
                    itemStyle: {
                        normal: {
                        	color: barColor,
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
            grid: organGrid,

        },
        init: function (organId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != organId) {
                self.requestData(organId);
            }
        },
        requestData: function (organId) {
            var self = this;
            loading(self.chartId);
            $.get(urls.getSubOrganPassengersUrl, {organId: organId}, function (result) {
            	  loaded(self.chartId);
            	if (!_.isEmpty(result)) {
                    self.render(result.list);
                } else {
                	showEmptyTip($('#' + self.chartId));
                }
            });
        },
        render: function (result) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var list = _.sortBy(result, function (item) {
                return item.frequency;
            });
            _.each(list, function (item) {
                xAxisData.push(item.organizationName);
                seriesData.push(item.frequency);
            });
            var len = xAxisData.length;
            if (len > 0) {
            	scalChartHeight(self,xAxisData.length);
                self.chartObj.clear();
                self.option.yAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData;
                self.chartObj.setOption(self.option, true);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [{
                            name: v.name,
                            value: v.data[0].value ,
                            unit:"人"
                        }];
                        return {
                            title: "",
                            cols: cols
                        };
                    }
                });
            } else {
            	showEmptyTip($('#' + self.chartId));
            }
        },

        resize: function () {
        	  this.chartObj.resize();
        }
    };

    /**
     * 培训满意度统计
     */
    var satisfactionObj = {
        chartId: "satisfactionChart",
        chartObj: null,
        option : {
        	animation:animation,
            grid: defaultGrid,
            xAxis: [
                {
                    type: "category",
                    splitLine: false,
                    axisTick: false,
                    data: [],
                    axisLine: false,
                    axisLabel: {
                        //rotate: 30,
                        itemStyle: {
                            color: '#000000'
                        }, textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontFamily: fontFamily
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
                    axisLine: false,
                    splitLine:splitLine,
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
                    barWidth: barWidth,
                    itemStyle: {
                        normal: {
                        	color: barColor,
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
        },
        init: function (orgId) {
            var self = this;
            if (self.chartObj == null) {
                self.chartObj = initEChart(self.chartId);
            }
            if (self.organId != orgId) {
                self.requestData(orgId);
            }
        },
        requestData: function (orgId) {
            var self = this;
            loading(self.chartId);
            $.get(urls.getTrainingSatisfactionUrl, {organId: orgId}, function (result) {
            	 loaded(self.chartId);
            	if (!_.isEmpty(result)) {
                    self.render(result);
                } else {
                	showEmptyTip($('#' + self.chartId));
                }
            });
        },
        render: function (result) {
        	var self=this;
            var data = [], xdata = [];
            _.each(result, function (item) {
                data.push(item.soure);
                xdata.push(item.year + '年');
            });
            if (data.length > 0) {
                if (data.length > 6) {   //加斜体
                    this.option.xAxis[0].axisLabel.rotate = 30;
                }
                self.option.xAxis[0].data = xdata;
                self.option.series[0].data = data;
                this.chartObj.setOption(self.option, true);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [{
                            name: v.name,
                            value: (v.data[0].value*100).toFixed(2),
                            unit:"%"
                        }];
                        return {
                            title: "",
                            cols: cols
                        };
                    }
                });
            } else {
            	showEmptyTip($('#' + self.chartId));
            }
        },
        resize: function () {
        	this.chartObj.resize();
        }
    }
    /**
     * 培训记录查询
     */

    var trainDetailObj = {
        gridId: '#trainDetailGrid',
        scroll:null,
        load:false,
        option:{
            url: urls.findTrainingRecordUrl,
            datatype: "json",
            postData: {},
            mtype: 'POST',
           // altRows: true,//设置表格行的不同底色
            //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用

//            height: height,
            rowHeight:51,
            colNames: ['姓名',  '培训课程', '培训时间','培训类型', '学时(小时)', '培训状态'],
            colModel: [
                {name: 'userName', index: 'userName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'courseName', index: 'courseName', width: 120, sortable: false, align: 'left'},
                {
                    name: 'startDate', index: 'startDate', width: 120, sortable: false, align: 'left',
                    formatter: function (value, options, row) {
                        if (_.isEmpty(value)) {
                            return "";
                        }
                        return '开始:' + value + '&nbsp;&nbsp;<br/>结束:' + (row.finishDate == null ? '-' : row.finishDate);
                    }
                },
                {name: 'typeName', index: 'typeName', width: 140, fixed: true, sortable: false, align: 'center'},
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
            ],gridComplete:function(){

            	
             },
            scroll: true
        },
        init: function (organId) {
            this.option.postData = {organId: organId};
            $(this.gridId).jqGrid(this.option);
            trainDetailObj.resize();
            var loadLeft=($(window).width()/2-20-10); 
            $('#trainDetailGridPanel').find("#load_trainDetailGrid").css('left', loadLeft+"px");
        },
        resize: function () {
            $(this.gridId).setGridHeight($('#trainDetailGridPanel').height()-10);
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
        	self.load=true;
        	  var width=$(self.gridId).width()-$('#trainDetailGridPanel').width()-30;
              if(width<0){
              	return;
              }
              var scrollWidth=$('#trainDetailGridPanel').width()-width;
              var scal=1;
              if(scrollWidth<50){
              	scrollWidth=50;
              	var newScrollMoveWidth=$(window).width()-50-15-15;
              	scal=newScrollMoveWidth/width;
              }
              var div=$("<div class='scrollTable-hide'></div>")
              $(this.gridId).append(div);
              self.scroll=$("<div class='scrollTable'></div>")
              self.scroll.width(scrollWidth);
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
              	self.scroll.css("left",scrollLeft+"px");
              	$('#trainDetailGridPanel').find("table").css('-webkit-transform', 'translate3d(' + currLeft + 'px,0,0)');
              	
              });
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
    
   /* $.screenZoom({
    	"trainCostChart":trainCostObj,
    	"avgpriceCostChart":avgpriceCostObj,
    	"yearTrendChart":yearTrendObj,
    	"subOrganContrastChart":subOrganContrastObj,
    	"completionRateChart":completionRateObj,
    	"coverageContrastChart":coverageContrastObj,
    	"trainTypeChart":trainTypeObj,
    	"teacherChart":teacherObj,
    	"subOrganSchoolChart":subOrganSchoolObj,
    	"peopleCompareChart":peopleCompareObj,
    	"satisfactionChart":satisfactionObj,
    	"trainDetailGrid":trainDetailObj
    });*/
    $.screenZoom([
    	trainCostObj,
    	avgpriceCostObj,
    	yearTrendObj,
    	subOrganContrastObj,
    	completionRateObj,
    	coverageContrastObj,
    	trainTypeObj,
    	teacherObj,
    	subOrganSchoolObj,
    	peopleCompareObj,
    	satisfactionObj,
    	trainDetailObj
    ]);
    topObj.init(reqOrganId);
    trainCostObj.init(reqOrganId);
    avgpriceCostObj.init(reqOrganId);
    yearTrendObj.init(reqOrganId);

});