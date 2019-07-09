/*
 *@param author htpeng
 */
var echartsVersion=2.7;
var mobileType = navigator.userAgent.toLowerCase();	
var mobileIos =true;
	if (/iphone|ipad|ipod/.test(mobileType)) {
		mobileIos= true; 		
	} else if (/android/.test(mobileType)) {
		mobileIos=false; 		
	}
	
var isMobile = ('ontouchstart' in document.documentElement);
var clickEvent = isMobile ? 'touchstart' : 'click';
var clickOverEvent = isMobile ? 'touchend' : 'click';
var scrollEvent = isMobile ? 'touchstart touchmove touchend scroll' : 'scroll';
var resizeEvent=isMobile?'orientationchange':'resize';
var barWidth =15;  //默认全局柱状图宽度
var barColor ="#007aff";  //默认全局柱状图颜色
var lineColor ="#ffc000";  //默认全局条形图颜色
var fontFamily='Applied Font Regular,Applied Font';//chart默认字体
var animation=mobileIos;//chart是否显示动画

/**
 * 饼图颜色
 */

var colorPie=["#007aff","#15a4fa","#01d0d2","#01d286","#99cc67","#fec047","#ff5b5a","#eb5899","#c830ca","#6558eb","#00FF7F","#EEEE00"];
//var colorPie=["#0b98e0","#00bda9","#4573a7","#92c3d4","#de6e1b","#ff0084","#af00e1","#8d55f6","#6a5888","#2340f3","#00FF7F","#EEEE00"];
var measurement = "万元";
var  scrollApp;
(function($) {
	'use strict'
	!function() {
		 window.app={
				    size:[],
				    lastOrientation:'', //0 竖屏  1横屏
					init:function(){
						this.initChart();
						if(this.lastOrientation!=''&&this.lastOrientation==window.orientation){
							return this.size[0];
						}
						window.screenTop=0;
						window.screenFull=false;
						window.screenScroll=true;
						var width=$(window).width();
						var height=getWindowHeight();
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
					},initChart:function(){
						var width=$(window).width();
						var chartPanels=$(".tab-content").children(".tab-panel").find(".chartPanel");
						var charts=$(chartPanels).children(".chart");
//						$(chartPanels).width(width-25);
//						$(charts).width(width-25);
						$(chartPanels).width(width);
						$(charts).width(width);
						$(charts).height($(chartPanels).height());
					}
				};
		 window.app.init();
		$(document).ready(function(){
			$("body").attr("onselectstart", "return false");
			window.fixedTop=155;
			if($(".tabPanel").offset()){
				window.fixedTop = $(".tabPanel").offset().top;
			}
			
			 scrollApp={
				lastY:0,// 上一次位置
				stopInertiaMove : false, // 是否停止缓动
				lastMoveTime:0,
				lastMoveStart:0,
				inertia:10,
				moveScal:2,
				heightRectify:0,      //页面高度矫正
				heightExtendRectify:20,      //页面扩展高度
				touchTable:false,
				lastClickTabId:"",
				lock:true,//是否有需要时锁定屏幕
				inertiaTime:10/1000,
				pageHeightInterval:null,
				pageHeightIntervalCount:0,
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
					window.fixedTop = $(".tabPanel").offset().top;

					var scrollHeight=$("#" + this.lastClickTabId).height()+$("#" + this.lastClickTabId).offset().top;
					this.windowHeight = getWindowHeight()
					if(Math.abs(window.orientation)==90){
						//横屏
						if(this.windowHeight==464){
							this.windowHeight=200;
						}
					}
					this.scrollBottom = scrollHeight - this.windowHeight;
					this.setScrollHeight();
					if(this.$scrollShow)
						this.scrollMove(0);
					this.moveHand();
				},
				touchstart:function(){
					var self=this;
					$(self.pageAll).on("touchstart", function(e) {
						if(!self.valid(e)){
							return;
						}
						var _touch = e.originalEvent.targetTouches[0]; 
						self.lastY= _touch.pageY;
						self.lastMoveStart = self.lastY;
						self.lastMoveTime = e.timeStamp || Date.now();
			
						  var contentTop = ($(self.pageAll).css("top")).replace('px', '');
						  /**
						     * 缓动代码
						     */
						 
						  self.stopInertiaMove = true;
						  if(mobileIos)
						  $(window).scrollTop(0);
					});
				},touchend:function(){
					var self=this;
					$(self.pageAll).on("touchend", function(e) {
						if(!self.valid(e)){
							return;
						}
						  var contentY = ($(self.pageAll).css("top")).replace('px', '');
						    /**
						     * 缓动代码
						     */
						    var nowTime = e.timeStamp || Date.now();
						    var v = (self.lastY - self.lastMoveStart) / ((nowTime - self.lastMoveTime)/1000); //最后一段时间手指划动速度
						    if(v>0&&self.touchTable){
						    	return;
						    } 
						    self.stopInertiaMove = false;
						    (function(v, startTime, contentY) {
						        var dir = v > 0 ? -1 : 1; //加速度方向
						        var deceleration = dir*0.2;

						        var duration = Math.abs(v / deceleration); // 速度消减至0所需时间
						        
						        var dist = v * duration/1000 / 2; //最终移动多少
					            if(dist==0){
					            	if(self.$scrollShow)
					            	self.hideScroll();
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
						if(!self.touchTable){
							e.preventDefault();
						}
						if(!self.valid(e)){
							return;
						}
						 var _touch = e.originalEvent.targetTouches[0]; 
						    var nowY= _touch.pageY;
						    var moveY = (nowY-self.lastY)*self.moveScal;

						    if(moveY>0&&self.touchTable){
						    	return;
						    } 
						    if(self.$scrollShow&&self.$scroll.is(":hidden"))
								  self.showScroll();
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
					
					$.each($(".tabPanel"),function(){
						var div=$("<div class='arrow'></div>")
						$(this).children().append(div);
					});
					self.getInitHeight();
					$.each($(".tabPanel").find("div[data-toggle='tab']"), function() {
						var _=this;
						$.each($(_).attr("class").split(" "),function(i,o){
							if(o.indexOf("tab")==0){
								$(_).attr("sign_class",o.split("-")[0]);
							}
						});
						$(this).children(".icon").on(clickEvent,
								function(e) {
									var tab=$(this).parent();
									var className=$(tab).attr("sign_class");
									
									if($(tab).hasClass(className + "-active")){
										return;
									}
									self.lastClickTabId=$(tab).attr("aria-controls");
									$.each($(tab).parents(".tabPanel").find("div[data-toggle='tab']"),
											function() {
												var className=$(this).attr("sign_class");
												$(this).removeClass(className + "-active")
														.removeClass(className).addClass(
																className);
												$(this).removeClass("active")
												$("#" + $(this).attr("aria-controls"))
														.removeClass("active")
									});
									
									$(tab).removeClass(className).removeClass(
											className + "-active").addClass(
													className + "-active");
									$(tab).removeClass("active").addClass("active");
									$("#" + $(tab).attr("aria-controls")).addClass(
											"active");
									self.clickTab(tab);
									$(tab).trigger("shown.bs.tab", tab);
								});
					});
					$.each($(".tabPanel-interior").find("div[data-toggle='tab']"), function() {
						$(this).on(clickEvent,
								function(e) {
									if($(this).hasClass("active")){
										return;
									}
									$.each($(this).parents(".tabPanel-interior").find("div[data-toggle='tab']"),
											function() {
												$(this).removeClass("active");
												$("#" + $(this).attr("aria-controls")).removeClass(
												"active");
											});
									$(this).removeClass("active").addClass("active");
									$("#" + $(this).attr("aria-controls")).addClass(
									"active");
									if(self.pageHeightInterval!=null){
										window.clearInterval(self.pageHeightInterval);
										self.pageHeightInterval=null;
									}
									var scrollTop = $(self.pageAll).offset().top;
									var tabPanel=$("#" + $(this).attr("aria-controls")).parents(".tab-panel");
									if(tabPanel.length==0){
										tabPanel=$("#" + $(this).attr("aria-controls"));
									}
									var contentTop = $(self.pageAll).css("top");
									var currTop=parseInt(contentTop) ;
									var scrollHeight=$(tabPanel).height()+$(tabPanel).offset().top-currTop;
									scrollHeight=self.rectify(scrollHeight,tabPanel);
									self.windowHeight =getWindowHeight();
									if(Math.abs(window.orientation)==90){
										//横屏
										if(this.windowHeight==464){
											this.windowHeight=200;
										}
									}
									self.scrollBottom = scrollHeight - self.windowHeight;
									self.setScrollHeight();
									$(this).trigger("shown.bs.tab", this);
								});
					});              
				},moveHand:function(){
					var scrollTop = $(this.pageAll).offset().top;
					$.each($(".fixed"), function() {
						if (Math.abs(scrollTop) >= window.fixedTop) {
							if(!$(this).children(".fixed-tab").hasClass("sticky-fix")){
								$(this).next().children(".active").addClass("fixed-next");
								$(this).children(".fixed-tab").addClass("sticky-fix");
							}

						} else {
							$(this).next().children(".active").removeClass("fixed-next")
							$(this).children(".fixed-tab").removeClass("sticky-fix");
						}
					});
				},getScroll:function(){
					this.$scroll=$('<div class="scroll">'+
					'<div>'+
					'</div><div></div>'+
				    '</div>');
					this.$scroll.hide();
					$("body").append(this.$scroll);
				},scrollMove:function(currTop){
					this.$scroll.children().css("top",-currTop*this.scal);
				},setScrollHeight:function(){
					if(this.scrollBottom >0){
						var scrollHeight=this.windowHeight-this.scrollBottom;
						this.scal=1;
						if(scrollHeight<50){
							this.scal=(this.windowHeight-50)*1.0/this.scrollBottom;
							scrollHeight=50;
						}
						var contentTop = $(self.pageAll).css("top");
						var currTop=parseInt(contentTop) ;
						this.scrollMove(currTop);
						this.$scroll.children().height(scrollHeight);
						this.$scrollShow=true;
						//this.showScroll(true);
					}else{
						this.$scrollShow=false;
						//this.hideScroll(true);
					}
				},hideScroll:function(){
					this.$scroll.stop(true,true).hide(200);
				},showScroll:function(){

					this.$scroll.stop(true,true).show(200);
				},setScreenTop:function(scrollTop){
					$(this.pageAll).css("top",scrollTop + 'px')
					window.screenTop=scrollTop;
					$(window).scrollTop(0);
				},valid:function(e){
					this.touchTable=$(e.target).is('td')||$(e.target).parents("td").length>0;
					return !((this.lock&&!window.screenScroll));
				},rectify:function(height,el){
					if($(el).find("table").length>0)
						return height-this.heightRectify;
					return height;
				},getInitHeight:function(){
					var self=this;
					this.pageHeight=$(this.pageAll).height()+this.heightExtendRectify;
					this.windowHeight = getWindowHeight();
					if(Math.abs(window.orientation)==90){
						//横屏
						if(this.windowHeight==464){
							this.windowHeight=200;
						}
					}
					this.scrollBottom = this.pageHeight - this.windowHeight;
					self.setScrollHeight();
					this.pageHeightInterval=setInterval(function(){
						self.pageHeightIntervalCount++;
						var pageHeight=$(self.pageAll).height()+self.heightExtendRectify;
						var stop=false;
						if(pageHeight>self.pageHeight){
							stop=true;
							self.pageHeight=pageHeight;
							self.scrollBottom = self.pageHeight - self.windowHeight;
							self.setScrollHeight();
						}else if(self.pageHeightIntervalCount>20){
							stop=true;
						}
						if(stop){
							window.clearInterval(self.pageHeightInterval);
							self.pageHeightInterval=null;
						}
					}, 100);
				},refreshHeight:function(){
					var self=this;
					this.pageHeight=$(this.pageAll).height()+this.heightExtendRectify;
					this.windowHeight = getWindowHeight();
					if(Math.abs(window.orientation)==90){
						//横屏
						if(this.windowHeight==464){
							this.windowHeight=200;
						}
					}
					this.scrollBottom = this.pageHeight - this.windowHeight;
					self.setScrollHeight();
				},clickTab:function(tab){
					var self=this;
					if(self.pageHeightInterval!=null){
						window.clearInterval(self.pageHeightInterval);
						self.pageHeightInterval=null;
					}
					var scrollTop = $(self.pageAll).offset().top;
					var tabPanel=$(tab).parents(".fixed");
					self.setScreenTop(0);
					self.moveHand();
					var scrollHeight=$("#" + $(tab).attr("aria-controls")).height()+$("#" + $(tab).attr("aria-controls")).offset().top;
					scrollHeight=self.rectify(scrollHeight,$("#" + $(tab).attr("aria-controls")));
					self.windowHeight = getWindowHeight();
					if(Math.abs(window.orientation)==90){
						//横屏
						if(this.windowHeight==464){
							this.windowHeight=200;
						}
					}
					self.scrollBottom = scrollHeight - self.windowHeight;
					self.setScrollHeight();
					if (Math.abs(scrollTop) >= window.fixedTop) {
						if(self.scrollBottom>window.fixedTop){
							self.setScreenTop(-window.fixedTop);
						}else if(self.scrollBottom>0&&self.scrollBottom<window.fixedTop){
							self.setScreenTop(-self.scrollBottom);
						}
					}else{
						$(tabPanel).next().children(".active").removeClass("fixed-next")
					    $(tabPanel).children(".fixed-tab").removeClass("sticky-fix");
						if(self.scrollBottom>(-scrollTop)){
							self.setScreenTop(scrollTop);
						}else if(self.scrollBottom>0&&self.scrollBottom<(-scrollTop)){
							self.setScreenTop(-self.scrollBottom);
						}
					} 
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
//			},100);
		});
			
	}();

})(window.jQuery)


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



/**
 * 默认添加图例
 * @param $targetDom
 * @param data
 */
function appendLegend($targetDom,data){
	$targetDom = $targetDom.parent().parent();
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
    x:70,
    y:20,
    x2:40,
    y2:50
};
var defaultGrid ={
    borderWidth: 0,
    x: 55,
    y: 40,
    x2: 20,
    y2: 40
};
var defaultDoubleGrid ={
    borderWidth: 0,
    x:50,
    y:40,
    x2:50,
    y2:40
}
var axisLine={
		 lineStyle: {
             color: '#BEBEBE',
             width: 1
         }	
};
var splitLine={
	show:true,
	lineStyle:{
		color: '#d7d7d7',
		width: 1,
		type: 'solid',
		shadowOffsetX: 0,
		shadowOffsetY: 0,
		},
}
/**
 * 动态计算chart高度
 * @param chart
 * @param len
 */
function scalChartHeight(chart,len){
	if(len>6){
		$('#' + chart.chartId).parent().css("height",len*30)
		$('#' + chart.chartId).css("height",len*30)
		chart.resize();
		refreshWindowHeight();
	}
}

function loading(chartId){
	  var recode= $("#" + chartId);
    var $targetDom = recode.parent();

	// 如果是显示状态
	if ($targetDom.find('.loading-tip').length != 0) {
		return;
	}
	var domHeight = $targetDom.height() || 100;
	recode.hide();
	var loadTipStyle = 'height:' + domHeight + 'px;line-height:' + domHeight
			+ 'px;';
	$targetDom.append('<div class="loading-tip" style="' + loadTipStyle
			+ '">数据加载中</div>');
}

function loaded(chartId){
   var $targetDom = $("#" + chartId).parent();
   $targetDom.find('.loading-tip').remove();
   $targetDom.children().show();
}
/**
 * 设置按钮颜色
 * @param _t
 */
function setBtnClass(_t){
	 $.each($(_t).parent().children(), function() {
			$(this).removeClass("condition");
		 });
		$(_t).addClass("condition");
};

/**
 * 默认饼图的option
 * @param options
 * @returns {___anonymous17055_18039}
 */
function getDefaultPieOption(options){
	var defaultOption={
			name:'',
			toroidal:false,//是否是环型
			radius:'80%',
			color:colorPie,
			showLine:false,
			formatter:'',
	};
	if(typeof options=="string"){
		defaultOption.name=options;
	}else if(typeof options=="object"){
		jQuery.extend(defaultOption,options);
	}
	if(defaultOption.toroidal==true){
		defaultOption.radius=['60%','80%'];
	}
	var defaultPieOption={
			calculable: false,
	        color: defaultOption.color,
	        animation:animation,
	        series: [
	            {
	                clickable: false,
	                name: defaultOption.name,
	                type: 'pie',
	                radius: defaultOption.radius,
	                center: ['50%', '50%'],
	                data: [],
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: defaultOption.showLine,
	                            formatter: defaultOption.formatter
	                        },
	                        labelLine: {
	                        	show: defaultOption.showLine,
	                        	length: 10
	                        }
	                    },
	                    emphasis : {
	                        label : {
	                            show : false,
	                        }
	                    }
	                }
	            }
	        ]
	}
	return defaultPieOption;
}

/**
 * 默认仪表盘的option
 * @param options
 * @returns {___anonymous17055_18039}
 */
function getDefaultGaugeOption(options){
	var defaultOption={
			name:'',
			showLine:false,
			formatter:'',
	};
	if(typeof options=="string"){
		defaultOption.name=options;
	}else if(typeof options=="object"){
		jQuery.extend(defaultOption,options);
	}
	var defaultGaugeOption={
            toolbox: false,
            animation:animation,
            series: [
                {
                    name: defaultOption.name,
                    type: 'gauge',
                    center : ['50%', '80%'],    // 默认全局居中
                    startAngle: 180,
                    endAngle: 0,
                    radius : 80,
                    clockwise:false,
                    pointer: {
                        width:2,
                        length: '90%',
                        color: '#61bb33'
                    },
                    max: 25,
                    splitNumber: 0,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.3, '#61bb33'], [0.7, '#ffc000'], [1, '#ff3232']],
                            width: 2
                        }
                    },
                    axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                        show: true,
                        formatter: function (v) {
                            if (v == 0) return '';
                            return Math.round(v);
                        },
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            color: '#333'
                        }
                    },
                    data: [{value: 0}]
                }
            ]
        }
	return defaultGaugeOption;
}
/**
 * 默认仪图例
 * @param options
 * @returns {___anonymous17055_18039}
 */
function getDefaultLegend(options){
	var data=[];
	if(typeof options =="object"){
		if(Object.prototype.toString.call(options) === '[object Array]'){
			data=options;
		}else{
			data.push(options);
		}
	}else{
		data.push(options);
	}

	var defaultLegend={
		    data: data,
		    show:true,
		    selectedMode:false,
		    itemWidth:10,
		    itemHeight:11,
		    y: 'top',
		    x: 'right',
		    textStyle: {
		    	fontSize: 12,
		    	fontStyle: 'normal',
		    	fontWeight: 'normal',

		    	},

		}
	return defaultLegend;
}

function refreshWindowHeight(){
	scrollApp.refreshHeight();
}

function getWindowHeight(){
	var windowHeight = $(window).height();
	if(windowHeight==0||windowHeight==298){
		windowHeight=window.screen.height-100;
	}
	return windowHeight;
}