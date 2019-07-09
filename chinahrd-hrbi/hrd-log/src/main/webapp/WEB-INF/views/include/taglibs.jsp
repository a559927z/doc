<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
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

<script language="JavaScript">
    var version =<%=versionJson%>;
    var USER_KEY = '<%=session.getAttribute("userKey")%>';
    G_WEB_ROOT = "<%=basePath%>";
</script>


<c:set var="ctx" value="<%=basePath%>"/>
<c:set var="v" value="<%=versionObj%>"/>
<c:set var="reflesh" value="<%=reflesh%>"/>

<c:set var="zrwURL" value="http://www.chinahrd.net/"/>
<c:set var="outTr" value="http://www.our.tm"/>
<c:set var="hrbiURL" value="http://hrbi.chinahrd.net/"/>
<c:set var="eadURL" value="http://zrw.gs.chinahrd.net/"/>
<c:set var="nexusURL" value="http://nexus.gs.chinahrd.net/#welcome"/>
<c:set var="gitlabURL" value="http://gitlab.gs.chinahrd.net"/>
<c:set var="solrURL" value="http://solr.gs.chinahrd.net/index.html#/"/>
<c:set var="redisURL" value="${ctx}/redis/index.do"/>
<c:set var="svnURL" value="http://svn.gs.chinahrd.net/svn/tmp"/>
<c:set var="caibaoURL" value="http://caibao.chinahrd.net"/>