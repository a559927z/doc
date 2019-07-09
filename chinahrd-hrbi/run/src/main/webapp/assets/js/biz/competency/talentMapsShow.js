require(['jquery', 'echarts3', 'echarts/chart/line', 'echarts/chart/scatter', 'bootstrap', 'jgGrid', 'underscore', 'select2', 'mapGrid', 'keyTalentsAdd',
    'utils', 'messenger', 'talent-map2', 'organTreeSelector', 'searchBox3', 'selection', 'inventoryReport', 'jBootstrapPage', 'cardTabel'], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        toMapsHomeUrl: webRoot + '/talentMaps/toTalentMapsView',          //跳转到人才地图首页
        searchBoxUrl: webRoot + '/common/getTalentMapsSearchBox.do',	//筛选条件信息
        getMapsBaseInfoUrl: webRoot + '/talentMaps/getMapsBaseInfo.do',             //获取地图轴线数据
        getMapsSimpleEmpCountUrl: webRoot + '/talentMaps/getMapsSimpleEmpCount.do',            //获取建议相关数据展示
        getTalentMapsEmpPointUrl: webRoot + '/talentMaps/getTalentMapsEmpPoint.do',                  //获取地图员工点信息
        getTalentMapsEmpPointByStrUrl: webRoot + '/talentMaps/getTalentMapsEmpPointByStr.do',                  //获取地图员工点信息
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

    // 搜索组件默认值
    var defaultBoxData = {
        sequenceId: null,
        sequenceSubId: null,
        abilityId: null,
        performanceKey: null,
        ageId: null,
        sexId: null,
        eduId: null
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


    /**
     * 搜索组件
     */
    var searchBoxObj = {
        searchBoxId: '#searchBox',
        init: function () {
            var self = this;

            var searchBoxConfig = {
                url: urls.searchBoxUrl,
                lazy: true,
                attach: [],
                // 重写组件里的onClick事件
                onClick: function (obj) {
                    defaultBoxData = this.getSelectData();

                    //初始化地图数据
                    if (maps) maps.renderMapsMain();
                },
                close: function (w, h) {
                },
                expand: function (w, h) {
                },
                loadComple: function (o) {
                    var $component = $(o.$component), $sequenSubGroup = $component.find('div[name="sequenceSubId"]').parents('.form-group');
                    $sequenSubGroup.addClass('hide');
                    var $sequenGroup = $component.find('div[name="sequenceId"]').parents('.form-group');
                    //主序列选项触发事件
                    $sequenGroup.find('.childrenBtn').children().click(function () {
                        var $this = $(this), bool = $this.hasClass('condition-click'),
                            $condition = $this.siblings('.condition-click'), $sequenSub = $sequenSubGroup.find('.childrenBtn').children();

                        //假如当前为选中,同级也有选中；或者当前为未选中，同级不止一个为选中 则隐藏子序列
                        if ((bool && $condition.length != 0)
                            || (!bool && $condition.length != 1)) {

                            $sequenSubGroup.addClass('hide');
                            $sequenSub.removeClass('condition-click');
                            return;
                        }
                        //当前选择则取当前序列的ID，否则取同级选中的ID 渲染子序列
                        var sequenceId = bool ? $this.attr('value') : $($condition[0]).attr('value');
                        $sequenSub.addClass('hide');
                        $sequenSubGroup.removeClass('hide');
                        $.each($sequenSub, function (idx, item) {
                            if (sequenceId == $(item).data('bindObj')) $(item).removeClass('hide');
                        });
                    });
                }
            };

            $(self.searchBoxId).searchBox3(searchBoxConfig);
        }
    }
    searchBoxObj.init();

    /**
     * 页面初始化对象
     */
    var pageLoadingObj = {
        selectObj: $('#mapsTypeSelected'),
        organBar: null,
        navSelectObj: null,
        init: function () {
            var self = this;
            self.initDateSelect();
            self.initOrganSelect();
            self.initMapsTypeSelect();
            self.initNavSearch();
        },
        initDateSelect: function () {
            var self = this, cycleDate = $('#cycleDate').val();
            self.cycleDate = cycleDate;
            var $dateSelect = $('#dateSelect');
            $dateSelect.val(cycleDate);
            self.setMapsDateTitle();
            $dateSelect.unbind('change').change(function () {
                var $this = $(this);
                var date = parseInt($this.val());
                self.cycleDate = date;
                self.setMapsDateTitle();

                if (maps) maps.renderMapsMain();
            });
        },
        initOrganSelect: function () {
            var self = this, organId = $('#reqOrganId').val(), organName = $('#reqOrganName').val();
            self.organId = organId;
            self.organBar = $("#organZtree").organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                mouseenterColor: '#fff',
                mouseleaveColor: '#fff',
                onSelect: function (ids, texts) {
                    if (!_.isEmpty(ids)) {
                        self.organId = ids;
                        self.setMapOrganTitle(texts);

                        if (maps) maps.renderMapsMain();
                    }
                }
            });
            self.organBar.organTreeSelector("value", {id: organId, text: organName});
            self.setMapOrganTitle(organName);
        },
        initNavSearch: function () {
            var self = this, $navSearchEmp = $('#navSearchEmp');
            var pointsData = {};
            // 下拉菜单
            self.navSelectObj = $navSearchEmp.select2({
                language: 'zh-CN',
                placeholder: "请输入名称",
                allowClear: true,
                minimumInputLength: 1,
                ajax: {
                    url: urls.getTalentMapsEmpPointByStrUrl,
                    dataType: "json",
                    delay: 250,
                    type: 'post',
                    data: function (params) {
                        var queryDto = pageLoadingObj.getParams();
                        queryDto.name = params.term;
                        return {
                            queryDtoStr: JSON.stringify(queryDto),
                            page: params.page || 1,
                            rows: 30
                        };
                    },
                    cache: true,
                    processResults: function (data) {
                        console.log(data);
                        var data = $.map(data.rows, function (p) {
                            p.id = p.empId;
                            return p;
                        });
                        pointsData = $.extend(true, pointsData, data);
                        return {
                            results: data,
                            pagination: {
                                more: (data.page * 30) < data.records
                            }
                        }
                    },
                },
                escapeMarkup: function (markup) {
                    return markup;
                }
            });
            self.navSelectObj.on('select2:select', function (evt) {
                var obj = evt.params.data;
                var point = _.find(pointsData, function (o) {
                    return o.empId == obj.id;
                });
                self.drawDislog(point);
            });
            self.navSelectObj.on('select2:unselect', function (evt) {
                $('#navSearchBlock').addClass('hide');
            });
        },
        clearNavSearch: function () {
            var self = this;
            self.navSelectObj.empty();
            $('#navSearchBlock').addClass('hide');
        },
        drawDislog: function (point) {
            var self = this, $navSearchBlock = $('#navSearchBlock');
            $navSearchBlock.removeClass('hide');
            $navSearchBlock.html(searchObj.drawEmpDislog(point));
            $navSearchBlock.find('button.btn').click(function () {
                var $this = $(this), pointId = $this.data('toggle');
                $navSearchBlock.addClass('hide');
                $this.inventoryReport({
                    empId: pointId
                });
            });
        },
        initMapsTypeSelect: function () {
            var self = this;
            self.selectObj.change(function (e) {
                var txt = $(this).val();
                if (txt == self.selected) return;
                self.selected = txt;
                maps.renderMapsMain();
                if (txt == 1) {
                    $('#grid').removeClass('hide');
                    $('#map,#mapAxisBlock,.help-inline').addClass('hide');
                } else {
                    $('#grid').addClass('hide');
                    $('#map,#mapAxisBlock,.help-inline').removeClass('hide');
                }
            });
        },
        setMapsDateTitle: function () {
            var self = this;
            var text = $('#dateSelect').find('option:selected').text();
            $('.mapsDateTitle').text(text);
        },
        setMapOrganTitle: function (text) {
            var self = this;
            $('.mapsOrganTitle').text(text);
        },
        getParams: function () {
            var self = this;
            var params = {
                organId: self.organId,
                yearMonths: self.cycleDate,
                sequenceStr: defaultBoxData.sequenceId,
                sequenceSubStr: defaultBoxData.sequenceSubId,
                abilityStr: defaultBoxData.abilityId,
                performanceStr: defaultBoxData.performanceKey,
                ageStr: defaultBoxData.ageId,
                sexStr: defaultBoxData.sexId,
                eduStr: defaultBoxData.eduId
            };
            return params;
        }
    }
    pageLoadingObj.init();


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
                '<i class="icon-circle u-point"></i>' +
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
        mapObj: null,
        fullMapObj: null,
        empTabel: '#emp_tabel',
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
            var self = this, w = $(self.mapId).parent().width() - 30;
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: true,
                isSimple: true,
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
                        var teamPoints = mapObj.getZAxisPoints();
                        var len = mapObj.getAllPointsNumber();
                        self.renderAxis(teamPoints, len);
                    },
                    onClick: function (elem, point) {
                        if (undefined === point.remainNum) {
                            $(this).inventoryReport({
                                empId: point.empId
                            });
                            return;
                        }
                        var param = pageLoadingObj.getParams();
                        param.yLaberId = point.yLabelId;
                        param.xLaberId = point.xLabelId;
                        self.talentMapsModal(param, point.yLabel, point.xLabel);
                    }
                },
                groupPoint: {
                    onClick: function (elem, points, groupInfo) {
                        if (_.isEmpty(points)) return;
                        var point = points[0];
                        var param = pageLoadingObj.getParams();
                        param.yLaberId = point.yLabelId;
                        param.xLaberId = point.xLabelId;
                        self.talentMapsModal(param, self.yAxisData[groupInfo.y - 1], self.xAxisData[groupInfo.x - 1]);
                    }
                }
            });
            self.attachEvent(self.mapObj);

            self.requestData();
        },
        talentMapsModal: function (param, yLabel, xLabel) {
            var self = this, $talentMapsModal = $('#talentMapsModal');
            $talentMapsModal.find('.modal-header-text').text(yLabel + '  ' + xLabel);
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
        attachEvent: function (mapObj) {
            var self = this;
            //显示/隐藏总数
            $("#toggleGroupMode").click(function () {
                mapObj.toggleGroupMode();
            });
            //显示/隐藏表格
            $("#toggleGrids").click(function () {
                mapObj.toggleGrids();
            });
        },
        renderMapsMain: function () {
            var self = this, bool = pageLoadingObj.selected;
            //先清除搜索框的选择
            pageLoadingObj.clearNavSearch();

            if (bool == 1) self.renderMapGrid();
            else self.requestData();
        },
        renderMapGrid: function () {
            var self = this, $grid = $('#grid'), params = pageLoadingObj.getParams();
            $grid.mapGrid({param: params});
        },
        renderAxis: function (data, len) {
            var self = this;
            var $hideBlock = $('#mapAxisBlock .map-axis-hide');
            var $showBlock = $('#mapAxisBlock .map-axis-show');
            var $showBlockRemove = $showBlock.find('.icon-remove');
            //渲染主体内容
            $showBlock.find('.map-axis-main').html(self.renderAxisHtml(data, len));
            if ($hideBlock.is(':hidden') && $showBlock.is(':hidden')) $hideBlock.show();
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
                var obj = data[i];
                var total = obj.pointsTotal;
                var w = Tc.formatFloat(total / len * 100, 0);
                html += '<div class="row map-axis-area">';
                html += '<div class="col-xs-3">' + obj.name + '</div>';
                html += '<div class="col-xs-6 ct-col1"><div class="progress" data-percent="' + w + '%"><div class="progress-bar" style="width:' + w + '%;background-color: ' + obj.color + ';"></div></div></div>';
                html += '<div class="col-xs-3 ct-colr">&nbsp;&nbsp;' + total + '人</div>';
                html += '</div>';
            }
            return html;
        },
        requestData: function () {
            var self = this, params = pageLoadingObj.getParams(), mapObj = self.mapObj;
            $.post(urls.getMapsSimpleEmpCountUrl, params, function (rs) {
                if (mapObj) {
                    mapObj.clear();
                    if (!_.isEmpty(rs)) mapObj.addSimplePoints(rs);
                }
            });
        },
        fullInit: function () {
            var self = this;
            var w = $(self.fullMapId).parent().width() - 30;
            self.fullMapObj = $(self.fullMapId).talentMap({
                showNum: true,
                width: w,
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
            self.requestFullData();
        },
        requestFullData: function () {
            var self = this, params = pageLoadingObj.getParams(), fullMapObj = self.fullMapObj;
            $.post(urls.getTalentMapsEmpPointUrl, params, function (rs) {
                if (fullMapObj) {
                    fullMapObj.clear();
                    if (!_.isEmpty(rs.rows)) fullMapObj.addPoints(rs.rows);
                }
            });
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
            var self = this, $selectEmp = $('#selectEmp'), mapsObj = maps.fullMapObj;
            if (!mapsObj) return;
            var allPoints = mapsObj.getAllPoints();
            if ($.isEmptyObject(allPoints)) return;

            var pointsData = $.map(allPoints, function (p) {
                p.id = p.__id;
                return p;
            });
            // 下拉菜单,先初始化
            $selectEmp.html('<option value="-1"></option>');
            self.selectObj = $selectEmp.select2({
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
                $(this).inventoryReport({
                    empId: point.empId
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
        var mapObj = maps.mapObj, number = mapObj.getAllPointsNumber();
        if (number <= 1000 && number > 0) {
            win.doFullScreen(function (e) {
                $('#fullMapBody').removeClass('hide');
                $('#talentMapBody').addClass('hide');
                maps.fullInit();
                searchObj.isFirst = true;
                searchObj.init();
            });
            return;
        }
        showErrMsg("当前人数影响全屏显示，请通过筛选条件过滤多余人员，最多支持1000人全屏!");
    });

    $('#fullCloseBtn').click(function () {
        win.doRestoreWindow(function (e) {
            $('#fullMapBody').addClass('hide');
            $('#talentMapBody').removeClass('hide');
            searchObj.exit();
        });
    });

    $(window).resize(function () {
        maps.resizeMap();
        searchObj.resize();
    });
});