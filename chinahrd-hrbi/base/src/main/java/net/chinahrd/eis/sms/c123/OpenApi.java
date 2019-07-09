package net.chinahrd.eis.sms.c123;

import java.util.List;

/**
 * 发送短信接口
 * @author hhzhou
 *
 */
public class OpenApi {
	private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
	private static String sAccount = "";
	private static String sAuthKey = "";
	private static int nCgid = 0;
	private static int nCsid = 0;

	public static void initialzeAccount(String url, String account,
			String authkey, int cgid, int csid) {
		sOpenUrl = url;
		sAccount = account;
		sAuthKey = authkey;
		nCgid = cgid;
		nCsid = csid;
	}

	public static String querySendOnce(String mobile, String content, int cgid,
			int csid, String time) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=sendOnce&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		sb.append("&m=");
		sb.append(mobile);
		sb.append("&c=");
		sb.append(PublicUtils.UrlEncode(content, null));
		if ((cgid > 0) || (nCgid > 0)) {
			sb.append("&cgid=");
			sb.append(cgid > 0 ? cgid : nCgid);
		}
		if ((csid > 0) || (nCsid > 0)) {
			sb.append("&csid=");
			sb.append(csid > 0 ? csid : nCsid);
		}
		if (time != null) {
			sb.append("&t=");
			sb.append(time);
		}
		return HttpUtils.post(sOpenUrl, sb.toString(), "POST", "UTF-8");
	}

	public static String querySendBatch(String mobile, String content,
			int cgid, int csid, String time) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=sendBatch&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		sb.append("&m=");
		sb.append(mobile);
		sb.append("&c=");
		sb.append(PublicUtils.UrlEncode(content, null));
		if ((cgid > 0) || (nCgid > 0)) {
			sb.append("&cgid=");
			sb.append(cgid > 0 ? cgid : nCgid);
		}
		if ((csid > 0) || (nCsid > 0)) {
			sb.append("&csid=");
			sb.append(csid > 0 ? csid : nCsid);
		}
		if (time != null) {
			sb.append("&t=");
			sb.append(time);
		}
		return HttpUtils.post(sOpenUrl, sb.toString(), "POST", "UTF-8");
	}

	public static String querySendParam(String mobile, String content,
			String[] paramArray, int cgid, int csid, String time)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=sendParam&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		sb.append("&m=");
		sb.append(mobile);
		sb.append("&c=");
		sb.append(PublicUtils.UrlEncode(content, null));

		int nCount = Math.min(paramArray.length, 10);
		for (int i = 0; i < nCount; i++) {
			if (paramArray[i] != null) {
				sb.append("&p");
				sb.append(i + 1);
				sb.append("=");
				sb.append(PublicUtils.UrlEncode(paramArray[i], null));
			}
		}
		if ((cgid > 0) || (nCgid > 0)) {
			sb.append("&cgid=");
			sb.append(cgid > 0 ? cgid : nCgid);
		}
		if ((csid > 0) || (nCsid > 0)) {
			sb.append("&csid=");
			sb.append(csid > 0 ? csid : nCsid);
		}
		if (time != null) {
			sb.append("&t=");
			sb.append(time);
		}
		return HttpUtils.post(sOpenUrl, sb.toString(), "POST", "UTF-8");
	}

	public static String queryBalance() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=getBalance&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		return HttpUtils.post(sOpenUrl, sb.toString(), "GET", "UTF-8");
	}

	public static String postUpdateKey() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("action=updateKey&ac=");
		sb.append(sAccount);
		sb.append("&authkey=");
		sb.append(sAuthKey);
		return HttpUtils.post(sOpenUrl, sb.toString(), "GET", "UTF-8");
	}

	public static BalanceResultBean getBalance() {
		try {
			String sRet = queryBalance();
			return PublicUtils.parseBalance(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static UpdateResultBean updateKey() {
		try {
			String sRet = postUpdateKey();
			return PublicUtils.parseAuthKey(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<SendResultBean> sendOnce(String mobile, String content,
			int cgid, int csid, String time) {
		try {
			String sRet = querySendOnce(mobile, content, cgid, csid, time);
			return PublicUtils.parseResult(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<SendResultBean> sendOnce(String[] mobileArray,
			String content, int cgid, int csid, String time) {
		try {
			String mobile = PublicUtils.joinArray(mobileArray, ",");
			String sRet = querySendOnce(mobile, content, cgid, csid, time);
			return PublicUtils.parseResult(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<SendResultBean> sendBatch(String mobile, String content,
			int cgid, int csid, String time) {
		try {
			String sRet = querySendBatch(mobile, content, cgid, csid, time);
			return PublicUtils.parseResult(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<SendResultBean> sendBatch(String[] mobileArray,
			String[] contentArray, int cgid, int csid, String time) {
		try {
			String mobile = PublicUtils.joinArray(mobileArray, ",");
			String content = PublicUtils.joinArray(contentArray, "{|}");
			String sRet = querySendBatch(mobile, content, cgid, csid, time);
			return PublicUtils.parseResult(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<SendResultBean> sendParam(String mobile, String content,
			String[] paramArray, int cgid, int csid, String time) {
		try {
			String sRet = querySendParam(mobile, content, paramArray, cgid,
					csid, time);
			return PublicUtils.parseResult(sRet);
		} catch (Exception localException) {
		}
		return null;
	}

	public static List<SendResultBean> sendParam(String[] mobileArray,
			String content, String[] paramArray, int cgid, int csid, String time) {
		try {
			String mobile = PublicUtils.joinArray(mobileArray, ",");
			String sRet = querySendParam(mobile, content, paramArray, cgid,
					csid, time);
			return PublicUtils.parseResult(sRet);
		} catch (Exception localException) {
		}
		return null;
	}
}