;(function($) {

	if (window.Tc == undefined) {
		window.Tc = {};
	}
	var Tc = window.Tc;
	
	
	//解决浏览器缓存
	Tc.timestamp = function(url) {
		// var getTimestamp=Math.random();
		var getTimestamp = new Date().getTime();
		if (url.indexOf("?") > -1) {
			url = url + "&timestamp=" + getTimestamp
		} else {
			url = url + "?timestamp=" + getTimestamp
		}
		return url;
	}

	//深度克隆对象
	Tc.cloneObj = function(obj){
		var str, newobj = obj.constructor === Array ? [] : {};
		if(typeof obj !== 'object'){
			return;
		} else if(window.JSON){
			str = JSON.stringify(obj), //系列化对象
				newobj = JSON.parse(str); //还原
		} else {
			for(var i in obj){
				newobj[i] = typeof obj[i] === 'object' ?
					cloneObj(obj[i]) : obj[i];
			}
		}
		return newobj;
	};

	/**
	 * 符号转换
	 */
	Tc.markChange= function(val){
		return val.replace(/[;,\s\n；，]/g, function(match,pos,originalText){
			switch(match){
			case ";" : return ",";
			case "^\s*$" : return ",";
			case "," : return ",";
			case " " : return ",";
			case "\n" : return ",";
			case "，" : return ",";
			case "；" : return ",";
			}
		});
	}
	/**
	 * 获取id
	 */
	Tc.splitId=function(getIdsUmIds){
		var ids = [];
		if(getIdsUmIds){
			for(var i=0; i<getIdsUmIds.length; i++){
				var id = getIdsUmIds[i].split("_")[0];
				ids.push(id);
			}
		}
		return ids;
	}
	/**
	 * 获取umId
	 */
	Tc.splitUmId=function(getIdsUmIds){
		var umIds = [];
		if(getIdsUmIds){
			for(var i=0; i<getIdsUmIds.length; i++){
				var umId = getIdsUmIds[i].split("_")[1];
				umIds.push(umId);
			}
			var umIdsStr = umIds.join(",");
		}
		return umIdsStr;
	}
	
	
	
	
	/**
	 * 格式化千位分割 val:值 comma:分割符
	 */
	Tc.formatNumber = function(val, comma) {
		comma = comma || ",";
		var reg = /(\d+)(\d{3})/;
		if(val==undefined || val == null) return ''; 
		var number = val.toString();
		while (reg.test(number)) {
			number = number.replace(reg, "$1" + comma + "$2");
		}
		return number;
	}
	/**
	 * 格式化浮点数字,并去除多余空格(默认保留2位)
     */
	Tc.formatFloat = function (val, num) {
		if (val && typeof(val) == 'number') {
			num = num | 2;
			return parseFloat((val).toFixed(num));
		} else {
			return 0;
		}
	}
    /**
     * 截取字符
     */
	Tc.limit = function(objString, num) {

		var objLength = objString.length;
		
		if (objLength > num) {
			return objString.substring(0, num) + "...";
		}
		return objString;
	}
	
	/**
	 * 解析字符串为Date
	 */
	Tc.parseToDate = function(str) {
		var arr;
		if (!str || !$.trim(str) || !( arr = str.match(/\d+/g)).length) {
			return new Date();
		}

		var ret = new Date();
		ret.setFullYear(arr[0]);
		if (arr.length > 1) {
			ret.setMonth(arr[1] - 1);
		}
		if (arr.length > 2) {
			ret.setDate(arr[2]);
		}
		return ret;
	};
	
	Tc.getYesterday = function(date) {
		var now = new Date();
		date = date || now;
		date.setDate(date.getDate()-1);
		return date;
	}
	Tc.getYesterdayInt = function(date) {
		return Tc.getYearMonth(Tc.getYesterday());
	}
	/**
	 * 获取最后每月的最后一天的日期
	 */
	Tc.getLastDay = function(date) {
		var now = new Date();
		date = date || now;
		if (date.getMonth() == now.getMonth()) {
			return date;
		}
		return new Date(date.getFullYear(), date.getMonth() + 1, 0);
	}

	/**
	 * 得到201405格式的日期类型
	 */
	Tc.getYearMonth = function(jsDate) {
		if (!jsDate) {
			return "";
		}
		var m = jsDate.getMonth() + 1;
		var y = jsDate.getFullYear();
		var sm = m < 10 ? ("0" + m) : m;
		return y + ""+ sm;
	};
	
	/**
	 * 得到201405格式的日期类型
	 */
	Tc.getYM = function(yearInt, MonthInt) {
		if (!yearInt || !MonthInt) {
			return "";
		}
		var MonthInt = MonthInt < 10 ? ("0" + MonthInt) : MonthInt;
		return yearInt + ""+ MonthInt;
	};
	
	/**
	 * 格式化日期
	 * pattern : Y代表年, m代表月, d代表天
	 */
	Tc.formatDate = function(jsDate, pattern) {
		if (!jsDate) {
			return "";
		}
		pattern = pattern || 'Y年m月d日';
		var y = jsDate.getFullYear();
		var m = Tc.padZero(jsDate.getMonth() + 1);
		var d = Tc.padZero(jsDate.getDate());
		return pattern.replace('Y', y).replace('m', m).replace('d', d);
	};
	
	/**
	 * 格式化yearMonth,将其按指定格式返回；如 :　ym＝201506,
	 * 1.pattern=Y年m月, 则结果为2016年6月; 
	 * 2.pattern=Y年mm月,则结果为2016年06月
	 * pattern : Y代表年, m或mm代表月（m:小于10时，不补0　；mm:小于10时，补0）,
	 */
	Tc.FormatYm = function(ym,pattern){
		ym += "";
		if(!ym || ym.length != 6){
			return "";
		}
		ym += "";
		pattern = pattern || 'Y年m月';
		var y = ym.substr(0,4);
		var m = ym.substr(4,2);
		if(pattern.indexOf('mm') > 0){
			return pattern.replace('Y', y).replace('mm', m);
		}else{
			return pattern.replace('Y', y).replace('m', parseInt(m));
		}
	};
	
	/**
	 * 往数字前填充0
	 * @param num 要填充的数字
	 * @param len 填充完成后的字符串长度
	 */
	Tc.padZero = function(num, len) {
		len = len || 2;
		
		var ret = "" + num;
		while(ret.length < len) {
			ret = "0" + ret;
		}
		return ret;
	}
	
	/**
	 * Loading
	 */
	$(document).ready(function() {
		var loading = $("<div class='f-loading'>").appendTo("body").hide();
		var requestCount = 0;
		loading.ajaxComplete(function(event, request, settings) {
			--requestCount <= 0 && $(this).hide();
			redirectWhenTimeout(request);
		});
		loading.ajaxStart(function() {
			requestCount++;
			$(this).show();
		});
		loading.ajaxError(function(event, request, settings, thrownError ) {
			console.error(settings.type + "请求[" + settings.url + "]时出错啦!参数: " + settings.data);
		});
	});
	
	function redirectWhenTimeout(request) {
		if (!request || request.status !== 201) {
			return;
		}
		var json = $.parseJSON(request.responseText);
		if (json && json._redirect) {
			var win = (window != top) ? top : window;
			win.location.href = json._redirect;
		}
	}
	/* aop 拦截ajax方法 */
	$.ajax = doBefore($.ajax, function(arg) {
		if(arg && arg.success) {
			arg.success = doBefore(arg.success, function(data) {
				if (data && data._redirect) {
					var win = (window != top) ? top : window;
					win.location.href = data._redirect;
				}
			});
		}
	});
	function doBefore(fn, beforeFn) {
		return function() {
			beforeFn.apply(this, arguments);
			fn.apply(this, arguments);
		};
	}
	
	String.prototype.trim=function(){
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
	
})(jQuery);

/**
 * Protect window.console method calls, e.g. console is not defined on IE
 * unless dev tools are open, and IE doesn't define console.debug
 */
(function() {
  if (!window.console) {
    window.console = {};
  }
  // union of Chrome, FF, IE, and Safari console methods
  var m = [
    "log", "info", "warn", "error", "debug", "trace", "clear"
  ];
  // define undefined methods as noops to prevent errors
  for (var i = 0; i < m.length; i++) {
    if (!window.console[m[i]]) {
      window.console[m[i]] = function() {};
    }    
  } 
})();

/**
 * ajax download plugin
 */
(function($) {
	var loading = null;
	
	$.download = function(url, data, fileName/* completeCallback*/) {
		if(!loading) {
			loading = $("<div class='f-overlay'><div class='f-overlay-txt'>导出中,请稍候...</div></div>").appendTo("body");
		}
		loading.show();		
		// callback
		checkDownloadComplete(/*completeCallback*/);
		doDownload(url, data, fileName);		
	}
	
	$.ajaxDownload = function(url, fileName) {
		doDownload(url, null, fileName);
	};
	
	function doDownload(url, data, fileName) {
		// iframe
		var iframe = $('#download_iframe');
		if (iframe.length === 0) {
			iframe = $("<iframe id='download_iframe' style='display:none' src='about:blank'></iframe>")
			.appendTo("body");
		}
		var iframeDoc = iframe[0].contentWindow || iframe[0].contentDocument;
		if (iframeDoc.document) {
			iframeDoc = iframeDoc.document;
		}
		iframeDoc.open();
		iframeDoc.write("<html><head></head><body><form method='POST'></form></body></html>");
		// bind value
		var dataInput = $("<input type='hidden' name='data'>");
		dataInput.val(JSON.stringify(data));
		var fileNameInput = $("<input type='hidden' name='fileName'>");
		fileNameInput.val(fileName);		
		var form = $(iframeDoc).find('form');
		form.attr("action", url);
		form.append(dataInput).append(fileNameInput);
		form.submit();
	}

	function checkDownloadComplete(/*completeCallback*/) {
		if (Cookie.pop('tcds_file')) {
			loading.hide();
			//completeCallback && completeCallback();
			return;
		}
		window.setTimeout(function() {
			checkDownloadComplete(/*completeCallback*/);
		}, 150);
	}

	var Cookie = Tc.Cookie = {
		get : function(name) {
			var cookie = document.cookie;
			if (cookie.length > 0) {
				var beginIndex = cookie.indexOf(name + "=");
				if (beginIndex !== -1) {
					beginIndex = beginIndex + name.length + 1;
					var endIndex = cookie.indexOf(";", beginIndex)
					if (endIndex === -1) {
						endIndex = cookie.length;
					}
					return unescape(cookie.substring(beginIndex, endIndex))
				}
			}
			return "";
		},
		remove : function(name) {
			document.cookie = name + "=;expires=" + new Date(0).toUTCString() + ";path=/";
		},
		pop : function(name) {
			var v = this.get(name);
			v && this.remove(name);
			return v;
		}
	};
})(jQuery);

function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}
/*
*  方法:Array.baoremove(dx)
*  功能:删除数组元素.
*  参数:dx删除元素的下标.
*  返回:在原数组上修改数组.
* splice方法见http://www.w3school.com.cn/js/jsref_slice_array.asp
*/
Array.prototype.baoremove = function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    this.splice(dx,1);
} 
