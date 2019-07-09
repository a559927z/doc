package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_train_value")
public class SourceTrainValueEntity implements Entity{

	private String _train_value_id;
	private String _customer_id;
	private String _organization_id;
	private Double _budget_value;
	private int _year;

	public SourceTrainValueEntity(){
		super();
	}

	public SourceTrainValueEntity(String _train_value_id,String _customer_id,String _organization_id,Double _budget_value,int _year){
		this._train_value_id = _train_value_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._budget_value = _budget_value;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceTrainValueEntity ["+
			"	train_value_id="+_train_value_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	budget_value="+_budget_value+
			"	year="+_year+
			"]";
	}

	@Column(name = "train_value_id",type=ColumnType.VARCHAR)
	public String getTrainValueId(){
		return this._train_value_id; 
	}

	public void setTrainValueId(String _train_value_id){
		this._train_value_id = _train_value_id;
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

	@Column(name = "budget_value",type=ColumnType.DOUBLE)
	public Double getBudgetValue(){
		return this._budget_value; 
	}

	public void setBudgetValue(Double _budget_value){
		this._budget_value = _budget_value;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceTrainValueEntityAuxiliary  getEntityAuxiliary(){
		return new SourceTrainValueEntityAuxiliary();
	}

	public static class SourceTrainValueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceTrainValueEntityAuxiliary asTrainValueId(String colName, CustomColumn<?, ?>... cols){
			setColName("train_value_id","`train_value_id`", colName, "setTrainValueId", "getString", cols);
			return this;
		}
		public SourceTrainValueEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceTrainValueEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceTrainValueEntityAuxiliary asBudgetValue(String colName, CustomColumn<?, ?>... cols){
			setColName("budget_value","`budget_value`", colName, "setBudgetValue", "getDouble", cols);
			return this;
		}
		public SourceTrainValueEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
