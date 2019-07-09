package net.chinahrd.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * 反射工具类
 * @author guan
 *
 */
public class RefUtil extends ReflectUtil {
	
	/**
	 * 调用属性的get方法
	 * @param obj
	 * @param fieldName
	 * @throws SecurityException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> Object invokeFiledGetter(Object obj, String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		return getMethodIgnoreCase(obj.getClass(), "get"+fieldName, null).invoke(obj, null);
	}
	
	/**
	 * 调用属性的set方法
	 * @param obj
	 * @param fieldName
	 * @param args
	 */
	public static <T> Object invokeFiledSetter(Object obj, String fieldName, Object... args) {
		return invoke(obj, "set"+captureName(fieldName), args);
	}

	/**
	 * 首字母大写
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}
}
