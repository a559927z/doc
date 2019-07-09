/** 筛选条件 */
define(['jquery', 'bootstrap', 'dataTable', 'underscore', 'layer', 'template'], function (tpl) {
    var style = {
        btnColor: "btn-default",
        iconColor: "glyphiconColor"
    }
    var webRoot = G_WEB_ROOT;
    layer.config({
        path: webRoot + '/assets/layer-v3.0.3/layer/' // layer.js所在的目录，可以是绝对目录，也可以是相对目录
    });
    ///////////////////////////////////////////////////////////////////////////
    //模板
    ///////////////////////////////////////////////////////////////////////////
    var checkBoxTpl = '<input type="hidden" name="checkIds" />'; //批量删除

    var addTpl =
        '			<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
        '				<div class="modal-dialog">' +
        '					<div class="modal-content">' +
        '						<div class="modal-header">' +
        '							<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '								<span aria-hidden="true">&times;</span>' +
        '							</button>' +
        '							<h4 class="modal-title" id="myModalLabel">新增</h4>' +
        '						</div>' +
        '						<div class="modal-body"></div>' +
        '						<div class="modal-footer">' +
        '							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>' +
        '							<button type="button" class="btn btn-primary" id="save">保存</button>' +
        '						</div>' +
        '					</div>' +
        '				</div>' +
        '			</div>'
    ;

    var abc = '<div><h1>title:<%=title%></h1></div>';

    ///////////////////////////////////////////////////////////////////////////
    //默认opt
    ///////////////////////////////////////////////////////////////////////////
    var defaults = {
        // 内外连通属性
        tableId: "#example",
        showTool: false,
        // 封装属性
        "serverSide": true,
        "ordering": false,
        "language": {
            "lengthMenu": '显示 <select>'
            + '	<option value="10">10</option>'
            + '	<option value="20">20</option>'
            + '	<option value="30">30</option>'
            + '	<option value="-1">所有</option>'
            + '</select> 记录',
            "zeroRecords": "没有找到记录",
            "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "infoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "search": "搜索：",
            "infoFiltered": "(从 _MAX_ 条记录过滤)",
            "paginate": {
                "previous": "上一页",
                "next": "下一页"
            }
        },
    }

    $.fn.dataTablePush = function (options) {
        var opt = $.extend(true, {}, defaults, options), el = this;

        if (opt.showTool) {

            renderAddModal();

            opt.dom = " <'row'<'col-xs-3'f><'#mytool.col-xs-6'>r>   t   <'row'<'col-xs-5'i><'col-xs-2'l><'col-xs-5'p>  >";
            opt.initComplete = function () {
                $("#mytool")
                    .append(
                        '<button id="delBatch" type="button" class="btn '
                        + style.btnColor
                        + ' btn-sm">批量删除   <span class="glyphicon glyphicon-remove '
                        + style.iconColor
                        + '"></span></button>&nbsp');
                $("#mytool")
                    .append('<button id="addRole" type="button" class="btn '
                        + style.btnColor
                        + ' btn-sm" data-toggle="modal" data-target="#addModal">添加  <span class="glyphicon glyphicon-plus '
                        + style.iconColor
                        + '"></span></button>');
            }
        } else {
            opt.dom = " <'row'<'col-xs-3'f>r>   t   <'row'<'col-xs-5'i><'col-xs-2'l><'col-xs-5'p>  >";
            opt.initComplete = function () {
            }
        }

        function renderAddModal() {
            // var data={
            // 	"title":'欢迎使用baiduTemplate',
            // 	"list":[
            // 		'test1:默认支持HTML转义，如输出<script>，也可以关掉，语法<:=value> 详见API',
            // 		'test2:',
            // 		'test3:',
            // 		'test4:list[5]未定义，模板系统会输出空'
            // 	]
            // };
            // tpl.LEFT_DELIMITER = '<%';
            // tpl.RIGHT_DELIMITER = '%>';
            // var html = tpl(abc, data);
            $('body').append(addTpl);

            var html = '';
            var theadTr = $(opt.tableId).find('thead tr:nth-child(1)');
            var index = 1; // 不用i是因为可能去掉前两列
            $.each(theadTr.children(), function (i, o) {
                var txt = $(o).text();
                if (txt != '' & txt != '序号' & txt != '创建时间' & txt != '修改时间' & txt != '操作') {
                    html +=
                        '<div class="form-group">' +
                        '	<input type="text" class="form-control" id="addParam_' + index + '" placeholder="' + txt + '" />' +
                        '</div>'
                    ;
                }
            });
            $("#addModal .modal-body").append(html);
        }

        var table = $(opt.tableId).dataTable(opt);
        var editFlag = false;
        // 添加序号
        // 不管是排序，还是分页，还是搜索最后都会重画，这里监听draw事件即可
        table.on('draw.dt', function () {
            if (opt.showNum) {
                table.column(1, {
                    search: 'applied',
                    order: 'applied'
                }).nodes().each(function (cell, i) {
                    // i 从0开始，所以这里先加1
                    i = i + 1;
                    // 服务器模式下获取分页信息
                    var page = table.page.info();
                    // 当前第几页，从0开始
                    var pageno = page.page;
                    // 每页数据
                    var length = page.length;
                    // 行号等于 页数*每页数据长度+行号
                    var columnIndex = (i + pageno * length);
                    cell.innerHTML = columnIndex;
                });
            }
            initEvents();
        }).draw();

        /**
         * 初始化事件
         */
        function initEvents() {
            // $("#save").unbind().bind('click', function () {
            // 	if (!editFlag) {
            // 		add();
            // 	} else {
            // 		edit();
            // 	}
            // });
            $("._edit").unbind().bind('click', function () {
                editFlag = true;
                var _this = $(this);
                var roleId = _this.attr('data-id');
                var roleKey = _this.attr('data-key');
                var roleName = _this.attr('data-name');
                var note = _this.attr('data-note');
                $("#roleId").val(roleId);
                $("#roleKey").val(roleKey);
                $("#roleName").val(roleName);
                $("#note").val(note);
                $("#myModalLabel").text("修改");
            });
            $("._del").unbind().bind('click', function () {
                var _this = $(this);
                var id = _this.attr('data-index');
                $("#roleId").val(id);
            });
            $("#del").unbind().bind('click', function () {
                del($("#roleId").val());
            });

        }

        /**
         * 编辑方法
         */
        function edit() {
            var param = {
                "roleDto": {
                    "roleId": $("#roleId").val(),
                    "roleKey": $("#roleKey").val(),
                    "roleName": $("#roleName").val(),
                    "note": $("#note").val(),
                },
                "oper": "edit"
            };
            ajax(param);
        }

        function clear() {
            $("#roleKey").val("").attr("disabled", false);
            $("#roleName").val("");
            $("#note").val("");
            editFlag = false; // 将窗口改回新增
        }

        function ajax(obj) {
            // var url = options.operateUrl;
            $.ajax({
                url: url,
                type: 'POST',
                data: JSON.stringify(obj),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (data) {
                    table.ajax.reload();
                    $(".modal").modal("hide");
                    $("#myModalLabel").text("新增");
                    clear();
                    layer.msg('亲，操作已完成...', {
                        icon: 6
                    });
                }
            });
        }


        if (opt.showCheckBox) {
            $('body').prepend(checkBoxTpl);
            /**
             * 多选选中和取消选中,同时选中第一个单元格单选框,并联动全选单选框
             */
            $(opt.tableId).find('tbody').on('click', 'tr', function (event) {
                var allChecked = $('input[name=allChecked]')[0];// 关联全选单选框
                var trs = $(this).children()[0];//所有tr
                $(trs).children().each(function () {	// 遍历checkbox
                    if (this.type == "checkbox" && (!$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click"))) {
                        if (!this.checked) {
                            this.checked = true;
                            addValue(this);
                            var selected = table.rows('.selected').data().length;// 被选中的行数
                        } else {
                            this.checked = false;
                            cancelValue(this);
                            // allChecked.checked  = false;
                        }
                    }
                });
                $(this).toggleClass('selected');// 放在最后处理，以便给checkbox做检测
            });
            /**
             * 全选按钮被点击事件
             */
            $('input[name=allChecked]').click(function () {
                if (this.checked) {
                    $(opt.tableId).find('tbody tr').each(function () {
                        if (!$(this).hasClass('selected')) {
                            $(this).click();
                        }
                    });
                } else {
                    $(opt.tableId).find('tbody tr').click();
                }
            });

            /**
             * 单选框被选中时将它的value放入隐藏域
             */
            function addValue(para) {
                var checkIds = $("input[name=checkIds]");
                if (checkIds.val() === "") {
                    checkIds.val($(para).val());
                } else {
                    checkIds.val(checkIds.val() + "," + $(para).val());
                }
            }

            /**
             * 单选框取消选中时将它的value移除隐藏域
             */
            function cancelValue(para) {
                // 取消选中checkbox要做的操作
                var checkIds = $("input[name=checkIds]").val();
                var delVal = $(para).val();
                var array = checkIds.split(",");
                var tmp = [];
                for (var i = 0; i < array.length; i++) {
                    if (array[i] === delVal) {
                        continue;
                    } else {
                        tmp.push(array[i]);
                    }
                }
                $("input[name=checkIds]").val(tmp.join(","));
            }
        }
        return table;
    }
});
