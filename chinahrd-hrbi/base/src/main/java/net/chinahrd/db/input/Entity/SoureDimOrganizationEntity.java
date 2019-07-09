package net.chinahrd.db.input.Entity;

import java.io.Serializable;

import net.chinahrd.utils.DateUtil;

public class SoureDimOrganizationEntity implements Serializable {

	private static final long serialVersionUID = 316660467232399799L;

	private String id;
	private String c_id;
	private String customer_id;
	private String organization_type_id;
	private String organization_type_key;
	private String organization_key;
	private String organization_parent_id;
	private String organization_parent_key;
	private String organization_name;
	private String organization_name_full;
	private String organization_company_id;
	private String note;
	private String is_single;
	private String full_path; // 数据库带出
	private String full_path2;// 程序生成
	private int has_children = 0;
	private int depth = 0;
	private String profession_id;
	
	private boolean isAdd = true;

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public String getFull_path2() {
		return full_path2;
	}

	public void setFull_path2(String full_path2) {
		this.full_path2 = full_path2;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public String getOrganization_company_id() {
		return organization_company_id;
	}

	public void setOrganization_company_id(String organization_company_id) {
		this.organization_company_id = organization_company_id;
	}

	public String getProfession_id() {
		return profession_id;
	}

	public void setProfession_id(String profession_id) {
		this.profession_id = profession_id;
	}

	private String refresh_date = DateUtil.getDBNow();

	private SoureDimOrganizationEntity hf;

	public void setHf(SoureDimOrganizationEntity hf) {
		this.hf = hf;
	}

	public String getFp() {

		if (null != hf) {
			return hf.getFp() + "_" + organization_key;
		} else {
			return organization_key;
		}
	}

	public String getOrganization_name_full() {
		return organization_name_full;
	}

	public void setOrganization_name_full(String organization_name_full) {
		this.organization_name_full = organization_name_full;
	}

	public String getRefresh_date() {
		return refresh_date;
	}

	public void setRefresh_date(String refresh_date) {
		this.refresh_date = refresh_date;
	}

	public String getOrganization_type_key() {
		return organization_type_key;
	}
	public void setOrganization_type_key(String organization_type_key) {
		this.organization_type_key = organization_type_key;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFull_path() {
		return full_path;
	}
	public void setFull_path(String full_path) {
		this.full_path = full_path;
	}
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getOrganization_type_id() {
		return organization_type_id;
	}
	public void setOrganization_type_id(String organization_type_id) {
		this.organization_type_id = organization_type_id;
	}
	public String getOrganization_key() {
		return organization_key;
	}
	public void setOrganization_key(String organization_key) {
		this.organization_key = organization_key;
	}
	public String getOrganization_parent_id() {
		return organization_parent_id;
	}
	public void setOrganization_parent_id(String organization_parent_id) {
		this.organization_parent_id = organization_parent_id;
	}
	public String getOrganization_parent_key() {
		return organization_parent_key;
	}
	public void setOrganization_parent_key(String organization_parent_key) {
		this.organization_parent_key = organization_parent_key;
	}
	public String getOrganization_name() {
		return organization_name;
	}
	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}
	public String getIs_single() {
		return is_single;
	}
	public void setIs_single(String is_single) {
		this.is_single = is_single;
	}

	public int getHas_children() {
		return has_children;
	}

	public void setHas_children(int has_children) {
		this.has_children = has_children;
	}

}
