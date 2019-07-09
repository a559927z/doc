/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import java.lang.reflect.Field;

import net.chinahrd.core.timer.Timer;
import net.chinahrd.core.timer.TimerRegisterCenter;

/**
 * 缓存任务
 * @author htpeng
 *2016年11月9日上午10:54:20
 */
public class CacheTimer {
	public static void loading(Class<?> cacheClazz){
		if(null!=cacheClazz){
			for(Field f:cacheClazz.getFields()){
				Class<?>[] clazzs=CacheBlock.class.getInterfaces();
				if(clazzs!=null){
					for(Class<?> clazz:clazzs){
						if(clazz.equals(Timer.class)){
							try {
								TimerRegisterCenter.getInstance().register((Timer)f.get(null));
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							break;
						}
					}
					
				}
			}
		}
	}
}
