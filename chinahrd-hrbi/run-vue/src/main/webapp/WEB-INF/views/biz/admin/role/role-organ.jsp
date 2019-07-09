<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>角色数据配置</title>
    <link href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
    <style>
        .btn {
            margin-right: 20px;
        }
    </style>
</head>
<body>

<div class="rightBody">
    <div class="SetUpBody">
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">
                角色数据配置界面
                <small>(${roleDto.roleName})</small>
            </div>
            <input type="hidden" id="roleId" value="${roleDto.roleId}">
        </div>
        <div class="bottom-div clearfix">
            <div class="col-sm-12 ct-col2">
                <button type="submit" class="btn btn-primary submitBtn">提交</button>
                <button type="button" class="btn btn-default" id="resetBtn">返回</button>
                <div class="ct-mCustomScrollBar height450">
                    <ul id="orgTree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    require(['messenger', 'ztree', 'utils', 'jquery-mCustomScrollBar'], function () {
        var webRoot = G_WEB_ROOT;
        var urls = {
            getTreeDataJsonUrl: webRoot + "/role/getTreeDataJson",
            addRoleOrganUrl: webRoot + "/role/addRoleOrganiation",
            goRoleUrl: webRoot + '/role/list'
        }
        var treeData = [];

        $(".ct-mCustomScrollBar").mCustomScrollbar({
            axis: "yx",
            scrollButtons: {enable: true},
            scrollbarPosition: "outside",
            theme: "minimal-dark"
        });

        function getTreeDataJson() {
            $.ajax({
                url: urls.getTreeDataJsonUrl,
                type: 'post',
                dataType: 'json',
                data: {roleId: $("#roleId").val()},
                async: false,	//同步
                success: function (data) {
                    treeData = data;
                }
            });
        }

        getTreeDataJson();

        var treeObj;
        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "ps", "N": "ps"}
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                    if (treeNode.isParent) {
                        treeObj.expandNode(treeNode);
                        return false;
                    }
                }
            }
        };

        var el = $("#orgTree");
        treeObj = $.fn.zTree.init(el, setting, treeData);

        $(".submitBtn").click(function () {
            var selectData = [];
            // 获取已选的节点（已存在节点）
            var checkedNodes = treeObj.getCheckedNodes(true);
            $.each(checkedNodes, function (index, item) {
                selectData.push({
                    organizationId: item.id,
                    fullPath: item.fullPath,
                    halfCheck: item.getCheckStatus().half == true ? 1 : 0
                });
            });
            var pojoDto = {
                roleId: $("#roleId").val(),
                organDto: selectData
            }
            $.ajax({
                url: urls.addRoleOrganUrl,
                type: 'post',
                data: JSON.stringify(pojoDto),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (data) {
                    var dataType = (data.type) ? 'success' : 'error';
                    notifyInfo(data.msg, dataType);
                }
            });
        });

        function notifyInfo(msg, type) {
            Messenger().post({
                type: type,
                message: msg
            });
        }

        $('#resetBtn').click(function () {
            window.location.href = urls.goRoleUrl;
        });
    });
</script>
</body>
</html>