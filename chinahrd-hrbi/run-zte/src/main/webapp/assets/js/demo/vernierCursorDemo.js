/**
 * Created by wqcai on 15/5/14.
 */
require(['jquery', 'vernierCursor'], function() {
	var options = {
		title: '主动流失率',
		width: 245,
		colors: 1,
		data:[0.05, 0.1, 0.15],
		value: 0.061,
		onClick: function(rs){
			console.log(rs);
		}
	}
	$('#cursor').vernierCursor(options);
});