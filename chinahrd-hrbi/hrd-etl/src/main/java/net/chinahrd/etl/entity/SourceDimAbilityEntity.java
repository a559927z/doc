package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_ability")
public class SourceDimAbilityEntity implements Entity{

	private String _ability_id;
	private String _customer_id;
	private String _ability_key;
	private String _ability_name;
	private String _curt_name;
	private int _type;
	private int _show_index;

	public SourceDimAbilityEntity(){
		super();
	}

	public SourceDimAbilityEntity(String _ability_id,String _customer_id,String _ability_key,String _ability_name,String _curt_name,int _type,int _show_index){
		this._ability_id = _ability_id;
		this._customer_id = _customer_id;
		this._ability_key = _ability_key;
		this._ability_name = _ability_name;
		this._curt_name = _curt_name;
		this._type = _type;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimAbilityEntity ["+
			"	ability_id="+_ability_id+
			"	customer_id="+_customer_id+
			"	ability_key="+_ability_key+
			"	ability_name="+_ability_name+
			"	curt_name="+_curt_name+
			"	type="+_type+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "ability_id",type=ColumnType.VARCHAR)
	public String getAbilityId(){
		return this._ability_id; 
	}

	public void setAbilityId(String _ability_id){
		this._ability_id = _ability_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "ability_key",type=ColumnType.VARCHAR)
	public String getAbilityKey(){
		return this._ability_key; 
	}

	public void setAbilityKey(String _ability_key){
		this._ability_key = _ability_key;
	}

	@Column(name = "ability_name",type=ColumnType.VARCHAR)
	public String getAbilityName(){
		return this._ability_name; 
	}

	public void setAbilityName(String _ability_name){
		this._ability_name = _ability_name;
	}

	@Column(name = "curt_name",type=ColumnType.VARCHAR)
	public String getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(String _curt_name){
		this._curt_name = _curt_name;
	}

	@Column(name = "type",type=ColumnType.TINYINT)
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
	public static SourceDimAbilityEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimAbilityEntityAuxiliary();
	}

	public static class SourceDimAbilityEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimAbilityEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceDimAbilityEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimAbilityEntityAuxiliary asAbilityKey(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_key","`ability_key`", colName, "setAbilityKey", "getString", cols);
			return this;
		}
		public SourceDimAbilityEntityAuxiliary asAbilityName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_name","`ability_name`", colName, "setAbilityName", "getString", cols);
			return this;
		}
		public SourceDimAbilityEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
		public SourceDimAbilityEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceDimAbilityEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
