package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_performance")
public class SourceDimPerformanceEntity implements Entity{

	private String _performance_id;
	private String _customer_id;
	private String _performance_key;
	private String _performance_name;
	private int _curt_name;
	private int _show_index;

	public SourceDimPerformanceEntity(){
		super();
	}

	public SourceDimPerformanceEntity(String _performance_id,String _customer_id,String _performance_key,String _performance_name,int _curt_name,int _show_index){
		this._performance_id = _performance_id;
		this._customer_id = _customer_id;
		this._performance_key = _performance_key;
		this._performance_name = _performance_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimPerformanceEntity ["+
			"	performance_id="+_performance_id+
			"	customer_id="+_customer_id+
			"	performance_key="+_performance_key+
			"	performance_name="+_performance_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "performance_id",type=ColumnType.VARCHAR)
	public String getPerformanceId(){
		return this._performance_id; 
	}

	public void setPerformanceId(String _performance_id){
		this._performance_id = _performance_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "performance_key",type=ColumnType.VARCHAR)
	public String getPerformanceKey(){
		return this._performance_key; 
	}

	public void setPerformanceKey(String _performance_key){
		this._performance_key = _performance_key;
	}

	@Column(name = "performance_name",type=ColumnType.VARCHAR)
	public String getPerformanceName(){
		return this._performance_name; 
	}

	public void setPerformanceName(String _performance_name){
		this._performance_name = _performance_name;
	}

	@Column(name = "curt_name",type=ColumnType.TINYINT)
	public int getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(int _curt_name){
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
	public static SourceDimPerformanceEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimPerformanceEntityAuxiliary();
	}

	public static class SourceDimPerformanceEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimPerformanceEntityAuxiliary asPerformanceId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_id","`performance_id`", colName, "setPerformanceId", "getString", cols);
			return this;
		}
		public SourceDimPerformanceEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimPerformanceEntityAuxiliary asPerformanceKey(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_key","`performance_key`", colName, "setPerformanceKey", "getString", cols);
			return this;
		}
		public SourceDimPerformanceEntityAuxiliary asPerformanceName(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_name","`performance_name`", colName, "setPerformanceName", "getString", cols);
			return this;
		}
		public SourceDimPerformanceEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getInt", cols);
			return this;
		}
		public SourceDimPerformanceEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
