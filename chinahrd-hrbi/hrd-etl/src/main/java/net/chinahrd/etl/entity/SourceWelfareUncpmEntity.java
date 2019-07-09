package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_welfare_uncpm")
public class SourceWelfareUncpmEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _emp_id;
	private String _uncpm_id;
	private String _note;
	private String _date;
	private int _year_month;
	private String _refresh;

	public SourceWelfareUncpmEntity(){
		super();
	}

	public SourceWelfareUncpmEntity(String _id,String _customer_id,String _emp_id,String _uncpm_id,String _note,String _date,int _year_month,String _refresh){
		this._id = _id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._uncpm_id = _uncpm_id;
		this._note = _note;
		this._date = _date;
		this._year_month = _year_month;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceWelfareUncpmEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	uncpm_id="+_uncpm_id+
			"	note="+_note+
			"	date="+_date+
			"	year_month="+_year_month+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "id",type=ColumnType.VARCHAR)
	public String getId(){
		return this._id; 
	}

	public void setId(String _id){
		this._id = _id;
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

	@Column(name = "uncpm_id",type=ColumnType.VARCHAR)
	public String getUncpmId(){
		return this._uncpm_id; 
	}

	public void setUncpmId(String _uncpm_id){
		this._uncpm_id = _uncpm_id;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "date",type=ColumnType.DATE)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceWelfareUncpmEntityAuxiliary  getEntityAuxiliary(){
		return new SourceWelfareUncpmEntityAuxiliary();
	}

	public static class SourceWelfareUncpmEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceWelfareUncpmEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asUncpmId(String colName, CustomColumn<?, ?>... cols){
			setColName("uncpm_id","`uncpm_id`", colName, "setUncpmId", "getString", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceWelfareUncpmEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
