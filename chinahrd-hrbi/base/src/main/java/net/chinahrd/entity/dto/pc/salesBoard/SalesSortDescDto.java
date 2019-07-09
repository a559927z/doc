package net.chinahrd.entity.dto.pc.salesBoard;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesSortDescDto implements Comparable<SalesSortDescDto> {

	private String id;
	private String name;
	private Double value;
	private String organId;
	private String organName;
	private Integer conNum;
	private String flag;

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

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Integer getConNum() {
		return conNum;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public int compareTo(SalesSortDescDto o) {
		if (value != o.getValue()) {
			return (int) (o.getValue() * 100) - (int) (value * 100);
		} else if (conNum != o.getConNum()) {
			return o.getConNum() - conNum;
		} else if (!organName.equals(o.getOrganName())) {
			return organName.compareTo(o.getOrganName());
		} else {
			return name.compareTo(o.getName());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SalesSortDescDto) {
			SalesSortDescDto o = (SalesSortDescDto) obj;
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