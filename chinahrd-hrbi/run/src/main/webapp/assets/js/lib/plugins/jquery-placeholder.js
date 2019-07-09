/*
 * placeholder 组件
 * 2013-11-25 马超 创建（暂时不支持自定义参数，需要时再增加）
 * 使用方法：
   <span><input type="text" myholder="这是placeholder文案" /></span>
   组件修改为(不支持placeholder的浏览器)：
   <span class="holderBox"><input type="text" myholder="这是placeholder文案" /><i class="placeholder">这是placeholder文案</i></span>
   请使用样式定位覆盖到输入框上，实现类似效果，由于样式差异不好统一，这里不提供任何默认样式，一个建议的样式是：
   .holderBox{position:relative;}
   .holderBox .placeholder{position:absolute;z-index:2;left:6px;top:3px;color:#979797;}
 */
(function($){
//IE10的placeholder实现跟别的浏览器不一致，效果不好
//jquery1.9版本后移除了$.browser.msie，故改为/msie/.test(navigator.userAgent.toLowerCase())
var supportPlaceholder = ! (/msie/.test(navigator.userAgent.toLowerCase())) && ('placeholder' in document.createElement('input')),
	checkTimer = null,
	checkQueue = [],
	check = function(){
		var i = 0, input, holder;
		for(; i<checkQueue.length; i++){
			input = checkQueue[i].input;
			holder = checkQueue[i].holder;
			if( !input[0] || !holder[0] ){ //dom节点不存在，就删除
				checkQueue.splice(i, 1);
			}else{
				holder[ $.trim(input.val()).length ? "hide" : "show" ]();
			}
		}
		//没有内容就停止计时器
		if( checkQueue.length == 0 && checkTimer ){
			window.clearInterval(checkTimer);
			checkTimer = 0;
		}
	},
	pushTask = function( input, holder ){ // dom为jquery对象
		if( !checkTimer )checkTimer = window.setInterval(check, 200);
		//dom排重
		var n = checkQueue.length, i = 0;
		for(; i<n; i++){
			if( checkQueue[i].input[0] == input[0] ) return;
		}
		checkQueue.push({input:input,holder:holder});
	};

var makePlaceHolder = function( dom ){
	return $(dom).each(function(){
		var me = $(this), input = me[0].tagName.toLowerCase() == "input" ? me : me.find("input"), holder;
		if( input[0] ){
			holder = input.attr("placeholder") || "";
			if( holder ){
				if( supportPlaceholder ){
					return;
				}
				//模拟placeholder
				input.after("<span class='placeholder'>"+ holder +"</span>");
				//查找关键元素，提高效率
				var box = input.parent().addClass("holderBox"), iholder = box.find(".placeholder");
				input.bind("keypress keyup",function(){
					iholder[ $.trim(this.value).length ? "hide" : "show" ]();
				});
				iholder.bind("click mousedown mouseup", function(e){
					e.stopPropagation();
					switch(e.type){
						case "mousedown":
						case "mouseup":
							input.triggerHandler(e.type);
							break;
						case "click":
							input.trigger(e.type);
							input[0].focus();
							break;
					}
				});
				pushTask(input, iholder);
			}
		}
	});
};
/*
 * 对外接口
 */
$.fn.placeholder = function(){
	if(supportPlaceholder) { return this;};
	return makePlaceHolder( this );
};
})(jQuery);