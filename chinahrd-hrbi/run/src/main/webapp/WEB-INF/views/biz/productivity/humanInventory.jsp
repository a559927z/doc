<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>产品人力结构</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
    <link rel="stylesheet" href="${ctx}/assets/css/biz/manageHome2.css"/>
    <style>
    .index-son-body{
        min-height: 768px;
    }
    .ct-drag .drag-content {
        max-width: 948px;
        min-height: 200px;
        width: 100%;
        background-color: #ffffff;
        margin: auto;
        display: inline-block;
    }
    /*tab 切换 样式 begin*/
    .sz-window .index-jxmb-tab {position: relative;font-size: 12px;height: 34px;width: 100%;border-left: 1px solid #e0e0e0;border-bottom: 1px solid #e0e0e0;}
    .sz-window .index-jxmb-btn {border-top: 1px solid #e0e0e0;border-right: 1px solid #e0e0e0;width: 110px;height: 34px;line-height: 32px;text-align: center;float: left;cursor: pointer;background-color: #fafafa;border-bottom: 1px solid #e5e5e5;}
    .sz-window .index-jxmb-btn-select {border-top: 2px solid #009edd;border-bottom: #ffffff;background: #ffffff;line-height: 30px;cursor: default;}
    /*tab 切换 样式 end*/
    .sz-window .ct-row .ct-base{float:left;display:inline-block;min-height: 60px;line-height: 50px;margin-right:100px;font-size: 15px;}
    </style>
</head>
<body>
<div class="container-fluid ct-container-fluid ct-ancestors index">

    <div name="人力结构" class="row ct-row">
        <div name="人力结构" showIndex="1" class="DRAG-DIV col-sm-12 ct-col index-yj-div-djrcsy">
            <div class="index-son-body">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">项目人力盘点
                        <img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif" title="" data-original-title="一种人力投入衡量方法，把全职员工加班时数及兼职员工工作时数折算为全职员工人数">
                    </div>
                </div>

                <div class="index-son-middle">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="row">&nbsp;</div>
                            <table id="grid-table"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<!--主动流失率指标设置 弹出框 begin-->
<div class="ct-drag sz-window">
    <div class="drag-body">
        <div class="drag-content">
            <div class="drag-title">
                <div class="drag-title-blue"></div>
                <div class="drag-title-text">主导产品盘点</div>
                <div class="cancelDragBtnEvent closeIcon"></div>
            </div>
            <div class="drag-middle">
                <div class="row ct-row">
                    <span class="ct-base">时间:&nbsp;2015年6月</span>
                    <span class="ct-base">产品:&nbsp;才报平台</span>
                    <span class="ct-base">人力投入:&nbsp;369.5人</span>
                </div>
                <div class="row ct-row">
                    <div class="index-jxmb-tab">
                        <div page="chartview-0" class="index-jxmb-btn index-jxmb-btn-select">当前人力投入</div>
                        <div page="chartview-1" class="index-jxmb-btn">人力投入环比趋势</div>
                        <div page="chartview-2" class="index-jxmb-btn">人力结构分析</div>
                        <div page="chartview-3" class="index-jxmb-btn">子项目明细</div>
                    </div>
                    <div class="bottom-div " id="chartview-0">
                        <div class="row">&nbsp;</div>
                        <img src="${ctx}/assets/img/temp/ts2-0001.png" width="100%" />
                    </div>
                    <div class="bottom-div" style="display: none;" id="chartview-1">
                        <div class="row">&nbsp;</div>
                        <img src="${ctx}/assets/img/temp/ts2-0002.png" width="100%" />
                    </div>
                    <div class="bottom-div" style="display: none;" id="chartview-2">
                        <div class="row">&nbsp;</div>
                        <img src="${ctx}/assets/img/temp/ts2-0003.png" width="100%" />
                    </div>
                    <div class="bottom-div" style="display: none;" id="chartview-3">
                        <div class="row">&nbsp;</div>
                        <img src="${ctx}/assets/img/temp/ts2-0004.png" width="100%" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--主动流失率指标设置 弹出框 end-->
<!--遮罩层--><div id="shade" class="shade"></div>

<script>
    require(['jgGrid', 'messenger'],function(){
        var webRoot = G_WEB_ROOT;
        var win = top != window ? top.window : window;
        $(win.document.getElementById('tree')).next().show();

        var urls = {
            findRoleAll : webRoot + '/role/findRoleAll.do',
            operateRole : webRoot + '/role/operateRole.do',
            delRole : webRoot + '/role/delRole.do'
        }

        var grid_selector = "#grid-table";

        var requestData = [
            {name:'才报平台',username:'江浩',curr:29.5,prev:25,range:'18.00%',product:7,design:11,develop:11,oper:0.5,test:0,other:0},
            {name:'才报对标',username:'刘宇凡',curr:25,prev:24,range:'4.17%',product:2,design:8,develop:6,oper:2,test:3,other:4},
            {name:'才报课程',username:'张漠然',curr:16,prev:13,range:'23.08%',product:5,design:3,develop:3,oper:2,test:2,other:1},
            {name:'点善学堂',username:'王益阳',curr:26,prev:28,range:'-7.14%',product:8,design:10,develop:5,oper:1,test:2,other:0},
            {name:'媒体广告',username:'刘宇凡',curr:17,prev:20,range:'-15.00%',product:6,design:2,develop:3,oper:3,test:2,other:1}
        ]
        $(grid_selector).jqGrid({
            data: requestData,
            datatype: "local",
            autowidth: true,
            height: 326,
            colNames:['产品名','产品负责人','本期投入','上期投入','本期变化幅度','产品','设计','开发','运维','测试','其它','操作'],
            colModel:[
                {name: 'name', index: 'name', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
                {name:'username',index:'username', sortable:false,fixed:true, width: 140,align: 'center',editable: false},
                {name:'curr',index:'curr', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'prev',index:'prev', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'range',index:'range', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
                {name:'product',index:'product', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'design',index:'design', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'develop',index:'develop', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'oper',index:'oper', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'test',index:'test', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
                {name:'other',index:'other', sortable:false,fixed:true, width: 80,align: 'center', editable: false},
                {name:'myac',index:'', width:100, fixed:true, sortable:false, resize:false}
            ],
            viewrecords : true,
            multiboxonly: true,
            loadComplete : function(xhr) {

                var rows = xhr.rows;
                var ids = $(grid_selector).jqGrid('getDataIDs');
                for (var i = 0; i < ids.length; i++) {
                    var col = ids[i];
                    var	html = '<a href="javascript:void(0)"  class="role_col1" style="margin:0 10px;">查看详情</a>';
                    $(grid_selector).jqGrid('setRowData', col, {myac: html});
                }

                $('.role_col1').unbind().bind('click',function(){
                    $(".sz-window").css("display", "table");
                    $(".shade").show();

                    //取消按钮
                    $(".sz-window .cancelDragBtn").unbind('click').bind('click', function () {
                        closeShade();
                    });
                });
            },
        });

        //关闭按钮
        $(".closeIcon").click(function () {
            closeShade();
        });

        $(".index-jxmb-btn").click(function(){
            var _self = $(this);
            $(".index-jxmb-btn").removeClass("index-jxmb-btn-select");
            _self.addClass("index-jxmb-btn-select");

            $('.sz-window .ct-row .bottom-div').hide();
            $('#'+ _self.attr("page")).show();
        });

        function closeShade() {
            $(".ct-drag").hide();
            $(".shade").hide();
        }
    });
</script>
</body>
</html>