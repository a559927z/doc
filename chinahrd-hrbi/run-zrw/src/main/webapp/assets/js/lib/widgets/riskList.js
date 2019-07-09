/**
 * 离职风险li
 */
define(['echarts','echarts/chart/scatter','bootstrap','messenger'], function (echarts) {
    var webRoot = G_WEB_ROOT;
	var urls = {
	        empBaseInfoUrl: webRoot + "/common/getEmpBaseInfo.do",	//获取员工基本信息
			empRiskDetailUrl: webRoot + "/dismissRisk/getEmpRiskDetail.do",	//获取员工离职风险信息
			empAllRiskDetailUrl: webRoot + "/dismissRisk/getEmpAllRiskDetail.do",	//获取员工历史离职风险信息
			addRiskUrl: webRoot + "/common/addRisk.do",	//评估离职风险信息
			getEmpRiskDefaultDetailUrl:webRoot + "/dismissRisk/getEmpRiskDefaultDetail.do",	//默认离职风险信息
			};
	var defaultPic='assets/img/base/u-default.png';

	var head= '<div class="base-info-riskList">'+
						'<!--上面头像-->'+
						'<div class="riskListImg">'+
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
						'	 <span class="riskFlag" style="vertical-align: top !important;margin-right: 10px"></span>离职风险 '+
						'</div>'+
				'</div>';
	var modal='<div class="modal fade" tabindex="-1" role="dialog"'+
				    'aria-labelledby="talentDevelModalLabel" aria-hidden="true" style="overflow: auto;">'+
				    '<div class="modal-dialog riskListDialogModalSize">'+
				       '<div class="modal-content">'+
				            '<div class="modal-header">'+
				                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">'+
				                '    &times;'+
				               ' </button>'+
				                '<h4 class="modal-title bolder">离职风险评估</h4>'+
				            '</div>'+
				            '<div class="modal-body"  style="height: 523px">'+
					            '<div class=" riskList ">'+head+
								 '<div class="row col-xs-12 detail-info">'+
							        	'<div class="wtf-div"><div></div></div>'+
							        	'<div class="scatter-div hide row">'+
							        	'<div class="col-xs-11"><span class="risk-old-link"></span></div><div class="col-xs-11"></div></div>'+
								'</div></div><div class="col-xs-11 risk-remark"></div>'+
				            '</div>'+
				            '<div class="modal-footer">'+
				            	'<button type="button" class="btn btn-primary">提交</button>'+
					            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
				            '</div>'+
				        '</div><!-- /.modal-content -->'+
				    '</div><!-- /.modal -->'+
				'</div>';
	var template=  '<div class=" riskList ">'+head+
				 '<div class="row col-xs-12 detail-info">'+
			        	'<div class="wtf-div"><div></div></div>'+
			        	'<div class="scatter-div row">'+
			        	'<div class="col-xs-11"><span class="risk-old-link">过往离职风险评估》</span></div><div class="col-xs-11"></div><div class="col-xs-11 risk-remark"></div></div>'+
				'</div></div>';
    var flagItemArr = [
                   {index:0,color:'gray',text:'未评估'},
                   {index:1,color:'red',text:'红灯'},
                   {index:2,color:'yellow',text:'黄灯'},
                   {index:3,color:'green',text:'绿灯'}
                ];
    $.fn.extend({
    	riskList: function (options) {
        	var self = this;//
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
        	
        	
        },
        //生成dom对象
        render:function(options){
        	var self=this;

        	self.recodeData ={empId:options.empId,empObj:options.empObj};
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
                enterable:true,
                formatter: function (params,ticket,callback) {
                	var target = "";
                	var text = params.data[2];
                	if(text.length > 13)
	                    while(text.length > 13){

	                    	target += "<br/>";
	                    	target += text.substring(0,13);
	                    	text = text.substring(13, text.length);
	                    	if(text.length <= 13){
	                    		target += "<br/>" + text;
	                    	}
	                    }
                	else
                		return text;
                	console.log(target);
                    return target;
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
        	var container = $(this);
        	var dialog=$(container).parents(".modal-dialog");
        	if(!self._options.riskModal){
        		$(container).empty().append(template);
        		if(dialog){
        			dialog.removeClass("riskListDialogSize").addClass("riskListDialogSize");
        		}
        		self.loadPanel(container);
        		self.bindRiskOld();
        	}else{
        		if(self._options.level==1){
        			var modalObj=$(modal);
        			container=modalObj;
    				$("#riskDetailModal").append(modal);
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
        	var detailText = container.find('.text-center .row').last().find('span');
        	var lastTime=container.find(".lastTime");
        	var lastTimePar = $(lastTime).parent();
        	var empObj=self._options.empObj;
        	 //职位position
        	container.find(".base-info-riskList").find("div:nth-child(2)").find("span:nth-child(2)").text(empObj.organizationName + "/" + empObj.positionName);
        	container.find(".base-info-riskList").find("div:nth-child(2)").find("span:nth-child(1)").text(empObj.userNameCh);
//             detailText.eq(2).text( empObj.sequenceName + ' ' + empObj.abilityName);
        	//上次评价时间
             var riskDate="";
             if(empObj.riskDate!=null&&empObj.riskDate.length>10){
            	 riskDate= (empObj.riskDate).substring(0,10);
             }
             lastTime.html(lastTime.html()+"("+riskDate+")");
             if(riskDate == null || riskDate == '' || riskDate == 'null'){
            	 $(lastTimePar).hide();
             }
             //评价结果
             var flagClass = 'risklist-flag circle ' + flagItemArr[empObj.riskFlag||0].color;
             container.find('.base-info-riskList').find(":nth-child(4) span").removeClass().addClass(flagClass);
             
             
          
             if(empObj.imgPath==""){
            	 empObj.imgPath=defaultPic;
             }
             (container.find('.base-info-riskList .head-pic-riskList')).attr("src",webRoot+"/"+defaultPic);
             self.ztree=$(container).find('.wtf-div').children();
        	$(self.ztree).css('position','relative');
			var conWidth = self.ztree.width() + 'px';
			var cssStyle = {width : conWidth};
			self.lineCanvasDom = $(container).find('.scatter-div');
			$(self.lineCanvasDom).find('div:nth-child(2)').addClass('line-riskList-canvas');
			//修改树为list
			self.riskListDom = $('<div>',{'class' : 'risk-list row','css' : cssStyle});
			self.ztree.append(self.riskListDom).append(self.lineCanvasDom);
			//获取到树结构的数据
			var treeData = self.transData(self._options.data);
			self._createList(treeData,true);
        	self.riskListDom.html(self._html);
        	$(".modal-content").css({height: 650+'px'});
        	//不延迟的话，无法正确获得正确高度
        	setTimeout(function(){
//        		self._initLineCanvas();
//        		self.LineShape = require('zrender/shape/Line');
//        		self.connectLine($(container).find('.riskList').children(),true);
//        		self.zr.render();
        		if(self._options.hasSelect){
        			self.bindSelectEvent(container);
        		}
        		//if(!self._options.riskModal){
        			$(".risk-remark").append(self.getMemoHtml())
        		//	self.find(".risk-memo").empty().append(self.getMemoHtml());
        		//}
        			//设定散点图的样式
        			//过早加载echart会加载不到高度和宽度
        		var conheight = self.ztree.height() + 'px';
        		$(self.lineCanvasDom).find('.line-riskList-canvas').css({height:conheight,width:self.ztree.width()-100+'px',margin:'20px 0 20px 20px',position: 'relative'});
                if(!self._options.riskModal){
               	 self.initAllRiskChart(); //加载过往风险
                }
        	
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
        						note: $(".modal.fade.in").find("textarea").val(),
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
            	$(".modal-body").css({overflow: 'auto'});
        	},100);
        	$(self.riskListDom).css({position: 'relative',height:$(self.riskListDom).height+40+"px"});

        	$(".modal-body").css({height: $(".modal-body").height+"px"});
        	//隐藏散点图
        	$(self.lineCanvasDom).find('.line-riskList-canvas').addClass("hide");
        },
        /**
         * @param data 数据
         * @param 转换为两层的list
         */
        _createList : function(data){
        	var self = this;
        	var issetMainRisk = false;
			$.each(data,function(i,item){
				if(item.pid == '-1' && item.pid != null){
					leftborder = (i+1)%2 == 0 ? ' style="margin-left: 70px;border-left: 1px solid #d9d8d9;" ':'';
					self._html += '<div class="col-xs-5" ' + leftborder + ' >';
					self._html += '<div class="col-xs-11 risklisthead">' + item.name + '</div>';
					self._html += '<div class="col-xs-11 risklist-detail-info" >';

			        //"离职风险" 评估
					if(!issetMainRisk){
						var ishide = self._options.riskModal ? '':'hide';
						self._html += '<div class=" ' + ishide + '"> <span class="circle risklist-flag mainriskflag ' 
							+  flagItemArr[item.riskFlag||0].color + '"></span> <label>离职风险</label>'
							+ self.getSelectDom(-1,-1,0)
							+'</div>';
						issetMainRisk = true;
					}
				}
				if(item.pid != '-1' && item.pid != null){
					self._html += '<div> <span class="circle risklist-flag ' 
						+  flagItemArr[item.riskFlag||0].color + '"></span> <label>' 
						+ item.name + '</label>'
						+ self.getSelectDom(item.id,item.pid,item.riskFlag)
						+'</div>';
				}
				if(item.children){
					self._createList(item.children);
				}
				if(item.pid == '-1' && item.pid != null){
					self._html += '</div>';

					self._html += '</div>';
				}
			});
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
		getXPosition : function($dom){
			return $dom.position().left + $dom.outerWidth()/2;
		},
		getYPosition:function($dom){
			return $dom.position().top + $dom.outerHeight()/2
		},
//		// 绘画
//		drawLine : function(xStart,yStart,xEnd,yEnd){
//			// 直线
//			this.zr.addShape(new this.LineShape({
//			    style : {
//			        xStart : xStart,
//			        yStart : yStart,
//			        xEnd : xEnd,
//			        yEnd : yEnd,
//			        strokeColor : '#999',
//			        lineWidth : 1,
//			        lineType : 'solid'    // default solid
//			    },
//			    hoverable : false   // default true
//			}));
//		},
		//绑定下拉框事件
		bindSelectEvent : function(container){
			var self = this;
			$(container).find('select').change(function(){
				var flagColor = flagItemArr[$(this).val()].color;
				$(this).siblings('.risklist-flag').removeClass('red green gray yellow').addClass(flagColor);
			});
		},
		getMemoHtml:function(){
			var self=this;
			if(self._options.riskModal){
				var div=$('<div class="risk-memo" style="border:0px"></div>');
				div.append('<span class="floatleft">备注：</span>');
				div.append('<div class=""><textarea  rows="1" cols="50"></textarea>');
				return div;
			}
			var div=$('<div class="risk-memo"></div>');
			div.append("<span>备注:"+(self._options.empObj.note==null?"":self._options.empObj.note)+"</span>");
			return  div;
		},
		//评估离职风险操作
		getAssessmentRiskHtml:function(){
			var self=this;
        	var container = $(this);
			if(self._options.riskModal){
				return null;
			}
			var btn=$('<button type="button" class="btn btn-primary btn-assessment">评估离职风险</button>');
			$(btn).click(function(){

	        	var dialog=$(container).parents(".modal.fade.in");
        		if(dialog){
        			$(dialog).modal('hide');
        		}
				var modalObj=$(modal);
				

				modalObj.modal('show');
				self._html="";
				 var riskListOption = {
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
				        };
				 modalObj.on('shown.bs.modal', function () {
					 modalObj.riskList(riskListOption);
		            });
			});
			return  btn;
		},getData:function(options){
			var self=this;
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
                     var chartObj = echarts.init(($(self).find(".line-riskList-canvas"))[0]);
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
		},
		bindRiskOld: function(){
			$riskold = $(".risk-old-link");
			$riskold.unbind('click').on('click',function(){
				$(".line-riskList-canvas").removeClass("hide");
				$riskold.css({float: 'left',padding:'0 0 0 50px'});
				$riskold.text("过往离职风险评估");
			});
		}
    });
});
