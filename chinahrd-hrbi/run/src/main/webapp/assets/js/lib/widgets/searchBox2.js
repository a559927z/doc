/** 筛选条件 */
define(
		[ 'jquery', 'template' ],
		function($, tpl) {
			/*
			 * 模板start
			 * ---------------------------------------------------------------------------------
			 */
			var template = 
				'		<div class="u-searchBox-content<%= theme%>" >'
				+'			<div class="u-searchBox-content-top<%= theme%>">'
				+'				<b>筛选条件</b>'
				+'			</div>'
				+'			<div class="u-searchBox-content-bottom" style="height:<%= height%>px;overflow:auto;">'
				+'				<div class="u-searchBox-searchItem<%= theme%>">'
				+'				<% for(var i=0, len=data.length; i<len; i++) { %>'
				+'					<div class="u-searchBox-node">'
				+'					<% var id = data[i].id; var name = data[i].name; %>'
				+'						<div class="u-searchBox-title"><%=name%></div>'
				+'						<ul>'
				+'						<% for(var j=0, len2=data[i].childs.length; j<len2; j++) { %>'
				+'							<li><label class="checkbox-inline"><input type="checkbox" name="<%=id%>" value="<%:= data[i].childs[j].id %>" /><span><%:= data[i].childs[j].name %></span></label></li>'
				+'						<% } %>'
				+'						</ul>'
				+'					</div>'
				+'					<% } %>'
				+'				</div>'
				+'			</div>'
				+'		</div>';
			/*
			 * 模板end
			 * ---------------------------------------------------------------------------------
			 */
			var defaults = {
				url:'',
				theme: '',
				data : null,	// furnish public data
				onClick : function(id, name, type){},	// function(id, name, type) { }
				height : ''
			};

			$.fn.searchBox2 = function(options) {
				var opt = $.extend(true, {}, defaults, options), el = this;
				
				var init = function() {
					opt.id = $(el).attr('id');
					getData(renderCallbackFun);
				}
				
				var getData = function(renderCallbackFun) {
					$.getJSON(opt.url, function(json) {
						if(json!= null){
							opt.data = json;
							renderCallbackFun();
						}
					});
				}
				
				var renderCallbackFun = function() {
					tpl.LEFT_DELIMITER = '<%';
					tpl.RIGHT_DELIMITER = '%>';
					var html = tpl(template, opt);
					el.append(html);
					event();
				}
				var event = function(){
					$('.u-searchBox-searchItem'+opt.theme).on('click','input',function(){
						var id = $(this).attr('id');
						var name = $(this).next().text();
						var checked = $(this).is(':checked');
						 opt.onClick(id, name, checked );
					});
				}
				init();
			};
		});
