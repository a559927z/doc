package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_city")
public class SourceDimCityEntity implements Entity{

	private String _city_id;
	private String _customer_id;
	private String _city_key;
	private String _city_name;
	private String _province_id;
	private int _show_index;

	public SourceDimCityEntity(){
		super();
	}

	public SourceDimCityEntity(String _city_id,String _customer_id,String _city_key,String _city_name,String _province_id,int _show_index){
		this._city_id = _city_id;
		this._customer_id = _customer_id;
		this._city_key = _city_key;
		this._city_name = _city_name;
		this._province_id = _province_id;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimCityEntity ["+
			"	city_id="+_city_id+
			"	customer_id="+_customer_id+
			"	city_key="+_city_key+
			"	city_name="+_city_name+
			"	province_id="+_province_id+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "city_id",type=ColumnType.VARCHAR)
	public String getCityId(){
		return this._city_id; 
	}

	public void setCityId(String _city_id){
		this._city_id = _city_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "city_key",type=ColumnType.VARCHAR)
	public String getCityKey(){
		return this._city_key; 
	}

	public void setCityKey(String _city_key){
		this._city_key = _city_key;
	}

	@Column(name = "city_name",type=ColumnType.VARCHAR)
	public String getCityName(){
		return this._city_name; 
	}

	public void setCityName(String _city_name){
		this._city_name = _city_name;
	}

	@Column(name = "province_id",type=ColumnType.VARCHAR)
	public String getProvinceId(){
		return this._province_id; 
	}

	public void setProvinceId(String _province_id){
		this._province_id = _province_id;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimCityEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimCityEntityAuxiliary();
	}

	public static class SourceDimCityEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimCityEntityAuxiliary asCityId(String colName, CustomColumn<?, ?>... cols){
			setColName("city_id","`city_id`", colName, "setCityId", "getString", cols);
			return this;
		}
		public SourceDimCityEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimCityEntityAuxiliary asCityKey(String colName, CustomColumn<?, ?>... cols){
			setColName("city_key","`city_key`", colName, "setCityKey", "getString", cols);
			return this;
		}
		public SourceDimCityEntityAuxiliary asCityName(String colName, CustomColumn<?, ?>... cols){
			setColName("city_name","`city_name`", colName, "setCityName", "getString", cols);
			return this;
		}
		public SourceDimCityEntityAuxiliary asProvinceId(String colName, CustomColumn<?, ?>... cols){
			setColName("province_id","`province_id`", colName, "setProvinceId", "getString", cols);
			return this;
		}
		public SourceDimCityEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
