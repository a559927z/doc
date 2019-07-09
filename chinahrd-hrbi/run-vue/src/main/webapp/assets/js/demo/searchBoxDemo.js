require([ 'searchBox' ], function() {

	url = G_WEB_ROOT + "/assets/json/searchBox.json";

	$("#sBoxId").searchBox({
		url : url,
		onClick : function(id, name) {
			console.log("id:" + id, "name:" + name);
		}
	}).show();

});