package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_channel")
public class SourceDimChannelEntity implements Entity{

	private String _channel_id;
	private String _customer_id;
	private String _channel_key;
	private String _channel_name;
	private int _show_index;
	private String _refresh;

	public SourceDimChannelEntity(){
		super();
	}

	public SourceDimChannelEntity(String _channel_id,String _customer_id,String _channel_key,String _channel_name,int _show_index,String _refresh){
		this._channel_id = _channel_id;
		this._customer_id = _customer_id;
		this._channel_key = _channel_key;
		this._channel_name = _channel_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimChannelEntity ["+
			"	channel_id="+_channel_id+
			"	customer_id="+_customer_id+
			"	channel_key="+_channel_key+
			"	channel_name="+_channel_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "channel_id",type=ColumnType.VARCHAR)
	public String getChannelId(){
		return this._channel_id; 
	}

	public void setChannelId(String _channel_id){
		this._channel_id = _channel_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "channel_key",type=ColumnType.VARCHAR)
	public String getChannelKey(){
		return this._channel_key; 
	}

	public void setChannelKey(String _channel_key){
		this._channel_key = _channel_key;
	}

	@Column(name = "channel_name",type=ColumnType.VARCHAR)
	public String getChannelName(){
		return this._channel_name; 
	}

	public void setChannelName(String _channel_name){
		this._channel_name = _channel_name;
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
	public static SourceDimChannelEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimChannelEntityAuxiliary();
	}

	public static class SourceDimChannelEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimChannelEntityAuxiliary asChannelId(String colName, CustomColumn<?, ?>... cols){
			setColName("channel_id","`channel_id`", colName, "setChannelId", "getString", cols);
			return this;
		}
		public SourceDimChannelEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimChannelEntityAuxiliary asChannelKey(String colName, CustomColumn<?, ?>... cols){
			setColName("channel_key","`channel_key`", colName, "setChannelKey", "getString", cols);
			return this;
		}
		public SourceDimChannelEntityAuxiliary asChannelName(String colName, CustomColumn<?, ?>... cols){
			setColName("channel_name","`channel_name`", colName, "setChannelName", "getString", cols);
			return this;
		}
		public SourceDimChannelEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimChannelEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
