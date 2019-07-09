<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    boolean isPopOut = false;
    String domain = "links.net";
    String cookieName = "versionNum";
    String versionNum = "v2.6.0";

    // 有没有最新的版本cookie
    String value = "";
    Cookie[] cookies = request.getCookies();
    if (null != cookies) {
        for (int i = 0; i < cookies.length; i++) {
            if (cookieName.equals(cookies[i].getName())) {
                value = cookies[i].getValue();
            }
        }
    }

    if (value != "" && value.equals(versionNum)) {
        isPopOut = false;
    } else {
        isPopOut = true;
        Cookie cookie = new Cookie(cookieName, versionNum);
//        cookie.setDomain(domain);
        // jdk1.8
//        cookie.setHttpOnly(isHttpOnly);
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }

%>

<script language="JavaScript">
    var isPopOut =<%=isPopOut%>;
</script>


<c:set var="sysVersion" value="当前版本：v2.6.0（更新时间：2019-02-15）"/>