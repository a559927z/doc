require(['jquery', 'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger', 'talent-map2'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
    		toTalentMapsView: webRoot + "/talentMaps/toTalentMapsView",  //跳转到人才首页
    		getTalentPerformanceAbilityUrl: webRoot + '/talentMaps/getTalentPerformanceAbility.do',	//人才信息
    		getTalentPublishUrl: webRoot + '/talentMaps/getTalentPublish.do',	//待发布的人才信息
    		getTalentAjustmentInfoUrl: webRoot + '/talentMaps/getTalentAjustmentInfo.do',
    		getZInfoUrl: webRoot + "/talentMaps/getZInfo.do", //获取z轴纬度
    		getZInfoCountUrl: webRoot + "/talentMaps/getZInfoCount.do", //获取z轴统计
    		queryMapsPreviewUrl: webRoot + "/talentMaps/queryMapsPreview.do", //地图预览
    		pulishMapsManagementUrl: webRoot + "/talentMaps/pulishMapsManagement.do", //发布地图
    		toTalentMapsPublishUrl: webRoot + "/talentMaps/toTalentMapsPublishView",       //跳转到人才地图发布页面
    }

    $(win.document.getElementById('tree')).next().hide();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    $("[data-toggle='tooltip']").tooltip();

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        timeLineObj.init(reqOrgId);
        perBenefitOjb.init();
        profitObj.init();
        orgBenefitObj.init();
    };
    var topId = $("#topId").val();
    var adjustmentId = $("#adjustmentId").val();
    var publishTime = $("#time").val();
    if(publishTime.indexOf("上半年") != -1) 
    	publishTime = publishTime.substr(0, 4) + '06';
    else
    	publishTime = publishTime.substr(0, 4) + '12';
    var performanceStr = $("#performance").val();
    var performanceArr = performanceStr.split(",");
    var performance = [];
    $.each(performanceArr, function(i, o) {
		performance.push({key: i+1, value: o});
	});
    var abilityStr = $("#ability").val();
    var abilityArr = abilityStr.split(",");
    var ability = [];
    $.each(abilityArr, function(i, o) {
		ability.push({key: i+1, value: o});
	});
    
    var maps = {
        mapId: '#map',
        fullMapId: '#fullMap',
        mapObj: null,
        fullMapObj: null,
        talentData: [],
        adjustData: [],
        init: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: false,
                xAxis: {
                    title: "绩效排名",
                    data: performanceArr//['后10%', '[90%-70%)', '[70%-40%)', '[40%-20%)', '前20%']
                },
                yAxis: {
                    title: "能力等级",
                    data: abilityArr//['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']
                },
                //关闭背景颜色
                // zAxis: false,
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                        var test = maps.mapObj.getZAxisPoints();
//                        console.log(test);
                    }
                }
            });
            self.requestMapData();
            self.fullClick();
        },
        requestMapData: function() {
        	var self = this;
        	var param = {flag: 0, topId: topId, yearMonth: publishTime};
        	$.post(
        		urls.queryMapsPreviewUrl,
        		param,
        		function(data) {
        			self.talentData = data;
        			self.render(data);
        		}
        	);
        	$.post(
        		urls.getTalentAjustmentInfoUrl,
        		{topId: topId, adjustmentId: adjustmentId, yearMonth: parseInt(publishTime)},
        		function(data) {
        			if(undefined != data && data.length > 0) {
    					$.each(data, function(i, o) {
    						var param = {
    							userName: o.userName, x: o.xDataAf, y: o.yDataAf
		                    };
    						self.adjustData.push(param);
    					});
    				}
        		}
        	);
        },
        render: function (data) {
            var self = this;
            window.setTimeout(function () { // 模仿ajax的感觉
                var points = [];
                var pointer11=0;
                var pointer12=0;
                var pointer13=0;
                var pointer14=0;
                var pointer15=0;
                var pointer16=0;
                var pointer17=0;
                var pointer21=0;
                var pointer22=0;
                var pointer23=0;
                var pointer24=0;
                var pointer25=0;
                var pointer26=0;
                var pointer27=0;
                var pointer31=0;
                var pointer32=0;
                var pointer33=0;
                var pointer34=0;
                var pointer35=0;
                var pointer36=0;
                var pointer37=0;
                var pointer41=0;
                var pointer42=0;
                var pointer43=0;
                var pointer44=0;
                var pointer45=0;
                var pointer46=0;
                var pointer47=0;
                var pointer51=0;
                var pointer52=0;
                var pointer53=0;
                var pointer54=0;
                var pointer55=0;
                var pointer56=0;
                var pointer57=0;
                $.each(data, function (i, o) {
                	if(o.xData==1&&o.yData==1)
                		pointer11++;
                	if(o.xData==1&&o.yData==2)
                		pointer12++;
                	if(o.xData==1&&o.yData==3)
                		pointer13++;
                	if(o.xData==1&&o.yData==4)
                		pointer14++;
                	if(o.xData==1&&o.yData==5)
                		pointer15++;
                	if(o.xData==1&&o.yData==6)
                		pointer16++;
                	if(o.xData==1&&o.yData==7)
                		pointer17++;
                	if(o.xData==2&&o.yData==1)
                		pointer21++;
                	if(o.xData==2&&o.yData==2)
                		pointer22++;
                	if(o.xData==2&&o.yData==3)
                		pointer23++;
                	if(o.xData==2&&o.yData==4)
                		pointer24++;
                	if(o.xData==2&&o.yData==5)
                		pointer25++;
                	if(o.xData==2&&o.yData==6)
                		pointer26++;
                	if(o.xData==2&&o.yData==7)
                		pointer27++;
                	if(o.xData==3&&o.yData==1)
                		pointer31++;
                	if(o.xData==3&&o.yData==2)
                		pointer32++;
                	if(o.xData==3&&o.yData==3)
                		pointer33++;
                	if(o.xData==3&&o.yData==4)
                		pointer34++;
                	if(o.xData==3&&o.yData==5)
                		pointer35++;
                	if(o.xData==3&&o.yData==6)
                		pointer36++;
                	if(o.xData==3&&o.yData==7)
                		pointer37++;
                	if(o.xData==4&&o.yData==1)
                		pointer41++;
                	if(o.xData==4&&o.yData==2)
                		pointer42++;
                	if(o.xData==4&&o.yData==3)
                		pointer43++;
                	if(o.xData==4&&o.yData==4)
                		pointer44++;
                	if(o.xData==4&&o.yData==5)
                		pointer45++;
                	if(o.xData==4&&o.yData==6)
                		pointer46++;
                	if(o.xData==4&&o.yData==7)
                		pointer47++;
                	if(o.xData==5&&o.yData==1)
                		pointer51++;
                	if(o.xData==5&&o.yData==2)
                		pointer52++;
                	if(o.xData==5&&o.yData==3)
                		pointer53++;
                	if(o.xData==5&&o.yData==4)
                		pointer54++;
                	if(o.xData==5&&o.yData==5)
                		pointer55++;
                	if(o.xData==5&&o.yData==6)
                		pointer56++;
                	if(o.xData==5&&o.yData==7)
                		pointer57++;
                });
                var pointerNum = [pointer11, pointer12, pointer13, pointer14, pointer15, pointer16, pointer17,
                				  pointer21, pointer22, pointer23, pointer24, pointer25, pointer26, pointer27,
					              pointer31, pointer32, pointer33, pointer34, pointer35, pointer36, pointer37,
					              pointer41, pointer42, pointer43, pointer44, pointer25, pointer46, pointer47,
					              pointer51, pointer52, pointer53, pointer54, pointer55, pointer56, pointer57];
                var maxVal = pointerNum.max();
                var len = Math.ceil(maxVal/6);
                for(var i = 1; i <= Math.ceil(pointer11/len); i++) {
                	points.push({
                        x: 1,
                        y: 1
                    });
                }
                for(var i = 1; i <= Math.ceil(pointer12/len); i++) {
                	points.push({
                		x: 1,
                		y: 2
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer13/len); i++) {
                	points.push({
                		x: 1,
                		y: 3
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer14/len); i++) {
                	points.push({
                		x: 1,
                		y: 4
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer15/len); i++) {
                	points.push({
                		x: 1,
                		y: 5
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer16/len); i++) {
                	points.push({
                		x: 1,
                		y: 6
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer17/len); i++) {
                	points.push({
                		x: 1,
                		y: 7
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer21/len); i++) {
                	points.push({
                		x: 2,
                		y: 1
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer22/len); i++) {
                	points.push({
                		x: 2,
                		y: 2
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer23/len); i++) {
                	points.push({
                		x: 2,
                		y: 3
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer24/len); i++) {
                	points.push({
                		x: 2,
                		y: 4
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer25/len); i++) {
                	points.push({
                		x: 2,
                		y: 5
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer26/len); i++) {
                	points.push({
                		x: 2,
                		y: 6
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer27/len); i++) {
                	points.push({
                		x: 2,
                		y: 7
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer31/len); i++) {
                	points.push({
                		x: 3,
                		y: 1
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer32/len); i++) {
                	points.push({
                		x: 3,
                		y: 2
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer33/len); i++) {
                	points.push({
                		x: 3,
                		y: 3
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer34/len); i++) {
                	points.push({
                		x: 3,
                		y: 4
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer35/len); i++) {
                	points.push({
                		x: 3,
                		y: 5
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer36/len); i++) {
                	points.push({
                		x: 3,
                		y: 6
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer37/len); i++) {
                	points.push({
                		x: 3,
                		y: 7
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer41/len); i++) {
                	points.push({
                		x: 4,
                		y: 1
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer42/len); i++) {
                	points.push({
                		x: 4,
                		y: 2
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer43/len); i++) {
                	points.push({
                		x: 4,
                		y: 3
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer44/len); i++) {
                	points.push({
                		x: 4,
                		y: 4
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer45/len); i++) {
                	points.push({
                		x: 4,
                		y: 5
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer46/len); i++) {
                	points.push({
                		x: 4,
                		y: 6
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer47/len); i++) {
                	points.push({
                		x: 4,
                		y: 7
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer51/len); i++) {
                	points.push({
                		x: 5,
                		y: 1
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer52/len); i++) {
                	points.push({
                		x: 5,
                		y: 2
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer53/len); i++) {
                	points.push({
                		x: 5,
                		y: 3
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer54/len); i++) {
                	points.push({
                		x: 5,
                		y: 4
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer55/len); i++) {
                	points.push({
                		x: 5,
                		y: 5
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer56/len); i++) {
                	points.push({
                		x: 5,
                		y: 6
                	});
                }
                for(var i = 1; i <= Math.ceil(pointer57/len); i++) {
                	points.push({
                		x: 5,
                		y: 7
                	});
                }
                
                self.mapObj.addPoints(points); // ！！批量添加点一定要用这个，尤其不确定点有多少时
            }, 500);
        },
        fullClick: function() {
        	var self = this;
        	$('#fullScreen').click(function () {
				if(self.talentData.length > 1000)
					showErrMsg("超过1000人，全屏功能暂不能使用");
				else {
					win.doFullScreen(function (e) {
						$('#fullMapBody').removeClass('hide');
			            $('#talentMapsPublish').addClass('hide');
		                self.fullInit();
		            });
				}
            });
        },
        fullInit: function () {
            var self = this;
            var w = $(self.fullMapId).parent().width();
            self.fullMapObj = $(self.fullMapId).talentMap({
                showNum: true,
                showTeam: true,
                width: w,
                xAxis: {
                    title: "绩效排名",
                    data: performanceArr,  //['后10%', '[90%-70%)', '[70%-40%)', '[40%-20%)', '前20%'],
                    widths: [0.1, 0.2, 0.3, 0.2, 0.2]
                },
                yAxis: {
                    title: "能力等级",
                    data: abilityArr  //['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']
                },
                point: {
                    afterCreate: function (elem, point) {

                    }
                }
            });
            self.fullRender();
        },
        fullRender: function () {
            var self = this;
            window.setTimeout(function () { // 模仿ajax的感觉
                var points = [];
                $.each(self.talentData, function(i, o) {
                	var flag = true;
                	$.each(self.adjustData, function(j, obj){
                		if(o.userName == obj.userName) 
                			flag = false;
                	});
                	if(flag) {
                		points.push({
                    		team: '全部人员',
                    		text: o.userName,
                    		x: o.xData,
                    		y: o.yData
                    	});
                	}
                });
                $.each(self.adjustData, function(i, o) {
                	points.push({
                		team: '调整人员',
                		text: o.userName,
                		x: o.x,
                		y: o.y
                	});
                });
                self.fullMapObj.addPoints(points); // ！！批量添加点一定要用这个，尤其不确定点有多少时
            }, 500);
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
            var fw = $(self.fullMapId).parent().width();
            if (fw > 0 && !_.isNull(self.fullMapObj)) self.fullMapObj.resize(fw);
        }
    };
    maps.init();
    
    var talentPublishObj = {
		gridId: 'talentPublishGrid',
    	pagerId: 'talentPublishPager',
    	tabId: 'tab-content',
    	isTrue : false,
    	init : function(){
    		var self = this;
    		self.initGridFun();
    		self.loadComple();
    		$("#hasPublishNum").hide();
    	},
    	initGridFun: function(){
    		var self = this;
    		var param = {
    			topId: topId,
    			adjustmentId: adjustmentId,
    			yearMonth: parseInt(publishTime)
    		};
    		if(self.isTrue){
    			self.reloadGridFun(param);
    		}
    		$('#' + self.gridId).jqGrid({
    			url: urls.getTalentPublishUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
//                altRows: true, 
                shrinkToFit:false,
                height: 279,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['empId', 'organName', 'positionName', '姓名', '原位置', '调整后位置', '调整说明'],
                colModel: [
					{name:'empId', index: 'empId', key: true, hidden: true},
					{name:'organName', index: 'organName', key: true, hidden: true},
					{name:'positionName', index: 'positionName', key: true, hidden: true},
					{name:'userName', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
					{name:'', sortable:false,fixed:true, width: 120,align: 'center',editable: false,
						formatter: function (value, options, row) {
							return row.xaxisName + "\n" + row.yaxisName;
						}
					},
					{name:'', sortable:false,fixed:true, width: 120,align: 'center',editable: false,title: false,
						formatter: function (value, options, row) {
							var arr = [];
							arr.push('<select class="performance">');
							$.each(performance, function(i, o) {
								if(row.xDataAf == o.key) {
									arr.push('	<option value="' + o.key + '" selected>' + o.value + '</option>');
								} else {
									arr.push('	<option value="' + o.key + '">' + o.value + '</option>');
								}
							});
			        		arr.push('</select><br>');
			        		arr.push('<select class="ability">');
			        		$.each(ability, function(i, o) {
			        			if(row.yDataAf == o.key) {
									arr.push('	<option value="' + o.key + '" selected>' + o.value + '</option>');
								} else {
									arr.push('	<option value="' + o.key + '">' + o.value + '</option>');
								}
							});
			        		arr.push('</select>');
					        return arr.join("");
					    }
					},
					{name:'note', sortable:false,fixed:true, width: 200,align: 'center',editable: false,
						formatter: function(value) {
							return '<div class="text_overflow">' + value +'</div>';
						}
					}
				],
                viewrecords: true,
                rowNum: 5,
                rowList: [5, 10, 15],
                pager: '#' + self.pagerId,
                loadComplete: function (xhr) {
                	var rowNum = parseInt($(this).getGridParam("records"), 10); 
                	if (rowNum <= 0) { 
                		$('#' + self.gridId).parent().append("<div class=\"norecords\">暂无数据。。。</div>");
                	} else {
                		$('#' + self.gridId).parent().find(".norecords").remove();
                	}
                    talentPerformanceAbilityObj.selectPublishChangeFun();
                }
    		});
//    		$('#' + self.gridId).setGridHeight($('.' + self.tabId).height() * 0.9);
    		self.isTrue = true;
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$('#' + self.gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    	},
    	loadComple: function () {
        	var self = this;
            $("#searchBtn1").click(function () {
                var keyName = $.trim($("#keyName1").val());
                var param = {keyName: keyName, topId: topId};
//                if (keyName != "") {
                	self.isClicked = true;
                	self.reloadGridFun(param);
//                }
            });
    	},
    	resizeGrid: function() {
        	$('#' + this.gridId).setGridWidth($('.tab-content').width() * 0.98);
        },
    };
    talentPublishObj.init();
    
    var talentPerformanceAbilityObj = {
    	gridId: 'talentPerformanceAbilityGrid',
    	pagerId: 'talentPerformanceAbilityPager',
    	tabId: 'tab-content',
    	isTrue : false,
    	changeData: [],
    	publishData: [],
    	ajustmentData: [],
    	init : function(){
    		var self = this;
    		self.initGridFun();
    		self.loadComple();
    		self.requestData();
    		self.requestPublishInfo();
    	},
    	requestPublishInfo: function() {
    		var self = this;
    		$.ajax({
    			url: urls.getTalentAjustmentInfoUrl,
    			type: "post",
    			data: {topId: topId, adjustmentId: adjustmentId, yearMonth: parseInt(publishTime)},
    			success : function(data){
    				if(undefined != data && data.length > 0) {
    					$.each(data, function(i, o) {
    						var param = {
		                        empId: o.empId, name: o.userName, organName: o.organName, positionName: o.positionName, note: o.note,
		                        prePer: o.xaxisName, preAbi: o.yaxisName, per: o.xaxisAfName, perId: o.xDataAf, abi: o.yaxisAfName, abiId: o.yDataAf
		                    };
//    						self.changeData.push(param);
    						self.ajustmentData.push(param);
    					});
    					$("#records").html(self.ajustmentData.length);
    					if(self.changeData.length > 0)
    	                	$("#hasPublishNum").show();
    	                else
    	                	$("#hasPublishNum").hide();
    					$("#publishNum").html(self.changeData.length);
    				}
    			},
    			error : function(){}
    		});
    	},
    	requestData: function() {
    		var self = this;
    		$.ajax({
    			url: urls.getZInfoUrl,
    			type: "post",
    			data: {topId: topId, flag: 0},
    			success : function(data){
    				self.render(data);
    			},
    			error : function(){}
    		});
    	},
    	render: function(data) {
    		var self = this;
    		var arr = [];
    		arr.push('<div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">');
    		arr.push('	<div class="rightSetUpBtnTop"></div>');
    		arr.push('	<div class="text" id="allNum" data-key="all">全部 </div>');
    		arr.push('</div>');
    		$.each(data, function(i, o) {
				arr.push('<div class="rightSetUpBtnDiv rightSetUpRight">');
				arr.push('	<div class="rightSetUpBtnTop"></div>');
				arr.push('	<div class="text" id="' + o.id + '" data-key="' + o.name + '">' + o.name + '</div>');
				arr.push('</div>');
    		});
    		$(".rightSetUpBtn").append(arr.join(""));
    		var zNameObj = $(".rightSetUpBtn").find(".text");
            $.each(zNameObj, function(i, o) {
            	var zName = $(o).attr("data-key");
            	if(zName == "all") {
            		var param = {topId: topId, flag: 0, zName: null, yearMonth: parseInt(publishTime)}
            		$.ajax({
                        url: urls.getZInfoCountUrl,
                        type: "post",
                        data: param,
                        success: function (data) {
                        	$(o).append(' (' + data + ')');
                        },
                        error: function () {
                        }
                    });
            	}
        		else {
        			var param = {topId: topId, flag: 0, zName: zName, yearMonth: parseInt(publishTime)}
        			$.ajax({
                        url: urls.getZInfoCountUrl,
                        type: "post",
                        data: param,
                        success: function (data) {
                        	$(o).append(' (' + data + ')');
                        },
                        error: function () {
                        }
                    });
        		}
            });
            
    		self.rightSetUpBtnDivFun();
    	},
    	rightSetUpBtnDivFun: function() {
    		var self = this;
    		$(".rightSetUpBtnDiv").click(function () {
                if ($(this).hasClass("rightSetUpBtnSelect"))return;
                $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
                $(this).addClass("rightSetUpBtnSelect");
                var key = $(this).find(".text").attr("data-key");
                $("#keyName2").val('');
                if(key == "all") {
                	var param = {zName: null, keyName: null};
                    self.reloadGridFun(param);
                } else {
                    var param = {zName: key, keyName: null};
                    self.reloadGridFun(param);
                }
            });
    	},
    	initGridFun: function(){
    		var self = this;
    		var param = {
    			topId: topId,
    			yearMonth: parseInt(publishTime)
    		};
    		if(self.isTrue){
    			self.reloadGridFun(param);
    		}
    		$('#' + self.gridId).jqGrid({
    			url: urls.getTalentPerformanceAbilityUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
//                altRows: true, 
                shrinkToFit:false,
                height: 209,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['empId', 'organName', 'positionName', '姓名', '绩效', '能力', '调整位置'],
                colModel: [
					{name:'empId', index: 'empId', key: true, hidden: true},
					{name:'organName', index: 'organName', key: true, hidden: true},
					{name:'positionName', index: 'positionName', key: true, hidden: true},
					{name:'userName', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
					{name:'xaxisName', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
					{name:'yaxisName', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
					{name:'', sortable:false,fixed:true, width: 240,align: 'center',editable: false,title: false,
						formatter: function (value, options, row) {
							var arr = [];
							var hasChange = false;
							var perId = "";
							var abiId = "";
							$.each(self.changeData, function(i, o) {
								if(o.empId == row.empId) {
									hasChange = true;
									perId = o.perId;
									abiId = o.abiId;
								}
							});
							arr.push('<select class="performance">');
							$.each(performance, function(i, o) {
								if(hasChange) {
									if(perId == o.key) {
										arr.push('	<option value="' + o.key + '" selected>' + o.value + '</option>');
									} else {
										arr.push('	<option value="' + o.key + '">' + o.value + '</option>');
									}
								} else {
									if(row.xData == o.key) {
										arr.push('	<option value="' + o.key + '" selected>' + o.value + '</option>');
									} else {
										arr.push('	<option value="' + o.key + '">' + o.value + '</option>');
									}
								}
							});
			        		arr.push('</select>&nbsp;');
			        		arr.push('<select class="ability">');
			        		$.each(ability, function(i, o) {
			        			if(hasChange) {
			        				if(abiId == o.key) {
				        				arr.push('	<option value="' + o.key + '" selected>' + o.value + '</option>');
									} else {
										arr.push('	<option value="' + o.key + '">' + o.value + '</option>');
									}
			        			} else {
			        				if(row.yData == o.key) {
				        				arr.push('	<option value="' + o.key + '" selected>' + o.value + '</option>');
									} else {
										arr.push('	<option value="' + o.key + '">' + o.value + '</option>');
									}
			        			}
							});
			        		arr.push('</select>');
					        return arr.join("");
					    }
					}
				],
                viewrecords: true,
                rowNum: 5,
                rowList: [5, 10, 15],
                pager: '#' + self.pagerId,
                loadComplete: function (xhr) {
                	var rowNum = parseInt($(this).getGridParam("records"), 10); 
                	if (rowNum <= 0) { 
                		$('#' + self.gridId).parent().append("<div class=\"norecords\">暂无数据。。。</div>");
                	} else {
                		$('#' + self.gridId).parent().find(".norecords").remove();
                	}
                    self.selectChangeFun();
                }
    		});
//    		$('#' + self.gridId).setGridHeight($('.' + self.tabId).height() * 0.9);
    		self.isTrue = true;
    	},
    	reloadGridFun: function(param){
    		var self = this;
    		$('#' + self.gridId).clearGridData().setGridParam({
    			postData: param
    		}).trigger("reloadGrid");
    	},
    	loadComple: function () {
        	var self = this;
            $("#searchBtn2").click(function () {
                var keyName = $.trim($("#keyName2").val());
                var param = {keyName: keyName, topId: topId};
//                if (keyName != "") {
                	self.isClicked = true;
                	self.reloadGridFun(param);
//                }
            });
    	},
    	resizeGrid: function() {
    		$('#' + this.gridId).setGridWidth($('#' + this.gridId).parent().width() * 0.98);
        },
    	selectChangeFun: function() {
    		var self = this;
    		$("#talentPerformanceAbilityGrid").find("tr").find("select").change(function() {
        		var $this = this;
        		var tr = $($this).parent().parent();
            	var empId = $(tr).children("td:eq(0)").text();
                var organName = $(tr).children("td:eq(1)").text();
                var positionName = $(tr).children("td:eq(2)").text();
                var name = $(tr).children("td:eq(3)").text();
                var prePer = $(tr).children("td:eq(4)").text();
                var preAbi = $(tr).children("td:eq(5)").text();
                var per = $(tr).children("td:eq(6)").children("select:eq(0)").find("option:selected").text();
                var perId = $(tr).children("td:eq(6)").children("select:eq(0)").find("option:selected").val();
                var abi = $(tr).find("td:eq(6) select:eq(1) option:selected").text();
                var abiId = $(tr).find("td:eq(6) select:eq(1) option:selected").val();
                if(prePer != per || preAbi != abi) {
                	var param = {
                        empId: empId, name: name, organName: organName, positionName: positionName,
                        prePer: prePer, preAbi: preAbi, per: per, perId: perId, abi: abi, abiId: abiId
                    };
                	if(self.changeData.length < 1) {
                		self.changeData.push(param);
                	} else {
                		var flag = true;
                		$.each(self.changeData, function(i, obj) {
                			if(empId == obj.empId) {
                				flag = false;
                			}
                		});
                		if(flag) {
                			self.changeData.push(param);
                		} else {
                			self.changeData.forEach(function(item){
								if(item.empId === empId){
									//这里是编辑操作per: per, perId: perId, abi: abi, abiId: abiId
									item.per = per;
									item.perId = perId;
									item.abi = abi;
									item.abiId = abiId;
								}
            				});
                		}
                	}
                } else {
                	var flag = true;
            		$.each(self.changeData, function(i, obj) {
            			if(empId == obj.empId) {
            				flag = false;
            			}
            		});
            		if(!flag) {
            			var newArray = self.changeData.filter(function(obj){
            				return obj.empId !== empId;
            			});
            			self.changeData = newArray;
            		}
                }
                if(self.changeData.length > 0)
                	$("#hasPublishNum").show();
                else
                	$("#hasPublishNum").hide();
                $("#publishNum").html(self.changeData.length);
        	});
        },
        selectPublishChangeFun: function() {
        	var self = this;
        	$("#talentPublishGrid").find("tr").find("select").change(function() {
        		var $this = this;
        		var tr = $($this).parent().parent();
            	var empId = $(tr).children("td:eq(0)").text();
                var organName = $(tr).children("td:eq(1)").text();
                var positionName = $(tr).children("td:eq(2)").text();
                var name = $(tr).children("td:eq(3)").text();
                var before = $(tr).children("td:eq(4)").text();
                var prePer = before.split('\n')[0];
                var preAbi = before.split('\n')[1];
                var per = $(tr).children("td:eq(5)").children("select:eq(0)").find("option:selected").text();
                var perId = $(tr).children("td:eq(5)").children("select:eq(0)").find("option:selected").val();
                var abi = $(tr).find("td:eq(5) select:eq(1) option:selected").text();
                var abiId = $(tr).find("td:eq(5) select:eq(1) option:selected").val();
                var note = $(tr).children("td:eq(6) textarea").val();
                var param = {
                    empId: empId, name: name, organName: organName, positionName: positionName,
                    prePer: prePer, preAbi: preAbi, per: per, perId: perId, abi: abi, abiId: abiId, note: note
                };
        		var flag = true;
        		$.each(self.ajustmentData, function(i, obj) {
        			if(empId == obj.empId && obj.per == per && obj.abi == abi) {
        				flag = false;
        			}
        		});
        		if(flag) {
        			var ishas = false;
        			self.changeData.forEach(function(item){
						if(item.empId === empId){
							//这里是编辑操作per: per, perId: perId, abi: abi, abiId: abiId
							item.per = per;
							item.perId = perId;
							item.abi = abi;
							item.abiId = abiId;
							ishas = true;
						}
    				});
        			if(!ishas) self.changeData.push(param);
        		}
        		if(self.changeData.length > 0)
                	$("#hasPublishNum").show();
                else
                	$("#hasPublishNum").hide();
                $("#publishNum").html(self.changeData.length);
        	});
        }
    };
    talentPerformanceAbilityObj.init();
    
    var nextStepClickObj = {
    	changeData: [],
    	publishData: [],
    	ajustmentData: [],
    	nameArr: [],
    	init: function() {
    		var self = this;
    		$('#publish').show();
			$('#publishing').hide();
			self.preStepFun();
    		self.nextStepFun();
    		self.confirmFun();
    	},
    	nextStepFun: function() {
    		var self = this;
    		$("#next-step").click(function() {
    	    	$("#page-one").hide();
    	    	$("#page-two").show();
    	    	self.nameArr = [];
    	    	self.changeData = talentPerformanceAbilityObj.changeData;
    	    	self.publishData = talentPerformanceAbilityObj.publishData;
    	    	self.ajustmentData = talentPerformanceAbilityObj.ajustmentData;
    	    	mapPreviewObj.data = talentPerformanceAbilityObj.changeData;
    	    	self.generateFun();
	    		self.getAuditingDataFun();
    	    });
    	},
    	preStepFun: function() {
    		$("#previous-step").unbind('click').bind('click', function() {
    	    	$("#page-one").show();
    	    	$("#page-two").hide();
    	    	$('body,html').animate({ scrollTop: 0 }, 0);
    	    });
    	},
    	generateFun: function() {
    		var self = this;
    		var arr = [];
    		$.each(self.changeData, function(i, obj) {
//    			if(obj.prePer != obj.per || obj.preAbi != obj.abi) {
    				arr.push('	<tr class="talent-next">');
    	    		arr.push('		<td style="display:none">' + obj.empId + '</td>');
    	    		arr.push('		<td style="display:none">' + obj.perId + '</td>');
    	    		arr.push('		<td style="display:none">' + obj.abiId + '</td>');
            		arr.push('		<td class="next-tdstyle1"><div class="emp-img"></div><div class="emp-text">' + obj.name + '<br>' + obj.organName + '/' + obj.positionName + '</td></div>');
            		arr.push('		<td class="next-tdstyle">' + obj.prePer + '<br>' + obj.preAbi + '</td>');
            		arr.push('		<td class="next-tdstyle">' + obj.per + '<br>' + obj.abi + '</td>');
            		arr.push('		<td class="next-tdstyle2">');
            		arr.push('			<textarea id="textArea" style="width:100%; overflow:auto; word-break:break-all;"></textarea>');
            		arr.push('		</td>');
            		arr.push('	</tr>');
            		if($.inArray(obj.name, self.nameArr) == -1)
            			self.nameArr.push(obj.name);
//    			}
	    	});
    		$("#thisTimeTalentGrid").children(".talent-next").remove();
    		$("#thisTimeTalentGrid").append(arr.join(""));
    		$("#talents").html(self.nameArr.join("、") + "等" + self.nameArr.length + "人");
    	},
    	getAuditingDataFun: function() {
    		var self = this;
    		var arr = [];
    		$.each(self.ajustmentData, function(i, obj) {
				arr.push('	<tr class="talent-next">');
	    		arr.push('		<td style="display:none">' + obj.empId + '</td>');
	    		arr.push('		<td style="display:none">' + obj.perId + '</td>');
	    		arr.push('		<td style="display:none">' + obj.abiId + '</td>');
        		arr.push('		<td class="next-tdstyle1"><div class="emp-img"></div><div class="emp-text">' + obj.name + '<br>' + obj.organName + '/' + obj.positionName + '</td></div>');
        		arr.push('		<td class="next-tdstyle">' + obj.prePer + '<br>' + obj.preAbi + '</td>');
        		arr.push('		<td class="next-tdstyle">' + (_.isNull(obj.per) ? "" : obj.per) + '<br>' + (_.isNull(obj.abi) ? "" : obj.abi) + '</td>');
        		arr.push('		<td class="next-tdstyle2">');
        		arr.push('			<textarea style="width:100%; overflow:auto; word-break:break-all;" readonly>' + (_.isNull(obj.note) ? "" : obj.note) + '</textarea>');
        		arr.push('		</td>');
        		arr.push('	</tr>');
        		/*if($.inArray(obj.name, self.nameArr) == -1)
        			self.nameArr.push(obj.name);*/
	    	});
    		$("#talentNextGrid").children(".talent-next").remove();
    		$("#talentNextGrid").append(arr.join(""));
    	},
    	confirmFun: function() {
    		var self = this;
    		$("#publish").click(function() {
//    			if(confirm('你确定要发布人才地图吗？')) {
    			$('#publish').hide();
    			$('#publishing').show();
    			$('#previous-step').unbind('click');
				self.getPublishDataFun();
//    			}
    		});
    	},
    	getPublishDataFun: function() {
    		var self = this;
    		var arr = [];
    		var tr = $("#thisTimeTalentGrid").find("tr");
			$.each(tr, function(i, obj) {
				var empId = $(obj).children("td:eq(0)").text();
				var perId = $(obj).children("td:eq(1)").text();
				var abiId = $(obj).children("td:eq(2)").text();
				var note = $(obj).find("td:eq(6) textarea").val();
				var p = {empId: empId, xDataAf: perId, yDataAf: abiId, note: note, text: '1'};
				arr.push(p);
			});
			var str = JSON.stringify(arr);
			var param = {arr: JSON.stringify(arr), topId: topId, publishTime: publishTime};
			self.requestPublishFun(param);
    	},
        requestPublishFun: function (param) {
            $.ajax({
                url: urls.pulishMapsManagementUrl,
                data: param,
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                	if(data != null && data.length > 0) {
                		var obj = data[0];
                		$("#nextOrganName").text(obj.organName);
                		$("#riskConfigModal").modal("show");
                		$('#confirmOk').click(function() {
                			window.location.href = urls.toTalentMapsPublishUrl + "?adjustment=" + stringToHex(obj.userName) + "&adjustmentId=" + obj.adjustmentId + "&topId=" + obj.organId + "&yearMonth=" + obj.yearMonth + "&organName=" + stringToHex(obj.organName);
                		});
                		$('#cancel').click(function() {
                			win.setlocationUrl(urls.toTalentMapsView);
                		});
                	} else {
                		$("#successModal").modal("show");
                		$('#successConfirm').click(function() {
                			win.setlocationUrl(urls.toTalentMapsView);
                		});
                	}
                },
                error: function () {
                }
            });
        }
    };
    nextStepClickObj.init();
    
    mapPreviewObj = {
		mapId: '#mapPreview',
        mapObj: null,
        data: [],
        allData: [],
        pointers: [],
    	init: function() {
    		var self = this;
    		self.initData();
    	},
    	initData: function() {
    		var self = this;
    		var param = {flag: 0, topId: topId, yearMonth: publishTime};
        	$.post(
        		urls.queryMapsPreviewUrl,
        		param,
        		function(data) {
        			self.allData = data;
        		}
        	);
        	self.clickFun();
    	},
    	clickFun: function() {
    		var self = this;
    		$("#preview").click(function() {
    			if(undefined != self.allData && self.allData.length + self.data.length > 1000) 
    				showErrMsg("超过1000人，预览功能暂不能使用");
    			else {
        			$('#mapPreviewModal').modal('show');
    				$('#mapPreviewModal').on('shown.bs.modal', function() {
    					self.mapObj = null;
    					self.initMaps();
    				});
    				self.render();
    			}
    		});
    	},
    	initMaps: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: true,
                showTeam: true,
//                showText: false,
                xAxis: {
                    title: "绩效排名",
                    data: performanceArr  //['后10%', '[90%-70%)', '[70%-40%)', '[40%-20%)', '前20%']
                },
                yAxis: {
                    title: "能力等级",
                    data: abilityArr  //['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']
                },
//                zAxis: false,
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                    }
                }
            });
        },
        render: function () {
            var self = this;
            window.setTimeout(function () { // 模仿ajax的感觉
                var points = [];
                $.each(self.allData, function (i, o) {
                	var flag = true;
                	$.each(self.data, function (j, obj) {
                        if(o.userName == obj.name)
                        	flag = false;
                    });
                	if(flag) {
                		points.push({
                        	team: '全部人员',
                        	text: o.userName,
                            x: o.xData,
                            y: o.yData
                        });
                	}
                });
                $.each(self.data, function (i, o) {
                    points.push({
                    	team: '调整人员',
                    	text: o.name,
                        x: o.perId,
                        y: o.abiId
                    });
                });
                self.pointers = points;
                self.mapObj.addPoints(points); // ！！批量添加点一定要用这个，尤其不确定点有多少时
            }, 5000);
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
        }
    };
    mapPreviewObj.init();
    
    function stringToHex(str){
		var val="";
		if(undefined != str) {
			for(var i = 0; i < str.length; i++){
				if(val == "")
					val = str.charCodeAt(i).toString(16);
				else
					val += "," + str.charCodeAt(i).toString(16);
			}
		}
		return val;
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
    
    $(window).resize(function () {
    	talentPublishObj.resizeGrid();
//    	talentPerformanceAbilityObj.resizeGrid();
    	maps.resizeMap();
    	mapPreviewObj.resizeMap();
    });
    
    Array.prototype.max = function() {
    	return Math.max.apply({},this)
	};

    $("#toHomeBtn").click(function () {
        win.setlocationUrl(urls.toTalentMapsView);
    });

    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
    });
    
    $(".nav-div-text").click(function() {
    	if ($(this).hasClass("nav-text-selected")) return;
        $(this).parents(".nav-div").find(".nav-div-text").removeClass("nav-text-selected");
        $(this).addClass("nav-text-selected");
        if ($("#allEmp").hasClass("nav-text-selected")) {
        	$(".rightSetUpBtn").removeClass("display-none");
        	$("#talentPublish2").removeClass("display-none");
        	$("#talentPublish1").addClass("display-none");
//        	talentPerformanceAbilityObj.resizeGrid();
        } else {
        	$(".rightSetUpBtn").addClass("display-none");
        	$("#talentPublish2").addClass("display-none");
        	$("#talentPublish1").removeClass("display-none");
        	talentPublishObj.resizeGrid();
        }
    });
    
    $('#fullCloseBtn').click(function () {
        win.doRestoreWindow(function (e) {
            $('#fullMapBody').addClass('hide');
            $('#talentMapsPublish').removeClass('hide');
        });
    });
});