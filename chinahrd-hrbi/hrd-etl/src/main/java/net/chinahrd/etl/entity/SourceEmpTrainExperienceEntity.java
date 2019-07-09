package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_train_experience")
public class SourceEmpTrainExperienceEntity implements Entity{

	private String _train_experience_id;
	private String _course_record_id;
	private String _customer_id;
	private String _emp_id;
	private String _course_name;
	private String _start_date;
	private String _finish_date;
	private Double _train_time;
	private int _status;
	private String _result;
	private String _train_unit;
	private String _gain_certificate;
	private String _lecturer_name;
	private String _note;
	private int _year;
	private int _mark;

	public SourceEmpTrainExperienceEntity(){
		super();
	}

	public SourceEmpTrainExperienceEntity(String _train_experience_id,String _course_record_id,String _customer_id,String _emp_id,String _course_name,String _start_date,String _finish_date,Double _train_time,int _status,String _result,String _train_unit,String _gain_certificate,String _lecturer_name,String _note,int _year,int _mark){
		this._train_experience_id = _train_experience_id;
		this._course_record_id = _course_record_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._course_name = _course_name;
		this._start_date = _start_date;
		this._finish_date = _finish_date;
		this._train_time = _train_time;
		this._status = _status;
		this._result = _result;
		this._train_unit = _train_unit;
		this._gain_certificate = _gain_certificate;
		this._lecturer_name = _lecturer_name;
		this._note = _note;
		this._year = _year;
		this._mark = _mark;
	}

	@Override
	public String toString() {
		return "SourceEmpTrainExperienceEntity ["+
			"	train_experience_id="+_train_experience_id+
			"	course_record_id="+_course_record_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	course_name="+_course_name+
			"	start_date="+_start_date+
			"	finish_date="+_finish_date+
			"	train_time="+_train_time+
			"	status="+_status+
			"	result="+_result+
			"	train_unit="+_train_unit+
			"	gain_certificate="+_gain_certificate+
			"	lecturer_name="+_lecturer_name+
			"	note="+_note+
			"	year="+_year+
			"	mark="+_mark+
			"]";
	}

	@Column(name = "train_experience_id",type=ColumnType.VARCHAR)
	public String getTrainExperienceId(){
		return this._train_experience_id; 
	}

	public void setTrainExperienceId(String _train_experience_id){
		this._train_experience_id = _train_experience_id;
	}

	@Column(name = "course_record_id",type=ColumnType.VARCHAR)
	public String getCourseRecordId(){
		return this._course_record_id; 
	}

	public void setCourseRecordId(String _course_record_id){
		this._course_record_id = _course_record_id;
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

	@Column(name = "course_name",type=ColumnType.VARCHAR)
	public String getCourseName(){
		return this._course_name; 
	}

	public void setCourseName(String _course_name){
		this._course_name = _course_name;
	}

	@Column(name = "start_date",type=ColumnType.DATETIME)
	public String getStartDate(){
		return this._start_date; 
	}

	public void setStartDate(String _start_date){
		this._start_date = _start_date;
	}

	@Column(name = "finish_date",type=ColumnType.DATETIME)
	public String getFinishDate(){
		return this._finish_date; 
	}

	public void setFinishDate(String _finish_date){
		this._finish_date = _finish_date;
	}

	@Column(name = "train_time",type=ColumnType.DOUBLE)
	public Double getTrainTime(){
		return this._train_time; 
	}

	public void setTrainTime(Double _train_time){
		this._train_time = _train_time;
	}

	@Column(name = "status",type=ColumnType.TINYINT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	@Column(name = "result",type=ColumnType.VARCHAR)
	public String getResult(){
		return this._result; 
	}

	public void setResult(String _result){
		this._result = _result;
	}

	@Column(name = "train_unit",type=ColumnType.VARCHAR)
	public String getTrainUnit(){
		return this._train_unit; 
	}

	public void setTrainUnit(String _train_unit){
		this._train_unit = _train_unit;
	}

	@Column(name = "gain_certificate",type=ColumnType.VARCHAR)
	public String getGainCertificate(){
		return this._gain_certificate; 
	}

	public void setGainCertificate(String _gain_certificate){
		this._gain_certificate = _gain_certificate;
	}

	@Column(name = "lecturer_name",type=ColumnType.VARCHAR)
	public String getLecturerName(){
		return this._lecturer_name; 
	}

	public void setLecturerName(String _lecturer_name){
		this._lecturer_name = _lecturer_name;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "mark",type=ColumnType.TINYINT)
	public int getMark(){
		return this._mark; 
	}

	public void setMark(int _mark){
		this._mark = _mark;
	}

	// 实例化内部类
	public static SourceEmpTrainExperienceEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpTrainExperienceEntityAuxiliary();
	}

	public static class SourceEmpTrainExperienceEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpTrainExperienceEntityAuxiliary asTrainExperienceId(String colName, CustomColumn<?, ?>... cols){
			setColName("train_experience_id","`train_experience_id`", colName, "setTrainExperienceId", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asCourseRecordId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_record_id","`course_record_id`", colName, "setCourseRecordId", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asCourseName(String colName, CustomColumn<?, ?>... cols){
			setColName("course_name","`course_name`", colName, "setCourseName", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asFinishDate(String colName, CustomColumn<?, ?>... cols){
			setColName("finish_date","`finish_date`", colName, "setFinishDate", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asTrainTime(String colName, CustomColumn<?, ?>... cols){
			setColName("train_time","`train_time`", colName, "setTrainTime", "getDouble", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asResult(String colName, CustomColumn<?, ?>... cols){
			setColName("result","`result`", colName, "setResult", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asTrainUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("train_unit","`train_unit`", colName, "setTrainUnit", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asGainCertificate(String colName, CustomColumn<?, ?>... cols){
			setColName("gain_certificate","`gain_certificate`", colName, "setGainCertificate", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asLecturerName(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_name","`lecturer_name`", colName, "setLecturerName", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceEmpTrainExperienceEntityAuxiliary asMark(String colName, CustomColumn<?, ?>... cols){
			setColName("mark","`mark`", colName, "setMark", "getInt", cols);
			return this;
		}
	}
}
