require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/pie', 'echarts/chart/map', 'echarts/chart/heatmap',
         'bootstrap', 'jgGrid', 'underscore', 'timeLine2', 'selection', 'datetimepicker'], function ($, echarts) {

    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    $(win.document.getElementById('tree')).next().show();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var ecConfig = require('echarts/config');
    var TextShape = require('zrender/shape/Text');
    var urls = {
        memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
//        queryTalentProfitLossValUrl: webRoot + '/talentProfitLoss/queryTalentProfitLossVal.do',	//获取人才损益值
        queryTalentInflowValUrl: webRoot + '/talentProfitLoss/queryTalentInflowVal.do',	//获取流入统计
        queryTalentOutflowValUrl: webRoot + '/talentProfitLoss/queryTalentOutflowVal.do',	//获取流出统计
        queryInflowDetailBtnsUrl: webRoot + '/talentProfitLoss/queryInflowDetailBtns.do',	//获取流入统计-异动统计(明细)-按钮组
        queryOutflowDetailBtnsUrl: webRoot + '/talentProfitLoss/queryOutflowDetailBtns.do',	//获取流出统计-异动统计(明细)-按钮组
        queryTalentInflowDetailUrl: webRoot + '/talentProfitLoss/queryTalentInflowDetail.do',	//获取流入统计-异动统计(明细)
        queryTalentOutflowDetailUrl: webRoot + '/talentProfitLoss/queryTalentOutflowDetail.do',	//获取流出统计-异动统计(明细)
        queryTimecrowdUrl: webRoot + '/talentProfitLoss/queryTimecrowd.do',	//获取时间人群切片
        queryPopulationMapUrl: webRoot + '/talentProfitLoss/queryPopulationMap.do',	//人才损益-人员分布
        queryPopulationPieUrl: webRoot + '/talentProfitLoss/queryPopulationPie.do',	//人才损益-人员分布
        queryTalentProfitLossRingDataUrl: webRoot + '/talentProfitLoss/queryTalentProfitLossRingData.do',	//人才损益环比
        queryTalentProfitLossSameDataUrl: webRoot + '/talentProfitLoss/queryTalentProfitLossSameData.do',	//人才损益同比
    };
    var pieDefaultColor = ['#0b98e0', '#00bda9', '#4573a7', '#92c3d4', '#de6e1b', '#ff0084', '#af00e1', '#8d55f6', '#6a5888', '#2340f3'];
    var defaultDatas = {
		profitLossMonthNum : 0,
		profitLossYearNum : 0,
		inflowMonthNum : 0,
		inflowMonthText : null,
		inflowYearNum : 0,
		inflowYearText : null,
		outflowMonthNum : 0,
		outflowMonthText : null,
		outflowYearNum : 0,
		outflowYearText : null,
		inflowFlag : 0,
		outflowFlag : 0
    }

    /** 本月本年按钮切换 */
    var toolbarStyleObj = {
		profitLossValId: "talentProfitLossVal_toolbar",
		inflowBarId: "inflowCount_toolbar",
		outflowBarId: "outflowCount_toolbar",
		profitLossNumId: 'profitLossNum',
		inflowCountNumId: 'inflowCountNum',
		inflowCountTextId: 'inflowPersonText',
		outflowCountNumId: 'outflowCountNum',
		outflowCountTextId: 'outflowPersonText',
    	init : function(){
    		var self = this;
    		self.checkToolbarStyleFun(self.profitLossValId);
    		self.checkToolbarStyleFun(self.inflowBarId);
    		self.checkToolbarStyleFun(self.outflowBarId);
    	},
    	checkToolbarStyleFun: function(id){
    		var self = this;
    		$("#"+id+" span").unbind('click').bind('click',function(){
        		var par = $(this).parent();
        		var _t = this;
        		$.each(par.children(), function (i, o) {
        			if (this == _t) {
        				$(this).addClass("select");
        				if($(this).parent().prev().html() == '人才损益值'){
        					if($(this).text() == '本月'){
        						$("#" + self.profitLossNumId).html(defaultDatas.profitLossMonthNum);
        					}else{
        						$("#" + self.profitLossNumId).html(defaultDatas.profitLossYearNum);
        					}
        				} else if($(this).parent().prev().html() == '流入统计'){
        					if($(this).text() == '本月'){
        						$("#" + self.inflowCountNumId).html(defaultDatas.inflowMonthNum);
        						$("#" + self.inflowCountTextId).html(defaultDatas.inflowMonthText);
        					}else{
        						$("#" + self.inflowCountNumId).html(defaultDatas.inflowYearNum);
        						$("#" + self.inflowCountTextId).html(defaultDatas.inflowYearText);
        					}
    	    			} else if($(this).parent().prev().html() == '流出统计'){
    	    				if($(this).text() == '本月'){
    	    					$("#" + self.outflowCountNumId).html(defaultDatas.outflowMonthNum);
    	    					$("#" + self.outflowCountTextId).html(defaultDatas.outflowMonthText);
    	    				}else{
    	    					$("#" + self.outflowCountNumId).html(defaultDatas.outflowYearNum);
    	    					$("#" + self.outflowCountTextId).html(defaultDatas.outflowYearText);
    	    				}
    	    			}
        			} else {
        				$(this).removeClass("select");
        			}
        		});
        	});
    	}
    }
    
    /**
     * 人才损益值
     * */
    var profitLossValObj = {
    	toolbarId: 'talentProfitLossVal_toolbar',
    	numId: 'profitLossNum',
    	init: function(){
    		var self = this;
    		self.loadDatasFun();
    	},
    	loadDatasFun: function(data){
    		var self = this;
    		defaultDatas.profitLossMonthNum = defaultDatas.inflowMonthNum - defaultDatas.outflowMonthNum;
    		defaultDatas.profitLossYearNum = defaultDatas.inflowYearNum - defaultDatas.outflowYearNum;
    		/*if(defaultDatas.inflowFlag == 1 || defaultDatas.outflowFlag == 1){
    			defaultDatas.profitLossMonthNum = defaultDatas.inflowMonthNum - defaultDatas.outflowMonthNum;
    			defaultDatas.profitLossYearNum = defaultDatas.inflowYearNum - defaultDatas.outflowYearNum;
    		} else {
    			defaultDatas.profitLossMonthNum = 0;
    			defaultDatas.profitLossYearNum = 0;
    		}*/
    		var name = $('#' + self.toolbarId).find('.select').text();
    		if(name == '本月'){
    			$("#" + self.numId).html(defaultDatas.profitLossMonthNum);
    		} else {
    			$("#" + self.numId).html(defaultDatas.profitLossYearNum);
    		}
    	}
    }
    /**
     * 流入统计
     * */
    var inflowCountObj = {
		toolbarId: 'inflowCount_toolbar',
    	numId: 'inflowCountNum',
    	textId: 'inflowPersonText',
    	init: function(organId){
    		var self = this;
    		self.getRequestDatasFun(organId);
    	},
    	getRequestDatasFun: function(organId){
    		var self = this;
    		var param = {organId : organId};
    		$.ajax({
    			url : urls.queryTalentInflowValUrl,
    			type : 'post',
    			data : param,
    			success : function(data){
    				self.loadDatasFun(data);
    			},
    			error : function(){}
    		});
    	},
    	loadDatasFun: function(data){
    		var self = this;
    		defaultDatas.inflowMonthNum = data.monthCountNum;
    		defaultDatas.inflowMonthText = data.monthCountName;
    		defaultDatas.inflowYearNum = data.yearCountNum;
    		defaultDatas.inflowYearText = data.yearCountName;
    		defaultDatas.inflowFlag = 1;
    		var name = $('#' + self.toolbarId).find('.select').text();
    		if(name == '本月'){
    			$("#" + self.numId).html(defaultDatas.inflowMonthNum);
    			$("#" + self.textId).html(defaultDatas.inflowMonthText);
    		} else {
    			$("#" + self.numId).html(defaultDatas.inflowYearNum);
    			$("#" + self.textId).html(defaultDatas.inflowYearText);
    		}
    		profitLossValObj.init();
    	}
    }
    
    /**
     * 流出统计
     * */
    var outflowCountObj = {
		toolbarId: 'outflowCount_toolbar',
    	numId: 'outflowCountNum',
    	textId: 'outflowPersonText',
		init: function(organId){
    		var self = this;
    		self.getRequestDatasFun(organId);
    	},
    	getRequestDatasFun: function(organId){
    		var self = this;
    		var param = {organId : organId};
    		$.ajax({
    			url : urls.queryTalentOutflowValUrl,
    			type : 'post',
    			data : param,
    			success : function(data){
    				self.loadDatasFun(data);
    			},
    			error : function(){}
    		});
    	},
    	loadDatasFun: function(data){
    		var self = this;
    		defaultDatas.outflowMonthNum = data.monthCountNum;
    		defaultDatas.outflowMonthText = data.monthCountName;
    		defaultDatas.outflowYearNum = data.yearCountNum;
    		defaultDatas.outflowYearText = data.yearCountName;
    		defaultDatas.outflowFlag = 1;
    		var name = $('#' + self.toolbarId).find('.select').text();
    		if(name == '本月'){
    			$("#" + self.numId).html(defaultDatas.outflowMonthNum);
    			$("#" + self.textId).html(defaultDatas.outflowMonthText);
    		} else {
    			$("#" + self.numId).html(defaultDatas.outflowYearNum);
    			$("#" + self.textId).html(defaultDatas.outflowYearText);
    		}
    		profitLossValObj.init();
    	}
    }
    
    /**
     * 流入统计/流出统计-异动统计（明细）
     * */
    var inflowOutflowObj = {
    	inflowToolbarId: 'inflowCount_toolbar',
    	outflowToolbarId: 'outflowCount_toolbar',
    	inflowCountId: 'inflowCountDetail',
    	outflowCountId: 'outflowCountDetail',
    	detailDialogId: 'inflowOutflowDetailModal',
    	detailTabsId: 'inflowOutflowDetailTabs',
    	inflowBtnGroupId: 'inflowBtnGroups',
    	outflowBtnGroupId: 'outflowBtnGroups',
    	inflowTableId: 'inflowTable',
    	outflowTableId: 'outflowTable',
    	inflowTabId: 'inflowTab',
    	outflowTabId: 'outflowTab',
    	inflowGridId: 'inflowGrid',
    	outflowGridId: 'outflowGrid',
    	inflowPagerId: 'inflowGridPager',
    	outflowPagerId: 'outflowGridPager',
    	inflowNumId: 'inflowNum',
    	outflowNumId: 'outflowNum',
    	date: 'month',
    	organId: null,
    	inflowTabFlag: '0',
    	outflowTabFlag: '1',
    	flag: '',
    	inIsTrue: false,
    	outIsTrue: false,
    	init: function(organId){
    		var self = this;
    		self.organId = organId;
    		self.showCountDetailFun(self.inflowCountId, self.inflowTabFlag, self.inflowToolbarId);
    		self.showCountDetailFun(self.outflowCountId, self.outflowTabFlag, self.outflowToolbarId);
    	},
    	showCountDetailFun: function(countId, flag, toolbarId){
    		var self = this;
    		$('#' + countId).unbind('click').bind('click',function(){
    			$('#' + self.detailDialogId).modal('show');
    			$('#' + self.detailDialogId).on('shown.bs.modal', function() {
    				var name = $('#' + toolbarId).find('.select').text();
    				if(name == '本年'){
    					self.date = 'year';
    					$('#' + self.inflowNumId).html(defaultDatas.inflowYearNum);
    					$('#' + self.outflowNumId).html(defaultDatas.outflowYearNum);
    				} else {
    					$('#' + self.inflowNumId).html(defaultDatas.inflowMonthNum);
    					$('#' + self.outflowNumId).html(defaultDatas.outflowMonthNum);
    				}
    				self.inflowOutflowTabsChangeFun();
    				if(flag == self.inflowTabFlag){
    					$("#" + self.detailDialogId + " ul li:eq(0)").addClass('active').siblings().removeClass('active');
    					$('#' + self.outflowTabId).removeClass('active');
    					$('#' + self.inflowTabId).addClass('active');
    					self.getInflowDetailBtnsFun();
    					self.getInflowDatasFun();
    				}
    				if(flag == self.outflowTabFlag){
    					$("#" + self.detailDialogId + " ul li:eq(1)").addClass('active').siblings().removeClass('active');
    					$('#' + self.inflowTabId).removeClass('active');
    					$('#' + self.outflowTabId).addClass('active');
    					self.getOutflowDetailBtnsFun();
    					self.getOutflowDatasFun();
    				}
    			});
    		});
    	},
    	getInflowDetailBtnsFun: function(){
    		var self = this;
    		$.ajax({
    			url : urls.queryInflowDetailBtnsUrl,
    			type: 'post',
    			success: function(data){
    				self.loadInflowBtnsFun(data);
    			},
    			error: function(){}
    		});
    	},
    	loadInflowBtnsFun: function(data){
    		var self = this;
    		$('.inflow-btns-tr').remove();
    		var tr = "<tr class='inflow-btns-tr'><td class='btn-div selected' id=''>全部</td>";
    		$.each(data, function(index, object){
    			tr += "<td class='btn-div' id='" + object.curtName + "'>" + object.changeTypeName + "</td>";
    		})
    		tr += "</tr>";
    		$('#' + self.inflowBtnGroupId).append(tr);
    		self.setBtnGroupsSelectedFun(self.inflowBtnGroupId);
    		self.detailBtnGroupsChangeFun(self.inflowBtnGroupId, self.inflowTabFlag);
    	},
    	getOutflowDetailBtnsFun: function(){
    		var self = this;
    		$.ajax({
    			url : urls.queryOutflowDetailBtnsUrl,
    			type: 'post',
    			success: function(data){
    				self.loadOutflowBtnsFun(data);
    			},
    			error: function(){}
    		});
    	},
    	loadOutflowBtnsFun: function(data){
    		var self = this;
    		$('.outflow-btns-tr').remove();
    		var tr = "<tr class='outflow-btns-tr'><td class='btn-div selected' id=''>全部</td>";
    		$.each(data, function(index, object){
    			tr += "<td class='btn-div' id='" + object.curtName + "'>" + object.changeTypeName + "</td>";
    		})
    		tr += "</tr>";
    		$('#' + self.outflowBtnGroupId).append(tr);
    		self.setBtnGroupsSelectedFun(self.outflowBtnGroupId);
    		self.detailBtnGroupsChangeFun(self.outflowBtnGroupId, self.outflowTabFlag);
    	},
    	getInflowDatasFun: function(){
    		var self = this;
    		var param = {
    			organId : self.organId,
    			date : self.date,
    			flag : self.flag
    		};
    		if(self.inIsTrue){
    			self.reloadGridFun(self.inflowGridId, param);
    			return;
    		}
    		$('#' + self.inflowGridId).setGridWidth($('#' + self.inflowTabId).width() * 0.98);
    		$('#' + self.inflowGridId).jqGrid({
    			url : urls.queryTalentInflowDetailUrl,
    			postData : param,
    			mtype: 'POST',
		        datatype: "json",
		        height: 200,
		        colNames:['姓名','性别','部门','学历','岗位','职级','异动类型','异动日期'],
		        colModel:[
					{name:'userName',sortable:false,width:100,fixed:true, align: 'center',editable: false},
					{name:'sex',sortable:false,width:80,fixed:true, align: 'center',editable: false,
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == 'm'){
								return '男';
							} else if(cellvalue == 'w'){
								return '女';
							} else {
								return '';
							}
						}
					},
					{name:'organName',sortable:false,width:120,fixed:true, align: 'center',editable: false},
					{name:'degree',sortable:false,width:100,fixed:true, align: 'center',editable: false},
					{name:'positionName',sortable:false,width:120,fixed:true, align: 'center',editable: false},
					{name:'rankName',sortable:false,width:80,fixed:true, align: 'center',editable: false},
					{name:'changeTypeName',sortable:false,width:100,fixed:true, align: 'center',editable: false},
					{name:'changeDate',sortable:false,width:100,fixed:true, align: 'center',editable: false}
				],
				page: 1,
				rowNum: 10,
		        rowList: [10, 20, 30],
		        pager : '#' + self.inflowPagerId,
//		        rownumbers: true,
		        hoverrows : false,
		        viewrecords: true,
//		        editable : false,
		        autowidth: true,
		        loadComplete: function (xhr) {
		            setTimeout(function () {
		            	Tc.updatePagerIcons();
		            }, 0);
		        }
		    });
    		self.inIsTrue = true;
    	},
    	getOutflowDatasFun: function(){
    		var self = this;
    		var param = {
    			organId : self.organId,
    			date : self.date,
    			flag : self.flag
    		};
    		if(self.outIsTrue){
    			self.reloadGridFun(self.outflowGridId, param);
    			return;
    		}
    		$('#' + self.outflowGridId).setGridWidth($('#' + self.outflowTabId).width() * 0.98);
    		$('#' + self.outflowGridId).jqGrid({
    			url : urls.queryTalentOutflowDetailUrl,
    			postData : param,
    			mtype: 'POST',
		        datatype: "json",
		        height: 200,
		        colNames:['姓名','性别','部门','学历','岗位','职级','异动类型','异动日期'],
		        colModel:[
					{name:'userName',sortable:false,width:100,fixed:true, align: 'center',editable: false},
					{name:'sex',sortable:false,width:80,fixed:true, align: 'center',editable: false,
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == 'm'){
								return '男';
							} else if(cellvalue == 'w'){
								return '女';
							} else {
								return '';
							}
						}
					},
					{name:'organName',sortable:false,width:120,fixed:true, align: 'center',editable: false},
					{name:'degree',sortable:false,width:100,fixed:true, align: 'center',editable: false},
					{name:'positionName',sortable:false,width:120,fixed:true, align: 'center',editable: false},
					{name:'rankName',sortable:false,width:80,fixed:true, align: 'center',editable: false},
					{name:'changeTypeName',sortable:false,width:100,fixed:true, align: 'center',editable: false},
					{name:'changeDate',sortable:false,width:100,fixed:true, align: 'center',editable: false}
				],
				page: 1,
				rowNum: 10,
		        rowList: [10, 20, 30],
		        pager : '#' + self.outflowPagerId,
//		        rownumbers: true,
		        hoverrows : false,
                viewrecords: true,
//		        editable : false,
		        autowidth: true,
		        loadComplete: function (xhr) {
		            setTimeout(function () {
		            	Tc.updatePagerIcons();
		            }, 0);
		        }
		    });
    		self.outIsTrue = true;
    	},
    	reloadGridFun: function(gridId, param){
    		$('#' + gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    	},
    	inflowOutflowTabsChangeFun: function(){
    		var self = this;
    		$('#' + self.detailDialogId + ' ul li').unbind('click').bind('click', function(){
    			var par = $(this).parent();
    			var _t = this;
    			$.each(par.children(), function (i, o) {
    				if (this == _t) {
    					$(this).addClass('active').siblings().removeClass('active');
    					var name = $(this).find('a').text();
    					if(name.indexOf('流入') != -1){
    						$('#' + self.outflowTabId).removeClass('active');
        					$('#' + self.inflowTabId).addClass('active');
    						self.getInflowDetailBtnsFun();
    						self.getInflowDatasFun();
    					} else {
    						$('#' + self.inflowTabId).removeClass('active');
    						$('#' + self.outflowTabId).addClass('active');
    						self.getOutflowDetailBtnsFun();
    						self.getOutflowDatasFun();
    					}
    				}
    			})
    		});
    	},
    	detailBtnGroupsChangeFun: function(btnGroupId, flag){
    		var self = this;
    		$('#' + btnGroupId + ' tr td').unbind('click').bind('click', function(){
    			var par = $(this).parent();
    			var _t = this;
    			$.each(par.children(), function (i, o) {
    				if (this == _t) {
    					$(this).addClass('selected').siblings().removeClass('selected');
    					var curtName = $(this).attr('id');
    					if(flag == self.inflowTabFlag){
    						self.flag = curtName;
    						self.getInflowDatasFun();
    					}
    					if(flag == self.outflowTabFlag){
    						self.flag = curtName;
    						self.getOutflowDatasFun();
    					}
    				}
    			})
    		});
    	},
    	setBtnGroupsSelectedFun: function(btnGroupId){
    		$('#' + btnGroupId + ' tr td:first').addClass('selected').siblings().removeClass('selected');
    	}
    }
    
    /**
     * 时间人群切片
     * */
    var timecrowdObj = {
    	populationId : 'populationTimecrowd',
    	ringId : 'ringTimecrowd',
    	sameId : 'sameTimecrowd',
    	organId : null,
    	init : function(organId){
    		var self = this;
    		self.organId = organId;
    		self.getRequestDataFun();
    	},
    	getRequestDataFun: function(){
    		var self = this;
    		var param = {organId : self.organId};
    		$.ajax({
    			url : urls.queryTimecrowdUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				self.populationTimecrowdFun(data);
    				self.ringTimecrowdFun(data);
    				self.sameTimecrowdFun(data);
    			},
    			error : function(){}
    		});
    	},
    	populationTimecrowdFun: function(data){
    		var self = this;
    		 $('#' + self.populationId).selection({
 				dateType: 7,
 				dateRange:{
 					min: data.minDate.substr(1, 4),
 					max: data.maxDate.substr(1, 4)
 				},
 				dateSelected: data.selectedDateOne,
 				crowdSelected:['0'],
 				ok:function(event, data){
 					populationMapObj.times = data.date.join('@');
 					populationPieObj.times = data.date.join('@');
					if(data.crowd != undefined){
						populationMapObj.crowds = data.crowd.join('@');
						populationPieObj.crowds = data.crowd.join('@');
					}
					populationMapObj.init(self.organId);
					populationPieObj.init(self.organId);
 				}
 			});
    	},
    	ringTimecrowdFun: function(data){
    		var self = this;
			$('#' + self.ringId).selection({
				dateType:2,
				dateRange:{
					min: data.minDate,
					max: data.maxDate
				},
				dateSelected: data.selectedDate,
				dateSelectedLength: 6,
				crowdSelected: ['0'],
				ok: function(event, data){
					profitLossRingObj.times = data.date.join('@');
					if(data.crowd != undefined){
						profitLossRingObj.crowds = data.crowd.join('@');
					}
					profitLossRingObj.init(self.organId);
				}
			});
    	},
    	sameTimecrowdFun: function(data){
    		var self = this;
    		$('#' + self.sameId).selection({
				dateType:2,
				dateRange:{
					min: data.minDate,
					max: data.maxDate
				},
				dateSelected: data.selectedDate,
				dateSelectedLength: 6,
				crowdSelected: ['0'],
				ok: function(event, data){
					profitLossSameObj.times = data.date.join('@');
					if(data.crowd != undefined){
						profitLossSameObj.crowds = data.crowd.join('@');
					}
					profitLossSameObj.init(self.organId);
				}
			});
    	}
    }
    /**
     * 人员分布-地图
     * */
    var populationMapOption = {
		tooltip : {
			trigger : 'item',
			formatter : '{b}'
		},
		dataRange : {
//			min : 0,
//			max : 1000,
			x : 'center',
			y : 'top',
			text : [ '高', '低' ],
			orient : 'horizontal'
		},
		series : [ {
			type : 'map',
			mapType : 'china',
			roam : false,
			selectedMode : 'single',
			data : [ ]
		} ]
    }
    /**
     * 人员分布-地图
     * */
    var populationMapObj = {
    	chart : initEChart('populationMapChart'),
    	option: populationMapOption,
    	organId: null,
    	times: null,
		crowds: null,
    	init : function(organId){
    		var self = this;
    		self.organId = organId;
    		clearEChart(self.chart);
    		self.getRequestDatasFun();
    	},
    	getRequestDatasFun: function(){
    		var self = this;
    		var param = {
				organId : self.organId,
				times : self.times,
				crowds : self.crowds
			};
    		$.ajax({
    			url : urls.queryPopulationMapUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				self.getOptionFun(data);
    			},
    			error : function(){}
    		});
    	},
    	getOptionFun: function(data){
    		var self = this;
    		if(data && data.maxNum > 0){
    			self.option.dataRange.min = data.minNum;
    			self.option.dataRange.max = data.maxNum;
    			self.option.series[0].data = data.list;
    			self.setOptionFun();
    		} else {
    			showNoDataEcharts(self.chart);
    		}
    	},
    	setOptionFun: function(){
    		var self = this;
    		self.chart.setOption(self.option);
    		self.optionClickFun();
    	},
    	optionClickFun: function(){
			var self = this;
			self.chart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
			    var selected = param.selected;
			    var str;
			    for (var p in selected) {
			        if (selected[p]) {
			            str = p;
			        }
			    }
			    populationPieObj.provinceName = str;
			    populationPieObj.init(self.organId);
			})
		}
    }
    /**
     * 人员分布-饼图
     * */
    var populationPieOption = {
		series : [ {
			type : 'pie',
			radius : '60%',
			center : [ '50%', '50%' ],
			selectedMode : 'single',
			data : [],
		} ],
		color : pieDefaultColor
    }
    /**
     * 人员分布-饼图
     * */
    var populationPieObj = {
    		chart : initEChart('populationPieChart'),
    		option: populationPieOption,
    		organId: null,
    		times: null,
    		crowds: null,
    		provinceName: null,
    		init : function(organId){
    			var self = this;
    			self.organId = organId;
    			clearEChart(self.chart);
    			self.getRequestDatasFun();
    		},
    		getRequestDatasFun: function(){
    			var self = this;
    			var param = {
    					organId : self.organId,
    					provinceName : self.provinceName,
    					times : self.times,
    					crowds : self.crowds
    			};
    			$.ajax({
    				url : urls.queryPopulationPieUrl,
    				data : param,
    				type : 'post',
    				success : function(data){
    					self.getOptionFun(data);
    				},
    				error : function(){}
    			});
    		},
    		getOptionFun: function(data){
    			var self = this;
    			if(data && data != null && data != ''){
    				self.option.series[0].data = data;
    				self.setOptionFun();
    			} else {
    				showNoDataEcharts(self.chart);
    			}
    		},
    		setOptionFun: function(){
    			var self = this;
    			self.chart.setOption(self.option);
    		}
    }
    
    /**
     * 人才损益环比柱状图
     */
    var profitLossRingOption = {
		grid : {
			x : 35,
			y : 25,
			x2 : 15,
			borderWidth : 0
		},
		xAxis : [ {
			type : 'category',
			axisTick : false,
			splitLine : false,
			boundaryGap : true,
			axisLabel : {
				interval : 0
			},
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			data : []
		} ],
		yAxis : [ {
			type : 'value',
			axisTick : false,
			splitLine : false,
			axisLabel : {
				interval : 0
			},
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			}
		} ],
		series : [ {
			type : 'bar',
			clickable: false,
			barCategoryGap: '50%',
            barMaxWidth: 50,
			data : [],
			itemStyle : {
				normal : {
					color : '#00bda9',
					label : {
						show : true
					}
				}
        	}
		} ]
    }
    
    /**
     * 人才损益环比
     */
    var profitLossRingObj = {
		chart: initEChart("talentProfitLossRingChart"),
		option: profitLossRingOption,
		organId: null,
		times: null,
		crowds: null,
    	init : function(organId){
    		var self = this;
    		self.organId = organId;
    		clearEChart(self.chart);
    		self.getRequestDatasFun();
    	},
    	getRequestDatasFun: function(){
    		var self = this;
    		var param = {
				organId : self.organId,
				times : self.times,
				crowds : self.crowds
			};
    		$.ajax({
    			url : urls.queryTalentProfitLossRingDataUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				self.getOptionFun(data);
    			},
    			error : function(){}
    		});
    	},
    	getOptionFun: function(data){
    		var self = this;
    		if(data && data.date != undefined && data.date.length > 0){
//    			self.option.xAxis[0].axisLabel.margin = data.maxNum;
    			self.option.xAxis[0].data = data.date;
    			self.option.series[0].data = data.conNum;
    			changeXAxisLabelRotate(self.option, data.date);
    			self.setOptionFun();
    		} else {
    			showNoDataEcharts(self.chart);
    		}
    	},
    	setOptionFun: function(){
    		var self = this;
    		self.chart.setOption(self.option);
    	}
    }
    
    /**
     * 人才损益同比柱状图
     */
    var profitLossSameOption = {
		grid : {
			x : 35,
			y : 25,
			x2 : 15,
			borderWidth : 0
		},
		xAxis : [ {
			type : 'category',
			axisTick : false,
			splitLine : false,
			axisLabel : {
				interval : 0
//				margin : -100
			},
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			data : [ ]
		} ],
		yAxis : [ {
			type : 'value',
			axisTick : false,
			splitLine : false,
			axisLabel : {
				interval : 0
			},
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			}
		} ],
		series : [
				{
					type : 'bar',
					clickable: false,
					barCategoryGap: '40%',
		            barMaxWidth: 50,
					data : [ ],
					itemStyle : {
						normal : {
							color : '#de6e1b',
							label : {
								show : true
							}
						}
		        	}
				},
				{
					type : 'bar',
					clickable: false,
					barCategoryGap: '40%',
		            barMaxWidth: 50,
					data : [ ],
					itemStyle : {
						normal : {
							color : '#00bda9',
							label : {
								show : true
							}
						}
		        	}
				} ]
	};
    
    /**
     * 人才损益同比
     */
    var profitLossSameObj = {
    		chart: initEChart("talentProfitLossSameChart"),
    		option: profitLossSameOption,
    		organId: null,
    		times: null,
    		crowds: null,
    		legendClass: '.chart-legend',
    		oldLegendId: '#oldLegend',
    		curLegendId: '#curLegend',
    		init : function(organId){
    			var self = this;
    			self.organId = organId;
    			clearEChart(self.chart);
    			$(self.legendClass).hide();
    			self.getRequestDatasFun();
    		},
    		getRequestDatasFun: function(){
    			var self = this;
    			var param = {
					organId : self.organId,
					times : self.times,
					crowds : self.crowds
				};
    			$.ajax({
    				url : urls.queryTalentProfitLossSameDataUrl,
    				data : param,
    				type : 'post',
    				success : function(data){
    					self.getOptionFun(data);
    				},
    				error : function(){}
    			});
    		},
    		getOptionFun: function(data){
    			var self = this;
    			if(data && data.curDate != undefined && data.curDate.length > 0){
//    				self.option.xAxis[0].axisLabel.margin = data.maxNum - 0.5;
        			self.option.xAxis[0].data = data.curDate;
        			self.option.series[0].data = data.oldConNum;
        			self.option.series[1].data = data.curConNum;
        			changeXAxisLabelRotate(self.option, data.curDate);
        			var oldDate = data.oldDate;
        			var curDate = data.curDate;
        			var oldMinYear = oldDate[0].substr(0,4);
        			var oldMaxYear = oldDate[oldDate.length - 1].substr(0, 4);
        			var curMinYear = curDate[0].substr(0,4);
        			var curMaxYear = curDate[curDate.length - 1].substr(0, 4);
        			var oldLegend, curLegend;
        			if(oldMinYear == oldMaxYear){
        				oldLegend = oldMaxYear;
        			}else {
        				oldLegend = oldMinYear + '-' + oldMaxYear;
        			}
        			if(curMinYear == curMaxYear){
        				curLegend = curMaxYear;
        			}else {
        				curLegend = curMinYear + '-' + curMaxYear;
        			}
        			$(self.oldLegendId).html(oldLegend);
        			$(self.curLegendId).html(curLegend);
        			$(self.legendClass).show();
        			self.setOptionFun();
    			} else {
    				showNoDataEcharts(self.chart);
    			}
    		},
    		setOptionFun: function(){
    			var self = this;
    			self.chart.setOption(self.option);
    		}
    }
    

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    /**
     * 清除echart面板
     * @param eChartObj
     */
    function clearEChart(eChartObj){
    	if(eChartObj){
    		eChartObj.clear();
    	}
    }
    
    /**
     * 设置x轴标签显示方向
     * @param option echart对象
     * @param xAxisLabel x轴标签
     */
    function changeXAxisLabelRotate(option, xAxisLabel){
    	if(xAxisLabel.length > 6){
			option.xAxis[0].axisLabel.rotate = 30;
		}else{
			option.xAxis[0].axisLabel.rotate = 0;
		}
    }
    

    /**
     * 无数据据时显示 暂无数据
     * @param echartObj echart对象
     */
    var showNoDataEcharts = function (echartObj) {
        var zr = echartObj.getZrender();
        zr.addShape(new TextShape({
            style: {
                x: (echartObj.dom.clientWidth - 56) / 2,
                y: (echartObj.dom.clientHeight - 20) / 2,
                color: '#ccc',
                text: '暂无数据',
                textFont: '14px 宋体'
            }
        }));
        zr.render();
    };
    
    /***
     * 修改grid分页图标
     */
    Tc.updatePagerIcons = function() {
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
    
    var winTotalObject = {
    	init : function(){
    		toolbarStyleObj.init();
    		inflowCountObj.init(reqOrgId);
    		outflowCountObj.init(reqOrgId);
    		inflowOutflowObj.init(reqOrgId);
    		timecrowdObj.init(reqOrgId);
    		populationMapObj.init(reqOrgId);
    		populationPieObj.init(reqOrgId);
    		profitLossRingObj.init(reqOrgId);
    		profitLossSameObj.init(reqOrgId);
    		Tc.myOrgId = reqOrgId;
    		Tc.pieGridObj();
    	}
    }
    winTotalObject.init();
    
    $(window).resize(function () {
    	populationMapObj.chart.resize();
    	populationPieObj.chart.resize();
    	profitLossRingObj.chart.resize();
    	profitLossSameObj.chart.resize();
    });

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
//        $(".rightSetUpLeft").click();
        reqOrgId = organId;
        Tc.myOrgId = reqOrgId;
        timeLineObj.init(reqOrgId);
        winTotalObject.init();
    };
    
    /**
     * 管理建议与备忘
     * @type {{init: timeLineObj.init, getOption: timeLineObj.getOption}}
     */
    var timeLineObj = {
        init: function (organId) {
            var self = this;
            self.organizationId = organId;
            $('#timeLine').timeLine(self.getOption());	//初始化
        },
        getOption: function () {
            var organizationId = this.organizationId;
            var quotaId = $('#quotaId').val();
            //参数配置
            var options = {
                title: '管理建议与备忘',
                titleSuffix: '条未读',
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
        }
    }
    timeLineObj.init(reqOrgId);

    /*
     显示更多备忘
     */
    $("#memo-body-div").mouseenter(function () {
        $("#memo-big-list-more").show();
    });
    $("#memo-body-div").mouseleave(function () {
        $("#memo-big-list-more").hide();
    });

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart") {
            $(this).parents(".SetUpBody").find(".table-view").show();
            $(this).parents(".SetUpBody").find(".chart-view").hide();
            $(this).parents(".SetUpBody").attr("view", "table");

            $(".mCSB_container").css({"left": "0px"});
            $(this).parents(".SetUpBody").find(".ct-mCustomScrollBar").mCustomScrollbar("scrollTo", "left");
        } else {

            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
        }
    });

});