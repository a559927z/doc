package net.chinahrd.resolver;

import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.auth.CurrentUserHolder;
import net.chinahrd.common.auth.InjectCurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;

/**
 * <p>
 *  controller api里如果有类型为CurrentUser的参数且有相关注解标志，将自动注入当前登录用户信息
 * </p>
 *
 * @author bright
 * @since 2019/3/14 15:03
 */
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        final Method method = methodParameter.getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        boolean hasInjectAnn = clazz.isAnnotationPresent(InjectCurrentUser.class)
                || method.isAnnotationPresent(InjectCurrentUser.class);
        boolean hasInjectParam = methodParameter.getParameterType().isAssignableFrom(CurrentUser.class);
        return hasInjectAnn && hasInjectParam;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return CurrentUserHolder.get();
    }
}
