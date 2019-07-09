package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_course")
public class SourceDimCourseEntity implements Entity{

	private String _course_id;
	private String _customer_id;
	private String _course_key;
	private String _course_name;
	private String _course_type_id;
	private int _show_index;

	public SourceDimCourseEntity(){
		super();
	}

	public SourceDimCourseEntity(String _course_id,String _customer_id,String _course_key,String _course_name,String _course_type_id,int _show_index){
		this._course_id = _course_id;
		this._customer_id = _customer_id;
		this._course_key = _course_key;
		this._course_name = _course_name;
		this._course_type_id = _course_type_id;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimCourseEntity ["+
			"	course_id="+_course_id+
			"	customer_id="+_customer_id+
			"	course_key="+_course_key+
			"	course_name="+_course_name+
			"	course_type_id="+_course_type_id+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "course_id",type=ColumnType.VARCHAR)
	public String getCourseId(){
		return this._course_id; 
	}

	public void setCourseId(String _course_id){
		this._course_id = _course_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "course_key",type=ColumnType.VARCHAR)
	public String getCourseKey(){
		return this._course_key; 
	}

	public void setCourseKey(String _course_key){
		this._course_key = _course_key;
	}

	@Column(name = "course_name",type=ColumnType.VARCHAR)
	public String getCourseName(){
		return this._course_name; 
	}

	public void setCourseName(String _course_name){
		this._course_name = _course_name;
	}

	@Column(name = "course_type_id",type=ColumnType.VARCHAR)
	public String getCourseTypeId(){
		return this._course_type_id; 
	}

	public void setCourseTypeId(String _course_type_id){
		this._course_type_id = _course_type_id;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimCourseEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimCourseEntityAuxiliary();
	}

	public static class SourceDimCourseEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimCourseEntityAuxiliary asCourseId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_id","`course_id`", colName, "setCourseId", "getString", cols);
			return this;
		}
		public SourceDimCourseEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimCourseEntityAuxiliary asCourseKey(String colName, CustomColumn<?, ?>... cols){
			setColName("course_key","`course_key`", colName, "setCourseKey", "getString", cols);
			return this;
		}
		public SourceDimCourseEntityAuxiliary asCourseName(String colName, CustomColumn<?, ?>... cols){
			setColName("course_name","`course_name`", colName, "setCourseName", "getString", cols);
			return this;
		}
		public SourceDimCourseEntityAuxiliary asCourseTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_type_id","`course_type_id`", colName, "setCourseTypeId", "getString", cols);
			return this;
		}
		public SourceDimCourseEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
