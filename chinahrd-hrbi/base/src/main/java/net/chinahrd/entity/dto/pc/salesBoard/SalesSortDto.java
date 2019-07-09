package net.chinahrd.entity.dto.pc.salesBoard;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesSortDto implements Comparable<SalesSortDto> {

	private String id;
	private String name;
	private Double value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public int compareTo(SalesSortDto o) {
		if (value != o.getValue()) {
			return (int) (value * 100) - (int) (o.getValue() * 100);
		} else {
			return value.compareTo(o.getValue());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SalesSortDto) {
			SalesSortDto o = (SalesSortDto) obj;
			if (value == o.getValue()) {
				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

}