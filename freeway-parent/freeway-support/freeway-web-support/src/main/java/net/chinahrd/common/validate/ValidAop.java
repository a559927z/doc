package net.chinahrd.common.validate;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.bean.CheckErrorResultBean;
import net.chinahrd.common.BaseResponse;
import net.chinahrd.enums.ResponseStatusEnum;
import org.apache.shiro.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author guanjian
 *
 */
@Slf4j
@Aspect
@Order(3)
@Component
public class ValidAop {
    ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(net.chinahrd.common.validate.MyValid)")
    public void exec() {
    }

    /**
     * 拦截器具体实现 在TOKEN 注解的方法前调用
     * 
     * @param pjp
     * @throws Throwable
     */
    @SuppressWarnings({ "unused", "rawtypes" })
    @Around("exec()")
    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable {
        // 获得切入目标对象
        Object target = pjp.getThis();
        // 获取方法参数值
        Object[] args = pjp.getArgs();

        MethodSignature ms = (MethodSignature) pjp.getSignature();
        // 当前方法
        Method method = ms.getMethod();
        // 方法的参数类型
        Object[] parameterTypes = ms.getParameterTypes();
        // 方法的参数名称
        String[] parameterNames = ms.getParameterNames();
        // 方法上的注解，Valid注解的相关信息 value group
        MyValid annotation = method.getAnnotation(MyValid.class);
        // 获取方法参数的注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        log.debug("注解验证：" + StringUtils.toString(parameterNames) + ":" + StringUtils.toString(args));
        if (annotation != null) {
            // 保存错误的list
            List<CheckErrorResultBean> errorList = new ArrayList<CheckErrorResultBean>();

            // 获取注解的value和group值
            Class<?>[] checkToken = annotation.value();
            Class<?>[] group = annotation.group();


            // valid有的value多个对象的情况
            for (Class c : checkToken) {
                // 对每一个参数对象进行校验
                for (Object obj : args) {
                    if (c == obj.getClass()) {
                        List<CheckErrorResultBean> result = null;
                        if (group.length < 1) {
                            result = ValidationUtil.validate(obj);
                        } else {
                            result = ValidationUtil.validate(obj, group);
                        }
                        if (result != null) {
                            errorList.addAll(result);
                        }
                    }

                }
            }

            // 简单对象的校验 如：String等
            if (checkToken == null || checkToken.length == 0) {
                // 执行校验，获得校验结果
                Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, args);
                String[] parameterNames2 = parameterNameDiscoverer.getParameterNames(method); // 获得方法的参数名称
                errorList = validResult.stream().map(constraintViolation -> {
                    PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath(); // 获得校验的参数路径信息
                    int paramIndex = pathImpl.getLeafNode().getParameterIndex(); // 获得校验的参数位置
                    String paramName = parameterNames2[paramIndex]; // 获得校验的参数名称
                    CheckErrorResultBean error = new CheckErrorResultBean(paramName, constraintViolation.getMessage()); // 将需要的信息包装成简单的对象，方便后面处理
                    return error;
                }).collect(Collectors.toList());
            }
            if (!errorList.isEmpty()) {
                BaseResponse<List<CheckErrorResultBean>> vo = new BaseResponse<List<CheckErrorResultBean>>();
                vo.setMessage(ResponseStatusEnum.PARM_CHECK_ERROR.getValue());
                vo.setCode(ResponseStatusEnum.PARM_CHECK_ERROR.getCode());
                vo.setData(errorList);
                log.debug(vo.toString());
                return vo;
            }
        }

        return pjp.proceed();

    }

    /**
     * 进行参数校验
     * @param obj
     * @param method
     * @param params
     * @return
     */
    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        ExecutableValidator validatorParam = ValidationUtil.getValidationUtil().getValidator().forExecutables();
        return validatorParam.validateParameters(obj, method, params);
    }

}
