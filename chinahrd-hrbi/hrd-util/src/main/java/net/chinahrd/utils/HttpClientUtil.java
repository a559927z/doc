package net.chinahrd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Title: HttpClientUtil <br/>
 * Description:
 * 
 * @author jxzhang
 * @DATE 2017年11月29日 下午4:22:05
 * @Verdion 1.0 版本
 */
public class HttpClientUtil {

	private static String CONTENT_TYPE = "Content-Type";
	private static String JSON_UTF8 = "application/json;charset=utf-8";
	private static String ACCEPT = "Accept";
	private static String UTF8 = "utf-8";

	public static String get(String url) {
		return get(null, url, UTF8);
	}

	public static String get(Map<String, Object> paramester, String url) {
		return get(paramester, url, UTF8);
	}

	private static String get(Map<String, Object> paramester, String url, String encode) {
		String queryString = "";
		if (!CollectionKit.isEmpty(paramester)) {
			Set<String> keySet = paramester.keySet();
			for (String key : keySet) {
				queryString += "&";
				queryString += key;
				queryString += "=";
				queryString += paramester.get(key);
			}
		}
		if (queryString.startsWith("&")) {
			queryString = queryString.substring(1, queryString.length());
		}
		url += "?" + queryString;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		String bodyString = null;
		try {
			HttpResponse response = client.execute(httpGet);
			bodyString = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		return bodyString;
	}

	public static String postBody(Map<String, String> parameter, String url) {
		return post(parameter, url, UTF8);
	}

	private static String post(Map<String, String> parameter, String url, String encode) {
		HttpClient httpclient = HttpClientBuilder.create().build();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (parameter != null && !parameter.isEmpty()) {
			Set<String> keySet = parameter.keySet();
			for (String key : keySet) {
				NameValuePair pair = new BasicNameValuePair(key, parameter.get(key));
				params.add(pair);
			}
		}
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader(CONTENT_TYPE, JSON_UTF8);
		String jsonStr = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, encode));
			HttpResponse response = httpclient.execute(httppost);

			System.out.println(response.toString());

			HttpEntity entity = response.getEntity();
			jsonStr = EntityUtils.toString(entity, encode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httppost.releaseConnection();
		}
		return jsonStr;
	}

	public static String postBody(String json, String url) {
		return postBody(json, url, null);
	}

	private static String postBody(String json, String url, Map<String, String> header) {
		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpPost httppost = new HttpPost(url);
		httppost.addHeader(CONTENT_TYPE, JSON_UTF8);
		httppost.addHeader(ACCEPT, JSON_UTF8);
		if (header != null && !header.isEmpty()) {
			Set<String> keys = header.keySet();
			if (keys != null) {
				for (String key : keys) {
					httppost.addHeader(key, header.get(key));
				}
			}
		}
		String jsonStr = null;
		try {
			httppost.setEntity(new StringEntity(json, UTF8));
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			jsonStr = EntityUtils.toString(entity, UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httppost.releaseConnection();
		}
		return jsonStr;
	}

}
