require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'jgGrid', 'underscore', 'utils', 'bootstrap', 'timeLine2', 'searchBox3', "jquery-mCustomScrollBar", 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        queryAccordDismissUrl: webRoot + '/accordDismiss/queryDisminss4Quarter.do', //查询主动流失率信息
        getKeyDismissDataUrl: webRoot + '/accordDismiss/getKeyDismissData.do', //获取“关键人才流失人数”的数据
        getPerfDismissDataUrl: webRoot + '/accordDismiss/getPerfDismissData.do', //获取“高绩效流失人数”的数据
        dismissRecordUrl: webRoot + '/accordDismiss/getDismissRecord.do',	//流失原因
        subDismissDataUrl: webRoot + '/accordDismiss/getSubDismissData.do',	//本部与下属部门流失率对比数据
        searchBoxUrl: webRoot + '/common/getSearchBox.do',	//筛选条件信息
        timeScopeUrl: webRoot + '/common/getTimeScope.do',	//筛选条件信息
        getRunOffDetailUrl: webRoot + '/accordDismiss/queryRunOffDetail.do',	//流失人员明细
        getDismissTrendUrl: webRoot + '/accordDismiss/queryDismissTrend.do',	//主动流失率趋势
        getQuarterDismissUrl: webRoot + '/accordDismiss/quarterDismissInfo.do',    //季度流失人员统计信息
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',    //跳转到员工详情
        updateDismissValueUrl: webRoot + '/accordDismiss/updateDismissValue.do',   //更新主动流失率指标风险值信息
        getDismissBaseConfigUrl: webRoot + '/accordDismiss/getDismissBaseConfig.do',    //获取主动流失率基础配置信息
        updateDismissBaseConfigUrl: webRoot + '/accordDismiss/updateDismissBaseConfig.do'    //更新主动流失率基础配置信息
    };
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();

    var reqOrgId = win.currOrganId;
    var quarterLastDay = $('#quarterLastDay').val();
    var prevQuarterFirstDay = firstDate(quarterLastDay);

    var TextShape = require('zrender/shape/Text');


    var tableSpileWidth=1;

    $(".ct-mCustomScrollBar").mCustomScrollbar({});     //mCustomScrollbar

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        runOffDetailObj.searchBox.clear();
        $("#searchTxt").val("");
        var curTopTabId = getActiveTabId(".leftListDiv");
        changeData(curTopTabId);
    };

    /**
     * 主动流失率
     * @type {{chartId: string, chartObj: null, quarterCount: number, chartOption: {tooltip: boolean, toolbox: boolean, series: *[]}, init: dismissObj.init, requestData: dismissObj.requestData}}
     */
    var dismissObj = {
    	customizeId:'page-one-group1-1',
        chartId: 'LSLDismissChart',
        chartObj: null,
        quarterCount: 0,
        chartOption: {
            tooltip: {
                formatter: "{a} <br>{c}%"
            },
            toolbox: false,
            series: [
                {
                    name: '主动流失率',
                    type: 'gauge',
                    radius: [0, '90%'],
                    max: 25,
                    splitNumber: 0,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.3, '#72B031'], [0.7, '#F1A502'], [1, '#D4531A']],
                            width: 6
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        splitNumber: 1,   // 每份split细分多少段
                        length: 6        // 属性length控制线长
                    },
                    axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                        show: true,
                        formatter: function (v) {
                            if (v == 0) return '';
                            return Math.round(v);
                        },
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            color: '#333'
                        }
                    },
                    pointer: {
                        length: '80%',
                        width: 4,
                        color: '#000'
                    },
                    detail: false,
                    data: [{value: 4.8}]
                }
            ]
        },
        init: function () {
            var self = this;
            self.chartObj = initEChart(self.chartId);
        },
        initData: function (organId) {
            var self = this;
            self.organId = organId;
            $.post(urls.queryAccordDismissUrl, {'organId': organId, 'prevQuarter': quarterLastDay}, function (rs) {
                var dismissTrend = rs.dismissTrend;
                if (!$.isEmptyObject(dismissTrend)) {
                    self.dismissTrend = dismissTrend;
                    self.quarterCount = dismissTrend.monthCount;

                    self.normal = rs.normal;
                    self.risk = rs.risk;
                    self.initChart(rs.normal, rs.risk, rs.max);
                    //调用不同维度分布图
                    accordDismissPieObj.initData(organId);
                }
            });
        },
        initChart: function (normal, risk, max) {
            var self = this;
            var normalPer = Tc.formatFloat(normal * 100), riskPer = Tc.formatFloat(risk * 100);
            self.chartOption.series[0].max = Tc.formatFloat(max * 100);
            self.chartOption.series[0].axisLine.lineStyle.color[0] = [Tc.formatFloat(normal / max), '#72B031'];
            self.chartOption.series[0].axisLine.lineStyle.color[1] = [Tc.formatFloat(risk / max), '#F1A502'];

            var dismissTrend = self.dismissTrend;

            var trendRate = dismissTrend.rate;
            var rate = Tc.formatFloat(trendRate * 100);
            self.chartOption.series[0].data[0].value = rate;
            self.chartObj.setOption(self.chartOption, true);

            //定义文本信息
            $('.accord-dismiss-normal .accord-main-number').text(rate);
            $('.accord-dismiss-normal .accord-desc-warn-txt').text(normalPer + '% - ' + riskPer + '%');
            $('.accord-dismiss-normal .accord-desc-over-txt').text(riskPer + '% 以上');

            if (trendRate <= normal) {
                $('.accord-dismiss-normal .accord-main-number').css('color', '#72B031');
                $('.accord-dismiss-normal .accord-main-content').css('color', '#72B031');
                $('.accord-dismiss-normal .accord-main-content').html('流失率正常，<br/>队伍状态不错！');
            } else if (normal < trendRate && trendRate <= risk) {
                $('.accord-dismiss-normal .accord-main-number').css('color', '#F1A502');
                $('.accord-dismiss-normal .accord-main-content').css('color', '#F1A502');
                $('.accord-dismiss-normal .accord-main-content').html('流失率偏高，<br/>请持续关注！');
            } else {
                $('.accord-dismiss-normal .accord-main-number').css('color', '#D4531A');
                $('.accord-dismiss-normal .accord-main-content').css('color', '#D4531A');
                $('.accord-dismiss-normal .accord-main-content').html('流失率超高，<br/>队伍状态异常！');
            }
            $('#unit').html("&nbsp;%");
        },
        resizeChart: function () {
            this.chartObj.resize();
        }
    };

    /**
     * 关键人才流失人数、高绩效流失人数
     */
    var keyPerfDismissObj = {
        keyAreaId: 'keyDismissArea',
        perfAreaId: 'perfDismissArea',
        initData: function (organId) {
            var self = this;
            if (self.organId == organId) {
                return;
            }
            self.organId = organId;
            self.getRequestData(organId, urls.getKeyDismissDataUrl, self.keyAreaId);
            self.getRequestData(organId, urls.getPerfDismissDataUrl, self.perfAreaId);
        },
        getRequestData: function (organId, url, containerId) {
            var self = this;
            $('#' + containerId).find('.currentQuarter').unbind('click').css('cursor', 'default');
            $.get(url, {organId: organId, date: quarterLastDay}, function (data) {
                self.generateDetail(data, containerId);
            });
        },
        generateDetail: function (data, containerId) {
            var self = this;
            var preValue = data.prev;
            var prevIcon = '', prevColor = '', preValueStr = '';
            if (preValue != null) {
                preValueStr = preValue > 0 ? preValue : -preValue;
                prevColor = preValue > 0 ? 'accord-bottom-float-value-rise' : 'accord-bottom-float-value-drop'
            }
            $('#' + containerId).find('.accord-bottom-float-arrow')
                .removeClass('accord-bottom-float-value-rise accord-bottom-float-value-drop')
                .addClass(prevColor);
            $('#' + containerId).find('.accord-bottom-float-value')
                .removeClass('accord-bottom-float-value-rise accord-bottom-float-value-drop')
                .addClass(prevColor).text(preValueStr);
            $('#' + containerId).find('.accord-yj-float-value').text(data.cur || 0);
            if (data.cur) {
                self.bindClickEvent(containerId);
            }
        },
        bindClickEvent: function (containerId) {
            var self = this;
            $('#' + containerId).find('.currentQuarter').css('cursor', 'pointer').click(function () {
                runOffDetailObj.searchBox.clear();
                $("#searchTxt").val("");
                runOffDetailObj.searchBox.select("roType", 1);
                if (self.keyAreaId == containerId) {
                    //点击的是关键人才流失人数
                    runOffDetailObj.searchBox.select("isKeyTalent", 1);
                    runOffDetailObj.initData(organTree.getVal(), quarterLastDay);
                } else {
                    //点击的是高绩效流失人数
                    $.each(runOffDetailObj.searchBox.findRow("performanceKey"), function (i, item) {
                        var perfKey = parseInt($(this).attr("value"));
                        if (perfKey >= highPerfBegin) {
                            $(this).select();
                        }
                        ;
                    });
                    runOffDetailObj.initData(organTree.getVal(), quarterLastDay);
                }
                $('#dimissTypeTabs li').eq(2).find('a').click();
            });
        }
    }



    /**
     * 管理建议与备忘
     * @type {{init: timeLineObj.init, getOption: timeLineObj.getOption}}
     */
    var timeLineObj = {
        customizeId:'page-one-group1-4',
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

    /*切换左边导航*/
    $(".leftListDiv").click(function () {
        if ($(this).hasClass("selectList")) {
            return;
        } else {
            var $page = $(this).attr("page");
            $(".rightBodyPage").hide();
            $("#" + $page).show();
            $(".leftListDiv").removeClass("selectList");
            $(this).addClass("selectList");
            changeData($page);
            if ($page == 'page-one') {
                dismissObj.resizeChart();
            }
        }
    });


    /***
     * 主动流失率趋势
     * @type {{chartId: string, gridId: string, highPerfBegin: number, chartObj: null, chartOption: {legend: {data: string[], selected: {上级组织均值: boolean}, y: string}, grid: {borderWidth: number}, color: string[], xAxis: *[], yAxis: *[], tooltip: {trigger: string, axisPointer: {type: string}, formatter: trendChartObj.chartOption.tooltip.formatter}, series: *[]}, tableOption: {data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], scroll: boolean}, init: trendChartObj.init, initData: trendChartObj.initData, initGrid: trendChartObj.initGrid, resizeGrid: trendChartObj.resizeGrid, initChartData: trendChartObj.initChartData, requestData: trendChartObj.requestData, extendData: trendChartObj.extendData, parentline: trendChartObj.parentline, extendChildData: trendChartObj.extendChildData, extendParentRate: trendChartObj.extendParentRate, calculateRate: trendChartObj.calculateRate, toprecords: trendChartObj.toprecords, calculateChain: trendChartObj.calculateChain}}
     */
    var trendChartObj = {
    	customizeId:'page-one-group2-1',
        chartId: 'trendChart',
        gridId: '#trendTableGrid',
        highPerfBegin: 4,
        chartObj: null,
        chartOption: {
            legend: {
                data: ['主动流失率','环比变化',  '上级组织均值'],
                selected: {'上级组织均值': false},
                y: 320
            },
            grid: {
                x: 55,
                y: 40,
                x2: 15,
                borderWidth: 0
            },
            color:  ['#5cb7f1', '#f5b147', '#a88fd7'],
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    onZero: false,
                    lineStyle: {
                    	color: '#666666',
                    	 width: 1
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                    	 color: '#666666'
                    }
                },
                axisLabel: {
                	itemStyle: {
                        color: '#666666'
                    },
                    textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: {
                    show: true,
                    lineStyle : {
                    	color : '#e4e4e4'
                    }
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#666666',
                        width: 1
                    }
                },
                axisLabel: {
                    show: true,
                    formatter: function (value) {
                        return (value * 100) + '%';
                    }
                }
            }],
            tooltip: {
            	 trigger: 'axis',
       	      	  axisPointer: {
	       	          type: 'line',
	       	          lineStyle:{
	       	          	color: '#999999',
	       	          	type: 'dashed',
	       	          	width: 1
	       	          }
       	      	  },
                formatter: function (params) {
                    var html = "<div>" + params[0].name + "<br>主动流失率：" + (params[0].data * 10000 / 100).toFixed(2) + "%<br>环比变化：" + (params[1].data * 10000 / 100).toFixed(2) + "%<div>";
                    return html;
                }
            },
            series: [{
                name: '主动流失率',
                type: 'bar',
                clickable: false,
                barCategoryGap: '45%',
                barWidth: 30,
                itemStyle: {
                	normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return formatPercentage(i.value);
                            }
                        }
                    }
                    
                },
                data: []
            }, {
                name: '环比变化',
                type: 'line',
                clickable: false,
                yAxisIndex: 0,
                symbol: 'circle',
                itemStyle: {
                	emphasis: {
                        color: "rgba(0, 0, 0, 0)"
                    }
                },
                data: []
            }, {
                name: '上级组织均值',
                type: 'line',
                clickable: false,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return formatPercentage(i.value);
                            }
                        },
                        lineStyle: {
                            type: 'dotted'
                        }
                    }
                },
                data: []
            }]
        },
        tableOption: {
            data: [],
            datatype: "local",
            //altRows: false,//设置表格行的不同底色
//			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
            height: 268,//268
            colNames: ['', '当前季度', '当年累计', '上级组织季度均值'],
            colModel: [
                {name: 'strQuarter', index: 'strQuarter', width: 120, sortable: false,align: 'center',},
                {
                    name: 'rate',
                    index: 'rate',
                    width: 120,
                    sortable: false,
                    align: 'center',
                    formatter: formatPercentage
                },
                {
                    name: 'yearRate',
                    index: 'yearRate',
                    width: 120,
                    sortable: false,
                    align: 'center',
                    formatter: formatPercentage
                },
                {
                    name: 'parentRate',
                    index: 'parentRate',
                    width: 150,
                    fixed: true,
                    sortable: false,
                    align: 'center',
                    formatter: formatPercentage
                }
            ],
            //rownumbers: true,
            //rownumWidth: 40,
            scroll: true
        },
        init: function () {
            var self = this;
            $('#' + self.chartId).height($('#' + self.chartId).parents('.bottom-div').height());
            self.chartObj = initEChart(self.chartId);
            $(self.gridId).jqGrid(self.tableOption);
            self.resizeGrid();
        },
        initData: function (organId) {
            var self = this;
            if (self.organId == organId) {
                return;
            }
            self.organId = organId;
            self.requestData(organId);
        },
        initGrid: function () {
            var self = this;
            var hasParent = self.hasParent;
            $(self.gridId).clearGridData().setGridParam({
                data: self.tableOption.data
            }).trigger("reloadGrid");
            if (!hasParent) {
                $(self.gridId).jqGrid('hideCol', 'parentRate');
                //}else{
                //    $(self.gridId).jqGrid('showCol','parentRate');
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#trendGridArea').width()-tableSpileWidth);
            $('#trendGridArea').children(".ui-jqgrid ").width($('#trendGridArea').width()-tableSpileWidth);
        },
        
        initChartData: function (data) {
            var self = this;
            var xAxisData = [], chainData = [], dismissRateData = [], parentRateData = [];

            var hasParent = self.hasParent;
            $.each(data, function (i, obj) {
                xAxisData.push(obj.strQuarter);
                chainData.push(obj.chain);
                dismissRateData.push(obj.rate);
                if (hasParent) {
                    parentRateData.push(obj.parentRate);
                }
            });

            var chartOption = _.clone(self.chartOption);
            chartOption.xAxis[0].data = xAxisData;
            chartOption.series[0].data = dismissRateData;
            chartOption.series[1].data = chainData;

            if (hasParent) {
                chartOption.series[2].data = parentRateData;
            } else {
                chartOption.legend.data = chartOption.legend.data.slice(0, 2);
                chartOption.color = chartOption.color.slice(0, 2);
                chartOption.series = chartOption.series.slice(0, 2);
            }
            self.extendsOption = chartOption;
        },
        resizeChart: function () {
            this.chartObj.resize();
        },
        requestData: function (organId) {
            var self = this;
            $.post(urls.getDismissTrendUrl, {'organId': organId, 'prevQuarter': quarterLastDay}, function (rs) {
                if (_.isEmpty(rs)) {
                    showEmptyTip($('#' + self.chartId).parent());
                    return;
                }
                removeEmptyTip($('#' + self.chartId).parent());
                self.extendData(organId, rs);
                self.chartObj.setOption(self.extendsOption, true);
                self.initGrid();
            });
        },
        extendData: function (organId, source) {
            var self = this;
            var packData = [], parentData = [];		//封装的数据
            $.each(source, function (i, obj) {		//取得相关数据
                if (obj.organId == organId) {			//本部门相关数据
                    self.extendChildData(obj, packData);
                } else {
                    self.extendChildData(obj, parentData);
                }
            });
            //计算流失率
            self.calculateRate(packData);
            //计算环比
            packData = _.sortBy(packData, 'strQuarter');
            self.calculateChain(packData);
            if (!$.isEmptyObject(parentData)) {
                self.calculateRate(parentData);
            }
            parentData = self.toprecords(parentData, self.highPerfBegin);
            self.extendParentRate(packData, parentData);
            packData = self.toprecords(packData, self.highPerfBegin);
            self.tableOption.data = packData;
            self.initChartData(packData);
        },
        //上级组织均值线
        parentline: function (data) {
            var line = 0;
            if (data.length > 0) {
                var first = _.first(data).begin;
                var last = _.last(data).end;
                var count = 0;
                var n = 0;
                $.each(data, function (i, o) {
                    count += o.count;
                    n++;
                });
                line = 2 * count / ((first + last) * n);
            }
            return line;
        },
        extendChildData: function (obj, data) {
            var year = obj.yearMonth.substr(0, 4);
            var month = obj.yearMonth.substr(year.length, 2);
            var quarter = parseInt((parseInt(month) + 2) / 3);
            var strQuarter = year + 'Q' + quarter;
            if ($.isEmptyObject(data)) {		//假如没有，则添加新的数据
                var packObj = {
                    quarter: quarter,
                    year: year,
                    minMonth: month,
                    maxMonth: month,
                    strQuarter: strQuarter,
                    begin: obj.monthBegin,
                    end: obj.monthEnd,
                    count: obj.accordCount
                };
                data.push(packObj);
                return true;
            }
            var bool = false;
            $.each(data, function (j, pack) {
                if (year == pack.year && quarter == pack.quarter) {
                    bool = true;
                    pack.count += obj.accordCount;
                    if (month > pack.maxMonth) {
                        pack.end = obj.monthEnd;
                        pack.maxMonth = month;
                    }
                    if (month < pack.minMonth) {
                        pack.begin = obj.monthBegin;
                        pack.minMonth = month;
                    }
                }
            });
            if (!bool) {
                var packObj = {
                    quarter: quarter,
                    year: year,
                    minMonth: month,
                    maxMonth: month,
                    strQuarter: strQuarter,
                    begin: obj.monthBegin,
                    end: obj.monthEnd,
                    count: obj.accordCount
                };
                data.push(packObj);
            }
        },
        extendParentRate: function (packData, parentData) {
            var hasParent = !$.isEmptyObject(parentData);
            self.hasParent = hasParent;
            $.each(packData, function (i, obj) {
                if (!hasParent) {
                    obj.parentRate = 0;
                    return true;
                }
                var bool = false;
                $.each(parentData, function (j, pObj) {
                    if (obj.year == pObj.year && obj.quarter == pObj.quarter) {
                        obj.parentRate = pObj.rate;
                        bool = true;
                    }
                });
                if (!bool) obj.parentRate = 0;
            });
        },
        calculateRate: function (data) {
            $.each(data, function (i, obj) {
                //计算当前季度流失率
                obj.rate = parseFloat((obj.count / ((obj.begin + obj.end) / 2)).toFixed(4));
                //计算年度流失率
                var runOff = obj.count;
                obj.minYear = obj.minMonth;
                obj.maxYear = obj.maxMonth;
                obj.yearBegin = obj.begin;
                obj.yearEnd = obj.end;
                $.each(data, function (j, child) {
                    if (obj.year == child.year && obj.quarter > child.quarter) {
                        runOff += child.count;
                        if (obj.minYear > child.minMonth) {
                            obj.minYear = child.minMonth;
                            obj.yearBegin = child.begin;
                        }
                        if (obj.maxYear < child.maxMonth) {
                            obj.maxYear = child.maxMonth;
                            obj.yearEnd = child.end;
                        }
                    }
                });

                obj.yearRate = parseFloat((runOff / ((obj.yearBegin + obj.yearEnd) / 2)).toFixed(4));
            });
        },
        //取前n条记录
        toprecords: function (data, number) {
            var das = _.sortBy(data, function (obj) {
                return -parseInt(obj.year.toString() + obj.minMonth);
            });
            var lis = [];
            $.each(das, function (i) {
                if (i < number)
                    lis.push(das[i]);
            });
            lis = _.sortBy(lis, function (obj) {
                return parseInt(obj.year.toString() + obj.minMonth);
            });
            return lis;
        },
        calculateChain: function (data) {
            $.each(data, function (i, obj) {
                if (i == 0) {
                    obj.chain = 0;
                    return true;
                }
                var preRate = data[i - 1].rate;
                obj.chain = preRate != 0 ? (obj.rate - preRate) / preRate : 0;
            });
            if (data.length > 4) {
                data = _.rest(data);
            }
        }
    };



    /***
     * 主动流失率对比chart的option
     * @type {{grid: {borderWidth: number}, dataZoom: {show: boolean, realtime: boolean, y: number, height: number, backgroundColor: string, dataBackgroundColor: string, fillerColor: string, handleColor: string, zoomLock: boolean, showDetail: boolean, handleSize: number, start: number, end: number}, xAxis: *[], yAxis: *[], series: *[]}}
     */
    var contrastBarOption = {
        grid: {
            x: 55,
            y: 40,
            y2: 80,
            x2: 15,
            borderWidth: 0
        },
        dataZoom: {
            show: false,
            realtime: true,
            height: 20,
            zoomLock: true,   //当设置为true时选择区域不能伸缩
            showDetail: false,
            start: 0,
            end: 40
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                onZero: false,
                lineStyle: {
                	color: '#666666',
                	 width: 1
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                	 color: '#666666'
                }
            },
            axisLabel: {
            	itemStyle: {
                    color: '#666666'
                },
                textStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
                show: true,
                lineStyle : {
                	color : '#e4e4e4'
                }
            },
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#666666',
                    width: 1
                }
            },
            axisLabel: {
                show: true,
                formatter: '{value}%'
            }
        }],
        color:  ['#5cb7f1', '#f5b147', '#a88fd7'],
        series: [{
            type: 'bar',
            clickable: false,
            barCategoryGap: '45%',
            barWidth: 30,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: '#333'
                        },
                        formatter: function (i) {
                            return i.value + '%';
                        }
                    }
                }
            },
            data: [],
            markLine: {
                clickable: false,
                symbolSize: [0, 0],
                itemStyle: {
                    normal: {
                        color: '#6FB12D',
                        lineStyle: {
                            type: 'solid'
                        }
                    }
                },
                data: []
            }
        }]
    }

    /***
     * 主动流失率对比grid的option
     * @type {{data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], scroll: boolean}}
     */
    var contrastGridOption = {
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 268,//268
        colNames: ['', '季初人数', '季末人数', '流失人数', '主动流失率'],
        colModel: [
            {
                name: 'organizationName',
                index: 'organizationName',
                width: 70,
                sortable: false,
                align: 'center'
            },
            {
                name: 'beginSum',
                index: 'beginSum',
                width: 70,
                sortable: false,
                align: 'center'
            },
            {
                name: 'endSum',
                index: 'endSum',
                width: 70,
                sortable: false,
                align: 'center'
            },
            {
                name: 'runOffCount',
                index: 'runOffCount',
                width: 70,
                sortable: false,
                align: 'center'
            },

            {
                name: 'dismissRate',
                index: '',
                width: 80,
                fixed: true,
                sortable: false,
                align: 'center',
                formatter: function (cellvalue) {
                    return (cellvalue == null) ? '-' : cellvalue + '%';
                }
            }
        ],
        scroll: true
    };

    /***
     * 主动流失率对比
     * @type {{chartId: string, chartObj: null, chartOption, gridId: string, gridObj: null, init: dismissContrastObj.init, initLineLengend: dismissContrastObj.initLineLengend, initData: dismissContrastObj.initData, getRequestData: dismissContrastObj.getRequestData, generateChart: dismissContrastObj.generateChart, packMarkLineData: dismissContrastObj.packMarkLineData, generateGrid: dismissContrastObj.generateGrid, resizeGrid: dismissContrastObj.resizeGrid, resizeChart: dismissContrastObj.resizeChart}}
     */
    var dismissContrastObj = {
        customizeId:'page-one-group2-2',
        chartId: 'contrastChart',
        chartObj: null,
        chartOption: _.clone(contrastBarOption),
        gridId: '#contrastGrid',
        gridObj: null,
        init: function () {
            var self = this;
            $('#' + self.chartId).height($('#' + self.chartId).parents('.bottom-div').height());
            self.chartObj = initEChart(self.chartId);
            //self.initData(organId);
            self.gridObj = $(self.gridId).jqGrid(contrastGridOption);
        },
        //因为图表中的横线没有图例，故用zrender画一个
        initLineLengend: function () {
            var _ZR = this.chartObj.getZrender();
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 65,
                    y: 310,
                    color: '#6FB12D',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑',
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 40,
                    y: 310,
                    color: '#666',
                    text: '当前组织主动流失率',
                    textAlign: 'left',
                    textFont: 'normal 14px 微软雅黑'
                },
                hoverable: false
            }));
            _ZR.refresh();
        },
        initData: function (organId) {
            var self = this;
            self.resizeChart();
            if (self.organId == organId) {
                return;
            }
            self.organId = organId;
            self.getRequestData(organId);
        },
        getRequestData: function (organId) {
            var self = this;
            $.get(urls.subDismissDataUrl, {organizationId: organId, date: quarterLastDay}, function (data) {
                //因为子集会有一个“合计”，故子集的长度需大于1，才能证明有数据
                if (_.isEmpty(data) || _.isEmpty(data.sub) || data.sub.length <= 1) {
                    showEmptyTip($('#' + self.chartId).parent());
                    self.generateGrid([]);
                    return;
                }
                removeEmptyTip($('#' + self.chartId).parent());
                self.resizeChart();
                //最后一个元素“合计”，不需放入图表 PS:_.initial():返回数组中除了最后一个元素外的其他全部元素
//				if(null==data.curRate){
//					data.curRate=0;
//				}
                //本部门直接取之前查询出的数据
                data.curRate = $('.accord-dismiss-normal .accord-main-number').text();
                $.each(data.sub, function (i, item) {
                    if (null == item.dismissRate) {
                        item.dismissRate = 0;
                    }
                });
                self.generateChart(_.initial(data.sub), data.curRate);
                self.generateGrid(data.sub);
            });
        },
        /**
         * @param subData 子节点数据（柱状图）
         * @param curData 当前节点数据（横线）
         */
        generateChart: function (subData, curRate) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var len=0;
            $.each(subData, function (i, item) {
                category.push(item.organizationName);
                len++;
                barDataArr.push({name: item.organizationName, value: item.dismissRate});
            });
            self.chartOption.dataZoom={
                show: false
            }
            if(len>4){
              self.chartOption.dataZoom={
	              show: true,
	              realtime: true,
	              height: 20,
	              zoomLock: true,   //当设置为true时选择区域不能伸缩
	              showDetail: false,
	              start: 0,
	              end: 40
              }
            }
            self.chartObj.clear();
            self.chartOption.xAxis[0].data = category;
            self.chartOption.series[0].data = barDataArr;
            self.chartOption.series[0].markLine.data = self.packMarkLineData(barDataArr.length, curRate);
            self.chartObj.setOption(self.chartOption, true);
            self.initLineLengend();
        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}]];
        },
        generateGrid: function (data) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                data: data
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#contrastGridArea').width()-tableSpileWidth);
            $('#contrastGridArea').children(".ui-jqgrid ").width($('#contrastGridArea').width()-tableSpileWidth);
        },
        resizeChart: function () {
            this.chartObj.resize();
        }
    };



    /***
     * 人才主动流失率相关饼图对象（人才主动流失绩效分布、人才主动流失层级分布、人才主动流失司龄分布）
     * 饼图的option
     * @type {{title: {show: boolean, text: string, x: string, y: string, textStyle: {fontSize: number}}, color: string[], calculable: boolean, series: *[]}}
     */
    var pieOption = {
//        title: {
//            show: true,
//            text: '',
//            x: 'center',
//            y: 'bottom',
//            textStyle: {
//                fontSize: 14
//            }
//        },
    		tooltip: {
    	        trigger: 'item',
    	        formatter: "{b}<br/>{c} ({d}%)"
    	    },
        color: Tc.defaultPieColor,
        legend: {
            data: [],
            y: 'bottom',
            type:'bar',
            selectedMode: true
        },
        calculable: false,
        series: [{
            type: 'pie',
            clickable: false,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        }
                    },
                    labelLine: {
                        length: 1
                    }
                }
            },
            radius: '55%',
            data: []
        }]
    };

    /***
     * 人才主动流失率相关饼图对象（人才主动流失绩效分布、人才主动流失层级分布、人才主动流失司龄分布）
     * 表格的option
     * @type {{data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], scroll: boolean}}
     */
    var tableOption = {
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
//		altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: ['绩效', '流失人数', '流失占比', '流失率'],
        colModel: [
            {name: 'typeName', index: 'name', width: 120, sortable: false, align: 'center'},
            {name: 'count', index: 'count', width: 120, sortable: false, align: 'center'},
            {
                name: 'occupy',
                index: 'occupy',
                width: 120,
                sortable: false,
                align: 'center',
                formatter: formatPercentage
            },
            {
                name: 'rate',
                index: 'rate',
                width: 150,
                fixed: true,
                sortable: false,
                align: 'center',
                formatter: formatPercentage
            }
        ],
        //rownumbers: true,
        //rownumWidth: 40,
        scroll: true
    }

    /**
     * 该对象数据的依赖于dismissCursorObj的数据，故在dismissCursorObj数据加载完成后，再加载该对象的数据
     * 人才主动流失率相关饼图对象（人才主动流失绩效分布、人才主动流失层级分布、人才主动流失司龄分布）
     * @type {{perfChartId: string, abilityChartId: string, ageChartId: string, colors: *[], init: Function, initData: Function, extendsData: Function, initPie: Function}}
     */
    var accordDismissPieObj = {
        perfChartId: 'perfChart',
        abilityChartId: 'abilityChart',
        ageChartId: 'ageChart',
        perfGridId: '#perfTableGrid',
        abilityGridId: '#abilityTableGrid',
        ageGridId: '#ageTableGrid',
        colNames: ['层级', '司龄'],
        init: function () {
            var self = this;
            var height = 348;
            $('#' + self.perfChartId).height(height);
            $('#' + self.abilityChartId).height(height);
            $('#' + self.ageChartId).height(height);

            self.perfPieObj = initEChart(self.perfChartId);
            self.abilityPieObj = initEChart(self.abilityChartId);
            self.agePieObj = initEChart(self.ageChartId);

            var perfTableOption = $.extend(true, {}, tableOption);
            $(self.perfGridId).jqGrid(perfTableOption);

            var abilityTableOption = $.extend(true, {}, tableOption);
            abilityTableOption.colNames[0] = self.colNames[0];
            $(self.abilityGridId).jqGrid(abilityTableOption);

            var ageTableOption = $.extend(true, {}, tableOption);
            ageTableOption.colNames[0] = self.colNames[1];
            $(self.ageGridId).jqGrid(ageTableOption);
        },
        initData: function (organId) {
            var self = this;
            if (self.organId != organId) {
                self.organId = organId;
                self.extendsData(organId);
                return;
            }
            var perfData = self.perfData;
            if (!$.isEmptyObject(perfData)) {
                self.initPie(self.perfPieObj, _.initial(perfData));
                self.initGrid(self.perfGridId, self.perfData);
            }
            var abilityData = self.abilityData;
            if (!$.isEmptyObject(abilityData)) {
                self.initPie(self.abilityPieObj, _.initial(abilityData));
                self.initGrid(self.abilityGridId, abilityData);
            }
            var companyAgeData = self.companyAgeData;
            if (!$.isEmptyObject(companyAgeData)) {
                self.initPie(self.agePieObj, _.initial(companyAgeData));
                self.initGrid(self.ageGridId, companyAgeData);
            }
        },
        extendsData: function (organId) {
            var self = this;
            $.post(urls.getQuarterDismissUrl, {'organId': organId, 'prevQuarter': quarterLastDay}, function (rs) {
                var quarterCount = dismissObj.quarterCount;
                if (_.isEmpty(rs.ability)) {
                    showEmptyTip($('#' + self.abilityChartId).parent());
                } else {
                    removeEmptyTip($('#' + self.abilityChartId).parent());
                }
                self.abilityData = self.extendsResultData(rs.ability, quarterCount);
                if (_.isEmpty(rs.companyAge)) {
                    showEmptyTip($('#' + self.ageChartId).parent());
                } else {
                    removeEmptyTip($('#' + self.ageChartId).parent());
                }
                self.companyAgeData = self.extendsResultData(rs.companyAge, quarterCount);
                if (_.isEmpty(rs.pref)) {
                    showEmptyTip($('#' + self.perfChartId).parent());
                } else {
                    removeEmptyTip($('#' + self.perfChartId).parent());
                }
                self.perfData = self.extendsResultData(rs.pref, quarterCount);
                self.initData(organId);
            });
        },
        initGrid: function (gridId, paramData) {
            var self = this;
            $(gridId).clearGridData().setGridParam({
                data: paramData
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.perfGridId).setGridWidth($('#perfGridArea').width()-tableSpileWidth);
            $('#perfGridArea').children(".ui-jqgrid ").width($('#perfGridArea').width()-tableSpileWidth);
            $(self.abilityGridId).setGridWidth($('#abilityGridArea').width() -tableSpileWidth);
            $('#perfGridArea').children(".ui-jqgrid ").width($('#perfGridArea').width()-tableSpileWidth);
            $(self.ageGridId).setGridWidth($('#ageGridArea').width() -tableSpileWidth);
            $('#ageGridArea').children(".ui-jqgrid ").width($('#ageGridArea').width()-tableSpileWidth);
        },
        initPie: function (pieObj, resultData) {
            pieObj.clear();
            var self = this;
            var newPieOption = _.clone(pieOption);
            var seriesData = [];
            var total = 0;
            var legend=[];
            $.each(resultData, function (i, obj) {
                total += obj.count == '-' ? 0 : obj.count;
            });
            $.each(resultData, function (i, obj) {
                if (obj.count == '-' || obj.count <= 0) {
                    return true;
                }
                legend.push({name:obj.typeName,icon:'bar'});
                seriesData.push(formatPieData(obj.typeName, obj.count, total));
            });
            newPieOption.series[0].data = seriesData;
            newPieOption.legend.data=legend;
//            self.initLengend(pieObj, resultData, newPieOption.color);
            pieObj.setOption(newPieOption, true);
        },
        resizeChart: function () {
            var self = this;
            self.perfPieObj.resize();
            self.abilityPieObj.resize()
            self.agePieObj.resize();
        },
        extendsResultData: function (source, quarterCount) {
            var dismissCount = 0;
            $.each(source, function (i, obj) {
                dismissCount += obj.runOffCount;
            });
            $.each(source, function (i, obj) {
                var runOffCount = obj.runOffCount;
                obj.count = obj.workingCount == 0 ? '-' : runOffCount;
                obj.occupy = runOffCount / dismissCount;
                obj.rate = runOffCount / quarterCount;
            });
            var newObj = {'typeName': '总计', 'count': dismissCount, 'occupy': 0, 'rate': (dismissCount / quarterCount)};
            source.push(newObj);
            return source;
        },
        //因为图表中没有图例，故用zrender画一个
        initLengend: function (_chartObj, data, colors) {
            var self = this;
            var _ZR = _chartObj.getZrender();
            var len = data.length, ids = 0;
            $.each(data, function (i, obj) {
                if (obj.count == '-' || obj.count <= 0) {
                    return true;
                }
                _ZR.addShape(new TextShape({
                    style: {
                        x: 0,
                        y: 10 + ids * 19,
                        color: colors[ids],
                        text: '▅',
                        textAlign: 'left',
                        textFont: 'bolder 17px 微软雅黑'
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: 21,
                        y: 13 + (ids * 19),
                        color: '#000',
                        text: obj.typeName,
                        textAlign: 'left',
                        textBaseline: 'middle',
                        textFont: 'border 15px 微软雅黑'
                    },
                    hoverable: false
                }));
                ids++;
            });
            //$.each(data, function (i, obj) {
            //    var num = i - parseInt(len / 2);
            //    var ids = i == 0 ? 0 : i - 1;
            //    var nameLen = self.getLength(data[ids].typeName);
            //    _ZR.addShape(new TextShape({
            //        style: {
            //            x: _ZR.getWidth() - 15,
            //            y: _ZR.getHeight() / 2 + (num * 30),
            //            color: colors[i],
            //            text: '▅',
            //            textAlign: 'right',
            //            textFont: 'bolder 18px 微软雅黑'
            //        },
            //        hoverable: false
            //    }));
            //    _ZR.addShape(new TextShape({
            //        style: {
            //            x: _ZR.getWidth() - 35,
            //            y: _ZR.getHeight() / 2 + (num * 29 + (num + 3)),
            //            color: '#666',
            //            text: obj.typeName,
            //            textAlign: 'right',
            //            textFont: 'normal 12px 微软雅黑'
            //        },
            //        hoverable: false
            //    }));
            //});
            _ZR.refresh();
        },
        getLength: function (str) {
            var realLength = 0, len = str.length, charCode = -1;
            for (var i = 0; i < len; i++) {
                charCode = str.charCodeAt(i);
                if (charCode >= 0 && charCode <= 128) realLength += 1;
                else realLength += 2;
            }
            return realLength;
        }
    };
    accordDismissPieObj.init();


    /***
     * 流失原因及去向-横向柱状图的option
     * @type {{calculable: boolean, grid: {borderWidth: number, x: number}, xAxis: *[], yAxis: *[], series: *[]}}
     */
    var abnormalBarOption = {
        calculable: false,
        color:  ['#5cb7f1', '#f5b147', '#a88fd7'],
        grid: {
            borderWidth: 0,
            x: 140
//            y: 10,
//            y2: 35
        },
        xAxis: [{
            type: 'value',
            axisLine: {
                show: false,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            }
        }],
        yAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#666666'
                }
            },
            axisTick: {show: false},
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#666666'
                }
            },
            data: []
        }],
        series: [{
            name: '',
            barWidth: 30,
            type: 'bar',
            clickable: false,
            itemStyle: {
                normal: {
//                    color: barColor[0],
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        },
                        formatter: function (i) {
                            return Tc.formatFloat(i.value)+"人";
                        }
                    }
                }
            },
            barGap: 0,	//柱之类的距离
            barCategoryGap: '45%',		//分类柱之间的距离
            data: []
        }]
    };

    var dismissTypeEnum = {
        1: {name: '主动流失'},
        2: {name: '被动流失'},
        3: {name: '其他'}
    };
    var reHiredEnum = {
        1: {name: '建议重新录用'},
        2: {name: '不建议重新录用'},
        3: {name: '其他'}
    };
    /**
     * 流失原因和流失去向
     */
    var dismissCauseObj = {
        chartObjs: {
            reasonPieObj: initEChart('dismissReasonPie'),
            reasonBarObj: initEChart('dismissReasonBar'),
            gonePieObj: initEChart('dismissGonePie'),
            goneBarObj: initEChart('dismissGoneBar')
        },
        chartOptions: {
            reasonPieOption: _.clone(pieOption),
            reasonBarOption: _.clone(abnormalBarOption),
            gonePieOption: _.clone(pieOption),
            goneBarOption: _.clone(abnormalBarOption)
        },
        init: function (organId) {
            this.chartOptions.goneBarOption.color = [dismissTypeEnum[1].color];
        },
        initData: function (organId) {
            var self = this;
            self.resizeChart();
            if (self.organId == organId) {
                return;
            }
            self.organId = organId;
            self.getRequestData(organId);
        },
        getRequestData: function (organId) {
            var self = this;
            $.get(urls.dismissRecordUrl, {organizationId: organId, prevQuarter: quarterLastDay}, function (data) {
                self.resultData = data;
                self.generateDetail(data);
            });
        },
        generateDetail: function (data) {
            var self = this;
            if (_.isEmpty(data)) {
                showEmptyTip($('#page-two .bottom-div-two>.row'));
                return;
            }
            removeEmptyTip($('#page-two .bottom-div-two>.row'));
            self.resizeChart();
            //流失类型、流失名称类型、建议录取与否、流失去向
            var typeData = {}, typeNameData = {}, reHiredData = {}, whereAboutsData = {};
            //
            $.each(data, function (i, item) {
                self.setDataByKey(typeData, item.type);
                self.setDataByKey(typeNameData, item.runOffName || '其他');
                self.setDataByKey(reHiredData, item.reHire);
                self.setDataByKey(whereAboutsData, item.whereAbouts);
                //用于生成柱状图时，标识颜色
                typeNameData[item.runOffName || '其他'].type = item.type;
            });
            var chartOptions = self.chartOptions;
            var chartObjs = self.chartObjs;
            //流失原因
            self.initPie(typeData, chartOptions.reasonPieOption, chartObjs.reasonPieObj, dismissTypeEnum);
            self.initBar(typeNameData, chartOptions.reasonBarOption, chartObjs.reasonBarObj, true);
            //流失去向
            self.initPie(reHiredData, chartOptions.gonePieOption, chartObjs.gonePieObj, reHiredEnum);
            self.initBar(whereAboutsData, chartOptions.goneBarOption, chartObjs.goneBarObj, false);
        },
        setDataByKey: function (obj, key) {
            if (!obj[key]) {
                obj[key] = {};
                obj[key].value = 1;
            } else {
                obj[key].value++;
            }
        },
        /**
         * 生成柱状图
         * @param data 数据
         * @param chartOption 图表的option
         * @param chartObj 图表对象
         * @param changeColor 是否需要改变柱子的颜色
         */
        initBar: function (data, chartOption, chartObj, changeColor) {
            var self = this;
            var category = [];
            var valArr = [];
            var list1 = [];
            var list2 = [];
            var list = [];
            $.each(data, function (i, item) {
                if (item.type == 1)
                    list1.push({"name": i, "type": item.type, "value": item.value});
                else if (item.type == 2)
                    list2.push({"name": i, "type": item.type, "value": item.value});
                else
                    list.push({"name": i, "type": item.type, "value": item.value});
            });
            var listsSort1 = _.sortBy(list1, function (o) {
                return o.value;
            });
            var listsSort2 = _.sortBy(list2, function (o) {
                return o.value;
            });
            var listsSort = _.sortBy(list, function (o) {
                return o.value;
            });
            var listsSort = _.union(listsSort2, listsSort1, listsSort);
            $.each(listsSort, function (i, item) {
                category.push(item.name);
                var newItem = {value: item.value};
//                if (changeColor) {
//                    newItem.itemStyle = self.getItemColorStyle(item.type);
//                }
                valArr.push(newItem)
            });
            chartOption.yAxis[0].data = category;
            chartOption.series[0].data = valArr;
            chartObj.setOption(chartOption, true);
        },
        /**
         * 生成饼图
         * @data 数据
         * @chartOption 图表的option
         * @chartObj 图表对象
         * @nameEnum 显示文本的枚举类型
         */
        initPie: function (data, chartOption, chartObj, nameEnum) {
            var self = this;
            //统计总数
            var totalEmp = _.reduce(data, function (memo, num) {
                return memo + num.value;
            }, 0);
            var pieDataArr = [];
            var legend=[];
            $.each(data, function (i, item) {
                var pieDataObj = formatPieData(nameEnum[i].name, item.value, totalEmp);
//                pieDataObj.itemStyle = self.getItemColorStyle(i);
                pieDataArr.push(pieDataObj);
                legend.push({name:nameEnum[i].name,icon:'bar'});
            })
             chartOption.legend.data = legend;
            chartOption.series[0].data = pieDataArr;
            chartObj.setOption(chartOption, true);
        },
//        getItemColorStyle: function (type) {
//            return {
//                normal: {color: dismissTypeEnum[type].color}
//            };
//        },
        resizeChart: function () {
            $.each(this.chartObjs, function (i, item) {
                item.resize();
            });
        }
    };



    /***
     * 流失率明细grid的option
     * @type {{url: *, datatype: string, postData: {}, mtype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], rownumbers: boolean, rownumWidth: number, viewrecords: boolean, rowNum: number, rowList: number[], pager: string, loadComplete: dismissGridOption.loadComplete}}
     */
    var tableHeight = 322;
    var dismissGridOption = {
        url: urls.getRunOffDetailUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,
        colNames: ['姓名', '所属组织', '岗位', '序列', '层级', '绩效', '流失类型', '流失时间', '流失去向'],
        colModel: [
            {
                name: 'userNameCh',
                index: 'userNameCh',
                width: 180,
                sortable: false,
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }

                    return "<a href='javascript:void(0)' data='" + row.empId + "' class='talent_col' >" + value + "</a>";
                }
            },
            {name: 'organizationName', index: 'organizationName', width: 130, sortable: false, align: 'center'},
            {name: 'positionName', index: 'positionName', width: 130, sortable: false, align: 'center'},
            {name: 'sequenceName', index: 'sequenceName', width: 100, fixed: true, sortable: false, align: 'center'},
            {name: 'abilityName', index: 'abilityName', width: 100, fixed: true, sortable: false, align: 'center'},
            {
                name: 'performanceName',
                index: 'performanceName',
                width: 60,
                fixed: true,
                sortable: false,
                align: 'center'
            },
            {name: 'roTypeToString', index: 'roType', width: 80, fixed: true, sortable: false, align: 'center'},
            {
                name: 'roDateToString',
                index: 'roDateToString',
                width: 110,
                fixed: true,
                sortable: false,
                align: 'center'
            },
            {name: 'whereAbouts', index: 'whereAbouts', width: 140, fixed: true, sortable: false, align: 'center'},
        ],
        rownumbers: true,
        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#runOffDetailSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $('.talent_col').unbind().bind('click', function () {
                var _this = $(this);
                var empId = _this.attr('data');
                var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random();
                window.open(herf);
            });
            $("#runOffDetailGrid").children().children(".jqgfirstrow").children(":last").width(160);
            $("#runOffDetailGrid_whereAbouts").width(160);
            $("#runOffDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 40);
//            //约束iframe高度
//            $("#dismissDetailTab .transparent").height(580);
//            $("#runOffDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 40);
        }
    };
    /***
     * 流失率明细搜索条件
     * @type {{url: *, lazy: boolean, attach: *[], onClick: searchBoxConfig.onClick, close: searchBoxConfig.close, expand: searchBoxConfig.expand, loadComple: searchBoxConfig.loadComple}}
     */
    var searchBoxConfig = {
        url: urls.searchBoxUrl, lazy: true,
        attach: [{
            label: "流失时间",
            type: "date",
            data: [{
                name: "beginDate",
                format: "yyyy-mm-dd",
                date: prevQuarterFirstDay,
                end: quarterLastDay
            }, {name: "endDate", format: "yyyy-mm-dd", date: quarterLastDay, end: quarterLastDay}]
        }],
        // 重写组件里的onClick事件
        onClick: function () {
            runOffDetailObj.getRequestData(null);
        }, close: function (w, h) {
            //$("#panel_height").height($("#panel_height").height()-h-300);
        }, expand: function (w, h) {
            //$("#panel_height").height($("#panel_height").height()+h+300);
        }, loadComple: function (o) {
            $("#searchBtn").click(function () {
                var searchTxt = $.trim($("#searchTxt").val());
                if (searchTxt != "") {
                    //var orgId = $("#recordSelectTreeOrgId").val();
                    $(runOffDetailObj.gridId).clearGridData().setGridParam({
                        postData: {organizationId: reqOrgId, keyName: searchTxt, queryType: 1}
                    }).trigger("reloadGrid");
                    runOffDetailObj.resizeGrid();
                }
            });
            $("#searchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#searchBtn").click();
                }
            })
        }
        //   height : 420
    };

    /***
     * 流失率明细
     * @type {{searchBoxId: string, gridId: string, clearCondBtnId: string, resultData: null, init: runOffDetailObj.init, getRequestData: runOffDetailObj.getRequestData, initData: runOffDetailObj.initData, resizeGrid: runOffDetailObj.resizeGrid}}
     */
    var runOffDetailObj = {
    	customizeId:'page-three-group1-1',
        searchBoxId: "#searchBox",
        gridId: '#runOffDetailGrid',
        clearCondBtnId: '#clearConditionBtn',
        resultData: null,
        init: function (organId) {
            var self = this;
            self.searchBox = $(self.searchBoxId).searchBox3(searchBoxConfig);
            dismissGridOption.postData = {
                organizationId: organId,
                beginDate: prevQuarterFirstDay,
                endDate: quarterLastDay,
                queryType: 2
            };
            $(self.gridId).jqGrid(dismissGridOption);
        },
        getRequestData: function (organId) {
            var self = this;
            // 存起。重新选seachBox的参数时，没有带上的机构Id
            if (organId != null) {
                self.organId = organId
            }
            var _organId = self.organId;
            var params = $.extend(true, {}, self.searchBox.getSelectData(), {organizationId: _organId, queryType: 2});
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        initData: function (organId, date) {
            var self = this;
            self.resizeGrid();
            self.organId = organId;
            self.getRequestData(organId);
        },
        resizeGrid: function () {
            var self = this;
//            alert($('#runOffDeailTable').width())
            $(self.gridId).setGridWidth($('#runOffDeailTable').width());
//            $("#runOffDetailSel").width($('#runOffDeailTable').width()-15);
        }
    };
  


    /*
     筛选条件点击事件
     */
    $(".condition-btn").click(function () {
        if ($(this).hasClass("condition-btn-selected")) return;

        $(this).parents(".condition-body-list").find(".condition-btn").removeClass("condition-btn-selected");
        $(this).addClass("condition-btn-selected");
    });

    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData(targetAreaId) {
        var selOrganId = reqOrgId;
        if (targetAreaId == 'page-three') {
            //流失人员明细
            runOffDetailObj.initData(selOrganId);
        } else if (targetAreaId == 'page-two') {
            //流失原因及去向
            dismissCauseObj.initData(selOrganId);
        } else {
            //主动流失率
            initFirstTabData(selOrganId);
        }
    }


    /*
     筛选条件选中鼠标触摸事件
     */
    $(".condition-btn-too").mouseover(function () {
        if (!$(this).hasClass("condition-btn-selected")) return;

        $(this).append('<div class="condition-btn-too-icon"></div>');
        $(this).css("paddingLeft", "5px");
        $(this).css("paddingRight", "18px");
    });

    $(".condition-btn-too").mouseout(function () {
        if (!$(this).hasClass("condition-btn-selected")) return;

        $(this).find(".condition-btn-too-icon").remove();
        $(this).css("paddingLeft", "10px");
        $(this).css("paddingRight", "10px");

    });
    /*
     筛选条件取消事件
     */
    $(".condition-btn-too").click(function () {
        if ($(this).find(".condition-btn-too-icon").length == 0) return;

        $(this).find(".condition-btn-too-icon").remove();
        $(this).css("paddingLeft", "10px");
        $(this).css("paddingRight", "10px");
        $(this).parents(".condition-body-list").find(".condition-btn").removeClass("condition-btn-selected");
        $(this).parents(".condition-body-list").find(".condition-btn").eq(0).addClass("condition-btn-selected");
    });


    $('.LSLSetUp').click(function () {
        $('#warnSettingModal').modal('show');
        if (!_.isUndefined(dismissObj.normal)) {
            $('#normalDismiss').val(Tc.formatFloat(dismissObj.normal * 100));
            $('#riskDismiss').val(Tc.formatFloat(dismissObj.risk * 100));
        }

        //确认按钮
        $('#warnSettingModal .initDragBtn').unbind('click').bind('click', function () {
            var normal = parseFloat($.trim($('#normalDismiss').val()));
            var risk = parseFloat($.trim($('#riskDismiss').val()));
            if (normal == '' || normal < 0) {
                $('#normalDismiss')[0].focus();
                return false;
            }
            if (risk == '' || risk <= normal || risk > 99) {
                $('#riskDismiss')[0].focus();
                return false;
            }
            var normalRs = dismissObj.normal = normal / 100;
            var riskRs = dismissObj.risk = risk / 100;
            var max = riskRs + riskRs / 2 > 1 ? 1 : riskRs + riskRs / 2;

            $.post(urls.updateDismissValueUrl, {'normal': normalRs, 'risk': riskRs}, function (rs) {
                if (rs.type) {
                    dismissObj.initChart(normalRs, riskRs, max);

                    $('#warnSettingModal').modal('hide');
                }
            });
        });
    });

    /***
     * 主动流失率基础配置
     */
    $(".leftListSet").click(function () {
        baseConfigObj.init();
        $('#baseSettingModal').modal('show');
        //确定按钮
        $("#baseSettingModal .initDragBtn").unbind('click').bind('click', function () {
            var notify = $('#notifyConfig').val();
            var persons = $('input[name="YJObject"]:checked').map(function (i, e) {
                return $(e).val();
            }).get().join(",");
            var terminals = $('input[name="YJMode"]:checked').map(function (i, e) {
                return $(e).val();
            }).get().join(",");
            baseConfigObj.updateRequestData({terminals: terminals, persons: persons, notify: notify});
        });
    });

    var baseConfigObj = {
        bool: false,
        init: function () {
            var self = this;
            if (!self.bool) {
                self.getRequestData();
            }
        },
        initConfigHtml: function (data) {
            var self = this;
            var terminals = data.terminals, persons = data.persons, notify = data.notify;
            $('#notifyConfig').val(notify);
            $.each(persons, function (i, item) {
                $('input[name="YJObject"][value="' + item + '"]').prop('checked', true);
            });
            $.each(terminals, function (i, item) {
                $('input[name="YJMode"][value="' + item + '"]').prop('checked', true);
            });
        },
        getRequestData: function () {
            var self = this;
            $.post(urls.getDismissBaseConfigUrl, function (rs) {
                if (!_.isNull(rs)) {
                    self.initConfigHtml(rs);
                }
            });
        },
        updateRequestData: function (params) {
            $.post(urls.updateDismissBaseConfigUrl, {
                terminals: params.terminals.length == 0 ? 0 : params.terminals,
                persons: params.persons.length == 0 ? 0 : params.persons,
                notify: params.notify
            }, function (rs) {
                if (rs.type) {
                    $('#baseSettingModal').modal('hide');
                }
            });
        }
    }


    /**
     * 加载顶级的第一个tab页的数据（即“主动流失率”tab页）
     */
    function initFirstTabData(organId) {
        dismissObj.initData(organId);
        timeLineObj.init(organId);
        dismissContrastObj.initData(organId);
        trendChartObj.initData(organId);
        keyPerfDismissObj.initData(organId);
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
     * 返回当前焦点tab页显示的区域id
     */
    function getActiveTabId(targetDom) {
        var _currObj = _.find($(targetDom), function (obj) {
            return $(obj).hasClass('selectList');
        });
        return _currObj != null ? $(_currObj).attr('page') : 'page-one';
    }

    function showEmptyTip($targetDom) {
        //如果是显示状态
        if ($targetDom.find('.empty-tip').length != 0)  return;

        var domHeight = $targetDom.height() || 100;
        $targetDom.children().hide();
        var emptyTipStyle = 'height:' + domHeight + 'px;line-height:' + domHeight + 'px;';
        $targetDom.append('<div class="empty-tip" style="' + emptyTipStyle + '">暂无数据</div>');
    }

    function removeEmptyTip($targetDom) {
        $targetDom.find('.empty-tip').remove();
        $targetDom.children().show();
    }

    function formatPieData(name, value, total) {
        if (!value) return {};
        //页面展示为 ：name，value，(换行)percent%
        return {
            value: value,
//            name: (name + '，' + Tc.formatNumber(value) + '，\n' + ((value / total) * 100).toFixed(0) + '%')
            name:name,
        };
    }

    function formatPercentage(value) {
        if (!value) return '-';

        return value == 0 ? '-' : Math.round(value * 10000) / 100 + '%';
    }

    function firstDate(end) {
        var d = new Date(end);
        var month = d.getMonth();
        var firstMonth = 1;
        if (month >= 4 && month <= 6)
            firstMonth = 4;
        else if (month >= 7 && month <= 9)
            firstMonth = 7;
        else if (month >= 10 && month <= 12)
            firstMonth = 10;
        return (d.getFullYear()) + "-" + firstMonth + "-01";
    }

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        var _self = $(this);
        if (_self.hasClass("rightSetUpBtnSelect")) return;
        _self.parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        _self.addClass("rightSetUpBtnSelect");
        var _SetUpBody = _self.parents(".SetUpBody");
        if (_SetUpBody.attr("view") == "chart") {
            _SetUpBody.find(".table-view").show();
            trendChartObj.resizeGrid();
            dismissContrastObj.resizeGrid();
            accordDismissPieObj.resizeGrid();
            _SetUpBody.find(".chart-view").hide();
            _SetUpBody.attr("view", "table");
        } else {
            _SetUpBody.find(".chart-view").show();
            _SetUpBody.find(".table-view").hide();
            _SetUpBody.attr("view", "chart");
            trendChartObj.resizeChart();
            dismissContrastObj.resizeChart();
            accordDismissPieObj.resizeChart();
        }
    });
//   	execute([dismissObj,
//    	      timeLineObj,
//    	    trendChartObj,
//    	    dismissContrastObj,
//    	    runOffDetailObj
//              ]);
    	
   dismissObj.init();
   trendChartObj.init();
   dismissContrastObj.init();
   runOffDetailObj.init(reqOrgId);
   
   initFirstTabData(reqOrgId);
    $(window).resize(function () {
        var $page = $('.leftListDiv.selectList').attr('page');
        if ($page == 'page-one') {
            dismissObj.resizeChart();
            trendChartObj.resizeChart();
            trendChartObj.resizeGrid();
            dismissContrastObj.resizeChart();
            dismissContrastObj.resizeGrid();
            accordDismissPieObj.resizeChart();
            accordDismissPieObj.resizeGrid();
        } else if ($page == 'page-two') {
            dismissCauseObj.resizeChart();
        }
    });
    
    
});