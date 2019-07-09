/**
 * Created by Administrator on 2016/5/4.
 */
require(['jquery', 'echarts', 'zrender/shape/Text', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'bootstrap', 'jgGrid', 'underscore', 'timeLine2', 'messenger', 'select2', 'selection', 'organTreeSelector'], function ($, echarts, TextShape) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var ecConfig = require('echarts/config');
	$(function () { $("[data-toggle='tooltip']").tooltip({
		placement:'bottom',
	    html: true
	    }); });
    var urls = {
        //页签一
        getEligibilityApplication: webRoot + "/promotionBoard/getEligibilityApplication.do", //符合条件已申请
        getEligibilityApplicationList: webRoot + "/promotionBoard/getEligibilityApplicationList.do", //符合条件已申请 列表
        getEligibilityNotApplication: webRoot + "/promotionBoard/getEligibilityNotApplication.do", //符合条件未申请
        getEligibilityNotApplicationList: webRoot + "/promotionBoard/getEligibilityNotApplicationList.do", //符合条件未申请 列表
        getSomeEligibility: webRoot + "/promotionBoard/getSomeEligibility.do", //部分符合条件
        getAllEligibilityList: webRoot + "/promotionBoard/getAllEligibilityList.do", //全部符合条件 列表

        getTrendAnalysis: webRoot + "/promotionBoard/getTrendAnalysis.do", //趋势分析
        getOrgAnalysis: webRoot + "/promotionBoard/getOrgAnalysis.do", //下级组织分析
        getOrgAnalysisPerJinsuList: webRoot + "/promotionBoard/getOrgAnalysisPerJinsuList.do", //下级组织分析,个人晋级速度
        getSequenceAnalysis: webRoot + "/promotionBoard/getSequenceAnalysis.do", //序列统计
        getKeyTalentAnalysis: webRoot + "/promotionBoard/getKeyTalentAnalysis.do", //关键人才
        getPerformanceAnalysis: webRoot + "/promotionBoard/getPerformanceAnalysis.do", //绩效
        getTrackChart: webRoot + "/promotionBoard/getTrackChart.do", //晋级轨迹(图)
        getTrackSelect: webRoot + "/promotionBoard/getTrackSelect.do", //晋级轨迹(搜索下拉)
        getTrackList: webRoot + "/promotionBoard/getTrackList.do", //晋级轨迹(列表)

        //页签二
        getStatusList: webRoot + "/promotionBoard/getStatusList.do", //人员晋级状态

        //页签三
        getPromotionSelectRank: webRoot + "/promotionBoard/getPromotionSelectRank.do", //晋级薪酬模拟器（筛选显示的职级）
        getPromotionRankList: webRoot + "/promotionBoard/getPromotionRankList.do", //晋级薪酬模拟器（列表）
        getPromotionAddPersonList: webRoot + "/promotionBoard/getPromotionAddPersonList.do", //晋级薪酬模拟器(添加下拉)
        toEmpDetailUrl: webRoot + '/promotionBoard/toTalentDetailView.do'    //跳转到员工详情
    };
    $(win.document.getElementById('tree')).next().show();
    var tableHeight=$(window).height()-165;
    //win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var defaultDatas = {
        	organArr : new Array(),
        	organNum : 0
        }
    var prarr={organIdArr : new Array(),prarrNum : 0};
    var isdianji=0;   //如果有多层的，而没有点击最后一层的柱形，返回上级也有效果，比如北京
    var isdianInit=0;   //最上面的组织时，如果是只有两层，然后点击最后一层，不出现返回上级

    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 2
        });
    };

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        $.each($("#promoteCrowdCatagory div"),function(i){
        	$(this).removeClass("selected");
        	if(i==0){
        		$(this).addClass("selected");
        	}
        });
        timeLineObj.init(organId);

//        t.tabFirstLoaded = t.tabSecondLoaded = t.tabThreeLoaded = false;
        if(pageObj.tabFirstLoaded){
        	pageObj.initFirstTab(true);
        }

        if(pageObj.tabSecondLoaded){
        	if(pageObj.tabName=="page-two"){
        		pageObj.initSecondTab(true);
        	}else{
        		pageObj.lazyFreshenSecond=true;
        	}
        }
        if(pageObj.tabThreeLoaded&&pageObj.tabName=="page-three"){
        	pageObj.initThreeTab(false);
        }
//        switch (t.tabName) {
//            case "page-one": {
//                t.initFirstTab();
//                break;
//            }
//            case "page-two": {
//                t.initSecondTab();
//                break;
//            }
//            case "page-three": {
//                t.initThreeTab();
//                break;
//            }
//        }
    };

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
    timeLineObj.init(reqOrgId);

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart") {
            $(this).parents(".SetUpBody").find(".table-view").show();
            $(this).parents(".SetUpBody").find(".chart-view").hide();
            $(this).parents(".SetUpBody").attr("view", "table");
        } else {
            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
        }
    });

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

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

            pageObj.click(_page);
        }
    });

    var optionGrid = {
        borderWidth: 0,
        x: 40,
        x2: 40,
        y: 40,
        y2: 40
    };

    var language = {
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
    };

    $.fn.extend({
        loading: function () {
            var self = this;
            if (self.find(".msgText").length == 0) {
                self.html('<div class="msgText">数据加载中</div><div id="' + self.selector.replace("#", "") + 'Chart" class="chart hide"></div>');
            } else {
                self.find(".msgText").removeClass("hide").text("数据加载中");
                self.find(".chart").addClass("hide");
            }
        },
        noData: function () {
            var self = this;
            if (self.find(".msgText").length == 0) {
                self.html('<div class="msgText">暂无数据</div><div class="chart hide"></div>');
            } else {
                self.find(".msgText").removeClass("hide").text("暂无数据");
                self.find(".chart").addClass("hide");
            }
        },
        showData: function () {
            var self = this;
            self.find(".msgText").addClass("hide");
            self.find(".chart").removeClass("hide");
        }
    });

    var minDate = $("#minDate").val(), maxDate = $("#maxDate").val(), selectedDate = [];
    var dateObj = {
        number: 5,//设置相隔区间单位
        init: function () {
            var self = this;
            var max = new Date(maxDate), min = new Date(minDate);
            var maxYear = max.getYear() + 1900, minYear = min.getYear(), maxMonth = max.getMonth() + 1, minMonth = min.getMonth() + 1;
            var maxTotal = maxYear * 2 + (maxMonth > 6 ? 1 : 0), minTotal = minYear * 2 + (minMonth > 6 ? 1 : 0);
            if (maxTotal - minTotal > self.number - 1) {
                selectedDate = [parseInt((maxTotal - self.number + 1) / 2), ((maxTotal - self.number + 1) % 2 == 1 ? 2 : 1), maxYear, (maxMonth > 6 ? 2 : 1)];
            } else {
                selectedDate = [minYear, (minMonth > 6 ? 2 : 1), maxYear, (maxMonth > 6 ? 2 : 1)];
            }
        },
        getBeginDate: function (date) {
            return date[0] + "-" + (parseInt(date[1]) == 1 ? "01" : "07") + "-01";
        },
        getEndDate: function (date) {
            return date[2] + "-" + (parseInt(date[3]) == 1 ? "06-30" : "12-31")
        },
        getTrendBeginDate: function (date) {
            return date[0] + "-" + (parseInt(date[1]) == 1 ? "06-30" : "12-31") ;
        },
        getTrendEndDate: function (date) {
            return date[2] + "-" + (parseInt(date[3]) == 1 ? "06-30" : "12-31")
        }
    }
    dateObj.init();

    /*
     *************************************************************
     * 页签一开始
     * ***********************************************************
     * */

    //弹出框
    var applicationConformityDialogObj = {
        jqGrid0: null,
        jqGrid1: null,
        jqGrid2: null,
        gridId: 'applicationConformityGrid',
        gridPageId: 'applicationConformityGridPage',
        optionGrid: {
            url: '',
            mtype: 'POST',
            datatype: "json",
            width: 0,
            height: 0,
            colNames: ['姓名', '当前职级', '晋级目标', '申请状态'],
            colModel: [
                {name: 'name', index: 'name', sortable: false, align: 'center'},
                {name: 'rankName', index: 'organizationName', sortable: false, align: 'center'},
                {name: 'rankNameAfter', index: 'abilityLvName', sortable: false, align: 'center'},
                {
                    name: 'status',
                    index: 'status',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return parseInt(cellvalue) == 1 ? '已申请' : '未申请';
                    }
                }
            ],
            rownumbers: true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '#promoteTrackGridPage',
            rowHeight: 36,
            styleUI: 'Bootstrap',
            postData: {},
            loadComplete: function (xhr) {
                applicationConformityDialogObj.resize();
            }
        },
        init: function () {
            var self = this;
            self.render0();
            self.render1();
            self.render2();
            self.event();
        },
        render0: function () {
            var self = this;
            var pd = {organId: reqOrgId};
            if (self.jqGrid0) {
                self.jqGrid0.clearGridData().setGridParam({
                    postData: pd
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.postData = pd;
                self.optionGrid.url = urls.getAllEligibilityList;
                self.optionGrid.pager = self.gridPageId + "0";
                self.optionGrid.height = $("#acm").height() - 170;
                self.jqGrid0 = $("#" + self.gridId + "0").jqGrid(self.optionGrid);
            }
            self.resize();
        },
        render1: function () {
            var self = this;
            var pd = {organId: reqOrgId};
            if (self.jqGrid1) {
                self.jqGrid1.clearGridData().setGridParam({
                    postData: pd
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.postData = pd;
                self.optionGrid.url = urls.getEligibilityApplicationList;
                self.optionGrid.pager = self.gridPageId + "1";
                self.optionGrid.height = $("#acm").height() - 170;
                self.jqGrid1 = $("#" + self.gridId + "1").jqGrid(self.optionGrid);
            }
            self.resize();
        },
        render2: function () {
            var self = this;
            var pd = {organId: reqOrgId};
            if (self.jqGrid2) {
                self.jqGrid2.clearGridData().setGridParam({
                    postData: pd
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.postData = pd;
                self.optionGrid.url = urls.getEligibilityNotApplicationList;
                self.optionGrid.pager = self.gridPageId + "2";
                self.optionGrid.height = $("#acm").height() - 170;
                self.jqGrid2 = $("#" + self.gridId + "2").jqGrid(self.optionGrid);
            }
            self.resize();
        },
        event: function () {
            var self = this;
            $("#acm .acmselect1 div").unbind("click").on("click", function () {
                $("#acm .acmselect1 .selected").removeClass("selected");
                $(this).addClass("selected");
                $("#acm .acmtab").addClass("hide");
                var index = $(this).data("index");
                $("#acmtab" + index).removeClass("hide");
                if (index == 1) {
                    $("#eligibilityAll").text("符合条件已申请人员");
                } else if (index == 2) {
                    $("#eligibilityAll").text("符合条件未申请人员");
                } else {
                    $("#eligibilityAll").text("符合条件人员");
                }
                self.resize();
            });
        },
        resize: function () {
            var self = this;
            if (self.jqGrid0 && $("#acmtab0:visible").length > 0) {
                self.jqGrid0.setGridWidth($("#acm .acmbody").width());
            }
            if (self.jqGrid1 && $("#acmtab1:visible").length > 0) {
                self.jqGrid1.setGridWidth($("#acm .acmbody").width());
            }
            if (self.jqGrid2 && $("#acmtab2:visible").length > 0) {
                self.jqGrid2.setGridWidth($("#acm .acmbody").width());
            }
        }
    }

    //符合条件已申请
    var applicationConformityNumObj = {
        id: 'applicationConformityNum',
        data: null,
        init: function () {
            var self = this;
            $("#" + self.id + " .data").addClass("hide").eq(0).text("数据加载中").removeClass("hide");
            $.get(urls.getEligibilityApplication, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#" + self.id + " .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#" + self.id + " .data").addClass("hide").eq(0).text("暂无数据").removeClass("hide");
                }
            });
            self.event();
        },
        render: function () {
            var self = this;
            var data = self.data;
            $("#" + self.id + " .accord-yj-float-value").text(data.number);
            $("#" + self.id + " .accord-yj-float-datetime").text(data.year);
            $("#" + self.id + " .index-yj-div-bottom .index-yj-div-bottom-value").text(data.list.join('、'));
        },
        event: function () {
            var self = this;
            $("#" + self.id + " .top-div").unbind("click").on("click", function () {
                $("#applicationConformityModal").modal("show").on('shown.bs.modal', function () {
                    $("#acm").css({height: $(window).height() - 120 + "px"});
                    $("#acm .acmselect1 div").removeClass("selected").eq(0).addClass("selected");
                    $("#acm .acmtab").addClass("hide").eq(0).removeClass("hide");
                    applicationConformityDialogObj.init();
                });
            });
        }
    }

    //符合条件未申请
    var applicationUnConformityNumObj = {
        id: 'applicationUnConformityNum',
        data: null,
        init: function () {
            var self = this;
            $("#" + self.id + " .data").addClass("hide").eq(0).text("数据加载中").removeClass("hide");
            $.get(urls.getEligibilityNotApplication, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#" + self.id + " .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#" + self.id + " .data").addClass("hide").eq(0).text("暂无数据").removeClass("hide");
                }
            });
            self.event();
        },
        render: function () {
            var self = this;
            var data = self.data;
            $("#" + self.id + " .accord-yj-float-value").text(data.number);
            $("#" + self.id + " .accord-yj-float-datetime").text(data.year);
            $("#" + self.id + " .index-yj-div-bottom .index-yj-div-bottom-value").text(data.list.join('、'));
        },
        event: function () {
            var self = this;
            $("#" + self.id + " .top-div").unbind("click").on("click", function () {
                $("#applicationConformityModal").modal("show").on('shown.bs.modal', function () {
                    $("#acm").css({height: $(window).height() - 120 + "px"});
                    $("#acm .acmselect1 div").removeClass("selected").eq(2).addClass("selected");
                    $("#acm .acmtab").addClass("hide").eq(2).removeClass("hide");
                    $("#eligibilityAll").text("符合条件未申请人员");
                    applicationConformityDialogObj.init();
                });
            });
        }
    }

    //部分符合条件
    var applicationSomeConformityNumObj = {
        id: 'applicationSomeConformityNum',
        data: null,
        init: function () {
            var self = this;
            $("#" + self.id + " .data").addClass("hide").eq(0).text("数据加载中").removeClass("hide");
            $.get(urls.getSomeEligibility, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#" + self.id + " .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#" + self.id + " .data").addClass("hide").eq(0).text("暂无数据").removeClass("hide");
                }
            });
            self.event();
        },
        event: function () {
            $("#applicationSomeConformityNum .top-div").unbind("click").on("click", function () {
                var t = promoteStatusObj;
                var orgName = win.$(".dropDownValue").text();
                $("#organization").data("id", reqOrgId).text(orgName);
                t.postData = $.extend({}, t.postData, {
                    organId: reqOrgId,
                    empId: $("#promoteStatusText").val(),
                    rankName: '',
                    rankNameNext: '',
                    condition: 1,
                    isAll:false,
                    conditionBegin: $("#conditionProp").val(),
                    conditionEnd: 100
                });
                $('.leftBody div[page="page-two"]').click();

                if (!$("#promoteStatus .selectMore span").eq(0).hasClass("hide")) {
                    $("#promoteStatus .selectMore").click();
                }
                $("#positionRankText").val("");
                $("#condition").val("1").change();
                $("#conditionBegin").val($("#conditionProp").val());
                $("#conditionEnd").val("100");
                $("#promoteStatusSearchMore").click();
            });
        },
        render: function () {
            var self = this;
            var data = self.data;
            $("#" + self.id + " .accord-yj-float-value").text(data.number);
            $("#" + self.id + " .accord-yj-float-datetime").text(data.year);
        }
    }

    //晋级速度 Chart Option
    var contrastBarOption = {
        grid: {
            x: 44,
            y: 45,
            y2: 80,
            x2: 30,
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
            	show : true,
            	lineStyle: {
                    color: '#666666'
                }
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
            splitNumber:5,
            splitLine:  {
                show: true,
                lineStyle : {
                	color : '#e4e4e4'
                }
            },
            axisLine: {
            	show: true,
                onZero: false,
                lineStyle: {
                    width: 1,
                    color: '#666666'
                }
           },
            name: "（级/年）"
        }],
        series: [{
            type: 'bar',
            clickable: false,
            barCategoryGap: '45%',
            barMaxWidth: 30,
            itemStyle: {
                normal: {
                    color: '#0b98e0',
                    label: {
                        show: true,
                        textStyle: {
                            color: '#0b98e0'
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
                        color: '#6FB12D',
                        lineStyle: {
                            type: 'solid'
                        }
                    }
                },
                data: []
            }
        }]
    }

    //趋势分析
    var promoteTrendObj = {
        chart: null,
        chartId: 'promoteTrend',
        selectId: 'promoteTrendSelect',
        option: {
            tooltip: {
            	 trigger: 'axis',
                 axisPointer: {
                     lineStyle: {
                         color: '#999999',
                         width: 1,
                         type: 'dashed'
                     },
                 },
                 formatter: function (a) {
                     var arr = [];
                     $.each(a, function (i, item) {
                         if (i == 0) {
                             arr.push(a[i].name + "<br>");
                         } else {
                             arr.push('<br>');
                         }
                         if(item.seriesIndex==1){
                        	 
                        	 if(item.data=="-"){
                        		 arr.push(item.seriesName + "：" +  item.data);
                        	 }else{
                        		 arr.push(item.seriesName + "：" +  item.data+"%");
                        	 }
                         }else{
                        	 arr.push(item.seriesName + "：" +  item.data);
                         }
                         
                     });
                     return arr.join('');
                 }
            },
            legend: {
                data: ["","环比变化"],
                y: 295,
                selectedMode: false
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: true ,
                        onZero: false,
                        lineStyle: {
                            color: "#666666",
                            width: 1
                        }
                    },
                    axisTick: {
                    	show : true,
                    	lineStyle: {
                            color: '#666666'
                        }
                    },
                    axisLabel: {
                        rotate: 10
                    },
                    position: 'bottom'
                }
            ],
            yAxis: [
                {
                    type: "value",
                    splitNumber:5,
                    splitLine: {
                    	 show: true,
                    	 lineStyle : {
                         	color : '#e4e4e4'
                         }
                    },
                    //min: 0,
                    //max: 1,
                    name: "（级/年）",
                    axisLine: {
                    	 show: true,
                         onZero: false,
                         lineStyle: {
                             width: 1,
                             color: '#666666'
                         }
                    }
                },
                {
                    type: "value",
                    splitNumber:5,
                    axisLine: {
                   	 show: true,
                        onZero: false,
                        lineStyle: {
                            width: 1,
                            color: '#666666'
                        }
                   },
                    splitLine: {
                        show: false
                    },
                    //min: 0,
                    //max: 100,
                    axisLabel: {
                        show: true,
                        formatter: '{value}%'
                    }
                }
            ],
            series: [
                {
                    name: "平均晋级速度",
                    type: "bar",
                    yAxisIndex: 0,
                    clickable: false,
                    barMaxWidth: 30,
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    barGap: 0
                },
                {
                	name: '环比变化',
                    type: 'line',
                    data: [],
                    yAxisIndex: 1,
                    clickable: false,
                    itemStyle: {
                        normal: {
                            label: false
                        }
                    }
                }
            ],
            grid: $.extend({}, optionGrid, {x: 44,x2: 41, y: 45,y2:80}),
            color: ["#f5b147","#0b98e0"]
        },
        data: null,
        date: [],
        crowd: [],
        init: function () {
            var self = this;
            self.date = selectedDate;
            self.crowd = [''];
            self.request();

            $("#" + self.selectId).selection({
                dateType: 6,
                dateRange: {
                    min: minDate,
                    max: maxDate
                },
                dateSelected:self.date,
                dateSelectedLength:5,
                //crowdSelected:self.crowd,
                ok:function(event, data){
                    self.date=data.date;
                   // self.crowd=data.crowd;
                    self.request();
                }
            });
        },
        request: function () {
            var self = this;
            var $obj = $("#" + self.chartId);

            $obj.loading();
            var t = dateObj;
            var beginDate = t.getTrendBeginDate(self.date);
            var endDate = t.getTrendEndDate(self.date);
            $.get(urls.getTrendAnalysis, {
                organId: reqOrgId,
                beginDate: beginDate,
                endDate: endDate,
                populationIds: self.crowd.join(',')
            }, function (data) {
                if (data && data.length > 0) {
                    $obj.showData();
                    self.data = data;
                    self.render();
                } else {
                    $obj.noData();
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data || [];

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId + "Chart");

            //option
            var xAxisData = [], seriesData1 = [], seriesData2 = [];
            $.each(data, function (i, item) {
                xAxisData.push(item.name);
                seriesData1.push(Tc.formatFloat(item.avg));
                if(item.increase!=="-"){
                	 seriesData2.push(Tc.formatFloat(item.increase * 100));
                }else{
                	 seriesData2.push(item.increase);
                }
               
            });
            self.option.xAxis[0].data = xAxisData;//["2012年", "2013年", "2014年", "2015年", "2016年"];
            self.option.series[0].data = seriesData1;//[0.4,0.7,0.75,0.2,0.4];
            self.option.series[1].data = seriesData2;//[110,150,110,200,250];

            self.chart.setOption(self.option);
            self.chart.refresh();
            self.resize();
        },
        resize: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

  //下级组织分析弹出框
    var applicationOrganDialogObj = {
        jqGrid0: null,
        gridId: 'applicationOrganGrid0',
        gridPageId: 'applicationOrganGridPage0',
        optionGrid: {
            url: '',
            mtype: 'POST',
            datatype: "json",
            width: 0,
            height: 0,
            colNames: ['姓名', '当前职级', '晋级速度'],
            colModel: [
                {name: 'userName', index: 'userName', sortable: false, align: 'center'},
                {name: 'rankName', index: 'rankName', sortable: false, align: 'center',
               	 formatter: function (cellvalue, options, rowObject) {
                     return cellvalue==""?"-":cellvalue;
                 }	},
                {name: 'rank', index: 'rank', sortable: false, align: 'center',
                	 formatter: function (cellvalue, options, rowObject) {
                         return Tc.formatFloat(cellvalue);
                     }	
                }
            ],
            rownumbers: true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '#promoteTrackGridPage',
            rowHeight: 36,
            styleUI: 'Bootstrap',
            postData: {},
            loadComplete: function (xhr) {
            	applicationOrganDialogObj.resize();
            }
        },
        init: function (childOrId) {
            var self = this;
            if(childOrId!=null&&childOrId!=""){
            	  self.render0(childOrId);
            }
        },
        render0: function (childOrId) {
            var self = this;
            var pd = {organId: childOrId};
            if (self.jqGrid0) {
                self.jqGrid0.clearGridData().setGridParam({
                    postData: pd
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.postData = pd;
                self.optionGrid.url = urls.getOrgAnalysisPerJinsuList;
                self.optionGrid.pager = self.gridPageId;
                //self.optionGrid.height = $("#acmOrgan").height() - 170;
                self.optionGrid.height =$("#acmOrgan").height() - 100;
                self.jqGrid0 = $("#" + self.gridId).jqGrid(self.optionGrid);
            }
            self.resize();
        },
        resize: function () {
            var self = this;
            if (self.jqGrid0 && $("#acmtabOrgan:visible").length > 0) {
            	 self.jqGrid0.setGridWidth(700);
            }
        }
    }
 var initFlag=false;
    //下级组织分析
    var promoteSubOrgObj = {
        chart: null,
        chartId: 'promoteSubOrg',
        selectId: 'promoteSubOrgSelect',
        returnOrgan: '#returnOrgan',
    	returnOrganText: '.returnOrganText',
    	/*returnTeamText: '.returnTeamText',*/
        option: $.extend({}, Tc.cloneObj(contrastBarOption)),
        organId:null,
        data: null,
        date: [],
        crowd: [],
        da:[],
//        grid: $.extend({}, optionGrid, {x2: 41,y2:60})
        //因为图表中的横线没有图例，故用zrender画一个
        initLineLengend: function () {
            var _ZR = this.chart.getZrender();
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 65,
                    y: 310,
                    color: '#6FB12D',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑'
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 40,
                    y: 310,
                    color: '#444',
                    text: '当前组织平均晋级速度',
                    textAlign: 'left',
                    textFont: '14px Microsoft YaHei, Arial, Helvetica, sans-serif, 宋体'
                },
                hoverable: false
            }));
            _ZR.refresh();
        },
        init: function (organId,initFlags) {
        	initFlag=initFlags;
            var self = this;
            this.organId = organId;
            self.date = selectedDate;
            self.crowd = [''];
            self.request(initFlags);
           // self.option.grid.y2=120;
            $("#" + self.selectId).selection({
                dateType: 6,
                dateRange: {
                    min: minDate,
                    max: maxDate
                },
                dateSelected:self.date,
                dateSelectedLength:10,
               // crowdSelected:self.crowd,
                ok:function(event, data){
                    self.date=data.date;
                   // self.crowd=data.crowd;
                    self.request(initFlags);
                }
            });
            self.returnBtnClick();
           // self.childJinsuEvent();
        },
        request: function (initFlags) {
            var self = this;
            var $obj = $("#" + self.chartId);
            if(defaultDatas.organNum == 0){
				/*$(self.returnTeamText).addClass('hide');*/
				$(self.returnOrganText).addClass('hide');
    		}
            $obj.loading();
            var t = dateObj;
            var beginDate = t.getBeginDate(self.date);
            var endDate = t.getEndDate(self.date);
            var parentOrgid='';
            parentOrgid=defaultDatas.organArr[defaultDatas.organNum-1];
            $.get(urls.getOrgAnalysis, {
                organId: promoteSubOrgObj.organId,
                beginDate: beginDate,
                endDate: endDate,
                populationIds: self.crowd.join(',')
            }, function (data) {
            	if(data&&data.list!=null){
            		if (data && data.list.length > 0) {
            			isdianji=isdianji+1;
                        $obj.showData();
                        self.data = data;
                        self.render();
                    } else {
                    	if(initFlags){
                    		 $obj.noData();
                    	}else{
                    		  $.get(urls.getOrgAnalysis, {
                                  organId: parentOrgid,
                                  beginDate: beginDate,
                                  endDate: endDate,
                                  populationIds: self.crowd.join(',')
                              }, function (data) {
                              	if(data&&data.list!=null){
                              		if (data && data.list.length > 0) {
                                          $obj.showData();
                                          self.data = data;
                                          self.render();
                                      	self.childJinsuEvent(promoteSubOrgObj.organId);
                                          isdianji=true;
                                          isdianInit=isdianInit+1;
                                          self.chart.resize();
                                      }
                              	}
                               
                              });
                    	}
                    }
            	}else{
            		 $obj.noData();
            	}
                
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId + "Chart");
            self.initLineLengend();

            var xAxisData = [], seriesData1 = [], seriesData2 = [];
            var list = _.sortBy(data.list, function (item) {
                return -Tc.formatFloat(item.avg);
            });
            var da=[];
            var d={};
            $.each(list, function (i, item) {
                xAxisData.push(item.name);
                seriesData1.push({value:Tc.formatFloat(item.avg),id:item.id,parOrganId:item.parOrganId});
            });
           
            var len = seriesData1.length;
            var num = 4, dataZoom = {};//4个以上显示滚动条
            if (len > num) {
                dataZoom = {
                		  show: true,
                          realtime: true,
                          height: 20,
                          zoomLock: true,   //当设置为true时选择区域不能伸缩
                          showDetail: false,
                          start: 0,
                          end: 30,
                          y: 330
                };
            }
            self.da=da;
           
            self.option.dataZoom = dataZoom;
            self.option.xAxis[0].data = xAxisData;
            self.option.series[0].data = seriesData1;
            self.option.series[0].markLine.data = self.packMarkLineData(self.option.series[0].data.length, Tc.formatFloat(data.avg));
			if(defaultDatas.organNum <=100){
				//当鼠标放到柱状图形上时出现手抓，并给点击事件
				self.option.series[0].clickable = true;
				self.chart.un(ecConfig.EVENT.CLICK).on(ecConfig.EVENT.CLICK, self.clickEvent);
			} else {
				self.option.series[0].clickable = false;
				self.chart.un(ecConfig.EVENT.CLICK);
			}
            self.chart.setOption(self.option, true);
            self.chart.resize();
        },
        clickEvent: function (param) {
        	if (typeof param.seriesIndex != 'undefined') {
				if(param.value != 0){
					var flagNum=prarr.prarrNum;
					if(prarr.prarrNum!=0){
					
						if(prarr.organIdArr[prarr.prarrNum-1]!=param.data.parOrganId){
							prarr.organIdArr[prarr.prarrNum]= param.data.parOrganId;
							prarr.prarrNum=prarr.prarrNum+1;
						}
					}else{
						prarr.organIdArr[prarr.prarrNum]= param.data.parOrganId;
						prarr.prarrNum=prarr.prarrNum+1;
					}
					defaultDatas.organArr[defaultDatas.organNum] = param.data.parOrganId;
					promoteSubOrgObj.organId = param.data.id;
					clearEChart(promoteSubOrgObj.chart);
					//defaultDatas.organArr[defaultDatas.organNum + 1] = param.data.id;
					defaultDatas.organNum = defaultDatas.organNum + 1;
					/*if(initFlag){
						promoteSubOrgObj.showReturnBtn(false);
					}else{*/
						promoteSubOrgObj.showReturnBtn(true, prarr.prarrNum,initFlag);
					//}
					promoteSubOrgObj.request();
					initFlag=false;
					
//		    		teamRankingObj.setOption();
				}
			}
       },
		returnBtnClick: function(){
			var self = this;
			$(self.returnOrgan).unbind('click').bind('click', function(){
				clearEChart(promoteSubOrgObj.chart);
				if(prarr.prarrNum >= 0){
					if(prarr.prarrNum==2&&isdianji>=3){
						self.showReturnBtn(true, prarr.prarrNum-1);
						self.organId =prarr.organIdArr[prarr.prarrNum-1];
					}else if(prarr.prarrNum==2){
						self.showReturnBtn(false);
						self.organId =prarr.organIdArr[prarr.prarrNum-2];
					}else if(prarr.prarrNum>2){
						self.showReturnBtn(true, prarr.prarrNum-1);
						self.organId =prarr.organIdArr[prarr.prarrNum-2];
					}else{
						self.showReturnBtn(false);
						self.organId =prarr.organIdArr[prarr.prarrNum-1];
					}
				} else {
					self.organId = reqOrgId;
					self.showReturnBtn(false);
				}
				/*if(prarr.prarrNum>=2){
					self.organId =prarr.organIdArr[prarr.prarrNum-2];
				}else{
					self.organId =prarr.organIdArr[prarr.prarrNum-1];
				}*/
				
				promoteSubOrgObj.request();
				prarr.prarrNum=prarr.prarrNum-1;
			});
		},
		showReturnBtn: function(flag, num,initFlags){
			var self = this;
			if(flag){
				/*if(initFlags&&num==1){
					$(self.returnOrgan).addClass('hide');
				}else{*/
					if(num > 1){
						//prarr.prarrNum=prarr.prarrNum-1;
						/*$(self.returnTeamText).removeClass('hide').siblings().addClass('hide');*/
					} else {
						$(self.returnOrganText).removeClass('hide').siblings().addClass('hide');
					}
					$(self.returnOrgan).removeClass('hide');
				//}
				
			} else {
				$(self.returnOrgan).addClass('hide');
				if(!initFlag){
					defaultDatas.organNum=0;
					isdianji=0;
				}
				
			}
		},
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}]];
        },
        childJinsuEvent: function (childOrId) {
            var self = this;
            $("#applicationOrganModal").modal("show");
            $("#applicationOrganModal-childOrId").val(childOrId);
//            $("#acmOrgan").css({height: $(window).height() - 120 + "px"});
//            applicationOrganDialogObj.init(childOrId);
        },
        resize: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }
    $('#applicationOrganModal').on('shown.bs.modal', function () {
    	$("#acmOrgan").css({height: $(window).height() - 120 + "px"});
        applicationOrganDialogObj.init($("#applicationOrganModal-childOrId").val());
    	})
    /**
     * 清除echart面板
     * @param eChartObj
     */
    function clearEChart(eChartObj){
    	if(eChartObj){
    		eChartObj.clear();
    	}
    };
    //序列统计
    var promoteSequenceObj = {
        chart: null,
        chartId: 'promoteSequence',
        selectId: 'promoteSequenceSelect',
        option: $.extend({}, Tc.cloneObj(contrastBarOption)),
        data: null,
        date: [],
        crowd: [],
        //因为图表中的横线没有图例，故用zrender画一个
        initLineLengend: function () {
            var _ZR = this.chart.getZrender();
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 65,
                    y: 310,
                    color: '#6FB12D',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑'
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 40,
                    y: 310,
                    color: '#444',
                    text: '当前组织平均晋级速度',
                    textAlign: 'left',
                    textFont: '14px Microsoft YaHei, Arial, Helvetica, sans-serif, 宋体'
                },
                hoverable: false
            }));
            _ZR.refresh();
        },
        init: function () {
            var self = this;
            self.date = selectedDate;
            self.crowd = [''];
            self.request();

            $("#" + self.selectId).selection({
                dateType: 6,
                dateRange: {
                    min: minDate,
                    max: maxDate
                },
                dateSelected:self.date,
                dateSelectedLength:10,
               // crowdSelected:self.crowd,
                ok:function(event, data){
                    self.date=data.date;
                   // self.crowd=data.crowd;
                    self.request();
                }
            });
        },
        request: function () {
            var self = this;
            var $obj = $("#" + self.chartId);

            $obj.loading();
            var t = dateObj;
            var beginDate = t.getBeginDate(self.date);
            var endDate = t.getEndDate(self.date);
            $.get(urls.getSequenceAnalysis, {
                organId: reqOrgId,
                beginDate: beginDate,
                endDate: endDate,
                populationIds: self.crowd.join(',')
            }, function (data) {
                if (data && data.list.length > 0) {
                    $obj.showData();
                    self.data = data;
                    self.render();
                } else {
                    $obj.noData();
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId + "Chart");
            self.initLineLengend();

            var xAxisData = [], seriesData1 = [], seriesData2 = [];
            var list = _.sortBy(data.list, function (item) {
                return -Tc.formatFloat(item.avg);
            });
            $.each(list, function (i, item) {
                xAxisData.push(item.name);
                seriesData1.push(Tc.formatFloat(item.avg));
            });
            self.option.xAxis[0].data = xAxisData;
            self.option.series[0].data = seriesData1;
            self.option.series[0].markLine.data = self.packMarkLineData(self.option.series[0].data.length, Tc.formatFloat(data.avg));
            self.chart.setOption(self.option, true);

        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}]];
        },
        resize: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //人群统计
    var promoteCrowdObj = {
        chart: null,
        chartId: 'promoteCrowd',
        selectId: 'promoteCrowdSelect',
        option: $.extend({}, Tc.cloneObj(contrastBarOption)),
        dataKeytalent: null,
        dataPerformance: null,
        date: [],
        crowd: [],
        categoryId: 0,
        //因为图表中的横线没有图例，故用zrender画一个
        initLineLengend: function () {
            var _ZR = this.chart.getZrender();
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 65,
                    y: 310,
                    color: '#6FB12D',
                    text: '—',
                    textAlign: 'left',
                    textFont: 'bolder 20px 微软雅黑'
                },
                hoverable: false
            }));
            _ZR.addShape(new TextShape({
                style: {
                    x: _ZR.getWidth() / 2 - 40,
                    y: 310,
                    color: '#444',
                    text: '当前组织平均晋级速度',
                    textAlign: 'left',
                    textFont: '14px Microsoft YaHei, Arial, Helvetica, sans-serif, 宋体'
                },
                hoverable: false
            }));
            _ZR.refresh();
        },
        init: function () {
            var self = this;
            self.date = selectedDate;
            self.crowd = [''];
            self.categoryId = 0,
                self.request();

            $("#" + self.selectId).selection({
                dateType: 6,
                dateRange: {
                    min: minDate,
                    max: maxDate
                },
                dateSelected:self.date,
                dateSelectedLength:10,
                //crowdSelected:self.crowd,
                ok:function(event, data){
                    self.date=data.date;
                    //self.crowd=data.crowd;
                    self.request();
                }
            });

            self.event();
        },
        request: function () {
            var self = this;
            var $obj = $("#" + self.chartId);

            $obj.loading();
            var t = dateObj;
            var beginDate = t.getBeginDate(self.date);
            var endDate = t.getEndDate(self.date);
            $.get(urls.getKeyTalentAnalysis, {
                organId: reqOrgId,
                beginDate: beginDate,
                endDate: endDate,
                populationIds: self.crowd.join(',')
            }, function (data) {
                if (data) {
                    $obj.showData();
                    self.dataKeytalent = data;
                    self.render();
                } else {
                    $obj.noData();
                }
            });

            $.get(urls.getPerformanceAnalysis, {
                organId: reqOrgId,
                beginDate: beginDate,
                endDate: endDate,
                populationIds: self.crowd.join(',')
            }, function (data) {
                if (data) {
                    $obj.showData();
                    self.dataPerformance = data;
                    self.render();
                } else {
                    $obj.noData();
                }
            });
        },
        render: function () {
            var self = this;
            if (parseInt(self.categoryId) == 0) {
                self.render2(self.dataKeytalent);
            } else if (parseInt(self.categoryId) == 1) {
                self.render2(self.dataPerformance);
            }
        },
        render2: function (data) {
            if (data) {
                var self = this;

                if (self.chart) {
                    self.chart.clear();
                }
                self.chart = initEChart(self.chartId + "Chart");
                self.initLineLengend();

                var xAxisData = [], seriesData1 = [], seriesData2 = [];
                $.each(data.list, function (i, item) {
                    xAxisData.push(item.name);
                    seriesData1.push(Tc.formatFloat(item.avg));
                });
                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData1;
                self.option.series[0].markLine.data = self.packMarkLineData(self.option.series[0].data.length, Tc.formatFloat(data.avg));
                self.chart.setOption(self.option, true);
            }
        },
        packMarkLineData: function (cateLen, value) {
            return [[{xAxis: -1, yAxis: value},
                {xAxis: cateLen + 1, yAxis: value}]];
        },
        event: function () {
            var self = this;
            $("#promoteCrowdCatagory div").on("click", function () {
                $(this).parent().find(".selected").removeClass("selected");
                $(this).addClass("selected");
                var categoryId = $(this).data("categoryid");
                self.categoryId = categoryId;
                self.render();
            });
        },
        resize: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    //晋级轨迹
    var promoteTrackObj = {
        chart: null,
        jqGrid: null,
        chartId: 'promoteTrackChart',
        gridId: 'promoteTrackGrid',
        optionChart: {
            tooltip: {
            	 trigger: 'axis',
                 axisPointer: {
                     lineStyle: {
                         color: '#999999',
                         width: 1,
                         type: 'dashed'
                     },
                 },
                formatter: function (a) {
                    var arr = [];
                    $.each(a, function (i, item) {
                        if (i == 0) {
                            arr.push(a[i].name + "<br>");
                        } else {
                            arr.push('<br>');
                        }
                       // arr.push(item["0"] + "：PM" + item.data);
                        if(item.data.name=="-")
                        	 arr.push(item.seriesName + "：" +  item.data.name);
                        else
                        	arr.push(item.seriesName + "：" +  item.data.name+item.data.rankVal);
                    });
                    return arr.join('');
                }
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    name: "（时间）",
                    /*nameTextStyle: {
                        color: "rgb(204, 204, 204)"
                    },*/
                    axisLine: {
                      	 show: true,
                           onZero: false,
                           lineStyle: {
                               width: 1,
                               color: '#666666'
                           }
                       },
                       axisTick: {
                       	show : true,
                       	lineStyle: {
                               color: '#666666'
                           }
                       },
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        rotate: 20
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    name: "（职级）",
                    axisLine: {
                      	 show: true,
                           onZero: false,
                           lineStyle: {
                               width: 1,
                               color: '#666666'
                           }
                       },
                    splitLine:  {
                        show: true,
                        lineStyle : {
                        	color : '#e4e4e4'
                        }
                    },
                    min: 0,
                    max: 0,
                    splitNumber: 0,
                    boundaryGap: true,
                    axisLabel: {
                        formatter: "{value}级"
                    }
                }
            ],
            series: [],
            grid: {
                borderWidth: 0,
                x: 45,
                y: 40,
                x2: 60,
                y2: 60
            },
            color: ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed"]
        },
        optionGrid: {
            url: urls.getTrackList,
            mtype: 'POST',
            datatype: "json",
            width:400,
            height: 363,
            scrollOffset:0,
            colNames: ['姓名', '最新绩效', '晋级速度', '总晋级数', '操作'],
            colModel: [
                {name: 'userName', index: 'userName', sortable: false, align: 'center',
                	formatter: function (value, options, row) {
                        return "<a href='javascript:void(0)' data='" + row.empId + "' class='talent_col' >" + value + "</a>";
                    }
                },
                {name: 'performanceName', index: 'performanceName', sortable: false, align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue==""||cellvalue=="null"||cellvalue==null?"-":cellvalue;
                    }
                },
                {
                    name: 'rank',
                    index: 'rank',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return Tc.formatFloat(cellvalue);
                    }
                },
                {name: 'total', index: 'total', sortable: false, align: 'center',
                	formatter: function (cellvalue, options, rowObject) {
                        return cellvalue==null?"-":cellvalue;
                    }	
                },
                {
                    name: 'opt',
                    index: 'opt',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        var arr = [];
                        arr.push('<a href="javascript:;" class="promoteTrackOpt add" data-key="' + rowObject.empId + '" data-name="' + rowObject.userName + '">对比轨迹</a>');
                        arr.push('<a href="javascript:;" class="promoteTrackOpt cancel hide" data-key="' + rowObject.empId + '" data-name="' + rowObject.userName + '">取消对比</a>');
                        return arr.join('');
                    }
                }
            ],
            rownumbers: true,
            rownumWidth:50,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '#promoteTrackGridPage',
            rowHeight: 36,
            styleUI: 'Bootstrap',
            postData: {},
            loadComplete: function (xhr) {
                var self = promoteTrackObj;
//                $("#promoteTrackGridPage").find(".ui-paging-info").html("共 "+xhr.records+" 条");
                $("#promoteTrackGridPage").find(".ui-paging-info").parent().hide();
                $("#promoteTrackGridPage_left").remove();
                $("#promoteTrack a").unbind("click").on("click", function () {
                    var value = $(this).data("key");
                    var key = $(this).data("name");
                    var obj = $(this).parent().find("a");
                    if ($(this).hasClass("add")) {
                        if (self.requestIds.length > 4) {
                            showErrMsg("最多加入5位人员.");
                        } else {
                            obj.addClass("hide").eq(1).removeClass("hide");
                            self.addToComparison({k: key, v: value}, this);
                        }
                    } else {
                        obj.addClass("hide").eq(0).removeClass("hide");
                        self.removeComparison({k: key, v: value}, this);
                    }
                });
                if (self.setRequestIds) {
                    var empIds = [];
                    $.each(xhr.rows, function (i, item) {
                        if (i < 5) {
                            empIds.push({k: item.userName, v: item.empId});
                        }
                    });
                    self.requestIds = empIds;
                    self.setRequestIds = false;
                }
                self.requestChart();
                toEmpDetail();
            }
        },
        dataChart: null,
        dataGrid: null,
        requestIds: [],
        setRequestIds: false,
        empId: null,
        init: function () {
            var self = this;
            self.setRequestIds = true;
//            self.select();
            self.renderGrid();
            $("#" + self.chartId).loading();

            //事件
            $("#promoteTrackSearch").unbind("click").click(function () {
                self.empId = $("#promoteTrackSelect").val();
                self.renderGrid();
            });
        },
        requestChart: function () {
            var self = this;
            var $obj = $("#" + self.chartId);


            if (self.requestIds.length > 0) {
                $obj.loading();
                var empId = [];
                $.each(self.requestIds, function (i, item) {
                    empId.push(item.v);
                });
                $.get(urls.getTrackChart, {organId: reqOrgId, empIds: empId.join(',')}, function (data) {
                    if (data) {
                        $obj.showData();
                        self.dataChart = data;
                        self.renderChart();
                    } else {
                        $("#" + self.chartId).parents(".promoteTrack").find(".value").html('');
                        $("#promoteTrackGrid .add").removeClass("hide");
                        $("#promoteTrackGrid .cancel").addClass("hide");
                        $obj.noData();
                    }
                });
            } else {
                $("#" + self.chartId).parents(".promoteTrack").find(".value").html('');
                $("#promoteTrackGrid .add").removeClass("hide");
                $("#promoteTrackGrid .cancel").addClass("hide");
                $obj.noData();
            }
        },
        renderChart: function () {
            var self = this;
            var data = self.dataChart;
          
            var arr = [];
            var colorArr = ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed"];
            var empIdArr=[];
        	var vra={empId:'',color:''};
            $.each(self.requestIds, function (i, item) {
            	vra={empId:item.v,color:colorArr[i]};
            	empIdArr.push(vra);
                arr.push('<span class="item unselectable" style="border: 1px solid ' +colorArr[i] + ';color: ' + colorArr[i] + ';margin-top:20px;">' + item.k + '<i data-id="' + item.v + '" class="icon-remove-sign" title="删除"></i></span>');
            });
            $("#" + self.chartId).parents(".promoteTrack").find(".value").html(arr.join(''));

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId + "Chart");

            var showNum = 5;
            var maxYear = (_.max(data, function (m) {
                return m.years
            })).years;
            var minYear = (_.min(data, function (m) {
                return m.years
            })).years;
            
            var maxYear = isNaN(maxYear)?0:maxYear;
            var minYear = isNaN(minYear)?0:minYear;
            
            if ((maxYear - minYear + 1) > showNum) {
                minYear = maxYear - showNum + 1;
            }
            var empIds = [];
            $.each(self.requestIds, function (i, item) {
                if (!_.contains(empIds, item.v)) {
                    empIds.push(item.v);
                }
            });
            var xAxisData = [];
            for (var i = minYear; i <= maxYear; i++) {
                xAxisData.push(parseInt(i / 2) + "年" + (i % 2 > 0 ? "下半年" : "上半年"));
            }
            if(minYear==maxYear&&maxYear==0){
            	xAxisData=[""];
            }
            var seriesData = [], all = [];
            var colorArr1 = [];
            $.each(empIds, function (j, empId) {
            	for(var s=0;s<empIdArr.length;s++){
            		if(empId==empIdArr[s].empId){
            			colorArr1[j]=empIdArr[s].color;
            		}
            	}
                var da = [], name = '';
                for (var i = minYear; i <= maxYear; i++) {
                    var value = 0;
//                    var d={name:"PM",rankVal:value, value: value};
                    var d={name:"-",rankVal:'-', value: '-'};
                    if(data.length>0){
                    	 $.each(data, function (m, item) {
                             if (item.empId == empId && item.years == i) {
                                 value = Tc.formatFloat(item.currRank);
                                 d = {name: item.rankName,rankVal:value, value: value}
                             }
                         });
                    }
                   // da.push(value);
                    da.push(d);
                    all.push(value);
                }
                $.each(self.requestIds, function (i, item) {
                    if (item.v == empId) {
                        name = item.k;
                    }
                });
                var obj = {
                    name: name,
                    type: "line",
                    data: da,
                    symbolSize: 2,
                    itemStyle: {
                        normal: {
                        	color: colorArr1[j]
                        	/*label: {
                                show: true
                                //formatter: "PM{c}"
                                formatter: function(obj){
                                	return obj.data.name+obj.data.rankVal;
                                }
                            }*/
                        }
                    },
                    symbol: "circle"
                };
                seriesData.push(obj);
            });

            var min = parseInt(_.min(all));
            var max = parseInt(_.max(all) + 1);
//            if(xAxisData.length==0){
//            	xAxisData.push("");
//            }
            self.optionChart.xAxis[0].data = xAxisData;
            self.optionChart.yAxis[0].min = isNaN(min)?0:min;
            self.optionChart.yAxis[0].max =isNaN(max)?0:max;
            self.optionChart.yAxis[0].splitNumber = self.optionChart.yAxis[0].max - self.optionChart.yAxis[0].min;
            self.optionChart.series = seriesData;
            self.chart.setOption(self.optionChart, true);

            self.eventChart();
            self.resetComparisonButton();
        },

        eventChart: function () {
            var self = this;
            $("#" + self.chartId).parents(".promoteTrack").find(".value i").unbind("click").on("click", function () {
                $(this).parent().remove();
                var reqIds = [];
                $("#" + self.chartId).parents(".promoteTrack").find(".value span").each(function (i, item) {
                    reqIds.push({k: $(this).text(), v: $(this).find("i").data("id")});
                });
                self.requestIds = reqIds;
                self.requestChart();
            });
        },
        renderGrid: function () {
            var self = this;
            var pd = {organId: reqOrgId, empId: self.empId};
            if (self.jqGrid) {
                self.jqGrid.clearGridData().setGridParam({
                    postData: pd
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.postData = pd;
                self.jqGrid = $("#" + self.gridId).jqGrid(self.optionGrid);
            }
            self.resizeGrid();
            self.resetComparisonButton();
        },
        select: function () {
            $("#promoteTrackSelect").select2({
                language: language,
                width: '200',
                allowClear: true,
                multiple: false,
                openOnEnter: true,
                placeholder: "输入员工姓名",
                ajax: {
                    url: urls.getTrackSelect,
                    dataType: 'json',
                    delay: 500,
                    type: "POST",
                    data: function (params, page) {
                        var ps = {
                            organId: reqOrgId,
                            key: params && params.term ? params.term : "",
                            page: params.page == null ? 1 : params.page,
                            rows: 30
                        };
                        return ps;
                    },
                    processResults: function (data) {
                        var lists = [];
                        $.each(data.list, function (i, item) {
                            lists.push({id: item.empId, text: item.userName});
                        });
                        return {
                            results: lists,
                            pagination: {
                                more: data.more > 0
                            }
                        };
                    }
                }
            }).val(null).trigger("change");
        },
        eventGrid: function () {
            var self = this;
            $("#promoteTrackSearch").unbind("click").click(function () {
                self.keyword = $("#promoteTrackSelect").val();
                self.renderGrid();
            });
        },
        addToComparison: function (obj, t) {
            var self = this;
            self.requestIds.push(obj);
            self.requestChart();
        },
        removeComparison: function (obj, t) {
            var self = this;
            var arr = [];
            $.each(self.requestIds, function (i, item) {
                if (item.v != obj.v) {
                    arr.push(item);
                }
            });
            self.requestIds = arr;
            self.requestChart();
        },
        resetComparisonButton: function () {
            var self = this;
            var obj = $("#promoteTrackGrid .promoteTrackOpt");
            obj.parent().find(".add").removeClass("hide");
            obj.parent().find(".cancel").addClass("hide");
            $.each(self.requestIds, function (i, item) {
                obj.parent().find('a[data-key="' + item.v + '"]').parent().find("a").addClass("hide").eq(1).removeClass("hide");
            });
        },
        resize: function () {
            var self = this;
            self.resizeChart();
            self.resizeGrid();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqGrid) {
                self.jqGrid.setGridWidth($("#promoteTrack").width()-15);
            }
        }
    }

    /*
     *************************************************************
     * 页签二开始
     * ***********************************************************
     * */
    //人员晋级状态
    var promoteStatusObj = {
        chart: [],
        jqGrid: null,
        gridId: 'promoteStatusGrid',
        optionGrid: {
            url: urls.getStatusList,
            mtype: 'POST',
            datatype: "json",
            width:960,
            height: 2680,
            scrollOffset:0,
            colNames: ['基本信息','晋级目标','晋级要求','个人状态', '是否符合', '晋级轨迹'],
            colModel: [
                {
                    name: 'name',
                    index: 'name',
                    width: 80,
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        var imgPath = webRoot + (rowObject.imgPath == null || rowObject.imgPath == "" ? "/assets/img/man.png" : rowObject.imgPath);
                        var arr = [];
                      arr.push('<div class="prostatusimg" ><img src="' + imgPath +'" data="' + rowObject.empId + '"  class="talent_col" id="renImg">');
                      arr.push("</br>"+rowObject.userName);
                      arr.push("</br>"+rowObject.organizationName);
                      arr.push("</br>"+((rowObject.rankName==null||rowObject.rankName=="null")?"-":rowObject.rankName)+"</div>");
                        return arr.join('')
                    }
                },
                {
                    name: 'target',
                    index: 'target',
                    width: 75,
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                    	var rankNameAf=rowObject.rankNameNext!=null?rowObject.rankNameNext:"-";
                        return  rankNameAf+ "<br>(" + (rowObject.status > 0 ? "已申请" : "未申请") + ")";
                    }
                },
                {
                    name: 'condition1',
                    index: 'condition1',
                    width:170 ,
                    sortable: false,
                    align: 'left',
                    formatter: function (cellvalue, options, rowObject) {
                        var arr = [];
                        if (rowObject.listReq.length > 0) {
                            $.each(rowObject.listReq, function (i, item) {
                                arr.push("<div style='padding-left: 20px;'><label style='color:#999999'>"+item.note+"</label>");
                                switch (item.noteIndex) {
                                    case 0: {
                                        arr.push('：'+item.promotionRequest + '年</br>');
                                        break;
                                    }
                                   case 1: {
                                        var array = item.promotionRequest.split('_');
                                        var s = [];
                                        //s.push('：'+array[0] + "次" + array[1] + "星");
                                        s.push(":至少连续" + array[2] + "次");
                                        s.push(array[1] + "星");
                                        arr.push(s.join('') +'</br>');
                                        break;
                                    }
                                      default: {
                                        arr.push('：' + item.promotionRequest+'</br>');
                                        break;
                                    }
                                }
                               arr.push("</div>");
                            });
                        }else{
                        	 arr.push("<div style='padding-left: 110px;'>");
                        	 arr.push("-");
                        	 arr.push("</div>");
                        }
                        return arr.join('');
                    }
                },
                {
                    name: 'condition2',
                    index: 'condition2',
                    width:60,
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        var arr = [];
                        if (rowObject.listReq.length > 0) {
                            $.each(rowObject.listReq, function (i, item) {
                            	arr.push("<div style='height:24px;'>");
                                switch (item.noteIndex) {
                                    case 0: {
                                        arr.push( item.persomaStatus + '年'+'</br>');
                                        break;
                                    }
                                    case 1: {
                                        arr.push( item.persomaStatus+'</br>');
                                        break;
                                    }
                                    default: {
                                        arr.push( item.persomaStatus+'</br>');
                                        break;
                                    }
                                }
                                arr.push("</div>");
                            });
                        }else{
                       	 arr.push("<div>");
                    	 arr.push("-");
                    	 arr.push("</div>");
                        }
                        return arr.join('');
                    }
                },  {
                    name: 'condition3',
                    index: 'condition3',
                    width: 60,
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        var arr = [];
                        if (rowObject.listReq.length > 0) {
                            $.each(rowObject.listReq, function (i, item) {
                            	  arr.push("<div style='height:24px;'>");
                            	  arr.push((item.isAccord > 0 ? '<i class="icon-ok" style="font-size:18px;"></i>' : '<i class="icon-remove"></i>')+"</br>");
                            	  arr.push("</div>");
                            });
                        }else{
                          	 arr.push("<div>");
                        	 arr.push("-");
                        	 arr.push("</div>");
                        }
                        return arr.join('');
                    }
                },
                {
                    name: 'chart',
                    index: 'chart',
                    width: 275,
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        //var obj={
                        //    xaxis:["2014年上半年", "2014年下半年", "2015年上半年", "2015年下半年", "2016年上半年"],
                        //    series:[1, 1.1, 1.2, 1.2, 1.3]
                        //};
                        return '<div id="promoteStatusChart' + options.rowId + '" class="promoteStatusChart" data-empid="' + rowObject.empId + '"></div>';
                    }
                }
            ],
            rownumbers: false,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '#promoteStatusGridPage',
            styleUI: 'Bootstrap',
            postData: {},
            loadComplete: function (xhr) {
                var self = promoteStatusObj;
                var empIds = [];
                $.each(xhr.rows, function (i, item) {
                    empIds.push(item.empId);
                });
                var len=xhr.rows.length;
                len=len==0?1:len;
                $("#proStatus").height((268*len)+105);
                
                if (empIds.length > 0) {
                    $.get(urls.getTrackChart, {
                        organId: self.postData.organId,
                        empIds: empIds.join(',')
                    }, function (rs) {
                        self.chart = [];
                        $("#proStatus .promoteStatusChart").each(function (i) {
                            $(this).css({width: $(this).parent().width() + "px"}).parent().attr("title", "");
                            var id = $(this).attr("id");
                            var empId = $(this).data("empid");
                            var data = _.filter(rs, function (item) {
                                return item.empId == empId
                            });

                            if (data.length > 0) {
                                var showNum = 5;
                                var maxYear = (_.max(data, function (m) {
                                    return m.years
                                })).years;
                                var minYear = (_.min(data, function (m) {
                                    return m.years
                                })).years;
                                if ((maxYear - minYear + 1) > showNum) {
                                    minYear = maxYear - showNum + 1;
                                }
                                var xAxisData = [];
                                for (var i = minYear; i <= maxYear; i++) {
                                    xAxisData.push(parseInt(i / 2) + "年" + (i % 2 > 0 ? "下半年" : "上半年"));
                                }
                                var seriesData = [], all = [], name = '';
                                for (var i = minYear; i <= maxYear; i++) {
                                    var value = 0;
                                    var d={name:"PM",rankVal:value, value: value};
                                    $.each(data, function (m, item) {
                                        if (item.empId == empId && item.years == i) {
                                            value = Tc.formatFloat(item.currRank);
                                            d = {name: item.rankName,rankVal:value, value: value}
                                        }
                                    });
                                    seriesData.push(d);
                                    all.push(value);
                                }
                                var option = self.getOption(xAxisData, seriesData);
                                self.chart[i] = initEChart(id);
                                self.chart[i].setOption(option);
                            } else {
                                $("#" + id).html("<div class='hidechart'>暂无数据</div>");
                            }
                        });
                        self.resizeHeight();
                        self.resize();
                    });
                }else{
                	self.resizeHeight();
                }
                
                toEmpDetail();
            }
        },
        optionChart: {
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    lineStyle: {
                        color: "#999999",
                        width: 1,
                        type: 'dashed'
                    }
                },
                formatter: function (a,b,c) {
                    var arr = [];
                    $.each(a, function (i, item) {
                        if (i == 0) {
                            arr.push(a[i].name+"<br>");
                        } else {
                            arr.push('<br>');
                        }
                        arr.push(item.seriesName + "：" +  item.data.name+item.data.rankVal);
                    });
                    return arr.join('');
                }
               // formatter: "{b}<br>{a}：PM{c}"
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: [],
                    name: "时间",
                    axisLine: {
                      	 show: true,
                           onZero: false,
                           lineStyle: {
                               width: 1,
                               color: '#666666'
                           }
                       },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                    	show : true,
                    	lineStyle: {
                            color: '#666666'
                        }
                    },
                    axisLabel: {
                        rotate: 10
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    name: "职级",
                    axisLine: {
                      	 show: true,
                           onZero: false,
                           lineStyle: {
                               width: 1,
                               color: '#666666'
                           }
                       },
                    splitLine:  {
                        show: true,
                        lineStyle : {
                        	color : '#e4e4e4'
                        }
                    },
                    axisLabel: {
                        formatter: "{value}级"
                    },
                    min: 1,
                }
            ],
            series: [
                {
                    name: "职级",
                    type: "line",
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                               // formatter: "PM{c}"
                                	 formatter: function(obj){
                                     	return obj.data.name+obj.data.rankVal;
                                     }
                            }
                        }
                    }
                }
            ],
            grid: {
                borderWidth: 0,
                x: 45,
                y: 30,
                x2: 35,
                y2: 40
            },
            color: ["#f5b147"]
        },
        postData: {
            organId: reqOrgId,
            empId: $("#promoteStatusText").val(),
            rankName: '',
            rankNameNext: '',
            condition: -1,
            isAll:true,
            conditionBegin: 0,
            conditionEnd: 0
        },
        init: function (reqOrgId2) {
            var self = this;
            var orgName = win.$(".dropDownValue").text();
            if(reqOrgId2!=null&&reqOrgId2!=""&&reqOrgId2!=undefined){
            	self.postData.organId=reqOrgId2;
            	$("#organization,#promoteStatus .dropDownValue").data("id",  self.postData.organId).text(orgName);
            }else{
            	$("#organization,#promoteStatus .dropDownValue").data("id",  self.postData.organId);
            }
            self.render();
            self.event();
        },
        render: function () {
            var self = this;
            //var empId='', orgId= reqOrgId, rankName='', rankNameNext='';
            if (self.jqGrid) {
                self.jqGrid.clearGridData().setGridParam({
                    postData: self.postData
                }).trigger("reloadGrid");
            } else {
//                var width = $("#proStatus").width() - self.optionGrid.colModel[0].width - self.optionGrid.colModel[1].width;
//                var wa = parseInt(width / 2);
//                self.optionGrid.colModel[2].width = wa;
//                self.optionGrid.colModel[3].width = width - wa;
                self.optionGrid.postData = self.postData;
                self.jqGrid = $("#" + self.gridId).jqGrid(self.optionGrid);
            }
            self.resize();
        },
        getOption: function (xAxisData, seriesData) {
            var self = this;
            var min = 0, max = parseInt(_.max(seriesData)) + 1, splitNumber = max - min;
            self.optionChart.xAxis[0].data = xAxisData;
            self.optionChart.yAxis[0].min = min;
            self.optionChart.yAxis[0].max = max;
            self.optionChart.yAxis[0].splitNumber = splitNumber;
            self.optionChart.series[0].data = seriesData;
            return self.optionChart;
        },
        event: function () {
            var self = this;
            //搜索 下拉人员
//            self.select();
            //搜索 提交搜索
            $("#promoteStatusSearch").unbind("click").on("click", function () {
                self.postData = $.extend({}, self.postData, {
                    organId: reqOrgId,
                    empId: $("#promoteStatusText").val(),
                    rankName: '',
                    isAll:true,
                    rankNameNext: '',
                    condition: -1,
                    conditionBegin: 0,
                    conditionEnd: 0
                });
                self.init();
            });
            $("#promoteStatus .selectMore").unbind("click").on("click", function () {
                var obj = $(this).find("span");
                var ms = $("#promoteStatus .moreSearch");
                if (obj.eq(0).hasClass("hide")) {
                    obj.eq(0).removeClass("hide");
                    obj.eq(1).addClass("hide");
                    ms.addClass("hide");
                } else {
                    obj.eq(0).addClass("hide");
                    obj.eq(1).removeClass("hide");
                    ms.removeClass("hide");
                }
                self.resize();
            });
            //搜索 组织架构
            self.organization();
            //搜索 条件
            $("#condition").unbind("change").change(function () {
                $(this).parent().find("em").toggle(parseInt($(this).val()) > 0);
            });
            //搜索 提交搜索
            $("#promoteStatusSearchMore").unbind("click").on("click", function () {
                var orgId = $("#organization").data("id");
                var rankIndex = parseInt($("#positionRank").val());
                var rankText = $("#positionRankText").val();
                var conditionIndex = parseInt($("#condition").val());
                var conditionBegin = $("#conditionBegin").val();
                var conditionEnd = $("#conditionEnd").val();
                var isAll=(rankIndex==0||rankText=="")&&conditionIndex==-1;
                if (conditionIndex > 0) {
                	if(conditionBegin==""){
                		conditionBegin=0;
                	}
                	if(conditionEnd ==""){
                		conditionEnd=100;
                	}
                }
            	self.postData.organId=orgId;
                self.postData = $.extend({}, self.postData, {
                    organId: self.postData.organId,
                    empId: '',
                    isAll:isAll,
                    rankName: rankIndex == 0 ? rankText : '',
                    rankNameNext: rankIndex == 1 ? rankText : '',
                    condition: conditionIndex,
                    conditionBegin: conditionIndex > 0 ? conditionBegin : 0,
                    conditionEnd: conditionIndex > 0 ? conditionEnd : 0
                });
                self.init();
                var ms = $("#promoteStatus .moreSearch");
                    ms.addClass("hide");
                   $("#promoteStatus .selectMore").click();    
            });
            //搜索 清除
            $("#promoteStatusClear").unbind("click").on("click", function () {
                var orgName = win.$(".dropDownValue").text();
                $("#organization").data("id", reqOrgId).parent().find(".dropDownValue").text(orgName);
                $("#positionRankText,#conditionBegin,#conditionEnd").val("");
                $("#positionRank").val("0").change();
                $("#condition").val("-1").change();

                $("#promoteStatus .selectMore").click();

                self.postData = $.extend({}, self.postData, {
                    organId: reqOrgId,
                    empId: '',
                    rankName: '',
                    rankNameNext: '',
                    condition: -1,
                    isAll:true,
                    conditionBegin: 0,
                    conditionEnd: 0
                });
                self.init();
            });
        },
        select: function () {
            $("#promoteStatusText").select2({
                language: language,
                width: '100%',
                allowClear: true,
                multiple: false,
                openOnEnter: true,
                placeholder: "输入员工姓名",
                ajax: {
                    url: urls.getTrackSelect,
                    dataType: 'json',
                    delay: 500,
                    type: "POST",
                    data: function (params, page) {
                        var ps = {
                            organId: reqOrgId,
                            key: params && params.term ? params.term : "",
                            page: params.page == null ? 1 : params.page,
                            rows: 30
                        };
                        return ps;
                    },
                    processResults: function (data) {
                        var lists = [];
                        $.each(data.list, function (i, item) {
                            lists.push({id: item.empId, text: item.userName});
                        });
                        return {
                            results: lists,
                            pagination: {
                                more: data.more > 0
                            }
                        };
                    }
                }
            }).val(null).trigger("change");
        },
        organization: function () {
            var orgName = win.$(".dropDownValue").text();
            var self=this;
            $("#organization").data("id", self.postData.organId).text(orgName).organTreeSelector({
                showSelectBtn: false,
                multiple: false,
                value: {
                    'id': self.postData.organId,
                    'text': orgName
                },
                onSelect: function (ids, texts) {
                    $("#organization").data("id", ids).text(orgName);
                }
            });
           $(".dropDownBlackIcon").addClass("icon-chevron-sign-down");
        },
        resize: function () {
            var self = this;
            if (self.jqGrid) {
                self.jqGrid.setGridWidth($("#proStatus").width()-15);
                //grid高度
//                var height = 168;
//                if ($("#promoteStatus .moreSearch:visible").length > 0) {
//                    height += 179;
//                }
//                $("#promoteStatusGrid").parents(".ui-jqgrid-bdiv").css({height: $(window).height() - height + "px"});
                //chart
                $("#proStatus .promoteStatusChart").each(function (i) {
                    if (self.chart[i]) {
                        var id = $(self.chart[i].dom).attr("id");
                        $("#" + id).css({width: $("#" + id).parent().width() + "px"});
                        if (self.chart[i]) {
                            self.chart[i].resize();
                        }
                    }
                });
            }
        }, resizeHeight: function () {
            var self = this;
            if (self.jqGrid) {
                self.jqGrid.setGridHeight($("#proStatus").height()-105);
            }
        }
    }

    /*
     *************************************************************
     * 页签三开始
     * ***********************************************************
     * */
    //晋级薪酬模拟器
    var salarySimulationObj = {
        jqGrid: null,
        promoteSalarys:[],
        jqGridEligibility: [],
        jqGridUneligibility: [],
        select2: [],
        data: null,
        dataRank: null,
        queryRank: '',
        optionGrid: {
            datatype: "local",
            width: 0,
            height: 280,
            colNames: ['姓名', '当前薪酬<br><span class="colorccc">(万元)</span>', '晋级后薪酬<br><span class="colorccc">(万元)</span>', '晋级变化<br><span class="colorccc">(万元)</span>'],
            colModel: [
                {
                    name: 'name',
                    index: 'name',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return '<div><input class="eligibilityCheckbox" data-empid="' + rowObject.id + '" type="checkbox">' + cellvalue + '</div>';
                    }
                },
                {
                    name: 'currentSalary',
                    index: 'currentSalary',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return '<div class="eligibilityCurrentSalary">' + cellvalue + '万元</div>';
                    }
                },
                {
                    name: 'promoteSalary',
                    index: 'promoteSalary',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return '<div><input class="eligibilityPromoteSalary" type="text" value="' + Tc.formatFloat(cellvalue) + '" style="text-align:center; font-size:12px; width:60px;">万元</div>';
                    }
                },
                {
                    name: 'change',
                    index: 'change',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return '<div class="eligibilitySalaryTotal"></div>';
                    }
                }
            ],
            rownumbers: false,
            viewrecords: true,
            altRows: true
        },
        init: function (show) {
            var self = this;
            $("#emSubmit span").text(0);
            $("#emulator").html('<div class="msg"></div>');
            $("#emulatorModal").unbind("shown.bs.modal").on("shown.bs.modal", function () {
                $("#emulatorModal .modal-body").css({height: $(window).height() - 180 + "px"});
                $("#emulatorModal #emulator").css({height: $(window).height() - 221 + "px"});

                $("#emulator").html('<div class="msg">数据加载中</div>');
                $.get(urls.getPromotionSelectRank, {organId: reqOrgId}, function (data) {
                    if (data) {
                        self.dataRank = data;
                        self.renderRank();
                    } else {
                        self.dataRank = [];
                        $("#emulator").html('<div class="msg">暂无数据</div>');
                    }
                });
            });
            if(show){
            	  $("#emulatorModal").modal('show');
            }else{
            	if($("#emulatorModal").hasClass("in")){
            		$("#emulatorModal").trigger("shown.bs.modal", $("#emulatorModal"));
            	}else{
            		  $("#emulatorModal").modal('show');
            	}
            }
          
            $("#setttingRank").unbind("click").on("click", function () {
                $("#messageDialog").modal({
                    keyboard: true
                });
            });
            $("#msgConfirm").unbind("click").on("click", function () {
                $("#messageDialog").modal('hide');
                $("#emulator").html('<div class="msg">数据加载中</div>');
                self.init();
            });
        },
        renderRank: function () {
            var self = this;
            var data = self.dataRank;
            var ranks = self.queryRank.split(',');

            var arr = [];
            var selects=0;
            $.each(data, function (i, item) {
            	selects+=(_.contains(ranks, item.rank) ? 1:0);
                arr.push('<div class="emitem' + (_.contains(ranks, item.rank) ? ' selected' : '') + '">');
                arr.push('  <div class="emok"><i class="icon-ok"></i></div>');
                arr.push('  <div class="emrank">' + item.rank + '</div>');
                arr.push('  <div class="emcount">');
                arr.push('	<div class="ec1">符合条件：' + item.match + '人</div>');
                arr.push('	<div class="ec2">部分符合：' + item.mismatch + '人</div>');
                arr.push('  </div>');
                arr.push('</div>');
            });
            $("#emulator").html(arr.join(''));
            $("#emSubmit span").text(selects);
            //事件
            $("#emulator .emitem").unbind("click").on("click", function () {
                if ($(this).hasClass("selected")) {
                    $(this).removeClass("selected");
                } else {
                    if ($("#emulator .selected").length < 10) {
                        $(this).addClass("selected");
                    } else {
                        showErrMsg("职级最多选中10个.");
                    }
                }
                $("#emSubmit span").text($("#emulator .selected").length);
            });
            $("#emSubmit").unbind("click").on("click", function () {
                var ranks = [];
                $("#emulator .selected .emrank").each(function (i, item) {
                    ranks.push($(this).text());
                });
                if (ranks.length > 0) {
                    self.queryRank = ranks.join(',');
                    $("#emulatorModal").modal('hide');
                    self.init2();
                } else {
                    showErrMsg("请选择职级.");
                }
            });
        },
        init2: function () {
            var self = this;

            $("#eligibilityGrid tbody").html('<tr><td class="cell" colspan="4"><div class="textmsg">数据加载中</div></td></tr>');
            $.get(urls.getPromotionRankList, {organId: reqOrgId, ranks: self.queryRank}, function (data) {
                if (data) {
                    var array = [];
                    $.each(data, function (i, item) {
                        var match = [];
                        $.each(item.match, function (j, m) {
                            match.push({
                                id: m.empId,
                                name: m.userName,
                                currentSalary: m.salary,
                                promoteSalary: item.nextRankSalary
                            })
                        });
                        var mismatch = [];
                        $.each(item.mismatch, function (j, m) {
                            mismatch.push({
                                id: m.empId,
                                name: m.userName,
                                currentSalary: m.salary,
                                promoteSalary: item.nextRankSalary
                            })
                        });
                        array.push({
                            nextRankSalary: item.nextRankSalary,
                            rank: item.rankName,
                            eligibility: match,
                            unEligibility: mismatch
                        });
                    });

                    self.data = array;
                    self.render();
                } else {
                    $("#eligibilityGrid tbody").html('<tr><td class="cell" colspan="4"><div class="textmsg">暂无数据</div></td></tr>');
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            var array = [];
            $.each(data, function (i, item) {
            	self.promoteSalarys[i]=item.nextRankSalary;
                array.push('<tr class="eligibilityRow">');
                array.push('  <td class="cell1">' + item.rank + '</td>');
                array.push('  <td class="cell2" valign="top"><div class="eligibilitytd"><table class="eligibilitySub" data-index="' + i + '" id="eligibilitySubtbId"></table></div></td>');
                array.push('  <td class="cell3"><div class="eligibilitytd"><table class="uneligibilitySub" data-index="' + i + '" id="uneligibilitySubtbId"></table></div><div class="uneligibilitySubDivs"><select data-rank="' + item.rank + '" data-nextranksalary="' + item.nextRankSalary + '" class="uneligibilitySubSelect"></select></div><div class="uneligibilitySubDivb"><input class="uneligibilitySubAdd" value="确定" type="button"></div></td>');
                array.push('  <td class="cell4"><span>...</span>万元</td>');
                array.push('</tr>');
            });
            $("#eligibilityGrid tbody").html(array.join(''));

            self.resize();

            //符合条件
            self.optionGrid.width = $("#eligibilityGrid thead").find(".cell2").width();
            $("#eligibilityGrid .eligibilitySub").each(function (i, item) {
                self.jqGridEligibility[i] = $(this).jqGrid(self.optionGrid);
                var index = $(this).data("index");
                var da = data[index].eligibility;
                for (var j = 0; j <= da.length; j++) {
                    self.jqGridEligibility[i].jqGrid('addRowData', j + 1, da[j]);
                }
            });
            //不符合条件
            self.optionGrid.width = $("#eligibilityGrid thead").find(".cell3").width();
            $("#eligibilityGrid .uneligibilitySub").each(function (i, item) {
                self.jqGridUneligibility[i] = $(this).jqGrid(self.optionGrid);
                var index = $(this).data("index");
                var da = data[index].unEligibility;
                for (var j = 0; j <= da.length; j++) {
                    self.jqGridUneligibility[i].jqGrid('addRowData', j + 1, da[j]);
                }
            });
            self.resizeGrid();

            self.selectAddMismatch();

            //符合的打勾
            $(".cell2 .eligibilityCheckbox").prop("checked", true);

            self.calculate();
            self.event();
        },
        //部分符合条件添加人员
        selectAddMismatch: function () {
            var self = this;
            $(".uneligibilitySubSelect").each(function (i) {
                var rank = $(this).data("rank");
                var nextranksalary = $(this).data("nextranksalary");
                var t = this;
                self.select2[i] = $(this).select2({
                    language: language,
                    width: '100%',
                    allowClear: true,
                    multiple: false,
                    openOnEnter: true,
                    placeholder: "添加其他人员",
                    ajax: {
                        url: urls.getPromotionAddPersonList,
                        dataType: 'json',
                        delay: 500,
                        type: "POST",
                        data: function (params, page) {
                            var ps = {
                                organId: reqOrgId,
                                rank: rank,
                                page: params.page == null ? 1 : params.page,
                                rows: 30,
                                key: params && params.term ? params.term : "",
                            };
                            return ps;
                        },
                        processResults: function (data) {
                            var empIds = [];
                            $(t).parent().parent().find(".eligibilityCheckbox").each(function () {
                                empIds.push($(this).data("empid"));
                            });
                            var lists = [];
                            $.each(data.list, function (i, item) {
                                if (!_.contains(empIds, function (m) {
                                        return m == item.empId
                                    })) {
                                    lists.push({
                                        id: '{"empid":"' + item.empId + '", "text":"' + item.userName + '", "pay":"' + item.pay + '", "nextranksalary":"' + nextranksalary + '"}',
                                        text: item.userName
                                    });
                                }
                            });
                            return {
                                results: lists,
                                pagination: {
                                    more: data.more > 0
                                }
                            };
                        }
                    }
                }).val(null).trigger("change");
            });

            $(".uneligibilitySubAdd").each(function (i) {
                $(this).on("click", function () {
                    var objStr = $(this).parent().parent().find(".uneligibilitySubSelect").val();
                    if(objStr==null)return;
                    var obj = $.parseJSON(objStr);
                    var b = false, j = 0;
                    $(this).parent().parent().find(".eligibilityCheckbox").each(function () {
                        j++;
                        if ($(this).data("empid") == obj.empid) {
                            b = true;
                        }
                    });
                    if (!b) {
                        self.jqGridUneligibility[i].jqGrid('addRowData', j, {
                            id: obj.empid,
                            name: obj.text,
                            currentSalary: obj.pay,
                            promoteSalary: salarySimulationObj.promoteSalarys[i]
                        });

                        self.select2[i].val(null).trigger("change");
                        self.calculate();
                        self.event();
                    } else {
                        showErrMsg("已添加到列表中");
                    }
                });
            });
        },
        calculate: function () {
            var self = this;
            //小计
            $(".eligibilityPromoteSalary").each(function () {
                var obj = $(this).parent().parent().parent();
                var current = parseFloat(obj.find(".eligibilityCurrentSalary").text());
                var promote = parseFloat($(this).val());
                var value = (promote - current > 0 ? "+" : "") + Tc.formatFloat(promote - current);
                obj.find(".eligibilitySalaryTotal").text(value+"万元");
            });
            //大计
            $(".eligibilityRow").each(function () {
                var total = 0;
                $(this).find(".eligibilityCheckbox").each(function () {
                    if ($(this).prop("checked")) {
                        var v = $(this).parent().parent().parent().find(".eligibilitySalaryTotal").text().replace("+", "");
                        total += parseFloat(v)
                    }
                });
                $(this).find(".cell4 span").text(Tc.formatFloat(total));
            });
            //总计
            var total = 0, num = 0;
            $(".eligibilityCheckbox").each(function () {
                if ($(this).prop("checked")) {
                    var v = $(this).parent().parent().parent().find(".eligibilitySalaryTotal").text().replace("+", "");
                    total += parseFloat(v);
                    num++;
                }
            });
            $("#eligibilityTotal span").text(Tc.formatFloat(total));
            $("#eligibilityTotal em").text(num);
        },
        event: function () {
            var self = this;
            $(".eligibilityCheckbox").on("click", function () {
                self.calculate();
            });
            $(".eligibilityPromoteSalary").on("blur", function () {
                self.calculate();
            });
        },
        resize: function () {
            var self = this;
            var obj = $("#eligibilityGrid");
            var width = obj.width() - obj.find(".cell1").width() - obj.find(".cell4").width();
            var wd = parseInt(width / 2);
            obj.find(".cell2").css({width: wd + "px"});
            obj.find(".cell3").css({width: width - wd + "px"});

            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            var obj = $("#eligibilityGrid");
            var width = obj.width() - obj.find(".cell1").first().width() - obj.find(".cell4").width();
            var wd = parseInt(width / 2);
            $.each(self.jqGridEligibility, function (i, item) {
                if (item) {
                    item.setGridWidth(wd - 3);
                }
            });
            $.each(self.jqGridUneligibility, function (i, item) {
                if (item) {
                    item.setGridWidth(width - wd - 5);
                }
            });
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

    function loadingGrid(gridId, isloading) {
        if (isloading) {
            if ($("#" + gridId + "Loading").length == 0) {
                $("#" + gridId).before("<div id='" + gridId + "Loading' class='loadingmessage'></div>");
            }
            $("#" + gridId + "Loading").text("数据加载中").show();
            $("#" + gridId + ",#gbox_" + gridId).hide();
        } else {
            $("#" + gridId + "Loading").hide();
            $("#" + gridId + ",#gbox_" + gridId).show();
        }
    }

    function hideGrid(gridId) {
        $("#" + gridId + "Loading").text("暂无数据").show();
        $("#" + gridId + ",#gbox_" + gridId).hide();
    }

    //缩放重置
    $(window).resize(function () {
        $(".leftBody .leftListDiv").each(function () {
            $(this).data("resize", !$(this).hasClass("selectList"));
        });
        resizeChartOrGrid();
    });

    var resizeChartOrGrid = function () {
        var t = pageObj;
        switch ($(".leftBody .selectList").attr("page")) {
            case "page-one": {//页签1
                if (t.tabFirstLoaded) {
                    applicationConformityDialogObj.resize();
                    promoteTrendObj.resize();
                    promoteSubOrgObj.resize();
                    promoteSequenceObj.resize();
                    promoteCrowdObj.resize();
                    promoteTrackObj.resize();
                }
                break;
            }
            case "page-two": {//页签2
                if (t.tabSecondLoaded) {
                    promoteStatusObj.resize();
                }
                break;
            }
            case "page-three": {//页签3
                if (t.tabThreeLoaded) {
                    salarySimulationObj.resize();
                }
                break;
            }
        }
    }

    //初始化页签
    var pageObj = {
        tabName: "page-one",
        tabFirstLoaded: false,
        tabSecondLoaded: false,
        tabThreeLoaded: false,
        lazyFreshenSecond:false,
        click: function (page) {
            var self = this;
            if (self.tabName == page)return;

            self.tabName = page;
            switch (page) {
                case "page-one": {
                    self.initFirstTab();
                    break;
                }
                case "page-two": {
                    self.initSecondTab(self.lazyFreshenSecond);
                    break;
                }
                case "page-three": {
                    self.initThreeTab(true);
                    break;
                }
            }
            var obj = $(".leftBody div[page='" + page + "']");
            if (obj.data("resize")) {
                obj.data("resize", false);
                resizeChartOrGrid();
            }
        },
        //页签1
        initFirstTab: function (freshen) {
            var self = this;
            if (freshen||!self.tabFirstLoaded) {
                self.tabFirstLoaded = true;
                applicationConformityNumObj.init();
                applicationUnConformityNumObj.init();
                applicationSomeConformityNumObj.init();
                promoteTrendObj.init();
                defaultDatas.organNum=0;
                prarr.prarrNum=0;
                isdianji=0;
                promoteSubOrgObj.init(reqOrgId,true);
                promoteSequenceObj.init();
                promoteCrowdObj.init();
                promoteTrackObj.init();
            }else{
            	 applicationConformityDialogObj.resize();
                 promoteTrendObj.resize();
                 promoteSubOrgObj.resize();
                 promoteSequenceObj.resize();
                 promoteCrowdObj.resize();
                 promoteTrackObj.resize();
            }
        },
        //页签2
        initSecondTab: function (freshen) {
            var self = this;
            if (freshen||!self.tabSecondLoaded) {
                self.tabSecondLoaded = true;
                pageObj.lazyFreshenSecond=false;
                promoteStatusObj.init(reqOrgId);
            }else{
            	 promoteStatusObj.resize();
            }
        },
        //页签3
        initThreeTab: function (show) {
            var self = this;
           // if (!self.tabThreeLoaded) {
                self.tabThreeLoaded = true;
                salarySimulationObj.init(show);
          //  }
        }
    };
    pageObj.initFirstTab();

    //$('[page="page-three"]').click();
    

    		//进入人才剖像详情页面
			 function toEmpDetail() {
				 $('.talent_col').unbind().bind('click', function () {
		                var _this = $(this);
		                var empId = _this.attr('data');
		                var herf = urls.toEmpDetailUrl + '?empId=' + empId + '&rand=' + Math.random();
		                window.open(herf);
		            });
						
			}

});