package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_organization_type")
public class SourceDimOrganizationTypeEntity implements Entity{

	private String _organization_type_id;
	private String _customer_id;
	private String _organization_type_key;
	private int _organization_type_level;
	private String _organization_type_name;
	private int _show_index;

	public SourceDimOrganizationTypeEntity(){
		super();
	}

	public SourceDimOrganizationTypeEntity(String _organization_type_id,String _customer_id,String _organization_type_key,int _organization_type_level,String _organization_type_name,int _show_index){
		this._organization_type_id = _organization_type_id;
		this._customer_id = _customer_id;
		this._organization_type_key = _organization_type_key;
		this._organization_type_level = _organization_type_level;
		this._organization_type_name = _organization_type_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimOrganizationTypeEntity ["+
			"	organization_type_id="+_organization_type_id+
			"	customer_id="+_customer_id+
			"	organization_type_key="+_organization_type_key+
			"	organization_type_level="+_organization_type_level+
			"	organization_type_name="+_organization_type_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "organization_type_id",type=ColumnType.VARCHAR)
	public String getOrganizationTypeId(){
		return this._organization_type_id; 
	}

	public void setOrganizationTypeId(String _organization_type_id){
		this._organization_type_id = _organization_type_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_type_key",type=ColumnType.VARCHAR)
	public String getOrganizationTypeKey(){
		return this._organization_type_key; 
	}

	public void setOrganizationTypeKey(String _organization_type_key){
		this._organization_type_key = _organization_type_key;
	}

	@Column(name = "organization_type_level",type=ColumnType.INT)
	public int getOrganizationTypeLevel(){
		return this._organization_type_level; 
	}

	public void setOrganizationTypeLevel(int _organization_type_level){
		this._organization_type_level = _organization_type_level;
	}

	@Column(name = "organization_type_name",type=ColumnType.VARCHAR)
	public String getOrganizationTypeName(){
		return this._organization_type_name; 
	}

	public void setOrganizationTypeName(String _organization_type_name){
		this._organization_type_name = _organization_type_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimOrganizationTypeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimOrganizationTypeEntityAuxiliary();
	}

	public static class SourceDimOrganizationTypeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimOrganizationTypeEntityAuxiliary asOrganizationTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_type_id","`organization_type_id`", colName, "setOrganizationTypeId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationTypeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationTypeEntityAuxiliary asOrganizationTypeKey(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_type_key","`organization_type_key`", colName, "setOrganizationTypeKey", "getString", cols);
			return this;
		}
		public SourceDimOrganizationTypeEntityAuxiliary asOrganizationTypeLevel(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_type_level","`organization_type_level`", colName, "setOrganizationTypeLevel", "getInt", cols);
			return this;
		}
		public SourceDimOrganizationTypeEntityAuxiliary asOrganizationTypeName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_type_name","`organization_type_name`", colName, "setOrganizationTypeName", "getString", cols);
			return this;
		}
		public SourceDimOrganizationTypeEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
