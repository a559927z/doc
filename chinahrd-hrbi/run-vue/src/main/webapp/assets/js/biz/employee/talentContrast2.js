/**
 * Created by wqcai on 15/10/10 0028.
 */
require(['jquery', 'moment', 'echarts', 'echarts/chart/line', 'bootstrap', 'ace',
    'jquery-pin', 'jgGrid', 'underscore'], function ($, moment, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        getEmpInfoUrl: webRoot + '/talentContrast/getEmpInfo.do',               //获取员工信息
        getPerfChangeUrl: webRoot + '/talentProfile/getPerfChange.do',          //获取员工绩效信息
        getJobChangeUrl: webRoot + '/talentProfile/getJobChange.do',             //获取工作异动相关信息
        getSearchEmpUrl: webRoot + '/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
        getEvalReportUrl: webRoot + '/talentContrast/getEvalReport.do',          //获取个人能力信息
        getDepartChangeUrl: webRoot + '/talentContrast/getDepartChange.do',      //获取本公司经历信息
        getPastResumeUrl: webRoot + '/talentProfile/getPastResume.do'              //获取过往履历信息
    };

    var winObj = top != window ? top.window : window;
    $(winObj.document.getElementById('tree')).next().hide();
    var isCurPage = true;                   //是否在当前页面
    var floatNumber = 168;
    var empIds = "", rowIds = {};

    var searchEmpObj = {
        gridId: '#searchEmpGrid',
        searchTxt: null,
        hasInit: false,
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
                        var html = '<span style="color: #999;">已加入</span>';
                        var empId = row.empId;
                        if (empIds.indexOf(empId, 0) < 0) {
                            html = '<a href="javascript:void(0)" data-row="' + options.rowId + '" data-index="' + row.empId + '" class="add_col" >加入</a>';
                        }
                        return html;
                    }
                }
            ],
            rownumbers: true,
            rownumWidth: 40,
            loadComplete: function (xhr) {

                $('.add_col').unbind('click').bind('click', addColClickEvent);

                //TODO 当最后一页只有一条数据的时候，jqGrid计算的高度不准确，最后一页无法滚动触发加载，现无有好的办法解决
                //$("#searchEmpGrid").parent().height(34 * xhr.records);
            },
            scroll: true
        },
        init: function (searchTxt) {
            var self = this;
            if (_.isNull(self.searchTxt)) {
                self.searchTxt = searchTxt;
                self.gridOption.postData = {'keyName': searchTxt};
                $(self.gridId).jqGrid(self.gridOption);
                self.hasInit = true;
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
        removeAddStatus: function (empId) {
            var self = this;
            if (_.isEmpty(rowIds)) return true;

            var rowId = rowIds[empId];
            if (_.isUndefined(rowId)) return false;
            var html = '<a href="javascript:void(0)" data-row="' + rowId + '" data-index="' + empId + '" class="add_col" >加入</a>';
            var $rowObj = $('#' + rowId).children(':last');
            $rowObj.html(html);
            $rowObj.find('.add_col').unbind('click').bind('click', addColClickEvent);
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
            if (_.isEmpty(empObj) || _.isUndefined(empObj)) {
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
            var imgHtml = '<div class="top-ct-circle">';
            imgHtml += '<input type="hidden" id="empId' + index + '" value="' + empObj.empId + '">';
            imgHtml += '<div class="img-rc-head-del btn-header-remove"></div>';
            imgHtml += '<img class="img-circle img-rc-head" src="' + imgPath + '"></div>';
            imgHtml += '<div class="top-div-float"><div class="top-div-name">' + empObj.userName + '</div></div>';

            var _imgParent = $($('#contrastObj .top-div')[index - 1]);
            _imgParent.html(imgHtml);
            _imgParent.find('.btn-header-remove').bind('click', removeEmpBtnEvent);

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
            var _arounds = _obj.siblings(':not(.contrast-td-one)');
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
            $('#growthLinkArea td').eq(index).empty().append(growLink);
            growLink.click(function () {
                $('#growModal').modal('show');
                $('#growModal .modal-header-text').children('span').text($(this).data('name'));
                jobChangeObj.init($(this).data('id'));
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
            var empArr = empId.split(',');
            $.each(empArr, function (i, item) {
                var chartData = chartDataMap[item];
                self.initChart(bool ? chartData.xAxisQuarterData : chartData.xAxisYearData, chartData.seriesData, index || i + 1);
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
                    self.compareDiff(index, empArr.length > 1 ? empArr : $('#contrastObj .col-xs-3'));
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
                var idx = index || _.indexOf(empArr, obj.empId) + 1;
                $($(self.evalYear).children()[idx]).text(obj.reportYear + '年度');

                var score = (Math.round(obj.score * 10000) / 100) + '%';
                var $childs = $(self.dimension).find('tr:gt(1)');   //非前2个的tr元素
                if ($childs.length == 0) {
                    self.createNewRows(obj, idx, score);
                    return true;
                }
                var bool = false;
                $.each($childs, function (c, child) {
                    var $child = $(child);
                    if ($child.attr('dimId') == obj.abilityId) {
                        bool = true;
                        $($child.children()[idx]).text(score);
                        return true;
                    }
                });
                if (!bool) self.createNewRows(obj, idx, score);
            });
            self.compareDiff(index, empArr.length > 1 ? empArr : $('#contrastObj .col-xs-3'));
        },
        createNewRows: function (obj, index, score) {
            var self = this;
            var html = '<tr dimId="' + obj.abilityId + '">'
                + '<td class="contrast-td-one">' + obj.abilityName + '</td>'
            for (var i = 1; i <= 4; i++) {
                html += '<td>' + (i == index ? score + ' ' : ' ') + '</td>';
            }
            html += '</tr>';
            $(self.dimension).append(html);
        },
        remove: function () {
            var self = this;
            $.each($(self.dimension).find('tr:gt(1)'), function (i, child) {
                var num = 0;
                var recordMax = 0;
                var maxDivObj = null;
                $.each($(child).children(":gt(0)"), function (j, td) {
                    var text = $(td).html();
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
            $.each($(self.dimension).find('tr:gt(1)'), function (i, child) {
                var _child = $(child).children(':gt(0)');
                $.each(empArr, function (e, emp) {
                    if (!_.isObject(emp)) {
                        var _one = $(_child[e]);
                        if (_.isEmpty($.trim(_one.text()))) {
                            _one.text('- ');
                        }
                        return true;
                    }
                    var _empChild = $(emp).find('div.btn-header-remove');
                    if (_empChild.length > 0) {
                        var _one = $(_child[e]);
                        if (_.isEmpty($.trim(_one.text()))) {
                            _one.text('- ');
                        }
                    }
                });

                var recordMax = 0;
                var maxDivObj = null;
                $.each(_child, function (j, td) {
                    var text = $(td).html();
                    $(this).removeClass('high');
                    if (text == "- " || text == "-" || text == "" || text == " ") {
                        return true;
                    }
                    var val = parseFloat(text);
                    if (val > recordMax) {
                        recordMax = val;
                        maxDivObj = this;
                    }
                });
                if (maxDivObj != null) {
                    $(maxDivObj).addClass('high');
                }
            });
            $.each($(self.evalYear).children(':gt(0)'), function (i, obj) {
                var $child = $(obj);
                var _text = $child.text();
                var fillTxt = _.isEmpty($.trim(_text)) ? '- ' : _text;
                $.each(empArr, function (e, emp) {
                    if (i == e){
                        if(!_.isObject(emp)){
                            $child.text(fillTxt);
                            return true;
                        }
                        var _empChild = $(emp).find('div.btn-header-remove');
                        if (_empChild.length > 0) {
                            $child.text(fillTxt);
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
                var idx = index || _.indexOf(empArr, obj.empId) + 1;
                var _child = $($(objId).children()[idx]);
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
            var unitTitle = 'title="' + workUnit + '" ';
            var unitShow = workUnit.length > 12 ? workUnit.substring(0, 11) + '...' : workUnit;
            var position = obj.positionName;
            var positionStyle = position.length < 7 ? 'style="line-height:36px;" ' : '';
            var positionTitle = 'title="' + position + '" ';
            var positionShow = position.length > 12 ? position.substring(0, 11) + '...' : position;
            var rowHtml = '<div class="row">'
                + '<div class="col-xs-4" ' + timeStyle + '>' + timeInterval + '</div>'
                + '<div class="col-xs-4 textoverflowellipsis" ' + unitStyle + unitTitle + '>' + unitShow + '</div>'
                + '<div class="col-xs-4 textoverflowellipsis" ' + positionStyle + positionTitle + '>' + positionShow + '</div></div>';
            _obj.append(rowHtml);
        },
        addDiffHTML: function (objId) {
            var self = this;
            var _obj = $(objId);
            var _childs = _obj.children(':not(.contrast-td-one)');
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
            var _rowChild = $('#postionAssumeDateRow').children(':not(.contrast-td-one)');
            var _rowOneChild = $(_rowChild[index - 1]);
            _rowOneChild.text(dateValue);
            var _diffChild = _.find(_rowOneChild.siblings(':not(.contrast-td-one)'), function (sib) {
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

    //初始化页面数据对象
    var winContrastObj = {
        empId: '#empIds',
        init: function () {
            var self = this;
            empIds = $(self.empId).val();
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
            var self = this;
            var empArr = empIds.split(',');
            $.post(urls.getEmpInfoUrl, {'empIds': empIds}, function (rs) {
                if (_.isEmpty(rs)) {
                    self.initHeaderHTML();
                    return;
                }
                for (var i = 1; i <= 4; i++) {
                    empAssignInfoObj.init(rs[i - 1], i);
                }
            });
        },
        initHeaderHTML: function (index) {
            var _headerObj = $('#contrastObj .top-div');
            if (!_.isUndefined(index)) {
                _headerObj = $(_headerObj[index - 1]);
            }
            _headerObj.html($('#img-window').html());
            _headerObj.find('.top-div-btn').bind('click', searchBtnEvent);
        }
    };
    winContrastObj.init();


    /**
     * 搜索按钮点击事件
     */
    function searchBtnEvent() {
        var _this = $(this);
        var idx = _this.parents('.top-obj').index() + 1;
        $('#search-index').val(idx);

        var _modal = $('#search-modal');
        _modal.modal('show');

        $('#search-btn').click(function () {
            var searchTxt = $.trim($('#search-txt').val());
            if (!_.isEmpty(searchTxt)) {
                searchEmpObj.init(searchTxt);
            }
        });

        $("#search-txt").keydown(function (e) {
            if (e.keyCode == 13) {
                $("#search-btn").click();
            }
        })
    }

    /**
     * 移除人员按钮事件
     */
    function removeEmpBtnEvent() {
        var _this = $(this);
        var _parent = _this.parents('.top-obj');
        var idx = _parent.index() + 1;
        //删除已存在的empid
        var empid = $('#empId' + idx).val();
        if (empIds.indexOf(empid, 0) != -1) {
            if (empIds.indexOf(empid, 0) == 0) {
                empIds = empIds.replace(empid + ',', '');
            } else {
                empIds = empIds.replace(',' + empid, '');
            }
        }

        _parent.html($('#img-window').html());

        $(_parent.find('.top-div-btn')).bind('click', searchBtnEvent);

        var _rowObjs = $('#accordion .contrast-table tr');
        $.each(_rowObjs, function (i, obj) {
            var _childs = $(obj).children();
            var _child = $(_childs[idx]);
            _child.html('');
            _child.removeClass('diff').removeClass('high');
            var _sibling = _child.siblings(':not(.contrast-td-one)');
            var eq = true;
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

        searchEmpObj.removeAddStatus(empid);
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

    function addColClickEvent(e) {
        var _this = $(this);
        var empId = _this.data('index');
        var rowId = _this.data('row');
        $.post(urls.getEmpInfoUrl, {'empIds': empId}, function (rs) {
            if (_.isEmpty(rs)) {
                return;
            }
            empIds += ',' + empId;
            rowIds[empId] = rowId;
            var empObj = rs[0];
            var idx = $('#search-index').val();
            empAssignInfoObj.init(empObj, idx);

            perfObj.init(empId, idx);
            evalObj.init(empId, idx);
            workChangeObj.init(empId, idx);
            $('#search-modal').modal('hide');
            _this.parent().html('<span style="color: #999;">已加入</span>');
        });
    }

    /*
     表头点击事件
     */
    $(".contrast-table-top").click(function () {
        if ($(this).hasClass("contrast-table-false")) {
            $(this).removeClass("contrast-table-false");
            $(this).next().show();
        } else {
            $(this).addClass("contrast-table-false");
            $(this).next().hide();
        }
    });

    /*
     菜单点击事件
     */
    $(".menu-line").click(function (e) {
        e.stopPropagation();
        var $this = $(this);
        $(".menu-line").removeClass("menu-line-select");
        $this.addClass("menu-line-select");
        if ($this.find(".menu-line-text").text() == "回顶部") {
            $(window).scrollTop(0);
            return;
        }
        //if ($(winObj).scrollTop() < 25) {
        //    console.log($('.' + $this.attr("position")).offset().top);
        //    var resultTop = ($('.' + $this.attr("position")).offset().top) - 120;
        //}
        //else {
            var resultTop = ($('.' + $this.attr("position")).offset().top) - floatNumber;
        //}
        $(window).scrollTop(resultTop);
    });

    /*
     scroll
     */
    $(window).scroll(function (e) {
        if (!isCurPage) {
            return false;
        }
        var top = $(this).scrollTop();
        //top-fa控制
        if (top >= 25) {
            if (!$(".top-fa").hasClass("top-fa-fixed")) {
                $(".top-fa").addClass("top-fa-fixed");
            }
        } else {
            if ($(".top-fa").hasClass("top-fa-fixed")) {
                $(".top-fa").removeClass("top-fa-fixed");
            }
        }
        //menu控制
        if (top >= 140) {
            $(".menu").css("top", top + 40 + "px");
        } else {
            $(".menu").css("top", 168 + "px");
        }
        var _body = this.document.body;
        //top控制menu
        if (top < 20) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(0).addClass("menu-line-select");
        }else if(top >= _body.scrollHeight - _body.clientHeight){
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(6).addClass("menu-line-select");
        }else if (top >= $(".float1").offset().top - floatNumber && top < $(".float2").offset().top - floatNumber) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(1).addClass("menu-line-select");
        } else if (top >= $(".float2").offset().top - floatNumber && top < $(".float3").offset().top - floatNumber) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(2).addClass("menu-line-select");
        } else if (top >= $(".float3").offset().top - floatNumber && top < $(".float4").offset().top - floatNumber) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(3).addClass("menu-line-select");
        } else if (top >= $(".float4").offset().top - floatNumber && top < $(".float5").offset().top - floatNumber) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(4).addClass("menu-line-select");
        } else if (top >= $(".float5").offset().top - floatNumber && top < $(".float6").offset().top - floatNumber) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(5).addClass("menu-line-select");
        } else if (top >= $(".float6").offset().top - floatNumber) {
            $(".menu-line").removeClass("menu-line-select");
            $(".menu-line").eq(6).addClass("menu-line-select");
        }
    });


    //function showErrMsg(content) {
    //    Messenger({
    //        extraClasses: 'messenger-fixed messenger-on-top messenger-on-right'
    //    }).post({
    //        message: content,
    //        type: 'error',
    //        hideAfter: 3
    //    });
    //}
});
