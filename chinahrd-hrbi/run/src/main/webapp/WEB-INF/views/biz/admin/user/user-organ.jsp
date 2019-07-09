<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>用户数据配置</title>
    <link href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
    <style >
        .btn{margin-right: 20px;}
    </style>
</head>
<body>
    <div class="rightBody">
        <div class="SetUpBody">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">员工数据配置界面
                    <small>(${userDto.userNameCh})</small>
                </div>
                <input type="hidden" id="userId" value="${userDto.userId}">
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
            getTreeDataJsonUrl: webRoot + "/organ/getTreeDataJson",
            addEmpOrganUrl: webRoot + "/organ/addEmpOrganiation",
            goUserUrl: webRoot + '/user/list'
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
                data: {userId: $("#userId").val()},
                async: false,	//同步
                success: function (data) {
                    treeData = data;
                }
            });
        }

        getTreeDataJson();

        var selectData = [];
        var treeObj;
        var setting = {
            check: {
                enable: true,
//                 chkboxType: {"Y": "ps", "N": "ps"}
                chkboxType: {"Y": "s", "N": "ps"}
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

        $(function () {
            var el = $("#orgTree");
            treeObj = $.fn.zTree.init(el, setting, treeData);

            $(".submitBtn").click(function () {

                // bug314 数据权限不是’开发部‘，而是’北京中人网信息咨询有限公司‘	by jxzhang
                selectData = [];
                // 获取已选的节点（已存在节点）
                var checkedNodes = treeObj.getCheckedNodes(true);
                //假如机构树没有选中的阶段，不给提交
                if (checkedNodes.length == 0) {
                    notifyInfo('请至少选择一条数据权限！', 'error');
                    return false;
                }
                $.each(checkedNodes, function (index, item) {
                    selectData.push({
                        organizationId: item.id,
                        fullPath: item.fullPath,
                        halfCheck: item.getCheckStatus().half == true ? 1 : 0
                    });
                });
                var pojoDto = {
                    userId: $("#userId").val(),
                    organDto: selectData
                }
                $.ajax({
                    url: urls.addEmpOrganUrl,
                    type: 'post',
                    data: JSON.stringify(pojoDto),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        var dataType = (data.type) ? 'success' : 'error';
                        notifyInfo(data.msg, dataType);
                        if (dataType == 'success') $('#resetBtn').click();
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
                window.location.href = urls.goUserUrl;
            });
        });
    });
</script>
</body>
</html>