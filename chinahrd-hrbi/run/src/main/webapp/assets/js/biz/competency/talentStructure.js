require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/pie', 'echarts/chart/gauge', 'jgGrid', 'bootstrap',
    'underscore', 'utils', 'organTreeSelector', 'vernierCursor', 'timeLine2', 'searchBox3', "jquery-mCustomScrollBar", 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;

    var urls = {
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        structureBudgetAnalyse: webRoot + '/talentStructure/getBudgetAnalyse.do', //编制分析
        structureGetConfigWarnVal: webRoot + '/talentStructure/getConfigWarnVal.do',  //加载使用率
        structureSetConfigWarnVal: webRoot + '/talentStructure/setConfigWarnVal.do',  //保存使用率
        structureGetTalentStuctureData: webRoot + '/talentStructure/getTalentStuctureData.do',  //人力结构数据
        structureGetAbEmpCountBarBySeqId: webRoot + '/talentStructure/getAbEmpCountBarBySeqId.do'  //职位序列分布 职级分布
    };
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var ecConfig = require('echarts/config');


    $(".ct-mCustomScrollBar").mCustomScrollbar({
        axis: "yx",
        scrollButtons: {enable: true},
        scrollbarPosition: "outside",
        theme: "minimal-dark"
    });

    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    };

    $(window).resize(function () {
        objBudgetAnalyse.chart.resize();
        objManagersEmployee.chart.resize();
        objRank.chart.resize();
        objPositionSequence.chart.resize();
        objPositionRank.chart.resize();
        organDistribution.chart.resize();
        organDistribution.chart.resize();
        workLocation.chart.resize();
        degree.chart.resize();
        seniority.chart.resize();
    });

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        $(".rightSetUpLeft").click();
        reqOrgId = organId;
        objBudgetAnalyse.init();
        objRequestData.init();
        timeLineObj.init(reqOrgId);
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
                title: '管理建议与备忘',
                titleSuffix: '条未读',
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
        }
    }
    timeLineObj.init(reqOrgId);

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    };

    /*
     显示更多备忘
     */
    $("#memo-body-div").mouseenter(function () {
        $("#memo-big-list-more").show();
    });
    $("#memo-body-div").mouseleave(function () {
        $("#memo-big-list-more").hide();
    });

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        var $this = $(this);
        if ($this.hasClass("rightSetUpBtnSelect"))return;
        $this.parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $this.addClass("rightSetUpBtnSelect");
        if ($this.parents(".SetUpBody").attr("view") == "chart") {
            $this.parents(".SetUpBody").find(".table-view").show();
            $this.parents(".SetUpBody").find(".chart-view").hide();
            $this.parents(".SetUpBody").attr("view", "table");

            $(".mCSB_container").css({"left": "0px"});
            $this.parents(".SetUpBody").find(".ct-mCustomScrollBar").mCustomScrollbar("scrollTo", "left");
        } else {
            $this.parents(".SetUpBody").find(".chart-view").show();
            $this.parents(".SetUpBody").find(".table-view").hide();
            $this.parents(".SetUpBody").attr("view", "chart");
        }
    });

    /*
     设置编制使用率
     */
    $(".LSLSetUp").click(function () {
        $("#settingModal").modal("show");
        $.post(urls.structureGetConfigWarnVal, function (data) {
            $("#zdWindowNormal").val(data.normal * 100);
            $("#zdWindowRisk").val(data.risk * 100);
        });
    });

    $("#btnSaveRate").click(function () {
        var normal = $.trim($("#zdWindowNormal").val());
        var risk = $.trim($("#zdWindowRisk").val());
        if (_.isEmpty(normal) || !$.isNumeric(normal) || parseFloat(normal) < 0) {
            $("#zdWindowNormal").focus();
            return;
        }
        if (_.isEmpty(risk) || !$.isNumeric(risk) || parseFloat(risk) < 0) {
            $("#zdWindowRisk").focus();
            return;
        }
        if (parseFloat(normal) >= parseFloat(risk)) {
            var $errsg = $("#settingModal .errmsg");
            $errsg.attr('style', null);
            $errsg.removeClass('hide').fadeOut(3000, function () {
                $errsg.addClass('hide');
            });
            $("#zdWindowNormal").focus();
            return;
        }
        var param = {
            "normal": parseFloat(normal) / 100,
            "risk": parseFloat(risk) / 100
        };
        $.post(urls.structureSetConfigWarnVal, param, function (data) {
            $("#settingModal").modal("hide");
            if (data.type) {
                objBudgetAnalyse.init();

            } else {
                showErrMsg(data.msg)
            }
        });
    });

    /*
     * 编制使用率、编制分析
     * */
    var objBudgetAnalyse = {
        chart: initEChart('structureRateChart'),
        data: {
            value: 0,
            greenValue: 0.95,
            yellowVlue: 1
        },
        textArea: $("#structureRateText"),
        color: ["green", "yellow", "red"],
        text: ["招兵买马，弹药充足！", "人手富足，大展宏图！", "余粮有限，注意节制！"],
        //图表Option
        option: {
            toolbox: {
                show: false
            },
            series: [
                {
                    name: "编制",
                    type: "gauge",
                    data: [
                        {
                            value: 0,
                            name: "使用率"
                        }
                    ],
                    min: 0,
                    max: 100,
                    axisLine: {
                        lineStyle: {
                            color: [[0.95, "rgb(106, 175, 43)"], [1, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]],
                            width: 10
                        },
                        show: true
                    },
                    title: {
                        show: false
                    },
                    detail: {
                        show: false
                    },
                    splitNumber: 5,
                    pointer: {
                        length: "80%",
                        width: 5
                    },
                    radius: "90%",
                    center: ["50%", "55%"],
                    axisTick: {
                        show: true,
                        splitNumber: 2,
                        length: 4
                    },
                    splitLine: {
                        lineStyle: {
                            width: 1
                        },
                        show: true,
                        length: 10
                    }
                }
            ],
            tooltip: {
                show: false
            }
        },
        init: function () {
            var self = this;
            self.request();
        },
        request: function () {
            var self = this;

            $.post(urls.structureBudgetAnalyse, {"organId": reqOrgId}, function (data) {
                if (data.hasBudgetPer) {
                    $("#structureRateNoData").addClass("hide");
                    $("#structureRateChart,#structureRateText").show();
                    self.data.value = (data.budgetPer * 100).toFixed(2);
                    self.data.greenValue = data.normal * 100;
                    self.data.yellowVlue = data.risk * 100;
                    self.renderChart();
                } else {
                    $("#structureRateChart,#structureRateText").hide();
                    $("#structureRateNoData").removeClass("hide");
                }
                $("#talentStructureTable .number").text(data.hasBudgetPer ? data.number : "-");
                $("#talentStructureTable .empCount").text(data.empCount);
                $("#talentStructureTable .usableEmpCount").text(data.hasBudgetPer ? data.usableEmpCount : "-");
            });
        },
        renderChart: function () {
            var self = this;
            //图表
            var maxValue = self.data.value > 100 ? parseInt(self.data.value / 10) * 10 + 10 : 100;
            self.option.series[0].data[0].value = self.data.value;
            self.option.series[0].max = maxValue;
            var g = self.data.greenValue / maxValue;
            var y = self.data.yellowVlue / maxValue;
            if (g > 1) {
                self.data.greenValue = self.data.yellowVlue = 1;
            } else {
                self.data.greenValue = g;
                self.data.yellowVlue = y > 1 ? 1 : y;
            }
            self.option.series[0].axisLine.lineStyle.color = [[self.data.greenValue, "rgb(106, 175, 43)"], [self.data.yellowVlue, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]];
            self.chart.clear();
            self.chart.setOption(self.option);
            self.chart.refresh();

            //文字说明
            var index = 2;
            if (self.data.value <= self.data.greenValue * 100) {
                index = 0;
            } else if (self.data.value <= self.data.yellowVlue * 100) {
                index = 1;
            }
            self.textArea.removeClass(self.color[0]).removeClass(self.color[1]).removeClass(self.color[2]).addClass(self.color[index]);
            self.textArea.find(".rate").text(self.data.value + "%");
            self.textArea.find(".text").text(self.text[index]);
        }
    };
    objBudgetAnalyse.init();

    var optionPie = {
        tooltip: {
            show: false
        },
        toolbox: {
            show: false
        },
        calculable: false,
        series: [
            {
                clickable: false,
                type: "pie",
                radius: "70%",
                center: ["50%", "50%"],
                data: [],
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            }
        ],
        color: Tc.defaultPieColor
    };
    /*
     * 管理者员工分布
     * */
    var objManagersEmployee = {
        chart: initEChart('managerChart'),
        option: optionPie,
        init: function (data) {
            if (data == undefined) {
                console.info("管理者员工分布出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var manager = 0, employee = 0, i = 0;
                $.each(data, function (n, v) {
                    if (n == "员工") {
                        employee += v;
                    } else {
                        manager += v;
                    }
                    if (v != 0) {
                        lis.push({"name": n, "value": v});
                        legend.push('<div><label style="background-color: ' + Tc.defaultPieColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                        i++;
                    }
                });

                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();

                $("#managerText").removeClass("hide").find(".legend").html(legend.join(""));
                $("#managerText table");
                var rate = manager + ':' + employee;
                if (manager != 0 && employee != 0) {
                    rate = "1 : " + Tc.formatFloat(employee / manager, 0);
                }
                $("#managerText .proportion").text(rate);
            }
        }
    };

    /*
     * 职级分布
     * */
    var objRank = {
        chart: initEChart('rankChart'),
        option: optionPie,
        init: function (data) {
            if (data == undefined) {
                console.info("职级分布出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function (n, v) {
                    if (v != 0) {
                        lis.push({"name": n, "value": v});
                        legend.push('<div><label style="background-color: ' + Tc.defaultPieColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                        i++;
                    }
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();

                $("#rankText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        }
    };

    var isReturn = false;
    /*
     * 职位序列分布 序列分布
     * */
    var objPositionSequence = {
        chart: initEChart('positionSequenceChart'),
        option: {
            title: {
                text: "职位序列分布",
                x: "center",
                y: 20
            },
            tooltip: {
                show: false
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
            xAxis: [
                {
                    type: "value",
                    boundaryGap: [0, 0.01],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: "rgb(169, 170, 170)"
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    name: "职位序列分布",
                    type: "bar",
                    data: [],
                    tooltip: false,
                    barWidth: 30,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: function (i) {
                                    var rate = i.seriesName > 0 ? (i.value * 100 / i.seriesName).toFixed(2) : 0;
                                    return i.value + "人，" + rate + "%";
                                },
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    }
                }
            ],
            grid: {
                borderColor: "#ffffff",
                x2: 120
            },
            color: Tc.defaultBarColor
        },
        init: function (data) {
            var self = this;
            if (data == undefined) {
                console.info("职位序列分布序列分布出错了！");
            } else {
                var yaxisData = [], seriesData = [];
                var total = 0;
                $.each(data, function (n, v) {
                    var cateName = n;
                    var seqId = "";
                    var arr = n.split(',');
                    if (arr.length > 1) {
                        cateName = arr[1];
                        seqId = arr[0];
                    }
                    seriesData.push({"value": v, "seqId": seqId, "cateName": cateName});
                    total += v;
                });
                seriesData = _.sortBy(seriesData, "value");
                $.each(seriesData, function (i, item) {
                    yaxisData.push(item.cateName);
                });
                self.option.series[0].name = total;
                self.option.yAxis[0].data = yaxisData;
                self.option.series[0].data = seriesData;

                if (self.chart != null)
                    self.chart.dispose();
                self.chart = initEChart('positionSequenceChart');

                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();

                self.chart.on(ecConfig.EVENT.CLICK, self.eConsole);
            }
        },
        //点击事件
        eConsole: function (param) {
            var self = this;
            if (!isReturn) {//钻取数据
                isReturn = true;
                var seqId = param.data.seqId;
                if (seqId == "") {
                    showErrMsg("其他序列不能钻取！");
                } else {
                    var obj = {};
                    var name = (param.data.seqId == "" ? "" : param.data.seqId + ",") + param.name;
                    obj[name] = param.value;
                    objPositionSequence.init(obj);

                    $.post(urls.structureGetAbEmpCountBarBySeqId, {
                        "organId": reqOrgId,
                        "seqId": seqId
                    }, function (data) {
                        objPositionRank.init(data);
                    });
                }
            } else {//返回上一级
                isReturn = false;
                var dataRowBar = objRequestData.data.seqEmpCountBar;
                objPositionSequence.init(dataRowBar);

                var dataBar = objRequestData.data.abilityCurtEmpCountPie;
                objPositionRank.init(dataBar);
            }
        }
    }

    /*
     * 职位序列分布 职级分布
     * */
    var objPositionRank = {
        chart: initEChart('positionRankChart'),
        option: {
            title: {
                text: "职级分布",
                x: "center",
                y: 20,
                textAlign: "center"
            },
            tooltip: {
                show: false
            },
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
                        textStyle: {
                            color: "#0000",
                            fontSize: 13
                        },
                        interval: 0
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    axisLabel: {
                        formatter: "{value}%",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: "dashed",
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            series: [
                {
                    clickable: false,
                    name: "职级",
                    type: "bar",
                    data: [],
                    barWidth: 30,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: '{c}%',
                                textStyle: {
                                    color: "#000"
                                }
                            }
                        }
                    }
                }
            ],
            color: Tc.defaultBarColor,
            grid: {
                borderColor: "#fff",
                y: 80,
                x2: 30
            }
        },
        init: function (data) {
            if (data == undefined) {
                console.info("职位序列分布职级分布出错了！");
            } else {
                var self = this;
                var xaxisData = [], seriesData = [];
                var total = 0;
                $.each(data, function (n, v) {
                    total += v;
                });
                $.each(data, function (n, v) {
                    xaxisData.push(n + "\n" + v + "人");
                    seriesData.push((v * 100 / total).toFixed(2));
                });
                self.option.series[0].name = total;
                self.option.xAxis[0].data = xaxisData;
                self.option.series[0].data = seriesData;

                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
            }
        }
    }

    /*
     * 组织分布
     * */
    var organDistribution = {
        chart: initEChart("organDistributionChart"),
        option: {
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
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#000',
                            width: 1
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "#000",
                            fontSize: 13
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    name: '(人数)',
                    axisLine: {
                        lineStyle: {
                            color: '#000',
                            width: 1
                        }
                    },
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
                    },
                    barWidth: 30
                }
            ],
            color: Tc.defaultBarColor,
            grid: {
                borderColor: "#ffffff",
                x: 55,
                y: 25,
                x2: 15,
                borderWidth: 0
            },
            dataZoom: {
                show: true,
                realtime: true,
                height: 20,
                start: 0,
                end: 30,
                showDetail: false
            }
        },
        init: function (data) {
            var self = this;
            if (data == undefined) {
                console.info("组织分布出错了！");
            } else {
                var xAxisData = [], seriesData = [];
                var tem = [];
                $.each(data, function (n, v) {
                    tem.push({"name": n, "value": -v});
                });
                tem = _.sortBy(tem, "value");
                $.each(tem, function (i, item) {
                    xAxisData.push(item.name);
                    seriesData.push(-item.value);
                });
                if (xAxisData.length > 0) {
                    var num = 3;//3个以上显示滚动条
                    if (xAxisData.length > num) {
                        self.option.dataZoom = {
                            show: true,
                            realtime: true,
                            height: 20,
                            start: 0,
                            end: 30,
                            showDetail: false
                        };
                    } else {
                        self.option.dataZoom = {}
                    }
                    self.chart.clear();
                    self.option.xAxis[0].data = xAxisData;
                    self.option.series[0].data = seriesData;
                    self.chart.setOption(self.option);
                    self.chart.refresh();
                } else {
                    if (self.chart)
                        self.chart.dispose();
                    $("#organDistributionChart").html("<div class='loadingmessage'>暂无数据</div>");
                }
            }
        }
    };

    /**
     * 工作地点分布
     */
    var workLocation = {
        chart: initEChart("workLocationChart"),
        option: {
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
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#000',
                            width: 1
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "#000",
                            fontSize: 13
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    name: '(人数)',
                    axisLine: {
                        lineStyle: {
                            color: '#000',
                            width: 1
                        }
                    },
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
                    },
                    barWidth: 30
                }
            ],
            color: Tc.defaultBarColor,
            grid: {
                borderColor: "#ffffff",
                x: 55,
                y: 25,
                x2: 15,
                borderWidth: 0
            }
        },
        init: function (data) {
            var self = this;
            if (data == undefined) {
                console.info("工作地点分布出错了！");
            } else {
                var lis = [], xAxisData = [], seriesData = [];
                $.each(data, function (n, v) {
                    lis.push({"name": n, "value": -v});
                });
                lis = _.sortBy(lis, "value");
                $.each(lis, function (i, item) {
                    xAxisData.push(item.name);
                    seriesData.push(-item.value);
                })
                self.chart.clear();
                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData;
                self.chart.setOption(self.option);
                self.chart.refresh();
            }
        }
    };
    /**
     * 学历分布
     */
    var degree = {
        chart: initEChart("degreeChart"),
        table: null,
        option: optionPie,
        init: function (chartData, tableData) {
            this.initChart(chartData);
        }, initChart: function (data) {
            var self = this;
            if (data == undefined) {
                console.info("学历分布出错了！");
            } else {
                var lis = [], legend = [], i = 0;
                $.each(data, function (n, v) {
                    if (v > 0) {
                        var sort = 0;
                        switch (n) {
                            case "博士": {
                                sort = 4;
                                break;
                            }
                            case "硕士": {
                                sort = 3;
                                break;
                            }
                            case "本科": {
                                sort = 2;
                                break;
                            }
                            case "大专": {
                                sort = 1;
                                break;
                            }
                        }
                        lis.push({"name": n, "value": v, "sort": sort});
                        i++;
                    }
                });
                var lis = _.sortBy(lis, "sort");
                var seriesData = [];
                $.each(lis, function (i, item) {
                    seriesData.push(item);
                    legend.push('<div><label style="background-color: ' + Tc.defaultPieColor[i] + ';"></label><span>' + item.name + ' ' + item.value + '人</span></div>');
                })

                self.option.series[0].data = seriesData;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
                $("#degreelegend").removeClass("hide").find(".legend").html(legend.join(""));
            }
        }
    };
    /**
     * 司龄分布
     */
    var seniority = {
        chart: initEChart("seniorityChart"),
        table: null,
        tableId: "seniorityTable",
        option: optionPie,
        init: function (chartData, tableData) {
            this.initChart(chartData);
            this.initTable(tableData);
        }, initChart: function (data) {
            var self = this;
            if (data == undefined) {
                console.info("司龄分布出错了！");
            } else {
                var lis = [], legend = [], i = 0;
                $.each(data, function (n, v) {
                    if (v > 0) {
                        lis.push({"name": n, "value": v});
                        legend.push('<div><label style="background-color: ' + Tc.defaultPieColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                        i++;
                    }
                });

                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
                $("#senioritylegend").removeClass("hide").find(".legend").html(legend.join(""));
            }
        }, initTable: function (data) {
            //tableId
        }
    };
    /*
     * 人力结构请求数据
     * */
    var objRequestData = {
        data: null,
        init: function () {
            var self = this;
            $.post(urls.structureGetTalentStuctureData, {"organId": reqOrgId}, function (data) {
                self.data = data;
                console.log(data);
                self.manager();
                self.rank();
                self.positionSequence();
                self.positionRank();
                self.workLocation();
                self.organDistribution();
                self.degree();
                self.seniority();
            });
        },
        //管理者员工分布
        manager: function () {
            var self = this;
            //饼图
            var dataPie = self.data.abilityEmpCountPie;
            objManagersEmployee.init(dataPie);

            //数据表
            var dataTable = self.data.abilityEmpCount ? self.data.abilityEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    if (y !== 0)
                        names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var managerTotal = 0, employeeTotal = 0;
            var objTotal = {'下级组织': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                var manager = 0, employee = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    if (y == '员工') {
                        employee = num;
                        employeeTotal += num;
                    } else {
                        manager += num;
                        managerTotal += num;
                        obj.push(num);
                        objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                    }
                });
                obj.push(manager);
                obj.push(employee);
                var rate = "";
                if (manager > 0 && employee > 0) {
                    rate = "1:" + (employee / manager).toFixed(1);
                } else if (manager == 0 || employee == 0) {
                    rate = manager + ":" + employee;
                }
                obj.push(rate);
                lists.push(obj);
            });
            objTotal['管理者合计'] = managerTotal;
            objTotal['员工'] = employeeTotal;
            var rate = "";
            if (managerTotal > 0 && employeeTotal > 0) {
                rate = "1:" + (employeeTotal / managerTotal).toFixed(1);
            } else if (managerTotal == 0 || employeeTotal == 0) {
                rate = managerTotal + ":" + employeeTotal;
            }
            objTotal['管理者员工比例'] = rate;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#managerTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#managerTable tbody").html(rowbody.join(''));

            //样式
            $("#managerTable tbody tr").first().addClass("total");
            $("#managerTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        //职级
        rank: function () {
            var self = this;
            //饼图
            var dataPie = self.data.abilityCurtEmpCountPie;
            objRank.init(dataPie);

            //数据表
            var dataTable = self.data.abilityCurtEmpCount ? self.data.abilityCurtEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    if (y != 0)
                        names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var totals = 0;
            var objTotal = {'下级组织': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                var total = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    total += num;
                    totals += num;
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                obj.push(total);
                lists.push(obj);
            });
            objTotal['合计'] = totals;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#rankTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#rankTable tbody").html(rowbody.join(''));

            //样式
            $("#rankTable tbody tr").first().addClass("total");
            $("#rankTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        //职位序列分布 序列分布
        positionSequence: function () {
            var self = this;
            //条形图
            var dataRowBar = self.data.seqEmpCountBar;
            objPositionSequence.init(dataRowBar);

            //数据表
            var dataTable = self.data.seqEmpCount ? self.data.seqEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var totals = 0;
            var objTotal = {'职级': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                var total = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    total += num;
                    totals += num;
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                obj.push(total);
                lists.push(obj);
            });
            objTotal['合计'] = totals;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#positionSequenceTable thead").html(rowheader.join(''));

            //body
            var rowbody = [];
            $.each(lists, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#positionSequenceTable tbody").html(rowbody.join(''));

            $("#positionSequenceTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        //职位序列分布 职级分布
        positionRank: function () {
            var self = this;
            //柱形图
            var dataBar = self.data.abilityCurtEmpCountPie;
            objPositionRank.init(dataBar);
        },//工作地点
        workLocation: function () {
            var self = this;
            //柱形图
            var dataBar = self.data.workplaceEmpCount;
            workLocation.init(dataBar);
        },//组织分布
        organDistribution: function () {
            var self = this;
            //柱形图
            var dataBar = self.data.organEmpCount ? self.data.organEmpCount : [];
            organDistribution.init(dataBar);
        },//学历分布
        degree: function () {
            var self = this;
            //柱形图
            var dataBar = self.data.degreeEmpCountPie;
            var degreeEmp = self.data.degreeEmpCount;
            degree.init(dataBar, degreeEmp);

            //数据表
            var dataTable = self.data.degreeEmpCount ? self.data.degreeEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    names.push(x);
                });
            });
            names = _.uniq(names);
            var nv = [];
            $.each(names, function (i, item) {
                var sort = 0;
                switch (item) {
                    case "博士": {
                        sort = 4;
                        break;
                    }
                    case "硕士": {
                        sort = 3;
                        break;
                    }
                    case "本科": {
                        sort = 2;
                        break;
                    }
                    case "大专": {
                        sort = 1;
                        break;
                    }
                }
                nv.push({"name": item, "sort": sort});
            });
            nv = _.sortBy(nv, "sort")
            names = [];
            $.each(nv, function (n, v) {
                names.push(v.name)
            });
            var lists = [];
            var objTotal = {'下级组织': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                lists.push(obj);
            });

            //合计为零列去除
            var zeroCols = [];
            var cols = 1;
            $.each(objTotal, function (n, v) {
                if (v == 0) {
                    zeroCols.push(cols);
                }
                cols++;
            });

            //header
            var rowheader = ['<tr>']
            cols = 1;
            $.each(objTotal, function (n, v) {
                if (!_.contains(zeroCols, cols)) {
                    rowheader.push('<th>' + n + '</th>');
                }
                cols++;
            });
            $("#degreeTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                cols = 1;
                $.each(item, function (j, value) {
                    if (!_.contains(zeroCols, cols)) {
                        rowbody.push('<td>' + value + '</td>');
                    }
                    cols++;
                });
                rowbody.push("</tr>");
            });
            $("#degreeTable tbody").html(rowbody.join(''));

            //样式
            $("#degreeTable tbody tr").first().addClass("total");
            $("#degreeTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },//司龄分布
        seniority: function () {
            var self = this;
            //柱形图
            var dataBar = self.data.companyAgeEmpCountPie;
            var seniorityEmp = self.data.companyAgeEmpCount;
            seniority.init(dataBar, seniorityEmp);

            //数据表
            var dataTable = self.data.companyAgeEmpCount ? self.data.companyAgeEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var objTotal = {'下级组织': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                lists.push(obj);
            });

            //合计为零列去除
            var zeroCols = [];
            var cols = 1;
            $.each(objTotal, function (n, v) {
                if (v == 0) {
                    zeroCols.push(cols);
                }
                cols++;
            });

            //header
            var rowheader = ['<tr>']
            cols = 1;
            $.each(objTotal, function (n, v) {
                if (!_.contains(zeroCols, cols)) {
                    rowheader.push('<th>' + n + '</th>');
                }
                cols++;
            });
            $("#seniorityTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                cols = 1;
                $.each(item, function (j, value) {
                    if (!_.contains(zeroCols, cols)) {
                        rowbody.push('<td>' + value + '</td>');
                    }
                    cols++;
                });
                rowbody.push("</tr>");
            });
            $("#seniorityTable tbody").html(rowbody.join(''));

            //样式
            $("#seniorityTable tbody tr").first().addClass("total");
            $("#seniorityTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        }
    }
    objRequestData.init();



});