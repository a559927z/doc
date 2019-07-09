package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;

public class DaysDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String days;			//日期
	private String isForkFlag;		//是否工作日
	private String isHoliday;		//是否休息日
	private String isVacation;		//是否节假日
	
	private String beginDay;
	private String endDay;
	
	public DaysDto(String days, String isForkFlag, String isHoliday, String isVacation, String begin, String end){
		this.days = days;
		this.isForkFlag = isForkFlag;
		this.isHoliday = isHoliday;
		this.isVacation = isVacation;
		this.beginDay = begin;
		this.endDay = end;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getIsForkFlag() {
		return isForkFlag;
	}
	public void setIsForkFlag(String isForkFlag) {
		this.isForkFlag = isForkFlag;
	}
	public String getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}
	public String getIsVacation() {
		return isVacation;
	}
	public void setIsVacation(String isVacation) {
		this.isVacation = isVacation;
	}
	public String getBeginDay() {
		return beginDay;
	}
	public void setBeginDay(String beginDay) {
		this.beginDay = beginDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	
	

}
