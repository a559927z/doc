<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

// Object version=request.getAttribute("version");
    Object versionObj = request.getAttribute("versionMap");
    if (versionObj == null) {
        versionObj = new Object();
    }
    String versionJson = "{}";
    if (request.getAttribute("versionJson") != null) {
        versionJson = request.getAttribute("versionJson").toString();
    }
	long reflesh = System.currentTimeMillis();
%>
<c:set var="ctx" value="<%=basePath%>"/>
<c:set var="jsRoot" value="<%=basePath + \"/assets/js/\" %>"/>
<c:set var="mjsRoot" value="<%=basePath + \"/assets/mobile/js/\" %>"/>
<c:set var="v" value="<%=versionObj%>"/>
<c:set var="reflesh" value="<%=reflesh%>"/>

<script>
    var version =<%=versionJson%>;
</script>

<script language ="JavaScript">
	var USER_KEY='<%=session.getAttribute("userKey")%>';
</script>