package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_sales_team")
public class SourceDimSalesTeamEntity implements Entity{

	private String _team_id;
	private String _customer_id;
	private String _team_name;
	private String _emp_id;
	private String _emp_names;
	private String _organization_id;
	private int _show_index;
	private String _refresh;

	public SourceDimSalesTeamEntity(){
		super();
	}

	public SourceDimSalesTeamEntity(String _team_id,String _customer_id,String _team_name,String _emp_id,String _emp_names,String _organization_id,int _show_index,String _refresh){
		this._team_id = _team_id;
		this._customer_id = _customer_id;
		this._team_name = _team_name;
		this._emp_id = _emp_id;
		this._emp_names = _emp_names;
		this._organization_id = _organization_id;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimSalesTeamEntity ["+
			"	team_id="+_team_id+
			"	customer_id="+_customer_id+
			"	team_name="+_team_name+
			"	emp_id="+_emp_id+
			"	emp_names="+_emp_names+
			"	organization_id="+_organization_id+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "team_id",type=ColumnType.VARCHAR)
	public String getTeamId(){
		return this._team_id; 
	}

	public void setTeamId(String _team_id){
		this._team_id = _team_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "team_name",type=ColumnType.VARCHAR)
	public String getTeamName(){
		return this._team_name; 
	}

	public void setTeamName(String _team_name){
		this._team_name = _team_name;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "emp_names",type=ColumnType.VARCHAR)
	public String getEmpNames(){
		return this._emp_names; 
	}

	public void setEmpNames(String _emp_names){
		this._emp_names = _emp_names;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
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
	public static SourceDimSalesTeamEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimSalesTeamEntityAuxiliary();
	}

	public static class SourceDimSalesTeamEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimSalesTeamEntityAuxiliary asTeamId(String colName, CustomColumn<?, ?>... cols){
			setColName("team_id","`team_id`", colName, "setTeamId", "getString", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asTeamName(String colName, CustomColumn<?, ?>... cols){
			setColName("team_name","`team_name`", colName, "setTeamName", "getString", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asEmpNames(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_names","`emp_names`", colName, "setEmpNames", "getString", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimSalesTeamEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
