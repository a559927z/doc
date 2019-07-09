/*
 *@param author htpeng
 */
var isMobile = ('ontouchstart' in document.documentElement);
var clickEvent = isMobile ? 'touchstart' : 'click';
var scrollEvent = isMobile ? 'touchstart touchmove touchend scroll' : 'scroll';
var resizeEvent=isMobile?'orientationchange':'resize';
var barWidth =10;  //默认全局柱状图宽度
(function($) {
	'use strict'
	
	!function() {
		 window.app={
				    size:[],
				    lastOrientation:'', //0 竖屏  1横屏
					init:function(){
						if(this.lastOrientation!=''&&this.lastOrientation==window.orientation){
							return this.size[0];
						}
						window.screenTop=0;
						window.screenScroll=true;
						var width=$(window).width();
						var height=$(window).height();
						var obj={width:width,height:height,orientation:window.orientation};
//						if(Math.abs(window.orientation)==90){
//							//横屏
//							
////							obj.orientation="landscape";
//							obj.orientation=1;
//						}else{
//							this.lastOrientation=0;
////							obj.orientation="portrait";
//							obj.orientation=0;
//						}
						this.lastOrientation=window.orientation;
						this.size.push(obj);
						return obj;
					},getSize:function(){
						console.log(this.size)
						if(this.size.length==1){
							return this.init();
						}else{
							var obj=null;
							var nowOrientation=(Math.abs(window.orientation)==90)?1:0;
							$.each(this.size,function(i,o){
								if(o.orientation=nowOrientation){
									obj=o;
								}
							});
							return obj;
						}
					}
				};
		$(document).ready(function(){
			$("body").attr("onselectstart", "return false");
			window.fixedTop = $("#tabPanel").offset().top;
		//	 window.app.init();
			var scrollApp={
				lastY:0,// 上一次位置
				stopInertiaMove : false, // 是否停止缓动
				lastMoveTime:0,
				lastMoveStart:0,
				inertia:10,
				moveScal:2,
				lastClickTabId:"",
				lock:true,//是否有需要时锁定屏幕
				inertiaTime:10/1000,
				init:function(){
					var self=this;
					this.pageAll=$(".ovfHiden");
					$(window).bind(resizeEvent,function(){
						setTimeout(function(){
							self.reload();
						},100)
					});
					if(!this.pageAll){
						return;
					}
					this.getScroll();
					this.tabPanel();
					this.touchstart();
					this.touchend();
					this.touchmove();
				},reload:function(){
					this.setScreenTop(0);
					$(".fixed-next").removeClass("fixed-next")
				    $(".sticky-fix").removeClass("sticky-fix");
					window.fixedTop = $("#tabPanel").offset().top;

					var scrollHeight=$("#" + this.lastClickTabId).height()+$("#" + this.lastClickTabId).offset().top;
					this.windowHeight = $(window).height();
					if(Math.abs(window.orientation)==90){
						//横屏
						if(this.windowHeight==464){
							this.windowHeight=200;
						}
					}
					//alert(window.orientation+"  "+window.fixedTop+"  :  "+this.windowHeight+"  :  "+scrollHeight)
					this.scrollBottom = scrollHeight - this.windowHeight;
					this.setScrollHeight();
					if(this.$scrollShow)
						this.scrollMove(0);
					this.moveHand();
				},
				touchstart:function(){
					var self=this;
					$(self.pageAll).on("touchstart", function(e) {
						if(self.lock&&!window.screenScroll){
							return;
						}
						var _touch = e.originalEvent.targetTouches[0]; 
						self.lastY= _touch.pageY;
						  var contentTop = ($(self.pageAll).css("top")).replace('px', '');
						  /**
						     * 缓动代码
						     */
						  self.lastMoveStart = self.lastY;
						  self.lastMoveTime = e.timeStamp || Date.now();
						  self.stopInertiaMove = true;
						  if(self.$scrollShow)
						  self.$scroll.show();
						  $(window).scrollTop(0);
					});
				},touchend:function(){
					var self=this;
					$(self.pageAll).on("touchend", function(e) {
						if(self.lock&&!window.screenScroll){
							return;
						}
						  var contentY = ($(self.pageAll).css("top")).replace('px', '');
						    /**
						     * 缓动代码
						     */
						    var nowTime = e.timeStamp || Date.now();
						    var v = (self.lastY - self.lastMoveStart) / ((nowTime - self.lastMoveTime)/1000); //最后一段时间手指划动速度
						    self.stopInertiaMove = false;
						    (function(v, startTime, contentY) {
						        var dir = v > 0 ? -1 : 1; //加速度方向
						        var deceleration = dir*0.2;

						        var duration = Math.abs(v / deceleration); // 速度消减至0所需时间
						        
						        var dist = v * duration/1000 / 2; //最终移动多少
					            if(dist==0){
					            	if(self.$scrollShow)
					            	self.$scroll.hide();
					            	return;
					            }
					            var t=0;
						        function inertiaMove() {
						            if(self.stopInertiaMove) return;
						            v= v + t*deceleration;
						            t+=10;
						            contentY = parseFloat(($(self.pageAll).css("top")).replace('px', ''));
						            // 速度方向变化表示速度达到0了
						           
						            if(v ==0||dir*v>0) {
						            	if(self.$scrollShow)
						            	self.hideScroll();
						                return;
						            }
						            var moveY = parseFloat((v /2 )* self.inertiaTime)*self.moveScal;
							      
							        var currTop=contentY + moveY;
							        if(currTop>0||-currTop>self.scrollBottom){
							        	if(self.$scrollShow)
							            	self.hideScroll();
								    	return;
								    }

							        self.setScreenTop(currTop);
			
						            if(self.$scrollShow)
						            	self.scrollMove(currTop);
						            self.moveHand();
						            setTimeout(inertiaMove, self.inertia);
						        }
						        inertiaMove();
						    })(v, nowTime, contentY);
					});
				},touchmove:function(){
					var self=this;
					$(self.pageAll).on("touchmove", function(e) {
							if(self.lock&&!window.screenScroll){
								return;
							}
						    var _touch = e.originalEvent.targetTouches[0]; 
						    var nowY= _touch.pageY;
						    var moveY = (nowY-self.lastY)*self.moveScal;
						    var contentTop = $(self.pageAll).css("top");
						    self.lastY = nowY;
						    // 设置top值移动content
						    var currTop=parseInt(contentTop) + moveY;
						    /**
						     * 缓动代码
						     */
						    var nowTime = e.timeStamp || Date.now();
						    self.stopInertiaMove = true;
						    if(nowTime - self.lastMoveTime > 300) {
						    	self.lastMoveTime = nowTime;
						    	self.lastMoveStart = nowY;
						    }

						    if(currTop>0||-currTop>self.scrollBottom){
						    	return;
						    }
						    if($(e.target).parents(".ui-jqgrid-btable").length>0){
						    return;	
						    }
						    self.setScreenTop(currTop);
						    if(self.$scrollShow)
						    	self.scrollMove(currTop);
						    self.moveHand();
					});
				},tabPanel:function(){
					var self=this;
					var first=$(".tabPanel").find("div[data-toggle='tab']:first");
					
					if(first){
						this.lastClickTabId=$(first).attr("aria-controls");
						var firstPanel=$("#" + this.lastClickTabId);
						var scrollHeight=firstPanel.height()+firstPanel.offset().top;
						self.windowHeight = $(window).height();
						self.scrollBottom = scrollHeight - self.windowHeight;
						self.setScrollHeight();
					}else{
						self.scrollBottom=0;
						this.hideScroll(true);
					}
					
					$.each($(".tabPanel").find("div[data-toggle='tab']"), function() {
//						$(this).press(
//								{time:100},
//								function(_this,e) {
//									var calssName = $(_this).attr("class").split("-")[0];
//									if($(_this).hasClass(calssName + "-active")){
//										return;
//									}
//									$.each($(_this).parents(".tabPanel").find("div[data-toggle='tab']"),
//											function() {
//												var calssName = $(this).attr("class")
//														.split("-")[0];
//												$(this).removeClass(calssName + "-active")
//														.removeClass(calssName).addClass(
//																calssName);
//												$("#" + $(this).attr("aria-controls"))
//														.removeClass("active")
//											});
//									
//									$(_this).removeClass(calssName).removeClass(
//											calssName + "-active").addClass(
//											calssName + "-active");
//									$("#" + $(_this).attr("aria-controls")).addClass(
//											"active");
//									$(_this).next().removeClass("fixed-next")
//								$(_this).children(".fixed-tab").removeClass("sticky-fix");
//									$(self.pageAll).css("top",0 + 'px')
//									self.moveHand();
//									var scrollHeight=$("#" + $(_this).attr("aria-controls")).height()+$("#" + $(_this).attr("aria-controls")).offset().top;
//									self.windowHeight = $(window).height();
//									self.scrollBottom = scrollHeight - self.windowHeight;
//									self.setScrollHeight();
//									$(_this).trigger("shown.bs.tab", _this);
//								});
						$(this).on(clickEvent,
								function(e) {
									var calssName = $(this).attr("class").split("-")[0];
									if($(this).hasClass(calssName + "-active")){
										return;
									}
									self.lastClickTabId=$(this).attr("aria-controls");
									$.each($(this).parents(".tabPanel").find("div[data-toggle='tab']"),
											function() {
												var calssName = $(this).attr("class")
														.split("-")[0];
												$(this).removeClass(calssName + "-active")
														.removeClass(calssName).addClass(
																calssName);
												$("#" + $(this).attr("aria-controls"))
														.removeClass("active")
											});
									
									$(this).removeClass(calssName).removeClass(
											calssName + "-active").addClass(
											calssName + "-active");
									$("#" + $(this).attr("aria-controls")).addClass(
											"active");
									var scrollTop = $(self.pageAll).offset().top;
									$(this).next().removeClass("fixed-next")
								    $(this).children(".fixed-tab").removeClass("sticky-fix");
									
									
									self.setScreenTop(0);
									
									
									self.moveHand();
									var scrollHeight=$("#" + $(this).attr("aria-controls")).height()+$("#" + $(this).attr("aria-controls")).offset().top;
									self.windowHeight = $(window).height();
									if(Math.abs(window.orientation)==90){
										//横屏
										if(this.windowHeight==464){
											this.windowHeight=200;
										}
									}
									self.scrollBottom = scrollHeight - self.windowHeight;
									self.setScrollHeight();
									if (Math.abs(scrollTop) >= window.fixedTop) {
										$(this).next().addClass("fixed-next");
										$(this).children(".fixed-tab").addClass("sticky-fix");
										if(self.scrollBottom>window.fixedTop){
											//this.scrollMove(window.fixedTop);
											self.setScreenTop(-window.fixedTop);
					
										}else if(self.scrollBottom>0&&self.scrollBottom<window.fixedTop){
											self.setScreenTop(-self.scrollBottom);
										}
									}else{
									//	this.scrollMove(scrollTop);
										if(self.scrollBottom>(-scrollTop)){
											self.setScreenTop(scrollTop);
										}else if(self.scrollBottom>0&&self.scrollBottom<(-scrollTop)){
											self.setScreenTop(-self.scrollBottom);
										}
										
									} 
									
									$(this).trigger("shown.bs.tab", this);
								});
					});
				},moveHand:function(){
					var scrollTop = $(this.pageAll).offset().top;
					$.each($(".fixed"), function() {
						if (Math.abs(scrollTop) >= window.fixedTop+5) {
							if(!$(this).children(".fixed-tab").hasClass("sticky-fix")){
								$(this).next().addClass("fixed-next");
								$(this).children(".fixed-tab").addClass("sticky-fix");
							}

						} else {
							$(this).next().removeClass("fixed-next")
							$(this).children(".fixed-tab").removeClass("sticky-fix");
						}
					});
				},getScroll:function(){
					this.$scroll=$('<div class="scroll">'+
					'<div>'+
					'</div>'+
				    '</div>');
					this.$scroll.hide();
					$("body").append(this.$scroll);
				},scrollMove:function(currTop){
					this.$scroll.children().css("top",-currTop*this.scal);
				},setScrollHeight:function(){
					if(this.scrollBottom >0){
						var scrollHeight=this.windowHeight-this.scrollBottom;
						this.scal=1;
						if(scrollHeight<25){
							scrollHeight=25;
							this.scal=this.windowHeight/this.scrollBottom;
						}
						this.scrollMove(0);
						this.$scroll.children().height(scrollHeight);
						this.showScroll(true);
					}else{
						this.hideScroll(true);
					}
				},hideScroll:function(bool){
					if(bool)
						this.$scrollShow=false;
					this.$scroll.hide();
				},showScroll:function(bool){
					if(bool)
						this.$scrollShow=true;
					this.$scroll.show();
				},setScreenTop:function(scrollTop){
					$(this.pageAll).css("top",scrollTop + 'px')
					window.screenTop=scrollTop;
					$(window).scrollTop(0);
				}
			};
			scrollApp.init();
			
				$(".btn-group").attr("onselectstart", "return false")
//				$(".btn-group").find(".button").on(clickEvent, function(e) {
////					if ($(this).hasClass("condition")) {
////						e.preventDefault();
////						return false;
////					}
//					$.each($(this).parent().children(), function() {
//						$(this).removeClass("condition");
//					});
//
//					$(this).addClass("condition");
//
//				});
			},100);
			
	}();

})(window.jQuery)
var formatterChartTooltip = function(parm) {
	var result = '<div class="chart-tooltip" >';
	result += '<div class="">';
	result += '<div class="chart-tooltip-title">';
	if (!_.isEmpty(parm.title)) {
		result += parm.title;
	}
	result += '</div>';
	result += '</div>';
	if (!_.isEmpty(parm.cols)) {
		$.each(parm.cols, function(i, o) {
			result += '<div class="chart-tooltip-col">';
			result += '<div class="chart-tooltip-name"><label>' + o.name
					+ '</label></div>';
			result += '<div class="chart-tooltip-value"><label>' + o.value
					+ '</label></div>';
			result += '</div>';
		});
	}

	result += '</div>';
	return result;
}

function showEmptyTip($targetDom) {
	var recode=$targetDom;
	$targetDom = $targetDom.parent();
	// 如果是显示状态
	if ($targetDom.find('.empty-tip').length != 0) {
		return;
	}
	var domHeight = $targetDom.height() || 100;
	recode.hide();
	var emptyTipStyle = 'height:' + domHeight + 'px;line-height:' + domHeight
			+ 'px;';
	$targetDom.append('<div class="empty-tip" style="' + emptyTipStyle
			+ '">暂无数据</div>');
}

function removeEmptyTip($targetDom) {
	$targetDom = $targetDom.parent();
	$targetDom.find('.empty-tip').remove();
	$targetDom.children().show();
}



function appendLegend($targetDom,data){
	$targetDom = $targetDom.parent();
	$targetDom.find('.legend-tip').remove();
	var domHeight = $targetDom.height() || 100;
	var legend=$('<div class="legend-tip"></div>');

	legend.append(data);
	$targetDom.append(legend);
	
	
}
/*******************************************************************************
 * 格式化浮点数字保留2位小数,并去除多余空格
 * 
 * @param val
 */
function formatFloat(val) {
	return parseFloat((val).toFixed(2));
}

/**
 * 格式化 echart Y轴 LABEL
 * 
 * @param val
 * @returns {String}
 */
function formatAxis(val) {
	var result = val;
	if (val.length > 7) {
		result = val.substring(0, 3) + ".."
				+ val.substring(val.length - 3, val.length)
	}
	return result;
}

// 纵坐标为组织机构的默认Grid
var organGrid={
    borderWidth: 0,
    x:73,
    y:20,
    x2:40,
    y2:50
};


function scalChartHeight(chart,len){
	if(len>6){
		$('#' + chart.chartId).parent().css("height",len*30)
		$('#' + chart.chartId).css("height",len*30)
		chart.resize();
	}
}
