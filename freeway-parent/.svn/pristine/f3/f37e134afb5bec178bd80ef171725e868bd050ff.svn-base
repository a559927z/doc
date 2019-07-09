package net.chinahrd.deployment;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PlatformTenant;
import net.chinahrd.common.constant.ServiceConstant;
import net.chinahrd.request.BasePageRequest;
import net.chinahrd.request.BaseRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 *  自动切换数据源
 * </p>
 *
 * @author bright
 * @since 2019/3/13 14:34
 */
@Slf4j
@Aspect
@Order(-10) //使该切面在事务之前执行
@Component
public class SwitchDataSourceAspect {

    @Value("${freeway.deployment}")
    private String deployment;

    @Lazy
    @Autowired
    public CacheTenantManager cacheTenantManager;

    /**
     * 层切点 注解拦截
     */
    @Pointcut("@within(net.chinahrd.deployment.SwitchDataSource)")
    public void switchDataSource() {
    }

    /**
     * 使用around方式监控
     * 
     * @param point
     *            切点
     */
    @SuppressWarnings({ "unused", "rawtypes" })
    @Around("switchDataSource()")
    public Object aroundSwitch(ProceedingJoinPoint point) throws Throwable {
        if (ServiceConstant.DEPLOYMENT_SINGLE.equals(deployment)) {
            return point.proceed();
        }

        // 获取执行方法
        final Method method = getMethodByPoint(point);
        // 获取执行参数
        final Parameter[] params = method.getParameters();
        final Class<?> clazz = method.getDeclaringClass();

        String tenantId = null;
        CurrentUser currentUser = null;
        boolean hasSwitchAnn = clazz.isAnnotationPresent(SwitchDataSource.class)
                || method.isAnnotationPresent(SwitchDataSource.class);

        if (hasSwitchAnn) {
            for (int i = params.length - 1; i >= 0; i--) {
                Object arg = point.getArgs()[i];
                if (arg instanceof BaseRequest) {
                    currentUser = ((BaseRequest) arg).getCurrentUser();
                } else if (arg instanceof BasePageRequest) {
                    currentUser = ((BasePageRequest) arg).getCurrentUser();
                } else if (arg instanceof CurrentUser) {
                    currentUser = (CurrentUser) arg;
                }

                if (currentUser != null) {
                    tenantId = currentUser.getTenantId();
                    break;
                }
            }
        }

        try {
            if (StringUtils.isNotBlank(tenantId)) {
                TenantInfoHolder.setTenantId(tenantId);
                PlatformTenant platformTenant = cacheTenantManager.getPlatformTenant(tenantId);
                if (platformTenant != null) {
                    TenantInfoHolder.setDataSourceKey(platformTenant.getDatasourceId());
                }
            }
            return point.proceed();
        } finally {
            if (StringUtils.isNotBlank(tenantId)) {
                TenantInfoHolder.removeTenantId();
                TenantInfoHolder.removeDataSourceKey();
            }
        }
    }


    private Method getMethodByPoint(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }

}
