/**
 * Created by qpzhu on 16/03/02 .
 */
require(['bootstrap', 'jgGrid', 'underscore'], function () {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        toTalentSearchView: webRoot + '/talentSearch/toTalentSearchView.do',	//跳到员工搜索初始化页面
        findEmpUrl: webRoot + '/talentSearch/findEmpAll.do',   //查询所有的员工信息
        toEmpDetailUrl: webRoot + '/talentSearch/toTalentDetailView.do'    //跳转到员工详情
    };

    //初始化'加入对比'
    var stackObject;

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;

    win.organizationObject = function (organId) {
        reqOrgId = organId;
    }
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
                width: 80,
                sortable: false,
                editoptions: {size: "20", maxlength: "30"}
            },
            {
                name: 'organizationName',
                index: 'organizationName',
                width: 90,
                sortable: false,
                editable: true,
                editoptions: {size: "20", maxlength: "30"}
            },
            {
                name: 'positionName',
                index: 'positionName',
                width: 90,
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
                width: 220,
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
        loadComplete: function (xhr) {
            var rows = xhr.rows;
            var gridSelector = searchGridObj.gridId;
            var ids = $(gridSelector).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var col = ids[i];
                var html = '<a href="javascript:void(0)" target="_blank" data-index="' + i + '" class="emp_col" >人才剖像</a>';
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
                }
            });

            var tableHeight = 0, iFrameHeight = 0;
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

    var searchGridObj = {
        gridId: '#searchGridTable',
        init: function (keyTxt) {
            var self = this;
            gridOption.postData = {'keyName': keyTxt, 'reqOrgId': reqOrgId};
            $(self.gridId).jqGrid(gridOption);
        },
        resizeGridData: function (keyTxt) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                postData: {'keyName': keyTxt, 'reqOrgId': reqOrgId}
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#searchGridPanel').width() * 0.98);
        }
    }

    $(function () {
        $('#firstSearchBtn').click(function () {
            var searchTxt = $.trim($('#firstSearchTxt').val());
            if (!$.isEmptyObject(searchTxt)) {
                initPage(searchTxt);
            }
        });

        $('#lastSearchBtn').click(function () {
            var searchTxt = $.trim($('#lastSearchTxt').val());
            if (!$.isEmptyObject(searchTxt)) {
                searchGridObj.resizeGridData(searchTxt);
            }
        });
        $("#firstSearchTxt").keydown(function (e) {
            if (e.keyCode == 13) {
                var firstSearchTxt = $.trim($('#firstSearchTxt').val());
                var hasHide = $('#firstRows').hasClass('hide');
                if (!$.isEmptyObject(firstSearchTxt) && !hasHide) {
                    initPage(firstSearchTxt);
                    return;
                }
                var lastSearchTxt = $.trim($('#lastSearchTxt').val());
                if (!$.isEmptyObject(lastSearchTxt)) {
                    searchGridObj.resizeGridData(lastSearchTxt);
                }
            }
        })
        $("#lastSearchTxt").keydown(function (e) {
            if (e.keyCode == 13) {
                var firstSearchTxt = $.trim($('#firstSearchTxt').val());
                var hasHide = $('#firstRows').hasClass('hide');
                if (!$.isEmptyObject(firstSearchTxt) && !hasHide) {
                    initPage(firstSearchTxt);
                    return;
                }
                var lastSearchTxt = $.trim($('#lastSearchTxt').val());
                if (!$.isEmptyObject(lastSearchTxt)) {
                    searchGridObj.resizeGridData(lastSearchTxt);
                }
            }
        })

        function initPage(searchTxt) {
            $('#lastRows').removeClass('hide');
            $('#firstRows').addClass('hide');
            $('#titleText').html(searchTxt);
            searchGridObj.init(searchTxt);
            $('#lastSearchTxt').val(searchTxt);
        }

        function initKeyNamePage() {
            var keyName = $.trim($('#keyName').val());
            if (keyName != '') {
                initPage(keyName);
            }
        }

        initKeyNamePage();
        $("#gotoAdvancedView").click(function () {
            $(this).attr("href", webRoot + "/talentSearch/toTalentSearchAdvancedView");
        });

        $(window).resize(function () {
            searchGridObj.resizeGrid();
        });

        $('#searchReturn').click(function () {
            window.location.href = urls.toTalentSearchView;
        });
    });

});
