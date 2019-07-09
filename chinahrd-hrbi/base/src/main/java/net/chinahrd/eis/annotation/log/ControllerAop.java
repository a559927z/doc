package net.chinahrd.eis.annotation.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 拦截Controller
 * 
 * @author jxzhang on 2016年11月9日
 * @Verdion 1.0 版本
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerAop {

	String description() default "";

	boolean writeDb() default true;

	/**
	 * 1，登录， 2 登出， 3，查询、修改、添加
	 *
	 * @return
	 */
	int type() default 3;
}
