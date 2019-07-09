package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;
import java.util.List;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesMapsConfigDto implements Serializable {

	private static final long serialVersionUID = -3004691346605653547L;

	private String name;
	private String color;
	private List<SalesMapsConfigAxisDto> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<SalesMapsConfigAxisDto> getValues() {
		return values;
	}

	public void setValues(List<SalesMapsConfigAxisDto> values) {
		this.values = values;
	}

}
