package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_welfare_nfb")
public class SourceWelfareNfbEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _emp_id;
	private String _user_name_ch;
	private String _nfb_id;
	private Double _welfare_value;
	private String _date;
	private int _year_month;
	private String _refresh;

	public SourceWelfareNfbEntity(){
		super();
	}

	public SourceWelfareNfbEntity(String _id,String _customer_id,String _emp_id,String _user_name_ch,String _nfb_id,Double _welfare_value,String _date,int _year_month,String _refresh){
		this._id = _id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._user_name_ch = _user_name_ch;
		this._nfb_id = _nfb_id;
		this._welfare_value = _welfare_value;
		this._date = _date;
		this._year_month = _year_month;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceWelfareNfbEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	user_name_ch="+_user_name_ch+
			"	nfb_id="+_nfb_id+
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

	@Column(name = "nfb_id",type=ColumnType.VARCHAR)
	public String getNfbId(){
		return this._nfb_id; 
	}

	public void setNfbId(String _nfb_id){
		this._nfb_id = _nfb_id;
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
	public static SourceWelfareNfbEntityAuxiliary  getEntityAuxiliary(){
		return new SourceWelfareNfbEntityAuxiliary();
	}

	public static class SourceWelfareNfbEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceWelfareNfbEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asNfbId(String colName, CustomColumn<?, ?>... cols){
			setColName("nfb_id","`nfb_id`", colName, "setNfbId", "getString", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asWelfareValue(String colName, CustomColumn<?, ?>... cols){
			setColName("welfare_value","`welfare_value`", colName, "setWelfareValue", "getDouble", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceWelfareNfbEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
