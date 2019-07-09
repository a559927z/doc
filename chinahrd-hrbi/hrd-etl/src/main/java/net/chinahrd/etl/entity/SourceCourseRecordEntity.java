package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_course_record")
public class SourceCourseRecordEntity implements Entity{

	private String _course_record_id;
	private String _customer_id;
	private String _course_id;
	private String _start_date;
	private String _end_date;
	private int _status;
	private String _train_unit;
	private String _train_plan_id;
	private int _year;

	public SourceCourseRecordEntity(){
		super();
	}

	public SourceCourseRecordEntity(String _course_record_id,String _customer_id,String _course_id,String _start_date,String _end_date,int _status,String _train_unit,String _train_plan_id,int _year){
		this._course_record_id = _course_record_id;
		this._customer_id = _customer_id;
		this._course_id = _course_id;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._status = _status;
		this._train_unit = _train_unit;
		this._train_plan_id = _train_plan_id;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceCourseRecordEntity ["+
			"	course_record_id="+_course_record_id+
			"	customer_id="+_customer_id+
			"	course_id="+_course_id+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	status="+_status+
			"	train_unit="+_train_unit+
			"	train_plan_id="+_train_plan_id+
			"	year="+_year+
			"]";
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

	@Column(name = "status",type=ColumnType.TINYINT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	@Column(name = "train_unit",type=ColumnType.VARCHAR)
	public String getTrainUnit(){
		return this._train_unit; 
	}

	public void setTrainUnit(String _train_unit){
		this._train_unit = _train_unit;
	}

	@Column(name = "train_plan_id",type=ColumnType.VARCHAR)
	public String getTrainPlanId(){
		return this._train_plan_id; 
	}

	public void setTrainPlanId(String _train_plan_id){
		this._train_plan_id = _train_plan_id;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceCourseRecordEntityAuxiliary  getEntityAuxiliary(){
		return new SourceCourseRecordEntityAuxiliary();
	}

	public static class SourceCourseRecordEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceCourseRecordEntityAuxiliary asCourseRecordId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_record_id","`course_record_id`", colName, "setCourseRecordId", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asCourseId(String colName, CustomColumn<?, ?>... cols){
			setColName("course_id","`course_id`", colName, "setCourseId", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asTrainUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("train_unit","`train_unit`", colName, "setTrainUnit", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asTrainPlanId(String colName, CustomColumn<?, ?>... cols){
			setColName("train_plan_id","`train_plan_id`", colName, "setTrainPlanId", "getString", cols);
			return this;
		}
		public SourceCourseRecordEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
