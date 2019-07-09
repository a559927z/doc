require(["jquery", "echarts", "echarts/chart/bar", 'utils', 'organTreeSelector', 'jgGrid', "datetimepicker"], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        MAIN_SEQ_SEQ: webRoot + '/assets/json/posseq/jobMainSeqCount/jobMainSeqData.json',
        queryInflowOutflowChangeTypeUrl: webRoot + '/talentProfitLoss/queryInflowOutflowChangeType.do', // 异动统计饼图及表格类型数据
        queryChangePopulationUrl: webRoot + '/talentProfitLoss/queryChangePopulation.do', // 异动统计-人群类型
        querySequenceBarUrl: webRoot + '/talentProfitLoss/querySequenceBar.do', // 异动统计-序列分布柱状图
        queryAbilityBarUrl: webRoot + '/talentProfitLoss/queryAbilityBar.do', // 异动统计-职级分布柱状图
        queryEntryListDatasUrl: webRoot + '/talentProfitLoss/queryEntryListDatas.do', // 异动统计-入职名单
    }

    var no_data_info = "很抱歉，没有找到相关的记录";
    var totalSummary = 0;
    var textStyle = {color: '#000000', fontFamily: '微软雅黑', fontFamily2: '微软雅黑'};

    var changeStartDate = $('#changeStartDate').val();
    var changeEndDate = $('#changeEndDate').val();
    var defaultDatas = {
        parentType: '',
        childType: ''
    }

    var reqOrgId = win.currOrganId;
    var TextShape = require('zrender/shape/Text');
    var ecConfig = require('echarts/config');

    /**
     * 异动统计
     * */
    var profitLossChangeObj = {
        parBtnId: '#profitLossChangePerson',
        chiBtnId: '#profitLossChangePersonChild',
        init: function () {
            var self = this;
            self.getChildTypeDatasFun();
            self.personBtnChangeFun();
//    		self.childBtnChangeFun();
        },
        getChildTypeDatasFun: function () {
            var self = this;
            $.ajax({
                url: urls.queryChangePopulationUrl,
                type: 'post',
                success: function (data) {
                    self.loadChildTypesDatasFun(data);
                },
                error: function () {
                }
            });
        },
        loadChildTypesDatasFun: function (data) {
            var self = this;
            $('.childTypeClass').remove();
            var ul = "<ul class='childTypeClass'>";
            ul += "<li class='left div-btn-style chi-li-full' value=''>全部</li>";
            ul += "<li class='left li-line-style'>&nbsp;</li>";
            $.each(data, function (index, object) {
                ul += "<li class='left div-btn-style' value=" + object.value + ">" + object.name + "</li>";
            });
            ul += "</ul>";
            $(self.chiBtnId).append(ul);
            self.childBtnChangeFun();
        },
        personBtnChangeFun: function () {
            var self = this;
            $(self.parBtnId + ' li').unbind('click').bind('click', function () {
                var par = $(this).parent();
                var _t = this;
                $.each(par.children(), function (i, o) {
                    if (this == _t && i != 1) {
                        if (i == 0) {
                            if ($(this).hasClass('select')) {
                                $(this).removeClass('select').siblings('.div-btn-style').removeClass('select');
                            } else {
                                $(this).addClass('select').siblings('.div-btn-style').addClass('select');
                            }
                        } else if ($(this).hasClass('select')) {
                            $(this).removeClass('select');
                            $('.li-full').removeClass('select');
                        } else {
                            $(this).addClass('select');
                        }
                        var selLen = $(self.parBtnId + ' ul').find('.select').length
                        var len = $(self.parBtnId + ' .div-btn-style').length - 1;
                        if (selLen == 0) {
                            $('#parentType').val('');
                        } else if (selLen == len) {
                            $('.li-full').addClass('select');
//    						$('#parentType').val('');
                            var val = '';
                            var values = $(self.parBtnId + ' ul').find('.select');
                            $.each(values, function (i, o) {
                                if (val != '') {
                                    val += ',';
                                }
                                val += $(o).attr('value');
                            });
                            $('#parentType').val(val);
                        } else {
                            var value = $(self.parBtnId + ' ul').find('.select').attr('value');
                            $('#parentType').val(value);
                        }
//    					self.removeChildBtnSelectFun();
//    					$('#childType').val('');
//    					setChangePieAndTabInitFun();
//    					initChangeObj.init();
                    }
                })
            });
        },
        childBtnChangeFun: function () {
            var self = this;
            $(self.chiBtnId + ' li').unbind('click').bind('click', function () {
                var par = $(this).parent();
                var _t = this;
                $.each(par.children(), function (i, o) {
                    if (this == _t && i != 1) {
                        if (i == 0) {
                            if ($(this).hasClass('select')) {
                                $(this).removeClass('select').siblings('.div-btn-style').removeClass('select');
                            } else {
                                $(this).addClass('select').siblings('.div-btn-style').addClass('select');
                            }
                        } else if ($(this).hasClass('select')) {
                            $(this).removeClass('select');
                            $('.chi-li-full').removeClass('select');
                        } else {
                            $(this).addClass('select');
                        }
                        var selLen = $(self.chiBtnId + ' ul').find('.select').length
                        var len = $(self.chiBtnId + ' .div-btn-style').length - 1;
                        if (selLen == 0) {
                            $('#childType').val('');
                        } else if (selLen == len) {
                            $('.chi-li-full').addClass('select');
//    						$('#childType').val('');
                            var val = '';
                            var values = $(self.chiBtnId + ' ul').find('.select');
                            $.each(values, function (i, o) {
                                if (val != '') {
                                    val += ',';
                                }
                                val += $(o).attr('value');
                            });
                            $('#childType').val(val);
                        } else {
                            var val = '';
                            var values = $(self.chiBtnId + ' ul').find('.select');
                            $.each(values, function (i, o) {
                                if (val != '') {
                                    val += ',';
                                }
                                val += $(o).attr('value');
                            });
                            $('#childType').val(val);
                        }

                        if ($(self.parBtnId + ' ul').find('.select').length == 1) {
                            var pVal = $(self.parBtnId + ' ul').find('.select').attr('value');
                            $('#parentType').val(pVal);
                        } else {
                            $('#parentType').val('');
                        }
//    					setChangePieAndTabInitFun();
//    					initChangeObj.init();
                    }
                })
            });
        },
        removeChildBtnSelectFun: function () {
            var self = this;
            $(self.chiBtnId + ' li').removeClass('select');
        }
    }

    var mainSeqOption = {
        color: Tc.defaultBarColor,
        calculable: false,
        animation: false,
        tooltip: {
            formatter: function (a, b, c, d) {
                return a[1];
            }
        },
        series: [{
            type: 'pie',
            radius: ['30%', '50%'],
            top: ['0%', '0%'],       // 默认全局居中
            minAngle: 15,
            selectedMode: 'single',
            clockWise: true,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        // position: 'inside',
                        textStyle: textStyle,
                        formatter: function (obj) {
                            return obj.data.sum + "\n" + obj.value + "%";
                        }
                    },
                    labelLine: {show: true}
                }
            },
            data: ''
        }]
    };

    /**
     * 统计时间
     * */
    var countDateObj = {
        startDate: '.startDate',
        endDate: '.endDate',
        init: function () {
            var self = this;
            self.setDateFun();
        },
        setDateFun: function () {
            var self = this;
            $(self.startDate).datetimepicker({
                language: "cn",
                format: "yyyy-mm",
                autoclose: true,
                startView: 'year',
                minView: "year",
                maxView: "decade",
//				todayBtn : true,
                pickerPosition: "bottom-left"
            })
            /*.on("show", function(ev) {
             $("." + self.startDate).datetimepicker(
             "setEndDate", $("." + self.endDate+ " input").val());
             })*/
                .on("changeDate", function (ev) {
//				setChangePieAndTabInitFun();
//				initChangeObj.init();
                });
            $(self.endDate).datetimepicker({
                language: "cn",
                format: "yyyy-mm",
                autoclose: true,
                startView: 'year',
                minView: "year",
                maxView: "decade",
//				todayBtn : true,
                pickerPosition: "bottom-left"
            }).on("changeDate", function (ev) {
//				setChangePieAndTabInitFun();
//				initChangeObj.init();
            });
        }
    }
    /**
     * 异动统计 确认/清除
     * */
    var changeOkAndClearObj = {
        changeOk: '#changeOk',
        changeClear: '#changeClear',
        startDate: '.startDate',
        endDate: '.endDate',
        parBtnId: '#profitLossChangePerson',
        chiBtnId: '#profitLossChangePersonChild',
        init: function (flag) {
            var self = this;
            countDateObj.init();
            if (!flag) {
                profitLossChangeObj.init();
            }
            self.okFun();
            self.clearFun();
        },
        okFun: function () {
            var self = this;
            $(self.changeOk).unbind('click').bind('click', function () {
                setChangePieAndTabInitFun();
                initChangeObj.init();
            });
        },
        clearFun: function () {
            var self = this;
            $(self.changeClear).unbind('click').bind('click', function () {
                $(self.startDate + ' input').val(changeStartDate);
                $(self.endDate + ' input').val(changeEndDate);
                $(self.parBtnId + ' li').removeClass('select');
                $(self.chiBtnId + ' li').removeClass('select');
                $('#parentType').val('');
                $('#childType').val('');
            });
        }
    }

    function loadingFun() {
        var div = '<div class="div-loading-img"></div>';
        $('#positionSequenceChart').prepend(div);
    }

    var setChangePieAndTabInitFun = function () {
        loadingFun();
        if ($(".detailseq_area").show()) {
            jobMainSeqCount.changeMainStyle();
            $(".detailseq_area").hide(0, function () {
                $("#seq").parent().animate({
                    width: "show",
                    height: "show",
                    paddingLeft: "show",
                    paddingRight: "show",
                    marginLeft: "show",
                    marginRight: "show"
                }, 1000, function () {
                    $("#seqtable li").removeClass("current");
                });
            });
        }
    }

    var jobMainSeqCount = {
        /*******************************************************************
         * id:图表id, el:表格id data:主序列数据
         */
        chartId: null,
        myChart: null,
        init: function (id, el, deptInfo) {
            var self = this;
            loadingFun();
            self.el = el;
            self.chartId = id;
            loadingChart(self.chartId);
            var myChart = initEChart(id);
            self.myChart = myChart;
            self.isClickBind = false;
            self.options = mainSeqOption;
            self.initEvent(deptInfo);
        },
        initEvent: function (deptInfo) {
            if (deptInfo['id']) {
                this.initDefaultData();
            }
        },
        addRowOver: function () {
            var self = this;
            var firstLi = $("#seqtable li:gt(0)");
            firstLi.mouseover(function () {
                self.changeTableStyle("#seqtable li", $(this));
                if (!self.isPieRender) {
                    return;
                }
//			  	var serie = self.options.series[0]; 
                var tableSelectd = $(this).find("span").eq(0).attr("value");
                var serie = self.options.series[0];
                serie.data[getSelectItem(serie)].selected = false;
                serie.data[tableSelectd].selected = true;
                self.options.animation = false;
                self.myChart.setOption(self.options);
            });
            firstLi.mouseout(function () {
                $(this).removeClass("hover");
            });
            function getSelectItem(serie) {
                for (var i = 0; i < serie.data.length; i++) {
                    if (serie.data[i].selected) {
                        return i;
                    }
                }
            }
        },
        // 表格点击事件
        addRowClick: function () {
            var self = this;
            $("#seqtable li:gt(0)").click(function () {
                $('.talentProfitLoss .bottom-div-change').height('1100px');
                totalSummary = 0;
                self.lvTitleData = [];
                $("#seqtable li:gt(0)").removeClass("current");
                $(this).addClass("current");
                var mainSeqId = $(this).attr("val");
                var mainSeqText = $(this).find('span:eq(0)').text();
                self.setMainSeqId(mainSeqId);
                $("#seqtable").css({
                    "margin-left": "0px",
                    "margin-top": "15px"
                });
                $("#seqtable").parent().attr("class", "span7");
                $("#seq").parent().animate({
                    width: "hide",
                    paddingLeft: "hide",
                    paddingRight: "hide",
                    marginLeft: "hide",
                    marginRight: "hide"
                }, 0, function () {
                    self._detailseqShowCall(mainSeqId, mainSeqText);
                });
            });
        },
        _detailseqShowCall: function (mainSeqId, mainSeqText) {
            var self = this;
            self.addCloseEvent();
            $(".detailseq_area").show(0, function () {
                var param = {
                    organId: self.getUniqueDeptId(),
                    startDate: self.getStartDate(),
                    endDate: self.getEndDate(),
                    parentType: self.getParentType(),
                    childType: self.getChildType(),
                    changeType: mainSeqId
                }
                $('#childseqText').html(mainSeqText);
                // TODO 查询右面数据
                sequenceBarObj.init(param);
                abilityBarObj.init(param);
                entryListObj.init(param);
            });
        },
        changeMainStyle: function () {
            $("#seqtable").css({
                "margin-left": "100px"
//				"margin-top" : "50px"
            });
            // 中间表格宽度
            $("#seqtable").parent().attr("class", "span12");
        },
        setMainSeqData: function (uniqueDeptId, startDate, endDate, parentType, childType) {
            if (!uniqueDeptId) {
                return;
            }
            var self = this,
                params = {
                    organId: uniqueDeptId,
                    startDate: startDate,
                    endDate: endDate,
                    parentType: parentType,
                    childType: childType
                };
            $.getJSON(urls.queryInflowOutflowChangeTypeUrl, params, function (data) {
                $('.div-loading-img').remove();
                self.changeMainStyle();
                self.isPieRender = false;
                var isZero = data.datas.length == 0 || data.total == 0;
                $("#seq").toggle(!isZero);
                // 如果所有值为空，则不渲染饼图
                $.each(data.datas, function (i, val) {
                    if (val.sum > 0) {
                        self.isPieRender = true;
                        return false;
                    }
                });
                if (self.isPieRender) {
                    hideChart(self.chartId, false);
                    var mId = self.getMainSeqId();
                    var series_data = [];
                    var flag = false;
                    // TODO 左边图饼数据 data
                    $.each(data.datas, function (i, data) {
                        data.selected = mId == data.seqId ? true : false;
                        flag = data.selected;
                        if (data.sum > 0) {
                            series_data.push(data);
                        }
                    });
                    if (!flag)
                        series_data[0].selected = true;
                    self.options.series[0].data = series_data;
                    self.setOption(self.options);
                } else {
                    hideChart(self.chartId, true);
                    self.clear();
                }

                // TODO 中间表格数据 data.datas
                var tableArr = [];
                $.each(data.datas, function (i, data) {
                    if (data.sum > 0) {
                        tableArr.push(data);
                    }
                });
                self.renderTable(self.el, tableArr, data.total);
                self.myChart.on(ecConfig.EVENT.HOVER, function (param) {
                    var eqIndex = param.dataIndex == 0 ? 0 : param.dataIndex;
                    self.changeTableStyle("#seqtable li", $("#seqtable li:gt(0)").eq(eqIndex));
                });
                self.addEvent();
            }, "json");
        },
        addCloseEvent: function () {
            var self = this;
            $(".seqdetailclose").click(function () {
                self.changeMainStyle();
                $(".detailseq_area").hide(0, function () {
                    $("#seq").parent().animate({
                        width: "show",
                        height: "show",
                        paddingLeft: "show",
                        paddingRight: "show",
                        marginLeft: "show",
                        marginRight: "show"
                    }, 1000, function () {
                        $("#seqtable li").removeClass("current");
                    });
                });
            });
        },
        renderTable: function (el, data, total) {
            var self = this;
            var ul = $("<ul>");
            if (total != 0 && data != null && data.length > 0) {
                var lis = "<li style='border-bottom:none;'>" +
                    "<span style='width:100px;float:left;'>&nbsp;</span>" +
                    "<span class='num'>人数</span><span class='percent'>人数占比</span>" +
                    "<a href='javascript:void(0);'></a>" +
                    "</li>";
                var mainId = this.getMainSeqId();
                for (var i = 0; i < data.length; i++) {
                    var isManagerSeq = data[i].isManagerSeq;
                    var isExecutiveSeq = data[i].isExecutiveSeq;
                    var seqId = data[i].seqId;

                    var clzName = seqId == mainId ? "current" : "";
                    lis += "<li class='" + clzName + "' val='" + seqId + "'>" +
                        "<span class='title' style='background:" + Tc.defaultBarColor[i] + "' value='" + i + "'>" +
                        data[i].alias +
                        "</span>" +
                        "<span class='num'>" +
                        Tc.formatNumber(data[i].sum) +
                        "</span>" +
                        "<span class='percent'>" +
                        data[i].percentatge + "%" +
                        "</span>" +
                        "<span id='navbox' style='width:10px;'>" +
                        "<a href='javascript:void(0);' class='jt'></a>" +
                        "</span>" +
                        "</li>";
                }
                ul.append($(lis));
            }
            $("#" + el).empty().append(ul);
            self.setChangeDivHeight();
        },
        setChangeDivHeight: function () {
            var self = this;
            var tabHeight = $("#seqtable").parent().height();
            if (tabHeight > 550) {
                $('.talentProfitLoss .bottom-div-change').height(tabHeight + 150);
                $('#' + self.chartId).height(tabHeight);
                self.myChart.resize();
            } else {
                $('.talentProfitLoss .bottom-div-change').height('600px');
                $('#' + self.chartId).height('400px');
                self.myChart.resize();
            }
        },

        setOption: function (options) {
            var self = this;
            self.myChart.setOption(this.options, true);
            self.myChart.on(ecConfig.EVENT.HOVER, function (param) {

            });
        },
        clear: function (chart) {
            var self = this;
            if (!chart) {
                self.myChart.clear();
            } else {
                chart.clear();
            }
//			var style = 'padding : 15% 40%';
//			$('#positionSequenceChart').html('暂无数据...').attr('style', style);
        },
        changeTableStyle: function (dom, obj) {
            $(dom).removeClass("hover");
            obj.addClass("hover");
        },
        getMainSeqId: function () {
            return $("#mainSeqId").val();
        },
        setMainSeqId: function (val) {
            $("#mainSeqId").val(val);
        },
        initDefaultData: function () {
            this.setMainSeqData(this.getUniqueDeptId(), this.getStartDate(), this.getEndDate(), this.getParentType(), this.getChildType());
        },
        getUniqueDeptId: function () {
            return $("#uniqueId").val();
        },
        getUnitId: function () {
            return this.getUniqueDeptId().split("_")[0];
        },
        getDate: function () {
            return $("#initDate").val();
        },
        getStartDate: function () {
            return $(".startDate input").val();
        },
        getEndDate: function () {
            return $(".endDate input").val();
        },
        getParentType: function () {
            return $('#parentType').val();
        },
        getChildType: function () {
            return $('#childType').val();
        },
        addEvent: function () {
            this.addRowClick();
            this.addRowOver();
        },
    };

    /**
     * 序列分布柱图
     * */
    var sequenceBarObj = {
        option: {
            grid: {
                x: 45,
                y: 25,
                x2: 5,
                borderWidth: 0
            },
            xAxis: [{
                type: 'category',
                axisTick: false,
                splitLine: false,
                axisLabel: {
                    interval: 0,
                    rotate: 30
                },
                axisLine: {
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                axisTick: false,
                axisLine: {
                    show: false
                },
                axisLabel: {
                    formatter: '{value} %'
                }
            }],
            series: [{
                type: 'bar',
                barWidth: 30,
                data: [],
                itemStyle: {
                    normal: {
                        color: '#0099cb',
                        label: {
                            show: true,
                            formatter: function (obj) {
                                return obj.value + ' %'
                            }
                        }
                    }
                }
            }]
        },
        chart: initEChart("sequenceBar"),
        init: function (param) {
            var self = this;
            clearEChart(self.chart);
            self.getRequestDatasFun(param);
        },
        getRequestDatasFun: function (param) {
            var self = this;
            $.ajax({
                url: urls.querySequenceBarUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOptionFun(data);
                },
                error: function () {
                }
            });
        },
        getOptionFun: function (data) {
            var self = this;
            if (data.date) {
                self.option.xAxis[0].data = data.date;
                self.option.series[0].data = data.seriesData;
                changeXAxisLabelRotate(self.option, data.date);
                self.setOptionFun();
            } else {
                showNoDataEcharts(self.chart);
            }
        },
        setOptionFun: function () {
            var self = this;
            self.chart.setOption(self.option);
        }
    }
    /**
     * 职级分布柱图
     * */
    var abilityBarObj = {
        option: {
            grid: {
                x: 45,
                y: 25,
                x2: 5,
                borderWidth: 0
            },
            xAxis: [{
                type: 'category',
                axisTick: false,
                splitLine: false,
                axisLabel: {
                    interval: 0,
                    rotate: 30
                },
                axisLine: {
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                axisTick: false,
                axisLine: {
                    show: false
                },
                axisLabel: {
                    formatter: '{value} %'
                }
            }],
            series: [{
                type: 'bar',
                data: [],
                barWidth: 30,
                itemStyle: {
                    normal: {
                        color: '#0099cb',
                        label: {
                            show: true,
                            formatter: function (obj) {
                                return obj.value + ' %'
                            }
                        }
                    }
                }
            }]
        },
        chart: initEChart("abilityBar"),
        init: function (param) {
            var self = this;
            clearEChart(self.chart);
            self.getRequestDatasFun(param);
        },
        getRequestDatasFun: function (param) {
            var self = this;
            $.ajax({
                url: urls.queryAbilityBarUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOptionFun(data);
                },
                error: function () {
                }
            });
        },
        getOptionFun: function (data) {
            var self = this;
            if (data.date) {
                self.option.xAxis[0].data = data.date;
                self.option.series[0].data = data.seriesData;
                changeXAxisLabelRotate(self.option, data.date);
                self.setOptionFun();
            } else {
                showNoDataEcharts(self.chart);
            }
        },
        setOptionFun: function () {
            var self = this;
            self.chart.setOption(self.option);
        }
    }

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    /**
     * 清除echart面板
     * @param eChartObj
     */
    function clearEChart(eChartObj) {
        if (eChartObj) {
            eChartObj.clear();
        }
    }

    /**
     * 无数据据时显示 暂无数据
     *
     * @param echartObj echart对象
     */
    var showNoDataEcharts = function (echartObj) {
        var zr = echartObj.getZrender();
        zr.addShape(new TextShape({
            style: {
                x: (echartObj.dom.clientWidth - 56) / 2,
                y: (echartObj.dom.clientHeight - 20) / 2,
                color: '#ccc',
                text: '暂无数据',
                textFont: '14px 宋体'
            }
        }));
        zr.render();
    };

    /**
     * 设置x轴标签显示方向
     * @param option echart对象
     * @param xAxisLabel x轴标签
     */
    function changeXAxisLabelRotate(option, xAxisLabel) {
        if (xAxisLabel.length > 3) {
            option.xAxis[0].axisLabel.rotate = 30;
        } else {
            option.xAxis[0].axisLabel.rotate = 0;
        }
    }

    /**
     * 入职名单
     * */
    var entryListObj = {
        gridTabId: 'entryDiv',
        gridId: 'entryGrid',
        gridPagerId: 'entryGridPager',
        flag: false,
        init: function (param) {
            var self = this;
            if (self.flag) {
                self.reloadGridFun(param);
                return;
            }
            self.getRequestDatasFun(param);
            self.flag = true;
        },
        objIsUndefinedFun: function (obj) {
            return obj == undefined ? '' : obj;
        },
        getRequestDatasFun: function (param) {
            var self = this;
            $('#' + self.gridId).setGridWidth($('#' + self.gridTabId).width());
            $('#' + self.gridId).jqGrid({
                url: urls.queryEntryListDatasUrl,
                postData: param,
                mtype: 'POST',
                datatype: "json",
                height: 361,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['姓名', '性别', '年龄', '学历', '专业', '部门', '岗位', '异动日期'],
                colModel: [
                    {name: 'userName', sortable: false, width: 100, fixed: true, align: 'center', editable: false},
                    {
                        name: 'sex', sortable: false, width: 60, fixed: true, align: 'center', editable: false,
                        formatter: function (cellvalue, options, rowObject) {
                            if (cellvalue == 'm') {
                                return '男';
                            } else if (cellvalue == 'w') {
                                return '女';
                            } else {
                                return '';
                            }
                        }
                    },
                    {name: 'age', sortable: false, width: 60, fixed: true, align: 'center', editable: false},
                    {name: 'degree', sortable: false, width: 60, fixed: true, align: 'center', editable: false},
                    {name: 'major', sortable: false, width: 120, fixed: true, align: 'center', editable: false},
                    {name: 'organName', sortable: false, width: 120, fixed: true, align: 'center', editable: false},
                    {name: 'positionName', sortable: false, width: 120, fixed: true, align: 'center', editable: false},
                    {name: 'changeDate', sortable: false, width: 90, fixed: true, align: 'center', editable: false}
                ],
                page: 1,
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#' + self.gridPagerId,
//		        rownumbers: true,
                hoverrows: false,
                viewrecords: true,
//		        editable : false,
                autowidth: true,
                loadComplete: function (xhr) {
                    setTimeout(function () {
                    }, 0);
                }
            });
        },
        reloadGridFun: function (param) {
            var self = this;
            $('#' + self.gridId).clearGridData().setGridParam({
                postData: param
            }).trigger("reloadGrid");
            $('#' + self.gridId).setGridWidth($('#' + self.gridTabId).width());
        }
    }

    Tc.JobMainSeqCount = function (id, el, deptInfo) {
        jobMainSeqCount.init(id, el, deptInfo);
        return jobMainSeqCount;
    };

    var initChangeObj = {
        init: function () {
            var deptInfo = {};
            deptInfo['id'] = Tc.myOrgId;
//			deptInfo['id'] = $("#uniqueId").val();
//			deptInfo['text'] = $("#deptName").val();
            var jobMainSeq = new Tc.JobMainSeqCount("seq", "seqtable", deptInfo);
        }
    }

    Tc.pieGridObj = function (flag) {
        if (Tc.myOrgId == undefined) {
            $("#uniqueId").val(reqOrgId);
        }
        $("#uniqueId").val(Tc.myOrgId);
        changeOkAndClearObj.init(flag);
//		countDateObj.init();
        initChangeObj.init();
//		if(!flag){
//			profitLossChangeObj.init();
//		}
        // TODO 从Controller转入机构Id和名称
    }
    Tc.changeResize = function () {
        var width = $('#profitLossChangePerson').width();
        if (width < 1100) {
            $('#btnGroups').addClass('btn-group-2').removeClass('btn-group-1');
        } else {
            $('#btnGroups').addClass('btn-group-1').removeClass('btn-group-2');
        }
    }
    //暂无数据
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

    //数据加载
    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
    }

    //返回饼图表格
    Tc.reloadChangeObj = function () {
        setChangePieAndTabInitFun();
        initChangeObj.init();
    }
//	Tc.pieGridObj();
});
