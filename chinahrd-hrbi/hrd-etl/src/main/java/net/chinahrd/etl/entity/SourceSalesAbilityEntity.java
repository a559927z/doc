package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_ability")
public class SourceSalesAbilityEntity implements Entity{

	private String _emp_id;
	private String _customer_id;
	private int _status;
	private String _check_date;

	public SourceSalesAbilityEntity(){
		super();
	}

	public SourceSalesAbilityEntity(String _emp_id,String _customer_id,int _status,String _check_date){
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._status = _status;
		this._check_date = _check_date;
	}

	@Override
	public String toString() {
		return "SourceSalesAbilityEntity ["+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	status="+_status+
			"	check_date="+_check_date+
			"]";
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "status",type=ColumnType.INT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	@Column(name = "check_date",type=ColumnType.DATE)
	public String getCheckDate(){
		return this._check_date; 
	}

	public void setCheckDate(String _check_date){
		this._check_date = _check_date;
	}

	// 实例化内部类
	public static SourceSalesAbilityEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesAbilityEntityAuxiliary();
	}

	public static class SourceSalesAbilityEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesAbilityEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceSalesAbilityEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesAbilityEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
		public SourceSalesAbilityEntityAuxiliary asCheckDate(String colName, CustomColumn<?, ?>... cols){
			setColName("check_date","`check_date`", colName, "setCheckDate", "getString", cols);
			return this;
		}
	}
}
