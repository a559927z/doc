require(['jquery', 'jquery-ui', 'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger', 'talent-map2', 'select2', 'selectItem',
    'organTreeSelector', 'mapGrid', 'inventoryReport', 'keyTalentsAdd', 'jBootstrapPage', 'cardTabel'], function ($) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        toMapsHomeUrl: webRoot + '/talentMaps/toTalentMapsView',          //跳转到人才地图首页
        searchBoxUrl: webRoot + '/common/getTalentMapsSearchBox.do',	//筛选条件信息
        getUserTeamInfoUrl: webRoot + '/talentMaps/getUserTeamInfo.do',        //获取团队信息
        getMapsBaseInfoUrl: webRoot + '/talentMaps/getMapsBaseInfo.do',             //获取地图轴线数据
        getTeamSettingEmpCountUrl: webRoot + '/talentMaps/getTeamSettingEmpCount.do',  //获取团队设置人员统计
        getTeamEmpPointUrl: webRoot + '/talentMaps/getTeamEmpPoint.do',          //获取地图员工点信息
        doDeleteUserTeamUrl: webRoot + '/talentMaps/doDeleteUserTeam.do',       //移除用户保存的团队信息
        queryAbilityForListUrl: webRoot + '/talentMaps/queryAbilityForList.do',	    //标题列表
        doEditUserTeamInfoUrl: webRoot + '/talentMaps/doEditUserTeamInfo.do',           //执行团队PK的团队信息修复
        getTalentMapsEmpPointUrl: webRoot + '/talentMaps/getTalentMapsEmpPoint.do',     //获取地图员工点信息
        getUserTeamNumUrl: webRoot + '/talentMaps/getUserTeamNum.do',               //获取用户保存团队数量
    }
    $(win.document.getElementById('tree')).next().hide();

    //初始化'加入对比'
    var stackObject;

    function loadStack() {
        if (win.stackObject) {
            stackObject = win.stackObject;
        }
        //假如有对比人员则显示，没有则隐藏
        if (stackObject && stackObject.bottomStock('getPersonIds') != '') stackObject.bottomStock('hideFrame', 0);
        else stackObject.bottomStock('hideFrame', 1);
    }

    loadStack();

    /**
     * 团队条件事件
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
        teamNames: [],  //团队名称
        allParams: [],  //团队参数初始值
        init: function () {
            var self = this;
            self.requestData();
            self.initOrganBar();

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
            var self = this, $teamName = $('#teamName');
            if (_.isUndefined(idx)) {
                $teamName.val('');
                return;
            }
            self.currIdx = idx; //保存当前索引
            $teamName.focusout(function () {
                var $this = $(this);
                var teamName = $this.val();
                var bool = self.checkTeamName(idx, teamName);
                if (bool) {
                    showErrMsg('团队名称已存在，请重新输入！');
                    self.initSubmitButton(false);
                } else {
                    self.initSubmitButton(true);
                }
            });

            var _this = $('#teamBlock').children(':eq(' + idx + ')');
            if (_this.hasClass('team-main-add')) {
                var newName = self.getTeamName(idx);
                $teamName.val(newName);
                return;
            }
            var teamName = _this.find('.team-main-name').text();
            $teamName.val(teamName);
        },
        initExistData: function (idx) {
            var self = this, topOrganId = $('#topOrganId').val(), topOrganName = $('#topOrganName').val();
            self.initTeamName(idx);
            var param = self.allParams[idx];
            if (_.isUndefined(param)) {
                var values = {id: topOrganId, text: topOrganName};
                if (self.organBar && !_.isEmpty(topOrganId)) self.organBar.organTreeSelector("value", values);
                return;
            }
            //初始化已选参数
            if (self.sequenceBar && (!_.isNull(param.sequenceStr) && !_.isUndefined(param.sequenceStr))) self.sequenceBar.setSelectItems(param.sequenceStr);
            //子序列初始化
            if (self.sequenceSubBar && (!_.isNull(param.sequenceStr) && !_.isUndefined(param.sequenceStr) && (param.sequenceStr).split(',').length == 1)) {
                self.initSequenceSubShow(true, param.sequenceStr);
                if (!_.isNull(param.sequenceSubStr) && !_.isUndefined(param.sequenceSubStr)) {
                    self.sequenceSubBar.setSelectItems(param.sequenceSubStr);
                }
            } else {
                self.initSequenceSubShow(false);
            }
            if (self.abilityBar && (!_.isNull(param.abilityStr) && !_.isUndefined(param.abilityStr))) self.abilityBar.setSelectItems(param.abilityStr);
            if (self.performanceBar && (!_.isNull(param.performanceStr) && !_.isUndefined(param.performanceStr))) self.performanceBar.setSelectItems(param.performanceStr);
            if (self.ageBar && (!_.isNull(param.ageStr) && !_.isUndefined(param.ageStr))) self.ageBar.setSelectItems(param.ageStr);
            if (self.sexBar && (!_.isNull(param.sexStr) && !_.isUndefined(param.sexStr))) self.sexBar.setSelectItems(param.sexStr);
            if (self.eduBar && (!_.isNull(param.eduStr) && !_.isUndefined(param.eduStr))) self.eduBar.setSelectItems(param.eduStr);
            if (self.organBar && !_.isNull(param.organId)) self.organBar.organTreeSelector("value", {id: param.organId});

            self.requestEmpCount();
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
            var self = this;
            var options = {
                selectType: true,
                selectCallback: function (ids, selected, texts) {
                    var items = self.sequenceBar.getSelectedItems();
                    if (items.length != 1) {
                        self.initSequenceSubShow(false);
                        self.sequenceSubBar.clearSelected();
                        self.requestEmpCount();
                        return;
                    }
                    self.initSequenceSubShow(true, items[0]);
                    self.requestEmpCount();
                }
            };
            self.sequenceBar = new window.W.SelectItem('sequenceId', options);
            self.sequenceBar.setDataItems(data);
        },
        initSequenceSubShow: function (isShow, sequenceId) {
            var self = this, $sequenceSub = $('#sequenceSubId');
            if (!isShow) {
                $sequenceSub.parent().addClass('hide');
                return;
            }
            $sequenceSub.parent().removeClass('hide');
            $.each($sequenceSub.children('.col-sm-11').children(), function (idx, item) {
                var $this = $(this);
                if (sequenceId == $this.data('bindObj')) {
                    $this.removeClass('hide');
                } else {
                    $this.addClass('hide');
                }
            });
        },
        initSelectBar: function (objName, objId, text, data) {  //初始化选项
            var self = this;
            var options = {
                selectType: true,
                selectCallback: function (ids, selected, texts) {
                    self.requestEmpCount();
                }
            };
            self[objName] = new window.W.SelectItem(objId, options);
            self[objName].setDataItems(data);
        },
        initSubmitButton: function (bool) {  //初始化按钮事件
            var self = this, $submitBtn = $('#teamSubmitBtn');
            if (!bool) {
                $submitBtn.addClass('btn-light disabled').removeClass('btn-info').unbind('click');
                return;
            }
            $submitBtn.removeClass('btn-light disabled').addClass('btn-info');
            $submitBtn.unbind('click').click(function () {
                var teamName = $.trim($('#teamName').val());
                var teamNum = $('#teamNum').text();
                var addTeamBool = $('#addTeamInput').prop('checked');
                if (addTeamBool) {  //没有勾选保存则直接渲染地图
                    var _this = $($('#teamBlock').children().get(self.currIdx));
                    var teamId = _this.data('team');
                    $.get(urls.getUserTeamNumUrl, {teamId: teamId}, function (rs) {
                        if (!rs.type || rs.t >= 3) {
                            showErrMsg("默认团队已超过3个，不能保存");
                            return;
                        }
                        self.submitTeamEvent(teamName, teamNum, addTeamBool);
                    });
                    return;
                }
                self.submitTeamEvent(teamName, teamNum, addTeamBool);
            });
        },
        submitTeamEvent: function (teamName, teamNum, addTeamBool) {
            var self = this, $submitBtn = $('#teamSubmitBtn'), $teamBlock = $('#teamBlock');

            var param = self.getTeamParams();
            var idx = self.currIdx;
            if (_.isUndefined(self.allParams[idx])) self.allParams.push(param);
            else self.allParams[idx] = param;

            $('#customTeamModal').modal('hide');

            var _this = $($teamBlock.children().get(idx));

            if (_this.hasClass('team-main-add')) {
                var html = '<span class="team-main-name"></span><i class="icon-remove"></i><i class="icon-pencil"></i></div>';
                _this.removeClass('team-main-add').addClass('team-main').html(html);
                _this.unbind('click');
                _this.find('.icon-pencil').click(function () {
                    pageLoadingObj.teamEditEvent($(this));
                });
                _this.find('.icon-remove').click(function () {
                    pageLoadingObj.teamRemoveEvent($(this));
                });

                if ($teamBlock.children().length < 3 && $teamBlock.children('.team-main-name').length == 0) {
                    $teamBlock.append(pageLoadingObj.initNoTeamHtml());
                    //添加事件
                    $($teamBlock.find('.team-main-add')).click(function () {
                        pageLoadingObj.teamAddEvent($(this));
                    });
                }
            }
            _this.find('.team-main-name').text(teamName);
            _this.data('total', teamNum);

            var teamId = _this.data('team');
            param.team = teamName;
            if (addTeamBool) {
                var item = {teamName: teamName, queryDto: param, pkView: addTeamBool};
                if (!_.isEmpty(teamId)) {
                    item.teamId = teamId;
                }
                self.submitTeamData(item);
                return;
            }

            maps.initMapsMain();
        },
        submitTeamData: function (params) {
            var self = this, idx = self.currIdx;
            $.post(urls.doEditUserTeamInfoUrl, {teamInfoStr: JSON.stringify(params)}, function (rs) {
                if (!rs.type) {
                    showErrMsg("后台服务器出现问题，请联系管理员！");
                    return;
                }
                if (rs.t) {
                    $('#teamBlock').children(':eq(' + idx + ')').data('team', rs.msg);
                }
                maps.initMapsMain();
            });
        },
        removeOrganEvent: function (bool) { //机构移除事件
            var self = this;
            var $btn = $('#organRemovebtn');
            if (bool) {
                $btn.addClass('hide').unbind('click');
                self.organBar && self.organBar.organTreeSelector("value", {id: '', text: ''});
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

            self.removeOrganEvent(true);

            self.sequenceBar.clearSelected();
            self.sequenceSubBar.clearSelected();
            self.abilityBar.clearSelected();
            self.performanceBar.clearSelected();
            self.ageBar.clearSelected();
            self.sexBar.clearSelected();
            self.eduBar.clearSelected();
            $('#continueAddTeam').prop('checked', false);
            $('#teamNum').text('...');
        },
        requestEmpCount: function () {  //获取团队成员统计
            var self = this;
            var params = self.getTeamParams();
            $('#teamNum').text('...');
            self.initSubmitButton(false);
            $.get(urls.getTeamSettingEmpCountUrl, params, function (rs) {
                if (!_.isNumber(rs)) {
                    $('#teamNum').text('...');
                    return;
                }
                $('#teamNum').text(rs);
                if (rs == 0) return;
                var bool = self.checkCustomTeamNumber(rs, self.currIdx);
                if (bool) self.initSubmitButton(true);

            });
        },
        getTeamName: function (idx) {
            var self = this;
            var name = '团队' + (idx + 1);
            var bool = self.checkTeamName(idx, name);
            if (!bool) return name;
            var rsName;
            for (var i = 0; i < 3; i++) {
                var newName = '团队' + (i + 1);
                var newBool = self.checkTeamName(idx, newName);
                if (!newBool) {
                    rsName = newName;
                    break;
                }
            }
            return rsName;
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
            var yearMonths = pageLoadingObj.cycleDate;
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
            var bool = false;
            $.each($('#teamBlock').children(':not(' + idx + ')'), function (i, obj) {
                var $obj = $(obj);
                var name = $obj.find('.team-main-name').text();
                if (teamName == name) {
                    bool = true;
                    return true;
                }
            });
            return bool;
        },
        checkCustomTeamNumber: function (num, idx) {
            var self = this, allNum = num;
            var params = self.allParams;
            for (var i = 0; i < params.length; i++) {
                if (i == idx || null == params[i]) continue;
                var $obj = $('#teamBlock').children(':eq(' + i + ')');
                var teamNum = $obj.data('total');
                allNum += _.isUndefined(teamNum) ? 0 : parseInt(teamNum);
            }
            if (allNum > 1000) {
                showErrMsg('团队人数超过1000,请重新选择定义团队!');
                return false;
            } else {
                return true
            }
        }
    }
    searchBoxObj.init();

    var pageLoadingObj = {
        selectObj: $('#mapsTypeSelected'),
        navSelectObj: null,
        init: function () {
            var self = this;
            var cycleDate = $('#cycleDate').val();
            self.cycleDate = cycleDate;
            self.initDateSelect();
            self.initMapsTypeSelect();
            self.requestData();
        },
        requestData: function () {
            var self = this;
            $.post(urls.getUserTeamInfoUrl, function (rs) {
                if (!_.isEmpty(rs)) {
                    self.render(rs);
                }
            });
        },
        render: function (data) {
            var self = this, $block = $('#teamBlock'), teamNames = [], allParams = [];
            $block.html('');
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                if (!_.isNull(obj.requirement)) {
                    var param = eval('(' + obj.requirement + ')');
                    param.team = obj.teamName;
                    teamNames.push(obj.teamName);
                    allParams.push(param);
                }
                $block.append(self.initTeamHtml(obj));
            }
            //编辑事件
            $($block.find('.icon-pencil')).click(function () {
                self.teamEditEvent($(this));
            });
            //移除事件
            $($block.find('.icon-remove')).click(function () {
                self.teamRemoveEvent($(this));
            });
            if ($block.children().length < 3) {
                $block.append(self.initNoTeamHtml());
                //添加事件
                $($block.find('.team-main-add')).click(function () {
                    self.teamAddEvent($(this));
                });
            }
            if (!_.isNull(maps.mapObj))  maps.mapObj.initTeamName(teamNames);
            searchBoxObj.allParams = allParams;
            maps.initMapsMain();
        },
        teamEditEvent: function (_this) {
            var idx = _this.parents('.team-main').index();
            searchBoxObj.clearAllValues();
            searchBoxObj.initExistData(idx);
            searchBoxObj.initSubmitButton(false);
            $('#customTeamModal').modal('show');

            $("[data-toggle='tooltip']").tooltip();
        },
        teamRemoveEvent: function (_this) {
            var self = this, $removeTeam = $('#removeTeamModal');
            var $parent = _this.parents('.team-main');
            var idx = $parent.index();
            var teamId = $parent.data('team');
            var successBtn = $removeTeam.find('.success-btn');
            $removeTeam.modal('show');

            successBtn.unbind('click').click(function () {
                var remoteBool = $('#delRemoteInput').prop('checked');
                if (!remoteBool || _.isUndefined(teamId)) {
                    self.renderTeamRemove($parent, idx);
                    return;
                }
                $.get(urls.doDeleteUserTeamUrl, {teamId: teamId}, function (rs) {
                    if (!rs.type) {
                        showErrMsg("后台服务器出现问题，请联系管理员！");
                        return;
                    }
                    self.renderTeamRemove($parent, idx);
                });
            });
        },
        teamAddEvent: function (_this) {
            var idx = _this.index(), topOrganId = $('#topOrganId').val(), topOrganName = $('#topOrganName').val();
            searchBoxObj.clearAllValues();
            searchBoxObj.initTeamName(idx);
            if (!_.isEmpty(topOrganId) && searchBoxObj.organBar) {
                var values = {id: topOrganId, text: topOrganName};
                searchBoxObj.organBar.organTreeSelector("value", values);
            }
            searchBoxObj.initSubmitButton(false);
            $('#customTeamModal').modal('show');

            $("[data-toggle='tooltip']").tooltip();
        },
        renderTeamRemove: function (_parent, idx) {
            var self = this, $removeTeam = $('#removeTeamModal'), $block = $('#teamBlock');
            _parent.remove();
            $removeTeam.modal('hide');

            if ($block.children().length < 3 && $block.children('.team-main-name').length == 0) {
                $block.append(self.initNoTeamHtml());
                //添加事件
                $($block.find('.team-main-add')).unbind('click').click(function () {
                    self.teamAddEvent($(this));
                });
            }
            searchBoxObj.allParams.splice(idx, 1);

            maps.initMapsMain();
        },
        initTeamHtml: function (obj) {
            var self = this;
            var html = '';
            html += '<div class="team-main" data-team="' + obj.teamId + '">';
            html += '<span class="team-main-name">' + obj.teamName + '</span>';
            html += '<i class="icon-remove"></i><i class="icon-pencil"></i>';
            html += '</div>';
            return html;
        },
        initNoTeamHtml: function () {
            var html = '';
            html += '<div class="team-main-add">';
            html += '<i class="icon-plus"></i><span>添加团队</span>';
            html += '</div>';
            return html;
        },
        initDateSelect: function () {
            var self = this, cycleDate = self.cycleDate, $dateSelect = $('#dateSelect');

            $dateSelect.val(cycleDate);
            self.setMapsTitle();
            $dateSelect.unbind('change').change(function () {
                var $this = $(this);
                var date = parseInt($this.val());
                self.cycleDate = date;
                self.setMapsTitle();

                var params = searchBoxObj.allParams;
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (_.isNull(param)) continue;
                    param.yearMonths = date;
                }

                maps.initMapsMain();
            });
        },
        initNavSearch: function () {
            var self = this, $navSearchEmp = $('#navSearchEmp'), allPoints = maps.mapObj.getAllPoints(), teamArray = maps.mapObj.options.teamArray;
            var pointsData = [];
            $.each(allPoints, function (idx, item) {
                var teamName = teamArray[item._t];
                item.id = item._t + '_' + item.empId;
                var points = _.find(pointsData, function (obj) {
                    return obj.text == teamName;
                });
                if (_.isUndefined(points)) pointsData.push({text: teamName, children: [item]});
                else points.children.push(item);
            });

            $navSearchEmp.html('<option value="-1">请输入名称</option>');
            // 下拉菜单
            self.navSelectObj = $navSearchEmp.select2({
                language: 'zh-CN',
                data: pointsData,
                placeholder: {
                    id: '-1',
                    text: '请输入名称'
                },
                allowClear: true
            });
            self.navSelectObj.unbind('select2:select').on('select2:select', function (evt) {
                var obj = evt.params.data;

                var teamPoints = _.find(pointsData, function (o) {
                    return o.text == obj.team;
                });
                var point = _.find(teamPoints.children, function (o) {
                    return o.empId == obj.empId;
                });
                maps.drawDislog(point, true);
            });
            self.navSelectObj.unbind('select2:unselect').on('select2:unselect', function (evt) {
                $('#navSearchBlock').addClass('hide');
            });
        },
        clearNavSearch: function () {
            var self = this;
            self.navSelectObj.empty();
            $('#navSearchBlock').addClass('hide');
        },
        initMapsTypeSelect: function () {
            var self = this;
            self.selectObj.change(function (e) {
                var txt = $(this).val();
                if (txt == self.selected) return;
                if (txt == 1) {
                    $('#grid-group').removeClass('hide');
                    $('#map,#mapAxisBlock,.help-inline').addClass('hide');
                } else {
                    $('#grid-group').addClass('hide');
                    $('#map,#mapAxisBlock,.help-inline').removeClass('hide');
                }
                self.selected = txt;
                maps.initMapsMain();
            });
        },
        setMapsTitle: function () {
            var self = this;
            var text = $('#dateSelect').find('option:selected').text();
            $('.teamMapsTitle').text(text);
        }
    }


    var mapsEmpsTablesOption = {    //人才地图小弹窗option
        url: urls.getTalentMapsEmpPointUrl,
        postData: {},
        splitNum: 12,
        type: "post",
        splitHeight: 10,
        modelHeight: 312,
        height: 312,
        rows: 96,
        model: $("#maps-emp-table").html(),
        formatModel: function (el, o) {
            var result = '<span class="u-point-wrapper" data-row="' + o.empId + '">' +
                '<i class="icon-circle u-point" style="color: ' + maps.currColor + ';"></i>' +
                '<span class="u-point-label" title="' + o.text + '">' + o.text + '</span></span>';
            return result;
        },
        loadRowComple: function (el, o, index) {
            $('#emp_tabel').find('.u-point-wrapper').unbind('click').click(function () {
                var $this = $(this), empId = $this.data('row');
                $this.inventoryReport({
                    empId: empId
                });
            });
        }
    }

    var maps = {
        mapId: '#map',
        fullMapId: '#fullMap',
        empTabel: '#emp_tabel',
        mapObj: null,
        fullMapObj: null,
        mapData: [],
        currColor: '#0777F1',
        init: function () {
            var self = this;
            self.requestMapsAxis();
        },
        requestMapsAxis: function () {
            var self = this;
            $.get(urls.getMapsBaseInfoUrl, function (rs) {
                if (!_.isEmpty(rs)) {
                    //初始化地图基础信息
                    self.xAxisTitle = rs.xAxisTitle;
                    self.xAxisData = _.map(rs.xAxisData, _.iteratee('name'));
                    self.yAxisTitle = rs.yAxisTitle;
                    self.yAxisData = _.map(rs.yAxisData, _.iteratee('name'));
                    self.zAxisData = rs.zAxisData;
                    self.initMaps();
                }
            });
        },
        initMaps: function () {
            var self = this;
            var w = $(self.mapId).parent().width() - 30;
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: true,
                showTeam: true,     //是否启动团队分组
                xAxis: {
                    title: self.xAxisTitle,
                    data: self.xAxisData
                },
                yAxis: {
                    title: self.yAxisTitle,
                    data: self.yAxisData
                },
                zAxis: {
                    data: self.zAxisData
                },
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                        var mapObj = maps.mapObj;
                        var teamPoints = mapObj.getZAxisTeamPoints();
                        var len = mapObj.getAllPointsNumber();
                        self.renderAxis(teamPoints, len);

                        pageLoadingObj.initNavSearch();
                    },
                    onClick: function (elem, point) {
                        $(this).inventoryReport({
                            empId: point.empId
                        });
                    }
                },
                groupPoint: {
                    onClick: function (elem, points, groupInfo) {
                        var tIdx = groupInfo.t;
                        var param = searchBoxObj.allParams[tIdx], point = _.find(points, function (p) {
                            return p._t == tIdx;
                        });
                        if (_.isUndefined(param) || _.isUndefined(point)) return;

                        maps.currColor = self.mapObj.options.teamColors[tIdx];
                        param.yLaberId = point.yLabelId;
                        param.xLaberId = point.xLabelId;

                        self.talentMapsModal(param, self.yAxisData[groupInfo.y - 1], self.xAxisData[groupInfo.x - 1], point.team);
                    }
                }
            });
            self.attachEvent(self.mapObj);

            pageLoadingObj.init();    //初始化团队信息
        },
        attachEvent: function (mapObj) {
            var self = this;
            //显示/隐藏总数
            $("#toggleGroupMode").click(function () {
                mapObj.toggleGroupMode();
            });
            //显示/隐藏网格
            $("#toggleGrids").click(function () {
                mapObj.toggleGrids();
            });
        },
        initMapsMain: function () {
            var self = this, params = searchBoxObj.allParams, bool = pageLoadingObj.selected, $gridGroup = $('#grid-group');
            //清除之前数据
            if (bool == 1) $gridGroup.empty();
            else {
                var teamNames = [];
                for (var i = 0; i < params.length; i++) {
                    teamNames.push(params[i].team);
                }
                self.mapObj.clear();
                self.mapData = [];
                maps.mapObj.initTeamName(teamNames);
            }

            for (var i = 0; i < params.length; i++) {
                if (bool == 1) self.renderMapGrid(params[i], i);
                else self.requestData(params[i], i);
            }
        },
        renderMapGrid: function (param, idx) {
            var self = this, $gridGroup = $('#grid-group'), gridId = 'grid' + idx;
            var $gridBlock = $('#' + gridId);
            if (_.isNull($gridBlock.length) || $gridBlock.length == 0) {
                var html = '<div><h5 class="bolder">' + param.team + '</h5><div id="' + gridId + '" class="grid-view"></div></div>';
                $gridGroup.append(html);
                $gridBlock = $('#' + gridId);
                $gridBlock.mapGrid({param: param});
                return;
            }
            $gridBlock.prev().html(param.team);
            $gridBlock.mapGrid({param: param});
        },
        renderAxis: function (data, len) {
            var self = this;
            var $hideBlock = $('#mapAxisBlock .map-axis-hide'), $showBlock = $('#mapAxisBlock .map-axis-show');
            var $showBlockRemove = $showBlock.find('.icon-remove');
            //渲染主体内容
            $showBlock.find('.map-axis-main').html(self.renderAxisHtml(data, len));
            if ($hideBlock.is(':hidden') && $showBlock.is(':hidden')) {
                $hideBlock.show();
                $showBlock.hide();
            }
            //绑定相关事件
            $hideBlock.unbind('click').click(function () {
                $(this).hide();
                $showBlock.show();
            });
            $showBlockRemove.unbind('click').click(function () {
                $showBlock.hide();
                $hideBlock.show();
            });
        },
        renderAxisHtml: function (data, len) {
            var html = '';
            for (var i = 0; i < data.length; i++) {
                var obj = data[i], total = obj.pointsTotal, w = Tc.formatFloat(total / len * 100, 0);
                html += '<div class="row map-axis-area">';
                html += '<div class="col-xs-3">' + obj.name + '</div>';
                html += '<div class="col-xs-6 ct-col1">';
                html += '<div class="progress" data-percent="' + w + '%"><div class="progress-bar" style="width:' + w + '%;background-color: ' + obj.color + ';"></div></div>';
                html += '<div class="map-axis-team">';
                for (var j = 0; j < obj.points.length; j++) {
                    var team = obj.points[j];
                    html += '<span><i class="icon-circle" style="color: ' + team.color + ';"></i>&nbsp;' + team.data.length + '人</span>'
                }
                html += '</div></div>';
                html += '<div class="col-xs-3 ct-colr">&nbsp;&nbsp;' + total + '人</div>';
                html += '</div>';
            }
            return html;
        },
        requestData: function (params, idx) {
            var self = this;
            if (_.isUndefined(params)) {
                var mapData = self.mapData;
                for (var i = 0; i < mapData.length; i++) {
                    var data = mapData[i];
                    if (_.isNull(data)) continue;
                    if (!_.isNull(self.fullMapObj))  self.fullMapObj.addPoints(data);
                }
                return;
            }
            $.ajax({
                url: urls.getTeamEmpPointUrl,
                data: params,
                type: 'post',
                async: false,
                success: function (rs) {
                    if (!_.isEmpty(rs)) {
                        if (!_.isNull(self.mapObj)) {
                            self.mapData[idx] = rs;
                            self.mapObj.addPoints(rs);
                        }
                        //初始化赋值团队人数
                        var $teamObj = $($('#teamBlock .team-main').get(idx));
                        if (!$teamObj.data('total')) $teamObj.data('total', rs.length);
                    }
                }
            });
        },
        fullInit: function () {
            var self = this;
            var w = $(self.fullMapId).parent().width() - 30;
            self.fullMapObj = $(self.fullMapId).talentMap({
                width: w,
                showNum: true,
                showTeam: true,
                xAxis: {
                    title: self.xAxisTitle,
                    data: self.xAxisData
                },
                yAxis: {
                    title: self.yAxisTitle,
                    data: self.yAxisData
                },
                zAxis: {
                    data: self.zAxisData
                },
                point: {
                    afterCreate: function (elem, point) {
                    },
                    onClick: function (elem, point) {
                        $(this).inventoryReport({
                            empId: point.empId
                        });
                    }
                }
            });
            self.requestData();
        },
        talentMapsModal: function (param, yLabel, xLabel, tName) {
            var self = this, $talentMapsModal = $('#talentMapsModal');
            $talentMapsModal.find('.modal-header-text').text(yLabel + '  ' + xLabel + '  ' + tName);
            $talentMapsModal.modal('show');
            if ($(self.empTabel).html() == '') {
                mapsEmpsTablesOption.postData = param;
                $(self.empTabel).cardTabel(mapsEmpsTablesOption);
            } else {
                $(self.empTabel).clearCardTableData().setCardTableParam({
                    postData: param
                }).reloadCardTable();
            }
        },
        drawDislog: function (point, bool) {
            var self = this;
            var $navSearchBlock = $('#navSearchBlock');
            $('#navSearchBlock').removeClass('hide');
            $navSearchBlock.html(self.drawEmpDislog(point));
            $navSearchBlock.find('button.btn').click(function () {
                var $this = $(this), pointId = $this.data('toggle');
                $this.inventoryReport({
                    empId: pointId
                });
            });
            self.point = point;
        },
        drawEmpDislog: function (point) {
            var html = $('<div class="search-emp-main"></div>');

            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.text + '</span>');
            html.append('<span class="col-xs-6 ct-col1 right"><button class="btn btn-primary" data-toggle="' + point.empId + '">盘点报告</button></span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.xLabel + '</span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.yLabel + '</span>');

            return html;
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width() - 30;
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
            var fw = $(self.fullMapId).parent().width() - 30;
            if (fw > 0 && !_.isNull(self.fullMapObj)) self.fullMapObj.resize(fw);
        }
    }
    maps.init();


    /***
     * 全屏下的搜索功能
     */
    var searchObj = {
        show: false,
        isFirst: true,
        searchBtn: '#searchEmpBtn',
        searchBlock: '#searchEmpBlock',
        init: function () {
            var self = this;
            //解除绑定是为了防止取消全屏之后重复绑定事件
            $(self.searchBtn).unbind('click').click(function () {
                var $searchBlock = $(self.searchBlock);
                $searchBlock.height(0);
                var h = $searchBlock.parents('.row').height();
                var winh = document.body.clientHeight - 41;
                $searchBlock.height(h > winh ? h : winh);
                $searchBlock.fadeToggle('fast', 'linear');
                self.show = self.show ? false : true;

                if (!self.show) {
                    if (self.point) self.drawDislog(self.point, false);
                    return;
                }

                //第一次获取地图人员数据并渲染select2的值
                if (self.isFirst) self.requestData();

                var select2Obj = self.selectObj;
                if (undefined === select2Obj) return;

                select2Obj.unbind('select2:select').on('select2:select', function (evt) {
                    self.drawDislog(evt.params.data, true);
                });
                select2Obj.unbind('select2:unselect').on('select2:unselect', function (evt) {
                    $('#searchContent').empty();
                    $('#shade').hide();
                });
            });
            //隐藏搜索框操作
            $('#searchCloseBtn').unbind('click').click(function () {
                $(self.searchBtn).click();
            });
        },
        requestData: function () {
            var self = this, mapsObj = maps.fullMapObj;
            if (!mapsObj) return;
            var allPoints = mapsObj.getAllPoints(), teamArray = maps.fullMapObj.options.teamArray;
            if ($.isEmptyObject(allPoints)) return;

            var pointsData = [];
            $.each(allPoints, function (idx, item) {
                item.id = item.__id;
                var teamName = teamArray[item._t];
                var points = _.find(pointsData, function (obj) {
                    return obj.text == teamName;
                });
                if (_.isUndefined(points)) pointsData.push({text: teamName, children: [item]});
                else points.children.push(item);
            });
            // 下拉菜单
            $('#selectEmp').html('<option value="-1">请输入名称</option>');
            self.selectObj = $("#selectEmp").select2({
                language: 'zh-CN',
                data: pointsData,
                placeholder: {
                    id: '-1',
                    text: '请输入名称'
                },
                allowClear: true
            });
            self.isFirst = false;
        },
        drawDislog: function (point, bool) {
            var self = this, pointId = point.id, $searchContent = $('#searchContent');
            if (!bool) {
                $('#shade').hide();
                $('#' + pointId).removeAttr('style');
                return;
            }
            $('#shade').show();
            //假如之前有搜索过,隐藏之前的
            if (self.point) $('#' + self.point.id).removeAttr('style');

            $('#' + pointId).css({
                'zIndex': 200,
                'backgroundColor': '#fff'
            });

            $searchContent.html(self.drawEmpDislog(point));
            $searchContent.find('button.btn').click(function () {
                var empId = $(this).data('toggle');
                $(this).inventoryReport({
                    empId: empId
                });
            });
            self.point = point;
        },
        drawEmpDislog: function (point) {
            var html = $('<div class="search-emp-main"></div>');

            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.text + '</span>');
            html.append('<span class="col-xs-6 ct-col1 right"><button class="btn btn-primary" data-toggle="' + point.empId + '">盘点报告</button></span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.xLabel + '</span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.yLabel + '</span>');

            return html;
        },
        exit: function () {
            var self = this, $searchContent = $('#searchContent');
            if (self.show) $(self.searchBtn).click();
            $searchContent.empty();
            self.isFirst = true;
        },
        resize: function () {
            var self = this;
            var $searchBlock = $(self.searchBlock);
            $searchBlock.height(0);
            var h = $searchBlock.parents('.row').height();
            var winh = document.body.clientHeight - 41;
            $searchBlock.height(h > winh ? h : winh);
        }
    }


    $('#toHomeBtn').click(function () {
        win.setlocationUrl(urls.toMapsHomeUrl);
    });

    $('#fullScreen').click(function () {
        win.doFullScreen(function (e) {
            $('#fullMapBody').removeClass('hide');
            $('#talentMapBody').addClass('hide');
            maps.fullInit();
            searchObj.isFirst = true;
            searchObj.init();
        });
    });

    $('#fullCloseBtn').click(function () {
        win.doRestoreWindow(function (e) {
            $('#fullMapBody').addClass('hide');
            $('#talentMapBody').removeClass('hide');
            searchObj.exit();
            maps.initMapsMain();
        });
    });

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
        maps.resizeMap();
        searchObj.resize();
    });
});