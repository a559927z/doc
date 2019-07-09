/** 筛选条件
 *
 * htpeng 2015-12-25
 * */

define(
    ['jquery'],
    function ($) {

        var defaults = {
            url: '',
            attach: [],
            lazy: false,
            data: null,	// furnish public data
            onClick: function (id, name, type) {
            },	// function(id, name, type) { }
            height: 0,
            loadComple: function (o) {
            },
            nodeClick: false,
            bind: {},
            target: {},
            close: function (w, h) {

            }, expand: function (w, h) {

            }
        };

        $.fn.searchBox3 = function (options) {
            var opt = $.extend(true, {}, defaults, options);
            var searchBox = function (element, options) {
                this.init(element, options)
            };

            searchBox.prototype = {
                _init: false,//是否初始化
                firstExpend: true,
                selectData: {},
                organTree: {},
                _defaultData: {},
                init: function (element, options) {
                    $.extend(this, options);
                    this.initModal();
                    //this.option=options;
                    this.el = element;
                    this.getData();
                }, initModal: function () {
                    var div = $("<div></div>");
                    div.attr("class", "row filter");
                    this.$content = $('<div class="form-horizontal"></div>');
                    div.append(this.$content);
                    this.$bottom = this.loadCol();
                    var colVal = this.loadColVal();
                    this.loadBottom(colVal);
                    this.$bottom.append(colVal);
                    this.$component = div;
                }, getData: function () {
                    var _ = this;
                    if (_.url == '') {
                        _.data = [];
                        _.renderCallbackFun();
                        _._init = true;
                    } else {
                        $.getJSON(_.url, function (json) {
                            if (json != null) {
                                _.data = json;
                                _.renderCallbackFun();
                                _._init = true;
                            }
                        });
                    }
                }, loadAttach: function () {
                    var _ = this;
                    $.each(this.attach, function (i, v) {
                        var col = _.loadCol(v);
                        var colVal = _.loadColVal();
                        if (v.type == "date") {
                            $.each(v.data, function (j, k) {
                                _.loadTime(k, colVal);
                            });
                        } else if (v.type == "select") {
                            $.each(v.data, function (j, k) {
                                _.loadSelect(k, colVal);
                            });
                        } else if (v.type == "input") {
                            $.each(v.data, function (j, k) {
                                _.loadText(k, colVal);
                            });
                        }else if(v.type == "organTree"){
                        	$.each(v.data, function (j, k) {
                              _.loadOrganTree(k, colVal);
                        	});
                        }
                        col.append(colVal);
                        _.$content.append(col);
                    });
                }, loadSelect: function (k, colVal) {
                    var _ = this;
                    _.selectData[k.name] = [];
                    _._defaultData[k.name] = [];
                    var sel = $('<select></select>')
                    $(sel).attr("name", k.name);
                    $(sel).attr("id", k.name);
                    $.each(k.options, function (i, o) {
                        $(sel).append('<option value="' + o.key + '">' + o.value + '</option>');
                    });
                    colVal.append(sel);
                },loadOrganTree:function(k, colVal){
                	 var _ = this;
                     _.selectData[k.name] = [];
                     _._defaultData[k.name] = [];
                     var sel = $('<div></div>');
                      $(sel).addClass("pull-left");
                      $(sel).addClass("serachbox-organ-tree");
                     var ul=$('<ul></ul>');
                     $(ul).addClass("ztree");
                     $(ul).attr("name", k.name);
                     $(ul).attr("id", k.name);
                     $(sel).append(ul);
//                     $.each(k.options, function (i, o) {
//                         $(sel).append('<option value="' + o.key + '">' + o.value + '</option>');
//                     });
                     colVal.append(sel);
                }, loadAll: function (v, childs, colVal) {
                    var _ = this;
                    var id = v.id;
                    var btnAll = $('<div class="condition"  sign="all" name="' + v.id + '" value="">全部</div>');
                    colVal.append(btnAll);
                    if (v.bindObj != null) {
                        _.bind[id] = childs;
                        _.target[id] = v.bindObj;
                    }
                    $(btnAll).click(function () {
                        if ($(this).hasClass("condition-click")) {
                            $.each($(this).parent().parent().find(".condition"), function () {
                                $(this).removeClass("condition-click");
                            });
                        } else {
                            $.each($(this).parent().parent().find(".condition"), function () {
                                $(this).removeClass("condition-click").addClass("condition-click");
                            });
                        }
                        var id = $(this).attr('id');
                        var name = $(this).text();
                        var checked = $(this).hasClass("condition-click");
                        if (v.bindObj != null) {
                            $.each(_.target[v.id], function (j, k) {
                                $(k).show();
                            });
                        }
                        if (_.nodeClick) {
                            _.onClick(id, name, checked);
                        }

                    });

                    colVal.append('<img class="split"/>');
                }, _loadComple: function () {
                    var _ = this;
                    $.each(_.target, function (i, o) {
                        var targetArr = [];
                        $.each($(_.el).find("button"), function () {
                            if ($(this).attr("sign") != "all") {
                                var name = $(this).attr("name");
                                if (name == o) {
                                    targetArr.push(this);
                                }
                            }
                        });
                        _.target[i] = targetArr;
                    });
                    this.loadComple(this);
                }, loadButton: function (v, k, colVal) {
                    var _ = this;
                    var btn = $('<div class="condition" name="' + v.id + '" value="' + k.id + '">' + k.name + '</div>');
                    //update wqcai
                    if (undefined != k.bindObj && null != k.bindObj) {
                        btn.data('bindObj', k.bindObj);
                    }
                    btn.data()
                    colVal.append(btn);
                    $(btn).select(function () {
                        if (!$(this).hasClass("condition-click")) {
                            $(this).click();
                        }
                    });

                    $(btn).click(function () {
                        if ($(this).hasClass("condition-click")) {
                            $(this).removeClass("condition-click");
                        } else {
                            $(this).removeClass("condition-click").addClass("condition-click");
                        }
                        var checkAll = true;
                        var thisClickArr = [];
                        var checkUnclickAll = true;
                        $.each($(this).parent().children(".condition"), function (m, n) {
                            if (!$(this).hasClass("condition-click")) {
                                checkAll = false;
                            } else {
                                checkUnclickAll = false;
                                thisClickArr.push($(this).attr("value"));
                            }
                        });
                        $(this).parent().parent().children(":first").children(".condition").removeClass("condition-click");
                        if (checkAll) {
                            $(this).parent().parent().children(":first").children(".condition").addClass("condition-click");
                        }
                        if (v.bindObj != null) {
                            var childs = _.bind[v.id];
                            var targetArr = _.target[v.id];
                            var objArr = [];
                            $.each($(_.el).find(".condition"), function () {
                                if ($(this).attr("sign") != "all") {
                                    var name = $(this).attr("name");
                                    var value = $(this).attr("value");
                                    if (!(checkAll || checkUnclickAll) && name == v.id && $(this).hasClass("condition-click")) {
                                        $.each(childs, function () {
                                            if (this.id == value) {
                                                $.each(this.bindData.split(","), function () {
                                                    var bool = true;
                                                    var _this = this;
                                                    $.each(objArr, function () {
                                                        if (this == _this) {
                                                            bool = false;
                                                        }
                                                    });
                                                    if (bool) {
                                                        objArr.push(this);
                                                    }
                                                });
                                            }
                                        })
                                    }
//												else if(name==v.bindObj){
//													targetArr.push(this);
//												}
                                }
                            });
                            if (checkAll || checkUnclickAll) {
                                $.each(targetArr, function (j, k) {
                                    $(k).show();
                                });
                            } else {
                                var selectArr = [];
                                $.each(targetArr, function (j, k) {
                                    if ($(k).hasClass("condition-click")) {
                                        selectArr.push($(k).attr("value"));
                                    }
                                    $(k).removeClass("condition-click");
                                    $(k).hide();
                                });
                                $.each(objArr, function (i, o) {
                                    $.each(targetArr, function (j, k) {
                                        var id = $(k).attr("value");
                                        if (o == id) {
                                            $.each(selectArr, function (a, s) {
                                                if (o == s) {
                                                    $(k).addClass("condition-click");
                                                }
                                            });
                                            $(k).show();
                                            return true;
                                        }
                                    });
                                });
                            }
                        }
                        var id = $(this).attr('id');
                        var name = $(this).text();
                        var checked = $(this).hasClass("condition-click");
                        if (_.nodeClick) {
                            _.onClick(id, name, checked);
                        }

                    });
                }, loadMore: function () {
                    var _ = this;
                    var more = $(_.el).html();
                    if (more) {
                        var moreDiv = $('<div class="row"></div>');
                        moreDiv.css("padding", "10px 20px 0");
                        this.moreLabel = $('<label class="more-search-label icon-panel-down">更多筛选条件</label>');

                        moreDiv.append($(_.el).html());
                        $(_.el).html(moreDiv);
                        $.each($(_.el).find(".more-search"), function () {
                            $(this).append(_.moreLabel);
                            //$(this).append(icon);
                        });
                        $(_.el).append(_.$component);
                        if (this.height == 0) {
                            this.height = $(_.$component).height();
                        }

                        $(_.$component).hide();
                        $(this.moreLabel).click(function () {
                            var _this = $(this);
                            _this.removeClass("icon-panel-down icon-panel-up");
                            if (_.$component.is(":hidden")) {
                                _.showPanel();
                                _this.text('精简筛选条件');
                                _this.addClass("icon-panel-up");
                            } else {
                                _.closePanel();
                                _this.text('更多筛选条件');
                                _this.addClass("icon-panel-down")
                            }
                        });
                    } else {
                        $(_.el).append(_.$component);
                    }
                }, loadBottom: function (colVal) {
                    var _ = this;
                    var submit = $('<div class="condition-btn-search" >搜索</div>');
                    colVal.append(submit);
                    var reset = $('<div class="condition-btn-reset" >清除</div>');
                    colVal.append(reset);
                    $(submit).click(function () {
                        _.$component.hide();
                        $(_.moreLabel).removeClass("icon-panel-down").removeClass("icon-panel-up").addClass("icon-panel-down");
                        _.close(0, _.height);
                        _.onClick();
                        //_.closePanel();

                    });

                    $(reset).click(function () {
                        _.clear();
                    });
                }, loadTime: function (v, colVal) {
                    var _ = this;
                    _.selectData[v.name] = [];
                    _._defaultData[v.name] = [];
                    if (colVal.html() != "") {
                        colVal.append($('<span class="input-group timesplit"> - </span>'));
                    }
                    var time = $('<div id="' + v.name + '_div" class="input-group date form_date col-md-2" data-date-format="' + v.format + '"></div>')
                    var input = $('<input class="form-control" type="text" id="' + v.name + '" name="' + v.name + '" readonly/>');
                    time.append(input);
                    v.date = v.date ? v.date : "";
                    $(input).val(v.date);
                    time.append($('<span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar"></span></span>'));

                    colVal.append(time);
                }, loadText: function (v, colVal) {
                    var _ = this;
                    _.selectData[v.name] = [];
                    _._defaultData[v.name] = [];
                    if (colVal.html() != "") {
                        colVal.append($('<span class="input-group timesplit"> - </span>'));
                    }
                    if (!v.className) {
                        v.className = "";
                    }
                    if (!v.leftLabel) {
                        v.leftLabel = "";
                    }
                    if (!v.rightLabel) {
                        v.rightLabel = "";
                    }
                    var max = "";
                    if (v.max) {
                        max = 'maxlength="' + v.max + '"';
                    }
                    var div = $('<div id="' + v.name + '_div" class="input-group"></div>')
                    var input = $('<input class="form-control ' + v.className + '"  type="text" id="' + v.name + '" name="' + v.name + '" ' + max + '/>');
                    if (v.leftLabel != "") {
                        div.append($('<span  class="input-group labelsplit">' + v.leftLabel + '</span>'));
                    }
                    div.append(input);
                    if (v.rightLabel != "") {
                        div.append($('<span  class="input-group labelsplit">' + v.rightLabel + '</span>'));
                    }
                    $(div).val(v.value);

                    colVal.append(div);
                }, loadCol: function (v) {
                    var label = "";
                    if (v) {
                        label = v.name ? v.name : v.label;
                        label += "：";
                    }
                    var col = $('<div class="form-group"></div>');
                    col.append('<label for="xx" class="col-sm-1 control-label">' + label + '	</label>');
                    return col;
                }, loadColVal: function () {
                    return $('<div class="col-sm-10 ct-col1"></div>');
                }
                , renderCallbackFun: function () {
                    var _ = this;
                    this.loadAttach();
                    $.each(_.data, function (i, v) {
                        var col = _.loadCol(v);
                        var colVal = _.loadColVal();
                        var allDiv = $('<div class="col-sm-2 all"></div>');
                        colVal.append(allDiv);
                        _.loadAll(v, v.childs, allDiv);
                        colVal.append(allDiv);
                        _.selectData[v.id] = [];
                        _._defaultData[v.id] = [];
                        var btnDiv = $('<div class="col-sm-10 childrenBtn"></div>');
                        $.each(v.childs, function (j, k) {
                            _.loadButton(v, k, btnDiv);
                        });
                        colVal.append(btnDiv);
                        col.append(colVal);
                        _.$content.append(col);

                    });
                    this.loadMore();
                    this.$content.append(this.$bottom);
                    this.initTime();
                    this.initOrganTree();
                    this.listener();
                    this._loadComple();
                }, hide: function (h, num) {
                    var _ = this;
                    setTimeout(function () {
                        var nh = h - 100 * num;
                        if (nh <= 0) {
                            $(_.$component).height(0);
                            $(_.$component).hide();
                        } else {

                            $(_.$component).height(nh);
                            _.hide(nh, (num + 1));
                        }
                    }, 100);
                }, show: function (h, num) {
                    var _ = this;
                    $(_.$component).show();
                    setTimeout(function () {
                        var nh = h + 80 * num;
                        if (nh < _.height) {
                            $(_.$component).height(nh);
                            _.show(nh, (num + 1))
                        } else {
                            $(_.$component).height(_.height);

                        }
                    }, 100);
                }
                , getSelectData: function () {
                    var _ = this;
                    //	_.selectData=_._defaultData;
                    $.each(_.selectData, function (k, v) {
                        _.selectData[k] = [];
                    });
                    $.each(this.attach, function (i, v) {
                    	if(v.type == "organTree"){
                    		  $.each(v.data, function (j, k) {
                                  (_.selectData[k.name]).push($("#" + k.name).attr("value"));
                              });
                    	}else{
                    		  $.each(v.data, function (j, k) {
                                  (_.selectData[k.name]).push($("#" + k.name).val());
                              });
                    	}	
                      
                    });

                    $.each($(_.el).find(".condition"), function (i, v) {
                        if ($(this).attr("sign") != "all" && $(this).hasClass("condition-click")) {
                            var id = $(this).attr("name");
                            (_.selectData[id]).push($(this).attr("value"));
                        }
                    });
                    $.each(_.selectData, function (i, v) {
                        _.selectData[i] = v.join(",");
                    });
//								$.each(_.selectData,function(i,k){
//									alert(i +" : "+k);
//								});
                    return _.selectData;
                }, initTime: function () {
                    var _ = this;
                    $.each(this.attach, function (i, v) {
                        if (v.type == "date") {
                            $.each(v.data, function (j, k) {
                                var p = {
                                    format: k.format,
                                    autoclose: true,
                                    pickerPosition: 'bottom-left',
                                    startView: 2,//从年开始
                                    minView: 2,
                                    //  endDate:new Date(),
                                    language: 'cn'
                                };
                                if (v.end) {
                                    $.extend(p, {
                                        endDate: v.end
                                    });
                                }
                                $('#' + k.name + "_div").datetimepicker(p);
                            });
                        }
                    });

                },initOrganTree:function(){
                	var _ = this;
                    $.each(this.attach, function (i, v) {
                        if (v.type == "organTree") {
                            $.each(v.data, function (j, k) {
                            	var organBar=$("#"+k.name).organTreeSelector({
                                  multiple: false,
                                  showSelectBtn: false,
                                  mouseenterColor: '#fff',
                                  mouseleaveColor: '#fff',
                                  onSelect: function (ids, texts) {
                                	  $("#"+k.name).attr("value",ids);
                                  }
                               });
                            	$("#"+k.name).attr("value",k.options.id);
                            	_.organTree[k.name]=organBar;
                            	organBar.organTreeSelector("value", {id: k.options.id, text:  k.options.text});
                            });
                        }
                    });
                }, listener: function () {
                    var _ = this;
                    $.each(this.attach, function (i, v) {
                        if (v.type == "input") {
                            $.each(v.data, function (j, k) {
                                $('#' + k.name).keyup(function () {
                                    if (k.type == "num") {
                                        this.value = this.value.replace(/\D/g, '');
                                    }
                                });
//                            	 $('#' + k.name).on("afterpaste",function(){
//		                       		  if(v.num){
//		                       			  this.value=this.value.replace(/\D/g,'');
//		                       		  }
//                               	 });
                            });
                        }
                    });

                }, clear: function () {
                	var _=this;
                    $.each(this.attach, function (i, v) {
                        if (v.type == "date") {
                            $.each(v.data, function (j, k) {
                                $("#" + k.name).val(k.date);
                            });
                        } else if (v.type == "select") {
                            $("#" + v.name).find("option:first").attr("selected", true);
                        } else if (v.type == "input") {
                            $.each(v.data, function (j, k) {
                                $("#" + k.name).val("");
                            });
                        }else if (v.type == "organTree") {
                            $.each(v.data, function (j, k) {
                            	$("#"+k.name).attr("value",k.options.id);
                            	_.organTree[k.name].organTreeSelector("value", {id: k.options.id, text:  k.options.text});
                            });
                        }
                    });

                    $.each($(this.el).find(".condition"), function (i, v) {
                        if ($(this).attr("sign") == "all") {
                            $(this).click();
                            if ($(this).hasClass("condition-click")) {
                                $(this).click();
                            }

                        }
                    });
                }, select: function (id, val) {
                    $.each($(this.el).find(".condition"), function (i, v) {
                        if ($(this).attr("name") == id && $(this).attr("value") == val) {
                            $(this).select();

                        }
                    });
                }, findRow: function (id) {
                    var result = [];
                    $.each($(this.el).find(".condition"), function (i, v) {
                        if ($(this).attr("name") == id) {
                            result.push($(this));
                        }
                    });
                    return result;
                }, showPanel: function () {
                    if (this.height <= 0) {
                        this.$component.show();
                        this.height = $(this.$component).height();
                        //this.$component.hide();
                    } else {
                        if (this.firstExpend) {//设置默认高度  初次展开
                            this.$component.show();
                            var h = $(this.$component).height();
                            this.$component.hide();
                            //alert(h+"  "+this.height);
                            if (h > this.height) {
                                this.$component.css("overflow-y", "scroll");
                            }
                            this.firstExpend = false;
                        }
                        $(this.$component).height(0);
                        this.show(0, 1);
                    }
                    this.expand(0, this.height);

                    //window.ischang=true;
                    //$(window.parent.document.getElementById("mainFrame")).height($(window).height()+this.height);
                    //$(window).height($(window).height()+this.height);
                    //	$("#main-container").height($("#main-container").height()+this.height);
                }, closePanel: function () {
//			        			if(this.height<=0){
//			        				this.$component.hide();
//			        				this.height=$(this.$component).height();
//			        			}else{

                    this.hide(this.height, 1);
                    this.close(0, this.height);
                    //window.ischang=true;
//			        				$(window.parent.document.getElementById("mainFrame")).height($(window).height()-this.height)
                    //$(window).height($(window).height()-this.height)
                    //	$("#main-container").height($("#main-container").height()-this.height);

                    //}
                }, clearAll: function () {
                    this.clear();
                }, destroy: function () {
                    var _ = this;
                    console.log($(_.el))
                    $(_.el).find(".more-search").html("");
                    $(_.el).html($(_.el).children().html());
                    _ = null;
                },setValue:function(id,obj){
                	var _=this;
                	var organTree=_.organTree[id];
                	if(undefined!=organTree){
                		$("#"+id).attr("value",obj.id);
                		organTree.organTreeSelector("value", {id: obj.id, text:  obj.text});
                	}
                }
            }

            return new searchBox(this, opt);
        };
    });
