package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_position")
public class SourceDimPositionEntity implements Entity{

	private String _position_id;
	private String _customer_id;
	private String _position_key;
	private String _position_name;
	private int _show_index;
	private String _refresh;

	public SourceDimPositionEntity(){
		super();
	}

	public SourceDimPositionEntity(String _position_id,String _customer_id,String _position_key,String _position_name,int _show_index,String _refresh){
		this._position_id = _position_id;
		this._customer_id = _customer_id;
		this._position_key = _position_key;
		this._position_name = _position_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimPositionEntity ["+
			"	position_id="+_position_id+
			"	customer_id="+_customer_id+
			"	position_key="+_position_key+
			"	position_name="+_position_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "position_key",type=ColumnType.VARCHAR)
	public String getPositionKey(){
		return this._position_key; 
	}

	public void setPositionKey(String _position_key){
		this._position_key = _position_key;
	}

	@Column(name = "position_name",type=ColumnType.VARCHAR)
	public String getPositionName(){
		return this._position_name; 
	}

	public void setPositionName(String _position_name){
		this._position_name = _position_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
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
	public static SourceDimPositionEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimPositionEntityAuxiliary();
	}

	public static class SourceDimPositionEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimPositionEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceDimPositionEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimPositionEntityAuxiliary asPositionKey(String colName, CustomColumn<?, ?>... cols){
			setColName("position_key","`position_key`", colName, "setPositionKey", "getString", cols);
			return this;
		}
		public SourceDimPositionEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourceDimPositionEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimPositionEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
