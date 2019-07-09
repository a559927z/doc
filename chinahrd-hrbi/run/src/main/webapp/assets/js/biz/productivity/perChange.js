require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie',
    'jgGrid', 'underscore', 'organTreeSelector', 'timeLine', 'searchBox3', 'resize'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getTop4BliokData: webRoot + '/perChange/getPageTop4BlockData',//获取顶部四块区域的数据
        getPreDistributionData: webRoot + '/perChange/getPreDistributionData',//获取绩效分布
        getPreChangeCountData: webRoot + '/perChange/getPreChangeCountData',//获取绩效变化
        queryPreStarCountByMonth: webRoot + '/perChange/queryPreStarCountByMonth',//获取绩效变化
        getEmpCountByOrganId: webRoot + '/perChange/getEmpCountByOrganId',//查询本部门的人员数
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        queryPerformanceDetailUrl: webRoot + '/perChange/queryPerformanceDetail.do',//查询绩效详情
        searchBoxUrl: webRoot + '/common/getPerChangeSearchBox.do',	//筛选条件信息
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',    //跳转到员工详情//季度流失人员统计信息
        getEmpCountUrl: webRoot + '/perChange/getEmpCount.do'    //部门总人数
    };

    var treeSelector = null;

//    //部门人数总数
//    var orgEmpCount = $('#empCount').val();
    $(win.document.getElementById('tree')).next().hide();
    var reqOrgId = $('#reqOrganId').val();
    var reqOrgText = $('#reqOrganName').val();

    //绩效年度
    var yearMonths = $('#yearMonths').val();
    //当前绩效年度 点击下拉框后改变这里的值
    var yearMonth = yearMonths.substr(0, yearMonths.indexOf(',')).trim();

    function generateInput(orgId) {
        var recordInput = $("<input type='hidden' id='recordSelectTreeOrgId'>");
        $("body").append(recordInput);
        $("#recordSelectTreeOrgId").val(orgId);
    }

    generateInput(reqOrgId);
    function initOrganTree() {
        treeSelector = $("#tree").organTreeSelector({
            multiple: false,
            value: {
                id: reqOrgId,
                text: reqOrgText
            },
            showSelectBtn: false,
            onSelect: function (id, texts) {
                performanceDetailObj.searchBox.clear();
                $("#searchTxt").val("");
                $("#recordSelectTreeOrgId").val(id);
                initAll(id);
                initTimeLine(id);
                //在切换组织机构的时候，将本部门的人数查询出来
                //已在下面具体查询时处理
//                $.ajax({
//                    url: urls.getEmpCountByOrganId,
//                    data: {
//                        organizationId: id
//                    },
//                    dataType: 'json',
//                    success: function (empCount) {
//                        $('#empCount').val(empCount);
//                        initAll(id);
//                        initTimeLine(id);
//                    }
//                });
            }
        });
    }

    function initTimeLine(organizationId) {
        var quotaId = $('#quotaId').val();
        $.post(urls.memoUrl, {
            'quotaId': quotaId,
            'organizationId': organizationId
        }, function (rs) {
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
                            initTimeLine(organizationId);		//重新加载
                        }
                    });
                }
            };
            $('#timeLine').timeLine(options);	//初始化
        });
    }

    function initTop4Block(organizationId, yearMonths) {
        var userNames = "#userNames";
        var userNum = "#userCount";
        var tooltip = "#tooltip";
        $("[data-toggle='tooltip']").tooltip({html: true});
        $.ajax({
            url: urls.getTop4BliokData,
            data: {
                organizationId: organizationId, yearMonths: yearMonths
            },
            dataType: 'json',
            success: function (data) {
                if (!$.isEmptyObject(data)) {
                    for (var i = 0; i < data.length; i++) {
                        var arr = data[i];
                        $(userNum + (i + 1)).text(data[i].length);
                        $(userNames + (i + 1)).text(getUserNamesStr(arr, 2));
                        $(tooltip + (i + 1)).attr("data-original-title", getAllUserNamesStr(arr, 3));
                    }
                } else {
                    $('.userCount').text('暂无数据');
                }
            }
        });

    }

    //获取部分人员 最多显示max个
    function getUserNamesStr(arr, max) {
        var str = '';
        for (var j = 0; j < arr.length; j++) {
            str += arr[j].userName + '、';
            if (j == 1) {
                break;
            }
        }
        str = str.substring(0, str.length - 1);
        if (arr.length >= max) {
            str += '...';
        }
        return str;
    }

    //获取所有人员 一行显示最多数 splitNum
    function getAllUserNamesStr(arr, splitNum) {
        var str = '';
        for (var j = 0; j < arr.length; j++) {
            str += arr[j].userName + '、 ';
            //三个人一行，多了换行
            if ((j + 1) % splitNum == 0) {
                str += '<br>';
            }
            var a = arr.length - 1;
            if (j == a) {
                // 这里的3是和上面的有关的
                str = str.substr(0, str.length - 2);//删除 '、 '
                if ((j + 1) % splitNum == 0) {//删除<br>
                    str = str.substr(0, str.length - 4);
                }
            }
        }
        return str;
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
        init: function (organizationId, yearMonth, empType, clickTree) {
            var self = this;
            //this.drawStat(5);
            self.pieChartOption = new Array(3);

            if (!this.first && clickTree) {//通过点击组织结构树
                var idx = $('#selectYM').get(0).selectedIndex;
                var ym = arrYearMonths[idx];
                ym = ym.trim();
                yearMonth = ym;
            }
            this.first = false;
            if (yearMonth != null && yearMonth != "") {
                self.yearMonth = yearMonth;
                self.organizationId = organizationId;
                self.getPieChartData(organizationId, yearMonth, empType);
                self.getStarChartData(organizationId, yearMonth, empType);
            } else {
                return;
            }
            //监听事件
            $('a[data-toggle="tab"]').unbind('show.bs.tab').on('show.bs.tab', function (e) {
                var t = e.target; // 激活的标签页
                if (t.id == "all") {
                    self.clickTab(0);
                } else if (t.id == "manager") {
                    self.clickTab(1);
                } else if (t.id == "emp") {
                    self.clickTab(2);
                }
            });
        },
        //获取绩效分布的数据
        getPieChartData: function (organizationId, yearMonth, empType) {
            var self = this;
            $.ajax({
                url: urls.getPreDistributionData,
                data: {
                    organizationId: organizationId, yearMonth: yearMonth, empType: empType
                },
                dataType: 'json',
                success: function (data) {

                    if (empType == 0) {
                        self.pieChartOption[0] = data;
                    } else if (empType == 1) {
                        self.pieChartOption[1] = data;
                    } else if (empType == 2) {
                        self.pieChartOption[2] = data;
                    }
                    if (data == null || $.isEmptyObject(data)
                        || ($.isEmptyObject(data[0]) && $.isEmptyObject(data[1]) && $.isEmptyObject(data[2]))
                    ) {
                        return;
                    } else {
                        self.rightPieChart(data);
                    }
                }
            });
        },
        //获取绩效星级人员分布的数据
        getStarChartData: function (organizationId, yearMonth, empType) {
            //getPreDistributionData
            var self = this;
            $.ajax({
                url: urls.queryPreStarCountByMonth,
                data: {
                    organizationId: organizationId, yearMonth: yearMonth
                },
                dataType: 'json',
                success: function (data) {
                    if (!$.isEmptyObject(data)) {
                        var option = self.starChart(data, empType);
                        self.starChartSetOption(option);
                    } else {
                        return;
                    }
                }
            });
        },
        starChart: function (data, empType) {
            $("#preDistributionChart a[id='all']").tab('show');
            var self = this;
            var option = {
                grid: {
                    x: 160,
                    y: 0,
                    borderWidth: 0
                },
                legend: {
                    y: 'bottom',
                    data: ['管理者', '员工']
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
                            textStyle: {color: '#ef831f', fontSize: 13},
                            formatter: function (value) {
//	                                        console.log(value);
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
                    },
                    {
                        name: '员工',
                        type: 'bar',
                        stack: '总量',
                        itemStyle: {
                            normal: {
                                color: '#23C6C8', label: {
                                    show: true,
                                    formatter: function (a, b, c, d) {
                                        var name = b;
                                        var index;  //y轴序列顺序
                                        for (var i = 0; i < option.yAxis[0].data.length; i++) {
                                            if (name == option.yAxis[0].data[i]) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        var total = c += option.series[0].data[index];
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
                        data: []
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
            $.ajax({
                url: urls.getEmpCountUrl,
                data: {
                    organizationId: self.organizationId,
                    yearMonth: self.yearMonth,
                    empType: empType
                },
                dataType: 'json',
                success: function (data) {

                    $('#joinCount').text(self.joinEmpCount);
                    var orgEmpCount = data.count;
                    $('#notJoinCount').text(orgEmpCount - self.joinEmpCount);
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

                }
            });


            option.yAxis[0].data = yDataArr;
            option.series[0].data = seriesDataArr1;
            option.series[1].data = seriesDataArr2;
            self.starChartOption = option;
            return option;
        },
        starChartSetOption: function (option) {
            this.starChartObj = echarts.init(document.getElementById('preDisChart'));
            this.starChartObj.setOption(option);
        },
        rightPieChart: function (obj) {
//        	console.log(obj[0].length);	//高
//        	console.log(obj[1].length); //中
//        	console.log(obj[2].length); //低
            if (obj == null || (obj[0].length == 0 && obj[1].length == 0 && obj[2].length == 0)) {
                $('#rightPieChart').hide();
                $('#noData').show();
                return;
            } else {
                $('#rightPieChart').show();
                $('#noData').hide();
            }
            this.pieChartObj = echarts.init(document.getElementById('rightPieChart'));
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: function (a, b, c, d) {
                        var aa = a.dataIndex;
                        //每两个换行一次
                        var str = getAllUserNamesStr(obj[aa], 5);
                        return str;
                    }
                },
                series: [{
                    type: 'pie',
                    radius: [50, 70],
                    // for funnel
                    x: '60%',
                    width: '35%',
                    funnelAlign: 'left',
                    max: 1048,
                    data: [
                        {
                            value: 0, name: '高绩效',
                            itemStyle: {
                                normal: {
                                    color: '#99ff66',
                                    label: {textStyle: {color: '#1E1E1E'}}
                                }
                            }
                        },
                        {
                            value: 0, name: '其它等级绩效',
                            itemStyle: {
                                normal: {
                                    color: '#bfbfbf',
                                    label: {textStyle: {color: '#1E1E1E'}}
                                }
                            }
                        },
                        {
                            value: 0, name: '低绩效',
                            itemStyle: {
                                normal: {
                                    color: '#FF0000',
                                    label: {textStyle: {color: '#1E1E1E'}}
                                }
                            }
                        }
                    ]
                }
                ]
            };
            option.series[0].data[0].value = obj[0].length;
            option.series[0].data[1].value = obj[1].length;
            option.series[0].data[2].value = obj[2].length;
            option.series[0].data[0].name += ',' + obj[0].length;
            option.series[0].data[1].name += ',' + obj[1].length;
            option.series[0].data[2].name += ',' + obj[2].length;

            this.pieChartObj.setOption(option);
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
                    formatter: function (a, b, c, d) {
                        joinCount += c;
                        return c + '人';
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
                tempOption.series[0].itemStyle.normal.color = '#23C6C8';
                tempOption.series[0].itemStyle.normal.label = {
                    show: true,
                    formatter: function (a, b, c, d) {
                        joinCount += c;
                        return c + '人';
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
                        $('#notJoinCount').text(orgEmpCount - joinCount);
                        self.recordTotal[self.organizationId + self.yearMonth][type] = {
                            total: orgEmpCount,
                            joinCount: joinCount
                        };
                    }
                });
            } else {
                $('#joinCount').text(perObj.joinCount);
                $('#notJoinCount').text(perObj.total - perObj.joinCount);
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
        }
    };

    //绩效结果变化和绩效异常
    var preChangeObj = {
        organizationId: null,
        yearMonths: null,
        first: true,
        init: function (organizationId, yearMonths, type, clickTree) {
            var self = this;
            self.organizationId = organizationId;
            self.yearMonths = yearMonths;
            if (!this.first && clickTree) {//不是第一次加载  并且通过点击组织结构树
                var idx = $('#selectYM').get(0).selectedIndex;
                var ym = arrYearMonths[idx];
                ym = ym + '';
                ym = ym.trim();
                //设置绩效变化的数据
                var yms = yearMonths.substr(yearMonths.indexOf(ym), yearMonths.length);
                self.yearMonths = yms;
            }
            if (type) {//是否是首次加载
                self.setPaneTitle();
                $("#selectYM").change(function (e) {
                    self.selectChange(e);
                });
            }
            self.getPreChangeData();
            this.first = false;
        },
        getPreChangeData: function () {
            var self = this;
            $.each($(".mainArea"), function () {
                showDataTip($(this));
            });
            $.ajax({
                url: urls.getPreChangeCountData,
                data: {
                    organizationId: self.organizationId, yearMonths: self.yearMonths
                },
                dataType: 'json',
                success: function (data) {
                    $.each($(".mainArea"), function () {
                        removeDataTip($(this));
                    });
                    if (!$.isEmptyObject(data)) {
                        if (!$.isEmptyObject(data.change)) {
                            self.setPreChangeData(data.change);
                        } else {
                            $.each($(".mainArea"), function (i) {
                                if (i == 0) {
                                    showDataTip($(this));
                                }
                            });
                        }
                        if (!$.isEmptyObject(data.bigChange)) {
                            self.setPreExceptionData(data.bigChange);
                        } else {
                            $.each($(".mainArea"), function (i) {
                                if (i == 1) {
                                    showDataTip($(this));
                                }
                            });
                        }

                    } else {
                        $.each($(".mainArea"), function () {
                            showDataTip($(this));
                        });
                    }
                }
            });
        },
        getymName: function (n, _perWeek) {
            n = '' + n;
            n = n.trim();
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
                var perWeek = parseInt($('#perWeek').val());
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
            ym = ym + '';
            ym = ym.trim();
            //设置标题
            $('.titleYearMonth').text(self.getymName(ym));
            //设置绩效分布的数据
            preDistributionObj.init(reqOrgId, ym, 0);
            //设置绩效变化的数据
            var yms = yearMonths.substr(yearMonths.indexOf(ym), yearMonths.length);
            preChangeObj.init(reqOrgId, yms, false);
        },
        setPreChangeData: function (obj) {
            var c = new PreChange('preChange', obj, preChangeOption);
            c.init();
        },
        setPreExceptionData: function (obj) {
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


    //绩效结果变化趋势 配置对象
    var preChangeOption = {
        gap: 4.5, //间隔
        jianjiao: 16.5, //顶部和底部尖角
        width: 300,
        height: 200,
        x: 10.5,//x轴的起点
        topColor: '#9BBB57',
        middleColor: '#4BACC6',
        bottomColor: '#F79D46',
        leftFontStyle: {
            fontSize: 22,
            fontColor: '#F2F2F2',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 13,
            fontColor: '#434343',
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
        ;
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


    /**
     * 个人绩效详情表格参数
     */

    function getymName(n) {
        n = '' + n;
        n = n.trim();
        var end = n.substr(4, 5);
        n = n.substr(0, 4);
        n += (end == '06' ? '上半年' : '下半年');
        return n;
    }

    var selectDateArr = [{name: "yearMonth", options: []}];
    $.each(yearMonths.split(','), function (idx, n) {
        selectDateArr[0].options.push({key: n, value: getymName(n)});
    });
    var searchBoxConfig = {
        url: urls.searchBoxUrl, lazy: true,
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
                if ($.trim(searchTxt) != "") {
                    $(performanceDetailObj.gridId).clearGridData();
                    $(performanceDetailObj.gridId).showCol("performanceName2");
                    $(performanceDetailObj.gridId).showCol("performanceName3");
                    var orgId = $("#recordSelectTreeOrgId").val();
                    $(performanceDetailObj.gridId).setGridParam({
                        postData: {organizationId: orgId, keyName: searchTxt, queryType: 1}
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
    var tableHeight = 321;
    var performanceDetailGridOption = {
        url: urls.queryPerformanceDetailUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: tableHeight,//268
        colNames: ['姓名', '当期绩效', '上次绩效', '上上次绩效', '所属组织', '岗位', '职位序列', '职级'],
        colModel: [
            {
                name: 'userNameCh',
                index: 'userNameCh',
                sortable: false,
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }

                    return "<a href='javascript:void(0)' data='" + row.empId + "' class='talent_col' >" + value + "</a>";
                }
            },
            {name: 'performanceName1', index: 'performanceName1', sortable: false, align: 'center'},
            {name: 'performanceName2', index: 'performanceName2', sortable: false, align: 'center'},
            {
                name: 'performanceName3',
                index: 'performanceName3',
                sortable: false,
                align: 'center'
            },
            {
                name: 'organizationName',
                index: 'organizationName',
                sortable: false,
                align: 'center'
            },
            {name: 'positionName', index: 'positionName', sortable: false, align: 'center'},
            {name: 'sequenceName', index: 'sequenceName', sortable: false, align: 'center'},
            {name: 'rankName', index: 'rankName', sortable: false, align: 'center'},
        ],
        rownumbers: true,
        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#performanceDetailSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
//            var limit=xhr.limit;
//            //XXX 目前没有好办法解决自动heigth   加2是为了防止滚动条
//            var tbBodyHeigth=limit*32+1;
//            if(tbBodyHeigth<tableHeight){
//            	tbBodyHeigth = tableHeight;
//            }
//            $('#performanceDetailGrid').jqGrid('setGridHeight',tbBodyHeigth);

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
            performanceDetailGridOption.postData = {organizationId: organId, yearMonth: arrYearMonths[0], queryType: 2};
            $(self.gridId).jqGrid(performanceDetailGridOption).trigger("reloadGrid");
            this.isInit = true;
        },
        getRequestData: function (organId) {
            var self = this;
            // 存起。重新选seachBox的参数时，没有带上的机构Id
            if (organId != null) {
                self.organId = organId
            }
            var _organId = self.organId;

            var params = $.extend(true, {}, self.searchBox.getSelectData(), {organizationId: _organId, queryType: 2});
            //jQuery("#grid_id").setGridParam().showCol("colname").trigger("reloadGrid");

            //jQuery("#grid_id").setGridParam().hideCol("colname").trigger("reloadGrid");
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
            if (!this.isInit) {
                this.init(organId);
                return;
            }
            var self = this;
            self.resizeGrid();
            self.organId = organId;
            self.getRequestData(organId);
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#performanceDetailTable').width() * 0.98);
            // $("performanceDetailSel_center").width($('#runOffDeailTable').width() * 0.98);
        }
    };
    // performanceDetailObj.init(reqOrgId);
    //点击tab页
    $('#performanceTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var targetAreaId = $(e.target).attr('aria-controls');
        if (targetAreaId == "performanceDetailTab") {
            performanceDetailObj.resizeGrid();
        } else {
            preDistributionObj.starChartObj.resize();
            preDistributionObj.pieChartObj.resize();
        }

    });

    function initAll(id) {
        reqOrgId = id;
        initArrYearMonths();
        initOrganTree();	//机构树
        initTimeLine(reqOrgId);
        //界面功能
        initTop4Block(reqOrgId, yearMonths);
        preDistributionObj.init(reqOrgId, yearMonth, 0, true);
        preChangeObj.init(reqOrgId, yearMonths, preChangeObj.first, true);
        performanceDetailObj.initData(reqOrgId);
    }

    initAll(reqOrgId);

    function showDataTip($targetDom) {
        //如果是显示状态
        if ($targetDom.find('.empty-tip').length != 0) {
            return;
        }
        var domHeight = $targetDom.height() || 100;
        $targetDom.children().hide();
        var emptyTipStyle = 'height:' + domHeight + 'px;line-height:' + domHeight + 'px;';
        $targetDom.append('<div class="empty-tip" style="' + emptyTipStyle + '">暂无数据</div>');
    }

    function removeDataTip($targetDom) {
        $targetDom.find('.empty-tip').remove();
        $targetDom.children().show();
    }
});