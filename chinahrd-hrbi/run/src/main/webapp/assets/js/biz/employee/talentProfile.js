/**
 * Created by wqcai on 15/09/28 0028.
 */
require(['bootstrap', 'jgGrid', 'underscore'], function () {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        findEmpUrl: webRoot + '/talentProfile/findEmpAll.do',   //查询所有的员工信息
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do'    //跳转到员工详情
    };
    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    
    win.organizationObject = function (organId) {
        reqOrgId = organId;
    }

    var gridOption = {
        url: urls.findEmpUrl,
        datatype: 'json',
        postData: {},
        mtype: 'POST',
        autowidth: true,
        height: 355,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['id', '名称', '所属机构', '岗位', '序列', '职位层级', '操作'],
        colModel: [
            {name: 'empId', index: 'empId', hidedlg: true, hidden: true, width: 40},
            {name: 'userNameCh', index: 'userNameCh', editable: true, width: 120, editoptions: {size: "20", maxlength: "30"}},
            {name: 'organizationName', index: 'organizationName', width: 120, editable: true, editoptions: {size: "20", maxlength: "30"}},
            {name: 'positionName', index: 'positionName', width: 120, editable: true, align: 'center', editoptions: {size: "20", maxlength: "5"}},
            {name: 'sequenceName', index: 'sequenceName', width: 80, editable: true, align: 'center', editoptions: {size: "20", maxlength: "30"}},
            {name: 'abilityName', index: 'abilityName', width: 80, editable: true, align: 'center', editoptions: {size: "20", maxlength: "30"}},
            {
                name: 'myac', index: '', width: 120, fixed: true, align: 'center', sortable: false, resize: false, search: false
//				formatter:'actions',
//				formatoptions:{
//					keys:true,
//					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback}
//				}
            }
        ],
        hideCol: ['userKey'],
        rownumbers: true,
        rownumWidth: 50,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#profileGridPager",
        altRows: true,
        loadComplete: function (xhr) {
            var rows = xhr.rows;
            var gridSelector = profileGridObj.gridId;
            var ids = $(gridSelector).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var col = ids[i];
                var html = '<a href="javascript:void(0)" data-index="' + i + '" class="emp_col" >查看</a>';
                $(gridSelector).jqGrid('setRowData', col, {myac: html});
            }
            $('.emp_col').unbind().bind('click', function (e) {
                var _this = $(this);
                var idx = _this.attr('data-index');
                var userObj = rows[idx];
                //console.log(userObj.empId);
                window.location.href = urls.toEmpDetailUrl + '?empId=' + userObj.empId + '&rand=' + Math.random();
            });

            var table = this;
            setTimeout(function () {
            }, 0);

            var tableHeight=0;var iFrameHeight=0;
            if($("#profileGridTable").data("tableHeight") == undefined){
                tableHeight=$("#profileGridTable").height();
                $("#profileGridTable").data("tableHeight",tableHeight);
            }else{
                tableHeight=$("#profileGridTable").data("tableHeight");
            }
            if($("#profileGridTable").data("iFrameHeight") == undefined){
                iFrameHeight=$("#profileGridTable").parents("body").height();
                $("#profileGridTable").data("iFrameHeight",iFrameHeight);
            }else{
                iFrameHeight=$("#profileGridTable").data("iFrameHeight")
            }
            //window.parent.$("iframe").css({"height":$("#profileGridTable").height()-tableHeight+iFrameHeight});
            $(".ui-jqgrid-bdiv").css({"height":$("#profileGridTable").height()+1+"px"});

            $(".ui-jqgrid-htable,#profileGridTable").css({"width":$("#profileGridPanel").width()+"px"});
        },gridComplete:function(){	
        	//alert(); 
        	window.scrollTo(0,$("#profileGridTable_rn").offset().top);
        }
    };

    var profileGridObj = {
        gridId: '#profileGridTable',
        init: function (keyTxt) {
            var self = this;
            gridOption.postData = {'keyName': keyTxt,'reqOrgId': reqOrgId};
            $(self.gridId).jqGrid(gridOption);
        },
        resizeGridData: function (keyTxt) {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                postData: {'keyName': keyTxt,'reqOrgId': reqOrgId}
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#profileGridPanel').width() * 0.98);
        }
    }

    $(function () {
        $('#firstSearchBtn').click(function () {
            var searchTxt = $.trim($('#firstSearchTxt').val());
            if (!$.isEmptyObject(searchTxt)) {
                initPage(searchTxt);
            }
        });

        $('#lastSearchBtn').click(function () {
            var searchTxt = $.trim($('#lastSearchTxt').val());
            if (!$.isEmptyObject(searchTxt)) {
                profileGridObj.resizeGridData(searchTxt);
            }
        });
        $("#firstSearchTxt").keydown(function(e) {  
        	if (e.keyCode == 13) {
                var firstSearchTxt = $.trim($('#firstSearchTxt').val());
                var hasHide = $('#firstRows').hasClass('hide');
                if (!$.isEmptyObject(firstSearchTxt) && !hasHide) {
                    initPage(firstSearchTxt);
                    return;
                }
                var lastSearchTxt = $.trim($('#lastSearchTxt').val());
                if (!$.isEmptyObject(lastSearchTxt)) {
                    profileGridObj.resizeGridData(lastSearchTxt);
                }
            }
        })  
       $("#lastSearchTxt").keydown(function(e) {  
        	if (e.keyCode == 13) {
                var firstSearchTxt = $.trim($('#firstSearchTxt').val());
                var hasHide = $('#firstRows').hasClass('hide');
                if (!$.isEmptyObject(firstSearchTxt) && !hasHide) {
                    initPage(firstSearchTxt);
                    return;
                }
                var lastSearchTxt = $.trim($('#lastSearchTxt').val());
                if (!$.isEmptyObject(lastSearchTxt)) {
                    profileGridObj.resizeGridData(lastSearchTxt);
                }
            }
        })  
       
        function initPage(searchTxt) {
            $('#lastRows').removeClass('hide');
            $('#firstRows').addClass('hide');
            $('#titleText').html(searchTxt);
            profileGridObj.init(searchTxt);
            $('#lastSearchTxt').val(searchTxt);
        }

        function initKeyNamePage(){
            var keyName = $.trim($('#keyName').val());
            if(keyName != ''){
                initPage(keyName);
            }
        }
        initKeyNamePage();

        $(window).resize(function () {
            profileGridObj.resizeGrid();
        });
    });
    
//    // TODO Test
////  urls.getHighPerformanceUrl= webRoot + '/talentStructure/getConfigWarnVal.do';
////  urls.getHighPerformanceUrl= webRoot + '/talentStructure/getBudgetAnalyse.do';
//  urls.getHighPerformanceUrl= webRoot + '/talentStructure/getTalentStuctureData.do';
////  urls.getHighPerformanceUrl= webRoot + '/talentStructure/getAbEmpCountBarBySeqId.do';
//  $.ajax({
//      url: urls.getHighPerformanceUrl,
//      type:"post",
////      data: {organId: '783c3a74bda747bb9dbd2e1d8b26d14a'}, //gzBusiness
//      data: {organId: 'fcb4d31b3470460f93be81cf1dd64cf0'},
////      data: {organId: '9f570168958c4f23a7d2b86a52f8b79b'},	// GZ
////      data: {organId: '4f4deacb06ae457dad7ab8db27dea35e'}, // SH
////      data: {organId: '423238847d2311e58aee08606e0aa89a'}, // bj
////      data : {organId :'fcb4d31b3470460f93be81cf1dd64cf0', seqId : '05b1f3cb44b411e590e608606e0aa89a'},
//      success: function (result) {
//      	console.log(result);
//      }
//	});

    
});
