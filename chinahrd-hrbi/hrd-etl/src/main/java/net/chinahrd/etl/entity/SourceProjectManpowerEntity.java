package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_project_manpower")
public class SourceProjectManpowerEntity implements Entity{

	private String _project_manpower_id;
	private String _customer_id;
	private String _emp_id;
	private Double _input;
	private String _note;
	private String _project_id;
	private String _project_sub_id;
	private String _date;
	private int _type;

	public SourceProjectManpowerEntity(){
		super();
	}

	public SourceProjectManpowerEntity(String _project_manpower_id,String _customer_id,String _emp_id,Double _input,String _note,String _project_id,String _project_sub_id,String _date,int _type){
		this._project_manpower_id = _project_manpower_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._input = _input;
		this._note = _note;
		this._project_id = _project_id;
		this._project_sub_id = _project_sub_id;
		this._date = _date;
		this._type = _type;
	}

	@Override
	public String toString() {
		return "SourceProjectManpowerEntity ["+
			"	project_manpower_id="+_project_manpower_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	input="+_input+
			"	note="+_note+
			"	project_id="+_project_id+
			"	project_sub_id="+_project_sub_id+
			"	date="+_date+
			"	type="+_type+
			"]";
	}

	@Column(name = "project_manpower_id",type=ColumnType.VARCHAR)
	public String getProjectManpowerId(){
		return this._project_manpower_id; 
	}

	public void setProjectManpowerId(String _project_manpower_id){
		this._project_manpower_id = _project_manpower_id;
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

	@Column(name = "input",type=ColumnType.DOUBLE)
	public Double getInput(){
		return this._input; 
	}

	public void setInput(Double _input){
		this._input = _input;
	}

	@Column(name = "note",type=ColumnType.TEXT)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "project_id",type=ColumnType.VARCHAR)
	public String getProjectId(){
		return this._project_id; 
	}

	public void setProjectId(String _project_id){
		this._project_id = _project_id;
	}

	@Column(name = "project_sub_id",type=ColumnType.VARCHAR)
	public String getProjectSubId(){
		return this._project_sub_id; 
	}

	public void setProjectSubId(String _project_sub_id){
		this._project_sub_id = _project_sub_id;
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
	public static SourceProjectManpowerEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProjectManpowerEntityAuxiliary();
	}

	public static class SourceProjectManpowerEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProjectManpowerEntityAuxiliary asProjectManpowerId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_manpower_id","`project_manpower_id`", colName, "setProjectManpowerId", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asInput(String colName, CustomColumn<?, ?>... cols){
			setColName("input","`input`", colName, "setInput", "getDouble", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asProjectId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_id","`project_id`", colName, "setProjectId", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asProjectSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_sub_id","`project_sub_id`", colName, "setProjectSubId", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceProjectManpowerEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
	}
}
