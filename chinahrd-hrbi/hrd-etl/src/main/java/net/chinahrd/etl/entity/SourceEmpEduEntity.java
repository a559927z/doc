package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_edu")
public class SourceEmpEduEntity implements Entity{

	private String _emp_edu_id;
	private String _customer_id;
	private String _emp_id;
	private String _school_name;
	private String _degree_id;
	private String _degree;
	private String _major;
	private String _start_date;
	private String _end_date;
	private int _is_last_major;
	private String _academic_degree;
	private String _develop_mode;
	private String _bonus;
	private String _note;

	public SourceEmpEduEntity(){
		super();
	}

	public SourceEmpEduEntity(String _emp_edu_id,String _customer_id,String _emp_id,String _school_name,String _degree_id,String _degree,String _major,String _start_date,String _end_date,int _is_last_major,String _academic_degree,String _develop_mode,String _bonus,String _note){
		this._emp_edu_id = _emp_edu_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._school_name = _school_name;
		this._degree_id = _degree_id;
		this._degree = _degree;
		this._major = _major;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._is_last_major = _is_last_major;
		this._academic_degree = _academic_degree;
		this._develop_mode = _develop_mode;
		this._bonus = _bonus;
		this._note = _note;
	}

	@Override
	public String toString() {
		return "SourceEmpEduEntity ["+
			"	emp_edu_id="+_emp_edu_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	school_name="+_school_name+
			"	degree_id="+_degree_id+
			"	degree="+_degree+
			"	major="+_major+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	is_last_major="+_is_last_major+
			"	academic_degree="+_academic_degree+
			"	develop_mode="+_develop_mode+
			"	bonus="+_bonus+
			"	note="+_note+
			"]";
	}

	@Column(name = "emp_edu_id",type=ColumnType.VARCHAR)
	public String getEmpEduId(){
		return this._emp_edu_id; 
	}

	public void setEmpEduId(String _emp_edu_id){
		this._emp_edu_id = _emp_edu_id;
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

	@Column(name = "school_name",type=ColumnType.VARCHAR)
	public String getSchoolName(){
		return this._school_name; 
	}

	public void setSchoolName(String _school_name){
		this._school_name = _school_name;
	}

	@Column(name = "degree_id",type=ColumnType.VARCHAR)
	public String getDegreeId(){
		return this._degree_id; 
	}

	public void setDegreeId(String _degree_id){
		this._degree_id = _degree_id;
	}

	@Column(name = "degree",type=ColumnType.VARCHAR)
	public String getDegree(){
		return this._degree; 
	}

	public void setDegree(String _degree){
		this._degree = _degree;
	}

	@Column(name = "major",type=ColumnType.VARCHAR)
	public String getMajor(){
		return this._major; 
	}

	public void setMajor(String _major){
		this._major = _major;
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

	@Column(name = "is_last_major",type=ColumnType.TINYINT)
	public int getIsLastMajor(){
		return this._is_last_major; 
	}

	public void setIsLastMajor(int _is_last_major){
		this._is_last_major = _is_last_major;
	}

	@Column(name = "academic_degree",type=ColumnType.VARCHAR)
	public String getAcademicDegree(){
		return this._academic_degree; 
	}

	public void setAcademicDegree(String _academic_degree){
		this._academic_degree = _academic_degree;
	}

	@Column(name = "develop_mode",type=ColumnType.VARCHAR)
	public String getDevelopMode(){
		return this._develop_mode; 
	}

	public void setDevelopMode(String _develop_mode){
		this._develop_mode = _develop_mode;
	}

	@Column(name = "bonus",type=ColumnType.VARCHAR)
	public String getBonus(){
		return this._bonus; 
	}

	public void setBonus(String _bonus){
		this._bonus = _bonus;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	// 实例化内部类
	public static SourceEmpEduEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpEduEntityAuxiliary();
	}

	public static class SourceEmpEduEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpEduEntityAuxiliary asEmpEduId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_edu_id","`emp_edu_id`", colName, "setEmpEduId", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asSchoolName(String colName, CustomColumn<?, ?>... cols){
			setColName("school_name","`school_name`", colName, "setSchoolName", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asDegreeId(String colName, CustomColumn<?, ?>... cols){
			setColName("degree_id","`degree_id`", colName, "setDegreeId", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asDegree(String colName, CustomColumn<?, ?>... cols){
			setColName("degree","`degree`", colName, "setDegree", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asMajor(String colName, CustomColumn<?, ?>... cols){
			setColName("major","`major`", colName, "setMajor", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asIsLastMajor(String colName, CustomColumn<?, ?>... cols){
			setColName("is_last_major","`is_last_major`", colName, "setIsLastMajor", "getInt", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asAcademicDegree(String colName, CustomColumn<?, ?>... cols){
			setColName("academic_degree","`academic_degree`", colName, "setAcademicDegree", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asDevelopMode(String colName, CustomColumn<?, ?>... cols){
			setColName("develop_mode","`develop_mode`", colName, "setDevelopMode", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asBonus(String colName, CustomColumn<?, ?>... cols){
			setColName("bonus","`bonus`", colName, "setBonus", "getString", cols);
			return this;
		}
		public SourceEmpEduEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
	}
}
