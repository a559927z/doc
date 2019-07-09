/** 导航菜单 */
define(
		[ 'jquery', 'template' ],
		function($, tpl) {
			/*
			 * 模板start
			 * ---------------------------------------------------------------------------------
			 */
			var template = 
				'<div id="<%:= id %>" class="u-nMenu-content" >'
				+ '	<ul class="dropdown-menu" style="display:block">'
				+ "<% for(var i=0, len=data.length; i<len; i++) { %>"
				+ '		<li class="u-nMenu-dropdown-submenu <%:= id %>_hasClick">'
				+ '			<a id="<%:= data[i].id %>" href="javascript:;"><%:= data[i].name %></a>'
				+ '<% 		if(data[i].sub != null) { %>'
				+ '				<ul class="dropdown-menu u-dropdown-menu">'
				+ '					<li class="u-nMenu-dropdown-submenu-right">'
				+ '<% 				for(var j=0, slen = data[i].sub.length; j<slen; j++) { %>'
				+ '						<a id="<%:= data[i].sub[j].id %>" href="javascript:;"><%:= data[i].sub[j].name %></a>'
				+ '<% 				} %>'
				+ '					</li>'
				+ '				</ul>'
				+ '<% 		} %>'
				+ '		</li>'
				+ '		<li class="u-nMenu-divider"></li>'
				+ '<% } %>'
				+ '	</ul>'
				+ '</div>		';
			/*
			 * 模板end
			 * ---------------------------------------------------------------------------------
			 */
			var defaults = {
				url:'',
				data : null,	// furnish public data
				onClick : null	// function(id, name) { }
			};

			$.fn.navMenu = function(options) {
				var opt = $.extend(true, {}, defaults, options), el = this;
				
				var init = function() {
					if(opt.data == null){
						getData(setup);
					} else{
						setup(event);
					}
				}
				var getData = function(setupCallbackFun) {
					$.getJSON(opt.url, function(json) {
						if(json!=null) {
							opt.data = json;
							setupCallbackFun(event);
						}else{
							opt.data = [];
						}
					});
				}
				
				var setup = function(eventCallbackFun) {
					el.wrap('<div class="u-nMenu-dropdown" style="position: relative;"></div>');
					//（重要属性）作用：区别不同的组件实例。让event知道针对哪个唯一的组件实例操作。
					opt.id = 'nMenu_'+el.attr('id');
					
					tpl.LEFT_DELIMITER = '<%';
					tpl.RIGHT_DELIMITER = '%>';
					var html = tpl(template, opt);
					
					el.after(html);
					
					//事件
					eventCallbackFun();
				}
				var event = function(){
					var item = $('.' + opt.id + '_hasClick');
					opt.onClick && item.on('click', 'a', function() {
						var a = $(this);
						var id = parseInt(a.attr("id"));
						var name = a.text();
						$('#' + opt.id).css('display','none');
						opt.onClick(id, name);
					});
				}
				init();
				
				var nMenu = {
						show : function() {
							el.click(function() {
									$('#'+opt.id).toggle();
							});
						}
				}
				return nMenu;
			};
		});