package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_encourages")
public class SourceDimEncouragesEntity implements Entity{

	private String _encourages_id;
	private String _customer_id;
	private String _encourages_key;
	private String _content;
	private int _show_index;
	private String _c_id;

	public SourceDimEncouragesEntity(){
		super();
	}

	public SourceDimEncouragesEntity(String _encourages_id,String _customer_id,String _encourages_key,String _content,int _show_index,String _c_id){
		this._encourages_id = _encourages_id;
		this._customer_id = _customer_id;
		this._encourages_key = _encourages_key;
		this._content = _content;
		this._show_index = _show_index;
		this._c_id = _c_id;
	}

	@Override
	public String toString() {
		return "SourceDimEncouragesEntity ["+
			"	encourages_id="+_encourages_id+
			"	customer_id="+_customer_id+
			"	encourages_key="+_encourages_key+
			"	content="+_content+
			"	show_index="+_show_index+
			"	c_id="+_c_id+
			"]";
	}

	@Column(name = "encourages_id",type=ColumnType.VARCHAR)
	public String getEncouragesId(){
		return this._encourages_id; 
	}

	public void setEncouragesId(String _encourages_id){
		this._encourages_id = _encourages_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "encourages_key",type=ColumnType.VARCHAR)
	public String getEncouragesKey(){
		return this._encourages_key; 
	}

	public void setEncouragesKey(String _encourages_key){
		this._encourages_key = _encourages_key;
	}

	@Column(name = "content",type=ColumnType.VARCHAR)
	public String getContent(){
		return this._content; 
	}

	public void setContent(String _content){
		this._content = _content;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "c_id",type=ColumnType.VARCHAR)
	public String getCId(){
		return this._c_id; 
	}

	public void setCId(String _c_id){
		this._c_id = _c_id;
	}

	// 实例化内部类
	public static SourceDimEncouragesEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimEncouragesEntityAuxiliary();
	}

	public static class SourceDimEncouragesEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimEncouragesEntityAuxiliary asEncouragesId(String colName, CustomColumn<?, ?>... cols){
			setColName("encourages_id","`encourages_id`", colName, "setEncouragesId", "getString", cols);
			return this;
		}
		public SourceDimEncouragesEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimEncouragesEntityAuxiliary asEncouragesKey(String colName, CustomColumn<?, ?>... cols){
			setColName("encourages_key","`encourages_key`", colName, "setEncouragesKey", "getString", cols);
			return this;
		}
		public SourceDimEncouragesEntityAuxiliary asContent(String colName, CustomColumn<?, ?>... cols){
			setColName("content","`content`", colName, "setContent", "getString", cols);
			return this;
		}
		public SourceDimEncouragesEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimEncouragesEntityAuxiliary asCId(String colName, CustomColumn<?, ?>... cols){
			setColName("c_id","`c_id`", colName, "setCId", "getString", cols);
			return this;
		}
	}
}
