package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_ability_change")
public class SourceAbilityChangeEntity implements Entity{

	private String _ability_change_id;
	private String _customer_id;
	private String _emp_id;
	private String _user_name_ch;
	private String _full_path;
	private String _organization_parent_id;
	private String _organization_id;
	private String _sequence_id;
	private String _sequence_sub_id;
	private String _ability_id;
	private int _age;
	private String _sex;
	private String _degree_id;
	private String _ability_number_id;
	private String _update_time;
	private int _year_months;

	public SourceAbilityChangeEntity(){
		super();
	}

	public SourceAbilityChangeEntity(String _ability_change_id,String _customer_id,String _emp_id,String _user_name_ch,String _full_path,String _organization_parent_id,String _organization_id,String _sequence_id,String _sequence_sub_id,String _ability_id,int _age,String _sex,String _degree_id,String _ability_number_id,String _update_time,int _year_months){
		this._ability_change_id = _ability_change_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._user_name_ch = _user_name_ch;
		this._full_path = _full_path;
		this._organization_parent_id = _organization_parent_id;
		this._organization_id = _organization_id;
		this._sequence_id = _sequence_id;
		this._sequence_sub_id = _sequence_sub_id;
		this._ability_id = _ability_id;
		this._age = _age;
		this._sex = _sex;
		this._degree_id = _degree_id;
		this._ability_number_id = _ability_number_id;
		this._update_time = _update_time;
		this._year_months = _year_months;
	}

	@Override
	public String toString() {
		return "SourceAbilityChangeEntity ["+
			"	ability_change_id="+_ability_change_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	user_name_ch="+_user_name_ch+
			"	full_path="+_full_path+
			"	organization_parent_id="+_organization_parent_id+
			"	organization_id="+_organization_id+
			"	sequence_id="+_sequence_id+
			"	sequence_sub_id="+_sequence_sub_id+
			"	ability_id="+_ability_id+
			"	age="+_age+
			"	sex="+_sex+
			"	degree_id="+_degree_id+
			"	ability_number_id="+_ability_number_id+
			"	update_time="+_update_time+
			"	year_months="+_year_months+
			"]";
	}

	@Column(name = "ability_change_id",type=ColumnType.VARCHAR)
	public String getAbilityChangeId(){
		return this._ability_change_id; 
	}

	public void setAbilityChangeId(String _ability_change_id){
		this._ability_change_id = _ability_change_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "full_path",type=ColumnType.VARCHAR)
	public String getFullPath(){
		return this._full_path; 
	}

	public void setFullPath(String _full_path){
		this._full_path = _full_path;
	}

	@Column(name = "organization_parent_id",type=ColumnType.VARCHAR)
	public String getOrganizationParentId(){
		return this._organization_parent_id; 
	}

	public void setOrganizationParentId(String _organization_parent_id){
		this._organization_parent_id = _organization_parent_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "sequence_sub_id",type=ColumnType.VARCHAR)
	public String getSequenceSubId(){
		return this._sequence_sub_id; 
	}

	public void setSequenceSubId(String _sequence_sub_id){
		this._sequence_sub_id = _sequence_sub_id;
	}

	@Column(name = "ability_id",type=ColumnType.VARCHAR)
	public String getAbilityId(){
		return this._ability_id; 
	}

	public void setAbilityId(String _ability_id){
		this._ability_id = _ability_id;
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

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "ability_number_id",type=ColumnType.VARCHAR)
	public String getAbilityNumberId(){
		return this._ability_number_id; 
	}

	public void setAbilityNumberId(String _ability_number_id){
		this._ability_number_id = _ability_number_id;
	}

	@Column(name = "update_time",type=ColumnType.DATETIME)
	public String getUpdateTime(){
		return this._update_time; 
	}

	public void setUpdateTime(String _update_time){
		this._update_time = _update_time;
	}

	@Column(name = "year_months",type=ColumnType.INT)
	public int getYearMonths(){
		return this._year_months; 
	}

	public void setYearMonths(int _year_months){
		this._year_months = _year_months;
	}

	// 实例化内部类
	public static SourceAbilityChangeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceAbilityChangeEntityAuxiliary();
	}

	public static class SourceAbilityChangeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceAbilityChangeEntityAuxiliary asAbilityChangeId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_change_id","`ability_change_id`", colName, "setAbilityChangeId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asOrganizationParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_id","`organization_parent_id`", colName, "setOrganizationParentId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asSequenceSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_id","`sequence_sub_id`", colName, "setSequenceSubId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asAge(String colName, CustomColumn<?, ?>... cols){
			setColName("age","`age`", colName, "setAge", "getInt", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asSex(String colName, CustomColumn<?, ?>... cols){
			setColName("sex","`sex`", colName, "setSex", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asAbilityNumberId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_number_id","`ability_number_id`", colName, "setAbilityNumberId", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asUpdateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("update_time","`update_time`", colName, "setUpdateTime", "getString", cols);
			return this;
		}
		public SourceAbilityChangeEntityAuxiliary asYearMonths(String colName, CustomColumn<?, ?>... cols){
			setColName("year_months","`year_months`", colName, "setYearMonths", "getInt", cols);
			return this;
		}
	}
}
