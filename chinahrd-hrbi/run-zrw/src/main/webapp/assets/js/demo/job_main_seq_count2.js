require(["jquery", "echarts", "echarts/chart/bar", 'echarts/chart/pie', 'utils', 'organTreeSelector', 'jgGrid'], function ($, echarts) {

    var mainSeqColor = ['#275A7E', '#2C6894', '#3174A6', '#3580B7', '#7B9FC5', '#A7BBD3', '#C6D0E0'];
    var childColor = ['#CD692C', '#EE7D34', '#FF8E3D', '#FFB184', '#FFCEB2'];
    var libColor = ['#80B5E1'];
    var globalheader = null, globaldatas = null;
    var no_data_info = "很抱歉，没有找到相关的记录";
    var totalSummary = 0;
    var textStyle = {color: '#000000', fontFamily: '微软雅黑', fontFamily2: '微软雅黑'};
    var urls = {
        MAIN_SEQ_SEQ: G_WEB_ROOT + '/assets/json/posseq/jobMainSeqCount/jobMainSeqData.json'
    }

    var ecConfig = require('echarts/config');

    var mainSeqOption = {
        color: mainSeqColor,
        calculable: false,
        animation: false,
        tooltip: {
            formatter: function (a, b, c, d) {
                return a[1];
            }
        },
        series: [{
            type: 'pie',
            radius: ['30%', '50%'],
            top: ['0%', '0%'],       // 默认全局居中
            minAngle: 15,
            selectedMode: 'single',
            clockWise: true,
            itemStyle: {
                normal: {
                    label: label = {
                        show: true,
                        position: 'inside',
                        textStyle: textStyle,
                        formatter: function (obj) {
                            return obj.data.sum + "," + obj.value + "%";
                        }
                    },
                    labelLine: {show: true}
                }
            },
            data: ''
        }]
    };


    var date = setDateNow($('#initDate').val());
    // 显示获取的时间
    function setDateNow(obj) {
        if (obj != '' && obj.indexOf('/') >= 0) {
            return new Date(obj);
        } else {
            return Tc.getYesterday();
        }
    }

    var jobMainSeqCount = {
        /*******************************************************************
         * id:图表id, el:表格id data:主序列数据
         */
        init: function (id, el, deptInfo) {
            this.el = el;
            this.myChart = echarts.init(document.getElementById(id));
            this.isClickBind = false;
            this.options = mainSeqOption;
            this.initEvent(deptInfo);
        },
        initEvent: function (deptInfo) {
            // TODO 这个可以去掉机构组件
            this.addTreeSelect(deptInfo);
            if (deptInfo['id']) {
                this.initDefaultData();
            }
        },

        _backInitStatu: function () {
            var seq_detail = $(".detailseq_area").css("display");
            if (seq_detail == 'none')return;
            $(".detailseq_area").hide(0, function () {
                $("#seq").parent().animate({
                    width: "show",
                    height: "show",
                    paddingLeft: "show",
                    paddingRight: "show",
                    marginLeft: "show",
                    marginRight: "show"
                }, 1000, function () {
                    $("#seqtable li").removeClass("current");
                });
            });
        },
        addTreeSelect: function (deptInfo) {
            var self = this;
            var uniqueId = this.getUniqueDeptId();
            $("#tree").organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                value: deptInfo,
                onSelect: function (ids, texts) {

                    // TODO 因为是json数据， 为了Demo展示 硬编码
                    ids = 'PA001_S000000002'
                    texts = '集团总部'
                    console.log(ids, texts);


                    $("#uniqueDeptId").val(ids);
                    self._backInitStatu();
                    self.initDefaultData();
                }
            });
        },
        addRowOver: function () {
            var self = this;
            var firstLi = $("#seqtable li:gt(0)");
            firstLi.mouseover(function () {
                self.changeTableStyle("#seqtable li", $(this));
                if (!self.isPieRender) {
                    return;
                }
                var serie = self.options.series[0];
                var tableSelectd = $(this).find("span").eq(0).attr("value");
                var serie = self.options.series[0];
                serie.data[getSelectItem(serie)].selected = false;
                serie.data[tableSelectd].selected = true;
                self.options.animation = false;
                self.myChart.setOption(self.options);
            });
            firstLi.mouseout(function () {
                $(this).removeClass("hover");
            });
            function getSelectItem(serie) {
                for (var i = 0; i < serie.data.length; i++) {
                    if (serie.data[i].selected) {
                        return i;
                    }
                }
            }
        },
        // 表格点击事件
        addRowClick: function () {
            var self = this;
            $("#seqtable li:gt(0)").click(function () {
                console.log(11111);
                totalSummary = 0;
                self.lvTitleData = [];
                $("#seqtable li:gt(0)").removeClass("current");
                $(this).addClass("current");
                var mainSeqId = $(this).attr("val");
                self.setMainSeqId(mainSeqId);
                $("#seqtable").css({
                    "margin-left": "0px",
                    "margin-top": "15px"
                });
                $("#seqtable").parent().attr("class", "span7");
                $("#seq").parent().animate({
                    width: "hide",
                    paddingLeft: "hide",
                    paddingRight: "hide",
                    marginLeft: "hide",
                    marginRight: "hide"
                }, 0, function () {
                    self._detailseqShowCall(mainSeqId);
                });
            });
        },
        _detailseqShowCall: function (mainSeqId) {
            var self = this;
            self.addCloseEvent();
            $(".detailseq_area").show(0, function () {

                var unitId = self.getUniqueDeptId();
                var date = self.getDate();

                // TODO 查询右面数据
                console.log(mainSeqId, unitId);
                $('#chart1').text('发ajax请求，返回数据后生成图表。' + mainSeqId).css({
                    'text-align': 'center',
                    'line-height': '300px'
                });
                $('#chart2').text('发ajax请求，返回数据后生成图表。' + mainSeqId).css({
                    'text-align': 'center',
                    'line-height': '300px'
                });
                $('#chart3').text('发ajax请求，返回数据后生成表格。机构ID：' + unitId + '表格ID：' + mainSeqId).css({
                    'text-align': 'center',
                    'line-height': '250px'
                });

            });
        },

        changeMainStyle: function () {
            $("#seqtable").css({
                "margin-left": "100px",
                "margin-top": "50px"
            });
            // 中间表格宽度
            $("#seqtable").parent().attr("class", "span12");
        },
        setMainSeqData: function (uniqueDeptId, date) {
            if (!uniqueDeptId) {
                return;
            }
            var self = this, params = {uniqueDeptId: uniqueDeptId, date: date};
            $.getJSON(urls.MAIN_SEQ_SEQ, params, function (data) {

                console.log(data);
                self.changeMainStyle();

                self.isPieRender = false;
                var isZero = data.datas.length == 0 || data.total == 0;

                $("#seq").toggle(!isZero);
                // 如果所有值为空，则不渲染饼图
                $.each(data.datas, function (i, val) {
                    if (val.value > 0) {
                        self.isPieRender = true;
                        return false;
                    }
                });
                if (self.isPieRender) {
                    var mId = self.getMainSeqId();
                    var series_data = [];
                    var flag = false;
                    // TODO 左边图饼数据 data
                    $.each(data.datas, function (i, data) {
                        data.selected = mId == data.seqId ? true : false;
                        flag = data.selected;
                        series_data.push(data);
                    });
                    if (!flag)
                        series_data[0].selected = true;
                    self.options.series[0].data = series_data;
                    self.setOption(self.options);
                } else {
                    self.clear();
                }

                // TODO 中间表格数据 data.datas
                self.renderTable(self.el, data.datas, data.total);
                self.myChart.on(ecConfig.EVENT.HOVER, function (param) {
                    var eqIndex = param.dataIndex == 0 ? 0 : param.dataIndex;
                    self.changeTableStyle("#seqtable li", $("#seqtable li:gt(0)").eq(eqIndex));
                });
                self.addEvent();
            }, "json");
        },
        addCloseEvent: function () {
            var self = this;
            $(".seqdetailclose").click(function () {
                self.changeMainStyle();
                $(".detailseq_area").hide(0, function () {
                    $("#seq").parent().animate({
                        width: "show",
                        height: "show",
                        paddingLeft: "show",
                        paddingRight: "show",
                        marginLeft: "show",
                        marginRight: "show"
                    }, 1000, function () {
                        $("#seqtable li").removeClass("current");
                    });
                });


            });
        },
        renderTable: function (el, data, total) {
            var ul = $("<ul>");
            if (total != 0 && data != null && data.length > 0) {
                var lis = "<li style='border-bottom:none;'>" +
                    "<span style='width:100px;float:left;'>&nbsp;</span>" +
                    "<span class='percent'>人数占比</span><span class='num'>人数</span>" +
                    "<a href='javascript:void(0);'></a>" +
                    "</li>";
                var mainId = this.getMainSeqId();
                for (var i = 0; i < data.length; i++) {
                    var isManagerSeq = data[i].isManagerSeq;
                    var isExecutiveSeq = data[i].isExecutiveSeq;
                    var seqId = data[i].seqId;

                    var clzName = seqId == mainId ? "current" : "";
                    lis += "<li class='" + clzName + "' val='" + seqId + "'>" +
                        "<span class='title' style='background:" + mainSeqColor[i] + "' value='" + i + "'>" +
                        data[i].alias +
                        "</span>" +
                        "<span class='percent'>" +
                        data[i].percentatge + "%" +
                        "</span>" +
                        "<span class='num'>" +
                        Tc.formatNumber(data[i].sum) +
                        "</span>" +
                        "<span id='navbox' style='width:10px;'>" +
                        "<a href='javascript:void(0);' class='jt'></a>" +
                        "</span>" +
                        "</li>";
                }
                ul.append($(lis));
            }
            $("#" + el).empty().append(ul);
        },


        setOption: function (options) {
            _self = this;
            _self.myChart.setOption(this.options, true);
            _self.myChart.on(ecConfig.EVENT.HOVER, function (param) {
            });
        },
        clear: function (chart) {
            _self = this;
            if (!chart)
                _self.myChart.clear();
            else
                chart.clear();
        },
        changeTableStyle: function (dom, obj) {
            $(dom).removeClass("hover");
            obj.addClass("hover");
        },
        getMainSeqId: function () {
            return $("#mainSeqId").val();
        },
        setMainSeqId: function (val) {
            $("#mainSeqId").val(val);
        },
        initDefaultData: function () {
            this.setMainSeqData(this.getUniqueDeptId(), this.getDate());
        },
        getUniqueDeptId: function () {
            return $("#uniqueDeptId").val();
        },
        getUnitId: function () {
            return this.getUniqueDeptId().split("_")[0];
        },
        getDate: function () {
            return $("#initDate").val();
        },
        addEvent: function () {
            this.addRowClick();
            this.addRowOver();
        },
    };


    Tc.JobMainSeqCount = function (id, el, deptInfo) {
        jobMainSeqCount.init(id, el, deptInfo);
        return jobMainSeqCount;
    };

    $(function () {
        // TODO 从Controller转入机构Id和名称
        var deptInfo = {};
        deptInfo['id'] = $("#uniqueId").val();
        deptInfo['text'] = $("#deptName").val();
        console.log(deptInfo);

        var jobMainSeq = new Tc.JobMainSeqCount("seq", "seqtable", deptInfo);
    });
});
