require(['jquery', 'echarts', 'echarts/chart/pie', 'echarts/chart/bar', 'bootstrap', 'underscore', 'utils'
], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getTeamImgDataUrl: webRoot + '/teamImg/getTeamImgData.do'
    };

    //饼图颜色
    var colorPie = ["#0b98e0", "#00bda9", "#4573a7", "#92c3d4", "#de6e1b", "#ff0084", "#af00e1", "#8d55f6", "#6a5888", "#2340f3"];
    var chartWidth = $("#constellatory").parent().width();

    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var ecConfig = require('echarts/config');

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        teamImgObj.getRequestData(organId);
    };

    var legendReset = function (data) {
        var array = [];
        $.each(data, function (i, item) {
            array.push({name: item, icon: 'circle', width: '5px'})
        });
        return array;
    }
    var legendObj = {
        orient: 'vertical',
        x: '85%',
        y: 'center',
        selectedMode: false,
        textStyle: {color: '#555', fontFamily: '微软雅黑 verdana tahoma', fontSize: 12},
        itemWidth: 10,
        itemHeight: 10,
        itemGap: 12,
        backgroundColor: '#fff',
        padding: 0,
        data: []
    };

    var getItemStyle = function (index, showEmphasis) {
        var style = {
            normal: {
                label: {
                    show: true,
                    //textStyle: {color: "#555555"},
                    formatter: function (i) {
                        return i.name + (chartWidth > 450 ? '：' : '\n') + teamImgObj.formatPieData(teamImgObj.total[index], i.value);
                    }
                },
                labelLine: {
                    length: 10
                }
            }
        }
        if (showEmphasis) {
            style.emphasis = {
                label: {
                    show: true,
                    position: 'center',
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            }
        }
        return style;
    };

    var piePosition = ['45%', '50%'];

    /*==============================================================*/
    /* 职级															*/
    /*==============================================================*/
    var abilityLvOpt = {
        calculable: false,
        legend: legendObj,
        color: colorPie,
        series: [
            {
                name: '职级',
                type: 'pie',
                clickable: false,
                radius: '55%',
                center: piePosition,
                data: [],
                itemStyle: getItemStyle(0, false)
            }
        ]
    };

    var abilityLvObj = {
        pieObj: null,
        chartId: 'abilityLv',
        resultData: null,
        init: function (rsData, rslegend) {
            var self = this;
            var width = $("#abilityLv").width();
            abilityLvOpt.series[0].radius = width < 400 ? (width / 10).toString() + '%' : '55%';
            abilityLvOpt.series[0].data = rsData;
            abilityLvOpt.legend.data = rslegend;

            self.pieObj.setOption(abilityLvOpt, true);
        }
    };


    /*==============================================================*/
    /* 工作地点														*/
    /*==============================================================*/
    var workLocationOpt = {
        calculable: false,
        legend: legendObj,
        color: colorPie,
        series: [
            {
                name: '工作地点',
                clickable: false,
                type: 'pie',
                radius: '55%',
                center: piePosition,
                data: [],
                itemStyle: getItemStyle(1, false)
            }
        ]
    };
    var workLocationObj = {
        pieObj: null,
        chartId: 'workLocation',
        resultData: null,
        init: function (rsData, rslegend) {

            var self = this;
            var width = $("#abilityLv").width();
            workLocationOpt.series[0].radius = width < 400 ? (width / 10).toString() + '%' : '55%';
            workLocationOpt.series[0].data = rsData;
            workLocationOpt.legend.data = rslegend;

            self.pieObj.setOption(workLocationOpt, true);
        }
    };


    /*==============================================================*/
    /* 性别															*/
    /*==============================================================*/
    var sexOpt = {
        calculable: false,
        color: ['#019BD9', '#FF88A2'],
        series: [
            {
                type: 'pie',
                clickable: false,
                radius: ['40%', '60%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        textStyle: {color: 'black'},
                        labelLine: {
                            show: false
                        }
                    }
                }
            }
        ]
    };
    var sexObj = {
        pieObj: null,
        chartId: 'sex',
        resultData: null,
        init: function (rsData) {

            var self = this;

            sexOpt.series[0].data = rsData;
            self.pieObj.setOption(sexOpt, true);

            self.style();
        },
        style: function () {

            var width = $(".sex-pie").width();
            var _icoLeft = (width / 2) - 20;
            var _lidyLeft = (width / 2) - 165;
            var _manLeft = (width / 2) + 140;

            $(".teamImg-sex").css({position: "absolute", top: "157px", left: _icoLeft + "px"}).removeClass("hide");
            $(".lidyZone").css({position: "absolute", top: "137px", left: _lidyLeft + "px"}).removeClass("hide");
            $(".manZone").css({position: "absolute", top: "137px", left: _manLeft + "px"}).removeClass("hide");


        }
    };


    /*==============================================================*/
    /* 婚姻状况														*/
    /*==============================================================*/
    var marryObj = {
        pieObj: null,
        chartId: 'sex',
        resultData: null,
        init: function (rsData) {

            var self = this;

            self.style(rsData);
        },
        style: function (rsData) {
            var self = this;
            self.reset();

            // 总人数
            var total = rsData.total;

            // 系数 = 总人数 / 总高度@see table.tr.height : 200px;
            var quotiety = total / 200;

            // 人数
            var _leftBatVal = rsData.unIsMarry;
            var _righBarVal = rsData.isMarry;
            $(".marry_state #leftBarVal").append(_leftBatVal + "人");
            $(".marry_state #righBarVal").append(_righBarVal + "人");


            // 换算人数占的高度
            var _leftBarH = _leftBatVal / quotiety;
            var _rightBarH = _righBarVal / quotiety;

            var _leftBarHeight = _leftBarH < 16 ? 16 : _leftBarH;
            var _rightBarHeight = _rightBarH < 16 ? 16 : _rightBarH;
            $(".marry_state .leftBar").css({height: _leftBarHeight + "px", lineHeight: _leftBarHeight + "px"});
            $(".marry_state .rightBar").css({height: _rightBarHeight + "px", lineHeight: _rightBarHeight + "px"});


            // 换算人数高度占的百分比
            $(".marry_state .leftBarPer").append((Math.round((_leftBarH / 200) * 100)) + "%");
            $(".marry_state .rightBarPer").append((Math.round((_rightBarH / 200) * 100)) + "%");
        },
        reset: function () {

            $(".marry_state #leftBarVal").text('');
            $(".marry_state #righBarVal").text('');
            $(".marry_state .leftBarPer").text('');
            $(".marry_state .rightBarPer").text('');

            var width = $("#ms").width();

            var _left = (width / 2) - 100;

            $("#ms").css({position: "absolute", left: _left + "px"});
            $(".marry_state").css({position: "absolute", top: "50px"}).removeClass("hide");

        }
    };


    /*==============================================================*/
    /* 年龄															*/
    /*==============================================================*/
    var ageOpt = {
        calculable: false,
        grid: {
            borderWidth: 0
        },
        xAxis: [
            {
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
                data: []
            }
        ],
        yAxis: [
            {
                axisLine: false,
                splitLine: false,
                axisLabel: false,
                type: 'value'
            }
        ],
        series: [
            {
                type: 'bar',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: function (params) {
                            return colorPie[params.dataIndex]
                        },
                        label: {
                            show: true,
                            textStyle: {color: 'black'},
                            position: 'top',
                            formatter: ''
                        }
                    }
                },
                data: []
            }

        ]
    };
    var ageObj = {
        barObj: {},
        chartId: 'age',
        resultData: null,
        init: function (rsData) {

            var self = this;
            ageOpt.xAxis[0].data = rsData.xAxisData;
            ageOpt.series[0].data = rsData.seriesData;

            self.barObj.setOption(ageOpt, true);
        }

    };


    /*==============================================================*/
    /* 司龄															*/
    /*==============================================================*/
    var companyAgeOpt = {
        calculable: false,
        grid: {
            borderWidth: 0
        },
        xAxis: [
            {
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: false,
                data: []
            }
        ],
        yAxis: [
            {
                axisLine: false,
                splitLine: false,
                axisLabel: false,
                type: 'value'
            }
        ],
        series: [
            {
                type: 'bar',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: function (params) {
                            return colorPie[params.dataIndex]
                        },
                        label: {
                            show: true,
                            textStyle: {color: 'black'},
                            position: 'top',
                            formatter: ''
                        }
                    }
                },
                data: []
            }

        ]
    };
    var companyAgeObj = {
        barObj: null,
        chartId: 'companyAge',
        resultData: null,
        init: function (rsData) {
            var self = this;

            companyAgeOpt.xAxis[0].data = rsData.xAxisData;
            companyAgeOpt.series[0].data = rsData.seriesData;

            self.barObj.setOption(companyAgeOpt, true);
        }

    };


    /*==============================================================*/
    /* 血型															*/
    /*==============================================================*/
    var bloodOpt = {
        calculable: false,
        legend: legendObj,
        color: colorPie,
        series: [
            {
                clickable: false,
                name: '',
                type: 'pie',
                radius: '55%',
                center: piePosition,
                data: [],
                itemStyle: getItemStyle(1, false)
            }
        ]
    };
    var bloodObj = {
        pieObj: null,
        chartId: 'blood',
        resultData: null,
        init: function (rsData, rslegend) {

            var self = this;
            var width = $("#blood").width();
            bloodOpt.series[0].radius = width < 400 ? (width / 10).toString() + '%' : '55%';
            bloodOpt.series[0].data = rsData;
            bloodOpt.legend.data = rslegend;

            self.pieObj.setOption(bloodOpt, true);
        }

    };


    /*==============================================================*/
    /* 星座															*/
    /*==============================================================*/
    var constellatoryOpt = {
        calculable: false,
        grid: {
            borderWidth: 0,
            x: 20,
            x2: 20,
            y: 80,
            y2: 60
        },
        xAxis: [
            {
                type: 'category',
                show: false,
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                show: false
            }
        ],
        series: [
            {
                name: 'ECharts例子个数统计',
                type: 'bar',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: function (params) {
                            var colorList = [
                                '#848ADC', '#A07E67', '#F92D3B', '#C7B043', '#8CBE45',
                                '#797A7D', '#E98AC9', '#893DB2', '#6EAB90', '#5FD384',
                                '#49ACEC', '#E2A43E'
                            ];
                            return colorList[params.dataIndex]
                        },
                        label: {
                            show: true,
                            position: 'top',
                            formatter: function (i) {
                                return i.name + "\n" + i.value;
                            }
                        }
                    }
                },
                data: [],
                markPoint: {
                    tooltip: {
                        trigger: 'item',
                        backgroundColor: 'rgba(0,0,0,0)',
                        formatter: function (params) {
                            return '<img src="'
                                + params.data.symbol.replace('image://', '')
                                + '">';
                        }
                    },
                    clickable: false,
                    data: [
                        {
                            xAxis: 0,
                            y: 345,
                            name: '白羊座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-1.png'
                        },
                        {
                            xAxis: 1,
                            y: 345,
                            name: '金牛座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-2.png'
                        },
                        {
                            xAxis: 2,
                            y: 345,
                            name: '双子座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-3.png'
                        },
                        {
                            xAxis: 3,
                            y: 345,
                            name: '巨蟹座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-4.png'
                        },
                        {
                            xAxis: 4,
                            y: 345,
                            name: '狮子座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-5.png'
                        },
                        {
                            xAxis: 5,
                            y: 345,
                            name: '处女座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-6.png'
                        },
                        {
                            xAxis: 6,
                            y: 345,
                            name: '天秤座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-7.png'
                        },
                        {
                            xAxis: 7,
                            y: 345,
                            name: '天蝎座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-8.png'
                        },
                        {
                            xAxis: 8,
                            y: 345,
                            name: '射手座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-11.png'
                        },
                        {
                            xAxis: 9,
                            y: 345,
                            name: '摩羯座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-10.png'
                        },
                        {
                            xAxis: 10,
                            y: 345,
                            name: '水瓶座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-9.png'
                        },
                        {
                            xAxis: 11,
                            y: 345,
                            name: '双鱼座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-12.png'
                        }
                    ]
                }
            }
        ]
    };
    var constellatoryObj = {
        barObj: null,
        chartId: 'constellatory',
        resultData: null,
        init: function (rsData) {
            var self = this;
            constellatoryOpt.xAxis[0].data = rsData.xAxisData;
            constellatoryOpt.series[0].data = rsData.seriesData;
            var width = $("#constellatory").width();
            constellatoryOpt.series[0].itemStyle.normal.label.formatter = function (param) {
                var name = param.name;
                if (width < 470) {
                    var s = "";
                    for (var i = 0; i < b.length; i++) {
                        s += (s != "" ? "\n" : "") + name.substring(i, i + 1);
                    }
                    name = s;
                }
                return name + "\n" + param.value;
            }
            var data = constellatoryOpt.series[0].markPoint.data;
            $.each(data, function (i, item) {
                item.symbolSize = width < 470 ? 14 : 20;
            });
            constellatoryOpt.grid.width = width - 40;
            self.barObj.setOption(constellatoryOpt, true);
        }

    };


    /*==============================================================*/
    /* 性格															*/
    /*==============================================================*/
    var personalityOpt = {
        calculable: false,
        tooltip: {
            trigger: 'item',
            formatter: ''
        },
//        grid:{
//        	 x: '10px',
//             y: '10px',
//        },
        legend: legendObj,
        color: colorPie,
        series: [
            {
                name: '性格',
                type: 'pie',
                clickable: false,
                center: piePosition,
                radius: ['40%', '60%'],
                data: [],
                itemStyle: getItemStyle(3, true)
            }
        ]
    };
    var personalityObj = {
        pieObj: null,
        chartId: 'personality',
        resultData: null,
        init: function (rsData, rslegend) {

            var self = this;
            self.setData(rsData, rslegend);
            self.pieObj.setOption(personalityOpt, true);

        },
        setData: function (rsData, rslegend) {
            var width = $("#personality").width();
            // personalityOpt.series[0].radius = [width < 550 ? (width / 25).toString() + '%' : '50%', width < 550 ? (width / 15).toString() + '%' : '70%'];
            personalityOpt.series[0].data = rsData;
            personalityOpt.tooltip.show = false;
            personalityOpt.legend.data = rslegend;
            personalityOpt.tooltip.formatter =
                function (a, b, c) {
                    var _data = a.data;
                    var html = '';
                    html += '<div>' + _data.name + '：(' + _data.value + ') <br>';

                    $.each(_data.emps, function (i, item) {
                        html += item + '<br>';
                    });
                    html += '</div>';
                    return html;
                };
        }
    };


    /*==============================================================*/
    /* 页面初始化													*/
    /*==============================================================*/
    var teamImgObj = {
        total: [],
        resultData: [],
        legend: [],
        organId: '',
        init: function (organId) {
            var self = this;

            abilityLvObj.pieObj = initEChart(abilityLvObj.chartId);
            workLocationObj.pieObj = initEChart(workLocationObj.chartId);
            sexObj.pieObj = initEChart(sexObj.chartId);
            ageObj.barObj = initEChart(ageObj.chartId);
            companyAgeObj.barObj = initEChart(companyAgeObj.chartId);
            bloodObj.pieObj = initEChart(bloodObj.chartId);
            constellatoryObj.barObj = initEChart(constellatoryObj.chartId);
            personalityObj.pieObj = initEChart(personalityObj.chartId);

            self.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            if (_.isNull(self.resultData) || self.organId != organId) {
                self.organId = organId;
                self.getRequestData(organId);
                return;
            }
        }, showTeamTip: function () {
            showTeamTip($('#abilityLv'));
            showTeamTip($('#workLocation'));
            showTeamTip($('#sex_div'));
            showTeamTip($('#ms_div'));
            showTeamTip($('#age'));
            showTeamTip($('#companyAge'));
            showTeamTip($('#blood'));
            showTeamTip($('#constellatory'));
            showTeamTip($('#personality'));
        }, removeTeamTip: function () {
            removeTeamTip($('#abilityLv'));
            removeTeamTip($('#workLocation'));
            removeTeamTip($('#sex_div'));
            removeTeamTip($('#ms_div'));
            removeTeamTip($('#age'));
            removeTeamTip($('#companyAge'));
            removeTeamTip($('#blood'));
            removeTeamTip($('#constellatory'));
            removeTeamTip($('#personality'));
        },
        getRequestData: function (organId) {
            var self = this;
            $.post(urls.getTeamImgDataUrl, {'organId': organId}, function (rs) {

                if (_.isEmpty(rs)) {
                    self.showTeamTip();
                    return;
                }
                self.removeTeamTip();
                // 职级
                var abLvTotal = 0;
                self.legend[0] = [];
                self.resultData[0] = [];
                $.each(rs[0], function (a, b) {
                    abLvTotal += parseInt(b.v);
                    self.resultData[0].push({value: b.v, name: b.k});
                });
                self.total[0] = abLvTotal;
                $.each(self.resultData[0], function (i, item) {
                    self.legend[0].push(item.name);
                });


                // 工作地点
                var workLocationTotal = 0;
                self.legend[1] = [];
                self.resultData[1] = [];
                $.each(rs[1], function (a, b) {
                    workLocationTotal += parseInt(b.v);
                    self.resultData[1].push({value: b.v, name: b.k});
                });
                self.total[1] = workLocationTotal;
                $.each(self.resultData[1], function (i, item) {
                    self.legend[1].push(item.name);
                });


                // 男女占比
                self.resultData[2] = [];
                $('.manVal').text(0);
                $('.lidyVal').text(0);
                $.each(rs[2], function (a, b) {
                    self.resultData[2].push({value: b.v, name: b.k});
                    b.k == '男' ? $('.manVal').text(b.v) : $('.lidyVal').text(b.v);
                });


                // 婚姻状况
                self.resultData[3] =
                {
                    unIsMarry: rs[3].unIsMarry,
                    isMarry: rs[3].isMarry,
                    total: rs[3].total
                };


                // 年龄
                self.resultData[4] =
                {
                    xAxisData: rs[4].xAxisData,
                    seriesData: rs[4].seriesData
                };


                // 司龄
                self.resultData[5] =
                {
                    xAxisData: rs[5].xAxisData,
                    seriesData: rs[5].seriesData
                };


                // 血型
                var bloodTotal = 0;
                self.legend[6] = [];
                self.resultData[6] = [];
                $.each(rs[6], function (a, b) {
                    bloodTotal += b.v;
                    self.resultData[6].push({value: b.v, name: b.k});
                });
                self.total[2] = bloodTotal;
                $.each(self.resultData[6], function (i, item) {
                    self.legend[6].push(item.name);
                });


                // 星座
                self.resultData[7] =
                {
                    xAxisData: rs[7].xAxisData,
                    seriesData: rs[7].seriesData
                };


                // 性格
//                var starTotal = 0;
                self.legend[8] = [];
                self.resultData[8] = [];
                $.each(rs[8], function (a, b) {
//                    starTotal += parseInt(b.value);
                    self.resultData[8].push({value: b.value, name: b.name, emps: b.empNames});
                });
//                self.total[3] = starTotal;
                self.total[3] = rs[3].total;
                $.each(self.resultData[8], function (i, item) {
                    self.legend[8].push(item.name);
                });
                self.itemRun();
            });
        },
        formatPieData: function (total, item) {
            if (!item) {
                return {};
            }
            return Tc.formatNumber(item) + '人\n' + ((item / total) * 100).toFixed(0) + '%';
        },
        itemRun: function () {
            var self = this;

            abilityLvObj.init(self.resultData[0], legendReset(self.legend[0]));
            workLocationObj.init(self.resultData[1], legendReset(self.legend[1]));
            sexObj.init(self.resultData[2]);
            marryObj.init(self.resultData[3]);
            ageObj.init(self.resultData[4]);
            companyAgeObj.init(self.resultData[5]);
            bloodObj.init(self.resultData[6], legendReset(self.legend[6]));
            constellatoryObj.init(self.resultData[7]);
            personalityObj.init(self.resultData[8], legendReset(self.legend[8]));

        }
    }

    teamImgObj.init(reqOrgId);

    window.onresize = function () {
        chartWidth = $("#constellatory").parent().width();
        if (!_.isEmpty(teamImgObj.resultData)) {
            abilityLvObj.init(teamImgObj.resultData[0], legendReset(teamImgObj.legend[0]));
            abilityLvObj.pieObj.resize();
            workLocationObj.init(teamImgObj.resultData[1], legendReset(teamImgObj.legend[1]));
            workLocationObj.pieObj.resize();
            bloodObj.init(teamImgObj.resultData[6], legendReset(teamImgObj.legend[6]));
            bloodObj.pieObj.resize();
            personalityObj.init(teamImgObj.resultData[8], legendReset(teamImgObj.legend[8]));
            personalityObj.pieObj.resize();
            constellatoryObj.init(teamImgObj.resultData[7]);
            constellatoryObj.barObj.resize();
        }
    };

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    //重新加载表格
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    function showTeamTip($targetDom) {
        //如果是显示状态
        if ($targetDom.find('.empty-tip').length != 0) {
            return;
        }
        var domHeight = $targetDom.height() || 100;
        $targetDom.children().hide();
        var emptyTipStyle = 'height:' + domHeight + 'px;line-height:' + domHeight + 'px;';
        $targetDom.append('<div class="empty-tip" style="' + emptyTipStyle + '">暂无数据</div>');
    }

    function removeTeamTip($targetDom) {
        $targetDom.find('.empty-tip').remove();
        $targetDom.children().show();
    }
});