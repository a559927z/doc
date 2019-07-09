require(['jquery', 'ace', 'jquery-pin', 'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger'], function ($) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        toMapsHomeUrl: webRoot + '/talentMaps/toTalentMapsView',          //跳转到人才地图首页
        queryMapManageUrl: webRoot + '/talentMaps/queryMapManage.do',          //查询历史
        getTalentPublishUrl: webRoot + '/talentMaps/getTalentPublish.do',          //查询待发布人员
        getTalentPublishInfoUrl: webRoot + '/talentMaps/getTalentPublishInfo.do',          //查询已发布人员
        getTalentMapsTimeUrl: webRoot + '/talentMaps/getTalentMapsTime.do',          //查询时间机构
    }

    $(win.document.getElementById('tree')).next().hide();
    win.setCurrNavStyle();

    $('#toHomeBtn').click(function () {
        win.setlocationUrl(urls.toMapsHomeUrl);
    });
    
    var topId = $("#topId").val();
    var adjustmentId = $("#adjustmentId").val();
    var releaseId = $("#empId").val();
    var yearMonth = $("#yearMonth").val();
    
    /*var talentMapsTimeObj = {
		halfTime: '#halfTime',
		organization: "#organization",
		time: null,
		organId: null,
    	init: function() {
    		this.requestData();
    	},
    	requestData: function() {
    		var self = this;
    		var id = $("#mapId").val();
    		$.ajax({
    			url: urls.getTalentMapsTimeUrl,
    			type: "post",
    			success: function(data) {
    				self.generate(data);
    			},
    			error: function() { }
    		});
    	},
    	generate: function(data) {
    		var self = this;
    		var arr = [];
    		$.each(data.timeList, function(i, time) {
    			var halfTime = parseInt((time + "").substr(4, 2)) < 7 ? (time + "").substr(0, 4) + '年上半年' : (time + "").substr(0, 4) + '年下半年';
    			if(time == yearMonth)
    				arr.push('<option value="' + time +'" selected>' + halfTime + '</option>');
    			else
    				arr.push('<option value="' + time +'">' + halfTime + '</option>');
    		});
    		$(self.halfTime).append(arr.join(''));
    		self.timeSelectChangeFun();
    		var organArr = [];
    		$.each(data.organPermit, function(i, o) {
    			if(o.organizationId == topId)
    				organArr.push('<option value="' + o.organizationId +'" selected>' + o.organizationName + '</option>');
    			else
    				organArr.push('<option value="' + o.organizationId +'">' + o.organizationName + '</option>');
    		});
    		$(self.organization).append(organArr.join(''));
    		self.organSelectChangeFun();
    	},
    	timeSelectChangeFun: function() {
    		$(self.halfTime).change(function(){
    			var $this = this;
    			var time = $($this).find("option:selected").val();
    			self.time = time;
    			var param = {id: null, topId: (self.organId?self.organId:topId), yearMonth: time};
    			historycalReviewObj.requestData(param);
    			var param1 = {
    	    			topId: self.organId,
    	    			adjustmentId: adjustmentId,
    	    			yearMonth: time
    	    		};
    			talentAdjustmentObj.reloadGridFun(param1);
    			var param2 = {
    	    			topId: self.organId,
    	    			releaseId: releaseId,
    	    			yearMonth: time
    	    		};
    			talentMapsObj.reloadGridFun(param2);
    		});
    	},
    	organSelectChangeFun: function() {
    		$(self.organization).change(function(){
    			var $this = this;
    			var organId = $($this).find("option:selected").val();
    			self.organId = organId;
    			var param = {id: null, topId: organId, yearMonth: (self.time?self.time:yearMonth)};
    			historycalReviewObj.requestData(param);
    			var param1 = {
    	    			topId: organId,
    	    			adjustmentId: adjustmentId,
    	    			yearMonth: self.time
    	    		};
    			talentAdjustmentObj.reloadGridFun(param1);
    			var param2 = {
    	    			topId: organId,
    	    			releaseId: releaseId,
    	    			yearMonth: self.time
    	    		};
    			talentMapsObj.reloadGridFun(param2);
    		});
    	}
    };
    talentMapsTimeObj.init();*/
    
    var historycalReviewObj = {
    	init: function() {
    		var id = $("#mapId").val();
    		var param = {id: id, topId: topId, yearMonth: yearMonth};
    		this.requestData(param);
    	},
    	requestData: function(param) {
    		var self = this;
    		$.ajax({
    			url: urls.queryMapManageUrl,
    			data: param,
    			type: "post",
    			success: function(data) {
    				self.generate(data);
    			},
    			error: function() {
    				
    			}
    		});
    	},
    	generate: function(data) {
    		var startTime = data.startTime;
    		$("#createTime").html(startTime.split('-').join('.').substr(0, 10));
    		
    		var modifyTime = data.modifyTime;
    		$("#auditingTime").html(modifyTime.split('-').join('.').substr(0, 10));
    		$("#auditingName").html(data.curtName);
    		talentAdjustmentObj.init();
    		
    		var releaseTime = data.releaseTime;
    		if(data.flag == 2) {
    			if(undefined != releaseTime) {
        			$("#releaseTime").html(releaseTime.split('-').join('.').substr(0, 10));
        		}
    			$("#releaseName").html(data.userName);
    			talentMapsObj.init();
    		    var cicleTop2 = $("#ui_step_cicle_2").offset().top;
    		    $("#step_line_2").css({"height": cicleTop2+230});
    		    var cicleTop3 = $("#ui_step_cicle_3").offset().top;
    		    $(".content-two").css({"position": "relative", "top": cicleTop3 - cicleTop2 - 40});
    		} else {
    			$("#step_line_2").hide();
    			$("#ui_step_cicle_3").hide();
    			$(".content-two").hide();
    		}
    	}
    };
    historycalReviewObj.init();
    
    var talentAdjustmentObj = {
		gridId: 'talentAdjustmentGrid',
    	pagerId: 'talentAdjustmentPager',
    	tabId: 'tab-content',
    	isTrue : false,
    	init : function(){
    		var self = this;
    		self.initGridFun();
    	},
    	initGridFun: function(){
    		var self = this;
    		var param = {
    			topId: topId,
    			adjustmentId: adjustmentId,
    			yearMonth: yearMonth
    		};
    		if(self.isTrue){
    			self.reloadGridFun(param);
    		}
    		$('#' + self.gridId).jqGrid({
    			url: urls.getTalentPublishUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
                shrinkToFit:false,
                height: 300,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['empId', '', '调整人员', '原位置', '', '调整后位置', '调整说明'],
                colModel: [
					{name:'empId', index: 'empId', key: true, hidden: true},
					{name:'', index: 'empId', key: true, width: 60, 
						formatter: function() {
							return '<div class="emp-img"></div>';
						}
					},
					{name:'', sortable:false,fixed:true, width: 180,align: 'center',editable: false,
						formatter: function(value, option, row) {
							return '</div><div class="emp-text">' + row.userName + '<br>' + row.organName + '/' + row.positionName + '</div>';
						}
					},
					{name:'', sortable:false,fixed:true, width: 180,align: 'center',editable: false,
						formatter: function (value, options, row) {
							return row.xaxisName + "\n" + row.yaxisName;
						}
					},
					{name:'', sortable:false,fixed:true, width: 50,align: 'center',editable: false,
						formatter: function () {
							return '<div class="auditing-map"></div>';
						}
					},
					{name:'', sortable:false,fixed:true, width: 180,align: 'center',editable: false,
						formatter: function (value, options, row) {
							return (_.isNull(row.xaxisAfName)?"":row.xaxisAfName) + "\n" + (_.isNull(row.yaxisAfName)?"":row.yaxisAfName);
					    }
					},
					{name:'note', sortable:false,fixed:true, width: 320,align: 'center',editable: false}
				],
                viewrecords: true,
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#' + self.pagerId,
                loadComplete: function (xhr) {
                	var rowNum = parseInt($(this).getGridParam("records"), 10); 
                	if (rowNum <= 0) { 
                		$('#' + self.gridId).parent().append("<div class=\"norecords\">暂无数据。。。</div>");
                	} else {
                		$('#' + self.gridId).parent().find(".norecords").remove();
                	}
                }
    		});
    		self.isTrue = true;
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$('#' + self.gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    	},
    	resizeGrid: function() {
        	$('#' + this.gridId).setGridWidth($('.' + self.tabId).width() * 0.98);
        }
    };
    
    var talentMapsObj = {
		gridId: 'talentPublishGrid',
    	pagerId: 'talentPublishPager',
    	tabId: 'tab-content',
    	isTrue : false,
    	init : function(){
    		var self = this;
    		self.initGridFun();
    	},
    	initGridFun: function(){
    		var self = this;
    		var param = {
    			topId: topId,
    			releaseId: releaseId,
    			yearMonth: yearMonth
    		};
    		if(self.isTrue){
    			self.reloadGridFun(param);
    		}
    		$('#' + self.gridId).jqGrid({
    			url: urls.getTalentPublishInfoUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
                shrinkToFit:false,
                height: 300,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['empId', '', '调整人员', '原位置', '', '调整后位置', '调整说明'],
                colModel: [
					{name:'empId', index: 'empId', key: true, hidden: true},
					{name:'', index: 'empId', key: true, width: 60, 
						formatter: function() {
							return '<div class="emp-img"></div>';
						}
					},
					{name:'', sortable:false,fixed:true, width: 180,align: 'left',editable: false,
						formatter: function(value, option, row) {
							return '</div><div class="emp-text">' + row.userName + '<br>' + row.organName + '/' + row.positionName + '</div>';
						}
					},
					{name:'', sortable:false,fixed:true, width: 180,align: 'center',editable: false,
						formatter: function (value, options, row) {
							return row.xaxisName + "\n" + row.yaxisName;
						}
					},
					{name:'', sortable:false,fixed:true, width: 50,align: 'center',editable: false,
						formatter: function () {
							return '<div class="auditing-map"></div>';
						}
					},
					{name:'', sortable:false,fixed:true, width: 180,align: 'center',editable: false,
						formatter: function (value, options, row) {
							return (_.isNull(row.xaxisAfName)?"":row.xaxisAfName) + "\n" + (_.isNull(row.yaxisAfName)?"":row.yaxisAfName);
					    }
					},
					{name:'note', sortable:false,fixed:true, width: 320,align: 'center',editable: false}
				],
                viewrecords: true,
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#' + self.pagerId,
                loadComplete: function (xhr) {
                	var rowNum = parseInt($(this).getGridParam("records"), 10); 
                	if (rowNum <= 0) { 
                		$('#' + self.gridId).parent().append("<div class=\"norecords\">暂无数据。。。</div>");
                	} else {
                		$('#' + self.gridId).parent().find(".norecords").remove();
                	}
                }
    		});
    		self.isTrue = true;
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$('#' + self.gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    	},
    	resizeGrid: function() {
        	$('#' + this.gridId).setGridWidth($('.' + self.tabId).width() * 0.98);
        }
    };
    
    $(window).resize(function () {
//    	talentAdjustmentObj.resizeGrid();
//    	talentMapsObj.resizeGrid();
    });
});