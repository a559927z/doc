/**
 * Created by htpeng on 16/1/11.
 */
define(['template', 'moment'], function (tpl, moment) {
    if (!("zrw" in window)) {
        window.zrw = {}
    }

    var webRoot = G_WEB_ROOT;
    var Urls = {
        readMessageUrl: webRoot + '/message/readMessage',            //消息标记为已读
    }
    var defaults = {
        title: null,
        titleSuffix: '条',
        data: null,
        firstClick:true,
        onSubmit: null
    };


    window.zrw.checkContentLong = function(txt){
        var tLength = txt.length;
        var num = 0;
        var first = '',second = '',third='';
        for(var i = 0; i < tLength; i++){
            if(txt.charCodeAt(i) == 10){
                if(num == 0){ first = txt.substring(0,i); }
                if(num == 1){
                    second = txt.substring(first.length,i);
                    var thirdLength = first.length+second.length+1;
                    if(thirdLength < tLength){
                        third = txt.substring(thirdLength, tLength);
                    }
                }
                num++;
            }
        }
        return (num > 2) ||(third.length > 0) ||(num >= 0 && tLength > 42) || (num == 1 && first.length > 21 && second.length > 0) || (num == 1 && first.length > 0 && second.length > 21);
    }
    var template = '<div class="u-message">'
        + '    <% var dataLength = data.length; %>'
        + '    		<div class="u-message-title"><span class="u-message-title-small"><span class="u-message-title-num"><%=unreadNum%></span><%=titleSuffix%></span></div>'
        + '    		<div class="u-message-main" id="messageMain">'
        + '        		<% for(var i = 0; i < dataLength;i++){ %>'
        + '                    <% var className = "u-message-col"; %>'
        + '                    <% if(data[i].targetUrl!=null&&data[i].targetUrl!=""){ %>'
        + '                    <%	   className+=" gotoTarget";%>'   
        + '                    <% } %>'   
        + '                		<div class="<%=className%>" data-id="<%=data[i].id %>" data-index="<%=data[i].targetUrl%>">'
        + '                    		<span><%=data[i].userName%> ( <%=data[i].userNameCh%> )<%=data[i].createTime%></span>'
        + '                    		<% if(data[i].type==2){ %>'
        + '                    				<span>组织架构：<a class="organName"><%=data[i].organName%></a>，指标：<a class="organName"><%=data[i].target%></a></span>'
        + '                   		 <% } %>'    
        + '                   		 <div><%=data[i].content%></div>'
        + '               		 </div>'
        + '        		<% } %>'
        + '    		</div>'
        + '</div>';
    var content=  '		<div title="新消息" class="u-message-panel">'
    			 +'			<div class="icon-bell-message icon-animated-bell"></div>'
    			 +'			<div class="mark-num">0</div>'
    			 +'		</div>'
    	         +'     <% var dataLength = data.length; %>'
    	         +'     <% if(dataLength>0){ %>'
    			 +'			<div class="u-message-context pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">'
    		     + '    <% }else{ %>'
    		     +'			<div class="u-message-context-empty pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">'   		     
    		     + '    <% } %>'
    			 +'</div>';
    
    var templateEmpty =  '<div class="u-message-empty">'
    				+ '    		<div class="u-message-main">'
			        + '                		<div class="u-message-col">'
			        + '                   		 <div class="no-message">您当前没有未读消息</div>'
			        + '               		 </div>'
			        + '    		</div>'
			        + '</div>';
    $.fn.extend({
    	message: function (options) {
    		var self=this;
            var defaultHeight = 500;
            var _this = $(this);
            var opt = $.extend(defaults, options);
            opt.unreadNum = opt.data.length;
            tpl.LEFT_DELIMITER = '<%';
            tpl.RIGHT_DELIMITER = '%>';
            var html = templateEmpty;
            if(opt.unreadNum>0){
            	 html = tpl(template, opt);
            }
            _this.html(tpl(content, opt));
            _this.children(":last").html(html);
            var _icon=_this.children(":first");
            var _content=_this.children(":last");
            _icon.attr("title",opt.unreadNum+"条新消息");
            if(opt.unreadNum>0){
            	_icon.children(".icon-bell-message").removeClass("icon-animated-bell").addClass("icon-animated-bell");
            	_icon.children(".mark-num").show();
            	_icon.children(".mark-num").text(opt.unreadNum);
            }else{
            	_icon.children(".mark-num").hide();
            	_icon.children(".icon-bell-message").removeClass("icon-animated-bell");
            	
            	//_content.find("#messageMain").html('<div class="u-message-col">暂无未读信息</div>');
            }
            
            $.each(_content.find(".gotoTarget"),function(){
            	$(this).click(function(){
            		window.open(webRoot+"/"+$(this).attr("data-index"));
            	});
            });
            var _child = _this.children('.u-message-main');
            var height = 40;
//                $('.nav-tabs a').click(function (e) {
//                    e.preventDefault();     //阻止a链接的跳转行为
//                    $(this).tab('show');
//                });
            $(window).click(function(e){
            	 if(!_content.is(":hidden")){
            		 if(_icon.get(0)==e.target){
            			 return;
            		 }else{
            			 var bool=false;
            	    	   $.each(_icon.children(),function(){
            	    		   if($(this).get(0)==e.target){
            	    			   bool=true;
                      			 return;
                      		 }
                    	   });
            	    	   if(bool){
            	    		   return;
            	    	   }
            		 }
            		 
            		var div=$(e.target).parents(".u-message");
               		if(div.length==0){
               			_content.hide();
               		}
      		   }
            });
           if(!opt.id||opt.id==""){
        	   setTimeout(function(){
        		   _icon.unbind("click").click(function(){
//             		  console.log("click_icon:"+_content.is(":hidden"));
             		   if(!_content.is(":hidden")){
//             			   _content.hide();
             			  return; 
             		   }else{
             			   if(opt.unreadNum>0&&opt.firstClick){
              				  _content.css('right','30px');
                     		   _content.show();
                     		   if(_this.attr('data-status')!=1){
                     			   var record=defaultHeight;
	                   	           $.each(_content.find("#messageMain").children(), function (i, obj) {
	                   	        	   console.log($(obj).height());
	                   	        	  height += ($(obj).height()+5);
	                   	        	  if((record+$(obj).height())<defaultHeight){
	                   	        		  record+=($(obj).height()+5);
	                   	        	  }
	                   	           });
	                   	           
	                   	          
	                   	          	if (height > defaultHeight) {
	                   	          	    self.recordHeight=record;
	                   	          		_content.css('overflow-y','scroll');
	                   	          		_content.stop().animate({
	                   	          			height: record + 'px'
	                                     });
	                                 }else{
	                                	 self.recordHeight=height;
	                              	  // _content.css('overflow-y','hidden');
	                              	   _content.stop().animate({
	                                         height: height + 'px'
	                                     });
	                                 }
//                   	          _this.css("height",height);
	                   	          	$(_content.find(".u-message")).css("height",height);
                 				   _this.attr('data-status', 1);
                 			   }else{
                 				   _content.css('height','0');
                 				   _content.stop().animate({
                                      height: self.recordHeight + 'px'
                                  });
                 			   }
                     		  $.ajax({
                                  url: Urls.readMessageUrl,
                                  type:"post",
                                  data: opt.parm,
                                  success: function (result) {  
                                	  this.firstClick=false;
                                  }
                     		  });
                     		 
                     	   }else{
                     		   _content.show();
                     	   }
             			 
             			  
             		   }   
             	   });
        	   },1000);
           }
           return _content;
        }
    });

});
