// 所有模块都通过 define 来定义
// 第一个参数是依赖的名称数组；第二个参数是函数.
define(["jquery-ui"], function (require) {
    /**
     * 基础的下拉组件框架
     */
    $.widget("W.jQdropdown", {
        options: {
            width: 150,
            moveTop: 3,//弹出层上移像素,盖住 input的 border
            multiple: true,
            autoHide: true,
            value: null, // 初值, id或id数组
            data: null, // 下拉选项
            styleClass: "u-dropdown",
            inputClass: "u-dropdown-input",
            inputDownClass: "u-dropdown-input-down",
            btnsClass: "u-dropdown-btns",
            checkClass: "u-dropdown-checkable",
            maxHeight: 200,
            mouseenterColor: '#62a8d1',
            mouseleaveColor: '#0099cc',
            /*
             *  如果是单选控件，须提供 onCheck(itemId, itemText, checked)事件;
             *  如果是多选控件，还须提供选择指定id的Item check(itemIds)、清除所有选择这两个方法clear();
             */
            checkableWidget: function () {
                throw "请实现checkableWidget(options)方法";
            },
            onSelect: function (ids, texts, isBiz) {
            }
        },

        value: function (value) {
            if (undefined == value) {
                var ids = this._ids;
                if (this.options.multiple) {
                    return $.isArray(ids) ? ids : [ids];
                }
                return $.isArray(ids) ? (ids[0] || null) : ids;
            }

            this.clear();
            var ids = this._ids = $.isArray(value) ? value : [value];
            this.select(ids);
        },

        /**
         * 只接收 [ {id:'', text:''} ]
         */
        setIdAndText: function (id, text) {
            this.select([id]);
            this._inputbox.find(".dropDownValue").text(text);
            //this._inputbox.val(text);
        },
        setMulIdAndText: function (values) {
            var self = this;
            var ids = [];
            var texts = "";
            $.each(values, function (i, value) {
                ids.push(value.id);
                texts += value.text;
                if (i != (values.length - 1))
                    texts += ",";
            })
            self.select(ids);
            self._inputbox.val(texts);
        },
        select: function (itemIds) {
            var self = this;
            if (itemIds.length) {
                this._checkableWidget().check(itemIds);
                // 选中时，把id设置进去,如果已经存在，不重复设置
                if (!this.options.multiple) {//modify by lkliao
                    self._ids = [];
                }
                self._ids = self._ids || [];
                $.each(itemIds, function (i, id) {
                    if ($.inArray(id, self._ids) === -1) {
                        self._ids.push(id);
                    }
                });
            }
            if (this.options.data) {
                var texts = $.map(this.options.data, function (n) {
                    return $.inArray(n.id, itemIds) > -1 ? n.text : null;
                });
                this._inputbox.val(texts.join(","));
            }
        },

        clear: function (checkNodes) {
            var self = this;
            self._inputbox.val("");
            self._ids = [];
            if (self.options.multiple) {
                self._oldIds = [];
                self._oldTexts = "";
                if (checkNodes || checkNodes == undefined)
                    self._checkableWidget().clear();
            }
        },

        show: function () {
            this._setPos();

            if (this.options.multiple) {
                this.select(this._ids);
            }
            this._checkableWidget();

            //防止被彈出層覆蓋
            var jbox = this._inputbox.parents(".jbox");

            if (jbox) {
                var zIndex = jbox.css('zIndex');
                this._inputbox.addClass("u-input-noborder");
                var _dropdown_top = $(this._dropdown).css("top");
                var _top = _dropdown_top.substr(0, _dropdown_top.length - 2);
                $(this._dropdown).css({
                    zIndex: ++zIndex,
                    top: parseInt(_top) - this.options.moveTop + "px"
                });
            }
            // 防止没有初始化
            this._dropdown.slideDown();
            this._inputbox.addClass(this.options.inputDownClass);
            this._inputbox.attr("readonly", true);
            var self = this;
            $("body").bind("mousedown", function (event) {
                var len = $(event.target).parents(".u-dropdown").length;
                if (!(event.target == self._inputbox || event.target == this._dropdown || len > 0)) {
                    //self.hide();//修改源码 取消事件冒泡
                }
            });
            $("body").bind("keydown", function (event) {
                var e = event || window.event || arguments.callee.caller.arguments[0];
                if (e && (e.keyCode == 27 || e.keyCode == 13)) { // 按 Esc
                    //self.hide();//修改源码 取消事件冒泡
                }
            });

            $(window).on("scroll", function (event) {
                //self._setPos();
                //self.hide();
            });
        },
        _setPos: function () {
            var self = this;
            self._dropdown.show().position({
                of: self._inputbox,
                my: "left top",
                at: "left bottom"
            });//.hide();
        },

        hide: function () {
            var self = this;
            self._dropdown.hide("fast", function () {
                if (self.options.multiple) {
                    self._checkableWidget().clear();
                }
            });
            this._inputbox.removeClass(this.options.inputDownClass);
        },

        _create: function () {
            this._ids = [];
            var opt = this.options;
            this.element.hide();
            // draw widgets
            var dropdown = this._dropdown = $("<div>", {"readonly": true}).addClass(opt.styleClass).addClass("dropdown-obj").hide();
            opt.multiple && this._addBtnbox(dropdown);
            var input = this._addInputbox(dropdown);
            //input.outerWidth(opt.width + 70);//70 修改源码 下拉框的宽度
            dropdown.css("width", opt.dropWidth + 10 || opt.width + "px" + 10);//10 修改源码 下拉框弹出框的宽度
            $('body').append(dropdown);
            $("." + opt.styleClass).append('<div class="ct-u-dropdown-arrow"></div>')
            //更改
            // 设置初值
            opt.value && this.value(opt.value);
        },

        _onCheck: function (id, text, checked, isBiz) {
            var self = this;
            var input = this._inputbox;
            var val = input.val();
            if (false == this.options.multiple) {
                this._ids = id;
                $(input).find(".dropDownValue").text(text);
                this.hide();
                //this.options.onSelect(id, text,checked,isBiz);
                this.options.onSelect(id, text, isBiz);
            } else if (checked) {
                if ($.inArray(id, this._ids) === -1) {
                    this._ids.push(id);
                }
                val ? input.val(val + ',' + text) : input.val(text);
            } else if (val) {
                var texts = val.split(",");
                removeFromArr(text, texts);
                removeFromArr(id, this._ids);
                input.val(texts.join(","));
            }
        },

        _addBtnbox: function (dropdown) {
            var self = this;
            self._oldText = "";
            self._oldIds = [];

            var ok = $("<button>确定</button>").addClass("u-btn").click(function () {
                self._oldText = self._inputbox.val();
                self._oldIds = self._ids.slice();
                var ids = $.isArray(self._ids) ? self._ids.join(",") : self._ids;
                self.options.onSelect(ids, self._oldText.split(","));
                self.hide();
            });
            var cancel = $("<button>取消</button>").addClass("u-btn").click(function () {
                self._ids = self._oldIds.slice();
                self._inputbox.val(self._oldText);
                self.select(self._ids);
                self.hide();
            });
            var box = this._btnbox = $("<div>").append(ok).append(cancel);
            box.addClass(this.options.btnsClass).appendTo(dropdown);
        },

        _addInputbox: function () {
            var self = this, p = self.options;
            var elem = self.element.hide();
            //var input = $("<div type='text' readonly='true'><div class='dropDownBlueIcon'></div><span class='dropDownValue'></span><div class='dropDownRightBtn'><div class='dropDownBlackIcon'></div></div></div>").on("click focus",function() {
            //更改
            var input = $("<div class='dropdown-obj' type='text' readonly='true'><span class='dropDownValue dropdown-obj'></span><div class='dropDownRightBtn dropdown-obj'><i class='icon-caret-down'></i></div></div>").on("mouseenter", function () {
                self.show(1);
                $(".u-dropdown-input").css("backgroundColor", p.mouseenterColor);
            }).on("mouseleave", function (e) {
//                var e = arguments.callee.caller.arguments[0] || window.event;
                if ($(e.toElement).hasClass("dropdown-obj") || $(e.toElement).hasClass("ct-u-dropdown-arrow")) {
                    return;
                } else {
                    self.hide(1);
                    $(".u-dropdown-input").css("backgroundColor", p.mouseleaveColor);
                }
//                if(e.toElement != undefined && e.toElement != null){
//                    if(e.toElement.id == "ztree_0" || e.toElement.id == ""){
//                        return;
//                    }
//                }


            }).addClass(this.options.inputClass);
            input.insertAfter(elem);
            return (this._inputbox = input);
        },

        getOldIds: function () {
            return this._oldIds;
        },

        setOldIds: function (oldIds) {
            this._oldIds = oldIds;
        },

        /**
         * 创建下拉部分控件，属于懒加载
         */
        _checkableWidget: function () {
            if (!this._widget) {
                var self = this, opt = this.options;
                this._widget = opt.checkableWidget();
                this._widget.addClass(opt.checkClass);
                this._widget.addClass("dropdown-obj");
                this._widget.onCheck = function (id, text, checked, isBiz) {
                    self._onCheck(id, text, checked, isBiz);
                };
                this._widget.css({
                    "overflow": "auto",
                    "max-height": opt.maxHeight
                });
                // 会影响到控件本身
                this._dropdown.prepend(this._widget);
                /*	opt.autoHide &&	this._dropdown.on("mouseleave", function() {
                 self.hide('slow');
                 });*/
                // 设置整个控件的高度
                var h = Math.min(this._widget.height(), opt.maxHeight);
                h = opt.multiple ? h + this._btnbox.height() : h;
                this._dropdown.height(h);

                //var tid = 0;
                if (opt.autoHide) {
                    this._dropdown.on("mouseenter", function () {
                        $(".u-dropdown-input").css("backgroundColor", opt.mouseenterColor);
                        $(this).show(1);
                        window.clearTimeout(self.tid);
                    }).on("mouseleave", function () {
                        $(this).hide(1);
                        $(".u-dropdown-input").css("backgroundColor", opt.mouseleaveColor);
                    });
                    //end
                }


            }
            return this._widget;
        }
    });

    function removeFromArr(elem, arr) {
        var idx = $.inArray(elem, arr);
        if (idx > -1) {
            arr.splice(idx, 1);
        }
    }

});
