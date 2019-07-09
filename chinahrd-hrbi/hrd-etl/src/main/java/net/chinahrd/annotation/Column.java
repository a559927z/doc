package net.chinahrd.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import net.chinahrd.utils.version.configure.ColumnType;

/**
 * 列注解
 * @author htpeng
 *2017年4月14日下午4:06:51
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 列名
	 * @return
	 */
	String name();
	/**
	 * 不能为空
	 * @return
	 */
	boolean notNull() default true;
	int len() default -1;
	/**
	 * 数据类型
	 * @return
	 */
	ColumnType type() default ColumnType.NULL;
	String defaultValue() default "";
	//foreignKey
}
