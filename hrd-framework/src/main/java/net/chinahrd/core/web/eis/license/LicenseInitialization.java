package net.chinahrd.core.web.eis.license;


import javax.servlet.ServletConfig;

import net.chinahrd.core.tools.InjectionTools;
import net.chinahrd.core.web.init.AbstractDataInitialization;

/**
 * 初始化授权信息
 * @author htpeng
 *2016年2月16日下午2:27:07
 */
public class LicenseInitialization extends AbstractDataInitialization{

	public void init(ServletConfig config) {
		LicenseDeveloperMode licenseDeveloperMode=InjectionTools.getBean(LicenseDeveloperMode.class,"licenseDeveloperMode");
		if(null!=licenseDeveloperMode){
			licenseDeveloperMode.execute(getProjectPath());
		}
//		 new TestLicenseServer(getProjectPath()).test();
		License.getInstance().start(getProjectPath());
    }


}