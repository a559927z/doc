package net.chinahrd.interceptor;

import net.chinahrd.utils.VerifyPermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 受权拦截器
 *
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
public class DeleteOrUpdateHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private VerifyPermissionUtil verifyPermissionUtil;

    /**
     * 按钮无权限
     */
    final String unAuthorizedBtn = "/error/unAuthorizedBtn.do";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过
        if (verifyPermissionUtil.isAdminOrSuperAdmin(request) ||
                verifyPermissionUtil.isExportAttendanceMonthly(request)) {
            // 走拦截器链
            return true;
        }

        // 不通过重定向到403
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        response.sendRedirect(basePath + unAuthorizedBtn);

        // 不走拦截器链
        return false;
    }

}
