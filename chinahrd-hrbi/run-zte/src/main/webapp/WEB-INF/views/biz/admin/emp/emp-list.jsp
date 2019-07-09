<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>员工管理</title>
</head>
<body>
<div class="page-content">
    <div class="main-container-inner">
        <div class="row">
            <div class="col-xs-7">
                <div class="input-group">
                    <input type="text" class="form-control search-query" id="searchTxt" placeholder="请输入员工ID/姓名"/>
                    <span class="input-group-btn">
                                    <button type="button" class="btn btn-white" id="searchBtn">
                                        	查询
                                        <i class="icon-search icon-on-right bigger-110"></i>
                                    </button>
                                </span>
                </div>
            </div>
            <div class="col-xs-5"><label>* 姓名支持模糊查询</label></div>
        </div>

        <div class="row">
            <div class="col-xs-12">&nbsp;</div>
            <div class="col-xs-12">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript">
    require(['jgGrid', 'bootstrap', 'underscore'], function () {
        var webRoot = G_WEB_ROOT;
        var urls = {
            //		  getSearchEmpUrl: webRoot + '/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
            findEmpAll: webRoot + '/talentContrast/getSearchEmpList.do',
            operateRole: webRoot + '/role/operateRole.do'
        }

        var grid_selector = "#grid-table";
        var pager_selector = "#grid-pager";


        var searchEmpObj = {
            gridId: grid_selector,
            searchTxt: null,
            gridOption: {
                url: urls.findEmpAll,
                datatype: 'json',
                mtype: 'POST',
                autowidth: true,
                height: 326,
                colNames: ['id', '员工编码', '员工名称', '组织机构', '操作'],
                colModel: [
                    {name: 'id', index: 'id', hidedlg: true, hidden: true, width: 60},
                    {
                        name: 'empKey',
                        width: 60,
                        editable: true,
                        editoptions: {maxlength: "20"},
                        editrules: {required: true}
                    },
                    {
                        name: 'userName',
                        width: 100,
                        editable: true,
                        editoptions: {size: "20", maxlength: "20"},
                        editrules: {required: true}
                    },
                    {
                        name: 'organName',
                        width: 150,
                        editable: true,
                        editoptions: {size: "20", maxlength: "32"},
                        editrules: {required: true}
                    },
                    {
                        name: 'myac', index: '', width: 200, fixed: true, sortable: false, resize: false
                    }
                ],
                viewrecords: true,
                rowNum: 10,
                rowList: [10, 20, 30],
                rowHeight: 36,
                styleUI: 'Bootstrap',
                pager: pager_selector,
                altRows: true,
                multiselect: true,
                multiboxonly: true,
                loadComplete: function (xhr) {

                    var rows = xhr.rows;
                    var ids = $(grid_selector).jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var col = ids[i];
                        var html = '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configData"><a href="javascript:void(0)" data-index="' + i + '" class="emp_col" >配置数据</a></shiro:hasPermission>';

                        $(grid_selector).jqGrid('setRowData', col, {myac: html});
                    }

                    $('.emp_col').unbind().bind('click', function () {
                        var _this = $(this);
                        var idx = _this.attr('data-index');
                        var roleObj = rows[idx];
                        //异步
                        // 				window.location.href = webRoot + '/role/roleOrganAsync?roleId='+roleObj.roleId;
                        //同步
                        window.location.href = webRoot + '/emp/empOrgan?empId=' + roleObj.empId;
                    });
                },

                editurl: urls.operateRole,
                caption: '员工管理'
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

        searchEmpObj.init("");


        $('#searchBtn').click(function () {
            var searchTxt = $.trim($('#searchTxt').val());
            searchEmpObj.init(searchTxt);
        });
        $("#searchTxt").keydown(function (e) {
            if (e.keyCode == 13) {
                //假如焦点在文本框上,则获取文本框的值
                if (document.activeElement.id == 'searchTxt') {
                    var searchTxt = $.trim($('#searchTxt').val());
                    searchEmpObj.init(searchTxt);
                }
            }
        });
    });


</script>
</body>
</html>