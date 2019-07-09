<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>才报平台服务器</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="//cdn.bootcss.com/jquery/2.1.4/jquery.js"></script>
</head>
<body>

<div id="log-container" style="height: 450px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
    <div></div>
</div>

</body>
<script type="text/javascript">
    $(document).ready(
        function () {
            console.log(G_WEB_ROOT);
            var s = G_WEB_ROOT.replace("http://", "");
            // 指定websocket路径
            var websocket = new WebSocket('ws://' + s + '/tail/tomcat');
            websocket.onmessage = function (event) {
                // 接收服务端的实时日志并添加到HTML页面中
                $("#log-container div").append(event.data);
                // 滚动条滚动到最低部
                $("#log-container").scrollTop(
                    $("#log-container div").height()
                    - $("#log-container").height());
            };
        });
</script>
</html>

