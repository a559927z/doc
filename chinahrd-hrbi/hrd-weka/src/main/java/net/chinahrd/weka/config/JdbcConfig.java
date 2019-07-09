package net.chinahrd.weka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JdbcConfig {
	private String url;
	private String username;
	private String password;
	
	public String getUrl() {
		return url;
	}
	
	@Value("${jdbc.url}")
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	
	@Value("${jdbc.username}")
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	@Value("${jdbc.password}")
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
