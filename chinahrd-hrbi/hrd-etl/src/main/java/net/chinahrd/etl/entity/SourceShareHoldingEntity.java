package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_share_holding")
public class SourceShareHoldingEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _emp_id;
	private String _usre_name_ch;
	private String _organization_id;
	private String _full_path;
	private int _now_share;
	private int _confer_share;
	private Double _price;
	private String _hold_period;
	private int _sub_num;
	private String _sub_date;
	private int _year;
	private String _refresh;

	public SourceShareHoldingEntity(){
		super();
	}

	public SourceShareHoldingEntity(String _id,String _customer_id,String _emp_id,String _usre_name_ch,String _organization_id,String _full_path,int _now_share,int _confer_share,Double _price,String _hold_period,int _sub_num,String _sub_date,int _year,String _refresh){
		this._id = _id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._usre_name_ch = _usre_name_ch;
		this._organization_id = _organization_id;
		this._full_path = _full_path;
		this._now_share = _now_share;
		this._confer_share = _confer_share;
		this._price = _price;
		this._hold_period = _hold_period;
		this._sub_num = _sub_num;
		this._sub_date = _sub_date;
		this._year = _year;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceShareHoldingEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	usre_name_ch="+_usre_name_ch+
			"	organization_id="+_organization_id+
			"	full_path="+_full_path+
			"	now_share="+_now_share+
			"	confer_share="+_confer_share+
			"	price="+_price+
			"	hold_period="+_hold_period+
			"	sub_num="+_sub_num+
			"	sub_date="+_sub_date+
			"	year="+_year+
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

	@Column(name = "usre_name_ch",type=ColumnType.VARCHAR)
	public String getUsreNameCh(){
		return this._usre_name_ch; 
	}

	public void setUsreNameCh(String _usre_name_ch){
		this._usre_name_ch = _usre_name_ch;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "full_path",type=ColumnType.VARCHAR)
	public String getFullPath(){
		return this._full_path; 
	}

	public void setFullPath(String _full_path){
		this._full_path = _full_path;
	}

	@Column(name = "now_share",type=ColumnType.INT)
	public int getNowShare(){
		return this._now_share; 
	}

	public void setNowShare(int _now_share){
		this._now_share = _now_share;
	}

	@Column(name = "confer_share",type=ColumnType.INT)
	public int getConferShare(){
		return this._confer_share; 
	}

	public void setConferShare(int _confer_share){
		this._confer_share = _confer_share;
	}

	@Column(name = "price",type=ColumnType.DOUBLE)
	public Double getPrice(){
		return this._price; 
	}

	public void setPrice(Double _price){
		this._price = _price;
	}

	@Column(name = "hold_period",type=ColumnType.VARCHAR)
	public String getHoldPeriod(){
		return this._hold_period; 
	}

	public void setHoldPeriod(String _hold_period){
		this._hold_period = _hold_period;
	}

	@Column(name = "sub_num",type=ColumnType.INT)
	public int getSubNum(){
		return this._sub_num; 
	}

	public void setSubNum(int _sub_num){
		this._sub_num = _sub_num;
	}

	@Column(name = "sub_date",type=ColumnType.DATETIME)
	public String getSubDate(){
		return this._sub_date; 
	}

	public void setSubDate(String _sub_date){
		this._sub_date = _sub_date;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceShareHoldingEntityAuxiliary  getEntityAuxiliary(){
		return new SourceShareHoldingEntityAuxiliary();
	}

	public static class SourceShareHoldingEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceShareHoldingEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asUsreNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("usre_name_ch","`usre_name_ch`", colName, "setUsreNameCh", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asNowShare(String colName, CustomColumn<?, ?>... cols){
			setColName("now_share","`now_share`", colName, "setNowShare", "getInt", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asConferShare(String colName, CustomColumn<?, ?>... cols){
			setColName("confer_share","`confer_share`", colName, "setConferShare", "getInt", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asPrice(String colName, CustomColumn<?, ?>... cols){
			setColName("price","`price`", colName, "setPrice", "getDouble", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asHoldPeriod(String colName, CustomColumn<?, ?>... cols){
			setColName("hold_period","`hold_period`", colName, "setHoldPeriod", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asSubNum(String colName, CustomColumn<?, ?>... cols){
			setColName("sub_num","`sub_num`", colName, "setSubNum", "getInt", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asSubDate(String colName, CustomColumn<?, ?>... cols){
			setColName("sub_date","`sub_date`", colName, "setSubDate", "getString", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceShareHoldingEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
