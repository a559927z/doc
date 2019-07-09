package net.chinahrd.db.input.Entity;

import java.io.Serializable;
import java.util.Date;

public class HistoryEmpCountEntity implements Serializable {

	private static final long serialVersionUID = -3325108571772487596L;

	private String history_emp_count_id;
	private String customer_id;
	private String organization_id;
	private String organization_key;
	private String full_path;
	private int type;
	private int emp_count;
	private int emp_count_sum;
	private String day;
	private String note;

	private String history_emp_count_month_id;
	private int organization_full_path;
	private int month_begin;
	private int month_begin_sum;
	private int month_end;
	private int month_end_sum;
	private double month_count;
	private double month_count_sum;
	private double month_avg;
	private double month_avg_sum;
	private int year_month;
	private Date refresh;
	private String refreshStr;
	
	public Date getRefresh() {
		return refresh;
	}
	public void setRefresh(Date refresh) {
		this.refresh = refresh;
	}
	public String getRefreshStr() {
		return refreshStr;
	}
	public void setRefreshStr(String refreshStr) {
		this.refreshStr = refreshStr;
	}
	public String getHistory_emp_count_month_id() {
		return history_emp_count_month_id;
	}
	public void setHistory_emp_count_month_id(String history_emp_count_month_id) {
		this.history_emp_count_month_id = history_emp_count_month_id;
	}
	public int getOrganization_full_path() {
		return organization_full_path;
	}
	public void setOrganization_full_path(int organization_full_path) {
		this.organization_full_path = organization_full_path;
	}
	public int getMonth_begin() {
		return month_begin;
	}
	public void setMonth_begin(int month_begin) {
		this.month_begin = month_begin;
	}
	public int getMonth_begin_sum() {
		return month_begin_sum;
	}
	public void setMonth_begin_sum(int month_begin_sum) {
		this.month_begin_sum = month_begin_sum;
	}
	public int getMonth_end() {
		return month_end;
	}
	public void setMonth_end(int month_end) {
		this.month_end = month_end;
	}
	public int getMonth_end_sum() {
		return month_end_sum;
	}
	public void setMonth_end_sum(int month_end_sum) {
		this.month_end_sum = month_end_sum;
	}
	public double getMonth_count() {
		return month_count;
	}
	public void setMonth_count(double month_count) {
		this.month_count = month_count;
	}
	public double getMonth_count_sum() {
		return month_count_sum;
	}
	public void setMonth_count_sum(double month_count_sum) {
		this.month_count_sum = month_count_sum;
	}
	public double getMonth_avg() {
		return month_avg;
	}
	public void setMonth_avg(double month_avg) {
		this.month_avg = month_avg;
	}
	public double getMonth_avg_sum() {
		return month_avg_sum;
	}
	public void setMonth_avg_sum(double month_avg_sum) {
		this.month_avg_sum = month_avg_sum;
	}
	public int getYear_month() {
		return year_month;
	}
	public void setYear_month(int year_month) {
		this.year_month = year_month;
	}
	public String getOrganization_key() {
		return organization_key;
	}
	public void setOrganization_key(String organization_key) {
		this.organization_key = organization_key;
	}
	public String getHistory_emp_count_id() {
		return history_emp_count_id;
	}
	public void setHistory_emp_count_id(String history_emp_count_id) {
		this.history_emp_count_id = history_emp_count_id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}
	public String getFull_path() {
		return full_path;
	}
	public void setFull_path(String full_path) {
		this.full_path = full_path;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEmp_count() {
		return emp_count;
	}
	public void setEmp_count(int emp_count) {
		this.emp_count = emp_count;
	}
	public int getEmp_count_sum() {
		return emp_count_sum;
	}
	public void setEmp_count_sum(int emp_count_sum) {
		this.emp_count_sum = emp_count_sum;
	}

	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
