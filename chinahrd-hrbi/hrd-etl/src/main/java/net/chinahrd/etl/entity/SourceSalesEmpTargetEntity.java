package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_emp_target")
public class SourceSalesEmpTargetEntity implements Entity{

	private String _emp_id;
	private Double _sales_target;
	private Double _return_amount;
	private Double _payment;
	private int _year_month;

	public SourceSalesEmpTargetEntity(){
		super();
	}

	public SourceSalesEmpTargetEntity(String _emp_id,Double _sales_target,Double _return_amount,Double _payment,int _year_month){
		this._emp_id = _emp_id;
		this._sales_target = _sales_target;
		this._return_amount = _return_amount;
		this._payment = _payment;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceSalesEmpTargetEntity ["+
			"	emp_id="+_emp_id+
			"	sales_target="+_sales_target+
			"	return_amount="+_return_amount+
			"	payment="+_payment+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "sales_target",type=ColumnType.DOUBLE)
	public Double getSalesTarget(){
		return this._sales_target; 
	}

	public void setSalesTarget(Double _sales_target){
		this._sales_target = _sales_target;
	}

	@Column(name = "return_amount",type=ColumnType.DOUBLE)
	public Double getReturnAmount(){
		return this._return_amount; 
	}

	public void setReturnAmount(Double _return_amount){
		this._return_amount = _return_amount;
	}

	@Column(name = "payment",type=ColumnType.DOUBLE)
	public Double getPayment(){
		return this._payment; 
	}

	public void setPayment(Double _payment){
		this._payment = _payment;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceSalesEmpTargetEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesEmpTargetEntityAuxiliary();
	}

	public static class SourceSalesEmpTargetEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesEmpTargetEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceSalesEmpTargetEntityAuxiliary asSalesTarget(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_target","`sales_target`", colName, "setSalesTarget", "getDouble", cols);
			return this;
		}
		public SourceSalesEmpTargetEntityAuxiliary asReturnAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("return_amount","`return_amount`", colName, "setReturnAmount", "getDouble", cols);
			return this;
		}
		public SourceSalesEmpTargetEntityAuxiliary asPayment(String colName, CustomColumn<?, ?>... cols){
			setColName("payment","`payment`", colName, "setPayment", "getDouble", cols);
			return this;
		}
		public SourceSalesEmpTargetEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
