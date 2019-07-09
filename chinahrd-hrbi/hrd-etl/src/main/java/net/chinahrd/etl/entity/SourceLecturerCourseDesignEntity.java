package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_lecturer_course_design")
public class SourceLecturerCourseDesignEntity implements Entity{

	private String _lecturer_course_design_id;
	private String _customer_id;
	private String _lecturer_id;
	private String _course_id;

	public SourceLecturerCourseDesignEntity(){
		super();
	}

	public SourceLecturerCourseDesignEntity(String _lecturer_course_design_id,String _customer_id,String _lecturer_id,String _course_id){
		this._lecturer_course_design_id = _lecturer_course_design_id;
		this._customer_id = _customer_id;
		this._lecturer_id = _lecturer_id;
		this._course_id = _course_id;
	}

	@Override
	public String toString() {
		return "SourceLecturerCourseDesignEntity ["+
			"	lecturer_course_design_id="+_lecturer_course_design_id+
			"	customer_id="+_customer_id+
			"	lecturer_id="+_lecturer_id+
			"	course_id="+_course_id+
			"]";
	}

	@Column(name = "lecturer_course_design_id",type=ColumnType.VARCHAR)
	public String getLecturerCourseDesignId(){
		return this._lecturer_course_design_id; 
	}

	public void setLecturerCourseDesignId(String _lecturer_course_design_id){
		this._lecturer_course_design_id = _lecturer_course_design_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "lecturer_id",type=ColumnType.VARCHAR)
	public String getLecturerId(){
		return this._lecturer_id; 
	}

	public void setLecturerId(String _lecturer_id){
		this._lecturer_id = _lecturer_id;
	}

	@Column(name = "course_id",type=ColumnType.VARCHAR)
	public String getCourseId(){
		return this._course_id; 
	}

	public void setCourseId(String _course_id){
		this._course_id = _course_id;
	}

	// 实例化内部类
	public static SourceLecturerCourseDesignEntityAuxiliary  getEntityAuxiliary(){
		return new SourceLecturerCourseDesignEntityAuxiliary();
	}

	public static class SourceLecturerCourseDesignEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceLecturerCourseDesignEntityAuxiliary asLecturerCourseDesignId(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_course_design_id","`lecturer_course_design_id`", colName, "setLecturerCourseDesignId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseDesignEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseDesignEntityAuxiliary asLecturerId(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_id","`lecturer_id`", colName, "setLecturerId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseDesignEntityAuxiliary asCourseId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_id","`course_id`", colName, "setCourseId", "getString", cols);
			return this;
		}
	}
}
