package net.chinahrd.core.web.eis.license;

import java.io.File;

import net.chinahrd.core.web.eis.license.util.Base64Utils;
import net.chinahrd.core.web.eis.license.util.RSAUtils;
import net.chinahrd.core.web.eis.license.License;
import net.chinahrd.core.web.eis.license.LicenseConfig;
import net.chinahrd.core.web.eis.license.LicenseData;
import net.chinahrd.core.web.eis.license.LicenseVerify;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 授权信息检查
 * @author htpeng
 *2016年2月16日下午5:38:32
 */
public class License {

	private  CheckLicenseThread checkLicenseThread=null;
	private  String path;
	private  LicenseData licenseData=null;
	private static License license =null;
	private License() {
		
	}
	public LicenseData getLicenseData() {
		return licenseData;
	}
	public static License getInstance(){
		if(null==license){
			license=new License();
			license.licenseData=new LicenseData();
		}
		return license;
	}
	
	 void start(){
		if(null!=checkLicenseThread){
			checkLicenseThread.stop.set(true);
			checkLicenseThread=null;
		}
		checkLicenseThread=	new CheckLicenseThread();
		new Thread(checkLicenseThread).start();
	}
	 
	 
	 void start(String path){
		this.path = path+File.separator+LicenseConfig.folderName+File.separator+ LicenseConfig.filename;
		start();
	}

	/**
	 * 公钥获取主机特征文件
	 * 
	 * @return
	 */
	public static byte[] getLicenseFile() {
		try {
			return RSAUtils.encryptByPublicKey(
					LicenseConfig.sequenceService.getSequence().getBytes(), LicenseConfig.publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private  class CheckLicenseThread implements Runnable {
		private AtomicBoolean stop = new AtomicBoolean(false);
		private LicenseVerify licenseVerify=new LicenseVerify(licenseData);
		public void run() {
			while (!stop.get()) {
				synchronized (licenseData) {
					try {
//						licenseData.clear();
						byte[]  data1 = Base64Utils.fileToByte(path);
						licenseVerify.verifyLicense(data1);
					} catch (Exception e) {

					} 
				}
				try {
					Thread.sleep(LicenseConfig.defaultSleepTime);
				}catch (InterruptedException e) {
					
				}
			}
		}

	}

}
