package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_pay_value")
public class SourcePayValueEntity implements Entity{

	private String _pay_value_id;
	private String _customer_id;
	private String _organization_id;
	private Double _pay_value;
	private int _year;
	private String _refresh;

	public SourcePayValueEntity(){
		super();
	}

	public SourcePayValueEntity(String _pay_value_id,String _customer_id,String _organization_id,Double _pay_value,int _year,String _refresh){
		this._pay_value_id = _pay_value_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._pay_value = _pay_value;
		this._year = _year;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourcePayValueEntity ["+
			"	pay_value_id="+_pay_value_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	pay_value="+_pay_value+
			"	year="+_year+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "pay_value_id",type=ColumnType.VARCHAR)
	public String getPayValueId(){
		return this._pay_value_id; 
	}

	public void setPayValueId(String _pay_value_id){
		this._pay_value_id = _pay_value_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "pay_value",type=ColumnType.DOUBLE)
	public Double getPayValue(){
		return this._pay_value; 
	}

	public void setPayValue(Double _pay_value){
		this._pay_value = _pay_value;
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
	public static SourcePayValueEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePayValueEntityAuxiliary();
	}

	public static class SourcePayValueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePayValueEntityAuxiliary asPayValueId(String colName, CustomColumn<?, ?>... cols){
			setColName("pay_value_id","`pay_value_id`", colName, "setPayValueId", "getString", cols);
			return this;
		}
		public SourcePayValueEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePayValueEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourcePayValueEntityAuxiliary asPayValue(String colName, CustomColumn<?, ?>... cols){
			setColName("pay_value","`pay_value`", colName, "setPayValue", "getDouble", cols);
			return this;
		}
		public SourcePayValueEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourcePayValueEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
