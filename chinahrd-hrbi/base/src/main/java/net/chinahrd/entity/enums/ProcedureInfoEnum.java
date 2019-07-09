package net.chinahrd.entity.enums;

/**
 * 过程信息
 * 
 * @author jxzhang on 2017年2月14日
 * @Verdion 1.0 版本
 */
public enum ProcedureInfoEnum {
	DAYS("DAYS", "DAYS", 101),
	THEORY_ATTENDANCE("THEORY_ATTENDANCE", "THEORY_ATTENDANCE", 201);

	private String proName; // 过程名称\表名
	private String tableName; // 表名称
	private Integer showIndex; // 顺序

	private ProcedureInfoEnum(String proName, String tableName, Integer showIndex) {
		this.proName = proName;
		this.tableName = tableName;
		this.showIndex = showIndex;
	}

	public String getProName() {
		return proName;
	}

	public String getTableName() {
		return tableName;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

}
