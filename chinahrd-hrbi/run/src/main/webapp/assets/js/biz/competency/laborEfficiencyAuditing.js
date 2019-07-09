require(['jquery', 'bootstrap', 'jgGrid', 'underscore', 'utils'], function () {
	
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
    		queryItemInfoUrl: webRoot + '/laborEfficiency/queryItemInfo.do',           //待审核的考勤数据列表
    		queryPersonDetailUrl: webRoot + '/laborEfficiency/queryPersonDetail.do',           //待审核的个人考勤数据
    		updateImportInfoUrl: webRoot + '/laborEfficiency/updateImportInfo.do',           //更新审核状态
    		toLaborEfficiencyViewUrl: webRoot + '/laborEfficiency/toLaborEfficiencyView.do',           //跳转到首页
    };

    $(win.document.getElementById('tree')).next().show();
//    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var reqOrgTxt = win.currOrganTxt;

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        reqOrgTxt = organTxt;
        changeData();
    };
    
    var attId = $("#attId").val();
    
    /**
     * 出勤明细
     * */
    var attendanceDetailObj = {
    	gridId: 'attendanceGrid',
    	tabId: 'attendance-view',
    	isTrue: false,
    	init : function(){
    		var self = this;
    		self.initGridFun();
    	},
    	initGridFun: function(){
    		var self = this;
    		var param = {
    			attId : attId,
    		};
    		if(self.isTrue){
    			self.reloadGridFun(param);
    		}
    		$('#' + self.gridId).jqGrid({
    			url: urls.queryItemInfoUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
                altRows: true, 
                shrinkToFit: false,
				rowHeight: 36,
				styleUI: 'Bootstrap',
                height: 500,
                colNames:['姓名','实际出勤','应出勤','加班','假期统计','查看'],
                colModel:[
        			{name:'userName', sortable:false, width: 215,align: 'center',editable: false},
        			{name:'hourCount', sortable:false, width: 215,align: 'center',editable: false},
        			{name:'beInAttendance', sortable:false, width: 215,align: 'center',editable: false},
        			{name:'overTime', sortable:false, width: 215,align: 'center',editable: false},
        			{name:'other', sortable:false, width: 215,align: 'center',editable: false},
        			{name:'', sortable:false, width: 220,align: 'center',editable: false,
        				formatter: function (value, options, row) {
                            return "<a href='javascript:void(0)' data-key='" + row.userKey + "," + row.userName + "," + row.yearMonth + "' class='user_col' >明细</a>";
                        }
        			}
        		],
                viewrecords: true,
                scroll: true,
                loadComplete: function (xhr) {
                    $('.user_col').unbind().bind('click', function () {
                    	var _this = $(this);
                    	var param = _this.attr('data-key');
                    	personDetailObj.init(param);
                    });
                }
    		});
    		$('#' + self.gridId).setGridHeight($('#' + self.tabId).height() * 0.9);
    		self.isTrue = true;
    		updateAuditingStatusObj.init();
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$('#' + self.gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    		updateAuditingStatusObj.init();
    	}
    }
    
    /**
     * 更新审核状态并入库
     */
    var updateAuditingStatusObj = {
    	init: function() {
    		this.requestFun();
    	},
    	requestFun: function() {
    		$('.auditing-button').unbind().bind('click', function () {
            	var param = {attId: attId};
            	if(confirm('你确定要通过审核吗？')) {
            		$.ajax({
            			url: urls.updateImportInfoUrl,
            			data: param,
            			type: "post",
            			success : function(data){
            				if(data == "1")
            					alert("审核成功并入库！");
            				else 
            					alert("审核失败！！！");
            			},
            			error : function(){
            				alert("链接超时！！！");
            			}
            		}); 
            	}
            	
            });
    	}
    }
    
    /**
     * 出勤明细-查看个人明细
     * */
    var personDetailObj = {
    	modalId : 'personDetailModal',
    	init : function(data){
    		var self = this;
    		self.setModalShowFun(data);
    	},
    	setModalShowFun: function(data){
    		var self = this;
    		var arr = data.split(',');
    		$('#' + self.modalId).modal('show');
			$('#' + self.modalId).on('shown.bs.modal', function() {
				personDetailGridObj.userKey = arr[0];
				personDetailGridObj.userName = arr[1];
				personDetailGridObj.yearMonth = arr[2];
				personDetailGridObj.init();
			});
			$('#' + self.modalId).on('hidden.bs.modal', function() {
    			$('#' + self.modalId).off();
			});
    	},
    }
    
    /**
     * 个人考勤明细
     * */
    var personDetailGridObj = {
    	gridId: 'personDetailGrid',
    	tabId: 'personDetailTab',
    	userKey: null,
    	userName: null,
    	yearMonth: null,
    	isTrue: false,
    	init : function(){
    		var self = this;
    		$("#userNameId").html(self.userName);
    		self.initGridFun();
    		self.personDetailYearSelectFun();
    		self.personDetailOkBtnFun();
    	},
    	initGridFun: function(){
    		var self = this;
    		var param = {
    			userKey : self.userKey,
    			yearMonth : self.yearMonth,
    		};
    		if(self.isTrue){
    			self.reloadGridFun(param);
    		}
    		$('#' + self.gridId).jqGrid({
    			url: urls.queryPersonDetailUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
                altRows: true, 
                shrinkToFit: false,
				rowHeight: 36,
				styleUI: 'Bootstrap',
                height: 300,
                colNames:['日期','实际出勤','应出勤','加班','假期统计'],
                colModel:[
        			{name:'date', sortable:false, width: 140,align: 'center',editable: false,
        				formatter: function (value) {
                            return value.substr(8, 2) + "日";
                        }
        			},
        			{name:'hourCount', sortable:false, width: 140,align: 'center',editable: false},
        			{name:'beInAttendance', sortable:false, width: 140,align: 'center',editable: false},
        			{name:'overTime', sortable:false, width: 140,align: 'center',editable: false},
        			{name:'other', sortable:false, width: 150,align: 'center',editable: false},
        		],
                viewrecords: true,
                scroll: true
    		});
    		$('#' + self.gridId).setGridHeight($('#' + self.tabId).height() * 0.9);
    		self.isTrue = true;
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$('#' + self.gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    	},
    	personDetailYearSelectFun: function() {
    		var self = this;
    		$("#personDetailYearSelect").empty();
    		var option = "";
    		var date = new Date();
    		var year = date.getFullYear();
    		var month = date.getMonth() + 1;
    		for(var i = year; i > year - 5; i --) {
    			if(i == parseInt(self.yearMonth.substr(0, 4))) {
    				option += "<option value='" + i + "' selected>" + i + "年</option>";
    			} else {
    				option += "<option value='" + i + "'>" + i + "年</option>";
    			}
    		}
    		$("#personDetailYearSelect").append(option);
    		self.personDetailMonthSelectFun(month);
    		$("#personDetailYearSelect").change(function() {
    			var selectedYear = $("#personDetailYearSelect option:selected").val();
    			if(selectedYear == parseInt(self.yearMonth.substr(0, 4))) {
    				self.personDetailMonthSelectFun(month);
    			} else {
    				self.changeMonthSelectFun();
    			}
    		});
    	},
    	personDetailMonthSelectFun: function(month) {
    		var self = this;
    		$("#personDetailMonthSelect").empty();
    		var option = "";
    		for(var i = 1; i <= month; i ++) {
    			if(i == parseInt(self.yearMonth.substr(4, 2))) {
    				option += "<option value='" + i + "' selected>" + i + "月</option>";
    			} else {
    				option += "<option value='" + i + "'>" + i + "月</option>";
    			}
    		}
    		$("#personDetailMonthSelect").append(option);
    	},
    	changeMonthSelectFun: function() {
    		var self = this;
    		$("#personDetailMonthSelect").empty();
    		var option = "";
    		for(var i = 1; i <= 12; i ++) {
    			if(i == parseInt(self.yearMonth.substr(4, 2))) {
    				option += "<option value='" + i + "' selected>" + i + "月</option>";
    			} else {
    				option += "<option value='" + i + "'>" + i + "月</option>";
    			}
    		}
    		$("#personDetailMonthSelect").append(option);
    	},
    	personDetailOkBtnFun: function() {
    		var self = this;
    		$("#personDetailOkBtnId").click(function() {
    			var year = $("#personDetailYearSelect option:selected").val();
    			var month = $("#personDetailMonthSelect option:selected").val();
    			self.yearMonth = year + (parseInt(month) > 9 ? month : "0" + month);
    			self.initGridFun();
    		});
    	}
    };

    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData() {
        var selOrganId = reqOrgId;
        attendanceDetailObj.init();
    }

    changeData();

    $(window).resize(function () {
        
    });
    
    $('#toHomeBtn').click(function () {
        win.setlocationUrl(urls.toLaborEfficiencyViewUrl + '?type=1');
    });

});