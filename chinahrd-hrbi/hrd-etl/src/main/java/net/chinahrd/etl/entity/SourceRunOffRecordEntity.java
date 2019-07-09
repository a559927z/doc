package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_run_off_record")
public class SourceRunOffRecordEntity implements Entity{

	private String _run_off_record_id;
	private String _customer_id;
	private String _emp_id;
	private String _run_off_id;
	private String _where_abouts;
	private String _run_off_date;
	private int _is_warn;
	private int _re_hire;

	public SourceRunOffRecordEntity(){
		super();
	}

	public SourceRunOffRecordEntity(String _run_off_record_id,String _customer_id,String _emp_id,String _run_off_id,String _where_abouts,String _run_off_date,int _is_warn,int _re_hire){
		this._run_off_record_id = _run_off_record_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._run_off_id = _run_off_id;
		this._where_abouts = _where_abouts;
		this._run_off_date = _run_off_date;
		this._is_warn = _is_warn;
		this._re_hire = _re_hire;
	}

	@Override
	public String toString() {
		return "SourceRunOffRecordEntity ["+
			"	run_off_record_id="+_run_off_record_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	run_off_id="+_run_off_id+
			"	where_abouts="+_where_abouts+
			"	run_off_date="+_run_off_date+
			"	is_warn="+_is_warn+
			"	re_hire="+_re_hire+
			"]";
	}

	@Column(name = "run_off_record_id",type=ColumnType.VARCHAR)
	public String getRunOffRecordId(){
		return this._run_off_record_id; 
	}

	public void setRunOffRecordId(String _run_off_record_id){
		this._run_off_record_id = _run_off_record_id;
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

	@Column(name = "run_off_id",type=ColumnType.VARCHAR)
	public String getRunOffId(){
		return this._run_off_id; 
	}

	public void setRunOffId(String _run_off_id){
		this._run_off_id = _run_off_id;
	}

	@Column(name = "where_abouts",type=ColumnType.VARCHAR)
	public String getWhereAbouts(){
		return this._where_abouts; 
	}

	public void setWhereAbouts(String _where_abouts){
		this._where_abouts = _where_abouts;
	}

	@Column(name = "run_off_date",type=ColumnType.DATETIME)
	public String getRunOffDate(){
		return this._run_off_date; 
	}

	public void setRunOffDate(String _run_off_date){
		this._run_off_date = _run_off_date;
	}

	@Column(name = "is_warn",type=ColumnType.INT)
	public int getIsWarn(){
		return this._is_warn; 
	}

	public void setIsWarn(int _is_warn){
		this._is_warn = _is_warn;
	}

	@Column(name = "re_hire",type=ColumnType.INT)
	public int getReHire(){
		return this._re_hire; 
	}

	public void setReHire(int _re_hire){
		this._re_hire = _re_hire;
	}

	// 实例化内部类
	public static SourceRunOffRecordEntityAuxiliary  getEntityAuxiliary(){
		return new SourceRunOffRecordEntityAuxiliary();
	}

	public static class SourceRunOffRecordEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceRunOffRecordEntityAuxiliary asRunOffRecordId(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_record_id","`run_off_record_id`", colName, "setRunOffRecordId", "getString", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asRunOffId(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_id","`run_off_id`", colName, "setRunOffId", "getString", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asWhereAbouts(String colName, CustomColumn<?, ?>... cols){
			setColName("where_abouts","`where_abouts`", colName, "setWhereAbouts", "getString", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asRunOffDate(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_date","`run_off_date`", colName, "setRunOffDate", "getString", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asIsWarn(String colName, CustomColumn<?, ?>... cols){
			setColName("is_warn","`is_warn`", colName, "setIsWarn", "getInt", cols);
			return this;
		}
		public SourceRunOffRecordEntityAuxiliary asReHire(String colName, CustomColumn<?, ?>... cols){
			setColName("re_hire","`re_hire`", colName, "setReHire", "getInt", cols);
			return this;
		}
	}
}
