require(['jquery', 'bootstrap', 'spinJs', 'dataTable', 'datatables.net', 'utils', 'layer'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        list: webRoot + '/emp/list.do',
        delete: webRoot + '/emp/delete.do',
        lock: webRoot + '/emp/lock.do',
        updateAnnualTotal: webRoot + '/emp/updateAnnualTotal.do'
    }
    layer.config({
        path: webRoot + '/assets/layer-v3.0.3/layer/' // layer.js所在的目录，可以是绝对目录，也可以是相对目录
    });
    var style = {
        btnColor: "btn-default",
        iconColor: "glyphiconColor"
    }

    var dtMgr = {
        currentItem: null,
        fuzzySearch: true,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "userKey";
                        break;
                    case 2:
                        param.orderColumn = "userNameCh";
                        break;
                    case 3:
                        param.orderColumn = "email";
                        break;
                    default:
                        param.orderColumn = "userKey";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            // //组装查询参数
            param.search = data.search.value;
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;
            param.draw = data.draw;
            return param;
        },
        showItemDetail: function (item) {
            $("#user-view").show().siblings(".info-block").hide();
            if (!item) {
                $("#user-view .prop-value").text("");
                return;
            }
            $("#userId-view").text(item.userId);
            $("#userKey-view").text(item.userKey);
            $("#userNameCh-view").text(item.userNameCh);
            $("#entry-date-view").text(item.entryDate.split(' ')[0]);
            $("#address-view").text(item.address);
            $("#orgParName-view").text(item.orgParName);
            $("#orgName-view").text(item.orgName);
            $("#positionName-view").text(item.positionName);
            $("#telPhone-view").text(item.telPhone);
            $("#annualTotal-view").text(item.annualTotal);
            $("#role-view").text(item.roles);
            $("#annual-view").text(item.annual);
            $("#canLeave-view").text(item.canLeave);
            var annual = parseFloat(item.annual);
            var canLeave = parseFloat(item.canLeave);
            $("#total-view").text(annual + canLeave);
            $("#card-view").text(item.nationalId);
        },
        addItemInit: function () {
        },
        editItemInit: function (item) {
        },
        addItemSubmit: function () {
            layer.msg("保存当前添加用户", {icon: 1});
        },
        editItemSubmit: function () {
            layer.msg("保存当前编辑用户", {icon: 1});
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length == 1) {
                    message = "确定要删除 '" + selectedItems[0].userNameCh + "' 吗?";
                } else {
                    message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
                }
                var userIds = [];
                $.each(selectedItems, function (index, item) {
                    userIds.push(item.userId);
                });

                layer.confirm(message, {
                    btn: ['确认', '取消'] //按钮
                }, function () {
                    var setting = {
                        url: G_WEB_ROOT + '/emp/delete.do',
                        data: {"userIds": userIds},
                        success: function (data, status) {
                            if (data.k) {
                                layer.msg(data.v, {icon: 6});
                                $('#table-user_filter input').val('');
                                _table.draw();
                            } else {
                                layer.msg(data.v, {icon: 5});
                            }
                        }
                    };
                    Tc.ajax(setting);
                }, function () {
                    layer.msg('已取消删除操作', {
                        time: 20000 //20s后自动关闭
                    });
                });
            } else {
                layer.msg("请先选中要操作的行", {icon: 1});
            }
        },
        lockItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length == 1) {
                    message = "确定要锁定 '" + selectedItems[0].userNameCh + "' 吗?";
                } else {
                    message = "确定要锁定选中的" + selectedItems.length + "项记录吗?";
                }
                var userIds = [];
                $.each(selectedItems, function (index, item) {
                    userIds.push(item.userId);
                });

                layer.confirm(message, {
                    btn: ['确认', '取消'] //按钮
                }, function () {
                    var setting = {
                        url: G_WEB_ROOT + '/emp/lock.do',
                        data: {"userIds": userIds},
                        success: function (data, status) {
                            if (data.k) {
                                layer.msg(data.v, {icon: 6});
                                $('#table-user_filter input').val('');
                                _table.draw();
                            } else {
                                layer.msg(data.v, {icon: 5});
                            }
                        }
                    };
                    Tc.ajax(setting);
                }, function () {
                    layer.msg('已取消锁定操作', {
                        time: 20000 //20s后自动关闭
                    });
                });
            } else {
                layer.msg("请先选中要操作的行", {icon: 1});
            }
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
            //手动控制遮罩
            // $wrapper.spinModal();
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
                        //没有数据
                        if (result.data.length == 0) {
                            eventError();
                            return;
                        }
                        //异常判断与处理
                        if (result.errorCode) {
                            eventError();
                            return;
                        }
                        //封装返回数据，这里仅演示了修改属性名
                        var returnData = {};
                        // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.total;
                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;
                        //关闭遮罩
                        // $wrapper.spinModal(false);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                    }, 200);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    eventError();
                    // $wrapper.spinModal(false);
                }
            });
        },
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX, {
                "data": "userKey",
            }, {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                "data": "userNameCh"
            }, {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                "data": "email"
            }, {
                "data": "isLock",
                "createdCell": function (td, cellData, rowData, row, col) {
                    // rowData.isLocal == 1 ? $(td).text('加锁') : $(td).text('解锁');
                }
            }, {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "220px"
            }],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
            //给当前行某列加样式 eq从0开始
            // $('td', row).eq(2).addClass(data.userKey == 'dcli' ? "text-danger" : "text-success");

            //不使用render，改用jquery文档操作呈现单元格
            var $btnDel = $('<button type="button" class="btn btn-default btn-small btn-del"><span class="glyphicon glyphicon-remove ' + style.iconColor + '"></span> 删除</button>');
            $('td', row).eq(5).append($btnDel);
            var textLock = "";
            if (data.isLock == 0) {
                textLock = '<span class="' + style.iconColor + '"><i class="fa fa-lock"></i></span> 加锁';
            } else {
                textLock = '<span class="' + style.iconColor + '"><i class="fa fa-lock"></i></span> 解锁';
            }
            var $btnLock = $('<button type="button" class="btn btn-default btn-small btn-lock">' + textLock);
            $('td', row).eq(5).append($btnLock);
        },
        "drawCallback": function (settings) {
            //渲染完毕后的回调
            //清空全选状态
            $(":checkbox[name='cb-check-all']", $wrapper).prop('checked', false);
            //默认选中第一行
            $("tbody tr", $table).eq(0).click();

            var jsonData = settings.aoData;
            $.each(jsonData, function (index, item) {
                var obj = item._aData;
                // console.log(obj.isLock)
                var currEl = $("tbody tr", $table).eq(index);
                var currTd = $("tbody tr", $table).eq(index).find("td");
                if (obj.isLock == 0) {
                    currTd.eq(4).html(' <span class=\"' + style.iconColor + '\"><i class=\"fa fa-unlock\"></i></span> 解锁中');
                    currTd.eq(5).find(".btn-lock").html(' <span class="' + style.iconColor + '"><i class="fa fa-lock"></i></span> 加锁');
                } else {
                    currTd.eq(4).html(' <span class="' + style.iconColor + '"><i class="fa fa-lock"></i></span> 加锁中');
                    currEl.addClass("text-danger");
                    currTd.eq(5).find(".btn-lock").html('<span class="' + style.iconColor + '"><i class="fa fa-unlock"></i></span> 解锁');
                }

            });

        },
        "dom": "<'row'<'col-xs-2'l><'#bar.col-xs-3'><'#searchRs.col-xs-2'><'col-xs-5'f>r>" + "t" + "<'row'<'col-xs-6'i><'col-xs-6'p>>",
        "initComplete": function () {
            $("#bar").append('<button id="batch-btn-del" type="button" class="btn ' + style.btnColor + ' btn-sm">批量删除   <span class="glyphicon glyphicon-remove ' + style.iconColor + '"></span></button>&nbsp');
            $("#searchRs").append('<div style="background-color: orangered; text-align: center; font-weight: bold;" ><span>没有查询结果</span></div>');
            $("#searchRs div").hide();
            // 批量删除
            $("#batch-btn-del").click(function () {
                    var arrItemId = [];
                    $("tbody :checkbox:checked", $table).each(function (i) {
                        var item = _table.row($(this).closest('tr')).data();
                        arrItemId.push(item);
                    });
                    dtMgr.deleteItem(arrItemId);
                }
            );

        }
    })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    //行点击事件
    $("tbody", $table).on("click", "tr", function (event) {
        $(this).addClass("active").siblings().removeClass("active");
        //获取该行对应的数据
        var item = _table.row($(this).closest('tr')).data();
        dtMgr.currentItem = item;
        dtMgr.showItemDetail(item);
    });

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        dtMgr.deleteItem([item]);
    }).on("click", ".btn-lock", function () {
        //点击锁定按钮
        var item = _table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        dtMgr.lockItem([item]);
    });


    $('#showAnnualModalBtn').on('click', function () {
        $('#updateAnnualTotalModal').modal('show');
    });
    $('#updateAnnualBtn').on('click', function () {
        var annualTotal = $("#setAnnualVal").val();
        var userId = $("#userId-view").text();
        $.ajax({
            url: urls.updateAnnualTotal + "?annualTotal=" + annualTotal + "&userId=" + userId,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (data, status) {
                if (data.k) {
                    layer.msg(data.v, {icon: 6});
                    window.location = webRoot + "/emp/index.do";
                } else {
                    layer.msg(data.v, {icon: 5});
                    $('#updateAnnualTotalModal').modal('hide');
                }
            }
        });
    });

    /**
     * 错误提示
     */
    function eventError() {
        $("#searchRs div").show();
        setTimeout('$("#searchRs div").fadeOut(3000)', 3000);   //3s后执行，再3s淡出
    }
});

