/**
 * 销售看板维度分析-销售明细列表
 * @author malong 2016-12-19
 * 说明：
 * $(self.contentDivId).salesGrid({
 * 		param : {},	//生成列表所需参数
 * 		url : '',	//生成列表请求url路径
 * })
 * */
require([ 'jquery', 'jBootstrapPage','pagination' ], function($) {
	var webRoot = G_WEB_ROOT;
	$.fn.salesGrid = function(options){
		var myOpt = $.extend({}, $.fn.salesGrid.defaults, options || {});
		var myTab = function(element, options){
			this.init(element, options);
		};
		myTab.prototype = {
			_defaults : $.fn.salesGrid.defaults,
	    	pagerId: 'PagerId',
	    	pagerCountId: 'PagerCount',
	    	pageId: '-page',
	    	rowsId: '-rows',
			init : function(element, options){
				$.extend(this, options);
				this.el = element;
				this.options = options;
				this.elId = $(this.el).attr('id');
				this.thisId = '#' + this.elId;
				$(this.el).empty();
				this._initDivFun();
//				this._getRequestDataFun();
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
				$('.' + _this.elId + '-pager').remove();
				$(_this.el).addClass('height540');
				//作为分页div
				var pagerDiv = '<div id="' + _this.elId + 'PagerId" class="' + _this.elId + '-pager model-grid-pagination">'
				+'</div>';
				$(_this.el).after(pagerDiv);
				_this._defaults.count = 0;
				_this._defaults.page = 1;
				_this._defaults.rows = 8;
				_this._getRequestDataFun();
				//分页控件
				$(_this.thisId + this.pagerId).pagination({
					otherparam: options.param,
					'total': _this._defaults.count,
					'pageNum': 1,
					'pageSize': 8,
					'url': _this.options.url,
					uiLoaderFunc: function(data){_this._loadDataFun(data);}
					
				});
			},
			_getRequestDataFun : function(){
				var _this = this;
				$(_this.el).append(_this._initLoadingFun());
				var _url = _this._defaults.url;
				if(_this._isFined(_this.options.url)){
					_url = _this.options.url;
				}
				var _param = _this._defaults.param;
				if(_this._isFined(_this.options.param)){
					_param = _this.options.param;
					_param.page = _this._defaults.page;
					if(_this._isFined(_this.options.param.page)){
						_param.page = _this.options.param.page;
					}
					_param.rows = _this._defaults.rows;
					if(_this._isFined(_this.options.rows)){
						_param.rows = _this.options.rows;
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
				}
				
			},
			_loadDataFun: function(data){
				var _this = this;
				$(_this.el).empty();
				$(_this.el).removeClass('height540');
				var div = "<div class='col-md-12 col-xs-12 model-table-content'></div>";
				$(_this.el).append(div);
				if(_this._isFined(_this.options.model)){
					var num = 0;
					$.each(data.rows, function(ind, obj){
						if(ind % 4 == 0){
							num++;
							var tr = "<div class='col-md-12 model-tr-content tr-" + num +"'></div>";
							$(_this.el).find('.model-table-content').append(tr);
						}
						$(_this.el).find('.tr-' + num).append(_this.options.formatModel(_this.options.model, obj, ind));
						$(_this.el).find('.tr-' + num).children().addClass('col-md-3 padding10');
						_this.options.loadRowComple(_this.options.model, obj, ind);
					});
				}
				$(_this.el).removeClass('height300');
				$('.' + _this.elId + '-pager').removeClass('hide');
				if(data.records == 0){
					$(_this.el).prepend(_this._noDataFun());
					$(_this.el).addClass('height300');
					$('.' + _this.elId + '-pager').addClass('hide');
				} else {
					$('.div-nodata').remove();
					_this._defaults.count = data.records;
				}
			},
			_isFined : function(data){
				if(data != undefined && data != null && data != ''){
					return true;
				} else {
					return false;
				}
			}
		};
		return new myTab(this, myOpt);
	}
	$.fn.salesGrid.defaults = {
		param : {},
		count : 0,	//总记录数
		page : 1,	//默认第一页
		rows : 8	//默认一页8条
	}
});