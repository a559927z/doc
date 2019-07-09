package net.chinahrd.entity.dto.pc.laborEfficiency;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
public class LaborEfficiencyTypeDto implements Comparable<LaborEfficiencyTypeDto> {

	private String name;
	private Double value;
	private Double hours;
	private String percent;

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

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Override
	public int compareTo(LaborEfficiencyTypeDto o) {
		if (hours != o.getHours()) {
			return (int) (o.getHours() * 100) - (int) (hours * 100);
		} else if(value != o.getValue()){
			return (int) (o.getValue() * 100) - (int) (value * 100);
		} else {
			return name.compareTo(o.getName());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LaborEfficiencyTypeDto) {
			LaborEfficiencyTypeDto dto = (LaborEfficiencyTypeDto) obj;
			if (hours == dto.getHours()) {
				return true;
			} else
				return true;
		} else {
			return false;
		}
	}

}
