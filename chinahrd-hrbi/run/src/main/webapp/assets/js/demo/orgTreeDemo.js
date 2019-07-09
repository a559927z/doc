require([ 'organTreeSelector' ], function() {
	var treeSelector = $("#treeDemo").organTreeSelector({
		multiple : false,
		value : {
			'id' : 'fcb4d31b3470460f93be81cf1dd64cf0',
			'text' : '北京中人网信息咨询有限公司'
		},
		onSelect : function(ids, texts) {
			console.log(ids);
			console.log(texts);
		},
		params : {
			name : "test", // 自定义参数名称
			value : "22" // 传递的值
		}
	});
	$("#setVal").click(function() {
//		treeSelector.organTreeSelector('value', {});
		 treeSelector.organTreeSelector('value',{'id':'1111111','text':'textSetVal'});
	});
	$("#testVal").click(function() {
		var val = treeSelector.organTreeSelector("value");
		alert(val);
	});
	
//--------------------------------------------------
//		multiTree
//--------------------------------------------------
	$("#multiTree").organTreeSelector({
		multiple : true,
		showSelectBtn : false,
		onSelect : function(ids, texts) {
			console.log(ids);
			console.log(texts);
		}
	});
	$("#testVal2").click(function() {
		var val = $("#multiTree").organTreeSelector("value");
		console.info(val);
	});
	
});