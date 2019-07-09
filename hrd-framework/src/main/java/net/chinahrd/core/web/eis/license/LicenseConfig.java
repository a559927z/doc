package net.chinahrd.core.web.eis.license;

import net.chinahrd.core.web.eis.license.LinuxSequenceService;
import net.chinahrd.core.web.eis.license.SequenceService;
import net.chinahrd.core.web.eis.license.WindowSequenceService;

/**
 *授权配置信息 
 * @author htpeng
 *2016年2月18日上午9:46:39
 */
public class LicenseConfig {
	public static final String folderName="license";
	public static final long defaultTime = 2592000000l;
	public static final long defaultSleepTime = 60000l;
	public static final String filename="zrwcb.license";
	
	public static enum LicenseParm{
		SN("SN"),   //主机唯一序列号
		SV("SV"),   //菜单权限
		EMPNUM("EMPNUM");  //用户数量
		private String value;

		LicenseParm(String value){
			this.value=value;
		}
		public String getValue(){
			return this.value;
		}
		public static LicenseParm getLicenseParm(String value){
			for(LicenseParm l:LicenseParm.values()){
				if(value.startsWith(l.getValue())){
					return l;
				}
			}
			return null;
			
		}
	}
	public static final SequenceService sequenceService = getSequenceService();
	
	public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiUOW1ESPYYwC1VMPRVf21nxVNENwLCoCMi5eQ"
			+ "YLv9NBhRDtunUxOEhBXDX0iOoZPYzD98bwjxxjONTcQoVcMvNvdp3vW87gMDTGF0iiy1vVDTZo4U"
			+ "zr1G6uI4t7vg0hV3rExWfvBQBfPTq3A6ln16OZ0y6uouWVm8DmwHKGUjdwIDAQAB";

	
	/**
	 * 获取机器序列号
	 * @return
	 */
	public static SequenceService getSequenceService(){
		String osName=System.getProperties().getProperty("os.name");
		
		if(osName.contains("Windows")){
			return new WindowSequenceService();
		}else{
			return new LinuxSequenceService();
		}
	}
}
