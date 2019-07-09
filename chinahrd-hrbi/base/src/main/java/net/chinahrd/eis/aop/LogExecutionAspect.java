package net.chinahrd.eis.aop;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.RemoteUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 系日志切入点,以正则表达式为切入点
 *
 * @author jxzhang on 2016年11月9日
 * @Verdion 1.0 版本
 */
@Aspect
@Component
public class LogExecutionAspect {

    @Autowired
    private HttpSession session;

    @Autowired
    private LogService logService;


    private static Logger logger = LoggerFactory.getLogger(LogExecutionAspect.class);

    // Dao层切点
    @Pointcut("execution(* net.chinahrd.*.mvc.pc.service..*Impl.*(..))")
    public void executionDaoApi() {
    }

    //	@Around("executionDaoApi()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {

        AopInformation aopInfo = packDto(pjp);
        aopInfo.setType(3);// 1，登录， 2 登出， 3，查询、修改、添加
        aopInfo.setWriteDb(false);

        long startTime = System.currentTimeMillis();

        Object object = pjp.proceed();// 执行该方法

        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
//        aopInfo.setUseTime(ArithUtil.div(useTime, 1000, 4));    // 转秒
        aopInfo.setUseTime(useTime);    // 毫秒

        System.out.println("=====环绕通知开始=====");
        logService.information(aopInfo);
        System.out.println("=====环绕通知结束=====");
        return object;
    }

    private AopInformation packDto(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        RbacUser rbacUser = EisWebContext.getCurrentUser();

        String packageName = joinPoint.getTarget().getClass().getName();
//		String[] split = StringUtils.split(packageName, ".");
//		String clazz = split.length > 0 ? split[split.length-1] : "";
        String method = joinPoint.getSignature().getName();

        AopInformation aopInfo = new AopInformation();
        aopInfo.setIpAddress(RemoteUtil.getRemoteIP(request));
        aopInfo.setUserId(rbacUser.getEmpId());
        aopInfo.setUserKey(rbacUser.getUserKey());
        aopInfo.setUserName(rbacUser.getUsername());
        aopInfo.setUserNameCh(rbacUser.getUserNameCh());
        aopInfo.setPackageName(packageName);
//		aopInfo.setClazz(clazz);
        aopInfo.setMethod(method);
        aopInfo.setMethodFull(packageName + "." + method + "()");
        return aopInfo;
    }

    // Controller层切点
    @Pointcut("execution(String net.chinahrd.*.mvc.pc.controller..*Controller.*View(..))")
    public void executionAspect() {
    }

    /**
     * 前置通知 用于监听Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("executionAspect()")
    public void doBefore(JoinPoint joinPoint) {


        AopInformation aopInfo = packDto(joinPoint);
        aopInfo.setType(3);// 1，登录， 2 登出， 3，查询、修改、添加
        aopInfo.setWriteDb(true);
        List<FunctionDto> function = CacheHelper.getFunction();
        for (FunctionDto functionDto : function) {
            String[] split = functionDto.getUrl().split("/");
            String url = split[split.length - 1];
            if (aopInfo.getMethod().equals(url)) {
                aopInfo.setDescription("跳转到" + functionDto.getFunctionName());
                break;
            }
        }
        logger.info("Aop信息:", aopInfo);
        logger.info("=====前置通知开始=====");
        logService.information(aopInfo);
        logger.info("=====前置通知结束=====");
    }


}
