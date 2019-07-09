package net.chinahrd.entity.dto.pc.laborEfficiency;

import java.io.Serializable;

import net.chinahrd.utils.DateUtil;

/**
 * 
 * Title: TheoryAttendanceDto <br/>
 * Description: 
 * 
 * @author jxzhang
 * @DATE 2017年12月27日 下午2:13:46
 * @Verdion 1.0 版本
 */
public class TheoryAttendanceDto implements Serializable {

	private static final long serialVersionUID = -7628473011785814017L;

	private String id;
	private String customerId;
	private double hourCount;
	private String days;
	private int yearMonths;
	private int year;

	public TheoryAttendanceDto() {
		super();
	}
	
	public TheoryAttendanceDto(String id, String customerId, double hourCount, String days, int year) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.hourCount = hourCount;
		this.days = days;
		this.yearMonths = DateUtil.convertToIntYearMonth(days);
		this.year = year;
	}

	public TheoryAttendanceDto(String id, String customerId, double hourCount, String days, int yearMonths, int year) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.hourCount = hourCount;
		this.days = days;
		this.yearMonths = yearMonths;
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getHourCount() {
		return hourCount;
	}

	public void setHourCount(double hourCount) {
		this.hourCount = hourCount;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getYearMonths() {
		return yearMonths;
	}

	public void setYearMonths(int yearMonths) {
		this.yearMonths = yearMonths;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
