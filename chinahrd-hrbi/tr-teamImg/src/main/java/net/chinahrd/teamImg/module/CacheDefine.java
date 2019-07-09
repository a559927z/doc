/**
*net.chinahrd
*/
package net.chinahrd.teamImg.module;


import net.chinahrd.core.cache.CacheRegisterAbstract;


/**
 * @author htpeng
 *2016年10月8日下午1:45:11
 */

public class CacheDefine extends CacheRegisterAbstract{

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.CacheRegister#register()
	 */
	@Override
	protected String getXmlPath() {
		// TODO Auto-generated method stub
		return "mapper/paper/manage/Cache.xml";
		//return this.getClass().getResourceAsStream("/mapper/common/Cache.xml");
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.CacheRegister#getCacheClass()
	 */
	@Override
	public Class<? extends Object> getCacheClass() {
		// TODO Auto-generated method stub
		return Cache.class;
	}

}
