package net.chinahrd.core.web.eis.license;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.core.web.eis.license.LicenseConfig.LicenseParm;
import net.chinahrd.core.web.eis.license.util.RSAUtils;




/**
 * 对License文件内容进行校验
 * @author htpeng
 *2016年2月16日下午5:37:21
 */
public class LicenseVerify {
	private static boolean vaildData=true;
	private static List<String> list=new ArrayList<String>();
	private LicenseData licenseData;
	public LicenseVerify(LicenseData licenseData){
		this.licenseData=licenseData;
	}
	public void  verifyLicense(byte[] data){
		//首先读取某路径下的license文件是否存在，不存在返回false;
		//若license文件存在，读取文件内容并解密，读取其中的机器码或试用截止日期
		//机器码和试用截止日期，两个必须有一个存在，两个都存在不允许
		//若只存在机器码，就比较机器码，机器码一致返回true，不一致返回false
		//若只存在试用截止日期
		getLicenseData(deciphering(data));
	}
	
	

	/**
	 * 公钥解密文件
	 * 
	 * @param data
	 * @return
	 */
	private  String deciphering(byte[] data, String publicKey, int num) {
		if(data!=null){
			byte[] decodedData = data;
			try {
				while ((num--) > 0) {
					decodedData = RSAUtils.decryptByPublicKey(decodedData,
							publicKey);
				}
				return new String(decodedData);
			} catch (Exception e) {
				return null;
			}
		}
		
		return null;
	}

	/**
	 * 公钥解密文件
	 * 
	 * @param data
	 * @return
	 */
	private  String deciphering(byte[] data) {
		return deciphering(data, LicenseConfig.publicKey, 2);
	}

	public  void HandLiceseData(String[] parm){
		if(parm.length==2){
			LicenseParm l=LicenseParm.getLicenseParm(parm[0]);
			switch(l){
				case SV:
//					String[] targets=parm[1].split(",");
//					List<String> list=new ArrayList<String>();
//					for(String target:targets){
//						list.add(target);
//					}
					licenseData.put(l,list);
					break;
				case SN:
					licenseData.put(l,parm[1]);
					break;
				default:
					licenseData.put(l,parm[1]);
					break;
			}
		}
	}
	
	private void validLicense(){
		@SuppressWarnings("unused")
		Object obj=licenseData.get(LicenseParm.SN);
		licenseData.setValid(true);
		/*
		if(obj!=null){
			String sn=obj.toString();
//			if(!sn.equals("null")){
//				licenseData.setValid(sn.equals(LicenseConfig.sequenceService.getSequence()));
//			}
			licenseData.setValid(true);
		}else{
			licenseData.setValid(false);
		}*/
	}
	public  void getLicenseData(String data){
		if(null!=data){
			String[] splitArr=data.split("\\n");
			for(String str:splitArr){
				String[] parm=str.split(":");
				HandLiceseData(parm);
			}
			validLicense();
		}else{
			licenseData.setValid(false);
		}
	}
	
	public static void setMenus(String menu){
		if(vaildData){
			list.add(menu);
		}
	}
	public static void over(){
		vaildData=false;
	}
}
