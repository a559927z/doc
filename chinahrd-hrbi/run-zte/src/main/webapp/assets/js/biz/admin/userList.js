/**
 * Created by wqcai on 15/6/17.
 */
require(['jgGrid', 'bootstrap', 'underscore'], function () {
    var webRoot = G_WEB_ROOT;
    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";
    
    var urls = {
		findUserAll : webRoot + '/user/findUserAll.do',
		operateUser :webRoot + '/user/operateUser.do',
        getSearchEmpUrl: webRoot + '/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
        userBindEmpUrl: webRoot + '/user/userBindEmp.do',        //获取搜索人员信息
    }

    $(function(){
        $('#confirmRoleBtn').click(function(){  //弹出层点击事件
            confirmRoleEvent();
        });
    });
    var template = "<form class='container' style='width : 300px'> " +
    					"<div class='row form-group'> " +
				    		"<label class='col-xs-4 text-right'>用户名：</label> " +
				    		"<span class='col-xs-8'>{userName}</span> " +
			    		"</div> " +
			    		"<div class='row form-group'> " +
				    		"<label class='col-xs-4 text-right'>中文名：</label> " +
				    		"<span class='col-xs-8'>{userNameCh}</span> " +
			    		"</div> " +
			    		"<div class='row form-group'> " +
				    		"<label class='col-xs-4 text-right'>用户编码：</label> " +
				    		"<span class='col-xs-8'>{userKey}</span> " +
			    		"</div> " +
			    		"<div class='row form-group'> " +
				    		"<label class='col-xs-4 text-right'>备注：</label> " +
				    		"<span class='col-xs-8'>{note}</span> " +
			    		"</div> " +
			    		"<div class='row form-group'> {sData} {cData}</div>"+
	    			"</form>";

    $(grid_selector).jqGrid({
        url: urls.findUserAll,
        datatype: 'json',
        mtype: 'POST',
        autowidth: true,
        height: 326,
        colNames: ['id','用户编码', '用户名', '中文名', '创建时间','密码','备注','操作'],
        colModel: [
           {name: 'id', index: 'id', hidedlg:true,hidden:true,width: 60},
            {name: 'userKey', index: 'userKey', editable: true,width: 60},
            {name: 'userName', index: '', width: 100, hidden:true,editable: true, editoptions: {size: "20", maxlength: "30"},editrules : { required: true}},
            {name: 'userNameCh', index: '', width: 150, editable: true, editoptions: {size: "20", maxlength: "5"},editrules : { required: true}},
            {name: 'createTime', index: 'createTime', width: 100, align: 'center', editable: false, sorttype: "date",editrules : { required: true}},
            {name: 'password', index: 'password', hidden:true,editable: true, editoptions: {size: "20", maxlength: "30",type:'password'}},
            {name: 'note', index: 'note',hidden:true,editable: true,editrules : { required: true}},
            {
                name: 'myac', index: '', width: 200, fixed: true, sortable: false, align:'center',resize: false, search: false
//				formatter:'actions',
//				formatoptions:{
//					keys:true,
//					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback}
//				}
            }
        ],
        hideCol : ['userKey'],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: pager_selector,
        altRows: true,
        multiselect: true,
        multiboxonly: true,
        loadComplete: function (xhr) {
            var rows = xhr.rows;
            var ids = $(grid_selector).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var col = ids[i];
                var html = '<a href="javascript:void(0)" data-index="' + i + '" class="user_col" style="padding-right:5px;">配置角色</a>'
//                + '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configData"><a href="javascript:void(0)" data-index="' + i + '" class="user_col2" >绑定员工</a></shiro:hasPermission>';
                + '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configData"><a href="javascript:void(0)" data-index="' + i + '" class="user_col2" >配置数据</a></shiro:hasPermission>';
                $(grid_selector).jqGrid('setRowData', col, {myac: html});
            }
            $('.user_col').unbind().bind('click', function (e) {
                var _this = $(this);
                var idx = _this.attr('data-index');
                var userObj = rows[idx];
                clickLinkEvent(userObj, e);
            });
//        	$('.user_col2').unbind().bind('click',function(){
//				var _this = $(this);
//				var idx = _this.attr('data-index');
//				var userObj = rows[idx];
//		        var userId = userObj.userId;
//		        console.log(userId);
//		        $('#searchUserId').val(userId);
//
//		        $('#searchModal').modal('show');
//
//			});
			$('.user_col2').unbind().bind('click',function(){
				var _this = $(this);
				var idx = _this.attr('data-index');
				var userObj = rows[idx];
				//异步
// 				window.location.href = webRoot + '/role/roleOrganAsync?roleId='+roleObj.roleId;
				//同步
				window.location.href = webRoot + '/user/userOrgan?userId='+userObj.userId;
			});

            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
        },
        editurl: urls.operateUser,  //nothing is saved
        caption: '用户管理'
    });
    //navButtons
    $(grid_selector).navGrid(pager_selector,
        {
            edit: true,
            editicon: 'icon-pencil blue',
            add: true,
            addicon: 'icon-plus-sign purple',
            del: true,
            delicon: 'icon-trash red',
            search: true,
            searchicon: 'icon-search orange',
            refresh: true,
            refreshicon: 'icon-refresh green',
            view: true,
            viewicon: 'icon-zoom-in grey'
        },
        {
            editCaption: "修改",
            width : 350,
			template: template,
			closeAfterEdit: true,
            errorTextFormat: function (data) {
                return 'Error: ' + data.responseText
            }
        },
        // options for the Add Dialog
        {
        	editCaption: "新增",
			template: template,
			width : 350,
			closeAfterAdd: true,
            errorTextFormat: function (data) {
                return 'Error: ' + data.responseText
            }
        }
    )

    function beforeDeleteCallback(e) {
        var form = $(e[0]);
    }

    function clickLinkEvent(obj, e) {
        //console.log(obj);
        var userId = obj.userId;
        var label = '(' + obj.userNameCh + ')';
        $('#roleUserId').val(userId);
        $('#userRoleModalLabel small').text(label);

        $.post(webRoot + '/user/findRoleOfUser', {'userId': userId}, function (rs) {
            var notSelect = '', isSelect = '';
            $.each(rs, function (i, obj) {
            	var selected = obj.isSelect;
            	var _link = '<a href="javascript:void(0)" data-id="' + obj.roleId + '" data-select="'+selected+'" class="list-group-item">' + obj.roleName + '</a>';
                if (selected) {
                	isSelect += _link;
                } else {
                	notSelect += _link;
                }
            });
            $('#notSelectPanel').html(notSelect);
            $('#isSelectPanel').html(isSelect);
            $('#modalPanel .list-group-item').unbind().bind('click',function(e){
                selectItemEvent(this);
            });
            $('#userRoleModal').modal('show');
        });
    }

    function selectItemEvent(obj){
        var _this = $(obj);
        var newObj = obj;
        var isSelect = _this.attr('data-select');
        var _parentPanel = isSelect == 0 ? $('#isSelectPanel') : $('#notSelectPanel');
        $(newObj).attr('data-select',isSelect == 0 ? 1 : 0);
        _this.remove();
        _parentPanel.append(newObj);

        $('#modalPanel .list-group-item').unbind().bind('click',function(e){
            selectItemEvent(this);
        });
    }

    function confirmRoleEvent(){
        var userId = $('#roleUserId').val();
        var roleIds = [];
        var selectItems = $('#isSelectPanel').children();
        $.each(selectItems,function(i,item){
            roleIds.push($(item).attr('data-id'));
        });
        $.post(webRoot + '/user/addUserRoles',{'userId':userId,'roleIds':roleIds},function(rs){
            if(rs.type){
                $('#userRoleModal').modal('hide');
            }
        });
    }

//    function beforeEditCallback(e) {
//        var form = $(e[0]);
//    }
//
//    function aceSwitch(cellvalue, options, cell) {
//    }
//
//
//    function styleCheckbox(table) {
//
//    }
//
//
//    function updateActionIcons(table) {
//
//    }

//
    function updatePagerIcons(table) {
        var replacement =
        {
            'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev': 'icon-angle-left bigger-140',
            'ui-icon-seek-next': 'icon-angle-right bigger-140',
            'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }
//
//    function enableTooltips(table) {
//
//    }
    
    window.onresize = function  _doResize() { 
    	//XXX 修改
    	return;
    	 var ss = pageSize(); 
    	 $(grid_selector).jqGrid('setGridWidth', ss.WinW-10).jqGrid('setGridHeight', ss.WinH-200);
    	 }
    	   
    	 function pageSize() { 
    	   var winW, winH; 
    	  if(window.innerHeight) {// all except IE 
    	   winW = window.innerWidth; 
    	   winH = window.innerHeight; 
    	  } else if (document.documentElement && document.documentElement.clientHeight) {// IE 6 Strict Mode
    	   winW = document.documentElement.clientWidth; 
    	   winH = document.documentElement.clientHeight; 
    	  } else if (document.body) { // other 
    	   winW = document.body.clientWidth; 
    	   winH = document.body.clientHeight; 
    	  }  // for small pages with total size less then the viewport 
    	  return {WinW:winW, WinH:winH}; 
    	} 
    	 
    	 
    	 var searchEmpObj = {
    		        gridId: '#searchEmpGrid',
    		        searchTxt: null,
    		        gridOption: {
    		            url: urls.getSearchEmpUrl,
    		            datatype: 'json',
    		            postData: {},
    		            mtype: 'POST',
    		            autowidth: true,
    		            height: 268,//268
    		            colNames: ['员工ID', '姓名', '部门', '操作'],
    		            colModel: [
    		                {name: 'empKey', width: 80, sortable: false, align: 'center'},
    		                {name: 'userName', width: 150, sortable: false, align: 'center'},
    		                {name: 'organName', width: 200, sortable: false, align: 'center'},
    		                {name: 'myac', width: 100, fixed: true, sortable: false, align: 'center'}
    		            ],
    		            rownumbers: true,
    		            rownumWidth: 40,
    		            loadComplete: function (xhr) {
    		                var rows = xhr.rows;
    		                var _this = $(searchEmpObj.gridId);
    		                var ids = _this.jqGrid('getDataIDs');
    		                for (var i = 0; i < ids.length; i++) {
    		                    var col = ids[i];
    		                    var html = '<a href="javascript:void(0)" data-index="' + i + '" class="add_col" >绑定</a>';
    		                    _this.jqGrid('setRowData', col, {myac: html});
    		                }

    		                $('.add_col').unbind().bind('click', function (e) {
    		                    var _this = $(this);
    		                    var idx = _this.attr('data-index');
    		                    var userObj = rows[idx];
    		                    var empId = userObj.empId;
    		                    var userId=$("#searchUserId").val();
    		                    $.ajax({
    		    					url : urls.userBindEmpUrl,
    		    					type : 'post',
    		    					dataType : 'json',
    		    					data:{empId: empId,userId:userId},
    		    					async: true,	//同步
    		    					success : function(data) {
    		    						console.log(data);
    		    						//treeData = data;
    		    					}
    		    				});
    		                    $('#searchModal').modal('hide');
    		                });
    		            },
    		            scroll: true
    		        },
    		        init: function (searchTxt) {
    		            var self = this;
    		            if (_.isNull(self.searchTxt)) {
    		                self.searchTxt = searchTxt;
    		                self.gridOption.postData = {'keyName': searchTxt};
    		                $(self.gridId).jqGrid(self.gridOption);
    		            }
    		            if (searchTxt != self.searchTxt) {
    		                self.searchTxt = searchTxt;
    		                self.initGrid(searchTxt);
    		            }
    		        },
    		        initGrid: function (keyTxt) {
    		            var self = this;
    		            $(self.gridId).clearGridData().setGridParam({
    		                postData: {'keyName': keyTxt}
    		            }).trigger("reloadGrid");
    		            self.resizeGrid();
    		        },
    		        resizeGrid: function () {
    		            var self = this;
    		            var parentDom = $('#gbox_' + self.gridId.split('#')[1]).parent();
    		            $(self.gridId).setGridWidth(parentDom.width());
    		        }
    		    };
    	 $('#searchBtn').click(function () {
	            var searchTxt = $.trim($('#searchTxt').val());
	            if (!_.isEmpty(searchTxt)) {
	                searchEmpObj.init(searchTxt);
	            }
	        });
	    	 $("#searchTxt").keydown(function (e) {
	             if (e.keyCode == 13) {
	                 //假如焦点在文本框上,则获取文本框的值
	                 if (document.activeElement.id == 'searchTxt') {
	                     var searchTxt = $.trim($('#searchTxt').val());
	                     if (!_.isEmpty(searchTxt)) {
	                         searchEmpObj.init(searchTxt);
	                     }
	                 }
	             }
	         });
});
