(function(a, c) {
	var b = function(e, d) {
			this.$element = a(e);
			this.options = a.extend({}, a.fn.tree.defaults, d);
			this.$element.on("click", ".u-tree-item", a.proxy(function(f) {
				this.selectItem(f.currentTarget);
				this.options.selectCallBack(a(f.currentTarget).data());
			}, this));
			this.$element.on("click", ".u-tree-folder-header", a.proxy(function(f) {
				this.selectFolder(f.currentTarget)
			}, this));
			this.render()
		};
	b.prototype = {
		constructor: b,
		render: function() {
			this.populate(this.$element)
		},
		populate: function(f) {
			var e = this;
			var d = f.parent().find(".tree-loader:eq(0)");
			d.show();
			this.options.dataSource.data(f.data(), function(g) {
				d.hide();
				a.each(g.data, function(h, j) {
					var i;
					if (j.type === "folder") {
						i = e.$element.find(".u-tree-folder:eq(0)").clone().show();
						i.find(".u-tree-folder-name").html(j.name);
						i.find(".u-tree-loader").html(e.options.loadingHTML);
						var k = i.find(".u-tree-folder-header");
						k.data(j);
						if ("icon-class" in j) {
							k.find('[class*="icon-"]').addClass(j["icon-class"])
						}
					} else {
						if (j.type === "item") {
							i = e.$element.find(".u-tree-item:eq(0)").clone().show();
							i.find(".u-tree-item-name").html(j.name);
							i.data(j);
							if ("additionalParameters" in j && "item-selected" in j.additionalParameters && j.additionalParameters["item-selected"] == true) {
								i.addClass("u-tree-selected");
								i.find("i").removeClass(e.options["unselected-icon"]).addClass(e.options["selected-icon"])
							}
						}
					}
					if (f.hasClass("u-tree-folder-header")) {
						f.parent().find(".u-tree-folder-content:eq(0)").append(i)
					} else {
						f.append(i)
					}
				});
				e.$element.trigger("loaded")
			})
		},
		selectItem: function(e) {
			if (this.options.selectable == false) {
				return
			}
			var d = a(e);
			var g = this.$element.find(".u-tree-selected");
			var f = [];
			if (this.options.multiSelect) {
				a.each(g, function(i, j) {
					var h = a(j);
					if (h[0] !== d[0]) {
						f.push(a(j).data())
					}
				})
			} else {
				if (g[0] !== d[0]) {
					g.removeClass("u-tree-selected").find("i").removeClass(this.options["selected-icon"]).addClass(this.options["unselected-icon"]);
					f.push(d.data())
				}
			}
			if (d.hasClass("u-tree-selected")) {
				d.removeClass("u-tree-selected");
				d.find("i").removeClass(this.options["selected-icon"]).addClass(this.options["unselected-icon"])
			} else {
				d.addClass("u-tree-selected");
				d.find("i").removeClass(this.options["unselected-icon"]).addClass(this.options["selected-icon"]);
				if (this.options.multiSelect) {
					f.push(d.data())
				}
			}
			if (f.length) {
				this.$element.trigger("selected", {
					info: f
				})
			}
		},
		selectFolder: function(e) {
			var d = a(e);
			var f = d.parent();
			if (d.find("." + this.options["close-icon"]).length) {
				if (f.find(".u-tree-folder-content").children().length) {
					f.find(".u-tree-folder-content:eq(0)").show()
				} else {
					this.populate(d)
				}
				f.find("." + this.options["close-icon"] + ":eq(0)").removeClass(this.options["close-icon"]).addClass(this.options["open-icon"]);
				this.$element.trigger("opened", d.data())
			} else {
				if (this.options.cacheItems) {
					f.find(".u-tree-folder-content:eq(0)").hide()
				} else {
					f.find(".u-tree-folder-content:eq(0)").empty()
				}
				f.find("." + this.options["open-icon"] + ":eq(0)").removeClass(this.options["open-icon"]).addClass(this.options["close-icon"]);
				this.$element.trigger("closed", d.data())
			}
		},
		selectedItems: function() {
			var e = this.$element.find(".u-tree-selected");
			var d = [];
			a.each(e, function(f, g) {
				console.info(g);
				d.push(a(g).data())
			});
			return d
		}
	};
	a.fn.tree = function(e, g) {
		var f;
		var d = this.each(function() {
			var j = a(this);
			var i = j.data("tree");
			var h = typeof e === "object" && e;
			if (!i) {
				j.data("tree", (i = new b(this, h)))
			}
			if (typeof e === "string") {
				f = i[e](g)
			}
		});
		return (f === c) ? d : f
	};
	a.fn.tree.defaults = {
		multiSelect: false,
		loadingHTML: "<div>Loading...</div>",
		cacheItems: true
	};
	a.fn.tree.Constructor = b
})(window.jQuery);