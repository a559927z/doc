package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_population")
public class SourceDimPopulationEntity implements Entity{

	private String _population_id;
	private String _customer_id;
	private String _population_key;
	private String _population_name;
	private int _show_index;
	private String _refresh;

	public SourceDimPopulationEntity(){
		super();
	}

	public SourceDimPopulationEntity(String _population_id,String _customer_id,String _population_key,String _population_name,int _show_index,String _refresh){
		this._population_id = _population_id;
		this._customer_id = _customer_id;
		this._population_key = _population_key;
		this._population_name = _population_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimPopulationEntity ["+
			"	population_id="+_population_id+
			"	customer_id="+_customer_id+
			"	population_key="+_population_key+
			"	population_name="+_population_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "population_id",type=ColumnType.VARCHAR)
	public String getPopulationId(){
		return this._population_id; 
	}

	public void setPopulationId(String _population_id){
		this._population_id = _population_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "population_key",type=ColumnType.VARCHAR)
	public String getPopulationKey(){
		return this._population_key; 
	}

	public void setPopulationKey(String _population_key){
		this._population_key = _population_key;
	}

	@Column(name = "population_name",type=ColumnType.VARCHAR)
	public String getPopulationName(){
		return this._population_name; 
	}

	public void setPopulationName(String _population_name){
		this._population_name = _population_name;
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
	public static SourceDimPopulationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimPopulationEntityAuxiliary();
	}

	public static class SourceDimPopulationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimPopulationEntityAuxiliary asPopulationId(String colName, CustomColumn<?, ?>... cols){
			setColName("population_id","`population_id`", colName, "setPopulationId", "getString", cols);
			return this;
		}
		public SourceDimPopulationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimPopulationEntityAuxiliary asPopulationKey(String colName, CustomColumn<?, ?>... cols){
			setColName("population_key","`population_key`", colName, "setPopulationKey", "getString", cols);
			return this;
		}
		public SourceDimPopulationEntityAuxiliary asPopulationName(String colName, CustomColumn<?, ?>... cols){
			setColName("population_name","`population_name`", colName, "setPopulationName", "getString", cols);
			return this;
		}
		public SourceDimPopulationEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimPopulationEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
