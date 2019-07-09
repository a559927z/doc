package net.chinahrd.annotation;

import java.lang.annotation.*;

/**
 * 表 注解
 * 
 * @author htpeng 2017年4月14日下午4:07:40
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	/**
	 * 表名
	 * 
	 * @return
	 */
	String name();

	String primaryKey() default "";

	String user() default "";

	String dbname() default "";
}
