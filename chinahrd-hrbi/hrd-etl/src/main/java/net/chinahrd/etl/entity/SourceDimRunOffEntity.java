package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_run_off")
public class SourceDimRunOffEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _run_off_key;
	private String _run_off_name;
	private int _type;
	private int _show_index;

	public SourceDimRunOffEntity(){
		super();
	}

	public SourceDimRunOffEntity(String _id,String _customer_id,String _run_off_key,String _run_off_name,int _type,int _show_index){
		this._id = _id;
		this._customer_id = _customer_id;
		this._run_off_key = _run_off_key;
		this._run_off_name = _run_off_name;
		this._type = _type;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimRunOffEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	run_off_key="+_run_off_key+
			"	run_off_name="+_run_off_name+
			"	type="+_type+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "id",type=ColumnType.VARCHAR)
	public String getId(){
		return this._id; 
	}

	public void setId(String _id){
		this._id = _id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "run_off_key",type=ColumnType.VARCHAR)
	public String getRunOffKey(){
		return this._run_off_key; 
	}

	public void setRunOffKey(String _run_off_key){
		this._run_off_key = _run_off_key;
	}

	@Column(name = "run_off_name",type=ColumnType.VARCHAR)
	public String getRunOffName(){
		return this._run_off_name; 
	}

	public void setRunOffName(String _run_off_name){
		this._run_off_name = _run_off_name;
	}

	@Column(name = "type",type=ColumnType.INT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimRunOffEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimRunOffEntityAuxiliary();
	}

	public static class SourceDimRunOffEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimRunOffEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceDimRunOffEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimRunOffEntityAuxiliary asRunOffKey(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_key","`run_off_key`", colName, "setRunOffKey", "getString", cols);
			return this;
		}
		public SourceDimRunOffEntityAuxiliary asRunOffName(String colName, CustomColumn<?, ?>... cols){
			setColName("run_off_name","`run_off_name`", colName, "setRunOffName", "getString", cols);
			return this;
		}
		public SourceDimRunOffEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceDimRunOffEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
