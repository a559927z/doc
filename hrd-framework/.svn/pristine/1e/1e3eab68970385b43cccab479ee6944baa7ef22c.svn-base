/**
*net.chinahrd.core.tools.file
*/
package net.chinahrd.core.tools.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.jar.JarFile;

import net.chinahrd.core.exception.io.NotFoundFile;

/**
 * 文件工具类
 * @author htpeng
 *2016年10月12日上午12:04:42
 */
public class FileTool {
	/**
	 * 默认提供获取缓存Xml方法
	 * @param url  当前jar包的url
	 * @param path  文件在包项目中的路径    建议不以  "/" 绝对路径开头
	 * @return
	 */
	public static InputStream getInputStreamByJarPath(URL url ,String path) throws NotFoundFile{
		try {
			@SuppressWarnings("resource")
			JarFile jar = new JarFile(new File(url.toURI()));
			return jar.getInputStream(jar.getJarEntry(path));
		} catch (Exception e) {
			throw new NotFoundFile(path,e);
		}
	}
    
	/**
	 * 讀取項目中的配置文件
	 * @param path
	 * @return
	 * @throws IOException
	 */
    public static Properties getProperties(String path) throws IOException{
    	Properties pps= new Properties();
    	InputStream inputStream = FileTool.class.getClassLoader()
              .getResourceAsStream(path);
      	pps.load(inputStream);
         return pps;
    }

}
