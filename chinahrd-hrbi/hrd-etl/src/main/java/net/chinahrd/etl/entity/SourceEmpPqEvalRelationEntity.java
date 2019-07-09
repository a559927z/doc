package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_pq_eval_relation")
public class SourceEmpPqEvalRelationEntity implements Entity{

	private String _emp_id;
	private String _customer_id;
	private String _examination_result_id;
	private String _examination_result;
	private String _date;
	private String _refresh;
	private int _curt_name;

	public SourceEmpPqEvalRelationEntity(){
		super();
	}

	public SourceEmpPqEvalRelationEntity(String _emp_id,String _customer_id,String _examination_result_id,String _examination_result,String _date,String _refresh,int _curt_name){
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._examination_result_id = _examination_result_id;
		this._examination_result = _examination_result;
		this._date = _date;
		this._refresh = _refresh;
		this._curt_name = _curt_name;
	}

	@Override
	public String toString() {
		return "SourceEmpPqEvalRelationEntity ["+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	examination_result_id="+_examination_result_id+
			"	examination_result="+_examination_result+
			"	date="+_date+
			"	refresh="+_refresh+
			"	curt_name="+_curt_name+
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

	@Column(name = "examination_result_id",type=ColumnType.VARCHAR)
	public String getExaminationResultId(){
		return this._examination_result_id; 
	}

	public void setExaminationResultId(String _examination_result_id){
		this._examination_result_id = _examination_result_id;
	}

	@Column(name = "examination_result",type=ColumnType.VARCHAR)
	public String getExaminationResult(){
		return this._examination_result; 
	}

	public void setExaminationResult(String _examination_result){
		this._examination_result = _examination_result;
	}

	@Column(name = "date",type=ColumnType.DATE)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	@Column(name = "curt_name",type=ColumnType.INT)
	public int getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(int _curt_name){
		this._curt_name = _curt_name;
	}

	// 实例化内部类
	public static SourceEmpPqEvalRelationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpPqEvalRelationEntityAuxiliary();
	}

	public static class SourceEmpPqEvalRelationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpPqEvalRelationEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpPqEvalRelationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpPqEvalRelationEntityAuxiliary asExaminationResultId(String colName, CustomColumn<?, ?>... cols){
			setColName("examination_result_id","`examination_result_id`", colName, "setExaminationResultId", "getString", cols);
			return this;
		}
		public SourceEmpPqEvalRelationEntityAuxiliary asExaminationResult(String colName, CustomColumn<?, ?>... cols){
			setColName("examination_result","`examination_result`", colName, "setExaminationResult", "getString", cols);
			return this;
		}
		public SourceEmpPqEvalRelationEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceEmpPqEvalRelationEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceEmpPqEvalRelationEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getInt", cols);
			return this;
		}
	}
}
