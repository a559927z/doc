/**
 * 该js为盘点报告-关键人才添加
 * 传入参数为：empId,empName
 * */
require([ 'jquery', 'messenger' ], function($){
	var webRoot = G_WEB_ROOT;
	var win = top != window ? top.window : window;
	
	$.fn.keyTalentsAdd = function(options){
		var _defaults = $.fn.keyTalentsAdd.defaults;
		var opt = $.extend({}, _defaults, options || {});
		var add = function(element, options){
			this.init(element, options);
		}
		var modalDiv = '<div class="modal fade popup-modal" id="addDialog" role="dialog"'
				+ 'aria-labelledby="myModalLabel" aria-hidden="true">'
				+ '<div class="modal-dialog">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'
//				+ '<h4 class="modal-title">添加关键人才</h4>'
				+ '<div class="modal-header-text">添加关键人才</div>'
				+ '</div>'
				+ '<div class="modal-body page-content">'
				+ '<div class="row padding10">'
				+ '<div class="col-xs-3 col-sm-3 colnopadding margintop5 text-right">姓名：</div>'
				+ '<div class="col-xs-9 col-sm-9 colnopadding" id="txtKey"></div>'
				+ '</div>'
				+ '<div class="row padding10">'
				+ '<div class="col-xs-3 col-sm-3 colnopadding margintop5 text-right">人才分类：</div>'
				+ '<div class="col-xs-9 col-sm-9 colnopadding">'
				+ '<select id="talentCategory" class="form-control"></select>'
				+ '</div>'
				+ '</div>'
				+ '</div>'
				+ '<div class="modal-footer">'
				+ '<button id="btnAddTalent" type="button" class="btn btn-primary">确定</button>'
				+ '<button id="btnCancelTalent" type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
				+ '</div>' + '</div>' + '</div>' + '</div>';
		$(document.body).append(modalDiv);
		add.prototype = {
			modalId : '#addDialog',
			nameId : '#txtKey',
			categoryId : '#talentCategory',
			okId : '#btnAddTalent',
			cancelId : '#btnCancelTalent',
			init : function(element, options){
				$.extend(this, options);
				this.el = element;
				this.options = options;
				this._defaults = _defaults;
				this._inClickFun();
			},
			_inClickFun: function(){
				var _this = this;
				var flag = false;
//				var _isClick = _this.options.isClick;
//				if(_isClick != undefined){
//					flag = _isClick;
//				}
				if(flag){
					$(_this.el).click(function(){
						_this._modalFun();
					});
				} else {
					_this._modalFun();
				}
			},
			_modalFun: function(){
				var _this = this;
				$(_this.modalId).modal('show');
				$(_this.modalId).on('shown.bs.modal', function() {
					_this._getCategoryData();
					_this._addTalent();
					_this._closeModal();
				});
				$(_this.modalId).on('hidden.bs.modal', function() {
					$(_this.modalId).off();
				});
			},
			_getCategoryData: function(){
				var _this = this;
				$.post(_this._defaults.category, function (data) {
	                _this._categorySelectInit(data);
	            });
			},
			_categorySelectInit: function (list) {
				var _this = this;
	            var html = "";
	            $.each(list, function (i, item) {
	                html += "<option value='" + item.id + "'>" + item.name + "</option>";
	            });
	            $(_this.categoryId).html(html);
	        },
			_addTalent: function () {
				var _this = this;
				var empId = _defaults.empId, empName = _defaults.empName;
				if(isFined(options.empId)){
					empId = options.empId;
				}
				if(isFined(options.empName)){
					empName = options.empName;
				}
				$(_this.nameId).html(empName);
	            $(_this.okId).unbind('click').bind('click', function () {
	                var typeId = $(_this.categoryId).val();
	                if (typeId == "" || typeId == null) {
	                    showErrMsg("请选择人才分类！")
	                } else {
	                    $.post(_defaults.addKeyTalent, {empId: empId, keyTalentTypeId: typeId}, function (data) {
	                        $(_this.modalId).modal('hide');
	                        if (!data.type) {
	                            showErrMsg(data.msg);
	                        }
	                        var param = {
	                        	isKeyTalent : true
	                        }
	                        var _callback, _optCallback = options.isKeyTalentCallback;
	    					if(isFined(_optCallback)){
	    						_callback = _optCallback;
	    					}
	    					if(_this._isFunction(_callback)){
	    						_callback(param);
	    					}
	                    });
	                }
	            });
	        },
	        _closeModal: function(){
	        	var _this = this;
	        	$(_this.cancelId).unbind('click').bind('click', function(){
	        		$(_this.modalId).modal('hide');
	        	});
	        },
	        _isFunction : function(callback){
				return typeof callback == 'function';
			}
		}
		function isFined(param){
			if(param != undefined && param != null && param != ''){
				return true;
			} else {
				return false;
			}
		}
		return new add(this, opt);
	}
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
	$.fn.keyTalentsAdd.defaults = {
		addKeyTalent: webRoot + "/keyTalents/addKeyTalent",//添加关键人才
		category: webRoot + "/keyTalents/getKeyTalentTypePanel"//分类
	}
})