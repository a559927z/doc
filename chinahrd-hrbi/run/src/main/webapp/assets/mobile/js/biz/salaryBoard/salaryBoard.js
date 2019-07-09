require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
         'jgGrid','underscore', 'utils',  "appBase","chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
    		    getSalaryProportion: webRoot + '/mobile/salaryBoard/getSalaryProportion.do',                 //薪酬占人力成本比
    		    getSalaryRateOfReturn: webRoot + '/mobile/salaryBoard/getSalaryRateOfReturn.do',             //人力资本投资回报率
    		    getSalaryPayTotal: webRoot + '/mobile/salaryBoard/getSalaryPayTotal.do',                     //薪酬执行
    		    getSalarySubOrganization: webRoot + '/mobile/salaryBoard/getSalarySubOrganization.do',       //薪酬对比(薪酬总额与平均薪酬)
    	        getSalaryMonthRateOfReturn: webRoot + "/mobile/salaryBoard/getSalaryMonthRateOfReturn.do",   //人力资本投资回报率月度趋势
    	        getSalaryCostKpi: webRoot + "/mobile/salaryBoard/getSalaryCostKpi.do",                       //组织KPI达标率、人力成本、薪酬总额的年度趋势
    	        getSalaryCostSalesProfit: webRoot + "/mobile/salaryBoard/getSalaryCostSalesProfit.do",       //营业额、利润、人力成本及薪酬总额的月度趋势
    	        getSalaryBitValueYear: webRoot + "/mobile/salaryBoard/getSalaryBitValueYear.do",             //行业分位值年度趋势
    	        getSalaryDifferencePost: webRoot + "/mobile/salaryBoard/getSalaryDifferencePost.do",         //薪酬差异度岗位表
    	        getSalaryEmpCR: webRoot + "/mobile/salaryBoard/getSalaryEmpCR.do",           				 //员工CR值
    	        getSalaryBonusProfit: webRoot + '/mobile/salaryBoard/getSalaryBonusProfit.do'     			//年终奖见分析
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var height;
    function loadPage(){
        height=getWindowHeight()-140;
        $("#xinCChayGangwbGridPanel").width($(window).width())
        $("#xinCChayGangwbGridPanel").height(height)
    }
    loadPage();

    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	  var load = $(e.target).attr('load');
    	if(targetAreaId=="xczePanel"){
		    trainCostObj.resize(); 
		    avgpriceCostObj.resize();
    	}else if(targetAreaId=="qcyxxPanel"){
    		if(load=="true"){
    			huiBaoLvObj.resize();
        		nianDuQuShiObj.resize();
        		yuDuQuShiObj.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			huiBaoLvObj.init(reqOrganId); 
        		nianDuQuShiObj.resize();
        		yuDuQuShiObj.init(reqOrganId);
      		}
    	}else if(targetAreaId=="qccyxPanel"){
    		if(load=="true"){
    			hangYeNianDuqsObj.resize();
        		yuanGongCRObj.resize();
        		xinCChayGangwbGridObj.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			hangYeNianDuqsObj.init(reqOrganId);
        		yuanGongCRObj.init(reqOrganId);
        		xinCChayGangwbGridObj.init(reqOrganId);
      		}
        }else if(targetAreaId=="nzjfxPanel"){
    		if(load=="true"){
    			trainDetailObj.resize();
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
    	className:"salaryCardPanel",
    	span:[],
    	init:function (organId) {
    		var self=this;
    		self.organId=organId;
    		this.requestTrainCostYear();
    		this.requestTrainPlan();
    		$.each($("."+this.className).children(".salaryCard"),function(i,item){
    			self.span.push($(item).children("span"));
        		
    		});
    	},requestTrainCostYear: function () {
            var self = this;
            $.get(urls.getSalaryProportion, {organId: self.organId}, function (rs) {
            	console.log(rs)
                if (!_.isNull(rs)&&rs!=""&&!_.isNull(rs.proportion)&&rs.proportion!=0) {
                	$(self.span[0]).text(Tc.formatFloat(rs.proportion)+"%")
                } else {
                	$(self.span[0]).text("暂无数据");
                	$(self.span[0]).addClass("emptyData");
                }
                var cost=rs.cost;
            });
        },
        requestTrainPlan: function () {
            var self = this;
            $.get(urls.getSalaryRateOfReturn, {organId: self.organId}, function (rs) {
                if (!_.isNull(rs)&&rs!=""&&!_.isNull(rs.rateOfReturn)&&rs.rateOfReturn!=0) {
                	$(self.span[1]).text(Tc.formatFloat(rs.rateOfReturn))
                } else {
                	$(self.span[1]).text("暂无数据");
                	$(self.span[1]).addClass("emptyData");
                }
            });
        },
    
    }
    
    /**
     *薪酬总额
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
                        stack: '费用统计',
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
                        stack: '费用统计',
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
                $.get(urls.getSalaryPayTotal, {organId: orgId}, function (result) {
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
                if (_.isNull(result.payValue) || _.isNull(result.sumPay)) {
                	showEmptyTip($('#' + self.chartId));
                    return;
                }
                var data1 = [result.payValue, result.sumPay], data0 = [0, 0];
                var xdata = ['当年预算', '当年累计'];
                var list = result.list;
                var sumVal = 0;
                _.each(list, function (item, i) {
                    data0.push(sumVal);
                    data1.push(item.sumPay);
                    xdata.push(item.yearMonth);
                    sumVal += item.sumPay;
                });
                if (data1.length > 6) {   //加斜体
                    self.option.xAxis[0].axisLabel.rotate = 30;
                }
                var max = result.sumPay > result.payValue ? result.sumPay : result.payValue;
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
     * 薪酬对比
     */
    var avgpriceCostObj = {
   		 chartId:"avgpriceCostChart",
		chart: null,
        option:{
       	 animation:animation,
            grid: organGrid,
            xAxis: [
					{
						name:"(万元)",
						 splitNumber: 4,
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
            yAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000'
                    }
                },
                data: []
            }],
            series: [
                {
                	clickable:false,
                    type: "bar",
                    barWidth :barWidth,
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
        init:function(orgId,seqId){
        	 var self = this;
             if (self.chart == null) {
                 self.chart = initEChart(self.chartId);
             }
             self.organId =orgId;
            // if (self.organId != orgId) {
                 self.requestData(orgId,seqId);
          //   }
            
        }, requestData: function (orgId,seqId) {
            var self = this;
            $.get(urls.getSalarySubOrganization, {organId: orgId}, function (result) {
            	if (!_.isEmpty(result)) {
                    self.render(result,seqId);
                } else {
                	showEmptyTip($('#' + self.chartId));
                }
            });
        },render: function (data,seqId) {
        	var self=this;
        	if(data==undefined){
                console.info("薪酬对比出错了！");
            }else{
           	 var len=0;
            	  var yAxisData = [], seriesData = [];
                  var tem=[];
                $.each(data,function(n,v){
                	if(seqId!=undefined&&seqId==2){
                		 tem.push({"name":data[len].organizationName,"value":data[len].avgPay});
                	}else{
                		 tem.push({"name":data[len].organizationName,"value":data[len].sumPay});
                	}
                   len++;
                });
                scalChartHeight(this,len);
               // tem= _.sortBy(tem,"value").reverse();   //此方法是升序排序
                  tem= _.sortBy(tem,"value").sort();     //此方法是降序排序
                  $.each(tem, function (i, item) {
                      yAxisData.push(formatAxis(item.name));
                      seriesData.push(Tc.formatFloat(item.value));
                  });
                  if(yAxisData.length>0) {
                      self.option.yAxis[0].data = yAxisData;
                      self.option.series[0].data = seriesData;
                      self.chart.setOption(self.option);
                      $('#' + self.chartId).chartTooltip({chart:self.chart,formatter:function(index,v,p){
                    	  var cols=[{name:"薪酬总额",value:v.data[0].value,unit:"万元"}];
                    	  if(seqId!=undefined&&seqId==2){
                    		  cols=[{name:"平均薪酬",value:v.data[0].value,unit:"万元"}];
                    	  }
                          return {title:v.name,cols:cols};
                      }});
                  }else{
                      if(self.chart)
                        self.chart.dispose();
                      $("#avgpriceCostChart").html("<div class='loadingmessage'>暂无数据</div>");
                  }
            }
        },
        resize:function(){
       	 this.chart.resize();
        }
    		}
    
    /**
     * 薪酬有效性 人力资本投资回报率
     */

    var huiBaoLvObj = {
            chartId: "huiBaoLvChart",
            resultData: null,
            chart: null,
            option: {
            	animation:animation,
                grid: defaultGrid,
                legend:getDefaultLegend(['人力资本投资回报率']),
                xAxis: [{
                    type: "category",
                    splitLine: false,
                    axisTick: false,
                    data: [],
                    axisLine: false,
                    axisLabel: {
                        rotate: 20,
                        itemStyle: {
                            color: '#000000'
                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontFamily: fontFamily
                        }
                    }
                }],
                yAxis: [{
                    type: 'value',
                    name: "(" + measurement + ")",
                    splitNumber: 4,
                    splitLine: splitLine,
                    axisLine: false,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                series: [ {
                	itemStyle: {
                        normal: {
                            color: lineColor
                        }
                    },
                    name: '人力资本投资回报率',
                    type: 'line',
                    data: [],

                },]
            },
            getRequestData: function (organId, year) {
                var self = this;
                $.get(urls.getSalaryMonthRateOfReturn, {
                    'organId': organId
                }, function (rs) {
                    if (_.isEmpty(rs)) {
                        showEmptyTip($('#' + self.chartId).parent());
                        return;
                    }
                    removeEmptyTip($('#' + self.chartId).parent());
                    self.resultData = rs;
                    self.initData(organId);
                });
            },
            initData: function (organId) {
                var self = this;
                var xAxisData = [], seriesData1 = [], seriesData2 = [], seriesData3 = [], seriesData4 = [];
                $.each(self.resultData, function (i, item) {
                    xAxisData.push(item.yearMonth);
                    seriesData1.push(Tc.formatFloat(item.rateReturn));
                });
                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData1;
                self.initEcharts();
            },
            initEcharts: function () {
                var self = this;
                self.chart = echarts.init(document
                    .getElementById(self.chartId));
                self.chart.setOption(self.option);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chart,
                    formatter: function (i, v, p) {
                        var cols = [];
                        $.each(v.data, function (index, o) {
                            cols.push({
                                name: o.name,
                                value: (o.value).toFixed(2)
                            });
                        });
                        return {
                            title: v.name,
                            cols: cols
                        };
                    }
                });
            },
            init: function (reqOrgId, year) {
                var self = this;
                self.getRequestData(reqOrgId, year);
            },
            resize: function () {
                this.chart.resize();
            }
        };
    
    
    /**
     * 薪酬有效性 组织KPI达标率、人力成本、薪酬总额的年度趋势
     */
    var _barWidth=barWidth;
    var nianDuQuShiObj = {
            chartId: 'nianDuQuShiChart',
            chartObj: null,
            chartOption: {
            	animation:animation,
            	calculable: false,
                legend: getDefaultLegend(['薪酬额', '人力成本', '组织PK达标率']),
                grid: defaultDoubleGrid,
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
                yAxis: [{
                    type: 'value',
                    splitNumber: 4,
                    splitLine: splitLine,
                    name:"万元",
                    nameTextStyle:{
                    	color: 'black'
                    },
                    axisLine: false
                },{
                    type: 'value',
                    splitLine: false,
                    axisLine: false,
                    axisLabel: {
                        formatter: function (value) {
                            return (value) + '%';
                        }
                    }
                }],
                series: [ {
                  	 yAxisIndex: 0,
                     name: '薪酬额',
                     type: 'bar',
                     barCategoryGap: '70%',
                     barWidth :8*barWidth/10,
                    // clickable: false,
                     itemStyle: {
                         normal: {
                        	 color: barColor,
                             label: {
                                 show: true
                             }
                         }
                     },
                     data: []
                 }, {
                  	 yAxisIndex: 0,
                     name: '人力成本',
                     type: 'bar',
                     barCategoryGap: '70%',
                     barWidth: 8*barWidth/10,
                    // clickable: false,
                     itemStyle: {
                         normal: {
                        	 color: colorPie[1],
                             label: {
                                 show: true
                                // position : 'insideBottom'
                             }
                         }
                     },
                     data: []
                 },{
                    name: '组织PK达标率',
                    type: 'line',
                    clickable: false,
                    yAxisIndex: 1,
                    symbol: 'circle',
                    itemStyle: {
                        normal: {
                        	color: lineColor,
                            label: {
                                show: true,
                                formatter: function (a) {
                                    return a.value+"%";
                                }
                            }
                        }
                    },
                    data: []
                }]
            },
            init: function (organId) {
                var self = this;
                self.chartObj = initEChart(self.chartId);
                self.initData(organId);
            },
            initData: function (organId) {
                var self = this;
                if (self.organId == organId) {
                    return;
                }
                self.organId = organId;
                self.requestData(organId);
            },
            initChartData: function (trendData) {
            	console.log(8*barWidth/10)
                var self = this;
                 _barWidth=barWidth;
                var xAxisData = [];
        		var benefitData = [];
        		var changeData = [];
        		var series2 = [];
        		var result = trendData;
        		 var trendOption = _.clone(self.chartOption);
        		$.each(result, function(i, item) {
        			xAxisData.push(item.year + '年');
        			benefitData.push(Tc.formatFloat(item.sumPay));
        			changeData.push(Tc.formatFloat(item.cost));
        			series2.push(Tc.formatFloat(item.kpi * 100));
        		});
        		
        		trendOption.xAxis[0].data = xAxisData;
        		trendOption.series[0].data = benefitData;
        		trendOption.series[1].data = changeData;
        		trendOption.series[2].data = series2;
        		 _barWidth=2*8*barWidth/10+4;
    		   	self.chartObj.setOption(trendOption, true);
    		   	this.tooltip();
    		    self.resize();
            },
            resize: function(){
            	this.chartObj.resize();
            },
            requestData: function (organId) {
                var self = this;
                loading(self.chartId)
                $.ajax({
    				url : urls.getSalaryCostKpi,
    				data : {
    					organId: organId
    				},
    				success : function(data) {
    					 loaded(self.chartId)
    					if (_.isEmpty(data)) {
    						showEmptyTip($('#'+self.chartId));
    						return;
    					}
    					removeEmptyTip($('#'+self.chartId));
    					 self.extendData(data);
    				}
    			});	
            },
            extendData: function (source) {
            	var self=this;
    			self.initChartData(source);
            },tooltip:function(){
            	   $('#' + this.chartId).chartTooltip({
            		   chart:this.chartObj,
            		   barWidth:_barWidth,
                       formatter: function (i, v, p) {
                           var cols = [];
                           $.each(v.data, function (index, o) {
                        	   if(index==2){
                        		   cols.push({
                                       name: o.name,
                                       value: o.value+"%"
                                   });
                        	   }else{
                        		   cols.push({
                                       name: o.name,
                                       value: o.value ,
                                       unit:"万元"
                                   });
                        	   }
                              
                           });
                           return {
                               title: v.name,
                               cols: cols
                           };
                       }
                   });
            }
        };
    
    /**
     * 营业额、利润、人力成本及薪酬总额的月度趋势
     */

    var yuDuQuShiObj = {
            chartId: "yueDuQuShiChart",
            resultData: null,
            chartObj: null,
            option: {
            	animation:animation,
                grid:defaultDoubleGrid,
                legend:getDefaultLegend(['人力成本', '利润', '销售额', '薪酬总额']),
                xAxis: [{
                    type: "category",
                    splitLine: false,
                    axisTick: false,
                    data: [],
                    axisLine: false,
                    axisLabel: {
                        rotate: 20,
                        itemStyle: {
                            color: '#000000'
                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontFamily: fontFamily
                        }
                    }
                }],
                yAxis: [{
                    type: 'value',
                    name: "(" + measurement + ")",
                    splitNumber: 4,
                    splitLine: splitLine,
                    axisLine: false,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                series: [{
                	itemStyle: {
                        normal: {
                            color: colorPie[0]
                        }
                    },
                    name: '人力成本',
                    type: 'line',
                    data: [],

                }, {
                	itemStyle: {
                        normal: {
                            color: colorPie[2]
                        }
                    },
                    name: '利润',
                    type: 'line',
                    data: [],

                },{
                	itemStyle: {
                        normal: {
                            color: colorPie[4]
                        }
                    },
                    name: '销售额',
                    type: 'line',
                    data: [],

                },{
                	itemStyle: {
                        normal: {
                            color: colorPie[6]
                        }
                    },
                    name: '薪酬总额',
                    type: 'line',
                    data: [],

                }]
            },
            getRequestData: function (organId, year) {
                var self = this;
                $.get(urls.getSalaryCostSalesProfit, {
                    'organId': organId
                }, function (rs) {
                    if (_.isEmpty(rs)) {
                        showEmptyTip($('#' + self.chartId).parent());
                        return;
                    }
                    removeEmptyTip($('#' + self.chartId).parent());
                    self.resultData = rs;
                    self.initData(organId);
                });
            },
            initData: function (organId) {
                var self = this;
                var xAxisData = [], series0 = [], series1 = [], series2 = [], series3 = [];
                $.each(self.resultData, function (i, item) {
                    xAxisData.push(item.yearMonth);
                    series0.push(Tc.formatFloat(item.cost));
                    series1.push(Tc.formatFloat(item.gainAmount));
                    series2.push(Tc.formatFloat(item.salesAmount));
                    series3.push(Tc.formatFloat(item.sumPay));
                });
                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = series0;
                self.option.series[1].data = series1;
                self.option.series[2].data = series2;
                self.option.series[3].data = series3;
                self.initEcharts();
            },
            initEcharts: function () {
                var self = this;
                self.chartObj = echarts.init(document
                    .getElementById(self.chartId));
                self.chartObj.setOption(self.option);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [];
                        $.each(v.data, function (index, o) {
                            cols.push({
                                name: o.name,
                                value: (o.value).toFixed(2) ,
                                unit:"万元"
                            });
                        });
                        return {
                            title: v.name,
                            cols: cols
                        };
                    }
                });
            },
            init: function (reqOrgId, year) {
                var self = this;
                self.getRequestData(reqOrgId, year);
            },
            resize: function () {
                this.chartObj.resize();
            }
        };
    
  
    /**
     * 薪酬差异性 行业分位值年度趋势
     */

    var hangYeNianDuqsObj = {
            chartId: "hangYeNianDuqsChart",
            resultData: null,
            chart: null,
            option: {
            	animation:animation,
                grid: defaultGrid,
                legend:getDefaultLegend(['行业分位值']),
                xAxis: [{
                    type: "category",
                    splitLine: false,
                    axisTick: false,
                    data: [],
                    axisLine: false,
                    axisLabel: {
                        /*rotate: 20,*/
                        itemStyle: {
                            color: '#000000'
                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontFamily: fontFamily
                        }
                    }
                }],
                yAxis: [{
                    type: 'value',
                    name: "(" + measurement + ")",
                    splitNumber: 4,
                    splitLine: splitLine,
                    axisLine: false,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                series: [ {
                	itemStyle: {
                        normal: {
                            color: lineColor
                        }
                    },
                    name: '行业分位值',
                    type: 'line',
                    data: [],

                },]
            },
            getRequestData: function (organId, year) {
                var self = this;
                $.get(urls.getSalaryBitValueYear, {
                    'organId': organId
                }, function (rs) {
                    if (_.isEmpty(rs)) {
                        showEmptyTip($('#' + self.chartId).parent());
                        return;
                    }
                    removeEmptyTip($('#' + self.chartId).parent());
                    self.resultData = rs;
                    self.initData(organId);
                });
            },
            initData: function (organId) {
                var self = this;
                var xAxisData = [], seriesData1 = [], seriesData2 = [], seriesData3 = [], seriesData4 = [];
                $.each(self.resultData, function (i, item) {
                    xAxisData.push(item.year + '年');
                    seriesData1.push(Tc.formatFloat(item.bitValue));
                });
                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData1;
                self.initEcharts();
            },
            initEcharts: function () {
                var self = this;
                self.chart = echarts.init(document
                    .getElementById(self.chartId));
                self.chart.setOption(self.option);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chart,
                    formatter: function (i, v, p) {
                        var cols = [];
                        $.each(v.data, function (index, o) {
                            cols.push({
                                name: o.name,
                                value: (o.value).toFixed(2) ,
                                unit:"万元"
                            });
                        });
                        return {
                            title: v.name,
                            cols: cols
                        };
                    }
                });
            },
            init: function (reqOrgId, year) {
                var self = this;
                self.getRequestData(reqOrgId, year);
            },
            resize: function () {
                this.chart.resize();
            }
        };
    
    
    
    /**
     * 薪酬差异性 员工CR值

     */
    var yuanGongCRObj = {
   		 chartId:"yuanGongCRChart",
		chart: null,
        option:{
       	 animation:animation,
            grid: organGrid,
            xAxis: [
					{
						name:"(万元)",
						 splitNumber: 4,
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
            yAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#333'
                    }
                },
                data: []
            }],
            series: [
                {
                	clickable:false,
                    type: "bar",
                    barWidth :barWidth,
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
            ],
            color: ["#3285C7"]
        },
        init:function(orgId){
        	 var self = this;
             if (self.chart == null) {
                 self.chart = initEChart(self.chartId);
             }
             self.organId =orgId;
            // if (self.organId != orgId) {
                 self.requestData(orgId);
          //   }
            
        }, requestData: function (orgId) {
            var self = this;
            $.get(urls.getSalaryEmpCR, {organId: orgId}, function (result) {
            	if (!_.isEmpty(result)) {
                    self.render(result);
                } else {
                	showEmptyTip($('#' + self.chartId));
                }
            });
        },render: function (data) {
        	var self=this;
        	if(data==undefined){
                console.info("员工CR值出错了！");
            }else{
           	 var len=0;
            	  var yAxisData = [], seriesData = [];
                  var tem=[];
                $.each(data.list,function(n,v){
                	if (len < 5) {
                   tem.push({"name":data.list[len].empName,"value":data.list[len].crValue});
                   len++;
                	}
                });
                scalChartHeight(this,len);
                tem= _.sortBy(tem,"value").sort();
                  $.each(tem, function (i, item) {
                      yAxisData.push(formatAxis(item.name));
                      seriesData.push(Tc.formatFloat(item.value));
                  });
                  if(yAxisData.length>0) {
                      self.option.yAxis[0].data = yAxisData;
                      self.option.series[0].data = seriesData;
                      self.chart.setOption(self.option);
                      $('#' + self.chartId).chartTooltip({chart:self.chart,formatter:function(index,v,p){
                          var cols = [];
                          $.each(v.data, function (index, o) {
                              cols.push({
                                  name: '员工CR值',
                                  value: (o.value).toFixed(2) ,
                                  unit:""
                              });
                          });
                          return {
                              title: v.name,
                              cols: cols
                          };
                      }});
                  }else{
                      if(self.chart)
                        self.chart.dispose();
                      $("#avgpriceCostChart").html("<div class='loadingmessage'>暂无数据</div>");
                  }
            }
        },
        resize:function(){
       	 this.chart.resize();
        }
    }
    
    /**
     * 薪酬差异度岗位表
     */

    var xinCChayGangwbGridObj = {
        gridId:"#xinCChayGangwbGrid",
		PanelId:"#xinCChayGangwbGridPanel",
		scroll:true,
		load:false,
        option:{
            url: urls.getSalaryDifferencePost,
            datatype: "json",
            postData: {},
            mtype: 'POST',
//            rowHeight:51,
            autowidth: true,
           // rownumbers:true,
            colNames: ['<span class="grid-th">序号</span>',  '<span class="grid-th">岗位名称</span>', '<span class="grid-th">岗位薪酬差异度</span>'],
            colModel: [
                {name: 'id', index: 'id',sortable: false, align: 'center'},
                {name: 'positionName', index: 'positionName',sortable: false, align: 'center'},
                {name: 'difference', index: 'difference',sortable: false, align: 'center'}
            ],gridComplete:function(xhr){
                var _this = $(xinCChayGangwbGridObj.gridId);
                var ids =  _this.jqGrid('getDataIDs'); 
                var j=1;
                for (var i = 0; i < ids.length; i++) {
                    var curRowData = $(xinCChayGangwbGridObj.gridId).jqGrid('getRowData', _this[i]);   
                    //更改:更改id的值，当然也可用   rownumbers:true,获取序列，但是不会自动居中
                    $.extend(curRowData, {"id":j+i})   
                    _this.jqGrid('setRowData', ids[i], curRowData);   
                }  
             },
            scroll: true
        },
        init: function (organId) {
        	var self=this;
        	if(!this.load){
				this.load=true;
				 this.option.postData = {organId: organId};
		            $(this.gridId).jqGrid(this.option);
        	}else{
        		$(self.gridId).clearGridData().setGridParam({
	    			postData: {organId: organId}
	    		}).trigger("reloadGrid");
        	}
            this.resize();
        },
        resize: function () {
        	var self=this;
        	if(window.screenFull){
        		$(self.gridId).setGridWidth($(self.PanelId).width()+22);
        	}else{
        		if(this.scroll){
        			$(self.gridId).setGridWidth($(self.PanelId).width()+22);
        		}else{
        			$(self.gridId).setGridWidth($(self.PanelId).width());
        		}
        		
        	}
            $(self.gridId).setGridHeight($(self.PanelId).height());
        }
    };
    
    
    
    /**
     * 年终奖分析
     */

    var trainDetailObj = {
            chartId: "manpowerCostTrendsChars",
            resultData: null,
            chartObj: null,
            option: {
            	animation:animation,
                grid:defaultDoubleGrid,
                legend:getDefaultLegend(['年终奖', '利润']),
                xAxis: [{
                    type: "category",
                    splitLine: false,
                    axisTick: false,
                    data: [],
                    axisLine: false,
                    axisLabel: {
                        /*rotate: 20,*/
                        itemStyle: {
                            color: '#000000'
                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontFamily: fontFamily
                        }
                    }
                }],
                yAxis: [{
                    type: 'value',
                    name: "(" + measurement + ")",
                    splitNumber: 4,
                    splitLine: splitLine,
                    axisLine: false,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                series: [{
                	itemStyle: {
                        normal: {
                            color: colorPie[0]
                        }
                    },
                    name: '年终奖',
                    type: 'line',
                    data: [],

                }, {
                	itemStyle: {
                        normal: {
                            color: colorPie[2]
                        }
                    },
                    name: '利润',
                    type: 'line',
                    data: [],

                },]
            },
            getRequestData: function (organId, year) {
                var self = this;
                $.get(urls.getSalaryBonusProfit, {
                    'organId': organId
                }, function (rs) {
                    if (_.isEmpty(rs)) {
                        showEmptyTip($('#' + self.chartId).parent());
                        return;
                    }
                    removeEmptyTip($('#' + self.chartId).parent());
                    self.resultData = rs;
                    self.initData(organId);
                });
            },
            initData: function (organId) {
                var self = this;
                var xAxisData = [], seriesData1 = [], seriesData2 = [], seriesData3 = [], seriesData4 = [];
                $.each(self.resultData, function (i, item) {
                    xAxisData.push(item.year + '年');
                    seriesData1.push(Tc.formatFloat(item.bonus));
                    seriesData2.push(Tc.formatFloat(item.profit));
                });
                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData1;
                self.option.series[1].data = seriesData2;
                self.initEcharts();
            },
            initEcharts: function () {
                var self = this;
                self.chartObj = echarts.init(document
                    .getElementById(self.chartId));
                self.chartObj.setOption(self.option);
                $('#' + self.chartId).chartTooltip({
                    chart: self.chartObj,
                    formatter: function (i, v, p) {
                        var cols = [];
                        $.each(v.data, function (index, o) {
                            cols.push({
                                name: o.name,
                                value: (o.value).toFixed(2) ,
                                unit:"万元"
                            });
                        });
                        return {
                            title: v.name,
                            cols: cols
                        };
                    }
                });
            },
            init: function (reqOrgId, year) {
                var self = this;
                self.getRequestData(reqOrgId, year);
            },
            resize: function () {
                this.chartObj.resize();
            }
        };
    
    
    var objRequestData={
            data:null,
            init:function(organId){
                var self=this;
                    self.intdats(organId);
            },
            intdats:function(organId){
            	avgpriceCostObj.init(organId);
            	 $("#allSeq").children().on(clickEvent,function(){
               		 var _t=this;
               		 setBtnClass(_t);
                   	   var seqId=$(this).attr("data");
                   	   if(seqId==""){
                   		avgpriceCostObj.init(organId);
                   		 setBtnClass(_t);
                   	   }else{
                   		   if(seqId!=undefined){
                   			avgpriceCostObj.init(organId,seqId);
                   			 setBtnClass(_t);
                   		   }
                   	   }
                      });
            }
        }
 
    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }
    
    $.screenZoom([
    	trainCostObj,
    	avgpriceCostObj,
    	nianDuQuShiObj,
    	yuDuQuShiObj,
    	hangYeNianDuqsObj,
    	yuanGongCRObj,
    	xinCChayGangwbGridObj
    ]);
    
    topObj.init(reqOrganId);
    objRequestData.init(reqOrganId);
    trainCostObj.init(reqOrganId);
    avgpriceCostObj.init(reqOrganId);
    nianDuQuShiObj.init(reqOrganId);
    yuDuQuShiObj.init(reqOrganId);
    hangYeNianDuqsObj.init(reqOrganId);
    yuanGongCRObj.init(reqOrganId);
    xinCChayGangwbGridObj.init(reqOrganId);

});