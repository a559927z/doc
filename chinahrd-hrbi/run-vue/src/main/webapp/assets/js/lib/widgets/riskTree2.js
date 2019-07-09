/**
 * 离职风险li
 */
define(['jquery','echarts','echarts/chart/scatter','bootstrap','messenger'], function ($,echarts) {
    var webRoot = G_WEB_ROOT;
	var urls = {
	        empBaseInfoUrl: webRoot + "/common/getEmpBaseInfo.do",	//获取员工基本信息
			empRiskDetailUrl: webRoot + "/dismissRisk/getEmpRiskDetail.do",	//获取员工离职风险信息
			empAllRiskDetailUrl: webRoot + "/dismissRisk/getEmpAllRiskDetail.do",	//获取员工历史离职风险信息
			addRiskUrl: webRoot + "/common/addRisk.do",	//评估离职风险信息
			getEmpRiskDefaultDetailUrl:webRoot + "/dismissRisk/getEmpRiskDefaultDetail.do",	//默认离职风险信息
			};
	var defaultPic='assets/img/base/u-default.png';

	var head= '<div class="base-info">'+
						'<!--上面头像-->'+
						'<div class="riskTreeImg">'+
							'<img src="..//assets/img/man.png" class="resike img-circle" >'+
						'</div>'+
						'<!-- 下面内容 -->'+
						'<div class="text-center textoverflowellipsis">'+
							'<span></span> <span class="marginleft10"></span>'+
						'</div>'+
						'<div class="text-center textoverflowellipsis ">'+
							'最新离职风险评估：<span class="lastTime"></span>'+
						'</div>'+
						'<div class="text-center textoverflowellipsis">'+
						'	 <span class="riskFlag"></span>&nbsp;&nbsp;离职风险 '+
						'</div>'+
				'</div>';
	var modal='<div class="modal fade" tabindex="-1" role="dialog"'+
				    'aria-labelledby="talentDevelModalLabel" aria-hidden="true">'+
				    '<div class="modal-dialog riskDialogModalSize">'+
				       '<div class="modal-content">'+
				            '<div class="modal-header">'+
				                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">'+
				                '    &times;'+
				               ' </button>'+
				                '<h4 class="modal-title bolder">离职风险评估</h4>'+
				            '</div>'+
				            '<div class="modal-body">'+
				            	'<div class="riskTree">'+
				            	head+   
								 '<div class="row col-xs-11 detail-info">'+
									'<div class="row">'+
										'<div class="col-xs-11 risk-detail-info">'+
											'<div class="risk-tree-div"><div></div></div>'+
//											'<div class="risk-memo">'+
//												'<div class="col-xs-2 pull-left">备注：</div>'+
//												'<div class="col-xs-8"><textarea></textarea>'+
//												'</div>'+
//											'</div>'+
										'</div>'+

									'</div>'+
									'<div class="row">'+
										
									'</div>'+
								'</div>'+
								'</div>'+
				            '</div>'+
				            '<div class="modal-footer">'+
				            	'<button type="button" class="btn btn-primary">提交</button>'+
					            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
				            '</div>'+
				        '</div><!-- /.modal-content -->'+
				    '</div><!-- /.modal -->'+
				'</div>';
	var template=  '<div class=" riskTree ">'+head+
				 '<div class="row col-xs-12 detail-info">'+
			        	'<div class="col-xs-6">'+
					//	'<div class="row">'+
							'<div class="col-xs-11 risk-detail-info">'+
								'<div class="risk-tree-div"><div></div></div>'+
//								'<div class="risk-memo">'+
//								'</div>'+
							'</div>'+
							'<div class="col-xs-11 risk-footer">'+
							'</div>'+
				//		'</div>'+
						'</div>'+
						'<div class="col-xs-6">'+
					//	'<div class="row">'+
							'<div class="col-xs-11">过往离职风险评估：</div>'+
							'<div class="col-xs-11 all-info">'+
							'</div>'+
					//	'</div>'+
						'</div>'+
				'</div></div>';
    var flagItemArr = [
                   {index:0,color:'gray',text:'未评估'},
                   {index:1,color:'red',text:'红灯'},
                   {index:2,color:'yellow',text:'黄灯'},
                   {index:3,color:'green',text:'绿灯'}
                ];
    $.fn.extend({
    	riskTree2: function (options) {
        	var self = this;//
        	console.log(1);
			if(options.callback){
				self._options.callbackParent=options.callback;
			}
        	if(options.riskModal){
        		self.getModalData(options);
        	}else{
        		if(options.data==null){
            		self.getData(options);
            	}else{
            		self.render(options);
            	}
        	}
        	
        	
        },render:function(options){
        	var self=this;
        	console.log(1);

        	self.recodeData ={empId:options.empId,empObj:options.empObj};
        	console.log(self.recodeData);
//        	$.map(options.empObj, function(obj){
//        		return $.extend(true,{},obj);//返回对象的深拷贝
//        		});
        	jQuery.extend(self._options, options);
        	self._createSubDom();
        	
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
	        topText : '离职风险',
	        hasTopText : false,
	        riskModal:false,   //是否加载评估界面
	       // hasAssessmentRisk: true,  //是否有評估按鈕
		   // hasLoadAssessmentRisk: false,//是否是加载评估界面
		    level:1,
		    callback:function(){
				this.callbackParent();
		    },
			callbackParent:null,
	        data : null
	    },allChart:{
			grid: {
				x:47,
				y:20,
				x1:0,
				x2:10,
	            borderWidth: 1,
	            color: '#BCBCBC'
	        },
	        calculable: false,
	        tooltip: {
                show: true,
                trigger: "item",
                formatter: function(a,b,c){
                	return a.data[2];
                }
            },
	        xAxis: [
	            {
	            	 boundaryGap: [0.05, 0.05],
	                type: "category",
	                nameTextStyle: {
	                    color: '#000000'
	                },
	                splitLine: {
	                    color: ['#ccc'],
	                    width: 1,
	                    type: 'dashed'
	                } ,
	                axisLabel: {
	                	rotate: 30,
	                },
	                axisLine: {
	                	show:false
	                }, axisTick: {
	                    show: false
	                },
	                data: []
	            },{
	            	 boundaryGap: [0.05, 0.05],
	                type: "value",
	                show: false,
	                min:-0.5,
	                splitLine: false,
	                data: []
	            }
	        ],
	        yAxis: [
	            {
	            	 boundaryGap: [0.05, 0.05],
	                type: "category",
	                nameTextStyle: {
	                    color: '#000000'
	                },
	                splitLine: {
	                    color: ['#ccc'],
	                    width: 1,
	                    type: 'dashed' 
	                },
	                axisTick: {
	                    show: false
	                },
	                axisLine: {
	                	show:false
	                },
	                data: ['绿灯','黄灯','红灯','未评估']
	            },
	            {splitNumber: 3,
	                boundaryGap: [0.05, 0.05],
	                type: "value",
	                show: false,
	                splitLine: false,
	                min:-0.5,
	                max:3.5,
	            }
	        ],    
	        series: [
	            {
	                type: "scatter",
	                smooth: false,
	                xAxisIndex: 1,
	                yAxisIndex: 1,
	                symbolSize:10,
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: false
	                        },
	                        lineStyle: {
	                        	show: false
	                        },
	                        color: function(a,b,c){
	                        	return flagItemArr[(3-a.data[1])].color;
	                        }
	                    }
	                },
	                markLine :false,
	                data: []
	            }
	        ]
	    },
        //下拉框
        getSelectTpl : function(id,riskFlag){
        	var self = this;
        	var selectHtml = '<select class="form-control pull-right" id="'+id+'">';
        	$.each(flagItemArr,function(i,item){
        		var selected = riskFlag == item.index ? 'selected' : '';
        		selectHtml += '<option value="' + item.index + '" ' + selected + '>'+item.text+'</option>';
        	});
        	return selectHtml+'</select>';
        },
        _createSubDom : function(){
        	var self = this;
        	console.log(1);
        	var container = $(this);
        	var dialog=$(container).parents(".modal-dialog");
        	if(!self._options.riskModal){
        		$(container).empty().append(template);
        		if(dialog){
        			dialog.removeClass("riskDialogSize").addClass("riskDialogSize");
        		}
        		self.loadPanel(container);
        	}else{
        		if(self._options.level==1){
        			var modalObj=$(modal);
        			container=modalObj;
    				$("body").append(modal);
    				modalObj.modal('show');
    				modalObj.on('shown.bs.modal', function () {
    					self.loadPanel(container);
   		            });
        		}else{
        			self.loadPanel(container);
        		}
        	}
        	
        },loadPanel:function(container){
        	var self=this;
        	console.log(1);
        	var detailText = container.find('.text-center .row').last().find('span');
        	var lastTime=container.find(".lastTime");
        	var empObj=self._options.empObj;
        	 //职位position
        	container.find(".base-info").find("div:nth-child(2)").find("span:nth-child(2)").text(empObj.organizationName + "/" + empObj.positionName);
        	container.find(".base-info").find("div:nth-child(2)").find("span:nth-child(1)").text(empObj.userNameCh);
//             detailText.eq(2).text( empObj.sequenceName + ' ' + empObj.abilityName);
             var riskDate="";
             if(empObj.riskDate!=null&&empObj.riskDate.length>10){
            	 riskDate= (empObj.riskDate).substring(0,10);
             }
             lastTime.html(lastTime.html()+"("+riskDate+")");
             var flagClass = 'risk-flag circle ' + flagItemArr[empObj.riskFlag||0].color;
             container.find('.base-info').find(":nth-child(4) span").removeClass().addClass(flagClass);
             
          
             if(empObj.imgPath==""){
            	 empObj.imgPath=defaultPic;
             }
             (container.find('.base-info .head-pic')).attr("src",webRoot+"/"+defaultPic)
             if(!self._options.riskModal){
            	 self.initAllRiskChart(); //加载过往风险
             }
             self.ztree=$(container).find('.risk-tree-div').children();
        	$(self.ztree).css('position','relative');
			var conWidth = self.ztree.width() + 'px';
			var cssStyle = {width : conWidth};
			self.lineCanvasDom = document.createElement('div');
			$(self.lineCanvasDom).addClass('line-canvas').css(cssStyle);
			self.riskTreeDom = $('<div>',{'class' : 'risk-tree','css' : cssStyle});
			self.ztree.append(self.lineCanvasDom).append(self.riskTreeDom);
			console.log(self._options.data);
			var treeData = self.transData(self._options.data);
			console.log(treeData);
			self._createTree(treeData,true);
			console.log(self._html);
        	self.riskTreeDom.html(self._html);
        	//不延迟的话，无法正确获得正确高度
        	setTimeout(function(){
            	console.log(1);
        		self._initLineCanvas();
        		self.LineShape = require('zrender/shape/Line');
        		self.connectLine($(container).find('.risk-tree').children(),true);
        		self.zr.render();
        		if(self._options.hasSelect){
        			self.bindSelectEvent(container);
        		}
        		//if(!self._options.riskModal){
        			self.find(".risk-tree").append(self.getMemoHtml())
        		//	self.find(".risk-memo").empty().append(self.getMemoHtml());
        		//}
        	
        		var modal=$(container).parents(".modal-dialog");
        		if(modal){
        			$.each($(modal).find(".modal-footer").find(".btn-assessment"),function(){
        				$(this).remove();
        			});
        			var html=$(modal).find(".modal-footer").html();
        			$(modal).find(".modal-footer").empty().append(self.getAssessmentRiskHtml());
        			$(modal).find(".modal-footer").append(html);
        		}
        		if(true){
        			$(container).find(".btn-primary").click(function(){
        				var parm=[];
        				var bool=true;
        				$.each($(container).find('select'),function(){
        					var id=$(this).attr("id");
        					var val=$(this).val();
        					if(id==-1&&val==0){
        						bool=false;
        					}
        					parm.push({riskId:id,riskFlag:val});
        				});
        				//
        				if(!bool){
        					Messenger().post({
 								type : "error",
 								message : "请评估离职风险！"
 							});
        					return;
        				}
        				var opearRiskDto = {
        						note:$(self).find("textarea").val(),
        						empId : self._options.empObj.empId,
        						risks : parm
        					}

        				 $.ajax({
        	                 url: urls.addRiskUrl,
        	                 data: JSON.stringify(opearRiskDto),
        	                 type:"post",
        	                 dataType : 'json',
        	                 contentType : 'application/json;charset=utf-8',
        	                 success: function (data) {
        	                	 var dataType = (data.type) ? 'success' : 'error';
     							if(data.type){
     								$(container).modal('hide');
     								self._options.callback();
     							}
     							Messenger().post({
     								type : dataType,
     								message : data.msg
     							});
        	                 }
        	             });
        			});
        		}
        	},100);
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
        /***del***/
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
			var riskFlagClass = 'img-circle risk-flag '+ bigText + flagItemArr[(item.riskFlag||0)].color ;
			if(item.pid=='-1'){
				riskFlagClass = 'risk-flag';
			}
			return '<div class="p-node ">' +
						'<input type="hidden" value="' + item.id + '">'+
						'<span class="' + riskFlagClass + '"></span>' +
						'<label>'+ riskName +'</label>' +
						this.getSelectDom(item.id,item.pid,item.riskFlag) +
					'</div>';
		},
		getSelectDom : function(id,pid,riskFlag){
			if(id!='-1'&&(pid=='-1'||pid!="1d7f157956aa11e5b31c08606e0aa89a")){
				return "";
			}
			return (this._options.hasSelect) ? this.getSelectTpl(id,riskFlag) : '';
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
		bindSelectEvent : function(container){
			var self = this;
			$(container).find('select').change(function(){
				var flagColor = flagItemArr[$(this).val()].color;
				$(this).siblings('.risk-flag').removeClass('red green gray yellow').addClass(flagColor);
			});
		},
		getMemoHtml:function(){
			var self=this;
			if(self._options.riskModal){
				var div=$('<div class="risk-memo"></div>');
				div.append('<div class="col-xs-2 pull-left">备注：</div>');
				div.append('<div class="col-xs-8"><textarea></textarea>');
				return div;
			}
			var div=$('<div class="risk-memo"></div>');
			div.append("<span>备注:"+(self._options.empObj.note==null?"":self._options.empObj.note)+"</span>");
			return  div;
		},
		//评估离职风险操作
		getAssessmentRiskHtml:function(){
			var self=this;
			if(self._options.riskModal){
				return null;
			}
			var btn=$('<button type="button" class="btn btn-primary btn-assessment">评估离职风险</button>');
			$(btn).click(function(){
				var dialog=$(self).parents(".modal.fade.in");
        		if(dialog){
        			$(dialog).modal('hide');
        		}
				var modalObj=$(modal);
				$(self).append(modal);
				modalObj.modal('show');
				self._html="";
//				var data =$.map(self.recodeData, function(obj){
//            		return $.extend(true,{},obj);//返回对象的深拷贝
//            		});
				 var riskTreeOption = {
				            hasSelect: true,
				            data: null,
				            hasTopText: true,
				            topText:'离职风险',
				            empId:self.recodeData.empId,
				            empObj:self.recodeData.empObj,
				            level:2,
				            riskModal:true,
				            //hasAssessmentRisk: false,  //是否有評估按鈕
				           // hasLoadAssessmentRisk: true//是否是加载评估界面
				        }
				 modalObj.on('shown.bs.modal', function () {
					 modalObj.riskTree2(riskTreeOption);
		            });
			});
			return  btn;
		},getData:function(options){
			var self=this;
        	console.log(1);
			console.log(options);
			var empId=options.empId;
			 $.ajax({
                 url: urls.empBaseInfoUrl,
                 data: {empId: empId},
                 success: function (data) {
                	 options.empObj=data;
                	 $.ajax({
                         url: urls.empRiskDetailUrl,
                         data: {empId: empId},
                         success: function (data) {
                        	 options.data=data;
                    		 self.render(options);
                         }
                     }); 
                 }
             });
		},getModalData:function(options){
			var self=this;
        	console.log(1);
			if(options.empObj){
				$.ajax({
                    url: urls.getEmpRiskDefaultDetailUrl,
                    success: function (data) {
                   	 options.data=data;
               		 self.render(options);
                    }
                });
			}else{
				var empId=options.empId;
				$.ajax({
	                url: urls.empBaseInfoUrl,
	                data: {empId: empId},
	                success: function (data) {
	               	 options.empObj=data;
	               	$.ajax({
	                    url: urls.getEmpRiskDefaultDetailUrl,
	                    success: function (data) {
	                   	 options.data=data;
	               		 self.render(options);
	                    }
	                });
	                }
	            });
			}
			
		},
		initAllRiskChart:function(){
			var self=this;
			 $.ajax({
                 url: urls.empAllRiskDetailUrl,
                 data: {empId: self._options.empObj.empId},
                 success: function (data) {
                	 console.log(($(self).find(".all-info"))[0]);
                     var chartObj = echarts.init(($(self).find(".all-info"))[0]);
                     console.log(chartObj);
                     var X=[],Y=[];var X1=[];
                     $.each(data,function(i,o){
                    	 var date=o.riskDate;
                    	 var arr=date.split("-");
                    	 X.push(arr[0]+"/"+arr[1]);
                    	 X1.push(i);
                    	 var d=[];
                    	 d.push(i);
                    	 var note=o.note;
                    	
                    	 if(note==null){
                    		 note="无备注"; 
                    	 }
                    	 d.push(Math.abs(o.riskFlag-3)); d.push(note);
                    	 Y.push(d);
                     });
 	                 self.allChart.xAxis[1].splitNumber = Y.length;
                     self.allChart.xAxis[1].max = Y.length-0.5;
                	 self.allChart.xAxis[0].data = X;
                	 self.allChart.xAxis[1].data = X1;
                	 self.allChart.series[0].data = Y;
                	 chartObj.setOption(self.allChart, true);
                 }
        	 });
		}
    });
});
