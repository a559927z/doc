package net.chinahrd.entity.dto.pc.laborEfficiency;

import java.io.Serializable;

public class LaborEfficiencyGridDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609880245803027696L;
	// grid colModel
	private String name;
	private String index;
	private String align;
	private boolean fixed;
	private boolean sortable;
	private int width;
	private String formatter;
	private boolean hidden;
	private String dateMonth;
	private String days;
	// grid content
	private String empId;// emp_id
	private String userName; // 姓名
	private String organId;// 机构id
	private String organName;// 机构名称
	private Double actualNum;// 实出勤
	private Double shouldNum;// 应出勤
	private String percentNum;// 劳动力效能
	private Double overtimeNum;// 加班
	private String operate;// 操作
	private Double ctName1;// 类型1
	private Double ctName2;// 类型
	private Double ctName3;// 类型
	private Double ctName4;// 类型
	private Double ctName5;// 类型
	private Double ctName6;// 类型
	private Double ctName7;// 类型
	private Double ctName8;// 类型
	private Double ctName9;// 类型
	private Double ctName10;// 类型
	private Double ctName11;// 类型
	private Double ctName12;// 类型
	private Double ctName13;// 类型
	private Double ctName14;// 类型
	private Double ctName15;// 类型
	private Double ctName16;// 类型
	private Double ctName17;// 类型
	private Double ctName18;// 类型
	private Double ctName19;// 类型
	private Double ctName20;// 类型

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(String dateMonth) {
		this.dateMonth = dateMonth;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Double getActualNum() {
		return actualNum == null ? 0D : actualNum;
	}

	public void setActualNum(Double actualNum) {
		this.actualNum = actualNum;
	}

	public Double getShouldNum() {
		return shouldNum == null ? 0D : shouldNum;
	}

	public void setShouldNum(Double shouldNum) {
		this.shouldNum = shouldNum;
	}

	public String getPercentNum() {
		return percentNum;
	}

	public void setPercentNum(String percentNum) {
		this.percentNum = percentNum;
	}

	public Double getOvertimeNum() {
		return overtimeNum == null ? 0D : overtimeNum;
	}

	public void setOvertimeNum(Double overtimeNum) {
		this.overtimeNum = overtimeNum;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Double getCtName1() {
		return ctName1 == null ? 0D : ctName1;
	}

	public void setCtName1(Double ctName1) {
		this.ctName1 = ctName1;
	}

	public Double getCtName2() {
		return ctName2 == null ? 0D : ctName2;
	}

	public void setCtName2(Double ctName2) {
		this.ctName2 = ctName2;
	}

	public Double getCtName3() {
		return ctName3 == null ? 0D : ctName3;
	}

	public void setCtName3(Double ctName3) {
		this.ctName3 = ctName3;
	}

	public Double getCtName4() {
		return ctName4 == null ? 0D : ctName4;
	}

	public void setCtName4(Double ctName4) {
		this.ctName4 = ctName4;
	}

	public Double getCtName5() {
		return ctName5 == null ? 0D : ctName5;
	}

	public void setCtName5(Double ctName5) {
		this.ctName5 = ctName5;
	}

	public Double getCtName6() {
		return ctName6 == null ? 0D : ctName6;
	}

	public void setCtName6(Double ctName6) {
		this.ctName6 = ctName6;
	}

	public Double getCtName7() {
		return ctName7 == null ? 0D : ctName7;
	}

	public void setCtName7(Double ctName7) {
		this.ctName7 = ctName7;
	}

	public Double getCtName8() {
		return ctName8 == null ? 0D : ctName8;
	}

	public void setCtName8(Double ctName8) {
		this.ctName8 = ctName8;
	}

	public Double getCtName9() {
		return ctName9 == null ? 0D : ctName9;
	}

	public void setCtName9(Double ctName9) {
		this.ctName9 = ctName9;
	}

	public Double getCtName10() {
		return ctName10 == null ? 0D : ctName10;
	}

	public void setCtName10(Double ctName10) {
		this.ctName10 = ctName10;
	}

	public Double getCtName11() {
		return ctName11 == null ? 0D : ctName11;
	}

	public void setCtName11(Double ctName11) {
		this.ctName11 = ctName11;
	}

	public Double getCtName12() {
		return ctName12 == null ? 0D : ctName12;
	}

	public void setCtName12(Double ctName12) {
		this.ctName12 = ctName12;
	}

	public Double getCtName13() {
		return ctName13 == null ? 0D : ctName13;
	}

	public void setCtName13(Double ctName13) {
		this.ctName13 = ctName13;
	}

	public Double getCtName14() {
		return ctName14 == null ? 0D : ctName14;
	}

	public void setCtName14(Double ctName14) {
		this.ctName14 = ctName14;
	}

	public Double getCtName15() {
		return ctName15 == null ? 0D : ctName15;
	}

	public void setCtName15(Double ctName15) {
		this.ctName15 = ctName15;
	}

	public Double getCtName16() {
		return ctName16 == null ? 0D : ctName16;
	}

	public void setCtName16(Double ctName16) {
		this.ctName16 = ctName16;
	}

	public Double getCtName17() {
		return ctName17 == null ? 0D : ctName17;
	}

	public void setCtName17(Double ctName17) {
		this.ctName17 = ctName17;
	}

	public Double getCtName18() {
		return ctName18 == null ? 0D : ctName18;
	}

	public void setCtName18(Double ctName18) {
		this.ctName18 = ctName18;
	}

	public Double getCtName19() {
		return ctName19 == null ? 0D : ctName19;
	}

	public void setCtName19(Double ctName19) {
		this.ctName19 = ctName19;
	}

	public Double getCtName20() {
		return ctName20 == null ? 0D : ctName20;
	}

	public void setCtName20(Double ctName20) {
		this.ctName20 = ctName20;
	}

}
