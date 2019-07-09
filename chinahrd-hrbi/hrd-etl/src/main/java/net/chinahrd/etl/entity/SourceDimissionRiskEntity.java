package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dimission_risk")
public class SourceDimissionRiskEntity implements Entity{

	private String _dimission_risk_id;
	private String _customer_id;
	private String _emp_id;
	private String _note;
	private String _risk_date;
	private int _risk_flag;
	private int _is_last;

	public SourceDimissionRiskEntity(){
		super();
	}

	public SourceDimissionRiskEntity(String _dimission_risk_id,String _customer_id,String _emp_id,String _note,String _risk_date,int _risk_flag,int _is_last){
		this._dimission_risk_id = _dimission_risk_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._note = _note;
		this._risk_date = _risk_date;
		this._risk_flag = _risk_flag;
		this._is_last = _is_last;
	}

	@Override
	public String toString() {
		return "SourceDimissionRiskEntity ["+
			"	dimission_risk_id="+_dimission_risk_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	note="+_note+
			"	risk_date="+_risk_date+
			"	risk_flag="+_risk_flag+
			"	is_last="+_is_last+
			"]";
	}

	@Column(name = "dimission_risk_id",type=ColumnType.VARCHAR)
	public String getDimissionRiskId(){
		return this._dimission_risk_id; 
	}

	public void setDimissionRiskId(String _dimission_risk_id){
		this._dimission_risk_id = _dimission_risk_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "note",type=ColumnType.TEXT)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "risk_date",type=ColumnType.DATETIME)
	public String getRiskDate(){
		return this._risk_date; 
	}

	public void setRiskDate(String _risk_date){
		this._risk_date = _risk_date;
	}

	@Column(name = "risk_flag",type=ColumnType.INT)
	public int getRiskFlag(){
		return this._risk_flag; 
	}

	public void setRiskFlag(int _risk_flag){
		this._risk_flag = _risk_flag;
	}

	@Column(name = "is_last",type=ColumnType.INT)
	public int getIsLast(){
		return this._is_last; 
	}

	public void setIsLast(int _is_last){
		this._is_last = _is_last;
	}

	// 实例化内部类
	public static SourceDimissionRiskEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimissionRiskEntityAuxiliary();
	}

	public static class SourceDimissionRiskEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimissionRiskEntityAuxiliary asDimissionRiskId(String colName, CustomColumn<?, ?>... cols){
			setColName("dimission_risk_id","`dimission_risk_id`", colName, "setDimissionRiskId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceDimissionRiskEntityAuxiliary asRiskDate(String colName, CustomColumn<?, ?>... cols){
			setColName("risk_date","`risk_date`", colName, "setRiskDate", "getString", cols);
			return this;
		}
		public SourceDimissionRiskEntityAuxiliary asRiskFlag(String colName, CustomColumn<?, ?>... cols){
			setColName("risk_flag","`risk_flag`", colName, "setRiskFlag", "getInt", cols);
			return this;
		}
		public SourceDimissionRiskEntityAuxiliary asIsLast(String colName, CustomColumn<?, ?>... cols){
			setColName("is_last","`is_last`", colName, "setIsLast", "getInt", cols);
			return this;
		}
	}
}
