<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="overview & stats">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon">
<!-- basic styles -->
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/assets/css/font-awesome/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/assets/css/jgGrid/ui.jqgrid-bootstrap.css">
<%--<link rel="stylesheet" href="${ctx}/assets/css/jqueryui/jquery-ui-1.10.3.custom.min.css">--%>
<!-- messenger styles -->
<link rel="stylesheet" href="${ctx}/assets/css/messenger/messenger.css">
<link rel="stylesheet" href="${ctx}/assets/css/messenger/messenger-theme-future.css">
<!--jqueryMCustomScrollBar-->
<link rel="stylesheet" href="${ctx}/assets/css/jquery-mCustomScrollbar/jquery.mCustomScrollbar.min.css">
<!-- zrw styles -->
<link rel="stylesheet" href="${ctx}/assets/css/zrw.min.css">
<link rel="stylesheet" href="${ctx}/assets/css/global.css">
<script>
    //basePath 定义在taglibs.jsp中
    G_WEB_ROOT = "<%=basePath%>";
    var iframe = window.parent.document.getElementById("mainFrame");
    var gotoBack = function () {
//     	alert();
    };
    if (iframe) {
        window.onclick = function () {
            window.parent.closeMessage();
        };
        gotoBack = window.parent.gotoRecordPage;
    } else if (typeof("gotoRecordPage") == "function") {
        gotoBack = gotoRecordPage;
    }
    addEventHandler(window, 'keydown', function (event) {
        if (event.keyCode == 8) {
            var elem = event.srcElement;
            var name = elem.nodeName;
//             console.log(name);
//             if(name!='INPUT' && name!='TEXTAREA'){  
//                 event.returnValue = false ;  
//                 gotoBack();
//                 return ;  
//             }  
//             var type_e = elem.type.toUpperCase();  
            if (name != 'INPUT' && name != 'TEXT' && name != 'NUMBER' && name != 'TEXTAREA' && name != 'PASSWORD' && name != 'FILE') {
                event.returnValue = false;
                gotoBack();
                return;
            }
            if (name == 'INPUT' && (elem.readOnly == true || elem.disabled == true)) {
                event.returnValue = false;
                gotoBack();
                return;
            }

        }
    });


    function addEventHandler(obj, eventName, fun, param) {
        var fn = fun;
        if (param) {
            fn = function (e) {
                fun.call(this, param);  //继承监听函数,并传入参数以初始化;
            }
        }
        if (obj.attachEvent) {
            obj.attachEvent('on' + eventName, fn);
        } else if (obj.addEventListener) {
            obj.addEventListener(eventName, fn, false);
        } else {
            obj["on" + eventName] = fn;
        }
    }

    var dataZoom = {
        show: true,
        realtime: true,
        y: 1,
        height: 20,
//        backgroundColor: 'rgba(255,255,255,0.5)',
//        dataBackgroundColor: 'rgba(255,255,240,0.5)',
//        fillerColor: 'rgba(211 ,211 ,211 ,0.6)',
//        handleColor: 'rgba(100,100,100,0.8)',
        zoomLock: true,   //当设置为true时选择区域不能伸缩
        showDetail: false,
        handleSize: 40,
        start: 0,
        end: 40
    };
</script>
<script src="${jsRoot}require.js"></script>
		