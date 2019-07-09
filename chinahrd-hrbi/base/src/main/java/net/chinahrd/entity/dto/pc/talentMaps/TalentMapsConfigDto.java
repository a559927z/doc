package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;
import java.util.List;

/**
 * 人才地图配置dto
 * Created by wqcai on 16/7/25.
 */
public class TalentMapsConfigDto implements Serializable {
	private static final long serialVersionUID = -8073905010311950206L;

	private String name;
	private String color;
	private List<TalentMapsAxisDto> values;

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

	public List<TalentMapsAxisDto> getValues() {
		return values;
	}

	public void setValues(List<TalentMapsAxisDto> values) {
		this.values = values;
	}
}
