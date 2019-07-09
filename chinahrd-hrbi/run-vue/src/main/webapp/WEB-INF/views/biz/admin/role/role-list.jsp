<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>角色管理</title>
</head>
<body>
<div class="rightBody">
    <div class="SetUpBody">
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">角色管理</div>
        </div>
        <div class="bottom-div bottom-div2 clearfix">
            <div class="col-sm-12 ct-col1">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
        </div>
    </div>
</div>
<script>
    require(['jgGrid', 'messenger'], function () {
        var webRoot = G_WEB_ROOT;
        var urls = {
            findRoleAll: webRoot + '/role/findRoleAll.do',
            operateRole: webRoot + '/role/operateRole.do',
            delRole: webRoot + '/role/delRole.do'
        }
        var grid_selector = "#grid-table";
        var pager_selector = "#grid-pager";
        var template = 
        	"<form class='container' style='width : 300px'> " +
                "<div class='row form-group'> " +
                "<label class='col-xs-4 text-right'>角色编号：</label> " +
                "<span class='col-xs-8'>{roleKey}</span> " +
                "</div> " +
                "<div class='row form-group'> " +
                "<label class='col-xs-4 text-right'>角色名称：</label> " +
                "<span class='col-xs-8'>{roleName}</span> " +
                "</div> " +
                "<div class='row form-group'> " +
                "<label class='col-xs-4 text-right'>描述：</label> " +
                "<span class='col-xs-8'>{note}</span> " +
                "</div> " +
                "<div class='row form-group' align='center'> {sData} {cData}</div>" +
                "</form>";
        jQuery(grid_selector).jqGrid({
            url: urls.findRoleAll,
            datatype: 'json',
            mtype: 'POST',
            autowidth: true,
            height: 800,
            colNames: ['id', '角色编码', '角色名称', '角色描述', '创建时间', '操作'],
            colModel: [
                {name: 'id', index: 'id', hidedlg: true, hidden: true, width: 60},
                {name:'roleKey',width:60,editable:true,editoptions:{maxlength:"20"},editrules:{required:true}},
                {name:'roleName',width:100,editable:true,editoptions:{size:"20",maxlength:"20"},editrules:{required:true}},
                {name:'note',width:150,editable:true,editoptions:{size:"20",maxlength:"32"},editrules:{required:true}},
                {name:'createTime',width:100,editable:true,align:'center',sorttype:"date"},
                {
                    name: 'myac', width: 200, fixed: true, sortable: false, resize: false, title: false
                    //				formatter:'actions',
                    //				formatoptions:{
                    //					keys:true,
                    //					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback}
                    //				}
                }
            ],
            viewrecords: true,
            rowNum: 20,
            rowList: [10, 20, 30],
            pager: pager_selector,
            altRows: true,
            multiselect: true,
            multiboxonly: true,
            rowHeight: 36,
            styleUI: 'Bootstrap',
            loadComplete: function (xhr) {
                var rows = xhr.rows;
                var ids = $(grid_selector).jqGrid('getDataIDs');
                for (var i = 0; i < ids.length; i++) {
                    var col = ids[i];
                    var html = '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configFunction"><a href="javascript:void(0)" data-index="' + i + '" class="role_col1" style="margin:0 10px;">配置功能</a></shiro:hasPermission>'
                            + '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configData"><a href="javascript:void(0)" data-index="' + i + '" class="role_col2" >配置数据</a></shiro:hasPermission>';
                    $(grid_selector).jqGrid('setRowData', col, {myac: html});
                }
                $('.role_col1').unbind().bind('click', function () {
                    var _this = $(this);
                    var idx = _this.attr('data-index');
                    var roleObj = rows[idx];
                    window.location.href = webRoot + '/role/roleFunction?roleId=' + roleObj.roleId;
                });
                $('.role_col2').unbind().bind('click', function () {
                    var _this = $(this);
                    var idx = _this.attr('data-index');
                    var roleObj = rows[idx];
                    //异步
                    // 				window.location.href = webRoot + '/role/roleOrganAsync?roleId='+roleObj.roleId;
                    //同步
                    window.location.href = webRoot + '/role/roleOrgan?roleId=' + roleObj.roleId;
                });
                var table = this;
            },
            editurl: urls.operateRole
        });
        //navButtons
        jQuery(grid_selector).jqGrid('navGrid', pager_selector,
                {
                    editCaption: "修改",
                    width: 350,
                    left: 450,
                    top: 20,
                    template: template,
                    errorTextFormat: function (data) {
                        return 'Error: ' + data.responseText
                    }
                },
                {
                    editCaption: "新增",
                    template: template,
                    width: 350,
                    left: 450,
                    top: 20,
                    errorTextFormat: function (data) {
                        return 'Error: ' + data.responseText
                    }
                },
                {recreateForm:true,beforeShowForm:function(e){varform=$(e[0]);}},
                {closeAfterAdd:true,recreateForm:true,viewPagerButtons:false,beforeShowForm:function(e){varform=$(e[0]);}},
                {recreateForm:true,beforeShowForm:function(e){varform=$(e[0]);},onClick:function(e){alert(1);}},
                {recreateForm:true,afterShowSearch:function(e){varform=$(e[0]);},afterRedraw:function(){},multipleSearch:true},
                {recreateForm:true,beforeShowForm:function(e){varform=$(e[0]);}}
        )
        function notifyInfo(msg, type) {
            Messenger().post({
                type: type,
                message: msg
            });
        }
    });
</script>
</body>
</html>