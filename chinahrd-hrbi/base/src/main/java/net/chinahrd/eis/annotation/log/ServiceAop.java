package net.chinahrd.eis.annotation.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 拦截Service
 *
 * @author jxzhang on 2016年11月9日
 * @Verdion 1.0 版本
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAop {
    String description() default "";

    boolean writeDb() default true;
}
