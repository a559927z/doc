/**
*net.chinahrd
*/
package net.chinahrd.module;


import net.chinahrd.core.cache.CacheRegisterAbstract;


/**
 * @author htpeng
 *2016年10月8日下午1:45:11
 */

public class SysCacheDefine extends CacheRegisterAbstract{ 

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.CacheRegister#register()
	 */
	@Override
	protected String getXmlPath() {
		return "mapper/common/SysCache.xml";
	}

	@Override
	public Class<SysCache> getCacheClass() {
		return SysCache.class;
	}
}
