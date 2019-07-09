package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_sequence")
public class SourceDimSequenceEntity implements Entity{

	private String _sequence_id;
	private String _customer_id;
	private String _sequence_key;
	private String _sequence_name;
	private String _curt_name;
	private int _show_index;

	public SourceDimSequenceEntity(){
		super();
	}

	public SourceDimSequenceEntity(String _sequence_id,String _customer_id,String _sequence_key,String _sequence_name,String _curt_name,int _show_index){
		this._sequence_id = _sequence_id;
		this._customer_id = _customer_id;
		this._sequence_key = _sequence_key;
		this._sequence_name = _sequence_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimSequenceEntity ["+
			"	sequence_id="+_sequence_id+
			"	customer_id="+_customer_id+
			"	sequence_key="+_sequence_key+
			"	sequence_name="+_sequence_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "sequence_key",type=ColumnType.VARCHAR)
	public String getSequenceKey(){
		return this._sequence_key; 
	}

	public void setSequenceKey(String _sequence_key){
		this._sequence_key = _sequence_key;
	}

	@Column(name = "sequence_name",type=ColumnType.VARCHAR)
	public String getSequenceName(){
		return this._sequence_name; 
	}

	public void setSequenceName(String _sequence_name){
		this._sequence_name = _sequence_name;
	}

	@Column(name = "curt_name",type=ColumnType.VARCHAR)
	public String getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(String _curt_name){
		this._curt_name = _curt_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimSequenceEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimSequenceEntityAuxiliary();
	}

	public static class SourceDimSequenceEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimSequenceEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceDimSequenceEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimSequenceEntityAuxiliary asSequenceKey(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_key","`sequence_key`", colName, "setSequenceKey", "getString", cols);
			return this;
		}
		public SourceDimSequenceEntityAuxiliary asSequenceName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_name","`sequence_name`", colName, "setSequenceName", "getString", cols);
			return this;
		}
		public SourceDimSequenceEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
		public SourceDimSequenceEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
