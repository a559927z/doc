require(['jquery', 'bootstrap', 'dataTable', 'dataTablePush', 'utils'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        list: webRoot + '/user/list.do'
    }

    var $wrapper = $('#div-table-container');
    var $table = $('#table-user');

    var infoTableOpt = {
        tableId: "#infoTable",
        ajax: {
            url: urls.list,
            type: 'POST',
        },
        "dom": '<"top"f >rt<"bottom"ilp><"clear">',//dom定位
        "dom": 'tiprl',//自定义显示项
        "scrollY": "622px",//dt高度
        "lengthMenu": [
            [15, 20, 30, -1],
            [15, 20, 30, "所有"]
        ],//每页显示条数设置
        "lengthChange": false,//是否允许用户自定义显示数量
        "bPaginate": true, //翻页功能
        "bFilter": false, //列筛序功能
        "searching": true,//本地搜索
        "ordering": true, //排序功能
        "Info": true,//页脚信息
        "autoWidth": true,//自动宽度
        // "serverSide": true,//启用服务器端分页
        "oLanguage": {//国际语言转化
            "oAria": {
                "sSortAscending": " - click/return to sort ascending",
                "sSortDescending": " - click/return to sort descending"
            },
            "sLengthMenu": "显示 _MENU_ 记录",
            "sZeroRecords": "对不起，查询不到任何相关数据",
            "sEmptyTable": "未有相关数据",
            "sLoadingRecords": "正在加载数据-请等待...",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
            "sInfoEmpty": "当前显示0到0条，共0条记录",
            "sInfoFiltered": "（数据库中共为 _MAX_ 条记录）",
            "sProcessing": "<img src='../resources/user_share/row_details/select2-spinner.gif'/> 正在加载数据...",
            "sSearch": "模糊查询：",
            "sUrl": "",
            //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": " 上一页 ",
                "sNext": " 下一页 ",
                "sLast": " 尾页 "
            }
        },
        // "columnDefs": [{
        //     orderable: false,
        //     targets: 0
        // }],//第一列禁止排序
        // "order": [
        //     [0, null]
        // ],//第一列排序图标改为默认
        "columns": [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                "data": "userKey"
            }, {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                "data": "userNameCh"
            }, {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                "data": "email"
            }],
        "drawCallback": function (settings) {
            //渲染完毕后的回调
            //清空全选状态
            $(":checkbox[name='cb-check-all']", $wrapper).prop('checked', false);
            //默认选中第一行
            $("tbody tr", $table).eq(0).click();
        }
    }
    var infoTable = $("#infoTable").dataTable(infoTableOpt);
    infoTable.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", infoTable).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", infoTable);
            $(":checkbox[name='cb-check-all']", infoTable).prop('checked', checkbox.length == checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
            //点击单元格即点击复选框
            !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
        }
    );
});