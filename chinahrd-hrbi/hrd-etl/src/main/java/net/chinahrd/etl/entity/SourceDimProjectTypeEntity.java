package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_project_type")
public class SourceDimProjectTypeEntity implements Entity{

	private String _project_type_id;
	private String _customer_id;
	private String _project_type_name;
	private int _show_index;
	private String _refresh;

	public SourceDimProjectTypeEntity(){
		super();
	}

	public SourceDimProjectTypeEntity(String _project_type_id,String _customer_id,String _project_type_name,int _show_index,String _refresh){
		this._project_type_id = _project_type_id;
		this._customer_id = _customer_id;
		this._project_type_name = _project_type_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimProjectTypeEntity ["+
			"	project_type_id="+_project_type_id+
			"	customer_id="+_customer_id+
			"	project_type_name="+_project_type_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "project_type_id",type=ColumnType.VARCHAR)
	public String getProjectTypeId(){
		return this._project_type_id; 
	}

	public void setProjectTypeId(String _project_type_id){
		this._project_type_id = _project_type_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "project_type_name",type=ColumnType.VARCHAR)
	public String getProjectTypeName(){
		return this._project_type_name; 
	}

	public void setProjectTypeName(String _project_type_name){
		this._project_type_name = _project_type_name;
	}

	@Column(name = "show_index",type=ColumnType.TINYINT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceDimProjectTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimProjectTypeEntityAuxiliary();
	}

	public static class SourceDimProjectTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimProjectTypeEntityAuxiliary asProjectTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_type_id","`project_type_id`", colName, "setProjectTypeId", "getString", cols);
			return this;
		}
		public SourceDimProjectTypeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimProjectTypeEntityAuxiliary asProjectTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("project_type_name","`project_type_name`", colName, "setProjectTypeName", "getString", cols);
			return this;
		}
		public SourceDimProjectTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimProjectTypeEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
