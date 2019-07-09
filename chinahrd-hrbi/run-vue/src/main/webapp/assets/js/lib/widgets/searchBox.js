/** 筛选条件 */
define(
		[ 'jquery', 'template' ],
		function($, tpl) {
			/*
			 * 模板start
			 * ---------------------------------------------------------------------------------
			 */
			var heandTemplate = 
				'<div type="button" class="u-searchBox-toggle">'
                +'	<span class="u-icon-bar"></span>'
                +'	<span class="u-icon-bar"></span>'
                +'	<span class="u-icon-bar"></span>'
                +'	<div class="u-searchBox-content u-searchBox-content-pos"></div>'
                +'</div>';
			var bodyTemplate = 
				'		<div>'
				+'			<div class="u-searchBox-content-top">'
				+'				<b>筛选条件</b>'
				+'			</div>'
				+'			<div class="u-searchBox-content-bottom">'
				+'				<div class="u-searchBox-searchBorder">'
				+'					<input type="text" value="可搜索" oninput="searchBoxReInit(<%=id%>)"  />'
				+'					<div class="u-icon-search" ></div>'
				+'				</div>'
				+'				<div class="u-searchBox-searchItem">'
				+'				<% for(var i=0, len=data.length; i<len; i++) { %>'
				+'					<div class="u-searchBox-node_<%= data[i].id %>">'
				+'					<% var id = data[i].id; var name = data[i].name; %>'
				+'						<div class="u-searchBox-title" id="<%=id%>" name="<%=name%>"><%=name%></div>'
				+'						<ul>'
				+'						<% for(var j=0, len2=data[i].sub.length; j<len2; j++) { %>'
				+'							<li><input type="checkbox" id="<%:= data[i].sub[j].id %>" /><span><%:= data[i].sub[j].name %></span></li>'
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
				data : null,	// furnish public data
				onClick : null	// function(id, name) { }
			};

			$.fn.searchBox = function(options) {
				var opt = $.extend(true, {}, defaults, options), el = this;
				
				var init = function() {
					opt.id = $(el).attr('id');
					render(event);
				}
				var render = function() {
					tpl.LEFT_DELIMITER = '<%';
					tpl.RIGHT_DELIMITER = '%>';
					var html = tpl(heandTemplate);
					el.append(html);
				}
				
				var getData = function(setDataCallbackFun) {
					$.getJSON(opt.url, function(json) {
						if(json!= null){
							opt.data = json;
							setDataCallbackFun(event);
						}
					});
				}
				var setData = function(eventCallbackFun){
					$('.u-searchBox-content').empty();
					tpl.LEFT_DELIMITER = '<%';
					tpl.RIGHT_DELIMITER = '%>';
					
					var html = tpl(bodyTemplate, opt);
					
					$('.u-searchBox-content').append(html);
					
					$('.u-searchBox-content').click(function(event){
						event.stopPropagation(); //阻止事件冒泡
					});
					
					//事件
					eventCallbackFun();
				}
				var event = function(){
					$('.u-searchBox-searchItem').on('click','input',function(){
						var checked = $(this).is(':checked');
						if(checked){
							var id = $(this).attr('id');
							var name = $(this).next().text();
							opt.onClick(id,name);
						}
					});
					
					var $searchInput = $('.u-searchBox-searchBorder input');
//					$searchInput.focus();
					searchEventHandle($searchInput, setData);
					searchDefValEventHandle($searchInput);
				}
				init();
				
				var searchEventHandle = function($searchInput, setDataCallbackFun){
					$('.u-icon-search').click(function() {
						if($searchInput.val() == ''){
							getData(setDataCallbackFun);
						}
						var searchName =$searchInput.val();
						var searchId = $('div[name^="'+searchName+'"]').attr('id');
						if(!searchId){return;}
						$.each(opt.data, function(i, item){
							if(searchId != item.id){
								$('.u-searchBox-node_'+item.id).remove();
							}
						});
					});
				}
				
				var searchDefValEventHandle = function($searchInput){
					var defVal = $searchInput.val();
					$searchInput.focus(function(){
						var thisVal = $(this).val();
						if(thisVal == defVal){$(this).val("");}
					});
					$searchInput.blur(function(){
						var thisVal = $(this).val();
						if(thisVal == ""){$(this).val(defVal);}
					});
				}
				var searchBox = {
						show : function() {
							var flag = true;
							el.click(function(event) {
								if(flag){
									getData(setData);
									flag = false;
								}
								$(event.target).find('.u-searchBox-content').toggle();
							});
						},
						reInit : function(){
							getData(setData);
						}
				}
				return searchBox;
			};
		});

function searchBoxReInit(elId) {
	if($('.u-searchBox-searchBorder input').val() == ''){
		$("#"+elId).empty();
		$("#"+elId).searchBox({url:url}).reInit();
		$(".u-searchBox-content").show();
	}
}