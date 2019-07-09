package net.chinahrd.sequenceCount.module;

import net.chinahrd.core.cache.CacheRegisterAbstract;

public class CacheDefine extends CacheRegisterAbstract{

	@Override
	public Class<? extends Object> getCacheClass() {
		return Cache.class;
	}

	@Override
	protected String getXmlPath() {
		return "mapper/common/Cache.xml";
	}
}
