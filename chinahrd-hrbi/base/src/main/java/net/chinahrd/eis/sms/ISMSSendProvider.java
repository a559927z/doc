package net.chinahrd.eis.sms;

import java.util.List;
import java.util.Map;

public interface ISMSSendProvider {

	/**
	 * 短信提供商名称
	 * 
	 * @return
	 */
	String getProviderName();

	/**
	 * 剩余可用短信条数
	 * 
	 * @return
	 */
	int getRemain();

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 * @param smsText
	 */
	void sendSMS(String[] mobiles, String signName, String smsText);

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 * @param smsTemplate
	 * @param params
	 */
	void sendSMS(String[] mobiles, String signName, String smsTemplate, Map<String, String> params);

	/**
	 * 查询发送状态
	 * 
	 * @param mobile
	 * @return
	 */
	List<SMSSendStatus> getSendStatus(String mobile);

	/**
	 * 初始化短信发送状态监控线程
	 */
	void initMonitor();

	/**
	 * 终止监控线程
	 */
	void stopMonitor();
}
