package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_sequence_sub")
public class SourceDimSequenceSubEntity implements Entity{

	private String _sequence_sub_id;
	private String _customer_id;
	private String _sequence_id;
	private String _sequence_sub_key;
	private String _sequence_sub_name;
	private String _curt_name;
	private int _show_index;

	public SourceDimSequenceSubEntity(){
		super();
	}

	public SourceDimSequenceSubEntity(String _sequence_sub_id,String _customer_id,String _sequence_id,String _sequence_sub_key,String _sequence_sub_name,String _curt_name,int _show_index){
		this._sequence_sub_id = _sequence_sub_id;
		this._customer_id = _customer_id;
		this._sequence_id = _sequence_id;
		this._sequence_sub_key = _sequence_sub_key;
		this._sequence_sub_name = _sequence_sub_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimSequenceSubEntity ["+
			"	sequence_sub_id="+_sequence_sub_id+
			"	customer_id="+_customer_id+
			"	sequence_id="+_sequence_id+
			"	sequence_sub_key="+_sequence_sub_key+
			"	sequence_sub_name="+_sequence_sub_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "sequence_sub_id",type=ColumnType.VARCHAR)
	public String getSequenceSubId(){
		return this._sequence_sub_id; 
	}

	public void setSequenceSubId(String _sequence_sub_id){
		this._sequence_sub_id = _sequence_sub_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "sequence_sub_key",type=ColumnType.VARCHAR)
	public String getSequenceSubKey(){
		return this._sequence_sub_key; 
	}

	public void setSequenceSubKey(String _sequence_sub_key){
		this._sequence_sub_key = _sequence_sub_key;
	}

	@Column(name = "sequence_sub_name",type=ColumnType.VARCHAR)
	public String getSequenceSubName(){
		return this._sequence_sub_name; 
	}

	public void setSequenceSubName(String _sequence_sub_name){
		this._sequence_sub_name = _sequence_sub_name;
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
	public static SourceDimSequenceSubEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimSequenceSubEntityAuxiliary();
	}

	public static class SourceDimSequenceSubEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimSequenceSubEntityAuxiliary asSequenceSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_id","`sequence_sub_id`", colName, "setSequenceSubId", "getString", cols);
			return this;
		}
		public SourceDimSequenceSubEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimSequenceSubEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceDimSequenceSubEntityAuxiliary asSequenceSubKey(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_key","`sequence_sub_key`", colName, "setSequenceSubKey", "getString", cols);
			return this;
		}
		public SourceDimSequenceSubEntityAuxiliary asSequenceSubName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_name","`sequence_sub_name`", colName, "setSequenceSubName", "getString", cols);
			return this;
		}
		public SourceDimSequenceSubEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
		public SourceDimSequenceSubEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
