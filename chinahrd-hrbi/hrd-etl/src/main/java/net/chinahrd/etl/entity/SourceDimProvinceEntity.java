package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_province")
public class SourceDimProvinceEntity implements Entity{

	private String _province_id;
	private String _customer_id;
	private String _province_key;
	private String _province_name;
	private int _show_index;
	private String _curt_name;

	public SourceDimProvinceEntity(){
		super();
	}

	public SourceDimProvinceEntity(String _province_id,String _customer_id,String _province_key,String _province_name,int _show_index,String _curt_name){
		this._province_id = _province_id;
		this._customer_id = _customer_id;
		this._province_key = _province_key;
		this._province_name = _province_name;
		this._show_index = _show_index;
		this._curt_name = _curt_name;
	}

	@Override
	public String toString() {
		return "SourceDimProvinceEntity ["+
			"	province_id="+_province_id+
			"	customer_id="+_customer_id+
			"	province_key="+_province_key+
			"	province_name="+_province_name+
			"	show_index="+_show_index+
			"	curt_name="+_curt_name+
			"]";
	}

	@Column(name = "province_id",type=ColumnType.VARCHAR)
	public String getProvinceId(){
		return this._province_id; 
	}

	public void setProvinceId(String _province_id){
		this._province_id = _province_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "province_key",type=ColumnType.VARCHAR)
	public String getProvinceKey(){
		return this._province_key; 
	}

	public void setProvinceKey(String _province_key){
		this._province_key = _province_key;
	}

	@Column(name = "province_name",type=ColumnType.VARCHAR)
	public String getProvinceName(){
		return this._province_name; 
	}

	public void setProvinceName(String _province_name){
		this._province_name = _province_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "curt_name",type=ColumnType.CHAR)
	public String getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(String _curt_name){
		this._curt_name = _curt_name;
	}

	// 实例化内部类
	public static SourceDimProvinceEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimProvinceEntityAuxiliary();
	}

	public static class SourceDimProvinceEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimProvinceEntityAuxiliary asProvinceId(String colName, CustomColumn<?, ?>... cols){
			setColName("province_id","`province_id`", colName, "setProvinceId", "getString", cols);
			return this;
		}
		public SourceDimProvinceEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimProvinceEntityAuxiliary asProvinceKey(String colName, CustomColumn<?, ?>... cols){
			setColName("province_key","`province_key`", colName, "setProvinceKey", "getString", cols);
			return this;
		}
		public SourceDimProvinceEntityAuxiliary asProvinceName(String colName, CustomColumn<?, ?>... cols){
			setColName("province_name","`province_name`", colName, "setProvinceName", "getString", cols);
			return this;
		}
		public SourceDimProvinceEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimProvinceEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
	}
}
