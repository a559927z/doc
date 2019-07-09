package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_population_relation")
public class SourcePopulationRelationEntity implements Entity{

	private String _customer_id;
	private String _population_id;
	private String _emp_id;
	private String _organization_id;
	private String _days;

	public SourcePopulationRelationEntity(){
		super();
	}

	public SourcePopulationRelationEntity(String _customer_id,String _population_id,String _emp_id,String _organization_id,String _days){
		this._customer_id = _customer_id;
		this._population_id = _population_id;
		this._emp_id = _emp_id;
		this._organization_id = _organization_id;
		this._days = _days;
	}

	@Override
	public String toString() {
		return "SourcePopulationRelationEntity ["+
			"	customer_id="+_customer_id+
			"	population_id="+_population_id+
			"	emp_id="+_emp_id+
			"	organization_id="+_organization_id+
			"	days="+_days+
			"]";
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "population_id",type=ColumnType.VARCHAR)
	public String getPopulationId(){
		return this._population_id; 
	}

	public void setPopulationId(String _population_id){
		this._population_id = _population_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "days",type=ColumnType.DATE)
	public String getDays(){
		return this._days; 
	}

	public void setDays(String _days){
		this._days = _days;
	}

	// 实例化内部类
	public static SourcePopulationRelationEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePopulationRelationEntityAuxiliary();
	}

	public static class SourcePopulationRelationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePopulationRelationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePopulationRelationEntityAuxiliary asPopulationId(String colName, CustomColumn<?, ?>... cols){
			setColName("population_id","`population_id`", colName, "setPopulationId", "getString", cols);
			return this;
		}
		public SourcePopulationRelationEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourcePopulationRelationEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourcePopulationRelationEntityAuxiliary asDays(String colName, CustomColumn<?, ?>... cols){
			setColName("days","`days`", colName, "setDays", "getString", cols);
			return this;
		}
	}
}
