package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_organization")
public class SourceDimOrganizationEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _organization_company_id;
	private String _organization_type_id;
	private String _organization_id;
	private String _organization_key;
	private String _organization_parent_id;
	private String _organization_parent_key;
	private String _organization_name;
	private String _organization_name_full;
	private int _is_single;
	private String _note;
	private String _profession_id;

	public SourceDimOrganizationEntity(){
		super();
	}

	public SourceDimOrganizationEntity(String _id,String _customer_id,String _organization_company_id,String _organization_type_id,String _organization_id,String _organization_key,String _organization_parent_id,String _organization_parent_key,String _organization_name,String _organization_name_full,int _is_single,String _note,String _profession_id){
		this._id = _id;
		this._customer_id = _customer_id;
		this._organization_company_id = _organization_company_id;
		this._organization_type_id = _organization_type_id;
		this._organization_id = _organization_id;
		this._organization_key = _organization_key;
		this._organization_parent_id = _organization_parent_id;
		this._organization_parent_key = _organization_parent_key;
		this._organization_name = _organization_name;
		this._organization_name_full = _organization_name_full;
		this._is_single = _is_single;
		this._note = _note;
		this._profession_id = _profession_id;
	}

	@Override
	public String toString() {
		return "SourceDimOrganizationEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	organization_company_id="+_organization_company_id+
			"	organization_type_id="+_organization_type_id+
			"	organization_id="+_organization_id+
			"	organization_key="+_organization_key+
			"	organization_parent_id="+_organization_parent_id+
			"	organization_parent_key="+_organization_parent_key+
			"	organization_name="+_organization_name+
			"	organization_name_full="+_organization_name_full+
			"	is_single="+_is_single+
			"	note="+_note+
			"	profession_id="+_profession_id+
			"]";
	}

	@Column(name = "id",type=ColumnType.CHAR)
	public String getId(){
		return this._id; 
	}

	public void setId(String _id){
		this._id = _id;
	}

	@Column(name = "customer_id",type=ColumnType.CHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_company_id",type=ColumnType.CHAR)
	public String getOrganizationCompanyId(){
		return this._organization_company_id; 
	}

	public void setOrganizationCompanyId(String _organization_company_id){
		this._organization_company_id = _organization_company_id;
	}

	@Column(name = "organization_type_id",type=ColumnType.VARCHAR)
	public String getOrganizationTypeId(){
		return this._organization_type_id; 
	}

	public void setOrganizationTypeId(String _organization_type_id){
		this._organization_type_id = _organization_type_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "organization_key",type=ColumnType.VARCHAR)
	public String getOrganizationKey(){
		return this._organization_key; 
	}

	public void setOrganizationKey(String _organization_key){
		this._organization_key = _organization_key;
	}

	@Column(name = "organization_parent_id",type=ColumnType.VARCHAR)
	public String getOrganizationParentId(){
		return this._organization_parent_id; 
	}

	public void setOrganizationParentId(String _organization_parent_id){
		this._organization_parent_id = _organization_parent_id;
	}

	@Column(name = "organization_parent_key",type=ColumnType.VARCHAR)
	public String getOrganizationParentKey(){
		return this._organization_parent_key; 
	}

	public void setOrganizationParentKey(String _organization_parent_key){
		this._organization_parent_key = _organization_parent_key;
	}

	@Column(name = "organization_name",type=ColumnType.VARCHAR)
	public String getOrganizationName(){
		return this._organization_name; 
	}

	public void setOrganizationName(String _organization_name){
		this._organization_name = _organization_name;
	}

	@Column(name = "organization_name_full",type=ColumnType.VARCHAR)
	public String getOrganizationNameFull(){
		return this._organization_name_full; 
	}

	public void setOrganizationNameFull(String _organization_name_full){
		this._organization_name_full = _organization_name_full;
	}

	@Column(name = "is_single",type=ColumnType.TINYINT)
	public int getIsSingle(){
		return this._is_single; 
	}

	public void setIsSingle(int _is_single){
		this._is_single = _is_single;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "profession_id",type=ColumnType.CHAR)
	public String getProfessionId(){
		return this._profession_id; 
	}

	public void setProfessionId(String _profession_id){
		this._profession_id = _profession_id;
	}

	// 实例化内部类
	public static SourceDimOrganizationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimOrganizationEntityAuxiliary();
	}

	public static class SourceDimOrganizationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimOrganizationEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationCompanyId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_company_id","`organization_company_id`", colName, "setOrganizationCompanyId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_type_id","`organization_type_id`", colName, "setOrganizationTypeId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationKey(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_key","`organization_key`", colName, "setOrganizationKey", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_id","`organization_parent_id`", colName, "setOrganizationParentId", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationParentKey(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_key","`organization_parent_key`", colName, "setOrganizationParentKey", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asOrganizationNameFull(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name_full","`organization_name_full`", colName, "setOrganizationNameFull", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asIsSingle(String colName, CustomColumn<?, ?>... cols){
			setColName("is_single","`is_single`", colName, "setIsSingle", "getInt", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceDimOrganizationEntityAuxiliary asProfessionId(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_id","`profession_id`", colName, "setProfessionId", "getString", cols);
			return this;
		}
	}
}
