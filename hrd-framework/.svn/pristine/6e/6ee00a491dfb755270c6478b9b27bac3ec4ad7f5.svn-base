/**
*net.chinahrd.core.exception.cache
*/
package net.chinahrd.core.exception.io;

import net.chinahrd.core.exception.ChinahrdException;

/**
 * 缓存中找不到对应类或者方法
 * @author htpeng
 *2016年10月10日下午2:49:38
 */
public class NotFoundFile extends ChinahrdException{
	private static final long serialVersionUID = 6592479693122952280L;

	public NotFoundFile(){
		super("未查找到文件。");
	}
	
	public NotFoundFile(String FileName){
		super("未查找到文件："+FileName);
	}
	
	public NotFoundFile(String FileName,Throwable t){
		super("未查找到文件："+FileName,t);
	}
}
