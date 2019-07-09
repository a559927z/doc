require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/map', 'echarts/chart/heatmap', 'echarts/chart/pie', 'echarts/chart/scatter',
    'bootstrap', 'tooltip', 'jgGrid', 'underscore', 'selection', 'form', 'messenger', 'selectItem', 'talent-map2',  
    'select2', 'organTreeSelector', 'cardTabel', 'salesGrid', 'jBootstrapPage'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var ecConfig = require('echarts/config');
    var urls = {
		queryTimesUrl: webRoot + '/salesBoard/queryTimes.do',	//获取时间
		/*page-one*/
		queryWarningInfoUrl: webRoot + '/salesBoard/queryWarningInfo.do',	//修改预警设置信息
		updateWarningInfoUrl: webRoot + '/salesBoard/updateWarningInfo.do',	//修改预警设置信息
        getCurMonthSalesValUrl: webRoot + '/salesBoard/getCurMonthSalesVal.do',	//本月销售额及比较
        getCurMonthSalesExctOrganUrl: webRoot + '/salesBoard/getCurMonthSalesExctOrgan.do',	//销售异常组织
        getCurMonthPersonFlowUrl: webRoot + '/salesBoard/getCurMonthPersonFlow.do',	//人员流失异常
        getCurMonthAbilityCheckUrl: webRoot + '/salesBoard/getCurMonthAbilityCheck.do',	//业务能力考核
        queryTodaySalesInfoUrl: webRoot + '/salesBoard/queryTodaySalesInfo.do',	//今日销售量/今日销售额/今日利润
        querySalesMapByProvinceUrl: webRoot + '/salesBoard/querySalesMapByProvince.do',	//销售总览-地图省份显示数据
        querySalesMapByCityUrl: webRoot + '/salesBoard/querySalesMapByCity.do',	//销售总览-地图地市显示数据
        queryOrganSalesStatisticsUrl: webRoot + '/salesBoard/queryOrganSalesStatistics.do',	//团队排行榜
        querySalesRankingUrl: webRoot + '/salesBoard/querySalesRanking.do',	//销售排名榜
        /*page-two*/
        getSalesAndTargetUrl: webRoot + '/salesBoard/getSalesAndTarget.do',	//维度分析-查询年销售额
        getTeamSalesAndTargetUrl: webRoot + '/salesBoard/getTeamSalesAndTarget.do',	//维度分析-查询团队销售额和达标率
        getPersonSalesAndTargetUrl: webRoot + '/salesBoard/getPersonSalesAndTarget.do',	//维度分析-查询个人销售额和达标率
        querySubOrganizationUrl: webRoot + '/salesBoard/querySubOrganization.do',	//维度分析-查询下级组织机构
        getSalesProductsUrl: webRoot + '/salesBoard/getSalesProducts.do',	//维度分析-销售趋势/销售产品
        queryTeamTimeUrl: webRoot + '/salesBoard/queryTeamTime.do',	//查询下级组织机构
        queryAbilityByEmpidUrl: webRoot + '/salesBoard/queryAbilityByEmpid.do',	//查询能力趋势
        queryRankByEmpidUrl: webRoot + '/salesBoard/queryRankByEmpid.do',	//查询排名趋势
        /*page-three*/
        getMapsBaseInfoUrl: webRoot + '/salesBoard/getMapsBaseInfo.do',     //销售排名地图-获取地图轴线数据
        querySalesRankMapTeamPKUrl: webRoot + '/salesBoard/querySalesRankMapTeamPK.do',	//销售排名地图-团队PK下级组织
        queryTeamViewChartUrl: webRoot + '/salesBoard/queryTeamViewChart.do',	//销售排名地图-团队总览chart
        queryTeamViewFullChartUrl: webRoot + '/salesBoard/queryTeamViewFullChart.do',	//销售排名地图-团队总览全屏chart
        queryTeamPKChartUrl: webRoot + '/salesBoard/queryTeamPKChart.do',	//销售排名地图-团队PKchart
        getTeamEmpPointUrl: webRoot + '/salesBoard/getTeamEmpPoint.do',     //销售排名地图-团队PK获取地图员工点信息
        queryMapsGridUrl: webRoot + '/salesBoard/queryMapsGrid.do',     //销售排名地图-列表
        searchBoxUrl: webRoot + '/common/getTalentMapsSearchBox.do',	//筛选条件信息
        getTeamSettingEmpCountUrl: webRoot + '/salesBoard/getTeamSettingEmpCount.do',	//筛选条件信息-统计员工数量
        /*page-four*/
        queryChangeByMonthUrl: webRoot + '/salesBoard/queryChangeByMonth.do',	//人员变动-销售额
        getChangeInfoUrl: webRoot + '/salesBoard/getChangeInfo.do',	//人员变动-异动情况
        querySalesByOrganUrl: webRoot + '/salesBoard/querySalesByOrgan.do',	//人员销售影响-查询组织销售额
        queryEmpChangeInfoUrl: webRoot + '/salesBoard/queryEmpChangeInfo.do',	//人员销售影响-查询销售人员异动情况
        getSalesByEmpidUrl: webRoot + '/salesBoard/getSalesByEmpid.do',	// 人员销售影响-人员异动操作
    };
    var defaultColor = [ '#0099cc','#5cb7f1', '#01d286', '#e5689b', '#b285c3', '#4682bc', '#8f684b', '#f28e7f', '#fbc370'];
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var reqOrgTxt = win.currOrganTxt;
    var month = $('#month').val();
    var red = $('#red').val();
    var yellow = $('#yellow').val();
    var red_ywnlkh = $('#red_ywnlkh').val();
    var yellow_ywnlkh = $('#yellow_ywnlkh').val();
    var num = $('#num').val();
    var names = $('#names').val();
    var yellowRanges = $('#yellowRanges').val();
    var redRanges = $('#redRanges').val();
    var notes = $('#notes').val();
    var defaultDatas = {
    	// 时间控件默认值
		minDate : null,
		maxDate : null,
		selectDate : null,
		selectYear : null,
		viewSelectDate : null,
		pkSelectDate : null,
		teamViewDate : null,
		teamPKDate : null,
		salesDetailDate : null,
		personDetailDate : null,
    	salesCountDate : null,
    	salesRankingDate : null,
    	salesProduct : '',
    	salesSelectType : 0,
    	organArr : new Array(),
    	organNum : 0,
    	//销售预警设置默认值
    	red_sales_val : 0,
    	yellow_sales_val : '0,0',
    	red_sales_organ : 0,
    	yellow_sales_organ : '0,0',
    	red_person_flow : 0,
    	yellow_person_flow : '0,0',
    	red_ability_check : 0,
    	yellow_ability_check : '0,0',
    	//销售排名地图默认值
    	pkChartFlag : false,
    	pkCount : 0,
    	rows: 30,
    	pkFlag : 0,
    	normalNum : 0,
    	highNum: 0,
    	lowNum: 0,
    	//团队排行榜默认organId
    	teamRankingOrganId : null,
    	viewFullCount: 0, // 团队总览全屏标识
    	pkFullCount: 0 // 团队PK全屏标识
    }
    // 获取机构数据
	win.organizationObject = function(organId, organTxt) {
		reqOrgId = organId;
		reqOrgTxt = organTxt;
		defaultDatas.teamRankingOrganId = reqOrgId;
		var curTopTabId = getActiveTabId(".leftListDiv");
		//初始化销量分析-人员/销量影响
		$.each(personSalesEffectGridObj.empArr, function(ind, obj){
			$('#img' + obj).parent().remove();
			personSalesObj.removeComparison(obj);
		});
		personSalesEffectGridObj.num = 0;
		personSalesEffectGridObj.empArr = [];
		personSalesObj.empIds = [];
		changeData(curTopTabId, true);
	};
	//
	var dataZoom = {
        show: true,
        realtime: true,
        height: 20,
        start: 0,
        end: 30,
        showDetail: false
    };
	/**
     * 返回当前焦点tab页显示的区域id
     */
    function getActiveTabId(targetDom) {
        var _currObj = _.find($(targetDom), function (obj) {
            return $(obj).hasClass('selectList');
        });
        return _currObj != null ? $(_currObj).attr('page') : 'page-one';
    }
    
    /**销售额/销售异常组织/人员流失异常/业务能力考核*/
    var topViewsObj = {
		salesValNum : '#salesValNum',
		salesValCompare : '#salesValCompare',
		exctOrganDiv : '#exctOrganDiv',
		exctOrganName : '#exctOrganName',
		exctUnit : '#exctUnit',
		exctOneCompare : '#exctOneCompare',
		exctMoreCompare1 : '#exctMoreCompare1',
		exctMoreCompare2 : '#exctMoreCompare2',
		oneCompare : '#oneCompare',
		moreCompare : '#moreCompare',
		personFlowNum : '#personFlowNum',
		personFlowInfo : '#personFlowInfo',
		personFlowInfo2 : '#personFlowInfo2',
		abilityCheckNum : '#abilityCheckNum',
		abilityCheckInfo : '#abilityCheckInfo',
		abilityCheckInfo2 : '#abilityCheckInfo2',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		self.getSalesValRequestDatas();
    		self.getExctOrganRequestDatas();
    		self.getPersonFlowRequestDatas();
    		self.getAbilityCheckRequestDatas();
    	},
    	resetInit: function(){
    		var self = this;
    		self.reloadSalesValDatas();
    		self.reloadPersonFlowDatas();
    		self.reloadAbilityCheckDatas();
    		self.reloadExceOrganDatas();
    	},
    	reloadSalesValDatas: function(){
    		var self = this;
    		var parentDiv = $(self.salesValCompare).parent('.accord-bottom-float-text');
    		if(!parentDiv.hasClass('hide')){
    			var val = Number($(self.salesValCompare).html());
    			self.setNormalClass(self.salesValNum);
    			var text = $(self.salesValCompare).siblings(".upDown").text();
    			if(text == '下降'){
    				if(val > defaultDatas.red_sales_val){
    					self.setRedClass(self.salesValNum);
    				} else if(val >= defaultDatas.yellow_sales_val.split(',')[0]){
    					self.setYellowClass(self.salesValNum);
    				}
    			}
    		}
    	},
    	reloadExceOrganDatas: function(){
    		var self = this;
    		self.getExctOrganRequestDatas();
    	},
    	reloadPersonFlowDatas: function(){
    		var self = this;
    		var val = $(self.personFlowNum).html();
    		self.setNormalClass(self.personFlowNum);
    		$(self.personFlowInfo).hide();
    		$(self.personFlowInfo2).hide();
    		if(Number(val) > defaultDatas.red_person_flow){
    			self.setRedClass(self.personFlowNum);
    			$(self.personFlowInfo2).show();
    		} else if(Number(val) >= defaultDatas.yellow_person_flow.split(',')[0]){
    			self.setYellowClass(self.personFlowNum);
    			$(self.personFlowInfo).show();
    		}
    	},
    	reloadAbilityCheckDatas: function(){
    		var self = this;
    		var val = $(self.abilityCheckNum).html();
    		self.setNormalClass(self.abilityCheckNum);
    		$(self.abilityCheckInfo).hide();
    		$(self.abilityCheckInfo2).hide();
    		if(Number(val) < defaultDatas.red_ability_check){
    			self.setRedClass(self.abilityCheckNum);
    			$(self.abilityCheckInfo2).show();
    		} else if(Number(val) <= defaultDatas.yellow_ability_check.split(',')[1]){
    			self.setYellowClass(self.abilityCheckNum);
    			$(self.abilityCheckInfo).show();
    		}
    	},
    	setExctOrganColor: function(val){
    		var self = this;
    		self.setNormalClass(self.exctOrganName);
    		if(val > defaultDatas.red_sales_organ){
    			self.setRedClass(self.exctOrganName);
    		} else if(val >= defaultDatas.yellow_sales_organ.split(',')[0]){
    			self.setYellowClass(self.exctOrganName);
    		}
    	},
    	setRedClass: function(id){
    		$(id).addClass('font-red').removeClass('font-yellow');
    	},
    	setYellowClass: function(id){
    		$(id).addClass('font-yellow').removeClass('font-red');
    	},
    	setNormalClass: function(id){
    		$(id).removeClass('font-yellow').removeClass('font-red');
    	},
    	getSalesValRequestDatas: function(){
    		var self = this;
    		var param = {
    			organId : self.organId
    		}
    		$.post(urls.getCurMonthSalesValUrl, param, function(data){
    			self.loadSalesValDatas(data);
    		});
    	},
    	loadSalesValDatas: function(data){
    		var self = this;
    		$(self.salesValNum).html(Tc.formatDigital(Tc.formatFloat(data.sumNum)));
    		var parentDiv = $(self.salesValCompare).parent('.accord-bottom-float-text');
    		if(_.isNull(data.percentNum)){
    			parentDiv.addClass('hide');
    		} else {
    			parentDiv.removeClass('hide');
    		}
    		$(self.salesValCompare).html(Math.abs(Tc.formatFloat(data.percentNum)));
    		self.changeUpDown(self.salesValCompare, Tc.formatFloat(data.percentNum));
//    		self.changeImgByVal(self.salesValCompare, Tc.formatFloat(data.percentNum));
    		self.reloadSalesValDatas();
    	},
    	getExctOrganRequestDatas: function(){
    		var self = this;
    		$(self.exctUnit).addClass('this-hide');
    		$(self.oneCompare).addClass('this-hide');
    		$(self.moreCompare).addClass('this-hide');
    		$(self.exctOrganName).removeClass('cursorDefault');
    		$(self.exctOrganName).removeAttr('title').removeAttr('data-original-title')
    			.removeAttr('data-toggle').removeAttr('data-placement');
//    		$(self.exctOrganName).removeAttr('data-original-title');
    		var param = {
    			organId : self.organId,
    			yellowRange : defaultDatas.yellow_sales_organ,
    			redRange : defaultDatas.red_sales_organ
    		}
    		$.post(urls.getCurMonthSalesExctOrganUrl + '?r=' + Math.random(), param, function(data){
    			self.loadExctOrganDatas(data);
    		});
    	},
    	loadExctOrganDatas: function(data){
    		var self = this;
    		$(self.exctOrganName).html("无");
    		self.setNormalClass(self.exctOrganName);
    		if(data.organName){
    			var nameArr = data.organName.split(',');
    			var pNumArr = data.percentNum.split(',');
    			if(data.conNum > 1){
    				$(self.exctOrganName).addClass('cursorDefault');
    				$(self.exctUnit).removeClass('this-hide');
    				$(self.moreCompare).removeClass('this-hide');
    				$(self.exctOrganName).html(nameArr.length);
    				$(self.exctMoreCompare1).html(Math.abs(Number(pNumArr[0])));
    				$(self.exctMoreCompare2).html(Math.abs(Number(pNumArr[1])));
    				self.setExctOrganColor(Math.abs(Number(pNumArr[0])));
    				self.showExctOrgan(data);
    			} else if(data.conNum == 1){
    				$(self.oneCompare).removeClass('this-hide');
    				$(self.exctOrganName).html(data.organName);
    				$(self.exctOneCompare).html(Math.abs(Number(pNumArr[0])));
    				self.setExctOrganColor(Math.abs(Number(pNumArr[0])));
    			}
    		}
    	},
    	showExctOrgan: function(data){
    		var self = this;
    		var title = '';
    		$.each(data.list, function(ind, obj){
    			title += '<div class="tooltip-content width250">'
    				+ '<div class="tooltip-content-child col-md-4 padding0">' + obj.organName + '</div>'
    				+ '<div class="tooltip-content-child2 col-md-8 padding0"> : 较上月下降' + Math.abs(Tc.formatFloat(obj.percentNum * 100)) + '%</div></div>';
    		});
    		$(self.exctOrganName).attr('data-toggle', 'tooltip').attr('data-placement', 'right')
    			.attr('data-original-title', title).attr('data-html', true);
    		$(self.exctOrganName).tooltip();
    	},
    	getPersonFlowRequestDatas: function(){
    		var self = this;
    		$(self.personFlowInfo).hide();
    		var param = {
    			organId : self.organId
    		}
    		$.post(urls.getCurMonthPersonFlowUrl, param, function(data){
    			self.loadPersonFlowDatas(data);
    		});
    	},
    	loadPersonFlowDatas: function(data){
    		var self = this;
    		$(self.personFlowNum).html(Tc.formatFloat(data.sumNum));
    		if(data.sumNum == 0){
    			$(self.personFlowInfo).show();
    		}
    		self.reloadPersonFlowDatas();
    	},
    	getAbilityCheckRequestDatas: function(){
    		var self = this;
    		$(self.abilityCheck).hide();
    		var param = {
    			organId : self.organId
    		}
    		$.post(urls.getCurMonthAbilityCheckUrl, param, function(data){
    			self.loadAbilityCheckPersonFlowDatas(data);
    		});
    	},
    	loadAbilityCheckPersonFlowDatas: function(data){
    		var self = this;
    		$(self.abilityCheckNum).html(Tc.formatFloat(data.percentNum));
    		$(self.abilityCheck).show();
    		self.reloadAbilityCheckDatas();
    	},
    	changeUpDown: function(id, val){
    		var self = this;
    		var $upDown = $(id).siblings(".upDown");
    		$upDown.html('下降');
    		if(val > 0){
    			$upDown.html('上升');
    		}
    	},
    	changeImgByVal: function(id, val){
    		var self = this;
    		var $id = $(id).siblings(".accord-bottom-float-arrow");
    		var upClassName = "accord-bottom-float-value-rise";
    		var downClassName = "accord-bottom-float-value-drop";
    		$id.removeClass(upClassName).removeClass(downClassName);
    		if(val >= 0){
    			$id.addClass(upClassName);
    		} else {
    			$id.addClass(downClassName);
    		}
    	}
    }
    
    /**
     * 销售预警-预警设置
     * */
    var salesWarningObj = {
		salesWarningImg : '.salesWarningImg',
		modalId : '#salesWarningEditModal',
		okId : '#warningOkId',
		removeId : '#warningRemoveId',
		salesNumTitle : '#salesNumTitle',
		salesNumYel1 : '#salesNumYel1',
		salesNumYel2 : '#salesNumYel2',
		salesNumRed : '#salesNumRed',
		salesOrganTitle : '#salesOrganTitle',
		salesOrganYel1 : '#salesOrganYel1',
		salesOrganYel2 : '#salesOrganYel2',
		salesOrganRed : '#salesOrganRed',
		personFlowTitle : '#personFlowTitle',
		personFlowYel1 : '#personFlowYel1',
		personFlowYel2 : '#personFlowYel2',
		personFlowRed : '#personFlowRed',
		abilityCheckTitle : '#abilityCheckTitle',
		abilityCheckYel1 : '#abilityCheckYel1',
		abilityCheckYel2 : '#abilityCheckYel2',
		abilityCheckRed : '#abilityCheckRed',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		self.imgClickFun();
    	},
    	imgClickFun: function(){
    		var self = this;
    		$(self.salesWarningImg).unbind('click').bind('click', function(){
    			$(self.modalId).modal('show');
    			$(self.modalId).on('shown.bs.modal', function() {
    				self.setInputNumFun();
    				loadWarningInfo.requestDatas();
    				self.okClickFun();
    				self.removeClickFun();
				});
    		});
    	},
    	loadDatasFun: function(){
    		var self = this;
			$(self.salesNumYel1).val(defaultDatas.yellow_sales_val.split(',')[0]);
			$(self.salesNumYel2).val(defaultDatas.yellow_sales_val.split(',')[1]);
			$(self.salesNumRed).val(defaultDatas.red_sales_val);
			$(self.salesOrganYel1).val(defaultDatas.yellow_sales_organ.split(',')[0]);
			$(self.salesOrganYel2).val(defaultDatas.yellow_sales_organ.split(',')[1]);
			$(self.salesOrganRed).val(defaultDatas.red_sales_organ);
			$(self.personFlowYel1).val(defaultDatas.yellow_person_flow.split(',')[0]);
			$(self.personFlowYel2).val(defaultDatas.yellow_person_flow.split(',')[1]);
			$(self.personFlowRed).val(defaultDatas.red_person_flow);
			$(self.abilityCheckYel1).val(defaultDatas.yellow_ability_check.split(',')[0]);
			$(self.abilityCheckYel2).val(defaultDatas.yellow_ability_check.split(',')[1]);
			$(self.abilityCheckRed).val(defaultDatas.red_ability_check);
    	},
    	okClickFun: function(){
    		var self = this;
    		$(self.okId).unbind('click').bind('click', function(){
    			$(self.modalId).modal('hide');
    			var salesNumTitle = $(self.salesNumTitle).html();
    			var salesOrganTitle = $(self.salesOrganTitle).html();
    			var personFlowTitle = $(self.personFlowTitle).html();
    			var abilityCheckTitle = $(self.abilityCheckTitle).html();
    			var names = salesNumTitle + '@' + salesOrganTitle + '@' + personFlowTitle + '@' + abilityCheckTitle;
    			//该区域名称对应上面中文名称，固定写死唯一标识
    			var regions = 'salesNum@salesOrgan@personFlow@abilityCheck';
    			var salesNumYel = $(self.salesNumYel1).val() + ',' + $(self.salesNumYel2).val();
    			var salesNumRed = $(self.salesNumRed).val();
    			var salesOrganYel = $(self.salesOrganYel1).val() + ',' + $(self.salesOrganYel2).val();
    			var salesOrganRed = $(self.salesOrganRed).val();
    			var personFlowYel = $(self.personFlowYel1).val() + ',' + $(self.personFlowYel2).val();
    			var personFlowRed = $(self.personFlowRed).val();
    			var abilityCheckYel = $(self.abilityCheckYel1).val() + ',' + $(self.abilityCheckYel2).val();
    			var abilityCheckRed = $(self.abilityCheckRed).val();
    			var yellows = salesNumYel + '@' + salesOrganYel + '@' + personFlowYel + '@' + abilityCheckYel;
    			var reds = salesNumRed + '@' + salesOrganRed + '@' + personFlowRed + '@' + abilityCheckRed;
    			//设置js默认值
    			defaultDatas.yellow_sales_val = salesNumYel;
    			defaultDatas.red_sales_val = salesNumRed;
    			defaultDatas.yellow_sales_organ = salesOrganYel;
    			defaultDatas.red_sales_organ = salesOrganRed;
    			defaultDatas.yellow_person_flow = personFlowYel;
    			defaultDatas.red_person_flow = personFlowRed;
    			defaultDatas.yellow_ability_check = abilityCheckYel;
    			defaultDatas.red_ability_check = abilityCheckRed;
    			var param = {
    				names : names,
    				yellows : yellows,
    				reds : reds
    			}
    			$.ajax({
    				url : urls.updateWarningInfoUrl,
    				data : param,
    				type : 'post',
    				success : function(data){
    					topViewsObj.resetInit();
    				},
    				error : function(){}
    			});
    		});
    	},
    	removeClickFun: function(){
    		var self = this;
    		$(self.removeId).unbind('click').bind('click', function(){
    			$(self.modalId).modal('hide');
    		});
    	},
    	setInputNumFun: function(){
    		var self = this;
    		self.setSalesValRedFun();
    		self.setExcOrganRedFun();
    		self.setPersonFlowRedFun();
    		self.setAbilityCheckRedFun();
    	},
    	setSalesValRedFun: function(){
    		var self = this;
    		$(self.salesNumYel2).unbind('keyup').bind('keyup', function(){
    			var val = $(this).val();
    			var val1 = $(self.salesNumYel2).val();
    			if(val == null || val == ''){
    				val = (val1 == '' || val1 == null) ? 0 : val1;
    			}
    			$(self.salesNumRed).val(val);
    		});
    	},
    	setExcOrganRedFun: function(){
    		var self = this;
    		$(self.salesOrganYel2).unbind('keyup').bind('keyup', function(){
    			var val = $(this).val();
    			var val1 = $(self.salesOrganYel1).val();
    			if(val == null || val == ''){
    				val = (val1 == '' || val1 == null) ? 0 : val1;
    			}
    			$(self.salesOrganRed).val(val);
    		});
    	},
    	setPersonFlowRedFun: function(){
    		var self = this;
    		$(self.personFlowYel2).unbind('keyup').bind('keyup', function(){
    			var val = $(this).val();
    			var val1 = $(self.personFlowYel1).val();
    			if(val == null || val == ''){
    				val = (val1 == '' || val1 == null) ? 0 : val1;
    			}
    			$(self.personFlowRed).val(val);
    		});
    	},
    	setAbilityCheckRedFun: function(){
    		var self = this;
    		$(self.abilityCheckYel1).unbind('keyup').bind('keyup', function(){
    			var val = $(this).val();
    				val = (val == '' || val == null) ? 0 : val;
    			$(self.abilityCheckRed).val(val);
    		});
    	}
    }
    
    /**
     * 今日销售量，今日销售额，今日利润
     * */
    var todaySalesInfoObj = {
		todaySalesNumId : '#todaySalesNumId',
		todaySalesValId : '#todaySalesValId',
		todaySalesGainId : '#todaySalesGainId',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		self.getRequestDatas();
    	},
    	getRequestDatas: function(){
    		var self = this;
    		var param = {
    			organId : self.organId
    		}
    		$.ajax({
    			url : urls.queryTodaySalesInfoUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				self.loadDatas(data);
    			},
    			error : function(){}
    		});
    	},
    	loadDatas: function(data){
    		var self = this;
    		$(self.todaySalesNumId).html(data.numberNum);
    		$(self.todaySalesValId).html(Tc.formatFloat(data.moneyNum));
    		$(self.todaySalesGainId).html(Tc.formatFloat(data.profitNum));
    	}
    }
    
    var seriesData = [ ];
    var seriesDataCity = [
		{name: '重庆市',value: 0},
		{name: '北京市',value: 0},
		{name: '天津市',value: 0},
		{name: '上海市',value: 0},
		{name: '香港',value: 0},
		{name: '澳门',value: 0},
		{name: '巴音郭楞蒙古自治州',value: 0},
		{name: '和田地区',value: 0},
		{name: '哈密地区',value: 0},
		{name: '阿克苏地区',value: 0},
		{name: '阿勒泰地区',value: 0},
		{name: '喀什地区',value: 0},
		{name: '塔城地区',value: 0},
		{name: '昌吉回族自治州',value: 0},
		{name: '克孜勒苏柯尔克孜自治州',value: 0},
		{name: '吐鲁番地区',value: 0},
		{name: '伊犁哈萨克自治州',value: 0},
		{name: '博尔塔拉蒙古自治州',value: 0},
		{name: '乌鲁木齐市',value: 0},
		{name: '克拉玛依市',value: 0},
		{name: '阿拉尔市',value: 0},
		{name: '图木舒克市',value: 0},
		{name: '五家渠市',value: 0},
		{name: '石河子市',value: 0},
		{name: '那曲地区',value: 0},
		{name: '阿里地区',value: 0},
		{name: '日喀则地区',value: 0},
		{name: '林芝地区',value: 0},
		{name: '昌都地区',value: 0},
		{name: '山南地区',value: 0},
		{name: '拉萨市',value: 0},
		{name: '呼伦贝尔市',value: 0},
		{name: '阿拉善盟',value: 0},
		{name: '锡林郭勒盟',value: 0},
		{name: '鄂尔多斯市',value: 0},
		{name: '赤峰市',value: 0},
		{name: '巴彦淖尔市',value: 0},
		{name: '通辽市',value: 0},
		{name: '乌兰察布市',value: 0},
		{name: '兴安盟',value: 0},
		{name: '包头市',value: 0},
		{name: '呼和浩特市',value: 0},
		{name: '乌海市',value: 0},
		{name: '海西蒙古族藏族自治州',value: 0},
		{name: '玉树藏族自治州',value: 0},
		{name: '果洛藏族自治州',value: 0},
		{name: '海南藏族自治州',value: 0},
		{name: '海北藏族自治州',value: 0},
		{name: '黄南藏族自治州',value: 0},
		{name: '海东地区',value: 0},
		{name: '西宁市',value: 0},
		{name: '甘孜藏族自治州',value: 0},
		{name: '阿坝藏族羌族自治州',value: 0},
		{name: '凉山彝族自治州',value: 0},
		{name: '绵阳市',value: 0},
		{name: '达州市',value: 0},
		{name: '广元市',value: 0},
		{name: '雅安市',value: 0},
		{name: '宜宾市',value: 0},
		{name: '乐山市',value: 0},
		{name: '南充市',value: 0},
		{name: '巴中市',value: 0},
		{name: '泸州市',value: 0},
		{name: '成都市',value: 0},
		{name: '资阳市',value: 0},
		{name: '攀枝花市',value: 0},
		{name: '眉山市',value: 0},
		{name: '广安市',value: 0},
		{name: '德阳市',value: 0},
		{name: '内江市',value: 0},
		{name: '遂宁市',value: 0},
		{name: '自贡市',value: 0},
		{name: '黑河市',value: 0},
		{name: '大兴安岭地区',value: 0},
		{name: '哈尔滨市',value: 0},
		{name: '齐齐哈尔市',value: 0},
		{name: '牡丹江市',value: 0},
		{name: '绥化市',value: 0},
		{name: '伊春市',value: 0},
		{name: '佳木斯市',value: 0},
		{name: '鸡西市',value: 0},
		{name: '双鸭山市',value: 0},
		{name: '大庆市',value: 0},
		{name: '鹤岗市',value: 0},
		{name: '七台河市',value: 0},
		{name: '酒泉市',value: 0},
		{name: '张掖市',value: 0},
		{name: '甘南藏族自治州',value: 0},
		{name: '武威市',value: 0},
		{name: '陇南市',value: 0},
		{name: '庆阳市',value: 0},
		{name: '白银市',value: 0},
		{name: '定西市',value: 0},
		{name: '天水市',value: 0},
		{name: '兰州市',value: 0},
		{name: '平凉市',value: 0},
		{name: '临夏回族自治州',value: 0},
		{name: '金昌市',value: 0},
		{name: '嘉峪关市',value: 0},
		{name: '普洱市',value: 0},
		{name: '红河哈尼族彝族自治州',value: 0},
		{name: '文山壮族苗族自治州',value: 0},
		{name: '曲靖市',value: 0},
		{name: '楚雄彝族自治州',value: 0},
		{name: '大理白族自治州',value: 0},
		{name: '临沧市',value: 0},
		{name: '迪庆藏族自治州',value: 0},
		{name: '昭通市',value: 0},
		{name: '昆明市',value: 0},
		{name: '丽江市',value: 0},
		{name: '西双版纳傣族自治州',value: 0},
		{name: '保山市',value: 0},
		{name: '玉溪市',value: 0},
		{name: '怒江傈僳族自治州',value: 0},
		{name: '德宏傣族景颇族自治州',value: 0},
		{name: '百色市',value: 0},
		{name: '河池市',value: 0},
		{name: '桂林市',value: 0},
		{name: '南宁市',value: 0},
		{name: '柳州市',value: 0},
		{name: '崇左市',value: 0},
		{name: '来宾市',value: 0},
		{name: '玉林市',value: 0},
		{name: '梧州市',value: 0},
		{name: '贺州市',value: 0},
		{name: '钦州市',value: 0},
		{name: '贵港市',value: 0},
		{name: '防城港市',value: 0},
		{name: '北海市',value: 0},
		{name: '怀化市',value: 0},
		{name: '永州市',value: 0},
		{name: '邵阳市',value: 0},
		{name: '郴州市',value: 0},
		{name: '常德市',value: 0},
		{name: '湘西土家族苗族自治州',value: 0},
		{name: '衡阳市',value: 0},
		{name: '岳阳市',value: 0},
		{name: '益阳市',value: 0},
		{name: '长沙市',value: 0},
		{name: '株洲市',value: 0},
		{name: '张家界市',value: 0},
		{name: '娄底市',value: 0},
		{name: '湘潭市',value: 0},
		{name: '榆林市',value: 0},
		{name: '延安市',value: 0},
		{name: '汉中市',value: 0},
		{name: '安康市',value: 0},
		{name: '商洛市',value: 0},
		{name: '宝鸡市',value: 0},
		{name: '渭南市',value: 0},
		{name: '咸阳市',value: 0},
		{name: '西安市',value: 0},
		{name: '铜川市',value: 0},
		{name: '清远市',value: 0},
		{name: '韶关市',value: 0},
		{name: '湛江市',value: 0},
		{name: '梅州市',value: 0},
		{name: '河源市',value: 0},
		{name: '肇庆市',value: 0},
		{name: '惠州市',value: 0},
		{name: '茂名市',value: 0},
		{name: '江门市',value: 0},
		{name: '阳江市',value: 0},
		{name: '云浮市',value: 0},
		{name: '广州市',value: 0},
		{name: '汕尾市',value: 0},
		{name: '揭阳市',value: 0},
		{name: '珠海市',value: 0},
		{name: '佛山市',value: 0},
		{name: '潮州市',value: 0},
		{name: '汕头市',value: 0},
		{name: '深圳市',value: 0},
		{name: '东莞市',value: 0},
		{name: '中山市',value: 0},
		{name: '延边朝鲜族自治州',value: 0},
		{name: '吉林市',value: 0},
		{name: '白城市',value: 0},
		{name: '松原市',value: 0},
		{name: '长春市',value: 0},
		{name: '白山市',value: 0},
		{name: '通化市',value: 0},
		{name: '四平市',value: 0},
		{name: '辽源市',value: 0},
		{name: '承德市',value: 0},
		{name: '张家口市',value: 0},
		{name: '保定市',value: 0},
		{name: '唐山市',value: 0},
		{name: '沧州市',value: 0},
		{name: '石家庄市',value: 0},
		{name: '邢台市',value: 0},
		{name: '邯郸市',value: 0},
		{name: '秦皇岛市',value: 0},
		{name: '衡水市',value: 0},
		{name: '廊坊市',value: 0},
		{name: '恩施土家族苗族自治州',value: 0},
		{name: '十堰市',value: 0},
		{name: '宜昌市',value: 0},
		{name: '襄樊市',value: 0},
		{name: '黄冈市',value: 0},
		{name: '荆州市',value: 0},
		{name: '荆门市',value: 0},
		{name: '咸宁市',value: 0},
		{name: '随州市',value: 0},
		{name: '孝感市',value: 0},
		{name: '武汉市',value: 0},
		{name: '黄石市',value: 0},
		{name: '神农架林区',value: 0},
		{name: '天门市',value: 0},
		{name: '仙桃市',value: 0},
		{name: '潜江市',value: 0},
		{name: '鄂州市',value: 0},
		{name: '遵义市',value: 0},
		{name: '黔东南苗族侗族自治州',value: 0},
		{name: '毕节地区',value: 0},
		{name: '黔南布依族苗族自治州',value: 0},
		{name: '铜仁地区',value: 0},
		{name: '黔西南布依族苗族自治州',value: 0},
		{name: '六盘水市',value: 0},
		{name: '安顺市',value: 0},
		{name: '贵阳市',value: 0},
		{name: '烟台市',value: 0},
		{name: '临沂市',value: 0},
		{name: '潍坊市',value: 0},
		{name: '青岛市',value: 0},
		{name: '菏泽市',value: 0},
		{name: '济宁市',value: 0},
		{name: '德州市',value: 0},
		{name: '滨州市',value: 0},
		{name: '聊城市',value: 0},
		{name: '东营市',value: 0},
		{name: '济南市',value: 0},
		{name: '泰安市',value: 0},
		{name: '威海市',value: 0},
		{name: '日照市',value: 0},
		{name: '淄博市',value: 0},
		{name: '枣庄市',value: 0},
		{name: '莱芜市',value: 0},
		{name: '赣州市',value: 0},
		{name: '吉安市',value: 0},
		{name: '上饶市',value: 0},
		{name: '九江市',value: 0},
		{name: '抚州市',value: 0},
		{name: '宜春市',value: 0},
		{name: '南昌市',value: 0},
		{name: '景德镇市',value: 0},
		{name: '萍乡市',value: 0},
		{name: '鹰潭市',value: 0},
		{name: '新余市',value: 0},
		{name: '南阳市',value: 0},
		{name: '信阳市',value: 0},
		{name: '洛阳市',value: 0},
		{name: '驻马店市',value: 0},
		{name: '周口市',value: 0},
		{name: '商丘市',value: 0},
		{name: '三门峡市',value: 0},
		{name: '新乡市',value: 0},
		{name: '平顶山市',value: 0},
		{name: '郑州市',value: 0},
		{name: '安阳市',value: 0},
		{name: '开封市',value: 0},
		{name: '焦作市',value: 0},
		{name: '许昌市',value: 0},
		{name: '濮阳市',value: 0},
		{name: '漯河市',value: 0},
		{name: '鹤壁市',value: 0},
		{name: '大连市',value: 0},
		{name: '朝阳市',value: 0},
		{name: '丹东市',value: 0},
		{name: '铁岭市',value: 0},
		{name: '沈阳市',value: 0},
		{name: '抚顺市',value: 0},
		{name: '葫芦岛市',value: 0},
		{name: '阜新市',value: 0},
		{name: '锦州市',value: 0},
		{name: '鞍山市',value: 0},
		{name: '本溪市',value: 0},
		{name: '营口市',value: 0},
		{name: '辽阳市',value: 0},
		{name: '盘锦市',value: 0},
		{name: '忻州市',value: 0},
		{name: '吕梁市',value: 0},
		{name: '临汾市',value: 0},
		{name: '晋中市',value: 0},
		{name: '运城市',value: 0},
		{name: '大同市',value: 0},
		{name: '长治市',value: 0},
		{name: '朔州市',value: 0},
		{name: '晋城市',value: 0},
		{name: '太原市',value: 0},
		{name: '阳泉市',value: 0},
		{name: '六安市',value: 0},
		{name: '安庆市',value: 0},
		{name: '滁州市',value: 0},
		{name: '宣城市',value: 0},
		{name: '阜阳市',value: 0},
		{name: '宿州市',value: 0},
		{name: '黄山市',value: 0},
		{name: '巢湖市',value: 0},
		{name: '亳州市',value: 0},
		{name: '池州市',value: 0},
		{name: '合肥市',value: 0},
		{name: '蚌埠市',value: 0},
		{name: '芜湖市',value: 0},
		{name: '淮北市',value: 0},
		{name: '淮南市',value: 0},
		{name: '马鞍山市',value: 0},
		{name: '铜陵市',value: 0},
		{name: '南平市',value: 0},
		{name: '三明市',value: 0},
		{name: '龙岩市',value: 0},
		{name: '宁德市',value: 0},
		{name: '福州市',value: 0},
		{name: '漳州市',value: 0},
		{name: '泉州市',value: 0},
		{name: '莆田市',value: 0},
		{name: '厦门市',value: 0},
		{name: '丽水市',value: 0},
		{name: '杭州市',value: 0},
		{name: '温州市',value: 0},
		{name: '宁波市',value: 0},
		{name: '舟山市',value: 0},
		{name: '台州市',value: 0},
		{name: '金华市',value: 0},
		{name: '衢州市',value: 0},
		{name: '绍兴市',value: 0},
		{name: '嘉兴市',value: 0},
		{name: '湖州市',value: 0},
		{name: '盐城市',value: 0},
		{name: '徐州市',value: 0},
		{name: '南通市',value: 0},
		{name: '淮安市',value: 0},
		{name: '苏州市',value: 0},
		{name: '宿迁市',value: 0},
		{name: '连云港市',value: 0},
		{name: '扬州市',value: 0},
		{name: '南京市',value: 0},
		{name: '泰州市',value: 0},
		{name: '无锡市',value: 0},
		{name: '常州市',value: 0},
		{name: '镇江市',value: 0},
		{name: '吴忠市',value: 0},
		{name: '中卫市',value: 0},
		{name: '固原市',value: 0},
		{name: '银川市',value: 0},
		{name: '石嘴山市',value: 0},
		{name: '儋州市',value: 0},
		{name: '文昌市',value: 0},
		{name: '乐东黎族自治县',value: 0},
		{name: '三亚市',value: 0},
		{name: '琼中黎族苗族自治县',value: 0},
		{name: '东方市',value: 0},
		{name: '海口市',value: 0},
		{name: '万宁市',value: 0},
		{name: '澄迈县',value: 0},
		{name: '白沙黎族自治县',value: 0},
		{name: '琼海市',value: 0},
		{name: '昌江黎族自治县',value: 0},
		{name: '临高县',value: 0},
		{name: '陵水黎族自治县',value: 0},
		{name: '屯昌县',value: 0},
		{name: '定安县',value: 0},
		{name: '保亭黎族苗族自治县',value: 0},
		{name: '五指山市',value: 0}
	];
    var pointDataProvince = [
    	{id: '222', name : '北京', value: 400},
    	{id: '222', name : '山东', value: 300},
    	{id: '222', name : '湖南', value: 700},
    	{id: '222', name : '西藏', value: 200}
    ]
    var pointDataCity = [
		{name: "海门", value: 9},
		{name: "鄂尔多斯", value: 12},
		{name: "招远", value: 12},
		{name: "舟山", value: 12},
		{name: "齐齐哈尔", value: 14},
		{name: "盐城", value: 15},
		{name: "赤峰", value: 16},
		{name: "青岛", value: 18},
		{name: "乳山", value: 18},
		{name: "金昌", value: 19},
		{name: "泉州", value: 21},
		{name: "莱西", value: 21},
		{name: "日照", value: 21},
		{name: "胶南", value: 22},
		{name: "南通", value: 23},
		{name: "拉萨", value: 24},
		{name: "云浮", value: 24},
		{name: "梅州", value: 25},
		{name: "文登", value: 25},
		{name: "上海", value: 25},
		{name: "攀枝花", value: 25},
		{name: "威海", value: 25},
		{name: "承德", value: 25},
		{name: "厦门", value: 26},
		{name: "汕尾", value: 26},
		{name: "潮州", value: 26},
		{name: "丹东", value: 27},
		{name: "太仓", value: 27},
		{name: "曲靖", value: 27},
		{name: "烟台", value: 28},
		{name: "福州", value: 29},
		{name: "瓦房店", value: 30},
		{name: "即墨", value: 30},
		{name: "抚顺", value: 31},
		{name: "玉溪", value: 31},
		{name: "张家口", value: 31},
		{name: "阳泉", value: 31},
		{name: "莱州", value: 32},
		{name: "湖州", value: 32},
		{name: "汕头", value: 32},
		{name: "昆山", value: 33},
		{name: "宁波", value: 33},
		{name: "湛江", value: 33},
		{name: "揭阳", value: 34},
		{name: "荣成", value: 34},
		{name: "连云港", value: 35},
		{name: "葫芦岛", value: 35},
		{name: "常熟", value: 36},
		{name: "东莞", value: 36},
		{name: "河源", value: 36},
		{name: "淮安", value: 36},
		{name: "泰州", value: 36},
		{name: "南宁", value: 37},
		{name: "营口", value: 37},
		{name: "惠州", value: 37},
		{name: "江阴", value: 37},
		{name: "蓬莱", value: 37},
		{name: "韶关", value: 38},
		{name: "嘉峪关", value: 38},
		{name: "广州", value: 38},
		{name: "延安", value: 38},
		{name: "太原", value: 39},
		{name: "清远", value: 39},
		{name: "中山", value: 39},
		{name: "昆明", value: 39},
		{name: "寿光", value: 40},
		{name: "盘锦", value: 40},
		{name: "长治", value: 41},
		{name: "深圳", value: 41},
		{name: "珠海", value: 42},
		{name: "宿迁", value: 43},
		{name: "咸阳", value: 43},
		{name: "铜川", value: 44},
		{name: "平度", value: 44},
		{name: "佛山", value: 44},
		{name: "海口", value: 44},
		{name: "江门", value: 45},
		{name: "章丘", value: 45},
		{name: "肇庆", value: 46},
		{name: "大连", value: 47},
		{name: "临汾", value: 47},
		{name: "吴江", value: 47},
		{name: "石嘴山", value: 49},
		{name: "沈阳", value: 50},
		{name: "苏州", value: 50},
		{name: "茂名", value: 50},
		{name: "嘉兴", value: 51},
		{name: "长春", value: 51},
		{name: "胶州", value: 52},
		{name: "银川", value: 52},
		{name: "张家港", value: 52},
		{name: "三门峡", value: 53},
		{name: "锦州", value: 54},
		{name: "南昌", value: 54},
		{name: "柳州", value: 54},
		{name: "三亚", value: 54},
		{name: "自贡", value: 56},
		{name: "吉林", value: 56},
		{name: "阳江", value: 57},
		{name: "泸州", value: 57},
		{name: "西宁", value: 57},
		{name: "宜宾", value: 58},
		{name: "呼和浩特", value: 58},
		{name: "成都", value: 58},
		{name: "大同", value: 58},
		{name: "镇江", value: 59},
		{name: "桂林", value: 59},
		{name: "张家界", value: 59},
		{name: "宜兴", value: 59},
		{name: "北海", value: 60},
		{name: "西安", value: 61},
		{name: "金坛", value: 62},
		{name: "东营", value: 62},
		{name: "牡丹江", value: 63},
		{name: "遵义", value: 63},
		{name: "绍兴", value: 63},
		{name: "扬州", value: 64},
		{name: "常州", value: 64},
		{name: "潍坊", value: 65},
		{name: "重庆", value: 66},
		{name: "台州", value: 67},
		{name: "南京", value: 67},
		{name: "滨州", value: 70},
		{name: "贵阳", value: 71},
		{name: "无锡", value: 71},
		{name: "本溪", value: 71},
		{name: "克拉玛依", value: 72},
		{name: "渭南", value: 72},
		{name: "马鞍山", value: 72},
		{name: "宝鸡", value: 72},
		{name: "焦作", value: 75},
		{name: "句容", value: 75},
		{name: "北京", value: 79},
		{name: "徐州", value: 79},
		{name: "衡水", value: 80},
		{name: "包头", value: 80},
		{name: "绵阳", value: 80},
		{name: "乌鲁木齐", value: 84},
		{name: "枣庄", value: 84},
		{name: "杭州", value: 84},
		{name: "淄博", value: 85},
		{name: "鞍山", value: 86},
		{name: "溧阳", value: 86},
		{name: "库尔勒", value: 86},
		{name: "安阳", value: 90},
		{name: "开封", value: 90},
		{name: "济南", value: 92},
		{name: "德阳", value: 93},
		{name: "温州", value: 95},
		{name: "九江", value: 96},
		{name: "邯郸", value: 98},
		{name: "临安", value: 99},
		{name: "兰州", value: 99},
		{name: "沧州", value: 100},
		{name: "临沂", value: 103},
		{name: "南充", value: 104},
		{name: "天津", value: 105},
		{name: "富阳", value: 106},
		{name: "泰安", value: 112},
		{name: "诸暨", value: 112},
		{name: "郑州", value: 113},
		{name: "哈尔滨", value: 114},
		{name: "聊城", value: 116},
		{name: "芜湖", value: 117},
		{name: "唐山", value: 119},
		{name: "平顶山", value: 119},
		{name: "邢台", value: 119},
		{name: "德州", value: 120},
		{name: "济宁", value: 120},
		{name: "荆州", value: 127},
		{name: "宜昌", value: 130},
		{name: "义乌", value: 132},
		{name: "丽水", value: 133},
		{name: "洛阳", value: 134},
		{name: "秦皇岛", value: 136},
		{name: "株洲", value: 143},
		{name: "石家庄", value: 147},
		{name: "莱芜", value: 148},
		{name: "常德", value: 152},
		{name: "保定", value: 153},
		{name: "湘潭", value: 154},
		{name: "金华", value: 157},
		{name: "岳阳", value: 169},
		{name: "长沙", value: 175},
		{name: "衢州", value: 177},
		{name: "廊坊", value: 193},
		{name: "菏泽", value: 194},
		{name: "合肥", value: 229},
		{name: "武汉", value: 273},
		{name: "大庆", value: 279}
	];
    var geoCoordProvince = {
    	"北京市" : [ 116.39737, 39.939502 ],
		"天津市" : [ 117.133262, 39.256321 ],
		"上海市" : [ 121.36464, 31.303465 ],
		"重庆市" : [ 106.32485, 29.895013 ],
		"河北省" : [ 114.336873, 38.21885 ],
		"山西省" : [ 112.349964, 38.044464 ],
		"辽宁省" : [ 123.241164, 41.948112 ],
		"吉林省" : [ 125.228072, 43.894927 ],
		"黑龙江省" : [ 126.479088, 45.985284 ],
		"江苏省" : [ 118.715429, 32.246466 ],
		"浙江省" : [ 120.040035, 30.350837 ],
		"安徽省" : [ 117.170056, 31.99595 ],
		"福建省" : [ 119.156964, 26.182279 ],
		"江西省" : [ 115.808656, 28.774611 ],
		"山东省" : [ 116.912494, 36.812038 ],
		"河南省" : [ 113.453802, 34.895028 ],
		"湖北省" : [ 114.116105, 30.764814 ],
		"湖南省" : [ 112.800698, 28.474291 ],
		"广东省" : [ 113.233035, 23.224606 ],
		"海南省" : [ 110.179083, 19.921006 ],
		"四川省" : [ 103.924003, 30.796585 ],
		"贵州省" : [ 106.499624, 26.844365 ],
		"云南省" : [ 102.599397, 25.248948 ],
		"陕西省" : [ 108.780889, 34.408508 ],
		"甘肃省" : [ 103.66644, 36.218003 ],
		"青海省" : [ 101.605943, 36.752842 ],
		"西藏省" : [ 90.972306, 29.838888 ],
		"广西省" : [ 108.265765, 23.020403 ],
		"内蒙古自治区" : [ 111.614073, 40.951504 ],
		"宁夏回族自治区" : [ 106.094884, 38.624116 ],
		"新疆维吾尔自治区" : [ 87.476819, 43.894927 ],
		"香港特别行政区" : [ 114.1529, 22.542716 ],
		"澳门特别行政区" : [ 113.417008, 22.337477 ],
		"台湾省" : [ 121.36464, 25.248948 ]
    }
    var geoCoordCity = {
		"上海市" : [ 121.487899486, 31.24916171 ],
		"临沧市" : [ 100.092612914, 23.8878061038 ],
		"丽江市" : [ 100.229628399, 26.8753510895 ],
		"保山市" : [ 99.1779956133, 25.1204891962 ],
		"大理白族自治州" : [ 100.223674789, 25.5968996394 ],
		"德宏傣族景颇族自治州" : [ 98.5894342874, 24.441239663 ],
		"怒江傈僳族自治州" : [ 98.8599320425, 25.8606769782 ],
		"文山壮族苗族自治州" : [ 104.246294318, 23.3740868504 ],
		"昆明市" : [ 102.714601139, 25.0491531005 ],
		"昭通市" : [ 103.725020656, 27.3406329636 ],
		"普洱市" : [ 100.98005773, 22.7887777801 ],
		"曲靖市" : [ 103.782538888, 25.5207581429 ],
		"楚雄彝族自治州" : [ 101.529382239, 25.0663556742 ],
		"玉溪市" : [ 102.545067892, 24.3704471344 ],
		"红河哈尼族彝族自治州" : [ 103.384064757, 23.3677175165 ],
		"西双版纳傣族自治州" : [ 100.803038275, 22.0094330022 ],
		"迪庆藏族自治州" : [ 99.7136815989, 27.8310294612 ],
		"乌兰察布市" : [ 113.112846391, 41.0223629468 ],
		"乌海市" : [ 106.831999097, 39.6831770068 ],
		"兴安盟" : [ 122.048166514, 46.0837570652 ],
		"包头市" : [ 109.846238532, 40.6471194257 ],
		"呼伦贝尔市" : [ 119.760821794, 49.2016360546 ],
		"呼和浩特市" : [ 111.66035052, 40.8283188731 ],
		"巴彦淖尔市" : [ 107.42380672, 40.7691799024 ],
		"赤峰市" : [ 118.930761192, 42.2971123203 ],
		"通辽市" : [ 122.260363263, 43.633756073 ],
		"鄂尔多斯市" : [ 109.993706251, 39.8164895606 ],
		"锡林郭勒盟" : [ 116.027339689, 43.9397048423 ],
		"阿拉善盟" : [ 105.695682871, 38.8430752644 ],
		"北京市" : [ 116.395645038, 39.9299857781 ],
		"台中市" : [ 119.337634104, 26.0911937119 ],
		"台北市" : [ 114.130474436, 22.3748329286 ],
		"台南市" : [ 121.360525873, 38.9658447898 ],
		"嘉义市" : [ 114.246701335, 22.7288657203 ],
		"高雄市" : [ 111.590952812, 21.9464822541 ],
		"吉林市" : [ 126.564543989, 43.8719883344 ],
		"四平市" : [ 124.391382074, 43.1755247011 ],
		"延边朝鲜族自治州" : [ 129.485901958, 42.8964136037 ],
		"松原市" : [ 124.832994532, 45.1360489701 ],
		"白城市" : [ 122.840776679, 45.6210862752 ],
		"白山市" : [ 126.435797675, 41.945859397 ],
		"辽源市" : [ 125.133686052, 42.9233026191 ],
		"通化市" : [ 125.942650139, 41.7363971299 ],
		"长春市" : [ 125.313642427, 43.8983376071 ],
		"乐山市" : [ 103.760824239, 29.6009576111 ],
		"内江市" : [ 105.073055992, 29.5994615348 ],
		"凉山彝族自治州" : [ 102.259590803, 27.8923929037 ],
		"南充市" : [ 106.105553984, 30.8009651682 ],
		"宜宾市" : [ 104.633019062, 28.7696747963 ],
		"巴中市" : [ 106.757915842, 31.8691891592 ],
		"广元市" : [ 105.81968694, 32.4410401584 ],
		"广安市" : [ 106.635720331, 30.4639838879 ],
		"德阳市" : [ 104.402397818, 31.1311396527 ],
		"成都市" : [ 104.067923463, 30.6799428454 ],
		"攀枝花市" : [ 101.722423152, 26.5875712571 ],
		"泸州市" : [ 105.443970289, 28.8959298039 ],
		"甘孜藏族自治州" : [ 101.969232063, 30.0551441144 ],
		"眉山市" : [ 103.841429563, 30.0611150799 ],
		"绵阳市" : [ 104.705518975, 31.5047012581 ],
		"自贡市" : [ 104.776071339, 29.3591568895 ],
		"资阳市" : [ 104.635930302, 30.132191434 ],
		"达州市" : [ 107.494973447, 31.2141988589 ],
		"遂宁市" : [ 105.564887792, 30.5574913504 ],
		"阿坝藏族羌族自治州" : [ 102.228564689, 31.9057628583 ],
		"雅安市" : [ 103.009356466, 29.9997163371 ],
		"天津市" : [ 117.210813092, 39.1439299033 ],
		"中卫市" : [ 105.196754199, 37.5211241916 ],
		"吴忠市" : [ 106.208254199, 37.9935610029 ],
		"固原市" : [ 106.285267996, 36.0215234807 ],
		"石嘴山市" : [ 106.379337202, 39.0202232836 ],
		"银川市" : [ 106.206478608, 38.5026210119 ],
		"亳州市" : [ 115.787928245, 33.8712105653 ],
		"六安市" : [ 116.505252683, 31.7555583552 ],
		"合肥市" : [ 117.282699092, 31.8669422607 ],
		"安庆市" : [ 117.058738772, 30.5378978174 ],
		"宣城市" : [ 118.752096311, 30.9516423543 ],
		"宿州市" : [ 116.988692412, 33.6367723858 ],
		"池州市" : [ 117.494476772, 30.6600192482 ],
		"淮北市" : [ 116.791447429, 33.9600233054 ],
		"淮南市" : [ 117.018638863, 32.6428118237 ],
		"滁州市" : [ 118.324570351, 32.3173505954 ],
		"芜湖市" : [ 118.384108423, 31.3660197875 ],
		"蚌埠市" : [ 117.357079866, 32.9294989067 ],
		"铜陵市" : [ 117.819428729, 30.9409296947 ],
		"阜阳市" : [ 115.820932259, 32.9012113306 ],
		"马鞍山市" : [ 118.515881847, 31.6885281589 ],
		"黄山市" : [ 118.293569632, 29.7344348562 ],
		"东营市" : [ 118.583926333, 37.4871211553 ],
		"临沂市" : [ 118.340768237, 35.0724090744 ],
		"威海市" : [ 122.093958366, 37.5287870813 ],
		"德州市" : [ 116.328161364, 37.4608259263 ],
		"日照市" : [ 119.507179943, 35.4202251931 ],
		"枣庄市" : [ 117.279305383, 34.8078830784 ],
		"泰安市" : [ 117.089414917, 36.1880777589 ],
		"济南市" : [ 117.024967066, 36.6827847272 ],
		"济宁市" : [ 116.600797625, 35.4021216643 ],
		"淄博市" : [ 118.059134278, 36.8046848542 ],
		"滨州市" : [ 117.968292415, 37.4053139418 ],
		"潍坊市" : [ 119.142633823, 36.7161148731 ],
		"烟台市" : [ 121.30955503, 37.5365615629 ],
		"聊城市" : [ 115.986869139, 36.4558285147 ],
		"莱芜市" : [ 117.684666912, 36.2336541336 ],
		"菏泽市" : [ 115.463359775, 35.2624404961 ],
		"青岛市" : [ 120.384428184, 36.1052149013 ],
		"临汾市" : [ 111.538787596, 36.0997454436 ],
		"吕梁市" : [ 111.143156602, 37.527316097 ],
		"大同市" : [ 113.290508673, 40.1137444997 ],
		"太原市" : [ 112.550863589, 37.890277054 ],
		"忻州市" : [ 112.727938829, 38.461030573 ],
		"晋中市" : [ 112.7385144, 37.6933615268 ],
		"晋城市" : [ 112.867332758, 35.4998344672 ],
		"朔州市" : [ 112.479927727, 39.3376719662 ],
		"运城市" : [ 111.006853653, 35.0388594798 ],
		"长治市" : [ 113.120292086, 36.2016643857 ],
		"阳泉市" : [ 113.569237602, 37.8695294932 ],
		"东莞市" : [ 113.763433991, 23.0430238154 ],
		"中山市" : [ 113.422060021, 22.5451775145 ],
		"云浮市" : [ 112.050945959, 22.9379756855 ],
		"佛山市" : [ 113.134025635, 23.0350948405 ],
		"广州市" : [ 113.307649675, 23.1200491021 ],
		"惠州市" : [ 114.41065808, 23.1135398524 ],
		"揭阳市" : [ 116.379500855, 23.5479994669 ],
		"梅州市" : [ 116.126403098, 24.304570606 ],
		"汕头市" : [ 116.728650288, 23.3839084533 ],
		"汕尾市" : [ 115.372924289, 22.7787305002 ],
		"江门市" : [ 113.078125341, 22.5751167835 ],
		"河源市" : [ 114.713721476, 23.7572508505 ],
		"深圳市" : [ 114.025973657, 22.5460535462 ],
		"清远市" : [ 113.040773349, 23.6984685504 ],
		"湛江市" : [ 110.365067263, 21.2574631038 ],
		"潮州市" : [ 116.630075991, 23.6618116765 ],
		"珠海市" : [ 113.562447026, 22.2569146461 ],
		"肇庆市" : [ 112.47965337, 23.0786632829 ],
		"茂名市" : [ 110.931245331, 21.6682257188 ],
		"阳江市" : [ 111.977009756, 21.8715173045 ],
		"韶关市" : [ 113.594461107, 24.8029603119 ],
		"北海市" : [ 109.122627919, 21.472718235 ],
		"南宁市" : [ 108.297233556, 22.8064929356 ],
		"崇左市" : [ 107.357322038, 22.4154552965 ],
		"来宾市" : [ 109.231816505, 23.7411659265 ],
		"柳州市" : [ 109.42240181, 24.3290533525 ],
		"桂林市" : [ 110.260920147, 25.262901246 ],
		"梧州市" : [ 111.30547195, 23.4853946367 ],
		"河池市" : [ 108.069947709, 24.6995207829 ],
		"玉林市" : [ 110.151676316, 22.6439736084 ],
		"百色市" : [ 106.631821404, 23.9015123679 ],
		"贵港市" : [ 109.613707557, 23.1033731644 ],
		"贺州市" : [ 111.552594179, 24.4110535471 ],
		"钦州市" : [ 108.638798056, 21.9733504653 ],
		"防城港市" : [ 108.351791153, 21.6173984705 ],
		"乌鲁木齐市" : [ 87.5649877411, 43.8403803472 ],
		"伊犁哈萨克自治州" : [ 81.2978535304, 43.9222480963 ],
		"克孜勒苏柯尔克孜自治州" : [ 76.1375644775, 39.7503455778 ],
		"克拉玛依市" : [ 84.8811801861, 45.5943310667 ],
		"博尔塔拉蒙古自治州" : [ 82.0524362672, 44.9136513743 ],
		"吐鲁番地区" : [ 89.1815948657, 42.9604700169 ],
		"和田地区" : [ 79.9302386372, 37.1167744927 ],
		"哈密地区" : [ 93.5283550928, 42.8585963324 ],
		"喀什地区" : [ 75.9929732675, 39.4706271887 ],
		"塔城地区" : [ 82.9748805837, 46.7586836297 ],
		"昌吉回族自治州" : [ 87.2960381257, 44.0070578985 ],
		"自治区直辖" : [ 85.6148993383, 42.1270009576 ],
		"阿克苏地区" : [ 80.2698461793, 41.1717309015 ],
		"阿勒泰地区" : [ 88.1379154871, 47.8397444862 ],
		"南京市" : [ 118.778074408, 32.0572355018 ],
		"南通市" : [ 120.873800951, 32.0146645408 ],
		"宿迁市" : [ 118.296893379, 33.9520497337 ],
		"常州市" : [ 119.981861013, 31.7713967447 ],
		"徐州市" : [ 117.188106623, 34.2715534311 ],
		"扬州市" : [ 119.427777551, 32.4085052546 ],
		"无锡市" : [ 120.305455901, 31.5700374519 ],
		"泰州市" : [ 119.919606016, 32.4760532748 ],
		"淮安市" : [ 119.030186365, 33.6065127393 ],
		"盐城市" : [ 120.148871818, 33.3798618771 ],
		"苏州市" : [ 120.619907115, 31.317987368 ],
		"连云港市" : [ 119.173872217, 34.601548967 ],
		"镇江市" : [ 119.455835405, 32.2044094436 ],
		"上饶市" : [ 117.955463877, 28.4576225539 ],
		"九江市" : [ 115.999848022, 29.7196395261 ],
		"南昌市" : [ 115.893527546, 28.6895780001 ],
		"吉安市" : [ 114.992038711, 27.1138476502 ],
		"宜春市" : [ 114.400038672, 27.8111298958 ],
		"抚州市" : [ 116.360918867, 27.9545451703 ],
		"新余市" : [ 114.947117417, 27.8223215586 ],
		"景德镇市" : [ 117.186522625, 29.3035627684 ],
		"萍乡市" : [ 113.859917033, 27.639544223 ],
		"赣州市" : [ 114.935909079, 25.8452955363 ],
		"鹰潭市" : [ 117.035450186, 28.2413095972 ],
		"保定市" : [ 115.494810169, 38.886564548 ],
		"唐山市" : [ 118.183450598, 39.6505309225 ],
		"廊坊市" : [ 116.703602223, 39.5186106251 ],
		"张家口市" : [ 114.89378153, 40.8111884911 ],
		"承德市" : [ 117.933822456, 40.9925210525 ],
		"沧州市" : [ 116.863806476, 38.2976153503 ],
		"石家庄市" : [ 114.522081844, 38.0489583146 ],
		"秦皇岛市" : [ 119.604367616, 39.9454615659 ],
		"衡水市" : [ 115.686228653, 37.7469290459 ],
		"邢台市" : [ 114.520486813, 37.0695311969 ],
		"邯郸市" : [ 114.482693932, 36.6093079285 ],
		"三门峡市" : [ 111.181262093, 34.7833199411 ],
		"信阳市" : [ 114.085490993, 32.1285823075 ],
		"南阳市" : [ 112.542841901, 33.0114195691 ],
		"周口市" : [ 114.654101942, 33.6237408181 ],
		"商丘市" : [ 115.641885688, 34.4385886402 ],
		"安阳市" : [ 114.351806508, 36.1102667222 ],
		"平顶山市" : [ 113.300848978, 33.7453014565 ],
		"开封市" : [ 114.351642118, 34.8018541758 ],
		"新乡市" : [ 113.912690161, 35.3072575577 ],
		"洛阳市" : [ 112.447524769, 34.6573678177 ],
		"漯河市" : [ 114.0460614, 33.5762786885 ],
		"濮阳市" : [ 115.026627441, 35.7532978882 ],
		"焦作市" : [ 113.211835885, 35.234607555 ],
		"省直辖" : [ 113.486804058, 34.157183768 ],
		"许昌市" : [ 113.83531246, 34.0267395887 ],
		"郑州市" : [ 113.64964385, 34.7566100641 ],
		"驻马店市" : [ 114.049153547, 32.9831581541 ],
		"鹤壁市" : [ 114.297769838, 35.7554258742 ],
		"丽水市" : [ 119.929575843, 28.4562995521 ],
		"台州市" : [ 121.440612936, 28.6682832857 ],
		"嘉兴市" : [ 120.760427699, 30.7739922396 ],
		"宁波市" : [ 121.579005973, 29.8852589659 ],
		"杭州市" : [ 120.219375416, 30.2592444615 ],
		"温州市" : [ 120.690634734, 28.002837594 ],
		"湖州市" : [ 120.137243163, 30.8779251557 ],
		"绍兴市" : [ 120.592467386, 30.0023645805 ],
		"舟山市" : [ 122.169872098, 30.0360103026 ],
		"衢州市" : [ 118.875841652, 28.9569104475 ],
		"金华市" : [ 119.652575704, 29.1028991054 ],
		"三亚市" : [ 109.522771281, 18.2577759149 ],
		"三沙市" : [ 112.350383075, 16.840062894 ],
		"海口市" : [ 110.330801848, 20.022071277 ],
		"省直辖" : [ 109.733755488, 19.1805008013 ],
		"十堰市" : [ 110.801228917, 32.6369943395 ],
		"咸宁市" : [ 114.300060592, 29.8806567577 ],
		"孝感市" : [ 113.935734392, 30.9279547842 ],
		"宜昌市" : [ 111.310981092, 30.732757818 ],
		"恩施土家族苗族自治州" : [ 109.491923304, 30.2858883166 ],
		"武汉市" : [ 114.316200103, 30.5810841269 ],
		"省直辖" : [ 112.410562192, 31.2093162501 ],
		"荆州市" : [ 112.241865807, 30.332590523 ],
		"荆门市" : [ 112.217330299, 31.0426112029 ],
		"襄阳市" : [ 112.250092848, 32.2291685915 ],
		"鄂州市" : [ 114.895594041, 30.3844393228 ],
		"随州市" : [ 113.379358364, 31.7178576082 ],
		"黄冈市" : [ 114.906618047, 30.4461089379 ],
		"黄石市" : [ 115.050683164, 30.2161271277 ],
		"娄底市" : [ 111.996396357, 27.7410733023 ],
		"岳阳市" : [ 113.146195519, 29.3780070755 ],
		"常德市" : [ 111.653718137, 29.0121488552 ],
		"张家界市" : [ 110.481620157, 29.1248893532 ],
		"怀化市" : [ 109.986958796, 27.5574829012 ],
		"株洲市" : [ 113.131695341, 27.8274329277 ],
		"永州市" : [ 111.614647686, 26.4359716468 ],
		"湘潭市" : [ 112.935555633, 27.835095053 ],
		"湘西土家族苗族自治州" : [ 109.7457458, 28.3179507937 ],
		"益阳市" : [ 112.366546645, 28.5880877799 ],
		"衡阳市" : [ 112.583818811, 26.8981644154 ],
		"邵阳市" : [ 111.461525404, 27.2368112449 ],
		"郴州市" : [ 113.037704468, 25.7822639757 ],
		"长沙市" : [ 112.979352788, 28.2134782309 ],
		"无堂区划分区域" : [ 113.557519102, 22.2041179884 ],
		"澳门半岛" : [ 113.566432335, 22.1950041592 ],
		"澳门离岛" : [ 113.557519102, 22.2041179884 ],
		"临夏回族自治州" : [ 103.215249178, 35.5985143488 ],
		"兰州市" : [ 103.823305441, 36.064225525 ],
		"嘉峪关市" : [ 98.2816345853, 39.8023973267 ],
		"天水市" : [ 105.736931623, 34.5843194189 ],
		"定西市" : [ 104.626637601, 35.5860562418 ],
		"平凉市" : [ 106.688911157, 35.55011019 ],
		"庆阳市" : [ 107.644227087, 35.7268007545 ],
		"张掖市" : [ 100.459891869, 38.939320297 ],
		"武威市" : [ 102.640147343, 37.9331721429 ],
		"甘南藏族自治州" : [ 102.917442486, 34.9922111784 ],
		"白银市" : [ 104.171240904, 36.5466817062 ],
		"酒泉市" : [ 98.5084145062, 39.7414737682 ],
		"金昌市" : [ 102.208126263, 38.5160717995 ],
		"陇南市" : [ 104.934573406, 33.3944799729 ],
		"三明市" : [ 117.642193934, 26.2708352794 ],
		"南平市" : [ 118.181882949, 26.6436264742 ],
		"厦门市" : [ 118.103886046, 24.4892306125 ],
		"宁德市" : [ 119.54208215, 26.6565274192 ],
		"泉州市" : [ 118.600362343, 24.901652384 ],
		"漳州市" : [ 117.676204679, 24.5170647798 ],
		"福州市" : [ 119.330221107, 26.0471254966 ],
		"莆田市" : [ 119.077730964, 25.4484501367 ],
		"龙岩市" : [ 117.017996739, 25.0786854335 ],
		"山南地区" : [ 91.7506438744, 29.2290269317 ],
		"拉萨市" : [ 91.111890896, 29.6625570621 ],
		"日喀则地区" : [ 88.8914855677, 29.2690232039 ],
		"昌都地区" : [ 97.18558158, 31.1405756319 ],
		"林芝地区" : [ 94.3499854582, 29.6669406258 ],
		"那曲地区" : [ 92.0670183689, 31.4806798301 ],
		"阿里地区" : [ 81.1076686895, 30.4045565883 ],
		"六盘水市" : [ 104.85208676, 26.5918660603 ],
		"安顺市" : [ 105.928269966, 26.2285945777 ],
		"毕节市" : [ 105.333323371, 27.4085621313 ],
		"贵阳市" : [ 106.709177096, 26.6299067414 ],
		"遵义市" : [ 106.931260316, 27.6999613771 ],
		"铜仁市" : [ 109.168558028, 27.6749026906 ],
		"黔东南苗族侗族自治州" : [ 107.985352573, 26.5839917665 ],
		"黔南布依族苗族自治州" : [ 107.52320511, 26.2645359974 ],
		"黔西南布依族苗族自治州" : [ 104.900557798, 25.0951480559 ],
		"丹东市" : [ 124.338543115, 40.1290228266 ],
		"大连市" : [ 121.593477781, 38.9487099383 ],
		"抚顺市" : [ 123.929819767, 41.8773038296 ],
		"朝阳市" : [ 120.446162703, 41.5718276679 ],
		"本溪市" : [ 123.77806237, 41.3258376266 ],
		"沈阳市" : [ 123.432790922, 41.8086447835 ],
		"盘锦市" : [ 122.07322781, 41.141248023 ],
		"营口市" : [ 122.233391371, 40.6686510665 ],
		"葫芦岛市" : [ 120.860757645, 40.7430298813 ],
		"辽阳市" : [ 123.172451205, 41.2733392656 ],
		"铁岭市" : [ 123.854849615, 42.2997570121 ],
		"锦州市" : [ 121.147748738, 41.1308788759 ],
		"阜新市" : [ 121.660822129, 42.0192501071 ],
		"鞍山市" : [ 123.007763329, 41.1187436822 ],
		"重庆市" : [ 106.530635013, 29.5446061089 ],
		"咸阳市" : [ 108.707509278, 34.345372996 ],
		"商洛市" : [ 109.934208154, 33.8739073951 ],
		"安康市" : [ 109.038044563, 32.70437045 ],
		"宝鸡市" : [ 107.170645452, 34.3640808097 ],
		"延安市" : [ 109.500509757, 36.6033203523 ],
		"榆林市" : [ 109.745925744, 38.2794392401 ],
		"汉中市" : [ 107.045477629, 33.0815689782 ],
		"渭南市" : [ 109.483932697, 34.5023579758 ],
		"西安市" : [ 108.953098279, 34.2777998978 ],
		"铜川市" : [ 108.968067013, 34.9083676964 ],
		"果洛藏族自治州" : [ 100.223722769, 34.4804845846 ],
		"海东地区" : [ 102.085206987, 36.5176101677 ],
		"海北藏族自治州" : [ 100.879802174, 36.9606541011 ],
		"海南藏族自治州" : [ 100.624066094, 36.2843638038 ],
		"海西蒙古族藏族自治州" : [ 97.3426254153, 37.3737990706 ],
		"玉树藏族自治州" : [ 97.0133161374, 33.0062399097 ],
		"西宁市" : [ 101.76792099, 36.640738612 ],
		"黄南藏族自治州" : [ 102.007600308, 35.5228515517 ],
		"九龙" : [ 114.173291988, 22.3072458588 ],
		"新界" : [ 114.146701965, 22.4274312754 ],
		"香港岛" : [ 114.183870524, 22.2721034276 ],
		"七台河市" : [ 131.019048047, 45.7750053686 ],
		"伊春市" : [ 128.910765978, 47.7346850751 ],
		"佳木斯市" : [ 130.284734586, 46.8137796047 ],
		"双鸭山市" : [ 131.17140174, 46.6551020625 ],
		"哈尔滨市" : [ 126.657716855, 45.7732246332 ],
		"大兴安岭地区" : [ 124.19610419, 51.991788968 ],
		"大庆市" : [ 125.02183973, 46.59670902 ],
		"牡丹江市" : [ 129.608035396, 44.5885211528 ],
		"绥化市" : [ 126.989094572, 46.646063927 ],
		"鸡西市" : [ 130.941767273, 45.3215398866 ],
		"鹤岗市" : [ 130.292472051, 47.3386659037 ],
		"黑河市" : [ 127.500830295, 50.2506900907 ],
		"齐齐哈尔市" : [ 123.987288942, 47.3476998134 ]
	}
    var curIndx = 0;
    var mapType = [
        'china',
        // 23个省
        '广东', '青海', '四川', '海南', '陕西', 
        '甘肃', '云南', '湖南', '湖北', '黑龙江',
        '贵州', '山东', '江西', '河南', '河北',
        '山西', '安徽', '福建', '浙江', '江苏', 
        '吉林', '辽宁', '台湾',
        // 5个自治区
        '新疆', '广西', '宁夏', '内蒙古', '西藏', 
        // 4个直辖市
        '北京', '天津', '上海', '重庆',
        // 2个特别行政区
        '香港', '澳门'
    ];
    /**
     * 销售额地图
     * */
    var salesMapOption = {
		title : {
			subtext : 'china （点击切换）',
			show : false
		},
		tooltip : {
			trigger : 'item',
//			backgroundColor:'#0099cc',
		},
		dataRange : {
			min : 0,
			max : 300,
			color : ['#0000cc','#019AEF','#2DB7F5'],
			text : [ '高', '低' ],
			calculable : true,
			show : false
		},
		series : [ {
			name : 'sales',
			type : 'map',
			mapType : 'china',
			selectedMode : 'single',
			itemStyle : {
				normal : {
					color : '#ffffff',
					borderColor : '#999999',
					borderWidth : 1.5,
					label : {
						show : false,
					},
					areaStyle : {
						color : '#ffffff'
					}
				},
				emphasis : {
					color : '#ffffff',
					borderColor : '#999999',
					borderWidth : 1.5,
					label : {
						show : false,
					},
					areaStyle : {
						color : '#ffffff'
					}
				}
			},
			data : [],
			markPoint : {
				symbol : 'circle',
				symbolSize : 5,
				clickable:false,
				itemStyle : {
					normal : {
						borderWidth : 1,
						label : {
							show : false,
						}
					},
					emphasis : {
						borderWidth : 5,
						label : {
							show : true,
							formatter : function(params) {
								return params.name;
							},
							position : 'bottom'
						}
					}
				},
				data : []
			},
			geoCoord : geoCoordProvince
		},
		{
            name : 'sales',
			type : 'map',
			data : [],
			itemStyle : {
				normal : {
					color : '#fff',
				},
				emphasis : {
					label : {
						show : false
					},
					areaStyle : {
						color : '#fff'
					}
				}
			},
			markPoint : {
				symbol : 'emptyCircle',
				symbolSize : 10,
				effect : {
					show : true,
					shadowBlur : 0
				},
				itemStyle : {
					normal : {
						label : {
							show : false
						}
					},
					emphasis : {
						borderWidth : 5,
						label : {
							show : true,
							formatter : function(params) {
								return params.name;
							},
							position : 'bottom'
						}
					}
				},
				data : []
			}
        } ]
	};
    	                    
    /**
	 * 销售额地图
	 */
    var salesMapObj = {
    	chartId : 'salesMapChart',
		chart: initEChart("salesMapChart"),
    	option: salesMapOption,
    	provinceId : null,
    	count : 0,
    	total : 0,
    	pointArr : [],
    	mapFlag : 0,
    	timer : 0,
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		self.getRequestDatas();
    	},
    	getRequestDatas: function(){
    		var self = this;
    		clearEChart(self.chart);
    		loadingChart(self.chartId);
    		var param = {
    			organId : self.organId,
    			provinceId : self.provinceId
    		}
    		$.post(urls.querySalesMapByProvinceUrl, param, function(data){
    			self.getOption(data);
    		});
    	},
    	getOption: function(data){
			var self = this;
			if(data.seriesData != undefined && data.seriesData.length > 1){
				hideChart(self.chartId);
				self.option.dataRange.min = data.minNum - 1;
				self.option.dataRange.max = data.maxNum + 1;
				self.option.series[0].data = data.seriesData;
				self.option.series[0].markPoint.data = data.pointData;
				self.option.series[1].markPoint.data = data.pointData;
				var arrName = new Array(), arrVal = new Array(), msg = '';
				if(data.pointData != null && data.pointData.length > 0){
					for(var i = 0; i < data.pointData.length; i++){
						arrName[i] = data.pointData[i].name;
						arrVal[i] = data.pointData[i].value;
						msg += data.pointData[i].name + ",";
					}
					self.pointArr = data.pointData;
					self.total = data.pointData.length;
				}
				self.option.tooltip.formatter = function(a,b,c) {
					if(msg.indexOf(a.name) != -1){
						var val = a.value;
						for(var i = 0; i < arrName.length; i++){
							if(arrName[i].indexOf(a.name) != -1){
								val = arrVal[i];
							}
						}
						return a.name + '销售额: ' + val + '万元<br> ';
					} else {
						return a.name + '销售额: 0万元<br> ';
					}
		        }
				self.setOption();
			}else{
				hideChart(self.chartId, true);
			}
		},
		setOption: function(){
			var self = this;
			self.chart.setOption(self.option);
			self.chart.resize();
			self.clearIntervalFun();
			self.autoShowTooltipFun();
			self.chart.un(ecConfig.EVENT.HOVER).on(ecConfig.EVENT.HOVER, self.clearIntervalFun);
			self.chart.un(ecConfig.EVENT.MOUSEOUT).on(ecConfig.EVENT.MOUSEOUT, self.setIntervalFun);
			self.chart.un(ecConfig.EVENT.CLICK).on(ecConfig.EVENT.CLICK, self.mapSelectFun);
			self.chart.un(ecConfig.EVENT.MAP_SELECTED).on(ecConfig.EVENT.MAP_SELECTED, self.mapSelectFun);
		},
		mapSelectFun: function(param){
			var len = mapType.length;
			var mt, name, selected = new Array();
			if(param.type == 'click'){
				salesMapObj.isChose = true;
				if(curIndx == 0){
					mt = 'china';
				} else {
					mt = param.name;
				}
				name = param.name;
				$.each(mapType, function(ind, obj){
					if(name.indexOf(obj) != -1){
						name = obj;
						selected[name] = true;
					}
				});
			} else {
				salesMapObj.isChose = false;
				mt = mapType[curIndx % len];
				selected = param.selected;
			}
		    var str = null;
		    if(!salesMapObj.isChose) return;
		    if (mt == 'china') {
		    	salesMapObj.mapFlag = 1;
		    	var op = salesMapObj.chart.getOption().series[0].data;
		        for (var i in selected) {
		            if (selected[i]) {
		            	for (var p = 0; p < op.length; p++) {
		            		if (op[p].name.indexOf(i) != -1) {
		            			str = op[p].id == undefined ? null : op[p].id;
		            		}
		            	}
		                mt = i;
		                while (len--) {
		                    if (mapType[len] == mt) {
		                        curIndx = len;
		                    }
		                }
		                break;
		            }
		        }
		        salesMapObj.option.series[0].geoCoord = geoCoordCity;
		    }
		    else {
		    	salesMapObj.mapFlag = 0;
		        curIndx = 0;
		        mt = 'china';
		        str = null;
		        salesMapObj.option.series[0].geoCoord = geoCoordProvince;
		    }
		    salesMapObj.count = 0;
	    	salesMapObj.provinceId = str;
		    salesMapObj.option.series[0].mapType = mt;
		    salesMapObj.option.series[1].mapType = mt;
		    salesMapObj.option.series[0].markPoint.data=[];
		    salesMapObj.option.series[1].markPoint.data=[];
		    salesMapObj.chart.setOption(salesMapObj.option, true);
		    salesMapObj.getRequestDatas();
		    salesMapObj.isChose = false;
		},
		autoShowTooltipFun: function(){
			var tooltip = salesMapObj.chart.component.tooltip;
			salesMapObj.timer = setInterval(function () {
	            var curr = salesMapObj.count % salesMapObj.total;
	            if(salesMapObj.pointArr != null && salesMapObj.pointArr.length > 0){
	            	var mapName = salesMapObj.pointArr[curr].name;
	            	if(salesMapObj.mapFlag == 0){
	            		if(mapName.indexOf('黑龙江') != -1 || mapName.indexOf('内蒙古') != -1){
	            			mapName = mapName.substr(0, 3);
	            		} else {
	            			mapName = mapName.substr(0, 2);
	            		}
	            	}
	            	tooltip.showTip({seriesIndex: '0', name: mapName});
	            }
	            salesMapObj.count += 1;
	        }, 5000);
		},
		clearIntervalFun: function(){
			clearInterval(salesMapObj.timer);
			salesMapObj.timer = 0;
		},
		setIntervalFun: function(){
			salesMapObj.autoShowTooltipFun();
		}
    }
    
    /**
     * 时间切片
     * */
    var timecrowdObj = {
		teamRankingId : '#teamRankingTimeBox',
		salesRankingId : '#salesRankingTimeBox',
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
    			url : urls.queryTimesUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				defaultDatas.minDate = data.minDate;
    				defaultDatas.maxDate = data.maxDate;
    				defaultDatas.selectDate = data.selectDate;
    				defaultDatas.selectYear = data.selectYear;
    				defaultDatas.viewSelectDate = data.selectDate;
    				defaultDatas.pkSelectDate = data.selectDate;
    				defaultDatas.salesDetailDate = data.selectDate;
    				defaultDatas.personDetailDate = data.selectDate;
    				if(!_.isUndefined(data.selectDate) && data.selectDate.length > 0){
    					defaultDatas.salesRankingDate = data.selectDate[0]; 
    				}
    				self.teamRankingSelectionFun(data);
    				self.salesRankingSelectionFun(data);
    			},
    			error : function(){}
    		});
    	},
    	teamRankingSelectionFun: function(data){
    		var self = this;
    		 $(self.teamRankingId).selection({
 				dateType: 7,
 				dateRange:{
 					min: data.minDate,
 					max: data.maxDate
 				},
 				dateSelected: data.selectDate,
 				ok:function(event, data){
 					defaultDatas.salesCountDate = data.date.join("");
 					if(_.isNull(defaultDatas.teamRankingOrganId) || _.isUndefined(defaultDatas.teamRankingOrganId) || defaultDatas.teamRankingOrganId == ''){
 						defaultDatas.teamRankingOrganId = reqOrgId;
 					}
 					teamRankingObj.init(defaultDatas.teamRankingOrganId);
 				}
 			});
    	},
    	salesRankingSelectionFun: function(data){
    		var self = this;
    		$(self.salesRankingId).selection({
    			dateType: 7,
    			dateRange:{
    				min: data.minDate,
    				max: data.maxDate
    			},
    			dateSelected: data.selectDate,
    			ok:function(event, data){
    				defaultDatas.salesRankingDate = data.date.join("");
    				salesRankingObj.init(reqOrgId);
    			}
    		});
    	}
    }
    
    /**
	 * 组织销售统计
	 */
    var teamRankingOption = {
		grid : {
			x: 5,
            y: 25,
            x2: 5,
            y2: 45,
			borderWidth : 0
		},
		calculable : false,
		xAxis : [ {
			type : 'category',
			data : [ ],
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			splitLine : {
				show : false
			},
			axisLabel : {
				show : true,
				interval : 0
			}
		} ],
		yAxis : [ {
			type : 'value',
			show : false
		} ],
		series : [ {
			type : 'bar',
			stack : 'sum',
			barMaxWidth: 30,
			barCategoryGap : '50%',
			itemStyle : {
				normal : {
					color : '#0099cc',
					barBorderColor : '#0099cc',
					barBorderWidth : 1,
					barBorderRadius : 0,
					label : {
						show : false
					}
				}
			},
			data : [ ]
		}, {
			type : 'bar',
			stack : 'sum',
			itemStyle : {
				normal : {
					color : '#ffffff',
					barBorderColor : '#e0e0e0',
					barBorderWidth : 1,
					barBorderRadius : 0,
					label : {
                        show: true, 
                        position: 'top',
                        formatter: function (params) {
                            for (var i = 0, l = teamRankingOption.xAxis[0].data.length; i < l; i++) {
                                if (teamRankingOption.xAxis[0].data[i] == params.name) {
                                    return teamRankingOption.series[0].data[i].value;
                                }
                            }
                        },
                        textStyle: {
                            color: '#666666'
                        }
                    }
				},
				emphasis : {
					label : {
						show : true,
						textStyle : {
							color : '#666666'
						}
					}
				}
			},
			data : [ ]
		} ]
	};
    $("[data-toggle='tooltip']").tooltip();
    /**
     * 组织销售统计
     * */
    var teamRankingObj = {
		chartId: "teamRankingChart",
		chart: initEChart("teamRankingChart"),
    	option: teamRankingOption,
    	returnOrgan: '#returnOrgan',
    	returnOrganText: '.returnOrganText',
    	returnTeamText: '.returnTeamText',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
			self.getRequestDatas();
    		self.returnBtnClick();
    	},
    	getRequestDatas: function(){
    		var self = this;
    		loadingChart(self.chartId);
    		clearEChart(self.chart);
    		if(defaultDatas.organNum == 0){
				$(self.returnTeamText).addClass('hide');
				$(self.returnOrganText).addClass('hide');
    		}
    		var param = {
    			organId : self.organId,
    			date : defaultDatas.salesCountDate,
    			flag : defaultDatas.organNum
    		}
    		$.post(urls.queryOrganSalesStatisticsUrl, param, function(data){
    			self.getOption(data);
    		});
    	},
    	getOption: function(data){
			var self = this;
			if(data.names != undefined && data.names.length > 1){
				hideChart(self.chartId);
				self.option.xAxis[0].data = data.names;
				self.option.series[0].data = data.datas1;
				self.option.series[1].data = data.datas2;
				if(data.names.length > 5){
					dataZoom.y = 170;
//					dataZoom.end = 70;
					data.names.length > 10 ? (dataZoom.end = 30) : ( data.names.length > 6 ? (dataZoom.end = 50) : (dataZoom.end = 70));
					self.option.dataZoom = dataZoom;
					self.option.grid.y2 = 60;
				} else {
					self.option.dataZoom = null;
					self.option.grid.y2 = 45;
				}
//				changeXAxisLabelRotate(self.option, data.names);
				self.setOption();
			}else{
				hideChart(self.chartId, true);
			}
		},
		setOption: function(){
			var self = this;
			if(defaultDatas.organNum < 2){
				self.option.series[0].clickable = true;
				self.option.series[1].clickable = true;
				self.chart.un(ecConfig.EVENT.CLICK).on(ecConfig.EVENT.CLICK, self.eConsole);
			} else {
				self.option.series[0].clickable = false;
				self.option.series[1].clickable = false;
				self.chart.un(ecConfig.EVENT.CLICK);
			}
			self.chart.setOption(self.option);
			self.chart.resize();
		},
		eConsole : function(param) {
			var self = this;
			if (typeof param.seriesIndex != 'undefined') {
				if(param.value != 0){
					defaultDatas.organArr[defaultDatas.organNum] = teamRankingObj.organId;
					teamRankingObj.organId = param.data.id;
					defaultDatas.teamRankingOrganId = param.data.id;
					clearEChart(teamRankingObj.chart);
					defaultDatas.organArr[defaultDatas.organNum + 1] = param.data.id;
					defaultDatas.organNum = defaultDatas.organNum + 1;
					teamRankingObj.showReturnBtn(true, defaultDatas.organNum);
					teamRankingObj.getRequestDatas();
				}
			}
		},
		returnBtnClick: function(){
			var self = this;
			$(self.returnOrgan).unbind('click').bind('click', function(){
				clearEChart(teamRankingObj.chart);
				defaultDatas.organNum = defaultDatas.organNum - 1;
				if(defaultDatas.organNum > 0){
					self.organId = defaultDatas.organArr[defaultDatas.organNum];
					self.showReturnBtn(true, defaultDatas.organNum);
					if(defaultDatas.organNum == 0){
						self.showReturnBtn(false);
					}
				} else {
					self.organId = reqOrgId;
					self.showReturnBtn(false);
				}
				defaultDatas.teamRankingOrganId = self.organId;
				teamRankingObj.getRequestDatas();
			});
		},
		showReturnBtn: function(flag, num){
			var self = this;
			if(flag){
				if(num > 1){
					$(self.returnTeamText).removeClass('hide').siblings().addClass('hide');
				} else {
					$(self.returnOrganText).removeClass('hide').siblings().addClass('hide');
				}
				$(self.returnOrgan).removeClass('hide');
			} else {
				$(self.returnOrgan).addClass('hide');
			}
		}
    }
    
    /**
     * 销售排名榜
     * */
    var salesRankingObj = {
		showMore : '#showMore',
		showMoreDiv : '#showMoreDiv',
		gridNoDatas : '#gridNoDatas',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		self.getRequestDatas();
    	},
    	getRequestDatas: function(){
    		var self = this;
    		$('.rank-div').remove();
    		$(self.gridNoDatas).addClass('hide');
    		$(self.showMore).addClass('hide');
    		var param = {
    			organId : self.organId,
    			date : defaultDatas.salesRankingDate
    		};
    		$.post(urls.querySalesRankingUrl, param, function(data){
    			if(data)
    				self.loadGridDatas(data);
    		});
    	},
    	loadGridDatas: function(data){
    		var self = this;
    		var div = '';
    		$.each(data.list, function(index, object){
    			var teamName =  (object.teamName == null || object.teamName == '') ? ' ' : object.teamName;
    			div += '<div class="right-bottom rank-div">'
    				+ '<div class="col-sm-4 left padding0">'
    				+ '<div class="index-content left content-div">' + (index + 1)+ '</div>'
    				+ '<div class="center-content-1 left content-div">'
    				+ '<div class="center-first">' + object.userName + '</div>'
    				+ '</div></div>'
    				+ '<div class="col-sm-4 left content-div center-content-2">'
    				+ '<span class="last-first">' + Tc.formatFloat(object.sumNum) + '</span>'
    				+ '<span class="last-second">万元</span>'
    				+ '</div>'
    				+ '<div class="col-sm-4 left content-div center-content-2">'
    				+ '<div class="center-second">' + teamName + '</div>'
    				+ '</div></div>';
    		});
    		$(self.showMoreDiv).before(div);
    		if(data.count > 3){
    			self.showOrHideMoreBtn(true);
    		} else if(data.count > 0){
    			self.showOrHideMoreBtn(false);
    		} else {
    			$(self.gridNoDatas).removeClass('hide');
    		}
    		self.showMoreBtnClick();
    	},
    	showMoreBtnClick: function(){
    		var self = this;
    		$(self.showMore).unbind('click').bind('click', function(){
    			var arr = new Array();
    			var rankingYearMonth = defaultDatas.salesRankingDate;
    			arr.push(rankingYearMonth);
    			defaultDatas.personDetailDate = arr;
    			personStandardRateObj.yearMonth = parseInt(rankingYearMonth.substr(0, 4) + rankingYearMonth.substr(5, 2));
    			var $this = $(".leftListDiv");
    			var _page = "page-two";
                $(".rightBodyPage").hide();
                $("#" + _page).show();
                $(".leftListDiv").removeClass("selectList");
                $("#selectListTwo").addClass("selectList");
                changeData(_page);
                
                $("#page-two .leftSetUpBtn").find(".leftSetUpBtnDiv").removeClass("leftSetUpBtnSelect");
                $("#personSlected").addClass("leftSetUpBtnSelect");
                var nav = $(".nav-btn");
                if (nav.attr("view") == "team") {
                	nav.attr("view", "person");
            	}
                nav.find(".team-view").hide();
            	nav.find(".person-view").show();
            	var top = $('body').height() + 20;
            	$("html,body").animate({"scrollTop":top})
    		});
    	},
    	showOrHideMoreBtn: function(flag){
    		var self = this;
    		if(flag){
    			$(self.showMore).removeClass('hide');
    		} else {
    			$(self.showMore).addClass('hide');
    		}
    	}
    }
    
    /**
     * page-four
     * 人员变动情况
     * */
    var personChangeOption = {
		tooltip : {
			show : false,
			trigger : 'axis',
			axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
	        },
			formatter: function(data) {
				if(undefined != data.seriesName) {
					if(data.seriesName == '离职/调出' || data.seriesName == '入职/调入') {
						var param = {
							flag: data.value[0],
							index: data.seriesIndex,
							organId: reqOrgId,
							salesType: personChangeObj.salesType
						};
						var arr = [];
						$.ajax({
							url: urls.getChangeInfoUrl,
							type: "post",
							async: false,
							data: param,
							success: function(result) {
								var title = personChangeObj.text, unit = '万元';
								if(title == '销售量'){
									unit = '件'
								}
								arr.push('<table>');
								arr.push('	<tr class="width150 lineHeight25 tooltip-title" style="border-bottom:solid 1px">');
								arr.push('		<td class="width45">时间</td>');
								arr.push('		<td class="width45">总' + title + '</td>');
								arr.push('		<td class="width70">异动情况</td>');
								arr.push('	</tr>');
								$.each(result, function(i, o) {
									var val = Tc.formatNumber(Tc.formatFloat(o.sales));
									if(title == '销售量'){
										val = Tc.formatFloat(o.sales);
									}
									arr.push('	<tr class="width150 lineHeight25">');
									arr.push('		<td class="width45">' + o.changeDate + '&nbsp;&nbsp;</td>');
									arr.push('		<td class="width45">' + val + unit + '&nbsp;&nbsp;</td>');
									arr.push('		<td class="width70">' + o.changeInfo + '</td>');
									arr.push('	</tr>');
								});
								arr.push('</table>');
							},
							error: function() {}
						});
						return arr.join('');
					}
				} else {
					var d = data[0];
					return d.name + '<br>' + d.seriesName + ' : ' + d.value;
				}
			}
		},
		legend : {
			data : [ '组织销售额', '离职/调出', '入职/调入' ],
			y : 'bottom'
		},
		grid : {
			x: 85,
            y: 25,
            x2: 30,
            y2: 60,
			borderWidth : 0
		},
		xAxis : [ {
			name : '时间',
			nameTextStyle: {
            	fontFamily: 'Microsoft YaHei',
                color: '#444444'
            },
			type : 'category',
			boundaryGap: false,
			axisLine : {
				lineStyle : {
					type : 'solid',
					color : '#666666',
					width : 1
				}
			},
			axisTick : {
				show : true
			},
			axisLabel : {
				interval : 0
			},
			splitLine : {
				show : false
			}
		},
		{
			type : 'value',
			axisLine : {
				show: false
			},
			axisTick : {
				show : false
			},
			axisLabel : {
				show: false
			},
			splitLine : {
				show : false
			}
		}
		],
		yAxis : [ {
			type : 'value',
			axisLine : {
				lineStyle : {
					type : 'solid',
					color : '#666666',
					width : 1
				}
			},
			axisLabel : {
				interval : 0
			}
		} ],
		series : [ {
			name : '组织销售额',
			type : 'line',
			showAllSymbol : true,
			symbolSize : 2,
			clickable : false,
			symbol : 'none',
			itemStyle : {
				normal : {
					color : '#0099cc',
					label : {
						show : false
					},
					lineStyle : {
						type : 'solid'
					}
				}
			},
			data : []
		},
		{
			name : '离职/调出',
			type : 'scatter',
			symbol : 'circle',
			showAllSymbol : true,
			xAxisIndex: 1,
			clickable : false,
			smooth: true,
			itemStyle : {
				normal : {
					color : '#ff0000',
					label : {
						show : false
					}
				}
			},
			data : []
		},
		{
			name : '入职/调入',
			type : 'scatter',
			symbol : 'circle',
			showAllSymbol : true,
			xAxisIndex: 1,
			clickable : false,
			smooth: true,
			itemStyle : {
				normal : {
					color : '#00ff00',
					label : {
						show : false
					}
				}
			},
			data : []
		}
		]
	};
    /**
     * page-four
     * 人员变动情况
     * */
    var personChangeObj = {
    	chartId: 'personChangeChart',
		chart: initEChart("personChangeChart"),
    	option: personChangeOption,
    	organId: null,
    	salesType: 0,
    	text: '销售额',
    	init : function(organId){
    		var self = this;
    		self.organId = organId;
    		clearEChart(self.chart);
			self.getRequestDatas(organId);
    		self.personChangeSelect();
    	},
    	personChangeSelect: function() {
			var self = this;
			$("#personChangeSelect").change(function() {
				var salesType = $("#personChangeSelect option:selected").val();
				self.text = $("#personChangeSelect option:selected").text();
				if(self.text == '销售量'){
					$('#personChangeUnit').html('(件)');
				} else {
					$('#personChangeUnit').html('(万元)');
				}
				self.salesType = salesType;
				self.getRequestDatas(self.organId, salesType);
			});
		},
    	getRequestDatas: function(organId, salesType){
    		var self = this;
    		loadingChart(self.chartId);
    		if(undefined == organId)
    			organId = self.organId;
    		if(undefined == salesType)
    			salesType = self.salesType;
    		var param = {organId: organId, salesType: salesType};
    		$.ajax({
    			url : urls.queryChangeByMonthUrl,
    			type : 'post',
    			data: param,
    			success : function(data){
    				self.getOption(data);
    			},
    			error : function(){}
    		});
    	},
    	getOption: function(data){
			var self = this;
			if(data.list != undefined && data.list.length > 1){
				hideChart(self.chartId);
				var xAxisData = [];
				var salesData = [];
				var changeData1 = [];
				var changeData2 = [];
				var arr = [];
				$.each(data.list, function(i, o) {
					var ym = o.yearMonth+'';
					if(i == 0)
						xAxisData.push('');
					else
						xAxisData.push(ym.substr(2, 2) + '/' + ym.substr(4, 2));
					salesData.push({name: o.yearMonth, value: o.sales});
					arr.push(o.sales);
				});
				$.each(data.list1, function(i, o) {
					changeData1.push([o.conNum, o.sales > 0 ? o.sales : '-']);
				});
				$.each(data.list2, function(i, o) {
					changeData2.push([o.conNum, o.sales > 0 ? o.sales : '-']);
				});
				if(!_.isNull(self.text)){
					self.option.legend.data[0] = '组织' + self.text;
					self.option.series[0].name = '组织' + self.text;
				}
				self.option.xAxis[0].data = xAxisData;
				self.option.series[0].data = salesData;
				self.option.series[1].data = changeData2;
				self.option.series[2].data = changeData1;
				setGridX(arr, self.option.grid);
				self.setOption();
			}else{
				hideChart(self.chartId, true);
			}
		},
		setOption: function(){
			var self = this;
			self.chart.setOption(self.option);
			self.chart.on(ecConfig.EVENT.HOVER, self.hoverFun);
			self.chart.on(ecConfig.EVENT.MOUSEOUT, self.mouseoutFun);
			self.myMouseFun();
			self.resizeChart();
		},
		resizeChart: function() {
			this.chart.resize();
		},
		hoverFun: function(){
			personChangeObj.option.tooltip.show = true;
			personChangeObj.chart.setOption(personChangeObj.option, true);
		},
		mouseoutFun: function(){
			personChangeObj.option.tooltip.show = false;
			personChangeObj.chart.setOption(personChangeObj.option, true);
		},
		myMouseFun: function(){
			var zRender = personChangeObj.chart.getZrender();
		    zRender.on('mousemove', function (param) {
		    	personChangeObj.chart.component.tooltip.hideTip();
		    });
		    zRender.on('mouseout', function (param) {
		    	personChangeObj.chart.component.tooltip.hideTip();
		    });
		}
    }
    
    /**
     * page-four
     * 人员/销售影响
     * */
    var personSalesOption = {
	    tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'none'
			},
			formatter : function(param){
	        	var div = '<div class="tooltip-title width140 bottomBorder">' + param[0].name + '</div>';
	        	var name, unit = '万元';
	        	$.each(param, function(ind, obj){
	        		var val;
	        		if(ind == 0){
	        			if(obj.value != '-'){
	        				val = Tc.formatNumber(Tc.formatFloat(obj.value));
	        				if(obj.seriesName == '组织销售额'){
	        					name = '总额';
	        				} else if(obj.seriesName == '组织销售量'){
	        					name = '总量';
	        					unit = '件';
	        					val = Tc.formatFloat(obj.value);
	        				} else {
	        					name = '总利润';
	        				}
	        				div += '<div class="tooltip-content"><div class="tooltip-content-child width45">' 
	        					+ name
	        					+ '</div><div class="tooltip-content-child2"> : ' + val + unit +'</div></div><br/>';
	        			}	        			
	        		} else {
	        			if(obj.value != '-'){
	        				val = Tc.formatNumber(Tc.formatFloat(obj.value));
	        				if(unit == '件'){
	        					val = Tc.formatFloat(obj.value);
	        				}
	        				div += '<div class="tooltip-content"><div class="tooltip-content-child width45">' 
	        					+ obj.seriesName
	        					+ '</div><div class="tooltip-content-child2"> : ' + val + unit +'</div></div><br/>';
	        			}
	        		}
	        	});
            	return div;
            }
		},
		legend : {
			x : 'center',
			y : 'bottom',
			data : []
		},
		grid : {
			x: 55,
            y: 25,
            x2: 35,
            y2: 90,
			borderWidth : 0
		},
		dataZoom : {
			show : true,
			start : 100,
			end : 70,
			realtime : true,
			height : 15,
			y: 455,
			showDetail : false,
			zoomLock : true
		},
		calculable : false,
		xAxis : [ {
			name : '时间',
			nameTextStyle: {
            	fontFamily: 'Microsoft YaHei',
                color: '#444444'
            },
			type : 'category',
			boundaryGap : true,
			data : [],
			axisLine : {
				lineStyle : {
					type : 'solid',
					color : '#666666',
					width : 1
				}
			},
			axisTick : {
				show : true,
				interval:30
			},
			axisLabel : {
				interval : 30,
				formatter : function(v) {
					return v.substr(2, 2) + '/' + v.substr(5, 2);
				}
			},
			splitLine : {
				show : false
			}
		} ],
		yAxis : [ {
			type : 'value',
			axisLine : {
				lineStyle : {
					type : 'solid',
					color : '#666666',
					width : 1
				}
			},
			axisTick : {
				show : false
			},
			splitLine : {
				show : false
			}
		} ],
		series : []
	};
    /**
     * page-four
     * 人员/销售影响
     * */
    var personSalesObj = {
    	chartId: 'personSalesChart',
		chart: initEChart("personSalesChart"),
		option: personSalesOption,
		organId: null,
		salesType: 0,
		empIds: [],
		data: null,
		perData: null,
		text: null,
		init : function(organId){
			var self = this;
			self.organId = organId;
			self.getRequestDatas(organId);
			self.organChangeSelect();
		},
		organChangeSelect: function() {
			var self = this;
			$("#organChangeSelect").change(function() {
				var salesType = $("#organChangeSelect option:selected").val();
				self.text = $("#organChangeSelect option:selected").text();
				if(self.text == '销售量'){
					$('#organChangeUnit').html('(件)');
				} else {
					$('#organChangeUnit').html('(万元)');
				}
				clearEChart(self.chart);
				self.salesType = salesType;
				self.getRequestDatas(self.organId, salesType);
				self.requestPersonSales();
			});
		},
		getRequestDatas: function(organId, salesType){
			var self = this;
			clearEChart(self.chart);
			loadingChart(self.chartId);
			if(undefined == organId)
				organId = self.organId;
			if(undefined == salesType) 
				salesType = self.salesType;
			var param = {organId: organId, salesType: salesType}
			$.ajax({
				url : urls.querySalesByOrganUrl,
				type : 'post',
				data : param,
				success : function(data){
					self.data = data;
					self.getOption(data);
				},
				error : function(){}
			});
		},
		initData: function() {
			var self = this;
			clearEChart(self.chart);
			loadingChart(self.chartId);
			self.requestPersonSales();
		},
		requestPersonSales: function() {
			var self = this;
			if(undefined != self.empIds && self.empIds.length > 0) {
				var empIds = [], changeTypes = [];
				var len = self.empIds.length;
				$.each(self.empIds, function(i, o) {
					if(len - 1 == i){
						empIds += o.empId;
						changeTypes += o.changeType;
					} else {
						empIds += o.empId + ',';
						changeTypes += o.changeType + ',';
					}
				});
				var param = {
					organId: self.organId, 
					salesType: self.salesType, 
					empIds: empIds, 
					changeTypes: changeTypes
				};
				$.ajax({
					url: urls.getSalesByEmpidUrl,
					type : 'post',
					data : param,
					success : function(data){
						self.perData = data;
						self.getOption(self.data, data);
					},
					error : function(){}
				});
			} else {
				self.getOption(self.data);
			}
		},
		getOption: function(data, personData){
			var self = this;
			var xAxisData = [];
			var legendData = [];
			var seriesData = [];
			if(data != undefined && data.length > 1){
				hideChart(self.chartId);
				var salesData = [];
				var arr = [];
				$.each(data, function(i, o) {
					salesData.push(Tc.formatFloat(o.sales));
					xAxisData.push(o.date);
					arr.push(Tc.formatFloat(o.sales));
				});
				var salesObj = {
					name : '组织销售额',
					type : 'line',
					data : salesData,
					symbol : 'none',
					smooth:true,
					itemStyle : {
						normal : {
							color : defaultColor[0]
						}
					}
				};
				legendData.push('组织销售额');
				seriesData.push(salesObj);
				if(personData && personData.length > 0){
					$.each(self.empIds, function(i, o) {
						var perData = [];
						$.each(personData, function(j, obj) {
							if(o.empId == obj[j].empId) {
								$.each(xAxisData, function(xInd, xObj){
									var ff = true;
									$.each(obj, function(k, d) {
										if(xObj == d.date){
											perData.push(Tc.formatFloat(d.sales));
											ff = false;
											return true;
										}
									});
									if(ff){
										perData.push('-');
									}
								})
							}
						});
						var obj = {
								name : o.empName,
								type : 'line',
								data : perData,
								symbol : 'none',
								smooth:true,
								itemStyle : {
									normal : {
										color : defaultColor[i + 1]
									}
								}
						};
						seriesData.push(obj);
					});
				}
				self.option.xAxis[0].data = xAxisData;
				self.option.legend.data = legendData;
				self.option.series = seriesData;
				if(!_.isNull(self.text)){
					self.option.legend.data[0] = '组织' + self.text;
					self.option.series[0].name = '组织' + self.text;
				}
				setGridX(arr, self.option.grid);
				self.setOption();
			}else{
				hideChart(self.chartId, true);
			}
		},
		setOption: function(){
			var self = this;
			self.chart.setOption(self.option);
			self.chart.resize();
		},
		removeComparison:function(empId){
            var self=this;
            var arr=[];
            $.each(self.empIds, function(i, item){
                if(item.empId != empId){
                    arr.push(item);
                }
            });
            self.empIds=arr;
            clearEChart(self.chart);
            self.requestPersonSales();
        },
        resizeChart: function() {
			this.chart.resize();
		}		
    }
    
    /**
     * 人员/销售影响列表
     * */
    var personSalesEffectGridObj = {
    	gridTabClass : '.four-right-grid',
    	gridId : '#perSalesGrid',
    	flag : false,
    	num: 0,
    	empArr: [],
    	organId: null,
    	init : function(organId){
    		var self = this;
    		self.organId = organId;
    		$("#userName").val('');
    		self.getParamFun(organId);
    	},
    	getParamFun: function(organId){
    		var self = this;
    		var param = {
    			organId: organId,
    			userName: null
    		};
    		if(self.flag){
    			self.reloadGridFun(param);
    			return;
    		}
    		self.getRequestDatasFun(param);
    		self.flag = true;
    	},
    	getRequestDatasFun: function(param){
    		var self = this;
    		$(self.gridId).jqGrid({
    			url : urls.queryEmpChangeInfoUrl + '?r=' + Math.round(),
    			postData : param,
    			mtype: 'POST',
		        datatype: "json",
		        height: 362,
		        rowHeight: 36,
		        styleUI: 'Bootstrap',
		        colNames:['姓名','异动时间','异动情况','操作'],
		        colModel:[
					{name:'userName',sortable:false,width: 80, align: 'center'},
					{name:'date',sortable:false,width: 80, align: 'center'},
					{name:'changeInfo',sortable:false,width: 80, align: 'center'},
					{name:'operate',sortable:false,width: 80, align: 'center',
						formatter: function(value, options, row) {
							var title = '加入对比';
							if(personSalesEffectGridObj.empArr.length > 0){
								$.each(personSalesEffectGridObj.empArr, function(ind, obj){
									if(obj == row.empId){
										title = '取消对比';
									}
								});
							}
							return "<a href='javascript:void(0)' class='operate_col " 
								+ row.empId+ "' value='" + (row.empId + "," + row.userName + "," + row.changeType) + "'>" 
								+ title + "</a>";
						}
					}
				],
//				page: 1,
				rowNum: 10,
		        rowList: [10, 20, 30],
		        pager : "#perSalesGridPager",
		        hoverrows : false,
		        viewrecords: true,
		        autowidth: true,
		        altRows: true,
		        loadComplete: function (xhr) {
	            	$('.operate_col').unbind().bind('click',function(){
		            	var $this = $(this);
		            	var emp = $this.attr('value');
		            	var empId = emp.split(",")[0];
		            	var userName = emp.split(",")[1];
		            	var changeType = emp.split(",")[2];
		            	var temp = $this.html();
		            	if('取消对比' == temp) {
		            		var index = self.empArr.indexOf(empId);
		        			self.empArr.splice(index, 1);
		            		personSalesObj.removeComparison(empId);
		            		$this.html('加入对比');
		            		var selector = $(".four-title-group").find("#img" + empId).parent();
	            			$(selector).remove();
		        			self.num --;
		            	} else {
		            		if(self.empArr.indexOf(empId) >= 0) return;
			            	if(self.num < 5) {
			            		self.empArr.push(empId)
			            		var imgPath = webRoot + '/assets/img/index/big-close.png';
			            		var html = '<div class="left four-title board-color">' + userName 
			            			+ '<img class="four-title-close" src="' + imgPath + '" id="img' + empId + '"></div>';
			            		$(".four-title-group").append(html);
			            		self.chartTitleClickFun(empId);
			            		$this.html('取消对比');
			            		personSalesObj.empIds.push({empId: empId, empName: userName, changeType: changeType});
			            		personSalesObj.initData();
				            	self.num ++;
			            	}
		            	}
		            });
	            	personSalesEffectGridObj.resizeGrid();
		        }
		    });
    		self.checkText();
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$(self.gridId).clearGridData().setGridParam({
                postData: param
            }).trigger("reloadGrid");
    	},
    	checkText: function() {
    		var self = this;
    		$("#userNameBtn").unbind().bind('click', function() {
    			var userName = $("#userName").val();
    			var param = {organId: self.organId, userName: userName};
    			self.reloadGridFun(param);
    		});
    	},
		chartTitleClickFun: function(empId) {
    		var self = this;
    		var selector = $(".four-title-group").find("#img" + empId);
    		$(selector).unbind().bind('click', function(){
    			$(this).parent().remove();
    			var index = self.empArr.indexOf(empId);
    			self.empArr.splice(index, 1);
        		personSalesObj.removeComparison(empId);
        		$('.' + empId).html('加入对比');
    			self.num --;
    		});
    	},
    	resizeGrid: function() {
    		var self = this;
    		$(self.gridId).setGridWidth($(self.gridTabClass).width());
    	}
    }

	/**
	 * 维度分析-销售趋势chart
	 */
	var salesTrendOption = {	
		legend : {
			data : [ ],
			y : 'bottom'
		},
		tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'line',        // 默认为直线，可选为：'line' | 'shadow'
	            lineStyle : {
	            	type: 'dashed',
	            	width: 1,
	            	color: '#999999'
	            }
	        },
	        formatter : function(param){
	        	var div = '<div class="tooltip-title width150"> </div>';
	        	if(param[0].name.indexOf("全年") == -1){
	        		div = '<div class="tooltip-title width150">' + param[0].name + '</div>';
	        	}
	        	var unit = '万元';
	        	if(param[4].seriesName.indexOf('销售量') != -1){
	        		unit = '件';
	        	}
	        	if(param[2].value != '-'){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
	        			+ param[2].seriesName
	        			+ '</div><div class="tooltip-content-child2"> : ' + param[2].value + unit + '</div></div><br/>';
	        	}
	        	if(param[1].value != '-' && defaultDatas.salesProduct == ''){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
	        			+ param[1].seriesName
	        			+ '</div><div class="tooltip-content-child2"> : ' + param[1].value + unit + '</div></div><br/>';
	        	}
	        	if(param[0].value != '-' && defaultDatas.salesProduct == ''){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
            	 	+ param[0].seriesName
            		+ '</div><div class="tooltip-content-child2"> : ' + param[0].value + '%</div></div><br/>';
	        	}
	        	if(param[3].value != '-'){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
	        			+ param[3].seriesName
	        			+ '</div><div class="tooltip-content-child2"> : ' + param[3].value + unit + '</div></div><br/>';
	        	}
	        	if(param[4].value != '-'){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
	        			+ param[4].seriesName
	        			+ '</div><div class="tooltip-content-child2"> : ' + param[4].value + unit + '</div></div><br/>';
	        	}
	        	if(param[5].value != '-'){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
	        			+ param[5].seriesName
	        			+ '</div><div class="tooltip-content-child2"> : ' + param[5].value + '%</div></div><br/>';
	        	}
	        	if(param[6].value != '-'){
	        		div += '<div class="tooltip-content"><div class="tooltip-content-child width70">' 
	        			+ param[6].seriesName
	        			+ '</div><div class="tooltip-content-child2"> : ' + param[6].value + '%</div></div><br/>';
	        	}
            	return div;
            }
	    },
		calculable : false,
		grid : {
			x: 75,
            y: 35,
            x2: 35,
			borderWidth : 0
		},
		color : [ '#0099cc', '#5cb7f1', '#6c8cc2', '#009326', '#FF7F00' ],
		xAxis : [{
			type : 'category',
			splitLine : false,
			axisLine : {
				show : true,
				onZero : false,
				lineStyle : {
					color : '#666666',
					width : 1
				}
			},
			axisTick : {
				show : false,
			},
			axisLabel : {
				show : true,
				/*rotate: 30,*/
				itemStyle : {
					color : '#666666'
				}
			},
			data : []
		}],
		yAxis : [{
			type : 'value',
			name: '(万元)',
			nameTextStyle: {
            	fontFamily: 'Microsoft YaHei',
                color: '#444444'
            },
			splitNumber: 5,
			axisLine : {
				show : true,
				lineStyle : {
					color : '#666666',
					width : 1
				}
			},
			axisLabel: {
                formatter: '{value}'
            }
		},
		{
			type : 'value',
			axisLine : false,
			splitNumber: 5,
			axisLabel: {
                formatter: '{value}%'
            }
		}],
		series : [ {
			type : 'bar',
			stack: '额度',
			clickable : false,
			barMaxWidth: 30,
			itemStyle : {
				normal : {
					color : '#0099cc',
					label : {
						show : true,
						textStyle : {
							color:'#666666'
						}
					}
				},
				emphasis : {
					label : {
						show : true,
						textStyle : {
							color:'#666666'
						}
					}
				}
			},
			data : []
		},{
			type : 'bar',
			stack: '额度',
			clickable : false,
			barMaxWidth: 30,
			itemStyle : {
				normal : {
					color : '#5cb7f1',
					label : {
						show : true,
						textStyle : {
							color:'#666666'
						}
					}
				},
				emphasis : {
					label : {
						show : true,
						textStyle : {
							color:'#666666'
						}
					}
				}
			},
			data : []
		},
		{
			type : 'bar',
			stack: '额度',
			clickable : false,
			barMaxWidth: 30,
			itemStyle : {
				normal : {
					color : '#6c8cc2',
					label : {
						show : true,
						textStyle : {
							color:'#666666'
						}
					}
				},
				emphasis : {
					label : {
						show : true,
						textStyle : {
							color:'#666666'
						}
					}
				}
			},
			data : []
		},
		{
			type : 'bar',
			stack: '额度',
			clickable : false,
			itemStyle : {
				normal:{
                    barBorderColor:'rgba(0,0,0,0)',
                    color:'rgba(0,0,0,0)'
                },
                emphasis:{
                    barBorderColor:'rgba(0,0,0,0)',
                    color:'rgba(0,0,0,0)'
                }
			},
			data : []
		},
		{
			type : 'bar',
			stack: '额度',
			clickable : false,
			itemStyle : {
				normal:{
                    barBorderColor:'rgba(0,0,0,0)',
                    color:'rgba(0,0,0,0)'
                },
                emphasis:{
                    barBorderColor:'rgba(0,0,0,0)',
                    color:'rgba(0,0,0,0)'
                }
			},
			data : []
		},
		{
			type : 'line',
			clickable : false,
			yAxisIndex: 1,
			symbol : 'circle',
			itemStyle : {
				normal : {
					color : '#f5b147',
					label : {
						show : false
					}
				}
			},
			data : []
		},{
			type : 'line',
			clickable : false,
			yAxisIndex: 1,
			symbol : 'circle',
			itemStyle : {
				normal : {
					color : '#009326',
					label : {
						show : false
					}
				}
			},
			data : []
		},
		{
			type : 'scatter',
			symbol : 'none',
			data : [],
			tooltip: {
				show : false
			},
			markPoint : {
				symbolSize : 20,
				data : [ ],
				itemStyle : {
					normal : {
						color : '#32cd99',
						label : {
							show : true,
							textStyle : {
								color : '#32cd99',
							},
							formatter : function(o) {
								return o.value + '%';
							}
						}
					}
				},
			},
			itemStyle : {
				normal : {
					color : '#32cd99',
					label : {
						show : false
					}
				}
			}
		} ]
	};
	/**
	 * 维度分析-销售趋势grid
	 */
	salesTrendGridOption = {
		data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 433,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        colNames: ['时间', '销售额（万元）', '目标值（万元）', '环比变化', '同比变化', '达标率'],
        colModel: [
            { name: 'yearMonth', width: 190, sortable: false, align: 'center' },
            { name: 'sales', width: 190, sortable: false, align: 'center' },
            { name: 'target', width: 190, sortable: false, align: 'center' },
            { name: 'ratio', width: 190, sortable: false, align: 'center',
            	formatter: function(value) {
            		if(value == "-") {
            			return '-';
            		}
            		return value + "%"; 
            	}
            },
            { name: 'basis', width: 190, sortable: false, align: 'center',
            	formatter: function(value) {
            		if(value == "-") {
            			return '-';
            		}
            		return value + "%"; 
            	}
            },
            { name: 'standardRate', width: 190, sortable: false, align: 'center',
            	formatter: function(value) {
            		if(value == "") {
            			return '0%';
            		}
            		return value + "%"; 
            	}
            }
        ]
	};
	/**
	 * 维度分析-销售趋势
	 * */
	var salesTrendObj = {
		id: "salesTrendChart",
        chart: null,
        chartOption: _.clone(salesTrendOption),
        gridId: "#salesTrendGrid",
        gridObj: null,
        gridOption: _.clone(salesTrendGridOption),
        year: null,
        productId: null,
        salesType: 0,
        salesNum: 0,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            if (self.chart == null) {
            	self.chart = echarts.init(document.getElementById(self.id));
            }
            if(self.gridObj == null) {
            	self.gridObj = $(self.gridId).jqGrid(self.gridOption);
            }
            self.requestData(organId);
            self.requestProduct();
        },
        requestProduct: function() {
        	var self = this;
        	$.ajax({
    			url: urls.getSalesProductsUrl,
    			type: "post",
    			success: function(data) {
    				self.salesSelectionFun(data);
    			},
    			error: function(){}
    		});
        },
        salesSelectionFun: function(list){
    		var self = this;
    		list.reverse();
    		list.push({k: '', v: '全部产品'});
    		list.reverse();
    		var listSales = [{k: 0, v: '销售额'}, {k: 1, v: '销售量'}, {k: 2, v: '利润'}];
    		var minYear, maxYear;
    		if(defaultDatas.minDate){
    			minYear = defaultDatas.minDate.substr(0, 4);
    		}
    		if(defaultDatas.minDate){
    			maxYear = defaultDatas.maxDate.substr(0, 4);
    		}
    		$("#salesTime").selection({
    			dateType: 8,
 				dateRange:{
 					min: minYear,
 					max: maxYear
 				},
 				dateSelected: defaultDatas.selectYear,
 				dynamicRows: [{name: '产品', list: list, selected: defaultDatas.salesProduct}, {name: '销售', list: listSales, selected: defaultDatas.salesSelectType}],
 				ok:function(event, data){
 					var year = data.date[0];
 					defaultDatas.selectYear = data.date;
 					self.year = year;
 					var productId = data.dynamicRows[0];
 					defaultDatas.salesProduct = productId;
 					self.productId = productId;
 					var salesType = data.dynamicRows[1];
 					self.salesType = salesType;
 					defaultDatas.salesSelectType = salesType;
 					if(salesType == 2)
 						$("#salesTypeName").html("利润");
 					else if(salesType == 1)
 						$("#salesTypeName").html("销售量");
 					else
 						$("#salesTypeName").html("销售额");
 					self.requestData(self.organId, year, productId, salesType);
 				}
 			});
    	},
        requestData: function (organId, year, productId, salesType) {
            var self = this;
            if(undefined == year)
            	year = self.year;
            if(undefined == productId)
            	productId = self.productId;
            if(undefined == salesType)
            	salesType = self.salesType;
            var param = {organId: organId, year: year, productId: productId, salesType: salesType};
            $.ajax({
                url: urls.getSalesAndTargetUrl,
                type:"post",
                data: param,
                success: function (result) {
                	self.salesNum = result.sales;
                	if(result.sales > 0){
                		self.generateChart(result);
                		self.generateGrid(result.basisList);
                	} else {
                		if(self.chart){
                			clearEChart(self.chart);
                		}
                		hideChart(self.id, true);
                		$(self.gridId).clearGridData();
                	}
                }
            });
        },
        generateChart: function (data) {
        	var self = this;
            var dataValue = [];
            var xAxisData = [];
            xAxisData.push('全年销售额');
            xAxisData.push('全年目标');
            var sales = [];
            sales.push(Tc.formatFloat(data.sales));
    		var target = [];
    		target.push('-');
    		target.push(Tc.formatFloat(data.target));
    		var salesData = [];
    		salesData.push('-');
    		salesData.push('-');
    		var salesMonthData = [];
    		salesMonthData.push('-');
    		salesMonthData.push('-');
    		var standardRateData = [];
    		standardRateData.push('-');
    		standardRateData.push('-');
    		var standardData = [];
    		standardData.push('-');
    		standardData.push('-');
    		var ratioData = [];
    		ratioData.push('-');
    		ratioData.push('-');
    		var basisData = [];
    		basisData.push('-');
    		basisData.push('-');
    		var scatterData = [];
    		var scatterPointData = [];
    		var markNum = parseInt(sales[0]/10);
    		$.each(data.basisList, function (i, o) {
                var month = (o.yearMonth + "").substring(2, 4)+ "/" +(o.yearMonth + "").substring(4, 6);
                xAxisData.push(month);
                salesData.push(Tc.formatFloat(o.sales));
                salesMonthData.push(Tc.formatFloat(o.target));
                standardRateData.push(Tc.formatFloat(o.standardRate));
    			ratioData.push(o.ratio == '' ? '-' : Tc.formatFloat(Number(o.ratio)));
    			basisData.push(o.basis == '' ? '-' : Tc.formatFloat(Number(o.basis)));
    			var arrData = [ i + 2, o.sales];
    			var scatterPoint = {value : o.standardRate, xAxis: i + 2, yAxis: o.sales + markNum};
    			if(defaultDatas.salesProduct != ''){
    				arrData = [ i + 1, o.sales];
    				scatterPoint = {value : o.standardRate, xAxis: i + 1, yAxis: o.sales + markNum};
    			}
    			scatterData.push(arrData);
    			scatterPointData.push(scatterPoint);
    			
            });
    		var legendData = [ '全年销售额', '全年目标', '月销售额', '环比变化', '同比变化', '达标率' ];
    		var seriesArr = self.chartOption.series;
    		seriesArr[0].name = legendData[0];
    		seriesArr[1].name = legendData[1];
    		seriesArr[2].name = legendData[2];
    		seriesArr[3].name = '月目标值';
    		seriesArr[4].name = '达标率 ';
    		seriesArr[5].name = legendData[3];
    		seriesArr[6].name = legendData[4];
    		seriesArr[7].name = legendData[5];
    		var yAxisArr = self.chartOption.yAxis;
    		var title, unit = '(万元)';
    		if(self.salesType == 2){
    			title = '利润';
    		} else if(self.salesType == 1){
    			title = '销售量';
    			unit = '(件)';
    		} else {
    			title = '销售额';
    		}
    		xAxisData[0] = '全年' + title;
    		legendData[0] = '全年' + title;
    		legendData[2] = '月' + title;
    		seriesArr[0].name = '全年' + title;
    		seriesArr[2].name = '月' + title;
    		yAxisArr[0].name = unit;
    		
    		if(defaultDatas.salesProduct != ''){
    			//隐藏目标值
    			xAxisData.splice(1, 1);
    			legendData.splice(1, 1);
    			salesData.splice(1, 1);
    			salesMonthData.splice(1, 1);
    			standardRateData.splice(1, 1);
    			ratioData.splice(1, 1);
    			basisData.splice(1, 1);
    			scatterData.splice(1, 1);
    			target[1] = '-';
    			//隐藏达标率
    			legendData.splice(4, 1);
    			scatterPointData = [];
    		}
    		self.chartOption.legend.data = legendData;
    		self.chartOption.xAxis[0].data = xAxisData;
    		self.chartOption.series[0].data = sales;
    		self.chartOption.series[1].data = target;
    		self.chartOption.series[2].data = salesData;
    		self.chartOption.series[3].data = salesMonthData;
    		self.chartOption.series[4].data = standardRateData;
    		self.chartOption.series[5].data = ratioData;
    		self.chartOption.series[6].data = basisData;
    		self.chartOption.series[7].data = scatterData;
    		self.chartOption.series[7].markPoint.data = scatterPointData;
            self.chart.setOption(self.chartOption, true);
            self.resizeChart();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart) {
                self.chart.resize();
                if(self.salesNum == 0){
                	hideChart(self.id, true);
                }
            }
        },
        generateGrid: function (result) {
            var self = this;
            if(!_.isEmpty(result)) {
            	$.each(result, function(ind, obj){
            		obj.sales = Tc.formatFloat(obj.sales);
            		obj.target = Tc.formatFloat(obj.target);
            		obj.ratio = obj.ratio == '' ? '-' : Tc.formatFloat(Number(obj.ratio));
            		obj.basis = obj.basis == '' ? '-' : Tc.formatFloat(Number(obj.basis));
            		obj.standardRate = Tc.formatFloat(obj.standardRate);
            	});
            	var title, unit = '(万元)';
        		if(self.salesType == 2){
        			title = '销售利润';
        		} else if(self.salesType == 1){
        			title = '销售量';
        			unit = '(件)';
        		} else {
        			title = '销售额';
        		}
        		$(self.gridId).setLabel(1, title + unit);
        		$(self.gridId).setLabel(2, '目标值' + unit);
        		if(defaultDatas.salesProduct != ''){
        			$(self.gridId).hideCol('target');
        			$(self.gridId).hideCol('standardRate');
        		} else {
        			$(self.gridId).showCol('target');
        			$(self.gridId).showCol('standardRate');
        		}
            	$(self.gridId).clearGridData().setGridParam({
            		colNames: salesTrendGridOption.colNames,
                    data: result
                }).trigger("reloadGrid");
                self.resizeGrid();
            }
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#salesTrendArea').width());
        }
	};
	
	/**
	 * 销售明细-团体
	 * */
	var standardRateObj = {
		teamId: 'teamView_tabel',
		organId: null,
		selectOrganId: null,
		yearMonth: null,
		load: false,
		isClick: true,
		init: function(organId){
			var self = this;
			self.organId = organId;
			self.selectOrganId = organId;
			self.getRequestDatas(organId);
			self.querySubOrganization(organId);
			self.checkCondition();
			self.teamSelectionFun();
		},
		teamSelectionFun: function(){
    		var self = this;
    		 $("#teamTime").selection({
 				dateType: 7,
 				dateRange:{
 					min: defaultDatas.minDate,
 					max: defaultDatas.maxDate
 				},
 				dateSelected: defaultDatas.salesDetailDate,
 				ok:function(event, data){
 					var date = data.date[0];
 					defaultDatas.salesDetailDate = data.date;
 					yearMonth = parseInt(date.substr(0, 4) + date.substr(5, 2));
 					self.yearMonth = yearMonth;
 					self.getRequestDatas(self.organId, yearMonth);
 				}
 			});
    	},
		checkCondition: function() {
			var self = this;
			$("#teamSearch").click(function(){
				self.compareDatas();
				if(self.isClick){
					$(".condition-body").hide();
					$(".more-search-label").text('更多筛选条件');
					$(".more-search-label").removeClass('icon-panel-up').removeClass('borderStyle').addClass("icon-panel-down");
					var organId = self.selectOrganId;
					var teamStandardRate = $("#teamStandardRate option:selected").val();
					var beginSales = $("#beginSales").val();
					var endSales = $("#endSales").val();
					var beginReturnAmount = $("#beginReturnAmount").val();
					var endReturnAmount = $("#endReturnAmount").val();
					self.getRequestDatas(organId, self.yearMonth, teamStandardRate, beginSales, endSales, beginReturnAmount, endReturnAmount);
				}
			});
		},
		compareDatas: function(){
			var self = this;
			var salesVal1 = $('#beginSales').val();
			var salesVal2 = $('#endSales').val();
			var returnVal1 = $('#beginReturnAmount').val();
			var returnVal2 = $('#endReturnAmount').val();
			self.isClick = true;
			$('#endSales').removeClass('fontRed').removeAttr('data-original-title');
			$('#endReturnAmount').removeClass('fontRed').removeAttr('data-original-title');
			if(salesVal1 != '' && salesVal2 != '' && Number(salesVal2) < Number(salesVal1)){
				$('#endSales').addClass('fontRed');
				$('#endSales').attr('data-toggle', 'tooltip').attr('data-placement', 'right')
					.attr('data-original-title', '销售额填写不合理');
				self.isClick = false;
			}
			if(returnVal1 != '' && returnVal2 != '' && Number(returnVal2) < Number(returnVal1)){
				$('#endReturnAmount').addClass('fontRed');
				$('#endReturnAmount').attr('data-toggle', 'tooltip').attr('data-placement', 'right')
					.attr('data-original-title', '回款额填写不合理');
				self.isClick = false;
			}
			$('.fontRed').tooltip();
		},
		getRequestDatas: function(organId, yearMonth, teamStandardRate, beginSales, endSales, beginReturnAmount, endReturnAmount){
			var self = this;
			if(undefined == teamStandardRate) {
				teamStandardRate = null;
			}
			if(undefined == beginSales) {
				beginSales = null;
			}
			if(undefined == endSales) {
				endSales = null;
			}
			if(undefined == beginReturnAmount) {
				beginReturnAmount = null;
			}
			if(undefined == endReturnAmount) {
				endReturnAmount = null;
			}
			if(undefined == yearMonth) {
				yearMonth = self.yearMonth;
			}
			var param = {organId: organId, teamStandardRate: teamStandardRate, beginSales: beginSales, endSales: endSales, 
					beginReturnAmount: beginReturnAmount, endReturnAmount: endReturnAmount, yearMonth: yearMonth};
			self.salesGridOption.param = param;
			$('#' + self.teamId).salesGrid(self.salesGridOption);
			
		},
		salesGridOption: {
			url : urls.getTeamSalesAndTargetUrl,
			param : {},
			model:$("#team_table_model").html(),
			formatModel:function(el, obj, i){
    		   var self = standardRateObj;
    		   var result=$(el);
    		   
    		   var chart=result.find(".competency-card-chart");
    		   var model=$($("#circle_model").html());
    		   var percent = obj.sales * 100 / obj.target;
    		   if(percent >= 80) {
    			   $(result).find(".detail").css("background-color", '#00b165');
    		   } else if (percent >= 60 && percent < 80) {
    			   $(result).find(".detail").css("background-color", '#f09200');
    		   } else {
    			   $(result).find(".detail").css("background-color", '#e7191b');
    		   }
    		   
    		   var cans = model.find('.competency-level-canvas');
    		   cans.attr("id","team_tabel_chart-"+i);
    		   $(model).find('.num>span').text(percent); 
    		   $(chart).append(model);
    		   
    		   var modelTeamName = result.find(".model-teamName");
    		   $(modelTeamName).text(obj.teamName);
    		   var rankNum = result.find(".rank-num");
    		   $(rankNum).text(i + 1);
    		   var emp = result.find(".team-emp");
    		   $(emp).text(_.isNull(obj.name)? "" : obj.name);
    		   
    		   var percent1 = (obj.sales * 100 / obj.target) > 100 ? 100 : (obj.sales * 100 / obj.target);
    		   var bar1 = result.find(".teamSales .progress-bar-info");
    		   var style1 = "width:" + percent1 + "%; background-color:#0099cc;";
    		   $(bar1).removeAttr("style").attr("style", style1);
    		   
    		   var sales = result.find(".bar-text .sales");
    		   $(sales).text(Tc.formatFloat(obj.sales));
    		   var target = result.find(".bar-text .target");
    		   $(target).text(Tc.formatFloat(obj.target));
    		   var standard = result.find(".bar-text .standard");
    		   var standardRateVal = obj.standardRate == null ? "-" : 
    			   ((obj.standardRate) > 0 ? "+" + Tc.formatFloat((obj.standardRate)) : Tc.formatFloat((obj.standardRate)));
    		   $(standard).text(standardRateVal);
    		   
    		   var percent2 = obj.payment * 100 /obj.returnAmount;
    		   var bar2 = result.find(".teamSalesBack .progress-bar-info");
    		   var style2 = "width:" + percent2 + "%; background-color:#30bfa6;";
    		   $(bar2).removeAttr("style").attr("style", style2);
    		   
    		   var payment = result.find(".bar-text .payment");
    		   $(payment).text(Tc.formatFloat(obj.payment));
    		   var returnAmount = result.find(".bar-text .returnAmount");
    		   $(returnAmount).text(Tc.formatFloat(obj.returnAmount));
    		   var raturnBack = result.find(".bar-text .raturnBack");
    		   $(raturnBack).text(Tc.formatFloat(obj.returnAmount - obj.payment));
    		   
    		   return result;
    	   },
    	   loadRowComple:function(el, obj, index){
    		   var color='#00b165';
    		   var percent = obj.sales * 100 / obj.target;
    		   if(percent >= 80) {
    			   color='#00b165';
    		   } else if (percent >= 60 && percent < 80) {
    			   color='#f09200';
    		   } else {
    			   color='#e7191b';
    		   }
    		   var can=document.getElementById("team_tabel_chart-"+index);
    		   var cans = can.getContext('2d');
    		   var rate = percent;
    		   drawArc(cans,50,50,35,1.5*Math.PI,-0.5*Math.PI,'#e0e0e0',10,1);
    		   drawArc(cans,50,50,35,1.5*Math.PI,(1.5-(rate/100)*2)*Math.PI,color,10,1);
    		   drawText(cans,color,"13px 黑体",(rate).toFixed(0)+'%',50, 60); 
    	   }
		},
		querySubOrganization: function(organId) {
			var self = this;
			var organTree = $("#subOrgan").organTreeSelector({
				multiple: false,
				showSelectBtn: false,
				mouseenterColor: '#fff',
				mouseleaveColor: '#fff',
				onSelect: function (ids, texts) {
					self.selectOrganId = ids;
				}
			});
			var values = {id: organId, text: reqOrgTxt};
			if (organTree && !_.isNull(organId)) 
				organTree.organTreeSelector("value", values);
		}
	};
	/**
	 * 销售明细-个人
	 * */
	var personStandardRateObj = {
		id: 'personView_tabel',
		modalId: "#personSalesTrendModal",
		organId: null,
		yearMonth: null,
		load: false,
		isClick: true,
		init: function(organId){
			var self = this;
			self.organId = organId;
			self.getRequestDatas(organId);
			self.personText();
			self.personConditionSearch();
			self.personSelectionFun();
		},
		personSelectionFun: function(){
    		var self = this;
    		 $("#personTime").selection({
 				dateType: 7,
 				dateRange:{
 					min: defaultDatas.minDate,
 					max: defaultDatas.maxDate
 				},
 				dateSelected: defaultDatas.personDetailDate,
 				ok:function(event, data){
 					var date = data.date[0];
 					defaultDatas.personDetailDate = data.date;
 					yearMonth = parseInt(date.substr(0, 4) + date.substr(5, 2));
 					self.yearMonth = yearMonth;
 					self.getRequestDatas(self.organId, yearMonth);
 				}
 			});
    	},
		personText: function() {
			var self = this;
			$("#searchText").click(function() {
				var txt = $("#personText").val();
				self.getRequestDatas(self.organId, self.yearMonth, txt);
			});
		},
		personConditionSearch: function() {
			var self = this;
			$("#personSearch").click(function() {
				self.compareDatas();
				if(self.isClick){
					$(".condition-body").hide();
					$(".more-search-label").text('更多筛选条件');
					$(".more-search-label").removeClass('icon-panel-up').removeClass('borderStyle').addClass("icon-panel-down");
					var personStandard = $("#personStandard option:selected").val();
					var beginPersonSales = $("#beginPersonSales").val();
					var endPersonSales = $("#endPersonSales").val();
					var beginPersonAmount = $("#beginPersonAmount").val();
					var endPersonAmount = $("#endPersonAmount").val();
					self.getRequestDatas(self.organId, self.yearMonth, null, personStandard, beginPersonSales, endPersonSales, beginPersonAmount, endPersonAmount);
				}
			});
		},
		compareDatas: function(){
			var self = this;
			var salesVal1 = $('#beginPersonSales').val();
			var salesVal2 = $('#endPersonSales').val();
			var returnVal1 = $('#beginPersonAmount').val();
			var returnVal2 = $('#endPersonAmount').val();
			self.isClick = true;
			$('#endPersonSales').removeClass('fontRed').removeAttr('data-original-title');
			$('#endPersonAmount').removeClass('fontRed').removeAttr('data-original-title');
			if(salesVal1 != '' && salesVal2 != '' && Number(salesVal2) < Number(salesVal1)){
				$('#endPersonSales').addClass('fontRed');
				$('#endPersonSales').attr('data-toggle', 'tooltip').attr('data-placement', 'right')
					.attr('data-original-title', '销售额填写不合理');
				self.isClick = false;
			}
			if(returnVal1 != '' && returnVal2 != '' && Number(returnVal2) < Number(returnVal1)){
				$('#endPersonAmount').addClass('fontRed');
				$('#endPersonAmount').attr('data-toggle', 'tooltip').attr('data-placement', 'right')
					.attr('data-original-title', '回款额填写不合理');
				self.isClick = false;
			}
			$('.fontRed').tooltip();
		},
		getRequestDatas: function(organId, yearMonth, txt, personStandard, beginPersonSales, endPersonSales, beginPersonAmount, endPersonAmount){
			var self = this;
			if(undefined == yearMonth)
				yearMonth = self.yearMonth;
			if(undefined == txt) {
				txt = null;
			}
			if(undefined == personStandard) {
				personStandard = null;
			}
			if(undefined == beginPersonSales) {
				beginPersonSales = null;
			}
			if(undefined == endPersonSales) {
				endPersonSales = null;
			}
			if(undefined == beginPersonAmount) {
				beginPersonAmount = null;
			}
			if(undefined == endPersonAmount) {
				endPersonAmount = null;
			}
			var param = {organId: organId, keyName: txt, personStandard: personStandard, beginPersonSales: beginPersonSales, endPersonSales: endPersonSales, 
					beginPersonAmount: beginPersonAmount, endPersonAmount: endPersonAmount, yearMonth: yearMonth};
			
			self.salesGridOption.param = param;
			$('#' + self.id).salesGrid(self.salesGridOption);
		},
		salesGridOption: {
			url:urls.getPersonSalesAndTargetUrl,
			param:{},
			model:$("#person_table_model").html(),
			formatModel:function(el, obj, i){
    		   var self = personStandardRateObj;
    		   var result=$(el);
    		   
    		   var chart=result.find(".competency-card-chart");
    		   var model=$($("#circle_model").html());
    		   var percent=obj.standardRate;
    		   if(obj.standardRate >= 80) {
    			   $(result).find(".detail").css("background-color", '#00b165');
    		   } else if (obj.standardRate >= 60 && obj.standardRate < 80) {
    			   $(result).find(".detail").css("background-color", '#f09200');
    		   } else {
    			   $(result).find(".detail").css("background-color", '#e7191b');
    		   }
    		   
    		   var cans = model.find('.competency-level-canvas');
    		   cans.attr("id","person_tabel_chart-"+i);
    		   $(model).find('.num>span').text(percent); 
    		   $(chart).append(model);
    		   
    		   var modelUserName = result.find(".model-userName");
    		   $(modelUserName).text(obj.userName);
    		   var rankTeamName = result.find(".rank-teamName");
    		   $(rankTeamName).text(obj.teamName);
    		   var rankNum = result.find(".rank-num");
    		   $(rankNum).text(obj.rank);
    		   
    		   var tt = (obj.sales/obj.target * 100) > 100 ? 100 : (obj.sales/obj.target * 100);
    		   var teamSales = result.find(".teamSales");
    		   var bar = $(teamSales).find(".progress-bar");
    		   var w = $(bar).css("width");
    		   
    		   var percent1 = obj.sales/obj.target*100;
    		   var bar1 = result.find(".personSales .progress-bar-info");
    		   var style1 = "width:" + percent1 + "%; background-color:#0099cc;";
    		   $(bar1).removeAttr("style").attr("style", style1);
    		   
    		   var sales = result.find(".bar-text .sales");
    		   $(sales).text(Tc.formatFloat(obj.sales));
    		   var target = result.find(".bar-text .target");
    		   $(target).text(Tc.formatFloat(obj.target));
    		   var standard = result.find(".bar-text .standard");
    		   var compareVal = obj.compareVal == null ? "-" : 
    			   (Tc.formatFloat(obj.compareVal) > 0 ? "+" + Tc.formatFloat(obj.compareVal) : Tc.formatFloat(obj.compareVal));
    		   $(standard).text(compareVal);
    		   
    		   var percent2 = obj.payment/obj.returnAmount*100;
    		   var bar2 = result.find(".personSalesBack .progress-bar-info");
    		   var style2 = "width:" + percent2 + "%; background-color:#30bfa6;";
    		   $(bar2).removeAttr("style").attr("style", style2);
    		   
    		   var payment = result.find(".bar-text .payment");
    		   $(payment).text(Tc.formatFloat(obj.payment));
    		   var returnAmount = result.find(".bar-text .returnAmount");
    		   $(returnAmount).text(Tc.formatFloat(obj.returnAmount));
    		   var raturnBack = result.find(".bar-text .raturnBack");
    		   $(raturnBack).text(Tc.formatFloat(obj.returnAmount - obj.payment));
    		   var select = result.find(".sales-track");
    		   self.clickFun(select, obj.empId);
    		   return result;
    	   },
    	   loadRowComple:function(el, obj, index){
    		   var color='#00b165';
    		   if(obj.standardRate >= 80) {
    			   color='#00b165';
    		   } else if (obj.standardRate >= 60 && obj.standardRate < 80) {
    			   color='#f09200';
    		   } else {
    			   color='#e7191b';
    		   }
    		   var can=document.getElementById("person_tabel_chart-"+index);
    		   var cans = can.getContext('2d');
    		   var rate = obj.standardRate;
    		   drawArc(cans,50,50,35,1.5*Math.PI,-0.5*Math.PI,'#e0e0e0',10,1);
    		   drawArc(cans,50,50,35,1.5*Math.PI,(1.5-(rate/100)*2)*Math.PI,color,10,1);
    		   drawText(cans,color,"13px 黑体",(rate).toFixed(0)+'%',50, 60); 
    	   }
		},
		clickFun: function(selector, empId) {
			var self = this;
			$(selector).unbind('click').bind('click', function(){
    			$(self.modalId).modal('show');
    			$(self.modalId).on('shown.bs.modal', function() {
    				abilityTrackObj.init(empId);
    				salesBankingTrackObj.organId = personStandardRateObj.organId;
    				salesBankingTrackObj.init(empId);
				});
    			$(self.modalId).on('hidden.bs.modal', function() {
    				$(self.modalId).off();
    			});
			});
		}
	};
	/**
	 * 销售趋势-弹窗
	 * */
	var salesTrendLineOption = {
        grid: {
            x: 75,
            y: 25,
            x2: 60,
            y2: 65,
            borderWidth: 0
        },
        xAxis: [
            {
                type: "category",
                name: '时间',
                splitLine: false,
                axisLine: {
                    lineStyle: {
                        color: '#666666',
                        width: 1
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#666666',
                        fontSize: 12
                    }
                },
                axisTick: false,
                boundaryGap: false,
                data: []
            }
        ],
        yAxis: [
            {
                type: "value",
                nameTextStyle: {
					color: '#666666',
					margin: '100px'
				},
				axisLine: {
					lineStyle: {
						color: '#666666',
						width: 1
					}
				},
				splitLine: {
                    show: true,
                    lineStyle : {
                    	color : '#e4e4e4'
                    }
                },
                axisLabel : {
					interval : 0
				}
            }
        ],
        series: [
            {
                type: "line",
                smooth: false,  //曲线true 折线false
                itemStyle: {
                    normal: {
                    	color : '#019AEF',
                        label: {
                            show: false,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                },
                clickable: false,
                data: []
            }
        ]
    };
	/**
	 * 销售趋势-能力轨迹-弹窗
	 * */
	var abilityTrackObj = {
		id: "abilityTrack",
        chart: null,
        option: _.clone(salesTrendLineOption),
		init: function(empId) {
			var self = this;
			if (self.chart == null) {
				self.chart = initEChart(self.id);
            }
			self.request(empId);
		},
		request: function (empId) {
            var self = this;
            if(self.chart != null){
            	clearEChart(self.chart);
            }
            loadingChart(self.id);
            var param = {empId: empId};
            $.ajax({
                url: urls.queryAbilityByEmpidUrl,
                type:"post",
                data: param,
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    }
                }
            });
        },
        render: function (result) {
        	var self = this;
        	if(result.list.length > 0){
        		hideChart(self.id);
	            var dataValue = [];
	            var xAxisData = [];
	            $.each(result.list, function (i, o) {
	                dataValue.push(o.conNum);
	                var half = "";
	                var year = (o.yearMonth+"").substr(0, 4);
	                if(o.yearMonth%100 == 6) half = year + "年上半年";
	                else half = year + "年下半年";
	                xAxisData.push(half);
	            });
	            clearEChart(self.chart);
	            self.option.xAxis[0].data = xAxisData;
	            self.option.yAxis[0].splitNumber = result.yVal.length;
	            self.option.yAxis[0].axisLabel.formatter = function(value) {
					for(var i=0;i<result.yVal.length;i++){
						if(value == result.yVal[i]){
							return result.yData[i];
						}
					}
				}
	            self.option.series[0].data = dataValue;
	            self.chart.setOption(self.option);
	            self.resizeChart();
        	} else {
        		hideChart(self.id, true);
        	}
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
	};
	/**
	 * 销售趋势-排名轨迹-弹窗
	 * */
	var salesBankingTrackObj = {
		id: "salesBankingTrack",
        chart: null,
        option: _.clone(salesTrendLineOption),
        organId : null,
		init: function(empId) {
			var self = this;
			if (self.chart == null) {
				self.chart = initEChart(self.id);
            }
			self.request(empId);
		},
		request: function (empId) {
            var self = this;
            if(self.chart != null){
            	clearEChart(self.chart);
            }
            loadingChart(self.id);
            var param = {
            	organId : self.organId,
        		empId: empId
            }
            $.ajax({
                url: urls.queryRankByEmpidUrl,
                type:"post",
                data: param,
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    }
                }
            });
        },
        render: function (result) {
        	var self = this;
        	if(result.list.length > 0){
        		hideChart(self.id);
        		var dataValue = [];
        		var xAxisData = [];
        		$.each(result.list, function (i, o) {
        			dataValue.push(o.conNum);
        			var year = (o.yearMonth+"").substr(0, 4);
        			var month = o.yearMonth%100;
        			var yearMonth = year + "年" + month + "月";
        			xAxisData.push(yearMonth);
        		});
        		self.option.xAxis[0].data = xAxisData;
        		self.option.yAxis[0].splitNumber = result.yVal.length;
        		self.option.yAxis[0].axisLabel.formatter = function(value) {
        			for(var i=0;i<result.yVal.length;i++){
        				if(value == result.yVal[i]){
        					return result.yData[i];
        				}
        			}
        		}
        		self.option.series[0].data = dataValue;
        		self.chart.setOption(self.option);
        		self.resizeChart();
        	} else {
        		hideChart(self.id, true);
        	}
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
	};
	
	/**
	 * 销售排名地图-时间
	 * */
	var salesMapsTimeObj = {
		teamViewTimeId : '#teamViewTimeBox',
		teamPKTimeId : '#teamPKTimeBox',
		init : function(organId){
			var self = this;
			this.organId = organId;
			self.teamViewFun();
			self.teamPKFun();
		},
		teamViewFun: function(){
			var self = this;
			$(self.teamViewTimeId).selection({
				dateType: 7,
				dateRange:{
					min: defaultDatas.minDate,
					max: defaultDatas.maxDate
				},
				dateSelected: defaultDatas.viewSelectDate,
				ok:function(event, data){
					defaultDatas.viewSelectDate = data.date;
					defaultDatas.teamViewDate = defaultDatas.viewSelectDate.join('');
					salesMapsTeamViewChartObj.init(self.organId);
					salesMapsViewGridObj.init(self.organId);
				}
			});
		},
		teamPKFun: function(){
			var self = this;
			$(self.teamPKTimeId).selection({
				dateType: 7,
				dateRange:{
					min: defaultDatas.minDate,
					max: defaultDatas.maxDate
				},
				dateSelected: defaultDatas.pkSelectDate,
				ok:function(event, data){
					defaultDatas.pkSelectDate = data.date;
					defaultDatas.teamPKDate = defaultDatas.pkSelectDate.join('');
					if(defaultDatas.pkFlag == 0){
						startPKObj.teamOrganPKRequest();
					} else {
						startPKObj.teamCustomPKRequest();
					}
				}
			});
		}
	}
	/**
	 * 销售排名地图-全屏操作
	 * */
	var salesMapsIsFullShowObj = {
		showFullTeamView : '#showFullTeamView',
		showFullTeamPK : '#showFullTeamPK',
		fullTeamViewTitle : '#fullTeamViewTitle',
		fullTeamPKTitle : '#fullTeamPKTitle',
		fullTeamView : '#fullTeamView',
		fullTeamPK : '#fullTeamPK',
		fullCloseId : '#fullCloseBtn',
		fullMapBody : '#fullMapBody',
		salesBoard : '#salesBoard',
		init : function(organId){
			var self = this;
			this.organId = organId;
			self.teamViewFullViewFun();
			self.teamPKFullViewFun();
		},
		teamViewFullViewFun : function(){
			var self = this;
			$(self.showFullTeamView).unbind('click').bind('click', function(){
				win.doFullScreen(function (e) {
					self.changeTeamViewTitleShow();
		            self.changeFullViewShow();
		            
		            var wid = $(self.fullTeamView).parent().width() - 30;
		            var hei = $(window).height() - 50;
	            	$(self.fullTeamView).removeClass('hide').siblings().addClass('hide');
	            	$(self.fullTeamView).width(wid);
	            	salesMapsTeamViewChartObj.fullInit(self.organId);
	            	searchObj.isFirst = true;
	                searchObj.init(true);
		        });
			});
			self.closeFullViewFun();
		},
		teamPKFullViewFun : function(){
			var self = this;
			$(self.showFullTeamPK).unbind('click').bind('click', function(){
				win.doFullScreen(function (e) {
					self.changeTeamPKTitleShow();
					self.changeFullViewShow();
					var wid = $(self.fullTeamPK).parent().width() - 30;
					var hei = $(window).height() - 50;
					$(self.fullTeamPK).removeClass('hide').siblings().addClass('hide');
					$(self.fullTeamPK).width(wid);
	            	salesMapsTeamPKChartObj.fullInit();
	            	searchObj.isFirst = true;
	                searchObj.init();
				});
			});
			self.closeFullViewFun();
		},
		closeFullViewFun : function(){
			var self = this;
			$(self.fullCloseId).unbind('click').bind('click', function(){
				win.doRestoreWindow(function (e) {
		            self.changeNormalViewShow();
		            searchObj.exit();
		        });
			});
		},
		changeTeamViewTitleShow: function(){
			var self = this;
			$(self.fullTeamViewTitle).removeClass('hide');
			$(self.fullTeamPKTitle).addClass('hide');
		},
		changeTeamPKTitleShow: function(){
			var self = this;
			$(self.fullTeamPKTitle).removeClass('hide');
			$(self.fullTeamViewTitle).addClass('hide');
		},
		changeFullViewShow: function(){
			var self = this;
			$(self.fullMapBody).removeClass('hide');
            $(self.salesBoard).addClass('hide');
		},
		changeNormalViewShow: function(){
			var self = this;
			$(self.fullMapBody).addClass('hide');
            $(self.salesBoard).removeClass('hide');
		}
	}
	/**销售排名地图*/
	var salesMapsStyleObj = {
		normalHeight: function(){
			$('.bottom-div-three').removeClass('height100p').addClass('height600');
		},
		currentHeight: function(){
			$('.bottom-div-three').removeClass('height600').addClass('height100p');
		}
	}
	/**
	 * 销售排名地图-团队总览 chart
	 * */
	var salesMapsTeamViewChartObj = {
        mapId: '#salesTeamViewChart',
        fullMapId: '#fullTeamView',
        mapObj: null,
        fullMapObj: null,
        organId: null,
        modalId: '#personSalesTrendModal',
        normalFlag: 0,
        fullFlag: 0,
        init: function(organId){
        	var self = this;
        	self.organId = organId;
        	self.requestMapsAxis();
        },
        requestMapsAxis: function () {
            var self = this;
            $.get(urls.getMapsBaseInfoUrl, function (rs) {
            	if (!_.isEmpty(rs)) {
            		salesMapsStyleObj.currentHeight();
                    //初始化地图基础信息
                    self.xAxisTitle = rs.xAxisTitle;
                    self.xAxisData = _.map(rs.xAxisData, _.iteratee('name'));
                    self.yAxisTitle = rs.yAxisTitle;
                    self.yAxisData = _.map(rs.yAxisData, _.iteratee('name'));
                    self.zAxisData = rs.zAxisData;
                    self.initChart();
                }
            });
        },
        initChart: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: false,
                isSimple: true,
                xAxis: {
                    title: self.xAxisTitle,
                    data: self.xAxisData
                },
                yAxis: {
                    title: self.yAxisTitle,
                    data: self.yAxisData
                },
                zAxis: {
                	data: self.zAxisData
                },
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    	salesMapsTeamViewChartObj.resizeMap();
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                    	$('#salesTeamViewChart .u-remain-point-wrapper').hide();
                    	var $td = $('#salesTeamViewChart .u-map-table td');
                    	defaultDatas.viewFullCount = 0;
                    	$.each($td, function(ind, obj){
                    		var $eq = $(obj).children('span');
                    		var $eq0 = $(obj).children('span').eq(0);
                    		var $eq1 = $(obj).children('span').eq(1);
                    		$eq.addClass('hide');
                    		if(!_.isUndefined($eq0)){
                    			$eq0.removeClass('hide');
                    		}
                    		if(!_.isUndefined($eq1)){
                    			$eq1.removeClass('hide');
                    		}
                    		$.each($eq, function(i, o){
                    			if(i > 2){
                    				defaultDatas.viewFullCount += 1;
                    			}
                    		});
                    	});
                    	$('#viewInfo').addClass('hide');
                    	if(defaultDatas.viewFullCount > 0){
                    		$('#viewInfo').removeClass('hide');
                    	}
                    	salesMapsTeamViewChartObj.resizeMap();
                    },
                    onClick: function (elem, point) {
                    	salesMapsTeamViewChartObj.showSalesTrend(point.empId, salesMapsTeamViewChartObj.organId);
                    }
                }
            });
            self.requestData(self.mapObj);
        },
        showSalesTrend: function(empId, organId){
        	$(salesMapsTeamViewChartObj.modalId).modal('show');
			$(salesMapsTeamViewChartObj.modalId).on('shown.bs.modal', function() {
				abilityTrackObj.init(empId);
				salesBankingTrackObj.organId = organId;
				salesBankingTrackObj.init(empId);
			});
			$(salesMapsTeamViewChartObj.modalId).on('hidden.bs.modal',function(){
				$(salesMapsTeamViewChartObj.modalId).off();
			});
        },
        requestData: function (mapObj) {
            var self = this;
        	var param = {
        		organId : self.organId,
        		date : defaultDatas.teamViewDate
        	}
            $.post(urls.queryTeamViewChartUrl, param, function (data) {
                if (mapObj) {
                	mapObj.clear();
            		if (!_.isEmpty(data)){
            			self.normalFlag = 0;
            			mapObj.addSimplePoints(data);
            		} else {
            			self.normalFlag = 1;
            		}
                }
            });
        },
        fullInit: function(organId){
        	var self = this;
        	self.organId = organId;
        	self.fullInitChart();
        },
        fullInitChart: function () {
        	var self = this;
            var w = $(self.fullMapId).parent().width();
            self.fullMapObj = $(self.fullMapId).talentMap({
                width: w,
                showNum: true,
                xAxis: {
                    title: self.xAxisTitle,
                    data: self.xAxisData
                },
                yAxis: {
                    title: self.yAxisTitle,
                    data: self.yAxisData
                },
                zAxis: {
                	data: self.zAxisData
                },
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                    },
                    onClick: function (elem, point) {
                    	salesMapsTeamViewChartObj.showSalesTrend(point.empId, salesMapsTeamViewChartObj.organId);
                    }
                }
            });
            self.fullRequestData(self.fullMapObj);
        },
        fullRequestData: function (mapObj) {
            var self = this;
        	var param = {
        		organId : self.organId,
        		date : defaultDatas.teamViewDate
        	}
            $.post(urls.queryTeamViewFullChartUrl, param, function (data) {
                if (mapObj) {
                	mapObj.clear();
                	if (!_.isEmpty(data)){
                		self.fullFlag = 0;
                		mapObj.addPoints(data);
                	} else {
                		self.fullFlag = 1;
                	}
                }
            });
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
            var fw = $(self.fullMapId).parent().width();
            if (fw > 0 && !_.isNull(self.fullMapObj)) self.fullMapObj.resize(fw);
            if(self.normalFlag == 1){
            	self.mapObj.clear();
            }
            if(self.fullFlag == 1){
            	self.fullMapObj.clear();
            }
        }
    }
	/**
	 * 销售排名地图-团队PK chart
	 * */
	var salesMapsTeamPKChartObj = {
		mapId: '#salesTeamPKChart',
        fullMapId: '#fullTeamPK',
        mapObj: null,
        fullMapObj: null,
        mapData: [],
        organId : null,
        normalFlag: 0,
        fullFlag: 0,
        num: 0,
        init: function () {
            var self = this;
            self.requestMapsAxis();
        },
        requestMapsAxis: function () {
            var self = this;
            $.get(urls.getMapsBaseInfoUrl, function (rs) {
                if (!_.isEmpty(rs)) {
                	salesMapsStyleObj.currentHeight();
                	defaultDatas.pkChartFlag = true;
                    //初始化地图基础信息
                    self.xAxisTitle = rs.xAxisTitle;
                    self.xAxisData = _.map(rs.xAxisData, _.iteratee('name'));
                    self.yAxisTitle = rs.yAxisTitle;
                    self.yAxisData = _.map(rs.yAxisData, _.iteratee('name'));
                    self.zAxisData = rs.zAxisData;
                    self.mapData = [];
                    if(!_.isNull(self.mapObj)){
                    	self.mapObj.clear();
                    }
                    self.initMaps();
                }
            });
        },
        initMaps: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: false,
                showTeam: true,     //是否启动团队分组
                isSimple: true,
                xAxis: {
                    title: self.xAxisTitle,
                    data: self.xAxisData
                },
                yAxis: {
                    title: self.yAxisTitle,
                    data: self.yAxisData
                },
                zAxis: {
                    data: self.zAxisData
                },
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    	salesMapsTeamPKChartObj.resizeMap();
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                    	$('#salesTeamPKChart .u-remain-point-wrapper').hide();
                    	var $td = $('#salesTeamPKChart .u-map-table td');
                    	defaultDatas.pkFullCount = 0;
                    	$.each($td, function(ind, obj){
                    		var num = 0;
                    		var $eq = $(obj).children('span');
                    		var $eq0 = $(obj).children('span').eq(0);
                    		var $eq1 = $(obj).children('span').eq(1);
                    		$eq.addClass('hide');
                    		if(!_.isUndefined($eq0)){
                    			$eq0.removeClass('hide');
                    		}
                    		if(!_.isUndefined($eq1)){
                    			$eq1.removeClass('hide');
                    		}
                    		var $eq0Color = $(obj).children('span').eq(0).find('i').css('color');
                    		$.each($eq, function(i, o){
                    			if(i >= 2){
                    				defaultDatas.pkFullCount += 1;
                    			}
                    			var color = $(o).children(i).css('color');
                    			if($eq0Color != color){
                    				num++;
                    				$eq0Color = color;
                    				$eq.eq(i).removeClass('hide');
                    				return true;
                    			}
                    		});
                    		if(num > 0){
                    			$eq1.addClass('hide');
                    		}
                    	});
                    	$('#pkInfo').addClass('hide');
                    	if(defaultDatas.pkFullCount > 0){
                    		$('#pkInfo').removeClass('hide');
                    	}
                    	salesMapsTeamPKChartObj.resizeMap();
                    },
                    onClick: function (elem, point) {
                    	salesMapsTeamViewChartObj.showSalesTrend(point.empId, point.organId);
                    }
                }
            });
            if(defaultDatas.pkFlag == 0){
				startPKObj.teamOrganPKRequest();
			} else {
				startPKObj.teamCustomPKRequest();
			}
        },
        renderAxis: function (data, len) {
            var self = this;
            var $hideBlock = $('#mapAxisBlock .map-axis-hide'), $showBlock = $('#mapAxisBlock .map-axis-show');
            var $showBlockRemove = $showBlock.find('.icon-remove');
            //渲染主体内容
            $showBlock.find('.map-axis-main').html(self.renderAxisHtml(data, len));
            if ($hideBlock.is(':hidden') && $showBlock.is(':hidden')) {
                $hideBlock.show();
                $showBlock.hide();
            }
            //绑定相关事件
            $hideBlock.unbind('click').click(function () {
                $(this).hide();
                $showBlock.show();
            });
            $showBlockRemove.unbind('click').click(function () {
                $showBlock.hide();
                $hideBlock.show();
            });
        },
        renderAxisHtml: function (data, len) {
            var html = '';
            for (var i = 0; i < data.length; i++) {
                var obj = data[i], total = obj.pointsTotal, w = Tc.formatFloat(total / len * 100, 0);
                html += '<div class="row map-axis-area">';
                html += '<div class="col-xs-3">' + obj.name + '</div>';
                html += '<div class="col-xs-7 ct-col1">';
                html += '<div class="progress" data-percent="' + w + '%"><div class="progress-bar" style="width:' + w + '%;background-color: ' + obj.color + ';"></div></div>';
                html += '<div class="map-axis-team">';
                for (var j = 0; j < obj.points.length; j++) {
                    var team = obj.points[j];
                    html += '<span><i class="icon-circle" style="color: ' + team.color + ';"></i>&nbsp;' + team.data.length + '人</span>'
                }
                html += '</div></div>';
                html += '<div class="col-xs-2 ct-col1">&nbsp;&nbsp;' + total + '人</div>';
                html += '</div>';
            }
            return html;
        },
        requestData: function (params, idx) {
            var self = this;
            $.ajax({
                url: urls.getTeamEmpPointUrl,
                data: params,
                type: 'post',
                async: false,
                success: function (rs) {
                    if (!_.isEmpty(rs)) {
                        if (!_.isNull(self.mapObj)) {
                        	if(self.num == 0){
                        		self.mapObj.clear();
                        	}
                        	self.num++;
                        	self.normalFlag = 0;
                            self.mapData[idx] = rs;
                            self.mapObj.addPoints(rs);
                        }
                    } else if (self.mapObj) {
                    	self.normalFlag = 1;
                		self.mapObj.clear();
                    }
                }
            });
        },
        fullInit: function () {
            var self = this;
            var w = $(self.fullMapId).parent().width();
            self.fullMapObj = $(self.fullMapId).talentMap({
                width: w,
                showNum: true,
                showTeam: true,
                xAxis: {
                    title: self.xAxisTitle,
                    data: self.xAxisData
                },
                yAxis: {
                    title: self.yAxisTitle,
                    data: self.yAxisData
                },
                zAxis: {
                    data: self.zAxisData
                },
                point: {
                    afterCreate: function (elem, point) {
                    },
                    onClick: function (elem, point) {
                    	salesMapsTeamViewChartObj.showSalesTrend(point.empId, point.organId);
                    }
                }
            });
            self.fullRequestData();
        },
        fullRequestData: function(){
        	var self = this;
        	var mapData = self.mapData;
            for (var i = 0; i < mapData.length; i++) {
                var data = mapData[i];
                if (_.isNull(data)) continue;
                if (!_.isNull(self.fullMapObj))  self.fullMapObj.addPoints(data);
            }
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
            var fw = $(self.fullMapId).parent().width();
            if (fw > 0 && !_.isNull(self.fullMapObj)) self.fullMapObj.resize(fw);
            if(self.normalFlag == 1){
            	self.mapObj.clear();
            }
            if(self.fullFlag == 1){
            	self.fullMapObj.clear();
            }
        }
	}
	/**
	 * 销售排名地图-团队PK
	 * */
	var salesRankMapObj = {
		organPK : '#organPK',
		selfPK : '#selfPK',
		mapsOnePK : '#maps-pk-one',
		init : function(organId){
			var self = this;
			this.organId = organId;
			self.getRequestDatas();
		},
		getRequestDatas: function(){
			var self = this;
			$(self.organPK).empty();
			var param = {
				organId : self.organId
			}
			$.post(urls.querySalesRankMapTeamPKUrl, param, function(data){
				if(data)
					self.loadDatas(data);
			});
		},
		loadDatas : function(data){
			var self = this;
			var div = "";
			$.each(data.list, function(index, object){
				div += '<div class="team-bottom-div left" data-toggle="'+object.conNum+'" data-organId="'+object.organId+'" data-organName="'+object.organName+'" >'
					+ '<div class="circle left">'
					+ '<span>'+object.organName.substr(0,1)+'</span>'
					+ '</div>'
					+ '<div class="bottom-text left">'
					+ '<span>'+object.organName+'</span><br>'
					+ '<span>'+object.conNum+'人</span>'
					+ '</div>'
					+ '</div>';
			});
			$(self.organPK).append(div);
			//PK组织机构赋值
			$('#topOrganId').val(data.topOrgans[0].organizationId);
			$('#topOrganName').val(data.topOrgans[0].organizationName);
			self.teamSelectFun();
		},
		teamSelectFun: function(){
			var self = this;
			$(self.mapsOnePK + " .team-bottom-div").click(function () {
		        var _this = $(this);
		        var _parent = _this.parent();
		        if (_this.hasClass("bg")) {
		            _this.removeClass("bg");
		            if (_parent.children('.bg').length < 4) {
		                startPKObj.organTeamInit();
		            }
		        } else {
		            if (_parent.children('.bg').length >= 3) {
		                showErrMsg('所选团队已超过3个!');
		                startPKObj.organTeamRemove();
		            }
		            _this.addClass("bg");
		        }
		        $('#organTeamNum').text(_parent.children('.bg').length);
		        var total = 0;
		        $.each(_parent.children('.bg'), function (i, obj) {
		            total += $(obj).data('toggle');
		        });
		        if (total > 1000) {
		            showErrMsg('团队人数超过1000,请重新选择或使用自定义团队!');
		            startPKObj.organTeamRemove();
		        } else {
		        	if (_parent.children('.bg').length > 3){
		        		startPKObj.organTeamRemove();
		        	} else {
		        		startPKObj.organTeamInit();
		        	}
		        }
		    });
		}
	}
	$('.team-name').tooltip();
	/**
     * 搜索事件
     * @type {{searchBoxId: string, init: searchBoxObj.init}}
     */
    var searchBoxObj = {
        organBar: null,
        sequenceBar: null,
        sequenceSubBar: null,
        abilityBar: null,
        performanceBar: null,
        ageBar: null,
        sexBar: null,
        eduBar: null,
        allParams: [],  //三个团队参数初始值
        init: function () {
            var self = this;
            self.requestData();
            self.initOrganBar();

            self.cycleDate = $('#cycleDate').val();
            //团队参数选择事件
            $("#maps-pk-two .team-bottom-div .bottom-text").unbind('click').bind('click', function () {
                var _this = $(this);
                var idx = _this.parent().index();
                self.clearAllValues();
                self.initExistData(idx);
                self.initSubmitButton(false);
                $('#customTeamModal').modal('show');
            });
        },
        requestData: function () {  //获取所有选项
            var self = this;
            $.get(urls.searchBoxUrl, function (rs) {
                if (!_.isEmpty(rs)) {
                    self.render(rs);
                }
            });
        },
        render: function (rs) { //渲染数据
            var self = this;
            for (var i = 0; i < rs.length; i++) {
                switch (rs[i].id) {
                    case 'performanceKey':
                        self.initSelectBar('performanceBar', 'performanceKey', rs[i].name, rs[i].childs);
                        break;
                    case 'ageId':
                        self.initSelectBar('ageBar', 'ageId', rs[i].name, rs[i].childs);
                        break;
                    case 'sexId':
                        self.initSelectBar('sexBar', 'sexId', rs[i].name, rs[i].childs);
                        break;
                    case 'eduId':
                        self.initSelectBar('eduBar', 'eduId', rs[i].name, rs[i].childs);
                        break;
                }
            }
        },
        initTeamName: function (idx) {  //初始化团队名称
            var self = this, $teamName = $('#teamName');
            if (_.isUndefined(idx)){
                $teamName.val('');
                return;
            }
            self.currIdx = idx; //保存当前索引
            $teamName.focusout(function () {
                var $this = $(this);
                var teamName = $this.val();
                var bool = self.checkTeamName(idx, teamName);
                if (bool) {
                    showErrMsg('团队名称已存在，请重新输入！');
                    self.initSubmitButton(false);
                } else {
                    self.initSubmitButton(true);
                }
            });
            
            var _this = $($('#maps-pk-two .team-bottom-div').get(idx));
            var teamName = _this.find('.bottom-text').children(':first').text();
            $teamName.val(teamName);
            //最后团队没有继续添加功能
            if (idx == 2) $('#continueAddBlock').addClass('hide');
            else $('#continueAddBlock').removeClass('hide');

            self.currIdx = idx; //保存当前索引
        },
        initExistData: function (idx) {
            var self = this, topOrganId = $('#topOrganId').val(), topOrganName = $('#topOrganName').val();
            self.initTeamName(idx);
            var param = self.allParams[idx];
            if (_.isUndefined(param)) {
                var values = {id: topOrganId, text: topOrganName};
                if (self.organBar && !_.isEmpty(topOrganId)) self.organBar.organTreeSelector("value", values);
                return;
            }
            //初始化已选参数
            if (self.performanceBar && !_.isNull(param.performanceStr)) self.performanceBar.setSelectItems(param.performanceStr);
            if (self.ageBar && !_.isNull(param.ageStr)) self.ageBar.setSelectItems(param.ageStr);
            if (self.sexBar && !_.isNull(param.sexStr)) self.sexBar.setSelectItems(param.sexStr);
            if (self.eduBar && !_.isNull(param.eduStr)) self.eduBar.setSelectItems(param.eduStr);
            if (self.organBar && !_.isNull(param.organId)) self.organBar.organTreeSelector("value", {id: param.organId});
        },
        initOrganBar: function () { //初始化机构
            var self = this;
            self.organBar = $("#organZtree").organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                mouseenterColor: '#fff',
                mouseleaveColor: '#fff',
                onSelect: function (ids, texts) {
                    if (!_.isEmpty(ids)) {
                        self.removeOrganEvent(false);
                        self.requestEmpCount();
                    }
                }
            });
        },
        initSelectBar: function (objName, objId, text, data) {  //初始化选项
            var self = this;
            var options = {
                selectType: true,
                selectCallback: function () {
                    self.requestEmpCount();
                }
            };
            self[objName] = new window.W.SelectItem(objId, options);
            self[objName].setDataItems(data);
        },
        initSubmitButton: function (bool) {  //初始化按钮事件
            var self = this;
            var $submitBtn = $('#customTeamSubmitBtn');
            if (!bool) {
                $submitBtn.addClass('btn-light disabled').removeClass('btn-info').unbind('click');
                return;
            }
            $submitBtn.removeClass('btn-light disabled').addClass('btn-info');
            $submitBtn.click(function () {
                var teamName = $.trim($('#teamName').val());
                var teamNum = $('#teamNum').text();
                var continueAddBool = $('#continueAddTeam').prop('checked');
                var idx = self.currIdx;
                var params = self.getTeamParams();
                params.team = teamName;
                self.allParams[idx] = params;
                if (continueAddBool) {
                    self.clearAllValues();
                    self.initTeamName(idx + 1);
                } else {
                    $('#customTeamModal').modal('hide');
                }
                var _this = $($("#maps-pk-two .team-bottom-div").get(idx));
                if (teamName != ('团队' + (idx + 1))) {
                    var feature = self.getTeamNameFeature(idx);
                    _this.find('.circle span').text(feature);
                }
                _this.find('.bottom-text span').text(teamName);
                _this.find('.bottom-text div').removeAttr('data-original-title').attr('data-original-title', teamName);
                
                _this.find('.bottom-text a').html(teamNum + '人');
                _this.find('.bottom-img').removeClass('this-hide');
                self.clearCustomTeamParam();
                self.checkCustomTeamNumber();
            });
        },
        clearCustomTeamParam: function(){
        	var self = this;
        	$('.bottom-img i').unbind('click').bind('click', function(){
        		$(this).parent().addClass('this-hide');
        		$(this).parent().siblings('.bottom-text').find('a').html('设置条件');
        		var team = $(this).parent().siblings('.bottom-text').find('span').html();
        		for(var i = 0; i < self.allParams.length; i++){
        			if(_.isUndefined(self.allParams[i]) || _.isNull(self.allParams[i])) continue;
        			if(self.allParams[i].team == team){
        				delete self.allParams[i];
        			}
        		}
        		var num = 0;
        		if(self.allParams.length > 0){
        			for(var i = 0; i < self.allParams.length; i++){
        				if(_.isNull(self.allParams[i]) || _.isUndefined(self.allParams[i])) continue;
        				num++;
        			}
        			if(num != 0){
        				startPKObj.customTeamInit();
        			} else {
        				startPKObj.customTeamRemove();
        			}
        		} else {
        			startPKObj.customTeamRemove();
        		}
        	});
        },
        removeOrganEvent: function (bool) { //机构移除事件
            var self = this, topOrganId = $('#topOrganId').val(), topOrganName = $('#topOrganName').val();
            var $btn = $('#organRemovebtn');
            if (bool) {
                $btn.addClass('hide').unbind('click');
                self.organBar && self.organBar.organTreeSelector("value", {id: topOrganId, text: topOrganName});
            } else {
                $btn.removeClass('hide');
                $btn.unbind('click').click(function () {
                    self.removeOrganEvent(true);
                });
            }
        },
        clearAllValues: function () { //清除所有值
            var self = this;
            self.initTeamName();

            self.removeOrganEvent(true);

            self.performanceBar.clearSelected();
            self.ageBar.clearSelected();
            self.sexBar.clearSelected();
            self.eduBar.clearSelected();
            $('#continueAddTeam').prop('checked', false);
            $('#teamNum').text('...');
        },
        requestEmpCount: function () {  //获取团队成员统计
            var self = this;
            var params = self.getTeamParams();
            $('#teamNum').text('...');
            self.initSubmitButton(false);
            $.get(urls.getTeamSettingEmpCountUrl, params, function (rs) {
                self.initSubmitButton(true);
                if (_.isNumber(rs)) $('#teamNum').text(rs);
                else $('#teamNum').text('...');
            });
        },
        getTeamNameFeature: function (idx) {    //获取团队名称特征
            var self = this, allName = [];
            $.each($('#maps-pk-two .team-bottom-div'), function (i, obj) {
                var $obj = $(obj);
                allName[i] = $obj.find('.bottom-text span').text();
            });
            var currName = allName[idx], feature = '';
            for (var i = 0; i < currName.length; i++) {
                var num = 0;
                for (var j = 0; j < allName.length; j++) {
                    if (allName[j][i] == currName[i]) {
                        num++;
                    }
                }
                if (num >= 2) continue;
                feature = currName[i];
            }
            return feature;
        },
        getTeamParams: function () {    //获取团队参数
            var self = this;
            var organId = self.organBar.organTreeSelector('value');
            var performanceItems = self.performanceBar.getSelectedItems();
            var ageItems = self.ageBar.getSelectedItems();
            var sexItems = self.sexBar.getSelectedItems();
            var eduItems = self.eduBar.getSelectedItems();
            var yearMonth = self.cycleDate;
            var params = {
                organId: organId,
                performanceStr: _.isEmpty(performanceItems) ? null : performanceItems.join(','),
                ageStr: _.isEmpty(ageItems) ? null : ageItems.join(','),
                sexStr: _.isEmpty(sexItems) ? null : sexItems.join(','),
                eduStr: _.isEmpty(eduItems) ? null : eduItems.join(','),
                yearMonth: yearMonth
            }
            return params;
        },
        checkTeamName: function (idx, teamName) {
            var self = this, bool = false;
            $.each($('#maps-pk-two .team-bottom-div:not(' + idx + ')'), function (i, obj) {
                var $obj = $(obj);
                var name = $obj.find('.bottom-text span').text();
                if (teamName == name) {
                    bool = true;
                    return true;
                }
            });
            return bool;
        },
        checkCustomTeamNumber: function () {
            var self = this, allNum = 0;
            var params = self.allParams;
            for (var i = 0; i < params.length; i++) {
                if (_.isNull(params[i]) || _.isUndefined(params[i])) continue;
                var $obj = $($('#maps-pk-two .team-bottom-div').get(i));
                var teamNum = $obj.find('.bottom-text a').html();
                allNum += parseInt(teamNum);
            }
            customAllNum = allNum;
            if (allNum > 1000) {
                showErrMsg('团队人数超过1000,请重新选择定义团队!');
                startPKObj.customTeamRemove();
            } else {
                startPKObj.customTeamInit();
            }
        }
    }
	var customAllNum = 0;
	searchBoxObj.init();
	/**开始pk*/
	var startPKObj = {
        organTeamId: '#organTeamBtn',
        customTeamId: '#customTeamBtn',
        init: function () {
            var self = this;
            self.organTeamInit();
            self.customTeamInit();
        },
        organTeamInit: function () {  //机构团队按钮绑定事件
            var self = this;
            var objs = $('#maps-pk-one .maps-pk-main .team-bottom-div.bg');
            if (objs.length == 0){
            	self.organTeamRemove();
            	return;
            }  else {
            	$(self.organTeamId).removeClass('btn-light disabled').addClass('btn-info');
            	$(self.organTeamId).unbind('click').bind('click', function () {
            		self.objs = objs;
            		defaultDatas.pkFlag = 0;
            		self.showChart();
            		salesMapsTeamPKChartObj.init();
            	});
            }
        },
        teamOrganPKRequest: function(){
        	var self = this;
        	self.returnOrganTeam();
        	defaultDatas.normalNum = 0;
			defaultDatas.highNum = 0;
			defaultDatas.lowNum = 0;
    		defaultDatas.pkCount = 0;
    		salesMapsTeamPKChartObj.num = 0;
        	$.each(self.objs, function (i, obj) {
                var $obj = $(obj);
                var organId = $obj.data('organid'), organName = $obj.data('organname');
                var param = {
                    team: organName,
                    organId : organId,
                    yearMonth : defaultDatas.teamPKDate
                };
                salesMapsTeamPKChartObj.requestData(param, i);
        		salesMapsPKGridObj.requestData(param);
            });
        },
        customTeamInit: function () { //自定义团队按钮绑定事件
            var self = this;
            if(customAllNum == 0){
            	self.customTeamRemove();
            	return;
            } else {
            	$(self.customTeamId).removeClass('btn-light disabled').addClass('btn-info');
            	$(self.customTeamId).unbind('click').bind('click', function () {
            		defaultDatas.pkFlag = 1;
            		self.showChart();
            		salesMapsTeamPKChartObj.init();
            	});
            }
        },
        teamCustomPKRequest: function(){
        	var self = this;
        	self.returnCustomTeam();
        	var params = searchBoxObj.allParams;
        	defaultDatas.normalNum = 0;
        	defaultDatas.highNum = 0;
        	defaultDatas.lowNum = 0;
        	defaultDatas.pkCount = 0;
        	salesMapsTeamPKChartObj.num = 0;
        	for (var i = 0; i < params.length; i++) {
        		var param = params[i];
        		if (_.isNull(param) || _.isUndefined(param)) continue;
        		if(param.organId == null){
        			param.organId = reqOrgId;
        		}
        		param.yearMonth = defaultDatas.teamPKDate;
        		salesMapsTeamPKChartObj.requestData(param, i);
        		salesMapsPKGridObj.requestData(param);
        	}
        },
        organTeamRemove: function () {    //机构团队按钮解除事件
            var self = this;
            $(self.organTeamId).addClass('btn-light disabled').removeClass('btn-info').unbind('click');
        },
        customTeamRemove: function () {   //自定义团队按钮解除事件
            var self = this;
            $(self.customTeamId).addClass('btn-light disabled').removeClass('btn-info').unbind('click');
        },
        showChart: function(){
        	var self = this;
        	$('#maps-pk-three').removeClass('hide');
        	$('#maps-pk-one').addClass('hide');
        	$('#maps-pk-two').addClass('hide');
        },
        returnOrganTeam: function(){
        	var self = this;
        	$('#returnTeamPK').unbind('click').bind('click', function(){
        		$('#maps-pk-one').removeClass('hide');
        		$('#maps-pk-two').addClass('hide');
        		$('#maps-pk-three').addClass('hide');
        	});
        },
        returnCustomTeam: function(){
        	var self = this;
        	$('#returnTeamPK').unbind('click').bind('click', function(){
        		$('#maps-pk-one').addClass('hide');
        		$('#maps-pk-two').removeClass('hide');
        		$('#maps-pk-three').addClass('hide');
        	});
        }
    }
	$("#customTeam").click(function () {
        $("#maps-pk-one").addClass('hide');
        $("#maps-pk-two").removeClass('hide');
    });
    $("#checkSuborgan").click(function () {
        $("#maps-pk-one").removeClass('hide');
        $("#maps-pk-two").addClass('hide');
    });
    /*提示信息*/
    function showErrMsg(content) {
        $._messengerDefaults = {
            extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top messenger-on-right'
        };
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    }
    /**
     * 销售排名地图总览列表
     * */
    var salesMapsViewGridObj = {
		loadingImg : '#teamViewLoading',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		self.requestData();
    	},
    	requestData: function(){
    		var self = this;
    		$(self.loadingImg).removeClass('hide');
    		self.clearGrid();
    		var param = {
				organId : self.organId,
				yearMonth : defaultDatas.teamViewDate,
				flag : true
    		};
    		$.post(urls.queryMapsGridUrl, param, function(data){
    			$(self.loadingImg).addClass('hide');
    			self.loadData(data);
    		});
    	},
    	loadData: function(data){
    		var self = this;
    		if(!_.isEmpty(data) && !_.isUndefined(data)){
    			var normalStyle = "width : " + data.normalPer +"%";
    			$('#normalBar').removeAttr('style').attr('style', normalStyle);
    			$('#normalBarVal').html(data.normalNum + '人');
    			$('#normalPerVal').html(Tc.formatFloat(data.normalPer));
    			var highStyle = "width : " + data.highPer +"%";
    			$('#highBar').removeAttr('style').attr('style', highStyle);
    			$('#highBarVal').html(data.highNum + '人');
    			$('#highPerVal').html(Tc.formatFloat(data.highPer));
    			var lowStyle = "width : " + data.lowPer +"%";
    			$('#lowBar').removeAttr('style').attr('style', lowStyle);
    			$('#lowBarVal').html(data.lowNum + '人');
    			$('#lowPerVal').html(Tc.formatFloat(data.lowPer));
    			var count = parseInt(data.normalNum)+parseInt(data.highNum)+parseInt(data.lowNum);
    		}
    	},
    	clearGrid: function(){
    		var self = this;
    		$('#normalBar').removeAttr('style');
			$('#normalBarVal').html('0人');
			$('#normalPerVal').html(0);
			$('#highBar').removeAttr('style');
			$('#highBarVal').html('0人');
			$('#highPerVal').html(0);
			$('#lowBar').removeAttr('style');
			$('#lowBarVal').html('0人');
			$('#lowPerVal').html(0);
    	}
    }
    /**
     * 销售排名地图PK列表
     * */
    var salesMapsPKGridObj = {
		loadingImg : '#teamPKLoading',
		init : function(organId){
			var self = this;
			this.organId = organId;
			var param = {
				organId : self.organId,
				yearMonth : defaultDatas.teamViewDate
			}
			self.requestData(param);
		},
		requestData: function(param){
			var self = this;
			$(self.loadingImg).removeClass('hide');
			self.clearGrid();
			$.post(urls.queryMapsGridUrl, param, function(data){
				$(self.loadingImg).addClass('hide');
				self.loadData(data);
			});
		},
		loadData: function(data){
			var self = this;
			if(!_.isEmpty(data) && !_.isUndefined(data)){
				defaultDatas.normalNum += parseInt(data.normalNum);
				defaultDatas.highNum += parseInt(data.highNum);
				defaultDatas.lowNum += parseInt(data.lowNum);
				defaultDatas.pkCount = defaultDatas.normalNum + defaultDatas.highNum + defaultDatas.lowNum;
				var pkNormalPer = Tc.formatFloat(defaultDatas.normalNum * 100 / defaultDatas.pkCount);
				var pkNormalStyle = "width : " + pkNormalPer +"%";
				$('#pkNormalBar').removeAttr('style').attr('style', pkNormalStyle);
				$('#pkNormalBarVal').html(defaultDatas.normalNum + '人');
				$('#pkNormalPerVal').html(pkNormalPer);
				var pkHighPer = Tc.formatFloat(defaultDatas.highNum * 100 / defaultDatas.pkCount);
				var pkHighStyle = "width : " + pkHighPer +"%";
				$('#pkHighBar').removeAttr('style').attr('style', pkHighStyle);
				$('#pkHighBarVal').html(defaultDatas.highNum + '人');
				$('#pkHighPerVal').html(pkHighPer);
				var pkLowPer = Tc.formatFloat(defaultDatas.lowNum * 100 / defaultDatas.pkCount);
				var pkLowStyle = "width : " + pkLowPer +"%";
				$('#pkLowBar').removeAttr('style').attr('style', pkLowStyle);
				$('#pkLowBarVal').html(defaultDatas.lowNum + '人');
				$('#pkLowPerVal').html(pkLowPer);
			}
		},
		clearGrid: function(){
			var self = this;
			$('#pkNormalBar').removeAttr('style');
			$('#pkNormalBarVal').html('0人');
			$('#pkNormalPerVal').html(0);
			$('#pkHighBar').removeAttr('style');
			$('#pkHighBarVal').html('0人');
			$('#pkHighPerVal').html(0);
			$('#pkLowBar').removeAttr('style');
			$('#pkLowBarVal').html('0人');
			$('#pkLowPerVal').html(0);
		}
    }
    /***
     * 全屏下的搜索功能
     * 
     */
    var searchObj = {
        show: false,
        isFirst: true,
        searchBtn: '#searchEmpBtn',
        searchBlock: '#searchEmpBlock',
        init: function (flag) {
            var self = this;
            //解除绑定是为了防止取消全屏之后重复绑定事件
            $(self.searchBtn).unbind('click').click(function () {
                var $searchBlock = $(self.searchBlock);
                $searchBlock.height(0);
                var h = $searchBlock.parents('.row').height();
                var winh = document.body.clientHeight - 41;
                $searchBlock.height(h > winh ? h : winh);
                $searchBlock.fadeToggle('fast', 'linear');
                self.show = self.show ? false : true;

                if (!self.show) {
                    if (self.point) self.drawDislog(self.point, false);
                    return;
                }

                //第一次获取地图人员数据并渲染select2的值
                if (self.isFirst) {
                	if(flag){
                		self.requestData();
                	} else {
                		self.requestDataPK();
                	}
                }

                var select2Obj = self.selectObj;
                if (undefined === select2Obj) return;
                select2Obj.unbind('select2:select').on('select2:select', function (evt) {
                    self.drawDislog(evt.params.data, true);
                });
                select2Obj.unbind('select2:unselect').on('select2:unselect', function (evt) {
                    $('#searchContent').empty();
                    $('#shade').hide();
                });
            });
            //隐藏搜索框操作
            $('#searchCloseBtn').unbind('click').click(function () {
                $(self.searchBtn).click();
            });
        },
        requestData: function () {
            var self = this, $selectEmp = $('#selectEmp'), mapsObj = salesMapsTeamViewChartObj.fullMapObj;
            if (!mapsObj) return;
            var allPoints = mapsObj.getAllPoints();
            if ($.isEmptyObject(allPoints)) return;
            
            var pointsData = [];
            $.each(allPoints, function (idx, item) {
                item.id = item.__id;
                var yearMonth = item.xLabel;
                var points = _.find(pointsData, function (obj) {
                    return obj.text == yearMonth;
                });
                if (_.isUndefined(points)) pointsData.push({text: yearMonth, children: [item]});
                else points.children.push(item);
            });
//            pointsData = _.sortBy(pointsData, 'text').reverse();

            // 下拉菜单,先初始化
            $selectEmp.html('<option value="-1"></option>');
            self.selectObj = $selectEmp.select2({
                language: 'zh-CN',
                data: pointsData,
                placeholder: {
                    id: '-1',
                    text: '请输入名称'
                },
                allowClear: true
            });
            self.isFirst = false;
        },
        requestDataPK: function () {
            var self = this, mapsObj = salesMapsTeamPKChartObj.fullMapObj;
            if (!mapsObj) return;
            var allPoints = mapsObj.getAllPoints(), teamArray = salesMapsTeamPKChartObj.fullMapObj.options.teamArray;
            if ($.isEmptyObject(allPoints)) return;

            var pointsData = [];
            $.each(allPoints, function (idx, item) {
                item.id = item.__id;
                var teamName = teamArray[item._t];
                var points = _.find(pointsData, function (obj) {
                    return obj.text == teamName;
                });
                if (_.isUndefined(points)) pointsData.push({text: teamName, children: [item]});
                else points.children.push(item);
            });
            // 下拉菜单
            $('#selectEmp').html('<option value="-1">请输入名称</option>');
            self.selectObj = $("#selectEmp").select2({
                language: 'zh-CN',
                data: pointsData,
                placeholder: {
                    id: '-1',
                    text: '请输入名称'
                },
                allowClear: true
            });
            self.isFirst = false;
        },
        drawDislog: function (point, bool) {
            var self = this;
            var pointId = point.id;
            if (!bool) {
                $('#shade').hide();
                $('#' + pointId).removeAttr('style');
                return;
            }
            $('#shade').show();
            //假如之前有搜索过,隐藏之前的
            if (self.point) $('#' + self.point.id).removeAttr('style');

            $('#' + pointId).css({
                'zIndex': 200,
                'backgroundColor': '#fff'
            });
            var $searchContent = $('#searchContent');
            $searchContent.html(self.drawEmpDislog(point));
            $searchContent.find('button.btn').click(function () {
            	salesMapsTeamViewChartObj.showSalesTrend(point.empId, point.organId);
            });
            self.point = point;
        },
        drawEmpDislog: function (point) {
            var html = $('<div class="search-emp-main"></div>');

            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.text + '</span>');
            html.append('<span class="col-xs-6 ct-col1 right"><button class="btn btn-primary" data-toggle="' + point.empId + '">销售趋势</button></span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.xLabel + '</span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.yLabel + '</span>');

            return html;
        },
        exit: function () {
        	var self = this, $searchContent = $('#searchContent');
            if (self.show) $(self.searchBtn).click();
            $searchContent.empty();
            self.isFirst = true;
        },
        resize: function () {
            var self = this;
            var $searchBlock = $(self.searchBlock);
            $searchBlock.height(0);
            var h = $searchBlock.parents('.row').height();
            var winh = document.body.clientHeight - 41;
            $searchBlock.height(h > winh ? h : winh);
        }
    }
    
	/***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    };

    /**
     * 清除echart面板
     * @param eChartObj
     */
    function clearEChart(eChartObj){
    	if(eChartObj){
    		eChartObj.clear();
    	}
    };
    
	//页面获取用户黄色/红色预警设置数据
    var loadWarningInfo = {
		salesNumTitle : '#salesNumTitle',
		salesOrganTitle : '#salesOrganTitle',
		personFlowTitle : '#personFlowTitle',
		abilityCheckTitle : '#abilityCheckTitle',
    	init : function(organId){
    		var self = this;
    		this.organId = organId;
    		if(num == 1){
        		self.requestDatas();
            } else {
            	self.setDefaultDatas();
            	topViewsObj.init(self.organId);
            }
    	},
    	requestDatas: function(){
    		var self = this;
    		$.post(urls.queryWarningInfoUrl, function(data){
    			if(data.names != null && data.names != ''){
    				self.setDatas(data);
    			} else {
    				self.setDefaultDatas();
    			}
    			topViewsObj.init(self.organId);
    			salesWarningObj.loadDatasFun();
    		});
    	},
    	setDatas: function(data){
    		var self = this;
    		var namesArr = data.names.split(',');
        	var yellowRangesArr = data.yellowRanges.split('#');
        	var redRangesArr = data.redRanges.split(',');
        	var notesArr = data.notes.split(',');
        	var salesNumTitle = $(self.salesNumTitle).html();
    		var salesOrganTitle = $(self.salesOrganTitle).html();
    		var personFlowTitle = $(self.personFlowTitle).html();
    		var abilityCheckTitle = $(self.abilityCheckTitle).html();
    		for(var i = 0; i < namesArr.length; i++){
    			if(salesNumTitle == notesArr[i]){
    				defaultDatas.red_sales_val  = redRangesArr[i];
    				defaultDatas.yellow_sales_val = yellowRangesArr[i];
    			} else if(salesOrganTitle == notesArr[i]){
    				defaultDatas.red_sales_organ = redRangesArr[i];
    				defaultDatas.yellow_sales_organ = yellowRangesArr[i];
    			} else if(personFlowTitle == notesArr[i]){
    				defaultDatas.red_person_flow = redRangesArr[i];
    				defaultDatas.yellow_person_flow = yellowRangesArr[i];
    			} else if(abilityCheckTitle == notesArr[i]){
    				defaultDatas.red_ability_check = redRangesArr[i];
    				defaultDatas.yellow_ability_check = yellowRangesArr[i];
    			}
    		}
    	},
    	setDefaultDatas: function(){
    		var self = this;
    		defaultDatas.red_sales_val  = red;
        	defaultDatas.yellow_sales_val = yellow;
        	defaultDatas.red_sales_organ = red;
        	defaultDatas.yellow_sales_organ = yellow;
        	defaultDatas.red_person_flow = red;
        	defaultDatas.yellow_person_flow = yellow;
        	defaultDatas.red_ability_check = red_ywnlkh;
        	defaultDatas.yellow_ability_check = yellow_ywnlkh;
    	}
    }
	// 总览
    var pageOneObj = {
        trainCover: 0.0,
        init: function (organId, flag) {
            var self = this;
            self.organId = organId;
            loadWarningInfo.init(organId);
            if(!flag){
            	timecrowdObj.init(organId);
            }
            defaultDatas.organNum = 0;
            todaySalesInfoObj.init(organId);
            teamRankingObj.init(organId);
            salesMapObj.init(organId);
            salesWarningObj.init(organId);
            salesRankingObj.init(organId);
        }
    }
    // 维度分析
    var pageTwoObj = {
        init: function (organId) {
            var self = this;
            self.organId = organId;
            standardRateObj.init(organId);
            salesTrendObj.init(organId);
            personStandardRateObj.init(organId);
        }
    }
    //销售排名地图
    var pageThreeObj = {
		init: function (organId) {
			var self = this;
			self.organId = organId;
			salesRankMapObj.init(organId);
			salesMapsTimeObj.init(organId);
			startPKObj.init();
			salesMapsIsFullShowObj.init(organId);
			salesMapsTeamViewChartObj.init(organId);
			salesMapsViewGridObj.init(organId);
			salesMapsStyleObj.currentHeight();
		}
    }
    //销量分析
    var pageFourObj = {
		init: function (organId) {
			var self = this;
			self.organId = organId;
			personChangeObj.init(organId);
			personSalesObj.init(organId);
			personSalesEffectGridObj.init(organId);
		}
    }
    
    /**
	 * 切换机构或点击tab页时,更新页面数据
	 */
    function changeData(targetAreaId, flag) {
        var selOrganId = reqOrgId;
        if (targetAreaId == 'page-four') {
        	pageFourObj.init(selOrganId);
        } else if (targetAreaId == 'page-three') {
        	pageThreeObj.init(selOrganId);
        } else if (targetAreaId == 'page-two') {
        	pageTwoObj.init(selOrganId);
        } else {
        	pageOneObj.init(selOrganId, flag);
        }
    }

    changeData('page-one');

    /*切换左边导航*/
    $(".leftListDiv").click(function (e) {
        e.stopPropagation();
        if ($(this).hasClass("selectList")) {
            return;
        } else {
            var _page = $(this).attr("page");
            $(".rightBodyPage").hide();
            $("#" + _page).show();
            $(".leftListDiv").removeClass("selectList");
            $(this).addClass("selectList");
            changeData(_page, true);
        }
    });
    /**
     * 销量分析-人员变动情况/人员销售影响标签页切换
     * */
    $("#page-four .leftSetUpBtnDiv").click(function () {
    	if ($(this).hasClass("leftSetUpBtnSelect"))return;
        $(this).parents(".leftSetUpBtn").find(".leftSetUpBtnDiv").removeClass("leftSetUpBtnSelect");
        $(this).addClass("leftSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart-1") {
            $(this).parents(".SetUpBody").find(".chart-view-2").show();
            $(this).parents(".SetUpBody").find(".chart-view-1").hide();
            $(this).parents(".SetUpBody").attr("view", "chart-2");
            personSalesObj.chart.resize();
            personSalesEffectGridObj.resizeGrid();
        } else {
            $(this).parents(".SetUpBody").find(".chart-view-1").show();
            $(this).parents(".SetUpBody").find(".chart-view-2").hide();
            $(this).parents(".SetUpBody").attr("view", "chart-1");
            personChangeObj.chart.resize();
        }
    });
    
    /**
     * 维度分析-销售趋势图表/表格切换
     * */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart") {
            $(this).parents(".SetUpBody").find(".table-view").show();
            $(this).parents(".SetUpBody").find(".chart-view").hide();
            $(this).parents(".SetUpBody").attr("view", "table");
            salesTrendObj.resizeGrid();
        } else {
            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
            salesTrendObj.resizeChart();
        }
    });
    /**
     * 销售排名地图-团队总览/团队PK切换
     **/
    $("#page-three .leftSetUpBtnDiv").click(function () {
    	if ($(this).hasClass("leftSetUpBtnSelect"))return;
    	$(this).parents(".leftSetUpBtn").find(".leftSetUpBtnDiv").removeClass("leftSetUpBtnSelect");
    	$(this).addClass("leftSetUpBtnSelect");
    	if ($(this).parents(".SetUpBody").attr("view") == "chart-1") {
    		$(this).parents(".SetUpBody").find(".chart-view-2").show();
    		$(this).parents(".SetUpBody").find(".chart-view-1").hide();
    		$(this).parents(".SetUpBody").attr("view", "chart-2");
    		salesMapsTeamPKChartObj.resizeMap();
    		if($('#maps-pk-three').hasClass('hide')){
    			salesMapsStyleObj.normalHeight();
    		} else {
    			salesMapsStyleObj.currentHeight();
    		}
    	} else {
    		$(this).parents(".SetUpBody").find(".chart-view-1").show();
    		$(this).parents(".SetUpBody").find(".chart-view-2").hide();
    		$(this).parents(".SetUpBody").attr("view", "chart-1");
    		salesMapsTeamViewChartObj.init(reqOrgId);
    		salesMapsTeamViewChartObj.resizeMap();
    		salesMapsStyleObj.currentHeight();
    	}
    });
    
    /**
	* 维度分析-绘制半圆环比例
	*/
	function drawArc(graphics,x,y,r,b,e,c,w,counterclockwise){
		if(counterclockwise==undefined)counterclockwise=0;
		graphics.beginPath();
		graphics.arc(x,y,r,b,e,counterclockwise);
		graphics.strokeStyle =c;
		graphics.lineWidth = w;
		graphics.stroke();
		graphics.closePath();
	}
	function drawText(graphics,s,f,v,x,y){
		graphics.beginPath();
		graphics.fillStyle=s; 
		graphics.globalAlpha="1"; 
		graphics.textAlign="center"; 
		graphics.font=f; 
		graphics.fillText(v,x, y);//IE不支持 
		graphics.closePath();
	}
	$(window).resize(function () {
        var _page = $('.leftListDiv.selectList').attr('page');
        if(_page == 'page-one'){
        	changePageOneChartsResize();
        }
        if(_page == 'page-two'){
        	changeChartsTwoResize();
        }
        if(_page == 'page-three'){
        	changeChartsThreeResize();
        	searchObj.resize();
        }
        if(_page == 'page-four'){
        	changeChartsFourResize();
        }
    });
	/**项目总览页面重置echart对象*/
    function changePageOneChartsResize(){
    	salesMapObj.chart.resize();
    	teamRankingObj.chart.resize();
    }
    /**维度分析页面重置echart对象*/
    function changeChartsTwoResize() {
    	salesTrendObj.resizeChart();
    	salesTrendObj.resizeGrid();
    }
    /**销售排名地图页面重置echart对象*/
    function changeChartsThreeResize() {
    	salesMapsStyleObj.currentHeight();
    	salesMapsTeamViewChartObj.resizeMap();
    	salesMapsTeamPKChartObj.resizeMap();
    }
    /**销量分析页面重置echart对象*/
    function changeChartsFourResize() {
    	personChangeObj.resizeChart();
    	personSalesObj.resizeChart();
    	personSalesEffectGridObj.resizeGrid();
    }
    /**
     * 维度分析-销售明细-团队/个人标签页切换
     * */
    $("#page-two .leftSetUpBtnDiv").click(function() {
    	var $this = $(this);
    	if($this.hasClass("leftSetUpBtnSelect")) return;
    	$this.parent(".leftSetUpBtn").find(".leftSetUpBtnDiv").removeClass("leftSetUpBtnSelect");
    	$this.addClass("leftSetUpBtnSelect");
    	if ($this.parents(".nav-btn").attr("view") == "team") {
    		$this.parents(".nav-btn").find(".person-view").show();
    		$this.parents(".nav-btn").find(".team-view").hide();
    		$this.parents(".nav-btn").attr("view", "person");
    	} else {
    		$this.parents(".nav-btn").find(".team-view").show();
    		$this.parents(".nav-btn").find(".person-view").hide();
    		$this.parents(".nav-btn").attr("view", "team");
    	}
    	$(".more-search-label").text('更多筛选条件');
    	$(".more-search-label").removeClass('icon-panel-up').removeClass('borderStyle').addClass("icon-panel-down");
    	$(".condition-body").hide();
    });
    
    $(".more-search-label").click(function(){
    	var _this = $(this);
    	if (_this.hasClass("icon-panel-down")) {
    		_this.removeClass("icon-panel-down icon-panel-up");
    		_this.text('精简筛选条件');
    		_this.addClass("icon-panel-up").addClass('borderStyle');
    		$(".condition-body").show();
    	} else {
    		_this.removeClass("icon-panel-down icon-panel-up");
    		_this.text('更多筛选条件');
    		_this.addClass("icon-panel-down").removeClass('borderStyle');
    		$(".condition-body").hide();
    	}
    });
    
    $(".condition-body-clearBtn").click(function () {
    	$(".conditionSearchForm").resetForm();
    });
    //隐藏加载和暂无数据
    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div .loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage' style='line-height : "+ $chart.height() +"px;'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
            $chart.children().show();
        }
    }
    //数据加载中
    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div .loadingmessage').remove();
        $chart.children().hide();
        $chart.prepend("<div class='loadingmessage' style='line-height : "+ $chart.height() +"px;'>数据加载中</div>");
    }
    /**动态设置echarts x轴距离*/
    function setGridX(arrData, optionGrid){
    	var maxVal = Math.max.apply(this, arrData);
    	if(maxVal > 100000000){
    		optionGrid.x = 90;
    	} else if(maxVal > 10000000){
    		optionGrid.x = 80;
    	} else if(maxVal > 1000000){
    		optionGrid.x = 70;
    	} else if(maxVal > 100000){
    		optionGrid.x = 60;
    	}else if(maxVal > 10000){
			optionGrid.x = 50;
		} else if(maxVal > 1000){
			optionGrid.x = 40;
    	} else {
    		optionGrid.x = 30;
    	}
    }
    
});