package net.chinahrd.perBenefit.module;

import net.chinahrd.core.cache.CacheDefine;
import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheBlockConstructor;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.PropertiesUtil;

/**
 * 定时更新缓存类
 * 
 * @author htpeng
 *
 */
public class Cache {
	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.Buffer#getDaoClass()
	 */
	
	private final static String customerId = EisWebContext.getCustomerId();

	public static  CacheBlock<Integer> AvailabilityDayNum = new CacheBlockConstructor<Integer>(
			"queryAvailabilityDayNum").getDefaultBlock(customerId,DateUtil.getDBCurdate(), PropertiesUtil.getProperty(ConfigKeyUtil.SY_REVIEWOTWEEK));

	
	
}
