package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;

/**
 * 人才地图轴坐标dto
 * Created by wqcai on 16/7/25.
 */
public class TalentMapsAxisDto implements Serializable {
	private static final long serialVersionUID = -1903306158686015029L;
	private String x;
	private String y;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
}
