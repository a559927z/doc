/**
 * Created by wqcai on 15/6/17.
 */
require(['jgGrid', 'bootstrap', 'underscore'], function () {
    var webRoot = G_WEB_ROOT;
    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    var urls = {
        findEmpUrl: webRoot + '/emp/findEmpAll.do',   //查询所有的员工信息
    }
    var win = top != window ? top.window : window;
	$(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    win.organizationObject = function (organId) {
        reqOrgId = organId;
    }

    $("#searchNameTxt").keydown(function (e) {
        if (e.keyCode == 13) {
            //假如焦点在文本框上,则获取文本框的值
            if (document.activeElement.id == 'searchNameTxt') {
                var searchTxt = $.trim($('#searchNameTxt').val());
                //if (!_.isEmpty(searchTxt)) {
                empDetailObj.init(reqOrgId);
              //  }
            }
        }
    });
    
   
    $('#searchBtnId').unbind().bind('click', function(){
    	 var searchNameTxt = $.trim($('#searchNameTxt').val());
        // if (!_.isEmpty(searchNameTxt)) {
        	 $(grid_selector).clearGridData().setGridParam({
                 postData: {'keyName': searchNameTxt, 'reqOrgId': reqOrgId}
             }).trigger("reloadGrid");
        	 var parentDom = $('#gbox_' +  "#grid-table".split('#')[1]).parent();
             $(grid_selector).setGridWidth(parentDom.width());
        // }
    });
    
    
    var empDetailObj = {
    		jqGrid: null,
            organId: null,
            keyName: null,
            gridId: 'grid-table',
            gridPageId: 'grid-pager',
            optionGrid: {
                url: urls.findEmpUrl,
                datatype: 'json',
                mtype: 'POST',
                autowidth: true,
                height: 768,
                colNames: ['id', '员工编码', '员工名称', '组织机构', '操作'],
                colModel: [ {name: 'id', index: 'id', hidedlg: true, hidden: true, width: 60},
                            {
                    name: 'empKey',
                    index: 'empKey',
                    width: 60,
                    editable: true,
                    editoptions: {maxlength: "20"},
                    editrules: {required: true}
                },
                {
                    name: 'empName',
                    index: 'empName',
                    width: 100,
                    editable: true,
                    editoptions: {size: "20", maxlength: "20"},
                    editrules: {required: true}
                },
                {
                    name: 'organizationName',
                    index: 'organizationName',
                    width: 150,
                    editable: true,
                    editoptions: {size: "20", maxlength: "32"},
                    editrules: {required: true}
                },
                {
                    name: 'myac', index: '', width: 200, fixed: true, sortable: false, resize: false
                }
                ],
                hideCol: ['userKey'],
                viewrecords: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                pager: pager_selector,
                altRows: true,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                multiselect: true,
                multiboxonly: true,
                postData: {},
                loadComplete: function (xhr) {
                    var rows = xhr.rows;
                    var ids = $(grid_selector).jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var col = ids[i];
                        var html ='<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configData"><a href="javascript:void(0)" data-index="' + i + '" class="user_col3" style="padding-left:5px;" >配置项目</a></shiro:hasPermission>';
                        $(grid_selector).jqGrid('setRowData', col, {myac: html});
                    }
                    
                    $('.user_col3').unbind().bind('click', function () {
                        var _this = $(this);
                        var idx = _this.attr('data-index');
                        var empObj = rows[idx];
                        //同步
                        window.location.href = webRoot + '/emp/empPro?empId=' + empObj.empId;
                    });
                    $(grid_selector).setGridWidth($(grid_selector).parent().width());
                }
            },
            init: function (orid) {
            	var searchNameTxt = $.trim($('#searchNameTxt').val());
                var self = this;
                self.organId=orid;
                self.keyName=searchNameTxt;
                self.render();
            },
            render: function () {
                var self = this;
                var pd = {reqOrgId: self.organId,keyName:self.keyName};
                if (self.jqGrid) {
                    self.jqGrid.clearGridData().setGridParam({
                        postData: pd
                    }).trigger("reloadGrid");
                } else {
                    self.optionGrid.postData = pd;
                    self.optionGrid.url = urls.findEmpUrl;
                    self.optionGrid.pager = self.gridPageId;
                    self.jqGrid = $("#" + self.gridId).jqGrid(self.optionGrid);
                }
            }
        }
    empDetailObj.init(reqOrgId);
});
