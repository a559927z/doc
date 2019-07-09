require(['jquery', 'bootstrap', 'underscore', 'utils'], function () {
	
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
    		toLaborEfficiencyViewUrl: webRoot + '/laborEfficiency/toLaborEfficiencyView.do',           //跳转到首页
    		queryAuditingInfoUrl: webRoot + '/laborEfficiency/queryAuditingInfo.do',    //审核历史数据
    };

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgTxt = win.currOrganTxt;

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        reqOrgTxt = organTxt;
        changeData();
    };
    
    var attId = $("#attId").val();
    
    /** 数据审核状态 */
    var auditingStateObj = {
    	auditingState: "#auditingState",
    	init: function() {
    		var self = this;
    		self.requestData();
    	},
    	requestData: function() {
    		var self = this;
    		var param = {status: 1};
    		$(self.auditingState).html('');
    		$.ajax({
    			url: urls.queryAuditingInfoUrl,
    			type: "post",
    			data: param,
    			success : function(data){
    				stepOption.initElement(data, self.auditingState);
    			},
    			error : function(){}
    		});
    	}
    };
    
    /** 数据审核状态图 */
    var stepOption = {
    	initElement: function(data, selector) {
    		$.each(data, function(i, obj){
    			var year = obj.createTime.substr(0, 4);
    			var month = obj.createTime.substr(5, 2);
    			var day = obj.createTime.substr(8, 2);
    			var importTime = year + '.' + month + '.' + day;
    			var examineYear = obj.examineTime.substr(0, 4);
    			var examineMonth = obj.examineTime.substr(5, 2);
    			var examineDay = obj.examineTime.substr(8, 2);
    			var examineTime = examineYear + '.' + examineMonth + '.' + examineDay;
    			var arr = [];
        		arr.push('	<div class="step-div">');	
            	arr.push('		<div class="step-div1">');	
            	arr.push('			<span class="step-span1">' + year + '年' + month + '月-数据审核</span>');	
            	arr.push('		</div>');
            	arr.push('		<div class="step-div2">');
            	arr.push('			<ol class="ui-step step-two">');
            	arr.push('				<li class="step-start">');
            	arr.push('					<div class="ui-step-line"></div>');
            	arr.push('					<div class="ui-step-cont">');
            	arr.push('						<span class="ui-step-cont-circle"></span>');
            	arr.push('						<span class="ui-step-cont-text">数据导入</span>');
            	arr.push('						<span class="ui-step-cont-time">' + obj.empName + '&nbsp;&nbsp;' + importTime + '</span>');
            	arr.push('					</div>');
            	arr.push('				</li>');
            	arr.push('				<li class="step-end">');
            	arr.push('					<div class="ui-step-cont">');
            	arr.push('						<span class="ui-step-cont-circle"></span>');
            	arr.push('						<span class="ui-step-cont-text">数据审核</span>');
            	arr.push('						<span class="ui-step-cont-time">' + obj.userName + '&nbsp;&nbsp;' + examineTime + '</span>');
            	arr.push('					</div>');
            	arr.push('				</li>');
            	arr.push('			</ol>');
            	arr.push('		</div>');
            	arr.push('	</div>');
            	if($(selector).find(".step-div").length != 0) {
            		$(selector).find(".step-div").last().after(arr.join(''));
            	} else {
            		$(selector).html(arr.join(''));
            	}
        	});
    	}
    };

    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData() {
        var selOrganId = reqOrgId;
        auditingStateObj.init();
    }

    changeData();

    $(window).resize(function () {
        
    });
    
    $('#toHomeBtn').click(function () {
        win.setlocationUrl(urls.toLaborEfficiencyViewUrl + '?type=1');
    });

});