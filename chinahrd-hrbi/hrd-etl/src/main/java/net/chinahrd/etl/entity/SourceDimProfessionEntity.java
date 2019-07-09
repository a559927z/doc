package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_profession")
public class SourceDimProfessionEntity implements Entity{

	private String _profession_id;
	private String _profession_name;
	private String _profession_key;
	private int _show_index;
	private String _refresh;

	public SourceDimProfessionEntity(){
		super();
	}

	public SourceDimProfessionEntity(String _profession_id,String _profession_name,String _profession_key,int _show_index,String _refresh){
		this._profession_id = _profession_id;
		this._profession_name = _profession_name;
		this._profession_key = _profession_key;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimProfessionEntity ["+
			"	profession_id="+_profession_id+
			"	profession_name="+_profession_name+
			"	profession_key="+_profession_key+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "profession_id",type=ColumnType.VARCHAR)
	public String getProfessionId(){
		return this._profession_id; 
	}

	public void setProfessionId(String _profession_id){
		this._profession_id = _profession_id;
	}

	@Column(name = "profession_name",type=ColumnType.VARCHAR)
	public String getProfessionName(){
		return this._profession_name; 
	}

	public void setProfessionName(String _profession_name){
		this._profession_name = _profession_name;
	}

	@Column(name = "profession_key",type=ColumnType.VARCHAR)
	public String getProfessionKey(){
		return this._profession_key; 
	}

	public void setProfessionKey(String _profession_key){
		this._profession_key = _profession_key;
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
	public static SourceDimProfessionEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimProfessionEntityAuxiliary();
	}

	public static class SourceDimProfessionEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimProfessionEntityAuxiliary asProfessionId(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_id","`profession_id`", colName, "setProfessionId", "getString", cols);
			return this;
		}
		public SourceDimProfessionEntityAuxiliary asProfessionName(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_name","`profession_name`", colName, "setProfessionName", "getString", cols);
			return this;
		}
		public SourceDimProfessionEntityAuxiliary asProfessionKey(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_key","`profession_key`", colName, "setProfessionKey", "getString", cols);
			return this;
		}
		public SourceDimProfessionEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimProfessionEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
