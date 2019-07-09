package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_structure")
public class SourceDimStructureEntity implements Entity{

	private String _structure_id;
	private String _customer_id;
	private String _structure_name;
	private int _is_fixed;
	private int _show_index;
	private String _refresh;

	public SourceDimStructureEntity(){
		super();
	}

	public SourceDimStructureEntity(String _structure_id,String _customer_id,String _structure_name,int _is_fixed,int _show_index,String _refresh){
		this._structure_id = _structure_id;
		this._customer_id = _customer_id;
		this._structure_name = _structure_name;
		this._is_fixed = _is_fixed;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimStructureEntity ["+
			"	structure_id="+_structure_id+
			"	customer_id="+_customer_id+
			"	structure_name="+_structure_name+
			"	is_fixed="+_is_fixed+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "structure_id",type=ColumnType.VARCHAR)
	public String getStructureId(){
		return this._structure_id; 
	}

	public void setStructureId(String _structure_id){
		this._structure_id = _structure_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "structure_name",type=ColumnType.VARCHAR)
	public String getStructureName(){
		return this._structure_name; 
	}

	public void setStructureName(String _structure_name){
		this._structure_name = _structure_name;
	}

	@Column(name = "is_fixed",type=ColumnType.TINYINT)
	public int getIsFixed(){
		return this._is_fixed; 
	}

	public void setIsFixed(int _is_fixed){
		this._is_fixed = _is_fixed;
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
	public static SourceDimStructureEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimStructureEntityAuxiliary();
	}

	public static class SourceDimStructureEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimStructureEntityAuxiliary asStructureId(String colName, CustomColumn<?, ?>... cols){
			setColName("structure_id","`structure_id`", colName, "setStructureId", "getString", cols);
			return this;
		}
		public SourceDimStructureEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimStructureEntityAuxiliary asStructureName(String colName, CustomColumn<?, ?>... cols){
			setColName("structure_name","`structure_name`", colName, "setStructureName", "getString", cols);
			return this;
		}
		public SourceDimStructureEntityAuxiliary asIsFixed(String colName, CustomColumn<?, ?>... cols){
			setColName("is_fixed","`is_fixed`", colName, "setIsFixed", "getInt", cols);
			return this;
		}
		public SourceDimStructureEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimStructureEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
