if (!("zrw" in window)) {
	window.zrw = {}
}
/***
 * 存放zrw所有封装的组件文件
 * 公共封装的zrw组件
 * @param a
 * @param b
 */
(function(a, b) {
	a.fn.zrw_tree = function(d) {
		var c = {
			"open-icon": "icon-folder-open",
			"close-icon": "icon-folder-close",
			selectable: true,
			selectCallBack:null,
			"selected-icon": "icon-ok",
			"unselected-icon": "tree-dot"
		};
		c = a.extend({}, c, d);
		this.each(function() {
			var e = a(this);
			e.html('<div class = "u-tree-folder" style="display:none;">				<div class="u-tree-folder-header">					<i class="' + c["close-icon"] + '"></i>					<div class="u-tree-folder-name"></div>				</div>				<div class="u-tree-folder-content"></div>				<div class="u-tree-loader" style="display:none"></div>			</div>			<div class="u-tree-item" style="display:none;">				' + (c["unselected-icon"] == null ? "" : '<i class="' + c["unselected-icon"] + '"></i>') + '				<div class="u-tree-item-name"></div>			</div>');
			e.addClass(c.selectable == true ? "u-tree-selectable" : "u-tree-unselectable");
			e.tree(c);
		});
		return this;
	}
})(window.jQuery);
