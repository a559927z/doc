package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_course_type")
public class SourceDimCourseTypeEntity implements Entity{

	private String _course_type_id;
	private String _customer_id;
	private String _course_type_key;
	private String _course_type_name;
	private int _show_index;

	public SourceDimCourseTypeEntity(){
		super();
	}

	public SourceDimCourseTypeEntity(String _course_type_id,String _customer_id,String _course_type_key,String _course_type_name,int _show_index){
		this._course_type_id = _course_type_id;
		this._customer_id = _customer_id;
		this._course_type_key = _course_type_key;
		this._course_type_name = _course_type_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimCourseTypeEntity ["+
			"	course_type_id="+_course_type_id+
			"	customer_id="+_customer_id+
			"	course_type_key="+_course_type_key+
			"	course_type_name="+_course_type_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "course_type_id",type=ColumnType.CHAR)
	public String getCourseTypeId(){
		return this._course_type_id; 
	}

	public void setCourseTypeId(String _course_type_id){
		this._course_type_id = _course_type_id;
	}

	@Column(name = "customer_id",type=ColumnType.CHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "course_type_key",type=ColumnType.VARCHAR)
	public String getCourseTypeKey(){
		return this._course_type_key; 
	}

	public void setCourseTypeKey(String _course_type_key){
		this._course_type_key = _course_type_key;
	}

	@Column(name = "course_type_name",type=ColumnType.VARCHAR)
	public String getCourseTypeName(){
		return this._course_type_name; 
	}

	public void setCourseTypeName(String _course_type_name){
		this._course_type_name = _course_type_name;
	}

	@Column(name = "show_index",type=ColumnType.TINYINT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimCourseTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimCourseTypeEntityAuxiliary();
	}

	public static class SourceDimCourseTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimCourseTypeEntityAuxiliary asCourseTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_type_id","`course_type_id`", colName, "setCourseTypeId", "getString", cols);
			return this;
		}
		public SourceDimCourseTypeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimCourseTypeEntityAuxiliary asCourseTypeKey(String colName, CustomColumn<?, ?>... cols){
			setColName("course_type_key","`course_type_key`", colName, "setCourseTypeKey", "getString", cols);
			return this;
		}
		public SourceDimCourseTypeEntityAuxiliary asCourseTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("course_type_name","`course_type_name`", colName, "setCourseTypeName", "getString", cols);
			return this;
		}
		public SourceDimCourseTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
