require(['jquery', 'jquery-ui', 'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger', 'selectItem', 'organTreeSelector'], function ($) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        toTalentMapsViewUrl: webRoot + "/talentMaps/toTalentMapsView",     //跳转到团队PK首页
        toTeamPKDetailUrl: webRoot + '/talentMaps/toTeamPKDetail',     //跳转到团队PK详细页面
        searchBoxUrl: webRoot + '/common/getTalentMapsSearchBox.do',	//筛选条件信息
        getTeamSettingEmpCountUrl: webRoot + '/talentMaps/getTeamSettingEmpCount.do',  //获取团队设置人员统计
        doTeamInfoInsertUrl: webRoot + '/talentMaps/doTeamInfoInsert.do',           //执行团队PK的团队信息存储
    }

    $(win.document.getElementById('tree')).next().hide();
    $("[data-toggle='tooltip']").tooltip();


    $("#toHomeBtn").click(function () {
        window.location.href = urls.toTalentMapsViewUrl;
    });

    $("#customTeam").click(function () {
        $("#page-one").addClass('hide');
        $("#page-two").removeClass('hide');
    });
    $("#checkSuborgan").click(function () {
        $("#page-one").removeClass('hide');
        $("#page-two").addClass('hide');
    });

    $("#page-one .team-bottom-div").click(function () {
        var _this = $(this);
        var _parent = _this.parent();
        if (_this.hasClass("bg")) {
            _this.removeClass("bg");
            if (_parent.children('.bg').length < 4) {
                srartPKObj.organTeamInit();
            }
        } else {
            if (_parent.children('.bg').length >= 3) {
                showErrMsg('所选团队已超过3个!');
                srartPKObj.organTeamRemove();
            }
            _this.addClass("bg");
        }
        $('#organTeamNum').text(_parent.children('.bg').length);
        var total = 0;
        $.each(_parent.children('.bg'), function (i, obj) {
            total += $(obj).data('toggle');
        });
        if (total > 1000) {
            showErrMsg('团队人数超过1000,请重新选择或使用自定义团队!');
            srartPKObj.organTeamRemove();
        } else {
            srartPKObj.organTeamInit();
        }
    });

    /**
     * 搜索事件
     * @type {{searchBoxId: string, init: searchBoxObj.init}}
     */
    var searchBoxObj = {
        organBar: null,
        sequenceBar: null,
        sequenceSubBar: null,
        abilityBar: null,
        performanceBar: null,
        ageBar: null,
        sexBar: null,
        eduBar: null,
        allParams: [null, null, null],  //三个团队参数初始值
        init: function () {
            var self = this;
            self.requestData();
            self.initOrganBar();

            self.cycleDate = $('#cycleDate').val();
            //团队参数选择事件
            $("#page-two .team-bottom-div").click(function () {
                var _this = $(this);
                var idx = _this.index();
                self.clearAllValues();
                self.initExistData(idx);
                self.initSubmitButton(false);
                $('#customTeamModal').modal('show');
            });
        },
        requestData: function () {  //获取所有选项
            var self = this;
            $.get(urls.searchBoxUrl, function (rs) {
                if (!_.isEmpty(rs)) {
                    self.render(rs);
                }
            });
        },
        render: function (rs) { //渲染数据
            var self = this;
            for (var i = 0; i < rs.length; i++) {
                switch (rs[i].id) {
                    case 'sequenceId':
                        self.initSequenceSelectBar(rs[i].name, rs[i].childs);
                        break;
                    case 'sequenceSubId':
                        self.initSelectBar('sequenceSubBar', 'sequenceSubId', rs[i].name, rs[i].childs);
                        break;
                    case 'abilityId':
                        self.initSelectBar('abilityBar', 'abilityId', rs[i].name, rs[i].childs);
                        break;
                    case 'performanceKey':
                        self.initSelectBar('performanceBar', 'performanceKey', rs[i].name, rs[i].childs);
                        break;
                    case 'ageId':
                        self.initSelectBar('ageBar', 'ageId', rs[i].name, rs[i].childs);
                        break;
                    case 'sexId':
                        self.initSelectBar('sexBar', 'sexId', rs[i].name, rs[i].childs);
                        break;
                    case 'eduId':
                        self.initSelectBar('eduBar', 'eduId', rs[i].name, rs[i].childs);
                        break;
                }
            }
        },
        initTeamName: function (idx) {  //初始化团队名称
            var self = this;
            if (_.isUndefined(idx)) {
                $('#teamName').val('');
                return;
            }
            var _this = $($('#page-two .team-bottom-div').get(idx));
            var teamName = _this.find('.bottom-text').children(':first').text();
            $('#teamName').val(teamName);
            //最后团队没有继续添加功能
            if (idx == 2) $('#continueAddBlock').addClass('hide');
            else $('#continueAddBlock').removeClass('hide');

            self.currIdx = idx; //保存当前索引
        },
        initExistData: function (idx) {
            var self = this;
            self.initTeamName(idx);
            var param = self.allParams[idx];
            if (null == param) return;
            //初始化已选参数
            if (self.sequenceBar && !_.isNull(param.sequenceStr)) self.sequenceBar.setSelectItems(param.sequenceStr);
            if (self.sequenceSubBar && !_.isNull(param.sequenceSubStr)) self.sequenceSubBar.setSelectItems(param.sequenceSubStr);
            if (self.abilityBar && !_.isNull(param.abilityStr)) self.abilityBar.setSelectItems(param.abilityStr);
            if (self.performanceBar && !_.isNull(param.performanceStr)) self.performanceBar.setSelectItems(param.performanceStr);
            if (self.ageBar && !_.isNull(param.ageStr)) self.ageBar.setSelectItems(param.ageStr);
            if (self.sexBar && !_.isNull(param.sexStr)) self.sexBar.setSelectItems(param.sexStr);
            if (self.eduBar && !_.isNull(param.eduStr)) self.eduBar.setSelectItems(param.eduStr);
            if (self.organBar && !_.isNull(param.organId)) self.organBar.organTreeSelector("value", {id: param.organId});
        },
        initOrganBar: function () { //初始化机构
            var self = this;
            self.organBar = $("#organZtree").organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                mouseenterColor: '#fff',
                mouseleaveColor: '#fff',
                onSelect: function (ids, texts) {
                    if (!_.isEmpty(ids)) {
                        self.removeOrganEvent(false);
                        self.requestEmpCount();
                    }
                }
            });
        },
        initSequenceSelectBar: function (text, data) {
            var self = this, $sequenceSub = $('#sequenceSubId');
            var options = {
                selectType: true,
                selectCallback: function (ids, selected, texts) {
                    self.requestEmpCount();

                    var items = self.sequenceBar.getSelectedItems();
                    if (items.length != 1) {
                        $sequenceSub.parent().addClass('hide');
                        self.sequenceSubBar.clearSelected();
                        return;
                    }
                    $sequenceSub.parent().removeClass('hide');
                    var sequenceId = items[0];
                    $.each($sequenceSub.children('.col-sm-11').children(), function (idx, item) {
                        var $this = $(this);
                        if (sequenceId == $this.data('bindObj')) {
                            $this.removeClass('hide');
                        } else {
                            $this.addClass('hide');
                        }
                    });
                }
            };
            self.sequenceBar = new window.W.SelectItem('sequenceId', options);
            self.sequenceBar.setDataItems(data);
        },
        initSelectBar: function (objName, objId, text, data) {  //初始化选项
            var self = this;
            var options = {
                selectType: true,
                selectCallback: function () {
                    self.requestEmpCount();
                }
            };
            self[objName] = new window.W.SelectItem(objId, options);
            self[objName].setDataItems(data);
        },
        initSubmitButton: function (bool) {  //初始化按钮事件
            var self = this;
            var $submitBtn = $('#customTeamSubmitBtn');
            if (!bool) {
                $submitBtn.addClass('btn-light disabled').removeClass('btn-info').unbind('click');
                return;
            }
            $submitBtn.removeClass('btn-light disabled').addClass('btn-info');
            $submitBtn.click(function () {
                var teamName = $.trim($('#teamName').val());
                var teamNum = $('#teamNum').text();
                var continueAddBool = $('#continueAddTeam').prop('checked');
                var idx = self.currIdx;
                self.allParams[idx] = self.getTeamParams();
                if (continueAddBool) {
                    self.clearAllValues();
                    self.initTeamName(idx + 1);
                } else {
                    $('#customTeamModal').modal('hide');
                }
                var _this = $($("#page-two .team-bottom-div").get(idx));
                if (teamName != ('团队' + (idx + 1))) {
                    var feature = self.getTeamNameFeature(idx);
                    _this.find('.circle span').text(feature);
                    _this.find('.bottom-text span').text(teamName);
                }
                _this.find('.bottom-text a').html(teamNum + '人');

                self.checkCustomTeamNumber();
            });
        },
        removeOrganEvent: function (bool) { //机构移除事件
            var self = this, topOrganId = $('#topOrganId').val(), topOrganName = $('#topOrganName').val();
            var $btn = $('#organRemovebtn');
            if (bool) {
                $btn.addClass('hide').unbind('click');
                self.organBar && self.organBar.organTreeSelector("value", {id: topOrganId, text: topOrganName});
            } else {
                $btn.removeClass('hide');
                $btn.unbind('click').click(function () {
                    self.removeOrganEvent(true);
                });
            }
        },
        clearAllValues: function () { //清除所有值
            var self = this;
            self.initTeamName();

            self.sequenceBar.clearSelected();
            self.sequenceSubBar.clearSelected();
            self.abilityBar.clearSelected();
            self.performanceBar.clearSelected();
            self.ageBar.clearSelected();
            self.sexBar.clearSelected();
            self.eduBar.clearSelected();
            self.removeOrganEvent(true);
            $('#continueAddTeam').prop('checked', false);
            $('#teamNum').html('...');
            return;
        },
        requestEmpCount: function () {  //获取团队成员统计
            var self = this;
            var params = self.getTeamParams();
            $('#teamNum').text('...');
            self.initSubmitButton(false);
            $.get(urls.getTeamSettingEmpCountUrl, params, function (rs) {
                if (_.isNumber(rs)) $('#teamNum').text(rs);
                else $('#teamNum').text('...');

                if (rs != 0) self.initSubmitButton(true);
            });
        },
        getTeamNameFeature: function (idx) {    //获取团队名称特征
            var self = this, allName = [];
            $.each($('#page-two .team-bottom-div'), function (i, obj) {
                var $obj = $(obj);
                allName[i] = $obj.find('.bottom-text span').text();
            });
            var currName = allName[idx], feature = '';
            for (var i = 0; i < currName.length; i++) {
                var num = 0;
                for (var j = 0; j < allName.length; j++) {
                    if (allName[j][i] == currName[i]) {
                        num++;
                    }
                }
                if (num >= 2) continue;
                feature = currName[i];
            }
            return feature;
        },
        getTeamParams: function () {    //获取团队参数
            var self = this;
            var organId = self.organBar.organTreeSelector('value');
            var sequenceItems = self.sequenceBar.getSelectedItems();
            var sequenceSubItems = self.sequenceSubBar.getSelectedItems();
            var abilityItems = self.abilityBar.getSelectedItems();
            var performanceItems = self.performanceBar.getSelectedItems();
            var ageItems = self.ageBar.getSelectedItems();
            var sexItems = self.sexBar.getSelectedItems();
            var eduItems = self.eduBar.getSelectedItems();
            var yearMonths = self.cycleDate;
            var params = {
                organId: organId,
                sequenceStr: _.isEmpty(sequenceItems) ? null : sequenceItems.join(','),
                sequenceSubStr: _.isEmpty(sequenceSubItems) ? null : sequenceSubItems.join(','),
                abilityStr: _.isEmpty(abilityItems) ? null : abilityItems.join(','),
                performanceStr: _.isEmpty(performanceItems) ? null : performanceItems.join(','),
                ageStr: _.isEmpty(ageItems) ? null : ageItems.join(','),
                sexStr: _.isEmpty(sexItems) ? null : sexItems.join(','),
                eduStr: _.isEmpty(eduItems) ? null : eduItems.join(','),
                yearMonths: yearMonths
            }
            return params;
        },
        checkTeamName: function (idx, teamName) {
            var self = this, bool = false;
            $.each($('#page-two .team-bottom-div:not(' + idx + ')'), function (i, obj) {
                var $obj = $(obj);
                var name = $obj.find('.bottom-text span').text();
                if (teamName == name) {
                    bool = true;
                    return true;
                }
            });
            return bool;
        },
        checkCustomTeamNumber: function () {
            var self = this, allNum = 0;
            var params = self.allParams;
            for (var i = 0; i < params.length; i++) {
                if (null == params[i]) continue;
                var $obj = $($('#page-two .team-bottom-div').get(i));
                var teamNum = $obj.find('.bottom-text a').html();
                allNum += parseInt(teamNum);
            }
            customAllNum = allNum;
            if (allNum > 1000) {
                showErrMsg('团队人数超过1000,请重新选择定义团队!');
                srartPKObj.customTeamRemove();
            } else {
                srartPKObj.customTeamInit();
            }
        }
    }
    var customAllNum = 0;
    searchBoxObj.init();

    var srartPKObj = {
        organTeamId: '#organTeamBtn',
        customTeamId: '#customTeamBtn',
        init: function () {
            var self = this;
            self.organTeamInit();
            self.customTeamInit();
        },
        organTeamInit: function () {  //机构团队按钮绑定事件
            var self = this;
            var objs = $('#page-one .maps-pk-main .team-bottom-div.bg');
            if (objs.length == 0) {
                self.organTeamRemove();
                return;
            }
            $(self.organTeamId).removeClass('btn-light disabled').addClass('btn-info');
            $(self.organTeamId).unbind('click').click(function () {
                var arr = new Array();
                var organTeamSelect = $('#organTeamCheckBox').prop('checked');
                $.each(objs, function (i, obj) {
                    var $obj = $(obj);
                    var organId = $obj.data('organid'), organName = $obj.data('organname');
                    var item = {
                        teamName: organName,
                        queryDto: {organId: organId, yearMonths: searchBoxObj.cycleDate},
                        pkView: organTeamSelect
                    };
                    arr.push(item);
                });
                $.post(urls.doTeamInfoInsertUrl, {teamInfoStr: JSON.stringify(arr)}, function (rs) {
                    if (rs.type) {
                        var cycleDate = searchBoxObj.cycleDate;
                        window.location.href = urls.toTeamPKDetailUrl + '?cycleDate=' + cycleDate;
                    }
                });
            });
        },
        customTeamInit: function () { //自定义团队按钮绑定事件
            var self = this;
            if (customAllNum == 0) {
                self.customTeamRemove();
                return;
            }
            $(self.customTeamId).removeClass('btn-light disabled').addClass('btn-info');
            $(self.customTeamId).unbind('click').click(function () {
                var arr = new Array();
                var params = searchBoxObj.allParams;
                var customTeamSelect = $('#customTeamCheckBox').prop('checked');
                for (var i = 0; i < params.length; i++) {
                    if (null == params[i]) continue;
                    var $obj = $($('#page-two .team-bottom-div').get(i));
                    var teamName = $obj.find('.bottom-text span').text();
                    var item = {teamName: teamName, queryDto: params[i], pkView: customTeamSelect};
                    arr.push(item);
                }
                $.post(urls.doTeamInfoInsertUrl, {teamInfoStr: JSON.stringify(arr)}, function (rs) {
                    if (rs.type) {
                        var cycleDate = searchBoxObj.cycleDate;
                        window.location.href = urls.toTeamPKDetailUrl + '?cycleDate=' + cycleDate;
                    }
                });
            });
        },
        organTeamRemove: function () {    //机构团队按钮解除事件
            var self = this;
            $(self.organTeamId).addClass('btn-light disabled').removeClass('btn-info').unbind('click');
        },
        customTeamRemove: function () {   //自定义团队按钮解除事件
            var self = this;
            $(self.customTeamId).addClass('btn-light disabled').removeClass('btn-info').unbind('click');
        }
    }
    srartPKObj.init();

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