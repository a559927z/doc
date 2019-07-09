package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesMapsConfigAxisDto implements Serializable {

	private static final long serialVersionUID = -5424781452876570737L;
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
