package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_target_benefit_value")
public class SourceTargetBenefitValueEntity implements Entity{

	private String _target_benefit_value_id;
	private String _customer_id;
	private String _organization_id;
	private Double _target_value;
	private int _year;

	public SourceTargetBenefitValueEntity(){
		super();
	}

	public SourceTargetBenefitValueEntity(String _target_benefit_value_id,String _customer_id,String _organization_id,Double _target_value,int _year){
		this._target_benefit_value_id = _target_benefit_value_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._target_value = _target_value;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceTargetBenefitValueEntity ["+
			"	target_benefit_value_id="+_target_benefit_value_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	target_value="+_target_value+
			"	year="+_year+
			"]";
	}

	@Column(name = "target_benefit_value_id",type=ColumnType.VARCHAR)
	public String getTargetBenefitValueId(){
		return this._target_benefit_value_id; 
	}

	public void setTargetBenefitValueId(String _target_benefit_value_id){
		this._target_benefit_value_id = _target_benefit_value_id;
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

	@Column(name = "target_value",type=ColumnType.DOUBLE)
	public Double getTargetValue(){
		return this._target_value; 
	}

	public void setTargetValue(Double _target_value){
		this._target_value = _target_value;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceTargetBenefitValueEntityAuxiliary  getEntityAuxiliary(){
		return new SourceTargetBenefitValueEntityAuxiliary();
	}

	public static class SourceTargetBenefitValueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceTargetBenefitValueEntityAuxiliary asTargetBenefitValueId(String colName, CustomColumn<?, ?>... cols){
			setColName("target_benefit_value_id","`target_benefit_value_id`", colName, "setTargetBenefitValueId", "getString", cols);
			return this;
		}
		public SourceTargetBenefitValueEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceTargetBenefitValueEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceTargetBenefitValueEntityAuxiliary asTargetValue(String colName, CustomColumn<?, ?>... cols){
			setColName("target_value","`target_value`", colName, "setTargetValue", "getDouble", cols);
			return this;
		}
		public SourceTargetBenefitValueEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
