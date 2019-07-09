/**
 * Created by Administrator on 2016/3/14.
 */
require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'jgGrid', 'underscore', 'utils', 'organTreeSelector', 'vernierCursor', 'timeLine2', 'searchBox3', "jquery-mCustomScrollBar", 'messenger'], function ($, echarts) {

    var ecConfig = require('echarts/config');
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var reqOrgId = win.currOrganId;
    var urls = {
        dedicateYearUrl: webRoot + '/empSatisfaction/getEngagementYearSoure',//年度敬业度对比
        dedicateSubUrl: webRoot + '/empSatisfaction/getEngagementSoure',//下级组织敬业度对比
        dedicateListUrl: webRoot + '/empSatisfaction/getEngagementSubject.json', //敬业度题目分析
        satisfactionYearUrl: webRoot + '/empSatisfaction/getSatisfactionYearSoure',//年度满意度对比
        satisfactionSubUrl: webRoot + '/empSatisfaction/getSatisfactionSoure',//下级组织满意度对比
        satisfactionListUrl: webRoot + '/empSatisfaction/getSatisfactiontSubject'  //满意度题目分析
    };

    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 2
        });
    };

    /*切换左边导航*/
    $(".leftListDiv").click(function () {
        var _this = $(this);
        if (_this.hasClass("selectList")) {
            return;
        } else {
            $(".rightBodyPage").hide();
            var $page = _this.attr("page");
            $("#" + $page).show();
            $(".leftListDiv").removeClass("selectList");
            _this.addClass("selectList");

            if ($("#page-one").is(':hidden')) {
                if (objSatisfactionYearChart.chart) objSatisfactionYearChart.chart.resize();
                objSatisfactionSubChart.resize();
                objSatisfactionList.resize();
                objSatisfactionList.resizeheight();
            } else {
                if (objDedicateYearChart.chart) objDedicateYearChart.chart.resize();
                objDedicateSubChart.resize();
                objDedicateList.resize();
                objDedicateList.resizeheight();
            }
        }
    });

    $(".ct-mCustomScrollBar").mCustomScrollbar({
        axis: "yx",
        scrollButtons: {enable: true},
        scrollbarPosition: "outside",
        theme: "minimal-dark"
    });

    var chartWidth = 0;
    var setHideChartWidthHeight = function () {
        var idobj = "";
        if ($("#page-one").is(':hidden')) {
            idobj = $("#satisfactionYearChart");
        } else {
            idobj = $("#dedicateYearChart");
        }
        chartWidth = idobj.parent().width();
        $("#dedicateYearChart,#dedicateSubChart,#satisfactionYearChart,#satisfactionSubChart").css({
            "width": chartWidth + "px",
            "height": idobj.parent().height() + "px"
        });
    }
    setHideChartWidthHeight();

    $(window).resize(function () {
        setHideChartWidthHeight();
        if (objDedicateYearChart.chart)
            objDedicateYearChart.chart.resize();
        objDedicateSubChart.resize();
        objDedicateList.resize();

        if (objSatisfactionYearChart.chart)
            objSatisfactionYearChart.chart.resize();
        objSatisfactionSubChart.resize();
        objSatisfactionList.resize();
    });

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        objDedicateYearChart.init();
        objDedicateSubChart.init();
        objDedicateList.init();
        timeLineObj.init(organId);

        objSatisfactionYearChart.init();
        objSatisfactionSubChart.init();
        objSatisfactionList.init();
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
                title: '管理建议与备忘',
                titleSuffix: '条未读',
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
        }
    }
    timeLineObj.init(reqOrgId);

    /*
     显示更多备忘
     */
    $("#memo-body-div").mouseenter(function () {
        $("#memo-big-list-more").show();
    });
    $("#memo-body-div").mouseleave(function () {
        $("#memo-big-list-more").hide();
    });

    //敬业度、满意度对比
    var objRate = {
        setColor: function (v) {
            var colorStr = "";
            if (v > 0) {
                colorStr = "colorincrease";
            } else if (v < 0) {
                colorStr = "colorreduce";
            }
            return colorStr;
        },
        initDedicate: function (thisyear, lastyear) {
            var self = this;
            var objtext = $("#dedicaterate");
            var objincrease = $("#dedicateincrease");
            self.render(objtext, objincrease, thisyear, lastyear);
        },
        initSatisfaction: function (thisyear, lastyear) {
            var self = this;
            var objtext = $("#satisfactionrate");
            var objincrease = $("#satisfactionincrease");
            self.render(objtext, objincrease, thisyear, lastyear);
        },
        render: function (objtext, objincrease, thisyear, lastyear) {
            var self = this;
            objtext.text(thisyear >= 0 ? parseFloat(thisyear).toFixed(1) + "%" : "-");
            objincrease.removeClass("colorincrease").removeClass("colorreduce");
            if (lastyear >= 0) {
                objincrease.parent().show();
                var rate = (((thisyear / lastyear).toFixed(6) - 1) * 100).toFixed(1);
                objincrease.text((rate > 0 ? "+" : "") + rate + "%");
                if (rate != 0) {
                    objincrease.addClass(self.setColor(rate));
                }
            } else {
                objincrease.parent().hide();
                objincrease.text("");
            }
            self.show(objtext);
        },
        show: function (obj) {
            obj.parents(".body-div").find(".scoreloadingmsg").addClass("hide");
            obj.parent().removeClass("hide");
        }
    }

    var message = ["数据读取中...", "暂无数据"];
    var divLoadding = '<div class="chartloadingmsg height100pc"></div>';
    var TextShape = require('zrender/shape/Text');
    var optionYearFormatter = function (params, ticket, callback) {
        var res = params[0].name + '年';
        for (var i = 0, l = params.length; i < l; i++) {
            res += '<br>' + params[i].seriesName + ' : ' + params[i].value + '%';
        }
        setTimeout(function () {
            callback(ticket, res);
        }, 10)
        return 'loading';
    };
    //年度敬业度满意度对比echarts option
    var optionYear = {
        tooltip: {
            trigger: "axis",
            axisPointer: {
                lineStyle: {
                    width: 0
                }
            }
        },
        legend: {
            data: ["当前组织", "公司"],
            x: 90,
            y: 330,
            orient: "horizontal",
            selectedMode: false
        },
        toolbox: {
            show: false
        },
        xAxis: [
            {
                type: "category",
                data: [],
                splitLine: {
                    show: false,
                    lineStyle: {
                        color: "rgb(188, 188, 188)",
                        width: 1
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(188, 188, 188)",
                        width: 1
                    }
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    formatter: "{value}年"
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: "rgb(188, 188, 188)"
                    }
                },
                axisLabel: {
                    formatter: "{value}%"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(188, 188, 188)",
                        width: 1
                    }
                },
                max: 100,
                min: 0,
                splitNumber: 5
            }
        ],
        series: [
            {
                clickable: false,
                barMaxWidth: 40,
                name: "当前组织",
                type: "bar",
                data: [],
                barGap: 0,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: "{c}%",
                            textStyle: {
                                color: "#000000"
                            }
                        }
                    }
                }
            },
            {
                clickable: false,
                barMaxWidth: 40,
                name: "公司",
                type: "bar",
                data: [],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: "{c}%",
                            textStyle: {
                                color: "#000000"
                            }
                        }
                    }
                }
            }
        ],
        color: ["rgb(49, 133, 196)", "rgb(151, 203, 255)"],
        grid: {
            x: 60,
            y: 50,
            x2: 30,
            y2: 90,
            borderWidth: 0
        }
    };

    //下级组织满意度满意度对比echarts option
    var optionSub = {
        toolbox: {
            show: false
        },
        xAxis: [
            {
                type: "category",
                data: [],
                axisLine: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    rotate: 30,
                    textStyle: {
                        color: "#000000",
                        fontFamily: '微软雅黑'
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
                splitLine: {
                    lineStyle: {
                        type: "dashed",
                        color: "rgb(226, 226, 226)"
                    }
                },
                max: 100,
                min: 0,
                splitNumber: 5,
                axisLabel: {
                    formatter: "{value}%"
                }
            }
        ],
        series: [
            {
                clickable: false,
                barMaxWidth: 40,
                name: "下级组织",
                type: "bar",
                data: [],
                itemStyle: {
                    normal: {
                        label: {
                            formatter: "{c}%",
                            textStyle: {
                                color: "#000000"
                            },
                            show: true
                        }
                    }
                },
                markLine: {
                    clickable: false,
                    symbolSize: [0, 0],
                    itemStyle: {
                        normal: {
                            color: '#FD3534',
                            lineStyle: {
                                type: 'solid'
                            },
                            label: {
                                show: true,
                                formatter: "{c}%"
                            }
                        }
                    },
                    data: []
                }
            }
        ],
        grid: {
            borderWidth: 0,
            x: 60,
            y: 60,
            x2: 50,
            y2: 100
        },
        color: ["rgb(49, 133, 196)", "#87cefa"],
        tooltip: {
            show: true,
            formatter: "{b}：{c}%"
        },
        dataZoom: {
            show: true,
            realtime: true,
            height: 20,
            start: 0,
            end: 30,
            y: 340,
            showDetail: false
        }
    };

    //敬业度满意度题目分析
    var optionList = {
        "hoverrows": false,
        "viewrecords": false,
        "gridview": true,
        "height": "auto",
        "loadonce": true,
        "rowNum": -1,
        "pager": "false",
        "scroll": false,
        "scrollrows": true,
        "treeGrid": true,
        "ExpandColumn": "name",
        "treedatatype": "json",
        "treeGridModel": "adjacency",
        "treeReader": {
            level_field: "level",
            parent_id_field: "parent",
            leaf_field: "isLeaf",
            expanded_field: "expanded"
        },
        "datatype": "json"
    };

    //下级组织满意度满意度对比 平均线图例
    var initLineLengend = function (zr) {
        if (zr) {
            zr.clear();
            zr.addShape(new TextShape({
                style: {
                    x: zr.getWidth() - 170,
                    y: 25,
                    color: '#FD3534',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑'
                },
                hoverable: false
            }));
            zr.addShape(new TextShape({
                style: {
                    x: zr.getWidth() - 140,
                    y: 25,
                    color: '#000',
                    text: '当前组织',
                    textAlign: 'left',
                    textFont: 'normal 14px 微软雅黑'
                },
                hoverable: false
            }));
            zr.refresh();
        }
    }

    //年度敬业度对比
    var objDedicateYearChart = {
        chartId: "dedicateYearChart",
        chart: null,
        data: null,
        option: null,
        init: function () {
            var self = this;
            var option = Tc.cloneObj(optionYear);
            option.tooltip.formatter = optionYearFormatter;
            self.option = option;
            self.loadding();
            self.request();
        },
        request: function () {
            var self = this;
            $.get(urls.dedicateYearUrl, {organizationId: reqOrgId}, function (data) {
                self.data = data;
                self.renderChart();
            });
        },
        renderChart: function () {
            var self = this;
            var hasTopOrg = !self.data.flag;
            var data = self.data.data;
            var curorgscore = [], comscore = [], years = [];
            $.each(data, function (i, item) {
                curorgscore.push(item.curorgscore ? (item.curorgscore * 100).toFixed(1) : 0);
                comscore.push(item.comscore ? (item.comscore * 100).toFixed(1) : 0);
                years.push(item.date ? parseInt(item.date.substring(0, 4)) : 0);
            });

            $("#dedicateyear").text(years.length > 0 && years[years.length - 1] != 0 ? years[years.length - 1] : '-');

            //年敬业度得分
            var thisyear = years.length > 0 && years[years.length - 1] != 0 ? years[years.length - 1] : 0;
            var lastyear = years.length > 1 && years[years.length - 2] != 0 ? years[years.length - 2] : 0;
            var thisyearscore = curorgscore.length > 0 ? curorgscore[curorgscore.length - 1] : -1;
            var lastyearscore = curorgscore.length > 1 && (thisyear == lastyear + 1) ? curorgscore[curorgscore.length - 2] : -1;
            objRate.initDedicate(thisyearscore, lastyearscore);

            if (data.length > 0) {
                self.option.xAxis[0].data = years;
                self.option.series[0].data = curorgscore;
                self.option.series[1].data = comscore;
                if (!hasTopOrg) {//顶级时不显示上级组织
                    self.option.series.splice(1, 1);
                    self.option.legend.data.splice(1, 1);
                }
                self.show();
                self.chart = echarts.init(document.getElementById(self.chartId));
                self.chart.setOption(self.option);
                self.chart.refresh();
            } else {
                var self = this;
                self.noData();
            }
        },
        loadding: function () {
            $("#" + this.chartId).before(divLoadding).addClass("hide");
        },
        show: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").text(message[0]).addClass("hide");
            $("#" + this.chartId).removeClass("hide");
        },
        noData: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").text(message[1])
        }
    }
    objDedicateYearChart.init();

    //下级组织敬业度对比
    var objDedicateSubChart = {
        zrender: null,
        chartId: "dedicateSubChart",
        chart: null,
        data: null,
        option: Tc.cloneObj(optionSub),
        init: function () {
            var self = this;
            self.loadding();
            self.request();
        },
        request: function () {
            var self = this;
            $.get(urls.dedicateSubUrl, {organizationId: reqOrgId}, function (data) {
                self.data = data;
                self.renderChart();
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.data;
            if (data.length > 1) {
                var xaxisData = [], seriesData = [];
                var currentValue = 0;
                $.each(data, function (i, item) {
                    if (i > 0) {
                        xaxisData.push(item.name);
                        seriesData.push((item.curorgscore * 100).toFixed(1));
                    } else {
                        currentValue = (item.curorgscore * 100).toFixed(1);
                    }
                });
                self.option.xAxis[0].data = xaxisData;
                self.option.series[0].data = seriesData;
                var len = self.option.series[0].data.length + 1;
                self.option.series[0].markLine.data = self.markLine(len, currentValue);
                self.dataZoom();

                self.show();
                self.chart = echarts.init(document.getElementById(self.chartId));
                self.chart.setOption(self.option);
                self.zrender = self.chart.getZrender();
                initLineLengend(self.zrender);
                self.chart.refresh();
                self.chart.resize();
            } else {
                var self = this;
                self.noData();
            }
        },
        dataZoom: function () {
            var self = this;
            var data = self.data;
            var width = 110 + (data.length * 35);
            if (width > chartWidth) {
                var barWidth = data.length * 35;
                var moreWidth = width - chartWidth;
                var w = (barWidth / (barWidth + moreWidth)) * 50;
                console.log(w)
                self.option.dataZoom = {
                    show: true,
                    realtime: true,
                    height: 20,
                    start: 0,
                    end: w,
                    y: 340,
                    showDetail: false,
                    z: 999
                };
            } else {
                self.option.dataZoom = null;
            }
        },
        markLine: function (len, currentValue) {
            return [
                [
                    {xAxis: -1, yAxis: currentValue, value: currentValue, name: '当前组织'},
                    {xAxis: len, yAxis: currentValue}
                ]
            ];
        },
        loadding: function () {
            $("#" + this.chartId).before(divLoadding).addClass("hide");
        },
        show: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").addClass("hide");
            $("#" + this.chartId).removeClass("hide");
        },
        noData: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").text(message[1])
        },
        resize: function () {
            var self = this;
            if (self.zrender)
                initLineLengend(self.zrender);
            if (self.chart)
                self.chart.resize();
        }
    }
    objDedicateSubChart.init();

    //敬业度题目分析
    var objDedicateList = {
        idStr: "dedicatelist",
        jqgrid: null,
        option: $.extend({}, optionList, {
            "url": urls.dedicateListUrl,
            "colModel": [{
                "name": "name",
                "index": "name",
                "sorttype": "string",
                "label": "敬业度题目",
                "align": "left",
                "sortable": false
            }, {
                "name": "curorgscore",
                "index": "curorgscore",
                "sorttype": "numeric",
                "label": "当前组织得分",
                "align": "center",
                "width": 90,
                "sortable": false
            }, {
                "name": "comscore",
                "index": "comscore",
                "label": "公司得分",
                "align": "center",
                "width": 60,
                "sortable": false
            }, {
                "name": "parent",
                "index": "parent",
                "hidden": true
            }
            ],
            gridComplete: function () {
                $("#dedicatelist td[aria-describedby='dedicatelisttree_parent']").each(function (i, item) {
                    if ($.trim($(this).text()) == "") {
                        $(this).parent().eq(0).find("span").addClass("fontweightbold");
                    }
                });
                $("#dedicatelist tr").each(function (i, item) {
                    var curorg = $.trim($(this).find("td[aria-describedby='dedicatelisttree_curorgscore']").eq(0).text().replace("%", ""));
                    var com = $.trim($(this).find("td[aria-describedby='dedicatelisttree_comscore']").eq(0).text().replace("%", ""));
                    var curorgscore = curorg != "" ? parseFloat(curorg) : 0;
                    var comscore = com != "" ? parseFloat(com) : 0;
                    if (curorgscore > comscore) {
                        $(this).find("td[aria-describedby='dedicatelisttree_curorgscore']").eq(0).addClass("colorreduce");
                    } else if (curorgscore < comscore) {
                        $(this).find("td[aria-describedby='dedicatelisttree_comscore']").eq(0).addClass("colorreduce");
                    }
                });

                objDedicateList.resizeheight();
                $("#dedicatelist .treeclick").on("click", function () {
                    objDedicateList.resizeheight();
                });
            }
        }),
        init: function () {
            var self = this;
            $("#" + self.idStr).html('<table id="' + self.idStr + 'tree"></table>');
            self.option.postData = {organizationId: reqOrgId};
            self.jqgrid = $("#" + self.idStr + 'tree').jqGrid(self.option);
            self.resize();
        },
        resize: function () {
            var self = this;
            self.jqgrid.setGridWidth($("#" + self.idStr).width());
        },
        resizeheight: function () {
            var self = this;
            var jqheight = $("#" + self.idStr).parent().height();
            var height = jqheight + 30;
            if (height < 350) {
                height = 350;
            }
            $("#" + self.idStr).parent().parent().css({"height": height + "px"});

            //win.$("iframe[name='mainFrame']").css({"height": ($("body").height() + 50) + "px"});
        }
    }
    objDedicateList.init();


    //年度满意度对比
    var objSatisfactionYearChart = {
        chartId: "satisfactionYearChart",
        chart: null,
        data: null,
        option: null,
        init: function () {
            var self = this;
            var option = Tc.cloneObj(optionYear);
            option.tooltip.formatter = optionYearFormatter;
            self.option = option;
            self.loadding();
            self.request();
        },
        request: function () {
            var self = this;
            $.get(urls.satisfactionYearUrl, {organizationId: reqOrgId}, function (data) {
                self.data = data;
                self.renderChart();
            });
        },
        renderChart: function () {
            var self = this;
            var hasTopOrg = !self.data.flag;
            var data = self.data.data;
            var curorgscore = [], comscore = [], years = [];
            $.each(data, function (i, item) {
                curorgscore.push(item.curorgscore ? (item.curorgscore * 100).toFixed(1) : 0);
                comscore.push(item.comscore ? (item.comscore * 100).toFixed(1) : 0);
                years.push(item.date ? parseInt(item.date.substring(0, 4)) : 0);
            });

            $("#satisfactionyear").text(years.length > 0 && years[years.length - 1] != 0 ? years[years.length - 1] : '-');

            //年满意度得分
            var thisyear = years.length > 0 && years[years.length - 1] != 0 ? years[years.length - 1] : 0;
            var lastyear = years.length > 1 && years[years.length - 2] != 0 ? years[years.length - 2] : 0;
            var thisyearscore = curorgscore.length > 0 ? curorgscore[curorgscore.length - 1] : -1;
            var lastyearscore = curorgscore.length > 1 && (thisyear == lastyear + 1) ? curorgscore[curorgscore.length - 2] : -1;
            objRate.initSatisfaction(thisyearscore, lastyearscore);

            if (data.length > 0) {
                self.option.xAxis[0].data = years;
                self.option.series[0].data = curorgscore;
                self.option.series[1].data = comscore;
                if (!hasTopOrg) {//顶级时不显示上级组织
                    self.option.series.splice(1, 1);
                    self.option.legend.data.splice(1, 1);
                }
                self.show();
                self.chart = echarts.init(document.getElementById(self.chartId));
                self.chart.setOption(self.option);
                self.chart.refresh();
            } else {
                var self = this;
                self.noData();
            }
        },
        loadding: function () {
            $("#" + this.chartId).before(divLoadding).addClass("hide");
        },
        show: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").text(message[0]).addClass("hide");
            $("#" + this.chartId).removeClass("hide");
        },
        noData: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").text(message[1])
        }
    }
    objSatisfactionYearChart.init();

    //下级组织满意度对比
    var objSatisfactionSubChart = {
        zrender: null,
        chartId: "satisfactionSubChart",
        chart: null,
        data: null,
        option: Tc.cloneObj(optionSub),
        init: function () {
            var self = this;
            self.loadding();
            self.request();
        },
        request: function () {
            var self = this;
            $.get(urls.satisfactionSubUrl, {organizationId: reqOrgId}, function (data) {
                self.data = data;
                self.renderChart();
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.data;
            if (data.length > 1) {
                var xaxisData = [], seriesData = [];
                var currentValue = 0;
                $.each(data, function (i, item) {
                    if (i > 0) {
                        xaxisData.push(item.name);
                        seriesData.push((item.curorgscore * 100).toFixed(1));
                    } else {
                        currentValue = (item.curorgscore * 100).toFixed(1);
                    }
                });
                self.option.xAxis[0].data = xaxisData;
                self.option.series[0].data = seriesData;
                var len = self.option.series[0].data.length + 1;
                self.option.series[0].markLine.data = self.markLine(len, currentValue);
                self.dataZoom();

                self.show();
                self.chart = echarts.init(document.getElementById(self.chartId));
                self.chart.setOption(self.option);
                self.zrender = self.chart.getZrender();
                initLineLengend(self.zrender);
                self.chart.refresh();
                self.chart.resize();
            } else {
                var self = this;
                self.noData();
            }
        },
        dataZoom: function () {
            var self = this;
            var data = self.data;
            var width = 110 + (data.length * 35);
            if (width > chartWidth) {
                var barWidth = data.length * 35;
                var moreWidth = width - chartWidth;
                var w = (barWidth / (barWidth + moreWidth)) * 50;
                console.log(w)
                self.option.dataZoom = {
                    show: true,
                    realtime: true,
                    height: 20,
                    start: 0,
                    end: w,
                    y: 340,
                    showDetail: false,
                    z: 999
                };
            } else {
                self.option.dataZoom = null;
            }
        },
        markLine: function (len, currentValue) {
            return [
                [
                    {xAxis: -1, yAxis: currentValue, value: currentValue, name: '当前组织'},
                    {xAxis: len, yAxis: currentValue}
                ]
            ];
        },
        loadding: function () {
            $("#" + this.chartId).before(divLoadding).addClass("hide");
        },
        show: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").addClass("hide");
            $("#" + this.chartId).removeClass("hide");
        },
        noData: function () {
            $("#" + this.chartId).parent().find(".chartloadingmsg").text(message[1])
        },
        resize: function () {
            var self = this;
            if (self.zrender)
                initLineLengend(self.zrender);
            if (self.chart)
                self.chart.resize();
        }
    }
    objSatisfactionSubChart.init();

    //满意度题目分析
    var objSatisfactionList = {
        idStr: "satisfactionlist",
        jqgrid: null,
        option: $.extend({}, optionList, {
            "url": urls.satisfactionListUrl,
            "colModel": [{
                "name": "name",
                "index": "name",
                "sorttype": "string",
                "label": "满意度维度",
                "align": "left",
                "sortable": false
            }, {
                "name": "curorgscore",
                "index": "curorgscore",
                "sorttype": "numeric",
                "label": "当前组织得分",
                "align": "center",
                "width": 90,
                "sortable": false
            }, {
                "name": "comscore",
                "index": "comscore",
                "label": "公司得分",
                "align": "center",
                "width": 60,
                "sortable": false
            }
            ],
            gridComplete: function () {
                $("#satisfactionlist tr").each(function (i, item) {
                    var curorg = $.trim($(this).find("td[aria-describedby='satisfactionlisttree_curorgscore']").eq(0).text().replace("%", ""));
                    var com = $.trim($(this).find("td[aria-describedby='satisfactionlisttree_comscore']").eq(0).text().replace("%", ""));
                    var curorgscore = curorg != "" ? parseFloat(curorg) : 0;
                    var comscore = com != "" ? parseFloat(com) : 0;
                    if (curorgscore > comscore) {
                        $(this).find("td[aria-describedby='satisfactionlisttree_curorgscore']").eq(0).addClass("colorreduce");
                    } else if (curorgscore < comscore) {
                        $(this).find("td[aria-describedby='satisfactionlisttree_comscore']").eq(0).addClass("colorreduce");
                    }
                });

                objSatisfactionList.resizeheight();
                $("#satisfactionlist .treeclick").on("click", function () {
                    objSatisfactionList.resizeheight();
                });
            }
        }),
        init: function () {
            var self = this;
            $("#" + self.idStr).html('<table id="' + self.idStr + 'tree"></table>');
            self.option.postData = {organizationId: reqOrgId};
            self.jqgrid = $("#" + self.idStr + 'tree').jqGrid(self.option);
            self.resize();
        },
        resize: function () {
            var self = this;
            self.jqgrid.setGridWidth($("#" + self.idStr).width());
        },
        resizeheight: function () {
            var self = this;
            var jqheight = $("#" + self.idStr).parent().height();
            var height = jqheight + 30;
            if (height < 350) {
                height = 350;
            }
            $("#" + self.idStr).parent().parent().css({"height": height + "px"});

            //win.$("iframe[name='mainFrame']").css({"height": ($("body").height() + 50) + "px"});
        }
    }
    objSatisfactionList.init();
});