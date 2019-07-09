package net.chinahrd.db.input.Entity;

import java.io.Serializable;
import java.util.Date;

public class SoureVDimEmpEntity implements Serializable {

	private static final long serialVersionUID = 1011551873308937607L;
	private String c_id;
	private String dim_emp_id;
	private String v_dim_emp_id;
	private String id;
	private String customer_id;
	private String emp_id;
	private String emp_key;
	private String user_name;
	private String user_name_ch;
	private String emp_hf_id;
	private String emp_hf_key;
	private String email;
	private String img_path;

	private String passtime_or_fulltime;
	private String contract;
	private String blood;
	private Double age;
	private Double company_age;

	private String degree_id;

	private String is_key_talent;
	private String sex;
	private String nation;
	private String degree;
	private String native_place;
	private String country;
	private String birth_place;
	private String national_id;
	private Date birth_date;
	private String national_type;
	private String marry_status;
	private String patty_name;
	private String position_id;
	private String position_name;
	private String organization_parent_id;
	private String organization_parent_name;
	private String organization_id;
	private String organization_name;
	private String sequence_id;
	private String sequence_name;
	private String sequence_sub_id;
	private String sequence_sub_name;
	private String performance_id;
	private String performance_name;
	private String key_talent_type_id;
	private String key_talent_type_name;
	private String key_talent_type_key;

	private String ability_id;
	private String job_title_id;
	private String ability_lv_id;
	private String ability_name;
	private String job_title_name;
	private String ability_lv_name;
	private String rank_name;

	private String population_id;
	private String population_name;
	private String area_id;

	
	private String run_off_date;
	private Date entry_date;

	private String tel_phone;
	private String qq;
	private String wx_code;
	private String address;
	private String contract_unit;
	private String work_place_id;
	private String work_place;
	private String regular_date;
	private String channel_id;
	private String speciality;
	private String is_regular;
	private String refresh_date;
	private  String apply_type;
	private String days;
	private int year;
	
	
	private SoureVDimEmpEntity hf;

	private String report_relation;	//数据库带出
	private String report_relation2;	// 程序生成
	
	

	public String getDim_emp_id() {
		return dim_emp_id;
	}

	public void setDim_emp_id(String dim_emp_id) {
		this.dim_emp_id = dim_emp_id;
	}

	public String getApply_type() {
		return apply_type;
	}

	public void setApply_type(String apply_type) {
		this.apply_type = apply_type;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getFullPath() {
		if (hf != null) {
			return hf.getFullPath() + "/" + emp_key;
		} else {
			return emp_key;
		}
	}

	public void setHf(SoureVDimEmpEntity hf) {
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

	
	public String getV_dim_emp_id() {
		return v_dim_emp_id;
	}

	public void setV_dim_emp_id(String v_dim_emp_id) {
		this.v_dim_emp_id = v_dim_emp_id;
	}

	public String getOrganization_key() {
		return organization_key;
	}

	public void setOrganization_key(String organization_key) {
		this.organization_key = organization_key;
	}

	public String getReport_relation() {
		return report_relation;
	}
	public void setReport_relation(String report_relation) {
		this.report_relation = report_relation;
	}
	public String getEmp_hf_key() {
		return emp_hf_key;
	}
	public void setEmp_hf_key(String emp_hf_key) {
		this.emp_hf_key = emp_hf_key;
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
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_key() {
		return emp_key;
	}
	public void setEmp_key(String emp_key) {
		this.emp_key = emp_key;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_name_ch() {
		return user_name_ch;
	}
	public void setUser_name_ch(String user_name_ch) {
		this.user_name_ch = user_name_ch;
	}
	public String getEmp_hf_id() {
		return emp_hf_id;
	}
	public void setEmp_hf_id(String emp_hf_id) {
		this.emp_hf_id = emp_hf_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public String getPasstime_or_fulltime() {
		return passtime_or_fulltime;
	}
	public void setPasstime_or_fulltime(String passtime_or_fulltime) {
		this.passtime_or_fulltime = passtime_or_fulltime;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public Double getAge() {
		return age;
	}
	public void setAge(Double age) {
		this.age = age;
	}

	public Double getCompany_age() {
		return company_age;
	}

	public void setCompany_age(Double company_age) {
		this.company_age = company_age;
	}

	public String getIs_key_talent() {
		return is_key_talent;
	}
	public void setIs_key_talent(String is_key_talent) {
		this.is_key_talent = is_key_talent;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getNative_place() {
		return native_place;
	}
	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBirth_place() {
		return birth_place;
	}
	public void setBirth_place(String birth_place) {
		this.birth_place = birth_place;
	}
	public String getNational_id() {
		return national_id;
	}
	public void setNational_id(String national_id) {
		this.national_id = national_id;
	}
	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public String getNational_type() {
		return national_type;
	}
	public void setNational_type(String national_type) {
		this.national_type = national_type;
	}
	public String getMarry_status() {
		return marry_status;
	}
	public void setMarry_status(String marry_status) {
		this.marry_status = marry_status;
	}
	public String getPatty_name() {
		return patty_name;
	}
	public void setPatty_name(String patty_name) {
		this.patty_name = patty_name;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public String getOrganization_parent_id() {
		return organization_parent_id;
	}
	public void setOrganization_parent_id(String organization_parent_id) {
		this.organization_parent_id = organization_parent_id;
	}
	public String getOrganization_parent_name() {
		return organization_parent_name;
	}
	public void setOrganization_parent_name(String organization_parent_name) {
		this.organization_parent_name = organization_parent_name;
	}
	public String getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}
	public String getOrganization_name() {
		return organization_name;
	}
	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}
	public String getSequence_id() {
		return sequence_id;
	}
	public void setSequence_id(String sequence_id) {
		this.sequence_id = sequence_id;
	}
	public String getSequence_name() {
		return sequence_name;
	}
	public void setSequence_name(String sequence_name) {
		this.sequence_name = sequence_name;
	}
	public String getSequence_sub_id() {
		return sequence_sub_id;
	}
	public void setSequence_sub_id(String sequence_sub_id) {
		this.sequence_sub_id = sequence_sub_id;
	}
	public String getSequence_sub_name() {
		return sequence_sub_name;
	}
	public void setSequence_sub_name(String sequence_sub_name) {
		this.sequence_sub_name = sequence_sub_name;
	}
	public String getPerformance_id() {
		return performance_id;
	}
	public void setPerformance_id(String performance_id) {
		this.performance_id = performance_id;
	}
	public String getPerformance_name() {
		return performance_name;
	}
	public void setPerformance_name(String performance_name) {
		this.performance_name = performance_name;
	}
	public String getKey_talent_type_id() {
		return key_talent_type_id;
	}
	public void setKey_talent_type_id(String key_talent_type_id) {
		this.key_talent_type_id = key_talent_type_id;
	}
	public String getKey_talent_type_name() {
		return key_talent_type_name;
	}
	public void setKey_talent_type_name(String key_talent_type_name) {
		this.key_talent_type_name = key_talent_type_name;
	}
	public String getAbility_id() {
		return ability_id;
	}
	public void setAbility_id(String ability_id) {
		this.ability_id = ability_id;
	}
	public String getJob_title_id() {
		return job_title_id;
	}
	public void setJob_title_id(String job_title_id) {
		this.job_title_id = job_title_id;
	}
	public String getAbility_lv_id() {
		return ability_lv_id;
	}
	public void setAbility_lv_id(String ability_lv_id) {
		this.ability_lv_id = ability_lv_id;
	}
	public String getAbility_name() {
		return ability_name;
	}
	public void setAbility_name(String ability_name) {
		this.ability_name = ability_name;
	}
	public String getJob_title_name() {
		return job_title_name;
	}
	public void setJob_title_name(String job_title_name) {
		this.job_title_name = job_title_name;
	}
	public String getAbility_lv_name() {
		return ability_lv_name;
	}
	public void setAbility_lv_name(String ability_lv_name) {
		this.ability_lv_name = ability_lv_name;
	}
	public String getRank_name() {
		return rank_name;
	}
	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	public String getRun_off_date() {
		return run_off_date;
	}
	public void setRun_off_date(String run_off_date) {
		this.run_off_date = run_off_date;
	}

	
	public Date getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}

	public String getTel_phone() {
		return tel_phone;
	}
	public void setTel_phone(String tel_phone) {
		this.tel_phone = tel_phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWx_code() {
		return wx_code;
	}
	public void setWx_code(String wx_code) {
		this.wx_code = wx_code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContract_unit() {
		return contract_unit;
	}
	public void setContract_unit(String contract_unit) {
		this.contract_unit = contract_unit;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	public String getRegular_date() {
		return regular_date;
	}
	public void setRegular_date(String regular_date) {
		this.regular_date = regular_date;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getIs_regular() {
		return is_regular;
	}
	public void setIs_regular(String is_regular) {
		this.is_regular = is_regular;
	}
	public String getRefresh_date() {
		return refresh_date;
	}
	public void setRefresh_date(String refresh_date) {
		this.refresh_date = refresh_date;
	}

	public String getKey_talent_type_key() {
		return key_talent_type_key;
	}

	public void setKey_talent_type_key(String key_talent_type_key) {
		this.key_talent_type_key = key_talent_type_key;
	}

	public String getDegree_id() {
		return degree_id;
	}

	public void setDegree_id(String degree_id) {
		this.degree_id = degree_id;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public String getPopulation_id() {
		return population_id;
	}

	public void setPopulation_id(String population_id) {
		this.population_id = population_id;
	}

	public String getPopulation_name() {
		return population_name;
	}

	public void setPopulation_name(String population_name) {
		this.population_name = population_name;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getWork_place_id() {
		return work_place_id;
	}

	public void setWork_place_id(String work_place_id) {
		this.work_place_id = work_place_id;
	}
	
}
