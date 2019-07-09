require(['jquery', 'moment', 'echarts', 'echarts/chart/funnel', 'echarts/chart/line', 'echarts/chart/bar', 'echarts/chart/pie', 'bootstrap', 'underscore',
    'utils', 'unveil', 'messenger', 'jgGrid', 'ztree', 'riskTree', 'tooltipZrw', "jquery-ui", "jquery-drag", "jquery-mCustomScrollBar"
], function ($, moment, echarts) {

    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        loaderUrl: webRoot + "/assets/img/base/loader.gif",
        empDefaultUrl: webRoot + '/assets/img/base/u-default.png',          //员工默认头像路径
        getChildDataUrl: webRoot + '/manageHome/getChildOrgData.do',	    //获取子节点数据
        getTeamEmpUrl: webRoot + '/manageHome/getTeamEmp.do',           //获取团队成员信息
        getRemindEmpUrl: webRoot + '/manageHome/getRemindEmp.do',           //获取团队提醒-生日榜成员信息
        getTeamRemindUrl: webRoot + '/manageHome/getTeamRemind.do',     //获取团队提醒相关信息
        getGainOfLossUrl: webRoot + '/manageHome/getGainOfLoss.do',         //获取当季人才损益信息
        getTeamImgAbUrl: webRoot + '/manageHome/getTeamImgAb.do',           //获取团队画像职位层级
        empBaseInfoUrl: webRoot + "/common/getEmpBaseInfo.do",              //获取员工基本信息
        empRiskDetailUrl: webRoot + "/dismissRisk/getEmpRiskDetail.do",	    //获取员工离职风险信息
        getPerformance: webRoot + '/manageHome/getPerformance.do',          //绩效目标
        getWorkOvertimeUrl: webRoot + '/manageHome/getWorkOvertimeInfo.do',             //人员加班详细信息
        getWarnCountUrl: webRoot + '/manageHome/getHomeWarnCount.do',                   //人员预警统计信息
        getRunOffWarnEmpInfoUrl: webRoot + '/manageHome/getRunOffWarnEmpInfo.do',       //离职风险预警
        getPerformanceUrl: webRoot + '/manageHome/getPerformanceInfo.do',               //高绩效与低绩效预警
        getOvertimeEmpUrl: webRoot + '/manageHome/getOvertimeEmp.do',                   //生活不平衡预警
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',               //跳转到员工详情
        getTalentDevelUrl: webRoot + '/manageHome/getTalentDevel.do',                   //人才发展
        getTalentDevelExamItemUrl: webRoot + '/manageHome/getTalentDevelExamItem.do',   //人才发展-测评项目
        getUserHomeConfigUrl: webRoot + '/manageHome/findUserHomeConfig.do',            //获取用户首页拖拽排序配置
        editUserHomeConfigUrl: webRoot + '/manageHome/editHomeConfig.do',               //保存用户首页拖拽排序配置
        getSearchEmpUrl: webRoot + '/talentContrast/getSearchEmpList.do',               //获取搜索人员信息
        toTalentProfileUrl: webRoot + "/talentProfile/toTalentProfileView.do",          //跳转到人员剖像页面
        toTalentContrastUrl: webRoot + "/talentContrast/toTalentContrastView.do"        //跳转到人员PK页面
    };

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;

    var riskFlagArr = ['gray', 'red', 'yellow', 'green'];
    var riskTreeOption = {
        hasSelect: false,
        data: null,
        hasTopText: false
    }

    /*
     resize
     */
    $(window).resize(function () {
        teamImgAbObj.funnelObj.resize();
        $("#treegrid").setGridWidth($("#index-son-grid-body").width());
    });

    $(".ct-mCustomScrollBar").mCustomScrollbar({});//mCustomScrollbar

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        warnPanel.loadPanel(organId);
        remindObj.requestData(organId);
        teamImgAbObj.getRequestData(organId);
        performanceObj.Init(organId);
        orgTreeGridObj.first = false;
        orgTreeGridObj.init(organId);
        gainOfLossObj.init(organId);
    }


    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.append("<div class='loadingmessage'>数据加载中</div>");
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
            $chart.children().show();
            $chart.children('div.loadingmessage').remove();
        }
    }

    /*
     * teamImg
     * */
    var teamImgAbOpt = {
        color: ['#0B98E1', '#00BDA9', '#08B560', '#88C000', '#EFA100', '#E80606', '#FF0084', '#AE00E0', '#732DF6', '#2341F3'],
        series: [
            {
                sort: 'ascending',
                type: 'funnel',
                left: '0%',
                width: '50%',
                itemStyle: {
                    normal: {
                        label: {
                            position: 'right',
                            formatter: '{b}'
                        },
                        labelLine: {
                            show: true
                        }
                    }
                },
                data: []
            },
            {
                sort: 'ascending',
                type: 'funnel',
                left: '0%',
                width: '50%',
                itemStyle: {
                    normal: {
                        label: {
                            position: 'inside',
                            formatter: '{b}',
                            textStyle: {
                                color: '#fff',
                                fontSize: "12"
                            }
                        }
                    }
                },
                data: []
            }
        ]
    };
    var teamImgAbObj = {
        funnelObj: null,
        chartId: 'teamImg-funel',
        resultData: null,
        init: function (organId) {
            var self = this;
            self.funnelObj = echarts.init(document.getElementById(this.chartId));
            self.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            if (_.isNull(self.resultData)) {
                self.getRequestData(organId);
                return;
            }
            self.render(self.resultData);
        },
        getRequestData: function (organId) {
            var self = this;
            loadingChart(self.chartId);
            $.post(urls.getTeamImgAbUrl, {'organId': organId}, function (rs) {
                if (_.isEmpty(rs)) {
                    hideChart(self.chartId, true);
                    return;
                }
                hideChart(self.chartId, false);
                self.resultData = rs;
                self.initData(organId);
            });
        },
        render: function (data) {
            var self = this;
            var sexPerData = data[0], yearsData = data[1], marryData = data[2], abilityData = data[3];

            //teamImg开始渲染数据
            teamImgAbOpt.series[0].data = [];
            //teamImgAbOpt.series[1].data = [];
            if (!_.isEmpty(abilityData)) {
                _.each(abilityData, function (item, i) {
                    teamImgAbOpt.series[0].data.push({value: item.k, name: item.v});
                });
            }

            self.funnelObj.setOption(teamImgAbOpt, true);

            $("#totalPer").text(self.resultData[4]);
            var sexPerV = _.isUndefined(sexPerData) ? '' : sexPerData.v;
            if (!_.isEmpty(sexPerV)) {
                $('#teamImgSex').show();
                $('#teamImgSexK').text(sexPerData.k);
                $('#teamImgSexV').text(sexPerV);
            } else {
                $('#teamImgSex').hide();
            }
            var yearsV = _.isUndefined(yearsData) ? '' : yearsData.v;
            if (!_.isEmpty(yearsV)) {
                $('#teamImgYear').show();
                $('#teamImgYearK').text(yearsData.k);
                $('#teamImgYearV').text(yearsV);
            } else {
                $('#teamImgYear').hide();
            }
            var marryV = _.isUndefined(marryData) ? '' : marryData.v;
            if (!_.isEmpty(marryV)) {
                $('#teamImgMarry').show();
                $('#teamImgMarryK').text(marryData.k);
                $('#teamImgMarryV').text(marryV);
            } else {
                $('#teamImgMarry').hide();
            }
        }
    }
    teamImgAbObj.init(reqOrgId);

    /*
     下属组织信息
     */
    var orgTreeGridObj = {
        shrinkToFit: true,
        modelId: '#orgTreeGridModal',
        isHide: true,
        treeGridId: '#treegrid',
        modalTreeGridId: '#modalTreeGrid',
        first: true,
        firstModal: true,
//        autowidth:true,
//        shrinkToFit:true,
        options: {
            treeGrid: true,
            treeGridModel: 'adjacency', //treeGrid模式，跟json元數據有關 ,adjacency/nested
            url: urls.getChildDataUrl,
            datatype: 'json',
            //mtype: "POST",
            height: "230px",
//            autoWidth:true,
            ExpandColClick: true, //当为true时，点击展开行的文本时，treeGrid就能展开或者收缩，不仅仅是点击图片
            ExpandColumn: 'name',//树状结构的字段
            colNames: ['organizationId', '架构名称', '编制数', '可用编制数', '在岗人数', '负责人'],
            colModel: [
                {name: 'id', key: true, hidden: true},
                {name: 'name', width: 240, sortable: false, align: 'left'},
                {name: 'number', width: 120, hidden: true, sortable: false, align: 'center'},
                {name: 'usableEmpCount', width: 110, sortable: false, align: 'center'},
                {name: 'empCount', width: 120, sortable: false, align: 'center'},
                {name: 'userName', width: 180, fixed: true, sortable: false, align: 'center'}
            ],
            jsonReader: {
                root: "rows",
                total: "total",
                repeatitems: true
            },
            beforeRequest: function () {
                if (orgTreeGridObj.first) {
                    var data = this.p.data;
                    var postData = this.p.postData;
                    //var nodeId=postData.nodeid;
                    if (postData.nodeid == null) {
                        postData.nodeid = reqOrgId;
                    }
                    if (postData.n_level != null) {
                        level = parseInt(postData.n_level, 10);
                        postData.n_level = level + 1;
                    }
                }

            },
            treeIcons: {
                //JQUERY UI
//   					"plus": "ui-icon-circlesmall-plus",
//   					"minus": "ui-icon-circlesmall-minus",
//   					"leaf" : "ui-icon-document"
                // BOOTSTARP UI
                "plus": "ct-glyphicon-plus",
                "minus": "ct-glyphicon-minus"
                //,"leaf" : "glyphicon glyphicon-star"
            },
            pager: "false"
        },
        init: function (organId) {
            var self = this;

            self.organId = organId;
            self.initTreeGrid(organId, self.treeGridId, self.options);
            $('#subOrgInfoTitle').unbind("click").click(function () {
                self.first = false;
                $(self.modelId).modal('show');
                self.options.height = '400px';
                self.options.colModel[2].hidden = false;
                self.initTreeGrid(organId, self.modalTreeGridId, self.options);
            });
        },
        initTreeGrid: function (organId, gridId, options) {
            var self = this;

            if (self.first) {
                $(gridId).jqGrid(options);
                $("#treegrid").setGridWidth($("#index-son-grid-body").width());
                self.first = true;
                return;
            }

            if (gridId == self.modalTreeGridId) {
                if (self.firstModal) {
                    self.firstModal = false;
                    options.postData = {nodeid: organId};
                    $(gridId).jqGrid(options);
                } else {
                    $(gridId).clearGridData().setGridParam({
                        postData: {nodeid: reqOrgId}
                    }).trigger("reloadGrid");
                }
            } else {
                $(gridId).clearGridData().setGridParam({
                    postData: {nodeid: organId}
                }).trigger("reloadGrid");
            }
        }
    };
    orgTreeGridObj.init(reqOrgId);

    //查看组织架构图
    $("#gotoOrgView").click(function () {
        if (win.setlocationUrl) {
            var url = webRoot + "/orgChart/toOrgView" + "?organId=" + reqOrgId;
            win.setlocationUrl(url);
        }
    });

    /*
     绩效目标
     */
    var performanceObj = {
        Init: function (orgId) {
            $.post(urls.getPerformance, {'organId': orgId}, function (rs) {
                //部门绩效目标
                var dep = rs[0];
                var depHrml = "";
                if (!_.isEmpty(dep)) {
                    $.each(dep, function (i) {
                        depHrml += "<tr>";
                        depHrml += "<td class='performance-dep-td-one'>" + dep[i].content + "</td>";
                        depHrml += "<td class=\"center\">" + dep[i].weight * 100 + "%</td>";
                        depHrml += "</tr>";
                    });
                }

                $("#performance_dep tbody").html(depHrml);

                //下属绩效目标
                var emp = rs[1];
                var empHtml = "";
                var empObj = [];
                if (!_.isEmpty(emp)) {
                    $.each(emp, function (i) {
                        var b = false;
                        $.each(empObj, function (j) {
                            if (empObj[j].empId == emp[i].empId) {
                                b = true;
                            }
                        });
                        if (!b) {
                            empObj.push(emp[i]);
                        }
                    });
                }
                $.each(empObj, function (i) {
                    var s = "";
                    var w = 0;
                    $.each(emp, function (j) {
                        if (emp[j].empId == empObj[i].empId) {
                            w += emp[j].weight * 100;
                            s += "<tr class=\"detail hide\">";
                            s += "<td class=\"nobb\"></td>";
                            s += "<td>" + emp[j].assessName + "</td>";
                            s += "<td class=\"center\">" + emp[j].weight * 100 + "%</td>";
                            s += "<td>" + emp[j].idp + "</td>";
                            s += "</tr>";
                        }
                    });
                    empHtml += "<tbody class=\"bb\">";
                    empHtml += "<tr class=\"head\">";
                    empHtml += "<td class=\"nobb\">" + empObj[i].name + "</td>";
                    empHtml += "<td>" + empObj[i].assessParentName + "</td>";
                    empHtml += "<td class=\"center\">" + w + "%</td>";
                    empHtml += "<td><div style='padding: 5px 0px;'>" + empObj[i].idptotal + "</div></td>";
                    empHtml += "</tr>";
                    empHtml += s;
                    empHtml += "</tbody>";
                });
                $("#performance_sub tbody").remove();
                $("#performance_sub").append(empHtml);

                //js渲染
                performanceObj.Performance();
            });
        },
        Performance: function () {
            $("#performance_" + $(".performance .tab .on").data("index")).show();
            $(".performance .tab .title").mouseover(function () {
                $("#performance_" + $(".performance .tab .on").data("index")).hide();
                $("#performance_" + $(this).data("index")).show();
                $(".performance .tab .on").removeClass("on");
                $(this).addClass("on");
            });

            $("#performance_sub .head").click(function () {
                $(this).parent().find(".detail").toggleClass("hide");
                var imgname = $(this).parent().find(".detail").hasClass("hide") ? "right" : "down";
                $(this).find("img").attr("src", webRoot + "/assets/img/manage/performance_" + imgname + ".png");
            });

            $("#performance_sub .head .nobb").each(function () {
                $(this).append("<img src=\"" + webRoot + "/assets/img/manage/performance_right.png\">");
            });
        }
    };
    performanceObj.Init(reqOrgId);
    //绩效目标点击事件
    $(".index-jxmb-btn").click(function () {
        $(".index-jxmb-btn").removeClass("index-jxmb-btn-select");
        $(this).addClass("index-jxmb-btn-select");
        if ($(".index-jxmb-btn").index(this) == 0) {
            $("#performance_dep").css("display", "table");
            $("#performance_sub").css("display", "none");
        } else {
            $("#performance_dep").css("display", "none");
            $("#performance_sub").css("display", "table");
        }
    });

    /*
     * 表格Option
     */
    var gridOption = {
        data: [],
        datatype: "local",
        altRows: false,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职级', '所待时长（年）', '操作'],
        colModel: [
            {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
            {name: 'userNameCh', width: 60, sortable: false, align: 'center'},
            {name: 'positionName', width: 70, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceSubName', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'abilityName', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
            {name: 'entryDate', width: 60, fixed: true, sortable: false, align: 'center'},
            {name: 'mycz', width: 120, fixed: true, sortable: false, align: 'center'}
        ],
        rowHeight: 44,
        scroll: true
    };

    /*
     当季人才损益
     */
    var gainOfLossObj = {
        modelId: '#gainOfLossModal',
        teamEmpGridId: '#teamEmpGrid',
        entryGridId: '#entryEmpsGrid',
        callinGridId: '#callinEmpsGrid',
        dimissionGridId: '#dimissionEmpsGrid',
        calloutGridId: '#calloutEmpsGrid',
        init: function (organId) {
            var self = this;
            if (self.organId != organId) {
                self.requestData(organId);
            }

            $('#personnel').click(function () {
                $(self.modelId).modal('show');
            });

            $(self.modelId).on('shown.bs.modal', function () {
                resizeGrid(self.entryGridId);
                resizeGrid(self.callinGridId);
                resizeGrid(self.dimissionGridId);
                resizeGrid(self.calloutGridId);
                $('#gainOfLossTabs a[data-toggle="tab"]').eq(0).click();
            });

            $('#gainOfLossTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                var targetAreaId = $(e.target).attr('aria-controls');
                //切换标签页时，要调用echarts的resize()方法对图表进行重绘（当tab页的内容处于隐藏状态时，改变窗口大小后重新显示时，图表可能会显示不正确，故需重绘）
                if (targetAreaId == 'changeEmpTab') {
                    resizeGrid(self.entryGridId);
                    resizeGrid(self.callinGridId);
                    resizeGrid(self.dimissionGridId);
                    resizeGrid(self.calloutGridId);
                } else {
                    self.initTeamEmp(reqOrgId);
                }
            });
        },
        requestData: function (organId) {
            var self = this;
            $.post(urls.getGainOfLossUrl, {'organId': organId}, function (rs) {
                if (_.isNull(rs) || _.isEmpty(rs)) {
                    return;
                }
                self.organId = organId;
                self.extendsGainOfLossData(rs);
            });
        },
        extendsGainOfLossData: function (source) {//编制信息和招聘信息

            var self = this;
            //渲染编制情况及招聘进程的数据
            var compileNum = source.compileNum;
            $('#compileNum').text(_.isNull(compileNum) ? '--' : compileNum);
            $('#usableCompileNum').text(_.isNull(compileNum) && _.isNull(source.workingNum) ? '--' : compileNum - source.workingNum);
            $('#publiceJobNum').text(_.isNull(source.publiceJobNum) ? '--' : source.publiceJobNum);
            $('#resumeNum').text(_.isNull(source.resumeNum) ? '--' : source.resumeNum);
            $('#acceptNum').text(_.isNull(source.acceptNum) ? '--' : source.acceptNum);
            $('#offerNum').text(_.isNull(source.offerNum) ? '--' : source.offerNum);
            self.extendsLossesEmpsData(source.empDtos);
        },
        extendsLossesEmpsData: function (source) {//人才损益信息
            var self = this;
            if (_.isEmpty(source)) {
                $('#entryEmpsNum,#callinEmpsNum,#dimissionEmpsNum,#calloutEmpsNum').text(0);
                $('#entryEmpsNum2,#callinEmpsNum2,#dimissionEmpsNum2,#calloutEmpsNum2').text(0);
                return;
            }
            var entryData = [], callinData = [], dimissionData = [], calloutData = [];
            //根据枚举或数据库调整   排序是：入职、调入、离职、调出
            $.each(source, function (i, obj) {
                switch (obj.changeType) {
                    case 3:
                        entryData.push(obj);
                        break;
                    case 2:
                        callinData.push(obj);
                        break;
                    case 5:
                        dimissionData.push(obj);
                        break;
                    case 4:
                        calloutData.push(obj);
                        break;
                }
            });
            var entryLen = entryData.length, callinLen = callinData.length,
                dimissionLen = dimissionData.length, calloutLen = calloutData.length;

            var entryGridId = self.entryGridId, callinGridId = self.callinGridId,
                dimissionGridId = self.dimissionGridId, calloutGridId = self.calloutGridId;

            //入职
            var entryOption = $.extend(true, {}, gridOption);
            entryOption.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职衔', '职级', '操作'];
            entryOption.colModel = [
                {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
                {name: 'userNameCh', width: 90, sortable: false, align: 'center'},
                {name: 'positionName', width: 90, sortable: false, align: 'center'},
                {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'jobTitleName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
                {name: 'mycz', width: 120, fixed: true, sortable: false, align: 'center'}
            ];
            //加载完成之后执行的方法
            entryOption.loadComplete = function (xhr) {
                extendsLoadComplete(entryGridId, xhr);
            };
            //if(entryLen != 0){
            initGrid(entryGridId, entryOption, entryData);
            //}
            //调入
            var callinOption = _.clone(entryOption);
            //加载完成之后执行的方法
            callinOption.loadComplete = function (xhr) {
                extendsLoadComplete(callinGridId, xhr);
            };
            //if(callinLen != 0){
            initGrid(callinGridId, callinOption, callinData);
            //}

            //离职
            var dimisstionOption = $.extend(true, {}, gridOption);
            dimisstionOption.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职级', '绩效', '离职日期', '操作'];
            dimisstionOption.colModel = [
                {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
                {name: 'userNameCh', width: 90, sortable: false, align: 'center'},
                {name: 'positionName', width: 90, sortable: false, align: 'center'},
                {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
                {name: 'performanceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {
                    name: 'changeDate',
                    width: 80,
                    fixed: true,
                    sortable: false,
                    align: 'center',
                    formatter: formatterToDate
                },
                {name: 'mycz', width: 120, fixed: true, sortable: false, align: 'center'}
            ];
            //加载完成之后执行的方法
            dimisstionOption.loadComplete = function (xhr) {
                extendsLoadComplete(dimissionGridId, xhr);
            };
            //if(dimissionLen != 0){
            initGrid(dimissionGridId, dimisstionOption, dimissionData);
            //}
            //调出
            var calloutOption = _.clone(dimisstionOption);
            calloutOption.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职级', '绩效', '调出日期', '操作'];
            //加载完成之后执行的方法
            calloutOption.loadComplete = function (xhr) {
                extendsLoadComplete(calloutGridId, xhr);
            };
            //if(calloutLen != 0){
            initGrid(calloutGridId, calloutOption, calloutData);
            //}

            $('#entryEmpsNum').text(entryLen);
            $('#callinEmpsNum').text(callinLen);
            $('#dimissionEmpsNum').text(dimissionLen);
            $('#calloutEmpsNum').text(calloutLen);
            $('#entryEmpsNum2').text(entryLen);
            $('#callinEmpsNum2').text(callinLen);
            $('#dimissionEmpsNum2').text(dimissionLen);
            $('#calloutEmpsNum2').text(calloutLen);

            var gainOfLossNum = entryLen + callinLen - dimissionLen - calloutLen;
            $('#gainOfLossNum').text(gainOfLossNum < 0 ? gainOfLossNum : ('+' + gainOfLossNum));
        },
        initTeamEmp: function (organId) {         //团队员工信息
            var self = this;
            if (self.hasTeamEmpInit && self.teamEmpOrganId != organId) {
                self.resizeTeamEmpGrid(organId);
                return true;
            }
            var gridId = self.teamEmpGridId;
            var option = $.extend(true, {}, gridOption);
            option.datatype = 'json';
            option.url = urls.getTeamEmpUrl;
            option.mtype = 'POST';
            option.postData = {'organId': organId};
            option.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职衔', '职级', '操作'];
            option.colModel = [
                {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
                {name: 'userNameCh', width: 90, sortable: false, align: 'center'},
                {name: 'positionName', width: 90, sortable: false, align: 'center'},
                {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'jobTitleName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
                {name: 'mycz', width: 120, fixed: true, sortable: false, align: 'center'}
            ];
            //加载完成之后执行的方法
            option.loadComplete = function (xhr) {
                extendsLoadComplete(gridId, xhr);
            };
            $(gridId).jqGrid(option);
            self.teamEmpOrganId = organId;
            self.hasTeamEmpInit = true;
        },
        resizeTeamEmpGrid: function (organId) {
            var self = this;
            $(self.teamEmpGridId).clearGridData().setGridParam({
                postData: {'organId': organId}
            }).trigger("reloadGrid");
            resizeGrid(self.teamEmpGridId);
        }
    };
    gainOfLossObj.init(reqOrgId);

    /*
     * 团队提醒
     */
    var remindObj = {
        modelId: '#remindModal',
        rows: 5,
        page: 0,
        rowHeight: 24,
        init: function (organId) {
            var self = this;
            //TODO 当前时间
            self.currDateTime = $('#updateTime').text();

            $('#remindBody').click(function () {
                self.resizeModelTop();
                self.initBirthday(reqOrgId);
                $(self.modelId).modal('show');
            });
            self.requestData(organId);
        },
        resizeModelTop: function () {
            var self = this;
            $(self.modelId).find('.modal-content').css('margin-top', win.pageYOffset);
        },
        initBirthday: function (organId) {        //初始化生日榜
            var self = this;
            if (self.hasInit) {
                self.requestBirthdayData(organId);
                return true;
            }
            var monthIdx = parseInt(moment(self.currDateTime, 'YYYY/MM/DD').format('MM')) - 1;
            var $dirthLayer = $('#dirthday_layer');
            var headChildren = $dirthLayer.find('.l-birthday-table .l-birthday-head>.l-birthday-child');
            var dataChildren = $dirthLayer.find('.l-birthday-table .l-birthday-body .l-birthday-child');

            $(headChildren[monthIdx]).addClass('bg_highlight');
            $(dataChildren[monthIdx]).addClass('bg_highlight_name');
            self.hasInit = true;
            self.birthLayer = dataChildren;
            self.requestBirthdayData(organId);
        },
        initAnnualHtml: function (annualArr) {
            var self = this;
            var _birthdayGroup = $('#birthdayBtnGroup');
            _birthdayGroup.html('');
            for (var i = 0; i < annualArr.length; i++) {
                var annualTxt = annualArr[i];
                var annualLabel = $('<div/>', {'class': 'birthdayBtn', 'data-key': annualTxt});
                annualLabel.text(annualTxt + '周年');
                _birthdayGroup.append(annualLabel);
            }
            _birthdayGroup.children().bind('click', function () {
                var _this = $(this);
                self.setAnnualSelect(_this.data('key'));
            });
        },
        setAnnualSelect: function (yearKey) {
            var self = this;
            $.each($('#birthdayBtnGroup').children(), function (i, obj) {
                var _this = $(obj);
                if (_this.data('key') == yearKey) {
                    _this.addClass('birthdayBtnSelect');
                } else {
                    _this.removeClass('birthdayBtnSelect');
                }
            });
            var empsArr = [];
            if (self.entryYearEmps) {
                empsArr = _.map(self.entryYearEmps[yearKey], _.property('userNameCh'));
            }
            $('#entryYearNum').text(empsArr.length);
            $("#yearNameList").text(_.isEmpty(empsArr) ? "无" : empsArr.join("、"));
        },
        requestData: function (organId) {        //请求获取数据
            var self = this;
            $.post(urls.getTeamRemindUrl, {'organId': organId}, function (rs) {
                self.extendsRequestData(rs);
            });
        },
        extendsRequestData: function (source) {        //封装生日提醒数据,入司周年数据
            var self = this;
            var birthdays = source.birthday;
            //渲染当月生日员工 生日提醒
            if (!_.isUndefined(birthdays) && birthdays.length > 0) {
                $("#currMonthNum").text(birthdays.length);
                var currMonthArray = _.map(birthdays, _.property('userNameCh'));
                $("#birName").text(currMonthArray.join("、"));
            } else {
                $("#currMonthNum").text(0);
                $("#birName").text("无");
            }

            //渲染入司周年提醒
            var entryYearEmps = [], annualArr = [];
            var maxNumYear = 0, maxNum = 0, idx = 0;
            _.each(source, function (item, i) {
                if (i == 'birthday') return true;
                entryYearEmps[i] = item;
                annualArr[idx] = i;
                if (maxNum == 0) {
                    maxNum = item.length;
                    maxNumYear = i;
                }
                idx++;
            });
            self.initAnnualHtml(annualArr);
            self.entryYearEmps = entryYearEmps;
            if (maxNumYear != 0) {
                self.setAnnualSelect(maxNumYear);
            } else {
                self.setAnnualSelect(entryYearEmps[entryYearEmps.length - 1]);
            }
        },
        requestBirthdayData: function (organId) {
            var self = this;
            if (self.requestOrganId != organId) {
                self.page = 0;
            }
            $.post(urls.getRemindEmpUrl, {organId: organId, page: self.page, rows: self.rows}, function (result) {
                self.requestOrganId = organId;
                var bool = result.total > result.page;
                self.page = bool ? result.page + 1 : result.total;
                if (result.page == 1) {
                    self.birthLayer.html('');
                }
                self.drawBirthdayHtml(result.rows, bool);
            });
        },
        drawBirthdayHtml: function (source, hasMore) {
            var self = this;
            $.each(source, function (i, item) {
                var month = new Date(item.birthDate).getMonth();

                var monthName = item.userNameCh;
                if (monthName.length > 6) {
                    monthName = monthName.substr(0, 6) + "...";
                }
                var innerHtml = '<span title="' + item.userNameCh + "：" + moment(item.birthDate).format('YYYY-MM-DD') + '">' + monthName + '</span>';
                $(self.birthLayer[month]).append(innerHtml);
            });
            var maxObj = _.max(self.birthLayer, function (item) {
                return $(item).children().length;
            });
            var maxHeight = $(maxObj).children().length * self.rowHeight;
            self.birthLayer.height(maxHeight);

            if (hasMore) {
                $('#more_layer').removeClass('hide');

                $('#smoreBtn').unbind('click').bind('click', function () {
                    self.moreBtnEvent();
                });
            } else {
                $('#more_layer').addClass('hide');
            }
        },
        moreBtnEvent: function () {
            var self = this;
            self.requestBirthdayData(self.requestOrganId);
        }
    };
    remindObj.init(reqOrgId);


    /*
     绩效信息表格模板
     */
    var performanceWarnOption = {
        data: [],
        datatype: "local",
        altRows: false,	//设置表格行的不同底色
        autowidth: true,
        height: 150,
        colNames: ['', '姓名', '岗位', '职位主序列', '职位子序列', '职衔', '职级', '绩效', '操作'],
        colModel: [
            {
                name: 'imgPath',
                index: 'imgPath',
                width: 70,
                sortable: false,
                align: 'center',
                formatter: function (value) {
                    if (_.isEmpty(value)) {
                        value = webRoot + "/assets/photo.jpg";
                    }
                    return "<img src='" + value + "' class='head-pic img-circle'>";
                }
            },
            {
                name: 'userNameCh',
                index: 'userNameCh',
                width: 100,
                sortable: false,
                align: 'center'
            },
            {
                name: 'positionName',
                index: 'positionName',
                width: 100,
                sortable: false,
                align: 'center'
            },
            {
                name: 'sequenceName',
                index: 'sequenceName',
                width: 90,
                sortable: false,
                align: 'center'
            },
            {
                name: 'sequenceSubName',
                index: 'sequenceSubName',
                width: 90,
                sortable: false,
                align: 'center'
            }, {
                name: 'jobTitleName',
                index: 'jobTitleName',
                width: 130,
                sortable: false,
                align: 'center'
            }, {
                name: 'rankName',
                index: 'rankName',
                width: 90,
                sortable: false,
                align: 'center'
            }, {
                name: 'performanceName',
                index: 'performanceName',
                width: 70,
                sortable: false,
                align: 'center'
            },
            {
                name: 'empId',
                index: 'empId',
                width: 250,
                sortable: false,
                align: 'center',
                formatter: function (value) {
                    return "<a href='javascript:void(0)' data='" + value + "' class='talent_col' >人才剖像</a>";
                }

            }
        ],
        rowNum: 9999999,
        // rownumbers: true,
        //rownumWidth: 40,
        // scroll: true,
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
        }
    };
    // 加班信息
    var overtimeOption = {
        calculable: false,
        grid: {
            borderWidth: 1,
            x: 40,
            y: 5
        },
        color: ['#23C6C8'],
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                onZero: false,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisLabel: {
                show: true,
                rotate: 45, //刻度旋转45度角
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [{
            type: "value",
            show: true,
            splitLine: false,
            min: 0,           //最小
            splitNumber: 2,
            axisTick: {
                show: false
            }, axisLabel: {
                show: true,
                formatter: '{value} h'
            },
            axisLine: {
                lineStyle: {
                    color: '#BCBCBC'
                }
            }
        }],
        series: [{
            type: "line",
            smooth: false,
            clickable: false,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            },
            data: []
        }]
    };

    /*
     预警
     */
    var warnPanel = {
        performanceWarnObj: {
            resultData: null,
            HPId: "#highPerformanceGrid",
            LPId: "#lowPerformanceGrid",
            init: function (l_data) {
                $(this.HPId).jqGrid(performanceWarnOption);
                $(this.LPId).jqGrid(performanceWarnOption);
            },
            initHPGrid: function (h_data) {
                $("#highNum").text(_.isUndefined(h_data) ? 0 : h_data.length);
                $(this.HPId).clearGridData().setGridParam({
                    data: h_data
                }).trigger("reloadGrid");
                this.resizeGrid();
            },
            initLPGrid: function (l_data) {
                $("#lowNum").text(_.isUndefined(l_data) ? 0 : l_data.length);
                $(this.LPId).clearGridData().setGridParam({
                    data: l_data
                }).trigger("reloadGrid");
                this.resizeGrid();
            },
            resizeGrid: function () {
                $(this.HPId).setGridWidth(880);
                $(this.LPId).setGridWidth(880);

                //$("#highPerformanceArea").find(".ui-jqgrid-htable").width($("#highPerformanceArea").children(":first").width() * 0.9);
                //$("#lowPerformanceArea").find(".ui-jqgrid-htable").width($("#lowPerformanceArea").children(":first").width() * 0.9);
                //$("#highPerformanceArea").find(".ui-jqgrid-hdiv").width($("#highPerformanceArea").children(":first").width() * 0.9);
                //$("#lowPerformanceArea").find(".ui-jqgrid-hdiv").width($("#lowPerformanceArea").children(":first").width() * 0.9);
                //$("#highPerformanceArea").find(".ui-jqgrid-hbox").width($("#highPerformanceArea").children(":first").width() * 0.5);
                //$("#lowPerformanceArea").find(".ui-jqgrid-hbox").width($("#lowPerformanceArea").children(":first").width() * 0.5);
            }
        }, loadRunOffWarnEmp: function (result) {
            if (_.isNumber(result) && result > 0) {//渲染离职风险
                $("#runOffWarnTabSpan").text(result).addClass('index-yj-warn-value');
                //if (len >= 10) {
                //    $("#runOffWarnTabDiv").text(result[0].userNameCh + "、" + result[1].userNameCh + "、" + result[2].userNameCh + "、" + result[3].userNameCh + "、" + result[4].userNameCh + "、" + result[5].userNameCh + "、" + result[6].userNameCh + "、" + result[7].userNameCh + "、" + result[8].userNameCh + "、" + result[9].userNameCh);
                //} else {
                //    var strArray = [];
                //    $(result).each(function (i, e) {
                //        strArray.push(result[i].userNameCh);
                //    });
                //    $("#runOffWarnTabDiv").text(strArray.join("、"));
                //}
            } else {
                $("#runOffWarnTabSpan").text(0).removeClass('index-yj-warn-value');
                //$("#runOffWarnTabDiv").text("无");
            }
        }, loadLowPerformance: function (result) {
            warnPanel.performanceWarnObj.initLPGrid(result);
            if (!_.isEmpty(result)) {//渲染低绩效未调整
                var len = result.length;
                $("#performanceLowWarnTabSpan").text(len).addClass('index-yj-warn-value');
                //if (len >= 10) {
                //    $("#performanceLowWarnTabDiv").text(result[0].userNameCh + "、" + result[1].userNameCh + "、" + result[2].userNameCh + "、" + result[3].userNameCh + "、" + result[4].userNameCh + "、" + result[5].userNameCh + "、" + result[6].userNameCh + "、" + result[7].userNameCh + "、" + result[8].userNameCh + "、" + result[9].userNameCh);
                //} else {
                //    var strArray = [];
                //    $(result).each(function (i, e) {
                //        strArray.push(result[i].userNameCh);
                //    });
                //    $("#performanceLowWarnTabDiv").text(strArray.join("、"));
                //}
            } else {
                $("#performanceLowWarnTabSpan").text(0).removeClass('index-yj-warn-value');
                //$("#performanceLowWarnTabDiv").text("无");
            }
        }, loadHighPerformance: function (result) {
            warnPanel.performanceWarnObj.initHPGrid(result);
            if (!_.isEmpty(result)) {//渲染高绩效未晋升
                var len = result.length;
                $("#performanceHighWarnTabSpan").text(len).addClass('index-yj-warn-value');
                //if (len >= 10) {
                //    $("#performanceHighWarnTabDiv").text(result[0].userNameCh + "、" + result[1].userNameCh + "、" + result[2].userNameCh + "、" + result[3].userNameCh + "、" + result[4].userNameCh + "、" + result[5].userNameCh + "、" + result[6].userNameCh + "、" + result[7].userNameCh + "、" + result[8].userNameCh + "、" + result[9].userNameCh);
                //} else {
                //    var strArray = [];
                //    $(result).each(function (i, e) {
                //        strArray.push(result[i].userNameCh);
                //    });
                //    $("#performanceHighWarnTabDiv").text(strArray.join("、"));
                //}
            } else {
                $("#performanceHighWarnTabSpan").text(0).removeClass('index-yj-warn-value');
                //$("#performanceHighWarnTabDiv").text("无");
            }
        }, loadOvertimeEmp: function (result) {
            this.overtimePanel(result);
            if (!_.isEmpty(result)) {//渲染工作生活水平欠佳
                var len = result.length;
                $("#workLifeWarnTabSpan").text(len).addClass('index-yj-warn-value');
                if (len >= 10) {
                    $("#workLifeWarnTabDiv").text(result[0].userNameCh + "、" + result[1].userNameCh + "、" + result[2].userNameCh + "、" + result[3].userNameCh + "、" + result[4].userNameCh + "、" + result[5].userNameCh + "、" + result[6].userNameCh + "、" + result[7].userNameCh + "、" + result[8].userNameCh + "、" + result[9].userNameCh);
                } else {
                    var strArray = [];
                    $(result).each(function (i, e) {
                        strArray.push(result[i].userNameCh);
                    });
                    $("#workLifeWarnTabDiv").text(strArray.join("、"));
                }
            } else {
                $("#workLifeWarnTabSpan").text(0).removeClass('index-yj-warn-value');
                $("#workLifeWarnTabDiv").text("无");
            }
        }, loadPanel: function (orgId) {
            var self = this;
            warnPanel.performanceWarnObj.init();
            var bool = false;
            $.each(warnPageCache, function (i, v) {
                if (v.orgId == orgId) {
                    var data = v.data;
                    self.loadRunOffWarnEmp(data.runOffWarnEmp);
                    self.loadLowPerformance(data.lowPerformance);
                    self.loadHighPerformance(data.highPerformance);
                    //self.loadOvertimeEmp(v.data.overtimeEmp);
                    self.loadDismissPanel(data.dismissRate, data.rateCompate);
                    bool = true;
                    return;
                }
            });
            if (bool) return;
            var data = {};
            $.post(urls.getWarnCountUrl, {organId: orgId}, function (result) {
                data.runOffWarnEmp = result.runOffWarn;
                self.loadRunOffWarnEmp(result.runOffWarn);
                data.dismissRate = result.dismissRate;
                data.rateCompate = result.rateCompate;
                self.loadDismissPanel(result.dismissRate, result.rateCompate);
            });

            $.post(urls.getPerformanceUrl, {organId: orgId}, function (result) {
                data.lowPerformance = result.lowPerfEmp;
                self.loadLowPerformance(result.lowPerfEmp);
                data.highPerformance = result.highPerfEmp;
                self.loadHighPerformance(result.highPerfEmp);
            });

            //$.ajax({
            //    url: urls.getOvertimeEmpUrl,
            //    type: "post",
            //    data: {organId: orgId},
            //    success: function (result) {
            //        data.overtimeEmp = result;
            //        self.loadOvertimeEmp(result);
            //    }
            //});
            warnPageCache.push({orgId: orgId, data: data});
        }, runOffPanel: function (empArr) {
            var detailArr = [];
            $('#riskEmpDetail').empty();
            $.each(empArr, function (i, item) {
                var divmodal = $(getEmpDetailTpl(item));
                $('#riskEmpDetail').append(divmodal);
                $(divmodal).find("img").tooltipZrw({
                    modal: "runoffInfo",
                    data: item,
                    event: "click|mousemove",
                    //	style:"top",
                    callback: function (obj, rsdata) {
                        var empId = rsdata.empId;
                        var talentType = _.isNull(rsdata.keyTalentTypeName) ? '' : rsdata.keyTalentTypeName + '类人才 ';
                        var baseInfoDom = $(obj).find('.base-info');
                        var detailText = $(obj).find('.base-info .row').last().find('span');
                        detailText.empty();
                        $.ajax({
                            url: urls.empBaseInfoUrl,
                            data: {empId: empId},
                            success: function (data) {
                                detailText.eq(0).text(data.userNameCh);
                                detailText.eq(2).text(talentType + data.sequenceName + ' ' + data.abilityName);
                            }
                        });

                        $.ajax({
                            url: urls.empRiskDetailUrl,
                            data: {empId: empId},
                            success: function (data) {
                                if (!_.isEmpty(data)) {
                                    var topRiskInfo = data[0];
                                    var flagClass = 'risk-flag img-circle ' + riskFlagArr[topRiskInfo.riskFlag];
                                    $(obj).find('.base-info .row').last().find('span').eq(1).removeClass().addClass(flagClass);
                                    $(obj).find('.suggest-info div').text(topRiskInfo.note || '');
                                }
                                riskTreeOption.data = data;
                                $(obj).find('.risk-detail-info').children().riskTree(riskTreeOption);
                            }
                        });
                    }
                });
            });
            $('#riskNum').text(empArr.length);
        }, overtimePanel: function (empArr) {
            var detailArr = [];
            $('#workLifeDetail').empty();
            $.each(empArr, function (i, item) {
                var divmodal = $(getEmpWorkOvertimeDetailTpl(item));
                $('#workLifeDetail').append(divmodal);
                $(divmodal).find("img").tooltipZrw({
                    modal: "workLifeInfo",
                    data: item,
                    event: "click|mousemove",
                    callback: function (obj, rsdata) {
                        var empId = rsdata.empId;
                        var talentType = rsdata.keyTalentTypeName;
                        $(obj).find("#total").text(item.totalHour);
                        $.ajax({
                            url: urls.getWorkOvertimeUrl,
                            type: "post",
                            data: {empId: empId},
                            success: function (data) {
                                if (!_.isEmpty(data)) {
                                    var workLifeChart = echarts.init($(obj).find("#workLifeChart")[0]);
                                    var xdata = [];
                                    var ydata = [];
                                    var ymax = 0;
                                    $.each(data, function (i, obj) {
                                        xdata.push(obj.date);
                                        ydata.push(obj.hourCount);
                                        if (ymax < obj.hourCount) {
                                            ymax = obj.hourCount;
                                        }
                                    });
                                    overtimeOption.xAxis[0].data = xdata;
                                    overtimeOption.series[0].data = ydata;
                                    overtimeOption.yAxis[0].max = ((ymax / 2) + 1) * 2;
                                    overtimeOption.yAxis[0].splitNumber = ((ymax / 2) + 1);
                                    workLifeChart.setOption(overtimeOption, true);
                                }

                            }
                        });
                    }
                });
            });
            $('#riskNum').text(empArr.length);
        }, loadDismissPanel: function (rate, compate) {
            var rateVal = Tc.formatFloat(rate * 100);
            var _tabSpan = $('#dismissWarnTabSpan');
            var _txtSpan = $('#dismissWarnTxtSpan');
            _tabSpan.html(rateVal);
            if (compate == 'exceed') {
                _tabSpan.removeClass('index-yj-risk-value index-yj-warn-value');
                _tabSpan.addClass('index-yj-warn-value');
                _txtSpan.html('高于公司紧戒线');
            } else if (compate == 'risk') {
                _tabSpan.removeClass('index-yj-risk-value index-yj-warn-value');
                _tabSpan.addClass('index-yj-risk-value');
                _txtSpan.html('高于公司预警线');
            } else {
                _tabSpan.removeClass('index-yj-risk-value index-yj-warn-value');
                _txtSpan.html('处于公司正常水平');
            }
        }, loadRunOffWarnModel: function (organId) {
            var self = this;
            if (self.runOffOrganId == organId) {
                self.runOffPanel(self.runOffData);
                return true;
            }
            $.post(urls.getRunOffWarnEmpInfoUrl, {organId: organId}, function (result) {
                self.runOffOrganId = organId;
                self.runOffData = result;
                self.runOffPanel(result);
            });
        }
    }
    var warnPageCache = [];//页面预警缓存
    warnPanel.loadPanel(reqOrgId);

    /*
     点击预警显示弹窗的函数
     */
    $(".YJ-DIV").click(function (e) {
        var ariaControls = $(this).attr("aria-controls");
        if (!_.isEmpty(ariaControls)) {

            if (ariaControls == 'runOffWarnTab') {    //离职风险
                warnPanel.loadRunOffWarnModel(reqOrgId);
            }
            if (ariaControls == 'dismissWarnTab') {   //离职率
                if (win.setlocationUrl) {
                    win.setlocationUrl(webRoot + "/accordDismiss/toAccordDismissView");
                }
            }
            $("#showWarningModal a[aria-controls='" + ariaControls + "']").tab('show');
        } else {
            $("#showWarningModal a:first").tab('show');
        }
        $("#showWarningModal").modal("show");
    });

    /*
     初始化表格
     */
    function initGrid(gridId, gridOption, data) {
        $(gridId).jqGrid(gridOption);
        var autoHeight = (data.length + 1) * 43;
        if (autoHeight < 650) {
            $(gridId).setGridHeight(autoHeight);
        }
        $(gridId).clearGridData().setGridParam({
            data: data
        }).trigger("reloadGrid");
        resizeGrid(gridId);
    }

    /*
     重新加载表格
     */
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    /*
     重新封装加载完表格之后的方法
     */
    function extendsLoadComplete(gridId, xhr) {
        var rows = xhr.rows;
        var ids = $(gridId).jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            var col = ids[i];
            var html = ' <a href="javascript:void(0)" data-index="' + i + '" class="profile_col" >人才剖像</a>';
            $(gridId).jqGrid('setRowData', col, {mycz: html});
        }
        $(gridId + ' .grow_col').unbind().on('click', function (e) {
            var _this = $(this);
            var idx = _this.attr('data-index');
            var userObj = rows[idx];
            window.open(webRoot + "/talentProfile/toTalentDetailView.do?empId=" + userObj.empId + "&anchor=growUpDiv")
        });
        $(gridId + ' .profile_col').unbind().on('click', function (e) {
            var _this = $(this);
            var idx = _this.attr('data-index');
            var userObj = rows[idx];
            window.open(webRoot + "/talentProfile/toTalentDetailView.do?empId=" + userObj.empId)
        });
    }


    /*
     查找头像
     */
    function getEmpDetailTpl(data) {
        var img = data.imgPath != '' ? data.imgPath : webRoot + "/assets/photo.jpg";
        var typeName = _.isNull(data.keyTalentTypeName) ? '' : data.keyTalentTypeName;
        var html = '<div class="pull-left text-center"><div class="position-relative">';
        html += '<img class="head-pic img-circle" src="' + img + '" data-src="' + img + '" data-id="' + data.empId + '" data-type="' + typeName + '">';
        if (typeName != '') {
            html += '<span class="mark-talent-type img-circle">' + typeName + '</span>';
        }
        html += '</div><div class="emp-name">' + data.userNameCh + '</div></div>';
        return html;
    }

    /*
     查找头像
     */
    function getEmpWorkOvertimeDetailTpl(data) {
        var src = webRoot + "/assets/photo.jpg";
        return '<div class="pull-left  work-div">' +
            '<div class="position-relative">' +
            '<img class="head-pic img-circle" src="' + src + '" data-src="' + src + '" data-id="' + data.empId + '" data-type="' + data.keyTalentTypeName + '">' +
            '<span class="mark-talent-type-work img-circle">' + data.keyTalentTypeName + '</span>' +
            '<span class="emp-name-work">' + data.userNameCh + '</span>' +
            '<span class="work-over-time">近' + data.week + '周平均加班' + data.avHour + 'h</span>' +
            '</div>' + '</div>';
    }

    /*
     格式化表格图片
     */
    function formatterToImg(cellvalue, options, rowObject) {
        var tempHtml = '<img src="' + (_.isEmpty(cellvalue) ? urls.empDefaultUrl : cellvalue) + '" width="32" height="32" >';
        return tempHtml;
    }

    /*
     跳转
     */
    $("#toPerChangeView").click(function () {//查看过往绩效
        win.setlocationUrl(webRoot + "/perChange/toPerChangeView");
    });
    $("#toTeamImgView").click(function () {//点击团队画像后的跳转
        win.setlocationUrl(webRoot + "/manageHome/toTeamImgView");
    });

    /***
     * 人员剖像
     */
    $('#yjSearchBtn').click(function () {     //点击跳转到人员剖像
        var yjSearchTxt = $.trim($('#yjSearchTxt').val());
        if (yjSearchTxt != '') {
            var url = urls.toTalentProfileUrl + "?keyName=" + yjSearchTxt;
            url = encodeURI(encodeURI(url));
            window.open(url);
        } else {
            window.open(urls.toTalentProfileUrl);
        }
    });
    /*
     人员PK
     */
    $('#talentContrast').find('.ct-circle-add').bind('click', addTalentContrastEmps);
    //人员PK搜索列表
    var searchEmpObj = {
        gridId: '#searchEmpGrid',
        searchTxt: null,
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
                        return '<a href="javascript:void(0)" data-index="' + row.empId + '" class="add_col" >加入</a>';
                    }
                }
            ],
            rownumbers: true,
            rownumWidth: 40,
            loadComplete: function (xhr) {
                var rows = xhr.rows;
                $('.add_col').unbind('click').bind('click', function (e) {
                    var _this = $(this);
                    var _mainChilds = $('#talentContrast').find('.ct-circle-main');
                    var empIds = '';
                    $.each(_mainChilds, function (i, obj) {
                        var _obj = $(obj);
                        empIds += (empIds != '' ? ',' : '') + _obj.data('key');
                    });
                    var empId = _this.attr('data-index');
                    //添加进对比的清单
                    if (empIds.indexOf(empId, 0) != -1) {
                        Messenger().post({
                            message: '该员工已存在对比列表！',
                            type: 'error',
                            hideAfter: 3
                        });
                        return;
                    } else {
                        empIds += empIds != '' ? ',' + empId : empId;
                    }
                    var userObj = _.find(rows, function (row) {
                        return row.empId == empId;
                    });
                    var idx = $('#contrastSearchIndex').val();
                    searchEmpObj.initContrastHtml(userObj, idx);
                    searchEmpObj.empIds = empIds;
                    $('#contrastSearchModal').modal('hide');
                });

                //当最后一页只有一条数据的时候，jqGrid计算的高度不准确，最后一页无法滚动触发加载，现无有好的办法解决
                $("#searchEmpGrid").parent().height(34 * xhr.records);
            },
            scroll: true
        },
        init: function (searchTxt) {
            var self = this;
            if (_.isNull(self.searchTxt)) {
                self.searchTxt = searchTxt;
                self.gridOption.postData = {'keyName': searchTxt};
                $(self.gridId).jqGrid(self.gridOption);
            }
            if (searchTxt != self.searchTxt) {
                self.searchTxt = searchTxt;
                self.initGrid(searchTxt);
            }
        },
        initContrastHtml: function (empObj, index) {
            var imgPath = _.isEmpty(empObj.imgPath) ? urls.empDefaultUrl : empObj.imgPath;
            var mainHtml = '<div data-key="' + empObj.empId + '" class="ct-circle-main"><div  class="ct-circle-del"></div>'
                + '<img class="img-circle img-rc-head" alt="100%x180" src="' + imgPath + '"></div>'
                + '<div class="size-12 img-rc-head-name">' + empObj.userName + '</div>';
            var _currObj = $($('#talentContrast').children()[index]);
            _currObj.html(mainHtml);
            //移除事件
            _currObj.find('.ct-circle-del').bind('click', function () {
                var _this = $(this);
                var keyId = _this.parent().data('key');

                var addHtml = '<div class="ct-circle-add"><div class="ct-circle-add-img"></div>'
                    + '</div><div class="size-12 img-rc-head-name">搜索添加</div>'
                _currObj.html(addHtml);
                _currObj.find('.ct-circle-add').bind('click', addTalentContrastEmps);
            });
        },
        initGrid: function (keyTxt) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                postData: {'keyName': keyTxt}
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            var parentDom = $('#gbox_' + self.gridId.split('#')[1]).parent();
            $(self.gridId).setGridWidth(parentDom.width());
        }
    };

    /*
     版本号
     */
    var version = {
        event: function () {
            $('.versionNumber span').bind('click', function () {
                $('#versionModal').modal('show');
            });
        }
    }
    version.event();

    $('.index .index-rc-btn,.index .index-tdtx-right').click(function () {
        var _mainChilds = $('#talentContrast').find('.ct-circle-main');
        var empIds = '';
        $.each(_mainChilds, function (i, obj) {
            var _obj = $(obj);
            empIds += (empIds != '' ? ',' : '') + _obj.data('key');
        });
        if (empIds != '') {
            window.open(urls.toTalentContrastUrl + '?empIds=' + empIds);
        } else {
            window.open(urls.toTalentContrastUrl);
        }
    });

    /*
     获取拖拽配置
     */
    $.get(urls.getUserHomeConfigUrl, function (rs) {
        $(document).drag({
            data: rs,
            url: "index"
        });
    });

    function addTalentContrastEmps() {
        var _this = $(this);
        var _parent = _this.parent();
        var idx = _parent.index();
        $('#contrastSearchIndex').val(idx);

        var _modal = $('#contrastSearchModal');
        _modal.find('.modal-content').css('margin-top', win.pageYOffset);
        _modal.modal('show');

        $('#contrastSearchBtn').click(function () {
            var searchTxt = $.trim($('#contrastSearchTxt').val());
            if (!_.isEmpty(searchTxt)) {
                searchEmpObj.init(searchTxt);
            }
        });
        $('#contrastSearchTxt').keydown(function (e) {
            if (e.keyCode == 13) {
                var searchTxt = $.trim($(this).val());
                if (!_.isEmpty(searchTxt)) {
                    searchEmpObj.init(searchTxt);
                }
            }
        });
    }

    /*
     格式化表格时间
     */
    function formatterToDate(cellvalue, options, rowObject) {
        return moment(cellvalue).format('YYYY-MM-DD');
    }

});

