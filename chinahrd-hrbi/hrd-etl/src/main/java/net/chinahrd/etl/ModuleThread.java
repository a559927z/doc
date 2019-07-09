/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;


import net.chinahrd.listener.ThreadObservable;
import net.chinahrd.listener.ThreadObserver;
import net.chinahrd.util.EtlStatusSync;

/**
 * 模块线程池
 * @author htpeng 2017年4月11日下午4:25:47
 */
public abstract class ModuleThread  implements Module, ThreadObservable{
	
//	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 18, 3, TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>(4));
	private Boolean active=true;
	/**
	 *  创建固定容量大小的缓冲池
	 *  创建的线程池corePoolSize和maximumPoolSize值是相等的，它使用的LinkedBlockingQueue；
	 */
//	private static ExecutorService threadPool = Executors.newFixedThreadPool(18);
	private static ThreadObserver  threadPool = new ThreadObserver(18);
	@Override
	public void run() {
		execute();
		active=false;
	}
	@Override
	public boolean isActive(){
		return active;
	}
	
	@Override
	public void start() {
		EtlStatusSync.setMultiThreadingStatus(false);

		threadPool.execute(this);	// 线程池
//		threadPool.shutdown();
//		new Thread(this).start();	// 线程
	}
	
	protected abstract void execute();
}
