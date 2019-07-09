/**
 * Created by qpzhu on 16/03/02 .
 */
require(['bootstrap', 'jgGrid', 'underscore', 'messenger'], function () {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        toTalentSearchView: webRoot + '/talentSearch/toTalentSearchView.do',	//跳到员工搜索初始化页面
        talentSearchBox: webRoot + '/common/getTalentSearchBox.do',	//筛选条件信息
        findEmpUrl: webRoot + '/talentSearch/findEmpAdvancedAll.do',   //查询所有的员工信息
        searchOrgan: webRoot + "/talentSearch/getTalentSearchOrgan",//组织架构
        toEmpDetailUrl: webRoot + '/talentSearch/toTalentDetailView.do',    //跳转到员工详情
        toResultExport: webRoot + '/talentSearch/toResultExport.do'//导出数据
    };

    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 2
        });
    };

    var resetHeight = function (height) {
        if (!height) {
            height = window.parent.$("iframe[name='mainFrame']").data("height");
        }
        window.parent.$("iframe[name='mainFrame']").css({"height": height - 50 + "px"});

        $("#tsa .bodytitle").css({"width": $("#tsa .bodycontent").width() + 10 + "px"});
    }

    win.resizeObject = function (height) {
        resetHeight(height);
    }

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;

    win.organizationObject = function (organId) {
        reqOrgId = organId;
    }
    /*
     * 初始化搜索条件
     */
    var searchCondition = {
        initData: function () {
            $.post(urls.talentSearchBox, function (result) {
                $.each(result, function (i, item) {
                    //个人信息
                    if (i <= 3) {
                        var html = "";
                        if (i == 3) {
                            html = '<div class="row optional"><div class="col-xs-12 border-bottom-no"><label class="rowtitle">' + item.name + '：</label><span class="conditionAll "  id=' + item.id + '>全部</span><img class="split">';
                        } else {
                            html = '<div class="row optional"><div class="col-xs-12 border-bottom"><label class="rowtitle">' + item.name + '：</label><span class="conditionAll "  id=' + item.id + '>全部</span><img class="split">';
                        }
                        $.each(item.childs, function (i, value) {
                            html = html + '<span class="condition" id=' + value.id + ' data-id=' + value.id + ' data-name=' + value.name + '>' + value.name + '</span>';
                        });
                        html = html + '</div></div>'
                        $('#personalInformation').append(html);
                        $.each(item.childs, function (i, value) {
                            $("#" + value.id).unbind("click").click(function () {
                                if ($(this).hasClass("condition-click")) {
                                    $(this).removeClass("condition-click");
                                } else {
                                    $(this).removeClass("condition-click").addClass("condition-click");
                                }
                                var checkAll = true;
                                $.each($(this).parent().children(".condition"), function () {
                                    if (!$(this).hasClass("condition-click")) {
                                        checkAll = false;
                                        return;
                                    }
                                });
                                if (checkAll) {
                                    $(this).parent().children(".conditionAll").addClass("condition-click");
                                } else {
                                    $(this).parent().children(".conditionAll").removeClass("condition-click");
                                }
                            });
                        });
                    }
                    //职位信息
                    if (i >= 4 && i <= 7) {
                        var html = "";
                        if (item.id == "sequenceSubId" || item.id == "JobTitleId") {
                            html = '<div class="row optional hide"><div class="col-xs-12 border-bottom"><label class="rowtitle">' + item.name + '：</label><span class="conditionAll " id=' + item.id + '>全部</span><img class="split">';
                            $.each(item.childs, function (i, value) {
                                if (item.id == "sequenceSubId" || item.id == "JobTitleId")
                                    html = html + '<span class="condition hide" id=' + value.id + ' pid=' + value.bindObj + ' data=' + value.bindData + ' data-id=' + value.id + ' data-name=' + value.name + '>' + value.name + '</span>';
                            });
                        } else {
                            if (i == 6) {
                                html = '<div class="row optional"><div class="col-xs-12 border-bottom-no"><label class="rowtitle">' + item.name + '：</label><span class="conditionAll " id=' + item.id + '>全部</span><img class="split">';
                            } else {
                                html = '<div class="row optional"><div class="col-xs-12 border-bottom"><label class="rowtitle">' + item.name + '：</label><span class="conditionAll " id=' + item.id + '>全部</span><img class="split">';
                            }
                            $.each(item.childs, function (i, value) {
                                html = html + '<span class="condition" id=' + value.id + ' data-id=' + value.id + ' data-name=' + value.name + '>' + value.name + '</span>';
                            });
                        }
                        html = html + '</div></div>';
                        $('#positionInformation').append(html);
                        $.each(item.childs, function (i, value) {
                            $("#" + value.id).unbind("click").click(function () {
                                if ($(this).hasClass("condition-click")) {
                                    $(this).removeClass("condition-click");
                                    if (item.id == "sequenceId") {
                                        $.each($("#sequenceSubId").parent().find("span"), function () {
                                            $(this).removeClass("condition-click");
                                        });
                                        var seqSelectCount = $("#sequenceId").parent().find(".condition-click").length;
                                        if (seqSelectCount > 1 || seqSelectCount == 0) {
                                            $("#sequenceSubId").parent().parent().addClass("hide");
                                        } else {
                                            var sequenceId = $("#sequenceId").parent().find(".condition-click").attr("id");
                                            $.each($("#sequenceSubId").parent().find("span[pid]"), function () {
                                                if (!$(this).hasClass("hide")) {
                                                    $(this).addClass("hide");
                                                }
                                                if ($(this).attr("pid") == sequenceId) {
                                                    $(this).parent().parent().removeClass("hide");
                                                    $(this).removeClass("hide");

                                                    var checkAll = true;
                                                    $.each($(this).parent().children(".condition"), function () {
                                                        if (!$(this).hasClass("condition-click") && !$(this).hasClass("hide")) {
                                                            checkAll = false;
                                                            return;
                                                        }
                                                    });
                                                    if (checkAll) {
                                                        $(this).parent().children(".conditionAll").addClass("condition-click");
                                                    } else {
                                                        $(this).parent().children(".conditionAll").removeClass("condition-click");
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    if (item.id == "abilityId") {
                                        $.each($("#JobTitleId").parent().find("span"), function () {
                                            $(this).removeClass("condition-click");
                                        });
                                        var SelectCount = $("#abilityId").parent().find(".condition-click").length;
                                        if (SelectCount > 1 || SelectCount == 0) {
                                            $("#JobTitleId").parent().parent().addClass("hide");
                                            $("#abilityId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                        } else {
                                            $("#abilityId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                            $("#JobTitleId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                            var abilityId = $("#abilityId").parent().find(".condition-click").attr("id");
                                            $.each($("#JobTitleId").parent().find("span[pid]"), function () {
                                                if (!$(this).hasClass("hide")) {
                                                    $(this).addClass("hide");
                                                }
                                                if ($(this).attr("pid") == abilityId) {
                                                    $(this).parent().parent().removeClass("hide");
                                                    $(this).removeClass("hide");

                                                    var checkAll = true;
                                                    $.each($(this).parent().children(".condition"), function () {
                                                        if (!$(this).hasClass("condition-click") && !$(this).hasClass("hide")) {
                                                            checkAll = false;
                                                            return;
                                                        }
                                                    });
                                                    if (checkAll) {
                                                        $(this).parent().children(".conditionAll").addClass("condition-click");
                                                    } else {
                                                        $(this).parent().children(".conditionAll").removeClass("condition-click");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                } else {
                                    $(this).removeClass("condition-click").addClass("condition-click");
                                    if (item.id == "sequenceId") {
                                        $.each($("#sequenceSubId").parent().find("span"), function () {
                                            $(this).removeClass("condition-click");
                                        });
                                        var seqSelectCount = $("#sequenceId").parent().find(".condition-click").length;
                                        if (seqSelectCount > 1) {
                                            $("#sequenceSubId").parent().parent().addClass("hide");
                                            $("#abilityId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                        } else {
                                            $.each($("#sequenceSubId").parent().find("span[pid]"), function () {
                                                if (!$(this).hasClass("hide")) {
                                                    $(this).addClass("hide");
                                                }
                                                if ($(this).attr("pid") == value.id) {
                                                    $(this).parent().parent().removeClass("hide");
                                                    $(this).removeClass("hide");

                                                    var checkAll = true;
                                                    $.each($(this).parent().children(".condition"), function () {
                                                        if (!$(this).hasClass("condition-click") && !$(this).hasClass("hide")) {
                                                            checkAll = false;
                                                            return;
                                                        }
                                                    });
                                                    if (checkAll) {
                                                        $(this).parent().children(".conditionAll").addClass("condition-click");
                                                    } else {
                                                        $(this).parent().children(".conditionAll").removeClass("condition-click");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    if (item.id == "abilityId") {
                                        $.each($("#JobTitleId").parent().find("span"), function () {
                                            $(this).removeClass("condition-click");
                                        });
                                        var SelectCount = $("#abilityId").parent().find(".condition-click").length;
                                        if (SelectCount > 1) {
                                            $("#JobTitleId").parent().parent().addClass("hide");
                                            $("#abilityId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                        } else {
                                            $("#abilityId").parent().removeClass("border-bottom-no").addClass("border-bottom");
                                            $("#JobTitleId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                            $.each($("#JobTitleId").parent().find("span[pid]"), function () {
                                                if (!$(this).hasClass("hide")) {
                                                    $(this).addClass("hide");
                                                }
                                                if ($(this).attr("pid") == value.id) {
                                                    $(this).parent().parent().removeClass("hide");
                                                    $(this).removeClass("hide");

                                                    var checkAll = true;
                                                    $.each($(this).parent().children(".condition"), function () {
                                                        if (!$(this).hasClass("condition-click") && !$(this).hasClass("hide")) {
                                                            checkAll = false;
                                                            return;
                                                        }
                                                    });
                                                    if (checkAll) {
                                                        $(this).parent().children(".conditionAll").addClass("condition-click");
                                                    } else {
                                                        $(this).parent().children(".conditionAll").removeClass("condition-click");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                                var checkAll = true;
                                $.each($(this).parent().children(".condition"), function () {
                                    if (!$(this).hasClass("condition-click") && !$(this).hasClass("hide")) {
                                        checkAll = false;
                                        return;
                                    }
                                });
                                if (checkAll) {
                                    $(this).parent().children(".conditionAll").addClass("condition-click");
                                } else {
                                    $(this).parent().children(".conditionAll").removeClass("condition-click");
                                }
                            });
                        });
                    }
                    //绩效
                    if (i >= 8 && i <= 10) {
                        if (i == 8) {
                            $.each(item.childs, function (key, value) {
                                $('#selectTimeOne').append($("<option/>", {
                                    value: value.name,
                                    text: value.name
                                }));
                            });

                            $.each(item.childs, function (key, value) {
                                $('#selectTimeTwo').append($("<option/>", {
                                    value: value.name,
                                    text: value.name
                                }));
                            });
                        }

                        if (i == 9) {
                            $.each(item.childs, function (i, value) {
                                var name = value.name;
                                if (i == 0) {
                                    $("#selectTimeTwo").val(name.substr(0, 4));
                                    if (name.substr(4, 6) == "06") {
                                        $("#selectTimeUpTwo").removeClass("condition-click").addClass("condition-click");
                                    } else {
                                        $("#selectTimeDownTwo").removeClass("condition-click").addClass("condition-click");
                                    }
                                }
                                if (i == 1) {
                                    $("#selectTimeOne").val(name.substr(0, 4));
                                    if (name.substr(4, 6) == "06") {
                                        $("#selectTimeUpOne").removeClass("condition-click").addClass("condition-click");
                                    } else {
                                        $("#selectTimeDownOne").removeClass("condition-click").addClass("condition-click");
                                    }
                                }
                            });

                        }

                        if (i == 10) {
                            var html = '<div class="row optional"><div class="col-xs-12 border-bottom-no"><label class="rowtitle">' + item.name + '：</label><span class="conditionAll "  id=' + item.id + '>全部</span><img class="split">';
                            $.each(item.childs, function (i, value) {
                                html = html + '<span class="condition" id=' + value.bindObj + ' data-id=' + value.id + ' data-name=' + value.name + '>' + value.name + '</span>';
                            });
                            html = html + '</div></div>'
                            $('#achievements').append(html);
                            $.each(item.childs, function (i, value) {
                                $("#" + value.bindObj).unbind("click").click(function () {
                                    if ($(this).hasClass("condition-click")) {
                                        $(this).removeClass("condition-click");
                                    } else {
                                        $(this).removeClass("condition-click").addClass("condition-click");
                                    }
                                    var checkAll = true;
                                    $.each($(this).parent().children(".condition"), function () {
                                        if (!$(this).hasClass("condition-click")) {
                                            checkAll = false;
                                            return;
                                        }
                                    });
                                    if (checkAll) {
                                        $(this).parent().children(".conditionAll").addClass("condition-click");
                                    } else {
                                        $(this).parent().children(".conditionAll").removeClass("condition-click");
                                    }
                                });
                            });
                        }
                    }
                    //全选控制
                    $("#" + item.id).unbind("click").click(function () {

                        if ($(this).hasClass("condition-click")) {
                            $.each($(this).parent().find(".condition"), function () {
                                $(this).removeClass("condition-click");
                            });
                            $.each($(this).parent().find(".conditionAll"), function () {
                                $(this).removeClass("condition-click");
                            });
                        } else {
                            $.each($(this).parent().find(".condition"), function () {
                                if (!$(this).hasClass("hide"))
                                    $(this).removeClass("condition-click").addClass("condition-click");
                                if (item.id == "sequenceId") {
                                    $("#sequenceSubId").parent().parent().addClass("hide");
                                }
                                if (item.id == "abilityId") {
                                    $("#JobTitleId").parent().parent().addClass("hide");
                                    $("#JobTitleId").parent().removeClass("border-bottom").addClass("border-bottom-no");
                                }

                            });
                            $.each($(this).parent().find(".conditionAll"), function () {
                                $(this).removeClass("condition-click").addClass("condition-click");
                            });
                        }
                    });
                });
                resetHeight();
            });
        }
    }
    //绩效控制上下半年选择
    searchCondition.initData();
    $("#selectTimeUpOne").unbind("click").click(function () {
        if ($(this).hasClass("condition-click")) {
            $(this).removeClass("condition-click");
            $("#selectTimeDownOne").addClass("condition-click");
        } else {
            $(this).removeClass("condition-click").addClass("condition-click");
            $("#selectTimeDownOne").removeClass("condition-click");
        }
    });
    $("#selectTimeDownOne").unbind("click").click(function () {
        if ($(this).hasClass("condition-click")) {
            $(this).removeClass("condition-click");
            $("#selectTimeUpOne").addClass("condition-click");
        } else {
            $(this).removeClass("condition-click").addClass("condition-click");
            $("#selectTimeUpOne").removeClass("condition-click");
        }
    });
    $("#selectTimeUpTwo").unbind("click").click(function () {
        if ($(this).hasClass("condition-click")) {
            $(this).removeClass("condition-click");
            $("#selectTimeDownTwo").addClass("condition-click");
        } else {
            $(this).removeClass("condition-click").addClass("condition-click");
            $("#selectTimeDownTwo").removeClass("condition-click");
        }
    });
    $("#selectTimeDownTwo").unbind("click").click(function () {
        if ($(this).hasClass("condition-click")) {
            $(this).removeClass("condition-click");
            $("#selectTimeUpTwo").addClass("condition-click");
        } else {
            $(this).removeClass("condition-click").addClass("condition-click");
            $("#selectTimeUpTwo").removeClass("condition-click");
        }
    });

    $(".panel-heading").unbind("click").click(function () {
        if ($(this).find("i").hasClass("icon-angle-down")) {
            $(this).find("i").removeClass("icon-angle-down").addClass("icon-angle-right");
        } else {
            $(this).find("i").removeClass("icon-angle-right").addClass("icon-angle-down");
        }
    });


    //初始化'加入对比'
    var stackObject;

    function loadStack() {
        var win = window != top ? top.window : window;
        if (win.stackObject) {
            stackObject = win.stackObject;
        }
        if (stackObject && stackObject.bottomStock('getPersonIds') != '')
            stackObject.bottomStock('hideFrame', 0);
        else
            stackObject.bottomStock('hideFrame', 1);
    }

    loadStack();

    var gridOption = {
        url: urls.findEmpUrl,
        datatype: 'json',
        postData: {},
        mtype: 'POST',
        autowidth: true,
        height: 355,
        colNames: ['id', '名称', '所属机构', '岗位', '序列', '职位层级', '操作'],
        colModel: [
            {name: 'empId', index: 'empId', hidedlg: true, hidden: true, sortable: false, width: 40},
            {
                name: 'userNameCh',
                index: 'userNameCh',
                editable: true,
                sortable: false,
                width: 80,
                editoptions: {size: "20", maxlength: "30"}
            },
            {
                name: 'organizationName',
                index: 'organizationName',
                sortable: false,
                width: 100,
                editable: true,
                editoptions: {size: "20", maxlength: "30"}
            },
            {
                name: 'positionName',
                index: 'positionName',
                width: 80,
                sortable: false,
                editable: true,
                align: 'center',
                editoptions: {size: "20", maxlength: "5"}
            },
            {
                name: 'sequenceName',
                index: 'sequenceName',
                width: 80,
                sortable: false,
                editable: true,
                align: 'center',
                editoptions: {size: "20", maxlength: "30"}
            },
            {
                name: 'abilityName',
                index: 'abilityName',
                width: 80,
                sortable: false,
                editable: true,
                align: 'center',
                editoptions: {size: "20", maxlength: "30"}
            },
            {
                name: 'myac',
                index: '',
                width: 200,
                fixed: true,
                align: 'center',
                sortable: false,
                resize: false,
                search: false
            }
        ],
        hideCol: ['userKey'],
        rownumbers: true,
        rownumWidth: 50,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#searchGridPager",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        altRows: true,
        loadComplete: function (xhr) {
            var rows = xhr.rows;
            var gridSelector = searchGridObj.gridId;
            var ids = $(gridSelector).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var col = ids[i];
                var html = '<a href="javascript:void(0)" target="_blank" data-index="' + i + '" class="emp_col" >人才剖象</a>';
                var html = html + '&nbsp;&nbsp;<a href="javascript:void(0)" data-index="' + i + '" class="pk" >加入对比</a>';
                $(gridSelector).jqGrid('setRowData', col, {myac: html});
            }
            $('.emp_col').unbind().bind('click', function (e) {
                var _this = $(this);
                var idx = _this.attr('data-index');
                var userObj = rows[idx];
                window.open(urls.toEmpDetailUrl + '?empId=' + userObj.empId + '&rand=' + Math.random());
            });
            $('.pk').unbind().bind('click', function (e) {
                var _this = $(this);
                var idx = _this.attr('data-index');
                var userObj = rows[idx];
                if (stackObject) {
                    stackObject.bottomStock('hideFrame', 0);
                    stackObject.bottomStock('addPerson', userObj.empId);
                } else {
                    showErrMsg("stackObject object is not exists!");
                }
            });

            var tableHeight = 0;
            var iFrameHeight = 0;
            if ($("#searchGridTable").data("tableHeight") == undefined) {
                tableHeight = $("#searchGridTable").height();
                $("#searchGridTable").data("tableHeight", tableHeight);
            } else {
                tableHeight = $("#searchGridTable").data("tableHeight");
            }
            if ($("#searchGridTable").data("iFrameHeight") == undefined) {
                iFrameHeight = $("#searchGridTable").parents("body").height();
                $("#searchGridTable").data("iFrameHeight", iFrameHeight);
            } else {
                iFrameHeight = $("#searchGridTable").data("iFrameHeight")
            }
            //window.parent.$("iframe").css({"height":$("#searchGridTable").height()-tableHeight+iFrameHeight});
            $(".ui-jqgrid-bdiv").css({"height": $("#searchGridTable").height() + 1 + "px"});

            $(".ui-jqgrid-htable,#searchGridTable").css({"width": $("#searchGridPanel").width() + "px"});
        },
        gridComplete: function () {
            window.scrollTo(0, $("#searchGridTable_rn").offset().top);
        }
    };

    var conditionMap = {};

    var searchGridObj = {
        jqGrid: null,
        gridId: '#searchGridTable',
        init: function (conditionMap) {
            var self = this;
            if (self.jqGrid) {
                self.jqGrid.clearGridData().setGridParam({
                    postData: {conditionMap: JSON.stringify(conditionMap)}
                }).trigger("reloadGrid");
            } else {
                gridOption.postData = {conditionMap: JSON.stringify(conditionMap)}
                self.jqGrid = $(self.gridId).jqGrid(gridOption);
            }
        }
    }

    $(function () {
        $('#firstSearchBtn').click(function () {
            initPage(getCondition());
        });

        function getCondition() {
            var searchTxt = $.trim($('#searchTxt').val());
            //员工类型
            var empType = '';
            $.each($("#empTypeId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-id") != undefined)
                    empType += $(this).attr("data-id") + ',';
            });
            //年龄
            var ageType = '';
            $.each($("#ageId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-id") != undefined)
                    ageType += $(this).attr("data-id") + ',';
            });

            //性别
            var sexType = '';
            $.each($("#sexId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-id") != undefined)
                    sexType += $(this).attr("data-id") + ',';
            });

            //学历
            var eduType = '';
            $.each($("#eduId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-name") != undefined)
                    eduType += $(this).attr("data-name") + ',';
            });

            var organizationId = reqOrgId;

            //职位主序列
            var sequenceType = '';
            $.each($("#sequenceId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-id") != undefined)
                    sequenceType += $(this).attr("data-id") + ',';
            });

            //职位子序列
            var sequenceSubType = '';
            $.each($("#sequenceSubId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-id") != undefined && !$(this).hasClass("hide"))
                    sequenceSubType += $(this).attr("data-id") + ',';
            });

            //职位层级
            var abilityIdType = '';
            $.each($("#abilityId").parent().find(".condition-click"), function () {
                if ($(this).attr("data-id") != undefined)
                    abilityIdType += $(this).attr("data-id") + ',';
            });

            //职衔
            var JobTitleType = '';
            $.each($("#JobTitleId").parent().find(".condition-click"), function () {
                if ($(this).attr("data") != undefined && !$(this).hasClass("hide"))
                    JobTitleType += $(this).attr("data") + ',';
            });

            //获取绩效选择时间
            var selectTimeOne = $("#selectTimeOne").val();
            if ($("#selectTimeUpOne").hasClass("condition-click")) {
                selectTimeOne = selectTimeOne + "06";
            } else if ($("#selectTimeDownOne").hasClass("condition-click")) {
                selectTimeOne = selectTimeOne + "12";
            }
            var selectTimeTwo = $("#selectTimeTwo").val();
            if ($("#selectTimeUpTwo").hasClass("condition-click")) {
                selectTimeTwo = selectTimeTwo + "06";
            } else if ($("#selectTimeDownTwo").hasClass("condition-click")) {
                selectTimeTwo = selectTimeTwo + "12";
            }
            if (selectTimeTwo < selectTimeOne) {
                showErrMsg("绩效结束时间不能大于开始时间！");
                return;
            }

            //绩效
            var performance = '';
            $.each($("#performanceKey").parent().find(".condition-click"), function () {
                if ($(this).attr("id") != undefined)
                    performance += $(this).attr("id") + ',';
            });

            //获取其他信息数据
            var pastHistory = $.trim($('#pastHistory').val());
            var projectExperience = $.trim($('#projectExperience').val());
            var trainingExperience = $.trim($('#trainingExperience').val());

            var conditionMap = {
                keyName: searchTxt,
                empTypeArray: empType,
                ageArray: ageType,
                sexArray: sexType,
                eduArray: eduType,
                organizationId: organizationId,
                sequenceArray: sequenceType,
                sequenceSubArray: sequenceSubType,
                abilityIdArray: abilityIdType,
                JobTitleArray: JobTitleType,
                selectTimeOne: selectTimeOne,
                selectTimeTwo: selectTimeTwo,
                performanceArray: performance,
                pastHistory: pastHistory,
                projectExperience: projectExperience,
                trainingExperience: trainingExperience
            };
            return conditionMap;
        }

        $('#editCondition').click(function () {
            $('#firstRows').removeClass('hide');
            $('#lastRows').addClass('hide');
            $('#contentRows').addClass('hide');
            $('#conditionRows').removeClass('hide');
            resetHeight();
        });

        $('#clearCondition').click(function () {
            $('#searchTxt').val("");
            $.each($(".condition"), function () {
                $(this).removeClass("condition-click");
            });
            $.each($(".conditionAll"), function () {
                $(this).removeClass("condition-click");
            });
            $.post(urls.talentSearchBox, function (result) {
                $.each(result, function (i, item) {
                    if (i == 9) {
                        $.each(item.childs, function (i, value) {
                            var name = value.name;
                            if (i == 0) {
                                $("#selectTimeTwo").val(name.substr(0, 4));
                                if (name.substr(4, 6) == "06") {
                                    $("#selectTimeUpTwo").removeClass("condition-click").addClass("condition-click");
                                } else {
                                    $("#selectTimeDownTwo").removeClass("condition-click").addClass("condition-click");
                                }
                            }
                            if (i == 1) {
                                $("#selectTimeOne").val(name.substr(0, 4));
                                if (name.substr(4, 6) == "06") {
                                    $("#selectTimeUpOne").removeClass("condition-click").addClass("condition-click");
                                } else {
                                    $("#selectTimeDownOne").removeClass("condition-click").addClass("condition-click");
                                }
                            }
                        });
                    }
                })
            });
            $('#pastHistory').val("");
            $('#projectExperience').val("");
            $('#trainingExperience').val("");
        });

        function initPage(searchTxt) {
            $('#lastRows').removeClass('hide');
            $('#firstRows').addClass('hide');
            $('#contentRows').removeClass('hide');
            $('#conditionRows').addClass('hide');
            searchGridObj.init(searchTxt);
        }

        $('#searchReturn').click(function () {
            window.location.href = urls.toTalentSearchView;
        });

        $("#resultExport").click(function () {
            var conditionMap = getCondition();
            window.location.href = urls.toResultExport + "?conditionMap=" + JSON.stringify(conditionMap);
        });

    });

});
