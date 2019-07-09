package net.chinahrd.common.auth;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.request.BasePageRequest;
import net.chinahrd.request.BaseRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 *  controller api里如果有类型基于BasePageRequest或者BaseRequest的参数，将自动注入当前登录用户信息到其currentUser属性里去
 * </p>
 *
 * @author bright
 * @since 2019/3/13 14:34
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class InjectCurrentUserAspect {

    /**
     * 层切点 注解拦截
     */
    @Pointcut("@within(net.chinahrd.common.auth.InjectCurrentUser)")
    public void inject() {
    }

    /**
     * 使用around方式监控
     * 
     * @param point
     *            切点
     */
    @SuppressWarnings({ "unused", "rawtypes" })
    @Around("inject()")
    public Object aroundInject(ProceedingJoinPoint point) throws Throwable {
        // 获取执行方法
        final Method method = getMethodByPoint(point);
        // 获取执行参数
        final Parameter[] params = method.getParameters();
        final Class<?> clazz = method.getDeclaringClass();

        boolean hasInjectAnn = clazz.isAnnotationPresent(InjectCurrentUser.class)
                || method.isAnnotationPresent(InjectCurrentUser.class);

        if (hasInjectAnn) {
            for (int i = params.length - 1; i >= 0; i--) {
                Object arg = point.getArgs()[i];
                if (arg instanceof BaseRequest) {
                    ((BaseRequest) arg).setCurrentUser(CurrentUserHolder.get());
                } else if (arg instanceof BasePageRequest) {
                    ((BasePageRequest) arg).setCurrentUser(CurrentUserHolder.get());
                }
            }
        }

        return point.proceed();
    }


    private Method getMethodByPoint(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }

}
