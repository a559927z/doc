package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_map_public")
public class SourceMapPublicEntity implements Entity{

	private String _organization_id;
	private String _customer_id;
	private String _emp_id;
	private String _full_path;
	private String _update_time;
	private int _year_month;

	public SourceMapPublicEntity(){
		super();
	}

	public SourceMapPublicEntity(String _organization_id,String _customer_id,String _emp_id,String _full_path,String _update_time,int _year_month){
		this._organization_id = _organization_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._full_path = _full_path;
		this._update_time = _update_time;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceMapPublicEntity ["+
			"	organization_id="+_organization_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	full_path="+_full_path+
			"	update_time="+_update_time+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
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

	@Column(name = "full_path",type=ColumnType.VARCHAR)
	public String getFullPath(){
		return this._full_path; 
	}

	public void setFullPath(String _full_path){
		this._full_path = _full_path;
	}

	@Column(name = "update_time",type=ColumnType.DATETIME)
	public String getUpdateTime(){
		return this._update_time; 
	}

	public void setUpdateTime(String _update_time){
		this._update_time = _update_time;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceMapPublicEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMapPublicEntityAuxiliary();
	}

	public static class SourceMapPublicEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMapPublicEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceMapPublicEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMapPublicEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceMapPublicEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourceMapPublicEntityAuxiliary asUpdateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("update_time","`update_time`", colName, "setUpdateTime", "getString", cols);
			return this;
		}
		public SourceMapPublicEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
