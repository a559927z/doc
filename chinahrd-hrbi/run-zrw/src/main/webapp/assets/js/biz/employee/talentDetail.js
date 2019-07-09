/**
 * Created by wqcai on 15/10/10 0032.
 */
require(['jquery', 'echarts', 'echarts/chart/line', 'bootstrap', 'ace', 'jquery-pin', 'jgGrid', 'underscore', 'utils'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        getAbilityUrl: webRoot + '/common/getAbility.do',                       //获取职级序列
        getJobChangeUrl: webRoot + '/talentProfile/getJobChange.do',             //获取工作异动相关信息
        getPerfChangeUrl: webRoot + '/talentProfile/getPerfChange.do',          //获取员工绩效信息
        get360EvaluationUrl: webRoot + '/talentProfile/get360Evaluation.do',          //获取360测评报告信息
        getOtherEvaluationUrl: webRoot + '/talentProfile/getOtherEvaluation.do',       //获取其它测评报告信息
        getBonusDataUrl: webRoot + '/talentProfile/getBonusData.do',             //获取员工奖惩信息 
        getTrainExpUrl: webRoot + '/talentProfile/getTrainExp.do',             //获取员工培训经历信息 
        getPastResumeUrl: webRoot + '/talentProfile/getPastResume.do',             //获取员工过往履历信息 
        getEduBgUrl: webRoot + '/talentProfile/getEduBg.do',                        //获取员工教育背景信息
        getProfTitleUrl: webRoot + '/talentProfile/getProfTitle.do',             //获取员工所获职称信息 
        getFamilyDataUrl: webRoot + '/talentProfile/getFamilyData.do'            //获取员工家庭关系信息 
    };
    var sourceEmpId = $('#empId').val();

    //加载头像-解决火狐下onerror不兼容问题
    function loadImgPath(){
    	var imgPath = $('#empImgPath').attr('src');
    	if(imgPath == null || imgPath == ''){
    		$('#empImgPath').attr('src', webRoot + '/assets/img/base/u-default.png');
    	}
    }
    loadImgPath();
    
    var isCurPage = true;   //是否在当前页面
    var loadComple = [false, false, false, false];
    var winScrollObj = {
        navId: '#myNav',
        isClick: false,
        init: function () {
            var self = this;
            var fOffsetTop = 0;
            isCurPage = true;
            var _navObj = $(self.navId);
            _navObj.width(_navObj.parent().width() * 0.98);
            var _navLi = _navObj.children('li');

            _navLi.click(function () {
                var _this = $(this);
                self.setLiClass(_this);
                self.isClick = true;
                var href = _this.children('.menu-line-select').attr('href');
                if (href.indexOf('#') != -1) {//判断是否是锚点而不是链接
                    $(window).scrollTop($(href + "-").offset().top);
                }
            });

            _navObj.pin({
                containerSelector: '#myScrollspy',
                padding: {
                    top: 1
                }
            });

            $(window).scroll(function () {
                if (!isCurPage) {
                    return;
                }
                var _this = $(this);
                var top = _this.scrollTop();
                var liArr = self.getScrollOffset(_navLi);
                var spaceHeight = 272.43;
                if (top > spaceHeight) {
                    _navObj.css('position', "fixed");
                    _navObj.css('left', "15px");
                    _navObj.css('top', '1px');
                } else {
                    _navObj.css('position', "");
                }

                if (self.isClick) {
                    self.isClick = false;
                    return;
                }
                var offetTop = 0;

                var wOffsetTop = top - fOffsetTop;
                var currObj;
                if (liArr) {
                    if (wOffsetTop <= liArr[0]) {
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
            });
        },
        setLiClass: function (_obj) {
            _obj.siblings().removeClass('active');
            _obj.addClass('active');
        },
        getScrollOffset: function (objs) {
            var newArr = [];
            if (window == null || window.parent == null) {
                isCurPage = false;
                return;
            }
            $.each(objs, function (i, obj) {
                var linkObj = $(obj).children('.menu-line-select').attr('href') + "-";
                newArr.push(Math.round($(linkObj).offset().top));
            });
            return newArr;
        }
    };


    //工作异动
    var jobChangeGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['异动日期', '异动类型', '任职部门', '岗位', '职位主序列', '职位子序列', '职级'],
        colModel: [
            {name: 'changeDate', width: 150, sortable: false, align: 'center'},
            {name: 'changeType', width: 150, sortable: false, align: 'center'},
            {name: 'organName', width: 180, sortable: false, align: 'center'},
            {name: 'positionName', width: 150, fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 150, fixed: true, sortable: false, align: 'center'},
            {name: 'subSequenceName', width: 150, fixed: true, sortable: false, align: 'center'},
            {name: 'rankName', width: 150, fixed: true, sortable: false, align: 'center'}
        ],
        scroll: true
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
                        width: 0,
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
                    lineStyle: {
                        width: 1,
                        color: '#BCBCBC'
                    }
                },
                // data: ['1级', '2级', '3级', '4级', '5级']
            },
            {
                type: "value",
                show: false,
                splitLine: false,
                min: 1,           //最小
                max: 5,         //TODO 此处根据不同公司的职级划分来定义
                splitNumber: 5,
                axisTick: {
                    show: false
                },
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
                showAllSymbol: true,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#000000'
                            },
                            formatter: function (i) {
                                var name = i.name;
                                var source = jobChangeObj.filterData;
                                if (source[name]) {
                                    return source[name].rankName + '\n' + source[name].positionName;
                                }
                            }
                        },
                        lineStyle: {
                            color: '#86D7C6'
                            //type: 'dotted'
                        },
                        color: '#86D7C6'
                    }
                },
                data: []
            }
        ]
    };

    var jobChangeObj = {
        requestSource: null,
        chartId: 'jobChangeChart',
        gridId: '#jobChangeGrid',
        init: function (empId) {
            var self = this, yAxisData = jobChangeChartOption.yAxis[0].data;
            if (!yAxisData || yAxisData.length == 0) {
                $.get(urls.getAbilityUrl, function (rs) {
                    yAxisData = _.map(rs, _.property('name'));
                    jobChangeChartOption.yAxis[0].data = yAxisData;
                    jobChangeChartOption.yAxis[1].max = yAxisData.length;
                    jobChangeChartOption.yAxis[1].splitNumber = yAxisData.length;
                });
            }

            $(self.gridId).jqGrid(jobChangeGridOption);
            $('#' + self.chartId).height(280);
            self.chartObj = echarts.init(document.getElementById(self.chartId));

            $.post(urls.getJobChangeUrl, {'empId': empId}, function (rs) {
                if (_.isEmpty(rs)) {
                    loadComple[0] = true;
                    return;
                }
                self.requestSource = rs;
                initGrid(self.gridId, jobChangeGridOption, rs);
                self.renderData(rs);
                loadComple[0] = true;
            });
        },
        renderData: function(rs){
            var self = this;
            var newTime = setTimeout(function () {
                if (_.isEmpty(jobChangeChartOption.yAxis[0].data)) {
                    self.renderData(rs);
                    return;
                }
                self.initChart(rs);
                clearTimeout(newTime);
            }, 100);
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
                    var value = parseFloat(obj.rankName.substr(-3, 3));
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
            var month = parseInt(ymd[1])
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
        }
    };


    jobChangeObj.init(sourceEmpId);

    //绩效
    var perfGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['排名年度', '单位', '部门', '岗位', '绩效', '能力等级', '综合得分'],
        colModel: [
            {name: 'rankingYear', width: 150, sortable: false, align: 'center'},
            {name: 'organParentName', width: 150, sortable: false, align: 'center'},
            {name: 'organName', width: 180, sortable: false, align: 'center'},
            {name: 'positionName', width: 150, fixed: true, sortable: false, align: 'center'},
            {name: 'perfName', width: 150, fixed: true, sortable: false, align: 'center'},
            {name: 'gradeName', width: 150, fixed: true, sortable: false, align: 'center'},
            {name: 'score', width: 150, fixed: true, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    var perfEnum = $('#performanceStr').val().split(',');
    var perfChartOption = {
        grid: {
            borderWidth: 0,
            x: 50,
            y: 50,
            x2: 50,
            y2: 50
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
                    rotate: 0
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
        chartId: 'perfChart',
        gridId: '#perfGrid',
        isQuarter: false,
        init: function (empId) {
            var self = this;
            $('#' + self.chartId).height(280);
            self.chartObj = echarts.init(document.getElementById(self.chartId));
            $.post(urls.getPerfChangeUrl, {'empId': empId}, function (rs) {
                if (_.isNull(rs)) {
                    loadComple[1] = true;
                    return;
                }
                self.extendData(rs);
                loadComple[1] = true;
            });
        },
        initChart: function (xAxisData, seriesData) {
            var self = this;
            perfChartOption.xAxis[0].data = xAxisData;
            perfChartOption.series[0].data = seriesData;
            self.chartObj.setOption(perfChartOption, true);
        },
        extendData: function (source) {
            var self = this;

            var bool = false;
            var xAxisYearData = [], xAxisQuarterData = [], seriesData = [];
            //倒叙排序
            var newSource = [];
//            $.each(source, function (i, obj) {
//            	newSource[source.length-i-1]=obj;
//            });
            for (var i = 0; i < source.length; i++) {
                newSource[i] = source[source.length - 1 - i];
            }
            $.each(newSource, function (i, obj) {
                var yearMonth = obj.yearMonth.toString();
                var month = parseInt(yearMonth.substring(4, yearMonth.length));
                if (month > 1 && month <= 3 || month > 7 && month <= 9) {
                    bool = true;
                }
                xAxisYearData.push(obj.rankingYear);
                xAxisQuarterData.push(obj.rankingQuarter);
                seriesData.push(obj.perfKey);
            });

            self.isQuarter = bool;
            initGrid(self.gridId, perfGridOption, source)
            self.initChart(bool ? xAxisQuarterData : xAxisYearData, seriesData);
        }
    };
    perfObj.init(sourceEmpId);

    var evaluationObj = {
        gridId: '#evaluationGrid',
        gridOption: {
            data: [],
            datatype: "local",
            altRows: true,  //设置表格行的不同底色
            //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
            height: 268,//268
            rowHeight: 36,
            styleUI: 'Bootstrap',
            colNames: ['能力维度', '能力层级'],
            colModel: [
                {name: 'abilityName', width: 120, sortable: false, align: 'center'},
                {name: 'abilityLvName', width: 100, sortable: false, align: 'center'}
            ],
            loadComplete: function (xhr) {
                var rows = xhr.rows;
                var _this = $(evaluationObj.gridId);
                var ids = _this.jqGrid('getDataIDs');
                for (var i = 0; i < ids.length; i++) {
                    var col = ids[i];
                    //TODO href的具体使用根据不同公司来定义
                    var html = '<a href="' + rows[i].path + '" target="_blank" style="margin:0 10px;">下载附件</a>';
                    _this.jqGrid('setRowData', col, {myac: html});
                }
            },
            scroll: true
        },
        init: function (empId) {
            var self = this;
            if (_.isUndefined(empId)) {
                return;
            }
            self.init360Report(empId);
            self.initOtherReport(empId);
        },
        init360Report: function (empId) {
            var self = this;
            $.post(urls.get360EvaluationUrl, {'empId': empId}, function (rs) {
                if (_.isEmpty(rs)) {
                    loadComple[2] = true;
                    return;
                }
                self.extendData(rs);
                loadComple[2] = true;
            });
        },
        initOtherReport: function (empId) {
            var self = this;
            $.post(urls.getOtherEvaluationUrl, {'empId': empId}, function (rs) {
                if (_.isEmpty(rs)) {
                    loadComple[3] = true;
                    return;
                }
                var otherEval = $('#otherEvaluation');
                $.each(rs, function (i, obj) {
                    //TODO href的具体使用根据不同公司来定义
                    var html = '<div class="col-xs-7"><span class="col-xs-6"><a href="' + obj.path + '" target="_blank" >' + obj.reportName + '</a></span>'
                        + '<span class="col-xs-6">' + obj.reportDate + '</span></div>';
                    otherEval.append(html);
                });
                loadComple[3] = true;
            });
        },
        extendData: function (source) {
            var self = this;
            var evalObj = [], evalYear = [], yearFilePath = [];
            $.each(source, function (i, obj) {
                var year = obj.reportYear;
                if (_.isEmpty(evalYear)) {
                    evalYear.push(year);
                    yearFilePath.push(obj.path);
                } else {
                    var idx = _.indexOf(evalYear, year);
                    if (idx == -1) {
                        evalYear.push(year);
                        yearFilePath.push(obj.path);
                    }
                }
                if (_.isEmpty(evalObj)) {
                    evalObj.push({'abilityName': obj.abilityName, 'abilityLvName': obj.abilityLvName});
                } else {
                    var bool = false;
                    if (_.isEmpty(obj.abilityName)) {
                        return true;
                    }
                    $.each(evalObj, function (j, newObj) {
                        if (obj.abilityName == newObj.abilityName && obj.abilityLvName == newObj.abilityLvName) {
                            bool = true;
                            return true;
                        }
                    });
                    if (!bool) evalObj.push({'abilityName': obj.abilityName, 'abilityLvName': obj.abilityLvName});
                }
            });

            $.each(source, function (i, obj) {
                $.each(evalObj, function (j, newObj) {
                    if (obj.abilityName == newObj.abilityName && obj.abilityLvName == newObj.abilityLvName) {
                        newObj[obj.reportYear] = obj.score;
                    }
                });
            });
            var option = _.clone(self.gridOption);
            var last = {};
            $.each(evalYear, function (i, year) {
                last[year] = yearFilePath[i];
                option.colNames.push(year + '年度');
                option.colModel.push({
                    name: year,
                    width: 100,
                    sortable: false,
                    align: 'center',
                    formatter: self.unitFormatter
                });
            });
            evalObj.push(last);
            initGrid(self.gridId, option, evalObj);
        },
        unitFormatter: function (cell, options, row) {
            if (_.isUndefined(cell)) {
                return '-';
            }
            if (_.isNumber(cell)) {
                return Math.round(cell * 10000) / 100 + '%'
            }
            return '<a href="' + cell + '">下载报告</a>';
        }
    };
    evaluationObj.init(sourceEmpId);

    //奖惩信息
    var bonusGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['类型', '奖惩名称', '授予单位', '证明人'],
        colModel: [
            {
                name: 'typeName', width: 150, sortable: false, align: 'center',
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == 0 ? '惩' : '奖';
                }
            },
            {name: 'bonusPenaltyName', width: 150, sortable: false, align: 'center'},
            {name: 'grantUnit', width: 180, sortable: false, align: 'center'},
            {name: 'witnessName', width: 150, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    //培训经历
    var trainExpGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['课程名称/项目', '完成日期', '状态', '成绩', '培训机构', '所获证书', '讲师', '备注'],
        colModel: [
            {name: 'trainName', width: 200, sortable: false, align: 'left'},
            {name: 'finishDate', width: 150, sortable: false, align: 'center'},
            {
                name: 'status', width: 80, sortable: false, align: 'center',
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == 0 ? '进行中' : '已完成';
                }
            },
            {name: 'result', width: 80, sortable: false, align: 'center'},
            {name: 'trainUnit', width: 150, sortable: false, align: 'center'},
            {name: 'gainCertificate', width: 150, sortable: false, align: 'center'},
            {name: 'teacherName', width: 150, sortable: false, align: 'center'},
            {name: 'note', width: 150, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    //过往履历
    var resumeGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['时间', '就职单位', '部门', '岗位', //'奖惩名称',
            '证明人', '联系方式', '变动原因'],
        colModel: [
            {name: 'startAndEnd', width: 160, sortable: false, align: 'center'},
            {name: 'workUnit', width: 150, sortable: false, align: 'center'},
            {name: 'departmentName', width: 150, sortable: false, align: 'center'},
            {name: 'positionName', width: 180, sortable: false, align: 'center'},
            //{name: 'bonusPenaltyName', width: 150, sortable: false, align: 'center'},
            {name: 'witnessName', width: 150, sortable: false, align: 'center'},
            {name: 'witnessContactInfo', width: 150, sortable: false, align: 'center'},
            {name: 'changeReason', width: 150, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    //教育背景
    var eduGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['学校名称', '专业', '学历', '学位', '培养方式', //'奖励信息',
            '备注'],
        colModel: [
            {name: 'schoolName', width: 150, sortable: false, align: 'center'},
            {name: 'major', width: 150, sortable: false, align: 'center'},
            {name: 'degree', width: 180, sortable: false, align: 'center'},
            {name: 'academicDegree', width: 150, sortable: false, align: 'center'},
            {name: 'developMode', width: 150, sortable: false, align: 'center'},
            //{name: 'bonus', width: 150, sortable: false, align: 'center'},
            {name: 'note', width: 150, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    //所获职称
    var profTitleGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['获得时间', '职称名称', '级别', '授予单位', '有效期'],
        colModel: [
            {name: 'gainDate', width: 150, sortable: false, align: 'center'},
            {name: 'profTitleName', width: 150, sortable: false, align: 'center'},
            {name: 'profLv', width: 180, sortable: false, align: 'center'},
            {name: 'awardUnit', width: 150, sortable: false, align: 'center'},
            {name: 'effectDate', width: 150, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    //家庭关系
    var familyGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['姓名', '亲属关系', '工作单位', '部门', '职务', '联系方式', '出生年月', '备注'],
        colModel: [
            {name: 'name', width: 150, sortable: false, align: 'center'},
            {name: 'call', width: 150, sortable: false, align: 'center'},
            {name: 'workUnit', width: 180, sortable: false, align: 'center'},
            {name: 'departmentName', width: 150, sortable: false, align: 'center'},
            {name: 'positionName', width: 150, sortable: false, align: 'center'},
            {name: 'telPhone', width: 150, sortable: false, align: 'center'},
            {
                name: 'bornDate', width: 150, sortable: false, align: 'center', formatter: function (value) {
                if (value.length >= 10) {
                    return value.substring(0, 10);
                }
                return value;
            }
            },
            {name: 'note', width: 100, sortable: false, align: 'center'},
        ],
        scroll: true
    };


    var gridParamArr = [{
        url: urls.getBonusDataUrl,
        gridOption: bonusGridOption,
        gridId: '#bonusGrid'
    }, {
        url: urls.getTrainExpUrl,
        gridOption: trainExpGridOption,
        gridId: '#trainExpGrid'
    }, {
        url: urls.getPastResumeUrl,
        gridOption: resumeGridOption,
        gridId: '#resumeGrid'
    }, {
        url: urls.getEduBgUrl,
        gridOption: eduGridOption,
        gridId: '#eduGrid'
    }, {
        url: urls.getProfTitleUrl,
        gridOption: profTitleGridOption,
        gridId: '#profTitleGrid'
    }, {
        url: urls.getFamilyDataUrl,
        gridOption: familyGridOption,
        gridId: '#familyGrid'
    }
    ];

    function formatDate(data) {
        var arr = data.split("-");
        if (arr.length >= 2) {
            return arr[0] + "/" + arr[1];
        }
        return data;
    }

    function initBottomGrids() {
        $.each(gridParamArr, function (i, item) {
            $.get(item.url, {empId: sourceEmpId}, function (rs) {
                if (item.gridId == '#resumeGrid') {
                    $.each(rs, function (j, data) {
                        data.startAndEnd = formatDate(data.entryDate) + "-" + formatDate(data.runOffDate);
                    });
                }
                initGrid(item.gridId, item.gridOption, rs);
                if (item.gridId != '#familyGrid') {
                    return true;
                }

                var childNum = _.reduce(rs, function (num, obj) {
                    return num + obj.isChild;
                }, 0)
                $('#childNum').text(childNum);
            });
        });
    }


    function initGrid(gridId, gridOption, data) {
        $(gridId).jqGrid(gridOption);
        $(gridId).setGridHeight((data.length + 1) * 33);
        $(gridId).clearGridData().setGridParam({
            data: data
        }).trigger("reloadGrid");
        resizeGrid(gridId);
    }

    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    initBottomGrids();

    winScrollObj.init();

    $(window).resize(function () {
        jobChangeObj.chartObj.resize();
        perfObj.chartObj.resize();
        resizeGrid(jobChangeObj.gridId);
        resizeGrid(perfObj.gridId);
        resizeGrid(evaluationObj.gridId);
        $.each(gridParamArr, function (i, item) {
            resizeGrid(item.gridId);
        });
    });

    $("#collapseOne label,#collapseTwo label").each(function () {
        $(this).addClass("textoverflowellipsis").attr("title", $(this).text());
    });

    //function gotoAnchor() {
    //    if (anchor == "") {
    //        return;
    //    }
    //    setTimeout(function () {
    //        var end = true;
    //        $.each(loadComple, function (i, o) {
    //            if (o == false) {
    //                end = false;
    //                return;
    //            }
    //        });
    //        if (end) {
    //            $("html,body").animate({scrollTop: $("#" + anchor).offset().top}, 300);
    //            //window.localtion.hash=anchor;
    //        } else {
    //            gotoAnchor();
    //        }
    //    }, 100);
    //}
    //
    //gotoAnchor();
});
