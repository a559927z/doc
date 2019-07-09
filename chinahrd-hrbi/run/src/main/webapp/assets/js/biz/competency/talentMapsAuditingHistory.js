require(['jquery', 'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
    	toTalentMapsUrl: webRoot + "/talentMaps/toTalentMapsView",
    	toHistoricalReviewUrl: webRoot + "/talentMaps/toTalentMapsHistoryView",  //跳转到历史页面
    	queryMapsAuditingUrl: webRoot + '/talentMaps/queryMapsAuditing.do',	//查询待审核发布的地图
    	toTalentMapsAuditingUrl : webRoot + "/talentMaps/toTalentMapsAuditingView",       //跳转到人才地图审核页面
        toTalentMapsPublishUrl : webRoot + "/talentMaps/toTalentMapsPublishView",       //跳转到人才地图发布页面
        getHistoricalAuditingUrl : webRoot + "/talentMaps/getHistoricalAuditing.do",  //获取历史审核列表
        checkAuditingPermissionUrl: webRoot + "/talentMaps/checkAuditingPermission.do", //是否有审核权限
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
    
    var auditingHistoryObj = {
    	auditing: "#auditing",
//    	historicalReview: "#historicalReview",
		init: function() {
    		var self = this;
    		self.requestFun();
    	},
    	requestFun: function() {
    		var self = this;
    		$.ajax({
    			type: "POST",
    			url: urls.queryMapsAuditingUrl,
				success: function(data) {
					if(undefined != data && data.length > 0) {
						self.generateAuditing(data);
					}
					self.requestHistoricalFun();
				},
				error : function(){}
        	});
    	},
    	generateAuditing: function(data) {
    		var self = this;
    		$.each(data, function(i, obj) {
    			var startTime = obj.startTime;
    			var half;
    			if(parseInt(startTime.substr(5, 2)) < 7) {
    				half = "年上半年";
    			} else {
    				half = "年下半年";
    			}
    			var auditingHalfYear = startTime.substr(0, 4) + half;
    			var auditingTop = obj.organName;
    			var topId = obj.organId;
    			var createTime = startTime.split('-').join('.').substr(0, 10);
    			if(obj.flag == 0) {
    				stepObj.initAuditingElement2(auditingHalfYear, auditingTop, createTime, obj.yearMonth, obj.organName, topId, i);
    			} else {
    				var modifyTime = obj.modifyTime;
    				var auditingTime = modifyTime.split('-').join('.').substr(0, 10);
    				stepObj.initPublishElement2(auditingHalfYear, auditingTop, createTime, auditingTime, obj.userName, obj.adjustmentId, obj.yearMonth, obj.organName, topId, i);
    			}
    		});
    	},
    	requestHistoricalFun: function() {
    		var self = this;
    		$.ajax({
    			type: "POST",
    			url: urls.getHistoricalAuditingUrl,
				success: function(data) {
					self.generateHistoricalFun(data);
				},
				error : function(){}
        	});
    	},
    	generateHistoricalFun: function(data) {
    		$.each(data, function(i, o) {
//    			var className;
    			var auditingTop = o.organName;
    			var startTime = o.startTime;
    			var createTime;
    			if(undefined != startTime) {
    				createTime = startTime.split('-').join('.').substr(0, 10);
    			}
    			var modifyTime = o.modifyTime;
    			var auditingTime;
    			if(undefined != modifyTime) {
    				auditingTime = modifyTime.split('-').join('.').substr(0, 10);
    			}
    			var releaseTime = o.releaseTime;
    			var publishTime;
    			if(undefined != releaseTime) {
    				publishTime = releaseTime.split('-').join('.').substr(0, 10);
    			}
    			var time = o.yearMonth + "";
    			var year = time.substr(0, 4);
    			var month = time.substr(4, 2);
    			var auditingHalfYear;
    			if(parseInt(month) < 7) {
    				auditingHalfYear = year + "年上半年";
    			} else {
    				auditingHalfYear = year + "年下半年";
    			}
    			var id = o.id;
    			var topId = o.topId;
    			if(o.flag == 1) {
    				stepObj.initHistoricalElement2(id, topId, o.adjustmentId, o.empId, o.yearMonth, auditingHalfYear, auditingTop, createTime, auditingTime, i);
    			} else if (o.flag == 2) {
    				stepObj.initHistoricalElement3(id, topId, o.adjustmentId, o.empId, o.yearMonth, auditingHalfYear, auditingTop, createTime, auditingTime, publishTime, i);
    			}
    		});
    	}
    }
    
    var stepObj = {
    	adjust: ".bottom-div-two",
    	initAuditingElement2: function(auditingHalfYear, auditingTop, createTime, yearMonth, organName, topId, inx) {
    		var self = this;
    		var arr = [];
    		arr.push('<div class="step-div">');
    		arr.push('	<div class="step-div-one">');
    		arr.push('		<span class="span-one">人才地图-发送审核</span>');
    		arr.push('		<span class="span-two">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('	</div>');
    		arr.push('	<div class="vertical-split"></div>');
    		arr.push('	<div class="step-div-two">');
    		arr.push('		<div class="line_step">');
    		arr.push('			<div class="steped-left left" id="step_left'+inx+'"></div>');
    		arr.push('			<div class="line-block"></div>');
    		arr.push('			<div class="step-ing left" id="step_ing'+inx+'"></div>');
    		arr.push('			<div class="line-block"></div>');
    		arr.push('			<div class="step-right left" id="step_right'+inx+'"></div>');
    		arr.push('		</div>');
    		arr.push('		<div class="content_step">');
    		arr.push('			<div id="content_step_one'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">生成原始地图</span>');
    		arr.push('				<span class="display-block">' + createTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_three'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block step-color">调整位置，发送审核</span>');
    		arr.push('				<span class="display-block"></span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_five'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block step-color">发布人才地图</span>');
			arr.push('				<span class="display-block"></span>');
			arr.push('			</div>');
			arr.push('		</div>');
			arr.push('	</div>');
			arr.push('	<div class="step-div-three" id="auditing' + inx + '">发送审核</div>');
			arr.push('</div>');
			$(self.adjust).append(arr.join(''));
            $(self.adjust).find("#auditing" + inx).click(function () {
                $.post(urls.checkAuditingPermissionUrl, {topId: topId}, function (data) {
                    if (data == 0) {
                        showErrMsg("没有审核权限");
                        return;
                    }
                    var url = urls.toTalentMapsAuditingUrl + "?topId=" + topId + "&yearMonth=" + yearMonth + "&organName=" + stringToHex(organName);
                    window.location.href = url;
                });
            });
            self.classInit(inx);
    	},
    	classInit: function (inx) {
    		var self = this;
    		var left1 = $(self.adjust).find("#step_left"+inx).offset().left;
    		var top1 = $(self.adjust).find("#step_left"+inx).offset().top;
    		$(self.adjust).find("#content_step_one"+inx).css({"position": "absolute", 'top':top1+25, 'left':left1-25});
    		var left2 = $(self.adjust).find("#step_ing"+inx).offset().left;
    		var top2 = $(self.adjust).find("#step_ing"+inx).offset().top;
    		$(self.adjust).find("#content_step_three"+inx).css({"position": "absolute", 'top':top2+25, 'left':left2-39});
    		var left3 = $(self.adjust).find("#step_right"+inx).offset().left;
    		var top3 = $(self.adjust).find("#step_right"+inx).offset().top;
    		$(self.adjust).find("#content_step_five"+inx).css({"position": "absolute", 'top':top3+25, 'left':left3-25});
    	},
    	initPublishElement2: function(auditingHalfYear, auditingTop, createTime, auditingTime, adjustment, adjustmentId, yearMonth, organName, topId, inx) {
        	var self = this;
    		var arr = [];
    		arr.push('<div class="step-div">');
    		arr.push('	<div class="step-div-one">');
    		arr.push('		<span class="span-one">人才地图-发送审核</span>');
    		arr.push('		<span class="span-two">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('	</div>');
    		arr.push('	<div class="vertical-split"></div>');
    		arr.push('	<div class="step-div-two">');
    		arr.push('		<div class="line_step">');
    		arr.push('			<div class="steped-left left" id="step_left'+inx+'"></div>');
    		arr.push('			<div class="line-blocked"></div>');
    		arr.push('			<div class="steped-ing left" id="step_ing'+inx+'"></div>');
    		arr.push('			<div class="line-block"></div>');
    		arr.push('			<div class="step-right left" id="step_right'+inx+'"></div>');
    		arr.push('		</div>');
    		arr.push('		<div class="content_step">');
    		arr.push('			<div id="content_step_one'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">生成原始地图</span>');
    		arr.push('				<span class="display-block">' + createTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_three'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">调整位置，发送审核</span>');
    		arr.push('				<span class="display-block">' + auditingTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_five'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block step-color">发布人才地图</span>');
			arr.push('				<span class="display-block"></span>');
			arr.push('			</div>');
			arr.push('		</div>');
			arr.push('	</div>');
			arr.push('	<div class="step-div-three" id="publish' + inx + '">发布地图</div>');
			arr.push('</div>');
			$(self.adjust).append(arr.join(''));
            $(self.adjust).find("#publish" + inx).click(function () {
                $.post(urls.checkPublishPermissionUrl, {topId: topId, time: yearMonth}, function (data) {
                    if (data <= 0) {
                        showErrMsg("没有发布权限");
                        return;
                    }
                    var url = urls.toTalentMapsPublishUrl + "?adjustment=" + stringToHex(adjustment)
                        + "&adjustmentId=" + adjustmentId + "&topId=" + topId + "&yearMonth="
                        + yearMonth + "&organName=" + stringToHex(organName);
                    window.location.href = url;
                });
            });
            self.classInit(inx);
        },
        initHistoricalElement2: function(id, topId, adjustmentId, empId, yearMonth, auditingHalfYear, auditingTop, createTime, auditingTime, inx) {
        	var self = this;
    		var arr = [];
    		arr.push('<div class="step-div-h">');
    		arr.push('	<div class="step-div-one">');
    		arr.push('		<span class="span-one">人才地图-发送审核</span>');
    		arr.push('		<span class="span-two">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('	</div>');
    		arr.push('	<div class="vertical-split"></div>');
    		arr.push('	<div class="step-div-two">');
    		arr.push('		<div class="line_step">');
    		arr.push('			<div class="steped-left left" id="step_left_h'+inx+'"></div>');
    		arr.push('			<div class="line-blocked"></div>');
    		arr.push('			<div class="steped-ing left" id="step_ing_h'+inx+'"></div>');
    		arr.push('			<div class="line-block"></div>');
    		arr.push('			<div class="step-right left" id="step_right_h'+inx+'"></div>');
    		arr.push('		</div>');
    		arr.push('		<div class="content_step">');
    		arr.push('			<div id="content_step_one_h'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">生成原始地图</span>');
    		arr.push('				<span class="display-block">' + createTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_three_h'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">调整位置，发送审核</span>');
    		arr.push('				<span class="display-block">' + auditingTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_five_h'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block step-color">发布人才地图</span>');
			arr.push('				<span class="display-block"></span>');
			arr.push('			</div>');
			arr.push('		</div>');
			arr.push('	</div>');
			arr.push('	<div class="step-div-three-h" id="review' + inx + '">查看</div>');
			arr.push('</div>');
			$(self.adjust).append(arr.join(''));
        	$(self.adjust).find("#review"+inx).click(function () {
		        window.location.href = urls.toHistoricalReviewUrl + "?id=" + id + "&topId=" + topId + "&adjustmentId=" + adjustmentId + "&empId=" + empId + "&yearMonth=" + yearMonth;
		    });
            self.classInitH(inx);
        },
        classInitH: function (inx) {
    		var self = this;
    		var left1 = $(self.adjust).find("#step_left_h"+inx).offset().left;
    		var top1 = $(self.adjust).find("#step_left_h"+inx).offset().top;
    		$(self.adjust).find("#content_step_one_h"+inx).css({"position": "absolute", 'top':top1+25, 'left':left1-25});
    		var left2 = $(self.adjust).find("#step_ing_h"+inx).offset().left;
    		var top2 = $(self.adjust).find("#step_ing_h"+inx).offset().top;
    		$(self.adjust).find("#content_step_three_h"+inx).css({"position": "absolute", 'top':top2+25, 'left':left2-39});
    		var left3 = $(self.adjust).find("#step_right_h"+inx).offset().left;
    		var top3 = $(self.adjust).find("#step_right_h"+inx).offset().top;
    		$(self.adjust).find("#content_step_five_h"+inx).css({"position": "absolute", 'top':top3+25, 'left':left3-25});
    	},
    	initHistoricalElement3: function(id, topId, adjustmentId, empId, yearMonth, auditingHalfYear, auditingTop, createTime, auditingTime, publishTime, inx) {
        	var self = this;
    		var arr = [];
    		arr.push('<div class="step-div-h">');
    		arr.push('	<div class="step-div-one">');
    		arr.push('		<span class="span-one">人才地图-发送审核</span>');
    		arr.push('		<span class="span-two">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('	</div>');
    		arr.push('	<div class="vertical-split"></div>');
    		arr.push('	<div class="step-div-two">');
    		arr.push('		<div class="line_step">');
    		arr.push('			<div class="steped-left left" id="step_left_h'+inx+'"></div>');
    		arr.push('			<div class="line-blocked"></div>');
    		arr.push('			<div class="steped-ing left" id="step_ing_h'+inx+'"></div>');
    		arr.push('			<div class="line-blocked"></div>');
    		arr.push('			<div class="steped-right left" id="step_right_h'+inx+'"></div>');
    		arr.push('		</div>');
    		arr.push('		<div class="content_step">');
    		arr.push('			<div id="content_step_one_h'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">生成原始地图</span>');
    		arr.push('				<span class="display-block">' + createTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_three_h'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">调整位置，发送审核</span>');
    		arr.push('				<span class="display-block">' + auditingTime + '</span>');
    		arr.push('			</div>');
    		arr.push('			<div id="content_step_five_h'+inx+'" class="content-center left">');
    		arr.push('				<span class="display-block steped-color">发布人才地图</span>');
			arr.push('				<span class="display-block">' + publishTime + '</span>');
			arr.push('			</div>');
			arr.push('		</div>');
			arr.push('	</div>');
			arr.push('	<div class="step-div-three-h" id="review' + inx + '">查看</div>');
			arr.push('</div>');
			$(self.adjust).append(arr.join(''));
        	$(self.adjust).find("#review"+inx).click(function () {
		        window.location.href = urls.toHistoricalReviewUrl + "?id=" + id + "&topId=" + topId + "&adjustmentId=" + adjustmentId + "&empId=" + empId + "&yearMonth=" + yearMonth;
		    });
            self.classInitH(inx);
        }
    };
    
    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData() {
        var selOrganId = reqOrgId;
        auditingHistoryObj.init();
    }
    changeData();

    $("#toHomeBtn").click(function () {
        win.setlocationUrl(urls.toTalentMapsUrl);
    });
    
    $("#btn-form").click(function() {
    	
    });
    
    function stringToHex(str){
		var val="";
		if(str != null) {
			for(var i = 0; i < str.length; i++){
				if(val == "")
					val = str.charCodeAt(i).toString(16);
				else
					val += "," + str.charCodeAt(i).toString(16);
			}
		}
		return val;
	}
    
    $(window).resize(function () {
    	var ele = $(".bottom-div-two").find(".step-div");
    	for(var i = 0; i < ele.length; i ++) {
    		stepObj.classInit(i);
    	}
    	var his = $(".bottom-div-two").find(".step-div-h");
    	for(var i = 0; i < his.length; i ++) {
    		stepObj.classInitH(i);
    	}
    });
});