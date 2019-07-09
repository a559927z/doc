/**
 * 说明：
 * $(self.contentDivId).mapGrid({
 * 		param : {},	//生成列表所需参数
 * 		queryTeamCPGridUrl : '',	//生成列表请求url路径
 * 		imgHref : '',	//点击图片请求url路径
 * 		imgClickCallback : function(param){},	//图片点击后返回函数 param为empId
 * 		infoClickCallback : function(param){},	//盘点报告点击后返回函数 param为empId
 * })
 * */
require([ 'jquery', 'jBootstrapPage', 'inventoryReport' ], function($) {
	var webRoot = G_WEB_ROOT;
	$.fn.mapGrid = function(options){
		var myOpt = $.extend({}, $.fn.mapGrid.defaults, options || {});
		var myTab = function(element, options){
			this.init(element, options);
		}
		myTab.prototype = {
			_defaults : $.fn.mapGrid.defaults,
			titleUlId : 'GridViewTitleId',
	    	contentDivId : 'GridViewContentId',
	    	pagerId: 'PagerId',
	    	pagerCountId: 'PagerCount',
	    	fullTitleUlId : 'FullGridViewTitleId',
	    	fullContentDivId : 'FullGridViewContentId',
	    	fullPagerId: 'FullPagerId',
	    	fullPagerCountId: 'FullPagerCount',
			init : function(element, options){
				$.extend(this, options);
				this.el = element;
				this.options = options;
				this.elId = $(this.el).attr('id');
				this.thisId = '#' + this.elId;
				$(this.el).empty();
//				this._getRequestDataFun();
				this._isFull = this._defaults.isFull;
				if(this._isFined(this.options.isFull)){
					this._isFull = this.options.isFull;
				}
				if(this._isFull){
					this._initFullDivFun();
				} else {
					this._initDivFun();
				}
			},
			_initLoadingFun: function(){
				var _this = this;
				var div = '<div class="div-loading-img"></div>';
				return div;
			},
			_noDataFun: function(){
				var _this = this;
				var div = '<div class="div-nodata">暂无数据</div>';
				return div;
			},
			_initDivFun: function(){
				var _this = this;
				$(_this.el).empty();
				$(_this.el).prepend(_this._initLoadingFun());
				var div = '<div class="view-grid-title">'
						+ '<ul id="' + _this.elId + 'GridViewTitleId"></ul>'
						+ '</div>'
						+ '<div class="view-grid-content" id="' + _this.elId + 'GridViewContentId">'
						+ '</div>'
						+ '<div class="view-grid-pager">'
						+ '<div class="left pager-total">'
						+ '共<span class="pager-total-num" id="' + _this.elId + 'PagerCount">0</span>条'
						+ '</div>'
						+ '<div class="right pager-operate">'
						+ '<ul class="jpagination" id="' + _this.elId + 'PagerId"></ul>'
						+ '</div>' + '</div>';
				$(_this.el).append(div);
				_this._getRequestTitleDataFun();
			},
			_initFullDivFun: function(){
				var _this = this;
				$(_this.el).empty();
				$(_this.el).prepend(_this._initLoadingFun());
				var div = '<div class="view-grid-title">'
					+ '<ul id="' + _this.elId + 'FullGridViewTitleId"></ul>'
					+ '</div>'
					+ '<div class="view-grid-content" id="' + _this.elId + 'FullGridViewContentId">'
					+ '</div>'
					+ '<div class="view-grid-pager">'
					+ '<div class="left pager-total">'
					+ '共<span class="pager-total-num" id="' + _this.elId + 'FullPagerCount">0</span>条'
					+ '</div>'
					+ '<div class="right pager-operate">'
					+ '<ul class="jpagination" id="' + _this.elId + 'FullPagerId"></ul>'
					+ '</div>' + '</div>';
				$(_this.el).append(div);
				_this._getRequestTitleDataFun();
			},
			_getRequestTitleDataFun: function(){
				var _this = this;
				var _url = _this._defaults.queryAbilityForListUrl;
				if(_this._isFined(_this.options.queryAbilityForListUrl)){
					_url = _this.options.queryAbilityForListUrl;
				}
				var _param;
				if(_this._isFined(_this.options.param)){
					_param = _this.options.param;
					$.ajax({
						url : _url,
						type : 'post',
						data : _param,
						async: false,
						success : function(data){
							_this._loadTitleGrid(data);
						},
						error : function(){}
					});
				} else {
					$('.div-loading-img').remove();
				}
			},
			_loadTitleGrid: function(data){
				var _this = this;
				var id;
				if(_this._isFull){
					id = _this.fullTitleUlId;
				} else {
					id = _this.titleUlId;
				}
				$(_this.thisId + id).empty();
				//初始化参数值
				_this._defaults.titleId = '';
				_this._defaults.count = 0;
				_this._defaults.pageNum = 1;
				_this._defaults.pageSize = 12;
				
				var ul = "<li data=''>全部<span>" + data.count + "</span></li>";
				$.each(data.list, function(index, object){
					ul += "<li data='" + object.name + "'>" + object.name + "<span>" + object.conNum + "</span></li>";
				});
				$(_this.thisId + id).append(ul);
				_this._titleBtnsChangeFun(_this.thisId + id);
				_this._defaults.count = data.count;
				_this._getRequestDataFun();
				_this._pagerBtnsChangeFun();
				if(data.count == 0){
					$(_this.el).prepend(_this._noDataFun());
				} else {
					$('.div-nodata').remove();
				}
			},
			_titleBtnsChangeFun: function(id){
				var _this = this;
				$(id +' li:eq(0)').addClass('grid-li-style').siblings().removeClass('grid-li-style');
				$(id + ' li').unbind('click').bind('click', function(){
					$(this).addClass('grid-li-style').siblings().removeClass('grid-li-style');
					var _id = $(this).attr('data');
					var _count = $(this).find('span').text();
					_this._defaults.titleId = _id;
					_this._defaults.count = _count;
					_this._defaults.pageNum = 1;
					_this._getRequestDataFun();
					_this._pagerBtnsChangeFun();
				});
			},
			_pagerBtnsChangeFun: function(){
				var _this = this;
				var pagerCountId, pagerId;
				if(_this._isFull){
					pagerCountId = _this.fullPagerCountId;
					pagerId = _this.fullPagerId;
				} else {
					pagerCountId = _this.pagerCountId;
					pagerId = _this.pagerId;
				}
				var pageSize = _this._defaults.pageSize;
				if(_this._isFined(_this.options.pageSize)){
					pageSize = _this.options.pageSize;
				}
				$(_this.thisId + pagerCountId).html(_this._defaults.count);
				var btns = 0;
				if(_this._defaults.count > 0){
					btns = Math.ceil(_this._defaults.count / pageSize);
					if(btns > 10){
						btns = 10;
					} else if(btns == 1){
						btns = 2;
					}
				}
				$(_this.thisId + pagerId).jBootstrapPage({
					pageSize : pageSize,
					total : _this._defaults.count,
					maxPageButton : btns,
					onPageClicked : function(obj, pageIndex) {
						_this._defaults.pageNum = pageIndex + 1;
						_this._getRequestDataFun();
					}
				});
			},
			_getRequestDataFun : function(){
				var _this = this;
				$(_this.el).prepend(_this._initLoadingFun());
				var _url = _this._defaults.queryTeamCPGridUrl;
				if(_this._isFined(_this.options.queryTeamCPGridUrl)){
					_url = _this.options.queryTeamCPGridUrl;
				}
				var _param;
				if(_this._isFined(_this.options.param)){
					_param = _this.options.param;
					var num = _this.thisId.substr(_this.thisId.length - 1);
					if(_param.arr){
						_param.yearMonths = _param.arr[num];
					}
					_param.titleId = _this._defaults.titleId;
					_param.pageNum = _this._defaults.pageNum;
					_param.pageSize = _this._defaults.pageSize;
					if(_this._isFined(_this.options.pageSize)){
						_param.pageSize = _this.options.pageSize;
					}
					$.ajax({
						url : _url,
						type : 'post',
						data : _param,
						async: false,
						success : function(data){
							$('.div-loading-img').remove();
							_this._loadDataFun(data);
						},
						error : function(){}
					});
//					_this._pagerBtnsChangeFun();
				}
			},
			_loadDataFun : function(data){
				var _this = this;
				var id;
				if(_this._isFull){
					id = _this.fullContentDivId;
				} else {
					id = _this.contentDivId;
				}
				$(_this.thisId + id).empty();
				var _div = "";
				$.each(data, function(index, object){
					var img = object.imgPath != null && object.imgPath != '' ? object.imgPath : webRoot + "/assets/photo.jpg";
					_div += "<div class='grid-img-div left'><div class='left'>"
						+ "<img class='head-pic img-circle span-img' src='" + img + "' data-src='" + img + "' data-id='" + object.empId + "' >"
						+ "</div><div>"
						+ "<div class='grid-div-right'><span class='span-name left'>" + object.userName + "</span>"
						+ "<span class='span-info right' data='" +object.empId+ "'>盘点报告</span></div>"
						+ "<div class='jl-style'><span class='span-organName'>" + object.organName + "</span>"
						+ "<span>/</span>"
						+ "<span class='span-ability'>" + object.rankName + "</span></div>"
						+ "</div><div class='jl-style'>"
						+ "<span>能力等级：</span>"
						+ "<span>" + object.abilityNumberName + "</span><br/>"
						+ "</div><div class='jl-style'>"
						+ "<span>绩效排名：</span>"
						+ "<span>" + object.performanceName + "</span>"
						+ "</div></div>";
				});
				$(_this.thisId + id).append(_div);
				_this._imgClickFun();
				_this._infoClickFun();
			},
			_imgClickFun : function(){
				var _this = this;
				$(_this.el).find('.span-img').unbind('click').bind('click', function(){
					var _empId = $(this).attr('data-id');
					var _callback = _this._imgClickCallback;
					var _optCallback = _this.options.imgClickCallback;
					if(_this._isFined(_optCallback)){
						_callback = _optCallback;
					}
					if(_this._isFunction(_callback)){
						_callback(_empId);
					}
				});
			},
			_infoClickFun: function(){
				var _this = this;
				$(_this.el).find('.span-info').unbind('click').bind('click', function(){
					var _empId = $(this).attr('data');
					var _callback = _this._infoClickCallback;
					var _optCallback = _this.options.infoClickCallback;
					if(_this._isFined(_optCallback)){
						_callback = _optCallback;
					}
					if(_this._isFunction(_callback)){
						_callback(_empId);
					}
				});
			},
			_imgClickCallback : function(param){
				var _defaults = $.fn.mapGrid.defaults;
				var _href = _defaults.imgHref;
				_href = _href + '?empId=' + param + '&rand=' + Math.random();
				window.open(_href);
			},
			_infoClickCallback : function(empId){
				var _this = this;
				var param = {
					empId : empId
				}
				$(_this).inventoryReport(param);
			},
			_isFined : function(data){
				if(data != undefined && data != null && data != ''){
					return true;
				} else {
					return false;
				}
			},
			_isFunction : function(callback){
				return typeof callback == 'function';
			}
		}
		return new myTab(this, myOpt);
	}
	$.fn.mapGrid.defaults = {
		queryAbilityForListUrl: webRoot + '/talentMaps/queryAbilityForList.do',	// 团队能力和绩效趋势图-列表显示-标题列表
		queryTeamCPGridUrl : webRoot + '/talentMaps/queryTeamCPGrid.do',	// 团队能力和绩效趋势图-列表显示
		imgHref : webRoot + '/talentProfile/toTalentDetailView.do',    //跳转到员工详情
		isFull : false,
		titleId : '',	//列表id
		count : 0,	//总记录数
		pageNum : 1,	//默认第一页
		pageSize : 12	//默认一页10条
	}
});