package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_out_talent")
public class SourceOutTalentEntity implements Entity{

	private String _out_talent_id;
	private String _customer_id;
	private String _user_name_ch;
	private String _user_name;
	private String _email;
	private int _age;
	private String _sex;
	private String _degree_id;
	private String _url;
	private String _school;
	private String _refresh;
	private String _img_path;
	private String _tag;
	private String _c_id;

	public SourceOutTalentEntity(){
		super();
	}

	public SourceOutTalentEntity(String _out_talent_id,String _customer_id,String _user_name_ch,String _user_name,String _email,int _age,String _sex,String _degree_id,String _url,String _school,String _refresh,String _img_path,String _tag,String _c_id){
		this._out_talent_id = _out_talent_id;
		this._customer_id = _customer_id;
		this._user_name_ch = _user_name_ch;
		this._user_name = _user_name;
		this._email = _email;
		this._age = _age;
		this._sex = _sex;
		this._degree_id = _degree_id;
		this._url = _url;
		this._school = _school;
		this._refresh = _refresh;
		this._img_path = _img_path;
		this._tag = _tag;
		this._c_id = _c_id;
	}

	@Override
	public String toString() {
		return "SourceOutTalentEntity ["+
			"	out_talent_id="+_out_talent_id+
			"	customer_id="+_customer_id+
			"	user_name_ch="+_user_name_ch+
			"	user_name="+_user_name+
			"	email="+_email+
			"	age="+_age+
			"	sex="+_sex+
			"	degree_id="+_degree_id+
			"	url="+_url+
			"	school="+_school+
			"	refresh="+_refresh+
			"	img_path="+_img_path+
			"	tag="+_tag+
			"	c_id="+_c_id+
			"]";
	}

	@Column(name = "out_talent_id",type=ColumnType.VARCHAR)
	public String getOutTalentId(){
		return this._out_talent_id; 
	}

	public void setOutTalentId(String _out_talent_id){
		this._out_talent_id = _out_talent_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "user_name",type=ColumnType.VARCHAR)
	public String getUserName(){
		return this._user_name; 
	}

	public void setUserName(String _user_name){
		this._user_name = _user_name;
	}

	@Column(name = "email",type=ColumnType.TEXT)
	public String getEmail(){
		return this._email; 
	}

	public void setEmail(String _email){
		this._email = _email;
	}

	@Column(name = "age",type=ColumnType.INT)
	public int getAge(){
		return this._age; 
	}

	public void setAge(int _age){
		this._age = _age;
	}

	@Column(name = "sex",type=ColumnType.CHAR)
	public String getSex(){
		return this._sex; 
	}

	public void setSex(String _sex){
		this._sex = _sex;
	}

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "url",type=ColumnType.TEXT)
	public String getUrl(){
		return this._url; 
	}

	public void setUrl(String _url){
		this._url = _url;
	}

	@Column(name = "school",type=ColumnType.VARCHAR)
	public String getSchool(){
		return this._school; 
	}

	public void setSchool(String _school){
		this._school = _school;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	@Column(name = "img_path",type=ColumnType.VARCHAR)
	public String getImgPath(){
		return this._img_path; 
	}

	public void setImgPath(String _img_path){
		this._img_path = _img_path;
	}

	@Column(name = "tag",type=ColumnType.VARCHAR)
	public String getTag(){
		return this._tag; 
	}

	public void setTag(String _tag){
		this._tag = _tag;
	}

	@Column(name = "c_id",type=ColumnType.VARCHAR)
	public String getCId(){
		return this._c_id; 
	}

	public void setCId(String _c_id){
		this._c_id = _c_id;
	}

	// 实例化内部类
	public static SourceOutTalentEntityAuxiliary  getEntityAuxiliary(){
		return new SourceOutTalentEntityAuxiliary();
	}

	public static class SourceOutTalentEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceOutTalentEntityAuxiliary asOutTalentId(String colName, CustomColumn<?, ?>... cols){
			setColName("out_talent_id","`out_talent_id`", colName, "setOutTalentId", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asUserName(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name","`user_name`", colName, "setUserName", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asEmail(String colName, CustomColumn<?, ?>... cols){
			setColName("email","`email`", colName, "setEmail", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asAge(String colName, CustomColumn<?, ?>... cols){
			setColName("age","`age`", colName, "setAge", "getInt", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asSex(String colName, CustomColumn<?, ?>... cols){
			setColName("sex","`sex`", colName, "setSex", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asUrl(String colName, CustomColumn<?, ?>... cols){
			setColName("url","`url`", colName, "setUrl", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asSchool(String colName, CustomColumn<?, ?>... cols){
			setColName("school","`school`", colName, "setSchool", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asImgPath(String colName, CustomColumn<?, ?>... cols){
			setColName("img_path","`img_path`", colName, "setImgPath", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asTag(String colName, CustomColumn<?, ?>... cols){
			setColName("tag","`tag`", colName, "setTag", "getString", cols);
			return this;
		}
		public SourceOutTalentEntityAuxiliary asCId(String colName, CustomColumn<?, ?>... cols){
			setColName("c_id","`c_id`", colName, "setCId", "getString", cols);
			return this;
		}
	}
}
