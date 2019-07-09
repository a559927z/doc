/**
*net.chinahrd.listener
*/
package net.chinahrd.listener;



/**
 * 监视该线程运行状态
 * @author htpeng
 *2017年5月24日下午2:41:17
 */
public interface ThreadObservable extends Runnable{
	/**
	 * 线程是否处于活跃状态
	 * @return
	 */
	boolean isActive();
}
