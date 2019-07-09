require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'underscore', 'utils',"appBase","chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
            structureBudgetAnalyse:webRoot+'/mobile/talentStructure/getBudgetAnalyse.do', //编制分析
            getAllSeq:webRoot+'/mobile/talentStructure/getAllSeq.do', //编制分析
            structureGetTalentStuctureData:webRoot+'/mobile/talentStructure/getTalentStuctureData.do',  //人力结构数据
            structureGetAbEmpCountBarBySeqId:webRoot+'/mobile/talentStructure/getAbEmpCountBarBySeqId.do'  //职位序列分布 职级分布
        };
    
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var LSLChartMaxWidth=parseInt($("#LSLChart").css("max-width"));
    var LSLChartMinWidth=parseInt($("#LSLChart").css("min-width"));
    var time=$("#time").val();
    function loadLSL(){
    	$(".managerAndEmployee").css("width",($(window).width()-30)+"px");
    	var topWidth=$(".structure-top").width();
        var LSLWidth=topWidth/2;
        var normalWidth,chartWidth=LSLWidth;
        if(LSLWidth>LSLChartMaxWidth){
        	chartWidth=LSLChartMaxWidth;
        }else if(LSLWidth<LSLChartMinWidth){
        	chartWidth=LSLChartMinWidth;
        }	
        normalWidth=topWidth-chartWidth-5;
        if(normalWidth>400){
        	normalWidth-=15;
        }
       // $("#LSLChart").css("width",chartWidth);
        $("#LSLNormal").css("width",normalWidth);
        window.fixedTop = $(".tabPanel").offset().top;
    }
    
    $(window).bind(resizeEvent,function(){
    	loadLSL();
    
    });
    loadLSL();

    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	 if(targetAreaId=="xltjPanel"){
    		objPositionSequence.resize();
    		objPositionRank.resize();
    	}else if(targetAreaId=="zzfbPanel"){
    		organDistribution.resize();
    		workLocation.resize();
    	}else if(targetAreaId=="tzfxPanel"){
    		degree.resize();
    		seniority.resize();
    	}
    });

    /**
     * 编制  top
     * @type 
     */
    var budgetAnalyseObj = {
        chartId: 'LSLChart',
        data:{
            value:0,
            greenValue:0.95,
            yellowVlue:1
        },
        chartObj: null,
        option:{
        	animation:animation,
            toolbox: {
                show: false
            },
            series: [
                {
                    name: "编制",
                    type: "gauge",
                    data: [
                        {
                            value: 0,
                            name: "使用率"
                        }
                    ],
                    radius: [0, '99%'],
                   // max: 25,
                    splitNumber: 0,
                    min: 0,
                    max: 100,
                    axisLine: {
                        lineStyle: {
                            color: [[0.95, "rgb(106, 175, 43)"], [1, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]],
                            width: 10
                        },
                        show: true
                    },
                    title: {
                        show: false
                    },
                    detail: {
                        show: false
                    },
                    splitNumber: 4,
                    pointer: {
                        length: "80%",
                        width: 5
                    },
                    radius: "100%",
                    center: ["50%", "55%"],
                    axisTick: {
                        show: true,
                        splitNumber: 1,
                        length: 10
                    },
                    splitLine: {
                        lineStyle: {
                            width: 1
                        },
                        show: true,
                        length: 10
                    }
                }
            ],
            tooltip: {
                show: false
            }
        },
        init: function (organId) {
            var self = this;
//            self.chartObj = initEChart(self.chartId);
            self.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            self.organId = organId;
            $.ajax({
                url: urls.structureBudgetAnalyse,
                type: "post",
                async: false,
                data: {'organId': organId},
                success: function (data) {
//                	 if(data.hasBudgetPer) {
//                         self.data.value = (data.budgetPer * 100).toFixed(2);
//                         self.data.greenValue = data.normal*100;
//                         self.data.yellowVlue = data.risk*100;
//                         self.renderChart();
//                     }
                     $(".top-content-panel .number").text(data.hasBudgetPer?data.number:0);
                     $(".top-content-panel .empCount").text(data.empCount);
                     $(".top-content-panel .usableEmpCount").text(data.hasBudgetPer?data.usableEmpCount:0);
                     window.fixedTop = $(".tabPanel").offset().top;
                }
            });
        },
        renderChart:function(){
            var self=this;
            //图表
            var maxValue=self.data.value>100?parseInt(self.data.value/10)*10+10:100;
            self.option.series[0].data[0].value=self.data.value;
            self.option.series[0].max=maxValue;
            var g=self.data.greenValue/maxValue;
            var y=self.data.yellowVlue/maxValue;
            if(g>1){
                self.data.greenValue=self.data.yellowVlue=1;
            }else {
                self.data.greenValue=g;
                self.data.yellowVlue=y>1?1:y;
            }
            self.option.series[0].axisLine.lineStyle.color=[[self.data.greenValue, "rgb(106, 175, 43)"], [self.data.yellowVlue, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]];
            self.chartObj.setOption(self.option);

        },
        resize: function(){
            this.chartObj.resize();
        }
    };
    
    
    /*
     * 人力结构请求数据
     * */
     var objRequestData={
         data:null,
         init:function(organId){
             var self=this;
             $.post(urls.structureGetTalentStuctureData,{"organId":organId},function(data){
                 self.data=data;
                 self.manager();
                 self.rank();
                 self.positionSequence();
                 self.positionRank();
                 self.workLocation();
                 self.organDistribution();
                 self.degree();
                 self.seniority();
             });
         },
         //管理者员工分布
         manager:function(){
             var self=this;
             //饼图
             var data=self.data.abilityEmpCountPie;
             var lis = [], legend = [];
             var manager = 0, employee = 0, i = 0;
             $.each(data, function (n, v) {
                 if (n == "员工") {
                     employee += v;
                 } else {
                     manager += v;
                 }
             });
             $(".managerAndEmployee .manager-normal span").text(manager);
             $(".managerAndEmployee .employee-normal span").text(employee);
             if(manager!=0&&employee!=0){
            	 var rate="1:"+(employee/manager).toFixed(1);
                 $("#glzblPanel .title").text("管理者与员工比例  "+rate);
             }
            
         },
         //职级
         rank:function(){
             var self=this;
             //饼图
             var dataPie=self.data.abilityCurtEmpCountPie;
            // objRank.init(dataPie);

         },
         //职位序列分布 序列分布
         positionSequence:function(){
             var self=this;
             //条形图
             var dataRowBar=self.data.seqEmpCountBar;
             removeEmptyTip($('#' + objPositionSequence.chartId));
             if(dataRowBar==undefined||_.isEmpty(dataRowBar)){
            	  showEmptyTip($('#' + objPositionSequence.chartId));
                  return;
             }
             objPositionSequence.init(dataRowBar);
             
         },
         //职位序列分布 职级分布
         positionRank:function(){
             var self=this;
             //柱形图
             var dataBar=self.data.abilityCurtEmpCountPie;
             objPositionRank.init(dataBar);
             var recodeData={};
             $.ajax({
                 url: urls.getAllSeq,
                 type: "post",
                 async: true,
                 success: function (data) {
                	 $.each(data,function(i,o){
                		 var clazz="button";
                		 if(i==0){
                			 clazz+=" condition";
                		 }
                		 var option='<span class="'+clazz+'" data="'+o.k+'">'+o.v+'</span>';
                		 $("#allSeq").append(option);
                	 });
//                	  $(".btn-group").find(".button").click(function(e){
//                      	if($(this).hasClass("condition")){
//                      		 e.preventDefault();
//                      		 return false;
//                      	}
//                      	 $.each($(this).parent().children(),function(){
//                         	   $(this).removeClass("condition");
//                           });
//
//                      	 $(this).addClass("condition");
//                      	
//                      });
                	
                	 $("#allSeq").children().on(clickEvent,function(){
                		 var _t=this;
                    	   var seqId=$(this).attr("data");
                    	   if(seqId==""){
                    		 objPositionRank.init(dataBar);
                    		 setBtnClass(_t);
                    	   }else{
                    		   var recode=recodeData[seqId];
                    		   if(recode!=undefined){
                    			 objPositionRank.init(recode);
                    			 setBtnClass(_t);
                    		   }else{
	                      		   $.post(urls.structureGetAbEmpCountBarBySeqId, {
	                      	           "organId": reqOrganId,
	                      	           "seqId": seqId
	                      	       }, function (data) {
	                      	    	   recodeData[seqId]=data;
	                      	           objPositionRank.init(data);
	                      	         setBtnClass(_t);
	                      	       });  
                    		   }
                    	   }
                       });
                 }
             });
             
         },//工作地点
         workLocation:function(){
         	 var self=this;
              //柱形图
              var dataBar=self.data.workplaceEmpCount;
              removeEmptyTip($('#' + workLocation.chartId));
              if(dataBar==undefined||_.isEmpty(dataBar)){
             	  showEmptyTip($('#' + workLocation.chartId));
                   return;
              }
              workLocation.init(dataBar);
         },//组织分布
         organDistribution:function(){
         	var self=this;
             //柱形图
             var dataBar=self.data.organEmpCount?self.data.organEmpCount:[];
             removeEmptyTip($('#' + organDistribution.chartId));
             if(dataBar==undefined||_.isEmpty(dataBar)){
            	  showEmptyTip($('#' + organDistribution.chartId));
                  return;
             }
             organDistribution.init(dataBar);
         },//学历分布
         degree:function(){
         	var self=this;
             //柱形图
             var dataBar=self.data.degreeEmpCountPie;
             removeEmptyTip($('#' + degree.chartId));
             if(dataBar==undefined||_.isEmpty(dataBar)){
            	  showEmptyTip($('#' + degree.chartId));
                  return;
             }
             degree.init(dataBar);
             
           
         },//司龄分布
         seniority:function(){
         	var self=this;
             //柱形图
             var dataBar=self.data.companyAgeEmpCountPie;
             removeEmptyTip($('#' + seniority.chartId));
             if(dataBar==undefined||_.isEmpty(dataBar)){
            	  showEmptyTip($('#' + seniority.chartId));
                  return;
             }
             seniority.init(dataBar);
             
            
         }
     }
   
	budgetAnalyseObj.init(reqOrganId);
	objRequestData.init(reqOrganId);
    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    
    /*
     * 职位序列分布 序列分布
     * */
     var objPositionSequence={
    		 chartId:"positionSequenceChart",
         chart: echarts.init(document.getElementById('positionSequenceChart')),
         option:{
        	 animation:animation,
             xAxis: [
                 {
                	 name:"(人数)",
                     type: "value",
                     boundaryGap: [0, 0.01],
                     splitLine: splitLine,
                     axisLine: false,
                     axisLabel: {
                    	 name:"(人数)"
                     }
                 }
             ],
             yAxis: [
                 {
                     type: "category",
                     data: [],
                     axisTick: false,
                     axisLine: false,
                     splitLine: false
                 }
             ],
             series: [
                 {
                     name: "职位序列分布",
                     type: "bar",
                     data: [],
                     barWidth :barWidth,
                     itemStyle: {
                         normal: {
                        	 color: barColor,
                             label: {
                                 show: true,
                                 formatter: function(a){
                                	 var curr=a.value;
                                	 var total=0;
                                	 $.each(a.series.data,function(){
                                		 total+=this.value;
                                	 });
                                     var rate=total>0?(curr*100/total).toFixed(2):0;
                                     return curr+"人,"+rate+"%";
                                 },
                                 textStyle: {
                                     color: "#000000"
                                 }
                             }
                         }
                     }
                 }
             ],
             grid: organGrid,
             color: ["#3285C7"]
         },
         init: function(data){
             var self=this;
             if(data==undefined){
                 console.info("职位序列分布序列分布出错了！");
             }else {
                 var yaxisData=[],seriesData=[];
                 var total=0;
                 $.each(data, function(n, v){
                     var cateName=n;
                     var seqId="";
                     var arr= n.split(',');
                     if(arr.length>1){
                         cateName=arr[1];
                         seqId=arr[0];
                     }
                     seriesData.push({"value":v,"seqId":seqId,"cateName":cateName});
                     total+=v;
                 });
                 seriesData= _.sortBy(seriesData, "value");
                 $.each(seriesData, function(i, item){
                     yaxisData.push(item.cateName);
                 });
                 self.option.series[0].name=total;
                 self.option.yAxis[0].data=yaxisData;
                 self.option.series[0].data=seriesData;

                 if(self.chart!=null)
                     self.chart.dispose();
                 self.chart=echarts.init(document.getElementById('positionSequenceChart'));
                 self.chart.setOption(self.option);
                 $('#' + self.chartId).chartTooltip({chart:self.chart,formatter:function(index,v,p){
                	 var total=v.data[0].name;
                	 var val=v.data[0].value.value;
                	 var cols=[{name:"人数",value:val,unit:"人"}];
                	 cols.push({name:"占比",value:(val/total*100).toFixed(2),unit:"%"});
                     return {title:v.name,cols:cols};
                 },toolTipTop:-44});
             }
         },resize:function(){
        	 this.chart.resize();
         }
     }
   
     /*
      * 职位序列分布 职级分布
      * */
     var objPositionRank={
    		 chartId:"positionRankChart",
         chart: echarts.init(document.getElementById('positionRankChart')),
         option:{
        	 animation:animation,
             xAxis: [{
                type: 'value',
                name:"(人数)",
                nameTextStyle:{
                	color: 'black'
                },
                
                axisLabel: {
                	name:"(人数)",
                    formatter: function (value) {
                        return value;
                    }

                },
                splitLine:splitLine,
                axisLine: false,
                axisTick: false
            }        
             ],
             yAxis: [
					{
					    type: "category",
					    data: [],
					    splitLine: false,
					    axisTick: false,
					    axisLine: false,
					    axisLabel: {
					        textStyle: {
					            color: "#000000",
					            fontSize: 13
					        },
					        interval:0
					    }
					}
             ],
             series: [
                 {
                     clickable:false,
                     name: "职级",
                     type: "bar",
                     data: [],
                     barWidth :barWidth,
                     itemStyle: {
                         normal: {
                        	 color: barColor,
                             label: {
                                 show: true,
                                 formatter: function(a){
                                	 var curr=a.value;
                                	 var total=0;
                                	 $.each(a.series.data,function(){
                                		 total+=this;
                                	 });
                                     var rate=total>0?(curr*100/total).toFixed(2):0;
                                     return curr+"人,"+rate+"%";
                                 },
                                 textStyle: {
                                     color: "#000000"
                                 }
                             }
                         }
                     }
                 }
             ],
             color: ["#3285C7"],
             grid: defaultGrid
         },
         init: function(data){
        	 var self=this;
        	 removeEmptyTip($('#' + self.chartId));
             if(data==undefined||_.isEmpty(data)){
            	  showEmptyTip($('#' + self.chartId));
                  return;
                 console.info("职位序列分布职级分布出错了！");
             }else {
                 var self = this;
                 var yaxisData=[], seriesData=[];
                 var total=0;
                 $.each(data, function(n, v){
                     total+=v;
                 });
                 
                 $.each(data, function(n, v){
//                     xaxisData.push(n+"\n"+v+"人");
//                     seriesData.push((v*100/total).toFixed(2));
                	   yaxisData.unshift(n);
                       seriesData.unshift(v);
                 });
                 self.option.series[0].name=total;
                 self.option.yAxis[0].data=yaxisData;
                 self.option.series[0].data=seriesData;
                 if(self.chart!=null)
                     self.chart.dispose();
                 self.chart=echarts.init(document.getElementById('positionRankChart'));
                 self.chart.setOption(self.option);
                 $('#' + self.chartId).chartTooltip({chart:self.chart,formatter:function(index,v,p){
                  	 var total=v.data[0].name;
                	 var val=v.data[0].value;
                	 var cols=[{name:"人数",value:val,unit:"人"}];
                	 cols.push({name:"占比",value:(val/total*100).toFixed(2),unit:"%"});
                     return {title:v.name,cols:cols};
//                	 var cols=[{name:v.name,value:(v.data[0].value).toFixed(2)+"%"}];
//
//                     return {title:v.name,cols:cols};
                 },toolTipTop:10});
             }
         },resize:function(){
        	 this.chart.resize();
         }
     }
     
     /*
      * 组织分布
      * */
     var organDistribution={
    		 chartId:"organDistributionChart",
 		chart: echarts.init(document.getElementById("organDistributionChart")),
         option:{
        	 animation:animation,
             grid: organGrid,
             xAxis: [
					{
						name:"(人数)",
					 	type: "value",
					 	axisLine: false,
					     axisLabel: {
					         formatter: "{value}",
					         textStyle: {
					             color: "rgb(0, 0, 0)",
					             fontSize: 13
					         }
					     },
					     splitLine: splitLine
					 }
             ],
             yAxis: [
					{
					 	type: "category",
					     data: [],
					     axisLine: false,
					     splitLine:false,
					     axisTick:false,
					     axisLabel: {
					         textStyle: {
					             color: "#000000",
					             fontSize: 13
					         }
					     }
					 }
                 
             ],
             series: [
                 {
                 	clickable:false,
                     type: "bar",
                     barWidth :barWidth,
                     data: [],
                     itemStyle: {
                         normal: {
                        	 color: barColor,
                             label: {
                                 show: true,
                                 textStyle: {
                                     color: "#000000"
                                 }
                             }
                         }
                     }
                 }
             ],
             color: ["#3285C7"]
         },
         init:function(data){
         	var self=this;
             if(data==undefined){
                 console.info("组织分布出错了！");
             }else{
            	 var len=0;
             	  var yAxisData = [], seriesData = [];
                   var tem=[];
                 $.each(data,function(n,v){
                    tem.push({"name":n,"value":-v});
                    len++;
                 });
                 scalChartHeight(this,len);
                 tem= _.sortBy(tem,"value").reverse();
                   $.each(tem, function (i, item) {
                       yAxisData.push(formatAxis(item.name));
                       seriesData.push(-item.value);
                   });
                   if(yAxisData.length>0) {
                       self.option.yAxis[0].data = yAxisData;
                       self.option.series[0].data = seriesData;
                       self.chart.setOption(self.option);
                       $('#' + self.chartId).chartTooltip({chart:self.chart,formatter:function(index,v,p){
                      	 var cols=[{name:v.name,value:v.data[0].value,unit:"人"}];
                           return {title:"",cols:cols};
                       },toolTipTop:-40});
                   }else{
                       if(self.chart)
                         self.chart.dispose();
                       $("#organDistributionChart").html("<div class='loadingmessage'>暂无数据</div>");
                   }
             }
         },resize:function(){
        	 this.chart.resize();
         }
         
     };
     
    
     /**
      * 工作地点分布
      */
     var workLocation={
    		 chartId:"workLocationChart",
    		 chartObj: echarts.init(document.getElementById("workLocationChart")),
     		option:getDefaultPieOption("工作地点"),
             init:function(data){
             	var self=this;
                 if(data==undefined){
                     console.info("工作地点分布出错了！");
                 }else {
                 	  var lis=[], legend = [],total=0;
                       $.each(data, function (n, v) {
                    	   total+=v;
                           lis.push({"name":n, "value":v});
                       });
                       
                       lis= _.sortBy(lis,"value").reverse();

                       $.each(lis, function (i, v) {
                    	   legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + v.name + '</span></div>');
                  
                       });
                       self.option.series[0].data = lis;
                       self.chartObj.setOption(self.option);
                     appendLegend($("#"+self.chartId),legend);
                     $('#' + self.chartId).chartTooltip({
         				chart : self.chartObj,
         				formatter : function( p) {
         					var cols = [  ];
         					$.each(lis, function(i, item) {
         						cols.push({
         							name : item.name,
         							value : item.value+"人" ,
         							rate : "("+(item.value/total*100).toFixed(2)+"%)"
         						});

         					});
         					return {
         						color:colorPie,
         						title : "",
         						note:"",
         						cols : cols
         					};
         				},toolTipTop:-97
         			});
                 }
             },resize:function(){
            	 this.chartObj.resize();
             }
         };
     /**
      * 学历分布
      */
     var degree={
    		 chartId:"degreeChart",
    		 chartObj: echarts.init(document.getElementById("degreeChart")),
     		table: null,
             option:getDefaultPieOption("学历"),
             init:function(chartData){
             	this.initChart(chartData);
             },initChart:function(data){
             	var self=this;
                 if(data==undefined){
                     console.info("学历分布出错了！");
                 }else {
                      var lis = [], legend = [],i=0,total=0;
                      $.each(data, function (n, v) {
                          if(v>0) {
                              var sort = 0;
                              switch (n) {
                                  case "博士":
                                  {
                                      sort = 4;
                                      break;
                                  }
                                  case "硕士":
                                  {
                                      sort = 3;
                                      break;
                                  }
                                  case "本科":
                                  {
                                      sort = 2;
                                      break;
                                  }
                                  case "大专":
                                  {
                                      sort = 1;
                                      break;
                                  }
                              }
                              total+=v;
                              lis.push({"name": n, "value": v, "sort": sort});
                              i++;
                          }
                      });
                      var lis = _.sortBy(lis, "sort");
                      var seriesData=[];
                     $.each(lis, function(i, item){
                         seriesData.push(item);
                         legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + item.name + '</span></div>');
                     })

                      self.option.series[0].data = seriesData;
                      self.chartObj.setOption(self.option);
                      appendLegend($("#"+self.chartId),legend);
                      $('#' + self.chartId).chartTooltip({
           				chart : self.chartObj,
           				formatter : function( p) {
           					var cols = [  ];
           					$.each(lis, function(i, item) {
           						cols.push({
           							name : item.name,
           							value : item.value+"人" ,
           							rate : "("+(item.value/total*100).toFixed(2)+"%)"
           						});

           					});

           					return {
           						color:colorPie,
           						title : "",
           						note:"",
           						cols : cols
           					};
           				},toolTipTop:-63
           			});
                 }
             },resize:function(){
             	this.chartObj.resize();
             }
         };
     /**
      * 司龄分布
      */
     var seniority={
    		 chartId:"seniorityChart",
    		 chartObj: echarts.init(document.getElementById("seniorityChart")),
     		table: null,
     		tableId: "seniorityTable",
             option:getDefaultPieOption("司龄"),
             init:function(chartData){
             	this.initChart(chartData);
             },initChart:function(data){
             	var self=this;
                 if(data==undefined){
                     console.info("司龄分布出错了！");
                 }else {
                      var lis = [], legend = [],i=0,total=0;
                      $.each(data, function (n, v) {
                          if(v>0) {
                              lis.push({"name": n, "value": v});
                              legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + '</span></div>');
                              i++;
                              total+=v;
                          }
                      });

                      self.option.series[0].data = lis;
                      self.chartObj.setOption(self.option);
                      appendLegend($("#"+self.chartId),legend);
                      $('#' + self.chartId).chartTooltip({
           				chart : self.chartObj,
           				formatter : function( p) {
           					var cols = [  ];
           					$.each(lis, function(i, item) {
           						cols.push({
           							name : item.name,
           							value : item.value+"人" ,
           							rate : "("+(item.value/total*100).toFixed(2)+"%)"
           						});

           					});

           					return {
           						color:colorPie,
           						title : "",
           						note:"",
           						cols : cols
           					};
           				},toolTipTop:-65
           			});
                 }
             },resize:function(){
             	this.chartObj.resize();
             }
         };
     $.screenZoom([
                 	objPositionSequence,
                 	objPositionRank,
                 	organDistribution,
                 	workLocation,
                 	degree,
                 	seniority
                 ]);
});