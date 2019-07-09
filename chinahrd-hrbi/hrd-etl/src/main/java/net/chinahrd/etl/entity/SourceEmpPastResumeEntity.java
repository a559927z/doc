package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_past_resume")
public class SourceEmpPastResumeEntity implements Entity{

	private String _emp_past_resume_id;
	private String _customer_id;
	private String _emp_id;
	private String _work_unit;
	private String _department_name;
	private String _position_name;
	private String _bonus_penalty_name;
	private String _witness_name;
	private String _witness_contact_info;
	private String _change_reason;
	private String _entry_date;
	private String _run_off_date;
	private int _mark;

	public SourceEmpPastResumeEntity(){
		super();
	}

	public SourceEmpPastResumeEntity(String _emp_past_resume_id,String _customer_id,String _emp_id,String _work_unit,String _department_name,String _position_name,String _bonus_penalty_name,String _witness_name,String _witness_contact_info,String _change_reason,String _entry_date,String _run_off_date,int _mark){
		this._emp_past_resume_id = _emp_past_resume_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._work_unit = _work_unit;
		this._department_name = _department_name;
		this._position_name = _position_name;
		this._bonus_penalty_name = _bonus_penalty_name;
		this._witness_name = _witness_name;
		this._witness_contact_info = _witness_contact_info;
		this._change_reason = _change_reason;
		this._entry_date = _entry_date;
		this._run_off_date = _run_off_date;
		this._mark = _mark;
	}

	@Override
	public String toString() {
		return "SourceEmpPastResumeEntity ["+
			"	emp_past_resume_id="+_emp_past_resume_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	work_unit="+_work_unit+
			"	department_name="+_department_name+
			"	position_name="+_position_name+
			"	bonus_penalty_name="+_bonus_penalty_name+
			"	witness_name="+_witness_name+
			"	witness_contact_info="+_witness_contact_info+
			"	change_reason="+_change_reason+
			"	entry_date="+_entry_date+
			"	run_off_date="+_run_off_date+
			"	mark="+_mark+
			"]";
	}

	@Column(name = "emp_past_resume_id",type=ColumnType.VARCHAR)
	public String getEmpPastResumeId(){
		return this._emp_past_resume_id; 
	}

	public void setEmpPastResumeId(String _emp_past_resume_id){
		this._emp_past_resume_id = _emp_past_resume_id;
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

	@Column(name = "work_unit",type=ColumnType.VARCHAR)
	public String getWorkUnit(){
		return this._work_unit; 
	}

	public void setWorkUnit(String _work_unit){
		this._work_unit = _work_unit;
	}

	@Column(name = "department_name",type=ColumnType.VARCHAR)
	public String getDepartmentName(){
		return this._department_name; 
	}

	public void setDepartmentName(String _department_name){
		this._department_name = _department_name;
	}

	@Column(name = "position_name",type=ColumnType.VARCHAR)
	public String getPositionName(){
		return this._position_name; 
	}

	public void setPositionName(String _position_name){
		this._position_name = _position_name;
	}

	@Column(name = "bonus_penalty_name",type=ColumnType.VARCHAR)
	public String getBonusPenaltyName(){
		return this._bonus_penalty_name; 
	}

	public void setBonusPenaltyName(String _bonus_penalty_name){
		this._bonus_penalty_name = _bonus_penalty_name;
	}

	@Column(name = "witness_name",type=ColumnType.VARCHAR)
	public String getWitnessName(){
		return this._witness_name; 
	}

	public void setWitnessName(String _witness_name){
		this._witness_name = _witness_name;
	}

	@Column(name = "witness_contact_info",type=ColumnType.VARCHAR)
	public String getWitnessContactInfo(){
		return this._witness_contact_info; 
	}

	public void setWitnessContactInfo(String _witness_contact_info){
		this._witness_contact_info = _witness_contact_info;
	}

	@Column(name = "change_reason",type=ColumnType.VARCHAR)
	public String getChangeReason(){
		return this._change_reason; 
	}

	public void setChangeReason(String _change_reason){
		this._change_reason = _change_reason;
	}

	@Column(name = "entry_date",type=ColumnType.DATETIME)
	public String getEntryDate(){
		return this._entry_date; 
	}

	public void setEntryDate(String _entry_date){
		this._entry_date = _entry_date;
	}

	@Column(name = "run_off_date",type=ColumnType.DATETIME)
	public String getRunOffDate(){
		return this._run_off_date; 
	}

	public void setRunOffDate(String _run_off_date){
		this._run_off_date = _run_off_date;
	}

	@Column(name = "mark",type=ColumnType.TINYINT)
	public int getMark(){
		return this._mark; 
	}

	public void setMark(int _mark){
		this._mark = _mark;
	}

	// 实例化内部类
	public static SourceEmpPastResumeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpPastResumeEntityAuxiliary();
	}

	public static class SourceEmpPastResumeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpPastResumeEntityAuxiliary asEmpPastResumeId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_past_resume_id","`emp_past_resume_id`", colName, "setEmpPastResumeId", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asWorkUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("work_unit","`work_unit`", colName, "setWorkUnit", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asDepartmentName(String colName, CustomColumn<?, ?>... cols){
			setColName("department_name","`department_name`", colName, "setDepartmentName", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asBonusPenaltyName(String colName, CustomColumn<?, ?>... cols){
			setColName("bonus_penalty_name","`bonus_penalty_name`", colName, "setBonusPenaltyName", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asWitnessName(String colName, CustomColumn<?, ?>... cols){
			setColName("witness_name","`witness_name`", colName, "setWitnessName", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asWitnessContactInfo(String colName, CustomColumn<?, ?>... cols){
			setColName("witness_contact_info","`witness_contact_info`", colName, "setWitnessContactInfo", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asChangeReason(String colName, CustomColumn<?, ?>... cols){
			setColName("change_reason","`change_reason`", colName, "setChangeReason", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asEntryDate(String colName, CustomColumn<?, ?>... cols){
			setColName("entry_date","`entry_date`", colName, "setEntryDate", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asRunOffDate(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_date","`run_off_date`", colName, "setRunOffDate", "getString", cols);
			return this;
		}
		public SourceEmpPastResumeEntityAuxiliary asMark(String colName, CustomColumn<?, ?>... cols){
			setColName("mark","`mark`", colName, "setMark", "getInt", cols);
			return this;
		}
	}
}
