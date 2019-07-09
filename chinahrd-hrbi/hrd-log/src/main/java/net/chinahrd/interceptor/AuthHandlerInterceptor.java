package net.chinahrd.interceptor;

import net.chinahrd.dto.EmpExtendDto;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 受权拦截器
 *
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 403
     */
    final String unAuthorized = "/error/unAuthorized.do";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过
        if (isAuth(request)) {
            // 走拦截器链
            return true;
        }

        // 不通过重定向到403
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        response.sendRedirect(basePath + unAuthorized);

        // 不走拦截器链
        return false;
    }

    /**
     * 是否有认证权限
     * TODO 目录只做有登录的。有空可以扩展动态受权，如用户配受权userKey=authKey
     *
     * @param request
     * @return
     */
    private boolean isAuth(HttpServletRequest request) {
        EmpExtendDto empInfo = (EmpExtendDto) request.getSession().getAttribute("empInfo");
        if (null == empInfo) {
            return false;
        }
        return true;
    }
}
