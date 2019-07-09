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
    		self.requestHistoricalFun();
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
//					else $(".bottom-div").html("暂时没有待审核发布地图");
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
    			if(obj.flag == 0)
    				stepObj.initAuditingElement(self.auditing, "step-one", auditingHalfYear, auditingTop, createTime, obj.yearMonth, obj.organName, topId, i);
    			else {
    				var modifyTime = obj.modifyTime;
    				var auditingTime = modifyTime.split('-').join('.').substr(0, 10);
    				stepObj.initPublishElement(self.auditing, "step-two", auditingHalfYear, auditingTop, createTime, auditingTime, obj.yearMonth, obj.organName, topId, obj.userName, obj.adjustmentId, i);
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
    			var className;
    			if(o.flag == 1) {
    				className = "step-two";
    			} else if (o.flag == 2) {
    				className = "step-three";
    			}
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
    			stepObj.initHistoricalElement(self.auditing, className, id, topId, o.adjustmentId, o.empId, o.yearMonth, auditingHalfYear, auditingTop, createTime, auditingTime, publishTime, i);
    		});
    	}
    }
    
    var stepObj = {
    	initAuditingElement: function(element, className, auditingHalfYear, auditingTop, createTime, yearMonth, organName, topId, inx) {
    		var arr = [];
    		arr.push('	<div class="step clear">');
    		arr.push('		<div class="bottom-div1 left">');
    		arr.push('			<span class="bottom-span1">人才地图-发送审核</span>');
    		arr.push('			<span class="bottom-span2">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('		</div>');
    		arr.push('		<div class="bottom-div2 left">');
    		arr.push('			<ol class="ui-step ' + className + '">');
    		arr.push('				<li class="step-start">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">生成原始地图</span>');
    		arr.push('						<span class="ui-step-cont-time">' + createTime + '</span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('				<li class="step-active">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">调整位置，发送审核</span>');
    		arr.push('						<span class="ui-step-cont-time"></span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('				<li class="step-end">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">发布人才地图</span>');
    		arr.push('						<span class="ui-step-cont-time"></span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('			</ol>');
    		arr.push('		</div>');
    		arr.push('		<div class="bottom-div3 left">');
			arr.push('			<div class="botton-div auditing" id="auditing'+inx+'">发送审核</div>');
    		arr.push('		</div>');
    		arr.push('	</div>');
			$(element).append(arr.join(''));
			$(element).find("#auditing"+inx).click(function () {
				$.ajax({
                	url: urls.checkAuditingPermissionUrl,
                	type: "post",
                	data: {topId: topId},
                	success: function(data) {
                		if(data == 0) {
                			showErrMsg("没有审核权限");
                			return;
                		} else {
                			window.location.href = urls.toTalentMapsAuditingUrl + "?topId=" + topId + "&yearMonth=" + yearMonth + "&organName=" + stringToHex(organName);
                		}
                	},
                	error: function(){}
                });
		    });
    	},
    	initPublishElement: function(element, className, auditingHalfYear, auditingTop, createTime, auditingTime, yearMonth, organName, topId, adjustment, adjustmentId, inx) {
    		var arr = [];
    		arr.push('	<div class="step clear">');
    		arr.push('		<div class="bottom-div1 left">');
    		arr.push('			<span class="bottom-span1">人才地图-发布地图</span>');
    		arr.push('			<span class="bottom-span2">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('		</div>');
    		arr.push('		<div class="bottom-div2 left">');
    		arr.push('			<ol class="ui-step ' + className + '">');
    		arr.push('				<li class="step-start">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">生成原始地图</span>');
    		arr.push('						<span class="ui-step-cont-time">' + createTime + '</span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('				<li class="step-active">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">调整位置，发送审核</span>');
    		arr.push('						<span class="ui-step-cont-time">' + auditingTime + '</span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('				<li class="step-end">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">发布人才地图</span>');
    		arr.push('						<span class="ui-step-cont-time"></span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('			</ol>');
    		arr.push('		</div>');
    		arr.push('		<div class="bottom-div3 left">');
			arr.push('			<div class="botton-div release" id="release'+inx+'">发布地图</div>');
    		arr.push('		</div>');
    		arr.push('	</div>');
			$(element).append(arr.join(''));
			$(element).find("#release"+inx).click(function () {
				$.ajax({
                	url: urls.checkPublishPermissionUrl,
                	type: "post",
                	data: {topId: topId, time: yearMonth},
                	success: function(data) {
                		if(data <= 0) {
                			showErrMsg("没有发布权限");
                			return;
                		} else {
                			window.location.href = urls.toTalentMapsPublishUrl + "?topId=" + topId + "&adjustment=" + stringToHex(adjustment) + "&adjustmentId=" + adjustmentId + "&yearMonth=" + yearMonth + "&organName=" + stringToHex(organName);
                		}
                	},
                	error: function(){}
                });
		    });
    	},
    	initHistoricalElement: function(element, className, id, topId, adjustmentId, empId, yearMonth, auditingHalfYear, auditingTop, createTime, auditingTime, publishTime, inx) {
    		var arr = [];
    		arr.push('	<div class="step clear">');
    		arr.push('		<div class="bottom-div1 left">');
    		if(className == "step-two") {
    			arr.push('			<span class="bottom-span1">人才地图-等待发布</span>');
    		} else if (className == "step-three") {
    			arr.push('			<span class="bottom-span1">人才地图-已发布</span>');
    		}
    		arr.push('			<span class="bottom-span2">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
    		arr.push('		</div>');
    		arr.push('		<div class="bottom-div2 left">');
    		arr.push('			<ol class="ui-step ' + className + '">');
    		arr.push('				<li class="step-start">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">生成原始地图</span>');
    		arr.push('						<span class="ui-step-cont-time">' + (createTime == undefined ? "" : createTime) + '</span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('				<li class="step-active">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">调整位置，发送审核</span>');
    		arr.push('						<span class="ui-step-cont-time">' + (auditingTime == undefined ? "" : auditingTime) + '</span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('				<li class="step-end">');
    		arr.push('					<div class="ui-step-line"></div>');
    		arr.push('					<div class="ui-step-cont">');
    		arr.push('						<span class="ui-step-cont-circle"></span>');
    		arr.push('						<span class="ui-step-cont-text">发布人才地图</span>');
    		arr.push('						<span class="ui-step-cont-time">' + (publishTime == undefined ? "" : publishTime)  + '</span>');
    		arr.push('					</div>');
    		arr.push('				</li>');
    		arr.push('			</ol>');
    		arr.push('		</div>');
    		arr.push('		<div class="bottom-div3 left">');
    		arr.push('			<div class="botton-div review" id="review'+inx+'">查看</div>');
    		arr.push('		</div>');
    		arr.push('	</div>');
			$(element).append(arr.join(''));
        	$(element).find("#review"+inx).click(function () {
		        window.location.href = urls.toHistoricalReviewUrl + "?id=" + id + "&topId=" + topId + "&adjustmentId=" + adjustmentId + "&empId=" + empId + "&yearMonth=" + yearMonth;
		    });
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
});