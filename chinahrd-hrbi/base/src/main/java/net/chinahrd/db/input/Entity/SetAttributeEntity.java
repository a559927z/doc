package net.chinahrd.db.input.Entity;

import java.io.Serializable;
import java.util.Date;

public class SetAttributeEntity implements Serializable {

	private static final long serialVersionUID = 1011551873308937607L;
	private String v_dim_emp_id;
	private String emp_key;
	private String emp_hf_id;
	private String emp_hf_key;
	private SetAttributeEntity hf;
	private Date birth_date;
	private Date entry_date;
	private Double company_age;

	private String report_relation; // 数据库带出
	private String report_relation2; // 程序生成

	public String getFullPath() {
		if (hf != null) {
			return hf.getFullPath() + "/" + emp_key;
		} else {
			return emp_key;
		}
	}

	public void setHf(SetAttributeEntity hf) {
		this.hf = hf;
	}

	private String full_path;
	private String organization_key;

	public String getFull_path() {
		return full_path;
	}

	public void setFull_path(String full_path) {
		this.full_path = full_path;
	}

	public String getReport_relation2() {
		return report_relation2;
	}

	public void setReport_relation2(String report_relation2) {
		this.report_relation2 = report_relation2;
	}

	public String getEmp_key() {
		return emp_key;
	}

	public void setEmp_key(String emp_key) {
		this.emp_key = emp_key;
	}

	public String getEmp_hf_id() {
		return emp_hf_id;
	}

	public void setEmp_hf_id(String emp_hf_id) {
		this.emp_hf_id = emp_hf_id;
	}

	public String getEmp_hf_key() {
		return emp_hf_key;
	}

	public void setEmp_hf_key(String emp_hf_key) {
		this.emp_hf_key = emp_hf_key;
	}

	public String getReport_relation() {
		return report_relation;
	}

	public void setReport_relation(String report_relation) {
		this.report_relation = report_relation;
	}

	public String getOrganization_key() {
		return organization_key;
	}

	public void setOrganization_key(String organization_key) {
		this.organization_key = organization_key;
	}

	public String getV_dim_emp_id() {
		return v_dim_emp_id;
	}

	public void setV_dim_emp_id(String v_dim_emp_id) {
		this.v_dim_emp_id = v_dim_emp_id;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public Date getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}

	public Double getCompany_age() {
		return company_age;
	}

	public void setCompany_age(Double company_age) {
		this.company_age = company_age;
	}

	
}
