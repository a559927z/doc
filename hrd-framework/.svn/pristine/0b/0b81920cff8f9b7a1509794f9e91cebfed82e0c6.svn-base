/**
*net.chinahrd.core.timer
*/
package net.chinahrd.core.timer;

import java.util.ArrayList;
import java.util.List;


import net.chinahrd.core.timer.model.TimerConfig;

/**
 * 任务栈
 * @author htpeng
 *2016年11月9日下午3:05:13
 */
public class TimerStack {
	private List<Timer> list=new ArrayList<Timer>();

	public void put(Timer timer){
		synchronized (list) {
			list.add(index(timer.getTimerConfig()), timer);
		} 
	}
	
	public List<Timer> get(){
		return list;
	}
	
	
	private int index(TimerConfig timerConfig){
		int index =0;
		for(Timer t:list){
			int fp=Timer.NORM_PRIORITY;
			if(null!=t.getTimerConfig())fp=t.getTimerConfig().getPriority();
			if(fp<timerConfig.getPriority()){
				break;
			}
			index++;
		}
		return index;
	}
	
}
