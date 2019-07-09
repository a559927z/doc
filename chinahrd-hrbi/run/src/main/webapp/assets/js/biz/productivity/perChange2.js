require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'bootstrap',
    'jgGrid', 'underscore', 'timeLine2', 'searchBox3', 'selection'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getTop4BliokData: webRoot + '/perChange/getPageTop4BlockData.do',//获取顶部四块区域的数据
        getTop4BliokDataDetail: webRoot + '/perChange/getPageTop4BlockDataDetail.do',//获取顶部四块区域的数据
        getPerfDistributeCount: webRoot + '/perChange/getPerfDistributeCount.do',//获取绩效分布
        getPerfDistributeEmp: webRoot + '/perChange/getPerfDistributeEmp.do',       //获取绩效分布人员明细
        getPreChangeCountData: webRoot + '/perChange/getPreChangeCountData.do',//获取绩效变化
        queryPreStarCountByMonth: webRoot + '/perChange/queryPreStarCountByMonth.do',//获取绩效变化
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        queryPerformanceDetailUrl: webRoot + '/perChange/queryPerformanceDetail.do',//查询绩效详情
        searchBoxUrl: webRoot + '/common/getPerChangeSearchBox.do',	//筛选条件信息
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',    //跳转到员工详情//季度流失人员统计信息
        getEmpCountUrl: webRoot + '/perChange/getEmpCount.do',    //部门总人数
        getBaseConfigUrl: webRoot + '/perChange/getBaseConfig.do',    //获取主动流失率基础配置信息
        updateBaseConfigUrl: webRoot + '/perChange/updateBaseConfig.do'    //更新主动流失率基础配置信息
    };
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;


    //绩效年度
    var yearMonths = $('#yearMonths').val();
    //当前绩效年度 点击下拉框后改变这里的值
    var yearMonth = yearMonths.substr(0, yearMonths.indexOf(','));
    var perWeek = parseInt($('#perWeek').val());

    var timeObj = {
        selectedYearMonth: '',//选中项
        yearMonths: [],//年数组
        init: function () {
            var self = this;
            self.selectedYearMonth = yearMonth;
            self.get();
        },
        get: function () {
            var self = this;
            //将年度的下拉框设置值
            var arr = yearMonths.split(',');
            var times = [];
            $.each(arr, function (idx, n) {
                var name = self.getymName(n, perWeek);
                times.push({k: name, v: n});
            });
            self.yearMonths = times;
        },
        getymName: function (n, _perWeek) {
            // 绩效周期的三种情况	1一年、2半年、3季度 by jxzhang
            switch (_perWeek) {
                case 1:
                    n = n.substr(0, 4);
                    n += '年';
                    break;
                case 2:
                    var end = n.substr(4, 5);
                    n = n.substr(0, 4);
                    n += (end == '06' ? '上半年' : '下半年');
                    break;
                case 3:
                    var end = n.substr(4, 5);
                    n = n.substr(0, 4);
                    n += (end == '01' ? 'Q1' : end == '04' ? 'Q2' : end == '07' ? 'Q3' : 'Q4');
                    break;
            }
            return n;
        }
    }
    timeObj.init();


    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;

        initAll(organId);
        $("#searchTxt").val("");
        performanceDetailObj.initData(reqOrgId);
    };

    /**
     * 无数据时展示
     * @param chartId
     * @param hide
     */
    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
        }
    }

    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
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

    /*
     绩效信息表格模板
     */
    var performanceInfoOption = {
        url: null,
        mtype: 'POST',
        datatype: "json",
        altRows: false,	//设置表格行的不同底色
        autowidth: true,
        height: 425,
        colNames: ['', '姓名', '当前绩效', '上次绩效', '操作'],
        colModel: [
            {
                name: 'imgPath', width: 40, sortable: false, align: 'right',
                formatter: function (value) {
                    if (_.isEmpty(value)) {
                        value = webRoot + "/assets/photo.jpg";
                    }
                    return '<img src="' + value + '" class="head-pic img-circle" width="30">';
                }
            },
            {name: 'userName', width: 100, sortable: false, align: 'left'},
            {name: 'performanceName', width: 100, sortable: false, align: 'center'},
            {name: 'prevPerformanceName', width: 90, sortable: false, align: 'center'},
            {
                name: 'empId', width: 250, sortable: false, align: 'center',
                formatter: function (value) {
                    return "<a href='javascript:void(0)' data='" + value + "' class='talent_col' >人才剖像</a>";
                }
            }
        ],

        rowNum: 10,
        rowList: [10, 20, 30],
        viewrecords: true,
        pager: '#performanceGridPage',
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $('.growUp_col').unbind().bind('click', function () {
                var _this = $(this);
                var empId = _this.attr('data');
                var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random() + "&anchor=growUpDiv";
                window.open(herf);
            });

            $('.talent_col').unbind().bind('click', function () {
                var _this = $(this);
                var empId = _this.attr('data');
                var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random();
                window.open(herf);
            });
        },

    };

    var performanceObj = {
        gridId: '#performanceGrid',
        gridObj: null,
        init: function (title, url, param) {
            var self = this;
            if (self.gridObj) {
                self.gridObj.clearGridData().setGridParam({
                    url: url,
                    postData: param
                }).trigger("reloadGrid");
            } else {
                var option = $.extend(true, {}, performanceInfoOption);
                option.url = url;
                option.postData = param;
                self.gridObj = $(self.gridId).jqGrid(option);
            }
            self.resizeGrid();
            self.initGridAndShow(title);
        },
        initGridAndShow: function (title) {
            var self = this;
            if (title != '') {
                $('#performanceModal .modal-title-small').text('(' + title + ')');
            }
            var scrollTop = $(win.document).scrollTop();
            $('#performanceModal').modal('show');
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth(560);
        }
    };
    // performanceObj.init();

    var top4BlockObj = {
        userNames: "#userNames",
        userNum: "#userCount",
        tooltip: "#tooltip",
        init: function (organId, yearMonths) {
            var self = this;
            for (var i = 1; i <= 4; i++) {
                self.requestData(organId, yearMonths, i);
            }
        },
        requestData: function (organId, yearMonths, i) {
            var self = this;
            $.get(urls.getTop4BliokData, {
                organId: organId, yearMonths: yearMonths, idx: i
            }, function (data) {
                if (!$.isEmptyObject(data)) {
                    $(self.userNum + i).text(data.k);
                    $(self.userNames + i).text((data.v != '' && data.v != null) ? data.v : '无');

                    $(self.tooltip + i).unbind('click').bind('click', function () {
                        var $this = $(this);
                        var titleTxt = $.trim($this.find('.index-common-title .index-common-title-text').text());
                        var total = Number($this.find('.body-div .accord-yj-float-value').text());
                        var params = {
                            organizationId: organId, yearMonths: yearMonths, index: i, total: total
                        }
                        performanceObj.init(titleTxt, urls.getTop4BliokDataDetail, params);
                    });
                } else {
                    $(self.userNum + i).text(0);
                    $(self.userNames + i).text('无');
                }
            });
        }
    }

    function initTop4Block(organizationId, yearMonths) {
        var userNames = "#userNames";
        var userNum = "#userCount";
        var tooltip = "#tooltip";
        $.get(urls.getTop4BliokData, {
            organizationId: organizationId, yearMonths: yearMonths
        }, function (data) {
            if (!$.isEmptyObject(data)) {
                // 不带所有明细结果集出来 by jxzhang
                $.each(data, function (i, item) {
                    $(userNum + (i + 1)).text(item.k);
                    $(userNames + (i + 1)).text(item.v != '' ? item.v : '无');
                });

                $.each($("[data-toggle='tooltip']"), function (i, item) {
                    var arrObj = data[i];
                    $(item).unbind('click').bind('click', function () {
                        var $this = $(this);
                        var titleTxt = $.trim($this.find('.index-common-title .index-common-title-text').text());
                        var total = Number($this.find('.body-div .accord-yj-float-value').text());
                        var params = {
                            organizationId: organizationId, yearMonths: yearMonths, index: i, total: total
                        }
                        performanceObj.init(titleTxt, urls.getTop4BliokDataDetail, params);
                    });
                });


            } else {
                for (var i = 0; i < 4; i++) {
                    $(userNum + (i + 1)).text(0);
                    $(userNames + (i + 1)).text('无');
                }
            }
        });
    }

    //绩效分布
    var preDistributionObj = {
        starDiv: '#preDisStar',
        barWidth: 20,
        joinEmpCount: 0,
        starChartOption: null,
        pieChartOption: null,
        yearMonth: null,
        organizationId: null,
        recordTotal: {},//记录已查询的总人数
        starChart: null,
        pieChart: null,
        first: true,
        init: function (organizationId, yearMonth, empType) {
            var self = this;
            self.pieChartOption = new Array(3);

            if (self.organizationId != organizationId) {
                //选择时间人群
                $("#preDisSelect").selection({
                    dateType: 1,
                    dateValue: timeObj.yearMonths,
                    dateSelected: [timeObj.selectedYearMonth],
                    crowdSelected: ['0'],
                    ok: function (event, data) {
                        var ym = data.date[0];
                        var cd = data.crowd.join(',') == '0' ? '' : data.crowd.join(',');
                        self.yearMonth = ym;
                        self.getStarChartData(reqOrgId, ym, empType, cd);
                    }
                });
            }

            self.yearMonth = yearMonth;
            self.organizationId = organizationId;
            self.getStarChartData(organizationId, yearMonth, empType, '');
        },
        //获取绩效分布的数据
        getPieChartData: function (organizationId, yearMonth, empType, crowds) {
            var self = this;
            self.params = {
                organizationId: organizationId,
                yearMonth: yearMonth,
                crowds: crowds,
                empType: empType
            };
            $.get(urls.getPerfDistributeCount, self.params, function (data) {
                // if (empType == 0) {
                //     self.pieChartOption[0] = data;
                // } else if (empType == 1) {
                //     self.pieChartOption[1] = data;
                // } else if (empType == 2) {
                //     self.pieChartOption[2] = data;
                // }

                if (!_.isNull(data)) {
                    self.rightPieChart(data);
                } else {
                    hideChart('rightPieChart', true);
                }
            });
        },
        //获取绩效星级人员分布的数据
        getStarChartData: function (organizationId, yearMonth, empType, crowds) {
            //getPreDistributionData
            var self = this;
            loadingChart('preDisChart');
            loadingChart('rightPieChart');
            $.get(urls.queryPreStarCountByMonth, {
                organizationId: organizationId, yearMonth: yearMonth, crowds: crowds
            }, function (data) {
                if (!$.isEmptyObject(data)) {
                    hideChart('preDisChart', false);
                    var option = self.starChart(data, empType, crowds);
                    self.starChartSetOption(option);
                    self.getPieChartData(organizationId, yearMonth, empType, crowds);
                } else {
                    hideChart('preDisChart', true);
                    return;
                }
            });
        },
        starChart: function (data, empType, crowds) {
            $(".index-jxmb-btn").removeClass('index-jxmb-btn-select');
            $('.index-jxmb-btn[page="chartview-all"]').addClass('index-jxmb-btn-select');

            var self = this;
            var option = {
                grid: {
                    x: 160,
                    y: 0,
                    borderWidth: 0
                },
                legend: {
                    y: 'bottom',
                    data: ['管理者', '员工'],
//                    selectedMode: false
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'none'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    formatter: function (d) {
                        var name = d[0].name.substr(d[0].name.indexOf('_') + 1) + "<br>";
                        $.each(d, function (i, o) {
                            name += o.seriesName + " : " + o.value + "人<br>";
                        });
                        return name;
                    }
                },
                xAxis: [
                    {
                        type: 'value',
                        show: false,
                        splitLine: {show: false},
                        axisLine: {show: false}
                    }
                ],
                yAxis: [
                    {
                        axisLabel: {
                            textStyle: {color: 'rgb(212,129,38)', fontSize: 13},
                            formatter: function (value) {
                                value = '' + value;
                                var key = value.substr(0, value.indexOf('_'));
                                var name = value.substr(value.indexOf('_') + 1, value.length);
                                var temp = '';
                                for (var i = 0; i < key; i++) {
                                    temp += '★';
                                }
                                return temp + '  ' + name;
                            }
                        },
                        type: 'category',
                        data: [],
                        splitLine: {show: false},
                        axisLine: {show: false},
                        axisTick: {show: false}
                    }
                ],
                series: [
                    {
                        name: '管理者',
                        type: 'bar',
                        stack: '总量',
                        itemStyle: {normal: {color: '#a1a1a1'}},
                        data: [],
                        barWidth: this.barWidth,
                        clickable: false
                    },
                    {
                        name: '员工',
                        type: 'bar',
                        stack: '总量',
                        clickable: false,
                        itemStyle: {
                            normal: {
                                color: 'rgb(40,137,204)', label: {
                                    show: true,
                                    formatter: function (param) {
                                        var name = param.name;
                                        var index;  //y轴序列顺序
                                        for (var i = 0; i < option.yAxis[0].data.length; i++) {
                                            if (name == option.yAxis[0].data[i]) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        var total = param.value += option.series[0].data[index];
                                        if (self.joinEmpCount != null && self.joinEmpCount != "") {
                                            return total + '人(' + Math.round(total / self.joinEmpCount * 100) + '%)';
                                        } else {
                                            return total + '人(' + 0 + '%)';
                                        }
                                    },
                                    textStyle: {color: '#1E1E1E'}
                                }
                            }
                        },
                        data: [],
                        barWidth: this.barWidth
                    }
                ]
            };
            var num = 0;
            var yDataArr = [];
            var seriesDataArr1 = [];
            var seriesDataArr2 = [];
            for (var i = 0; i < data.length; i++) {
                num += data[i].joinCount;
                yDataArr[i] = data[i].performanceKey + '_' + data[i].performanceName;
                seriesDataArr1[i] = data[i].mgrCount;
                seriesDataArr2[i] = data[i].notMgrCount;
            }
            self.joinEmpCount = num;
            $.get(urls.getEmpCountUrl, {
                organizationId: self.organizationId,
                yearMonth: self.yearMonth,
                empType: empType,
                crowds: crowds
            }, function (data) {
                $('#joinCount').text(self.joinEmpCount);
                var orgEmpCount = data.count;
                $('#notJoinCount').text(orgEmpCount - self.joinEmpCount < 0 ? 0 : orgEmpCount - self.joinEmpCount);
                if (self.recordTotal[self.organizationId + self.yearMonth]) {
                    self.recordTotal[self.organizationId + self.yearMonth][empType] = {
                        total: orgEmpCount,
                        joinCount: self.joinEmpCount
                    };
                } else {
                    self.recordTotal[self.organizationId + self.yearMonth] = {};
                    self.recordTotal[self.organizationId + self.yearMonth][empType] = {
                        total: orgEmpCount,
                        joinCount: self.joinEmpCount
                    };
                }
            });

            option.yAxis[0].data = yDataArr;
            option.series[0].data = seriesDataArr1;
            option.series[1].data = seriesDataArr2;
            self.starChartOption = option;
            return option;
        },
        starChartSetOption: function (option) {
            this.starChartObj = initEChart('preDisChart');
            this.starChartObj.setOption(option);
        },
        rightPieChart: function (data) {
            var self = this;
            if (_.isNull(data) || (data[0].joinCount == 0 && data[1].joinCount == 0)) {
                hideChart('rightPieChart', true);
                return;
            }
            hideChart('rightPieChart', false);
            $('#rightPieChart').height(function () {
                return $(this).parent().height();
            });
            var hVal = 0, lVal = 0, oVal = 0, fVal = 0;
            var total = self.joinEmpCount;
            for (var i = 0; i < data.length; i++) {
                var count = data[i].joinCount
                if (Number(data[i].performanceKey) == 1) {
                    lVal = count;
                } else {
                    hVal = count;
                }
                fVal += count;
            }
            oVal = total - fVal;
            self.pieChartObj = initEChart('rightPieChart');
            var option = {
                legend: {
                    y: 'bottom',
                    data: [{name: '高绩效', icon: "bar"},
                        {name: '其它等级绩效', icon: "bar"},
                        {name: '低绩效', icon: "bar"}
                    ]
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (d) {
                        return d.name + ' : ' + d.value + '人';
                    }
                },
                series: [{
                    type: 'pie',
                    radius: [50, 70],
                    // for funnel
                    x: '60%',
                    width: '35%',

                    data: [
                        {
                            value: 0, name: '高绩效',
                            itemStyle: {
                                normal: {
                                    color: '#5cb7f1',
                                    label: {textStyle: {color: '#1E1E1E'}}
                                }
                            }
                        },
                        {
                            value: 0, name: '其它等级绩效',
                            itemStyle: {
                                normal: {
                                    color: '#01d286',
                                    label: {textStyle: {color: '#1E1E1E'}}
                                }
                            }
                        },
                        {
                            value: 0, name: '低绩效',
                            itemStyle: {
                                normal: {
                                    color: '#e5689b',
                                    label: {textStyle: {color: '#1E1E1E'}}
                                }
                            }
                        }
                    ]
                }
                ]
            };

            option.series[0].data[0].value = hVal;
            option.series[0].data[1].value = oVal;
            option.series[0].data[2].value = lVal;
            /*option.series[0].data[0].name += ',' + hVal;
             option.series[0].data[1].name += ',' + oVal;
             option.series[0].data[2].name += ',' + lVal;*/

            self.pieChartObj.setOption(option);
            self.pieChartObj.on('click', function (e) {
                var name = e.name;
                var title = name.substr(0, name.indexOf(','));
                var params = self.params;
                params.idx = e.dataIndex;
                params.total = e.value;
                performanceObj.init(title, urls.getPerfDistributeEmp, params);
            });
        },
        //点击tab页签
        clickTab: function (type) {
            var self = this;
            var tempOption = $.extend(true, {}, self.starChartOption);
            if ($.isEmptyObject(tempOption)) {
                return;
            }
            var query = true;
            var perObj = {};
            if (self.recordTotal[self.organizationId + self.yearMonth]) {
                if (self.recordTotal[self.organizationId + self.yearMonth][type]) {
                    query = false;
                    perObj = self.recordTotal[self.organizationId + self.yearMonth][type];
                }
            } else {
                self.recordTotal[self.organizationId + self.yearMonth] = {};
            }
            var joinCount = 0;
            if (type == 1) {
                //删除员工的图例
                tempOption.legend.show = false;
                tempOption.series[0].itemStyle.normal.label = {
                    show: true,
                    formatter: function (i) {
                        joinCount += i.value;
                        return i.value + '人';
                    },
                    textStyle: {color: '#1E1E1E'}
                };
                tempOption.legend.data.splice(1, 1);
                tempOption.series.splice(1, 1);
            } else if (type == 2) {
                tempOption.legend.data[0] = '员工';
                tempOption.legend.show = false;
                tempOption.legend.data.splice(1, 1);
                tempOption.series[0] = tempOption.series[1];
                //不显示label
                tempOption.series[0].itemStyle.normal.color = 'rgb(40,137,204)';
                tempOption.series[0].itemStyle.normal.label = {
                    show: true,
                    formatter: function (i) {
                        joinCount += i.value;
                        return i.value + '人';
                    },
                    textStyle: {color: '#1E1E1E'}
                };
                tempOption.series.splice(1, 1);
            }

            self.starChartSetOption(tempOption);
            if (query) {
                $.ajax({
                    url: urls.getEmpCountUrl,
                    data: {
                        organizationId: self.organizationId,
                        yearMonth: self.yearMonth,
                        empType: type
                    },
                    dataType: 'json',
                    success: function (data) {
                        $('#joinCount').text(joinCount);
                        var orgEmpCount = data.count;
                        $('#notJoinCount').text(orgEmpCount - joinCount < 0 ? 0 : orgEmpCount - joinCount);
                        self.recordTotal[self.organizationId + self.yearMonth][type] = {
                            total: orgEmpCount,
                            joinCount: joinCount
                        };
                    }
                });
            } else {
                $('#joinCount').text(perObj.joinCount);
                $('#notJoinCount').text(perObj.total - perObj.joinCount < 0 ? 0 : perObj.total - perObj.joinCount);
            }


            if (type == 0) {
                self.rightPieChart(self.pieChartOption[0]);
            }
            //获取全部数据
            if (type == 0) {
                if (!$.isEmptyObject(self.pieChartOption[0])) {
                    self.rightPieChart(self.pieChartOption[0]);
                } else {
                    self.getPieChartData(self.organizationId, self.yearMonth, 0);
                }
            }//获取管理者数据
            else if (type == 1) {
                if (!$.isEmptyObject(self.pieChartOption[1])) {
                    self.rightPieChart(self.pieChartOption[1]);
                } else {
                    self.getPieChartData(self.organizationId, self.yearMonth, 1);
                }
                //获取员工的饼图数据
            } else if (type == 2) {
                if (!$.isEmptyObject(self.pieChartOption[2])) {
                    self.rightPieChart(self.pieChartOption[2]);
                } else {
                    self.getPieChartData(self.organizationId, self.yearMonth, 2);
                }
            }
        },
        resizeAll: function () {
            var self = this;
            if (self.starChartObj != null) {
                self.starChartObj.resize();
            }
            if (self.pieChartObj != null) {
                self.pieChartObj.resize();
            }
        }
    };


    //绩效结果变化趋势 配置对象
    var preChangeOption = {
        gap: 4.5, //间隔
        jianjiao: 16.5, //顶部和底部尖角
        width: 300,
        height: 220,
        x: 10.5,//x轴的起点
        topColor: 'rgb(92,200,43)',
        middleColor: 'rgb(41,140,208)',
        bottomColor: 'rgb(210,76,0)',
        leftFontStyle: {
            fontSize: 22,
            fontColor: '#f0f0f0',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 13,
            fontColor: '#000000',
            lineColor: '#333333',
            textAlign: 'left',
            textBaseline: 'middle'
        },
        splitLineW: 40
    };

    //绩效结果变化趋势HTML5绘制图形
    var PreChange = function (mainId, obj, option) {
        this.mainId = mainId;
        this.obj = obj;
        this.option = option;
        this.middleY = this.option.width / 2;
    };

    //创建画布
    PreChange.prototype.createCanvas = function () {
        //清空div
        $('#' + this.mainId).html('');

        var canvas = document.createElement('canvas');
        $('#' + this.mainId).append(canvas);
        this.setCanvasPos(canvas);
        return canvas.getContext('2d');
    };

    //设置画布样式
    PreChange.prototype.setCanvasPos = function (canvas) {
        canvas.setAttribute('width', this.option.width);
        canvas.setAttribute('height', this.option.height);
        // 减少reflow
//			var canvasStyle = 'width:' + this.option.width + 'px;height:' + this.option.height + 'px;position:absolute;left:0px;top:' + 10 px;
//			canvas.style.cssText += canvasStyle;
    };

    //画上面的区域
    PreChange.prototype.drawTopArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.topColor;
        ctx.strokeStyle = this.option.topColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w / 2, y - this.option.jianjiao);//画左上斜线
        ctx.lineTo(x + w, y);//画横线 往右下
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x, h + y);//画左横线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //画中间的区域
    PreChange.prototype.drawMiddleArea = function (ctx, x, y, w, h) {
        //75 172 178
        ctx.fillStyle = this.option.middleColor;
        ctx.strokeStyle = this.option.middleColor;
        ctx.beginPath();
        ctx.rect(x, y, w, h);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //话底部的区域
    PreChange.prototype.drawBottomArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.bottomColor;
        ctx.strokeStyle = this.option.bottomColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w, y);//画横线 往右
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x + w / 2, h + y + this.option.jianjiao);//画左下斜线
        ctx.lineTo(x, h + y);//画左上斜线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };
    //画文字
    PreChange.prototype.drawFont = function (ctx, x, y, str, fontStyle) {
        ctx.beginPath();
        if (fontStyle.fontColor != null) {
            ctx.fillStyle = fontStyle.fontColor;
        }
        if (fontStyle.fontSize != null) {
            ctx.font = fontStyle.fontSize + 'px 微软雅黑';
        }
        if (fontStyle.textBaseline != null) {
            ctx.textBaseline = fontStyle.textBaseline;
        }
        if (fontStyle.textAlign != null) {
            ctx.textAlign = fontStyle.textAlign;
        }
        ctx.fillText(str, x, y);
    };

    //画右边的线
    PreChange.prototype.drawRightLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x, y);
        ctx.lineTo(x + w, y);//画线
        ctx.stroke();
    };

    PreChange.prototype.init = function () {
        //获取中心位置
        var ctx = this.createCanvas();
        var w = this.option.width / 3;
        var x = this.option.x;
        var gap = this.option.gap;
        var obj = this.obj;
        if (obj == null) {
            obj = new Object();
            obj.rise = 0;
            obj.equal = 0;
            obj.down = 0;
        }
        var canvasCenterY = this.option.height / 2;
        //中间部分
        var mh = 50;//高
        var my = canvasCenterY;//获取中间位置
        my = my - mh / 2;//开始位置 左上角
        this.drawMiddleArea(ctx, x, my, w, mh);

        //底部b
        var bh = 40;//高
        var by = my + mh + gap;//中间的开始位置+中间部分的高+间隔
        this.drawBottomArea(ctx, x, by, w, bh);

        //顶部
        var th = 40;//高
        var ty = my - th - gap;//中间的开始位置+中间部分的高+间隔
        this.drawTopArea(ctx, x, ty, w, th);

        //写文字  middlearea  top bootom
        //Y的部分 还可以加上文字的
        var allCenterX = x + w / 2;
        var txtTopY = ty + th / 2;
        var txtMidY = canvasCenterY;
        var txtBotY = by + bh / 2;
        //比例
        var count = obj.rise + obj.down + obj.equal;
        var pRise = obj.rise / count;
        var pDown = obj.down / count;
        var pNoChange = 1 - pRise - pDown;

        pNoChange = isNaN(pNoChange) ? 0 : pNoChange;
        pRise = isNaN(pRise) ? 0 : pRise;
        pDown = isNaN(pDown) ? 0 : pDown;

        pRise = Math.round(pRise * 100) + '%';
        pDown = Math.round(pDown * 100) + '%';
        pNoChange = Math.round(pNoChange * 100) + '%';

        this.drawFont(ctx, allCenterX, txtMidY, pNoChange, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtTopY, pRise, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtBotY, pDown, this.option.leftFontStyle);

        //写右边的文字
        var allTextX = x + w + this.option.splitLineW;
        this.drawFont(ctx, allTextX, txtTopY, '有所进步，' + obj.rise + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtMidY, '维持现状，' + obj.equal + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtBotY, '出现下滑，' + obj.down + '人', this.option.rigthFontStyle);

        //画右边的线
        var allLineX = x + w;
        this.drawRightLine(ctx, allLineX, txtMidY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtTopY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtBotY, this.option.splitLineW);

    };
    var arrYearMonths;

    function initArrYearMonths() {
        if (yearMonths != null && yearMonths != "") {
            arrYearMonths = yearMonths.split(',');
        }
    }

    //绩效异常 配置对象
    var preUnusualOption = {
        gap: 4.5, //间隔
        jianjiao: 30, //顶部和底部尖角
        width: 300,
        height: 220,
        x: 20.5,//x轴的起点
        topColor: 'rgb(92,200,43)',
        bottomColor: 'rgb(210,76,0)',
        leftFontStyle: {
            fontSize: 22,
            fontColor: '#f0f0f0',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 13,
            fontColor: '#000000',
            lineColor: '#333333',
            textAlign: 'left',
            textBaseline: 'middle'
        },
        splitLineW: 40
    };

    //绩效异常HTML5绘制图形
    var PreUnusual = function (mainId, obj, option) {
        this.mainId = mainId;
        this.obj = obj;
        this.option = option;
        this.middleY = this.option.width / 2;
    };

    //创建画布
    PreUnusual.prototype.createCanvas = function () {
        //清空div
        $('#' + this.mainId).html('');

        var canvas = document.createElement('canvas');
        $('#' + this.mainId).append(canvas);
        this.setCanvasPos(canvas);
        return canvas.getContext('2d');
    };

    //设置画布样式
    PreUnusual.prototype.setCanvasPos = function (canvas) {
        canvas.setAttribute('width', this.option.width);
        canvas.setAttribute('height', this.option.height);
        // 减少reflow
//			var canvasStyle = 'width:' + this.option.width + 'px;height:' + this.option.height + 'px;position:absolute;left:0px;top:' + 10 px;
//			canvas.style.cssText += canvasStyle;
    };

    //画上面的区域
    PreUnusual.prototype.drawTopArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.topColor;
        ctx.strokeStyle = this.option.topColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x - 20, y);//画横线 往左
        ctx.lineTo(x + w / 2, y - this.option.jianjiao);//画左上斜线
        ctx.lineTo(x + w + 20, y);//画右下斜线 往右
        ctx.lineTo(x + w, y);//画横线 往左
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x, h + y);//画左横线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //话底部的区域
    PreUnusual.prototype.drawBottomArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.bottomColor;
        ctx.strokeStyle = this.option.bottomColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w, y);//画横线 往右
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x + w + 20, h + y);//画横线 往右
        ctx.lineTo(x + w / 2, h + y + this.option.jianjiao);//画左下斜线
        ctx.lineTo(x - 20, h + y);//画左上斜线
        ctx.lineTo(x, h + y);//画横线 往右
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };
    //画文字
    PreUnusual.prototype.drawFont = function (ctx, x, y, str, fontStyle) {
        ctx.beginPath();
        if (fontStyle.fontColor != null) {
            ctx.fillStyle = fontStyle.fontColor;
        }
        if (fontStyle.fontSize != null) {
            ctx.font = fontStyle.fontSize + 'px 微软雅黑';
        }
        if (fontStyle.textBaseline != null) {
            ctx.textBaseline = fontStyle.textBaseline;
        }
        if (fontStyle.textAlign != null) {
            ctx.textAlign = fontStyle.textAlign;
        }
        ctx.fillText(str, x, y);
    };

    //画右边的线
    PreUnusual.prototype.drawRightLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x, y);
        ctx.lineTo(x + w, y);//画线
        ctx.stroke();
    };

    PreUnusual.prototype.init = function () {
        //获取中心位置
        var ctx = this.createCanvas();
        var w = this.option.width / 3;
        var x = this.option.x;
        var gap = this.option.gap;
        var obj = this.obj;
        if (obj == null) {
            obj = new Object();
            obj.rise = 0;
            obj.equal = 0;
            obj.down = 0;
        }
        var canvasCenterY = this.option.height / 2;

        //底部b
        var bh = 50;//高
        var by = canvasCenterY + gap;//中间的开始位置+间隔
        this.drawBottomArea(ctx, x, by, w, bh);

        //顶部
        var th = 50;//高
        var ty = canvasCenterY - bh;//中间的开始位置+底部的高度
        this.drawTopArea(ctx, x, ty, w, th);

        //写文字  op bootom
        //Y的部分 还可以加上文字的
        var allCenterX = x + w / 2;
        var txtTopY = ty + th / 2;
        var txtBotY = by + bh / 2;
        //比例
        var pRise = obj.rise / obj.equal;
        var pDown = obj.down / obj.equal;

        pRise = isNaN(pRise) ? 0 : pRise;
        pDown = isNaN(pDown) ? 0 : pDown;

        pRise = Math.round(pRise * 100) + '%';
        pDown = Math.round(pDown * 100) + '%';

        this.drawFont(ctx, allCenterX, txtTopY, pRise, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtBotY, pDown, this.option.leftFontStyle);

        //写右边的文字
        var allTextX = x + w + this.option.splitLineW;
        this.drawFont(ctx, allTextX, txtTopY, '飞速提升，' + obj.rise + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtBotY, '加速跌落，' + obj.down + '人', this.option.rigthFontStyle);

        //画右边的线
        var allLineX = x + w;
        this.drawRightLine(ctx, allLineX, txtTopY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtBotY, this.option.splitLineW);

    };


    //绩效结果变化和绩效异常
    var preChangeObj = {
        organizationId: null,
        yearMonths: null,
        crowds: '',
        init: function (organizationId, yearMonths) {
            var self = this;


            if (self.organizationId != organizationId) {//是否是首次加载
                self.setPaneTitle();
                $("#selectYM").change(function (e) {
                    self.selectChange(e);
                });

                //选择时间人群
                $("#preChangeSelect").selection({
                    dateType: 1,
                    dateValue: timeObj.yearMonths,
                    dateSelected: [timeObj.selectedYearMonth],
                    crowdSelected: ['0'],
                    ok: function (event, data) {
                        var currIdx = _.indexOf(arrYearMonths, data.date[0]);
                        var prevYearMonth;
                        if (currIdx != -1 && currIdx != arrYearMonths.length - 1) {
                            prevYearMonth = arrYearMonths[currIdx + 1];
                        }
                        self.yearMonths = data.date[0] + (prevYearMonth ? "," + prevYearMonth : '');
                        self.crowds = data.crowd.join(',') == '0' ? '' : data.crowd.join(',');
                        self.getPreChangeData(0);
                    }
                });

                //选择时间人群
                $("#preUnusualSelect").selection({
                    dateType: 1,
                    dateValue: timeObj.yearMonths,
                    dateSelected: [timeObj.selectedYearMonth],
                    crowdSelected: ['0'],
                    ok: function (event, data) {
                        var currIdx = _.indexOf(arrYearMonths, data.date[0]);
                        var prevYearMonth;
                        if (currIdx != -1 && currIdx != arrYearMonths.length - 1) {
                            prevYearMonth = arrYearMonths[currIdx + 1];
                        }
                        self.yearMonths = data.date[0] + (prevYearMonth ? "," + prevYearMonth : '');
                        self.crowds = data.crowd.join(',') == '0' ? '' : data.crowd.join(',');
                        self.getPreChangeData(1);
                    }
                });
            }
            self.organizationId = organizationId;
            self.yearMonths = yearMonths;
            self.getPreChangeData(-1);
        },
        //index控制改变时间人群时只刷新一模块：-1二块都刷新，0左边模块，1右边模块
        getPreChangeData: function (index) {
            var self = this;
            $.each($(".mainArea"), function (i) {
                var chartId = $($(this).find('.chartArea').children()).attr('id');
                if (index == -1 || index == i) loadingChart(chartId);

            });
            var params = {
                organizationId: self.organizationId, yearMonths: self.yearMonths, crowds: self.crowds
            };

            $.get(urls.getPreChangeCountData, params, function (data) {
                if (!$.isEmptyObject(data)) {
                    if (index == -1 || index == 0) {
                        if (!$.isEmptyObject(data.change)) {
                            hideChart('preChange', false);
                            self.setPreChangeData(data.change);
                        } else {
                            hideChart('preChange', true);
                        }
                    }
                    if (index == -1 || index == 1) {
                        if (!$.isEmptyObject(data.bigChange)) {
                            hideChart('preUnusual', false);
                            self.setPreExceptionData(data.bigChange);
                        } else {
                            hideChart('preUnusual', true);
                        }
                    }
                } else {
                    $.each($(".mainArea"), function (i) {
                        var chartId = $($(this).find('.chartArea').children()).attr('id');
                        if (index == -1 || index == i) hideChart(chartId, true);
                    });
                }
            });
        },
        getymName: function (n, _perWeek) {
            // 绩效周期的三种情况	1一年、2半年、3季度 by jxzhang
            switch (_perWeek) {
                case 1:
                    n = n.substr(0, 4);
                    n += '年';
                    break;
                case 2:
                    var end = n.substr(4, 5);
                    n = n.substr(0, 4);
                    n += (end == '06' ? '上半年' : '下半年');
                    break;
                case 3:
                    var end = n.substr(4, 5);
                    n = n.substr(0, 4);
                    n += (end == '01' ? 'Q1' : end == '04' ? 'Q2' : end == '07' ? 'Q3' : 'Q4');
                    break;
            }
            return n;
        },
        setPaneTitle: function () {
            var self = this;
            //将年度的下拉框设置值
            var arr = yearMonths.split(',');
            var html = "";
            $.each(arr, function (idx, n) {
                var selectFlag = (idx == 0 ? 'selected' : '');
                n = self.getymName(n, perWeek);
                //设置页面下面两块区域标题上的的年月
                if (idx == 0) {
                    $('.titleYearMonth').text(n);
                }
                html += '<option value="' + n + '" ' + selectFlag + '>' + n + '</option>';
            });
            $("#selectYM").html(html);
        },
        //切换年月
        selectChange: function (e) {
            var self = this;
            var idx = e.target.selectedIndex;
            var ym = arrYearMonths[idx];
            //设置标题
            $('.titleYearMonth').text(self.getymName(ym, perWeek));
            //设置绩效分布的数据
            preDistributionObj.init(reqOrgId, ym, 0);
            //设置绩效变化的数据
            var yms = yearMonths.substr(yearMonths.indexOf(ym), yearMonths.length);
            preChangeObj.init(reqOrgId, yms);
        },
        setPreChangeData: function (obj) {
            var c = new PreChange('preChange', obj, preChangeOption);
            c.init();
        },
        setPreExceptionData: function (obj) {
            var u = new PreUnusual('preUnusual', obj, preUnusualOption);
            u.init();
            return false;
            var preRiseHeight = 24;
            var preDownHeight = 24;
            var preRiseNumText = 0;
            var preDownNumText = 0;
            //百分比
            var preRise = $('#preRise');
            var preDown = $('#preDown');
            //具体数字
            var preRiseNum = '#preRiseNum';
            var preDownNum = '#preDownNum';
            //比率
            var pRise = 0;
            var pDown = 0;
            if (obj != null && obj.equal != 0) {
                //具体的数值
                preRiseNumText = obj.rise;
                preDownNumText = obj.down;
                //比率
                pRise = (obj.rise / obj.equal);
                pDown = obj.down / obj.equal;

                pRise = isNaN(pRise) ? 0 : pRise;
                pDown = isNaN(pDown) ? 0 : pDown;

                //先获取父div的高度,也就是最大高度，在按照高度进行计算
                var h = $('.preException').css('height');
                h = h.substr(0, h.length - 2);
                preRiseHeight = h * pRise;
                preDownHeight = h * pDown;
                //防止比率太小，显示不出来文字
                if (preRiseHeight < 24) {
                    preRiseHeight = 24;
                }
                if (preDownHeight < 24) {
                    preDownHeight = 24;
                }
            }
            //设置DIV高度
            preRise.css({'height': preRiseHeight + 'px', 'line-height': preRiseHeight + 'px'});
            preDown.css({'height': preDownHeight + 'px', 'line-height': preDownHeight + 'px'});
            //设置百分比
            preRise.text(Math.round(pRise * 100) + '%');
            preDown.text(Math.round(pDown * 100) + '%');
            //设置具体的数值
            $(preRiseNum).text(preRiseNumText);
            $(preDownNum).text(preDownNumText);
        }
    };


    /***
     * 人员绩效明细Option
     * @type {{url: *, lazy: boolean, attach: *[], onClick: searchBoxConfig.onClick, loadComple: searchBoxConfig.loadComple}}
     */
    var selectDateArr = [{name: "yearMonth", options: []}];
    $.each(yearMonths.split(','), function (idx, n) {
        selectDateArr[0].options.push({key: n, value: preChangeObj.getymName(n, perWeek)});
    });
    var searchBoxConfig = {
        url: urls.searchBoxUrl, lazy: true,
        height: 300,
        attach: [{label: "时间", type: "select", data: selectDateArr}],
//        onClick: function (id, name, type) {
//        	runOffDetailObj.initGrid(reqOrgId);
//        },
        // 重写组件里的onClick事件
        onClick: function () {
            performanceDetailObj.getRequestData(null);
        }, loadComple: function (o) {
            $("#searchBtn").click(function () {
                var searchTxt = $.trim($("#searchTxt").val());
                if (searchTxt != "") {
                    var _gridId = performanceDetailObj.gridId;
                    $(_gridId).clearGridData();
                    $(_gridId).showCol("performanceName2");
                    $(_gridId).showCol("performanceName3");
                    //var orgId = $("#recordSelectTreeOrgId").val();
                    $(_gridId).setGridParam({
                        postData: {organizationId: reqOrgId, keyName: searchTxt, queryType: 1}
                    }).trigger("reloadGrid");
                    performanceDetailObj.resizeGrid();
                }
            });
            $("#searchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#searchBtn").click();
                }
            })
        }
        // height : 420
    };
    //为表格设置一个高度
    var tableHeight = 350;
    var performanceDetailGridOption = {
        url: urls.queryPerformanceDetailUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
//        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,//268
        colNames: ['姓名', '当期绩效', '上次绩效', '上上次绩效', '所属组织', '岗位', '职位序列', '职级'],
        colModel: [
            {
                name: 'userNameCh',
                index: 'userNameCh',
                align: 'center',
                sortable: false,
                width: 120,
                
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }

                    return "<a href='javascript:void(0)' data='" + row.empId + "' class='talent_col' >" + value + "</a>";
                }
            },
            {name: 'performanceName1', index: 'performanceName1', width: 120, sortable: false, align: 'center',
            	formatter: function(value) {
            		if (_.isEmpty(value)) {
                        return "-";
                    }
            		return value;
            	}
            },
            {name: 'performanceName2', index: 'performanceName2', width: 120, sortable: false, align: 'center',
            	formatter: function(value) {
            		if (_.isEmpty(value)) {
                        return "-";
                    }
            		return value;
            	}	
            },
            {name: 'performanceName3', index: 'performanceName3', width: 120, sortable: false, align: 'center',
            	formatter: function(value) {
            		if (_.isEmpty(value)) {
                        return "-";
                    }
            		return value;
            	}	
            },
            {name: 'organizationName', index: 'organizationName', width: 150, sortable: false, align: 'center'},
            {name: 'positionName', index: 'positionName', width: 170, sortable: false, align: 'center'},
            {name: 'sequenceName', index: 'sequenceName', width: 145, sortable: false, align: 'center'},
            {name: 'rankName', index: 'rankName', width: 120, sortable: false, align: 'center'},
        ],
        rownumbers: true,
        rownumWidth: 50,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#performanceDetailSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {

            $('.talent_col').unbind().bind('click', function () {
                var _this = $(this);
                var empId = _this.attr('data');
                var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random();
                window.open(herf);
            });
            $("#performanceDetailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
        }
    };

    /**
     * 人员绩效明细
     */
    var performanceDetailObj = {
        searchBoxId: "#searchBox",
        gridId: '#performanceDetailGrid',
        clearCondBtnId: '#clearConditionBtn',
        resultData: null,
        isInit: false,
        init: function (organId) {
            var self = this;
            self.searchBox = $(self.searchBoxId).searchBox3(searchBoxConfig);
            if (_.isUndefined(arrYearMonths)) {
                return false;
            }
            performanceDetailGridOption.postData = {organizationId: organId, yearMonth: arrYearMonths[0], queryType: 2};
            $(self.gridId).jqGrid(performanceDetailGridOption);
            this.isInit = true;
        },
        getRequestData: function (organId) {
            var self = this;
            // 存起。重新选seachBox的参数时，没有带上的机构Id
            if (organId != null) {
                self.organId = organId;
            }
            var _organId = self.organId;

            var params = $.extend(true, {}, self.searchBox.getSelectData(), {
                organizationId: _organId,
                keyName: null,
                queryType: 2
            });

            $(self.gridId).clearGridData();
            var t = params.yearMonth;
            var index = -1;
            $.each(arrYearMonths, function (i, o) {
                if (o == t) {
                    index = i;
                }
            });
            $(self.gridId).showCol("performanceName2");
            $(self.gridId).showCol("performanceName3");
            if ((index + 1) == arrYearMonths.length) {
                $(self.gridId).hideCol("performanceName2");
                $(self.gridId).hideCol("performanceName3");
            }
            if ((index + 2) == arrYearMonths.length) {
                $(self.gridId).hideCol("performanceName3");
            }
            $(self.gridId).setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        initData: function (organId) {
            var self = this;
            if (!self.isInit) {
                self.init(organId);
                return;
            }
            if (self.searchBox) {
                self.searchBox.clear();
            }
            self.resizeGrid();
            self.getRequestData(organId);
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#performanceDetailTable').width() * 0.98);
            // $("performanceDetailSel_center").width($('#runOffDeailTable').width() * 0.98);
        }
    };

    /**
     * 基础配置信息
     * @type {{bool: boolean, init: baseConfigObj.init, initConfigHtml: baseConfigObj.initConfigHtml, getRequestData: baseConfigObj.getRequestData, updateRequestData: baseConfigObj.updateRequestData}}
     */
    var baseConfigObj = {
        bool: false,
        init: function () {
            var self = this;
            if (!self.bool) {
                self.bool = true;
                self.getRequestData();
            }
        },
        initConfigHtml: function (data) {
            var self = this;
            var lowPerfman = data.lowPerfman, heightPerfman = data.heightPerfman, perfmanWeek = data.perfmanWeek;
            $('#perfmanWeekObj').val(perfmanWeek);
            if (lowPerfman.length > 0) {
                var minLowPer = lowPerfman[0], maxLowPer = 0;
                $.each(lowPerfman, function (i, item) {
                    if (item > maxLowPer) {
                        maxLowPer = item;
                    }
                    if (item < minLowPer) {
                        minLowPer = item;
                    }
                });
                $('#minLowPerObj').val(minLowPer);
                $('#maxLowPerObj').val(maxLowPer);
            }
            if (heightPerfman.length > 0) {
                var minHeightPer = heightPerfman[0], maxHeightPer = 0;
                $.each(heightPerfman, function (i, item) {
                    if (item > maxHeightPer) {
                        maxHeightPer = item;
                    }
                    if (item < minHeightPer) {
                        minHeightPer = item;
                    }
                });
                $('#minHeightPerObj').val(minHeightPer);
                $('#maxHeightPerObj').val(maxHeightPer);
            }
        },
        getRequestData: function () {
            var self = this;
            $.post(urls.getBaseConfigUrl, function (rs) {
                if (!_.isNull(rs)) {
                    self.initConfigHtml(rs);
                }
            });
        },
        updateRequestData: function (params) {
            $.post(urls.updateBaseConfigUrl, params, function (rs) {
                if (rs.type) {
                    closeShade();
                    window.location.reload();
                }
            });
        }
    }

    /***
     * 主动流失率配置窗口
     */
    $(".leftListSet").click(function () {
        baseConfigObj.init();
        $(".sz-window").css("display", "table");
        $(".shade").show();

        //确定按钮
        $(".sz-window .initDragBtn").unbind('click').bind('click', function () {
            var perfmanWeek = $('#perfmanWeekObj').val();
            var minHeightPer = Math.round($('#minHeightPerObj').val());
            var maxheightPer = Math.round($('#maxHeightPerObj').val());
            var minLowPer = Math.round($('#minLowPerObj').val());
            var maxLowPer = Math.round($('#maxLowPerObj').val());

            if (minHeightPer > maxheightPer) {
                $('#maxHeightPerObj')[0].focus();
                return false;
            }
            if (minLowPer > maxLowPer || maxLowPer >= minHeightPer) {
                $('#maxLowPerObj')[0].focus();
                return false;
            }
            var heightPer = [], lowPer = [];
            var h = 0, l = 0;
            for (var i = minHeightPer; i <= maxheightPer; i++) {
                heightPer[h] = i;
                h++;
            }
            for (var e = minLowPer; e <= maxLowPer; e++) {
                lowPer[l] = e;
                l++;
            }
            baseConfigObj.updateRequestData({
                heightPer: heightPer.join(','),
                lowPer: lowPer.join(','),
                perfmanWeek: perfmanWeek
            });
        });

        //取消按钮
        $(".sz-window .cancelDragBtn").unbind('click').bind('click', function () {
            closeShade();
        });
    });


    /*
     筛选条件点击事件
     */
    $(".condition-btn").click(function () {
        if ($(this).hasClass("condition-btn-selected")) return;

        $(this).parents(".condition-body-list").find(".condition-btn").removeClass("condition-btn-selected");
        $(this).addClass("condition-btn-selected");
    });

    /*
     筛选条件选中鼠标触摸事件
     */
    $(".condition-btn-too").mouseover(function () {
        if ($(this).hasClass("condition-btn-selected")) return;

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

    $(".closeIcon").click(function () {
        closeShade();
    });

    /*
     筛选条件取消事件
     */
    $(".condition-btn-too").click(function () {
        if ($(this).find(".condition-btn-too-icon").length == 1) return;

        $(this).find(".condition-btn-too-icon").remove();
        $(this).css("paddingLeft", "10px");
        $(this).css("paddingRight", "10px");
        $(this).parents(".condition-body-list").find(".condition-btn").removeClass("condition-btn-selected");
        $(this).parents(".condition-body-list").find(".condition-btn").eq(0).addClass("condition-btn-selected");
    });
    /*
     精简筛选条件
     */
    $("#conditionBtn").click(function () {
        if ($(this).attr("switch") == "true") {
            $(".condition-body-list-too").hide();
            $(this).attr("switch", "false");
        } else if ($(this).attr("switch") == "false") {
            $(".condition-body-list-too").show();
            $(this).attr("switch", "true");
        }
    });

    /*
     绩效分布 点击切换函数
     */
    $(".index-jxmb-btn").click(function () {
        var _self = $(this);
        $(".index-jxmb-btn").removeClass("index-jxmb-btn-select");
        _self.addClass("index-jxmb-btn-select");

        if (!preDistributionObj) return false;

        var $page = _self.attr("page");

        if ($page == 'chartview-g') {
            preDistributionObj.clickTab(1);
        } else if ($page == 'chartview-y') {
            preDistributionObj.clickTab(2);
        } else {
            preDistributionObj.clickTab(0);
        }
    });

    function closeShade() {
        $(".ct-drag").hide();
        $(".shade").hide();
    }

    function initAll(id) {
        timeLineObj.init(id);
        initArrYearMonths();
        //界面功能
        top4BlockObj.init(id, yearMonths);
        preDistributionObj.init(id, yearMonth, 0);
        preChangeObj.init(id, yearMonths);
    }

    initAll(reqOrgId);

    /*切换左边导航*/
    $(".leftListDiv").click(function () {
        var _this = $(this);
        if (_this.hasClass("selectList")) return;

        $(".rightBodyPage").hide();
        $(".leftListDiv").removeClass("selectList");

        var $page = _this.attr("page");
        $("#" + $page).show();
        _this.addClass("selectList");
        if ($page == 'page-one') {
            preDistributionObj.resizeAll();
        } else {
            performanceDetailObj.initData(reqOrgId);
        }
    });
    
    $(window).resize(function () {
        var _page = $('.leftListDiv.selectList').attr('page');
        if (_page == 'page-two') {
        	performanceDetailObj.resizeGrid();
        } else {
        	preDistributionObj.resizeAll();
        }
    });

});