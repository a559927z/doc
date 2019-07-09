package net.chinahrd.entity.dto.pc.employeePerformance;

import java.io.Serializable;

/**
 * 绩效变化状态
 * @author guanjian
 *
 */
public class PreChangeStatusDto implements Serializable {

	/**  */
	private static final long serialVersionUID = 6880134883890345766L;
	/** 绩效上升 */
	private Integer rise;
	/** 绩效下降 */
	private Integer down;
	/** 绩效持平 */
	private Integer equal;
	public Integer getRise() {
		return rise;
	}
	public void setRise(Integer rise) {
		this.rise = rise;
	}
	public Integer getDown() {
		return down;
	}
	public void setDown(Integer down) {
		this.down = down;
	}
	public Integer getEqual() {
		return equal;
	}
	public void setEqual(Integer equal) {
		this.equal = equal;
	}

}
