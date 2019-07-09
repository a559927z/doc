/**
*net.chinahrd
*/
package net.chinahrd.clientImg.module;

import net.chinahrd.core.cache.CacheRegisterAbstract;

/**
 * 
 * @author jxzhang on 2017年1月12日
 * @Verdion 1.0 版本
 */
public class CacheDefine extends CacheRegisterAbstract {

	@Override
	protected String getXmlPath() {
		return "mapper/paper/Cache.xml";
	}

	@Override
	public Class<? extends Object> getCacheClass() {
		return Cache.class;
	}

}
