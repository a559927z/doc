require(['jquery', 'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        toMapsShowUrl: webRoot + "/talentMaps/toTalentMapsShow",       //跳转到人才地图
        toTalentMapsAuditingHistoryUrl: webRoot + "/talentMaps/toTalentMapsAuditingHistoryView",       //跳转到全部历史审核页面
        toTalentMapsAuditingUrl: webRoot + "/talentMaps/toTalentMapsAuditingView",       //跳转到人才地图审核页面
        toTalentMapsPublishUrl: webRoot + "/talentMaps/toTalentMapsPublishView",       //跳转到人才地图发布页面
        toTalentMapsForTeamPKUrl: webRoot + "/talentMaps/toTalentMapsForTeamPKView",       //跳转到人才地图团队PK页面
        toTalentMapsTeamCPViewUrl: webRoot + '/talentMaps/toTalentMapsTeamCPView.do',	//团队能力和绩效地图
        queryMapsAuditingUrl: webRoot + '/talentMaps/queryMapsAuditing.do',	            //查询待审核发布的地图
        getTalentMapsNewDateUrl: webRoot + '/talentMaps/getTalentMapsNewDate.do',		//获取地图最新时间
        checkAuditingPermissionUrl: webRoot + "/talentMaps/checkAuditingPermission.do", //是否有审核权限
        checkPublishPermissionUrl: webRoot + "/talentMaps/checkPublishPermission.do", //是否有发布权限
    }

    $(win.document.getElementById('tree')).next().hide();
    win.setCurrNavStyle();

    $("[data-toggle='tooltip']").tooltip();

    var queryMapsAuditing = {
        adjustment: ".adjustment",
        init: function () {
            var self = this;
            $.post(urls.queryMapsAuditingUrl, function (data) {
                if (!_.isUndefined(data) && data.length > 0) {
                    self.generateAuditing(data);
                }
            });
        },
        generateAuditing: function (data) {
            var self = this;
            $.each(data, function (i, obj) {
                if (i > 2) return false;

                var startTime = new Date(obj.startTime);
                var auditingHalfYear = startTime.getFullYear();
                if (startTime.getMonth() + 1 < 7) {
                    auditingHalfYear += "年上半年";
                } else {
                    auditingHalfYear += "年下半年";
                }
                var auditingTop = obj.organName, topId = obj.organId;
                var createTime = Tc.formatDate(startTime, 'Y.m.d');
                if (obj.flag == 0)
                    stepObj.initAuditingElement(self.adjustment, auditingHalfYear, auditingTop, createTime, obj.yearMonth, obj.organName, topId, i);
                else {
                    var auditingTime = Tc.formatDate(new Date(obj.modifyTime), 'Y.m.d');
                    stepObj.initPublishElement(self.adjustment, auditingHalfYear, auditingTop, createTime, auditingTime, obj.userName, obj.adjustmentId, obj.yearMonth, obj.organName, topId, i);
                }
            });
        }
    }

    var stepObj = {
        initAuditingElement: function (element, auditingHalfYear, auditingTop, createTime, yearMonth, organName, topId, inx) {
            var arr = [];
            arr.push('	<div class="step-div1">');
            arr.push('		<span class="span1">人才地图-发送审核</span>');
            arr.push('		<span class="span2">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
            arr.push('	</div>');
            arr.push('	<div class="step-div2">');
            arr.push('		<ol class="ui-step step-one">');
            arr.push('			<li class="step-start">');
            arr.push('				<div class="ui-step-line"></div>');
            arr.push('				<div class="ui-step-cont">');
            arr.push('					<span class="ui-step-cont-circle"></span>');
            arr.push('					<span class="ui-step-cont-text">生成原始地图</span>');
            arr.push('					<span class="ui-step-cont-time">' + createTime + '</span>');
            arr.push('				</div>');
            arr.push('			</li>');
            arr.push('			<li class="step-active">');
            arr.push('				<div class="ui-step-line"></div>');
            arr.push('				<div class="ui-step-cont">');
            arr.push('					<span class="ui-step-cont-circle"></span>');
            arr.push('					<span class="ui-step-cont-text">调整位置，发送审核</span>');
            arr.push('					<span class="ui-step-cont-time"></span>');
            arr.push('				</div>');
            arr.push('			</li>');
            arr.push('			<li class="step-end">');
            arr.push('				<div class="ui-step-line"></div>');
            arr.push('				<div class="ui-step-cont">');
            arr.push('					<span class="ui-step-cont-circle"></span>');
            arr.push('					<span class="ui-step-cont-text">发布人才地图</span>');
            arr.push('					<span class="ui-step-cont-time"></span>');
            arr.push('				</div>');
            arr.push('			</li>');
            arr.push('		</ol>');
            arr.push('	</div>');
            arr.push('	<div class="step-div3">');
            arr.push('		<div class="botton-div" id="auditing' + inx + '">发送审核</div>');
            arr.push('	</div>');
            $(element).append(arr.join(''));
            $(element).find("#auditing" + inx).click(function () {

                $.post(urls.checkAuditingPermissionUrl, {topId: topId}, function (data) {
                    if (data == 0) {
                        showErrMsg("没有审核权限");
                        return;
                    }
                    var url = urls.toTalentMapsAuditingUrl + "?topId=" + topId + "&yearMonth=" + yearMonth
                        + "&organName=" + stringToHex(organName);

                    window.location.href = url;
                });
            });
        },
        initPublishElement: function (element, auditingHalfYear, auditingTop, createTime, auditingTime, adjustment, adjustmentId, yearMonth, organName, topId, inx) {
            var arr = [];
            arr.push('	<div class="step-div1">');
            arr.push('		<span class="span1">人才地图-发布地图</span>');
            arr.push('		<span class="span2">' + auditingHalfYear + ' | ' + auditingTop + '</span>');
            arr.push('	</div>');
            arr.push('	<div class="step-div2">');
            arr.push('		<ol class="ui-step step-two">');
            arr.push('			<li class="step-start">');
            arr.push('				<div class="ui-step-line"></div>');
            arr.push('				<div class="ui-step-cont">');
            arr.push('					<span class="ui-step-cont-circle"></span>');
            arr.push('					<span class="ui-step-cont-text">生成原始地图</span>');
            arr.push('					<span class="ui-step-cont-time">' + createTime + '</span>');
            arr.push('				</div>');
            arr.push('			</li>');
            arr.push('			<li class="step-active">');
            arr.push('				<div class="ui-step-line"></div>');
            arr.push('				<div class="ui-step-cont">');
            arr.push('					<span class="ui-step-cont-circle"></span>');
            arr.push('					<span class="ui-step-cont-text">调整位置，发送审核</span>');
            arr.push('					<span class="ui-step-cont-time">' + auditingTime + '</span>');
            arr.push('				</div>');
            arr.push('			</li>');
            arr.push('			<li class="step-end">');
            arr.push('				<div class="ui-step-line"></div>');
            arr.push('				<div class="ui-step-cont">');
            arr.push('					<span class="ui-step-cont-circle"></span>');
            arr.push('					<span class="ui-step-cont-text">发布人才地图</span>');
            arr.push('					<span class="ui-step-cont-time"></span>');
            arr.push('				</div>');
            arr.push('			</li>');
            arr.push('		</ol>');
            arr.push('	</div>');
            arr.push('	<div class="step-div3">');
            arr.push('		<div class="botton-div" id="publish' + inx + '">发布地图</div>');
            arr.push('	</div>');
            $(element).append(arr.join(''));

            $(element).find("#publish" + inx).click(function () {

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
        }
    };

    var mapsDate = {
        map1Id: '#map1',
        CPBtnId: '#map2',
        teamPKId: '#teampk',
        init: function () {
            var self = this;
            self.requestData();
        },
        requestData: function () {
            var self = this;
            self.unBindEvent();
            $.get(urls.getTalentMapsNewDateUrl, function (rs) {
                if (_.isEmpty(rs)) {
                    $('.noMapsDate').removeClass('hide');
                    $('.newMapsDate').addClass('hide');
                    self.unBindEvent();
                    return;
                }
                self.render(rs);
            });
        },
        render: function (rs) {
            var self = this;
            var mapsDate = rs.v;
            self.cycleDate = rs.k;
            $('.noMapsDate').addClass('hide');
            $('.newMapsDate').removeClass('hide');
            $('.bottom-div-maps .maps-date').text(mapsDate);
            self.bindBtnEvent();
        },
        unBindEvent: function () {
            var self = this;
            $(self.map1Id + ',' + self.CPBtnId + ',' + self.teamPKId).addClass('disabled btn-light').removeClass('btn-info').unbind('click');
        },
        bindBtnEvent: function () {
            var self = this;
            $(self.map1Id + ',' + self.CPBtnId + ',' + self.teamPKId).removeClass('disabled btn-light').addClass('btn-info');
            $(self.teamPKId).click(function () {
                if (_.isUndefined(self.cycleDate)) {
                    window.location.href = urls.toTalentMapsForTeamPKUrl;
                    return;
                }
                window.location.href = urls.toTalentMapsForTeamPKUrl + '?cycleDate=' + self.cycleDate;
            });

            $(self.map1Id).click(function () {
                if (_.isUndefined(self.cycleDate)) {
                    window.location.href = urls.toMapsShowUrl;
                    return;
                }
                window.location.href = urls.toMapsShowUrl + '?cycleDate=' + self.cycleDate;
            });

            $(self.CPBtnId).unbind('click').bind('click', function () {
                win.setlocationUrl(urls.toTalentMapsTeamCPViewUrl);
            });
        }
    };
    mapsDate.init();

    queryMapsAuditing.init();

    $("a.index-jxmb-href").click(function () {
        window.location.href = urls.toTalentMapsAuditingHistoryUrl;
    });

    function stringToHex(str) {
        var val = "";
        if (undefined != str) {
            for (var i = 0; i < str.length; i++) {
                if (val == "")
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

});

