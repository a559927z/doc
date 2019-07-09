/**
 * Created by Administrator on 2016/5/30.
 */
require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge', 'echarts/config',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'organTreeSelector', 'vernierCursor', 'timeLine2', 'searchBox3', 'resize', "jquery-mCustomScrollBar",
    'messenger', 'selection', 'organTreeSelector', 'datetimepicker', 'multipleselect'], function ($, echarts) {

    var ecConfig = require('echarts/config');
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var urls = {
        getPopulations:webRoot+'/common/getPopulations.do', //人群
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
        structureBudgetAnalyse: webRoot + '/talentStructure/getBudgetAnalyse.do', //编制分析
        structureGetConfigWarnVal: webRoot + '/talentStructure/getConfigWarnVal.do',  //加载使用率
        structureSetConfigWarnVal: webRoot + '/talentStructure/setConfigWarnVal.do',  //保存使用率

        getOrganTree: webRoot + '/organ/queryOrganTree.do',

        getAbilityEmpCount:webRoot+"/talentStructure/getAbilityEmpCount.do", //管理者员工分布
        getAbilityEmpList:webRoot+"/talentStructure/getAbilityEmpList.do", //管理者员工分布 详细列表
        getAbilityCurtEmpCount:webRoot+"/talentStructure/getAbilityCurtEmpCount.do", //职级分布
        getAbilityCurtEmpList:webRoot+"/talentStructure/getAbilityCurtEmpList.do", //职级分布 详细列表
        getSeqEmpCount:webRoot+"/talentStructure/getSeqEmpCount.do", //职位序列分布
        getAbEmpCountBarBySeqId: webRoot + '/talentStructure/getAbEmpCountBarBySeqId.do',  //职位序列分布 职级分布,
        getAbEmpCountBarBySeqIdList: webRoot + '/talentStructure/getAbEmpCountBarBySeqIdList.do',  //职位序列分布 详细列表,
        getOrganEmpCount:webRoot+'/talentStructure/getOrganEmpCount.do', //下级组织人员分布
        getOrganEmpList:webRoot+'/talentStructure/getOrganEmpList.do', //下级组织人员分布 详细列表
        getWorkplaceEmpCount:webRoot+'/talentStructure/getWorkplaceEmpCount.do', //工作地分布
        getWorkplaceEmpList:webRoot+'/talentStructure/getWorkplaceEmpList.do', //工作地分布 详细列表

        getSexData: webRoot + '/talentStructure/getSexData.do', //男女占比
        getMarryStatusData: webRoot + '/talentStructure/getMarryStatusData.do', //婚姻状况
        getBloodData: webRoot + '/talentStructure/getBloodData.do', //血型
        getStarData: webRoot + '/talentStructure/getStarData.do', //星座
        getPersonalityData: webRoot + '/talentStructure/getPersonalityData.do', //性格

        getSexDataList: webRoot + '/talentStructure/getSexDataList.do', //男女占比 详细列表
        getMarryStatusDataList: webRoot + '/talentStructure/getMarryStatusDataList.do', //婚姻状况 详细列表
        getBloodDataList: webRoot + '/talentStructure/getBloodDataList.do', //血型 详细列表
        getStarDataList: webRoot + '/talentStructure/getStarDataList.do', //星座 详细列表
        getPersonalityDataList: webRoot + '/talentStructure/getPersonalityDataList.do' //性格 详细列表
    };
    var minDate=$("#minDay").val();
    var maxDate=$("#maxDay").val();

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

    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 2
        });
    };

    $(".ct-mCustomScrollBar").mCustomScrollbar({
        axis: "yx",
        scrollButtons: {enable: true},
        scrollbarPosition: "outside",
        theme: "minimal-dark"
    });

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        $(".rightSetUpLeft").click();
        reqOrgId = organId;
        pageObj.reload();
    };

    $(".index-common-title-tooltip").attr("title", $(".index-common-title-tooltip").text());

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
                title: '管理建议与备忘',
                titleSuffix: '条未读',
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
        }
    }
    timeLineObj.init(reqOrgId);

    /*
     显示更多备忘
     */
    $("#memo-body-div").mouseenter(function () {
        $("#memo-big-list-more").show();
    });
    $("#memo-body-div").mouseleave(function () {
        $("#memo-big-list-more").hide();
    });

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

            $(".mCSB_container").css({"left": "0px"});
            $(this).parents(".SetUpBody").find(".ct-mCustomScrollBar").mCustomScrollbar("scrollTo", "left");
        } else {

            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
        }


        var obj=$(this).parents(".SetUpBody");
        if(obj.find(".managerOpt").length>0){
            objManagersEmployee.resize();
        }else if(obj.find(".rankOpt").length>0){
            objRank.resize();
        }else if(obj.find(".positionOpt").length>0){
            objPositionSequence.resize();
            objPositionRank.resize();
        }else if(obj.find(".organDistributionOpt").length>0){
            organDistribution.resize();
        }else if(obj.find(".workLocationOpt").length>0){
            workLocation.resize();
        }
    });

    /*********************************************************
     *********************************************************
     * 页签一：团队结构
     * *******************************************************
     *********************************************************/

    /*
     设置编制使用率
     */
    $(".LSLSetUp").click(function () {
        $(".zd-window").css("display", "table");
        $(".shade").show();
        $.post(urls.structureGetConfigWarnVal, {}, function (data) {
            $("#zdWindowNormal").val(data.normal * 100);
            $("#zdWindowRisk").val(data.risk * 100);
        });
    });
    $("#btnSaveRate").click(function () {
        var normal = $.trim($("#zdWindowNormal").val());
        var risk = $.trim($("#zdWindowRisk").val());
        if (normal == "" || !$.isNumeric(normal) || parseFloat(normal) < 0) {
            $("#zdWindowNormal").focus();
            return false;
        }
        else if (risk == "" || !$.isNumeric(risk) || parseFloat(risk) < 0) {
            $("#zdWindowRisk").focus();
            return false;
        }
        else if (parseFloat(normal) >= parseFloat(risk)) {
            $(".zd-window .errmsg").remove();
            $(".zd-window .drag-bottom").prepend("<span class='errmsg'>正常值必须小于风险值！</span>").find(".errmsg").fadeOut(5000, function () {
                $(".zd-window .errmsg").remove();
            });
            $("#zdWindowNormal").focus();
            return false;
        }
        else {
            $.post(urls.structureSetConfigWarnVal, {
                "normal": parseFloat(normal) / 100,
                "risk": parseFloat(risk) / 100
            }, function (data) {
                $(".zd-window").css("display", "none");
                $(".shade").hide();
                if (data.type) {
                    objBudgetAnalyse.init();
                } else {
                    showErrMsg(data.msg)
                }
            });
        }
    });

    $(".closeIcon,.cancelDragBtn").click(function () {
        $(".ct-drag").hide();
        $(".shade").hide();
    });

    /*
     * 编制使用率、编制分析
     * */
    var objBudgetAnalyse = {
        chart: echarts.init(document.getElementById('structureRateChart')),
        data: {
            value: 0,
            greenValue: 0.95,
            yellowVlue: 1
        },
        textArea: $("#structureRateText"),
        color: ["green", "yellow", "red"],
        text: ["招兵买马，弹药充足！", "人手富足，大展宏图！", "余粮有限，注意节制！"],
        //图表Option
        option: {
            toolbox: {
                show: false
            },
            series: [
                {
                    name: "编制",
                    type: "gauge",
                    data: [
                        {
                            value: 0,
                            name: "使用率"
                        }
                    ],
                    min: 0,
                    max: 100,
                    axisLine: {
                        lineStyle: {
                            color: [[0.95, "rgb(106, 175, 43)"], [1, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]],
                            width: 10
                        },
                        show: true
                    },
                    title: {
                        show: false
                    },
                    detail: {
                        show: false
                    },
                    splitNumber: 5,
                    pointer: {
                        length: "80%",
                        width: 5
                    },
                    radius: "90%",
                    center: ["50%", "55%"],
                    axisTick: {
                        show: true,
                        splitNumber: 2,
                        length: 4
                    },
                    splitLine: {
                        lineStyle: {
                            width: 1
                        },
                        show: true,
                        length: 10
                    },
                    clickable:false
                }
            ],
            tooltip: {
                show: false
            }
        },
        init: function () {
            var self = this;
            self.request();
        },
        request: function () {
            var self = this;

            $.post(urls.structureBudgetAnalyse, {"organId": reqOrgId}, function (data) {
                if (data.hasBudgetPer) {
                    $("#structureRateNoData").addClass("hide");
                    $("#structureRateChart,#structureRateText").show();
                    self.data.value = (data.budgetPer * 100).toFixed(2);
                    self.data.greenValue = data.normal * 100;
                    self.data.yellowVlue = data.risk * 100;
                    self.renderChart();
                } else {
                    $("#structureRateChart,#structureRateText").hide();
                    $("#structureRateNoData").removeClass("hide");
                }
                $("#talentStructureTable .number").text(data.hasBudgetPer ? data.number : "-");
                $("#talentStructureTable .empCount").text(data.empCount);
                $("#talentStructureTable .usableEmpCount").text(data.hasBudgetPer ? data.usableEmpCount : "-");
            });
        },
        renderChart: function () {
            var self = this;
            //图表
            var maxValue = self.data.value > 100 ? parseInt(self.data.value / 10) * 10 + 10 : 100;
            self.option.series[0].data[0].value = self.data.value;
            self.option.series[0].max = maxValue;
            var g = self.data.greenValue / maxValue;
            var y = self.data.yellowVlue / maxValue;
            if (g > 1) {
                self.data.greenValue = self.data.yellowVlue = 1;
            } else {
                self.data.greenValue = g;
                self.data.yellowVlue = y > 1 ? 1 : y;
            }
            self.option.series[0].axisLine.lineStyle.color = [[self.data.greenValue, "rgb(106, 175, 43)"], [self.data.yellowVlue, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]];
            self.chart.clear();
            self.chart.setOption(self.option);
            self.chart.refresh();

            //文字说明
            var index = 2;
            if (self.data.value <= self.data.greenValue * 100) {
                index = 0;
            } else if (self.data.value <= self.data.yellowVlue * 100) {
                index = 1;
            }
            self.textArea.removeClass(self.color[0]).removeClass(self.color[1]).removeClass(self.color[2]).addClass(self.color[index]);
            self.textArea.find(".rate").text(self.data.value + "%");
            self.textArea.find(".text").text(self.text[index]);
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    };

    $.fn.extend({
        loadingData:function(){
            $(this).find(">div").addClass("hide").eq(0).removeClass("hide").text("数据加载中");
            $(this).find(">div").eq(2).removeClass("hide").text("数据加载中");
        },
        showData:function(){
            $(this).find(">div").addClass("hide").eq(1).removeClass("hide");
            $(this).find(">div").eq(3).removeClass("hide");
        },
        noData:function(){
            $(this).find(">div").addClass("hide").eq(0).removeClass("hide").text("暂无数据");
            $(this).find(">div").eq(2).removeClass("hide").text("暂无数据");
        }
    });

    //饼图颜色
    var colorPie = ["#0b98e0", "#00bda9", "#4573a7", "#92c3d4", "#de6e1b", "#ff0084", "#af00e1", "#8d55f6", "#6a5888", "#2340f3"];
    var optionPie = {
        tooltip: {
            show: false
        },
        toolbox: {
            show: false
        },
        calculable: false,
        series: [
            {
                type: "pie",
                radius: "70%",
                center: ["50%", "50%"],
                data: [],
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            }
        ],
        color: colorPie
    };

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

    //人员明细弹出框
    var dialogObj={
        obj:$("#detailListDialog"),
        jqGrid:$("#detailList"),
        optionGrid:{
            url: null,
            datatype: "json",
            postData: {},
            mtype: 'POST',
            altRows: true,//设置表格行的不同底色
            autowidth: true,
            colNames: ['姓名','性别','所属组织','主序列','子序列','职级','工作地'],
            colModel: [
                {name: 'userNameCh', sortable: false, align: 'left'},
                {name: 'sex', sortable: false, align: 'left', formatter:function(cellvalue, options, rowObject){
                    if(cellvalue=='m'){
                        return '男';
                    }else if(cellvalue=='w'){
                        return '女';
                    }else{
                        return '-';
                    }
                }},
                {name: 'organizationName', sortable: false, align: 'left'},
                {name: 'sequenceName', sortable: false, align: 'left'},
                {name: 'sequenceSubName', sortable: false, align: 'left'},
                {name: 'abilityLvName', sortable: false, align: 'left'},
                {name: 'workPlace', sortable: false, align: 'left'},
            ],
            rownumbers: true,
            rownumWidth: 38,
            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: "#detailPage",
            loadComplete: function (xhr) {
                updatePagerIcons();
            }
        },
        selectedAll:false,
        data:null,
        jq:null,
        init:function(){
            var self=this;

            self.render();
            $.post(urls.getPopulations, {}, function(data){
                self.data=data;
                self.renderCrowd();
            });
            self.event();
        },
        render:function(){
            //组织架构
            var orgName=win.$(".dropDownValue").text();
            $("#detailListcontent .dropDownValue").text(orgName);
            $("#dtOrg").data("id",reqOrgId).text(orgName).organTreeSelector({
                showSelectBtn: false,
                multiple : false,
                value : {
                    'id' : reqOrgId,
                    'text' : orgName
                },
                onSelect : function(ids, texts) {
                    $("#dtOrg").data("id",ids).text(orgName);
                }
            });
            //日期
            $("#dtTime").datetimepicker({
                format:'yyyy-mm-dd',
                startDate:minDate,
                endDate:maxDate,
                autoclose:true,
                startView:2,
                minView:2,
                todayHighlight:true,
                language:'cn'
            });
        },
        renderCrowd:function(){
            var self=this;
            var data=self.data;

            var crowd=[];
            crowd.push('<option value="0">不限</option>');
            $.each(data, function(i, item){
                crowd.push('<option value="'+item.k+'">'+item.v+'</option>');
            });
            $("#dtCrowd").html(crowd.join(''));

            $("#dtCrowd").multipleSelect({
                width:135,
                selectAll:false,
                minimumCountSelected:1000,
                onOpen: function () {
                    var $obj = $(".ms-drop ul");
                    var t=dialogObj;
                    t.selectedAll = $obj.find("li").first().find("input").prop("checked");
                },
                onClick: function () {
                    var $obj = $(".ms-drop ul");
                    var t=dialogObj;
                    var sa = $obj.find("li").first().find("input").prop("checked");
                    var len=$obj.find("li.selected input").length-(sa?1:0);
                    var clickAll=false;
                    if(t.selectedAll!=sa||len==0){
                        clickAll=true;
                    }

                    if(clickAll){
                        t.selectedAll=true;
                        $("#dtCrowd").multipleSelect('setSelects', [0]);
                    }else{
                        t.selectedAll=false;
                        var ck=[];
                        $obj.find("li.selected input").each(function(i, item){
                            if($(this).val()!="0"){
                                ck.push($(this).val());
                            }
                        });
                        $("#dtCrowd").multipleSelect('setSelects', ck);
                    }
                }
            });
        },
        event:function(){
            var self=this;
            $("#dtSearch").on("click", function(){
                var orgId=$("#dtOrg").data("id");
                var day=$("#dtTime").val();
                var c=$('#dtCrowd').multipleSelect('getSelects').join(',');
                var crowd=c=="0"?'':c;
                var dim=$("#dtDim").val();
                var seqId=objPositionSequence.seqId;
                self.renderGrid({
                    organId:orgId,
                    day:day,
                    crowd:crowd,
                    id:dim,
                    seqId:seqId
                });
            });
        },
        setValue:function(obj){
            var orgName=win.$(".dropDownValue").text();
            var obj= $.extend({}, {orgId:reqOrgId, orgName:orgName}, obj);
            //组织架构
            $("#detailListcontent .dropDownValue").text(obj.orgName);
            $("#dtOrg").data("id",obj.orgId).text(obj.orgName);
            //时间
            $("#dtTime").val(obj.day);
            //维度
            $("#dtDimName").text(obj.dimName+'：');
            $("#dtDim").html(obj.dim.join('')).val(obj.dimSelected);
            //人群
            if(obj.crowd!=''){
                var cs=obj.crowd.split(',');
                $("#dtCrowd").multipleSelect('setSelects', cs);
            }
        },
        renderGrid:function(pdata, option){
            var self=this;
            var h=$(window).height();
            $("#detailListcontent").css({height:h-120+"px"});
            var height=h-263;
            if (self.jq) {
                var op= $.extend({}, {postData: pdata}, option?option:{});
                self.jq.clearGridData().setGridParam(op).trigger("reloadGrid").setGridHeight(height);
            } else {
                if(option) {
                    self.optionGrid = $.extend({}, self.optionGrid, option);
                }

                self.optionGrid.height=height;
                self.optionGrid.postData = pdata;
                self.jq = self.jqGrid.jqGrid(self.optionGrid);
            }
        }
    }
    dialogObj.init();


    /*
     * 管理者员工分布
     * */
    var objManagersEmployee = {
        chart: null,
        option: optionPie,
        data:null,
        chartId:'managerChart',
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#managerSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".managerOpt").loadingData();
            $.post(urls.getAbilityEmpCount, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".managerOpt").showData();
                    self.data = data;
                    self.renderPie();
                    self.renderGrid();
                }else{
                    $(".managerOpt").noData();
                }
            });
        },
        renderPie:function(){
            var self=this;
            var data=self.data.abilityEmpCountPie;
            if (data == undefined) {
                console.info("管理者员工分布出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var manager = 0, employee = 0, i = 0;
                $.each(data, function (n, v) {
                    if (n == "员工") {
                        employee += v;
                    } else {
                        manager += v;
                    }
                    if (v != 0) {
                        lis.push({"name": n, "value": v});
                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                        i++;
                    }
                });

                if(self.chart){
                    self.chart.clear();
                }
                self.chart=initEChart(self.chartId);

                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();

                $("#managerText").removeClass("hide").find(".legend").html(legend.join(""));
                $("#managerText table");
                var rate = manager + ':' + employee;
                if (manager != 0 && employee != 0) {
                    rate = "1:" + (employee / manager).toFixed(1);
                }
                $("#managerText .proportion").text(rate);


                self.click(data);
            }
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.chart.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.name;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(n, v){
                        dim.push('<option value="'+n+'">'+n+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'管理层级',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getAbilityEmpList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        renderGrid:function(){
            var self=this;

            //数据表
            var dataTable = self.data.abilityEmpCount ? self.data.abilityEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    if (y !== 0)
                        names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var managerTotal = 0, employeeTotal = 0;
            var objTotal = {'下级组织': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                var manager = 0, employee = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    if (y == '员工') {
                        employee = num;
                        employeeTotal += num;
                    } else {
                        manager += num;
                        managerTotal += num;
                        obj.push(num);
                        objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                    }
                });
                obj.push(manager);
                obj.push(employee);
                var rate = "";
                if (manager > 0 && employee > 0) {
                    rate = "1:" + (employee / manager).toFixed(1);
                } else if (manager == 0 || employee == 0) {
                    rate = manager + ":" + employee;
                }
                obj.push(rate);
                lists.push(obj);
            });
            objTotal['管理者合计'] = managerTotal;
            objTotal['员工'] = employeeTotal;
            var rate = "";
            if (managerTotal > 0 && employeeTotal > 0) {
                rate = "1:" + (employeeTotal / managerTotal).toFixed(1);
            } else if (managerTotal == 0 || employeeTotal == 0) {
                rate = managerTotal + ":" + employeeTotal;
            }
            objTotal['管理者员工比例'] = rate;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#managerTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#managerTable tbody").html(rowbody.join(''));

            //样式
            $("#managerTable tbody tr").first().addClass("total");
            $("#managerTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    };

    /*
     * 职级分布
     * */
    var objRank = {
        chart: null,
        option: optionPie,
        chartId:"rankChart",
        data:null,
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#rankSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".rankOpt").loadingData();
            $.post(urls.getAbilityCurtEmpCount, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".rankOpt").showData();
                    self.data = data;
                    self.renderPie();
                    self.renderGrid();
                }else{
                    $(".rankOpt").noData();
                }
            });
        },
        renderPie:function(){
            var self=this;
            var data=self.data.abilityCurtEmpCountPie;

            if (data == undefined) {
                console.info("职级分布出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function (n, v) {
                    if (v != 0) {
                        lis.push({"name": n, "value": v});
                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                        i++;
                    }
                });
                if(self.chart){
                    self.chart.clear();
                }
                self.chart=initEChart(self.chartId);

                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();

                $("#rankText").removeClass("hide").find(".legend").html(legend.join(""));

                self.click(data);
            }
        },
        renderGrid:function(){
            var self=this;

            //数据表
            var dataTable = self.data.abilityCurtEmpCount ? self.data.abilityCurtEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    if (y != 0)
                        names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var totals = 0;
            var objTotal = {'下级组织': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [];
                var total = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    total += num;
                    totals += num;
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                obj.push(total);
                lists.push(obj);
            });
            objTotal['合计'] = totals;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#rankTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#rankTable tbody").html(rowbody.join(''));

            //样式
            $("#rankTable tbody tr").first().addClass("total");
            $("#rankTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.chart.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.name;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(n, v){
                        dim.push('<option value="'+n+'">'+n+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'职级',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getAbilityCurtEmpList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    };

    var isReturn = false;
    /*
     * 职位序列分布 序列分布
     * */
    var objPositionSequence = {
        chart: null,
        chartId:'positionSequenceChart',
        option: {
            title: {
                text: "职位序列分布",
                x: "center",
                y: 20
            },
            tooltip: {
                show: false
            },
            toolbox: {
                show: false,
                feature: {
                    dataView: {
                        readOnly: true
                    },
                    magicType: {
                        type: ["line", "bar"],
                        show: false
                    }
                }
            },
            xAxis: [
                {
                    type: "value",
                    boundaryGap: [0, 0.01],
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: "rgb(169, 170, 170)"
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    name: "职位序列分布",
                    type: "bar",
                    data: [],
                    tooltip: {
                        show: false
                    },
                    barMaxWidth: 50,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: function (i) {
                                    var rate = i.seriesName > 0 ? (i.value * 100 / i.seriesName).toFixed(2) : 0;
                                    return i.value + "人，" + rate + "%";
                                },
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    }
                }
            ],
            grid: {
                borderColor: "#ffffff",
                x2: 120
            },
            color: ["#3285C7"]
        },
        data:null,
        day:maxDate,
        crowd:'',
        seqId:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#positionSequenceSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".positionOpt").loadingData();
            $.post(urls.getSeqEmpCount, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".positionOpt").showData();
                    self.data = data;
                    self.renderBar(self.data.seqEmpCountBar);
                    objPositionRank.init(self.data.abilityCurtEmpCountPie);
                    self.renderGrid(self.data.seqEmpCount);
                }else{
                    $(".positionOpt").noData();
                }
            });
        },
        renderBar:function(data){
            var self=this;

            if (data == undefined) {
                console.info("职位序列分布序列分布出错了！");
            } else {
                var yaxisData = [], seriesData = [];
                var total = 0;
                $.each(data, function (n, v) {
                    var cateName = n;
                    var seqId = "";
                    var arr = n.split(',');
                    if (arr.length > 1) {
                        cateName = arr[1];
                        seqId = arr[0];
                    }
                    seriesData.push({"value": v, "seqId": seqId, "cateName": cateName});
                    total += v;
                });
                seriesData = _.sortBy(seriesData, "value");
                $.each(seriesData, function (i, item) {
                    yaxisData.push(item.cateName);
                });

                if(self.chart){
                    self.chart.clear();
                }
                self.chart=initEChart(self.chartId);

                self.option.series[0].name = total;
                self.option.yAxis[0].data = yaxisData;
                self.option.series[0].data = seriesData;

                if (self.chart != null)
                    self.chart.dispose();
                self.chart = echarts.init(document.getElementById('positionSequenceChart'));

                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();

                self.chart.on(ecConfig.EVENT.CLICK, self.eConsole);
            }
        },
        renderGrid:function(data){
            var self=this;

            var names = [];
            $.each(data, function (n, v) {
                $.each(v, function (x, y) {
                    names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var totals = 0;
            var objTotal = {'职级': '合计'};
            $.each(data, function (n, v) {
                var obj = [];
                var total = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    total += num;
                    totals += num;
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                obj.push(total);
                lists.push(obj);
            });
            objTotal['合计'] = totals;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#positionSequenceTable thead").html(rowheader.join(''));

            //body
            var rowbody = [];
            $.each(lists, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#positionSequenceTable tbody").html(rowbody.join(''));

            $("#positionSequenceTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        //点击事件
        eConsole: function (param) {
            var self = objPositionSequence;
            if (!isReturn) {//钻取数据
                isReturn = true;
                var seqId = param.data.seqId;
                if (seqId == "") {
                    showErrMsg("其他管理序列不能钻取！");
                } else {
                    var obj = {};
                    var name = (param.data.seqId == "" ? "" : param.data.seqId + ",") + param.name;
                    obj[name] = param.value;
                    self.renderBar(obj);

                    $.post(urls.getAbEmpCountBarBySeqId, { "organId": reqOrgId, "seqId": seqId, day:self.day, crowd:self.crowd }, function (data) {
                        self.seqId=seqId;
                        objPositionRank.init(data);
                    });
                }
            } else {//返回上一级
                isReturn = false;
                self.renderBar(self.data.seqEmpCountBar);
                objPositionRank.init(self.data.abilityCurtEmpCountPie);
            }
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    }

    /*
     * 职位序列分布 职级分布
     * */
    var objPositionRank = {
        chart: null,
        chartId:'positionRankChart',
        option: {
            title: {
                text: "职级分布",
                x: "center",
                y: 20,
                textAlign: "center"
            },
            tooltip: {
                show: false
            },
            toolbox: {
                show: false
            },
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "#000000",
                            fontSize: 13
                        },
                        interval: 0
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLine: {
                        show: false
                    },
                    axisLabel: {
                        formatter: "{value}%",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: "dashed",
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            series: [
                {
                    name: "职级",
                    type: "bar",
                    data: [],
                    barMaxWidth: 50,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: '{c}%',
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    }
                }
            ],
            color: ["#3285C7"],
            grid: {
                borderColor: "#ffffff",
                y: 80,
                x2: 30
            }
        },
        data:null,
        init: function (data) {
            if (data == undefined) {
                console.info("职位序列分布职级分布出错了！");
            } else {
                var self = this;
                var xaxisData = [], seriesData = [];
                var total = 0;
                $.each(data, function (n, v) {
                    total += v;
                });
                $.each(data, function (n, v) {
                    xaxisData.push(n + "\n" + v + "人");
                    seriesData.push((v * 100 / total).toFixed(2));
                });

                if(self.chart){
                    self.chart.clear();
                }
                self.chart=initEChart(self.chartId);

                self.option.series[0].name = total;
                self.option.xAxis[0].data = xaxisData;
                self.option.series[0].data = seriesData;

                self.chart.setOption(self.option);
                self.chart.refresh();

                self.click(data);
            }
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;

            self.data=_.pairs(dimData);
            //点击图表
            self.chart.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=self.data[param.dataIndex][0];
                var day=objPositionSequence.day;
                var crowd=objPositionSequence.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(n, v){
                        dim.push('<option value="'+n+'">'+n+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'职级',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var seqId=objPositionSequence.seqId;
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected, seqId:seqId};
                    var option={
                        url: isReturn?urls.getAbEmpCountBarBySeqIdList:urls.getAbilityCurtEmpList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    }

    /*
     * 组织分布
     * */
    var organDistribution = {
        chart: null,
        chartId:'organDistributionChart',
        option: {
            tooltip: {
                trigger: "axis",
                axisPointer: {type: 'none'},
                formatter: function (a, b, c) {
                    return a[0].name + "：" + a[0].value;
                }
            },
            toolbox: {
                show: false,
                feature: {
                    mark: {
                        show: true
                    },
                    dataView: {
                        show: true,
                        readOnly: true
                    },
                    magicType: {
                        show: false,
                        type: ["line", "bar"]
                    },
                    restore: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }
                }
            },
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "#000000",
                            fontSize: 13
                        }
                    }
                }
            ],
            yAxis: [
                {
                    name:'(人数)',
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle:{
                            width:0,
                            color:'#cccccc'
                        }
                    },
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: "solid",
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            series: [
                {
                    type: "bar",
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    },
                    barMaxWidth: 50
                }
            ],
            color: ["#3285C7"],
            grid: {
                borderColor: "#ffffff",
                x: 55,
                y: 25,
                x2: 15,
                borderWidth: 0
            },
            dataZoom: {
                show: true,
                realtime: true,
                height: 20,
                start: 0,
                end: 30,
                showDetail: false
            }
        },
        data:null,
        day:maxDate,
        crowd:'',
        dataOrgTree:null,
        parentOrgId:'',
        parentOrgName:'',
        init: function () {
            var self=this;
            self.parentOrgId=reqOrgId;
            self.parentOrgName=win.$(".dropDownValue").text();
            self.request(self.day, self.crowd);

            $("#organDistributionSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".organDistributionOpt").loadingData();
            $.post(urls.getOrganEmpCount, {organId: self.parentOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".organDistributionOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".organDistributionOpt").noData();
                }
            });
        },
        render:function(){
            var self=this;
            var data=self.data.organEmpCount;

            if (data == undefined) {
                console.info("组织分布出错了！");
            } else {
                var xAxisData = [], seriesData = [];
                var tem = [];
                $.each(data, function (n, v) {
                    tem.push({"name": n, "value": -v});
                });
                tem = _.sortBy(tem, "value");
                $.each(tem, function (i, item) {
                    xAxisData.push(item.name);
                    seriesData.push(-item.value);
                });
                if (xAxisData.length > 0) {
                    var num = 3;//3个以上显示滚动条
                    if (xAxisData.length > num) {
                        self.option.dataZoom = {
                            show: true,
                            realtime: true,
                            height: 20,
                            start: 0,
                            end: 30,
                            showDetail: false
                        };
                    } else {
                        self.option.dataZoom = {}
                    }

                    if(self.chart) {
                        self.chart.clear();
                    }
                    self.chart=initEChart(self.chartId);

                    self.option.xAxis[0].data = xAxisData;
                    self.option.series[0].data = seriesData;
                    self.chart.setOption(self.option);
                    self.chart.refresh();
                } else {
                    if (self.chart)
                        self.chart.dispose();
                    $("#organDistributionChart").html("<div class='loadingmessage'>暂无数据</div>");
                }

                self.click(data);
            }
        },
        tree:function(callback){
            var self=this;
            $.post(urls.getOrganTree, {}, function(data) {
                self.dataOrgTree=data;
                callback.call(new Object());
            });
        },
        hasChild:function(orgName, callback){
            var self=this;
            if(!self.dataOrgTree){
                self.tree(function(){
                    self.hasChildCalculate(orgName, callback);
                });
            }else{
                self.hasChildCalculate(orgName, callback);
            }
        },
        hasChildCalculate:function(name, callback){
            var self=this;
            var parentId=self.parentOrgId;
            var data=self.dataOrgTree;
            var orgId='', orgName='';
            $.each(data, function(i, item){
                if(item.parentId==parentId && item.name==name){
                    orgId=item.id;
                    orgName=item.name;
                }
            });
            var hasChild=false;
            $.each(data, function(i, item){
                if(item.parentId==orgId){
                    hasChild=true;
                }
            });

            callback.call(new Object(), hasChild, orgId, orgName);
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.chart.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.name;
                var day=self.day;
                var crowd=self.crowd;
                self.hasChild(dimSelected, function(hasChild, orgId, orgName){
                    if(hasChild){
                        self.parentOrgId=orgId;
                        self.parentOrgName=orgName;
                        self.request(self.day, self.crowd);
                    }else{
                        t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                            //设置搜索栏值
                            var dim = [];
                            $.each(dimData, function (n, v) {
                                dim.push('<option value="' + n + '">' + n + '</option>');
                            });
                            t.setValue({
                                day: day,
                                dim: dim,
                                dimName: '下级组织',
                                dimSelected: dimSelected,
                                crowd: crowd,
                                orgId:self.parentOrgId,
                                orgName:self.parentOrgName
                            });

                            //jqGrid
                            var pdata = {organId: self.parentOrgId, day: day, crowd: crowd, id: dimSelected};
                            var option = {
                                url: urls.getOrganEmpList
                            };
                            t.renderGrid(pdata, option);
                        });
                    }
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    };

    /**
     * 工作地点分布
     */
    var workLocation = {
        chart: null,
        chartId:'workLocationChart',
        day:maxDate,
        crowd:'',
        option: {
            tooltip: {
                trigger: "axis",
                axisPointer: {type: 'none'},
                formatter: function (a, b, c) {
                    return a[0].name + "：" + a[0].value;
                }
            },
            toolbox: {
                show: false
            },
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "#000000",
                            fontSize: 13
                        }
                    }
                }
            ],
            yAxis: [
                {
                    name:'(人数)',
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle:{
                            width:0,
                            color:'#cccccc'
                        }
                    },
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: "solid",
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            series: [
                {
                    type: "bar",
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    },
                    barMaxWidth: 50
                }
            ],
            color: ["#3285C7"],
            grid: {
                borderColor: "#ffffff",
                x: 55,
                y: 25,
                x2: 15,
                borderWidth: 0
            }
        },
        data:null,
        init: function () {
            var self=this;
            self.reqOrgId=reqOrgId;
            self.request(self.day, self.crowd);

            $("#workLocationSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".workLocationOpt").loadingData();
            $.post(urls.getWorkplaceEmpCount, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".workLocationOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".workLocationOpt").noData();
                }
            });
        },
        render:function(){
            var self=this;
            var data=self.data.workplaceEmpCount;

            if (data == undefined) {
                console.info("工作地点分布出错了！");
            } else {
                var lis = [], xAxisData = [], seriesData = [];
                $.each(data, function (n, v) {
                    lis.push({"name": n, "value": -v});
                });
                lis = _.sortBy(lis, "value");
                $.each(lis, function (i, item) {
                    xAxisData.push(item.name);
                    seriesData.push(-item.value);
                })

                if(self.chart) {
                    self.chart.clear();
                }
                self.chart=initEChart(self.chartId);

                self.option.xAxis[0].data = xAxisData;
                self.option.series[0].data = seriesData;
                self.chart.setOption(self.option);
                self.chart.refresh();

                self.click(data);
            }
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.chart.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.name;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(n, v){
                        dim.push('<option value="'+n+'">'+n+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'工作地点',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getWorkplaceEmpList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.chart){
                self.chart.resize();
            }
        }
    };


    /*********************************************************
     *********************************************************
     * 页签二：团队结构
     *********************************************************
     *********************************************************/
    var chartWidth = $("#constellatory").parent().width();
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
    var legendReset = function (data) {
        var array = [];
        $.each(data, function (i, item) {
            array.push({name: item, icon: 'circle', width: '5px'})
        });
        return array;
    }

    //初始化echart
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }
    var piePosition = ['45%', '50%'];
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
    var getItemStyle = function (showEmphasis) {
        var style = {
            normal: {
                label: {
                    show: true,
                    //textStyle: {color: "#555555"},
                    formatter: function (a,b,c,d) {
                        var total=0;
                        $.each(a.series.data, function(i, item){
                            total+=parseInt(item.value);

                        });
                        return a.name + (chartWidth > 450 ? '：' : '\n') + formatPieData(total, a.value);
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

    //下级组织本月加班统计
    var overtime = {
        chart: null,
        chartPersonnel:null,
        isHideChart:true,
        option: {
            tooltip: {
                trigger: "axis",
                axisPointer: {type: 'none'},
                formatter: function (a, b, c) {
                    return a[0].name + "：" + a[0].value;
                }
            },
            toolbox: {
                show: false
            },
            xAxis: [
                {
                    type: "category",
                    data: [],
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: "#000000",
                            fontSize: 13
                        }
                    }
                }
            ],
            yAxis: [
                {
                    name:'小时',
                    type: "value",
                    axisLine: {
                        show: true,
                        lineStyle:{
                            width:0,
                            color:'#999'
                        }
                    },
                    axisLabel: {
                        formatter: "{value}",
                        textStyle: {
                            color: "rgb(0, 0, 0)",
                            fontSize: 13
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: "solid",
                            color: "rgb(204, 204, 204)"
                        }
                    }
                }
            ],
            series: [
                {
                    type: "bar",
                    data: [],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: "#000000"
                                }
                            }
                        }
                    },
                    barMaxWidth: 50
                }
            ],
            color: ["#3285C7"],
            grid: {
                borderColor: "#ffffff",
                x: 55,
                y: 25,
                x2: 15,
                borderWidth: 0
            },
            dataZoom: {
                show: true,
                realtime: true,
                height: 20,
                start: 0,
                end: 30,
                showDetail: false
            }
        },
        optionPersonnel:{
            tooltip: {
                trigger: "axis",
                show: true,
                axisPointer: {
                    lineStyle: {
                        width: 1,
                        color: "rgb(225, 225, 225)"
                    }
                },
                formatter: "{b}<br>{a}：{c}h"
            },
            toolbox: {
                show: false,
                feature: {
                    dataView: {
                        readOnly: true
                    },
                    magicType: {
                        type: ["line", "bar"],
                        show: false
                    }
                }
            },
            xAxis: [
                {
                    type: "category",
                    boundaryGap: true,
                    data: ["10月29日", "10月30日", "10月31日"],
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        },
                        length: 3
                    },
                    axisLine: {
                        lineStyle: {
                            width: 1,
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    axisLabel: {
                        rotate: 30
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgb(204, 204, 204)",
                            width: 1
                        }
                    },
                    axisTick: {
                        length: 3,
                        show: true,
                        lineStyle: {
                            color: "rgb(204, 204, 204)"
                        }
                    },
                    axisLabel: {
                        formatter: "{value}h"
                    }
                }
            ],
            series: [
                {
                    name: "加班时长",
                    type: "line",
                    data: [4, 8, 6],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        }
                    }
                }
            ],
            calculable: false,
            grid: {
                borderWidth: 0,
                x: 35,
                x2: 20
            }
        },
        init: function () {
            var self = this;
            //if (data == undefined) {
            //    console.info("下级组织本月加班统计！");
            //} else {
            //    var xAxisData = [], seriesData = [];
            //    var tem = [];
            //    $.each(data, function (n, v) {
            //        tem.push({"name": n, "value": -v});
            //    });
            //    tem = _.sortBy(tem, "value");
            //    $.each(tem, function (i, item) {
            //        xAxisData.push(item.name);
            //        seriesData.push(-item.value);
            //    });
            //    if (xAxisData.length > 0) {
            //        var num = 3;//3个以上显示滚动条
            //        if (xAxisData.length > num) {
            //            self.option.dataZoom = {
            //                show: true,
            //                realtime: true,
            //                height: 20,
            //                start: 0,
            //                end: 30,
            //                showDetail: false
            //            };
            //        } else {
            //            self.option.dataZoom = {}
            //        }
            //        self.chart.clear();
            //        self.option.xAxis[0].data = xAxisData;
            //        self.option.series[0].data = seriesData;
            //        self.chart.setOption(self.option);
            //        self.chart.refresh();
            //    } else {
            //        if (self.chart)
            //            self.chart.dispose();
            //        $("#overtime").html("<div class='loadingmessage'>暂无数据</div>");
            //    }
            //}

            if(self.chart) {
                self.chart.clear();
            }
            self.chart=initEChart("overtime");
            self.option.xAxis[0].data = ['北京分公司','北京总部'];
            self.option.series[0].data = [40, 30];
            self.chart.setOption(self.option);
            self.chart.refresh();

            self.chart.on(ecConfig.EVENT.CLICK, function(param){
                var dataIndex=param.dataIndex;
                console.log(param);


                $("#overtimeDialog").modal("show").on('shown.bs.modal', function () {
                    $("#overtimecontent").css({height: $(window).height() - 120 + "px"});
                    $("#overtimecontent .item").unbind().on("click", function(){
                        var position=$(this).position();
                        var width=$("#overtimecontent").width()-18;


                        $("#overtimeChart").css({top:position.top+130+"px", width:width+"px"}).removeClass("hide");
                        $(".cornerb,.cornerf").css({left:position.left+"px"});

                        if(self.chartPersonnel){
                            self.chartPersonnel.clear();
                        }
                        self.chartPersonnel=initEChart("otchart");
                        self.chartPersonnel.setOption(self.optionPersonnel);
                        self.chartPersonnel.refresh();
                    }).hover(function(){}, function(){
                        if($("#overtimeChart:visible").length>0) {
                            self.isHideChart = true;
                            setTimeout(function () {
                                if (self.isHideChart) {
                                    $("#overtimeChart").addClass("hide");
                                }
                            }, 200);
                        }
                    });

                });

            });

            $("#overtimeSelect").selection({
                dateType:3,
                dateRange:{
                    min:'2010-10-01',
                    max:'2016-06-3'
                },
                dateSelected:['2015', '3', '2016', '2'],
                crowdSelected:['0'],
                ok:function(event, data){
                    console.log(data)
                }
            });

            $("#overtimeChart").hover(function(){
                self.isHideChart=false;
            }, function(){
                setTimeout(function(){
                    $("#overtimeChart").addClass("hide");
                }, 200);
            });
        }
    };

    //性别
    var sexOpt = {
        calculable: false,
        color: ['#019BD9', '#FF88A2'],
        series: [
            {
                type: 'pie',
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
        data: null,
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#sexSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".sexOpt").loadingData();
            $(".teamImg-sex,.lidyZone,.manZone").addClass("hide");
            $.post(urls.getSexData, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".teamImg-sex,.lidyZone,.manZone").removeClass("hide");
                    $(".sexOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".sexOpt").noData();
                }
            });
        },
        render:function(){
            var self=this;
            var data=self.data;

            if(self.pieObj){
                self.pieObj.clear();
            }
            self.pieObj = initEChart(self.chartId);
            // 男女占比
            var rsData = [];
            $('.manVal').text(0);
            $('.lidyVal').text(0);
            $.each(data, function (a, b) {
                rsData.push({value: b.total, name: b.name, id: b.id});
                b.id == 'm' ? $('.manVal').text(b.total) : $('.lidyVal').text(b.total);
            });
            sexOpt.series[0].data = rsData;
            self.pieObj.setOption(sexOpt, true);

            self.click(data);
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.pieObj.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.data.id;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(i, item){
                        dim.push('<option value="'+item.id+'">'+item.name+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'性别',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getSexDataList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.pieObj){
                self.pieObj.resize();
            }
        }
    };


    //婚姻状况
    var marryObj = {
        pieObj: null,
        chartId: 'sex',
        data: null,
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#marrySelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".marryOpt").loadingData();
            $.post(urls.getMarryStatusData, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".marryOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".marryOpt").noData();
                }
            });
        },
        render: function () {
            var self=this;
            var data=self.data;

            var unIsMarry= 0, isMarry=0;
            $.each(data, function(i, item){
                if(parseInt(item.id)==0){
                    unIsMarry=item.total;
                }else if(parseInt(item.id)==1){
                    isMarry=item.total;
                }
            });

            // 婚姻状况
            rsData =
            {
                unIsMarry: unIsMarry,
                isMarry: isMarry,
                total: unIsMarry+isMarry
            };


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
            var _leftBarH =quotiety==0?0: _leftBatVal / quotiety;
            var _rightBarH =quotiety==0?0: _righBarVal / quotiety;

            var _leftBarHeight = _leftBarH < 16 ? 16 : _leftBarH;
            var _rightBarHeight = _rightBarH < 16 ? 16 : _rightBarH;
            $(".marry_state .leftBar").css({height: _leftBarHeight + "px", lineHeight: _leftBarHeight + "px"});
            $(".marry_state .rightBar").css({height: _rightBarHeight + "px", lineHeight: _rightBarHeight + "px"});


            // 换算人数高度占的百分比
            $(".marry_state .leftBarPer").append((Math.round((_leftBarH / 200) * 100)) + "%");
            $(".marry_state .rightBarPer").append((Math.round((_rightBarH / 200) * 100)) + "%");

            self.click(data);
        },
        reset: function () {
            $(".marry_state #leftBarVal").text('');
            $(".marry_state #righBarVal").text('');
            $(".marry_state .leftBarPer").text('');
            $(".marry_state .rightBarPer").text('');
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            $(".leftBar,.rightBar").on("click", function(){
                var dimSelected=$(this).hasClass("leftBar")?0:1;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(i, item){
                        dim.push('<option value="'+item.id+'">'+item.name+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'婚姻状况',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getMarryStatusDataList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.pieObj){
                self.pieObj.resize();
            }
        }
    };



    //血型
    var bloodOpt = {
        calculable: false,
        legend: legendObj,
        color: colorPie,
        series: [
            {
                name: '',
                type: 'pie',
                radius: '55%',
                center: piePosition,
                data: [],
                itemStyle: getItemStyle(false)
            }
        ]
    };
    var bloodObj = {
        pieObj: null,
        chartId: 'blood',
        data: null,
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#bloodSelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".bloodOpt").loadingData();
            $.post(urls.getBloodData, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".bloodOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".bloodOpt").noData();
                }
            });
        },
        render:function(){
            var self=this;
            var data=self.data;

            // 血型
            var rslegend = [],rsData = [];
            $.each(data, function (a, b) {
                rsData.push({value: b.total, name: b.name, id: b.id});
                rslegend.push(b.name);
            });

            if(self.pieObj){
                self.pieObj.clear();
            }
            self.pieObj = initEChart(self.chartId);

            var width = $("#blood").width();
            bloodOpt.series[0].radius = width < 400 ? (width / 10).toString() + '%' : '55%';
            bloodOpt.series[0].data = rsData;
            bloodOpt.legend.data = rslegend;

            self.pieObj.setOption(bloodOpt, true);

            self.click(data);
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.pieObj.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.data.id;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(i, item){
                        dim.push('<option value="'+item.id+'">'+item.name+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'血型',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getBloodDataList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.pieObj){
                self.pieObj.resize();
            }
        }
    };


    //星座
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
                name: '星座',
                type: 'bar',
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
                                + '"/>';
                        }
                    },
                    data: [
                        {
                            xAxis: 0,
                            y: 325,
                            name: '白羊座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-1.png'
                        },
                        {
                            xAxis: 1,
                            y: 325,
                            name: '金牛座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-2.png'
                        },
                        {
                            xAxis: 2,
                            y: 325,
                            name: '双子座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-3.png'
                        },
                        {
                            xAxis: 3,
                            y: 325,
                            name: '巨蟹座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-4.png'
                        },
                        {
                            xAxis: 4,
                            y: 325,
                            name: '狮子座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-5.png'
                        },
                        {
                            xAxis: 5,
                            y: 325,
                            name: '处女座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-6.png'
                        },
                        {
                            xAxis: 6,
                            y: 325,
                            name: '天秤座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-7.png'
                        },
                        {
                            xAxis: 7,
                            y: 325,
                            name: '天蝎座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-8.png'
                        },
                        {
                            xAxis: 8,
                            y: 325,
                            name: '射手座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-11.png'
                        },
                        {
                            xAxis: 9,
                            y: 325,
                            name: '摩羯座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-10.png'
                        },
                        {
                            xAxis: 10,
                            y: 325,
                            name: '水瓶座',
                            symbolSize: 20,
                            symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-9.png'
                        },
                        {
                            xAxis: 11,
                            y: 325,
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
        data: null,
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#constellatorySelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".constellatoryOpt").loadingData();
            $.post(urls.getStarData, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".constellatoryOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".constellatoryOpt").noData();
                }
            });
        },
        render:function(){
            var self = this;
            var rsData=self.data;

            if(self.barObj){
                self.barObj.clear();
            }
            self.barObj = initEChart(self.chartId);

            constellatoryOpt.xAxis[0].data = rsData.xAxisData;
            constellatoryOpt.series[0].data = rsData.seriesData;
            var width = $("#constellatory").width();
            constellatoryOpt.series[0].itemStyle.normal.label.formatter = function (param) {
                var name = param.name;
                if (width < 470) {
                    var s = "";
                    for (var i = 0; i < name.length; i++) {
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

            self.click(data);
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.barObj.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.dataIndex;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(i, item){
                        dim.push('<option value="'+i+'">'+item.name+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'星座',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getStarDataList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.barObj){
                self.render();
            }
        }
    };


    //性格
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
                center: piePosition,
                radius: ['40%', '60%'],
                data: [],
                itemStyle: getItemStyle(true)
            }
        ]
    };
    var personalityObj = {
        pieObj: null,
        chartId: 'personality',
        data: null,
        day:maxDate,
        crowd:'',
        init: function () {
            var self=this;
            self.request(self.day, self.crowd);

            $("#personalitySelect").selection({
                dateType:5,
                dateRange:{
                    min:minDate,
                    max:maxDate
                },
                dateSelected:[maxDate],
                crowdSelected:['0'],
                ok:function(event, data){
                    self.day=data.date[0];
                    self.crowd=data.crowd.join(',');
                    self.request(self.day, self.crowd);
                }
            });
        },
        request:function(day, crowd){
            var self=this;
            $(".personalityOpt").loadingData();
            $.post(urls.getPersonalityData, {organId: reqOrgId, day:day, crowd:crowd}, function(data){
                if(data) {
                    $(".personalityOpt").showData();
                    self.data = data;
                    self.render();
                }else{
                    $(".personalityOpt").noData();
                }
            });
        },
        render:function(){
            var self=this;
            var data=self.data;

            var rsData = [],rslegend = [];
            $.each(data, function (a, b) {
                rsData.push({value: b.total, name: b.name, emps: b.id});
                rslegend.push(b.name);
            });

            if(self.pieObj){
                self.pieObj.clear();
            }
            self.pieObj=initEChart(self.chartId);

            self.setData(rsData, rslegend);
            self.pieObj.setOption(personalityOpt, true);

            self.click(data);
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
                    html += '<div>' + _data.name + '：(' + _data.value + ') <br />';

                    $.each(_data.emps, function (i, item) {
                        html += item + '<br />';
                    });
                    html += '</div>';
                    return html;
                };
        },
        click:function(dimData){
            var self=this;
            var t=dialogObj;
            //点击图表
            self.pieObj.on(ecConfig.EVENT.CLICK, function(param){
                var dimSelected=param.data.emps;
                var day=self.day;
                var crowd=self.crowd;

                t.obj.modal("show").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    //设置搜索栏值
                    var dim=[];
                    $.each(dimData, function(i, item){
                        dim.push('<option value="'+item.id+'">'+item.name+'</option>');
                    });
                    t.setValue({
                        day:day,
                        dim:dim,
                        dimName:'性格',
                        dimSelected:dimSelected,
                        crowd:crowd
                    });

                    //jqGrid
                    var pdata = {organId: reqOrgId, day:day, crowd:crowd, id:dimSelected};
                    var option={
                        url:urls.getPersonalityDataList
                    };
                    t.renderGrid(pdata, option);
                });
            });
        },
        resize:function(){
            var self=this;
            if(self.pieObj){
                self.pieObj.resize();
            }
        }
    };

    var formatPieData=function (total, item) {
        if (!item) {
            return {};
        }
        return Tc.formatNumber(item) + '人\n' + ((item / total) * 100).toFixed(0) + '%';
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
        chartResize();
    });

    var chartResize = function () {
        if($("#detailListDialog:visible").length>0){
            $("#detailList").setGridWidth($("#detailListcontent").width()-16);
        }

        var t = pageObj;
        chartWidth = $("#constellatory").parent().width();
        switch ($(".leftBody .selectList").attr("page")) {
            case "page-one":
            {//页签1
                if (t.tabFirstLoaded) {
                    objBudgetAnalyse.resize();
                    objManagersEmployee.resize();
                    objRank.resize();
                    objPositionSequence.resize();
                    objPositionRank.resize();
                    organDistribution.resize();
                    workLocation.resize();
                }
                break;
            }
            case "page-two":
            {//页签2
                if (t.tabSecondLoaded) {
                    sexObj.resize();
                    marryObj.resize();
                    bloodObj.resize();
                    constellatoryObj.resize();
                    personalityObj.resize();
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
        tabFourLoaded: false,
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
                chartResize();
            }
        },
        //页签1
        initFirstTab: function () {
            var self = this;
            if (!self.tabFirstLoaded) {
                self.tabFirstLoaded = true;
                objBudgetAnalyse.init();
                timeLineObj.init(reqOrgId);

                objManagersEmployee.init();
                objRank.init();
                objPositionSequence.init();
                organDistribution.init();
                workLocation.init();
            }
        },
        //页签2
        initSecondTab: function () {
            var self = this;
            if (!self.tabSecondLoaded) {
                self.tabSecondLoaded = true;
                //overtime.init();
                sexObj.init();
                marryObj.init();
                bloodObj.init();
                constellatoryObj.init();
                personalityObj.init();
            }
        },
        reload: function(){
            var self=this;
            self.tabFirstLoaded = self.tabSecondLoaded = self.tabThreeLoaded = self.tabFourLoaded = false;
            switch (self.tabName) {
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
        }
    };
    pageObj.initFirstTab();
});