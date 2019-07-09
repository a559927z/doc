require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge', 'bootstrap',
    'jgGrid', 'underscore', 'utils', 'vernierCursor', "jquery-mCustomScrollBar", "appBase", "chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        trendUrl: webRoot + "/mobile/perBenefit/getTrendData.do", // 人均效益趋势
        perBenefitUrl: webRoot + "/mobile/perBenefit/getPerBenefitData.do", // 人均效益
        avgValueUrl: webRoot + "/mobile/perBenefit/getAvgValueData.do", // 平均效益
        getPerBenefitInfoUrl: webRoot + "/mobile/perBenefit/getPerBenefitInfo.do", // 人均效益  营业额   员工数环比
        orgBenefitUrl: webRoot + "/mobile/perBenefit/getOrgBenefitData.do", // 当前组织人均效益

    };
    var reqOrganId = $("#reqOrganId").val();
    var reqOrganName = $("#reqOrganName").val();
    var year = $("#year").val();

    var timeRange = $("#timeRange").val();

    function loadRunOffPanel() {
        var windownWidth = $(window).width();
        $("#runOffDetailPanel").css("width", windownWidth);
    }

    loadRunOffPanel();
    $(window).bind(resizeEvent, function () {
        loadRunOffPanel();
        perBenefitObj.resize();
        trendChartObj.resize();
        inferiorChart.resize();
        empComposingObj.resize();
    });
//    var frames = window.parent.document.getElementById("mainFrame");
//    var winObj = !frames ? window : window.parent;

    $('#tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var targetAreaId = $(e.target).attr('aria-controls');
        if (targetAreaId == "ztqsPanel") {
            perBenefitObj.resize();
        } else if (targetAreaId == "ydqsPanel") {
            trendChartObj.resize();
        } else if (targetAreaId == "xjdbPanel") {
            inferiorChart.resize();
        } else if (targetAreaId == "ygsgcPanel") {
            empComposingObj.resize();
        }
    });

    function initPerBenefit(organizationId) {
        $.ajax({
            url: urls.perBenefitUrl,
            data: {organizationId: organizationId, year: year},
            success: function (data) {
                if (_.isEmpty(data)) {
                    showEmptyTip($('.perBenefit .widget-body'));
                    return;
                }
                removeEmptyTip($('.perBenefit .widget-body'));
                var lastMonth = data;

                //营业利润
                var profitValue = (lastMonth.profit).toFixed(1);
                $(".perBenefit .benefit-profit").text(Tc.formatNumber(profitValue, ","));
                //等效全职员工数
                var eqEmpNum = lastMonth.eqEmpNum;
                $(".perBenefit .eqEmpNum").text(Tc.formatNumber(eqEmpNum, ","));
//				$(".benefit-note .time").text("(统计时间：2016.6)");
                //人均效益
                //var benefitValue = lastMonth.benefitValue;
                var benefitValue = (profitValue / eqEmpNum).toFixed(1);
                $(".perBenefit .benefit-value").text(Tc.formatNumber(benefitValue, ","));

                // 行业均值
                initAvgValue(organizationId, benefitValue);
            }
        });
    }

    /**
     * 行业均值预警
     */
    function initAvgValue(organizationId, benefitValue) {
        $.ajax({
            url: urls.avgValueUrl,
            data: {organizationId: organizationId},
            success: function (data) {
                $('.trade-warn').empty();
                if (!data) {
                    showWarnLess($('.trade-warn'), '暂无行业均值数据');
                    return;
                }
                var tradeAver = data;
                //与行业均值比较的百分比
                var tradePercent = ((benefitValue - tradeAver) / tradeAver * 100).toFixed(0);
                $('.trade-warn').append(getTradeWarn(data, tradePercent));
            }
        });
    }

    function getTradeWarn(tradeAver, tradePercent) {
        var detailMsg = '月行业均值<label class="orange bolder">&nbsp;' + tradeAver.toFixed(2) + '&nbsp;</label>万元';
        var arrowClass = '';
        var eqText = '';
        var msgClass = '';
        if (tradePercent > 0) {
            arrowClass = 'smile-face';
            eqText = '高于行业50分位值';
            msgClass = 'blue';
            tradePercent = tradePercent + '%';
        } else if (tradePercent < 0) {
            arrowClass = 'warn-icon';
            eqText = '低于行业50分位值';
            msgClass = 'orange';
            tradePercent = Math.abs(tradePercent) + '%';
        } else {
            eqText = '等于行业50分位值';
            tradePercent = "";
        }

        $(".benefit-note .icon").addClass(arrowClass);
        $(".trade-warn").text(eqText);
        $(".benefit-note .value").addClass(msgClass);
        $(".benefit-note .value").text(tradePercent);
//		var warnMsg = eqText; 
//		trade-warn
        //	return warnTpl(tradePercent >= 0,detailMsg,warnMsg,arrowClass);
    }

    function showWarnLess(targetDom, text) {
        var tipDom = $('<div>', {
            'class': 'text-center',
            'style': 'line-height:' + targetDom.height() + 'px',
            'text': text
        });
        targetDom.append(tipDom);
    }


    /**
     * 整体趋势
     */
    var perBenefitObj = {
        profitChartId: 'profitChart',
        benefitChartId: 'benefitChart',
        empNumChartId: 'empNumChart',
        option: {
            grid: {
                borderWidth: 0,
                x: 40,
                y: 40,
                x2: 40,
                y2: 40
            },
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
                    }, formatter: function (value) {
                        return parseInt((value + "").substring(4, 6)) + "月";
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: false,
                splitNumber: 5,
                nameTextStyle: {
                    color: 'black'
                },
                axisLine: {
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                }
            }],
            series: [{
                type: 'line',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: '#23C6C8',
                        label: {
                            show: true,
                            textStyle: {
                                color: '#333'
                            },
                            formatter: function (a) {
                                return a.value;
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
                            color: '#1AB394',
                            lineStyle: {
                                type: 'solid'
                            }
                        }
                    },
                    data: []
                }
            }]
        },
        init: function (organId) {
            var self = this;
            /*  var height = 320;
             $('#' + self.profitChartId).height(height);
             $('#' + self.benefitChartId).height(height);
             $('#' + self.empNumChartId).height(height);
             */
            self.profitObj = echarts.init(document.getElementById(self.profitChartId));
            self.benefitObj = echarts.init(document.getElementById(self.benefitChartId));
            self.empNumObj = echarts.init(document.getElementById(self.empNumChartId));
            self.profitOption = $.extend(true, {}, self.option);
//            self.profitOption.tooltip={
//                trigger: 'item',
//                axisPointer: {type: 'none'},
//                formatter: function (params, ticket, callback) {
//                	var cols=[{name:"营业利润",value:params.value+"万元"}];
//                    var html=formatterChartTooltip({title:params.name,cols:cols});
//                    return html;
//                }
//            }
            self.benefitOption = $.extend(true, {}, self.option);
//            self.benefitOption.tooltip={
//                    trigger: 'item',
//                    axisPointer: {type: 'none'},
//                    formatter: function (params, ticket, callback) {
//                    	var cols=[{name:"人均效益",value:params.value+"万元"}];
//                        var html=formatterChartTooltip({title:params.name,cols:cols});
//                        return html;
//                    }
//                }
            self.empNumOption = $.extend(true, {}, self.option);
//            self.empNumOption.tooltip={
//                    trigger: 'item',
//                    axisPointer: {type: 'none'},
//                    formatter: function (params, ticket, callback) {
//                    	console.log(params);
//                    	var cols=[{name:"员工数",value:params.value+"人"}];
//                        var html=formatterChartTooltip({title:params.name,cols:cols});
//                        return html;
//                    }
//                }
            self.extendsData(organId);
        },
        initData: function (data) {
            var self = this;
            var perfData = self.perfData;
            if (!$.isEmptyObject(data)) {
                var xaxisData = [];
                var perBenefitData = [];
                var profitData = [];
                var empData = [];
                $.each(data, function (n, v) {
                    xaxisData.push(v.yearMonth);
                    perBenefitData.push((v.benefitValue).toFixed(2));
                    profitData.push((v.profit).toFixed(2));
                    empData.push(v.eqEmpNum);
                });
                if (data.length > 2) {
                    var prevObj = data[data.length - 2];
                    var lastObj = data[data.length - 1];
                    var empMsg = "";
                    if (lastObj.benefitValue > prevObj.benefitValue) {
                        if (lastObj.profit > prevObj.profit) {
                            if (lastObj.eqEmpNum > prevObj.eqEmpNum) {
                                empMsg = "人均效益上升,是由于利润上升幅度高于人员数增加幅度";
                            } else if (lastObj.eqEmpNum == prevObj.eqEmpNum) {
                                empMsg = "人均效益上升,是由于润上升，但人员数持平";
                            } else {
                                empMsg = "人均效益上升,是由于润上升，且人员数在减少";
                            }
                        } else if (lastObj.profit == prevObj.profit && lastObj.eqEmpNum < prevObj.eqEmpNum) {
                            empMsg = "人均效益上升,是由于虽然利润持平，但人员数减少";
                        } else if (lastObj.eqEmpNum < prevObj.eqEmpNum) {
                            empMsg = "人均效益上升,是由于利润持平,但人员数减少";

                        }
                    } else if (lastObj.benefitValue == prevObj.benefitValue) {
                        if (lastObj.profit > prevObj.profit && lastObj.eqEmpNum > prevObj.eqEmpNum) {
                            empMsg = "人均效益持平,是由于利润上升幅度与人员数增加幅度持平";
                        } else if (lastObj.profit == prevObj.profit && lastObj.eqEmpNum == prevObj.eqEmpNum) {
                            empMsg = "人均效益持平,是由于虽然利润与人员数维持一致";
                        } else if (lastObj.eqEmpNum > prevObj.eqEmpNum) {
                            empMsg = "人均效益持平,是由于利润下降幅度与人员数减少幅度一致";
                        }
                    } else {
                        if (lastObj.profit > prevObj.profit && lastObj.eqEmpNum > prevObj.eqEmpNum) {
                            empMsg = "人均效益下降,是由于员工数增加幅度高于利润上升幅度";
                        } else if (lastObj.profit == prevObj.profit && lastObj.eqEmpNum > prevObj.eqEmpNum) {
                            empMsg = "人均效益下降,是由于利润持平，但员工数量增加";
                        } else if (lastObj.eqEmpNum > prevObj.eqEmpNum) {
                            empMsg = "人均效益下降,是由于利润下降，但员工数量增加";
                        } else if (lastObj.eqEmpNum == prevObj.eqEmpNum) {
                            empMsg = "人均效益下降,是由于利润下降，但员工数量不变";
                        } else {
                            empMsg = "人均效益下降,是由于利润下降幅度高于员工数下降幅度";
                        }
                    }
                }
                $(".xyzt").html("<span class='benefitWarn'></span>" + empMsg);
                self.initChart(self.profitObj, self.profitOption, xaxisData, profitData, "(万元)");

                $('#' + self.profitChartId).chartTooltip({
                    chart: self.profitObj, formatter: function (index, v, p) {

                        var cols = [{name: '营业利润', value: (v.data[0].value * 1.0).toFixed(2) + "万元"}];

                        return {title: v.name, cols: cols};
                    }
                });
                self.initChart(self.benefitObj, self.benefitOption, xaxisData, perBenefitData, "(万元)");
                $('#' + self.benefitChartId).chartTooltip({
                    chart: self.benefitObj, formatter: function (index, v, p) {
                        var cols = [{name: '人均效益', value: (v.data[0].value * 1.0).toFixed(2) + "万元"}];

                        return {title: v.name, cols: cols};
                    }
                });
                self.initChart(self.empNumObj, self.empNumOption, xaxisData, empData, "(人数)");
                $('#' + self.empNumChartId).chartTooltip({
                    chart: self.empNumObj, formatter: function (index, v, p) {
                        var cols = [{name: '人数', value: v.data[0].value + "人"}];

                        return {title: v.name, cols: cols};
                    }
                });
            }
        },
        extendsData: function (organId) {
            var self = this;
            $.post(urls.getPerBenefitInfoUrl, {'organId': organId, year: year}, function (rs) {
                if (_.isEmpty(rs)) {
                    showEmptyTip($('#' + self.benefitChartId));
                } else {
                    removeEmptyTip($('#' + self.benefitChartId));
                    self.perBenefitData = rs;
                    self.initData(rs);
                }


            });
        },
        initChart: function (chartObj, option, xaxisData, seriesData, xName) {
            chartObj.clear();
            option.xAxis[0].data = xaxisData;
            option.yAxis[0].name = xName;
            option.series[0].data = seriesData;
            chartObj.setOption(option, true);
        }, resize: function () {
            var self = this;
            self.profitObj.resize();
            self.benefitObj.resize();
            self.empNumObj.resize();
        }
    };


    /**]
     * 月度趋势
     */
    /***
     * 人均效益趋势
     * @type
     */
    var trendChartObj = {
        chartId: 'ydqsChart',
        maxNum: 6,
        chartObj: null,
        chartOption: {
            calculable: false,
            legend: {
                data: ['人均效益', '环比变化'],
                y: 'top'
            },
            grid: {
                borderWidth: 0,
                x: 50,
                y: 40,
                x2: 40,
                y2: 40
            },
            color: ['#0099CB', '#B93C07'],
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: false,
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
                    },
                    formatter: function (value) {
                        var v = value.substring(3, 5);
                        if (v.indexOf("0") == 0) {
                            v = v.substring(1, 2);
                        }
                        v += "月";
                        return v;
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: false,
                name: "万元",
                nameTextStyle: {
                    color: 'black'
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        return value;
                    }
                }
            }, {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        return (value) + '%';
                    }
                }
            }],
//            tooltip: {
//                trigger: 'axis',
//                axisPointer: {type: 'none'},
////                position:function(a,b,c){
////                	var left=$("#"+trendChartObj.chartId).offset().left;
////                   var width=$("#"+trendChartObj.chartId).width();
////                	return [left+width/2-100,0];
////                },
//               
//                formatter: function (params, ticket, callback) {
//                    //var html = "<div>" + params[0].name + "<br>环比变化：" + (params[0].data * 10000 / 100).toFixed(2) + "%<br>主动流失率：" + (params[1].data * 10000 / 100).toFixed(2) + "%<div>";
//                	var cols=[{name:"人均效益",value:params[0].value+"万元"}];
//                	cols.push({name:"环比变化",value:params[1].value+"%"});
//                    var html=formatterChartTooltip({title:params[0].name,cols:cols});
//                    return html;
//                }
//            },
            series: [{
                yAxisIndex: 0,
                name: '人均效益',
                type: 'bar',
                barWidth: barWidth,
                clickable: false,
                barCategoryGap: '45%',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            position: 'insideBottom'
                        }
                    }
                },
                data: []
            }, {
                name: '环比变化',
                type: 'line',
                clickable: false,
                yAxisIndex: 1,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (a) {
                                return formatPercentage(a.value);
                            }
                        }
                    }
                },
                data: []
            }]
        },
        init: function (organId) {
            var self = this;
//            $('#' + self.chartId).height($('#' + self.chartId).parents('.bottom-div').height());
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
            var self = this;

            var xAxisData = [];
            var benefitData = [];
            var changeData = [];
            var result = trendData;
            var trendOption = _.clone(self.chartOption);
            $.each(result, function (i, o) {
                var yearMonthStr = o.yearMonth.substr(2, 2) + '/'
                    + o.yearMonth.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefitValue == null) {
                    o.benefitValue = 0;
                }
                benefitData.push(o.benefitValue.toFixed(2));
                changeData.push(o.changeValue >= 0 ? o.changeValue
                    : getNagativeItem(o.changeValue));

            });
            trendOption.xAxis[0].data = xAxisData;
            trendOption.series[0].data = benefitData;
            trendOption.series[1].data = changeData;
            self.extendsOption = trendOption;
        },
        resize: function () {
            this.chartObj.resize();
        },
        requestData: function (organId) {
            var self = this;
            $.ajax({
                url: urls.trendUrl,
                data: {
                    organizationId: organId, year: year
                },
                success: function (data) {
                    if (_.isEmpty(data)) {
                        showEmptyTip($('#' + self.chartId));
                        return;
                    }
                    removeEmptyTip($('#' + self.chartId));


                    self.extendData(data);
                    self.chartObj.setOption(self.extendsOption, true);
                    $('#' + self.chartId).chartTooltip({
                        chart: self.chartObj, formatter: function (i, v, p) {

                            var cols = [{name: "人均效益", value: (v.data[0].value * 1.0).toFixed(2) + "万元"}];
                            //	if(trendChartObj.hasParent){
                            if (typeof(v.data[1].value) == "string") {
                                cols.push({name: "环比变化", value: (v.data[1].value * 1.0).toFixed(2) + "%"});
                            } else {
                                cols.push({name: "环比变化", value: (v.data[1].value.value * 1.0).toFixed(2) + "%"});
                            }


                            //}
                            return {title: v.name, cols: cols};
                        }
                    });

                }
            });
        },
        extendData: function (source) {
            var self = this;
            source.reverse();
            // 上一个月 的数据（计算同比/环比 时用到）
            var preObj = null;
            var trendData = [];
            $.each(source, function (i, item) {
                // 将年月转为字符串
                item.yearMonth += "";
                if (i == 0) {
                    preObj = item;
                    item.changeValue = '0';
                    // 页面上最多展示6条数据，如果结果集为7条时，说名第一条结果集是用于计算同比/环比的，不需在图表上显示
                    if (source.length < 7) {
                        if (item.targetValue == null || item.targetValue == "null") {
                            item.targetValue = "";
                        }
                        if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                            if (item.targetValue == null || item.targetValue == "null") {
                                item.targetValue = "";
                            }
                            item.complianceRate = "100";
                        } else {
                            item.complianceRate = (item.benefitValue * 100 / item.targetValue).toFixed(2) + "";
                        }
                        trendData.push(item);
                    }
                    return true;
                }

                // 同比/环比的值
                if (preObj.benefitValue == null) {
                    item.changeValue = 0;
                } else {
                    item.changeValue = (((item.benefitValue.toFixed(2) - preObj.benefitValue.toFixed(2)) / preObj.benefitValue.toFixed(2)) * 100)
                        .toFixed(2);
                }

                if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                    if (item.targetValue == null || item.targetValue == "null") {
                        item.targetValue = "";
                    }
                    item.complianceRate = "100";
                } else {
                    item.complianceRate = (item.benefitValue * 100 / item.targetValue).toFixed(2) + "%";
                }

                trendData.push(item);
                preObj = item;
            })
//			self.trendData=trendData;
            self.initChartData(trendData);
        }
    };

    /*
     * 当值为负数时，返回包含itemStyle的对象
     */
    function getNagativeItem(val) {
        return {
            value: val,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'red'
                        }
                    }
                }
            }
        }
    }

    /**
     * 下级对比
     *
     */
    var inferiorChart = {
        chartId: 'inferiorChart',
        option: {
            calculable: false,
            //color: ['#FFD966', '#BFBFBF'],
            grid: organGrid,
//                tooltip: {
//                    trigger: 'item',
//                    axisPointer: {type: 'none'},
////                    position:function(a,b,c){
////                    	var left=$("#"+trendChartObj.chartId).offset().left;
////                       var width=$("#"+trendChartObj.chartId).width();
////                    	return [left+width/2-100,0];
////                    },
//                    formatter: function (params, ticket, callback) {
//                        //var html = "<div>" + params[0].name + "<br>环比变化：" + (params[0].data * 10000 / 100).toFixed(2) + "%<br>主动流失率：" + (params[1].data * 10000 / 100).toFixed(2) + "%<div>";
//                        var cols=[{name:"当前组织",value:params[1]},{name:"人均效益",value:params[2]}];
//                        var html=formatterChartTooltip({title:timeRange,cols:cols});
//                        return html;
//                    }
//                },
            xAxis: [{
                position: 'top',
                type: 'value',
                name: '万元',
                nameTextStyle: {
                    color: 'black'
                },
                axisLabel: {
                    name: '万元',
                    formatter: function (value) {
                        return value;
                    }

                },
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: false
                }
            }],
            yAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
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
                type: "bar",
                barWidth: barWidth,
                data: [],
                itemStyle: {
                    normal: {
                        color: "rgb(35, 198, 200)",
                        label: {
                            show: true,
                            textStyle: {
                                color: "rgb(34, 34, 34)"
                            },
                            formatter: function (a) {
                                return (a.value).toFixed(2) + "万元";
                            }
                        }
                    }
                }
            }]
        },
        init: function (organId) {
            this.chart = null;
            this.chart = echarts.init(document.getElementById(this.chartId));
            this.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            if (_.isEmpty(self.resultData)) {
                self.getRequestData(organId);
                return;
            }
            self.generateDetail();
        }, resize: function () {
            this.chart.resize();
        },
        getRequestData: function (organId) {
            var self = this;
            $.ajax({
                url: urls.orgBenefitUrl,
                data: {organizationId: organId},
                success: function (data) {
                    self.resultData = data;
                    empComposingObj.init(data[0]);
                    removeEmptyTip($('#' + self.chartId));
                    if (_.isEmpty(data) || data.length <= 1) {
                        showEmptyTip($('#' + self.chartId));
                        return;
                    }
                    var yaxisData = [], seriesData = [];
                    $.each(data, function (n, v) {

                        yaxisData.push(formatAxis(v.organizationName));
                        seriesData.push(v.benefitValue);
                    });
                    self.option.yAxis[0].data = yaxisData;
                    self.option.series[0].data = seriesData;

                    self.chart.setOption(self.option);
                    self.chart.refresh();
                    $('#' + self.chartId).chartTooltip({
                        chart: self.chart, formatter: function (index, v, p) {
                            var cols = [{name: v.name, value: (v.data[0].value).toFixed(2) + "%"}];

                            return {title: v.name, cols: cols};
                        }
                    });
                },
                error: function () {

                }
            });
        }
    };

    /**等效全职员工数 的饼图
     *人员数构成
     */
    var empComposingObj = {
        chartId: 'empComposingChart',
        option: {
            title: {
                show: true,
                text: '',
                x: 'center',
                y: 'bottom',
                textStyle: {
                    fontSize: 12
                }
            },
            color: ['#1AB394', '#79D2C0', '#23C6C8'],
            calculable: false,
            series: [{
                type: 'pie',
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                },
                radius: '55%',
                data: []
            }]
        },
        init: function (data) {
            var self = this;
            /*  var height = 320;
             $('#' + self.chartId).height(height);*/
            self.charObj = echarts.init(document.getElementById(self.chartId));

            self.extendsData(data);
        },
        extendsData: function (data) {
            var self = this;
            var fulltimeSum = data.fulltimeSum;
            var passtimeSum = data.passtimeSum;
            var overtimeSum = data.overtimeSum;
            var total = fulltimeSum + passtimeSum + overtimeSum;
            var seriesData = [self.formatData('全职', fulltimeSum, total), self.formatData('兼职', passtimeSum, total), self.formatData('加班', overtimeSum, total)];
            self.option.series[0].data = seriesData;
            //self.option.title.text = '合计 ' + Tc.formatNumber(total);
            self.charObj.setOption(self.option, true);
        }, resize: function () {
            var self = this;
            self.charObj.resize();
        }, formatData: function (name, value, total) {
            if (!value) {
                return {};
            }
            //页面展示为 ：name，value，(换行)percent%
            return {
                value: value,
                name: (name + '\n' + Tc.formatNumber(value) + '\n' + ((value / total) * 100).toFixed(0) + '%')
            };
        }
    };

    var fomartDate = function (date) {
        var arr = date.split(" ");
        if (arr.length = 2) {
            return arr[0];
        } else {
            return date;
        }
    }
    initPerBenefit(reqOrganId);
    trendChartObj.init(reqOrganId);
    inferiorChart.init(reqOrganId);
    perBenefitObj.init(reqOrganId);
    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }


    function formatPercentage(value) {
        if (!value) {
            return '0';
        }
        return Math.round(value * 100) / 100 + '%';
    }
});