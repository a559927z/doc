package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_profession_value")
public class SourceProfessionValueEntity implements Entity{

	private String _profession_value;
	private String _profession_name;
	private String _profession_value_key;
	private Double _value;
	private String _profession_id;
	private String _refresh;

	public SourceProfessionValueEntity(){
		super();
	}

	public SourceProfessionValueEntity(String _profession_value,String _profession_name,String _profession_value_key,Double _value,String _profession_id,String _refresh){
		this._profession_value = _profession_value;
		this._profession_name = _profession_name;
		this._profession_value_key = _profession_value_key;
		this._value = _value;
		this._profession_id = _profession_id;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceProfessionValueEntity ["+
			"	profession_value="+_profession_value+
			"	profession_name="+_profession_name+
			"	profession_value_key="+_profession_value_key+
			"	value="+_value+
			"	profession_id="+_profession_id+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "profession_value",type=ColumnType.VARCHAR)
	public String getProfessionValue(){
		return this._profession_value; 
	}

	public void setProfessionValue(String _profession_value){
		this._profession_value = _profession_value;
	}

	@Column(name = "profession_name",type=ColumnType.VARCHAR)
	public String getProfessionName(){
		return this._profession_name; 
	}

	public void setProfessionName(String _profession_name){
		this._profession_name = _profession_name;
	}

	@Column(name = "profession_value_key",type=ColumnType.VARCHAR)
	public String getProfessionValueKey(){
		return this._profession_value_key; 
	}

	public void setProfessionValueKey(String _profession_value_key){
		this._profession_value_key = _profession_value_key;
	}

	@Column(name = "value",type=ColumnType.DOUBLE)
	public Double getValue(){
		return this._value; 
	}

	public void setValue(Double _value){
		this._value = _value;
	}

	@Column(name = "profession_id",type=ColumnType.VARCHAR)
	public String getProfessionId(){
		return this._profession_id; 
	}

	public void setProfessionId(String _profession_id){
		this._profession_id = _profession_id;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceProfessionValueEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProfessionValueEntityAuxiliary();
	}

	public static class SourceProfessionValueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProfessionValueEntityAuxiliary asProfessionValue(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_value","`profession_value`", colName, "setProfessionValue", "getString", cols);
			return this;
		}
		public SourceProfessionValueEntityAuxiliary asProfessionName(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_name","`profession_name`", colName, "setProfessionName", "getString", cols);
			return this;
		}
		public SourceProfessionValueEntityAuxiliary asProfessionValueKey(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_value_key","`profession_value_key`", colName, "setProfessionValueKey", "getString", cols);
			return this;
		}
		public SourceProfessionValueEntityAuxiliary asValue(String colName, CustomColumn<?, ?>... cols){
			setColName("value","`value`", colName, "setValue", "getDouble", cols);
			return this;
		}
		public SourceProfessionValueEntityAuxiliary asProfessionId(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_id","`profession_id`", colName, "setProfessionId", "getString", cols);
			return this;
		}
		public SourceProfessionValueEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
