/**
 * Created by wqcai on 15/6/15.
 */
require(['jquery', 'zrw-elements', 'underscore', 'ztree', 'bootstrap', 'validate', 'form'], function ($) {
    var webRoot = G_WEB_ROOT;
    var treeObj;

    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: beforeClick
        }
    };


    $(function () {
        var t = $("#ztree");
        $.post(webRoot + "/function/findFunctionAndItem", function (rs) {
            treeObj = $.fn.zTree.init(t, setting, rs);
        });

        //添加根节点触发事件
        $('#addParentFunctionBtn').click(function () {
            resetTable('#functionFrom');

            $('#parentFunctionGroup').addClass('hide');
            $('#isMenuGroup').removeClass('hide');
            $('#urlGroup').addClass('hide');
            $('#functionModal').modal('show');
        });


        $('#functionFrom').validate({
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                'functionKey': {
                    required: true
                },
                'functionName': {
                    required: true
                },
                'showIndex': {
                    number: true
                },
                'fullPath': {
                    required: true
                }
            },
            messages: {
                'functionKey': {
                    required: "功能编码必须填写!"
                },
                'functionName': {
                    required: "功能名称必须填写!"
                },
                'showIndex': {
                    number: "请输入整数!"
                },
                'fullPath': {
                    required: "功能路径必须填写!"
                }
            },
            highlight: function (element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                element.parent('div').append(error);
            },
            submitHandler: function (form) {
                ajaxFunctionForm(form);
            }
        });
        //单选框事件
        $('#isMenuGroup').find(':radio[name="isMenu"]').change(function () {
            var _this = $(this);
            var _urlGroup = $('#urlGroup');
            if (_this.val() == 1) {
                _urlGroup.addClass('hide');
                _urlGroup.find(':input[name="url"]').val('');
            } else {
                _urlGroup.removeClass('hide');
            }
        });

        $('#functionFrom input').keypress(function (e) {
            if (e.which == 13) {
                if ($('#functionFrom').validate().form()) {
                    ajaxFunctionForm('#functionFrom');
                }
                return false;
            }
        });

        $('#itemFrom').validate({
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                'note': {
                    required: true
                },
                'itemCode': {
                    required: true
                }
            },
            messages: {
                'note': {
                    required: "功能说明必须填写!"
                },
                'itemCode': {
                    required: "功能操作项必须填写!"
                }
            },
            highlight: function (element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                element.parent('div').append(error);
            },
            submitHandler: function (form) {
                ajaxItemForm(form);
            }
        });

        $('#itemFrom input').keypress(function (e) {
            if (e.which == 13) {
                if ($('#itemFrom').validate().form()) {
                    ajaxItemForm('#itemFrom');
                }
                return false;
            }
        });
    });

    function toLoadTree() {      //重新获取树对象
        treeObj = $.fn.zTree.getZTreeObj("ztree");
    }

    function ajaxFunctionForm(form) {
        var _from = $(form);
        _from.ajaxSubmit({
            success: function (rs) {
                if (!rs.type) return false;
                var treeId = $('#functionParentTreeId').val();
                if ($.isEmptyObject(treeObj)) {
                    toLoadTree();
                }
                if (_.isEmpty(treeId)) {  //添加根节点则刷新页面
                    window.location.href = webRoot + "/function/list";
                }
                var treeNode = treeObj.getNodeByTId(treeId);
                var result = rs.t;
                //假如返回的是对象,则是修改操作，否则是新增操作
                if (!_.isObject(result)) {
                    var newNode = {};
                    $.each(_from.serializeArray(), function (i, item) {
                        if (item.name == 'functionName') {
                            newNode['name'] = item.value;
                            return true;
                        }
                        if (item.name == 'url') {
                            newNode['urlPath'] = item.value;
                            return true;
                        }
                        newNode[item.name] = item.value;
                    });
                    newNode['id'] = rs.t;
                    newNode['type'] = 0;
                    treeObj.addNodes(treeNode, newNode);
                    _from[0].reset();
                } else {
                    $.each(result, function (i, item) {
                        console.log(i, item);
                        if (i == 'functionName') {
                            treeNode['name'] = item;
                            return true;
                        }
                        if (i == 'url') {
                            treeNode['urlPath'] = item;
                            return true;
                        }
                        treeNode[i] = item;
                    });
                    treeObj.updateNode(treeNode);
                }
                $('#functionModal').modal('hide');
            }
        });
    }

    function ajaxItemForm(form) {
        var _from = $(form);
        _from.ajaxSubmit({
            success: function (rs) {
                if (!rs.type) return false;
                var treeId = $('#itemParentTreeId').val();
                if ($.isEmptyObject(treeObj)) {
                    toLoadTree();
                }
                var treeNode = treeObj.getNodeByTId(treeId);
                var result = rs.t;
                //假如返回的是对象,则是修改操作，否则是新增操作
                if (!_.isObject(result)) {
                    var newNode = {};
                    $.each(_from.serializeArray(), function (i, item) {
                        if (item.name == 'note') {
                            newNode['name'] = item.value;
                            return true;
                        }
                        if (item.name == 'functionId') {
                            newNode['pid'] = item.value;
                            return true;
                        }
                        newNode[item.name] = item.value;
                    });
                    newNode['id'] = rs.t;
                    newNode['type'] = 1;
                    treeObj.addNodes(treeNode, newNode);
                    _from[0].reset();
                } else {
                    $.each(result, function (i, item) {
                        if (i == 'note') {
                            treeNode['name'] = item;
                            return true;
                        }
                        if (i == 'functionId') {
                            treeNode['pid'] = item;
                            return true;
                        }
                        treeNode[i] = item;
                    });
                    treeObj.updateNode(treeNode);
                }
                $('#itemModal').modal('hide');
            }
        });
    }

    function addHoverDom(treeId, treeNode) {
        if (treeNode.level > 2) return true;
        var sObj = $("#" + treeNode.tId + "_span");
        sObj.css('position', 'relative');
        if (treeNode.editNameFlag || $("#editBtn_" + treeNode.tId).length > 0) return true;
        var addStr = '';
        if (treeNode.level != 2) {
            addStr += '<span class="button add" id="addBtn_' + treeNode.tId + '"></span>';
        }
        addStr += '<span class="button edit" id="editBtn_' + treeNode.tId + '"></span>';
        addStr += '<span class="button remove" id="removeBtn_' + treeNode.tId + '"></span>';
        sObj.after(addStr);
        var addBtn = $("#addBtn_" + treeNode.tId);
        var removeBtn = $('#removeBtn_' + treeNode.tId);
        var editBtn = $('#editBtn_' + treeNode.tId);

        if (addBtn) addBtn.bind("click", function () {
            $('#parentFunctionGroup').removeClass('hide');
            addClickEvent(treeNode);
            return false;
        });

        if (removeBtn) removeBtn.bind('click', function () {
            deleteClickEvent(treeNode);
            return false;
        });

        if (editBtn) editBtn.bind('click', function () {
            editClickEvent(treeNode);
            return false;
        });
    };

    function beforeClick(treeId, treeNode) {
        if ($.isEmptyObject(treeObj)) {
            toLoadTree();
        }
        if (treeNode.isParent) {
            treeObj.expandNode(treeNode);
            return false;
        } else {
            return true;
        }
    }

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#removeBtn_" + treeNode.tId).unbind().remove();
        $("#editBtn_" + treeNode.tId).unbind().remove();
    };

    function addClickEvent(treeNode) {
        if (treeNode.isMenu == 1) {    //判断添加的是功能还是操作
            resetTable('#functionFrom');
            $('#urlGroup').removeClass('hide');

            $('#isMenuGroup').find(':radio[name="isMenu"][value="0"]').prop('checked', true);
            $('#isMenuGroup').addClass('hide');
            $('#functionParentTreeId').val(treeNode.tId);   //用于获取父级treeId
            $('#functionParentName').val(treeNode.name);
            $('#functionParentKey').val(treeNode.functionKey);
            $('#functionParentId').val(treeNode.id);
            $('#functionModal').modal('show');
        } else {
            resetTable('#itemFrom');

            $('#itemParentTreeId').val(treeNode.tId);        //用于获取父级treeId
            $('#functionName').val(treeNode.name);
            $('#functionId').val(treeNode.id);
            $('#itemModal').modal('show');
        }
    }

    function deleteClickEvent(treeNode) {
        $('#removeModal').modal('show');
        var tId = treeNode.tId;
        $('#removeSubmitBtn').unbind().bind('click', function () {
            $.post(webRoot + '/function/delFunctionAndItem',
                {'id': treeNode.id, 'type': treeNode.type}, function () {
                    if ($.isEmptyObject(treeObj)) {
                        toLoadTree();
                    }
                    var treeNode = treeObj.getNodeByTId(tId);
                    treeObj.removeNode(treeNode);
                    $('#removeModal').modal('hide');
                });
        });
    }

    function editClickEvent(treeNode) {
        var parentTId = treeNode.parentTId;
        if ($.isEmptyObject(treeObj)) {
            toLoadTree();
        }
        var pName = '', pKey = '';
        if (!_.isNull(parentTId)) {
            var node = treeObj.getNodeByTId(parentTId);
            pName = node.name;
            pKey = node.functionKey;
        }
        if (treeNode.type == 0) {
            resetTable('#functionFrom');

            if (treeNode.level == 0) {
                $('#isMenuGroup').removeClass('hide');
                $('#parentFunctionGroup').addClass('hide');
                if (treeNode.isMenu != 1) {
                    $('#urlGroup').removeClass('hide');
                } else {
                    $('#urlGroup').addClass('hide');
                }
            } else {
                $('#parentFunctionGroup').removeClass('hide');
                $('#isMenuGroup').addClass('hide');
                $('#urlGroup').removeClass('hide');
            }
            $('#functionParentTreeId').val(treeNode.tId);   //修改保存的是本身treeId
            $('#functionParentName').val(pName);
            $('#functionParentKey').val(pKey);
            $('#isMenuGroup').find(':radio[name="isMenu"][value="' + (treeNode.isMenu == 1 ? 1 : 0) + '"]').prop('checked', true);
            $('#functionFrom input[name="functionId"]').val(treeNode.id);
            $('#functionFrom input[name="functionKey"]').val(treeNode.functionKey);
            $('#functionFrom input[name="functionName"]').val(treeNode.name);
            $('#functionFrom input[name="pid"]').val(treeNode.pid);
            $('#functionFrom input[name="url"]').val(treeNode.urlPath);
            $('#functionFrom input[name="showIndex"]').val(treeNode.showIndex);
            $('#functionFrom input[name="fullPath"]').val(treeNode.fullPath);
            $('#functionFrom textarea[name="note"]').text(treeNode.note);
            $('#functionModal').modal('show');
        } else {
            resetTable('#itemFrom');
            $('#itemParentTreeId').val(treeNode.tId);    //修改保存的是本身treeId
            $('#itemFrom input[name="functionItemId"]').val(treeNode.id);
            $('#itemFrom input[name="itemCode"]').val(treeNode.functionKey);
            $('#itemFrom input[name="note"]').val(treeNode.name);
            $('#functionName').val(pName);
            $('#itemFrom input[name="functionId"]').val(treeNode.pid);
            $('#itemModal').modal('show');
        }
    }

    //初始化表单
    function resetTable(tableId) {
        var _this = $(tableId);
        _this[0].reset();
        _this.find('textarea[name="note"]').text('');
        _this.find('input[name="functionId"],input[name="pid"],input[name="functionItemId"],input[name="note"]').val('');
        $('#functionParentTreeId').val('');
    }

});