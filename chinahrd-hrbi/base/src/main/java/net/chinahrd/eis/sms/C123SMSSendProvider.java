package net.chinahrd.eis.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.chinahrd.eis.sms.c123.BalanceResultBean;
import net.chinahrd.eis.sms.c123.DataApi;
import net.chinahrd.eis.sms.c123.OpenApi;
import net.chinahrd.eis.sms.c123.SendStateBean;
import net.chinahrd.utils.Identities;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * C123短信平台网关接口
 * 
 * @author hhzhou
 * 
 */
public class C123SMSSendProvider extends AbstractSMSSendProvider {

	private static Log log = LogFactory.getLog(C123SMSSendProvider.class);

	// 通道组编号
	private static final int cgid = 52;
	// 默认使用的签名编号(未指定签名编号时传此值到服务器)
	private static final int csid = 0;

	@Override
	public int getRemain() {

		initialzeAccount();

		// 取帐户余额
		BalanceResultBean br = OpenApi.getBalance();
		if (br == null) {
			log.error("获取可用余额时发生异常!");
			return -1;
		}
		if (br.getResult() < 1) {
			log.error("获取可用余额失败: " + br.getErrMsg());
			return -1;
		}
		log.info("可用条数: " + br.getRemain());

		return br.getRemain();
	}

	@Override
	public void sendSMS(String[] mobiles, String signName, String smsText) {
		if (mobiles == null || mobiles.length == 0) {
			return;
		}

		initialzeAccount();
		if (getRemain() == -1) {
			log.error("获取可剩余短信条数失败，暂停发送。");
			return;
		} else if (getRemain() <= mobiles.length) {
			log.warn("短信剩余条数不足，有可能发送失败。");
		}
		
		int sign = csid;
		try {
			sign = Integer.parseInt(signName);
		} catch (NumberFormatException e) {
			log.error(e);
		}

		OpenApi.sendOnce(mobiles, smsText, cgid, sign, null);// time=null,立即发送
	}

	@Override
	public void sendSMS(String[] mobiles, String signName, String smsTemplate,
			Map<String, String> params) {
		if (mobiles == null || mobiles.length == 0) {
			return;
		}

		initialzeAccount();
		if (getRemain() == -1) {
			log.error("获取可剩余短信条数失败，暂停发送。");
			return;
		} else if (getRemain() <= mobiles.length) {
			log.warn("短信剩余条数不足，有可能发送失败。");
		}

		//----为了将就OpenApi的参数传递规则，重写模版参数
		// 必备参数
		String[] paramNames = extract(smsTemplate);
		String[] paramValues = new String[paramNames.length];

		// 修改模版中参数名为{p1}，{p2}等
		// 组装要发送的参数值
		smsTemplate = smsTemplate.replaceAll("\\$", "");
		for (int i = 0; i < paramNames.length; i++) {
			String key = paramNames[i].substring(1,paramNames[i].length()-1);
			smsTemplate = smsTemplate.replaceAll(key, "p" + (i+1));
			paramValues[i] = params.get(key);
		}

		int sign = csid;
		try {
			sign = Integer.parseInt(signName);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		OpenApi.sendParam(mobiles, smsTemplate, paramValues, cgid, sign, null);

		log.info("短信已发送至" + Arrays.toString(mobiles));
	}

	@Override
	protected List<SMSSendStatus> getSendStatus() {
		// 状态及回复参数
		DataApi.initialzeAccount(dataUrl, uid, authKey);

		List<SMSSendStatus> statusList = new ArrayList<SMSSendStatus>();

		List<SendStateBean> listSendState = DataApi.getSendState();

		if (listSendState != null) {
			for (SendStateBean t : listSendState) {
				SMSSendStatus status = new SMSSendStatus();
				status.setId(Identities.uuid2());
				status.setProvider(getProviderName());
				status.setMsgNo(t.getMsgId() + "");
				status.setMobile(t.getMobile());
				// log.setSmsTxt(t.get)
				// log.setSendTime(t.get)
				status.setStatus(t.getStatus());
				status.setTelecomStatus(t.getDetail());
				statusList.add(status);
			}
		}

		return statusList;
	}

	private void initialzeAccount() {
		OpenApi.initialzeAccount(sendUrl, uid, authKey, cgid, csid);
	}

	/**
	 * 提取模版中的参数
	 * 
	 * @param str
	 * @return
	 */
	private static String[] extract(String str) {

		if (StringUtils.isBlank(str)) {
			return new String[0];
		}

		// 用参数替换模板中的${}变量
		Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(str);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String param = m.group(); // ${xx}
			sb.append(param.replaceAll("\\$", "")).append(",");
		}
		return sb.toString().split(",");
	}
}
