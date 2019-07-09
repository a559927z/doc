package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_emp")
public class SourceSalesEmpEntity implements Entity{

	private String _emp_id;
	private String _customer_id;
	private String _user_name_ch;
	private String _organization_id;
	private int _age;
	private String _sex;
	private String _performance_id;
	private String _degree_id;
	private String _team_id;

	public SourceSalesEmpEntity(){
		super();
	}

	public SourceSalesEmpEntity(String _emp_id,String _customer_id,String _user_name_ch,String _organization_id,int _age,String _sex,String _performance_id,String _degree_id,String _team_id){
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._user_name_ch = _user_name_ch;
		this._organization_id = _organization_id;
		this._age = _age;
		this._sex = _sex;
		this._performance_id = _performance_id;
		this._degree_id = _degree_id;
		this._team_id = _team_id;
	}

	@Override
	public String toString() {
		return "SourceSalesEmpEntity ["+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	user_name_ch="+_user_name_ch+
			"	organization_id="+_organization_id+
			"	age="+_age+
			"	sex="+_sex+
			"	performance_id="+_performance_id+
			"	degree_id="+_degree_id+
			"	team_id="+_team_id+
			"]";
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

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
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

	@Column(name = "performance_id",type=ColumnType.VARCHAR)
	public String getPerformanceId(){
		return this._performance_id; 
	}

	public void setPerformanceId(String _performance_id){
		this._performance_id = _performance_id;
	}

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "team_id",type=ColumnType.VARCHAR)
	public String getTeamId(){
		return this._team_id; 
	}

	public void setTeamId(String _team_id){
		this._team_id = _team_id;
	}

	// 实例化内部类
	public static SourceSalesEmpEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesEmpEntityAuxiliary();
	}

	public static class SourceSalesEmpEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesEmpEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asAge(String colName, CustomColumn<?, ?>... cols){
			setColName("age","`age`", colName, "setAge", "getInt", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asSex(String colName, CustomColumn<?, ?>... cols){
			setColName("sex","`sex`", colName, "setSex", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asPerformanceId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_id","`performance_id`", colName, "setPerformanceId", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceSalesEmpEntityAuxiliary asTeamId(String colName, CustomColumn<?, ?>... cols){
			setColName("team_id","`team_id`", colName, "setTeamId", "getString", cols);
			return this;
		}
	}
}
