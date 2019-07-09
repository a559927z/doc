define(["jquery-ui", "jQdropdown", "utils", "ztree"], function (require) {
    var contextPath = G_WEB_ROOT;
    var treeId = 0;
    $.widget("W.organTreeSelector", {
        options: {
            showSelectBtn: true,
            height: 300,
            multiple: true,
            params: {
                name: "type",
                value: null
            },
            isLevelOption: true,//(默认全部可选)
            check: {
                enable: false,
                radioType: "level"
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: -1
                }
            },
            async: {
                enable: true,
                autoParam: ["id"],
                type: "post"
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false
            }
        },

        /**
         * @param value 只接收{id : 'PA004_S000025496', text : '总经理室'}
         */
        value: function (value) {
            var self = this;
            if (undefined == value) {
                var v = self.element.jQdropdown("value");
                return v;
            }

            // 暂时只支持单选
            if (self.options.multiple) {
                self.element.jQdropdown("setMulIdAndText", value);
            } else {
                //modify by wqcai
                var vid = value.id, vname = value.text;
                if (undefined === vname) {
                    vname = self._getNameById(vid);
                    if (undefined == vname || vname == '') {
                        $.ajax({
                            url: contextPath + "/organ/getOrganNameById.do",
                            async: false,
                            type: 'POST',
                            data: {'id': vid},
                            success: function (rs) {
                                if (rs.type) {
                                    vname = rs.t;
                                }
                            }
                        });
                    }
                }
                self.element.jQdropdown("setIdAndText", vid, vname);

                if (typeof(self.options.onSelect) == 'function') self.options.onSelect(vid, vname);
            }
        },

        _enhanceOptions: function () {
            var opt = this.options;
            if (!opt.date) {
                opt.date = Tc.getYesterdayInt();
            }
            if (opt.async && !opt.async.url) {
                var value = opt.params.value;
                var urlParams = "";
                if (value) {
                    urlParams += "&" + opt.params.name + "=" + opt.params.value;
                }
                opt.async.url = Tc.timestamp(contextPath + "/organ/queryOrganTree.do") + urlParams;
            }
            if (opt.multiple) {
                opt.check = {
                    enable: true,
                    chkboxType: {"Y": "", "N": ""}
                };
            }
        },
        _getNameById: function (id) {
            var self = this, ztreeData = self.ztree_data;
            return self._recursiveZtreeGetName(ztreeData, id);
        },
        _recursiveZtreeGetName: function (data, id) {
            var self = this, name = '';
            if (!data) return name;
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                if (obj.id == id) {
                    name = obj.name;
                    break;
                }
                self._recursiveZtreeGetName(obj.children, id);
            }
            return name;
        },
        _createZTree: function () {
            var self = this, opt = self.options;
            self.isOne = true;
            //modify by lkliao
            self.ztree_data = [];
            self._enhanceOptions();
            var ul = $("<ul/>", {
                "id": "ztree_" + (treeId++),
                "class": "ztree"
            });
            if (!opt.callback) {
                opt.callback = {};
                // 防止选择不同级别节点
                var currentLv = -1;
                opt.callback.beforeCheck = function (treeId, treeNode) {
                    var oIds = self.element.jQdropdown('getOldIds');
                    if (self.isOne) {
                        self.element.jQdropdown("clear", true);
                        self.element.jQdropdown('setOldIds', oIds);
                    }
                    //modify by lkliao
                    var currentLv = treeNode.level;
                    var flag = true;
                    var checkNodes = self._ztree.getCheckedNodes(true);
                    for (var i in checkNodes) {
                        var node = checkNodes[i];
                        if (node.level != currentLv) {
                            flag = false;
                            return flag;
                        }
                    }
                    return true;
                };

                opt.callback.onClick = function (event, treeId, treeNode) {
                    if (opt.multiple == false) {
                        self.isOne = false;
                        //防止选择不同级别节点
                        if (!treeNode.checked && tree.getCheckedNodes(true).length == 0) {
                            currentLv = -1;
                        }
                        self.ztree_data.push(treeNode);
                        ul.onCheck && ul.onCheck(treeNode.id, treeNode.name, treeNode.checked);
                    }
                }
                // 选中事件
                opt.callback.onCheck = function (event, treeId, treeNode) {
                    self.isOne = false;
                    //防止选择不同级别节点
                    if (!treeNode.checked && tree.getCheckedNodes(true).length == 0) {
                        currentLv = -1;
                    }
                    self.ztree_data.push(treeNode);
                    ul.onCheck && ul.onCheck(treeNode.id, treeNode.name, treeNode.checked);
                    console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
                };
            }

            ul.outerHeight(opt.height + 25);
            var tree = this._ztree = $.fn.zTree.init(ul, opt);
            ul.check = function (itemIds) {
                /*$.each(itemIds, function(idx, id){
                 var nodes = tree.getNodesByParam("id", id);
                 nodes.length && tree.checkNode(nodes[0]);
                 });*/
            };
            ul.clear = function () {
                tree.checkAllNodes(false);
                self.isOne = true;
            };
            return ul;
        },
        _create: function () {
            var opt = this.options;
            var ul = this._createZTree();
            var self = this;
            if (opt.showSelectBtn) {
                var btn = $('<input type="button" value="选择" style="margin-left:5px;" class="u-btn">');
                btn.click(function () {
                    self.element.jQdropdown("show");
                });
                btn.insertAfter(this.element);
            }

            var c = this.element.jQdropdown({
                multiple: opt.multiple,
                width: opt.width,
                dropWidth: 200,
                maxHeight: opt.height,
                height: opt.height,
                mouseenterColor: opt.mouseenterColor,
                mouseleaveColor: opt.mouseleaveColor,
                onSelect: opt.onSelect,
                checkableWidget: function () {
                    return ul;
                }
            });
            // 设置初值
            opt.value && this.value(opt.value);
        }
    });

});