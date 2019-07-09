package net.chinahrd.db.input.Entity.churnRate;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务表-流失率月度总人数
 * 
 * @author jxzhang on 2016-03-07
 *
 */
public class MonthlyEmpCountEntity implements Serializable {

	private static final long serialVersionUID = 7893740093501219441L;

	private String monthly_emp_id;
	private String customer_id;
	private String organization_id;
	private String organization_full_path;
	private int month_begin;
	private int month_begin_sum;
	private int month_end;
	private int month_end_sum;
	private double month_count;
	private double month_count_sum;
	private int act_count;
	private int un_act_count;
	private int act_count_sum;
	private int un_act_count_sum;
	private int year_month;
	private Date refresh;
	private String refreshStr;
	
	
	
	public String getRefreshStr() {
		return refreshStr;
	}
	public void setRefreshStr(String refreshStr) {
		this.refreshStr = refreshStr;
	}
	public String getMonthly_emp_id() {
		return monthly_emp_id;
	}
	public void setMonthly_emp_id(String monthly_emp_id) {
		this.monthly_emp_id = monthly_emp_id;
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
	public String getOrganization_full_path() {
		return organization_full_path;
	}
	public void setOrganization_full_path(String organization_full_path) {
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
	public int getAct_count() {
		return act_count;
	}
	public void setAct_count(int act_count) {
		this.act_count = act_count;
	}
	public int getUn_act_count() {
		return un_act_count;
	}
	public void setUn_act_count(int un_act_count) {
		this.un_act_count = un_act_count;
	}
	public int getAct_count_sum() {
		return act_count_sum;
	}
	public void setAct_count_sum(int act_count_sum) {
		this.act_count_sum = act_count_sum;
	}
	public int getUn_act_count_sum() {
		return un_act_count_sum;
	}
	public void setUn_act_count_sum(int un_act_count_sum) {
		this.un_act_count_sum = un_act_count_sum;
	}
	public int getYear_month() {
		return year_month;
	}
	public void setYear_month(int year_month) {
		this.year_month = year_month;
	}
	public Date getRefresh() {
		return refresh;
	}
	public void setRefresh(Date refresh) {
		this.refresh = refresh;
	}

}
