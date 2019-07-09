/** CardTable 卡片分页
 *
 * htpeng 2016-08-31
 * */

define(
    ['jquery'],
    function ($) {
    	var cardTabelObjs={};
        var defaults = {
            url: '',
            data:[],
            model:"",
            splitNum: 1,
            splitHeight:10,
            type:"get",
            height: 300,
            modelHeight:500,
            rows:20,
            horizontal:false,//默认垂直分页
            modelWidth:0,
            postData:{},
            newsData:0,
            loadComple: function (o) {
            },
            formatModel:function(el,o,index){
            	return $(el);
            },loadRowComple:function(el,o,index){
            	
            }
        };
        $.fn.clearCardTableData=function(options){
        	var cardTable=cardTabelObjs[$(this).attr("id")];
        	if(cardTable==undefined){
        		cardTable= new cardTabel(this, opt);
        	}
        	cardTable.clearCardTableData();
        	return cardTable;
        }
        $.fn.cardTabel = function (options) {
            var opt = $.extend(true, {}, defaults, options);
            var cardTabel = function (element, options) {
                this.init(element, options)
            };

            cardTabel.prototype = {
                _totalHeight:0,
                _height:0,
                _page:1,
                _totalPage:1,
                _currPage:1,
                _horizontalWidth:64,
                _loading:true,
                init: function (element, options) {
                	var o=cardTabelObjs[$(element).attr("id")];
                	if(o!=undefined){
                		alert("表格已经初始化");
                		return;
                	}
                	cardTabelObjs[$(element).attr("id")]=this;
                    $.extend(this, options);
                    this.el = element;
                    this.initModal();
                    if(this.horizontal){
                    	 $(this.el).css("height",this.height+"px")
                    	 $(this.el).addClass("card-table-horizontal");
                    }else{
                    	 $(this.el).css("height",this.modelHeight+"px")	
                    }
                   
                    this.getData();
                }, initModal: function () {
                	if(this.horizontal){
                		 var div = $("<div></div>");
                         div.attr("class", "card-table-horizontal-content");
//                         div.append(this.$content);
                         this.$component = div;
                         $(this.$component).css("height",this.modelHeight+"px")	
                	}else{
                		 var div = $("<div></div>");
                         div.attr("class", "col-md-12  col-xs-12 card-table-content");
//                         this.$content = $('<div class="form-horizontal"></div>');
//                         div.append(this.$content);
                         this.$component = div;
                         this.tdClassNum=12/this.splitNum;
                	}
                	  $(this.el).append(this.$component);
                   
                }, getData: function () {
                    var _ = this;
                    if(_.url==''){
                    	 _.renderData(_.data);
                    }else{
                    	_.loadPage(1,true);
                    }
                },_calHeight:function(data){
                	var _=this;
             
                	var total=data.records;
                	var totalPage=data.total;
//                    var rows=total/_.splitNum+(total%_.splitNum==0?0:1);
//                    _._totalHeight=rows*_.height+(rows*_.splitNum);
                    _._totalPage=totalPage;
                    //$(_.$component).css("height",_._totalHeight);
                    _.listener();
//                    $(_.el).append(_.$component);
                    
                }, renderData: function (data) {
                	if(data.rows==undefined){
                		data={"page" : 1,
                				  "row" : 10,
                				  "total" : 1,
                				  "records" : 0,
                				  "rows" : []
                		}
                	}
                    var _ = this;
                    var tr;
                    var len=_.data.length;
                    _.data=_.data.concat(data.rows);
                    if(_.data.length==0){
                    	this.empty();
                    	return ;
                    }
                    if(this.horizontal){
                    	$.each(data.rows,function(i,o){
                        	var tr= _.loadTr();
                    	//	tr.css("margin-right", _.splitHeight);
                    		tr.append(_.formatModel(_.model,o,len+i));
                      	 _._horizontalWidth+=_.modelWidth;
                      	 $(_.$component).css("width",_._horizontalWidth);
                    		$(_.$component).append(tr);
                    		_.loadRowComple(_.model,o,len+i);
                        })
                    }else{
                    	$.each(data.rows,function(i,o){
                        	var td= $("<div></div>");
                    		td.attr("class", "col-sm-"+_.tdClassNum);
                    		td.css("margin-top", _.splitHeight);
                    		td.append(_.formatModel(_.model,o,len+i));
                        	if(i%_.splitNum==0){
                        		tr=_.loadTr();
                        		$(_.$component).append(tr);
                        		_._height+=(_.height+_.splitHeight);
                        	}
                        	$(tr).append(td);
                        	_.loadRowComple(_.model,o,len+i);
                        })
                    }
                    
                },loadTr:function(className){
                	var tr= $("<div></div>");
                	
                	 if(this.horizontal){
                		 tr.attr("class", "");
                		 if(className){
                    		 tr.attr("class", "card-table-tr card-table-tr-horizontal "+className);
 	                   	}else{
 	                   		 tr.attr("class", "card-table-tr card-table-tr-horizontal");
 	                   	}
                	}else{
                		if(className){
                   		 	tr.attr("class", "card-table-tr "+className);
	                   	}else{
	                   		 tr.attr("class", "card-table-tr");
	                   	}
                	}
            		return tr;
                },
                listener:function(){
                	var _ = this;
                	 if(this.horizontal){
                		 $(_.el).scroll(function(e){
                   		  var $this =$(this);
                   	         var viewH =$(this).width();//可见高度
                   	         var contentH =$(this).get(0).scrollWidth;//内容高度
                   	         var scrollTop =$(this).scrollLeft();//滚动高度
                   	        //if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
                   	        if(!_._loading&&(contentH - viewH - scrollTop <= 100)&&_._currPage<_._totalPage){ //到达底部100px时,加载新内容
                         		_.loadPage(_._currPage+1);
                   	        }
                         });
                	 }else{
                		 $(_.el).scroll(function(e){
                   		  var $this =$(this);
                   	         var viewH =$(this).height();//可见高度
                   	         var contentH =$(this).get(0).scrollHeight;//内容高度
                   	         var scrollTop =$(this).scrollTop();//滚动高度
                   	        //if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
                   	        if(!_._loading&&(contentH - viewH - scrollTop <= 100)&&_._currPage<_._totalPage){ //到达底部100px时,加载新内容
                         		console.log("加载数据:"+_._currPage);
                         		_.loadPage(_._currPage+1);
                   	        }
                         });
                	 }
                	 
                },loadPage:function(page,reload){
                	var _=this;
                	var parm={rows:_.rows,page:page}
                	$.extend(parm, _.postData);
                	_._loading=true;
                	_.loadShade();
                	var time=new Date().getMilliseconds();
                	_.newsData=time;
                	$.ajax({
                        url: _.url,
                        type: _.type,
                        data: parm,
                        success: function (data) {
                        	if(time<_.newsData){
                        		return;
                        	}
                        	_.removeShade();
                        	_._currPage=page;
                        	if(reload){
//                        		_._currPage=page+1;
                                _._calHeight(data);
                        	}
                        	_.renderData(data);
                         	_._loading=false;
                        }
                    });
                },loadShade:function(){
                	this.removeEmpty();
                	if(this.horizontal){
                		var tr=this.loadTr("loadShade loadShade_horizontal");
                		$(tr).css("height",this.modelHeight+"px");
                    	var td= $("<div></div>");
                		td.attr("class", "loadShade_td");
                		td.html("<div class='loadShade_td_icon'></div><div class='loadShade_horizontal_td_text'>正<br/>在<br/>加<br/>载<br/>数<br/>据<br/>.<br/>.<br/>.</div>");
                    	tr.append(td);
                    	this._horizontalWidth+=64;
                    	$(this.$component).css("width",this._horizontalWidth);
                    	$(this.$component).append(tr);
                	}else{
                		var tr=this.loadTr("loadShade");
                    	var td= $("<div></div>");
                		td.attr("class", "col-sm-12 loadShade_td");
                		td.css("margin-top", _.splitHeight);
                		td.html("<div class='loadShade_td_icon_div'><div class='loadShade_td_icon'></div></div><div class='loadShade_td_text'>正在加载数据...</div>");
                    	tr.append(td);
                    	$(this.$component).append(tr);
                	}
                },empty:function(){
                	if(this.horizontal){
                		var tr=this.loadTr("loadShade loadShade_horizontal");
                		$(tr).css("height",this.modelHeight+"px");
                    	var td= $("<div></div>");
                		td.attr("class", "loadShade_td");
                		td.html("<div class='loadShade_horizontal_td_text'>暂<br/>无<br/>数<br/>据<br/></div>");
                    	tr.append(td);
                    	this._horizontalWidth+=64;
                    	$(this.$component).css("width",this._horizontalWidth);
                    	$(this.$component).append(tr);
                	}else{
                		var tr=this.loadTr("loadShade");
                    	var td= $("<div></div>");
                		td.attr("class", "col-sm-12 loadShade_td");
                		td.css("margin-top", _.splitHeight);
                		td.html("<div class='loadShade_td_icon_div'></div><div class='loadShade_td_text'>暂无数据</div>");
                    	tr.append(td);
                    	$(this.$component).append(tr);
                	}
                },removeEmpty:function(){
                	$(this.$component).children(".loadShade").remove();
                	if(this.horizontal){
                		this._horizontalWidth-=64;
                		if(this._horizontalWidth<64){
                			this._horizontalWidth=64;
                		}
                		$(this.$component).css("width",_._horizontalWidth);
                	}
                },removeShade:function(){
                	$(this.$component).children(".loadShade").remove();
                	if(this.horizontal){
                		this._horizontalWidth-=64;
                		if(this._horizontalWidth<64){
                			this._horizontalWidth=64;
                		}
                		$(this.$component).css("width",_._horizontalWidth);
                	}
                },
                clearCardTableData: function () {
                   this.data=[];
                   $(this.$component).html("");
                   if(this.horizontal){
                	   this._horizontalWidth=64;
                  	 $(this.$component).css("width",0);
                   }
                 
                },setCardTableParam:function(option){
                	this.postData={};
                	$.extend(this.postData, option.postData);
                	return this;
                },reloadCardTable:function(){
                	this.loadPage(1,true);
                }, findRow: function (id) {
                  
                }, clearAll: function () {
                    this.clear();
                }
            }

            return new cardTabel(this, opt);
        };
    });
