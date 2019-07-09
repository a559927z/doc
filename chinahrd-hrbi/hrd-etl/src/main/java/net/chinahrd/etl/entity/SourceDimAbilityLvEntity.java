package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_ability_lv")
public class SourceDimAbilityLvEntity implements Entity{

	private String _ability_lv_id;
	private String _customer_id;
	private String _ability_lv_key;
	private String _ability_lv_name;
	private String _curt_name;
	private int _show_index;

	public SourceDimAbilityLvEntity(){
		super();
	}

	public SourceDimAbilityLvEntity(String _ability_lv_id,String _customer_id,String _ability_lv_key,String _ability_lv_name,String _curt_name,int _show_index){
		this._ability_lv_id = _ability_lv_id;
		this._customer_id = _customer_id;
		this._ability_lv_key = _ability_lv_key;
		this._ability_lv_name = _ability_lv_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimAbilityLvEntity ["+
			"	ability_lv_id="+_ability_lv_id+
			"	customer_id="+_customer_id+
			"	ability_lv_key="+_ability_lv_key+
			"	ability_lv_name="+_ability_lv_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "ability_lv_id",type=ColumnType.VARCHAR)
	public String getAbilityLvId(){
		return this._ability_lv_id; 
	}

	public void setAbilityLvId(String _ability_lv_id){
		this._ability_lv_id = _ability_lv_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "ability_lv_key",type=ColumnType.VARCHAR)
	public String getAbilityLvKey(){
		return this._ability_lv_key; 
	}

	public void setAbilityLvKey(String _ability_lv_key){
		this._ability_lv_key = _ability_lv_key;
	}

	@Column(name = "ability_lv_name",type=ColumnType.VARCHAR)
	public String getAbilityLvName(){
		return this._ability_lv_name; 
	}

	public void setAbilityLvName(String _ability_lv_name){
		this._ability_lv_name = _ability_lv_name;
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
	public static SourceDimAbilityLvEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimAbilityLvEntityAuxiliary();
	}

	public static class SourceDimAbilityLvEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimAbilityLvEntityAuxiliary asAbilityLvId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_id","`ability_lv_id`", colName, "setAbilityLvId", "getString", cols);
			return this;
		}
		public SourceDimAbilityLvEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimAbilityLvEntityAuxiliary asAbilityLvKey(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_key","`ability_lv_key`", colName, "setAbilityLvKey", "getString", cols);
			return this;
		}
		public SourceDimAbilityLvEntityAuxiliary asAbilityLvName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_name","`ability_lv_name`", colName, "setAbilityLvName", "getString", cols);
			return this;
		}
		public SourceDimAbilityLvEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
		public SourceDimAbilityLvEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
