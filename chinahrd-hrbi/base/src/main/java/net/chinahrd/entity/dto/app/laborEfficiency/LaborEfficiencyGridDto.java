package net.chinahrd.entity.dto.app.laborEfficiency;

import java.io.Serializable;

/**
 * 表格DTO
 * @author htpeng
 *2016年8月19日下午3:10:29
 */
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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the align
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * @param align the align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * @return the fixed
	 */
	public boolean isFixed() {
		return fixed;
	}

	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * @param sortable the sortable to set
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the formatter
	 */
	public String getFormatter() {
		return formatter;
	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the dateMonth
	 */
	public String getDateMonth() {
		return dateMonth;
	}

	/**
	 * @param dateMonth the dateMonth to set
	 */
	public void setDateMonth(String dateMonth) {
		this.dateMonth = dateMonth;
	}

	/**
	 * @return the days
	 */
	public String getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(String days) {
		this.days = days;
	}

	// grid colModel
	private String empId;// emp_id
	private String userName; // 姓名
	private String organId;// 机构id
	private String organName;// 机构名称
	private Double actualNum;// 实出勤
	private Double shouldNum;// 应出勤
	private String percentNum;// 劳动力效能
	private Double overtimeNum;// 加班
	private String operate;// 操作  休假
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
	
	/**
	 * @return the ctName1
	 */
	public Double getCtName1() {
		return ctName1;
	}

	/**
	 * @param ctName1 the ctName1 to set
	 */
	public void setCtName1(Double ctName1) {
		this.ctName1 = ctName1;
	}

	/**
	 * @return the ctName2
	 */
	public Double getCtName2() {
		return ctName2;
	}

	/**
	 * @param ctName2 the ctName2 to set
	 */
	public void setCtName2(Double ctName2) {
		this.ctName2 = ctName2;
	}

	/**
	 * @return the ctName3
	 */
	public Double getCtName3() {
		return ctName3;
	}

	/**
	 * @param ctName3 the ctName3 to set
	 */
	public void setCtName3(Double ctName3) {
		this.ctName3 = ctName3;
	}

	/**
	 * @return the ctName4
	 */
	public Double getCtName4() {
		return ctName4;
	}

	/**
	 * @param ctName4 the ctName4 to set
	 */
	public void setCtName4(Double ctName4) {
		this.ctName4 = ctName4;
	}

	/**
	 * @return the ctName5
	 */
	public Double getCtName5() {
		return ctName5;
	}

	/**
	 * @param ctName5 the ctName5 to set
	 */
	public void setCtName5(Double ctName5) {
		this.ctName5 = ctName5;
	}

	/**
	 * @return the ctName6
	 */
	public Double getCtName6() {
		return ctName6;
	}

	/**
	 * @param ctName6 the ctName6 to set
	 */
	public void setCtName6(Double ctName6) {
		this.ctName6 = ctName6;
	}

	/**
	 * @return the ctName7
	 */
	public Double getCtName7() {
		return ctName7;
	}

	/**
	 * @param ctName7 the ctName7 to set
	 */
	public void setCtName7(Double ctName7) {
		this.ctName7 = ctName7;
	}

	/**
	 * @return the ctName8
	 */
	public Double getCtName8() {
		return ctName8;
	}

	/**
	 * @param ctName8 the ctName8 to set
	 */
	public void setCtName8(Double ctName8) {
		this.ctName8 = ctName8;
	}

	/**
	 * @return the ctName9
	 */
	public Double getCtName9() {
		return ctName9;
	}

	/**
	 * @param ctName9 the ctName9 to set
	 */
	public void setCtName9(Double ctName9) {
		this.ctName9 = ctName9;
	}

	/**
	 * @return the ctName10
	 */
	public Double getCtName10() {
		return ctName10;
	}

	/**
	 * @param ctName10 the ctName10 to set
	 */
	public void setCtName10(Double ctName10) {
		this.ctName10 = ctName10;
	}

	/**
	 * @return the ctName11
	 */
	public Double getCtName11() {
		return ctName11;
	}

	/**
	 * @param ctName11 the ctName11 to set
	 */
	public void setCtName11(Double ctName11) {
		this.ctName11 = ctName11;
	}

	/**
	 * @return the ctName12
	 */
	public Double getCtName12() {
		return ctName12;
	}

	/**
	 * @param ctName12 the ctName12 to set
	 */
	public void setCtName12(Double ctName12) {
		this.ctName12 = ctName12;
	}

	/**
	 * @return the ctName13
	 */
	public Double getCtName13() {
		return ctName13;
	}

	/**
	 * @param ctName13 the ctName13 to set
	 */
	public void setCtName13(Double ctName13) {
		this.ctName13 = ctName13;
	}

	/**
	 * @return the ctName14
	 */
	public Double getCtName14() {
		return ctName14;
	}

	/**
	 * @param ctName14 the ctName14 to set
	 */
	public void setCtName14(Double ctName14) {
		this.ctName14 = ctName14;
	}

	/**
	 * @return the ctName15
	 */
	public Double getCtName15() {
		return ctName15;
	}

	/**
	 * @param ctName15 the ctName15 to set
	 */
	public void setCtName15(Double ctName15) {
		this.ctName15 = ctName15;
	}

	/**
	 * @return the ctName16
	 */
	public Double getCtName16() {
		return ctName16;
	}

	/**
	 * @param ctName16 the ctName16 to set
	 */
	public void setCtName16(Double ctName16) {
		this.ctName16 = ctName16;
	}

	/**
	 * @return the ctName17
	 */
	public Double getCtName17() {
		return ctName17;
	}

	/**
	 * @param ctName17 the ctName17 to set
	 */
	public void setCtName17(Double ctName17) {
		this.ctName17 = ctName17;
	}

	/**
	 * @return the ctName18
	 */
	public Double getCtName18() {
		return ctName18;
	}

	/**
	 * @param ctName18 the ctName18 to set
	 */
	public void setCtName18(Double ctName18) {
		this.ctName18 = ctName18;
	}

	/**
	 * @return the ctName19
	 */
	public Double getCtName19() {
		return ctName19;
	}

	/**
	 * @param ctName19 the ctName19 to set
	 */
	public void setCtName19(Double ctName19) {
		this.ctName19 = ctName19;
	}

	/**
	 * @return the ctName20
	 */
	public Double getCtName20() {
		return ctName20;
	}

	/**
	 * @param ctName20 the ctName20 to set
	 */
	public void setCtName20(Double ctName20) {
		this.ctName20 = ctName20;
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
		return actualNum;
	}

	public void setActualNum(Double actualNum) {
		this.actualNum = actualNum;
	}

	public Double getShouldNum() {
		return shouldNum;
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
		return overtimeNum;
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

}
