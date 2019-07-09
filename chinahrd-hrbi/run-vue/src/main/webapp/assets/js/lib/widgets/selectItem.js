(function ($) {
    var defaluts = {
        selectType: false, // 默认单选
        disableAll: false,		//关闭"全部"选项
        confirmOpen: false,
        beforeSelectCall: null,
        selectCallback: null,
        confirmInfo: '',
        title: '_unique_' // 唯一标识字符串，区分一个元素多个组件
    }
    if (window.W == undefined) {
        window.W = {};
    }
    /**
     * 标签选择组件
     *
     * @param elementId
     *            div元素ID
     * @param options
     *           { title:选项标题
	 *             selectType //是否多选,默认单选
	 *             selectCallback(点击选项值,是否选中,名称);
	 *           }
     * @returns
     */
    window.W.SelectItem = function (elementId, options) {
        var self = this, p = $.extend({}, defaluts, options);
        self.elementId = elementId;// div元素ID
        var dataItemMap = {};// 选项集合map
        var selectArray = [];// 选中的map
        var itemIdPrex = "select_" + elementId + p.title;
        var allStr = 'all';
        var contentDiv;
        /**设置初始化数据*/
        self.setDataItems = function (dataItems) {
            initDataItems(dataItems);
        };
        self.addDataItems = function (dataItems) {
            if (contentDiv == undefined) {
                console && console.log("组件还未初始化，无法添加更多选项！");
                return;
            }
            // 初始化跟选项条件
            initItems(dataItems);
        };
        /**设置数据加载URL*/
        self.setUrl = function (url, params) {
            $.getJSON(url, params, function (datas) {
                initDataItems(datas);
            });
        };
        self.setSelectConfirmInfo = function (info) {
            p.confirmOpen = true;
            p.confirmInfo = info;
        };
        /**设置选中事件*/
        self.setSelectCallback = function (callback) {
            p.selectCallback = callback;
        };
        /**获取选中的选项名称*/
        self.getSelectedTexts = function () {
            var items = [];
            $.each(selectArray, function (i, n) {
                if (selectArray[i] != '' && selectArray[i] != allStr) {
                    items[items.length] = dataItemMap[selectArray[i]];
                }
            });
            return items;
        };
        /**获取选中的选项值--数组*/
        self.getSelectedItems = function () {
            var items = [];
            $.each(selectArray, function (i, n) {
                if (selectArray[i] != '' && selectArray[i] != allStr) {
                    items[items.length] = selectArray[i];
                }
            });
            return items;
        };
        /**获取选中的选项值--字符串*/
        self.getSelectedIds = function () {
            var items = "";
            $.each(selectArray, function (i, n) {
                if (selectArray[i] != '' && selectArray[i] != allStr) {
                    if (!p.selectType) {
                        items = selectArray[i];
                    } else {
                        items += i == selectArray.length - 1 ? selectArray[i] : selectArray[i] + ",";
                    }
                }
            });
            return items;
        };
        /**获取全部的选项对象--数组*/
        self.getSelectObj = function () {
            var itemObj = [];
            $.each(selectArray, function (i, n) {
                var key = selectArray[i];
                if (key != '' && key != allStr) {
                    var data = {
                        id: key,
                        text: dataItemMap[key]
                    };
                    itemObj.push(data);
                }
            });
            return itemObj;
        };
        /**获取全部的选项对象--数组*/
        self.getAllObj = function () {
            var allData = {};
            var allItemArr = [];
            for (var key in dataItemMap) {
                allData[key] = dataItemMap[key];
                var item = {
                    id: key,
                    text: allData[key]
                }
                allItemArr.push(item);
            }
            return allItemArr;
        };
        /**获取全部的选项值--Map*/
        self.getAllMap = function () {
            var allData = {};
            for (var key in dataItemMap) {
                allData[key] = dataItemMap[key];
            }
            return allData;
        };
        /**获取全部的选项值--数组*/
        self.getAllItems = function () {
            var items = [];
            for (var key in dataItemMap) {
                items[items.length] = key;
            }
            return items;
        };
        /**获取全部的选项值--字符串*/
        self.getAllIds = function () {
            var items = "";
            for (var key in dataItemMap) {
                items += key + ",";
            }
            if (items.length > 0) {
                items = items.substr(0, items.length - 1);
            }
            return items;
        };
        /**
         * 设置多个值选中
         * @param 多个key 选项的值（支持‘,’的字符串）
         * @returns false 设置选中失败，无此选项
         */
        self.setSelectItems = function (keyIds) {
            var keys = [];
            if (typeof(keyIds) == 'string') {
                keys = keyIds.split(',');
            } else {
                keys = keyIds;
            }
            if (keys.length == 0) {
                return [];
            }

            var validKeys = [];
            for (var i = 0; i < keys.length; i++) {
                var key = keys[i];
                if (dataItemMap[key]) {
                    validKeys.push(key);
                }
            }
            //多选时，若无选中数据则默认设置选中为全部
            if (validKeys.length == 0 && p.selectType) {
                // 默认选中全部
                removeAllSelected(true);
                return [];
            }
            unSelectAllItem();
            $.each(validKeys, function (i, val) {
                if (val != '') {
                    setCurrentSelect(val);
                }
            });
            return validKeys;
        };
        /**
         * 设置单个选中
         * @param key 选项的值
         * @returns false 设置选中失败，无此选项
         */
        self.setSelectItem = function (key) {
            removeAllSelected();

            var flag = setCurrentSelect(key);
            //多选时，若无选中数据则默认设置选中为全部
            if (!flag && p.selectType) {
                // 默认选中全部
                removeAllSelected(true);
                return false;
            }
            return flag;
        };

        /** 清除选中的项 */
        self.clearSelected = function () {
            removeAllSelected(true);
        };
        /** 清除选所有的选项 */
        self.clearAllItems = function () {
            removeAllSelected(true);
            dataItemMap = {};
            var allItem = $("#" + itemIdPrex + allStr);
            allItem.unbind("click");
            if (contentDiv != undefined) {
                contentDiv.html('');
            }
        };
        /** 设置选中的项 */
        function setCurrentSelect(key) {
            var exists = itemExists(key);
            if (exists) {
                return true;
            }

            var flag = false;
            for (var itemKey in dataItemMap) {
                if (key == itemKey) {
                    flag = true;
                }
            }
            // 不存在此选项值
            if (!flag) {
                return flag;
            }
            setSelectTagStyle(key);
            addToArray(selectArray, selectArray.length, key);// 添加现有的
            return true;
        }

        /** 初始化组件数据 */
        function initDataItems(dataItems) {
            removeAllSelected();
            dataItemMap = {};
            if (undefined == contentDiv) {
                contentDiv = $("#" + self.elementId);
            }
            contentDiv.html('');

            if (dataItems == undefined || dataItems == null
                || dataItems.length == 0) {
                return;
            }

            // 多选时添加“全部”选项item
            if (p.selectType && !p.disableAll) {
                addAllItem();
            }
            // 初始化跟选项条件
            initItems(dataItems);
        }

        /** 初始化选项ITEM */
        function initItems(dataItems) {
            var childrenIteam = $('<div class="col-sm-11"></div>');
            for (var i = 0; i < dataItems.length; i++) {
                var key = dataItems[i].id;
                var textValue = dataItems[i].text;
                dataItemMap[key] = textValue;

                var itemDiv = $("<span></span>", {
                    'id': itemIdPrex + key,
                    "class": "u-tag",
                    html: textValue,
                    click: function () {
                        if (p.confirmOpen) {
                            var r = window.confirm(p.confirmInfo);
                            if (!r) {
                                return;	//取消时不继续往下
                            }
                        }
                        var $this = $(this);
                        onSelect($this, $this.data('key'));
                    }
                }).appendTo(childrenIteam);
                itemDiv.data('key', key);
                if (undefined != dataItems[i].bindObj && null != dataItems[i].bindObj)  itemDiv.data('bindObj', dataItems[i].bindObj);
            }
            contentDiv.append(childrenIteam);
        }

        function addAllItem() {
            var allItem = $('<div class="col-sm-1 ct-col1"></div>');
            var id = itemIdPrex + allStr;
            var item = $("<span></span>", {
                'id': id,
                "class": "u-tag",
                'text': "全部",
                'click': function () {
                    var self = $(this);
                    var key = self.data('key')
                    var exists = itemExists(key);
                    removeAllSelected(true);// 移除选中的并选中全部
                    callback(null, !exists, '全部');
                }
            }).appendTo(allItem);
            $('<img class="u-tag-split">').appendTo(allItem);
            item.data('key', allStr);
            contentDiv.append(allItem);
            // 默认选中全部
            selectAllItem();
        }

        /** 选中"全部" */
        function selectAllItem() {
            setSelectTagStyle(allStr);
            addToArray(selectArray, 0, allStr);
        }

        /** 移除"全部" */
        function unSelectAllItem() {
            setSelectTagStyle(allStr);
            removeFromArray(selectArray, 0, allStr);
        }

        function addToArray(array, index, value) {
            array.push(value);
        }

        function removeFromArray(array, index) {
            array.splice(index, 1);
        }

        function onSelect(ele, key) {
            if (!p.selectType) {
                if (p.beforeSelectCall) {
                    // 选中之前的判断
                    if (p.beforeSelectCall(key, ele)) {
                        singleSelect(key);
                    }
                } else {
                    singleSelect(key);
                }
            } else {
                if (p.beforeSelectCall) {
                    // 选中之前的判断
                    if (p.beforeSelectCall(key, ele)) {
                        mulSelect(key, ele);
                    }
                } else {
                    mulSelect(key, ele);
                }
            }
        };

        function singleSelect(key) {
            var item = selectArray[0];
            if (item != undefined && key == item) {
                callback(key, true, dataItemMap[key]);
                return;
            }
            setSelectTagStyle(item);
            removeFromArray(selectArray, 0);// 移除原来的

            setSelectTagStyle(key);
            addToArray(selectArray, 0, key);// 添加现有的
            callback(key, true, dataItemMap[key]);
        };

        function setSelectTagStyle(item) {
            $('#' + itemIdPrex + item).toggleClass("u-tag-selected");
        };

        function mulSelect(key, ele) {
            var length = selectArray.length;
            var index = indexOfItem(key), exists = itemExists(key);
            if (itemExists(allStr)) {
                removeFromArray(selectArray, indexOfItem(allStr));
                setSelectTagStyle(allStr);
            }
            $(ele).toggleClass("u-tag-selected");
            if (exists) {
                // 存在，表明已选中过，进行移除操作
                removeFromArray(selectArray, index);// 移除
                // 移除之后，默认让全部选中
                if (selectArray.length == 0 && !p.disableAll) {
                    selectAllItem();
                }
            } else {
                addToArray(selectArray, length, key);// 加入选中
            }
            // 点击事件
            callback(key, !exists, dataItemMap[key]);
        }

        /** 清除选项时，若选中全部选项是否也删除，默认删除，true为保留 */
        function removeAllSelected(keepAll) {
            for (var i = 0; i < selectArray.length; i++) {
                setSelectTagStyle(selectArray[i]);
            }
            selectArray.length = 0;
            // 多选，清空选中后默认选中全部
            if (keepAll && p.selectType) {
                selectAllItem();
            }
        }

        function itemExists(key) {
            return indexOfItem(key) >= 0;
        }

        function indexOfItem(key) {
            return $.inArray(key, selectArray);
        }

        function callback(id, selected, text) {
            p.selectCallback && p.selectCallback(id, selected, text);
        }
    };
})(jQuery);
