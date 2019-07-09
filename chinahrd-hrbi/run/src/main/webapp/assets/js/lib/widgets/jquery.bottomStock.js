define(['jquery', "jquery-ui", 'bootstrap', 'messenger', 'unveil'], function ($) {
    var webUrl = G_WEB_ROOT;
    var defineds = {
        queryStockEmpUrl: webUrl + '/talentContrast/getEmpInfo.do',
        removeStockEmpUrl: webUrl + '/talentContrast/removeStoreContrastEmp.do',
        getStockEmpUrl: webUrl + '/talentContrast/getStoreContrastEmp.do',
        setStockEmpUrl: webUrl + '/talentContrast/setStoreContrastEmp',
        toContrastUrl: webUrl + '/talentContrast/toTalentContrastView.do',
        asyncUrl: webUrl + '/assets/img/talent/avatar_placeholder.png'
    };

    function showErrMsg(content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    }

    var removeFromArr = function (arr, val) {
        var index = -1;
        for (var i = 0, len = arr.length; i < len; i++) {
            if (arr[i] == val) {
                index = i;
                continue;
            }
        }
        if (index > -1) {
            arr.splice(index, 1);
        }
    };

    $.widget("W.bottomStock", {
        options: {
            hide: 0,
            texts: ['人员对比', '搜索方案'],		//current only two tab
            mode: 0,
            projects: [],
            open: -1,
            onCloseClick: null
        },
        close: function (id) {
            var self = this;
            $.post(defineds.removeStockEmpUrl, {'id': id}, function (data) {
                if (data.type == 0)
                    self._reloadPersons(data.emps);
                else
                    showErrMsg('删除失败');
            }, "json");
        },
        addPerson: function (empId) {
            if (typeof(empId) != 'string') return;
            var self = this;
            var sub = self.compareSub;
            var child = sub.find('.person');
            if (child.length >= 4) {
                showErrMsg('对比人员最多选择4人');
                return;
            }
            var bool = true;
            $.each(child, function (i, o) {
                if ($(o).attr('_id') == empId) {
                    bool = false;
                    return;
                }
            });

            if (!bool) {
                showErrMsg('所选对比人员已经存在');
                return;
            }
            $.getJSON(defineds.setStockEmpUrl, {'id': empId}, function (data) {
                if (data.type != 0) {
                    if (data.type == 1) {
                        showErrMsg('对比人员最多选择4人');
                        return;
                    } else {
                        showErrMsg('所选对比人员已经存在');
                        return;
                    }
                } else {
                    self.doPerson(data.emps[child.length], child.length);
                }
                self._openEvent(self.peopleCompare, 0);
            }, "json");
        },
        getPersonIds: function () {
            var persons = this.compareSub.find('.person');
            var ids = $.map(persons, function (obj) {
                return $(obj).attr("_id");
            });
            return ids.join(",");
        },
        //len:员工数量
        doPerson: function (emp, len) {
            var self = this;
            var sub = self.compareSub;
            var person = $('<div/>', {'class': 'person', '_id': emp.empId});
            person.append($('<span/>', {'class': 'close-btn'}));

            var basic = $('<div/>', {'class': 'basic'});
            basic.append($('<img/>', {
                'src': defineds.asyncUrl,
                'data-src': emp.imgPath,
                'class': 'lazy'
            }));

            person.append(basic);

            var text = $('<div/>', {'class': 'text'});
            var textLabel = $('<h4/>');
            var name = emp.userName;
            if (name.length > 12) {
                name = name.substring(0, 12) + '...';
                textLabel.attr('title', emp.userName);
            }
            textLabel.text(name);
            text.append(textLabel);

            var positionName = emp.positionName;
            if (positionName != null) {
                var position = $('<span/>');
                if (positionName.length > 20) {
                    positionName = positionName.substring(0, 19) + '...';
                    position.attr('title', emp.positionName);
                }
                position.text(positionName);
                text.append(position);
            }
            person.append(text);
            sub.children('.cons').before(person);
            sub.find('span.close-btn').unbind('click').bind('click', function (e) {
                e.stopPropagation();
                self._closeEvent($(this), 0);
            });
            var txt = self.options.texts[0] + '(' + (len + 1) + ')';
            self.compareRevise = txt;
            var parent = self.peopleCompare;
            if (parent.hasClass('default')) {
                parent.children(':first').text(txt);
            }
            sub.find('img.lazy').unveil();
        },
        addProject: function (obj) {
            if (typeof(obj) != 'object') return;
            var self = this;
            if (obj.length) {
                for (var i = 0; i < obj.length; i++) {
                    self._createProject(obj[i]);
                }
            } else {
                self._createProject(obj);
            }
            self._openEvent(self.takeProject, 1);
        },
        hideFrame: function (bool) {
            var self = this;
            if (bool) {
                self.element.css('display', 'none');
            } else {
                self.element.css('display', 'inline-block');
            }
        },
        //create texts[0] data
        _createCompare: function () {
            var self = this;
            self.compareRevise = self.options.texts[0];
            var people_compare = $('<div/>', {
                'id': 'people_compare',
                'class': 'default',
                'style': 'left: 30px;'
            });
            var compare_sub = $('<div/>', {'id': 'compare_sub', 'class': 'sub', 'index': 0});
            self.compareSub = compare_sub;
            self._createCompareNode();
            var compare_txt = $('<span/>', {'text': self.compareRevise});

            people_compare.append(compare_txt).append(compare_sub);

            people_compare.click(function () {
                var _this = $(this);
                if (_this.hasClass('default')) {
                    self._openEvent(_this, 0);
                } else {
                    self._defaultEvent(_this, 0);
                }
            });

            this.peopleCompare = people_compare;
            return people_compare;
        },
        //create texts[1] data
        _createProjects: function () {
            var self = this;
            var take_project = $('<div/>', {
                'id': 'take_project',
                'class': 'default',
                'style': 'left: 126px;'
            });
            var project_sub = $('<div/>', {'id': 'project_sub', 'class': 'sub', 'index': 1});
            self.projectSub = project_sub;
            self._createProjectNode();

            var project_txt = $('<span/>', {'text': self.projectRevise});

            take_project.append(project_txt).append(project_sub);

            take_project.click(function () {
                var _this = $(this);
                if (_this.hasClass('default')) {
                    self._openEvent(_this, 1);
                } else {
                    self._defaultEvent(_this, 1);
                }
            });

            this.takeProject = take_project;
            return take_project;
        },
        //create texts[0] node data
        _createCompareNode: function () {
            var self = this;
            $.ajax({
                url: defineds.getStockEmpUrl,
                async: false,
                dataType: 'json',
                success: function (data) {
                    var len = data.length;
                    var sub = self.compareSub;
                    //create person display div
                    for (var i = 0; i < len; i++) {
                        var obj = data[i];
                        var person = $('<div/>', {'class': 'person', '_id': obj.empId});
                        person.append($('<span/>', {'class': 'close-btn'}));

                        var basic = $('<div/>', {'class': 'basic'});
                        basic.append($('<img/>', {
                            'src': defineds.asyncUrl,
                            'data-src': obj.imgPath,
                            'class': 'lazy'
                        }));

                        person.append(basic);

                        var text = $('<div/>', {'class': 'text'});
                        var textLabel = $('<h4/>');
                        var name = obj.userName;
                        if (name.length > 12) {
                            name = name.substring(0, 12) + '...';
                            textLabel.attr('title', obj.userName);
                        }
                        textLabel.text(name);

                        text.append(textLabel);
                        var positionName = obj.positionName;
                        if (positionName != null) {
                            var position = $('<span/>');
                            if (positionName.length > 20) {
                                positionName = positionName.substring(0, 19) + '...';
                                position.attr('title', obj.positionName);
                            }
                            position.text(positionName);
                            text.append(position);
                        }
                        person.append(text);

                        sub.append(person);
                    }
                    sub.find('span.close-btn').click(function (e) {
                        e.stopPropagation();

                        self._closeEvent($(this), 0);
                    });
                    //create button div
                    var cons = $('<div/>', {'class': 'cons'});
                    var compareBtn = $('<input/>', {'type': 'button', 'class': 'compare_btn', 'value': '开始对比'});
                    compareBtn.click(function (e) {
                        e.stopPropagation(); 	//stop the parent node event trigger

                        self._compareEvent();
                    });
                    var span = $('<span/>', {'text': '(最多选择4人对比)'});
                    cons.append(compareBtn).append(span);
                    sub.append(cons);
                    self.compareRevise = self.options.texts[0] + (len != 0 ? '(' + len + ')' : '');
                }
            });
        },
        //create texts[1] node data
        _createProjectNode: function () {
            var self = this;
            var projects = self.options.projects;
            var len = projects.length;
            var sub = self.projectSub;
            self.projectIds = [];
            var global = $('<div/>', {'class': 'global'});
            for (var i = 0; i < len; i++) {
                var project = projects[i];
                self.projectIds.push(project.id);

                var _project = $('<div/>', {'class': 'project', '_id': project.id});
                var _close = $('<span/>', {'class': 'close-btn'});

                _close.click(function (e) {
                    e.stopPropagation();
                    var closeClick = self.options.onCloseClick;
                    if (closeClick && $.isFunction(closeClick)) {
                        closeClick(_project, project);
                    }

                    self._closeEvent($(this), 1);
                });
                _project.append(_close);

                var text = $('<span/>', {'class': 'text', 'url': project.url, 'text': project.name});
                _project.append(text);

                global.append(_project);
            }
            sub.append(global);

            sub.find('span.text').click(function (e) {
                e.stopPropagation();	//stop the parent node event trigger

                var win = window.open($(this).attr('url'));
                win.moveTo(0, 0);
                win.resizeTo(screen.availWidth, screen.availHeight);
            });

            self.projectRevise = self.options.texts[1] + (len != 0 ? '(' + len + ')' : '');
        },
        _createProject: function (obj) {
            var self = this;
            var sub = self.projectSub;
            var child = sub.find('.project');
            var bool = true;

            $.each(child, function (i, o) {
                if ($(o).attr('_id') == obj.id) {
                    bool = false;
                    return;
                }
            });

            if (!bool) {
                notify['error']('所选对比人员已经存在', {
                    "closeButton": true
                });
                return;
            }

            var _project = $('<div/>', {'class': 'project', '_id': obj.id});
            var _close = $('<span/>', {'class': 'close-btn'});

            _close.click(function (e) {
                e.stopPropagation();
                var closeClick = self.options.onCloseClick;
                if (closeClick && $.isFunction(closeClick)) {
                    closeClick(_project, obj);
                }

                self._closeEvent($(this), 0);
            });
            _project.append(_close);

            var text = $('<span/>', {'class': 'text', 'url': obj.url, 'text': obj.userName});
            _project.append(text);

            sub.find('.global').append(_project);

            self.projectIds.push(obj.id);
            var len = self.projectIds.length;
            var txt = self.options.texts[1] + (len != 0 ? '(' + len + ')' : '');
            self.projectRevise = txt;
            var parent = self.takeProject;
            if (parent.hasClass('default')) {
                parent.children(':first').text(txt);
            }
        },
        _reloadPersons: function (data) {
            var self = this;
            var len = data.length;
            var sub = self.compareSub;
            sub.find('.person').remove();
            //create person display div
            for (var i = 0; i < len; i++) {
                var obj = data[i];
                var person = $('<div/>', {'class': 'person', '_id': obj.empId});
                person.append($('<span/>', {'class': 'close-btn'}));

                var basic = $('<div/>', {'class': 'basic'});
                basic.append($('<img/>', {
                    'src': defineds.asyncUrl,
                    'data-src': obj.imgPath,
                    'class': 'lazy'
                }));

                person.append(basic);

                var text = $('<div/>', {'class': 'text'});
                var textLabel = $('<h4/>');
                var name = obj.userName;
                if (name.length > 12) {
                    name = name.substring(0, 12) + '...';
                    textLabel.attr('title', obj.userName);
                }
                textLabel.text(name);
                text.append(textLabel);

                var positionName = obj.positionName;
                if (positionName != null) {
                    var position = $('<span/>');
                    if (positionName.length > 20) {
                        positionName = positionName.substring(0, 19) + '...';
                        position.attr('title', obj.positionName);
                    }
                    position.text(positionName);
                    text.append(position);
                }
                person.append(text);

                sub.children('.cons').before(person);
            }
            sub.find('span.close-btn').unbind('click').bind('click', function (e) {
                e.stopPropagation();

                self._closeEvent($(this), 0);
            });
            var txt = self.options.texts[0] + (len != 0 ? '(' + len + ')' : '');
            self.compareRevise = txt;
            var parent = self.peopleCompare;
            if (parent.hasClass('default')) {
                parent.children(':first').text(txt);
            }
            sub.find('img.lazy').unveil();
        },
        _openEvent: function (obj, i) {
            var self = this;
            var mode = self.options.mode;
            if (mode != 0) {
                var _siblings = obj.siblings();
                var siblings_txt = i != 0 ? self.compareRevise : self.projectRevise;
                _siblings.removeClass('open').addClass('default').css({'height': '', 'zIndex': '0'});
                _siblings.children(':first').removeClass('txt').text(siblings_txt);
                _siblings.children(':last').hide().css('height', '');
            }
            obj.removeClass('default').addClass('open').css('zIndex', '999');
            var curr_txt = self.options.texts[i];
            obj.children(':first').addClass('txt').text(curr_txt);

            var parent_w = obj.parents('div').width();
            var curr_w = parent_w - (i == 0 ? 102 : 197);
            obj.children(':last').show().css('width', curr_w + 'px');

            if (i == 0) {
                obj.find('img.lazy').unveil();
            }
        },
        _reloadEvent: function () {
            /*var self = this;
             $.getJSON(defineds.getStockEmpUrl,function(data){
             self._reloadPersons(data.emps);
             });*/
        },
        _defaultEvent: function (obj, i) {
            var self = this;
            var txt = i == 0 ? self.compareRevise : self.projectRevise;
            obj.children(':first').removeClass('txt').css('padding', '').text(txt);
            obj.children(':last').hide().css('height', '');
            obj.removeClass('open').addClass('default').css('height', '');
        },
        _closeEvent: function (obj, type) {
            var self = this;
            var _parent = obj.parent();
            var _id = _parent.attr('_id');
            if (type == 0) {
                var child = self.compareSub.find('.person');
                var len = child.length;
                self.compareRevise = self.options.texts[0] + ((len - 1) != 0 ? '(' + (len - 1) + ')' : '');
            }
            else {
                removeFromArr(self.projectIds, _id);
                var len = self.projectIds.length;
                self.projectRevise = self.options.texts[1] + (len != 0 ? '(' + len + ')' : '');
            }
            self.close(_id);
            _parent.remove();
        },
        _compareEvent: function () {
            var self = this;
            var childs = self.compareSub.find('.person');
            if (childs.length < 1) {
                showErrMsg("请选择对比人员");
                return;
            }
            if (childs.length < 2) {
                showErrMsg("人员对比最少需要2个人");
                return;
            }
            var personIds = self.getPersonIds();
            var win = window.open(defineds.toContrastUrl + "?empIds=" + personIds);
            win.moveTo(0, 0);
            win.resizeTo(screen.availWidth, screen.availHeight);
        },
        //create init
        _create: function () {
            var self = this;
            var mode = self.options.mode;
            var node_one = self._createCompare();
            ;
            var node_two = mode == 0 ? '' : self._createProjects();

            var _this = self.element;

            if (self.options.hide != 0) _this.css('display', 'none');

            _this.addClass('u-bottom-stock').append(node_one).append(node_two);

            var open_index = self.options.open;
            if (open_index != -1) {
                if (mode == 0) {
                    self._openEvent(node_two, 0);
                } else {
                    self._openEvent((open_index == 0 ? node_one : node_two), open_index);
                }
            }
        }
    });
});
