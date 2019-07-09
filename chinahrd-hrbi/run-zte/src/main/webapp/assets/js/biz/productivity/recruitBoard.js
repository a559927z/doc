/**
 * Created by Administrator on 2016/5/4.
 */
require(['jquery', 'jquery-ui', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'timeLine2', 'resize', 'messenger'], function ($, jqueryui, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        //页签一
        getWaitRecruitPost: webRoot + "/recruitBoard/getWaitRecruitPost.do",//待招聘岗位
        getWaitRecruitNum: webRoot + "/recruitBoard/getWaitRecruitNum.do",//待招聘人数
        getRecruitCostAndBudget: webRoot + "/recruitBoard/getRecruitCostAndBudget.do",//招聘费用
        getPostMeetRate: webRoot + "/recruitBoard/getPostMeetRate.do", //岗位满足率统计 列表
        updatePostMeetRateSequence: webRoot + "/recruitBoard/updatePostMeetRateSequence.do",//岗位满足率统计 设置
        getPositionResult: webRoot + "/recruitBoard/getPositionResult.do",//岗位满足率统计 简历 面试 offer 入职
        getRecruitChannel: webRoot + "/recruitBoard/getRecruitChannel.do", //招聘渠道统计
        getProbationDismissionRate: webRoot + "/recruitBoard/getProbationDismissionRate.do", //招聘渠道统计 试用期离职率统计
        getRecruitChange: webRoot + "/recruitBoard/getRecruitChange.do", //人员异动提醒
        getRecruitChangeList: webRoot + "/recruitBoard/getRecruitChangeList.do", //人员异动提醒 列表

        //页签二
        getPositionImages: webRoot + "/recruitBoard/getPositionImages.do", //高绩效员工画像
        getImagesQueryTags: webRoot + "/recruitBoard/getImagesQueryTags.do", //高绩效员工画像 对话框页面元素
        getPositionPay: webRoot + "/recruitBoard/getPositionPay.do" //招聘岗位薪酬参考
    };
    $(win.document.getElementById('tree')).next().show();

    var reqOrgId = win.currOrganId;

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;

        timeLineObj.init(organId);

        var t = pageObj;
        t.tabFirstLoaded = t.tabSecondLoaded = t.tabThreeLoaded = t.tabFourLoaded = false;
        switch (t.tabName) {
            case "page-one":
            {
                t.initFirstTab();
                break;
            }
            case "page-two":
            {
                t.initSecondTab();
                break;
            }
        }
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
     * 修改grid分页图标
     */
    function updatePagerIcons() {
        var replacement =
        {
            'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev': 'icon-angle-left bigger-140',
            'ui-icon-seek-next': 'icon-angle-right bigger-140',
            'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

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

    //echarts显示滚动条需要达到多少柱
    var loadingText = "数据加载中";
    var nodataText = "暂无数据";

    /*
     *************************************************************
     * 页签一开始
     * ***********************************************************
     * */

    /*
     * 待招聘岗位
     * */
    var recruitingPosition = {
        data: null,
        init: function () {
            var self = this;
            $("#recruitingPositionNum .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getWaitRecruitPost, {organId: reqOrgId}, function (data) {
                if (data && data.year && data.dtos && data.dtos.length > 0) {
                    $("#recruitingPositionNum .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#recruitingPositionNum .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }
            });
            self.event();
        },
        render: function () {
            var self = this;
            var data = self.data;
            $("#recruitingPositionNum .year").text(data.year + "年");
            $("#recruitingPositionNum .accord-yj-float-value").text(data.dtos.length);
            var text = [], tm = [];
            $.each(data.dtos, function (i, item) {
                text.push(item.positionName);
                tm.push('<div class="item unselectable">');
                tm.push('<div class="item1">');
                tm.push('<div class="item11">');
                tm.push('<div class="text">' + item.positionName + '</div>');
                tm.push('<div class="number">' + item.planNum + '人</div>');
                tm.push('</div>');
                tm.push('</div>');
                tm.push('</div>');
            });
            $("#recruitingPositionNum .text").text(text.join('、'));
            $("#rpm .content").html(tm.join(''));
        },
        event: function () {
            var self = this;
            $("#recruitingPositionNum .detail").unbind("click").on("click", function () {
                $("#recruitingPositionModal").modal("show").on('shown.bs.modal', function () {
                    $("#recruitingPositionModal .modal-title span").text('（' + $("#recruitingPositionNum .accord-yj-float-value").text() + ' 个）');
                    $("#rpm").css({height: $(window).height() - 120 + "px"});

                });
            });
        }
    }

    /*
     * 待招聘人数
     * */
    var recruitingNum = {
        data: null,
        init: function () {
            var self = this;
            $("#recruitingPeopleNum .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getWaitRecruitNum, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#recruitingPeopleNum .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#recruitingPeopleNum .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;
            $("#recruitingPeopleNum .year").text(data.year + "年");
            $("#recruitingPeopleNum .accord-yj-float-value").text(data.recruitNum);
        }
    }

    /*
     * 招聘费用
     * */
    var recruitingCost = {
        data: null,
        init: function () {
            var self = this;
            $("#recruitingCost .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getRecruitCostAndBudget, {organId: reqOrgId}, function (data) {
                if (data) {
                    $("#recruitingCost .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#recruitingCost .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;
            $("#recruitingCost .year").text(data.year + "年");
            $("#recruitingCost .accord-yj-float-value").text(data.outlay);
            $("#recruitingCost .usedCost").text(Tc.formatFloat(data.outlayRate * 100) + '%');
        }
    }

    /*
     * 岗位满足率统计
     */
    var positionFillRateObj = {
        data: null,
        optionGrid: {
            url: urls.getPositionResult,
            mtype: 'GET',
            datatype: "json",
            width: 0,
            height: 0,
            colNames: ['员工姓名', '性别', '年龄', '学历', '专业', '毕业院校', '操作'],
            colModel: [
                {name: 'username', index: 'username', sortable: false, align: 'center'},
                {name: 'sex', index: 'sex', sortable: false, align: 'center'},
                {name: 'age', index: 'age', sortable: false, align: 'center'},
                {name: 'degree', index: 'degree', sortable: false, align: 'center'},
                {name: 'major', index: 'major', sortable: false, align: 'center'},
                {name: 'school', index: 'school', sortable: false, align: 'center'},
                {
                    name: 'operation',
                    index: 'operation',
                    sortable: false,
                    align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return rowObject.url == "" ? "" : ('<a href="' + rowObject.url + '" data-id="' + rowObject.id + '" target="_self">查看简历</a>');
                    }
                }
            ],
            rownumbers: true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '',
            postData: {},
            loadComplete: function (xhr) {
                updatePagerIcons();
                positionFillRateObj.resizeGrid();
            }
        },
        sortable: null,
        uuid: '',
        jqgridResume: null,
        jqgridInterview: null,
        jqgridOffer: null,
        jqgridJob: null,
        init: function () {
            var self = this;
            $("#positionFillRate .data").addClass("hide").eq(0).text(loadingText).removeClass("hide");
            $.get(urls.getPostMeetRate, {organId: reqOrgId, quotaId: $("#quotaId").val()}, function (data) {
                if (data && data.length > 0) {
                    $("#positionFillRate .data").addClass("hide").eq(1).removeClass("hide");
                    self.data = data;
                    self.render();
                } else {
                    $("#positionFillRate .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;
            var text = [], textshow = [], texthide = [];
            $.each(data, function (i, item) {
                if (item.isView) {
                    text.push('<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 frb" data-uuid="' + item.id + '">');
                    text.push('  <div class="frc">');
                    text.push('	<div class="title">');
                    text.push('	  <div class="title1">【<span>' + item.positionName + '</span>】满足率</div>');
                    text.push('	  <div class="title2">(招聘周期：<span>' + item.startDate.substr(0, 10).replace("-", '.').replace("-", '.') + '-' + item.endDate.substr(0, 10).replace("-", '.').replace("-", '.') + '</span>)</div>');
                    text.push('	</div>');
                    text.push('	<div>');
                    text.push('	  <div class="rate' + (item.warn ? ' red' : '') + '">');
                    text.push('		<div><span class="rateNum">' + Tc.formatFloat(item.meetRate * 100) + '</span><small>%</small></div>');
                    text.push('	  </div>');
                    text.push('	  <div>');
                    text.push('		<label class="ratetext">');
                    text.push('		  待招：<span class="vacancy">' + item.waitRecruitNum + '</span>人<br>');
                    text.push('		  计划：<span class="plan">' + item.planNum + '</span>人');
                    text.push('		</label>');
                    text.push('	  </div>');
                    text.push('	  <div class="schedule">');
                    text.push('		<ul>');
                    text.push('		  <li class="noleft' + (item.public ? ' lired' : '') + '">发布</li>');
                    text.push('		  <li' + (item.resumeNum < item.planNum ? ' class="lired"' : '') + '><label data-key="0">简历<br><span class="resumeNum">' + item.resumeNum + '</span>份</label></li>');
                    text.push('		  <li' + (item.interviewNum < item.planNum ? ' class="lired"' : '') + '><label data-key="1">面试<br><span class="interviewNum">' + item.interviewNum + '</span>人</label></li>');
                    text.push('		  <li' + (item.offerNum < item.planNum ? ' class="lired"' : '') + '><label data-key="2">offer<br><span class="offerNum">' + item.offerNum + '</span>人</label></li>');
                    text.push('		  <li' + (item.entryNum < item.planNum ? ' class="lired"' : '') + '><label data-key="3">入职<br><span class="jobNum">' + item.entryNum + '</span>人</label></li>');
                    text.push('		</ul>');
                    text.push('	  </div>');
                    text.push('	</div>');
                    text.push('  </div>');
                    text.push('</div>');

                    self.sortabletext(textshow, item);
                } else {
                    self.sortabletext(texthide, item);
                }
            });
            if (text.length > 0) {
                $("#positionFillRate .data").eq(1).html(text.join(''));
            } else {
                $("#positionFillRate .data").addClass("hide").eq(0).text(nodataText).removeClass("hide");
            }
            $("#psmc .bshow .content").html(textshow.join(''));
            $("#psmc .bhide .content").html(texthide.join(''));
            self.event();
        },
        sortabletext: function (text, item) {
            text.push('<div class="item" data-id="' + item.id + '" data-fcid="' + item.functionConfigId + '">');
            text.push('	<i class="icon-minus-sign"></i>');
            text.push('	<i class="icon-plus-sign"></i>');
            text.push('	<div class="itemblock">');
            text.push('	  <div class="itemtable">');
            text.push('		<div class="unselectable">' + item.positionName + '</div>');
            text.push('	  </div>');
            text.push('	</div>');
            text.push('</div>');
        },
        event: function () {
            var self = this;
            //点击设置
            $("#positionSetting").unbind("click").click(function () {
                $("#positionSettingModal").modal("show").on('shown.bs.modal', function () {
                    $("#psmc").css({height: $(window).height() - 120 + "px"});
                    self.renderSetting();
                });
            });
            //点击设置 保存
            $("#saveSetting").unbind("click").click(function () {
                var sequence = [];
                $("#psmc .bshow .content .item").each(function (i, item) {
                    sequence.push({
                        cardCode: $(this).data("id"),
                        isView: true,
                        showIndex: i,
                        functionId: reqOrgId,
                        functionConfigId: $(this).data("fcid")
                    });
                });
                $("#psmc .bhide .content .item").each(function (i, item) {
                    sequence.push({
                        cardCode: $(this).data("id"),
                        isView: false,
                        showIndex: i,
                        functionId: reqOrgId,
                        functionConfigId: $(this).data("fcid")
                    });
                });
                var sequenceStr = JSON.stringify(sequence);
                $.post(urls.updatePostMeetRateSequence, {
                    sequenceStr: sequenceStr,
                    quotaId: $("#quotaId").val()
                }, function () {
                    $("#positionSettingModal").modal('hide');
                    self.init();
                });
            });
            //点击设置 取消
            $("#cancelSetting").unbind("click").click(function () {
                $("#positionSettingModal").modal('hide');
            });
            //点击简历/面试/offer/入职
            $("#positionFillRate label").unbind("click").on("click", function () {
                var uuid = $(this).parents(".frb").data("uuid");
                var index = $(this).data("key");
                var obj = $(this).parents(".frc");
                var name = obj.find(".title1 span").text();
                var resumeNum = obj.find(".resumeNum").text();
                var interviewNum = obj.find(".interviewNum").text();
                var offerNum = obj.find(".offerNum").text();
                var jobNum = obj.find(".jobNum").text();
                $("#positionFillRateModal").modal("show").on('shown.bs.modal', function () {
                    $("#pfrm").css({height: $(window).height() - 120 + "px"});
                    $("#positionFillRateModal .modal-title span").text(name);
                    $("#pfrm .resumeNum").text(resumeNum);
                    $("#pfrm .interviewNum").text(interviewNum);
                    $("#pfrm .offerNum").text(offerNum);
                    $("#pfrm .jobNum").text(jobNum);
                    self.optionGrid.width = $("#pfrm .detail").width();
                    self.optionGrid.height = $("#pfrm").height() - 150;
                    $("#pfrm .tab").unbind("click").on("click", function () {
                        var i = $(this).data("index");
                        self.renderGridTab(i);
                        self.resizeGrid();
                    });
                    self.renderGridTab(index);
                    self.renderGrid(uuid);
                });
            });
        },
        //对话框 设置
        renderSetting: function () {
            var self = this;
            if (self.sortable) {
                self.sortable.sortable("destroy");
            }
            self.sortable = $("#psmc .content").sortable().disableSelection();

            $("#psmc .icon-minus-sign").unbind("click").on("click", function () {
                var obj = $(this).parent();
                $("#psmc .bhide .content").append(obj);
            });
            $("#psmc .icon-plus-sign").unbind("click").on("click", function () {
                var obj = $(this).parent();
                $("#psmc .bshow .content").append(obj);
            });
        },
        //对话框 简历/面试/offer/入职
        renderGrid: function (uuid) {
            var self = this;
            self.renderGridResume(uuid);
            self.renderGridInterview(uuid);
            self.renderGridOffer(uuid);
            self.renderGridJob(uuid);
        },
        renderGridTab: function (index) {
            $("#pfrm .tab").removeClass("selected").eq(index).addClass("selected");
            $("#pfrm .tgrid").addClass("hide").eq(index).removeClass("hide");
        },
        renderGridResume: function (uuid) {
            var self = this;
            var type = 0;//0:简历 1:面试 2:offer 3:入职
            var pdata = {id: uuid, type: type};
            if (self.jqgridResume) {
                self.jqgridResume.clearGridData().setGridParam({
                    postData: pdata
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.pager = "#resumeGridPage";
                self.optionGrid.postData = pdata;
                self.jqgridResume = $("#resumeGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        renderGridInterview: function (uuid) {
            var self = this;
            var type = 1;//0:简历 1:面试 2:offer 3:入职
            var pdata = {id: uuid, type: type};
            if (self.jqgridInterview) {
                self.jqgridInterview.clearGridData().setGridParam({
                    postData: pdata
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.pager = "#interviewGridPage";
                self.optionGrid.postData = pdata;
                self.jqgridInterview = $("#interviewGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        renderGridOffer: function (uuid) {
            var self = this;
            var type = 2;//0:简历 1:面试 2:offer 3:入职
            var pdata = {id: uuid, type: type};
            if (self.jqgridOffer) {
                self.jqgridOffer.clearGridData().setGridParam({
                    postData: pdata
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.pager = "#offerGridPage";
                self.optionGrid.postData = pdata;
                self.jqgridOffer = $("#offerGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        renderGridJob: function (uuid) {
            var self = this;
            var type = 3;//0:简历 1:面试 2:offer 3:入职
            var pdata = {id: uuid, type: type};
            if (self.jqgridJob) {
                self.jqgridJob.clearGridData().setGridParam({
                    postData: pdata
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.pager = "#jobGridPage";
                self.optionGrid.postData = pdata;
                self.jqgridJob = $("#jobGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqgridResume && $("#pfrm .detail .resume:visible").length > 0) {
                self.jqgridResume.setGridWidth($("#pfrm .detail").width());
            } else if (self.jqgridInterview && $("#pfrm .detail .interview:visible").length > 0) {
                self.jqgridInterview.setGridWidth($("#pfrm .detail").width());
            } else if (self.jqgridOffer && $("#pfrm .detail .offer:visible").length > 0) {
                self.jqgridOffer.setGridWidth($("#pfrm .detail").width());
            } else if (self.jqgridJob && $("#pfrm .detail .job:visible").length > 0) {
                self.jqgridJob.setGridWidth($("#pfrm .detail").width());
            }
        }
    }

    /*
     * 招聘渠道统计
     * */
    var trenchStatistics = {
        jqgrid: null,
        chart: null,
        dataChart: null,
        chartId: "tsmChart",
        gridId: "trenchStatisticsGrid",
        optionGrid: {
            "hoverrows": false,
            "viewrecords": false,
            "gridview": true,
            "height": "auto",
            "loadonce": true,
            "rowNum": -1,
            "pager": "false",
            "scroll": false,
            "scrollrows": true,
            "treeGrid": true,
            "ExpandColumn": "name",
            "treedatatype": "json",
            "treeGridModel": "adjacency",
            "treeReader": {
                level_field: "level",
                parent_id_field: "parent",
                leaf_field: "isLeaf",
                expanded_field: "expanded"
            },
            "datatype": "json",
            "url": urls.getRecruitChannel,
            "colModel": [{
                "name": "channelName",
                "index": "channelName",
                "sorttype": "string",
                "label": "招聘渠道",
                "align": "left",
                "sortable": false
            }, {
                "name": "employNum",
                "index": "employNum",
                "sorttype": "numeric",
                "label": "已招人数",
                "align": "center",
                "sortable": false
            }, {
                "name": "dimissionRate",
                "index": "dimissionRate",
                "label": "试用期离职率",
                "align": "center",
                "sortable": false,
                "formatter": function (cellvalue, options, rowObject) {
                    return '<a href="javascript:;" class="trenchrate" data-channelid="' + rowObject.channelId + '" data-outlay="' + rowObject.outlay + '" data-parent="' + rowObject.parent + '">' + cellvalue + '</a>'
                }
            }, {
                "name": "days",
                "index": "days",
                "label": "招聘周期（天）",
                "align": "center",
                "sortable": false
            }, {
                "name": "outlay",
                "index": "outlay",
                "label": "人均招聘费用（元）",
                "align": "center",
                "sortable": false
            }, {
                "name": "parent",
                "index": "parent",
                "hidden": true
            }
            ],
            gridComplete: function () {
                $(".trenchrate").unbind("click").on("click", function () {
                    var outlay = $(this).data("outlay");
                    var channelId = $(this).data("channelid");
                    var parent = $(this).data("parent");
                    $("#trenchStatisticsModal").modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                        $("#tsm").css({height: $(window).height() - 120 + "px"});
                        $("#tsmChart").css({height: $(window).height() - 136 + "px"});
                        trenchStatistics.initChart(outlay, channelId, parent);
                    });
                });
            }
        },
        optionChart: {
            tooltip: {
                show: false
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
                    axisLine: {
                        lineStyle: {
                            width: 1,
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        lineStyle: {
                            width: 0
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        formatter: "{value}%"
                    }
                }
            ],
            series: [
                {
                    name: "试用期离职率统计",
                    type: "line",
                    data: [],
                    clickable: false,
                    itemStyle: {
                        emphasis: {
                            color: "rgba(255, 255, 255, 0)"
                        },
                        normal: {
                            label: {
                                show: true,
                                formatter: "{c}%"
                            }
                        }
                    },
                    smooth: true,
                    symbolSize: 2
                }
            ],
            grid: {
                borderWidth: 0
            }
        },
        init: function () {
            var self = this;
            self.renderGrid();
            self.renderEvent();
        },
        renderGrid: function () {
            var self = this;
            var keyName = $("#txtTrenchStatistics").val();
            $("#trenchStatistics").html('<table id="' + self.gridId + '"></table>');
            self.optionGrid.height = 305;
            self.optionGrid.postData = {organId: reqOrgId, keyName: keyName};
            self.jqgrid = $("#" + self.gridId).jqGrid(self.optionGrid);
            self.resizeGrid();
        },
        initChart: function (outlay, channelId, parent) {
            var self = this;
            loadingChart(self.chartId);
            $.get(urls.getProbationDismissionRate, {
                outlay: outlay,
                channelId: channelId,
                parent: parent
            }, function (data) {
                if (data && data.length > 0) {
                    self.renderChart();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.dataChart;

            var xaxisData = [], seriesData = [];
            $.each(data, function (i, item) {
                xaxisData.push(item.weekName);
                seriesData.push(Tc.formatFloat(item.dismissionRate * 100));
            });

            if (self.chart) {
                self.chart.clear();
            }
            self.chart = initEChart(self.chartId);

            self.optionChart.xAxis[0].data = xaxisData;
            self.optionChart.series[0].data = seriesData;

            self.chart.setOption(self.optionChart);
            self.chart.refresh();
            self.resizeChart();
        },
        renderEvent: function () {
            var self = this;
            $("#btnTrenchStatistics").unbind("click").click(function () {
                self.renderGrid();
            });
        },
        resize: function () {
            var self = this;
            self.resizeGrid();
            self.resizeChart();
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqgrid && $("#trenchStatistics:visible").length > 0) {
                self.jqgrid.setGridWidth($("#trenchStatistics").width());
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
            }
        }
    }

    /*
     * 人员异动提醒
     * */
    var transactionPrompt = {
        data: null,
        dataTotal: {promoteNum: 0, moveNum: 0, dimissionNum: 0, retireNum: 0},
        jqgrid: null,
        jqgridPromote: null,
        jqgridMove: null,
        jqgridDimission: null,
        jqgridRetire: null,
        optionGrid: {
            url: urls.getRecruitChangeList,
            mtype: 'GET',
            datatype: "json",
            width: 0,
            height: 0,
            colNames: [],
            colModel: [],
            rownumbers: true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '',
            postData: {},
            loadComplete: function (xhr) {
                updatePagerIcons();
                transactionPrompt.resizeGrid();
            }
        },
        init: function () {
            var self = this;
            $.get(urls.getRecruitChange, {organId: reqOrgId}, function (data) {
                self.data = data;
                self.render();
            });
        },
        render: function () {
            var self = this;
            var data = self.data;

            var obj = {
                promoteNum: 0,
                promoteName: '',
                moveNum: 0,
                moveName: '',
                dimissionNum: 0,
                dimissionName: '',
                retireNum: 0,
                retireName: ''
            };

            $.each(data, function (i, item) {
                switch (parseInt(item.curtName)) {
                    case 1:
                    {//晋升/晋级
                        obj.promoteNum = item.empNum;
                        obj.promoteName = item.empStr;
                        break;
                    }
                    case 4:
                    {//调动/调出
                        obj.moveNum = item.empNum;
                        obj.moveName = item.empStr;
                        break;
                    }
                    case 5:
                    {//离职
                        obj.dimissionNum = item.empNum;
                        obj.dimissionName = item.empStr;
                        break;
                    }
                    case 8:
                    {//退休
                        obj.retireNum = item.empNum;
                        obj.retireName = item.empStr;
                        break;
                    }
                }
            });
            self.renderText({id: "promote", number: obj.promoteNum, nameStr: obj.promoteName});
            self.renderText({id: "move", number: obj.moveNum, nameStr: obj.moveName});
            self.renderText({id: "dimission", number: obj.dimissionNum, nameStr: obj.dimissionName});
            self.renderText({id: "retire", number: obj.retireNum, nameStr: obj.retireName});

            self.event();
        },
        renderText: function (obj) {
            $("#" + obj.id + " .text span").text(obj.number);
            $("#" + obj.id + " .itembottom").text(obj.nameStr);
        },
        event: function () {
            var self = this;
            $(".transaction .itembd").unbind("click").on("click", function () {
                var index = $(this).data("index");
                self.dataTotal.promoteNum = $("#promote .text span").text();
                self.dataTotal.moveNum = $("#move .text span").text();
                self.dataTotal.dimissionNum = $("#dimission .text span").text();
                self.dataTotal.retireNum = $("#retire .text span").text();
                $("#transactionPromptModal").modal("show").on('shown.bs.modal', function () {
                    $("#tpm").css({height: $(window).height() - 120 + "px"});
                    $(".promoteNum").text(self.dataTotal.promoteNum);
                    $(".moveNum").text(self.dataTotal.moveNum);
                    $(".dimissionNum").text(self.dataTotal.dimissionNum);
                    $(".retireNum").text(self.dataTotal.retireNum);
                    self.optionGrid.width = $("#tpm .detail").width();
                    self.optionGrid.height = $("#tpm").height() - 150;
                    $("#tpm .tab").unbind("click").on("click", function () {
                        var i = $(this).data("index");
                        self.renderGridTab(i);
                        self.resizeGrid();
                    });
                    self.renderGridTab(index);
                    self.renderGrid();
                });
            });
        },
        //对话框 晋级、调动、离职、退休
        renderGrid: function () {
            var self = this;
            self.renderGridPromote();
            self.renderGridMove();
            self.renderGridDimission();
            self.renderGridRetire();
        },
        renderGridTab: function (index) {
            $("#tpm .tab").removeClass("selected").eq(index).addClass("selected");
            $("#tpm .tgrid").addClass("hide").eq(index).removeClass("hide");
        },
        renderGridPromote: function () {
            var self = this;
            var changeType = 1;
            if (self.jqgridPromote) {
                self.jqgridPromote.clearGridData().setGridParam({
                    postData: {changeType: changeType, organId: reqOrgId, total: self.dataTotal.promoteNum}
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.colNames = ['姓名', '性别', '年龄', '学历', '专业', '晋级前岗位', '现任岗位', '晋升日期'];
                self.optionGrid.colModel = [
                    {name: 'userNameCh', userNameCh: 'name', sortable: false, align: 'center'},
                    {name: 'sex', index: 'sex', sortable: false, align: 'center'},
                    {name: 'age', index: 'age', sortable: false, align: 'center'},
                    {name: 'degree', index: 'degree', sortable: false, align: 'center'},
                    {name: 'major', index: 'major', sortable: false, align: 'center'},
                    {name: 'positionNameEd', index: 'positionNameEd', sortable: false, align: 'center'},
                    {name: 'positionName', index: 'positionName', sortable: false, align: 'center'},
                    {name: 'changeDate', index: 'changeDate', sortable: false, align: 'center'}
                ];
                self.optionGrid.pager = "#promoteGridPage";
                self.optionGrid.postData = {
                    changeType: changeType,
                    organId: reqOrgId,
                    total: self.dataTotal.promoteNum
                };
                self.jqgridPromote = $("#promoteGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        renderGridMove: function () {
            var self = this;
            var changeType = 4;
            if (self.jqgridMove) {
                self.jqgridMove.clearGridData().setGridParam({
                    postData: {changeType: changeType, organId: reqOrgId, total: self.dataTotal.moveNum}
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.colNames = ['姓名', '性别', '年龄', '学历', '专业', '现任部门', '调任前岗位', '调任后岗位', '调任日期'];
                self.optionGrid.colModel = [
                    {name: 'userNameCh', index: 'userNameCh', sortable: false, align: 'center'},
                    {name: 'sex', index: 'sex', sortable: false, align: 'center'},
                    {name: 'age', index: 'age', sortable: false, align: 'center'},
                    {name: 'degree', index: 'degree', sortable: false, align: 'center'},
                    {name: 'major', index: 'major', sortable: false, align: 'center'},
                    {name: 'organizationName', index: 'organizationName', sortable: false, align: 'center'},
                    {name: 'positionNameEd', index: 'positionNameEd', sortable: false, align: 'center'},
                    {name: 'positionName', index: 'positionName', sortable: false, align: 'center'},
                    {name: 'changeDate', index: 'changeDate', sortable: false, align: 'center'}
                ];
                self.optionGrid.pager = "#moveGridPage";
                self.optionGrid.postData = {changeType: changeType, organId: reqOrgId, total: self.dataTotal.moveNum};
                self.jqgridMove = $("#moveGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        renderGridDimission: function () {
            var self = this;
            var changeType = 5;
            if (self.jqgridDimission) {
                self.jqgridDimission.clearGridData().setGridParam({
                    postData: {changeType: changeType, organId: reqOrgId, total: self.dataTotal.dimissionNum}
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.colNames = ['姓名', '性别', '年龄', '学历', '专业', '部门', '岗位', '离职日期'];
                self.optionGrid.colModel = [
                    {name: 'userNameCh', index: 'userNameCh', sortable: false, align: 'center'},
                    {name: 'sex', index: 'sex', sortable: false, align: 'center'},
                    {name: 'age', index: 'age', sortable: false, align: 'center'},
                    {name: 'degree', index: 'degree', sortable: false, align: 'center'},
                    {name: 'major', index: 'major', sortable: false, align: 'center'},
                    {name: 'organizationName', index: 'organizationName', sortable: false, align: 'center'},
                    {name: 'positionName', index: 'positionName', sortable: false, align: 'center'},
                    {name: 'changeDate', index: 'changeDate', sortable: false, align: 'center'}
                ];
                self.optionGrid.pager = "#dimissionGridPage";
                self.optionGrid.postData = {
                    changeType: changeType,
                    organId: reqOrgId,
                    total: self.dataTotal.dimissionNum
                };
                self.jqgridDimission = $("#dimissionGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        renderGridRetire: function (uuid) {
            var self = this;
            var changeType = 8;
            if (self.jqgridRetire) {
                self.jqgridRetire.clearGridData().setGridParam({
                    postData: {changeType: changeType, organId: reqOrgId, total: self.dataTotal.retireNum}
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.colNames = ['姓名', '性别', '年龄', '学历', '专业', '部门', '岗位', '退休日期'];
                self.optionGrid.colModel = [
                    {name: 'userNameCh', index: 'userNameCh', sortable: false, align: 'center'},
                    {name: 'sex', index: 'sex', sortable: false, align: 'center'},
                    {name: 'age', index: 'age', sortable: false, align: 'center'},
                    {name: 'degree', index: 'degree', sortable: false, align: 'center'},
                    {name: 'major', index: 'major', sortable: false, align: 'center'},
                    {name: 'organizationName', index: 'organizationName', sortable: false, align: 'center'},
                    {name: 'positionName', index: 'positionName', sortable: false, align: 'center'},
                    {name: 'changeDate', index: 'changeDate', sortable: false, align: 'center'}
                ];
                self.optionGrid.pager = "#retireGridPage";
                self.optionGrid.postData = {changeType: changeType, organId: reqOrgId, total: self.dataTotal.retireNum};
                self.jqgridRetire = $("#retireGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqgridPromote && $("#tpm .detail .promote:visible").length > 0) {
                self.jqgridPromote.setGridWidth($("#tpm .detail").width());
            } else if (self.jqgridMove && $("#tpm .detail .move:visible").length > 0) {
                self.jqgridMove.setGridWidth($("#tpm .detail").width());
            } else if (self.jqgridDimission && $("#tpm .detail .dimission:visible").length > 0) {
                self.jqgridDimission.setGridWidth($("#tpm .detail").width());
            } else if (self.jqgridRetire && $("#tpm .detail .retire:visible").length > 0) {
                self.jqgridRetire.setGridWidth($("#tpm .detail").width());
            }
        }
    }


    /*
     *************************************************************
     * 页签二开始
     * ***********************************************************
     * */
    //高绩效员工画像
    var performance = {
        data: null,
        dataDialog: null,
        dataTemp: null,
        abilityHtml: '',
        init: function () {
            var self = this;
            var positionName = $("#txtPerformance").val();
            var yearNum = $("#yeardefault").text();
            var continueNum = $("#frequencydefault").text();
            var star = $("#stardefault").text();
            $("#performance .bd").addClass("hide").eq(1).removeClass("hide");
            $("#performance .set div").addClass("hide");
            $.get(urls.getPositionImages, {
                positionName: positionName,
                yearNum: yearNum,
                continueNum: continueNum,
                star: star
            }, function (data) {
                $("#performance .bd").addClass("hide").eq(0).removeClass("hide");
                $("#performance .set div").removeClass("hide");
                if (data) {
                    self.data = data;
                    self.renderPage(data);
                } else {
                    $("#performance .pfleft,#performance .pfright").html("");
                }
            });

            self.event();
        },
        initDialog: function () {
            var self = this;
            //人员标签
            self.initDialogTag(self.data);

            var positionName = $("#txtPerformance").val();
            $("#star,#school,#degree").html('<option value="0">数据加载中</option>');
            $("#ability").html('<div class="msg">数据加载中</div>');
            $.get(urls.getImagesQueryTags, {positionName: positionName}, function (data) {
                if (data && data.length > 0) {
                    self.dataDialog = data;
                    self.renderDialog();
                } else {
                    $("#ability").html('暂无数据');
                }
            });
        },
        initDialogAbility: function (data) {
            var self = this;
            var aby = _.filter(data, function (item) {
                return item.tagType == 1;
            });
            $("#ability").html('');
            if (aby.length > 0) {
                $.each(aby, function (i, item) {
                    var abilityObj = $(self.abilityHtml);
                    abilityObj.find("option[value='" + item.tagId + "']").attr("selected", "selected");
                    $("#ability").append(abilityObj);
                });
            } else {
                $("#ability").append(self.abilityHtml);
            }
        },
        initDialogTag: function (data) {
            var tags = _.filter(data, function (item) {
                return item.tagType == 2;
            });
            if (tags.length > 0) {
                //人员标签
                var tag = [];
                $.each(tags, function (i, item) {
                    if (item.tagType == 2) {
                        tag.push('<div class="tag selected" data-id="' + item.tagName + '">' + item.tagName + '</div>');
                    }
                });
                $("#tag").html(tag.join(""));
            } else {
                $("#tag").html('<div class="msg">暂无数据</div>');
            }
        },
        renderPage: function (data) {
            var self = this;
            //数据类型 0:基本信息 1:能力素质  2:关健人才优势
            var all = _.sortBy(data, function (item) {
                return item.tagType;
            });
            var left = [], right = [];
            $.each(all, function (i, item) {
                if (i < 14) {
                    var tag = '<div data-tagid="' + item.tagId + '" data-tagname="' + item.tagName + '" class="icon-circle' + (item.tagType == 1 ? " pre1" : (item.tagType == 2 ? " pre2" : "")) + '" title="' + item.tagName + " " + Tc.formatFloat(item.tagVal * 100) + "%" + '">' + item.tagName + " " + Tc.formatFloat(item.tagVal * 100) + "%" + '</div>';
                    if (i % 2 == 0) {
                        left.push(tag);
                    } else {
                        right.push(tag);
                    }
                }
            });
            $("#performance .pfleft").html(left.join(''));
            $("#performance .pfright").html(right.join(''));
        },
        renderDialog: function () {
            var self = this;
            var data = self.dataDialog;

            var star = [], school = [], degree = [], abilitySelect = [], abilityScore = [];
            //0:绩效 1:学校类型  2:学历  3:能力素质分值
            $.each(data, function (i, item) {
                switch (item.tagType) {
                    case 0:
                    {
                        star.push(item);
                        break;
                    }
                    case 1:
                    {
                        school.push(item);
                        break;
                    }
                    case 2:
                    {
                        degree.push(item);
                        break;
                    }
                    case 3:
                    {
                        abilityScore.push(item);
                        break;
                    }
                    case 4:
                    {
                        abilitySelect.push(item);
                        break;
                    }
                }

            });

            //模拟数据
            abilitySelect.push({tagId: "6681c295173e11e6a03808606e0aa89a", tagName: "高度整合能力", tagVal: 199, tagType: 1});
            abilitySelect.push({tagId: "6681c868173e11e6a03808606e0aa89a", tagName: "服从领导", tagVal: 140, tagType: 1});
            abilitySelect.push({tagId: "6681caf4173e11e6a03808606e0aa89a", tagName: "上进心强", tagVal: 136, tagType: 1});

            //星
            star = _.sortBy(star, function (item) {
                return item.tagVal;
            });
            $("#star").html(self.getSelectOption(star));
            //学校
            school = _.sortBy(school, function (item) {
                return item.tagVal;
            });
            $("#school").html('<option value="0">全部院校</option>' + self.getSelectOption(school));
            //学历
            degree = _.sortBy(degree, function (item) {
                return item.tagVal;
            });
            $("#degree").html('<option value="0">全部学历</option>' + self.getSelectOption(degree));
            //能力素质 下拉
            abilitySelect = _.sortBy(abilitySelect, function (item) {
                return item.tagVal;
            });
            var selectHtml = self.getSelectOption(abilitySelect);
            //能力素质 评分
            abilityScore = _.sortBy(abilityScore, function (item) {
                return item.tagVal;
            });

            var score = [];
            $.each(abilityScore, function (i, item) {
                score.push('<div class="aly" data-id="' + item.tagId + '">' + item.tagName + '</div>');
            });
            var scoreHtml = score.join("");

            //能力素质
            var ability = [];
            ability.push('<div class="ability clearfix">');
            ability.push('<div class="aby">');
            ability.push('<i class="add fa fa-plus-circle"></i>');
            ability.push('<i class="remove fa fa-minus-circle"></i>');
            ability.push('<i class="eyeshow eye fa fa-eye"></i>');
            ability.push('<i class="eyehide eye fa fa-eye-slash color999 hide"></i>');
            ability.push('<select>');
            ability.push(selectHtml);
            ability.push('</select>');
            ability.push('</div>');
            ability.push('<div class="abyc clearfix">');
            ability.push('<div class="all selected">不限</div>');
            ability.push('<div class="clearfix">');
            ability.push(scoreHtml);
            ability.push('</div>');
            ability.push('</div>');
            ability.push('</div>');
            self.abilityHtml = ability.join('');

            //绑定数据
            $("#year").val($("#yeardefault").text());
            $("#frequency").val($("#frequencydefault").text());
            $("#star").val($("#stardefault").text());
            $("#sex").val($("#performance .pfleft div").eq(0).data("tagid"));
            $("#degree").val($("#performance .pfleft div").eq(1).data("tagid"));
            $("#school").val($("#performance .pfright div").eq(0).data("tagname"));
            self.initDialogAbility(self.data);

            //对话框事件
            self.renderDialogEvent();
        },
        renderDialogEvent: function () {
            var self = this;
            $("#ability .add").unbind("click").on("click", function () {
                if ($(self.abilityHtml).find("select option").length > $("#ability .ability").length) {
                    $("#ability").append(self.abilityHtml);
                    self.renderDialogEvent();
                }
            });
            $("#ability .remove").unbind("click").on("click", function () {
                if ($("#ability .ability").length > 1) {
                    $(this).parents(".ability").remove();
                }
            });
            $("#ability .eyeshow").unbind("click").on("click", function () {
                $(this).addClass("hide");
                $(this).parents(".ability").find(".eyehide").removeClass("hide");
                $(this).parents(".ability").addClass("disabled");
                $(this).parent().find("select").attr("disabled", "disabled");
            });
            $("#ability .eyehide").unbind("click").on("click", function () {
                $(this).addClass("hide");
                $(this).parents(".ability").find(".eyeshow").removeClass("hide");
                $(this).parents(".ability").removeClass("disabled");
                $(this).parent().find("select").removeAttr("disabled");
            });
            $("#ability .abyc .all").unbind("click").on("click", function () {
                var obj = $(this).parents(".abyc");
                if (!$(this).hasClass("selected")) {
                    $(this).addClass("selected");
                    obj.find(".aly").removeClass("selected");
                }
            });
            $("#ability .abyc .aly").unbind("click").on("click", function () {
                var obj = $(this).parent();
                if ($(this).hasClass("selected")) {
                    $(this).removeClass("selected");
                    if (obj.find(".selected").length == 0) {
                        $(this).parents(".abyc").find(".all").addClass("selected");
                    }
                } else {
                    $(this).addClass("selected");
                    $(this).parents(".abyc").find(".all").removeClass("selected");
                }
            });
            //人员标签事件
            $("#ptm .pcontent .content .tag").unbind("click").on("click", function () {
                if ($(this).hasClass("selected")) {
                    $(this).removeClass("selected");
                } else {
                    $(this).addClass("selected");
                }
            });
        },
        event: function () {
            var self = this;
            //点击搜索
            $("#btnperformance").unbind("click").click(function () {
                self.init();
            });

            //打开对话框
            $("#performanceSetting").unbind("click").click(function () {
                $("#portrayalModal").modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    $("#ptm").css({height: $(window).height() - 120 + "px"});
                    self.initDialog();
                });
            });

            //对话框 下拉事件
            $("#year,#frequency,#star").unbind("change").change(function () {
                var positionName = $("#txtPerformance").val();
                var yearNum = $("#year").val();
                var continueNum = $("#frequency").val();
                var star = $("#star").val();
                $("#tag").html('<div class="msg">数据加载中</div>');
                $.get(urls.getPositionImages, {
                    positionName: positionName,
                    yearNum: yearNum,
                    continueNum: continueNum,
                    star: star
                }, function (data) {
                    self.dataTemp = data;
                    if (data && data.length > 0) {
                        self.initDialogAbility(data);
                        self.initDialogTag(data);

                        //对话框事件
                        self.renderDialogEvent();
                    } else {
                        $("#tag").html('<div class="msg">暂无数据</div>');
                    }
                });
            });

            //调整画像
            $("#save").unbind("click").click(function () {
                $("#yeardefault").text($("#year").val());
                $("#frequencydefault").text($("#frequency").val());
                $("#stardefault").text($("#star").val());
                self.renderPage(self.dataTemp);
            });
        },
        getSelectOption: function (obj) {
            var option = [];
            $.each(obj, function (i, item) {
                option.push('<option value="' + (item.tagId == null ? item.tagName : item.tagId) + '">' + item.tagName + '</option>');
            });
            return option.join("");
        },
        resizeGrid: function () {
            var self = this;
            if (self.jqgrid && $("#performanceGrid:visible").length > 0) {
                self.jqgrid.setGridWidth($("#performanceList").width());
            }
        }
    }

    ///招聘岗位薪酬参考
    var remuneration = {
        gridId: "remunerationGrid",
        data: null,
        init: function () {
            var self = this;
            $("#" + self.gridId + " tbody").html('<tr><td colspan="4"><div class="nodata">数据加载中</div></td></tr>');
            $.get(urls.getPositionPay, {keyName: $("#txtRemuneration").val(), organId: reqOrgId}, function (data) {
                if (data && data.length > 0) {
                    self.data = data;
                    self.render();
                } else {
                    $("#" + self.gridId + " tbody").html('<tr><td colspan="4"><div class="nodata">暂无数据</div></td></tr>');
                }
            });
            self.event();
        },
        render: function () {
            var self = this;
            var data = self.data;
            var positionId = '', total = 0, avg = 0, n = 0, j = 0, array = [], len = data.length;
            $.each(data, function (i, item) {
                if (item.positionId != positionId) {
                    positionId = item.positionId;
                    total = avg = n = j = 0;
                    $.each(data, function (i, item) {
                        if (item.positionId == positionId) {
                            n++;
                            j++;
                            total += item.pay * 10000;
                        }
                    });
                    avg = Tc.formatFloat(total / parseFloat(n));

                    array.push('<tr' + (i + j == len ? ' class="nb"' : '') + '>');
                    array.push('	<td rowspan="' + n + '">' + item.positionName + '</td>');
                    array.push('	<td>' + item.rankName + '</td>');
                    array.push('	<td>' + Tc.formatFloat(item.pay * 10000) + '</td>');
                    array.push('	<td rowspan="' + n + '">' + avg + '</td>');
                    array.push('</tr>');
                } else {
                    array.push('<tr' + (i == len - 1 ? ' class="nb"' : '') + '>');
                    array.push('	<td>' + item.rankName + '</td>');
                    array.push('	<td>' + Tc.formatFloat(item.pay * 10000) + '</td>');
                    array.push('</tr>');
                }

            });
            $("#" + self.gridId + " tbody").html(array.join(''));
        },
        event: function () {
            var self = this;
            $("#btnRemuneration").unbind("click").click(function () {
                self.init();
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
            case "page-one":
            {//页签1
                if (t.tabFirstLoaded) {
                    trenchStatistics.resize();
                    transactionPrompt.resizeGrid();
                    positionFillRateObj.resizeGrid();
                }
                break;
            }
            case "page-two":
            {//页签2
                if (t.tabSecondLoaded) {
                    performance.resizeGrid();
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
        click: function (page) {
            var self = this;
            if (self.tabName == page)return;

            self.tabName = page;
            switch (page) {
                case "page-one":
                {
                    self.initFirstTab();
                    break;
                }
                case "page-two":
                {
                    self.initSecondTab();
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
        initFirstTab: function () {
            var self = this;
            if (!self.tabFirstLoaded) {
                self.tabFirstLoaded = true;
                recruitingPosition.init();
                recruitingNum.init();
                recruitingCost.init();

                trenchStatistics.init();
                transactionPrompt.init();
                positionFillRateObj.init();
            }
        },
        //页签2
        initSecondTab: function () {
            var self = this;
            if (!self.tabSecondLoaded) {
                self.tabSecondLoaded = true;
                performance.init();
                remuneration.init();
            }
        }
    };
    pageObj.initFirstTab();
});