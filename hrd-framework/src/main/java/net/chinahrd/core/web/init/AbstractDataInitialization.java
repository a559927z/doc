package net.chinahrd.core.web.init;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import net.chinahrd.core.web.WarMain;
import net.chinahrd.core.web.init.DataInitialization;

/**
 * 初始化加载接口
 * @author htpeng
 *2016年2月2日下午2:27:53
 */
public abstract class AbstractDataInitialization implements DataInitialization{
	private static String projectPath=null;
	protected  String getProjectPath(){
//		String projectUrl=null;
//		ServerInfo s=ServerInfo.getServerInfo(config.getServletContext().getServerInfo());
////		服务器类型信息。
//		System.out.println(config.getServletContext().getServerInfo());
//		switch (s) {
//			case TOMCAT:
//				projectUrl=getClass().getClassLoader().getResource("//").getPath();
//				if(projectUrl.indexOf("/")==0){
//					projectUrl=projectUrl.replaceFirst("/", "");
//				}
//				break;
//			case JETTY:
//				//cacheXmlPath= getClass().getClassLoader().getResource(XmlConfig.CACHE_XML).getPath();
//				break;
//			default:
//			//	return null;
//		}
//		if(null==projectUrl){
//			projectUrl=forceGetPath();
//		}else{
//			File file=new File(projectUrl);
//			if(!file.exists()){
//				projectUrl=forceGetPath();
//			}
//		}
		if(null==projectPath){
//			projectPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			projectPath = WarMain.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		        if (projectPath.endsWith(".jar")){
		        	projectPath = projectPath.substring(0, projectPath.lastIndexOf("/"));
		            try {
		            	projectPath = URLDecoder.decode(projectPath, "UTF-8"); //解决路径中有空格%20的问题
		            } catch (UnsupportedEncodingException ex) {

		            }
		        }else if(projectPath.endsWith(".class")){
		        	String WEB_INFO="WEB-INF";
		        	String CLASSES="classes";
		        	int n_w=projectPath.indexOf(WEB_INFO);
		        	int n_c=projectPath.indexOf(CLASSES);
		        	if(n_w>-1&&n_c>-1&&n_c==n_w+WEB_INFO.length()+1){
		        		projectPath = projectPath.substring(0, n_c+CLASSES.length());
		        	}
		          try {
		        	  projectPath = URLDecoder.decode(projectPath, "UTF-8"); //解决路径中有空格%20的问题
		          } catch (UnsupportedEncodingException ex) {

		          }
		        }
		        File file = new File(projectPath);
		        projectPath = file.getAbsolutePath();
		}
	   
        return projectPath;
	}

//	/**
//	 * 如果按照不同容器检查不到目录 则调用该方法
//	 * @return
//	 */
//	private String forceGetPath(){
//		String projectUrl=null;
//		URL url=getClass().getClassLoader().getResource("//");
//		if(null==url){
//			 projectUrl=getClass().getClassLoader().getResource("//").getPath();
//			if(projectUrl.indexOf("/")==0){
//				projectUrl=projectUrl.replaceFirst("/", "");
//			}
//		}else{
//			projectUrl= url.getPath();
//		}
//		return projectUrl;
//	}
}
