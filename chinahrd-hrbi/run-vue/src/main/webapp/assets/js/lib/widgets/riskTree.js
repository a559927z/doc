/**
 * 离职风险树
 */
define(['jquery'], function ($) {
    $.fn.extend({
        riskTree: function (options) {
        	var self = this;
        	jQuery.extend(self._options, options);
        	var treeData = self.transData(options.data);
        	self._createSubDom();
        	self._createTree(treeData,true);
        	self.riskTreeDom.html(self._html);
        	//不延迟的话，无法正确获得正确高度
        	setTimeout(function(){
        		self._initLineCanvas();
        		self.LineShape = require('zrender/shape/Line');
        		self.connectLine($(self).find('.risk-tree').children(),true);
        		self.zr.render();
        		if(options.hasSelect){
        			self.bindSelectEvent();
        		}
        	},100);
        },
        _html : '',
        _options : {
	        //改变参数的属性
	        key : {
	        	id : 'id',
	        	pid : 'pid',
	        	name : 'name',
	        	riskFlag : 'riskFlag'
	        },
	        hasSelect : false,//是否有下拉选择框
	        topText : '离职风险评估',
	        hasTopText : false,
	        data : null
	    },
	    flagItemArr : [
           {index:0,color:'gray',text:'未评估'},
           {index:1,color:'red',text:'红灯'},
           {index:2,color:'yellow',text:'黄灯'},
           {index:3,color:'green',text:'绿灯'}
        ],
        //下拉框
        getSelectTpl : function(riskFlag){
        	var self = this;
        	var selectHtml = '<select class="form-control pull-right">';
        	$.each(self.flagItemArr,function(i,item){
        		var selected = riskFlag == item.index ? 'selected' : '';
        		selectHtml += '<option value="' + item.index + '" ' + selected + '>'+item.text+'</option>';
        	});
        	return selectHtml+'</select>';
        },
        _createSubDom : function(){
        	var self = this;
        	var container = $(this);
        	$(container).css('position','relative');
			var conWidth = container.width() + 'px';
			var cssStyle = {width : conWidth};
			self.lineCanvasDom = document.createElement('div');
			$(self.lineCanvasDom).addClass('line-canvas').css(cssStyle);
			self.riskTreeDom = $('<div>',{'class' : 'risk-tree','css' : cssStyle});
			$(container).empty().append(self.lineCanvasDom).append(self.riskTreeDom);
        },
        //所有节点渲染完成后，再来指定画布的高度
        _initLineCanvas : function(){
        	var self = this
        	var treeHeight = self.riskTreeDom.height() + 'px';
        	$(self.lineCanvasDom).css('height',treeHeight);
        	var zrender = require('zrender');
        	self.zr = zrender.init(self.lineCanvasDom);
        	self.zr.clear();
        },
        /**
         * @param data 数据
         * @param isTopNode 是否顶级节点。（若为顶级节点，圆圈是大号）
         */
        _createTree : function(data,isTopNode){
        	var self = this;
			$.each(data,function(i,item){
				self._html += '<div class="tree-lv">';
				self._html += self.getPnodeTpl(item,isTopNode);
				if(item.children){
					self._html += '<div class="sub-nodes">';
					self._createTree(item.children);
					self._html += '</div>';
				}
				self._html += '</div>'
			});
		},
		getPnodeTpl : function(item,isTopNode){
			var self = this;
			var bigText = (isTopNode)?'big-risk-flag ' : '';
			var riskName = '';
			if(isTopNode){
				riskName = (self._options.hasTopText) ? self._options.topText : '';
			}else{
				riskName = item.name;
			}
			//ex : (risk-flag big-risk-flag red), (risk-flag red)
			var riskFlagClass = 'img-circle risk-flag '+ bigText + this.flagItemArr[(item.riskFlag||0)].color ;
			return '<div class="p-node ">' +
						'<input type="hidden" value="' + item.id + '">'+
						'<span class="' + riskFlagClass + '"></span>' +
						'<label>'+ riskName +'</label>' +
						this.getSelectDom(item.riskFlag) +
					'</div>';
		},
		getSelectDom : function(riskFlag){
			return (this._options.hasSelect) ? this.getSelectTpl(riskFlag) : '';
		},
		//将数据组装成树结构
		transData : function(a){
			var self = this;
			var r = [], hash = {}, id = self._options.key.id, pid = self._options.key.pid, children = 'children', i = 0, j = 0, len = a.length;    
		    for(; i < len; i++){    
		        hash[a[i][id]] = a[i];    
		    }    
		    for(; j < len; j++){    
		        var aVal = a[j], hashVP = hash[aVal[pid]];    
		        if(hashVP){    
		            !hashVP[children] && (hashVP[children] = []);    
		            hashVP[children].push(aVal);    
		        }else{    
		            r.push(aVal);    
		        }    
		    }    
		    return r;    
		},
		//画节点之间的连线
		connectLine : function(treeChildren){
			var self = this;
			var topXStart = 0,topYStart=0;
			$.each(treeChildren,function(i,item){
				var $dom= $(item);
				var flagDom = $dom.find('.risk-flag').eq(0);
				var xStart,yStart,xEnd,yEnd;
				if($dom.hasClass('sub-nodes') || $dom.hasClass('tree-lv')){
					self.connectLine($dom.children());
				}
				if($dom.hasClass('p-node')){
					topXStart = self.getXPosition(flagDom);
					topYStart = self.getYPosition(flagDom);
				}
				if(i < treeChildren.length - 1 || $dom.hasClass('tree-lv') || $dom.hasClass('p-node')){
					return true;
				}
				//一个递归完成时
				//是否有多个子节点。若有多个，竖线的终点为最后一个riskDom的坐标，并且需画横线
				var multiChild = $dom.children().length > 1;
				var domChildren = $dom.children();
				$.each(domChildren,function(i,item){
					var childFlag = $(item).find('.risk-flag').eq(0)
					//画横线
					var curChildY = self.getYPosition(childFlag)
					self.drawLine(topXStart,curChildY,self.getXPosition(childFlag),curChildY);
				});
				//当前层级的最后一个预警灯的节点
				var lastFlagDom = domChildren.last().find('.risk-flag').eq(0);
				//画竖线
				self.drawLine(topXStart,topYStart,topXStart,self.getYPosition(lastFlagDom));
			});
			
		},
		getXPosition : function($dom){
			return $dom.position().left + $dom.outerWidth()/2;
		},
		getYPosition:function($dom){
			return $dom.position().top + $dom.outerHeight()/2
		},
		// 绘画
		drawLine : function(xStart,yStart,xEnd,yEnd){
			// 直线
			this.zr.addShape(new this.LineShape({
			    style : {
			        xStart : xStart,
			        yStart : yStart,
			        xEnd : xEnd,
			        yEnd : yEnd,
			        strokeColor : '#999',
			        lineWidth : 1,
			        lineType : 'solid'    // default solid
			    },
			    hoverable : false   // default true
			}));
		},
		//绑定下拉框事件
		bindSelectEvent : function(){
			var self = this;
			$(self).find('select').change(function(){
				var flagColor = self.flagItemArr[$(this).val()].color;
				$(this).siblings('.risk-flag').removeClass('red green gray yellow').addClass(flagColor);
			});
		}
    });
});
