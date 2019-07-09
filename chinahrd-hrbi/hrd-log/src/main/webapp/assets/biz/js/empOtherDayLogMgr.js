require(['jquery', 'bootstrap', 'spinJs', 'dataTable', 'datatables.net', 'utils', 'layer'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        queryEmpOtherDayYearUrl: webRoot + '/log/queryEmpOtherDayYear.do', // 历史休假情况
        list: webRoot + '/log/empOtherDayLogMgr/list.do'
    }
    layer.config({
        path: webRoot + '/assets/layer-v3.0.3/layer/' // layer.js所在的目录，可以是绝对目录，也可以是相对目录
    });
    var startYm = 201601, endYm = 201612;
    var dtMgr = {
        currentItem: null,
        ymSearch: false,
        getQueryCondition: function (data) {
            var param = {};
            //组装查询参数
            param.search = data.search.value;
            param.ymSearch = dtMgr.ymSearch;
            param.startYm = startYm;
            param.endYm = endYm;
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;
            param.draw = data.draw;
            return param;
        }
    };

    var $table = $('#table-user');
    var $wrapper = $('#div-table-container');
    var defaultoption = CONSTANT.DATA_TABLES.DEFAULT_OPTION;
    defaultoption.searching = true;
    var _table = $table.dataTable($.extend(true, {}, defaultoption, {
        ajax: function (data, callback, settings) {
            var param = dtMgr.getQueryCondition(data);
            $.ajax({
                type: "POST",
                url: urls.list,
                cache: false,
                data: param,
                dataType: "json",
                success: function (result) {
                    setTimeout(function () {
                        if (result.errorCode) {
                            layer.msg("查询失败。错误码：" + result.errorCode, {
                                icon: 5
                            });
                            return;
                        }
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.total;
                        returnData.recordsFiltered = result.total;
                        returnData.data = result.data;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        callback(returnData);
                    }, 200);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.msg("查询失败", {
                        icon: 5
                    });
                }
            });
        },
        "columns": [{
            "data": "empKey"
        }, {
            "data": "userNameCh"
        }, {
            "data": "typeName"
        }, {
            "data": "num"
        }, {
            "data": "days",
        }],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
        },
        "drawCallback": function (settings) {
            //渲染完毕后的回调
        },
        "dom": "<'row'<'col-xs-2'l><'#bar.col-xs-4'><'col-xs-6'f>r>" + "t" + "<'row'<'col-xs-6'i><'col-xs-6'p>>",
        "initComplete": function () {
            //表格数据渲染回调,在这里可以对该行dom元素进行任何操作
            $("#bar").append('<select class="form-control" id="otherDayYesrStatus"></select>');
            getYmData();
        }
    })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    //行点击事件
    $("tbody", $table).on("click", "tr", function (event) {
        $(this).addClass("active").siblings().removeClass("active");
    });

    function getYmData() {
        $.post(urls.queryEmpOtherDayYearUrl, function (yearList) {
            if (Tc.isNotEmpty(yearList)) {
                $('#otherDayYesrStatus').empty();
                $.each(yearList, function (i, item) {
                    $('#otherDayYesrStatus').append('<option value="' + item + '">' + item + '</option>');
                });
                selectEvents();
            }
        });
    }

    function selectEvents() {
        $("select#otherDayYesrStatus").change(function () {
            var year = $(this).val();
            startYm = year + "01";
            endYm = year + "12";
            dtMgr.ymSearch = true;
            _table.draw();
        });
    }

    $("select#otherDayYesrStatus").change(function () {
        var year = $(this).val();
        param.startYm = year + "01";
        param.endYm = year + "12";
        dtMgr.ymSearch = true;
        console.log(_table);
        _table.draw();
    });

});