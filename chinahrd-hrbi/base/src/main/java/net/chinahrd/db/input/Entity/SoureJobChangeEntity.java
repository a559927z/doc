package net.chinahrd.db.input.Entity;

import java.io.Serializable;
import java.util.Date;

public class SoureJobChangeEntity implements Serializable {
	private static final long serialVersionUID = -8851987240496616439L;

	private String emp_job_change_id;

	private String customer_id;

	private String emp_id;

	private String emp_key;

	private Date change_date;

	private int change_type_id;

	private String organization_id;

	private String organization_name;

	private String position_id;

	private String position_name;

	private String sequence_id;

	private String sequence_name;

	private String sequence_sub_id;

	private String sequence_sub_name;

	private String ability_id;

	private String ability_name;

	private String job_title_id;

	private String job_title_name;

	private String ability_lv_id;

	private String ability_lv_name;

	private String rank_name;

	private String rank_name_ed;
	private String position_id_ed;
	private String position_name_ed;
	private Date refresh;

	public String getEmp_job_change_id() {
		return this.emp_job_change_id;
	}

	public void setEmp_job_change_id(String emp_job_change_id) {
		this.emp_job_change_id = emp_job_change_id;
	}

	public String getCustomer_id() {
		return this.customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getEmp_id() {
		return this.emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_key() {
		return this.emp_key;
	}

	public void setEmp_key(String emp_key) {
		this.emp_key = emp_key;
	}

	public int getChange_type_id() {
		return change_type_id;
	}

	public void setChange_type_id(int change_type_id) {
		this.change_type_id = change_type_id;
	}

	public String getOrganization_id() {
		return this.organization_id;
	}

	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	public String getOrganization_name() {
		return this.organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getPosition_id() {
		return this.position_id;
	}

	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}

	public String getPosition_name() {
		return this.position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}

	public String getSequence_id() {
		return this.sequence_id;
	}

	public void setSequence_id(String sequence_id) {
		this.sequence_id = sequence_id;
	}

	public String getSequence_name() {
		return this.sequence_name;
	}

	public void setSequence_name(String sequence_name) {
		this.sequence_name = sequence_name;
	}

	public String getSequence_sub_id() {
		return this.sequence_sub_id;
	}

	public void setSequence_sub_id(String sequence_sub_id) {
		this.sequence_sub_id = sequence_sub_id;
	}

	public String getSequence_sub_name() {
		return this.sequence_sub_name;
	}

	public void setSequence_sub_name(String sequence_sub_name) {
		this.sequence_sub_name = sequence_sub_name;
	}

	public String getAbility_id() {
		return this.ability_id;
	}

	public void setAbility_id(String ability_id) {
		this.ability_id = ability_id;
	}

	public String getAbility_name() {
		return this.ability_name;
	}

	public void setAbility_name(String ability_name) {
		this.ability_name = ability_name;
	}

	public String getJob_title_id() {
		return this.job_title_id;
	}

	public void setJob_title_id(String job_title_id) {
		this.job_title_id = job_title_id;
	}

	public String getJob_title_name() {
		return this.job_title_name;
	}

	public void setJob_title_name(String job_title_name) {
		this.job_title_name = job_title_name;
	}

	public String getAbility_lv_id() {
		return this.ability_lv_id;
	}

	public void setAbility_lv_id(String ability_lv_id) {
		this.ability_lv_id = ability_lv_id;
	}

	public String getAbility_lv_name() {
		return this.ability_lv_name;
	}

	public void setAbility_lv_name(String ability_lv_name) {
		this.ability_lv_name = ability_lv_name;
	}

	public String getRank_name() {
		return this.rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

	public String getRank_name_ed() {
		return rank_name_ed;
	}

	public void setRank_name_ed(String rank_name_ed) {
		this.rank_name_ed = rank_name_ed;
	}

	public String getPosition_id_ed() {
		return position_id_ed;
	}

	public void setPosition_id_ed(String position_id_ed) {
		this.position_id_ed = position_id_ed;
	}

	public String getPosition_name_ed() {
		return position_name_ed;
	}

	public void setPosition_name_ed(String position_name_ed) {
		this.position_name_ed = position_name_ed;
	}

	public Date getRefresh() {
		return refresh;
	}

	public void setRefresh(Date refresh) {
		this.refresh = refresh;
	}

	public Date getChange_date() {
		return change_date;
	}

	public void setChange_date(Date change_date) {
		this.change_date = change_date;
	}

}
