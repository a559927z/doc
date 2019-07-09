package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_map_talent_info")
public class SourceMapTalentInfoEntity implements Entity{

	private String _emp_id;
	private String _user_name_ch;
	private String _customer_id;
	private String _full_path;
	private String _organization_parent_id;
	private String _organization_id;
	private String _sequence_id;
	private String _sequence_sub_id;
	private String _ability_id;
	private String _degree_id;
	private String _performance_id;
	private String _position_id;
	private int _age;
	private String _sex;
	private String _refresh;

	public SourceMapTalentInfoEntity(){
		super();
	}

	public SourceMapTalentInfoEntity(String _emp_id,String _user_name_ch,String _customer_id,String _full_path,String _organization_parent_id,String _organization_id,String _sequence_id,String _sequence_sub_id,String _ability_id,String _degree_id,String _performance_id,String _position_id,int _age,String _sex,String _refresh){
		this._emp_id = _emp_id;
		this._user_name_ch = _user_name_ch;
		this._customer_id = _customer_id;
		this._full_path = _full_path;
		this._organization_parent_id = _organization_parent_id;
		this._organization_id = _organization_id;
		this._sequence_id = _sequence_id;
		this._sequence_sub_id = _sequence_sub_id;
		this._ability_id = _ability_id;
		this._degree_id = _degree_id;
		this._performance_id = _performance_id;
		this._position_id = _position_id;
		this._age = _age;
		this._sex = _sex;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceMapTalentInfoEntity ["+
			"	emp_id="+_emp_id+
			"	user_name_ch="+_user_name_ch+
			"	customer_id="+_customer_id+
			"	full_path="+_full_path+
			"	organization_parent_id="+_organization_parent_id+
			"	organization_id="+_organization_id+
			"	sequence_id="+_sequence_id+
			"	sequence_sub_id="+_sequence_sub_id+
			"	ability_id="+_ability_id+
			"	degree_id="+_degree_id+
			"	performance_id="+_performance_id+
			"	position_id="+_position_id+
			"	age="+_age+
			"	sex="+_sex+
			"	refresh="+_refresh+
			"]";
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

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
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

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "performance_id",type=ColumnType.VARCHAR)
	public String getPerformanceId(){
		return this._performance_id; 
	}

	public void setPerformanceId(String _performance_id){
		this._performance_id = _performance_id;
	}

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
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

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceMapTalentInfoEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMapTalentInfoEntityAuxiliary();
	}

	public static class SourceMapTalentInfoEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMapTalentInfoEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asOrganizationParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_id","`organization_parent_id`", colName, "setOrganizationParentId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asSequenceSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_id","`sequence_sub_id`", colName, "setSequenceSubId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asPerformanceId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_id","`performance_id`", colName, "setPerformanceId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asAge(String colName, CustomColumn<?, ?>... cols){
			setColName("age","`age`", colName, "setAge", "getInt", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asSex(String colName, CustomColumn<?, ?>... cols){
			setColName("sex","`sex`", colName, "setSex", "getString", cols);
			return this;
		}
		public SourceMapTalentInfoEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
