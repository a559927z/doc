require(['jquery', 'moment', 'echarts', 'echarts/chart/funnel', 'echarts/chart/line', 'echarts/chart/bar', 'echarts/chart/pie', 'bootstrap', 'underscore',
    'utils', 'unveil', 'messenger', 'jgGrid', 'ztree', 'riskTree', 'tooltipZrw', 'resize', "jquery-ui", "jquery-drag", "jquery-mCustomScrollBar"
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

    var treeSelector = null;

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var ecConfig = require('echarts/config');
    var TextShape = require('zrender/shape/Text');

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
        teamImgAbObj.getRequestData(organId);
        performanceObj.Init(organId);
        //talentDevelObj.getRequestData(organId);
        //$(orgTreeGridObj.treeGridId).clearGridData();
        orgTreeGridObj.first = false;
        orgTreeGridObj.init(organId);
        gainOfLossObj.init(organId);
    }

    /*
     * teamImg
     * */
    var teamImgAbOpt = {
        color: ['#0B98E1', '#00BDA9', '#08B560', '#88C000', '#EFA100', '#E80606', '#FF0084', '#AE00E0', '#732DF6', '#2341F3'],
//        series : [
//            {
//                type:'funnel',
//                y : '10%',
//                sort : 'ascending',
//                itemStyle: {
//                    normal: {
//                        opacity: 0.7,
//                        label: {
//                            position:'right',
//                            formatter: '{b}'
//                        }
//                    }
////                    ,emphasis: {
////                        label: {
////                            show:true,
////                            position:'inside',
////                            formatter: '{c}',
////                            textStyle: {
////                                color: '#fff'
////                            }
////                        }
////                    }
//                },
//                data : []
//            }
//        ]
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
            self.style();
        },
        initData: function (organId) {
            var self = this;
            if (_.isNull(self.resultData)) {
                self.getRequestData(organId);
                return;
            }
            var sexPerData = self.resultData[0], yearsData = self.resultData[1], marryData = self.resultData[2], abilityData = self.resultData[3];

            //teamImg开始渲染数据
            teamImgAbOpt.series[0].data = [];
            //teamImgAbOpt.series[1].data = [];
            if (!_.isEmpty(abilityData)) {
                $.each(abilityData, function (a, b) {
                    teamImgAbOpt.series[0].data.push({value: b.k, name: b.v});
                });
            }

            self.funnelObj.setOption(teamImgAbOpt, true);
            $("#totalPer").text(self.resultData[4]);
            if(!_.isNull(sexPerData.v)){
                $('#teamImgSex').show();
                $('#teamImgSexK').text(sexPerData.k);
                $('#teamImgSexV').text(sexPerData.v);
            }else{
                $('#teamImgSex').hide();
            }
            if(!_.isNull(yearsData.v)){
                $('#teamImgYear').show();
                $('#teamImgYearK').text(yearsData.k);
                $('#teamImgYearV').text(yearsData.v);
            }else{
                $('#teamImgYear').hide();
            }
            if(!_.isNull(marryData.v)){
                $('#teamImgMarry').show();
                $('#teamImgMarryK').text(marryData.k);
                $('#teamImgMarryV').text(marryData.v);
            }else{
                $('#teamImgMarry').hide();
            }
        },
        getRequestData: function (organId) {
            var self = this;
            $.post(urls.getTeamImgAbUrl, {'organId': organId}, function (rs) {
                self.resultData = rs;
                self.initData(organId);
            });
        },
        style: function () {
            //var headWidth = $(".teamImg-head").width(),btnWidth = (headWidth / 3) - 35,_marginLeft = 20;
            //$(".minInfo").css({width : btnWidth+"px", marginLeft : _marginLeft + "px"});
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
        }, initTreeGrid: function (organId, gridId, options) {
            //
            var self = this;
            if (self.first) {
                jQuery(gridId).jqGrid(options);
            } else {
                if (gridId == self.modalTreeGridId) {
                    //	jQuery(gridId).jqGrid(options);
                    if (self.firstModal) {
                        self.firstModal = false;
                        options.postData = {nodeid: organId};
                        jQuery(gridId).jqGrid(options);
                    } else {
                        jQuery(gridId).clearGridData().setGridParam({
                            postData: {nodeid: reqOrgId}
                        }).trigger("reloadGrid");
                    }
                } else {
                    jQuery(gridId).clearGridData().setGridParam({
                        postData: {nodeid: organId}
                    }).trigger("reloadGrid");
                }

            }
            self.first = true;
            $("#treegrid").setGridWidth($("#index-son-grid-body").width());
        }
    };
    orgTreeGridObj.init(reqOrgId);
    //查看组织架构图
    $("#gotoOrgView").click(function () {
        $(this).attr("href", webRoot + "/orgChart/toOrgView" + "?organId=" + reqOrgId);
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
     当季人才损益
     */
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

            self.requestData(organId);

            $('#personnel').click(function () {
                setUpWindow("#gainOfLossModal");
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
                if (_.isNull(rs)) {
                    return;
                }
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
            var entryOption = _.clone(gridOption);
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
            var dimisstionOption = _.clone(gridOption);
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
            var option = _.clone(gridOption);
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

                    return "<a href='javascript:void(0)' data='" + value + "'  class='growUp_col' style='margin:0 5px;'>成长轨迹</a>" +
                        "<a href='javascript:void(0)' data='" + value + "' class='talent_col' >人才剖象</a>";
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
                //window.location.href = webRoot + '/role/roleFunction?roleId='+empId;
                //document.getElementById('showPerformanceWarningFrm').src = herf;
                //$("#showPerformanceWarningModal").modal("show");
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
                $("#highNum").text(h_data.length);
                $(this.HPId).clearGridData().setGridParam({
                    data: h_data
                }).trigger("reloadGrid");
                this.resizeGrid();
            },
            initLPGrid: function (l_data) {
                $("#lowNum").text(l_data.length);
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
                //if (len >= 10) {
                //    $("#workLifeWarnTabDiv").text(result[0].userNameCh + "、" + result[1].userNameCh + "、" + result[2].userNameCh + "、" + result[3].userNameCh + "、" + result[4].userNameCh + "、" + result[5].userNameCh + "、" + result[6].userNameCh + "、" + result[7].userNameCh + "、" + result[8].userNameCh + "、" + result[9].userNameCh);
                //} else {
                //    var strArray = [];
                //    $(result).each(function (i, e) {
                //        strArray.push(result[i].userNameCh);
                //    });
                //    $("#workLifeWarnTabDiv").text(strArray.join("、"));
                //}
            } else {
                $("#workLifeWarnTabSpan").text(0).removeClass('index-yj-warn-value');
                //$("#workLifeWarnTabDiv").text("无");
            }
        },
        loadPanel: function (orgId) {
            var self = this;
            warnPanel.performanceWarnObj.init();
            var bool = false;
            $.each(warnPageCache, function (i, v) {
                if (v.orgId == orgId) {
                    self.loadRunOffWarnEmp(v.data.runOffWarnEmp);
                    //self.loadLowPerformance(v.data.lowPerformance);
                    self.loadHighPerformance(v.data.highPerformance);
                    //self.loadOvertimeEmp(v.data.overtimeEmp);
                    self.loadDismissPanel(data.dismissRate, data.rateCompate);
                    bool = true;
                    return;
                }
            });
            if (bool)
                return;
            var data = {};
            $.post(urls.getWarnCountUrl, {organId: orgId}, function (result) {
                data.runOffWarnEmp = result.runOffWarn;
                self.loadRunOffWarnEmp(result.runOffWarn);
                data.dismissRate = result.dismissRate;
                data.rateCompate = result.rateCompate;
                self.loadDismissPanel(result.dismissRate, result.rateCompate);
            });

            $.post(urls.getPerformanceUrl, {organId: orgId}, function (result) {
                //data.lowPerformance = result.lowPerfEmp;
                //self.loadLowPerformance(result.lowPerfEmp);
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
                        var talentType = rsdata.keyTalentTypeName;
                        var baseInfoDom = $(obj).find('.base-info');
                        var detailText = $(obj).find('.base-info .row').last().find('span');
                        detailText.empty();
                        $.ajax({
                            url: urls.empBaseInfoUrl,
                            data: {empId: empId},
                            success: function (data) {
                                detailText.eq(0).text(data.userNameCh);
                                detailText.eq(2).text(talentType + '类人才 ' + data.sequenceName + ' ' + data.abilityName);
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
        },loadDismissPanel: function (rate, compate) {
            var rateVal = (rate * 100).toFixed(2);
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
        },loadRunOffWarnModel: function (organId) {
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
        setUpWindow("#showWarningModal");
        $("#showWarningModal").modal("show");
    });

    /*
    初始化弹框位置
     */
    function setUpWindow(obj){
        if(parent.document != undefined && parent.document != null){
            var t = parent.document.documentElement.scrollTop || parent.document.body.scrollTop;
            $(obj).css("marginTop", t - 0 + "px");
        }
    }

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
            data: data,
            rowNum: 9999999
        }).trigger("reloadGrid");
        resizeGrid(gridId);
    }

    /*
     团队提醒的弹出框
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
        scroll: true
    };

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
            var html = '<a href="javascript:void(0)" data-index="' + i + '" class="grow_col" >成长轨迹</a>'
                + ' <a href="javascript:void(0)" data-index="' + i + '" class="profile_col" >人才剖像</a>';
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
        var src = data.imgPath != '' ? data.imgPath : webRoot + "/assets/photo.jpg";
        var typeName = _.isNull(data.keyTalentTypeName) ? '' : data.keyTalentTypeName;

        var html = '<div class="pull-left  work-div"><div class="position-relative">'
            + '<img class="head-pic img-circle" src="' + src + '" data-src="' + src + '" data-id="' + data.empId + '" data-type="' + typeName + '">';
        if (typeName != '') {
            html += '<span class="mark-talent-type-work img-circle">' + typeName + '</span>';
        }
        html += '<span class="emp-name-work">' + data.userNameCh + '</span>';
        html += '<span class="work-over-time">近' + data.week + '周平均加班' + data.avHour + 'h</span>';
        html += '</div></div>';
        return html;
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
        window.open(webRoot + "/perChange/toPerChangeView" + "?organId=" + reqOrgId);
    });
    $("#toTeamImgView").click(function () {//点击团队画像后的跳转
        window.open(webRoot + "/manageHome/toTeamImgView" + "?organId=" + reqOrgId);
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

    $(window).keydown(function (e) {
        if (e.keyCode == 13) {
            //假如焦点在文本框上,则获取文本框的值
            if (document.activeElement.id == 'contrastSearchTxt') {
                var searchTxt = $.trim($('#contrastSearchTxt').val());
                if (!_.isEmpty(searchTxt)) {
                    searchEmpObj.init(searchTxt);
                }
            }
        }
    });
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
        //可见屏幕/2 - 滚动高度 - 弹出层高度
        var marginTop = window.screen.availHeight / 2 + win.pageYOffset - 280;
        _modal.find('.modal-content').css('margin-top', marginTop);
        _modal.modal('show');

        $('#contrastSearchBtn').click(function () {
            var searchTxt = $.trim($('#contrastSearchTxt').val());
            if (!_.isEmpty(searchTxt)) {
                searchEmpObj.init(searchTxt);
            }
        });
    }

    /*
     格式化表格时间
     */
    function formatterToDate(cellvalue, options, rowObject) {
        return moment(cellvalue).format('YYYY-MM-DD');
    }

    //固定模态窗口
    //$("#mainFrame").css("height","1000px");
    //$.zrw_resizeFrameSize();
//    $.zrw_resizeModal("talentDevelModal");
//    $("#remindModal").css("top","10px");
//    $.zrw_resizeModal("abInfoModal");
//    $.zrw_resizeModal("orgTreeGridModal");

});

