package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_project_cost")
public class SourceProjectCostEntity implements Entity{

	private String _project_cost_id;
	private String _customer_id;
	private Double _input;
	private Double _output;
	private Double _gain;
	private String _project_id;
	private String _date;
	private int _type;

	public SourceProjectCostEntity(){
		super();
	}

	public SourceProjectCostEntity(String _project_cost_id,String _customer_id,Double _input,Double _output,Double _gain,String _project_id,String _date,int _type){
		this._project_cost_id = _project_cost_id;
		this._customer_id = _customer_id;
		this._input = _input;
		this._output = _output;
		this._gain = _gain;
		this._project_id = _project_id;
		this._date = _date;
		this._type = _type;
	}

	@Override
	public String toString() {
		return "SourceProjectCostEntity ["+
			"	project_cost_id="+_project_cost_id+
			"	customer_id="+_customer_id+
			"	input="+_input+
			"	output="+_output+
			"	gain="+_gain+
			"	project_id="+_project_id+
			"	date="+_date+
			"	type="+_type+
			"]";
	}

	@Column(name = "project_cost_id",type=ColumnType.VARCHAR)
	public String getProjectCostId(){
		return this._project_cost_id; 
	}

	public void setProjectCostId(String _project_cost_id){
		this._project_cost_id = _project_cost_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "input",type=ColumnType.DOUBLE)
	public Double getInput(){
		return this._input; 
	}

	public void setInput(Double _input){
		this._input = _input;
	}

	@Column(name = "output",type=ColumnType.DOUBLE)
	public Double getOutput(){
		return this._output; 
	}

	public void setOutput(Double _output){
		this._output = _output;
	}

	@Column(name = "gain",type=ColumnType.DOUBLE)
	public Double getGain(){
		return this._gain; 
	}

	public void setGain(Double _gain){
		this._gain = _gain;
	}

	@Column(name = "project_id",type=ColumnType.VARCHAR)
	public String getProjectId(){
		return this._project_id; 
	}

	public void setProjectId(String _project_id){
		this._project_id = _project_id;
	}

	@Column(name = "date",type=ColumnType.DATE)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	@Column(name = "type",type=ColumnType.TINYINT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	// 实例化内部类
	public static SourceProjectCostEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProjectCostEntityAuxiliary();
	}

	public static class SourceProjectCostEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProjectCostEntityAuxiliary asProjectCostId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_cost_id","`project_cost_id`", colName, "setProjectCostId", "getString", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asInput(String colName, CustomColumn<?, ?>... cols){
			setColName("input","`input`", colName, "setInput", "getDouble", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asOutput(String colName, CustomColumn<?, ?>... cols){
			setColName("output","`output`", colName, "setOutput", "getDouble", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asGain(String colName, CustomColumn<?, ?>... cols){
			setColName("gain","`gain`", colName, "setGain", "getDouble", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asProjectId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_id","`project_id`", colName, "setProjectId", "getString", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceProjectCostEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
	}
}
