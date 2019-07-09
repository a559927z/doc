package net.chinahrd.accordDismiss.module;


import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheBlockConstructor;
import net.chinahrd.eis.permission.EisWebContext;

/**
 * 定时更新缓存类
 * 
 * @author htpeng
 *
 */
public class Cache{
	
	private final static String customerId = EisWebContext.getCustomerId();



	
	public static CacheBlock<String> queryQuarterLastDay=new CacheBlockConstructor<String>("queryQuarterLastDay").getDefaultBlock(customerId);
	
}
