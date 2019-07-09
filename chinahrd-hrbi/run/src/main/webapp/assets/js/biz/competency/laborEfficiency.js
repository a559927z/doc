require(['jquery', 'jquery-ui', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'timeLine2', 'organTreeSelector', 'tooltipZrw', 'selection',
    'form', 'select2'], function ($, jqueryui, echarts) {
    var ecConfig = require('echarts/config');
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getLaborEfficiencyRatioUrl: webRoot + '/laborEfficiency/getLaborEfficiencyRatio.do',           //劳动力效能对比
        findLaborEfficiencyUrl: webRoot + '/laborEfficiency/findLaborEfficiencyUrl.do',           //劳动力效能明细
        getLaborEfficiencyTrendUrl: webRoot + '/laborEfficiency/getLaborEfficiencyTrend.do',           //劳动力效能趋势
        getLaborEfficiencyValueUrl: webRoot + '/laborEfficiency/getLaborEfficiencyValue.do',           //劳动力效能
        queryOvertimeHoursUrl: webRoot + '/laborEfficiency/queryOvertimeHours.do',           //加班时长-本月/本年
        queryOvertimeWarningCountUrl: webRoot + '/laborEfficiency/queryOvertimeWarningCount.do',           //加班预警统计
        queryOvertimeWarningDetailUrl: webRoot + '/laborEfficiency/queryOvertimeWarningDetail.do',           //加班预警明细
        queryOvertimeWarningPersonHoursUrl: webRoot + '/laborEfficiency/queryOvertimeWarningPersonHours.do',           //加班预警明细-人员加班线图
        queryOvertimeAvgHoursUrl: webRoot + '/laborEfficiency/queryOvertimeAvgHours.do',           //加班时长趋势-人均时长
        queryOvertimeTotalHoursUrl: webRoot + '/laborEfficiency/queryOvertimeTotalHours.do',           //加班时长趋势-总时长
        queryOvertimeConditionUrl: webRoot + '/laborEfficiency/queryOvertimeCondition.do',           //加班情况
        queryOvertimeConditionPersonHoursUrl: webRoot + '/laborEfficiency/queryOvertimeConditionPersonHours.do',   //加班情况-人员加班线图
        getConditionValueUrl: webRoot + '/laborEfficiency/getConditionValue.do',           //获取机构和时间
        queryCheckWorkTypeForDateUrl: webRoot + '/laborEfficiency/queryCheckWorkTypeForDate.do',           //考勤类型分布-时间
        queryCheckWorkTypeLayoutUrl: webRoot + '/laborEfficiency/queryCheckWorkTypeLayout.do',           //考勤类型分布
        queryOvertimeByOrganUrl: webRoot + '/laborEfficiency/queryOvertimeByOrgan.do',           //组织机构加班时长
        queryAttendanceDetailUrl: webRoot + '/laborEfficiency/queryAttendanceDetail.do',           // 出勤明细
        queryCheckWorkTypeUrl: webRoot + '/laborEfficiency/queryCheckWorkType.do',           // 出勤明细-考勤类型
        queryOnePersonDetailCheckWorkTypeUrl: webRoot + '/laborEfficiency/queryOnePersonDetailCheckWorkType.do',           // 个人出勤明细-考勤类型
        queryOnePersonDetailUrl: webRoot + '/laborEfficiency/queryOnePersonDetail.do',           // 个人出勤明细
        getParentIdUrl: webRoot + '/laborEfficiency/getParentId.do',           //获取组织机构
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',    //跳转到员工详情
        toAuditingHistoryUrl: webRoot + '/laborEfficiency/toAuditingHistoryView.do',    //跳转到历史审核页面
        toAuditingViewUrl: webRoot + '/laborEfficiency/toAuditingView.do',    //跳转到审核页面
        downloadTempletExcelUrl: webRoot + '/laborEfficiency/downloadTempletExcel.do',    //下载《员工考勤数据》导入模板
        importLaborEfficiencyDataUrl: webRoot + '/laborEfficiency/importLaborEfficiencyData.do',    //导入《员工考勤数据》
        queryAuditingInfoUrl: webRoot + '/laborEfficiency/queryAuditingInfo.do',    //待审核数据
        checkEmpInfoUrl: webRoot + "/laborEfficiency/checkEmpInfo.do",  //查询员工信息
        queryOrganUrl: webRoot + "/laborEfficiency/queryOrgan.do",  //查询机构
        attendanceMonthlyUrl: webRoot + "/laborEfficiency/attendanceMonthly.do",//导出月度考勤
    };

    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var reqOrgTxt = win.currOrganTxt;
    var pageType = $('#pageType').val();

    var dataZoom = {
            show: true,
            realtime: true,
            height: 20,
            start: 100,
            end: 60,
            showDetail: false,
            y: 310
        };
    var TextShape = require('zrender/shape/Text');
    var pieDefaultColor = ['#5cb7f1', '#01d286', '#e5689b', '#b285c3', '#4682bc', '#8f684b', '#f28e7f', '#fbc370'];
    var defaultDatas = {
        defaultNum: 0,
        fullMonth: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        partMonth: [],
        fullDate: [],
        page: 1,
        count: 0,
        rows: 24,
        next: false,
        overtimePage: 1,
        overtimeCount: 0,
        overtimeRows: 24,
        overtimeNext: false
    }
    var dateYear = $('#yearHiddenId').val();
    var dateMonth = Number($('#monthHiddenId').val());
    var dateYearList = $('#yearListHiddenId').val().split(',');
    var selectMonth = null;





    //提示
    $(".icon-question-sign").tooltip({
    	trigger: 'click',
        html: true,
        placement: 'bottom'
    });



    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        reqOrgTxt = organTxt;
        var curTopTabId = getActiveTabId(".leftListDiv");
        changeData(curTopTabId, true);
        defaultDatas.page = 1;
        defaultDatas.overtimePage = 1;
        overtimeWarningDetailObj.setModalHideFun();
        overtimeConditionObj.setModalHideFun();
    };

    var minMaxTime = $('#minMaxTime').val();
    var minMaxTimes = minMaxTime.split(',');
    var timeObj = {
        selectedYearMonth: '',//选中项
        defaultDate: '',//选中项
        selectedFullYearMonth: '',//选中项
        dateBegin: '',
        dateEnd: '',
        init: function () {
            var self = this;
            self.getDateSelected();
            self.getDefaultDateSelected();
            self.getFullDateSelected();
            self.getDateBegin();
            self.getDateEnd();
        },
        getDateSelected: function () {
            var self = this;
            if (minMaxTimes.length > 1) {
                var time1 = minMaxTimes[2];
                var time2 = minMaxTimes[1];
                var year1 = time1.substr(0, 4);
                var month1 = Number(time1.substr(4, 2));
                var year2 = time2.substr(0, 4);
                var month2 = Number(time2.substr(4, 2));
                self.selectedYearMonth = [year1, month1, year2, month2];
            }
        },
        getDefaultDateSelected: function () {
            var self = this;
            var time2 = minMaxTimes[1];
            var year2 = time2.substr(0, 4);
            var month2 = Number(time2.substr(4, 2));
            self.defaultDate = [year2, month2, year2, month2];
        },
        getFullDateSelected: function () {
            var self = this;
            if (minMaxTimes.length > 1) {
                var time1 = minMaxTimes[2];
                var time2 = minMaxTimes[1];
                var year1 = time1.substr(0, 4);
                var month1 = Number(time1.substr(4, 2));
                var year2 = time2.substr(0, 4);
                var month2 = Number(time2.substr(4, 2));
                self.selectedFullYearMonth = [year1, month1, year2, month2];
            }
        },
        getDateBegin: function () {
            var self = this;
            if (minMaxTimes.length > 1) {
                var time = minMaxTimes[0];
                self.dateBegin = time.substr(0, 4) + '-' + time.substr(4, 2);
            }
        },
        getDateEnd: function () {
            var self = this;
            if (minMaxTimes.length > 1) {
                var time = minMaxTimes[1];
                self.dateEnd = time.substr(0, 4) + '-' + time.substr(4, 2);
            }
        }
    }
    timeObj.init();

    /**
     * 劳动力效能
     * */
    var laborEfficiencyCountObj = {
        id: 'countNum',
        organId: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.setDefaultValFun();
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            var param = {
                organId: self.organId
            };
            $.ajax({
                url: urls.getLaborEfficiencyValueUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.loadDataFun(data);
                },
                error: function () {
                }
            });
        },
        loadDataFun: function (data) {
            var self = this;
            if (data && data.num != undefined) {
                $('#' + self.id).html(data.num);
            }
        },
        setDefaultValFun: function () {
            var self = this;
            $('#' + self.id).html(defaultDatas.defaultNum);
        }
    }

    /**
     * 加班时长
     * */
    var overtimeHoursCountObj = {
        toolbarId: 'overtimeHoursToolbar',
        conId: 'overtimeHoursNum',
        comId: 'compareHourNum',
        organId: null,
        conNum: 0,
        conDiffer: 0,
        avgNum: 0,
        avgDiffer: 0,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.setDefaultValFun();
            self.getRequestDataFun();
            self.spanClickFun();
        },
        getRequestDataFun: function () {
            var self = this;
            var param = {
                organId: self.organId
            };
            $.ajax({
                url: urls.queryOvertimeHoursUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.loadDataFun(data);
                },
                error: function () {
                }
            });
        },
        loadDataFun: function (data) {
            var self = this;
            if (data) {
                self.conNum = data.conNum;
                self.conDiffer = data.conDiffer;
                self.avgNum = data.avgNum;
                self.avgDiffer = data.avgDiffer;
                var name = $('#' + self.toolbarId).find('.select').text();
                self.setTextWithSelectFun(name);
            }
        },
        spanClickFun: function () {
            var self = this;
            $('#' + self.toolbarId + ' span').unbind('click').bind('click', function () {
                var par = $(this).parent();
                var _t = this;
                $.each(par.children(), function () {
                    if (this == _t) {
                        $(this).addClass("select");
                        self.setTextWithSelectFun($(this).text());
                    } else {
                        $(this).removeClass("select");
                    }
                });
            });
        },
        setTextWithSelectFun: function (name) {
            var self = this;
            if (name == '总数') {
                $('#' + self.conId).html(Tc.formatFloat(self.conNum));
                $('#' + self.comId).html(Tc.formatFloat(self.conDiffer));
            } else {
                $('#' + self.conId).html(Tc.formatFloat(self.avgNum));
                $('#' + self.comId).html(Tc.formatFloat(self.avgDiffer));
            }
        },
        setDefaultValFun: function () {
            var self = this;
            $('#' + self.conId).html(defaultDatas.defaultNum);
            $('#' + self.comId).html(defaultDatas.defaultNum);
        }
    }

    /**
     * 加班预警
     * */
    var overtimeWarningCountObj = {
        divId: 'overtimeWarningDiv',
        conId: 'overtimeWarningNum',
        personId: 'overtimeWarningPerson',
        organId: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.setDefaultValFun();
            self.getRequestDataFun();
            self.openDetailDialogFun();
        },
        getRequestDataFun: function () {
            var self = this;
            var param = {
                organId: self.organId
            };
            $.ajax({
                url: urls.queryOvertimeWarningCountUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.loadDataFun(data);
                },
                error: function () {
                }
            });
        },
        loadDataFun: function (data) {
            var self = this;
            if (data && data.conNum != undefined) {
                $('#' + self.conId).html(data.conNum);
                $('#' + self.personId).html(data.userName);
            }
        },
        openDetailDialogFun: function () {
            var self = this;
            $('#' + self.divId).unbind('click').bind('click', function () {
                overtimeWarningDetailObj.init(self.organId);
            });
        },
        setDefaultValFun: function () {
            var self = this;
            $('#' + self.conId).html(defaultDatas.defaultNum);
            $('#' + self.personId).html('');
        }
    }
    /**
     * 加班预警明细
     * */
    var overtimeWarningDetailObj = {
        modalId: 'overtimeWarningDetailModal',
        scrollId: 'overtimeWarningScroll',
        tabId: 'overtimeWarningTable',
        treeId: 'overtimeOrganTree',
        personModalId: 'overtimeWarningPersonInfo',
        organId: null,
        f: true,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.setModalShowFun();
        },
        setModalShowFun: function () {
            var self = this;
            self.loadTreeFun();
            $('#' + self.modalId).modal('show');
            $('#' + self.modalId).on('shown.bs.modal', function () {
                defaultDatas.page = 1;
                defaultDatas.count = 0;
                defaultDatas.rows = 24;
                defaultDatas.next = false;
                $('#' + self.tabId).empty();
                self.getRequestDataFun();
            });
            $('#' + self.modalId).on('hidden.bs.modal', function () {
                $('#' + self.tabId).empty();
                $('#' + self.modalId).off();
            });
        },
        setModalHideFun: function () {
            var self = this;
            $('#' + self.modalId).modal('hide');
            $('#' + self.modalId).on('hidden.bs.modal', function () {
                $('#' + self.tabId).empty();
            });
        },
        loadTreeFun: function () {
            var self = this;
            var deptInfo = {
                id: reqOrgId,
                text: reqOrgTxt
            }
            $('#' + self.treeId).parent().find('.dropDownValue').text(reqOrgTxt);
            $('#' + self.treeId).organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                value: deptInfo,
                onSelect: function (ids, texts) {
                    if (defaultDatas.next) {
                        self.organId = ids;
                        defaultDatas.page = 1;
                        defaultDatas.count = 0;
                        defaultDatas.rows = 24;
                        defaultDatas.next = false;
                        self.getRequestDataFun();
                    }
                }
            });
        },
        scrollFun: function () {
            var self = this;
            $('#' + self.scrollId).unbind('scroll').bind('scroll', function () {
                if (self.f) {
                    setTimeout(self.scrollOneFun, 1000);
                    self.f = false;
                }
            });
        },
        scrollOneFun: function () {
            var self = this;
            if (defaultDatas.page < defaultDatas.count) {
                if (defaultDatas.next) {
                    defaultDatas.page++;
                    overtimeWarningDetailObj.getRequestDataFun(true);
                }
            }
            self.f = true;
        },
        getRequestDataFun: function (flag) {
            var self = this;
            var param = {
                organId: self.organId,
                page: defaultDatas.page,
                rows: defaultDatas.rows
            };
            $.ajax({
                url: urls.queryOvertimeWarningDetailUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    if (data && data.count > 0) {
                        defaultDatas.count = data.count;
                    }
                    defaultDatas.next = true;
                    if (!flag) {
                        $('#' + self.tabId).empty();
                    }
                    self.loadDataFun(data);
                    self.f = true;
//    				var scrollHeight = $('#' + overtimeWarningDetailObj.scrollId).height();
//    				var tabHeight = $('#' + overtimeWarningDetailObj.tabId).height();
//    				$('#' + overtimeWarningDetailObj.scrollId).scrollTop(scrollHeight + tabHeight);
                    self.scrollFun();
                },
                error: function () {
                }
            });
        },
        loadDataFun: function (data) {
            var self = this;
//    		$('#' + self.tabId).empty();
            $.each(data.list, function (index, object) {
                var div = $(getEmpDetailTpl(object));
                $('#' + self.tabId).append(div);
                $(div).find("img").unbind('click').bind('click', function() {
                	$('#overtimeWarning_body').hide();
                	$('#overtimeWarningPersonInfo_body').show();
                	
                	$('#overtimeCountHours').html(defaultDatas.defaultNum);
                	$('#empName').html(object.userName);
                    var param = {
                        empId: object.empId
                    }
                    $.ajax({
                        url: urls.queryOvertimeWarningPersonHoursUrl,
                        data: param,
                        type: 'post',
                        success: function (data) {
                            if (data && data.date != undefined && data.date.length > 0) {
                                var overtimeChart = initEChart("overtimeChart");
                                clearEChart(overtimeChart);
                                $('#overtimeCountHours').html(data.conNum);
                                var date = [];
                                $.each(data.date, function(i, o) {
                                	date.push(o.substr(5, 2) + '月' + o.substr(8, 2) + '日');
                                });
                                overtimeHoursOption.xAxis[0].data = date;
                                overtimeHoursOption.series[0].data = data.series;
                                changeXAxisLabelRotate(overtimeHoursOption, data.date);
                                overtimeChart.setOption(overtimeHoursOption);
                            } else {
                                showNoDataEcharts(overtimeChart);
                            }
                        },
                        error: function () {
                        }
                    });
                    
                    $('#return_warn').unbind('click').bind('click', function() {
                    	$('#overtimeWarning_body').show();
                    	$('#overtimeWarningPersonInfo_body').hide();
                    });
                })
                
                /*tooltipZrw({
                    modal: self.personModalId,
                    data: object,
                    event: "click|mousemove",
                    //	style:"top",
                    callback: function (obj, rsdata) {
                        $(obj).find('#overtimeCountHours').html(defaultDatas.defaultNum);
                        var param = {
                            empId: rsdata.empId
                        }
                        $.ajax({
                            url: urls.queryOvertimeWarningPersonHoursUrl,
                            data: param,
                            type: 'post',
                            success: function (data) {
                                if (data && data.date != undefined && data.date.length > 0) {
                                    var overtimeChart = echarts.init($(obj).find("#overtimeChart")[0]);
                                    clearEChart(overtimeChart);
                                    $(obj).find('#overtimeCountHours').html(data.conNum);
                                    $(obj).find('#empName').html(rsdata.userName);
                                    
                                    overtimeHoursOption.xAxis[0].data = data.date;
                                    overtimeHoursOption.series[0].data = data.series;
                                    changeXAxisLabelRotate(overtimeHoursOption, data.date);
                                    overtimeChart.setOption(overtimeHoursOption);
                                } else {
                                    showNoDataEcharts(overtimeChart);
                                }
                            },
                            error: function () {
                            }
                        });
                    }
                });*/
            });
        }
    }
    /**
     * 加班预警-人员线图
     * */
    var overtimeHoursOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
            axisPointer: {
                type: 'none'
            },
            formatter: function(obj) {
            	var arr = [];
            	arr.push('<div>'+obj[0].name+'</div>');
            	arr.push('<div>加班：'+obj[0].value+'小时</div>');
            	return arr.join('');
            }
        },
        xAxis: [
            {
                type: 'category',
                //boundaryGap : false,
                splitLine: false,
                axisLine: {
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                },
                axisLabel: {
                    interval: 0
                },
                data: []
            }
        ],
        yAxis: [
            {
            	name: '(小时)',
                type: 'value',
                splitNumber: 5,
                axisLabel: {
                    formatter: '{value}'
                },
                splitLine: false,
                axisLine: {
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                }
            }
        ],
        series: [
            {
                type: 'line',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: '#f5b147',
                        label: {
                            show: true
                        }
                    }
                },
                data: []
            }
        ]
    }

    /**
     * 加班时长趋势-平均时长图
     * */
    var overtimeAvgHoursOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 55,
            y2: 80,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (o) {
                var html = '';
                var name = '';
                $.each(o, function (i, d) {
                    if (i == 0) {
                        html += '<div>' + d.seriesName + ' : ' + d.value + '小时</div>';
                        name = '<div>' + d.name + '</div>';
                    } else {
                        if (d.value == '-') {
                            html += '<div>' + d.seriesName + ' : ' + d.value + '小时</div>';
                        } else {
                            html += '<div>' + d.seriesName + ' : ' + d.value + '%' + '</div>';
                        }
                    }
                })
                return name + html;
            }
        },
        legend: {
            x: 'center',
            y: 'bottom',
            data: ['人均加班时长', '人均加班趋势'],
            padding: 15
        },
        calculable: false,

        xAxis: [
            {
                type: 'category',
                splitLine: false,
                axisLine: {
                	show: true,
                	onZero: false,
                    lineStyle: {
                    	width: 1,
                        color: '#666666'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                    	color: '#666666'
                    }
                },
                axisLabel: {
                    interval: 0,
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
            }
        ],
        yAxis: [
            {
            	name: '(小时)',
                nameTextStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"
                },
                type: 'value',
                splitNumber: 5,
                axisLine: {
                    lineStyle: {
                    	width: 1,
                        color: '#666666'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"
                    },
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                splitNumber: 5,
                axisLine: {
                    show: true,
                    lineStyle: {
                    	width: 1,
                    	color: '#666666'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"
                    },
                    formatter: '{value} %'
                }
            }
        ],
        series: [
            {
                type: 'bar',
                name: '人均加班时长',
                barCategoryGap: '45%',
                barWidth: 30,//barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        color: '#5cb7f1',
                        label: {
                            show: true
                        }
                    }
                },
                data: []
            },
            {
                type: 'line',
                name: '人均加班趋势',
                yAxisIndex: 1,
                itemStyle: {
                    normal: {
                        color: '#f5b147',
                        label: {
                            show: false,
//                            formatter: function (params, ticket, callback) {
//                                return params.value + ' %';
//                            }
                        }
                    }
                },
                clickable: false,
                data: []
            }
        ]
    }


    /**
     * 时间人群切片
     * */
    var timecrowdObj = {
        overtimeHoursSelectId: 'overtimeHoursSelect',
        organId: null,
        date: null,
        crowds: null,
        hasInit: false,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            if (self.hasInit) {
                self.initData(organId)
            } else {
                self.overtimeHoursSelectFun();
            }
            self.hasInit = true;
        },
        initData: function (organId) {
            var self = this;
            if (self.hasInit) {
                overtimeAvgHoursObj.times = self.date;
                overtimeTotalHoursObj.times = self.date;
                if (self.crowd != undefined) {
                    overtimeAvgHoursObj.crowds = self.crowds;
                    overtimeTotalHoursObj.crowds = self.crowds;
                }
                overtimeAvgHoursObj.init(self.organId);
                overtimeTotalHoursObj.init(self.organId);
            }
        },
        overtimeHoursSelectFun: function () {
            var self = this;
            $('#' + self.overtimeHoursSelectId).selection({
                dateType: 2,
                dateRange: {
                    min: timeObj.dateBegin,
                    max: timeObj.dateEnd
                },
                dateSelected: timeObj.selectedFullYearMonth,
                crowdSelected: [0],
                dateSelectedLength: 6,
                ok: function (event, data) {
//                	if ($('#' + self.overtimeHoursSelectId).parents(".SetUpBody").attr("view") == "chart") {
                    self.date = data.date.join('@');
                    overtimeAvgHoursObj.times = self.date;
                    if (data.crowd != undefined) {
                        self.crowds = data.crowd.join('@');
                        overtimeAvgHoursObj.crowds = self.crowds;
                    }
                    overtimeAvgHoursObj.init(self.organId);
//                	} else {
                    overtimeTotalHoursObj.times = self.date;
                    if (data.crowd != undefined) {
                        overtimeTotalHoursObj.crowds = self.crowds;
                    }
                    overtimeTotalHoursObj.init(self.organId);
//                	}
                }
            });
        }
    }

    /**
     * 加班时长趋势-平均时长
     * */
    var overtimeAvgHoursObj = {
        chart: initEChart('overtimeAvgHoursChart'),
        option: overtimeAvgHoursOption,
        organId: null,
        times: null,
        crowds: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            clearEChart(self.chart);
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            var param = {
                organId: self.organId,
                times: self.times,
                crowds: self.crowds
            };
            $.ajax({
                url: urls.queryOvertimeAvgHoursUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            var self = this;
            self.resizeChart();
            if (data && data.date != undefined && data.date.length > 0) {
                defaultDatas.fullDate = data.fullDate;
                self.option.xAxis[0].data = data.date;
                self.option.series[0].data = data.avgList;
                self.option.series[1].data = data.percentList;
                changeXAxisLabelRotate(self.option, data.date);
                self.setOption();
            } else {
                showNoDataEcharts(self.chart);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.on(ecConfig.EVENT.CLICK, self.eConsole);
        },
        resizeChart: function () {
            this.chart.resize();
        },
        eConsole: function (param) {
            if (typeof param.seriesIndex != 'undefined') {
                if (param.value != 0) {
                    overtimeConditionObj.date = defaultDatas.fullDate[param.dataIndex];
                    overtimeConditionObj.crowds = overtimeAvgHoursObj.crowds;
                    overtimeConditionObj.init(reqOrgId);
                }
            }
        }
    }

    /**
     * 加班情况弹窗
     */
    var overtimeConditionObj = {
        modalId: 'overtimeConditionModal',
        scrollId: 'overtimeConditionScroll',
        tabId: 'overtimeConditionTable',
        treeId: 'overtimeConditionTree',
        personModalId: 'overtimeConditionPersonInfo',
        yearId: 'overtimeConditionYearSelect',
        monthId: 'overtimeConditionMonthSelect',
        okId: 'overtimeConditionOkBtnId',
        organId: null,
        date: null,
        crowds: null,
        year: null,
        month: null,
        maxMonth: null,
        f: true,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.setModalShowFun(organId);
        },
        setModalShowFun: function (organId) {
            var self = this;
            self.loadTreeDataFun(organId);
            self.getDateFun();
            $('#' + self.modalId).modal('show');
            $('#' + self.modalId).on('shown.bs.modal', function () {
                defaultDatas.overtimePage = 1;
                defaultDatas.overtimeCount = 0;
                defaultDatas.overtimeRows = 24;
                defaultDatas.overtimeNext = false;
                self.getRequestDataFun();
            });
            $('#' + self.modalId).on('hidden.bs.modal', function () {
                $('#' + self.tabId).empty();
                $('#' + self.modalId).off();
            });
        },
        setModalHideFun: function () {
            var self = this;
            $('#' + self.modalId).modal('hide');
            $('#' + self.modalId).on('hidden.bs.modal', function () {
                $('#' + self.tabId).empty();
            });
        },
        loadTreeDataFun: function (organId) {
            var self = this;
            var organName;
            $.ajax({
                url: urls.queryOrganUrl,
                type: "post",
                data: {organId: organId},
                success: function (data) {
                    self.loadTreeFun(organId, data.organName);
                },
                error: function () {
                }
            });
        },
        loadTreeFun: function (organId, organName) {
            var self = this;
            var deptInfo = {
                id: organId,
                text: organName
            }
            $('#' + self.treeId).parent().find('.dropDownValue').text(organName);
            $('#' + self.treeId).organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                value: deptInfo,
                onSelect: function (ids, texts) {
                    self.organId = ids;
//    				if(defaultDatas.overtimeNext){
                    self.organId = ids;
                    defaultDatas.overtimePage = 1;
                    defaultDatas.overtimeCount = 0;
                    defaultDatas.overtimeRows = 24;
                    defaultDatas.overtimeNext = false;
//    				}
                }
            });
        },
        getDateFun: function () {
            var self = this;
            self.year = Number(self.date.substr(0, 4));
            self.month = Number(self.date.substr(5, 2));
            self.maxMonth = dateMonth;
            self.yearList = dateYearList;
            self.loadYearFun();
        },
        loadYearFun: function () {
            var self = this;
            $('#' + self.yearId).empty();
            var option = '';
            $.each(self.yearList, function (index, object) {
                option += "<option value='" + object + "'>" + object + "年</option>";
            });
            $('#' + self.yearId).append(option);
            $('#' + self.yearId).val(self.year);
            self.setMonthFun();
        },
        setMonthFun: function () {
            var self = this;
            var array = new Array();
            for (var i = 1; i <= self.maxMonth; i++) {
                array.push(i);
            }
            defaultDatas.partMonth = array;
            if(self.year == dateYear){
            	self.appendMonthFun(defaultDatas.partMonth);
            } else {
            	self.appendMonthFun(defaultDatas.fullMonth);
            }
            $('#' + self.monthId).val(self.month);
            self.selectChangeFun();
        },
        selectChangeFun: function () {
            var self = this;
            $('#' + self.yearId).change(function () {
                var option = $('#' + self.yearId + ' option:selected').attr('value');
                self.month = '1';
                if (option == dateYear) {
                    self.appendMonthFun(defaultDatas.partMonth);
                    self.year = dateYear;
                } else {
                    self.appendMonthFun(defaultDatas.fullMonth);
                    self.year = option;
                }
            });
        },
        appendMonthFun: function (data) {
            var self = this;
            $('#' + self.monthId).empty();
            var option = '';
            $.each(data, function (ind, obj) {
                option += "<option value= " + obj + ">" + obj + "月</option>";
            });
            $('#' + self.monthId).append(option);
            $('#' + self.monthId).change(function () {
                self.month = $('#' + self.monthId + ' option:selected').attr('value');
            });
            self.okBtnClickFun();
        },
        okBtnClickFun: function () {
            var self = this;
            $('#' + self.okId).unbind('click').bind('click', function () {
                $('#' + self.tabId).empty();
                self.date = self.year + '' + self.month;
                self.getRequestDataFun();
            });
        },
        scrollFun: function () {
            var self = this;
            $('#' + self.scrollId).unbind('scroll').bind('scroll', function () {
                if (self.f) {
                    setTimeout(self.scrollOneFun, 1000);
                    self.f = false;
                }
            });
        },
        scrollOneFun: function () {
            var self = this;
            if (defaultDatas.overtimePage < defaultDatas.overtimeCount) {
                if (defaultDatas.overtimeNext) {
                    defaultDatas.overtimePage++;
                    overtimeConditionObj.getRequestDataFun(true);
                }
            }
            self.f = true;
        },
        getRequestDataFun: function (flag) {
            var self = this;
            var param = {
                organId: self.organId,
                date: self.date,
                crowds: self.crowds,
                page: defaultDatas.overtimePage,
                rows: defaultDatas.overtimeRows
            };
            $.ajax({
                url: urls.queryOvertimeConditionUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    if (data && data.count > 0) {
                        defaultDatas.overtimeCount = data.count;
                    }
                    defaultDatas.overtimeNext = true;
                    if (!flag) {
                        $('#' + self.tabId).empty();
                    }
                    self.loadDataFun(data);
                    self.f = true;
                    self.scrollFun();
                },
                error: function () {
                }
            });
        },
        loadDataFun: function (data) {
            var self = this;
//    		$('#' + self.tabId).empty();
            $.each(data.list, function (index, object) {
                var div = $(getEmpDetailTplWithCurMonth(object));
                $('#' + self.tabId).append(div);
                $(div).find("img").unbind('click').bind('click', function() {
                	$('#overtimeConditionTable_body').hide();
                	$('#overtimeConditionPersonInfo').show();
                	$('#empName_con').html(object.userName);
                	
                	$('#overtimeConditionCountHours').html(defaultDatas.defaultNum);
                    var param = {
                        empId: object.empId,
                        date: object.date
                    }
                    $.ajax({
                        url: urls.queryOvertimeConditionPersonHoursUrl,
                        data: param,
                        type: 'post',
                        success: function (data) {
                            if (data && data.date != undefined && data.date.length > 0) {
                                var overtimeChart = initEChart('overtimeConditionChart');
                                clearEChart(overtimeChart);
                                $('#overtimeConditionCountHours').html(Tc.formatFloat(data.conNum));
                                var date = [];
                                $.each(data.date, function(i, o) {
                                	date.push(o.substr(5, 2) + '月' + o.substr(8, 2) + '日');
                                });
                                overtimeHoursOption.xAxis[0].data = date;
                                overtimeHoursOption.series[0].data = data.series;
                                changeXAxisLabelRotate(overtimeHoursOption, data.date);
                                overtimeChart.setOption(overtimeHoursOption);
                            } else {
                                showNoDataEcharts(overtimeChart);
                            }
                        },
                        error: function () {
                        }
                    });
                });
                
                $('#return_cond').unbind('click').bind('click', function() {
                	$('#overtimeConditionTable_body').show();
                	$('#overtimeConditionPersonInfo').hide();
                });
                
                /*tooltipZrw({
                    modal: self.personModalId,
                    data: object,
                    event: "click|mousemove",
                    //	style:"top",
                    callback: function (obj, rsdata) {
                        $(obj).find('#overtimeConditionCountHours').html(defaultDatas.defaultNum);
                        var param = {
                            empId: rsdata.empId,
                            date: rsdata.date
                        }
                        $.ajax({
                            url: urls.queryOvertimeConditionPersonHoursUrl,
                            data: param,
                            type: 'post',
                            success: function (data) {
                                if (data && data.date != undefined && data.date.length > 0) {
                                    var overtimeChart = echarts.init($(obj).find("#overtimeConditionChart")[0]);
                                    clearEChart(overtimeChart);
                                    $(obj).find('#overtimeConditionCountHours').html(data.conNum);
                                    overtimeHoursOption.xAxis[0].data = data.date;
                                    overtimeHoursOption.series[0].data = data.series;
                                    changeXAxisLabelRotate(overtimeHoursOption, data.date);
                                    overtimeChart.setOption(overtimeHoursOption);
                                } else {
                                    showNoDataEcharts(overtimeChart);
                                }
                            },
                            error: function () {
                            }
                        });
                    }
                });*/
            });
        }
    };

    /**
     * 加班时长趋势-总时长图
     * */
    var overtimeTotalHoursOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 55,
            y2: 80,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (o) {
                var html = '';
                var name = '';
                $.each(o, function (i, d) {
                    if (i == 0) {
                        html += '<div>' + d.seriesName + ' : ' + d.value + '小时</div>';
                        name = '<div>' + d.name + '</div>';
                    } else {
                        if (d.value == '-') {
                            html += '<div>' + d.seriesName + ' : ' + d.value + '小时</div>';
                        } else {
                            html += '<div>' + d.seriesName + ' : ' + d.value + '%' + '</div>';
                        }
                    }
                })
                return name + html;
            }
        },
        legend: {
            x: 'center',
            y: 'bottom',
            data: ['总加班时长', '总加班趋势'],
            padding: 15
        },
        calculable: false,
        xAxis: [
            {
                type: 'category',
                splitLine: false,
                axisLine: {
                	onZero: false,
                    lineStyle: {
                    	width: 1,
                        color: '#666666'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#666666'
                    }
                },
                axisLabel: {
                    interval: 0,
                    textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"
                    },
                },
                data: []
            }
        ],
        yAxis: [
            {
            	name: '(小时)',
                nameTextStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"
                },
                type: 'value',
                splitNumber: 5,
                axisLine: {
                    lineStyle: {
                    	width: 1,
                        color: '#666666'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"
                    },
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                splitNumber: 5,
                axisLine: {
                    show: true,
                    lineStyle: {
                    	width: 1,
                    	color: '#666666'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#666666',
                        fontSize: 12,
                        fontFamily: "'微软雅黑', 'verdana'"
                    },
                    formatter: '{value} %'
                }
            }
        ],
        series: [
            {
                type: 'bar',
                name: '总加班时长',
                barCategoryGap: '45%',
                barWidth: 30,
                itemStyle: {
                    normal: {
                        color: '#5cb7f1',
                        label: {
                            show: true
                        }
                    }
                },
                data: []
            },
            {
                type: 'line',
                name: '总加班趋势',
                yAxisIndex: 1,
                itemStyle: {
                    normal: {
                        color: '#f5b147',
                        label: {
                            show: false,
//                            formatter: function (params, ticket, callback) {
//                                return params.value + ' %'
//                            }
                        }
                    }
                },
                data: []
            }
        ]
    }

    /**
     * 加班时长趋势-总时长
     * */
    var overtimeTotalHoursObj = {
        chart: initEChart('overtimeTotalHoursChart'),
        option: overtimeTotalHoursOption,
        organId: null,
        times: null,
        crowds: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            clearEChart(self.chart);
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            var param = {
                organId: self.organId,
                times: self.times,
                crowds: self.crowds
            };
            $.ajax({
                url: urls.queryOvertimeTotalHoursUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            var self = this;
            self.resizeChart();
            if (data && data.date != undefined && data.date.length > 0) {
                defaultDatas.fullDate = data.fullDate;
                self.option.xAxis[0].data = data.date;
                self.option.series[0].data = data.totalList;
                self.option.series[1].data = data.percentList;
                changeXAxisLabelRotate(self.option, data.date);
                self.setOption();
            } else {
                showNoDataEcharts(self.chart);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.on(ecConfig.EVENT.CLICK, self.eConsole);
        },
        resizeChart: function () {
            this.chart.resize();
        },
        eConsole: function (param) {
            if (typeof param.seriesIndex != 'undefined') {
                if (param.value != 0) {
                    overtimeConditionObj.date = defaultDatas.fullDate[param.dataIndex];
                    overtimeConditionObj.crowds = overtimeTotalHoursObj.crowds;
                    overtimeConditionObj.init(reqOrgId);
                }
            }
        }
    }

    /**
     * 考勤类型-环形图
     * */
    var checkWorkTypePieOption = {
        legend: {
        	y: 'bottom',
            data: []
        },
        tooltip: {
        	trigger: 'item',
        	formatter: "{b}: {c} (小时)"
        },
        series: [
            {
                type: 'pie',
                radius: ['50%', '70%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        },
                        labelLine: {
                            show: true
                        }
                    },
                    /*emphasis: {
                        label: {
                            show: true,
                            position: 'center',
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold'
                            }
                        }
                    }*/
                },
                data: []
            }
        ],
        color: pieDefaultColor
    }

    /**
     * 考勤类型-日期
     * */
    var chekWorkTypeDateObj = {
        yearId: 'year-select',
        monthId: 'month-select',
        okId: 'okBtnId',
        organId: null,
        year: null,
        month: null,
        yearList: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            self.year = dateYear;
            self.month = dateMonth;
            self.yearList = dateYearList;
            self.loadDataFun();
        },
        loadDataFun: function () {
            var self = this;
            $('#' + self.yearId).empty();
            var option = '';
            $.each(self.yearList, function (index, object) {
                option += "<option value='" + object + "'>" + object + "年</option>";
            });
            $('#' + self.yearId).append(option);
            $('#' + self.yearId).val(self.year);
            self.setMonthFun(self.month);
        },
        setMonthFun: function (data) {
            var self = this;
            var array = new Array();
            for (var i = 1; i <= data; i++) {
                array.push(i);
            }
            defaultDatas.partMonth = array;
            self.appendMonthFun(defaultDatas.partMonth);
            if (selectMonth != null) {
                $('#' + self.monthId).val(selectMonth);
            } else {
                $('#' + self.monthId).val(self.month);
            }
            self.selectChangeFun();
        },
        selectChangeFun: function () {
            var self = this;
            $('#' + self.yearId).change(function () {
                var option = $('#' + self.yearId + ' option:selected').attr('value');
//                self.year = option;
//                self.month = '1';
                if (option == dateYear) {
                    self.appendMonthFun(defaultDatas.partMonth);
                    self.year = dateYear;
                } else {
                    self.appendMonthFun(defaultDatas.fullMonth);
                    self.year = option;
                }
            });
        },
        appendMonthFun: function (data) {
            var self = this;
            $('#' + self.monthId).empty();
            var option = '';
            $.each(data, function (ind, obj) {
                option += "<option value= " + obj + ">" + obj + "月</option>";
            });
            $('#' + self.monthId).append(option);
            $('#' + self.monthId).change(function () {
                self.month = $('#' + self.monthId + ' option:selected').attr('value');
                selectMonth = self.month;
            });
            self.okBtnClickFun();
        },
        okBtnClickFun: function () {
            var self = this;
            $('#' + self.okId).unbind('click').bind('click', function () {
                checkWorkTypeObj.date = self.year + self.month;
                checkWorkTypeObj.init(self.organId);
            });
        }
    }
    /**
     * 考勤类型
     * */
    var checkWorkTypeObj = {
        chart: initEChart('checkWorkTypeChart'),
        option: checkWorkTypePieOption,
        gridId: 'checkWorkTypeGrid',
        gridObj: null,
        organId: null,
        date: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            clearEChart(self.chart);
            self.clearTrFun();
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            var param = {
                organId: self.organId,
                date: self.date
            };
            $.ajax({
                url: urls.queryCheckWorkTypeLayoutUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                    self.loadGridFun(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.pieList && data.pieList.length > 0) {
                self.option.series[0].data = data.pieList;
                var legendData = [];
                $.each(data.pieList, function(i, o) {
                	legendData.push({name: o.name, icon: 'bar'});
                });
                self.option.legend.data = legendData;
                self.setOption();
            } else {
                showNoDataEcharts(self.chart);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
        },
        loadGridFun: function (data) {
            var self = this;
            var gridOptioin = {
        		data: [],
                datatype: "local",
                height: 'auto',
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['考勤类型', '工时', '占比'],
                colModel: [
                    {name: 'name', index: 'name', width: 178, sortable: false, align: 'center'},
                    {name: 'hours', index: 'hours', width: 120, sortable: false, align: 'center'},
                    {name: 'percent', index: 'percent', width: 120, sortable: false, align: 'center'}
                ],
                scroll: true
            }
            self.gridObj = $('#' + self.gridId).jqGrid(gridOptioin);
            if (data.gridList && data.gridList.length > 0) {
                var tr = "";
                /*$.each(data.gridList, function (ind, obj) {
                    tr += "<tr class='appendTr'><td>" + obj.name + "</td><td>"
                        + obj.hours + "</td><td>"
                        + obj.percent + "</td></tr>";
                });
                $('#' + self.gridId).append(tr);*/
                $('#' + self.gridId).clearGridData().setGridParam({
                    data: data.gridList
                }).trigger("reloadGrid");
                self.resizeGrid();
            }
        },
        resizeGrid: function () {
            $('#' + this.gridId).setGridWidth($('#checkWorkTypeArea').width() * 0.98);
        },
        clearTrFun: function () {
            $('.appendTr').remove();
        }
    }

    /**
     * 追加图片
     * */
    function getEmpDetailTpl(data) {
        var img = data.imgPath != '' && data.imgPath != null ? data.imgPath : webRoot + "/assets/photo.jpg";
        var html = '<div class="pull-left text-left img-div-width"><div class="position-relative img-div-float">';
        html += '<img class="head-pic img-circle" src="' + img + '" data-src="' + img + '" data-id="' + data.empId + '" >';
        html += '</div><div class="emp-name"><span>' + data.userName + '</span><br><span>近两周加班<label class="emp-hours">' + data.conNum + '</label>小时</span></div></div>';
        return html;
    }

    /**
     * 追加图片
     * */
    function getEmpDetailTplWithCurMonth(data) {
        var img = data.imgPath != '' && data.imgPath != null ? data.imgPath : webRoot + "/assets/photo.jpg";
        var html = '<div class="pull-left text-left img-div-width"><div class="position-relative img-div-float">';
        html += '<img class="head-pic img-circle" src="' + img + '" data-src="' + img + '" data-id="' + data.empId + '" >';
        html += '</div><div class="emp-name"><span>' + data.userName + '</span><br><span>本月加班<label class="emp-hours">' + data.conNum + '</label>小时</span></div></div>';
        return html;
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
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
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
     * 重新加载表格
     * @param gridId
     */
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    /**
     * 劳动力效能下级组织对比chart
     */
    var laborRatioChartOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 55,
            y2: 80,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (o) {
                var tip;
                $.each(o, function (i, d) {
                    if (i == 0)
                        tip = d.name + ' : ' + d.value + '%';
                })
                return tip;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                	width: 1,
                	color: '#666666'
                }
            },
            axisLabel: {
                show: true,
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
            axisLine: {
                lineStyle: {
                	width: 1,
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
                },
                formatter: '{value}%'
            }
        }],
        series: [{
            name: '',
            type: 'bar',
            clickable: true,
            barCategoryGap: '45%',
            barWidth: 30,//barMaxWidth: 30,
            itemStyle: {
                normal: {
                    color: '#5cb7f1',
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
                        color: '#00FF00',
                        lineStyle: {
                            type: 'solid'
                        }
                    }
                },
                data: []
            }
        },
        {
            type: 'bar',
            barMaxWidth: 1,
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
        }
        ],
        dataZoom: {}
    };

    /**
     * 劳动力效能下级组织对比grid
     */
    var laborRadioGridOption = {
        data: [],
        datatype: "local",
//        altRows: true,//设置表格行的不同底色
//        autowidth: true,
        height: 272,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['组织', '实出勤', '应出勤', '劳动力效能'],
        colModel: [
            {name: 'organName', index: 'organName', width: 178, sortable: false, align: 'center'},
            {name: 'actualAttendance', index: 'actualAttendance', width: 120, sortable: false, align: 'center'},
            {name: 'beInAttendance', index: 'beInAttendance', width: 120, sortable: false, align: 'center'},
            {
                name: 'attendanceRate', index: 'attendanceRate', width: 129, sortable: false, align: 'center',
                formatter: function (value) {
                    if (value == "") {
                        return 0;
                    }
                    return (parseFloat(value) * 100).toFixed(2) + "%";
                }
            }
        ],
        scroll: true
    };

    /**
     * 劳动力效能下级组织对比Obj
     */
    var laborRatioObj = {
        chartId: 'laborRatioChart',
        chartObj: null,
        chartOption: _.clone(laborRatioChartOption),
        gridId: '#laborRatioGrid',
        gridObj: null,
        organId: null,
        time: null,
        crowds: null,
        init: function () {
            var self = this;
            $('#' + self.chartId).height($('#' + self.chartId).parents('.chart-view').height());
            self.chartObj = initEChart(self.chartId);
            self.gridObj = $(self.gridId).jqGrid(laborRadioGridOption);

            // 添加点击事件
            self.chartObj.on(ecConfig.EVENT.CLICK, self.eConsole);
            $('#returnParentOrgan').click(self.returnBack);
            self.ratioTimecrowdFun();
        },
        ratioTimecrowdFun: function () {
            var self = this;
            $("#ratioSelect").selection({
                dateType: 2,
                dateRange: {
                    min: timeObj.dateBegin,
                    max: timeObj.dateEnd
                },
                dateSelected: timeObj.defaultDate,
                crowdSelected: [0],
                dateSelectedLength: 6,
                ok: function (event, data) {
                    var cd = data.crowd.join(',') == '0' ? '' : data.crowd.join(',');
                    self.initData(self.organId, data.date, cd);
                }
            });
        },
        initData: function (organId, time, crowds) {
            var self = this;
            self.resizeChart();
            self.resizeGrid();
            if (self.topId == undefined) {
                self.topId = organId;
            }
            /*if (self.organId == organId && self.time == time && self.crowds == crowds) {
             return;
             }*/
            if (undefined == organId) organId = self.organId;
            else self.organId = organId;
            if (undefined == time) time = self.time;
            else self.time = time;
            if (undefined == crowds) crowds = self.crowds;
            else self.crowds = crowds;
            clearEChart(self.chartObj);
            self.getRequestData(organId, time, crowds);
        },
        getRequestData: function (organId, time, crowds) {
            var self = this;
            var beginTime;
            var endTime
            if (time != undefined) {
                beginTime = time[0] + (time[1] <= 9 ? '0' + time[1] : time[1]);
                endTime = time[2] + (time[3] <= 9 ? '0' + time[3] : time[3]);
            }
            showLoadingChart(self.chartObj);
            $.get(urls.getLaborEfficiencyRatioUrl, {
                organId: organId, beginTime: beginTime, endTime: endTime,
                populationIds: crowds
            }, function (data) {
                self.generateGrid(data);
                self.generateChart(data);
                if (self.topId == organId || reqOrgId == organId) {
                    $('#returnParentOrgan').hide();
                }
            });
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
            clearEChart(self.chartObj);
            self.resizeChart();
            if (_.isEmpty(data.listBar)) {
                showNoDataEcharts(self.chartObj);
                $('#returnParentOrgan').hide();
            } else {
                $.each(data.listBar, function (i, item) {
                    category.push(item.organName);
                    barDataArr.push({
                        id: item.organId,
                        name: item.organName,
                        value: parseFloat(item.attendanceRate * 100).toFixed(2),
                        flag: item.hasChildren
                    });
                });
                var len = barDataArr.length;
                var num = 4, dataZoom = {};//4个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 15,
                        end: end,
                        showDetail: false,
                        zoomLock: true,
                        y: 310
                    };
                }
                self.chartOption.dataZoom = dataZoom;
                self.chartOption.xAxis[0].data = category;
                self.chartOption.series[0].data = barDataArr;
                var markLineData = [];
                var markLineData2 = [];
                var compRate = (data.company.attendanceRate * 100).toFixed(2);
                if (data.listGrid[0].organId == data.company.organId) {
                    markLineData.push(self.packMarkLineData(barDataArr.length, compRate));
                    self.initLineLengend(data.company.organName);
                } else {
                	var curRate = (data.listGrid[0].attendanceRate * 100).toFixed(2);
                	var curName = data.listGrid[0].organName;
                    markLineData.push(self.packMarkLineData(barDataArr.length, compRate));
                    markLineData2.push(self.packMarkLineData(barDataArr.length, curRate));
                    self.initLineLengend(data.company.organName, curName);
                    self.chartOption.series[1].name = curName + "劳动力效能：" + curRate + "%";
                }
                self.chartOption.series[0].name = "公司劳动力效能：" + compRate + "%";
                self.chartOption.series[0].markLine.data = markLineData;
                self.chartOption.series[1].markLine.data = markLineData2;
                self.chartObj.setOption(self.chartOption, true);
            }
        },
        initLineLengend: function (companyName, organName) {
            var _ZR = this.chartObj.getZrender();
            var line = 35, text = 10;
            if (undefined != organName) {
                line = 85;
                text = 60;
            }
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - line,
                    y: 290,
                    color: '#00FF00',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑',
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - text,
                    y: 290,
                    color: '#333',
                    text: '公司',
                    textAlign: 'left',
                    textFont: 'normal 12px 微软雅黑'
                },
                hoverable: false
            }));
            if (undefined != organName) {
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + 25,
                        y: 290,
                        color: '#6FB12D',
                        text: '—',
                        textAlign: 'left',
                        textFont: 'bolder 20px 微软雅黑',
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + 50,
                        y: 290,
                        color: '#333',
                        text: organName,
                        textAlign: 'left',
                        textFont: 'normal 12px 微软雅黑'
                    },
                    hoverable: false
                }));
            }
            _ZR.refresh();
        },
        packMarkLineData: function (cateLen, value) {
            return [{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}];
        },
        generateGrid: function (data) {
            var self = this;
            var result = data.listGrid;
//            if(!_.isEmpty(data.listBar)) {
            $(self.gridId).clearGridData().setGridParam({
                data: result
            }).trigger("reloadGrid");
            self.resizeGrid();
//            }
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#laborRatioArea').width() * 0.98);
        },
        resizeChart: function () {
            this.chartObj.resize();
        },
        eConsole: function (param) {
            var self = laborRatioObj;
            if (param.data.flag == 0) {
                getConditionValue(param.data.id, self.time, 1);
                laborEfficiencyGridObj.init(param.data.id, self.time, self.crowds, 1);
                $('#laborEfficiencyModal').modal('show');
                return;
            }
            self.chartObj.clear();
            self.resizeChart();
            self.initData(param.data.id, self.time, self.crowds);
            $('#returnParentOrgan').show();
        },
        returnBack: function () {
            var self = laborRatioObj;
            $.ajax({
                url: urls.getParentIdUrl,
                data: {organId: self.organId},
                success: function (data) {
                    if (data[0] != -1) {
                        self.chartObj.clear();
                        self.resizeChart();
                        self.initData(data[0], self.time, self.crowds);
                    }
                }
            });
        }
    };
    laborRatioObj.init();

    /**
     * 劳动力效能明细grid
     */
    var laborEfficiencyGridOption = {
        url: urls.findLaborEfficiencyUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,  //设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['姓名', '实出勤', '应出勤', '劳动力效能'],
        colModel: [
            {name: 'empName', index: 'empName', fixed: true, sortable: false, width: 150, align: 'center'},
            {
                name: 'actualAttendance',
                index: 'actualAttendance',
                fixed: true,
                sortable: false,
                width: 180,
                align: 'center'
            },
            {
                name: 'beInAttendance',
                index: 'beInAttendance',
                fixed: true,
                sortable: false,
                width: 180,
                align: 'center'
            },
            {
                name: 'attendanceRate',
                index: 'attendanceRate',
                fixed: true,
                sortable: false,
                width: 180,
                align: 'center',
                formatter: function (value) {
                    if (value == "") {
                        return 0;
                    }
                    return (parseFloat(value) * 100).toFixed(2) + "%";
                }
            }
        ],
        viewrecords: true,
        rowNum: 10,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        rowList: [10, 20, 30],
        pager: "#laborEfficiencySel"
    };
    var laborEfficiencyGridObj = {
        gridId: '#laborEfficiencyGrid',
        treeId: 'organTree',
        resultData: null,
        organId: null,
        init: function (organId, times, crowds, type) {
            var self = this;
            self.loadComple();
            self.loadTreeDataFun(organId);
            $(".time-warning-div").hide();
            /*if(self.organId == organId && self.times == times && self.crowds == crowds) {
             return;
             }*/
            self.organId = organId;
            self.times = times;
//            self.crowds = crowds;
            var beginTime;
            var endTime
            if (times != undefined) {
                if (type == 1) {
                    beginTime = times[0] + (times[1] <= 9 ? '0' + times[1] : times[1]);
                    endTime = times[2] + (times[3] <= 9 ? '0' + times[3] : times[3]);
                } else if (type == 2) {
                    beginTime = times;
                    endTime = times;
                }
            }

            if (self.hasInit) {
                self.reloadData(organId, beginTime, endTime, crowds);
                return;
            }
            laborEfficiencyGridOption.postData = {
                organId: organId,
                beginTime: beginTime,
                endTime: endTime,
                populationIds: crowds
            };
            $(self.gridId).jqGrid(laborEfficiencyGridOption);
            self.hasInit = true;
        },
        loadComple: function () {
            var self = this;
            $('#searchBtn').unbind().bind('click', function () {
                var organId = self.organId;
                var beginYear = $('#beginYear option:selected').attr('value');
                var beginMonth = $('#beginMonth option:selected').attr('value');
                var endYear = $('#endYear option:selected').attr('value');
                var endMonth = $('#endMonth option:selected').attr('value');
                var beginTime = beginYear + (parseInt(beginMonth) < 10 ? '0' + beginMonth : beginMonth);
                var endTime = endYear + (parseInt(endMonth) < 10 ? '0' + endMonth : endMonth);
                $(".time-warning-div").hide();
                if (endYear == beginYear) {
                    if (parseInt(endTime) - parseInt(beginTime) >= 6) {
                        $(".time-warning-div").show();
                        $("#timeWarning").text("开始时间和结束时间差不能大于6！");
                        return;
                    }
                } else {
                    var d = parseInt(endYear) - parseInt(beginYear);
                    var temp = parseInt(endTime) - 100 * d + 12 * d;
                    if (temp - parseInt(beginTime) >= 6) {
                        $(".time-warning-div").show();
                        $("#timeWarning").text("开始时间和结束时间差不能大于6！");
                        return;
                    }
                }
                if (parseInt(beginTime) > parseInt(endTime)) {
                    $(".time-warning-div").show();
                    $("#timeWarning").text("开始时间不能大于结束时间！");
                    return;
                }
                self.reloadData(organId, beginTime, endTime, null);
            });
        },
        reloadData: function (organId, beginTime, endTime, crowds) {
            var self = this;
            var params = {organId: organId, beginTime: beginTime, endTime: endTime, populationIds: crowds};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
        },
        loadTreeDataFun: function (organId) {
            var self = this;
            var organName;
            $.ajax({
                url: urls.queryOrganUrl,
                type: "post",
                data: {organId: organId},
                success: function (data) {
                    self.loadTreeFun(organId, data.organName);
                },
                error: function () {
                }
            });
        },
        loadTreeFun: function (organId, organName) {
            var self = this;
            var deptInfo = {
                id: organId,
                text: organName
            }
            $('#' + self.treeId).parent().find('.dropDownValue').text(organName);
            $('#' + self.treeId).organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                value: deptInfo,
                onSelect: function (ids, texts) {
                    self.organId = ids;
                }
            });
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#laborEfficiencyTable').width() * 0.98);
        }
    };
    /* 环比变化 */
    var laborEfficiencyTrendOption = {
        legend: {
            data: ['劳动力效能', '环比变化'],
            y: 'bottom',
            padding: 15
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (o) {
                var html;
                $.each(o, function (i, d) {
                    if (i == 0) html = '<div>' + d.name + '</div>';
                    html += '<div>' + d.seriesName + ' : ' + d.value + '%' + '</div>';
                })
                return html;
            }
        },
        calculable: false,
        grid: {
            x: 55,
            y: 25,
            x2: 55,
            y2: 80,
            borderWidth: 0
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                onZero: false,
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#666666'
                }
            },
            axisLabel: {
                show: true,
                /*rotate: 30,*/
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
            axisLine: {
                /*show : true,
                 onZero : false,*/
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisLabel: {
                formatter: '{value}%',
                itemStyle: {
                    color: '#666666'
                },
                textStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"
                }
            }
        },
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                    	width: 1,
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
                    },
                    formatter: '{value}%'
                    	
                }
            }],
        series: [{
            name: '劳动力效能',
            type: 'bar',
            clickable: true,
            barWidth: 30,//barMaxWidth: 30,
            itemStyle: {
                normal: {
                    color: '#5cb7f1',
                    type: 'dotted',
                    label: {
                        show: true,
                        formatter: function (i) {
                            return i.value + '%';
                        }
                    }
                }
            },
            data: []
        }, {
            name: '环比变化',
            type: 'line',
            yAxisIndex: 1,
            clickable: false,
            symbol: 'circle',
            itemStyle: {
                normal: {
                    color: '#f5b147',
                    type: 'dotted',
                    lineStyle: {
                    	width: 2,
                    },
                    label: {
                        show: false,
//                        formatter: function (i) {
//                            return i.value + '%';
//                        }
                    }
                }
            },
            data: []
        }]
    };
    /* 同比变化 */
    var laborTrendOption = {
        legend: {
//			data : [ '本年劳动力效能', '上年劳动力效能', '同比变化' ],
            data: [],
            y: 'bottom',
//            selectedMode: false
            padding: 15
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (o) {
                var html = '劳动力效能统计<hr style="margin-top: 5px;">';
                var len = o.length;
                $.each(o, function (i, d) {
                    var time = d.data.name + '';
                    if (i == len - 1) html += '<div>' + d.seriesName + ' : ' + d.value + '%' + '</div>';
                    else if (i == 0) html += '<div style="margin-top: -15px;">' + time.substr(0, 4) + '年' + time.substr(4, 2) + '月' + ' : ' + d.value + '%' + '</div>';
                    else html += '<div>' + time.substr(0, 4) + '年' + time.substr(4, 2) + '月' + ' : ' + d.value + '%' + '</div>';
                })
                return html;
            }
        },
        calculable: false,
        grid: {
            x: 55,
            y: 25,
            x2: 55,
            y2: 80,
            borderWidth: 0
        },
        xAxis: [{
            type: 'category',
            splitLine: true,
            axisLine: {
            	onZero: false,
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisLabel: {
                show: true,
                /*rotate: 30,*/

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
            splitNumber: 5,
            splitLine: true,
            axisLine: {
                /*show : true,
                 onZero : false,*/
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisLabel: {
                formatter: '{value}%',
                itemStyle: {
                    color: '#666666'
                },
                textStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"
                }
            }
        },
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                    	width: 1,
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
                    },
                    formatter: '{value}%'
                    	
                }
            }],
	        series: [{
	            name: '',
	            type: 'bar',
	            clickable: true,
	            barMaxWidth: 30,//barMaxWidth: 30,
	            itemStyle: {
	                normal: {
	                    color: '#5cb7f1',
	                    type: 'dotted',
	                    label: {
	                        show: true,
	                        formatter: function (i) {
	                            return i.value + '%';
	                        }
	                    }
	                }
	            },
	            data: [],
                barGap: 10
	        }, {
	            name: '',
	            type: 'bar',
	            clickable: true,
	            barMaxWidth: 30,
	            itemStyle: {
	                normal: {
	                    color: '#6cdcb4',
	                    type: 'dotted',
	                    label: {
	                        show: true,
	                        formatter: function (i) {
	                            return i.value + '%';
	                        }
	                    }
	                }
	            },
	            data: []
	        }, {
	            name: '同比变化',
	            type: 'line',
	            yAxisIndex: 1,
	            clickable: false,
	            symbol: 'circle',
	            itemStyle: {
	                normal: {
	                    color: '#f5b147',
	                    type: 'dotted',
	                    lineStyle: {
	                    	width: 2,
	                    },
	                    label: {
	                        show: false,
	//                        formatter: function (i) {
	//                            return i.value + '%';
	//                        }
	                    }
	                }
	            },
	            data: []
	        }],
        dataZoom : {
            show: true,
            realtime: true,
            height: 15,
            start: 0,
            end: 50,
            showDetail: false,
            zoomLock: true,
            y: 310
        }
        
    };

    /** 劳动力效能趋势 */
    var laborEfficiencyTrendObj = {
        radioId: "laborEfficiencyRatio",
        radioChart: null,
        radioChartOption: _.clone(laborEfficiencyTrendOption),
        basisId: "laborEfficiencyBasis",
        basisChart: null,
        basisChartOption: _.clone(laborTrendOption),
        organId: null,
        time: null,
        crowds: null,
        init: function () {
            var self = this;
            $('#' + self.radioId).height($('#' + self.radioId).parents('.chart-view').height());
            $('#' + self.basisId).height($('#' + self.radioId).parents('.chart-view').height());
            self.radioChart = initEChart(self.radioId);
            self.basisChart = initEChart(self.basisId);
            // 添加点击事件
            self.radioChart.on(ecConfig.EVENT.CLICK, self.eConsole);
            self.basisChart.on(ecConfig.EVENT.CLICK, self.eConsole);
            self.trendTimecrowdFun();
        },
        trendTimecrowdFun: function () {
            var self = this;
            $("#trendSelect").selection({
                dateType: 2,
                dateRange: {
                    min: timeObj.dateBegin,
                    max: timeObj.dateEnd
                },
                dateSelected: timeObj.selectedFullYearMonth,
                dateSelectedLength: 6,
                crowdSelected: [0],
                ok: function (event, data) {
                    var cd = data.crowd.join(',') == '0' ? '' : data.crowd.join(',');
                    self.initData(self.organId, data.date, cd);
                }
            });
        },
        initData: function (organId, time, crowds) {
            var self = this;
            self.resizeRatioChart();
            self.resizeBasisChart();
            if (self.topId == undefined) {
                self.topId = organId;
            }
            /*if (self.organId == organId && self.time == time && self.crowds == crowds) {
             return;
             }*/
            if (undefined == organId) organId = self.organId;
            else self.organId = organId;
            if (undefined == time) time = self.time;
            else self.time = time;
            if (undefined == crowds) crowds = self.crowds;
            else self.crowds = crowds;
            clearEChart(self.radioChart);
            clearEChart(self.basisChart);
            self.getRequestData(organId, time, crowds);
        },
        getRequestData: function (organId, time, crowds) {
            var self = this;
            self.organId = organId;
            var beginTime;
            var endTime
            if (time != undefined) {
                beginTime = time[0] + (time[1] <= 9 ? '0' + time[1] : time[1]);
                endTime = time[2] + (time[3] <= 9 ? '0' + time[3] : time[3]);
            }
            showLoadingChart(self.radioChart);
            showLoadingChart(self.basisChart);
            $.get(urls.getLaborEfficiencyTrendUrl, {
                organId: organId, beginTime: beginTime, endTime: endTime,
                populationIds: crowds
            }, function (data) {
                self.generateChart(data);
            });
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var lineDataArr = [];
            var category2 = [];
            var barDataArr1 = [];
            var barDataArr2 = [];
            var lineDataArr2 = [];
            clearEChart(self.radioChart);
            clearEChart(self.basisChart);
            self.resizeRatioChart();
            self.resizeBasisChart();
            if (_.isEmpty(data.listRatio)) {
                showNoDataEcharts(self.radioChart);
            } else {
                $.each(data.listRatio, function (i, item) {
                    category.push(item.yearMonth.toString().substr(2, 2) + '/' + item.yearMonth.toString().substr(4, 2));
                    barDataArr.push({name: item.yearMonth, value: parseFloat(item.attendanceRate * 100).toFixed(2)});
                    lineDataArr.push(parseFloat(item.rate * 100).toFixed(2));
                });
                self.radioChartOption.xAxis[0].data = category;
                self.radioChartOption.series[0].data = barDataArr;
                self.radioChartOption.series[1].data = lineDataArr;
                self.radioChart.setOption(self.radioChartOption, true);
            }
            if (_.isEmpty(data.listBasis1)) {
                showNoDataEcharts(self.basisChart);
            } else {
                var curLen = data.listBasis1.length;
                var curTime;
                $.each(data.listBasis1, function (i, item) {
                    if (i == curLen - 1) curTime = item.yearMonth.toString().substr(0, 4) + '年';
                    category2.push(item.yearMonth.toString().substr(2, 2) + '/' + item.yearMonth.toString().substr(4, 2));
                    barDataArr1.push({name: item.yearMonth, value: parseFloat(item.attendanceRate * 100).toFixed(2)});
                });
                var oldLen = data.listBasis1.length;
                var oldTime;
                $.each(data.listBasis2, function (i, item) {
                    if (i == oldLen - 1) oldTime = item.yearMonth.toString().substr(0, 4) + '年';
                    barDataArr2.push({name: item.yearMonth, value: parseFloat(item.attendanceRate * 100).toFixed(2)});
                });
                $.each(data.listBasis, function (i, item) {
                    lineDataArr2.push(parseFloat(item * 100).toFixed(2));
                });
                self.basisChartOption.legend.data = [curTime + '劳动力效能', oldTime + '劳动力效能', '同比变化'];
                self.basisChartOption.xAxis[0].data = category2;
                self.basisChartOption.series[0].data = barDataArr1;
                self.basisChartOption.series[0].name = curTime + '劳动力效能';
                self.basisChartOption.series[1].data = barDataArr2;
                self.basisChartOption.series[1].name = oldTime + '劳动力效能';
                self.basisChartOption.series[2].data = lineDataArr2;
                self.basisChart.setOption(self.basisChartOption, true);
            }
        },
        resizeRatioChart: function () {
            this.radioChart.resize();
        },
        resizeBasisChart: function () {
            this.basisChart.resize();
        },
        eConsole: function (param) {
            var self = laborEfficiencyTrendObj;
            var time = param.data.name + '';
            getConditionValue(self.organId, time, 2);
            laborEfficiencyGridObj.init(self.organId, time, self.crowds, 2);
            $('#laborEfficiencyModal').modal('show');
        }
    };
    laborEfficiencyTrendObj.init();

    /**
     * 加班时长chart
     */
    var overtimeChartOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 55,
            y2: 80,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (o) {
                var html = '';
                $.each(o, function (i, d) {
                    if (i == 0)
                        html += '<div>' + d.name + ' : ' + d.value + '小时</div>';
                })
                return html;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                	width: 1,
                	color: '#666666'
                }
            },
            axisLabel: {
                show: true,
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
        	name: '(小时)',
        	nameTextStyle:{
                color: '#666666',
                fontSize: 12,
                fontFamily: "'微软雅黑', 'verdana'"
        	},
            type: 'value',
            min: null,
            max: null,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: '#666666'
                }
            },
            axisLabel: {
                formatter: '{value}',
                textStyle: {
                    color: '#666666',
                    fontSize: 12,
                    fontFamily: "'微软雅黑', 'verdana'"
                }
            }
        }],
        series: [{
            type: 'bar',
            clickable: true,
            barCategoryGap: '45%',
            barWidth: 30,
            itemStyle: {
                normal: {
                    color: '#5cb7f1',
                    label: {
                        show: true,
                        textStyle: {
                            color: '#333'
                        },
                        formatter: function (o) {
                            return o.value;
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
                        color: '#00FF00',
                        lineStyle: {
                            type: 'solid'
                        }
                    }
                },
                data: []
            }
        },
            {
                type: 'bar',
                barMaxWidth: 1,
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
            }
        ],
        dataZoom: {}
    };

    var overtimeTimecrowd = {
        init: function () {
            $("#overtimeSelect").selection({
                dateType: 2,
                dateRange: {
                    min: timeObj.dateBegin,
                    max: timeObj.dateEnd
                },
                dateSelectedLength: 6,
                dateSelected: timeObj.defaultDate,
                crowdSelected: [0],
                ok: function (event, data) {
                    var cd = data.crowd.join(',') == '0' ? '' : data.crowd.join(',');
                    overtimeAvgObj.initData(self.organId, data.date, cd);
                    overtimeTotalObj.initData(self.organId, data.date, cd);
                }
            });
        }
    };

    /** 平均加班时长 */
    var overtimeAvgObj = {
        avgChartId: "overtimeAvgnumChart",
        avgChart: null,
        avgChartOption: _.clone(overtimeChartOption),
        organId: null,
        time: null,
        crowds: null,
        init: function () {
            var self = this;
            $('#' + self.avgChartId).height($('#' + self.avgChartId).parents('.chart-view').height());
            self.avgChart = initEChart(self.avgChartId);
            // 添加点击事件
            self.avgChart.on(ecConfig.EVENT.CLICK, self.avgEConsole);
            self.returnBack();
            overtimeTimecrowd.init();
        },
        initData: function (organId, time, crowds) {
            var self = this;
            self.resizeChart();
            if (self.topId == undefined) {
                self.topId = organId;
            }
            /*if (self.organId == organId && self.time == time && self.crowds == crowds) {
             return;
             }*/
            if (undefined == organId) organId = self.organId;
            else self.organId = organId;
            if (undefined == time) time = self.time;
            else self.time = time;
            if (undefined == crowds) crowds = self.crowds;
            else self.crowds = crowds;
            clearEChart(self.avgChart);
            self.getRequestData(organId, time, crowds);
        },
        getRequestData: function (organId, time, crowds) {
            var self = this;
            var beginTime;
            var endTime;
            if (time != undefined) {
                beginTime = time[0] + (time[1] <= 9 ? '0' + time[1] : time[1]);
                endTime = time[2] + (time[3] <= 9 ? '0' + time[3] : time[3]);
            }
            showLoadingChart(self.avgChart);
            $.get(urls.queryOvertimeByOrganUrl, {
                organId: organId, beginTime: beginTime, endTime: endTime,
                populationIds: crowds
            }, function (data) {
                self.generateChart(data);
                if (self.topId == organId || reqOrgId == organId) {
                    $('#returnParentAvgOvertime').hide();
                }
            });
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var min = 0, max = 0;
            clearEChart(self.avgChart);
            self.resizeChart();
            if (_.isEmpty(data.avgList)) {
                showNoDataEcharts(self.avgChart);
                $('#returnParentAvgOvertime').hide();
            } else {
                $.each(data.avgList, function (i, item) {
                    category.push(item.organName);
                    barDataArr.push({
                        name: item.organName,
                        value: parseFloat(item.avgNum).toFixed(2),
                        id: item.organId,
                        flag: item.hasChildren
                    });
                    if (i == 0) {
                        max = parseFloat(item.avgNum).toFixed(2);
                    }
                });
                var len = barDataArr.length;
                var num = 4, dataZoom = {};//4个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 15,
                        end: end,
                        showDetail: false,
                        zoomLock: true,
                        y: 310
                    };
                }
                self.avgChartOption.dataZoom = dataZoom;
                self.avgChartOption.xAxis[0].data = category;
                self.avgChartOption.yAxis[0].min = min;
                self.avgChartOption.yAxis[0].max = max;
                self.avgChartOption.series[0].data = barDataArr;
                var markLineData = [];
                var markLineData2 = [];
                if (data.organId == data.company.organId) {
                    markLineData.push(self.packMarkLineData(barDataArr.length, data.company.avgNum));
                    self.initLineLengend(data.company.organName);
                } else {
                    markLineData.push(self.packMarkLineData(barDataArr.length, data.company.avgNum));
                    markLineData2.push(self.packMarkLineData(barDataArr.length, data.avgNum));
                    self.initLineLengend(data.company.organName, data.organName);
                }
                self.avgChartOption.series[0].markLine.data = markLineData;
                self.avgChartOption.series[0].name = "公司平均加班：" + data.company.avgNum + "小时";
                self.avgChartOption.series[1].markLine.data = markLineData2;
                self.avgChartOption.series[1].name = data.organName + "平均加班：" + data.avgNum + "小时";
                self.avgChart.setOption(self.avgChartOption, true);
            }
        },
        initLineLengend: function (companyName, organName) {
            var _ZR = this.avgChart.getZrender();
            var line = 35, text = 10;
            if (undefined != organName) {
                line = 85;
                text = 60;
            }
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - line,
                    y: 290,
                    color: '#00FF00',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑',
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - text,
                    y: 290,
                    color: '#333',
                    text: '公司',
                    textAlign: 'left',
                    textFont: 'normal 12px 微软雅黑'
                },
                hoverable: false
            }));
            if (undefined != organName) {
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + 25,
                        y: 290,
                        color: '#6FB12D',
                        text: '—',
                        textAlign: 'left',
                        textFont: 'bolder 20px 微软雅黑',
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + 50,
                        y: 290,
                        color: '#333',
                        text: organName,
                        textAlign: 'left',
                        textFont: 'normal 12px 微软雅黑'
                    },
                    hoverable: false
                }));
            }
            _ZR.refresh();
        },
        packMarkLineData: function (cateLen, value) {
            return [{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}];
        },
        resizeChart: function () {
            this.avgChart.resize();
        },
        avgEConsole: function (param) {
            var self = overtimeAvgObj;
            if (param.data.flag == 0) {
                var time = self.time;
                if (time != null) {
                    overtimeConditionObj.date = time[2] + '/' + (time[3] <= 9 ? '0' + time[3] : time[3]);
                } else {
                    overtimeConditionObj.date = minMaxTimes[1].substr(0, 4) + '/' + minMaxTimes[1].substr(4, 2);
                }
                overtimeConditionObj.crowds = self.crowds;
                overtimeConditionObj.init(param.data.id);
                return;
            }
            self.avgChart.clear();
            self.resizeChart();
            self.initData(param.data.id);
            $('#returnParentAvgOvertime').show();
        },
        returnBack: function () {
            var self = overtimeAvgObj;
            $('#returnParentAvgOvertime').click(function () {
                $.ajax({
                    url: urls.getParentIdUrl,
                    data: {organId: self.organId},
                    success: function (data) {
                        if (data[0] != -1) {
                            self.avgChart.clear();
                            self.resizeChart();
                            self.initData(data[0], self.time, self.crowds);
                        }
                    }
                });
            });
        }
    };
    overtimeAvgObj.init();

    /** 加班总时长 */
    var overtimeTotalObj = {
        totalChartId: "overtimeTotalChart",
        totalChart: null,
        totalChartOption: _.clone(overtimeChartOption),
        organId: null,
        time: null,
        crowds: null,
        flag: false,
        init: function () {
            var self = this;
            $('#' + self.totalChartId).height($('#overtimeAvgnumChart').parents('.chart-view').height());
            self.totalChart = initEChart(self.totalChartId);
            // 添加点击事件
            self.totalChart.on(ecConfig.EVENT.CLICK, self.totalEConsole);
            self.returnBack();
            overtimeTimecrowd.init();
        },
        initData: function (organId, time, crowds) {
            var self = this;
            self.resizeChart();
            if (self.topId == undefined) {
                self.topId = organId;
            }
            /*if (self.organId == organId && self.time == time && self.crowds == crowds) {
             return;
             }*/
            if (undefined == organId) organId = self.organId;
            else self.organId = organId;
            if (undefined == time) time = self.time;
            else self.time = time;
            if (undefined == crowds) crowds = self.crowds;
            else self.crowds = crowds;
            clearEChart(self.totalChart);
            self.getRequestData(organId, time, crowds);
        },
        getRequestData: function (organId, time, crowds) {
            var self = this;
            var beginTime;
            var endTime;
            if (time != undefined) {
                beginTime = time[0] + (time[1] <= 9 ? '0' + time[1] : time[1]);
                endTime = time[2] + (time[3] <= 9 ? '0' + time[3] : time[3]);
            }
            showLoadingChart(self.totalChart);
            $.get(urls.queryOvertimeByOrganUrl, {
                organId: organId, beginTime: beginTime, endTime: endTime,
                populationIds: crowds
            }, function (data) {
                self.generateChart(data);
                if (self.topId == organId || reqOrgId == organId) {
                    $('#returnParentTotalOvertime').hide();
                }
            });
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
            var inx = (data.company.hourCount + '').indexOf('.');
            var temp = numericShift(inx);
            var min = 0, max = parseInt(data.company.hourCount / temp + 1) * temp;
            clearEChart(self.totalChart);
            self.resizeChart();
            if (_.isEmpty(data.couList)) {
                showNoDataEcharts(self.totalChart);
                $('#returnParentTotalOvertime').hide();
            } else {
                $.each(data.couList, function (i, item) {
                    category.push(item.organName);
                    barDataArr.push({
                        name: item.organName,
                        value: parseFloat(item.hourCount).toFixed(2),
                        id: item.organId,
                        flag: item.hasChildren
                    });
                });
                var len = barDataArr.length;
                var num = 4, dataZoom = {};//4个以上显示滚动条
                if (len > num) {
                    var end = 50;
                    if (len > 8) {
                        end = 22;
                    }
                    dataZoom = {
                        show: true,
                        realtime: true,
                        height: 15,
                        end: end,
                        showDetail: false,
                        zoomLock: true,
                        y: 310
                    };
                }
                self.totalChartOption.dataZoom = dataZoom;
                self.totalChartOption.xAxis[0].data = category;
                self.totalChartOption.yAxis[0].min = min;
                self.totalChartOption.yAxis[0].max = max;
                self.totalChartOption.series[0].data = barDataArr;
                var markLineData = [];
                var markLineData2 = [];
                if (data.organId == data.company.organId) {
                    markLineData.push(self.packMarkLineData(barDataArr.length, data.company.hourCount));
                    self.initLineLengend(data.company.organName);
                } else {
                    markLineData.push(self.packMarkLineData(barDataArr.length, data.company.hourCount));
                    markLineData2.push(self.packMarkLineData(barDataArr.length, data.conNum));
                    self.initLineLengend(data.company.organName, data.organName);
                }
                self.totalChartOption.series[0].markLine.data = markLineData;
                self.totalChartOption.series[0].name = "公司总加班：" + data.company.hourCount + "小时";
                self.totalChartOption.series[1].markLine.data = markLineData2;
                self.totalChartOption.series[1].name = data.organName + "总加班：" + data.conNum + "小时";
                self.totalChart.setOption(self.totalChartOption, true);
            }
        },
        initLineLengend: function (companyName, organName) {
            var self = this;
            var _ZR = this.totalChart.getZrender();
            var line = 35, text = 10;
            if (self.flag) {
                if (undefined != organName) {
                    line = 85;
                    text = 60;
                }
            } else {
                line = -205;
                text = -230;
                self.flag = true;
            }
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - line,
                    y: 290,
                    color: '#00FF00',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑',
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - text,
                    y: 290,
                    color: '#333',
                    text: '公司',
                    textAlign: 'left',
                    textFont: 'normal 12px 微软雅黑'
                },
                hoverable: false
            }));
            if (undefined != organName) {
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + 25,
                        y: 290,
                        color: '#6FB12D',
                        text: '—',
                        textAlign: 'left',
                        textFont: 'bolder 20px 微软雅黑',
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: _ZR.getWidth() / 2 + 50,
                        y: 290,
                        color: '#333',
                        text: organName,
                        textAlign: 'left',
                        textFont: 'normal 12px 微软雅黑'
                    },
                    hoverable: false
                }));
            }

            _ZR.refresh();
        },
        packMarkLineData: function (cateLen, value) {
            return [{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}];
        },
        resizeChart: function () {
            this.totalChart.resize();
        },
        totalEConsole: function (param) {
            var self = overtimeTotalObj;
            if (param.data.flag == 0) {
                var time = self.time;
                if (time != undefined) {
                    overtimeConditionObj.date = time[2] + '/' + (time[3] <= 9 ? '0' + time[3] : time[3]);
                } else {
                    overtimeConditionObj.date = minMaxTimes[1].substr(0, 4) + '/' + minMaxTimes[1].substr(4, 2);
                }
                overtimeConditionObj.crowds = self.crowds;
                overtimeConditionObj.init(param.data.id);
                return;
            }
            self.totalChart.clear();
            self.resizeChart();
            self.initData(param.data.id);
            $('#returnParentTotalOvertime').show();
        },
        returnBack: function () {
            var self = overtimeTotalObj;
            $('#returnParentTotalOvertime').click(function () {
                $.ajax({
                    url: urls.getParentIdUrl,
                    data: {organId: self.organId},
                    success: function (data) {
                        if (data[0] != -1) {
                            self.totalChart.clear();
                            self.resizeChart();
                            self.initData(data[0], self.time, self.crowds);
                        }
                    }
                });
            });
        }
    };
    overtimeTotalObj.init();

    function numericShift(num) {
        var temp = 0;
        switch (num) {
            case 2:
                temp = 10;
                break;
            case 3:
                temp = 100;
                break;
            case 4:
                temp = 1000;
                break;
            case 5:
                temp = 10000;
                break;
        }
        return temp;
    };

    /**
     * 获取劳动力效能明细的查询条件，type(1:times为数组，2:times为字符串)
     */
    function getConditionValue(organId, times, type) {
        $.get(urls.getConditionValueUrl, {organId: organId}, function (data) {
            /*$('#organTree').find('option').remove();*/
            $('#beginYear').find('option').remove();
            $('#beginMonth').find('option').remove();
            $('#endYear').find('option').remove();
            $('#endMonth').find('option').remove();
            /*$.each(data.listOrgans, function(i, o) {
             $('#organTree').append('<option value="' + o.organId + '">' + o.organName + '</option>');
             });*/

            var minyear = parseInt(data.beginTime.toString().substr(0, 4));
            var maxyear = parseInt(data.endTime.toString().substr(0, 4));
            var minmonth = parseInt(data.beginTime.toString().substr(4, 2));
            var maxmonth = parseInt(data.endTime.toString().substr(4, 2));

            var selectedBeginYear;
            var selectedBeginMonth;
            var selectedEndYear;
            var selectedEndMonth;
            if (times != undefined) {
                if (type == 1) {
                    selectedBeginYear = times[0];
                    selectedBeginMonth = parseInt(times[1]);
                    selectedEndYear = times[2];
                    selectedEndMonth = parseInt(times[3]);
                } else if (type == 2) {
                    selectedBeginYear = times.substr(0, 4);
                    selectedBeginMonth = parseInt(times.substr(4, 2));
                    selectedEndYear = times.substr(0, 4);
                    selectedEndMonth = parseInt(times.substr(4, 2));
                }
            } else {
                selectedBeginYear = minMaxTimes[1].substr(0, 4);
                selectedBeginMonth = parseInt(minMaxTimes[1].substr(4, 2));
                selectedEndYear = minMaxTimes[1].substr(0, 4);
                selectedEndMonth = parseInt(minMaxTimes[1].substr(4, 2));
            }
            //from年
            for (var i = maxyear; i >= minyear; i--) {
                if (selectedBeginYear == i) {
                    $('#beginYear').append('<option value="' + i + '" selected="selected">' + i + '年</option>');
                } else {
                    $('#beginYear').append('<option value="' + i + '">' + i + '年</option>');
                }
            }
            //to年
            for (var i = maxyear; i >= minyear; i--) {
                if (selectedEndYear == i) {
                    $('#endYear').append('<option value="' + i + '" selected="selected">' + i + '年</option>');
                } else {
                    $('#endYear').append('<option value="' + i + '">' + i + '年</option>');
                }
            }

            var min = [], max = [], all = [];
            var min2 = [], max2 = [], all2 = [];
            for (var i = 1; i <= 12; i++) {
                if (i == selectedBeginMonth) {
                    min.push('<option value="' + i + '" selected="selected">' + i + '月</option>');
                    max.push('<option value="' + i + '" selected="selected">' + i + '月</option>');
                    all.push('<option value="' + i + '" selected="selected">' + i + '月</option>');
                } else {
                    if (i >= minmonth) {
                        min.push('<option value="' + i + '">' + i + '月</option>');
                    }
                    if (i <= maxmonth) {
                        max.push('<option value="' + i + '">' + i + '月</option>');
                    }
                    all.push('<option value="' + i + '">' + i + '月</option>');
                }
                if (i == selectedEndMonth) {
                    min2.push('<option value="' + i + '" selected="selected">' + i + '月</option>');
                    max2.push('<option value="' + i + '" selected="selected">' + i + '月</option>');
                    all2.push('<option value="' + i + '" selected="selected">' + i + '月</option>');
                } else {
                    if (i >= minmonth) {
                        min2.push('<option value="' + i + '">' + i + '月</option>');
                    }
                    if (i <= maxmonth) {
                        max2.push('<option value="' + i + '">' + i + '月</option>');
                    }
                    all2.push('<option value="' + i + '">' + i + '月</option>');
                }
            }
            if (selectedBeginYear == minyear) {
                $("#beginMonth").append(min.join(''));
            } else if (selectedBeginYear == maxyear) {
                $("#beginMonth").append(max.join(''));
            } else {
                $("#beginMonth").append(all.join(''));
            }
            if (selectedEndYear == minyear) {
                $("#endMonth").append(min2.join(''));
            } else if (selectedEndYear == maxyear) {
                $("#endMonth").append(max2.join(''));
            } else {
                $("#endMonth").append(all2.join(''));
            }

            $("#beginYear").unbind().change(function () {
                if ($(this).val() == minyear) {
                    $("#beginMonth").html(min.join(''));
                } else if ($(this).val() == maxyear) {
                    $("#beginMonth").html(max.join(''));
                } else {
                    $("#beginMonth").html(all.join(''));
                }
            });

            $("#endYear").unbind().change(function () {
                if ($(this).val() == minyear) {
                    $("#endMonth").html(min.join(''));
                } else if ($(this).val() == maxyear) {
                    $("#endMonth").html(max.join(''));
                } else {
                    $("#endMonth").html(all.join(''));
                }
            });
        });
    };

    /**
     * 出勤明细-搜索框
     * */
    var attendanceDetailSearchObj = {
        searchInput: 'searchUserNameTxt',
        yearId: 'detailYear',
        monthId: 'detailMonth',
        searchId: 'searchBtnId',
        clearId: 'clearBtnId',
        organId: null,
        year: null,
        month: null,
        maxMonth: null,
        yearList: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.getDateFun();
        },
        getDateFun: function () {
            var self = this;
            self.year = dateYear;
            self.month = dateMonth;
            self.maxMonth = dateMonth;
            self.yearList = dateYearList;
            self.loadYearFun();
            self.clearBtnClickFun();
        },
        loadYearFun: function () {
            var self = this;
            $('#' + self.yearId).empty();
            var option = '';
            $.each(self.yearList, function (index, object) {
                option += "<option value='" + object + "'>" + object + "年</option>";
            });
            $('#' + self.yearId).append(option);
            $('#' + self.yearId).val(self.year);
            self.setMonthFun();
        },
        setMonthFun: function () {
            var self = this;
            var array = new Array();
            for (var i = 1; i <= self.maxMonth; i++) {
                array.push(i);
            }
            defaultDatas.partMonth = array;
            if(self.year == dateYear){
            	self.appendMonthFun(defaultDatas.partMonth);
            } else {
            	self.appendMonthFun(defaultDatas.fullMonth);
            }
            $('#' + self.monthId).val(self.month);
            self.selectChangeFun();
        },
        selectChangeFun: function () {
            var self = this;
            $('#' + self.yearId).change(function () {
                var option = $('#' + self.yearId + ' option:selected').attr('value');
//    			self.month = '1';
                self.month = $('#' + self.monthId + ' option:selected').attr('value');
                if (option == '') {
                    self.appendMonthFun("", true);
                    self.month = '';
                } else if (option == dateYear) {
                    self.appendMonthFun(defaultDatas.partMonth);
                    self.year = dateYear;
                } else {
                    self.appendMonthFun(defaultDatas.fullMonth);
                    self.year = option;
                }
            });
        },
        appendMonthFun: function (data, flag) {
            var self = this;
            var option = '';
            if (flag) {
                $('#' + self.monthId).empty();
//	    		option += "<option value=''>请选择</option>";
//    			$('#' + self.monthId).append(option);
            } else {
                $('#' + self.monthId).empty();
//    			option += "<option value=''>请选择</option>";
                var num = 0;
                $.each(data, function (ind, obj) {
                	if(obj >= self.month){
                		num++;
                	}
                    option += "<option value= " + obj + ">" + obj + "月</option>";
                });
                $('#' + self.monthId).append(option);
                if(num > 0){
                	$('#' + self.monthId).val(self.month);
                } else {
                	$('#' + self.monthId).val('1');
                	self.month = '1';
                }
                $('#' + self.monthId).change(function () {
                    self.month = $('#' + self.monthId + ' option:selected').attr('value');
                });
            }
            self.searchBtnClickFun();
        },
        searchBtnClickFun: function () {
            var self = this;
            $('#' + self.searchId).unbind('click').bind('click', function () {
                attendanceDetailCheckWorkTypeObj.userName = $('#' + self.searchInput).val();
                attendanceDetailCheckWorkTypeObj.date = self.year + '' + self.month;
                attendanceDetailCheckWorkTypeObj.init();
            });
        },
        clearBtnClickFun: function () {
            var self = this;
            $('#' + self.clearId).unbind('click').bind('click', function () {
                $('#' + self.searchInput).val('');
                $('#' + self.yearId).val(dateYear);
                $('#' + self.monthId).val(dateMonth);
                self.year = dateYear,
                    self.month = dateMonth;
            });
        }
    }
    attendanceDetailSearchObj.init(reqOrgId);

    /**
     * 出勤明细-考勤类型
     * */
    var attendanceDetailCheckWorkTypeObj = {
        organId: null,
        userName: null,
        date: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            if (self.date == null) {
                self.date = dateYear + '' + dateMonth;
            }
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            $.ajax({
                url: urls.queryCheckWorkTypeUrl,
                type: 'post',
                success: function (data) {
                    self.loadGridFun(data);
                },
                error: function () {
                }
            });
        },
        loadGridFun: function (data) {
            var self = this;
            var modelArray = data.colModelArray;
            if (modelArray != null && modelArray.length > 0) {
                modelArray[1].formatter = nameFormatter;
                modelArray[modelArray.length - 1].formatter = operateFormatter;
                for (var i = 5; i < modelArray.length - 1; i++) {
                    modelArray[i].formatter = changeNullFormatter;
                }
                attendanceDetailObj.colModelArray = modelArray;
                attendanceDetailObj.checkWorkTypeNameArray = data.nameArray;
                attendanceDetailObj.organId = self.organId;
                attendanceDetailObj.userName = self.userName;
                attendanceDetailObj.date = self.date;
                attendanceDetailObj.init();
            }
        }
    }
    /**
     * 出勤明细
     * */
    var attendanceDetailObj = {
        gridId: 'attendanceDetailGrid',
        pagerId: 'attendanceDetailGridPager',
        tabId: 'tab-content',
        checkWorkTypeNameArray: null,
        colModelArray: null,
        organId: null,
        userName: null,
        date: null,
        isTrue: false,
        init: function () {
            var self = this;
            self.initGridFun();
        },
        initGridFun: function () {
            var self = this;
            var param = {
                organId: self.organId,
                userName: self.userName,
                date: self.date
            };
            if (self.isTrue) {
                self.reloadGridFun(param);
            }
            $('#' + self.gridId).jqGrid({
                url: urls.queryAttendanceDetailUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
                altRows: true,
                shrinkToFit: true,
                width: '100%',
                height: 'auto',
                colNames: self.checkWorkTypeNameArray,
                colModel: self.colModelArray,
                viewrecords: true,
                rowNum: 10,
                rowList: [10, 20, 30],
                rowHeight: 36,
                styleUI: 'Bootstrap',
                pager: '#' + self.pagerId,
                loadComplete: function (xhr) {
                    $('.talent_col').unbind().bind('click', function () {
                        var _this = $(this);
                        var empId = _this.attr('data');
                        var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random();
                        window.open(herf);
                    });
                    $('.detail_col').unbind().bind('click', function () {
                        var _this = $(this);
                        var param = _this.attr('data');
                        showOnePersonDetail(param);
                    });
                }
            });
//            $('#' + self.gridId).setGridHeight($('.' + self.tabId).height() * 0.9);
            self.isTrue = true;
        },
        reloadGridFun: function (param) {
            var self = this;
            $('#' + self.gridId).clearGridData().setGridParam({
                postData: param
            }).trigger("reloadGrid");
        }
    }
    //姓名列
    var nameFormatter = function (value, options, rows) {
        return "<a href='javascript:void(0)' style=\"color:#08B560;\" data='" + rows.empId + "' class='talent_col' >" + value + "</a>";
    };
    //操作列
    var operateFormatter = function (cellvalue, options, rowObject) {
        var param = rowObject.empId + '@' + rowObject.userName + '@' + rowObject.organId + '@' + attendanceDetailSearchObj.year + '@' + attendanceDetailSearchObj.month;
        return "<a href='javascript:void(0)' style=\"color:#0099CB;\" data='" + param + "' class='detail_col' >明细</a>";
    };
    //其他列
    var changeNullFormatter = function (cellvalue, options, rowObject) {
        if (cellvalue == null) {
            return '0';
        } else {
            return cellvalue;
        }
    };
    /**
     * 查看个人明细操作
     * */
    var showOnePersonDetail = function (data) {
        var arr = data.split('@');
        onePersonDetailObj.empId = arr[0];
        onePersonDetailObj.userName = arr[1];
        onePersonDetailObj.organId = arr[2];
        onePersonDetailObj.year = arr[3];
        onePersonDetailObj.month = arr[4];
        onePersonDetailObj.init();
    }
    /**
     * 出勤明细-查看个人明细
     * */
    var onePersonDetailObj = {
        modalId: 'onePersonDetailModal',
        yearId: 'onePersonDetailYearSelect',
        monthId: 'onePersonDetailMonthSelect',
        okId: 'onePersonDetailOkBtnId',
        userNameId: 'userNameId',
        organId: null,
        empId: null,
        userName: null,
        date: null,
        year: null,
        month: null,
        maxMonth: null,
        init: function () {
            var self = this;
            self.setModalShowFun();
        },
        setModalShowFun: function () {
            var self = this;
            $('#' + self.modalId).modal('show');
            $('#' + self.modalId).on('shown.bs.modal', function () {
                self.setUserNameFun();
                self.getDateFun();
                onePersonDetailCheckWorkTypeObj.date = self.year + '' + self.month;
                onePersonDetailCheckWorkTypeObj.empId = self.empId;
                onePersonDetailCheckWorkTypeObj.organId = self.organId;
                onePersonDetailCheckWorkTypeObj.init();
            });
            $('#' + self.modalId).on('hidden.bs.modal', function () {
                $('#' + self.modalId).off();
            });
        },
        setUserNameFun: function () {
            var self = this;
            $('#' + self.userNameId).html(self.userName);
        },
        getDateFun: function () {
            var self = this;
            self.maxMonth = dateMonth;
            self.yearList = dateYearList;
            self.loadYearFun();
        },
        loadYearFun: function () {
            var self = this;
            $('#' + self.yearId).empty();
            var option = '';
            $.each(self.yearList, function (index, object) {
                option += "<option value='" + object + "'>" + object + "年</option>";
            });
            $('#' + self.yearId).append(option);
            $('#' + self.yearId).val(self.year);
            self.setMonthFun();
        },
        setMonthFun: function () {
            var self = this;
            var array = new Array();
            for (var i = 1; i <= self.maxMonth; i++) {
                array.push(i);
            }
            defaultDatas.partMonth = array;
            if(self.year == dateYear){
            	self.appendMonthFun(defaultDatas.partMonth);
            } else {
            	self.appendMonthFun(defaultDatas.fullMonth);
            }
            $('#' + self.monthId).val(self.month);
            self.selectChangeFun();
        },
        selectChangeFun: function () {
            var self = this;
            $('#' + self.yearId).change(function () {
                var option = $('#' + self.yearId + ' option:selected').attr('value');
                self.month = $('#' + self.monthId + ' option:selected').attr('value');
//    			self.month = '1';
                if (option == '') {
                    self.appendMonthFun('', true);
                    self.month = '';
                } else if (option == dateYear) {
                    self.appendMonthFun(defaultDatas.partMonth);
                    self.year = dateYear;
                } else {
                    self.appendMonthFun(defaultDatas.fullMonth);
                    self.year = option;
                }
            });
        },
        appendMonthFun: function (data, flag) {
            var self = this;
            $('#' + self.monthId).empty();
            var option = '';
            if (flag) {
                /*option += "<option value=''>请选择</option>";*/
                $('#' + self.monthId).append(option);
            } else {
                /*option += "<option value=''>请选择</option>";*/
            	var num = 0;
                $.each(data, function (ind, obj) {
                	if(obj >= self.month){
                		num++;
                	}
                    option += "<option value= " + obj + ">" + obj + "月</option>";
                });
                $('#' + self.monthId).append(option);
                if(num > 0){
                	$('#' + self.monthId).val(self.month);
                } else {
                	$('#' + self.monthId).val('1');
                	self.month = 1;
                }
                $('#' + self.monthId).change(function () {
                    self.month = $('#' + self.monthId + ' option:selected').attr('value');
                });
            }
            self.okBtnClickFun();
        },
        okBtnClickFun: function () {
            var self = this;
            $('#' + self.okId).unbind('click').bind('click', function () {
                self.date = self.year + '' + self.month;
                onePersonDetailCheckWorkTypeObj.organId = self.organId;
                onePersonDetailCheckWorkTypeObj.empId = self.empId;
                onePersonDetailCheckWorkTypeObj.date = self.date;
                onePersonDetailCheckWorkTypeObj.init();
            });
        }
    }
    /**
     * 个人出勤明细-考勤类型
     * */
    var onePersonDetailCheckWorkTypeObj = {
        organId: null,
        empId: null,
        date: null,
        init: function () {
            var self = this;
            if (self.date == null) {
                self.date = dateYear + '' + dateMonth;
            }
            self.getRequestDataFun();
        },
        getRequestDataFun: function () {
            var self = this;
            $.ajax({
                url: urls.queryOnePersonDetailCheckWorkTypeUrl,
                type: 'post',
                success: function (data) {
                    self.loadGridFun(data);
                },
                error: function () {
                }
            });
        },
        loadGridFun: function (data) {
            var self = this;
            var modelArray = data.colModelArray;
            modelArray[0].formatter = changeDateFormatter;
            for (var i = 4; i < modelArray.length; i++) {
                modelArray[i].formatter = changeNullFormatter;
            }
            onePersonDetailGridObj.colModelArray = modelArray;
            onePersonDetailGridObj.checkWorkTypeNameArray = data.nameArray;
            onePersonDetailGridObj.organId = self.organId;
            onePersonDetailGridObj.date = self.date;
            onePersonDetailGridObj.empId = self.empId;
            onePersonDetailGridObj.init();
        }
    }
    /**
     * 个人出勤明细grid
     * */
    var onePersonDetailGridObj = {
        gridId: 'onePersonDetailGrid',
        pagerId: 'onePersonDetailGridPager',
        tabId: 'onePersonDetailTab',
        checkWorkTypeNameArray: null,
        colModelArray: null,
        organId: null,
        date: null,
        empId: null,
        isTrue: false,
        init: function () {
            var self = this;
            self.initGridFun();
        },
        initGridFun: function () {
            var self = this;
            var param = {
                organId: self.organId,
                empId: self.empId,
                date: self.date
            };
            if (self.isTrue) {
                self.reloadGridFun(param);
            }
            $('#' + self.gridId).jqGrid({
                url: urls.queryOnePersonDetailUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
                altRows: true,
                shrinkToFit: false,
                height: 353,
                colNames: self.checkWorkTypeNameArray,
                colModel: self.colModelArray,
                viewrecords: true,
                rowNum: 10,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                rowList: [10, 20, 30],
                pager: '#' + self.pagerId
            });
//            $('#' + self.gridId).setGridHeight($('#' + self.tabId).height() * 0.77);
            self.isTrue = true;
        },
        reloadGridFun: function (param) {
            var self = this;
            $('#' + self.gridId).clearGridData().setGridParam({
                postData: param
            }).trigger("reloadGrid");
        }
    }
    //日期列
    var changeDateFormatter = function (cellvalue, options, rowObject) {
        return Number(cellvalue.split('-')[1]) + '月' + Number(cellvalue.split('-')[2]) + '日';
    };

    /** 数据审核状态 */
    var auditingStateObj = {
        auditingState: "#auditingState",
        init: function () {
            var self = this;
            self.generate();
        },
        generate: function () {
            var self = this;
            var param = {status: 0};
            $(self.auditingState).html('');
            $.ajax({
                url: urls.queryAuditingInfoUrl,
                type: "post",
                data: param,
                success: function (data) {
                    stepOption.initElement(data, self.auditingState);
                },
                error: function () {
                }
            });
        }
    };

    /** 数据审核状态图 */
    var stepOption = {
        initElement: function (data, selector) {
            $.each(data, function (i, obj) {
                var year = obj.createTime.substr(0, 4);
                var month = obj.createTime.substr(5, 2);
                var day = obj.createTime.substr(8, 2);
                var importTime = year + '.' + month + '.' + day;
                var arr = [];
                arr.push('	<div class="step-div">');
                arr.push('		<div class="step-div1">');
                arr.push('			<span class="step-span1">' + year + '年' + month + '月-数据审核</span>');
                arr.push('		</div>');
                arr.push('		<div class="step-div2">');
                arr.push('			<ol class="ui-step step-one">');
                arr.push('				<li class="step-start">');
                arr.push('					<div class="ui-step-line"></div>');
                arr.push('					<div class="ui-step-cont">');
                arr.push('						<span class="ui-step-cont-circle"></span>');
                arr.push('						<span class="ui-step-cont-text">数据导入</span>');
                arr.push('						<span class="ui-step-cont-time">' + obj.empName + '&nbsp;&nbsp;' + importTime + '</span>');
                arr.push('					</div>');
                arr.push('				</li>');
                arr.push('				<li class="step-end">');
                arr.push('					<div class="ui-step-cont">');
                arr.push('						<span class="ui-step-cont-circle"></span>');
                arr.push('						<span class="ui-step-cont-text">数据审核</span>');
                arr.push('						<span class="ui-step-cont-time"></span>');
                arr.push('					</div>');
                arr.push('				</li>');
                arr.push('			</ol>');
                arr.push('		</div>');
                arr.push('		<div class="step-div3">');
                arr.push('			<div class="botton-div">数据审核</div>');
                arr.push('		</div>');
                arr.push('	</div>');
                if ($(selector).find(".step-div").length != 0) {
                    $(selector).find(".step-div").last().after(arr.join(''));
                } else {
                    $(selector).html(arr.join(''));
                }
                $(selector).find(".botton-div").unbind('click').bind('click', function () {
                    window.location.href = urls.toAuditingViewUrl + '?empName=' + obj.empName + '&createTime=' + importTime + '&attId=' + obj.attId;
                });
            });
        }
    };

    //选择审核人
    var addTalentObj = {
        id: "txtKey",
        init: function () {
            var self = this;
            self.select();
        },
        select: function () {
            var self = this;

            $("#" + self.id).select2({
                language: {
                    errorLoading: function () {
                        return "无法载入结果。"
                    },
                    inputTooLong: function (e) {
                        var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
                        return n
                    },
                    inputTooShort: function (e) {
                        var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
                        return n
                    },
                    loadingMore: function () {
                        return "载入更多结果…"
                    },
                    maximumSelected: function (e) {
                        var t = "最多只能选择" + e.maximum + "个项目";
                        return t
                    },
                    noResults: function () {
                        return "未找到结果"
                    },
                    searching: function () {
                        return "搜索中…"
                    }
                },
                width: '100%',
                allowClear: true,
                multiple: false,
                openOnEnter: true,
                placeholder: "输入员工姓名",
                minimumInputLength: 1,
                ajax: {
                    url: urls.checkEmpInfoUrl,
                    dataType: 'json',
                    delay: 500,
                    type: "POST",
                    data: function (params) {
                        var ps = {
                            keyName: params && params.term ? params.term : "",
                            /* page: params.page == null ? 1 : params.page,
                             rows: 30*/
                        };
                        return ps;
                    },
                    processResults: function (data) {
                        var lists = [];
                        $.each(data, function (i, item) {
                            lists.push({id: item.empId, text: item.userName});
                        });
                        return {
                            results: lists,
                            /*pagination: {
                             more: data.total > data.page
                             }*/
                        };
                    }
                }
            }).val(null);
        }
    };
    addTalentObj.init();
    
    var downloadTempletObj = {
        linkId: ".hyperlink-style",
        init: function () {
            this.downloadTempletFun();
        },
        downloadTempletFun: function () {
            $(this.linkId).unbind('click').bind('click', function () {
                window.location.href = urls.downloadTempletExcelUrl;
            });
        }
    };

    var importTimeObj = {
        init: function () {
            var self = this;
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var timeArr = [];
            var monthArr = [];
            var flag = 12 - month;
            var temp = month;
            for (var i = 0; i < 12; i++) {
                if (i < month) {
                    timeArr[i] = year + "年" + (temp) + "月";
                    monthArr[i] = temp--;
                } else {
                    timeArr[i] = (year - 1) + "年" + (12 - i + month) + "月";
                    monthArr[i] = 12 - i + month;
                }

            }
            self.generateFun(timeArr, monthArr, month);
        },
        generateFun: function (timeArr, monthArr, month) {
            var arr = [];
            for (var i = 0; i < timeArr.length; i++) {
                if (timeArr[i] == month) {
                    arr.push('<option selected="selected" value="' + monthArr[i] + '">' + timeArr[i] + '</option>');
                } else {
                    arr.push('<option value="' + monthArr[i] + '">' + timeArr[i] + '</option>');
                }
            }
            $("#attendanceTime").append(arr.join(""));
        }
    };

    var importObj = {
        btnId: "#btn-form",
        btnImportText: "#btnImportText",
        btnNowImportText: "#btnNowImportText",
        importState: "#importState",
        continueImport: "#continueImport",
        cancelImport: "#cancelImport",
        init: function () {
            var self = this;
            self.importFun();
            self.continueImportFun();
            self.chooseFileFun();
            self.cancelImportFun();
        },
        chooseFileFun: function () {
            var self = this;
            $("#attendanceFile").change(function () {
                self.btnImportTextShowFun();
                self.removeBtnDisabledStyleFun();
            });
        },
        importFun: function () {
            var self = this;
            $(self.btnId).unbind('click').bind('click', function () {
                if (!self.empKeyIsnullFun()) {
                    return;
                }
                var month = $("#attendanceTime option:selected").text();
                var examineId = $("#txtKey").val();
                self.btnNowImportTextShowFun();
                self.addBtnDisabledStyleFun();
                var param = {month: month, examineId: examineId, type: 1};
                $('#importForm').ajaxSubmit({
                    url: urls.importLaborEfficiencyDataUrl,
                    type: 'post',
                    data: param,
                    success: function (data) {
                        self.hideImportStateTextFun();
                        if (data.checkPermiss != undefined && data.checkPermiss.length > 0) {
                            self.alertModelFun(data.checkPermiss);
                            self.removeBtnDisabledStyleFun();
                            self.btnImportTextShowFun();
                            return true;
                        } else if (data.fileError != undefined && data.fileError.length > 0) {
                            self.resetImportFormFun();
                            self.removeBtnDisabledStyleFun();
                            self.btnImportTextShowFun();
                            $("#templateInfo").show();
                            self.alertModelFun(data.fileError);
                            return true;
                        } else if (data.errorList != undefined && data.errorList.length > 0) {
                            formalErrorGridObj.init(data.errorList);
                            self.removeBtnDisabledStyleFun();
                            self.btnImportTextShowFun();
                            $("#importError").show();
                            $("#continueImportBtn").hide();
                        } else if (data.info != undefined && data.info.length > 0) {
                            updateTipsGridObj.init(data.info);
                            self.addBtnDisabledStyleFun();
                            $("#importInfo").show();
                            $("#importYearMonth").html(month);
                            $("#continueImportBtn").show();
                        } else if (data.seccess != undefined && data.seccess.length > 0) {
                            self.resetImportFormFun();
                            self.removeBtnDisabledStyleFun();
                            self.btnImportTextShowFun();
                            $("#importSuccess").show();
                        }
                        self.importStateShowFun();
                    },
                    error: function (XmlHttpRequest, errorThrown) {
                        self.resetImportFormFun();
                        self.removeBtnDisabledStyleFun();
                        self.btnImportTextShowFun();
                        self.alertModelFun("请求后台超时.");
                    }
                });
            });
        },
        continueImportFun: function () {
            var self = this;
            $(self.continueImport).unbind('click').bind('click', function () {
                if (!self.empKeyIsnullFun()) {
                    return;
                }
                var month = $("#attendanceTime option:selected").text();
                self.btnNowImportTextShowFun();
                var param = {month: month, type: 2};
                $('#importForm').ajaxSubmit({
                    url: urls.importLaborEfficiencyDataUrl,
                    type: 'post',
                    data: param,
                    success: function (data) {
                        self.hideImportStateTextFun();
                        if (data.errorList != undefined && data.errorList.length > 0) {
                            formalErrorGridObj.init(data.errorList);
                            self.removeBtnDisabledStyleFun();
                            self.btnImportTextShowFun();
                            $("#importError").show();
                            $("#continueImportBtn").hide();
                        } else if (data.info != undefined && data.info.length > 0) {
                            updateTipsGridObj.init(data.info);
                            self.addBtnDisabledStyleFun();
                            $("#importInfo").show();
                            $("#importYearMonth").html(month);
                            $("#continueImportBtn").show();
                        } else if (data.success != undefined && data.success.length > 0) {
                            self.resetImportFormFun();
                            self.removeBtnDisabledStyleFun();
                            self.btnImportTextShowFun();
                            $("#importSuccess").show();
                        }
                        self.importStateShowFun();
                    },
                    error: function (XmlHttpRequest, errorThrown) {
                        self.resetImportFormFun();
                        self.removeBtnDisabledStyleFun();
                        self.btnImportTextShowFun();
                        self.alertModelFun("请求后台超时.");
                    }
                });
            });
        },
        empKeyIsnullFun: function () {
            var self = this;
            var empKey = $("#txtKey").val();
            if (empKey == undefined || empKey == "") {
                self.alertModelFun("审核人不能为空！！！");
                return false;
            }
            return true;
        },
        btnImportTextShowFun: function () {
            var self = this;
            $(self.btnImportText).show();
            $(self.btnNowImportText).hide();
        },
        btnNowImportTextShowFun: function () {
            var self = this;
            $(self.btnImportText).hide();
            $(self.btnNowImportText).show();
        },
        importStateShowFun: function () {
            $(this.importState).show();
        },
        alertModelFun: function (data) {
            $('#importDataTemplate').modal('show');
            $('#importDataTemplate').on('shown.bs.modal', function () {
                $('#importFileSizeError').html(data);
            });
        },
        resetImportFormFun: function () {
            $('#importForm').resetForm();
        },
        addBtnDisabledStyleFun: function () {
            $('#btn-form').attr('disabled', 'disabled');
        },
        removeBtnDisabledStyleFun: function () {
            $('#btn-form').removeAttr('disabled');
        },
        hideImportStateTextFun: function () {
            $("#templateInfo").hide();
            $("#importError").hide();
            $("#importInfo").hide();
            $("#importSuccess").hide();
        },
        cancelImportFun: function () {
            var self = this;
            $(self.cancelImport).unbind('click').bind('click', function () {
                self.resetImportFormFun();
                self.removeBtnDisabledStyleFun();
                self.btnImportTextShowFun();
                self.hideImportStateTextFun();
            });
        }
    };

    /** 格式错误信息 */
    var formalErrorGridObj = {
        gridId: '#formalErrorGrid',
        numId: '#importErrorNum',
        init: function (errorData) {
            var self = this;
            self.appendGrid(errorData);
        },
        appendGrid: function (errorData) {
            var self = this;
            $('.importErrorTr').remove();
            var tr = '';
            $.each(errorData, function (index, object) {
                tr += '<tr class="importErrorTr"><td>' + (index + 1) + '</td><td>'
                    + object.locationError + '</td><td class="importErrorTd">'
                    + object.errorMsg + '</td></tr>';
            });
            $(self.numId).html(errorData.length);
            $(self.gridId).html(tr);
        }
    };

    /***
     * 更新提示
     */
    var updateTipsGridObj = {
        gridId: '#updateTipsGrid',
        init: function (infoData) {
            var self = this;
            self.appendGrid(infoData);
        },
        appendGrid: function (infoData) {
            var self = this;
            $('.importInfoTr').remove();
            var tr = "";
            $.each(infoData, function (index, object) {
                tr += "<tr class='importInfoTr'><td>" + (index + 1) + "</td><td>"
                    + object.userKey + "</td><td class='importInfoTd'>"
                    + object.date + "</td></tr>";
            });
            $(self.gridId).append(tr);
        }
    };

    var searchTextObj = {
        init: function () {
            var availableTags = ["ActionScript", "AppleScript", "Asp", "BASIC", "C", "C++", "Clojure", "COBOL", "ColdFusion", "Erlang", "Fortran",
                "Groovy", "Haskell", "Java", "JavaScript", "Lisp", "Perl", "PHP", "Python", "Ruby", "Scala", "Scheme"];
            $("#empId").autocomplete({
                source: availableTags
            });
        }
    }

    /**无数据时显示 暂无数据*/
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

    /** 数据加载中 */
    var showLoadingChart = function (echartObj) {
        var zr = echartObj.getZrender();
        zr.addShape(new TextShape({
            style: {
                x: (echartObj.dom.clientWidth - 56) / 2,
                y: (echartObj.dom.clientHeight - 20) / 2,
                color: '#ccc',
                text: '数据加载中...',
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
        if (xAxisLabel.length > 6) {
            option.xAxis[0].axisLabel.rotate = 30;
        } else {
            option.xAxis[0].axisLabel.rotate = 0;
        }
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

    function formatPieData(key, name, value) {
        if (!value) {
            return {};
        }
        //页面展示为 ：name，value
        return {
            value: value,
            key: key,
            name: (name + '\n' + Tc.formatNumber(value))
        };
    }

    var pageOneObj = {
        init: function (organId) {
            var self = this;
            self.organId = organId;
            timecrowdObj.init(organId);
            laborRatioObj.initData(organId);
            laborEfficiencyTrendObj.initData(organId);
            laborEfficiencyCountObj.init(organId);
            overtimeHoursCountObj.init(organId);
            overtimeWarningCountObj.init(organId);
            overtimeAvgHoursObj.init(organId);
            overtimeTotalHoursObj.init(organId);
            chekWorkTypeDateObj.init(organId);
            checkWorkTypeObj.init(organId);
            overtimeAvgObj.initData(organId);
            overtimeTotalObj.initData(organId);
        }
    }

    var pageTwoObj = {
        init: function (organId, flag) {
            var self = this;
            self.organId = organId;
            if (flag) {
                $('#searchUserNameTxt').val('');
                attendanceDetailCheckWorkTypeObj.userName = null;
                attendanceDetailCheckWorkTypeObj.date = dateYear + '' + dateMonth;
            }
//            attendanceDetailSearchObj.init(organId);
            attendanceDetailCheckWorkTypeObj.init(organId);
        }
    }

    var pageThreeObj = {
        init: function () {
            auditingStateObj.init();
            downloadTempletObj.init();
            importObj.init();
            searchTextObj.init();
            importTimeObj.init();
        }
    }

    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData(targetAreaId, flag) {
        var selOrganId = reqOrgId;
        if (pageType == "1") {
            var _page = "page-three";
            $(".rightBodyPage").hide();
            $("#" + _page).show();
            $(".leftListDiv").removeClass("selectList");
            $("[page='" + _page + "']").addClass("selectList");
            pageThreeObj.init();
            pageType = "0";
        } else {
            if (targetAreaId == 'page-two') {
                pageTwoObj.init(selOrganId, flag);
            } else if (targetAreaId == 'page-three') {
                pageThreeObj.init();
            } else {
                timeLineObj.init(selOrganId);
                pageOneObj.init(selOrganId);
            }
        }
    }

    changeData('page-one');

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
            changeData(_page);
        }
    });

    /**
     显示 tableView chartView
     */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart") {
            $(this).parents(".SetUpBody").find(".table-view").show();
            $(this).parents(".SetUpBody").find(".chart-view").hide();
            $(this).parents(".SetUpBody").attr("view", "table");
            overtimeTotalHoursObj.chart.resize();
            laborEfficiencyTrendObj.resizeBasisChart();
            laborRatioObj.resizeGrid();
            overtimeTotalObj.resizeChart();
        } else {
            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
            laborEfficiencyTrendObj.resizeRatioChart();
            laborRatioObj.resizeChart();
            overtimeAvgObj.resizeChart();
            overtimeAvgHoursObj.chart.resize();
        }
    });

    $(window).resize(function () {
        var _page = $('.leftListDiv.selectList').attr('page');
        if (_page == 'page-two') {

        } else {
            changeChartsResize();
        }
    });

    function changeChartsResize() {
        laborRatioObj.resizeChart();
        laborRatioObj.resizeGrid();
        laborEfficiencyTrendObj.resizeRatioChart();
        laborEfficiencyTrendObj.resizeBasisChart();
        overtimeAvgObj.resizeChart();
        overtimeTotalObj.resizeChart();
        overtimeTotalHoursObj.chart.resize();
        overtimeAvgHoursObj.chart.resize();
    };

    $(".nav-div-text").click(function () {
        var $this = $(this);
        if ($this.hasClass("nav-text-selected")) return;
        $this.parents(".nav-div").find(".nav-div-text").removeClass("nav-text-selected");
        $this.addClass("nav-text-selected");
        if ($("#dataImport").hasClass("nav-text-selected")) {
            $("#dataImport-view").show();
            $("#dataAuditing-view").hide();
        } else {
            $("#dataImport-view").hide();
            $("#dataAuditing-view").show();
        }
    });

    $("#toAuditingHistoryView").click(function () {
        window.location.href = urls.toAuditingHistoryUrl;
    });
});