<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>角色功能配置</title>
    <style>
    a:link {text-decoration: none;}
    .panel-body .checkbox-inline {cursor: default;}
    .panel-body .checkbox-inline label {cursor: pointer;}
    </style>
</head>
<body>
    <div class="rightBody">
        <div class="SetUpBody">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">角色功能配置界面<small>(${roleDto.roleName})</small></div>
                <input type="hidden" id="userId" value="${userDto.userId}">
            </div>
            <div class="bottom-div bottom-div2 clearfix">
                <form id="roleFunctionForm" action="${ctx}/role/addRoleFunction" method="post">
                    <input type="hidden" name="roleId" id="roleId" value="${roleDto.roleId}">

                    <div class="panel-group" id="accordion" aria-multiselectable="true">
                        <c:forEach items="${list}" var="parentDto">
                            <c:choose>
                                <c:when test="${parentDto.isMenu == 1}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                                ${parentDto.functionName}
                                        </div>
                                        <div class="panel-body">
                                            <c:forEach items="${parentDto.childs}" var="childDto">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse_${childDto.functionId}">
                                                    <div class="checkbox">
                                                        <label>
                                                            <input type="checkbox" class="collapse-function"
                                                                   data-id="collapse_${childDto.functionId}" name="function[]"
                                                                   value="${childDto.functionId}"> ${childDto.functionName}
                                                        </label>
                                                    </div>
                                                </a>

                                                <div id="collapse_${childDto.functionId}" class="panel-collapse collapse">
                                                    <div class="panel panel-default">
                                                        <div class="panel-body">
                                                            <div class="checkbox-inline col-sm-2">
                                                                <label>
                                                                    <input type="checkbox" class="collapse-function-item" data-index="0"
                                                                           name="functionItem[]" value="${childDto.functionId}:*"> 所有&nbsp;
                                                                </label>
                                                            </div>
                                                            <c:forEach var="item" items="${childDto.functionItems}" varStatus="s">
                                                                <div class="checkbox-inline col-sm-2">
                                                                    <label>
                                                                        <input type="checkbox" class="collapse-function-item"
                                                                               data-index="${status.index + 1}" name="functionItem[]"
                                                                               value="${childDto.functionId}:${item}"> ${childDto.itemNames[s.index]}
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${parentDto.isMenu != 1 && parentDto.pathUrl != ''}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">&nbsp;</div>
                                        <div class="panel-body">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse_${parentDto.functionId}">
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" class="collapse-function"
                                                               data-id="collapse_${parentDto.functionId}" name="function[]"
                                                               value="${parentDto.functionId}"> ${parentDto.functionName}
                                                    </label>
                                                </div>
                                            </a>

                                            <div id="collapse_${parentDto.functionId}" class="panel-collapse collapse">
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        <div class="checkbox-inline col-sm-2">
                                                            <label>
                                                                <input type="checkbox" class="collapse-function-item" data-index="0"
                                                                       name="functionItem[]" value="${parentDto.functionId}:*"> *&nbsp;
                                                            </label>
                                                        </div>
                                                        <c:forEach var="item" items="${parentDto.functionItems}">
                                                            <div class="checkbox-inline col-sm-2">
                                                                <label>
                                                                    <input type="checkbox" class="collapse-function-item"
                                                                           data-index="${status.index + 1}" name="functionItem[]"
                                                                           value="${parentDto.functionId}:${item}"> ${item}
                                                                </label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <%-- 展示其它功能 --%>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <div class="col-xs-12">&nbsp;</div>
                    <div class="col-xs-12 ">
                        <button type="submit" class="btn btn-primary">提交</button>
                        <button type="button" class="btn btn-default" id="resetBtn">返回</button>
                    </div>
                    <div class="col-xs-12">&nbsp;</div>
                </form>
            </div>
        </div>
    </div>
<script>
    require(['bootstrap', 'form'], function () {
        var webRoot = G_WEB_ROOT;

        $('.collapse').collapse({
            toggle: false
        });

        $(function () {
            //加载角色功能
            getRoleFunction($('#roleId').val());

            $('#resetBtn').click(function () {
                window.location.href = webRoot + '/role/list';
            });

            $('#accordion .collapse').on('show.bs.collapse', function () {
                var _this = $(this);
                var idVal = _this.attr('id');
                var _child = _this.parents('.panel-group').find('a[href="#' + idVal + '"]').find(':checkbox');
                _child.prop('checked', true);
                var bool = false;
                var _sub = _this.find('input[type="checkbox"]');
                $.each(_sub, function (i, item) {
                    var checked = $(item).prop('checked');
                    if (checked) {
                        bool = true;
                        return true;
                    }
                });
                if (!bool) {
                    _sub.first().prop('checked', true);
                }
            });

            $('#accordion .collapse-function').bind('click', function (e) {
                e.stopPropagation();
                var _this = $(this);
                var collapseId = _this.attr('data-id');
                $('#' + collapseId).collapse('toggle');
                _this.prop('checked', true);
            });

            $('#accordion .collapse-function-item').click(function () {
                var _this = $(this);
                var isSelect = _this.prop('checked');
                var idx = _this.attr('data-index');
                var _parent = _this.parents('.checkbox-inline');
                var _panal = _parent.parents('.panel-collapse');
                var _parentCheckbox = _panal.prev().find('input[type="checkbox"]');
                if (isSelect) {
                    _parentCheckbox.prop('checked', true);
                    if (idx == 0) {
                        _parent.siblings().find('input[type="checkbox"]').prop('checked', false);
                        return;
                    }
                    _parent.siblings().first().find('input[type="checkbox"]').prop('checked', false);
                } else {
                    if (idx == 0) {
                        _parentCheckbox.prop('checked', false);
                        _panal.collapse('hide');
                        return;
                    }
                    var bool = false;
                    $.each(_parent.siblings(), function (i, item) {
                        var siblingsChecked = $(item).find('input[type="checkbox"]').prop('checked');
                        if (siblingsChecked) {
                            bool = true;
                            return false;
                        }
                    });
                    if (!bool) {
                        _parentCheckbox.prop('checked', bool);
                        _panal.collapse('hide');
                    }
                }
            });
            $('#roleFunctionForm').submit(function () {
                $(this).ajaxSubmit({
                    success: function (rs) {
                        if (rs.type) {
                            alert('提交成功!');
                            window.location.href = webRoot + '/role/list';
                        }
                    }
                });
                return false;	//阻止默认表单提交
            });
        });

        function openCollapse(obj) {

        }

        function getRoleFunction(roleId) {
            $.post(webRoot + '/role/getRoleFunction', {'roleId': roleId}, function (rs) {
                if (rs != null && !$.isEmptyObject(rs)) {
                    for (var i = 0; i < rs.length; i++) {
                        var functionId = rs[i].functionId;
                        $('#accordion :checkbox[name="function[]"][value="' + functionId + '"]').prop('checked', true);
                        var items = rs[i].functionItems;
                        for (var j = 0; j < items.length; j++) {
                            var itemValue = functionId + ':' + items[j];
                            $('#accordion :checkbox[name="functionItem[]"][value="' + itemValue + '"]').prop('checked', true);
                        }
                    }
                }
            });
        }
    });
</script>
</body>
</html>