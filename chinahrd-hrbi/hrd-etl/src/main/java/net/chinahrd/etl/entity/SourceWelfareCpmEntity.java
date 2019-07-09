package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_welfare_cpm")
public class SourceWelfareCpmEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _emp_id;
	private String _user_name_ch;
	private String _cpm_id;
	private Double _welfare_value;
	private String _date;
	private int _year_month;
	private String _refresh;

	public SourceWelfareCpmEntity(){
		super();
	}

	public SourceWelfareCpmEntity(String _id,String _customer_id,String _emp_id,String _user_name_ch,String _cpm_id,Double _welfare_value,String _date,int _year_month,String _refresh){
		this._id = _id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._user_name_ch = _user_name_ch;
		this._cpm_id = _cpm_id;
		this._welfare_value = _welfare_value;
		this._date = _date;
		this._year_month = _year_month;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceWelfareCpmEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	user_name_ch="+_user_name_ch+
			"	cpm_id="+_cpm_id+
			"	welfare_value="+_welfare_value+
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

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "cpm_id",type=ColumnType.VARCHAR)
	public String getCpmId(){
		return this._cpm_id; 
	}

	public void setCpmId(String _cpm_id){
		this._cpm_id = _cpm_id;
	}

	@Column(name = "welfare_value",type=ColumnType.DOUBLE)
	public Double getWelfareValue(){
		return this._welfare_value; 
	}

	public void setWelfareValue(Double _welfare_value){
		this._welfare_value = _welfare_value;
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
	public static SourceWelfareCpmEntityAuxiliary  getEntityAuxiliary(){
		return new SourceWelfareCpmEntityAuxiliary();
	}

	public static class SourceWelfareCpmEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceWelfareCpmEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asCpmId(String colName, CustomColumn<?, ?>... cols){
			setColName("cpm_id","`cpm_id`", colName, "setCpmId", "getString", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asWelfareValue(String colName, CustomColumn<?, ?>... cols){
			setColName("welfare_value","`welfare_value`", colName, "setWelfareValue", "getDouble", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceWelfareCpmEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
