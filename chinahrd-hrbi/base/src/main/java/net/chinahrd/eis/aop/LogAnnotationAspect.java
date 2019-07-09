package net.chinahrd.eis.aop;

import net.chinahrd.eis.annotation.log.ControllerAop;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.RemoteUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 系日志切入点,以自定义标签为切入点
 *
 * @author jxzhang on 2016年11月9日
 * @Verdion 1.0 版本
 */
@Aspect
@Component
public class LogAnnotationAspect {

    @Autowired
    private LogService logService;
    
    // 本地异常日志记录对象
    private static Logger logger = LoggerFactory.getLogger(LogAnnotationAspect.class);

    private static List<Object> AopArgs = CollectionKit.newList();

    // Service层切点
    @Pointcut("@annotation(net.chinahrd.eis.annotation.log.ServiceAop)")
    public void serviceAspect() {
    }

    // Controller层切点
    @Pointcut("@annotation(net.chinahrd.eis.annotation.log.ControllerAop)")
    public void annotationAspect() {
    }

    @Around("serviceAspect()")
    public Object doBefore4Service(ProceedingJoinPoint pjoinPoint) throws Throwable {
        Object obj = pjoinPoint.proceed();
        return obj;
    }

    /**
     * 前置通知 用于监听Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("annotationAspect()")
    public void doBefore(JoinPoint joinPoint) {

        try {
            AopInformation aopInfo = packDto(joinPoint);

            LogAnnotationAspect.AopArgs = getControllerAopArgs(joinPoint);
            aopInfo.setDescription((String) LogAnnotationAspect.AopArgs.get(0));
            aopInfo.setWriteDb((boolean) LogAnnotationAspect.AopArgs.get(1));
            aopInfo.setType((int) LogAnnotationAspect.AopArgs.get(2));


            logger.info("Aop信息:", aopInfo);
            logger.info("=====前置通知开始=====");
            logService.information(aopInfo);
            logger.info("=====前置通知结束=====");

        } catch (Exception e) {
            // 记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解所有参数
     *
     * @param joinPoint 切点
     * @return 所有参数
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    private static List<Object> getControllerAopArgs(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    List<Object> args = CollectionKit.newList();
//					if(arguments.length > 0){
//						String params = "";
//						for (Object object : args) {
//							params +=object.getClass().getSimpleName() + "、" ;
//						}
//					}
                    args.add(method.getAnnotation(ControllerAop.class).description());
                    args.add(method.getAnnotation(ControllerAop.class).writeDb());
                    args.add(method.getAnnotation(ControllerAop.class).type());
                    return args;
                }
            }
        }
        return null;
    }

    private AopInformation packDto(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RbacUser rbacUser = EisWebContext.getCurrentUser();

        String packageName = joinPoint.getTarget().getClass().getName();
        String[] split = StringUtils.split(packageName, ".");
        String clazz = split.length > 0 ? split[split.length - 1] : "";
        String method = joinPoint.getSignature().getName();

        AopInformation aopInfo = new AopInformation();
        aopInfo.setIpAddress(RemoteUtil.getRemoteIP(request));
        aopInfo.setUserId(rbacUser.getEmpId());
        aopInfo.setUserKey(rbacUser.getUserKey());
        aopInfo.setUserName(rbacUser.getUsername());
        aopInfo.setUserNameCh(rbacUser.getUserNameCh());
        aopInfo.setPackageName(packageName);
        aopInfo.setClazz(clazz);
        aopInfo.setMethod(method);
        aopInfo.setMethodFull(packageName + "." + method + "()");
//		aopInfo.setParams(params);
        return aopInfo;
    }
}
