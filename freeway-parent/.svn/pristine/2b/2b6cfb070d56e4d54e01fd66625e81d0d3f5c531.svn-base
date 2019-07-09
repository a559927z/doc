package net.chinahrd.common.log;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.SysLog;
import net.chinahrd.common.constant.WebConstant;
import net.chinahrd.service.log.SysLogService;
import net.chinahrd.utils.UuidUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

/**
 * TODO 根据实际情况完善
 *
 */
@Slf4j
@Aspect
@Configuration
public class LogAspect {
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal beginTime");
    private static final ThreadLocal<SysLog> sysLogThreadLocal = new NamedThreadLocal<>("ThreadLocal SysLog");
    private static final ThreadLocal<CurrentUser> currentUser = new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required = false)
    private HttpServletRequest request;

    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        return exec;
    }

    @Resource
    private SysLogService logService;

    /**
     * Controller层切点 注解拦截
     */
    @Pointcut("execution(* net.chinahrd.modules.*.web.*.*(..))")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * 
     * @param joinPoint
     *            切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）

        // 读取session中的用户
        HttpSession session = request.getSession();
        CurrentUser user = (CurrentUser) session.getAttribute(WebConstant.CURRENT_USER);
        currentUser.set(user);

    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     * 
     * @param joinPoint
     *            切点
     */
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        CurrentUser user = currentUser.get();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        boolean methodHasAnnotation = methodHasAnnotation(method);
        if(!methodHasAnnotation){
            return ;
        }

        if (user != null) {
            String type = "info"; // 日志类型(info:入库,error:错误)
            String remoteAddr = getIpAddr(request);// 请求的IP
            String requestUri = request.getRequestURI();// 请求的Uri
            String requestMethod = request.getMethod(); // 请求的方法类型(post/get)
            String remoteHost = request.getRemoteHost(); // 远程主机名称
            String useragent = request.getHeader("User-Agent"); // useragent
            String params="";
            try {
                if (!"POST".equals(requestMethod)) {
                    Map<String, String[]> paramsMap = request.getParameterMap(); // 请求提交的参数
                    params = JSON.toJSONString(paramsMap);
                } else {
                    InputStream inputStream = request.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                    String str = "";
                    while ((str = reader.readLine()) != null) {//一行一行的读取body体里面的内容；
                        params += str;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 打印JVM信息。
            long beginTime = beginTimeThreadLocal.get().getTime();// 得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis(); // 2、结束时间
            long usedTime = endTime - beginTime;
            if (log.isDebugEnabled()) {
                log.debug("URI: {}  耗时： {}", request.getRequestURI(), usedTime);
            }

            String opDesc = getControllerMethodRequestName(method);
            String opName = getApiDescription(method);
            if (StringUtils.isEmpty(opName)) {
                opName = opDesc;
            }

            SysLog sysLog = new SysLog();
            sysLog.setLogId(UuidUtil.uuid32());
            sysLog.setOpName(opName);
            sysLog.setOpDesc(opDesc);
            sysLog.setRequestMethod(requestMethod);
            sysLog.setLogType(type);
            sysLog.setRequestUri(requestUri);
            sysLog.setRemoteHost(remoteHost);
            sysLog.setRequestIp(remoteAddr);
            sysLog.setUserAgent(useragent);
            sysLog.setParams(params);
            sysLog.setUserName(user.getUserName());
            sysLog.setExecTime(usedTime);
            sysLog.setCreateInfo(user.getUserId());

            // 通过线程池来执行日志保存
            try {
                getThreadPoolTaskExecutor().execute(new SaveSysLogThread(sysLog, logService));
                sysLogThreadLocal.set(sysLog);
            } catch (Exception e) {
                log.debug("日志保存错误，{}", e);
            }
        }
    }
    
    /**
     * 异常通知 记录操作报错日志
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SysLog SysLog = sysLogThreadLocal.get();
        SysLog.setLogType("error");
        String errInfo = e.toString();
        if(!StringUtils.isEmpty(errInfo)){
            errInfo = org.apache.commons.lang3.StringUtils.substring(errInfo, 0, 2000);
        }
        SysLog.setErrorInfo(errInfo);
        new UpdateSysLogThread(SysLog, logService).start();
        clearLocal();
    }

    @AfterReturning(pointcut = "controllerAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {
        clearLocal();
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 方法是否有注解
     * @param method
     * @return
     */
    private static boolean methodHasAnnotation(Method method ){
        Annotation[] annotations = method.getAnnotations(); 
        return ArrayUtil.isNotEmpty(annotations);
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param method
     *            切点
     * @return discription
     */
    private static String getApiDescription(Method method ) {
        ApiLog apiLog = method.getAnnotation(ApiLog.class);
        if (apiLog != null) {
            return apiLog.title();
        }
        return null;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param method
     *            切点
     * @return discription
     */
    private static String getControllerMethodRequestName(Method method ) {
        
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        if (apiOperation != null && !StringUtils.isEmpty(apiOperation.value())) {
            return apiOperation.value();
        }

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null && !StringUtils.isEmpty(postMapping.name())) {
            return postMapping.name();
        }
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null && !StringUtils.isEmpty(getMapping.name())) {
            return getMapping.name();
        }

        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null && !StringUtils.isEmpty(requestMapping.name())) {
            return requestMapping.name();
        }
        return "";
    }

    private static void clearLocal() {
        beginTimeThreadLocal.remove();
        currentUser.remove();
        sysLogThreadLocal.remove();
    }

    /**
     * 保存日志线程
     */
    private static class SaveSysLogThread implements Runnable {
        private SysLog sysLog;
        private SysLogService logService;

        public SaveSysLogThread(SysLog SysLog, SysLogService LogService) {
            this.sysLog = SysLog;
            this.logService = LogService;
        }

        @Override
        public void run() {
            if(!log.isDebugEnabled() && this.logService != null){
                logService.insert(sysLog);
            }
        }
    }

    /**
     * 日志更新线程
     */
    private static class UpdateSysLogThread extends Thread {
        private SysLog sysLog;
        private SysLogService logService;

        public UpdateSysLogThread(SysLog SysLog, SysLogService LogService) {
            super(UpdateSysLogThread.class.getSimpleName());
            this.sysLog = SysLog;
            this.logService = LogService;
        }

        @Override
        public void run() {
            if(!log.isDebugEnabled() && this.logService != null){
                this.logService.updateById(sysLog);
            }
        }
    }
}
