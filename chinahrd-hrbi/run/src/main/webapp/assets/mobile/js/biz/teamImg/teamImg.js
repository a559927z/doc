require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/pie',
    'underscore', 'utils',  "appBase","chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
    		getTeamImgDataUrl: webRoot + '/mobile/teamImg/getTeamImgData.do'
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();

    $(window).bind(resizeEvent,function(){
    	 constellatoryObj.resize();
         bloodObj.resize();
         sexObj.resize();
         marryObj.resize();
         ageObj.resize();
         personalityObj.resize();
    });
    

    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	if(targetAreaId=="constellatoryAndBloodPanel"){
              constellatoryObj.resize();
              bloodObj.resize();
    	}else if(targetAreaId=="sexAndMarryPanel"){
          sexObj.resize();
          marryObj.resize();
    	}else if(targetAreaId=="agePanel"){
              ageObj.resize();
    	}else if(targetAreaId=="personalityPanel"){
          personalityObj.resize();
    	}
    });
    
    
    /*==============================================================*/
    /* TOP															*/
    /*==============================================================*/
    var topObj={
    	className:"teamCardPanel",
    	init:function (data,total) {
    		$(".teamTotal").children("span").text(total);
    		var teamCards=$("."+this.className).children(".teamCard");
    		$.each(teamCards,function(i,item){
    			var span=$(item).children(".value").children("span");
    			var label=$(item).children("label");
    			if(i==0){
    				$(label).text(data.sex.k)
    				$(span).text(data.sex.v)
    			}else if(i==1){
    				$(label).text(data.age.k)
    				$(span).text(data.age.v)
    			}else if(i==2){
    				$(label).text(data.marry.k)
    				$(span).text(data.marry.v)
    			}
    		});
    	}
    
    }
    
    
    /*==============================================================*/
    /* 性别															*/
    /*==============================================================*/
    var sexColor=['#019BD9', '#FF88A2']
    var sexObj = {
    		chartObj: null,
        chartId: 'sex',
        total:1,
        option: getDefaultPieOption({name:"性别",toroidal:true,
        	color:sexColor,
        	showLine:false, formatter: function (i) {

                return i.name + '\n'  + Tc.formatNumber(i.value) + '人\n' + ((i.value / sexObj.total) * 100).toFixed(0) + '%';
            }}),
        init: function (rsData,total) {

            var self = this;
            this.total=total;
            this.option.series[0].data = rsData;
            var legend=[];
    		$.each(rsData, function(i, item) {
    			if(item.name=="男"){
    				legend[1]='<div>'+
        					'<label style="background-color: '+ sexColor[i] + ';"></label>'+
        					'<span>'+ item.name + '</span>'+
        					'</div>';
    			}else{
    				legend[0]='<div>'+
					'<label style="background-color: '+ sexColor[i] + ';"></label>'+
					'<span>'+ item.name + '</span>'+
					'</div>';
    			}
//    			legend.push('<div>'+
//    					'<label style="background-color: '+ sexColor[i] + ';"></label>'+
//    					'<span>'+ item.name + '</span>'+
//    					'</div>');
			});
			appendLegend($("#" + this.chartId), legend);
            self.chartObj.setOption(this.option, true);
           
            $('#' + this.chartId).chartTooltip({chart:this.chartObj,formatter:function(p){  
            	var cols = [  ];
					$.each(rsData, function(i, item) {
						cols.push({
							name : item.name,
							value : item.value+"人" ,
							rate : "("+(item.value/total*100).toFixed(0)+"%)"
						});
					});
					return {
						color:sexColor,
						title : "",
						note:"",
						cols : cols
					};            }});
          
            self.style();
        },
        style: function () {

            var width = $("#sex").width();
            var height = $("#sex").height();
            var _icoLeft = (width / 2) - 31;
            var _icoTop = (height / 2) - 33;

            $(".teamImg-sex").css({position: "absolute", top: _icoTop+ "px", left: _icoLeft + "px"}).removeClass("hide");
           
        },resize:function(){
        	this.style();
        	this.chartObj.resize();
        }
    };


    /*==============================================================*/
    /* 婚姻状况														*/
    /*==============================================================*/
    var marryOpt = {
            calculable: false,
            grid: {
                borderWidth: 0
            },
            xAxis: [
                {
                    type: 'category',
                    splitLine: false,
                    axisLine: false,
                    axisTick: false,
                    data: []
                }
            ],
            yAxis: [
                {
                    axisLine: false,
                    splitLine: false,
                    axisLabel: false,
                    type: 'value'
                }
            ],
            series: [
                {
                    type: 'bar',
                    clickable: false,
                    itemStyle: {
                        normal: {
                            color: function (params) {
                                return colorPie[params.dataIndex]
                            },
                            label: {
                                show: true,
                                textStyle: {color: 'black'},
                                position: 'top',
                                formatter: ''
                            }
                        }
                    },
                    data: []
                }

            ]
        };
    var marryObj = {
    	chartObj: null,
        chartId: 'marry',
        resultData: null,
        init: function (rsData) {

            var self = this;

            self.style(rsData);
        },
        style: function (rsData) {
            var self = this;
            marryOpt.xAxis[0].data = rsData.xAxisData;
            marryOpt.series[0].data = rsData.seriesData;

            self.chartObj.setOption(marryOpt, true);

            
        },resize:function(){
        	this.chartObj.resize();
        }
    };


    /*==============================================================*/
    /* 年龄															*/
    /*==============================================================*/
    
    var ageObj = {
    		chartObj: {},
        chartId: 'age',
        option: getDefaultPieOption("年龄"),
        init: function (seriesData,total) {
            this.option.series[0].data = seriesData;

            this.chartObj.setOption(this.option, true);
            var legend=[];
    		$.each(seriesData, function(i, item) {
    			legend.push('<div>'+
    					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
    					'<span>'+ item.name + '</span>'+
    					'</div>');
			});
			appendLegend($("#" + this.chartId), legend);
            $('#' + this.chartId).chartTooltip({chart:this.chartObj,formatter:function(p){  
            	var cols = [  ];
					$.each(seriesData, function(i, item) {
						cols.push({
							name : item.name,
							value : item.value+"人" ,
							rate : "("+(item.value/total*100).toFixed(0)+"%)"
						});

					});

					return {
						color:colorPie,
						title : "",
						note:"",
						cols : cols
					};            }});
        },resize:function(){
        	this.chartObj.resize();
        }

    };

    
    /*==============================================================*/
    /* 血型															*/
    /*==============================================================*/
    var bloodObj = {
    	chartObj: null,
        chartId: 'blood',
        option: getDefaultPieOption("血型"),
        init: function (seriesData,total) {

            var self = this;

            this.option.series[0].data = seriesData;
            self.chartObj.setOption(this.option, true);
            var legend=[];
    		$.each(seriesData, function(i, item) {
    			legend.push('<div>'+
    					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
    					'<span>'+ item.name + '</span>'+
    					'</div>');
			});
			appendLegend($("#" + self.chartId), legend);
            $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
            	var cols = [  ];
					$.each(seriesData, function(i, item) {
						cols.push({
							name : item.name,
							value : item.value+"人" ,
							rate : "("+(item.value/total*100).toFixed(0)+"%)"
						});

					});

					return {
						color:colorPie,
						title : "",
						note:"",
						cols : cols
					};            }});
        },resize:function(){
        	this.chartObj.resize();
        }

    };


    /*==============================================================*/
    /* 星座															*/
    /*==============================================================*/
    
    var constellatoryObj = {
        chartObj: null,
        option:getDefaultPieOption("星座"),
        chartId: 'constellatory',
        resultData: null,
        init: function (seriesData,total) {
            var self = this;
            this.option.series[0].data = seriesData;
            self.chartObj.setOption(this.option, true);
        	var legend=[];
    		$.each(seriesData, function(i, item) {
    			legend.push('<div>'+
    					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
    					'<span>'+ item.name + '</span>'+
    					'</div>');
			});
			appendLegend($("#" + self.chartId), legend);
            $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
            	var cols = [  ];
					$.each(seriesData, function(i, item) {
						cols.push({
							name : item.name,
							value : item.value+"人" ,
							rate : "("+(item.value/total*100).toFixed(0)+"%)"
						});

					});

					return {
						color:colorPie,
						title : "",
						note:"",
						cols : cols
					};            },pieSplitNumber:1});
        },resize:function(){
        	this.chartObj.resize();
        }

    };


    /*==============================================================*/
    /* 性格															*/
    /*==============================================================*/

    var personalityObj = {
    	chartObj: null,
        chartId: 'personality',
        option: getDefaultPieOption({name:"性格",toroidal:true}),
        init: function (seriesData,total) {
            var self = this;
            this.option.series[0].data = seriesData;
            self.chartObj.setOption(this.option, true);
            var legend=[];
    		$.each(seriesData, function(i, item) {
    			legend.push('<div>'+
    					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
    					'<span>'+ item.name + '</span>'+
    					'</div>');
			});
			appendLegend($("#" + self.chartId), legend);
            $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
            	var cols = [  ];
					$.each(seriesData, function(i, item) {
						cols.push({
							name : item.name,
							value : item.value+"人" ,
							rate : "("+(item.value/total*100).toFixed(0)+"%)"
						});

					});

					return {
						color:colorPie,
						title : "",
						note:"",
						cols : cols
					};            }});
        },resize:function(){
        	this.chartObj.resize();
        }
    };

    /*==============================================================*/
    /* 页面初始化													*/
    /*==============================================================*/
    var teamImgObj = {
        total: 0,
        resultData: {},
        init: function (organId) {
            var self = this;
            sexObj.chartObj = initEChart(sexObj.chartId);
            ageObj.chartObj = initEChart(ageObj.chartId);
            marryObj.chartObj = initEChart(marryObj.chartId);
            bloodObj.chartObj = initEChart(bloodObj.chartId);
            constellatoryObj.chartObj = initEChart(constellatoryObj.chartId);
            personalityObj.chartObj = initEChart(personalityObj.chartId);

            self.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            if (_.isNull(self.resultData) || self.organId != organId) {
                self.organId = organId;
                self.getRequestData(organId);
                return;
            }
        }, showTeamTip: function () {
        	  showEmptyTip($('#' + sexObj.chartId));
        	  showEmptyTip($('#' + ageObj.chartId));
        	  showEmptyTip($('#' + marryObj.chartId));
        	  showEmptyTip($('#' + bloodObj.chartId));
        	  showEmptyTip($('#' + constellatoryObj.chartId));
        	  showEmptyTip($('#' + personalityObj.chartId));
        	  $(".teamImg-sex").hide();

        }, removeTeamTip: function () {
        	 $(".teamImg-sex").show();
        	 removeEmptyTip($('#' + sexObj.chartId));
        	 removeEmptyTip($('#' + ageObj.chartId));
        	 removeEmptyTip($('#' + marryObj.chartId));
        	 removeEmptyTip($('#' + bloodObj.chartId));
        	 removeEmptyTip($('#' + constellatoryObj.chartId));
        	 removeEmptyTip($('#' + personalityObj.chartId));

        },
        getRequestData: function (organId) {
            var self = this;
            $.post(urls.getTeamImgDataUrl, {'organId': organId}, function (rs) {
            	var total=rs.total;
            	self.total=total;
                if (total==0) {
                    self.showTeamTip();
                    return;
                }
                self.removeTeamTip();
                //top
                self.resultData.top=rs.top;
                
                
                // 星座
                self.resultData.constellatory = [];
                $.each(rs.constellatory, function (a, b) {
                    self.resultData.constellatory.push({value: b.v, name: b.k});
                });

                // 血型
                self.resultData.blood = [];
                $.each(rs.blood, function (a, b) {
                    self.resultData.blood.push({value: b.v, name: b.k});
                });
      
                
                // 男女占比
                self.resultData.sex = [];
                $.each(rs.sex, function (a, b) {
                    self.resultData.sex.push({value: b.v, name: b.k});
                });
   
                // 婚姻状况
                //var marry=rs.marry;
                self.resultData.marry ={
                    	xAxisData: [],
                    	seriesData: []
                    };
                $.each(rs.marry, function (a, b) {
                	self.resultData.marry.xAxisData.push(b.k);
                	self.resultData.marry.seriesData.push(b.v);
                });

                // 年龄
                self.resultData.age = [];
                $.each(rs.age, function (a, b) {
                    self.resultData.age.push({value: b.v, name: b.k});
                });

                // 性格
                self.resultData.personality = [];
                $.each(rs.personality, function (a, b) {
                    self.resultData.personality.push({value: b.v, name: b.k});
                });
                self.itemRun();
            });
        },
        itemRun: function () {
            var self = this;
            topObj.init(self.resultData.top,self.total);
            sexObj.init(self.resultData.sex,self.total);
            marryObj.init(self.resultData.marry);
            ageObj.init(self.resultData.age,self.total);
            bloodObj.init(self.resultData.blood,self.total);
            constellatoryObj.init(self.resultData.constellatory,self.total);
            personalityObj.init(self.resultData.personality,self.total);

        }
    }
    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }
    teamImgObj.init(reqOrganId);
    $.screenZoom([
               	constellatoryObj,
               	bloodObj,
               	sexObj,
               	marryObj,
               	ageObj,
               	personalityObj
              ]);
	
});