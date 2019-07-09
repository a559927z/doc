/**
*net.chinahrd.util
*/
package net.chinahrd.util;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Etl状态同步类
 * @author htpeng
 *2017年5月25日上午11:50:15
 */
public class EtlStatusSync {
	/**
	 * 主线程是否执行完成
	 */
	private static AtomicBoolean mainThread=new AtomicBoolean(true);
	
	/**
	 * 多线程是否执行完成
	 */
	private static AtomicBoolean multiThreading=new AtomicBoolean(true);

	
	/**
	 * ETL是否正在执行中
	 * @return
	 */
	public static boolean isETLActive(){
		return !(mainThread.get()&&multiThreading.get());
	}
	
	
	/**
	 * 设置主线程状态   
	 * @param bool   true 完成    false  执行中
	 */
	public static void setMainThreadStatus(boolean bool){
		mainThread.set(bool);
	}
	
	/**
	 * 设置多线程状态
	 * @param bool   true 完成    false  执行中
	 * @return
	 */
	public static void setMultiThreadingStatus(boolean bool){
		multiThreading.set(bool);
	}
}
