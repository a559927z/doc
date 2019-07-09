package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_recruit_result")
public class SourceRecruitResultEntity implements Entity{

	private String _recruit_result_id;
	private String _customer_id;
	private String _recruit_result_key;
	private String _username;
	private String _sex;
	private Double _age;
	private String _degree_id;
	private String _major;
	private String _school;
	private int _is_resume;
	private int _is_interview;
	private int _is_offer;
	private int _is_entry;
	private String _url;
	private String _recruit_public_id;
	private int _year;
	private String _refresh;
	private String _c_id;

	public SourceRecruitResultEntity(){
		super();
	}

	public SourceRecruitResultEntity(String _recruit_result_id,String _customer_id,String _recruit_result_key,String _username,String _sex,Double _age,String _degree_id,String _major,String _school,int _is_resume,int _is_interview,int _is_offer,int _is_entry,String _url,String _recruit_public_id,int _year,String _refresh,String _c_id){
		this._recruit_result_id = _recruit_result_id;
		this._customer_id = _customer_id;
		this._recruit_result_key = _recruit_result_key;
		this._username = _username;
		this._sex = _sex;
		this._age = _age;
		this._degree_id = _degree_id;
		this._major = _major;
		this._school = _school;
		this._is_resume = _is_resume;
		this._is_interview = _is_interview;
		this._is_offer = _is_offer;
		this._is_entry = _is_entry;
		this._url = _url;
		this._recruit_public_id = _recruit_public_id;
		this._year = _year;
		this._refresh = _refresh;
		this._c_id = _c_id;
	}

	@Override
	public String toString() {
		return "SourceRecruitResultEntity ["+
			"	recruit_result_id="+_recruit_result_id+
			"	customer_id="+_customer_id+
			"	recruit_result_key="+_recruit_result_key+
			"	username="+_username+
			"	sex="+_sex+
			"	age="+_age+
			"	degree_id="+_degree_id+
			"	major="+_major+
			"	school="+_school+
			"	is_resume="+_is_resume+
			"	is_interview="+_is_interview+
			"	is_offer="+_is_offer+
			"	is_entry="+_is_entry+
			"	url="+_url+
			"	recruit_public_id="+_recruit_public_id+
			"	year="+_year+
			"	refresh="+_refresh+
			"	c_id="+_c_id+
			"]";
	}

	@Column(name = "recruit_result_id",type=ColumnType.VARCHAR)
	public String getRecruitResultId(){
		return this._recruit_result_id; 
	}

	public void setRecruitResultId(String _recruit_result_id){
		this._recruit_result_id = _recruit_result_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "recruit_result_key",type=ColumnType.VARCHAR)
	public String getRecruitResultKey(){
		return this._recruit_result_key; 
	}

	public void setRecruitResultKey(String _recruit_result_key){
		this._recruit_result_key = _recruit_result_key;
	}

	@Column(name = "username",type=ColumnType.VARCHAR)
	public String getUsername(){
		return this._username; 
	}

	public void setUsername(String _username){
		this._username = _username;
	}

	@Column(name = "sex",type=ColumnType.CHAR)
	public String getSex(){
		return this._sex; 
	}

	public void setSex(String _sex){
		this._sex = _sex;
	}

	@Column(name = "age",type=ColumnType.DOUBLE)
	public Double getAge(){
		return this._age; 
	}

	public void setAge(Double _age){
		this._age = _age;
	}

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "major",type=ColumnType.VARCHAR)
	public String getMajor(){
		return this._major; 
	}

	public void setMajor(String _major){
		this._major = _major;
	}

	@Column(name = "school",type=ColumnType.VARCHAR)
	public String getSchool(){
		return this._school; 
	}

	public void setSchool(String _school){
		this._school = _school;
	}

	@Column(name = "is_resume",type=ColumnType.TINYINT)
	public int getIsResume(){
		return this._is_resume; 
	}

	public void setIsResume(int _is_resume){
		this._is_resume = _is_resume;
	}

	@Column(name = "is_interview",type=ColumnType.TINYINT)
	public int getIsInterview(){
		return this._is_interview; 
	}

	public void setIsInterview(int _is_interview){
		this._is_interview = _is_interview;
	}

	@Column(name = "is_offer",type=ColumnType.TINYINT)
	public int getIsOffer(){
		return this._is_offer; 
	}

	public void setIsOffer(int _is_offer){
		this._is_offer = _is_offer;
	}

	@Column(name = "is_entry",type=ColumnType.TINYINT)
	public int getIsEntry(){
		return this._is_entry; 
	}

	public void setIsEntry(int _is_entry){
		this._is_entry = _is_entry;
	}

	@Column(name = "url",type=ColumnType.TEXT)
	public String getUrl(){
		return this._url; 
	}

	public void setUrl(String _url){
		this._url = _url;
	}

	@Column(name = "recruit_public_id",type=ColumnType.VARCHAR)
	public String getRecruitPublicId(){
		return this._recruit_public_id; 
	}

	public void setRecruitPublicId(String _recruit_public_id){
		this._recruit_public_id = _recruit_public_id;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	@Column(name = "c_id",type=ColumnType.VARCHAR)
	public String getCId(){
		return this._c_id; 
	}

	public void setCId(String _c_id){
		this._c_id = _c_id;
	}

	// 实例化内部类
	public static SourceRecruitResultEntityAuxiliary  getEntityAuxiliary(){
		return new SourceRecruitResultEntityAuxiliary();
	}

	public static class SourceRecruitResultEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceRecruitResultEntityAuxiliary asRecruitResultId(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_result_id","`recruit_result_id`", colName, "setRecruitResultId", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asRecruitResultKey(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_result_key","`recruit_result_key`", colName, "setRecruitResultKey", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asUsername(String colName, CustomColumn<?, ?>... cols){
			setColName("username","`username`", colName, "setUsername", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asSex(String colName, CustomColumn<?, ?>... cols){
			setColName("sex","`sex`", colName, "setSex", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asAge(String colName, CustomColumn<?, ?>... cols){
			setColName("age","`age`", colName, "setAge", "getDouble", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asMajor(String colName, CustomColumn<?, ?>... cols){
			setColName("major","`major`", colName, "setMajor", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asSchool(String colName, CustomColumn<?, ?>... cols){
			setColName("school","`school`", colName, "setSchool", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asIsResume(String colName, CustomColumn<?, ?>... cols){
			setColName("is_resume","`is_resume`", colName, "setIsResume", "getInt", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asIsInterview(String colName, CustomColumn<?, ?>... cols){
			setColName("is_interview","`is_interview`", colName, "setIsInterview", "getInt", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asIsOffer(String colName, CustomColumn<?, ?>... cols){
			setColName("is_offer","`is_offer`", colName, "setIsOffer", "getInt", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asIsEntry(String colName, CustomColumn<?, ?>... cols){
			setColName("is_entry","`is_entry`", colName, "setIsEntry", "getInt", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asUrl(String colName, CustomColumn<?, ?>... cols){
			setColName("url","`url`", colName, "setUrl", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asRecruitPublicId(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_public_id","`recruit_public_id`", colName, "setRecruitPublicId", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceRecruitResultEntityAuxiliary asCId(String colName, CustomColumn<?, ?>... cols){
			setColName("c_id","`c_id`", colName, "setCId", "getString", cols);
			return this;
		}
	}
}
