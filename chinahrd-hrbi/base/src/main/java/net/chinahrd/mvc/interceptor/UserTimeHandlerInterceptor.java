package net.chinahrd.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.eis.aop.AopInformation;
import net.chinahrd.eis.aop.LogService;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.utils.RemoteUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
@Slf4j
public class UserTimeHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LogService logService;

    /**
     * 回车
     */
    private final String enter = "\r\n";

    /**
     * 当前线程池
     */
    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

    /**
     * 该方法将在Controller处理之前进行调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、开始时间
        long beginTime = System.currentTimeMillis();
        //线程绑定变量（该数据只有当前请求的线程可见）
        startTimeThreadLocal.set(beginTime);
        return super.preHandle(request, response, handler);
    }


    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行。");
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = startTimeThreadLocal.get();
        long useTime = endTime - beginTime;
        log.info("Aop信息:");
        log.info("=====前置通知开始=====");
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                //类
                String beanName = handlerMethod.getBean().getClass().toString();
                //方法名称
                String methodName = handlerMethod.getMethod().getName();
                if (methodName.equals("error") || methodName.equals("success")) {
                    log.error("方法名称错误");
                }
                //请求路径
                String uri = request.getRequestURI();
                //ip
                String remoteAddr = RemoteUtil.getIp(request);
                //请求方式
                String method = request.getMethod();
                String sbf = "";
                if (StringUtils.equals(method, "GET")) {
                    StringBuffer tmpSbf = new StringBuffer();
                    Map<String, String[]> pramMap = request.getParameterMap();
                    int count = 0;
                    String forCunt = "";
                    for (Map.Entry<String, String[]> entry : pramMap.entrySet()) {
                        forCunt = "[" + count + "]" + " : ";
                        tmpSbf.append("paramName" + forCunt + entry.getKey() + " - " + "paramValue" +
                                forCunt + request.getParameter(entry.getKey()) + "\n");
                        count++;
                    }
                    sbf = tmpSbf.toString();
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String message = "";
                    String tmpSbf = null;
                    while ((tmpSbf = br.readLine()) != null) {
                        message += tmpSbf;
                    }
                    sbf = message;
                }

                AopInformation aopInfo =
                        packDto(beginTime, endTime, beanName, methodName, uri, remoteAddr, method, sbf, useTime);
                logService.information(aopInfo);
            }
        } catch (Exception e) {
            //出错
            log.error("用户操作日志记录异常");
        }
        log.info("=====前置通知结束=====");
        super.afterCompletion(request, response, handler, ex);
    }


    /**
     * 组装AopInformation
     *
     * @return
     */
    private AopInformation packDto(
            long beginTime, long endTime, String beanName, String methodName, String uri, String remoteAddr, String method, String sbf, long useTime
    ) {
        RbacUser empInfo = EisWebContext.getCurrentUser();

        StringBuffer info = new StringBuffer();
        info.append(enter)
                .append("开始时间：" + new DateTime(beginTime).toString("yyyyMMdd HH:mm:ss:sss"))
                .append(enter)
                .append("类名：" + beanName)
                .append(enter)
                .append("方法名：" + methodName)
                .append(enter)
                .append("请求路径：" + uri)
                .append(enter)
                .append("地址：" + remoteAddr)
                .append(enter)
                .append("请求方式：" + method)
                .append(enter)
                .append("参数：" + sbf)
                .append(enter)
                .append("粍时======>：" + useTime + "ms")
                .append(enter)
                .append("结束时间：" + new DateTime(endTime).toString("yyyyMMdd HH:mm:ss:sss"))
        ;
        log.info(info.toString());

        AopInformation aopInfo = new AopInformation();
        aopInfo.setIpAddress(remoteAddr);
        aopInfo.setClazz(beanName);
        aopInfo.setMethod(method + ":    " + methodName);
        aopInfo.setParams(sbf);
        aopInfo.setPackageName(uri);
        aopInfo.setUseTime(useTime);
        aopInfo.setUserId(empInfo.getEmpId());
        aopInfo.setUserKey(empInfo.getUserKey());
        aopInfo.setUserName(empInfo.getUsername());
        aopInfo.setUserNameCh(empInfo.getUserNameCh());
        aopInfo.setType(3);// 1，登录， 2 登出， 3，查询、修改、添加
        aopInfo.setWriteDb(true);
        return aopInfo;
    }

}