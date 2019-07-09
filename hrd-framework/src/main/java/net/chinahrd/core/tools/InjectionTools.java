/**
*net.chinahrd.core.tools
*/
package net.chinahrd.core.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.chinahrd.core.RegisterInterface;
import net.chinahrd.core.annotation.Injection;

/**注入和注解工具类
 * @author htpeng
 *2017年5月10日下午5:16:31
 */
public class InjectionTools {
	private static Logger log=Logger.getLogger(InjectionTools.class);
	/**
	 * 实现注入工作
	 * @param registerInterface
	 */
	public static void injection(RegisterInterface registerInterface){
		for (Field f : registerInterface.getClass().getDeclaredFields()) {
			Annotation[] annotations = f.getAnnotations();
			if (annotations.length > 0) {
				for (Annotation annotation : annotations) {
					if(annotation.annotationType()==Injection.class){
						Object bean=null;
						String beanName=((Injection)annotation).value().trim();
						if(beanName.length()==0){
							/**
							 * 注入注解没有设置value 查找该类上是否存在spring注入注解
							 */
							for(Annotation b:f.getType().getAnnotations()){
								if(b instanceof Component){
									beanName=((Component) b).value();
									break;
								}else if(b instanceof Repository){
									beanName=((Repository) b).value();
									break;
								}else if(b instanceof Service){
									beanName=((Service) b).value();
									break;
								}else if(b instanceof Controller){
									beanName=((Controller) b).value();
									break;
								}
							}
							if(null!=beanName){
								bean=getBean(beanName);
							}
						}else{
							bean=getBean(beanName);
						}
						/**
						 * 根据对象名称查找
						 */
						if(null==bean){
							 beanName=f.getName();
							 bean=getBean(f.getName());
						}
						if(null!=bean){
							f.setAccessible(true);
							try {
								f.set(registerInterface, bean);
							} catch (IllegalArgumentException
									| IllegalAccessException e) {
								e.printStackTrace();
							}
						}else{
							log.info("找不到实例："+beanName);
						}
						
					}
				}
			}
		}
	}
	
	
	/**
	 * 根据name 获取Bean实例
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		return getBean(ContextLoader.getCurrentWebApplicationContext(),name);
	}
	
	/**
	 * 根据name 获取Bean实例
	 * @param name
	 * @return
	 */
	public static Object getBean(WebApplicationContext wac,String name){
		try {
			if(null==wac){
				log.info("WebApplicationContext 对象为空!");
				return null;
			}
			return wac.getBean(name);
		} catch (BeansException e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据name 获取Bean实例
	 * @param name
	 * @return
	 */
	public static <T>T getBean(Class<T> clazz,WebApplicationContext wac,String name){
		try {
			if(null==wac){
				log.info("WebApplicationContext 对象为空!");
				return null;
			}
			return (T)wac.getBean(name);
		} catch (BeansException e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据name 获取Bean实例
	 * @param name
	 * @return
	 */
	public static <T>T getBean(Class<T> clazz,String name){
		return getBean(clazz,ContextLoader.getCurrentWebApplicationContext(),name);
	}
}
