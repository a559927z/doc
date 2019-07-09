package net.chinahrd.core.web.init;

import net.chinahrd.core.web.init.ServerInfo;

/**
 * 服务器类型
 * @author htpeng
 *2016年2月2日下午2:28:25
 */
public enum ServerInfo {
	JETTY("jetty"),
	TOMCAT("Apache Tomcat");
	
	private String value;
	ServerInfo(String value){
		this.value=value;
	}
	
	public String getValue(){
		return this.value;
	}
	public static ServerInfo getServerInfo(String value){
		for(ServerInfo s:ServerInfo.values()){
			if(value.startsWith(s.getValue())){
				return s;
			}
		}
		return null;
		
	}
}
