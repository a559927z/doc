/**
*net.chinahrd.listener
*/
package net.chinahrd.listener;

import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.chinahrd.util.EtlStatusSync;

/**
 * 模块线程观察者
 * 
 * @author htpeng 2017年5月24日下午2:41:17
 */
public class ThreadObserver {
	private ExecutorService threadPool;
	private Vector<ThreadObservable> vector = new Vector<ThreadObservable>();
	private Vector<ThreadObservable> activeVecor = new Vector<ThreadObservable>();
	private Vector<ThreadObservable> unActiveVecor = new Vector<ThreadObservable>();

	public ThreadObserver(int num) {
		threadPool = Executors.newFixedThreadPool(num);
		new Thread(new ThreadPoolListener()).start();
	}

	public void execute(ThreadObservable command) {
		threadPool.execute(command);
		vector.addElement(command);
	}

	class ThreadPoolListener implements Runnable {
		/**
		 * 是否允许关闭
		 */
		private boolean shutdown=false;
		
		/**
		 * 是否关闭
		 */
		private boolean shutdownNow=false;
		@Override
		public void run() {
			while (!shutdownNow) {
				activeVecor.removeAllElements();
				unActiveVecor.removeAllElements();
				Enumeration<ThreadObservable> iterator = vector.elements();
				while (iterator.hasMoreElements()) {
					ThreadObservable t = iterator.nextElement();
					if (t.isActive()) {
						activeVecor.addElement(t);
					} else {
						unActiveVecor.addElement(t);
					}
				}
				System.out.println("*********************************************");
				System.out.println("正在执行完成模块:");
				Enumeration<ThreadObservable> activeIterator = activeVecor.elements();
				while (activeIterator.hasMoreElements()) {
					System.out.println(activeIterator.nextElement().getClass());
				}
				System.out.println("*********************************************");
				System.out.println("已执行完成模块:");
				Enumeration<ThreadObservable> unActiveIterator = unActiveVecor.elements();
				while (unActiveIterator.hasMoreElements()) {
					System.out.println(unActiveIterator.nextElement().getClass());
				}
				System.out.println("*********************************************");
				if(shutdown&&activeVecor.size()==0){
					shutdownNow=true;
					vector.removeAllElements();
					EtlStatusSync.setMultiThreadingStatus(true);
				}
				try {
					Thread.sleep(60 * 1000);
					if(vector.size()>0){
						shutdown=true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
