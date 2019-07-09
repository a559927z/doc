require(['navMenu' ], function() {

	url = G_WEB_ROOT + "/assets/json/navMenu.json";

	// 导航菜单
	$('#tabBtn').navMenu({url : url}).show();
	
	// 可以通过其它组件提供json
	$.getJSON(url, function(json) {
		
		$('#tabBtn2').navMenu({
			data : json,
			onClick : function(id, name) {
				console.log("id：" + id, "name:" + name);
			}
		}).show();
		
	});
	
	// 组件完成json数据
	$('#tabBtn3').navMenu({
		url : url,
		onClick : function(id, name) {
			console.log("id：" + id, "name:" + name);
		}
	}).show();
	
	
});