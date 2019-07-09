package net.chinahrd.eis.permission.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;

import net.chinahrd.core.web.Servlets;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.exception.ShiroAuthenticationException;
import net.chinahrd.eis.permission.model.RbacRole;
import net.chinahrd.eis.permission.model.RbacUser;

/**
 * 扩展 自FormAuthenticationFilte:
 */
public class ShiroAuthenticationFilter extends FormAuthenticationFilter {

    private static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

    private final static Log log = LogFactory.getLog(ShiroAuthenticationFilter.class);

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response)
                                    		 throws Exception {
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (false == Servlets.isAjaxRequest(httpRequest)) {
            issueSuccessRedirect(request, response);
            return true;
        }
        
        response.setCharacterEncoding("UTF-8");
        
//        WebUtils.getAndClearSavedRequest(httpRequest);
//        WebUtils.redirectToSavedRequest(request, response, redirectUrl);

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String inputPwd = String.valueOf(userToken.getPassword());
        boolean bool = EisWebContext.checkUserPasswd(inputPwd);
        RbacUser user = null;
        Object principal = subject.getPrincipal();
        if (principal instanceof RbacUser) {
            user = (RbacUser) principal;
        }
        
		if (user.getSysDeploy() == 1) {
			getRedirectUrl(1, httpRequest, response);
		} else {
			List<RbacRole> rbacRoles = user.getRbacRoles();
			if(null == rbacRoles || rbacRoles.size() == 0){
				getRedirectUrl(3, httpRequest, response);
			}else if(bool){
				httpRequest.getSession().setAttribute("userKey", user.getUserKey());
				getRedirectUrl(2, httpRequest, response);
			}else{
				getRedirectUrl(1, httpRequest, response);
			}
		}
		// 直接处理了成功重定向，防止链的持续
		return false;
    }
    
	/**
	 * 重写onLoginSuccess方法，核心就是不去spring-shiro.xml，匹配successUrl属</br>
	 * json伪装response.sendRedirect(url)重定向。 </br>
	 * 封装json对象属性redirectUrl让jsp重新请求。 </br>
	 * 
	 * PS ： 这里算是onLoginSuccess了所以还对shiroSession对像清空 </br>
	 * 
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author jxzhang by 2016-12-12
	 */
	private void getRedirectUrl(int type, HttpServletRequest httpRequest, ServletResponse response) throws IOException {

		String basePath = net.chinahrd.utils.WebUtils.getBasePath(httpRequest);
		String redirectUrl = httpRequest.getContextPath();

		if ("".equals(redirectUrl))
			redirectUrl = "/";

		PrintWriter out = response.getWriter();
		StringBuilder json = new StringBuilder();

		switch (type) {
		case 1: // 成功
			json.delete(0, json.length());
			json.append("{\"success\":true,");
			json.append("\"_redirectUrl\":\"").append(redirectUrl).append("\"}");
			break;
		case 2: // 跳转到修改密码页面
			json.delete(0, json.length());
			json.append("{\"success\":true, \"toUpdatePasswd\":true, ");
			json.append("\"_redirectUrl\":\"").append(basePath + "/toUpdatePasswd").append("\"}");
			break;
		case 3: // 没有角色
			json.delete(0, json.length());
			json.append("{\"success\":true, \"roleIsNull\":true } ");
			if (SecurityUtils.getSubject().isAuthenticated()) {
				SecurityUtils.getSubject().logout();
			}
			break;
		}
		out.print(json.toString());
		out.flush();
		out.close();
	}
    
    

    @SuppressWarnings("unused")
    private String getRedirectUrlFromSavedRequest(ServletRequest request,
                                                  ServletResponse response) throws IOException {
        String successUrl = null;
        SavedRequest savedRequest = org.apache.shiro.web.util.WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
            successUrl = savedRequest.getRequestUrl();
        }
        if (successUrl == null) successUrl = getSuccessUrl();
        if (successUrl == null) {
            throw new IllegalStateException(
                    "Success URL not available via saved request or via the "
                            + "successUrlFallback method parameter. One of these must be non-null for "
                            + "issueSuccessRedirect() to work.");
        }
        return successUrl;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        if (e.getCause() != null) {
            log.error("Login failure", e.getCause());
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (false == Servlets.isAjaxRequest(httpRequest)) {
            setFailureAttribute(request, e);
            return true;
        }

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String errorCode = UNKNOWN_ERROR;
            if (e instanceof ShiroAuthenticationException) {
                errorCode = ((ShiroAuthenticationException) e).getCode().name();
            }
            StringBuilder json = new StringBuilder();
            json.append("{\"errCode\":\"").append(errorCode).append("\"}");
            out.print(json.toString());
            out.flush();
            out.close();
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        if (false == Servlets.isAjaxRequest((HttpServletRequest) request)) {
            return super.onAccessDenied(request, response);
        }

        if (isLoginRequest(request, response)) {
            return executeLogin(request, response);
        }

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String url = Servlets.getBaseUrl((HttpServletRequest) request) + getLoginUrl();
        out.print("{\"_redirectUrl\":\"" + url + "\"}");
        out.flush();
        out.close();
        // 统一返回false，不去spring-shiro.xml，匹配successUrl属性
        return false;
    }

}
