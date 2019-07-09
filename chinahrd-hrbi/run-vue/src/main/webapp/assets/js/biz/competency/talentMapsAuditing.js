require(['jquery', 'bootstrap', 'utils', 'jgGrid', 'underscore', 'messenger', 'select2', 'talent-map2'], function ($) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        toTalentMapsView: webRoot + "/talentMaps/toTalentMapsView",  //跳转到人才首页
        getTalentPerformanceAbilityUrl: webRoot + '/talentMaps/getTalentPerformanceAbility.do',	//人才信息
        checkEmpInfoUrl: webRoot + "/talentMaps/checkEmpInfo.do", //获取审核人
        updateAuditingInfoUrl: webRoot + "/talentMaps/updateAuditingInfo.do", //审核
        getZInfoUrl: webRoot + "/talentMaps/getZInfo.do", //获取z轴纬度
        getZInfoCountUrl: webRoot + "/talentMaps/getZInfoCount.do", //获取z轴统计
        queryMapsPreviewUrl: webRoot + "/talentMaps/queryMapsPreview.do", //地图预览
        toTalentMapsAuditingUrl: webRoot + "/talentMaps/toTalentMapsAuditingView",       //跳转到人才地图审核页面
    }

    $(win.document.getElementById('tree')).next().hide();

    $("[data-toggle='tooltip']").tooltip();

    $("#toHomeBtn").click(function () {
        win.setlocationUrl(urls.toTalentMapsView);
    });
    var topId = $("#topId").val();
    var time = $("#time").val();
    var yearMonth;
    if(time.indexOf("上半年") != -1)
    	yearMonth = parseInt(time.substr(0, 4) + "06");
    else
    	yearMonth = parseInt(time.substr(0, 4) + "12");
    var organName = $("#organName").val();
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
        init: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: false,
                showText: false,
                xAxis: {
                    title: "绩效排名",
                    data: performanceArr/*['后10%', '[90%-70%)', '[70%-40%)', '[40%-20%)', '前20%']*/
                },
                yAxis: {
                    title: "能力",
                    data: abilityArr/*['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']*/
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
            self.requestMapData();
            self.fullClick();
        },
        requestMapData: function() {
        	var self = this;
        	var param = {flag: 0, topId: topId, yearMonth: yearMonth};
        	$.post(
        		urls.queryMapsPreviewUrl,
        		param,
        		function(data) {
        			self.talentData = data;
        			self.render(data);
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
		                $('#talentMapsAuditing').addClass('hide');
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
                width: w,
                xAxis: {
                    title: "绩效排名",
                    data: performanceArr/*['后10%', '[90%-70%)', '[70%-40%)', '[40%-20%)', '前20%'],*/
                },
                yAxis: {
                    title: "能力等级",
                    data: abilityArr/*['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']*/
                },
                point: {
                    afterCreate: function (elem, point) {

                    },
                    onClick: function (elem, point) {
                        console.log(elem, point);
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
                	points.push({
                		text: o.userName,
                		x: o.xData,
                		y: o.yData
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

    var talentPerformanceAbilityObj = {
    	gridId: 'talentPerformanceAbilityGrid',
    	pagerId: 'talentPerformanceAbilityPager',
    	tabId: 'tab-content',
    	isTrue : false,
    	changeData: [],
        init: function () {
            var self = this;
            self.initGridFun();
            self.loadComple();
            self.getOriginalDataFun();
            self.auditingFun();
            self.requestData();
            $("#hasAuditingNum").hide();
        },
        requestData: function () {
            var self = this;
            $.ajax({
                url: urls.getZInfoUrl,
                type: "post",
                success: function (data) {
                    self.render(data);
                },
                error: function () {
                }
            });
        },
        render: function (data) {
            var self = this;
            var arr = [];
            arr.push('<div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">');
            arr.push('	<div class="rightSetUpBtnTop"></div>');
            arr.push('	<div class="text" data-key="all">全部</div>');
            arr.push('</div>');
            $.each(data, function (i, o) {
                arr.push('<div class="rightSetUpBtnDiv rightSetUpRight">');
                arr.push('	<div class="rightSetUpBtnTop"></div>');
                arr.push('	<div class="text" data-key="' + o.name + '">' + o.name + '</div>');
                arr.push('</div>');
            });
            $(".rightSetUpBtn").append(arr.join(""));
            var zNameObj = $(".rightSetUpBtn").find(".text");
            $.each(zNameObj, function(i, o) {
            	var zName = $(o).attr("data-key");
            	if(zName == "all") {
            		var param = {topId: topId, flag: 0, zName: null, yearMonth: yearMonth}
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
        			var param = {topId: topId, flag: 0, zName: zName, yearMonth: yearMonth}
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
        rightSetUpBtnDivFun: function () {
            var self = this;
            $(".rightSetUpBtnDiv").click(function () {
                if ($(this).hasClass("rightSetUpBtnSelect"))return;
                $("#keyName").val("");
                $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
                $(this).addClass("rightSetUpBtnSelect");
                var key = $(this).find(".text").attr("data-key");
                if(key == "all") {
                	var param = {zName: null, keyName: null, topId: topId};
                    self.reloadGridFun(param);
                } else {
                    var param = {zName: key, keyName: null, topId: topId};
                    self.reloadGridFun(param);
                }
            });
        },
        initGridFun: function () {
            var self = this;
            var param = {
                type: 0,
                topId: topId,
                yearMonth: yearMonth
            };
            if (self.isTrue) {
                self.reloadGridFun(param);
            }
            $('#' + self.gridId).jqGrid({
                url: urls.getTalentPerformanceAbilityUrl,
                datatype: "json",
                postData: param,
                mtype: 'POST',
//                altRows: true,
                shrinkToFit: false,
                height: 141,
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: ['empId', 'organName', 'positionName', '姓名', '绩效', '能力', '调整位置'],
                colModel: [
					{name:'empId', index: 'empId', key: true, hidden: true},
					{name:'organName', index: 'organName', key: true, hidden: true},
					{name:'positionName', index: 'positionName', key: true, hidden: true},
					{name:'userName', sortable:false,fixed:true, width: 91,align: 'center',editable: false},
					{name:'xaxisName', sortable:false,fixed:true, width: 90,align: 'center',editable: false},
					{name:'yaxisName', sortable:false,fixed:true, width: 115,align: 'center',editable: false},
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
            $('#' + self.gridId).setGridHeight($('.' + self.tabId).height() * 0.9);
            self.isTrue = true;
        },
        reloadGridFun: function (param) {
            var self = this;
            $('#' + self.gridId).clearGridData().setGridParam({
                postData: param
            }).trigger("reloadGrid");
        },
        resizeGrid: function() {
        	var width = $('.tab-content').width();
        	$('#' + this.gridId).setGridWidth($('.tab-content').width() * 0.98);
        },
        loadComple: function () {
            var self = this;
            $("#searchBtn").click(function () {
                var keyName = $.trim($("#keyName").val());
                var param = {type: 0, keyName: keyName, topId: topId};
                if (keyName != "") {
                    self.isClicked = true;
                    self.reloadGridFun(param);
                }
            });
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
                		if(flag)
                			self.changeData.push(param);
                		else {
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
                	$("#hasAuditingNum").show();
                else
                	$("#hasAuditingNum").hide();
                $("#auditingNum").html(self.changeData.length);
        	});
        },
        getOriginalDataFun: function () {
            var self = this;
            $("#next-step").click(function () {
                $("#page-one").hide();
                $("#page-two").show();
                $('body,html').animate({ scrollTop: 0 }, 0);
                self.nextStepFun();
                mapPreviewObj.data = self.changeData;
            });
        },
        nextStepFun: function () {
            var self = this;
            var arr = [];
            var nameArr = [];
            $.each(self.changeData, function (i, obj) {
                if (obj.prePer != obj.per || obj.preAbi != obj.abi) {
                    arr.push('	<tr class="talent-next">');
                    arr.push('		<td style="display:none">' + obj.empId + '</td>');
                    arr.push('		<td style="display:none">' + obj.perId + '</td>');
                    arr.push('		<td style="display:none">' + obj.abiId + '</td>');
                    arr.push('		<td class="next-tdstyle1"><div class="emp-img"></div><div class="emp-text">' + obj.name + '<br>' + obj.organName + '/' + obj.positionName + '</td></div>');
                    arr.push('		<td class="next-tdstyle">' + obj.prePer + '<br>' + obj.preAbi + '</td>');
                    arr.push('		<td class="next-tdstyle3"><div class="auditing-map"></div></td>');
                    arr.push('		<td class="next-tdstyle">' + obj.per + '<br>' + obj.abi + '</td>');
                    arr.push('		<td class="next-tdstyle2">');
                    arr.push('			<textarea style="width:100%; overflow:auto; word-break:break-all;"></textarea>');
                    arr.push('		</td>');
                    arr.push('	</tr>');
                    nameArr.push(obj.name);
                }
            });
            $("#nextPerformanceAbility").children(".talent-next").remove();
            $("#nextPerformanceAbility").append(arr.join(""));
            $("#talents").html(nameArr.join("、") + "等" + nameArr.length + "人");
            
            $('#auditing').show();
            $('#auditing_ing').hide();
            $("#previous-step").unbind('click').bind('click', function () {
                $("#page-one").show();
                $("#page-two").hide();
                $('body,html').animate({ scrollTop: 0 }, 0);
            });
            self.auditorFun();
        },
        auditorFun: function () {
            $("#txtKey").change(function () {
                $("#cp-auditor").html("");
                var tame = $("#txtKey option:selected").text();
                $("#cp-auditor").html(tame);
            });
        },
        auditingFun: function () {
            var self = this;
            $("#auditing").click(function () {
                if ($("#txtKey option:selected").text() == undefined
                    || $.trim($("#txtKey option:selected").text()) == '') {
                	showErrMsg("请选择审核人");
                    return;
                }
                $('#auditing').hide();
                $('#auditing_ing').show();
                $('#previous-step').unbind('click');
                
                self.getAuditingDataFun();
            });
        },
        getAuditingDataFun: function () {
            var self = this;
            var tr = $("#nextPerformanceAbility").find("tr");
            var arr = [];
            $.each(tr, function (i, obj) {
//                if (i != 0) {
                    var empId = $(obj).children("td:eq(0)").text();
                    var xData = $(obj).children("td:eq(1)").text();
                    var yData = $(obj).children("td:eq(2)").text();
                    var note = $(obj).find("td:eq(6) textarea").val();
                    var p = {empId: empId, xData: xData, yData: yData, note: note};
                    arr.push(p);
//                }
            });
            var auditorId = $("#txtKey option:selected").val();
            var auditorName = $("#txtKey option:selected").text();
            var param = {arr: JSON.stringify(arr), auditorId: auditorId, auditorName: auditorName, topId: topId, time: time, organName: organName};
            self.requestAuditingFun(param);
        },
        requestAuditingFun: function (param) {
            $.ajax({
                url: urls.updateAuditingInfoUrl,
                data: param,
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                	if(data != null && data.length > 0) {
                		$("#auditing").unbind('click');
//                		showErrMsg("审核成功");
                		var obj = data[0];
                		$("#nextOrganName").text(obj.organName);
                		$("#riskConfigModal").modal("show");
                		$('#confirmOk').click(function() {
                			window.location.href = urls.toTalentMapsAuditingUrl + "?topId=" + obj.organId + "&yearMonth=" + obj.yearMonth + "&organName=" + stringToHex(obj.organName);
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
                error: function (err) {
                	console.log("错误:"+err);
                }
            });
        }
    };
    
    mapPreviewObj = {
		mapId: '#mapPreview',
        mapObj: null,
        data: [],
        allData: [],
        points: [],
        allPoints: [],
    	init: function() {
    		var self = this;
    		self.initData();
    		self.clickFun();
    	},
    	initData: function() {
    		var self = this;
    		var param = {flag: 0, topId: topId, yearMonth: yearMonth};
        	$.post(
        		urls.queryMapsPreviewUrl,
        		param,
        		function(data) {
        			self.allData = data;
        		}
        	);
    	},
    	clickFun: function() {
    		var self = this;
    		$("#preview").click(function() {
    			if(self.allData.length + self.data.length > 1000) 
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
    		$("#showAdjustment").click(function() {
    			if(null != self.mapObj) self.mapObj.clear();
    			var t = $('#showAdjustment');
    	    	if($('#showAdjustment').is(':checked')) {
    	    		self.mapObj.addPoints(self.points);
    	    	}
    	    	else {
    	    		self.mapObj.addPoints(self.allPoints);
    	    		self.mapObj.addPoints(self.points);
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
                    data: performanceArr/*['后10%', '[90%-70%)', '[70%-40%)', '[40%-20%)', '前20%']*/
                },
                yAxis: {
                    title: "能力等级",
                    data: abilityArr/*['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']*/
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
                var allPoints = [];
                $.each(self.allData, function (i, o) {
                	var flag = true;
                	$.each(self.data, function (j, obj) {
                    	if(o.userName == obj.name)
                    		flag = false;
                    });
                	if(flag) {
                		allPoints.push({
                        	team: '全部人员',
                        	text: o.userName,
                            x: o.xData,
                            y: o.yData
                        });
                	}
                });
                self.allPoints = allPoints;
                self.mapObj.addPoints(allPoints);
                var points = [];
                $.each(self.data, function (i, o) {
                	points.push({
                    	team: '调整人员',
                    	text: o.name,
                        x: o.perId,
                        y: o.abiId
                    });
                });
                self.points = points;
                self.mapObj.addPoints(points); // ！！批量添加点一定要用这个，尤其不确定点有多少时
            }, 1000);
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width();
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
        }
    };
    mapPreviewObj.init();

    //选择审核人
    var addTalentObj = {
        id: "txtKey",
        init: function () {
            var self = this;
            self.select();
        },
        select: function () {
            var self = this;

            $("#" + self.id).select2({
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
                placeholder: "请输入审核人",
                minimumInputLength: 1,
                ajax: {
                    url: urls.checkEmpInfoUrl,
                    dataType: 'json',
                    delay: 500,
                    type: "POST",
                    data: function (params/*, page*/) {
                        var ps = {
                            keyName: params && params.term ? params.term : "",
                            /*page: params.page == null ? 1 : params.page,
                             rows: 30*/
                        };
                        return ps;
                    },
                    results: function (data/*, page*/) {
                        return data;
                    },
                    processResults: function (data) {
                        var lists = [];
                        $.each(data, function (i, item) {
                            lists.push({id: item.empId, text: item.userName});
                        });
                        return {
                            results: lists,
                            /*pagination: {
                             more: data.total > data.page
                             }*/
                        };
                    },
                    cache: true
                }
            }).val(null);
        }
    };
    
    $(window).resize(function () {
    	talentPerformanceAbilityObj.resizeGrid();
    	maps.resizeMap();
    	mapPreviewObj.resizeMap();
    });
    
    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData() {
        maps.init();
        talentPerformanceAbilityObj.init();
        addTalentObj.init();
    }
    changeData();
    
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
    
    Array.prototype.max = function() {
    	return Math.max.apply({},this)
	};

    $('#fullCloseBtn').click(function () {
        win.doRestoreWindow(function (e) {
            $('#fullMapBody').addClass('hide');
            $('#talentMapsAuditing').removeClass('hide');
        });
    });
});