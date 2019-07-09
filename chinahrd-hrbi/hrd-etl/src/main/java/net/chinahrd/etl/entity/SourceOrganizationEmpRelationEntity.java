package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_organization_emp_relation")
public class SourceOrganizationEmpRelationEntity implements Entity{

	private String _customer_id;
	private String _organization_id;
	private String _emp_id;
	private String _sys_code_item_id;
	private String _refresh_date;

	public SourceOrganizationEmpRelationEntity(){
		super();
	}

	public SourceOrganizationEmpRelationEntity(String _customer_id,String _organization_id,String _emp_id,String _sys_code_item_id,String _refresh_date){
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._emp_id = _emp_id;
		this._sys_code_item_id = _sys_code_item_id;
		this._refresh_date = _refresh_date;
	}

	@Override
	public String toString() {
		return "SourceOrganizationEmpRelationEntity ["+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	emp_id="+_emp_id+
			"	sys_code_item_id="+_sys_code_item_id+
			"	refresh_date="+_refresh_date+
			"]";
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "emp_id",type=ColumnType.CHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "sys_code_item_id",type=ColumnType.VARCHAR)
	public String getSysCodeItemId(){
		return this._sys_code_item_id; 
	}

	public void setSysCodeItemId(String _sys_code_item_id){
		this._sys_code_item_id = _sys_code_item_id;
	}

	@Column(name = "refresh_date",type=ColumnType.DATETIME)
	public String getRefreshDate(){
		return this._refresh_date; 
	}

	public void setRefreshDate(String _refresh_date){
		this._refresh_date = _refresh_date;
	}

	// 实例化内部类
	public static SourceOrganizationEmpRelationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceOrganizationEmpRelationEntityAuxiliary();
	}

	public static class SourceOrganizationEmpRelationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceOrganizationEmpRelationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceOrganizationEmpRelationEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceOrganizationEmpRelationEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceOrganizationEmpRelationEntityAuxiliary asSysCodeItemId(String colName, CustomColumn<?, ?>... cols){
			setColName("sys_code_item_id","`sys_code_item_id`", colName, "setSysCodeItemId", "getString", cols);
			return this;
		}
		public SourceOrganizationEmpRelationEntityAuxiliary asRefreshDate(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh_date","`refresh_date`", colName, "setRefreshDate", "getString", cols);
			return this;
		}
	}
}
