require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'timeLine2', 'resize', 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        //页签一
        getSalaryBudgetYear: webRoot + "/salaryBoard/getSalaryBudgetYear.do",            //薪酬总额
        getSalaryProportion: webRoot + "/salaryBoard/getSalaryProportion.do",            //薪酬占人力成本比
        getSalaryRateOfReturn: webRoot + "/salaryBoard/getSalaryRateOfReturn.do",            //人力资本投资回报率
        getSalaryPayTotal: webRoot + "/salaryBoard/getSalaryPayTotal.do",            //薪酬总额统计
        getSalarySubOrganization: webRoot + "/salaryBoard/getSalarySubOrganization.do",            //下级组织薪酬统计
        getSalaryCostKpi: webRoot + "/salaryBoard/getSalaryCostKpi.do",            //组织KPI达标率、人力成本、薪酬总额的年度趋势
        getSalaryCostSalesProfit: webRoot + "/salaryBoard/getSalaryCostSalesProfit.do",            //营业额、利润、人力成本及薪酬总额的月度趋势
        getSalaryMonthRateOfReturn: webRoot + "/salaryBoard/getSalaryMonthRateOfReturn.do",            //人力资本投资回报率月度趋势
        getSalaryBitValueYear: webRoot + "/salaryBoard/getSalaryBitValueYear.do",            //行业分位值年度趋势
        getSalaryDifferencePost: webRoot + "/salaryBoard/getSalaryDifferencePost.do",            //薪酬差异度岗位表
        getSalaryEmpCR: webRoot + "/salaryBoard/getSalaryEmpCR.do",            //员工CR值
        findSalaryEmpCR: webRoot + "/salaryBoard/findSalaryEmpCR.do",            //更多员工CR值
        //页签二
        getSalaryWageStatistics: webRoot + "/salaryBoard/getSalaryWageStatistics.do",//工资统计
        getSalaryWagesMonth: webRoot + "/salaryBoard/getSalaryWagesMonth.do",//工资总额月度趋势
        getSalaryWagesYear: webRoot + "/salaryBoard/getSalaryWagesYear.do", //工资总额及占薪酬比年度趋势
        getSalarySubOrgWages: webRoot + "/salaryBoard/getSalarySubOrgWages.do", //下级组织工资对比
        getSalaryWageStructure: webRoot + "/salaryBoard/getSalaryWageStructure.do", //工资结构
        getSalaryFixedProportion: webRoot + "/salaryBoard/getSalaryFixedProportion.do", //固定与浮动薪酬分析
        getSalarySequenceFixed: webRoot + "/salaryBoard/getSalarySequenceFixed.do",  //职位序列固浮比统计
        getSalaryAbilityFixed: webRoot + "/salaryBoard/getSalaryAbilityFixed.do", //职位序列固浮比统计
        getSalarySubOrgFixed: webRoot + "/salaryBoard/getSalarySubOrgFixed.do", //下级组织固浮比
        getSalarySubOrgFixedList: webRoot + "/salaryBoard/getSalarySubOrgFixedList.do", //下级组织固浮比 列表
        getSalaryBonusProfit: webRoot + "/salaryBoard/getSalaryBonusProfit.do", //年终奖金总额与利润的年度趋势
        getSalaryBonusProfitList: webRoot + "/salaryBoard/getSalaryBonusProfitList.do", //年终奖金总额与利润的年度趋势 列表


        //TODO 页签三 
        getSalaryWelfare: webRoot + "/salaryBoard/getSalaryWelfare.do",            //福利费用统计
        getSalaryWelfareMonth: webRoot + "/salaryBoard/getSalaryWelfareMonth.do",            //福利费用总额月度趋势
        getSalaryWelfareYear: webRoot + "/salaryBoard/getSalaryWelfareYear.do",            //福利费用及占薪酬比年度趋势
        getSalarySubOrgWelfare: webRoot + "/salaryBoard/getSalarySubOrgWelfare.do",            //下级组织福利费用总额对比
        getSalarySubOrgAvgWelfare: webRoot + "/salaryBoard/getSalarySubOrgAvgWelfare.do",            //下级组织平均福利费用对比

        getSalaryWelfareCategoryUrl: webRoot + '/salaryBoard/getSalaryWelfareCategory.do',    //获取福利类型
        getSalaryFixedBenefitsUrl: webRoot + '/salaryBoard/getSalaryFixedBenefits.do',        //国家固定福利
        findSalaryBenefitsDetailedUrl: webRoot + '/salaryBoard/findSalaryBenefitsDetailed.do',        //国家固定福利明细
        getSalaryBenefitsCurrencyUrl: webRoot + '/salaryBoard/getSalaryBenefitsCurrency.do',        //企业福利货币
        findSalaryCurrencyDetailedUrl: webRoot + '/salaryBoard/findSalaryCurrencyDetailed.do',        //企业福利货币明细
        getSalaryBenefitsNoCurrencyUrl: webRoot + '/salaryBoard/getSalaryBenefitsNoCurrency.do',        //企业福利非货币
        findSalaryNoCurrencyDetailedUrl: webRoot + '/salaryBoard/findSalaryNoCurrencyDetailed.do',        //企业福利非货币明细

        //TODO 页签四
        getSalarySharesUrl: webRoot + '/salaryBoard/getSalaryShares.do',                    //持股统计
        getSalaryEmpSharesUrl: webRoot + '/salaryBoard/getSalaryEmpShares.do',              //持股员工总数年度趋势
        getSalarySumSharesUrl: webRoot + '/salaryBoard/getSalarySumShares.do',              //持股数量年度趋势
        getSalarySubOrgSharesUrl: webRoot + '/salaryBoard/getSalarySubOrgShares.do',        //下级组织持股员工数
        getSalarySubOrgSumSharesUrl: webRoot + '/salaryBoard/getSalarySubOrgSumShares.do',  //下级组织持股数量
        findSalaryEmpSharesUrl: webRoot + '/salaryBoard/findSalaryEmpShares.do',            //员工股票期权


        getYearCostUrl: webRoot + '/trainBoard/getYearCost.do',                 //费用年度趋势图
        getSubOrganPassengersUrl: webRoot + '/trainBoard/getSubOrganizationPassengers.do', //下级组织培训人次对比
        findTrainingRecordUrl: webRoot + '/trainBoard/findTrainingRecord.do'           //培训记录
    };
    $(win.document.getElementById('tree')).next().show();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var tableHeight = 321;

    var chartPieColor = ["#0b98e0", "#00bda9", "#4573a7", "#92c3d4", "#de6e1b", "#ff0084", "#af00e1", "#8d55f6", "#6a5888", "#2340f3"];

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;

        timeLineObj.init(organId);

        var t = pageObj;
        t.tabFirstLoaded = t.tabSecondLoaded = t.tabThreeLoaded = t.tabFourLoaded = false;
        switch (t.tabName) {
            case "page-one":
            {
                t.initFirstTab();
                break;
            }
            case "page-two":
            {
                t.initSecondTab();
                break;
            }
            case "page-three":
            {
                t.initThreeTab();
                break;
            }
            case "page-four":
            {
                t.initFourTab();
                break;
            }
        }
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
    timeLineObj.init(reqOrgId);

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart") {
            $(this).parents(".SetUpBody").find(".table-view").show();
            $(this).parents(".SetUpBody").find(".chart-view").hide();
            $(this).parents(".SetUpBody").attr("view", "table");
        } else {
            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
        }

        salarySubOrgObj.resizeChart();
    });


    /***
     * 修改grid分页图标
     */
    function updatePagerIcons() {
        var replacement =
        {
            'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev': 'icon-angle-left bigger-140',
            'ui-icon-seek-next': 'icon-angle-right bigger-140',
            'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

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

            pageObj.click(_page);
        }
    });

    $(".chartselect div").on("click", function () {
        $(".chartselect .select").removeClass("select");
        $(this).addClass("select");
        $(".chartselectcontent").addClass("hide");
        $("#" + $(this).data("id")).removeClass("hide");

        salarySubOrgWageObj.resizeChart();
    });

    var optionGrid = {
        borderWidth: 0,
        x: 55,
        x2: 15,
        y: 25
    };

    var dataZoom = {
        show: true,
        realtime: true,
        height: 20,
        start: 0,
        end: 30,
        showDetail: false
    };
    //echarts显示滚动条需要达到多少柱
    var showDataZoomNumber = 4;
    var loadingText = "数据加载中";
    var nodataText = "暂无数据";

    //公用Option
    var commonOption = {
        //薪酬总额、工资总额
        Total: {
            toolbox: {
                show: false
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
                            fontFamily: "'微软雅黑', 'verdana'"
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
                    name: "（万元）",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
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
                            color: "#0b98e0",
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    tooltip: {
                        show: false
                    },
                    barMaxWidth: 50
                }
            ],
            grid: optionGrid,
            dataZoom: dataZoom
        },
        Avg: {
            toolbox: {
                show: false
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
                            fontFamily: "'微软雅黑', 'verdana'"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    splitLine: {
                        show: true,
                        lineStyle: {
                            width: 1,
                            color: "#ddd"
                        }
                    },
                    name: "（万元）",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
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
                            color: "#0b98e0",
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    tooltip: {
                        show: false
                    },
                    barMaxWidth: 50
                }
            ],
            grid: optionGrid,
            dataZoom: dataZoom
        }
    };

    /*
     *************************************************************
     * 页签一开始
     * ***********************************************************
     * */

    //年薪酬总额
    var salarytotalObj = {
        click: function () {
            $("#salarytotalTab span").on("click", function () {
                $(this).parent().find("span").removeClass("select");
                $(this).addClass("select");
                $("#salarytotalContent .content").addClass("hide");
                $("#" + $(this).data("id")).removeClass("hide");
            });
        },
        init: function () {
            var self = this;
            var year = '-', budget = '-', budgetRate = '-', total = '-', totalRate = '-';
            $("#budget .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $("#accumulative .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getSalaryBudgetYear, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#budget .data").addClass("hide").eq(1).removeClass("hide");
                    $("#accumulative .data").addClass("hide").eq(1).removeClass("hide");

                    year = data.year;
                    budget = Tc.formatFloat(data.payValue);
                    budgetRate = Tc.formatFloat(data.compareValue);
                    total = Tc.formatFloat(data.sumPay);
                    totalRate = Tc.formatFloat(data.totalCompareValue);
                    self.render(year, budget, budgetRate, total, totalRate);
                } else {
                    $("#salarytotal").text(1900 + new Date().getYear());
                    $("#budget .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                    $("#accumulative .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }

                var obj = $("#salarytotalTab").parent().find(".index-common-title-text");
                obj.attr("title", obj.text());
            });
        },
        render: function (year, budget, budgetRate, total, totalRate) {
            $("#salarytotal").text(year);
            $("#budget .accord-yj-float-value").text(budget);
            $("#budget .accord-bottom-float-value").text((budgetRate>0?"+":"") + budgetRate + "%").removeClass("red").removeClass("green").addClass(budgetRate > 0 ? "red" : (budgetRate < 0 ? "green" : ""));
            $("#accumulative .accord-yj-float-value").text(total);
            $("#accumulative .accord-bottom-float-value").text((totalRate>0?"+":"") + totalRate + "%").removeClass("red").removeClass("green").addClass(totalRate > 0 ? "red" : (totalRate < 0 ? "green" : ""));
        }
    }
    salarytotalObj.click();

    //年薪酬占人力成本比
    var salarystractureOjb = {
        init: function () {
            var self = this;
            $("#salarystractureContent .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getSalaryProportion, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#salarystractureContent .data").addClass("hide").eq(1).removeClass("hide");
                    self.render(data.year, Tc.formatFloat(data.proportion));
                } else {
                    $("#salarystracture").text(1900 + new Date().getYear());
                    $("#salarystractureContent .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }
            });
        },
        render: function (year, value) {
            $("#salarystracture").text(year);
            $("#salarystractureContent .accord-yj-float-value").text(value);
        }
    }

    //年人力资本投资回报率
    var salaryrateOjb = {
        init: function () {
            var self = this;
            $("#salaryrateContent .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getSalaryRateOfReturn, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#salaryrateContent .data").addClass("hide").eq(1).removeClass("hide");
                    self.render(data.year, Tc.formatFloat(data.rateOfReturn));
                } else {
                    $("#salaryrate").text(1900 + new Date().getYear());
                    $("#salaryrateContent .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }
            });
        },
        render: function (year, value) {
            $("#salaryrate").text(year);
            $("#salaryrateContent .accord-yj-float-value").text(value);
        }
    }

    //薪酬总额统计
    var salaryCostChartObj = {
        option: {
            grid: optionGrid,
            xAxis: [
                {
                    type: "category",
                    splitLine: {show: false},
                    axisTick: false,
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#ccc',
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
                            fontFamily: "'微软雅黑', 'verdana'"
                        },
                        interval: 0,
                        rotate: 30
                    }
                }
            ],
            yAxis: [
                {
                    name: "（万元）",
                    splitLine: false,
                    type: 'value',
                    axisLabel: {
                        itemStyle: {
                            color: '#333'
                        },
                        textStyle: {
                            color: '#333',
                            fontSize: 12,
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
                    clickable: false,
                    type: 'bar',
                    stack: '1',
                    itemStyle: {
                        normal: {
                            color: 'rgba(0,0,0,0)'
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    data: []
                },
                {
                    clickable: false,
                    name: '薪酬总额统计',
                    type: 'bar',
                    stack: '1',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'top'
                            },
                            color: function (a) {
                                if (a.dataIndex == 0) {
                                    return "#A6A6A6";
                                } else if (a.dataIndex == 1) {
                                    return "#0b98e0";
                                } else {
                                    return "#33CABA";
                                }

                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    data: []
                }
            ]
        },
        data: null,
        chart: null,
        chartId: "salaryCostChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryPayTotal, {organId: reqOrgId}, function (data) {
                if (data && data.list && data.list.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var thisyear = Tc.formatFloat(data.payValue), total = Tc.formatFloat(data.sumPay);
            //计算
            var whiteBar = [0, 0], barName = ["当年预算", "当年累计"], barValue = [thisyear, total], v = 0;
            //var list=[{"name":"1月",value:8},{"name":"2月",value:52},{"name":"3月",value:48},{"name":"4月",value:8},{"name":"5月",value:52},{"name":"6月",value:48}]
            $.each(data.list, function (i, item) {
                whiteBar.push(Tc.formatFloat(v));
                barName.push(item.yearMonth);
                barValue.push(Tc.formatFloat(item.sumPay));

                v += item.sumPay;
            });
            //option
            self.option.xAxis[0].data = barName;
            self.option.series[0].data = whiteBar;
            self.option.series[1].data = barValue;

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //下级组织薪酬统计
    var salarySubOrgObj = {
        optionTotal: Tc.cloneObj(commonOption.Total),
        optionAvg: Tc.cloneObj(commonOption.Avg),
        data: null,
        chartTotal: null,
        chartAvg: null,
        chartTotalId: "salaryCostSubTotalChart",
        chartAvgId: "salaryCostSubAvgChart",
        init: function () {
            var self = this;
            loadingChart(self.chartTotalId);
            loadingChart(self.chartAvgId);
            $.get(urls.getSalarySubOrganization, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.renderTotal();
                    self.renderAvg();
                } else {
                    self.data = null;
                    hideChart(self.chartTotalId, true);
                    hideChart(self.chartAvgId, true);
                }
            });
        },
        renderTotal: function () {
            var self = this;
            var data = self.data;

            if (self.chartTotal) {
                self.chartTotal.clear();
            }
            self.chartTotal = initEChart(self.chartTotalId);

            var xAxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.organizationName);
                seriesData.push(Tc.formatFloat(item.sumPay));
            });
            //option
            self.optionTotal.xAxis[0].data = xAxisData;//["深圳分公司","上海分公司","广州分公司","北京分公司","北京总部"];
            self.optionTotal.series[0].data = seriesData;//薪酬总额
            self.optionTotal.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chartTotal.setOption(self.optionTotal);
            self.chartTotal.refresh();
            self.chartTotal.resize();
        },
        renderAvg: function () {
            var self = this;
            var data = self.data || [];
            data = _.sortBy(data, function (o) {
                return -Tc.formatFloat(o.avgPay);
            });

            if (self.chartAvg) {
                self.chartAvg.clear();
            }
            self.chartAvg = initEChart(self.chartAvgId);

            var xAxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.organizationName);
                seriesData.push(Tc.formatFloat(item.avgPay));
            });
            //option
            self.optionAvg.xAxis[0].data = xAxisData;//["深圳分公司","上海分公司","广州分公司","北京分公司","北京总部"];
            self.optionAvg.series[0].data = seriesData;//平均薪酬
            self.optionAvg.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chartAvg.setOption(self.optionAvg);
            self.chartAvg.refresh();
            self.chartAvg.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chartTotal && self.data) {
                self.chartTotal.resize();
            }
            if (self.chartAvg && self.data) {
                self.chartAvg.resize();
            }
        }
    };

    //组织KPI达标率、人力成本、薪酬总额的年度趋势
    var salarycompletionYearRateObj = {
        option: {
            tooltip: {
                show: false
            },
            legend: {
                data: ["薪酬额", "人力成本", "组织PKI达标率"],
                y: "bottom",
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
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
                    min: 0,
                    max: 0,
                    name: "（万元）",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
                        }
                    }
                },
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    min: 0,
                    max: 100,
                    axisLabel: {
                        show: true,
                        formatter: '{value}%'
                    }
                }
            ],
            series: [
                {
                    name: "薪酬额",
                    type: "bar",
                    yAxisIndex: 0,
                    clickable: false,
                    barMaxWidth: 40,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barGap: 0
                },
                {
                    name: "人力成本",
                    type: "bar",
                    yAxisIndex: 0,
                    clickable: false,
                    barMaxWidth: 40,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                },
                {
                    type: "line",
                    name: "组织PKI达标率",
                    yAxisIndex: 1,
                    clickable: false,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: "{c}%"
                            }
                        }
                    }
                }
            ],
            grid: $.extend({}, optionGrid, {x2: 50}),
            color: ["#0b98e0", "#33CABA", "rgb(255, 192, 0)"]
        },
        data: null,
        chart: null,
        chartId: "salarycompletionYearRateChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryCostKpi, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var years = [], min = 0, max = 0, series0 = [], series1 = [], series2 = [], all = [];
            $.each(data, function (i, item) {
                years.push(item.year + '年');
                series0.push(Tc.formatFloat(item.sumPay));
                series1.push(Tc.formatFloat(item.cost));
                series2.push(Tc.formatFloat(item.kpi * 100));

                all.push(Tc.formatFloat(item.sumPay));
                all.push(Tc.formatFloat(item.cost));
            });
            min = _.min(all);
            min = parseInt(min / 100) * 100;
            max = _.max(all);
            max = (parseInt(max / 100) + 1) * 100;

            //option
            self.option.xAxis[0].data = years; //["2012年", "2013年", "2014年", "2015年", "2016年"];
            self.option.yAxis[0].min = min; //500;
            self.option.yAxis[0].max = max; //1200;
            self.option.series[0].data = series0; //薪酬额
            self.option.series[1].data = series1; //人力成本
            self.option.series[2].data = series2; //组织KPI达标率

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //营业额、利润、人力成本及薪酬总额的月度趋势
    var salarycompletionMonthRateObj = {
        option: {
            tooltip: {
                show: true,
                trigger: 'axis',
                axisPointer: {
                    type: 'line',
                    lineStyle: {
                        color: '#ccc',
                        width: 1,
                        type: 'solid'
                    }
                }
            },
            legend: {
                data: ["人力成本", "利润", "销售额", "薪酬总额"],
                y: "300",
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "rgb(51, 51, 51)"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    name: "（万元）",
                    nameTextStyle: {
                        color: "rgb(153, 153, 153)"
                    },
                    splitNumber: 5,
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: true,
                        length: 3,
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    }
                }
            ],
            series: [
                {
                    name: "人力成本",
                    type: "line",
                    data: [],
                    clickable: false,
                    symbol: "circle",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        }
                    }
                },
                {
                    name: "利润",
                    type: "line",
                    data: [],
                    symbol: "circle",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        }
                    }
                },
                {
                    type: "line",
                    name: "销售额",
                    data: [],
                    symbol: "circle",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        }
                    }
                },
                {
                    type: "line",
                    name: "薪酬总额",
                    data: [],
                    symbol: "circle",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        }
                    }
                }
            ],
            grid: $.extend({}, optionGrid, {y2: 80}),
            color: ["rgb(91, 155, 213)", "rgb(237, 125, 49)", "rgb(165, 165, 165)", "rgb(255, 192, 0)"],
            calculable: false,
            dataZoom: dataZoom
        },
        data: null,
        chart: null,
        chartId: "salarycompletionMonthRateChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryCostSalesProfit, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var years = [], series0 = [], series1 = [], series2 = [], series3 = [];
            $.each(data, function (i, item) {
                years.push(item.yearMonth);
                series0.push(Tc.formatFloat(item.cost));
                series1.push(Tc.formatFloat(item.gainAmount));
                series2.push(Tc.formatFloat(item.salesAmount));
                series3.push(Tc.formatFloat(item.sumPay));
            });
            //option
            self.option.xAxis[0].data = years; //["16/01", "16/02", "16/03", "16/04", "16/05", "16/06"];
            self.option.series[0].data = series0; //人力成本
            self.option.series[1].data = series1; //利润
            self.option.series[2].data = series2; //销售额
            self.option.series[3].data = series3; //薪酬总额
            if (data.length > 6) {
                self.option.dataZoom = dataZoom;
            } else {
                self.option.dataZoom = null;
            }

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //人力资本投资回报率月度趋势
    var salaryBringBackMonthObj = {
        option: {
            tooltip: {
                show: false
            },
            legend: {
                data: ["人力资本投资回报率"],
                y: "bottom",
                selectedMode: false
            },
            toolbox: {
                show: false,
                feature: {
                    dataView: {
                        readOnly: true
                    },
                    magicType: {
                        type: ["line", "bar"],
                        show: false
                    }
                }
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        length: 3,
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    axisLabel: {
                        interval: 0,
                        rotate: 30
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    name: "人力资本投资回报率",
                    type: "line",
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        }
                    }
                }
            ],
            grid: optionGrid
        },
        data: null,
        chart: null,
        chartId: "salaryBringBackMonthChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryMonthRateOfReturn, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var years = [], seriesData = [];
            $.each(data, function (i, item) {
                years.push(item.yearMonth);
                seriesData.push(Tc.formatFloat(item.rateReturn));
            });
            //option
            self.option.xAxis[0].data = years;//["16/01", "16/02", "16/03", "16/04", "16/05", "16/06"];
            self.option.series[0].data = seriesData;//投资回报率

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //行业分位值年度趋势
    var salaryBringBackYearObj = {
        option: {
            tooltip: {
                show: false
            },
            legend: {
                data: ["行业分位值"],
                y: "bottom",
                selectedMode: false
            },
            toolbox: {
                show: false,
                feature: {
                    dataView: {
                        readOnly: true
                    },
                    magicType: {
                        type: ["line", "bar"],
                        show: false
                    }
                }
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        length: 3,
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    name: "（分位）",
                    min: 0,
                    max: 100,
                    splitNumber: 4
                }
            ],
            series: [
                {
                    name: "行业分位值",
                    type: "line",
                    data: [],
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: {
                                show: false
                            }
                        }
                    },
                    symbolSize: 2
                }
            ],
            grid: optionGrid
        },
        data: null,
        chart: null,
        chartId: "salaryBringBackYearChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryBitValueYear, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var xaxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.year + '年');
                seriesData.push(Tc.formatFloat(item.bitValue));
            });
            //option
            self.option.xAxis[0].data = xaxisData;//["2012年", "2013年", "2014年", "2015年", "2016年"];
            self.option.series[0].data = seriesData;//分位值

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //薪酬差异度岗位表
    var salaryDiffList = {
        jqGrid: null,
        data: null,
        option: {
            data: [],
            datatype: "local",
            width: 0,
            height: 336,
            rowNum: 999999,
            colNames: ['序号', '岗位名称', '岗位薪酬差异度'],
            colModel: [
                {name: 'id', index: 'id', sortable: false, align: 'center'},
                {name: 'name', index: 'name', sortable: false, align: 'center'},
                {name: 'diff', index: 'diff', sortable: false, align: 'center'}
            ]
        },
        gridId: "salaryDiffList",
        init: function () {
            var self = this;
            loadingGrid(self.gridId, true);
            $.get(urls.getSalaryDifferencePost, {organId: reqOrgId}, function (data) {
                loadingGrid(self.gridId, false);
                if (data && data.date)
                    $("#" + self.gridId + "Date").text("（" + data.date + "）");

                if (data && data.list) {
                    var list = [];
                    $.each(data.list, function (i, item) {
                        list.push({
                            id: i + 1,
                            name: item.positionName,
                            diff: Tc.formatFloat(item.difference)
                        });
                    });
                    self.data = list;
                    self.render();
                } else {
                    hideGrid(self.gridId);
                }
            });
        },
        render: function () {
            var self = this;
            self.jqGrid = $("#" + self.gridId).jqGrid(self.option);
            self.jqGrid.clearGridData().setGridParam({
                data: self.data
            }).trigger("reloadGrid");

            self.resize();
        },
        resize: function () {
            var self = this;
            if (self.jqGrid) {
                self.jqGrid.setGridWidth($("#gbox_" + self.gridId).parent().width());
            }
        }
    };

    //员工CR值
    var salaryCrObj = {
        option: {
            toolbox: {
                show: false
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
                            color: '#333'
                        },
                        textStyle: {
                            color: '#333',
                            fontSize: 12,
                            fontFamily: "'微软雅黑', 'verdana'"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    splitLine: {
                        show: true,
                        lineStyle: {
                            width: 1,
                            color: "#ddd"
                        }
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
                    clickable: false,
                    itemStyle: {
                        normal: {
                            color: "#0b98e0",
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    tooltip: {
                        show: false
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
        data: null,
        chart: null,
        chartId: "salaryCrChart",
        jqGrid: null,
        optionGrid: {
            url: urls.findSalaryEmpCR,
            mtype: 'POST',
            datatype: "json",
            width: 0,
            height: 0,
            colNames: ['姓名', '所属组织', '职级', '绩效', 'CR值'],
            colModel: [
                {name: 'name', index: 'name', sortable: false, align: 'center'},
                {name: 'organizationName', index: 'organizationName', sortable: false, align: 'center'},
                {name: 'abilityLvName', index: 'abilityLvName', sortable: false, align: 'center'},
                {name: 'performanceName', index: 'performanceName', sortable: false, align: 'center'},
                {name: 'cr', index: 'cr', sortable: false, align: 'center'}
            ],
            rownumbers: true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '#CrGridPager',
            postData: {},
            loadComplete: function (xhr) {

                updatePagerIcons();
            }
        },
        dataGrid: null,
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryEmpCR, {organId: reqOrgId}, function (data) {
                if (data && data.date)
                    $("#" + self.chartId + "Date").text("（" + data.date + "）");

                if (data && data.list && data.list.length > 0) {
                    self.data = data.list;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var xAxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                if (i < 5) {
                    xAxisData.push(item.empName);
                    seriesData.push(Tc.formatFloat(item.crValue));
                }
            });
            //option
            self.option.xAxis[0].data = xAxisData;//["张三","张琳","张丽","张扬","张浩"];
            self.option.series[0].data = seriesData; //Cr值

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        renderMore: function () {
            var self = this;
            if (self.jqGrid) {
                self.jqGrid.clearGridData().setGridParam({
                    postData: {organId: reqOrgId}
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.postData = {organId: reqOrgId};
                self.jqGrid = $("#CrGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        click: function () {
            var self = this;
            $("#salaryCrMore").click(function () {
                $("#moreCr").modal("show").on('shown.bs.modal', function () {
                    $("#crMoreGrid").css({height: $(window).height() - 120 + "px"});
                    self.optionGrid.width = $("#crMoreGrid").width() - 30;
                    self.optionGrid.height = $("#crMoreGrid").height() - 130;
                    self.renderMore();
                });
            });
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqGrid)
                self.jqGrid.setGridWidth($("#crMoreGrid").width() - 30);
        },
        resizeChart: function () {
            var self = this;
            self.resizeGrid();
            if (self.chart) {
                self.chart.resize();
            }
        }
    }
    salaryCrObj.click();

    /*
     *************************************************************
     * 页签二开始
     * ***********************************************************
     * */
    //工资统计
    var salaryWageStatis = {
        init: function () {
            var self = this;
            self.render('...', '...');
            $.get(urls.getSalaryWageStatistics, {organId: reqOrgId}, function (data) {
                if (data) {
                    self.render(Tc.formatFloat(data.wages), Tc.formatFloat(data.wageShare))
                } else {
                    self.render('-', '-');
                }
            });
        },
        render: function (wage, rate) {
            $("#salaryWageTotal").text(wage);
            $("#salaryWageRate").text(rate);
        }
    };

    //工资总额月度趋势
    var salaryTotalMonthObj = {
        option: {
            tooltip: {
                show: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        length: 3,
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    name: "（万元）",
                    nameLocation: "end"
                }
            ],
            series: [
                {
                    name: "行业分位值",
                    type: "line",
                    clickable: false,
                    data: [],
                    markPoint: {
                        itemStyle: {
                            normal: {
                                color: '#333',
                                borderWidth: 30
                            },
                            emphasis: {
                                color: 'rgba(0,0,0,0)'
                            }
                        },
                        clickable: false,
                        data: []
                    },
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: function (a) {
                                    return a.name == salaryTotalMonthObj.name ? '' : a.value;
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    symbolSize: 0
                }
            ],
            grid: optionGrid,
            dataZoom: dataZoom
        },
        name: null,
        data: null,
        chart: null,
        chartId: "salaryTotalMonthChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryWagesMonth, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var xAxisData = [], seriesData = [], lastxAxis = "", lastValue = "";
            $.each(data, function (i, item) {
                xAxisData.push(item.yearMonth);
                seriesData.push(Tc.formatFloat(item.sumSalary));
                if (i == data.length - 1) {
                    lastxAxis = item.yearMonth;
                    lastValue = Tc.formatFloat(item.sumSalary);
                }
            });
            //option
            self.option.xAxis[0].data = xAxisData;//["16/01", "16/02", "16/03", "16/04", "16/05"];
            self.option.series[0].data = seriesData;//工资总额
            self.option.series[0].markPoint.data = [
                {
                    name: '标注1',
                    value: lastValue,
                    xAxis: lastxAxis,
                    yAxis: lastValue
                }
            ];
            self.name = lastxAxis;
            if (data.length > 6) {
                self.option.dataZoom = dataZoom;
            } else {
                self.option.dataZoom = null;
            }

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //工资总额及占薪酬比年度趋势
    var salaryTotalRateObj = {
        option: {
            tooltip: {
                show: false
            },
            legend: {
                data: ["工资总额", "占薪酬总额比"],
                y: "bottom",
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
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
                    min: 0,
                    max: 0,
                    name: "（万元）",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
                        }
                    }
                },
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    min: 0,
                    max: 100,
                    axisLabel: {
                        show: true,
                        formatter: '{value}%'
                    }
                }
            ],
            series: [
                {
                    name: "工资总额",
                    type: "bar",
                    yAxisIndex: 0,
                    clickable: false,
                    barMaxWidth: 50,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barGap: 0
                },
                {
                    type: "line",
                    name: "占薪酬总额比",
                    yAxisIndex: 1,
                    clickable: false,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: "{c}%"
                            }
                        }
                    }
                }
            ],
            grid: $.extend({}, optionGrid, {x2: 50}),
            color: ["#0b98e0", "rgb(255, 192, 0)"]
        },
        data: null,
        chart: null,
        chartId: "salaryTotalRateChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryWagesYear, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var years = [], min = 0, max = 0, series0 = [], series1 = [], all = [];
            $.each(data, function (i, item) {
                years.push(item.year + '年');
                series0.push(Tc.formatFloat(item.sumSalary));
                series1.push(Tc.formatFloat(item.wageShare * 100));

                all.push(Tc.formatFloat(item.sumSalary));
            });
            min = _.min(all);
            min = parseInt(min / 100) * 100;
            max = _.max(all);
            max = (parseInt(max / 100) + 1) * 100;
            //option
            self.option.xAxis[0].data = years;//["2012年", "2013年", "2014年", "2015年", "2016年"];
            self.option.yAxis[0].min = min;
            self.option.yAxis[0].max = max;
            self.option.series[0].data = series0;//工资总额
            self.option.series[1].data = series1;//占薪酬总额比

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //下级组织工资对比
    var salarySubOrgWageObj = {
        optionTotal: Tc.cloneObj(commonOption.Total),
        optionAvg: Tc.cloneObj(commonOption.Avg),
        data: null,
        chartTotal: null,
        chartTotalId: "salaryTotalChart",
        chartAvg: null,
        chartAvgId: "salaryAvgChart",
        init: function () {
            var self = this;
            loadingChart(self.chartTotalId);
            loadingChart(self.chartAvgId);
            $.get(urls.getSalarySubOrgWages, {organId: reqOrgId}, function (data) {
                if (data && data.beginDate && data.endDate)
                    $("#" + self.chartTotalId + "Date").text("（" + data.beginDate + " - " + data.endDate + "）");

                if (data && data.list && data.list.length > 0) {
                    self.data = data.list;
                    self.renderTotal();
                    self.renderAvg();
                } else {
                    self.data = null;
                    hideChart(self.chartTotalId, true);
                    hideChart(self.chartAvgId, true);
                }
            });
        },
        renderTotal: function () {
            var self = this;
            var data = self.data || [];

            if (self.chartTotal) {
                self.chartTotal.clear();
            }
            self.chartTotal = initEChart(self.chartTotalId);

            var xaxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.organizationName);
                seriesData.push(Tc.formatFloat(item.sumSalary));
            });
            //option
            self.optionTotal.xAxis[0].data = xaxisData;//["深圳分公司","上海分公司","广州分公司","北京分公司","北京总部"];
            self.optionTotal.series[0].data = seriesData;//工资总额
            self.optionTotal.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chartTotal.setOption(self.optionTotal);
            self.chartTotal.refresh();
            self.chartTotal.resize();
        },
        renderAvg: function () {
            var self = this;
            var data = self.data || [];
            data = _.sortBy(data, function (o) {
                return -Tc.formatFloat(o.avgSalary);
            });

            if (self.chartAvg) {
                self.chartAvg.clear();
            }
            self.chartAvg = initEChart(self.chartAvgId);

            var xaxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.organizationName);
                seriesData.push(Tc.formatFloat(item.avgSalary));
            });
            //option
            self.optionAvg.xAxis[0].data = xaxisData;//["深圳分公司","上海分公司","广州分公司","北京分公司","北京总部"];
            self.optionAvg.series[0].data = seriesData;//工资总额
            self.optionAvg.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chartAvg.setOption(self.optionAvg);
            self.chartAvg.refresh();
            self.chartAvg.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chartTotal && self.data) {
                self.chartTotal.resize();
            }
            if (self.chartAvg && self.data) {
                self.chartAvg.resize();
            }
        }
    };

    //工资结构
    var salaryStructureObj = {
        option: {
            legend: {
                data: [],
                y: 'bottom',
                selectedMode: false,
                textStyle: {color: '#555', fontFamily: '微软雅黑 verdana tahoma', fontSize: 12},
                itemWidth: 12,
                itemHeight: 12,
                itemGap: 12,
                padding: 5
            },
            toolbox: {
                show: false
            },
            calculable: false,
            series: [
                {
                    name: "工资结构",
                    clickable: false,
                    type: "pie",
                    radius: "60%",
                    center: ["50%", "50%"],
                    data: [],
                    tooltip: {
                        show: false
                    },
                    itemStyle: {
                        normal: {
                            label: {
                                formatter: function (a, b, c, d) {
                                    return a.name + ', ' + a.value + ',\n' + a.percent + '%';
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                }
            ],
            title: {
                text: "单位：万元",
                x: "right",
                y: "bottom",
                textStyle: {
                    color: '#999',
                    fontFamily: '微软雅黑 verdana tahoma',
                    fontSize: 12,
                    fontWeight: "normal"
                },
                padding: 5
            },
            color: chartPieColor
        },
        data: null,
        chart: null,
        chartId: "salaryStructureChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryWageStructure, {organId: reqOrgId}, function (data) {
                if (data && data.beginDate && data.endDate)
                    $("#" + self.chartId + "Date").text("（" + data.beginDate + " - " + data.endDate + "）");

                if (data && data.list && data.list.length > 0) {
                    self.data = data.list;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var seriesData = [], legendData = [];
            $.each(data, function (i, item) {
                seriesData.push({value: Tc.formatFloat(item.salaryValue), name: item.structureName});
                legendData.push({name: item.structureName, icon: 'bar'});
            });
            //option
            self.option.legend.data = legendData;
            self.option.series[0].data = seriesData;

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //固定与浮动薪酬分析
    var salaryFixedProportion = {
        init: function () {
            var self = this;
            self.render('...');
            $.get(urls.getSalaryFixedProportion, {organId: reqOrgId}, function (data) {
                if (data && data.fixed && data.flot) {
                    self.render(Tc.formatFloat(data.fixed) + ":" + Tc.formatFloat(data.flot));
                } else {
                    self.render('-');
                }
            });
        },
        render: function (rate) {
            $("#salaryFixedProportion").text(rate);
        }
    };

    //职位序列固浮比统计
    var positionSubObj = {
        drill: true,
        option: {
            tooltip: {
                show: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    min: 0,
                    max: 1
                }
            ],
            yAxis: [
                {
                    type: "category",
                    data: [],
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    name: "固定",
                    type: "bar",
                    stack: "1",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "insideRight",
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barMaxWidth: 40,
                    data: []
                },
                {
                    name: "浮动",
                    type: "bar",
                    stack: "1",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "insideLeft",
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    data: []
                }
            ],
            color: ["#0b98e0", "#33CABA"],
            grid: $.extend({}, optionGrid, {x: 80, x2: 30})
        },
        data: null,
        chart: null,
        chartId: "positionChart",
        optionSub: {
            grid: optionGrid,
            xAxis: [
                {
                    type: "category",
                    splitLine: {show: false},
                    axisTick: false,
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#ccc',
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
                            fontFamily: "'微软雅黑', 'verdana'"
                        },
                        interval: 0,
                        rotate: 30
                    }
                }
            ],
            yAxis: [
                {
                    name: "",
                    splitLine: false,
                    type: 'value',
                    min: 0,
                    max: 1,
                    axisLabel: {
                        itemStyle: {
                            color: '#333'
                        },
                        textStyle: {
                            color: '#333',
                            fontSize: 12,
                            fontStyle: 'normal',
                            fontWeight: 'normal'
                        }
                    },
                    axisLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    clickable: false,
                    type: 'bar',
                    stack: '职位序列固浮比统计',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'insideTop',
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barMaxWidth: 40,
                    data: []
                },
                {
                    clickable: false,
                    name: '职位序列固浮比统计',
                    type: 'bar',
                    stack: '职位序列固浮比统计',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'insideBottom',
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barMaxWidth: 40,
                    data: []
                }
            ],
            color: ["#0b98e0", "#33CABA"]
        },
        dataSub: null,
        chartSub: null,
        chartSubId: "positionSubChart",
        init: function () {
            var self = this;
            self.drill = true;
            loadingChart(self.chartId);
            $.get(urls.getSalarySequenceFixed, {organId: reqOrgId}, function (data) {
                if (data && data.beginDate && data.endDate)
                    $("#" + self.chartId + "Date").text("（" + data.beginDate + " - " + data.endDate + "）");

                if (data && data.list && data.list.length > 0) {
                    self.data = data.list;
                    self.render(self.data);
                } else {
                    hideChart(self.chartId, true);
                }
            });

            self.requestSub("", function (data) {
                self.dataSub = data;
                self.renderSub(data);
            });
        },
        render: function (data) {
            var self = this;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            //计算
            var ids = [], bargreen = [], barred = [], barName = [];
            $.each(data, function (i, item) {
                ids.push(item.sequenceId);
                barName.push(item.sequenceName);
                bargreen.push(Tc.formatFloat(item.fixed));
                barred.push(Tc.formatFloat(1 - item.fixed));
            });

            //option
            self.option.yAxis[0].data = barName;
            self.option.series[0].data = bargreen;
            self.option.series[1].data = barred;

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();

            self.setClickEvent();
        },
        //点击事件
        setClickEvent: function () {
            var self = this;
            self.chart.on('click', function (param) {
                if (self.drill) {//钻取数据
                    self.drill = false;
                    var index = param.dataIndex;
                    loadingChart(self.chartId);

                    //子
                    var id = self.data[index].sequenceId;
                    self.requestSub(id, function (data) {
                        var list = [];
                        list.push(self.data[index]);
                        self.render(list);
                        self.renderSub(data);
                    });
                } else {//返回上一级
                    self.drill = true;
                    setTimeout(function () {
                        self.render(self.data);
                        self.renderSub(self.dataSub);
                    }, 1);
                }
            });
        },
        requestSub: function (id, callback) {
            var self = this;
            loadingChart(self.chartSubId);
            $.get(urls.getSalaryAbilityFixed, {organId: reqOrgId, positionId: id}, function (data) {
                hideChart(self.chartSubId, false);
                if (data && data.length > 0) {
                    callback.call(new Object(), data);
                } else {
                    hideChart(self.chartSubId, true);
                }
            });
        },
        renderSub: function (data) {
            var self = this;

            if (self.chartSub) {
                self.chartSub.clear();
            }
            self.chartSub = initEChart(self.chartSubId);

            //计算
            var bargreen = [], barred = [], barName = [];
            $.each(data, function (i, item) {
                barName.push(item.abilityName);
                bargreen.push(Tc.formatFloat(item.fixed));
                barred.push(Tc.formatFloat(1 - item.fixed));
            });

            //option
            self.optionSub.xAxis[0].data = barName;
            self.optionSub.series[0].data = bargreen;
            self.optionSub.series[1].data = barred;

            self.chartSub.setOption(self.optionSub);
            self.chartSub.refresh();
            self.chartSub.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
            if (self.chartSub) {
                self.chartSub.resize();
            }
        }
    }

    //下级组织固浮比
    var subOrgObj = {
        option: {
            tooltip: {
                show: false
            },
            legend: {
                data: ["固定薪酬比", "浮动薪酬比"],
                y: "300",
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    min: 0,
                    max: 1
                }
            ],
            series: [
                {
                    name: "固定薪酬比",
                    type: "bar",
                    clickable: false,
                    data: [],
                    stack: "1",
                    barMaxWidth: 40,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "insideTop",
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        emphasis: {
                            color: "rgba(0, 0, 0, 0)"
                        }
                    }
                },
                {
                    name: "浮动薪酬比",
                    type: "bar",
                    clickable: false,
                    stack: "1",
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "insideBottom",
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        emphasis: {
                            color: "rgba(0, 0, 0, 0)"
                        }
                    }
                }
            ],
            grid: $.extend({}, optionGrid, {y2: 80}),
            color: ["#0b98e0", "#33CABA"],
            dataZoom: dataZoom
        },
        dataChart: null,
        chart: null,
        chartId: "subOrgChart",
        optionjqGrid: {
            data: [],
            datatype: "local",
            width: 0,
            height: 305,
            rowNum: 999999,
            colNames: ['组织名称', '固浮比', '固定工资（万元）', '浮动工资（万元）'],
            colModel: [
                {name: 'org', index: 'org', sortable: false, align: 'center'},
                {name: 'rate', index: 'rate', sortable: false, align: 'center'},
                {name: 'fixed', index: 'fixed', sortable: false, align: 'center'},
                {name: 'float', index: 'float', sortable: false, align: 'center'}
            ]
        },
        dataGrid: null,
        gridId: "subOrgList",
        init: function () {
            var self = this;
            //图表
            loadingChart(self.chartId);
            $.get(urls.getSalarySubOrgFixed, {organId: reqOrgId}, function (data) {
                if (data && data.beginDate && data.endDate)
                    $("#" + self.chartId + "Date").text("（" + data.beginDate + " - " + data.endDate + "）");

                if (data && data.list && data.list.length > 0) {
                    self.dataChart = _.sortBy(data.list, function (m) {
                        return -m.fixed;
                    });
                    self.renderChart();
                } else {
                    hideChart(self.chartId, true);
                }
            });

            //列表
            loadingGrid(self.gridId, true);
            $.get(urls.getSalarySubOrgFixedList, {organId: reqOrgId}, function (data) {
                loadingGrid(self.gridId, false);
                data = _.sortBy(data, function (m) {
                    return -m.fixed;
                });
                if (data.length > 0) {
                    var lists = [];
                    $.each(data, function (i, item) {
                        lists.push({
                            org: item.organizationName,
                            rate: Tc.formatFloat(item.fixed) + ":" + Tc.formatFloat(item.flot),
                            fixed: Tc.formatFloat(item.fixedSalary),
                            float: Tc.formatFloat(item.flotSalary)
                        });
                    });
                    self.dataGrid = lists;
                    self.renderGrid();
                } else {
                    hideGrid(self.gridId);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.dataChart;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            //计算
            var bargreen = [], barred = [], barName = [];
            $.each(data, function (i, item) {
                barName.push(item.organizationName);
                bargreen.push(Tc.formatFloat(item.fixed));
                barred.push(Tc.formatFloat(1 - item.fixed));
            });

            //option
            self.option.xAxis[0].data = barName;
            self.option.series[0].data = bargreen;
            self.option.series[1].data = barred;
            self.option.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        renderGrid: function () {
            var self = this;
            self.jqGrid = $("#" + self.gridId).jqGrid(self.optionjqGrid);
            self.jqGrid.clearGridData().setGridParam({
                data: self.dataGrid
            }).trigger("reloadGrid");

            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqGrid) {
                $("#gbox_" + self.gridId).css({"margin-left": "11px", "margin-bottom": "11px"});
                self.jqGrid.setGridWidth($("#gbox_" + self.gridId).parent().width() - 22);
            }
        },
        resizeChart: function () {
            var self = this;
            self.resizeGrid();
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //年终奖金总额与利润的年度趋势
    var yearEndBonusObj = {
        option: {
            tooltip: {
                show: true,
                trigger: 'axis',
                axisPointer: {
                    type: 'line',
                    lineStyle: {
                        color: '#ccc',
                        width: 1,
                        type: 'solid'
                    }
                }
            },
            legend: {
                data: ["年终奖", "利润"],
                y: "bottom",
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "rgb(51, 51, 51)"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    name: "（万元）",
                    nameTextStyle: {
                        color: "rgb(153, 153, 153)"
                    },
                    splitNumber: 5,
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 0
                        }
                    }
                }
            ],
            series: [
                {
                    name: "年终奖",
                    type: "line",
                    data: [],
                    clickable: false,
                    symbol: "circle",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                },
                {
                    name: "利润",
                    type: "line",
                    data: [],
                    symbol: "circle",
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                }
            ],
            grid: optionGrid,
            color: ["rgb(91, 155, 213)", "rgb(237, 125, 49)"],
            calculable: false
        },
        data: null,
        chart: null,
        optionjqGrid: {
            data: [],
            datatype: "local",
            width: 0,
            height: 305,
            rowNum: 999999,
            colNames: ['年份', '年终奖（万元）', '利润（万元）', '年终奖占利润比'],
            colModel: [
                {name: 'year', index: 'year', sortable: false, align: 'center'},
                {name: 'bonus', index: 'bonus', sortable: false, align: 'center'},
                {name: 'profit', index: 'profit', sortable: false, align: 'center'},
                {name: 'rate', index: 'rate', sortable: false, align: 'center'}
            ]
        },
        dataGrid: null,
        chartId: "yearEndBonusChart",
        gridId: "yearEndBonusList",
        init: function () {
            var self = this;
            //图表
            loadingChart(self.chartId);
            $.get(urls.getSalaryBonusProfit, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.dataChart = data;
                    self.renderChart();
                } else {
                    hideChart(self.chartId, true);
                }
            });

            //列表
            loadingGrid(self.gridId, true);
            $.get(urls.getSalaryBonusProfitList, {organId: reqOrgId}, function (data) {
                loadingGrid(self.gridId, false);
                if (data.length > 0) {
                    var lists = [];
                    $.each(data, function (i, item) {
                        lists.push({
                            year: item.year + '年',
                            bonus: item.bonus == 0 ? '-' : Tc.formatFloat(item.bonus),
                            profit: item.profit == 0 ? '-' : Tc.formatFloat(item.profit),
                            rate: item.profit == 0 ? '-' : (Tc.formatFloat(item.bonusProportion) + '%')
                        });
                    });
                    self.dataGrid = lists;
                    self.renderGrid();
                } else {
                    hideGrid(self.gridId);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.dataChart || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            //计算
            var xaxisData = [], series0 = [], series1 = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.year + '年');
                series0.push(Tc.formatFloat(item.bonus));
                series1.push(Tc.formatFloat(item.profit));
            });
            //option
            self.option.xAxis[0].data = xaxisData;//["2012年", "2013年", "2014年", "2015年", "2016年"];
            self.option.series[0].data = series0; //年终奖
            self.option.series[1].data = series1; //利润

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        renderGrid: function () {
            var self = this;

            self.jqGrid = $("#" + self.gridId).jqGrid(self.optionjqGrid);
            self.jqGrid.clearGridData();
            if (self.dataGrid && self.dataGrid.length > 0) {
                self.jqGrid.setGridParam({
                    data: self.dataGrid
                }).trigger("reloadGrid");
            }

            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqGrid) {
                $("#gbox_" + self.gridId).css({"margin-left": "11px"});
                self.jqGrid.setGridWidth($("#gbox_" + self.gridId).parent().width() - 22);
            }
        },
        resizeChart: function () {
            var self = this;
            self.renderGrid();
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    /*
     *************************************************************
     * 页签三开始
     * ***********************************************************
     * */

    //福利费用统计
    var getSalaryWelfareObj = {
        init: function () {
            var self = this;
            self.render('...', '...');
            $.get(urls.getSalaryWelfare, {organId: reqOrgId}, function (data) {
                if (data) {
                    self.render(Tc.formatFloat(data.wages), Tc.formatFloat(data.wageShare));
                } else {
                    self.render('-', '-');
                }
            });
        },
        render: function (wage, rate) {
            $("#salaryWelfareTotal").text(wage);
            $("#salaryWelfareRate").text(rate);
        }
    };

    //福利费用总额月度趋势
    var welfareTotalMonthObj = {
        option: {
            tooltip: {
                show: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        length: 3,
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    name: "（万元）",
                    nameLocation: "end"
                }
            ],
            series: [
                {
                    name: "行业分位值",
                    type: "line",
                    clickable: false,
                    data: [],
                    markPoint: {
                        itemStyle: {
                            normal: {
                                color: '#333',
                                borderWidth: 30
                            },
                            emphasis: {
                                color: 'rgba(0,0,0,0)'
                            }
                        },
                        clickable: false,
                        data: []
                    },
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: function (a) {
                                    return a.name == welfareTotalMonthObj.name ? '' : a.value;
                                }
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    symbolSize: 0
                }
            ],
            grid: optionGrid,
            dataZoom: dataZoom
        },
        name: null,
        data: null,
        chart: null,
        chartId: "welfareTotalMonthChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryWelfareMonth, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var xaxisData = [], seriesData = [], name = '', value = '';
            $.each(data, function (i, item) {
                xaxisData.push(item.yearMonth);
                seriesData.push(Tc.formatFloat(item.sumWelfare));
                if (i == data.length - 1) {
                    name = item.yearMonth;
                    value = Tc.formatFloat(item.sumWelfare);
                }
            });
            //option
            self.option.xAxis[0].data = xaxisData;//["16/01", "16/02", "16/03", "16/04", "16/05"];
            self.option.series[0].data = seriesData;//福利费用
            self.option.series[0].markPoint.data = [
                {
                    name: '标注',
                    value: value,
                    xAxis: name,
                    yAxis: value
                }
            ];
            self.name = name;
            if (data.length > 6) {
                self.option.dataZoom = dataZoom;
            } else {
                self.option.dataZoom = null;
            }

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //福利费用及占薪酬比年度趋势
    var welfareTotalRateObj = {
        option: {
            tooltip: {
                show: false
            },
            legend: {
                data: ["工资总额", "占薪酬总额比"],
                y: "bottom",
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
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
                    min: 0,
                    max: 0,
                    name: "（万元）",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
                        }
                    }
                },
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    min: 0,
                    max: 100,
                    axisLabel: {
                        show: true,
                        formatter: '{value}%'
                    }
                }
            ],
            series: [
                {
                    name: "工资总额",
                    type: "bar",
                    yAxisIndex: 0,
                    clickable: false,
                    barMaxWidth: 50,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barGap: 0
                },
                {
                    type: "line",
                    name: "占薪酬总额比",
                    yAxisIndex: 1,
                    clickable: false,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: "{c}%"
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                }
            ],
            grid: $.extend({}, optionGrid, {x2: 50}),
            color: ["#0b98e0", "rgb(255, 192, 0)"]
        },
        data: null,
        chart: null,
        chartId: "welfareTotalRateChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryWelfareYear, {organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var years = [], min = 0, max = 0, series0 = [], series1 = [], all = [];
            $.each(data, function (i, item) {
                years.push(item.year + '年');
                series0.push(Tc.formatFloat(item.sumWelfare));
                series1.push(Tc.formatFloat(item.welfareShare * 100));

                all.push(Tc.formatFloat(item.sumWelfare));
            });
            min = _.min(all);
            min = parseInt(min / 100) * 100;
            max = _.max(all);
            max = (parseInt(max / 100) + 1) * 100;
            //option
            self.option.xAxis[0].data = years;//["2012年", "2013年", "2014年", "2015年", "2016年"];
            self.option.yAxis[0].min = min;
            self.option.yAxis[0].max = max;
            self.option.series[0].data = series0;//福利费用
            self.option.series[1].data = series1;//占薪酬总额比

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //下级组织福利费用总额对比
    var welfareSubOrgTotalObj = {
        option: Tc.cloneObj(commonOption.Total),
        data: null,
        chart: null,
        chartId: "welfareSubOrgTotalChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalarySubOrgWelfare, {organId: reqOrgId}, function (data) {
                if (data && data.beginDate && data.endDate)
                    $("#" + self.chartId + "Date").text("（" + data.beginDate + " - " + data.endDate + "）");

                if (data && data.list && data.list.length > 0) {
                    self.data = data.list;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var xaxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.organizationName);
                seriesData.push(Tc.formatFloat(item.sumWelfare));
            });
            //option
            self.option.xAxis[0].data = xaxisData;//["深圳分公司", "上海分公司", "广州分公司", "北京分公司", "北京总部"];
            self.option.series[0].data = seriesData;//福利费用总额对比
            self.option.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    };

    //下级组织平均福利费用对比
    var welfareSubOrgAvgObj = {
        option: {
            tooltip: {
                show: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        lineStyle: {
                            width: 1,
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        itemStyle: {
                            color: '#333'
                        },
                        textStyle: {
                            color: '#333',
                            fontSize: 12,
                            fontFamily: "'微软雅黑', 'verdana'"
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 0,
                            color: "#999"
                        }
                    },
                    name: "（万元）"
                }
            ],
            series: [
                {
                    name: "薪酬福利费",
                    type: "bar",
                    data: [],
                    clickable: false,
                    barMaxWidth: 40,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                }
            ],
            color: ["#0b98e0"],
            grid: optionGrid,
            dataZoom: {}
        },
        data: null,
        chart: null,
        chartId: "welfareSubOrgAvgChart",
        init: function () {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalarySubOrgAvgWelfare, {organId: reqOrgId}, function (data) {
                if (data && data.beginDate && data.endDate)
                    $("#" + self.chartId + "Date").text("（" + data.beginDate + " - " + data.endDate + "）");

                if (data && data.list && data.list.length > 0) {
                    self.data = data.list;
                    self.render();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            var xaxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.organizationName);
                seriesData.push(Tc.formatFloat(item.avgWelfare));
            });
            //option
            self.option.xAxis[0].data = xaxisData;//["广州分公司", "上海分公司", "深圳分公司", "北京分公司", "北京总部"];
            self.option.series[0].data = seriesData;//平均福利费用对比
            self.option.dataZoom = data.length > showDataZoomNumber ? dataZoom : null;

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.chart.resize();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }


    /***
     * 国家固定福利统计
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}, formatter: peopleCompareOption.tooltip.formatter}, toolbox: {show: boolean, feature: {mark: {show: boolean}, dataView: {show: boolean, readOnly: boolean}, magicType: {show: boolean, type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}}, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {borderColor: string, x: number, y: number, x2: number, borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, height: number, start: number, end: number, showDetail: boolean}}}
     */
    insureGridHeight = 262;
    var insureGridOption = {
        url: urls.findSalaryBenefitsDetailedUrl,
        datatype: "json",
        mtype: 'POST',
        altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 268,//268
        colNames: ['员工姓名', '福利类别', '缴费时间', '缴费金额'],
        colModel: [
            {name: 'empName', width: 100, sortable: false, align: 'center'},
            {name: 'welfareName', width: 100, sortable: false, align: 'center'},
            {name: 'payDate', width: 100, fixed: true, sortable: false, align: 'left'},
            {name: 'payAmount', width: 60, fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 55,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#insurePager",
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#insureTable").find(".ui-jqgrid-bdiv").height(insureGridHeight);
        }
    };
    var insureObj = {
        gridId: '#insureList',
        chartId: 'insureChart',
        typeId: 'insureType',
        type: 'nfb',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.organId != organId) {
                self.requestChart(organId);
            }
            self.resizeChart();
            self.initGrid(organId);
        },
        requestChart: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryFixedBenefitsUrl, {organId: orgId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = orgId;
                    self.render(result);
                } else {
                    self.chartObj = null;
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (data) {
            var self = this;
            var category = [], welfareArr = [], rateArr = [];

            _.each(data, function (item) {
                category.push(item.welfareName);
                welfareArr.push(item.welfare);
                rateArr.push(Tc.formatFloat(item.coverageRate) + '%');
            });
            if (category.length > 0) {
                var legend = ['企业缴费(万元)', '覆盖率'];
                self.chartObj = {
                    legend: legend,
                    category: category,
                    welfareArr: welfareArr,
                    rateArr: rateArr
                };
                $('#' + self.chartId).html(renderBarGrid(legend, category, welfareArr, rateArr));
            } else {
                self.chartObj = null;
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                var obj = self.chartObj;
                $('#' + self.chartId).html(renderBarGrid(obj.legend, obj.category, obj.welfareArr, obj.rateArr));
            }
        },
        initAndRenderType: function () {
            var self = this;
            var $type = $('#' + self.typeId);
            $.get(urls.getSalaryWelfareCategoryUrl, {'welfareType': self.type}, function (rs) {
                if (_.isEmpty(rs)) {
                    $type.html('');
                    return false;
                }
                var html = '<option value="">全部</option>';
                _.each(rs, function (item) {
                    html += '<option value="' + item.welfareKey + '">' + item.welfareName + '</option>';
                });
                $type.html(html);
            });
            $('#insureTxt').val('');
            $('#insureSearchBtn').click(function () {
                var insureTxt = $('#insureTxt').val();
                var selectVal = $('#insureType').val();
                insureObj.resizeGrid(insureObj.gridOrganId, insureTxt, selectVal);
            });
        },
        initGrid: function (organId) {
            var self = this;
            if (!_.isUndefined(self.gridOrganId) && self.gridOrganId != organId) {
                var insureTxt = $('#insureTxt').val();
                var selectVal = $('#insureType').val();
                self.resizeGrid(organId, insureTxt, selectVal);
                return true;
            }
            self.initAndRenderType();

            insureGridOption.postData = {'organId': organId};
            $(self.gridId).jqGrid(insureGridOption);
            self.gridOrganId = organId;
        },
        resizeGrid: function (organId, keyName, welfareKey) {
            var self = this;
            var params = {
                'organId': organId,
                'keyName': keyName,
                'welfareKey': welfareKey
            };
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            resizeGrid(self.gridId);
            self.gridOrganId = organId;
        }
    };

    /***
     * 企业福利（货币）
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}, formatter: peopleCompareOption.tooltip.formatter}, toolbox: {show: boolean, feature: {mark: {show: boolean}, dataView: {show: boolean, readOnly: boolean}, magicType: {show: boolean, type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}}, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {borderColor: string, x: number, y: number, x2: number, borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, height: number, start: number, end: number, showDetail: boolean}}}
     */
    var insureMoneyGridOption = {
        url: urls.findSalaryCurrencyDetailedUrl,
        datatype: "json",
        mtype: 'POST',
        altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 268,//268
        colNames: ['员工姓名', '福利类别', '发放时间', '发放金额'],
        colModel: [
            {name: 'empName', width: 100, sortable: false, align: 'center'},
            {name: 'welfareName', width: 100, sortable: false, align: 'center'},
            {name: 'grantDate', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'grantAmount', width: 60, fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 55,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#insureMoneyPager",
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#insureMoneyTable").find(".ui-jqgrid-bdiv").height(insureGridHeight);
        }
    };
    var insureMoneyObj = {
        gridId: '#insureMoneyList',
        chartId: 'insureMoneyChart',
        typeId: 'insureMoneyType',
        type: 'cpm',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.organId != organId) {
                self.requestChart(organId);
            }
            self.initGrid(organId);
        },
        requestChart: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryBenefitsCurrencyUrl, {organId: orgId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = orgId;
                    self.render(result);
                } else {
                    self.chartObj = null;
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (data) {
            var self = this;
            var category = [], payArr = [], rateArr = [];
            _.each(data, function (item) {
                category.push(item.welfareName);
                payArr.push(item.welfare);
                rateArr.push(Tc.formatFloat(item.coverageRate) + '%');
            });
            if (category.length > 0) {
                var legend = ['费用(万元)', '覆盖率'];
                self.chartObj = {
                    legend: legend,
                    category: category,
                    payArr: payArr,
                    rateArr: rateArr
                };
                $('#' + self.chartId).html(renderBarGrid(legend, category, payArr, rateArr));
            } else {
                self.chartObj = null;
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                var obj = self.chartObj;
                $('#' + self.chartId).html(renderBarGrid(obj.legend, obj.category, obj.payArr, obj.rateArr));
            }
        },
        initAndRenderType: function () {
            var self = this;
            var $type = $('#' + self.typeId);
            $.get(urls.getSalaryWelfareCategoryUrl, {'welfareType': self.type}, function (rs) {
                if (_.isEmpty(rs)) {
                    $type.html('');
                    return false;
                }
                var html = '<option value="">全部</option>';
                _.each(rs, function (item) {
                    html += '<option value="' + item.welfareKey + '">' + item.welfareName + '</option>';
                });
                $type.html(html);
            });
            $('#insureMoneyTxt').val('');
            $('#insureMoneySearchBtn').click(function () {
                var insureTxt = $('#insureMoneyTxt').val();
                var selectVal = $('#insureMoneyType').val();
                insureMoneyObj.resizeGrid(insureMoneyObj.gridOrganId, insureTxt, selectVal);
            });
        },
        initGrid: function (organId) {
            var self = this;
            if (!_.isUndefined(self.gridOrganId) && self.gridOrganId != organId) {
                var insureTxt = $('#insureMoneyTxt').val();
                var selectVal = $('#insureMoneyType').val();
                self.resizeGrid(organId, insureTxt, selectVal);
                return true;
            }
            self.initAndRenderType();

            insureMoneyGridOption.postData = {'organId': organId};
            $(self.gridId).jqGrid(insureMoneyGridOption);
            self.gridOrganId = organId;
        },
        resizeGrid: function (organId, keyName, welfareKey) {
            var self = this;
            var params = {
                'organId': organId,
                'keyName': keyName,
                'welfareKey': welfareKey
            };
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.gridOrganId = organId;
            resizeGrid(self.gridId);
        }
    };

    /***
     * 企业福利（非货币）
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}, formatter: peopleCompareOption.tooltip.formatter}, toolbox: {show: boolean, feature: {mark: {show: boolean}, dataView: {show: boolean, readOnly: boolean}, magicType: {show: boolean, type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}}, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {borderColor: string, x: number, y: number, x2: number, borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, height: number, start: number, end: number, showDetail: boolean}}}
     */
    var insureNoMoneyGridOption = {
        url: urls.findSalaryNoCurrencyDetailedUrl,
        datatype: "json",
        mtype: 'POST',
        altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 268,//268
        colNames: ['员工姓名', '福利类别', '备注'],
        colModel: [
            {name: 'empName', width: 100, sortable: false, align: 'center'},
            {name: 'welfareName', width: 100, sortable: false, align: 'center'},
            {name: 'holidayTime', width: 100, fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 55,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#insureNoMoneyPager",
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#insureNoMoneyTable").find(".ui-jqgrid-bdiv").height(insureGridHeight);
        }
    };
    var insureNoMoneyObj = {
        gridId: '#insureNoMoneyList',
        chartId: 'insureNoMoneyChart',
        typeId: 'insureNoMoneyType',
        type: 'uncpm',
        chartObj: null,
        init: function (organId) {
            var self = this;
            if (self.organId != organId) {
                self.requestChart(organId);
            }
            self.resizeChart();
            self.initGrid(organId);
        },
        requestChart: function (orgId) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSalaryBenefitsNoCurrencyUrl, {organId: orgId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = orgId;
                    self.render(result);
                } else {
                    self.chartObj = null;
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (data) {
            var self = this;
            var category = [], payArr = [], rateArr = [];
            _.each(data, function (item) {
                category.push(item.welfareName);
                payArr.push(item.welfare);
                rateArr.push(Tc.formatFloat(item.coverageRate) + '%');
            });
            if (category.length > 0) {
                var legend = ['人数', '覆盖率'];
                self.chartObj = {
                    legend: legend,
                    category: category,
                    payArr: payArr,
                    rateArr: rateArr
                };
                $('#' + self.chartId).html(renderBarGrid(legend, category, payArr, rateArr));
            } else {
                self.chartObj = null;
                hideChart(self.chartId, true);
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chartObj != null && $(self.chartId).find('div.loadingmessage').length == 0) {
                var obj = self.chartObj;
                $('#' + self.chartId).html(renderBarGrid(obj.legend, obj.category, obj.payArr, obj.rateArr));
            }
        },
        initAndRenderType: function () {
            var self = this;
            var $type = $('#' + self.typeId);
            $.get(urls.getSalaryWelfareCategoryUrl, {'welfareType': self.type}, function (rs) {
                if (_.isEmpty(rs)) {
                    $type.html('');
                    return false;
                }
                var html = '<option value="">全部</option>';
                _.each(rs, function (item) {
                    html += '<option value="' + item.welfareKey + '">' + item.welfareName + '</option>';
                });
                $type.html(html);
            });
            $('#insureNoMoneyTxt').val('');
            $('#insureNoMoneySearchBtn').click(function () {
                var insureTxt = $('#insureNoMoneyTxt').val();
                var selectVal = $('#insureNoMoneyType').val();
                insureNoMoneyObj.resizeGrid(insureNoMoneyObj.gridOrganId, insureTxt, selectVal);
            });
        },
        initGrid: function (organId) {
            var self = this;
            if (!_.isUndefined(self.gridOrganId) && self.gridOrganId != organId) {
                var insureTxt = $('#insureNoMoneyTxt').val();
                var selectVal = $('#insureNoMoneyType').val();
                self.resizeGrid(organId, insureTxt, selectVal);
                return true;
            }
            self.initAndRenderType();

            insureNoMoneyGridOption.postData = {'organId': organId};
            $(self.gridId).jqGrid(insureNoMoneyGridOption);
            self.gridOrganId = organId;
        },
        resizeGrid: function (organId, keyName, welfareKey) {
            var self = this;
            var params = {
                'organId': organId,
                'keyName': keyName,
                'welfareKey': welfareKey
            };
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            resizeGrid(self.gridId);
            self.gridOrganId = organId;
        }
    };

    /**
     * 福利类型图表展示
     * @param legend 说明
     * @param yAxis y轴数据
     * @param xAxis x轴主数据
     * @param param0  覆盖率数据/占比数据
     * @param param1  覆盖率数据/占比数据
     */
    function renderBarGrid(legend, yAxis, xAxis, param0, param1) {
        //100% - y轴宽度 - 覆盖率数据/占比数据的宽度 = x轴主数据的总宽度
        var w = 82 - 22 * (legend.length - 1);

        var html = '<div class="welfare-main">';
        _.each(legend, function (l, i) {
            if (i == 0) {
                html += '<div class="welfare-row-hred"><span class="welfare-title">&nbsp;</span>';
                html += '<span class="welfare-bar-main" style="width: ' + w + '%;">' + l + '</span>';
            } else {
                html += '<span class="welfare-col">' + l + '</span>';
            }
            if (i == legend.length - 1) {
                html += '</div>';
            }
        });
        var max = _.max(xAxis);
        _.each(yAxis, function (y, e) {
            html += '<div class="welfare-row">';
            var len = y.length;
            if (len > 4) {
                html += '<span class="welfare-title" title="' + y + '">' + (y.substring(0, 3) + '...') + '</span>';
            } else {
                html += '<span class="welfare-title">' + y + '</span>';
            }
            _.each(legend, function (l, i) {
                if (i == 0) {    //x轴主数据
                    var xw = (xAxis[e] / max * 100).toFixed(2);
                    html += '<span class="welfare-bar-main" style="width: ' + w + '%;">';
                    if (xw > 50) {
                        html += '<span class="welfare-bar" style="width: ' + xw + '%;">' + xAxis[e] + '&nbsp;&nbsp;&nbsp;</span>';
                    } else {
                        html += '<span class="welfare-bar" style="width: ' + xw + '%;"></span><span class="welfare-bar-txt">' + xAxis[e] + '</span>';
                    }
                    html += '</span>';
                } else {
                    html += '<span class="welfare-col">' + (i == 1 ? param0[e] : (param1 ? param1[e] : '')) + '</span>';
                }
            });
            html += '</div>';
        });
        html += '</div>';
        return html;
    }


    /*
     *************************************************************
     * 页签四开始
     * ***********************************************************
     * */

    var shareTotalObj = {
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.requestSalaryShares();
        },
        requestSalaryShares: function () {
            var self = this;
            $('#sumShares').text('...');
            $('#empSharesCount').text('...');
            $('#sharesCover').text('...');
            $.get(urls.getSalarySharesUrl, {organId: self.organId}, function (rs) {
                if (!_.isEmpty(rs)) {
                    $('#sumShares').text(rs.sumShares);
                    $('#empSharesCount').text(rs.empSharesCount);
                    $('#sharesCover').text(Tc.formatFloat(rs.sharesCover * 100));
                } else {
                    $('#sumShares').text('-');
                    $('#empSharesCount').text('-');
                    $('#sharesCover').text('-');
                }
            });
        }
    };

    /**
     * 持股员工总数年度趋势
     * @type {{grid: {x: number, y: number, x2: number, borderWidth: number}, xAxis: *[], yAxis: *[], calculable: boolean, series: *[]}}
     */
    var shareTotalYearOption = {
        grid: {
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
                nameTextStyle: {
                    color: '#000000',
                    margin: '100px'
                },
                axisLine: false,
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
                            },
                            formatter: function (i) {
                                return i.name == shareTotalYearObj.year ? '' : i.value;
                            }
                        }
                    }
                },
                symbolSize: 1,
                data: [],
                markPoint: {
                    itemStyle: {
                        normal: {
                            color: '#333',
                            borderWidth: 30
                        }
                    },
                    data: []
                }
            }
        ]
    }
    var shareTotalYearObj = {
        chartId: "shareTotalYearChart",
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
            $.get(urls.getSalaryEmpSharesUrl, {organId: organId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = organId;
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            var dataValue = [], xAxisData = [], pointData = [];
            var max = _.max(result, 'empSharesCount').empSharesCount;

            $.each(result, function (i, o) {
                dataValue.push(o.empSharesCount);
                var year = o.year + '年';
                xAxisData.push(year);
                if (i == result.length - 1) {
                    var yAxis = o.empSharesCount + max / 9;
                    yAxis = yAxis == 0 ? 0.48 : Tc.formatFloat(yAxis);
                    self.year = year;
                    pointData.push({name: '', value: o.empSharesCount, xAxis: year, yAxis: yAxis});
                }
            });
            if (dataValue.length > 0) {
                self.chartObj.clear();
                shareTotalYearOption.xAxis[0].data = xAxisData;
                shareTotalYearOption.series[0].data = dataValue;
                shareTotalYearOption.series[0].markPoint.data = pointData;
                self.chartObj.setOption(shareTotalYearOption, true);
                self.chartObj.resize();
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
     * 持股数量年度趋势
     * @type {{grid: {x: number, y: number, x2: number, borderWidth: number}, xAxis: *[], yAxis: *[], calculable: boolean, series: *[]}}
     */
    var shareTendencyYearOption = {
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
                                return i.name == shareTendencyYearObj.year ? '' : i.value;
                            }
                        }
                    }
                },
                symbolSize: 1,
                data: [],
                markPoint: {
                    itemStyle: {
                        normal: {
                            color: '#333',
                            borderWidth: 30
                        }
                    },
                    data: []
                }
            }
        ]
    }
    var shareTendencyYearObj = {
        chartId: "shareTendencyYearChart",
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
            $.get(urls.getSalarySumSharesUrl, {organId: organId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = organId;
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            var dataValue = [], xAxisData = [], pointData = [];
            var max = _.max(result, 'sumShares').sumShares;
            $.each(result, function (i, o) {
                dataValue.push(o.sumShares);
                var year = o.year + '年';
                xAxisData.push(year);
                if (i == result.length - 1) {
                    var yAxis = o.sumShares + max / 9;
                    yAxis = yAxis == 0 ? 0.48 : Tc.formatFloat(yAxis);
                    self.year = year;
                    pointData.push({name: '', value: o.sumShares, xAxis: year, yAxis: yAxis});
                }
            });
            if (dataValue.length > 0) {
                self.chartObj.clear();
                shareTendencyYearOption.xAxis[0].data = xAxisData;
                shareTendencyYearOption.series[0].data = dataValue;
                shareTendencyYearOption.series[0].markPoint.data = pointData;
                self.chartObj.setOption(shareTendencyYearOption, true);
                self.chartObj.resize();
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
     * 下级组织持股员工数
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}, formatter: peopleCompareOption.tooltip.formatter}, toolbox: {show: boolean, feature: {mark: {show: boolean}, dataView: {show: boolean, readOnly: boolean}, magicType: {show: boolean, type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}}, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {borderColor: string, x: number, y: number, x2: number, borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, height: number, start: number, end: number, showDetail: boolean}}}
     */
    var shareStaffTotalOption = {
        tooltip: {
            trigger: "axis",
            axisPointer: {type: 'none'},
            formatter: function (a, b, c) {
                return a[0].name + "：" + a[0].value;
            }
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
                axisLine: false,
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
                //barMaxWidth: 50
            }
        ],
        color: ["#3285C7"],
        grid: {
            x: 55,
            x2: 15,
            borderWidth: 0
        },
        dataZoom: {}
    };
    var shareStaffTotalObj = {
        chartId: 'shareStaffTotalChart',
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
            $.get(urls.getSalarySubOrgSharesUrl, {organId: organId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = organId;
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var list = _.sortBy(result, function (item) {
                return -item.empSharesCount;
            });
            _.each(list, function (item) {
                xAxisData.push(item.organizationName);
                seriesData.push(item.empSharesCount);
            });
            var len = xAxisData.length;
            if (len > 0) {
                var dataZoom = {};
                if (len > showDataZoomNumber) {
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
                shareStaffTotalOption.dataZoom = dataZoom;
                shareStaffTotalOption.xAxis[0].data = xAxisData;
                shareStaffTotalOption.series[0].data = seriesData;
                self.chartObj.setOption(shareStaffTotalOption, true);
                self.chartObj.resize();
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
    };
    /***
     * 下级组织持股数量
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}, formatter: peopleCompareOption.tooltip.formatter}, toolbox: {show: boolean, feature: {mark: {show: boolean}, dataView: {show: boolean, readOnly: boolean}, magicType: {show: boolean, type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}}, xAxis: *[], yAxis: *[], series: *[], color: string[], grid: {borderColor: string, x: number, y: number, x2: number, borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, height: number, start: number, end: number, showDetail: boolean}}}
     */
    var shareSubOrgTotalOption = {
        tooltip: {
            trigger: "axis",
            axisPointer: {type: 'none'},
            formatter: function (a, b, c) {
                return a[0].name + "：" + a[0].value;
            }
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
                axisLine: false,
                axisLabel: {
                    formatter: "{value}",
                    textStyle: {
                        color: "rgb(0, 0, 0)",
                        fontSize: 13
                    }
                },
                splitLine: false
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
                //barMaxWidth: 50
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
    var shareSubOrgTotalObj = {
        chartId: 'shareSubOrgTotalChart',
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
            $.get(urls.getSalarySubOrgSumSharesUrl, {organId: organId}, function (result) {
                if (!_.isEmpty(result)) {
                    hideChart(self.chartId, false);
                    self.organId = organId;
                    self.render(result);
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        render: function (result) {
            var self = this;
            var xAxisData = [], seriesData = [];
            var list = _.sortBy(result, function (item) {
                return -item.sumShares;
            });
            _.each(list, function (item) {
                xAxisData.push(item.organizationName);
                seriesData.push(item.sumShares);
            });
            var len = xAxisData.length;
            if (len > 0) {
                var dataZoom = {};
                if (len > showDataZoomNumber) {
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
                shareSubOrgTotalOption.dataZoom = dataZoom;
                shareSubOrgTotalOption.xAxis[0].data = xAxisData;
                shareSubOrgTotalOption.series[0].data = seriesData;
                self.chartObj.setOption(shareSubOrgTotalOption, true);
                self.chartObj.resize();
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
    };

    /***
     * 员工股票明细
     * @type {{searchBoxId: string, gridId: string, clearCondBtnId: string, resultData: null, init: runOffDetailObj.init, getRequestData: runOffDetailObj.getRequestData, initData: runOffDetailObj.initData, resizeGrid: runOffDetailObj.resizeGrid}}
     */
    var sharesGridOption = {
        url: urls.findSalaryEmpSharesUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,
        colNames: ['姓名', '当前数量(股)', '授予数量(股)', '授予价(元/股)', '持有期', '最近减持数量', '最近减持时间'],
        colModel: [
            {name: 'empName', width: 100, fixed: true, sortable: false, align: 'center'},
            {name: 'currentShares', width: 100, fixed: true, sortable: false, align: 'center'},
            {name: 'grantShares', width: 100, sortable: false, align: 'center'},
            {name: 'grantPrice', width: 100, sortable: false, align: 'center'},
            {name: 'holdYear', width: 100, fixed: true, sortable: false, align: 'center'},
            {name: 'subtractShares', width: 120, fixed: true, sortable: false, align: 'center'},
            {name: 'subtractDate', width: 120, fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#sharesDetailPager",
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#sharesDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
        }
    };
    var sharesDetailObj = {
        gridId: '#sharesDetailGrid',
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
            sharesGridOption.postData = {organId: organId};
            $(self.gridId).jqGrid(sharesGridOption);
            self.hasInit = true;
            self.organId = organId
        },
        loadComple: function () {
            $("#searchBtn").click(function () {
                var searchTxt = $.trim($("#searchTxt").val());
                if (searchTxt != "") {
                    sharesDetailObj.reloadData(reqOrgId, searchTxt);
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
            self.organId = organId
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#sharesDeailTable').width() * 0.98);
        }
    };


    /**
     * 重新加载表格
     * @param gridId
     */
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

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
        } else {
            $chart.children('div.loadingmessage').remove();
        }
    }

    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
    }

    function loadingGrid(gridId, isloading) {
        if (isloading) {
            if ($("#" + gridId + "Loading").length == 0) {
                $("#" + gridId).before("<div id='" + gridId + "Loading' class='loadingmessage'></div>");
            }
            $("#" + gridId + "Loading").text("数据加载中").show();
            $("#" + gridId + ",#gbox_" + gridId).hide();
        } else {
            $("#" + gridId + "Loading").hide();
            $("#" + gridId + ",#gbox_" + gridId).show();
        }
    }

    function hideGrid(gridId) {
        $("#" + gridId + "Loading").text("暂无数据").show();
        $("#" + gridId + ",#gbox_" + gridId).hide();
    }

    //缩放重置
    $(window).resize(function () {
        $(".leftBody .leftListDiv").each(function () {
            $(this).data("resize", !$(this).hasClass("selectList"));
        });
        chartResize();
    });

    var chartResize = function () {
        var t = pageObj;
        switch ($(".leftBody .selectList").attr("page")) {
            case "page-one":
            {//页签1
                if (t.tabFirstLoaded) {
                    salaryCostChartObj.resizeChart();
                    salarySubOrgObj.resizeChart();
                    salarycompletionYearRateObj.resizeChart();
                    salarycompletionMonthRateObj.resizeChart();
                    salaryBringBackMonthObj.resizeChart();
                    salaryBringBackYearObj.resizeChart();
                    salaryDiffList.resize();
                    salaryCrObj.resizeChart();
                }
                break;
            }
            case "page-two":
            {//页签2
                if (t.tabSecondLoaded) {
                    salaryTotalMonthObj.resizeChart();
                    salaryTotalRateObj.resizeChart();
                    salarySubOrgWageObj.resizeChart();
                    salaryStructureObj.resizeChart();
                    positionSubObj.resizeChart();
                    subOrgObj.resizeChart();
                    yearEndBonusObj.resizeChart();
                }
                break;
            }
            case "page-three":
            {//页签3
                if (t.tabThreeLoaded) {
                    welfareTotalMonthObj.resizeChart();
                    welfareTotalRateObj.resizeChart();
                    welfareSubOrgTotalObj.resizeChart();
                    welfareSubOrgAvgObj.resizeChart();

                    insureObj.resizeChart();
                    insureMoneyObj.resizeChart();
                    insureNoMoneyObj.resizeChart();
                }
                break;
            }
            case "page-four":
            {//页签4
                if (t.tabFourLoaded) {
                    shareTotalYearObj.resizeChart();
                    shareTendencyYearObj.resizeChart();
                    shareStaffTotalObj.resizeChart();
                    shareSubOrgTotalObj.resizeChart();
                    sharesDetailObj.resizeGrid();

                }
                break;
            }
        }
    }

    //初始化页签
    var pageObj = {
        tabName: "page-one",
        tabFirstLoaded: false,
        tabSecondLoaded: false,
        tabThreeLoaded: false,
        tabFourLoaded: false,
        click: function (page) {
            var self = this;
            if (self.tabName == page)return;

            self.tabName = page;
            switch (page) {
                case "page-one":
                {
                    self.initFirstTab();
                    break;
                }
                case "page-two":
                {
                    self.initSecondTab();
                    break;
                }
                case "page-three":
                {
                    self.initThreeTab();
                    break;
                }
                case "page-four":
                {
                    self.initFourTab();
                    break;
                }
            }
            var obj = $(".leftBody div[page='" + page + "']");
            if (obj.data("resize")) {
                obj.data("resize", false);
                chartResize();
            }
        },
        //页签1
        initFirstTab: function () {
            var self = this;
            if (!self.tabFirstLoaded) {
                self.tabFirstLoaded = true;
                salarytotalObj.init();
                salarystractureOjb.init();
                salaryrateOjb.init();
                salaryCostChartObj.init();
                salarySubOrgObj.init();
                salarycompletionYearRateObj.init();
                salarycompletionMonthRateObj.init();
                salaryBringBackMonthObj.init();
                salaryBringBackYearObj.init();
                salaryDiffList.init();
                salaryCrObj.init();
            }
        },
        //页签2
        initSecondTab: function () {
            var self = this;
            if (!self.tabSecondLoaded) {
                self.tabSecondLoaded = true;

                salaryWageStatis.init();
                salaryTotalMonthObj.init();
                salaryTotalRateObj.init();
                salarySubOrgWageObj.init();
                salaryStructureObj.init();
                salaryFixedProportion.init();
                positionSubObj.init();
                subOrgObj.init();
                yearEndBonusObj.init();
            }
        },
        //页签3
        initThreeTab: function () {
            var self = this;
            if (!self.tabThreeLoaded) {
                self.tabThreeLoaded = true;

                getSalaryWelfareObj.init();
                welfareTotalMonthObj.init();
                welfareTotalRateObj.init();
                welfareSubOrgTotalObj.init();
                welfareSubOrgAvgObj.init();

                insureObj.init(reqOrgId);
                insureMoneyObj.init(reqOrgId);
                insureNoMoneyObj.init(reqOrgId);
            }
        },
        //页签4
        initFourTab: function () {
            var self = this;
            if (!self.tabFourLoaded) {
                self.tabFourLoaded = true;

                shareTotalObj.init(reqOrgId);
                shareTotalYearObj.init(reqOrgId);
                shareTendencyYearObj.init(reqOrgId);
                shareStaffTotalObj.init(reqOrgId);
                shareSubOrgTotalObj.init(reqOrgId);
                sharesDetailObj.init(reqOrgId);
            }
        }
    };
    pageObj.initFirstTab();
});