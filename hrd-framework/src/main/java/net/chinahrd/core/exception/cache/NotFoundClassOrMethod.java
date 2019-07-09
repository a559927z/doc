/**
*net.chinahrd.core.exception.cache
*/
package net.chinahrd.core.exception.cache;

import net.chinahrd.core.exception.ChinahrdException;

/**
 * 缓存中找不到对应类或者方法
 * @author htpeng
 *2016年10月10日下午2:49:38
 */
public class NotFoundClassOrMethod extends ChinahrdException{
	private static final long serialVersionUID = 6592479693122952280L;

	public NotFoundClassOrMethod(){
		super("缓存中找不到对应类或者方法,请检查Buffer定义类的getDaoClass方法返回值或xml中命名空间是否正确。");
	}
	
	public NotFoundClassOrMethod(String daoClassName,String methodName){
		super("缓存中找不到对应类或者方法,请检查Buffer定义类的getDaoClass方法返回值："+daoClassName+" 或xml中命名空间是否正确。");
	}
	
	public NotFoundClassOrMethod(String daoClassName,String methodName,Throwable t){
		super("缓存中找不到对应类或者方法,请检查Buffer定义类的getDaoClass方法返回值："+daoClassName+" 或xml中命名空间是否正确。",t);
	}
}
