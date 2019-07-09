package net.chinahrd.core.web.init;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.core.module.ModuleInitialization;
import net.chinahrd.core.timer.TimerInitialization;
import net.chinahrd.core.web.eis.license.LicenseInitialization;
import net.chinahrd.core.web.init.DataInitialization;


/**
 * 初始化的类的配置信息
 * @author htpeng
 *2016年2月2日下午2:28:57
 */
public class DataInitializationConfig {
	private static List<Class<? extends DataInitialization>> list=new ArrayList<Class<? extends DataInitialization>>();
	static{
		
		register(ModuleInitialization.class);
		register(TimerInitialization.class);
		register(LicenseInitialization.class);
		
	}
	public static List<Class<? extends DataInitialization>> getList() {
		return list;
	}

	private static synchronized void register(Class<? extends DataInitialization> c){
		synchronized (list) {
			list.add(c);
		}
	}

}
