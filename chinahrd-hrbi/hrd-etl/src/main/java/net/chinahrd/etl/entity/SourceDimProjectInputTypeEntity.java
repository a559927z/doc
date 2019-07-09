package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_project_input_type")
public class SourceDimProjectInputTypeEntity implements Entity{

	private String _project_input_type_id;
	private String _project_input_type_name;
	private int _show_index;

	public SourceDimProjectInputTypeEntity(){
		super();
	}

	public SourceDimProjectInputTypeEntity(String _project_input_type_id,String _project_input_type_name,int _show_index){
		this._project_input_type_id = _project_input_type_id;
		this._project_input_type_name = _project_input_type_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimProjectInputTypeEntity ["+
			"	project_input_type_id="+_project_input_type_id+
			"	project_input_type_name="+_project_input_type_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "project_input_type_id",type=ColumnType.VARCHAR)
	public String getProjectInputTypeId(){
		return this._project_input_type_id; 
	}

	public void setProjectInputTypeId(String _project_input_type_id){
		this._project_input_type_id = _project_input_type_id;
	}

	@Column(name = "project_input_type_name",type=ColumnType.VARCHAR)
	public String getProjectInputTypeName(){
		return this._project_input_type_name; 
	}

	public void setProjectInputTypeName(String _project_input_type_name){
		this._project_input_type_name = _project_input_type_name;
	}

	@Column(name = "show_index",type=ColumnType.TINYINT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimProjectInputTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimProjectInputTypeEntityAuxiliary();
	}

	public static class SourceDimProjectInputTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimProjectInputTypeEntityAuxiliary asProjectInputTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_input_type_id","`project_input_type_id`", colName, "setProjectInputTypeId", "getString", cols);
			return this;
		}
		public SourceDimProjectInputTypeEntityAuxiliary asProjectInputTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("project_input_type_name","`project_input_type_name`", colName, "setProjectInputTypeName", "getString", cols);
			return this;
		}
		public SourceDimProjectInputTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
