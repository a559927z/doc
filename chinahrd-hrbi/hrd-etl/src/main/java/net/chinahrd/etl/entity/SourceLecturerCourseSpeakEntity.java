package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_lecturer_course_speak")
public class SourceLecturerCourseSpeakEntity implements Entity{

	private String _lecturer_course_speak_id;
	private String _customer_id;
	private String _lecturer_id;
	private String _course_id;
	private String _start_date;
	private String _end_date;
	private int _year;

	public SourceLecturerCourseSpeakEntity(){
		super();
	}

	public SourceLecturerCourseSpeakEntity(String _lecturer_course_speak_id,String _customer_id,String _lecturer_id,String _course_id,String _start_date,String _end_date,int _year){
		this._lecturer_course_speak_id = _lecturer_course_speak_id;
		this._customer_id = _customer_id;
		this._lecturer_id = _lecturer_id;
		this._course_id = _course_id;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceLecturerCourseSpeakEntity ["+
			"	lecturer_course_speak_id="+_lecturer_course_speak_id+
			"	customer_id="+_customer_id+
			"	lecturer_id="+_lecturer_id+
			"	course_id="+_course_id+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	year="+_year+
			"]";
	}

	@Column(name = "lecturer_course_speak_id",type=ColumnType.VARCHAR)
	public String getLecturerCourseSpeakId(){
		return this._lecturer_course_speak_id; 
	}

	public void setLecturerCourseSpeakId(String _lecturer_course_speak_id){
		this._lecturer_course_speak_id = _lecturer_course_speak_id;
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

	@Column(name = "start_date",type=ColumnType.DATETIME)
	public String getStartDate(){
		return this._start_date; 
	}

	public void setStartDate(String _start_date){
		this._start_date = _start_date;
	}

	@Column(name = "end_date",type=ColumnType.DATETIME)
	public String getEndDate(){
		return this._end_date; 
	}

	public void setEndDate(String _end_date){
		this._end_date = _end_date;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceLecturerCourseSpeakEntityAuxiliary  getEntityAuxiliary(){
		return new SourceLecturerCourseSpeakEntityAuxiliary();
	}

	public static class SourceLecturerCourseSpeakEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceLecturerCourseSpeakEntityAuxiliary asLecturerCourseSpeakId(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_course_speak_id","`lecturer_course_speak_id`", colName, "setLecturerCourseSpeakId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseSpeakEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseSpeakEntityAuxiliary asLecturerId(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_id","`lecturer_id`", colName, "setLecturerId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseSpeakEntityAuxiliary asCourseId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_id","`course_id`", colName, "setCourseId", "getString", cols);
			return this;
		}
		public SourceLecturerCourseSpeakEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceLecturerCourseSpeakEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceLecturerCourseSpeakEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
