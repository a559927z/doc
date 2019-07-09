package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_ability_number")
public class SourceDimAbilityNumberEntity implements Entity{

	private String _ability_number_id;
	private String _ability_number_key;
	private String _ability_number_name;
	private String _customer_id;
	private int _show_index;
	private String _create_date;

	public SourceDimAbilityNumberEntity(){
		super();
	}

	public SourceDimAbilityNumberEntity(String _ability_number_id,String _ability_number_key,String _ability_number_name,String _customer_id,int _show_index,String _create_date){
		this._ability_number_id = _ability_number_id;
		this._ability_number_key = _ability_number_key;
		this._ability_number_name = _ability_number_name;
		this._customer_id = _customer_id;
		this._show_index = _show_index;
		this._create_date = _create_date;
	}

	@Override
	public String toString() {
		return "SourceDimAbilityNumberEntity ["+
			"	ability_number_id="+_ability_number_id+
			"	ability_number_key="+_ability_number_key+
			"	ability_number_name="+_ability_number_name+
			"	customer_id="+_customer_id+
			"	show_index="+_show_index+
			"	create_date="+_create_date+
			"]";
	}

	@Column(name = "ability_number_id",type=ColumnType.VARCHAR)
	public String getAbilityNumberId(){
		return this._ability_number_id; 
	}

	public void setAbilityNumberId(String _ability_number_id){
		this._ability_number_id = _ability_number_id;
	}

	@Column(name = "ability_number_key",type=ColumnType.VARCHAR)
	public String getAbilityNumberKey(){
		return this._ability_number_key; 
	}

	public void setAbilityNumberKey(String _ability_number_key){
		this._ability_number_key = _ability_number_key;
	}

	@Column(name = "ability_number_name",type=ColumnType.VARCHAR)
	public String getAbilityNumberName(){
		return this._ability_number_name; 
	}

	public void setAbilityNumberName(String _ability_number_name){
		this._ability_number_name = _ability_number_name;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "create_date",type=ColumnType.DATE)
	public String getCreateDate(){
		return this._create_date; 
	}

	public void setCreateDate(String _create_date){
		this._create_date = _create_date;
	}

	// 实例化内部类
	public static SourceDimAbilityNumberEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimAbilityNumberEntityAuxiliary();
	}

	public static class SourceDimAbilityNumberEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimAbilityNumberEntityAuxiliary asAbilityNumberId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_number_id","`ability_number_id`", colName, "setAbilityNumberId", "getString", cols);
			return this;
		}
		public SourceDimAbilityNumberEntityAuxiliary asAbilityNumberKey(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_number_key","`ability_number_key`", colName, "setAbilityNumberKey", "getString", cols);
			return this;
		}
		public SourceDimAbilityNumberEntityAuxiliary asAbilityNumberName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_number_name","`ability_number_name`", colName, "setAbilityNumberName", "getString", cols);
			return this;
		}
		public SourceDimAbilityNumberEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimAbilityNumberEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimAbilityNumberEntityAuxiliary asCreateDate(String colName, CustomColumn<?, ?>... cols){
			setColName("create_date","`create_date`", colName, "setCreateDate", "getString", cols);
			return this;
		}
	}
}
