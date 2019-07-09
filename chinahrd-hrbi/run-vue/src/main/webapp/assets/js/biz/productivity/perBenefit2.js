require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'bootstrap', 'jgGrid', 'underscore', 'timeLine2', 'utils', 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        perBenefitUrl: webRoot + "/perBenefit/getPerBenefitData.do", // 人均效益
        avgValueUrl: webRoot + "/perBenefit/getAvgValueData.do", // 行业均值
        trendUrl: webRoot + "/perBenefit/getTrendData.do", // 人均效益趋势
        orgBenefitUrl: webRoot + "/perBenefit/getOrgBenefitData.do", // 当前组织人均效益
        orgRecentDataUrl: webRoot + "/perBenefit/getOrgRecentData.do", // 当前组织人均效益、利润总额、销售总额数据
        variationRangeUrl: webRoot + "/perBenefit/getVariationRange.do", //人均效益变化幅度
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do'			//添加备忘录信息
    }

    var chartPieColor = ["#0b98e0", "#00bda9", "#4573a7", "#92c3d4", "#de6e1b", "#ff0084", "#af00e1", "#8d55f6", "#6a5888", "#2340f3"];

    $(win.document.getElementById('tree')).next().show();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    $("[data-toggle='tooltip']").tooltip();

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        timeLineObj.init(reqOrgId);
        perBenefitOjb.init();
        profitObj.init();
        orgBenefitObj.init();
    };

    /**
     * 初始化echart对象
     */
    function initChart(targetId) {
        return echarts.init(document.getElementById(targetId));
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

    var messageStr = ["数据加载中", "暂无数据"];

    //计算最近两个值之间的变化率
    var getPerValue = function (recentValue, preValue) {
        if (!preValue) {
            return '-';
        }
        var rs = Tc.formatFloat(((recentValue - preValue) / preValue) * 100);
        var result = '';
        if (rs > 0) {
            result = '<span class="blue">+' + rs + "%</span>";
        } else if (rs < 0) {
            result = '<span class="red">-' + rs + "%</span>";
        } else {
            result = rs + "%";
        }
        return result;
    }

    //人均效益变化幅度增幅的option
    var incChartOption = {
        calculable: false,
        legend: {
            show: true,
            x: 'center',
            y: 'bottom',
            selectedMode: false,
            data: []
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (param) {
                var a = param[0];
                var b = param[1];
                var devSymbol = b[3].rangePer >= 0 ? '+' : '';
                return a[1] + '<br/>' + a[0] + '：' + Tc.formatFloat(a[2]) + '<br/>' + b[0] + '：' + Tc.formatFloat(b[2]) + '<br/>' +
                    '幅度：' + devSymbol + b[3].rangePer + '%';
            }
        },
        grid: {
            borderWidth: 0,
            x: 35,
            x2: 30,
            y: 35
        },
        xAxis: [{
            type: 'category',
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
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            splitLine: false,
            data: []
        }],
        yAxis: [{
            type: 'value',
            name: '万元',
            nameTextStyle: {
                color: '#333'
            },
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
                    color: '#BEBEBE'
                }
            }
        }],
        series: []
    };
    //人均效益变化幅度降幅的option
    var decChartOption = $.extend(true, {}, incChartOption);

    //人均效益、营业利润、等效全职员工数
    var perBenefitOjb = {
        data: null,
        isDialogInit: null,
        barColorArr: ['#23C6C8', '#EA711E'],
        init: function () {
            var self = this;
            self.isDialogInit = false;
            $("#benefit .data").addClass("hide").eq(0).text(messageStr[0]).removeClass("hide");
            $("#profit .data").addClass("hide").eq(0).text(messageStr[0]).removeClass("hide");
            $("#employeenumber .data").addClass("hide").eq(0).text(messageStr[0]).removeClass("hide");
            $.ajax({
                url: urls.perBenefitUrl,
                data: {organizationId: reqOrgId},
                success: function (data) {
                    if (_.isEmpty(data)) {
                        self.data = null;
                        $("#benefit .data").addClass("hide").eq(0).text(messageStr[1]).removeClass("hide");
                        $("#profit .data").addClass("hide").eq(0).text(messageStr[1]).removeClass("hide");
                        $("#employeenumber .data").addClass("hide").eq(0).text(messageStr[1]).removeClass("hide");
                    } else {
                        self.data = data;
                        $("#benefit .data").addClass("hide").eq(1).removeClass("hide");
                        $("#profit .data").addClass("hide").eq(1).removeClass("hide");
                        $("#employeenumber .data").addClass("hide").eq(1).removeClass("hide");

                        self.render();
                    }
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;
            var lastMonth = data[0];	//上个月
            var twoMonthesAgo = data[1];//上上个月

            //人均效益
            var benefitValue = lastMonth.benefitValue;
            var twoBenefitValue = twoMonthesAgo.benefitValue;
            $("#benefit .accord-yj-float-value").text(Tc.formatFloat(benefitValue));
            $("#benefit .accord-bottom-float-value").html(getPerValue(benefitValue, twoBenefitValue));

            //营业利润
            var profitValue = lastMonth.profit;
            var twoProfitValue = twoMonthesAgo.profit;
            $("#profit .accord-yj-float-value").text(Tc.formatFloat(profitValue));
            $("#profit .accord-bottom-float-value").html(getPerValue(profitValue, twoProfitValue));

            //等效全职员工数
            var eqEmpNum = lastMonth.eqEmpNum;
            var twoEqEmpNum = twoMonthesAgo.eqEmpNum;
            $("#employeenumber .accord-yj-float-value").text(Tc.formatFloat(eqEmpNum));
            $("#employeenumber .accord-bottom-float-value").html(getPerValue(eqEmpNum, twoEqEmpNum));

            //点击
            $("#benefit .data").eq(1).unbind("click").on("click", function () {
                if (!self.isDialogInit) {
                    self.initVariationRange();
                    self.isDialogInit = true;
                }
                $('#beChangeModal').modal('show');
            });
        },
        initVariationRange: function () {
            var self = this;
            var increaseChart = initChart('increaseChart');
            var decreaseChart = initChart('decreaseChart');
            //增幅
            loadingChart('increaseChart');
            $.get(urls.variationRangeUrl, {organizationId: reqOrgId, upgrade: true}, function (data) {
                if (_.isEmpty(data)) {
                    hideChart('increaseChart', true);
                    return;
                }
                hideChart('increaseChart', false);
                self.initRangeChart(data, incChartOption, increaseChart);
            });
            //降幅
            loadingChart('decreaseChart');
            $.get(urls.variationRangeUrl, {organizationId: reqOrgId, upgrade: false}, function (data) {
                if (_.isEmpty(data)) {
                    hideChart('decreaseChart', true);
                    return;
                }
                hideChart('decreaseChart', false);
                self.initRangeChart(data, decChartOption, decreaseChart);
            });
        },
        initRangeChart: function (data, targetOption, targetChart) {
            var self = this;
            var xAxisData = [];
            var legendData = [];
            var seriesArr = [];
            var dataIndex = 0;
            $.each(data, function (i, n) {
                var legendStr = Tc.FormatYm(i) + '人均效益';
                legendData.push(legendStr);
                $.each(n, function (index, item) {
                    var bool = _.indexOf(xAxisData, item.organizationName);
                    if (bool == -1) {
                        xAxisData.push(item.organizationName);
                    }
                });
                seriesArr.push(self.getSingleSeries(n, self.barColorArr[1 - dataIndex], legendStr));
                dataIndex++;
            });
            targetOption.legend.data = legendData;
            targetOption.xAxis[0].data = xAxisData;
            targetOption.series = seriesArr;
            targetChart.setOption(targetOption);
            targetChart.resize();
        },
        getSingleSeries: function (data, barColor, name) {
            var obj = {
                type: 'bar',
                name: name,
                itemStyle: {
                    normal: {
                        color: barColor,
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return Tc.formatFloat(i.value);
                            }
                        }
                    }
                },
                barGap: 0,	//柱之类的距离
                barCategoryGap: '35%',		//分类柱之间的距离
                barMaxWidth: 43,
                data: data
            }
            return obj;
        }
    };
    perBenefitOjb.init();

    /*
     * 人均效益趋势
     * */
    var profitObj = {
        dataMonth: null,
        dataYear: null,
        types: ["month", "year"],
        option: {
            legend: {
                data: ['人均效益', '环比变化'],
                y: 'bottom',
                selectedMode: false
            },
            calculable: false,
            grid: {
                borderWidth: 0,
                x: 35,
                y: 45,
                x2: 50,
                y2: 70
            },
            color: ['#0b98e0', '#EA711E'],
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
//			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
            height: 300,//268
            // colNames: ['', '月份', '目标人均效（万元）', '实际人均效益（万元）','环比','达标率'],
            colModel: [
                {
                    name: 'yearMonth', width: 100, sortable: false, align: 'center',
                    formatter: function (value) {
                        if (value.length == 4) {
                            return value;
                        }
                        var year = value.substring(2, 4);
                        var momth = value.substring(4);
                        return year + "/" + momth;
                    }
                },
                {name: 'targetValue', width: 150, sortable: false, align: 'center'},
                {name: 'benefitValue', width: 150, sortable: false, align: 'center'},
                {
                    name: 'changeValue', width: 120, sortable: false, align: 'center',
                    formatter: function (value) {
                        if (value == "-") {
                            return value;
                        }

                        return value + "%";
                    }
                },
                {name: 'complianceRate', width: 120, sortable: false, align: 'center'}
            ],
            rownumbers: true,
            //rownumWidth: 40,
            scroll: true
        },
        chartMonth: null,
        chartYear: null,
        gridMonth: null,
        gridYear: null,
        chartMonthId: "profitMonthChart",
        chartYearId: "profitYearChart",
        gridMonthId: "profitMonthGrid",
        gridYearId: "profitYearGrid",
        init: function () {
            var self = this;
            self.initGrid();
            var objMonth = {
                chart: $("#profitMonthChart").parent(),
                grid: $("#profitMonth").parent()
            };
            self.message(objMonth, 0);
            self.request(0, function () {
                if (self.dataMonth && self.dataMonth.length > 0) {
                    self.message(objMonth, -1);
                    self.renderMonthChart(function (trendData) {
                        //月预警
                        self.warnMonth(trendData);
                    });
                    self.renderMonthGrid();
                } else {
                    self.message(objMonth, 1);
                }
            });

            var objYear = {
                chart: $("#profitYearChart").parent(),
                grid: $("#profitYear").parent()
            };
            self.message(objYear, 0);
            self.request(1, function () {
                if (self.dataYear && self.dataYear.length > 0) {
                    self.message(objYear, -1);
                    self.renderYearChart(function (trendData) {
                        //年预警
                        self.warnYear(trendData);
                    });
                    self.renderYearGrid();
                } else {
                    self.message(objYear, 1);
                }
            });
        },
        message: function (obj, index) {
            var self = this;
            if (index >= 0) {
                obj.chart.find(".loadingmessage").text(messageStr[index]).removeClass("hide");
                obj.chart.find(".data").addClass("hide");
                obj.grid.find(".loadingmessage").text(messageStr[index]).removeClass("hide");
                obj.grid.find(".data").addClass("hide");

                self.showloading($('.trade-warn,.recent-warn'), messageStr[index]);
            } else {
                obj.chart.find(".loadingmessage").addClass("hide");
                obj.chart.find(".data").removeClass("hide");
                obj.grid.find(".loadingmessage").addClass("hide");
                obj.grid.find(".data").removeClass("hide");
            }
        },
        initGrid: function () {
            var self = this;
            if (!self.gridMonth) {
                self.optionGrid.colNames = ['月份', '目标人均效益（万元）', '实际人均效益（万元）', '环比', '达标率'];
                self.gridMonth = $("#" + self.gridMonthId).jqGrid(self.optionGrid);
            }
            if (!self.gridYear) {
                self.optionGrid.colNames = ['年份', '目标人均效益（万元）', '实际人均效益（万元）', '环比', '达标率'];
                self.gridYear = $("#" + self.gridYearId).jqGrid(self.optionGrid);
            }
        },
        request: function (index, callback) {
            var self = this;
            $.ajax({
                url: urls.trendUrl,
                data: {
                    organizationId: reqOrgId,
                    type: self.types[index]
                },
                success: function (data) {
                    data.reverse();
                    if (index == 0) {
                        self.dataMonth = data;
                    } else if (index == 1) {
                        self.dataYear = data;
                    }
                    callback.call(new Object());
                }
            });
        },
        renderMonthChart: function (callback) {
            var self = this
            var data = self.dataMonth;
            var trendData = self.calculate(data);

            var option = self.getChartOption(trendData, self.types[0]);

            if (self.chartMonth) {
                self.chartMonth.clear();
            }
            self.chartMonth = initChart(self.chartMonthId);
            self.chartMonth.setOption(option, true);
            self.chartMonth.resize();

            if (callback) {
                callback.call(new Object(), trendData);
            }
        },
        renderMonthGrid: function () {
            var self = this;
            var trendData = self.dataMonth;
            var data = self.gridData(trendData);

            if (data.length > 0) {
                data.splice(data.length - 1, 1);
            }

            self.gridMonth.clearGridData().setGridParam({
                data: data
            }).trigger("reloadGrid");
            self.resizeGridMonth();
        },
        renderYearChart: function (callback) {
            var self = this;
            var data = self.dataYear;

            //最多取6个
            var n = 6, das = [];
            var len = data.length;
            var start = len > n ? len - n : 0;
            $.each(data, function (i, o) {
                if (i >= start)
                    das.push(o);
            });
            data = das;

            var trendData = self.calculate(data);

            var option = self.getChartOption(trendData, self.types[1]);

            if (self.chartYear) {
                self.chartYear.clear();
            }
            self.chartYear = initChart(self.chartYearId);
            self.chartYear.setOption(option);
            self.chartYear.resize();

            if (callback) {
                callback.call(new Object(), trendData);
            }
        },
        renderYearGrid: function () {
            var self = this;
            var trendData = self.dataYear;
            var data = self.gridData(trendData);
            self.gridYear.clearGridData().setGridParam({
                data: data
            }).trigger("reloadGrid");
            self.resizeGridYear();
        },
        getChartOption: function (trendData, type) {
            var self = this;
            var xAxisData = [];
            var benefitData = [];
            var changeData = [];
            $.each(trendData, function (i, o) {
                var yearMonthStr = (type == 'year') ? o.yearMonth : o.yearMonth.substr(2, 2) + '/' + o.yearMonth.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefitValue == null) {
                    o.benefitValue = 0;
                }
                benefitData.push(Tc.formatFloat(o.benefitValue));
                changeData.push(o.changeValue >= 0 ? o.changeValue : self.getNagativeItem(o.changeValue));
            });

            var option = Tc.cloneObj(self.option);
            option.tooltip.formatter = function (params) {
                var html = "<div>" + params[0].name + "<br>人均效益：" + params[0].value + "万元<br>环比变化：" + params[1].value + "%<div>";
                return html;
            };
            option.series[1].itemStyle.normal.label.formatter = function (i) {
                return i.value + '%';
            };

            option.xAxis[0].data = xAxisData;
            option.series[0].data = benefitData;
            option.series[1].data = changeData;

            return option;
        },
        calculate: function (data) {
            // 上一个月/上一年 的数据（计算同比/环比 时用到）
            var preObj = null;
            var trendData = [];
            $.each(data, function (i, item) {
                // 将年月转为字符串
                item.yearMonth += "";
                if (i == 0) {
                    preObj = item;
                    item.changeValue = '-';
                    // 页面上最多展示12条数据，如果结果集为13条时，说名第一条结果集是用于计算同比/环比的，不需在图表上显示
                    if (data.length < 13) {
                        if (item.targetValue == null || item.targetValue == "null") {
                            item.targetValue = "";
                        }
                        if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                            if (item.targetValue == null || item.targetValue == "null") {
                                item.targetValue = "";
                            }
                            item.complianceRate = "100%";
                        } else {
                            item.complianceRate = Tc.formatFloat(item.benefitValue * 100 / item.targetValue) + "%";
                        }
                        trendData.push(item);
                    }
                    return true;
                }
                // 同比/环比的值
                //	alert(item.benefitValue - preObj.benefitValue);
                if (preObj.benefitValue == null) {
                    item.changeValue = 0;
                } else {
                    item.changeValue = Tc.formatFloat(((item.benefitValue - preObj.benefitValue) / preObj.benefitValue) * 100);
                }

                if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                    if (item.targetValue == null || item.targetValue == "null") {
                        item.targetValue = "";
                    }
                    item.complianceRate = "100%";
                } else {
                    item.complianceRate = Tc.formatFloat(item.benefitValue * 100 / item.targetValue) + "%";
                }

                trendData.push(item);
                preObj = item;
            });
            return trendData;
        },
        getNagativeItem: function (val) {
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
        },
        gridData: function (trendData) {
            var showArr = [];
            $.each(trendData, function (i, obj) {
                showArr[trendData.length - 1 - i] = obj;
            });
            return showArr;
        },
        rerendder: function () {
            var self = this;
            if ($("#profitMonthChart:visible").length > 0) {
                self.renderMonthChart();
            } else if ($("#profitMonthGrid:visible").length > 0) {
                self.renderMonthGrid();
            } else if ($("#profitYearChart:visible").length > 0) {
                self.renderYearChart();
            } else if ($("#profitYearGrid:visible").length > 0) {
                self.renderYearGrid();
            }
        },
        resize: function () {
            var self = this;
            if ($("#profitMonthChart:visible").length > 0) {
                self.chartMonth.resize();
            } else if ($("#profitMonthGrid:visible").length > 0) {
                self.resizeGridMonth();
            } else if ($("#profitYearChart:visible").length > 0) {
                self.chartYear.resize();
            } else if ($("#profitYearGrid:visible").length > 0) {
                self.resizeGridYear();
            }
        },
        resizeGridMonth: function () {
            var self = this;
            if (self.gridMonth) {
                self.gridMonth.setGridWidth($("#gbox_" + self.gridMonthId).parent().width());
            }
        },
        resizeGridYear: function () {
            var self = this;
            if (self.gridYear) {
                self.gridYear.setGridWidth($("#gbox_" + self.gridYearId).parent().width());
            }
        },
        //预警
        warnMonth: function (trendData) {
            var self = this;
            self.warnIndustryMonth();
            self.warnTrend(trendData, 0);
        },
        warnYear: function (trendData) {
            var self = this;
            self.warnIndustryYear();
            self.warnTrend(trendData, 1);
        },
        //月预警 行业均值
        warnIndustryMonth: function () {
            var self = this;
            var warnObj = $('.trade-warn');
            $.get(urls.avgValueUrl, {organizationId: reqOrgId}, function (data) {
                warnObj.empty();
                if (!data) {
                    self.showWarnLess(warnObj, '暂无行业均值数据');
                    return;
                }
                var benefitVal = Tc.formatFloat(_.last(self.dataMonth).benefitValue);
                var tradeAver = Tc.formatFloat(data);
                //与行业均值比较的百分比
                var tradePercent = parseFloat(((benefitVal - tradeAver) / tradeAver * 100).toFixed(0));
                warnObj.append(self.getTradeWarn(data, tradePercent));
            });
        },
        //年预警 行业均值
        warnIndustryYear: function () {
            //TODO 年预警未完成，暂用月预警占位
        },
        getTradeWarn: function (tradeAver, tradePercent) {
            var self = this;
            var detailMsg = '月行业均值<label class="orange bolder">&nbsp;' + Tc.formatFloat(tradeAver) + '&nbsp;</label>万元';
            var arrowClass = '';
            var eqText = '';
            if (tradePercent > 0) {
                arrowClass = 'arrow-higher';
                eqText = '高于行业均值' + tradePercent + '%';
            } else if (tradePercent < 0) {
                arrowClass = 'arrow-lower';
                eqText = '低于行业均值' + Math.abs(tradePercent) + '%';
            } else {
                eqText = '等于行业均值';
            }
            var warnMsg = eqText;
            return self.warnTpl(tradePercent >= 0, detailMsg, warnMsg, arrowClass);
        },
        //预警 变化趋势 type：0月 1年
        warnTrend: function (trendData, type) {
            var self = this;
            var warnDom = $('#' + (type == 0 ? "warnMonth" : "warnYear")).find('.recent-warn');
            warnDom.empty();

            var len = trendData.length;
            //最近三个月/三年的人均效益   lastPerBenefit1：最近一个月
            if (len >= 4) {
                var lastPerBenefit1 = trendData[len - 1].benefitValue;
                var lastPerBenefit2 = trendData[len - 2].benefitValue;
                var lastPerBenefit3 = trendData[len - 3].benefitValue;
                var lastPerBenefit4 = trendData[len - 4].benefitValue;
                //是否下降
                var isFall = (lastPerBenefit1 < lastPerBenefit2) && (lastPerBenefit2 < lastPerBenefit3) && (lastPerBenefit3 < lastPerBenefit4);
                //是否上升
                var isRise = (lastPerBenefit1 > lastPerBenefit2) && (lastPerBenefit2 > lastPerBenefit3) && (lastPerBenefit3 > lastPerBenefit4);
                if (isFall) {
                    warnDom.append(self.getRecentWarn(type, 0));
                } else if (isRise) {
                    warnDom.append(self.getRecentWarn(type, 1));
                } else {
                    self.showWarnLess(warnDom, '暂无明显趋势变化');
                }
            }
            else {
                self.showWarnLess(warnDom, '暂无明显趋势变化');
            }
        },
        getRecentWarn: function (type, isUp) {
            var self = this;
            var dateTypeText = type == 0 ? '个月' : '年';
            var trendText = (isUp) ? '上升' : '下降';
            var arrowClass = (isUp) ? 'arrow-up' : 'arrow-down';
            var detailMsg = '近3' + dateTypeText + '变化趋势';
            var warnMsg = '连续3' + dateTypeText + trendText;
            return self.warnTpl(isUp, detailMsg, warnMsg, arrowClass);
        },
        //预警公用
        warnTpl: function (isUp, detailMsg, warnMsg, arrowClass) {
            var iconClass, msgClass;
            if (isUp) {
                iconClass = 'smile-face';
                msgClass = 'blue';
            } else {
                iconClass = 'warn-icon';
                msgClass = 'orange';
            }
            return '<div class="col-xs-4 col-md-3 ' + iconClass + '"></div>' +
                '<div class="col-xs-8 col-md-9" class="trade-aver">' +
                '<div class="detail-msg"> ' + detailMsg + ' </div>' +
                '<div class="warn-msg ' + msgClass + '">' + warnMsg + '<span class="' + arrowClass + '">&nbsp;</span></div>' +
                '</div>';
        },
        showWarnLess: function (targetDom, text) {
            var tipDom = $('<div>', {
                'class': 'text-center',
                'style': 'line-height:90px',
                'text': text
            });
            targetDom.append(tipDom);
        },
        showloading: function (targetDom, text) {
            targetDom.html('<div class="loadingmessage margintop35">' + text + '</div>');
        }
    }
    profitObj.init();

    /*
     *当前组织人均效益
     * */
    var orgBenefitObj = {
        optionChart: {
            grid: {
                borderWidth: 0,
                x: 50,
                y: 30,
                x2: 30,
                y2: 50
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
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
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
        optionGrid: {
            data: [],
            datatype: "local",
            altRows: true,//设置表格行的不同底色
            //			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
            height: 300,//268
            colNames: ['机构', '人均效益(万元)', '利润(万元)', '销售(万元)', '利润率'],
            colModel: [
                {name: 'organizationName', width: 160, sortable: false},
                {name: 'benefitValue', width: 100, sortable: false, align: 'right'},
                {
                    name: 'profit', width: 90, sortable: false, align: 'right',
                    formatter: function (cellvalue) {
                        return Tc.formatFloat(cellvalue);
                    }
                },
                {
                    name: 'salesAmount', width: 70, sortable: false, align: 'right',
                    formatter: function (cellvalue) {
                        return Tc.formatFloat(cellvalue);
                    }
                },
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
        dataText: null,
        chart: null,
        chartId: "orgBenefitChart",
        chartTextId: "orgBenefitText",
        grid: null,
        gridParentId: "orgBenefit",
        gridId: "orgBenefitGrid",
        isRenderChart: null,
        isRenderText: null,
        init: function () {
            var self = this;
            self.isRenderChart = self.isRenderText = -1;

            //图表、列表
            $("#" + self.chartId).parent().find(".data").addClass("hide").eq(0).text(messageStr[0]).removeClass("hide");
            $("#" + self.gridParentId).parent().find(".data").addClass("hide").eq(0).text(messageStr[0]).removeClass("hide");
            if (fteObj) {
                fteObj.message(0);
            }
            $.get(urls.orgBenefitUrl, {organizationId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.isRenderChart = 1;
                    $("#" + self.chartId).parent().find(".data").addClass("hide").eq(1).removeClass("hide");
                    $("#" + self.gridParentId).parent().find(".data").addClass("hide").eq(1).removeClass("hide");
                    $.each(data, function (i, item) {
                        item.benefitValue = Tc.formatFloat(item.benefitValue);
                    });
                    self.data = data;
                    self.renderChart();
                    self.renderGrid();
                } else {
                    self.isRenderChart = 0;
                    $("#" + self.chartId).parent().find(".data").addClass("hide").eq(0).text(messageStr[1]).removeClass("hide");
                    $("#" + self.gridParentId).parent().find(".data").addClass("hide").eq(0).text(messageStr[1]).removeClass("hide");
                }
                self.renderNoData();

                //FTE
                fteObj.init(data);
            });

            //图表右栏内容
            $("#" + self.chartTextId).parent().find(".data").addClass("hide").eq(0).text(messageStr[0]).removeClass("hide");
            $.get(urls.orgRecentDataUrl, {organizationId: reqOrgId}, function (data) {
                if (!_.isEmpty(data)) {
                    self.isRenderText = 1;
                    $("#" + self.chartTextId).parent().find(".data").addClass("hide").eq(1).removeClass("hide");
                    self.renderChartText(data);
                } else {
                    self.isRenderText = 0;
                    $("#" + self.chartTextId).parent().find(".data").addClass("hide").eq(0).text(messageStr[1]).removeClass("hide");
                }
                self.renderNoData();
            });
        },
        renderNoData: function () {
            var self = this;
            if (self.isRenderChart >= 0 && self.isRenderText >= 0) {
                if (self.isRenderChart == 0 && self.isRenderText == 0) {
                    $("#" + self.chartTextId).parent().addClass("hide");
                    $("#" + self.chartId).parent().addClass("width100pc");
                } else {
                    $("#" + self.chartTextId).parent().removeClass("hide");
                    $("#" + self.chartId).parent().removeClass("width100pc");
                    self.resize();
                }
            }
        },
        renderChart: function () {
            var self = this;
            var data = self.data;

            var xAxisData = [];
            // 人均效益
            var benefitData = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.organizationName);
                benefitData.push({value: item.benefitValue, id: item.id});
            });

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initChart(self.chartId);

            self.optionChart.xAxis[0].data = xAxisData;
            self.optionChart.series[0].data = benefitData;
            self.chart.setOption(self.optionChart);
        },
        renderGrid: function () {
            var self = this;
            var data = self.data;

            if (!self.grid) {
                self.grid = $("#" + self.gridId).jqGrid(self.optionGrid);
            }
            self.grid.clearGridData().setGridParam({
                data: data
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        renderChartText: function (data) {
            var self = this;

            var benefit = data.benefit;
            var gainAmount = data.gainAmount;
            var salesAmount = data.salesAmount;
            //（最近12个月的值 - 上一12个月的值）/上一12个月的值
            var preBenfitRate = '', preGainAmountRate = '', preSalesAmountRate = '';
            if(!_.isNull(data.yoyBenefit)){
                preBenfitRate = getPerValue(benefit, data.yoyBenefit);
            }
            if(!_.isNull(data.yoyGainAmount)){
                preGainAmountRate = getPerValue(gainAmount, data.yoyGainAmount);
            }
            if(!_.isNull(data.yoySalesAmount)){
                preSalesAmountRate = getPerValue(salesAmount, data.yoySalesAmount);
            }
            var tpl1 = self.getRecentRateTpl(Tc.formatFloat(benefit), preBenfitRate, '人均效益');
            var tpl2 = self.getRecentRateTpl(Tc.formatFloat(gainAmount), preGainAmountRate, '利润总额');
            var tpl3 = self.getRecentRateTpl(salesAmount.toFixed(0), preSalesAmountRate, '销售总额');
            $('#' + self.chartTextId).empty().append(tpl1 + tpl2 + tpl3);
        },
        getRecentRateTpl: function (num, percent, text) {
            return '<div class="row">' +
                '<div class="col-xs-12">' +
                '<div class="num pull-left">' + num + '</div>' +
                '<b class="pull-right paddingtop8 blue">' + percent + '</b>' +
                '</div>' +
                '<div class="col-xs-12 bottom-grey">近十二个月' + text + '</div>' +
                '</div>';
        },
        resizeGrid: function () {
            var self = this;
            if (self.grid) {
                self.grid.setGridWidth($("#" + self.gridParentId).width());
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
                if (self.chart) {
                    self.chart.resize();
                }
            } else if ($("#orgBenefit:visible").length > 0) {
                self.resizeGrid();
            }
        }
    }
    orgBenefitObj.init();

    /*
     *等效全职员工数(FTE)
     * */
    var fteObj = {
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
            color: chartPieColor,
            calculable: false,
            series: [{
                type: 'pie',
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
                    }
                },
                radius: '55%',
                data: []
            }]
        },
        data: null,
        chart: null,
        chartId: "fteChart",
        init: function (data) {
            var self = this;
            if (data && data.length > 0 && data[0].id == reqOrgId) {
                self.message(-1);
                self.data = data;
                self.render();
            } else {
                self.message(1);
            }
        },
        render: function () {
            var self = this;
            var data = self.data[0];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initChart(self.chartId);

            //全职，兼职
            var fulltimeSum = data.fulltimeSum;
            var passtimeSum = data.passtimeSum;
            var overtimeSum = data.overtimeSum;
            var total = fulltimeSum + passtimeSum + overtimeSum;
            var seriesData = [self.formatData('全职', fulltimeSum, total), self.formatData('兼职', passtimeSum, total), self.formatData('加班', overtimeSum, total)];
            self.option.series[0].data = seriesData;
            self.option.title.text = '合计 ' + Tc.formatNumber(total);
            self.chart.setOption(self.option);
            self.chart.resize();
        },
        formatData: function (name, value, total) {
            if (!value) {
                return {};
            }
            //页面展示为 ：name，value，(换行)percent%
            return {
                value: value,
                name: (name + '\n' + Tc.formatNumber(value) + '\n' + ((value / total) * 100).toFixed(0) + '%')
            };
        },
        message: function (index) {
            var self = this;
            if (index >= 0) {
                $("#" + self.chartId).parent().find(".data").addClass("hide").eq(0).text(messageStr[index]).removeClass("hide");
            } else {
                $("#" + self.chartId).parent().find(".data").addClass("hide").eq(1).removeClass("hide");
            }
        },
        resize: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //改变窗口大小时,改变图表及表格的大小
    $(window).resize(function () {
        profitObj.resize();
        orgBenefitObj.resize();
        fteObj.resize();
    });

});