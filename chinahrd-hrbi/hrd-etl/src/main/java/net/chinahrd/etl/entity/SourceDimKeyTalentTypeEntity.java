package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_key_talent_type")
public class SourceDimKeyTalentTypeEntity implements Entity{

	private String _key_talent_type_id;
	private String _customer_id;
	private String _key_talent_type_key;
	private String _key_talent_type_name;
	private int _show_index;

	public SourceDimKeyTalentTypeEntity(){
		super();
	}

	public SourceDimKeyTalentTypeEntity(String _key_talent_type_id,String _customer_id,String _key_talent_type_key,String _key_talent_type_name,int _show_index){
		this._key_talent_type_id = _key_talent_type_id;
		this._customer_id = _customer_id;
		this._key_talent_type_key = _key_talent_type_key;
		this._key_talent_type_name = _key_talent_type_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimKeyTalentTypeEntity ["+
			"	key_talent_type_id="+_key_talent_type_id+
			"	customer_id="+_customer_id+
			"	key_talent_type_key="+_key_talent_type_key+
			"	key_talent_type_name="+_key_talent_type_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "key_talent_type_id",type=ColumnType.VARCHAR)
	public String getKeyTalentTypeId(){
		return this._key_talent_type_id; 
	}

	public void setKeyTalentTypeId(String _key_talent_type_id){
		this._key_talent_type_id = _key_talent_type_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "key_talent_type_key",type=ColumnType.VARCHAR)
	public String getKeyTalentTypeKey(){
		return this._key_talent_type_key; 
	}

	public void setKeyTalentTypeKey(String _key_talent_type_key){
		this._key_talent_type_key = _key_talent_type_key;
	}

	@Column(name = "key_talent_type_name",type=ColumnType.VARCHAR)
	public String getKeyTalentTypeName(){
		return this._key_talent_type_name; 
	}

	public void setKeyTalentTypeName(String _key_talent_type_name){
		this._key_talent_type_name = _key_talent_type_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimKeyTalentTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimKeyTalentTypeEntityAuxiliary();
	}

	public static class SourceDimKeyTalentTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimKeyTalentTypeEntityAuxiliary asKeyTalentTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("key_talent_type_id","`key_talent_type_id`", colName, "setKeyTalentTypeId", "getString", cols);
			return this;
		}
		public SourceDimKeyTalentTypeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimKeyTalentTypeEntityAuxiliary asKeyTalentTypeKey(String colName, CustomColumn<?, ?>... cols){
			setColName("key_talent_type_key","`key_talent_type_key`", colName, "setKeyTalentTypeKey", "getString", cols);
			return this;
		}
		public SourceDimKeyTalentTypeEntityAuxiliary asKeyTalentTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("key_talent_type_name","`key_talent_type_name`", colName, "setKeyTalentTypeName", "getString", cols);
			return this;
		}
		public SourceDimKeyTalentTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
