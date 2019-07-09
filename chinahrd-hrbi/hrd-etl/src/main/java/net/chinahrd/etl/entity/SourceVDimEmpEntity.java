package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_v_dim_emp")
public class SourceVDimEmpEntity implements Entity{

	private String _v_dim_emp_id;
	private String _emp_id;
	private String _customer_id;
	private String _emp_key;
	private String _user_name;
	private String _user_name_ch;
	private String _emp_hf_id;
	private String _emp_hf_key;
	private String _email;
	private String _img_path;
	private String _passtime_or_fulltime;
	private String _contract;
	private String _blood;
	private int _age;
	private String _sex;
	private String _nation;
	private String _degree_id;
	private String _degree;
	private String _native_place;
	private String _country;
	private String _birth_place;
	private String _birth_date;
	private String _national_id;
	private String _national_type;
	private int _marry_status;
	private String _patty_name;
	private int _company_age;
	private int _is_key_talent;
	private String _organization_parent_id;
	private String _organization_parent_name;
	private String _organization_id;
	private String _organization_name;
	private String _position_id;
	private String _position_name;
	private String _sequence_id;
	private String _sequence_name;
	private String _sequence_sub_id;
	private String _sequence_sub_name;
	private String _ability_id;
	private String _ability_name;
	private String _job_title_id;
	private String _job_title_name;
	private String _ability_lv_id;
	private String _ability_lv_name;
	private String _rank_name;
	private String _performance_id;
	private String _performance_name;
	private String _population_id;
	private String _population_name;
	private String _tel_phone;
	private String _qq;
	private String _wx_code;
	private String _address;
	private String _contract_unit;
	private String _work_place_id;
	private String _work_place;
	private String _regular_date;
	private String _channel_id;
	private String _speciality;
	private int _is_regular;
	private String _area_id;
	private String _entry_date;
	private String _run_off_date;
	private String _refresh_date;
	private int _mark;

	public SourceVDimEmpEntity(){
		super();
	}

	public SourceVDimEmpEntity(String _v_dim_emp_id,String _emp_id,String _customer_id,String _emp_key,String _user_name,String _user_name_ch,String _emp_hf_id,String _emp_hf_key,String _email,String _img_path,String _passtime_or_fulltime,String _contract,String _blood,int _age,String _sex,String _nation,String _degree_id,String _degree,String _native_place,String _country,String _birth_place,String _birth_date,String _national_id,String _national_type,int _marry_status,String _patty_name,int _company_age,int _is_key_talent,String _organization_parent_id,String _organization_parent_name,String _organization_id,String _organization_name,String _position_id,String _position_name,String _sequence_id,String _sequence_name,String _sequence_sub_id,String _sequence_sub_name,String _ability_id,String _ability_name,String _job_title_id,String _job_title_name,String _ability_lv_id,String _ability_lv_name,String _rank_name,String _performance_id,String _performance_name,String _population_id,String _population_name,String _tel_phone,String _qq,String _wx_code,String _address,String _contract_unit,String _work_place_id,String _work_place,String _regular_date,String _channel_id,String _speciality,int _is_regular,String _area_id,String _entry_date,String _run_off_date,String _refresh_date,int _mark){
		this._v_dim_emp_id = _v_dim_emp_id;
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._emp_key = _emp_key;
		this._user_name = _user_name;
		this._user_name_ch = _user_name_ch;
		this._emp_hf_id = _emp_hf_id;
		this._emp_hf_key = _emp_hf_key;
		this._email = _email;
		this._img_path = _img_path;
		this._passtime_or_fulltime = _passtime_or_fulltime;
		this._contract = _contract;
		this._blood = _blood;
		this._age = _age;
		this._sex = _sex;
		this._nation = _nation;
		this._degree_id = _degree_id;
		this._degree = _degree;
		this._native_place = _native_place;
		this._country = _country;
		this._birth_place = _birth_place;
		this._birth_date = _birth_date;
		this._national_id = _national_id;
		this._national_type = _national_type;
		this._marry_status = _marry_status;
		this._patty_name = _patty_name;
		this._company_age = _company_age;
		this._is_key_talent = _is_key_talent;
		this._organization_parent_id = _organization_parent_id;
		this._organization_parent_name = _organization_parent_name;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._position_id = _position_id;
		this._position_name = _position_name;
		this._sequence_id = _sequence_id;
		this._sequence_name = _sequence_name;
		this._sequence_sub_id = _sequence_sub_id;
		this._sequence_sub_name = _sequence_sub_name;
		this._ability_id = _ability_id;
		this._ability_name = _ability_name;
		this._job_title_id = _job_title_id;
		this._job_title_name = _job_title_name;
		this._ability_lv_id = _ability_lv_id;
		this._ability_lv_name = _ability_lv_name;
		this._rank_name = _rank_name;
		this._performance_id = _performance_id;
		this._performance_name = _performance_name;
		this._population_id = _population_id;
		this._population_name = _population_name;
		this._tel_phone = _tel_phone;
		this._qq = _qq;
		this._wx_code = _wx_code;
		this._address = _address;
		this._contract_unit = _contract_unit;
		this._work_place_id = _work_place_id;
		this._work_place = _work_place;
		this._regular_date = _regular_date;
		this._channel_id = _channel_id;
		this._speciality = _speciality;
		this._is_regular = _is_regular;
		this._area_id = _area_id;
		this._entry_date = _entry_date;
		this._run_off_date = _run_off_date;
		this._refresh_date = _refresh_date;
		this._mark = _mark;
	}

	@Override
	public String toString() {
		return "SourceVDimEmpEntity ["+
			"	v_dim_emp_id="+_v_dim_emp_id+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	emp_key="+_emp_key+
			"	user_name="+_user_name+
			"	user_name_ch="+_user_name_ch+
			"	emp_hf_id="+_emp_hf_id+
			"	emp_hf_key="+_emp_hf_key+
			"	email="+_email+
			"	img_path="+_img_path+
			"	passtime_or_fulltime="+_passtime_or_fulltime+
			"	contract="+_contract+
			"	blood="+_blood+
			"	age="+_age+
			"	sex="+_sex+
			"	nation="+_nation+
			"	degree_id="+_degree_id+
			"	degree="+_degree+
			"	native_place="+_native_place+
			"	country="+_country+
			"	birth_place="+_birth_place+
			"	birth_date="+_birth_date+
			"	national_id="+_national_id+
			"	national_type="+_national_type+
			"	marry_status="+_marry_status+
			"	patty_name="+_patty_name+
			"	company_age="+_company_age+
			"	is_key_talent="+_is_key_talent+
			"	organization_parent_id="+_organization_parent_id+
			"	organization_parent_name="+_organization_parent_name+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	position_id="+_position_id+
			"	position_name="+_position_name+
			"	sequence_id="+_sequence_id+
			"	sequence_name="+_sequence_name+
			"	sequence_sub_id="+_sequence_sub_id+
			"	sequence_sub_name="+_sequence_sub_name+
			"	ability_id="+_ability_id+
			"	ability_name="+_ability_name+
			"	job_title_id="+_job_title_id+
			"	job_title_name="+_job_title_name+
			"	ability_lv_id="+_ability_lv_id+
			"	ability_lv_name="+_ability_lv_name+
			"	rank_name="+_rank_name+
			"	performance_id="+_performance_id+
			"	performance_name="+_performance_name+
			"	population_id="+_population_id+
			"	population_name="+_population_name+
			"	tel_phone="+_tel_phone+
			"	qq="+_qq+
			"	wx_code="+_wx_code+
			"	address="+_address+
			"	contract_unit="+_contract_unit+
			"	work_place_id="+_work_place_id+
			"	work_place="+_work_place+
			"	regular_date="+_regular_date+
			"	channel_id="+_channel_id+
			"	speciality="+_speciality+
			"	is_regular="+_is_regular+
			"	area_id="+_area_id+
			"	entry_date="+_entry_date+
			"	run_off_date="+_run_off_date+
			"	refresh_date="+_refresh_date+
			"	mark="+_mark+
			"]";
	}

	@Column(name = "v_dim_emp_id",type=ColumnType.VARCHAR)
	public String getVDimEmpId(){
		return this._v_dim_emp_id; 
	}

	public void setVDimEmpId(String _v_dim_emp_id){
		this._v_dim_emp_id = _v_dim_emp_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_key",type=ColumnType.VARCHAR)
	public String getEmpKey(){
		return this._emp_key; 
	}

	public void setEmpKey(String _emp_key){
		this._emp_key = _emp_key;
	}

	@Column(name = "user_name",type=ColumnType.VARCHAR)
	public String getUserName(){
		return this._user_name; 
	}

	public void setUserName(String _user_name){
		this._user_name = _user_name;
	}

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "emp_hf_id",type=ColumnType.VARCHAR)
	public String getEmpHfId(){
		return this._emp_hf_id; 
	}

	public void setEmpHfId(String _emp_hf_id){
		this._emp_hf_id = _emp_hf_id;
	}

	@Column(name = "emp_hf_key",type=ColumnType.VARCHAR)
	public String getEmpHfKey(){
		return this._emp_hf_key; 
	}

	public void setEmpHfKey(String _emp_hf_key){
		this._emp_hf_key = _emp_hf_key;
	}

	@Column(name = "email",type=ColumnType.VARCHAR)
	public String getEmail(){
		return this._email; 
	}

	public void setEmail(String _email){
		this._email = _email;
	}

	@Column(name = "img_path",type=ColumnType.VARCHAR)
	public String getImgPath(){
		return this._img_path; 
	}

	public void setImgPath(String _img_path){
		this._img_path = _img_path;
	}

	@Column(name = "passtime_or_fulltime",type=ColumnType.VARCHAR)
	public String getPasstimeOrFulltime(){
		return this._passtime_or_fulltime; 
	}

	public void setPasstimeOrFulltime(String _passtime_or_fulltime){
		this._passtime_or_fulltime = _passtime_or_fulltime;
	}

	@Column(name = "contract",type=ColumnType.VARCHAR)
	public String getContract(){
		return this._contract; 
	}

	public void setContract(String _contract){
		this._contract = _contract;
	}

	@Column(name = "blood",type=ColumnType.VARCHAR)
	public String getBlood(){
		return this._blood; 
	}

	public void setBlood(String _blood){
		this._blood = _blood;
	}

	@Column(name = "age",type=ColumnType.INT)
	public int getAge(){
		return this._age; 
	}

	public void setAge(int _age){
		this._age = _age;
	}

	@Column(name = "sex",type=ColumnType.VARCHAR)
	public String getSex(){
		return this._sex; 
	}

	public void setSex(String _sex){
		this._sex = _sex;
	}

	@Column(name = "nation",type=ColumnType.VARCHAR)
	public String getNation(){
		return this._nation; 
	}

	public void setNation(String _nation){
		this._nation = _nation;
	}

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "degree",type=ColumnType.VARCHAR)
	public String getDegree(){
		return this._degree; 
	}

	public void setDegree(String _degree){
		this._degree = _degree;
	}

	@Column(name = "native_place",type=ColumnType.VARCHAR)
	public String getNativePlace(){
		return this._native_place; 
	}

	public void setNativePlace(String _native_place){
		this._native_place = _native_place;
	}

	@Column(name = "country",type=ColumnType.VARCHAR)
	public String getCountry(){
		return this._country; 
	}

	public void setCountry(String _country){
		this._country = _country;
	}

	@Column(name = "birth_place",type=ColumnType.VARCHAR)
	public String getBirthPlace(){
		return this._birth_place; 
	}

	public void setBirthPlace(String _birth_place){
		this._birth_place = _birth_place;
	}

	@Column(name = "birth_date",type=ColumnType.DATETIME)
	public String getBirthDate(){
		return this._birth_date; 
	}

	public void setBirthDate(String _birth_date){
		this._birth_date = _birth_date;
	}

	@Column(name = "national_id",type=ColumnType.VARCHAR)
	public String getNationalId(){
		return this._national_id; 
	}

	public void setNationalId(String _national_id){
		this._national_id = _national_id;
	}

	@Column(name = "national_type",type=ColumnType.VARCHAR)
	public String getNationalType(){
		return this._national_type; 
	}

	public void setNationalType(String _national_type){
		this._national_type = _national_type;
	}

	@Column(name = "marry_status",type=ColumnType.INT)
	public int getMarryStatus(){
		return this._marry_status; 
	}

	public void setMarryStatus(int _marry_status){
		this._marry_status = _marry_status;
	}

	@Column(name = "patty_name",type=ColumnType.VARCHAR)
	public String getPattyName(){
		return this._patty_name; 
	}

	public void setPattyName(String _patty_name){
		this._patty_name = _patty_name;
	}

	@Column(name = "company_age",type=ColumnType.INT)
	public int getCompanyAge(){
		return this._company_age; 
	}

	public void setCompanyAge(int _company_age){
		this._company_age = _company_age;
	}

	@Column(name = "is_key_talent",type=ColumnType.INT)
	public int getIsKeyTalent(){
		return this._is_key_talent; 
	}

	public void setIsKeyTalent(int _is_key_talent){
		this._is_key_talent = _is_key_talent;
	}

	@Column(name = "organization_parent_id",type=ColumnType.VARCHAR)
	public String getOrganizationParentId(){
		return this._organization_parent_id; 
	}

	public void setOrganizationParentId(String _organization_parent_id){
		this._organization_parent_id = _organization_parent_id;
	}

	@Column(name = "organization_parent_name",type=ColumnType.VARCHAR)
	public String getOrganizationParentName(){
		return this._organization_parent_name; 
	}

	public void setOrganizationParentName(String _organization_parent_name){
		this._organization_parent_name = _organization_parent_name;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "organization_name",type=ColumnType.VARCHAR)
	public String getOrganizationName(){
		return this._organization_name; 
	}

	public void setOrganizationName(String _organization_name){
		this._organization_name = _organization_name;
	}

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
	}

	@Column(name = "position_name",type=ColumnType.VARCHAR)
	public String getPositionName(){
		return this._position_name; 
	}

	public void setPositionName(String _position_name){
		this._position_name = _position_name;
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "sequence_name",type=ColumnType.VARCHAR)
	public String getSequenceName(){
		return this._sequence_name; 
	}

	public void setSequenceName(String _sequence_name){
		this._sequence_name = _sequence_name;
	}

	@Column(name = "sequence_sub_id",type=ColumnType.VARCHAR)
	public String getSequenceSubId(){
		return this._sequence_sub_id; 
	}

	public void setSequenceSubId(String _sequence_sub_id){
		this._sequence_sub_id = _sequence_sub_id;
	}

	@Column(name = "sequence_sub_name",type=ColumnType.VARCHAR)
	public String getSequenceSubName(){
		return this._sequence_sub_name; 
	}

	public void setSequenceSubName(String _sequence_sub_name){
		this._sequence_sub_name = _sequence_sub_name;
	}

	@Column(name = "ability_id",type=ColumnType.VARCHAR)
	public String getAbilityId(){
		return this._ability_id; 
	}

	public void setAbilityId(String _ability_id){
		this._ability_id = _ability_id;
	}

	@Column(name = "ability_name",type=ColumnType.VARCHAR)
	public String getAbilityName(){
		return this._ability_name; 
	}

	public void setAbilityName(String _ability_name){
		this._ability_name = _ability_name;
	}

	@Column(name = "job_title_id",type=ColumnType.VARCHAR)
	public String getJobTitleId(){
		return this._job_title_id; 
	}

	public void setJobTitleId(String _job_title_id){
		this._job_title_id = _job_title_id;
	}

	@Column(name = "job_title_name",type=ColumnType.VARCHAR)
	public String getJobTitleName(){
		return this._job_title_name; 
	}

	public void setJobTitleName(String _job_title_name){
		this._job_title_name = _job_title_name;
	}

	@Column(name = "ability_lv_id",type=ColumnType.VARCHAR)
	public String getAbilityLvId(){
		return this._ability_lv_id; 
	}

	public void setAbilityLvId(String _ability_lv_id){
		this._ability_lv_id = _ability_lv_id;
	}

	@Column(name = "ability_lv_name",type=ColumnType.VARCHAR)
	public String getAbilityLvName(){
		return this._ability_lv_name; 
	}

	public void setAbilityLvName(String _ability_lv_name){
		this._ability_lv_name = _ability_lv_name;
	}

	@Column(name = "rank_name",type=ColumnType.VARCHAR)
	public String getRankName(){
		return this._rank_name; 
	}

	public void setRankName(String _rank_name){
		this._rank_name = _rank_name;
	}

	@Column(name = "performance_id",type=ColumnType.VARCHAR)
	public String getPerformanceId(){
		return this._performance_id; 
	}

	public void setPerformanceId(String _performance_id){
		this._performance_id = _performance_id;
	}

	@Column(name = "performance_name",type=ColumnType.VARCHAR)
	public String getPerformanceName(){
		return this._performance_name; 
	}

	public void setPerformanceName(String _performance_name){
		this._performance_name = _performance_name;
	}

	@Column(name = "population_id",type=ColumnType.VARCHAR)
	public String getPopulationId(){
		return this._population_id; 
	}

	public void setPopulationId(String _population_id){
		this._population_id = _population_id;
	}

	@Column(name = "population_name",type=ColumnType.VARCHAR)
	public String getPopulationName(){
		return this._population_name; 
	}

	public void setPopulationName(String _population_name){
		this._population_name = _population_name;
	}

	@Column(name = "tel_phone",type=ColumnType.VARCHAR)
	public String getTelPhone(){
		return this._tel_phone; 
	}

	public void setTelPhone(String _tel_phone){
		this._tel_phone = _tel_phone;
	}

	@Column(name = "qq",type=ColumnType.VARCHAR)
	public String getQq(){
		return this._qq; 
	}

	public void setQq(String _qq){
		this._qq = _qq;
	}

	@Column(name = "wx_code",type=ColumnType.VARCHAR)
	public String getWxCode(){
		return this._wx_code; 
	}

	public void setWxCode(String _wx_code){
		this._wx_code = _wx_code;
	}

	@Column(name = "address",type=ColumnType.VARCHAR)
	public String getAddress(){
		return this._address; 
	}

	public void setAddress(String _address){
		this._address = _address;
	}

	@Column(name = "contract_unit",type=ColumnType.VARCHAR)
	public String getContractUnit(){
		return this._contract_unit; 
	}

	public void setContractUnit(String _contract_unit){
		this._contract_unit = _contract_unit;
	}

	@Column(name = "work_place_id",type=ColumnType.VARCHAR)
	public String getWorkPlaceId(){
		return this._work_place_id; 
	}

	public void setWorkPlaceId(String _work_place_id){
		this._work_place_id = _work_place_id;
	}

	@Column(name = "work_place",type=ColumnType.VARCHAR)
	public String getWorkPlace(){
		return this._work_place; 
	}

	public void setWorkPlace(String _work_place){
		this._work_place = _work_place;
	}

	@Column(name = "regular_date",type=ColumnType.DATETIME)
	public String getRegularDate(){
		return this._regular_date; 
	}

	public void setRegularDate(String _regular_date){
		this._regular_date = _regular_date;
	}

	@Column(name = "channel_id",type=ColumnType.VARCHAR)
	public String getChannelId(){
		return this._channel_id; 
	}

	public void setChannelId(String _channel_id){
		this._channel_id = _channel_id;
	}

	@Column(name = "speciality",type=ColumnType.VARCHAR)
	public String getSpeciality(){
		return this._speciality; 
	}

	public void setSpeciality(String _speciality){
		this._speciality = _speciality;
	}

	@Column(name = "is_regular",type=ColumnType.INT)
	public int getIsRegular(){
		return this._is_regular; 
	}

	public void setIsRegular(int _is_regular){
		this._is_regular = _is_regular;
	}

	@Column(name = "area_id",type=ColumnType.VARCHAR)
	public String getAreaId(){
		return this._area_id; 
	}

	public void setAreaId(String _area_id){
		this._area_id = _area_id;
	}

	@Column(name = "entry_date",type=ColumnType.DATETIME)
	public String getEntryDate(){
		return this._entry_date; 
	}

	public void setEntryDate(String _entry_date){
		this._entry_date = _entry_date;
	}

	@Column(name = "run_off_date",type=ColumnType.DATETIME)
	public String getRunOffDate(){
		return this._run_off_date; 
	}

	public void setRunOffDate(String _run_off_date){
		this._run_off_date = _run_off_date;
	}

	@Column(name = "refresh_date",type=ColumnType.DATETIME)
	public String getRefreshDate(){
		return this._refresh_date; 
	}

	public void setRefreshDate(String _refresh_date){
		this._refresh_date = _refresh_date;
	}

	@Column(name = "mark",type=ColumnType.TINYINT)
	public int getMark(){
		return this._mark; 
	}

	public void setMark(int _mark){
		this._mark = _mark;
	}

	// 实例化内部类
	public static SourceVDimEmpEntityAuxiliary  getEntityAuxiliary(){
		return new SourceVDimEmpEntityAuxiliary();
	}

	public static class SourceVDimEmpEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceVDimEmpEntityAuxiliary asVDimEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("v_dim_emp_id","`v_dim_emp_id`", colName, "setVDimEmpId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asEmpKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_key","`emp_key`", colName, "setEmpKey", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asUserName(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name","`user_name`", colName, "setUserName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asEmpHfId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_hf_id","`emp_hf_id`", colName, "setEmpHfId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asEmpHfKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_hf_key","`emp_hf_key`", colName, "setEmpHfKey", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asEmail(String colName, CustomColumn<?, ?>... cols){
			setColName("email","`email`", colName, "setEmail", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asImgPath(String colName, CustomColumn<?, ?>... cols){
			setColName("img_path","`img_path`", colName, "setImgPath", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPasstimeOrFulltime(String colName, CustomColumn<?, ?>... cols){
			setColName("passtime_or_fulltime","`passtime_or_fulltime`", colName, "setPasstimeOrFulltime", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asContract(String colName, CustomColumn<?, ?>... cols){
			setColName("contract","`contract`", colName, "setContract", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asBlood(String colName, CustomColumn<?, ?>... cols){
			setColName("blood","`blood`", colName, "setBlood", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAge(String colName, CustomColumn<?, ?>... cols){
			setColName("age","`age`", colName, "setAge", "getInt", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asSex(String colName, CustomColumn<?, ?>... cols){
			setColName("sex","`sex`", colName, "setSex", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asNation(String colName, CustomColumn<?, ?>... cols){
			setColName("nation","`nation`", colName, "setNation", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asDegree(String colName, CustomColumn<?, ?>... cols){
			setColName("degree","`degree`", colName, "setDegree", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asNativePlace(String colName, CustomColumn<?, ?>... cols){
			setColName("native_place","`native_place`", colName, "setNativePlace", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asCountry(String colName, CustomColumn<?, ?>... cols){
			setColName("country","`country`", colName, "setCountry", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asBirthPlace(String colName, CustomColumn<?, ?>... cols){
			setColName("birth_place","`birth_place`", colName, "setBirthPlace", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asBirthDate(String colName, CustomColumn<?, ?>... cols){
			setColName("birth_date","`birth_date`", colName, "setBirthDate", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asNationalId(String colName, CustomColumn<?, ?>... cols){
			setColName("national_id","`national_id`", colName, "setNationalId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asNationalType(String colName, CustomColumn<?, ?>... cols){
			setColName("national_type","`national_type`", colName, "setNationalType", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asMarryStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("marry_status","`marry_status`", colName, "setMarryStatus", "getInt", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPattyName(String colName, CustomColumn<?, ?>... cols){
			setColName("patty_name","`patty_name`", colName, "setPattyName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asCompanyAge(String colName, CustomColumn<?, ?>... cols){
			setColName("company_age","`company_age`", colName, "setCompanyAge", "getInt", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asIsKeyTalent(String colName, CustomColumn<?, ?>... cols){
			setColName("is_key_talent","`is_key_talent`", colName, "setIsKeyTalent", "getInt", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asOrganizationParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_id","`organization_parent_id`", colName, "setOrganizationParentId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asOrganizationParentName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_name","`organization_parent_name`", colName, "setOrganizationParentName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asSequenceName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_name","`sequence_name`", colName, "setSequenceName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asSequenceSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_id","`sequence_sub_id`", colName, "setSequenceSubId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asSequenceSubName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_name","`sequence_sub_name`", colName, "setSequenceSubName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAbilityName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_name","`ability_name`", colName, "setAbilityName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asJobTitleId(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_id","`job_title_id`", colName, "setJobTitleId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asJobTitleName(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_name","`job_title_name`", colName, "setJobTitleName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAbilityLvId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_id","`ability_lv_id`", colName, "setAbilityLvId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAbilityLvName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_name","`ability_lv_name`", colName, "setAbilityLvName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asRankName(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name","`rank_name`", colName, "setRankName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPerformanceId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_id","`performance_id`", colName, "setPerformanceId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPerformanceName(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_name","`performance_name`", colName, "setPerformanceName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPopulationId(String colName, CustomColumn<?, ?>... cols){
			setColName("population_id","`population_id`", colName, "setPopulationId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asPopulationName(String colName, CustomColumn<?, ?>... cols){
			setColName("population_name","`population_name`", colName, "setPopulationName", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asTelPhone(String colName, CustomColumn<?, ?>... cols){
			setColName("tel_phone","`tel_phone`", colName, "setTelPhone", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asQq(String colName, CustomColumn<?, ?>... cols){
			setColName("qq","`qq`", colName, "setQq", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asWxCode(String colName, CustomColumn<?, ?>... cols){
			setColName("wx_code","`wx_code`", colName, "setWxCode", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAddress(String colName, CustomColumn<?, ?>... cols){
			setColName("address","`address`", colName, "setAddress", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asContractUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("contract_unit","`contract_unit`", colName, "setContractUnit", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asWorkPlaceId(String colName, CustomColumn<?, ?>... cols){
			setColName("work_place_id","`work_place_id`", colName, "setWorkPlaceId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asWorkPlace(String colName, CustomColumn<?, ?>... cols){
			setColName("work_place","`work_place`", colName, "setWorkPlace", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asRegularDate(String colName, CustomColumn<?, ?>... cols){
			setColName("regular_date","`regular_date`", colName, "setRegularDate", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asChannelId(String colName, CustomColumn<?, ?>... cols){
			setColName("channel_id","`channel_id`", colName, "setChannelId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asSpeciality(String colName, CustomColumn<?, ?>... cols){
			setColName("speciality","`speciality`", colName, "setSpeciality", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asIsRegular(String colName, CustomColumn<?, ?>... cols){
			setColName("is_regular","`is_regular`", colName, "setIsRegular", "getInt", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asAreaId(String colName, CustomColumn<?, ?>... cols){
			setColName("area_id","`area_id`", colName, "setAreaId", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asEntryDate(String colName, CustomColumn<?, ?>... cols){
			setColName("entry_date","`entry_date`", colName, "setEntryDate", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asRunOffDate(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_date","`run_off_date`", colName, "setRunOffDate", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asRefreshDate(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh_date","`refresh_date`", colName, "setRefreshDate", "getString", cols);
			return this;
		}
		public SourceVDimEmpEntityAuxiliary asMark(String colName, CustomColumn<?, ?>... cols){
			setColName("mark","`mark`", colName, "setMark", "getInt", cols);
			return this;
		}
	}
}
