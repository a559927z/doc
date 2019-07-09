<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>用户数据配置</title>
    <link href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
    <style>
        .panel-footer .btn:last-child {
            margin-left: 12px;
        }

        #orgTree {
            /* 		    width:300px; */
            min-height: 400px;
            max-height: 400px;
            height: 400px;
            overflow: auto;
        }
    </style>
</head>
<body>
<div class="page-content">
    <div class="container">
        <div class="page-header">
            <input type="hidden" id="userId" value="${userDto.userId}">
            <h1>
                员工数据配置界面
                <small>(${userDto.userNameCh})</small>
            </h1>
        </div>
        <div class="col-md-4 col-sm-5 col-xs-6">
            <div class="panel panel-default">
                <div class="panel-body">
                    <ul id="orgTree" class="ztree"></ul>
                </div>
                <div class="panel-footer">
                    <button type="submit" class="btn btn-primary submitBtn">提交</button>
                    <button type="button" class="btn btn-default" id="resetBtn">返回</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript">
    require(['messenger', 'ztree', 'utils'], function () {
        var getTreeDataJsonUrl = G_WEB_ROOT + "/organ/getTreeDataJson";
        var treeData = [];

        function getTreeDataJson() {
            $.ajax({
                url: getTreeDataJsonUrl,
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


        var url = G_WEB_ROOT + "/organ/addEmpOrganiation";
        var selectData = [];
        var treeObj;
        var setting = {
            check: {
                enable: true,
//                 chkboxType: {"Y": "ps", "N": "ps"}
                chkboxType: { "Y" : "s", "N" : "ps" }
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
                    url: url,
                    type: 'post',
                    data: JSON.stringify(pojoDto),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        var dataType = (data.type) ? 'success' : 'error';
                        notifyInfo(data.msg, dataType);
                        if(dataType=='success'){
                        	$('#resetBtn').click();
                        }
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
                window.location.href = G_WEB_ROOT + '/user/list';
            });
        });// end $(function(){})
    });
</script>
</body>
</html>