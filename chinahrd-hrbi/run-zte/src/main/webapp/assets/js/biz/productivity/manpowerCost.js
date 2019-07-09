require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'jgGrid', 'underscore', 'organTreeSelector', 'timeLine', 'utils', 'messenger'], function ($, echarts) {
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

    //窗口大小改变时，Echart也改变大小
    window.onresize = function () {
        contrastOrgObj.echartObj.resize();
        contrastDetailObj.echartObj.resize();
        manpowerTrendsObj.chart.resize();
        trendByMonthObj.chart.resize();
    };

    //无数据据时显示 暂无数据
    var ShowNoDataEcharts = function (echartObj, echartId) {
        var zr = echartObj.getZrender();
        var TextShape = require('zrender/shape/Text');
        zr.addShape(new TextShape({
            style: {
                x: ($("#" + echartId).width() - 56) / 2,
                y: ($("#" + echartId).height() - 20) / 2,
                color: '#333333',
                text: '暂无数据',
                textFont: '14px 宋体'
            }
        }));
        zr.render();
    };

    var treeSelector = null;

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var curdate = $('#curdate').val();
    var year = curdate.substring(0, 4);
    $("#costYear").text(year + $("#costYear").text());
    $("#avgCostYear").text(year + $("#avgCostYear").text());
    $("#trendByMonth_title").text(year);
    $("#barlineYearLable").text(year + $("#barlineYearLable").text());
    $("#manpowerCostYear").text(year + $("#manpowerCostYear").text());

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        isDialogInit = false;
        initAll(organId);
        initTimeLine(organId);
    }


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
        }, setData: function (budget, total) {
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

    function initTimeLine(organizationId) {
        var quotaId = $('#quotaId').val();
        $.post(urls.memoUrl, {
            'quotaId': quotaId,
            'organizationId': organizationId
        }, function (rs) {
            //参数配置
            var options = {
                title: '管理建议与备忘',
                titleSuffix: '条未读',
                data: rs,
                onSubmit: function (text) {
                    $.post(urls.addMemoUrl, {
                        'quotaId': quotaId,
                        'organizationId': organizationId,
                        'content': text
                    }, function (result) {
                        if (result.type) {
                            initTimeLine(organizationId);		//重新加载
                            $(".modal-backdrop").hide();
                        }
                    });
                }
            }
            $('#timeLine').timeLine(options);	//初始化
        });
    }

    initTimeLine(reqOrgId);		//管理和备忘

    function initAll(orgId) {///
        changeOrgan("manpowerCostProportion_toolbar", manpowerCostProportionObj, orgId);
        changeOrgan("avgCostProportion_toolbar", avgCostProportionObj, orgId);
        changeOrgan("manpowerCostTrends_toolbar", manpowerCostTrendsObj, orgId);
        trendByMonthObj.init(orgId);
        contrastOrgObj.init(orgId);
        contrastDetailObj.init(orgId);
        manpowerTrendObj.init(orgId);
        manpowerTrendsObj.init(orgId);
        industryAverageObj.init(orgId);
    }

    function changeOrgan(id, obj, orgId) {
        var par = $("#" + id);
        $.each(par.children(), function (i, o) {
            if ($(this).hasClass("green")) {
                if (i == 0) {
                    obj.loadMonth(orgId);
                } else {
                    obj.loadYear(orgId);
                }
                $(this).attr("data", orgId)
            }
        });
    }

    /**
     * 人力成本
     */
    var manpowerCostProportionObj = {
        obj: $("#manpowerCostProportion"),
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
        }, calResult: function (result) {
            var data = {currentMonth: -1, lastMonth: -1};
            $.each(result, function (i, o) {
                if (o.type == 1) {
                    data.currentMonth = o.cost;
                }
                if (o.type == 0) {
                    data.lastMonth = o.cost;
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
                } else if (data.currentMonth == data.lastMonth) {
                    data.lastMonth = "0%";
                } else {
                    var flag = "";
                    if (data.currentMonth > data.lastMonth) {
                        data.className = "lastMonthRed";
                        flag = "+";
                    }
                    data.lastMonth = flag + ((data.currentMonth - data.lastMonth) / data.lastMonth * 100).toFixed(2) + "%";

                }
                if (data.currentMonth != 0) {
                    data.currentMonth = (data.currentMonth).toFixed(2);
                }
            }
            return data;
        }, loadYear: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareYear,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }, loadMonth: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareMonth,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }, init: function (orgId) {
            setClickEvent("manpowerCostProportion_toolbar", manpowerCostProportionObj)
            this.loadYear(orgId);
        }
    };
    manpowerCostProportionObj.init(reqOrgId);


    /**
     * 人均成本
     */
    var avgCostProportionObj = {
        obj: $("#avgCostProportion"),
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
        }, calResult: function (result) {
            var data = {currentMonth: -1, lastMonth: -1};
            $.each(result, function (i, o) {
                if (o.type == 1) {
                    data.currentMonth = o.costAvg;
                }
                if (o.type == 0) {
                    data.lastMonth = o.costAvg;
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
                } else if (data.currentMonth == data.lastMonth) {
                    data.lastMonth = "0%";
                } else {
                    var flag = "";
                    if (data.currentMonth > data.lastMonth) {
                        data.className = "lastMonthRed";
                        flag = "+";
                    }
                    data.lastMonth = flag + ((data.currentMonth - data.lastMonth) / data.lastMonth * 100).toFixed(2) + "%";
                }
                if (data.currentMonth != 0) {
                    data.currentMonth = (data.currentMonth).toFixed(2);
                }
            }
            return data;
        }, loadYear: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareYear,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }, loadMonth: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getCompareMonth,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }, init: function (orgId) {
            setClickEvent("avgCostProportion_toolbar", avgCostProportionObj)
            this.loadYear(orgId);
        }
    };

    function setClickEvent(id, obj) {
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

    avgCostProportionObj.init(reqOrgId);


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
        }, calResult: function (result) {
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
                    var flag = "";
                    if (data.currentMonth > data.lastMonth) {
                        data.className = "lastMonthRed";
                        flag = "+";
                    }
                    data.lastMonth = flag + ((dataRusult.currentMonth - dataRusult.lastMonth) / dataRusult.lastMonth * 100).toFixed(2) + "%";
//    					$("#manpowerCostTrends").find(".lastLabel").text("较上月");
                }
            }
            return data;
        }, loadYear: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getProportionYear,
                data: {organId: orgId},
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }, loadMonth: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getProportionMonth,
                data: {organId: orgId},
                success: function (result) {
                    self.render(self.calResult(result));
                }
            });
        }, init: function (orgId) {
            setClickEvent("manpowerCostTrends_toolbar", manpowerCostTrendsObj)
            this.loadYear(orgId);
        }
    };
    manpowerCostTrendsObj.init(reqOrgId);

    /**
     * 人力成本预警
     */
    var industryAverageObj = {
        obj: $("#manpowerCostearlyWarning"),
        render: function (data, resultData) {
            var self = this;
            var result = "";
            if (resultData > 0) {
                result = ((Math.abs(data - resultData)) / data * 100).toFixed(2) + "%";
            } else if (resultData == null) {
                result = "100%";
            }
            if (data > resultData) {
                self.obj.find(".smile-face").css("display", "block");
                self.obj.find(".warn-icon").css("display", "none");
                self.obj.find("#averageCompare").text("低于行业均值" + result);
                self.obj.find(".change").css("color",  "#1C84C6");
                self.obj.find(".arrow-icon").removeClass("arrow-lower").removeClass("arrow-higher").addClass("arrow-lower");
              //  self.obj.find(".arrow-lower").css("display", "block");
            } else {
                self.obj.find(".warn-icon").css("display", "block");
                self.obj.find(".smile-face").css("display", "none");
                self.obj.find("#averageCompare").text("高于行业均值" + result);
                self.obj.find(".change").css("color","#ff6600");
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
                    }
                }
            ],
            series: [
                {
                    type: "bar",
                    data: [],
                    itemStyle: {
                        normal: {
                            color: "rgb(35, 198, 200)",
                            label: {
                                show: true,
                                textStyle: {
                                    color: "rgb(34, 34, 34)"
                                }
                            }
                        }
                    },
                    tooltip: {
                        show: true,
                        trigger: "item",
                        formatter: "{b} : {c} 万元"
                    },
                    barMaxWidth:50
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
            self.echartObj.setOption(self.echartOption);
            if (self.resultData.length == 0) {
                ShowNoDataEcharts(self.echartObj, self.echartId)
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
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {color: 'black'},
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
            ]
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
                legendData.push(item.itemName);
                seriesData.push({"value": item.cost, "name": item.itemName});
            });
            self.echartOption.legend.data = legendData;
            self.echartOption.series[0].data = seriesData;
            self.initEcharts();
        },
        initEcharts: function () {
            var self = this;
            self.echartObj = echarts.init(document.getElementById(self.echartId));
            self.echartObj.setOption(self.echartOption);
            if (self.resultData.length == 0) {
                ShowNoDataEcharts(self.echartObj, self.echartId)
            }
        },
        init: function () {
            var self = this;
            self.getRequestData(reqOrgId);
        }
    };
    contrastDetailObj.init();

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
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12,
                        fontFamily: "'Applied Font Regular', 'Applied Font'"
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
                    }, textStyle: {
                        color: 'black',
                        fontSize: 12,
                        //fontFamily:"'Microsoft YaHei', Arial, Helvetica, sans-serif, '宋体'",
                        fontStyle: 'normal',
                        fontWeight: 'normal'
                    }
                }, axisLine: {
                show: true,
                onZero: false,
                lineStyle: {
                    width: 0,
                    color: '#000000'
                }
            }
            }
        ],
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
                data: [0, 900, 1245, 1530, 1376, 1376, 1511, 1689, 1856, 1495, 1292]
            },
            {
                name: '成本',
                type: 'bar',
                stack: '成本',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: 'black'
                            }
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
        }, getData: function (orgId) {
            var self = this;
            $.ajax({
                url: urls.getTrendByMonth,
                //  type:"post",
                data: {organId: orgId},
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    } else {
                        trendByMonthOption.xAxis[0].data = [];
                        //trendByMonthOption.yAxis[0].max = max+30;
                        trendByMonthOption.series[0].data = [];
                        trendByMonthOption.series[1].data = [];
                        self.chart.setOption(trendByMonthOption, true);
                    }

                }
            });
        }, render: function (result) {
            var lastData = [0, 0];
            processerbar.setData(result.budget, result.total);
            var data = [result.budget, result.total];
            var xdata = ['当年预算', '当年累计'];
            var record = 0;
            $.each(result.detail, function (i, o) {
                lastData.push(record);
                data.push(o.cost);
                var month = parseInt((o.yearMonth + "").substring(4, 6)) + "月";
                xdata.push(month);
                record += o.cost;
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


    //人力成本趋势
    //人力成本趋势
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
                type: "value",
                splitNumber: 8,
                name: "",
                nameTextStyle: {
                    color: '#000000',
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
                        color: '#000000'
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12,
                        fontStyle: 'normal',
                        fontWeight: 'normal'
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
                data: []

            }
        ]

    }
    var manpowerTrendsObj = {
        id: "manpowerTrend",
        chart: null,
        init: function (organId) {
            var self = this;
            if (this.chart == null) {
                this.chart = echarts.init(document.getElementById(this.id));
            }
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
                        self.render(result);
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
            this.chart.setOption(manpowerTrendOption, true);
        }
    }
    manpowerTrendsObj.init(reqOrgId);

    //人力成本趋势
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
            var xAxisData = [], seriesData1 = [], seriesData2 = [], seriesData3 = [], seriesData4 = [];
            $.each(self.resultData, function (i, item) {
                var month = parseInt((item.yearMonth + "").substring(4, 6)) + "月";
                xAxisData.push(month);
                seriesData1.push(item.cost);
                seriesData2.push(item.gainAmount);
                seriesData3.push(item.salesAmount);
                seriesData4.push(item.costAvg);
//                seriesData2.push(item.costAvg);
//                seriesData3.push(item.gainAmount);
//                seriesData4.push(item.salesAmount);
            });
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
            self.echartObj.setOption(self.echartOption);
        },
        init: function () {
            var self = this;
            self.getRequestData(reqOrgId, year);
        }
    };
    manpowerTrendObj.init();
    $(function () {
        $("[data-toggle='tooltip']").tooltip();
    });
});