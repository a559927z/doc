require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'utils', 'bootstrap', 'jgGrid', 'underscore', 'timeLine2'], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        getCompareMonth: webRoot + "/manpowerCost/getCompareMonth.do", //获取上月的人力成本 和人均成本
        getCompareYear: webRoot + "/manpowerCost/getCompareYear.do", //  /* 获取本年的人力成本 和人均成本
        getTrendByMonth: webRoot + "/manpowerCost/getTrendByMonth.do", // 成本月度趋势
        getAvgTrendByMonth: webRoot + "/manpowerCost/getAvgTrendByMonth.do", // 人均成本月度趋势
        getItemDetail: webRoot + "/manpowerCost/getItemDetail.do", // 人力成本结构
        getOrganCost: webRoot + "/manpowerCost/getOrganCost.do", // 各架构人力成本
        getProportionMonth: webRoot + "/manpowerCost/getProportionMonth.do", // 人力成本占比（按月环比)
        getProportionYear: webRoot + "/manpowerCost/getProportionYear.do", // 人力成本占比（按年同比）
        getAvgValueData: webRoot + "/manpowerCost/getAvgValueData.do", // 行业均值
        getAllDetailData: webRoot + "/manpowerCost/getAllDetailData.do", // 销售 成本 利润  明细
        getCostAvgWarn: webRoot + "/manpowerCost/getCostAvgWarn.do" // 人均成本预警
    }
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();

    $("[data-toggle='tooltip']").tooltip();

    //窗口大小改变时，Echart也改变大小
    window.onresize = function () {
        if (contrastOrgObj.echartObj) contrastOrgObj.echartObj.resize();
        if (contrastDetailObj.echartObj) contrastDetailObj.echartObj.resize();
        if (manpowerTrendsObj.chart) manpowerTrendsObj.chart.resize();
        if (trendByMonthObj.chart) trendByMonthObj.chart.resize();
        if (manpowerTrendObj.echartObj) manpowerTrendObj.echartObj.resize();
    };
    /**
     * 无数据时展示
     * @param chartId
     * @param hide
     */
    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
            $chart.parent().find(".bottom-div-first").addClass("hide");
        } else {
            $chart.children('div.loadingmessage').remove();
            $chart.parent().find(".bottom-div-first").removeClass("hide");
        }
    }

    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var year = $('#curdate').val().substring(0, 4);
    $(".year").text(year);

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        manpowertotalObj.init();
        manpowerAvgObj.init();
        timeLineObj.init(organId);

        trendByMonthObj.init(organId);
        manpowerTrendsObj.init(organId);
        contrastOrgObj.init();
        contrastDetailObj.init();
        manpowerCostTrendsObj.init(organId);
        industryAverageObj.init(organId);
        manpowerTrendObj.init();
    }

    $('.index-common-title-right span').on('click', function () {
        var $this = $(this);
        $this.siblings().removeClass("select");
        $this.addClass("select");
        $this.parents('.index-common-title').next().find('.content').addClass("hide");
        $("#" + $this.data("id")).removeClass("hide");
    });


    //人力成本
    var manpowertotalObj = {
        init: function () {
            var self = this;
            self.loadYear(reqOrgId);
            self.loadMonth(reqOrgId);
        },
        loadYear: function (orgId) {
            var self = this;
            $.get(urls.getCompareYear, {organId: orgId}, function (result) {
                var currVal, prevVal;
                $.each(result, function (i, o) {
                    if (o.type == 1) currVal = o.cost;
                    if (o.type == 0) prevVal = o.cost;
                });
                Tc.renderTopDigitalContrast($("#totalaccumulative"), currVal, prevVal);
            });
        },
        loadMonth: function (orgId) {
            var self = this;
            $.get(urls.getCompareMonth, {organId: orgId}, function (result) {
                var currVal, prevVal;
                $.each(result, function (i, o) {
                    if (o.type == 1) currVal = o.cost;
                    if (o.type == 0) prevVal = o.cost;
                });
                Tc.renderTopDigitalContrast($("#totalbudget"), currVal, prevVal);
            });
        }
    }
    manpowertotalObj.init();


    //人均成本
    var manpowerAvgObj = {
        init: function () {
            var self = this;
            self.loadYear(reqOrgId);
            self.loadMonth(reqOrgId);
        },
        loadYear: function (orgId) {
            var self = this;
            $.get(urls.getCompareYear, {organId: orgId}, function (result) {
                var currVal, prevVal;
                $.each(result, function (i, o) {
                    if (o.type == 1) currVal = Tc.formatFloat(o.costAvg);
                    if (o.type == 0) prevVal = Tc.formatFloat(o.costAvg);
                });
                Tc.renderTopDigitalContrast($("#avgaccumulative"), currVal, prevVal);
            });
        },
        loadMonth: function (orgId) {
            var self = this;
            $.get(urls.getCompareMonth, {organId: orgId}, function (result) {
                var currVal, prevVal;
                $.each(result, function (i, o) {
                    if (o.type == 1) currVal = Tc.formatFloat(o.costAvg);
                    if (o.type == 0) prevVal = Tc.formatFloat(o.costAvg);
                });
                Tc.renderTopDigitalContrast($("#avgbudget"), currVal, prevVal);
            });
        }
    }
    manpowerAvgObj.init();


    /*
     人力成本执行率进度条
     */
    var processerbar = {
        getData: function () {
            var self = this;
            $.ajax({
                url: urls.getTrendByMonth,
                data: {organId: reqOrgId},
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.processerValue = parseInt(result.budget / result.total);
                        self.budgetValue = result.budget;

                        $("#budgetValue").text(self.budgetValue + "万");
                        $("#probar").find("#line").attr("w", self.processerValue);

                        $("#line").each(function (i, item) {
                            $(item).animate({width: parseFloat($(item).attr("w")) + "%"}, 1000);
                        });
                        window.setInterval(function () {
                            $('#percent').css('marginLeft', $("#line").width() - $("#percent").width() / 2 + "px");
                        }, 70);
                        $('#percent').html(($("#line").attr("w")) + "%");
                    }
                }
            });
        },
        setData: function (budget, total) {
            var obj = $("#salaryrateContent");
            if (parseFloat(budget) == 0 && parseFloat(total) == 0) {
                obj.find(".data").addClass("hide").eq(0).removeClass("hide");
            } else {
                obj.find(".data").addClass("hide").eq(1).removeClass("hide");
                var processerValue = ((total / budget ) * 100).toFixed(2);
                if (total == 0 && budget == 0) {
                    processerValue = 0;
                } else if (total == 0) {
                    processerValue = 100;
                }
                $("#budgetValue").text(budget + "万");
                $("#probar").find("#line").attr("w", processerValue);
                $("#line").each(function (i, item) {
                    $(item).animate({width: parseFloat($(item).attr("w")) + "%"}, 1000);
                });
                window.setInterval(function () {
                    $('#percent').css('marginLeft', $("#line").width() - $("#percent").width() / 2 + "px");
                }, 70);
                $('#percent').html(($("#line").attr("w")) + "%");
            }
        }
    }


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
    timeLineObj.init(reqOrgId);


    /**
     * 人力成本月度趋势
     */
    var trendByMonthOption = {
        grid: {
            borderWidth: 0,
            x: 70,
            x2: 5,
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
                    rotate: 30,
                    itemStyle: {
                        color: '#000'
                    },
                    textStyle: {
                        color: '#000',
                        fontSize: 12
                    }
                }
            }
        ],
        color: ['#23C6C8'],
        yAxis: [
            {
                name: "",
                splitLine: false,
                type: 'value',
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
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                }
            }
        ],
        series: [
            {
                type: 'bar',
                stack: '成本',
                clickable: false,
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
                name: '成本',
                type: 'bar',
                stack: '成本',
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top'
                        }, color: function (a) {
                            if (a.dataIndex == 0) {
                                return Tc.defaultBarColor[0];
                            } else if (a.dataIndex == 1) {
                                return Tc.defaultBarColor[1];
                            } else {
                                return Tc.defaultBarColor[2];
                            }

                        }
                    }
                },
                data: []
            }
        ]
    };
    var trendByMonthObj = {
        id: "trendByMonth",
        chart: null,
        init: function (orgId) {
            var self = this;
            if (this.chart == null) {
                this.chart = echarts.init(document.getElementById(this.id));
            }
            this.getData(orgId);
        },
        getData: function (orgId) {
            var self = this;
            $.get(urls.getTrendByMonth, {organId: orgId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.id, false);
                    self.render(result);
                } else {
                    hideChart(self.id, true);
                }
            });
        },
        render: function (result) {
            var lastData = [0, 0], xdata = ['当年预算', '当年累计'], record = 0;
            processerbar.setData(Tc.formatFloat(result.budget), Tc.formatFloat(result.total));
            var data = [Tc.formatFloat(result.budget), Tc.formatFloat(result.total)];

            $.each(result.detail, function (i, o) {
                lastData.push(record);
                data.push(Tc.formatFloat(o.cost));
                var month = parseInt((o.yearMonth + "").substring(4, 6)) + "月";
                xdata.push(month);
                record += Tc.formatFloat(o.cost);
            });
            var max = result.total > result.budget ? result.total : result.budget;
            trendByMonthOption.xAxis[0].data = xdata;
            trendByMonthOption.yAxis[0].max = max + 30;
            trendByMonthOption.series[0].data = lastData;
            trendByMonthOption.series[1].data = data;
            this.chart.setOption(trendByMonthOption, true);
        }
    }
    trendByMonthObj.init(reqOrgId);


    //人均成本月度趋势
    var manpowerTrendOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                lineStyle: {
                    color: '#999',
                    width: 1,
                    type: 'dashed'
                },
            },
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
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                splitNumber: 8,
                nameTextStyle: {
                    color: '#999',
                    margin: '100px'
                },
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        calculable: false,
        series: [
            {
                name: '人均趋势',
                type: "line",
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        },
                        color: Tc.defaultLineColor[0]
                    }
                },
                data: []
            }
        ]

    }
    var manpowerTrendsObj = {
        id: "manpowerTrend",
        chart: null,
        init: function (organId) {
            var self = this;
            this.getData(organId);
        },
        getData: function (organId) {
            var self = this;
            $.ajax({
                url: urls.getAvgTrendByMonth,
                //  type:"post",
                data: {organId: organId},
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        hideChart(self.id, false);
                        self.render(result);
                    } else {
                        hideChart(self.id, true);
                    }
                }
            });
        },
        render: function (result) {
            var dataValue = [], xAxisData = []
            $.each(result, function (i, o) {
                dataValue.push(Tc.formatFloat(o.costAvg));
                var month = parseInt((o.yearMonth + "").substring(4, 6)) + "月";
                xAxisData.push(month);
            });
            manpowerTrendOption.xAxis[0].data = xAxisData;
            manpowerTrendOption.series[0].data = dataValue;

            if (this.chart != null) {
                this.chart.clear();
            }
            this.chart = echarts.init(document.getElementById(this.id));
            this.chart.setOption(manpowerTrendOption, true);
        }
    }
    manpowerTrendsObj.init(reqOrgId);


    //各架构人力成本
    var contrastOrgObj = {
        echartId: "contrastOrgChart",
        resultData: null,
        echartObj: null,
        echartOption: {
            tooltip: {
                trigger: "axis"
            },
            toolbox: {
                show: false,
                feature: {
                    mark: {
                        show: true
                    },
                    dataView: {
                        show: true,
                        readOnly: true
                    },
                    magicType: {
                        show: false,
                        type: ["line", "bar"]
                    },
                    restore: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }
                }
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
                        rotate: 20,
                        itemStyle: {
                            color: '#000000'
                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 1,
                            color: "#cecece"
                        }
                    }
                }
            ],
            series: [
                {
                    type: "bar",
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            color: Tc.defaultBarColor[0],
                            label: {
                                show: true
                            }
                        }
                    },
                    tooltip: {
                        show: true,
                        trigger: "item",
                        formatter: "{b} : {c} 万元"
                    },
                    barMaxWidth: 30
                }
            ],
            grid: {
                x: 85,
                y: 25,
                x2: 15,
                borderWidth: 0
            }
        },
        getRequestData: function (organId) {
            var self = this;
            $.get(urls.getOrganCost, {'organId': organId}, function (rs) {
                self.resultData = rs;
                self.initData(organId);
            });
        },
        initData: function (organId) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var name = "", len = 0;
            $.each(self.resultData, function (i, item) {
                name = item.organ;
                len = name.length;
                xAxisData.push(len > 8 ? name.substring(0, 8) + "\n" + name.substring(8, len) : item.organ);
                seriesData.push(Tc.formatFloat(item.cost));
            });
            self.echartOption.xAxis[0].data = xAxisData;
            self.echartOption.series[0].data = seriesData;
            self.initEcharts();
        },
        initEcharts: function () {
            var self = this;
            self.echartObj = echarts.init(document.getElementById(self.echartId));
            if (self.resultData.length == 0) {
                hideChart(self.echartId, true);
            } else {
                hideChart(self.echartId, false);
                self.echartObj.setOption(self.echartOption);
            }
        },
        init: function () {
            var self = this;
            self.getRequestData(reqOrgId);
        }
    };
    contrastOrgObj.init();


    //人力成本结构
    var contrastDetailObj = {
        echartId: "contrastDetailChart",
        resultData: null,
        echartObj: null,
        echartOption: {
            legend: {
                x: "center",
                data: [],
                y: "bottom",
                orient: "horizontal",
                selectedMode: false
            },
            toolbox: {
                show: false,
                feature: {
                    mark: {
                        show: true
                    },
                    dataView: {
                        show: true,
                        readOnly: true
                    },
                    restore: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }
                }
            },
            series: [
                {
                    name: "人力成本结构",
                    type: "pie",
                    radius: ["50%", "70%"],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: "{b}, {c},\n{d}%"
                            },
                            labelLine: {
                                show: true
                            }
                        },
                        emphasis: {
                            label: {
                                show: true,
                                position: "center",
                                textStyle: {
                                    fontSize: "30",
                                    fontWeight: "bold"
                                }
                            }
                        }
                    },
                    data: []
                }
            ],
            color: Tc.defaultBarColor
        },
        getRequestData: function (organId) {
            var self = this;
            $.get(urls.getItemDetail, {'organId': organId}, function (rs) {
                self.resultData = rs;
                self.initData(organId);
            });
        },
        initData: function (organId) {
            var self = this;
            var legendData = [], seriesData = [];
            $.each(self.resultData, function (i, item) {
                legendData.push({name: item.itemName, icon: 'bar'});
                seriesData.push({"value": Tc.formatFloat(item.cost), "name": item.itemName});
            });
            self.echartOption.legend.data = legendData;
            self.echartOption.series[0].data = seriesData;
            self.initEcharts();
        },
        initEcharts: function () {
            var self = this;
            if (self.resultData.length == 0) {
                hideChart(self.echartId, true);
            } else {
                hideChart(self.echartId, false);
                self.echartObj = echarts.init(document.getElementById(self.echartId));
                self.echartObj.setOption(self.echartOption);
            }
        },
        init: function () {
            var self = this;
            self.getRequestData(reqOrgId);
        }
    };
    contrastDetailObj.init();

    /**
     * 人力成本占比
     */
    var manpowerCostTrendsObj = {
        obj: $("#manpowerCostTrends"),
        init: function (orgId) {
            var self = this;
            self.loadYear(orgId);
            self.loadMonth(orgId);
        },
        loadYear: function (orgId) {
            var self = this;
            $.get(urls.getProportionYear, {organId: orgId}, function (result) {
                if (result && result.length > 0) {
                    var currVal, prevVal;
                    $.each(result, function (i, o) {
                        if (o.type == 1) currVal = Tc.formatFloat(o.cost / o.total * 100);
                        if (o.type == 0) prevVal = Tc.formatFloat(o.cost / o.total * 100);
                    });
                    Tc.renderTopDigitalContrast($("#costTrendsYear"), currVal, prevVal);
                } else {
                    $("#costTrendsYear .data").addClass("hide").eq(0).removeClass("hide");
                }
            });
        },
        loadMonth: function (orgId) {
            var self = this;
            $.get(urls.getProportionMonth, {organId: orgId}, function (result) {
                if (result && result.length > 0) {
                    var currVal, prevVal;
                    $.each(result, function (i, o) {
                        if (o.type == 1) currVal = Tc.formatFloat(o.cost / o.total * 100);
                        if (o.type == 0) prevVal = Tc.formatFloat(o.cost / o.total * 100);
                    });
                    Tc.renderTopDigitalContrast($("#costTrendsMonth"), currVal, prevVal);
                } else {
                    $("#costTrendsMonth .data").addClass("hide").eq(0).removeClass("hide");
                }
            });
        }

    };
    manpowerCostTrendsObj.init(reqOrgId);

    /**
     * 人力成本趋势
     * 人力成本预警2
     */
    var industryAverageObj = {
        obj: $("#manpowerCostearlyWarning"),
        render: function (data, resultData) {
            var self = this;
            var result = "";
            if (resultData > 0) {
                result = ((Math.abs(data - resultData)) / data * 100).toFixed(2) + "%";
            } else if (resultData == null || resultData == 0) {
                result = "100%";
            }
            if (data > resultData) {
                self.obj.find(".smile-face").removeClass("hide");
                self.obj.find(".warn-icon").addClass("hide");
                self.obj.find("#averageCompare").text("低于行业均值" + result);
                self.obj.find(".change").css("color", "#1C84C6");
                self.obj.find(".arrow-icon").removeClass("arrow-lower").removeClass("arrow-higher").addClass("arrow-lower");
            } else {
                self.obj.find(".smile-face").addClass("hide");
                self.obj.find(".warn-icon").removeClass("hide");
                self.obj.find("#averageCompare").text("高于行业均值" + result);
                self.obj.find(".change").css("color", "#ff6600");
                self.obj.find(".arrow-icon").removeClass("arrow-lower").removeClass("arrow-higher").addClass("arrow-higher");
            }
            self.obj.find("#industryAverage").text(data);
        },
        getRequestData: function (orgId) {
            var self = this;
            var resultData = null;
            var data = null;
            $.ajax({
                url: urls.getCostAvgWarn,
                data: {organId: orgId},
                async: false,
                success: function (result) {
                    data = result.avgValue;
                    resultData = result.avgCost;
                }
            });
            self.render(data, resultData);
        },
        init: function (orgId) {
            var self = this;
            self.getRequestData(reqOrgId);
        }
    };
    industryAverageObj.init(reqOrgId);

    /*
     * 人力成本趋势
     * 图表
     * */
    var manpowerTrendObj = {
        echartId: "manpowerCostTrendsChars",
        resultData: null,
        echartObj: null,
        echartOption: {
            grid: {
                x: 55,
                y: 25,
                x2: 15,
                borderWidth: 0
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: '#999',
                        width: 1,
                        type: 'dashed'
                    },
                },
            },
            legend: {
                y: 'bottom',
                data: ['人力成本', '利润', '销售额', '人均成本']
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
                    name: '',
                    splitLine: false,
                    axisLine: {
                        lineStyle: {
                            color: '#cecece',
                            width: 1
                        }
                    },
                    axisLabel: {
                        formatter: '{value}'
                    }
                }
            ],
            series: [
                {
                    name: '人力成本',
                    type: 'line',
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: false
                        }
                    }
                },
                {
                    name: '利润',
                    type: 'line',
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: false
                        }
                    }
                },
                {
                    name: '销售额',
                    type: 'line',
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: false
                        }
                    }
                },
                {
                    name: '人均成本',
                    type: 'line',
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: false
                        }
                    }
                },
            ]
        },
        getRequestData: function (organId, year) {
            var self = this;
            $.get(urls.getAllDetailData, {'organId': organId, 'time': year}, function (rs) {
                self.resultData = rs;
                self.initData(organId);
            });
        },
        initData: function (organId) {
            var self = this;
            var xAxisData = [], seriesData1 = [], seriesData2 = [], seriesData3 = [], seriesData4 = [], all = [], max = 0;
            $.each(self.resultData, function (i, item) {
                var month = parseInt((item.yearMonth + "").substring(4, 6)) + "月";
                xAxisData.push(month);
                seriesData1.push(Tc.formatFloat(item.cost));
                seriesData2.push(Tc.formatFloat(item.gainAmount));
                seriesData3.push(Tc.formatFloat(item.salesAmount));
                seriesData4.push(Tc.formatFloat(item.costAvg));

                all.push(Tc.formatFloat(item.cost));
                all.push(Tc.formatFloat(item.gainAmount));
                all.push(Tc.formatFloat(item.salesAmount));
                all.push(Tc.formatFloat(item.costAvg));
            });
            max = _.max(all);
            max = parseInt(max / 100) * 100 + 100;
            self.echartOption.yAxis[0].max = max;

            self.echartOption.xAxis[0].data = xAxisData;
            self.echartOption.series[0].data = seriesData1;
            self.echartOption.series[1].data = seriesData2;
            self.echartOption.series[2].data = seriesData3;
            self.echartOption.series[3].data = seriesData4;
            self.initEcharts();
        },
        initEcharts: function () {
            var self = this;
            self.echartObj = echarts.init(document.getElementById(self.echartId));
            if (self.resultData.length == 0) {
                hideChart(self.echartId, true);
            } else {
                hideChart(self.echartId, false);
                self.echartObj.setOption(self.echartOption);
            }
        },
        init: function () {
            var self = this;
            self.getRequestData(reqOrgId, year);
        }
    };
    manpowerTrendObj.init();
})
;