<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <meta charset="UTF-8">  
    <title>Document</title>  
    <style>  
    *{  
        margin: 0;  
        padding: 0;  
    }  
    .wrap,.circle,.percent{  
        position: absolute;  
        width: 75px;  
        height: 75px;  
        border-radius: 50%;  
    }  
    .wrap{  
        top:50px;  
        left:50px;  
        background-color: #ccc;  
    }  
    .circle{  
        box-sizing: border-box;  
        border:8px solid #ccc;  
        clip:rect(0,75px,75px,38px);  
    }  
    .clip-auto{  
        clip:rect(auto, auto, auto, auto);  
    }  
    .percent{  
        box-sizing: border-box;  
        top:-8px;  
        left:-8px;  
    }  
    .left{  
    -webkit-animation-duration: 2s;
        transition:transform ease;  
        border:8px solid blue;  
        clip: rect(0,38px,75px,0);  
    }  
    .right{  
    -webkit-animation-duration: 1s;
        border:8px solid blue;  
        clip: rect(0,75px,75px,38px);  
    }  
    .wth0{  
        width:0;  
    }  
    .num{  
        position: absolute;  
        box-sizing: border-box;  
        width: 60px;  
        height: 60px;  
        line-height: 60px;  
        text-align: center;  
        font-size: 20px;  
        left: 8px;  
        top: 8px;  
        border-radius: 50%;  
        background-color: #fff;  
        z-index: 1;  
    }  
    </style>  
    <script src="http://apps.bdimg.com/libs/zepto/1.1.4/zepto.min.js"></script>  
</head>  
<body>  
<div class="wrap">  
    <div class="circle">  
        <div class="percent left"></div>  
        <div class="percent right wth0"></div>  
    </div>  
    <div class="num"><span>0</span>%</div>  
</div>  
</body>  
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script>
require(['jquery', 'datetime'], function ($) {
	
	var percent=30;
		 if(percent>50){  
	            $('.circle').addClass('clip-auto');  
	            $('.right').removeClass('wth0');  
	        }  
	        $('.left').css("-webkit-transform","rotate("+(18/5)*percent+"deg)");  
	        $('.num>span').text(percent);  
});
</script>
<script>  
   
</script>  
</html>  