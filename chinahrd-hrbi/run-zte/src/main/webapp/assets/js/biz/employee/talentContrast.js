/**
 * Created by wqcai on 15/10/10 0028.
 */
require(['jquery', 'moment', 'echarts', 'echarts/chart/line', 'bootstrap', 'ace', 'jquery-pin', 'jgGrid', 'underscore', 'resize'], function ($, moment, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        getEmpInfoUrl: webRoot + '/talentContrast/getEmpInfo.do',               //获取员工信息
        getPerfChangeUrl: webRoot + '/talentProfile/getPerfChange.do',          //获取员工绩效信息
        getJobChangeUrl: webRoot + '/talentProfile/getJobChange.do',             //获取工作异动相关信息
        getSearchEmpUrl: webRoot + '/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
        getEvalReportUrl: webRoot + '/talentContrast/getEvalReport.do',          //获取测试信息
        getDepartChangeUrl: webRoot + '/talentContrast/getDepartChange.do',      //获取本公司经历信息
        getPastResumeUrl: webRoot + '/talentProfile/getPastResume.do'              //获取过往履历信息
    };
    $.zrw_resizeFrameSize("myScrollspy");
    var empIds = "";
    var isCurPage = true;   //是否在当前页面
    var winScrollObj = {
        navId: '#myNav',
        isClick: false,
        clickNavId: "",
        init: function () {
            var self = this;
            var fOffsetTop = 0;
            isCurPage = true;
            var _navObj = $(self.navId);
            var _navLi = _navObj.children('li');
            var frames = window.parent.document.getElementById("mainFrame");
            var winObj = !frames ? window : window.parent;
            self.num = 2
            _navLi.click(function () {
                self.num = 2;
                var _this = $(this);
                self.setLiClass(_this);
                self.isClick = true;
                self.clickNavId = $(this).children().attr("href");
                if (frames) {
                    var href = _this.children().attr('href');
                    if (href.indexOf('#') != -1) {//判断是否是锚点而不是链接
                        //父窗口滚动

                        $(window.parent).scrollTop($(href).offset().top + $(frames).offset().top);
                    }
                }
                $(winObj).scroll();
            });

            if (!frames) {	//父级iframe不存在则使用bootstrap的pin插件
//                _navObj.pin({
//                   frames containerSelector: '#myScrollspy',
//                    padding: {
//                        top: 1
//                    }
//                });
            } else {
                fOffsetTop = $(frames).offset().top;
            }


            $(winObj).scroll(function () {
                if (!isCurPage) {
                    return;
                }

                var offetTop = (!frames) ? 124 : 183;
                //var clickTop=0;
                if (frames) {
                    var breadcrumbs = winObj.document.getElementById("breadcrumbs");
                    var navbar = winObj.document.getElementById("navbar");
                    if (!$(breadcrumbs).hasClass("breadcrumbs-fixed")) {
                        offetTop -= 40;
                        //clickTop-=40;
                    }
                    if (!$(navbar).hasClass("navbar-fixed-top")) {
                        offetTop -= 60;
                        //clickTop-=60;
                    }
                }
                var _this = $(this);
                var top = _this.scrollTop();
                var liArr = self.getScrollOffset(_navLi);
                // console.log($(winObj).scrollTop()+" "+screen.height+" "+$(window).height());
                if (self.isClick) {
                    self.num--;
                    self.isClick = false;
                    setTimeout(function () {
                        var _navObj = $("#myNav");
                        var scrollTop = $(winObj).scrollTop();
                        var scrollHeight = $(winObj.document).height();
                        var windowHeight = $(winObj).height();
                        if (frames) {
                            if (scrollTop + windowHeight >= (scrollHeight - 2)) {
                                var t = $(self.clickNavId).offset().top;
                                if (t > (scrollTop + 130)) {
                                    $(winObj).scrollTop(scrollHeight);
                                } else {
                                    $(winObj).scrollTop(scrollTop - 130 + (t - scrollTop));
                                }
                            } else {
                                $(winObj).scrollTop(scrollTop - offetTop - 34);
                            }
                        } else {
                            var t = $(self.clickNavId).offset().top;
                            if (Math.abs(t - scrollTop) < 3) {
                                $(winObj).scrollTop(scrollTop - offetTop - 34);
                            } else {
                                if ((t - scrollTop) > (offetTop + 34)) {
                                    $(winObj).scrollTop(scrollTop);
                                } else {
                                    $(winObj).scrollTop(scrollTop - (offetTop + 34) + (t - scrollTop));
                                }
                            }

                        }


                        self.num--;
                    }, 20);

                }
                //搜索框随滚动
                var srollTop = frames ? 80 : 80;
                // alert(srollTop+"  "+(top < srollTop));
                var navTop = srollTop;
                if (frames) {
                    var breadcrumbs = winObj.document.getElementById("breadcrumbs");
                    var navbar = winObj.document.getElementById("navbar");

                    if (!$(breadcrumbs).hasClass("breadcrumbs-fixed")) {
                        navTop = navTop + 50;
                    }
                    if (!$(navbar).hasClass("navbar-fixed-top")) {
                        navTop = navTop + 40;
                    }
                }
                if (top < navTop) {
                    $('#contrastObj').css({
                        position: 'static',
                        top: 0,
                        borderBottom: 'none'
                    });
                } else {
                    if (frames) {
                        var breadcrumbs = winObj.document.getElementById("breadcrumbs");
                        var navbar = winObj.document.getElementById("navbar");
                        var objTop = top - 70;
                        if (!$(breadcrumbs).hasClass("breadcrumbs-fixed")) {
                            objTop -= 40;
                        }
                        if (!$(navbar).hasClass("navbar-fixed-top")) {
                            objTop -= 60;

                        }
                        $('#contrastObj').css({
                            position: 'relative',
                            top: objTop,
                            borderBottom: '1px solid #999'
                        });
                    } else {
                        $('#contrastObj').css({
                            position: 'relative',
                            top: top - 80,
                            borderBottom: '1px solid #999'
                        });
                    }

                }
                if (top < (navTop - 63)) {
                    _navObj.css({
                        position: ''
                    });
                } else {
                    if (frames) {
                        var breadcrumbs = winObj.document.getElementById("breadcrumbs");
                        var navbar = winObj.document.getElementById("navbar");
                        var _navObjTop = top;
                        if (!$(breadcrumbs).hasClass("breadcrumbs-fixed")) {
                            _navObjTop -= 40;
                        }
                        if (!$(navbar).hasClass("navbar-fixed-top")) {
                            _navObjTop -= 60;

                        }
                        _navObj.css({
                            position: 'fixed',
                            top: _navObjTop,
                            borderBottom: '1px solid #999'
                        });
                    } else {
                        _navObj.css({
                            position: 'fixed',
                            top: 5,
                            borderBottom: '1px solid #999'
                        });
                    }
                }
                if (liArr) {
                    if (!self.isClick && self.num == 2) {

                        var wOffsetTop = top - fOffsetTop;
                        var currObj;
                        if ((frames && (top + 1 >= $(_this[0].document).height() - _this.height()))
                            || (wOffsetTop >= liArr[liArr.length - 1])) {
                            currObj = _navLi[_navLi.length - 1];
                        } else if (wOffsetTop <= liArr[0]) {
                            currObj = _navLi[0];
                        } else {
                            $.each(liArr, function (i, num) {
                                var prevNum;
                                if (i - 1 >= 0) {
                                    var prevNum = liArr[i - 1];
                                    if (prevNum - offetTop <= wOffsetTop && wOffsetTop < num - offetTop) {
                                        currObj = _navLi[i - 1];
                                        return true;
                                    }
                                }
                            });
                        }
                        self.setLiClass($(currObj));
                    }
                    if (self.num <= 0) {
                        self.num = 2;
                    }
                    if (frames) {
                        $(window).scrollTop(0);
                    }
                }

            });
        },
        setLiClass: function (_obj) {
            _obj.siblings().removeClass('active');
            _obj.addClass('active');
        },
        getScrollOffset: function (objs) {
            if (window == null) {
                isCurPage = false;
                return;
            }
            var frames = window.parent.document.getElementById("mainFrame");
            var newArr = [];
            $.each(objs, function (i, obj) {
                var linkObj = $(obj).children().attr('href');
                if (!frames) {
                    newArr.push(Math.round($(linkObj).offset().top) - 40);
                } else {
                    newArr.push(Math.round($(linkObj).offset().top - 75));
                }
                // newArr.push(Math.round($(linkObj).offset().top));
            });
            return newArr;
        }
    };

    var searchEmpObj = {
        gridId: '#searchEmpGrid',
        searchTxt: null,
        gridOption: {
            url: urls.getSearchEmpUrl,
            datatype: 'json',
            postData: {},
            mtype: 'POST',
            autowidth: true,
            height: 268,//268
            colNames: ['员工ID', '姓名', '部门', '操作'],
            colModel: [
                {name: 'empKey', width: 80, sortable: false, align: 'center'},
                {name: 'userName', width: 150, sortable: false, align: 'center'},
                {name: 'organName', width: 200, sortable: false, align: 'center'},
                {
                    name: 'myac',
                    width: 100,
                    fixed: true,
                    sortable: false,
                    align: 'center',
                    formatter: function (value, options, row) {
                        return '<a href="javascript:void(0)" data-index="' + row.empId + '" class="add_col" >加入</a>';
                    }
                }
            ],
            rownumbers: true,
            rownumWidth: 40,
            loadComplete: function (xhr) {

                $('.add_col').unbind('click').bind('click', function (e) {
                    var _this = $(this);
                    var empId = _this.attr('data-index');
                    //TODO 添加进对比的清单
                    if (empIds.indexOf(empId, 0) != -1) {
                        alert('该员工已存在对比列表！');
                        return;
                    } else {
                        empIds += ' ' + empId;
//                    	console.log(empIds);
                    }
                    $.post(urls.getEmpInfoUrl, {'empIds': empId}, function (rs) {
                        if (_.isEmpty(rs)) {
                            return;
                        }
                        var empObj = rs[0];
                        var idx = $('#searchIndex').val();
                        empAssignInfoObj.init(empObj, idx);

                        perfObj.init(empId, idx);
                        evalObj.init(empId, idx);
                        workChangeObj.init(empId, idx);
                        $('#searchModal').modal('hide');
                    });
                });

                //TODO 当最后一页只有一条数据的时候，jqGrid计算的高度不准确，最后一页无法滚动触发加载，现无有好的办法解决
                $("#searchEmpGrid").parent().height(34 * xhr.records);
            },
            scroll: true
        },
        init: function (searchTxt) {
            var self = this;
            if (_.isNull(self.searchTxt)) {
                self.searchTxt = searchTxt;
                self.gridOption.postData = {'keyName': searchTxt};
                $(self.gridId).jqGrid(self.gridOption);
            }
            if (searchTxt != self.searchTxt) {
                self.searchTxt = searchTxt;
                self.initGrid(searchTxt);
            }
        },
        initGrid: function (keyTxt) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                postData: {'keyName': keyTxt}
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            var parentDom = $('#gbox_' + self.gridId.split('#')[1]).parent();
            $(self.gridId).setGridWidth(parentDom.width());
        }
    };


    //赋值员工信息
    var empAssignInfoObj = {
        init: function (empObj, index) {
            var self = this;
            if (_.isEmpty(empObj)) {
                winContrastObj.initHeaderHTML(index);
                return;
            }
            var empId = empObj.empId;
            self.assignBase(empObj, index);
            //生成底部的查看链接
            jobChangeObj.generateGrowLink(empObj, index);
        },
        assignBase: function (empObj, index) {
            var self = this;
            var imgPath = _.isEmpty(empObj.imgPath) ? webRoot + '/assets/img/base/u-default.png' : empObj.imgPath;
            var imgHTML = '<img src="' + imgPath + '"/>'
                + '<label class="panel-header-name">' + empObj.userName + '</label>'
                + '<label class="panel-header-um">' + empObj.empKey + '</label>'
                + '<button class="btn btn-white btn-header-remove">移除</button>'
                + '<input type=\"hidden\" id=\"empId' + index + '\" value=' + empObj.empId + '>';
            var _imgParent = $($('#contrastObj .col-xs-10 .col-xs-3')[index]);
            _imgParent.html(imgHTML);
            _imgParent.children('.btn-header-remove').bind('click', removeEmpBtnEvent);

            self.compareDiff($('#sexRow').children()[index], empObj.sex == 'm' ? '男' : '女');
            self.compareDiff($('#ageRow').children()[index], empObj.age);
            self.compareDiff($('#marryStatusRow').children()[index], empObj.marryStatus == 0 ? '未婚' : '已婚');
            self.compareDiff($('#degreeRow').children()[index], empObj.degree);
            self.compareDiff($('#organParentRow').children()[index], empObj.organParentName);
            self.compareDiff($('#organRow').children()[index], empObj.organName);
            self.compareDiff($('#entryDateRow').children()[index], empObj.entryDate);
            self.compareDiff($('#sequenceRow').children()[index], empObj.sequenceName);
            self.compareDiff($('#sequenceSubRow').children()[index], empObj.sequenceSubName);
            self.compareDiff($('#abilityRow').children()[index], empObj.abilityName);
            self.compareDiff($('#rankRow').children()[index], empObj.rankName);
            self.compareDiff($('#positionRow').children()[index], empObj.positionName);
        },
        compareDiff: function (obj, value) {
            var _obj = $(obj);
            var _txt = value == null ? '-' : value;
            _obj.text(_txt);
            var _arounds = _obj.siblings();
            var hasDiff = false;
            $.each(_arounds, function (i, temp) {
                var _tempVal = $.trim($(temp).text());
                if (!_.isEmpty(_tempVal) && _tempVal != value) {
                    hasDiff = true;
                    return true;
                }
            });
            if (!hasDiff) return;
            $.each(_arounds, function (i, temp) {
                var _temp = $(temp);
                var _tempVal = $.trim(_temp.text());
                if (!_.isEmpty(_tempVal)) {
                    _temp.addClass('diff');
                }
            });
            _obj.addClass('diff');
        }
    };


    var jobChangeChartOption = {
        grid: {
            borderWidth: 1,
            color: '#BCBCBC'
        },
        calculable: false,
        xAxis: [
            {
                boundaryGap: true,
                type: "category",
                name: "工作异动",
                nameTextStyle: {
                    color: '#000000'
                },
                splitLine: {
                    color: ['#ccc'],
                    width: 1,
                    type: 'dashed'
                },
                axisLine: {
                    lineStyle: {
                        color: '#BCBCBC'
                    }
                }, axisTick: {
                show: false
            },
                data: []
            }
        ],
        yAxis: [
            {
                boundaryGap: false,
                type: "category",
                name: "职级",
                nameTextStyle: {
                    color: '#000000'
                },
                splitLine: {
                    color: ['#ccc'],
                    width: 1,
                    type: 'dashed'
                },
                axisTick: {
                    show: false
                },
                axisLine: {
//                        onZero: false,
                    lineStyle: {
                        color: '#BCBCBC'
                    }
                },
                data: ['1级', '2级', '3级', '4级', '5级']
            },
            {
                type: "value",
                show: false,
                splitLine: false,
                min: 1,
                max: 5,         //TODO 此处根据不同公司的职级划分来定义
                splitNumber: 5,
                axisLine: {
                    lineStyle: {
                        color: '#BCBCBC'
                    }
                }
            }
        ],
        series: [
            {
                type: "line",
                smooth: false,
                yAxisIndex: 1,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#000000'
                            },
                            formatter: function (a, b, c) {
                                var source = jobChangeObj.filterData;
                                if (source[b]) {
                                    return source[b].rankName + '\n' + source[b].positionName;
                                }
                            }
                        },
                        lineStyle: {
                            color: '#86D7C6'
                            // type: 'dotted'
                        },
                        color: '#86D7C6'
                    }
                },
                data: []
            }
        ]
    };
    //成长轨迹
    var jobChangeObj = {
        requestSource: null,
        chartObj: echarts.init(document.getElementById('jobChangeChart')),
        init: function (empId) {
            var self = this;
            $.post(urls.getJobChangeUrl, {'empId': empId}, function (rs) {
                if (_.isEmpty(rs)) {
                    return;
                }
                self.initChart(rs);
            });
        },
        initChart: function (source) {
            var self = this;
            var filterData = {};
            for (var i = source.length - 1; i >= 0; i--) {
                //10/22，跟xn,sy确定以季度为单位，一个季度有多条数据，取最后一条
                var obj = source[i];
                var timeKey = self.getYearQuarter(obj.changeDate);
                filterData[timeKey] = obj;
            }
            self.filterData = filterData;
            var timeArr = self.packTimeArr(filterData);
            var seriesData = self.getSeriesData(filterData, timeArr);
            jobChangeChartOption.xAxis[0].data = timeArr;
            jobChangeChartOption.series[0].data = seriesData;
            self.chartObj.setOption(jobChangeChartOption, true);
            self.chartObj.resize();
        },
        getSeriesData: function (filterData, timeArr) {
            var seriesData = [];
            $.each(timeArr, function (i, time) {
                var obj = filterData[time];
                var rsData;
                if (!obj) {
                    var preData = _.clone(seriesData[i - 1]);
                    preData.symbolSize = 0;
                    rsData = preData;
                } else {
                    //-1是为了在坐标上正确显示，如A1.2,应该是显示在坐标轴[0,1]区间
                    var value = parseFloat(obj.rankName.substr(-3, 3)) - 1;
                    rsData = {value: value, data: obj};
                }
                seriesData.push(rsData);
            });
            return seriesData;
        },
        //遍历时间集合，生成每一个区间。如：开始时间 = 2014Q4 ,结束时间 = 2015Q2,则生成[2014Q4,2015Q1,2015Q2]
        packTimeArr: function (filterData) {
            var filterTime = _.keys(filterData);
            return filterTime;
//            var beginYQ = filterTime[0].split('Q');
//            var endYQ = _.last(filterTime).split('Q');
//            var beginYear = parseInt(beginYQ[0]),
//                beginMonth = parseInt(beginYQ[1]);
//            var yearLen = parseInt(endYQ[0]) - beginYear;
//
//            var timeArr = [];
//            for (var i = 0; i <= yearLen; i++) {
//                var beginIndex = (i == 0) ? beginMonth : 1;
//                var endIndex = (i == yearLen) ? parseInt(endYQ[1]) : 4;
//                for (var j = beginIndex; j <= endIndex; j++) {
//                    timeArr.push((beginYear + i) + 'Q' + j);
//                }
//            }
//            return timeArr;
        },
        //转换成年度季度，如2015Q1
        getYearQuarter: function (date) {
            var ymd = date.split('-');
            var month = parseInt(ymd[1]);
            var quarter;
            if (month <= 3) {
                quarter = 1;
            } else if (month <= 6) {
                quarter = 2;
            } else if (month <= 9) {
                quarter = 3;
            } else {
                quarter = 4;
            }
            return ymd[0] + 'Q' + quarter;
        },
        //生成查看链接
        generateGrowLink: function (empObj, index) {
            var growLink = $('<a href="#section-7" data-id="' + empObj.empId + '" data-name="' + empObj.userName + '">查看</a>');
            $('#growthLinkArea div').eq(index).empty().append(growLink);
            growLink.click(function () {
                var frames = window.parent.document.getElementById("mainFrame");

                $('#growModal').modal('show');
                $('#growModal .modal-title ').find('span').text($(this).attr('data-name'));
                jobChangeObj.init($(this).attr('data-id'));
            });
        }
    };

//    /*
//     * 点击成长轨迹重新定位锚点
//     */
//    $('#growModal').on('shown.bs.modal', function () {
//    	location.href = "#section-7";
//    	});
//    $('#growModal').on('show.bs.modal', function () {
//    	location.href = "#section-7";
//    	});
    var perfEnum = $('#performanceStr').val().split(',');
    var perfChartOption = {
        grid: {
            borderWidth: 0,
            x: 40,
            y: 10,
            x2: 10,
            y2: 40
        },
        calculable: false,
        xAxis: [
            {
                boundaryGap: false,
                type: "category",
                splitLine: {
                    color: ['#ccc'],
                    width: 0,
                    type: 'dashed'
                },
                axisLine: {
                    lineStyle: {
                        width: 0,
                        color: '#BCBCBC'
                    }
                },
                axisLabel: {
                    rotate: 30,
                },
                axisTick: false,
                data: []
            }
        ],
        yAxis: [
            {
                axisTick: false,
                boundaryGap: false,
                type: "category",
                splitLine: {
                    color: ['#ccc'],
                    width: 0,
                    type: 'dashed'
                },

                axisLine: {
                    onZero: false,
                    lineStyle: {
                        width: 0,
                        color: '#BCBCBC'
                    }
                },
                data: perfEnum
            },
            {
                boundaryGap: false,
                type: "value",
                show: false,
                splitLine: false,
                splitNumber: 1,
                min: 1, //最小1星
                max: perfEnum.length,         //TODO 此处根据不同公司的职级划分来定义
                splitNumber: perfEnum.length,
                axisLine: {
                    lineStyle: {
                        color: '#BCBCBC'
                    }
                }
            }
        ],
        series: [
            {
                type: "line",
                smooth: false, //不用平滑模式
                yAxisIndex: 1,
                showAllSymbol: true,
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        lineStyle: {
                            color: '#86D7C6'
                            // ,type: 'dotted'
                        },
                        color: '#86D7C6'
                    }
                },
                data: []
            }
        ]
    };

    var perfObj = {
        chartIdPrefix: 'perfTrackChart',
        isQuarter: false,
        isFirst: true,
        init: function (empId, index) {
            var self = this;
//            self.chartObj = echarts.init(document.getElementById('perfTrackChart'+ index) );
            $.post(urls.getPerfChangeUrl, {'empId': empId}, function (rs) {
                if (_.isNull(rs)) {
                    return;
                }
                self.extendData(rs, index, empId);
            });
        },
        initChart: function (xAxisData, seriesData, index) {
            var self = this;
            var chartObj = echarts.init(document.getElementById(self.chartIdPrefix + index));
            var xAxisDataNew = [];
            $.each(xAxisData, function (i, obj) {
                var str = obj.substring(0, 4);
                str += obj.substring(5, 6);
                xAxisDataNew.push(str);
            })
            perfChartOption.xAxis[0].data = xAxisDataNew;
            perfChartOption.series[0].data = seriesData;
            chartObj.setOption(perfChartOption, true);
        },
        extendData: function (source, index, empId) {
            var self = this;
            var bool = false;

            var newSource = [];
//          $.each(source, function (i, obj) {
//          	newSource[source.length-i-1]=obj;
//          });
            for (var i = 0; i < source.length; i++) {
                newSource[i] = source[source.length - 1 - i];
            }

            var chartDataMap = {};
//            var xAxisYearData = [], xAxisQuarterData = [], seriesData = [];
            $.each(newSource, function (i, obj) {
                var chartData = chartDataMap[obj.empId] || {};
                var yearMonth = obj.yearMonth.toString();
                var month = parseInt(yearMonth.substring(4, yearMonth.length));
                if (month > 1 && month <= 3 || month > 7 && month <= 9) {
                    bool = true;
                }
                if (!chartData.xAxisYearData) {
                    chartData.xAxisYearData = [];
                }
                if (!chartData.xAxisQuarterData) {
                    chartData.xAxisQuarterData = [];
                }
                if (!chartData.seriesData) {
                    chartData.seriesData = [];
                }
                chartData.xAxisYearData.push(obj.rankingYear);
                chartData.xAxisQuarterData.push(obj.rankingQuarter);
                chartData.seriesData.push(obj.perfKey);
                chartDataMap[obj.empId] = chartData;
            });
            self.isQuarter = bool;
//            console.info(chartDataMap)
//            if(index != null){
//            	var chartData = chartDataMap[empId];
//            	self.initChart(bool ? chartData.xAxisQuarterData : chartData.xAxisYearData, chartData.seriesData,);
//            	return;
//            }
            var empArr = empId.split(',');
            $.each(empArr, function (i, item) {
                var chartData = chartDataMap[item];
                self.initChart(bool ? chartData.xAxisQuarterData : chartData.xAxisYearData, chartData.seriesData, index || i);
            });

        }
    };


    //测评信息
    var evalObj = {
        evalYear: '#evalYearId',
        dimension: '#dimensionId',
        init: function (empId, index) {
            var self = this;
            $.post(urls.getEvalReportUrl, {'empId': empId}, function (rs) {
                if (_.isEmpty(rs)) {
                    var empArr = empId.split(',');
                    self.compareDiff(index, empArr.length > 1 ? empArr : $('#contrastObj .col-xs-10 .col-xs-3'));
                    return;
                }
                self.extendData(rs, index, empId);
            });
        },
        extendData: function (source, index, empId) {
            var self = this;
            var empArr = empId.split(',');
            $.each(source, function (i, obj) {

                if (_.isEmpty(obj.abilityName)) {
                    return true;
                }
                var idx = index || _.indexOf(empArr, obj.empId);
                $($(self.evalYear).children()[idx]).text(obj.reportYear + '年度');

                var score = (Math.round(obj.score * 10000) / 100) + '%';
                var _childs = $(self.dimension).children();
                if (_childs.length == 0) {
                    self.createNewRows(obj, idx, score);
                    return true;
                }
                var bool = false;
                $.each(_childs, function (c, child) {
                    var _child = $(child);
                    if (_child.attr('dimId') == obj.abilityId) {
                        bool = true;
                        $(_child.find('.col-xs-10 .row').children()[idx]).text(score);
                        return true;
                    }
                });
                if (!bool) self.createNewRows(obj, idx, score);
            });
            self.compareDiff(index, empArr.length > 1 ? empArr : $('#contrastObj .col-xs-10 .col-xs-3'));
        },
        createNewRows: function (obj, index, score) {
            var self = this;
            var html = '<div class="row" dimId="' + obj.abilityId + '">'
                + '<div class="col-xs-2"><span>' + obj.abilityName + '</span></div>'
                + '<div class="col-xs-10">'
                + '<div class="row">';
            for (var i = 0; i < 4; i++) {
                html += '<div class="col-xs-3">' + (i == index ? score + ' ' : ' ') + '</div>';
            }
            html += '</div></div></div>';
            $(self.dimension).append(html);
        }, remove: function () {
            var self = this;
            $.each($(self.dimension).children(), function (i, child) {
                var num = 0;
                var recordMax = 0;
                var maxDivObj = null;
                $.each($(child).find(".col-xs-3"), function (j, div) {
                    var text = $(div).html();
                    $(this).removeClass('high');
                    if (text == "- " || text == "-" || text == "" || text == " ") {
                        num++;
                    } else {
                        var val = parseFloat(text);
                        if (val > recordMax) {
                            recordMax = val;
                            maxDivObj = this;
                        }
                    }
                });
                if (num >= 4) {
                    $(this).remove();
                } else if (maxDivObj != null) {
                    $(maxDivObj).addClass('high');
                }
            });
        },
        compareDiff: function (index, empArr) {
            var self = this;
            $.each($(self.dimension).children(), function (i, child) {
                var _child = $(child).find('.col-xs-10 .row').children();
                console.log(_child);
                $.each(empArr, function (e, emp) {
                    if (!_.isObject(emp)) {
                        var _one = $(_child[e]);
                        if (_.isEmpty($.trim(_one.text()))) {
                            _one.text('- ');
                        }
                        return true;
                    }
                    var _empChild = $(emp).children('button.btn-header-remove');
                    if (_empChild.length > 0) {
                        var _one = $(_child[e]);
                        if (_.isEmpty($.trim(_one.text()))) {
                            _one.text('- ');
                        }
                    }
                });

                /**
                 * 原 表格列高亮代码
                 */
//                var txtArr = _child.text().split(' ');
//                var isMax = 0, maxIdx = -1;
//                $.each(txtArr, function (t, txt) {
//                    if (_.isEmpty(txt)) {
//                        return true;
//                    }
//                    var txtNum = parseFloat(txt.substring(0, txt.length - 1));
//                    if (txtNum > isMax) {
//                    	
//                        isMax = txtNum;
//                        maxIdx = t;
//                        
//                        alert(maxIdx+" "+isMax);
//                    }
//                });
//                $(_child).removeClass('high');
//                if (maxIdx != -1) {
//                    $(_child[maxIdx]).addClass('high');
//                }

                var recordMax = 0;
                var maxDivObj = null;
                $.each($(child).find(".col-xs-3"), function (j, div) {
                    var text = $(div).html();
                    $(this).removeClass('high');
                    if (text == "- " || text == "-" || text == "" || text == " ") {

                    } else {
                        var val = parseFloat(text);
                        if (val > recordMax) {
                            recordMax = val;
                            maxDivObj = this;
                        }
                    }
                });
                if (maxDivObj != null) {
                    $(maxDivObj).addClass('high');
                }
            });
            $.each($(self.evalYear).children(), function (i, obj) {
                var _child = $(obj);
                $.each(empArr, function (e, emp) {
                    if (!_.isObject(emp)) {
                        var _one = $(_child[i]);
                        if (_.isEmpty($.trim(_one.text()))) {
                            _one.text('- ');
                        }
                        return true;
                    }
                    var _empChild = $(emp).children('button.btn-header-remove');
                    if (_empChild.length > 0) {
                        var _one = $(_child[i]);
                        if (_.isEmpty($.trim(_one.text()))) {
                            _one.text('- ');
                        }
                    }
                });
            });
        }
    };

    //工作经历
    var workChangeObj = {
        departChange: '#departChange',
        pastResume: '#pastResume',
        init: function (empId, index) {
            var self = this;
            self.requestData(urls.getDepartChangeUrl, self.departChange, index, empId);
            self.requestData(urls.getPastResumeUrl, self.pastResume, index, empId);
        },
        requestData: function (url, obj, index, empId) {
            var self = this;
            $.post(url, {'empId': empId}, function (rs) {
                if (_.isEmpty(rs)) {
                    return;
                }
                self.extendData(rs, obj, index, empId);
            });
        },
        extendData: function (source, objId, index, empId) {
            var self = this;
            var empArr = empId.split(',');
            $.each(source, function (i, obj) {
                var idx = index || _.indexOf(empArr, obj.empId);
                var _child = $($(objId).next().children()[idx]);
                var isfirst = 0;
                if (_.isEmpty($.trim(_child.html()))) {
                    isfirst = 1;
                    self.createRowFirst(_child, objId);
                }
                //为（现岗位任职时间）赋值,并对比
                if (objId == self.departChange && isfirst == 1) {
                    self.compareAssumeDate(obj.changeDate, idx);
                }
                self.createNewRow(_child, objId, obj);
            });
            self.addDiffHTML(objId);
        },
        createRowFirst: function (_obj, objId) {
            var self = this;
            var typeName = objId == self.departChange ? '部门' : '公司';
            var rowHtml = '<div class="row"><div class="col-xs-4">时间</div>'
                + '<div class="col-xs-4">' + typeName + '</div>'
                + '<div class="col-xs-4">岗位</div></div>';
            _obj.append(rowHtml);
        },
        createNewRow: function (_obj, objId, obj) {
            var self = this;
            var type = objId == self.departChange;
            var timeInterval = type ? moment(obj.changeDate).format('YYYY/MM/DD')
                : (moment(obj.entryDate).format('YYYY/MM/DD') + '-' + moment(obj.runOffDate).format('YYYY/MM/DD'));
            var timeStyle = type ? 'style="line-height:36px;" ' : '';
            var workUnit = type ? obj.organName : obj.workUnit;
            var unitStyle = workUnit.length < 6 ? 'style="line-height:36px;" ' : '';
            var unitTitle = workUnit.length > 12 ? ('title="' + workUnit + '" ') : '';
            var unitShow = workUnit.length > 12 ? workUnit.substring(0, 11) + '...' : workUnit;
            var position = obj.positionName;
            var positionStyle = position.length < 7 ? 'style="line-height:36px;" ' : '';
            var positionTitle = position.length > 12 ? ('title="' + position + '" ') : '';
            var positionShow = position.length > 12 ? position.substring(0, 11) + '...' : position;
            var rowHtml = '<div class="row">'
                + '<div class="col-xs-4" ' + timeStyle + '>' + timeInterval + '</div>'
                + '<div class="col-xs-4" ' + unitStyle + unitTitle + '>' + unitShow + '</div>'
                + '<div class="col-xs-4" ' + positionStyle + positionTitle + '>' + positionShow + '</div></div>';
            _obj.append(rowHtml);
        },
        addDiffHTML: function (objId) {
            var self = this;
            var _obj = $(objId);
            var _childs = _obj.next().children();
            var _child = _.max(_childs, function (child) {
                return $(child).children().length;
            });
            var len = $(_child).children().length;
            var height = (len * 36 + len) + 'px';
            _obj.css('lineHeight', height);
            _childs.height('');     //取消原本的高度设置
            $.each(_childs, function (i, row) {
                if (row == _child) return true;

                var _row = $(row);
                var cLen = _row.children().length;
                if (cLen > 0) {
                    for (var i = cLen; i < len; i++) {
                        var rowHtml = '<div class="row"><div class="col-xs-4"></div>'
                            + '<div class="col-xs-4"></div><div class="col-xs-4"></div></div>';
                        _row.append(rowHtml);
                    }
                } else {
                    _row.height(height);
                }
            });
        },
        compareAssumeDate: function (dateValue, index) {
            var _rowChild = $('#postionAssumeDateRow').children();
            var _rowOneChild = $(_rowChild[index]);
            _rowOneChild.text(dateValue);
            var _diffChild = _.find(_rowOneChild.siblings(), function (sib) {
                var sibTxt = $.trim($(sib).text());
                return (!_.isEmpty(sibTxt) && sibTxt != dateValue);
            });
            if (_diffChild) {
                $.each(_rowChild, function (j, newObj) {
                    var _newObj = $(newObj);
                    var newObjTxt = $.trim(_newObj.text());
                    if (!_.isEmpty(newObjTxt)) {
                        _newObj.addClass('diff');
                    }
                });
            }
        }
    };

    function removeEmpBtnEvent() {      //移除人员按钮事件
        var _this = $(this);
        var _parent = _this.parent();
        var idx = _parent.index();
        //删除已存在的empid
        var empid = $('#empId' + idx).val();
        if (empIds.indexOf(empid, 0) != -1) {
            empIds = empIds.replace(empid, '');
//        	console.log(empIds);
        }

        _parent.html($('#hiddrenObj').html());

        $(_parent.children('.btn-header-search')).bind('click', searchBtnEvent);

        var _rowObjs = $('#accordion .col-xs-10 .row');

        $.each(_rowObjs, function (i, obj) {
            var _childs = $(obj).children('.col-xs-3');
            var _child = $(_childs[idx]);
            _child.html('');
            _child.removeClass('diff').removeClass('high');
            var _sibling = _child.siblings();
            var eq = true;
            ;
            var record = "";
            var first = true;
            $.each(_sibling, function (i, obj) {
                if (eq) {
                    if (_.isEmpty($.trim($(obj).text()))) {

                    } else if (first || record == $.trim($(obj).text())) {
                        record = $.trim($(obj).text());
                    } else {
                        eq = false;
                        return;
                    }
                }
                first = false;
            });
            if (eq) {     //取消同级对比
                _sibling.removeClass('diff').removeClass('high');
            }
        });
        evalObj.remove();
        removeOfCompareDiff($('#departChange'), idx);
        removeOfCompareDiff($('#pastResume'), idx);
    }

    function removeCC() {

    }

    //移除时对比（工作经历）的不同来修改布局
    function removeOfCompareDiff(_obj, index) {
        var _childObj = _obj.next().children();
        var _childOneObj = $(_childObj[index]);
        _childOneObj.html('');
        var _objSib = _childOneObj.siblings();
        var maxNum = 0;
        $.each(_objSib, function (i, sib) {
            var _objSibChild = $(sib).children();
            if (_objSibChild.length < 1) {
                return true;
            }
            var hasChild = 0;
            $.each(_objSibChild, function (j, child) {
                var _child = $($(child).children()[0]);
                if (!_.isEmpty($.trim(_child.html()))) {
                    hasChild++;
                    return true;
                }
            });
            if (hasChild > maxNum) {
                maxNum = hasChild;
            }
        });
        if (maxNum > 0) {
            var height = (maxNum * 36 + maxNum) + 'px';
            _obj.css('lineHeight', height);
            _childObj.height(height);
        } else {
            _obj.css('lineHeight', '36px');
            _childObj.height('36px');
        }
    }

    function searchBtnEvent() {     //启动搜索人员按钮事件
        var _this = $(this);
        var idx = _this.parent().index();
        $('#searchIndex').val(idx);

        $('#searchModal').modal('show');

        $('#searchBtn').click(function () {
            var searchTxt = $.trim($('#searchTxt').val());
            if (!_.isEmpty(searchTxt)) {
                searchEmpObj.init(searchTxt);
            }
        });
    }

    //初始化页面数据对象
    var winContrastObj = {
        empId: '#empIds',
        init: function () {
            var self = this;
            var empIds = $(self.empId).val();
            if (!_.isEmpty(empIds)) {
                self.requestData(empIds);
                perfObj.init(empIds);
                evalObj.init(empIds);
                workChangeObj.init(empIds);
            } else {
                self.initHeaderHTML();
            }
        },
        requestData: function (empIds) {
            console.log(empIds);
            $.post(urls.getEmpInfoUrl, {'empIds': empIds}, function (rs) {
                if (_.isEmpty(rs)) {
                    self.initHeaderHTML();
                    return;
                }
                for (var i = 0; i < 4; i++) {
                    empAssignInfoObj.init(rs[i], i);
                }
            });
        },
        initHeaderHTML: function (index) {
            var _headerObj = $('#contrastObj .col-xs-10 .col-xs-3');
            if (!_.isUndefined(index)) {
                _headerObj = $(_headerObj[index]);
            }
            _headerObj.html($('#hiddrenObj').html());
            _headerObj.children('.btn-header-search').bind('click', searchBtnEvent);
        }
    };
    winContrastObj.init();
    winScrollObj.init();
    $.zrw_resizeModal("searchModal");
    $.zrw_resizeModal("growModal");

    $(function () {
        $(window).keydown(function (e) {
            if (e.keyCode == 13) {
                //假如焦点在文本框上,则获取文本框的值
                if (document.activeElement.id == 'searchTxt') {
                    var searchTxt = $.trim($('#searchTxt').val());
                    if (!_.isEmpty(searchTxt)) {
                        searchEmpObj.init(searchTxt);
                    }
                }
            }
        });
    });
});
