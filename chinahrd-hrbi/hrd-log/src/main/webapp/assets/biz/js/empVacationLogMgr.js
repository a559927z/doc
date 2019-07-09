require(['jquery', 'bootstrap', 'spinJs', 'dataTable', 'datatables.net', 'utils', 'layer'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        list: webRoot + '/log/empVactionLogMgr/list.do'
    }
    layer.config({
        path: webRoot + '/assets/layer-v3.0.3/layer/' // layer.js所在的目录，可以是绝对目录，也可以是相对目录
    });
    var dtMgr = {
        currentItem: null,
        fuzzySearch: true,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "empKey";
                        break;
                    case 2:
                        param.orderColumn = "userNameCh";
                        break;
                    case 3:
                        param.orderColumn = "annualTotal";
                        break;
                    case 4:
                        param.orderColumn = "annual";
                        break;
                    default:
                        param.orderColumn = "empKey";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装查询参数
            param.search = data.search.value;
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
    // 原生搜索
    defaultoption.searching = true;
    var _table = $table.dataTable($.extend(true, {}, defaultoption, {
        //ajax配置为function,手动调用异步查询
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = dtMgr.getQueryCondition(data);
            $.ajax({
                type: "POST",
                url: urls.list,
                cache: false,	//禁用缓存
                data: param,	//传入已封装的参数
                dataType: "json",
                success: function (result) {
                    //setTimeout仅为测试遮罩效果
                    setTimeout(function () {
                        //异常判断与处理
                        if (result.errorCode) {
                            layer.msg("查询失败。错误码：" + result.errorCode, {
                                icon: 5
                            });
                            return;
                        }
                        //封装返回数据，这里仅演示了修改属性名
                        var returnData = {};
                        // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.total;
                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
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
            "data": "annualTotal"
        }, {
            "data": "annualDelayed"
        }, {
            "data": "annual"
        }, {
            "data": "canLeave"
        }, {
            "data": "historyHour",
        }],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
        },
        "drawCallback": function (settings) {
            //渲染完毕后的回调
            //清空全选状态
            $(":checkbox[name='cb-check-all']", $wrapper).prop('checked', false);
            //默认选中第一行
            $("tbody tr", $table).eq(0).click();
        }
    })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    //行点击事件
    $("tbody", $table).on("click", "tr", function (event) {
        $(this).addClass("active").siblings().removeClass("active");
    });
})
;

