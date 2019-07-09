package net.chinahrd.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.auth.CurrentUserHolder;
import net.chinahrd.common.constant.WebConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author bright
 * @since 2019/3/14 11:46
 */
@Slf4j
public class CurrentUserInterceptor implements HandlerInterceptor {
    /**
     * 进入controller方法之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // TODO remove
        //  mock登录用户
        CurrentUser currentUser;
        if (request.getSession().getAttribute(WebConstant.CURRENT_USER) == null) {
            currentUser = new CurrentUser();
            currentUser.setUserId("12345");
            currentUser.setUserName("admin");
            currentUser.setTenantId("F54096CC86A9494DA345B74DD25AE5B0");
            request.getSession().setAttribute(WebConstant.CURRENT_USER, currentUser);
        } else {
            currentUser = (CurrentUser) request.getSession().getAttribute(WebConstant.CURRENT_USER);
        }
        CurrentUserHolder.set(currentUser);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    /**
     * 调用完controller之后，视图渲染之前
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    /**
     * 整个完成之后，通常用于资源清理
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        CurrentUserHolder.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
