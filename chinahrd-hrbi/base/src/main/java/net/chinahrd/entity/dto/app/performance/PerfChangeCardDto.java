package net.chinahrd.entity.dto.app.performance;

import java.io.Serializable;

/**
 * 员工绩效 Card dto
 * @author htpeng
 *2016年6月6日下午12:05:21
 */
public class PerfChangeCardDto implements Serializable {

    private static final long serialVersionUID = -8982011075350343543L;
	
    private int highCount;   
    private int lowCount;
    
	public int getHighCount() {
		return highCount;
	}
	public void setHighCount(int highCount) {
		this.highCount = highCount;
	}
	public int getLowCount() {
		return lowCount;
	}
	public void setLowCount(int lowCount) {
		this.lowCount = lowCount;
	}
	
   
}
