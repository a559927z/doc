package net.chinahrd.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Title: AuthorizerAspect <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年4月29日 下午4:47:44
 * @Verdion 1.0 版本
 */
@Aspect
@Component
@Slf4j
public class AuthorizerAspect {

    @Autowired
    HttpServletRequest request;

    @Pointcut("@annotation(net.chinahrd.annotation.Authorizer)")
    public void aspect() {
    }

    @Before("aspect()")
    public void checkAuth(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class targetClass;
        try {
            targetClass = Class.forName(targetName);
            Object[] arguments = joinPoint.getArgs();
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Boolean isPass = (Boolean) request.getSession().getAttribute("isPass");
                    if (null == isPass || !isPass) {
                        throw new Exception("没有受权!!!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
