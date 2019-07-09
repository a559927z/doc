package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_change_type")
public class SourceDimChangeTypeEntity implements Entity{

	private String _change_type_id;
	private String _customer_id;
	private String _change_type_name;
	private String _curt_name;
	private int _show_index;
	private String _refresh;

	public SourceDimChangeTypeEntity(){
		super();
	}

	public SourceDimChangeTypeEntity(String _change_type_id,String _customer_id,String _change_type_name,String _curt_name,int _show_index,String _refresh){
		this._change_type_id = _change_type_id;
		this._customer_id = _customer_id;
		this._change_type_name = _change_type_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimChangeTypeEntity ["+
			"	change_type_id="+_change_type_id+
			"	customer_id="+_customer_id+
			"	change_type_name="+_change_type_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "change_type_id",type=ColumnType.VARCHAR)
	public String getChangeTypeId(){
		return this._change_type_id; 
	}

	public void setChangeTypeId(String _change_type_id){
		this._change_type_id = _change_type_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "change_type_name",type=ColumnType.VARCHAR)
	public String getChangeTypeName(){
		return this._change_type_name; 
	}

	public void setChangeTypeName(String _change_type_name){
		this._change_type_name = _change_type_name;
	}

	@Column(name = "curt_name",type=ColumnType.CHAR)
	public String getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(String _curt_name){
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
	public static SourceDimChangeTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimChangeTypeEntityAuxiliary();
	}

	public static class SourceDimChangeTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimChangeTypeEntityAuxiliary asChangeTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("change_type_id","`change_type_id`", colName, "setChangeTypeId", "getString", cols);
			return this;
		}
		public SourceDimChangeTypeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimChangeTypeEntityAuxiliary asChangeTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("change_type_name","`change_type_name`", colName, "setChangeTypeName", "getString", cols);
			return this;
		}
		public SourceDimChangeTypeEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
		public SourceDimChangeTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimChangeTypeEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
