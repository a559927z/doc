package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_checkwork_type")
public class SourceDimCheckworkTypeEntity implements Entity{

	private String _checkwork_type_id;
	private String _customer_id;
	private String _checkwork_type_name;
	private int _curt_name;
	private int _show_index;
	private String _refresh;

	public SourceDimCheckworkTypeEntity(){
		super();
	}

	public SourceDimCheckworkTypeEntity(String _checkwork_type_id,String _customer_id,String _checkwork_type_name,int _curt_name,int _show_index,String _refresh){
		this._checkwork_type_id = _checkwork_type_id;
		this._customer_id = _customer_id;
		this._checkwork_type_name = _checkwork_type_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimCheckworkTypeEntity ["+
			"	checkwork_type_id="+_checkwork_type_id+
			"	customer_id="+_customer_id+
			"	checkwork_type_name="+_checkwork_type_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "checkwork_type_id",type=ColumnType.VARCHAR)
	public String getCheckworkTypeId(){
		return this._checkwork_type_id; 
	}

	public void setCheckworkTypeId(String _checkwork_type_id){
		this._checkwork_type_id = _checkwork_type_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "checkwork_type_name",type=ColumnType.VARCHAR)
	public String getCheckworkTypeName(){
		return this._checkwork_type_name; 
	}

	public void setCheckworkTypeName(String _checkwork_type_name){
		this._checkwork_type_name = _checkwork_type_name;
	}

	@Column(name = "curt_name",type=ColumnType.TINYINT)
	public int getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(int _curt_name){
		this._curt_name = _curt_name;
	}

	@Column(name = "show_index",type=ColumnType.TINYINT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceDimCheckworkTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimCheckworkTypeEntityAuxiliary();
	}

	public static class SourceDimCheckworkTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimCheckworkTypeEntityAuxiliary asCheckworkTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("checkwork_type_id","`checkwork_type_id`", colName, "setCheckworkTypeId", "getString", cols);
			return this;
		}
		public SourceDimCheckworkTypeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimCheckworkTypeEntityAuxiliary asCheckworkTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("checkwork_type_name","`checkwork_type_name`", colName, "setCheckworkTypeName", "getString", cols);
			return this;
		}
		public SourceDimCheckworkTypeEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getInt", cols);
			return this;
		}
		public SourceDimCheckworkTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimCheckworkTypeEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
