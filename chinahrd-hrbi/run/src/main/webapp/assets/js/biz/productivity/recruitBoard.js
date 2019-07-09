/**
 * Created by Administrator on 2016/5/4.
 */
require(['jquery', 'jquery-ui', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'timeLine2', 'resize', 'messenger', 'select2'], function ($, jqueryui, echarts) {
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
        getPosition: webRoot + "/common/getPosition.do", //高绩效员工画像 职位下拉菜单
        getPositionPerfEmpCount: webRoot + '/recruitBoard/getPositionPerfEmpCount.do',  //获取岗位高绩效人群统计
        getPositionImages: webRoot + "/recruitBoard/getPositionImages.do", //高绩效员工画像
        getImagesPerformatTags: webRoot + "/recruitBoard/getImagesPerformatTags.do", //高绩效员工画像 对话框页面元素
        getImagesQueryTags: webRoot + "/recruitBoard/getImagesQueryTags.do", //高绩效员工画像 对话框页面元素
        getPositionRecommendEmp: webRoot + "/recruitBoard/getPositionRecommendEmp.do",      //推荐人群明细
        getPositionRecommendCount: webRoot + '/recruitBoard/getPositionRecommendCount.do',          //推荐人群统计
        getPositionPay: webRoot + "/recruitBoard/getPositionPay.do" //招聘岗位薪酬参考
    };
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;

        timeLineObj.init(organId);

        var t = pageObj;
        t.tabFirstLoaded = t.tabSecondLoaded = t.tabThreeLoaded = t.tabFourLoaded = false;
        switch (t.tabName) {
            case "page-one": {
                t.initFirstTab();
                break;
            }
            case "page-two": {
                t.initSecondTab();
                break;
            }
        }
    };

    //初始化'加入对比'
    var stackObject;

    function loadStack() {
        var win = window != top ? top.window : window;
        if (win.stackObject) stackObject = win.stackObject;
        if (stackObject && stackObject.bottomStock('getPersonIds') != '')
            stackObject.bottomStock('hideFrame', 0);
        else
            stackObject.bottomStock('hideFrame', 1);
    }

    loadStack();

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

    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
            $chart.children().show();
        }
    }

    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
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

    //岗位
    var position = {
        hasInit: false,
        select2: null,
        data: null,
        init: function (positionId, callback) {
            var self = this;
            if (self.hasInit) {
                if (positionId) {
                    self.select2.val(positionId).trigger("change");
                } else {
                    var position = self.select2.val();
                    if (_.isFunction(callback)) callback(position);
                }
                return;
            }
            $.post(urls.getPosition, function (data) {
                var dataPosition = [];
                $.each(data, function (i, item) {
                    dataPosition.push({id: item.k, text: item.v});
                });

                //岗位下拉菜单
                self.select2 = $("#selectPosition").select2({
                    data: dataPosition,
                    placeholder: "请输入岗位名称"
                }).unbind('select2:select').on('select2:select', function (evt) {
                    if (_.isFunction(callback)) callback(evt.params.data.id);
                });

                if (positionId) {
                    self.select2.val(positionId).trigger("change");
                } else {
                    var position = self.select2.val();
                    if (_.isFunction(callback)) callback(position);
                }
                self.hasInit = true;
            });
        },
        getSelect2Val: function () {
            var self = this;
            return self.select2.val();
        }
    };

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
            $("#recruitingPositionNum .index-yj-div-bottom-value").text(text.join('、'));
            $("#rpm .content").html(tm.join(''));
        },
        event: function () {
            var self = this;
            $("#recruitingPositionNum .body-div").unbind("click").on("click", function () {
                $("#recruitingPositionModal").modal("show").on('shown.bs.modal', function () {
                    $("#recruitingPositionModal .modal-header-text span").text('（' + $("#recruitingPositionNum .accord-yj-float-value").text() + ' 个）');
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
            $("#recruitingCost .accord-yj-float-value").html(Tc.formatDigital(Tc.formatFloat(data.outlay)));
            $("#recruitingCost .accord-bottom-float-value").text(Tc.formatFloat(data.outlayRate * 100));
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
            height: 356,
            styleUI: 'Bootstrap',
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
                        return rowObject.url == "" ? "-" : ('<a class="trenchrate go-link" href="javascript:void(0)" data-toggle="' + rowObject.url + '">查看简历</a>');
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
                positionFillRateObj.resizeGrid();

                $('.go-link').unbind('click').click(function (e) {
                    var $this = $(this);
                    var link = $.trim($this.data('toggle'));
                    if (!_.isEmpty(link)) {
                        if (link.indexOf('http://') == -1 && link.indexOf('https://') == -1) win.open('http://' + link);
                        else win.open(link);
                    }
                });
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
                    $("#positionSettingModal .b .content").html("");
                }
            });
        },
        render: function () {
            var self = this;
            var data = self.data;
            var text = [], textshow = [], texthide = [];

            $.each(data, function (i, item) {
                if (item.view) {
                    //待招人数超多则显示0
                    var waitRecruitNum = item.waitRecruitNum < 0 ? 0 : item.waitRecruitNum;

                    text.push('<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 frb" data-uuid="' + item.id + '">');
                    text.push('  <div class="frc">');
                    text.push('	<div class="title">');
                    text.push('	  <div class="title1" data-positionid="' + item.positionId + '">【<span>' + item.positionName + '</span>】满足率</div>');
                    text.push('	  <div class="title2">(招聘周期：<span>' + item.startDate.substr(0, 10).replace("-", '.').replace("-", '.') + '-' + item.endDate.substr(0, 10).replace("-", '.').replace("-", '.') + '</span>)</div>');
                    text.push('	</div>');
                    text.push('	<div>');
                    text.push('	  <div class="rate' + (item.warn ? ' red' : '') + '">');
                    text.push('		<div><span class="rateNum">' + Tc.formatFloat(item.meetRate * 100) + '</span><small>%</small></div>');
                    text.push('	  </div>');
                    text.push('	  <div>');
                    text.push('		<label class="ratetext">');
                    text.push('		  待招：<span class="vacancy">' + waitRecruitNum + '</span>人<br>');
                    text.push('		  计划：<span class="plan">' + item.planNum + '</span>人');
                    text.push('		</label>');
                    text.push('	  </div>');
                    text.push('	  <div class="schedule">');
                    text.push('		<ul>');
                    text.push('		  <li class="noleft' + (item.isPublic == 1 ? ' lired' : '') + '">发布</li>');
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
                $("#positionFillRateModal").modal("show").unbind('shown.bs.modal').on('shown.bs.modal', function () {
                    $("#positionFillRateModal .modal-header-text span").text(name);
                    $("#pfrm .resumeNum").text(resumeNum);
                    $("#pfrm .interviewNum").text(interviewNum);
                    $("#pfrm .offerNum").text(offerNum);
                    $("#pfrm .jobNum").text(jobNum);
                    self.optionGrid.width = $("#pfrm .detail").width();
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
            hoverrows: false,
            viewrecords: false,
            autowidth: true,
            // gridview: true,
            rowHeight: 36,
            styleUI: 'Bootstrap',
            height: "auto",
            loadonce: true,
            rowNum: -1,
            pager: "false",
            scroll: false,
            scrollrows: true,
            treeGrid: true,
            ExpandColumn: "name",
            treedatatype: "json",
            treeGridModel: "adjacency",
            treeReader: {
                level_field: "level",
                parent_id_field: "parent",
                leaf_field: "isLeaf",
                expanded_field: "expanded"
            },
            datatype: "json",
            mtype: "POST",
            url: urls.getRecruitChannel,
            colModel: [
                {"name": "channelName", "sorttype": "string", "label": "招聘渠道", "align": "left", "sortable": false},
                {"name": "employNum", "sorttype": "numeric", "label": "已招人数", "align": "center", "sortable": false},
                {
                    "name": "dimissionRate", "label": "试用期离职率", "align": "center", "sortable": false,
                    "formatter": function (cellvalue, options, rowObject) {
                        if (cellvalue == 0) return cellvalue;
                        return '<a href="javascript:void(0);" class="trenchrate" data-channelid="' + rowObject.channelId + '" data-num="' + rowObject.employNum + '" data-parent="' + rowObject.parent + '">' + cellvalue + '</a>';
                    }
                },
                {"name": "days", "label": "招聘周期（天）", "align": "center", "sortable": false},
                {
                    "name": "outlay", "index": "outlay", "label": "人均招聘费用（元）", "align": "center", "sortable": false,
                    "formatter": function (cellvalue) {
                        return Tc.formatFloat(cellvalue);
                    }
                },
                {"name": "parent", "index": "parent", "hidden": true}
            ],
            gridComplete: function () {
                $(".trenchrate").unbind("click").on("click", function () {
                    var employNum = $(this).data("num");
                    var channelId = $(this).data("channelid");
                    var parent = $(this).data("parent");
                    $("#trenchStatisticsModal").modal("show");
                    trenchStatistics.initChart(employNum, channelId, parent);
                });
            }
        },
        chartOption: {
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
                    splitLine: {show: false},
                    axisTick: false,
                    boundaryGap: false,
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#cecece',
                            width: 1
                        }
                    },
                    axisLabel: {
                        itemStyle: {
                            color: '#000000'
                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    nameTextStyle: {
                        color: '#000000',
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#cecece',
                            width: 1
                        }
                    },
                    axisLabel: {
                        formatter: "{value}%",
                        itemStyle: {
                            color: '#000000',

                        },
                        textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontStyle: 'normal',
                            fontWeight: 'normal'
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    name: "试用期离职率统计",
                    type: "line",
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: "{c}%"
                                // textStyle: {
                                //     color: 'black'
                                // }
                            }
                        }
                    },
                    data: []
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
            self.organId = reqOrgId;
            self.resizeGrid();
        },
        initChart: function (employNum, channelId, parent) {
            var self = this;
            loadingChart(self.chartId);
            var organId = self.organId;
            $.get(urls.getProbationDismissionRate, {
                employNum: employNum,
                channelId: channelId,
                parent: parent,
                organId: organId
            }, function (data) {
                if (data && data.length > 0) {
                    self.dataChart = data;
                    hideChart(self.chartId, false);
                    self.renderChart();
                } else {
                    hideChart(self.chartId, true);
                }
            });
        },
        renderChart: function () {
            var self = this;
            var data = self.dataChart;
            var xAxisData = [0], seriesData = ["-"];
            $.each(data, function (i, item) {
                xAxisData.push(item.weekName);
                seriesData.push(Tc.formatFloat(item.dismissionRate * 100));
            });
            if (_.isNull(self.chart)) self.chart = initEChart(self.chartId);
            else self.chart.clear();

            self.chartOption.xAxis[0].data = xAxisData;
            self.chartOption.series[0].data = seriesData;
            self.chart.setOption(self.chartOption, true);
            self.chart.refresh();

            setTimeout(function () {
                trenchStatistics.resizeChart();
            }, 500);
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
            if (!_.isNull(self.chart)) self.chart.resize();
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
            height: 355,
            colNames: [],
            colModel: [],
            rownumbers: true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            rowHeight: 36,
            styleUI: 'Bootstrap',
            pager: '',
            postData: {},
            loadComplete: function (xhr) {
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
                    case 1: {//晋升/晋级
                        obj.promoteNum = item.empNum;
                        obj.promoteName = item.empStr;
                        break;
                    }
                    case 4: {//调动/调出
                        obj.moveNum = item.empNum;
                        obj.moveName = item.empStr;
                        break;
                    }
                    case 5: {//离职
                        obj.dimissionNum = item.empNum;
                        obj.dimissionName = item.empStr;
                        break;
                    }
                    case 8: {//退休
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
                    $(".promoteNum").text(self.dataTotal.promoteNum);
                    $(".moveNum").text(self.dataTotal.moveNum);
                    $(".dimissionNum").text(self.dataTotal.dimissionNum);
                    $(".retireNum").text(self.dataTotal.retireNum);
                    self.optionGrid.width = $("#tpm .detail").width();
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
                    {name: 'sex', sortable: false, align: 'center'},
                    {name: 'age', sortable: false, align: 'center'},
                    {name: 'degree', sortable: false, align: 'center'},
                    {name: 'major', sortable: false, align: 'center'},
                    {name: 'positionNameEd', sortable: false, align: 'center'},
                    {name: 'positionName', sortable: false, align: 'center'},
                    {name: 'changeDate', sortable: false, align: 'center'}
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
        abilitys: null,
        abilityHtml: '',
        optionGrid: {
            url: urls.getPositionRecommendEmp,
            mtype: 'POST',
            datatype: "json",
            autowidth: true,
            height: 280,
            colNames: ['', '姓名', '匹配度', '所属组织', '能力', '操作'],
            colModel: [
                {
                    name: 'imgPath', width: 50, sortable: false, align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        var imgPath = _.isEmpty(cellvalue) ? webRoot + '/assets/photo.jpg' : rowObject.imgPath;
                        return '<img src="' + imgPath + '" class="head-pic img-circle" width="35">';
                    }
                },
                {name: 'userNameCh', width: 100, sortable: false, align: 'center'},
                {
                    name: 'score', width: 80, sortable: false, align: 'center',
                    formatter: function (cellvalue) {
                        return Tc.formatFloat(cellvalue * 100) + '%';
                    }
                },
                {
                    name: 'organName', width: 120, sortable: false, align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        return rowObject.type == 0 ? cellvalue : "外部人才";
                    }
                },
                {
                    name: 'qualitysStr', width: 150, sortable: false, align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        var arr = [];
                        if (_.isNull(cellvalue)) return '';

                        var qualitys = cellvalue.split(',');
                        // var abilitys = performance.abilitys;
                        // if (abilitys) return "";
                        $.each(qualitys, function (i, item) {
                            var vals = item.split(':');
                            arr.push('<div>' + vals[0] + " " + vals[1] + "</div>");
                        });
                        return arr.join("");
                    }
                },
                {
                    name: 'type', width: 100, sortable: false, align: 'center',
                    formatter: function (cellvalue, options, rowObject) {
                        if (rowObject.type == 0) {
                            var s = "<div><a class='trenchrate' href='" + webRoot + "/talentProfile/toTalentDetailView.do?empId=" + rowObject.empId + "' target='_blank'>人才剖像</a></div>";
                            s += "<div><a href='javascript:void(0)' class='trenchrate contrast' data-toggle='" + rowObject.empId + "'>加入对比</a></div>";
                            return s;
                        } else {
                            return '<a class="trenchrate goLink" href="javascript:void(0)" data-toggle="' + rowObject.url + '">查看简历</a>'
                        }
                    }
                }
            ],
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 30],
            altRows: true,
            pager: '',
            styleUI: 'Bootstrap',
            postData: {},
            loadComplete: function (xhr) {
                performance.resizeGrid();

                $('a.contrast').unbind('click').click(function () {
                    var $this = $(this);
                    var empId = $this.data('toggle');
                    if (stackObject) {
                        stackObject.bottomStock('hideFrame', 0);
                        stackObject.bottomStock('addPerson', empId);
                    }
                });

                $('a.goLink').unbind('click').click(function (e) {
                    var $this = $(this);
                    var link = $.trim($this.data('toggle'));
                    if (!_.isEmpty(link)) {
                        if (link.indexOf('http://') == -1 && link.indexOf('https://') == -1) win.open('http://' + link);
                        else win.open(link);
                    }
                });

            }
        },
        dataPosition: null,
        hasInitDialog: false,
        init: function () {
            var self = this;
            //设置岗位下拉菜单选中值
            var positionId = '';
            if ($("#positionFillRate .data .frb").length > 0) {
                positionId = $("#positionFillRate .data .frb .title1").first().data("positionid");
            }
            position.init(positionId, function (key) {
                self.init2(key);
            });
            self.renderPageEvent();
            if (!positionId) return;
            self.init2(positionId);
        },
        init2: function (positionId) {
            var self = this;
            positionId = positionId || self.positionId;

            var yearNum = $("#yeardefault").text();
            var continueNum = $("#frequencydefault").text();
            var star = $("#stardefault").text();
            $("#performance .bd").addClass("hide").eq(1).removeClass("hide");
            $("#performance .set div").addClass("hide");
            $.post(urls.getPositionImages, {
                position: positionId,
                yearNum: yearNum,
                continueNum: continueNum,
                star: star
            }, function (data) {
                $("#performance .set div").removeClass("hide");
                $('#noBodyDialogBox').addClass('hide');

                $("#performance .bd").addClass("hide").eq(0).removeClass("hide");
                if (data) {
                    $('#performance,#bodyRecommend').show();
                    self.data = data;
                    self.renderPage(data);
                    self.hasInitDialog = false;
                } else {
                    $('#performance, #bodyRecommend').hide();
                    $('#noBodyDialogBox').removeClass('hide');
                }
                self.positionId = positionId;
            });
        },
        renderPage: function (data) {
            var self = this;
            var existLen = 0;
            //数据类型 0:基本信息   1:能力素质  2:关健人才优势
            var all = _.sortBy(data, function (item) {
                if (!_.isNull(item.tagId)) existLen++;
                return item.tagType;
            });
            var left = [], right = [], showNum = 14, len = existLen > showNum ? showNum : existLen, leftLen = parseInt(len / 2) + (len % 2 == 0 ? 0 : 1);
            $.each(all, function (i, item) {
                if (i < showNum) {
                    if (_.isNull(item.tagId)) return true;
                    var tagName = item.tagType == 0 ? (item.tagName + " " + Tc.formatFloat(item.tagVal * 100) + "%") : item.tagName + " " + item.tagVal + "人";
                    var baseType = item.tagScore < 14 && item.tagScore > 10 ? Number(item.tagScore) : '';
                    var tag = '<div type="' + baseType + '" data-tagid="' + item.tagId + '" data-tagname="'
                        + item.tagName + '" class="icon-circle' +
                        (item.tagType == 1 ? " pre1" : (item.tagType == 2 ? " pre2" : ""))
                        + '" title="' + tagName + '">' + tagName + '</div>';
                    if (i < leftLen) {
                        left.push(tag);
                    } else {
                        right.push(tag);
                    }
                }
            });
            $("#performance .pfleft").html(left.join(''));
            $("#performance .pfright").html(right.join(''));

            //人员推荐
            var baseinfo = [], ability = [], content = [], quality = [];
            $.each(all, function (i, item) {
                switch (item.tagType) {
                    case 0: {
                        baseinfo.push({id: item.tagId, name: item.tagName});
                        break;
                    }
                    case 1: {
                        ability.push({id: item.tagId, name: item.tagName, score: item.tagScore});
                        quality.push({k: item.tagId, v: item.tagScore + ""});
                        break;
                    }
                    case 2: {
                        content.push(item.tagId);
                        break;
                    }
                }
            });
            self.abilitys = ability;

            var qualityStr = JSON.stringify(quality);

            var contentStr = content.join(",");
            var postData = {
                sex: baseinfo[0].id,
                degreeId: baseinfo[2].id,
                schoolType: baseinfo[1].id,
                contentStr: contentStr,
                qualityStr: qualityStr
            };
            self.postData = postData;
            self.renderGrid(postData);

            //显示男女头像
            if (baseinfo[0].id == 'm') {
                $("#performance .avatar").addClass("hide").eq(0).removeClass("hide");
            } else {
                $("#performance .avatar").addClass("hide").eq(1).removeClass("hide");
            }
        },
        renderPageEvent: function () {
            var self = this;
            //点击搜索
            $("#btnperformance").click(function () {
                self.init();
            });

            //打开高绩效员工画像对话框
            $("#performanceSetting, #performanceSettingLink").click(function () {
                $("#portrayalModal").modal("show").on('shown.bs.modal', function () {
                    $("#year").val($("#yeardefault").text());
                    $("#frequency").val($("#frequencydefault").text());

                    $("#star").html('<option value="0">数据加载中</option>');
                    $.get(urls.getImagesPerformatTags, function (data) {
                        if (data && data.length > 0) {
                            var star = [];
                            $.each(data, function (i, item) {
                                star.push('<option value="' + item.tagId + '">' + item.tagName + '</option>');
                            });
                            $("#star").html(star.join('')).val($("#stardefault").text());

                            $("#save").unbind("click").on("click", function () {
                                $("#yeardefault").text($("#year").val());
                                $("#frequencydefault").text($("#frequency").val());
                                $("#stardefault").text($("#star").val());
                                self.init2();
                                $("#portrayalModal").modal("hide");
                            });
                            self.getPerfNumbers();
                        } else {
                            $("#star").html('<option value="0">暂无数据</option>');
                            $("#save").unbind("click");
                        }
                    });

                    $('#year,#frequency,#star').unbind('change').change(function () {
                        $("#yeardefault").text($("#year").val());
                        $("#frequencydefault").text($("#frequency").val());
                        $("#stardefault").text($("#star").val());
                        self.getPerfNumbers();
                    });
                });
            });

            //打开人员推荐对话框
            $("#performanceListSetting").click(function () {
                $("#portrayalListModal").modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    $("#ptm").css({height: $(window).height() - 120 + "px"});
                    if (!self.hasInitDialog) self.initDialog();

                    //确定按钮
                    $("#saveList").unbind("click").click(function () {
                        var postData = self.getPostData();
                        self.renderGrid(postData);

                        var before = self.postData;
                        if (!_.isEqual(before, postData)) {
                            $('#resetSearch').removeClass('hide');
                        }

                        $("#portrayalListModal").modal("hide");
                    });
                });
            });

            //重置原始条件
            $('#resetBtn').click(function () {
                $('#resetSearch').addClass('hide');
                self.hasInitDialog = false;
                self.renderGrid(self.postData);
            });
        },
        initDialog: function () {
            var self = this;
            var positionId = position.getSelect2Val();
            $("#school, #degree").html('<option value="0">数据加载中</option>');
            $("#ability").html('<div class="msg">数据加载中</div>');
            self.hasInitDialog = true;

            if (self.queryTags) {
                self.renderDialog(self.queryTags);
                return;
            }
            $.post(urls.getImagesQueryTags, {position: positionId}, function (data) {
                if (data && data.length > 0) {
                    self.renderDialog(data);
                    self.queryTags = data;
                } else {
                    $("#ability").html('暂无数据');
                }
            });
        },
        renderDialog: function (data) {
            var self = this;

            //人员标签
            self.renderDialogTag(self.data);

            var school = [], degree = [], abilitySelect = [], abilityScore = [];
            //0:绩效 1:学校类型  2:学历  3:能力素质分值
            $.each(data, function (i, item) {
                switch (item.tagType) {
                    case 1: {
                        school.push(item);
                        break;
                    }
                    case 2: {
                        degree.push(item);
                        break;
                    }
                    case 3: {
                        abilityScore.push(item);
                        break;
                    }
                    case 4: {
                        abilitySelect.push(item);
                        break;
                    }
                }

            });

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
                return -item.tagVal;
            });

            var score = [];
            $.each(abilityScore, function (i, item) {
                score.push('<div class="aly" data-id="' + item.tagVal + '">' + item.tagName + '</div>');
            });
            var scoreHtml = score.join("");

            //能力素质
            var ability = [];
            ability.push('<div class="ability clearfix">');
            ability.push('<div class="aby">');
            ability.push('<i class="add icon-plus-sign"></i>');
            ability.push('<i class="remove icon-minus-sign"></i>');
            ability.push('<i class="eyeshow eye icon-eye-open"></i>');
            ability.push('<i class="eyehide eye icon-eye-close color999 hide"></i>');
            ability.push('<select>');
            ability.push(selectHtml);
            ability.push('</select>');
            ability.push('</div>');
            ability.push('<div class="abyc clearfix">');
            ability.push('<div class="all">不限</div>');
            ability.push('<div class="clearfix">');
            ability.push(scoreHtml);
            ability.push('</div>');
            ability.push('</div>');
            ability.push('</div>');
            self.abilityHtml = ability.join('');
            //绑定数据
            var _sex = $('#performance div[type="11"]').data("tagid");
            var _school = $('#performance div[type="12"]').data("tagid");
            var _degree = $('#performance div[type="13"]').data("tagid");
            $("#sex").val(_.isNull(_sex) ? 0 : _sex);
            $("#school").val(_.isNull(_school) ? 0 : _school);
            $("#degree").val(_.isNull(_degree) ? 0 : _degree);
            self.renderDialogAbility(self.data, abilitySelect);

            self.getNumbers();

            //对话框事件
            self.renderDialogEvent();
        },
        renderDialogTag: function (data) {
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
        renderDialogAbility: function (data, abilitySelect) {
            var self = this;
            var $ability = $("#ability");
            if (abilitySelect.length <= 0) {
                $ability.parent().addClass("hide");
                return;
            }
            $("#ability").parent().removeClass("hide");

            var aby = _.filter(data, function (item) {
                return item.tagType == 1;
            });

            if (aby.length <= 0) {
                $ability.html(self.abilityHtml);
                $ability.find('i.remove').removeClass('remove').css({color: '#999', cursor: 'default'});
                var $eyeshow = $ability.find('i.eyeshow');
                $eyeshow.addClass("hide");
                $ability.find("i.eyehide").removeClass("hide");
                $ability.children('.ability').addClass("disabled");
                $eyeshow.parent().find("select").attr("disabled", "disabled");
                return;
            }
            $("#ability").html('');
            $.each(aby, function (i, item) {
                var $abilityObj = $(self.abilityHtml);
                $abilityObj.find("option[value='" + item.tagId + "']").attr("selected", "selected");
                if (_.isNumber(item.tagScore)) {
                    $abilityObj.find('div.aly[data-id="' + item.tagScore + '"]').addClass('selected');
                } else {
                    $abilityObj.find('div.all').addClass('selected');
                }
                $("#ability").append($abilityObj);

                if (i == 0) {
                    $("#ability").find('i.remove').removeClass('remove').css({color: '#999', cursor: 'default'});
                }
            });
        },
        renderDialogEvent: function () {
            var self = this;
            $("#ability .add").unbind("click").on("click", function () {
                var $abilityObj = $(self.abilityHtml);
                if ($abilityObj.find("select option").length > $("#ability .ability").length) {
                    $("#ability").append($abilityObj);
                    $abilityObj.find('div.all').addClass('selected');
                    self.renderDialogEvent();
                }
                self.getNumbers();
            });
            $("#ability .remove").unbind("click").on("click", function () {
                if ($("#ability .ability").length > 1) {
                    $(this).parents(".ability").remove();
                }
                self.getNumbers();
            });
            $("#ability .eyeshow").unbind("click").on("click", function () {
                $(this).addClass("hide");
                $(this).parents(".ability").find(".eyehide").removeClass("hide");
                $(this).parents(".ability").addClass("disabled");
                $(this).parent().find("select").attr("disabled", "disabled");
                self.getNumbers();
            });
            $("#ability .eyehide").unbind("click").on("click", function () {
                $(this).addClass("hide");
                $(this).parents(".ability").find(".eyeshow").removeClass("hide");
                $(this).parents(".ability").removeClass("disabled");
                $(this).parent().find("select").removeAttr("disabled");
                self.getNumbers();
            });
            $("#ability .abyc .all,#ability .abyc .aly").unbind("click").on("click", function () {
                $(this).parents(".ability").find(".abyc .all,.abyc .aly").removeClass("selected");
                $(this).addClass("selected");
                self.getNumbers();
            });
            //人员标签事件
            $(".ptm .pcontent .content .tag").unbind("click").on("click", function () {
                if ($(this).hasClass("selected")) {
                    $(this).removeClass("selected");
                } else {
                    $(this).addClass("selected");
                }
                self.getNumbers();
            });

            $("#ability select,#sex,#school,#degree").unbind("change").on("change", function () {
                self.getNumbers();
            });
        },
        getSelectOption: function (obj) {
            var option = [];
            $.each(obj, function (i, item) {
                option.push('<option value="' + item.tagId + '">' + item.tagName + '</option>');
            });
            return option.join("");
        },
        getPostData: function () {
            var sex = $("#sex").val() == "0" ? null : $("#sex").val();
            var school = $("#school").val() == "0" ? null : $("#school").val();
            var degree = $("#degree").val() == "0" ? null : $("#degree").val();
            var content = [];
            $("#tag .selected").each(function () {
                content.push($(this).data("id"));
            });
            var contentStr = content.join(',');
            var quality = [];
            $("#ability .ability").each(function () {
                if (!$(this).find(".eyeshow").hasClass("hide")) {
                    var tagId = $(this).find("select").val();
                    var tagVal = null;
                    if (!$(this).find(".abyc .all").hasClass("selected")) {
                        var val = [];
                        $(this).find(".abyc .aly").each(function () {
                            if ($(this).hasClass("selected")) {
                                val.push($(this).data("id"));
                            }
                        });
                        tagVal = val.join(',');
                    }
                    quality.push({k: tagId, v: tagVal == '' ? null : tagVal});
                }
            });
            var qualityStr = JSON.stringify(quality);

            var postData = {
                sex: sex,
                degreeId: degree,
                schoolType: school,
                contentStr: contentStr,
                qualityStr: qualityStr
            };
            return postData;
        },
        getPerfNumbers: function () {
            var self = this;
            var yearNum = $("#yeardefault").text();
            var continueNum = $("#frequencydefault").text();
            var star = $("#stardefault").text();
            var postData = {
                position: self.positionId,
                yearNum: yearNum,
                continueNum: continueNum,
                star: star
            }
            $("#perfNumber").text("...");
            $.post(urls.getPositionPerfEmpCount, postData, function (len) {
                $("#perfNumber").text(len);
            });
        },
        getNumbers: function () {
            var self = this;
            var postData = $.extend({}, self.getPostData());
            $("#number").text("...");
            $.post(urls.getPositionRecommendCount, postData, function (len) {
                $("#number").text(len);
            });
        },
        renderGrid: function (postData) {
            var self = this;
            if (self.jqgrid) {
                self.jqgrid.clearGridData().setGridParam({
                    postData: postData
                }).trigger("reloadGrid");
            } else {
                self.optionGrid.pager = "#performanceGridPage";
                self.optionGrid.postData = postData;
                self.jqgrid = $("#performanceGrid").jqGrid(self.optionGrid);
            }
            self.resizeGrid();
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
            self.resize();
            $.post(urls.getPositionPay, {keyName: $("#txtRemuneration").val(), organId: reqOrgId}, function (data) {
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
            if ($.isArray(data)) {
                var positionId = '', total = 0, empTotal = 0, avg = 0, n = 0, j = 0, array = [], len = data.length;
                $.each(data, function (i, item) {
                    if (item.positionId != positionId) {
                        positionId = item.positionId;
                        total = empTotal = avg = n = j = 0;
                        $.each(data, function (i, item) {
                            if (item.positionId == positionId) {
                                j++;
                                n++;
                                empTotal += item.empTotal;
                                total += item.pay * item.empTotal;
                            }
                        });
                        avg = Tc.formatFloat(total / empTotal);

                        array.push('<tr' + (i + j == len ? ' class="nb"' : '') + '>');
                        array.push('	<td rowspan="' + n + '">' + item.positionName + '</td>');
                        array.push('	<td' + (i == len - 1 ? '' : ' class="nbtd"') + '>' + item.rankName + '</td>');
                        array.push('	<td' + (i == len - 1 ? '' : ' class="nbtd"') + '>' + Tc.formatNumber(item.pay) + '</td>');
                        array.push('	<td rowspan="' + n + '">' + avg + '</td>');
                        array.push('</tr>');
                    } else {
                        array.push('<tr' + (i == len - 1 ? ' class="nb"' : '') + '>');
                        array.push('	<td>' + item.rankName + '</td>');
                        array.push('	<td>' + Tc.formatNumber(item.pay) + '</td>');
                        array.push('</tr>');
                    }
                });
                $("#" + self.gridId + " tbody").html(array.join(''));

                self.resize();
            } else {
                $("#" + self.gridId + " tbody").html('<tr><td colspan="4"><div class="nodata">加载失败</div></td></tr>');
            }
        },
        event: function () {
            var self = this;
            $("#btnRemuneration").unbind("click").click(function () {
                self.init();
            });
        },
        resize: function () {
            $("#remuneration .tt table").css({width: ($("#remuneration .of table").width() + 1) + "px"});
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

    //缩放重置
    $(window).resize(function () {
        $(".leftBody .leftListDiv").each(function () {
            $(this).data("resize", !$(this).hasClass("selectList"));
        });
        resizeChartOrGrid();
    });

    $(window).keydown(function (e) {
        if (e.keyCode == 13) {
            var activeId = document.activeElement.id;
            //假如焦点在文本框上,则获取文本框的值
            if (activeId == 'txtRemuneration') {    //招聘岗位薪酬
                remuneration.init();
                return;
            }
            if (activeId == 'txtTrenchStatistics') {  //招聘渠道
                trenchStatistics.renderGrid();
                return;
            }
        }
    });

    var resizeChartOrGrid = function () {
        var t = pageObj;
        switch ($(".leftBody .selectList").attr("page")) {
            case "page-one": {//页签1
                if (t.tabFirstLoaded) {
                    trenchStatistics.resize();
                    transactionPrompt.resizeGrid();
                    positionFillRateObj.resizeGrid();
                }
                break;
            }
            case "page-two": {//页签2
                if (t.tabSecondLoaded) {
                    performance.resizeGrid();
                    remuneration.resize();
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
                case "page-one": {
                    self.initFirstTab();
                    break;
                }
                case "page-two": {
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