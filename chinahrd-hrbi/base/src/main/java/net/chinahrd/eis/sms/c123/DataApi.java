package net.chinahrd.eis.sms.c123;

import java.util.List;

/**
 * 短信发送状态查询等
 * @author hhzhou
 *
 */
public class DataApi {
	private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";
	private static String sAccount = "";
	private static String sAuthKey = "";

	public static void initialzeAccount(String url, String account,
			String authkey) {
		sDataUrl = url;
		sAccount = account;
		sAuthKey = authkey;
	}

	public static String querySendState() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=getSendState&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		return HttpUtils.post(sDataUrl, sb.toString(), "GET", "UTF-8");
	}

	public static String queryReply() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=getReply&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		return HttpUtils.post(sDataUrl, sb.toString(), "GET", "UTF-8");
	}

	public static List<SendStateBean> getSendState() {
		try {
			String sRet = querySendState();
			return PublicUtils.parseSendState(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<ReplyBean> getReply() {
		try {
			String sRet = queryReply();
			return PublicUtils.parseReply(sRet);
		} catch (Exception localException) {
		}
		return null;
	}
}
