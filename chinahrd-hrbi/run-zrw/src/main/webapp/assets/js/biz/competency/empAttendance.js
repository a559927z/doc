require(['jquery', 'moment', 'echarts', 'echarts/chart/bar', 'bootstrap', 'underscore', 'utils', 'clndr', 'messenger', 'datetimepicker'], function ($, moment, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgTxt = win.currOrganTxt;

    $("[data-toggle='tooltip']").tooltip({html: true, trigger: 'click'});
    var arr = [];
    arr.push('<ul class="tooltip-ul">');
    arr.push('<li>正常工时：上午（9:00-12:00）3小时<br/>');
    arr.push('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下午（13:30-18:00）4.5小时</li>');
    arr.push('<li>如果你有可调休或年假时间可先选择调休或年假。其中病假扣除50%工资，其它不扣工资。</li>');
    arr.push('</ul>');
    $('#cardInfo').attr("data-original-title", function () {
        return arr.join('');
    });
    $('#cardInfoB').attr("data-original-title", function () {
        return arr.join('');
    });
    var warnDay = parseInt($('#warnDay').val());
    var beginYear = $('#beginYear').val();
    var this_organId = $('#organId').val();

    var urls = {
        getCheckTypUrl: webRoot + '/empAttendance/getCheckTyp.do',    // 考勤类型
        getAttendanceRecordUrl: webRoot + '/empAttendance/getAttendanceRecord.do',    // 统计员工考勤信息
        getAttendanceByEmpIdUrl: webRoot + '/empAttendance/getAttendanceByEmpId.do',    // 查询员工考勤信息
        getSoureItemUrl: webRoot + '/empAttendance/getSoureItem.do',    // 异常类型
        updateEmpAttendanceUrl: webRoot + '/empAttendance/updateEmpAttendance.do',    // 更新考勤信息
        getEmpAttendanceUrl: webRoot + '/empAttendance/getEmpAttendance.do',
        getAttendanceUrl: webRoot + '/empAttendance/getAttendance.do',
        getAttendanceFromCardUrl: webRoot + '/empAttendance/getAttendanceFromCard.do',   // 查询正常打卡记录
        getWeatherUrl: webRoot + '/empAttendance/getWeather.do',   // 请求天气预报
        queryEmpOtherDayUrl: webRoot + '/empAttendance/queryEmpOtherDay.do',   // 历史休假情况
        queryEmpOtherDayYearUrl: webRoot + '/empAttendance/queryEmpOtherDayYear.do',   // 历史休假情况
    };

    // 获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        reqOrgTxt = organTxt;
    };

    /**
     * 遮罩提示层展示
     *
     * @type {{init: shareObj.init}}
     */
    var shareObj = {
        isFisrt: 1,
        bool: false,
        oneId: '#tooltipOne',
        twoId: '#tooltipTwo',
        threeId: '#tooltipThree',
        fourId: '#tooltipFour',
        fiveId: '#tooltipFive',
        init: function () {
            var self = this;
            self.isFisrt = $('#isFisrt').val();

            if (self.isFisrt == 0) return;
            self.initOne();
            $(window).keydown(function (e) {
                if (e.keyCode == 13) {
                    if ($(self.oneId).css('display') != 'none') {
                        self.initTwo();
                        $(self.oneId).hide();
                    } else if ($(self.twoId).css('display') != 'none') {
                        self.initThree();
                        $(self.twoId).hide();
                    } else if ($(self.threeId).css('display') != 'none') {
                        $(self.threeId).hide();
                    } else if ($(self.fourId).css('display') != 'none') {
                        self.initFive();
                        $(self.fourId).hide();
                    } else if ($(self.fiveId).css('display') != 'none') {
                        $(self.fiveId).hide();
                    }
                }
            });
        },
        initOne: function () {
            var self = this, $one = $(self.oneId), $oneBtn = $one.children('.tooltipOneBtn');
            var top = document.body.scrollHeight / 2 - 108, left = document.body.scrollWidth / 2 - 370;
            $one.css({
                backgroundPositionX: left,
                backgroundPositionY: top,
                width: document.body.scrollWidth,
                height: document.body.scrollHeight
            });
            $one.show();

            var btnTop = top + 129, btnLeft = left + 296;

            $oneBtn.css({top: btnTop, left: btnLeft});

            $oneBtn.unbind('click').click(function () {
                self.initTwo();
                $one.hide();
            });
        },
        initTwo: function () {
            var self = this, $infoRight = $('#attendanceInfoRight'), offset = $infoRight.offset();
            var $two = $(self.twoId), $bg = $two.children('.tooltip-two-bg');
            $two.children('.shade-patch').remove();
            var top = offset.top - 140, left = offset.left - 315;
            self.renderPatch($two, $bg, top, left);

            $two.unbind('click').click(function () {
                self.initThree();
                $two.hide();
            });
        },
        initThree: function () {
            var self = this, $remainder = $('#remainderBlock'), offset = $remainder.offset();
            var $three = $(self.threeId), $bg = $three.children('.tooltip-three-bg');
            $three.children('.shade-patch').remove();
            var top = offset.top - 166, left = offset.left - 586;
            self.renderPatch($three, $bg, top, left);

            $three.find('.tooltipThreeBtn').unbind('click').click(function () {
                $three.hide();
            });
        },
        initFour: function () {
            var self = this, $multiday = $('#multiday-mixed-performance'), offset = $multiday.offset();
            if (self.isFisrt == 0 || self.bool) return;
            var $four = $(self.fourId), $bg = $four.children('.tooltip-four-bg');
            $four.children('.shade-patch').remove();
            var top = offset.top - 25, left = offset.left - 25;
            self.renderPatch($four, $bg, top, left);

            $four.find('.tooltipFourBtn').unbind('click').click(function () {
                self.initFive();
                $four.hide();
                self.bool = true;
            });
        },
        initFive: function () {
            var self = this, isSZ = $('#isSZ').val(), $instruction = $('#instruction'), offset = $instruction.offset();
            if (isSZ == 0) return;
            var $five = $(self.fiveId), $bg = $five.children('.tooltip-five-bg');
            $five.children('.shade-patch').remove();
            var top = offset.top - 15, left = offset.left - 245;
            self.renderPatch($five, $bg, top, left);

            $five.find('.tooltipFiveBtn').unbind('click').click(function () {
                $five.hide();
            });
        },
        renderPatch: function (_obj, _bg, top, left) {
            var winHeight = document.body.scrollHeight, winWidth = document.body.scrollWidth, bgWidth = _bg.width(),
                bgHeight = _bg.height();
            _bg.css({left: left, top: top});

            if (top > 0) {
                var topPatch = $('<div class="shade-patch"></div>');
                topPatch.css({
                    width: winWidth,
                    height: top,
                    left: 0,
                    top: 0
                });
                _obj.append(topPatch);
            }
            var bottomHeight = winHeight - (bgHeight + top);
            if (bgHeight + top < winHeight) {
                var bottom = document.documentElement.scrollHeight > document.documentElement.clientHeight ? -19 : 0;
                var bottomPatch = $('<div class="shade-patch"></div>');
                bottomPatch.css({
                    width: winWidth,
                    height: bottomHeight,
                    left: 0,
                    bottom: bottom
                });
                _obj.append(bottomPatch);
            }
            if (left > 0) {
                var leftPatch = $('<div class="shade-patch"></div>');
                leftPatch.css({
                    width: left,
                    height: winHeight - (bottomHeight + top),
                    left: 0,
                    top: top
                });
                _obj.append(leftPatch);
            }
            if (bgWidth + left < winWidth) {
                var rightPatch = $('<div class="shade-patch"></div>');
                rightPatch.css({
                    width: winWidth - (bgWidth + left),
                    height: winHeight - (bottomHeight + top),
                    right: 0,
                    top: top
                });
                _obj.append(rightPatch);
            }
            _obj.show();
        },
        resize: function () {
            var self = this;
            if ($(self.oneId).css('display') != 'none') {
                self.initOne();
            } else if ($(self.twoId).css('display') != 'none') {
                self.initTwo();
            } else if ($(self.threeId).css('display') != 'none') {
                self.initThree();
            } else if ($(self.fourId).css('display') != 'none') {
                self.initFour();
            } else if ($(self.fiveId).css('display') != 'none') {
                self.initFive();
            }
        }
    }
    shareObj.init();

    $(window).resize(function () {
        shareObj.resize();
    });


    var sex = $('#sex').val();
    var imgClass = $('#nav_attendance_img').attr('class');
    $('#nav_attendance_img').removeClass(imgClass);
    if (sex == 'm') {
        $('#nav_attendance_img').addClass('nav-attendance-img-m');
    } else {
        $('#nav_attendance_img').addClass('nav-attendance-img-w');
    }

    /** 请求天气预报 */
    var getWeather = {
        init: function () {
            $('.remind-left').hide();
            $('.margin-lr20').hide();
            $('.remind-right').hide();
            this.request();
        },
        request: function () {
            $.post(urls.getWeatherUrl, function (rs) {
                if (undefined != rs && null != rs && "" != rs) {
                    $('.remind-left').show();
                    $('.margin-lr20').show();
                    $('.remind-right').show();
                    $('#weatherInfo').html(rs.date + '，' + rs.weekDay + '，' + rs.cond);
                    $('#weatherDtoTmpMax').html(rs.tmpMax);
                    $('#weatherDtoTmpMin').html(rs.tmpMin);
                    $('#weatherDtoSuggest').html(rs.suggest);
                    $('.weather-icon').css("background-image", "url(" + webRoot + "/assets/img/icon/" + rs.condCode + ".png)");
                }
            });
        }
    }
// getWeather.init();

    var miniteArr = [0, 15, 30, 45]
    // 考勤类型
    var checkTypeObj = {
        checkType: [],
        overtimeId: '',
        normalId: '',
        init: function () {
            this.requestData();
        },
        requestData: function () {
            var self = this;
            $.post(urls.getCheckTypUrl, function (rs) {
                self.checkType = rs;
                $.each(rs, function (i, o) {
                    if (o.typeName == '加班')
                        self.overtimeId = o.typeId;
                    if (o.typeName == '正常出勤')
                        self.normalId = o.typeId;
                })
            });
        }
    };
    checkTypeObj.init();
    /** 考勤记录 */
    var attendanceObj = {
        attdance: [],
        attdanceDay: {},
        day: '',
        yearMonth: null,
        init: function (day) {
            this.day = day;
            this.request();
        },
        request: function (yearMonth) {
            var self = this;
            if (undefined == yearMonth) {
                yearMonth = self.yearMonth;
            }
            $.post(
                urls.getAttendanceUrl,
                {yearMonth: yearMonth},
                function (rs) {
                    if (undefined != rs) {
                        var all = '', t2 = '';
                        var today = moment().format('YYYY-MM-DD');
                        self.attdance.splice(0, self.attdance.length);
                        $.each(rs, function (i, o) {
                            if (o.entryDay < today) {
                                if (o.id == 0) {
                                    var obj = {
                                        id: o.id,
                                        day: o.entryDay,
                                        typeName: o.typeName,
                                        className: 'normal-contents'
                                    };
                                    self.attdance.push(obj);
                                } else if (o.id == 1) {
                                    var obj = {
                                        id: o.id,
                                        day: o.entryDay,
                                        typeName: o.typeName,
                                        className: 'day-over-time'
                                    };
                                    self.attdance.push(obj);
                                } else if (o.id == 2) {
                                    var obj = {
                                        id: o.id,
                                        day: o.entryDay,
                                        typeName: o.typeName,
                                        className: 'exception-contents'
                                    };
                                    self.attdance.push(obj);
                                    t2 += o.entryDay + ' ';
                                }
                                all += o.entryDay + ' ';
                            }
                        });
                        self.attdanceDay = {alldays: all, t2: t2};
                        attendanceAdjustmentObj.clndrFun(self.day);
                    }
                }
            );
            self.yearMonth = yearMonth;
        }
    };
    attendanceObj.init();

    /** 正常打卡记录 */
    var normalAttendanceObj = {
        normalAttdance: [],
        theory: [],
        allTheory: '',
        batchTheory: '',
        init: function (yearMonth) {
            this.request(yearMonth);
        },
        request: function (yearMonth) {
            var self = this;
            $.post(
                urls.getAttendanceFromCardUrl,
                {yearMonth: yearMonth},
                function (rs) {
                    self.normalAttdance = rs.normal;
                    self.theory = rs.theory;
                    self.allTheory = rs.allTheory.join(' ');
                    self.batchTheory = rs.allTheory;
                }
            );
        }
    };
    normalAttendanceObj.init(-1);
    /** 年假和可调休天数 */
    var vacationObj = {
        total: 0,
        annual: 0,
        init: function () {
            this.request();
        },
        request: function () {
            var self = this;
            $.post(urls.getEmpAttendanceUrl, function (rs) {
                self.total = 0;
                self.annual = 0;
                if (!_.isEmpty(rs) && !_.isUndefined(rs) && !_.isNull(rs) && !_.isNaN(rs)) {
                    if (!_.isNaN(rs.total)) {
                        self.total = rs.total;
                    }
                    if (!_.isNaN(rs.annual)) {
                        self.annual = rs.annual;
                    }
                }
                $('#vacationTotal').text(self.total + self.annual);
                $('#vacationCanLeave').text(self.total);
                $('#vactionAnnual').text(self.annual);
            });
        }
    };
    vacationObj.init();

    /**
     * 员工考勤信息
     */
    var attendanceRecordOption = {
        grid: {
            x: 0,
            y: 35,
            x2: 0,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params) {
                if (params[0].name == "加班(小时)") {
                    return params[0].name + ' : ' + params[0].data.overtime;
                } else {
                    return params[0].name + ' : ' + params[0].value;
                }
            }
        },
        dataZoom: {},
        xAxis: [
            {
                type: 'category',
                // boundaryGap : false,
                splitLine: false,
                axisLine: false,
                axisTick: {
                    show: false,
                },
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: {
                    lineStyle: {
                        color: '#e0e0e0',
                        type: 'dashed'
                    }
                },
                axisLabel: {
                    show: false,
                },
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                }
            }
        ],
        series: [
            {
                type: 'bar',
                stack: 'sum',
// barCategoryGap: '50%',
                barMaxWidth: 50,
                itemStyle: {
                    normal: {
// color: '',
                        color: function (params) {
                            var colorList = [
                                '#e75e3b', '#4a96dc', '#79c104'
                            ];
                            if (params.data.name == "异常")
                                return colorList[0];
                            else if (params.data.name == "正常出勤")
                                return colorList[1];
                            else
                                return colorList[2];
                        },
                        /*
						 * barBorderColor: 'tomato', barBorderWidth: 1,
						 * barBorderRadius:0,
						 */
                        label: {
                            show: true,
                            // position: 'outer',
                            textStyle: {
                                fontSize: 20,
                                fontWeight: 'bold'
                            },
                            formatter: function (params) {
                                if (params.name == "加班(小时)") {
                                    return params.data.overtime;
                                } else {
                                    return params.value;
                                }
                            }
                        }
                    },
                },
                data: []
            },
            // {
            // type: 'bar',
            // stack: 'sum',
            // barMaxWidth: 50,
            // itemStyle: {
            // normal: {
            // color: '#f1f2f7',
            // label: {
            // show: false,
            // /*position: 'top',
            // textStyle: {
            // color: 'tomato'
            // }*/
            // }
            // }
            // },
            // data: []
            // }
        ]
    };
    var attendanceRecordObj = {
        id: 'attendanceBar',
        chart: initEChart('attendanceBar'),
        option: _.clone(attendanceRecordOption),
        init: function () {
            var date = new Date();
            var day = date.getDate();
            var month = date.getMonth();
            var year = date.getFullYear();
            if (day <= warnDay) {
                if (month == 0) {
                    $('#attendanceMonth').html(12);
                    $('.remainder-month').html(12);
                } else {
                    $('#attendanceMonth').html(month);
                    $('.remainder-month').html(month);
                }
                $('.remainder-num').html(warnDay - day);
            } else {
                $('#attendanceMonth').html(month + 1);
                $('.remainder-month').html(month + 1);
                var num = new Date(year, month + 1, 0).getDate();
                $('.remainder-num').html(num - day + warnDay);
            }

            this.requestData();
            this.btnClickFun();
        },
        requestData: function () {
            var self = this;
            $.post(
                urls.getAttendanceRecordUrl,
                function (rs) {
                    if (undefined != rs.query) {
                        self.generate(rs);
                    }
                }
            );
        },
        generate: function (rs) {
            var self = this, xAxisData = [], series1 = [], max = rs.theory.length;
            // var series2 = [];
            $.each(rs.query, function (i, o) {
                if (o.typeName == "加班") {
                    xAxisData.push(o.typeName + "(小时)");
                    var yu = o.total % 7.5;
                    var zheng = parseInt(o.total / 7.5);
                    var su = yu >= 4 ? zheng + 0.5 : zheng;
                    series1.push({name: "加班", value: su, overtime: o.total});
                    // series1.push({name: "加班", value: su, overtime: o.total});
                    // series2.push({name: "加班-", value: rs.theory.length - su,
                    // overtime: rs.theory.length*7.5 - o.total});
                } else {
                    xAxisData.push(o.typeName + "(天)");
                    var yu = o.total % 7.5;
                    var zheng = parseInt(o.total / 7.5);
                    var su = yu >= 4 ? zheng + 0.5 : zheng;
                    series1.push({name: o.typeName, value: su});
                    // series2.push(rs.theory.length - su);
                }
            });
            var dataZoom = {};
            if (rs.query.length > 4) {
                dataZoom = {
                    show: true,
                    realtime: true,
                    height: 15,
                    start: 0,
                    end: 4 / rs.query.length * 100,
                    showDetail: false,
                    zoomLock: true
                };
            }
            self.option.dataZoom = dataZoom;
            self.option.yAxis[0].max = max;
            self.option.xAxis[0].data = xAxisData;
            self.option.series[0].data = series1;
            // self.option.series[1].data = series2;
            clearEChart(self.chart);
            self.chart.setOption(self.option);
            self.resizeChart();
        },
        btnClickFun: function () {
            $('#adjustment').unbind('click').bind('click', function () {
                $('#attendanceAdjustmentModal').modal('show');
                $('#attendanceAdjustmentModal').unbind('shown.bs.modal').on('shown.bs.modal', function () {
                    attendanceAdjustmentObj.init();
                    shareObj.initFour();
                });
                $('#attendanceAdjustmentModal').unbind('hide.bs.modal').on('hide.bs.modal', function () {
                    vacationObj.init();
                    attendanceRecordObj.init();
                    attendanceObj.yearMonth = null;
                    attendanceObj.init();
                });
            });
        },
        resizeChart: function () {
            this.chart.resize();
        }
    }
    attendanceRecordObj.init();

    var allOverTime = 0;
    var allLeaveTime = 0;

    /** 考勤调整 */
    var attendanceAdjustmentObj = {
        option: '',
        optionRs: [],
        multidayMixedPerfArray: [],
        curDate: null,
        flag: true,
        init: function () {
            var self = this;
            $('#instruction').show();
            $('#batchAdjustment').hide();
            $('.batch-submit-remind').hide();
            $('#preDayAdjustment').hide();
            $('#dayAdjustment').hide();
            self.timeSelectRender();
            self.timeSelectChange();
            // 批量调整
            batchToAdjustObj.init();
            // 日历控件
            self.flag = true;
            self.clndrFun();
            // 查询异常考勤类型的
            $.post(
                urls.getSoureItemUrl,
                function (rs) {
                    self.optionRs = rs;
                    var option = '<option value="">请选择</option>';
                    $.each(rs, function (i, obj) {
                        option += '<option value="' + obj.typeId + '">' + obj.typeName + '</option>';
                    });
                    self.option = option;
                    dayToAdjustObj.option = option;
                    batchToAdjustObj.option = option;
                }
            );
        },
        timeSelectRender: function () {
            var now = new Date();
            var nowDay = now.getDate();
            var month = now.getMonth() + 1;
            var year = now.getFullYear();
            var preyear = now.getFullYear();
            if (nowDay <= warnDay) {
                var lastMonth = moment().subtract(1, 'M');
                month = moment(lastMonth).format("MM");
                preyear = moment(lastMonth).format("YYYY");
            }
            var opy = "";
            for (var i = parseInt(beginYear); i <= year; i++) {
                if (i == preyear)
                    opy += "<option value='" + i + "' selected>" + i + "年</option>";
                else
                    opy += "<option value='" + i + "'>" + i + "年</option>";
            }
            $('#selectYear').html(opy);
            var opm = "";
            for (var i = 1; i <= 12; i++) {
                if (i == month) {
                    opm += "<option value='" + (i > 9 ? i : '0' + i) + "' selected>" + i + "月</option>";
                } else {
                    opm += "<option value='" + (i > 9 ? i : '0' + i) + "'>" + i + "月</option>";
                }
            }
            $('#selectMonth').html(opm);
        },
        timeSelectChange: function () {
            var self = this;
            $('.modal-style-margin').find('select').change(function () {
                var curYear = $('#selectYear').val();
                var curMonth = $('#selectMonth').val();
                var curDate = moment(curYear + "-" + curMonth + "-01");
                attendanceObj.request(parseInt(curYear + curMonth));
                self.curDate = curDate;
                self.flag = false;
                var daysInMonth = curDate.daysInMonth();
                self.multidayMixedPerfArray.splice(0, self.multidayMixedPerfArray.length);
                for (var i = 1; i <= daysInMonth; i++) {
                    var padDay = (i < 10) ? '0' + i : i;
                    self.multidayMixedPerfArray.push({
                        endDate: curDate.format('YYYY-MM-') + padDay,
                        startDate: curDate.format('YYYY-MM-') + padDay
                    });
                }
// self.clndrFun();
                $('#contentTitle').html('调整说明');
                $('#instruction').show();
                $('#batchAdjustment').hide();
                $('#preDayAdjustment').hide();
                $('.batch-submit-remind').hide();
                $('#dayAdjustment').hide();
                $('.day').removeClass('clicked');
            });
        },
        clndrFun: function (day) {
            var self = this;
// $('#multiday-mixed-performance').html('');
            var clndr = {};
            // Declare all vars at the top
            var daysInMonth;

            // Test multi-day event performance
            // Start with two truly multiday events.
            var now = new Date();
            var nowDay = now.getDate();
            if (self.flag) {
                if (nowDay <= warnDay) {
                    var month = moment().subtract(1, 'M');
                    var ym = month.format('YYYY-MM-');
                    // 获取天数：
                    daysInMonth = moment(month).daysInMonth();
                    self.multidayMixedPerfArray.splice(0, self.multidayMixedPerfArray.length);
                    for (var i = 1; i <= daysInMonth; i++) {
                        var padDay = (i < 10) ? '0' + i : i;
// for (var j = 0; j < 10; j++) {
                        self.multidayMixedPerfArray.push({
                            endDate: ym + padDay,
                            startDate: ym + padDay
                        });
// }
                    }
                    self.curDate = null;
                } else {
                    // Add ten events every day this month that are only a day
                    // long,
                    // which triggers clndr to use a performance optimization.
                    daysInMonth = moment().daysInMonth();
                    self.multidayMixedPerfArray.splice(0, self.multidayMixedPerfArray.length);
                    for (var i = 1; i <= daysInMonth; i++) {
                        var padDay = (i < 10) ? '0' + i : i;
// for (var j = 0; j < 10; j++) {
                        self.multidayMixedPerfArray.push({
                            endDate: moment().format('YYYY-MM-') + padDay,
                            startDate: moment().format('YYYY-MM-') + padDay
                        });
// }
                    }
                    self.curDate = null;
                }
            }

            var clndrOption = {
                attdance: attendanceObj.attdance,
                attdanceDay: attendanceObj.attdanceDay,
                allTheory: normalAttendanceObj.allTheory,
                events: self.multidayMixedPerfArray,
                month: month,
                curDate: self.curDate,
                warnDay: warnDay,
                multiDayEvents: {
                    singleDay: 'date',
                    endDate: 'endDate',
                    startDate: 'startDate'
                },
                clickEvents: {
                    click: function (target) {
                        var day = target.date._i;
                        var date = new Date();
                        var dateStr = date.getFullYear() + '-' + (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-' + (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
                        if (day < dateStr) {
                            $('.day').removeClass('clicked');
                            $('.calendar-day-' + day).addClass('clicked');
                            $('#contentTitle').html(day.substr(0, 4) + '年' + day.substr(5, 2) + '月' + day.substr(8, 2) + '日');
                            $('#preDayAdjustment').show();
                            $('.batch-submit-remind').hide();
                            $('#instruction').hide();
                            $('#batchAdjustment').hide();
                            $('#dayAdjustment').hide();
                            dayToAdjustObj.init(day);
                        }
                    }
                }
            }
// if(moment().days() <= warnDay){
// clndrOption.startWithMonth = moment().subtract(1,'M')
// }

            clndr.multidayMixedPerformance = $('#multiday-mixed-performance').clndr(clndrOption);
            $('.day').removeClass('clicked');
            $('.calendar-day-' + day).addClass('clicked');
        }
    };
    /** 调整--天 */
    var dayToAdjustObj = {
        day: '',
        option: '',
        dayResult: [],
        init: function (day) {
            this.day = day;
            this.requestDayAttendance();
        },
        requestDayAttendance: function () {
            var self = this;
            $.post(
                urls.getAttendanceByEmpIdUrl,
                {day: self.day},
                function (rs) {
                    if (undefined != rs) {
                        self.generateDayFun(rs);
                        self.dayResult = rs;
                    }
                }
            );
        },
        generateDayFun: function (rs) {
            var self = this;
            var dayBeginTime = _.isNull(rs.clockInAm) ? "无" : rs.clockInAm;
            var dayEndTime = _.isNull(rs.clockOutPm) ? "无" : rs.clockOutPm;
            var adjustBegin = _.isNull(rs.adjustBegin) ? "无" : rs.adjustBegin;
            var adjustEnd = _.isNull(rs.adjustEnd) ? "无" : rs.adjustEnd;

            $('#dayBeginTime').html(dayBeginTime == undefined ? "无" : dayBeginTime);
            $('#dayEndTime').html(dayEndTime == undefined ? "无" : dayEndTime);
            $('#adjustBegin').html(adjustBegin == undefined ? "无" : adjustBegin);
            $('#adjustEnd').html(adjustEnd == undefined ? "无" : adjustEnd);
            $('#excTd').html(rs.reason == undefined ? "-" : rs.reason);
            $('#excTd').html(rs.reason == undefined ? "-" : rs.reason);
            $('#preNote').html(rs.note == undefined ? "" : rs.note);
            // 显示考勤结果
            var attendanceResult = '';
            if (undefined != rs.typeName) {
                if (undefined != rs.calHour) {
                    var typeNameArr = rs.typeName.split(',');
                    var hourArr = rs.calHour.split(',');
                    if (typeNameArr.length > 1) {
                        for (var i = 0; i < typeNameArr.length; i++) {
                            if (typeNameArr[i] != "正常出勤") {
                                if (typeNameArr[i] == "加班") {
                                    if (i == typeNameArr.length - 1)
                                        attendanceResult += typeNameArr[i] + hourArr[i] + "小时";
                                    else
                                        attendanceResult += typeNameArr[i] + hourArr[i] + "小时" + ",";
                                } else {
                                    if (i == typeNameArr.length - 1)
                                        attendanceResult += typeNameArr[i] + (parseFloat(hourArr[i]) >= 7.5 ? 1 : 0.5) + "天";
                                    else
                                        attendanceResult += typeNameArr[i] + (parseFloat(hourArr[i]) >= 7.5 ? 1 : 0.5) + "天" + ",";
                                }
                            }
                        }
                    } else {
                        if (rs.typeName == "正常出勤") {
                            attendanceResult += rs.typeName;
                        } else if (rs.typeName == "加班") {
                            attendanceResult += rs.typeName + rs.calHour + "小时";
                        } else {
                            attendanceResult += rs.typeName + rs.calHour + "天";
                        }
                    }
                } else {
                    if (rs.clockInAm == null || rs.clockOutPm == null) {
                        if (rs.clockInAm != null) {
                            var hour = parseInt(rs.clockInAm.substr(0, 2));
                            var minite = parseInt(rs.clockInAm.substr(3, 2));
                            if (hour > 10 || (hour == 10 && minite > 0))
                                attendanceResult = '异常';
                        } else {
                            attendanceResult = '异常';
                        }
                        if (rs.clockOutPm != null) {
                            var hour = parseInt(rs.clockOutPm.substr(0, 2));
                            var minite = parseInt(rs.clockOutPm.substr(3, 2));
                            if (hour < 18)
                                attendanceResult = '异常';
                        } else {
                            attendanceResult = '异常';
                        }
                        if (rs.clockInAm == null && rs.clockOutPm == null) {
                            attendanceResult = '异常';
                        }
                    } else {
                        var hour = parseInt(rs.clockInAm.substr(0, 2));
                        var minite = parseInt(rs.clockInAm.substr(3, 2));
                        var ohour = parseInt(rs.clockOutPm.substr(0, 2));
                        var ominite = parseInt(rs.clockOutPm.substr(3, 2));
                        if (hour > 10 || (hour == 10 && minite > 0) || ohour < 18)
                            attendanceResult = '异常';
                        else
                            attendanceResult = rs.typeName;
                    }
                }
            }
            if (undefined == attendanceResult || attendanceResult == '') {
                if (normalAttendanceObj.allTheory.indexOf(self.day) > -1) {
                    var begin = (undefined != adjustBegin && "无" != adjustBegin) ? adjustBegin : dayBeginTime;
                    if (undefined != begin && "无" != begin) {
                        var hour = parseInt(begin.substr(0, 2));
                        var minite = parseInt(begin.substr(3, 2));
                        if (hour > 10) {
                            attendanceResult = '异常';
                        } else if (hour == 10) {
                            if (minite > 0) {
                                attendanceResult = '异常';
                            }
                        }
                    } else {
                        attendanceResult = '异常';
                    }
                    var end = (undefined != adjustEnd && "无" != adjustEnd) ? adjustEnd : dayEndTime;
                    if (undefined != end && "无" != end) {
                        var hour = parseInt(end.substr(0, 2));
                        var minite = parseInt(end.substr(3, 2));
                        if (hour < 18) {
                            attendanceResult = '异常';
                        }
                    } else {
                        attendanceResult = '异常';
                    }
                } else {
                    attendanceResult = '公休';
                }
            } else {

            }
            $('#attendanceResult').html(attendanceResult);
            // 是否显示异常原因
            var reason = rs.reason;
            if (reason == undefined || reason == '') {
                $('#tr_dayReason').hide();
            } else {
                $('#tr_dayReason').show();
                $('#excTd').html(reason);
            }
            // 是否显示备注
            var note = rs.note;
            if (note == undefined || note == '') {
                $('#tr_dayNote').hide();
            } else {
                $('#tr_dayNote').show();
                $('#preNote').html(note);
            }
            /** 控制调整按钮事件 */
            var d = new Date();
            var dd = d.getDate();
            var mm = d.getMonth() + 1;
            var yy = d.getFullYear();
            if (yy == parseInt(self.day.substr(0, 4))) {
                if (dd > warnDay && parseInt(self.day.substr(8, 2)) < dd && mm == parseInt(self.day.substr(5, 2))) {
                    self.bindAdjustClickFun(dayBeginTime, dayEndTime, adjustBegin, adjustEnd);
                } else if (dd <= warnDay && mm <= parseInt(self.day.substr(5, 2)) + 1) {
                    self.bindAdjustClickFun(dayBeginTime, dayEndTime, adjustBegin, adjustEnd);
                } else {
                    $('#preDayAdjustmentBtn').unbind('click');
                    $('#preDayAdjustmentBtn').hide();
                }
            } else {
                if (dd <= warnDay && yy == parseInt(self.day.substr(0, 4)) + 1 && mm == 1 && parseInt(self.day.substr(5, 2)) == 12) {
                    self.bindAdjustClickFun(dayBeginTime, dayEndTime, adjustBegin, adjustEnd);
                } else {
                    $('#preDayAdjustmentBtn').unbind('click');
                    $('#preDayAdjustmentBtn').hide();
                }
            }
            $('#comeBack').unbind('click').bind('click', function () {
                $('#contentTitle').html('调整说明');
                $('#instruction').show();
                $('#batchAdjustment').hide();
                $('#preDayAdjustment').hide();
                $('.batch-submit-remind').hide();
                $('#dayAdjustment').hide();
                $('.day').removeClass('clicked');
            });
        },
        bindAdjustClickFun: function (dayBeginTime, dayEndTime, adjustBegin, adjustEnd) {
            var self = this;
            $('#preDayAdjustmentBtn').show();
            $('#preDayAdjustmentBtn').unbind('click').bind('click', function () {
                $('#dayAdjustment').show();
                $('#instruction').hide();
                $('#batchAdjustment').hide();
                $('.batch-submit-remind').hide();
                $('#preDayAdjustment').hide();
                $('#dayReason').html(self.option);
                $('#tr_exceptionWarn').hide();
                $('#tr_resultWarn').hide();
                dayAdjustmentObj.flag = false;
                if (normalAttendanceObj.allTheory.indexOf(self.day) != -1) {
                    $('#linkLeave').show();
                } else {
                    $('#linkLeave').hide();
                }
                self.dayAdjustFun(dayBeginTime, dayEndTime, adjustBegin, adjustEnd);
            });
        },
        dayAdjustFun: function (dayBeginTime, dayEndTime, adjustBegin, adjustEnd) {
            var self = this;
            $('#dayBeginTimeAfter').html(dayBeginTime == undefined ? "无" : dayBeginTime);
            $('#dayEndTimeAfter').html(dayEndTime == undefined ? "无" : dayEndTime);
            if (undefined != adjustBegin && "无" != adjustBegin) {
                $('#indayTime').val(adjustBegin);
            } else if (undefined != dayBeginTime && "无" != dayBeginTime) {
                $('#indayTime').val(dayBeginTime);
            } else {
                $('#indayTime').val('');
            }
            if (undefined != adjustEnd && "无" != adjustEnd) {
                $('#outdayTime').val(adjustEnd);
            } else if (undefined != dayEndTime && "无" != dayEndTime) {
                if (dayEndTime >= '18:00') {
                    $('#outdayTime').val('18:00');
                } else {
                    $('#outdayTime').val(dayEndTime);
                }
            } else {
                $('#outdayTime').val('');
            }

            $('#indayTime').datetimepicker({
                format: 'hh:ii',
                autoclose: true,
                minuteStep: 15,
                initialDate: new Date(),
                language: 'cn',
                startView: 1,
                viewSelect: 'hour',
                maxView: 1,
                minView: 0,
            }).on('changeDate', function (ev) {
                var inTime = $('#indayTime').val();
                var outTime = $('#outdayTime').val();
                if ('' != inTime && '' != outTime) {
                    if (inTime > outTime) {
                        showErrMsg("上班时间不能大于下班时间！");
                        return;
                    }
                    self.dayAdjustTimeChange();
                }
            });
            $('#outdayTime').datetimepicker({
                format: 'hh:ii',
                autoclose: true,
                minuteStep: 15,
                initialDate: new Date(),
                language: 'cn',
                startView: 1,
                viewSelect: 'hour',
                maxView: 1,
                minView: 0,
            }).on('changeDate', function (ev) {
                var inTime = $('#indayTime').val();
                var outTime = $('#outdayTime').val();
                if ('' != inTime && '' != outTime) {
                    if (inTime > outTime) {
                        showErrMsg("上班时间不能大于下班时间！");
                        return;
                    }
                    self.dayAdjustTimeChange();
                }
            });

            // 全天请假
            self.bindClickDayLeave();
            // 考勤结果
            var ar = $('#attendanceResult').html();
            if (undefined != self.dayResult.calHour && "" != self.dayResult.calHour) {
                var typeids = self.dayResult.typeId.split(",");
                var calhour = self.dayResult.calHour.split(",");
                if (self.dayResult.typeName.indexOf("正常出勤") != -1 && typeids.length == 1) {
                    $('#dayResult').hide();
                    $('#dayLeaveTime').html('');
                    $('#dayOverTime').html('');
                    $('#dayResultLine').hide();
                    $('#normalDayResult').html('正常出勤');
                } else if (self.dayResult.typeName.indexOf("加班") != -1 && typeids.length <= 2) {
                    $('#dayResult').hide();
                    $('#dayLeaveTime').html('');
                    if (typeids.length == 2) {
                        $('#dayOverTime').html("加班" + calhour[1] + "小时");
                    } else {
                        $('#dayOverTime').html("加班" + calhour[0] + "小时");
                    }
                    $('#dayResultLine').hide();
                    $('#normalDayResult').html('');
                    allOverTime = calhour[1];
                } else {
                    var op = '';
                    var temp = '';
                    $.each(checkTypeObj.checkType, function (i, o) {
                        if ('正常出勤' != o.typeName && '加班' != o.typeName) {
                            if (ar.indexOf(o.typeName) != -1) {
                                op += '<option value="' + o.typeId + '" selected>' + o.typeName + '</option>';
                                temp = ar.replace(o.typeName, '');
                            } else
                                op += '<option value="' + o.typeId + '">' + o.typeName + '</option>';
                        }
                    });
                    $('#dayResult').html(op);
                    $('#dayResult').show();
                    var tempArr = temp.split(",");
                    var tm = '';
                    var tmo = '';
                    for (var i = 0; i < tempArr.length; i++) {
                        if (tempArr[i].indexOf("加班") != -1) {
                            tmo = tempArr[i];
                        } else {
                            tm = tempArr[i];
                        }
                    }
                    $('#dayLeaveTime').html(tm + (tmo != '' ? ',' : ''));
                    if (tm.indexOf("1天") != -1) {
                        allLeaveTime = 7.5;
                    } else if (tm.indexOf("0.5天") != -1) {
                        allLeaveTime = 4;
                    }

                    $('#dayOverTime').html(tmo);
                    allOverTime = tmo.substr(2, tmo.length - 4);
                    $('#dayResultLine').hide();
                    $('#normalDayResult').html('');
                }
            } else {
                $('#dayResult').hide();
                $('#dayLeaveTime').html('');
                $('#dayOverTime').html('');
                if ('' != ar) {
                    $('#dayResultLine').hide();
                    $('#normalDayResult').html(ar);
                } else {
                    $('#dayResultLine').show();
                    $('#normalDayResult').html('');
                }
            }
            $('#dayResult').change(function () {
                var dr = $('#dayResult option:selected').text();
                if (dr == "调休") {
                    $('#tr_resultWarn').show();
                    $('#leaveInLieu').show();
                    $('#annualLeave').hide();
                    $('#overtime_remind').hide();
                    $('#resultday').html(vacationObj.total);
                    var dot = $('#dayOverTime').html();
                    if (dot.indexOf("加班") != -1) {
                        $('#overtime_remind').show();
                    }
                } else if (dr == "年假") {
                    $('#tr_resultWarn').show();
                    $('#leaveInLieu').hide();
                    $('#annualLeave').show();
                    $('#overtime_remind').hide();
                    $('#annualday').html(vacationObj.annual);
                    var dot = $('#dayOverTime').html();
                    if (dot.indexOf("加班") != -1) {
                        $('#overtime_remind').show();
                    }
                } else {
                    $('#tr_resultWarn').hide();
                }
            });
            // 异常原因
            var excTd = $('#excTd').html();
            if ("" == excTd || "-" == excTd) {
                $('#dayReasonLine').show();
                $('#dayReason').hide();
// $('#dayReasonText').html('');
            } else {
                $('#dayReasonLine').hide();
                $('#dayReason').show();
                var oop = '';
                $.each(attendanceAdjustmentObj.optionRs, function (i, obj) {
                    if (excTd == obj.typeName)
                        oop += '<option value="' + obj.typeId + '" selected>' + obj.typeName + '</option>';
                    else
                        oop += '<option value="' + obj.typeId + '">' + obj.typeName + '</option>';
                });
                console.log(oop);
                $('#dayReason').html(oop);
            }
            $('#dayNote').val($('#preNote').html());
            $('#dayReason').change(function () {
                var dr = $('#dayReason option:selected').text();
                if (dr != "请选择" && dr != "") {
                    $('#tr_exceptionWarn').hide();
                } else {
                    $('#tr_exceptionWarn').show();
                }
            });

            dayAdjustmentObj.init(self.day);
            $('#dayCancel').unbind('click').bind('click', function () {
                $('#instruction').hide();
                $('#batchAdjustment').hide();
                $('#preDayAdjustment').show();
                $('.batch-submit-remind').hide();
                $('#dayAdjustment').hide();
            });
        },
        bindClickDayLeave: function () {
            var flag = true;
            $('#linkLeave').unbind('click').bind('click', function () {
                $('#tr_exceptionWarn').hide();
                $('#tr_resultWarn').hide();
                $('#indayTime').val('');
                $('#outdayTime').val('');

                if (flag) {
                    $('#dayResult').show();
                    $('#dayResultLine').hide();
                    $('#dayLeaveTime').html('1天');
                    $('#dayOverTime').html('');
                    $('#normalDayResult').html('');

                    $('#dayReason').hide();
                    $('#dayReasonLine').show();
                    allLeaveTime = 7.5;
                    // 调整时间
                    var inHourOption = '<option value="" selected>-</option>';
                    var outHourOption = '<option value="" selected>-</option>';
                    for (var i = 0; i < 24; i++) {
                        var temp = i;
                        if (i < 10)
                            temp = '0' + i;
                        inHourOption += '<option value="' + temp + '">' + temp + '</option>';
                        outHourOption += '<option value="' + temp + '">' + temp + '</option>';
                    }
                    $('#inHour').html(inHourOption);
                    $('#outHour').html(outHourOption);
                    var inMiniteOption = '<option value="" selected>-</option>';
                    var outMiniteOption = '<option value="" selected>-</option>';
                    $.each(miniteArr, function (i, o) {
                        var temp = o;
                        if (o < 10)
                            temp = '0' + i;
                        inMiniteOption += '<option value="' + temp + '">' + temp + '</option>';
                        outMiniteOption += '<option value="' + temp + '">' + temp + '</option>';
                    });
                    $('#inMinite').html(inMiniteOption);
                    $('#outMinite').html(outMiniteOption);
                    var op = '';
                    $.each(checkTypeObj.checkType, function (i, o) {
                        if ('正常出勤' != o.typeName && '加班' != o.typeName) {
                            if ('事假' == o.typeName)
                                op += '<option value="' + o.typeId + '" selected>' + o.typeName + '</option>';
                            else
                                op += '<option value="' + o.typeId + '">' + o.typeName + '</option>';
                        }
                    });
                    $('#dayResult').html(op);
                    flag = false;
                    dayAdjustmentObj.flag = true;
                } else {
                    $('#dayResult').hide();
                    $('#dayResultLine').show();
                    $('#dayLeaveTime').html('');
                    $('#dayOverTime').html('');
                    $('#normalDayResult').html('');

                    $('#dayReason').hide();
                    $('#dayReasonLine').show();
                    flag = true;
                    dayAdjustmentObj.flag = false;
                }
            });
        },
        dayAdjustTimeChange: function () {
            var self = this;
            $('#tr_exceptionWarn').hide();
            $('#tr_resultWarn').hide();
            if (normalAttendanceObj.allTheory.indexOf(self.day) > -1) {
                self.changeSelectFun();
            } else {  // 周末和节假日调整
                self.overtimeChangeFun();
            }
            var dot = $('#dayOverTime').html();
            if (dot.indexOf("加班") != -1) {
                $('#tr_resultWarn').show();
                $('#leaveInLieu').hide();
                $('#annualLeave').hide();
                $('#overtime_remind').show();
            }
        },
        overtimeChangeFun: function () {
            var self = this;
            var intime = $('#indayTime').val();
            var outtime = $('#outdayTime').val();

            var inHour = intime.substr(0, 2);
            var inMinite = intime.substr(3, 2);
            var outHour = outtime.substr(0, 2);
            var outMinite = outtime.substr(3, 2);

            var overTime = 0;
            if (parseInt(inHour) < 12) {  // 12点前上班
                if (parseInt(outHour) > 13 || (parseInt(outHour) == 13 && parseInt(outMinite) >= 30)) {  // 如果上班时间包含中午休息时间（12：00-13：30）则要减去中午休息时间
                    var hr = (parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1);
                    var mi = (parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite));
                    var mit = mi >= 30 ? mi - 30 : mi + 30;
                    var hrt = mi >= 30 ? hr - 1 : hr - 2;
                    var hort = mit >= 30 ? hrt + 0.5 : hrt;
                    allOverTime = hort;
                    overTime = "加班" + hort + "小时";
                } else if (parseInt(outHour) < 12) {
                    var hr = parseInt(outHour) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1;
                    var mi = parseInt(outHour) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite);
                    var hrt = mi >= 30 ? hr + 0.5 : hr;
                    allOverTime = hrt;
                    overTime = "加班" + hrt + "小时";
                } else {
                    var hr = parseInt(inMinite) > 0 ? 11 - parseInt(inHour) : 12 - parseInt(inHour);
                    var mi = parseInt(inMinite) > 0 ? 60 - parseInt(inMinite) : 0;
                    var hrt = mi >= 30 ? hr + 0.5 : hr;
                    allOverTime = hrt;
                    overTime = "加班" + hrt + "小时";
                }
            } else {  // 12点后上班
                if (parseInt(inMinite) > 13 || (parseInt(inHour) == 13 && parseInt(inMinite) >= 30)) {
                    var hr = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1;
                    var mi = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite);
                    var hrt = mi >= 30 ? hr + 0.5 : hr;
                    allOverTime = hrt;
                    overTime = "加班" + hrt + "小时";
                } else {
                    var hr = parseInt(outMinite) >= 30 ? parseInt(outHour) - 13 : parseInt(outHour) - 14;
                    var mi = parseInt(outMinite) >= 30 ? parseInt(outMinite) - 30 : parseInt(outMinite) + 30;
                    var hrt = mi >= 30 ? hr + 0.5 : hr;
                    allOverTime = hrt;
                    overTime = "加班" + hrt + "小时";
                }
            }
            $('#normalDayResult').html('');
            $('#dayResultLine').hide();
            $('#dayOverTime').html(overTime);
        },
        changeSelectFun: function () {
            var self = this;
            var intime = $('#indayTime').val();
            var outtime = $('#outdayTime').val();

            var inHour = intime.substr(0, 2);
            var inMinite = intime.substr(3, 2);
            var outHour = outtime.substr(0, 2);
            var outMinite = outtime.substr(3, 2);

            var opt = '';
            $.each(checkTypeObj.checkType, function (i, o) {
                if ('正常出勤' != o.typeName && '加班' != o.typeName) {
                    if ('事假' == o.typeName)
                        opt += '<option value="' + o.typeId + '" selected>' + o.typeName + '</option>';
                    else
                        opt += '<option value="' + o.typeId + '">' + o.typeName + '</option>';
                }
            });
            if ((parseInt(inHour) > 10 || (parseInt(inHour) == 10 && parseInt(inMinite) > 0)) && parseInt(outHour) < 18) {
                $('#dayResult').show();
                $('#dayResultLine').hide();
                $('#dayResult').html(opt);
                $('#dayOverTime').html('');
                $('#normalDayResult').html('');

                $('#dayReason').hide();
                $('#dayReasonLine').show();
                var leaveTime = 0;
                if (parseInt(inHour) < 12 && (parseInt(outHour) > 13 || (parseInt(outHour) == 13 && parseInt(outMinite) > 30))) {
                    var hr = parseInt(inHour) - 9 + (parseInt(outMinite) > 0 ? 17 - parseInt(outHour) : 18 - parseInt(outHour));
                    var mi = parseInt(inMinite) + (parseInt(outMinite) > 0 ? 60 - parseInt(outMinite) : 0);
                    var hrt = mi > 60 ? hr + 1.5 : (mi > 30 ? hr + 1 : (mi > 0 ? hr + 0.5 : hr));
                    var hourtoday = hourToDay(hrt);
                    leaveTime = hourtoday + "天";
                    if (hourtoday == 0.5) {
                        allLeaveTime = 4;
                    } else {
                        allLeaveTime = 7.5;
                    }
                } else if (parseInt(inHour) < 12 && (parseInt(outHour) < 13 || (parseInt(outHour) == 13 && parseInt(outMinite) <= 30))) {
                    leaveTime = "1天";
                    allLeaveTime = 7.5;
                } else if (parseInt(inHour) >= 12) {
                    if (parseInt(inHour) > 13 || (parseInt(inHour) == 13 && parseInt(inMinite) >= 30)) {
                        var hr = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1;
                        var mi = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite);
                        var hrt = mi >= 30 ? hr + 0.5 : hr;
                    } else {
                        var hr = parseInt(outMinite) >= 30 ? parseInt(outHour) - 13 : parseInt(outHour) - 13 - 1;
                        var mi = parseInt(outMinite) >= 30 ? parseInt(outMinite) - 30 : parseInt(outMinite) + 30;
                        var hrt = mi >= 30 ? hr + 0.5 : hr;
                    }
                    if (hrt >= 3.5) {
                        leaveTime = "0.5天";
                        allLeaveTime = 4;
                    } else {
                        leaveTime = "1天";
                        allLeaveTime = 7.5;
                    }
                }
                $('#dayLeaveTime').html(leaveTime);
            } else if ((parseInt(inHour) > 10 || (parseInt(inHour) == 10 && parseInt(inMinite) > 0)) && parseInt(outHour) >= 18) {
                $('#dayResult').show();
                $('#dayResultLine').hide();
                $('#dayResult').html(opt);
                $('#normalDayResult').html('');

                $('#dayReason').hide();
                $('#dayReasonLine').show();
                var leaveTime = 0;
                if (parseInt(inHour) < 13 || (parseInt(inHour) == 13 && parseInt(inMinite) <= 30)) {
                    allLeaveTime = 4;
                    leaveTime = "0.5天";
                } else {
                    var hr = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1;
                    var mi = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite);
                    var hrt = mi > 30 ? hr + 0.5 : hr;
                    if (hrt >= 3.5) {
                        leaveTime = "0.5天";
                        allLeaveTime = 4;
                    } else {
                        leaveTime = "1天";
                        allLeaveTime = 7.5;
                    }
                }
                $('#dayLeaveTime').html(leaveTime);
                if (parseInt(outHour) > 18 || (parseInt(outHour) == 18 && parseInt(outMinite) >= 30)) {
                    var hor = parseInt(outHour) - 18;
                    var moi = parseInt(outMinite);
                    var hort = moi >= 30 ? 0.5 + hor : hor;
                    allOverTime = hort;
                    var overTime = "加班" + hort + "小时";
                    $('#dayOverTime').html(overTime);
                }
            } else if ((parseInt(inHour) < 10 || (parseInt(inHour) == 10 && parseInt(inMinite) == 0)) && parseInt(outHour) < 18) {
                $('#dayResult').show();
                $('#dayResultLine').hide();
                $('#dayResult').html(opt);
                $('#dayOverTime').html('');
                $('#normalDayResult').html('');

                $('#dayReason').hide();
                $('#dayReasonLine').show();
                var leaveTime = 0;
                if (parseInt(outHour) >= 12) {
                    leaveTime = "0.5天";
                    allLeaveTime = 4;
                } else {
                    leaveTime = "1天";
                    allLeaveTime = 7.5;
                }
                $('#dayLeaveTime').html(leaveTime);
            } else if ((parseInt(inHour) < 10 || (parseInt(inHour) == 10 && parseInt(inMinite) == 0)) && (parseInt(outHour) > 18 || (parseInt(outHour) == 18 && parseInt(outMinite) >= 30))) {
                $('#dayResultLine').hide();
                $('#dayResult').hide();
                $('#dayLeaveTime').html('');
                $('#normalDayResult').html('');

                $('#dayReason').hide();
                $('#dayReasonLine').show();
                var hor = parseInt(outHour) - 18;
                var moi = parseInt(outMinite);
                var hort = moi >= 30 ? 0.5 + hor : hor;
                allOverTime = hort;
                var overTime = "加班" + hort + "小时";
                $('#dayOverTime').html(overTime);
            } else {
                $('#dayResultLine').hide();
                $('#dayResult').hide();
                $('#dayLeaveTime').html('');
                $('#normalDayResult').html('正常出勤');

                $('#dayReason').show();
                console.log(self.option);

                // 补丁 zrw不对加班处理 by jxzhang on 20171228
                var sOpt = self.option.replace('<option value="5">加班</option>', '');
                $('#dayReason').html(sOpt);
                $('#dayReasonLine').hide();
                $('#dayOverTime').html('');
            }
        }
    };
    /** 批量调整 */
    var batchToAdjustObj = {
        option: '',
        init: function () {
            var self = this;
            self.batchLinkClickFun();
            self.cancelBtnFun();
        },
        batchLinkClickFun: function () {
            var self = this;
            $('#batchLink').unbind('click').bind('click', function () {
                var selectedYear = $('#selectYear option:selected').val();
                var selectedMonth = $('#selectMonth option:selected').val();
                var lastmonth = moment().subtract(1, 'M');
                if (moment().format('D') <= warnDay) {
                    if ((moment().format('YYYY') != selectedYear || moment().format('MM') != selectedMonth) &&
                        (lastmonth.format('YYYY') != selectedYear || lastmonth.format('MM') != selectedMonth)) {
                        showErrMsg(warnDay + "号之前只能调整当前月和上个月的考勤数据！");
                        return;
                    }
                } else {
                    if (moment().format('YYYY') != selectedYear || moment().format('MM') != selectedMonth) {
                        showErrMsg(warnDay + "号之后只能调整当前月的考勤数据！");
                        return;
                    }
                }

                $('#contentTitle').html(selectedMonth + '月批量调整');
                $('#batchAdjustment').show();
                $('.batch-submit-remind').hide();
                $('#preDayAdjustment').hide();
                $('#instruction').hide();
                $('#dayAdjustment').hide();
                $('#tr_batchResultWarn').hide();
                $('#tr_batchExceptionWarn').hide();

                batchAdjustmentObj.flag = false;

                var day = new Date();
                var date = moment().format('D') - 1;
                var yearMonth = moment().format('YYYYMM');
                if (selectedMonth != moment().format('MM')) {
                    date = lastmonth.daysInMonth();
                    yearMonth = lastmonth.format('YYYYMM');
                }
                $('#yearMonth').val(yearMonth);
                // 调整日期
                var monthStartOption = '';
                for (var i = 1; i <= date; i++) {
                    monthStartOption += '<option value="' + i + '">' + i + '日</option>';
                }
                $('#monthStart').html(monthStartOption);
                var monthEndOption = '';
                for (var i = date; i > 0; i--) {
                    monthEndOption += '<option value="' + i + '">' + i + '日</option>';
                }
                $('#monthEnd').html(monthEndOption);
                // 调整时间
                self.batchAdjustTimeFun();
                // 考勤结果
                $('#batchResult').hide();
                $('#batchResultLine').show();
                $('#batchLeaveTime').html('');
                $('#batchOverTime').html('');
                $('#normalResult').html('');
                // 异常原因
                $('#batchReason').hide();
                $('#batchReasonLine').show();
                $('#note').val('');
                // 时间调整触发事件
                $('#monthStart').change(function () {
                    var monthStart = $('#monthStart option:selected').val();
                    var op = '';
                    for (var i = date; i >= parseInt(monthStart); i--) {
                        op += '<option value="' + i + '">' + i + '日</option>';
                    }
                    $('#monthEnd').html(op);
                });
                // 点击全天请假事件
                self.bindClickBatchLeave();
                // 调整提交
                batchAdjustmentObj.init();
            });
        },
        batchAdjustTimeFun: function () {
            var self = this;
            $('#inTime').val('');
            $('#outTime').val('');
            $('#inTime').datetimepicker({
                format: 'hh:ii',
                autoclose: true,
                minuteStep: 15,
                initialDate: new Date(),
                language: 'cn',
                startView: 1,
                maxView: 1,
                minView: 0,
            }).on('changeDate', function (ev) {
                var inTime = $('#inTime').val();
                var outTime = $('#outTime').val();
                if ('' != inTime && '' != outTime) {
                    if (inTime > outTime) {
                        showErrMsg("上班时间不能大于下班时间！");
                        return;
                    }
                    var inhour = inTime.substr(0, 2);
                    var inminite = inTime.substr(3, 2);
                    var outhour = outTime.substr(0, 2);
                    var outminite = outTime.substr(3, 2);
                    self.batchChangeFun(inhour, inminite, outhour, outminite);
                }
            });
            $('#outTime').datetimepicker({
                format: 'hh:ii',
                autoclose: true,
                minuteStep: 15,
                initialDate: new Date(),
                language: 'cn',
                startView: 1,
                viewSelect: 'hour',
                maxView: 1,
                minView: 0,
            }).on('changeDate', function (ev) {
                var inTime = $('#inTime').val();
                var outTime = $('#outTime').val();
                if ('' != inTime && '' != outTime) {
                    if (inTime > outTime) {
                        showErrMsg("上班时间不能大于下班时间！");
                        return;
                    }
                    var inhour = inTime.substr(0, 2);
                    var inminite = inTime.substr(3, 2);
                    var outhour = outTime.substr(0, 2);
                    var outminite = outTime.substr(3, 2);
                    self.batchChangeFun(inhour, inminite, outhour, outminite);
                }
            });

            $('#batchResult').change(function () {
                var dr = $('#batchResult option:selected').text();
                if (dr == "调休") {
                    $('#tr_batchResultWarn').show();
                    $('#batchLeaveInLieu').show();
                    $('#batchAnnualLeave').hide();
                    $('#batchOvertime_remind').hide();
                    $('#batchResultday').html(vacationObj.total);
                    var dot = $('#batchOverTime').html();
                    if (dot.indexOf("加班") != -1) {
                        $('#batchOvertime_remind').show();
                    }
                } else if (dr == "年假") {
                    $('#tr_batchResultWarn').show();
                    $('#batchLeaveInLieu').hide();
                    $('#batchAnnualLeave').show();
                    $('#batchOvertime_remind').hide();
                    $('#batchAnnualday').html(vacationObj.annual);
                    var dot = $('#batchOverTime').html();
                    if (dot.indexOf("加班") != -1) {
                        $('#batchOvertime_remind').show();
                    }
                } else {
                    $('#tr_batchResultWarn').hide();
                }
            });

            $('#batchReason').change(function () {
                var dr = $('#batchReason option:selected').text();
                if (dr != "请选择" && dr != "") {
                    $('#tr_batchExceptionWarn').hide();
                } else {
                    $('#tr_batchExceptionWarn').show();
                }
            });

        },
        bindClickBatchLeave: function () {
            var self = this;
            var flag = true;
            $('#batchLeave').unbind('click').bind('click', function () {
                $('#tr_batchResultWarn').hide();
                $('#tr_batchExceptionWarn').hide();
                $('#inTime').val('');
                $('#outTime').val('');

                if (flag) {
                    $('#batchResult').show();
                    $('#batchResultLine').hide();
                    $('#batchLeaveTime').html('1天');
                    $('#batchOverTime').html('');
                    $('#normalResult').html('');

                    $('#batchReason').hide();
                    $('#batchReasonLine').show();
                    allLeaveTime = 7.5;
                    self.batchAdjustTimeFun();
                    var op = '';
                    $.each(checkTypeObj.checkType, function (i, o) {
                        if ('正常出勤' != o.typeName && '加班' != o.typeName) {
                            if ('事假' == o.typeName)
                                op += '<option value="' + o.typeId + '" selected>' + o.typeName + '</option>';
                            else
                                op += '<option value="' + o.typeId + '">' + o.typeName + '</option>';
                        }
                    });
                    $('#batchResult').html(op);
                    flag = false;
                    batchAdjustmentObj.flag = true;
                } else {
                    $('#batchResult').hide();
                    $('#batchResultLine').show();
                    $('#batchLeaveTime').html('');
                    $('#batchOverTime').html('');

                    $('#batchReason').hide();
                    $('#batchReasonLine').show();
                    flag = true;
                    batchAdjustmentObj.flag = false;
                }
            });
        },
        batchChangeFun: function (inHour, inMinite, outHour, outMinite) {
            var self = this;
            $('#tr_batchResultWarn').hide();
            $('#tr_batchExceptionWarn').hide();

            var opt = '';
            $.each(checkTypeObj.checkType, function (i, o) {
                if ('正常出勤' != o.typeName && '加班' != o.typeName) {
                    if ('事假' == o.typeName)
                        opt += '<option value="' + o.typeId + '" selected>' + o.typeName + '</option>';
                    else
                        opt += '<option value="' + o.typeId + '">' + o.typeName + '</option>';
                }
            });
            if (inHour != '' && inMinite != '' && outHour != '' && outMinite != '') {
                if ((parseInt(inHour) > 10 || (parseInt(inHour) == 10 && parseInt(inMinite) > 0)) && parseInt(outHour) < 18) {
                    $('#batchResult').show();
                    $('#batchResultLine').hide();
                    $('#batchResult').html(opt);
                    $('#batchOverTime').html('');
                    $('#normalResult').html('');

                    $('#batchReason').hide();
                    $('#batchReasonLine').show();
                    var leaveTime = 0;
                    if (parseInt(inHour) < 12 && (parseInt(outHour) > 13 || (parseInt(outHour) == 13 && parseInt(outMinite) > 30))) {
                        var hr = parseInt(inHour) - 9 + (parseInt(outMinite) > 0 ? 17 - parseInt(outHour) : 18 - parseInt(outHour));
                        var mi = parseInt(inMinite) + (parseInt(outMinite) > 0 ? 60 - parseInt(outMinite) : 0);
                        var hrt = mi > 60 ? hr + 1.5 : (mi > 30 ? hr + 1 : (mi > 0 ? hr + 0.5 : hr));
                        var hourtoday = hourToDay(hrt);
                        leaveTime = hourtoday + "天";
                        if (hourtoday == 0.5) {
                            allLeaveTime = 4;
                        } else {
                            allLeaveTime = 7.5;
                        }
                    } else if (parseInt(inHour) < 12 && (parseInt(outHour) < 13 || (parseInt(outHour) == 13 && parseInt(outMinite) <= 30))) {
                        leaveTime = "1天";
                        allLeaveTime = 7.5;
                    } else if (parseInt(inHour) >= 12) {
                        if (parseInt(inHour) > 13 || (parseInt(inHour) == 13 && parseInt(inMinite) >= 30)) {
                            var hr = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1;
                            var mi = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite);
                            var hrt = mi >= 30 ? hr + 0.5 : hr;
                        } else {
                            var hr = parseInt(outMinite) >= 30 ? parseInt(outHour) - 13 : parseInt(outHour) - 13 - 1;
                            var mi = parseInt(outMinite) >= 30 ? parseInt(outMinite) - 30 : parseInt(outMinite) + 30;
                            var hrt = mi >= 30 ? hr + 0.5 : hr;
                        }
                        if (hrt >= 3.5) {
                            leaveTime = "0.5天";
                            allLeaveTime = 4;
                        } else {
                            leaveTime = "1天";
                            allLeaveTime = 7.5;
                        }
                    }
                    $('#batchLeaveTime').html(leaveTime);
                } else if ((parseInt(inHour) > 10 || (parseInt(inHour) == 10 && parseInt(inMinite) > 0)) && parseInt(outHour) >= 18) {
                    $('#batchResult').show();
                    $('#batchResultLine').hide();
                    $('#batchResult').html(opt);
                    $('#normalResult').html('');

                    $('#batchReason').hide();
                    $('#batchReasonLine').show();
                    var leaveTime = 0;
                    if (parseInt(inHour) < 13 || (parseInt(inHour) == 13 && parseInt(inMinite) <= 30)) {
                        allLeaveTime = 4;
                        leaveTime = "0.5天";
                    } else {
                        var hr = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outHour) - parseInt(inHour) : parseInt(outHour) - parseInt(inHour) - 1;
                        var mi = parseInt(outMinite) >= parseInt(inMinite) ? parseInt(outMinite) - parseInt(inMinite) : parseInt(outMinite) + 60 - parseInt(inMinite);
                        var hrt = mi >= 30 ? hr + 0.5 : hr;
                        if (hrt >= 3.5) {
                            leaveTime = "0.5天";
                            allLeaveTime = 4;
                        } else {
                            leaveTime = "1天";
                            allLeaveTime = 7.5;
                        }
                    }
                    $('#batchLeaveTime').html(leaveTime);
                    if (parseInt(outHour) > 18 || (parseInt(outHour) == 18 && parseInt(outMinite) >= 30)) {
                        var hor = parseInt(outHour) - 18;
                        var moi = parseInt(outMinite);
                        var hort = moi >= 30 ? 0.5 + hor : hor;
                        allOverTime = hort;
                        var overTime = "加班" + hort + "小时";
                        $('#batchOverTime').html(overTime);
                    }
                } else if ((parseInt(inHour) < 10 || (parseInt(inHour) == 10 && parseInt(inMinite) == 0)) && parseInt(outHour) < 18) {
                    $('#batchResult').show();
                    $('#batchResultLine').hide();
                    $('#batchResult').html(opt);
                    $('#batchOverTime').html('');
                    $('#normalResult').html('');

                    $('#batchReason').hide();
                    $('#batchReasonLine').show();
                    var leaveTime = 0;
                    if (parseInt(outHour) >= 12) {
                        leaveTime = "0.5天";
                        allLeaveTime = 4;
                    } else {
                        leaveTime = "1天";
                        allLeaveTime = 7.5;
                    }
                    $('#batchLeaveTime').html(leaveTime);
                } else if ((parseInt(inHour) < 10 || (parseInt(inHour) == 10 && parseInt(inMinite) == 0)) && (parseInt(outHour) > 18 || (parseInt(outHour) == 18 && parseInt(outMinite) >= 30))) {
                    $('#batchResult').show();
                    $('#batchResultLine').hide();
                    $('#batchResult').hide();
                    $('#batchLeaveTime').html('');
                    $('#normalResult').html('');

                    $('#batchReason').hide();
                    $('#batchReasonLine').show();
                    var hor = parseInt(outHour) - 18;
                    var moi = parseInt(outMinite);
                    var hort = moi >= 30 ? 0.5 + hor : hor;
                    allOverTime = hort;
                    var overTime = "加班" + hort + "小时";
                    $('#batchOverTime').html(overTime);
                } else {
                    $('#batchResultLine').hide();
                    $('#batchResult').hide();
                    $('#batchLeaveTime').html('');
                    $('#normalResult').html('正常出勤');

                    $('#batchReason').show();
                    $('#batchReason').html(self.option);
                    $('#batchReasonLine').hide();
                    $('#batchOverTime').html('');
                }
            }
        },
        cancelBtnFun: function () {
            $('#cancelBtn').unbind('click').bind('click', function () {
                $('#contentTitle').html('调整说明');
                $('#instruction').show();
                $('#batchAdjustment').hide();
                $('.batch-submit-remind').hide();
                $('#preDayAdjustment').hide();
                $('#dayAdjustment').hide();
                $('.day').removeClass('clicked');
            });
        }
    }

    var dayAdjustmentObj = {
        flag: false,  // 是否为全天调整 true是，false否
        init: function (day) {
            this.submitFun(day);
        },
        submitFun: function (day) {
            var self = this;
            $('#daySubmit').show();
            $('#dayNowSubmit').hide();
            $('#daySubmit').unbind('click').bind('click', function () {
                $('#daySubmit').hide();
                $('#dayNowSubmit').show();
// var day = $('#contentTitle').html();
                var inHour = $('#inHour option:selected').val();
                var inMinite = $('#inMinite option:selected').val();
                var outHour = $('#outHour option:selected').val();
                var outMinite = $('#outMinite option:selected').val();

                var dayResult = $('#dayResult option:selected').val();
                var dayResultName = $('#dayResult option:selected').text();
                var dayOverTime = $('#dayOverTime').html();
                var normalResult = $('#normalDayResult').html();
                var dayLeaveTime = $('#dayLeaveTime').html();
                var dayReason = $('#dayReason option:selected').text();
                if (normalResult == "正常出勤") {
                    if (dayReason == "请选择") {
                        $('#tr_exceptionWarn').show();
                        $('#daySubmit').show();
                        $('#dayNowSubmit').hide();
                        return;
                    } else {
                        $('#tr_exceptionWarn').hide();
                    }
                } else {
                    dayReason = '';
                }
                if (dayReason == "请选择")
                    dayReason = '';
                var note = $('#dayNote').val();
                //
                var intime = $('#indayTime').val();
                var outtime = $('#outdayTime').val();
                var isAll = true;
                if (self.flag) {
                    isAll = true;
                    dayReason = null;
                } else {
                    if (intime != '' && outtime != '') {
                        isAll = true;
                    } else {
                        isAll = false;
                    }
                }
                if (isAll || dayLeaveTime == "1天") {
                    if (intime > outtime) {
                        showErrMsg("上班时间不能大于下班时间！");
                        return;
                    }
                    var hour = '';
                    var result = '';
                    var typeName = '';
                    if (normalAttendanceObj.allTheory.indexOf(day) > -1) {
                        if (undefined != normalResult && '' != normalResult) {
                            result = checkTypeObj.normalId;
                            typeName = "正常出勤";
                            hour = 7.5;
                        } else {
                            if ((undefined != dayLeaveTime && '' != dayLeaveTime) && (undefined != dayOverTime && '' != dayOverTime)) {
                                result = checkTypeObj.normalId + "," + checkTypeObj.overtimeId + "," + dayResult;
                                typeName = "正常出勤" + "," + "加班" + "," + "请假";
                                hour = (allLeaveTime == 7.5 ? 0 : 4) + "," + allOverTime + "," + allLeaveTime;
                            } else if ((undefined != dayLeaveTime && '' != dayLeaveTime) && (undefined == dayOverTime || '' == dayOverTime)) {
                                result = checkTypeObj.normalId + "," + dayResult;
                                typeName = "正常出勤" + "," + "请假";
                                hour = (allLeaveTime == 7.5 ? 0 : 4) + "," + allLeaveTime;
                            } else if ((undefined == dayLeaveTime || '' == dayLeaveTime) && (undefined != dayOverTime && '' != dayOverTime)) {
                                result = checkTypeObj.normalId + "," + checkTypeObj.overtimeId;
                                typeName = "正常出勤" + "," + "加班";
                                hour = 7.5 + "," + allOverTime;
                            } else {
                                result = checkTypeObj.normalId;
                                typeName = "正常出勤";
                                hour = 7.5;
                            }
                        }
                    } else {  // 周末和节假日调整
                        result = checkTypeObj.overtimeId;
                        typeName = "加班";
                        hour = allOverTime;
                    }

                    var inTime = intime == '' ? null : day + ' ' + intime + ":00";
                    var outTime = outtime == '' ? null : day + ' ' + outtime + ":00";
                    var yearMonth = day.substr(0, 4) + day.substr(5, 2);
                    var param = {
                        inTime: inTime,
                        outTime: outTime,
                        reason: dayReason,
                        typeId: result,
                        typeName: typeName,
                        note: note,
                        day: day,
                        yearMonth: yearMonth,
                        hour: hour,
                        organId: this_organId,
                        type: 1
                    };
                    self.requestData(param);
                } else {
                    showErrMsg("请选择时间！");
                    $('#daySubmit').show();
                    $('#dayNowSubmit').hide();
                    return;
                }
            });
        },
        requestData: function (param) {
            $.post(
                urls.updateEmpAttendanceUrl,
                param,
                function (rs) {
                    if (rs == true) {
                        attendanceObj.yearMonth = param.day.substr(0, 4) + param.day.substr(5, 2);
                        attendanceObj.init(param.day);
                        dayToAdjustObj.init(param.day);
                        $('#contentTitle').html(param.day.substr(0, 4) + '年' + param.day.substr(5, 2) + '月' + param.day.substr(8, 2) + '日');
                        $('#preDayAdjustment').show();
                        $('.batch-submit-remind').hide();
                        $('#instruction').hide();
                        $('#batchAdjustment').hide();
                        $('#dayAdjustment').hide();

                        vacationObj.init();
                    } else {
                        showErrMsg(warnDay + "号或者" + warnDay + "号之前只能调整上月的考勤；" + warnDay + "号之后只能调整当前月的考前！");
                        $('#daySubmit').show();
                        $('#dayNowSubmit').hide();
                    }
                }
            );
        }
    };

    var batchAdjustmentObj = {
        flag: false,
        param: {},
        dayArr: [],
        init: function () {
            this.submit();
        },
        submit: function () {
            var self = this;
            $('#batchSubmit').unbind('click').bind('click', function () {
                $('#batchRemindSubmit').show();
                $('#batchNowSubmit').hide();
                var monthStart = $('#monthStart option:selected').val();
                var monthEnd = $('#monthEnd option:selected').val();
                var inTime = $('#inTime').val();
                var outTime = $('#outTime').val();
                var batchResult = $('#batchResult option:selected').val();
                var batchResultName = $('#batchResult option:selected').text();
                var batchOverTime = $('#batchOverTime').html();
                var normalResult = $('#normalResult').html();
                var batchLeaveTime = $('#batchLeaveTime').html();
                var batchReason = $('#batchReason option:selected').text();

                if (normalResult == "正常出勤") {
                    if (batchReason == "请选择") {
                        $('#tr_batchExceptionWarn').show();
                        return;
                    } else {
                        $('#tr_batchExceptionWarn').hide();
                    }
                } else {
                    batchReason = '';
                }
                if (batchReason == "请选择")
                    batchReason = '';
                if (normalResult == "正常出勤") {

                }
                var note = $('#note').val();
                var isAll = true;
                if (self.flag) {
                    isAll = true;
                    batchReason = null;
                } else {
                    if (inTime != '' && outTime != '') {
                        isAll = true;
                    } else {
                        isAll = false;
                    }
                }
                var leaveTime = $('#leaveTime').html();
                if (isAll) {
                    if ('' != inTime && '' != outTime) {
                        if (inTime > outTime) {
                            showErrMsg("上班时间不能大于下班时间！");
                            $('#batchRemindSubmit').hide();
                            $('#batchNowSubmit').show();
                            return;
                        }
                    }
                    var hour = '';
                    var result = '';
                    var typeName = '';
                    if (undefined != normalResult && '' != normalResult) {
                        result = checkTypeObj.normalId;
                        hour = 7.5;
                        typeName = "正常出勤";
                        $('#attendancetype').html('正常出勤');
                    } else {
                        if ((undefined != batchLeaveTime && '' != batchLeaveTime) && (undefined != batchOverTime && '' != batchOverTime)) {
                            result = checkTypeObj.normalId + "," + checkTypeObj.overtimeId + "," + batchResult;
                            hour = (allLeaveTime == 7.5 ? 0 : 4) + "," + allOverTime + "," + allLeaveTime;
                            typeName = "正常出勤" + "," + "加班" + "," + "请假";
                            $('#attendancetype').html("加班" + "," + batchResultName);
                        } else if ((undefined != batchLeaveTime && '' != batchLeaveTime) && !(undefined != batchOverTime && '' != batchOverTime)) {
                            result = checkTypeObj.normalId + "," + batchResult;
                            typeName = "正常出勤" + "," + "请假";
                            hour = (allLeaveTime == 7.5 ? 0 : 4) + "," + allLeaveTime;
                            $('#attendancetype').html(batchResultName);
                        } else if (!(undefined != batchLeaveTime && '' != batchLeaveTime) && (undefined != batchOverTime && '' != batchOverTime)) {
                            result = checkTypeObj.normalId + "," + checkTypeObj.overtimeId;
                            hour = 7.5 + "," + allOverTime;
                            typeName = "正常出勤" + "," + "加班";
                            $('#attendancetype').html('加班');
                        } else {
                            result = checkTypeObj.normalId;
                            hour = 7.5;
                            typeName = "正常出勤";
                            $('#attendancetype').html('正常出勤');
                        }
                    }
                    inTime = inTime == '' ? null : inTime + ':00';
                    outTime = outTime == '' ? null : outTime + ':00';

                    var yearMonth = $('#yearMonth').val();
                    var dayArr = [];
                    var selectYear = $('#selectYear option:selected').val();
                    var selectMonth = $('#selectMonth option:selected').val();
                    var YM = selectYear + '-' + selectMonth + '-';

                    $.each(normalAttendanceObj.batchTheory, function (i, day) {
                        if (day.indexOf(YM) != -1 &&
                            parseInt(day.substr(8, 2)) >= parseInt(monthStart) &&
                            parseInt(day.substr(8, 2)) <= parseInt(monthEnd)) {
                            dayArr.push(day);
                        }
                    });
                    self.dayArr = dayArr;
                    var param = {
                        inTime: inTime,
                        outTime: outTime,
                        reason: batchReason,
                        typeId: result,
                        typeName: typeName,
                        note: note,
                        day: dayArr.toString(),
                        yearMonth: yearMonth,
                        hour: hour,
                        organId: this_organId,
                        type: 2
                    };
                    self.param = param;
                    self.generate(yearMonth);
                } else {
                    showErrMsg("请选择时间！");
                    $('#batchRemindSubmit').show();
                    $('#batchNowSubmit').hide();
                    return;
                }
            });
        },
        generate: function (yearMonth) {
            var self = this;
            $('.batch-submit-remind').show();
            $('#batchAdjustment').hide();
            var days = '';
            $.each(self.dayArr, function (i, day) {
                if (i == self.dayArr.length - 1)
                    days += parseInt(day.substr(8, 2));
                else
                    days += parseInt(day.substr(8, 2)) + '、';
            });
            $('#days').html(days);
            $('#cancelRemindBtn').unbind('click').bind('click', function () {
                $('.batch-submit-remind').hide();
                $('#batchAdjustment').show();
                $('#batchRemindSubmit').show();
                $('#batchNowSubmit').hide();
            });
            $('#batchRemindSubmit').unbind('click').bind('click', function () {
                $('#batchNowSubmit').show();
                $('#batchRemindSubmit').hide();
                self.requestData(yearMonth);
            });
        },
        requestData: function (yearMonth) {
            var self = this;
            $.post(
                urls.updateEmpAttendanceUrl,
                self.param,
                function (rs) {
                    if (rs == true) {
                        attendanceObj.request(yearMonth);
                        $('#contentTitle').html("调整说明");
                        $('#preDayAdjustment').hide();
                        $('.batch-submit-remind').hide();
                        $('#instruction').show();
                        $('#batchAdjustment').hide();
                        $('#dayAdjustment').hide();
                        $('.day').removeClass('clicked');
                        vacationObj.init();
                    } else {
                        showErrMsg(warnDay + "号或者" + warnDay + "号之前只能调整上月的考勤；" + warnDay + "号之后只能调整当前月的考前！");
                        $('#batchRemindSubmit').show();
                        $('#batchNowSubmit').hide();
                    }
                }
            );
        }
    };

    /** 历史假期 by jxzhang */
    var empOtherDay = {
        yearList: [],
        init: function () {
            this.requestY();
            var tb = '';
            if (Tc.isNotEmpty(empOtherDay.yearList)) {
                var selectHtml = '<select id="otherDayYearStatus">';
                $.each(empOtherDay.yearList, function (i, item) {
                    selectHtml += '<option value="' + item + '">' + item + '</option>';
                });
                selectHtml += '</select>';
                tb =
                    `<table class="table table-striped">
                		<thead><tr><td>名称</td><td>考勤类别</td><td>小时数</td><td>` + selectHtml + `</td></tr></thead>
                		<tbody>
                	`;
                tb += '</tbody></table>';
                empOtherDay.request(empOtherDay.yearList[0]);   // 第一次load数据
            }
            $('.attendance-other-day').empty();
            $('.attendance-other-day').append(tb);
            empOtherDay.selectEvent();
        },
        requestY: function () {
            $.ajax({
                type: "post",
                url: urls.queryEmpOtherDayYearUrl,
                async: false,
                success: function (yearList) {
                    empOtherDay.yearList = yearList;

                }
            });
        },
        request: function (yearList) {
            var param = {
                startYm: yearList + "01",
                endYm: yearList + "12"
            };
            $.post(urls.queryEmpOtherDayUrl, param, function (rs) {

                var trHtml = "";
                if (Tc.isNotEmpty(rs)) {

                    $.each(rs, function (i, item) {
                        trHtml += '<tr><td>' + item.userName + '</td><td>' + item.typeName + '</td><td>-' + item.hourCount + '</td><td>' + item.days + '</td></tr>';
                    });
                    $('.attendance-other-day table tbody').empty();
                    $('.attendance-other-day table tbody').append(trHtml);
                }

            });
        },
        selectEvent: function () {
            $("select#otherDayYearStatus").change(function () {
                empOtherDay.request($(this).val());
            });
        }
    }
    empOtherDay.init();


    /***************************************************************************
     * 初始化echart
     *
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    /**
     * 清除echart面板
     *
     * @param eChartObj
     */
    function clearEChart(eChartObj) {
        if (eChartObj) {
            eChartObj.clear();
        }
    }

    /**
     * 显示错误提示
     */
    function showErrMsg(content) {
        $._messengerDefaults = {
            extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top messenger-on-right'
        };
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    }

    /**
     * 小时转换为天的规则
     */
    function hourToDay(hour) {
        if (hour <= 4) {
            return 0.5;
        } else if (hour > 4) {
            return 1;
        }
    }

    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };

    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };

});