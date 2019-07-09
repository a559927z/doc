require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/pie', 'echarts/chart/gauge', 'jgGrid', 'bootstrap',
    'underscore', 'utils', 'organTreeSelector', 'vernierCursor', 'timeLine2', 'searchBox3', "jquery-mCustomScrollBar", 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;

    var urls = {
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        structureBudgetAnalyse: webRoot + '/talentStructure/getBudgetAnalyse.do', //编制分析
        structureGetConfigWarnVal: webRoot + '/talentStructure/getConfigWarnVal.do',  //加载使用率
        structureSetConfigWarnVal: webRoot + '/talentStructure/setConfigWarnVal.do',  //保存使用率
        // structureGetTalentStuctureData: webRoot + '/talentStructure/getTalentStuctureData.do',  //人力结构数据
        // structureGetAbEmpCountBarBySeqId: webRoot + '/talentStructure/getAbEmpCountBarBySeqId.do',  //职位序列分布 职级分布

        getManageEmp4OrganUrl: webRoot + '/talentStructure/getManageEmp4Organ.do',      //当前机构管理者员工分布
        getManageToSubOrganUrl: webRoot + '/talentStructure/getManageToSubOrgan.do',  //下级机构管理者员工分布
        getAbility4OrganUrl: webRoot + '/talentStructure/getAbility4Organ.do',      //当前机构职级分布
        getAbilityToSubOrganUrl: webRoot + '/talentStructure/getAbilityToSubOrgan.do',  //下级机构职级分布
        getSequence4OrganUrl: webRoot + '/talentStructure/getSequence4Organ.do',  //职位序列分布
        getSequenceAbilityCountUrl: webRoot + '/talentStructure/getSequenceAbilityCount.do',  //职位序列职级统计
        getSubOrganCountUrl: webRoot + '/talentStructure/getSubOrganCount.do',  //组织分布
        getWorkplaceCountUrl: webRoot + '/talentStructure/getWorkplaceCount.do',  //工作地分布
        getDegree4OrganUrl: webRoot + '/talentStructure/getDegree4Organ.do',      //当前机构学历分布
        getDegreeToSubOrganUrl: webRoot + '/talentStructure/getDegreeToSubOrgan.do',  //下级机构学历分布
        getCompanyAge4OrganUrl: webRoot + '/talentStructure/getCompanyAge4Organ.do',      //当前机构司龄分布
        getCompanyAgeToSubOrganUrl: webRoot + '/talentStructure/getCompanyAgeToSubOrgan.do',  //下级机构司龄分布
    };
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();

    var reqOrgId = win.currOrganId;

    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    };

    $(".ct-mCustomScrollBar").mCustomScrollbar({
        axis: "yx",
        scrollButtons: {enable: true},
        scrollbarPosition: "outside",
        theme: "minimal-dark"
    });

    /**
     * 重新加载表格
     * @param gridId
     */
    function resizeGrid(gridId) {
        var $this = $('#gbox_' + gridId.split('#')[1]);
        var thColumn = $this.find('table.ui-jqgrid-htable th.ui-th-column');
        var w = $this.parent().width() > thColumn.length * 100 ? $this.parent().width() - 2 : thColumn.length * 100;
        $(gridId).setGridWidth(w);
    }

    function formatNum(value) {
        if (!value) return 0;
        return value;
    }

    $(window).resize(function () {
        budgetAnalyseObj.chartObj.resize();
        managersEmpObj.chartObj.resize();
        rankObj.chartObj.resize();
        degreeObj.chartObj.resize();
        compangAgeObj.chartObj.resize();
        organDistributionObj.chartObj.resize();
        workplaceObj.chartObj.resize();

        sequenceObj.chartObj.resize();
        positionRankObj.chartObj.resize();
    });

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        $(".rightSetUpLeft").click();
        reqOrgId = organId;
        budgetAnalyseObj.init();
        timeLineObj.init(reqOrgId);

        managersEmpObj.init();
        rankObj.init();
        degreeObj.init();
        compangAgeObj.init();
        organDistributionObj.init();
        workplaceObj.init();

        sequenceObj.init();
        positionRankObj.init();
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
        var $parents = $this.parents(".SetUpBody");
        if ($parents.attr("view") == "chart") {
            $parents.find(".table-view").show();
            $parents.find(".chart-view").hide();
            $parents.attr("view", "table");

            $(".mCSB_container").css({"left": "0px"});

            managersEmpObj.initGrid(reqOrgId);
            rankObj.initGrid(reqOrgId);
            degreeObj.initGrid(reqOrgId);
            compangAgeObj.initGrid(reqOrgId);
            sequenceObj.initGrid(reqOrgId);
        } else {
            $parents.find(".chart-view").show();
            $parents.find(".table-view").hide();
            $parents.attr("view", "chart");
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
            if (!data.type) {
                showErrMsg(data.msg);
                return;
            }
            budgetAnalyseObj.init();
        });
    });

    /*
     * 编制使用率、编制分析
     * */
    var budgetAnalyseObj = {
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
                    self.data.value = Tc.formatFloat(data.budgetPer * 100);
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
    budgetAnalyseObj.init();

    var optionPie = {
        toolbox: {
            show: false
        },
        tooltip: {
            show: true
        },
        legend: {
            y: 'bottom',
            itemWidth: 15,
            itemHeight: 15,
            data: []
        },
        calculable: false,
        series: [
            {
                clickable: false,
                type: "pie",
                radius: "60%",
                data: []
            }
        ],
        color: Tc.defaultPieColor
    };

    var gridOption = {
        data: [],
        datatype: "local",
        autowidth: true,
        height: 303,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: [],
        colModel: [
            // {name: 'strQuarter', index: 'strQuarter', width: 120, sortable: false},
            // {
            //     name: 'rate',
            //     index: 'rate',
            //     width: 120,
            //     sortable: false,
            //     align: 'center',
            //     formatter: formatPercentage
            // },
            // {
            //     name: 'yearRate',
            //     index: 'yearRate',
            //     width: 120,
            //     sortable: false,
            //     align: 'center',
            //     formatter: formatPercentage
            // },
            // {
            //     name: 'parentRate',
            //     index: 'parentRate',
            //     width: 150,
            //     fixed: true,
            //     sortable: false,
            //     align: 'center',
            //     formatter: formatPercentage
            // }
        ],
        //rownumbers: true,
        //rownumWidth: 40,
        scroll: true
    }
    /*
     * 管理者员工分布
     * */
    var managersEmpObj = {
        chartId: 'managerChart',
        chartObj: initEChart('managerChart'),
        gridObj: null,
        gridId: '#managerGrid',
        init: function () {
            var self = this;
            self.initChart(reqOrgId);
            self.initGrid(reqOrgId);
        },
        initGrid: function (organId) {
            var self = this;
            if (self.gridOrganId == organId) {
                resizeGrid(self.gridId);
                return;
            }
            $.get(urls.getManageToSubOrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.rendergGrid(data);
                self.gridOrganId = organId;
            });
        },
        initChart: function (organId) {
            var self = this;
            if (self.chartOrganId == organId) self.chartObj.resize();
            $.get(urls.getManageEmp4OrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.chartOrganId = organId;
            });

        },
        render: function (data) {
            var self = this, lis = [], legend = [];
            $.each(data, function (idx, item) {
                if (idx == data.length - 1) return true;

                lis.push({name: item.k, value: item.v});
                legend.push({name: item.k, icon: "bar"});
            });
            var option = $.extend(true, {}, optionPie);
            option.series[0].data = lis;
            option.legend.data = legend;
            self.chartObj.clear();
            self.chartObj.setOption(option);

            $("#managerChartTxt").text(data[data.length - 1].v);
        },
        rendergGrid: function (datas) {
            var self = this, names = [], modals = [];

            $.each(datas.modals, function (idx, m) {
                names.push(m.v);
                var obj = {name: m.k, sortable: false, formatter: formatNum};
                if(m.k == -999){
                    obj.width = 200;
                }
                modals.push(obj);
            });

            if (_.isNull(self.gridObj)) {
                var option = $.extend(true, {}, gridOption);
                option.colNames = names;
                option.colModel = modals;
                option.data = datas.data;
                self.gridObj = $(self.gridId).jqGrid(option);
                resizeGrid(self.gridId);
                return;
            }

            $(self.gridId).clearGridData().setGridParam({
                colNames: names,
                colModel: modals,
                data: datas.data
            }).trigger("reloadGrid");
        }
    };
    managersEmpObj.init();


    /*
     * 职级分布
     * */
    var rankObj = {
        chartObj: initEChart('rankChart'),
        chartId: 'rankChart',
        gridObj: null,
        gridId: '#rankGrid',
        init: function () {
            var self = this;
            self.initChart(reqOrgId);
            self.initGrid(reqOrgId);
        },
        initGrid: function (organId) {
            var self = this;
            if (self.gridOrganId == organId) {
                resizeGrid(self.gridId);
                return;
            }
            $.get(urls.getAbilityToSubOrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.rendergGrid(data);
                self.gridOrganId = organId;
            });
        },
        initChart: function (organId) {
            var self = this;
            if (self.chartOrganId == organId) self.chartObj.resize();
            $.get(urls.getAbility4OrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.chartOrganId = organId;
            });

        },
        render: function (data) {
            var self = this, lis = [], legend = [];
            $.each(data, function (idx, item) {

                lis.push({name: item.k, value: item.v});
                legend.push({name: item.k, icon: "bar"});
            });
            var option = $.extend(true, {}, optionPie);
            option.series[0].data = lis;
            option.legend.data = legend;
            self.chartObj.clear();
            self.chartObj.setOption(option);
        },
        rendergGrid: function (datas) {
            var self = this, names = [], modals = [];

            $.each(datas.modals, function (idx, m) {
                names.push(isNaN(m.v) ? m.v : m.v + '级');
                modals.push({name: m.k, sortable: false, formatter: formatNum});
            });
            if (_.isNull(self.gridObj)) {
                var option = $.extend(true, {}, gridOption);
                option.colNames = names;
                option.colModel = modals;
                option.data = datas.data;
                self.gridObj = $(self.gridId).jqGrid(option);
                resizeGrid(self.gridId);
                return;
            }

            $(self.gridId).clearGridData().setGridParam({
                colNames: names,
                colModel: modals,
                data: datas.data
            }).trigger("reloadGrid");
        }
    };
    rankObj.init();


    var isReturn = false;
    /*
     * 职位序列分布 序列分布
     * */
    var sequenceObj = {
        chartObj: initEChart('positionSequenceChart'),
        chartId: 'positionSequenceChart',
        gridObj: null,
        gridId: '#positionSequenceGrid',
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
                                    var rate = i.seriesName > 0 ? Tc.formatFloat(i.value * 100 / i.seriesName) : 0;
                                    return i.value + "人，" + rate + "%";
                                },
                                textStyle: {
                                    color: "#000"
                                }
                            }
                        }
                    }
                }
            ],
            grid: {
                borderColor: "#fff",
                x2: 120
            },
            color: Tc.defaultBarColor
        },
        init: function () {
            var self = this;
            self.requestData(reqOrgId);
            self.initGrid(reqOrgId);
            self.chartObj.on('click', self.eConsole);
        },
        initGrid: function (organId) {
            var self = this;
            if (self.gridOrganId == organId) {
                resizeGrid(self.gridId);
                return;
            }
            $.get(urls.getSequenceAbilityCountUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.rendergGrid(data);
                self.gridOrganId = organId;
            });
        },
        requestData: function (organId) {
            var self = this;
            if (self.organId == organId) self.chartObj.resize();
            $.get(urls.getSequence4OrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.chartData = data;
                self.organId = organId;
            });
        },
        render: function (data) {
            var self = this, yaxisData = [], seriesData = [];
            var total = 0;
            $.each(data, function (idx, obj) {
                yaxisData.push(obj.itemName);
                seriesData.push({value: obj.empCount, seqId: obj.itemId, cateName: obj.itemName});
                total += obj.empCount;
            });

            var option = $.extend(true, {}, self.option);
            option.series[0].name = total;
            option.yAxis[0].data = yaxisData;
            option.series[0].data = seriesData;

            self.chartObj.clear();
            self.chartObj.setOption(option);
        },
        eConsole: function (param) {    //点击事件
            if (!isReturn) {    //钻取数据
                isReturn = true;
                var seqId = param.data.seqId;
                if (seqId == "-1") {
                    showErrMsg("其它序列不能钻取！");
                    return;
                }
                var obj = [];
                obj.push({empCount: param.value, itemId: seqId, itemName: param.name});
                sequenceObj.render(obj);

                positionRankObj.requestData(reqOrgId, seqId);
            } else {//返回上一级
                isReturn = false;
                sequenceObj.render(sequenceObj.chartData);

                positionRankObj.requestData(reqOrgId);
            }
        },
        rendergGrid: function (datas) {
            var self = this, names = [], modals = [];

            $.each(datas.modals, function (idx, m) {
                names.push(m.v);
                modals.push({name: m.k, sortable: false, formatter: formatNum});
            });

            if (_.isNull(self.gridObj)) {
                var option = $.extend(true, {}, gridOption);
                option.colNames = names;
                option.colModel = modals;
                option.data = datas.data;
                self.gridObj = $(self.gridId).jqGrid(option);
                resizeGrid(self.gridId);
                return;
            }

            $(self.gridId).clearGridData().setGridParam({
                colNames: names,
                colModel: modals,
                data: datas.data
            }).trigger("reloadGrid");
        }
    }
    sequenceObj.init();

    /*
     * 职位序列分布 职级分布
     * */
    var positionRankObj = {
        chartObj: initEChart('positionRankChart'),
        chartId: 'positionRankChart',
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
        init: function () {
            var self = this;
            self.requestData(reqOrgId);
        },
        requestData: function (organId, sequenceId) {
            var self = this;
            if (self.organId == organId) self.chartObj.resize();
            $.get(urls.getAbility4OrganUrl, {organId: organId, sequenceId: sequenceId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.chartData = data;
                self.organId = organId;
            });
        },
        render: function (data) {
            var self = this, xAxisData = [], seriesData = [];
            var total = 0;
            $.each(data, function (idx, obj) {
                xAxisData.push(obj.k + "\n" + obj.v + "人");
                total += parseInt(obj.v);
            });
            $.each(data, function (idx, obj) {
                seriesData.push(Tc.formatFloat(parseInt(obj.v) / total * 100));
            });

            var option = $.extend(true, {}, self.option);
            option.series[0].name = total;
            option.xAxis[0].data = xAxisData;
            option.series[0].data = seriesData;

            self.chartObj.clear();
            self.chartObj.setOption(option);
        },
    }
    positionRankObj.init();

    var barOption = {
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
                        color: '#666',
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
                        color: '#666',
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
                        color: "#e4e4e4"
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
            height: 30,
            start: 0,
            end: 30,
            showDetail: false
        }
    }

    /*
     * 组织分布
     * */
    var organDistributionObj = {
        chartObj: initEChart('organDistributionChart'),
        chartId: 'organDistributionChart',
        init: function () {
            var self = this;
            self.requestData(reqOrgId);
        },
        requestData: function (organId) {
            var self = this;
            if (self.organId == organId) self.chartObj.resize();
            $.get(urls.getSubOrganCountUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.organId = organId;
            });
        },
        render: function (data) {
            var self = this, lis = [], xAxis = [];
            $.each(data, function (idx, item) {

                lis.push(item.v);
                xAxis.push(item.k);
            });
            var option = $.extend(true, {}, barOption);
            var num = 6;    //6个以上显示滚动条
            if (xAxis.length > num) {
                option.dataZoom = {
                    show: true,
                    realtime: true,
                    height: 30,
                    start: 0,
                    end: 30,
                    showDetail: false
                };
            } else {
                option.dataZoom = {}
            }
            option.series[0].data = lis;
            option.xAxis[0].data = xAxis;
            self.chartObj.clear();
            self.chartObj.setOption(option);
        }
    };
    organDistributionObj.init();

    /**
     * 工作地点分布
     */
    var workplaceObj = {
        chartObj: initEChart('workplaceChart'),
        chartId: 'workplaceChart',
        init: function () {
            var self = this;
            self.requestData(reqOrgId);
        },
        requestData: function (organId) {
            var self = this;
            if (self.organId == organId) self.chartObj.resize();
            $.get(urls.getWorkplaceCountUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.organId = organId;
            });
        },
        render: function (data) {
            var self = this, lis = [], xAxis = [];
            $.each(data, function (idx, item) {

                lis.push(item.v);
                xAxis.push(item.k);
            });
            var option = $.extend(true, {}, barOption);
            var num = 6;    //6个以上显示滚动条
            if (xAxis.length > num) {
                option.dataZoom = {
                    show: true,
                    realtime: true,
                    height: 30,
                    start: 0,
                    end: 30,
                    showDetail: false
                };
            } else {
                option.dataZoom = {}
            }
            option.series[0].data = lis;
            option.xAxis[0].data = xAxis;
            self.chartObj.clear();
            self.chartObj.setOption(option);
        }
    };
    workplaceObj.init();

    /**
     * 学历分布
     */
    var degreeObj = {
        chartObj: initEChart('degreeChart'),
        chartId: 'degreeChart',
        gridObj: null,
        gridId: '#degreeGrid',
        init: function () {
            var self = this;
            self.initChart(reqOrgId);
            self.initGrid(reqOrgId);
        },
        initGrid: function (organId) {
            var self = this;
            if (self.gridOrganId == organId) {
                resizeGrid(self.gridId);
                return;
            }
            $.get(urls.getDegreeToSubOrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.rendergGrid(data);
                self.gridOrganId = organId;
            });
        },
        initChart: function (organId) {
            var self = this;
            if (self.chartOrganId == organId) self.chartObj.resize();
            $.get(urls.getDegree4OrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.chartOrganId = organId;
            });

        },
        render: function (data) {
            var self = this, lis = [], legend = [];
            $.each(data, function (idx, item) {

                lis.push({name: item.k, value: item.v});
                legend.push({name: item.k, icon: "bar"});
            });
            var option = $.extend(true, {}, optionPie);
            option.series[0].data = lis;
            option.legend.data = legend;
            self.chartObj.clear();
            self.chartObj.setOption(option);
        },
        rendergGrid: function (datas) {
            var self = this, names = [], modals = [];

            $.each(datas.modals, function (idx, m) {
                names.push(isNaN(m.v) ? m.v : m.v + '级');
                modals.push({name: m.k, sortable: false, formatter: formatNum});
            });

            if (_.isNull(self.gridObj)) {
                var option = $.extend(true, {}, gridOption);
                option.colNames = names;
                option.colModel = modals;
                option.data = datas.data;
                self.gridObj = $(self.gridId).jqGrid(option);
                resizeGrid(self.gridId);
                return;
            }

            $(self.gridId).clearGridData().setGridParam({
                colNames: names,
                colModel: modals,
                data: datas.data
            }).trigger("reloadGrid");
        }
    };
    degreeObj.init();

    /**
     * 司龄分布
     */
    var compangAgeObj = {
        chartObj: initEChart('compangAgeChart'),
        chartId: 'compangAgeChart',
        gridObj: null,
        gridId: '#compangAgeGrid',
        init: function () {
            var self = this;
            self.initChart(reqOrgId);
            self.initGrid(reqOrgId);
        },
        initGrid: function (organId) {
            var self = this;
            if (self.gridOrganId == organId) {
                resizeGrid(self.gridId);
                return;
            }
            $.get(urls.getCompanyAgeToSubOrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.rendergGrid(data);
                self.gridOrganId = organId;
            });
        },
        initChart: function (organId) {
            var self = this;
            if (self.chartOrganId == organId) self.chartObj.resize();
            $.get(urls.getCompanyAge4OrganUrl, {organId: organId}, function (data) {
                if (_.isNull(data)) return;
                self.render(data);
                self.chartOrganId = organId;
            });

        },
        render: function (data) {
            var self = this, lis = [], legend = [];
            $.each(data, function (idx, item) {

                lis.push({name: item.k, value: item.v});
                legend.push({name: item.k, icon: "bar"});
            });
            var option = $.extend(true, {}, optionPie);
            option.series[0].data = lis;
            option.legend.data = legend;
            self.chartObj.clear();
            self.chartObj.setOption(option);
        },
        rendergGrid: function (datas) {
            var self = this, names = [], modals = [];

            $.each(datas.modals, function (idx, m) {
                names.push(isNaN(m.v) ? m.v : m.v + '级');
                modals.push({name: m.k, sortable: false, formatter: formatNum});
            });

            if (_.isNull(self.gridObj)) {
                var option = $.extend(true, {}, gridOption);
                option.colNames = names;
                option.colModel = modals;
                option.data = datas.data;
                self.gridObj = $(self.gridId).jqGrid(option);
                resizeGrid(self.gridId);
                return;
            }

            $(self.gridId).clearGridData().setGridParam({
                colNames: names,
                colModel: modals,
                data: datas.data
            }).trigger("reloadGrid");
        }
    };
    compangAgeObj.init();
});