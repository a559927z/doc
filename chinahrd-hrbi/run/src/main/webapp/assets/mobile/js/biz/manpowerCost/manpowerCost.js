require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'underscore','utils', "appBase", "chartTooltip",'gaugeAuxiliary'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        getTrendByMonthUrl: webRoot + "/mobile/manpowerCost/getTrendByMonth.do", // 成本月度趋势
        getOrganCostUrl: webRoot + "/mobile/manpowerCost/getOrganCost.do", // 各架构人力成本
        getItemDetailUrl: webRoot + "/mobile/manpowerCost/getItemDetail.do", // 人力成本结构
        getAllDetailDataUrl: webRoot + "/mobile/manpowerCost/getAllDetailData.do", // 销售 成本 利润 明细
        getProportionYearUrl: webRoot + "/mobile/manpowerCost/getProportionYear.do", // 人力成本占比（按年同比）
    };

    var ecConfig = require('echarts/config');
    var reqOrganId = $("#reqOrganId").val();
    var reqOrganName = $("#reqOrganName").val();

    function loadLSL() {

        var windownWidth = $(window).width();
        $("#LSLCostNormal").css("width", windownWidth - 127);

    }

    $(window).bind(resizeEvent, function () {
        loadLSL();
      /*  costBudgetObj.resize();
        trendByMonthObj.resize();
        manpowerTrendObj.resize();
        contrastOrgObj.resize();
        contrastDetailObj.resize();*/
    });

    loadLSL();
    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab',
        function (e) {
            var targetAreaId = $(e.target).attr('aria-controls');
            if (targetAreaId == "zxqkPanel") {
                trendByMonthObj.resize();
            } else if (targetAreaId == "qsbhPanel") {
                manpowerTrendObj.resize();
            } else if (targetAreaId == "jgdbPanel") {
                contrastOrgObj.resize();
            } else if (targetAreaId == "cbjgPanel") {
                contrastDetailObj.resize();
            }
        });

    /**
     * 人力成本执行率
     *
     * @type
     */
    var costBudgetObj = {
        chartId: 'LSLChart',
        chartObj: null,
        normal: 0.8,
        option:  getDefaultGaugeOption("人力成本执行率"),
        init: function (budget, total) {
            var self = this;
            self.chartObj = echarts.init(document.getElementById(self.chartId));
            var rate = 0;
            if (total != 0 && budget != 0) {
                rate = total / budget;
            }
            var normal = rate > 1 ? self.normal / rate : self.normal;
            var max = rate > 1 ? rate : 1;
            self.initChart(normal, rate, max);
        },

        initChart: function (normal, rate, max) {
            var self = this;
            self.max = parseFloat((max * 100).toFixed(2));
            self.option.series[0].max = self.max;
            self.option.series[0].axisLine.lineStyle.color[0] = [Tc.formatFloat(normal), '#61bb33'];
            if (rate > 1) {
            	self.option.series[0].pointer.color= '#ff5b5a';
                self.option.series[0].axisLine.lineStyle.color[1] = [Tc.formatFloat(1 / rate), '#ffc000'];
                self.option.series[0].axisLine.lineStyle.color[2] = [Tc.formatFloat(1), '#ff3232'];
            } else {
            	if(rate<=normal){
            		self.option.series[0].pointer.color= '#61bb33';
            	}else{
            		self.option.series[0].pointer.color= '#ff3232';
            	}
                self.option.series[0].axisLine.lineStyle.color[1] = [Tc.formatFloat(1), '#ffc000'];
            }

            self.option.series[0].data[0].value = Tc.formatFloat(rate * 100);
            $('#' + self.chartId).gaugeAuxiliary({option:self.option,
            	textColor:self.option.series[0].pointer.color,
            	text:(rate*100).toFixed(1)+"%"});
            self.chartObj.setOption(self.option, true);

            //定义文本信息
            //$('.top-chart-normal .cost-main-number').text(Tc.formatFloat(rate * 100));
            $('.top-chart-icon-value .top-chart-icon-note-warn').text((self.normal * 100) + '% - ' + '100%');
            $('.top-chart-icon-value .top-chart-icon-note-over').text('100% 以上');
            if (rate <= self.normal) {
//                $('.top-chart-content-div .top-chart-number').css('color', '#72B031');
//                $('.top-chart-content-div .top-chart-content').css('color', '#61bb33');
                $('.top-chart-content-div .top-chart-content').html('执行率正常,请继续保持！');
            } else if (rate < 1 && self.normal < rate) {
//                $('.manpowercost-normal .top-chart-number').css('color', '#F1A502');
//                $('.top-chart-content-div .top-chart-content').css('color', '#ffc000');
                $('.top-chart-content-div .top-chart-content').html('执行率偏高,请持续关注！');
            } else {
//                $('.manpowercost-normal .top-chart-number').css('color', '#D4531A');
//                $('.top-chart-content-div .top-chart-content').css('color', '#ff5b5a');
                $('.top-chart-content-div .top-chart-content').html('执行率超标,状态异常！');
            }
        },
        resize: function () {
            this.chartObj.resize();
        }
    };

    /**
     * 人力成本占比
     */
    var manpowerCostTrendsObj = {
        obj: $("#manpowerCostTrends"),
        render: function (data) {
            var self = this;
            if (_.isEmpty(data)) {
                self.obj.find(".currentMonth").text("");
            } else {
                self.obj.find(".currentMonth").text(data.currentMonth);
            }
        },
        calResult: function (result) {
            var data = {
                currentMonth: 0
            };
            $.each(result, function (i, o) {
                if (o.type == 1) {
                    data.currentMonth = Tc.formatFloat(o.cost / o.total * 100) + "%";
                }
            });

            return data;
        },
        init: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getProportionYearUrl,
                data: {
                    organId: orgId
                },
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }
    };

    /**
     * ] 月度趋势
     */

    var trendByMonthObj = {
        chartId: "trendByMonth",
        chartObj: null,
        option: {
        	animation:animation,
            grid: {
                borderWidth: 0,
                x: 50,
                y: 40,
                x2: 0,
                y2: 49
            },
            xAxis: [{
                type: "category",
                splitLine: false,
                axisTick: false,
                data: [],
                axisLine: false,
                axisLabel: {
                    rotate: 30,
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
            color: ['#23C6C8'],
            yAxis: [{
                name: "(" + measurement + ")",
                splitLine: false,
                type: 'value',
                splitLine:splitLine,
                axisLabel: {
                    itemStyle: {
                        color: 'black'
                    },
                    textStyle: {
                        color: 'black',
                        fontSize: 12,
                        fontStyle: 'normal',
                        fontWeight: 'normal'
                    }
                },
                axisLine: false
            }],
            series: [
                {
                    type: 'bar',
                    stack: '成本',
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
                }, {
                    name: '成本',
                    type: 'bar',
                    barWidth: barWidth,
                    stack: '成本',
                    itemStyle: {
                        normal: {
                            color: function (a) {
                                if (a.dataIndex == 0) {
                                    return colorPie[0];
                                } else if (a.dataIndex == 1) {
                                    return colorPie[2];
                                } else {
                                    return colorPie[4];
                                }

                            }
                        }
                    },
                    data: []
                }]
        },
        init: function (orgId) {
            var self = this;
            if (this.chartObj == null) {
                this.chartObj = echarts.init(document
                    .getElementById(this.chartId));
            }
            this.getData(orgId);
        },
        getData: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getTrendByMonthUrl,
                // type:"post",
                data: {
                    organId: orgId
                },
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    } else {
                        this.option.xAxis[0].data = [];
                        this.option.series[0].data = [];
                        this.option.series[1].data = [];
                        self.chartObj.setOption(this.option, true);
                    }

                }
            });
        },
        render: function (result) {
            var lastData = [0, 0];
            var data = [(result.budget).toFixed(2),
                (result.total).toFixed(2)];
            //costBudgetObj.init(1100, 3700);
            costBudgetObj.init(result.budget, result.total);
            var xdata = ['当年预算', '当年累计'];
            var record = 0;
            var startIndex = result.detail.length - 7;
            if (startIndex < 0) {
                startIndex = 0;
            }
            $.each(result.detail, function (i, o) {
                if (i < startIndex) {
                    record += o.cost;
                }
            });
            $.each(result.detail, function (i, o) {
                if (i >= startIndex) {
                    lastData.push(record);
                    data.push(o.cost);
                    var month = parseInt((o.yearMonth + "").substring(
                            4, 6))
                        + "月";
                    xdata.push(month);
                    record += o.cost;
                }
            });
            var max = result.total > result.budget ? result.total
                : result.budget;
            this.option.xAxis[0].data = xdata;
            this.option.yAxis[0].max = max + 30;
            this.option.series[0].data = lastData;
            this.option.series[1].data = data;
            this.chartObj.setOption(this.option, true);
            var self = this;
            $('#' + self.chartId).chartTooltip({
                chart: self.chartObj,
                formatter: function (i, v, p) {
                    var cols = [{
                        name: v.name,
                        value: (v.data[1].value * 1).toFixed(2) ,
                        unit:"万元"
                    }];
                    return {
                        title: "",
                        cols: cols
                    };
                }
            });

        },
        resize: function () {
            this.chartObj.resize();
        }
    }

    // 人力成本趋势
    var manpowerTrendObj = {
        chartId: "manpowerCostTrendsChars",
        resultData: null,
        echartObj: null,
        option: {
        	animation:animation,
            grid: {
                borderWidth: 0,
                x: 50,
                y: 20,
                x2: 0,
                y2: 55
            },
            legend:getDefaultLegend(['人力成本', '利润', '销售额', '人均成本']),
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

            }, {
            	itemStyle: {
                    normal: {
                        color: colorPie[4]
                    }
                },
                name: '销售额',
                type: 'line',
                data: [],

            }, {
            	itemStyle: {
                    normal: {
                        color: colorPie[6]
                    }
                },
                name: '人均成本',
                type: 'line',
                data: [],

            },]
        },
        getRequestData: function (organId, year) {
            var self = this;
            $.get(urls.getAllDetailDataUrl, {
                'organId': organId,
                'time': year
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
                var month = parseInt((item.yearMonth + "").substring(4,
                        6))
                    + "月";
                xAxisData.push(month);
                seriesData1.push(item.cost);
                seriesData2.push(item.gainAmount);
                seriesData3.push(item.salesAmount);
                seriesData4.push(item.costAvg);
            });
            self.option.xAxis[0].data = xAxisData;
            self.option.series[0].data = seriesData1;
            self.option.series[1].data = seriesData2;
            self.option.series[2].data = seriesData3;
            self.option.series[3].data = seriesData4;
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
                },
                render: function (parm) {
                    var result = '<div class="chart-tooltip" >';
                    result += '<div class="chart-tooltip-title">'
                        + parm.title + '</div>';
                    result += '<div>';
                    if (!_.isEmpty(parm.cols)) {
                        $.each(parm.cols, function (i, o) {
                            result += '<div style="margin: 0 6px 0 4px;display: inline-block;">';
                            result += '<label style="font-size:11px;">' + o.name + ':' + o.value + '</label>';
                            result += '</div>';
                        });
                    }
                    result += '</div>';
                    result += '</div>';
                    return result;
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

    // 各架构人力成本
    var contrastOrgObj = {
        chartId: "contrastOrgChart",
        resultData: null,
        chartObj: null,
        option: {
        	animation:animation,
            xAxis: [{
                type: "value",
                splitLine: splitLine,
                name: "(" + measurement + ")",
                axisLine:false, 
                splitNumber: 3
            }],
            yAxis: [{
                type: "category",
                splitLine: false,
                axisTick: false,
                data: [],
                axisLine:false,
                axisLabel: {
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
            series: [{
                type: "bar",
                data: [],
                itemStyle: {
                    normal: {
                    	color: barColor,
                        label: {
                            show: true,
                            textStyle: {
                                color: "rgb(34, 34, 34)"
                            }
                        }
                    }
                },
                barWidth: barWidth
            }],
            grid: organGrid,
        },
        getRequestData: function (organId) {
            var self = this;
            $.get(urls.getOrganCostUrl, {
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
            var yAxisData = [], seriesData = [];
            var name = "", len = 0;
            $.each(self.resultData, function (i, item) {
                name = item.organ;
                len = name.length;
                yAxisData.push(len > 8 ? name.substring(0, 8) + "\n"
                + name.substring(8, len) : item.organ);
                seriesData.push(item.cost);
            });

            self.option.yAxis[0].data = yAxisData;
            self.option.series[0].data = seriesData;
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
                    var cols = [{
                        name: v.name,
                        value: (v.data[0].value).toFixed(2) ,
                        unit:"万元"
                    }];
                    return {
                        title: "",
                        cols: cols
                    };
                },toolTipTop:-93
            });
        },
        init: function (reqOrgId) {
            var self = this;
            self.getRequestData(reqOrgId);
        },
        resize: function () {
            this.chartObj.resize();
        }
    };

    // 人力成本结构
    var contrastDetailObj = {
        chartId: "contrastDetailChart",
        resultData: null,
        chartObj: null,
        option: getDefaultPieOption({name: "成本结构", toroidal: true}),
        getRequestData: function (organId) {
            var self = this;
            $.get(urls.getItemDetailUrl, {
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
            var legendData = [], seriesData = [], legend = [], total = 0;

            $.each(self.resultData, function (i, item) {
                total += item.cost;
            });
            $.each(self.resultData, function (i, item) {
                // legendData.push(item.itemName);
                legend.push('<div>' +
                    '<label style="background-color: ' + colorPie[i] + ';"></label>' +
                    '<span>' + item.itemName + '</span>' +
                    '</div>');
                seriesData.push({
                    "value": item.cost,
                    "name": item.itemName
                });
            });

            appendLegend($("#" + self.chartId), legend);
            self.option.series[0].data = seriesData;
            self.initEcharts();
            $('#' + self.chartId).chartTooltip({
                chart: self.chartObj,
                formatter: function (p) {
                    var cols = [];
                    $.each(self.resultData, function (i, item) {
                        // legendData.push(item.itemName);
                        cols.push({
                            name: item.itemName,
                            value: (item.cost).toFixed(2),
                            rate: "(" + (item.cost / total * 100).toFixed(2) + "%)"
                        });

                    });

                    return {
                        color: colorPie,
                        title: "",
                        note: "说明：统计单位为(万元)",
                        cols: cols
                    };
                }
            });
        },
        initEcharts: function () {
            var self = this;
            self.chartObj = echarts.init(document
                .getElementById(self.chartId));
            self.chartObj.setOption(self.option);
        },
        init: function (organId) {
            var self = this;
            self.getRequestData(organId);
        },
        resize: function () {
            this.chartObj.resize();
        }
    };
    trendByMonthObj.init(reqOrganId);
    manpowerCostTrendsObj.init(reqOrganId);
    manpowerTrendObj.init(reqOrganId);
    contrastOrgObj.init(reqOrganId);
    contrastDetailObj.init(reqOrganId);
    $.screenZoom([
                 	costBudgetObj,
                 	trendByMonthObj,
                 	manpowerTrendObj,
                 	contrastOrgObj,
                 	contrastDetailObj
                 ]);
});