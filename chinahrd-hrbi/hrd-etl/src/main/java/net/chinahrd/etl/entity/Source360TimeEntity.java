package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_360_time")
public class Source360TimeEntity implements Entity{

	private String _360_time_id;
	private String _customer_id;
	private String _emp_id;
	private String _report_date;
	private String _report_name;
	private String _path;
	private int _type;
	private int _year;

	public Source360TimeEntity(){
		super();
	}

	public Source360TimeEntity(String _360_time_id,String _customer_id,String _emp_id,String _report_date,String _report_name,String _path,int _type,int _year){
		this._360_time_id = _360_time_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._report_date = _report_date;
		this._report_name = _report_name;
		this._path = _path;
		this._type = _type;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "Source360TimeEntity ["+
			"	360_time_id="+_360_time_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	report_date="+_report_date+
			"	report_name="+_report_name+
			"	path="+_path+
			"	type="+_type+
			"	year="+_year+
			"]";
	}

	@Column(name = "360_time_id",type=ColumnType.VARCHAR)
	public String get360TimeId(){
		return this._360_time_id; 
	}

	public void set360TimeId(String _360_time_id){
		this._360_time_id = _360_time_id;
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

	@Column(name = "report_date",type=ColumnType.DATETIME)
	public String getReportDate(){
		return this._report_date; 
	}

	public void setReportDate(String _report_date){
		this._report_date = _report_date;
	}

	@Column(name = "report_name",type=ColumnType.VARCHAR)
	public String getReportName(){
		return this._report_name; 
	}

	public void setReportName(String _report_name){
		this._report_name = _report_name;
	}

	@Column(name = "path",type=ColumnType.VARCHAR)
	public String getPath(){
		return this._path; 
	}

	public void setPath(String _path){
		this._path = _path;
	}

	@Column(name = "type",type=ColumnType.INT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static Source360TimeEntityAuxiliary  getEntityAuxiliary(){
		return new Source360TimeEntityAuxiliary();
	}

	public static class Source360TimeEntityAuxiliary extends AbstractSqlAuxiliary{
		public Source360TimeEntityAuxiliary as360TimeId(String colName, CustomColumn<?, ?>... cols){
			setColName("360_time_id","`360_time_id`", colName, "set360TimeId", "getString", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asReportDate(String colName, CustomColumn<?, ?>... cols){
			setColName("report_date","`report_date`", colName, "setReportDate", "getString", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asReportName(String colName, CustomColumn<?, ?>... cols){
			setColName("report_name","`report_name`", colName, "setReportName", "getString", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asPath(String colName, CustomColumn<?, ?>... cols){
			setColName("path","`path`", colName, "setPath", "getString", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public Source360TimeEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
