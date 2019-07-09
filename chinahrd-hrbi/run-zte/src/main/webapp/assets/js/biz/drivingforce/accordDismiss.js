require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'jgGrid', 'underscore', 'utils', 'organTreeSelector', 'vernierCursor', 'timeLine', 'searchBox3', 'resize'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        queryAccordDismissUrl: webRoot + '/accordDismiss/queryDisminss4Quarter.do', //查询主动流失率信息
        getKeyDismissDataUrl: webRoot + '/accordDismiss/getKeyDismissData.do', //获取“关键人才流失人数”的数据
        getPerfDismissDataUrl: webRoot + '/accordDismiss/getPerfDismissData.do', //获取“高绩效流失人数”的数据
        dismissRecordUrl: webRoot + '/accordDismiss/getDismissRecord.do',	//流失原因
        subDismissDataUrl: webRoot + '/accordDismiss/getSubDismissData.do',	//本部与下属部门流失率对比数据
        searchBoxUrl: webRoot + '/common/getSearchBox.do',	//筛选条件信息
        timeScopeUrl: webRoot + '/common/getTimeScope.do',	//筛选条件信息
        getRunOffDetailUrl: webRoot + '/accordDismiss/queryRunOffDetail.do',	//流失人员明细
        getDismissTrendUrl: webRoot + '/accordDismiss/queryDismissTrend.do',	//主动流失率趋势
        getQuarterDismissUrl: webRoot + '/accordDismiss/quarterDismissInfo.do',
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do'    //跳转到员工详情//季度流失人员统计信息
    };

    //TODO 写死高绩效的起点值，后期可能需改成配置
    var highPerfBegin = 4;

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var quarterLastDay = $('#quarterLastDay').val();
    var prevQuarterFirstDay = firstDate(quarterLastDay);
    var ecConfig = require('echarts/config');
    var TextShape = require('zrender/shape/Text');

    function generateInput(orgId) {
        var recordInput = $("<input type='hidden' id='recordSelectTreeOrgId'/>");
        $("body").append(recordInput);
        $("#recordSelectTreeOrgId").val(orgId);
    }

    generateInput(reqOrgId);
    function firstDate(end) {
        var d = new Date(end);
        var month = d.getMonth();
        /*
         var firstMonth=month-2;
         if(firstMonth<1){
         firstMonth+=12;
         if(firstMonth<10){
         firstMonth="0"+firstMonth;
         }
         return (d.getFullYear()-1)+"-"+firstMonth+"-01";
         }else{
         if(firstMonth<10){
         firstMonth="0"+firstMonth;
         }
         return (d.getFullYear())+"-"+firstMonth+"-01";
         }
         */
        var firstMonth = 1;
        if (month >= 4 && month <= 6)
            firstMonth = 4;
        else if (month >= 7 && month <= 9)
            firstMonth = 7;
        else if (month >= 10 && month <= 12)
            firstMonth = 10;
        return (d.getFullYear()) + "-" + firstMonth + "-01";
    }

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        runOffDetailObj.searchBox.clear();
        $("#searchTxt").val("");
        $("#recordSelectTreeOrgId").val(organId);
        var curTopTabId = getActiveTabId("#dimissTypeTabs");
        changeData(curTopTabId);
    }


    /**
     * 主动流失率
     * @type {{cursor: string, cursorOptions: {title: string, width: number, split: number, colors: number, data: number[], value: number, onClick: Function}, init: Function, resize: Function}}
     */
    var dismissCursorObj = {
        cursor: '#accordDismissCursor',
        quarterCount: 0,
        cursorOptions: {
            title: '主动流失率',
            width: 245,	//默认宽度
            split: 15,		//游标尺的分度
            colors: 1,		//默认颜色样式
            data: [0.05, 0.1, 0.15],	//分段刻度
            value: 0.061,		//值
            onClick: function (rs) {		//点击确定之后的回调，可做保存操作
                console.log(rs);
            }
        },
        init: function () {
            var self = this;
            var _this = $(self.cursor);
            self.cursorOptions.width = _this.width();
        },
        initData: function (organId) {
            var self = this;
            self.resize();
//            if(self.organId == organId){
//            	return;
//            }
            self.organId = organId;
            $.ajax({
                url: urls.queryAccordDismissUrl,
                type: "post",
                async: false,
                data: {'organId': organId, 'prevQuarter': quarterLastDay},
                success: function (rs) {
                    if (!$.isEmptyObject(rs.dismissTrend)) {
                        var trend = rs.dismissTrend;
                        var count = trend.monthCount;
                        self.quarterCount = count;
                        self.cursorOptions.value = trend.rate;
                        $(self.cursor).vernierCursor(self.cursorOptions);
                        accordDismissPieObj.initData(organId);
                    }
                }
            });

        },
        resize: function () {
            var self = this;
            var _this = $(self.cursor);
            self.cursorOptions.width = _this.width();
            _this.vernierCursor(self.cursorOptions);
        }
    };
    dismissCursorObj.init();

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
            var prevColor = '', preValueStr = '';
            if (preValue != null) {
                preValueStr = (preValue >= 0 ? '+' : '') + preValue;
                prevColor = preValue > 0 ? 'red' : 'blue';
            }
            $('#' + containerId).find('.lastQuarter').removeClass('red blue').addClass(prevColor).text(preValueStr);
            $('#' + containerId).find('.currentQuarter').text(data.cur || 0);
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
     * 备忘录
     */
    var timeLineObj = {
        init: function (organizationId) {
            var self = this;
//    		if(organizationId == self.organizationId){
//    			return;
//    		}
            self.organizationId = organizationId;
            var quotaId = $('#quotaId').val();
            $.post(urls.memoUrl, {
                'quotaId': quotaId,
                'organizationId': organizationId
            }, function (rs) {
                $('#timeLine').timeLine(self.getOption(rs));	//初始化
                $(".modal-backdrop").hide();
            });
        },
        getOption: function (rs) {
            var organizationId = this.organizationId;
            var quotaId = $('#quotaId').val();
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
                            timeLineObj.init(organizationId);
                            $(".modal-backdrop").hide();
//                        	 setTimeout(function() {
//                        		timeLineObj.init(organizationId);		//重新加载
//         					}, 100);
                        }
                    });
                }
            }
            return options;
        }
    }

    /**
     * 主动流失率趋势
     * @type {{chartId: string, gridId: string, chartObj: null, chartOption: {legend: {data: string[], selected: {上级组织均值: boolean}, y: string}, grid: {borderWidth: number}, color: string[], xAxis: *[], yAxis: *[], series: *[]}, tableOption: {data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], scroll: boolean}, init: Function, initData: Function, initGrid: Function, resizeGrid: Function, initChartData: Function, requestData: Function, extendData: Function, extendChildData: Function, extendParentRate: Function, calculateRate: Function, calculateChain: Function}}
     */
    var trendChartObj = {
        chartId: 'trendChart',
        gridId: '#trendTableGrid',
        chartObj: null,
        chartOption: {
            legend: {
                data: ['环比变化', '主动流失率', '上级组织均值'],
                selected: {'上级组织均值': false},
                y: 'bottom'
            },
            grid: {
                borderWidth: 0
            },
            color: ['#23C6C8', '#EA711E', '#A5A5A5'],
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
            yAxis: [{
                type: 'value',
                splitLine: false,
                axisLine: {
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        return (value * 100) + '%';
                    }

                }
            }],
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'none'},
                formatter: function (params, ticket, callback) {
                    var html = "<div>" + params[0].name + "<br>环比变化：" + (params[0].data * 10000 / 100).toFixed(2) + "%<br>主动流失率：" + (params[1].data * 10000 / 100).toFixed(2) + "%<div>";
                    return html;
                }
            },
            series: [{
                name: '环比变化',
                type: 'bar',
                clickable: false,
                barCategoryGap: '45%',
                barMaxWidth: 40,
                data: []
            }, {
                name: '主动流失率',
                type: 'line',
                clickable: false,
                yAxisIndex: 0,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (a, b, c) {
                                return formatPercentage(c);
                            }
                        }
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
                            formatter: function (a, b, c) {
                                return formatPercentage(c);
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
            altRows: true,//设置表格行的不同底色
//			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
            height: 268,//268
            colNames: ['', '当前季度', '当年累计', '上级组织季度均值'],
            colModel: [
                {name: 'strQuarter', index: 'strQuarter', width: 120, sortable: false},
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
            $('#' + self.chartId).height(280);
            self.chartObj = echarts.init(document.getElementById(self.chartId));
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
            $(this.gridId).setGridWidth($('#trendGridArea').width() * 0.98);
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
            chartOption.series[0].data = chainData;
            chartOption.series[1].data = dismissRateData;

            if (hasParent) {
                chartOption.series[2].data = parentRateData;
            } else {
                chartOption.legend.data = chartOption.legend.data.slice(0, 2);
                chartOption.color = chartOption.color.slice(0, 2);
                chartOption.series = chartOption.series.slice(0, 2);
            }
            self.extendsOption = chartOption;
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
            parentData = self.toprecords(parentData, highPerfBegin); //只取前4条
            self.extendParentRate(packData, parentData);
            packData = self.toprecords(packData, highPerfBegin);  //只取前4条
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
                obj.rate = Math.round(obj.count / ((obj.begin + obj.end) / 2) * 10000) / 10000;
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
                obj.yearRate = Math.round(runOff / ((obj.yearBegin + obj.yearEnd) / 2) * 10000) / 10000;
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
    trendChartObj.init();

    //饼图option(默认是人才主动流失绩效分布)
    var pieOption = {
        title: {
            show: true,
            text: '',
            x: 'center',
            y: 'bottom',
            textStyle: {
                fontSize: 14
            }
        },
        color: ['#1AB394', '#23C6C8', '#79D2C0', '#BABABA', '#D3D3D3'],
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

    /**
     * 本部与下属部门流失率对比的option
     */
    var contrastBarOption = {
        grid: {
            borderWidth: 0
        },
        dataZoom: dataZoom,
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
        yAxis: [{
            type: 'value',
            splitLine: false,
            axisLine: {
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisLabel: {
                formatter: '{value}%'
            }
        }],
        series: [{
            type: 'bar',
            clickable: false,
            barCategoryGap: '45%',
            barMaxWidth: 40,
            itemStyle: {
                normal: {
                    color: '#23C6C8',
                    label: {
                        show: true,
                        textStyle: {
                            color: '#333'
                        },
                        formatter: function (a, b, c) {
                            return (c.name) ? '-' : c + '%';
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
                        color: '#1AB394',
                        lineStyle: {
                            type: 'solid'
                        }
                    }
                },
                data: []
            }
        }]
    }

    // 本部与下属部门流失率对比grid的option
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
                width: 160,
                sortable: false
            },
            {
                name: 'beginSum',
                index: 'beginSum',
                width: 100,
                sortable: false,
                align: 'right'
            },
            {
                name: 'endSum',
                index: 'endSum',
                width: 90,
                sortable: false,
                align: 'right'
            },
            {
                name: 'runOffCount',
                index: 'runOffCount',
                width: 70,
                sortable: false,
                align: 'right'
            },

            {
                name: 'dismissRate',
                index: '',
                width: 80,
                fixed: true,
                sortable: false,
                align: 'right',
                formatter: function (cellvalue,
                                     options, rowObject) {
                    return (cellvalue == null) ? '-' : cellvalue + '%';
                }
            }
        ],
        scroll: true
    };

    var dismissContrastObj = {
        chartId: 'contrastChart',
        chartObj: null,
        chartOption: _.clone(contrastBarOption),
        gridId: '#contrastGrid',
        gridObj: null,
        init: function (organId) {
            var self = this;
            self.chartObj = initEChart(self.chartId);
            self.initLineLengend();
            self.initData(organId);
            self.gridObj = jQuery(self.gridId).jqGrid(contrastGridOption);
        },
        //因为图表中的横线没有图例，故用zrender画一个
        initLineLengend: function () {
            var _ZR = this.chartObj.getZrender();
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 65,
                    y: _ZR.getHeight() - 10,
                    color: '#1AB394',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑',
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 40,
                    y: _ZR.getHeight() - 10,
                    color: '#666',
                    text: '当前季度主动流失率',
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
                data.curRate = dismissCursorObj.cursorOptions.value * 100;
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
            $.each(subData, function (i, item) {
                category.push(item.organizationName);
                barDataArr.push({name: item.organizationName, value: item.dismissRate});
            });
            self.chartOption.xAxis[0].data = category;
            self.chartOption.series[0].data = barDataArr;
            self.chartOption.series[0].markLine.data = self.packMarkLineData(barDataArr.length, curRate);
            self.chartObj.setOption(self.chartOption, true);
        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}]];
        },
        generateGrid: function (data) {
            var self = this;
            jQuery(self.gridId).clearGridData().setGridParam({
                data: data
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            jQuery(this.gridId).setGridWidth($('#contrastGridArea').width() * 0.95);
        },
        resizeChart: function () {
            this.chartObj.resize();
        }
    }

    dismissContrastObj.init(reqOrgId);

    var tableOption = {
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
//		altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: ['绩效', '流失人数', '流失占比', '流失率'],
        colModel: [
            {name: 'typeName', index: 'name', width: 120, sortable: false},
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
        colors: [['#1AB394', '#23C6C8', '#79D2C0', '#BABABA', '#D3D3D3'], ['#5B9BD5', '#1AB394', '#79D2C0', '#BABABA', '#D3D3D3'], ['#1AB394', '#23C6C8', '#79D2C0', '#BABABA', '#D3D3D3']],
        colNames: ['层级', '司龄'],
        init: function () {
            var self = this;
            var height = 320;
            $('#' + self.perfChartId).height(height);
            $('#' + self.abilityChartId).height(height);
            $('#' + self.ageChartId).height(height);

            self.perfPieObj = echarts.init(document.getElementById(self.perfChartId));
            self.abilityPieObj = echarts.init(document.getElementById(self.abilityChartId));
            self.agePieObj = echarts.init(document.getElementById(self.ageChartId));

            var perfTableOption = _.clone(tableOption);
            $(self.perfGridId).jqGrid(perfTableOption);

            var abilityTableOption = _.clone(tableOption);
            abilityTableOption.colNames[0] = self.colNames[0];
            $(self.abilityGridId).jqGrid(abilityTableOption);

            var ageTableOption = _.clone(tableOption);
            ageTableOption.colNames[0] = self.colNames[1];
            $(self.ageGridId).jqGrid(ageTableOption);
        },
        //因为图表中没有图例，故用zrender画一个
        initLengend: function (_chartObj, data, colors) {
            var self = this;
            var _ZR = _chartObj.getZrender();

            var len = data.length;
            $.each(data, function (i, obj) {
                var num = i - 2;
                var ids = i == 0 ? 0 : i - 1;
                var nameLen = self.getLength(data[ids].typeName);
                var nameWidth = nameLen * (nameLen <= 4 ? 12 : 9);
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + (num * nameWidth - 20),
                        y: _ZR.getHeight() - 12,
                        color: colors[i],
                        text: '▅',
                        textAlign: 'left',
                        textFont: 'bolder 10px 微软雅黑'
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + (num * nameWidth - 10),
                        y: _ZR.getHeight() - 10,
                        color: '#666',
                        text: obj.typeName,
                        textAlign: 'left',
                        textFont: 'normal 12px 微软雅黑'
                    },
                    hoverable: false
                }));
            });
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
                self.initPie(self.perfPieObj, _.initial(perfData), 0, self.colors[0]);
                self.initGrid(self.perfGridId, self.perfData);
            }
            var abilityData = self.abilityData;
            if (!$.isEmptyObject(abilityData)) {
                self.initPie(self.abilityPieObj, _.initial(abilityData), 1, self.colors[1]);
                self.initGrid(self.abilityGridId, abilityData);
            }
            var companyAgeData = self.companyAgeData;
            if (!$.isEmptyObject(companyAgeData)) {
                self.initPie(self.agePieObj, _.initial(companyAgeData), 0, self.colors[2]);
                self.initGrid(self.ageGridId, companyAgeData);
            }
        },
        extendsData: function (organId) {
            var self = this;
            $.post(urls.getQuarterDismissUrl, {'organId': organId, 'prevQuarter': quarterLastDay}, function (rs) {
                var quarterCount = dismissCursorObj.quarterCount;
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
            $(self.perfGridId).setGridWidth($('#perfGridArea').width() * 0.98);
            $(self.abilityGridId).setGridWidth($('#abilityGridArea').width() * 0.98);
            $(self.ageGridId).setGridWidth($('#ageGridArea').width() * 0.98);
        },
        initPie: function (pieObj, resultData, type, colors) {
            pieObj.clear();
            var self = this;
            var newPieOption = _.clone(pieOption);
            newPieOption.color = colors;
            var seriesData = [];
            var total = 0;
            $.each(resultData, function (i, obj) {
                total += obj.count == '-' ? 0 : obj.count;
            });
            $.each(resultData, function (i, obj) {
                seriesData.push(formatPieData(obj.typeName, obj.count, total, type));
            });
            newPieOption.series[0].data = seriesData;
            self.initLengend(pieObj, resultData, colors);
            pieObj.setOption(newPieOption, true);
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
        }
    };
    accordDismissPieObj.init();


    //横向柱状图的option
    var abnormalBarOption = {
        calculable: false,
        grid: {
            borderWidth: 0,
            x: 140
//            y: 10,
//            y2: 35
        },
        xAxis: [{
            type: 'value',
            axisLabel: {show: false},
            splitLine: false,
            axisLine: {show: false},
            axisTick: {show: false}
        }],
        yAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {show: false},
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#666'
                }
            },
            data: []
        }],
        series: [{
            name: '',
            barMaxWidth: 40,
            type: 'bar',
            clickable: false,
            itemStyle: {
                normal: {
//                    color: barColor[0],
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
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
        1: {name: '主动流失', color: '#1AB394'},
        2: {name: '被动流失', color: '#79D2C0'},
        3: {name: '其他', color: '#BABABA'}
    };
    var reHiredEnum = {
        1: {name: '建议重新录用', color: '#1AB394'},
        2: {name: '不建议重新录用', color: '#79D2C0'},
        3: {name: '其他', color: '#BABABA'}
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
                showEmptyTip($('#dismissCauseTab .widget-body'));
                return;
            }
            removeEmptyTip($('#dismissCauseTab .widget-body'));
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
                if (changeColor) {
                    newItem.itemStyle = self.getItemColorStyle(item.type);
                }
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
            $.each(data, function (i, item) {
                var pieDataObj = formatPieData(nameEnum[i].name, item.value, totalEmp);
                pieDataObj.itemStyle = self.getItemColorStyle(i);
                pieDataArr.push(pieDataObj);
            })
            chartOption.series[0].data = pieDataArr;
            chartObj.setOption(chartOption, true);
        },

        getItemColorStyle: function (type) {
            return {
                normal: {color: dismissTypeEnum[type].color}
            };
        },
        resizeChart: function () {
            $.each(this.chartObjs, function (i, item) {
                item.resize();
            });
        }
    }


    function showEmptyTip($targetDom) {
        //如果是显示状态
        if ($targetDom.find('.empty-tip').length != 0) {
            return;
        }
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
        if (!value) {
            return {};
        }
        //页面展示为 ：name，value，(换行)percent%
        return {
            value: value,
            name: (name + '，' + Tc.formatNumber(value) + '，\n' + ((value / total) * 100).toFixed(0) + '%')
        };
    }

    dismissCauseObj.init();

    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }


    /**
     * 人员流失明细表格参数
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
//        onClick: function (id, name, type) {
//        	runOffDetailObj.initGrid(reqOrgId);
//        },
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
                if ($.trim(searchTxt) != "") {
                    var orgId = $("#recordSelectTreeOrgId").val();
                    $(runOffDetailObj.gridId).clearGridData().setGridParam({
                        postData: {organizationId: orgId, keyName: searchTxt, queryType: 1}
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

    //为表格设置一个高度
    var tableHeight = 321;
    var dismissGridOption = {
        url: urls.getRunOffDetailUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,//268
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
            {name: 'abilityName', index: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
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
            {name: 'whereAbouts', index: 'whereAbouts', width: 120, fixed: true, sortable: false, align: 'center'},
        ],
        rownumbers: true,
        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#runOffDetailSel",
        loadComplete: function (xhr) {
            var table1 = this;
//            var limit=xhr.limit;
//            //XXX 目前没有好办法解决自动heigth   加2是为了防止滚动条
//            var tbBodyHeigth=limit*32+1; 
//            if(tbBodyHeigth<tableHeight){
//            	tbBodyHeigth = tableHeight;
//            }
//            $('#runOffDetailGrid').jqGrid('setGridHeight',tbBodyHeigth);
            setTimeout(function () {
                updatePagerIcons();
            }, 0);

            $('.talent_col').unbind().bind('click', function () {
                var _this = $(this);
                var empId = _this.attr('data');
                var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random();
                window.open(herf);
            });
            $("#runOffDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
            //约束iframe高度
            $("#dismissDetailTab .transparent").height(580);
        }
    };

    /**
     * 人员流失明细
     */
    var runOffDetailObj = {
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
            $(self.gridId).setGridWidth($('#runOffDeailTable').width() * 0.98);
        }
    };
    runOffDetailObj.init(reqOrgId);

    function formatPieData(name, value, total, type) {
        if (!value) {
            return {};
        }
        //页面展示  1 : name，value，(换行)percent% ; 0 :name，value，percent%
        var newLine = (type) ? '\n' : '';
        return {
            value: value,
            name: (name + '，' + Tc.formatNumber(value) + '，' + newLine + ((value / total) * 100).toFixed(0) + '%')
        };
    }

    function formatPercentage(value) {
        if (!value) {
            return '-';
        }
        return value == 0 ? '-' : Math.round(value * 10000) / 100 + '%';
    }

    $(window).resize(function () {
        dismissCursorObj.resize();
        dismissCauseObj.resizeChart();
    });

    //点击tab页
    $('#dimissTypeTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var targetAreaId = $(e.target).attr('aria-controls');
        changeData(targetAreaId);
    });


    /**
     * 返回当前焦点tab页显示的区域id
     */
    function getActiveTabId(targetDom) {
        return $(targetDom).find('li.active').find('a').attr('aria-controls');
    }

    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData(targetAreaId) {
        var selOrganId = reqOrgId;
        if (targetAreaId == 'dismissDetailTab') {
            //流失人员明细
            runOffDetailObj.initData(selOrganId);
        } else if (targetAreaId == 'dismissCauseTab') {
            //流失原因及去向
            dismissCauseObj.initData(selOrganId);
        } else {
            //主动流失率
            initFirstTabData(selOrganId);
        }
    }

    /**
     * 加载顶级的第一个tab页的数据（即“主动流失率”tab页）
     */
    function initFirstTabData(organId) {
        dismissCursorObj.initData(organId);
        timeLineObj.init(organId);
        dismissContrastObj.initData(organId);
        trendChartObj.initData(organId);
        keyPerfDismissObj.initData(organId);
    }

    initFirstTabData(reqOrgId);

    //点击“本部与下属部门流失率对比”tab页
    $('#contrastTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var targetAreaId = $(e.target).attr('aria-controls');
        //切换标签页时，要调用echarts的resize()方法对图表进行重绘（当tab页的内容处于隐藏状态时，改变窗口大小后重新显示时，图表可能会显示不正确，故需重绘）
        if (targetAreaId == 'contrastChartArea') {
            dismissContrastObj.resizeChart();
        } else {
            dismissContrastObj.resizeGrid();
        }
    });

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

    $.zrw_resizeFrameSize();
});