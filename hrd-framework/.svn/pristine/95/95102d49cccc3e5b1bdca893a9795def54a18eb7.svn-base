/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.cache;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.ibatis.annotations.Param;

import net.chinahrd.core.cache.config.CacheXmlRead;
import net.chinahrd.core.cache.model.CacheRepertoryConfig;
import net.chinahrd.core.cache.model.MethodConfig;
import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;
import net.chinahrd.core.xml.XmlRead;

/**
 * 缓存注册中心
 * @author htpeng 2016年10月8日下午1:42:50
 */

class CacheRegisterCenter {
	// private List<InputStream> list = new ArrayList<InputStream>();
	private static CacheRegisterCenter cacheRegister = null;

	private CacheRegisterCenter() {

	}

	public static CacheRegisterCenter getInstance() {
		if (null == cacheRegister) {
			cacheRegister = new CacheRegisterCenter();
		}
		return cacheRegister;
	}

	void register(InputStream ins, ModuleModel moduleModel) {
		try {
			XmlRead xmlRead = new CacheXmlRead();
			xmlRead.load(ins);
			XmlModel xmlModel = xmlRead.getXmlModel();
			String daoClassName = xmlModel
					.getAttribute(ConfigEnum.CACHE_DAO_NAME);
			Class<? extends Object> clazz = Class.forName(daoClassName);
			moduleModel.setCacheDaoClass(clazz);
			CacheRepertoryConfig cacheConfig = new CacheRepertoryConfig();
			for (Method m : clazz.getMethods()) {
				XmlModel mxm = xmlModel.getNodeById(m.getName());
				if (null != mxm) {
					MethodConfig mc = new MethodConfig(mxm);
					mc.setName(m.getName());
					mc.setResultType(m.getReturnType());
					for (Annotation[] aArr : m.getParameterAnnotations()) {
						for (Annotation a : aArr) {
							if (a instanceof Param) {
								mc.getParmList().add(((Param) a).value());
							}
						}
					}
					cacheConfig.setMethodConfig(m.getName(), mc);
				}
			}
			CacheConfigTool.setMethodParmMap(daoClassName, cacheConfig);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
