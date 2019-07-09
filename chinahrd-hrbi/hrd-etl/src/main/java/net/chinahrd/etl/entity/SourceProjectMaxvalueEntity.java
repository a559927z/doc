package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_project_maxvalue")
public class SourceProjectMaxvalueEntity implements Entity{

	private String _project_maxvalue_id;
	private String _customer_id;
	private String _organization_id;
	private int _maxval;
	private Double _total_work_hours;
	private String _refresh;

	public SourceProjectMaxvalueEntity(){
		super();
	}

	public SourceProjectMaxvalueEntity(String _project_maxvalue_id,String _customer_id,String _organization_id,int _maxval,Double _total_work_hours,String _refresh){
		this._project_maxvalue_id = _project_maxvalue_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._maxval = _maxval;
		this._total_work_hours = _total_work_hours;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceProjectMaxvalueEntity ["+
			"	project_maxvalue_id="+_project_maxvalue_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	maxval="+_maxval+
			"	total_work_hours="+_total_work_hours+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "project_maxvalue_id",type=ColumnType.VARCHAR)
	public String getProjectMaxvalueId(){
		return this._project_maxvalue_id; 
	}

	public void setProjectMaxvalueId(String _project_maxvalue_id){
		this._project_maxvalue_id = _project_maxvalue_id;
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

	@Column(name = "maxval",type=ColumnType.INT)
	public int getMaxval(){
		return this._maxval; 
	}

	public void setMaxval(int _maxval){
		this._maxval = _maxval;
	}

	@Column(name = "total_work_hours",type=ColumnType.DOUBLE)
	public Double getTotalWorkHours(){
		return this._total_work_hours; 
	}

	public void setTotalWorkHours(Double _total_work_hours){
		this._total_work_hours = _total_work_hours;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceProjectMaxvalueEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProjectMaxvalueEntityAuxiliary();
	}

	public static class SourceProjectMaxvalueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProjectMaxvalueEntityAuxiliary asProjectMaxvalueId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_maxvalue_id","`project_maxvalue_id`", colName, "setProjectMaxvalueId", "getString", cols);
			return this;
		}
		public SourceProjectMaxvalueEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceProjectMaxvalueEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceProjectMaxvalueEntityAuxiliary asMaxval(String colName, CustomColumn<?, ?>... cols){
			setColName("maxval","`maxval`", colName, "setMaxval", "getInt", cols);
			return this;
		}
		public SourceProjectMaxvalueEntityAuxiliary asTotalWorkHours(String colName, CustomColumn<?, ?>... cols){
			setColName("total_work_hours","`total_work_hours`", colName, "setTotalWorkHours", "getDouble", cols);
			return this;
		}
		public SourceProjectMaxvalueEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
