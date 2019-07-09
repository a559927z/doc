
/**
 * 分页，页码
 * @author 梁志威 2016-12-19
 * 说明：重新封装jboostrappage;使用的div需要有class="model-grid-pagination"
 * $(self.contentDivId).pagination({
 * 		otherparam : {},	//生成列表所需参数
 * 	    pageSize：''单页显示的行数
 * 	    pageNum: ''当前页
 * 		total: ''总条数
 * 	    url:''请求url路径
 * 		uiLoaderFunc：function 换页UI的切换方法
 * })
 * */
require(['jquery'], function($){
	$.fn.pagination = function(option){
		var $this = $(this);
		//page参数
		var c = {
	        	pageSize : 10,//单页显示条数
	        	total : 0,//总条数
	        	maxPages : 1,
	        	lastSelectedIndex : 1,
	        	selectedIndex : 1,
	        	minPageButton: 1,//当前状态的最小号
	        	maxPageButton: 5,//当前状态的最大号
	        	pageNum: 0,//当前页数
	        	url:null,//换页url
	        	uiLoaderFunc : null,//换页UI的切换方法
	        	otherparam: {}//分页请求的其他参数
	        };
		//组件
        var firstBtn, preBtn, nextBtn, lastBtn;
		/***使用参数原型存储方法和参数（私有）****/
        var fus = function(element,option){
        	if(option){
        		$.extend(c,option);
        		console.log(c);
        	}
        	this.init();
        }
        fus.prototype = {
        	init: function (){
        		var self = this;
                if (option) $.extend(c, option);
        		self.crateHtml();
        		self.bindALL();
        		self.initGoToPage();
        	},
        	/***获取到分页的html***/
        	crateHtml: function(){
            	$this.find('li').remove();
            	c.maxPages = Math.ceil(c.total/c.pageSize);
            	if(c.maxPages < 1) return;
            	var divid = $this.attr("id");
            	
            	//分页器分为左右两边，左：总览，右：页面序列和btn等
            	var rightside = 
            		  '<div class="pagination-group">'
					+ '<div class="left pager-operate">'
					+	'<ul class="mypagination" id="' + divid + 'PagerId">'
            		+	 '<li class="disabled"><a class="first" href="#">&laquo;</a></li>'
            		+	 '<li class="disabled"><a class="pre" href="#">上一页</a></li>'
            		+	 '{pageBtnsTmp}'
            		+ 	 '<li class="disabled"><a class="next" href="#">下一页</a></li>'
            		+ 	 '<li><a class="last" href="#">&raquo;</a></li>'
            		+ 	'</ul>'
            		+ '</div>'
            		
            		+ '<div class="right total-pagination">'
            		+   '第<input class="width-10 btn-margin" id="pagina_' + divid + '_pageText" type="text" value="1"/>页'
            		+ 	'共<span class="" id="' + divid + 'PagerNum">' + c.maxPages + '</span>页'
            		+ 	'<span class="" id="' + divid + 'PagerCount">' + c.total + '</span>条'
            		+ '</div>'
	            	+ '</div>';
        		var pageCount = c.maxPages < c.maxPageButton ? c.maxPages : c.maxPageButton;
        		var pNum = 0;
        		var pageBtnsTmp = '';
        		for(var index = 1; index <= pageCount; index++) {
        			pNum++;
        			pageBtnsTmp += '<li class="page" pNum="'+pNum+'"><a href="#" page="'+index+'">'+index+'</a></li>';
        		}
        		
        		rightside = rightside.replace("{pageBtnsTmp}", pageBtnsTmp);
        		$this.append(rightside);
        		if(c.maxPageButton > c.maxPages) {
        			$this.find('li a.next').parent().addClass("disabled");
            		$this.find('li a.last').parent().addClass("disabled");
            	}else {
            		$this.find('li a.next').parent().removeClass("disabled");
            		$this.find('li a.last').parent().removeClass("disabled");
            	}
        		
        		$this.find('li:nth-child(3)').addClass('active');
        		
        		firstBtn = $this.find('li a.first').parent();
        		preBtn = $this.find('li a.pre').parent();
        		lastBtn = $this.find('li a.last').parent();
        		nextBtn = $this.find('li a.next').parent();
            
        	},
        	mathPrePage: function(currButtonNum, currPage, maxPage, showPage){
    			var self = this;

            	if(maxPage < 1) return; 
            	
            	//选中的按钮大于中间数，就进一位
            	var middle = Math.ceil(showPage/2); // 4
            	// 4 > 3
            	// 5 - 4 + 3 
            	if(currButtonNum != currPage && currButtonNum < c.minPageButton && currButtonNum > 0) {
            		$this.find('li.page').remove();
            		
            		var startPages = 0;
            		var endPages = c.maxPages;
            		//前3个页
            		//后三个页
            		//未到达最后
            		if(currButtonNum < c.maxPages){
            			startPages = currButtonNum - 3;
            			endPages = currButtonNum + 3;
            		}
            		//到达最后
            		if(currButtonNum == c.maxPages){
            			startPages = currButtonNum - 3;
            			endPages = c.maxPages;
            		}
            		//防止起点小于1，终点大于最大页数
            		startPages = startPages < 1 ? 1:startPages;
            		endPages = endPages > c.maxPages ? c.maxPages:endPages;
            		
            		c.minPageButton = startPages;
            		c.maxPageButton = endPages;
            		var pNum = 0;
            		var html = '';
            		for(var index = startPages; index <= endPages; index++) {
            			pNum++;
            			html += '<li class="page" pNum="'+index+'"><a href="#" page="'+index+'">'+index+'</a></li>';
            		}
//            		console.log("$this.find('li:nth-child(2)') pre");
//            		console.log($this.find('li:nth-child(2)').html);
//            		console.log(html);
            		$this.find('li:nth-child(2)').after(html);
            		
            		self.bindPages();
            	}
        	},
        	mathNextPage: function(currButtonNum, currPage, maxPage, showPage){
    			var self = this;
        		if(maxPage < 1) return; 
            	var offsetRight = 2;

    			c.maxPageButton+=offsetRight+1;
            	var middle = showPage - 1; // 4
            	
            	if((currButtonNum != currPage+1 || maxPage > showPage) && currButtonNum >= middle) {
//            		console.log()
            		//显示后面2个按钮,每次进2位
            		//前3个页
            		//后三个页
            		//未到达最后
            		if(currButtonNum < c.maxPages){
            			startPages = currButtonNum - 3;
            			endPages = currButtonNum + 3;
            		}
            		//到达最后
            		if(currButtonNum == c.maxPages){
            			startPages = currButtonNum - 3;
            			endPages = c.maxPages;
            		}
            		//防止起点小于1，终点大于最大页数
            		startPages = startPages < 1 ? 1:startPages;
            		endPages = endPages >= c.maxPages ? c.maxPages+1:endPages;
            		
            		c.minPageButton = startPages;
            		c.maxPageButton = endPages-1;
            		
            		
            		var pNum = 0;
            		var html = '';
            		for(var index = startPages; index < endPages; index++) {
            			pNum++;
            			html += '<li class="page" pNum="'+index+'"><a href="#" page="'+index+'">'+index+'</a></li>';
            		}

//            		console.log("$this.find('li:nth-child(2)') last");
//            		console.log($this.find('li:nth-child(2)').html);
//            		console.log(html);
            		$this.find('li.page').remove();
            		$this.find('li:nth-child(2)').after(html);
            		
            		self.bindPages();
            	}
        	},
        	onClickPage: function(pageBtn, $_this){
    			var self = this;
            	if(c.uiLoaderFunc) {
            		var self = this;
    				$this.append(self.initLoadingFun());//添加一个正在加载的动画
    				var _url = c.url;
    				if(c.url != undefined && c.url != null && c.url != ''){
    					_url = c.url;
    				}
    				var paginationBase = {
    						'page': c.pageNum
    				};
//    				console.log(paginationBase);
//    				console.log(c.otherparam);
    				var _param = $.extend({},c.otherparam,paginationBase);//组合为包含分页参数”pageSize,pageNum“和请求页面的特定参数所组成的集合
//    				console.log(_param);
	    			$.ajax({
	    				url : _url,
	    				type : 'post',
	    				data : _param,
	    				async: false,
	    				success : function(da){
	    					$('.div-loading-img').remove();
//	    					console.log(da);
	    					c.uiLoaderFunc(da);//回调页面的加载新页面方法
	    				},
	    				error : function(){}
	    			});
            	}
            	else{
            		console.log("****************\n未设定换页UI方法！！")
            	}
            	
            	$this.find('li.active').removeClass('active');
            	pageBtn.parent().addClass('active');
            	if(c.selectedIndex > 1) {
            		if(preBtn.hasClass('disabled')) {
	            		firstBtn.removeClass("disabled");
	            		preBtn.removeClass("disabled");
//	            		console.log("rebind pre");
	            		self.bindFirsts($(this));
            		}
            	}else {
            		if(!preBtn.hasClass('disabled')) {
            			firstBtn.addClass("disabled");
            			preBtn.addClass("disabled");
            		}
            	}
            	
            	if(c.selectedIndex >= c.maxPages) {
            		if(!nextBtn.hasClass('disabled')) {
            			nextBtn.addClass("disabled");
            			lastBtn.addClass("disabled");
            		}
            	}else {
            		if(nextBtn.hasClass('disabled')) {
            			nextBtn.removeClass("disabled");
            			lastBtn.removeClass("disabled");

//	            		console.log("rebind last");
            			self.bindLasts($(this));
            		}
            	}
            	$("#pagina_" + $this.attr("id") + "_pageText").val(c.pageNum);
            },
            onPageBtnClick: function($_this){
    			var self = this;
            	var selectedText = $_this.text();
//            	console.log(selectedText);
//    			console.log("total:"+c.total+";pageNum"+c.pageNum+";selectedIndex:"+c.selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton+";lastSelectedIndex:"+c.lastSelectedIndex);
            	if(selectedText == undefined || selectedText == null || selectedText == '') return;
            	

            	c.lastSelectedIndex = c.selectedIndex;
            	var selectedBtn = $(this).find('li.active').find('a');
            	if(selectedText == '下一页'|| selectedText == '»') {
            		var selectedIndex = parseInt(selectedBtn.text());
            		var selectNum = 0;
            		if(selectedText != '»'){
            			selectNum = parseInt($this.find('li.active').attr('pNum'))+1;
            		}else{
            			selectNum  = c.maxPages
            		}
//            		console.log(selectNum);
            		if(selectNum > c.maxPageButton || selectedText == '»') 
            		{
            			selectedIndex = selectNum-1;
//            			console.log("mathNextPage:selectNum:"+selectNum+";selectedIndex:"+c.selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton);
            			self.mathNextPage(selectNum, c.selectedIndex, c.maxPages, c.maxPageButton);
            			selectedBtn = $this.find('li.page').find('a[page="'+(selectedIndex+1)+'"]');
            		}
            		if(selectNum <= c.maxPageButton){
//            			console.log("mathNextPage:selectNum:"+selectNum+";selectedIndex:"+c.selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton);
            			selectedBtn = $this.find('li.page').find('a[page="'+(selectNum)+'"]');
            		}
                	c.selectedIndex = selectNum;
            	}
            	else if(selectedText == '上一页' || selectedText == '«') {
            		var selectedIndex = parseInt(selectedBtn.text())-1;
            		var selectNum = 0;
            		if(selectedText != '«')
            			selectNum = parseInt($this.find('li.active').attr('pNum'))-1;
            		else
            			selectNum = 1;
            		if(selectNum < 1) selectNum = 1;
            		selectedIndex = selectNum+1;
//            		console.log("mathPrePage:selectNum:"+selectNum+";selectedIndex:"+selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton+";minPageButton:"+c.minPageButton);
//	        		console.log("mathPrePage:selectNum:"+selectNum+";selectedIndex:"+selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton+";minPageButton:"+c.minPageButton);
	        		self.mathPrePage(selectNum, selectedIndex, c.maxPages, c.maxPageButton);
	            	selectedBtn = $this.find('li.page').find('a[page="'+(selectedIndex-1)+'"]');
//            		alert(selectedBtn.text());
                	c.selectedIndex = selectNum;
            	}
            	else if(selectedText == '«'){
            		
            	}
            	else if(!isNaN(selectedText)){
            		//必须是数字
            		selectedBtn = $_this;
                	c.selectedIndex = parseInt(selectedBtn.text());
            	}
            	else{
            		return ;
            	}

            	c.pageNum = c.selectedIndex;
//            	console.log("pageNum:"+c.pageNum+";maxPages:"+c.maxPages);
            	self.onClickPage(selectedBtn, $_this);
            
            },
            bindPages: function(){     
    			var self = this;	
            	$this.find("li.page a").each(function(){
            		if($(this).parent().hasClass('disabled')) return;
//            		console.log("bindPages");
            		$(this).on('pageClick', function(e) {
            			self.onPageBtnClick($(this));
            		});
                });
            	
            	$this.find("li.page a").click(function(e){
                	e.preventDefault();
//                	console.log("trriger bindPages fire");
                	$(this).trigger('pageClick', e);
                });
            },
            bindFirsts: function($_this){ 
    			var self = this;
            	$this.find("li a.first,li a.pre").each(function() {
            		if($this.parent().hasClass('disabled')) return;
//            		console.log("bindPages");
            		
            		$(this).unbind('pageClick');
            		$(this).on('pageClick', function(e) {
            			self.onPageBtnClick($(this));
            		});
                });
            },
            bindLasts: function ($_this){ 
    			var self = this;
            	$this.find("li a.last,li a.next").each(function() {
            		if($(this).parent().hasClass('disabled')) return;
            		
            		$(this).unbind('pageClick');
            		$(this).on('pageClick', function(e) {
            			self.onPageBtnClick($(this));
            		});
                });
            },
            /***绑定所有按钮***/
            bindALL: function (){ 
    			var self = this;
    			var fir = 0;
            	$this.find("li.page a,li a.first,li a.last,li a.pre,li a.next").each(function() {
            		if($(this).parent().hasClass('disabled')) return;
            		
            		$(this).on('pageClick', function(e) {
            			self.onPageBtnClick($(this));
            		});
            		fir++;
                });
            	var second = 0;
                $this.find("li.page a,li a.first,li a.last,li a.pre,li a.next").click(function(e) {
                	e.preventDefault();
                	
                	if($(this).parent().hasClass('disabled')) return;
                	$(this).trigger('pageClick', e);
                	second++;
                });
            },
            //一个正在加载的动画
            initLoadingFun: function(){
				var _this = this;
				var div = '<div class="div-loading-img"></div>';
				return div;
			},
			//绑定跳转页数
			initGoToPage: function(){
				var self = this;
				$("#pagina_" + $this.attr("id") + "_pageText").unbind("keyup").keyup(function (e) {
	                if (e.keyCode == 13) {
	                    var v = $(this).val();
	                    if(!isNaN(v)){
	                    	if(v <= c.maxPages){
	                    		c.pageNum = v;
	                			var selectedBtn = $this.find('li.page').find('a[page="'+(c.pageNum)+'"]');
	                			//超出当前最大的按键
	                			if(v > c.maxPageButton){
		                        	c.selectedIndex = v-1;
//		                        	console.log("mathNextPage1:selectNum:"+v+";selectedIndex:"+c.selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton);
		                        	self.mathNextPage(v, c.selectedIndex, c.maxPages, c.maxPageButton);
		                        	selectedBtn = $this.find('li.page').find('a[page="'+(c.selectedIndex+1)+'"]');
	                			}
	                			//在最大页数以下
                        		if(v <= c.maxPageButton && v > c.minPageButton){
//		                        	console.log("mathNextPage2:selectNum:"+v+";selectedIndex:"+c.selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton);
                        			selectedBtn = $this.find('li.page').find('a[page="'+(v)+'"]');
                        		}
                        		//小于当前最小的按钮
                        		if(v <= c.minPageButton){

                            		if(v < 1) v = 1;
                            		c.selectedIndex = parseInt(v)+1;
//                            		console.log("mathPrePage1:selectNum:"+v+";selectedIndex:"+c.selectedIndex+";c.maxPages"+c.maxPages+";c.maxPageButton:"+c.maxPageButton+";minPageButton:"+c.minPageButton);
                        			self.mathPrePage(v, c.selectedIndex, c.maxPages, c.maxPageButton);
                	            	selectedBtn = $this.find('li.page').find('a[page="'+(c.selectedIndex-1)+'"]');
                        		}
                        		c.selectedIndex = v;
	                				
	                        	self.onClickPage(selectedBtn, $this);
	                    	}
	                    }
	                    	
	                }
	            });
			},
        };
        return new fus(this,option);
	}
});