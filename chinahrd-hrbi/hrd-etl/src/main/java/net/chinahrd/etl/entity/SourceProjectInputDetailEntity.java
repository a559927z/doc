package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_project_input_detail")
public class SourceProjectInputDetailEntity implements Entity{

	private String _project_input_detail_id;
	private String _customer_id;
	private String _project_id;
	private String _project_input_type_id;
	private Double _outlay;
	private String _date;
	private int _type;

	public SourceProjectInputDetailEntity(){
		super();
	}

	public SourceProjectInputDetailEntity(String _project_input_detail_id,String _customer_id,String _project_id,String _project_input_type_id,Double _outlay,String _date,int _type){
		this._project_input_detail_id = _project_input_detail_id;
		this._customer_id = _customer_id;
		this._project_id = _project_id;
		this._project_input_type_id = _project_input_type_id;
		this._outlay = _outlay;
		this._date = _date;
		this._type = _type;
	}

	@Override
	public String toString() {
		return "SourceProjectInputDetailEntity ["+
			"	project_input_detail_id="+_project_input_detail_id+
			"	customer_id="+_customer_id+
			"	project_id="+_project_id+
			"	project_input_type_id="+_project_input_type_id+
			"	outlay="+_outlay+
			"	date="+_date+
			"	type="+_type+
			"]";
	}

	@Column(name = "project_input_detail_id",type=ColumnType.VARCHAR)
	public String getProjectInputDetailId(){
		return this._project_input_detail_id; 
	}

	public void setProjectInputDetailId(String _project_input_detail_id){
		this._project_input_detail_id = _project_input_detail_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "project_id",type=ColumnType.VARCHAR)
	public String getProjectId(){
		return this._project_id; 
	}

	public void setProjectId(String _project_id){
		this._project_id = _project_id;
	}

	@Column(name = "project_input_type_id",type=ColumnType.VARCHAR)
	public String getProjectInputTypeId(){
		return this._project_input_type_id; 
	}

	public void setProjectInputTypeId(String _project_input_type_id){
		this._project_input_type_id = _project_input_type_id;
	}

	@Column(name = "outlay",type=ColumnType.DOUBLE)
	public Double getOutlay(){
		return this._outlay; 
	}

	public void setOutlay(Double _outlay){
		this._outlay = _outlay;
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
	public static SourceProjectInputDetailEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProjectInputDetailEntityAuxiliary();
	}

	public static class SourceProjectInputDetailEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProjectInputDetailEntityAuxiliary asProjectInputDetailId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_input_detail_id","`project_input_detail_id`", colName, "setProjectInputDetailId", "getString", cols);
			return this;
		}
		public SourceProjectInputDetailEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceProjectInputDetailEntityAuxiliary asProjectId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_id","`project_id`", colName, "setProjectId", "getString", cols);
			return this;
		}
		public SourceProjectInputDetailEntityAuxiliary asProjectInputTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("project_input_type_id","`project_input_type_id`", colName, "setProjectInputTypeId", "getString", cols);
			return this;
		}
		public SourceProjectInputDetailEntityAuxiliary asOutlay(String colName, CustomColumn<?, ?>... cols){
			setColName("outlay","`outlay`", colName, "setOutlay", "getDouble", cols);
			return this;
		}
		public SourceProjectInputDetailEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceProjectInputDetailEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
	}
}
