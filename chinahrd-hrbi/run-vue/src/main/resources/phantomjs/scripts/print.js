var system = require('system');
if (system.args.length < 3) {
	console.log('Usage: print.js -url -filename [-pdfSize] [-direction] [-zoom] [-timeout]');
	phantom.exit(1);
}

function mapArguments() {
	var args = {};
	for (var i = 0; i < system.args.length; i++) {
		if (system.args[i].charAt(0) === '-') {
			var key = system.args[i].substr(1);
			args[key] = system.args[i + 1];
		}
	}
	return args;
}

function waitFor(testFn, readyFn, timeOutMillis) {
	var startTime = new Date().getTime();
	var condition = false;
	var tid = window.setInterval(function() {
		if ((new Date().getTime() - startTime < timeOutMillis) && !condition) {
			condition = testFn();
			return;
		}
		// time out or test result is true
		window.clearInterval(tid);
		readyFn();
	}, 500);
}

function rasterize() {
	page.render(args.filename);
	console.log('complete');
	phantom.exit();
}

// map system arguments as json
var args = mapArguments();
var page = require('webpage').create();
// viewortSize effectively simulates the size of the window.
page.viewportSize = {
	width : 600,
	height : 600
};
// defines the size of the web page when rendered as a PDF.
if (args.filename && args.filename.substr(-4) === ".pdf") {
	args.pdfSize = args.pdfSize || 'A4';
	args.orientation = args.direction === 'horizontal' ? 'landscape' : 'portrait';
	page.paperSize = {
		format : args.pdfSize,
		orientation : args.orientation,
		margin : '1cm',
		footer : {
			height : '0.5cm',
			contents : phantom.callback(function(pageNum, numPages) {
				return '<h5><span style="float:right;font-size:10px;">' + pageNum + "/" + numPages + "</span></h5>";
			})
		}
	};
}
// specifies the scaling factor for the WebPage#render, default value is 1 (100%).
if (args.zoom) {
	page.zoomFactor = args.zoom;
}

/*
 * rasterize the web page to image or PDF.
 * --------------------------------------------------------------------------------------- */
page.open(args.url, function(status) {
	if (status !== 'success') {
		console.log('Unable to load the url!');
		phantom.exit();
	}
	var existsReadyFlag = page.evaluate(function() {
		return typeof readyToExport !== "undefined";
	});
	if (existsReadyFlag) {
		waitFor(function() {
			return page.evaluate(function() {return readyToExport;});
		}, rasterize, 5000);
	} else {
		window.setTimeout(rasterize, args.timeout || 200);
	}
});