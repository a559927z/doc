package net.chinahrd.eis.notice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class NoticeInfo {

	private String receiverName;
	private String mailReceiver;
	private String mailSubject;
	private String mailTemplate;
	private Map<String, String> mailTemplateParams = new HashMap<String, String>();
	private String smsReceiver;
	private String smsSignName;
	private String smsTemplate;
	private Map<String, String> smsTemplateParams = new HashMap<String, String>();

	public String getMailReceiver() {
		return mailReceiver;
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public Map<String, String> getMailTemplateParams() {
		return mailTemplateParams;
	}

	public String getSmsReceiver() {
		return smsReceiver;
	}

	public void setSmsReceiver(String smsReceiver) {
		this.smsReceiver = smsReceiver;
	}

	public String getSmsSignName() {
		return smsSignName;
	}

	public void setSmsSignName(String smsSignName) {
		this.smsSignName = smsSignName;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public Map<String, String> getSmsTemplateParams() {
		return smsTemplateParams;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * 去除多余的模版参数
	 */
	public void loseTemplateParams() {
		//loseTemplateParams(mailTemplate, mailTemplateParams);
		//loseTemplateParams(smsTemplate, smsTemplateParams);
	}

	public void loseTemplateParams(String template,
			Map<String, String> templateParams) {
		
		if(StringUtils.isBlank(template)){
			templateParams.clear();
			return;
		}
		
		// 必备参数
		List<String> paramList = extract(template);

		Map<String, String> maps = new HashMap<>();

		for (String param : paramList) {
			if (templateParams.containsKey(param)) {
				maps.put(param, templateParams.get(param));
			}
		}

		templateParams.clear();
		templateParams.putAll(maps);
	}

	/**
	 * 提取模版中的参数
	 */
	private List<String> extract(String str) {

		if (StringUtils.isBlank(str)) {
			return new ArrayList<>();
		}

		// 用参数替换模板中的${}变量
		Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(str);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String param = m.group(); // ${xx}
			sb.append(param.replaceAll("\\$\\{", "").replaceAll("\\}", ""))
					.append(",");
		}

		String[] strs = sb.toString().split(",");

		return Arrays.asList(strs);
	}
}
