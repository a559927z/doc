require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'utils', 'bootstrap', 'jgGrid', 'underscore', 'timeLine2'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        getCompareMonth: webRoot + "/manpowerCost/getCompareMonth.do", //获取上月的人力成本 和人均成本
        getCompareYear: webRoot + "/manpowerCost/getCompareYear.do", //  /* 获取本年的人力成本 和人均成本
        getTrendByMonth: webRoot + "/manpowerCost/getTrendByMonth.do", // 成本月度趋势
        getAvgTrendByMonth: webRoot + "/manpowerCost/getAvgTrendByMonth.do", // 人均成本月度趋势
        getItemDetail: webRoot + "/manpowerCost/getItemDetail.do", // 人力成本结构
        getOrganCost: webRoot + "/manpowerCost/getOrganCost.do", // 各架构人力成本
        getProportionMonth: webRoot + "/manpowerCost/getProportionMonth.do", // 人力成本占比（按月环比
        getProportionYear: webRoot + "/manpowerCost/getProportionYear.do", // 人力成本占比（按年同比）
        getAvgValueData: webRoot + "/manpowerCost/getAvgValueData.do", // 行业均值
        getAllDetailData: webRoot + "/manpowerCost/getAllDetailData.do", // 销售 成本 利润  明细
        getCostAvgWarn: webRoot + "/manpowerCost/getCostAvgWarn.do" // 人均成本预警
    }
    var chartPieColor = ["#0b98e0", "#00bda9", "#4573a7", "#92c3d4", "#de6e1b", "#ff0084", "#af00e1", "#8d55f6", "#6a5888", "#2340f3"];
    var fontFamily = "'微软雅黑', 'verdana'";
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();

    $("[data-toggle='tooltip']").tooltip();

    //窗口大小改变时，Echart也改变大小
    window.onresize = function () {
        if (contrastOrgObj.echartObj)
            contrastOrgObj.echartObj.resize();
        if (contrastDetailObj.echartObj)
            contrastDetailObj.echartObj.resize();
        if (manpowerTrendsObj.chart)
            manpowerTrendsObj.chart.resize();
        if (trendByMonthObj.chart)
            trendByMonthObj.chart.resize();
        if (manpowerTrendObj.echartObj)
            manpowerTrendObj.echartObj.resize();
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

    var treeSelector = null;

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var year = $('#curdate').val().substring(0, 4);
    $(".year").text(year);

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        isDialogInit = false;
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

    //人力成本
    var manpowertotalObj = {
        click: function () {
            $("#manpowertotalTab span").on("click", function () {
                $(this).parent().find("span").removeClass("select");
                $(this).addClass("select");
                $("#manpowertotalContent .content").addClass("hide");
                $("#" + $(this).data("id")).removeClass("hide");
            });
        },
        init: function () {
            var self = this;
            self.loadYear(reqOrgId);
            self.loadMonth(reqOrgId);
        },
        loadYear: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareYear,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    self.render($("#totalaccumulative"), self.calResult(result));
                }
            });
        },
        loadMonth: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareMonth,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    self.render($("#totalbudget"), self.calResult(result));
                }
            });
        },
        calResult: function (result) {
            var data = {currentMonth: -1, lastMonth: -1};
            $.each(result, function (i, o) {
                if (o.type == 1) {
                    data.currentMonth = o.cost;
                }
                if (o.type == 0) {
                    data.lastMonth = o.cost;
                }
            });
            data.className = "blue";
            if (data.currentMonth == -1) {
                data.currentMonth = "";
                if (data.lastMonth == -1) {
                    data.lastMonth = "";
                } else {
                    data.lastMonth = "0%";
                }
            } else {
                if (data.lastMonth == -1 || data.lastMonth == 0) {
                    data.className = "red";
                    data.lastMonth = "+100%";
                } else if (data.currentMonth == data.lastMonth) {
                    data.lastMonth = "0%";
                } else {
                    var flag = "";
                    if (data.currentMonth > data.lastMonth) {
                        data.className = "red";
                        flag = "+";
                    }
                    data.lastMonth = flag + ((data.currentMonth - data.lastMonth) / data.lastMonth * 100).toFixed(2) + "%";

                }
                if (data.currentMonth != 0) {
                    data.currentMonth = (data.currentMonth).toFixed(2);
                }
            }
            return data;
        },
        render: function (obj, data) {
            if (data.currentMonth == "") {
                obj.find(".data").addClass("hide").eq(0).removeClass("hide");
            } else {
                obj.find(".data").addClass("hide").eq(1).removeClass("hide");
                obj.find(".num").text(data.currentMonth);
                obj.find(".rate").text(data.lastMonth).removeClass("blue").removeClass("red").addClass(data.className);
            }
        }
    }
    manpowertotalObj.click();
    manpowertotalObj.init();


    //人均成本
    var manpowerAvgObj = {
        click: function () {
            $("#manpoweravgTab span").on("click", function () {
                $(this).parent().find("span").removeClass("select");
                $(this).addClass("select");
                $("#manpoweravgContent .content").addClass("hide");
                $("#" + $(this).data("id")).removeClass("hide");
            });
        },
        init: function () {
            var self = this;
            self.loadYear(reqOrgId);
            self.loadMonth(reqOrgId);
        },
        loadYear: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareYear,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    var obj = $("#avgaccumulative");
                    if (result.length > 0) {
                        self.render(obj, self.calResult(result));
                    } else {
                        obj.find(".data").addClass("hide").eq(0).removeClass("hide");
                    }
                }
            });
        },
        loadMonth: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareMonth,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    var obj = $("#avgbudget");
                    if (result.length > 0) {
                        self.render(obj, self.calResult(result));
                    } else {
                        obj.find(".data").addClass("hide").eq(0).removeClass("hide");
                    }
                }
            });
        },
        calResult: function (result) {
            var data = {currentMonth: -1, lastMonth: -1};
            $.each(result, function (i, o) {
                if (o.type == 1) {
                    data.currentMonth = o.costAvg;
                }
                if (o.type == 0) {
                    data.lastMonth = o.costAvg;
                }
            });
            data.className = "blue";
            if (data.currentMonth == -1) {
                data.currentMonth = "";
                if (data.lastMonth == -1) {
                    data.lastMonth = "";
                } else {
                    data.lastMonth = "0%";
                }
            } else {
                if (data.lastMonth == -1 || data.lastMonth == 0) {
                    data.className = "red";
                    data.lastMonth = "+100%";
                } else if (data.currentMonth == data.lastMonth) {
                    data.lastMonth = "0%";
                } else {
                    var flag = "";
                    if (data.currentMonth > data.lastMonth) {
                        data.className = "red";
                        flag = "+";
                    }
                    data.lastMonth = flag + ((data.currentMonth - data.lastMonth) / data.lastMonth * 100).toFixed(2) + "%";

                }
                if (data.currentMonth != 0) {
                    data.currentMonth = (data.currentMonth).toFixed(2);
                }
            }
            return data;
        },
        render: function (obj, data) {
            if (_.isEmpty(data)) {
                obj.find(".data").addClass("hide").eq(0).removeClass("hide");
            } else {
                obj.find(".data").addClass("hide").eq(1).removeClass("hide");
                obj.find(".num").text(data.currentMonth);
                obj.find(".rate").text(data.lastMonth).removeClass("blue").removeClass("red").addClass(data.className);
            }
        }
    }
    manpowerAvgObj.click();
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
                        fontFamily: fontFamily,
                        fontStyle: 'normal',
                        fontWeight: 'normal'
                    }
                },
                axisLine: {
                    show: true,
                    onZero: false,
                    lineStyle: {
                        width: 0,
                        color: '#999'
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
                data: [0, 900, 1245, 1530, 1376, 1376, 1511, 1689, 1856, 1495, 1292]
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
            $.ajax({
                url: urls.getTrendByMonth,
                //  type:"post",
                data: {organId: orgId},
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
            var lastData = [0, 0];
            processerbar.setData(Tc.formatFloat(result.budget), Tc.formatFloat(result.total));
            var data = [Tc.formatFloat(result.budget), Tc.formatFloat(result.total)];
            var xdata = ['当年预算', '当年累计'];
            var record = 0;
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
                        fontSize: 12,
                        fontFamily: fontFamily
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                splitNumber: 8,
                name: "",
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
                        fontStyle: 'normal',
                        fontWeight: 'normal',
                        fontFamily: fontFamily
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
                type: "line",
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
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
            var dataValue = [];
            var xAxisData = []
            $.each(result, function (i, o) {
                dataValue.push(o.costAvg);
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
                            fontSize: 12,
                            fontFamily: fontFamily
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
                    name: "",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#333333"
                        }
                    },
                    axiaLabel: {
                        fontFamily: fontFamily
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
                            color: "#1C84C6",
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
                    barMaxWidth: 50
                }
            ],
            grid: {
                x: 55,
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
                seriesData.push(item.cost);
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
                                fontFamily: fontFamily,
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
            color: chartPieColor
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
                seriesData.push({"value": item.cost, "name": item.itemName});
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


    var setClickEvent = function (id, obj) {
        $("#" + id + " span").click(function () {
            var par = $(this).parent();
            var _t = this;
            $.each(par.children(), function (i, o) {
                if (this == _t) {
                    if ($(this).hasClass("green") && $(this).attr("data") == reqOrgId) {
                        return;
                    }
                    $(this).removeClass("green").removeClass("white").addClass("green");
                    if (i == 0) {
                        obj.loadMonth(reqOrgId);
                        obj.obj.find(".lastLabel").text("较上月");
                    } else {
                        obj.loadYear(reqOrgId);
                        obj.obj.find(".lastLabel").text("较上一年");
                    }
                    $(this).attr("data", reqOrgId)
                } else {
                    $(this).removeClass("green").removeClass("white").addClass("white");
                }
            });

        });
    }

    /**
     * 人力成本占比
     */
    var manpowerCostTrendsObj = {
        obj: $("#manpowerCostTrends"),
        render: function (data) {
            var self = this;
            if (_.isEmpty(data)) {
                self.obj.find(".currentMonth").text("");
                self.obj.find(".lastMonth").text("");
            } else {
                self.obj.find(".currentMonth").text(data.currentMonth);
                self.obj.find(".lastMonth").text(data.lastMonth);
            }
            self.obj.find(".lastMonth").removeClass("lastMonthBlue").removeClass("lastMonthRed").addClass(data.className);
        },
        calResult: function (result) {
            var data = {currentMonth: -1, lastMonth: -1};
            var dataRusult = {currentMonth: -1, lastMonth: -1};
            $.each(result, function (i, o) {
                if (o.type == 1) {
                    data.currentMonth = (o.cost / o.total * 100).toFixed(2) + "%";
                    dataRusult.currentMonth = (o.cost / o.total * 100).toFixed(2);
                }
                if (o.type == 0) {
                    data.lastMonth = (o.cost / o.total * 100).toFixed(2) + "%";
                    dataRusult.lastMonth = (o.cost / o.total * 100).toFixed(2);
                }
            });
            data.className = "lastMonthBlue";
            if (data.currentMonth == -1) {
                data.currentMonth = "";
                if (data.lastMonth == -1) {
                    data.lastMonth = "";
                } else {
                    data.lastMonth = "0%";
                }
            } else {
                if (data.lastMonth == -1 || data.lastMonth == 0) {
                    data.className = "lastMonthRed";
                    data.lastMonth = "+100%";
//    					$("#manpowerCostTrends").find(".lastLabel").text("较上一年");
                } else {
                    var m = ((dataRusult.currentMonth - dataRusult.lastMonth) / dataRusult.lastMonth * 100).toFixed(2);
                    var flag = "";
                    if (m > 0) {
                        data.className = "lastMonthRed";
                        flag = "+";
                    }
                    data.lastMonth = flag + ((dataRusult.currentMonth - dataRusult.lastMonth) / dataRusult.lastMonth * 100).toFixed(2) + "%";
//    					$("#manpowerCostTrends").find(".lastLabel").text("较上月");
                }
            }
            return data;
        },
        loadYear: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getProportionYear,
                data: {organId: orgId},
                success: function (result) {
                    if (result && result.length > 0) {
                        $("#manpowerCostTrends .row").addClass("hide").eq(1).removeClass("hide");
                        self.render(self.calResult(result));
                    } else {
                        $("#manpowerCostTrends .row").addClass("hide").eq(0).removeClass("hide");
                    }
                }
            });
        },
        loadMonth: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getProportionMonth,
                data: {organId: orgId},
                success: function (result) {
                    if (result && result.length > 0) {
                        $("#manpowerCostTrends .row").addClass("hide").eq(1).removeClass("hide");
                        self.render(self.calResult(result));
                    } else {
                        $("#manpowerCostTrends .row").addClass("hide").eq(0).removeClass("hide");
                    }
                }
            });
        },
        init: function (orgId) {
            setClickEvent("manpowerCostTrends_toolbar", manpowerCostTrendsObj);
            if ($("#manpowerCostTrends_toolbar span").eq(0).hasClass("green")) {
                this.loadMonth(orgId);
            } else {
                this.loadYear(orgId);
            }
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
                //  self.obj.find(".arrow-lower").css("display", "block");
            } else {
                self.obj.find(".smile-face").addClass("hide");
                self.obj.find(".warn-icon").removeClass("hide");
                self.obj.find("#averageCompare").text("高于行业均值" + result);
                self.obj.find(".change").css("color", "#ff6600");
                self.obj.find(".arrow-icon").removeClass("arrow-lower").removeClass("arrow-higher").addClass("arrow-higher");
                //self.obj.find(".arrow-lower").css("display", "none");
                // self.obj.find(".arrow-higher").css("display", "block");
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
                trigger: 'axis'
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
                            fontSize: 12,
                            fontFamily: "'Applied Font Regular', 'Applied Font'"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '',
                    splitLine: {
                        show: false
                    },
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
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                }
                            }
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
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                }
                            }
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
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                }
                            }
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
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                }
                            }
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
});