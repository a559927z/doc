package net.chinahrd.mvc.interceptor;

import net.chinahrd.eis.permission.EisWebContext;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WEB端请求拦截器
 *
 * @author wqcai
 */
public class WebHandlerInterceptor implements HandlerInterceptor {
    protected static final Logger log = Logger.getLogger(WebHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
        if (!EisWebContext.isLogon()) return true;

        boolean bool = EisWebContext.checkUserPasswd(null);
        if (bool) {
            //没有登陆，转向登陆界面
            request.getRequestDispatcher("/toUpdatePasswd").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
