/**
 * Created by htpeng on 16/09/10 
 */

require(['jquery', 'moment', 'echarts', 'echarts/chart/line','echarts/chart/bar','echarts/chart/radar', 'echarts/chart/gauge','bootstrap', 
    'jquery-pin', 'jgGrid','select2', 'underscore','timeLine2','searchBox3','cardTabel'], function ($, moment, echarts) {
	         var webRoot = G_WEB_ROOT,win = top != window ? top.window : window;
	         var isCurPage=true;
	         var curTab="page-one";
	         var floatNumber = 168;
	         var tableHeight=$(window).height()-165;
	         var ecConfig = require('echarts/config');
	         var rangeObj={};
	         var urls = {
	             getTeamAvgCompetencyUrl: webRoot + '/positionCompetency/getTeamAvgCompetency.do',//获取团队平均胜任度
	             getPositionMaxMinCompetencyUrl: webRoot + '/positionCompetency/getPositionMaxMinCompetency.do',//获取岗位最高和最低胜任度
	             querySubOrganUrl: webRoot + '/positionCompetency/querySubOrgan.do',//获取下级组织列表
	             getSequenceCompetencyUrl: webRoot + '/positionCompetency/getSequenceCompetency.do',//序列团队平均胜任度对比分析
	             getAbilityCompetencyUrl: webRoot + '/positionCompetency/getAbilityCompetency.do',//职级团队平均胜任度对比分析
	             getEmpByNameUrl: webRoot + '/positionCompetency/queryEmpByName.do',//人员面板
	             getPositionTableUrl: webRoot + '/positionCompetency/queryPositionTable.do',//岗位面板
	             getPositionEmpTableUrl: webRoot + '/positionCompetency/queryPositionEmpTable.do',//岗位面板 下的人员列表
	             getPositionImagesUrl: webRoot + '/positionCompetency/getPositionImages.do',//岗位画像
	             
	             memoUrl: webRoot + '/memo/findMemo.do',				//查看备忘录信息
	             addMemoUrl: webRoot + '/memo/addMemo.do',			//添加备忘录信息
	             getBaseConfigUrl: webRoot + '/positionCompetency/getBaseConfig.do',    //获取主动流失率基础配置信息
	             updateBaseConfigUrl: webRoot + '/positionCompetency/updateBaseConfig.do' ,
	             
	             selectPosition: webRoot + "/positionCompetency/queryPositionByName", //岗位 select2
	             
	             getSearchEmpUrl: webRoot + '/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
	             
	             getEmpContrastInfoUrl: webRoot + '/positionCompetency/getEmpContrastInfo.do',               //获取员工信息
	             
	             selectEmpByPositionUrl: webRoot + "/positionCompetency/selectEmpByPosition", //人员 select2
	             getKeyWordInfoUrl: webRoot + "/positionCompetency/getKeyWordInfo", //关键词搜索
	             indexEmpTaskUrl: webRoot + "/positionCompetency/indexEmpTask", //添加索引
	             
	         };
	         $(win.document.getElementById('tree')).next().show();
	         var reqOrgId = win.currOrganId;
	         var reqOrgName=win.currOrganTxt;
	         if(reqOrgId==undefined){
	        	 reqOrgId='fcb4d31b3470460f93be81cf1dd64cf0'
	         }
	         var reqOrgText = win.currOrganTxt;
	         if (win.setCurrNavStyle) win.setCurrNavStyle();

	         //所有周期 构造周期下拉框
	         var times = $('#times').val().split(",");
	         var timeValues = $('#timeValues').val().split(",");
	         var selectTimeVals=[]; //周期下拉框
	         var threndTimes="";    //趋势的时间
	         var threndTimesNum=5;
	         for(var i=0;i<times.length;i++){
	        	 selectTimeVals.push({key:times[i],val:timeValues[i]});
	        	 if(i<threndTimesNum){
	        		 threndTimes+=(times[i]+",");
		         }
	         }
	         
	         
	        
	         //当前周期  key和value
	         var time = $('#time').val();
	         var timeValue = $('#timeValue').val();
	         

	         //获取机构数据
	         win.organizationObject = function (organId, organTxt) {
	             reqOrgId = organId;
	             reqOrgName=organTxt;
	             empTabelObj.setOrganTreeValue(organId, organTxt);
	             $("#searchTxt").val("");
	             //$("#recordSelectTreeOrgId").val(id);
	             initAll(organId);
//	             performanceDetailObj.initData(reqOrgId);
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
	                     quotaId: quotaId,
	                     organId: organizationId
	                 }
	                 return options;
	             }
	         }
		       
	        

  var  tooltip={
      trigger: 'axis',
      axisPointer: {
          type: 'line',
          lineStyle:{
          	color: '#999999',
          	type: 'dashed',
          	width: 1
          }
      },formatter: function (a, b, c) {
//          return ((a.value*100).toFixed(0))+"%";
//    	  var html=a[0][0]+"&nbsp;&nbsp;<br/>";
    	  var html="&nbsp;"+a[0][1]+"&nbsp;:&nbsp;"+((a[0][2]*100).toFixed(0))+"%&nbsp;";
    	    return html;
      }
  };
 var  xAxis={
     type: "category",
     splitLine: {show: false},
     axisTick: {
     	show : true,
     	lineStyle: {
             color: '#666666'
         }
     },
     data: [],
     axisLine: {
         lineStyle: {
             color: '#666666',
             width: 1
         }
     },
     axisLabel: {
         itemStyle: {
             color: '#666666'
         },
         textStyle: {
             color: '#666666',
             fontSize: 12,
             fontFamily: "'微软雅黑', 'verdana'"
         }
     }
 };
	         /*==============================================================*/
	         /* TOP															*/
	         /*==============================================================*/
	         var topObj={
	         	chartId:"top-chart",
	         	chartObj:null,
	         	load:false,
	         	option: {
	                toolbox: false,
	                series: [
	                    {
	                        name: "团队平均胜任度",
	                        type: 'gauge',
	                        center : ['50%', '60%'],    // 默认全局居中
//	                        startAngle: 180,
//	                        endAngle: 0,
	                        radius : 55,
//	                        clockwise:false,
	                        pointer: {
	                            width:5,
	                            length: '90%',
	                            color: '#61bb33'
	                        },
	                        max: 1,
	                        splitNumber: 0,
	                        axisLine: {            // 坐标轴线
	                            lineStyle: {       // 属性lineStyle控制线条样式
	                                color: [[0.6, '#e7191b'], [0.8, '#f09200'], [1, '#6fb12f']],
	                                width: 10
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
	                        data: [{value: 0}], 
	                        detail : {
//	                        	show:false,
	                        	offsetCenter:[0,10],
	                            formatter:function(v){
	                            	return (v*100).toFixed(0)+"%"
	                            },
	                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                                color: 'auto',
	                                fontSize:20
	                            }
	                        },
	                    }
	                ]
	            },
	         	init:function (organId,yearMonth) {
	         		if(this.chartObj==null){
	         			 this.chartObj = echarts.init(document.getElementById(this.chartId));
	         		}
	         		this.getAvgCom(organId,yearMonth);
	         		this.getPositionMaxMin(organId,yearMonth);
	         	},loadMaxMinData:function(data){
	         		var min=data.min;
	         		var max=data.max;
	         		var can=document.getElementById("high-chart");
	      		    var cans = can.getContext('2d');
	      		  
	      		   cans.beginPath(); 
	      		   cans.clearRect(0,0,160,90);
	      		   cans.closePath(); 
	      		   if(!this.load){
	      			 cans.scale(2,2);
	      		   }
	      		    
	      		    drawArc(cans,80,80,70,Math.PI,0,'#e0e0e0',15);
	     		    drawArc(cans,80,80,70,Math.PI,(1+(max.rate*100).toFixed(0)/100)*Math.PI,getColorByCom(max.rate),15);
	     		    drawText(cans,"#666666","30px Arial",(max.rate*100).toFixed(0)+'%',80, 80);
	     		   
	     		    $("#high-chart").parent().parent().find(".top-bottom").html(max.positionName)
	     			 can=document.getElementById("low-chart");
	     	 		 cans = can.getContext('2d');
	     	 		cans.beginPath(); 
		      		cans.clearRect(0,0,160,90);
		      		cans.closePath(); 
		      		 if(!this.load){
		      			 cans.scale(2,2);
		      			this.load=true;
		      		   }
	     	 		 drawArc(cans,80,80,70,Math.PI,0,'#e0e0e0',15);
	     	 		   drawArc(cans,80,80,70,Math.PI,(1+(min.rate*100).toFixed(0)/100)*Math.PI,getColorByCom(min.rate),15);
	     	 		   drawText(cans,"#666666","30px Arial",(min.rate*100).toFixed(0)+'%',80, 80);
	     	 		  $("#low-chart").parent().parent().find(".top-bottom").html(min.positionName)
	         	},loadAvgCom:function(data){
	                this.option.series[0].data[0].value = data[0].rate;
	                this.option.series[0].pointer.color=getColorByCom(data[0].rate);
	               
	                this.chartObj.setOption(this.option, true);
	         	},getAvgCom:function(organId,yearMonth){
	         		//查询团队平均胜任力
	         		var _=this;
	         		$.ajax({
	         			url : urls.getTeamAvgCompetencyUrl,
	         			data : {
	             			organId : organId,
	             			yearMonth:yearMonth
	             		},
	         			type : 'post',
	         			success : function(data){
//	         				loaded(self.chartId);
	         				_.loadAvgCom(data);
	         			},
	         			error : function(){}
	         		});
	         	},getPositionMaxMin:function(organId,yearMonth){
	         		var _=this;
	         		$.ajax({
	         			url : urls.getPositionMaxMinCompetencyUrl,
	         			data : {
	             			organId : organId,
	             			yearMonth:yearMonth
	             		},
	         			type : 'post',
	         			success : function(data){
	         				_.loadMaxMinData(data);
	         			},
	         			error : function(){}
	         		});
	         	},resize: function () {
	                var self = this;
	                var _this = $(self.chartId);
	                self.option.width = _this.width();
//	                _this.vernierCursor(self.option);
	            }
	         
	         }
	         
	       /**
	        * 团队平均胜任度趋势
	        */
	         var teamAvgComThrendObj={
	        		 chartId:"teamAvgComThrendChart",
	        		 selectId:"teamAvg-select",
	        		 chartObj:null,
	        		 organId:null,
	        		 option:{
	        	            tooltip: tooltip,
	        	            toolbox: {
	        	                show: true,
	        	                feature: {
	        	                    dataView: {
	        	                        readOnly: true
	        	                    },
	        	                    magicType: {
	        	                        type: ["line", "bar"],
	        	                        show: false
	        	                    }
	        	                }
	        	            },
	        	            yAxis: [
	        	                {
	        	                    type: "value",
	        	                    splitNumber: 5,
	        	                    splitLine: {
	        	                        show: true,
	        	                        lineStyle : {
	        	                        	color : '#e4e4e4'
	        	                        }
	        	                    },
	        	                    axisLine: {
	        	                        show: true,
	        	                        lineStyle: {
	        	                            color: '#666666',
	        	                            width: 1
	        	                        }
	        	                    },
	        	                    axisLabel: {
	        	                        show: true,
	        	                        formatter: function (value) {
	        	                            return value*100+"%";
	        	                        }
	        	                    }
	        	                }
	        	            ],
	        	            xAxis: [
	        	                    xAxis
	        	            ],
	        	            series: [
	        	                {
	        	                    name: "团队平均胜任度",
	        	                    type: "bar",
	        	                    data: [],
	        	                    clickable: false,
	        	                    itemStyle: {
	        	                    	normal: {
	        	                            color: "#5cb7f1",
	        	                            label: {
	        	                                show: true,
	        	                                formatter: function (a, b, c) {
	        	                                    return ((a.value*100).toFixed(0))+"%";
	        	                                }
	        	                            }
	        	                        },
	        	                        emphasis: {
	        	                            color: 'rgba(0,0,0,0)'
	        	                        }
	        	                    },
	        	                    barWidth: 30,
	        	                }
	        	            ],
	        	            grid: {
	        	                borderColor: "#ffffff",
	        	                x2: 20,
	        	                y2: 50,
	        	            	y:90,
	        	            },
	        	            color: ["#3285C7"]
	        	        },
	        		 init:function(organId){
	        			 if(this.chartObj==null){
	        				 this.chartObj=echarts.init(document.getElementById(this.chartId));
	        				 this.select2();
	        				 this.bindEvent();
	        				 this.bindOrganEvent();
	        			 }
	        			 this.organId=organId;
	        			 this.getData(organId);
	        		 },getData: function (organId,positionId) {
	        			 var self=this;
	        			 loading(self.chartId);
	        			 $.ajax({
	        		  			url : urls.getTeamAvgCompetencyUrl,
	        		  			data : {
	        		    			organId : organId,
	        		      			yearMonth:threndTimes,
	        		      			positionId:positionId,
	        		      		},
	        		  			type : 'post',
	        		  			success : function(data){
	        		  				self.loadChart(data);
	        		  			},
	        		  			error : function(){}
	        		  		});
	        	           
	        	        },loadChart:function(data){
	        	        	 var self = this;
		        	            if (data!= undefined&&data.length>0) {
		        	            	 hideChart(self.chartId);
		        	                var xaxisData = [], seriesData = [];
		        	                $.each(data, function (n, v) {
		        	                    seriesData.push({"value": v.rate, "orgId": v.organId, "porgId": v.parentId});
		        	                   xaxisData.push(v.yearMonth);
		        	                });
//		        	                seriesData = _.sortBy(seriesData, "value");
//		        	           
		        	                self.option.xAxis[0].data = xaxisData;
		        	                self.option.series[0].data = seriesData;

		        	                self.chartObj.clear();
		        	                self.chartObj.setOption(self.option);
		        	                self.chartObj.refresh();
		        	            }else{
		        	            	 hideChart(self.chartId,true);
		        	            }
	        	        },select2:function(){
	        	        	var self=this;
	        	        	initPositionSelect2(self.selectId);
	        	        },bindEvent:function(){
	        	        	var self=this;
	        	        	 $(".teamAvgComBtn").children().click(function(){
	        	       	    	var show=$(this).children(".posion_expand").is(":hidden");
	        	       	    	$(this).parent().children().removeClass("active");
	        	       	    	$(this).parent().children().children(".posion_expand").hide();
	        	       	    	$(this).addClass("active");
	        	       	    	if(show==true){
	        	       	    		$(this).children(".posion_expand").show();
	        	       	    	}
	        	       	    	
	        	       	    });
	        	       	  
	        	       	    
	        	       	  $(".panelBtn_expand-search").click(function(e){
	        	    	    	return false ;
	        	    	    });
	        	    	    $("#teamAvgCom_search").unbind("click").click(function(e){
	        	    	    	$(this).parent().parent().parent().hide();
	        	    	    	var val=$("#teamAvg-select").val();
						 		$("#teamAvg-select-showText").text($("#select2-teamAvg-select-container").attr("title"));
						 		self.resetOrgan();
						 		self.reloadByPosition(val);
	        	    	    	return false ;
	        	    	    });
	        	        },reload:function(organId){
	        	        	 this.getData(organId);
	        	        },reloadByPosition:function(positionId){
	        	        	 this.getData(this.organId,positionId);
	        	        },bindOrganEvent:function(){
	        	        	var self=this;
	        	        	this.organBar=$("#teamAvg_organZtree").organTreeSelector({
                                multiple: false,
                                showSelectBtn: false,
                                mouseenterColor: '#fff',
                                mouseleaveColor: '#fff',
                                onSelect: function (ids, texts) {
                              	  $("#teamAvg_organZtree").attr("value",ids);
                              	  if(self.resetOrganBool){
                              		self.resetOrganBool=false;
                              		  return;
                              	  }
                              	  self.resetPosition();
                              	  self.reload(ids);
                                }
                             });
                          	$("#teamAvg_organZtree").attr("value",reqOrgId);
                          	this.organBar.organTreeSelector("value", {id: reqOrgId, text: "组织"});
	        	        },resetPosition:function(){
	        	        	$("#teamAvg-select-showText").html("岗位");
	        	        },resetOrgan:function(){
	        	        	if(undefined==this.organBar)return;
	        	        	this.resetOrganBool=true;
	        	        	this.organBar.organTreeSelector("value", {id: reqOrgId, text: "组织"});
//	        	        	this.organBar.organTreeSelector("value", {id: reqOrgId, text: reqOrgName});
	        	        }
	        		 
	         }
	         
 /**
    * 下级组织团队平均胜任度对比

    */
     var teamAvgComContrastObj={
    		 chartId:"teamAvgComContrastChart",
    		 backId:"#teamAvgComContrast_back",
    		 chartObj:null,
    		 yearMonth:'',
    		 stack:[],
    		 option:{
    			 	tooltip: tooltip,
	 	            toolbox: {
	 	                show: true,
	 	                feature: {
	 	                    dataView: {
	 	                        readOnly: true
	 	                    },
	 	                    magicType: {
	 	                        type: ["line", "bar"],
	 	                        show: false
	 	                    }
	 	                }
	 	            },
	 	            yAxis: [
	 	                {
	 	                    type: "value",
	 	                    splitNumber: 5,
	 	                    splitLine: {
	 	                        show: true,
	 	                        lineStyle : {
	 	                        	color : '#e4e4e4'
	 	                        }
	 	                    },
	 	                    axisLine: {
	 	                        show: true,
	 	                        lineStyle: {
	 	                            color: '#666666',
	 	                            width: 1
	 	                        }
	 	                    },
	 	                    axisLabel: {
	 	                        show: true,
	 	                        formatter: function (value) {
	 	                            return value*100+"%";
	 	                        }
	 	                    }
	 	                }
	 	            ],
	 	            xAxis: [
	 	                    xAxis
	 	            ],
    	            series: [
    	                {
    	                    name: "团队平均胜任度",
    	                    type: "bar",
    	                    barGap: 50,
    	                    data: [],
    	                    itemStyle: {
    	                    	normal: {
    	                            color: "#5cb7f1",
    	                            label: {
    	                                show: true,
    	                                formatter: function (a, b, c) {
    	                                    return ((a.value*100).toFixed(0))+"%";
    	                                }
    	                            }
    	                        },
    	                        emphasis: {
    	                            color: 'rgba(0,0,0,0)'
    	                        }
    	                    },
    	                    barWidth: 30,
    	                }
    	            ],
    	            grid: {
    	                borderColor: "#ffffff",
    	                x2: 20,
    	                y2: 40,
    	                y:90,
    	            },dataZoom: {
    	                show: true,
    	                realtime: true,
    	                height: 20,
    	                zoomLock: true,   //当设置为true时选择区域不能伸缩
    	                showDetail: false,
    	                start: 0,
    	                end: 40
    	            },
    	            color: ["#3285C7"]
    	        },
    		 init:function(organId,time){
    			 if(this.chartObj==null){
    				 this.chartObj=echarts.init(document.getElementById(this.chartId));
    				 this.chartObj.on(ecConfig.EVENT.CLICK, this.clickEvent);
    				 this.backEvent();
    				 this.btnEvent();
    			 }
    			 this.stack.push(organId);	 
    			 this.organId=organId;
    			 this.yearMonth=time;
    			 this.getData(organId);
    		 },reload:function(organId,time,del){
    			 if(!del){
    				 this.stack.push(organId);	 
    			 }
    			 this.yearMonth=time;
    			 this.getData(organId);
    		 },getData: function (organId) {
    			 var self=this;
    			 $(teamAvgComContrastObj.backId).hide();
    			 self.chartObj.clear();
    			 loading(self.chartId);
    			 $.ajax({
    		  			url : urls.getTeamAvgCompetencyUrl,
    		  			data : {
    		  				organId : organId,
    		      			yearMonth:self.yearMonth,
    		      			next:true,
    		      		},
    		  			type : 'post',
    		  			success : function(data){
    		  				if(self.stack.length>1){
    		  					$(teamAvgComContrastObj.backId).show();
    		  				}
    		  				self.loadChart(data);
    		  			},
    		  			error : function(){}
    		  		});
    	           
    	        },loadChart:function(data){
    	        	 var self = this;
        	            if (data!= undefined&&data.length>0) {
          	            	hideChart(self.chartId);
        	                var xaxisData = [], seriesData = [],len=0;
//			        	                var total = 0;
        	                $.each(data, function (n, v) {
        	                	len++;
        	                    seriesData.push({"value": v.rate, "organId": v.organId,"hasChildren":v.hasChildren});
        	                   xaxisData.push(v.organName);
        	                });
        	                self.option.dataZoom={};
        	                self.option.grid.y2=40;
        	                var num=4;
        	                if (len > num) {
        	                    var end = 50;
        	                    if (len > 8) {
        	                        end = 22;
        	                        _.each(xaxisData, function (item, i) {
        	                        	xaxisData[i] = item.length > 6 ? item.substring(0, 5) + '...' : item;
        	                        });
        	                    }
        	                    self.option.dataZoom={
        	                    		show: true,
            	                        realtime: true,
            	                        height: 20,
            	                        end: end,
            	                        showDetail: false,
            	                        zoomLock: true   //当设置为true时选择区域不能伸缩
            	    	            };
        	                    self.option.grid.y2=60;
        	                }
        	                self.option.xAxis[0].data = xaxisData;
        	                self.option.series[0].data = seriesData;
        	                self.chartObj.setOption(self.option);
        	                self.chartObj.refresh();
        	            }else{
        	            	hideChart(self.chartId,true);
        	            }
    	        },
    	        //点击事件
    	        clickEvent: function (param) {
    	        	   if(param.data.hasChildren==0){
    	        		   return;
    	        	   }
    	        	   teamAvgComContrastObj.reload(param.data.organId,teamAvgComContrastObj.yearMonth);
    	        },backEvent:function(){
    	        	var self=this;
    	        	$(this.backId).click(function(){
    	        		if(self.stack.length>1){
    	        			var organId=self.stack[self.stack.length-2];
    	        			self.stack.splice(self.stack.length-1,1);
    	        			 teamAvgComContrastObj.reload(organId,teamAvgComContrastObj.yearMonth,true);
		  				}
    	        	});
    	        },btnEvent:function(){
    	        	var self=this;
    	      	    $(".select-time-div .show_context").click(function(e){
    	      	    	var show=$(this).parent().children(".expand_div").is(":hidden");
    	      	    	$(this).removeClass("active");
    	      	    	$(this).parent().children(".expand_div").hide();
    	      	    	if(show==true){
    	      	    		$(this).parent().children(".expand_div").show();
    	      	    		$(this).addClass("active");
    	      	    	}
    	      	    });
    	      	   
    	      	    $(".select-time-div .btn").unbind("click").click(function(e){
    	      	    	$(this).parent().parent().parent().children(".show_context").removeClass("active");
    	      	    	$(this).parent().parent().hide();
    	      	    	return false ;
    	      	    });
    	      	    
    	      	    $("#teamAvg—btn").click(function(e){
    	      	    	var select=$(this).parent().parent().parent().find("select");
    	      	    	var val=$(select).val();
    	      	    	var text=$(select).children("option:selected").text();
    	      	    	$(this).parent().parent().parent().find(".show_context span").text(text);
    	      	    	self.stack=[];
    	      	    	self.reload(self.organId,val);
    	      	    });
    	        }
    		 
     }

	         /**
	          * 序列团队平均胜任度对比分析

	          */
	           var sequenceObj={
	          		 chartId:"sequenceChart",
	          		 backId:"#sequence_back",
	          		 chartObj:null,
	          		 click:true,
	          		 stack:[],
	          		 option:{
	          			tooltip: tooltip,
		 	            toolbox: {
		 	                show: true,
		 	                feature: {
		 	                    dataView: {
		 	                        readOnly: true
		 	                    },
		 	                    magicType: {
		 	                        type: ["line", "bar"],
		 	                        show: false
		 	                    }
		 	                }
		 	            },
		 	            xAxis: [
		 	                {
		 	                    type: "value",
//		 	                    splitNumber: 5,
		 	                    axisTick: {
							     	show : false,
							     	lineStyle: {
							             color: '#666666'
							         }
							     },
		 	                    splitLine: {
		 	                        show: true,
		 	                        lineStyle : {
		 	                        	color : '#e4e4e4'
		 	                        }
		 	                    },
		 	                    axisLine: {
		 	                        show: false,
		 	                        lineStyle: {
		 	                            color: '#666666',
		 	                            width: 1
		 	                        }
		 	                    },
		 	                    axisLabel: {
		 	                        show: true,
		 	                        formatter: function (value) {
		 	                            return value*100+"%";
		 	                        }
		 	                    }
		 	                }
		 	            ],
		 	            yAxis: [
		 	                     {type: "category",
								     splitLine: {show: false},
								     axisTick: {
								     	show : false,
								     	lineStyle: {
								             color: '#666666'
								         }
								     },
								     data: [],
								     axisLine: {
								         lineStyle: {
								             color: '#666666',
								             width: 1
								         }
								     },
								     axisLabel: {
								         itemStyle: {
								             color: '#666666'
								         },
								         textStyle: {
								             color: '#666666',
								             fontSize: 12,
								             fontFamily: "'微软雅黑', 'verdana'"
								         }
								     }}
		 	            ],
	          	            series: [
	          	                {
	          	                    name: "序列团队平均胜任度",
	          	                    type: "bar",
	          	                    data: [],
	          	                    itemStyle: {
	          	                    	normal: {
	        	                            color: "#5cb7f1",
	        	                            label: {
	        	                                show: true,
	        	                                formatter: function (a, b, c) {
	        	                                    return ((a.value*100).toFixed(0))+"%";
	        	                                }
	        	                            }
	        	                        },
	        	                        emphasis: {
	        	                            color: 'rgba(0,0,0,0)'
	        	                        }
	        	                    },
	          	                    barWidth: 30,
	          	                }
	          	            ],
	          	            grid: {
	          	                borderColor: "#ffffff",
	          	                x2: 40,
	          	                y:90,
	          	            },
	          	            color: ["#3285C7"]
	          	        },
	          		 init:function(organId,time,sequenceId){
	          			 if(this.chartObj==null){
	          				 this.chartObj=echarts.init(document.getElementById(this.chartId));
	          				 this.chartObj.on(ecConfig.EVENT.CLICK, this.clickEvent);
	          				 this.backEvent();
	          				 this.btnEvent();
	          				 this.organId=organId;
	          			 }
	          			 if(!sequenceId){
	          				this.click=true;
	          			 }else{
	          				this.click=false;
	          			 }
	          			this.yearMonth=time;
	          			 this.getData(organId,time,sequenceId);
	          		 },getData: function (organId,time,sequenceId) {
	          			 var self=this;
	          			 self.organId=organId;
	          			 $(sequenceObj.backId).hide();
	          			 loading(self.chartId);
	          			 $.ajax({
	          		  			url : urls.getSequenceCompetencyUrl,
	          		  			data : {
	          		  				organId : organId,
	          		      			yearMonth:time,
	          		      		    sequenceId:sequenceId
	          		      		},
	          		  			type : 'post',
	          		  			success : function(data){
	          		  				self.loadChart(data);
	          		  				if(!self.click){
	          		  					$(sequenceObj.backId).show();
	          		  				}
	          		  				
	          		  			},
	          		  			error : function(){}
	          		  		});
	          	           
	          	        },loadChart:function(data){
	          	        	 var self = this;
	              	            if (data!= undefined&&data.length>0) {
	              	            	hideChart(self.chartId);
	              	                var yaxisData = [], seriesData = [];
	              	                $.each(data, function (n, v) {
	              	                    seriesData.push({"value": v.rate, "sequenceId": v.id,"click":self.click});
	              	                  yaxisData.push(v.name);
	              	                });
	              	                self.option.yAxis[0].data = yaxisData;
	              	                self.option.series[0].data = seriesData;

	              	                self.chartObj.clear();
	              	                self.chartObj.setOption(self.option);
	              	                self.chartObj.refresh();
	              	            }else{
	              	            	hideChart(self.chartId,true);
	              	            }
	          	        },
	          	        //点击事件
	          	        clickEvent: function (param) {
	          	        	   if(sequenceObj.click==false){
	          	        		   return;
	          	        	   }
	          	        	 sequenceObj.init(sequenceObj.organId,sequenceObj.yearMonth,param.data.sequenceId);
	          	        	abilityObj.init(sequenceObj.organId,sequenceObj.yearMonth,param.data.sequenceId);
	          	        },backEvent:function(){
	          	        	var self=this;
	          	        	$(this.backId).click(function(){
          	        			sequenceObj.init(sequenceObj.organId,sequenceObj.yearMonth);
          	        			abilityObj.init(sequenceObj.organId,sequenceObj.yearMonth);
	          	        	});
	          	        },btnEvent:function(){
	          	        	var self=this;
	          	    	    $("#ability—btn").click(function(e){
	          	    	    	var select=$(this).parent().parent().parent().find("select");
	          	    	    	var val=$(select).val();
	          	    	    	var text=$(select).children("option:selected").text();
	          	    	    	$(this).parent().parent().parent().find(".show_context span").text(text);
	        	      	    	self.init(self.organId,val);
	        	      	    	abilityObj.init(self.organId,val);
	          	    	    });
	          	        }
	          		 
	           }   
	         
	         /**
	          * 职级团队平均胜任度对比分析

	          */
	           var abilityObj={
	          		 chartId:"abilityChart",
	          		 chartObj:null,
	          		 stack:[],
	          		 option:{
	          			tooltip: {
	          		      trigger: 'axis',
	          		      axisPointer: {
	          		          type: 'line',
	          		          lineStyle:{
	          		          	color: '#999999',
	          		          	type: 'dashed',
	          		          	width: 1
	          		          }
	          		      },formatter: function (a, b, c) {
//	          		          return ((a.value*100).toFixed(0))+"%";
//	          		    	  console.log(a)
	          		    	var html="&nbsp;"+a[0][1]+"&nbsp;:&nbsp;"+((a[0][2]*100).toFixed(0))+"%&nbsp;";
	          		    	    return html;
	          		      }
	          		  },
		 	            toolbox: {
		 	                show: false,
		 	                feature: {
		 	                    dataView: {
		 	                        readOnly: true
		 	                    },
		 	                    magicType: {
		 	                        type: ["line", "bar"],
		 	                        show: false
		 	                    }
		 	                }
		 	            },
		 	            yAxis: [
		 	                {
		 	                    type: "value",
		 	                   splitNumber: 5,
		 	                    splitLine: {
		 	                        show: true,
		 	                        lineStyle : {
		 	                        	color : '#e4e4e4'
		 	                        }
		 	                    },
		 	                    axisLine: {
		 	                        show: true,
		 	                        lineStyle: {
		 	                            color: '#666666',
		 	                            width: 1
		 	                        }
		 	                    },
		 	                    axisLabel: {
		 	                        show: true,
		 	                        formatter: function (value) {
		 	                            return value*100+"%";
		 	                        }
		 	                    },
		 	                   axisTick: {
							     	show : false,
							     	lineStyle: {
							             color: '#666666'
							         }
							     }
		 	                }
		 	            ],
		 	            xAxis: [
		 	                   {type: "category",
								     splitLine: {show: false},
								     axisTick: {
								     	show : true,
								     	lineStyle: {
								             color: '#666666'
								         }
								     },
								     data: [],
								     axisLine: {
								         lineStyle: {
								             color: '#666666',
								             width: 1
								         }
								     },
								     axisLabel: {
								         itemStyle: {
								             color: '#666666'
								         },
								         textStyle: {
								             color: '#666666',
								             fontSize: 12,
								             fontFamily: "'微软雅黑', 'verdana'"
								         }
								     }}
		 	            ],
	          	            series: [
	          	                {
	          	                    name: "职级团队平均胜任度",
	          	                    type: "bar",
	          	                    data: [],
	          	                     itemStyle: {
	          	                    	normal: {
	        	                            color: "#5cb7f1",
	        	                            label: {
	        	                                show: true,
	        	                                formatter: function (a, b, c) {
	        	                                    return ((a.value*100).toFixed(0))+"%";
	        	                                }
	        	                            }
	        	                        },
	        	                        emphasis: {
	        	                            color: 'rgba(0,0,0,0)'
	        	                        }
	        	                    },
	          	                    barWidth: 30,
	          	                }
	          	            ],
	          	            grid: {
	          	                borderColor: "#ffffff",
	          	                x2: 20,
	          	                y:90,
	          	            },
	          	            color: ["#3285C7"]
	          	        },
	          		 init:function(organId,time,sequenceId){
	          			 if(this.chartObj==null){
	          				 this.chartObj=echarts.init(document.getElementById(this.chartId));
	          			 }
	          			
	          			 this.getData(organId,time,sequenceId);
	          		 },getData: function (organId,time,sequenceId) {
	          			 var self=this;
	          			 loading(self.chartId);
	          			 $.ajax({
	          		  			url : urls.getAbilityCompetencyUrl,
	          		  			data : {
	          		  				organId : organId,
	          		      			yearMonth:time,
	          		      			sequenceId:sequenceId
	          		      		},
	          		  			type : 'post',
	          		  			success : function(data){
	          		  				self.loadChart(data);
	          		  				
	          		  			},
	          		  			error : function(){}
	          		  		});
	          	           
	          	        },loadChart:function(data){
	          	        	 var self = this;
	              	            if (data!= undefined&&data.length>0) {
	              	            	hideChart(self.chartId);
	              	                var xaxisData = [], seriesData = [];
	              	                $.each(data, function (n, v) {
	              	                    seriesData.push({value:v.rate,count:v.count});
	              	                  xaxisData.push(v.name);
	              	                });
	              	                self.option.xAxis[0].data = xaxisData;
	              	                self.option.series[0].data = seriesData;

	              	                self.chartObj.clear();
	              	                self.chartObj.setOption(self.option);
	              	                self.chartObj.refresh();
	              	            }else{
	              	          	hideChart(self.chartId,true);
	              	            }
	          	        }
	          		 
	           }   
	         
	         /**
	          * 人员面板
	          */
	  var empTabelObj={
		   	tableId:"#emp_tabel",
		   	searchBoxId:"#searchBox",
		   	load:false,
		   	loadSearchBox:false,
		   	searchBoxConfig:{//人员明细筛选Option
		             height: 200,
			             attach: [{label: "组织机构", type: "organTree", data: [{name: "organId", options: {}}]},
			                      {
			                 label: "岗位胜任度",
			                 type: "input",
			                 data: [{
			                     name: "start",
			                     type:"num",
			                     max:3,
			                     className:"positon-box-input",
			                     rightLabel:"%",
			                     value:"",
			                 }, {name: "end" ,
			                	   className:"positon-box-input",
			                	   rightLabel:"%",
			                	   type:"num",
			                	   max:3,
			                	 value:"",
			                	 }]
			             }],

			             // 重写组件里的onClick事件
			             onClick: function () {
			            	 empTabelObj.reloadSearch();
			             }, loadComple: function (o) {
			            	 $("#emp_searchTxt").keydown(function (e) {
			         	        if (e.keyCode == 13) {
			         	            $("#emp_detail_search").click();
			         	        }
			         	    });
			         	 	 $("#emp_detail_search").click(function(){
			         	 		 var text=$("#emp_searchTxt").val();
			         	 	     var time=$("#emp_searchTime").val();
			         	 		 empTabelObj.reload(text,time);
			         	 	 });
			             }
			         },
		   	option:{
		    	   url:urls.getEmpByNameUrl,
		    	   postData:{ 
		    		  
		                },
		    	   splitNum:4,
		    	   type:"post",
		    	   splitHeight:10,
		    	   modelHeight:tableHeight,
		    	   height:388,
		    	   model:$("#emp_table_model").html(),
		    	   formatModel:function(el,o,index){
		    		   var result=$(el);
		    		   var chart=result.find(".competency-card-chart");
		    		   var model=$($("#circle_model").html());
		    		   var percent=o.rate*100;
		    		   var calssName=getColorClassByCom(o.rate);
		    		   $(result).find(".detail").css("background-color",getColorByCom(o.rate));
		    		   var cans = model.find('.competency-level-canvas');
		    		   cans.attr("id","emp_tabel_chart-"+index);
		    		   $(model).find('.num>span').text(percent); 
		    		   $(chart).append(model);
		    		   
		    		   $(result).find('.detail_username').text(o.empName); 
		    		   $(result).find('.detail_position').text(o.positionName); 
		    		   
		    		   $(result).find('.high_div .rate').html(o.high.reallyScoreName); 
		    		   $(result).find('.high_div .name').html(o.high.name); 
		    		   
		    		   $(result).find('.low_div .rate').html(o.low.reallyScoreName); 
		    		   $(result).find('.low_div .name').html(o.low.name); 
		    		   
		    		   $.each(o.fail,function(i,o){
		    			  if(i==0){
		    				  var div= '<div  class="position-wdb-left-panel">'+
		         				'<span  class="position-wdb-name">'+o.name+'</span>'+
			        			'<span  class="position-wdb-name">'+o.reallyScoreName+'</span>'+
			        			'</div>';
		    				  $(result).find('.wdb').append(div);  
		    			  } else if(i==1){
		    				  var div= '<div  class="position-wdb-right-panel">'+
		         				'<span  class="position-wdb-name">'+o.name+'</span>'+
			        			'<span  class="position-wdb-name">'+o.reallyScoreName+'</span>'+
			        			'</div>';
		    				  $(result).find('.wdb').append(div);  
		    			  }
		    		   });
		    		
		    		   $(result).find(".competency-card-all").click(function(){
		    			   $("#ability-dimension-modal").modal('show');
		    			   var indicator=[ ];
		    			   seriesData=[
			   		                   {
					                       value : [],
					                       name : '能力得分'
					                   },
					                    {
					                       value : [],
					                       name : '能力标准'
					                   }
					               ];
		    			  $.each(o.list,function(i,obj){
		    				  indicator.push({text:obj.name,max:5});
		    				  seriesData[0].value.push({"value":obj.reallyScore,label:obj.reallyScoreName});
		    				  seriesData[1].value.push({"value":obj.expectScore,label:obj.expectScoreName});
		    			  });
		    			   abilityDimensionChart.option.polar[0].indicator=indicator;
		    			   abilityDimensionChart.option.series[0].data=seriesData;
		    		   });
		    		   return result;
		    	   },loadRowComple:function(el,o,index){
		    		   var color=getColorByCom(o.rate);
		    		   var can=document.getElementById("emp_tabel_chart-"+index);
		    		   var cans = can.getContext('2d');
		    		   cans.scale(2,2);
		    		   drawArc(cans,40,40,30,1.5*Math.PI,-0.5*Math.PI,'#e0e0e0',10,1);
			    		 drawArc(cans,40,40,30,1.5*Math.PI,(1.5-o.rate*2)*Math.PI,color,10,1);
			    			
			    		 drawText(cans,color,"15px Arial",(o.rate*100).toFixed(0)+'%',40, 46); 
		    	   }
		       },
			init:function(organId,time){
				 this.organId=organId;
				 this.option.postData={ 
					   organId: organId,
					   yearMonth: time}
				 if(!this.load){
					 this.load=true;
					 $(this.tableId).cardTabel(this.option);
						this.searchBoxConfig.attach[0].data[0].options={id: reqOrgId, text: reqOrgName};
					 this.searchBox = $(this.searchBoxId).searchBox3(this.searchBoxConfig);
				 }else{
					
					 $(this.tableId).clearCardTableData().setCardTableParam({
	                      postData: {organId: this.organId, yearMonth:time}
	                  }).reloadCardTable();
				 }
				 //
				 
			},reload:function(text,time){
				  $(this.tableId).clearCardTableData().setCardTableParam({
                      postData: {organId: this.organId, keyName:text,yearMonth:time}
                  }).reloadCardTable();
			},reloadSearch:function(){
				   var params = $.extend(true, {}, this.searchBox.getSelectData(), {yearMonth:time});
				   $(this.tableId).clearCardTableData().setCardTableParam({
	                      postData: params
	                  }).reloadCardTable();
			},initSearchBox:function(data){
//				var options=[];
//				  $.each(data, function (idx, o) {
//		  				options.push({key: o.organId, value: o.organName});
//		 	         });
//				  this.searchBoxConfig.attach[0].data[0].options=options;
//				  if(this.loadSearchBox){
//					  this.searchBox.destroy();
//				  }
//				this.searchBox = $(this.searchBoxId).searchBox3(this.searchBoxConfig);
//				this.loadSearchBox=true;
			},setOrganTreeValue:function(id,text){
				if(this.searchBox==undefined)return;
				this.searchBox.setValue("organId",{id:id, text:  text});
			}
		};
	  
	  /**
	   * 岗位面板
	   */
	  var positionTabelObj={
			   	tableId:"#position_tabel",
			   	load:false,
			   	organId:reqOrgId,
			   	time:time,
			   	option:{
			    	   url:urls.getPositionTableUrl,
			    	   postData:{ 
			    		 
			                },
			    	   splitNum:4,
			    	   type:"post",
			    	   splitHeight:10,
			    	   modelHeight:tableHeight,
			    	   height:388,
			    	   model:$("#position_table_model").html(),
			    	   formatModel:function(el,o,index){
			    		   var result=$(el);
			    		   var chart=result.find(".competency-card-chart");
			    		   var model=$($("#circle_model").html());
			    		 
			    		   var calssName=getColorClassByCom(o.rate);
			    		   $(result).find(".detail").css("background-color",getColorByCom(o.rate));
			    		   var cans = model.find('.competency-level-canvas');
			    		   cans.attr("id","position_tabel_chart-"+index);
			    		   $(chart).append(model);
			    		   $(result).find('.detail_position_big').text(o.positionName); 

			    		   $(result).find('.high_emp_div .rate').html(o.empList[0].rate*100+"%"); 
			    		   $(result).find('.high_emp_div .name').html(o.empList[0].empName); 
			    		   
			    		   $(result).find('.low_emp_div .rate').html(o.empList[1].rate*100+"%"); 
			    		   $(result).find('.low_emp_div .name').html(o.empList[1].empName); 

			    		   $(result).find('.high_dimension_div .rate').html((o.highDimension.rate*100).toFixed(0)+"%"); 
			    		   $(result).find('.high_dimension_div .name').html(o.highDimension.name); 
			    		   
			    		   $(result).find('.low_dimension_div .rate').html((o.lowDimension.rate*100).toFixed(0)+"%")
			    		   $(result).find('.low_dimension_div .name').html(o.lowDimension.name); 
			    		   
			    		   $(result).find('.total_emp').text(o.totalEmp); 
			    		   
			    		   $(result).find(".competency-card-all").click(function(){
			    			   var content=$("#competency-level-modal").find(".content_table .content");
			    			   $(content).html("");
			    			   $.each(o.list,function(i,obj){
			    				   var m=$($("#dimension_model").html());
			    				   var rate=obj.rate;
			    				   var className=getColorClassByCom(rate);
			    				   $(m).find(".custom_model_name").html(obj.name);
			    				   $(m).find(".progress_bar_fill").css("width",(rate*100).toFixed(0)+"%");
			    				   $(m).find(".progress_bar_fill").addClass("progress_bar_fill_"+className);
			    				   $(m).find(".progress_bar").addClass("progress_bar_"+className);
			    				   $(m).find(".progress_bar_value").html((rate*100).toFixed(0)+"%")
			    				   $(content).append(m);
				    		   });
				    		   
			    			   $("#competency-level-modal").modal('show');
			    			 	 postionCompetencyTabelObj.init(positionTabelObj.organId,positionTabelObj.time,o.positionId);
			    		   });
			    		   return result;
			    	   },loadRowComple:function(el,o,index){
			    		   var color=getColorByCom(o.rate);
			    		   var can=document.getElementById("position_tabel_chart-"+index);
			    		   var cans = can.getContext('2d');
			    		   cans.scale(2,2);
			    		   drawArc(cans,40,40,30,1.5*Math.PI,-0.5*Math.PI,'#e0e0e0',10,1);
				    		 drawArc(cans,40,40,30,1.5*Math.PI,(1.5-o.rate*2)*Math.PI,color,10,1);
				    		 drawText(cans,color,"15px Arial",(o.rate*100).toFixed(0)+'%',40, 46); 
			    	   }
			       },
				init:function(organId,time){
					 this.organId=organId;
					 this.time=time;
					 this.option.postData={ 
						   organId: organId,
			               yearMonth: time}
					 if(!this.load){
						 this.load=true;
						 this.btnEvent();
						 $(this.tableId).cardTabel(this.option);
					 }else{
						 $(this.tableId).clearCardTableData().setCardTableParam({
		                      postData: {organId: this.organId, yearMonth:time}
		                  }).reloadCardTable();
					 }
				},reload:function(text,time){
						this.time=time;
					  $(this.tableId).clearCardTableData().setCardTableParam({
	                      postData: {organId: this.organId, yearMonth: time,keyName:text}
	                  }).reloadCardTable();
				},btnEvent:function(){
					var self=this;
					 /**
				 	  * 岗位 搜索事件
				 	  */
				 	 
				 	$("#position_searchTxt").keydown(function (e) {
				        if (e.keyCode == 13) {
				            $("#position_detail_search").click();
				        }
				    });
				 	 $("#position_detail_search").click(function(){
				 		 var text=$("#position_searchTxt").val();
				 		 var time=$("#position_searchTime").val();
				 		positionTabelObj.reload(text,time);
				 	 });
				     
				}
			};
    
	  $('#ability-dimension-modal').on('shown.bs.modal', function () {
		  abilityDimensionChart.init();
		});
	  /**
	     * 能力维度 雷达图
	     */
	    var abilityDimensionChart = {
	    	chartObj:null,
	        chartId: 'ability-dimension-chart',
	        option:{
	            tooltip: {
	                trigger: 'axis',
//	                backgroundColor : 'rgba(0,0,250,0.2)',
	                formatter:function(a){
//	                	console.log(a)
	                	var index=a[0].dataIndex;
	                	var result=a[0].indicator+"<br/>";
	                	result+=a[0].name+" : "+a[0].data.value[index].label+"<br/>";
	                	result+=a[1].name+" : "+a[1].data.value[index].label+"<br/>";
	                	return result;
	                }
	            },
		       legend: {
		           x : 'center',
		           y : 'bottom',
		           data:['能力得分','能力标准']
		       },
		       polar : [
		          {
		              indicator : [
		                  
		               ]
		           }
		       ],
		       series : [
		           {
		               type: 'radar',
		               data : [
		                  
		               ]
		           }
		       ]
		   },
		   _init:false,
	        init: function () {
	        	if(!this._init){
	        		this._init=true;
	        		this.chartObj = echarts.init(document.getElementById(this.chartId));
	        	}
	            this.initData();
	        },
	        initData: function () {
	        	 var self = this;
	        	 self.chartObj.clear();

                 self.chartObj.setOption(self.option);
                 self.resize();
	        },resize:function(){
	        	 this.chartObj.resize();
	        }
	    };
	    
	    /**
	        * 弹出的 岗位能力 横向分页表格
	        */
	 	  var postionCompetencyTabelObj={
				   	tableId:"#postion-competency-tabel",
				   	load:false,
				   	option:{
				    	   url:urls.getPositionEmpTableUrl,
				    	   postData:{ 
				    		  
				                },
				           horizontal:true,
				    	   type:"post",
				    	   modelWidth:182,
				    	   splitHeight:10,
				    	   modelHeight:266,
				    	   height:286,
				    	   model:$("#cardTab-tr-horizontal-model").html(),
				    	   formatModel:function(el,o,index){
				    		   var result=$(el);
				    		   var content=$(result).find(".content_table .content");
			    			   $(content).html("");
			    			   $(result).find(".user-name").html(o.empName);
			    			   $.each(o.list,function(i,obj){
			    				   var m=$($("#dimension_emp_model").html());
			    				   var rate=obj.rate;
			    				   var className=getColorClassByCom(rate,true);
			    				   $(m).find(".progress_bar_fill").css("width",(rate*100).toFixed(0)+"%");
			    				   $(m).find(".progress_bar_fill").addClass("progress_bar_fill_"+className);
			    				   $(m).find(".progress_bar").addClass("progress_bar_"+className);
			    				   $(m).find(".progress_bar_value").html(obj.reallyScoreName);
			    				   $(content).append(m);
				    		   });
				    		   var cans = result.find('.competency-level-chart');
				    		   cans.children().attr("id","competency-level-"+index);
				    		   return result;
				    	   },loadRowComple:function(el,o,index){
				    		   var color=getColorByCom(o.rate);
				    		   var can=document.getElementById("competency-level-"+index);
				    		   var cans = can.getContext('2d');
				    		   cans.scale(2,2);
				    		   	drawArc(cans,30,30,25,Math.PI,0,'#e0e0e0',10);
					    		 drawArc(cans,30,30,25,Math.PI,(1+o.rate)*Math.PI,color,10);
					    		 drawText(cans,color,"16px Arial",(o.rate*100).toFixed(0)+'%',30, 29); 
				    	   },
				       },
					init:function(organId,time,positionId){
						this.organId=organId;
						this.positionId=positionId;
						this.yearMonth=time;
						var postData={ 
								   organId: organId,
								   positionId:positionId,
								   yearMonth: time,
								   keyName:'',
						}
						this.option.postData=postData;
						if(!this.load){
							this.load=true;
							this.btnEvent();
							 $(this.tableId).cardTabel(this.option);
						}else{
							 $(this.tableId).clearCardTableData().setCardTableParam({
			                      postData: postData
			                  }).reloadCardTable();
						}
					},reload:function(text){
						var self=this;
						  $(this.tableId).clearCardTableData().setCardTableParam({
		                      postData: { 
								   organId: self.organId,
								   positionId:self.positionId,
								   yearMonth: self.yearMonth,
								   keyName:text,
						}
		                  }).reloadCardTable();
					},btnEvent:function(){
						var self=this;
						$("#competency-level-modal-btn").click(function(){
							var text=$("#competency-level-modal-text").val();
							self.reload(text);
						});
					}
				};
	    
	    /**
	     * 对比 向导
	     */
	   var contrastGuideObj={
			   positionId:"",
			   positionName:"",
			   modelId:"#guide-modal",
			   btnId:"#guide-btn",
//			   organId:reqOrgId,
			   empIds:[],
			   parmData:{yearNum:3,continueNum:2,star:4},
			   init:function(){
				   if(this.positionId==""){
					   this.select2();
					   this.btnClick();
					   $(this.modelId).modal('show');
				   }else{
					   contrastObj.init();
				   }
			   },btnClick:function(){
				   var self=this;
				   $(this.btnId).click(function(e){
				 		var val=$("#guide_position").val();
				 		if(val==null){
				 			alert("请选择岗位");
				 			return;
				 		}
				 		self.positionId=val;
				 		var guide_emp1=$("#guide_emp1").val();
				 		var guide_emp2=$("#guide_emp2").val();
				 		var guide_emp3=$("#guide_emp3").val();
				 		self.empIds=[];
				 		if(guide_emp1!=null){
				 			self.empIds.push(guide_emp1);
				 		}
				 		if(guide_emp2!=null){
				 			self.empIds.push(guide_emp2);
				 		}
				 		if(guide_emp3!=null){
				 			self.empIds.push(guide_emp3);
				 		}
				 		if((guide_emp1!=null&&(guide_emp1==guide_emp2||guide_emp1==guide_emp3))
				 			||(guide_emp2!=null&&guide_emp2==guide_emp3)
				 			){
				 			alert("不能添加相同人员");
				 			return;
				 		}
				 		self.queryContrastTable(true);
				 		$("#contrast-position").html($("#guide_position").text());
				 		$(self.modelId).modal('hide');
					 });
			   },select2:function(){
			 		initPositionSelect2("guide_position");
			 		initPositionSelect2("guide_position2");
			 		initEmpSelect2("guide_emp1");
			 		initEmpSelect2("guide_emp2");
			 		initEmpSelect2("guide_emp3");
			   },queryContrastTable:function(updatePositon){
				   var self=this;
				   $.ajax({
   		  			url : urls.getPositionImagesUrl,
   		  			data : {
//   		  				organId : self.organId,
   		      			positionId:self.positionId,
   		      		    yearNum:self.parmData.yearNum,
   		      		    continueNum:self.parmData.continueNum,
   		      		    star:self.parmData.star,
   		      		    updatePositon:updatePositon
   		      		},
   		  			type : 'post',
   		  			success : function(data){
   		  				var base=data.base;
   		  				if(base==undefined){
   		  					base={sex:{k:"",v:""}
   		  						  ,degree:{k:"",v:""}
   		  						  ,seniority:{k:"",v:""}
   		  						  ,age:{k:"",v:""}
   		  					};
   		  				}
   		  				//高绩效
   	   		  				$.each($("#sexRow").children(":first").find("span"),function(i,o){
   	   		  					var obj=base.sex;
   	   		  					if(i==0){
   	   		  						$(this).html(obj.k);
   	   		  					}else{
   	   		  					$(this).html(obj.v);
   	   		  					}
   	   		  				});
   	   		  				$.each($("#degreeRow").children(":first").find("span"),function(i,o){
   			  					var obj=base.degree;
   			  					if(i==0){
   			  						$(this).html(obj.k);
   			  					}else{
   			  					$(this).html(obj.v);
   			  					}
   			  				});
   			   		  		$.each($("#seniorityRow").children(":first").find("span"),function(i,o){
   				  					var obj=base.seniority;
   				  					if(i==0){
   				  						$(this).html(obj.k);
   				  					}else{
   				  					$(this).html(obj.v);
   				  					}
   				  			});
   				   		  	$.each($("#ageRow").children(":first").find("span"),function(i,o){
   			  					var obj=base.age;
   			  					if(i==0){
   			  						$(this).html(obj.k);
   			  					}else{
   			  					$(this).html(obj.v);
   			  					}
   				   		  	});
   		  				if(!updatePositon){
   		  					return;
   		  				}
   		  				//维度信息
   		  				$("#dimensionTableId").html("");
   		  				var dimension=data.dimension;
   		  				if(dimension!=undefined){
	   		  				$.each(dimension,function(i,o){
	   		  					var tr=$("<tr></tr>");
	   		  					$(tr).attr("id",o.id);
	   		  					$(tr).attr("score",o.expectScore);
	   		  					var td=$("<td></td>")
	   		  					$(td).attr("class","contrast-td-one");
	   		  				    $(td).html(o.name+"&nbsp;&nbsp;&nbsp;");
	   		  			        $(td).append("<span>"+o.expectScoreName+"</span>");
	   		  			         $(tr).append(td);
	   		  			        for(var j=0;j<4;j++){
	  		  			         $(tr).append("<td></td>");
	   		  			        }
	   		  			        $("#dimensionTableId").append(tr);
	   		  				});
	   		  			$.each(contrastObj.empObjs,function(i,o){
	   		  				if(o!=null){
	   		  				contrastObj.dimensionTable(o.list,i+1);
	   		  				}
	   		  			});
   		  				}
   		  				contrastObj.init(self.empIds);
   		  			},
   		  			error : function(){}
   		  		});
			   }
	    }
	    
	  
       
	   /**
	     * 对比 面板
	     */
	   var contrastObj={
			   empId: '#empIds',
			   load:false,
			   empIds:"",
			   empObjs:[null,null,null,null],
			   init:function(empIds){
				   var self=this;
				   if(!this.load){
					   this.load=true;
					   this.btnClick();
					   this.initTop();
					   this.panelScrollEvent();
				   }
				   $.each(empIds,function(i,empId){
					   self.requestData(empId,i+1);
				   })
			   },empAssignInfo:function(empObj,index){
				   this.empObjs[index-1]=empObj;
				   this.baseData(empObj,index);
				   this.dimensionTable(empObj.list,index);
			   },baseData:function(empObj,index){
				    var self = this;
		            var imgPath = _.isEmpty(empObj.imgPath) ? webRoot + '/assets/img/base/u-default.png' : empObj.imgPath;
		            var imgHtml = '<div class="top-ct-circle">';
		            imgHtml += '<input type="hidden" id="empId' + index + '" value="' + empObj.empId + '">';
		            imgHtml += '<div class="img-rc-head-del btn-header-remove"></div>';
		            imgHtml += '<img class="img-circle img-rc-head" src="' + imgPath + '"></div>';
		            imgHtml += '<div class="top-div-float"><div class="top-div-name">' + empObj.empName + '</div></div>';
		
		            var _imgParent = $($('#contrastObj .top-div')[index - 1]);
		            _imgParent.html(imgHtml);
		            _imgParent.find('.btn-header-remove').bind('click', removeEmpBtnEvent);
		
		            self.dimensionTable(empObj.list,index);
		            
		            self.compareDiff($('#sexRow').children()[index], empObj.sex == 'm' ? '男' : '女');
		            self.compareDiff($('#ageRow').children()[index], empObj.age);
		            self.compareDiff($('#degreeRow').children()[index], empObj.degree);
		            self.compareDiff($('#seniorityRow').children()[index], parseFloat(empObj.seniority));
			   },btnClick:function(){
				   var self=this;
//				    $(".contrast-table-top-too").unbind("click").click(function(e){
//				    	  e.stopPropagation();
//				    });
//				    $(".contrast-table-top-too").children().unbind("click").click(function(e){
//				    	  e.stopPropagation();
//				    });;
				   	  //显示 隐藏 岗位选择
				     $(".top-left-label").click(function(){
				    	 if($(this).next().is(":hidden")){
					    		$(this).next().show();
					    	}else{
					    		$(this).next().hide();
					    	}
//				 		  $(this).next().show()
				 	  });
//				     //隐藏岗位选择
//				     $($(".top-left-label").next().children(':first')).click(function(){
//				 		  $(this).parent().hide();
//				 	  });
				     //选择岗位
					 $("#guide_position2_btn").click(function(e){
						 var val=$("#guide_position2").val();
					 		if(val==null){
					 			return;
					 		}
					 		contrastGuideObj.positionId=val;
					 		contrastGuideObj.queryContrastTable(true);
					 		$("#contrast-position").html($("#select2-guide_position2-container").attr("title"));
					 		$(this).parent().parent().parent().parent().hide();
						  });
				     //高绩效配置
				 	 $(".float2 .config_div").click(function(e){
				 		  e.stopPropagation();
				 		$("#high-custom-modal").modal('show');
					  });
				 	 //高绩效配置   取消
				 	$("#high-custom-modal .btn-default").click(function(e){
				 		 
				 		$("#high-custom-modal").modal('hide');
					  });
				 	 //高绩效配置   确认
				 	$("#high-custom-modal .btn-primary").click(function(e){
				 		var yearNum=$("#yearNumSelect").val();
				 		var continueNum=$("#continueNumSelect").val();
				 		var star=$("#starSelect").val();
				 		if(contrastGuideObj.parmData.yearNum!=yearNum
				 				||contrastGuideObj.parmData.continueNum!=continueNum
				 				||contrastGuideObj.parmData.star!=star	
				 		){
				 			contrastGuideObj.parmData.yearNum=yearNum;
					 		contrastGuideObj.parmData.continueNum=continueNum;
					 		contrastGuideObj.parmData.star=star;
					 		$.each($(".float2 .config_note span"),function(i,o){
					 			if(i==0){
					 				$(this).text(yearNum);
					 			}else if(i==1){
					 				$(this).text(continueNum);
					 			}else if(i==2){
					 				$(this).text(star);
					 			}
					 		});
					 		
					 		contrastGuideObj.queryContrastTable(false);	
				 		}
				 		$("#high-custom-modal").modal('hide');
					  });
				 	 //关键词搜索
				 	 $(".float3 .keyword-search").click(function(e){
						  	e.stopPropagation();
					    	if($(this).next().is(":hidden")){
					    		$(this).next().show();
					    	}else{
					    		$(this).next().hide();
					    	}
						  	 
					 });
				 	 $(".float3 .expand_div").click(function(e){
						  	e.stopPropagation();
					 });
				 	 $("#keyword-search-btn").click(function(e){
				 		  $(this).parent().parent().hide();
						  	var keyword=$("#keyword-search").val();
						  	$.each(contrastObj.empObjs,function(i,o){
						  		if(o!=null){
						  			$.ajax({
					 		  			url : urls.getKeyWordInfoUrl,
					 		  			data : {
					 		      			empId : o.empId,
					 		      			keyword:keyword
					 		      		},
					 		  			type : 'post',
					 		  			success : function(data){
					 		  			
					 		  				var tr=self.loadKeywordCol(keyword);
					 		  				var resume=parseInt(data.resumeNum);
					 		  				var train=parseInt(data.trainNum);
					 		  				var file=parseInt(data.fileNum);
					 		  				var td=$(tr).children()[i+1];
					 		  				if(resume==0&&train==0&&file==0){
					 		  					$(td).html("无匹配信息");
					 		  				}else{
					 		  					if(resume>0){
					 		  						var span=$("<span class='click-show'>过往履历<span class='keyword-col-num'>"+resume+"</span>条</span>");
					 		  						$($("#keyword-tab").children()[0]).children("span").text(resume);
					 		  						self.loadKeywordColClick(span,"resume");
					 		  						$(td).append(span);
					 		  						keywordTrainTableObj.init(data.resume);
					 		  					}
					 		  					
					 		  					if(train>0){
					 		  						if(resume>0){
						 		  						$(td).append("<br>");
						 		  					}
					 		  						var span=$("<span  class='click-show'>培训经历<span class='keyword-col-num'>"+train+"</span>条</span>");
					 		  						$($("#keyword-tab").children()[1]).children("span").text(train);
					 		  						self.loadKeywordColClick(span,"train");
					 		  						$(td).append(span);
					 		  						keywordResumeTableObj.init(data.train);
					 		  					}
					 		  					if(file>0){
					 		  						if(resume>0||train>0){
						 		  						$(td).append("<br>");
						 		  					}
					 		  						var span=$("<span  class='click-show'>测评报告<span class='keyword-col-num'>"+file+"</span>个</span>");
					 		  						$($("#keyword-tab").children()[2]).children("span").text(file);
					 		  						self.loadKeywordColClick(span,"file");
					 		  						$(td).append(span);
					 		  						keywordFileTableObj.init(data.file);
					 		  					}
					 		  				}

					 		  			},
					 		  			error : function(){}
					 		  		});
						  		}
						  	})
						  	
					 });
				 	
			   },loadKeywordCol:function(keyword){
				   var tr=$("<tr></tr>");
	  					$(tr).attr("value",keyword);
	  					var td=$("<td></td>")
	  					$(td).attr("class","contrast-td-one");
	  				    $(td).html(keyword);
	  			         $(tr).append(td);
	  			        for(var j=0;j<4;j++){
	  			         $(tr).append("<td></td>");
	  			        }
	  			        $("#keywordTable").append(tr);
	  			        return tr;
			   },loadKeywordColClick:function(span,type,list){
				   $(span).click(function(){
					   $("#keyword-modal").modal("show");
					   $("#keyword-tab").children().removeClass("index-jxmb-btn-select");
		
					   $("#keyword-tab").children("div[page='"+"keyword-"+type+"-tab"+"']").addClass("index-jxmb-btn-select");
	 			         
						 $("#keyword-resume-tab").removeClass("select_tab");
						 $("#keyword-train-tab").removeClass("select_tab");
						 $("#keyword-file-tab").removeClass("select_tab");
	 			         $("#keyword-"+type+"-tab").removeClass("select_tab").addClass("select_tab");
					   
				   });
			   },initTop:function(){
				   var self = this;
				   self.initHeaderHTML();
			   },requestData: function (empId,index) {
		           var self = this;
	        	   $.post(urls.getEmpContrastInfoUrl, {'empId': empId,'yearMonth':time}, function (rs) {
	        		   if (_.isEmpty(rs)) {
                           return;
                       }
	        		   self.empIds += ',' + empId;
	        		   self.empAssignInfo(rs, index);
	        	   });
		       },initHeaderHTML: function (index) {
		           var _headerObj = $('#contrastObj .top-div');
		           if (!_.isUndefined(index)) {
		               _headerObj = $(_headerObj[index - 1]);
		           }
		           _headerObj.html($('#img-window').html());
		           _headerObj.find('.top-div-btn').bind('click', searchBtnEvent);
		       },panelScrollEvent:function(){
				    	    /*
				  	     表头点击事件
				  	     */
//				  	    $(".contrast-table-top").click(function () {
//				  	        if ($(this).hasClass("contrast-table-false")) {
//				  	            $(this).removeClass("contrast-table-false");
//				  	            $(this).next().show();
//				  	        } else {
//				  	            $(this).addClass("contrast-table-false");
//				  	            $(this).next().hide();
//				  	        }
//				  	    });
		
				  	    /*
				  	     菜单点击事件
				  	     */
				  	    $(".menu-line").click(function (e) {
				  	        e.stopPropagation();
				  	        var $this = $(this);
				  	        $(".menu-line").removeClass("menu-line-select");
				  	        $this.addClass("menu-line-select");
				  	        if ($this.find(".menu-line-text").text() == "回顶部") {
				  	            $(window).scrollTop(0);
				  	            return;
				  	        }
		
				  	        var resultTop = ($('.' + $this.attr("position")).offset().top) - floatNumber;
				  	        $(window).scrollTop(resultTop);
				  	    });
		
				  	    /*
				  	     scroll
				  	     */
				  	    $(window).scroll(function (e) {
				  	        if (!isCurPage||curTab!="page-three") {
				  	            return false;
				  	        }
				  	        var top = $(this).scrollTop();
				  	        //top-fa控制
				  	        if (top >= 25) {
				  	            if (!$(".top-fa").hasClass("top-fa-fixed")) {
				  	                $(".top-fa").addClass("top-fa-fixed");
				  	            }
				  	        } else {
				  	            if ($(".top-fa").hasClass("top-fa-fixed")) {
				  	                $(".top-fa").removeClass("top-fa-fixed");
				  	            }
				  	        }
				  	        //menu控制
				  	        if (top >= 140) {
				  	            $(".menu").css("top", top + 40 + "px");
				  	        } else {
				  	            $(".menu").css("top", 168 + "px");
				  	        }
				  	        var _body = this.document.body;
				  	        //top控制menu
				  	        if (top < 20) {
				  	            $(".menu-line").removeClass("menu-line-select");
				  	            $(".menu-line").eq(0).addClass("menu-line-select");
				  	        }else if(top >= _body.scrollHeight - $(window).height()){
				  	            $(".menu-line").removeClass("menu-line-select");
				  	            $(".menu-line").eq(3).addClass("menu-line-select");
				  	        }else if (top >= $(".float1").offset().top - floatNumber && top < $(".float2").offset().top - floatNumber) {
				  	            $(".menu-line").removeClass("menu-line-select");
				  	            $(".menu-line").eq(1).addClass("menu-line-select");
				  	        } else if (top >= $(".float2").offset().top - floatNumber && top < $(".float3").offset().top - floatNumber) {
				  	            $(".menu-line").removeClass("menu-line-select");
				  	            $(".menu-line").eq(2).addClass("menu-line-select");
				  	        }
				  	    });
		       },compareDiff: function (obj, value) {
   	            var _obj = $(obj);
	            var _txt = value == null ? '-' : value;
	            _obj.text(_txt);
	            var _arounds = _obj.siblings(':not(.contrast-td-one)');
	            var hasDiff = false;
	            $.each(_arounds, function (i, temp) {
	                var _tempVal = $.trim($(temp).text());
	                if (!_.isEmpty(_tempVal) && _tempVal != value) {
	                    hasDiff = true;
	                    return true;
	                }
	            });
	            if (!hasDiff) return;
	            $.each(_arounds, function (i, temp) {
	                var _temp = $(temp);
	                var _tempVal = $.trim(_temp.text());
	                if (!_.isEmpty(_tempVal)) {
	                    _temp.addClass('diff');
	                }
	            });
	            _obj.addClass('diff');
	        },dimensionTable:function(list,index){
	        	$.each(list,function(){
	        		var o=this;
	        		$.each($("#dimensionTableId").children().children(),function(){
	        			if($(this).attr("id")==o.id){
	        				var score=$(this).attr("score");
	        				var span=$("<span></span>");
	        				$(span).text(o.reallyScoreName);
	        				if(o.reallyScore<score){
	        					$(span).addClass("text-red");
	        				}
	        				$($(this).children()[index]).html(span);
	        			}
	        		});
	        	});
	        }
	    }
	   
	   var keywordResumeTableObj={
			   tableId:"#keyword-resume-table",
			   option: {
				   data: [],
			        datatype: "local",
			        altRows: true,  //设置表格行的不同底色
			        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
			        autowidth: true,
			        height: 268,//268
			        colNames: ['时间', '就职单位', '部门', '岗位', '证明人', '联系方式', '异动原因'],
			        colModel: [
			          
			            {name: 'entryDate', width: 200, sortable: false, align: 'center',formatter:function(v,o,r){
	                    	return r.entryDate+"-"+r.runOffDate;
	                    }},
	                    {name: 'workUnit', width: 160, sortable: false, align: 'center'},
			            {name: 'departmentName', width: 100, sortable: false, align: 'center'},
			            {name: 'positionName', width: 100, fixed: true, sortable: false, align: 'center'},
			            {name: 'witnessName', width: 100, fixed: true, sortable: false, align: 'center'},
			            {name: 'witnessContactInfo', width: 200, fixed: true, sortable: false, align: 'center'},
			            {name: 'changeReason', width: 160, fixed: true, sortable: false, align: 'center'}

			        ],
			        scroll: true

   	        },lode:false,
			   init:function(data){
				   if(!this.lode){
					   this.lode=true;
					   $(this.tableId).jqGrid(this.option);
				   }
			        $(this.tableId).clearGridData().setGridParam({
			            data: data,
			            rowNum: 9999999
			        }).trigger("reloadGrid");
			        this.resize();
			   },resize:function(){
				   
			   }
	   };
	  
	   var keywordTrainTableObj={
			   tableId:"#keyword-train-table",
			   option: {
				   data: [],
			        datatype: "local",
			        altRows: true,  //设置表格行的不同底色
			        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
			        autowidth: true,
			        height: 268,//268
			        colNames: ['课程名称/项目', '完成日期', '状态', '成绩', '培训机构', '所获证书', '讲师', '备注'],
			        colModel: [
			            {name: 'trainName', width: 160, sortable: false, align: 'center'},
			            {name: 'finishDate', width: 100, sortable: false, align: 'center'},
			            {name: 'statusName', width: 70, sortable: false, align: 'center'},
			            {name: 'result', width: 70, fixed: true, sortable: false, align: 'center'},
			            {name: 'trainUnit', width: 160, fixed: true, sortable: false, align: 'center'},
			            {name: 'gainCertificate', width: 160, fixed: true, sortable: false, align: 'center'},
			            {name: 'teacherName', width: 100, fixed: true, sortable: false, align: 'center'},
			            {name: 'note', width: 160, fixed: true, sortable: false, align: 'center'}
			        ],
			        scroll: true

   	        },lode:false,
			   init:function(data){
				   if(!this.lode){
					   this.lode=true;
					   $(this.tableId).jqGrid(this.option);
				   }
			        $(this.tableId).clearGridData().setGridParam({
			            data: data,
			            rowNum: 9999999
			        }).trigger("reloadGrid");
			        this.resize();
			   },resize:function(){
				   
			   }
	   };
	   var keywordFileTableObj={
			   tableId:"#keyword-file-table",
			   option: {
				   data: [],
			        datatype: "local",
			        altRows: true,  //设置表格行的不同底色
			        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
			        autowidth: true,
			        height: 268,//268
			        colNames: ['文件名',  '下载'],
			        colModel: [
			            {name: 'fileName', width: 160, sortable: false, align: 'center'},
			            {name: 'filePath', width: 100, sortable: false, align: 'center'}
			   
			        ],
			        scroll: true

   	        },lode:false,
			   init:function(data){
				   if(!this.lode){
					   this.lode=true;
					   $(this.tableId).jqGrid(this.option);
				   }
			        $(this.tableId).clearGridData().setGridParam({
			            data: data,
			            rowNum: 9999999
			        }).trigger("reloadGrid");
			        this.resize();
			   },resize:function(){
				   
			   }
	   };
       var searchEmpObj = {
    	        gridId: '#searchEmpGrid',
    	        searchTxt: null,
    	        hasInit: false,
    	        gridOption: {
    	            url: urls.getSearchEmpUrl,
    	            datatype: 'json',
    	            postData: {},
    	            mtype: 'POST',
    	            autowidth: true,
    	            height: 268,//268
    	            colNames: ['员工ID', '姓名', '部门', '操作'],
    	            colModel: [
    	                {name: 'empKey', width: 80, sortable: false, align: 'center'},
    	                {name: 'userName', width: 150, sortable: false, align: 'center'},
    	                {name: 'organName', width: 200, sortable: false, align: 'center'},
    	                {
    	                    name: 'myac',
    	                    width: 100,
    	                    fixed: true,
    	                    sortable: false,
    	                    align: 'center',
    	                    formatter: function (value, options, row) {
    	                        var html = '<span class="exist" data-row="' + options.rowId + '" data-index="' + row.empId + '">已加入</span>';
    	                        var empId = row.empId;
    	                        if (contrastObj.empIds.indexOf(empId, 0) < 0) {
    	                            html = '<a href="javascript:void(0)" data-row="' + options.rowId + '" data-index="' + row.empId + '" class="add_col" >加入</a>';
    	                        }
    	                        return html;
    	                    }
    	                }
    	            ],
    	            rownumbers: true,
    	            rownumWidth: 40,
    	            loadComplete: function (xhr) {
    	                $('.add_col').unbind('click').bind('click', addColClickEvent);
    	            },
    	            scroll: true
    	        },
    	        init: function (searchTxt) {
    	            var self = this;
    	            if (_.isNull(self.searchTxt)) {
    	                self.searchTxt = searchTxt;
    	                self.gridOption.postData = {'keyName': searchTxt};
    	                $(self.gridId).jqGrid(self.gridOption);
    	                self.hasInit = true;
    	            }
    	            if (searchTxt != self.searchTxt) {
    	                self.searchTxt = searchTxt;
    	                self.initGrid(searchTxt);
    	            }
    	        },
    	        initGrid: function (keyTxt) {
    	            var self = this;
    	            $(self.gridId).clearGridData().setGridParam({
    	                postData: {'keyName': keyTxt}
    	            }).trigger("reloadGrid");
    	            self.resizeGrid();
    	        },
    	        resizeGrid: function () {
    	            var self = this;
    	            var parentDom = $('#gbox_' + self.gridId.split('#')[1]).parent();
    	            $(self.gridId).setGridWidth(parentDom.width());
    	        }
    	    };


       /**
        * 搜索按钮点击事件
        */
       function searchBtnEvent() {
           var _this = $(this);
           var idx = _this.parents('.top-obj').index() + 1;
           $('#search-index').val(idx);

           var _modal = $('#search-modal');
           _modal.modal('show');

           $('#search-btn').click(function () {
               var searchTxt = $.trim($('#search-txt').val());
               if (!_.isEmpty(searchTxt)) {
                   searchEmpObj.init(searchTxt);
               }
           });

           $("#search-txt").keydown(function (e) {
               if (e.keyCode == 13) {
                   $("#search-btn").click();
               }
           })
       }

       /**
        * 移除人员按钮事件
        */
       function removeEmpBtnEvent() {
           var _this = $(this);
           var _parent = _this.parents('.top-obj');
           var idx = _parent.index() + 1;
           //删除已存在的empid
           var empid = $('#empId' + idx).val();
           if (contrastObj.empIds.indexOf(empid, 0) != -1) {
               if (contrastObj.empIds.indexOf(empid, 0) == 0) {
            	   contrastObj.empIds = contrastObj.empIds.replace(empid + ',', '');
               } else {
            	   contrastObj.empIds = contrastObj.empIds.replace(',' + empid, '');
               }
           } 
//           $("#searchEmpGrid").clearGridData();
//           $("#search-txt").val("");
           $.each($("#searchEmpGrid").find(".exist"),function(i,o){
        	   var rowId= $(this).attr("data-row");
               var empId=  $(this).attr("data-index");
               if(empid==empId){
            	 var  html = '<a href="javascript:void(0)" data-row="' + rowId + '" data-index="' + empId+ '" class="add_col" >加入</a>';
            	 $(this).parent().html(html);
               }
              
           });
           $('.add_col').unbind('click').bind('click', addColClickEvent);
//           _this.parent().html('<span exist>已加入</span>');
           contrastObj.empObjs[idx-1]=null;
           _parent.html($('#img-window').html());

           $(_parent.find('.top-div-btn')).bind('click', searchBtnEvent);

           var _rowObjs = $('#accordion .contrast-table tr');
           $.each(_rowObjs, function (i, obj) {
               var _childs = $(obj).children();
               var _child = $(_childs[idx]);
               _child.html('');
               _child.removeClass('diff').removeClass('high');
               var _sibling = _child.siblings(':not(.contrast-td-one)');
               var eq = true;
               var record = "";
               var first = true;
               $.each(_sibling, function (i, obj) {
                   if (eq) {
                       if (_.isEmpty($.trim($(obj).text()))) {

                       } else if (first || record == $.trim($(obj).text())) {
                           record = $.trim($(obj).text());
                       } else {
                           eq = false;
                           return;
                       }
                   }
                   first = false;
               });
               if (eq) {     //取消同级对比
                   _sibling.removeClass('diff').removeClass('high');
               }
           });
       }


       function addColClickEvent(e) {
           var _this = $(this);
           var empId = _this.data('index');
           var rowId = _this.data('row');
           $.post(urls.getEmpContrastInfoUrl, {'empId': empId,'yearMonth':time}, function (rs) {
               if (_.isEmpty(rs)) {
                   return;
               }
               var idx = $('#search-index').val();
               contrastObj.empIds += ',' + _this.data('index');
//               rowIds[empId] = rowId;
               var empObj = rs;
               contrastObj.empAssignInfo(empObj, idx);
               $('#search-modal').modal('hide');
               var rowId= _this.attr("data-row");
               var empId= _this.attr("data-index");
               _this.parent().html('<span class="exist" data-row="' + rowId + '" data-index="' + empId + '">已加入</span>');
           });
       }
       
       

 	  /**
 	   * 绘制半圆环比例
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
 	  	
 	  	/**
 	  	 * 根据胜任度获取颜色
 	  	 */
 		function getColorByCom(val){
 			val=(val*100).toFixed(0);
           if(val>=rangeObj.high*100){
        	   return "#00b165";
           }else if(val>=rangeObj.low*100){
        	   return "#F09200";
           }else{
        	   return "#E7191B";
           }
 	  	}
 		function getColorClassByCom(val,isEmp){
 			val=(val*100).toFixed(0);
 			if(undefined==isEmp||!isEmp){
 				 if(val>=rangeObj.high*100){
 		         	   return "high";
 		            }else if(val>=rangeObj.low*100){
 		         	   return "middle";
 		            }else{
 		         	   return "low";
 		            }
 			}else{
 				if(val>=100){
		         	   return "high";
		            }else{
		            	return "low";
		            }
 			}
           
  	  	}
 	  	/**
 	  	 * 设置胜任度范围
 	  	 */
 	  	function setRange(obj){
 	  		rangeObj=obj;
 	  		$("#config_high").val((obj.high*100).toFixed(0));
 	  		$("#config_high_show").val((obj.high*100).toFixed(0));
// 	  		$("#config_middle1").val(obj.middle[0]*100);
// 	  		$("#config_middle2").val(obj.middle[1]*100);
 	  		$("#config_low").val((obj.low*100).toFixed(0));
 	  		$("#config_low_show").val((obj.low*100).toFixed(0));
           topObj.option.series[0].axisLine.lineStyle.color[0] = [obj.low, '#e7191b'];
           topObj.option.series[0].axisLine.lineStyle.color[1] = [obj.high, '#F09200'];
           topObj.option.series[0].axisLine.lineStyle.color[2] = [1, '#00b165'];
 	  	}
 	
 	  	/**
 	  	 * 岗位 select2 
 	  	 */
 	  	function initPositionSelect2(selectId){
 	  		$("#" + selectId).select2({
 	            language: {
 	                errorLoading: function () {
 	                    return "无法载入结果。"
 	                },
 	                inputTooLong: function (e) {
 	                    var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
 	                    return n
 	                },
 	                inputTooShort: function (e) {
 	                    var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
 	                    return n
 	                },
 	                loadingMore: function () {
 	                    return "载入更多结果…"
 	                },
 	                maximumSelected: function (e) {
 	                    var t = "最多只能选择" + e.maximum + "个项目";
 	                    return t
 	                },
 	                noResults: function () {
 	                    return "未找到结果"
 	                },
 	                searching: function () {
 	                    return "搜索中…"
 	                }
 	            },
 	            width: '100%',
 	            allowClear: true,
 	            multiple: false,
 	            openOnEnter: true,
 	            placeholder: "输入岗位",
 	            ajax: {
 	                url: urls.selectPosition,
 	                dataType: 'json',
 	                delay: 500,
 	                type: "POST",
 	                data: function (params, page) {
 	                    var ps = {
 	                        keyName: params && params.term ? params.term : "",
 	                        page: params.page == null ? 1 : params.page,
 	                        rows: 30
 	                    };
 	                    return ps;
 	                },
 	                processResults: function (data) {
 	                    var lists = [];
 	                    $.each(data.rows, function (i, item) {
 	                        lists.push({id: item.k, text: item.v});
 	                    });
 	                    return {
 	                        results: lists,
 	                        pagination: {
 	                            more: data.total > data.page
 	                        }
 	                    };
 	                }
 	            }
 	        }).val(null);
 	  	}
 		/**
 	  	 * 人员 select2 
 	  	 */
 	  	function initEmpSelect2(selectId){
 	  		$("#" + selectId).select2({
 	            language: {
 	                errorLoading: function () {
 	                    return "无法载入结果。"
 	                },
 	                inputTooLong: function (e) {
 	                    var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
 	                    return n
 	                },
 	                inputTooShort: function (e) {
 	                    var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
 	                    return n
 	                },
 	                loadingMore: function () {
 	                    return "载入更多结果…"
 	                },
 	                maximumSelected: function (e) {
 	                    var t = "最多只能选择" + e.maximum + "个项目";
 	                    return t
 	                },
 	                noResults: function () {
 	                    return "未找到结果"
 	                },
 	                searching: function () {
 	                    return "搜索中…"
 	                }
 	            },
 	            width: '100%',
 	            allowClear: true,
 	            multiple: false,
 	            openOnEnter: true,
 	            placeholder: "输入员工",
 	            ajax: {
 	                url: urls.getSearchEmpUrl,
 	                dataType: 'json',
 	                delay: 500,
 	                type: "POST",
 	                data: function (params, page) {
 	                    var ps = {
 	                        keyName: params && params.term ? params.term : "",
 	                        page: params.page == null ? 1 : params.page,
 	                        rows: 30
 	                    };
 	                    return ps;
 	                },
 	                processResults: function (data) {
 	                    var lists = [];
 	                    $.each(data.rows, function (i, item) {
 	                        lists.push({id: item.empId, text: item.userName});
 	                    });
 	                    return {
 	                        results: lists,
 	                        pagination: {
 	                            more: data.total > data.page
 	                        }
 	                    };
 	                }
 	            }
 	        }).val(null);
 	  	}
 	  	
 	  	/**
 	  	 * 页面初始化
 	  	 */
 	 var windowObj={
 			 init:function(){
 				 var self=this;
 				$.ajax({
 					url : urls.getBaseConfigUrl,
 					type : 'post',
 					success : function(data){
 						setRange(data);
 		 		  	  self.initBaseConfig(data);
 		 		  	  self.initPanelEvent();
 					},
 					error : function(){}
 				});
 			 },initPanelEvent:function(){
 				 //首页数据加载
// 				topObj.init(reqOrgId,time);
// 		 		teamAvgComThrendObj.init(reqOrgId);
// 		 		teamAvgComContrastObj.init(reqOrgId,time);
// 		 		sequenceObj.init(reqOrgId,time);
// 		 		abilityObj.init(reqOrgId,time);
	 		  	  /*切换左边导航*/
	 		        $(".leftListDiv").click(function () {
	 		            var _this = $(this);
	 		            curTab=$(this).attr("page");
	 		            if (_this.hasClass("selectList")) {
	 		                return;
	 		            } else {
	 		                $(".rightBodyPage").hide();
	 		                $(".leftListDiv").removeClass("selectList");
	
	 		                var $page = _this.attr("page");
	 		                $("#" + $page).show();
	 		                _this.addClass("selectList");
	 		                if ($page == 'page-one') {
	// 		                    preDistributionObj.resizeAll()
	 		                } else  if ($page == 'page-two') {
	 		               	 if( $(this).attr("load")=="true"){
	 		               		 return;
	 		               	 }
	 		               	 $(this).attr("load",true);
	 		               	 empTabelObj.init(reqOrgId,time);
	 		               	 positionTabelObj.init(reqOrgId,time);
	 		                } else  if ($page == 'page-three') {
	 		                	contrastGuideObj.init();
	 		                }
	 		            }
	 		        });
	 			  	  /*
	 			      	岗位 人员明细 点击切换函数
	 			      */
	 			     $(".index-jxmb-btn").click(function () {
	 			         var _self = $(this);
	 			         $(".index-jxmb-btn").removeClass("index-jxmb-btn-select");
	 			         _self.addClass("index-jxmb-btn-select");
	 			         var $page = _self.attr("page");
	 			         $.each($(this).parent().children(),function(){
	 			        	 var _p=$(this).attr("page");
	 			        	 $("#"+_p).removeClass("select_tab");
	 			         });
	 			         $("#"+$page).removeClass("select_tab").addClass("select_tab");
	 			     });
 			 },initBaseConfig:function(data){
 				 /**
	 		         * 初始化配置事件
	 		         */
	 		  	  $("#tooltip1").find(".index-common-title-right .icon").click(function(){
	 		  		 $("#config-modal").modal('show');
	 		  	  });
	 		  	  
	 		  	 //基础配置   取消
				 	$("#config-modal .btn-default").click(function(e){
				 		 
				 		$("#config-modal").modal('hide');
					  });
				 	 //基础配置   确认
				 	$("#config-modal .btn-primary").click(function(e){
				 		var config_high=$.trim($("#config_high").val());
				 		var config_low=$.trim($("#config_low").val());
				 		
				 		if(config_low!=""&&config_high!=""){
				 			var low=config_low/100;
				 			var high=config_high/100;
				 			setRange({high:high,low:low});
				 			$.ajax({
			         			url : urls.updateBaseConfigUrl,
			         			data : {
			         				low : low,
			         				high:high,
			             		},
			         			type : 'post',
			         			success : function(data){
			         			    $("#searchTxt").val("");
			       	             initAll(reqOrgId);
			         			},
			         			error : function(){}
			         		});
				 			$("#config-modal").modal('hide');
				 		}
					  });
 			 }
 	 };
 	 windowObj.init();
 	 function initAll(id) {
         reqOrgId = id;
         clearSelect2();
         timeLineObj.init(reqOrgId);
         /**
			  * 初始化时间下拉框
			  */
			$(".select-time").html("");
	 		 $.each(selectTimeVals,function(i,o){
	 			 var option="<option value='"+o.key+"'>"+o.val+"</option>"
	 			$(".select-time").append(option); 
	 		 });
	 		$(".select-time-div").find(".show_context span").text(timeValue);
         $.ajax({
	  			url : urls.querySubOrganUrl,
	  			data : {
	      			organId : reqOrgId
	      		},
	  			type : 'post',
	  			success : function(data){
	  			 /**
		  			  * 初始化下级组织下拉框
		  			  */
	  				 $("#teamAvgCom_orgain").html("");
	  			  $.each(data, function (idx, o) {
	  				 $("#teamAvgCom_orgain").append($("<div value='"+o.organId+"' class='option'>"+o.organName+"</div>"));
	 	         });
	  			empTabelObj.initSearchBox(data);
	  			}
	  		});
         
            topObj.init(reqOrgId,time);
            teamAvgComThrendObj.resetPosition();
            teamAvgComThrendObj.resetOrgan();
	 		teamAvgComThrendObj.init(reqOrgId);
	 		teamAvgComContrastObj.stack=[];
	 		teamAvgComContrastObj.init(reqOrgId,time);
	 		sequenceObj.init(reqOrgId,time);
	 		abilityObj.init(reqOrgId,time);

	 		var load=$("div[page='page-two']").attr("load");
	 		if(load==true||load=="true"){
	 			 empTabelObj.init(reqOrgId,time);
               	 positionTabelObj.init(reqOrgId,time);
	 		}
	 		 $(this).attr("load",true);
     }

     initAll(reqOrgId);
     
     /**无数据/隐藏数据加载*/
     function hideChart(chartId,hide) {
    	  var $chart = $("#" + chartId);
          if (hide) {
              $chart.children('div.loadingmessage').remove();
              $chart.children().hide();
              $chart.append("<div class='loadingmessage'>暂无数据</div>");
          } else {
              $chart.children('div.loadingmessage').remove();
              $chart.children().show();
          }
       
     }
     /**暂无数据*/
     function loading(chartId) {
         var $chart = $("#" + chartId);
         $chart.children('div.loadingmessage').remove();
         $chart.children().hide();
         $chart.prepend("<div class='loadingmessage'>数据加载中</div>");
     }
     
     function clearSelect2(){
    	 $("#teamAvg-select").empty();
    	 $("#guide_position").empty();
    	 $("#guide_position2").empty();
    	 $("#guide_emp1").empty();
    	 $("#guide_emp2").empty();
    	 $("#guide_emp3").empty();
    	 $("input[type='text']").each(function(){
    		 var id=$(this).attr("id");
    		 if(id=="config_high_show"||id=="config_high"||id=="config_low"||id=="config_low_show")return;    		 
    		 $(this).val("");
    	 });
    	 
    	
//    	 $("#emp_searchTxt").val("");
//    	 $("#start").val("");
//    	 $("#end").val("");
     }
     
// 	$.ajax({
//			url : urls.indexEmpTaskUrl,
//
//			type : 'post',
//			success : function(data){
//				
//			},
//			error : function(){}
//		});
});
