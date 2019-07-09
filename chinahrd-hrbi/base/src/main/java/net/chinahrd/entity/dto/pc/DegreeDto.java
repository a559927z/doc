package net.chinahrd.entity.dto.pc;

import java.io.Serializable;

/**
 * 学历dto Created by jxzhang on 16/04/25 0030.
 */
public class DegreeDto implements Serializable {

	private static final long serialVersionUID = 5609682032810472592L;
	private String itemId; // 主键ID
	private String itemName; // 名称
	private Integer showIndex; // 排序索引
	private Integer type; // :0否， 1：大专以下

	public DegreeDto() {
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
