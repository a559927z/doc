package net.chinahrd.eis.sms.c123;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	public static String post(String urlPost, String param, String method,
			String encoding) throws Exception {
		URL url = new URL(urlPost);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);
		http.setDoInput(true);
		http.setRequestMethod(method);
		http.setUseCaches(false);
		http.setConnectTimeout(10000);
		http.setReadTimeout(10000);
		http.setInstanceFollowRedirects(true);
		http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				http.getOutputStream(), encoding));
		out.write(param);
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				http.getInputStream(), encoding));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();
		http.disconnect();

		return sb.toString();
	}
}
