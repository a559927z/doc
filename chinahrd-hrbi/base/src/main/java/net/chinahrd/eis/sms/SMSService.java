package net.chinahrd.eis.sms;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 发送手机短信服务
 * 
 * @author hhzhou
 * 
 */
public class SMSService {

	private ISMSSendProvider provider;

	public void setProvider(ISMSSendProvider provider) {
		this.provider = provider;
	}
	
	public String getProviderName() {
		return provider.getProviderName();
	}

	/**
	 * 获取剩余可用短信条数
	 * 
	 * @return
	 */
	public int getRemain() {
		return provider.getRemain();
	}

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 * @param smsText
	 */
	public void sendSMS(String[] mobiles, String signName, String smsText) {		
		mobiles = getDistinct(mobiles);
		if(mobiles == null || mobiles.length == 0 || StringUtils.isBlank(smsText)){
			return;
		}
		provider.sendSMS(mobiles, signName, smsText);
	}
	
	/**
	 * 发送短信
	 * @param mobile
	 * @param smsText
	 */
	public void sendSMS(String mobile, String signName, String smsText){
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(smsText)) {
			return;
		}
		provider.sendSMS(new String[]{mobile}, signName, smsText);
	}
	
	public void sendSMS(String[] mobiles, String signName, String smsText, Map<String,String> params) {
		provider.sendSMS(mobiles, signName, smsText, params);
	}

	/**
	 * 查询短信发送状态
	 * 
	 * @param mobile
	 * @return
	 */
	public List<SMSSendStatus> getSendStatus(String mobile) {
		return provider.getSendStatus(mobile);
	}
	
	/**
	 * 数组去重
	 * @param str
	 * @return
	 */
	private  static String[] getDistinct(String[] str) {
        List<String> list = new java.util.ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            if (!list.contains(str[i])) {
                list.add(str[i]);
            }
        }
        return list.toArray(new String[0]);
	}
}
