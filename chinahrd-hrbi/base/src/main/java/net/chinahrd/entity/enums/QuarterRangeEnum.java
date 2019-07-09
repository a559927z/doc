package net.chinahrd.entity.enums;



/**
 * 季节代表的年月范围
 * @author jayu
 *
 */
public enum QuarterRangeEnum {
	Q1(1,"01-01","03-31"),
	Q2(2,"04-01","06-30"),
	Q3(3,"07-01","09-30"),
	Q4(4,"10-01","12-31");
	
	private int type;
	private String beginDate;
	private String endDate;
	
	private QuarterRangeEnum(int type, String beginDate, String endDate) {
		this.type = type;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	public int getType() {
		return type;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	
	public static void main(String[] args) {
		System.out.println(QuarterRangeEnum.valueOf("Q1").getBeginDate());
	}	
	
}
