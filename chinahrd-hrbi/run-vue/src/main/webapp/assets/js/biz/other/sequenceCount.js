require(['jquery', 'bootstrap', 'underscore', 'organTreeSelector', 'utils', 'messenger', 'selectItem', 'resize'], function ($) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getSequenceUrl: webRoot + '/sequenceCount/getSequence.do',              //获取主序列和子序列信息
        getSequenceCountUrl: webRoot + '/sequenceCount/getSequenceCount.do'     //获取职位序列统计信息
    };

    $(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;

    var clickTree = true;

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        clickTree = true;
        navObj.assignOrgan(organId, organTxt);
    }


    window.countObj = {
        elemId: null,
        subWidth: 200,
        selectOrganId: null,
        selectSequenceId: null,
        hasJobTitle: null,
        init: function (hasChild, sequenceData, subSeqData) {
            var self = this;
            self.hasChild = hasChild;
            self.sequenceData = sequenceData;
            self.subSeqData = subSeqData;
        },
        initSequence: function (organId, sequenceId, subSequenceIds) {
            var self = this;
            var selectSeqId = sequenceId || self.firstSeqId;
            var sourceData = self.sequenceData;
            if (!sourceData) {
                return;
            }
            if (self.hasChild) {
                var html = '';
                $.each(sourceData, function (i, obj) {
                    var classStyle = 'fb';
                    if (selectSeqId == obj.id) {
                        classStyle = 'fm';
                    }
                    html += '<li class="' + classStyle + '" sequenceId="' + obj.id + '">' + obj.text + '</li>';
                });
                var _mainSequence = $('#mainSequence');
                _mainSequence.html(html);

                _mainSequence.hover(function () {
                    var _this = $(this).find(".fm");
                    _this.prev().addClass('fl');
                    _this.next().addClass('fr');
                }, function () {
                    var _this = $(this).find(".fm");
                    _this.prev().removeClass('fl');
                    _this.next().removeClass('fr');
                });
                _mainSequence.children().unbind('click').bind('click', function () {
                    var _this = $(this);
                    var sequenceId = _this.attr('sequenceId');
                    _this.siblings().removeClass('fm fl fr').addClass('fb');
                    if (_this.hasClass('fr')) {
                        _this.next().removeClass('fm fl').addClass('fr');
                        _this.prev().removeClass('fm fr').addClass('fl');
                    }
                    if (_this.hasClass('fl')) {
                        _this.prev().removeClass('fm fr').addClass('fl');
                        _this.next().removeClass('fm fl').addClass('fr');
                    }
                    _this.removeClass('fr fl fb').addClass('fm');
                    self.requestSeqData(self.selectOrganId, sequenceId);
                    navObj.setNavSequence(sequenceId, _this.text());
                });
                //if (clickTree) {
                //    clickTree = !clickTree;
                //    _mainSequence.children(":first").click();
                //}
            }
            self.requestSeqData(organId, selectSeqId, subSequenceIds);
        },
        requestSeqData: function (organId, sequenceId, subSequenceIds) {
            var self = this;
            var requestOrganId = organId || self.selectOrganId;
            var requestSequenceId = sequenceId || self.firstSeqId;
            var requestSubSeqIds = subSequenceIds || [];
            var requestSubSeqStr = _.isObject(requestSubSeqIds) ? requestSubSeqIds.join(',') : requestSubSeqIds;
            var hasChild = self.hasChild;
            if (self.selectOrganId != requestOrganId) {
                self.selectOrganId = requestOrganId;
            }
            console.log(requestOrganId);
            var params = {
                organId: requestOrganId,
                hasSub: hasChild,
                sequenceId: requestSequenceId,
                subSequenceStr: requestSubSeqStr,
                hasJobTitle: self.hasJobTitle
            };
            $.post(urls.getSequenceCountUrl, params, function (rs) {
                if (_.isNull(rs.legends)) {
                    self.hideSequenceCount(true);
                    return;
                }
                self.hideSequenceCount(false);
                self.selectSequenceId = requestSequenceId;
                self.extendRsData(rs, hasChild);
                self.setLeftHTML('countLeft');
                self.setRightHTML('boxes');
                self.initSequenceHTML('count');
            });
        },
        hideSequenceCount: function (bool) {
            var _boxes = $('#boxes');
            if (bool) {
                _boxes.html('<div style="width: 100%;line-height: 50px;margin: 10px auto;text-align: center;">暂无序列统计数据</div>');
                _boxes.parent('.main').next().children(':first').addClass('hide');
                $('#arrowBtn').addClass('hide');
                $('#countLeft').html('');
                $('#count').html('');
            } else {
                _boxes.parent('.main').next().children(':first').removeClass('hide');
                $('#arrowBtn').removeClass('hide');
            }
        },
        /**
         * 重新封装返回的数据
         * @param rsSource
         * @param selectSeqId
         */
        extendRsData: function (rsSource, hasChild) {
            var self = this;
            self.legends = rsSource.legends;
            self.hasJobTitle = rsSource.hasJobTitle;
            var sequeceData = hasChild ? self.subSeqData[self.selectSequenceId] : self.sequenceData;
            var subItems = rsSource.subItems;
            $.each(subItems, function (i, obj) {
                var sequenceObj = _.find(sequeceData, function (seq) {
                    return obj.itemId == seq.id;
                });
                var bool = !_.isUndefined(sequenceObj);
                obj.itemName = bool ? sequenceObj.text : '';
                obj.prefix = bool ? sequenceObj.prefix : '';
            });
            self.subItems = subItems;
        },
        setLeftHTML: function (elemId) {
            var self = this;
            var _left = $('#' + elemId);
            if (_left.length > 0) {
                var legends = self.legends;
                var len = legends.length;
                var html = '<ul>';
                if (!_.isEmpty(legends)) {
                    for (var i = len - 1; i >= 0; i--) {
                        var name = legends[i].itemName;
                        if (!_.isNull(name)) {
                            if (name.length > 7)
                                name = name.substring(0, 7);
                        } else {
                            name = '';
                        }
                        html += '<li><p>' + name + '</p></li>';
                    }
                } else {
                    html += '<li></li>';
                }
                html += '</ul>';
                _left.html(html);
            }
        },
        setRightHTML: function (elemId) {
            var self = this;
            var subHtml = self.getCountData(self.subItems);
            var _right = $("#" + elemId);
            if (_right.length > 0) {
                _right.css('margin-left', ''); // 恢复原位
                _right.html(subHtml);
            }

            $('.list .cate').each(function () {
                $(this).removeClass('bgbbd7ea');
            });

            // count层相关数据初始化
            this.li = $(".list");
            this.licur = $(".list");
            this.ul = $("#boxes");
            this.len = $(".list").length;

            this.gor = $('.next');
            this.gol = $('.pre');
            this.format();
            this.bindact();
            this.door = true;
            this.curidx = 1;
        },
        getCountData: function (objs) {
            var self = this;
            var html = '';
            if (!_.isEmpty(objs)) {
                for (var i = 0; i < objs.length; i++) {
                    var subRsObj = objs[i];
                    var subPrefix = _.isNull(subRsObj.prefix) ? '' : subRsObj.prefix;
                    html += '<div class="list">';
                    html += self.setLiData(subPrefix, subRsObj.subItems, i);
                    html += '<p class="cate" id="' + subRsObj.itemId + '" onclick="countObj.initSubHTML(\'' + subRsObj.itemId + '\')">'
                        + subRsObj.itemName + '</p>';
                    html += '</div>';
                }
            }
            return html;
        },
        setLiData: function (subPrefix, objs, key) {
            var self = this;
            var html = '<ul>';
            var legends = this.legends;
            for (var i = legends.length - 1; i >= 0; i--) {
                var bool = false;
                for (var j = 0; j < objs.length; j++) {
                    var itemId = objs[j].itemId;
                    if (legends[i].itemId == itemId) {
                        bool = true;
                        html += '<li class="li' + i + '" onclick="countObj.initJobHTML('
                            + key + ',\'' + itemId + '\')"><p>'
                            + self.setJobStage(subPrefix, objs[j].prefix, objs[j].subItems) + '</p>';

                        if (self.hasJobTitle) {
                            html += '<p>职衔：' + objs[j].subItems.length + '</p>';
                        }
                        html += '</li>';
                    }
                }
                if (!bool) html += '<li></li>';
            }
            html += '</ul>';
            return html;
        },
        setJobStage: function (subPrefix, abilityPrefix, objs) {
            var self = this;
            var selectSeqId = self.selectSequenceId;
            var mainObj = _.find(self.sequenceData, function (seqObj) {
                return seqObj.id == selectSeqId;
            });
            var min = 1;
            var max = 1;

            var abilityLvPrefix = (self.hasChild ? mainObj.prefix : '') + subPrefix + abilityPrefix + '.';
            if (self.hasJobTitle) {   //是否有职衔
                for (var i = 0; i < objs.length; i++) { // 职衔
                    var abilityLvs = objs[i].subItems; // 职级
                    for (var j = 0; j < abilityLvs.length; j++) {
                        var prefix = parseInt(abilityLvs[j].prefix);
                        abilityLvs[j].itemName = abilityLvPrefix + prefix;
                        if (min > prefix) {
                            min = prefix;
                        }
                        if (max < prefix) {
                            max = prefix;
                        }
                    }
                }
            } else {
                for (var i = 0; i < objs.length; i++) {     //职级
                    var prefix = parseInt(objs[i].prefix);
                    objs[i].itemName = abilityLvPrefix + prefix;
                    if (min > prefix) {
                        min = prefix;
                    }
                    if (max < prefix) {
                        max = prefix;
                    }
                }
            }

            var html = '';
            if (min == max) {
                html = abilityLvPrefix + min;
            } else {
                html = (abilityLvPrefix + min) + '~' + (abilityLvPrefix + max);
            }
            return html;
        },
        // 获得同级最高值
        getMaxSubCount: function (objs) {
            var self = this;
            var max = 0;
            for (var i = 0; i < objs.length; i++) {
                if (!_.isEmpty(objs[i].subItems)) {
                    var resultNum = self.getSubDataCount(objs[i].subItems);
                    if (resultNum > max) max = resultNum;
                }
            }
            return max;
        },
        // 获得子级所有值统计
        getSubDataCount: function (objs) {
            var self = this;
            var count = 0;
            if (!_.isEmpty(objs)) {
                for (var i = 0; i < objs.length; i++) {
                    var data = objs[i].subItems;
                    if (!_.isEmpty(data)) {
                        count += self.getSubDataCount(data);
                    } else {
                        count += parseInt(objs[i].empCount);
                    }
                }
            } else {
                if (!_.isNull(objs)) {
                    count += parseInt(objs.empCount);
                }
            }
            return count;
        },
        // 初始化序列页面
        initSequenceHTML: function (elemId) {
            var self = this;
            var elem = $('#' + elemId);
            if (elem.length == 0) {
                return;
            }
            self.elemId = elemId;
            var title = '';
            var total = self.getSubDataCount(self.subItems);
            var size = self.subItems.length;
            elem.html([
                '<div class="contrast_div">',
                '<span style="margin-right: 20px;font-size: 16px;">总人数：',
                Tc.formatNumber(total),
                '</span><span style="font-size: 16px;">' + (self.hasChild ? '子' : '') + '序列：',
                size,
                '个</span>',
                '<div class="note_div">注：&nbsp;<span>上下移子序列可调整坐标轴顺序，方便比较</span><br>',
                title, '</div>', self.setSequenceHtml()].join(""));
        },
        // 获得序列详细文本
        setSequenceHtml: function () {
            var self = this;
            var objs = self.subItems;
            if (_.isEmpty(objs)) return '';
            var html = '<div class="sub_content">';
            var subMaxCount = self.getMaxSubCount(objs);
            self.maxData = subMaxCount;
            for (var i = 0; i < objs.length; i++) {
                var sub_name = objs[i].itemName;
                var sub_data = !_.isEmpty(objs[i].subItems) ? self.getSubDataCount(objs[i].subItems) : 0;

                html += '<ul class="sequence_ul" onMouseOver="countObj.mouseOverSequence(this)" onMouseOut="countObj.mouseOutSequence(this)">';
                html += '<li class="sub_title">' + (sub_name == '' ? '&nbsp;' : sub_name) + '</li>';
                var bgColor = '';
                html += self.setSubPositions(subMaxCount, sub_data);
                if (!_.isEmpty(objs[i].subItems)) {
                    html += '<li><a href="javascript:void(0)" class="count_link" onclick="countObj.initSubHTML(\''
                        + objs[i].itemId + '\')" >明细&nbsp;&gt;&gt;</a></li><li class="float_div">';

                    if (i != objs.length - 1) {
                        html += '<span class="next_icon" onclick="countObj.clickDollyNext(this,' + i + ')"></span>';
                    }
                    if (i != 0) {
                        html += '<span class="prev_icon" onclick="countObj.clickDollyPrev(this,' + i + ')"></span>';
                    }
                    html += '</li>';
                }
                html += '</ul>';
            }
            html += '</div>';
            return html;
        },

        /***************************************************************************
         * 序列（子序列）展示
         *
         * @param max 同级人数最多
         * @param curr 当前拥有的人数
         */
        setSubPositions: function (max, curr) {
            var self = this;
            var html = '<li class="sub_context">'; // 定义中间层
            var w = Math.round(curr / max * 200); // 计算占有比例
            html += '<span class="curr_people" style="width:' + w + 'px;"></span><label>' + Tc.formatNumber(curr) + '人</label>';
            html += '</li>';
            return html;
        },
        // 鼠标移动到层上面触发的事件
        mouseOverSequence: function (obj) {
            $(obj).children(':last').css('display', 'inline-block');
        },
        // 鼠标移动到层上面触发的事件
        mouseOutSequence: function (obj) {
            $(obj).children(':last').css('display', 'none');
        },
        // 点击上移按钮事件
        clickDollyNext: function (obj, index) {
            var _this = $(obj).parents('.sequence_ul');
            _this.next().after(_this);
            var li_this = $($(".list")[index]);
            li_this.next().after(li_this);
            var chlidren = _this.parent().children();
            $.each(chlidren, function (i, obj) {
                var html = '';
                if (i != Number(chlidren.size() - 1)) {
                    html += '<span class="next_icon" onclick="countObj.clickDollyNext(this,'
                        + i + ')"></span>';
                }
                if (i != 0) {
                    html += '<span class="prev_icon" onclick="countObj.clickDollyPrev(this,'
                        + i + ')"></span>';
                }

                $(obj).children('.float_div').css('display',
                    'none').html(html);
            });
        },
        // 点击下移按钮事件
        clickDollyPrev: function (obj, index) {
            var _this = $(obj).parents('.sequence_ul');
            _this.prev().before(_this);
            var li_this = $($(".list")[index]);
            li_this.prev().before(li_this);
            var chlidren = _this.parent().children();
            $.each(chlidren, function (i, obj) {
                var html = '';
                if (i != Number(chlidren.size() - 1)) {
                    html += '<span class="next_icon" onclick="countObj.clickDollyNext(this,'
                        + i + ')"></span>';
                }
                if (i != 0) {
                    html += '<span class="prev_icon" onclick="countObj.clickDollyPrev(this,'
                        + i + ')"></span>';
                }

                $(obj).children('.float_div').css('display', 'none').html(html);
            });
        },
        // 初始化子序列HTML
        initSubHTML: function (keyId) {
            var self = this;
            var _element = $('#' + self.elemId);
            var subObj = _.find(self.subItems, function (itemObj) {
                return itemObj.itemId == keyId;
            });
            if (!_.isUndefined(subObj) && _element.length > 0) {
                self.subName = subObj.itemName;
                var subTotal = self.subTotal = self.getSubDataCount(subObj.subItems);
                //self.subSequenceHtml = self.setSubHTML(subObj);
                var arrayHtml = [
                    '<div class="contrast_div">',
                    '<span class="col-xs-5"><label>总人数：', Tc.formatNumber(subTotal), '</label></span>',
                    '<span class="col-xs-7">',
                    '<label class="pull-right inline"><small class="muted">全部展开：</small>',
                    '<input', (self.hasJobTitle ? ' ' : ' checked="" '),
                    'onclick="countObj.checkedDisplayLevel(this)" type="checkbox" class="ace ace-switch ace-switch-5">',
                    '<span class="lbl"></span></label></span>',
                    self.setSubHTML(subObj), '</div>'].join("");

                _element.html(arrayHtml);
                $('.list .cate').each(function () {
                    $(this).removeClass('bgbbd7ea');
                });
                $('#' + subObj.itemId).addClass('bgbbd7ea');
                if (navObj) {
                    if (self.hasChild) {
                        navObj.setNavSubSeq(subObj.itemId, subObj.itemName);
                    } else {
                        navObj.setNavSequence(subObj.itemId, subObj.itemName);
                    }
                }
            } else {
                _element.html('');
            }


        },
        // 初始化职级页面
        initJobHTML: function (parent, targetId) {
            var self = this;
            var subObj = self.subItems[parent];
            var abilityObjs = subObj.subItems;
            var count = 0;
            if (!_.isEmpty(abilityObjs)) {
                var html = '';
                for (var i = 0; i < abilityObjs.length; i++) {
                    var abilityObj = abilityObjs[i];
                    if (targetId == abilityObj.itemId) {
                        var nameStr = abilityObj.itemName;
                        count = self.getSubDataCount(abilityObj.subItems);
                        html += '<div class="sub_content">';
                        html += '<ul class="level_0"><li class="static_icon">';
                        html += '<li class="level_0_text"><span>' + nameStr
                            + '</span></li>';
                        html += self.setAbilityPositions(count, count);
                        html += '</ul>';
                        html += self.setJobTitleHTML(abilityObj.subItems, self.hasJobTitle ? 0 : 1, count);
                        html += '</div>';
                    }
                }
                var arrayHtml = [
                    '<div class="contrast_div">',
                    '<span class="col-xs-5"><label>总人数：', Tc.formatNumber(count), '</label></span>',
                    '<span class="col-xs-7">',
                    '<label class="pull-right inline"><small class="muted">全部展开：</small>',
                    '<input', (self.hasJobTitle ? ' ' : ' checked="" '),
                    'onclick="countObj.checkedDisplayLevel(this)" type="checkbox" class="ace ace-switch ace-switch-5" >',
                    '<span class="lbl"></span></label></span>',
                    html, '</div>'].join("");

                var _element = $('#' + self.elemId);
                if (_element.length > 0) {
                    _element.html(arrayHtml);
                    $('.list .cate').each(function () {
                        $(this).removeClass('bgbbd7ea');
                    });
                    if (navObj) {
                        if (self.hasChild) {
                            navObj.setNavSubSeq(subObj.itemId, subObj.itemName);
                        } else {
                            navObj.setNavSequence(subObj.itemId, subObj.itemName);
                        }
                    }
                }
            }
        },
        /***************************************************************************
         * 能力层级展示
         *
         * @param max 同级人数最多
         * @param curr 当前拥有的人数
         */
        setAbilityPositions: function (curr, max) {
            var self = this;
            var html = '';
            var w = Math.round((curr / max) * self.subWidth);
            if (max == 0) {
                w = 0;
            }
            html += '<li class="ml45"><span class="level_0_dis" style="width:' + w + 'px;"></span><label>'
                + Tc.formatNumber(curr) + (curr == 0 ? '' : '人') + '</label></li>';
            return html;
        },
        // 获得子序列详细文本
        setSubHTML: function (obj) {
            var self = this;
            var html = '';
            var objs = obj.subItems;
            var maxData = self.getMaxSubCount(objs);
            for (var i = objs.length - 1; i >= 0; i--) {
                var nameStr = objs[i].itemName;
                var currCount = self.getSubDataCount(objs[i].subItems);
                html += '<div class="sub_content col-xs-12">';
                html += '<ul class="level_0"><li class="static_icon"></li>';
                html += '<li class="level_0_text"><span>' + nameStr + '</span></li>';
                html += self.setAbilityPositions(currCount, maxData);
                html += '</ul>';
                html += self.setJobTitleHTML(objs[i].subItems, self.hasJobTitle ? 0 : 1, maxData);
                html += '</div>';
            }
            return html;
        },
        setJobTitleHTML: function (objs, level, max_data) {
            var self = this;
            var html = '';
            if (level == 0) {
                for (var i = objs.length - 1; i >= 0; i--) {
                    var obj = objs[i];
                    var currCount = self.getSubDataCount(obj.subItems);
                    html += '<ul class="level_1" onclick="countObj.clickDisplayLevel(this)">';
                    var name = obj.itemName;
                    if (!_.isNull(name) && name.length > 7) { // 假如职衔太长截取
                        name = name.substring(0, 7) + '...';
                        html += '<li class="close_icon"></li><li class="level_1_text" title="'
                            + obj.itemName + '">' + name;
                    } else {
                        html += '<li class="close_icon"></li><li class="level_1_text">'
                            + name;
                    }
                    var w = Math.round((currCount / max_data) * self.subWidth);
                    if (max_data == 0) {
                        w = 0;
                    }
                    html += '</li><li><span class="level_1_dis" style="width:' + w
                        + 'px"></span><label>' + Tc.formatNumber(currCount) + (currCount > 0 ? '人' : '') + '</label></li></ul>';

                    if (!_.isEmpty(obj.subItems)) {
                        html += self.setJobTitleHTML(obj.subItems, 1, max_data);
                    }
                }
            } else {
                var hasJobTitle = self.hasJobTitle;
                var levelStyle = hasJobTitle ? '' : ' style="display:block;" ';
                var levelTitleStyle = hasJobTitle ? '' : 'style="text-align:left;padding-left:30px;"'
                html += '<div class="level_2" ' + levelStyle + '>';
                for (var i = objs.length - 1; i >= 0; i--) {
                    var obj = objs[i];
                    //var currCount = self.getSubDataCount(obj);
                    var currCount = obj.empCount;
                    html += '<ul class="level_2_ul">';
                    html += '<li class="level_2_title" ' + levelTitleStyle + '>' + obj.itemName;
                    var w = Math.round((currCount / max_data) * self.subWidth);
                    if (max_data == 0) {
                        w = 0;
                    }
                    html += '</li><li><span class="level_2_dis" style="width:' + w
                        + 'px;"></span><label>' + Tc.formatNumber(currCount) + (currCount > 0 ? '人' : '') + '</label></li></ul>';
                }
                html += '</div>';
            }

            return html;
        },
        // 点击职位显示职级
        clickDisplayLevel: function (obj) {
            var _this = $(obj);
            var sub_obj = $(obj).children();
            var first_obj = sub_obj.first();
            var level_obj = _this.next();
            if (first_obj.hasClass('close_icon')) {
                first_obj.removeClass('close_icon').addClass('open_icon');
                if (level_obj.hasClass('level_2')) {
                    level_obj.css('display', 'block');
                }
            } else {
                first_obj.removeClass('open_icon').addClass('close_icon');
                if (level_obj.hasClass('level_2')) {
                    level_obj.css('display', 'none');
                }
            }
        },
        checkedDisplayLevel: function (obj) {
            var _obj = $(obj);
            var _parent = _obj.parents('.contrast_div');
            var level_obj = _parent.children().find('.level_2');
            if (_obj.prop('checked')) {
                $.each(level_obj, function (i, obj) {
                    var _this = $(obj);
                    _this.prev().children('li:first').removeClass('close_icon').addClass('open_icon');
                    _this.css('display', 'block');
                });
            } else {
                $.each(level_obj, function (i, obj) {
                    var _this = $(obj);
                    _this.prev().children('li:first').removeClass('open_icon').addClass('close_icon');
                    _this.css('display', 'none');
                });
            }
        },
        format: function () {
            var self = this;
            var len = self.len;
            var w = (len) * 110 + 94;
            self.ul.width(w);
            if (len > 5) {
                $('#titleNav').removeClass('hide');
            } else {
                $('#titleNav').addClass('hide');
            }
        },
        step: function (i) {
            var self = this;
            if (!self.door) return false;

            self.door = false;
            self.curidx += i;
            var m = 110 * i;

            if (i < 0) {
                if (self.curidx <= -1) {
                    self.curidx += self.li.length;
                }
                i = -i;
                for (var k = 0; k < i; k++) {
                    self.ul.find("div:last").prependTo(self.ul);
                }
                self.ul.css({
                    "margin-left": m + "px"
                });
                self.ul.animate({
                    "margin-left": "0"
                }, {
                    duration: 500,
                    complete: function () {
                        self.door = true;
                    }
                });
            }
            else if (i > 0) {
                if (self.curidx >= self.li.length) {
                    self.curidx -= self.li.length;
                }
                self.ul.animate({
                    "margin-left": -m + "px"
                }, {
                    duration: 500,
                    complete: function () {
                        for (var k = 0; k < i; k++) {
                            self.ul.find("div:first").appendTo(self.ul);
                        }
                        self.ul.css({
                            "margin-left": 0
                        });
                        self.door = true;
                    }
                });
            }
        },
        bindact: function () {
            var self = this;
            this.gor.click(function () {
                self.step(-1);
                $(this).blur();

            });
            this.gol.click(function () {
                self.step(1);
                $(this).blur();
            });

        }
    };

    var navObj = {
        init: function (organId, organText) {
            var self = this;
            $.getJSON(urls.getSequenceUrl, function (data) {
                if (_.isEmpty(data)) {
                    return;
                }
                self.extendsData(data);
                var sequenceData = self.sequenceData;
                //默认所选节点
                var firstSeqObj = sequenceData[sequenceData.length - 1];
                var firstSeqId = firstSeqObj.id;
                self.createSequence(firstSeqId);
                self.createNavSequence(firstSeqObj);
                countObj.firstSeqId = firstSeqId;
                countObj.init(self.hasChild, sequenceData, self.subSeqData);
                self.assignOrgan(organId, organText);
            });
            //定义导航框里取消按钮事件
            $('#removeSearchBtn').click(function () {
                $('#collapseSearch').collapse('hide');
            });
            //定义导航框里确定按钮事件
            $('#submitSearchBtn').click(function () {
                var mainSelectors = self.sequenceSelector.getSelectedItems();
                var subseqItems = [];
                if (self.subSelectors) {
                    subseqItems = self.subSelectors.getSelectedItems();
                }
                if (!_.isEmpty(mainSelectors)) {
                    if (self.hasChild) {
                        var selectTxt = self.sequenceSelector.getSelectedTexts();
                        self.setNavSequence(mainSelectors[0], selectTxt);
                        countObj.initSequence(null, mainSelectors[0], subseqItems);
                    } else {
                        countObj.initSubHTML(mainSelectors[0]);
                    }

                    $('#collapseSearch').collapse('hide');
                }
            });
        },
        createSequence: function (mdata) {
            var self = this;
            var sequenceData = self.sequenceData;
            if (!sequenceData) {
                return;
            }
            if (!self.sequenceSelector) {
                var options = {
                    selectType: false,
                    disableAll: true,
                    selectCallback: function (id, isChecked, text) {
                        var mainSelectors = self.sequenceSelector.getSelectedItems();

                        self.createSubSeq(mainSelectors[0], null);
                    }
                };
                self.sequenceSelector = new window.W.SelectItem("sequenceData", options);
            }
            self.sequenceSelector.setDataItems(sequenceData);
            if (mdata && self.hasChild) {
                self.sequenceSelector.setSelectItems(mdata);
            }
        },
        createSubSeq: function (id, sdata) {
            var self = this;
            var subData = self.subSeqData[id];
            if (!subData) {
                if (self.subSelectors) {
                    self.subSelectors.clearSelected();
                }
                return;
            }
            self.hideSubSeq(false);
            if (!self.subSelectors) {
                var subOptions = {
                    selectType: true,
                    selectCallback: function (id, isChecked, text) {
                        var subSeqItem = self.subSelectors.getSelectedItems();
                    }
                };
                self.subSelectors = new window.W.SelectItem("subSequenceData", subOptions);
            }
            self.subSelectors.setDataItems(subData);
            if (sdata) {
                self.subSelectors.setSelectItems(sdata);
            }
        },
        createNavSequence: function (firstObj) {
            var self = this;
            var sequenceData = self.sequenceData;
            if (!sequenceData) {
                return;
            }
            self.hideNavSequence(!self.hasChild);
            var _child = $('#sequenceVal').children();
            _child[0].innerText = firstObj.text;

            var html = '';
            $.each(sequenceData, function (i, obj) {
                html += '<li sequenceId="' + obj.id + '"><a href="javascript:void(0)">' + obj.text + '</a></li>';
            });
            _child[1].innerHTML = html;
            $(_child[1]).find('a').click(function () {
                var _this = $(this);
                var sequenceId = _this.parent().attr('sequenceId');
                if (self.hasChild) {
                    countObj.initSequence(null, sequenceId);
                    self.hideNavSub(true);
                    self.setNavSequence(sequenceId, _this.text());
                } else {
                    countObj.initSubHTML(sequenceId);
                }
            });
        },
        createNavSubSeq: function (id, isOpen) {
            var self = this;
            var subSequenceDatas = self.subSeqData;
            var subData = subSequenceDatas[id];
            if (!subData) {
                self.hideNavSub(true);
                return;
            }
            self.hideNavSub(!isOpen);
            var _child = $('#subSequenceVal').children();
            _child[0].innerText = subData[0].text;
            var html = '';
            $.each(subData, function (i, obj) {
                html += '<li subSequenceId="' + obj.id + '"><a href="javascript:void(0)">' + obj.text + '</a></li>';
            });
            _child[1].innerHTML = html;
            $(_child[1]).find('a').click(function () {
                var _this = $(this);
                var subSequenceId = _this.parent().attr('subSequenceId');
                $(_child[0]).text(_this.text());
                countObj.initSequence(null, id, subSequenceId);
            });
        },
        setNavSequence: function (seqId, seqName) {
            var self = this;
            $('#sequenceNav').removeClass('hide');
            var _child = $('#sequenceVal').children();
            $(_child[0]).text(seqName);
            if (self.sequenceSelector) {
                self.sequenceSelector.setSelectItem(seqId);
            }
            self.createNavSubSeq(seqId, false);
        },
        setNavSubSeq: function (seqId, seqName) {
            $('#subSequenceNav').removeClass('hide');
            var _child = $('#subSequenceVal').children();
            $(_child[0]).text(seqName);
        },
        hideNavSequence: function (bool) {
            if (bool) {
                $('#sequenceNav').addClass('hide');
            } else {
                $('#sequenceNav').removeClass('hide');
            }
        },
        hideNavSub: function (bool) {
            if (bool) {
                $('#subSequenceNav').addClass('hide');
            } else {
                $('#subSequenceNav').removeClass('hide');
            }
        },
        hideSubSeq: function (bool) {
            if (bool) {
                $('#subSequenceRow').addClass('hide');
            } else {
                $('#subSequenceRow').removeClass('hide');
            }
        },
        assignOrgan: function (organId, organTxt) {
            var self = this;
            $('#organVal').text(organTxt);
            countObj.initSequence(organId);
        },
        extendsData: function (source) {
            var self = this;
            var sequenceData = [], subSeqData = {};
            var hasChild = false;
            $.each(source, function (i, obj) {
                sequenceData.push({'id': obj.itemId, 'text': obj.itemName, 'prefix': obj.prefix});
                if (obj.subItems.length > 0) {
                    hasChild = true;
                    var subData = [];
                    $.each(obj.subItems, function (j, subObj) {
                        subData.push({'id': subObj.itemId, 'text': subObj.itemName, 'prefix': subObj.prefix});
                    });
                    subSeqData[obj.itemId] = subData;
                }
            });
            self.hasChild = hasChild;
            self.sequenceData = sequenceData;
            self.subSeqData = subSeqData;
        }
    };
    navObj.init(reqOrgId, reqOrgText);
    $.zrw_resizeFrameSize();
});