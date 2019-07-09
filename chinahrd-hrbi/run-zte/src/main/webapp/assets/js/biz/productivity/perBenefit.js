require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'bootstrap', 'jgGrid', 'underscore', 'timeLine2', 'utils', 'messenger', 'selection'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getAvgBenefitUrl: webRoot + '/perBenefit/getAvgBenefit.do',     //人均效益
        getPerBenefitResultUrl: webRoot + '/perBenefit/getPerBenefitResult.do',  //万元薪资\人力资金执行率
        getAvgBenefitTrendUrl: webRoot + "/perBenefit/getAvgBenefitTrend.do", // 人均效益趋势
        getOrgBenefitUrl: webRoot + "/perBenefit/getOrgBenefitData.do",         // 下级组织人均效益
        getBenefitPayTrendUrl: webRoot + '/perBenefit/getBenefitPayTrend.do',   //万元薪资趋势
        getSubOrganPayUrl: webRoot + '/perBenefit/getSubOrganPay.do',           //下级组织万元薪资对比
        getExecuteRateTrendUrl: webRoot + '/perBenefit/getExecuteRateTrend.do',   //人力资金执行率趋势
        getSubOrganExecuteRateUrl: webRoot + '/perBenefit/getSubOrganExecuteRate.do',      //下级组织人力资金执行率
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do'			//添加备忘录信息
    }

    $(win.document.getElementById('tree')).next().show();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    $("[data-toggle='tooltip']").tooltip();

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        timeLineObj.init(reqOrgId);
        perBenefitObj.init();
    };

    /**
     * 初始化echart对象
     */
    function initChart(targetId) {
        return echarts.init(document.getElementById(targetId));
    }

    function showErrMsg(content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    }

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        var obj = $(this).parents(".index-common-title").parent();
        if (obj.attr("view") == "chart") {
            obj.find(">.bottom-div>.table-view").show();
            obj.find(">.bottom-div>.chart-view").hide();
            obj.attr("view", "table");
        } else {
            obj.find(">.bottom-div>.chart-view").show();
            obj.find(">.bottom-div>.table-view").hide();
            obj.attr("view", "chart");
        }

        var key = $(this).data("key");
        switch (key) {
            case "profit":
            {
                profitObj.rerendder();
                break;
            }
            case "orgBenefit":
            {
                orgBenefitObj.rerendder();
                break;
            }
        }
    });

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

    function nagativeItem(val) {
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

    function addGoBackBtn(chartId, parentId, fun) {
        var $chart = $("#" + chartId);
        $chart.children('div.goBackBtn').remove();
        var left = $chart.width() / 2 - 35;
        $chart.append('<div class="goBackBtn" data-parent="' + parentId + '" style="left:'
            + left + 'px"><a href="javascript:void(0)">返回上一层</a></div>');

        $chart.children('div.goBackBtn').click(function (e) {
            var $this = $(this);
            var parentId = $this.data('parent');
            if ($.isFunction(fun)) fun(parentId);
        });
    }

    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
    }

    function timeIntervalToStr(minYear, minMonth, maxYear, maxMonth) {
        var maxYearMonth = maxYear + ((maxMonth > 9 ? '' : '0') + maxMonth);
        var minYearMonth = minYear + ((minMonth > 9 ? '' : '0') + minMonth);
        var yearMonths = [minYearMonth, maxYearMonth];
        return yearMonths.join(',');
    }

    /**
     * 人均效益趋势
     */
    var profitObj = {
        dataMonth: null,
        dataYear: null,
        types: ["month", "year"],
        option: {
            legend: {
                data: ['人均效益', '环比变化', '同比变化'],
                y: 'bottom',
                selectedMode: false
            },
            calculable: false,
            grid: {
                borderWidth: 0,
                x: 35,
                x2: 50,
                y2: 70
            },
            color: ['#0b98e0', '#FF5F50', '#8EC15A'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'none'}
            },
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    onZero: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    rotate: 30,
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
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true
                }
            }, {
                type: 'value',
                scale: true,
                splitLine: false,
                splitNumber: 4,
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    formatter: '{value}%'
                }
            }],
            series: [{
                name: '人均效益',
                type: 'bar',
                clickable: false,
                yAxisIndex: 0,
                barCategoryGap: '45%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            position: 'insideBottom'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            }, {
                name: '环比变化',
                type: 'line',
                clickable: false,
                yAxisIndex: 1,
                symbolSize: 2,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return i.value + '%';
                            }
                        }
                    }
                },
                data: []
            },
                {
                    name: '同比变化',
                    type: 'line',
                    clickable: false,
                    yAxisIndex: 1,
                    symbolSize: 2,
                    symbol: 'circle',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                },
                                formatter: function (i) {
                                    return i.value + '%';
                                }
                            }
                        }
                    },
                    data: []
                }]
        },
        optionGrid: {
            data: [],
            datatype: "local",
            altRows: true,//设置表格行的不同底色
            autowidth: true,
            height: 300,//268
            colNames: ['月份', '人均效益（万元）', '环比变化', '同比变化'],
            colModel: [
                {
                    name: 'yearMonth', sortable: false, align: 'center',
                    formatter: function (value) {
                        if (value.length == 4) {
                            return value;
                        }
                        value += '';
                        return value.substring(2, 4) + "/" + value.substring(4);
                    }
                },
                {
                    name: 'benefit', sortable: false, align: 'center',
                    formatter: function (value) {
                        return value == "-" ? value : Tc.formatFloat(value);
                    }
                },
                {
                    name: 'chainRate', sortable: false, align: 'center',
                    formatter: function (value) {
                        return value == "-" ? value : Tc.formatFloat(value * 100) + "%";
                    }
                },
                {
                    name: 'yoyRate', sortable: false, align: 'center',
                    formatter: function (value) {
                        return value == "-" ? value : Tc.formatFloat(value * 100) + "%";
                    }
                }
            ],
            rownumbers: true,
            scroll: true
        },
        chartObj: null,
        gridObj: null,
        chartId: "profitChart",
        gridId: "profitGrid",
        init: function () {
            var self = this;

            self.dynamicRows = 1;
            var maxYear = perBenefitObj.maxYear;
            var maxMonth = perBenefitObj.maxMonth;
            var minYear = perBenefitObj.minYear;
            var minMonth = perBenefitObj.minMonth;
            console.log(minYear, minMonth, maxYear, maxMonth);
            $("#profitDate").selection({
                dateType: 2,
                dateRange: {
                    min: perBenefitObj.minDate,
                    max: perBenefitObj.maxDate
                },
                dateSelected: [minYear, minMonth, maxYear, maxMonth],
                dateSelectedLength: 6,
                dynamicRows: [{name: '类别', selected: self.dynamicRows, list: [{k: '按收入', v: '1'}, {k: '按毛利', v: '0'}]}],
                ok: function (event, data) {
                    self.dynamicRows = data.dynamicRows[0];
                    var dates = data.date;
                    self.request(timeIntervalToStr(dates[0], dates[1], dates[2], dates[3]));
                }
            });
            self.initGrid();
            self.request(timeIntervalToStr(minYear, minMonth, maxYear, maxMonth));
        },
        initGrid: function () {
            var self = this;
            if (!self.gridObj) {
                self.gridObj = $("#" + self.gridId).jqGrid(self.optionGrid);
            }
        },
        request: function (yearMonthStr) {
            var self = this;
            var type = self.dynamicRows;
            if (!yearMonthStr) {
                yearMonthStr = self.yearMonthStr;
            }
            loadingChart(self.chartId);
            $.get(urls.getAvgBenefitTrendUrl, {
                organId: reqOrgId,
                type: type,
                yearMonthStr: yearMonthStr
            }, function (data) {
                if (!_.isEmpty(data)) {
                    hideChart(self.chartId, false);
                    self.data = _.sortBy(data, 'yearMonth');
                    self.renderChart();
                    self.renderGrid();
                    self.yearMonthStr = yearMonthStr;
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.data;

            var option = self.getChartOption(data);

            if (self.chartObj) {
                self.chartObj.clear();
            }
            self.chartObj = initChart(self.chartId);
            self.chartObj.setOption(option, true);
            self.chartObj.resize();
        },
        renderGrid: function () {
            var self = this;
            var trendData = self.data;

            trendData = _.sortBy(trendData, function (item) {
                return -item.yearMonth;
            });

            self.gridObj.clearGridData().setGridParam({
                data: trendData
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        getChartOption: function (trendData) {
            var self = this;
            var xAxisData = [], benefitData = [], chainData = [], yoyData = [];

            $.each(trendData, function (i, o) {
                var ym = o.yearMonth + '';
                var yearMonthStr = ym.substr(2, 2) + '/' + ym.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefit == null) {
                    o.benefit = 0;
                }
                benefitData.push(Tc.formatFloat(o.benefit));
                var chainRate = Tc.formatFloat(o.chainRate * 100);
                chainData.push(chainRate >= 0 ? chainRate : nagativeItem(chainRate));
                var yoyRate = Tc.formatFloat(o.yoyRate * 100);
                yoyData.push(yoyRate >= 0 ? yoyRate : nagativeItem(yoyRate));
            });

            var option = $.extend(true, {}, self.option);
            option.tooltip.formatter = function (params) {
                var html = "<div>" + params[0].name + "<br>人均效益：" + params[0].value + "万元<br>环比变化：" + params[1].value + "%<br>同比变化：" + params[2].value + "%<div>";
                return html;
            };

            option.xAxis[0].data = xAxisData;
            option.series[0].data = benefitData;
            option.series[1].data = chainData;
            option.series[2].data = yoyData;

            return option;
        },
        rerendder: function () {
            var self = this;
            if ($("#profitChart:visible").length > 0) {
                self.renderChart();
            } else if ($("#profitGrid:visible").length > 0) {
                self.renderGrid();
            }
        },
        resize: function () {
            var self = this;
            if ($("#profitChart:visible").length > 0) {
                self.chartObj.resize();
            } else if ($("#profitGrid:visible").length > 0) {
                self.resizeGrid();
            }
        },
        resizeGrid: function () {
            var self = this;
            if (self.gridObj) {
                self.gridObj.setGridWidth($("#gbox_" + self.gridId).parent().width());
            }
        }
    }

    /**
     * 下级组织人均效益
     * @type {{optionChart: {grid: {borderWidth: number, x: number, y: number, x2: number, y2: number}, color: string[], xAxis: *[], yAxis: *[], series: *[]}, optionGrid: {data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], rownumbers: boolean, rownumWidth: number, scroll: boolean}, data: null, dataText: null, chart: null, chartId: string, grid: null, gridParentId: string, gridId: string, isRenderChart: null, isRenderText: null, init: orgBenefitObj.init, renderChart: orgBenefitObj.renderChart, renderGrid: orgBenefitObj.renderGrid, resizeGrid: orgBenefitObj.resizeGrid, rerendder: orgBenefitObj.rerendder, resize: orgBenefitObj.resize}}
     */
    var orgBenefitObj = {
        optionChart: {
            grid: {
                borderWidth: 0,
                x: 50,
                x2: 30
            },
            color: ['#0b98e0', '#EA711E'],
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    show: true,
                    rotate: 10,
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                        fontFamily: '微软雅黑'
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                        fontFamily: '微软雅黑'
                    }
                }
            }],
            series: [{
                type: 'bar',
                barCategoryGap: '45%',
                barMaxWidth: 43,
                // clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#ffffff'
                            },
                            position: 'insideBottom'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            }]
        },
        optionGrid: {
            data: [],
            datatype: "local",
            altRows: true,
            autowidth: true,
            height: 300,//268
            colNames: ['机构', '人均效益(万元)', '利润(万元)', '销售(万元)', '利润率'],
            colModel: [
                {name: 'organizationName', width: 160, sortable: false},
                {
                    name: 'benefitValue', width: 100, sortable: false, align: 'right',
                    formatter: function (cellvalue, options, rowObject) {
                        return Tc.formatFloat(cellvalue);
                    }
                },
                {name: 'profit', width: 90, sortable: false, align: 'right'},
                {name: 'salesAmount', width: 70, sortable: false, align: 'right'},
                {
                    name: 'profitRate', width: 80, fixed: true, sortable: false, align: 'right',
                    formatter: function (cellvalue, options, rowObject) {
                        return Tc.formatFloat(rowObject.profit / rowObject.salesAmount * 100) + '%';
                    }
                }
            ],
            rownumbers: true,
            rownumWidth: 40,
            scroll: true
        },
        data: null,
        organArr: {},
        dataText: null,
        chartObj: null,
        chartId: "orgBenefitChart",
        gridObj: null,
        gridParentId: "orgBenefit",
        gridId: "orgBenefitGrid",
        init: function (organId) {
            var self = this;
            self.organArr = {};
            self.dynamicRows = 1;
            var maxYear = perBenefitObj.maxYear;
            var maxMonth = perBenefitObj.maxMonth;
            var minYear = perBenefitObj.minYear;
            var minMonth = perBenefitObj.minMonth;

            $("#orgBenefitDate").selection({
                dateType: 2,
                dateRange: {
                    min: perBenefitObj.minDate,
                    max: perBenefitObj.maxDate
                },
                dateSelected: [minYear, minMonth, maxYear, maxMonth],
                dynamicRows: [{name: '类别', selected: self.dynamicRows, list: [{k: '按收入', v: '1'}, {k: '按毛利', v: '0'}]}],
                ok: function (event, data) {
                    self.dynamicRows = data.dynamicRows[0];
                    var dates = data.date;
                    self.request(self.organId, timeIntervalToStr(dates[0], dates[1], dates[2], dates[3]));
                }
            });

            self.request(organId, timeIntervalToStr(minYear, minMonth, maxYear, maxMonth));
        },
        request: function (organId, yearMonthStr) {
            var self = this;
            if (!yearMonthStr) {
                yearMonthStr = self.yearMonthStr;
            }
            var type = self.dynamicRows;
            loadingChart(self.chartId);
            $.get(urls.getOrgBenefitUrl, {
                organizationId: organId,
                type: type,
                yearMonthStr: yearMonthStr
            }, function (data) {
                if (!_.isEmpty(data)) {
                    hideChart(self.chartId, false);
                    $.each(data, function (i, item) {
                        item.benefitValue = Tc.formatFloat(item.benefitValue);
                    });
                    self.data = data;
                    self.renderChart();
                    self.renderGrid();
                    self.organId = organId;
                    self.yearMonthStr = yearMonthStr;
                } else {
                    hideChart(self.chartId, true);
                }
                var parentId = self.organArr[organId];
                if (parentId) {
                    addGoBackBtn(self.chartId, parentId, function (parentId) {
                        self.request(parentId);
                    });
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.data;

            var xAxisData = [];
            // 人均效益
            var benefitData = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.organizationName);
                benefitData.push({value: item.benefitValue, id: item.id, hasChild: item.hasChild});
            });

            if (self.chartObj) {
                self.chartObj.clear();
            }
            self.chartObj = initChart(self.chartId);
            self.chartObj.on('click', function (params) {
                var data = params.data;
                if (!data.hasChild) {
                    showErrMsg('不能再往下钻取了!');
                    return;
                }
                self.organArr[data.id] = self.organId;
                self.request(data.id);
            });
            self.optionChart.xAxis[0].data = xAxisData;
            self.optionChart.series[0].data = benefitData;
            self.chartObj.setOption(self.optionChart);
        },
        renderGrid: function () {
            var self = this;
            var data = self.data;

            if (!self.gridObj) {
                self.gridObj = $("#" + self.gridId).jqGrid(self.optionGrid);
            }
            self.gridObj.clearGridData().setGridParam({
                data: data
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            if (self.gridObj) {
                self.gridObj.setGridWidth($("#" + self.gridParentId).width());
            }
        },
        rerendder: function () {
            var self = this;
            if ($("#orgBenefitChart:visible").length > 0) {
                self.renderChart();
            } else if ($("#orgBenefit:visible").length > 0) {
                self.renderGrid();
            }
        },
        resize: function () {
            var self = this;
            if ($("#orgBenefitChart:visible").length > 0) {
                if (self.chartObj && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                    self.chartObj.resize();
                }
            } else if ($("#orgBenefit:visible").length > 0) {
                self.resizeGrid();
            }
        }
    }


    /**
     * 万元薪资趋势分析
     * @type {{dataMonth: null, dataYear: null, option: {legend: {data: string[], y: string, selectedMode: boolean}, calculable: boolean, grid: {borderWidth: number, x: number, x2: number, y2: number}, color: string[], tooltip: {trigger: string, axisPointer: {type: string}}, xAxis: *[], yAxis: *[], series: *[]}, chartObj: null, chartId: string, init: payObj.init, request: payObj.request, renderChart: payObj.renderChart, getChartOption: payObj.getChartOption, resize: payObj.resize}}
     */
    var payObj = {
        dataMonth: null,
        dataYear: null,
        option: {
            legend: {
                data: ['万元薪资', '环比变化', '同比变化'],
                y: 'bottom',
                selectedMode: false
            },
            calculable: false,
            grid: {
                borderWidth: 0,
                x: 35,
                x2: 50,
                y2: 70
            },
            color: ['#0b98e0', '#FF5F50', '#8EC15A'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'none'}
            },
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    onZero: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    rotate: 30,
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
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true
                }
            }, {
                type: 'value',
                scale: true,
                splitLine: false,
                splitNumber: 4,
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    formatter: '{value}%'
                }
            }],
            series: [{
                name: '万元薪资',
                type: 'bar',
                clickable: false,
                yAxisIndex: 0,
                barCategoryGap: '45%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            position: 'insideBottom'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            }, {
                name: '环比变化',
                type: 'line',
                clickable: false,
                yAxisIndex: 1,
                symbolSize: 2,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return i.value + '%';
                            }
                        }
                    }
                },
                data: []
            },
                {
                    name: '同比变化',
                    type: 'line',
                    clickable: false,
                    yAxisIndex: 1,
                    symbolSize: 2,
                    symbol: 'circle',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                },
                                formatter: function (i) {
                                    return i.value + '%';
                                }
                            }
                        }
                    },
                    data: []
                }]
        },
        chartObj: null,
        chartId: "payChart",
        init: function () {
            var self = this;

            self.dynamicRows = 1;
            var maxYear = perBenefitObj.maxYear;
            var maxMonth = perBenefitObj.maxMonth;
            var minYear = perBenefitObj.minYear;
            var minMonth = perBenefitObj.minMonth;

            $("#payDate").selection({
                dateType: 2,
                dateRange: {
                    min: perBenefitObj.minDate,
                    max: perBenefitObj.maxDate
                },
                dateSelectedLength: 6,
                dateSelected: [minYear, minMonth, maxYear, maxMonth],
                dynamicRows: [{name: '类别', selected: self.dynamicRows, list: [{k: '按收入', v: '1'}, {k: '按毛利', v: '0'}]}],
                ok: function (event, data) {
                    self.dynamicRows = data.dynamicRows[0];
                    var dates = data.date;
                    self.request(timeIntervalToStr(dates[0], dates[1], dates[2], dates[3]));
                }
            });
            self.request(timeIntervalToStr(minYear, minMonth, maxYear, maxMonth));
        },
        request: function (yearMonthStr) {
            var self = this;
            if (!yearMonthStr) {
                yearMonthStr = self.yearMonthStr;
            }
            var type = self.dynamicRows;
            loadingChart(self.chartId);
            $.get(urls.getBenefitPayTrendUrl, {
                organId: reqOrgId,
                type: type,
                yearMonthStr: yearMonthStr
            }, function (data) {
                if (!_.isEmpty(data)) {
                    hideChart(self.chartId, false);
                    self.data = _.sortBy(data, 'yearMonth');
                    self.renderChart();
                    self.yearMonthStr = yearMonthStr;
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.data;
            var option = self.getChartOption(data);

            if (self.chartObj) {
                self.chartObj.clear();
            }
            self.chartObj = initChart(self.chartId);
            self.chartObj.setOption(option, true);
            self.chartObj.resize();
        },
        getChartOption: function (trendData) {
            var self = this;
            var xAxisData = [], benefitData = [], chainData = [], yoyData = [];

            $.each(trendData, function (i, o) {
                var ym = o.yearMonth + '';
                var yearMonthStr = ym.substr(2, 2) + '/' + ym.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefit == null) {
                    o.benefit = 0;
                }
                benefitData.push(Tc.formatFloat(o.benefit));
                var chainRate = Tc.formatFloat(o.chainRate * 100);
                chainData.push(chainRate >= 0 ? chainRate : nagativeItem(chainRate));
                var yoyRate = Tc.formatFloat(o.yoyRate * 100);
                yoyData.push(yoyRate >= 0 ? yoyRate : nagativeItem(yoyRate));
            });

            var option = $.extend(true, {}, self.option);
            option.tooltip.formatter = function (params) {
                var html = "<div>" + params[0].name + "<br>万元薪资：" + params[0].value + "万元<br>环比变化：" + params[1].value + "%<br>同比变化：" + params[2].value + "%<div>";
                return html;
            };

            option.xAxis[0].data = xAxisData;
            option.series[0].data = benefitData;
            option.series[1].data = chainData;
            option.series[2].data = yoyData;

            return option;
        },
        resize: function () {
            var self = this;
            if (self.chartObj && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /**
     * 下级组织万元薪资
     * @type {{optionChart: {grid: {borderWidth: number, x: number, y: number, x2: number, y2: number}, color: string[], xAxis: *[], yAxis: *[], series: *[]}, optionGrid: {data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], rownumbers: boolean, rownumWidth: number, scroll: boolean}, data: null, dataText: null, chart: null, chartId: string, grid: null, gridParentId: string, gridId: string, isRenderChart: null, isRenderText: null, init: orgBenefitObj.init, renderChart: orgBenefitObj.renderChart, renderGrid: orgBenefitObj.renderGrid, resizeGrid: orgBenefitObj.resizeGrid, rerendder: orgBenefitObj.rerendder, resize: orgBenefitObj.resize}}
     */
    var subOrganPayObj = {
        optionChart: {
            grid: {
                borderWidth: 0,
                x: 50,
                x2: 30
            },
            color: ['#0b98e0', '#EA711E'],
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    show: true,
                    rotate: 10,
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                        fontFamily: '微软雅黑'
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                        fontFamily: '微软雅黑'
                    }
                }
            }],
            series: [{
                type: 'bar',
                barCategoryGap: '45%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#ffffff'
                            },
                            position: 'insideBottom'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            }]
        },
        organArr: {},
        chartObj: null,
        chartId: "paySubOrganChart",
        init: function (organId) {
            var self = this;
            self.organArr = {};
            self.dynamicRows = 1;
            var maxYear = perBenefitObj.maxYear;
            var maxMonth = perBenefitObj.maxMonth;
            var minYear = perBenefitObj.minYear;
            var minMonth = perBenefitObj.minMonth;

            $("#paySubOrganDate").selection({
                dateType: 2,
                dateRange: {
                    min: perBenefitObj.minDate,
                    max: perBenefitObj.maxDate
                },
                dateSelected: [minYear, minMonth, maxYear, maxMonth],
                dynamicRows: [{name: '类别', selected: self.dynamicRows, list: [{k: '按收入', v: '1'}, {k: '按毛利', v: '0'}]}],
                ok: function (event, data) {
                    self.dynamicRows = data.dynamicRows[0];
                    var dates = data.date;
                    self.request(self.organId, timeIntervalToStr(dates[0], dates[1], dates[2], dates[3]));
                }
            });
            self.request(organId, timeIntervalToStr(minYear, minMonth, maxYear, maxMonth));
        },
        request: function (organId, yearMonthStr) {
            var self = this;
            if (!yearMonthStr) {
                yearMonthStr = self.yearMonthStr;
            }
            loadingChart(self.chartId);
            var type = self.dynamicRows;
            $.get(urls.getSubOrganPayUrl, {organId: organId, type: type, yearMonthStr: yearMonthStr}, function (data) {
                if (!_.isEmpty(data)) {
                    hideChart(self.chartId, false);
                    self.renderChart(data, type);
                    self.yearMonthStr = yearMonthStr;
                    self.organId = organId;
                } else {
                    hideChart(self.chartId, true);
                }
                var parentId = self.organArr[organId];
                if (parentId) {
                    addGoBackBtn(self.chartId, parentId, function (parentId) {
                        self.request(parentId);
                    });
                }
            });
        },
        renderChart: function (data, type) {
            var self = this;
            var xAxisData = [];
            var benefitData = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.organizationName);
                benefitData.push({value: Tc.formatFloat(item.rangePer), id: item.organId, hasChild: item.hasChild});
            });

            if (self.chartObj) {
                self.chartObj.clear();
            }
            self.chartObj = initChart(self.chartId);
            self.chartObj.on('click', function (params) {
                var data = params.data;
                if (!data.hasChild) {
                    showErrMsg('不能再往下钻取了!');
                    return;
                }
                self.organArr[data.id] = self.organId;
                self.request(data.id);
            });
            self.optionChart.xAxis[0].data = xAxisData;
            self.optionChart.series[0].data = benefitData;
            self.chartObj.setOption(self.optionChart);
        },
        resize: function () {
            var self = this;
            if (self.chartObj && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }


    /**
     * 人力资金执行率
     * @type {{dataMonth: null, dataYear: null, option: {legend: {data: string[], y: string, selectedMode: boolean}, calculable: boolean, grid: {borderWidth: number, x: number, x2: number, y2: number}, color: string[], tooltip: {trigger: string, axisPointer: {type: string}}, xAxis: *[], yAxis: *[], series: *[]}, chartObj: null, chartId: string, init: executeRateObj.init, request: executeRateObj.request, renderChart: executeRateObj.renderChart, getChartOption: executeRateObj.getChartOption, resize: executeRateObj.resize}}
     */
    var executeRateObj = {
        dataMonth: null,
        dataYear: null,
        option: {
            legend: {
                data: ['人力资金执行率', '环比变化', '同比变化'],
                y: 'bottom',
                selectedMode: false
            },
            calculable: false,
            grid: {
                borderWidth: 0,
                x: 35,
                x2: 50,
                y2: 70
            },
            color: ['#0b98e0', '#FF5F50', '#8EC15A'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'none'}
            },
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    onZero: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    rotate: 30,
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
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true
                }
            }, {
                type: 'value',
                scale: true,
                splitLine: false,
                splitNumber: 4,
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    formatter: '{value}%'
                }
            }],
            series: [{
                name: '人力资金执行率',
                type: 'bar',
                clickable: false,
                yAxisIndex: 0,
                barCategoryGap: '45%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return i.value + '%';
                            },
                            position: 'insideBottom'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            }, {
                name: '环比变化',
                type: 'line',
                clickable: false,
                yAxisIndex: 1,
                symbolSize: 2,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return i.value + '%';
                            }
                        }
                    }
                },
                data: []
            },
                {
                    name: '同比变化',
                    type: 'line',
                    clickable: false,
                    yAxisIndex: 1,
                    symbolSize: 2,
                    symbol: 'circle',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: 'black'
                                },
                                formatter: function (i) {
                                    return i.value + '%';
                                }
                            }
                        }
                    },
                    data: []
                }]
        },
        chartObj: null,
        chartId: "executeRateChart",
        init: function () {
            var self = this;

            var maxYear = perBenefitObj.maxYear;
            var maxMonth = perBenefitObj.maxMonth;
            var minYear = perBenefitObj.minYear;
            var minMonth = perBenefitObj.minMonth;

            $("#executeRateDate").selection({
                dateType: 2,
                dateRange: {
                    min: perBenefitObj.minDate,
                    max: perBenefitObj.maxDate
                },
                dateSelectedLength: 6,
                dateSelected: [minYear, minMonth, maxYear, maxMonth],
                ok: function (event, data) {
                    var dates = data.date;
                    self.request(timeIntervalToStr(dates[0], dates[1], dates[2], dates[3]));
                }
            });
            self.request(timeIntervalToStr(minYear, minMonth, maxYear, maxMonth));
        },
        request: function (yearMonthStr) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getExecuteRateTrendUrl, {organId: reqOrgId, yearMonthStr: yearMonthStr}, function (data) {
                if (!_.isEmpty(data)) {
                    hideChart(self.chartId, false);
                    self.data = _.sortBy(data, 'yearMonth');
                    self.renderChart();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.data;
            var option = self.getChartOption(data);

            if (self.chartObj) {
                self.chartObj.clear();
            }
            self.chartObj = initChart(self.chartId);
            self.chartObj.setOption(option, true);
            self.chartObj.resize();
        },
        getChartOption: function (trendData) {
            var self = this;
            var xAxisData = [], benefitData = [], chainData = [], yoyData = [];

            $.each(trendData, function (i, o) {
                var ym = o.yearMonth + '';
                var yearMonthStr = ym.substr(2, 2) + '/' + ym.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefit == null) {
                    o.benefit = 0;
                }
                benefitData.push(Tc.formatFloat(o.benefit * 100));
                var chainRate = Tc.formatFloat(o.chainRate * 100);
                chainData.push(chainRate >= 0 ? chainRate : nagativeItem(chainRate));
                var yoyRate = Tc.formatFloat(o.yoyRate * 100);
                yoyData.push(yoyRate >= 0 ? yoyRate : nagativeItem(yoyRate));
            });

            var option = $.extend(true, {}, self.option);
            option.tooltip.formatter = function (params) {
                var html = "<div>" + params[0].name + "<br>人力资金执行率：" + params[0].value + "%<br>环比变化：" + params[1].value + "%<br>同比变化：" + params[2].value + "%<div>";
                return html;
            };
            option.xAxis[0].data = xAxisData;
            option.series[0].data = benefitData;
            option.series[1].data = chainData;
            option.series[2].data = yoyData;

            return option;
        },
        resize: function () {
            var self = this;
            if (self.chartObj != null && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }

    /***
     * 下级组织人力资金执行率
     * @type {{optionChart: {grid: {borderWidth: number, x: number, x2: number, y2: number}, color: string[], xAxis: *[], yAxis: *[], series: *[]}, chartObj: null, chartId: string, init: subOrganExecuteRateObj.init, renderChart: subOrganExecuteRateObj.renderChart, resize: subOrganExecuteRateObj.resize}}
     */
    var subOrganExecuteRateObj = {
        optionChart: {
            grid: {
                borderWidth: 0,
                x: 50,
                x2: 30
            },
            color: ['#0b98e0', '#EA711E'],
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    show: true,
                    rotate: 10,
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                        fontFamily: '微软雅黑'
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: false,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#333'
                    },
                    textStyle: {
                        color: '#333',
                        fontSize: 12,
                        fontFamily: '微软雅黑'
                    }
                }
            }],
            series: [{
                type: 'bar',
                barCategoryGap: '45%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#ffffff'
                            },
                            position: 'insideBottom',
                            formatter: function (i) {
                                return i.value + '%';
                            }
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: []
            }]
        },
        organArr: {},
        chartObj: null,
        chartId: "executeRateSubOrganChart",
        init: function (organId) {
            var self = this;
            self.organArr = {};
            var maxYear = perBenefitObj.maxYear;
            var maxMonth = perBenefitObj.maxMonth;
            var minYear = perBenefitObj.minYear;
            var minMonth = perBenefitObj.minMonth;

            $("#executeRateSubOrganDate").selection({
                dateType: 2,
                dateRange: {
                    min: perBenefitObj.minDate,
                    max: perBenefitObj.maxDate
                },
                dateSelected: [minYear, minMonth, maxYear, maxMonth],
                ok: function (event, data) {
                    var dates = data.date;
                    self.request(self.organId, timeIntervalToStr(dates[0], dates[1], dates[2], dates[3]));
                }
            });
            self.request(organId, timeIntervalToStr(minYear, minMonth, maxYear, maxMonth));
        },
        request: function (organId, yearMonthStr) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getSubOrganExecuteRateUrl, {organId: organId, yearMonthStr: yearMonthStr}, function (data) {
                if (!_.isEmpty(data)) {
                    hideChart(self.chartId, false);
                    self.renderChart(data);
                    self.organId = organId;
                } else {
                    hideChart(self.chartId, true);
                }
                var parentId = self.organArr[organId];
                if (parentId) {
                    addGoBackBtn(self.chartId, parentId, function (parentId) {
                        self.request(parentId);
                    });
                }
            });
        },
        renderChart: function (data) {
            var self = this;
            var xAxisData = [];
            var benefitData = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.organizationName);
                benefitData.push({
                    value: Tc.formatFloat(item.rangePer * 100),
                    id: item.organId,
                    hasChild: item.hasChild
                });
            });

            if (self.chartObj) {
                self.chartObj.clear();
            }
            self.chartObj = initChart(self.chartId);
            self.chartObj.on('click', function (params) {
                var data = params.data;
                if (!data.hasChild) {
                    showErrMsg('不能再往下钻取了!');
                    return;
                }
                self.organArr[data.id] = self.organId;
                self.request(data.id);
            });
            self.optionChart.xAxis[0].data = xAxisData;
            self.optionChart.series[0].data = benefitData;
            self.chartObj.setOption(self.optionChart);
        },
        resize: function () {
            var self = this;
            if (self.chartObj && $('#' + self.chartId).find('div.loadingmessage').length == 0) {
                self.chartObj.resize();
            }
        }
    }


    //人均效益、万元薪资、人力资金执行率
    var perBenefitObj = {
        data: null,
        isDialogInit: null,
        barColorArr: ['#23C6C8', '#EA711E'],
        init: function () {
            var self = this;
            var organId = reqOrgId;
            self.render();
            self.requestData(organId);
        },
        render: function () {
            var self = this;
            var _floatVal = $('#benefitsArea .accord-yj-float-value');
            var _bottomVal = $('#benefitsArea .accord-bottom-float-value');
            $("#benefitsToolbar span").unbind('click').bind('click', function () {
                var data = self.avgData;
                if (_.isUndefined(data)) {
                    _floatVal.text(0);
                    _bottomVal.text('0%');
                    return;
                }
                var _t = $(this);
                _t.siblings().removeClass('select');
                _t.addClass('select');
                var id = _t.data('id');
                var bool = id == 'income';
                _floatVal.text(Tc.formatFloat(bool ? data.income : data.gain));
                var chainRate = bool ? data.incomeChain : data.gainChain;
                _bottomVal.text(Tc.formatFloat(chainRate * 100) + '%');
                _bottomVal.removeClass('accord-value-normal accord-value-warn');
                if (chainRate > 0) {
                    _bottomVal.addClass('accord-value-normal');
                } else if (chainRate < 0) {
                    _bottomVal.addClass('accord-value-warn');
                }
            });

            $("#payToolbar span").unbind('click').bind('click', function () {
                var data = self.executeData;
                if (_.isUndefined(data)) {
                    $('#payArea .accord-yj-float-value').text(0);
                    $('#payArea .accord-bottom-float-value').text('');
                    return;
                }
                var _t = $(this);
                _t.siblings().removeClass('select');
                _t.addClass('select');
                var id = _t.data('id');
                $('#payArea .accord-yj-float-value').text(Tc.formatFloat(id == 'income' ? data.payIncome : data.payGain));
                $('#payArea .accord-bottom-float-value').text(data.year + '年');
            });
        },
        requestData: function (organId) {
            var self = this;
            $.get(urls.getAvgBenefitUrl, {'organId': organId}, function (rs) {
                if (rs != null) {
                    self.renderTimeInterval(rs.yearMonth);
                    self.avgData = rs;
                    var _obj = $("#benefitsToolbar span.select");
                    if (_obj.length == 0) {
                        _obj = $("#benefitsToolbar span:first");
                    }
                    _obj.click();
                }
            });

            $.get(urls.getPerBenefitResultUrl, {'organId': organId}, function (rs) {
                if (rs != null) {
                    self.executeData = rs;
                    $('#perBenefitsArea .accord-yj-float .accord-yj-float-value').text(Tc.formatFloat(rs.executeRate * 100));
                    $('#perBenefitsArea .accord-bottom-float .accord-bottom-float-value').text(rs.year + '年人力资金执行率');
                    var _obj = $("#payToolbar span.select");
                    if (_obj.length == 0) {
                        _obj = $("#payToolbar span:first");
                    }
                    _obj.click();
                }
            });
        },
        renderTimeInterval: function (time) {
            var self = this;
            var yearMonth = time;
            if (!yearMonth) {
                var currDate = new Date();
                yearMonth = currDate.getFullYear() + '' + ((currDate.getMonth() > 9 ? '' : '0' ) + (currDate.getMonth() + 1));
            }
            var maxDate = self.maxDate = (yearMonth + '').substr(0, 4) + '-' + (yearMonth + '').substr(4, 6);
            var minDate = self.minDate = (yearMonth - 400 + '').substr(0, 4) + '-' + (yearMonth - 400 + '').substr(4, 6);

            var maxDates = maxDate.split('-');
            self.maxYear = maxDates[0];
            self.maxMonth = Number(maxDates[1]);
            self.minYear = maxDates[1] - 5 > 0 ? maxDates[0] : maxDates[0] - 1;
            self.minMonth = maxDates[1] - 5 > 0 ? maxDates[1] - 5 : maxDates[1] - 5 + 12;

            profitObj.init();
            orgBenefitObj.init(reqOrgId);
            payObj.init();
            subOrganPayObj.init(reqOrgId);
            executeRateObj.init();
            subOrganExecuteRateObj.init(reqOrgId);
        }
    };
    perBenefitObj.init();

    //改变窗口大小时,改变图表及表格的大小
    $(window).resize(function () {
        profitObj.resize();
        profitObj.resizeGrid();
        orgBenefitObj.resize();
        orgBenefitObj.renderGrid();
        payObj.resize();
        subOrganPayObj.resize();
        executeRateObj.resize();
        subOrganExecuteRateObj.resize();
    });

});