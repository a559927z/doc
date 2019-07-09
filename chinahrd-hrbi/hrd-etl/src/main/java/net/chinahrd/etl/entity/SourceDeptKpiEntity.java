package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dept_kpi")
public class SourceDeptKpiEntity implements Entity{

	private String _dept_per_exam_relation_id;
	private String _customer_id;
	private String _organization_id;
	private Double _kpi_value;
	private int _year;
	private String _refresh;

	public SourceDeptKpiEntity(){
		super();
	}

	public SourceDeptKpiEntity(String _dept_per_exam_relation_id,String _customer_id,String _organization_id,Double _kpi_value,int _year,String _refresh){
		this._dept_per_exam_relation_id = _dept_per_exam_relation_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._kpi_value = _kpi_value;
		this._year = _year;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDeptKpiEntity ["+
			"	dept_per_exam_relation_id="+_dept_per_exam_relation_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	kpi_value="+_kpi_value+
			"	year="+_year+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "dept_per_exam_relation_id",type=ColumnType.VARCHAR)
	public String getDeptPerExamRelationId(){
		return this._dept_per_exam_relation_id; 
	}

	public void setDeptPerExamRelationId(String _dept_per_exam_relation_id){
		this._dept_per_exam_relation_id = _dept_per_exam_relation_id;
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

	@Column(name = "kpi_value",type=ColumnType.DOUBLE)
	public Double getKpiValue(){
		return this._kpi_value; 
	}

	public void setKpiValue(Double _kpi_value){
		this._kpi_value = _kpi_value;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceDeptKpiEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDeptKpiEntityAuxiliary();
	}

	public static class SourceDeptKpiEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDeptKpiEntityAuxiliary asDeptPerExamRelationId(String colName, CustomColumn<?, ?>... cols){
			setColName("dept_per_exam_relation_id","`dept_per_exam_relation_id`", colName, "setDeptPerExamRelationId", "getString", cols);
			return this;
		}
		public SourceDeptKpiEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDeptKpiEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceDeptKpiEntityAuxiliary asKpiValue(String colName, CustomColumn<?, ?>... cols){
			setColName("kpi_value","`kpi_value`", colName, "setKpiValue", "getDouble", cols);
			return this;
		}
		public SourceDeptKpiEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceDeptKpiEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
