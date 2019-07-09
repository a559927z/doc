package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_project")
public class SourceProjectEntity implements Entity{

	private String _project_id;
	private String _customer_id;
	private String _project_key;
	private String _project_name;
	private String _emp_id;
	private String _organization_id;
	private String _project_type_id;
	private String _project_progress_id;
	private String _project_parent_id;
	private String _full_path;
	private int _has_chilren;
	private String _start_date;
	private String _end_date;
	private String _refresh;

	public SourceProjectEntity(){
		super();
	}

	public SourceProjectEntity(String _project_id,String _customer_id,String _project_key,String _project_name,String _emp_id,String _organization_id,String _project_type_id,String _project_progress_id,String _project_parent_id,String _full_path,int _has_chilren,String _start_date,String _end_date,String _refresh){
		this._project_id = _project_id;
		this._customer_id = _customer_id;
		this._project_key = _project_key;
		this._project_name = _project_name;
		this._emp_id = _emp_id;
		this._organization_id = _organization_id;
		this._project_type_id = _project_type_id;
		this._project_progress_id = _project_progress_id;
		this._project_parent_id = _project_parent_id;
		this._full_path = _full_path;
		this._has_chilren = _has_chilren;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceProjectEntity ["+
			"	project_id="+_project_id+
			"	customer_id="+_customer_id+
			"	project_key="+_project_key+
			"	project_name="+_project_name+
			"	emp_id="+_emp_id+
			"	organization_id="+_organization_id+
			"	project_type_id="+_project_type_id+
			"	project_progress_id="+_project_progress_id+
			"	project_parent_id="+_project_parent_id+
			"	full_path="+_full_path+
			"	has_chilren="+_has_chilren+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "project_id",type=ColumnType.VARCHAR)
	public String getProjectId(){
		return this._project_id; 
	}

	public void setProjectId(String _project_id){
		this._project_id = _project_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "project_key",type=ColumnType.VARCHAR)
	public String getProjectKey(){
		return this._project_key; 
	}

	public void setProjectKey(String _project_key){
		this._project_key = _project_key;
	}

	@Column(name = "project_name",type=ColumnType.VARCHAR)
	public String getProjectName(){
		return this._project_name; 
	}

	public void setProjectName(String _project_name){
		this._project_name = _project_name;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "project_type_id",type=ColumnType.VARCHAR)
	public String getProjectTypeId(){
		return this._project_type_id; 
	}

	public void setProjectTypeId(String _project_type_id){
		this._project_type_id = _project_type_id;
	}

	@Column(name = "project_progress_id",type=ColumnType.VARCHAR)
	public String getProjectProgressId(){
		return this._project_progress_id; 
	}

	public void setProjectProgressId(String _project_progress_id){
		this._project_progress_id = _project_progress_id;
	}

	@Column(name = "project_parent_id",type=ColumnType.VARCHAR)
	public String getProjectParentId(){
		return this._project_parent_id; 
	}

	public void setProjectParentId(String _project_parent_id){
		this._project_parent_id = _project_parent_id;
	}

	@Column(name = "full_path",type=ColumnType.TEXT)
	public String getFullPath(){
		return this._full_path; 
	}

	public void setFullPath(String _full_path){
		this._full_path = _full_path;
	}

	@Column(name = "has_chilren",type=ColumnType.TINYINT)
	public int getHasChilren(){
		return this._has_chilren; 
	}

	public void setHasChilren(int _has_chilren){
		this._has_chilren = _has_chilren;
	}

	@Column(name = "start_date",type=ColumnType.DATE)
	public String getStartDate(){
		return this._start_date; 
	}

	public void setStartDate(String _start_date){
		this._start_date = _start_date;
	}

	@Column(name = "end_date",type=ColumnType.DATE)
	public String getEndDate(){
		return this._end_date; 
	}

	public void setEndDate(String _end_date){
		this._end_date = _end_date;
	}

	@Column(name = "refresh",type=ColumnType.DATE)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceProjectEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProjectEntityAuxiliary();
	}

	public static class SourceProjectEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProjectEntityAuxiliary asProjectId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_id","`project_id`", colName, "setProjectId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asProjectKey(String colName, CustomColumn<?, ?>... cols){
			setColName("project_key","`project_key`", colName, "setProjectKey", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asProjectName(String colName, CustomColumn<?, ?>... cols){
			setColName("project_name","`project_name`", colName, "setProjectName", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asProjectTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_type_id","`project_type_id`", colName, "setProjectTypeId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asProjectProgressId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_progress_id","`project_progress_id`", colName, "setProjectProgressId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asProjectParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_parent_id","`project_parent_id`", colName, "setProjectParentId", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asHasChilren(String colName, CustomColumn<?, ?>... cols){
			setColName("has_chilren","`has_chilren`", colName, "setHasChilren", "getInt", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceProjectEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
